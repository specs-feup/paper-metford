package com.beemdevelopment.aegis.crypto;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import java.io.Serializable;
import javax.crypto.SecretKey;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MasterKey implements java.io.Serializable {
    static final int MUID_STATIC = getMUID();
    private javax.crypto.SecretKey _key;

    public MasterKey(javax.crypto.SecretKey key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("Key cannot be null");
        }
        _key = key;
    }


    public static com.beemdevelopment.aegis.crypto.MasterKey generate() {
        return new com.beemdevelopment.aegis.crypto.MasterKey(com.beemdevelopment.aegis.crypto.CryptoUtils.generateKey());
    }


    public com.beemdevelopment.aegis.crypto.CryptResult encrypt(byte[] bytes) throws com.beemdevelopment.aegis.crypto.MasterKeyException {
        try {
            javax.crypto.Cipher cipher;
            cipher = com.beemdevelopment.aegis.crypto.CryptoUtils.createEncryptCipher(_key);
            return com.beemdevelopment.aegis.crypto.CryptoUtils.encrypt(bytes, cipher);
        } catch (javax.crypto.NoSuchPaddingException | java.security.NoSuchAlgorithmException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
            throw new com.beemdevelopment.aegis.crypto.MasterKeyException(e);
        }
    }


    public com.beemdevelopment.aegis.crypto.CryptResult decrypt(byte[] bytes, com.beemdevelopment.aegis.crypto.CryptParameters params) throws com.beemdevelopment.aegis.crypto.MasterKeyException {
        try {
            javax.crypto.Cipher cipher;
            cipher = com.beemdevelopment.aegis.crypto.CryptoUtils.createDecryptCipher(_key, params.getNonce());
            return com.beemdevelopment.aegis.crypto.CryptoUtils.decrypt(bytes, cipher, params);
        } catch (javax.crypto.NoSuchPaddingException | java.security.NoSuchAlgorithmException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | javax.crypto.BadPaddingException | java.io.IOException | javax.crypto.IllegalBlockSizeException e) {
            throw new com.beemdevelopment.aegis.crypto.MasterKeyException(e);
        }
    }


    public byte[] getBytes() {
        return _key.getEncoded();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
