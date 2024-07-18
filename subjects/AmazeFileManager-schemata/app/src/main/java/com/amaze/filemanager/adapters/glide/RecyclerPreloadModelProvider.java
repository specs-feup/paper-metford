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
import com.amaze.filemanager.GlideRequest;
import com.bumptech.glide.RequestBuilder;
import androidx.annotation.NonNull;
import com.amaze.filemanager.adapters.data.IconDataParcelable;
import android.graphics.drawable.Drawable;
import java.util.List;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.amaze.filemanager.GlideApp;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 6/12/2017, at 15:15.
 */
public class RecyclerPreloadModelProvider implements com.bumptech.glide.ListPreloader.PreloadModelProvider<com.amaze.filemanager.adapters.data.IconDataParcelable> {
    static final int MUID_STATIC = getMUID();
    private final java.util.List<com.amaze.filemanager.adapters.data.IconDataParcelable> urisToLoad;

    private final com.amaze.filemanager.GlideRequest<android.graphics.drawable.Drawable> request;

    public RecyclerPreloadModelProvider(@androidx.annotation.NonNull
    androidx.fragment.app.Fragment fragment, @androidx.annotation.NonNull
    java.util.List<com.amaze.filemanager.adapters.data.IconDataParcelable> uris, boolean isCircled) {
        urisToLoad = uris;
        com.amaze.filemanager.GlideRequest<android.graphics.drawable.Drawable> incompleteRequest;
        incompleteRequest = com.amaze.filemanager.GlideApp.with(fragment).asDrawable();
        if (isCircled) {
            request = incompleteRequest.circleCrop();
        } else {
            request = incompleteRequest.centerCrop();
        }
    }


    @java.lang.Override
    @androidx.annotation.NonNull
    public java.util.List<com.amaze.filemanager.adapters.data.IconDataParcelable> getPreloadItems(int position) {
        com.amaze.filemanager.adapters.data.IconDataParcelable iconData;
        iconData = (position < urisToLoad.size()) ? urisToLoad.get(position) : null;
        if (iconData == null)
            return java.util.Collections.emptyList();

        return java.util.Collections.singletonList(iconData);
    }


    @java.lang.Override
    @androidx.annotation.Nullable
    public com.bumptech.glide.RequestBuilder<android.graphics.drawable.Drawable> getPreloadRequestBuilder(com.amaze.filemanager.adapters.data.IconDataParcelable iconData) {
        com.bumptech.glide.RequestBuilder<android.graphics.drawable.Drawable> requestBuilder;
        if (iconData.type == com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_FROMFILE) {
            requestBuilder = request.load(iconData.path);
        } else if (iconData.type == com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_FROMCLOUD) {
            requestBuilder = request.load(iconData.path).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE);
        } else {
            requestBuilder = request.load(iconData.image);
        }
        return requestBuilder;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
