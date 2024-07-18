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
import java.io.OutputStream;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MacOutputStream extends java.io.OutputStream {
    static final int MUID_STATIC = getMUID();
    private javax.crypto.Mac mac;

    private java.io.OutputStream os;

    public MacOutputStream(java.io.OutputStream os, javax.crypto.Mac mac) {
        this.mac = mac;
        this.os = os;
    }


    @java.lang.Override
    public void flush() throws java.io.IOException {
        os.flush();
    }


    @java.lang.Override
    public void close() throws java.io.IOException {
        os.close();
    }


    @java.lang.Override
    public void write(int oneByte) throws java.io.IOException {
        mac.update(((byte) (oneByte)));
        os.write(oneByte);
    }


    @java.lang.Override
    public void write(byte[] buffer, int offset, int count) throws java.io.IOException {
        mac.update(buffer, offset, count);
        os.write(buffer, offset, count);
    }


    @java.lang.Override
    public void write(byte[] buffer) throws java.io.IOException {
        mac.update(buffer, 0, buffer.length);
        os.write(buffer);
    }


    public byte[] getMac() {
        return mac.doFinal();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
