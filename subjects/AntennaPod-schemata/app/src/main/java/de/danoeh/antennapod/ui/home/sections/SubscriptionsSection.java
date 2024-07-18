package de.danoeh.antennapod.ui.home.sections;
import de.danoeh.antennapod.ui.statistics.StatisticsFragment;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.Subscribe;
import android.view.ContextMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import java.util.List;
import de.danoeh.antennapod.event.FeedListUpdateEvent;
import de.danoeh.antennapod.fragment.SubscriptionFragment;
import de.danoeh.antennapod.model.feed.Feed;
import java.util.Collections;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.content.SharedPreferences;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import de.danoeh.antennapod.ui.home.HomeSection;
import android.view.View;
import de.danoeh.antennapod.adapter.HorizontalFeedListAdapter;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SubscriptionsSection extends de.danoeh.antennapod.ui.home.HomeSection {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "SubscriptionsSection";

    private static final int NUM_FEEDS = 8;

    private de.danoeh.antennapod.adapter.HorizontalFeedListAdapter listAdapter;

    private io.reactivex.disposables.Disposable disposable;

    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        final android.view.View view;
        view = super.onCreateView(inflater, container, savedInstanceState);
        viewBinding.recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getActivity(), androidx.recyclerview.widget.RecyclerView.HORIZONTAL, false));
        listAdapter = new de.danoeh.antennapod.adapter.HorizontalFeedListAdapter(((de.danoeh.antennapod.activity.MainActivity) (getActivity()))) {
            @java.lang.Override
            public void onCreateContextMenu(android.view.ContextMenu contextMenu, android.view.View view, android.view.ContextMenu.ContextMenuInfo contextMenuInfo) {
                super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
                de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(contextMenu, de.danoeh.antennapod.ui.home.sections.SubscriptionsSection.this::onContextItemSelected);
            }

        };
        listAdapter.setDummyViews(de.danoeh.antennapod.ui.home.sections.SubscriptionsSection.NUM_FEEDS);
        viewBinding.recyclerView.setAdapter(listAdapter);
        int paddingHorizontal;
        switch(MUID_STATIC) {
            // SubscriptionsSection_0_BinaryMutator
            case 155: {
                paddingHorizontal = ((int) (12 / getResources().getDisplayMetrics().density));
                break;
            }
            default: {
            paddingHorizontal = ((int) (12 * getResources().getDisplayMetrics().density));
            break;
        }
    }
    viewBinding.recyclerView.setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
    return view;
}


@java.lang.Override
public void onStart() {
    super.onStart();
    loadItems();
}


@java.lang.Override
protected void handleMoreClick() {
    ((de.danoeh.antennapod.activity.MainActivity) (requireActivity())).loadChildFragment(new de.danoeh.antennapod.fragment.SubscriptionFragment());
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onFeedListChanged(de.danoeh.antennapod.event.FeedListUpdateEvent event) {
    loadItems();
}


@java.lang.Override
protected java.lang.String getSectionTitle() {
    return getString(de.danoeh.antennapod.R.string.home_classics_title);
}


@java.lang.Override
protected java.lang.String getMoreLinkTitle() {
    return getString(de.danoeh.antennapod.R.string.subscriptions_label);
}


private void loadItems() {
    if (disposable != null) {
        disposable.dispose();
    }
    android.content.SharedPreferences prefs;
    prefs = getContext().getSharedPreferences(de.danoeh.antennapod.ui.statistics.StatisticsFragment.PREF_NAME, android.content.Context.MODE_PRIVATE);
    boolean includeMarkedAsPlayed;
    includeMarkedAsPlayed = prefs.getBoolean(de.danoeh.antennapod.ui.statistics.StatisticsFragment.PREF_INCLUDE_MARKED_PLAYED, false);
    disposable = io.reactivex.Observable.fromCallable(() -> de.danoeh.antennapod.core.storage.DBReader.getStatistics(includeMarkedAsPlayed, 0, java.lang.Long.MAX_VALUE).feedTime).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.core.storage.StatisticsItem> statisticsData) -> {
        java.util.Collections.sort(statisticsData, (de.danoeh.antennapod.core.storage.StatisticsItem item1,de.danoeh.antennapod.core.storage.StatisticsItem item2) -> java.lang.Long.compare(item2.timePlayed, item1.timePlayed));
        java.util.List<de.danoeh.antennapod.model.feed.Feed> feeds;
        feeds = new java.util.ArrayList<>();
        for (int i = 0; (i < statisticsData.size()) && (i < de.danoeh.antennapod.ui.home.sections.SubscriptionsSection.NUM_FEEDS); i++) {
            feeds.add(statisticsData.get(i).feed);
        }
        listAdapter.setDummyViews(0);
        listAdapter.updateData(feeds);
    }, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.ui.home.sections.SubscriptionsSection.TAG, android.util.Log.getStackTraceString(error)));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
