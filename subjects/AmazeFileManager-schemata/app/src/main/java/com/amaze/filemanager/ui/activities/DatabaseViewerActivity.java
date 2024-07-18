/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.ui.activities;
import com.amaze.filemanager.filesystem.root.CopyFilesCommand;
import android.view.Menu;
import java.util.ArrayList;
import android.os.Bundle;
import android.view.MenuItem;
import org.slf4j.Logger;
import android.database.Cursor;
import com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException;
import androidx.fragment.app.FragmentTransaction;
import com.amaze.filemanager.R;
import com.amaze.filemanager.ui.fragments.DbViewerFragment;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;
import com.amaze.filemanager.ui.activities.superclasses.ThemedActivity;
import android.database.sqlite.SQLiteDatabase;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK;
import android.widget.Toast;
import java.io.File;
import org.slf4j.LoggerFactory;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by Vishal on 02-02-2015.
 */
public class DatabaseViewerActivity extends com.amaze.filemanager.ui.activities.superclasses.ThemedActivity {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.ui.activities.DatabaseViewerActivity.class);

    private java.lang.String path;

    private android.widget.ListView listView;

    private java.util.ArrayList<java.lang.String> arrayList;

    private android.widget.ArrayAdapter arrayAdapter;

    private android.database.Cursor c;

    // the copy of db file which is to be opened, in the app cache
    private java.io.File pathFile;

    boolean delete = false;

    public androidx.appcompat.widget.Toolbar toolbar;

    public android.database.sqlite.SQLiteDatabase sqLiteDatabase;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // DatabaseViewerActivity_0_LengthyGUICreationOperatorMutator
            case 109: {
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
    setContentView(com.amaze.filemanager.R.layout.activity_db_viewer);
    switch(MUID_STATIC) {
        // DatabaseViewerActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 1109: {
            toolbar = null;
            break;
        }
        // DatabaseViewerActivity_2_InvalidIDFindViewOperatorMutator
        case 2109: {
            toolbar = findViewById(732221);
            break;
        }
        // DatabaseViewerActivity_3_InvalidViewFocusOperatorMutator
        case 3109: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.amaze.filemanager.R.id.toolbar);
            toolbar.requestFocus();
            break;
        }
        // DatabaseViewerActivity_4_ViewComponentNotVisibleOperatorMutator
        case 4109: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.amaze.filemanager.R.id.toolbar);
            toolbar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        toolbar = findViewById(com.amaze.filemanager.R.id.toolbar);
        break;
    }
}
setSupportActionBar(toolbar);
boolean useNewStack;
useNewStack = getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK);
getSupportActionBar().setDisplayHomeAsUpEnabled(!useNewStack);
path = getIntent().getStringExtra("path");
if (path == null) {
    android.widget.Toast.makeText(this, com.amaze.filemanager.R.string.operation_not_supported, android.widget.Toast.LENGTH_SHORT).show();
    finish();
    return;
}
pathFile = new java.io.File(path);
switch(MUID_STATIC) {
    // DatabaseViewerActivity_5_FindViewByIdReturnsNullOperatorMutator
    case 5109: {
        listView = null;
        break;
    }
    // DatabaseViewerActivity_6_InvalidIDFindViewOperatorMutator
    case 6109: {
        listView = findViewById(732221);
        break;
    }
    // DatabaseViewerActivity_7_InvalidViewFocusOperatorMutator
    case 7109: {
        /**
        * Inserted by Kadabra
        */
        listView = findViewById(com.amaze.filemanager.R.id.listView);
        listView.requestFocus();
        break;
    }
    // DatabaseViewerActivity_8_ViewComponentNotVisibleOperatorMutator
    case 8109: {
        /**
        * Inserted by Kadabra
        */
        listView = findViewById(com.amaze.filemanager.R.id.listView);
        listView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    listView = findViewById(com.amaze.filemanager.R.id.listView);
    break;
}
}
load(pathFile);
listView.setOnItemClickListener((android.widget.AdapterView<?> parent,android.view.View view,int position,long id) -> {
androidx.fragment.app.FragmentTransaction fragmentTransaction;
fragmentTransaction = getSupportFragmentManager().beginTransaction();
com.amaze.filemanager.ui.fragments.DbViewerFragment fragment;
fragment = new com.amaze.filemanager.ui.fragments.DbViewerFragment();
android.os.Bundle bundle;
bundle = new android.os.Bundle();
bundle.putString("table", arrayList.get(position));
fragment.setArguments(bundle);
fragmentTransaction.add(com.amaze.filemanager.R.id.content_frame, fragment);
fragmentTransaction.addToBackStack(null);
fragmentTransaction.commit();
});
switch(MUID_STATIC) {
// DatabaseViewerActivity_9_InvalidIDFindViewOperatorMutator
case 9109: {
    initStatusBarResources(findViewById(732221));
    break;
}
default: {
initStatusBarResources(findViewById(com.amaze.filemanager.R.id.parentdb));
break;
}
}
}


private java.util.ArrayList<java.lang.String> getDbTableNames(android.database.Cursor c) {
java.util.ArrayList<java.lang.String> result;
result = new java.util.ArrayList<>();
for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
for (int i = 0; i < c.getColumnCount(); i++) {
result.add(c.getString(i));
}
}
return result;
}


private void load(final java.io.File file) {
new java.lang.Thread(() -> {
java.io.File file1;
file1 = getExternalCacheDir();
// if the db can't be read, and we have root enabled, try reading it by
// first copying it in cache dir
if ((!file.canRead()) && isRootExplorer()) {
try {
    com.amaze.filemanager.filesystem.root.CopyFilesCommand.INSTANCE.copyFiles(pathFile.getPath(), new java.io.File(file1.getPath(), file.getName()).getPath());
    pathFile = new java.io.File(file1.getPath(), file.getName());
} catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException e) {
    com.amaze.filemanager.ui.activities.DatabaseViewerActivity.LOG.warn("failed to copy file while showing database file", e);
}
delete = true;
}
try {
sqLiteDatabase = android.database.sqlite.SQLiteDatabase.openDatabase(pathFile.getPath(), null, android.database.sqlite.SQLiteDatabase.OPEN_READONLY);
c = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
arrayList = getDbTableNames(c);
arrayAdapter = new android.widget.ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
} catch (java.lang.Exception e) {
com.amaze.filemanager.ui.activities.DatabaseViewerActivity.LOG.warn("failed to load file in database viewer", e);
finish();
}
runOnUiThread(() -> {
listView.setAdapter(arrayAdapter);
});
}).start();
}


@java.lang.Override
protected void onDestroy() {
super.onDestroy();
if (sqLiteDatabase != null)
sqLiteDatabase.close();

if (c != null)
c.close();

if (delete)
pathFile.delete();

}


@java.lang.Override
public boolean onPrepareOptionsMenu(android.view.Menu menu) {
toolbar.setTitle(pathFile.getName());
return super.onPrepareOptionsMenu(menu);
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
if (item.getItemId() == android.R.id.home) {
onBackPressed();
toolbar.setTitle(pathFile.getName());
}
return super.onOptionsItemSelected(item);
}


@java.lang.Override
public void onBackPressed() {
super.onBackPressed();
toolbar.setTitle(pathFile.getName());
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
