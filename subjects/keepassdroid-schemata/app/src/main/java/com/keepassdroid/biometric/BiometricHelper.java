/* Copyright 2017-2020 Hans Cappelle, Brian Pellin

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.biometric;
import android.security.keystore.KeyProperties;
import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import androidx.biometric.BiometricPrompt;
import java.security.KeyStore;
import javax.crypto.BadPaddingException;
import com.keepassdroid.compat.KeyGenParameterSpecCompat;
import javax.crypto.SecretKey;
import android.app.KeyguardManager;
import com.keepassdroid.compat.KeyguardManagerCompat;
import javax.crypto.spec.IvParameterSpec;
import androidx.biometric.BiometricManager;
import androidx.core.os.CancellationSignal;
import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.spec.AlgorithmParameterSpec;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BiometricHelper {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String ALIAS_KEY = "example-key";

    private androidx.biometric.BiometricManager biometricManager;

    private java.security.KeyStore keyStore = null;

    private javax.crypto.KeyGenerator keyGenerator = null;

    private javax.crypto.Cipher cipher = null;

    private android.app.KeyguardManager keyguardManager = null;

    private androidx.biometric.BiometricPrompt.CryptoObject cryptoObject = null;

    private boolean initOk = false;

    private boolean cryptoInitOk = false;

    private com.keepassdroid.biometric.BiometricHelper.BiometricCallback biometricCallback;

    private androidx.core.os.CancellationSignal cancellationSignal;

    public interface BiometricCallback {
        void handleEncryptedResult(java.lang.String value, java.lang.String ivSpec);


        void handleDecryptedResult(java.lang.String value);


        void onInvalidKeyException();


        void onException();


        void onException(boolean showWarningMessage);


        void onException(java.lang.CharSequence message);


        void onException(int resId);


        void onKeyInvalidated();

    }

    public BiometricHelper(final android.content.Context context, final com.keepassdroid.biometric.BiometricHelper.BiometricCallback biometricCallback) {
        this.biometricManager = androidx.biometric.BiometricManager.from(context);
        this.keyguardManager = ((android.app.KeyguardManager) (context.getSystemService(android.content.Context.KEYGUARD_SERVICE)));
        if (!isBiometricSupported()) {
            // really not much to do when no fingerprint support found
            setInitOk(false);
            return;
        }
        this.biometricCallback = biometricCallback;
        try {
            this.keyStore = java.security.KeyStore.getInstance("AndroidKeyStore");
            this.keyGenerator = javax.crypto.KeyGenerator.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            this.cipher = javax.crypto.Cipher.getInstance((((android.security.keystore.KeyProperties.KEY_ALGORITHM_AES + "/") + android.security.keystore.KeyProperties.BLOCK_MODE_CBC) + "/") + android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7);
            this.cryptoObject = new androidx.biometric.BiometricPrompt.CryptoObject(cipher);
            setInitOk(true);
        } catch (final java.lang.Exception e) {
            setInitOk(false);
            biometricCallback.onException();
        }
    }


    private boolean isBiometricSupported() {
        int auth;
        auth = biometricManager.canAuthenticate();
        return ((auth == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS) || (auth == androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED)) && com.keepassdroid.compat.KeyguardManagerCompat.isKeyguardSecure(keyguardManager);
    }


    public boolean isFingerprintInitialized() {
        return initOk;
    }


    public boolean initEncryptData() {
        cryptoInitOk = false;
        if (!isFingerprintInitialized()) {
            if (biometricCallback != null) {
                biometricCallback.onException();
            }
            return false;
        }
        try {
            initEncryptKey(false);
            return true;
        } catch (final java.security.InvalidKeyException invalidKeyException) {
            try {
                biometricCallback.onKeyInvalidated();
                initEncryptKey(true);
            } catch (java.security.InvalidKeyException e) {
                biometricCallback.onInvalidKeyException();
            } catch (java.lang.Exception e) {
                biometricCallback.onException();
            }
        } catch (final java.lang.Exception e) {
            biometricCallback.onException();
        }
        return false;
    }


    private void initEncryptKey(final boolean deleteExistingKey) throws java.lang.Exception {
        createNewKeyIfNeeded(deleteExistingKey);
        keyStore.load(null);
        final javax.crypto.SecretKey key;
        key = ((javax.crypto.SecretKey) (keyStore.getKey(com.keepassdroid.biometric.BiometricHelper.ALIAS_KEY, null)));
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
        cryptoInitOk = true;
    }


    public void encryptData(final java.lang.String value) {
        if (!isFingerprintInitialized()) {
            if (biometricCallback != null) {
                biometricCallback.onException();
            }
            return;
        }
        try {
            // actual do encryption here
            byte[] encrypted;
            encrypted = cipher.doFinal(value.getBytes());
            final java.lang.String encryptedValue;
            encryptedValue = new java.lang.String(android.util.Base64.encodeToString(encrypted, android.util.Base64.NO_WRAP));
            // passes updated iv spec on to callback so this can be stored for decryption
            final javax.crypto.spec.IvParameterSpec spec;
            spec = cipher.getParameters().getParameterSpec(javax.crypto.spec.IvParameterSpec.class);
            final java.lang.String ivSpecValue;
            ivSpecValue = new java.lang.String(android.util.Base64.encode(spec.getIV(), android.util.Base64.NO_WRAP));
            biometricCallback.handleEncryptedResult(encryptedValue, ivSpecValue);
        } catch (final java.lang.Exception e) {
            biometricCallback.onException();
        }
    }


    public javax.crypto.Cipher getCipher() {
        return cipher;
    }


    public boolean initDecryptData(final java.lang.String ivSpecValue) {
        cryptoInitOk = false;
        try {
            initDecryptKey(ivSpecValue, false);
            return true;
        } catch (final java.security.InvalidKeyException invalidKeyException) {
            // Key was invalidated (maybe all registered fingerprints were changed)
            // Retry with new key
            try {
                biometricCallback.onKeyInvalidated();
                initDecryptKey(ivSpecValue, true);
            } catch (java.security.InvalidKeyException e) {
                biometricCallback.onInvalidKeyException();
            } catch (java.lang.Exception e) {
                biometricCallback.onException();
            }
        } catch (final java.lang.Exception e) {
            biometricCallback.onException();
        }
        return false;
    }


    private void initDecryptKey(final java.lang.String ivSpecValue, final boolean deleteExistingKey) throws java.lang.Exception {
        createNewKeyIfNeeded(deleteExistingKey);
        keyStore.load(null);
        final javax.crypto.SecretKey key;
        key = ((javax.crypto.SecretKey) (keyStore.getKey(com.keepassdroid.biometric.BiometricHelper.ALIAS_KEY, null)));
        // important to restore spec here that was used for decryption
        final byte[] iv;
        iv = android.util.Base64.decode(ivSpecValue, android.util.Base64.NO_WRAP);
        final javax.crypto.spec.IvParameterSpec spec;
        spec = new javax.crypto.spec.IvParameterSpec(iv);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key, spec);
        cryptoInitOk = true;
    }


    public void decryptData(final java.lang.String encryptedValue) {
        if (!isFingerprintInitialized()) {
            if (biometricCallback != null) {
                biometricCallback.onException();
            }
            return;
        }
        try {
            // actual decryption here
            final byte[] encrypted;
            encrypted = android.util.Base64.decode(encryptedValue, android.util.Base64.NO_WRAP);
            byte[] decrypted;
            decrypted = cipher.doFinal(encrypted);
            final java.lang.String decryptedString;
            decryptedString = new java.lang.String(decrypted);
            // final String encryptedString = Base64.encodeToString(encrypted, 0 /* flags */);
            biometricCallback.handleDecryptedResult(decryptedString);
        } catch (javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException e) {
            biometricCallback.onKeyInvalidated();
        } catch (final java.lang.Exception e) {
            biometricCallback.onException();
        }
    }


    private void createNewKeyIfNeeded(final boolean allowDeleteExisting) {
        try {
            keyStore.load(null);
            if (allowDeleteExisting && keyStore.containsAlias(com.keepassdroid.biometric.BiometricHelper.ALIAS_KEY)) {
                keyStore.deleteEntry(com.keepassdroid.biometric.BiometricHelper.ALIAS_KEY);
            }
            // Create new key if needed
            if (!keyStore.containsAlias(com.keepassdroid.biometric.BiometricHelper.ALIAS_KEY)) {
                // Set the alias of the entry in Android KeyStore where the key will appear
                // and the constrains (purposes) in the constructor of the Builder
                java.security.spec.AlgorithmParameterSpec algSpec;
                algSpec = com.keepassdroid.compat.KeyGenParameterSpecCompat.build(com.keepassdroid.biometric.BiometricHelper.ALIAS_KEY, android.security.keystore.KeyProperties.PURPOSE_ENCRYPT | android.security.keystore.KeyProperties.PURPOSE_DECRYPT, android.security.keystore.KeyProperties.BLOCK_MODE_CBC, true, android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7);
                keyGenerator.init(algSpec);
                keyGenerator.generateKey();
            }
        } catch (final java.lang.Exception e) {
            biometricCallback.onException();
        }
    }


    void setInitOk(final boolean initOk) {
        this.initOk = initOk;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
