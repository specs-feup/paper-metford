package com.automattic.simplenote.widgets;
import java.util.Hashtable;
import android.graphics.Typeface;
import android.widget.TextView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TypefaceCache {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TYPEFACE_NAME_ROBOTO_REGULAR = "Roboto-Regular.ttf";

    public static final java.lang.String TYPEFACE_NAME_ROBOTO_MEDIUM = "Roboto-Medium.ttf";

    public static final java.lang.String TYPEFACE_NAME_ROBOTO_LIGHT = "Roboto-Light.ttf";

    private static final java.util.Hashtable<java.lang.String, android.graphics.Typeface> mTypefaceCache = new java.util.Hashtable<>();

    public static android.graphics.Typeface getTypeface(android.content.Context context, java.lang.String typefaceName) {
        if ((context == null) || (typefaceName == null)) {
            return null;
        }
        if (!com.automattic.simplenote.widgets.TypefaceCache.mTypefaceCache.containsKey(typefaceName)) {
            android.graphics.Typeface typeface;
            typeface = android.graphics.Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/" + typefaceName);
            if (typeface != null) {
                com.automattic.simplenote.widgets.TypefaceCache.mTypefaceCache.put(typefaceName, typeface);
            }
        }
        return com.automattic.simplenote.widgets.TypefaceCache.mTypefaceCache.get(typefaceName);
    }


    /* sets the typeface for a TextView (or TextView descendant such as EditText or Button) based on
    the passed attributes, defaults to normal typeface
     */
    protected static void setCustomTypeface(android.content.Context context, android.widget.TextView view, java.lang.String typefaceName) {
        if (((context == null) || (view == null)) || (typefaceName == null)) {
            return;
        }
        // skip at design-time
        if (view.isInEditMode())
            return;

        android.graphics.Typeface typeface;
        typeface = com.automattic.simplenote.widgets.TypefaceCache.getTypeface(context, typefaceName);
        if (typeface != null) {
            view.setTypeface(typeface);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
