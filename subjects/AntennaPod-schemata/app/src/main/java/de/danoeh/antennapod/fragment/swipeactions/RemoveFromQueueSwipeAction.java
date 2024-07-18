package de.danoeh.antennapod.fragment.swipeactions;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RemoveFromQueueSwipeAction implements de.danoeh.antennapod.fragment.swipeactions.SwipeAction {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public java.lang.String getId() {
        return de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_QUEUE;
    }


    @java.lang.Override
    public int getActionIcon() {
        return de.danoeh.antennapod.R.drawable.ic_playlist_remove;
    }


    @java.lang.Override
    public int getActionColor() {
        return de.danoeh.antennapod.R.attr.colorAccent;
    }


    @java.lang.Override
    public java.lang.String getTitle(android.content.Context context) {
        return context.getString(de.danoeh.antennapod.R.string.remove_from_queue_label);
    }


    @java.lang.Override
    public void performAction(de.danoeh.antennapod.model.feed.FeedItem item, androidx.fragment.app.Fragment fragment, de.danoeh.antennapod.model.feed.FeedItemFilter filter) {
        int position;
        position = de.danoeh.antennapod.core.storage.DBReader.getQueueIDList().indexOf(item.getId());
        de.danoeh.antennapod.core.storage.DBWriter.removeQueueItem(fragment.requireActivity(), true, item);
        if (willRemove(filter, item)) {
            switch(MUID_STATIC) {
                // RemoveFromQueueSwipeAction_0_BuggyGUIListenerOperatorMutator
                case 109: {
                    ((de.danoeh.antennapod.activity.MainActivity) (fragment.requireActivity())).showSnackbarAbovePlayer(fragment.getResources().getQuantityString(de.danoeh.antennapod.R.plurals.removed_from_queue_batch_label, 1, 1), com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(fragment.getString(de.danoeh.antennapod.R.string.undo), null);
                    break;
                }
                default: {
                ((de.danoeh.antennapod.activity.MainActivity) (fragment.requireActivity())).showSnackbarAbovePlayer(fragment.getResources().getQuantityString(de.danoeh.antennapod.R.plurals.removed_from_queue_batch_label, 1, 1), com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(fragment.getString(de.danoeh.antennapod.R.string.undo), (android.view.View v) -> de.danoeh.antennapod.core.storage.DBWriter.addQueueItemAt(fragment.requireActivity(), item.getId(), position, false));
                break;
            }
        }
    }
}


@java.lang.Override
public boolean willRemove(de.danoeh.antennapod.model.feed.FeedItemFilter filter, de.danoeh.antennapod.model.feed.FeedItem item) {
    return filter.showQueued || filter.showNotQueued;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
