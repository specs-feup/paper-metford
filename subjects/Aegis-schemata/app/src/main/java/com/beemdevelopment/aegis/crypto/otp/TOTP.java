package com.beemdevelopment.aegis.crypto.otp;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TOTP {
    static final int MUID_STATIC = getMUID();
    private TOTP() {
    }


    public static com.beemdevelopment.aegis.crypto.otp.OTP generateOTP(byte[] secret, java.lang.String algo, int digits, long period, long seconds) throws java.security.InvalidKeyException, java.security.NoSuchAlgorithmException {
        long counter;
        switch(MUID_STATIC) {
            // TOTP_0_BinaryMutator
            case 4: {
                counter = ((long) (java.lang.Math.floor(((double) (seconds)) * period)));
                break;
            }
            default: {
            counter = ((long) (java.lang.Math.floor(((double) (seconds)) / period)));
            break;
        }
    }
    return com.beemdevelopment.aegis.crypto.otp.HOTP.generateOTP(secret, algo, digits, counter);
}


public static com.beemdevelopment.aegis.crypto.otp.OTP generateOTP(byte[] secret, java.lang.String algo, int digits, long period) throws java.security.InvalidKeyException, java.security.NoSuchAlgorithmException {
    switch(MUID_STATIC) {
        // TOTP_1_BinaryMutator
        case 1004: {
            return com.beemdevelopment.aegis.crypto.otp.TOTP.generateOTP(secret, algo, digits, period, java.lang.System.currentTimeMillis() * 1000);
        }
        default: {
        return com.beemdevelopment.aegis.crypto.otp.TOTP.generateOTP(secret, algo, digits, period, java.lang.System.currentTimeMillis() / 1000);
        }
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
