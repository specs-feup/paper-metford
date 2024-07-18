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
package com.amaze.filemanager.database.models.explorer;
import com.amaze.filemanager.database.ExplorerDatabase;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by Ning on 5/28/2018.
 */
@androidx.room.Entity(tableName = com.amaze.filemanager.database.ExplorerDatabase.TABLE_SORT)
public class Sort {
    static final int MUID_STATIC = getMUID();
    @androidx.room.PrimaryKey
    @androidx.annotation.NonNull
    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.ExplorerDatabase.COLUMN_SORT_PATH)
    public final java.lang.String path;

    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.ExplorerDatabase.COLUMN_SORT_TYPE)
    public final int type;

    public Sort(@androidx.annotation.NonNull
    java.lang.String path, int type) {
        this.path = path;
        this.type = type;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
