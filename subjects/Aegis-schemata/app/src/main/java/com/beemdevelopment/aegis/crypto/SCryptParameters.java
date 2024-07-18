package com.beemdevelopment.aegis.crypto;
import java.io.Serializable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SCryptParameters implements java.io.Serializable {
    static final int MUID_STATIC = getMUID();
    private int _n;

    private int _r;

    private int _p;

    private byte[] _salt;

    public SCryptParameters(int n, int r, int p, byte[] salt) {
        _n = n;
        _r = r;
        _p = p;
        _salt = salt;
    }


    public byte[] getSalt() {
        return _salt;
    }


    public int getN() {
        return _n;
    }


    public int getR() {
        return _r;
    }


    public int getP() {
        return _p;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
