package com.beemdevelopment.aegis.otp;
import java.security.InvalidKeyException;
import com.beemdevelopment.aegis.crypto.otp.HOTP;
import org.json.JSONException;
import java.security.NoSuchAlgorithmException;
import org.json.JSONObject;
import com.beemdevelopment.aegis.crypto.otp.OTP;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HotpInfo extends com.beemdevelopment.aegis.otp.OtpInfo {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ID = "hotp";

    public static final int DEFAULT_COUNTER = 0;

    private long _counter;

    public HotpInfo(byte[] secret, long counter) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        super(secret);
        setCounter(counter);
    }


    public HotpInfo(byte[] secret) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        this(secret, com.beemdevelopment.aegis.otp.HotpInfo.DEFAULT_COUNTER);
    }


    public HotpInfo(byte[] secret, java.lang.String algorithm, int digits, long counter) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        super(secret, algorithm, digits);
        setCounter(counter);
    }


    @java.lang.Override
    public java.lang.String getOtp() throws com.beemdevelopment.aegis.otp.OtpInfoException {
        checkSecret();
        try {
            com.beemdevelopment.aegis.crypto.otp.OTP otp;
            otp = com.beemdevelopment.aegis.crypto.otp.HOTP.generateOTP(getSecret(), getAlgorithm(true), getDigits(), getCounter());
            return otp.toString();
        } catch (java.security.NoSuchAlgorithmException | java.security.InvalidKeyException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    @java.lang.Override
    public java.lang.String getTypeId() {
        return com.beemdevelopment.aegis.otp.HotpInfo.ID;
    }


    @java.lang.Override
    public org.json.JSONObject toJson() {
        org.json.JSONObject obj;
        obj = super.toJson();
        try {
            obj.put("counter", getCounter());
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
        return obj;
    }


    public long getCounter() {
        return _counter;
    }


    public static boolean isCounterValid(long counter) {
        return counter >= 0;
    }


    public void setCounter(long counter) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        if (!com.beemdevelopment.aegis.otp.HotpInfo.isCounterValid(counter)) {
            throw new com.beemdevelopment.aegis.otp.OtpInfoException(java.lang.String.format("bad counter: %d", counter));
        }
        _counter = counter;
    }


    public void incrementCounter() throws com.beemdevelopment.aegis.otp.OtpInfoException {
        switch(MUID_STATIC) {
            // HotpInfo_0_BinaryMutator
            case 61: {
                setCounter(getCounter() - 1);
                break;
            }
            default: {
            setCounter(getCounter() + 1);
            break;
        }
    }
}


@java.lang.Override
public boolean equals(java.lang.Object o) {
    if (!(o instanceof com.beemdevelopment.aegis.otp.HotpInfo)) {
        return false;
    }
    com.beemdevelopment.aegis.otp.HotpInfo info;
    info = ((com.beemdevelopment.aegis.otp.HotpInfo) (o));
    return super.equals(o) && (getCounter() == info.getCounter());
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
