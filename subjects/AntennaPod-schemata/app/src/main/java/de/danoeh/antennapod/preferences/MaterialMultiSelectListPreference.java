package de.danoeh.antennapod.preferences;
import java.util.Set;
import android.util.AttributeSet;
import android.content.DialogInterface;
import androidx.preference.MultiSelectListPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.HashSet;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MaterialMultiSelectListPreference extends androidx.preference.MultiSelectListPreference {
    static final int MUID_STATIC = getMUID();
    public MaterialMultiSelectListPreference(android.content.Context context) {
        super(context);
    }


    public MaterialMultiSelectListPreference(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    @java.lang.Override
    protected void onClick() {
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
        builder.setTitle(getTitle());
        builder.setIcon(getDialogIcon());
        builder.setNegativeButton(getNegativeButtonText(), null);
        boolean[] selected;
        selected = new boolean[getEntries().length];
        java.lang.CharSequence[] values;
        values = getEntryValues();
        for (int i = 0; i < values.length; i++) {
            selected[i] = getValues().contains(values[i].toString());
        }
        builder.setMultiChoiceItems(getEntries(), selected, (android.content.DialogInterface dialog,int which,boolean isChecked) -> selected[which] = isChecked);
        switch(MUID_STATIC) {
            // MaterialMultiSelectListPreference_0_BuggyGUIListenerOperatorMutator
            case 76: {
                builder.setPositiveButton(android.R.string.ok, null);
                break;
            }
            default: {
            builder.setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> {
                java.util.Set<java.lang.String> selectedValues;
                selectedValues = new java.util.HashSet<>();
                for (int i = 0; i < values.length; i++) {
                    if (selected[i]) {
                        selectedValues.add(getEntryValues()[i].toString());
                    }
                }
                setValues(selectedValues);
            });
            break;
        }
    }
    builder.show();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
