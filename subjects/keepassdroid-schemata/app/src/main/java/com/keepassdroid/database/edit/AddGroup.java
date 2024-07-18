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
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AddGroup extends com.keepassdroid.database.edit.RunnableOnFinish {
    static final int MUID_STATIC = getMUID();
    protected com.keepassdroid.Database mDb;

    private java.lang.String mName;

    private int mIconID;

    private com.keepassdroid.database.PwGroup mGroup;

    private com.keepassdroid.database.PwGroup mParent;

    private android.content.Context ctx;

    protected boolean mDontSave;

    public static com.keepassdroid.database.edit.AddGroup getInstance(android.content.Context ctx, com.keepassdroid.Database db, java.lang.String name, int iconid, com.keepassdroid.database.PwGroup parent, com.keepassdroid.database.edit.OnFinish finish, boolean dontSave) {
        return new com.keepassdroid.database.edit.AddGroup(ctx, db, name, iconid, parent, finish, dontSave);
    }


    private AddGroup(android.content.Context ctx, com.keepassdroid.Database db, java.lang.String name, int iconid, com.keepassdroid.database.PwGroup parent, com.keepassdroid.database.edit.OnFinish finish, boolean dontSave) {
        super(finish);
        mDb = db;
        mName = name;
        mIconID = iconid;
        mParent = parent;
        mDontSave = dontSave;
        this.ctx = ctx;
        mFinish = new com.keepassdroid.database.edit.AddGroup.AfterAdd(mFinish);
    }


    @java.lang.Override
    public void run() {
        com.keepassdroid.database.PwDatabase pm;
        pm = mDb.pm;
        // Generate new group
        mGroup = pm.createGroup();
        mGroup.initNewGroup(mName, pm.newGroupId());
        mGroup.icon = mDb.pm.iconFactory.getIcon(mIconID);
        pm.addGroupTo(mGroup, mParent);
        // mParent.sortGroupsByName();
        // Commit to disk
        com.keepassdroid.database.edit.SaveDB save;
        save = new com.keepassdroid.database.edit.SaveDB(ctx, mDb, mFinish, mDontSave);
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
                // Mark parent group dirty
                mDb.dirty.add(mParent);
            } else {
                pm.removeGroupFrom(mGroup, mParent);
            }
            super.run();
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
