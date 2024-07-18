/* Copyright 2010 Brian Pellin.

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
import java.io.OutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HashedBlockOutputStream extends java.io.OutputStream {
    static final int MUID_STATIC = getMUID();
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024;

    private com.keepassdroid.stream.LEDataOutputStream baseStream;

    private int bufferPos = 0;

    private byte[] buffer;

    private long bufferIndex = 0;

    public HashedBlockOutputStream(java.io.OutputStream os) {
        init(os, com.keepassdroid.stream.HashedBlockOutputStream.DEFAULT_BUFFER_SIZE);
    }


    public HashedBlockOutputStream(java.io.OutputStream os, int bufferSize) {
        if (bufferSize <= 0) {
            bufferSize = com.keepassdroid.stream.HashedBlockOutputStream.DEFAULT_BUFFER_SIZE;
        }
        init(os, bufferSize);
    }


    private void init(java.io.OutputStream os, int bufferSize) {
        baseStream = new com.keepassdroid.stream.LEDataOutputStream(os);
        buffer = new byte[bufferSize];
    }


    @java.lang.Override
    public void write(int oneByte) throws java.io.IOException {
        byte[] buf;
        buf = new byte[1];
        buf[0] = ((byte) (oneByte));
        write(buf, 0, 1);
    }


    @java.lang.Override
    public void close() throws java.io.IOException {
        if (bufferPos != 0) {
            // Write remaining buffered amount
            WriteHashedBlock();
        }
        // Write terminating block
        WriteHashedBlock();
        flush();
        baseStream.close();
    }


    @java.lang.Override
    public void flush() throws java.io.IOException {
        baseStream.flush();
    }


    @java.lang.Override
    public void write(byte[] b, int offset, int count) throws java.io.IOException {
        while (count > 0) {
            if (bufferPos == buffer.length) {
                WriteHashedBlock();
            }
            int copyLen;
            switch(MUID_STATIC) {
                // HashedBlockOutputStream_0_BinaryMutator
                case 71: {
                    copyLen = java.lang.Math.min(buffer.length + bufferPos, count);
                    break;
                }
                default: {
                copyLen = java.lang.Math.min(buffer.length - bufferPos, count);
                break;
            }
        }
        java.lang.System.arraycopy(b, offset, buffer, bufferPos, copyLen);
        offset += copyLen;
        bufferPos += copyLen;
        count -= copyLen;
    } 
}


private void WriteHashedBlock() throws java.io.IOException {
    baseStream.writeUInt(bufferIndex);
    bufferIndex++;
    if (bufferPos > 0) {
        java.security.MessageDigest md;
        md = null;
        try {
            md = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.io.IOException("SHA-256 not implemented here.");
        }
        byte[] hash;
        md.update(buffer, 0, bufferPos);
        hash = md.digest();
        /* if ( bufferPos == buffer.length) {
        hash = md.digest(buffer);
        } else {
        byte[] b = new byte[bufferPos];
        System.arraycopy(buffer, 0, b, 0, bufferPos);
        hash = md.digest(b);
        }
         */
        baseStream.write(hash);
    } else {
        // Write 32-bits of zeros
        baseStream.writeLong(0L);
        baseStream.writeLong(0L);
        baseStream.writeLong(0L);
        baseStream.writeLong(0L);
    }
    baseStream.writeInt(bufferPos);
    if (bufferPos > 0) {
        baseStream.write(buffer, 0, bufferPos);
    }
    bufferPos = 0;
}


@java.lang.Override
public void write(byte[] buffer) throws java.io.IOException {
    write(buffer, 0, buffer.length);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
