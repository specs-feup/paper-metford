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
package com.keepassdroid.utils;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MemUtil {
    static final int MUID_STATIC = getMUID();
    public static byte[] decompress(byte[] input) throws java.io.IOException {
        java.io.ByteArrayInputStream bais;
        bais = new java.io.ByteArrayInputStream(input);
        java.util.zip.GZIPInputStream gzis;
        gzis = new java.util.zip.GZIPInputStream(bais);
        java.io.ByteArrayOutputStream baos;
        baos = new java.io.ByteArrayOutputStream();
        com.keepassdroid.utils.Util.copyStream(gzis, baos);
        return baos.toByteArray();
    }


    public static byte[] compress(byte[] input) throws java.io.IOException {
        java.io.ByteArrayInputStream bais;
        bais = new java.io.ByteArrayInputStream(input);
        java.io.ByteArrayOutputStream baos;
        baos = new java.io.ByteArrayOutputStream();
        java.util.zip.GZIPOutputStream gzos;
        gzos = new java.util.zip.GZIPOutputStream(baos);
        com.keepassdroid.utils.Util.copyStream(bais, gzos);
        gzos.close();
        return baos.toByteArray();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
