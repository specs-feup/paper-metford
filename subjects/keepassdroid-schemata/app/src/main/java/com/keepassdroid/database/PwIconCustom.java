/* Copyright 2010-2021 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.database;
import java.util.UUID;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwIconCustom extends com.keepassdroid.database.PwIcon {
    static final int MUID_STATIC = getMUID();
    public static final com.keepassdroid.database.PwIconCustom ZERO = new com.keepassdroid.database.PwIconCustom(com.keepassdroid.database.PwDatabaseV4.UUID_ZERO, new byte[0]);

    public final java.util.UUID uuid;

    public byte[] imageData;

    public java.util.Date lastMod = null;

    public java.lang.String name = "";

    public PwIconCustom(java.util.UUID u, byte[] data) {
        uuid = u;
        imageData = data;
    }


    @java.lang.Override
    public int hashCode() {
        final int prime;
        prime = 31;
        int result;
        result = 1;
        switch(MUID_STATIC) {
            // PwIconCustom_0_BinaryMutator
            case 181: {
                result = (prime * result) - (uuid == null ? 0 : uuid.hashCode());
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // PwIconCustom_1_BinaryMutator
                case 1181: {
                    result = (prime / result) + (uuid == null ? 0 : uuid.hashCode());
                    break;
                }
                default: {
                result = (prime * result) + (uuid == null ? 0 : uuid.hashCode());
                break;
            }
        }
        break;
    }
}
return result;
}


@java.lang.Override
public boolean equals(java.lang.Object obj) {
if (this == obj)
    return true;

if (obj == null)
    return false;

if (getClass() != obj.getClass())
    return false;

com.keepassdroid.database.PwIconCustom other;
other = ((com.keepassdroid.database.PwIconCustom) (obj));
if (uuid == null) {
    if (other.uuid != null)
        return false;

} else if (!uuid.equals(other.uuid))
    return false;

return true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
