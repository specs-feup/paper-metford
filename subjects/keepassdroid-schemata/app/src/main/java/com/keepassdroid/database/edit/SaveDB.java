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
import com.android.keepass.R;
import com.keepassdroid.database.exception.PwDbOutputException;
import java.io.IOException;
import com.keepassdroid.database.exception.FileUriException;
import com.keepassdroid.fragments.Android11WarningFragment;
import android.content.Context;
import com.keepassdroid.Database;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SaveDB extends com.keepassdroid.database.edit.RunnableOnFinish {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.Database mDb;

    private boolean mDontSave;

    private android.content.Context mCtx;

    public SaveDB(android.content.Context ctx, com.keepassdroid.Database db, com.keepassdroid.database.edit.OnFinish finish, boolean dontSave) {
        super(finish);
        mDb = db;
        mDontSave = dontSave;
        mCtx = ctx;
    }


    public SaveDB(android.content.Context ctx, com.keepassdroid.Database db, com.keepassdroid.database.edit.OnFinish finish) {
        super(finish);
        mDb = db;
        mDontSave = false;
        mCtx = ctx;
    }


    @java.lang.Override
    public void run() {
        if (!mDontSave) {
            try {
                mDb.SaveData(mCtx);
            } catch (java.io.IOException e) {
                finish(false, e.getMessage());
                return;
            } catch (com.keepassdroid.database.exception.FileUriException e) {
                if (com.keepassdroid.fragments.Android11WarningFragment.showAndroid11WarningOnThisVersion()) {
                    finish(false, new com.keepassdroid.fragments.Android11WarningFragment(com.android.keepass.R.string.Android11SaveFailed));
                } else {
                    finish(false, e.getMessage());
                }
                return;
            } catch (com.keepassdroid.database.exception.PwDbOutputException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
        finish(true);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
