package com.beemdevelopment.aegis.otp;
import androidx.annotation.NonNull;
import org.json.JSONException;
import java.util.Objects;
import java.security.NoSuchAlgorithmException;
import com.beemdevelopment.aegis.crypto.otp.MOTP;
import org.json.JSONObject;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MotpInfo extends com.beemdevelopment.aegis.otp.TotpInfo {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ID = "motp";

    public static final java.lang.String SCHEME = "motp";

    public static final java.lang.String ALGORITHM = "MD5";

    public static final int PERIOD = 10;

    public static final int DIGITS = 6;

    private java.lang.String _pin;

    public MotpInfo(@androidx.annotation.NonNull
    byte[] secret) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        this(secret, null);
    }


    public MotpInfo(byte[] secret, java.lang.String pin) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        super(secret, com.beemdevelopment.aegis.otp.MotpInfo.ALGORITHM, com.beemdevelopment.aegis.otp.MotpInfo.DIGITS, com.beemdevelopment.aegis.otp.MotpInfo.PERIOD);
        setPin(pin);
    }


    @java.lang.Override
    public java.lang.String getOtp() {
        if (_pin == null) {
            throw new java.lang.IllegalStateException("PIN must be set before generating an OTP");
        }
        try {
            com.beemdevelopment.aegis.crypto.otp.MOTP otp;
            otp = com.beemdevelopment.aegis.crypto.otp.MOTP.generateOTP(getSecret(), getAlgorithm(false), getDigits(), getPeriod(), getPin());
            return otp.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    @java.lang.Override
    public java.lang.String getOtp(long time) {
        if (_pin == null) {
            throw new java.lang.IllegalStateException("PIN must be set before generating an OTP");
        }
        try {
            com.beemdevelopment.aegis.crypto.otp.MOTP otp;
            otp = com.beemdevelopment.aegis.crypto.otp.MOTP.generateOTP(getSecret(), getAlgorithm(false), getDigits(), getPeriod(), getPin(), time);
            return otp.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    @java.lang.Override
    public java.lang.String getTypeId() {
        return com.beemdevelopment.aegis.otp.MotpInfo.ID;
    }


    @java.lang.Override
    public org.json.JSONObject toJson() {
        org.json.JSONObject result;
        result = super.toJson();
        try {
            result.put("pin", getPin());
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
        return result;
    }


    @androidx.annotation.Nullable
    public java.lang.String getPin() {
        return _pin;
    }


    public void setPin(@androidx.annotation.NonNull
    java.lang.String pin) {
        this._pin = pin;
    }


    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (!(o instanceof com.beemdevelopment.aegis.otp.MotpInfo)) {
            return false;
        }
        com.beemdevelopment.aegis.otp.MotpInfo info;
        info = ((com.beemdevelopment.aegis.otp.MotpInfo) (o));
        return super.equals(o) && java.util.Objects.equals(getPin(), info.getPin());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
