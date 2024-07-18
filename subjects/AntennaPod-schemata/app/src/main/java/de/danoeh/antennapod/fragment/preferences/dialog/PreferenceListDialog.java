package de.danoeh.antennapod.fragment.preferences.dialog;
import de.danoeh.antennapod.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PreferenceListDialog {
    static final int MUID_STATIC = getMUID();
    protected android.content.Context context;

    private java.lang.String title;

    private de.danoeh.antennapod.fragment.preferences.dialog.PreferenceListDialog.OnPreferenceChangedListener onPreferenceChangedListener;

    private int selectedPos = 0;

    public PreferenceListDialog(android.content.Context context, java.lang.String title) {
        this.context = context;
        this.title = title;
    }


    public interface OnPreferenceChangedListener {
        /**
         * Notified when user confirms preference
         *
         * @param pos
         * 		The index of the item that was selected
         */
        void preferenceChanged(int pos);

    }

    public void openDialog(java.lang.String[] items) {
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context);
        builder.setTitle(title);
        switch(MUID_STATIC) {
            // PreferenceListDialog_0_BuggyGUIListenerOperatorMutator
            case 89: {
                builder.setSingleChoiceItems(items, selectedPos, null);
                break;
            }
            default: {
            builder.setSingleChoiceItems(items, selectedPos, (android.content.DialogInterface dialog,int which) -> {
                selectedPos = which;
            });
            break;
        }
    }
    switch(MUID_STATIC) {
        // PreferenceListDialog_1_BuggyGUIListenerOperatorMutator
        case 1089: {
            builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
            break;
        }
        default: {
        builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> {
            if ((onPreferenceChangedListener != null) && (selectedPos >= 0)) {
                onPreferenceChangedListener.preferenceChanged(selectedPos);
            }
        });
        break;
    }
}
builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
builder.create().show();
}


public void setOnPreferenceChangedListener(de.danoeh.antennapod.fragment.preferences.dialog.PreferenceListDialog.OnPreferenceChangedListener onPreferenceChangedListener) {
this.onPreferenceChangedListener = onPreferenceChangedListener;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
