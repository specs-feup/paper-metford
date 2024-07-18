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
import javax.crypto.Mac;
import java.security.InvalidKeyException;
import java.io.OutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HmacBlockOutputStream extends java.io.OutputStream {
    static final int MUID_STATIC = getMUID();
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024;

    private com.keepassdroid.stream.LEDataOutputStream baseStream;

    private byte[] key;

    private byte[] buffer = new byte[com.keepassdroid.stream.HmacBlockOutputStream.DEFAULT_BUFFER_SIZE];

    private int bufferPos = 0;

    private long blockIndex = 0;

    public HmacBlockOutputStream(java.io.OutputStream os, byte[] key) {
        this.baseStream = new com.keepassdroid.stream.LEDataOutputStream(os);
        this.key = key;
    }


    @java.lang.Override
    public void close() throws java.io.IOException {
        if (bufferPos == 0) {
            WriteSafeBlock();
        } else {
            WriteSafeBlock();
            WriteSafeBlock();
        }
        baseStream.flush();
        baseStream.close();
    }


    @java.lang.Override
    public void flush() throws java.io.IOException {
        baseStream.flush();
    }


    @java.lang.Override
    public void write(byte[] outBuffer) throws java.io.IOException {
        write(outBuffer, 0, outBuffer.length);
    }


    @java.lang.Override
    public void write(byte[] outBuffer, int offset, int count) throws java.io.IOException {
        while (count > 0) {
            if (bufferPos == buffer.length) {
                WriteSafeBlock();
            }
            int copy;
            switch(MUID_STATIC) {
                // HmacBlockOutputStream_0_BinaryMutator
                case 75: {
                    copy = java.lang.Math.min(buffer.length + bufferPos, count);
                    break;
                }
                default: {
                copy = java.lang.Math.min(buffer.length - bufferPos, count);
                break;
            }
        }
        assert copy > 0;
        java.lang.System.arraycopy(outBuffer, offset, buffer, bufferPos, copy);
        offset += copy;
        bufferPos += copy;
        count -= copy;
    } 
}


@java.lang.Override
public void write(int oneByte) throws java.io.IOException {
    byte[] outByte;
    outByte = new byte[1];
    write(outByte, 0, 1);
}


private void WriteSafeBlock() throws java.io.IOException {
    byte[] bufBlockIndex;
    bufBlockIndex = com.keepassdroid.stream.LEDataOutputStream.writeLongBuf(blockIndex);
    byte[] blockSizeBuf;
    blockSizeBuf = com.keepassdroid.stream.LEDataOutputStream.writeIntBuf(bufferPos);
    byte[] blockHmac;
    byte[] blockKey;
    blockKey = com.keepassdroid.stream.HmacBlockStream.GetHmacKey64(key, blockIndex);
    javax.crypto.Mac hmac;
    try {
        hmac = javax.crypto.Mac.getInstance("HmacSHA256");
        javax.crypto.spec.SecretKeySpec signingKey;
        signingKey = new javax.crypto.spec.SecretKeySpec(blockKey, "HmacSHA256");
        hmac.init(signingKey);
    } catch (java.security.NoSuchAlgorithmException e) {
        throw new java.io.IOException("Invalid Hmac");
    } catch (java.security.InvalidKeyException e) {
        throw new java.io.IOException("Invalid HMAC");
    }
    hmac.update(bufBlockIndex);
    hmac.update(blockSizeBuf);
    if (bufferPos > 0) {
        hmac.update(buffer, 0, bufferPos);
    }
    blockHmac = hmac.doFinal();
    baseStream.write(blockHmac);
    baseStream.write(blockSizeBuf);
    if (bufferPos > 0) {
        baseStream.write(buffer, 0, bufferPos);
    }
    blockIndex++;
    bufferPos = 0;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
