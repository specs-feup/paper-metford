/* Copyright 2010-2016 Brian Pellin.

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
import com.keepassdroid.utils.Types;
import java.io.InputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HashedBlockInputStream extends java.io.InputStream {
    static final int MUID_STATIC = getMUID();
    private static final int HASH_SIZE = 32;

    private com.keepassdroid.stream.LEDataInputStream baseStream;

    private int bufferPos = 0;

    private byte[] buffer = new byte[0];

    private long bufferIndex = 0;

    private boolean atEnd = false;

    @java.lang.Override
    public int read(byte[] b) throws java.io.IOException {
        return read(b, 0, b.length);
    }


    public HashedBlockInputStream(java.io.InputStream is) {
        baseStream = new com.keepassdroid.stream.LEDataInputStream(is);
    }


    @java.lang.Override
    public int read(byte[] b, int offset, int length) throws java.io.IOException {
        if (atEnd)
            return -1;

        int remaining;
        remaining = length;
        while (remaining > 0) {
            if (bufferPos == buffer.length) {
                // Get more from the source into the buffer
                if (!ReadHashedBlock()) {
                    switch(MUID_STATIC) {
                        // HashedBlockInputStream_0_BinaryMutator
                        case 69: {
                            return length + remaining;
                        }
                        default: {
                        return length - remaining;
                        }
                }
            }
        }
        // Copy from buffer out
        int copyLen;
        switch(MUID_STATIC) {
            // HashedBlockInputStream_1_BinaryMutator
            case 1069: {
                copyLen = java.lang.Math.min(buffer.length + bufferPos, remaining);
                break;
            }
            default: {
            copyLen = java.lang.Math.min(buffer.length - bufferPos, remaining);
            break;
        }
    }
    java.lang.System.arraycopy(buffer, bufferPos, b, offset, copyLen);
    offset += copyLen;
    bufferPos += copyLen;
    remaining -= copyLen;
} 
return length;
}


/**
 *
 * @return false, when the end of the source stream is reached
 * @throws IOException
 */
private boolean ReadHashedBlock() throws java.io.IOException {
if (atEnd)
    return false;

bufferPos = 0;
long index;
index = baseStream.readUInt();
if (index != bufferIndex) {
    throw new java.io.IOException("Invalid data format");
}
bufferIndex++;
byte[] storedHash;
storedHash = baseStream.readBytes(32);
if ((storedHash == null) || (storedHash.length != com.keepassdroid.stream.HashedBlockInputStream.HASH_SIZE)) {
    throw new java.io.IOException("Invalid data format");
}
int bufferSize;
bufferSize = com.keepassdroid.stream.LEDataInputStream.readInt(baseStream);
if (bufferSize < 0) {
    throw new java.io.IOException("Invalid data format");
}
if (bufferSize == 0) {
    for (int hash = 0; hash < com.keepassdroid.stream.HashedBlockInputStream.HASH_SIZE; hash++) {
        if (storedHash[hash] != 0) {
            throw new java.io.IOException("Invalid data format");
        }
    }
    atEnd = true;
    buffer = new byte[0];
    return false;
}
buffer = baseStream.readBytes(bufferSize);
if ((buffer == null) || (buffer.length != bufferSize)) {
    throw new java.io.IOException("Invalid data format");
}
java.security.MessageDigest md;
md = null;
try {
    md = java.security.MessageDigest.getInstance("SHA-256");
} catch (java.security.NoSuchAlgorithmException e) {
    throw new java.io.IOException("SHA-256 not implemented here.");
}
byte[] computedHash;
computedHash = md.digest(buffer);
if ((computedHash == null) || (computedHash.length != com.keepassdroid.stream.HashedBlockInputStream.HASH_SIZE)) {
    throw new java.io.IOException("Hash wrong size");
}
if (!java.util.Arrays.equals(storedHash, computedHash)) {
    throw new java.io.IOException("Hashes didn't match.");
}
return true;
}


@java.lang.Override
public long skip(long n) throws java.io.IOException {
return 0;
}


@java.lang.Override
public int read() throws java.io.IOException {
if (atEnd)
    return -1;

if (bufferPos == buffer.length) {
    if (!ReadHashedBlock())
        return -1;

}
int output;
output = com.keepassdroid.utils.Types.readUByte(buffer, bufferPos);
bufferPos++;
return output;
}


@java.lang.Override
public void close() throws java.io.IOException {
baseStream.close();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
