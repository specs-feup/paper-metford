package com.beemdevelopment.aegis.helpers;
import com.beemdevelopment.aegis.crypto.KeyStoreHandle;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import com.beemdevelopment.aegis.vault.slots.Slot;
import java.util.Objects;
import com.beemdevelopment.aegis.crypto.KeyStoreHandleException;
import androidx.fragment.app.Fragment;
import javax.crypto.Cipher;
import com.beemdevelopment.aegis.vault.slots.SlotException;
import javax.crypto.SecretKey;
import androidx.fragment.app.FragmentActivity;
import com.beemdevelopment.aegis.vault.slots.BiometricSlot;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * A class that can prepare initialization of a BiometricSlot by generating a new
 * key in the Android KeyStore and authenticating a cipher for it through a
 * BiometricPrompt.
 */
public class BiometricSlotInitializer extends androidx.biometric.BiometricPrompt.AuthenticationCallback {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.vault.slots.BiometricSlot _slot;

    private com.beemdevelopment.aegis.helpers.BiometricSlotInitializer.Listener _listener;

    private androidx.biometric.BiometricPrompt _prompt;

    public BiometricSlotInitializer(androidx.fragment.app.Fragment fragment, com.beemdevelopment.aegis.helpers.BiometricSlotInitializer.Listener listener) {
        _listener = listener;
        _prompt = new androidx.biometric.BiometricPrompt(fragment, new com.beemdevelopment.aegis.helpers.UiThreadExecutor(), this);
    }


    public BiometricSlotInitializer(androidx.fragment.app.FragmentActivity activity, com.beemdevelopment.aegis.helpers.BiometricSlotInitializer.Listener listener) {
        _listener = listener;
        _prompt = new androidx.biometric.BiometricPrompt(activity, new com.beemdevelopment.aegis.helpers.UiThreadExecutor(), this);
    }


    /**
     * Generates a new key in the Android KeyStore for the new BiometricSlot,
     * initializes a cipher with it and shows a BiometricPrompt to the user for
     * authentication. If authentication is successful, the new slot will be
     * initialized and delivered back through the listener.
     */
    public void authenticate(androidx.biometric.BiometricPrompt.PromptInfo info) {
        if (_slot != null) {
            throw new java.lang.IllegalStateException("Biometric authentication already in progress");
        }
        com.beemdevelopment.aegis.crypto.KeyStoreHandle keyStore;
        try {
            keyStore = new com.beemdevelopment.aegis.crypto.KeyStoreHandle();
        } catch (com.beemdevelopment.aegis.crypto.KeyStoreHandleException e) {
            fail(e);
            return;
        }
        // generate a new Android KeyStore key
        // and assign it the UUID of the new slot as an alias
        javax.crypto.Cipher cipher;
        com.beemdevelopment.aegis.vault.slots.BiometricSlot slot;
        slot = new com.beemdevelopment.aegis.vault.slots.BiometricSlot();
        try {
            javax.crypto.SecretKey key;
            key = keyStore.generateKey(slot.getUUID().toString());
            cipher = com.beemdevelopment.aegis.vault.slots.Slot.createEncryptCipher(key);
        } catch (com.beemdevelopment.aegis.crypto.KeyStoreHandleException | com.beemdevelopment.aegis.vault.slots.SlotException e) {
            fail(e);
            return;
        }
        _slot = slot;
        _prompt.authenticate(info, new androidx.biometric.BiometricPrompt.CryptoObject(cipher));
    }


    /**
     * Cancels the BiometricPrompt and resets the state of the initializer. It will
     * also attempt to delete the previously generated Android KeyStore key.
     */
    public void cancelAuthentication() {
        if (_slot == null) {
            throw new java.lang.IllegalStateException("Biometric authentication not in progress");
        }
        reset();
        _prompt.cancelAuthentication();
    }


    private void reset() {
        if (_slot != null) {
            try {
                // clean up the unused KeyStore key
                // this is non-critical, so just fail silently if an error occurs
                java.lang.String uuid;
                uuid = _slot.getUUID().toString();
                com.beemdevelopment.aegis.crypto.KeyStoreHandle keyStore;
                keyStore = new com.beemdevelopment.aegis.crypto.KeyStoreHandle();
                if (keyStore.containsKey(uuid)) {
                    keyStore.deleteKey(uuid);
                }
            } catch (com.beemdevelopment.aegis.crypto.KeyStoreHandleException e) {
                e.printStackTrace();
            }
            _slot = null;
        }
    }


    private void fail(int errorCode, java.lang.CharSequence errString) {
        reset();
        _listener.onSlotInitializationFailed(errorCode, errString);
    }


    private void fail(java.lang.Exception e) {
        e.printStackTrace();
        fail(0, e.toString());
    }


    @java.lang.Override
    public void onAuthenticationError(int errorCode, @androidx.annotation.NonNull
    java.lang.CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        fail(errorCode, errString.toString());
    }


    @java.lang.Override
    public void onAuthenticationSucceeded(@androidx.annotation.NonNull
    androidx.biometric.BiometricPrompt.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        _listener.onInitializeSlot(_slot, java.util.Objects.requireNonNull(result.getCryptoObject()).getCipher());
    }


    @java.lang.Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
    }


    public interface Listener {
        void onInitializeSlot(com.beemdevelopment.aegis.vault.slots.BiometricSlot slot, javax.crypto.Cipher cipher);


        void onSlotInitializationFailed(int errorCode, @androidx.annotation.NonNull
        java.lang.CharSequence errString);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
