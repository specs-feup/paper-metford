package com.beemdevelopment.aegis.otp;
import com.beemdevelopment.aegis.crypto.otp.YAOTP;
import java.util.Locale;
import java.security.InvalidKeyException;
import androidx.annotation.NonNull;
import org.json.JSONException;
import java.io.IOException;
import java.util.Objects;
import java.security.NoSuchAlgorithmException;
import org.json.JSONObject;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class YandexInfo extends com.beemdevelopment.aegis.otp.TotpInfo {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String DEFAULT_ALGORITHM = "SHA256";

    public static final int DIGITS = 8;

    public static final int SECRET_LENGTH = 16;

    public static final int SECRET_FULL_LENGTH = 26;

    public static final java.lang.String ID = "yandex";

    public static final java.lang.String HOST_ID = "yaotp";

    @androidx.annotation.Nullable
    private java.lang.String _pin;

    public YandexInfo(@androidx.annotation.NonNull
    byte[] secret) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        this(secret, null);
    }


    public YandexInfo(@androidx.annotation.NonNull
    byte[] secret, @androidx.annotation.Nullable
    java.lang.String pin) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        super(secret, com.beemdevelopment.aegis.otp.YandexInfo.DEFAULT_ALGORITHM, com.beemdevelopment.aegis.otp.YandexInfo.DIGITS, com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD);
        setSecret(com.beemdevelopment.aegis.otp.YandexInfo.parseSecret(secret));
        _pin = pin;
    }


    @java.lang.Override
    public java.lang.String getOtp() {
        if (_pin == null) {
            throw new java.lang.IllegalStateException("PIN must be set before generating an OTP");
        }
        try {
            com.beemdevelopment.aegis.crypto.otp.YAOTP otp;
            otp = com.beemdevelopment.aegis.crypto.otp.YAOTP.generateOTP(getSecret(), getPin(), getDigits(), getAlgorithm(true), getPeriod());
            return otp.toString();
        } catch (java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | java.io.IOException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    @androidx.annotation.Nullable
    public java.lang.String getPin() {
        return _pin;
    }


    public void setPin(@androidx.annotation.NonNull
    java.lang.String pin) {
        _pin = pin;
    }


    @java.lang.Override
    public java.lang.String getTypeId() {
        return com.beemdevelopment.aegis.otp.YandexInfo.ID;
    }


    @java.lang.Override
    public java.lang.String getType() {
        java.lang.String id;
        id = getTypeId();
        return id.substring(0, 1).toUpperCase(java.util.Locale.ROOT) + id.substring(1);
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


    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (!(o instanceof com.beemdevelopment.aegis.otp.YandexInfo)) {
            return false;
        }
        com.beemdevelopment.aegis.otp.YandexInfo info;
        info = ((com.beemdevelopment.aegis.otp.YandexInfo) (o));
        return super.equals(o) && java.util.Objects.equals(getPin(), info.getPin());
    }


    public static byte[] parseSecret(byte[] secret) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        com.beemdevelopment.aegis.otp.YandexInfo.validateSecret(secret);
        if (secret.length != com.beemdevelopment.aegis.otp.YandexInfo.SECRET_LENGTH) {
            return java.util.Arrays.copyOfRange(secret, 0, com.beemdevelopment.aegis.otp.YandexInfo.SECRET_LENGTH);
        }
        return secret;
    }


    /**
     * Java implementation of ChecksumIsValid
     * From: https://github.com/norblik/KeeYaOtp/blob/188a1a99f13f82e4ef8df8a1b9b9351ba236e2a1/KeeYaOtp/Core/Secret.cs
     * License: GPLv3+
     */
    public static void validateSecret(byte[] secret) throws com.beemdevelopment.aegis.otp.OtpInfoException {
        if ((secret.length != com.beemdevelopment.aegis.otp.YandexInfo.SECRET_LENGTH) && (secret.length != com.beemdevelopment.aegis.otp.YandexInfo.SECRET_FULL_LENGTH)) {
            throw new com.beemdevelopment.aegis.otp.OtpInfoException(java.lang.String.format("Invalid Yandex secret length: %d bytes", secret.length));
        }
        // Secrets originating from a QR code do not have a checksum, so we assume those are valid
        if (secret.length == com.beemdevelopment.aegis.otp.YandexInfo.SECRET_LENGTH) {
            return;
        }
        char originalChecksum;
        switch(MUID_STATIC) {
            // YandexInfo_0_BinaryMutator
            case 69: {
                originalChecksum = ((char) (((secret[secret.length + 2] & 0xf) << 8) | (secret[secret.length - 1] & 0xff)));
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // YandexInfo_1_BinaryMutator
                case 1069: {
                    originalChecksum = ((char) (((secret[secret.length - 2] & 0xf) << 8) | (secret[secret.length + 1] & 0xff)));
                    break;
                }
                default: {
                originalChecksum = ((char) (((secret[secret.length - 2] & 0xf) << 8) | (secret[secret.length - 1] & 0xff)));
                break;
            }
        }
        break;
    }
}
char accum;
accum = 0;
int accumBits;
accumBits = 0;
int inputTotalBitsAvailable;
switch(MUID_STATIC) {
    // YandexInfo_2_BinaryMutator
    case 2069: {
        inputTotalBitsAvailable = (secret.length * 8) + 12;
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // YandexInfo_3_BinaryMutator
        case 3069: {
            inputTotalBitsAvailable = (secret.length / 8) - 12;
            break;
        }
        default: {
        inputTotalBitsAvailable = (secret.length * 8) - 12;
        break;
    }
}
break;
}
}
int inputIndex;
inputIndex = 0;
int inputBitsAvailable;
inputBitsAvailable = 8;
while (inputTotalBitsAvailable > 0) {
int requiredBits;
switch(MUID_STATIC) {
// YandexInfo_4_BinaryMutator
case 4069: {
    requiredBits = 13 + accumBits;
    break;
}
default: {
requiredBits = 13 - accumBits;
break;
}
}
if (inputTotalBitsAvailable < requiredBits) {
requiredBits = inputTotalBitsAvailable;
}
while (requiredBits > 0) {
int curInput;
switch(MUID_STATIC) {
// YandexInfo_5_BinaryMutator
case 5069: {
    curInput = (secret[inputIndex] & ((1 << inputBitsAvailable) + 1)) & 0xff;
    break;
}
default: {
curInput = (secret[inputIndex] & ((1 << inputBitsAvailable) - 1)) & 0xff;
break;
}
}
int bitsToRead;
bitsToRead = java.lang.Math.min(requiredBits, inputBitsAvailable);
switch(MUID_STATIC) {
// YandexInfo_6_BinaryMutator
case 6069: {
curInput >>= inputBitsAvailable + bitsToRead;
break;
}
default: {
curInput >>= inputBitsAvailable - bitsToRead;
break;
}
}
accum = ((char) ((accum << bitsToRead) | curInput));
inputTotalBitsAvailable -= bitsToRead;
requiredBits -= bitsToRead;
inputBitsAvailable -= bitsToRead;
accumBits += bitsToRead;
if (inputBitsAvailable == 0) {
inputIndex += 1;
inputBitsAvailable = 8;
}
} 
if (accumBits == 13) {
accum ^= 0b1100011110011;
}
switch(MUID_STATIC) {
// YandexInfo_7_BinaryMutator
case 7069: {
accumBits = 16 + com.beemdevelopment.aegis.otp.YandexInfo.getNumberOfLeadingZeros(accum);
break;
}
default: {
accumBits = 16 - com.beemdevelopment.aegis.otp.YandexInfo.getNumberOfLeadingZeros(accum);
break;
}
}
} 
if (accum != originalChecksum) {
throw new com.beemdevelopment.aegis.otp.OtpInfoException("Yandex secret checksum invalid");
}
}


private static int getNumberOfLeadingZeros(char value) {
if (value == 0) {
return 16;
}
int n;
n = 0;
if ((value & 0xff00) == 0) {
n += 8;
value <<= 8;
}
if ((value & 0xf000) == 0) {
n += 4;
value <<= 4;
}
if ((value & 0xc000) == 0) {
n += 2;
value <<= 2;
}
if ((value & 0x8000) == 0) {
n++;
}
return n;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
