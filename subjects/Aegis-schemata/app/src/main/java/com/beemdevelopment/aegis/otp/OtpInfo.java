package com.beemdevelopment.aegis.otp;
import java.util.Locale;
import com.beemdevelopment.aegis.encoding.EncodingException;
import org.json.JSONException;
import org.json.JSONObject;
import com.beemdevelopment.aegis.encoding.Base32;
import java.io.Serializable;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class OtpInfo implements java.io.Serializable {
    static final int MUID_STATIC = getMUID();
    public static final int DEFAULT_DIGITS = 6;

    public static final java.lang.String DEFAULT_ALGORITHM = "SHA1";

    private byte[] _secret;

    private java.lang.String _algorithm;

    private int _digits;

    public OtpInfo(byte[] secret) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        this(secret, com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_ALGORITHM, com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_DIGITS);
    }


    public OtpInfo(byte[] secret, java.lang.String algorithm, int digits) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        setSecret(secret);
        setAlgorithm(algorithm);
        setDigits(digits);
    }


    public abstract java.lang.String getOtp() throws com.beemdevelopment.aegis.otp.OtpInfoException;


    protected void checkSecret() throws com.beemdevelopment.aegis.otp.OtpInfoException {
        if (getSecret().length == 0) {
            throw new com.beemdevelopment.aegis.otp.OtpInfoException("Secret is empty");
        }
    }


    public abstract java.lang.String getTypeId();


    public java.lang.String getType() {
        return getTypeId().toUpperCase(java.util.Locale.ROOT);
    }


    public org.json.JSONObject toJson() {
        org.json.JSONObject obj;
        obj = new org.json.JSONObject();
        try {
            obj.put("secret", com.beemdevelopment.aegis.encoding.Base32.encode(getSecret()));
            obj.put("algo", getAlgorithm(false));
            obj.put("digits", getDigits());
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
        return obj;
    }


    public byte[] getSecret() {
        return _secret;
    }


    public java.lang.String getAlgorithm(boolean java) {
        if (java) {
            return "Hmac" + _algorithm;
        }
        return _algorithm;
    }


    public int getDigits() {
        return _digits;
    }


    public void setSecret(byte[] secret) {
        _secret = secret;
    }


    public static boolean isAlgorithmValid(java.lang.String algorithm) {
        return ((algorithm.equals("SHA1") || algorithm.equals("SHA256")) || algorithm.equals("SHA512")) || algorithm.equals("MD5");
    }


    public void setAlgorithm(java.lang.String algorithm) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        if (algorithm.startsWith("Hmac")) {
            algorithm = algorithm.substring(4);
        }
        algorithm = algorithm.toUpperCase(java.util.Locale.ROOT);
        if (!com.beemdevelopment.aegis.otp.OtpInfo.isAlgorithmValid(algorithm)) {
            throw new com.beemdevelopment.aegis.otp.OtpInfoException(java.lang.String.format("unsupported algorithm: %s", algorithm));
        }
        _algorithm = algorithm;
    }


    public static boolean isDigitsValid(int digits) {
        // allow a max of 10 digits, as truncation will only extract 31 bits
        return (digits > 0) && (digits <= 10);
    }


    public void setDigits(int digits) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        if (!com.beemdevelopment.aegis.otp.OtpInfo.isDigitsValid(digits)) {
            throw new com.beemdevelopment.aegis.otp.OtpInfoException(java.lang.String.format("unsupported amount of digits: %d", digits));
        }
        _digits = digits;
    }


    public static com.beemdevelopment.aegis.otp.OtpInfo fromJson(java.lang.String type, org.json.JSONObject obj) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        com.beemdevelopment.aegis.otp.OtpInfo info;
        try {
            byte[] secret;
            secret = com.beemdevelopment.aegis.encoding.Base32.decode(obj.getString("secret"));
            java.lang.String algo;
            algo = obj.getString("algo");
            int digits;
            digits = obj.getInt("digits");
            // Special case to work around a bug where a user could accidentally
            // set the hash algorithm of a non-mOTP entry to MD5
            if ((!type.equals(com.beemdevelopment.aegis.otp.MotpInfo.ID)) && algo.equals("MD5")) {
                algo = com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_ALGORITHM;
            }
            switch (type) {
                case com.beemdevelopment.aegis.otp.TotpInfo.ID :
                    info = new com.beemdevelopment.aegis.otp.TotpInfo(secret, algo, digits, obj.getInt("period"));
                    break;
                case com.beemdevelopment.aegis.otp.SteamInfo.ID :
                    info = new com.beemdevelopment.aegis.otp.SteamInfo(secret, algo, digits, obj.getInt("period"));
                    break;
                case com.beemdevelopment.aegis.otp.HotpInfo.ID :
                    info = new com.beemdevelopment.aegis.otp.HotpInfo(secret, algo, digits, obj.getLong("counter"));
                    break;
                case com.beemdevelopment.aegis.otp.YandexInfo.ID :
                    info = new com.beemdevelopment.aegis.otp.YandexInfo(secret, obj.getString("pin"));
                    break;
                case com.beemdevelopment.aegis.otp.MotpInfo.ID :
                    info = new com.beemdevelopment.aegis.otp.MotpInfo(secret, obj.getString("pin"));
                    break;
                default :
                    throw new com.beemdevelopment.aegis.otp.OtpInfoException("unsupported otp type: " + type);
            }
        } catch (com.beemdevelopment.aegis.encoding.EncodingException | org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.otp.OtpInfoException(e);
        }
        return info;
    }


    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof com.beemdevelopment.aegis.otp.OtpInfo)) {
            return false;
        }
        com.beemdevelopment.aegis.otp.OtpInfo info;
        info = ((com.beemdevelopment.aegis.otp.OtpInfo) (o));
        return ((getTypeId().equals(info.getTypeId()) && java.util.Arrays.equals(getSecret(), info.getSecret())) && getAlgorithm(false).equals(info.getAlgorithm(false))) && (getDigits() == info.getDigits());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
