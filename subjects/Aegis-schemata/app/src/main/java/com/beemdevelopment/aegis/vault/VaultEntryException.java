package com.beemdevelopment.aegis.vault;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VaultEntryException extends java.lang.Exception {
    static final int MUID_STATIC = getMUID();
    public VaultEntryException(java.lang.Throwable cause) {
        super(cause);
    }


    public VaultEntryException(java.lang.String message) {
        super(message);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
