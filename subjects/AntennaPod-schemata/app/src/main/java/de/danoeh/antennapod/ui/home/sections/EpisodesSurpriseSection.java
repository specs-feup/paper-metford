package de.danoeh.antennapod.ui.home.sections;
import de.danoeh.antennapod.event.EpisodeDownloadEvent;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.fragment.AllEpisodesFragment;
import java.util.ArrayList;
import de.danoeh.antennapod.event.PlayerStatusEvent;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.Subscribe;
import android.view.ContextMenu;
import de.danoeh.antennapod.adapter.HorizontalItemListAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.event.FeedItemEvent;
import java.util.Random;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import de.danoeh.antennapod.view.viewholder.HorizontalItemViewHolder;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import de.danoeh.antennapod.ui.home.HomeSection;
import android.view.View;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EpisodesSurpriseSection extends de.danoeh.antennapod.ui.home.HomeSection {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "EpisodesSurpriseSection";

    private static final int NUM_EPISODES = 8;

    private static int seed = 0;

    private de.danoeh.antennapod.adapter.HorizontalItemListAdapter listAdapter;

    private io.reactivex.disposables.Disposable disposable;

    private java.util.List<de.danoeh.antennapod.model.feed.FeedItem> episodes = new java.util.ArrayList<>();

    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        final android.view.View view;
        view = super.onCreateView(inflater, container, savedInstanceState);
        viewBinding.shuffleButton.setVisibility(android.view.View.VISIBLE);
        viewBinding.shuffleButton.setOnClickListener((android.view.View v) -> {
            de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection.seed = new java.util.Random().nextInt();
            /**
            * Inserted by Kadabra
            */
            viewBinding.recyclerView.scrollToPosition(0);
            loadItems();
        });
        listAdapter = new de.danoeh.antennapod.adapter.HorizontalItemListAdapter(((de.danoeh.antennapod.activity.MainActivity) (getActivity()))) {
            @java.lang.Override
            public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
                super.onCreateContextMenu(menu, v, menuInfo);
                de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection.this::onContextItemSelected);
            }

        };
        listAdapter.setDummyViews(de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection.NUM_EPISODES);
        viewBinding.recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext(), androidx.recyclerview.widget.RecyclerView.HORIZONTAL, false));
        viewBinding.recyclerView.setAdapter(listAdapter);
        int paddingHorizontal;
        switch(MUID_STATIC) {
            // EpisodesSurpriseSection_0_BinaryMutator
            case 156: {
                paddingHorizontal = ((int) (12 / getResources().getDisplayMetrics().density));
                break;
            }
            default: {
            paddingHorizontal = ((int) (12 * getResources().getDisplayMetrics().density));
            break;
        }
    }
    viewBinding.recyclerView.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
    if (de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection.seed == 0) {
        de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection.seed = new java.util.Random().nextInt();
    }
    return view;
}


@java.lang.Override
public void onStart() {
    super.onStart();
    loadItems();
}


@java.lang.Override
protected void handleMoreClick() {
    ((de.danoeh.antennapod.activity.MainActivity) (requireActivity())).loadChildFragment(new de.danoeh.antennapod.fragment.AllEpisodesFragment());
}


@java.lang.Override
protected java.lang.String getSectionTitle() {
    return getString(de.danoeh.antennapod.R.string.home_surprise_title);
}


@java.lang.Override
protected java.lang.String getMoreLinkTitle() {
    return getString(de.danoeh.antennapod.R.string.episodes_label);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onPlayerStatusChanged(de.danoeh.antennapod.event.PlayerStatusEvent event) {
    loadItems();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedItemEvent event) {
    android.util.Log.d(de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection.TAG, (("onEventMainThread() called with: " + "event = [") + event) + "]");
    for (int i = 0, size = event.items.size(); i < size; i++) {
        de.danoeh.antennapod.model.feed.FeedItem item;
        item = event.items.get(i);
        int pos;
        pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithId(episodes, item.getId());
        if (pos >= 0) {
            episodes.remove(pos);
            episodes.add(pos, item);
            listAdapter.notifyItemChangedCompat(pos);
        }
    }
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.EpisodeDownloadEvent event) {
    for (java.lang.String downloadUrl : event.getUrls()) {
        int pos;
        pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithDownloadUrl(episodes, downloadUrl);
        if (pos >= 0) {
            listAdapter.notifyItemChangedCompat(pos);
        }
    }
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
    if (listAdapter == null) {
        return;
    }
    for (int i = 0; i < listAdapter.getItemCount(); i++) {
        de.danoeh.antennapod.view.viewholder.HorizontalItemViewHolder holder;
        holder = ((de.danoeh.antennapod.view.viewholder.HorizontalItemViewHolder) (viewBinding.recyclerView.findViewHolderForAdapterPosition(i)));
        if ((holder != null) && holder.isCurrentlyPlayingItem()) {
            holder.notifyPlaybackPositionUpdated(event);
            break;
        }
    }
}


private void loadItems() {
    if (disposable != null) {
        disposable.dispose();
    }
    disposable = io.reactivex.Observable.fromCallable(() -> de.danoeh.antennapod.core.storage.DBReader.getRandomEpisodes(de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection.NUM_EPISODES, de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection.seed)).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.model.feed.FeedItem> episodes) -> {
        this.episodes = episodes;
        listAdapter.setDummyViews(0);
        listAdapter.updateData(episodes);
    }, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection.TAG, android.util.Log.getStackTraceString(error)));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
