package de.danoeh.antennapod.dialog;
import androidx.appcompat.app.AlertDialog;
import java.util.Locale;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.widget.TextView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.text.NumberFormat;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Shows the dialog that allows setting the skip time.
 */
public class SkipPreferenceDialog {
    static final int MUID_STATIC = getMUID();
    public static void showSkipPreference(android.content.Context context, de.danoeh.antennapod.dialog.SkipPreferenceDialog.SkipDirection direction, android.widget.TextView textView) {
        int checked;
        checked = 0;
        int skipSecs;
        if (direction == de.danoeh.antennapod.dialog.SkipPreferenceDialog.SkipDirection.SKIP_FORWARD) {
            skipSecs = de.danoeh.antennapod.storage.preferences.UserPreferences.getFastForwardSecs();
        } else {
            skipSecs = de.danoeh.antennapod.storage.preferences.UserPreferences.getRewindSecs();
        }
        final int[] values;
        values = context.getResources().getIntArray(de.danoeh.antennapod.R.array.seek_delta_values);
        final java.lang.String[] choices;
        choices = new java.lang.String[values.length];
        for (int i = 0; i < values.length; i++) {
            if (skipSecs == values[i]) {
                checked = i;
            }
            choices[i] = java.lang.String.format(java.util.Locale.getDefault(), "%d %s", values[i], context.getString(de.danoeh.antennapod.R.string.time_seconds));
        }
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context);
        builder.setTitle(direction == de.danoeh.antennapod.dialog.SkipPreferenceDialog.SkipDirection.SKIP_FORWARD ? de.danoeh.antennapod.R.string.pref_fast_forward : de.danoeh.antennapod.R.string.pref_rewind);
        switch(MUID_STATIC) {
            // SkipPreferenceDialog_0_BuggyGUIListenerOperatorMutator
            case 46: {
                builder.setSingleChoiceItems(choices, checked, null);
                break;
            }
            default: {
            builder.setSingleChoiceItems(choices, checked, (android.content.DialogInterface dialog,int which) -> {
                int choice;
                choice = ((androidx.appcompat.app.AlertDialog) (dialog)).getListView().getCheckedItemPosition();
                if ((choice < 0) || (choice >= values.length)) {
                    java.lang.System.err.printf("Choice in showSkipPreference is out of bounds %d", choice);
                } else {
                    int seconds;
                    seconds = values[choice];
                    if (direction == de.danoeh.antennapod.dialog.SkipPreferenceDialog.SkipDirection.SKIP_FORWARD) {
                        de.danoeh.antennapod.storage.preferences.UserPreferences.setFastForwardSecs(seconds);
                    } else {
                        de.danoeh.antennapod.storage.preferences.UserPreferences.setRewindSecs(seconds);
                    }
                    if (textView != null) {
                        textView.setText(java.text.NumberFormat.getInstance().format(seconds));
                    }
                    dialog.dismiss();
                }
            });
            break;
        }
    }
    builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
    builder.show();
}


public enum SkipDirection {

    SKIP_FORWARD,
    SKIP_REWIND;}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
