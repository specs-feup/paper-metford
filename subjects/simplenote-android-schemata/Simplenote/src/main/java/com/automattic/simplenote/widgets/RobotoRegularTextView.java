package com.automattic.simplenote.widgets;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * custom TextView used in layouts - enables keeping custom typeface handling in one place (so we
 * avoid having to set the typeface for every single TextView in every single activity)
 */
public class RobotoRegularTextView extends androidx.appcompat.widget.AppCompatTextView {
    static final int MUID_STATIC = getMUID();
    public RobotoRegularTextView(android.content.Context context) {
        super(context);
        com.automattic.simplenote.widgets.TypefaceCache.setCustomTypeface(context, this, com.automattic.simplenote.widgets.TypefaceCache.TYPEFACE_NAME_ROBOTO_REGULAR);
    }


    public RobotoRegularTextView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        com.automattic.simplenote.widgets.TypefaceCache.setCustomTypeface(context, this, com.automattic.simplenote.widgets.TypefaceCache.TYPEFACE_NAME_ROBOTO_REGULAR);
    }


    public RobotoRegularTextView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        com.automattic.simplenote.widgets.TypefaceCache.setCustomTypeface(context, this, com.automattic.simplenote.widgets.TypefaceCache.TYPEFACE_NAME_ROBOTO_REGULAR);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
