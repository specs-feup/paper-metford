package de.danoeh.antennapod.fragment;
import org.greenrobot.eventbus.ThreadMode;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup;
import de.danoeh.antennapod.core.storage.DBWriter;
import org.greenrobot.eventbus.Subscribe;
import android.view.MenuItem;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import de.danoeh.antennapod.core.dialog.ConfirmationDialog;
import android.view.View;
import de.danoeh.antennapod.event.playback.PlaybackHistoryEvent;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.model.feed.FeedItem;
import java.util.List;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PlaybackHistoryFragment extends de.danoeh.antennapod.fragment.EpisodesListFragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "PlaybackHistoryFragment";

    @androidx.annotation.NonNull
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        final android.view.View root;
        root = super.onCreateView(inflater, container, savedInstanceState);
        toolbar.inflateMenu(de.danoeh.antennapod.R.menu.playback_history);
        toolbar.setTitle(de.danoeh.antennapod.R.string.playback_history_label);
        updateToolbar();
        emptyView.setIcon(de.danoeh.antennapod.R.drawable.ic_history);
        emptyView.setTitle(de.danoeh.antennapod.R.string.no_history_head_label);
        emptyView.setMessage(de.danoeh.antennapod.R.string.no_history_label);
        return root;
    }


    @java.lang.Override
    protected de.danoeh.antennapod.model.feed.FeedItemFilter getFilter() {
        return de.danoeh.antennapod.model.feed.FeedItemFilter.unfiltered();
    }


    @java.lang.Override
    protected java.lang.String getFragmentTag() {
        return de.danoeh.antennapod.fragment.PlaybackHistoryFragment.TAG;
    }


    @java.lang.Override
    protected java.lang.String getPrefName() {
        return de.danoeh.antennapod.fragment.PlaybackHistoryFragment.TAG;
    }


    @java.lang.Override
    public boolean onMenuItemClick(android.view.MenuItem item) {
        if (super.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == de.danoeh.antennapod.R.id.clear_history_item) {
            de.danoeh.antennapod.core.dialog.ConfirmationDialog conDialog;
            conDialog = new de.danoeh.antennapod.core.dialog.ConfirmationDialog(getActivity(), de.danoeh.antennapod.R.string.clear_history_label, de.danoeh.antennapod.R.string.clear_playback_history_msg) {
                @java.lang.Override
                public void onConfirmButtonPressed(android.content.DialogInterface dialog) {
                    dialog.dismiss();
                    de.danoeh.antennapod.core.storage.DBWriter.clearPlaybackHistory();
                }

            };
            conDialog.createNewDialog().show();
            return true;
        }
        return false;
    }


    @java.lang.Override
    protected void updateToolbar() {
        // Not calling super, as we do not have a refresh button that could be updated
        toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.clear_history_item).setVisible(!episodes.isEmpty());
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onHistoryUpdated(de.danoeh.antennapod.event.playback.PlaybackHistoryEvent event) {
        loadItems();
        updateToolbar();
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    protected java.util.List<de.danoeh.antennapod.model.feed.FeedItem> loadData() {
        switch(MUID_STATIC) {
            // PlaybackHistoryFragment_0_BinaryMutator
            case 135: {
                return de.danoeh.antennapod.core.storage.DBReader.getPlaybackHistory(0, page / de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE);
            }
            default: {
            return de.danoeh.antennapod.core.storage.DBReader.getPlaybackHistory(0, page * de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE);
            }
    }
}


@androidx.annotation.NonNull
@java.lang.Override
protected java.util.List<de.danoeh.antennapod.model.feed.FeedItem> loadMoreData(int page) {
    switch(MUID_STATIC) {
        // PlaybackHistoryFragment_1_BinaryMutator
        case 1135: {
            return de.danoeh.antennapod.core.storage.DBReader.getPlaybackHistory((page - 1) / de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE);
        }
        default: {
        switch(MUID_STATIC) {
            // PlaybackHistoryFragment_2_BinaryMutator
            case 2135: {
                return de.danoeh.antennapod.core.storage.DBReader.getPlaybackHistory((page + 1) * de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE);
            }
            default: {
            return de.danoeh.antennapod.core.storage.DBReader.getPlaybackHistory((page - 1) * de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE, de.danoeh.antennapod.fragment.EpisodesListFragment.EPISODES_PER_PAGE);
            }
    }
    }
}
}


@java.lang.Override
protected int loadTotalItemCount() {
return ((int) (de.danoeh.antennapod.core.storage.DBReader.getPlaybackHistoryLength()));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
