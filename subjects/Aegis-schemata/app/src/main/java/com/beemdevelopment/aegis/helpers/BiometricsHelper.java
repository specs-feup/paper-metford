package com.beemdevelopment.aegis.helpers;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BiometricsHelper {
    static final int MUID_STATIC = getMUID();
    private BiometricsHelper() {
    }


    public static androidx.biometric.BiometricManager getManager(android.content.Context context) {
        androidx.biometric.BiometricManager manager;
        manager = androidx.biometric.BiometricManager.from(context);
        if (manager.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG) == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS) {
            return manager;
        }
        return null;
    }


    public static boolean isCanceled(int errorCode) {
        return ((errorCode == androidx.biometric.BiometricPrompt.ERROR_CANCELED) || (errorCode == androidx.biometric.BiometricPrompt.ERROR_USER_CANCELED)) || (errorCode == androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON);
    }


    public static boolean isAvailable(android.content.Context context) {
        return com.beemdevelopment.aegis.helpers.BiometricsHelper.getManager(context) != null;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
