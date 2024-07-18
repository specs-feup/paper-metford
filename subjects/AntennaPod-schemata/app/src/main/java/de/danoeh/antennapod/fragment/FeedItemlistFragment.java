package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.event.EpisodeDownloadEvent;
import android.content.res.Configuration;
import org.greenrobot.eventbus.Subscribe;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;
import de.danoeh.antennapod.event.FeedItemEvent;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import org.apache.commons.lang3.Validate;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.dialog.DownloadLogDetailsDialog;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.dialog.FeedItemFilterDialog;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import com.bumptech.glide.Glide;
import de.danoeh.antennapod.view.ToolbarIconTintManager;
import de.danoeh.antennapod.event.FavoritesEvent;
import de.danoeh.antennapod.model.feed.Feed;
import de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder;
import java.util.Collections;
import com.joanzapata.iconify.Iconify;
import io.reactivex.Maybe;
import android.util.Log;
import android.graphics.LightingColorFilter;
import android.os.Handler;
import android.view.LayoutInflater;
import de.danoeh.antennapod.databinding.FeedItemListFragmentBinding;
import de.danoeh.antennapod.dialog.RemoveFeedDialog;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.event.FeedUpdateRunningEvent;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import android.widget.AdapterView;
import de.danoeh.antennapod.adapter.EpisodeItemListAdapter;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.fragment.swipeactions.SwipeActions;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.event.PlayerStatusEvent;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import android.view.ContextMenu;
import de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler;
import de.danoeh.antennapod.model.download.DownloadResult;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import de.danoeh.antennapod.dialog.RenameItemDialog;
import de.danoeh.antennapod.core.util.gui.MoreContentListFooterUtil;
import de.danoeh.antennapod.ui.glide.FastBlurTransformation;
import de.danoeh.antennapod.R;
import android.view.KeyEvent;
import de.danoeh.antennapod.event.QueueEvent;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import android.widget.Toast;
import de.danoeh.antennapod.event.FeedListUpdateEvent;
import de.danoeh.antennapod.menuhandler.FeedMenuHandler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import de.danoeh.antennapod.core.util.FeedItemPermutors;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import android.view.MenuItem;
import de.danoeh.antennapod.databinding.MultiSelectSpeedDialBinding;
import android.view.View;
import androidx.appcompat.content.res.AppCompatResources;
import android.os.Looper;
import de.danoeh.antennapod.core.feed.FeedEvent;
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
 * Displays a list of FeedItems.
 */
public class FeedItemlistFragment extends androidx.fragment.app.Fragment implements android.widget.AdapterView.OnItemClickListener , androidx.appcompat.widget.Toolbar.OnMenuItemClickListener , de.danoeh.antennapod.adapter.SelectableAdapter.OnSelectModeListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "ItemlistFragment";

    private static final java.lang.String ARGUMENT_FEED_ID = "argument.de.danoeh.antennapod.feed_id";

    private static final java.lang.String KEY_UP_ARROW = "up_arrow";

    private de.danoeh.antennapod.fragment.FeedItemlistFragment.FeedItemListAdapter adapter;

    private de.danoeh.antennapod.fragment.swipeactions.SwipeActions swipeActions;

    private de.danoeh.antennapod.core.util.gui.MoreContentListFooterUtil nextPageLoader;

    private boolean displayUpArrow;

    private long feedID;

    private de.danoeh.antennapod.model.feed.Feed feed;

    private boolean headerCreated = false;

    private io.reactivex.disposables.Disposable disposable;

    private de.danoeh.antennapod.databinding.FeedItemListFragmentBinding viewBinding;

    private de.danoeh.antennapod.databinding.MultiSelectSpeedDialBinding speedDialBinding;

    /**
     * Creates new ItemlistFragment which shows the Feeditems of a specific
     * feed. Sets 'showFeedtitle' to false
     *
     * @param feedId
     * 		The id of the feed to show
     * @return the newly created instance of an ItemlistFragment
     */
    public static de.danoeh.antennapod.fragment.FeedItemlistFragment newInstance(long feedId) {
        de.danoeh.antennapod.fragment.FeedItemlistFragment i;
        i = new de.danoeh.antennapod.fragment.FeedItemlistFragment();
        android.os.Bundle b;
        b = new android.os.Bundle();
        b.putLong(de.danoeh.antennapod.fragment.FeedItemlistFragment.ARGUMENT_FEED_ID, feedId);
        i.setArguments(b);
        return i;
    }


    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // FeedItemlistFragment_0_LengthyGUICreationOperatorMutator
            case 120: {
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
    android.os.Bundle args;
    args = getArguments();
    org.apache.commons.lang3.Validate.notNull(args);
    feedID = args.getLong(de.danoeh.antennapod.fragment.FeedItemlistFragment.ARGUMENT_FEED_ID);
}


@androidx.annotation.Nullable
@java.lang.Override
public android.view.View onCreateView(@androidx.annotation.NonNull
android.view.LayoutInflater inflater, @androidx.annotation.Nullable
android.view.ViewGroup container, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    viewBinding = de.danoeh.antennapod.databinding.FeedItemListFragmentBinding.inflate(inflater);
    speedDialBinding = de.danoeh.antennapod.databinding.MultiSelectSpeedDialBinding.bind(viewBinding.getRoot());
    viewBinding.toolbar.inflateMenu(de.danoeh.antennapod.R.menu.feedlist);
    viewBinding.toolbar.setOnMenuItemClickListener(this);
    viewBinding.toolbar.setOnLongClickListener((android.view.View v) -> {
        viewBinding.recyclerView.scrollToPosition(5);
        viewBinding.recyclerView.post(() -> viewBinding.recyclerView.smoothScrollToPosition(0));
        viewBinding.appBar.setExpanded(true);
        return false;
    });
    displayUpArrow = getParentFragmentManager().getBackStackEntryCount() != 0;
    if (savedInstanceState != null) {
        displayUpArrow = savedInstanceState.getBoolean(de.danoeh.antennapod.fragment.FeedItemlistFragment.KEY_UP_ARROW);
    }
    ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).setupToolbarToggle(viewBinding.toolbar, displayUpArrow);
    updateToolbar();
    viewBinding.recyclerView.setRecycledViewPool(((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getRecycledViewPool());
    adapter = new de.danoeh.antennapod.fragment.FeedItemlistFragment.FeedItemListAdapter(((de.danoeh.antennapod.activity.MainActivity) (getActivity())));
    adapter.setOnSelectModeListener(this);
    viewBinding.recyclerView.setAdapter(adapter);
    swipeActions = new de.danoeh.antennapod.fragment.swipeactions.SwipeActions(this, de.danoeh.antennapod.fragment.FeedItemlistFragment.TAG).attachTo(viewBinding.recyclerView);
    viewBinding.progressBar.setVisibility(android.view.View.VISIBLE);
    de.danoeh.antennapod.view.ToolbarIconTintManager iconTintManager;
    iconTintManager = new de.danoeh.antennapod.view.ToolbarIconTintManager(getContext(), viewBinding.toolbar, viewBinding.collapsingToolbar) {
        @java.lang.Override
        protected void doTint(android.content.Context themedContext) {
            viewBinding.toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.refresh_item).setIcon(androidx.appcompat.content.res.AppCompatResources.getDrawable(themedContext, de.danoeh.antennapod.R.drawable.ic_refresh));
            viewBinding.toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.action_search).setIcon(androidx.appcompat.content.res.AppCompatResources.getDrawable(themedContext, de.danoeh.antennapod.R.drawable.ic_search));
        }

    };
    iconTintManager.updateTint();
    viewBinding.appBar.addOnOffsetChangedListener(iconTintManager);
    nextPageLoader = new de.danoeh.antennapod.core.util.gui.MoreContentListFooterUtil(viewBinding.moreContent.moreContentListFooter);
    nextPageLoader.setClickListener(() -> {
        if (feed != null) {
            de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnce(getContext(), feed, true);
        }
    });
    viewBinding.recyclerView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
        @java.lang.Override
        public void onScrolled(@androidx.annotation.NonNull
        androidx.recyclerview.widget.RecyclerView view, int deltaX, int deltaY) {
            super.onScrolled(view, deltaX, deltaY);
            boolean hasMorePages;
            hasMorePages = ((feed != null) && feed.isPaged()) && (feed.getNextPageLink() != null);
            boolean pageLoaderVisible;
            pageLoaderVisible = viewBinding.recyclerView.isScrolledToBottom() && hasMorePages;
            nextPageLoader.getRoot().setVisibility(pageLoaderVisible ? android.view.View.VISIBLE : android.view.View.GONE);
            viewBinding.recyclerView.setPadding(viewBinding.recyclerView.getPaddingLeft(), 0, viewBinding.recyclerView.getPaddingRight(), pageLoaderVisible ? nextPageLoader.getRoot().getMeasuredHeight() : 0);
        }

    });
    org.greenrobot.eventbus.EventBus.getDefault().register(this);
    viewBinding.swipeRefresh.setDistanceToTriggerSync(getResources().getInteger(de.danoeh.antennapod.R.integer.swipe_refresh_distance));
    viewBinding.swipeRefresh.setOnRefreshListener(() -> {
        de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnceOrAsk(requireContext(), feed);
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> viewBinding.swipeRefresh.setRefreshing(false), getResources().getInteger(de.danoeh.antennapod.R.integer.swipe_to_refresh_duration_in_ms));
    });
    loadItems();
    // Init action UI (via a FAB Speed Dial)
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
    return viewBinding.getRoot();
}


@java.lang.Override
public void onDestroyView() {
    super.onDestroyView();
    org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
    if (disposable != null) {
        disposable.dispose();
    }
    adapter.endSelectMode();
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
    outState.putBoolean(de.danoeh.antennapod.fragment.FeedItemlistFragment.KEY_UP_ARROW, displayUpArrow);
    super.onSaveInstanceState(outState);
}


private void updateToolbar() {
    if (feed == null) {
        return;
    }
    viewBinding.toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.visit_website_item).setVisible(feed.getLink() != null);
    de.danoeh.antennapod.menuhandler.FeedMenuHandler.onPrepareOptionsMenu(viewBinding.toolbar.getMenu(), feed);
}


@java.lang.Override
public void onConfigurationChanged(@androidx.annotation.NonNull
android.content.res.Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    int horizontalSpacing;
    horizontalSpacing = ((int) (getResources().getDimension(de.danoeh.antennapod.R.dimen.additional_horizontal_spacing)));
    viewBinding.header.headerContainer.setPadding(horizontalSpacing, viewBinding.header.headerContainer.getPaddingTop(), horizontalSpacing, viewBinding.header.headerContainer.getPaddingBottom());
}


@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem item) {
    if (feed == null) {
        ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.please_wait_for_data, android.widget.Toast.LENGTH_LONG);
        return true;
    }
    boolean feedMenuHandled;
    feedMenuHandled = de.danoeh.antennapod.menuhandler.FeedMenuHandler.onOptionsItemClicked(getActivity(), item, feed);
    if (feedMenuHandled) {
        return true;
    }
    final int itemId;
    itemId = item.getItemId();
    if (itemId == de.danoeh.antennapod.R.id.rename_item) {
        new de.danoeh.antennapod.dialog.RenameItemDialog(getActivity(), feed).show();
        return true;
    } else if (itemId == de.danoeh.antennapod.R.id.remove_feed) {
        de.danoeh.antennapod.dialog.RemoveFeedDialog.show(getContext(), feed, () -> {
            ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadFragment(de.danoeh.antennapod.storage.preferences.UserPreferences.getDefaultPage(), null);
            // Make sure fragment is hidden before actually starting to delete
            getActivity().getSupportFragmentManager().executePendingTransactions();
        });
        return true;
    } else if (itemId == de.danoeh.antennapod.R.id.action_search) {
        ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(de.danoeh.antennapod.fragment.SearchFragment.newInstance(feed.getId(), feed.getTitle()));
        return true;
    }
    return false;
}


@java.lang.Override
public boolean onContextItemSelected(@androidx.annotation.NonNull
android.view.MenuItem item) {
    de.danoeh.antennapod.model.feed.FeedItem selectedItem;
    selectedItem = adapter.getLongPressedItem();
    if (selectedItem == null) {
        android.util.Log.i(de.danoeh.antennapod.fragment.FeedItemlistFragment.TAG, "Selected item at current position was null, ignoring selection");
        return super.onContextItemSelected(item);
    }
    if (adapter.onContextItemSelected(item)) {
        return true;
    }
    return de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onMenuItemClicked(this, item.getItemId(), selectedItem);
}


@java.lang.Override
public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
    de.danoeh.antennapod.activity.MainActivity activity;
    activity = ((de.danoeh.antennapod.activity.MainActivity) (getActivity()));
    long[] ids;
    ids = de.danoeh.antennapod.core.util.FeedItemUtil.getIds(feed.getItems());
    activity.loadChildFragment(de.danoeh.antennapod.fragment.ItemPagerFragment.newInstance(ids, position));
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEvent(de.danoeh.antennapod.core.feed.FeedEvent event) {
    android.util.Log.d(de.danoeh.antennapod.fragment.FeedItemlistFragment.TAG, (("onEvent() called with: " + "event = [") + event) + "]");
    if (event.feedId == feedID) {
        loadItems();
    }
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedItemEvent event) {
    android.util.Log.d(de.danoeh.antennapod.fragment.FeedItemlistFragment.TAG, (("onEventMainThread() called with: " + "event = [") + event) + "]");
    if ((feed == null) || (feed.getItems() == null)) {
        return;
    }
    for (int i = 0, size = event.items.size(); i < size; i++) {
        de.danoeh.antennapod.model.feed.FeedItem item;
        item = event.items.get(i);
        int pos;
        pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithId(feed.getItems(), item.getId());
        if (pos >= 0) {
            feed.getItems().remove(pos);
            feed.getItems().add(pos, item);
            adapter.notifyItemChangedCompat(pos);
        }
    }
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.EpisodeDownloadEvent event) {
    if (feed == null) {
        return;
    }
    for (java.lang.String downloadUrl : event.getUrls()) {
        int pos;
        pos = de.danoeh.antennapod.core.util.FeedItemUtil.indexOfItemWithDownloadUrl(feed.getItems(), downloadUrl);
        if (pos >= 0) {
            adapter.notifyItemChangedCompat(pos);
        }
    }
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
    for (int i = 0; i < adapter.getItemCount(); i++) {
        de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder;
        holder = ((de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder) (viewBinding.recyclerView.findViewHolderForAdapterPosition(i)));
        if ((holder != null) && holder.isCurrentlyPlayingItem()) {
            holder.notifyPlaybackPositionUpdated(event);
            break;
        }
    }
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void favoritesChanged(de.danoeh.antennapod.event.FavoritesEvent event) {
    updateUi();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onQueueChanged(de.danoeh.antennapod.event.QueueEvent event) {
    updateUi();
}


@java.lang.Override
public void onStartSelectMode() {
    swipeActions.detach();
    if (feed.isLocalFeed()) {
        speedDialBinding.fabSD.removeActionItemById(de.danoeh.antennapod.R.id.download_batch);
    }
    speedDialBinding.fabSD.removeActionItemById(de.danoeh.antennapod.R.id.remove_all_inbox_item);
    speedDialBinding.fabSD.setVisibility(android.view.View.VISIBLE);
    updateToolbar();
}


@java.lang.Override
public void onEndSelectMode() {
    speedDialBinding.fabSD.close();
    speedDialBinding.fabSD.setVisibility(android.view.View.GONE);
    swipeActions.attachTo(viewBinding.recyclerView);
}


private void updateUi() {
    loadItems();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onPlayerStatusChanged(de.danoeh.antennapod.event.PlayerStatusEvent event) {
    updateUi();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onUnreadItemsChanged(de.danoeh.antennapod.event.UnreadItemsUpdateEvent event) {
    updateUi();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onFeedListChanged(de.danoeh.antennapod.event.FeedListUpdateEvent event) {
    if ((feed != null) && event.contains(feed)) {
        updateUi();
    }
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedUpdateRunningEvent event) {
    nextPageLoader.setLoadingState(event.isFeedUpdateRunning);
    if (!event.isFeedUpdateRunning) {
        nextPageLoader.getRoot().setVisibility(android.view.View.GONE);
    }
    de.danoeh.antennapod.core.menuhandler.MenuItemUtils.updateRefreshMenuItem(viewBinding.toolbar.getMenu(), de.danoeh.antennapod.R.id.refresh_item, event.isFeedUpdateRunning);
}


private void refreshHeaderView() {
    setupHeaderView();
    if ((viewBinding == null) || (feed == null)) {
        android.util.Log.e(de.danoeh.antennapod.fragment.FeedItemlistFragment.TAG, "Unable to refresh header view");
        return;
    }
    loadFeedImage();
    if (feed.hasLastUpdateFailed()) {
        viewBinding.header.txtvFailure.setVisibility(android.view.View.VISIBLE);
    } else {
        viewBinding.header.txtvFailure.setVisibility(android.view.View.GONE);
    }
    if (!feed.getPreferences().getKeepUpdated()) {
        viewBinding.header.txtvUpdatesDisabled.setText("{md-pause-circle-outline} " + this.getString(de.danoeh.antennapod.R.string.updates_disabled_label));
        com.joanzapata.iconify.Iconify.addIcons(viewBinding.header.txtvUpdatesDisabled);
        viewBinding.header.txtvUpdatesDisabled.setVisibility(android.view.View.VISIBLE);
    } else {
        viewBinding.header.txtvUpdatesDisabled.setVisibility(android.view.View.GONE);
    }
    viewBinding.header.txtvTitle.setText(feed.getTitle());
    viewBinding.header.txtvAuthor.setText(feed.getAuthor());
    if (feed.getItemFilter() != null) {
        de.danoeh.antennapod.model.feed.FeedItemFilter filter;
        filter = feed.getItemFilter();
        if (filter.getValues().length > 0) {
            viewBinding.header.txtvInformation.setText("{md-info-outline} " + this.getString(de.danoeh.antennapod.R.string.filtered_label));
            com.joanzapata.iconify.Iconify.addIcons(viewBinding.header.txtvInformation);
            switch(MUID_STATIC) {
                // FeedItemlistFragment_1_BuggyGUIListenerOperatorMutator
                case 1120: {
                    viewBinding.header.txtvInformation.setOnClickListener(null);
                    break;
                }
                default: {
                viewBinding.header.txtvInformation.setOnClickListener((android.view.View l) -> de.danoeh.antennapod.dialog.FeedItemFilterDialog.newInstance(feed).show(getChildFragmentManager(), null));
                break;
            }
        }
        viewBinding.header.txtvInformation.setVisibility(android.view.View.VISIBLE);
    } else {
        viewBinding.header.txtvInformation.setVisibility(android.view.View.GONE);
    }
} else {
    viewBinding.header.txtvInformation.setVisibility(android.view.View.GONE);
}
}


private void setupHeaderView() {
if ((feed == null) || headerCreated) {
    return;
}
// https://github.com/bumptech/glide/issues/529
viewBinding.imgvBackground.setColorFilter(new android.graphics.LightingColorFilter(0xff666666, 0x0));
switch(MUID_STATIC) {
    // FeedItemlistFragment_2_BuggyGUIListenerOperatorMutator
    case 2120: {
        viewBinding.header.butShowInfo.setOnClickListener(null);
        break;
    }
    default: {
    viewBinding.header.butShowInfo.setOnClickListener((android.view.View v) -> showFeedInfo());
    break;
}
}
switch(MUID_STATIC) {
// FeedItemlistFragment_3_BuggyGUIListenerOperatorMutator
case 3120: {
    viewBinding.header.imgvCover.setOnClickListener(null);
    break;
}
default: {
viewBinding.header.imgvCover.setOnClickListener((android.view.View v) -> showFeedInfo());
break;
}
}
switch(MUID_STATIC) {
// FeedItemlistFragment_4_BuggyGUIListenerOperatorMutator
case 4120: {
viewBinding.header.butShowSettings.setOnClickListener(null);
break;
}
default: {
viewBinding.header.butShowSettings.setOnClickListener((android.view.View v) -> {
if (feed != null) {
    de.danoeh.antennapod.fragment.FeedSettingsFragment fragment;
    fragment = de.danoeh.antennapod.fragment.FeedSettingsFragment.newInstance(feed);
    ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(fragment, de.danoeh.antennapod.fragment.TransitionEffect.SLIDE);
}
});
break;
}
}
switch(MUID_STATIC) {
// FeedItemlistFragment_5_BuggyGUIListenerOperatorMutator
case 5120: {
viewBinding.header.butFilter.setOnClickListener(null);
break;
}
default: {
viewBinding.header.butFilter.setOnClickListener((android.view.View v) -> de.danoeh.antennapod.dialog.FeedItemFilterDialog.newInstance(feed).show(getChildFragmentManager(), null));
break;
}
}
switch(MUID_STATIC) {
// FeedItemlistFragment_6_BuggyGUIListenerOperatorMutator
case 6120: {
viewBinding.header.txtvFailure.setOnClickListener(null);
break;
}
default: {
viewBinding.header.txtvFailure.setOnClickListener((android.view.View v) -> showErrorDetails());
break;
}
}
headerCreated = true;
}


private void showErrorDetails() {
io.reactivex.Maybe.fromCallable(() -> {
java.util.List<de.danoeh.antennapod.model.download.DownloadResult> feedDownloadLog;
feedDownloadLog = de.danoeh.antennapod.core.storage.DBReader.getFeedDownloadLog(feedID);
if ((feedDownloadLog.size() == 0) || feedDownloadLog.get(0).isSuccessful()) {
return null;
}
return feedDownloadLog.get(0);
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.download.DownloadResult downloadStatus) -> new de.danoeh.antennapod.dialog.DownloadLogDetailsDialog(getContext(), downloadStatus).show(), (java.lang.Throwable error) -> error.printStackTrace(), () -> new de.danoeh.antennapod.fragment.DownloadLogFragment().show(getChildFragmentManager(), null));
}


private void showFeedInfo() {
if (feed != null) {
de.danoeh.antennapod.fragment.FeedInfoFragment fragment;
fragment = de.danoeh.antennapod.fragment.FeedInfoFragment.newInstance(feed);
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(fragment, de.danoeh.antennapod.fragment.TransitionEffect.SLIDE);
}
}


private void loadFeedImage() {
com.bumptech.glide.Glide.with(this).load(feed.getImageUrl()).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.image_readability_tint).error(de.danoeh.antennapod.R.color.image_readability_tint).transform(new de.danoeh.antennapod.ui.glide.FastBlurTransformation()).dontAnimate()).into(viewBinding.imgvBackground);
com.bumptech.glide.Glide.with(this).load(feed.getImageUrl()).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).error(de.danoeh.antennapod.R.color.light_gray).fitCenter().dontAnimate()).into(viewBinding.header.imgvCover);
}


private void loadItems() {
if (disposable != null) {
disposable.dispose();
}
disposable = io.reactivex.Observable.fromCallable(this::loadData).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.feed.Feed result) -> {
feed = result;
swipeActions.setFilter(feed.getItemFilter());
refreshHeaderView();
viewBinding.progressBar.setVisibility(android.view.View.GONE);
adapter.setDummyViews(0);
adapter.updateItems(feed.getItems());
updateToolbar();
}, (java.lang.Throwable error) -> {
feed = null;
refreshHeaderView();
adapter.setDummyViews(0);
adapter.updateItems(java.util.Collections.emptyList());
updateToolbar();
android.util.Log.e(de.danoeh.antennapod.fragment.FeedItemlistFragment.TAG, android.util.Log.getStackTraceString(error));
});
}


@androidx.annotation.Nullable
private de.danoeh.antennapod.model.feed.Feed loadData() {
de.danoeh.antennapod.model.feed.Feed feed;
feed = de.danoeh.antennapod.core.storage.DBReader.getFeed(feedID, true);
if (feed == null) {
return null;
}
de.danoeh.antennapod.core.storage.DBReader.loadAdditionalFeedItemListData(feed.getItems());
if (feed.getSortOrder() != null) {
java.util.List<de.danoeh.antennapod.model.feed.FeedItem> feedItems;
feedItems = feed.getItems();
de.danoeh.antennapod.core.util.FeedItemPermutors.getPermutor(feed.getSortOrder()).reorder(feedItems);
feed.setItems(feedItems);
}
return feed;
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onKeyUp(android.view.KeyEvent event) {
if (((!isAdded()) || (!isVisible())) || (!isMenuVisible())) {
return;
}
switch (event.getKeyCode()) {
case android.view.KeyEvent.KEYCODE_T :
viewBinding.recyclerView.smoothScrollToPosition(0);
break;
case android.view.KeyEvent.KEYCODE_B :
switch(MUID_STATIC) {
// FeedItemlistFragment_7_BinaryMutator
case 7120: {
viewBinding.recyclerView.smoothScrollToPosition(adapter.getItemCount() + 1);
break;
}
default: {
viewBinding.recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
break;
}
}
break;
default :
break;
}
}


private class FeedItemListAdapter extends de.danoeh.antennapod.adapter.EpisodeItemListAdapter {
public FeedItemListAdapter(de.danoeh.antennapod.activity.MainActivity mainActivity) {
super(mainActivity);
}


@java.lang.Override
protected void beforeBindViewHolder(de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder, int pos) {
holder.coverHolder.setVisibility(android.view.View.GONE);
}


@java.lang.Override
public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
super.onCreateContextMenu(menu, v, menuInfo);
if (!inActionMode()) {
menu.findItem(de.danoeh.antennapod.R.id.multi_select).setVisible(true);
}
de.danoeh.antennapod.core.menuhandler.MenuItemUtils.setOnClickListeners(menu, de.danoeh.antennapod.fragment.FeedItemlistFragment.this::onContextItemSelected);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
