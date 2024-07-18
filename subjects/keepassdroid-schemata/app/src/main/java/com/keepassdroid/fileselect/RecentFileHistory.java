/* Copyright 2013-2022 Brian Pellin.

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
package com.keepassdroid.fileselect;
import android.content.SharedPreferences;
import com.android.keepass.R;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import com.keepassdroid.utils.UriUtil;
import com.keepassdroid.utils.EmptyUtils;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.database.Cursor;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RecentFileHistory {
    static final int MUID_STATIC = getMUID();
    private static java.lang.String DB_KEY = "recent_databases";

    private static java.lang.String KEYFILE_KEY = "recent_keyfiles";

    private java.util.List<java.lang.String> databases = new java.util.ArrayList<java.lang.String>();

    private java.util.List<java.lang.String> keyfiles = new java.util.ArrayList<java.lang.String>();

    private android.content.Context ctx;

    private android.content.SharedPreferences prefs;

    private android.content.SharedPreferences.OnSharedPreferenceChangeListener listner;

    private boolean enabled;

    private boolean init = false;

    public RecentFileHistory(android.content.Context c) {
        ctx = c.getApplicationContext();
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(c);
        enabled = prefs.getBoolean(ctx.getString(com.android.keepass.R.string.recentfile_key), ctx.getResources().getBoolean(com.android.keepass.R.bool.recentfile_default));
        listner = new android.content.SharedPreferences.OnSharedPreferenceChangeListener() {
            @java.lang.Override
            public void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, java.lang.String key) {
                if (key.equals(ctx.getString(com.android.keepass.R.string.recentfile_key))) {
                    enabled = sharedPreferences.getBoolean(ctx.getString(com.android.keepass.R.string.recentfile_key), ctx.getResources().getBoolean(com.android.keepass.R.bool.recentfile_default));
                }
            }

        };
        prefs.registerOnSharedPreferenceChangeListener(listner);
    }


    private synchronized void init() {
        if (!init) {
            if (!upgradeFromSQL()) {
                loadPrefs();
            }
            init = true;
        }
    }


    private boolean upgradeFromSQL() {
        try {
            // Check for a database to upgrade from
            if (!sqlDatabaseExists()) {
                return false;
            }
            databases.clear();
            keyfiles.clear();
            com.keepassdroid.fileselect.FileDbHelper helper;
            helper = new com.keepassdroid.fileselect.FileDbHelper(ctx);
            helper.open();
            android.database.Cursor cursor;
            cursor = helper.fetchAllFiles();
            int dbIndex;
            dbIndex = cursor.getColumnIndex(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_FILENAME);
            int keyIndex;
            keyIndex = cursor.getColumnIndex(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_KEYFILE);
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    java.lang.String filename;
                    filename = cursor.getString(dbIndex);
                    java.lang.String keyfile;
                    keyfile = cursor.getString(keyIndex);
                    databases.add(filename);
                    keyfiles.add(keyfile);
                } 
            }
            savePrefs();
            cursor.close();
            helper.close();
        } catch (java.lang.Exception e) {
            // If upgrading fails, we'll just give up on it.
        }
        try {
            com.keepassdroid.fileselect.FileDbHelper.deleteDatabase(ctx);
        } catch (java.lang.Exception e) {
            // If we fail to delete it, just move on
        }
        return true;
    }


    private boolean sqlDatabaseExists() {
        java.io.File db;
        db = ctx.getDatabasePath(com.keepassdroid.fileselect.FileDbHelper.DATABASE_NAME);
        return db.exists();
    }


    public void createFile(android.net.Uri uri, android.net.Uri keyUri) {
        if ((!enabled) || (uri == null))
            return;

        init();
        // Remove any existing instance of the same filename
        deleteFile(uri, false);
        databases.add(0, uri.toString());
        java.lang.String key;
        key = (keyUri == null) ? "" : keyUri.toString();
        keyfiles.add(0, key);
        trimLists();
        savePrefs();
    }


    public boolean hasRecentFiles() {
        if (!enabled)
            return false;

        init();
        return databases.size() > 0;
    }


    public java.lang.String getDatabaseAt(int i) {
        init();
        if (i < databases.size()) {
            return databases.get(i);
        } else {
            return "";
        }
    }


    public java.lang.String getKeyfileAt(int i) {
        init();
        if (i < keyfiles.size()) {
            return keyfiles.get(i);
        } else {
            return "";
        }
    }


    private void loadPrefs() {
        loadList(databases, com.keepassdroid.fileselect.RecentFileHistory.DB_KEY);
        loadList(keyfiles, com.keepassdroid.fileselect.RecentFileHistory.KEYFILE_KEY);
    }


    private void savePrefs() {
        saveList(com.keepassdroid.fileselect.RecentFileHistory.DB_KEY, databases);
        saveList(com.keepassdroid.fileselect.RecentFileHistory.KEYFILE_KEY, keyfiles);
    }


    private void loadList(java.util.List<java.lang.String> list, java.lang.String keyprefix) {
        android.content.SharedPreferences prefs;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
        int size;
        size = prefs.getInt(keyprefix, 0);
        list.clear();
        for (int i = 0; i < size; i++) {
            list.add(prefs.getString((keyprefix + "_") + i, ""));
        }
    }


    private void saveList(java.lang.String keyprefix, java.util.List<java.lang.String> list) {
        android.content.SharedPreferences prefs;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
        android.content.SharedPreferences.Editor edit;
        edit = prefs.edit();
        int size;
        size = list.size();
        edit.putInt(keyprefix, size);
        for (int i = 0; i < size; i++) {
            edit.putString((keyprefix + "_") + i, list.get(i));
        }
        edit.apply();
    }


    public void deleteFile(android.net.Uri uri) {
        deleteFile(uri, true);
    }


    public void deleteFile(android.net.Uri uri, boolean save) {
        init();
        java.lang.String uriName;
        uriName = uri.toString();
        java.lang.String fileName;
        fileName = uri.getPath();
        for (int i = 0; i < databases.size(); i++) {
            java.lang.String entry;
            entry = databases.get(i);
            boolean delete;
            delete = ((uriName != null) && uriName.equals(entry)) || ((fileName != null) && fileName.equals(entry));
            if (delete) {
                databases.remove(i);
                keyfiles.remove(i);
                break;
            }
        }
        if (save) {
            savePrefs();
        }
    }


    public java.util.List<java.lang.String> getDbList() {
        init();
        java.util.List<java.lang.String> displayNames;
        displayNames = new java.util.ArrayList<java.lang.String>();
        for (java.lang.String fileName : databases) {
            java.lang.String name;
            name = com.keepassdroid.utils.UriUtil.getFileName(android.net.Uri.parse(fileName), ctx);
            if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(name)) {
                name = fileName;
            } else {
                name = (name + " - ") + fileName;
            }
            displayNames.add(name);
        }
        return displayNames;
    }


    public android.net.Uri getFileByName(android.net.Uri database) {
        if (!enabled)
            return null;

        init();
        int size;
        size = databases.size();
        for (int i = 0; i < size; i++) {
            if (com.keepassdroid.utils.UriUtil.equalsDefaultfile(database, databases.get(i))) {
                return com.keepassdroid.utils.UriUtil.parseDefaultFile(keyfiles.get(i));
            }
        }
        return null;
    }


    public void deleteAll() {
        init();
        databases.clear();
        keyfiles.clear();
        savePrefs();
    }


    public void deleteAllKeys() {
        init();
        keyfiles.clear();
        int size;
        size = databases.size();
        for (int i = 0; i < size; i++) {
            keyfiles.add("");
        }
        savePrefs();
    }


    private void trimLists() {
        int size;
        size = databases.size();
        for (int i = com.keepassdroid.fileselect.FileDbHelper.MAX_FILES; i < size; i++) {
            if (i < databases.size()) {
                databases.remove(i);
            }
            if (i < keyfiles.size()) {
                keyfiles.remove(i);
            }
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
