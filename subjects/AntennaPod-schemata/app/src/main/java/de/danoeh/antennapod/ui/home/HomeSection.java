package de.danoeh.antennapod.ui.home;
import de.danoeh.antennapod.menuhandler.FeedMenuHandler;
import java.util.Locale;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import android.text.TextUtils;
import androidx.recyclerview.widget.DefaultItemAnimator;
import org.greenrobot.eventbus.EventBus;
import android.view.MenuItem;
import de.danoeh.antennapod.adapter.HorizontalItemListAdapter;
import androidx.fragment.app.Fragment;
import android.view.View;
import de.danoeh.antennapod.adapter.HorizontalFeedListAdapter;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import de.danoeh.antennapod.adapter.EpisodeItemListAdapter;
import de.danoeh.antennapod.databinding.HomeSectionBinding;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.model.feed.Feed;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Section on the HomeFragment
 */
public abstract class HomeSection extends androidx.fragment.app.Fragment implements android.view.View.OnCreateContextMenuListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "HomeSection";

    protected de.danoeh.antennapod.databinding.HomeSectionBinding viewBinding;

    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        viewBinding = de.danoeh.antennapod.databinding.HomeSectionBinding.inflate(inflater);
        viewBinding.titleLabel.setText(getSectionTitle());
        if (android.text.TextUtils.getLayoutDirectionFromLocale(java.util.Locale.getDefault()) == android.view.View.LAYOUT_DIRECTION_LTR) {
            viewBinding.moreButton.setText(getMoreLinkTitle() + " »");
        } else {
            viewBinding.moreButton.setText("« " + getMoreLinkTitle());
        }
        switch(MUID_STATIC) {
            // HomeSection_0_BuggyGUIListenerOperatorMutator
            case 161: {
                viewBinding.moreButton.setOnClickListener(null);
                break;
            }
            default: {
            viewBinding.moreButton.setOnClickListener((android.view.View view) -> handleMoreClick());
            break;
        }
    }
    if (android.text.TextUtils.isEmpty(getMoreLinkTitle())) {
        viewBinding.moreButton.setVisibility(android.view.View.INVISIBLE);
    }
    // Dummies are necessary to ensure height, but do not animate them
    viewBinding.recyclerView.setItemAnimator(null);
    viewBinding.recyclerView.postDelayed(() -> viewBinding.recyclerView.setItemAnimator(new androidx.recyclerview.widget.DefaultItemAnimator()), 500);
    return viewBinding.getRoot();
}


@java.lang.Override
public boolean onContextItemSelected(@androidx.annotation.NonNull
android.view.MenuItem item) {
    if (((!getUserVisibleHint()) || (!isVisible())) || (!isMenuVisible())) {
        // The method is called on all fragments in a ViewPager, so this needs to be ignored in invisible ones.
        // Apparently, none of the visibility check method works reliably on its own, so we just use all.
        return false;
    }
    if (viewBinding.recyclerView.getAdapter() instanceof de.danoeh.antennapod.adapter.HorizontalFeedListAdapter) {
        de.danoeh.antennapod.adapter.HorizontalFeedListAdapter adapter;
        adapter = ((de.danoeh.antennapod.adapter.HorizontalFeedListAdapter) (viewBinding.recyclerView.getAdapter()));
        de.danoeh.antennapod.model.feed.Feed selectedFeed;
        selectedFeed = adapter.getLongPressedItem();
        return (selectedFeed != null) && de.danoeh.antennapod.menuhandler.FeedMenuHandler.onMenuItemClicked(this, item.getItemId(), selectedFeed, () -> {
        });
    }
    de.danoeh.antennapod.model.feed.FeedItem longPressedItem;
    if (viewBinding.recyclerView.getAdapter() instanceof de.danoeh.antennapod.adapter.EpisodeItemListAdapter) {
        de.danoeh.antennapod.adapter.EpisodeItemListAdapter adapter;
        adapter = ((de.danoeh.antennapod.adapter.EpisodeItemListAdapter) (viewBinding.recyclerView.getAdapter()));
        longPressedItem = adapter.getLongPressedItem();
    } else if (viewBinding.recyclerView.getAdapter() instanceof de.danoeh.antennapod.adapter.HorizontalItemListAdapter) {
        de.danoeh.antennapod.adapter.HorizontalItemListAdapter adapter;
        adapter = ((de.danoeh.antennapod.adapter.HorizontalItemListAdapter) (viewBinding.recyclerView.getAdapter()));
        longPressedItem = adapter.getLongPressedItem();
    } else {
        return false;
    }
    if (longPressedItem == null) {
        android.util.Log.i(de.danoeh.antennapod.ui.home.HomeSection.TAG, "Selected item or listAdapter was null, ignoring selection");
        return super.onContextItemSelected(item);
    }
    return de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onMenuItemClicked(this, item.getItemId(), longPressedItem);
}


@java.lang.Override
public void onStart() {
    super.onStart();
    org.greenrobot.eventbus.EventBus.getDefault().register(this);
    registerForContextMenu(viewBinding.recyclerView);
}


@java.lang.Override
public void onStop() {
    super.onStop();
    org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
    unregisterForContextMenu(viewBinding.recyclerView);
}


protected abstract java.lang.String getSectionTitle();


protected abstract java.lang.String getMoreLinkTitle();


protected abstract void handleMoreClick();


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
