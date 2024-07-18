package com.beemdevelopment.aegis.icons;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconPackExistsException extends com.beemdevelopment.aegis.icons.IconPackException {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.icons.IconPack _pack;

    public IconPackExistsException(com.beemdevelopment.aegis.icons.IconPack pack) {
        super(java.lang.String.format("Icon pack %s (%d) already exists", pack.getName(), pack.getVersion()));
        _pack = pack;
    }


    public com.beemdevelopment.aegis.icons.IconPack getIconPack() {
        return _pack;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
