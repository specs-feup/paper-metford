package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.encoding.EncodingException;
import java.security.spec.KeySpec;
import javax.crypto.IllegalBlockSizeException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.PBEKeySpec;
import org.xmlpull.v1.XmlPullParser;
import com.topjohnwu.superuser.io.SuFileInputStream;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.topjohnwu.superuser.Shell;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.util.JsonUtils;
import com.beemdevelopment.aegis.vault.VaultEntry;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import android.content.pm.PackageManager;
import org.xmlpull.v1.XmlPullParserException;
import com.beemdevelopment.aegis.encoding.Base64;
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
public class AuthyImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String _subPath = "shared_prefs";

    private static final java.lang.String _pkgName = "com.authy.authy";

    private static final java.lang.String _authFilename = "com.authy.storage.tokens.authenticator";

    private static final java.lang.String _authyFilename = "com.authy.storage.tokens.authy";

    private static final int ITERATIONS = 1000;

    private static final int KEY_SIZE = 256;

    private static final byte[] IV = new byte[]{ 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };

    public AuthyImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() throws android.content.pm.PackageManager.NameNotFoundException {
        return getAppPath(com.beemdevelopment.aegis.importers.AuthyImporter._pkgName, com.beemdevelopment.aegis.importers.AuthyImporter._subPath);
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.DatabaseImporter.State readFromApp(com.topjohnwu.superuser.Shell shell) throws android.content.pm.PackageManager.NameNotFoundException, com.beemdevelopment.aegis.importers.DatabaseImporterException {
        com.topjohnwu.superuser.io.SuFile path;
        path = getAppPath();
        path.setShell(shell);
        org.json.JSONArray array;
        org.json.JSONArray authyArray;
        try {
            array = readFile(new com.topjohnwu.superuser.io.SuFile(path, java.lang.String.format("%s.xml", com.beemdevelopment.aegis.importers.AuthyImporter._authFilename)), java.lang.String.format("%s.key", com.beemdevelopment.aegis.importers.AuthyImporter._authFilename));
            authyArray = readFile(new com.topjohnwu.superuser.io.SuFile(path, java.lang.String.format("%s.xml", com.beemdevelopment.aegis.importers.AuthyImporter._authyFilename)), java.lang.String.format("%s.key", com.beemdevelopment.aegis.importers.AuthyImporter._authyFilename));
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
        try {
            for (int i = 0; i < authyArray.length(); i++) {
                array.put(authyArray.getJSONObject(i));
            }
        } catch (org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
        return read(array);
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.DatabaseImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try {
            org.xmlpull.v1.XmlPullParser parser;
            parser = android.util.Xml.newPullParser();
            parser.setFeature(org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
            org.json.JSONArray array;
            array = new org.json.JSONArray();
            for (com.beemdevelopment.aegis.util.PreferenceParser.XmlEntry entry : com.beemdevelopment.aegis.util.PreferenceParser.parse(parser)) {
                if (entry.Name.equals(java.lang.String.format("%s.key", com.beemdevelopment.aegis.importers.AuthyImporter._authFilename)) || entry.Name.equals(java.lang.String.format("%s.key", com.beemdevelopment.aegis.importers.AuthyImporter._authyFilename))) {
                    array = new org.json.JSONArray(entry.Value);
                    break;
                }
            }
            return read(array);
        } catch (org.xmlpull.v1.XmlPullParserException | org.json.JSONException | java.io.IOException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
    }


    private com.beemdevelopment.aegis.importers.DatabaseImporter.State read(org.json.JSONArray array) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try {
            for (int i = 0; i < array.length(); i++) {
                org.json.JSONObject obj;
                obj = array.getJSONObject(i);
                if ((!obj.has("decryptedSecret")) && (!obj.has("secretSeed"))) {
                    return new com.beemdevelopment.aegis.importers.AuthyImporter.EncryptedState(array);
                }
            }
        } catch (org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
        return new com.beemdevelopment.aegis.importers.AuthyImporter.DecryptedState(array);
    }


    private org.json.JSONArray readFile(com.topjohnwu.superuser.io.SuFile file, java.lang.String key) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        try (java.io.InputStream inStream = com.topjohnwu.superuser.io.SuFileInputStream.open(file)) {
            org.xmlpull.v1.XmlPullParser parser;
            parser = android.util.Xml.newPullParser();
            parser.setFeature(org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inStream, null);
            parser.nextTag();
            for (com.beemdevelopment.aegis.util.PreferenceParser.XmlEntry entry : com.beemdevelopment.aegis.util.PreferenceParser.parse(parser)) {
                if (entry.Name.equals(key)) {
                    return new org.json.JSONArray(entry.Value);
                }
            }
        } catch (org.json.JSONException ignored) {
        }
        return new org.json.JSONArray();
    }


    public static class EncryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private org.json.JSONArray _array;

        private EncryptedState(org.json.JSONArray array) {
            super(true);
            _array = array;
        }


        protected com.beemdevelopment.aegis.importers.AuthyImporter.DecryptedState decrypt(char[] password) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            try {
                for (int i = 0; i < _array.length(); i++) {
                    org.json.JSONObject obj;
                    obj = _array.getJSONObject(i);
                    java.lang.String secretString;
                    secretString = com.beemdevelopment.aegis.util.JsonUtils.optString(obj, "encryptedSecret");
                    if (secretString == null) {
                        continue;
                    }
                    byte[] encryptedSecret;
                    encryptedSecret = com.beemdevelopment.aegis.encoding.Base64.decode(secretString);
                    byte[] salt;
                    salt = obj.getString("salt").getBytes(java.nio.charset.StandardCharsets.UTF_8);
                    javax.crypto.SecretKeyFactory factory;
                    factory = javax.crypto.SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                    java.security.spec.KeySpec spec;
                    spec = new javax.crypto.spec.PBEKeySpec(password, salt, com.beemdevelopment.aegis.importers.AuthyImporter.ITERATIONS, com.beemdevelopment.aegis.importers.AuthyImporter.KEY_SIZE);
                    javax.crypto.SecretKey key;
                    key = factory.generateSecret(spec);
                    key = new javax.crypto.spec.SecretKeySpec(key.getEncoded(), "AES");
                    javax.crypto.Cipher cipher;
                    cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
                    javax.crypto.spec.IvParameterSpec ivSpec;
                    ivSpec = new javax.crypto.spec.IvParameterSpec(com.beemdevelopment.aegis.importers.AuthyImporter.IV);
                    cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key, ivSpec);
                    byte[] secret;
                    secret = cipher.doFinal(encryptedSecret);
                    obj.remove("encryptedSecret");
                    obj.remove("salt");
                    obj.put("decryptedSecret", new java.lang.String(secret, java.nio.charset.StandardCharsets.UTF_8));
                }
                return new com.beemdevelopment.aegis.importers.AuthyImporter.DecryptedState(_array);
            } catch (org.json.JSONException | com.beemdevelopment.aegis.encoding.EncodingException | java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | javax.crypto.NoSuchPaddingException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            }
        }


        @java.lang.Override
        public void decrypt(android.content.Context context, com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener listener) {
            com.beemdevelopment.aegis.ui.dialogs.Dialogs.showPasswordInputDialog(context, com.beemdevelopment.aegis.R.string.enter_password_authy_message, ( password) -> {
                try {
                    com.beemdevelopment.aegis.importers.AuthyImporter.DecryptedState state;
                    state = decrypt(password);
                    listener.onStateDecrypted(state);
                } catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
                    listener.onError(e);
                }
            }, (android.content.DialogInterface dialog1) -> listener.onCanceled());
        }

    }

    public static class DecryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private org.json.JSONArray _array;

        private DecryptedState(org.json.JSONArray array) {
            super(false);
            _array = array;
        }


        @java.lang.Override
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
            try {
                for (int i = 0; i < _array.length(); i++) {
                    org.json.JSONObject entryObj;
                    entryObj = _array.getJSONObject(i);
                    try {
                        com.beemdevelopment.aegis.vault.VaultEntry entry;
                        entry = com.beemdevelopment.aegis.importers.AuthyImporter.DecryptedState.convertEntry(entryObj);
                        result.addEntry(entry);
                    } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                        result.addError(e);
                    }
                }
            } catch (org.json.JSONException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            }
            return result;
        }


        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(org.json.JSONObject entry) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                com.beemdevelopment.aegis.importers.AuthyImporter.AuthyEntryInfo authyEntryInfo;
                authyEntryInfo = new com.beemdevelopment.aegis.importers.AuthyImporter.AuthyEntryInfo();
                authyEntryInfo.OriginalName = com.beemdevelopment.aegis.util.JsonUtils.optString(entry, "originalName");
                authyEntryInfo.OriginalIssuer = com.beemdevelopment.aegis.util.JsonUtils.optString(entry, "originalIssuer");
                authyEntryInfo.AccountType = com.beemdevelopment.aegis.util.JsonUtils.optString(entry, "accountType");
                authyEntryInfo.Name = entry.optString("name");
                boolean isAuthy;
                isAuthy = entry.has("secretSeed");
                com.beemdevelopment.aegis.importers.AuthyImporter.DecryptedState.sanitizeEntryInfo(authyEntryInfo, isAuthy);
                byte[] secret;
                if (isAuthy) {
                    secret = com.beemdevelopment.aegis.encoding.Hex.decode(entry.getString("secretSeed"));
                } else {
                    secret = com.beemdevelopment.aegis.encoding.Base32.decode(entry.getString("decryptedSecret"));
                }
                int digits;
                digits = entry.getInt("digits");
                com.beemdevelopment.aegis.otp.OtpInfo info;
                info = new com.beemdevelopment.aegis.otp.TotpInfo(secret, com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_ALGORITHM, digits, isAuthy ? 10 : com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD);
                return new com.beemdevelopment.aegis.vault.VaultEntry(info, authyEntryInfo.Name, authyEntryInfo.Issuer);
            } catch (com.beemdevelopment.aegis.otp.OtpInfoException | org.json.JSONException | com.beemdevelopment.aegis.encoding.EncodingException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, entry.toString());
            }
        }


        private static void sanitizeEntryInfo(com.beemdevelopment.aegis.importers.AuthyImporter.AuthyEntryInfo info, boolean isAuthy) {
            if (!isAuthy) {
                java.lang.String separator;
                separator = "";
                if (info.OriginalIssuer != null) {
                    info.Issuer = info.OriginalIssuer;
                } else if ((info.OriginalName != null) && info.OriginalName.contains(":")) {
                    info.Issuer = info.OriginalName.substring(0, info.OriginalName.indexOf(":"));
                    separator = ":";
                } else if (info.Name.contains(" - ")) {
                    info.Issuer = info.Name.substring(0, info.Name.indexOf(" - "));
                    separator = " - ";
                } else {
                    info.Issuer = info.AccountType.substring(0, 1).toUpperCase() + info.AccountType.substring(1);
                }
                info.Name = info.Name.replace(info.Issuer + separator, "");
            } else {
                info.Issuer = info.Name;
                info.Name = "";
            }
            if (info.Name.startsWith(": ")) {
                info.Name = info.Name.substring(2);
            }
        }

    }

    private static class AuthyEntryInfo {
        java.lang.String OriginalName;

        java.lang.String OriginalIssuer;

        java.lang.String AccountType;

        java.lang.String Issuer;

        java.lang.String Name;
    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
