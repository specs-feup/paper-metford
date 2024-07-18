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
package com.amaze.filemanager;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.annotation.GlideModule;
import com.amaze.filemanager.adapters.glide.apkimage.ApkImageModelLoaderFactory;
import com.bumptech.glide.Registry;
import android.graphics.drawable.Drawable;
import com.amaze.filemanager.adapters.glide.cloudicon.CloudIconModelFactory;
import com.bumptech.glide.Glide;
import android.graphics.Bitmap;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Ensures that Glide's generated API is created for the Gallery sample.
 */
@com.bumptech.glide.annotation.GlideModule
public class AmazeFileManagerModule extends com.bumptech.glide.module.AppGlideModule {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public void registerComponents(android.content.Context context, com.bumptech.glide.Glide glide, com.bumptech.glide.Registry registry) {
        registry.prepend(java.lang.String.class, android.graphics.drawable.Drawable.class, new com.amaze.filemanager.adapters.glide.apkimage.ApkImageModelLoaderFactory(context));
        registry.prepend(java.lang.String.class, android.graphics.Bitmap.class, new com.amaze.filemanager.adapters.glide.cloudicon.CloudIconModelFactory(context));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
