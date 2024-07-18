package de.danoeh.antennapod.ui.home;
import android.content.SharedPreferences;
import de.danoeh.antennapod.R;
import android.content.DialogInterface;
import android.text.TextUtils;
import java.util.List;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HomeSectionsSettingsDialog {
    static final int MUID_STATIC = getMUID();
    public static void open(android.content.Context context, android.content.DialogInterface.OnClickListener onSettingsChanged) {
        final java.util.List<java.lang.String> hiddenSections;
        hiddenSections = de.danoeh.antennapod.ui.home.HomeFragment.getHiddenSections(context);
        java.lang.String[] sectionLabels;
        sectionLabels = context.getResources().getStringArray(de.danoeh.antennapod.R.array.home_section_titles);
        java.lang.String[] sectionTags;
        sectionTags = context.getResources().getStringArray(de.danoeh.antennapod.R.array.home_section_tags);
        final boolean[] checked;
        checked = new boolean[sectionLabels.length];
        for (int i = 0; i < sectionLabels.length; i++) {
            java.lang.String tag;
            tag = sectionTags[i];
            if (!hiddenSections.contains(tag)) {
                checked[i] = true;
            }
        }
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context);
        builder.setTitle(de.danoeh.antennapod.R.string.configure_home);
        builder.setMultiChoiceItems(sectionLabels, checked, (android.content.DialogInterface dialog,int which,boolean isChecked) -> {
            if (isChecked) {
                hiddenSections.remove(sectionTags[which]);
            } else {
                hiddenSections.add(sectionTags[which]);
            }
        });
        switch(MUID_STATIC) {
            // HomeSectionsSettingsDialog_0_BuggyGUIListenerOperatorMutator
            case 160: {
                builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
                break;
            }
            default: {
            builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> {
                android.content.SharedPreferences prefs;
                prefs = context.getSharedPreferences(de.danoeh.antennapod.ui.home.HomeFragment.PREF_NAME, android.content.Context.MODE_PRIVATE);
                prefs.edit().putString(de.danoeh.antennapod.ui.home.HomeFragment.PREF_HIDDEN_SECTIONS, android.text.TextUtils.join(",", hiddenSections)).apply();
                onSettingsChanged.onClick(dialog, which);
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
