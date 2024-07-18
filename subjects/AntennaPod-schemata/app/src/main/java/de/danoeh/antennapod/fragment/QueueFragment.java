package de.danoeh.antennapod.fragment;
import java.util.Locale;
import de.danoeh.antennapod.event.EpisodeDownloadEvent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.danoeh.antennapod.core.storage.DBWriter;
import org.greenrobot.eventbus.Subscribe;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.core.dialog.ConfirmationDialog;
import com.google.android.material.appbar.MaterialToolbar;
import de.danoeh.antennapod.event.FeedItemEvent;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.Converter;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import android.widget.TextView;
import java.util.List;
import de.danoeh.antennapod.view.EpisodeItemListRecyclerView;
import de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.MenuInflater;
import android.os.Handler;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.view.LiftOnScrollListener;
import android.widget.CheckBox;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.event.FeedUpdateRunningEvent;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import de.danoeh.antennapod.adapter.EpisodeItemListAdapter;
import com.google.android.material.snackbar.Snackbar;
import de.danoeh.antennapod.core.feed.util.PlaybackSpeedUtils;
import de.danoeh.antennapod.fragment.swipeactions.SwipeActions;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import org.greenrobot.eventbus.ThreadMode;
import android.content.DialogInterface;
import de.danoeh.antennapod.view.EmptyViewHandler;
import de.danoeh.antennapod.event.PlayerStatusEvent;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import android.view.ContextMenu;
import de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import androidx.recyclerview.widget.ItemTouchHelper;
import de.danoeh.antennapod.R;
import android.view.KeyEvent;
import de.danoeh.antennapod.event.QueueEvent;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import android.widget.ProgressBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import de.danoeh.antennapod.model.feed.SortOrder;
import io.reactivex.Observable;
import android.view.MenuItem;
import android.view.View;
import android.os.Looper;
import de.danoeh.antennapod.adapter.QueueRecyclerAdapter;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import androidx.recyclerview.widget.RecyclerView;
import com.leinardi.android.speeddial.SpeedDialView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Shows all items in the queue.
 */
public class QueueFragment extends androidx.fragment.app.Fragment implements androidx.appcompat.widget.Toolbar.OnMenuItemClickListener , de.danoeh.antennapod.adapter.SelectableAdapter.OnSelectModeListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "QueueFragment";

    private static final java.lang.String KEY_UP_ARROW = "up_arrow";

    private android.widget.TextView infoBar;

    private de.danoeh.antennapod.view.EpisodeItemListRecyclerView recyclerView;

    private de.danoeh.antennapod.adapter.QueueRecyclerAdapter recyclerAdapter;

    private de.danoeh.antennapod.view.EmptyViewHandler emptyView;

    private com.google.android.material.appbar.MaterialToolbar toolbar;

    private boolean displayUpArrow;

    private java.util.List<de.danoeh.antennapod.model.feed.FeedItem> queue;

    private static final java.lang.String PREFS = "QueueFragment";

    private static final java.lang.String PREF_SHOW_LOCK_WARNING = "show_lock_warning";

    private io.reactivex.disposables.Disposable disposable;

    private de.danoeh.antennapod.fragment.swipeactions.SwipeActions swipeActions;

    private android.content.SharedPreferences prefs;

    private com.leinardi.android.speeddial.SpeedDialView speedDialView;

    private android.widget.ProgressBar progressBar;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // QueueFragment_0_LengthyGUICreationOperatorMutator
            case 122: {
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
    prefs = getActivity().getSharedPreferences(de.danoeh.antennapod.fragment.QueueFragment.PREFS, android.content.Context.MODE_PRIVATE);
}


@java.lang.Override
public void onStart() {
    super.onStart();
    if (queue != null) {
        recyclerView.restoreScrollPosition(de.danoeh.antennapod.fragment.QueueFragment.TAG);
    }
    loadItems(true);
    org.greenrobot.eventbus.EventBus.getDefault().register(this);
}


@java.lang.Override
public void onPause() {
    super.onPause();
    recyclerView.saveScrollPosition(de.danoeh.antennapod.fragment.QueueFragment.TAG);
}


@java.lang.Override
public void onStop() {
    super.onStop();
    org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
    if (disposable != null) {
        disposable.dispose();
    }
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.QueueEvent event) {
    android.util.Log.d(de.danoeh.antennapod.fragment.QueueFragment.TAG, (("onEventMainThread() called with: " + "event = [") + event) + "]");
    if (queue == null) {
        return;
    } else if (recyclerAdapter == null) {
        loadItems(true);
        return;
    }
    switch (event.action) {
        case ADDED :
            queue.add(event.position, event.item);
            recyclerAdapter.notifyItemInserted(event.position);
            break;
        case SET_QUEUE :
        case SORTED :
            // Deliberate fall-through
            queue = event.items;
            recyclerAdapter.updateItems(event.items);
            break;
        case REMOVED :
        case IRREVERSIBLE_REMOVED :
            int position;
            position = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithId(queue, event.item.getId());
            queue.remove(position);
            recyclerAdapter.notifyItemRemoved(position);
            break;
        case CLEARED :
            queue.clear();
            recyclerAdapter.updateItems(queue);
            break;
        case MOVED :
            return;
    }
    recyclerView.saveScrollPosition(de.danoeh.antennapod.fragment.QueueFragment.TAG);
    refreshInfoBar();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedItemEvent event) {
    android.util.Log.d(de.danoeh.antennapod.fragment.QueueFragment.TAG, (("onEventMainThread() called with: " + "event = [") + event) + "]");
    if (queue == null) {
        return;
    } else if (recyclerAdapter == null) {
        loadItems(true);
        return;
    }
    for (int i = 0, size = event.items.size(); i < size; i++) {
        de.danoeh.antennapod.model.feed.FeedItem item;
        item = event.items.get(i);
        int pos;
        pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithId(queue, item.getId());
        if (pos >= 0) {
            queue.remove(pos);
            queue.add(pos, item);
            recyclerAdapter.notifyItemChangedCompat(pos);
            refreshInfoBar();
        }
    }
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.EpisodeDownloadEvent event) {
    if (queue == null) {
        return;
    }
    for (java.lang.String downloadUrl : event.getUrls()) {
        int pos;
        pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithDownloadUrl(queue, downloadUrl);
        if (pos >= 0) {
            recyclerAdapter.notifyItemChangedCompat(pos);
        }
    }
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
    if (recyclerAdapter != null) {
        for (int i = 0; i < recyclerAdapter.getItemCount(); i++) {
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
    loadItems(false);
    refreshToolbarState();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onUnreadItemsChanged(de.danoeh.antennapod.event.UnreadItemsUpdateEvent event) {
    // Sent when playback position is reset
    loadItems(false);
    refreshToolbarState();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onKeyUp(android.view.KeyEvent event) {
    if (((!isAdded()) || (!isVisible())) || (!isMenuVisible())) {
        return;
    }
    switch (event.getKeyCode()) {
        case android.view.KeyEvent.KEYCODE_T :
            recyclerView.smoothScrollToPosition(0);
            break;
        case android.view.KeyEvent.KEYCODE_B :
            switch(MUID_STATIC) {
                // QueueFragment_1_BinaryMutator
                case 1122: {
                    recyclerView.smoothScrollToPosition(recyclerAdapter.getItemCount() + 1);
                    break;
                }
                default: {
                recyclerView.smoothScrollToPosition(recyclerAdapter.getItemCount() - 1);
                break;
            }
        }
        break;
    default :
        break;
}
}


@java.lang.Override
public void onDestroyView() {
super.onDestroyView();
if (recyclerAdapter != null) {
    recyclerAdapter.endSelectMode();
}
recyclerAdapter = null;
if (toolbar != null) {
    toolbar.setOnMenuItemClickListener(null);
    toolbar.setOnLongClickListener(null);
}
}


private void refreshToolbarState() {
boolean keepSorted;
keepSorted = de.danoeh.antennapod.storage.preferences.UserPreferences.isQueueKeepSorted();
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.queue_lock).setChecked(de.danoeh.antennapod.storage.preferences.UserPreferences.isQueueLocked());
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.queue_lock).setVisible(!keepSorted);
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.sort_random).setVisible(!keepSorted);
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.keep_sorted).setChecked(keepSorted);
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedUpdateRunningEvent event) {
de.danoeh.antennapod.core.menuhandler.MenuItemUtils.updateRefreshMenuItem(toolbar.getMenu(), de.danoeh.antennapod.R.id.refresh_item, event.isFeedUpdateRunning);
}


@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem item) {
final int itemId;
itemId = item.getItemId();
if (itemId == de.danoeh.antennapod.R.id.queue_lock) {
    toggleQueueLock();
    return true;
} else if (itemId == de.danoeh.antennapod.R.id.refresh_item) {
    de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnceOrAsk(requireContext());
    return true;
} else if (itemId == de.danoeh.antennapod.R.id.clear_queue) {
    // make sure the user really wants to clear the queue
    de.danoeh.antennapod.core.dialog.ConfirmationDialog conDialog;
    conDialog = new de.danoeh.antennapod.core.dialog.ConfirmationDialog(getActivity(), de.danoeh.antennapod.R.string.clear_queue_label, de.danoeh.antennapod.R.string.clear_queue_confirmation_msg) {
        @java.lang.Override
        public void onConfirmButtonPressed(android.content.DialogInterface dialog) {
            dialog.dismiss();
            de.danoeh.antennapod.core.storage.DBWriter.clearQueue();
        }

    };
    conDialog.createNewDialog().show();
    return true;
} else if (itemId == de.danoeh.antennapod.R.id.keep_sorted) {
    boolean keepSortedOld;
    keepSortedOld = de.danoeh.antennapod.storage.preferences.UserPreferences.isQueueKeepSorted();
    boolean keepSortedNew;
    keepSortedNew = !keepSortedOld;
    de.danoeh.antennapod.storage.preferences.UserPreferences.setQueueKeepSorted(keepSortedNew);
    if (keepSortedNew) {
        de.danoeh.antennapod.model.feed.SortOrder sortOrder;
        sortOrder = de.danoeh.antennapod.storage.preferences.UserPreferences.getQueueKeepSortedOrder();
        de.danoeh.antennapod.core.storage.DBWriter.reorderQueue(sortOrder, true);
    }
    if (recyclerAdapter != null) {
        recyclerAdapter.updateDragDropEnabled();
    }
    refreshToolbarState();
    return true;
} else if (itemId == de.danoeh.antennapod.R.id.action_search) {
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


private void toggleQueueLock() {
boolean isLocked;
isLocked = de.danoeh.antennapod.storage.preferences.UserPreferences.isQueueLocked();
if (isLocked) {
    setQueueLocked(false);
} else {
    boolean shouldShowLockWarning;
    shouldShowLockWarning = prefs.getBoolean(de.danoeh.antennapod.fragment.QueueFragment.PREF_SHOW_LOCK_WARNING, true);
    if (!shouldShowLockWarning) {
        setQueueLocked(true);
    } else {
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
        builder.setTitle(de.danoeh.antennapod.R.string.lock_queue);
        builder.setMessage(de.danoeh.antennapod.R.string.queue_lock_warning);
        android.view.View view;
        view = android.view.View.inflate(getContext(), de.danoeh.antennapod.R.layout.checkbox_do_not_show_again, null);
        android.widget.CheckBox checkDoNotShowAgain;
        switch(MUID_STATIC) {
            // QueueFragment_2_InvalidViewFocusOperatorMutator
            case 2122: {
                /**
                * Inserted by Kadabra
                */
                checkDoNotShowAgain = view.findViewById(de.danoeh.antennapod.R.id.checkbox_do_not_show_again);
                checkDoNotShowAgain.requestFocus();
                break;
            }
            // QueueFragment_3_ViewComponentNotVisibleOperatorMutator
            case 3122: {
                /**
                * Inserted by Kadabra
                */
                checkDoNotShowAgain = view.findViewById(de.danoeh.antennapod.R.id.checkbox_do_not_show_again);
                checkDoNotShowAgain.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            checkDoNotShowAgain = view.findViewById(de.danoeh.antennapod.R.id.checkbox_do_not_show_again);
            break;
        }
    }
    builder.setView(view);
    switch(MUID_STATIC) {
        // QueueFragment_4_BuggyGUIListenerOperatorMutator
        case 4122: {
            builder.setPositiveButton(de.danoeh.antennapod.R.string.lock_queue, null);
            break;
        }
        default: {
        builder.setPositiveButton(de.danoeh.antennapod.R.string.lock_queue, (android.content.DialogInterface dialog,int which) -> {
            prefs.edit().putBoolean(de.danoeh.antennapod.fragment.QueueFragment.PREF_SHOW_LOCK_WARNING, !checkDoNotShowAgain.isChecked()).apply();
            setQueueLocked(true);
        });
        break;
    }
}
builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
builder.show();
}
}
}


private void setQueueLocked(boolean locked) {
de.danoeh.antennapod.storage.preferences.UserPreferences.setQueueLocked(locked);
refreshToolbarState();
if (recyclerAdapter != null) {
recyclerAdapter.updateDragDropEnabled();
}
if (queue.size() == 0) {
if (locked) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.queue_locked, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
} else {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.queue_unlocked, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
}
}
}


/**
 * This method is called if the user clicks on a sort order menu item.
 *
 * @param sortOrder
 * 		New sort order.
 */
private void setSortOrder(de.danoeh.antennapod.model.feed.SortOrder sortOrder) {
de.danoeh.antennapod.storage.preferences.UserPreferences.setQueueKeepSortedOrder(sortOrder);
de.danoeh.antennapod.core.storage.DBWriter.reorderQueue(sortOrder, true);
}


@java.lang.Override
public boolean onContextItemSelected(android.view.MenuItem item) {
android.util.Log.d(de.danoeh.antennapod.fragment.QueueFragment.TAG, (("onContextItemSelected() called with: " + "item = [") + item) + "]");
if ((!isVisible()) || (recyclerAdapter == null)) {
return false;
}
de.danoeh.antennapod.model.feed.FeedItem selectedItem;
selectedItem = recyclerAdapter.getLongPressedItem();
if (selectedItem == null) {
android.util.Log.i(de.danoeh.antennapod.fragment.QueueFragment.TAG, "Selected item was null, ignoring selection");
return super.onContextItemSelected(item);
}
int position;
position = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithId(queue, selectedItem.getId());
if (position < 0) {
android.util.Log.i(de.danoeh.antennapod.fragment.QueueFragment.TAG, "Selected item no longer exist, ignoring selection");
return super.onContextItemSelected(item);
}
if (recyclerAdapter.onContextItemSelected(item)) {
return true;
}
final int itemId;
itemId = item.getItemId();
if (itemId == de.danoeh.antennapod.R.id.move_to_top_item) {
queue.add(0, queue.remove(position));
recyclerAdapter.notifyItemMoved(position, 0);
de.danoeh.antennapod.core.storage.DBWriter.moveQueueItemToTop(selectedItem.getId(), true);
return true;
} else if (itemId == de.danoeh.antennapod.R.id.move_to_bottom_item) {
switch(MUID_STATIC) {
// QueueFragment_5_BinaryMutator
case 5122: {
    queue.add(queue.size() + 1, queue.remove(position));
    break;
}
default: {
queue.add(queue.size() - 1, queue.remove(position));
break;
}
}
switch(MUID_STATIC) {
// QueueFragment_6_BinaryMutator
case 6122: {
recyclerAdapter.notifyItemMoved(position, queue.size() + 1);
break;
}
default: {
recyclerAdapter.notifyItemMoved(position, queue.size() - 1);
break;
}
}
de.danoeh.antennapod.core.storage.DBWriter.moveQueueItemToBottom(selectedItem.getId(), true);
return true;
}
return de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onMenuItemClicked(this, item.getItemId(), selectedItem);
}


@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
super.onCreateView(inflater, container, savedInstanceState);
android.view.View root;
root = inflater.inflate(de.danoeh.antennapod.R.layout.queue_fragment, container, false);
switch(MUID_STATIC) {
// QueueFragment_7_InvalidViewFocusOperatorMutator
case 7122: {
/**
* Inserted by Kadabra
*/
toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
toolbar.requestFocus();
break;
}
// QueueFragment_8_ViewComponentNotVisibleOperatorMutator
case 8122: {
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
recyclerView.scrollToPosition(5);
recyclerView.post(() -> recyclerView.smoothScrollToPosition(0));
return false;
});
displayUpArrow = getParentFragmentManager().getBackStackEntryCount() != 0;
if (savedInstanceState != null) {
displayUpArrow = savedInstanceState.getBoolean(de.danoeh.antennapod.fragment.QueueFragment.KEY_UP_ARROW);
}
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).setupToolbarToggle(toolbar, displayUpArrow);
toolbar.inflateMenu(de.danoeh.antennapod.R.menu.queue);
android.view.MenuItem queueItem;
queueItem = toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.queue_sort);
android.view.MenuInflater menuInflater;
menuInflater = getActivity().getMenuInflater();
menuInflater.inflate(de.danoeh.antennapod.R.menu.sort_menu, queueItem.getSubMenu());
refreshToolbarState();
switch(MUID_STATIC) {
// QueueFragment_9_InvalidViewFocusOperatorMutator
case 9122: {
/**
* Inserted by Kadabra
*/
progressBar = root.findViewById(de.danoeh.antennapod.R.id.progressBar);
progressBar.requestFocus();
break;
}
// QueueFragment_10_ViewComponentNotVisibleOperatorMutator
case 10122: {
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
switch(MUID_STATIC) {
// QueueFragment_11_InvalidViewFocusOperatorMutator
case 11122: {
/**
* Inserted by Kadabra
*/
infoBar = root.findViewById(de.danoeh.antennapod.R.id.info_bar);
infoBar.requestFocus();
break;
}
// QueueFragment_12_ViewComponentNotVisibleOperatorMutator
case 12122: {
/**
* Inserted by Kadabra
*/
infoBar = root.findViewById(de.danoeh.antennapod.R.id.info_bar);
infoBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
infoBar = root.findViewById(de.danoeh.antennapod.R.id.info_bar);
break;
}
}
switch(MUID_STATIC) {
// QueueFragment_13_InvalidViewFocusOperatorMutator
case 13122: {
/**
* Inserted by Kadabra
*/
recyclerView = root.findViewById(de.danoeh.antennapod.R.id.recyclerView);
recyclerView.requestFocus();
break;
}
// QueueFragment_14_ViewComponentNotVisibleOperatorMutator
case 14122: {
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
androidx.recyclerview.widget.RecyclerView.ItemAnimator animator;
animator = recyclerView.getItemAnimator();
if (animator instanceof androidx.recyclerview.widget.SimpleItemAnimator) {
((androidx.recyclerview.widget.SimpleItemAnimator) (animator)).setSupportsChangeAnimations(false);
}
recyclerView.setRecycledViewPool(((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getRecycledViewPool());
registerForContextMenu(recyclerView);
recyclerView.addOnScrollListener(new de.danoeh.antennapod.view.LiftOnScrollListener(root.findViewById(de.danoeh.antennapod.R.id.appbar)));
swipeActions = new de.danoeh.antennapod.fragment.QueueFragment.QueueSwipeActions();
swipeActions.setFilter(new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.QUEUED));
swipeActions.attachTo(recyclerView);
recyclerAdapter = new de.danoeh.antennapod.adapter.QueueRecyclerAdapter(((de.danoeh.antennapod.activity.MainActivity) (getActivity())), swipeActions) {
@java.lang.Override
public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
super.onCreateContextMenu(menu, v, menuInfo);
de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, de.danoeh.antennapod.fragment.QueueFragment.this::onContextItemSelected);
}

};
recyclerAdapter.setOnSelectModeListener(this);
recyclerView.setAdapter(recyclerAdapter);
androidx.swiperefreshlayout.widget.SwipeRefreshLayout swipeRefreshLayout;
switch(MUID_STATIC) {
// QueueFragment_15_InvalidViewFocusOperatorMutator
case 15122: {
/**
* Inserted by Kadabra
*/
swipeRefreshLayout = root.findViewById(de.danoeh.antennapod.R.id.swipeRefresh);
swipeRefreshLayout.requestFocus();
break;
}
// QueueFragment_16_ViewComponentNotVisibleOperatorMutator
case 16122: {
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
emptyView = new de.danoeh.antennapod.view.EmptyViewHandler(getContext());
emptyView.attachToRecyclerView(recyclerView);
emptyView.setIcon(de.danoeh.antennapod.R.drawable.ic_playlist_play);
emptyView.setTitle(de.danoeh.antennapod.R.string.no_items_header_label);
emptyView.setMessage(de.danoeh.antennapod.R.string.no_items_label);
emptyView.updateAdapter(recyclerAdapter);
switch(MUID_STATIC) {
// QueueFragment_17_InvalidViewFocusOperatorMutator
case 17122: {
/**
* Inserted by Kadabra
*/
speedDialView = root.findViewById(de.danoeh.antennapod.R.id.fabSD);
speedDialView.requestFocus();
break;
}
// QueueFragment_18_ViewComponentNotVisibleOperatorMutator
case 18122: {
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
speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.mark_read_batch);
speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.mark_unread_batch);
speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.add_to_queue_batch);
speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.remove_all_inbox_item);
speedDialView.setOnChangeListener(new com.leinardi.android.speeddial.SpeedDialView.OnChangeListener() {
@java.lang.Override
public boolean onMainActionSelected() {
return false;
}


@java.lang.Override
public void onToggleChanged(boolean open) {
if (open && (recyclerAdapter.getSelectedCount() == 0)) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.no_items_selected, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
speedDialView.close();
}
}

});
speedDialView.setOnActionSelectedListener((com.leinardi.android.speeddial.SpeedDialActionItem actionItem) -> {
new de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler(((de.danoeh.antennapod.activity.MainActivity) (getActivity())), actionItem.getId()).handleAction(recyclerAdapter.getSelectedItems());
recyclerAdapter.endSelectMode();
return true;
});
return root;
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
outState.putBoolean(de.danoeh.antennapod.fragment.QueueFragment.KEY_UP_ARROW, displayUpArrow);
super.onSaveInstanceState(outState);
}


private void refreshInfoBar() {
java.lang.String info;
info = java.lang.String.format(java.util.Locale.getDefault(), "%d%s", queue.size(), getString(de.danoeh.antennapod.R.string.episodes_suffix));
if (queue.size() > 0) {
long timeLeft;
timeLeft = 0;
for (de.danoeh.antennapod.model.feed.FeedItem item : queue) {
float playbackSpeed;
playbackSpeed = 1;
if (de.danoeh.antennapod.storage.preferences.UserPreferences.timeRespectsSpeed()) {
playbackSpeed = de.danoeh.antennapod.core.feed.util.PlaybackSpeedUtils.getCurrentPlaybackSpeed(item.getMedia());
}
if (item.getMedia() != null) {
long itemTimeLeft;
switch(MUID_STATIC) {
// QueueFragment_19_BinaryMutator
case 19122: {
itemTimeLeft = item.getMedia().getDuration() + item.getMedia().getPosition();
break;
}
default: {
itemTimeLeft = item.getMedia().getDuration() - item.getMedia().getPosition();
break;
}
}
switch(MUID_STATIC) {
// QueueFragment_20_BinaryMutator
case 20122: {
timeLeft += itemTimeLeft * playbackSpeed;
break;
}
default: {
timeLeft += itemTimeLeft / playbackSpeed;
break;
}
}
}
}
info += " • ";
info += getString(de.danoeh.antennapod.R.string.time_left_label);
info += de.danoeh.antennapod.core.util.Converter.getDurationStringLocalized(getActivity(), timeLeft);
}
infoBar.setText(info);
}


private void loadItems(final boolean restoreScrollPosition) {
android.util.Log.d(de.danoeh.antennapod.fragment.QueueFragment.TAG, "loadItems()");
if (disposable != null) {
disposable.dispose();
}
if (queue == null) {
emptyView.hide();
}
disposable = io.reactivex.Observable.fromCallable(de.danoeh.antennapod.core.storage.DBReader::getQueue).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) -> {
queue = items;
progressBar.setVisibility(android.view.View.GONE);
recyclerAdapter.setDummyViews(0);
recyclerAdapter.updateItems(queue);
if (restoreScrollPosition) {
recyclerView.restoreScrollPosition(de.danoeh.antennapod.fragment.QueueFragment.TAG);
}
refreshInfoBar();
}, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.QueueFragment.TAG, android.util.Log.getStackTraceString(error)));
}


@java.lang.Override
public void onStartSelectMode() {
swipeActions.detach();
speedDialView.setVisibility(android.view.View.VISIBLE);
refreshToolbarState();
infoBar.setVisibility(android.view.View.GONE);
}


@java.lang.Override
public void onEndSelectMode() {
speedDialView.close();
speedDialView.setVisibility(android.view.View.GONE);
infoBar.setVisibility(android.view.View.VISIBLE);
swipeActions.attachTo(recyclerView);
}


private class QueueSwipeActions extends de.danoeh.antennapod.fragment.swipeactions.SwipeActions {
// Position tracking whilst dragging
int dragFrom = -1;

int dragTo = -1;

public QueueSwipeActions() {
super(androidx.recyclerview.widget.ItemTouchHelper.UP | androidx.recyclerview.widget.ItemTouchHelper.DOWN, de.danoeh.antennapod.fragment.QueueFragment.this, de.danoeh.antennapod.fragment.QueueFragment.TAG);
}


@java.lang.Override
public boolean onMove(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder target) {
int fromPosition;
fromPosition = viewHolder.getBindingAdapterPosition();
int toPosition;
toPosition = target.getBindingAdapterPosition();
// Update tracked position
if (dragFrom == (-1)) {
dragFrom = fromPosition;
}
dragTo = toPosition;
int from;
from = viewHolder.getBindingAdapterPosition();
int to;
to = target.getBindingAdapterPosition();
android.util.Log.d(de.danoeh.antennapod.fragment.QueueFragment.TAG, ((("move(" + from) + ", ") + to) + ") in memory");
if (((((queue == null) || (from >= queue.size())) || (to >= queue.size())) || (from < 0)) || (to < 0)) {
return false;
}
queue.add(to, queue.remove(from));
recyclerAdapter.notifyItemMoved(from, to);
return true;
}


@java.lang.Override
public void onSwiped(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int direction) {
if (disposable != null) {
disposable.dispose();
}
// SwipeActions
super.onSwiped(viewHolder, direction);
}


@java.lang.Override
public boolean isLongPressDragEnabled() {
return false;
}


@java.lang.Override
public void clearView(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
super.clearView(recyclerView, viewHolder);
// Check if drag finished
if (((dragFrom != (-1)) && (dragTo != (-1))) && (dragFrom != dragTo)) {
reallyMoved(dragFrom, dragTo);
}
dragFrom = dragTo = -1;
}


private void reallyMoved(int from, int to) {
// Write drag operation to database
android.util.Log.d(de.danoeh.antennapod.fragment.QueueFragment.TAG, ((("Write to database move(" + from) + ", ") + to) + ")");
de.danoeh.antennapod.core.storage.DBWriter.moveQueueItem(from, to, true);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
