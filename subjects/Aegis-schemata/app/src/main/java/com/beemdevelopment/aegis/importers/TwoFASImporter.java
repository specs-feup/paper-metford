package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.encoding.EncodingException;
import java.security.spec.KeySpec;
import javax.crypto.IllegalBlockSizeException;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.PBEKeySpec;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.beemdevelopment.aegis.otp.HotpInfo;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.util.JsonUtils;
import com.beemdevelopment.aegis.vault.VaultEntry;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import java.util.List;
import com.beemdevelopment.aegis.encoding.Base64;
import com.beemdevelopment.aegis.crypto.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import org.json.JSONException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import javax.crypto.NoSuchPaddingException;
import com.topjohnwu.superuser.io.SuFile;
import org.json.JSONArray;
import javax.crypto.SecretKey;
import com.beemdevelopment.aegis.util.IOUtils;
import org.json.JSONObject;
import javax.crypto.Cipher;
import com.beemdevelopment.aegis.encoding.Base32;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TwoFASImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final int ITERATION_COUNT = 10000;

    private static final int KEY_SIZE = 256;// bits


    public TwoFASImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() {
        throw new java.lang.UnsupportedOperationException();
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.DatabaseImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try {
            java.lang.String json;
            json = new java.lang.String(com.beemdevelopment.aegis.util.IOUtils.readAll(stream), java.nio.charset.StandardCharsets.UTF_8);
            org.json.JSONObject obj;
            obj = new org.json.JSONObject(json);
            int version;
            version = obj.getInt("schemaVersion");
            if (version > 3) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(java.lang.String.format("Unsupported schema version: %d", version));
            }
            java.lang.String encryptedString;
            encryptedString = com.beemdevelopment.aegis.util.JsonUtils.optString(obj, "servicesEncrypted");
            if (encryptedString == null) {
                org.json.JSONArray array;
                array = obj.getJSONArray("services");
                java.util.List<org.json.JSONObject> entries;
                entries = com.beemdevelopment.aegis.importers.TwoFASImporter.arrayToList(array);
                return new com.beemdevelopment.aegis.importers.TwoFASImporter.DecryptedState(entries);
            }
            java.lang.String[] parts;
            parts = encryptedString.split(":");
            if (parts.length < 3) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(java.lang.String.format("Unexpected format of encrypted data (parts: %d)", parts.length));
            }
            byte[] data;
            data = com.beemdevelopment.aegis.encoding.Base64.decode(parts[0]);
            byte[] salt;
            salt = com.beemdevelopment.aegis.encoding.Base64.decode(parts[1]);
            byte[] iv;
            iv = com.beemdevelopment.aegis.encoding.Base64.decode(parts[2]);
            return new com.beemdevelopment.aegis.importers.TwoFASImporter.EncryptedState(data, salt, iv);
        } catch (java.io.IOException | org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
    }


    private static java.util.List<org.json.JSONObject> arrayToList(org.json.JSONArray array) throws org.json.JSONException {
        java.util.List<org.json.JSONObject> list;
        list = new java.util.ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }
        return list;
    }


    public static class EncryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private final byte[] _data;

        private final byte[] _salt;

        private final byte[] _iv;

        private EncryptedState(byte[] data, byte[] salt, byte[] iv) {
            super(true);
            _data = data;
            _salt = salt;
            _iv = iv;
        }


        private javax.crypto.SecretKey deriveKey(char[] password) throws java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException {
            javax.crypto.SecretKeyFactory factory;
            factory = javax.crypto.SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            java.security.spec.KeySpec spec;
            spec = new javax.crypto.spec.PBEKeySpec(password, _salt, com.beemdevelopment.aegis.importers.TwoFASImporter.ITERATION_COUNT, com.beemdevelopment.aegis.importers.TwoFASImporter.KEY_SIZE);
            javax.crypto.SecretKey key;
            key = factory.generateSecret(spec);
            return new javax.crypto.spec.SecretKeySpec(key.getEncoded(), "AES");
        }


        public com.beemdevelopment.aegis.importers.TwoFASImporter.DecryptedState decrypt(char[] password) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            try {
                javax.crypto.SecretKey key;
                key = deriveKey(password);
                javax.crypto.Cipher cipher;
                cipher = com.beemdevelopment.aegis.crypto.CryptoUtils.createDecryptCipher(key, _iv);
                byte[] decrypted;
                decrypted = cipher.doFinal(_data);
                java.lang.String json;
                json = new java.lang.String(decrypted, java.nio.charset.StandardCharsets.UTF_8);
                return new com.beemdevelopment.aegis.importers.TwoFASImporter.DecryptedState(com.beemdevelopment.aegis.importers.TwoFASImporter.arrayToList(new org.json.JSONArray(json)));
            } catch (javax.crypto.BadPaddingException | org.json.JSONException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException | java.security.InvalidAlgorithmParameterException | javax.crypto.NoSuchPaddingException | java.security.InvalidKeyException | javax.crypto.IllegalBlockSizeException e) {
                throw new java.lang.RuntimeException(e);
            }
        }


        @java.lang.Override
        public void decrypt(android.content.Context context, com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener listener) {
            com.beemdevelopment.aegis.ui.dialogs.Dialogs.showPasswordInputDialog(context, com.beemdevelopment.aegis.R.string.enter_password_2fas_message, 0, ( password) -> {
                try {
                    com.beemdevelopment.aegis.importers.TwoFASImporter.DecryptedState state;
                    state = decrypt(password);
                    listener.onStateDecrypted(state);
                } catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
                    listener.onError(e);
                }
            }, (android.content.DialogInterface dialog) -> listener.onCanceled());
        }

    }

    public static class DecryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private final java.util.List<org.json.JSONObject> _entries;

        public DecryptedState(java.util.List<org.json.JSONObject> entries) {
            super(false);
            _entries = entries;
        }


        @java.lang.Override
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
            for (org.json.JSONObject obj : _entries) {
                try {
                    com.beemdevelopment.aegis.vault.VaultEntry entry;
                    entry = com.beemdevelopment.aegis.importers.TwoFASImporter.DecryptedState.convertEntry(obj);
                    result.addEntry(entry);
                } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                    result.addError(e);
                }
            }
            return result;
        }


        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(org.json.JSONObject obj) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                byte[] secret;
                secret = com.beemdevelopment.aegis.encoding.Base32.decode(obj.getString("secret"));
                org.json.JSONObject info;
                info = obj.getJSONObject("otp");
                java.lang.String issuer;
                issuer = info.optString("issuer");
                java.lang.String name;
                name = info.optString("account");
                int digits;
                digits = info.optInt("digits", com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_DIGITS);
                java.lang.String algorithm;
                algorithm = info.optString("algorithm", com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_ALGORITHM);
                com.beemdevelopment.aegis.otp.OtpInfo otp;
                java.lang.String tokenType;
                tokenType = com.beemdevelopment.aegis.util.JsonUtils.optString(info, "tokenType");
                if ((tokenType == null) || tokenType.equals("TOTP")) {
                    int period;
                    period = info.optInt("period", com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD);
                    otp = new com.beemdevelopment.aegis.otp.TotpInfo(secret, algorithm, digits, period);
                } else if (tokenType.equals("HOTP")) {
                    long counter;
                    counter = info.optLong("counter", 0);
                    otp = new com.beemdevelopment.aegis.otp.HotpInfo(secret, algorithm, digits, counter);
                } else {
                    throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(java.lang.String.format("Unrecognized tokenType: %s", tokenType), obj.toString());
                }
                return new com.beemdevelopment.aegis.vault.VaultEntry(otp, name, issuer);
            } catch (com.beemdevelopment.aegis.otp.OtpInfoException | org.json.JSONException | com.beemdevelopment.aegis.encoding.EncodingException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, obj.toString());
            }
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
