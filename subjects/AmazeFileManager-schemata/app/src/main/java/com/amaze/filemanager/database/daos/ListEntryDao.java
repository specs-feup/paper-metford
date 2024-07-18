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
package com.amaze.filemanager.database.daos;
import androidx.room.OnConflictStrategy;
import androidx.room.Dao;
import io.reactivex.Completable;
import io.reactivex.Single;
import androidx.room.Query;
import com.amaze.filemanager.database.models.utilities.List;
import static com.amaze.filemanager.database.UtilitiesDatabase.COLUMN_PATH;
import static com.amaze.filemanager.database.UtilitiesDatabase.TABLE_LIST;
import androidx.room.Insert;
import androidx.room.Update;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * {@link Dao} interface definition for {@link List}. Concrete class is generated by Room during
 * build.
 *
 * @see Dao
 * @see List
 * @see com.amaze.filemanager.database.UtilitiesDatabase
 */
@androidx.room.Dao
public interface ListEntryDao {
    static final int MUID_STATIC = getMUID();
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    io.reactivex.Completable insert(com.amaze.filemanager.database.models.utilities.List instance);


    @androidx.room.Update
    io.reactivex.Completable update(com.amaze.filemanager.database.models.utilities.List instance);


    @androidx.room.Query((("SELECT " + com.amaze.filemanager.database.UtilitiesDatabase.COLUMN_PATH) + " FROM ") + com.amaze.filemanager.database.UtilitiesDatabase.TABLE_LIST)
    io.reactivex.Single<java.util.List<java.lang.String>> listPaths();


    @androidx.room.Query(((("DELETE FROM " + com.amaze.filemanager.database.UtilitiesDatabase.TABLE_LIST) + " WHERE ") + com.amaze.filemanager.database.UtilitiesDatabase.COLUMN_PATH) + " = :path")
    io.reactivex.Completable deleteByPath(java.lang.String path);


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
