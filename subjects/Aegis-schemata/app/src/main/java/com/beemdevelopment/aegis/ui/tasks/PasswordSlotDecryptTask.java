package com.beemdevelopment.aegis.ui.tasks;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.crypto.MasterKey;
import com.beemdevelopment.aegis.vault.slots.Slot;
import java.util.List;
import javax.crypto.Cipher;
import com.beemdevelopment.aegis.crypto.CryptoUtils;
import com.beemdevelopment.aegis.vault.slots.PasswordSlot;
import com.beemdevelopment.aegis.vault.slots.SlotException;
import javax.crypto.SecretKey;
import android.content.Context;
import com.beemdevelopment.aegis.vault.slots.SlotIntegrityException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PasswordSlotDecryptTask extends com.beemdevelopment.aegis.ui.tasks.ProgressDialogTask<com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Params, com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result> {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Callback _cb;

    public PasswordSlotDecryptTask(android.content.Context context, com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Callback cb) {
        super(context, context.getString(com.beemdevelopment.aegis.R.string.unlocking_vault));
        _cb = cb;
    }


    @java.lang.Override
    protected com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result doInBackground(com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Params... args) {
        setPriority();
        com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Params params;
        params = args[0];
        return com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.decrypt(params.getSlots(), params.getPassword());
    }


    public static com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result decrypt(java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> slots, char[] password) {
        for (com.beemdevelopment.aegis.vault.slots.PasswordSlot slot : slots) {
            try {
                return com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.decryptPasswordSlot(slot, password);
            } catch (com.beemdevelopment.aegis.vault.slots.SlotException e) {
                throw new java.lang.RuntimeException(e);
            } catch (com.beemdevelopment.aegis.vault.slots.SlotIntegrityException ignored) {
            }
        }
        return null;
    }


    public static com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result decryptPasswordSlot(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, char[] password) throws com.beemdevelopment.aegis.vault.slots.SlotIntegrityException, com.beemdevelopment.aegis.vault.slots.SlotException {
        com.beemdevelopment.aegis.crypto.MasterKey masterKey;
        javax.crypto.SecretKey key;
        key = slot.deriveKey(password);
        byte[] oldPasswordBytes;
        oldPasswordBytes = com.beemdevelopment.aegis.crypto.CryptoUtils.toBytesOld(password);
        try {
            masterKey = com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.decryptPasswordSlot(slot, key);
        } catch (com.beemdevelopment.aegis.vault.slots.SlotIntegrityException e) {
            // a bug introduced in afb9e59 caused passwords longer than 64 bytes to produce a different key than before
            // so, try again with the old password encode function if the password is longer than 64 bytes
            if (slot.isRepaired() || (oldPasswordBytes.length <= 64)) {
                throw e;
            }
            // try to decrypt the password slot with the old key
            javax.crypto.SecretKey oldKey;
            oldKey = slot.deriveKey(oldPasswordBytes);
            masterKey = com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.decryptPasswordSlot(slot, oldKey);
        }
        // if necessary, repair the slot by re-encrypting the master key with the correct key
        // slots with passwords smaller than 64 bytes also get this treatment to make sure those also have 'repaired' set to true
        boolean repaired;
        repaired = false;
        if (!slot.isRepaired()) {
            javax.crypto.Cipher cipher;
            cipher = com.beemdevelopment.aegis.vault.slots.Slot.createEncryptCipher(key);
            slot.setKey(masterKey, cipher);
            repaired = true;
        }
        return new com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result(masterKey, slot, repaired);
    }


    public static com.beemdevelopment.aegis.crypto.MasterKey decryptPasswordSlot(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, javax.crypto.SecretKey key) throws com.beemdevelopment.aegis.vault.slots.SlotException, com.beemdevelopment.aegis.vault.slots.SlotIntegrityException {
        javax.crypto.Cipher cipher;
        cipher = slot.createDecryptCipher(key);
        return slot.getKey(cipher);
    }


    @java.lang.Override
    protected void onPostExecute(com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result result) {
        super.onPostExecute(result);
        _cb.onTaskFinished(result);
    }


    public static class Params {
        private java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> _slots;

        private char[] _password;

        public Params(java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> slots, char[] password) {
            _slots = slots;
            _password = password;
        }


        public java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> getSlots() {
            return _slots;
        }


        public char[] getPassword() {
            return _password;
        }

    }

    public static class Result {
        private com.beemdevelopment.aegis.crypto.MasterKey _key;

        private com.beemdevelopment.aegis.vault.slots.PasswordSlot _slot;

        private boolean _repaired;

        public Result(com.beemdevelopment.aegis.crypto.MasterKey key, com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, boolean repaired) {
            _key = key;
            _slot = slot;
            _repaired = repaired;
        }


        public Result(com.beemdevelopment.aegis.crypto.MasterKey key, com.beemdevelopment.aegis.vault.slots.PasswordSlot slot) {
            this(key, slot, false);
        }


        public com.beemdevelopment.aegis.crypto.MasterKey getKey() {
            return _key;
        }


        public com.beemdevelopment.aegis.vault.slots.Slot getSlot() {
            return _slot;
        }


        public boolean isSlotRepaired() {
            return _repaired;
        }

    }

    public interface Callback {
        void onTaskFinished(com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result result);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
