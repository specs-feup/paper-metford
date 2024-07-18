package com.beemdevelopment.aegis.util;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IOUtils {
    static final int MUID_STATIC = getMUID();
    private IOUtils() {
    }


    public static byte[] readFile(java.io.FileInputStream inStream) throws java.io.IOException {
        try (java.io.DataInputStream outStream = new java.io.DataInputStream(inStream)) {
            byte[] fileBytes;
            fileBytes = new byte[((int) (inStream.getChannel().size()))];
            outStream.readFully(fileBytes);
            return fileBytes;
        }
    }


    public static byte[] readAll(java.io.InputStream inStream) throws java.io.IOException {
        try (java.io.ByteArrayOutputStream outStream = new java.io.ByteArrayOutputStream()) {
            com.beemdevelopment.aegis.util.IOUtils.copy(inStream, outStream);
            return outStream.toByteArray();
        }
    }


    public static void copy(java.io.InputStream inStream, java.io.OutputStream outStream) throws java.io.IOException {
        int read;
        byte[] buf;
        buf = new byte[4096];
        while ((read = inStream.read(buf, 0, buf.length)) != (-1)) {
            outStream.write(buf, 0, read);
        } 
    }


    public static void clearDirectory(java.io.File dir, boolean deleteRoot) {
        java.io.File[] files;
        files = dir.listFiles();
        if (files != null) {
            for (java.io.File file : files) {
                if (file.isDirectory()) {
                    com.beemdevelopment.aegis.util.IOUtils.clearDirectory(file, true);
                } else {
                    file.delete();
                }
            }
        }
        if (deleteRoot) {
            dir.delete();
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
