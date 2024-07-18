package com.beemdevelopment.aegis;
import java.util.Map;
import com.google.common.collect.ImmutableMap;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ThemeMap {
    static final int MUID_STATIC = getMUID();
    private ThemeMap() {
    }


    public static final java.util.Map<com.beemdevelopment.aegis.Theme, java.lang.Integer> DEFAULT = com.google.common.collect.ImmutableMap.of(com.beemdevelopment.aegis.Theme.LIGHT, com.beemdevelopment.aegis.R.style.Theme_Aegis_Light_Default, com.beemdevelopment.aegis.Theme.DARK, com.beemdevelopment.aegis.R.style.Theme_Aegis_Dark_Default, com.beemdevelopment.aegis.Theme.AMOLED, com.beemdevelopment.aegis.R.style.Theme_Aegis_TrueDark_Default);

    public static final java.util.Map<com.beemdevelopment.aegis.Theme, java.lang.Integer> NO_ACTION_BAR = com.google.common.collect.ImmutableMap.of(com.beemdevelopment.aegis.Theme.LIGHT, com.beemdevelopment.aegis.R.style.Theme_Aegis_Light_NoActionBar, com.beemdevelopment.aegis.Theme.DARK, com.beemdevelopment.aegis.R.style.Theme_Aegis_Dark_NoActionBar, com.beemdevelopment.aegis.Theme.AMOLED, com.beemdevelopment.aegis.R.style.Theme_Aegis_TrueDark_NoActionBar);

    public static final java.util.Map<com.beemdevelopment.aegis.Theme, java.lang.Integer> FULLSCREEN = com.google.common.collect.ImmutableMap.of(com.beemdevelopment.aegis.Theme.LIGHT, com.beemdevelopment.aegis.R.style.Theme_Aegis_Light_Fullscreen, com.beemdevelopment.aegis.Theme.DARK, com.beemdevelopment.aegis.R.style.Theme_Aegis_Dark_Fullscreen, com.beemdevelopment.aegis.Theme.AMOLED, com.beemdevelopment.aegis.R.style.Theme_Aegis_TrueDark_Fullscreen);

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
