/* Copyright 2017-2021 Brian Pellin.

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
import java.io.IOException;
import java.security.SecureRandom;
import java.util.UUID;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Argon2Kdf extends com.keepassdroid.crypto.keyDerivation.KdfEngine {
    public enum Argon2Type {

        D(0),
        ID(2);
        private int type;

        Argon2Type(int type) {
            this.type = type;
        }


        int value() {
            return type;
        }

    }

    static final int MUID_STATIC = getMUID();
    public static final java.util.UUID CIPHER_UUID_D = com.keepassdroid.utils.Types.bytestoUUID(new byte[]{ ((byte) (0xef)), ((byte) (0x63)), ((byte) (0x6d)), ((byte) (0xdf)), ((byte) (0x8c)), ((byte) (0x29)), ((byte) (0x44)), ((byte) (0x4b)), ((byte) (0x91)), ((byte) (0xf7)), ((byte) (0xa9)), ((byte) (0xa4)), ((byte) (0x3)), ((byte) (0xe3)), ((byte) (0xa)), ((byte) (0xc)) });

    public static final java.util.UUID CIPHER_UUID_ID = com.keepassdroid.utils.Types.bytestoUUID(new byte[]{ ((byte) (0x9e)), ((byte) (0x29)), ((byte) (0x8b)), ((byte) (0x19)), ((byte) (0x56)), ((byte) (0xdb)), ((byte) (0x47)), ((byte) (0x73)), ((byte) (0xb2)), ((byte) (0x3d)), ((byte) (0xfc)), ((byte) (0x3e)), ((byte) (0xc6)), ((byte) (0xf0)), ((byte) (0xa1)), ((byte) (0xe6)) });

    public static final java.lang.String ParamSalt = "S";// byte[]


    public static final java.lang.String ParamParallelism = "P";// UInt32


    public static final java.lang.String ParamMemory = "M";// UInt64


    public static final java.lang.String ParamIterations = "I";// UInt64


    public static final java.lang.String ParamVersion = "V";// UInt32


    public static final java.lang.String ParamSecretKey = "K";// byte[]


    public static final java.lang.String ParamAssocData = "A";// byte[]


    public static final long MinVersion = 0x10;

    public static final long MaxVersion = 0x13;

    private static final int MinSalt = 8;

    private static final int MaxSalt = java.lang.Integer.MAX_VALUE;

    private static final long MinIterations = 1;

    private static final long MaxIterations = 4294967295L;

    private static final long MinMemory = 1024 * 8;

    private static final long MaxMemory = java.lang.Integer.MAX_VALUE;

    private static final int MinParallelism = 1;

    private static final int MaxParallelism = (1 << 24) - 1;

    private static final long DefaultIterations = 2;

    private static final long DefaultMemory = 1024 * 1024;

    private static final long DefaultParallelism = 2;

    private com.keepassdroid.crypto.keyDerivation.Argon2Kdf.Argon2Type type;

    public Argon2Kdf(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.Argon2Type type) {
        if (type == com.keepassdroid.crypto.keyDerivation.Argon2Kdf.Argon2Type.D) {
            uuid = com.keepassdroid.crypto.keyDerivation.Argon2Kdf.CIPHER_UUID_D;
        } else {
            uuid = com.keepassdroid.crypto.keyDerivation.Argon2Kdf.CIPHER_UUID_ID;
        }
        this.type = type;
    }


    @java.lang.Override
    public com.keepassdroid.crypto.keyDerivation.KdfParameters getDefaultParameters() {
        com.keepassdroid.crypto.keyDerivation.KdfParameters p;
        p = super.getDefaultParameters();
        p.setUInt32(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamVersion, com.keepassdroid.crypto.keyDerivation.Argon2Kdf.MaxVersion);
        p.setUInt64(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamMemory, com.keepassdroid.crypto.keyDerivation.Argon2Kdf.DefaultMemory);
        p.setUInt32(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamParallelism, com.keepassdroid.crypto.keyDerivation.Argon2Kdf.DefaultParallelism);
        return p;
    }


    @java.lang.Override
    public byte[] transform(byte[] masterKey, com.keepassdroid.crypto.keyDerivation.KdfParameters p) throws java.io.IOException {
        byte[] salt;
        salt = p.getByteArray(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamSalt);
        int parallelism;
        parallelism = ((int) (p.getUInt32(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamParallelism)));
        long memory;
        memory = p.getUInt64(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamMemory);
        long iterations;
        iterations = p.getUInt64(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamIterations);
        long version;
        version = p.getUInt32(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamVersion);
        byte[] secretKey;
        secretKey = p.getByteArray(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamSecretKey);
        byte[] assocData;
        assocData = p.getByteArray(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamAssocData);
        return com.keepassdroid.crypto.keyDerivation.Argon2Native.transformKey(masterKey, salt, parallelism, memory, iterations, secretKey, assocData, version, type.value());
    }


    @java.lang.Override
    public void randomize(com.keepassdroid.crypto.keyDerivation.KdfParameters p) {
        java.security.SecureRandom random;
        random = new java.security.SecureRandom();
        byte[] salt;
        salt = new byte[32];
        random.nextBytes(salt);
        p.setByteArray(com.keepassdroid.crypto.keyDerivation.Argon2Kdf.ParamSalt, salt);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
