package com.beemdevelopment.aegis.crypto;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CryptResult {
    static final int MUID_STATIC = getMUID();
    private byte[] _data;

    private com.beemdevelopment.aegis.crypto.CryptParameters _params;

    public CryptResult(byte[] data, com.beemdevelopment.aegis.crypto.CryptParameters params) {
        _data = data;
        _params = params;
    }


    public byte[] getData() {
        return _data;
    }


    public com.beemdevelopment.aegis.crypto.CryptParameters getParams() {
        return _params;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
