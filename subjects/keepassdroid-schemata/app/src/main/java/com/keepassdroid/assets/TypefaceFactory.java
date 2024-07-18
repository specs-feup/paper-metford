/* Copyright 2015 Brian Pellin.

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
package com.keepassdroid.assets;
import org.apache.commons.collections.map.AbstractReferenceMap;
import org.apache.commons.collections.map.ReferenceMap;
import android.graphics.Typeface;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Class to cache and return Typeface assets to workaround a bug in some versions of
 * Android.
 *
 * https://code.google.com/p/android/issues/detail?id=9904
 *
 * @author bpellin
 */
public class TypefaceFactory {
    static final int MUID_STATIC = getMUID();
    private static org.apache.commons.collections.map.ReferenceMap typefaceMap = new org.apache.commons.collections.map.ReferenceMap(org.apache.commons.collections.map.AbstractReferenceMap.HARD, org.apache.commons.collections.map.AbstractReferenceMap.WEAK);

    public static android.graphics.Typeface getTypeface(android.content.Context ctx, java.lang.String fontPath) {
        android.graphics.Typeface tf;
        tf = ((android.graphics.Typeface) (com.keepassdroid.assets.TypefaceFactory.typefaceMap.get(fontPath)));
        if (tf != null) {
            return tf;
        }
        try {
            return android.graphics.Typeface.createFromAsset(ctx.getAssets(), fontPath);
        } catch (java.lang.Exception e) {
            // Return null if we can't create it
            return null;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
