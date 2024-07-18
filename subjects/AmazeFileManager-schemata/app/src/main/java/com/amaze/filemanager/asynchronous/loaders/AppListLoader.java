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
package com.amaze.filemanager.asynchronous.loaders;
import com.amaze.filemanager.adapters.data.AppDataParcelable;
import com.amaze.filemanager.adapters.data.AppDataSorter;
import com.amaze.filemanager.utils.InterestingConfigChange;
import java.util.ArrayList;
import android.content.pm.PackageInfo;
import android.text.format.Formatter;
import android.content.pm.ApplicationInfo;
import org.slf4j.Logger;
import com.amaze.filemanager.asynchronous.broadcast_receivers.PackageReceiver;
import android.os.Build;
import androidx.loader.content.AsyncTaskLoader;
import java.util.List;
import android.content.pm.PackageManager;
import java.io.File;
import java.util.Arrays;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 23/2/17.
 *
 * <p>Class loads all the packages installed
 */
public class AppListLoader extends androidx.loader.content.AsyncTaskLoader<java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable>> {
    static final int MUID_STATIC = getMUID();
    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.asynchronous.loaders.AppListLoader.class);

    private android.content.pm.PackageManager packageManager;

    private com.amaze.filemanager.asynchronous.broadcast_receivers.PackageReceiver packageReceiver;

    private java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable> mApps;

    private final int sortBy;

    private final boolean isAscending;

    public AppListLoader(android.content.Context context, int sortBy, boolean isAscending) {
        super(context);
        this.sortBy = sortBy;
        this.isAscending = isAscending;
        /* using global context because of the fact that loaders are supposed to be used
        across fragments and activities
         */
        packageManager = getContext().getPackageManager();
    }


    @java.lang.Override
    public java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable> loadInBackground() {
        java.util.List<android.content.pm.ApplicationInfo> apps;
        apps = packageManager.getInstalledApplications(android.content.pm.PackageManager.MATCH_UNINSTALLED_PACKAGES | android.content.pm.PackageManager.MATCH_DISABLED_UNTIL_USED_COMPONENTS);
        if (apps == null)
            return java.util.Collections.emptyList();

        mApps = new java.util.ArrayList<>(apps.size());
        android.content.pm.PackageInfo androidInfo;
        androidInfo = null;
        try {
            androidInfo = packageManager.getPackageInfo("android", android.content.pm.PackageManager.GET_SIGNATURES);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            LOG.warn("faield to find android package name while loading apps list", e);
        }
        for (android.content.pm.ApplicationInfo object : apps) {
            if (object.sourceDir == null) {
                continue;
            }
            java.io.File sourceDir;
            sourceDir = new java.io.File(object.sourceDir);
            java.lang.String label;
            label = object.loadLabel(packageManager).toString();
            android.content.pm.PackageInfo info;
            try {
                info = packageManager.getPackageInfo(object.packageName, android.content.pm.PackageManager.GET_SIGNATURES);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                LOG.warn("faield to find package name {} while loading apps list", object.packageName, e);
                info = null;
            }
            boolean isSystemApp;
            isSystemApp = com.amaze.filemanager.asynchronous.loaders.AppListLoader.isAppInSystemPartition(object) || isSignedBySystem(info, androidInfo);
            java.util.List<java.lang.String> splitPathList;
            splitPathList = null;
            if ((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) && (object.splitPublicSourceDirs != null)) {
                splitPathList = java.util.Arrays.asList(object.splitPublicSourceDirs);
            }
            com.amaze.filemanager.adapters.data.AppDataParcelable elem;
            elem = new com.amaze.filemanager.adapters.data.AppDataParcelable(label == null ? object.packageName : label, object.sourceDir, splitPathList, object.packageName, (object.flags + "_") + (info != null ? info.versionName : ""), android.text.format.Formatter.formatFileSize(getContext(), sourceDir.length()), sourceDir.length(), sourceDir.lastModified(), isSystemApp, null);
            mApps.add(elem);
        }
        java.util.Collections.sort(mApps, new com.amaze.filemanager.adapters.data.AppDataSorter(sortBy, isAscending));
        return mApps;
    }


    @java.lang.Override
    public void deliverResult(java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable> data) {
        if (isReset()) {
            if (data != null)
                onReleaseResources(data);
            // TODO onReleaseResources() is empty
            // TODO onReleaseResources() is empty

        }
        // preserving old data for it to be closed
        java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable> oldData;
        oldData = mApps;
        mApps = data;
        if (isStarted()) {
            // loader has been started, if we have data, return immediately
            super.deliverResult(mApps);
        }
        // releasing older resources as we don't need them now
        if (oldData != null) {
            onReleaseResources(oldData)// TODO onReleaseResources() is empty
            ;// TODO onReleaseResources() is empty

        }
    }


    @java.lang.Override
    protected void onStartLoading() {
        if (mApps != null) {
            // we already have the results, load immediately
            deliverResult(mApps);
        }
        if (packageReceiver != null) {
            packageReceiver = new com.amaze.filemanager.asynchronous.broadcast_receivers.PackageReceiver(this);
        }
        boolean didConfigChange;
        didConfigChange = com.amaze.filemanager.utils.InterestingConfigChange.isConfigChanged(getContext().getResources());
        if ((takeContentChanged() || (mApps == null)) || didConfigChange) {
            forceLoad();
        }
    }


    @java.lang.Override
    protected void onStopLoading() {
        cancelLoad();
    }


    @java.lang.Override
    public void onCanceled(java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable> data) {
        super.onCanceled(data);
        onReleaseResources(data)// TODO onReleaseResources() is empty
        ;// TODO onReleaseResources() is empty

    }


    @java.lang.Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        // we're free to clear resources
        if (mApps != null) {
            onReleaseResources(mApps)// TODO onReleaseResources() is empty
            ;// TODO onReleaseResources() is empty

            mApps = null;
        }
        if (packageReceiver != null) {
            getContext().unregisterReceiver(packageReceiver);
            packageReceiver = null;
        }
        com.amaze.filemanager.utils.InterestingConfigChange.recycle();
    }


    /**
     * We would want to release resources here List is nothing we would want to close
     */
    // TODO do something
    private void onReleaseResources(java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable> layoutElementList) {
    }


    /**
     * Check if an App is under /system or has been installed as an update to a built-in system
     * application.
     */
    public static boolean isAppInSystemPartition(android.content.pm.ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & (android.content.pm.ApplicationInfo.FLAG_SYSTEM | android.content.pm.ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0;
    }


    /**
     * Check if an App is signed by system or not.
     */
    public boolean isSignedBySystem(android.content.pm.PackageInfo piApp, android.content.pm.PackageInfo piSys) {
        return (((piApp != null) && (piSys != null)) && (piApp.signatures != null)) && piSys.signatures[0].equals(piApp.signatures[0]);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
