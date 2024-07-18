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
package com.keepassdroid.database.edit;
import com.keepassdroid.database.exception.KeyFileEmptyException;
import com.keepassdroid.database.exception.InvalidDBException;
import android.content.SharedPreferences;
import com.keepassdroid.database.exception.ArcFourException;
import com.keepassdroid.app.App;
import com.keepassdroid.database.exception.InvalidKeyFileException;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.keepassdroid.database.exception.InvalidDBSignatureException;
import android.net.Uri;
import android.preference.PreferenceManager;
import com.keepassdroid.fragments.Android11WarningFragment;
import com.keepassdroid.database.exception.InvalidDBVersionException;
import com.android.keepass.R;
import com.keepassdroid.database.exception.InvalidPasswordException;
import com.keepassdroid.database.exception.ContentFileNotFoundException;
import android.content.Context;
import com.keepassdroid.Database;
import com.keepassdroid.database.exception.InvalidAlgorithmException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class LoadDB extends com.keepassdroid.database.edit.RunnableOnFinish {
    static final int MUID_STATIC = getMUID();
    private android.net.Uri mUri;

    private java.lang.String mPass;

    private android.net.Uri mKey;

    private com.keepassdroid.Database mDb;

    private android.content.Context mCtx;

    private boolean mRememberKeyfile;

    public LoadDB(com.keepassdroid.Database db, android.content.Context ctx, android.net.Uri uri, java.lang.String pass, android.net.Uri key, com.keepassdroid.database.edit.OnFinish finish) {
        super(finish);
        mDb = db;
        mCtx = ctx;
        mUri = uri;
        mPass = pass;
        mKey = key;
        android.content.SharedPreferences prefs;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
        mRememberKeyfile = prefs.getBoolean(ctx.getString(com.android.keepass.R.string.keyfile_key), ctx.getResources().getBoolean(com.android.keepass.R.bool.keyfile_default));
    }


    @java.lang.Override
    public void run() {
        try {
            mDb.LoadData(mCtx, mUri, mPass, mKey, mStatus);
            saveFileData(mUri, mKey);
        } catch (com.keepassdroid.database.exception.ArcFourException e) {
            finish(false, mCtx.getString(com.android.keepass.R.string.error_arc4));
            return;
        } catch (com.keepassdroid.database.exception.InvalidPasswordException e) {
            finish(false, mCtx.getString(com.android.keepass.R.string.InvalidPassword));
            return;
        } catch (com.keepassdroid.database.exception.ContentFileNotFoundException e) {
            finish(false, mCtx.getString(com.android.keepass.R.string.file_not_found_content));
            return;
        } catch (java.io.FileNotFoundException e) {
            if ((mUri != null) && com.keepassdroid.fragments.Android11WarningFragment.showAndroid11Warning(mUri)) {
                finish(false, new com.keepassdroid.fragments.Android11WarningFragment());
                return;
            }
            finish(false, mCtx.getString(com.android.keepass.R.string.FileNotFound));
            return;
        } catch (java.io.IOException e) {
            finish(false, e.getMessage());
            return;
        } catch (com.keepassdroid.database.exception.KeyFileEmptyException e) {
            finish(false, mCtx.getString(com.android.keepass.R.string.keyfile_is_empty));
            return;
        } catch (com.keepassdroid.database.exception.InvalidAlgorithmException e) {
            finish(false, mCtx.getString(com.android.keepass.R.string.invalid_algorithm));
            return;
        } catch (com.keepassdroid.database.exception.InvalidKeyFileException e) {
            finish(false, mCtx.getString(com.android.keepass.R.string.keyfile_does_not_exist));
            return;
        } catch (com.keepassdroid.database.exception.InvalidDBSignatureException e) {
            finish(false, mCtx.getString(com.android.keepass.R.string.invalid_db_sig));
            return;
        } catch (com.keepassdroid.database.exception.InvalidDBVersionException e) {
            finish(false, mCtx.getString(com.android.keepass.R.string.unsupported_db_version));
            return;
        } catch (com.keepassdroid.database.exception.InvalidDBException e) {
            finish(false, mCtx.getString(com.android.keepass.R.string.error_invalid_db));
            return;
        } catch (java.lang.OutOfMemoryError e) {
            finish(false, mCtx.getString(com.android.keepass.R.string.error_out_of_memory));
            return;
        }
        finish(true);
    }


    private void saveFileData(android.net.Uri uri, android.net.Uri key) {
        if (!mRememberKeyfile) {
            key = null;
        }
        com.keepassdroid.app.App.getFileHistory().createFile(uri, key);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
