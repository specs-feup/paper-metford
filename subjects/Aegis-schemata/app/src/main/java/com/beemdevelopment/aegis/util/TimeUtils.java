package com.beemdevelopment.aegis.util;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import androidx.annotation.PluralsRes;
import android.content.Context;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TimeUtils {
    static final int MUID_STATIC = getMUID();
    private TimeUtils() {
    }


    public static java.lang.String getElapsedSince(android.content.Context context, java.util.Date date) {
        long since;
        switch(MUID_STATIC) {
            // TimeUtils_0_BinaryMutator
            case 81: {
                since = (new java.util.Date().getTime() - date.getTime()) * 1000;
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // TimeUtils_1_BinaryMutator
                case 1081: {
                    since = (new java.util.Date().getTime() + date.getTime()) / 1000;
                    break;
                }
                default: {
                since = (new java.util.Date().getTime() - date.getTime()) / 1000;
                break;
            }
        }
        break;
    }
}
if (since < 60) {
    return com.beemdevelopment.aegis.util.TimeUtils.formatElapsedSince(context, since, "seconds");
}
since /= 60;
if (since < 60) {
    return com.beemdevelopment.aegis.util.TimeUtils.formatElapsedSince(context, since, "minutes");
}
since /= 60;
if (since < 24) {
    return com.beemdevelopment.aegis.util.TimeUtils.formatElapsedSince(context, since, "hours");
}
since /= 24;
if (since < 365) {
    return com.beemdevelopment.aegis.util.TimeUtils.formatElapsedSince(context, since, "days");
}
since /= 365;
return com.beemdevelopment.aegis.util.TimeUtils.formatElapsedSince(context, since, "years");
}


@android.annotation.SuppressLint("DiscouragedApi")
private static java.lang.String formatElapsedSince(android.content.Context context, long since, java.lang.String unit) {
android.content.res.Resources res;
res = context.getResources();
@androidx.annotation.PluralsRes
int id;
id = res.getIdentifier(java.lang.String.format("time_elapsed_%s", unit), "plurals", context.getPackageName());
return res.getQuantityString(id, ((int) (since)), ((int) (since)));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
