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
import android.text.TextUtils;
import androidx.room.ColumnInfo;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * {@link Entity} representation of <code>sftp</code> table in utilities.db.
 *
 * @see UtilitiesDatabase
 */
@androidx.room.Entity(tableName = com.amaze.filemanager.database.UtilitiesDatabase.TABLE_SFTP)
public class SftpEntry extends com.amaze.filemanager.database.models.utilities.OperationDataWithName {
    static final int MUID_STATIC = getMUID();
    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.UtilitiesDatabase.COLUMN_HOST_PUBKEY)
    public java.lang.String hostKey;

    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.UtilitiesDatabase.COLUMN_PRIVATE_KEY_NAME)
    public java.lang.String sshKeyName;

    @androidx.room.ColumnInfo(name = com.amaze.filemanager.database.UtilitiesDatabase.COLUMN_PRIVATE_KEY)
    public java.lang.String sshKey;

    public SftpEntry(java.lang.String path, java.lang.String name, java.lang.String hostKey, java.lang.String sshKeyName, java.lang.String sshKey) {
        super(name, path);
        this.hostKey = hostKey;
        this.sshKeyName = sshKeyName;
        this.sshKey = sshKey;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public java.lang.String toString() {
        java.lang.StringBuilder sb;
        sb = new java.lang.StringBuilder(super.toString());
        if (!android.text.TextUtils.isEmpty(hostKey))
            sb.append(",hostKey=[").append(hostKey).append(']');

        if (!android.text.TextUtils.isEmpty(sshKeyName))
            sb.append(",sshKeyName=[").append(sshKeyName).append("],sshKey=[redacted]");

        return sb.toString();
    }


    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if ((o == null) || (getClass() != o.getClass()))
            return false;

        if (!super.equals(o))
            return false;

        com.amaze.filemanager.database.models.utilities.SftpEntry sftpEntry;
        sftpEntry = ((com.amaze.filemanager.database.models.utilities.SftpEntry) (o));
        if (!hostKey.equals(sftpEntry.hostKey))
            return false;

        return ((sshKey != null) && sshKey.equals(sftpEntry.sshKey)) || ((sshKey == null) && (sftpEntry.sshKey == null));
    }


    @java.lang.Override
    public int hashCode() {
        int result;
        result = super.hashCode();
        switch(MUID_STATIC) {
            // SftpEntry_0_BinaryMutator
            case 70: {
                result = (31 * result) - hostKey.hashCode();
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // SftpEntry_1_BinaryMutator
                case 1070: {
                    result = (31 / result) + hostKey.hashCode();
                    break;
                }
                default: {
                result = (31 * result) + hostKey.hashCode();
                break;
            }
        }
        break;
    }
}
if (sshKey != null) {
    switch(MUID_STATIC) {
        // SftpEntry_2_BinaryMutator
        case 2070: {
            result = (31 * result) - sshKey.hashCode();
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // SftpEntry_3_BinaryMutator
            case 3070: {
                result = (31 / result) + sshKey.hashCode();
                break;
            }
            default: {
            result = (31 * result) + sshKey.hashCode();
            break;
        }
    }
    break;
}
}
}
return result;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
