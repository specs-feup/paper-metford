/* Copyright 2009-2011 Brian Pellin.

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
import com.keepassdroid.database.PwGroup;
import android.content.Context;
import com.keepassdroid.Database;
import com.keepassdroid.database.PwEntry;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class UpdateEntry extends com.keepassdroid.database.edit.RunnableOnFinish {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.Database mDb;

    private com.keepassdroid.database.PwEntry mOldE;

    private com.keepassdroid.database.PwEntry mNewE;

    private android.content.Context ctx;

    public UpdateEntry(android.content.Context ctx, com.keepassdroid.Database db, com.keepassdroid.database.PwEntry oldE, com.keepassdroid.database.PwEntry newE, com.keepassdroid.database.edit.OnFinish finish) {
        super(finish);
        mDb = db;
        mOldE = oldE;
        mNewE = newE;
        this.ctx = ctx;
        // Keep backup of original values in case save fails
        com.keepassdroid.database.PwEntry backup;
        backup = ((com.keepassdroid.database.PwEntry) (mOldE.clone()));
        mFinish = new com.keepassdroid.database.edit.UpdateEntry.AfterUpdate(backup, finish);
    }


    @java.lang.Override
    public void run() {
        // Update entry with new values
        mOldE.assign(mNewE);
        mOldE.touch(true, true);
        // Commit to disk
        com.keepassdroid.database.edit.SaveDB save;
        save = new com.keepassdroid.database.edit.SaveDB(ctx, mDb, mFinish);
        save.run();
    }


    private class AfterUpdate extends com.keepassdroid.database.edit.OnFinish {
        private com.keepassdroid.database.PwEntry mBackup;

        public AfterUpdate(com.keepassdroid.database.PwEntry backup, com.keepassdroid.database.edit.OnFinish finish) {
            super(finish);
            mBackup = backup;
        }


        @java.lang.Override
        public void run() {
            if (mSuccess) {
                // Mark group dirty if title or icon changes
                if ((!mBackup.getTitle().equals(mNewE.getTitle())) || (!mBackup.getIcon().equals(mNewE.getIcon()))) {
                    com.keepassdroid.database.PwGroup parent;
                    parent = mBackup.getParent();
                    if (parent != null) {
                        // Resort entries
                        parent.sortEntriesByName();
                        // Mark parent group dirty
                        mDb.dirty.add(parent);
                    }
                }
            } else {
                // If we fail to save, back out changes to global structure
                mOldE.assign(mBackup);
            }
            super.run();
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
