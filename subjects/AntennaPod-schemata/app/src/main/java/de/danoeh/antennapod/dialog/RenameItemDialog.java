package de.danoeh.antennapod.dialog;
import androidx.appcompat.app.AlertDialog;
import de.danoeh.antennapod.model.feed.FeedPreferences;
import de.danoeh.antennapod.core.storage.NavDrawerData;
import java.util.ArrayList;
import de.danoeh.antennapod.core.storage.DBWriter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.databinding.EditTextDialogBinding;
import java.lang.ref.WeakReference;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import android.app.Activity;
import java.util.List;
import de.danoeh.antennapod.model.feed.Feed;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RenameItemDialog {
    static final int MUID_STATIC = getMUID();
    private final java.lang.ref.WeakReference<android.app.Activity> activityRef;

    private de.danoeh.antennapod.model.feed.Feed feed = null;

    private de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem drawerItem = null;

    public RenameItemDialog(android.app.Activity activity, de.danoeh.antennapod.model.feed.Feed feed) {
        this.activityRef = new java.lang.ref.WeakReference<>(activity);
        this.feed = feed;
    }


    public RenameItemDialog(android.app.Activity activity, de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem drawerItem) {
        this.activityRef = new java.lang.ref.WeakReference<>(activity);
        this.drawerItem = drawerItem;
    }


    public void show() {
        android.app.Activity activity;
        activity = activityRef.get();
        if (activity == null) {
            return;
        }
        final de.danoeh.antennapod.databinding.EditTextDialogBinding binding;
        binding = de.danoeh.antennapod.databinding.EditTextDialogBinding.inflate(android.view.LayoutInflater.from(activity));
        java.lang.String title;
        title = (feed != null) ? feed.getTitle() : drawerItem.getTitle();
        binding.urlEditText.setText(title);
        androidx.appcompat.app.AlertDialog dialog;
        switch(MUID_STATIC) {
            // RenameItemDialog_0_BuggyGUIListenerOperatorMutator
            case 64: {
                dialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(activity).setView(binding.getRoot()).setTitle(feed != null ? de.danoeh.antennapod.R.string.rename_feed_label : de.danoeh.antennapod.R.string.rename_tag_label).setPositiveButton(android.R.string.ok, null).setNeutralButton(de.danoeh.antennapod.core.R.string.reset, null).setNegativeButton(de.danoeh.antennapod.core.R.string.cancel_label, null).show();
                break;
            }
            default: {
            dialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(activity).setView(binding.getRoot()).setTitle(feed != null ? de.danoeh.antennapod.R.string.rename_feed_label : de.danoeh.antennapod.R.string.rename_tag_label).setPositiveButton(android.R.string.ok, (android.content.DialogInterface d,int input) -> {
                java.lang.String newTitle;
                newTitle = binding.urlEditText.getText().toString();
                if (feed != null) {
                    feed.setCustomTitle(newTitle);
                    de.danoeh.antennapod.core.storage.DBWriter.setFeedCustomTitle(feed);
                } else {
                    renameTag(newTitle);
                }
            }).setNeutralButton(de.danoeh.antennapod.core.R.string.reset, null).setNegativeButton(de.danoeh.antennapod.core.R.string.cancel_label, null).show();
            break;
        }
    }
    switch(MUID_STATIC) {
        // RenameItemDialog_1_BuggyGUIListenerOperatorMutator
        case 1064: {
            // To prevent cancelling the dialog on button click
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL).setOnClickListener(null);
            break;
        }
        default: {
        // To prevent cancelling the dialog on button click
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL).setOnClickListener((android.view.View view) -> binding.urlEditText.setText(title));
        break;
    }
}
}


private void renameTag(java.lang.String title) {
if (de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.TAG == drawerItem.type) {
    java.util.List<de.danoeh.antennapod.model.feed.FeedPreferences> feedPreferences;
    feedPreferences = new java.util.ArrayList<>();
    for (de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem item : ((de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem) (drawerItem)).children) {
        feedPreferences.add(((de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem) (item)).feed.getPreferences());
    }
    for (de.danoeh.antennapod.model.feed.FeedPreferences preferences : feedPreferences) {
        preferences.getTags().remove(drawerItem.getTitle());
        preferences.getTags().add(title);
        de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(preferences);
    }
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
