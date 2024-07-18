package com.automattic.simplenote.models;
import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@java.lang.SuppressWarnings("unused")
public class Suggestion {
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @androidx.annotation.IntDef({ com.automattic.simplenote.models.Suggestion.Type.HISTORY, com.automattic.simplenote.models.Suggestion.Type.QUERY, com.automattic.simplenote.models.Suggestion.Type.TAG })
    public @interface Type {
        int HISTORY = 0;

        int QUERY = 1;

        int TAG = 2;
    }

    static final int MUID_STATIC = getMUID();
    private java.lang.String mName;

    @com.automattic.simplenote.models.Suggestion.Type
    private int mType;

    public Suggestion(java.lang.String name, @com.automattic.simplenote.models.Suggestion.Type
    int type) {
        mName = name;
        mType = type;
    }


    public java.lang.String getName() {
        return mName;
    }


    @com.automattic.simplenote.models.Suggestion.Type
    public int getType() {
        return mType;
    }


    public void setName(java.lang.String name) {
        mName = name;
    }


    public void setType(@com.automattic.simplenote.models.Suggestion.Type
    int type) {
        mType = type;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
