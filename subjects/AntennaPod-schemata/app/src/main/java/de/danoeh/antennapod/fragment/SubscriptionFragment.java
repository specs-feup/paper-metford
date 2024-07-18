package de.danoeh.antennapod.fragment;
import java.util.Locale;
import de.danoeh.antennapod.ui.statistics.StatisticsFragment;
import java.util.ArrayList;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import org.greenrobot.eventbus.Subscribe;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import androidx.annotation.NonNull;
import java.util.List;
import de.danoeh.antennapod.model.feed.Feed;
import android.util.Log;
import de.danoeh.antennapod.core.storage.NavDrawerData;
import android.os.Handler;
import de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter;
import de.danoeh.antennapod.view.LiftOnScrollListener;
import de.danoeh.antennapod.dialog.FeedSortDialog;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.event.FeedUpdateRunningEvent;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.view.EmptyViewHandler;
import org.greenrobot.eventbus.EventBus;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.ContextMenu;
import de.danoeh.antennapod.dialog.RenameItemDialog;
import de.danoeh.antennapod.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.ProgressBar;
import de.danoeh.antennapod.event.FeedListUpdateEvent;
import de.danoeh.antennapod.menuhandler.FeedMenuHandler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.widget.LinearLayout;
import io.reactivex.disposables.Disposable;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import android.view.MenuItem;
import android.view.View;
import android.os.Looper;
import androidx.recyclerview.widget.GridLayoutManager;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import de.danoeh.antennapod.dialog.SubscriptionsFilterDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.leinardi.android.speeddial.SpeedDialView;
import de.danoeh.antennapod.fragment.actions.FeedMultiSelectActionHandler;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Fragment for displaying feed subscriptions
 */
public class SubscriptionFragment extends androidx.fragment.app.Fragment implements androidx.appcompat.widget.Toolbar.OnMenuItemClickListener , de.danoeh.antennapod.adapter.SelectableAdapter.OnSelectModeListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "SubscriptionFragment";

    private static final java.lang.String PREFS = "SubscriptionFragment";

    private static final java.lang.String PREF_NUM_COLUMNS = "columns";

    private static final java.lang.String KEY_UP_ARROW = "up_arrow";

    private static final java.lang.String ARGUMENT_FOLDER = "folder";

    private static final int MIN_NUM_COLUMNS = 2;

    private static final int[] COLUMN_CHECKBOX_IDS = new int[]{ de.danoeh.antennapod.R.id.subscription_num_columns_2, de.danoeh.antennapod.R.id.subscription_num_columns_3, de.danoeh.antennapod.R.id.subscription_num_columns_4, de.danoeh.antennapod.R.id.subscription_num_columns_5 };

    private androidx.recyclerview.widget.RecyclerView subscriptionRecycler;

    private de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter subscriptionAdapter;

    private de.danoeh.antennapod.view.EmptyViewHandler emptyView;

    private android.widget.LinearLayout feedsFilteredMsg;

    private com.google.android.material.appbar.MaterialToolbar toolbar;

    private android.widget.ProgressBar progressBar;

    private java.lang.String displayedFolder = null;

    private boolean displayUpArrow;

    private io.reactivex.disposables.Disposable disposable;

    private android.content.SharedPreferences prefs;

    private com.leinardi.android.speeddial.SpeedDialView speedDialView;

    private java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> listItems;

    public static de.danoeh.antennapod.fragment.SubscriptionFragment newInstance(java.lang.String folderTitle) {
        de.danoeh.antennapod.fragment.SubscriptionFragment fragment;
        fragment = new de.danoeh.antennapod.fragment.SubscriptionFragment();
        android.os.Bundle args;
        args = new android.os.Bundle();
        args.putString(de.danoeh.antennapod.fragment.SubscriptionFragment.ARGUMENT_FOLDER, folderTitle);
        fragment.setArguments(args);
        return fragment;
    }


    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SubscriptionFragment_0_LengthyGUICreationOperatorMutator
            case 130: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    setRetainInstance(true);
    prefs = requireActivity().getSharedPreferences(de.danoeh.antennapod.fragment.SubscriptionFragment.PREFS, android.content.Context.MODE_PRIVATE);
}


@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    android.view.View root;
    root = inflater.inflate(de.danoeh.antennapod.R.layout.fragment_subscriptions, container, false);
    switch(MUID_STATIC) {
        // SubscriptionFragment_1_InvalidViewFocusOperatorMutator
        case 1130: {
            /**
            * Inserted by Kadabra
            */
            toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
            toolbar.requestFocus();
            break;
        }
        // SubscriptionFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2130: {
            /**
            * Inserted by Kadabra
            */
            toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
            toolbar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
        break;
    }
}
toolbar.setOnMenuItemClickListener(this);
toolbar.setOnLongClickListener((android.view.View v) -> {
    subscriptionRecycler.scrollToPosition(5);
    subscriptionRecycler.post(() -> subscriptionRecycler.smoothScrollToPosition(0));
    return false;
});
displayUpArrow = getParentFragmentManager().getBackStackEntryCount() != 0;
if (savedInstanceState != null) {
    displayUpArrow = savedInstanceState.getBoolean(de.danoeh.antennapod.fragment.SubscriptionFragment.KEY_UP_ARROW);
}
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).setupToolbarToggle(toolbar, displayUpArrow);
toolbar.inflateMenu(de.danoeh.antennapod.R.menu.subscriptions);
for (int i = 0; i < de.danoeh.antennapod.fragment.SubscriptionFragment.COLUMN_CHECKBOX_IDS.length; i++) {
    switch(MUID_STATIC) {
        // SubscriptionFragment_3_BinaryMutator
        case 3130: {
            // Do this in Java to localize numbers
            toolbar.getMenu().findItem(de.danoeh.antennapod.fragment.SubscriptionFragment.COLUMN_CHECKBOX_IDS[i]).setTitle(java.lang.String.format(java.util.Locale.getDefault(), "%d", i - de.danoeh.antennapod.fragment.SubscriptionFragment.MIN_NUM_COLUMNS));
            break;
        }
        default: {
        // Do this in Java to localize numbers
        toolbar.getMenu().findItem(de.danoeh.antennapod.fragment.SubscriptionFragment.COLUMN_CHECKBOX_IDS[i]).setTitle(java.lang.String.format(java.util.Locale.getDefault(), "%d", i + de.danoeh.antennapod.fragment.SubscriptionFragment.MIN_NUM_COLUMNS));
        break;
    }
}
}
refreshToolbarState();
if (getArguments() != null) {
displayedFolder = getArguments().getString(de.danoeh.antennapod.fragment.SubscriptionFragment.ARGUMENT_FOLDER, null);
if (displayedFolder != null) {
    toolbar.setTitle(displayedFolder);
}
}
switch(MUID_STATIC) {
// SubscriptionFragment_4_InvalidViewFocusOperatorMutator
case 4130: {
    /**
    * Inserted by Kadabra
    */
    subscriptionRecycler = root.findViewById(de.danoeh.antennapod.R.id.subscriptions_grid);
    subscriptionRecycler.requestFocus();
    break;
}
// SubscriptionFragment_5_ViewComponentNotVisibleOperatorMutator
case 5130: {
    /**
    * Inserted by Kadabra
    */
    subscriptionRecycler = root.findViewById(de.danoeh.antennapod.R.id.subscriptions_grid);
    subscriptionRecycler.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
subscriptionRecycler = root.findViewById(de.danoeh.antennapod.R.id.subscriptions_grid);
break;
}
}
subscriptionRecycler.addItemDecoration(new de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter.GridDividerItemDecorator());
registerForContextMenu(subscriptionRecycler);
subscriptionRecycler.addOnScrollListener(new de.danoeh.antennapod.view.LiftOnScrollListener(root.findViewById(de.danoeh.antennapod.R.id.appbar)));
subscriptionAdapter = new de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter(((de.danoeh.antennapod.activity.MainActivity) (getActivity()))) {
@java.lang.Override
public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
super.onCreateContextMenu(menu, v, menuInfo);
de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, de.danoeh.antennapod.fragment.SubscriptionFragment.this::onContextItemSelected);
}

};
setColumnNumber(prefs.getInt(de.danoeh.antennapod.fragment.SubscriptionFragment.PREF_NUM_COLUMNS, getDefaultNumOfColumns()));
subscriptionAdapter.setOnSelectModeListener(this);
subscriptionRecycler.setAdapter(subscriptionAdapter);
setupEmptyView();
switch(MUID_STATIC) {
// SubscriptionFragment_6_InvalidViewFocusOperatorMutator
case 6130: {
/**
* Inserted by Kadabra
*/
progressBar = root.findViewById(de.danoeh.antennapod.R.id.progressBar);
progressBar.requestFocus();
break;
}
// SubscriptionFragment_7_ViewComponentNotVisibleOperatorMutator
case 7130: {
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
progressBar.setVisibility(android.view.View.VISIBLE);
com.google.android.material.floatingactionbutton.FloatingActionButton subscriptionAddButton;
switch(MUID_STATIC) {
// SubscriptionFragment_8_InvalidViewFocusOperatorMutator
case 8130: {
/**
* Inserted by Kadabra
*/
subscriptionAddButton = root.findViewById(de.danoeh.antennapod.R.id.subscriptions_add);
subscriptionAddButton.requestFocus();
break;
}
// SubscriptionFragment_9_ViewComponentNotVisibleOperatorMutator
case 9130: {
/**
* Inserted by Kadabra
*/
subscriptionAddButton = root.findViewById(de.danoeh.antennapod.R.id.subscriptions_add);
subscriptionAddButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
subscriptionAddButton = root.findViewById(de.danoeh.antennapod.R.id.subscriptions_add);
break;
}
}
switch(MUID_STATIC) {
// SubscriptionFragment_10_BuggyGUIListenerOperatorMutator
case 10130: {
subscriptionAddButton.setOnClickListener(null);
break;
}
default: {
subscriptionAddButton.setOnClickListener((android.view.View view) -> {
if (getActivity() instanceof de.danoeh.antennapod.activity.MainActivity) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(new de.danoeh.antennapod.fragment.AddFeedFragment());
}
});
break;
}
}
switch(MUID_STATIC) {
// SubscriptionFragment_11_InvalidViewFocusOperatorMutator
case 11130: {
/**
* Inserted by Kadabra
*/
feedsFilteredMsg = root.findViewById(de.danoeh.antennapod.R.id.feeds_filtered_message);
feedsFilteredMsg.requestFocus();
break;
}
// SubscriptionFragment_12_ViewComponentNotVisibleOperatorMutator
case 12130: {
/**
* Inserted by Kadabra
*/
feedsFilteredMsg = root.findViewById(de.danoeh.antennapod.R.id.feeds_filtered_message);
feedsFilteredMsg.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
feedsFilteredMsg = root.findViewById(de.danoeh.antennapod.R.id.feeds_filtered_message);
break;
}
}
switch(MUID_STATIC) {
// SubscriptionFragment_13_BuggyGUIListenerOperatorMutator
case 13130: {
feedsFilteredMsg.setOnClickListener(null);
break;
}
default: {
feedsFilteredMsg.setOnClickListener((android.view.View l) -> de.danoeh.antennapod.dialog.SubscriptionsFilterDialog.showDialog(requireContext()));
break;
}
}
androidx.swiperefreshlayout.widget.SwipeRefreshLayout swipeRefreshLayout;
switch(MUID_STATIC) {
// SubscriptionFragment_14_InvalidViewFocusOperatorMutator
case 14130: {
/**
* Inserted by Kadabra
*/
swipeRefreshLayout = root.findViewById(de.danoeh.antennapod.R.id.swipeRefresh);
swipeRefreshLayout.requestFocus();
break;
}
// SubscriptionFragment_15_ViewComponentNotVisibleOperatorMutator
case 15130: {
/**
* Inserted by Kadabra
*/
swipeRefreshLayout = root.findViewById(de.danoeh.antennapod.R.id.swipeRefresh);
swipeRefreshLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
swipeRefreshLayout = root.findViewById(de.danoeh.antennapod.R.id.swipeRefresh);
break;
}
}
swipeRefreshLayout.setDistanceToTriggerSync(getResources().getInteger(de.danoeh.antennapod.R.integer.swipe_refresh_distance));
swipeRefreshLayout.setOnRefreshListener(() -> {
de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnceOrAsk(requireContext());
new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> swipeRefreshLayout.setRefreshing(false), getResources().getInteger(de.danoeh.antennapod.R.integer.swipe_to_refresh_duration_in_ms));
});
switch(MUID_STATIC) {
// SubscriptionFragment_16_InvalidViewFocusOperatorMutator
case 16130: {
/**
* Inserted by Kadabra
*/
speedDialView = root.findViewById(de.danoeh.antennapod.R.id.fabSD);
speedDialView.requestFocus();
break;
}
// SubscriptionFragment_17_ViewComponentNotVisibleOperatorMutator
case 17130: {
/**
* Inserted by Kadabra
*/
speedDialView = root.findViewById(de.danoeh.antennapod.R.id.fabSD);
speedDialView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
speedDialView = root.findViewById(de.danoeh.antennapod.R.id.fabSD);
break;
}
}
speedDialView.setOverlayLayout(root.findViewById(de.danoeh.antennapod.R.id.fabSDOverlay));
speedDialView.inflate(de.danoeh.antennapod.R.menu.nav_feed_action_speeddial);
speedDialView.setOnChangeListener(new com.leinardi.android.speeddial.SpeedDialView.OnChangeListener() {
@java.lang.Override
public boolean onMainActionSelected() {
return false;
}


@java.lang.Override
public void onToggleChanged(boolean isOpen) {
}

});
speedDialView.setOnActionSelectedListener((com.leinardi.android.speeddial.SpeedDialActionItem actionItem) -> {
new de.danoeh.antennapod.fragment.actions.FeedMultiSelectActionHandler(((de.danoeh.antennapod.activity.MainActivity) (getActivity())), subscriptionAdapter.getSelectedItems()).handleAction(actionItem.getId());
return true;
});
return root;
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
outState.putBoolean(de.danoeh.antennapod.fragment.SubscriptionFragment.KEY_UP_ARROW, displayUpArrow);
super.onSaveInstanceState(outState);
}


private void refreshToolbarState() {
int columns;
columns = prefs.getInt(de.danoeh.antennapod.fragment.SubscriptionFragment.PREF_NUM_COLUMNS, getDefaultNumOfColumns());
switch(MUID_STATIC) {
// SubscriptionFragment_18_BinaryMutator
case 18130: {
toolbar.getMenu().findItem(de.danoeh.antennapod.fragment.SubscriptionFragment.COLUMN_CHECKBOX_IDS[columns + de.danoeh.antennapod.fragment.SubscriptionFragment.MIN_NUM_COLUMNS]).setChecked(true);
break;
}
default: {
toolbar.getMenu().findItem(de.danoeh.antennapod.fragment.SubscriptionFragment.COLUMN_CHECKBOX_IDS[columns - de.danoeh.antennapod.fragment.SubscriptionFragment.MIN_NUM_COLUMNS]).setChecked(true);
break;
}
}
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedUpdateRunningEvent event) {
de.danoeh.antennapod.core.menuhandler.MenuItemUtils.updateRefreshMenuItem(toolbar.getMenu(), de.danoeh.antennapod.R.id.refresh_item, event.isFeedUpdateRunning);
}


@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem item) {
final int itemId;
itemId = item.getItemId();
if (itemId == de.danoeh.antennapod.R.id.refresh_item) {
de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnceOrAsk(requireContext());
return true;
} else if (itemId == de.danoeh.antennapod.R.id.subscriptions_filter) {
de.danoeh.antennapod.dialog.SubscriptionsFilterDialog.showDialog(requireContext());
return true;
} else if (itemId == de.danoeh.antennapod.R.id.subscriptions_sort) {
de.danoeh.antennapod.dialog.FeedSortDialog.showDialog(requireContext());
return true;
} else if (itemId == de.danoeh.antennapod.R.id.subscription_num_columns_2) {
setColumnNumber(2);
return true;
} else if (itemId == de.danoeh.antennapod.R.id.subscription_num_columns_3) {
setColumnNumber(3);
return true;
} else if (itemId == de.danoeh.antennapod.R.id.subscription_num_columns_4) {
setColumnNumber(4);
return true;
} else if (itemId == de.danoeh.antennapod.R.id.subscription_num_columns_5) {
setColumnNumber(5);
return true;
} else if (itemId == de.danoeh.antennapod.R.id.action_search) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(de.danoeh.antennapod.fragment.SearchFragment.newInstance());
return true;
} else if (itemId == de.danoeh.antennapod.R.id.action_statistics) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(new de.danoeh.antennapod.ui.statistics.StatisticsFragment());
return true;
}
return false;
}


private void setColumnNumber(int columns) {
androidx.recyclerview.widget.GridLayoutManager gridLayoutManager;
gridLayoutManager = new androidx.recyclerview.widget.GridLayoutManager(getContext(), columns, androidx.recyclerview.widget.RecyclerView.VERTICAL, false);
subscriptionAdapter.setColumnCount(columns);
subscriptionRecycler.setLayoutManager(gridLayoutManager);
prefs.edit().putInt(de.danoeh.antennapod.fragment.SubscriptionFragment.PREF_NUM_COLUMNS, columns).apply();
refreshToolbarState();
}


private void setupEmptyView() {
emptyView = new de.danoeh.antennapod.view.EmptyViewHandler(getContext());
emptyView.setIcon(de.danoeh.antennapod.R.drawable.ic_subscriptions);
emptyView.setTitle(de.danoeh.antennapod.R.string.no_subscriptions_head_label);
emptyView.setMessage(de.danoeh.antennapod.R.string.no_subscriptions_label);
emptyView.attachToRecyclerView(subscriptionRecycler);
}


@java.lang.Override
public void onStart() {
super.onStart();
org.greenrobot.eventbus.EventBus.getDefault().register(this);
loadSubscriptions();
}


@java.lang.Override
public void onStop() {
super.onStop();
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
if (disposable != null) {
disposable.dispose();
}
if (subscriptionAdapter != null) {
subscriptionAdapter.endSelectMode();
}
}


private void loadSubscriptions() {
if (disposable != null) {
disposable.dispose();
}
emptyView.hide();
disposable = io.reactivex.Observable.fromCallable(() -> {
de.danoeh.antennapod.core.storage.NavDrawerData data;
data = de.danoeh.antennapod.core.storage.DBReader.getNavDrawerData(de.danoeh.antennapod.storage.preferences.UserPreferences.getSubscriptionsFilter());
java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> items;
items = data.items;
for (de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem item : items) {
if ((item.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.TAG) && item.getTitle().equals(displayedFolder)) {
return ((de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem) (item)).children;
}
}
return items;
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> result) -> {
if ((listItems != null) && (listItems.size() > result.size())) {
// We have fewer items. This can result in items being selected that are no longer visible.
subscriptionAdapter.endSelectMode();
}
listItems = result;
progressBar.setVisibility(android.view.View.GONE);
subscriptionAdapter.setItems(result);
emptyView.updateVisibility();
}, (java.lang.Throwable error) -> {
android.util.Log.e(de.danoeh.antennapod.fragment.SubscriptionFragment.TAG, android.util.Log.getStackTraceString(error));
});
if (de.danoeh.antennapod.storage.preferences.UserPreferences.getSubscriptionsFilter().isEnabled()) {
feedsFilteredMsg.setVisibility(android.view.View.VISIBLE);
} else {
feedsFilteredMsg.setVisibility(android.view.View.GONE);
}
}


private int getDefaultNumOfColumns() {
return getResources().getInteger(de.danoeh.antennapod.R.integer.subscriptions_default_num_of_columns);
}


@java.lang.Override
public boolean onContextItemSelected(android.view.MenuItem item) {
de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem drawerItem;
drawerItem = subscriptionAdapter.getSelectedItem();
if (drawerItem == null) {
return false;
}
int itemId;
itemId = item.getItemId();
if ((drawerItem.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.TAG) && (itemId == de.danoeh.antennapod.R.id.rename_folder_item)) {
new de.danoeh.antennapod.dialog.RenameItemDialog(getActivity(), drawerItem).show();
return true;
}
de.danoeh.antennapod.model.feed.Feed feed;
feed = ((de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem) (drawerItem)).feed;
if (itemId == de.danoeh.antennapod.R.id.multi_select) {
speedDialView.setVisibility(android.view.View.VISIBLE);
return subscriptionAdapter.onContextItemSelected(item);
}
return de.danoeh.antennapod.menuhandler.FeedMenuHandler.onMenuItemClicked(this, item.getItemId(), feed, this::loadSubscriptions);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onFeedListChanged(de.danoeh.antennapod.event.FeedListUpdateEvent event) {
loadSubscriptions();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onUnreadItemsChanged(de.danoeh.antennapod.event.UnreadItemsUpdateEvent event) {
loadSubscriptions();
}


@java.lang.Override
public void onEndSelectMode() {
speedDialView.close();
speedDialView.setVisibility(android.view.View.GONE);
subscriptionAdapter.setItems(listItems);
}


@java.lang.Override
public void onStartSelectMode() {
java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> feedsOnly;
feedsOnly = new java.util.ArrayList<>();
for (de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem item : listItems) {
if (item.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED) {
feedsOnly.add(item);
}
}
subscriptionAdapter.setItems(feedsOnly);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
