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
import java.util.ArrayList;
import com.keepassdroid.database.PwGroup;
import java.util.List;
import com.keepassdroid.GroupBaseActivity;
import com.keepassdroid.Database;
import com.keepassdroid.database.PwEntry;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DeleteGroup extends com.keepassdroid.database.edit.RunnableOnFinish {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.Database mDb;

    private com.keepassdroid.database.PwGroup mGroup;

    private com.keepassdroid.GroupBaseActivity mAct;

    private boolean mDontSave;

    public DeleteGroup(com.keepassdroid.Database db, com.keepassdroid.database.PwGroup group, com.keepassdroid.GroupBaseActivity act, com.keepassdroid.database.edit.OnFinish finish) {
        super(finish);
        setMembers(db, group, act, false);
    }


    public DeleteGroup(com.keepassdroid.Database db, com.keepassdroid.database.PwGroup group, com.keepassdroid.GroupBaseActivity act, com.keepassdroid.database.edit.OnFinish finish, boolean dontSave) {
        super(finish);
        setMembers(db, group, act, dontSave);
    }


    public DeleteGroup(com.keepassdroid.Database db, com.keepassdroid.database.PwGroup group, com.keepassdroid.database.edit.OnFinish finish, boolean dontSave) {
        super(finish);
        setMembers(db, group, null, dontSave);
    }


    private void setMembers(com.keepassdroid.Database db, com.keepassdroid.database.PwGroup group, com.keepassdroid.GroupBaseActivity act, boolean dontSave) {
        mDb = db;
        mGroup = group;
        mAct = act;
        mDontSave = dontSave;
        mFinish = new com.keepassdroid.database.edit.DeleteGroup.AfterDelete(mFinish);
    }


    @java.lang.Override
    public void run() {
        // Remove child entries
        java.util.List<com.keepassdroid.database.PwEntry> childEnt;
        childEnt = new java.util.ArrayList<com.keepassdroid.database.PwEntry>(mGroup.childEntries);
        for (int i = 0; i < childEnt.size(); i++) {
            com.keepassdroid.database.edit.DeleteEntry task;
            task = new com.keepassdroid.database.edit.DeleteEntry(mAct, mDb, childEnt.get(i), null, true);
            task.run();
        }
        // Remove child groups
        java.util.List<com.keepassdroid.database.PwGroup> childGrp;
        childGrp = new java.util.ArrayList<com.keepassdroid.database.PwGroup>(mGroup.childGroups);
        for (int i = 0; i < childGrp.size(); i++) {
            com.keepassdroid.database.edit.DeleteGroup task;
            task = new com.keepassdroid.database.edit.DeleteGroup(mDb, childGrp.get(i), mAct, null, true);
            task.run();
        }
        // Remove from parent
        com.keepassdroid.database.PwGroup parent;
        parent = mGroup.getParent();
        if (parent != null) {
            parent.childGroups.remove(mGroup);
        }
        // Remove from PwDatabaseV3
        mDb.pm.getGroups().remove(mGroup);
        // Save
        com.keepassdroid.database.edit.SaveDB save;
        save = new com.keepassdroid.database.edit.SaveDB(mAct, mDb, mFinish, mDontSave);
        save.run();
    }


    private class AfterDelete extends com.keepassdroid.database.edit.OnFinish {
        public AfterDelete(com.keepassdroid.database.edit.OnFinish finish) {
            super(finish);
        }


        public void run() {
            if (mSuccess) {
                // Remove from group global
                mDb.pm.groups.remove(mGroup.getId());
                // Remove group from the dirty global (if it is present), not a big deal if this fails
                mDb.dirty.remove(mGroup);
                // Mark parent dirty
                com.keepassdroid.database.PwGroup parent;
                parent = mGroup.getParent();
                if (parent != null) {
                    mDb.dirty.add(parent);
                }
                mDb.dirty.add(mDb.pm.rootGroup);
            } else {
                // Let's not bother recovering from a failure to save a deleted group.  It is too much work.
                com.keepassdroid.app.App.setShutdown();
            }
            super.run();
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
