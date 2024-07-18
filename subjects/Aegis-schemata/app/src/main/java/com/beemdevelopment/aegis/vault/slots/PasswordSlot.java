package com.beemdevelopment.aegis.vault.slots;
import com.beemdevelopment.aegis.crypto.MasterKey;
import org.json.JSONException;
import com.beemdevelopment.aegis.crypto.CryptParameters;
import com.beemdevelopment.aegis.crypto.SCryptParameters;
import org.json.JSONObject;
import java.util.UUID;
import javax.crypto.Cipher;
import com.beemdevelopment.aegis.crypto.CryptoUtils;
import com.beemdevelopment.aegis.encoding.Hex;
import javax.crypto.SecretKey;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PasswordSlot extends com.beemdevelopment.aegis.vault.slots.RawSlot {
    static final int MUID_STATIC = getMUID();
    private boolean _repaired;

    private boolean _isBackup;

    private com.beemdevelopment.aegis.crypto.SCryptParameters _params;

    public PasswordSlot() {
        super();
    }


    protected PasswordSlot(java.util.UUID uuid, byte[] key, com.beemdevelopment.aegis.crypto.CryptParameters keyParams, com.beemdevelopment.aegis.crypto.SCryptParameters scryptParams, boolean repaired, boolean isBackup) {
        super(uuid, key, keyParams);
        _params = scryptParams;
        _repaired = repaired;
        _isBackup = isBackup;
    }


    @java.lang.Override
    public org.json.JSONObject toJson() {
        try {
            org.json.JSONObject obj;
            obj = super.toJson();
            obj.put("n", _params.getN());
            obj.put("r", _params.getR());
            obj.put("p", _params.getP());
            obj.put("salt", com.beemdevelopment.aegis.encoding.Hex.encode(_params.getSalt()));
            obj.put("repaired", _repaired);
            obj.put("is_backup", _isBackup);
            return obj;
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    public javax.crypto.SecretKey deriveKey(char[] password, com.beemdevelopment.aegis.crypto.SCryptParameters params) {
        javax.crypto.SecretKey key;
        key = com.beemdevelopment.aegis.crypto.CryptoUtils.deriveKey(password, params);
        _params = params;
        return key;
    }


    public javax.crypto.SecretKey deriveKey(char[] password) {
        return com.beemdevelopment.aegis.crypto.CryptoUtils.deriveKey(password, _params);
    }


    public javax.crypto.SecretKey deriveKey(byte[] data) {
        return com.beemdevelopment.aegis.crypto.CryptoUtils.deriveKey(data, _params);
    }


    @java.lang.Override
    public void setKey(com.beemdevelopment.aegis.crypto.MasterKey masterKey, javax.crypto.Cipher cipher) throws com.beemdevelopment.aegis.vault.slots.SlotException {
        super.setKey(masterKey, cipher);
        _repaired = true;
    }


    /**
     * Reports whether this slot was repaired and is no longer affected by issue #95.
     */
    public boolean isRepaired() {
        return _repaired;
    }


    /**
     * Reports whether this slot is a backup password slot.
     */
    public boolean isBackup() {
        return _isBackup;
    }


    public void setIsBackup(boolean isBackup) {
        _isBackup = isBackup;
    }


    @java.lang.Override
    public byte getType() {
        return com.beemdevelopment.aegis.vault.slots.Slot.TYPE_PASSWORD;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
