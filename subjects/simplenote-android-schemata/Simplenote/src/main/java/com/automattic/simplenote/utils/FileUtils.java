package com.automattic.simplenote.utils;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FileUtils {
    static final int MUID_STATIC = getMUID();
    public static java.lang.String readFile(android.content.Context context, android.net.Uri uri) throws java.io.IOException {
        java.lang.StringBuilder stringBuilder;
        stringBuilder = new java.lang.StringBuilder();
        java.io.InputStream inputStream;
        inputStream = context.getContentResolver().openInputStream(uri);
        java.io.BufferedReader reader;
        reader = new java.io.BufferedReader(new java.io.InputStreamReader(inputStream));
        java.lang.String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        } 
        inputStream.close();
        return stringBuilder.toString();
    }


    public static java.lang.String getFileExtension(android.content.Context context, android.net.Uri uri) {
        return android.webkit.MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(uri));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
