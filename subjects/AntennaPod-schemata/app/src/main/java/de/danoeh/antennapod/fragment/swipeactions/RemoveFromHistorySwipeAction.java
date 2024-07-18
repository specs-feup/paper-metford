package de.danoeh.antennapod.fragment.swipeactions;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import android.content.Context;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RemoveFromHistorySwipeAction implements de.danoeh.antennapod.fragment.swipeactions.SwipeAction {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "RemoveFromHistorySwipeAction";

    @java.lang.Override
    public java.lang.String getId() {
        return de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_HISTORY;
    }


    @java.lang.Override
    public int getActionIcon() {
        return de.danoeh.antennapod.R.drawable.ic_history_remove;
    }


    @java.lang.Override
    public int getActionColor() {
        return de.danoeh.antennapod.R.attr.icon_purple;
    }


    @java.lang.Override
    public java.lang.String getTitle(android.content.Context context) {
        return context.getString(de.danoeh.antennapod.R.string.remove_history_label);
    }


    @java.lang.Override
    public void performAction(de.danoeh.antennapod.model.feed.FeedItem item, androidx.fragment.app.Fragment fragment, de.danoeh.antennapod.model.feed.FeedItemFilter filter) {
        java.util.Date playbackCompletionDate;
        playbackCompletionDate = item.getMedia().getPlaybackCompletionDate();
        de.danoeh.antennapod.core.storage.DBWriter.deleteFromPlaybackHistory(item);
        switch(MUID_STATIC) {
            // RemoveFromHistorySwipeAction_0_BuggyGUIListenerOperatorMutator
            case 111: {
                ((de.danoeh.antennapod.activity.MainActivity) (fragment.requireActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.removed_history_label, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(fragment.getString(de.danoeh.antennapod.R.string.undo), null);
                break;
            }
            default: {
            ((de.danoeh.antennapod.activity.MainActivity) (fragment.requireActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.removed_history_label, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(fragment.getString(de.danoeh.antennapod.R.string.undo), (android.view.View v) -> de.danoeh.antennapod.core.storage.DBWriter.addItemToPlaybackHistory(item.getMedia(), playbackCompletionDate));
            break;
        }
    }
}


@java.lang.Override
public boolean willRemove(de.danoeh.antennapod.model.feed.FeedItemFilter filter, de.danoeh.antennapod.model.feed.FeedItem item) {
    return true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
