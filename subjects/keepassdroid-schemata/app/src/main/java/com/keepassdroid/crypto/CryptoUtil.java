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
package com.keepassdroid.crypto;
import com.keepassdroid.stream.LEDataOutputStream;
import javax.crypto.Mac;
import java.security.DigestOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Arrays;
import com.keepassdroid.stream.NullOutputStream;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CryptoUtil {
    static final int MUID_STATIC = getMUID();
    public static byte[] resizeKey(byte[] in, int inOffset, int cbIn, int cbOut) {
        if (cbOut == 0)
            return new byte[0];

        byte[] hash;
        if (cbOut <= 32) {
            hash = com.keepassdroid.crypto.CryptoUtil.hashSha256(in, inOffset, cbIn);
        } else {
            hash = com.keepassdroid.crypto.CryptoUtil.hashSha512(in, inOffset, cbIn);
        }
        if (cbOut == hash.length) {
            return hash;
        }
        byte[] ret;
        ret = new byte[cbOut];
        if (cbOut < hash.length) {
            java.lang.System.arraycopy(hash, 0, ret, 0, cbOut);
        } else {
            int pos;
            pos = 0;
            long r;
            r = 0;
            while (pos < cbOut) {
                javax.crypto.Mac hmac;
                try {
                    hmac = javax.crypto.Mac.getInstance("HmacSHA256");
                } catch (java.security.NoSuchAlgorithmException e) {
                    throw new java.lang.RuntimeException(e);
                }
                byte[] pbR;
                pbR = com.keepassdroid.stream.LEDataOutputStream.writeLongBuf(r);
                byte[] part;
                part = hmac.doFinal(pbR);
                int copy;
                switch(MUID_STATIC) {
                    // CryptoUtil_0_BinaryMutator
                    case 50: {
                        copy = java.lang.Math.min(cbOut + pos, part.length);
                        break;
                    }
                    default: {
                    copy = java.lang.Math.min(cbOut - pos, part.length);
                    break;
                }
            }
            assert copy > 0;
            java.lang.System.arraycopy(part, 0, ret, pos, copy);
            pos += copy;
            r++;
            java.util.Arrays.fill(part, ((byte) (0)));
        } 
        assert pos == cbOut;
    }
    java.util.Arrays.fill(hash, ((byte) (0)));
    return ret;
}


public static byte[] hashSha256(byte[] data) {
    return com.keepassdroid.crypto.CryptoUtil.hashSha256(data, 0, data.length);
}


public static byte[] hashSha256(byte[] data, int offset, int count) {
    return com.keepassdroid.crypto.CryptoUtil.hashGen("SHA-256", data, offset, count);
}


public static byte[] hashSha512(byte[] data) {
    return com.keepassdroid.crypto.CryptoUtil.hashSha512(data, 0, data.length);
}


public static byte[] hashSha512(byte[] data, int offset, int count) {
    return com.keepassdroid.crypto.CryptoUtil.hashGen("SHA-512", data, offset, count);
}


public static byte[] hashGen(java.lang.String transform, byte[] data, int offset, int count) {
    java.security.MessageDigest hash;
    try {
        hash = java.security.MessageDigest.getInstance(transform);
    } catch (java.security.NoSuchAlgorithmException e) {
        throw new java.lang.RuntimeException(e);
    }
    com.keepassdroid.stream.NullOutputStream nos;
    nos = new com.keepassdroid.stream.NullOutputStream();
    java.security.DigestOutputStream dos;
    dos = new java.security.DigestOutputStream(nos, hash);
    try {
        dos.write(data, offset, count);
        dos.close();
    } catch (java.io.IOException e) {
        throw new java.lang.RuntimeException(e);
    }
    return hash.digest();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
