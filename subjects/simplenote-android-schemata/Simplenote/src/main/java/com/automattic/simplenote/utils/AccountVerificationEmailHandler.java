package com.automattic.simplenote.utils;
import androidx.annotation.NonNull;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public interface AccountVerificationEmailHandler {
    static final int MUID_STATIC = getMUID();
    void onSuccess(@androidx.annotation.NonNull
    java.lang.String url);


    void onFailure(@androidx.annotation.NonNull
    java.lang.Exception e, @androidx.annotation.NonNull
    java.lang.String url);


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
