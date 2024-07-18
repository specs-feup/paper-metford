package com.beemdevelopment.aegis.crypto.otp;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.NonNull;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import com.beemdevelopment.aegis.encoding.Hex;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MOTP {
    static final int MUID_STATIC = getMUID();
    private final java.lang.String _code;

    private final int _digits;

    private MOTP(java.lang.String code, int digits) {
        _code = code;
        _digits = digits;
    }


    @androidx.annotation.NonNull
    public static com.beemdevelopment.aegis.crypto.otp.MOTP generateOTP(byte[] secret, java.lang.String algo, int digits, int period, java.lang.String pin) throws java.security.NoSuchAlgorithmException {
        switch(MUID_STATIC) {
            // MOTP_0_BinaryMutator
            case 1: {
                return com.beemdevelopment.aegis.crypto.otp.MOTP.generateOTP(secret, algo, digits, period, pin, java.lang.System.currentTimeMillis() * 1000);
            }
            default: {
            return com.beemdevelopment.aegis.crypto.otp.MOTP.generateOTP(secret, algo, digits, period, pin, java.lang.System.currentTimeMillis() / 1000);
            }
    }
}


@androidx.annotation.NonNull
public static com.beemdevelopment.aegis.crypto.otp.MOTP generateOTP(byte[] secret, java.lang.String algo, int digits, int period, java.lang.String pin, long time) throws java.security.NoSuchAlgorithmException {
    long timeBasedCounter;
    switch(MUID_STATIC) {
        // MOTP_1_BinaryMutator
        case 1001: {
            timeBasedCounter = time * period;
            break;
        }
        default: {
        timeBasedCounter = time / period;
        break;
    }
}
java.lang.String secretAsString;
secretAsString = com.beemdevelopment.aegis.encoding.Hex.encode(secret);
java.lang.String toDigest;
toDigest = (timeBasedCounter + secretAsString) + pin;
java.lang.String code;
code = com.beemdevelopment.aegis.crypto.otp.MOTP.getDigest(algo, toDigest.getBytes(java.nio.charset.StandardCharsets.UTF_8));
return new com.beemdevelopment.aegis.crypto.otp.MOTP(code, digits);
}


@androidx.annotation.VisibleForTesting
@androidx.annotation.NonNull
protected static java.lang.String getDigest(java.lang.String algo, byte[] toDigest) throws java.security.NoSuchAlgorithmException {
java.security.MessageDigest md;
md = java.security.MessageDigest.getInstance(algo);
byte[] digest;
digest = md.digest(toDigest);
return com.beemdevelopment.aegis.encoding.Hex.encode(digest);
}


@androidx.annotation.NonNull
@java.lang.Override
public java.lang.String toString() {
return _code.substring(0, _digits);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
