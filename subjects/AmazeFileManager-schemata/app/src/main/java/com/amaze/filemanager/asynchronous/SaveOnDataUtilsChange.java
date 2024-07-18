/* Copyright (C) 2014-2021 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
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
package com.amaze.filemanager.asynchronous;
import java.lang.ref.WeakReference;
import com.amaze.filemanager.database.models.OperationData;
import com.amaze.filemanager.utils.DataUtils;
import com.amaze.filemanager.application.AppConfig;
import androidx.annotation.NonNull;
import com.amaze.filemanager.database.UtilsHandler;
import com.amaze.filemanager.ui.views.drawer.Drawer;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SaveOnDataUtilsChange implements com.amaze.filemanager.utils.DataUtils.DataChangeListener {
    static final int MUID_STATIC = getMUID();
    private final com.amaze.filemanager.database.UtilsHandler utilsHandler = com.amaze.filemanager.application.AppConfig.getInstance().getUtilsHandler();

    private final java.lang.ref.WeakReference<com.amaze.filemanager.ui.views.drawer.Drawer> drawer;

    public SaveOnDataUtilsChange(@androidx.annotation.NonNull
    com.amaze.filemanager.ui.views.drawer.Drawer drawer) {
        this.drawer = new java.lang.ref.WeakReference<>(drawer);
    }


    @java.lang.Override
    public void onHiddenFileAdded(java.lang.String path) {
        utilsHandler.saveToDatabase(new com.amaze.filemanager.database.models.OperationData(com.amaze.filemanager.database.UtilsHandler.Operation.HIDDEN, path));
    }


    @java.lang.Override
    public void onHiddenFileRemoved(java.lang.String path) {
        utilsHandler.removeFromDatabase(new com.amaze.filemanager.database.models.OperationData(com.amaze.filemanager.database.UtilsHandler.Operation.HIDDEN, path));
    }


    @java.lang.Override
    public void onHistoryAdded(java.lang.String path) {
        utilsHandler.saveToDatabase(new com.amaze.filemanager.database.models.OperationData(com.amaze.filemanager.database.UtilsHandler.Operation.HISTORY, path));
    }


    @java.lang.Override
    public void onBookAdded(java.lang.String[] path, boolean refreshdrawer) {
        utilsHandler.saveToDatabase(new com.amaze.filemanager.database.models.OperationData(com.amaze.filemanager.database.UtilsHandler.Operation.BOOKMARKS, path[0], path[1]));
        if (refreshdrawer) {
            final com.amaze.filemanager.ui.views.drawer.Drawer drawer;
            drawer = this.drawer.get();
            if (drawer != null) {
                drawer.refreshDrawer();
            }
        }
    }


    @java.lang.Override
    public void onHistoryCleared() {
        utilsHandler.clearTable(com.amaze.filemanager.database.UtilsHandler.Operation.HISTORY);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
