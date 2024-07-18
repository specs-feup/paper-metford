package de.danoeh.antennapod.dialog;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import java.util.List;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.fragment.NavDrawerFragment;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DrawerPreferencesDialog {
    static final int MUID_STATIC = getMUID();
    public static void show(android.content.Context context, java.lang.Runnable callback) {
        final java.util.List<java.lang.String> hiddenDrawerItems;
        hiddenDrawerItems = de.danoeh.antennapod.storage.preferences.UserPreferences.getHiddenDrawerItems();
        final java.lang.String[] navTitles;
        navTitles = context.getResources().getStringArray(de.danoeh.antennapod.R.array.nav_drawer_titles);
        boolean[] checked;
        checked = new boolean[de.danoeh.antennapod.fragment.NavDrawerFragment.NAV_DRAWER_TAGS.length];
        for (int i = 0; i < de.danoeh.antennapod.fragment.NavDrawerFragment.NAV_DRAWER_TAGS.length; i++) {
            java.lang.String tag;
            tag = de.danoeh.antennapod.fragment.NavDrawerFragment.NAV_DRAWER_TAGS[i];
            if (!hiddenDrawerItems.contains(tag)) {
                checked[i] = true;
            }
        }
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context);
        builder.setTitle(de.danoeh.antennapod.R.string.drawer_preferences);
        builder.setMultiChoiceItems(navTitles, checked, (android.content.DialogInterface dialog,int which,boolean isChecked) -> {
            if (isChecked) {
                hiddenDrawerItems.remove(de.danoeh.antennapod.fragment.NavDrawerFragment.NAV_DRAWER_TAGS[which]);
            } else {
                hiddenDrawerItems.add(de.danoeh.antennapod.fragment.NavDrawerFragment.NAV_DRAWER_TAGS[which]);
            }
        });
        switch(MUID_STATIC) {
            // DrawerPreferencesDialog_0_BuggyGUIListenerOperatorMutator
            case 54: {
                builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
                break;
            }
            default: {
            builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> {
                de.danoeh.antennapod.storage.preferences.UserPreferences.setHiddenDrawerItems(hiddenDrawerItems);
                if (hiddenDrawerItems.contains(de.danoeh.antennapod.storage.preferences.UserPreferences.getDefaultPage())) {
                    for (java.lang.String tag : de.danoeh.antennapod.fragment.NavDrawerFragment.NAV_DRAWER_TAGS) {
                        if (!hiddenDrawerItems.contains(tag)) {
                            de.danoeh.antennapod.storage.preferences.UserPreferences.setDefaultPage(tag);
                            break;
                        }
                    }
                }
                if (callback != null) {
                    callback.run();
                }
            });
            break;
        }
    }
    builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
    builder.create().show();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
