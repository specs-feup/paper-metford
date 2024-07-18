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
package com.keepassdroid.database;
import java.util.List;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntrySearchHandlerAll extends com.keepassdroid.database.EntryHandler<com.keepassdroid.database.PwEntry> {
    static final int MUID_STATIC = getMUID();
    private java.util.List<com.keepassdroid.database.PwEntry> listStorage;

    private com.keepassdroid.database.SearchParameters sp;

    private java.util.Date now;

    public EntrySearchHandlerAll(com.keepassdroid.database.SearchParameters sp, java.util.List<com.keepassdroid.database.PwEntry> listStorage) {
        this.sp = sp;
        this.listStorage = listStorage;
        now = new java.util.Date();
    }


    @java.lang.Override
    public boolean operate(com.keepassdroid.database.PwEntry entry) {
        if (sp.respectEntrySearchingDisabled && (!entry.isSearchingEnabled())) {
            return true;
        }
        if ((sp.excludeExpired && entry.expires()) && now.after(entry.getExpiryTime())) {
            return true;
        }
        listStorage.add(entry);
        return true;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
