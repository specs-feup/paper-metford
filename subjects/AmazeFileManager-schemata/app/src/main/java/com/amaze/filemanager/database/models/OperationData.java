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
package com.amaze.filemanager.database.models;
import com.amaze.filemanager.database.UtilsHandler.Operation;
import static com.amaze.filemanager.database.UtilsHandler.Operation.GRID;
import static com.amaze.filemanager.database.UtilsHandler.Operation.SFTP;
import androidx.annotation.NonNull;
import static com.amaze.filemanager.database.UtilsHandler.Operation.SMB;
import android.text.TextUtils;
import static com.amaze.filemanager.database.UtilsHandler.Operation.HIDDEN;
import static com.amaze.filemanager.database.UtilsHandler.Operation.BOOKMARKS;
import static com.amaze.filemanager.database.UtilsHandler.Operation.LIST;
import com.amaze.filemanager.database.UtilsHandler;
import androidx.annotation.Nullable;
import static com.amaze.filemanager.database.UtilsHandler.Operation.HISTORY;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class OperationData {
    static final int MUID_STATIC = getMUID();
    public final com.amaze.filemanager.database.UtilsHandler.Operation type;

    public final java.lang.String path;

    public final java.lang.String name;

    public final java.lang.String hostKey;

    public final java.lang.String sshKeyName;

    public final java.lang.String sshKey;

    /**
     * Constructor for types {@link Operation#HIDDEN}, {@link Operation#HISTORY}, {@link Operation#LIST} or {@link Operation#GRID}
     */
    public OperationData(com.amaze.filemanager.database.UtilsHandler.Operation type, java.lang.String path) {
        if ((((type != com.amaze.filemanager.database.UtilsHandler.Operation.HIDDEN) && (type != com.amaze.filemanager.database.UtilsHandler.Operation.HISTORY)) && (type != com.amaze.filemanager.database.UtilsHandler.Operation.LIST)) && (type != com.amaze.filemanager.database.UtilsHandler.Operation.GRID)) {
            throw new java.lang.IllegalArgumentException("Wrong constructor for object type");
        }
        this.type = type;
        this.path = path;
        name = null;
        hostKey = null;
        sshKeyName = null;
        sshKey = null;
    }


    /**
     * Constructor for types {@link Operation#BOOKMARKS} or {@link Operation#SMB}
     */
    public OperationData(com.amaze.filemanager.database.UtilsHandler.Operation type, java.lang.String name, java.lang.String path) {
        if ((type != com.amaze.filemanager.database.UtilsHandler.Operation.BOOKMARKS) && (type != com.amaze.filemanager.database.UtilsHandler.Operation.SMB))
            throw new java.lang.IllegalArgumentException("Wrong constructor for object type");

        this.type = type;
        this.path = path;
        this.name = name;
        hostKey = null;
        sshKeyName = null;
        sshKey = null;
    }


    /**
     * Constructor for {@link Operation#SFTP} {@param hostKey}, {@param sshKeyName} and {@param sshKey} may be null for when {@link OperationData} is used for {@link UtilsHandler#removeFromDatabase(OperationData)}
     */
    public OperationData(@androidx.annotation.NonNull
    com.amaze.filemanager.database.UtilsHandler.Operation type, @androidx.annotation.NonNull
    java.lang.String path, @androidx.annotation.NonNull
    java.lang.String name, @androidx.annotation.Nullable
    java.lang.String hostKey, @androidx.annotation.Nullable
    java.lang.String sshKeyName, @androidx.annotation.Nullable
    java.lang.String sshKey) {
        if (type != com.amaze.filemanager.database.UtilsHandler.Operation.SFTP)
            throw new java.lang.IllegalArgumentException("Wrong constructor for object type");

        this.type = type;
        this.path = path;
        this.name = name;
        this.hostKey = hostKey;
        this.sshKeyName = sshKeyName;
        this.sshKey = sshKey;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuilder sb;
        sb = new java.lang.StringBuilder("OperationData type=[").append(type).append("],path=[").append(path).append("]");
        if (!android.text.TextUtils.isEmpty(hostKey))
            sb.append(",hostKey=[").append(hostKey).append(']');

        if (!android.text.TextUtils.isEmpty(sshKeyName))
            sb.append(",sshKeyName=[").append(sshKeyName).append("],sshKey=[redacted]");

        return sb.toString();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
