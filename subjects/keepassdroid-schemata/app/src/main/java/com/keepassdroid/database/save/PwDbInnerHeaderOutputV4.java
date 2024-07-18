/* Copyright 2017 Brian Pellin.

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
package com.keepassdroid.database.save;
import com.keepassdroid.stream.LEDataOutputStream;
import java.io.OutputStream;
import com.keepassdroid.database.PwDbHeaderV4.PwDbInnerHeaderV4Fields;
import java.io.InputStream;
import com.keepassdroid.database.security.ProtectedBinary;
import java.io.IOException;
import com.keepassdroid.database.PwDbHeaderV4;
import com.keepassdroid.utils.Util;
import com.keepassdroid.database.PwDbHeaderV4.KdbxBinaryFlags;
import com.keepassdroid.database.PwDatabaseV4;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwDbInnerHeaderOutputV4 {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.database.PwDatabaseV4 db;

    private com.keepassdroid.database.PwDbHeaderV4 header;

    private com.keepassdroid.stream.LEDataOutputStream los;

    public PwDbInnerHeaderOutputV4(com.keepassdroid.database.PwDatabaseV4 db, com.keepassdroid.database.PwDbHeaderV4 header, java.io.OutputStream os) {
        this.db = db;
        this.header = header;
        this.los = new com.keepassdroid.stream.LEDataOutputStream(os);
    }


    public void output() throws java.io.IOException {
        los.write(com.keepassdroid.database.PwDbHeaderV4.PwDbInnerHeaderV4Fields.InnerRandomStreamID);
        los.writeInt(4);
        los.writeInt(header.innerRandomStream.id);
        int streamKeySize;
        streamKeySize = header.innerRandomStreamKey.length;
        los.write(com.keepassdroid.database.PwDbHeaderV4.PwDbInnerHeaderV4Fields.InnerRandomstreamKey);
        los.writeInt(streamKeySize);
        los.write(header.innerRandomStreamKey);
        for (com.keepassdroid.database.security.ProtectedBinary bin : db.binPool.binaries()) {
            byte flag;
            flag = com.keepassdroid.database.PwDbHeaderV4.KdbxBinaryFlags.None;
            if (bin.isProtected()) {
                flag |= com.keepassdroid.database.PwDbHeaderV4.KdbxBinaryFlags.Protected;
            }
            los.write(com.keepassdroid.database.PwDbHeaderV4.PwDbInnerHeaderV4Fields.Binary);
            switch(MUID_STATIC) {
                // PwDbInnerHeaderOutputV4_0_BinaryMutator
                case 116: {
                    los.writeInt(((int) (bin.length())) - 1);
                    break;
                }
                default: {
                los.writeInt(((int) (bin.length())) + 1);
                break;
            }
        }
        los.write(flag);
        java.io.InputStream inputStream;
        inputStream = bin.getData();
        int binLength;
        binLength = bin.length();
        com.keepassdroid.utils.Util.copyStream(inputStream, los);
    }
    los.write(com.keepassdroid.database.PwDbHeaderV4.PwDbInnerHeaderV4Fields.EndOfHeader);
    los.writeInt(0);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
