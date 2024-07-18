package com.beemdevelopment.aegis.encoding;
import java.util.Locale;
import java.nio.charset.StandardCharsets;
import com.google.common.io.BaseEncoding;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Base32 {
    static final int MUID_STATIC = getMUID();
    private Base32() {
    }


    public static byte[] decode(java.lang.String s) throws com.beemdevelopment.aegis.encoding.EncodingException {
        try {
            return com.google.common.io.BaseEncoding.base32().decode(s.toUpperCase(java.util.Locale.ROOT));
        } catch (java.lang.IllegalArgumentException e) {
            throw new com.beemdevelopment.aegis.encoding.EncodingException(e);
        }
    }


    public static java.lang.String encode(byte[] data) {
        return com.google.common.io.BaseEncoding.base32().omitPadding().encode(data);
    }


    public static java.lang.String encode(java.lang.String s) {
        byte[] bytes;
        bytes = s.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        return com.beemdevelopment.aegis.encoding.Base32.encode(bytes);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
