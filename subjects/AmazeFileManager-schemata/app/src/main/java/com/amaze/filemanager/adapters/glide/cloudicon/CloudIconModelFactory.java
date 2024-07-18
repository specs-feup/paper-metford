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
package com.amaze.filemanager.adapters.glide.cloudicon;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import android.graphics.Bitmap;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by Vishal Nehra on 3/27/2018.
 */
public class CloudIconModelFactory implements com.bumptech.glide.load.model.ModelLoaderFactory<java.lang.String, android.graphics.Bitmap> {
    static final int MUID_STATIC = getMUID();
    private android.content.Context context;

    public CloudIconModelFactory(android.content.Context context) {
        this.context = context;
    }


    @java.lang.Override
    public com.bumptech.glide.load.model.ModelLoader<java.lang.String, android.graphics.Bitmap> build(com.bumptech.glide.load.model.MultiModelLoaderFactory multiFactory) {
        return new com.amaze.filemanager.adapters.glide.cloudicon.CloudIconModelLoader(context);
    }


    @java.lang.Override
    public void teardown() {
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
