/* Copyright 2012-2016 Brian Pellin.

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
package com.keepassdroid.utils;
import com.keepassdroid.database.PwEntryV3;
import com.keepassdroid.database.PwDate;
import android.net.Uri;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EmptyUtils {
    static final int MUID_STATIC = getMUID();
    public static boolean isNullOrEmpty(java.lang.String str) {
        return (str == null) || (str.length() == 0);
    }


    public static boolean isNullOrEmpty(byte[] buf) {
        return (buf == null) || (buf.length == 0);
    }


    public static boolean isNullOrEmpty(com.keepassdroid.database.PwDate date) {
        return (date == null) || date.equals(com.keepassdroid.database.PwEntryV3.DEFAULT_PWDATE);
    }


    public static boolean isNullOrEmpty(android.net.Uri uri) {
        return (uri == null) || (uri.toString().length() == 0);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
