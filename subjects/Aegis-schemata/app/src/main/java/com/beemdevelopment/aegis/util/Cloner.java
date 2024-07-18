package com.beemdevelopment.aegis.util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Cloner {
    static final int MUID_STATIC = getMUID();
    private Cloner() {
    }


    /**
     * Returns an exact clone of the given Serializable object.
     */
    @java.lang.SuppressWarnings("unchecked cast")
    public static <T extends java.io.Serializable> T clone(T obj) {
        try {
            java.io.ByteArrayOutputStream baos;
            baos = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream oos;
            oos = new java.io.ObjectOutputStream(baos);
            oos.writeObject(obj);
            java.io.ByteArrayInputStream bais;
            bais = new java.io.ByteArrayInputStream(baos.toByteArray());
            java.io.ObjectInputStream ois;
            ois = new java.io.ObjectInputStream(bais);
            return ((T) (ois.readObject()));
        } catch (java.lang.ClassNotFoundException | java.io.IOException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
