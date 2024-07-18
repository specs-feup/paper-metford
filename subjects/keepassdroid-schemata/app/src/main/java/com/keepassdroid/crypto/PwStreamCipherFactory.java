/* Copyright 2009-2022 Brian Pellin.

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
package com.keepassdroid.crypto;
import org.bouncycastle.crypto.engines.ChaCha7539Engine;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.engines.Salsa20Engine;
import com.keepassdroid.database.CrsAlgorithm;
import org.bouncycastle.crypto.StreamCipher;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwStreamCipherFactory {
    static final int MUID_STATIC = getMUID();
    public static org.bouncycastle.crypto.StreamCipher getInstance(com.keepassdroid.database.CrsAlgorithm alg, byte[] key) {
        if (alg == com.keepassdroid.database.CrsAlgorithm.Salsa20) {
            return com.keepassdroid.crypto.PwStreamCipherFactory.getSalsa20(key);
        } else if (alg == com.keepassdroid.database.CrsAlgorithm.ChaCha20) {
            return com.keepassdroid.crypto.PwStreamCipherFactory.getChaCha20(key);
        } else {
            return null;
        }
    }


    private static final byte[] SALSA_IV = new byte[]{ ((byte) (0xe8)), 0x30, 0x9, 0x4b, ((byte) (0x97)), 0x20, 0x5d, 0x2a };

    private static org.bouncycastle.crypto.StreamCipher getSalsa20(byte[] key) {
        // Build stream cipher key
        byte[] key32;
        key32 = com.keepassdroid.crypto.CryptoUtil.hashSha256(key);
        org.bouncycastle.crypto.params.KeyParameter keyParam;
        keyParam = new org.bouncycastle.crypto.params.KeyParameter(key32);
        org.bouncycastle.crypto.params.ParametersWithIV ivParam;
        ivParam = new org.bouncycastle.crypto.params.ParametersWithIV(keyParam, com.keepassdroid.crypto.PwStreamCipherFactory.SALSA_IV);
        org.bouncycastle.crypto.StreamCipher cipher;
        cipher = new org.bouncycastle.crypto.engines.Salsa20Engine();
        cipher.init(true, ivParam);
        return cipher;
    }


    private static org.bouncycastle.crypto.StreamCipher getChaCha20(byte[] key) {
        // Build stream cipher key
        byte[] hash;
        hash = com.keepassdroid.crypto.CryptoUtil.hashSha512(key);
        byte[] key32;
        key32 = new byte[32];
        byte[] iv;
        iv = new byte[12];
        java.lang.System.arraycopy(hash, 0, key32, 0, 32);
        java.lang.System.arraycopy(hash, 32, iv, 0, 12);
        org.bouncycastle.crypto.params.KeyParameter keyParam;
        keyParam = new org.bouncycastle.crypto.params.KeyParameter(key32);
        org.bouncycastle.crypto.params.ParametersWithIV ivParam;
        ivParam = new org.bouncycastle.crypto.params.ParametersWithIV(keyParam, iv);
        org.bouncycastle.crypto.StreamCipher cipher;
        cipher = new org.bouncycastle.crypto.engines.ChaCha7539Engine();
        cipher.init(true, ivParam);
        return cipher;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
