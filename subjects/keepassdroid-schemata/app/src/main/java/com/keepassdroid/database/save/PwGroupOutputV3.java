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
import com.keepassdroid.stream.LEDataOutputStream;
import com.keepassdroid.database.PwGroupV3;
import com.keepassdroid.utils.Types;
import java.io.OutputStream;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwGroupOutputV3 {
    static final int MUID_STATIC = getMUID();
    // Constants
    public static final byte[] GROUPID_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(1);

    public static final byte[] NAME_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(2);

    public static final byte[] CREATE_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(3);

    public static final byte[] MOD_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(4);

    public static final byte[] ACCESS_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(5);

    public static final byte[] EXPIRE_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(6);

    public static final byte[] IMAGEID_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(7);

    public static final byte[] LEVEL_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(8);

    public static final byte[] FLAGS_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(9);

    public static final byte[] END_FIELD_TYPE = com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(0xffff);

    public static final byte[] LONG_FOUR = com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(4);

    public static final byte[] GROUPID_FIELD_SIZE = com.keepassdroid.database.save.PwGroupOutputV3.LONG_FOUR;

    public static final byte[] DATE_FIELD_SIZE = com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(5);

    public static final byte[] IMAGEID_FIELD_SIZE = com.keepassdroid.database.save.PwGroupOutputV3.LONG_FOUR;

    public static final byte[] LEVEL_FIELD_SIZE = com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(2);

    public static final byte[] FLAGS_FIELD_SIZE = com.keepassdroid.database.save.PwGroupOutputV3.LONG_FOUR;

    public static final byte[] ZERO_FIELD_SIZE = com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(0);

    private java.io.OutputStream mOS;

    private com.keepassdroid.database.PwGroupV3 mPG;

    /**
     * Output the PwGroupV3 to the stream
     *
     * @param pg
     * @param os
     */
    public PwGroupOutputV3(com.keepassdroid.database.PwGroupV3 pg, java.io.OutputStream os) {
        mPG = pg;
        mOS = os;
    }


    public void output() throws java.io.IOException {
        // NOTE: Need be to careful about using ints.  The actual type written to file is a unsigned int, but most values can't be greater than 2^31, so it probably doesn't matter.
        // Group ID
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.GROUPID_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.GROUPID_FIELD_SIZE);
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mPG.groupId));
        // Name
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.NAME_FIELD_TYPE);
        com.keepassdroid.utils.Types.writeCString(mPG.name, mOS);
        // Create date
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.CREATE_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.DATE_FIELD_SIZE);
        mOS.write(mPG.tCreation.getCDate());
        // Modification date
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.MOD_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.DATE_FIELD_SIZE);
        mOS.write(mPG.tLastMod.getCDate());
        // Access date
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.ACCESS_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.DATE_FIELD_SIZE);
        mOS.write(mPG.tLastAccess.getCDate());
        // Expiration date
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.EXPIRE_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.DATE_FIELD_SIZE);
        mOS.write(mPG.tExpire.getCDate());
        // Image ID
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.IMAGEID_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.IMAGEID_FIELD_SIZE);
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mPG.icon.iconId));
        // Level
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.LEVEL_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.LEVEL_FIELD_SIZE);
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeUShortBuf(mPG.level));
        // Flags
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.FLAGS_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.FLAGS_FIELD_SIZE);
        mOS.write(com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(mPG.flags));
        // End
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.END_FIELD_TYPE);
        mOS.write(com.keepassdroid.database.save.PwGroupOutputV3.ZERO_FIELD_SIZE);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
