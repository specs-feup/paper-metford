package de.danoeh.antennapod.activity;
import androidx.annotation.NonNull;
import android.content.DialogInterface;
import de.danoeh.antennapod.core.preferences.ThemeSwitcher;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import de.danoeh.antennapod.dialog.VariableSpeedDialog;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PlaybackSpeedDialogActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        setTheme(de.danoeh.antennapod.core.preferences.ThemeSwitcher.getTranslucentTheme(this));
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // PlaybackSpeedDialogActivity_0_LengthyGUICreationOperatorMutator
            case 146: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    de.danoeh.antennapod.dialog.VariableSpeedDialog speedDialog;
    speedDialog = new de.danoeh.antennapod.activity.PlaybackSpeedDialogActivity.InnerVariableSpeedDialog();
    speedDialog.show(getSupportFragmentManager(), null);
}


public static class InnerVariableSpeedDialog extends de.danoeh.antennapod.dialog.VariableSpeedDialog {
    @java.lang.Override
    public void onDismiss(@androidx.annotation.NonNull
    android.content.DialogInterface dialog) {
        super.onDismiss(dialog);
        getActivity().finish();
    }

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
