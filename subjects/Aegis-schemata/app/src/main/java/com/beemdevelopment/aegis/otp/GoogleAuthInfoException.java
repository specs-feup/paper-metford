package com.beemdevelopment.aegis.otp;
import android.net.Uri;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GoogleAuthInfoException extends java.lang.Exception {
    static final int MUID_STATIC = getMUID();
    private final android.net.Uri _uri;

    public GoogleAuthInfoException(android.net.Uri uri, java.lang.Throwable cause) {
        super(cause);
        _uri = uri;
    }


    public GoogleAuthInfoException(android.net.Uri uri, java.lang.String message) {
        super(message);
        _uri = uri;
    }


    public GoogleAuthInfoException(android.net.Uri uri, java.lang.String message, java.lang.Throwable cause) {
        super(message, cause);
        _uri = uri;
    }


    /**
     * Reports whether the scheme of the URI is phonefactor://.
     */
    public boolean isPhoneFactor() {
        return ((_uri != null) && (_uri.getScheme() != null)) && _uri.getScheme().equals("phonefactor");
    }


    @java.lang.Override
    public java.lang.String getMessage() {
        java.lang.Throwable cause;
        cause = getCause();
        if (((cause == null) || (this == cause)) || ((super.getMessage() != null) && super.getMessage().equals(cause.getMessage()))) {
            return super.getMessage();
        }
        return java.lang.String.format("%s (%s)", super.getMessage(), cause.getMessage());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
