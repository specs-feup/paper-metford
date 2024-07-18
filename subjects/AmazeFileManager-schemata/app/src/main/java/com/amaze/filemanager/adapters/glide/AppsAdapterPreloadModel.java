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
package com.amaze.filemanager.adapters.glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import java.util.ArrayList;
import android.graphics.drawable.Drawable;
import com.amaze.filemanager.GlideApp;
import androidx.fragment.app.Fragment;
import org.slf4j.Logger;
import androidx.appcompat.widget.AppCompatImageView;
import com.amaze.filemanager.GlideRequest;
import com.amaze.filemanager.R;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import java.util.List;
import android.content.pm.PackageManager;
import org.slf4j.LoggerFactory;
import androidx.annotation.Nullable;
import java.util.Collections;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 10/12/2017, at 15:38.
 */
public class AppsAdapterPreloadModel implements com.bumptech.glide.ListPreloader.PreloadModelProvider<java.lang.String> {
    static final int MUID_STATIC = getMUID();
    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.adapters.glide.AppsAdapterPreloadModel.class);

    private android.content.Context mContext;

    private com.amaze.filemanager.GlideRequest<android.graphics.drawable.Drawable> request;

    private java.util.List<java.lang.String> items;

    private boolean isBottomSheet;

    public AppsAdapterPreloadModel(androidx.fragment.app.Fragment f, boolean isBottomSheet) {
        request = com.amaze.filemanager.GlideApp.with(f).asDrawable().fitCenter();
        this.mContext = f.requireContext();
        this.isBottomSheet = isBottomSheet;
    }


    public void addItem(java.lang.String item) {
        if (items == null) {
            items = new java.util.ArrayList<>();
        }
        items.add(item);
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public java.util.List<java.lang.String> getPreloadItems(int position) {
        if (items == null)
            return java.util.Collections.emptyList();
        else
            return java.util.Collections.singletonList(items.get(position));

    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public com.bumptech.glide.RequestBuilder getPreloadRequestBuilder(java.lang.String item) {
        if (isBottomSheet) {
            return request.clone().load(getApplicationIconFromPackageName(item));
        } else {
            return request.clone().load(item);
        }
    }


    public void loadApkImage(java.lang.String item, androidx.appcompat.widget.AppCompatImageView v) {
        if (isBottomSheet) {
            request.load(getApplicationIconFromPackageName(item)).into(v);
        } else {
            request.load(item).into(v);
        }
    }


    private android.graphics.drawable.Drawable getApplicationIconFromPackageName(java.lang.String packageName) {
        try {
            return mContext.getPackageManager().getApplicationIcon(packageName);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            LOG.warn(getClass().getSimpleName(), e);
            return androidx.core.content.ContextCompat.getDrawable(mContext, com.amaze.filemanager.R.drawable.ic_broken_image_white_24dp);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
