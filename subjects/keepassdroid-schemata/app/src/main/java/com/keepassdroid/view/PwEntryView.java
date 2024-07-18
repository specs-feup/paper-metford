/* Copyright 2009-2014 Brian Pellin.

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
import com.keepassdroid.ProgressTask;
import com.keepassdroid.app.App;
import android.view.Menu;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import com.keepassdroid.database.PwEntry;
import com.android.keepass.R;
import com.keepassdroid.database.edit.DeleteEntry;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ImageView;
import com.keepassdroid.settings.PrefsUtil;
import android.widget.TextView;
import com.keepassdroid.EntryActivity;
import com.keepassdroid.GroupBaseActivity;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwEntryView extends com.keepassdroid.view.ClickView {
    static final int MUID_STATIC = getMUID();
    protected com.keepassdroid.GroupBaseActivity mAct;

    protected com.keepassdroid.database.PwEntry mPw;

    private android.widget.TextView mTv;

    private int mPos;

    protected static final int MENU_OPEN = android.view.Menu.FIRST;

    private static final int MENU_DELETE = com.keepassdroid.view.PwEntryView.MENU_OPEN + 1;

    public static com.keepassdroid.view.PwEntryView getInstance(com.keepassdroid.GroupBaseActivity act, com.keepassdroid.database.PwEntry pw, int pos) {
        return new com.keepassdroid.view.PwEntryView(act, pw, pos);
    }


    protected PwEntryView(com.keepassdroid.GroupBaseActivity act, com.keepassdroid.database.PwEntry pw, int pos) {
        super(act);
        mAct = act;
        android.view.View ev;
        ev = android.view.View.inflate(mAct, com.android.keepass.R.layout.entry_list_entry, null);
        switch(MUID_STATIC) {
            // PwEntryView_0_InvalidViewFocusOperatorMutator
            case 30: {
                /**
                * Inserted by Kadabra
                */
                mTv = ((android.widget.TextView) (ev.findViewById(com.android.keepass.R.id.entry_text)));
                mTv.requestFocus();
                break;
            }
            // PwEntryView_1_ViewComponentNotVisibleOperatorMutator
            case 1030: {
                /**
                * Inserted by Kadabra
                */
                mTv = ((android.widget.TextView) (ev.findViewById(com.android.keepass.R.id.entry_text)));
                mTv.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            mTv = ((android.widget.TextView) (ev.findViewById(com.android.keepass.R.id.entry_text)));
            break;
        }
    }
    mTv.setTextSize(com.keepassdroid.settings.PrefsUtil.getListTextSize(act));
    populateView(ev, pw, pos);
    android.widget.LinearLayout.LayoutParams lp;
    lp = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.FILL_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
    addView(ev, lp);
}


private void populateView(android.view.View ev, com.keepassdroid.database.PwEntry pw, int pos) {
    mPw = pw;
    mPos = pos;
    android.widget.ImageView iv;
    switch(MUID_STATIC) {
        // PwEntryView_2_InvalidViewFocusOperatorMutator
        case 2030: {
            /**
            * Inserted by Kadabra
            */
            iv = ((android.widget.ImageView) (ev.findViewById(com.android.keepass.R.id.entry_icon)));
            iv.requestFocus();
            break;
        }
        // PwEntryView_3_ViewComponentNotVisibleOperatorMutator
        case 3030: {
            /**
            * Inserted by Kadabra
            */
            iv = ((android.widget.ImageView) (ev.findViewById(com.android.keepass.R.id.entry_icon)));
            iv.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        iv = ((android.widget.ImageView) (ev.findViewById(com.android.keepass.R.id.entry_icon)));
        break;
    }
}
com.keepassdroid.app.App.getDB().drawFactory.assignDrawableTo(iv, getResources(), pw.getIcon());
mTv.setText(mPw.getDisplayTitle());
}


public void convertView(com.keepassdroid.database.PwEntry pw, int pos) {
populateView(this, pw, pos);
}


public void refreshTitle() {
mTv.setText(mPw.getDisplayTitle());
}


public void onClick() {
launchEntry();
}


private void launchEntry() {
com.keepassdroid.EntryActivity.Launch(mAct, mPw, mPos);
}


private void deleteEntry() {
android.os.Handler handler;
handler = new android.os.Handler();
com.keepassdroid.database.edit.DeleteEntry task;
task = new com.keepassdroid.database.edit.DeleteEntry(mAct, com.keepassdroid.app.App.getDB(), mPw, mAct.new RefreshTask(handler));
com.keepassdroid.ProgressTask pt;
pt = new com.keepassdroid.ProgressTask(mAct, task, com.android.keepass.R.string.saving_database);
pt.run();
}


@java.lang.Override
public void onCreateMenu(android.view.ContextMenu menu, android.view.ContextMenu.ContextMenuInfo menuInfo) {
menu.add(0, com.keepassdroid.view.PwEntryView.MENU_OPEN, 0, com.android.keepass.R.string.menu_open);
if (!readOnly) {
    menu.add(0, com.keepassdroid.view.PwEntryView.MENU_DELETE, 0, com.android.keepass.R.string.menu_delete);
}
}


@java.lang.Override
public boolean onContextItemSelected(android.view.MenuItem item) {
switch (item.getItemId()) {
    case com.keepassdroid.view.PwEntryView.MENU_OPEN :
        launchEntry();
        return true;
    case com.keepassdroid.view.PwEntryView.MENU_DELETE :
        deleteEntry();
        return true;
    default :
        return false;
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
