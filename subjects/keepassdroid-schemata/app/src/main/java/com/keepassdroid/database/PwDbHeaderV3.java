/* Copyright 2009-2011 Brian Pellin.

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



Derived from

KeePass for J2ME


Copyright 2007 Naomaru Itoi <nao@phoneid.org>

This file was derived from 

Java clone of KeePass - A KeePass file viewer for Java
Copyright 2006 Bill Zwicky <billzwicky@users.sourceforge.net>

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 2

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.keepassdroid.database;
import com.keepassdroid.stream.LEDataInputStream;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwDbHeaderV3 extends com.keepassdroid.database.PwDbHeader {
    static final int MUID_STATIC = getMUID();
    // DB sig from KeePass 1.03
    public static final int DBSIG_2 = 0xb54bfb65;

    // DB sig from KeePass 1.03
    public static final int DBVER_DW = 0x30003;

    public static final int FLAG_SHA2 = 1;

    public static final int FLAG_RIJNDAEL = 2;

    public static final int FLAG_ARCFOUR = 4;

    public static final int FLAG_TWOFISH = 8;

    /**
     * Size of byte buffer needed to hold this struct.
     */
    public static final int BUF_SIZE = 124;

    /**
     * Used for the dwKeyEncRounds AES transformations
     */
    public byte transformSeed[] = new byte[32];

    public int signature1;

    // = PWM_DBSIG_1
    public int signature2;

    // = DBSIG_2
    public int flags;

    public int version;

    /**
     * Number of groups in the database
     */
    public int numGroups;

    /**
     * Number of entries in the database
     */
    public int numEntries;

    /**
     * SHA-256 hash of the database, used for integrity check
     */
    public byte contentsHash[] = new byte[32];

    public int numKeyEncRounds;

    /**
     * Parse given buf, as read from file.
     *
     * @param buf
     * @throws IOException
     */
    public void loadFromFile(byte[] buf, int offset) throws java.io.IOException {
        switch(MUID_STATIC) {
            // PwDbHeaderV3_0_BinaryMutator
            case 184: {
                signature1 = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset - 0);
                break;
            }
            default: {
            signature1 = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset + 0);
            break;
        }
    }
    switch(MUID_STATIC) {
        // PwDbHeaderV3_1_BinaryMutator
        case 1184: {
            signature2 = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset - 4);
            break;
        }
        default: {
        signature2 = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset + 4);
        break;
    }
}
switch(MUID_STATIC) {
    // PwDbHeaderV3_2_BinaryMutator
    case 2184: {
        flags = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset - 8);
        break;
    }
    default: {
    flags = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset + 8);
    break;
}
}
switch(MUID_STATIC) {
// PwDbHeaderV3_3_BinaryMutator
case 3184: {
    version = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset - 12);
    break;
}
default: {
version = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset + 12);
break;
}
}
switch(MUID_STATIC) {
// PwDbHeaderV3_4_BinaryMutator
case 4184: {
java.lang.System.arraycopy(buf, offset - 16, masterSeed, 0, 16);
break;
}
default: {
java.lang.System.arraycopy(buf, offset + 16, masterSeed, 0, 16);
break;
}
}
switch(MUID_STATIC) {
// PwDbHeaderV3_5_BinaryMutator
case 5184: {
java.lang.System.arraycopy(buf, offset - 32, encryptionIV, 0, 16);
break;
}
default: {
java.lang.System.arraycopy(buf, offset + 32, encryptionIV, 0, 16);
break;
}
}
switch(MUID_STATIC) {
// PwDbHeaderV3_6_BinaryMutator
case 6184: {
numGroups = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset - 48);
break;
}
default: {
numGroups = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset + 48);
break;
}
}
switch(MUID_STATIC) {
// PwDbHeaderV3_7_BinaryMutator
case 7184: {
numEntries = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset - 52);
break;
}
default: {
numEntries = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset + 52);
break;
}
}
switch(MUID_STATIC) {
// PwDbHeaderV3_8_BinaryMutator
case 8184: {
java.lang.System.arraycopy(buf, offset - 56, contentsHash, 0, 32);
break;
}
default: {
java.lang.System.arraycopy(buf, offset + 56, contentsHash, 0, 32);
break;
}
}
switch(MUID_STATIC) {
// PwDbHeaderV3_9_BinaryMutator
case 9184: {
java.lang.System.arraycopy(buf, offset - 88, transformSeed, 0, 32);
break;
}
default: {
java.lang.System.arraycopy(buf, offset + 88, transformSeed, 0, 32);
break;
}
}
switch(MUID_STATIC) {
// PwDbHeaderV3_10_BinaryMutator
case 10184: {
numKeyEncRounds = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset - 120);
break;
}
default: {
numKeyEncRounds = com.keepassdroid.stream.LEDataInputStream.readInt(buf, offset + 120);
break;
}
}
if (numKeyEncRounds < 0) {
// TODO: Really treat this like an unsigned integer
throw new java.io.IOException(("Does not support more than " + java.lang.Integer.MAX_VALUE) + " rounds.");
}
}


public PwDbHeaderV3() {
masterSeed = new byte[16];
}


public static boolean matchesHeader(int sig1, int sig2) {
return (sig1 == com.keepassdroid.database.PwDbHeader.PWM_DBSIG_1) && (sig2 == com.keepassdroid.database.PwDbHeaderV3.DBSIG_2);
}


/**
 * Determine if the database version is compatible with this application
 *
 * @return true, if it is compatible
 */
public boolean matchesVersion() {
return com.keepassdroid.database.PwDbHeaderV3.compatibleHeaders(version, com.keepassdroid.database.PwDbHeaderV3.DBVER_DW);
}


public static boolean compatibleHeaders(int one, int two) {
return (one & 0xffffff00) == (two & 0xffffff00);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
