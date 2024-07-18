package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.event.EpisodeDownloadEvent;
import org.greenrobot.eventbus.Subscribe;
import de.danoeh.antennapod.net.discovery.CombinedSearcher;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.appbar.MaterialToolbar;
import de.danoeh.antennapod.event.FeedItemEvent;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import java.util.List;
import de.danoeh.antennapod.model.feed.Feed;
import de.danoeh.antennapod.view.EpisodeItemListRecyclerView;
import de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder;
import java.util.Collections;
import android.util.Log;
import android.os.Handler;
import de.danoeh.antennapod.view.LiftOnScrollListener;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import de.danoeh.antennapod.adapter.EpisodeItemListAdapter;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.activity.OnlineFeedViewActivity;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.view.EmptyViewHandler;
import de.danoeh.antennapod.event.PlayerStatusEvent;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import android.view.ContextMenu;
import android.view.inputmethod.InputMethodManager;
import de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler;
import com.google.android.material.chip.Chip;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import android.widget.ProgressBar;
import android.util.Pair;
import de.danoeh.antennapod.event.FeedListUpdateEvent;
import de.danoeh.antennapod.core.storage.FeedSearcher;
import io.reactivex.android.schedulers.AndroidSchedulers;
import de.danoeh.antennapod.menuhandler.FeedMenuHandler;
import io.reactivex.disposables.Disposable;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import android.content.Intent;
import android.view.MenuItem;
import de.danoeh.antennapod.databinding.MultiSelectSpeedDialBinding;
import android.view.View;
import de.danoeh.antennapod.adapter.HorizontalFeedListAdapter;
import android.os.Looper;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import com.leinardi.android.speeddial.SpeedDialView;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Performs a search operation on all feeds or one specific feed and displays the search result.
 */
public class SearchFragment extends androidx.fragment.app.Fragment implements de.danoeh.antennapod.adapter.SelectableAdapter.OnSelectModeListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "SearchFragment";

    private static final java.lang.String ARG_QUERY = "query";

    private static final java.lang.String ARG_FEED = "feed";

    private static final java.lang.String ARG_FEED_NAME = "feedName";

    private static final int SEARCH_DEBOUNCE_INTERVAL = 1500;

    private de.danoeh.antennapod.adapter.EpisodeItemListAdapter adapter;

    private de.danoeh.antennapod.adapter.HorizontalFeedListAdapter adapterFeeds;

    private io.reactivex.disposables.Disposable disposable;

    private android.widget.ProgressBar progressBar;

    private de.danoeh.antennapod.view.EmptyViewHandler emptyViewHandler;

    private de.danoeh.antennapod.view.EpisodeItemListRecyclerView recyclerView;

    private java.util.List<de.danoeh.antennapod.model.feed.FeedItem> results;

    private com.google.android.material.chip.Chip chip;

    private androidx.appcompat.widget.SearchView searchView;

    private android.os.Handler automaticSearchDebouncer;

    private long lastQueryChange = 0;

    private de.danoeh.antennapod.databinding.MultiSelectSpeedDialBinding speedDialBinding;

    private boolean isOtherViewInFoucus = false;

    /**
     * Create a new SearchFragment that searches all feeds.
     */
    public static de.danoeh.antennapod.fragment.SearchFragment newInstance() {
        de.danoeh.antennapod.fragment.SearchFragment fragment;
        fragment = new de.danoeh.antennapod.fragment.SearchFragment();
        android.os.Bundle args;
        args = new android.os.Bundle();
        args.putLong(de.danoeh.antennapod.fragment.SearchFragment.ARG_FEED, 0);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Create a new SearchFragment that searches all feeds with pre-defined query.
     */
    public static de.danoeh.antennapod.fragment.SearchFragment newInstance(java.lang.String query) {
        de.danoeh.antennapod.fragment.SearchFragment fragment;
        fragment = de.danoeh.antennapod.fragment.SearchFragment.newInstance();
        fragment.getArguments().putString(de.danoeh.antennapod.fragment.SearchFragment.ARG_QUERY, query);
        return fragment;
    }


    /**
     * Create a new SearchFragment that searches one specific feed.
     */
    public static de.danoeh.antennapod.fragment.SearchFragment newInstance(long feed, java.lang.String feedTitle) {
        de.danoeh.antennapod.fragment.SearchFragment fragment;
        fragment = de.danoeh.antennapod.fragment.SearchFragment.newInstance();
        fragment.getArguments().putLong(de.danoeh.antennapod.fragment.SearchFragment.ARG_FEED, feed);
        fragment.getArguments().putString(de.danoeh.antennapod.fragment.SearchFragment.ARG_FEED_NAME, feedTitle);
        return fragment;
    }


    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SearchFragment_0_LengthyGUICreationOperatorMutator
            case 117: {
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
    automaticSearchDebouncer = new android.os.Handler(android.os.Looper.getMainLooper());
}


@java.lang.Override
public void onStop() {
    super.onStop();
    if (disposable != null) {
        disposable.dispose();
    }
}


@androidx.annotation.Nullable
@java.lang.Override
public android.view.View onCreateView(@androidx.annotation.NonNull
android.view.LayoutInflater inflater, @androidx.annotation.Nullable
android.view.ViewGroup container, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    android.view.View layout;
    layout = inflater.inflate(de.danoeh.antennapod.R.layout.search_fragment, container, false);
    setupToolbar(layout.findViewById(de.danoeh.antennapod.R.id.toolbar));
    speedDialBinding = de.danoeh.antennapod.databinding.MultiSelectSpeedDialBinding.bind(layout);
    switch(MUID_STATIC) {
        // SearchFragment_1_InvalidViewFocusOperatorMutator
        case 1117: {
            /**
            * Inserted by Kadabra
            */
            progressBar = layout.findViewById(de.danoeh.antennapod.R.id.progressBar);
            progressBar.requestFocus();
            break;
        }
        // SearchFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2117: {
            /**
            * Inserted by Kadabra
            */
            progressBar = layout.findViewById(de.danoeh.antennapod.R.id.progressBar);
            progressBar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        progressBar = layout.findViewById(de.danoeh.antennapod.R.id.progressBar);
        break;
    }
}
switch(MUID_STATIC) {
    // SearchFragment_3_InvalidViewFocusOperatorMutator
    case 3117: {
        /**
        * Inserted by Kadabra
        */
        recyclerView = layout.findViewById(de.danoeh.antennapod.R.id.recyclerView);
        recyclerView.requestFocus();
        break;
    }
    // SearchFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4117: {
        /**
        * Inserted by Kadabra
        */
        recyclerView = layout.findViewById(de.danoeh.antennapod.R.id.recyclerView);
        recyclerView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    recyclerView = layout.findViewById(de.danoeh.antennapod.R.id.recyclerView);
    break;
}
}
recyclerView.setRecycledViewPool(((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getRecycledViewPool());
registerForContextMenu(recyclerView);
adapter = new de.danoeh.antennapod.adapter.EpisodeItemListAdapter(((de.danoeh.antennapod.activity.MainActivity) (getActivity()))) {
@java.lang.Override
public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    if (!inActionMode()) {
        menu.findItem(de.danoeh.antennapod.R.id.multi_select).setVisible(true);
    }
    de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, de.danoeh.antennapod.fragment.SearchFragment.this::onContextItemSelected);
}

};
adapter.setOnSelectModeListener(this);
recyclerView.setAdapter(adapter);
recyclerView.addOnScrollListener(new de.danoeh.antennapod.view.LiftOnScrollListener(layout.findViewById(de.danoeh.antennapod.R.id.appbar)));
androidx.recyclerview.widget.RecyclerView recyclerViewFeeds;
switch(MUID_STATIC) {
// SearchFragment_5_InvalidViewFocusOperatorMutator
case 5117: {
    /**
    * Inserted by Kadabra
    */
    recyclerViewFeeds = layout.findViewById(de.danoeh.antennapod.R.id.recyclerViewFeeds);
    recyclerViewFeeds.requestFocus();
    break;
}
// SearchFragment_6_ViewComponentNotVisibleOperatorMutator
case 6117: {
    /**
    * Inserted by Kadabra
    */
    recyclerViewFeeds = layout.findViewById(de.danoeh.antennapod.R.id.recyclerViewFeeds);
    recyclerViewFeeds.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
recyclerViewFeeds = layout.findViewById(de.danoeh.antennapod.R.id.recyclerViewFeeds);
break;
}
}
androidx.recyclerview.widget.LinearLayoutManager layoutManagerFeeds;
layoutManagerFeeds = new androidx.recyclerview.widget.LinearLayoutManager(getActivity());
layoutManagerFeeds.setOrientation(androidx.recyclerview.widget.RecyclerView.HORIZONTAL);
recyclerViewFeeds.setLayoutManager(layoutManagerFeeds);
adapterFeeds = new de.danoeh.antennapod.adapter.HorizontalFeedListAdapter(((de.danoeh.antennapod.activity.MainActivity) (getActivity()))) {
@java.lang.Override
public void onCreateContextMenu(android.view.ContextMenu contextMenu, android.view.View view, android.view.ContextMenu.ContextMenuInfo contextMenuInfo) {
super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(contextMenu, de.danoeh.antennapod.fragment.SearchFragment.this::onContextItemSelected);
}

};
recyclerViewFeeds.setAdapter(adapterFeeds);
emptyViewHandler = new de.danoeh.antennapod.view.EmptyViewHandler(getContext());
emptyViewHandler.attachToRecyclerView(recyclerView);
emptyViewHandler.setIcon(de.danoeh.antennapod.R.drawable.ic_search);
emptyViewHandler.setTitle(de.danoeh.antennapod.R.string.search_status_no_results);
emptyViewHandler.setMessage(de.danoeh.antennapod.R.string.type_to_search);
org.greenrobot.eventbus.EventBus.getDefault().register(this);
switch(MUID_STATIC) {
// SearchFragment_7_InvalidViewFocusOperatorMutator
case 7117: {
/**
* Inserted by Kadabra
*/
chip = layout.findViewById(de.danoeh.antennapod.R.id.feed_title_chip);
chip.requestFocus();
break;
}
// SearchFragment_8_ViewComponentNotVisibleOperatorMutator
case 8117: {
/**
* Inserted by Kadabra
*/
chip = layout.findViewById(de.danoeh.antennapod.R.id.feed_title_chip);
chip.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
chip = layout.findViewById(de.danoeh.antennapod.R.id.feed_title_chip);
break;
}
}
switch(MUID_STATIC) {
// SearchFragment_9_BuggyGUIListenerOperatorMutator
case 9117: {
chip.setOnCloseIconClickListener(null);
break;
}
default: {
chip.setOnCloseIconClickListener((android.view.View v) -> {
getArguments().putLong(de.danoeh.antennapod.fragment.SearchFragment.ARG_FEED, 0);
searchWithProgressBar();
});
break;
}
}
chip.setVisibility(getArguments().getLong(de.danoeh.antennapod.fragment.SearchFragment.ARG_FEED, 0) == 0 ? android.view.View.GONE : android.view.View.VISIBLE);
chip.setText(getArguments().getString(de.danoeh.antennapod.fragment.SearchFragment.ARG_FEED_NAME, ""));
if (getArguments().getString(de.danoeh.antennapod.fragment.SearchFragment.ARG_QUERY, null) != null) {
search();
}
searchView.setOnQueryTextFocusChangeListener((android.view.View view,boolean hasFocus) -> {
if (hasFocus && (!isOtherViewInFoucus)) {
showInputMethod(view.findFocus());
}
});
recyclerView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
@java.lang.Override
public void onScrollStateChanged(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, int newState) {
super.onScrollStateChanged(recyclerView, newState);
if (newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING) {
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (getActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
}
}

});
speedDialBinding.fabSD.setOverlayLayout(speedDialBinding.fabSDOverlay);
speedDialBinding.fabSD.inflate(de.danoeh.antennapod.R.menu.episodes_apply_action_speeddial);
speedDialBinding.fabSD.setOnChangeListener(new com.leinardi.android.speeddial.SpeedDialView.OnChangeListener() {
@java.lang.Override
public boolean onMainActionSelected() {
return false;
}


@java.lang.Override
public void onToggleChanged(boolean open) {
if (open && (adapter.getSelectedCount() == 0)) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.no_items_selected, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
speedDialBinding.fabSD.close();
}
}

});
speedDialBinding.fabSD.setOnActionSelectedListener((com.leinardi.android.speeddial.SpeedDialActionItem actionItem) -> {
new de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler(((de.danoeh.antennapod.activity.MainActivity) (getActivity())), actionItem.getId()).handleAction(adapter.getSelectedItems());
adapter.endSelectMode();
return true;
});
return layout;
}


@java.lang.Override
public void onDestroyView() {
super.onDestroyView();
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
}


private void setupToolbar(com.google.android.material.appbar.MaterialToolbar toolbar) {
toolbar.setTitle(de.danoeh.antennapod.R.string.search_label);
switch(MUID_STATIC) {
// SearchFragment_10_BuggyGUIListenerOperatorMutator
case 10117: {
toolbar.setNavigationOnClickListener(null);
break;
}
default: {
toolbar.setNavigationOnClickListener((android.view.View v) -> getParentFragmentManager().popBackStack());
break;
}
}
toolbar.inflateMenu(de.danoeh.antennapod.R.menu.search);
android.view.MenuItem item;
item = toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.action_search);
item.expandActionView();
searchView = ((androidx.appcompat.widget.SearchView) (item.getActionView()));
searchView.setQueryHint(getString(de.danoeh.antennapod.R.string.search_label));
searchView.setQuery(getArguments().getString(de.danoeh.antennapod.fragment.SearchFragment.ARG_QUERY), true);
searchView.requestFocus();
searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
@java.lang.Override
public boolean onQueryTextSubmit(java.lang.String s) {
searchView.clearFocus();
searchWithProgressBar();
return true;
}


@java.lang.Override
public boolean onQueryTextChange(java.lang.String s) {
automaticSearchDebouncer.removeCallbacksAndMessages(null);
switch(MUID_STATIC) {
// SearchFragment_11_BinaryMutator
case 11117: {
if ((s.isEmpty() || s.endsWith(" ")) || ((lastQueryChange != 0) && (java.lang.System.currentTimeMillis() > (lastQueryChange - de.danoeh.antennapod.fragment.SearchFragment.SEARCH_DEBOUNCE_INTERVAL)))) {
search();
} else {
automaticSearchDebouncer.postDelayed(() -> {
    search();
    lastQueryChange = 0// Don't search instantly with first symbol after some pause
    ;// Don't search instantly with first symbol after some pause

}, de.danoeh.antennapod.fragment.SearchFragment.SEARCH_DEBOUNCE_INTERVAL / 2);
}
break;
}
default: {
if ((s.isEmpty() || s.endsWith(" ")) || ((lastQueryChange != 0) && (java.lang.System.currentTimeMillis() > (lastQueryChange + de.danoeh.antennapod.fragment.SearchFragment.SEARCH_DEBOUNCE_INTERVAL)))) {
search();
} else {
switch(MUID_STATIC) {
// SearchFragment_12_BinaryMutator
case 12117: {
    automaticSearchDebouncer.postDelayed(() -> {
        search();
        lastQueryChange = 0// Don't search instantly with first symbol after some pause
        ;// Don't search instantly with first symbol after some pause

    }, de.danoeh.antennapod.fragment.SearchFragment.SEARCH_DEBOUNCE_INTERVAL * 2);
    break;
}
default: {
automaticSearchDebouncer.postDelayed(() -> {
    search();
    lastQueryChange = 0// Don't search instantly with first symbol after some pause
    ;// Don't search instantly with first symbol after some pause

}, de.danoeh.antennapod.fragment.SearchFragment.SEARCH_DEBOUNCE_INTERVAL / 2);
break;
}
}
}
break;
}
}
lastQueryChange = java.lang.System.currentTimeMillis();
return false;
}

});
item.setOnActionExpandListener(new android.view.MenuItem.OnActionExpandListener() {
@java.lang.Override
public boolean onMenuItemActionExpand(android.view.MenuItem item) {
return true;
}


@java.lang.Override
public boolean onMenuItemActionCollapse(android.view.MenuItem item) {
getParentFragmentManager().popBackStack();
return true;
}

});
}


@java.lang.Override
public boolean onContextItemSelected(@androidx.annotation.NonNull
android.view.MenuItem item) {
de.danoeh.antennapod.model.feed.Feed selectedFeedItem;
selectedFeedItem = adapterFeeds.getLongPressedItem();
if ((selectedFeedItem != null) && de.danoeh.antennapod.menuhandler.FeedMenuHandler.onMenuItemClicked(this, item.getItemId(), selectedFeedItem, () -> {
})) {
return true;
}
de.danoeh.antennapod.model.feed.FeedItem selectedItem;
selectedItem = adapter.getLongPressedItem();
if (selectedItem != null) {
if (adapter.onContextItemSelected(item)) {
return true;
}
if (de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onMenuItemClicked(this, item.getItemId(), selectedItem)) {
return true;
}
}
return super.onContextItemSelected(item);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onFeedListChanged(de.danoeh.antennapod.event.FeedListUpdateEvent event) {
search();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onUnreadItemsChanged(de.danoeh.antennapod.event.UnreadItemsUpdateEvent event) {
search();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedItemEvent event) {
android.util.Log.d(de.danoeh.antennapod.fragment.SearchFragment.TAG, (("onEventMainThread() called with: " + "event = [") + event) + "]");
if (results == null) {
return;
} else if (adapter == null) {
search();
return;
}
for (int i = 0, size = event.items.size(); i < size; i++) {
de.danoeh.antennapod.model.feed.FeedItem item;
item = event.items.get(i);
int pos;
pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithId(results, item.getId());
if (pos >= 0) {
results.remove(pos);
results.add(pos, item);
adapter.notifyItemChangedCompat(pos);
}
}
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.EpisodeDownloadEvent event) {
if (results == null) {
return;
}
for (java.lang.String downloadUrl : event.getUrls()) {
int pos;
pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithDownloadUrl(results, downloadUrl);
if (pos >= 0) {
adapter.notifyItemChangedCompat(pos);
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
search();
}


private void searchWithProgressBar() {
progressBar.setVisibility(android.view.View.VISIBLE);
emptyViewHandler.hide();
search();
}


private void search() {
if (disposable != null) {
disposable.dispose();
}
adapterFeeds.setEndButton(de.danoeh.antennapod.R.string.search_online, this::searchOnline);
chip.setVisibility(getArguments().getLong(de.danoeh.antennapod.fragment.SearchFragment.ARG_FEED, 0) == 0 ? android.view.View.GONE : android.view.View.VISIBLE);
disposable = io.reactivex.Observable.fromCallable(this::performSearch).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((android.util.Pair<java.util.List<de.danoeh.antennapod.model.feed.FeedItem>, java.util.List<de.danoeh.antennapod.model.feed.Feed>> results) -> {
progressBar.setVisibility(android.view.View.GONE);
this.results = results.first;
adapter.updateItems(results.first);
if (getArguments().getLong(de.danoeh.antennapod.fragment.SearchFragment.ARG_FEED, 0) == 0) {
adapterFeeds.updateData(results.second);
} else {
adapterFeeds.updateData(java.util.Collections.emptyList());
}
if (searchView.getQuery().toString().isEmpty()) {
emptyViewHandler.setMessage(de.danoeh.antennapod.R.string.type_to_search);
} else {
emptyViewHandler.setMessage(getString(de.danoeh.antennapod.R.string.no_results_for_query, searchView.getQuery()));
}
}, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.SearchFragment.TAG, android.util.Log.getStackTraceString(error)));
}


@androidx.annotation.NonNull
private android.util.Pair<java.util.List<de.danoeh.antennapod.model.feed.FeedItem>, java.util.List<de.danoeh.antennapod.model.feed.Feed>> performSearch() {
java.lang.String query;
query = searchView.getQuery().toString();
if (query.isEmpty()) {
return new android.util.Pair<>(java.util.Collections.emptyList(), java.util.Collections.emptyList());
}
long feed;
feed = getArguments().getLong(de.danoeh.antennapod.fragment.SearchFragment.ARG_FEED);
java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items;
items = de.danoeh.antennapod.core.storage.FeedSearcher.searchFeedItems(query, feed);
java.util.List<de.danoeh.antennapod.model.feed.Feed> feeds;
feeds = de.danoeh.antennapod.core.storage.FeedSearcher.searchFeeds(query);
return new android.util.Pair<>(items, feeds);
}


private void showInputMethod(android.view.View view) {
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (getActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
if (imm != null) {
imm.showSoftInput(view, 0);
}
}


private void searchOnline() {
searchView.clearFocus();
android.view.inputmethod.InputMethodManager in;
in = ((android.view.inputmethod.InputMethodManager) (getActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
in.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
java.lang.String query;
query = searchView.getQuery().toString();
if (query.matches("http[s]?://.*")) {
android.content.Intent intent;
switch(MUID_STATIC) {
// SearchFragment_13_NullIntentOperatorMutator
case 13117: {
intent = null;
break;
}
// SearchFragment_14_InvalidKeyIntentOperatorMutator
case 14117: {
intent = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, de.danoeh.antennapod.activity.OnlineFeedViewActivity.class);
break;
}
// SearchFragment_15_RandomActionIntentDefinitionOperatorMutator
case 15117: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(getActivity(), de.danoeh.antennapod.activity.OnlineFeedViewActivity.class);
break;
}
}
switch(MUID_STATIC) {
// SearchFragment_16_NullValueIntentPutExtraOperatorMutator
case 16117: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, new Parcelable[0]);
break;
}
// SearchFragment_17_IntentPayloadReplacementOperatorMutator
case 17117: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, "");
break;
}
default: {
switch(MUID_STATIC) {
// SearchFragment_18_RandomActionIntentDefinitionOperatorMutator
case 18117: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, query);
break;
}
}
break;
}
}
startActivity(intent);
return;
}
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(de.danoeh.antennapod.fragment.OnlineSearchFragment.newInstance(de.danoeh.antennapod.net.discovery.CombinedSearcher.class, query));
}


@java.lang.Override
public void onStartSelectMode() {
searchViewFocusOff();
speedDialBinding.fabSD.removeActionItemById(de.danoeh.antennapod.R.id.remove_from_inbox_batch);
speedDialBinding.fabSD.removeActionItemById(de.danoeh.antennapod.R.id.remove_from_queue_batch);
speedDialBinding.fabSD.removeActionItemById(de.danoeh.antennapod.R.id.delete_batch);
speedDialBinding.fabSD.setVisibility(android.view.View.VISIBLE);
}


@java.lang.Override
public void onEndSelectMode() {
speedDialBinding.fabSD.close();
speedDialBinding.fabSD.setVisibility(android.view.View.GONE);
searchViewFocusOn();
}


private void searchViewFocusOff() {
isOtherViewInFoucus = true;
searchView.clearFocus();
}


private void searchViewFocusOn() {
isOtherViewInFoucus = false;
searchView.requestFocus();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
