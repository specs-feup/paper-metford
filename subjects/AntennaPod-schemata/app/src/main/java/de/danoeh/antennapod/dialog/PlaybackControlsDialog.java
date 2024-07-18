package de.danoeh.antennapod.dialog;
import androidx.appcompat.app.AlertDialog;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import android.widget.CheckBox;
import android.os.Looper;
import de.danoeh.antennapod.R;
import android.widget.Button;
import androidx.annotation.NonNull;
import android.app.Dialog;
import de.danoeh.antennapod.core.util.playback.PlaybackController;
import java.util.List;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PlaybackControlsDialog extends androidx.fragment.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    private de.danoeh.antennapod.core.util.playback.PlaybackController controller;

    private androidx.appcompat.app.AlertDialog dialog;

    public static de.danoeh.antennapod.dialog.PlaybackControlsDialog newInstance() {
        android.os.Bundle arguments;
        arguments = new android.os.Bundle();
        de.danoeh.antennapod.dialog.PlaybackControlsDialog dialog;
        dialog = new de.danoeh.antennapod.dialog.PlaybackControlsDialog();
        dialog.setArguments(arguments);
        return dialog;
    }


    public PlaybackControlsDialog() {
        // Empty constructor required for DialogFragment
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        controller = new de.danoeh.antennapod.core.util.playback.PlaybackController(getActivity()) {
            @java.lang.Override
            public void loadMediaInfo() {
                setupUi();
                setupAudioTracks();
            }

        };
        controller.init();
        setupUi();
    }


    @java.lang.Override
    public void onStop() {
        super.onStop();
        controller.release();
        controller = null;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        dialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext()).setTitle(de.danoeh.antennapod.R.string.audio_controls).setView(de.danoeh.antennapod.R.layout.audio_controls).setPositiveButton(de.danoeh.antennapod.R.string.close_label, null).create();
        return dialog;
    }


    private void setupUi() {
        final android.widget.CheckBox skipSilence;
        switch(MUID_STATIC) {
            // PlaybackControlsDialog_0_InvalidViewFocusOperatorMutator
            case 51: {
                /**
                * Inserted by Kadabra
                */
                skipSilence = dialog.findViewById(de.danoeh.antennapod.R.id.skipSilence);
                skipSilence.requestFocus();
                break;
            }
            // PlaybackControlsDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1051: {
                /**
                * Inserted by Kadabra
                */
                skipSilence = dialog.findViewById(de.danoeh.antennapod.R.id.skipSilence);
                skipSilence.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            skipSilence = dialog.findViewById(de.danoeh.antennapod.R.id.skipSilence);
            break;
        }
    }
    skipSilence.setChecked(de.danoeh.antennapod.storage.preferences.UserPreferences.isSkipSilence());
    skipSilence.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> {
        de.danoeh.antennapod.storage.preferences.UserPreferences.setSkipSilence(isChecked);
        controller.setSkipSilence(isChecked);
    });
}


private void setupAudioTracks() {
    java.util.List<java.lang.String> audioTracks;
    audioTracks = controller.getAudioTracks();
    int selectedAudioTrack;
    selectedAudioTrack = controller.getSelectedAudioTrack();
    final android.widget.Button butAudioTracks;
    switch(MUID_STATIC) {
        // PlaybackControlsDialog_2_InvalidViewFocusOperatorMutator
        case 2051: {
            /**
            * Inserted by Kadabra
            */
            butAudioTracks = dialog.findViewById(de.danoeh.antennapod.R.id.audio_tracks);
            butAudioTracks.requestFocus();
            break;
        }
        // PlaybackControlsDialog_3_ViewComponentNotVisibleOperatorMutator
        case 3051: {
            /**
            * Inserted by Kadabra
            */
            butAudioTracks = dialog.findViewById(de.danoeh.antennapod.R.id.audio_tracks);
            butAudioTracks.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        butAudioTracks = dialog.findViewById(de.danoeh.antennapod.R.id.audio_tracks);
        break;
    }
}
if ((audioTracks.size() < 2) || (selectedAudioTrack < 0)) {
    butAudioTracks.setVisibility(android.view.View.GONE);
    return;
}
butAudioTracks.setVisibility(android.view.View.VISIBLE);
butAudioTracks.setText(audioTracks.get(selectedAudioTrack));
switch(MUID_STATIC) {
    // PlaybackControlsDialog_4_BuggyGUIListenerOperatorMutator
    case 4051: {
        butAudioTracks.setOnClickListener(null);
        break;
    }
    default: {
    butAudioTracks.setOnClickListener((android.view.View v) -> {
        switch(MUID_STATIC) {
            // PlaybackControlsDialog_5_BinaryMutator
            case 5051: {
                controller.setAudioTrack((selectedAudioTrack - 1) % audioTracks.size());
                break;
            }
            default: {
            controller.setAudioTrack((selectedAudioTrack + 1) % audioTracks.size());
            break;
        }
    }
    new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(this::setupAudioTracks, 500);
});
break;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
