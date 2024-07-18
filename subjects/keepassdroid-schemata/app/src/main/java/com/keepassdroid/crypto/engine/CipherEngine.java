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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class CipherEngine {
    static final int MUID_STATIC = getMUID();
    public int keyLength() {
        return 32;
    }


    public int ivLength() {
        return 16;
    }


    public abstract javax.crypto.Cipher getCipher(int opmode, byte[] key, byte[] IV, boolean androidOverride) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException;


    public javax.crypto.Cipher getCipher(int opmode, byte[] key, byte[] IV) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        return getCipher(opmode, key, IV, false);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
