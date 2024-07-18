package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.event.EpisodeDownloadEvent;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import java.util.ArrayList;
import org.greenrobot.eventbus.Subscribe;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;
import de.danoeh.antennapod.event.FeedItemEvent;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import de.danoeh.antennapod.core.event.DownloadLogEvent;
import java.util.List;
import de.danoeh.antennapod.view.EpisodeItemListRecyclerView;
import de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder;
import java.util.HashSet;
import java.util.Collections;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import de.danoeh.antennapod.view.LiftOnScrollListener;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import de.danoeh.antennapod.adapter.EpisodeItemListAdapter;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.adapter.actionbutton.DeleteActionButton;
import java.util.Set;
import de.danoeh.antennapod.fragment.swipeactions.SwipeActions;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.view.EmptyViewHandler;
import de.danoeh.antennapod.event.PlayerStatusEvent;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import android.view.ContextMenu;
import de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import android.widget.ProgressBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import de.danoeh.antennapod.model.feed.SortOrder;
import io.reactivex.Observable;
import android.view.MenuItem;
import android.view.View;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import com.leinardi.android.speeddial.SpeedDialView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays all completed downloads and provides a button to delete them.
 */
public class CompletedDownloadsFragment extends androidx.fragment.app.Fragment implements de.danoeh.antennapod.adapter.SelectableAdapter.OnSelectModeListener , androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "DownloadsFragment";

    public static final java.lang.String ARG_SHOW_LOGS = "show_logs";

    private static final java.lang.String KEY_UP_ARROW = "up_arrow";

    private java.util.Set<java.lang.String> runningDownloads = new java.util.HashSet<>();

    private java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items = new java.util.ArrayList<>();

    private de.danoeh.antennapod.fragment.CompletedDownloadsFragment.CompletedDownloadsListAdapter adapter;

    private de.danoeh.antennapod.view.EpisodeItemListRecyclerView recyclerView;

    private io.reactivex.disposables.Disposable disposable;

    private de.danoeh.antennapod.view.EmptyViewHandler emptyView;

    private boolean displayUpArrow;

    private com.leinardi.android.speeddial.SpeedDialView speedDialView;

    private de.danoeh.antennapod.fragment.swipeactions.SwipeActions swipeActions;

    private android.widget.ProgressBar progressBar;

    private com.google.android.material.appbar.MaterialToolbar toolbar;

    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        android.view.View root;
        root = inflater.inflate(de.danoeh.antennapod.R.layout.simple_list_fragment, container, false);
        switch(MUID_STATIC) {
            // CompletedDownloadsFragment_0_InvalidViewFocusOperatorMutator
            case 132: {
                /**
                * Inserted by Kadabra
                */
                toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
                toolbar.requestFocus();
                break;
            }
            // CompletedDownloadsFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1132: {
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
    toolbar.setTitle(de.danoeh.antennapod.R.string.downloads_label);
    toolbar.inflateMenu(de.danoeh.antennapod.R.menu.downloads_completed);
    inflateSortMenu(toolbar);
    toolbar.setOnMenuItemClickListener(this);
    toolbar.setOnLongClickListener((android.view.View v) -> {
        recyclerView.scrollToPosition(5);
        recyclerView.post(() -> recyclerView.smoothScrollToPosition(0));
        return false;
    });
    displayUpArrow = getParentFragmentManager().getBackStackEntryCount() != 0;
    if (savedInstanceState != null) {
        displayUpArrow = savedInstanceState.getBoolean(de.danoeh.antennapod.fragment.CompletedDownloadsFragment.KEY_UP_ARROW);
    }
    ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).setupToolbarToggle(toolbar, displayUpArrow);
    switch(MUID_STATIC) {
        // CompletedDownloadsFragment_2_InvalidViewFocusOperatorMutator
        case 2132: {
            /**
            * Inserted by Kadabra
            */
            recyclerView = root.findViewById(de.danoeh.antennapod.R.id.recyclerView);
            recyclerView.requestFocus();
            break;
        }
        // CompletedDownloadsFragment_3_ViewComponentNotVisibleOperatorMutator
        case 3132: {
            /**
            * Inserted by Kadabra
            */
            recyclerView = root.findViewById(de.danoeh.antennapod.R.id.recyclerView);
            recyclerView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        recyclerView = root.findViewById(de.danoeh.antennapod.R.id.recyclerView);
        break;
    }
}
recyclerView.setRecycledViewPool(((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getRecycledViewPool());
adapter = new de.danoeh.antennapod.fragment.CompletedDownloadsFragment.CompletedDownloadsListAdapter(((de.danoeh.antennapod.activity.MainActivity) (getActivity())));
adapter.setOnSelectModeListener(this);
recyclerView.setAdapter(adapter);
recyclerView.addOnScrollListener(new de.danoeh.antennapod.view.LiftOnScrollListener(root.findViewById(de.danoeh.antennapod.R.id.appbar)));
swipeActions = new de.danoeh.antennapod.fragment.swipeactions.SwipeActions(this, de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG).attachTo(recyclerView);
swipeActions.setFilter(new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.DOWNLOADED));
switch(MUID_STATIC) {
    // CompletedDownloadsFragment_4_InvalidViewFocusOperatorMutator
    case 4132: {
        /**
        * Inserted by Kadabra
        */
        progressBar = root.findViewById(de.danoeh.antennapod.R.id.progLoading);
        progressBar.requestFocus();
        break;
    }
    // CompletedDownloadsFragment_5_ViewComponentNotVisibleOperatorMutator
    case 5132: {
        /**
        * Inserted by Kadabra
        */
        progressBar = root.findViewById(de.danoeh.antennapod.R.id.progLoading);
        progressBar.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    progressBar = root.findViewById(de.danoeh.antennapod.R.id.progLoading);
    break;
}
}
progressBar.setVisibility(android.view.View.VISIBLE);
switch(MUID_STATIC) {
// CompletedDownloadsFragment_6_InvalidViewFocusOperatorMutator
case 6132: {
    /**
    * Inserted by Kadabra
    */
    speedDialView = root.findViewById(de.danoeh.antennapod.R.id.fabSD);
    speedDialView.requestFocus();
    break;
}
// CompletedDownloadsFragment_7_ViewComponentNotVisibleOperatorMutator
case 7132: {
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
speedDialView.inflate(de.danoeh.antennapod.R.menu.episodes_apply_action_speeddial);
speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.download_batch);
speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.mark_read_batch);
speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.mark_unread_batch);
speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.remove_from_queue_batch);
speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.remove_all_inbox_item);
speedDialView.setOnChangeListener(new com.leinardi.android.speeddial.SpeedDialView.OnChangeListener() {
@java.lang.Override
public boolean onMainActionSelected() {
return false;
}


@java.lang.Override
public void onToggleChanged(boolean open) {
if (open && (adapter.getSelectedCount() == 0)) {
    ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.no_items_selected, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
    speedDialView.close();
}
}

});
speedDialView.setOnActionSelectedListener((com.leinardi.android.speeddial.SpeedDialActionItem actionItem) -> {
new de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler(((de.danoeh.antennapod.activity.MainActivity) (getActivity())), actionItem.getId()).handleAction(adapter.getSelectedItems());
adapter.endSelectMode();
return true;
});
if ((getArguments() != null) && getArguments().getBoolean(de.danoeh.antennapod.fragment.CompletedDownloadsFragment.ARG_SHOW_LOGS, false)) {
new de.danoeh.antennapod.fragment.DownloadLogFragment().show(getChildFragmentManager(), null);
}
addEmptyView();
org.greenrobot.eventbus.EventBus.getDefault().register(this);
return root;
}


private void inflateSortMenu(com.google.android.material.appbar.MaterialToolbar toolbar) {
android.view.Menu menu;
menu = toolbar.getMenu();
android.view.MenuItem downloadsItem;
downloadsItem = menu.findItem(de.danoeh.antennapod.R.id.downloads_sort);
android.view.MenuInflater menuInflater;
menuInflater = getActivity().getMenuInflater();
menuInflater.inflate(de.danoeh.antennapod.R.menu.sort_menu, downloadsItem.getSubMenu());
// Remove the sorting options that are not needed in this fragment
menu.findItem(de.danoeh.antennapod.R.id.sort_feed_title).setVisible(false);
menu.findItem(de.danoeh.antennapod.R.id.sort_random).setVisible(false);
menu.findItem(de.danoeh.antennapod.R.id.sort_smart_shuffle).setVisible(false);
menu.findItem(de.danoeh.antennapod.R.id.keep_sorted).setVisible(false);
menu.findItem(de.danoeh.antennapod.R.id.sort_size).setVisible(true);
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
outState.putBoolean(de.danoeh.antennapod.fragment.CompletedDownloadsFragment.KEY_UP_ARROW, displayUpArrow);
super.onSaveInstanceState(outState);
}


@java.lang.Override
public void onDestroyView() {
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
adapter.endSelectMode();
if (toolbar != null) {
toolbar.setOnMenuItemClickListener(null);
toolbar.setOnLongClickListener(null);
}
super.onDestroyView();
}


@java.lang.Override
public void onStart() {
super.onStart();
loadItems();
}


@java.lang.Override
public void onStop() {
super.onStop();
if (disposable != null) {
disposable.dispose();
}
}


@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem item) {
if (item.getItemId() == de.danoeh.antennapod.R.id.refresh_item) {
de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnceOrAsk(requireContext());
return true;
} else if (item.getItemId() == de.danoeh.antennapod.R.id.action_download_logs) {
new de.danoeh.antennapod.fragment.DownloadLogFragment().show(getChildFragmentManager(), null);
return true;
} else if (item.getItemId() == de.danoeh.antennapod.R.id.action_search) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(de.danoeh.antennapod.fragment.SearchFragment.newInstance());
return true;
} else {
de.danoeh.antennapod.model.feed.SortOrder sortOrder;
sortOrder = de.danoeh.antennapod.fragment.MenuItemToSortOrderConverter.convert(item);
if (sortOrder != null) {
setSortOrder(sortOrder);
return true;
}
}
return false;
}


private void setSortOrder(de.danoeh.antennapod.model.feed.SortOrder sortOrder) {
de.danoeh.antennapod.storage.preferences.UserPreferences.setDownloadsSortedOrder(sortOrder);
loadItems();
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.EpisodeDownloadEvent event) {
java.util.Set<java.lang.String> newRunningDownloads;
newRunningDownloads = new java.util.HashSet<>();
for (java.lang.String url : event.getUrls()) {
if (de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isDownloadingEpisode(url)) {
newRunningDownloads.add(url);
}
}
if (!newRunningDownloads.equals(runningDownloads)) {
runningDownloads = newRunningDownloads;
loadItems();
return// Refreshed anyway
;// Refreshed anyway

}
for (java.lang.String downloadUrl : event.getUrls()) {
int pos;
pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithDownloadUrl(items, downloadUrl);
if (pos >= 0) {
adapter.notifyItemChangedCompat(pos);
}
}
}


@java.lang.Override
public boolean onContextItemSelected(@androidx.annotation.NonNull
android.view.MenuItem item) {
de.danoeh.antennapod.model.feed.FeedItem selectedItem;
selectedItem = adapter.getLongPressedItem();
if (selectedItem == null) {
android.util.Log.i(de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG, "Selected item at current position was null, ignoring selection");
return super.onContextItemSelected(item);
}
if (adapter.onContextItemSelected(item)) {
return true;
}
return de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onMenuItemClicked(this, item.getItemId(), selectedItem);
}


private void addEmptyView() {
emptyView = new de.danoeh.antennapod.view.EmptyViewHandler(getActivity());
emptyView.setIcon(de.danoeh.antennapod.R.drawable.ic_download);
emptyView.setTitle(de.danoeh.antennapod.R.string.no_comp_downloads_head_label);
emptyView.setMessage(de.danoeh.antennapod.R.string.no_comp_downloads_label);
emptyView.attachToRecyclerView(recyclerView);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedItemEvent event) {
android.util.Log.d(de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG, (("onEventMainThread() called with: " + "event = [") + event) + "]");
if (items == null) {
return;
} else if (adapter == null) {
loadItems();
return;
}
for (int i = 0, size = event.items.size(); i < size; i++) {
de.danoeh.antennapod.model.feed.FeedItem item;
item = event.items.get(i);
int pos;
pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithId(items, item.getId());
if (pos >= 0) {
items.remove(pos);
if (item.getMedia().isDownloaded()) {
    items.add(pos, item);
    adapter.notifyItemChangedCompat(pos);
} else {
    adapter.notifyItemRemoved(pos);
}
}
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
if (adapter != null) {
for (int i = 0; i < adapter.getItemCount(); i++) {
de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder;
holder = ((de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder) (recyclerView.findViewHolderForAdapterPosition(i)));
if ((holder != null) && holder.isCurrentlyPlayingItem()) {
    holder.notifyPlaybackPositionUpdated(event);
    break;
}
}
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onPlayerStatusChanged(de.danoeh.antennapod.event.PlayerStatusEvent event) {
loadItems();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onDownloadLogChanged(de.danoeh.antennapod.core.event.DownloadLogEvent event) {
loadItems();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onUnreadItemsChanged(de.danoeh.antennapod.event.UnreadItemsUpdateEvent event) {
loadItems();
}


private void loadItems() {
if (disposable != null) {
disposable.dispose();
}
emptyView.hide();
disposable = io.reactivex.Observable.fromCallable(() -> {
de.danoeh.antennapod.model.feed.SortOrder sortOrder;
sortOrder = de.danoeh.antennapod.storage.preferences.UserPreferences.getDownloadsSortedOrder();
java.util.List<de.danoeh.antennapod.model.feed.FeedItem> downloadedItems;
downloadedItems = de.danoeh.antennapod.core.storage.DBReader.getEpisodes(0, java.lang.Integer.MAX_VALUE, new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.DOWNLOADED), sortOrder);
java.util.List<java.lang.String> mediaUrls;
mediaUrls = new java.util.ArrayList<>();
if (runningDownloads == null) {
return downloadedItems;
}
for (java.lang.String url : runningDownloads) {
if (de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithDownloadUrl(downloadedItems, url) != (-1)) {
    continue// Already in list
    ;// Already in list

}
mediaUrls.add(url);
}
java.util.List<de.danoeh.antennapod.model.feed.FeedItem> currentDownloads;
currentDownloads = de.danoeh.antennapod.core.storage.DBReader.getFeedItemsWithUrl(mediaUrls);
currentDownloads.addAll(downloadedItems);
return currentDownloads;
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.model.feed.FeedItem> result) -> {
items = result;
adapter.setDummyViews(0);
progressBar.setVisibility(android.view.View.GONE);
adapter.updateItems(result);
}, (java.lang.Throwable error) -> {
adapter.setDummyViews(0);
adapter.updateItems(java.util.Collections.emptyList());
android.util.Log.e(de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG, android.util.Log.getStackTraceString(error));
});
}


@java.lang.Override
public void onStartSelectMode() {
swipeActions.detach();
speedDialView.setVisibility(android.view.View.VISIBLE);
}


@java.lang.Override
public void onEndSelectMode() {
speedDialView.close();
speedDialView.setVisibility(android.view.View.GONE);
swipeActions.attachTo(recyclerView);
}


private class CompletedDownloadsListAdapter extends de.danoeh.antennapod.adapter.EpisodeItemListAdapter {
public CompletedDownloadsListAdapter(de.danoeh.antennapod.activity.MainActivity mainActivity) {
super(mainActivity);
}


@java.lang.Override
public void afterBindViewHolder(de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder, int pos) {
if (!inActionMode()) {
if (holder.getFeedItem().isDownloaded()) {
    de.danoeh.antennapod.adapter.actionbutton.DeleteActionButton actionButton;
    actionButton = new de.danoeh.antennapod.adapter.actionbutton.DeleteActionButton(getItem(pos));
    actionButton.configure(holder.secondaryActionButton, holder.secondaryActionIcon, getActivity());
}
}
}


@java.lang.Override
public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
super.onCreateContextMenu(menu, v, menuInfo);
if (!inActionMode()) {
menu.findItem(de.danoeh.antennapod.R.id.multi_select).setVisible(true);
}
de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, de.danoeh.antennapod.fragment.CompletedDownloadsFragment.this::onContextItemSelected);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
