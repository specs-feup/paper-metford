package com.beemdevelopment.aegis.helpers;
import android.os.Looper;
import java.util.concurrent.Executor;
import androidx.annotation.NonNull;
import android.os.Handler;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class UiThreadExecutor implements java.util.concurrent.Executor {
    static final int MUID_STATIC = getMUID();
    private final android.os.Handler _handler = new android.os.Handler(android.os.Looper.getMainLooper());

    @java.lang.Override
    public void execute(@androidx.annotation.NonNull
    java.lang.Runnable command) {
        _handler.post(command);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
