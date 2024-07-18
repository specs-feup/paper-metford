/* Copyright 2009 Brian Pellin.

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
import com.keepassdroid.database.PwEntryV3;
import com.keepassdroid.stream.LEDataOutputStream;
import com.keepassdroid.utils.Types;
import java.io.OutputStream;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwEntryOutputV3 {
    static final int MUID_STATIC = getMUID();
    // Constants
    public static final byte[] UUID_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(1);

    public static final byte[] GROUPID_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(2);

    public static final byte[] IMAGEID_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(3);

    public static final byte[] TITLE_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(4);

    public static final byte[] URL_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(5);

    public static final byte[] USERNAME_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(6);

    public static final byte[] PASSWORD_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(7);

    public static final byte[] ADDITIONAL_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(8);

    public static final byte[] CREATE_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(9);

    public static final byte[] MOD_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(10);

    public static final byte[] ACCESS_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(11);

    public static final byte[] EXPIRE_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(12);

    public static final byte[] BINARY_DESC_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(13);

    public static final byte[] BINARY_DATA_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(14);

    public static final byte[] END_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(0xffff);

    public static final byte[] LONG_FOUR = com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(4);

    public static final byte[] UUID_FIELD_SIZE = com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(16);

    public static final byte[] DATE_FIELD_SIZE = com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(5);

    public static final byte[] IMAGEID_FIELD_SIZE = com.keepassdroid.database.save.PwEntryOutputV3.LONG_FOUR;

    public static final byte[] LEVEL_FIELD_SIZE = com.keepassdroid.database.save.PwEntryOutputV3.LONG_FOUR;

    public static final byte[] FLAGS_FIELD_SIZE = com.keepassdroid.database.save.PwEntryOutputV3.LONG_FOUR;

    public static final byte[] ZERO_FIELD_SIZE = com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(0);

    public static final byte[] ZERO_FIVE = new byte[]{ 0x0, 0x0, 0x0, 0x0, 0x0 };

    public static final byte[] TEST = new byte[]{ 0x33, 0x33, 0x33, 0x33 };

    private java.io.OutputStream mOS;

    private com.keepassdroid.database.PwEntryV3 mPE;

    private long outputBytes = 0;

    /**
     * Output the PwGroupV3 to the stream
     *
     * @param pe
     * @param os
     */
    public PwEntryOutputV3(com.keepassdroid.database.PwEntryV3 pe, java.io.OutputStream os) {
        mPE = pe;
        mOS = os;
    }


    // NOTE: Need be to careful about using ints.  The actual type written to file is a unsigned int
    public void output() throws java.io.IOException {
        outputBytes += 134// Length of fixed size fields
        ;// Length of fixed size fields

        // UUID
        mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.UUID_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.UUID_FIELD_SIZE);
        mOS.write(com.keepassdroid.utils.Types.UUIDtoBytes(mPE.getUUID()));
        // Group ID
        mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.GROUPID_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.LONG_FOUR);
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mPE.groupId));
        // Image ID
        mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.IMAGEID_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.LONG_FOUR);
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mPE.icon.iconId));
        // Title
        // byte[] title = mPE.title.getBytes("UTF-8");
        mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.TITLE_FIELD_TYPE);
        int titleLen;
        titleLen = com.keepassdroid.utils.Types.writeCString(mPE.title, mOS);
        outputBytes += titleLen;
        // URL
        mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.URL_FIELD_TYPE);
        int urlLen;
        urlLen = com.keepassdroid.utils.Types.writeCString(mPE.url, mOS);
        outputBytes += urlLen;
        // Username
        mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.USERNAME_FIELD_TYPE);
        int userLen;
        userLen = com.keepassdroid.utils.Types.writeCString(mPE.username, mOS);
        outputBytes += userLen;
        // Password
        byte[] password;
        password = mPE.getPasswordBytes();
        mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.PASSWORD_FIELD_TYPE);
        switch(MUID_STATIC) {
            // PwEntryOutputV3_0_BinaryMutator
            case 118: {
                mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(password.length - 1));
                break;
            }
            default: {
            mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(password.length + 1));
            break;
        }
    }
    mOS.write(password);
    mOS.write(0);
    switch(MUID_STATIC) {
        // PwEntryOutputV3_1_BinaryMutator
        case 1118: {
            outputBytes += password.length - 1;
            break;
        }
        default: {
        outputBytes += password.length + 1;
        break;
    }
}
// Additional
mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.ADDITIONAL_FIELD_TYPE);
int addlLen;
addlLen = com.keepassdroid.utils.Types.writeCString(mPE.additional, mOS);
outputBytes += addlLen;
// Create date
writeDate(com.keepassdroid.database.save.PwEntryOutputV3.CREATE_FIELD_TYPE, mPE.tCreation.getCDate());
// Modification date
writeDate(com.keepassdroid.database.save.PwEntryOutputV3.MOD_FIELD_TYPE, mPE.tLastMod.getCDate());
// Access date
writeDate(com.keepassdroid.database.save.PwEntryOutputV3.ACCESS_FIELD_TYPE, mPE.tLastAccess.getCDate());
// Expiration date
writeDate(com.keepassdroid.database.save.PwEntryOutputV3.EXPIRE_FIELD_TYPE, mPE.tExpire.getCDate());
// Binary desc
mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.BINARY_DESC_FIELD_TYPE);
int descLen;
descLen = com.keepassdroid.utils.Types.writeCString(mPE.binaryDesc, mOS);
outputBytes += descLen;
// Binary data
int dataLen;
dataLen = writeByteArray(mPE.getBinaryData());
outputBytes += dataLen;
// End
mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.END_FIELD_TYPE);
mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.ZERO_FIELD_SIZE);
}


private int writeByteArray(byte[] data) throws java.io.IOException {
int dataLen;
if (data != null) {
    dataLen = data.length;
} else {
    dataLen = 0;
}
mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.BINARY_DATA_FIELD_TYPE);
mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(dataLen));
if (data != null) {
    mOS.write(data);
}
return dataLen;
}


private void writeDate(byte[] type, byte[] date) throws java.io.IOException {
mOS.write(type);
mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.DATE_FIELD_SIZE);
if (date != null) {
    mOS.write(date);
} else {
    mOS.write(com.keepassdroid.database.save.PwEntryOutputV3.ZERO_FIVE);
}
}


/**
 * Returns the number of bytes written by the stream
 *
 * @return Number of bytes written
 */
public long getLength() {
return outputBytes;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
