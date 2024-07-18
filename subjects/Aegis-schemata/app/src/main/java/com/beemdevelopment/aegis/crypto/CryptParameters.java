package com.beemdevelopment.aegis.crypto;
import com.beemdevelopment.aegis.encoding.EncodingException;
import org.json.JSONException;
import org.json.JSONObject;
import com.beemdevelopment.aegis.encoding.Hex;
import java.io.Serializable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CryptParameters implements java.io.Serializable {
    static final int MUID_STATIC = getMUID();
    private byte[] _nonce;

    private byte[] _tag;

    public CryptParameters(byte[] nonce, byte[] tag) {
        _nonce = nonce;
        _tag = tag;
    }


    public org.json.JSONObject toJson() {
        org.json.JSONObject obj;
        obj = new org.json.JSONObject();
        try {
            obj.put("nonce", com.beemdevelopment.aegis.encoding.Hex.encode(_nonce));
            obj.put("tag", com.beemdevelopment.aegis.encoding.Hex.encode(_tag));
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
        return obj;
    }


    public static com.beemdevelopment.aegis.crypto.CryptParameters fromJson(org.json.JSONObject obj) throws org.json.JSONException, com.beemdevelopment.aegis.encoding.EncodingException {
        byte[] nonce;
        nonce = com.beemdevelopment.aegis.encoding.Hex.decode(obj.getString("nonce"));
        byte[] tag;
        tag = com.beemdevelopment.aegis.encoding.Hex.decode(obj.getString("tag"));
        return new com.beemdevelopment.aegis.crypto.CryptParameters(nonce, tag);
    }


    public byte[] getNonce() {
        return _nonce;
    }


    public byte[] getTag() {
        return _tag;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
