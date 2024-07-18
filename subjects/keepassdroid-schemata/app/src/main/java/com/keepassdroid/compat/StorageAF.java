/* Copyright 2016-2018 Brian Pellin.

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
import java.lang.reflect.Field;
import android.content.SharedPreferences;
import com.android.keepass.R;
import android.os.Build;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by bpellin on 3/10/16.
 */
public class StorageAF {
    static final int MUID_STATIC = getMUID();
    public static java.lang.String ACTION_OPEN_DOCUMENT;

    static {
        try {
            java.lang.reflect.Field openDocument;
            openDocument = android.content.Intent.class.getField("ACTION_OPEN_DOCUMENT");
            com.keepassdroid.compat.StorageAF.ACTION_OPEN_DOCUMENT = ((java.lang.String) (openDocument.get(null)));
        } catch (java.lang.Exception e) {
            com.keepassdroid.compat.StorageAF.ACTION_OPEN_DOCUMENT = "android.intent.action.OPEN_DOCUMENT";
        }
    }

    public static boolean supportsStorageFramework() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT;
    }


    public static boolean useStorageFramework(android.content.Context ctx) {
        if (!com.keepassdroid.compat.StorageAF.supportsStorageFramework()) {
            return false;
        }
        android.content.SharedPreferences prefs;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getBoolean(ctx.getString(com.android.keepass.R.string.saf_key), ctx.getResources().getBoolean(com.android.keepass.R.bool.saf_default));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
