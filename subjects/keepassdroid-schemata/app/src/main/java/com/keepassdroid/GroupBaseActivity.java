/* Copyright 2009-2020 Brian Pellin.

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
package com.keepassdroid;
import com.keepassdroid.app.App;
import com.keepassdroid.view.GroupViewOnlyView;
import com.keepassdroid.settings.AppSettingsActivity;
import com.keepassdroid.utils.Util;
import com.android.keepass.R;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.BaseAdapter;
import com.android.keepass.KeePass;
import android.widget.ListAdapter;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.MenuItem;
import com.keepassdroid.database.edit.OnFinish;
import android.preference.PreferenceManager;
import android.view.View;
import android.content.ActivityNotFoundException;
import com.keepassdroid.database.PwGroup;
import android.widget.AdapterView;
import com.keepassdroid.view.ClickView;
import android.content.SharedPreferences.Editor;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class GroupBaseActivity extends com.keepassdroid.LockCloseListActivity {
    static final int MUID_STATIC = getMUID();
    protected android.widget.ListView mList;

    protected android.widget.ListAdapter mAdapter;

    public static final java.lang.String KEY_ENTRY = "entry";

    public static final java.lang.String KEY_MODE = "mode";

    private android.content.SharedPreferences prefs;

    protected com.keepassdroid.database.PwGroup mGroup;

    @java.lang.Override
    protected void onResume() {
        super.onResume();
        refreshIfDirty();
    }


    public void refreshIfDirty() {
        com.keepassdroid.Database db;
        db = com.keepassdroid.app.App.getDB();
        if (db.dirty.contains(mGroup)) {
            db.dirty.remove(mGroup);
            ((android.widget.BaseAdapter) (mAdapter)).notifyDataSetChanged();
        }
    }


    protected void onListItemClick(android.widget.ListView l, android.view.View v, int position, long id) {
        com.keepassdroid.view.ClickView cv;
        cv = ((com.keepassdroid.view.ClickView) (mAdapter.getView(position, null, null)));
        cv.onClick();
    }


    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // GroupBaseActivity_0_LengthyGUICreationOperatorMutator
            case 200: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    // Likely the app has been killed exit the activity
    if (!com.keepassdroid.app.App.getDB().Loaded()) {
        finish();
        return;
    }
    prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
    this.invalidateOptionsMenu();
    setContentView(new com.keepassdroid.view.GroupViewOnlyView(this));
    setResult(com.android.keepass.KeePass.EXIT_NORMAL);
    androidx.appcompat.widget.Toolbar toolbar;
    switch(MUID_STATIC) {
        // GroupBaseActivity_1_InvalidViewFocusOperatorMutator
        case 1200: {
            /**
            * Inserted by Kadabra
            */
            toolbar = ((androidx.appcompat.widget.Toolbar) (findViewById(com.android.keepass.R.id.toolbar)));
            toolbar.requestFocus();
            break;
        }
        // GroupBaseActivity_2_ViewComponentNotVisibleOperatorMutator
        case 2200: {
            /**
            * Inserted by Kadabra
            */
            toolbar = ((androidx.appcompat.widget.Toolbar) (findViewById(com.android.keepass.R.id.toolbar)));
            toolbar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        toolbar = ((androidx.appcompat.widget.Toolbar) (findViewById(com.android.keepass.R.id.toolbar)));
        break;
    }
}
setSupportActionBar(toolbar);
styleScrollBars();
}


protected void styleScrollBars() {
ensureCorrectListView();
mList.setScrollBarStyle(android.view.View.SCROLLBARS_INSIDE_INSET);
mList.setTextFilterEnabled(true);
}


protected void setGroupTitle() {
if (mGroup != null) {
    java.lang.String name;
    name = mGroup.getName();
    if ((name != null) && (name.length() > 0)) {
        android.widget.TextView tv;
        switch(MUID_STATIC) {
            // GroupBaseActivity_3_InvalidViewFocusOperatorMutator
            case 3200: {
                /**
                * Inserted by Kadabra
                */
                tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.group_name)));
                tv.requestFocus();
                break;
            }
            // GroupBaseActivity_4_ViewComponentNotVisibleOperatorMutator
            case 4200: {
                /**
                * Inserted by Kadabra
                */
                tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.group_name)));
                tv.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.group_name)));
            break;
        }
    }
    if (tv != null) {
        tv.setText(name);
    }
} else {
    android.widget.TextView tv;
    switch(MUID_STATIC) {
        // GroupBaseActivity_5_InvalidViewFocusOperatorMutator
        case 5200: {
            /**
            * Inserted by Kadabra
            */
            tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.group_name)));
            tv.requestFocus();
            break;
        }
        // GroupBaseActivity_6_ViewComponentNotVisibleOperatorMutator
        case 6200: {
            /**
            * Inserted by Kadabra
            */
            tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.group_name)));
            tv.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.group_name)));
        break;
    }
}
if (tv != null) {
    tv.setText(getText(com.android.keepass.R.string.root));
}
}
}
}


protected void setGroupIcon() {
if (mGroup != null) {
android.widget.ImageView iv;
switch(MUID_STATIC) {
// GroupBaseActivity_7_InvalidViewFocusOperatorMutator
case 7200: {
    /**
    * Inserted by Kadabra
    */
    iv = ((android.widget.ImageView) (findViewById(com.android.keepass.R.id.icon)));
    iv.requestFocus();
    break;
}
// GroupBaseActivity_8_ViewComponentNotVisibleOperatorMutator
case 8200: {
    /**
    * Inserted by Kadabra
    */
    iv = ((android.widget.ImageView) (findViewById(com.android.keepass.R.id.icon)));
    iv.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
iv = ((android.widget.ImageView) (findViewById(com.android.keepass.R.id.icon)));
break;
}
}
com.keepassdroid.app.App.getDB().drawFactory.assignDrawableTo(iv, getResources(), mGroup.getIcon());
}
}


protected void setListAdapter(android.widget.ListAdapter adapter) {
ensureCorrectListView();
mAdapter = adapter;
mList.setAdapter(adapter);
}


protected android.widget.ListView getListView() {
ensureCorrectListView();
return mList;
}


private void ensureCorrectListView() {
switch(MUID_STATIC) {
// GroupBaseActivity_9_InvalidViewFocusOperatorMutator
case 9200: {
/**
* Inserted by Kadabra
*/
mList = ((android.widget.ListView) (findViewById(com.android.keepass.R.id.group_list)));
mList.requestFocus();
break;
}
// GroupBaseActivity_10_ViewComponentNotVisibleOperatorMutator
case 10200: {
/**
* Inserted by Kadabra
*/
mList = ((android.widget.ListView) (findViewById(com.android.keepass.R.id.group_list)));
mList.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mList = ((android.widget.ListView) (findViewById(com.android.keepass.R.id.group_list)));
break;
}
}
if (mList != null) {
mList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
public void onItemClick(android.widget.AdapterView<?> parent, android.view.View v, int position, long id) {
onListItemClick(((android.widget.ListView) (parent)), v, position, id);
}

});
}
}


@java.lang.Override
public boolean onCreateOptionsMenu(android.view.Menu menu) {
super.onCreateOptionsMenu(menu);
android.view.MenuInflater inflater;
inflater = getMenuInflater();
inflater.inflate(com.android.keepass.R.menu.group, menu);
return true;
}


private void setSortMenuText(android.view.Menu menu) {
boolean sortByName;
sortByName = false;
// Will be null if onPrepareOptionsMenu is called before onCreate
if (prefs != null) {
sortByName = prefs.getBoolean(getString(com.android.keepass.R.string.sort_key), getResources().getBoolean(com.android.keepass.R.bool.sort_default));
}
int resId;
if (sortByName) {
resId = com.android.keepass.R.string.sort_db;
} else {
resId = com.android.keepass.R.string.sort_name;
}
menu.findItem(com.android.keepass.R.id.menu_sort).setTitle(resId);
}


@java.lang.Override
public boolean onPrepareOptionsMenu(android.view.Menu menu) {
if (!super.onPrepareOptionsMenu(menu)) {
return false;
}
setSortMenuText(menu);
return true;
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
switch (item.getItemId()) {
case com.android.keepass.R.id.menu_donate :
try {
com.keepassdroid.utils.Util.gotoUrl(this, com.android.keepass.R.string.donate_url);
} catch (android.content.ActivityNotFoundException e) {
android.widget.Toast.makeText(this, com.android.keepass.R.string.error_failed_to_launch_link, android.widget.Toast.LENGTH_LONG).show();
return false;
}
return true;
case com.android.keepass.R.id.menu_lock :
com.keepassdroid.app.App.setShutdown();
setResult(com.android.keepass.KeePass.EXIT_LOCK);
finish();
return true;
case com.android.keepass.R.id.menu_search :
onSearchRequested();
return true;
case com.android.keepass.R.id.menu_app_settings :
com.keepassdroid.settings.AppSettingsActivity.Launch(this);
return true;
case com.android.keepass.R.id.menu_change_master_key :
setPassword();
return true;
case com.android.keepass.R.id.menu_sort :
toggleSort();
return true;
}
return super.onOptionsItemSelected(item);
}


private void toggleSort() {
// Toggle setting
java.lang.String sortKey;
sortKey = getString(com.android.keepass.R.string.sort_key);
boolean sortByName;
sortByName = prefs.getBoolean(sortKey, getResources().getBoolean(com.android.keepass.R.bool.sort_default));
android.content.SharedPreferences.Editor editor;
editor = prefs.edit();
editor.putBoolean(sortKey, !sortByName);
editor.apply();
// Refresh menu titles
this.invalidateOptionsMenu();
// Mark all groups as dirty now to refresh them on load
com.keepassdroid.Database db;
db = com.keepassdroid.app.App.getDB();
db.markAllGroupsAsDirty();
// We'll manually refresh this group so we can remove it
db.dirty.remove(mGroup);
// Tell the adapter to refresh it's list
((android.widget.BaseAdapter) (mAdapter)).notifyDataSetChanged();
}


private void setPassword() {
com.keepassdroid.SetPasswordDialog dialog;
dialog = new com.keepassdroid.SetPasswordDialog(this);
dialog.show();
}


public class RefreshTask extends com.keepassdroid.database.edit.OnFinish {
public RefreshTask(android.os.Handler handler) {
super(handler);
}


@java.lang.Override
public void run() {
if (mSuccess) {
refreshIfDirty();
} else {
displayMessage(com.keepassdroid.GroupBaseActivity.this);
}
}

}

@java.lang.Override
public void startActivityForResult(android.content.Intent intent, int requestCode, android.os.Bundle options) {
/* ACTION_SEARCH automatically forces a new task. This occurs when you open a kdb file in
another app such as Files or GoogleDrive and then Search for an entry. Here we remove the
FLAG_ACTIVITY_NEW_TASK flag bit allowing search to open it's activity in the current task.
 */
if (android.content.Intent.ACTION_SEARCH.equals(intent.getAction())) {
int flags;
flags = intent.getFlags();
flags &= ~android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
switch(MUID_STATIC) {
// GroupBaseActivity_11_RandomActionIntentDefinitionOperatorMutator
case 11200: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.setFlags(flags);
break;
}
}
}
super.startActivityForResult(intent, requestCode, options);
}


public class AfterDeleteGroup extends com.keepassdroid.database.edit.OnFinish {
public AfterDeleteGroup(android.os.Handler handler) {
super(handler);
}


@java.lang.Override
public void run() {
if (mSuccess) {
refreshIfDirty();
} else {
mHandler.post(new com.keepassdroid.UIToastTask(com.keepassdroid.GroupBaseActivity.this, "Unrecoverable error: " + mMessage));
com.keepassdroid.app.App.setShutdown();
finish();
}
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
