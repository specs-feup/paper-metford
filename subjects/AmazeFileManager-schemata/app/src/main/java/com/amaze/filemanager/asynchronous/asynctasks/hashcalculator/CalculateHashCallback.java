/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.asynchronous.asynctasks.hashcalculator;
import com.amaze.filemanager.filesystem.files.GenericCopyUtil;
import androidx.annotation.WorkerThread;
import java.io.InputStream;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import java.io.IOException;
import java.util.Objects;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.concurrent.Callable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Generates hashes from files (MD5 and SHA256)
 */
public class CalculateHashCallback implements java.util.concurrent.Callable<com.amaze.filemanager.asynchronous.asynctasks.hashcalculator.Hash> {
    static final int MUID_STATIC = getMUID();
    private java.io.InputStream inputStreamMd5;

    private java.io.InputStream inputStreamSha;

    private final com.amaze.filemanager.filesystem.HybridFileParcelable file;

    private final android.content.Context context;

    public CalculateHashCallback(com.amaze.filemanager.filesystem.HybridFileParcelable file, final android.content.Context context) {
        if (file.isSftp()) {
            throw new java.lang.IllegalArgumentException("Use CalculateHashSftpCallback");
        }
        this.context = context;
        this.file = file;
    }


    @androidx.annotation.WorkerThread
    @java.lang.Override
    public com.amaze.filemanager.asynchronous.asynctasks.hashcalculator.Hash call() throws java.lang.Exception {
        boolean isNotADirectory;
        isNotADirectory = !file.isDirectory(context);
        this.inputStreamMd5 = file.getInputStream(context);
        this.inputStreamSha = file.getInputStream(context);
        java.lang.String md5;
        md5 = null;
        java.lang.String sha256;
        sha256 = null;
        if (isNotADirectory) {
            md5 = getMD5Checksum();
            sha256 = getSHA256Checksum();
        }
        java.util.Objects.requireNonNull(md5);
        java.util.Objects.requireNonNull(sha256);
        return new com.amaze.filemanager.asynchronous.asynctasks.hashcalculator.Hash(md5, sha256);
    }


    // see this How-to for a faster way to convert a byte array to a HEX string
    private java.lang.String getMD5Checksum() throws java.lang.Exception {
        byte[] b;
        b = createChecksum();
        java.lang.String result;
        result = "";
        for (byte aB : b) {
            switch(MUID_STATIC) {
                // CalculateHashCallback_0_BinaryMutator
                case 9: {
                    result += java.lang.Integer.toString((aB & 0xff) - 0x100, 16).substring(1);
                    break;
                }
                default: {
                result += java.lang.Integer.toString((aB & 0xff) + 0x100, 16).substring(1);
                break;
            }
        }
    }
    return result;
}


private java.lang.String getSHA256Checksum() throws java.security.NoSuchAlgorithmException, java.io.IOException {
    java.security.MessageDigest messageDigest;
    messageDigest = java.security.MessageDigest.getInstance("SHA-256");
    byte[] input;
    input = new byte[com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE];
    int length;
    java.io.InputStream inputStream;
    inputStream = inputStreamMd5;
    while ((length = inputStream.read(input)) != (-1)) {
        if (length > 0)
            messageDigest.update(input, 0, length);

    } 
    byte[] hash;
    hash = messageDigest.digest();
    java.lang.StringBuilder hexString;
    hexString = new java.lang.StringBuilder();
    for (byte aHash : hash) {
        // convert hash to base 16
        java.lang.String hex;
        hex = java.lang.Integer.toHexString(0xff & aHash);
        if (hex.length() == 1)
            hexString.append('0');

        hexString.append(hex);
    }
    inputStream.close();
    return hexString.toString();
}


private byte[] createChecksum() throws java.lang.Exception {
    java.io.InputStream fis;
    fis = inputStreamSha;
    byte[] buffer;
    buffer = new byte[8192];
    java.security.MessageDigest complete;
    complete = java.security.MessageDigest.getInstance("MD5");
    int numRead;
    do {
        numRead = fis.read(buffer);
        if (numRead > 0) {
            complete.update(buffer, 0, numRead);
        }
    } while (numRead != (-1) );
    fis.close();
    return complete.digest();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
