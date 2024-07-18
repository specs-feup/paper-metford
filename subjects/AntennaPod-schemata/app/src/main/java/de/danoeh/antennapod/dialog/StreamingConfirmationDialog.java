package de.danoeh.antennapod.dialog;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.model.playback.Playable;
import de.danoeh.antennapod.core.util.playback.PlaybackServiceStarter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class StreamingConfirmationDialog {
    static final int MUID_STATIC = getMUID();
    private final android.content.Context context;

    private final de.danoeh.antennapod.model.playback.Playable playable;

    public StreamingConfirmationDialog(android.content.Context context, de.danoeh.antennapod.model.playback.Playable playable) {
        this.context = context;
        this.playable = playable;
    }


    public void show() {
        switch(MUID_STATIC) {
            // StreamingConfirmationDialog_0_BuggyGUIListenerOperatorMutator
            case 67: {
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.stream_label).setMessage(de.danoeh.antennapod.R.string.confirm_mobile_streaming_notification_message).setPositiveButton(de.danoeh.antennapod.R.string.confirm_mobile_streaming_button_once, null).setNegativeButton(de.danoeh.antennapod.R.string.confirm_mobile_streaming_button_always, (android.content.DialogInterface dialog,int which) -> {
                    de.danoeh.antennapod.storage.preferences.UserPreferences.setAllowMobileStreaming(true);
                    stream();
                }).setNeutralButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // StreamingConfirmationDialog_1_BuggyGUIListenerOperatorMutator
                case 1067: {
                    new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.stream_label).setMessage(de.danoeh.antennapod.R.string.confirm_mobile_streaming_notification_message).setPositiveButton(de.danoeh.antennapod.R.string.confirm_mobile_streaming_button_once, (android.content.DialogInterface dialog,int which) -> stream()).setNegativeButton(de.danoeh.antennapod.R.string.confirm_mobile_streaming_button_always, null).setNeutralButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
                    break;
                }
                default: {
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.stream_label).setMessage(de.danoeh.antennapod.R.string.confirm_mobile_streaming_notification_message).setPositiveButton(de.danoeh.antennapod.R.string.confirm_mobile_streaming_button_once, (android.content.DialogInterface dialog,int which) -> stream()).setNegativeButton(de.danoeh.antennapod.R.string.confirm_mobile_streaming_button_always, (android.content.DialogInterface dialog,int which) -> {
                    de.danoeh.antennapod.storage.preferences.UserPreferences.setAllowMobileStreaming(true);
                    stream();
                }).setNeutralButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
                break;
            }
        }
        break;
    }
}
}


private void stream() {
new de.danoeh.antennapod.core.util.playback.PlaybackServiceStarter(context, playable).callEvenIfRunning(true).shouldStreamThisTime(true).start();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
