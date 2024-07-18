package de.danoeh.antennapod.fragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import org.greenrobot.eventbus.ThreadMode;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import android.view.View;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.viewpager2.widget.ViewPager2;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.event.FeedItemEvent;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays information about a list of FeedItems.
 */
public class ItemPagerFragment extends androidx.fragment.app.Fragment implements androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String ARG_FEEDITEMS = "feeditems";

    private static final java.lang.String ARG_FEEDITEM_POS = "feeditem_pos";

    private static final java.lang.String KEY_PAGER_ID = "pager_id";

    private androidx.viewpager2.widget.ViewPager2 pager;

    /**
     * Creates a new instance of an ItemPagerFragment.
     *
     * @param feeditems
     * 		The IDs of the FeedItems that belong to the same list
     * @param feedItemPos
     * 		The position of the FeedItem that is currently shown
     * @return The ItemFragment instance
     */
    public static de.danoeh.antennapod.fragment.ItemPagerFragment newInstance(long[] feeditems, int feedItemPos) {
        de.danoeh.antennapod.fragment.ItemPagerFragment fragment;
        fragment = new de.danoeh.antennapod.fragment.ItemPagerFragment();
        android.os.Bundle args;
        args = new android.os.Bundle();
        args.putLongArray(de.danoeh.antennapod.fragment.ItemPagerFragment.ARG_FEEDITEMS, feeditems);
        args.putInt(de.danoeh.antennapod.fragment.ItemPagerFragment.ARG_FEEDITEM_POS, java.lang.Math.max(0, feedItemPos));
        fragment.setArguments(args);
        return fragment;
    }


    private long[] feedItems;

    private de.danoeh.antennapod.model.feed.FeedItem item;

    private io.reactivex.disposables.Disposable disposable;

    private com.google.android.material.appbar.MaterialToolbar toolbar;

    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        android.view.View layout;
        layout = inflater.inflate(de.danoeh.antennapod.R.layout.feeditem_pager_fragment, container, false);
        switch(MUID_STATIC) {
            // ItemPagerFragment_0_InvalidViewFocusOperatorMutator
            case 121: {
                /**
                * Inserted by Kadabra
                */
                toolbar = layout.findViewById(de.danoeh.antennapod.R.id.toolbar);
                toolbar.requestFocus();
                break;
            }
            // ItemPagerFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1121: {
                /**
                * Inserted by Kadabra
                */
                toolbar = layout.findViewById(de.danoeh.antennapod.R.id.toolbar);
                toolbar.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            toolbar = layout.findViewById(de.danoeh.antennapod.R.id.toolbar);
            break;
        }
    }
    toolbar.setTitle("");
    toolbar.inflateMenu(de.danoeh.antennapod.R.menu.feeditem_options);
    switch(MUID_STATIC) {
        // ItemPagerFragment_2_BuggyGUIListenerOperatorMutator
        case 2121: {
            toolbar.setNavigationOnClickListener(null);
            break;
        }
        default: {
        toolbar.setNavigationOnClickListener((android.view.View v) -> getParentFragmentManager().popBackStack());
        break;
    }
}
toolbar.setOnMenuItemClickListener(this);
feedItems = getArguments().getLongArray(de.danoeh.antennapod.fragment.ItemPagerFragment.ARG_FEEDITEMS);
final int feedItemPos;
feedItemPos = java.lang.Math.max(0, getArguments().getInt(de.danoeh.antennapod.fragment.ItemPagerFragment.ARG_FEEDITEM_POS));
switch(MUID_STATIC) {
    // ItemPagerFragment_3_InvalidViewFocusOperatorMutator
    case 3121: {
        /**
        * Inserted by Kadabra
        */
        pager = layout.findViewById(de.danoeh.antennapod.R.id.pager);
        pager.requestFocus();
        break;
    }
    // ItemPagerFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4121: {
        /**
        * Inserted by Kadabra
        */
        pager = layout.findViewById(de.danoeh.antennapod.R.id.pager);
        pager.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    pager = layout.findViewById(de.danoeh.antennapod.R.id.pager);
    break;
}
}
// FragmentStatePagerAdapter documentation:
// > When using FragmentStatePagerAdapter the host ViewPager must have a valid ID set.
// When opening multiple ItemPagerFragments by clicking "item" -> "visit podcast" -> "item" -> etc,
// the ID is no longer unique and FragmentStatePagerAdapter does not display any pages.
int newId;
newId = android.view.View.generateViewId();
if ((savedInstanceState != null) && (savedInstanceState.getInt(de.danoeh.antennapod.fragment.ItemPagerFragment.KEY_PAGER_ID, 0) != 0)) {
// Restore state by using the same ID as before. ID collisions are prevented in MainActivity.
newId = savedInstanceState.getInt(de.danoeh.antennapod.fragment.ItemPagerFragment.KEY_PAGER_ID, 0);
}
pager.setId(newId);
pager.setAdapter(new de.danoeh.antennapod.fragment.ItemPagerFragment.ItemPagerAdapter(this));
pager.setCurrentItem(feedItemPos, false);
pager.setOffscreenPageLimit(1);
loadItem(feedItems[feedItemPos]);
pager.registerOnPageChangeCallback(new androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
@java.lang.Override
public void onPageSelected(int position) {
    loadItem(feedItems[position]);
}

});
org.greenrobot.eventbus.EventBus.getDefault().register(this);
return layout;
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
outState.putInt(de.danoeh.antennapod.fragment.ItemPagerFragment.KEY_PAGER_ID, pager.getId());
}


@java.lang.Override
public void onDestroyView() {
super.onDestroyView();
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
if (disposable != null) {
disposable.dispose();
}
}


private void loadItem(long itemId) {
if (disposable != null) {
disposable.dispose();
}
disposable = io.reactivex.Observable.fromCallable(() -> de.danoeh.antennapod.core.storage.DBReader.getFeedItem(itemId)).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.feed.FeedItem result) -> {
item = result;
refreshToolbarState();
}, java.lang.Throwable::printStackTrace);
}


public void refreshToolbarState() {
if (item == null) {
return;
}
if (item.hasMedia()) {
de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onPrepareMenu(toolbar.getMenu(), item);
} else {
// these are already available via button1 and button2
de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onPrepareMenu(toolbar.getMenu(), item, de.danoeh.antennapod.R.id.mark_read_item, de.danoeh.antennapod.R.id.visit_website_item);
}
}


@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem menuItem) {
if (menuItem.getItemId() == de.danoeh.antennapod.R.id.open_podcast) {
openPodcast();
return true;
}
return de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onMenuItemClicked(this, menuItem.getItemId(), item);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedItemEvent event) {
for (de.danoeh.antennapod.model.feed.FeedItem item : event.items) {
if ((this.item != null) && (this.item.getId() == item.getId())) {
    this.item = item;
    refreshToolbarState();
    return;
}
}
}


private void openPodcast() {
if (item == null) {
return;
}
androidx.fragment.app.Fragment fragment;
fragment = de.danoeh.antennapod.fragment.FeedItemlistFragment.newInstance(item.getFeedId());
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(fragment);
}


private class ItemPagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
ItemPagerAdapter(@androidx.annotation.NonNull
androidx.fragment.app.Fragment fragment) {
super(fragment);
}


@androidx.annotation.NonNull
@java.lang.Override
public androidx.fragment.app.Fragment createFragment(int position) {
return de.danoeh.antennapod.fragment.ItemFragment.newInstance(feedItems[position]);
}


@java.lang.Override
public int getItemCount() {
return feedItems.length;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
