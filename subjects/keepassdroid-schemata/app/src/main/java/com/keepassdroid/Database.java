/* Copyright 2009-2022 Brian Pellin.

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
package com.keepassdroid;
import java.util.Set;
import java.io.OutputStream;
import com.keepassdroid.database.PwDatabase;
import com.keepassdroid.utils.UriUtil;
import org.apache.commons.io.FileUtils;
import java.io.FileNotFoundException;
import android.net.Uri;
import com.keepassdroid.database.save.PwDbOutput;
import com.keepassdroid.icons.DrawableFactory;
import java.io.BufferedInputStream;
import com.android.keepass.R;
import com.keepassdroid.database.exception.FileUriException;
import com.keepassdroid.database.exception.InvalidPasswordException;
import com.keepassdroid.database.load.ImporterFactory;
import com.keepassdroid.database.load.Importer;
import java.util.HashSet;
import com.keepassdroid.database.exception.InvalidDBException;
import android.content.SharedPreferences;
import android.util.Log;
import java.io.SyncFailedException;
import java.io.InputStream;
import java.io.IOException;
import android.preference.PreferenceManager;
import com.keepassdroid.database.PwDatabaseV3;
import com.keepassdroid.search.SearchDbHelper;
import java.io.FileOutputStream;
import com.keepassdroid.database.exception.PwDbOutputException;
import com.keepassdroid.database.PwGroup;
import java.io.File;
import com.keepassdroid.database.exception.ContentFileNotFoundException;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author bpellin
 */
public class Database {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = com.keepassdroid.Database.class.getName();

    public java.util.Set<com.keepassdroid.database.PwGroup> dirty = new java.util.HashSet<com.keepassdroid.database.PwGroup>();

    public com.keepassdroid.database.PwDatabase pm;

    public android.net.Uri mUri;

    public com.keepassdroid.search.SearchDbHelper searchHelper;

    public boolean readOnly = false;

    public boolean passwordEncodingError = false;

    public com.keepassdroid.icons.DrawableFactory drawFactory = new com.keepassdroid.icons.DrawableFactory();

    private boolean loaded = false;

    public boolean Loaded() {
        return loaded;
    }


    public void setLoaded() {
        loaded = true;
    }


    public void LoadData(android.content.Context ctx, java.io.InputStream is, java.lang.String password, java.io.InputStream keyInputStream) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
        LoadData(ctx, is, password, keyInputStream, new com.keepassdroid.UpdateStatus(), !com.keepassdroid.database.load.Importer.DEBUG);
    }


    public void LoadData(android.content.Context ctx, android.net.Uri uri, java.lang.String password, android.net.Uri keyfile) throws java.io.IOException, java.io.FileNotFoundException, com.keepassdroid.database.exception.InvalidDBException {
        LoadData(ctx, uri, password, keyfile, new com.keepassdroid.UpdateStatus(), !com.keepassdroid.database.load.Importer.DEBUG);
    }


    public void LoadData(android.content.Context ctx, android.net.Uri uri, java.lang.String password, android.net.Uri keyfile, com.keepassdroid.UpdateStatus status) throws java.io.IOException, java.io.FileNotFoundException, com.keepassdroid.database.exception.InvalidDBException {
        LoadData(ctx, uri, password, keyfile, status, !com.keepassdroid.database.load.Importer.DEBUG);
    }


    public void LoadData(android.content.Context ctx, android.net.Uri uri, java.lang.String password, android.net.Uri keyfile, com.keepassdroid.UpdateStatus status, boolean debug) throws java.io.IOException, java.io.FileNotFoundException, com.keepassdroid.database.exception.InvalidDBException {
        mUri = uri;
        readOnly = false;
        if (uri.getScheme().equals("file")) {
            java.io.File file;
            file = new java.io.File(uri.getPath());
            readOnly = !file.canWrite();
        }
        try {
            passUrisAsInputStreams(ctx, uri, password, keyfile, status, debug, 0);
        } catch (com.keepassdroid.database.exception.InvalidPasswordException e) {
            // Retry with rounds fix
            try {
                passUrisAsInputStreams(ctx, uri, password, keyfile, status, debug, getFixRounds(ctx));
            } catch (java.lang.Exception e2) {
                // Rethrow original exception
                throw e;
            }
        }
    }


    private long getFixRounds(android.content.Context ctx) {
        android.content.SharedPreferences prefs;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getLong(ctx.getString(com.android.keepass.R.string.roundsFix_key), ctx.getResources().getInteger(com.android.keepass.R.integer.roundsFix_default));
    }


    private void passUrisAsInputStreams(android.content.Context ctx, android.net.Uri uri, java.lang.String password, android.net.Uri keyfile, com.keepassdroid.UpdateStatus status, boolean debug, long roundsFix) throws java.io.IOException, java.io.FileNotFoundException, com.keepassdroid.database.exception.InvalidDBException {
        java.io.InputStream is;
        java.io.InputStream kfIs;
        try {
            is = com.keepassdroid.utils.UriUtil.getUriInputStream(ctx, uri);
        } catch (java.lang.Exception e) {
            android.util.Log.e("KPD", "Database::LoadData", e);
            throw com.keepassdroid.database.exception.ContentFileNotFoundException.getInstance(uri);
        }
        try {
            kfIs = com.keepassdroid.utils.UriUtil.getUriInputStream(ctx, keyfile);
        } catch (java.lang.Exception e) {
            android.util.Log.e("KPD", "Database::LoadData", e);
            throw com.keepassdroid.database.exception.ContentFileNotFoundException.getInstance(keyfile);
        }
        LoadData(ctx, is, password, kfIs, status, debug, roundsFix);
    }


    public void LoadData(android.content.Context ctx, java.io.InputStream is, java.lang.String password, java.io.InputStream kfIs, boolean debug) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
        LoadData(ctx, is, password, kfIs, new com.keepassdroid.UpdateStatus(), debug);
    }


    public void LoadData(android.content.Context ctx, java.io.InputStream is, java.lang.String password, java.io.InputStream kfIs, com.keepassdroid.UpdateStatus status, boolean debug) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
        LoadData(ctx, is, password, kfIs, status, debug, 0);
    }


    public void LoadData(android.content.Context ctx, java.io.InputStream is, java.lang.String password, java.io.InputStream kfIs, com.keepassdroid.UpdateStatus status, boolean debug, long roundsFix) throws java.io.IOException, com.keepassdroid.database.exception.InvalidDBException {
        java.io.BufferedInputStream bis;
        bis = new java.io.BufferedInputStream(is);
        if (!bis.markSupported()) {
            throw new java.io.IOException("Input stream does not support mark.");
        }
        // We'll end up reading 8 bytes to identify the header. Might as well use two extra.
        bis.mark(10);
        com.keepassdroid.database.load.Importer imp;
        imp = com.keepassdroid.database.load.ImporterFactory.createImporter(bis, ctx.getFilesDir(), debug);
        bis.reset()// Return to the start
        ;// Return to the start

        pm = imp.openDatabase(bis, password, kfIs, status, roundsFix);
        if (pm != null) {
            com.keepassdroid.database.PwGroup root;
            root = pm.rootGroup;
            pm.populateGlobals(root);
            LoadData(ctx, pm, password, kfIs, status);
        }
        loaded = true;
    }


    public void LoadData(android.content.Context ctx, com.keepassdroid.database.PwDatabase pm, java.lang.String password, java.io.InputStream keyInputStream, com.keepassdroid.UpdateStatus status) {
        if (pm != null) {
            passwordEncodingError = !pm.validatePasswordEncoding(password);
        }
        searchHelper = new com.keepassdroid.search.SearchDbHelper(ctx);
        loaded = true;
    }


    public com.keepassdroid.database.PwGroup Search(java.lang.String str) {
        if (searchHelper == null) {
            return null;
        }
        return searchHelper.search(this, str);
    }


    public void SaveData(android.content.Context ctx) throws java.io.IOException, com.keepassdroid.database.exception.FileUriException, com.keepassdroid.database.exception.PwDbOutputException {
        SaveData(ctx, mUri);
    }


    public void SaveData(android.content.Context ctx, android.net.Uri uri) throws java.io.IOException, com.keepassdroid.database.exception.FileUriException, com.keepassdroid.database.exception.PwDbOutputException {
        if (uri.getScheme().equals("file")) {
            java.lang.String filename;
            filename = uri.getPath();
            java.io.File tempFile;
            tempFile = new java.io.File(filename + ".tmp");
            try {
                saveFile(tempFile);
                java.io.File orig;
                orig = new java.io.File(filename);
                if (!tempFile.renameTo(orig)) {
                    throw new java.io.IOException("Failed to store database.");
                }
            } catch (java.io.IOException e) {
                try {
                    // Retry without temp file
                    java.io.File db;
                    db = new java.io.File(filename);
                    saveFile(db);
                } catch (java.io.IOException retryException) {
                    throw new com.keepassdroid.database.exception.FileUriException(retryException);
                }
            }
        } else {
            java.io.OutputStream os;
            try {
                os = ctx.getContentResolver().openOutputStream(uri, "rwt");
            } catch (java.lang.Exception e) {
                throw new java.io.IOException("Failed to store database.");
            }
            if (os == null) {
                throw new java.io.IOException("Failed to store database.");
            }
            com.keepassdroid.database.save.PwDbOutput pmo;
            pmo = com.keepassdroid.database.save.PwDbOutput.getInstance(pm, os);
            pmo.output();
            os.close();
        }
        mUri = uri;
    }


    private void saveFile(java.io.File db) throws java.io.IOException, com.keepassdroid.database.exception.PwDbOutputException {
        java.io.FileOutputStream fos;
        fos = new java.io.FileOutputStream(db);
        com.keepassdroid.database.save.PwDbOutput pmo;
        pmo = com.keepassdroid.database.save.PwDbOutput.getInstance(pm, fos);
        pmo.output();
        fos.close();
        // Force data to disk before continuing
        try {
            fos.getFD().sync();
        } catch (java.io.SyncFailedException e) {
            // Ignore if fsync fails. We tried.
        }
    }


    public void clear(android.content.Context context) {
        dirty.clear();
        drawFactory.clear();
        // Delete the cache of the database if present
        if (pm != null)
            pm.clearCache();

        // In all cases, delete all the files in the temp dir
        try {
            org.apache.commons.io.FileUtils.cleanDirectory(context.getFilesDir());
        } catch (java.io.IOException e) {
            android.util.Log.e(com.keepassdroid.Database.TAG, "Unable to clear the directory cache.", e);
        }
        pm = null;
        mUri = null;
        loaded = false;
        passwordEncodingError = false;
    }


    public void markAllGroupsAsDirty() {
        for (com.keepassdroid.database.PwGroup group : pm.getGroups()) {
            dirty.add(group);
        }
        // TODO: This should probably be abstracted out
        // The root group in v3 is not an 'official' group
        if (pm instanceof com.keepassdroid.database.PwDatabaseV3) {
            dirty.add(pm.rootGroup);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
