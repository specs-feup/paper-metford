package com.beemdevelopment.aegis.vault.slots;
import com.beemdevelopment.aegis.util.UUIDMap;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import com.beemdevelopment.aegis.encoding.EncodingException;
import org.json.JSONException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import javax.crypto.NoSuchPaddingException;
import com.beemdevelopment.aegis.crypto.SCryptParameters;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import com.beemdevelopment.aegis.crypto.MasterKey;
import javax.crypto.spec.SecretKeySpec;
import com.beemdevelopment.aegis.crypto.CryptParameters;
import org.json.JSONObject;
import java.util.UUID;
import com.beemdevelopment.aegis.crypto.CryptResult;
import javax.crypto.Cipher;
import com.beemdevelopment.aegis.crypto.CryptoUtils;
import com.beemdevelopment.aegis.encoding.Hex;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class Slot extends com.beemdevelopment.aegis.util.UUIDMap.Value {
    static final int MUID_STATIC = getMUID();
    public static final byte TYPE_RAW = 0x0;

    public static final byte TYPE_PASSWORD = 0x1;

    public static final byte TYPE_BIOMETRIC = 0x2;

    private byte[] _encryptedMasterKey;

    private com.beemdevelopment.aegis.crypto.CryptParameters _encryptedMasterKeyParams;

    protected Slot() {
        super();
    }


    protected Slot(java.util.UUID uuid, byte[] key, com.beemdevelopment.aegis.crypto.CryptParameters keyParams) {
        super(uuid);
        _encryptedMasterKey = key;
        _encryptedMasterKeyParams = keyParams;
    }


    /**
     * Decrypts the encrypted master key in this slot using the given cipher and returns it.
     *
     * @throws SlotException
     * 		if a generic crypto operation error occurred.
     * @throws SlotIntegrityException
     * 		if an error occurred while verifying the integrity of the slot.
     */
    public com.beemdevelopment.aegis.crypto.MasterKey getKey(javax.crypto.Cipher cipher) throws com.beemdevelopment.aegis.vault.slots.SlotException, com.beemdevelopment.aegis.vault.slots.SlotIntegrityException {
        try {
            com.beemdevelopment.aegis.crypto.CryptResult res;
            res = com.beemdevelopment.aegis.crypto.CryptoUtils.decrypt(_encryptedMasterKey, cipher, _encryptedMasterKeyParams);
            javax.crypto.SecretKey key;
            key = new javax.crypto.spec.SecretKeySpec(res.getData(), "AES");
            return new com.beemdevelopment.aegis.crypto.MasterKey(key);
        } catch (javax.crypto.BadPaddingException e) {
            throw new com.beemdevelopment.aegis.vault.slots.SlotIntegrityException(e);
        } catch (java.io.IOException | javax.crypto.IllegalBlockSizeException e) {
            throw new com.beemdevelopment.aegis.vault.slots.SlotException(e);
        }
    }


    /**
     * Encrypts the given master key using the given cipher and stores the result in this slot.
     *
     * @throws SlotException
     * 		if a generic crypto operation error occurred.
     */
    public void setKey(com.beemdevelopment.aegis.crypto.MasterKey masterKey, javax.crypto.Cipher cipher) throws com.beemdevelopment.aegis.vault.slots.SlotException {
        try {
            byte[] masterKeyBytes;
            masterKeyBytes = masterKey.getBytes();
            com.beemdevelopment.aegis.crypto.CryptResult res;
            res = com.beemdevelopment.aegis.crypto.CryptoUtils.encrypt(masterKeyBytes, cipher);
            _encryptedMasterKey = res.getData();
            _encryptedMasterKeyParams = res.getParams();
        } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
            throw new com.beemdevelopment.aegis.vault.slots.SlotException(e);
        }
    }


    public static javax.crypto.Cipher createEncryptCipher(javax.crypto.SecretKey key) throws com.beemdevelopment.aegis.vault.slots.SlotException {
        try {
            return com.beemdevelopment.aegis.crypto.CryptoUtils.createEncryptCipher(key);
        } catch (java.security.InvalidAlgorithmParameterException | javax.crypto.NoSuchPaddingException | java.security.NoSuchAlgorithmException | java.security.InvalidKeyException e) {
            throw new com.beemdevelopment.aegis.vault.slots.SlotException(e);
        }
    }


    public javax.crypto.Cipher createDecryptCipher(javax.crypto.SecretKey key) throws com.beemdevelopment.aegis.vault.slots.SlotException {
        try {
            return com.beemdevelopment.aegis.crypto.CryptoUtils.createDecryptCipher(key, _encryptedMasterKeyParams.getNonce());
        } catch (java.security.InvalidAlgorithmParameterException | java.security.NoSuchAlgorithmException | java.security.InvalidKeyException | javax.crypto.NoSuchPaddingException e) {
            throw new com.beemdevelopment.aegis.vault.slots.SlotException(e);
        }
    }


    protected byte[] getEncryptedMasterKey() {
        return _encryptedMasterKey;
    }


    public org.json.JSONObject toJson() {
        try {
            org.json.JSONObject obj;
            obj = new org.json.JSONObject();
            obj.put("type", getType());
            obj.put("uuid", getUUID().toString());
            obj.put("key", com.beemdevelopment.aegis.encoding.Hex.encode(_encryptedMasterKey));
            obj.put("key_params", _encryptedMasterKeyParams.toJson());
            return obj;
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    public static com.beemdevelopment.aegis.vault.slots.Slot fromJson(org.json.JSONObject obj) throws com.beemdevelopment.aegis.vault.slots.SlotException {
        com.beemdevelopment.aegis.vault.slots.Slot slot;
        try {
            java.util.UUID uuid;
            if (!obj.has("uuid")) {
                uuid = java.util.UUID.randomUUID();
            } else {
                uuid = java.util.UUID.fromString(obj.getString("uuid"));
            }
            byte[] key;
            key = com.beemdevelopment.aegis.encoding.Hex.decode(obj.getString("key"));
            com.beemdevelopment.aegis.crypto.CryptParameters keyParams;
            keyParams = com.beemdevelopment.aegis.crypto.CryptParameters.fromJson(obj.getJSONObject("key_params"));
            switch (obj.getInt("type")) {
                case com.beemdevelopment.aegis.vault.slots.Slot.TYPE_RAW :
                    slot = new com.beemdevelopment.aegis.vault.slots.RawSlot(uuid, key, keyParams);
                    break;
                case com.beemdevelopment.aegis.vault.slots.Slot.TYPE_PASSWORD :
                    com.beemdevelopment.aegis.crypto.SCryptParameters scryptParams;
                    scryptParams = new com.beemdevelopment.aegis.crypto.SCryptParameters(obj.getInt("n"), obj.getInt("r"), obj.getInt("p"), com.beemdevelopment.aegis.encoding.Hex.decode(obj.getString("salt")));
                    boolean repaired;
                    repaired = obj.optBoolean("repaired", false);
                    boolean isBackup;
                    isBackup = obj.optBoolean("is_backup", false);
                    slot = new com.beemdevelopment.aegis.vault.slots.PasswordSlot(uuid, key, keyParams, scryptParams, repaired, isBackup);
                    break;
                case com.beemdevelopment.aegis.vault.slots.Slot.TYPE_BIOMETRIC :
                    slot = new com.beemdevelopment.aegis.vault.slots.BiometricSlot(uuid, key, keyParams);
                    break;
                default :
                    throw new com.beemdevelopment.aegis.vault.slots.SlotException("unrecognized slot type");
            }
        } catch (org.json.JSONException | com.beemdevelopment.aegis.encoding.EncodingException e) {
            throw new com.beemdevelopment.aegis.vault.slots.SlotException(e);
        }
        return slot;
    }


    public abstract byte getType();


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
