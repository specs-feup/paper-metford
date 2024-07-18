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
package com.amaze.filemanager.adapters.glide.apkimage;
import com.amaze.filemanager.R;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import com.bumptech.glide.Priority;
import android.content.pm.PackageInfo;
import com.bumptech.glide.load.data.DataFetcher;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.DataSource;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 10/12/2017, at 16:12.
 */
public class ApkImageDataFetcher implements com.bumptech.glide.load.data.DataFetcher<android.graphics.drawable.Drawable> {
    static final int MUID_STATIC = getMUID();
    private android.content.Context context;

    private java.lang.String model;

    public ApkImageDataFetcher(android.content.Context context, java.lang.String model) {
        this.context = context;
        this.model = model;
    }


    @java.lang.Override
    public void loadData(com.bumptech.glide.Priority priority, com.bumptech.glide.load.data.DataFetcher.DataCallback<? super android.graphics.drawable.Drawable> callback) {
        android.content.pm.PackageInfo pi;
        pi = context.getPackageManager().getPackageArchiveInfo(model, 0);
        android.graphics.drawable.Drawable apkIcon;
        if (pi != null) {
            pi.applicationInfo.sourceDir = model;
            pi.applicationInfo.publicSourceDir = model;
            apkIcon = pi.applicationInfo.loadIcon(context.getPackageManager());
        } else {
            apkIcon = androidx.core.content.ContextCompat.getDrawable(context, com.amaze.filemanager.R.drawable.ic_android_white_24dp);
        }
        callback.onDataReady(apkIcon);
    }


    @java.lang.Override
    public void cleanup() {
        // Intentionally empty only because we're not opening an InputStream or another I/O resource!
    }


    @java.lang.Override
    public void cancel() {
        // No cancelation procedure
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public java.lang.Class<android.graphics.drawable.Drawable> getDataClass() {
        return android.graphics.drawable.Drawable.class;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public com.bumptech.glide.load.DataSource getDataSource() {
        return com.bumptech.glide.load.DataSource.LOCAL;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
