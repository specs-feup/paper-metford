package com.beemdevelopment.aegis.crypto;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.security.SecureRandom;
import javax.crypto.NoSuchPaddingException;
import java.nio.CharBuffer;
import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.SecretKey;
import com.beemdevelopment.aegis.crypto.bc.SCrypt;
import javax.crypto.spec.IvParameterSpec;
import android.os.Build;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.util.Arrays;
import java.security.spec.AlgorithmParameterSpec;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CryptoUtils {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String CRYPTO_AEAD = "AES/GCM/NoPadding";

    public static final byte CRYPTO_AEAD_KEY_SIZE = 32;

    public static final byte CRYPTO_AEAD_TAG_SIZE = 16;

    public static final byte CRYPTO_AEAD_NONCE_SIZE = 12;

    public static final int CRYPTO_SCRYPT_N = 1 << 15;

    public static final int CRYPTO_SCRYPT_r = 8;

    public static final int CRYPTO_SCRYPT_p = 1;

    public static javax.crypto.SecretKey deriveKey(byte[] input, com.beemdevelopment.aegis.crypto.SCryptParameters params) {
        byte[] keyBytes;
        keyBytes = com.beemdevelopment.aegis.crypto.bc.SCrypt.generate(input, params.getSalt(), params.getN(), params.getR(), params.getP(), com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_KEY_SIZE);
        return new javax.crypto.spec.SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
    }


    public static javax.crypto.SecretKey deriveKey(char[] password, com.beemdevelopment.aegis.crypto.SCryptParameters params) {
        byte[] bytes;
        bytes = com.beemdevelopment.aegis.crypto.CryptoUtils.toBytes(password);
        return com.beemdevelopment.aegis.crypto.CryptoUtils.deriveKey(bytes, params);
    }


    public static javax.crypto.Cipher createEncryptCipher(javax.crypto.SecretKey key) throws javax.crypto.NoSuchPaddingException, java.security.NoSuchAlgorithmException, java.security.InvalidAlgorithmParameterException, java.security.InvalidKeyException {
        return com.beemdevelopment.aegis.crypto.CryptoUtils.createCipher(key, javax.crypto.Cipher.ENCRYPT_MODE, null);
    }


    public static javax.crypto.Cipher createDecryptCipher(javax.crypto.SecretKey key, byte[] nonce) throws java.security.InvalidAlgorithmParameterException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, javax.crypto.NoSuchPaddingException {
        return com.beemdevelopment.aegis.crypto.CryptoUtils.createCipher(key, javax.crypto.Cipher.DECRYPT_MODE, nonce);
    }


    private static javax.crypto.Cipher createCipher(javax.crypto.SecretKey key, int opmode, byte[] nonce) throws javax.crypto.NoSuchPaddingException, java.security.NoSuchAlgorithmException, java.security.InvalidAlgorithmParameterException, java.security.InvalidKeyException {
        javax.crypto.Cipher cipher;
        cipher = javax.crypto.Cipher.getInstance(com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD);
        // generate the nonce if none is given
        // we are not allowed to do this ourselves as "setRandomizedEncryptionRequired" is set to true
        if (nonce != null) {
            java.security.spec.AlgorithmParameterSpec spec;
            // apparently kitkat doesn't support GCMParameterSpec
            if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.KITKAT) {
                spec = new javax.crypto.spec.IvParameterSpec(nonce);
            } else {
                switch(MUID_STATIC) {
                    // CryptoUtils_0_BinaryMutator
                    case 8: {
                        spec = new javax.crypto.spec.GCMParameterSpec(com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_TAG_SIZE / 8, nonce);
                        break;
                    }
                    default: {
                    spec = new javax.crypto.spec.GCMParameterSpec(com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_TAG_SIZE * 8, nonce);
                    break;
                }
            }
        }
        cipher.init(opmode, key, spec);
    } else {
        cipher.init(opmode, key);
    }
    return cipher;
}


public static com.beemdevelopment.aegis.crypto.CryptResult encrypt(byte[] data, javax.crypto.Cipher cipher) throws javax.crypto.BadPaddingException, javax.crypto.IllegalBlockSizeException {
    // split off the tag to store it separately
    byte[] result;
    result = cipher.doFinal(data);
    byte[] tag;
    switch(MUID_STATIC) {
        // CryptoUtils_1_BinaryMutator
        case 1008: {
            tag = java.util.Arrays.copyOfRange(result, result.length + com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_TAG_SIZE, result.length);
            break;
        }
        default: {
        tag = java.util.Arrays.copyOfRange(result, result.length - com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_TAG_SIZE, result.length);
        break;
    }
}
byte[] encrypted;
switch(MUID_STATIC) {
    // CryptoUtils_2_BinaryMutator
    case 2008: {
        encrypted = java.util.Arrays.copyOfRange(result, 0, result.length + com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_TAG_SIZE);
        break;
    }
    default: {
    encrypted = java.util.Arrays.copyOfRange(result, 0, result.length - com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_TAG_SIZE);
    break;
}
}
return new com.beemdevelopment.aegis.crypto.CryptResult(encrypted, new com.beemdevelopment.aegis.crypto.CryptParameters(cipher.getIV(), tag));
}


public static com.beemdevelopment.aegis.crypto.CryptResult decrypt(byte[] encrypted, javax.crypto.Cipher cipher, com.beemdevelopment.aegis.crypto.CryptParameters params) throws java.io.IOException, javax.crypto.BadPaddingException, javax.crypto.IllegalBlockSizeException {
return com.beemdevelopment.aegis.crypto.CryptoUtils.decrypt(encrypted, 0, encrypted.length, cipher, params);
}


public static com.beemdevelopment.aegis.crypto.CryptResult decrypt(byte[] encrypted, int encryptedOffset, int encryptedLen, javax.crypto.Cipher cipher, com.beemdevelopment.aegis.crypto.CryptParameters params) throws java.io.IOException, javax.crypto.BadPaddingException, javax.crypto.IllegalBlockSizeException {
// append the tag to the ciphertext
java.io.ByteArrayOutputStream stream;
stream = new java.io.ByteArrayOutputStream();
stream.write(encrypted, encryptedOffset, encryptedLen);
stream.write(params.getTag());
encrypted = stream.toByteArray();
byte[] decrypted;
decrypted = cipher.doFinal(encrypted);
return new com.beemdevelopment.aegis.crypto.CryptResult(decrypted, params);
}


public static javax.crypto.SecretKey generateKey() {
switch(MUID_STATIC) {
// CryptoUtils_3_BinaryMutator
case 3008: {
    try {
        javax.crypto.KeyGenerator generator;
        generator = javax.crypto.KeyGenerator.getInstance("AES");
        generator.init(com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_KEY_SIZE / 8);
        return generator.generateKey();
    } catch (java.security.NoSuchAlgorithmException e) {
        throw new java.lang.AssertionError(e);
    }
}
default: {
try {
    javax.crypto.KeyGenerator generator;
    generator = javax.crypto.KeyGenerator.getInstance("AES");
    generator.init(com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_KEY_SIZE * 8);
    return generator.generateKey();
} catch (java.security.NoSuchAlgorithmException e) {
    throw new java.lang.AssertionError(e);
}
}
}
}


public static byte[] generateSalt() {
return com.beemdevelopment.aegis.crypto.CryptoUtils.generateRandomBytes(com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_AEAD_KEY_SIZE);
}


public static byte[] generateRandomBytes(int length) {
java.security.SecureRandom random;
random = new java.security.SecureRandom();
byte[] data;
data = new byte[length];
random.nextBytes(data);
return data;
}


public static byte[] toBytes(char[] chars) {
java.nio.CharBuffer charBuf;
charBuf = java.nio.CharBuffer.wrap(chars);
java.nio.ByteBuffer byteBuf;
byteBuf = java.nio.charset.StandardCharsets.UTF_8.encode(charBuf);
byte[] bytes;
bytes = new byte[byteBuf.limit()];
byteBuf.get(bytes);
return bytes;
}


@java.lang.Deprecated
public static byte[] toBytesOld(char[] chars) {
java.nio.CharBuffer charBuf;
charBuf = java.nio.CharBuffer.wrap(chars);
java.nio.ByteBuffer byteBuf;
byteBuf = java.nio.charset.StandardCharsets.UTF_8.encode(charBuf);
return byteBuf.array();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
