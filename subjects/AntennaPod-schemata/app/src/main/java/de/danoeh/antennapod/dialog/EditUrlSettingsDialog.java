package de.danoeh.antennapod.dialog;
import androidx.appcompat.app.AlertDialog;
import java.util.Locale;
import android.os.CountDownTimer;
import de.danoeh.antennapod.core.storage.DBWriter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.databinding.EditTextDialogBinding;
import java.lang.ref.WeakReference;
import de.danoeh.antennapod.R;
import java.util.concurrent.ExecutionException;
import android.view.LayoutInflater;
import android.app.Activity;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import de.danoeh.antennapod.model.feed.Feed;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class EditUrlSettingsDialog {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "EditUrlSettingsDialog";

    private final java.lang.ref.WeakReference<android.app.Activity> activityRef;

    private final de.danoeh.antennapod.model.feed.Feed feed;

    public EditUrlSettingsDialog(android.app.Activity activity, de.danoeh.antennapod.model.feed.Feed feed) {
        this.activityRef = new java.lang.ref.WeakReference<>(activity);
        this.feed = feed;
    }


    public void show() {
        android.app.Activity activity;
        activity = activityRef.get();
        if (activity == null) {
            return;
        }
        final de.danoeh.antennapod.databinding.EditTextDialogBinding binding;
        binding = de.danoeh.antennapod.databinding.EditTextDialogBinding.inflate(android.view.LayoutInflater.from(activity));
        binding.urlEditText.setText(feed.getDownload_url());
        switch(MUID_STATIC) {
            // EditUrlSettingsDialog_0_BuggyGUIListenerOperatorMutator
            case 61: {
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(activity).setView(binding.getRoot()).setTitle(de.danoeh.antennapod.R.string.edit_url_menu).setPositiveButton(android.R.string.ok, null).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
                break;
            }
            default: {
            new com.google.android.material.dialog.MaterialAlertDialogBuilder(activity).setView(binding.getRoot()).setTitle(de.danoeh.antennapod.R.string.edit_url_menu).setPositiveButton(android.R.string.ok, (android.content.DialogInterface d,int input) -> showConfirmAlertDialog(java.lang.String.valueOf(binding.urlEditText.getText()))).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
            break;
        }
    }
}


private void onConfirmed(java.lang.String original, java.lang.String updated) {
    try {
        de.danoeh.antennapod.core.storage.DBWriter.updateFeedDownloadURL(original, updated).get();
        feed.setDownload_url(updated);
        de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnce(activityRef.get(), feed);
    } catch (java.util.concurrent.ExecutionException | java.lang.InterruptedException e) {
        throw new java.lang.RuntimeException(e);
    }
}


private void showConfirmAlertDialog(java.lang.String url) {
    android.app.Activity activity;
    activity = activityRef.get();
    androidx.appcompat.app.AlertDialog alertDialog;
    switch(MUID_STATIC) {
        // EditUrlSettingsDialog_1_BuggyGUIListenerOperatorMutator
        case 1061: {
            alertDialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(activity).setTitle(de.danoeh.antennapod.R.string.edit_url_menu).setMessage(de.danoeh.antennapod.R.string.edit_url_confirmation_msg).setPositiveButton(android.R.string.ok, null).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
            break;
        }
        default: {
        alertDialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(activity).setTitle(de.danoeh.antennapod.R.string.edit_url_menu).setMessage(de.danoeh.antennapod.R.string.edit_url_confirmation_msg).setPositiveButton(android.R.string.ok, (android.content.DialogInterface d,int input) -> {
            onConfirmed(feed.getDownload_url(), url);
            setUrl(url);
        }).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
        break;
    }
}
alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);
new android.os.CountDownTimer(15000, 1000) {
    @java.lang.Override
    public void onTick(long millisUntilFinished) {
        switch(MUID_STATIC) {
            // EditUrlSettingsDialog_2_BinaryMutator
            case 2061: {
                alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setText(java.lang.String.format(java.util.Locale.getDefault(), "%s (%d)", activity.getString(android.R.string.ok), (millisUntilFinished / 1000) - 1));
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // EditUrlSettingsDialog_3_BinaryMutator
                case 3061: {
                    alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setText(java.lang.String.format(java.util.Locale.getDefault(), "%s (%d)", activity.getString(android.R.string.ok), (millisUntilFinished * 1000) + 1));
                    break;
                }
                default: {
                alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setText(java.lang.String.format(java.util.Locale.getDefault(), "%s (%d)", activity.getString(android.R.string.ok), (millisUntilFinished / 1000) + 1));
                break;
            }
        }
        break;
    }
}
}


@java.lang.Override
public void onFinish() {
alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setEnabled(true);
alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setText(android.R.string.ok);
}

}.start();
}


protected abstract void setUrl(java.lang.String url);


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
