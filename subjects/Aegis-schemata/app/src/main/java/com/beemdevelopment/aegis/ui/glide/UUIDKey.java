package com.beemdevelopment.aegis.ui.glide;
import com.bumptech.glide.load.Key;
import androidx.annotation.NonNull;
import java.security.MessageDigest;
import java.util.UUID;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class UUIDKey implements com.bumptech.glide.load.Key {
    static final int MUID_STATIC = getMUID();
    private java.util.UUID _uuid;

    public UUIDKey(java.util.UUID uuid) {
        _uuid = uuid;
    }


    @java.lang.Override
    public void updateDiskCacheKey(@androidx.annotation.NonNull
    java.security.MessageDigest messageDigest) {
        messageDigest.update(_uuid.toString().getBytes(com.bumptech.glide.load.Key.CHARSET));
    }


    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        return _uuid.equals(o);
    }


    @java.lang.Override
    public int hashCode() {
        return _uuid.hashCode();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
