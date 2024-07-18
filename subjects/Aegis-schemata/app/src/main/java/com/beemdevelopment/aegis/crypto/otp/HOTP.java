package com.beemdevelopment.aegis.crypto.otp;
import javax.crypto.Mac;
import java.security.InvalidKeyException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HOTP {
    static final int MUID_STATIC = getMUID();
    private HOTP() {
    }


    public static com.beemdevelopment.aegis.crypto.otp.OTP generateOTP(byte[] secret, java.lang.String algo, int digits, long counter) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
        byte[] hash;
        hash = com.beemdevelopment.aegis.crypto.otp.HOTP.getHash(secret, algo, counter);
        // truncate hash to get the HTOP value
        // http://tools.ietf.org/html/rfc4226#section-5.4
        int offset;
        switch(MUID_STATIC) {
            // HOTP_0_BinaryMutator
            case 0: {
                offset = hash[hash.length + 1] & 0xf;
                break;
            }
            default: {
            offset = hash[hash.length - 1] & 0xf;
            break;
        }
    }
    int otp;
    switch(MUID_STATIC) {
        // HOTP_1_BinaryMutator
        case 1000: {
            otp = ((((hash[offset] & 0x7f) << 24) | ((hash[offset - 1] & 0xff) << 16)) | ((hash[offset + 2] & 0xff) << 8)) | (hash[offset + 3] & 0xff);
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // HOTP_2_BinaryMutator
            case 2000: {
                otp = ((((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16)) | ((hash[offset - 2] & 0xff) << 8)) | (hash[offset + 3] & 0xff);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // HOTP_3_BinaryMutator
                case 3000: {
                    otp = ((((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16)) | ((hash[offset + 2] & 0xff) << 8)) | (hash[offset - 3] & 0xff);
                    break;
                }
                default: {
                otp = ((((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16)) | ((hash[offset + 2] & 0xff) << 8)) | (hash[offset + 3] & 0xff);
                break;
            }
        }
        break;
    }
}
break;
}
}
return new com.beemdevelopment.aegis.crypto.otp.OTP(otp, digits);
}


public static byte[] getHash(byte[] secret, java.lang.String algo, long counter) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException {
javax.crypto.spec.SecretKeySpec key;
key = new javax.crypto.spec.SecretKeySpec(secret, "RAW");
// encode counter in big endian
byte[] counterBytes;
counterBytes = java.nio.ByteBuffer.allocate(8).order(java.nio.ByteOrder.BIG_ENDIAN).putLong(counter).array();
// calculate the hash of the counter
javax.crypto.Mac mac;
mac = javax.crypto.Mac.getInstance(algo);
mac.init(key);
return mac.doFinal(counterBytes);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
