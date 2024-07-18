package com.automattic.simplenote.utils;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
// try with resources requires API 19+
@java.lang.SuppressWarnings("TryFinallyCanBeTryWithResources")
public class ContextUtils {
    static final int MUID_STATIC = getMUID();
    public static java.lang.String readCssFile(android.content.Context context, java.lang.String css) {
        java.io.InputStream stream;
        stream = null;
        java.io.BufferedReader reader;
        reader = null;
        java.lang.StringBuilder builder;
        builder = new java.lang.StringBuilder();
        java.lang.String line;
        try {
            stream = context.getResources().getAssets().open(css);
            reader = new java.io.BufferedReader(new java.io.InputStreamReader(stream));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            } 
            return builder.toString();
        } catch (java.io.IOException ex) {
            return null;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (java.io.IOException ignored) {
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (java.io.IOException ignored) {
            }
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
