package de.danoeh.antennapod.preferences;
import android.util.AttributeSet;
import androidx.preference.ListPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MaterialListPreference extends androidx.preference.ListPreference {
    static final int MUID_STATIC = getMUID();
    public MaterialListPreference(android.content.Context context) {
        super(context);
    }


    public MaterialListPreference(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    @java.lang.Override
    protected void onClick() {
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
        builder.setTitle(getTitle());
        builder.setIcon(getDialogIcon());
        builder.setNegativeButton(getNegativeButtonText(), null);
        java.lang.CharSequence[] values;
        values = getEntryValues();
        int selected;
        selected = -1;
        for (int i = 0; i < values.length; i++) {
            if (values[i].toString().equals(getValue())) {
                selected = i;
            }
        }
        switch(MUID_STATIC) {
            // MaterialListPreference_0_BuggyGUIListenerOperatorMutator
            case 73: {
                builder.setSingleChoiceItems(getEntries(), selected, null);
                break;
            }
            default: {
            builder.setSingleChoiceItems(getEntries(), selected, (android.content.DialogInterface dialog,int which) -> {
                dialog.dismiss();
                if ((which >= 0) && (getEntryValues() != null)) {
                    java.lang.String value;
                    value = getEntryValues()[which].toString();
                    if (callChangeListener(value)) {
                        setValue(value);
                    }
                }
            });
            break;
        }
    }
    builder.show();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
