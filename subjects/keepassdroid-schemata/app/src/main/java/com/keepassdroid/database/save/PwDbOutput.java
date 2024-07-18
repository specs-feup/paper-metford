/* Copyright 2010-2017 Brian Pellin.

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
import java.io.OutputStream;
import com.keepassdroid.database.PwDatabase;
import com.keepassdroid.database.exception.PwDbOutputException;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import com.keepassdroid.database.PwDbHeader;
import com.keepassdroid.database.PwDatabaseV4;
import com.keepassdroid.database.PwDatabaseV3;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class PwDbOutput {
    static final int MUID_STATIC = getMUID();
    protected java.io.OutputStream mOS;

    public static com.keepassdroid.database.save.PwDbOutput getInstance(com.keepassdroid.database.PwDatabase pm, java.io.OutputStream os) {
        if (pm instanceof com.keepassdroid.database.PwDatabaseV3) {
            return new com.keepassdroid.database.save.PwDbV3Output(((com.keepassdroid.database.PwDatabaseV3) (pm)), os);
        } else if (pm instanceof com.keepassdroid.database.PwDatabaseV4) {
            return new com.keepassdroid.database.save.PwDbV4Output(((com.keepassdroid.database.PwDatabaseV4) (pm)), os);
        }
        return null;
    }


    protected PwDbOutput(java.io.OutputStream os) {
        mOS = os;
    }


    protected java.security.SecureRandom setIVs(com.keepassdroid.database.PwDbHeader header) throws com.keepassdroid.database.exception.PwDbOutputException {
        java.security.SecureRandom random;
        try {
            random = java.security.SecureRandom.getInstance("SHA1PRNG");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new com.keepassdroid.database.exception.PwDbOutputException("Does not support secure random number generation.");
        }
        random.nextBytes(header.encryptionIV);
        random.nextBytes(header.masterSeed);
        return random;
    }


    public abstract void output() throws com.keepassdroid.database.exception.PwDbOutputException;


    public abstract com.keepassdroid.database.PwDbHeader outputHeader(java.io.OutputStream os) throws com.keepassdroid.database.exception.PwDbOutputException;


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
