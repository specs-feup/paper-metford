/* Copyright 2013-2018 Brian Pellin.

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
package com.keepassdroid.database.security;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import javax.crypto.CipherOutputStream;
import android.util.Log;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.security.SecureRandom;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import com.keepassdroid.crypto.CipherFactory;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayInputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.CipherInputStream;
import java.io.File;
import javax.crypto.Cipher;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ProtectedBinary {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = com.keepassdroid.database.security.ProtectedBinary.class.getName();

    public static final com.keepassdroid.database.security.ProtectedBinary EMPTY = new com.keepassdroid.database.security.ProtectedBinary();

    private byte[] data;

    private boolean protect;

    private java.io.File dataFile;

    private int size;

    private static final java.security.SecureRandom secureRandom = new java.security.SecureRandom();

    private com.keepassdroid.database.security.ProtectedBinary.FileParams fileParams;

    private class FileParams {
        private java.io.File dataFile;

        public javax.crypto.CipherOutputStream cos;

        public javax.crypto.spec.SecretKeySpec keySpec;

        public javax.crypto.spec.IvParameterSpec ivSpec;

        public javax.crypto.Cipher initCipher(int mode) {
            javax.crypto.Cipher cipher;
            try {
                cipher = com.keepassdroid.crypto.CipherFactory.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(mode, keySpec, ivSpec);
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new java.lang.IllegalStateException(e);
            } catch (javax.crypto.NoSuchPaddingException e) {
                throw new java.lang.IllegalStateException(e);
            } catch (java.security.InvalidKeyException e) {
                throw new java.lang.IllegalStateException(e);
            } catch (java.security.InvalidAlgorithmParameterException e) {
                throw new java.lang.IllegalStateException(e);
            }
            return cipher;
        }


        public void setupEnc(java.io.File file) {
            byte[] iv;
            iv = new byte[16];
            byte[] key;
            key = new byte[32];
            com.keepassdroid.database.security.ProtectedBinary.secureRandom.nextBytes(key);
            com.keepassdroid.database.security.ProtectedBinary.secureRandom.nextBytes(iv);
            keySpec = new javax.crypto.spec.SecretKeySpec(key, "AES");
            ivSpec = new javax.crypto.spec.IvParameterSpec(iv);
            javax.crypto.Cipher cipherOut;
            cipherOut = initCipher(javax.crypto.Cipher.ENCRYPT_MODE);
            java.io.FileOutputStream fos;
            try {
                fos = new java.io.FileOutputStream(file);
            } catch (java.io.FileNotFoundException e) {
                throw new java.lang.IllegalStateException(e);
            }
            cos = new javax.crypto.CipherOutputStream(fos, cipherOut);
        }


        public FileParams(java.io.File dataFile) {
            this.dataFile = dataFile;
            setupEnc(dataFile);
        }

    }

    public boolean isProtected() {
        return protect;
    }


    public int length() {
        if (data != null)
            return data.length;

        if (dataFile != null)
            return size;

        return 0;
    }


    private ProtectedBinary() {
        this.protect = false;
        this.data = null;
        this.dataFile = null;
        this.size = 0;
    }


    public ProtectedBinary(boolean enableProtection, byte[] data) {
        this.protect = enableProtection;
        this.data = data;
        this.dataFile = null;
        if (data != null)
            this.size = data.length;
        else
            this.size = 0;

    }


    public ProtectedBinary(boolean enableProtection, java.io.File dataFile, int size) {
        this.protect = enableProtection;
        this.data = null;
        this.dataFile = dataFile;
        this.size = size;
        fileParams = new com.keepassdroid.database.security.ProtectedBinary.FileParams(dataFile);
    }


    public java.io.OutputStream getOutputStream() {
        assert fileParams != null;
        return fileParams.cos;
    }


    public java.io.InputStream getData() throws java.io.IOException {
        if (data != null)
            return new java.io.ByteArrayInputStream(data);
        else if (dataFile != null)
            return new javax.crypto.CipherInputStream(new java.io.FileInputStream(dataFile), fileParams.initCipher(javax.crypto.Cipher.DECRYPT_MODE));
        else
            return null;

    }


    public void clear() {
        data = null;
        if ((dataFile != null) && (!dataFile.delete()))
            android.util.Log.e(com.keepassdroid.database.security.ProtectedBinary.TAG, "Unable to delete temp file " + dataFile.getAbsolutePath());

    }


    public boolean equals(com.keepassdroid.database.security.ProtectedBinary o) {
        return (this == o) || (((((((o != null) && (getClass() == o.getClass())) && (protect == o.protect)) && (size == o.size)) && java.util.Arrays.equals(data, o.data)) && (dataFile != null)) && dataFile.equals(o.dataFile));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
