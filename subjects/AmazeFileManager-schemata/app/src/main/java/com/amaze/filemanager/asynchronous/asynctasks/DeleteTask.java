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
package com.amaze.filemanager.asynchronous.asynctasks;
import java.util.ArrayList;
import com.amaze.filemanager.ui.activities.MainActivity;
import org.slf4j.Logger;
import com.amaze.filemanager.utils.OTGUtil;
import com.cloudrail.si.interfaces.CloudStorage;
import com.amaze.filemanager.R;
import com.amaze.filemanager.database.CryptHandler;
import androidx.annotation.NonNull;
import com.amaze.filemanager.filesystem.files.CryptUtil;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import jcifs.smb.SmbException;
import static com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS;
import android.app.NotificationManager;
import com.amaze.filemanager.application.AppConfig;
import static com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_GENERAL;
import com.amaze.filemanager.filesystem.files.MediaConnectionUtils;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import androidx.documentfile.provider.DocumentFile;
import com.amaze.filemanager.filesystem.HybridFile;
import com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException;
import com.amaze.filemanager.ui.fragments.CompressedExplorerFragment;
import com.amaze.filemanager.filesystem.cloud.CloudUtil;
import com.amaze.filemanager.ui.notifications.NotificationConstants;
import com.amaze.filemanager.utils.DataUtils;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import com.amaze.filemanager.filesystem.SafRootHolder;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DeleteTask extends android.os.AsyncTask<java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable>, java.lang.String, com.amaze.filemanager.asynchronous.asynctasks.AsyncTaskResult<java.lang.Boolean>> {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.asynchronous.asynctasks.DeleteTask.class);

    private java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> files;

    private final android.content.Context applicationContext;

    private final boolean rootMode;

    private com.amaze.filemanager.ui.fragments.CompressedExplorerFragment compressedExplorerFragment;

    private boolean doDeletePermanently;

    private final com.amaze.filemanager.utils.DataUtils dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();

    public DeleteTask(@androidx.annotation.NonNull
    android.content.Context applicationContext, @androidx.annotation.NonNull
    boolean doDeletePermanently) {
        this.applicationContext = applicationContext.getApplicationContext();
        this.doDeletePermanently = doDeletePermanently;
        rootMode = androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ROOTMODE, false);
    }


    public DeleteTask(@androidx.annotation.NonNull
    android.content.Context applicationContext, com.amaze.filemanager.ui.fragments.CompressedExplorerFragment compressedExplorerFragment) {
        this.applicationContext = applicationContext.getApplicationContext();
        this.doDeletePermanently = false;
        rootMode = androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext).getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ROOTMODE, false);
        this.compressedExplorerFragment = compressedExplorerFragment;
    }


    @java.lang.Override
    protected void onProgressUpdate(java.lang.String... values) {
        super.onProgressUpdate(values);
        android.widget.Toast.makeText(applicationContext, values[0], android.widget.Toast.LENGTH_SHORT).show();
    }


    @java.lang.Override
    @java.lang.SafeVarargs
    protected final com.amaze.filemanager.asynchronous.asynctasks.AsyncTaskResult<java.lang.Boolean> doInBackground(final java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable>... p1) {
        files = p1[0];
        boolean wasDeleted;
        wasDeleted = true;
        if (files.size() == 0)
            return new com.amaze.filemanager.asynchronous.asynctasks.AsyncTaskResult<>(true);

        for (com.amaze.filemanager.filesystem.HybridFileParcelable file : files) {
            try {
                wasDeleted = doDeleteFile(file);
                if (!wasDeleted)
                    break;

            } catch (java.lang.Exception e) {
                return new com.amaze.filemanager.asynchronous.asynctasks.AsyncTaskResult<>(e);
            }
            // delete file from media database
            if ((!file.isSmb()) && (!file.isSftp()))
                com.amaze.filemanager.filesystem.files.MediaConnectionUtils.scanFile(applicationContext, files.toArray(new com.amaze.filemanager.filesystem.HybridFile[files.size()]));

            // delete file entry from encrypted database
            if (file.getName(applicationContext).endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION)) {
                com.amaze.filemanager.database.CryptHandler handler;
                handler = com.amaze.filemanager.database.CryptHandler.INSTANCE;
                handler.clear(file.getPath());
            }
        }
        return new com.amaze.filemanager.asynchronous.asynctasks.AsyncTaskResult<>(wasDeleted);
    }


    @java.lang.Override
    public void onPostExecute(com.amaze.filemanager.asynchronous.asynctasks.AsyncTaskResult<java.lang.Boolean> result) {
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // DeleteTask_0_NullIntentOperatorMutator
            case 15: {
                intent = null;
                break;
            }
            // DeleteTask_1_InvalidKeyIntentOperatorMutator
            case 1015: {
                intent = new android.content.Intent((String) null);
                break;
            }
            // DeleteTask_2_RandomActionIntentDefinitionOperatorMutator
            case 2015: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST);
            break;
        }
    }
    if (files.size() > 0) {
        java.lang.String path;
        path = files.get(0).getParent(applicationContext);
        switch(MUID_STATIC) {
            // DeleteTask_3_NullValueIntentPutExtraOperatorMutator
            case 3015: {
                intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, new Parcelable[0]);
                break;
            }
            // DeleteTask_4_IntentPayloadReplacementOperatorMutator
            case 4015: {
                intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, "");
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // DeleteTask_5_RandomActionIntentDefinitionOperatorMutator
                case 5015: {
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
                intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, path);
                break;
            }
        }
        break;
    }
}
applicationContext.sendBroadcast(intent);
}
if ((result.result == null) || (!result.result)) {
applicationContext.sendBroadcast(new android.content.Intent(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_GENERAL).putParcelableArrayListExtra(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS, files));
} else if (compressedExplorerFragment == null) {
com.amaze.filemanager.application.AppConfig.toast(applicationContext, com.amaze.filemanager.R.string.done);
}
if (compressedExplorerFragment != null) {
compressedExplorerFragment.files.clear();
}
// cancel any processing notification because of cut/paste operation
android.app.NotificationManager notificationManager;
notificationManager = ((android.app.NotificationManager) (applicationContext.getSystemService(android.content.Context.NOTIFICATION_SERVICE)));
notificationManager.cancel(com.amaze.filemanager.ui.notifications.NotificationConstants.COPY_ID);
}


private boolean doDeleteFile(@androidx.annotation.NonNull
com.amaze.filemanager.filesystem.HybridFileParcelable file) throws java.lang.Exception {
switch (file.getMode()) {
case OTG :
    androidx.documentfile.provider.DocumentFile documentFile;
    documentFile = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(file.getPath(), applicationContext, false);
    return documentFile.delete();
case DOCUMENT_FILE :
    documentFile = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(file.getPath(), com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), applicationContext, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, false);
    return documentFile.delete();
case DROPBOX :
case BOX :
case GDRIVE :
case ONEDRIVE :
    com.cloudrail.si.interfaces.CloudStorage cloudStorage;
    cloudStorage = dataUtils.getAccount(file.getMode());
    try {
        cloudStorage.delete(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(file.getMode(), file.getPath()));
        return true;
    } catch (java.lang.Exception e) {
        com.amaze.filemanager.asynchronous.asynctasks.DeleteTask.LOG.warn("failed to delete cloud files", e);
        return false;
    }
default :
    try {
        /* SMB and SFTP (or any remote files that may support in the future) should not be
        supported by recycle bin. - TranceLove
         */
        if (((!doDeletePermanently) && (!com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB.equals(file.getMode()))) && (!com.amaze.filemanager.fileoperations.filesystem.OpenMode.SFTP.equals(file.getMode()))) {
            return file.moveToBin(applicationContext);
        }
        return file.delete(applicationContext, rootMode);
    } catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException | jcifs.smb.SmbException e) {
        com.amaze.filemanager.asynchronous.asynctasks.DeleteTask.LOG.warn("failed to delete files", e);
        throw e;
    }
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
