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
import com.amaze.filemanager.ui.fragments.CloudSheetFragment;
import androidx.annotation.NonNull;
import io.reactivex.schedulers.Schedulers;
import com.amaze.filemanager.database.models.explorer.CloudEntry;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import com.amaze.filemanager.fileoperations.exceptions.CloudPluginException;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 18/4/17.
 */
public class CloudHandler {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String CLOUD_PREFIX_BOX = "box:/";

    public static final java.lang.String CLOUD_PREFIX_DROPBOX = "dropbox:/";

    public static final java.lang.String CLOUD_PREFIX_GOOGLE_DRIVE = "gdrive:/";

    public static final java.lang.String CLOUD_PREFIX_ONE_DRIVE = "onedrive:/";

    public static final java.lang.String CLOUD_NAME_GOOGLE_DRIVE = "Google Drive™";

    public static final java.lang.String CLOUD_NAME_DROPBOX = "Dropbox";

    public static final java.lang.String CLOUD_NAME_ONE_DRIVE = "One Drive";

    public static final java.lang.String CLOUD_NAME_BOX = "Box";

    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.database.CloudHandler.class);

    private final com.amaze.filemanager.database.ExplorerDatabase database;

    private final android.content.Context context;

    public CloudHandler(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    com.amaze.filemanager.database.ExplorerDatabase explorerDatabase) {
        this.context = context;
        this.database = explorerDatabase;
    }


    public void addEntry(com.amaze.filemanager.database.models.explorer.CloudEntry cloudEntry) throws com.amaze.filemanager.fileoperations.exceptions.CloudPluginException {
        if (!com.amaze.filemanager.ui.fragments.CloudSheetFragment.isCloudProviderAvailable(context))
            throw new com.amaze.filemanager.fileoperations.exceptions.CloudPluginException();

        database.cloudEntryDao().insert(cloudEntry).subscribeOn(io.reactivex.schedulers.Schedulers.io()).subscribe();
    }


    public void clear(com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType) {
        database.cloudEntryDao().findByServiceType(serviceType.ordinal()).subscribeOn(io.reactivex.schedulers.Schedulers.io()).subscribe((com.amaze.filemanager.database.models.explorer.CloudEntry cloudEntry) -> database.cloudEntryDao().delete(cloudEntry).subscribeOn(io.reactivex.schedulers.Schedulers.io()).subscribe(), (java.lang.Throwable throwable) -> LOG.warn("failed to delete cloud connection", throwable));
    }


    public void clearAllCloudConnections() {
        database.cloudEntryDao().clear().subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
    }


    public void updateEntry(com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType, com.amaze.filemanager.database.models.explorer.CloudEntry newCloudEntry) throws com.amaze.filemanager.fileoperations.exceptions.CloudPluginException {
        if (!com.amaze.filemanager.ui.fragments.CloudSheetFragment.isCloudProviderAvailable(context))
            throw new com.amaze.filemanager.fileoperations.exceptions.CloudPluginException();

        database.cloudEntryDao().update(newCloudEntry).subscribeOn(io.reactivex.schedulers.Schedulers.io()).subscribe();
    }


    public com.amaze.filemanager.database.models.explorer.CloudEntry findEntry(com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType) throws com.amaze.filemanager.fileoperations.exceptions.CloudPluginException {
        if (!com.amaze.filemanager.ui.fragments.CloudSheetFragment.isCloudProviderAvailable(context))
            throw new com.amaze.filemanager.fileoperations.exceptions.CloudPluginException();

        try {
            return database.cloudEntryDao().findByServiceType(serviceType.ordinal()).subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
        } catch (java.lang.Exception e) {
            // catch error to handle Single#onError for blockingGet
            LOG.error(getClass().getSimpleName(), e);
            return null;
        }
    }


    public java.util.List<com.amaze.filemanager.database.models.explorer.CloudEntry> getAllEntries() throws com.amaze.filemanager.fileoperations.exceptions.CloudPluginException {
        if (!com.amaze.filemanager.ui.fragments.CloudSheetFragment.isCloudProviderAvailable(context))
            throw new com.amaze.filemanager.fileoperations.exceptions.CloudPluginException();

        return database.cloudEntryDao().list().subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
