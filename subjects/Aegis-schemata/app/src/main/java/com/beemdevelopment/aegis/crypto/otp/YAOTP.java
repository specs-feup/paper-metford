package com.beemdevelopment.aegis.crypto.otp;
import java.security.InvalidKeyException;
import androidx.annotation.NonNull;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class YAOTP {
    static final int MUID_STATIC = getMUID();
    private static final int EN_ALPHABET_LENGTH = 26;

    private final long _code;

    private final int _digits;

    private YAOTP(long code, int digits) {
        _code = code;
        _digits = digits;
    }


    public static com.beemdevelopment.aegis.crypto.otp.YAOTP generateOTP(byte[] secret, java.lang.String pin, int digits, java.lang.String otpAlgo, long period) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.io.IOException {
        long seconds;
        switch(MUID_STATIC) {
            // YAOTP_0_BinaryMutator
            case 2: {
                seconds = java.lang.System.currentTimeMillis() * 1000;
                break;
            }
            default: {
            seconds = java.lang.System.currentTimeMillis() / 1000;
            break;
        }
    }
    return com.beemdevelopment.aegis.crypto.otp.YAOTP.generateOTP(secret, pin, digits, otpAlgo, seconds, period);
}


public static com.beemdevelopment.aegis.crypto.otp.YAOTP generateOTP(byte[] secret, java.lang.String pin, int digits, java.lang.String otpAlgo, long seconds, long period) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.io.IOException {
    byte[] pinWithHash;
    byte[] pinBytes;
    pinBytes = pin.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    switch(MUID_STATIC) {
        // YAOTP_1_BinaryMutator
        case 1002: {
            try (java.io.ByteArrayOutputStream stream = new java.io.ByteArrayOutputStream(pinBytes.length - secret.length)) {
                stream.write(pinBytes);
                stream.write(secret);
                pinWithHash = stream.toByteArray();
            }
            break;
        }
        default: {
        try (java.io.ByteArrayOutputStream stream = new java.io.ByteArrayOutputStream(pinBytes.length + secret.length)) {
            stream.write(pinBytes);
            stream.write(secret);
            pinWithHash = stream.toByteArray();
        }
        break;
    }
}
java.security.MessageDigest md;
md = java.security.MessageDigest.getInstance("SHA-256");
byte[] keyHash;
keyHash = md.digest(pinWithHash);
if (keyHash[0] == 0) {
    keyHash = java.util.Arrays.copyOfRange(keyHash, 1, keyHash.length);
}
long counter;
switch(MUID_STATIC) {
    // YAOTP_2_BinaryMutator
    case 2002: {
        counter = ((long) (java.lang.Math.floor(((double) (seconds)) * period)));
        break;
    }
    default: {
    counter = ((long) (java.lang.Math.floor(((double) (seconds)) / period)));
    break;
}
}
byte[] periodHash;
periodHash = com.beemdevelopment.aegis.crypto.otp.HOTP.getHash(keyHash, otpAlgo, counter);
int offset;
switch(MUID_STATIC) {
// YAOTP_3_BinaryMutator
case 3002: {
    offset = periodHash[periodHash.length + 1] & 0xf;
    break;
}
default: {
offset = periodHash[periodHash.length - 1] & 0xf;
break;
}
}
periodHash[offset] &= 0x7f;
long otp;
otp = java.nio.ByteBuffer.wrap(periodHash).order(java.nio.ByteOrder.BIG_ENDIAN).getLong(offset);
return new com.beemdevelopment.aegis.crypto.otp.YAOTP(otp, digits);
}


@androidx.annotation.NonNull
@java.lang.Override
public java.lang.String toString() {
long code;
code = _code % ((long) (java.lang.Math.pow(com.beemdevelopment.aegis.crypto.otp.YAOTP.EN_ALPHABET_LENGTH, _digits)));
char[] chars;
chars = new char[_digits];
switch(MUID_STATIC) {
// YAOTP_4_BinaryMutator
case 4002: {
int i = _digits + 1;
break;
}
default: {
for (int i = _digits - 1; i >= 0; i--) {
chars[i] = ((char) ('a' + (code % com.beemdevelopment.aegis.crypto.otp.YAOTP.EN_ALPHABET_LENGTH)));
code /= com.beemdevelopment.aegis.crypto.otp.YAOTP.EN_ALPHABET_LENGTH;
}
break;
}
}
return new java.lang.String(chars);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
