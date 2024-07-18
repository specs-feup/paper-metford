/* Copyright 2010-2014 Brian Pellin.

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
package com.keepassdroid.view;
import com.android.keepass.R;
import com.keepassdroid.ProgressTask;
import com.keepassdroid.app.App;
import android.os.Handler;
import android.view.ContextMenu.ContextMenuInfo;
import com.keepassdroid.database.PwGroup;
import android.view.ContextMenu;
import android.view.MenuItem;
import com.keepassdroid.GroupBaseActivity;
import com.keepassdroid.database.edit.DeleteGroup;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwGroupViewV3 extends com.keepassdroid.view.PwGroupView {
    static final int MUID_STATIC = getMUID();
    private static final int MENU_DELETE = com.keepassdroid.view.PwGroupView.MENU_OPEN + 1;

    protected PwGroupViewV3(com.keepassdroid.GroupBaseActivity act, com.keepassdroid.database.PwGroup pw) {
        super(act, pw);
    }


    @java.lang.Override
    public void onCreateMenu(android.view.ContextMenu menu, android.view.ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateMenu(menu, menuInfo);
        if (!readOnly) {
            menu.add(0, com.keepassdroid.view.PwGroupViewV3.MENU_DELETE, 0, com.android.keepass.R.string.menu_delete);
        }
    }


    @java.lang.Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        if (!super.onContextItemSelected(item)) {
            switch (item.getItemId()) {
                case com.keepassdroid.view.PwGroupViewV3.MENU_DELETE :
                    android.os.Handler handler;
                    handler = new android.os.Handler();
                    com.keepassdroid.database.edit.DeleteGroup task;
                    task = new com.keepassdroid.database.edit.DeleteGroup(com.keepassdroid.app.App.getDB(), mPw, mAct, mAct.new AfterDeleteGroup(handler));
                    com.keepassdroid.ProgressTask pt;
                    pt = new com.keepassdroid.ProgressTask(mAct, task, com.android.keepass.R.string.saving_database);
                    pt.run();
                    return true;
            }
        }
        return false;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
