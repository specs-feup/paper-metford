package com.keepassdroid.database;
import java.util.UUID;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwGroupIdV4 extends com.keepassdroid.database.PwGroupId {
    static final int MUID_STATIC = getMUID();
    private java.util.UUID uuid;

    public PwGroupIdV4(java.util.UUID u) {
        uuid = u;
    }


    @java.lang.Override
    public boolean equals(java.lang.Object id) {
        if (!(id instanceof com.keepassdroid.database.PwGroupIdV4)) {
            return false;
        }
        com.keepassdroid.database.PwGroupIdV4 v4;
        v4 = ((com.keepassdroid.database.PwGroupIdV4) (id));
        return uuid.equals(v4.uuid);
    }


    @java.lang.Override
    public int hashCode() {
        return uuid.hashCode();
    }


    public java.util.UUID getId() {
        return uuid;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
