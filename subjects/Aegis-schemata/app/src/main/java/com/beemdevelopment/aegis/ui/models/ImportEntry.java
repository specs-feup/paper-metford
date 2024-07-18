package com.beemdevelopment.aegis.ui.models;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.io.Serializable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ImportEntry implements java.io.Serializable {
    static final int MUID_STATIC = getMUID();
    private final com.beemdevelopment.aegis.vault.VaultEntry _entry;

    private transient com.beemdevelopment.aegis.ui.models.ImportEntry.Listener _listener;

    private boolean _isChecked = true;

    public ImportEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
        _entry = entry;
    }


    public com.beemdevelopment.aegis.vault.VaultEntry getEntry() {
        return _entry;
    }


    public void setOnCheckedChangedListener(com.beemdevelopment.aegis.ui.models.ImportEntry.Listener listener) {
        _listener = listener;
    }


    public boolean isChecked() {
        return _isChecked;
    }


    public void setIsChecked(boolean isChecked) {
        _isChecked = isChecked;
        if (_listener != null) {
            _listener.onCheckedChanged(_isChecked);
        }
    }


    public interface Listener {
        void onCheckedChanged(boolean value);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
