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
import com.keepassdroid.database.exception.InvalidKeyFileException;
import com.keepassdroid.database.PwDatabase;
import com.keepassdroid.utils.UriUtil;
import android.content.DialogInterface;
import java.io.InputStream;
import java.io.IOException;
import android.net.Uri;
import com.keepassdroid.dialog.PasswordEncodingDialogHelper;
import android.content.Context;
import com.keepassdroid.Database;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SetPassword extends com.keepassdroid.database.edit.RunnableOnFinish {
    static final int MUID_STATIC = getMUID();
    private java.lang.String mPassword;

    private android.net.Uri mKeyfile;

    private com.keepassdroid.Database mDb;

    private boolean mDontSave;

    private android.content.Context ctx;

    public SetPassword(android.content.Context ctx, com.keepassdroid.Database db, java.lang.String password, android.net.Uri keyfile, com.keepassdroid.database.edit.OnFinish finish) {
        this(ctx, db, password, keyfile, finish, false);
    }


    public SetPassword(android.content.Context ctx, com.keepassdroid.Database db, java.lang.String password, android.net.Uri keyfile, com.keepassdroid.database.edit.OnFinish finish, boolean dontSave) {
        super(finish);
        mDb = db;
        mPassword = password;
        mKeyfile = keyfile;
        mDontSave = dontSave;
        this.ctx = ctx;
    }


    public boolean validatePassword(android.content.Context ctx, android.content.DialogInterface.OnClickListener onclick) {
        if (!mDb.pm.validatePasswordEncoding(mPassword)) {
            com.keepassdroid.dialog.PasswordEncodingDialogHelper dialog;
            dialog = new com.keepassdroid.dialog.PasswordEncodingDialogHelper();
            dialog.show(ctx, onclick, true);
            return false;
        }
        return true;
    }


    @java.lang.Override
    public void run() {
        com.keepassdroid.database.PwDatabase pm;
        pm = mDb.pm;
        byte[] backupKey;
        backupKey = new byte[pm.masterKey.length];
        java.lang.System.arraycopy(pm.masterKey, 0, backupKey, 0, backupKey.length);
        // Set key
        try {
            java.io.InputStream is;
            is = com.keepassdroid.utils.UriUtil.getUriInputStream(ctx, mKeyfile);
            pm.setMasterKey(mPassword, is);
        } catch (com.keepassdroid.database.exception.InvalidKeyFileException e) {
            erase(backupKey);
            finish(false, e.getMessage());
            return;
        } catch (java.io.IOException e) {
            erase(backupKey);
            finish(false, e.getMessage());
            return;
        }
        // Save Database
        mFinish = new com.keepassdroid.database.edit.SetPassword.AfterSave(backupKey, mFinish);
        com.keepassdroid.database.edit.SaveDB save;
        save = new com.keepassdroid.database.edit.SaveDB(ctx, mDb, mFinish, mDontSave);
        save.run();
    }


    private class AfterSave extends com.keepassdroid.database.edit.OnFinish {
        private byte[] mBackup;

        public AfterSave(byte[] backup, com.keepassdroid.database.edit.OnFinish finish) {
            super(finish);
            mBackup = backup;
        }


        @java.lang.Override
        public void run() {
            if (!mSuccess) {
                // Erase the current master key
                erase(mDb.pm.masterKey);
                mDb.pm.masterKey = mBackup;
            }
            super.run();
        }

    }

    /**
     * Overwrite the array as soon as we don't need it to avoid keeping the extra data in memory
     *
     * @param array
     */
    private void erase(byte[] array) {
        if (array == null)
            return;

        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
