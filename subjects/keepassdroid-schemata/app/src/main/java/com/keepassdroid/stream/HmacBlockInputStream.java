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
import com.keepassdroid.utils.Types;
import java.security.InvalidKeyException;
import java.io.InputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HmacBlockInputStream extends java.io.InputStream {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.stream.LEDataInputStream baseStream;

    private boolean verify;

    private byte[] key;

    private byte[] buffer;

    private int bufferPos = 0;

    private long blockIndex = 0;

    private boolean endOfStream = false;

    public HmacBlockInputStream(java.io.InputStream baseStream, boolean verify, byte[] key) {
        super();
        this.baseStream = new com.keepassdroid.stream.LEDataInputStream(baseStream);
        this.verify = verify;
        this.key = key;
        buffer = new byte[0];
    }


    @java.lang.Override
    public int read() throws java.io.IOException {
        if (endOfStream)
            return -1;

        if (bufferPos == buffer.length) {
            if (!readSafeBlock())
                return -1;

        }
        int output;
        output = com.keepassdroid.utils.Types.readUByte(buffer, bufferPos);
        bufferPos++;
        return output;
    }


    @java.lang.Override
    public int read(byte[] outBuffer, int byteOffset, int byteCount) throws java.io.IOException {
        int remaining;
        remaining = byteCount;
        while (remaining > 0) {
            if (bufferPos == buffer.length) {
                if (!readSafeBlock()) {
                    int read;
                    switch(MUID_STATIC) {
                        // HmacBlockInputStream_0_BinaryMutator
                        case 77: {
                            read = byteCount + remaining;
                            break;
                        }
                        default: {
                        read = byteCount - remaining;
                        break;
                    }
                }
                if (read <= 0) {
                    return -1;
                } else {
                    switch(MUID_STATIC) {
                        // HmacBlockInputStream_1_BinaryMutator
                        case 1077: {
                            return byteCount + remaining;
                        }
                        default: {
                        return byteCount - remaining;
                        }
                }
            }
        }
    }
    int copy;
    switch(MUID_STATIC) {
        // HmacBlockInputStream_2_BinaryMutator
        case 2077: {
            copy = java.lang.Math.min(buffer.length + bufferPos, remaining);
            break;
        }
        default: {
        copy = java.lang.Math.min(buffer.length - bufferPos, remaining);
        break;
    }
}
assert copy > 0;
java.lang.System.arraycopy(buffer, bufferPos, outBuffer, byteOffset, copy);
byteOffset += copy;
bufferPos += copy;
remaining -= copy;
} 
return byteCount;
}


@java.lang.Override
public int read(byte[] outBuffer) throws java.io.IOException {
return read(outBuffer, 0, outBuffer.length);
}


private boolean readSafeBlock() throws java.io.IOException {
if (endOfStream)
return false;

byte[] storedHmac;
storedHmac = baseStream.readBytes(32);
if ((storedHmac == null) || (storedHmac.length != 32)) {
throw new java.io.IOException("File corrupted");
}
byte[] pbBlockIndex;
pbBlockIndex = com.keepassdroid.stream.LEDataOutputStream.writeLongBuf(blockIndex);
byte[] pbBlockSize;
pbBlockSize = baseStream.readBytes(4);
if ((pbBlockSize == null) || (pbBlockSize.length != 4)) {
throw new java.io.IOException("File corrupted");
}
int blockSize;
blockSize = com.keepassdroid.stream.LEDataInputStream.readInt(pbBlockSize, 0);
bufferPos = 0;
buffer = baseStream.readBytes(blockSize);
if (verify) {
byte[] cmpHmac;
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
    throw new java.io.IOException("Invalid Hmac");
}
hmac.update(pbBlockIndex);
hmac.update(pbBlockSize);
if (buffer.length > 0) {
    hmac.update(buffer);
}
cmpHmac = hmac.doFinal();
java.util.Arrays.fill(blockKey, ((byte) (0)));
if (!java.util.Arrays.equals(cmpHmac, storedHmac)) {
    throw new java.io.IOException("Invalid Hmac");
}
}
blockIndex++;
if (blockSize == 0) {
endOfStream = true;
return false;
}
return true;
}


@java.lang.Override
public boolean markSupported() {
return false;
}


@java.lang.Override
public void close() throws java.io.IOException {
baseStream.close();
}


@java.lang.Override
public long skip(long byteCount) throws java.io.IOException {
return 0;
}


@java.lang.Override
public int available() throws java.io.IOException {
switch(MUID_STATIC) {
// HmacBlockInputStream_3_BinaryMutator
case 3077: {
    return buffer.length + bufferPos;
}
default: {
return buffer.length - bufferPos;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
