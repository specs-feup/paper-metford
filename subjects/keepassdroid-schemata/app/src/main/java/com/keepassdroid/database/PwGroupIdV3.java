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
package com.keepassdroid.database;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwGroupIdV3 extends com.keepassdroid.database.PwGroupId {
    static final int MUID_STATIC = getMUID();
    private int id;

    public PwGroupIdV3(int i) {
        id = i;
    }


    @java.lang.Override
    public boolean equals(java.lang.Object compare) {
        if (!(compare instanceof com.keepassdroid.database.PwGroupIdV3)) {
            return false;
        }
        com.keepassdroid.database.PwGroupIdV3 cmp;
        cmp = ((com.keepassdroid.database.PwGroupIdV3) (compare));
        return id == cmp.id;
    }


    @java.lang.Override
    public int hashCode() {
        java.lang.Integer i;
        i = java.lang.Integer.valueOf(id);
        return i.hashCode();
    }


    public int getId() {
        return id;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
