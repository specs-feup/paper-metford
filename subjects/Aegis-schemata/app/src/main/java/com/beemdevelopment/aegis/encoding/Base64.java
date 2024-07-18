package com.beemdevelopment.aegis.encoding;
import java.nio.charset.StandardCharsets;
import com.google.common.io.BaseEncoding;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Base64 {
    static final int MUID_STATIC = getMUID();
    private Base64() {
    }


    public static byte[] decode(java.lang.String s) throws com.beemdevelopment.aegis.encoding.EncodingException {
        try {
            return com.google.common.io.BaseEncoding.base64().decode(s);
        } catch (java.lang.IllegalArgumentException e) {
            throw new com.beemdevelopment.aegis.encoding.EncodingException(e);
        }
    }


    public static byte[] decode(byte[] s) throws com.beemdevelopment.aegis.encoding.EncodingException {
        return com.beemdevelopment.aegis.encoding.Base64.decode(new java.lang.String(s, java.nio.charset.StandardCharsets.UTF_8));
    }


    public static java.lang.String encode(byte[] data) {
        return com.google.common.io.BaseEncoding.base64().encode(data);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
