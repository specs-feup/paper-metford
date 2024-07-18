/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.asynchronous.services;
import com.amaze.filemanager.asynchronous.asynctasks.Task;
import com.amaze.filemanager.utils.AESCrypt;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import com.amaze.filemanager.utils.DatapointParcelable;
import java.util.ArrayList;
import androidx.core.app.NotificationManagerCompat;
import com.amaze.filemanager.ui.activities.MainActivity;
import com.amaze.filemanager.asynchronous.asynctasks.TaskKt;
import org.slf4j.Logger;
import static com.amaze.filemanager.asynchronous.services.EncryptService.TAG_PASSWORD;
import java.util.concurrent.Callable;
import com.amaze.filemanager.R;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import com.amaze.filemanager.filesystem.files.CryptUtil;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.filesystem.FileProperties;
import com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import com.amaze.filemanager.application.AppConfig;
import com.amaze.filemanager.utils.ProgressHandler;
import android.content.Intent;
import com.amaze.filemanager.filesystem.files.EncryptDecryptUtils;
import androidx.preference.PreferenceManager;
import android.content.BroadcastReceiver;
import com.amaze.filemanager.filesystem.HybridFile;
import com.amaze.filemanager.utils.ObtainableServiceBinder;
import android.widget.RemoteViews;
import com.amaze.filemanager.ui.notifications.NotificationConstants;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 28/11/2017, at 20:59.
 */
public class DecryptService extends com.amaze.filemanager.asynchronous.services.AbstractProgressiveService {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG_SOURCE = "crypt_source";// source file to encrypt or decrypt


    public static final java.lang.String TAG_DECRYPT_PATH = "decrypt_path";

    public static final java.lang.String TAG_OPEN_MODE = "open_mode";

    public static final java.lang.String TAG_BROADCAST_CRYPT_CANCEL = "crypt_cancel";

    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.asynchronous.services.DecryptService.class);

    private androidx.core.app.NotificationManagerCompat notificationManager;

    private androidx.core.app.NotificationCompat.Builder notificationBuilder;

    private android.content.Context context;

    private final android.os.IBinder mBinder = new com.amaze.filemanager.utils.ObtainableServiceBinder<>(this);

    private final com.amaze.filemanager.utils.ProgressHandler progressHandler = new com.amaze.filemanager.utils.ProgressHandler();

    private com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.ProgressListener progressListener;

    // list of data packages, to initiate chart in process viewer fragment
    private final java.util.ArrayList<com.amaze.filemanager.utils.DatapointParcelable> dataPackages = new java.util.ArrayList<>();

    private com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil serviceWatcherUtil;

    private long totalSize = 0L;

    private java.lang.String decryptPath;

    private com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;

    private final java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile> failedOps = new java.util.ArrayList<>();

    private java.lang.String password;

    private android.widget.RemoteViews customSmallContentViews;

    private android.widget.RemoteViews customBigContentViews;

    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        switch(MUID_STATIC) {
            // DecryptService_0_LengthyGUICreationOperatorMutator
            case 6: {
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
    context = getApplicationContext();
    switch(MUID_STATIC) {
        // DecryptService_1_RandomActionIntentDefinitionOperatorMutator
        case 1006: {
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
        registerReceiver(cancelReceiver, new android.content.IntentFilter(com.amaze.filemanager.asynchronous.services.DecryptService.TAG_BROADCAST_CRYPT_CANCEL));
        break;
    }
}
}


@java.lang.Override
public int onStartCommand(android.content.Intent intent, int flags, int startId) {
baseFile = intent.getParcelableExtra(com.amaze.filemanager.asynchronous.services.DecryptService.TAG_SOURCE);
password = intent.getStringExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_PASSWORD);
decryptPath = intent.getStringExtra(com.amaze.filemanager.asynchronous.services.DecryptService.TAG_DECRYPT_PATH);
android.content.SharedPreferences sharedPreferences;
sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
int accentColor;
accentColor = ((com.amaze.filemanager.application.AppConfig) (getApplication())).getUtilsProvider().getColorPreference().getCurrentUserColorPreferences(this, sharedPreferences).getAccent();
notificationManager = androidx.core.app.NotificationManagerCompat.from(getApplicationContext());
android.content.Intent notificationIntent;
switch(MUID_STATIC) {
    // DecryptService_2_NullIntentOperatorMutator
    case 2006: {
        notificationIntent = null;
        break;
    }
    // DecryptService_3_InvalidKeyIntentOperatorMutator
    case 3006: {
        notificationIntent = new android.content.Intent((DecryptService) null, com.amaze.filemanager.ui.activities.MainActivity.class);
        break;
    }
    // DecryptService_4_RandomActionIntentDefinitionOperatorMutator
    case 4006: {
        notificationIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    notificationIntent = new android.content.Intent(this, com.amaze.filemanager.ui.activities.MainActivity.class);
    break;
}
}
switch(MUID_STATIC) {
// DecryptService_5_RandomActionIntentDefinitionOperatorMutator
case 5006: {
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
notificationIntent.setAction(android.content.Intent.ACTION_MAIN);
break;
}
}
switch(MUID_STATIC) {
// DecryptService_6_RandomActionIntentDefinitionOperatorMutator
case 6006: {
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
notificationIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
break;
}
}
switch(MUID_STATIC) {
// DecryptService_7_NullValueIntentPutExtraOperatorMutator
case 7006: {
notificationIntent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_PROCESS_VIEWER, new Parcelable[0]);
break;
}
// DecryptService_8_IntentPayloadReplacementOperatorMutator
case 8006: {
notificationIntent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_PROCESS_VIEWER, true);
break;
}
default: {
switch(MUID_STATIC) {
// DecryptService_9_RandomActionIntentDefinitionOperatorMutator
case 9006: {
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
notificationIntent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_PROCESS_VIEWER, true);
break;
}
}
break;
}
}
android.app.PendingIntent pendingIntent;
pendingIntent = android.app.PendingIntent.getActivity(this, 0, notificationIntent, com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.getPendingIntentFlag(0));
customSmallContentViews = new android.widget.RemoteViews(getPackageName(), com.amaze.filemanager.R.layout.notification_service_small);
customBigContentViews = new android.widget.RemoteViews(getPackageName(), com.amaze.filemanager.R.layout.notification_service_big);
android.content.Intent stopIntent;
switch(MUID_STATIC) {
// DecryptService_10_NullIntentOperatorMutator
case 10006: {
stopIntent = null;
break;
}
// DecryptService_11_InvalidKeyIntentOperatorMutator
case 11006: {
stopIntent = new android.content.Intent((java.lang.String) null);
break;
}
// DecryptService_12_RandomActionIntentDefinitionOperatorMutator
case 12006: {
stopIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
stopIntent = new android.content.Intent(com.amaze.filemanager.asynchronous.services.DecryptService.TAG_BROADCAST_CRYPT_CANCEL);
break;
}
}
android.app.PendingIntent stopPendingIntent;
stopPendingIntent = android.app.PendingIntent.getBroadcast(context, 1234, stopIntent, com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.getPendingIntentFlag(android.app.PendingIntent.FLAG_UPDATE_CURRENT));
androidx.core.app.NotificationCompat.Action action;
action = new androidx.core.app.NotificationCompat.Action(com.amaze.filemanager.R.drawable.ic_folder_lock_open_white_36dp, getString(com.amaze.filemanager.R.string.stop_ftp), stopPendingIntent);
notificationBuilder = new androidx.core.app.NotificationCompat.Builder(this, com.amaze.filemanager.ui.notifications.NotificationConstants.CHANNEL_NORMAL_ID);
notificationBuilder.setContentIntent(pendingIntent).setCustomContentView(customSmallContentViews).setCustomBigContentView(customBigContentViews).setCustomHeadsUpContentView(customSmallContentViews).setStyle(new androidx.core.app.NotificationCompat.DecoratedCustomViewStyle()).addAction(action).setColor(accentColor).setOngoing(true).setSmallIcon(com.amaze.filemanager.R.drawable.ic_folder_lock_open_white_36dp);
com.amaze.filemanager.ui.notifications.NotificationConstants.setMetadata(getApplicationContext(), notificationBuilder, com.amaze.filemanager.ui.notifications.NotificationConstants.TYPE_NORMAL);
startForeground(getNotificationId(), notificationBuilder.build());
initNotificationViews();
super.onStartCommand(intent, flags, startId);
super.progressHalted();
com.amaze.filemanager.asynchronous.asynctasks.TaskKt.fromTask(new com.amaze.filemanager.asynchronous.services.DecryptService.BackgroundTask());
return android.app.Service.START_NOT_STICKY;
}


@java.lang.Override
protected androidx.core.app.NotificationManagerCompat getNotificationManager() {
return notificationManager;
}


@java.lang.Override
protected androidx.core.app.NotificationCompat.Builder getNotificationBuilder() {
return notificationBuilder;
}


@java.lang.Override
protected int getNotificationId() {
return com.amaze.filemanager.ui.notifications.NotificationConstants.DECRYPT_ID;
}


@java.lang.Override
@androidx.annotation.StringRes
protected int getTitle(boolean move) {
return com.amaze.filemanager.R.string.crypt_decrypting;
}


@java.lang.Override
protected android.widget.RemoteViews getNotificationCustomViewSmall() {
return customSmallContentViews;
}


@java.lang.Override
protected android.widget.RemoteViews getNotificationCustomViewBig() {
return customBigContentViews;
}


public com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.ProgressListener getProgressListener() {
return progressListener;
}


@java.lang.Override
public void setProgressListener(com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.ProgressListener progressListener) {
this.progressListener = progressListener;
}


@java.lang.Override
protected java.util.ArrayList<com.amaze.filemanager.utils.DatapointParcelable> getDataPackages() {
return dataPackages;
}


@java.lang.Override
protected com.amaze.filemanager.utils.ProgressHandler getProgressHandler() {
return progressHandler;
}


@java.lang.Override
protected void clearDataPackages() {
dataPackages.clear();
}


class BackgroundTask implements com.amaze.filemanager.asynchronous.asynctasks.Task<java.lang.Long, java.util.concurrent.Callable<java.lang.Long>> {
@java.lang.Override
public void onError(@androidx.annotation.NonNull
java.lang.Throwable error) {
LOG.warn("failed to decrypt", error);
}


@java.lang.Override
public void onFinish(java.lang.Long value) {
serviceWatcherUtil.stopWatch();
finalizeNotification(failedOps, false);
android.content.Intent intent;
switch(MUID_STATIC) {
// DecryptService_13_NullIntentOperatorMutator
case 13006: {
intent = null;
break;
}
// DecryptService_14_InvalidKeyIntentOperatorMutator
case 14006: {
intent = new android.content.Intent((String) null);
break;
}
// DecryptService_15_RandomActionIntentDefinitionOperatorMutator
case 15006: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.DECRYPT_BROADCAST);
break;
}
}
switch(MUID_STATIC) {
// DecryptService_16_NullValueIntentPutExtraOperatorMutator
case 16006: {
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, new Parcelable[0]);
break;
}
// DecryptService_17_IntentPayloadReplacementOperatorMutator
case 17006: {
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, "");
break;
}
default: {
switch(MUID_STATIC) {
// DecryptService_18_RandomActionIntentDefinitionOperatorMutator
case 18006: {
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
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, "");
break;
}
}
break;
}
}
sendBroadcast(intent);
stopSelf();
}


@androidx.annotation.NonNull
@java.lang.Override
public java.util.concurrent.Callable<java.lang.Long> getTask() {
return () -> {
java.lang.String baseFileFolder;
baseFileFolder = (baseFile.isDirectory()) ? baseFile.getPath() : baseFile.getPath().substring(0, baseFile.getPath().lastIndexOf('/'));
if (baseFile.isDirectory())
totalSize = baseFile.folderSize(context);
else
totalSize = baseFile.length(context);

progressHandler.setSourceSize(1);
progressHandler.setTotalSize(totalSize);
progressHandler.setProgressListener((long speed) -> publishResults(speed, false, false));
serviceWatcherUtil = new com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil(progressHandler);
addFirstDatapoint(baseFile.getName(context), 1, totalSize, false)// we're using encrypt as move flag false
;// we're using encrypt as move flag false

if (com.amaze.filemanager.filesystem.FileProperties.checkFolder(baseFileFolder, context) == 1) {
serviceWatcherUtil.watch(com.amaze.filemanager.asynchronous.services.DecryptService.this);
// we're here to decrypt, we'll decrypt at a custom path.
// the path is to the same directory as in encrypted one in normal case
// and the cache directory in case we're here because of the viewer
try {
new com.amaze.filemanager.filesystem.files.CryptUtil(context, baseFile, decryptPath, progressHandler, failedOps, password);
} catch (com.amaze.filemanager.utils.AESCrypt.DecryptFailureException ignored) {
} catch (java.lang.Exception e) {
LOG.error("Error decrypting " + baseFile.getPath(), e);
failedOps.add(baseFile);
}
}
return totalSize;
};
}

}

@java.lang.Override
public boolean isDecryptService() {
return true;
}


@java.lang.Override
public android.os.IBinder onBind(android.content.Intent intent) {
return mBinder;
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
this.unregisterReceiver(cancelReceiver);
}


private final android.content.BroadcastReceiver cancelReceiver = new android.content.BroadcastReceiver() {
@java.lang.Override
public void onReceive(android.content.Context context, android.content.Intent intent) {
// cancel operation
progressHandler.setCancelled(true);
}

};

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
