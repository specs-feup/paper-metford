/* Copyright 2009 Brian Pellin.

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
package com.keepassdroid.settings;
import android.content.SharedPreferences;
import com.android.keepass.R;
import android.preference.PreferenceManager;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PrefsUtil {
    static final int MUID_STATIC = getMUID();
    public static float getListTextSize(android.content.Context ctx) {
        android.content.SharedPreferences prefs;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
        return java.lang.Float.parseFloat(prefs.getString(ctx.getString(com.android.keepass.R.string.list_size_key), ctx.getString(com.android.keepass.R.string.list_size_default)));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
