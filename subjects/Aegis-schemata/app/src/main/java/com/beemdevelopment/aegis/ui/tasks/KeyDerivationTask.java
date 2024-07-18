package com.beemdevelopment.aegis.ui.tasks;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.crypto.SCryptParameters;
import com.beemdevelopment.aegis.crypto.CryptoUtils;
import com.beemdevelopment.aegis.vault.slots.PasswordSlot;
import javax.crypto.SecretKey;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class KeyDerivationTask extends com.beemdevelopment.aegis.ui.tasks.ProgressDialogTask<com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Params, com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Result> {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Callback _cb;

    public KeyDerivationTask(android.content.Context context, com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Callback cb) {
        super(context, context.getString(com.beemdevelopment.aegis.R.string.encrypting_vault));
        _cb = cb;
    }


    @java.lang.Override
    protected com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Result doInBackground(com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Params... args) {
        setPriority();
        com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Params params;
        params = args[0];
        byte[] salt;
        salt = com.beemdevelopment.aegis.crypto.CryptoUtils.generateSalt();
        com.beemdevelopment.aegis.crypto.SCryptParameters scryptParams;
        scryptParams = new com.beemdevelopment.aegis.crypto.SCryptParameters(com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_SCRYPT_N, com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_SCRYPT_r, com.beemdevelopment.aegis.crypto.CryptoUtils.CRYPTO_SCRYPT_p, salt);
        com.beemdevelopment.aegis.vault.slots.PasswordSlot slot;
        slot = params.getSlot();
        javax.crypto.SecretKey key;
        key = slot.deriveKey(params.getPassword(), scryptParams);
        return new com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Result(slot, key);
    }


    @java.lang.Override
    protected void onPostExecute(com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Result result) {
        super.onPostExecute(result);
        _cb.onTaskFinished(result.getSlot(), result.getKey());
    }


    public static class Params {
        private com.beemdevelopment.aegis.vault.slots.PasswordSlot _slot;

        private char[] _password;

        public Params(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, char[] password) {
            _slot = slot;
            _password = password;
        }


        public com.beemdevelopment.aegis.vault.slots.PasswordSlot getSlot() {
            return _slot;
        }


        public char[] getPassword() {
            return _password;
        }

    }

    public static class Result {
        private com.beemdevelopment.aegis.vault.slots.PasswordSlot _slot;

        private javax.crypto.SecretKey _key;

        public Result(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, javax.crypto.SecretKey key) {
            _slot = slot;
            _key = key;
        }


        public com.beemdevelopment.aegis.vault.slots.PasswordSlot getSlot() {
            return _slot;
        }


        public javax.crypto.SecretKey getKey() {
            return _key;
        }

    }

    public interface Callback {
        void onTaskFinished(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, javax.crypto.SecretKey key);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
