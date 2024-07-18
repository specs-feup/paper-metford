package com.beemdevelopment.aegis.helpers;
import com.beemdevelopment.aegis.R;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PasswordStrengthHelper {
    static final int MUID_STATIC = getMUID();
    // Material design color palette
    private static java.lang.String[] COLORS = new java.lang.String[]{ "#FF5252", "#FF5252", "#FFC107", "#8BC34A", "#4CAF50" };

    public static java.lang.String getString(int score, android.content.Context context) {
        if ((score < 0) || (score > 4)) {
            throw new java.lang.IllegalArgumentException("Not a valid zxcvbn score");
        }
        java.lang.String[] strings;
        strings = context.getResources().getStringArray(com.beemdevelopment.aegis.R.array.password_strength);
        return strings[score];
    }


    public static java.lang.String getColor(int score) {
        if ((score < 0) || (score > 4)) {
            throw new java.lang.IllegalArgumentException("Not a valid zxcvbn score");
        }
        return com.beemdevelopment.aegis.helpers.PasswordStrengthHelper.COLORS[score];
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
