/* Copyright 2017 Brian Pellin.

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
import android.content.ContentResolver;
import java.lang.reflect.Method;
import android.net.Uri;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ContentResolverCompat {
    static final int MUID_STATIC = getMUID();
    public static boolean available;

    private static java.lang.Class contentResolver;

    private static java.lang.reflect.Method takePersistableUriPermission;

    static {
        try {
            com.keepassdroid.compat.ContentResolverCompat.contentResolver = android.content.ContentResolver.class;
            com.keepassdroid.compat.ContentResolverCompat.takePersistableUriPermission = com.keepassdroid.compat.ContentResolverCompat.contentResolver.getMethod("takePersistableUriPermission", new java.lang.Class[]{ android.net.Uri.class, int.class });
            com.keepassdroid.compat.ContentResolverCompat.available = true;
        } catch (java.lang.Exception e) {
            com.keepassdroid.compat.ContentResolverCompat.available = false;
        }
    }

    public static void takePersistableUriPermission(android.content.ContentResolver resolver, android.net.Uri uri, int modeFlags) {
        if (com.keepassdroid.compat.ContentResolverCompat.available) {
            try {
                com.keepassdroid.compat.ContentResolverCompat.takePersistableUriPermission.invoke(resolver, new java.lang.Object[]{ uri, modeFlags });
            } catch (java.lang.Exception e) {
                // Fail silently
            }
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
