package de.danoeh.antennapod.view;
import de.danoeh.antennapod.ui.i18n.R;
import de.danoeh.antennapod.model.feed.FeedItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class LocalDeleteModal {
    static final int MUID_STATIC = getMUID();
    public static void showLocalFeedDeleteWarningIfNecessary(android.content.Context context, java.lang.Iterable<de.danoeh.antennapod.model.feed.FeedItem> items, java.lang.Runnable deleteCommand) {
        boolean anyLocalFeed;
        anyLocalFeed = false;
        for (de.danoeh.antennapod.model.feed.FeedItem item : items) {
            if (item.getFeed().isLocalFeed()) {
                anyLocalFeed = true;
                break;
            }
        }
        if (!anyLocalFeed) {
            deleteCommand.run();
            return;
        }
        switch(MUID_STATIC) {
            // LocalDeleteModal_0_BuggyGUIListenerOperatorMutator
            case 12: {
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.ui.i18n.R.string.delete_episode_label).setMessage(de.danoeh.antennapod.ui.i18n.R.string.delete_local_feed_warning_body).setPositiveButton(de.danoeh.antennapod.ui.i18n.R.string.delete_label, null).setNegativeButton(de.danoeh.antennapod.ui.i18n.R.string.cancel_label, null).show();
                break;
            }
            default: {
            new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.ui.i18n.R.string.delete_episode_label).setMessage(de.danoeh.antennapod.ui.i18n.R.string.delete_local_feed_warning_body).setPositiveButton(de.danoeh.antennapod.ui.i18n.R.string.delete_label, (android.content.DialogInterface dialog,int which) -> deleteCommand.run()).setNegativeButton(de.danoeh.antennapod.ui.i18n.R.string.cancel_label, null).show();
            break;
        }
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
