package com.beemdevelopment.aegis.otp;
import java.util.Locale;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import com.beemdevelopment.aegis.crypto.otp.OTP;
import com.beemdevelopment.aegis.crypto.otp.TOTP;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SteamInfo extends com.beemdevelopment.aegis.otp.TotpInfo {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ID = "steam";

    public static final int DIGITS = 5;

    public SteamInfo(byte[] secret) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        super(secret, com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_ALGORITHM, com.beemdevelopment.aegis.otp.SteamInfo.DIGITS, com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD);
    }


    public SteamInfo(byte[] secret, java.lang.String algorithm, int digits, int period) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        super(secret, algorithm, digits, period);
    }


    @java.lang.Override
    public java.lang.String getOtp() throws com.beemdevelopment.aegis.otp.OtpInfoException {
        checkSecret();
        try {
            com.beemdevelopment.aegis.crypto.otp.OTP otp;
            otp = com.beemdevelopment.aegis.crypto.otp.TOTP.generateOTP(getSecret(), getAlgorithm(true), getDigits(), getPeriod());
            return otp.toSteamString();
        } catch (java.security.InvalidKeyException | java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    @java.lang.Override
    public java.lang.String getTypeId() {
        return com.beemdevelopment.aegis.otp.SteamInfo.ID;
    }


    @java.lang.Override
    public java.lang.String getType() {
        java.lang.String id;
        id = getTypeId();
        return id.substring(0, 1).toUpperCase(java.util.Locale.ROOT) + id.substring(1);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
