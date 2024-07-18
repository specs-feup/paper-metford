/* Copyright 2009-2013 Brian Pellin.

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
public class AddEntry extends com.keepassdroid.database.edit.RunnableOnFinish {
    static final int MUID_STATIC = getMUID();
    protected com.keepassdroid.Database mDb;

    private com.keepassdroid.database.PwEntry mEntry;

    private android.content.Context ctx;

    public static com.keepassdroid.database.edit.AddEntry getInstance(android.content.Context ctx, com.keepassdroid.Database db, com.keepassdroid.database.PwEntry entry, com.keepassdroid.database.edit.OnFinish finish) {
        return new com.keepassdroid.database.edit.AddEntry(ctx, db, entry, finish);
    }


    protected AddEntry(android.content.Context ctx, com.keepassdroid.Database db, com.keepassdroid.database.PwEntry entry, com.keepassdroid.database.edit.OnFinish finish) {
        super(finish);
        mDb = db;
        mEntry = entry;
        this.ctx = ctx;
        mFinish = new com.keepassdroid.database.edit.AddEntry.AfterAdd(mFinish);
    }


    @java.lang.Override
    public void run() {
        mDb.pm.addEntryTo(mEntry, mEntry.getParent());
        // Commit to disk
        com.keepassdroid.database.edit.SaveDB save;
        save = new com.keepassdroid.database.edit.SaveDB(ctx, mDb, mFinish);
        save.run();
    }


    private class AfterAdd extends com.keepassdroid.database.edit.OnFinish {
        public AfterAdd(com.keepassdroid.database.edit.OnFinish finish) {
            super(finish);
        }


        @java.lang.Override
        public void run() {
            com.keepassdroid.database.PwDatabase pm;
            pm = mDb.pm;
            if (mSuccess) {
                com.keepassdroid.database.PwGroup parent;
                parent = mEntry.getParent();
                // Mark parent group dirty
                mDb.dirty.add(parent);
            } else {
                pm.removeEntryFrom(mEntry, mEntry.getParent());
            }
            super.run();
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
