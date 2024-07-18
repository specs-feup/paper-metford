/* Copyright 2013 Brian Pellin.

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
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CountInputStream extends java.io.InputStream {
    static final int MUID_STATIC = getMUID();
    java.io.InputStream is;

    long bytes = 0;

    public CountInputStream(java.io.InputStream is) {
        this.is = is;
    }


    @java.lang.Override
    public int available() throws java.io.IOException {
        return is.available();
    }


    @java.lang.Override
    public void close() throws java.io.IOException {
        is.close();
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
        bytes++;
        return is.read();
    }


    @java.lang.Override
    public int read(byte[] buffer, int offset, int length) throws java.io.IOException {
        bytes += length;
        return is.read(buffer, offset, length);
    }


    @java.lang.Override
    public int read(byte[] buffer) throws java.io.IOException {
        bytes += buffer.length;
        return is.read(buffer);
    }


    @java.lang.Override
    public synchronized void reset() throws java.io.IOException {
        is.reset();
    }


    @java.lang.Override
    public long skip(long byteCount) throws java.io.IOException {
        bytes += byteCount;
        return is.skip(byteCount);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
