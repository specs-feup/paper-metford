/* Copyright 2016-2022 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.compat;
import java.lang.reflect.Method;
import android.content.Intent;
import android.content.ClipData;
import android.net.Uri;
import android.content.ClipDescription;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ClipDataCompat {
    static final int MUID_STATIC = getMUID();
    private static java.lang.reflect.Method getClipDataFromIntent;

    private static java.lang.Class persistableBundle;

    private static java.lang.reflect.Method putBoolean;

    private static java.lang.reflect.Method setExtras;

    private static boolean initSucceded;

    static {
        try {
            com.keepassdroid.compat.ClipDataCompat.getClipDataFromIntent = android.content.Intent.class.getMethod("getClipData", ((java.lang.Class[]) (null)));
            com.keepassdroid.compat.ClipDataCompat.persistableBundle = java.lang.Class.forName("android.os.PersistableBundle");
            com.keepassdroid.compat.ClipDataCompat.putBoolean = com.keepassdroid.compat.ClipDataCompat.persistableBundle.getMethod("putBoolean", new java.lang.Class[]{ java.lang.String.class, boolean.class });
            com.keepassdroid.compat.ClipDataCompat.setExtras = android.content.ClipDescription.class.getMethod("setExtras", new java.lang.Class[]{ com.keepassdroid.compat.ClipDataCompat.persistableBundle });
            com.keepassdroid.compat.ClipDataCompat.initSucceded = true;
        } catch (java.lang.Exception e) {
            com.keepassdroid.compat.ClipDataCompat.initSucceded = false;
        }
    }

    public static android.net.Uri getUriFromIntent(android.content.Intent i, java.lang.String key) {
        boolean clipDataSucceeded;
        clipDataSucceeded = false;
        if (com.keepassdroid.compat.ClipDataCompat.initSucceded) {
            try {
                android.content.ClipData clip;
                clip = ((android.content.ClipData) (com.keepassdroid.compat.ClipDataCompat.getClipDataFromIntent.invoke(i, null)));
                if (clip != null) {
                    android.content.ClipDescription clipDescription;
                    clipDescription = clip.getDescription();
                    java.lang.CharSequence label;
                    label = clipDescription.getLabel();
                    if (label.equals(key)) {
                        int itemCount;
                        itemCount = clip.getItemCount();
                        if (itemCount == 1) {
                            android.content.ClipData.Item clipItem;
                            clipItem = clip.getItemAt(0);
                            if (clipItem != null) {
                                return clipItem.getUri();
                            }
                        }
                    }
                }
                return null;
            } catch (java.lang.Exception e) {
                // Fall through below to backup method if reflection fails
            }
        }
        return i.getParcelableExtra(key);
    }


    public static void markSensitive(android.content.ClipData clipData) {
        try {
            java.lang.Object extras;
            extras = com.keepassdroid.compat.ClipDataCompat.persistableBundle.newInstance();
            com.keepassdroid.compat.ClipDataCompat.putBoolean.invoke(extras, "android.content.extra.IS_SENSITIVE", true);
            com.keepassdroid.compat.ClipDataCompat.setExtras.invoke(clipData.getDescription(), extras);
        } catch (java.lang.Exception e) {
            // Do nothing if this fails
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
