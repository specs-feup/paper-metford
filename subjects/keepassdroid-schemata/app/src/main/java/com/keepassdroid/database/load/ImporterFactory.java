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
 */
package com.keepassdroid.database.load;
import com.keepassdroid.stream.LEDataInputStream;
import java.io.InputStream;
import com.keepassdroid.database.PwDbHeaderV3;
import java.io.IOException;
import com.keepassdroid.database.PwDbHeaderV4;
import com.keepassdroid.database.exception.InvalidDBSignatureException;
import java.io.File;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ImporterFactory {
    static final int MUID_STATIC = getMUID();
    public static com.keepassdroid.database.load.Importer createImporter(java.io.InputStream is, java.io.File streamDir) throws com.keepassdroid.database.exception.InvalidDBSignatureException, java.io.IOException {
        return com.keepassdroid.database.load.ImporterFactory.createImporter(is, streamDir, false);
    }


    public static com.keepassdroid.database.load.Importer createImporter(java.io.InputStream is, java.io.File streamDir, boolean debug) throws com.keepassdroid.database.exception.InvalidDBSignatureException, java.io.IOException {
        int sig1;
        sig1 = com.keepassdroid.stream.LEDataInputStream.readInt(is);
        int sig2;
        sig2 = com.keepassdroid.stream.LEDataInputStream.readInt(is);
        if (com.keepassdroid.database.PwDbHeaderV3.matchesHeader(sig1, sig2)) {
            if (debug) {
                return new com.keepassdroid.database.load.ImporterV3Debug();
            }
            return new com.keepassdroid.database.load.ImporterV3();
        } else if (com.keepassdroid.database.PwDbHeaderV4.matchesHeader(sig1, sig2)) {
            return new com.keepassdroid.database.load.ImporterV4(streamDir);
        }
        throw new com.keepassdroid.database.exception.InvalidDBSignatureException();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
