/* Copyright 2010-2012 Brian Pellin.

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
package com.keepassdroid.icons;
import java.lang.reflect.Field;
import com.android.keepass.R;
import android.util.SparseIntArray;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Icons {
    static final int MUID_STATIC = getMUID();
    private static android.util.SparseIntArray icons = null;

    private static void buildList() {
        if (com.keepassdroid.icons.Icons.icons == null) {
            com.keepassdroid.icons.Icons.icons = new android.util.SparseIntArray();
            java.lang.Class<com.android.keepass.R.drawable> c;
            c = com.android.keepass.R.drawable.class;
            java.lang.reflect.Field[] fields;
            fields = c.getFields();
            for (int i = 0; i < fields.length; i++) {
                java.lang.String fieldName;
                fieldName = fields[i].getName();
                if (fieldName.matches("ic\\d{2}.*")) {
                    java.lang.String sNum;
                    sNum = fieldName.substring(2, 4);
                    int num;
                    num = java.lang.Integer.parseInt(sNum);
                    if (num > 69) {
                        continue;
                    }
                    int resId;
                    try {
                        resId = fields[i].getInt(null);
                    } catch (java.lang.Exception e) {
                        continue;
                    }
                    com.keepassdroid.icons.Icons.icons.put(num, resId);
                }
            }
        }
    }


    public static int iconToResId(int iconId) {
        com.keepassdroid.icons.Icons.buildList();
        return com.keepassdroid.icons.Icons.icons.get(iconId, com.android.keepass.R.drawable.ic99_blank);
    }


    public static int count() {
        com.keepassdroid.icons.Icons.buildList();
        return com.keepassdroid.icons.Icons.icons.size();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
