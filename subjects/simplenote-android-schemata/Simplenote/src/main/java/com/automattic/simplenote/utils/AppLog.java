package com.automattic.simplenote.utils;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AppLog {
    static final int MUID_STATIC = getMUID();
    private static final int LOG_MAX = 100;

    private static final java.util.LinkedHashMap<java.lang.Integer, java.lang.String> mQueue = new java.util.LinkedHashMap<java.lang.Integer, java.lang.String>() {
        @java.lang.Override
        protected boolean removeEldestEntry(java.util.Map.Entry<java.lang.Integer, java.lang.String> eldest) {
            return this.size() > com.automattic.simplenote.utils.AppLog.LOG_MAX;
        }

    };

    public enum Type {

        ACCOUNT,
        ACTION,
        AUTH,
        DEVICE,
        LAYOUT,
        NETWORK,
        SCREEN,
        SYNC,
        IMPORT,
        EDITOR;}

    public static void add(com.automattic.simplenote.utils.AppLog.Type type, java.lang.String message) {
        java.lang.String log;
        if ((type == com.automattic.simplenote.utils.AppLog.Type.ACCOUNT) || (type == com.automattic.simplenote.utils.AppLog.Type.DEVICE)) {
            log = message + "\n";
        } else {
            java.lang.String timestamp;
            timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", java.util.Locale.US).format(new java.util.Date());
            log = ((((timestamp + " - ") + type.toString()) + ": ") + message) + "\n";
        }
        com.automattic.simplenote.utils.AppLog.mQueue.put(com.automattic.simplenote.utils.AppLog.mQueue.size(), log);
    }


    public static java.lang.String get() {
        java.lang.StringBuilder queue;
        queue = new java.lang.StringBuilder();
        for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry : com.automattic.simplenote.utils.AppLog.mQueue.entrySet()) {
            queue.append(entry.getValue());
        }
        return queue.toString();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
