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
import com.keepassdroid.utils.Types;
import com.keepassdroid.crypto.CryptoUtil;
import com.keepassdroid.crypto.finalkey.FinalKey;
import com.keepassdroid.crypto.finalkey.FinalKeyFactory;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.UUID;
import com.keepassdroid.database.PwDatabaseV4;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AesKdf extends com.keepassdroid.crypto.keyDerivation.KdfEngine {
    static final int MUID_STATIC = getMUID();
    public static final java.util.UUID CIPHER_UUID = com.keepassdroid.utils.Types.bytestoUUID(new byte[]{ ((byte) (0xc9)), ((byte) (0xd9)), ((byte) (0xf3)), ((byte) (0x9a)), ((byte) (0x62)), ((byte) (0x8a)), ((byte) (0x44)), ((byte) (0x60)), ((byte) (0xbf)), ((byte) (0x74)), ((byte) (0xd)), ((byte) (0x8)), ((byte) (0xc1)), ((byte) (0x8a)), ((byte) (0x4f)), ((byte) (0xea)) });

    public static final java.lang.String ParamRounds = "R";

    public static final java.lang.String ParamSeed = "S";

    public AesKdf() {
        uuid = com.keepassdroid.crypto.keyDerivation.AesKdf.CIPHER_UUID;
    }


    @java.lang.Override
    public com.keepassdroid.crypto.keyDerivation.KdfParameters getDefaultParameters() {
        com.keepassdroid.crypto.keyDerivation.KdfParameters p;
        p = super.getDefaultParameters();
        p.setUInt32(com.keepassdroid.crypto.keyDerivation.AesKdf.ParamRounds, com.keepassdroid.database.PwDatabaseV4.DEFAULT_ROUNDS);
        return p;
    }


    @java.lang.Override
    public byte[] transform(byte[] masterKey, com.keepassdroid.crypto.keyDerivation.KdfParameters p) throws java.io.IOException {
        long rounds;
        rounds = p.getUInt64(com.keepassdroid.crypto.keyDerivation.AesKdf.ParamRounds);
        byte[] seed;
        seed = p.getByteArray(com.keepassdroid.crypto.keyDerivation.AesKdf.ParamSeed);
        if (masterKey.length != 32) {
            masterKey = com.keepassdroid.crypto.CryptoUtil.hashSha256(masterKey);
        }
        if (seed.length != 32) {
            seed = com.keepassdroid.crypto.CryptoUtil.hashSha256(seed);
        }
        com.keepassdroid.crypto.finalkey.FinalKey key;
        key = com.keepassdroid.crypto.finalkey.FinalKeyFactory.createFinalKey();
        return key.transformMasterKey(seed, masterKey, rounds);
    }


    @java.lang.Override
    public void randomize(com.keepassdroid.crypto.keyDerivation.KdfParameters p) {
        java.security.SecureRandom random;
        random = new java.security.SecureRandom();
        byte[] seed;
        seed = new byte[32];
        random.nextBytes(seed);
        p.setByteArray(com.keepassdroid.crypto.keyDerivation.AesKdf.ParamSeed, seed);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
