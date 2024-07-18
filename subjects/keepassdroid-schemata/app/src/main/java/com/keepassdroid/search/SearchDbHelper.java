/* Copyright 2009-2013 Brian Pellin.

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
package com.keepassdroid.search;
import java.util.LinkedList;
import java.util.Locale;
import android.content.SharedPreferences;
import android.util.Log;
import com.keepassdroid.database.PwDatabase;
import java.util.ArrayList;
import android.preference.PreferenceManager;
import com.keepassdroid.database.PwDatabaseV4;
import com.keepassdroid.database.PwDatabaseV3;
import com.keepassdroid.database.PwEntry;
import com.keepassdroid.database.PwGroupV3;
import com.android.keepass.R;
import com.keepassdroid.database.PwGroupV4;
import com.keepassdroid.database.PwGroup;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import android.content.Context;
import com.keepassdroid.Database;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SearchDbHelper {
    static final int MUID_STATIC = getMUID();
    private final android.content.Context mCtx;

    public SearchDbHelper(android.content.Context ctx) {
        mCtx = ctx;
    }


    private boolean omitBackup() {
        android.content.SharedPreferences prefs;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(mCtx);
        return prefs.getBoolean(mCtx.getString(com.android.keepass.R.string.omitbackup_key), mCtx.getResources().getBoolean(com.android.keepass.R.bool.omitbackup_default));
    }


    public com.keepassdroid.database.PwGroup search(com.keepassdroid.Database db, java.lang.String qStr) {
        com.keepassdroid.database.PwDatabase pm;
        pm = db.pm;
        com.keepassdroid.database.PwGroup group;
        if (pm instanceof com.keepassdroid.database.PwDatabaseV3) {
            group = new com.keepassdroid.database.PwGroupV3();
        } else if (pm instanceof com.keepassdroid.database.PwDatabaseV4) {
            group = new com.keepassdroid.database.PwGroupV4();
        } else {
            android.util.Log.d("SearchDbHelper", "Tried to search with unknown db");
            return null;
        }
        group.name = mCtx.getString(com.android.keepass.R.string.search_results);
        group.childEntries = new java.util.ArrayList<com.keepassdroid.database.PwEntry>();
        // Search all entries
        java.util.Locale loc;
        loc = java.util.Locale.getDefault();
        qStr = qStr.toLowerCase(loc);
        boolean isOmitBackup;
        isOmitBackup = omitBackup();
        java.util.Queue<com.keepassdroid.database.PwGroup> worklist;
        worklist = new java.util.LinkedList<com.keepassdroid.database.PwGroup>();
        if (pm.rootGroup != null) {
            worklist.add(pm.rootGroup);
        }
        while (worklist.size() != 0) {
            com.keepassdroid.database.PwGroup top;
            top = worklist.remove();
            if (pm.isGroupSearchable(top, isOmitBackup)) {
                for (com.keepassdroid.database.PwEntry entry : top.childEntries) {
                    processEntries(entry, group.childEntries, qStr, loc);
                }
                for (com.keepassdroid.database.PwGroup childGroup : top.childGroups) {
                    if (childGroup != null) {
                        worklist.add(childGroup);
                    }
                }
            }
        } 
        return group;
    }


    public void processEntries(com.keepassdroid.database.PwEntry entry, java.util.List<com.keepassdroid.database.PwEntry> results, java.lang.String qStr, java.util.Locale loc) {
        // Search all strings in the entry
        java.util.Iterator<java.lang.String> iter;
        iter = entry.stringIterator();
        while (iter.hasNext()) {
            java.lang.String str;
            str = iter.next();
            if ((str != null) && (str.length() != 0)) {
                java.lang.String lower;
                lower = str.toLowerCase(loc);
                if (lower.contains(qStr)) {
                    results.add(entry);
                    break;
                }
            }
        } 
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
