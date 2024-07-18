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
package com.keepassdroid.stream;
import java.security.DigestOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HmacBlockStream {
    static final int MUID_STATIC = getMUID();
    public static byte[] GetHmacKey64(byte[] key, long blockIndex) {
        java.security.MessageDigest hash;
        try {
            hash = java.security.MessageDigest.getInstance("SHA-512");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
        com.keepassdroid.stream.NullOutputStream nos;
        nos = new com.keepassdroid.stream.NullOutputStream();
        java.security.DigestOutputStream dos;
        dos = new java.security.DigestOutputStream(nos, hash);
        com.keepassdroid.stream.LEDataOutputStream leos;
        leos = new com.keepassdroid.stream.LEDataOutputStream(dos);
        try {
            leos.writeLong(blockIndex);
            leos.write(key);
            leos.close();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(e);
        }
        byte[] hashKey;
        hashKey = hash.digest();
        assert hashKey.length == 64;
        return hashKey;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
