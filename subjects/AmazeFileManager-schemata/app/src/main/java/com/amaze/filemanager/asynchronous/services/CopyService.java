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
import com.amaze.filemanager.filesystem.files.FileUtils;
import com.amaze.filemanager.utils.DatapointParcelable;
import java.util.ArrayList;
import androidx.core.app.NotificationManagerCompat;
import com.amaze.filemanager.ui.activities.MainActivity;
import org.slf4j.Logger;
import com.amaze.filemanager.asynchronous.asynctasks.DeleteTask;
import com.amaze.filemanager.R;
import com.amaze.filemanager.database.CryptHandler;
import android.os.IBinder;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import com.amaze.filemanager.filesystem.files.CryptUtil;
import android.widget.Toast;
import com.amaze.filemanager.filesystem.Operations;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.filesystem.FileProperties;
import com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil;
import com.amaze.filemanager.filesystem.root.MoveFileCommand;
import android.content.IntentFilter;
import com.amaze.filemanager.filesystem.root.CopyFilesCommand;
import android.content.SharedPreferences;
import com.amaze.filemanager.application.AppConfig;
import com.amaze.filemanager.utils.ProgressHandler;
import com.amaze.filemanager.database.models.explorer.EncryptedEntry;
import android.os.Bundle;
import java.io.IOException;
import com.amaze.filemanager.filesystem.files.MediaConnectionUtils;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import android.content.BroadcastReceiver;
import com.amaze.filemanager.filesystem.HybridFile;
import com.amaze.filemanager.utils.ObtainableServiceBinder;
import com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException;
import com.amaze.filemanager.filesystem.files.GenericCopyUtil;
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
public class CopyService extends com.amaze.filemanager.asynchronous.services.AbstractProgressiveService {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.asynchronous.services.CopyService.class);

    public static final java.lang.String TAG_IS_ROOT_EXPLORER = "is_root";

    public static final java.lang.String TAG_COPY_TARGET = "COPY_DIRECTORY";

    public static final java.lang.String TAG_COPY_SOURCES = "FILE_PATHS";

    public static final java.lang.String TAG_COPY_OPEN_MODE = "MODE";// target open mode


    public static final java.lang.String TAG_COPY_MOVE = "move";

    private static final java.lang.String TAG_COPY_START_ID = "id";

    public static final java.lang.String TAG_BROADCAST_COPY_CANCEL = "copycancel";

    private androidx.core.app.NotificationManagerCompat mNotifyManager;

    private androidx.core.app.NotificationCompat.Builder mBuilder;

    private android.content.Context c;

    private final android.os.IBinder mBinder = new com.amaze.filemanager.utils.ObtainableServiceBinder<>(this);

    private com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil watcherUtil;

    private final com.amaze.filemanager.utils.ProgressHandler progressHandler = new com.amaze.filemanager.utils.ProgressHandler();

    private com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.ProgressListener progressListener;

    // list of data packages, to initiate chart in process viewer fragment
    private final java.util.ArrayList<com.amaze.filemanager.utils.DatapointParcelable> dataPackages = new java.util.ArrayList<>();

    private android.widget.RemoteViews customSmallContentViews;

    private android.widget.RemoteViews customBigContentViews;

    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        switch(MUID_STATIC) {
            // CopyService_0_LengthyGUICreationOperatorMutator
            case 4: {
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
    c = getApplicationContext();
    switch(MUID_STATIC) {
        // CopyService_1_RandomActionIntentDefinitionOperatorMutator
        case 1004: {
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
        registerReceiver(receiver3, new android.content.IntentFilter(com.amaze.filemanager.asynchronous.services.CopyService.TAG_BROADCAST_COPY_CANCEL));
        break;
    }
}
}


@java.lang.Override
public int onStartCommand(android.content.Intent intent, int flags, final int startId) {
android.os.Bundle b;
b = new android.os.Bundle();
boolean isRootExplorer;
isRootExplorer = intent.getBooleanExtra(com.amaze.filemanager.asynchronous.services.CopyService.TAG_IS_ROOT_EXPLORER, false);
java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> files;
files = intent.getParcelableArrayListExtra(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_SOURCES);
java.lang.String targetPath;
targetPath = intent.getStringExtra(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_TARGET);
int mode;
mode = intent.getIntExtra(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_OPEN_MODE, com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN.ordinal());
final boolean move;
move = intent.getBooleanExtra(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_MOVE, false);
android.content.SharedPreferences sharedPreferences;
sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(c);
int accentColor;
accentColor = ((com.amaze.filemanager.application.AppConfig) (getApplication())).getUtilsProvider().getColorPreference().getCurrentUserColorPreferences(this, sharedPreferences).getAccent();
mNotifyManager = androidx.core.app.NotificationManagerCompat.from(getApplicationContext());
b.putInt(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_START_ID, startId);
android.content.Intent notificationIntent;
switch(MUID_STATIC) {
    // CopyService_2_NullIntentOperatorMutator
    case 2004: {
        notificationIntent = null;
        break;
    }
    // CopyService_3_InvalidKeyIntentOperatorMutator
    case 3004: {
        notificationIntent = new android.content.Intent((CopyService) null, com.amaze.filemanager.ui.activities.MainActivity.class);
        break;
    }
    // CopyService_4_RandomActionIntentDefinitionOperatorMutator
    case 4004: {
        notificationIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    notificationIntent = new android.content.Intent(this, com.amaze.filemanager.ui.activities.MainActivity.class);
    break;
}
}
switch(MUID_STATIC) {
// CopyService_5_RandomActionIntentDefinitionOperatorMutator
case 5004: {
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
// CopyService_6_RandomActionIntentDefinitionOperatorMutator
case 6004: {
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
// CopyService_7_NullValueIntentPutExtraOperatorMutator
case 7004: {
notificationIntent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_PROCESS_VIEWER, new Parcelable[0]);
break;
}
// CopyService_8_IntentPayloadReplacementOperatorMutator
case 8004: {
notificationIntent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_PROCESS_VIEWER, true);
break;
}
default: {
switch(MUID_STATIC) {
// CopyService_9_RandomActionIntentDefinitionOperatorMutator
case 9004: {
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
// CopyService_10_NullIntentOperatorMutator
case 10004: {
stopIntent = null;
break;
}
// CopyService_11_InvalidKeyIntentOperatorMutator
case 11004: {
stopIntent = new android.content.Intent((java.lang.String) null);
break;
}
// CopyService_12_RandomActionIntentDefinitionOperatorMutator
case 12004: {
stopIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
stopIntent = new android.content.Intent(com.amaze.filemanager.asynchronous.services.CopyService.TAG_BROADCAST_COPY_CANCEL);
break;
}
}
android.app.PendingIntent stopPendingIntent;
stopPendingIntent = android.app.PendingIntent.getBroadcast(c, 1234, stopIntent, com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.getPendingIntentFlag(android.app.PendingIntent.FLAG_UPDATE_CURRENT));
androidx.core.app.NotificationCompat.Action action;
action = new androidx.core.app.NotificationCompat.Action(com.amaze.filemanager.R.drawable.ic_content_copy_white_36dp, getString(com.amaze.filemanager.R.string.stop_ftp), stopPendingIntent);
mBuilder = new androidx.core.app.NotificationCompat.Builder(c, com.amaze.filemanager.ui.notifications.NotificationConstants.CHANNEL_NORMAL_ID).setContentIntent(pendingIntent).setSmallIcon(com.amaze.filemanager.R.drawable.ic_content_copy_white_36dp).setCustomContentView(customSmallContentViews).setCustomBigContentView(customBigContentViews).setCustomHeadsUpContentView(customSmallContentViews).setStyle(new androidx.core.app.NotificationCompat.DecoratedCustomViewStyle()).addAction(action).setOngoing(true).setColor(accentColor);
// set default notification views text
com.amaze.filemanager.ui.notifications.NotificationConstants.setMetadata(c, mBuilder, com.amaze.filemanager.ui.notifications.NotificationConstants.TYPE_NORMAL);
startForeground(com.amaze.filemanager.ui.notifications.NotificationConstants.COPY_ID, mBuilder.build());
initNotificationViews();
b.putBoolean(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_MOVE, move);
b.putString(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_TARGET, targetPath);
b.putInt(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_OPEN_MODE, mode);
b.putParcelableArrayList(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_SOURCES, files);
super.onStartCommand(intent, flags, startId);
super.progressHalted();
// going async
new com.amaze.filemanager.asynchronous.services.CopyService.DoInBackground(isRootExplorer).execute(b);
// If we get killed, after returning from here, restart
return android.app.Service.START_NOT_STICKY;
}


@java.lang.Override
protected androidx.core.app.NotificationManagerCompat getNotificationManager() {
return mNotifyManager;
}


@java.lang.Override
protected androidx.core.app.NotificationCompat.Builder getNotificationBuilder() {
return mBuilder;
}


@java.lang.Override
protected int getNotificationId() {
return com.amaze.filemanager.ui.notifications.NotificationConstants.COPY_ID;
}


@java.lang.Override
protected android.widget.RemoteViews getNotificationCustomViewSmall() {
return customSmallContentViews;
}


@java.lang.Override
protected android.widget.RemoteViews getNotificationCustomViewBig() {
return customBigContentViews;
}


@java.lang.Override
@androidx.annotation.StringRes
protected int getTitle(boolean move) {
return move ? com.amaze.filemanager.R.string.moving : com.amaze.filemanager.R.string.copying;
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


public void onDestroy() {
super.onDestroy();
unregisterReceiver(receiver3);
}


private class DoInBackground extends android.os.AsyncTask<android.os.Bundle, java.lang.Void, java.lang.Void> {
boolean move;

private com.amaze.filemanager.asynchronous.services.CopyService.DoInBackground.Copy copy;

private java.lang.String targetPath;

private final boolean isRootExplorer;

private int sourceProgress = 0;

private DoInBackground(boolean isRootExplorer) {
this.isRootExplorer = isRootExplorer;
}


protected java.lang.Void doInBackground(android.os.Bundle... p1) {
java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> sourceFiles;
sourceFiles = p1[0].getParcelableArrayList(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_SOURCES);
// setting up service watchers and initial data packages
// finding total size on background thread (this is necessary condition for SMB!)
long totalSize;
totalSize = com.amaze.filemanager.filesystem.files.FileUtils.getTotalBytes(sourceFiles, c);
int totalSourceFiles;
totalSourceFiles = sourceFiles.size();
progressHandler.setSourceSize(totalSourceFiles);
progressHandler.setTotalSize(totalSize);
progressHandler.setProgressListener((long speed) -> publishResults(speed, false, move));
watcherUtil = new com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil(progressHandler);
addFirstDatapoint(sourceFiles.get(0).getName(c), sourceFiles.size(), totalSize, move);
targetPath = p1[0].getString(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_TARGET);
move = p1[0].getBoolean(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_MOVE);
com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode;
openMode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.getOpenMode(p1[0].getInt(com.amaze.filemanager.asynchronous.services.CopyService.TAG_COPY_OPEN_MODE));
copy = new com.amaze.filemanager.asynchronous.services.CopyService.DoInBackground.Copy();
copy.execute(sourceFiles, targetPath, move, openMode);
if (copy.failedFOps.size() == 0) {
// adding/updating new encrypted db entry if any encrypted file was copied/moved
for (com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile : sourceFiles) {
try {
findAndReplaceEncryptedEntry(sourceFile);
} catch (java.lang.Exception e) {
// unable to modify encrypted entry in database
android.widget.Toast.makeText(c, getString(com.amaze.filemanager.R.string.encryption_fail_copy), android.widget.Toast.LENGTH_SHORT).show();
}
}
}
return null;
}


@java.lang.Override
public void onPostExecute(java.lang.Void b) {
super.onPostExecute(b);
// publishResults(b, "", totalSourceFiles, totalSourceFiles, totalSize, totalSize, 0, true,
// move);
// stopping watcher if not yet finished
watcherUtil.stopWatch();
finalizeNotification(copy.failedFOps, move);
android.content.Intent intent;
switch(MUID_STATIC) {
// CopyService_13_NullIntentOperatorMutator
case 13004: {
intent = null;
break;
}
// CopyService_14_InvalidKeyIntentOperatorMutator
case 14004: {
intent = new android.content.Intent((String) null);
break;
}
// CopyService_15_RandomActionIntentDefinitionOperatorMutator
case 15004: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST);
break;
}
}
switch(MUID_STATIC) {
// CopyService_16_NullValueIntentPutExtraOperatorMutator
case 16004: {
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, new Parcelable[0]);
break;
}
// CopyService_17_IntentPayloadReplacementOperatorMutator
case 17004: {
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, "");
break;
}
default: {
switch(MUID_STATIC) {
// CopyService_18_RandomActionIntentDefinitionOperatorMutator
case 18004: {
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
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, targetPath);
break;
}
}
break;
}
}
sendBroadcast(intent);
stopSelf();
}


/**
 * Iterates through every file to find an encrypted file and update/add a new entry about it's
 * metadata in the database
 *
 * @param sourceFile
 * 		the file which is to be iterated
 */
private void findAndReplaceEncryptedEntry(com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile) {
// even directories can end with CRYPT_EXTENSION
if (sourceFile.isDirectory() && (!sourceFile.getName(c).endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION))) {
sourceFile.forEachChildrenFile(getApplicationContext(), isRootExplorer, (com.amaze.filemanager.filesystem.HybridFileParcelable file) -> {
// iterating each file inside source files which were copied to find instance of
// any copied / moved encrypted file
findAndReplaceEncryptedEntry(file);
});
} else if (sourceFile.getName(c).endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION)) {
try {
com.amaze.filemanager.database.CryptHandler cryptHandler;
cryptHandler = com.amaze.filemanager.database.CryptHandler.INSTANCE;
com.amaze.filemanager.database.models.explorer.EncryptedEntry oldEntry;
oldEntry = cryptHandler.findEntry(sourceFile.getPath());
com.amaze.filemanager.database.models.explorer.EncryptedEntry newEntry;
newEntry = new com.amaze.filemanager.database.models.explorer.EncryptedEntry();
newEntry.setPassword(oldEntry.getPassword());
newEntry.setPath((targetPath + "/") + sourceFile.getName(c));
if (move) {
// file was been moved, update the existing entry
newEntry.setId(oldEntry.getId());
cryptHandler.updateEntry(oldEntry, newEntry);
} else {
// file was copied, create a new entry with same data
cryptHandler.addEntry(newEntry);
}
} catch (java.lang.Exception e) {
com.amaze.filemanager.asynchronous.services.CopyService.LOG.warn("failed to find and replace encrypted entry after copy", e);
// couldn't change the entry, leave it alone
}
}
}


class Copy {
java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile> failedFOps;

java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> toDelete;

Copy() {
failedFOps = new java.util.ArrayList<>();
toDelete = new java.util.ArrayList<>();
}


/**
 * Method iterate through files to be copied
 *
 * @param mode
 * 		target file open mode (current path's open mode)
 */
public void execute(final java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> sourceFiles, final java.lang.String targetPath, final boolean move, com.amaze.filemanager.fileoperations.filesystem.OpenMode mode) {
// initial start of copy, initiate the watcher
watcherUtil.watch(com.amaze.filemanager.asynchronous.services.CopyService.this);
if (com.amaze.filemanager.filesystem.FileProperties.checkFolder(targetPath, c) == 1) {
for (int i = 0; i < sourceFiles.size(); i++) {
sourceProgress = i;
com.amaze.filemanager.filesystem.HybridFileParcelable f1;
f1 = sourceFiles.get(i);
try {
com.amaze.filemanager.filesystem.HybridFile hFile;
if (targetPath.contains(getExternalCacheDir().getPath())) {
// the target open mode is not the one we're currently in!
// we're processing the file for cache
hFile = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, targetPath, sourceFiles.get(i).getName(c), f1.isDirectory());
} else {
// the target open mode is where we're currently at
hFile = new com.amaze.filemanager.filesystem.HybridFile(mode, targetPath, sourceFiles.get(i).getName(c), f1.isDirectory());
}
if (!progressHandler.getCancelled()) {
if (((f1.getMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT) || (mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT)) && isRootExplorer) {
// either source or target are in root
com.amaze.filemanager.asynchronous.services.CopyService.LOG.debug("either source or target are in root");
progressHandler.setSourceFilesProcessed(++sourceProgress);
copyRoot(f1, hFile, move);
continue;
}
progressHandler.setSourceFilesProcessed(++sourceProgress);
copyFiles(f1, hFile, progressHandler);
} else {
break;
}
} catch (java.lang.Exception e) {
com.amaze.filemanager.asynchronous.services.CopyService.LOG.error("Got exception checkout: " + f1.getPath(), e);
failedFOps.add(sourceFiles.get(i));
for (int j = i + 1; j < sourceFiles.size(); j++)
failedFOps.add(sourceFiles.get(j));

break;
}
}
} else if (isRootExplorer) {
for (int i = 0; i < sourceFiles.size(); i++) {
if (!progressHandler.getCancelled()) {
com.amaze.filemanager.filesystem.HybridFile hFile;
hFile = new com.amaze.filemanager.filesystem.HybridFile(mode, targetPath, sourceFiles.get(i).getName(c), sourceFiles.get(i).isDirectory());
progressHandler.setSourceFilesProcessed(++sourceProgress);
progressHandler.setFileName(sourceFiles.get(i).getName(c));
copyRoot(sourceFiles.get(i), hFile, move);
/* if(checkFiles(new HybridFile(sourceFiles.get(i).getMode(),path),
new HybridFile(OpenMode.ROOT,targetPath+"/"+name))){
failedFOps.add(sourceFiles.get(i));
}
 */
}
}
} else {
failedFOps.addAll(sourceFiles);
return;
}
// making sure to delete files after copy operation is done
// and not if the copy was cancelled
if (move && (!progressHandler.getCancelled())) {
java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> toDelete;
toDelete = new java.util.ArrayList<>();
for (com.amaze.filemanager.filesystem.HybridFileParcelable a : sourceFiles) {
if (!failedFOps.contains(a))
toDelete.add(a);

}
new com.amaze.filemanager.asynchronous.asynctasks.DeleteTask(c, true).execute(toDelete);
}
}


void copyRoot(com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile, com.amaze.filemanager.filesystem.HybridFile targetFile, boolean move) {
try {
if (!move) {
com.amaze.filemanager.filesystem.root.CopyFilesCommand.INSTANCE.copyFiles(sourceFile.getPath(), targetFile.getPath());
} else {
com.amaze.filemanager.filesystem.root.MoveFileCommand.INSTANCE.moveFile(sourceFile.getPath(), targetFile.getPath());
}
com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.position += sourceFile.getSize();
} catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException e) {
com.amaze.filemanager.asynchronous.services.CopyService.LOG.warn("failed to copy root file source: {} dest: {}", sourceFile.getPath(), targetFile.getPath(), e);
failedFOps.add(sourceFile);
}
com.amaze.filemanager.filesystem.files.MediaConnectionUtils.scanFile(c, new com.amaze.filemanager.filesystem.HybridFile[]{ targetFile });
}


private void copyFiles(final com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile, final com.amaze.filemanager.filesystem.HybridFile targetFile, final com.amaze.filemanager.utils.ProgressHandler progressHandler) throws java.io.IOException {
if (progressHandler.getCancelled())
return;

if (sourceFile.isDirectory()) {
if (!targetFile.exists()) {
targetFile.mkdir(c);
}
// various checks
// 1. source file and target file doesn't end up in loop
// 2. source file has a valid name or not
if ((!com.amaze.filemanager.filesystem.Operations.isFileNameValid(sourceFile.getName(c))) || com.amaze.filemanager.filesystem.Operations.isCopyLoopPossible(sourceFile, targetFile)) {
failedFOps.add(sourceFile);
return;
}
targetFile.setLastModified(sourceFile.lastModified());
if (progressHandler.getCancelled())
return;

sourceFile.forEachChildrenFile(c, false, (com.amaze.filemanager.filesystem.HybridFileParcelable file) -> {
com.amaze.filemanager.filesystem.HybridFile destFile;
destFile = new com.amaze.filemanager.filesystem.HybridFile(targetFile.getMode(), targetFile.getPath(), file.getName(c), file.isDirectory());
try {
copyFiles(file, destFile, progressHandler);
} catch (java.io.IOException e) {
throw new java.lang.IllegalStateException(e)// throw unchecked exception, no throws needed
;// throw unchecked exception, no throws needed

}
});
} else {
if (!com.amaze.filemanager.filesystem.Operations.isFileNameValid(sourceFile.getName(c))) {
failedFOps.add(sourceFile);
return;
}
com.amaze.filemanager.filesystem.files.GenericCopyUtil copyUtil;
copyUtil = new com.amaze.filemanager.filesystem.files.GenericCopyUtil(c, progressHandler);
progressHandler.setFileName(sourceFile.getName(c));
copyUtil.copy(sourceFile, targetFile, () -> {
// we ran out of memory to map the whole channel, let's switch to streams
com.amaze.filemanager.application.AppConfig.toast(c, c.getString(com.amaze.filemanager.R.string.copy_low_memory));
}, com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.UPDATE_POSITION);
}
}

}
}

private final android.content.BroadcastReceiver receiver3 = new android.content.BroadcastReceiver() {
@java.lang.Override
public void onReceive(android.content.Context context, android.content.Intent intent) {
// cancel operation
progressHandler.setCancelled(true);
}

};

@java.lang.Override
public android.os.IBinder onBind(android.content.Intent arg0) {
return mBinder;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
