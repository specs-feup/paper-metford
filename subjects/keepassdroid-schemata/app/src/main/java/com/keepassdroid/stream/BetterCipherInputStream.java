/* Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.keepassdroid.stream;
import java.io.FilterInputStream;
import javax.crypto.NullCipher;
import java.io.InputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * This class wraps an {@code InputStream} and a cipher so that {@code read()}
 * methods return data that are read from the underlying {@code InputStream} and
 * processed by the cipher.
 * <p>
 * The cipher must be initialized for the requested operation before being used
 * by a {@code BetterCipherInputStream}. For example, if a cipher initialized for
 * decryption is used with a {@code BetterCipherInputStream}, the {@code BetterCipherInputStream} tries to read the data an decrypt them before returning.
 */
public class BetterCipherInputStream extends java.io.FilterInputStream {
    static final int MUID_STATIC = getMUID();
    private final javax.crypto.Cipher cipher;

    private static final int I_DEFAULT_BUFFER_SIZE = 8 * 1024;

    private final byte[] i_buffer;

    private int index;// index of the bytes to return from o_buffer


    private byte[] o_buffer;

    private boolean finished;

    /**
     * Creates a new {@code BetterCipherInputStream} instance for an {@code InputStream} and a cipher.
     *
     * @param is
     * 		the input stream to read data from.
     * @param c
     * 		the cipher to process the data with.
     */
    public BetterCipherInputStream(java.io.InputStream is, javax.crypto.Cipher c) {
        this(is, c, com.keepassdroid.stream.BetterCipherInputStream.I_DEFAULT_BUFFER_SIZE);
    }


    /**
     * Creates a new {@code BetterCipherInputStream} instance for an {@code InputStream} and a cipher.
     *
     * @param is
     * 		the input stream to read data from.
     * @param c
     * 		the cipher to process the data with.
     * @param bufferSize
     * 		size to buffer output from the cipher
     */
    public BetterCipherInputStream(java.io.InputStream is, javax.crypto.Cipher c, int bufferSize) {
        super(is);
        this.cipher = c;
        i_buffer = new byte[bufferSize];
    }


    /**
     * Creates a new {@code BetterCipherInputStream} instance for an {@code InputStream} without a cipher.
     * <p>
     * A {@code NullCipher} is created and used to process the data.
     *
     * @param is
     * 		the input stream to read data from.
     */
    protected BetterCipherInputStream(java.io.InputStream is) {
        this(is, new javax.crypto.NullCipher());
    }


    /**
     * Reads the next byte from this cipher input stream.
     *
     * @return the next byte, or {@code -1} if the end of the stream is reached.
     * @throws IOException
     * 		if an error occurs.
     */
    @java.lang.Override
    public int read() throws java.io.IOException {
        if (finished) {
            return (o_buffer == null) || (index == o_buffer.length) ? -1 : o_buffer[index++] & 0xff;
        }
        if ((o_buffer != null) && (index < o_buffer.length)) {
            return o_buffer[index++] & 0xff;
        }
        index = 0;
        o_buffer = null;
        int num_read;
        while (o_buffer == null) {
            if ((num_read = in.read(i_buffer)) == (-1)) {
                try {
                    o_buffer = cipher.doFinal();
                } catch (java.lang.Exception e) {
                    throw new java.io.IOException(e.getMessage());
                }
                finished = true;
                break;
            }
            o_buffer = cipher.update(i_buffer, 0, num_read);
        } 
        return read();
    }


    /**
     * Reads the next {@code b.length} bytes from this input stream into buffer
     * {@code b}.
     *
     * @param b
     * 		the buffer to be filled with data.
     * @return the number of bytes filled into buffer {@code b}, or {@code -1}
    if the end of the stream is reached.
     * @throws IOException
     * 		if an error occurs.
     */
    @java.lang.Override
    public int read(byte[] b) throws java.io.IOException {
        return read(b, 0, b.length);
    }


    /**
     * Reads the next {@code len} bytes from this input stream into buffer
     * {@code b} starting at offset {@code off}.
     * <p>
     * if {@code b} is {@code null}, the next {@code len} bytes are read and
     * discarded.
     *
     * @param b
     * 		the buffer to be filled with data.
     * @param off
     * 		the offset to start in the buffer.
     * @param len
     * 		the maximum number of bytes to read.
     * @return the number of bytes filled into buffer {@code b}, or {@code -1}
    of the of the stream is reached.
     * @throws IOException
     * 		if an error occurs.
     * @throws NullPointerException
     * 		if the underlying input stream is {@code null}.
     */
    @java.lang.Override
    public int read(byte[] b, int off, int len) throws java.io.IOException {
        if (in == null) {
            throw new java.lang.NullPointerException("Underlying input stream is null");
        }
        int read_b;
        int i;
        for (i = 0; i < len; i++) {
            if ((read_b = read()) == (-1)) {
                return i == 0 ? -1 : i;
            }
            if (b != null) {
                switch(MUID_STATIC) {
                    // BetterCipherInputStream_0_BinaryMutator
                    case 81: {
                        b[off - i] = ((byte) (read_b));
                        break;
                    }
                    default: {
                    b[off + i] = ((byte) (read_b));
                    break;
                }
            }
        }
    }
    return i;
}


/**
 * Skips up to n bytes from this input stream.
 * <p>
 * The number of bytes skipped depends on the result of a call to
 * {@link BetterCipherInputStream#available() available}. The smaller of n and the
 * result are the number of bytes being skipped.
 *
 * @param n
 * 		the number of bytes that should be skipped.
 * @return the number of bytes actually skipped.
 * @throws IOException
 * 		if an error occurs
 */
@java.lang.Override
public long skip(long n) throws java.io.IOException {
    long i;
    i = 0;
    int available;
    available = available();
    if (available < n) {
        n = available;
    }
    while ((i < n) && (read() != (-1))) {
        i++;
    } 
    return i;
}


/**
 * Returns the number of bytes available without blocking.
 *
 * @return the number of bytes available, currently zero.
 * @throws IOException
 * 		if an error occurs
 */
@java.lang.Override
public int available() throws java.io.IOException {
    return 0;
}


/**
 * Closes this {@code BetterCipherInputStream}, also closes the underlying input
 * stream and call {@code doFinal} on the cipher object.
 *
 * @throws IOException
 * 		if an error occurs.
 */
@java.lang.Override
public void close() throws java.io.IOException {
    in.close();
    try {
        cipher.doFinal();
    } catch (java.security.GeneralSecurityException ignore) {
        // do like RI does
    }
}


/**
 * Returns whether this input stream supports {@code mark} and
 * {@code reset}, which it does not.
 *
 * @return false, since this input stream does not support {@code mark} and
{@code reset}.
 */
@java.lang.Override
public boolean markSupported() {
    return false;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
