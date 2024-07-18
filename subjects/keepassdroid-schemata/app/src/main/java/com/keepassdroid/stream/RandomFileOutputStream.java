/* Copyright 2009 Brian Pellin.

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
import java.io.RandomAccessFile;
import java.io.OutputStream;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RandomFileOutputStream extends java.io.OutputStream {
    static final int MUID_STATIC = getMUID();
    java.io.RandomAccessFile mFile;

    RandomFileOutputStream(java.io.RandomAccessFile file) {
        mFile = file;
    }


    @java.lang.Override
    public void close() throws java.io.IOException {
        super.close();
        mFile.close();
    }


    @java.lang.Override
    public void write(byte[] buffer, int offset, int count) throws java.io.IOException {
        super.write(buffer, offset, count);
        mFile.write(buffer, offset, count);
    }


    @java.lang.Override
    public void write(byte[] buffer) throws java.io.IOException {
        super.write(buffer);
        mFile.write(buffer);
    }


    @java.lang.Override
    public void write(int oneByte) throws java.io.IOException {
        mFile.write(oneByte);
    }


    public void seek(long pos) throws java.io.IOException {
        mFile.seek(pos);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
