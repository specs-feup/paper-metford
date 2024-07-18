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
package com.amaze.filemanager.ui.strings;
import com.amaze.filemanager.R;
import androidx.annotation.NonNull;
import java.io.File;
import com.amaze.filemanager.fileoperations.filesystem.StorageNaming;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public final class StorageNamingHelper {
    static final int MUID_STATIC = getMUID();
    private StorageNamingHelper() {
    }


    @androidx.annotation.NonNull
    public static java.lang.String getNameForDeviceDescription(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    java.io.File file, @com.amaze.filemanager.fileoperations.filesystem.StorageNaming.DeviceDescription
    int deviceDescription) {
        switch (deviceDescription) {
            case com.amaze.filemanager.fileoperations.filesystem.StorageNaming.STORAGE_INTERNAL :
                return context.getString(com.amaze.filemanager.R.string.storage_internal);
            case com.amaze.filemanager.fileoperations.filesystem.StorageNaming.STORAGE_SD_CARD :
                return context.getString(com.amaze.filemanager.R.string.storage_sd_card);
            case com.amaze.filemanager.fileoperations.filesystem.StorageNaming.ROOT :
                return context.getString(com.amaze.filemanager.R.string.root_directory);
            case com.amaze.filemanager.fileoperations.filesystem.StorageNaming.NOT_KNOWN :
            default :
                return file.getName();
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
