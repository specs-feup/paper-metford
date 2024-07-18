package com.beemdevelopment.aegis.util;
import org.json.JSONObject;
import javax.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class JsonUtils {
    static final int MUID_STATIC = getMUID();
    private JsonUtils() {
    }


    @javax.annotation.Nullable
    public static java.lang.String optString(org.json.JSONObject obj, java.lang.String key) {
        return obj.isNull(key) ? null : obj.optString(key, null);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
