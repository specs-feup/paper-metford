package de.danoeh.antennapod.dialog;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.Arrays;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FeedSortDialog {
    static final int MUID_STATIC = getMUID();
    public static void showDialog(android.content.Context context) {
        com.google.android.material.dialog.MaterialAlertDialogBuilder dialog;
        dialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context);
        dialog.setTitle(context.getString(de.danoeh.antennapod.R.string.pref_nav_drawer_feed_order_title));
        switch(MUID_STATIC) {
            // FeedSortDialog_0_BuggyGUIListenerOperatorMutator
            case 63: {
                dialog.setNegativeButton(android.R.string.cancel, null);
                break;
            }
            default: {
            dialog.setNegativeButton(android.R.string.cancel, (android.content.DialogInterface d,int listener) -> d.dismiss());
            break;
        }
    }
    int selected;
    selected = de.danoeh.antennapod.storage.preferences.UserPreferences.getFeedOrder();
    java.util.List<java.lang.String> entryValues;
    entryValues = java.util.Arrays.asList(context.getResources().getStringArray(de.danoeh.antennapod.R.array.nav_drawer_feed_order_values));
    final int selectedIndex;
    selectedIndex = entryValues.indexOf("" + selected);
    java.lang.String[] items;
    items = context.getResources().getStringArray(de.danoeh.antennapod.R.array.nav_drawer_feed_order_options);
    switch(MUID_STATIC) {
        // FeedSortDialog_1_BuggyGUIListenerOperatorMutator
        case 1063: {
            dialog.setSingleChoiceItems(items, selectedIndex, null);
            break;
        }
        default: {
        dialog.setSingleChoiceItems(items, selectedIndex, (android.content.DialogInterface d,int which) -> {
            if (selectedIndex != which) {
                de.danoeh.antennapod.storage.preferences.UserPreferences.setFeedOrder(entryValues.get(which));
                // Update subscriptions
                org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.UnreadItemsUpdateEvent());
            }
            d.dismiss();
        });
        break;
    }
}
dialog.show();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
