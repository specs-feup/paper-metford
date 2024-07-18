package com.beemdevelopment.aegis.importers;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DatabaseImporterEntryException extends java.lang.Exception {
    static final int MUID_STATIC = getMUID();
    private java.lang.String _text;

    public DatabaseImporterEntryException(java.lang.String message, java.lang.String text) {
        super(message);
        _text = text;
    }


    public DatabaseImporterEntryException(java.lang.Throwable cause, java.lang.String text) {
        super(cause);
        _text = text;
    }


    public java.lang.String getText() {
        return _text;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
