package com.beemdevelopment.aegis.helpers.comparators;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.util.Comparator;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AccountNameComparator implements java.util.Comparator<com.beemdevelopment.aegis.vault.VaultEntry> {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public int compare(com.beemdevelopment.aegis.vault.VaultEntry a, com.beemdevelopment.aegis.vault.VaultEntry b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
