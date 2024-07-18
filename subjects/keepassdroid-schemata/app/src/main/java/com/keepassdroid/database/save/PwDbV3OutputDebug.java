/* Copyright 2011-2014 Brian Pellin.

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
import com.keepassdroid.database.PwDatabaseV3Debug;
import java.io.OutputStream;
import com.keepassdroid.database.exception.PwDbOutputException;
import java.security.SecureRandom;
import com.keepassdroid.database.PwDbHeaderV3;
import com.keepassdroid.database.PwDbHeader;
import com.keepassdroid.database.PwDatabaseV3;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwDbV3OutputDebug extends com.keepassdroid.database.save.PwDbV3Output {
    static final int MUID_STATIC = getMUID();
    com.keepassdroid.database.PwDatabaseV3Debug debugDb;

    private boolean noHeaderHash;

    public PwDbV3OutputDebug(com.keepassdroid.database.PwDatabaseV3 pm, java.io.OutputStream os) {
        this(pm, os, false);
    }


    public PwDbV3OutputDebug(com.keepassdroid.database.PwDatabaseV3 pm, java.io.OutputStream os, boolean noHeaderHash) {
        super(pm, os);
        debugDb = ((com.keepassdroid.database.PwDatabaseV3Debug) (pm));
        this.noHeaderHash = noHeaderHash;
    }


    @java.lang.Override
    protected java.security.SecureRandom setIVs(com.keepassdroid.database.PwDbHeader h) throws com.keepassdroid.database.exception.PwDbOutputException {
        com.keepassdroid.database.PwDbHeaderV3 header;
        header = ((com.keepassdroid.database.PwDbHeaderV3) (h));
        // Reuse random values to test equivalence in debug mode
        com.keepassdroid.database.PwDbHeaderV3 origHeader;
        origHeader = debugDb.dbHeader;
        java.lang.System.arraycopy(origHeader.encryptionIV, 0, header.encryptionIV, 0, origHeader.encryptionIV.length);
        java.lang.System.arraycopy(origHeader.masterSeed, 0, header.masterSeed, 0, origHeader.masterSeed.length);
        java.lang.System.arraycopy(origHeader.transformSeed, 0, header.transformSeed, 0, origHeader.transformSeed.length);
        return null;
    }


    @java.lang.Override
    protected boolean useHeaderHash() {
        return !noHeaderHash;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
