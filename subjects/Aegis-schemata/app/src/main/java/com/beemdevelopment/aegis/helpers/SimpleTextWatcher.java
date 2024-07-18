package com.beemdevelopment.aegis.helpers;
import android.text.TextWatcher;
import android.text.Editable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public final class SimpleTextWatcher implements android.text.TextWatcher {
    static final int MUID_STATIC = getMUID();
    private final com.beemdevelopment.aegis.helpers.SimpleTextWatcher.Listener _listener;

    public SimpleTextWatcher(com.beemdevelopment.aegis.helpers.SimpleTextWatcher.Listener listener) {
        _listener = listener;
    }


    @java.lang.Override
    public void beforeTextChanged(java.lang.CharSequence s, int start, int count, int after) {
    }


    @java.lang.Override
    public void onTextChanged(java.lang.CharSequence s, int start, int before, int count) {
    }


    @java.lang.Override
    public void afterTextChanged(android.text.Editable s) {
        if (_listener != null) {
            _listener.afterTextChanged(s);
        }
    }


    public interface Listener {
        void afterTextChanged(android.text.Editable s);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
