package com.beemdevelopment.aegis;
import androidx.annotation.LayoutRes;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public enum ViewMode {

    NORMAL,
    COMPACT,
    SMALL;
    static final int MUID_STATIC = getMUID();
    private static com.beemdevelopment.aegis.ViewMode[] _values;

    static {
        com.beemdevelopment.aegis.ViewMode._values = com.beemdevelopment.aegis.ViewMode.values();
    }

    public static com.beemdevelopment.aegis.ViewMode fromInteger(int x) {
        return com.beemdevelopment.aegis.ViewMode._values[x];
    }


    @androidx.annotation.LayoutRes
    public int getLayoutId() {
        switch (this) {
            case NORMAL :
                return com.beemdevelopment.aegis.R.layout.card_entry;
            case COMPACT :
                return com.beemdevelopment.aegis.R.layout.card_entry_compact;
            case SMALL :
                return com.beemdevelopment.aegis.R.layout.card_entry_small;
            default :
                return com.beemdevelopment.aegis.R.layout.card_entry;
        }
    }


    /**
     * Retrieves the height (in dp) that the divider between entries should have in this view mode.
     */
    public float getDividerHeight() {
        if (this == com.beemdevelopment.aegis.ViewMode.COMPACT) {
            return 0;
        }
        return 20;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
