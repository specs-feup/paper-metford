package de.danoeh.antennapod.fragment.actions;
import de.danoeh.antennapod.databinding.PlaybackSpeedFeedSettingDialogBinding;
import java.util.Locale;
import de.danoeh.antennapod.fragment.preferences.dialog.PreferenceListDialog;
import de.danoeh.antennapod.model.feed.FeedPreferences;
import android.util.Log;
import java.util.ArrayList;
import de.danoeh.antennapod.dialog.TagSettingsDialog;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.core.storage.DBWriter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.R;
import androidx.core.util.Consumer;
import de.danoeh.antennapod.dialog.RemoveFeedDialog;
import java.util.List;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.PluralsRes;
import de.danoeh.antennapod.fragment.preferences.dialog.PreferenceSwitchDialog;
import de.danoeh.antennapod.model.feed.Feed;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FeedMultiSelectActionHandler {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "FeedSelectHandler";

    private final de.danoeh.antennapod.activity.MainActivity activity;

    private final java.util.List<de.danoeh.antennapod.model.feed.Feed> selectedItems;

    public FeedMultiSelectActionHandler(de.danoeh.antennapod.activity.MainActivity activity, java.util.List<de.danoeh.antennapod.model.feed.Feed> selectedItems) {
        this.activity = activity;
        this.selectedItems = selectedItems;
    }


    public void handleAction(int id) {
        if (id == de.danoeh.antennapod.R.id.remove_feed) {
            de.danoeh.antennapod.dialog.RemoveFeedDialog.show(activity, selectedItems);
        } else if (id == de.danoeh.antennapod.R.id.notify_new_episodes) {
            notifyNewEpisodesPrefHandler();
        } else if (id == de.danoeh.antennapod.R.id.keep_updated) {
            keepUpdatedPrefHandler();
        } else if (id == de.danoeh.antennapod.R.id.autodownload) {
            autoDownloadPrefHandler();
        } else if (id == de.danoeh.antennapod.R.id.autoDeleteDownload) {
            autoDeleteEpisodesPrefHandler();
        } else if (id == de.danoeh.antennapod.R.id.playback_speed) {
            playbackSpeedPrefHandler();
        } else if (id == de.danoeh.antennapod.R.id.edit_tags) {
            editFeedPrefTags();
        } else {
            android.util.Log.e(de.danoeh.antennapod.fragment.actions.FeedMultiSelectActionHandler.TAG, "Unrecognized speed dial action item. Do nothing. id=" + id);
        }
    }


    private void notifyNewEpisodesPrefHandler() {
        de.danoeh.antennapod.fragment.preferences.dialog.PreferenceSwitchDialog preferenceSwitchDialog;
        preferenceSwitchDialog = new de.danoeh.antennapod.fragment.preferences.dialog.PreferenceSwitchDialog(activity, activity.getString(de.danoeh.antennapod.R.string.episode_notification), activity.getString(de.danoeh.antennapod.R.string.episode_notification_summary));
        preferenceSwitchDialog.setOnPreferenceChangedListener((boolean enabled) -> saveFeedPreferences((de.danoeh.antennapod.model.feed.FeedPreferences feedPreferences) -> feedPreferences.setShowEpisodeNotification(enabled)));
        preferenceSwitchDialog.openDialog();
    }


    private void autoDownloadPrefHandler() {
        de.danoeh.antennapod.fragment.preferences.dialog.PreferenceSwitchDialog preferenceSwitchDialog;
        preferenceSwitchDialog = new de.danoeh.antennapod.fragment.preferences.dialog.PreferenceSwitchDialog(activity, activity.getString(de.danoeh.antennapod.R.string.auto_download_settings_label), activity.getString(de.danoeh.antennapod.R.string.auto_download_label));
        preferenceSwitchDialog.setOnPreferenceChangedListener((boolean enabled) -> saveFeedPreferences((de.danoeh.antennapod.model.feed.FeedPreferences feedPreferences) -> feedPreferences.setAutoDownload(enabled)));
        preferenceSwitchDialog.openDialog();
    }


    private void playbackSpeedPrefHandler() {
        de.danoeh.antennapod.databinding.PlaybackSpeedFeedSettingDialogBinding viewBinding;
        viewBinding = de.danoeh.antennapod.databinding.PlaybackSpeedFeedSettingDialogBinding.inflate(activity.getLayoutInflater());
        viewBinding.seekBar.setProgressChangedListener((java.lang.Float speed) -> viewBinding.currentSpeedLabel.setText(java.lang.String.format(java.util.Locale.getDefault(), "%.2fx", speed)));
        viewBinding.useGlobalCheckbox.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> {
            viewBinding.seekBar.setEnabled(!isChecked);
            viewBinding.seekBar.setAlpha(isChecked ? 0.4F : 1.0F);
            viewBinding.currentSpeedLabel.setAlpha(isChecked ? 0.4F : 1.0F);
        });
        viewBinding.seekBar.updateSpeed(1.0F);
        switch(MUID_STATIC) {
            // FeedMultiSelectActionHandler_0_BuggyGUIListenerOperatorMutator
            case 85: {
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(activity).setTitle(de.danoeh.antennapod.R.string.playback_speed).setView(viewBinding.getRoot()).setPositiveButton(android.R.string.ok, null).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
                break;
            }
            default: {
            new com.google.android.material.dialog.MaterialAlertDialogBuilder(activity).setTitle(de.danoeh.antennapod.R.string.playback_speed).setView(viewBinding.getRoot()).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> {
                float newSpeed;
                newSpeed = (viewBinding.useGlobalCheckbox.isChecked()) ? de.danoeh.antennapod.model.feed.FeedPreferences.SPEED_USE_GLOBAL : viewBinding.seekBar.getCurrentSpeed();
                saveFeedPreferences((de.danoeh.antennapod.model.feed.FeedPreferences feedPreferences) -> feedPreferences.setFeedPlaybackSpeed(newSpeed));
            }).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
            break;
        }
    }
}


private void autoDeleteEpisodesPrefHandler() {
    de.danoeh.antennapod.fragment.preferences.dialog.PreferenceListDialog preferenceListDialog;
    preferenceListDialog = new de.danoeh.antennapod.fragment.preferences.dialog.PreferenceListDialog(activity, activity.getString(de.danoeh.antennapod.R.string.auto_delete_label));
    java.lang.String[] items;
    items = activity.getResources().getStringArray(de.danoeh.antennapod.R.array.spnAutoDeleteItems);
    preferenceListDialog.openDialog(items);
    preferenceListDialog.setOnPreferenceChangedListener((int which) -> {
        de.danoeh.antennapod.model.feed.FeedPreferences.AutoDeleteAction autoDeleteAction;
        autoDeleteAction = de.danoeh.antennapod.model.feed.FeedPreferences.AutoDeleteAction.fromCode(which);
        saveFeedPreferences((de.danoeh.antennapod.model.feed.FeedPreferences feedPreferences) -> feedPreferences.setAutoDeleteAction(autoDeleteAction));
    });
}


private void keepUpdatedPrefHandler() {
    de.danoeh.antennapod.fragment.preferences.dialog.PreferenceSwitchDialog preferenceSwitchDialog;
    preferenceSwitchDialog = new de.danoeh.antennapod.fragment.preferences.dialog.PreferenceSwitchDialog(activity, activity.getString(de.danoeh.antennapod.R.string.kept_updated), activity.getString(de.danoeh.antennapod.R.string.keep_updated_summary));
    preferenceSwitchDialog.setOnPreferenceChangedListener((boolean keepUpdated) -> saveFeedPreferences((de.danoeh.antennapod.model.feed.FeedPreferences feedPreferences) -> feedPreferences.setKeepUpdated(keepUpdated)));
    preferenceSwitchDialog.openDialog();
}


private void showMessage(@androidx.annotation.PluralsRes
int msgId, int numItems) {
    activity.showSnackbarAbovePlayer(activity.getResources().getQuantityString(msgId, numItems, numItems), com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
}


private void saveFeedPreferences(androidx.core.util.Consumer<de.danoeh.antennapod.model.feed.FeedPreferences> preferencesConsumer) {
    for (de.danoeh.antennapod.model.feed.Feed feed : selectedItems) {
        preferencesConsumer.accept(feed.getPreferences());
        de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feed.getPreferences());
    }
    showMessage(de.danoeh.antennapod.R.plurals.updated_feeds_batch_label, selectedItems.size());
}


private void editFeedPrefTags() {
    java.util.ArrayList<de.danoeh.antennapod.model.feed.FeedPreferences> preferencesList;
    preferencesList = new java.util.ArrayList<>();
    for (de.danoeh.antennapod.model.feed.Feed feed : selectedItems) {
        preferencesList.add(feed.getPreferences());
    }
    de.danoeh.antennapod.dialog.TagSettingsDialog.newInstance(preferencesList).show(activity.getSupportFragmentManager(), de.danoeh.antennapod.dialog.TagSettingsDialog.TAG);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
