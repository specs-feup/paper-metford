/* Copyright 2014 Brian Pellin.

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
import com.keepassdroid.database.PwDatabase;
import com.keepassdroid.database.PwDatabaseV4;
import com.keepassdroid.database.PwEntry;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SprEngine {
    static final int MUID_STATIC = getMUID();
    private static com.keepassdroid.utils.SprEngineV4 sprV4 = new com.keepassdroid.utils.SprEngineV4();

    private static com.keepassdroid.utils.SprEngine spr = new com.keepassdroid.utils.SprEngine();

    public static com.keepassdroid.utils.SprEngine getInstance(com.keepassdroid.database.PwDatabase db) {
        if (db instanceof com.keepassdroid.database.PwDatabaseV4) {
            return com.keepassdroid.utils.SprEngine.sprV4;
        } else {
            return com.keepassdroid.utils.SprEngine.spr;
        }
    }


    public java.lang.String compile(java.lang.String text, com.keepassdroid.database.PwEntry entry, com.keepassdroid.database.PwDatabase database) {
        return text;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
