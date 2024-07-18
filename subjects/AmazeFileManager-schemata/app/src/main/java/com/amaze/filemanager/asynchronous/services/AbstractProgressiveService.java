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
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import com.amaze.filemanager.utils.DatapointParcelable;
import java.util.ArrayList;
import android.text.format.Formatter;
import androidx.core.app.NotificationManagerCompat;
import com.amaze.filemanager.ui.activities.MainActivity;
import static android.app.PendingIntent.FLAG_IMMUTABLE;
import org.slf4j.Logger;
import static android.os.Build.VERSION.SDK_INT;
import android.os.PowerManager;
import com.amaze.filemanager.R;
import static android.os.Build.VERSION_CODES.S;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil;
import com.amaze.filemanager.utils.ProgressHandler;
import com.amaze.filemanager.ui.fragments.ProcessViewerFragment;
import android.app.Service;
import android.content.Intent;
import com.amaze.filemanager.filesystem.HybridFile;
import androidx.annotation.CallSuper;
import android.widget.RemoteViews;
import com.amaze.filemanager.ui.notifications.NotificationConstants;
import com.amaze.filemanager.utils.Utils;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 28/11/2017, at 19:32.
 */
public abstract class AbstractProgressiveService extends android.app.Service implements com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks {
    static final int MUID_STATIC = getMUID();
    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.class);

    private boolean isNotificationTitleSet = false;

    private android.os.PowerManager.WakeLock wakeLock;

    @java.lang.Override
    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @java.lang.Override
    @androidx.annotation.CallSuper
    public void onCreate() {
        super.onCreate();
        switch(MUID_STATIC) {
            // AbstractProgressiveService_0_LengthyGUICreationOperatorMutator
            case 3: {
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
    final android.os.PowerManager powerManager;
    powerManager = ((android.os.PowerManager) (getSystemService(android.content.Context.POWER_SERVICE)));
    wakeLock = powerManager.newWakeLock(android.os.PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
    wakeLock.setReferenceCounted(false);
}


protected abstract androidx.core.app.NotificationManagerCompat getNotificationManager();


protected abstract androidx.core.app.NotificationCompat.Builder getNotificationBuilder();


protected abstract int getNotificationId();


@androidx.annotation.StringRes
protected abstract int getTitle(boolean move);


protected abstract android.widget.RemoteViews getNotificationCustomViewSmall();


protected abstract android.widget.RemoteViews getNotificationCustomViewBig();


public abstract com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.ProgressListener getProgressListener();


public abstract void setProgressListener(com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.ProgressListener progressListener);


/**
 *
 * @return list of data packages, to initiate chart in process viewer fragment
 */
protected abstract java.util.ArrayList<com.amaze.filemanager.utils.DatapointParcelable> getDataPackages();


protected abstract com.amaze.filemanager.utils.ProgressHandler getProgressHandler();


protected abstract void clearDataPackages();


@java.lang.Override
public void progressHalted() {
    // set notification to indeterminate unless progress resumes
    getNotificationCustomViewSmall().setProgressBar(com.amaze.filemanager.R.id.notification_service_progressBar_small, 0, 0, true);
    getNotificationCustomViewBig().setProgressBar(com.amaze.filemanager.R.id.notification_service_progressBar_big, 0, 0, true);
    getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_timeRemaining_big, getString(com.amaze.filemanager.R.string.unknown));
    getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_transferRate_big, getString(com.amaze.filemanager.R.string.unknown));
    getNotificationManager().notify(getNotificationId(), getNotificationBuilder().build());
}


@java.lang.Override
public void progressResumed() {
    // set notification to indeterminate unless progress resumes
    getNotificationCustomViewSmall().setProgressBar(com.amaze.filemanager.R.id.notification_service_progressBar_small, 100, java.lang.Math.round(getProgressHandler().getPercentProgress()), false);
    getNotificationCustomViewBig().setProgressBar(com.amaze.filemanager.R.id.notification_service_progressBar_big, 100, java.lang.Math.round(getProgressHandler().getPercentProgress()), false);
    getNotificationManager().notify(getNotificationId(), getNotificationBuilder().build());
}


@java.lang.Override
@androidx.annotation.CallSuper
public void onDestroy() {
    super.onDestroy();
    // remove the listener on destruction to prevent
    // implicit AbstractProgressiveService instance from leaking (as "this")
    getProgressHandler().setProgressListener(null);
    wakeLock.release();
    clearDataPackages();
}


/**
 * Publish the results of the progress to notification and {@link DatapointParcelable} and
 * eventually to {@link ProcessViewerFragment}
 *
 * @param speed
 * 		number of bytes being copied per sec
 * @param isComplete
 * 		whether operation completed or ongoing (not supported at the moment)
 * @param move
 * 		if the files are to be moved
 */
public final void publishResults(long speed, boolean isComplete, boolean move) {
    if (!getProgressHandler().getCancelled()) {
        java.lang.String fileName;
        fileName = getProgressHandler().getFileName();
        long totalSize;
        totalSize = getProgressHandler().getTotalSize();
        long writtenSize;
        writtenSize = getProgressHandler().getWrittenSize();
        if (!isNotificationTitleSet) {
            getNotificationBuilder().setSubText(getString(getTitle(move)));
            isNotificationTitleSet = true;
        }
        if (com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.state != com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks.STATE_HALTED) {
            java.lang.String written;
            written = (android.text.format.Formatter.formatFileSize(this, writtenSize) + "/") + android.text.format.Formatter.formatFileSize(this, totalSize);
            getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_filename_big, fileName);
            getNotificationCustomViewSmall().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_filename_small, fileName);
            getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_written_big, written);
            getNotificationCustomViewSmall().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_written_small, written);
            getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_transferRate_big, android.text.format.Formatter.formatFileSize(this, speed) + "/s");
            java.lang.String remainingTime;
            if (speed != 0) {
                switch(MUID_STATIC) {
                    // AbstractProgressiveService_1_BinaryMutator
                    case 1003: {
                        remainingTime = com.amaze.filemanager.utils.Utils.formatTimer(java.lang.Math.round((totalSize - writtenSize) * speed));
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // AbstractProgressiveService_2_BinaryMutator
                        case 2003: {
                            remainingTime = com.amaze.filemanager.utils.Utils.formatTimer(java.lang.Math.round((totalSize + writtenSize) / speed));
                            break;
                        }
                        default: {
                        remainingTime = com.amaze.filemanager.utils.Utils.formatTimer(java.lang.Math.round((totalSize - writtenSize) / speed));
                        break;
                    }
                }
                break;
            }
        }
    } else {
        remainingTime = getString(com.amaze.filemanager.R.string.unknown);
    }
    getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_timeRemaining_big, remainingTime);
    getNotificationCustomViewSmall().setProgressBar(com.amaze.filemanager.R.id.notification_service_progressBar_small, 100, java.lang.Math.round(getProgressHandler().getPercentProgress()), false);
    getNotificationCustomViewBig().setProgressBar(com.amaze.filemanager.R.id.notification_service_progressBar_big, 100, java.lang.Math.round(getProgressHandler().getPercentProgress()), false);
    getNotificationManager().notify(getNotificationId(), getNotificationBuilder().build());
}
if ((writtenSize == totalSize) || (totalSize == 0)) {
    if (move && (getNotificationId() == com.amaze.filemanager.ui.notifications.NotificationConstants.COPY_ID)) {
        // mBuilder.setContentTitle(getString(R.string.move_complete));
        // set progress to indeterminate as deletion might still be going on from source
        // while moving the file
        getNotificationCustomViewSmall().setProgressBar(com.amaze.filemanager.R.id.notification_service_progressBar_small, 0, 0, true);
        getNotificationCustomViewBig().setProgressBar(com.amaze.filemanager.R.id.notification_service_progressBar_big, 0, 0, true);
        getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_filename_big, getString(com.amaze.filemanager.R.string.processing));
        getNotificationCustomViewSmall().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_filename_small, getString(com.amaze.filemanager.R.string.processing));
        getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_timeRemaining_big, getString(com.amaze.filemanager.R.string.unknown));
        getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_transferRate_big, getString(com.amaze.filemanager.R.string.unknown));
        getNotificationBuilder().setOngoing(false);
        getNotificationBuilder().setAutoCancel(true);
        getNotificationManager().notify(getNotificationId(), getNotificationBuilder().build());
    } else {
        publishCompletedResult(getNotificationId());
    }
}
// for processviewer
com.amaze.filemanager.utils.DatapointParcelable intent;
intent = new com.amaze.filemanager.utils.DatapointParcelable(fileName, getProgressHandler().getSourceSize(), getProgressHandler().getSourceFilesProcessed(), totalSize, writtenSize, speed, move, isComplete);
// putDataPackage(intent);
addDatapoint(intent);
} else
publishCompletedResult(getNotificationId());

}


private void publishCompletedResult(int id1) {
try {
getNotificationManager().cancel(id1);
} catch (java.lang.Exception e) {
LOG.warn("failed to publish results", e);
}
}


protected void addFirstDatapoint(java.lang.String name, int amountOfFiles, long totalBytes, boolean move) {
if (!getDataPackages().isEmpty()) {
LOG.error("This is not the first datapoint!");
getDataPackages().clear();
}
com.amaze.filemanager.utils.DatapointParcelable intent1;
intent1 = com.amaze.filemanager.utils.DatapointParcelable.Companion.buildDatapointParcelable(name, amountOfFiles, totalBytes, move);
putDataPackage(intent1);
}


protected void addDatapoint(com.amaze.filemanager.utils.DatapointParcelable datapoint) {
if (getDataPackages().isEmpty()) {
LOG.error("This is the first datapoint!");
}
putDataPackage(datapoint);
if (getProgressListener() != null) {
getProgressListener().onUpdate(datapoint);
if (datapoint.getCompleted())
    getProgressListener().refresh();

}
}


/**
 * Returns the {@link #getDataPackages()} list which contains data to be transferred to {@link ProcessViewerFragment} Method call is synchronized so as to avoid modifying the list by {@link ServiceWatcherUtil#handlerThread} while {@link MainActivity#runOnUiThread(Runnable)} is
 * executing the callbacks in {@link ProcessViewerFragment}
 */
public final synchronized com.amaze.filemanager.utils.DatapointParcelable getDataPackage(int index) {
return getDataPackages().get(index);
}


public final synchronized int getDataPackageSize() {
return getDataPackages().size();
}


/**
 * Puts a {@link DatapointParcelable} into a list Method call is synchronized so as to avoid
 * modifying the list by {@link ServiceWatcherUtil#handlerThread} while {@link MainActivity#runOnUiThread(Runnable)} is executing the callbacks in {@link ProcessViewerFragment}
 */
private synchronized void putDataPackage(com.amaze.filemanager.utils.DatapointParcelable dataPackage) {
getDataPackages().add(dataPackage);
}


public interface ProgressListener {
void onUpdate(com.amaze.filemanager.utils.DatapointParcelable dataPackage);


void refresh();

}

@java.lang.Override
public boolean isDecryptService() {
return false;
}


/**
 * Displays a notification, sends intent and cancels progress if there were some failures
 */
void finalizeNotification(java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile> failedOps, boolean move) {
clearDataPackages();
if (!move)
getNotificationManager().cancelAll();

if (failedOps.size() == 0)
return;

androidx.core.app.NotificationCompat.Builder mBuilder;
mBuilder = new androidx.core.app.NotificationCompat.Builder(getApplicationContext(), com.amaze.filemanager.ui.notifications.NotificationConstants.CHANNEL_NORMAL_ID);
mBuilder.setContentTitle(getString(com.amaze.filemanager.R.string.operation_unsuccesful));
mBuilder.setContentText(getString(com.amaze.filemanager.R.string.copy_error, getString(getTitle(move)).toLowerCase()));
mBuilder.setAutoCancel(true);
getProgressHandler().setCancelled(true);
android.content.Intent intent;
switch(MUID_STATIC) {
// AbstractProgressiveService_3_NullIntentOperatorMutator
case 3003: {
    intent = null;
    break;
}
// AbstractProgressiveService_4_InvalidKeyIntentOperatorMutator
case 4003: {
    intent = new android.content.Intent((AbstractProgressiveService) null, com.amaze.filemanager.ui.activities.MainActivity.class);
    break;
}
// AbstractProgressiveService_5_RandomActionIntentDefinitionOperatorMutator
case 5003: {
    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent = new android.content.Intent(this, com.amaze.filemanager.ui.activities.MainActivity.class);
break;
}
}
switch(MUID_STATIC) {
// AbstractProgressiveService_6_NullValueIntentPutExtraOperatorMutator
case 6003: {
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS, new Parcelable[0]);
break;
}
// AbstractProgressiveService_7_IntentPayloadReplacementOperatorMutator
case 7003: {
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS, (java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile>) null);
break;
}
default: {
switch(MUID_STATIC) {
// AbstractProgressiveService_8_RandomActionIntentDefinitionOperatorMutator
case 8003: {
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
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS, failedOps);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// AbstractProgressiveService_9_NullValueIntentPutExtraOperatorMutator
case 9003: {
intent.putExtra("move", new Parcelable[0]);
break;
}
// AbstractProgressiveService_10_IntentPayloadReplacementOperatorMutator
case 10003: {
intent.putExtra("move", true);
break;
}
default: {
switch(MUID_STATIC) {
// AbstractProgressiveService_11_RandomActionIntentDefinitionOperatorMutator
case 11003: {
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
intent.putExtra("move", move);
break;
}
}
break;
}
}
android.app.PendingIntent pIntent;
pIntent = android.app.PendingIntent.getActivity(this, 101, intent, com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.getPendingIntentFlag(android.app.PendingIntent.FLAG_UPDATE_CURRENT));
mBuilder.setContentIntent(pIntent);
mBuilder.setSmallIcon(com.amaze.filemanager.R.drawable.ic_folder_lock_open_white_36dp);
getNotificationManager().notify(com.amaze.filemanager.ui.notifications.NotificationConstants.FAILED_ID, mBuilder.build());
switch(MUID_STATIC) {
// AbstractProgressiveService_12_NullIntentOperatorMutator
case 12003: {
intent = null;
break;
}
// AbstractProgressiveService_13_InvalidKeyIntentOperatorMutator
case 13003: {
intent = new android.content.Intent((String) null);
break;
}
// AbstractProgressiveService_14_RandomActionIntentDefinitionOperatorMutator
case 14003: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_GENERAL);
break;
}
}
switch(MUID_STATIC) {
// AbstractProgressiveService_15_NullValueIntentPutExtraOperatorMutator
case 15003: {
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS, new Parcelable[0]);
break;
}
// AbstractProgressiveService_16_IntentPayloadReplacementOperatorMutator
case 16003: {
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS, (java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile>) null);
break;
}
default: {
switch(MUID_STATIC) {
// AbstractProgressiveService_17_RandomActionIntentDefinitionOperatorMutator
case 17003: {
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
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS, failedOps);
break;
}
}
break;
}
}
sendBroadcast(intent);
}


/**
 * Initializes notification views to initial (processing..) state
 */
public void initNotificationViews() {
getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_filename_big, getString(com.amaze.filemanager.R.string.processing));
getNotificationCustomViewSmall().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_filename_small, getString(com.amaze.filemanager.R.string.processing));
java.lang.String zeroBytesFormat;
zeroBytesFormat = android.text.format.Formatter.formatFileSize(this, 0L);
getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_written_big, zeroBytesFormat);
getNotificationCustomViewSmall().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_written_small, zeroBytesFormat);
getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_transferRate_big, zeroBytesFormat + "/s");
getNotificationCustomViewBig().setTextViewText(com.amaze.filemanager.R.id.notification_service_textView_timeRemaining_big, getString(com.amaze.filemanager.R.string.unknown));
getNotificationCustomViewSmall().setProgressBar(com.amaze.filemanager.R.id.notification_service_progressBar_small, 0, 0, true);
getNotificationCustomViewBig().setProgressBar(com.amaze.filemanager.R.id.notification_service_progressBar_big, 0, 0, true);
getNotificationManager().notify(getNotificationId(), getNotificationBuilder().build());
}


/**
 * For compatibility purposes. Wraps the pending intent flag, return with FLAG_IMMUTABLE if device
 * SDK >= 32.
 *
 * @see PendingIntent.FLAG_IMMUTABLE
 * @param pendingIntentFlag
 * 		proposed PendingIntent flag
 * @return original PendingIntent flag if SDK < 32, otherwise adding FLAG_IMMUTABLE flag.
 */
public static int getPendingIntentFlag(final int pendingIntentFlag) {
if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S) {
return pendingIntentFlag;
} else {
return pendingIntentFlag | android.app.PendingIntent.FLAG_IMMUTABLE;
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
