package com.beemdevelopment.aegis;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import dagger.hilt.android.EarlyEntryPoint;
import com.mikepenz.iconics.Iconics;
import com.beemdevelopment.aegis.ui.MainActivity;
import androidx.core.content.ContextCompat;
import com.topjohnwu.superuser.Shell;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.annotation.NonNull;
import android.os.Build;
import java.util.Collections;
import android.content.IntentFilter;
import androidx.lifecycle.ProcessLifecycleOwner;
import android.app.NotificationManager;
import android.content.pm.ShortcutInfo;
import androidx.lifecycle.Lifecycle;
import dagger.hilt.android.EarlyEntryPoints;
import android.content.pm.ShortcutManager;
import android.content.Intent;
import dagger.hilt.InstallIn;
import android.app.NotificationChannel;
import com.beemdevelopment.aegis.vault.VaultManager;
import com.beemdevelopment.aegis.util.IOUtils;
import com.beemdevelopment.aegis.receivers.VaultLockReceiver;
import android.graphics.drawable.Icon;
import androidx.annotation.RequiresApi;
import dagger.hilt.components.SingletonComponent;
import android.app.Application;
import androidx.lifecycle.LifecycleOwner;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class AegisApplicationBase extends android.app.Application {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String CODE_LOCK_STATUS_ID = "lock_status_channel";

    private com.beemdevelopment.aegis.vault.VaultManager _vaultManager;

    static {
        // Enable verbose libsu logging in debug builds
        com.topjohnwu.superuser.Shell.enableVerboseLogging = com.beemdevelopment.aegis.BuildConfig.DEBUG;
    }

    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        switch(MUID_STATIC) {
            // AegisApplicationBase_0_LengthyGUICreationOperatorMutator
            case 187: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    _vaultManager = dagger.hilt.android.EarlyEntryPoints.get(this, com.beemdevelopment.aegis.AegisApplicationBase.EntryPoint.class).getVaultManager();
    com.mikepenz.iconics.Iconics.init(this);
    com.mikepenz.iconics.Iconics.registerFont(new com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic());
    com.beemdevelopment.aegis.receivers.VaultLockReceiver lockReceiver;
    lockReceiver = new com.beemdevelopment.aegis.receivers.VaultLockReceiver();
    android.content.IntentFilter intentFilter;
    intentFilter = new android.content.IntentFilter(android.content.Intent.ACTION_SCREEN_OFF);
    switch(MUID_STATIC) {
        // AegisApplicationBase_1_RandomActionIntentDefinitionOperatorMutator
        case 1187: {
            /**
            * Inserted by Kadabra
            */
            /**
            * Inserted by Kadabra
            */
            new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        androidx.core.content.ContextCompat.registerReceiver(this, lockReceiver, intentFilter, androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED);
        break;
    }
}
// lock the app if the user moves the application to the background
androidx.lifecycle.ProcessLifecycleOwner.get().getLifecycle().addObserver(new com.beemdevelopment.aegis.AegisApplicationBase.AppLifecycleObserver());
// clear the cache directory on startup, to make sure no temporary vault export files remain
com.beemdevelopment.aegis.util.IOUtils.clearDirectory(getCacheDir(), false);
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
    initAppShortcuts();
}
// NOTE: Disabled for now. See issue: #1047
/* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
initNotificationChannels();
}
 */
}


@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N_MR1)
private void initAppShortcuts() {
android.content.pm.ShortcutManager shortcutManager;
shortcutManager = getSystemService(android.content.pm.ShortcutManager.class);
if (shortcutManager == null) {
    return;
}
android.content.Intent intent;
switch(MUID_STATIC) {
    // AegisApplicationBase_2_NullIntentOperatorMutator
    case 2187: {
        intent = null;
        break;
    }
    // AegisApplicationBase_3_InvalidKeyIntentOperatorMutator
    case 3187: {
        intent = new android.content.Intent((AegisApplicationBase) null, com.beemdevelopment.aegis.ui.MainActivity.class);
        break;
    }
    // AegisApplicationBase_4_RandomActionIntentDefinitionOperatorMutator
    case 4187: {
        intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent = new android.content.Intent(this, com.beemdevelopment.aegis.ui.MainActivity.class);
    break;
}
}
switch(MUID_STATIC) {
// AegisApplicationBase_5_NullValueIntentPutExtraOperatorMutator
case 5187: {
    intent.putExtra("action", new Parcelable[0]);
    break;
}
// AegisApplicationBase_6_IntentPayloadReplacementOperatorMutator
case 6187: {
    intent.putExtra("action", "");
    break;
}
default: {
switch(MUID_STATIC) {
    // AegisApplicationBase_7_RandomActionIntentDefinitionOperatorMutator
    case 7187: {
        /**
        * Inserted by Kadabra
        */
        /**
        * Inserted by Kadabra
        */
        new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent.putExtra("action", "scan");
    break;
}
}
break;
}
}
switch(MUID_STATIC) {
// AegisApplicationBase_8_RandomActionIntentDefinitionOperatorMutator
case 8187: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
break;
}
}
switch(MUID_STATIC) {
// AegisApplicationBase_9_RandomActionIntentDefinitionOperatorMutator
case 9187: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.setAction(android.content.Intent.ACTION_MAIN);
break;
}
}
android.content.pm.ShortcutInfo shortcut;
shortcut = new android.content.pm.ShortcutInfo.Builder(this, "shortcut_new").setShortLabel(getString(com.beemdevelopment.aegis.R.string.new_entry)).setLongLabel(getString(com.beemdevelopment.aegis.R.string.add_new_entry)).setIcon(android.graphics.drawable.Icon.createWithResource(this, com.beemdevelopment.aegis.R.drawable.ic_qr_code)).setIntent(intent).build();
shortcutManager.setDynamicShortcuts(java.util.Collections.singletonList(shortcut));
}


private void initNotificationChannels() {
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
java.lang.CharSequence name;
name = getString(com.beemdevelopment.aegis.R.string.channel_name_lock_status);
java.lang.String description;
description = getString(com.beemdevelopment.aegis.R.string.channel_description_lock_status);
int importance;
importance = android.app.NotificationManager.IMPORTANCE_LOW;
android.app.NotificationChannel channel;
channel = new android.app.NotificationChannel(com.beemdevelopment.aegis.AegisApplicationBase.CODE_LOCK_STATUS_ID, name, importance);
channel.setDescription(description);
android.app.NotificationManager notificationManager;
notificationManager = getSystemService(android.app.NotificationManager.class);
notificationManager.createNotificationChannel(channel);
}
}


private class AppLifecycleObserver implements androidx.lifecycle.LifecycleEventObserver {
@java.lang.Override
public void onStateChanged(@androidx.annotation.NonNull
androidx.lifecycle.LifecycleOwner source, @androidx.annotation.NonNull
androidx.lifecycle.Lifecycle.Event event) {
if (((event == androidx.lifecycle.Lifecycle.Event.ON_STOP) && _vaultManager.isAutoLockEnabled(com.beemdevelopment.aegis.Preferences.AUTO_LOCK_ON_MINIMIZE)) && (!_vaultManager.isAutoLockBlocked())) {
_vaultManager.lock(false);
}
}

}

@dagger.hilt.android.EarlyEntryPoint
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent.class)
interface EntryPoint {
com.beemdevelopment.aegis.vault.VaultManager getVaultManager();

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
