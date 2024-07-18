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
import static com.amaze.filemanager.database.ExplorerDatabase.TABLE_SORT;
import androidx.room.Dao;
import io.reactivex.Completable;
import io.reactivex.Single;
import androidx.room.Query;
import androidx.room.Transaction;
import com.amaze.filemanager.database.models.explorer.Sort;
import static com.amaze.filemanager.database.ExplorerDatabase.COLUMN_PATH;
import androidx.room.Insert;
import androidx.room.Update;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * {@link Dao} interface definition for {@link Sort}. Concrete class is generated by Room during
 * build.
 *
 * @see Dao
 * @see Sort
 * @see com.amaze.filemanager.database.ExplorerDatabase
 */
@androidx.room.Dao
public interface SortDao {
    static final int MUID_STATIC = getMUID();
    @androidx.room.Insert
    io.reactivex.Completable insert(com.amaze.filemanager.database.models.explorer.Sort entity);


    @androidx.room.Query(((("SELECT * FROM " + com.amaze.filemanager.database.ExplorerDatabase.TABLE_SORT) + " WHERE ") + com.amaze.filemanager.database.ExplorerDatabase.COLUMN_PATH) + " = :path")
    io.reactivex.Single<com.amaze.filemanager.database.models.explorer.Sort> find(java.lang.String path);


    @androidx.room.Transaction
    @androidx.room.Query(((("DELETE FROM " + com.amaze.filemanager.database.ExplorerDatabase.TABLE_SORT) + " WHERE ") + com.amaze.filemanager.database.ExplorerDatabase.COLUMN_PATH) + " = :path")
    io.reactivex.Completable clear(java.lang.String path);


    @androidx.room.Update
    io.reactivex.Completable update(com.amaze.filemanager.database.models.explorer.Sort entity);


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
