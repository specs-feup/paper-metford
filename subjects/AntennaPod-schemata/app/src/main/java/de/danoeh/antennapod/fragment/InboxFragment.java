package de.danoeh.antennapod.fragment;
import android.content.SharedPreferences;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.os.Bundle;
import android.view.ViewGroup;
import de.danoeh.antennapod.model.feed.SortOrder;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.core.storage.DBWriter;
import android.view.MenuItem;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import android.widget.CheckBox;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.model.feed.FeedItem;
import android.widget.Toast;
import java.util.List;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Like 'EpisodesFragment' except that it only shows new episodes and
 * supports swiping to mark as read.
 */
public class InboxFragment extends de.danoeh.antennapod.fragment.EpisodesListFragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "NewEpisodesFragment";

    private static final java.lang.String PREF_NAME = "PrefNewEpisodesFragment";

    private static final java.lang.String PREF_DO_NOT_PROMPT_REMOVE_ALL_FROM_INBOX = "prefDoNotPromptRemovalAllFromInbox";

    private android.content.SharedPreferences prefs;

    @androidx.annotation.NonNull
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        final android.view.View root;
        root = super.onCreateView(inflater, container, savedInstanceState);
        toolbar.inflateMenu(de.danoeh.antennapod.R.menu.inbox);
        inflateSortMenu();
        toolbar.setTitle(de.danoeh.antennapod.R.string.inbox_label);
        prefs = getActivity().getSharedPreferences(de.danoeh.antennapod.fragment.InboxFragment.PREF_NAME, android.content.Context.MODE_PRIVATE);
        updateToolbar();
        emptyView.setIcon(de.danoeh.antennapod.R.drawable.ic_inbox);
        emptyView.setTitle(de.danoeh.antennapod.R.string.no_inbox_head_label);
        emptyView.setMessage(de.danoeh.antennapod.R.string.no_inbox_label);
        speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.mark_unread_batch);
        speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.remove_from_queue_batch);
        speedDialView.removeActionItemById(de.danoeh.antennapod.R.id.delete_batch);
        return root;
    }


    @java.lang.Override
    protected de.danoeh.antennapod.model.feed.FeedItemFilter getFilter() {
        return new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.NEW);
    }


    @java.lang.Override
    protected java.lang.String getFragmentTag() {
        return de.danoeh.antennapod.fragment.InboxFragment.TAG;
    }


    @java.lang.Override
    protected java.lang.String getPrefName() {
        return de.danoeh.antennapod.fragment.InboxFragment.PREF_NAME;
    }


    @java.lang.Override
    public boolean onMenuItemClick(android.view.MenuItem item) {
        if (super.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == de.danoeh.antennapod.R.id.remove_all_inbox_item) {
            if (prefs.getBoolean(de.danoeh.antennapod.fragment.InboxFragment.PREF_DO_NOT_PROMPT_REMOVE_ALL_FROM_INBOX, false)) {
                removeAllFromInbox();
            } else {
                showRemoveAllDialog();
            }
            return true;
        } else {
            de.danoeh.antennapod.model.feed.SortOrder sortOrder;
            sortOrder = de.danoeh.antennapod.fragment.MenuItemToSortOrderConverter.convert(item);
            if (sortOrder != null) {
                de.danoeh.antennapod.storage.preferences.UserPreferences.setInboxSortedOrder(sortOrder);
                loadItems();
                return true;
            }
        }
        return false;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    protected java.util.List<de.danoeh.antennapod.model.feed.FeedItem> loadData() {
        switch(MUID_STATIC) {
            // InboxFragment_0_BinaryMutator
            case 116: {
                return de.danoeh.antennapod.core.storage.DBReader.getEpisodes(0, page / de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.NEW), de.danoeh.antennapod.storage.preferences.UserPreferences.getInboxSortedOrder());
            }
            default: {
            return de.danoeh.antennapod.core.storage.DBReader.getEpisodes(0, page * de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.NEW), de.danoeh.antennapod.storage.preferences.UserPreferences.getInboxSortedOrder());
            }
    }
}


@androidx.annotation.NonNull
@java.lang.Override
protected java.util.List<de.danoeh.antennapod.model.feed.FeedItem> loadMoreData(int page) {
    switch(MUID_STATIC) {
        // InboxFragment_1_BinaryMutator
        case 1116: {
            return de.danoeh.antennapod.core.storage.DBReader.getEpisodes((page - 1) / de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.NEW), de.danoeh.antennapod.storage.preferences.UserPreferences.getInboxSortedOrder());
        }
        default: {
        switch(MUID_STATIC) {
            // InboxFragment_2_BinaryMutator
            case 2116: {
                return de.danoeh.antennapod.core.storage.DBReader.getEpisodes((page + 1) * de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.NEW), de.danoeh.antennapod.storage.preferences.UserPreferences.getInboxSortedOrder());
            }
            default: {
            return de.danoeh.antennapod.core.storage.DBReader.getEpisodes((page - 1) * de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.NEW), de.danoeh.antennapod.storage.preferences.UserPreferences.getInboxSortedOrder());
            }
    }
    }
}
}


@java.lang.Override
protected int loadTotalItemCount() {
return de.danoeh.antennapod.core.storage.DBReader.getTotalEpisodeCount(new de.danoeh.antennapod.model.feed.FeedItemFilter(de.danoeh.antennapod.model.feed.FeedItemFilter.NEW));
}


private void removeAllFromInbox() {
de.danoeh.antennapod.core.storage.DBWriter.removeAllNewFlags();
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.removed_all_inbox_msg, android.widget.Toast.LENGTH_SHORT);
}


private void inflateSortMenu() {
android.view.Menu menu;
menu = toolbar.getMenu();
android.view.MenuItem downloadsItem;
downloadsItem = menu.findItem(de.danoeh.antennapod.R.id.inbox_sort);
android.view.MenuInflater menuInflater;
menuInflater = getActivity().getMenuInflater();
menuInflater.inflate(de.danoeh.antennapod.R.menu.sort_menu, downloadsItem.getSubMenu());
// Remove the sorting options that are not needed in this fragment
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.sort_episode_title).setVisible(false);
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.sort_feed_title).setVisible(false);
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.sort_random).setVisible(false);
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.sort_smart_shuffle).setVisible(false);
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.keep_sorted).setVisible(false);
}


private void showRemoveAllDialog() {
com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
builder.setTitle(de.danoeh.antennapod.R.string.remove_all_inbox_label);
builder.setMessage(de.danoeh.antennapod.R.string.remove_all_inbox_confirmation_msg);
android.view.View view;
view = android.view.View.inflate(getContext(), de.danoeh.antennapod.R.layout.checkbox_do_not_show_again, null);
android.widget.CheckBox checkNeverAskAgain;
switch(MUID_STATIC) {
// InboxFragment_3_InvalidViewFocusOperatorMutator
case 3116: {
    /**
    * Inserted by Kadabra
    */
    checkNeverAskAgain = view.findViewById(de.danoeh.antennapod.R.id.checkbox_do_not_show_again);
    checkNeverAskAgain.requestFocus();
    break;
}
// InboxFragment_4_ViewComponentNotVisibleOperatorMutator
case 4116: {
    /**
    * Inserted by Kadabra
    */
    checkNeverAskAgain = view.findViewById(de.danoeh.antennapod.R.id.checkbox_do_not_show_again);
    checkNeverAskAgain.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
checkNeverAskAgain = view.findViewById(de.danoeh.antennapod.R.id.checkbox_do_not_show_again);
break;
}
}
builder.setView(view);
switch(MUID_STATIC) {
// InboxFragment_5_BuggyGUIListenerOperatorMutator
case 5116: {
builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
break;
}
default: {
builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> {
dialog.dismiss();
removeAllFromInbox();
prefs.edit().putBoolean(de.danoeh.antennapod.fragment.InboxFragment.PREF_DO_NOT_PROMPT_REMOVE_ALL_FROM_INBOX, checkNeverAskAgain.isChecked()).apply();
});
break;
}
}
builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
builder.show();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
