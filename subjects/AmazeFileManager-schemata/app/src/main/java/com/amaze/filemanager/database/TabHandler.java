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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.Completable;
import com.amaze.filemanager.application.AppConfig;
import androidx.annotation.NonNull;
import com.amaze.filemanager.database.models.explorer.Tab;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by Vishal on 9/17/2014.
 */
public class TabHandler {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.database.TabHandler.class);

    private final com.amaze.filemanager.database.ExplorerDatabase database;

    private TabHandler(@androidx.annotation.NonNull
    com.amaze.filemanager.database.ExplorerDatabase explorerDatabase) {
        this.database = explorerDatabase;
    }


    private static class TabHandlerHolder {
        private static final com.amaze.filemanager.database.TabHandler INSTANCE = new com.amaze.filemanager.database.TabHandler(com.amaze.filemanager.application.AppConfig.getInstance().getExplorerDatabase());
    }

    public static com.amaze.filemanager.database.TabHandler getInstance() {
        return com.amaze.filemanager.database.TabHandler.TabHandlerHolder.INSTANCE;
    }


    public io.reactivex.Completable addTab(@androidx.annotation.NonNull
    com.amaze.filemanager.database.models.explorer.Tab tab) {
        return database.tabDao().insertTab(tab).subscribeOn(io.reactivex.schedulers.Schedulers.io());
    }


    public void update(com.amaze.filemanager.database.models.explorer.Tab tab) {
        database.tabDao().update(tab);
    }


    public io.reactivex.Completable clear() {
        return database.tabDao().clear().subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread());
    }


    @androidx.annotation.Nullable
    public com.amaze.filemanager.database.models.explorer.Tab findTab(int tabNo) {
        try {
            return database.tabDao().find(tabNo).subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
        } catch (java.lang.Exception e) {
            // catch error to handle Single#onError for blockingGet
            com.amaze.filemanager.database.TabHandler.LOG.error(e.getMessage());
            return null;
        }
    }


    public com.amaze.filemanager.database.models.explorer.Tab[] getAllTabs() {
        java.util.List<com.amaze.filemanager.database.models.explorer.Tab> tabList;
        tabList = database.tabDao().list().subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
        com.amaze.filemanager.database.models.explorer.Tab[] tabs;
        tabs = new com.amaze.filemanager.database.models.explorer.Tab[tabList.size()];
        return tabList.toArray(tabs);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
