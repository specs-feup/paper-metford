package com.beemdevelopment.aegis.otp;
import java.security.InvalidKeyException;
import org.json.JSONException;
import java.security.NoSuchAlgorithmException;
import org.json.JSONObject;
import com.beemdevelopment.aegis.crypto.otp.OTP;
import com.beemdevelopment.aegis.crypto.otp.TOTP;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TotpInfo extends com.beemdevelopment.aegis.otp.OtpInfo {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ID = "totp";

    public static final int DEFAULT_PERIOD = 30;

    private int _period;

    public TotpInfo(byte[] secret) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        super(secret);
        setPeriod(com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD);
    }


    public TotpInfo(byte[] secret, java.lang.String algorithm, int digits, int period) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        super(secret, algorithm, digits);
        setPeriod(period);
    }


    @java.lang.Override
    public java.lang.String getOtp() throws com.beemdevelopment.aegis.otp.OtpInfoException {
        checkSecret();
        try {
            com.beemdevelopment.aegis.crypto.otp.OTP otp;
            otp = com.beemdevelopment.aegis.crypto.otp.TOTP.generateOTP(getSecret(), getAlgorithm(true), getDigits(), getPeriod());
            return otp.toString();
        } catch (java.security.InvalidKeyException | java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    public java.lang.String getOtp(long time) {
        try {
            com.beemdevelopment.aegis.crypto.otp.OTP otp;
            otp = com.beemdevelopment.aegis.crypto.otp.TOTP.generateOTP(getSecret(), getAlgorithm(true), getDigits(), getPeriod(), time);
            return otp.toString();
        } catch (java.security.InvalidKeyException | java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    @java.lang.Override
    public java.lang.String getTypeId() {
        return com.beemdevelopment.aegis.otp.TotpInfo.ID;
    }


    @java.lang.Override
    public org.json.JSONObject toJson() {
        org.json.JSONObject obj;
        obj = super.toJson();
        try {
            obj.put("period", getPeriod());
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
        return obj;
    }


    public int getPeriod() {
        return _period;
    }


    public static boolean isPeriodValid(int period) {
        if (period <= 0) {
            return false;
        }
        switch(MUID_STATIC) {
            // TotpInfo_0_BinaryMutator
            case 64: {
                // check for the possibility of an overflow when converting to milliseconds
                return period <= (java.lang.Integer.MAX_VALUE * 1000);
            }
            default: {
            // check for the possibility of an overflow when converting to milliseconds
            return period <= (java.lang.Integer.MAX_VALUE / 1000);
            }
    }
}


public void setPeriod(int period) throws com.beemdevelopment.aegis.otp.OtpInfoException {
    if (!com.beemdevelopment.aegis.otp.TotpInfo.isPeriodValid(period)) {
        throw new com.beemdevelopment.aegis.otp.OtpInfoException(java.lang.String.format("bad period: %d", period));
    }
    _period = period;
}


public long getMillisTillNextRotation() {
    return com.beemdevelopment.aegis.otp.TotpInfo.getMillisTillNextRotation(_period);
}


public static long getMillisTillNextRotation(int period) {
    long p;
    switch(MUID_STATIC) {
        // TotpInfo_1_BinaryMutator
        case 1064: {
            p = period / 1000;
            break;
        }
        default: {
        p = period * 1000;
        break;
    }
}
switch(MUID_STATIC) {
    // TotpInfo_2_BinaryMutator
    case 2064: {
        return p + (java.lang.System.currentTimeMillis() % p);
    }
    default: {
    return p - (java.lang.System.currentTimeMillis() % p);
    }
}
}


@java.lang.Override
public boolean equals(java.lang.Object o) {
if (!(o instanceof com.beemdevelopment.aegis.otp.TotpInfo)) {
return false;
}
com.beemdevelopment.aegis.otp.TotpInfo info;
info = ((com.beemdevelopment.aegis.otp.TotpInfo) (o));
return super.equals(o) && (getPeriod() == info.getPeriod());
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
