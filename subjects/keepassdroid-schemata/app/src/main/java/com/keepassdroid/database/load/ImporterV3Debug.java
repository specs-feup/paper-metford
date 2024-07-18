/* Copyright 2011-2017 Brian Pellin.

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
package com.keepassdroid.database.load;
import com.keepassdroid.database.PwDatabaseV3Debug;
import com.keepassdroid.database.exception.InvalidDBException;
import com.keepassdroid.UpdateStatus;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ImporterV3Debug extends com.keepassdroid.database.load.ImporterV3 {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected com.keepassdroid.database.PwDatabaseV3Debug createDB() {
        return new com.keepassdroid.database.PwDatabaseV3Debug();
    }


    @java.lang.Override
    public com.keepassdroid.database.PwDatabaseV3Debug openDatabase(java.io.InputStream inStream, java.lang.String password, java.io.InputStream keyInputStream, com.keepassdroid.UpdateStatus status, long roundsFix) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
        return ((com.keepassdroid.database.PwDatabaseV3Debug) (super.openDatabase(inStream, password, keyInputStream, status, roundsFix)));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
