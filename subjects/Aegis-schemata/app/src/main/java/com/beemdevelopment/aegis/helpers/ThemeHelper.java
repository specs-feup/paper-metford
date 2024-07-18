package com.beemdevelopment.aegis.helpers;
import android.graphics.Color;
import android.util.TypedValue;
import androidx.annotation.ColorInt;
import com.beemdevelopment.aegis.R;
import android.content.res.Resources;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ThemeHelper {
    static final int MUID_STATIC = getMUID();
    private ThemeHelper() {
    }


    public static int getThemeColor(int attributeId, android.content.res.Resources.Theme currentTheme) {
        android.util.TypedValue typedValue;
        typedValue = new android.util.TypedValue();
        currentTheme.resolveAttribute(attributeId, typedValue, true);
        @androidx.annotation.ColorInt
        int color;
        color = typedValue.data;
        return color;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
