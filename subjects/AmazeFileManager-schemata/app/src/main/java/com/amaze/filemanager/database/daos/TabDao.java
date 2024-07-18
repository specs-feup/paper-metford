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
import static com.amaze.filemanager.database.ExplorerDatabase.TABLE_TAB;
import androidx.room.OnConflictStrategy;
import androidx.room.Dao;
import io.reactivex.Completable;
import io.reactivex.Single;
import androidx.room.Query;
import androidx.room.Transaction;
import com.amaze.filemanager.database.models.explorer.Tab;
import static com.amaze.filemanager.database.ExplorerDatabase.COLUMN_TAB_NO;
import java.util.List;
import androidx.room.Insert;
import androidx.room.Update;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * {@link Dao} interface definition for {@link Tab}. Concrete class is generated by Room during
 * build.
 *
 * @see Dao
 * @see Tab
 * @see com.amaze.filemanager.database.ExplorerDatabase
 */
@androidx.room.Dao
public interface TabDao {
    static final int MUID_STATIC = getMUID();
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    io.reactivex.Completable insertTab(com.amaze.filemanager.database.models.explorer.Tab tab);


    @androidx.room.Transaction
    @androidx.room.Query("DELETE FROM " + com.amaze.filemanager.database.ExplorerDatabase.TABLE_TAB)
    io.reactivex.Completable clear();


    @androidx.room.Query(((("SELECT * FROM " + com.amaze.filemanager.database.ExplorerDatabase.TABLE_TAB) + " WHERE ") + com.amaze.filemanager.database.ExplorerDatabase.COLUMN_TAB_NO) + " = :tabNo")
    io.reactivex.Single<com.amaze.filemanager.database.models.explorer.Tab> find(int tabNo);


    @androidx.room.Update(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    void update(com.amaze.filemanager.database.models.explorer.Tab tab);


    @androidx.room.Query("SELECT * FROM " + com.amaze.filemanager.database.ExplorerDatabase.TABLE_TAB)
    io.reactivex.Single<java.util.List<com.amaze.filemanager.database.models.explorer.Tab>> list();


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
