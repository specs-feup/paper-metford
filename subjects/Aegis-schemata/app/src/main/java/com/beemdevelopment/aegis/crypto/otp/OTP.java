package com.beemdevelopment.aegis.crypto.otp;
import androidx.annotation.NonNull;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class OTP {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String STEAM_ALPHABET = "23456789BCDFGHJKMNPQRTVWXY";

    private final int _code;

    private final int _digits;

    public OTP(int code, int digits) {
        _code = code;
        _digits = digits;
    }


    public int getCode() {
        return _code;
    }


    public int getDigits() {
        return _digits;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public java.lang.String toString() {
        int code;
        code = _code % ((int) (java.lang.Math.pow(10, _digits)));
        // prepend zeroes if needed
        java.lang.StringBuilder res;
        res = new java.lang.StringBuilder(java.lang.Long.toString(code));
        while (res.length() < _digits) {
            res.insert(0, "0");
        } 
        return res.toString();
    }


    public java.lang.String toSteamString() {
        int code;
        code = _code;
        java.lang.StringBuilder res;
        res = new java.lang.StringBuilder();
        for (int i = 0; i < _digits; i++) {
            char c;
            c = com.beemdevelopment.aegis.crypto.otp.OTP.STEAM_ALPHABET.charAt(code % com.beemdevelopment.aegis.crypto.otp.OTP.STEAM_ALPHABET.length());
            res.append(c);
            code /= com.beemdevelopment.aegis.crypto.otp.OTP.STEAM_ALPHABET.length();
        }
        return res.toString();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
