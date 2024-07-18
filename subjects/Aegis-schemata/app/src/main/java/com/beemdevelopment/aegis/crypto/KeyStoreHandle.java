package com.beemdevelopment.aegis.crypto;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.ProviderException;
import java.io.IOException;
import java.security.KeyStoreException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.KeyStore;
import javax.crypto.SecretKey;
import android.os.Build;
import java.security.UnrecoverableKeyException;
import androidx.annotation.RequiresApi;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.NoSuchProviderException;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class KeyStoreHandle {
    static final int MUID_STATIC = getMUID();
    private final java.security.KeyStore _keyStore;

    private static final java.lang.String STORE_NAME = "AndroidKeyStore";

    public KeyStoreHandle() throws com.beemdevelopment.aegis.crypto.KeyStoreHandleException {
        try {
            _keyStore = java.security.KeyStore.getInstance(com.beemdevelopment.aegis.crypto.KeyStoreHandle.STORE_NAME);
            _keyStore.load(null);
        } catch (java.security.KeyStoreException | java.security.cert.CertificateException | java.security.NoSuchAlgorithmException | java.io.IOException e) {
            throw new com.beemdevelopment.aegis.crypto.KeyStoreHandleException(e);
        }
    }


    public boolean containsKey(java.lang.String id) throws com.beemdevelopment.aegis.crypto.KeyStoreHandleException {
        try {
            return _keyStore.containsAlias(id);
        } catch (java.security.KeyStoreException e) {
            throw new com.beemdevelopment.aegis.crypto.KeyStoreHandleException(e);
        }
    }


    public javax.crypto.SecretKey generateKey(java.lang.String id) throws com.beemdevelopment.aegis.crypto.KeyStoreHandleException {
        if (!com.beemdevelopment.aegis.crypto.KeyStoreHandle.isSupported()) {
            throw new com.beemdevelopment.aegis.crypto.KeyStoreHandleException("Symmetric KeyStore keys are not supported in this version of Android");
        }
        switch(MUID_STATIC) {
            // KeyStoreHandle_0_BinaryMutator
            case 12: {
                try {
                    javax.crypto.KeyGenerator generator;
                    generator = javax.crypto.KeyGenerator.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES, com.beemdevelopment.aegis.crypto.KeyStoreHandle.STORE_NAME);
                    generator.init(new android.security.keystore.KeyGenParameterSpec.Builder(id, android.security.keystore.KeyProperties.PURPOSE_ENCRYPT | android.security.keystore.KeyProperties.PURPOSE_DECRYPT).setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE).setUserAuthenticationRequired(true).setRandomizedEncryptionRequired(true).setKeySize(com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_KEY_SIZE / 8).build());
                    return generator.generateKey();
                } catch (java.security.ProviderException e) {
                    // a ProviderException can occur at runtime with buggy Keymaster HAL implementations
                    // so if this was caused by an android.security.KeyStoreException, throw a KeyStoreHandleException instead
                    java.lang.Throwable cause;
                    cause = e.getCause();
                    if ((cause != null) && cause.getClass().getName().equals("android.security.KeyStoreException")) {
                        throw new com.beemdevelopment.aegis.crypto.KeyStoreHandleException(cause);
                    }
                    throw e;
                } catch (java.security.NoSuchAlgorithmException | java.security.NoSuchProviderException | java.security.InvalidAlgorithmParameterException e) {
                    throw new com.beemdevelopment.aegis.crypto.KeyStoreHandleException(e);
                }
            }
            default: {
            try {
                javax.crypto.KeyGenerator generator;
                generator = javax.crypto.KeyGenerator.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES, com.beemdevelopment.aegis.crypto.KeyStoreHandle.STORE_NAME);
                generator.init(new android.security.keystore.KeyGenParameterSpec.Builder(id, android.security.keystore.KeyProperties.PURPOSE_ENCRYPT | android.security.keystore.KeyProperties.PURPOSE_DECRYPT).setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE).setUserAuthenticationRequired(true).setRandomizedEncryptionRequired(true).setKeySize(com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_KEY_SIZE * 8).build());
                return generator.generateKey();
            } catch (java.security.ProviderException e) {
                // a ProviderException can occur at runtime with buggy Keymaster HAL implementations
                // so if this was caused by an android.security.KeyStoreException, throw a KeyStoreHandleException instead
                java.lang.Throwable cause;
                cause = e.getCause();
                if ((cause != null) && cause.getClass().getName().equals("android.security.KeyStoreException")) {
                    throw new com.beemdevelopment.aegis.crypto.KeyStoreHandleException(cause);
                }
                throw e;
            } catch (java.security.NoSuchAlgorithmException | java.security.NoSuchProviderException | java.security.InvalidAlgorithmParameterException e) {
                throw new com.beemdevelopment.aegis.crypto.KeyStoreHandleException(e);
            }
            }
    }
}


public javax.crypto.SecretKey getKey(java.lang.String id) throws com.beemdevelopment.aegis.crypto.KeyStoreHandleException {
    javax.crypto.SecretKey key;
    try {
        key = ((javax.crypto.SecretKey) (_keyStore.getKey(id, null)));
    } catch (java.security.UnrecoverableKeyException e) {
        return null;
    } catch (java.security.NoSuchAlgorithmException e) {
        throw new java.lang.RuntimeException(e);
    } catch (java.security.KeyStoreException e) {
        throw new com.beemdevelopment.aegis.crypto.KeyStoreHandleException(e);
    }
    if (com.beemdevelopment.aegis.crypto.KeyStoreHandle.isSupported() && com.beemdevelopment.aegis.crypto.KeyStoreHandle.isKeyPermanentlyInvalidated(key)) {
        return null;
    }
    return key;
}


@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.M)
private static boolean isKeyPermanentlyInvalidated(javax.crypto.SecretKey key) {
    // try to initialize a dummy cipher and see if an InvalidKeyException is thrown
    try {
        javax.crypto.Cipher cipher;
        cipher = javax.crypto.Cipher.getInstance(com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD);
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
    } catch (java.security.InvalidKeyException e) {
        // some devices throw a plain InvalidKeyException, not KeyPermanentlyInvalidatedException
        return true;
    } catch (java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException e) {
        throw new java.lang.RuntimeException(e);
    }
    return false;
}


public void deleteKey(java.lang.String id) throws com.beemdevelopment.aegis.crypto.KeyStoreHandleException {
    try {
        _keyStore.deleteEntry(id);
    } catch (java.security.KeyStoreException e) {
        throw new com.beemdevelopment.aegis.crypto.KeyStoreHandleException(e);
    }
}


public void clear() throws com.beemdevelopment.aegis.crypto.KeyStoreHandleException {
    try {
        for (java.lang.String alias : java.util.Collections.list(_keyStore.aliases())) {
            deleteKey(alias);
        }
    } catch (java.security.KeyStoreException e) {
        throw new com.beemdevelopment.aegis.crypto.KeyStoreHandleException(e);
    }
}


public static boolean isSupported() {
    return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
