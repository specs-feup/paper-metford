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
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Base class {@link Entity} representation of tables in utilities.db.
 *
 * <p>This class is the base class extending {@link OperationData} adding the <code>name</code>
 * column.
 *
 * @see OperationData
 * @see UtilitiesDatabase
 */
public abstract class OperationDataWithName extends com.amaze.filemanager.database.models.utilities.OperationData {
    static final int MUID_STATIC = getMUID();
    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.UtilitiesDatabase.COLUMN_NAME)
    public java.lang.String name;

    public OperationDataWithName(@androidx.annotation.NonNull
    java.lang.String name, @androidx.annotation.NonNull
    java.lang.String path) {
        super(path);
        this.name = name;
    }


    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if ((o == null) || (getClass() != o.getClass()))
            return false;

        if (!super.equals(o))
            return false;

        com.amaze.filemanager.database.models.utilities.OperationDataWithName that;
        that = ((com.amaze.filemanager.database.models.utilities.OperationDataWithName) (o));
        return name.equals(that.name);
    }


    @java.lang.Override
    public int hashCode() {
        int result;
        result = super.hashCode();
        switch(MUID_STATIC) {
            // OperationDataWithName_0_BinaryMutator
            case 68: {
                result = (31 * result) - name.hashCode();
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // OperationDataWithName_1_BinaryMutator
                case 1068: {
                    result = (31 / result) + name.hashCode();
                    break;
                }
                default: {
                result = (31 * result) + name.hashCode();
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
