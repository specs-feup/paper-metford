package com.beemdevelopment.aegis.importers;
import androidx.appcompat.app.AlertDialog;
import com.beemdevelopment.aegis.encoding.EncodingException;
import javax.crypto.IllegalBlockSizeException;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import org.xmlpull.v1.XmlPullParser;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.vault.VaultEntry;
import javax.crypto.spec.SecretKeySpec;
import android.content.pm.PackageManager;
import org.xmlpull.v1.XmlPullParserException;
import java.security.MessageDigest;
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
import com.beemdevelopment.aegis.util.PreferenceParser;
import com.beemdevelopment.aegis.util.IOUtils;
import javax.crypto.spec.IvParameterSpec;
import android.util.Xml;
import org.json.JSONObject;
import javax.crypto.Cipher;
import com.beemdevelopment.aegis.encoding.Base32;
import com.beemdevelopment.aegis.encoding.Hex;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TotpAuthenticatorImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String _subPath = "shared_prefs/TOTP_Authenticator_Preferences.xml";

    private static final java.lang.String _pkgName = "com.authenticator.authservice2";

    // WARNING: DON'T DO THIS IN YOUR OWN CODE
    // this is a hardcoded password and nonce, used solely to decrypt TOTP Authenticator backups
    private static final char[] PASSWORD = "TotpAuthenticator".toCharArray();

    private static final byte[] IV = new byte[]{ 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };

    private static final java.lang.String PREF_KEY = "STATIC_TOTP_CODES_LIST";

    public TotpAuthenticatorImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() throws android.content.pm.PackageManager.NameNotFoundException {
        return getAppPath(com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter._pkgName, com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter._subPath);
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.DatabaseImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try {
            if (isInternal) {
                org.xmlpull.v1.XmlPullParser parser;
                parser = android.util.Xml.newPullParser();
                parser.setFeature(org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(stream, null);
                parser.nextTag();
                java.lang.String data;
                data = null;
                for (com.beemdevelopment.aegis.util.PreferenceParser.XmlEntry entry : com.beemdevelopment.aegis.util.PreferenceParser.parse(parser)) {
                    if (entry.Name.equals(com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.PREF_KEY)) {
                        data = entry.Value;
                    }
                }
                if (data == null) {
                    throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(java.lang.String.format("Key %s not found in shared preference file", com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.PREF_KEY));
                }
                java.util.List<org.json.JSONObject> entries;
                entries = com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.parse(data);
                return new com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.DecryptedState(entries);
            } else {
                byte[] base64;
                base64 = com.beemdevelopment.aegis.util.IOUtils.readAll(stream);
                byte[] cipherText;
                cipherText = com.beemdevelopment.aegis.encoding.Base64.decode(base64);
                return new com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.EncryptedState(cipherText);
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException | org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
    }


    private static java.util.List<org.json.JSONObject> parse(java.lang.String data) throws org.json.JSONException {
        org.json.JSONArray array;
        array = new org.json.JSONArray(data);
        java.util.List<org.json.JSONObject> entries;
        entries = new java.util.ArrayList<>();
        for (int i = 0; i < array.length(); ++i) {
            org.json.JSONObject obj;
            obj = array.getJSONObject(i);
            entries.add(obj);
        }
        return entries;
    }


    public static class EncryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private byte[] _data;

        public EncryptedState(byte[] data) {
            super(true);
            _data = data;
        }


        protected com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.DecryptedState decrypt(char[] password) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            try {
                // WARNING: DON'T DO THIS IN YOUR OWN CODE
                // this is not a secure way to derive a key from a password
                java.security.MessageDigest hash;
                hash = java.security.MessageDigest.getInstance("SHA-256");
                byte[] keyBytes;
                keyBytes = hash.digest(com.beemdevelopment.aegis.crypto.CryptoUtils.toBytes(password));
                javax.crypto.SecretKey key;
                key = new javax.crypto.spec.SecretKeySpec(keyBytes, "AES");
                javax.crypto.Cipher cipher;
                cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
                javax.crypto.spec.IvParameterSpec spec;
                spec = new javax.crypto.spec.IvParameterSpec(com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.IV);
                cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key, spec);
                byte[] bytes;
                bytes = cipher.doFinal(_data);
                org.json.JSONObject obj;
                obj = new org.json.JSONObject(new java.lang.String(bytes, java.nio.charset.StandardCharsets.UTF_8));
                org.json.JSONArray keys;
                keys = obj.names();
                java.util.List<org.json.JSONObject> entries;
                entries = new java.util.ArrayList<>();
                if ((keys != null) && (keys.length() > 0)) {
                    entries = com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.parse(((java.lang.String) (keys.get(0))));
                }
                return new com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.DecryptedState(entries);
            } catch (java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | org.json.JSONException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            }
        }


        @java.lang.Override
        public void decrypt(android.content.Context context, com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener listener) {
            switch(MUID_STATIC) {
                // TotpAuthenticatorImporter_0_BuggyGUIListenerOperatorMutator
                case 46: {
                    com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setMessage(com.beemdevelopment.aegis.R.string.choose_totpauth_importer).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, null).setNegativeButton(com.beemdevelopment.aegis.R.string.no, (android.content.DialogInterface dialog,int which) -> {
                        decrypt(com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.PASSWORD, listener);
                    }).create());
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // TotpAuthenticatorImporter_1_BuggyGUIListenerOperatorMutator
                    case 1046: {
                        com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setMessage(com.beemdevelopment.aegis.R.string.choose_totpauth_importer).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, (android.content.DialogInterface dialog,int which) -> {
                            com.beemdevelopment.aegis.ui.dialogs.Dialogs.showPasswordInputDialog(context, ( password) -> {
                                decrypt(password, listener);
                            }, (android.content.DialogInterface dialog1) -> listener.onCanceled());
                        }).setNegativeButton(com.beemdevelopment.aegis.R.string.no, null).create());
                        break;
                    }
                    default: {
                    com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setMessage(com.beemdevelopment.aegis.R.string.choose_totpauth_importer).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, (android.content.DialogInterface dialog,int which) -> {
                        com.beemdevelopment.aegis.ui.dialogs.Dialogs.showPasswordInputDialog(context, ( password) -> {
                            decrypt(password, listener);
                        }, (android.content.DialogInterface dialog1) -> listener.onCanceled());
                    }).setNegativeButton(com.beemdevelopment.aegis.R.string.no, (android.content.DialogInterface dialog,int which) -> {
                        decrypt(com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.PASSWORD, listener);
                    }).create());
                    break;
                }
            }
            break;
        }
    }
}


private void decrypt(char[] password, com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener listener) {
    try {
        com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.DecryptedState state;
        state = decrypt(password);
        listener.onStateDecrypted(state);
    } catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
        listener.onError(e);
    }
}

}

public static class DecryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
private java.util.List<org.json.JSONObject> _objs;

private DecryptedState(java.util.List<org.json.JSONObject> objs) {
    super(false);
    _objs = objs;
}


@java.lang.Override
public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() {
    com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
    result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
    for (org.json.JSONObject obj : _objs) {
        try {
            com.beemdevelopment.aegis.vault.VaultEntry entry;
            entry = com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.DecryptedState.convertEntry(obj);
            result.addEntry(entry);
        } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
            result.addError(e);
        }
    }
    return result;
}


private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(org.json.JSONObject obj) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
    try {
        int base;
        base = obj.getInt("base");
        java.lang.String secretString;
        secretString = obj.getString("key");
        byte[] secret;
        switch (base) {
            case 16 :
                secret = com.beemdevelopment.aegis.encoding.Hex.decode(secretString);
                break;
            case 32 :
                secret = com.beemdevelopment.aegis.encoding.Base32.decode(secretString);
                break;
            case 64 :
                secret = com.beemdevelopment.aegis.encoding.Base64.decode(secretString);
                break;
            default :
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(java.lang.String.format("Unsupported secret encoding: base %d", base), obj.toString());
        }
        com.beemdevelopment.aegis.otp.TotpInfo info;
        info = new com.beemdevelopment.aegis.otp.TotpInfo(secret);
        java.lang.String name;
        name = obj.optString("name");
        java.lang.String issuer;
        issuer = obj.optString("issuer");
        return new com.beemdevelopment.aegis.vault.VaultEntry(info, name, issuer);
    } catch (org.json.JSONException | com.beemdevelopment.aegis.otp.OtpInfoException | com.beemdevelopment.aegis.encoding.EncodingException e) {
        throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, obj.toString());
    }
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
