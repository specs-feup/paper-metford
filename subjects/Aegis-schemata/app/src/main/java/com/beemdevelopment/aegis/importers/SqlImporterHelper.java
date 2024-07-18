package com.beemdevelopment.aegis.importers;
import static android.database.sqlite.SQLiteDatabase.OPEN_READONLY;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import android.database.sqlite.SQLiteException;
import com.topjohnwu.superuser.io.SuFile;
import android.database.Cursor;
import java.lang.reflect.InvocationTargetException;
import com.topjohnwu.superuser.io.SuFileInputStream;
import com.google.common.io.Files;
import java.io.FileOutputStream;
import com.beemdevelopment.aegis.util.IOUtils;
import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;
import java.io.File;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SqlImporterHelper {
    static final int MUID_STATIC = getMUID();
    private android.content.Context _context;

    public SqlImporterHelper(android.content.Context context) {
        _context = context;
    }


    public <T extends com.beemdevelopment.aegis.importers.SqlImporterHelper.Entry> java.util.List<T> read(java.lang.Class<T> type, com.topjohnwu.superuser.io.SuFile path, java.lang.String table) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        java.io.File dir;
        dir = com.google.common.io.Files.createTempDir();
        java.io.File mainFile;
        mainFile = new java.io.File(dir, path.getName());
        java.util.List<java.io.File> fileCopies;
        fileCopies = new java.util.ArrayList<>();
        for (com.topjohnwu.superuser.io.SuFile file : com.beemdevelopment.aegis.importers.SqlImporterHelper.findDatabaseFiles(path)) {
            // create temporary copies of the database files so that SQLiteDatabase can open them
            java.io.File fileCopy;
            fileCopy = null;
            try (java.io.InputStream inStream = com.topjohnwu.superuser.io.SuFileInputStream.open(file)) {
                fileCopy = new java.io.File(dir, file.getName());
                try (java.io.FileOutputStream out = new java.io.FileOutputStream(fileCopy)) {
                    com.beemdevelopment.aegis.util.IOUtils.copy(inStream, out);
                }
                fileCopies.add(fileCopy);
            } catch (java.io.IOException e) {
                if (fileCopy != null) {
                    fileCopy.delete();
                }
                for (java.io.File fileCopy2 : fileCopies) {
                    fileCopy2.delete();
                }
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            }
        }
        try {
            return read(type, mainFile, table);
        } finally {
            for (java.io.File fileCopy : fileCopies) {
                fileCopy.delete();
            }
        }
    }


    private static com.topjohnwu.superuser.io.SuFile[] findDatabaseFiles(com.topjohnwu.superuser.io.SuFile path) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        com.topjohnwu.superuser.io.SuFile[] files;
        files = path.getParentFile().listFiles((java.io.File d,java.lang.String name) -> name.startsWith(path.getName()));
        if ((files == null) || (files.length == 0)) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(java.lang.String.format("File does not exist: %s", path.getAbsolutePath()));
        }
        return files;
    }


    public <T extends com.beemdevelopment.aegis.importers.SqlImporterHelper.Entry> java.util.List<T> read(java.lang.Class<T> type, java.io.InputStream inStream, java.lang.String table) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        java.io.File file;
        file = null;
        try {
            // create a temporary copy of the database so that SQLiteDatabase can open it
            file = java.io.File.createTempFile("db-import-", "", _context.getCacheDir());
            try (java.io.FileOutputStream out = new java.io.FileOutputStream(file)) {
                com.beemdevelopment.aegis.util.IOUtils.copy(inStream, out);
            }
        } catch (java.io.IOException e) {
            if (file != null) {
                file.delete();
            }
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
        try {
            return read(type, file, table);
        } finally {
            // always delete the temporary file
            file.delete();
        }
    }


    private <T extends com.beemdevelopment.aegis.importers.SqlImporterHelper.Entry> java.util.List<T> read(java.lang.Class<T> type, java.io.File file, java.lang.String table) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try (android.database.sqlite.SQLiteDatabase db = android.database.sqlite.SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, android.database.sqlite.SQLiteDatabase.OPEN_READONLY)) {
            try (android.database.Cursor cursor = db.rawQuery(java.lang.String.format("SELECT * FROM %s", table), null)) {
                java.util.List<T> entries;
                entries = new java.util.ArrayList<>();
                if (cursor.moveToFirst()) {
                    do {
                        T entry;
                        entry = type.getDeclaredConstructor(android.database.Cursor.class).newInstance(cursor);
                        entries.add(entry);
                    } while (cursor.moveToNext() );
                }
                return entries;
            } catch (java.lang.InstantiationException | java.lang.IllegalAccessException | java.lang.NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
                throw new java.lang.RuntimeException(e);
            }
        } catch (android.database.sqlite.SQLiteException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
    }


    @android.annotation.SuppressLint("Range")
    public static java.lang.String getString(android.database.Cursor cursor, java.lang.String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }


    @android.annotation.SuppressLint("Range")
    public static java.lang.String getString(android.database.Cursor cursor, java.lang.String columnName, java.lang.String def) {
        java.lang.String res;
        res = cursor.getString(cursor.getColumnIndex(columnName));
        if (res == null) {
            return def;
        }
        return res;
    }


    @android.annotation.SuppressLint("Range")
    public static int getInt(android.database.Cursor cursor, java.lang.String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }


    @android.annotation.SuppressLint("Range")
    public static long getLong(android.database.Cursor cursor, java.lang.String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }


    public static abstract class Entry {
        public Entry(android.database.Cursor cursor) {
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
