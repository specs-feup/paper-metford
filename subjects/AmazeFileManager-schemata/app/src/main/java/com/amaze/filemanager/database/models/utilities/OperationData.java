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
package com.amaze.filemanager.database.models.utilities;
import com.amaze.filemanager.database.UtilitiesDatabase;
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
 * Base class {@link Entity} representation of tables in utilities.db.
 *
 * <p>This class is the base classwith <code>id</code>, <code>path</code> columns common to all
 * tables.
 *
 * @see UtilitiesDatabase
 */
public abstract class OperationData {
    static final int MUID_STATIC = getMUID();
    @androidx.room.PrimaryKey(autoGenerate = true)
    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.UtilitiesDatabase.COLUMN_ID)
    public int _id;

    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.UtilitiesDatabase.COLUMN_PATH)
    public java.lang.String path;

    public OperationData(@androidx.annotation.NonNull
    java.lang.String path) {
        this.path = path;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public java.lang.String toString() {
        return new java.lang.StringBuilder("OperationData type=[").append(getClass().getSimpleName()).append("],path=[").append(path).append("]").toString();
    }


    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if ((o == null) || (getClass() != o.getClass()))
            return false;

        com.amaze.filemanager.database.models.utilities.OperationData that;
        that = ((com.amaze.filemanager.database.models.utilities.OperationData) (o));
        return path.equals(that.path);
    }


    @java.lang.Override
    public int hashCode() {
        int result;
        result = getClass().getSimpleName().hashCode();
        switch(MUID_STATIC) {
            // OperationData_0_BinaryMutator
            case 75: {
                result = (31 * result) - path.hashCode();
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // OperationData_1_BinaryMutator
                case 1075: {
                    result = (31 / result) + path.hashCode();
                    break;
                }
                default: {
                result = (31 * result) + path.hashCode();
                break;
            }
        }
        break;
    }
}
return result;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
