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
import com.amaze.filemanager.database.models.StringWrapper;
import com.amaze.filemanager.database.ExplorerDatabase;
import androidx.room.Entity;
import com.amaze.filemanager.database.typeconverters.OpenModeTypeConverter;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import com.amaze.filemanager.database.typeconverters.EncryptedStringTypeConverter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 18/4/17.
 */
@androidx.room.Entity(tableName = com.amaze.filemanager.database.ExplorerDatabase.TABLE_CLOUD_PERSIST)
public class CloudEntry {
    static final int MUID_STATIC = getMUID();
    @androidx.room.PrimaryKey(autoGenerate = true)
    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.ExplorerDatabase.COLUMN_CLOUD_ID)
    private int _id;

    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.ExplorerDatabase.COLUMN_CLOUD_SERVICE)
    @androidx.room.TypeConverters(com.amaze.filemanager.database.typeconverters.OpenModeTypeConverter.class)
    private com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType;

    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.ExplorerDatabase.COLUMN_CLOUD_PERSIST)
    @androidx.room.TypeConverters(com.amaze.filemanager.database.typeconverters.EncryptedStringTypeConverter.class)
    private com.amaze.filemanager.database.models.StringWrapper persistData;

    public CloudEntry() {
    }


    public CloudEntry(com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType, java.lang.String persistData) {
        this.serviceType = serviceType;
        this.persistData = new com.amaze.filemanager.database.models.StringWrapper(persistData);
    }


    public void setId(int _id) {
        this._id = _id;
    }


    public int getId() {
        return this._id;
    }


    public void setPersistData(com.amaze.filemanager.database.models.StringWrapper persistData) {
        this.persistData = persistData;
    }


    public com.amaze.filemanager.database.models.StringWrapper getPersistData() {
        return this.persistData;
    }


    /**
     * Set the service type Support values from {@link OpenMode}
     */
    public void setServiceType(com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode) {
        this.serviceType = openMode;
    }


    /**
     * Returns ordinal value of service from {@link OpenMode}
     */
    public com.amaze.filemanager.fileoperations.filesystem.OpenMode getServiceType() {
        return this.serviceType;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
