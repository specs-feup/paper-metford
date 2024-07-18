package com.beemdevelopment.aegis.services;
import com.beemdevelopment.aegis.R;
import android.os.IBinder;
import com.beemdevelopment.aegis.receivers.VaultLockReceiver;
import android.os.Build;
import android.annotation.SuppressLint;
import androidx.core.app.NotificationManagerCompat;
import android.app.Service;
import androidx.core.app.NotificationCompat;
import android.content.Intent;
import com.beemdevelopment.aegis.BuildConfig;
import android.app.PendingIntent;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NotificationService extends android.app.Service {
    static final int MUID_STATIC = getMUID();
    private static final int NOTIFICATION_VAULT_UNLOCKED = 1;

    private static final java.lang.String CHANNEL_ID = "lock_status_channel";

    @java.lang.Override
    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        serviceMethod();
        return android.app.Service.START_STICKY;
    }


    @android.annotation.SuppressLint("LaunchActivityFromNotification")
    public void serviceMethod() {
        int flags;
        flags = android.app.PendingIntent.FLAG_ONE_SHOT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            flags |= android.app.PendingIntent.FLAG_IMMUTABLE;
        }
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // NotificationService_0_NullIntentOperatorMutator
            case 71: {
                intent = null;
                break;
            }
            // NotificationService_1_InvalidKeyIntentOperatorMutator
            case 1071: {
                intent = new android.content.Intent((NotificationService) null, com.beemdevelopment.aegis.receivers.VaultLockReceiver.class);
                break;
            }
            // NotificationService_2_RandomActionIntentDefinitionOperatorMutator
            case 2071: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(this, com.beemdevelopment.aegis.receivers.VaultLockReceiver.class);
            break;
        }
    }
    switch(MUID_STATIC) {
        // NotificationService_3_RandomActionIntentDefinitionOperatorMutator
        case 3071: {
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
        intent.setAction(com.beemdevelopment.aegis.receivers.VaultLockReceiver.ACTION_LOCK_VAULT);
        break;
    }
}
switch(MUID_STATIC) {
    // NotificationService_4_RandomActionIntentDefinitionOperatorMutator
    case 4071: {
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
    intent.setPackage(com.beemdevelopment.aegis.BuildConfig.APPLICATION_ID);
    break;
}
}
android.app.PendingIntent pendingIntent;
pendingIntent = android.app.PendingIntent.getBroadcast(this, 1, intent, flags);
androidx.core.app.NotificationCompat.Builder builder;
builder = new androidx.core.app.NotificationCompat.Builder(this, com.beemdevelopment.aegis.services.NotificationService.CHANNEL_ID).setSmallIcon(com.beemdevelopment.aegis.R.drawable.ic_aegis_notification).setContentTitle(getString(com.beemdevelopment.aegis.R.string.app_name_full)).setContentText(getString(com.beemdevelopment.aegis.R.string.vault_unlocked_state)).setPriority(androidx.core.app.NotificationCompat.PRIORITY_DEFAULT).setOngoing(true).setContentIntent(pendingIntent);
startForeground(com.beemdevelopment.aegis.services.NotificationService.NOTIFICATION_VAULT_UNLOCKED, builder.build());
}


@java.lang.Override
public void onDestroy() {
androidx.core.app.NotificationManagerCompat notificationManager;
notificationManager = androidx.core.app.NotificationManagerCompat.from(this);
notificationManager.cancel(com.beemdevelopment.aegis.services.NotificationService.NOTIFICATION_VAULT_UNLOCKED);
super.onDestroy();
}


@java.lang.Override
public void onTaskRemoved(android.content.Intent rootIntent) {
super.onTaskRemoved(rootIntent);
stopSelf();
}


@androidx.annotation.Nullable
@java.lang.Override
public android.os.IBinder onBind(android.content.Intent intent) {
return null;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
