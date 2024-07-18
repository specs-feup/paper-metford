package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.event.EpisodeDownloadEvent;
import io.reactivex.Completable;
import java.util.ArrayList;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import org.greenrobot.eventbus.Subscribe;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.core.dialog.ConfirmationDialog;
import com.google.android.material.appbar.MaterialToolbar;
import de.danoeh.antennapod.event.FeedItemEvent;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import androidx.appcompat.widget.Toolbar;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import android.widget.TextView;
import java.util.List;
import de.danoeh.antennapod.view.EpisodeItemListRecyclerView;
import de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder;
import java.util.Collections;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.util.Log;
import android.os.Handler;
import de.danoeh.antennapod.view.LiftOnScrollListener;
import android.view.LayoutInflater;
import de.danoeh.antennapod.event.FeedUpdateRunningEvent;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import de.danoeh.antennapod.adapter.EpisodeItemListAdapter;
import com.google.android.material.snackbar.Snackbar;
import de.danoeh.antennapod.fragment.swipeactions.SwipeActions;
import org.greenrobot.eventbus.ThreadMode;
import android.content.DialogInterface;
import de.danoeh.antennapod.view.EmptyViewHandler;
import de.danoeh.antennapod.event.PlayerStatusEvent;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import android.view.ContextMenu;
import de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import de.danoeh.antennapod.R;
import android.view.KeyEvent;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import android.widget.ProgressBar;
import androidx.core.util.Pair;
import de.danoeh.antennapod.event.FeedListUpdateEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import android.view.MenuItem;
import android.view.View;
import android.os.Looper;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import androidx.recyclerview.widget.RecyclerView;
import com.leinardi.android.speeddial.SpeedDialView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Shows unread or recently published episodes
 */
public abstract class EpisodesListFragment extends androidx.fragment.app.Fragment implements de.danoeh.antennapod.adapter.SelectableAdapter.OnSelectModeListener , androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "EpisodesListFragment";

    private static final java.lang.String KEY_UP_ARROW = "up_arrow";

    protected static final int EPISODES_PER_PAGE = 150;

    protected int page = 1;

    protected boolean isLoadingMore = false;

    protected boolean hasMoreItems = false;

    private boolean displayUpArrow;

    de.danoeh.antennapod.view.EpisodeItemListRecyclerView recyclerView;

    de.danoeh.antennapod.adapter.EpisodeItemListAdapter listAdapter;

    de.danoeh.antennapod.view.EmptyViewHandler emptyView;

    com.leinardi.android.speeddial.SpeedDialView speedDialView;

    com.google.android.material.appbar.MaterialToolbar toolbar;

    de.danoeh.antennapod.fragment.swipeactions.SwipeActions swipeActions;

    private android.widget.ProgressBar progressBar;

    @androidx.annotation.NonNull
    java.util.List<de.danoeh.antennapod.model.feed.FeedItem> episodes = new java.util.ArrayList<>();

    protected io.reactivex.disposables.Disposable disposable;

    protected android.widget.TextView txtvInformation;

    @java.lang.Override
    public void onStart() {
        super.onStart();
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
        loadItems();
    }


    @java.lang.Override
    public void onResume() {
        super.onResume();
        registerForContextMenu(recyclerView);
    }


    @java.lang.Override
    public void onPause() {
        super.onPause();
        recyclerView.saveScrollPosition(getPrefName());
        unregisterForContextMenu(recyclerView);
    }


    @java.lang.Override
    public void onStop() {
        super.onStop();
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
        if (disposable != null) {
            disposable.dispose();
        }
    }


    @java.lang.Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull
    android.view.MenuItem item) {
        if (super.onOptionsItemSelected(item)) {
            return true;
        }
        final int itemId;
        itemId = item.getItemId();
        if (itemId == de.danoeh.antennapod.R.id.refresh_item) {
            de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnceOrAsk(requireContext());
            return true;
        } else if (itemId == de.danoeh.antennapod.R.id.action_search) {
            ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(de.danoeh.antennapod.fragment.SearchFragment.newInstance());
            return true;
        }
        return false;
    }


    @java.lang.Override
    public boolean onContextItemSelected(@androidx.annotation.NonNull
    android.view.MenuItem item) {
        android.util.Log.d(de.danoeh.antennapod.fragment.EpisodesListFragment.TAG, (("onContextItemSelected() called with: " + "item = [") + item) + "]");
        if (((!getUserVisibleHint()) || (!isVisible())) || (!isMenuVisible())) {
            // The method is called on all fragments in a ViewPager, so this needs to be ignored in invisible ones.
            // Apparently, none of the visibility check method works reliably on its own, so we just use all.
            return false;
        } else if (listAdapter.getLongPressedItem() == null) {
            android.util.Log.i(de.danoeh.antennapod.fragment.EpisodesListFragment.TAG, "Selected item or listAdapter was null, ignoring selection");
            return super.onContextItemSelected(item);
        } else if (listAdapter.onContextItemSelected(item)) {
            return true;
        }
        de.danoeh.antennapod.model.feed.FeedItem selectedItem;
        selectedItem = listAdapter.getLongPressedItem();
        return de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onMenuItemClicked(this, item.getItemId(), selectedItem);
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        android.view.View root;
        root = inflater.inflate(de.danoeh.antennapod.R.layout.episodes_list_fragment, container, false);
        switch(MUID_STATIC) {
            // EpisodesListFragment_0_InvalidViewFocusOperatorMutator
            case 128: {
                /**
                * Inserted by Kadabra
                */
                txtvInformation = root.findViewById(de.danoeh.antennapod.R.id.txtvInformation);
                txtvInformation.requestFocus();
                break;
            }
            // EpisodesListFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1128: {
                /**
                * Inserted by Kadabra
                */
                txtvInformation = root.findViewById(de.danoeh.antennapod.R.id.txtvInformation);
                txtvInformation.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            txtvInformation = root.findViewById(de.danoeh.antennapod.R.id.txtvInformation);
            break;
        }
    }
    switch(MUID_STATIC) {
        // EpisodesListFragment_2_InvalidViewFocusOperatorMutator
        case 2128: {
            /**
            * Inserted by Kadabra
            */
            toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
            toolbar.requestFocus();
            break;
        }
        // EpisodesListFragment_3_ViewComponentNotVisibleOperatorMutator
        case 3128: {
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
    displayUpArrow = savedInstanceState.getBoolean(de.danoeh.antennapod.fragment.EpisodesListFragment.KEY_UP_ARROW);
}
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).setupToolbarToggle(toolbar, displayUpArrow);
switch(MUID_STATIC) {
    // EpisodesListFragment_4_InvalidViewFocusOperatorMutator
    case 4128: {
        /**
        * Inserted by Kadabra
        */
        recyclerView = root.findViewById(de.danoeh.antennapod.R.id.recyclerView);
        recyclerView.requestFocus();
        break;
    }
    // EpisodesListFragment_5_ViewComponentNotVisibleOperatorMutator
    case 5128: {
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
setupLoadMoreScrollListener();
recyclerView.addOnScrollListener(new de.danoeh.antennapod.view.LiftOnScrollListener(root.findViewById(de.danoeh.antennapod.R.id.appbar)));
swipeActions = new de.danoeh.antennapod.fragment.swipeactions.SwipeActions(this, getFragmentTag()).attachTo(recyclerView);
swipeActions.setFilter(getFilter());
androidx.recyclerview.widget.RecyclerView.ItemAnimator animator;
animator = recyclerView.getItemAnimator();
if (animator instanceof androidx.recyclerview.widget.SimpleItemAnimator) {
((androidx.recyclerview.widget.SimpleItemAnimator) (animator)).setSupportsChangeAnimations(false);
}
androidx.swiperefreshlayout.widget.SwipeRefreshLayout swipeRefreshLayout;
switch(MUID_STATIC) {
// EpisodesListFragment_6_InvalidViewFocusOperatorMutator
case 6128: {
    /**
    * Inserted by Kadabra
    */
    swipeRefreshLayout = root.findViewById(de.danoeh.antennapod.R.id.swipeRefresh);
    swipeRefreshLayout.requestFocus();
    break;
}
// EpisodesListFragment_7_ViewComponentNotVisibleOperatorMutator
case 7128: {
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
listAdapter = new de.danoeh.antennapod.adapter.EpisodeItemListAdapter(((de.danoeh.antennapod.activity.MainActivity) (getActivity()))) {
@java.lang.Override
public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
super.onCreateContextMenu(menu, v, menuInfo);
if (!inActionMode()) {
    menu.findItem(de.danoeh.antennapod.R.id.multi_select).setVisible(true);
}
de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, de.danoeh.antennapod.fragment.EpisodesListFragment.this::onContextItemSelected);
}

};
listAdapter.setOnSelectModeListener(this);
recyclerView.setAdapter(listAdapter);
switch(MUID_STATIC) {
// EpisodesListFragment_8_InvalidViewFocusOperatorMutator
case 8128: {
/**
* Inserted by Kadabra
*/
progressBar = root.findViewById(de.danoeh.antennapod.R.id.progressBar);
progressBar.requestFocus();
break;
}
// EpisodesListFragment_9_ViewComponentNotVisibleOperatorMutator
case 9128: {
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
emptyView = new de.danoeh.antennapod.view.EmptyViewHandler(getContext());
emptyView.attachToRecyclerView(recyclerView);
emptyView.setIcon(de.danoeh.antennapod.R.drawable.ic_feed);
emptyView.setTitle(de.danoeh.antennapod.R.string.no_all_episodes_head_label);
emptyView.setMessage(de.danoeh.antennapod.R.string.no_all_episodes_label);
emptyView.updateAdapter(listAdapter);
emptyView.hide();
switch(MUID_STATIC) {
// EpisodesListFragment_10_InvalidViewFocusOperatorMutator
case 10128: {
/**
* Inserted by Kadabra
*/
speedDialView = root.findViewById(de.danoeh.antennapod.R.id.fabSD);
speedDialView.requestFocus();
break;
}
// EpisodesListFragment_11_ViewComponentNotVisibleOperatorMutator
case 11128: {
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
speedDialView.setOnChangeListener(new com.leinardi.android.speeddial.SpeedDialView.OnChangeListener() {
@java.lang.Override
public boolean onMainActionSelected() {
return false;
}


@java.lang.Override
public void onToggleChanged(boolean open) {
if (open && (listAdapter.getSelectedCount() == 0)) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.no_items_selected, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
speedDialView.close();
}
}

});
speedDialView.setOnActionSelectedListener((com.leinardi.android.speeddial.SpeedDialActionItem actionItem) -> {
int confirmationString;
confirmationString = 0;
if ((listAdapter.getSelectedItems().size() >= 25) || listAdapter.shouldSelectLazyLoadedItems()) {
// Should ask for confirmation
if (actionItem.getId() == de.danoeh.antennapod.R.id.mark_read_batch) {
confirmationString = de.danoeh.antennapod.R.string.multi_select_mark_played_confirmation;
} else if (actionItem.getId() == de.danoeh.antennapod.R.id.mark_unread_batch) {
confirmationString = de.danoeh.antennapod.R.string.multi_select_mark_unplayed_confirmation;
}
}
if (confirmationString == 0) {
performMultiSelectAction(actionItem.getId());
} else {
new de.danoeh.antennapod.core.dialog.ConfirmationDialog(getActivity(), de.danoeh.antennapod.R.string.multi_select, confirmationString) {
@java.lang.Override
public void onConfirmButtonPressed(android.content.DialogInterface dialog) {
performMultiSelectAction(actionItem.getId());
}

}.createNewDialog().show();
}
return true;
});
return root;
}


private void performMultiSelectAction(int actionItemId) {
de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler handler;
handler = new de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler(((de.danoeh.antennapod.activity.MainActivity) (getActivity())), actionItemId);
io.reactivex.Completable.fromAction(() -> {
handler.handleAction(listAdapter.getSelectedItems());
if (listAdapter.shouldSelectLazyLoadedItems()) {
int applyPage;
switch(MUID_STATIC) {
// EpisodesListFragment_12_BinaryMutator
case 12128: {
applyPage = page - 1;
break;
}
default: {
applyPage = page + 1;
break;
}
}
java.util.List<de.danoeh.antennapod.model.feed.FeedItem> nextPage;
do {
nextPage = loadMoreData(applyPage);
handler.handleAction(nextPage);
applyPage++;
} while (nextPage.size() == de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE );
}
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(() -> listAdapter.endSelectMode(), (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.EpisodesListFragment.TAG, android.util.Log.getStackTraceString(error)));
}


private void setupLoadMoreScrollListener() {
recyclerView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
@java.lang.Override
public void onScrolled(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView view, int deltaX, int deltaY) {
super.onScrolled(view, deltaX, deltaY);
if (((!isLoadingMore) && hasMoreItems) && recyclerView.isScrolledToBottom()) {
/* The end of the list has been reached. Load more data. */
page++;
loadMoreItems();
isLoadingMore = true;
}
}

});
}


private void loadMoreItems() {
if (disposable != null) {
disposable.dispose();
}
isLoadingMore = true;
listAdapter.setDummyViews(1);
switch(MUID_STATIC) {
// EpisodesListFragment_13_BinaryMutator
case 13128: {
listAdapter.notifyItemInserted(listAdapter.getItemCount() + 1);
break;
}
default: {
listAdapter.notifyItemInserted(listAdapter.getItemCount() - 1);
break;
}
}
disposable = io.reactivex.Observable.fromCallable(() -> loadMoreData(page)).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.model.feed.FeedItem> data) -> {
if (data.size() < de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE) {
hasMoreItems = false;
}
episodes.addAll(data);
listAdapter.setDummyViews(0);
listAdapter.updateItems(episodes);
if (listAdapter.shouldSelectLazyLoadedItems()) {
switch(MUID_STATIC) {
// EpisodesListFragment_14_BinaryMutator
case 14128: {
listAdapter.setSelected(episodes.size() + data.size(), episodes.size(), true);
break;
}
default: {
listAdapter.setSelected(episodes.size() - data.size(), episodes.size(), true);
break;
}
}
}
}, (java.lang.Throwable error) -> {
listAdapter.setDummyViews(0);
listAdapter.updateItems(java.util.Collections.emptyList());
android.util.Log.e(de.danoeh.antennapod.fragment.EpisodesListFragment.TAG, android.util.Log.getStackTraceString(error));
}, () -> {
// Make sure to not always load 2 pages at once
recyclerView.post(() -> isLoadingMore = false);
});
}


@java.lang.Override
public void onDestroyView() {
super.onDestroyView();
listAdapter.endSelectMode();
}


@java.lang.Override
public void onStartSelectMode() {
speedDialView.setVisibility(android.view.View.VISIBLE);
}


@java.lang.Override
public void onEndSelectMode() {
speedDialView.close();
speedDialView.setVisibility(android.view.View.GONE);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedItemEvent event) {
android.util.Log.d(de.danoeh.antennapod.fragment.EpisodesListFragment.TAG, (("onEventMainThread() called with: " + "event = [") + event) + "]");
for (de.danoeh.antennapod.model.feed.FeedItem item : event.items) {
int pos;
pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithId(episodes, item.getId());
if (pos >= 0) {
episodes.remove(pos);
if (getFilter().matches(item)) {
episodes.add(pos, item);
listAdapter.notifyItemChangedCompat(pos);
} else {
listAdapter.notifyItemRemoved(pos);
}
}
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
for (int i = 0; i < listAdapter.getItemCount(); i++) {
de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder;
holder = ((de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder) (recyclerView.findViewHolderForAdapterPosition(i)));
if ((holder != null) && holder.isCurrentlyPlayingItem()) {
holder.notifyPlaybackPositionUpdated(event);
break;
}
}
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
// EpisodesListFragment_15_BinaryMutator
case 15128: {
recyclerView.smoothScrollToPosition(listAdapter.getItemCount() + 1);
break;
}
default: {
recyclerView.smoothScrollToPosition(listAdapter.getItemCount() - 1);
break;
}
}
break;
default :
break;
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
public void onPlayerStatusChanged(de.danoeh.antennapod.event.PlayerStatusEvent event) {
loadItems();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onUnreadItemsChanged(de.danoeh.antennapod.event.UnreadItemsUpdateEvent event) {
loadItems();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onFeedListChanged(de.danoeh.antennapod.event.FeedListUpdateEvent event) {
loadItems();
}


void loadItems() {
if (disposable != null) {
disposable.dispose();
}
disposable = io.reactivex.Observable.fromCallable(() -> new androidx.core.util.Pair<>(loadData(), loadTotalItemCount())).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((androidx.core.util.Pair<java.util.List<de.danoeh.antennapod.model.feed.FeedItem>, java.lang.Integer> data) -> {
final boolean restoreScrollPosition;
restoreScrollPosition = episodes.isEmpty();
episodes = data.first;
hasMoreItems = !((page == 1) && (episodes.size() < de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE));
progressBar.setVisibility(android.view.View.GONE);
listAdapter.setDummyViews(0);
listAdapter.updateItems(episodes);
listAdapter.setTotalNumberOfItems(data.second);
if (restoreScrollPosition) {
recyclerView.restoreScrollPosition(getPrefName());
}
updateToolbar();
}, (java.lang.Throwable error) -> {
listAdapter.setDummyViews(0);
listAdapter.updateItems(java.util.Collections.emptyList());
android.util.Log.e(de.danoeh.antennapod.fragment.EpisodesListFragment.TAG, android.util.Log.getStackTraceString(error));
});
}


@androidx.annotation.NonNull
protected abstract java.util.List<de.danoeh.antennapod.model.feed.FeedItem> loadData();


@androidx.annotation.NonNull
protected abstract java.util.List<de.danoeh.antennapod.model.feed.FeedItem> loadMoreData(int page);


protected abstract int loadTotalItemCount();


protected abstract de.danoeh.antennapod.model.feed.FeedItemFilter getFilter();


protected abstract java.lang.String getFragmentTag();


protected abstract java.lang.String getPrefName();


protected void updateToolbar() {
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedUpdateRunningEvent event) {
if (toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.refresh_item) != null) {
de.danoeh.antennapod.core.menuhandler.MenuItemUtils.updateRefreshMenuItem(toolbar.getMenu(), de.danoeh.antennapod.R.id.refresh_item, event.isFeedUpdateRunning);
}
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
outState.putBoolean(de.danoeh.antennapod.fragment.EpisodesListFragment.KEY_UP_ARROW, displayUpArrow);
super.onSaveInstanceState(outState);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
