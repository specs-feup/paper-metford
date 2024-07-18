package com.beemdevelopment.aegis.ui.preferences;
import androidx.preference.Preference;
import android.util.AttributeSet;
import androidx.preference.SwitchPreferenceCompat;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SwitchPreference extends androidx.preference.SwitchPreferenceCompat {
    static final int MUID_STATIC = getMUID();
    private androidx.preference.Preference.OnPreferenceChangeListener _listener;

    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
    public SwitchPreference(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public SwitchPreference(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public SwitchPreference(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public SwitchPreference(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    public void setOnPreferenceChangeListener(androidx.preference.Preference.OnPreferenceChangeListener listener) {
        super.setOnPreferenceChangeListener(listener);
        _listener = listener;
    }


    @java.lang.Override
    public void setChecked(boolean checked) {
        setChecked(true, false);
    }


    public void setChecked(boolean checked, boolean silent) {
        if (silent) {
            super.setOnPreferenceChangeListener(null);
        }
        super.setChecked(checked);
        if (silent) {
            super.setOnPreferenceChangeListener(_listener);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
