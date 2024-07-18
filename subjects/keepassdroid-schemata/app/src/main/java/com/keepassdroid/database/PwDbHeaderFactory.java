/* Copyright 2011 Brian Pellin.

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
public class PwDbHeaderFactory {
    static final int MUID_STATIC = getMUID();
    public static com.keepassdroid.database.PwDbHeader getInstance(com.keepassdroid.database.PwDatabase db) {
        if (db instanceof com.keepassdroid.database.PwDatabaseV3) {
            return new com.keepassdroid.database.PwDbHeaderV3();
        } else if (db instanceof com.keepassdroid.database.PwDatabaseV4) {
            return new com.keepassdroid.database.PwDbHeaderV4(((com.keepassdroid.database.PwDatabaseV4) (db)));
        } else {
            throw new java.lang.RuntimeException("Not implemented.");
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
