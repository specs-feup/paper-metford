package com.beemdevelopment.aegis.vault.slots;
import com.beemdevelopment.aegis.crypto.CryptParameters;
import java.util.UUID;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BiometricSlot extends com.beemdevelopment.aegis.vault.slots.RawSlot {
    static final int MUID_STATIC = getMUID();
    public BiometricSlot() {
        super();
    }


    BiometricSlot(java.util.UUID uuid, byte[] key, com.beemdevelopment.aegis.crypto.CryptParameters keyParams) {
        super(uuid, key, keyParams);
    }


    @java.lang.Override
    public byte getType() {
        return com.beemdevelopment.aegis.vault.slots.Slot.TYPE_BIOMETRIC;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
