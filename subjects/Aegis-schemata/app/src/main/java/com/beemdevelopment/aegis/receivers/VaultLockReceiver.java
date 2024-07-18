package com.beemdevelopment.aegis.receivers;
import com.beemdevelopment.aegis.vault.VaultManager;
import javax.inject.Inject;
import android.content.Intent;
import com.beemdevelopment.aegis.BuildConfig;
import dagger.hilt.android.AndroidEntryPoint;
import android.content.BroadcastReceiver;
import com.beemdevelopment.aegis.Preferences;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@dagger.hilt.android.AndroidEntryPoint
public class VaultLockReceiver extends android.content.BroadcastReceiver {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ACTION_LOCK_VAULT = java.lang.String.format("%s.LOCK_VAULT", com.beemdevelopment.aegis.BuildConfig.APPLICATION_ID);

    @javax.inject.Inject
    protected com.beemdevelopment.aegis.vault.VaultManager _vaultManager;

    @java.lang.Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if ((intent.getAction() == null) || ((!intent.getAction().equals(com.beemdevelopment.aegis.receivers.VaultLockReceiver.ACTION_LOCK_VAULT)) && (!intent.getAction().equals(android.content.Intent.ACTION_SCREEN_OFF)))) {
            return;
        }
        if (_vaultManager.isAutoLockEnabled(com.beemdevelopment.aegis.Preferences.AUTO_LOCK_ON_DEVICE_LOCK)) {
            _vaultManager.lock(false);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
