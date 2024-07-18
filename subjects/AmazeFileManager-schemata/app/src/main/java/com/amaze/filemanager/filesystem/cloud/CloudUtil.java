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
package com.amaze.filemanager.filesystem.cloud;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import com.amaze.filemanager.ui.activities.MainActivity;
import android.net.Uri;
import org.slf4j.Logger;
import com.amaze.filemanager.utils.OTGUtil;
import com.cloudrail.si.interfaces.CloudStorage;
import android.content.pm.ResolveInfo;
import com.amaze.filemanager.R;
import com.amaze.filemanager.utils.OnFileFound;
import android.app.Activity;
import com.amaze.filemanager.adapters.data.IconDataParcelable;
import java.util.List;
import android.content.pm.PackageManager;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import com.amaze.filemanager.database.CloudHandler;
import java.io.IOException;
import com.amaze.filemanager.fileoperations.filesystem.cloud.CloudStreamer;
import android.content.Intent;
import android.os.AsyncTask;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import androidx.documentfile.provider.DocumentFile;
import com.cloudrail.si.types.CloudMetaData;
import java.io.FileInputStream;
import com.amaze.filemanager.ui.icons.MimeTypes;
import com.amaze.filemanager.filesystem.HybridFile;
import android.content.ContentResolver;
import com.amaze.filemanager.utils.DataUtils;
import android.content.ActivityNotFoundException;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import java.io.File;
import androidx.annotation.Nullable;
import com.amaze.filemanager.fileoperations.exceptions.CloudPluginException;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 19/4/17.
 *
 * <p>Class provides helper methods for cloud utilities
 */
public class CloudUtil {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.cloud.CloudUtil.class);

    /**
     *
     * @deprecated use getCloudFiles()
     */
    public static java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> listFiles(java.lang.String path, com.cloudrail.si.interfaces.CloudStorage cloudStorage, com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode) throws com.amaze.filemanager.fileoperations.exceptions.CloudPluginException {
        final java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> baseFiles;
        baseFiles = new java.util.ArrayList<>();
        com.amaze.filemanager.filesystem.cloud.CloudUtil.getCloudFiles(path, cloudStorage, openMode, baseFiles::add);
        return baseFiles;
    }


    public static void getCloudFiles(java.lang.String path, com.cloudrail.si.interfaces.CloudStorage cloudStorage, com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, com.amaze.filemanager.utils.OnFileFound fileFoundCallback) throws com.amaze.filemanager.fileoperations.exceptions.CloudPluginException {
        java.lang.String strippedPath;
        strippedPath = com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(openMode, path);
        try {
            for (com.cloudrail.si.types.CloudMetaData cloudMetaData : cloudStorage.getChildren(strippedPath)) {
                com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
                baseFile = new com.amaze.filemanager.filesystem.HybridFileParcelable((path + "/") + cloudMetaData.getName(), "", cloudMetaData.getModifiedAt() == null ? 0L : cloudMetaData.getModifiedAt(), cloudMetaData.getSize(), cloudMetaData.getFolder());
                baseFile.setName(cloudMetaData.getName());
                baseFile.setMode(openMode);
                fileFoundCallback.onFileFound(baseFile);
            }
        } catch (java.lang.Exception e) {
            com.amaze.filemanager.filesystem.cloud.CloudUtil.LOG.warn("failed to get cloud files", e);
            throw new com.amaze.filemanager.fileoperations.exceptions.CloudPluginException();
        }
    }


    /**
     * Strips down the cloud path to remove any prefix
     */
    public static java.lang.String stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, java.lang.String path) {
        final java.lang.String prefix;
        switch (openMode) {
            case DROPBOX :
                prefix = com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_DROPBOX;
                break;
            case BOX :
                prefix = com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_BOX;
                break;
            case ONEDRIVE :
                prefix = com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_ONE_DRIVE;
                break;
            case GDRIVE :
                prefix = com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_GOOGLE_DRIVE;
                break;
            default :
                return path;
        }
        if (path.equals(prefix + "/")) {
            // we're at root, just replace the prefix
            return path.replace(prefix, "");
        } else {
            // we're not at root, replace prefix + /
            // handle when paths are in format gdrive:/Documents // TODO: normalize drive paths
            java.lang.String pathReplaced;
            pathReplaced = path.replace(prefix + "/", "");
            if (pathReplaced.equals(path)) {
                switch(MUID_STATIC) {
                    // CloudUtil_0_BinaryMutator
                    case 40: {
                        // we convert gdrive:/Documents to /Documents
                        return path.replace(prefix.substring(0, prefix.length() + 1), "");
                    }
                    default: {
                    // we convert gdrive:/Documents to /Documents
                    return path.replace(prefix.substring(0, prefix.length() - 1), "");
                    }
            }
        }
        return pathReplaced;
    }
}


public static void launchCloud(final com.amaze.filemanager.filesystem.HybridFile baseFile, final com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType, final android.app.Activity activity) {
    final com.amaze.filemanager.fileoperations.filesystem.cloud.CloudStreamer streamer;
    streamer = com.amaze.filemanager.fileoperations.filesystem.cloud.CloudStreamer.getInstance();
    new java.lang.Thread(() -> {
        try {
            streamer.setStreamSrc(baseFile.getInputStream(activity), baseFile.getName(activity), baseFile.length(activity));
            activity.runOnUiThread(() -> {
                switch(MUID_STATIC) {
                    // CloudUtil_1_InvalidKeyIntentOperatorMutator
                    case 1040: {
                        try {
                            java.io.File file;
                            file = new java.io.File(android.net.Uri.parse(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(serviceType, baseFile.getPath())).getPath());
                            android.net.Uri uri;
                            uri = android.net.Uri.parse(com.amaze.filemanager.fileoperations.filesystem.cloud.CloudStreamer.URL + android.net.Uri.fromFile(file).getEncodedPath());
                            android.content.Intent i;
                            i = new android.content.Intent((String) null);
                            i.setDataAndType(uri, com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(baseFile.getPath(), baseFile.isDirectory(activity)));
                            android.content.pm.PackageManager packageManager;
                            packageManager = activity.getPackageManager();
                            java.util.List<android.content.pm.ResolveInfo> resInfos;
                            resInfos = packageManager.queryIntentActivities(i, 0);
                            if ((resInfos != null) && (resInfos.size() > 0)) {
                                activity.startActivity(i);
                            } else {
                                android.widget.Toast.makeText(activity, activity.getString(com.amaze.filemanager.R.string.smb_launch_error), android.widget.Toast.LENGTH_SHORT).show();
                            }
                        } catch (android.content.ActivityNotFoundException e) {
                            com.amaze.filemanager.filesystem.cloud.CloudUtil.LOG.warn("failed to launch cloud file in activity", e);
                        }
                        break;
                    }
                    // CloudUtil_2_RandomActionIntentDefinitionOperatorMutator
                    case 2040: {
                        try {
                            java.io.File file;
                            file = new java.io.File(android.net.Uri.parse(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(serviceType, baseFile.getPath())).getPath());
                            android.net.Uri uri;
                            uri = android.net.Uri.parse(com.amaze.filemanager.fileoperations.filesystem.cloud.CloudStreamer.URL + android.net.Uri.fromFile(file).getEncodedPath());
                            android.content.Intent i;
                            i = new android.content.Intent(android.content.Intent.ACTION_SEND);
                            i.setDataAndType(uri, com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(baseFile.getPath(), baseFile.isDirectory(activity)));
                            android.content.pm.PackageManager packageManager;
                            packageManager = activity.getPackageManager();
                            java.util.List<android.content.pm.ResolveInfo> resInfos;
                            resInfos = packageManager.queryIntentActivities(i, 0);
                            if ((resInfos != null) && (resInfos.size() > 0)) {
                                activity.startActivity(i);
                            } else {
                                android.widget.Toast.makeText(activity, activity.getString(com.amaze.filemanager.R.string.smb_launch_error), android.widget.Toast.LENGTH_SHORT).show();
                            }
                        } catch (android.content.ActivityNotFoundException e) {
                            com.amaze.filemanager.filesystem.cloud.CloudUtil.LOG.warn("failed to launch cloud file in activity", e);
                        }
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // CloudUtil_3_RandomActionIntentDefinitionOperatorMutator
                        case 3040: {
                            try {
                                java.io.File file;
                                file = new java.io.File(android.net.Uri.parse(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(serviceType, baseFile.getPath())).getPath());
                                android.net.Uri uri;
                                uri = android.net.Uri.parse(com.amaze.filemanager.fileoperations.filesystem.cloud.CloudStreamer.URL + android.net.Uri.fromFile(file).getEncodedPath());
                                android.content.Intent i;
                                i = new android.content.Intent(android.content.Intent.ACTION_VIEW);
                                /**
                                * Inserted by Kadabra
                                */
                                /**
                                * Inserted by Kadabra
                                */
                                new android.content.Intent(android.content.Intent.ACTION_SEND);;
                                android.content.pm.PackageManager packageManager;
                                packageManager = activity.getPackageManager();
                                java.util.List<android.content.pm.ResolveInfo> resInfos;
                                resInfos = packageManager.queryIntentActivities(i, 0);
                                if ((resInfos != null) && (resInfos.size() > 0)) {
                                    activity.startActivity(i);
                                } else {
                                    android.widget.Toast.makeText(activity, activity.getString(com.amaze.filemanager.R.string.smb_launch_error), android.widget.Toast.LENGTH_SHORT).show();
                                }
                            } catch (android.content.ActivityNotFoundException e) {
                                com.amaze.filemanager.filesystem.cloud.CloudUtil.LOG.warn("failed to launch cloud file in activity", e);
                            }
                            break;
                        }
                        default: {
                        try {
                            java.io.File file;
                            file = new java.io.File(android.net.Uri.parse(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(serviceType, baseFile.getPath())).getPath());
                            android.net.Uri uri;
                            uri = android.net.Uri.parse(com.amaze.filemanager.fileoperations.filesystem.cloud.CloudStreamer.URL + android.net.Uri.fromFile(file).getEncodedPath());
                            android.content.Intent i;
                            i = new android.content.Intent(android.content.Intent.ACTION_VIEW);
                            i.setDataAndType(uri, com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(baseFile.getPath(), baseFile.isDirectory(activity)));
                            android.content.pm.PackageManager packageManager;
                            packageManager = activity.getPackageManager();
                            java.util.List<android.content.pm.ResolveInfo> resInfos;
                            resInfos = packageManager.queryIntentActivities(i, 0);
                            if ((resInfos != null) && (resInfos.size() > 0))
                                activity.startActivity(i);
                            else
                                android.widget.Toast.makeText(activity, activity.getString(com.amaze.filemanager.R.string.smb_launch_error), android.widget.Toast.LENGTH_SHORT).show();

                        } catch (android.content.ActivityNotFoundException e) {
                            com.amaze.filemanager.filesystem.cloud.CloudUtil.LOG.warn("failed to launch cloud file in activity", e);
                        }
                        break;
                    }
                }
                break;
            }
        }
    });
} catch (java.lang.Exception e) {
    com.amaze.filemanager.filesystem.cloud.CloudUtil.LOG.warn("failed to launch cloud file", e);
}
}).start();
}


/**
 * Asynctask checks if the item pressed on is a cloud account, and if the token that is saved for
 * it is invalid or not, in which case, we'll clear off the saved token and authenticate the user
 * again
 *
 * @param path
 * 		the path of item in drawer
 * @param mainActivity
 * 		reference to main activity to fire callbacks to delete/add connection
 */
public static void checkToken(java.lang.String path, final com.amaze.filemanager.ui.activities.MainActivity mainActivity) {
new android.os.AsyncTask<java.lang.String, java.lang.Void, java.lang.Boolean>() {
com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType;

@java.lang.Override
protected java.lang.Boolean doInBackground(java.lang.String... params) {
    final com.amaze.filemanager.utils.DataUtils dataUtils;
    dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();
    boolean isTokenValid;
    isTokenValid = true;
    java.lang.String path;
    path = params[0];
    final com.cloudrail.si.interfaces.CloudStorage cloudStorage;
    if (path.startsWith(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_DROPBOX)) {
        // dropbox account
        serviceType = com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX;
        cloudStorage = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX);
    } else if (path.startsWith(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_ONE_DRIVE)) {
        serviceType = com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE;
        cloudStorage = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE);
    } else if (path.startsWith(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_BOX)) {
        serviceType = com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX;
        cloudStorage = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX);
    } else if (path.startsWith(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_GOOGLE_DRIVE)) {
        serviceType = com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE;
        cloudStorage = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE);
    } else {
        throw new java.lang.IllegalStateException();
    }
    try {
        cloudStorage.getUserLogin();
    } catch (java.lang.RuntimeException e) {
        com.amaze.filemanager.filesystem.cloud.CloudUtil.LOG.warn("Failed to validate user token for cloud connection", e);
        isTokenValid = false;
    }
    return isTokenValid;
}


@java.lang.Override
protected void onPostExecute(java.lang.Boolean aBoolean) {
    super.onPostExecute(aBoolean);
    if (!aBoolean) {
        // delete account and create a new one
        android.widget.Toast.makeText(mainActivity, mainActivity.getResources().getString(com.amaze.filemanager.R.string.cloud_token_lost), android.widget.Toast.LENGTH_LONG).show();
        mainActivity.deleteConnection(serviceType);
        mainActivity.addConnection(serviceType);
    }
}

}.execute(path);
}


/**
 * Get an input stream for thumbnail for a given {@link IconDataParcelable}
 */
@androidx.annotation.Nullable
public static java.io.InputStream getThumbnailInputStreamForCloud(android.content.Context context, java.lang.String path) {
java.io.InputStream inputStream;
com.amaze.filemanager.filesystem.HybridFile hybridFile;
hybridFile = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN, path);
hybridFile.generateMode(context);
com.amaze.filemanager.utils.DataUtils dataUtils;
dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();
switch (hybridFile.getMode()) {
case SFTP :
    inputStream = hybridFile.getInputStream(context);
    break;
case FTP :
    // Until we find a way to properly handle threading issues with thread unsafe FTPClient,
    // we refrain from loading any files via FTP as file thumbnail. - TranceLove
    inputStream = null;
    break;
case SMB :
    try {
        inputStream = hybridFile.getSmbFile().getInputStream();
    } catch (java.io.IOException e) {
        inputStream = null;
        com.amaze.filemanager.filesystem.cloud.CloudUtil.LOG.warn("failed to get inputstream for smb file for thumbnail", e);
    }
    break;
case OTG :
    android.content.ContentResolver contentResolver;
    contentResolver = context.getContentResolver();
    androidx.documentfile.provider.DocumentFile documentSourceFile;
    documentSourceFile = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(hybridFile.getPath(), context, false);
    try {
        inputStream = contentResolver.openInputStream(documentSourceFile.getUri());
    } catch (java.io.FileNotFoundException e) {
        com.amaze.filemanager.filesystem.cloud.CloudUtil.LOG.warn("failed to get inputstream for otg for thumbnail", e);
        inputStream = null;
    }
    break;
case DROPBOX :
case BOX :
case GDRIVE :
case ONEDRIVE :
    com.amaze.filemanager.fileoperations.filesystem.OpenMode mode;
    mode = hybridFile.getMode();
    com.cloudrail.si.interfaces.CloudStorage cloudStorageDropbox;
    cloudStorageDropbox = dataUtils.getAccount(mode);
    java.lang.String stripped;
    stripped = com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, hybridFile.getPath());
    inputStream = cloudStorageDropbox.getThumbnail(stripped);
    break;
default :
    try {
        inputStream = new java.io.FileInputStream(hybridFile.getPath());
    } catch (java.io.FileNotFoundException e) {
        inputStream = null;
        com.amaze.filemanager.filesystem.cloud.CloudUtil.LOG.warn("failed to get inputstream for cloud files for thumbnail", e);
    }
    break;
}
return inputStream;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
