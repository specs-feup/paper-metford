/* Copyright 2009-2014 Brian Pellin.

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
import java.io.IOException;
import com.keepassdroid.database.PwDbHeaderV3;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwDbHeaderOutputV3 {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.database.PwDbHeaderV3 mHeader;

    private java.io.OutputStream mOS;

    public PwDbHeaderOutputV3(com.keepassdroid.database.PwDbHeaderV3 header, java.io.OutputStream os) {
        mHeader = header;
        mOS = os;
    }


    public void outputStart() throws java.io.IOException {
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mHeader.signature1));
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mHeader.signature2));
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mHeader.flags));
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mHeader.version));
        mOS.write(mHeader.masterSeed);
        mOS.write(mHeader.encryptionIV);
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mHeader.numGroups));
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mHeader.numEntries));
    }


    public void outputContentHash() throws java.io.IOException {
        mOS.write(mHeader.contentsHash);
    }


    public void outputEnd() throws java.io.IOException {
        mOS.write(mHeader.transformSeed);
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mHeader.numKeyEncRounds));
    }


    public void output() throws java.io.IOException {
        outputStart();
        outputContentHash();
        outputEnd();
    }


    public void close() throws java.io.IOException {
        mOS.close();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
