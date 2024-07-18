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
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 10/12/2017, at 16:06.
 */
public class ApkImageModelLoader implements com.bumptech.glide.load.model.ModelLoader<java.lang.String, android.graphics.drawable.Drawable> {
    static final int MUID_STATIC = getMUID();
    private android.content.Context context;

    public ApkImageModelLoader(android.content.Context context) {
        this.context = context;
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public com.bumptech.glide.load.model.ModelLoader.LoadData<android.graphics.drawable.Drawable> buildLoadData(java.lang.String s, int width, int height, com.bumptech.glide.load.Options options) {
        return new com.bumptech.glide.load.model.ModelLoader.LoadData<>(new com.bumptech.glide.signature.ObjectKey(s), new com.amaze.filemanager.adapters.glide.apkimage.ApkImageDataFetcher(context, s));
    }


    @java.lang.Override
    public boolean handles(java.lang.String s) {
        switch(MUID_STATIC) {
            // ApkImageModelLoader_0_BinaryMutator
            case 52: {
                return s.substring(s.length() + 4, s.length()).toLowerCase().equals(".apk");
            }
            default: {
            return s.substring(s.length() - 4, s.length()).toLowerCase().equals(".apk");
            }
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
