package com.beemdevelopment.aegis.vault;
import com.beemdevelopment.aegis.vault.slots.SlotListException;
import com.beemdevelopment.aegis.encoding.EncodingException;
import org.json.JSONException;
import java.nio.charset.StandardCharsets;
import com.beemdevelopment.aegis.crypto.CryptParameters;
import com.beemdevelopment.aegis.crypto.MasterKeyException;
import org.json.JSONObject;
import com.beemdevelopment.aegis.crypto.CryptResult;
import com.beemdevelopment.aegis.encoding.Base64;
import com.beemdevelopment.aegis.vault.slots.SlotList;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VaultFile {
    static final int MUID_STATIC = getMUID();
    public static final byte VERSION = 1;

    private java.lang.Object _content;

    private com.beemdevelopment.aegis.vault.VaultFile.Header _header;

    public VaultFile() {
    }


    private VaultFile(java.lang.Object content, com.beemdevelopment.aegis.vault.VaultFile.Header header) {
        _content = content;
        _header = header;
    }


    public com.beemdevelopment.aegis.vault.VaultFile.Header getHeader() {
        return _header;
    }


    public boolean isEncrypted() {
        return !_header.isEmpty();
    }


    public org.json.JSONObject toJson() {
        try {
            org.json.JSONObject obj;
            obj = new org.json.JSONObject();
            obj.put("version", com.beemdevelopment.aegis.vault.VaultFile.VERSION);
            obj.put("header", _header.toJson());
            obj.put("db", _content);
            return obj;
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    public byte[] toBytes() {
        org.json.JSONObject obj;
        obj = toJson();
        try {
            java.lang.String string;
            string = obj.toString(4);
            return string.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    public static com.beemdevelopment.aegis.vault.VaultFile fromJson(org.json.JSONObject obj) throws com.beemdevelopment.aegis.vault.VaultFileException {
        try {
            if (obj.getInt("version") > com.beemdevelopment.aegis.vault.VaultFile.VERSION) {
                throw new com.beemdevelopment.aegis.vault.VaultFileException("unsupported version");
            }
            com.beemdevelopment.aegis.vault.VaultFile.Header header;
            header = com.beemdevelopment.aegis.vault.VaultFile.Header.fromJson(obj.getJSONObject("header"));
            if (!header.isEmpty()) {
                return new com.beemdevelopment.aegis.vault.VaultFile(obj.getString("db"), header);
            }
            return new com.beemdevelopment.aegis.vault.VaultFile(obj.getJSONObject("db"), header);
        } catch (org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.vault.VaultFileException(e);
        }
    }


    public static com.beemdevelopment.aegis.vault.VaultFile fromBytes(byte[] data) throws com.beemdevelopment.aegis.vault.VaultFileException {
        try {
            org.json.JSONObject obj;
            obj = new org.json.JSONObject(new java.lang.String(data, java.nio.charset.StandardCharsets.UTF_8));
            return com.beemdevelopment.aegis.vault.VaultFile.fromJson(obj);
        } catch (org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.vault.VaultFileException(e);
        }
    }


    public org.json.JSONObject getContent() {
        return ((org.json.JSONObject) (_content));
    }


    public org.json.JSONObject getContent(com.beemdevelopment.aegis.vault.VaultFileCredentials creds) throws com.beemdevelopment.aegis.vault.VaultFileException {
        try {
            byte[] bytes;
            bytes = com.beemdevelopment.aegis.encoding.Base64.decode(((java.lang.String) (_content)));
            com.beemdevelopment.aegis.crypto.CryptResult result;
            result = creds.decrypt(bytes, _header.getParams());
            return new org.json.JSONObject(new java.lang.String(result.getData(), java.nio.charset.StandardCharsets.UTF_8));
        } catch (com.beemdevelopment.aegis.crypto.MasterKeyException | org.json.JSONException | com.beemdevelopment.aegis.encoding.EncodingException e) {
            throw new com.beemdevelopment.aegis.vault.VaultFileException(e);
        }
    }


    public void setContent(org.json.JSONObject obj) {
        _content = obj;
        _header = new com.beemdevelopment.aegis.vault.VaultFile.Header(null, null);
    }


    public void setContent(org.json.JSONObject obj, com.beemdevelopment.aegis.vault.VaultFileCredentials creds) throws com.beemdevelopment.aegis.vault.VaultFileException {
        try {
            java.lang.String string;
            string = obj.toString(4);
            byte[] vaultBytes;
            vaultBytes = string.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            com.beemdevelopment.aegis.crypto.CryptResult result;
            result = creds.encrypt(vaultBytes);
            _content = com.beemdevelopment.aegis.encoding.Base64.encode(result.getData());
            _header = new com.beemdevelopment.aegis.vault.VaultFile.Header(creds.getSlots(), result.getParams());
        } catch (com.beemdevelopment.aegis.crypto.MasterKeyException | org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.vault.VaultFileException(e);
        }
    }


    /**
     * Returns a copy of this VaultFile that's suitable for exporting.
     * In case there's a backup password slot, any regular password slots are stripped.
     */
    public com.beemdevelopment.aegis.vault.VaultFile exportable() {
        if (!isEncrypted()) {
            return this;
        }
        return new com.beemdevelopment.aegis.vault.VaultFile(_content, new com.beemdevelopment.aegis.vault.VaultFile.Header(getHeader().getSlots().exportable(), getHeader().getParams()));
    }


    public static class Header {
        private com.beemdevelopment.aegis.vault.slots.SlotList _slots;

        private com.beemdevelopment.aegis.crypto.CryptParameters _params;

        public Header(com.beemdevelopment.aegis.vault.slots.SlotList slots, com.beemdevelopment.aegis.crypto.CryptParameters params) {
            _slots = slots;
            _params = params;
        }


        public static com.beemdevelopment.aegis.vault.VaultFile.Header fromJson(org.json.JSONObject obj) throws com.beemdevelopment.aegis.vault.VaultFileException {
            if (obj.isNull("slots") && obj.isNull("params")) {
                return new com.beemdevelopment.aegis.vault.VaultFile.Header(null, null);
            }
            try {
                com.beemdevelopment.aegis.vault.slots.SlotList slots;
                slots = com.beemdevelopment.aegis.vault.slots.SlotList.fromJson(obj.getJSONArray("slots"));
                com.beemdevelopment.aegis.crypto.CryptParameters params;
                params = com.beemdevelopment.aegis.crypto.CryptParameters.fromJson(obj.getJSONObject("params"));
                return new com.beemdevelopment.aegis.vault.VaultFile.Header(slots, params);
            } catch (com.beemdevelopment.aegis.vault.slots.SlotListException | org.json.JSONException | com.beemdevelopment.aegis.encoding.EncodingException e) {
                throw new com.beemdevelopment.aegis.vault.VaultFileException(e);
            }
        }


        public org.json.JSONObject toJson() {
            try {
                org.json.JSONObject obj;
                obj = new org.json.JSONObject();
                obj.put("slots", _slots != null ? _slots.toJson() : org.json.JSONObject.NULL);
                obj.put("params", _params != null ? _params.toJson() : org.json.JSONObject.NULL);
                return obj;
            } catch (org.json.JSONException e) {
                throw new java.lang.RuntimeException(e);
            }
        }


        public com.beemdevelopment.aegis.vault.slots.SlotList getSlots() {
            return _slots;
        }


        public com.beemdevelopment.aegis.crypto.CryptParameters getParams() {
            return _params;
        }


        public boolean isEmpty() {
            return (_slots == null) && (_params == null);
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
