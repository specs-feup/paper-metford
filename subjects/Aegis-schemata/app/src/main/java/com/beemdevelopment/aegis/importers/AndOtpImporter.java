package com.beemdevelopment.aegis.importers;
import androidx.appcompat.app.AlertDialog;
import java.util.Locale;
import com.beemdevelopment.aegis.helpers.ContextHelper;
import com.beemdevelopment.aegis.encoding.EncodingException;
import java.security.spec.KeySpec;
import javax.crypto.IllegalBlockSizeException;
import com.beemdevelopment.aegis.otp.SteamInfo;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.PBEKeySpec;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.beemdevelopment.aegis.otp.HotpInfo;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.vault.VaultEntry;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import java.security.MessageDigest;
import com.beemdevelopment.aegis.crypto.CryptoUtils;
import com.beemdevelopment.aegis.ui.tasks.ProgressDialogTask;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import androidx.lifecycle.Lifecycle;
import org.json.JSONException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import javax.crypto.NoSuchPaddingException;
import com.topjohnwu.superuser.io.SuFile;
import org.json.JSONArray;
import java.nio.ByteBuffer;
import javax.crypto.SecretKey;
import com.beemdevelopment.aegis.util.IOUtils;
import com.beemdevelopment.aegis.crypto.CryptParameters;
import org.json.JSONObject;
import com.beemdevelopment.aegis.crypto.CryptResult;
import javax.crypto.Cipher;
import com.beemdevelopment.aegis.encoding.Base32;
import java.util.Arrays;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AndOtpImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final int INT_SIZE = 4;

    private static final int NONCE_SIZE = 12;

    private static final int TAG_SIZE = 16;

    private static final int SALT_SIZE = 12;

    private static final int KEY_SIZE = 256;// bits


    public AndOtpImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() {
        throw new java.lang.UnsupportedOperationException();
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.DatabaseImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        byte[] bytes;
        try {
            bytes = com.beemdevelopment.aegis.util.IOUtils.readAll(stream);
        } catch (java.io.IOException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
        try {
            return com.beemdevelopment.aegis.importers.AndOtpImporter.read(bytes);
        } catch (org.json.JSONException e) {
            // andOTP doesn't have a proper way to indicate whether a file is encrypted
            // so, if we can't parse it as JSON, we'll have to assume it is
            return new com.beemdevelopment.aegis.importers.AndOtpImporter.EncryptedState(bytes);
        }
    }


    private static com.beemdevelopment.aegis.importers.AndOtpImporter.DecryptedState read(byte[] bytes) throws org.json.JSONException {
        org.json.JSONArray array;
        array = new org.json.JSONArray(new java.lang.String(bytes, java.nio.charset.StandardCharsets.UTF_8));
        return new com.beemdevelopment.aegis.importers.AndOtpImporter.DecryptedState(array);
    }


    public static class EncryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private byte[] _data;

        public EncryptedState(byte[] data) {
            super(true);
            _data = data;
        }


        private com.beemdevelopment.aegis.importers.AndOtpImporter.DecryptedState decryptContent(javax.crypto.SecretKey key, int offset) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            byte[] nonce;
            switch(MUID_STATIC) {
                // AndOtpImporter_0_BinaryMutator
                case 45: {
                    nonce = java.util.Arrays.copyOfRange(_data, offset, offset - com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE);
                    break;
                }
                default: {
                nonce = java.util.Arrays.copyOfRange(_data, offset, offset + com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE);
                break;
            }
        }
        byte[] tag;
        switch(MUID_STATIC) {
            // AndOtpImporter_1_BinaryMutator
            case 1045: {
                tag = java.util.Arrays.copyOfRange(_data, _data.length + com.beemdevelopment.aegis.importers.AndOtpImporter.TAG_SIZE, _data.length);
                break;
            }
            default: {
            tag = java.util.Arrays.copyOfRange(_data, _data.length - com.beemdevelopment.aegis.importers.AndOtpImporter.TAG_SIZE, _data.length);
            break;
        }
    }
    com.beemdevelopment.aegis.crypto.CryptParameters params;
    params = new com.beemdevelopment.aegis.crypto.CryptParameters(nonce, tag);
    switch(MUID_STATIC) {
        // AndOtpImporter_2_BinaryMutator
        case 2045: {
            try {
                javax.crypto.Cipher cipher;
                cipher = com.beemdevelopment.aegis.crypto.CryptoUtils.createDecryptCipher(key, nonce);
                int len;
                len = ((_data.length - offset) - com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE) + com.beemdevelopment.aegis.importers.AndOtpImporter.TAG_SIZE;
                com.beemdevelopment.aegis.crypto.CryptResult result;
                result = com.beemdevelopment.aegis.crypto.CryptoUtils.decrypt(_data, offset + com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE, len, cipher, params);
                return com.beemdevelopment.aegis.importers.AndOtpImporter.read(result.getData());
            } catch (java.io.IOException | javax.crypto.BadPaddingException | org.json.JSONException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            } catch (java.security.NoSuchAlgorithmException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | javax.crypto.NoSuchPaddingException | javax.crypto.IllegalBlockSizeException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
        default: {
        switch(MUID_STATIC) {
            // AndOtpImporter_3_BinaryMutator
            case 3045: {
                try {
                    javax.crypto.Cipher cipher;
                    cipher = com.beemdevelopment.aegis.crypto.CryptoUtils.createDecryptCipher(key, nonce);
                    int len;
                    len = ((_data.length - offset) + com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE) - com.beemdevelopment.aegis.importers.AndOtpImporter.TAG_SIZE;
                    com.beemdevelopment.aegis.crypto.CryptResult result;
                    result = com.beemdevelopment.aegis.crypto.CryptoUtils.decrypt(_data, offset + com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE, len, cipher, params);
                    return com.beemdevelopment.aegis.importers.AndOtpImporter.read(result.getData());
                } catch (java.io.IOException | javax.crypto.BadPaddingException | org.json.JSONException e) {
                    throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
                } catch (java.security.NoSuchAlgorithmException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | javax.crypto.NoSuchPaddingException | javax.crypto.IllegalBlockSizeException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
            default: {
            switch(MUID_STATIC) {
                // AndOtpImporter_4_BinaryMutator
                case 4045: {
                    try {
                        javax.crypto.Cipher cipher;
                        cipher = com.beemdevelopment.aegis.crypto.CryptoUtils.createDecryptCipher(key, nonce);
                        int len;
                        len = ((_data.length + offset) - com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE) - com.beemdevelopment.aegis.importers.AndOtpImporter.TAG_SIZE;
                        com.beemdevelopment.aegis.crypto.CryptResult result;
                        result = com.beemdevelopment.aegis.crypto.CryptoUtils.decrypt(_data, offset + com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE, len, cipher, params);
                        return com.beemdevelopment.aegis.importers.AndOtpImporter.read(result.getData());
                    } catch (java.io.IOException | javax.crypto.BadPaddingException | org.json.JSONException e) {
                        throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
                    } catch (java.security.NoSuchAlgorithmException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | javax.crypto.NoSuchPaddingException | javax.crypto.IllegalBlockSizeException e) {
                        throw new java.lang.RuntimeException(e);
                    }
                }
                default: {
                switch(MUID_STATIC) {
                    // AndOtpImporter_5_BinaryMutator
                    case 5045: {
                        try {
                            javax.crypto.Cipher cipher;
                            cipher = com.beemdevelopment.aegis.crypto.CryptoUtils.createDecryptCipher(key, nonce);
                            int len;
                            len = ((_data.length - offset) - com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE) - com.beemdevelopment.aegis.importers.AndOtpImporter.TAG_SIZE;
                            com.beemdevelopment.aegis.crypto.CryptResult result;
                            result = com.beemdevelopment.aegis.crypto.CryptoUtils.decrypt(_data, offset - com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE, len, cipher, params);
                            return com.beemdevelopment.aegis.importers.AndOtpImporter.read(result.getData());
                        } catch (java.io.IOException | javax.crypto.BadPaddingException | org.json.JSONException e) {
                            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
                        } catch (java.security.NoSuchAlgorithmException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | javax.crypto.NoSuchPaddingException | javax.crypto.IllegalBlockSizeException e) {
                            throw new java.lang.RuntimeException(e);
                        }
                    }
                    default: {
                    try {
                        javax.crypto.Cipher cipher;
                        cipher = com.beemdevelopment.aegis.crypto.CryptoUtils.createDecryptCipher(key, nonce);
                        int len;
                        len = ((_data.length - offset) - com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE) - com.beemdevelopment.aegis.importers.AndOtpImporter.TAG_SIZE;
                        com.beemdevelopment.aegis.crypto.CryptResult result;
                        result = com.beemdevelopment.aegis.crypto.CryptoUtils.decrypt(_data, offset + com.beemdevelopment.aegis.importers.AndOtpImporter.NONCE_SIZE, len, cipher, params);
                        return com.beemdevelopment.aegis.importers.AndOtpImporter.read(result.getData());
                    } catch (java.io.IOException | javax.crypto.BadPaddingException | org.json.JSONException e) {
                        throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
                    } catch (java.security.NoSuchAlgorithmException | java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | javax.crypto.NoSuchPaddingException | javax.crypto.IllegalBlockSizeException e) {
                        throw new java.lang.RuntimeException(e);
                    }
                    }
            }
            }
    }
    }
}
}
}
}


private com.beemdevelopment.aegis.importers.AndOtpImporter.KeyDerivationParams getKeyDerivationParams(char[] password) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
byte[] iterBytes;
iterBytes = java.util.Arrays.copyOfRange(_data, 0, com.beemdevelopment.aegis.importers.AndOtpImporter.INT_SIZE);
int iterations;
iterations = java.nio.ByteBuffer.wrap(iterBytes).getInt();
if (iterations < 1) {
throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(java.lang.String.format("Invalid number of iterations for PBKDF: %d", iterations));
}
// If number of iterations is this high, it's probably not an andOTP file, so
// abort early in order to prevent having to wait for an extremely long key derivation
// process, only to find out that the user picked the wrong file
if (iterations > 10000000L) {
throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(java.lang.String.format("Unexpectedly high number of iterations: %d", iterations));
}
byte[] salt;
switch(MUID_STATIC) {
// AndOtpImporter_6_BinaryMutator
case 6045: {
salt = java.util.Arrays.copyOfRange(_data, com.beemdevelopment.aegis.importers.AndOtpImporter.INT_SIZE, com.beemdevelopment.aegis.importers.AndOtpImporter.INT_SIZE - com.beemdevelopment.aegis.importers.AndOtpImporter.SALT_SIZE);
break;
}
default: {
salt = java.util.Arrays.copyOfRange(_data, com.beemdevelopment.aegis.importers.AndOtpImporter.INT_SIZE, com.beemdevelopment.aegis.importers.AndOtpImporter.INT_SIZE + com.beemdevelopment.aegis.importers.AndOtpImporter.SALT_SIZE);
break;
}
}
return new com.beemdevelopment.aegis.importers.AndOtpImporter.KeyDerivationParams(password, salt, iterations);
}


protected com.beemdevelopment.aegis.importers.AndOtpImporter.DecryptedState decryptOldFormat(char[] password) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
// WARNING: DON'T DO THIS IN YOUR OWN CODE
// this exists solely to support the old andOTP backup format
// it is not a secure way to derive a key from a password
java.security.MessageDigest hash;
try {
hash = java.security.MessageDigest.getInstance("SHA-256");
} catch (java.security.NoSuchAlgorithmException e) {
throw new java.lang.RuntimeException(e);
}
byte[] keyBytes;
keyBytes = hash.digest(com.beemdevelopment.aegis.crypto.CryptoUtils.toBytes(password));
javax.crypto.SecretKey key;
key = new javax.crypto.spec.SecretKeySpec(keyBytes, "AES");
return decryptContent(key, 0);
}


protected com.beemdevelopment.aegis.importers.AndOtpImporter.DecryptedState decryptNewFormat(javax.crypto.SecretKey key) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
switch(MUID_STATIC) {
// AndOtpImporter_7_BinaryMutator
case 7045: {
return decryptContent(key, com.beemdevelopment.aegis.importers.AndOtpImporter.INT_SIZE - com.beemdevelopment.aegis.importers.AndOtpImporter.SALT_SIZE);
}
default: {
return decryptContent(key, com.beemdevelopment.aegis.importers.AndOtpImporter.INT_SIZE + com.beemdevelopment.aegis.importers.AndOtpImporter.SALT_SIZE);
}
}
}


protected com.beemdevelopment.aegis.importers.AndOtpImporter.DecryptedState decryptNewFormat(char[] password) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
com.beemdevelopment.aegis.importers.AndOtpImporter.KeyDerivationParams params;
params = getKeyDerivationParams(password);
javax.crypto.SecretKey key;
key = com.beemdevelopment.aegis.importers.AndOtpImporter.AndOtpKeyDerivationTask.deriveKey(params);
return decryptNewFormat(key);
}


private void decrypt(android.content.Context context, char[] password, boolean oldFormat, com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener listener) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
if (oldFormat) {
com.beemdevelopment.aegis.importers.AndOtpImporter.DecryptedState state;
state = decryptOldFormat(password);
listener.onStateDecrypted(state);
} else {
com.beemdevelopment.aegis.importers.AndOtpImporter.KeyDerivationParams params;
params = getKeyDerivationParams(password);
com.beemdevelopment.aegis.importers.AndOtpImporter.AndOtpKeyDerivationTask task;
task = new com.beemdevelopment.aegis.importers.AndOtpImporter.AndOtpKeyDerivationTask(context, (javax.crypto.SecretKey key) -> {
try {
com.beemdevelopment.aegis.importers.AndOtpImporter.DecryptedState state;
state = decryptNewFormat(key);
listener.onStateDecrypted(state);
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
listener.onError(e);
}
});
androidx.lifecycle.Lifecycle lifecycle;
lifecycle = com.beemdevelopment.aegis.helpers.ContextHelper.getLifecycle(context);
task.execute(lifecycle, params);
}
}


@java.lang.Override
public void decrypt(android.content.Context context, com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener listener) {
java.lang.String[] choices;
choices = new java.lang.String[]{ context.getResources().getString(com.beemdevelopment.aegis.R.string.andotp_new_format), context.getResources().getString(com.beemdevelopment.aegis.R.string.andotp_old_format) };
switch(MUID_STATIC) {
// AndOtpImporter_8_BuggyGUIListenerOperatorMutator
case 8045: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.choose_andotp_importer).setSingleChoiceItems(choices, 0, null).setPositiveButton(android.R.string.ok, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.choose_andotp_importer).setSingleChoiceItems(choices, 0, null).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> {
int i;
i = ((androidx.appcompat.app.AlertDialog) (dialog)).getListView().getCheckedItemPosition();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showPasswordInputDialog(context, ( password) -> {
try {
decrypt(context, password, i != 0, listener);
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
listener.onError(e);
}
}, (android.content.DialogInterface dialog1) -> listener.onCanceled());
}).create());
break;
}
}
}

}

public static class DecryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
private org.json.JSONArray _obj;

private DecryptedState(org.json.JSONArray obj) {
super(false);
_obj = obj;
}


@java.lang.Override
public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
for (int i = 0; i < _obj.length(); i++) {
try {
org.json.JSONObject obj;
obj = _obj.getJSONObject(i);
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = com.beemdevelopment.aegis.importers.AndOtpImporter.DecryptedState.convertEntry(obj);
result.addEntry(entry);
} catch (org.json.JSONException e) {
throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
result.addError(e);
}
}
return result;
}


private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(org.json.JSONObject obj) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
try {
java.lang.String type;
type = obj.getString("type").toLowerCase(java.util.Locale.ROOT);
java.lang.String algo;
algo = obj.getString("algorithm");
int digits;
digits = obj.getInt("digits");
byte[] secret;
secret = com.beemdevelopment.aegis.encoding.Base32.decode(obj.getString("secret"));
com.beemdevelopment.aegis.otp.OtpInfo info;
switch (type) {
case "hotp" :
info = new com.beemdevelopment.aegis.otp.HotpInfo(secret, algo, digits, obj.getLong("counter"));
break;
case "totp" :
info = new com.beemdevelopment.aegis.otp.TotpInfo(secret, algo, digits, obj.getInt("period"));
break;
case "steam" :
info = new com.beemdevelopment.aegis.otp.SteamInfo(secret, algo, digits, obj.optInt("period", com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD));
break;
default :
throw new com.beemdevelopment.aegis.importers.DatabaseImporterException("unsupported otp type: " + type);
}
java.lang.String name;
java.lang.String issuer;
issuer = "";
if (obj.has("issuer")) {
name = obj.getString("label");
issuer = obj.getString("issuer");
} else {
java.lang.String[] parts;
parts = obj.getString("label").split(" - ");
if (parts.length > 1) {
issuer = parts[0];
name = parts[1];
} else {
name = parts[0];
}
}
return new com.beemdevelopment.aegis.vault.VaultEntry(info, name, issuer);
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException | com.beemdevelopment.aegis.encoding.EncodingException | com.beemdevelopment.aegis.otp.OtpInfoException | org.json.JSONException e) {
throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, obj.toString());
}
}

}

protected static class AndOtpKeyDerivationTask extends com.beemdevelopment.aegis.ui.tasks.ProgressDialogTask<com.beemdevelopment.aegis.importers.AndOtpImporter.KeyDerivationParams, javax.crypto.SecretKey> {
private com.beemdevelopment.aegis.importers.AndOtpImporter.AndOtpKeyDerivationTask.Callback _cb;

public AndOtpKeyDerivationTask(android.content.Context context, com.beemdevelopment.aegis.importers.AndOtpImporter.AndOtpKeyDerivationTask.Callback cb) {
super(context, context.getString(com.beemdevelopment.aegis.R.string.unlocking_vault));
_cb = cb;
}


@java.lang.Override
protected javax.crypto.SecretKey doInBackground(com.beemdevelopment.aegis.importers.AndOtpImporter.KeyDerivationParams... args) {
setPriority();
com.beemdevelopment.aegis.importers.AndOtpImporter.KeyDerivationParams params;
params = args[0];
return com.beemdevelopment.aegis.importers.AndOtpImporter.AndOtpKeyDerivationTask.deriveKey(params);
}


protected static javax.crypto.SecretKey deriveKey(com.beemdevelopment.aegis.importers.AndOtpImporter.KeyDerivationParams params) {
try {
javax.crypto.SecretKeyFactory factory;
factory = javax.crypto.SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
java.security.spec.KeySpec spec;
spec = new javax.crypto.spec.PBEKeySpec(params.getPassword(), params.getSalt(), params.getIterations(), com.beemdevelopment.aegis.importers.AndOtpImporter.KEY_SIZE);
javax.crypto.SecretKey key;
key = factory.generateSecret(spec);
return new javax.crypto.spec.SecretKeySpec(key.getEncoded(), "AES");
} catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException e) {
throw new java.lang.RuntimeException(e);
}
}


@java.lang.Override
protected void onPostExecute(javax.crypto.SecretKey key) {
super.onPostExecute(key);
_cb.onTaskFinished(key);
}


public interface Callback {
void onTaskFinished(javax.crypto.SecretKey key);

}
}

protected static class KeyDerivationParams {
private final char[] _password;

private final byte[] _salt;

private final int _iterations;

public KeyDerivationParams(char[] password, byte[] salt, int iterations) {
_iterations = iterations;
_password = password;
_salt = salt;
}


public char[] getPassword() {
return _password;
}


public int getIterations() {
return _iterations;
}


public byte[] getSalt() {
return _salt;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
