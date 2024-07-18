package de.danoeh.antennapod.fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import java.util.ArrayList;
import de.danoeh.antennapod.model.feed.SortOrder;
import org.greenrobot.eventbus.Subscribe;
import android.view.MenuItem;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import android.view.View;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.model.feed.FeedItem;
import org.apache.commons.lang3.StringUtils;
import de.danoeh.antennapod.dialog.AllEpisodesFilterDialog;
import java.util.List;
import java.util.HashSet;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Shows all episodes (possibly filtered by user).
 */
public class AllEpisodesFragment extends de.danoeh.antennapod.fragment.EpisodesListFragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "EpisodesFragment";

    private static final java.lang.String PREF_NAME = "PrefAllEpisodesFragment";

    private static final java.lang.String PREF_FILTER = "filter";

    public static final java.lang.String PREF_SORT = "prefEpisodesSort";

    private android.content.SharedPreferences prefs;

    @androidx.annotation.NonNull
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        final android.view.View root;
        root = super.onCreateView(inflater, container, savedInstanceState);
        toolbar.inflateMenu(de.danoeh.antennapod.R.menu.episodes);
        inflateSortMenu();
        toolbar.setTitle(de.danoeh.antennapod.R.string.episodes_label);
        updateToolbar();
        updateFilterUi();
        prefs = getActivity().getSharedPreferences(de.danoeh.antennapod.fragment.AllEpisodesFragment.PREF_NAME, android.content.Context.MODE_PRIVATE);
        switch(MUID_STATIC) {
            // AllEpisodesFragment_0_BuggyGUIListenerOperatorMutator
            case 133: {
                txtvInformation.setOnClickListener(null);
                break;
            }
            default: {
            txtvInformation.setOnClickListener((android.view.View v) -> de.danoeh.antennapod.dialog.AllEpisodesFilterDialog.newInstance(getFilter()).show(getChildFragmentManager(), null));
            break;
        }
    }
    return root;
}


private void inflateSortMenu() {
    android.view.MenuItem sortItem;
    sortItem = toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.episodes_sort);
    getActivity().getMenuInflater().inflate(de.danoeh.antennapod.R.menu.sort_menu, sortItem.getSubMenu());
    // Remove the sorting options that are not needed in this fragment
    toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.sort_episode_title).setVisible(false);
    toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.sort_feed_title).setVisible(false);
    toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.sort_random).setVisible(false);
    toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.sort_smart_shuffle).setVisible(false);
    toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.keep_sorted).setVisible(false);
}


@androidx.annotation.NonNull
@java.lang.Override
protected java.util.List<de.danoeh.antennapod.model.feed.FeedItem> loadData() {
    switch(MUID_STATIC) {
        // AllEpisodesFragment_1_BinaryMutator
        case 1133: {
            return de.danoeh.antennapod.core.storage.DBReader.getEpisodes(0, page / de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, getFilter(), getSortOrder());
        }
        default: {
        return de.danoeh.antennapod.core.storage.DBReader.getEpisodes(0, page * de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, getFilter(), getSortOrder());
        }
}
}


@androidx.annotation.NonNull
@java.lang.Override
protected java.util.List<de.danoeh.antennapod.model.feed.FeedItem> loadMoreData(int page) {
switch(MUID_STATIC) {
    // AllEpisodesFragment_2_BinaryMutator
    case 2133: {
        return de.danoeh.antennapod.core.storage.DBReader.getEpisodes((page - 1) / de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, getFilter(), getSortOrder());
    }
    default: {
    switch(MUID_STATIC) {
        // AllEpisodesFragment_3_BinaryMutator
        case 3133: {
            return de.danoeh.antennapod.core.storage.DBReader.getEpisodes((page + 1) * de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, getFilter(), getSortOrder());
        }
        default: {
        return de.danoeh.antennapod.core.storage.DBReader.getEpisodes((page - 1) * de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, getFilter(), getSortOrder());
        }
}
}
}
}


@java.lang.Override
protected int loadTotalItemCount() {
return de.danoeh.antennapod.core.storage.DBReader.getTotalEpisodeCount(getFilter());
}


@java.lang.Override
protected de.danoeh.antennapod.model.feed.FeedItemFilter getFilter() {
android.content.SharedPreferences prefs;
prefs = getActivity().getSharedPreferences(de.danoeh.antennapod.fragment.AllEpisodesFragment.PREF_NAME, android.content.Context.MODE_PRIVATE);
return new de.danoeh.antennapod.model.feed.FeedItemFilter(prefs.getString(de.danoeh.antennapod.fragment.AllEpisodesFragment.PREF_FILTER, ""));
}


@java.lang.Override
protected java.lang.String getFragmentTag() {
return de.danoeh.antennapod.fragment.AllEpisodesFragment.TAG;
}


@java.lang.Override
protected java.lang.String getPrefName() {
return de.danoeh.antennapod.fragment.AllEpisodesFragment.PREF_NAME;
}


@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem item) {
if (super.onOptionsItemSelected(item)) {
return true;
}
if (item.getItemId() == de.danoeh.antennapod.R.id.filter_items) {
de.danoeh.antennapod.dialog.AllEpisodesFilterDialog.newInstance(getFilter()).show(getChildFragmentManager(), null);
return true;
} else if (item.getItemId() == de.danoeh.antennapod.R.id.action_favorites) {
java.util.ArrayList<java.lang.String> filter;
filter = new java.util.ArrayList<>(getFilter().getValuesList());
if (filter.contains(de.danoeh.antennapod.model.feed.FeedItemFilter.IS_FAVORITE)) {
filter.remove(de.danoeh.antennapod.model.feed.FeedItemFilter.IS_FAVORITE);
} else {
filter.add(de.danoeh.antennapod.model.feed.FeedItemFilter.IS_FAVORITE);
}
onFilterChanged(new de.danoeh.antennapod.dialog.AllEpisodesFilterDialog.AllEpisodesFilterChangedEvent(new java.util.HashSet<>(filter)));
return true;
} else {
de.danoeh.antennapod.model.feed.SortOrder sortOrder;
sortOrder = de.danoeh.antennapod.fragment.MenuItemToSortOrderConverter.convert(item);
if (sortOrder != null) {
saveSortOrderAndRefresh(sortOrder);
return true;
}
}
return false;
}


private void saveSortOrderAndRefresh(de.danoeh.antennapod.model.feed.SortOrder type) {
prefs.edit().putString(de.danoeh.antennapod.fragment.AllEpisodesFragment.PREF_SORT, "" + type.code).apply();
loadItems();
}


@org.greenrobot.eventbus.Subscribe
public void onFilterChanged(de.danoeh.antennapod.dialog.AllEpisodesFilterDialog.AllEpisodesFilterChangedEvent event) {
prefs.edit().putString(de.danoeh.antennapod.fragment.AllEpisodesFragment.PREF_FILTER, org.apache.commons.lang3.StringUtils.join(event.filterValues, ",")).apply();
updateFilterUi();
page = 1;
loadItems();
}


private void updateFilterUi() {
swipeActions.setFilter(getFilter());
if (getFilter().getValues().length > 0) {
txtvInformation.setVisibility(android.view.View.VISIBLE);
emptyView.setMessage(de.danoeh.antennapod.R.string.no_all_episodes_filtered_label);
} else {
txtvInformation.setVisibility(android.view.View.GONE);
emptyView.setMessage(de.danoeh.antennapod.R.string.no_all_episodes_label);
}
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.action_favorites).setIcon(getFilter().showIsFavorite ? de.danoeh.antennapod.R.drawable.ic_star : de.danoeh.antennapod.R.drawable.ic_star_border);
}


private de.danoeh.antennapod.model.feed.SortOrder getSortOrder() {
return de.danoeh.antennapod.model.feed.SortOrder.fromCodeString(prefs.getString(de.danoeh.antennapod.fragment.AllEpisodesFragment.PREF_SORT, "" + de.danoeh.antennapod.model.feed.SortOrder.DATE_NEW_OLD.code));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
