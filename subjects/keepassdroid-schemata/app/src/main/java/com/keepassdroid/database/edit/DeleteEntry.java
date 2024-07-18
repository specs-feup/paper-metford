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
import com.keepassdroid.database.PwDatabase;
import com.keepassdroid.database.PwGroup;
import android.content.Context;
import com.keepassdroid.Database;
import com.keepassdroid.database.PwEntry;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Task to delete entries
 *
 * @author bpellin
 */
public class DeleteEntry extends com.keepassdroid.database.edit.RunnableOnFinish {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.Database mDb;

    private com.keepassdroid.database.PwEntry mEntry;

    private boolean mDontSave;

    private android.content.Context ctx;

    public DeleteEntry(android.content.Context ctx, com.keepassdroid.Database db, com.keepassdroid.database.PwEntry entry, com.keepassdroid.database.edit.OnFinish finish) {
        this(ctx, db, entry, finish, false);
    }


    public DeleteEntry(android.content.Context ctx, com.keepassdroid.Database db, com.keepassdroid.database.PwEntry entry, com.keepassdroid.database.edit.OnFinish finish, boolean dontSave) {
        super(finish);
        mDb = db;
        mEntry = entry;
        mDontSave = dontSave;
        this.ctx = ctx;
    }


    @java.lang.Override
    public void run() {
        com.keepassdroid.database.PwDatabase pm;
        pm = mDb.pm;
        com.keepassdroid.database.PwGroup parent;
        parent = mEntry.getParent();
        // Remove Entry from parent
        boolean recycle;
        recycle = pm.canRecycle(mEntry);
        if (recycle) {
            pm.recycle(mEntry);
        } else {
            pm.deleteEntry(mEntry);
        }
        // Save
        mFinish = new com.keepassdroid.database.edit.DeleteEntry.AfterDelete(mFinish, parent, mEntry, recycle);
        // Commit database
        com.keepassdroid.database.edit.SaveDB save;
        save = new com.keepassdroid.database.edit.SaveDB(ctx, mDb, mFinish, mDontSave);
        save.run();
    }


    private class AfterDelete extends com.keepassdroid.database.edit.OnFinish {
        private com.keepassdroid.database.PwGroup mParent;

        private com.keepassdroid.database.PwEntry mEntry;

        private boolean recycled;

        public AfterDelete(com.keepassdroid.database.edit.OnFinish finish, com.keepassdroid.database.PwGroup parent, com.keepassdroid.database.PwEntry entry, boolean r) {
            super(finish);
            mParent = parent;
            mEntry = entry;
            recycled = r;
        }


        @java.lang.Override
        public void run() {
            com.keepassdroid.database.PwDatabase pm;
            pm = mDb.pm;
            if (mSuccess) {
                // Mark parent dirty
                if (mParent != null) {
                    mDb.dirty.add(mParent);
                }
                if (recycled) {
                    com.keepassdroid.database.PwGroup recycleBin;
                    recycleBin = pm.getRecycleBin();
                    mDb.dirty.add(recycleBin);
                    mDb.dirty.add(mDb.pm.rootGroup);
                }
            } else if (recycled) {
                pm.undoRecycle(mEntry, mParent);
            } else {
                pm.undoDeleteEntry(mEntry, mParent);
            }
            super.run();
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
