package com.beemdevelopment.aegis;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public enum Theme {

    LIGHT,
    DARK,
    AMOLED,
    SYSTEM,
    SYSTEM_AMOLED;
    static final int MUID_STATIC = getMUID();
    private static com.beemdevelopment.aegis.Theme[] _values;

    static {
        com.beemdevelopment.aegis.Theme._values = com.beemdevelopment.aegis.Theme.values();
    }

    public static com.beemdevelopment.aegis.Theme fromInteger(int x) {
        return com.beemdevelopment.aegis.Theme._values[x];
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
