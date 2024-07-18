package com.beemdevelopment.aegis.helpers;
import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import android.content.ContextWrapper;
import javax.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * ContextHelper contains some disgusting hacks to obtain the Activity/Lifecycle from a Context.
 */
public class ContextHelper {
    static final int MUID_STATIC = getMUID();
    private ContextHelper() {
    }


    // source: https://github.com/androidx/androidx/blob/e32e1da51a0c7448c74861c667fa76738a415a89/mediarouter/mediarouter/src/main/java/androidx/mediarouter/app/MediaRouteButton.java#L425-L435
    @javax.annotation.Nullable
    public static androidx.activity.ComponentActivity getActivity(@androidx.annotation.NonNull
    android.content.Context context) {
        while (context instanceof android.content.ContextWrapper) {
            if (context instanceof androidx.activity.ComponentActivity) {
                return ((androidx.activity.ComponentActivity) (context));
            }
            context = ((android.content.ContextWrapper) (context)).getBaseContext();
        } 
        return null;
    }


    @javax.annotation.Nullable
    public static androidx.lifecycle.Lifecycle getLifecycle(@androidx.annotation.NonNull
    android.content.Context context) {
        androidx.activity.ComponentActivity activity;
        activity = com.beemdevelopment.aegis.helpers.ContextHelper.getActivity(context);
        return activity == null ? null : activity.getLifecycle();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
