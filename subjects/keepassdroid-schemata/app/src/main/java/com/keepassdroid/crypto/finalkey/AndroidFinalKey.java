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
package com.keepassdroid.crypto.finalkey;
import java.security.InvalidKeyException;
import javax.crypto.spec.SecretKeySpec;
import android.annotation.SuppressLint;
import java.io.IOException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AndroidFinalKey extends com.keepassdroid.crypto.finalkey.FinalKey {
    static final int MUID_STATIC = getMUID();
    @android.annotation.SuppressLint("GetInstance")
    @java.lang.Override
    public byte[] transformMasterKey(byte[] pKeySeed, byte[] pKey, long rounds) throws java.io.IOException {
        javax.crypto.Cipher cipher;
        try {
            cipher = javax.crypto.Cipher.getInstance("AES/ECB/NoPadding");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("NoSuchAlgorithm: " + e.getMessage());
        } catch (javax.crypto.NoSuchPaddingException e) {
            throw new java.io.IOException("NoSuchPadding: " + e.getMessage());
        }
        try {
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, new javax.crypto.spec.SecretKeySpec(pKeySeed, "AES"));
        } catch (java.security.InvalidKeyException e) {
            throw new java.io.IOException("InvalidPasswordException: " + e.getMessage());
        }
        // Encrypt key rounds times
        byte[] newKey;
        newKey = new byte[pKey.length];
        java.lang.System.arraycopy(pKey, 0, newKey, 0, pKey.length);
        byte[] destKey;
        destKey = new byte[pKey.length];
        for (int i = 0; i < rounds; i++) {
            try {
                cipher.update(newKey, 0, newKey.length, destKey, 0);
                java.lang.System.arraycopy(destKey, 0, newKey, 0, newKey.length);
            } catch (javax.crypto.ShortBufferException e) {
                throw new java.io.IOException("Short buffer: " + e.getMessage());
            }
        }
        // Hash the key
        java.security.MessageDigest md;
        md = null;
        try {
            md = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            assert true;
            throw new java.io.IOException("SHA-256 not implemented here: " + e.getMessage());
        }
        md.update(newKey);
        return md.digest();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
