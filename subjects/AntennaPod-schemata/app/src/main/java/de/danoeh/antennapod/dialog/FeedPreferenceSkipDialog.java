package de.danoeh.antennapod.dialog;
import de.danoeh.antennapod.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import android.content.Context;
import android.widget.EditText;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays a dialog with a username and password text field and an optional checkbox to save username and preferences.
 */
public abstract class FeedPreferenceSkipDialog extends com.google.android.material.dialog.MaterialAlertDialogBuilder {
    static final int MUID_STATIC = getMUID();
    public FeedPreferenceSkipDialog(android.content.Context context, int skipIntroInitialValue, int skipEndInitialValue) {
        super(context);
        setTitle(de.danoeh.antennapod.R.string.pref_feed_skip);
        android.view.View rootView;
        rootView = android.view.View.inflate(context, de.danoeh.antennapod.R.layout.feed_pref_skip_dialog, null);
        setView(rootView);
        final android.widget.EditText etxtSkipIntro;
        switch(MUID_STATIC) {
            // FeedPreferenceSkipDialog_0_InvalidViewFocusOperatorMutator
            case 59: {
                /**
                * Inserted by Kadabra
                */
                etxtSkipIntro = rootView.findViewById(de.danoeh.antennapod.R.id.etxtSkipIntro);
                etxtSkipIntro.requestFocus();
                break;
            }
            // FeedPreferenceSkipDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1059: {
                /**
                * Inserted by Kadabra
                */
                etxtSkipIntro = rootView.findViewById(de.danoeh.antennapod.R.id.etxtSkipIntro);
                etxtSkipIntro.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            etxtSkipIntro = rootView.findViewById(de.danoeh.antennapod.R.id.etxtSkipIntro);
            break;
        }
    }
    final android.widget.EditText etxtSkipEnd;
    switch(MUID_STATIC) {
        // FeedPreferenceSkipDialog_2_InvalidViewFocusOperatorMutator
        case 2059: {
            /**
            * Inserted by Kadabra
            */
            etxtSkipEnd = rootView.findViewById(de.danoeh.antennapod.R.id.etxtSkipEnd);
            etxtSkipEnd.requestFocus();
            break;
        }
        // FeedPreferenceSkipDialog_3_ViewComponentNotVisibleOperatorMutator
        case 3059: {
            /**
            * Inserted by Kadabra
            */
            etxtSkipEnd = rootView.findViewById(de.danoeh.antennapod.R.id.etxtSkipEnd);
            etxtSkipEnd.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        etxtSkipEnd = rootView.findViewById(de.danoeh.antennapod.R.id.etxtSkipEnd);
        break;
    }
}
etxtSkipIntro.setText(java.lang.String.valueOf(skipIntroInitialValue));
etxtSkipEnd.setText(java.lang.String.valueOf(skipEndInitialValue));
setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
switch(MUID_STATIC) {
    // FeedPreferenceSkipDialog_4_BuggyGUIListenerOperatorMutator
    case 4059: {
        setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
        break;
    }
    default: {
    setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> {
        int skipIntro;
        int skipEnding;
        try {
            skipIntro = java.lang.Integer.parseInt(etxtSkipIntro.getText().toString());
        } catch (java.lang.NumberFormatException e) {
            skipIntro = 0;
        }
        try {
            skipEnding = java.lang.Integer.parseInt(etxtSkipEnd.getText().toString());
        } catch (java.lang.NumberFormatException e) {
            skipEnding = 0;
        }
        onConfirmed(skipIntro, skipEnding);
    });
    break;
}
}
}


protected abstract void onConfirmed(int skipIntro, int skipEndig);


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
