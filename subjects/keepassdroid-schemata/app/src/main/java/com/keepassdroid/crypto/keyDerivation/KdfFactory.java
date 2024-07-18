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
package com.keepassdroid.crypto.keyDerivation;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class KdfFactory {
    static final int MUID_STATIC = getMUID();
    public static java.util.List<com.keepassdroid.crypto.keyDerivation.KdfEngine> kdfList = new java.util.ArrayList<com.keepassdroid.crypto.keyDerivation.KdfEngine>();

    static {
        com.keepassdroid.crypto.keyDerivation.KdfFactory.kdfList.add(new com.keepassdroid.crypto.keyDerivation.AesKdf());
        com.keepassdroid.crypto.keyDerivation.KdfFactory.kdfList.add(new com.keepassdroid.crypto.keyDerivation.Argon2Kdf(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.Argon2Type.D));
        com.keepassdroid.crypto.keyDerivation.KdfFactory.kdfList.add(new com.keepassdroid.crypto.keyDerivation.Argon2Kdf(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.Argon2Type.ID));
    }

    public static com.keepassdroid.crypto.keyDerivation.KdfParameters getDefaultParameters() {
        return com.keepassdroid.crypto.keyDerivation.KdfFactory.kdfList.get(0).getDefaultParameters();
    }


    public static com.keepassdroid.crypto.keyDerivation.KdfEngine get(java.util.UUID uuid) {
        for (com.keepassdroid.crypto.keyDerivation.KdfEngine engine : com.keepassdroid.crypto.keyDerivation.KdfFactory.kdfList) {
            if (engine.uuid.equals(uuid)) {
                return engine;
            }
        }
        return null;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
