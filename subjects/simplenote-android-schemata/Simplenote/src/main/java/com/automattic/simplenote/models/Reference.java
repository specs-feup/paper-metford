package com.automattic.simplenote.models;
import java.util.Calendar;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * A note listed in the Information sheet which is an inbound reference to the open note.
 *
 * Calendar mDate   The last modified date of the note.
 * String mKey      The Simperium key of the note.
 * String mTitle    The title of the note.
 * int mCount       The number of references to the open note in the referencing note.
 */
public class Reference {
    static final int MUID_STATIC = getMUID();
    private java.util.Calendar mDate;

    private java.lang.String mKey;

    private java.lang.String mTitle;

    private int mCount;

    public Reference(java.lang.String key, java.lang.String title, java.util.Calendar date, int count) {
        mKey = key;
        mTitle = title;
        mDate = date;
        mCount = count;
    }


    public int getCount() {
        return mCount;
    }


    public java.util.Calendar getDate() {
        return mDate;
    }


    public java.lang.String getKey() {
        return mKey;
    }


    public java.lang.String getTitle() {
        return mTitle;
    }


    public void setCount(int count) {
        mCount = count;
    }


    public void setDate(java.util.Calendar date) {
        mDate = date;
    }


    public void setKey(java.lang.String key) {
        mKey = key;
    }


    public void setTitle(java.lang.String title) {
        mTitle = title;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
