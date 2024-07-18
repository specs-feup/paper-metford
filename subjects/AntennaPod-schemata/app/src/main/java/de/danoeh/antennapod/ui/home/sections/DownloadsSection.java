package de.danoeh.antennapod.ui.home.sections;
import de.danoeh.antennapod.fragment.swipeactions.SwipeActions;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.event.PlayerStatusEvent;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.Subscribe;
import android.view.ContextMenu;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import androidx.recyclerview.widget.LinearLayoutManager;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.event.FeedItemEvent;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.event.DownloadLogEvent;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import java.util.List;
import de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import de.danoeh.antennapod.model.feed.SortOrder;
import io.reactivex.Observable;
import de.danoeh.antennapod.ui.home.HomeSection;
import android.view.View;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.fragment.CompletedDownloadsFragment;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import androidx.recyclerview.widget.RecyclerView;
import de.danoeh.antennapod.adapter.EpisodeItemListAdapter;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DownloadsSection extends de.danoeh.antennapod.ui.home.HomeSection {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "DownloadsSection";

    private static final int NUM_EPISODES = 2;

    private de.danoeh.antennapod.adapter.EpisodeItemListAdapter adapter;

    private java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items;

    private io.reactivex.disposables.Disposable disposable;

    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        final android.view.View view;
        view = super.onCreateView(inflater, container, savedInstanceState);
        viewBinding.recyclerView.setPadding(0, 0, 0, 0);
        viewBinding.recyclerView.setOverScrollMode(androidx.recyclerview.widget.RecyclerView.OVER_SCROLL_NEVER);
        viewBinding.recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext(), androidx.recyclerview.widget.RecyclerView.VERTICAL, false));
        viewBinding.recyclerView.setRecycledViewPool(((de.danoeh.antennapod.activity.MainActivity) (requireActivity())).getRecycledViewPool());
        adapter = new de.danoeh.antennapod.adapter.EpisodeItemListAdapter(((de.danoeh.antennapod.activity.MainActivity) (requireActivity()))) {
            @java.lang.Override
            public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
                super.onCreateContextMenu(menu, v, menuInfo);
                de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, de.danoeh.antennapod.ui.home.sections.DownloadsSection.this::onContextItemSelected);
            }

        };
        adapter.setDummyViews(de.danoeh.antennapod.ui.home.sections.DownloadsSection.NUM_EPISODES);
        viewBinding.recyclerView.setAdapter(adapter);
        de.danoeh.antennapod.fragment.swipeactions.SwipeActions swipeActions;
        swipeActions = new de.danoeh.antennapod.fragment.swipeactions.SwipeActions(this, de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG);
        swipeActions.attachTo(viewBinding.recyclerView);
        swipeActions.setFilter(new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.DOWNLOADED));
        return view;
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        loadItems();
    }


    @java.lang.Override
    protected void handleMoreClick() {
        ((de.danoeh.antennapod.activity.MainActivity) (requireActivity())).loadChildFragment(new de.danoeh.antennapod.fragment.CompletedDownloadsFragment());
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onEventMainThread(de.danoeh.antennapod.event.FeedItemEvent event) {
        loadItems();
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onEventMainThread(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
        if (adapter == null) {
            return;
        }
        for (int i = 0; i < adapter.getItemCount(); i++) {
            de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder;
            holder = ((de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder) (viewBinding.recyclerView.findViewHolderForAdapterPosition(i)));
            if ((holder != null) && holder.isCurrentlyPlayingItem()) {
                holder.notifyPlaybackPositionUpdated(event);
                break;
            }
        }
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onDownloadLogChanged(de.danoeh.antennapod.core.event.DownloadLogEvent event) {
        loadItems();
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onPlayerStatusChanged(de.danoeh.antennapod.event.PlayerStatusEvent event) {
        loadItems();
    }


    @java.lang.Override
    protected java.lang.String getSectionTitle() {
        return getString(de.danoeh.antennapod.R.string.home_downloads_title);
    }


    @java.lang.Override
    protected java.lang.String getMoreLinkTitle() {
        return getString(de.danoeh.antennapod.R.string.downloads_label);
    }


    private void loadItems() {
        if (disposable != null) {
            disposable.dispose();
        }
        de.danoeh.antennapod.model.feed.SortOrder sortOrder;
        sortOrder = de.danoeh.antennapod.storage.preferences.UserPreferences.getDownloadsSortedOrder();
        disposable = io.reactivex.Observable.fromCallable(() -> de.danoeh.antennapod.core.storage.DBReader.getEpisodes(0, java.lang.Integer.MAX_VALUE, new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.DOWNLOADED), sortOrder)).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.model.feed.FeedItem> downloads) -> {
            if (downloads.size() > de.danoeh.antennapod.ui.home.sections.DownloadsSection.NUM_EPISODES) {
                downloads = downloads.subList(0, de.danoeh.antennapod.ui.home.sections.DownloadsSection.NUM_EPISODES);
            }
            items = downloads;
            adapter.setDummyViews(0);
            adapter.updateItems(items);
        }, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.ui.home.sections.DownloadsSection.TAG, android.util.Log.getStackTraceString(error)));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
