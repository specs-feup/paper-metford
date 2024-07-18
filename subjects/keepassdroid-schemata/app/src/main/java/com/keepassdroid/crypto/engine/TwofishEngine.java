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
package com.keepassdroid.crypto.engine;
import com.keepassdroid.utils.Types;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import com.keepassdroid.crypto.CipherFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.crypto.Cipher;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TwofishEngine extends com.keepassdroid.crypto.engine.CipherEngine {
    static final int MUID_STATIC = getMUID();
    public static final java.util.UUID CIPHER_UUID = com.keepassdroid.utils.Types.bytestoUUID(new byte[]{ ((byte) (0xad)), ((byte) (0x68)), ((byte) (0xf2)), ((byte) (0x9f)), ((byte) (0x57)), ((byte) (0x6f)), ((byte) (0x4b)), ((byte) (0xb9)), ((byte) (0xa3)), ((byte) (0x6a)), ((byte) (0xd4)), ((byte) (0x7a)), ((byte) (0xf9)), ((byte) (0x65)), ((byte) (0x34)), ((byte) (0x6c)) });

    @java.lang.Override
    public javax.crypto.Cipher getCipher(int opmode, byte[] key, byte[] IV, boolean androidOverride) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        javax.crypto.Cipher cipher;
        if (opmode == javax.crypto.Cipher.ENCRYPT_MODE) {
            cipher = com.keepassdroid.crypto.CipherFactory.getInstance("Twofish/CBC/ZeroBytePadding", androidOverride);
        } else {
            cipher = com.keepassdroid.crypto.CipherFactory.getInstance("Twofish/CBC/NoPadding", androidOverride);
        }
        cipher.init(opmode, new javax.crypto.spec.SecretKeySpec(key, "AES"), new javax.crypto.spec.IvParameterSpec(IV));
        return cipher;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
