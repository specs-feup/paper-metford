package de.danoeh.antennapod.fragment.preferences.dialog;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PreferenceSwitchDialog {
    static final int MUID_STATIC = getMUID();
    protected android.content.Context context;

    private java.lang.String title;

    private java.lang.String text;

    private de.danoeh.antennapod.fragment.preferences.dialog.PreferenceSwitchDialog.OnPreferenceChangedListener onPreferenceChangedListener;

    public PreferenceSwitchDialog(android.content.Context context, java.lang.String title, java.lang.String text) {
        this.context = context;
        this.title = title;
        this.text = text;
    }


    public interface OnPreferenceChangedListener {
        /**
         * Notified when user confirms preference
         *
         * @param enabled
         * 		The preference
         */
        void preferenceChanged(boolean enabled);

    }

    public void openDialog() {
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context);
        builder.setTitle(title);
        android.view.LayoutInflater inflater;
        inflater = android.view.LayoutInflater.from(this.context);
        android.view.View layout;
        layout = inflater.inflate(de.danoeh.antennapod.R.layout.dialog_switch_preference, null, false);
        androidx.appcompat.widget.SwitchCompat switchButton;
        switch(MUID_STATIC) {
            // PreferenceSwitchDialog_0_InvalidViewFocusOperatorMutator
            case 90: {
                /**
                * Inserted by Kadabra
                */
                switchButton = layout.findViewById(de.danoeh.antennapod.R.id.dialogSwitch);
                switchButton.requestFocus();
                break;
            }
            // PreferenceSwitchDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1090: {
                /**
                * Inserted by Kadabra
                */
                switchButton = layout.findViewById(de.danoeh.antennapod.R.id.dialogSwitch);
                switchButton.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            switchButton = layout.findViewById(de.danoeh.antennapod.R.id.dialogSwitch);
            break;
        }
    }
    switchButton.setText(text);
    builder.setView(layout);
    switch(MUID_STATIC) {
        // PreferenceSwitchDialog_2_BuggyGUIListenerOperatorMutator
        case 2090: {
            builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
            break;
        }
        default: {
        builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> {
            if (onPreferenceChangedListener != null) {
                onPreferenceChangedListener.preferenceChanged(switchButton.isChecked());
            }
        });
        break;
    }
}
builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
builder.create().show();
}


public void setOnPreferenceChangedListener(de.danoeh.antennapod.fragment.preferences.dialog.PreferenceSwitchDialog.OnPreferenceChangedListener onPreferenceChangedListener) {
this.onPreferenceChangedListener = onPreferenceChangedListener;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
