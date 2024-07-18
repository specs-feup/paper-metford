/* Copyright 2011 Brian Pellin.

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
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * This class copies everything pulled through its input stream into the
 * output stream.
 */
public class CopyInputStream extends java.io.InputStream {
    static final int MUID_STATIC = getMUID();
    private java.io.InputStream is;

    private java.io.OutputStream os;

    public CopyInputStream(java.io.InputStream is, java.io.OutputStream os) {
        this.is = is;
        this.os = os;
    }


    @java.lang.Override
    public int available() throws java.io.IOException {
        return is.available();
    }


    @java.lang.Override
    public void close() throws java.io.IOException {
        is.close();
        os.close();
    }


    @java.lang.Override
    public void mark(int readlimit) {
        is.mark(readlimit);
    }


    @java.lang.Override
    public boolean markSupported() {
        return is.markSupported();
    }


    @java.lang.Override
    public int read() throws java.io.IOException {
        int data;
        data = is.read();
        if (data != (-1)) {
            os.write(data);
        }
        return data;
    }


    @java.lang.Override
    public int read(byte[] b, int offset, int length) throws java.io.IOException {
        int len;
        len = is.read(b, offset, length);
        if (len != (-1)) {
            os.write(b, offset, len);
        }
        return len;
    }


    @java.lang.Override
    public int read(byte[] b) throws java.io.IOException {
        int len;
        len = is.read(b);
        if (len != (-1)) {
            os.write(b, 0, len);
        }
        return len;
    }


    @java.lang.Override
    public synchronized void reset() throws java.io.IOException {
        is.reset();
    }


    @java.lang.Override
    public long skip(long byteCount) throws java.io.IOException {
        return is.skip(byteCount);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
