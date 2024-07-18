package de.danoeh.antennapod.ui.home.sections;
import java.util.Locale;
import de.danoeh.antennapod.event.EpisodeDownloadEvent;
import de.danoeh.antennapod.fragment.swipeactions.SwipeActions;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.Subscribe;
import android.view.ContextMenu;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import androidx.recyclerview.widget.LinearLayoutManager;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.event.FeedItemEvent;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import java.util.List;
import androidx.core.util.Pair;
import de.danoeh.antennapod.event.FeedListUpdateEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import de.danoeh.antennapod.ui.home.HomeSection;
import android.view.View;
import de.danoeh.antennapod.fragment.InboxFragment;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import androidx.recyclerview.widget.RecyclerView;
import de.danoeh.antennapod.adapter.EpisodeItemListAdapter;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class InboxSection extends de.danoeh.antennapod.ui.home.HomeSection {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "InboxSection";

    private static final int NUM_EPISODES = 2;

    private de.danoeh.antennapod.adapter.EpisodeItemListAdapter adapter;

    private java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items = new java.util.ArrayList<>();

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
                de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, de.danoeh.antennapod.ui.home.sections.InboxSection.this::onContextItemSelected);
            }

        };
        adapter.setDummyViews(de.danoeh.antennapod.ui.home.sections.InboxSection.NUM_EPISODES);
        viewBinding.recyclerView.setAdapter(adapter);
        de.danoeh.antennapod.fragment.swipeactions.SwipeActions swipeActions;
        swipeActions = new de.danoeh.antennapod.fragment.swipeactions.SwipeActions(this, de.danoeh.antennapod.fragment.InboxFragment.TAG);
        swipeActions.attachTo(viewBinding.recyclerView);
        swipeActions.setFilter(new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.NEW));
        return view;
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        loadItems();
    }


    @java.lang.Override
    protected void handleMoreClick() {
        ((de.danoeh.antennapod.activity.MainActivity) (requireActivity())).loadChildFragment(new de.danoeh.antennapod.fragment.InboxFragment());
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onUnreadItemsChanged(de.danoeh.antennapod.event.UnreadItemsUpdateEvent event) {
        loadItems();
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onEventMainThread(de.danoeh.antennapod.event.FeedItemEvent event) {
        loadItems();
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onFeedListChanged(de.danoeh.antennapod.event.FeedListUpdateEvent event) {
        loadItems();
    }


    @org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onEventMainThread(de.danoeh.antennapod.event.EpisodeDownloadEvent event) {
        for (java.lang.String downloadUrl : event.getUrls()) {
            int pos;
            pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithDownloadUrl(items, downloadUrl);
            if (pos >= 0) {
                adapter.notifyItemChangedCompat(pos);
            }
        }
    }


    @java.lang.Override
    protected java.lang.String getSectionTitle() {
        return getString(de.danoeh.antennapod.R.string.home_new_title);
    }


    @java.lang.Override
    protected java.lang.String getMoreLinkTitle() {
        return getString(de.danoeh.antennapod.R.string.inbox_label);
    }


    private void loadItems() {
        if (disposable != null) {
            disposable.dispose();
        }
        disposable = io.reactivex.Observable.fromCallable(() -> new androidx.core.util.Pair<>(de.danoeh.antennapod.core.storage.DBReader.getEpisodes(0, de.danoeh.antennapod.ui.home.sections.InboxSection.NUM_EPISODES, new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.NEW), de.danoeh.antennapod.storage.preferences.UserPreferences.getInboxSortedOrder()), de.danoeh.antennapod.core.storage.DBReader.getTotalEpisodeCount(new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.NEW)))).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((androidx.core.util.Pair<java.util.List<de.danoeh.antennapod.model.feed.FeedItem>, java.lang.Integer> data) -> {
            items = data.first;
            adapter.setDummyViews(0);
            adapter.updateItems(items);
            viewBinding.numNewItemsLabel.setVisibility(android.view.View.VISIBLE);
            if (data.second >= 100) {
                viewBinding.numNewItemsLabel.setText(java.lang.String.format(java.util.Locale.getDefault(), "%d+", 99));
            } else {
                viewBinding.numNewItemsLabel.setText(java.lang.String.format(java.util.Locale.getDefault(), "%d", data.second));
            }
        }, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.ui.home.sections.InboxSection.TAG, android.util.Log.getStackTraceString(error)));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
