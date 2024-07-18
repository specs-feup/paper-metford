package de.danoeh.antennapod.fragment;
import java.util.ArrayList;
import android.content.res.ColorStateList;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import org.greenrobot.eventbus.Subscribe;
import de.danoeh.antennapod.core.storage.DBWriter;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.core.dialog.ConfirmationDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.shape.MaterialShapeDrawable;
import de.danoeh.antennapod.ui.home.HomeFragment;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import androidx.core.view.ViewCompat;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.os.Build;
import java.util.List;
import java.util.HashSet;
import de.danoeh.antennapod.model.feed.Feed;
import java.util.Collections;
import android.graphics.Color;
import android.util.Log;
import android.view.MenuInflater;
import de.danoeh.antennapod.core.storage.NavDrawerData;
import de.danoeh.antennapod.dialog.DrawerPreferencesDialog;
import android.view.LayoutInflater;
import de.danoeh.antennapod.dialog.RemoveFeedDialog;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import androidx.annotation.Nullable;
import com.google.android.material.shape.ShapeAppearanceModel;
import androidx.annotation.VisibleForTesting;
import androidx.core.graphics.Insets;
import java.util.Set;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.adapter.NavListAdapter;
import android.content.DialogInterface;
import org.greenrobot.eventbus.EventBus;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.ContextMenu;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import de.danoeh.antennapod.dialog.RenameItemDialog;
import de.danoeh.antennapod.R;
import org.apache.commons.lang3.StringUtils;
import de.danoeh.antennapod.event.QueueEvent;
import android.widget.ProgressBar;
import de.danoeh.antennapod.ui.appstartintent.MainActivityStarter;
import androidx.core.util.Pair;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.event.FeedListUpdateEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import de.danoeh.antennapod.dialog.TagSettingsDialog;
import io.reactivex.Observable;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import androidx.core.view.WindowInsetsCompat;
import de.danoeh.antennapod.dialog.SubscriptionsFilterDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NavDrawerFragment extends androidx.fragment.app.Fragment implements android.content.SharedPreferences.OnSharedPreferenceChangeListener {
    static final int MUID_STATIC = getMUID();
    @androidx.annotation.VisibleForTesting
    public static final java.lang.String PREF_LAST_FRAGMENT_TAG = "prefLastFragmentTag";

    private static final java.lang.String PREF_OPEN_FOLDERS = "prefOpenFolders";

    @androidx.annotation.VisibleForTesting
    public static final java.lang.String PREF_NAME = "NavDrawerPrefs";

    public static final java.lang.String TAG = "NavDrawerFragment";

    public static final java.lang.String[] NAV_DRAWER_TAGS = new java.lang.String[]{ de.danoeh.antennapod.ui.home.HomeFragment.TAG, de.danoeh.antennapod.fragment.QueueFragment.TAG, de.danoeh.antennapod.fragment.InboxFragment.TAG, de.danoeh.antennapod.fragment.AllEpisodesFragment.TAG, de.danoeh.antennapod.fragment.SubscriptionFragment.TAG, de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG, de.danoeh.antennapod.fragment.PlaybackHistoryFragment.TAG, de.danoeh.antennapod.fragment.AddFeedFragment.TAG, de.danoeh.antennapod.adapter.NavListAdapter.SUBSCRIPTION_LIST_TAG };

    private de.danoeh.antennapod.core.storage.NavDrawerData navDrawerData;

    private java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> flatItemList;

    private de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem contextPressedItem = null;

    private de.danoeh.antennapod.adapter.NavListAdapter navAdapter;

    private io.reactivex.disposables.Disposable disposable;

    private android.widget.ProgressBar progressBar;

    private java.util.Set<java.lang.String> openFolders = new java.util.HashSet<>();

    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        android.view.View root;
        root = inflater.inflate(de.danoeh.antennapod.R.layout.nav_list, container, false);
        setupDrawerRoundBackground(root);
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(root, (android.view.View view,androidx.core.view.WindowInsetsCompat insets) -> {
            androidx.core.graphics.Insets bars;
            bars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, 0);
            float navigationBarHeight;
            navigationBarHeight = 0;
            android.app.Activity activity;
            activity = getActivity();
            if ((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) && (activity != null)) {
                switch(MUID_STATIC) {
                    // NavDrawerFragment_0_BinaryMutator
                    case 140: {
                        navigationBarHeight = (getActivity().getWindow().getNavigationBarDividerColor() == android.graphics.Color.TRANSPARENT) ? 0 : 1 / getResources().getDisplayMetrics().density// Assuming the divider is 1dp in height
                        ;
                        break;
                    }
                    default: {
                    navigationBarHeight = (getActivity().getWindow().getNavigationBarDividerColor() == android.graphics.Color.TRANSPARENT) ? 0 : 1 * getResources().getDisplayMetrics().density// Assuming the divider is 1dp in height
                    ;// Assuming the divider is 1dp in height

                    break;
                }
            }
        }
        float bottomInset;
        switch(MUID_STATIC) {
            // NavDrawerFragment_1_BinaryMutator
            case 1140: {
                bottomInset = java.lang.Math.max(0.0F, java.lang.Math.round(bars.bottom + navigationBarHeight));
                break;
            }
            default: {
            bottomInset = java.lang.Math.max(0.0F, java.lang.Math.round(bars.bottom - navigationBarHeight));
            break;
        }
    }
    ((android.view.ViewGroup.MarginLayoutParams) (view.getLayoutParams())).bottomMargin = ((int) (bottomInset));
    return insets;
});
android.content.SharedPreferences preferences;
preferences = getContext().getSharedPreferences(de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_NAME, android.content.Context.MODE_PRIVATE);
openFolders = new java.util.HashSet<>(preferences.getStringSet(de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_OPEN_FOLDERS, new java.util.HashSet<>()))// Must not modify
;// Must not modify

switch(MUID_STATIC) {
    // NavDrawerFragment_2_InvalidViewFocusOperatorMutator
    case 2140: {
        /**
        * Inserted by Kadabra
        */
        progressBar = root.findViewById(de.danoeh.antennapod.R.id.progressBar);
        progressBar.requestFocus();
        break;
    }
    // NavDrawerFragment_3_ViewComponentNotVisibleOperatorMutator
    case 3140: {
        /**
        * Inserted by Kadabra
        */
        progressBar = root.findViewById(de.danoeh.antennapod.R.id.progressBar);
        progressBar.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    progressBar = root.findViewById(de.danoeh.antennapod.R.id.progressBar);
    break;
}
}
androidx.recyclerview.widget.RecyclerView navList;
switch(MUID_STATIC) {
// NavDrawerFragment_4_InvalidViewFocusOperatorMutator
case 4140: {
    /**
    * Inserted by Kadabra
    */
    navList = root.findViewById(de.danoeh.antennapod.R.id.nav_list);
    navList.requestFocus();
    break;
}
// NavDrawerFragment_5_ViewComponentNotVisibleOperatorMutator
case 5140: {
    /**
    * Inserted by Kadabra
    */
    navList = root.findViewById(de.danoeh.antennapod.R.id.nav_list);
    navList.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
navList = root.findViewById(de.danoeh.antennapod.R.id.nav_list);
break;
}
}
navAdapter = new de.danoeh.antennapod.adapter.NavListAdapter(itemAccess, getActivity());
navAdapter.setHasStableIds(true);
navList.setAdapter(navAdapter);
navList.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
switch(MUID_STATIC) {
// NavDrawerFragment_6_BuggyGUIListenerOperatorMutator
case 6140: {
root.findViewById(de.danoeh.antennapod.R.id.nav_settings).setOnClickListener(null);
break;
}
default: {
root.findViewById(de.danoeh.antennapod.R.id.nav_settings).setOnClickListener((android.view.View v) -> startActivity(new android.content.Intent(getActivity(), de.danoeh.antennapod.activity.PreferenceActivity.class)));
break;
}
}
preferences.registerOnSharedPreferenceChangeListener(this);
return root;
}


private void setupDrawerRoundBackground(android.view.View root) {
// Akin to this logic:
// https://github.com/material-components/material-components-android/blob/8938da8c/lib/java/com/google/android/material/navigation/NavigationView.java#L405
com.google.android.material.shape.ShapeAppearanceModel.Builder shapeBuilder;
shapeBuilder = com.google.android.material.shape.ShapeAppearanceModel.builder();
float cornerSize;
cornerSize = getResources().getDimension(de.danoeh.antennapod.R.dimen.drawer_corner_size);
boolean isRtl;
isRtl = getResources().getConfiguration().getLayoutDirection() == android.view.View.LAYOUT_DIRECTION_RTL;
if (isRtl) {
shapeBuilder.setTopLeftCornerSize(cornerSize).setBottomLeftCornerSize(cornerSize);
} else {
shapeBuilder.setTopRightCornerSize(cornerSize).setBottomRightCornerSize(cornerSize);
}
com.google.android.material.shape.MaterialShapeDrawable drawable;
drawable = new com.google.android.material.shape.MaterialShapeDrawable(shapeBuilder.build());
int themeColor;
themeColor = de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(root.getContext(), android.R.attr.colorBackground);
drawable.setFillColor(android.content.res.ColorStateList.valueOf(themeColor));
root.setBackground(drawable);
}


@java.lang.Override
public void onViewCreated(@androidx.annotation.NonNull
android.view.View view, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
super.onViewCreated(view, savedInstanceState);
org.greenrobot.eventbus.EventBus.getDefault().register(this);
}


@java.lang.Override
public void onDestroyView() {
super.onDestroyView();
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
if (disposable != null) {
disposable.dispose();
}
getContext().getSharedPreferences(de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_NAME, android.content.Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this);
}


@java.lang.Override
public void onCreateContextMenu(@androidx.annotation.NonNull
android.view.ContextMenu menu, @androidx.annotation.NonNull
android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
super.onCreateContextMenu(menu, v, menuInfo);
android.view.MenuInflater inflater;
inflater = getActivity().getMenuInflater();
menu.setHeaderTitle(contextPressedItem.getTitle());
if (contextPressedItem.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED) {
inflater.inflate(de.danoeh.antennapod.R.menu.nav_feed_context, menu);
// episodes are not loaded, so we cannot check if the podcast has new or unplayed ones!
} else {
inflater.inflate(de.danoeh.antennapod.R.menu.nav_folder_context, menu);
}
de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, this::onContextItemSelected);
}


@java.lang.Override
public boolean onContextItemSelected(@androidx.annotation.NonNull
android.view.MenuItem item) {
de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem pressedItem;
pressedItem = contextPressedItem;
contextPressedItem = null;
if (pressedItem == null) {
return false;
}
if (pressedItem.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED) {
return onFeedContextMenuClicked(((de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem) (pressedItem)).feed, item);
} else {
return onTagContextMenuClicked(pressedItem, item);
}
}


private boolean onFeedContextMenuClicked(de.danoeh.antennapod.model.feed.Feed feed, android.view.MenuItem item) {
final int itemId;
itemId = item.getItemId();
if (itemId == de.danoeh.antennapod.R.id.remove_all_inbox_item) {
de.danoeh.antennapod.core.dialog.ConfirmationDialog removeAllNewFlagsConfirmationDialog;
removeAllNewFlagsConfirmationDialog = new de.danoeh.antennapod.core.dialog.ConfirmationDialog(getContext(), de.danoeh.antennapod.R.string.remove_all_inbox_label, de.danoeh.antennapod.R.string.remove_all_inbox_confirmation_msg) {
@java.lang.Override
public void onConfirmButtonPressed(android.content.DialogInterface dialog) {
dialog.dismiss();
de.danoeh.antennapod.core.storage.DBWriter.removeFeedNewFlag(feed.getId());
}

};
removeAllNewFlagsConfirmationDialog.createNewDialog().show();
return true;
} else if (itemId == de.danoeh.antennapod.R.id.edit_tags) {
de.danoeh.antennapod.dialog.TagSettingsDialog.newInstance(java.util.Collections.singletonList(feed.getPreferences())).show(getChildFragmentManager(), de.danoeh.antennapod.dialog.TagSettingsDialog.TAG);
return true;
} else if (itemId == de.danoeh.antennapod.R.id.rename_item) {
new de.danoeh.antennapod.dialog.RenameItemDialog(getActivity(), feed).show();
return true;
} else if (itemId == de.danoeh.antennapod.R.id.remove_feed) {
de.danoeh.antennapod.dialog.RemoveFeedDialog.show(getContext(), feed, () -> {
if (java.lang.String.valueOf(feed.getId()).equals(de.danoeh.antennapod.fragment.NavDrawerFragment.getLastNavFragment(getContext()))) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadFragment(de.danoeh.antennapod.storage.preferences.UserPreferences.getDefaultPage(), null);
// Make sure fragment is hidden before actually starting to delete
getActivity().getSupportFragmentManager().executePendingTransactions();
}
});
return true;
}
return super.onContextItemSelected(item);
}


private boolean onTagContextMenuClicked(de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem drawerItem, android.view.MenuItem item) {
final int itemId;
itemId = item.getItemId();
if (itemId == de.danoeh.antennapod.R.id.rename_folder_item) {
new de.danoeh.antennapod.dialog.RenameItemDialog(getActivity(), drawerItem).show();
return true;
}
return super.onContextItemSelected(item);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onUnreadItemsChanged(de.danoeh.antennapod.event.UnreadItemsUpdateEvent event) {
loadData();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onFeedListChanged(de.danoeh.antennapod.event.FeedListUpdateEvent event) {
loadData();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onQueueChanged(de.danoeh.antennapod.event.QueueEvent event) {
android.util.Log.d(de.danoeh.antennapod.fragment.NavDrawerFragment.TAG, ("onQueueChanged(" + event) + ")");
// we are only interested in the number of queue items, not download status or position
if (((event.action == de.danoeh.antennapod.event.QueueEvent.Action.DELETED_MEDIA) || (event.action == de.danoeh.antennapod.event.QueueEvent.Action.SORTED)) || (event.action == de.danoeh.antennapod.event.QueueEvent.Action.MOVED)) {
return;
}
loadData();
}


@java.lang.Override
public void onResume() {
super.onResume();
loadData();
}


private final de.danoeh.antennapod.adapter.NavListAdapter.ItemAccess itemAccess = new de.danoeh.antennapod.adapter.NavListAdapter.ItemAccess() {
@java.lang.Override
public int getCount() {
if (flatItemList != null) {
return flatItemList.size();
} else {
return 0;
}
}


@java.lang.Override
public de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem getItem(int position) {
if (((flatItemList != null) && (0 <= position)) && (position < flatItemList.size())) {
return flatItemList.get(position);
} else {
return null;
}
}


@java.lang.Override
public boolean isSelected(int position) {
java.lang.String lastNavFragment;
lastNavFragment = de.danoeh.antennapod.fragment.NavDrawerFragment.getLastNavFragment(getContext());
if (position < navAdapter.getSubscriptionOffset()) {
return navAdapter.getFragmentTags().get(position).equals(lastNavFragment);
} else if (org.apache.commons.lang3.StringUtils.isNumeric(lastNavFragment)) {
// last fragment was not a list, but a feed
long feedId;
feedId = java.lang.Long.parseLong(lastNavFragment);
if (navDrawerData != null) {
de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem itemToCheck;
switch(MUID_STATIC) {
    // NavDrawerFragment_7_BinaryMutator
    case 7140: {
        itemToCheck = flatItemList.get(position + navAdapter.getSubscriptionOffset());
        break;
    }
    default: {
    itemToCheck = flatItemList.get(position - navAdapter.getSubscriptionOffset());
    break;
}
}
if (itemToCheck.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED) {
// When the same feed is displayed multiple times, it should be highlighted multiple times.
return ((de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem) (itemToCheck)).feed.getId() == feedId;
}
}
}
return false;
}


@java.lang.Override
public int getQueueSize() {
return navDrawerData != null ? navDrawerData.queueSize : 0;
}


@java.lang.Override
public int getNumberOfNewItems() {
return navDrawerData != null ? navDrawerData.numNewItems : 0;
}


@java.lang.Override
public int getNumberOfDownloadedItems() {
return navDrawerData != null ? navDrawerData.numDownloadedItems : 0;
}


@java.lang.Override
public int getReclaimableItems() {
return navDrawerData != null ? navDrawerData.reclaimableSpace : 0;
}


@java.lang.Override
public int getFeedCounterSum() {
if (navDrawerData == null) {
return 0;
}
int sum;
sum = 0;
for (int counter : navDrawerData.feedCounters.values()) {
sum += counter;
}
return sum;
}


@java.lang.Override
public void onItemClick(int position) {
int viewType;
viewType = navAdapter.getItemViewType(position);
if (viewType != de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_SECTION_DIVIDER) {
if (position < navAdapter.getSubscriptionOffset()) {
java.lang.String tag;
tag = navAdapter.getFragmentTags().get(position);
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadFragment(tag, null);
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getBottomSheet().setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
} else {
int pos;
switch(MUID_STATIC) {
// NavDrawerFragment_8_BinaryMutator
case 8140: {
    pos = position + navAdapter.getSubscriptionOffset();
    break;
}
default: {
pos = position - navAdapter.getSubscriptionOffset();
break;
}
}
de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem clickedItem;
clickedItem = flatItemList.get(pos);
if (clickedItem.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED) {
long feedId;
feedId = ((de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem) (clickedItem)).feed.getId();
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadFeedFragmentById(feedId, null);
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getBottomSheet().setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
} else {
de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem folder;
folder = ((de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem) (clickedItem));
if (openFolders.contains(folder.name)) {
openFolders.remove(folder.name);
} else {
openFolders.add(folder.name);
}
getContext().getSharedPreferences(de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_NAME, android.content.Context.MODE_PRIVATE).edit().putStringSet(de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_OPEN_FOLDERS, openFolders).apply();
disposable = io.reactivex.Observable.fromCallable(() -> makeFlatDrawerData(navDrawerData.items, 0)).subscribeOn(io.reactivex.schedulers.Schedulers.computation()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> result) -> {
flatItemList = result;
navAdapter.notifyDataSetChanged();
}, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.NavDrawerFragment.TAG, android.util.Log.getStackTraceString(error)));
}
}
} else if (de.danoeh.antennapod.storage.preferences.UserPreferences.getSubscriptionsFilter().isEnabled() && navAdapter.showSubscriptionList) {
de.danoeh.antennapod.dialog.SubscriptionsFilterDialog.showDialog(requireContext());
}
}


@java.lang.Override
public boolean onItemLongClick(int position) {
if (position < navAdapter.getFragmentTags().size()) {
de.danoeh.antennapod.dialog.DrawerPreferencesDialog.show(getContext(), () -> {
navAdapter.notifyDataSetChanged();
if (de.danoeh.antennapod.storage.preferences.UserPreferences.getHiddenDrawerItems().contains(de.danoeh.antennapod.fragment.NavDrawerFragment.getLastNavFragment(getContext()))) {
new de.danoeh.antennapod.ui.appstartintent.MainActivityStarter(getContext()).withFragmentLoaded(de.danoeh.antennapod.storage.preferences.UserPreferences.getDefaultPage()).withDrawerOpen().start();
}
});
return true;
} else {
switch(MUID_STATIC) {
// NavDrawerFragment_9_BinaryMutator
case 9140: {
contextPressedItem = flatItemList.get(position + navAdapter.getSubscriptionOffset());
break;
}
default: {
contextPressedItem = flatItemList.get(position - navAdapter.getSubscriptionOffset());
break;
}
}
return false;
}
}


@java.lang.Override
public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
de.danoeh.antennapod.fragment.NavDrawerFragment.this.onCreateContextMenu(menu, v, menuInfo);
}

};

private void loadData() {
disposable = io.reactivex.Observable.fromCallable(() -> {
de.danoeh.antennapod.core.storage.NavDrawerData data;
data = de.danoeh.antennapod.core.storage.DBReader.getNavDrawerData(de.danoeh.antennapod.storage.preferences.UserPreferences.getSubscriptionsFilter());
return new androidx.core.util.Pair<>(data, makeFlatDrawerData(data.items, 0));
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((androidx.core.util.Pair<de.danoeh.antennapod.core.storage.NavDrawerData, java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem>> result) -> {
navDrawerData = result.first;
flatItemList = result.second;
navAdapter.notifyDataSetChanged();
progressBar.setVisibility(android.view.View.GONE)// Stays hidden once there is something in the list
;// Stays hidden once there is something in the list

}, (java.lang.Throwable error) -> {
android.util.Log.e(de.danoeh.antennapod.fragment.NavDrawerFragment.TAG, android.util.Log.getStackTraceString(error));
progressBar.setVisibility(android.view.View.GONE);
});
}


private java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> makeFlatDrawerData(java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> items, int layer) {
java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> flatItems;
flatItems = new java.util.ArrayList<>();
for (de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem item : items) {
item.setLayer(layer);
flatItems.add(item);
if (item.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.TAG) {
de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem folder;
folder = ((de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem) (item));
folder.isOpen = openFolders.contains(folder.name);
if (folder.isOpen) {
switch(MUID_STATIC) {
// NavDrawerFragment_10_BinaryMutator
case 10140: {
flatItems.addAll(makeFlatDrawerData(((de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem) (item)).children, layer - 1));
break;
}
default: {
flatItems.addAll(makeFlatDrawerData(((de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem) (item)).children, layer + 1));
break;
}
}
}
}
}
return flatItems;
}


public static void saveLastNavFragment(android.content.Context context, java.lang.String tag) {
android.util.Log.d(de.danoeh.antennapod.fragment.NavDrawerFragment.TAG, ("saveLastNavFragment(tag: " + tag) + ")");
android.content.SharedPreferences prefs;
prefs = context.getSharedPreferences(de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_NAME, android.content.Context.MODE_PRIVATE);
android.content.SharedPreferences.Editor edit;
edit = prefs.edit();
if (tag != null) {
edit.putString(de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_LAST_FRAGMENT_TAG, tag);
} else {
edit.remove(de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_LAST_FRAGMENT_TAG);
}
edit.apply();
}


public static java.lang.String getLastNavFragment(android.content.Context context) {
android.content.SharedPreferences prefs;
prefs = context.getSharedPreferences(de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_NAME, android.content.Context.MODE_PRIVATE);
java.lang.String lastFragment;
lastFragment = prefs.getString(de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_LAST_FRAGMENT_TAG, de.danoeh.antennapod.ui.home.HomeFragment.TAG);
android.util.Log.d(de.danoeh.antennapod.fragment.NavDrawerFragment.TAG, "getLastNavFragment() -> " + lastFragment);
return lastFragment;
}


@java.lang.Override
public void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, java.lang.String key) {
if (de.danoeh.antennapod.fragment.NavDrawerFragment.PREF_LAST_FRAGMENT_TAG.equals(key)) {
navAdapter.notifyDataSetChanged()// Update selection
;// Update selection

}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
