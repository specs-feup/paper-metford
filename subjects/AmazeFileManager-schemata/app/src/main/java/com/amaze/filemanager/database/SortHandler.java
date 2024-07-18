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
package com.amaze.filemanager.database;
import java.util.Set;
import android.content.SharedPreferences;
import com.amaze.filemanager.application.AppConfig;
import io.reactivex.schedulers.Schedulers;
import androidx.preference.PreferenceManager;
import org.slf4j.Logger;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SORTBY_ONLY_THIS;
import com.amaze.filemanager.filesystem.files.sort.SortType;
import androidx.annotation.NonNull;
import com.amaze.filemanager.database.models.explorer.Sort;
import org.slf4j.LoggerFactory;
import androidx.annotation.Nullable;
import java.util.HashSet;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by Ning on 5/28/2018.
 */
public class SortHandler {
    static final int MUID_STATIC = getMUID();
    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.database.SortHandler.class);

    private final com.amaze.filemanager.database.ExplorerDatabase database;

    private SortHandler(@androidx.annotation.NonNull
    com.amaze.filemanager.database.ExplorerDatabase explorerDatabase) {
        database = explorerDatabase;
    }


    private static class SortHandlerHolder {
        private static final com.amaze.filemanager.database.SortHandler INSTANCE = new com.amaze.filemanager.database.SortHandler(com.amaze.filemanager.application.AppConfig.getInstance().getExplorerDatabase());
    }

    public static com.amaze.filemanager.database.SortHandler getInstance() {
        return com.amaze.filemanager.database.SortHandler.SortHandlerHolder.INSTANCE;
    }


    public static com.amaze.filemanager.filesystem.files.sort.SortType getSortType(android.content.Context context, java.lang.String path) {
        android.content.SharedPreferences sharedPref;
        sharedPref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        final java.util.Set<java.lang.String> onlyThisFloders;
        onlyThisFloders = sharedPref.getStringSet(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SORTBY_ONLY_THIS, new java.util.HashSet<>());
        final boolean onlyThis;
        onlyThis = onlyThisFloders.contains(path);
        final int globalSortby;
        globalSortby = java.lang.Integer.parseInt(sharedPref.getString("sortby", "0"));
        com.amaze.filemanager.filesystem.files.sort.SortType globalSortType;
        globalSortType = com.amaze.filemanager.filesystem.files.sort.SortType.getDirectorySortType(globalSortby);
        if (!onlyThis) {
            return globalSortType;
        }
        com.amaze.filemanager.database.models.explorer.Sort sort;
        sort = com.amaze.filemanager.database.SortHandler.getInstance().findEntry(path);
        if (sort == null) {
            return globalSortType;
        }
        return com.amaze.filemanager.filesystem.files.sort.SortType.getDirectorySortType(sort.type);
    }


    public void addEntry(java.lang.String path, com.amaze.filemanager.filesystem.files.sort.SortType sortType) {
        com.amaze.filemanager.database.models.explorer.Sort sort;
        sort = new com.amaze.filemanager.database.models.explorer.Sort(path, sortType.toDirectorySortInt());
        database.sortDao().insert(sort).subscribeOn(io.reactivex.schedulers.Schedulers.io()).subscribe();
    }


    public void clear(java.lang.String path) {
        database.sortDao().clear(path).subscribeOn(io.reactivex.schedulers.Schedulers.io()).subscribe();
    }


    public void updateEntry(com.amaze.filemanager.database.models.explorer.Sort oldSort, java.lang.String newPath, com.amaze.filemanager.filesystem.files.sort.SortType newSortType) {
        com.amaze.filemanager.database.models.explorer.Sort newSort;
        newSort = new com.amaze.filemanager.database.models.explorer.Sort(newPath, newSortType.toDirectorySortInt());
        database.sortDao().update(newSort).subscribeOn(io.reactivex.schedulers.Schedulers.io()).subscribe();
    }


    @androidx.annotation.Nullable
    public com.amaze.filemanager.database.models.explorer.Sort findEntry(java.lang.String path) {
        try {
            return database.sortDao().find(path).subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
        } catch (java.lang.Exception e) {
            // catch error to handle Single#onError for blockingGet
            LOG.error(getClass().getSimpleName(), e);
            return null;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
