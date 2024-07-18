/* Copyright 2009-2018 Brian Pellin.

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
import android.database.sqlite.SQLiteOpenHelper;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.io.FileFilter;
import android.database.Cursor;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FileDbHelper {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String LAST_FILENAME = "lastFile";

    public static final java.lang.String LAST_KEYFILE = "lastKey";

    public static final java.lang.String DATABASE_NAME = "keepassdroid";

    private static final java.lang.String FILE_TABLE = "files";

    private static final int DATABASE_VERSION = 1;

    public static final int MAX_FILES = 5;

    public static final java.lang.String KEY_FILE_ID = "_id";

    public static final java.lang.String KEY_FILE_FILENAME = "fileName";

    public static final java.lang.String KEY_FILE_KEYFILE = "keyFile";

    public static final java.lang.String KEY_FILE_UPDATED = "updated";

    private static final java.lang.String DATABASE_CREATE = ((((((((("create table " + com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE) + " ( ") + com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_ID) + " integer primary key autoincrement, ") + com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_FILENAME) + " text not null, ") + com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_KEYFILE) + " text, ") + com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_UPDATED) + " integer not null);";

    private final android.content.Context mCtx;

    private com.keepassdroid.fileselect.FileDbHelper.DatabaseHelper mDbHelper;

    private android.database.sqlite.SQLiteDatabase mDb;

    private static class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {
        private final android.content.Context mCtx;

        DatabaseHelper(android.content.Context ctx) {
            super(ctx, com.keepassdroid.fileselect.FileDbHelper.DATABASE_NAME, null, com.keepassdroid.fileselect.FileDbHelper.DATABASE_VERSION);
            mCtx = ctx;
        }


        @java.lang.Override
        public void onCreate(android.database.sqlite.SQLiteDatabase db) {
            db.execSQL(com.keepassdroid.fileselect.FileDbHelper.DATABASE_CREATE);
            // Migrate preference to database if it is set.
            android.content.SharedPreferences settings;
            settings = mCtx.getSharedPreferences("PasswordActivity", android.content.Context.MODE_PRIVATE);
            java.lang.String lastFile;
            lastFile = settings.getString(com.keepassdroid.fileselect.FileDbHelper.LAST_FILENAME, "");
            java.lang.String lastKey;
            lastKey = settings.getString(com.keepassdroid.fileselect.FileDbHelper.LAST_KEYFILE, "");
            if (lastFile.length() > 0) {
                android.content.ContentValues vals;
                vals = new android.content.ContentValues();
                vals.put(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_FILENAME, lastFile);
                vals.put(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_UPDATED, java.lang.System.currentTimeMillis());
                if (lastKey.length() > 0) {
                    vals.put(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_KEYFILE, lastKey);
                }
                db.insert(com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE, null, vals);
                // Clear old preferences
                deletePrefs(settings);
            }
        }


        @java.lang.Override
        public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
            // Only one database version so far
        }


        private void deletePrefs(android.content.SharedPreferences prefs) {
            // We won't worry too much if this fails
            try {
                android.content.SharedPreferences.Editor editor;
                editor = prefs.edit();
                editor.remove(com.keepassdroid.fileselect.FileDbHelper.LAST_FILENAME);
                editor.remove(com.keepassdroid.fileselect.FileDbHelper.LAST_KEYFILE);
                editor.apply();
            } catch (java.lang.Exception e) {
                assert true;
            }
        }

    }

    public FileDbHelper(android.content.Context ctx) {
        mCtx = ctx;
    }


    public com.keepassdroid.fileselect.FileDbHelper open() throws android.database.SQLException {
        mDbHelper = new com.keepassdroid.fileselect.FileDbHelper.DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }


    public boolean isOpen() {
        return mDb.isOpen();
    }


    public void close() {
        mDb.close();
    }


    public long createFile(java.lang.String fileName, java.lang.String keyFile) {
        // Check to see if this filename is already used
        android.database.Cursor cursor;
        try {
            cursor = mDb.query(true, com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE, new java.lang.String[]{ com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_ID }, com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_FILENAME + "=?", new java.lang.String[]{ fileName }, null, null, null, null);
        } catch (java.lang.Exception e) {
            assert true;
            return -1;
        }
        long result;
        // If there is an existing entry update it with the new key file
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            long id;
            id = cursor.getLong(cursor.getColumnIndexOrThrow(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_ID));
            android.content.ContentValues vals;
            vals = new android.content.ContentValues();
            vals.put(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_KEYFILE, keyFile);
            vals.put(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_UPDATED, java.lang.System.currentTimeMillis());
            result = mDb.update(com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE, vals, (com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_ID + " = ") + id, null);
            // Otherwise add the new entry
        } else {
            android.content.ContentValues vals;
            vals = new android.content.ContentValues();
            vals.put(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_FILENAME, fileName);
            vals.put(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_KEYFILE, keyFile);
            vals.put(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_UPDATED, java.lang.System.currentTimeMillis());
            result = mDb.insert(com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE, null, vals);
        }
        // Delete all but the last five records
        try {
            deleteAllBut(com.keepassdroid.fileselect.FileDbHelper.MAX_FILES);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            assert true;
        }
        cursor.close();
        return result;
    }


    private void deleteAllBut(int limit) {
        android.database.Cursor cursor;
        cursor = mDb.query(com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE, new java.lang.String[]{ com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_UPDATED }, null, null, null, null, com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_UPDATED);
        if (cursor.getCount() > limit) {
            cursor.moveToFirst();
            long time;
            time = cursor.getLong(cursor.getColumnIndexOrThrow(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_UPDATED));
            mDb.execSQL(((((("DELETE FROM " + com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE) + " WHERE ") + com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_UPDATED) + "<") + time) + ";");
        }
        cursor.close();
    }


    public void deleteAllKeys() {
        android.content.ContentValues vals;
        vals = new android.content.ContentValues();
        vals.put(com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_KEYFILE, "");
        mDb.update(com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE, vals, null, null);
    }


    public void deleteFile(java.lang.String filename) {
        mDb.delete(com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE, com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_FILENAME + " = ?", new java.lang.String[]{ filename });
    }


    public android.database.Cursor fetchAllFiles() {
        android.database.Cursor ret;
        ret = mDb.query(com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE, new java.lang.String[]{ com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_ID, com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_FILENAME, com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_KEYFILE }, null, null, null, null, com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_UPDATED + " DESC", java.lang.Integer.toString(com.keepassdroid.fileselect.FileDbHelper.MAX_FILES));
        return ret;
    }


    public android.database.Cursor fetchFile(long fileId) throws android.database.SQLException {
        android.database.Cursor cursor;
        cursor = mDb.query(true, com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE, new java.lang.String[]{ com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_FILENAME, com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_KEYFILE }, (com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_ID + "=") + fileId, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public java.lang.String getFileByName(java.lang.String name) {
        android.database.Cursor cursor;
        cursor = mDb.query(true, com.keepassdroid.fileselect.FileDbHelper.FILE_TABLE, new java.lang.String[]{ com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_KEYFILE }, com.keepassdroid.fileselect.FileDbHelper.KEY_FILE_FILENAME + "= ?", new java.lang.String[]{ name }, null, null, null, null);
        if (cursor == null) {
            return "";
        }
        java.lang.String filename;
        if (cursor.moveToFirst()) {
            filename = cursor.getString(0);
        } else {
            // Cursor is empty
            filename = "";
        }
        cursor.close();
        return filename;
    }


    public boolean hasRecentFiles() {
        android.database.Cursor cursor;
        cursor = fetchAllFiles();
        boolean hasRecent;
        hasRecent = cursor.getCount() > 0;
        cursor.close();
        return hasRecent;
    }


    /**
     * Deletes a database including its journal file and other auxiliary files
     * that may have been created by the database engine.
     *
     * @param file
     * 		The database file path.
     * @return True if the database was successfully deleted.
     */
    public static boolean deleteDatabase(android.content.Context ctx) {
        java.io.File file;
        file = ctx.getDatabasePath(com.keepassdroid.fileselect.FileDbHelper.DATABASE_NAME);
        if (file == null) {
            throw new java.lang.IllegalArgumentException("file must not be null");
        }
        boolean deleted;
        deleted = false;
        deleted |= file.delete();
        deleted |= new java.io.File(file.getPath() + "-journal").delete();
        deleted |= new java.io.File(file.getPath() + "-shm").delete();
        deleted |= new java.io.File(file.getPath() + "-wal").delete();
        java.io.File dir;
        dir = file.getParentFile();
        if (dir != null) {
            final java.lang.String prefix;
            prefix = file.getName() + "-mj";
            final java.io.FileFilter filter;
            filter = new java.io.FileFilter() {
                @java.lang.Override
                public boolean accept(java.io.File candidate) {
                    return candidate.getName().startsWith(prefix);
                }

            };
            for (java.io.File masterJournal : dir.listFiles(filter)) {
                deleted |= masterJournal.delete();
            }
        }
        return deleted;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
