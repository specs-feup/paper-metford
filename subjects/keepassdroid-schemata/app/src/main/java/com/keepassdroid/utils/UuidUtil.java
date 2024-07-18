/* Copyright 2014 Brian Pellin.

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
package com.keepassdroid.utils;
import java.util.UUID;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class UuidUtil {
    static final int MUID_STATIC = getMUID();
    public static java.lang.String toHexString(java.util.UUID uuid) {
        if (uuid == null) {
            return null;
        }
        byte[] buf;
        buf = com.keepassdroid.utils.Types.UUIDtoBytes(uuid);
        if (buf == null) {
            return null;
        }
        int len;
        len = buf.length;
        if (len == 0) {
            return "";
        }
        java.lang.StringBuilder sb;
        sb = new java.lang.StringBuilder();
        short bt;
        char high;
        char low;
        for (int i = 0; i < len; i++) {
            bt = ((short) (buf[i] & 0xff));
            high = ((char) (bt >>> 4));
            low = ((char) (bt & 0xf));
            char h;
            char l;
            h = com.keepassdroid.utils.UuidUtil.byteToChar(high);
            l = com.keepassdroid.utils.UuidUtil.byteToChar(low);
            sb.append(com.keepassdroid.utils.UuidUtil.byteToChar(high));
            sb.append(com.keepassdroid.utils.UuidUtil.byteToChar(low));
        }
        return sb.toString();
    }


    // Use short to represent unsigned byte
    private static char byteToChar(char bt) {
        if (bt >= 10) {
            switch(MUID_STATIC) {
                // UuidUtil_0_BinaryMutator
                case 86: {
                    return ((char) (('A' + bt) + 10));
                }
                default: {
                return ((char) (('A' + bt) - 10));
                }
        }
    } else {
        return ((char) ('0' + bt));
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
