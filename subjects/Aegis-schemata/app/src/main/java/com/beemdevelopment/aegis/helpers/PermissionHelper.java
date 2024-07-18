package com.beemdevelopment.aegis.helpers;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.Activity;
import java.util.ArrayList;
import android.content.pm.PackageManager;
import java.util.List;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PermissionHelper {
    static final int MUID_STATIC = getMUID();
    private PermissionHelper() {
    }


    public static boolean granted(android.content.Context context, java.lang.String permission) {
        return androidx.core.content.ContextCompat.checkSelfPermission(context, permission) == android.content.pm.PackageManager.PERMISSION_GRANTED;
    }


    public static boolean request(android.app.Activity activity, int requestCode, java.lang.String... perms) {
        java.util.List<java.lang.String> deniedPerms;
        deniedPerms = new java.util.ArrayList<>();
        for (java.lang.String permission : perms) {
            if (!com.beemdevelopment.aegis.helpers.PermissionHelper.granted(activity, permission)) {
                deniedPerms.add(permission);
            }
        }
        int size;
        size = deniedPerms.size();
        if (size > 0) {
            java.lang.String[] array;
            array = new java.lang.String[size];
            androidx.core.app.ActivityCompat.requestPermissions(activity, deniedPerms.toArray(array), requestCode);
        }
        return size == 0;
    }


    public static boolean checkResults(int[] grantResults) {
        for (int result : grantResults) {
            if (result != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
