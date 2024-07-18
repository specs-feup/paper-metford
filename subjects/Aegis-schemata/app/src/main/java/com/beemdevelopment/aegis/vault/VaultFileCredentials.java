package com.beemdevelopment.aegis.vault;
import com.beemdevelopment.aegis.crypto.MasterKey;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.crypto.CryptParameters;
import com.beemdevelopment.aegis.crypto.MasterKeyException;
import com.beemdevelopment.aegis.crypto.CryptResult;
import com.beemdevelopment.aegis.util.Cloner;
import java.io.Serializable;
import com.beemdevelopment.aegis.vault.slots.SlotList;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VaultFileCredentials implements java.io.Serializable {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.crypto.MasterKey _key;

    private com.beemdevelopment.aegis.vault.slots.SlotList _slots;

    public VaultFileCredentials() {
        _key = com.beemdevelopment.aegis.crypto.MasterKey.generate();
        _slots = new com.beemdevelopment.aegis.vault.slots.SlotList();
    }


    public VaultFileCredentials(com.beemdevelopment.aegis.crypto.MasterKey key, com.beemdevelopment.aegis.vault.slots.SlotList slots) {
        _key = key;
        _slots = slots;
    }


    public com.beemdevelopment.aegis.crypto.CryptResult encrypt(byte[] bytes) throws com.beemdevelopment.aegis.crypto.MasterKeyException {
        return _key.encrypt(bytes);
    }


    public com.beemdevelopment.aegis.crypto.CryptResult decrypt(byte[] bytes, com.beemdevelopment.aegis.crypto.CryptParameters params) throws com.beemdevelopment.aegis.crypto.MasterKeyException {
        return _key.decrypt(bytes, params);
    }


    public com.beemdevelopment.aegis.crypto.MasterKey getKey() {
        return _key;
    }


    public com.beemdevelopment.aegis.vault.slots.SlotList getSlots() {
        return _slots;
    }


    /**
     * Returns a copy of these VaultFileCredentials that is suitable for exporting.
     * In case there's a backup password slot, any regular password slots are stripped.
     */
    public com.beemdevelopment.aegis.vault.VaultFileCredentials exportable() {
        return new com.beemdevelopment.aegis.vault.VaultFileCredentials(_key, _slots.exportable());
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public com.beemdevelopment.aegis.vault.VaultFileCredentials clone() {
        return com.beemdevelopment.aegis.util.Cloner.clone(this);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
