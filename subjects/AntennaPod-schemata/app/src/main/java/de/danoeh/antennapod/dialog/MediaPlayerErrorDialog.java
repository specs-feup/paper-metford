package de.danoeh.antennapod.dialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import de.danoeh.antennapod.event.PlayerErrorEvent;
import de.danoeh.antennapod.R;
import android.text.style.ForegroundColorSpan;
import android.app.Activity;
import de.danoeh.antennapod.activity.MainActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.text.Spannable;
import android.text.SpannableString;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MediaPlayerErrorDialog {
    static final int MUID_STATIC = getMUID();
    public static void show(android.app.Activity activity, de.danoeh.antennapod.event.PlayerErrorEvent event) {
        final com.google.android.material.dialog.MaterialAlertDialogBuilder errorDialog;
        errorDialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(activity);
        errorDialog.setTitle(de.danoeh.antennapod.R.string.error_label);
        java.lang.String genericMessage;
        genericMessage = activity.getString(de.danoeh.antennapod.R.string.playback_error_generic);
        android.text.SpannableString errorMessage;
        errorMessage = new android.text.SpannableString((genericMessage + "\n\n") + event.getMessage());
        errorMessage.setSpan(new android.text.style.ForegroundColorSpan(0x88888888), genericMessage.length(), errorMessage.length(), android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        errorDialog.setMessage(errorMessage);
        switch(MUID_STATIC) {
            // MediaPlayerErrorDialog_0_BuggyGUIListenerOperatorMutator
            case 48: {
                errorDialog.setPositiveButton(android.R.string.ok, null);
                break;
            }
            default: {
            errorDialog.setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> {
                if (activity instanceof de.danoeh.antennapod.activity.MainActivity) {
                    ((de.danoeh.antennapod.activity.MainActivity) (activity)).getBottomSheet().setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
                }
            });
            break;
        }
    }
    errorDialog.create().show();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
