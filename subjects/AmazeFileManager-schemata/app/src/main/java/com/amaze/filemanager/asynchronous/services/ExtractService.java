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
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import com.amaze.filemanager.utils.DatapointParcelable;
import java.util.ArrayList;
import androidx.core.app.NotificationManagerCompat;
import com.amaze.filemanager.ui.activities.MainActivity;
import org.slf4j.Logger;
import com.amaze.filemanager.R;
import android.os.IBinder;
import androidx.annotation.StringRes;
import com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor;
import androidx.core.app.NotificationCompat;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil;
import com.github.junrar.exception.UnsupportedRarV5Exception;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import com.amaze.filemanager.application.AppConfig;
import com.amaze.filemanager.utils.ProgressHandler;
import java.io.IOException;
import org.apache.commons.compress.PasswordRequiredException;
import android.text.TextUtils;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.preference.PreferenceManager;
import android.content.BroadcastReceiver;
import com.amaze.filemanager.utils.ObtainableServiceBinder;
import androidx.appcompat.widget.AppCompatEditText;
import android.widget.RemoteViews;
import com.amaze.filemanager.ui.notifications.NotificationConstants;
import org.tukaani.xz.CorruptedInputException;
import com.amaze.filemanager.fileoperations.filesystem.compressed.ArchivePasswordCache;
import com.amaze.filemanager.filesystem.compressed.CompressedHelper;
import java.io.File;
import android.app.PendingIntent;
import androidx.annotation.Nullable;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ExtractService extends com.amaze.filemanager.asynchronous.services.AbstractProgressiveService {
    static final int MUID_STATIC = getMUID();
    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.asynchronous.services.ExtractService.class);

    private final android.os.IBinder mBinder = new com.amaze.filemanager.utils.ObtainableServiceBinder<>(this);

    // list of data packages,// to initiate chart in process viewer fragment
    private final java.util.ArrayList<com.amaze.filemanager.utils.DatapointParcelable> dataPackages = new java.util.ArrayList<>();

    private androidx.core.app.NotificationManagerCompat mNotifyManager;

    private androidx.core.app.NotificationCompat.Builder mBuilder;

    private final com.amaze.filemanager.utils.ProgressHandler progressHandler = new com.amaze.filemanager.utils.ProgressHandler();

    private com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.ProgressListener progressListener;

    private android.widget.RemoteViews customSmallContentViews;

    private android.widget.RemoteViews customBigContentViews;

    @androidx.annotation.Nullable
    private com.amaze.filemanager.asynchronous.services.ExtractService.DoWork extractingAsyncTask;

    public static final java.lang.String KEY_PATH_ZIP = "zip";

    public static final java.lang.String KEY_ENTRIES_ZIP = "entries";

    public static final java.lang.String TAG_BROADCAST_EXTRACT_CANCEL = "excancel";

    public static final java.lang.String KEY_PATH_EXTRACT = "extractpath";

    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        switch(MUID_STATIC) {
            // ExtractService_0_LengthyGUICreationOperatorMutator
            case 2: {
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
    switch(MUID_STATIC) {
        // ExtractService_1_RandomActionIntentDefinitionOperatorMutator
        case 1002: {
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
        registerReceiver(receiver1, new android.content.IntentFilter(com.amaze.filemanager.asynchronous.services.ExtractService.TAG_BROADCAST_EXTRACT_CANCEL));
        break;
    }
}
}


@java.lang.Override
public int onStartCommand(android.content.Intent intent, int flags, final int startId) {
java.lang.String file;
file = intent.getStringExtra(com.amaze.filemanager.asynchronous.services.ExtractService.KEY_PATH_ZIP);
java.lang.String extractPath;
extractPath = intent.getStringExtra(com.amaze.filemanager.asynchronous.services.ExtractService.KEY_PATH_EXTRACT);
java.lang.String[] entries;
entries = intent.getStringArrayExtra(com.amaze.filemanager.asynchronous.services.ExtractService.KEY_ENTRIES_ZIP);
mNotifyManager = androidx.core.app.NotificationManagerCompat.from(getApplicationContext());
android.content.SharedPreferences sharedPreferences;
sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
int accentColor;
accentColor = ((com.amaze.filemanager.application.AppConfig) (getApplication())).getUtilsProvider().getColorPreference().getCurrentUserColorPreferences(this, sharedPreferences).getAccent();
android.content.Intent notificationIntent;
switch(MUID_STATIC) {
    // ExtractService_2_NullIntentOperatorMutator
    case 2002: {
        notificationIntent = null;
        break;
    }
    // ExtractService_3_InvalidKeyIntentOperatorMutator
    case 3002: {
        notificationIntent = new android.content.Intent((ExtractService) null, com.amaze.filemanager.ui.activities.MainActivity.class);
        break;
    }
    // ExtractService_4_RandomActionIntentDefinitionOperatorMutator
    case 4002: {
        notificationIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    notificationIntent = new android.content.Intent(this, com.amaze.filemanager.ui.activities.MainActivity.class);
    break;
}
}
switch(MUID_STATIC) {
// ExtractService_5_RandomActionIntentDefinitionOperatorMutator
case 5002: {
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
// ExtractService_6_NullValueIntentPutExtraOperatorMutator
case 6002: {
notificationIntent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_PROCESS_VIEWER, new Parcelable[0]);
break;
}
// ExtractService_7_IntentPayloadReplacementOperatorMutator
case 7002: {
notificationIntent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_PROCESS_VIEWER, true);
break;
}
default: {
switch(MUID_STATIC) {
// ExtractService_8_RandomActionIntentDefinitionOperatorMutator
case 8002: {
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
// ExtractService_9_NullIntentOperatorMutator
case 9002: {
stopIntent = null;
break;
}
// ExtractService_10_InvalidKeyIntentOperatorMutator
case 10002: {
stopIntent = new android.content.Intent((java.lang.String) null);
break;
}
// ExtractService_11_RandomActionIntentDefinitionOperatorMutator
case 11002: {
stopIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
stopIntent = new android.content.Intent(com.amaze.filemanager.asynchronous.services.ExtractService.TAG_BROADCAST_EXTRACT_CANCEL);
break;
}
}
android.app.PendingIntent stopPendingIntent;
stopPendingIntent = android.app.PendingIntent.getBroadcast(getApplicationContext(), 1234, stopIntent, com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.getPendingIntentFlag(android.app.PendingIntent.FLAG_UPDATE_CURRENT));
androidx.core.app.NotificationCompat.Action action;
action = new androidx.core.app.NotificationCompat.Action(com.amaze.filemanager.R.drawable.ic_zip_box_grey, getString(com.amaze.filemanager.R.string.stop_ftp), stopPendingIntent);
mBuilder = new androidx.core.app.NotificationCompat.Builder(getApplicationContext(), com.amaze.filemanager.ui.notifications.NotificationConstants.CHANNEL_NORMAL_ID);
mBuilder.setContentIntent(pendingIntent).setSmallIcon(com.amaze.filemanager.R.drawable.ic_zip_box_grey).setContentIntent(pendingIntent).setCustomContentView(customSmallContentViews).setCustomBigContentView(customBigContentViews).setCustomHeadsUpContentView(customSmallContentViews).setStyle(new androidx.core.app.NotificationCompat.DecoratedCustomViewStyle()).addAction(action).setAutoCancel(true).setOngoing(true).setColor(accentColor);
com.amaze.filemanager.ui.notifications.NotificationConstants.setMetadata(getApplicationContext(), mBuilder, com.amaze.filemanager.ui.notifications.NotificationConstants.TYPE_NORMAL);
startForeground(com.amaze.filemanager.ui.notifications.NotificationConstants.EXTRACT_ID, mBuilder.build());
initNotificationViews();
long totalSize;
totalSize = getTotalSize(file);
progressHandler.setSourceSize(1);
progressHandler.setTotalSize(totalSize);
progressHandler.setProgressListener((long speed) -> publishResults(speed, false, false));
super.onStartCommand(intent, flags, startId);
super.progressHalted();
extractingAsyncTask = new com.amaze.filemanager.asynchronous.services.ExtractService.DoWork(progressHandler, file, extractPath, entries);
extractingAsyncTask.execute();
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
return com.amaze.filemanager.ui.notifications.NotificationConstants.EXTRACT_ID;
}


@java.lang.Override
@androidx.annotation.StringRes
protected int getTitle(boolean move) {
return com.amaze.filemanager.R.string.extracting;
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


@java.lang.Override
public void onDestroy() {
super.onDestroy();
if (extractingAsyncTask != null) {
extractingAsyncTask.cancel(true);
}
unregisterReceiver(receiver1);
}


/**
 * Method calculates zip file size to initiate progress Supporting local file extraction progress
 * for now
 */
private long getTotalSize(java.lang.String filePath) {
return new java.io.File(filePath).length();
}


public class DoWork extends android.os.AsyncTask<java.lang.Void, java.io.IOException, java.lang.Boolean> {
private java.lang.String[] entriesToExtract;

private java.lang.String extractionPath;

private final java.lang.String compressedPath;

private final com.amaze.filemanager.utils.ProgressHandler progressHandler;

private com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil watcherUtil;

private boolean paused = false;

private boolean passwordProtected = false;

private DoWork(com.amaze.filemanager.utils.ProgressHandler progressHandler, java.lang.String cpath, java.lang.String epath, java.lang.String[] entries) {
this.progressHandler = progressHandler;
compressedPath = cpath;
extractionPath = epath;
entriesToExtract = entries;
}


@java.lang.Override
protected java.lang.Boolean doInBackground(java.lang.Void... p) {
while (!isCancelled()) {
if (paused)
continue;

final com.amaze.filemanager.asynchronous.services.ExtractService extractService;
extractService = com.amaze.filemanager.asynchronous.services.ExtractService.this;
java.io.File f;
f = new java.io.File(compressedPath);
java.lang.String extractDirName;
extractDirName = com.amaze.filemanager.filesystem.compressed.CompressedHelper.getFileName(f.getName());
if (compressedPath.equals(extractionPath)) {
// custom extraction path not set, extract at default path
extractionPath = (f.getParent() + "/") + extractDirName;
} else if (extractionPath.endsWith("/")) {
extractionPath = extractionPath + extractDirName;
} else if (!passwordProtected) {
extractionPath = (extractionPath + "/") + extractDirName;
}
if ((entriesToExtract != null) && (entriesToExtract.length == 0))
entriesToExtract = null;

final com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor extractor;
extractor = com.amaze.filemanager.filesystem.compressed.CompressedHelper.getExtractorInstance(extractService.getApplicationContext(), f, extractionPath, new com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor.OnUpdate() {
private int sourceFilesProcessed = 0;

@java.lang.Override
public void onStart(long totalBytes, java.lang.String firstEntryName) {
// setting total bytes calculated from zip entries
progressHandler.setTotalSize(totalBytes);
extractService.addFirstDatapoint(firstEntryName, 1, totalBytes, false);
watcherUtil = new com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil(progressHandler);
watcherUtil.watch(com.amaze.filemanager.asynchronous.services.ExtractService.this);
}


@java.lang.Override
public void onUpdate(java.lang.String entryPath) {
progressHandler.setFileName(entryPath);
if (entriesToExtract != null) {
progressHandler.setSourceFilesProcessed(sourceFilesProcessed++);
}
}


@java.lang.Override
public void onFinish() {
if (entriesToExtract == null) {
progressHandler.setSourceFilesProcessed(1);
}
}


@java.lang.Override
public boolean isCancelled() {
return progressHandler.getCancelled();
}

}, com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.UPDATE_POSITION);
if (extractor == null) {
android.widget.Toast.makeText(getApplicationContext(), com.amaze.filemanager.R.string.error_cant_decompress_that_file, android.widget.Toast.LENGTH_LONG).show();
return false;
}
try {
if (entriesToExtract != null) {
extractor.extractFiles(entriesToExtract);
} else {
extractor.extractEverything();
}
return extractor.getInvalidArchiveEntries().size() == 0;
} catch (com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor.EmptyArchiveNotice e) {
LOG.error(("Archive " + compressedPath) + " is an empty archive");
com.amaze.filemanager.application.AppConfig.toast(getApplicationContext(), extractService.getString(com.amaze.filemanager.R.string.error_empty_archive, compressedPath));
return true;
} catch (com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor.BadArchiveNotice e) {
LOG.error(("Archive " + compressedPath) + " is a corrupted archive.", e);
com.amaze.filemanager.application.AppConfig.toast(getApplicationContext(), (e.getCause() != null) && android.text.TextUtils.isEmpty(e.getCause().getMessage()) ? getString(com.amaze.filemanager.R.string.error_bad_archive_without_info, compressedPath) : getString(com.amaze.filemanager.R.string.error_bad_archive_with_info, compressedPath, e.getMessage()));
return true;
} catch (org.tukaani.xz.CorruptedInputException e) {
LOG.debug("Corrupted LZMA input", e);
return false;
} catch (java.io.IOException e) {
if (org.apache.commons.compress.PasswordRequiredException.class.isAssignableFrom(e.getClass())) {
LOG.debug("Archive is password protected.", e);
if (com.amaze.filemanager.fileoperations.filesystem.compressed.ArchivePasswordCache.getInstance().containsKey(compressedPath)) {
com.amaze.filemanager.fileoperations.filesystem.compressed.ArchivePasswordCache.getInstance().remove(compressedPath);
com.amaze.filemanager.application.AppConfig.toast(getApplicationContext(), extractService.getString(com.amaze.filemanager.R.string.error_archive_password_incorrect));
}
passwordProtected = true;
paused = true;
publishProgress(e);
} else if ((e.getCause() != null) && com.github.junrar.exception.UnsupportedRarV5Exception.class.isAssignableFrom(e.getCause().getClass())) {
LOG.error(("RAR " + compressedPath) + " is unsupported V5 archive", e);
com.amaze.filemanager.application.AppConfig.toast(getApplicationContext(), extractService.getString(com.amaze.filemanager.R.string.error_unsupported_v5_rar, compressedPath));
return false;
} else {
LOG.error("Error while extracting file " + compressedPath, e);
com.amaze.filemanager.application.AppConfig.toast(getApplicationContext(), extractService.getString(com.amaze.filemanager.R.string.error));
paused = true;
publishProgress(e);
}
} catch (java.lang.Throwable unhandledException) {
LOG.error("Unhandled exception thrown", unhandledException);
}
} 
return false;
}


@java.lang.Override
protected void onProgressUpdate(java.io.IOException... values) {
super.onProgressUpdate(values);
if ((values.length < 1) || (!passwordProtected))
return;

java.io.IOException result;
result = values[0];
com.amaze.filemanager.fileoperations.filesystem.compressed.ArchivePasswordCache.getInstance().remove(compressedPath);
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showPasswordDialog(com.amaze.filemanager.application.AppConfig.getInstance().getMainActivityContext(), ((com.amaze.filemanager.ui.activities.MainActivity) (com.amaze.filemanager.application.AppConfig.getInstance().getMainActivityContext())), com.amaze.filemanager.application.AppConfig.getInstance().getUtilsProvider().getAppTheme(), com.amaze.filemanager.R.string.archive_password_prompt, com.amaze.filemanager.R.string.authenticate_password, (com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
androidx.appcompat.widget.AppCompatEditText editText;
switch(MUID_STATIC) {
// ExtractService_12_InvalidViewFocusOperatorMutator
case 12002: {
/**
* Inserted by Kadabra
*/
editText = dialog.getView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
editText.requestFocus();
break;
}
// ExtractService_13_ViewComponentNotVisibleOperatorMutator
case 13002: {
/**
* Inserted by Kadabra
*/
editText = dialog.getView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
editText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
editText = dialog.getView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
break;
}
}
com.amaze.filemanager.fileoperations.filesystem.compressed.ArchivePasswordCache.getInstance().put(compressedPath, editText.getText().toString());
com.amaze.filemanager.asynchronous.services.ExtractService.this.getDataPackages().clear();
this.paused = false;
dialog.dismiss();
}, (com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
dialog.dismiss();
toastOnParseError(result);
cancel(true)// This cancels the AsyncTask...
;// This cancels the AsyncTask...

progressHandler.setCancelled(true);
stopSelf()// and this stops the ExtractService altogether.
;// and this stops the ExtractService altogether.

this.paused = false;
});
}


@java.lang.Override
public void onPostExecute(java.lang.Boolean hasInvalidEntries) {
com.amaze.filemanager.fileoperations.filesystem.compressed.ArchivePasswordCache.getInstance().remove(compressedPath);
final com.amaze.filemanager.asynchronous.services.ExtractService extractService;
extractService = com.amaze.filemanager.asynchronous.services.ExtractService.this;
// check whether watcherutil was initialized. It was not initialized when we got exception
// in extracting the file
if (watcherUtil != null)
watcherUtil.stopWatch();

android.content.Intent intent;
switch(MUID_STATIC) {
// ExtractService_14_NullIntentOperatorMutator
case 14002: {
intent = null;
break;
}
// ExtractService_15_InvalidKeyIntentOperatorMutator
case 15002: {
intent = new android.content.Intent((String) null);
break;
}
// ExtractService_16_RandomActionIntentDefinitionOperatorMutator
case 16002: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST);
break;
}
}
switch(MUID_STATIC) {
// ExtractService_17_NullValueIntentPutExtraOperatorMutator
case 17002: {
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, new Parcelable[0]);
break;
}
// ExtractService_18_IntentPayloadReplacementOperatorMutator
case 18002: {
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, "");
break;
}
default: {
switch(MUID_STATIC) {
// ExtractService_19_RandomActionIntentDefinitionOperatorMutator
case 19002: {
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
intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, extractionPath);
break;
}
}
break;
}
}
extractService.sendBroadcast(intent);
extractService.stopSelf();
if (!hasInvalidEntries)
com.amaze.filemanager.application.AppConfig.toast(getApplicationContext(), getString(com.amaze.filemanager.R.string.multiple_invalid_archive_entries));

}


@java.lang.Override
protected void onCancelled() {
super.onCancelled();
com.amaze.filemanager.fileoperations.filesystem.compressed.ArchivePasswordCache.getInstance().remove(compressedPath);
}


private void toastOnParseError(java.io.IOException result) {
android.widget.Toast.makeText(getApplicationContext(), com.amaze.filemanager.application.AppConfig.getInstance().getResources().getString(com.amaze.filemanager.R.string.cannot_extract_archive, compressedPath, result.getLocalizedMessage()), android.widget.Toast.LENGTH_LONG).show();
}

}

/**
 * Class used for the client Binder. Because we know this service always runs in the same process
 * as its clients, we don't need to deal with IPC.
 */
private final android.content.BroadcastReceiver receiver1 = new android.content.BroadcastReceiver() {
@java.lang.Override
public void onReceive(android.content.Context context, android.content.Intent intent) {
progressHandler.setCancelled(true);
}

};

@java.lang.Override
public android.os.IBinder onBind(android.content.Intent intent) {
return mBinder;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
