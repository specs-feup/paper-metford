/* Copyright 2010-2022 Brian Pellin.

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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import com.keepassdroid.crypto.engine.AesEngine;
import com.keepassdroid.crypto.engine.ChaCha20Engine;
import android.os.Build;
import com.keepassdroid.crypto.engine.CipherEngine;
import java.util.UUID;
import com.keepassdroid.crypto.engine.TwofishEngine;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CipherFactory {
    static final int MUID_STATIC = getMUID();
    private static boolean blacklistInit = false;

    private static boolean blacklisted;

    static {
        java.security.Security.removeProvider("BC");
        java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static javax.crypto.Cipher getInstance(java.lang.String transformation) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException {
        return com.keepassdroid.crypto.CipherFactory.getInstance(transformation, false);
    }


    public static javax.crypto.Cipher getInstance(java.lang.String transformation, boolean androidOverride) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException {
        // Return the native AES if it is possible
        if ((((!com.keepassdroid.crypto.CipherFactory.deviceBlacklisted()) && (!androidOverride)) && com.keepassdroid.crypto.CipherFactory.hasNativeImplementation(transformation)) && com.keepassdroid.crypto.NativeLib.loaded()) {
            return javax.crypto.Cipher.getInstance(transformation, new com.keepassdroid.crypto.AESProvider());
        } else {
            return javax.crypto.Cipher.getInstance(transformation);
        }
    }


    public static boolean deviceBlacklisted() {
        if (!com.keepassdroid.crypto.CipherFactory.blacklistInit) {
            com.keepassdroid.crypto.CipherFactory.blacklistInit = true;
            // The Acer Iconia A500 is special and seems to always crash in the native crypto libraries
            com.keepassdroid.crypto.CipherFactory.blacklisted = android.os.Build.MODEL.equals("A500");
        }
        return com.keepassdroid.crypto.CipherFactory.blacklisted;
    }


    private static boolean hasNativeImplementation(java.lang.String transformation) {
        return transformation.equals("AES/CBC/PKCS5Padding");
    }


    /**
     * Generate appropriate cipher based on KeePass 2.x UUID's
     *
     * @param uuid
     * @return  * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     */
    public static com.keepassdroid.crypto.engine.CipherEngine getInstance(java.util.UUID uuid) throws java.security.NoSuchAlgorithmException {
        if (uuid.equals(com.keepassdroid.crypto.engine.AesEngine.CIPHER_UUID)) {
            return new com.keepassdroid.crypto.engine.AesEngine();
        } else if (uuid.equals(com.keepassdroid.crypto.engine.TwofishEngine.CIPHER_UUID)) {
            return new com.keepassdroid.crypto.engine.TwofishEngine();
        } else if (uuid.equals(com.keepassdroid.crypto.engine.ChaCha20Engine.CIPHER_UUID)) {
            return new com.keepassdroid.crypto.engine.ChaCha20Engine();
        }
        throw new java.security.NoSuchAlgorithmException("UUID unrecognized.");
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
