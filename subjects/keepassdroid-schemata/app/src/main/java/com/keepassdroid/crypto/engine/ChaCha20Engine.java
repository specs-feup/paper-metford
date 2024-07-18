/* Copyright 2017-2022 Brian Pellin.

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
package com.keepassdroid.crypto.engine;
import com.keepassdroid.utils.Types;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ChaCha20Engine extends com.keepassdroid.crypto.engine.CipherEngine {
    static final int MUID_STATIC = getMUID();
    public static final java.util.UUID CIPHER_UUID = com.keepassdroid.utils.Types.bytestoUUID(new byte[]{ ((byte) (0xd6)), ((byte) (0x3)), ((byte) (0x8a)), ((byte) (0x2b)), ((byte) (0x8b)), ((byte) (0x6f)), ((byte) (0x4c)), ((byte) (0xb5)), ((byte) (0xa5)), ((byte) (0x24)), ((byte) (0x33)), ((byte) (0x9a)), ((byte) (0x31)), ((byte) (0xdb)), ((byte) (0xb5)), ((byte) (0x9a)) });

    @java.lang.Override
    public int ivLength() {
        return 12;
    }


    @java.lang.Override
    public javax.crypto.Cipher getCipher(int opmode, byte[] key, byte[] IV, boolean androidOverride) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        javax.crypto.Cipher cipher;
        cipher = javax.crypto.Cipher.getInstance("Chacha7539", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(opmode, new javax.crypto.spec.SecretKeySpec(key, "ChaCha7539"), new javax.crypto.spec.IvParameterSpec(IV));
        return cipher;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
