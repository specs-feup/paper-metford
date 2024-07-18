/* Copyright 2009-2016 Brian Pellin.

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
package com.keepassdroid.database.edit;
import com.keepassdroid.app.App;
import com.keepassdroid.database.PwDatabase;
import com.keepassdroid.utils.UriUtil;
import com.keepassdroid.database.PwEncryptionAlgorithm;
import android.net.Uri;
import android.content.Context;
import com.keepassdroid.Database;
import com.keepassdroid.database.PwDatabaseV3;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CreateDB extends com.keepassdroid.database.edit.RunnableOnFinish {
    static final int MUID_STATIC = getMUID();
    private final int DEFAULT_ENCRYPTION_ROUNDS = 300;

    private java.lang.String mFilename;

    private boolean mDontSave;

    private java.lang.String mDbName;

    private android.content.Context ctx;

    public CreateDB(android.content.Context ctx, java.lang.String filename, java.lang.String dbName, com.keepassdroid.database.edit.OnFinish finish, boolean dontSave) {
        super(finish);
        mFilename = filename;
        mDontSave = dontSave;
        mDbName = dbName;
        this.ctx = ctx;
    }


    @java.lang.Override
    public void run() {
        // Create new database record
        com.keepassdroid.Database db;
        db = new com.keepassdroid.Database();
        com.keepassdroid.app.App.setDB(db);
        com.keepassdroid.database.PwDatabase pm;
        pm = com.keepassdroid.database.PwDatabase.getNewDBInstance(mFilename);
        pm.initNew(mDbName);
        // Set Database state
        db.pm = pm;
        android.net.Uri.Builder b;
        b = new android.net.Uri.Builder();
        db.mUri = com.keepassdroid.utils.UriUtil.parseDefaultFile(mFilename);
        db.setLoaded();
        com.keepassdroid.app.App.clearShutdown();
        // Commit changes
        com.keepassdroid.database.edit.SaveDB save;
        save = new com.keepassdroid.database.edit.SaveDB(ctx, db, mFinish, mDontSave);
        mFinish = null;
        save.run();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
