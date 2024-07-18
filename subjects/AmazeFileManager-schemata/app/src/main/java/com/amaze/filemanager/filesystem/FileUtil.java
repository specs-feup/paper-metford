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
package com.amaze.filemanager.filesystem;
import java.io.OutputStream;
import com.amaze.filemanager.exceptions.NotAllowedException;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import com.amaze.filemanager.ui.activities.MainActivity;
import android.net.Uri;
import org.slf4j.Logger;
import com.amaze.filemanager.utils.OTGUtil;
import com.cloudrail.si.interfaces.CloudStorage;
import java.io.BufferedInputStream;
import com.amaze.filemanager.R;
import androidx.annotation.NonNull;
import android.os.Build;
import java.util.List;
import android.widget.Toast;
import java.io.BufferedOutputStream;
import com.amaze.filemanager.exceptions.OperationWouldOverwriteException;
import org.slf4j.LoggerFactory;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.Maybe;
import com.amaze.filemanager.application.AppConfig;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import kotlin.NotImplementedError;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import androidx.documentfile.provider.DocumentFile;
import jcifs.smb.SmbFile;
import android.content.ContentResolver;
import io.reactivex.MaybeObserver;
import com.amaze.filemanager.filesystem.files.GenericCopyUtil;
import java.io.FileOutputStream;
import com.amaze.filemanager.filesystem.cloud.CloudUtil;
import com.amaze.filemanager.utils.DataUtils;
import android.media.MediaScannerConnection;
import com.amaze.filemanager.utils.smb.SmbUtil;
import java.io.File;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Utility class for helping parsing file systems.
 */
public abstract class FileUtil {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.FileUtil.class);

    /**
     * Determine the camera folder. There seems to be no Android API to work for real devices, so this
     * is a best guess.
     *
     * @return the default camera folder.
     */
    // TODO the function?
    @androidx.annotation.Nullable
    public static java.io.OutputStream getOutputStream(final java.io.File target, android.content.Context context) throws java.io.FileNotFoundException {
        java.io.OutputStream outStream;
        outStream = null;
        // First try the normal way
        if (com.amaze.filemanager.filesystem.FileProperties.isWritable(target)) {
            // standard way
            outStream = new java.io.FileOutputStream(target);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Storage Access Framework
            androidx.documentfile.provider.DocumentFile targetDocument;
            targetDocument = com.amaze.filemanager.filesystem.ExternalSdCardOperation.getDocumentFile(target, false, context);
            if (targetDocument == null)
                return null;

            outStream = context.getContentResolver().openOutputStream(targetDocument.getUri());
        } else if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.KITKAT) {
            // Workaround for Kitkat ext SD card
            return com.amaze.filemanager.filesystem.MediaStoreHack.getOutputStream(context, target.getPath());
        }
        return outStream;
    }


    /**
     * Writes uri stream from external application to the specified path
     */
    public static final void writeUriToStorage(@androidx.annotation.NonNull
    final com.amaze.filemanager.ui.activities.MainActivity mainActivity, @androidx.annotation.NonNull
    final java.util.List<android.net.Uri> uris, @androidx.annotation.NonNull
    final android.content.ContentResolver contentResolver, @androidx.annotation.NonNull
    final java.lang.String currentPath) {
        io.reactivex.MaybeOnSubscribe<java.util.List<java.lang.String>> writeUri;
        writeUri = ((io.reactivex.MaybeOnSubscribe<java.util.List<java.lang.String>>) ((io.reactivex.MaybeEmitter<java.util.List<java.lang.String>> emitter) -> {
            java.util.List<java.lang.String> retval;
            retval = new java.util.ArrayList<>();
            for (android.net.Uri uri : uris) {
                java.io.BufferedInputStream bufferedInputStream;
                bufferedInputStream = null;
                try {
                    bufferedInputStream = new java.io.BufferedInputStream(contentResolver.openInputStream(uri));
                } catch (java.io.FileNotFoundException e) {
                    emitter.onError(e);
                    return;
                }
                java.io.BufferedOutputStream bufferedOutputStream;
                bufferedOutputStream = null;
                switch(MUID_STATIC) {
                    // FileUtil_0_BinaryMutator
                    case 42: {
                        try {
                            androidx.documentfile.provider.DocumentFile documentFile;
                            documentFile = androidx.documentfile.provider.DocumentFile.fromSingleUri(mainActivity, uri);
                            java.lang.String filename;
                            filename = documentFile.getName();
                            if (filename == null) {
                                filename = uri.getLastPathSegment();
                                // For cleaning up slashes. Back in #1217 there is a case of
                                // Uri.getLastPathSegment() end up with a full file path
                                if (filename.contains("/")) {
                                    filename = filename.substring(filename.lastIndexOf('/') - 1);
                                }
                            }
                            java.lang.String finalFilePath;
                            finalFilePath = (currentPath + "/") + filename;
                            com.amaze.filemanager.utils.DataUtils dataUtils;
                            dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();
                            com.amaze.filemanager.filesystem.HybridFile hFile;
                            hFile = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN, currentPath);
                            hFile.generateMode(mainActivity);
                            switch (hFile.getMode()) {
                                case FILE :
                                case ROOT :
                                java.io.File targetFile;
                                targetFile = new java.io.File(finalFilePath);
                                if (!com.amaze.filemanager.filesystem.FileProperties.isWritableNormalOrSaf(targetFile.getParentFile(), mainActivity.getApplicationContext())) {
                                    emitter.onError(new com.amaze.filemanager.exceptions.NotAllowedException());
                                    return;
                                }
                                androidx.documentfile.provider.DocumentFile targetDocumentFile;
                                targetDocumentFile = com.amaze.filemanager.filesystem.ExternalSdCardOperation.getDocumentFile(targetFile, false, mainActivity.getApplicationContext());
                                // Fallback, in case getDocumentFile() didn't properly return a
                                // DocumentFile
                                // instance
                                if (targetDocumentFile == null) {
                                    targetDocumentFile = androidx.documentfile.provider.DocumentFile.fromFile(targetFile);
                                }
                                // Lazy check... and in fact, different apps may pass in URI in different
                                // formats, so we could only check filename matches
                                // FIXME?: Prompt overwrite instead of simply blocking
                                if (targetDocumentFile.exists() && (targetDocumentFile.length() > 0)) {
                                    emitter.onError(new com.amaze.filemanager.exceptions.OperationWouldOverwriteException());
                                    return;
                                }
                                bufferedOutputStream = new java.io.BufferedOutputStream(contentResolver.openOutputStream(targetDocumentFile.getUri()));
                                retval.add(targetFile.getPath());
                                break;
                                case SMB :
                                jcifs.smb.SmbFile targetSmbFile;
                                targetSmbFile = com.amaze.filemanager.utils.smb.SmbUtil.create(finalFilePath);
                                if (targetSmbFile.exists()) {
                                    emitter.onError(new com.amaze.filemanager.exceptions.OperationWouldOverwriteException());
                                    return;
                                } else {
                                    java.io.OutputStream outputStream;
                                    outputStream = targetSmbFile.getOutputStream();
                                    bufferedOutputStream = new java.io.BufferedOutputStream(outputStream);
                                    retval.add(com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(targetSmbFile.getPath()));
                                }
                                break;
                                case SFTP :
                                // FIXME: implement support
                                com.amaze.filemanager.application.AppConfig.toast(mainActivity, mainActivity.getString(com.amaze.filemanager.R.string.not_allowed));
                                emitter.onError(new kotlin.NotImplementedError());
                                return;
                                case DROPBOX :
                                case BOX :
                                case ONEDRIVE :
                                case GDRIVE :
                                com.amaze.filemanager.fileoperations.filesystem.OpenMode mode;
                                mode = hFile.getMode();
                                com.cloudrail.si.interfaces.CloudStorage cloudStorage;
                                cloudStorage = dataUtils.getAccount(mode);
                                java.lang.String path;
                                path = com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, finalFilePath);
                                cloudStorage.upload(path, bufferedInputStream, documentFile.length(), true);
                                retval.add(path);
                                break;
                                case OTG :
                                androidx.documentfile.provider.DocumentFile documentTargetFile;
                                documentTargetFile = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(finalFilePath, mainActivity, true);
                                if (documentTargetFile.exists()) {
                                    emitter.onError(new com.amaze.filemanager.exceptions.OperationWouldOverwriteException());
                                    return;
                                }
                                bufferedOutputStream = new java.io.BufferedOutputStream(contentResolver.openOutputStream(documentTargetFile.getUri()), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
                                retval.add(documentTargetFile.getUri().getPath());
                                break;
                                default :
                                return;
                            }
                            int count;
                            count = 0;
                            byte[] buffer;
                            buffer = new byte[com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE];
                            while (count != (-1)) {
                                count = bufferedInputStream.read(buffer);
                                if (count != (-1)) {
                                    bufferedOutputStream.write(buffer, 0, count);
                                }
                            }
                            bufferedOutputStream.flush();
                        } catch (java.io.IOException e) {
                            emitter.onError(e);
                            return;
                        } finally {
                            try {
                                if (bufferedInputStream != null) {
                                    bufferedInputStream.close();
                                }
                                if (bufferedOutputStream != null) {
                                    bufferedOutputStream.close();
                                }
                            } catch (java.io.IOException e) {
                                emitter.onError(e);
                            }
                        }
                        break;
                    }
                    default: {
                    try {
                        androidx.documentfile.provider.DocumentFile documentFile;
                        documentFile = androidx.documentfile.provider.DocumentFile.fromSingleUri(mainActivity, uri);
                        java.lang.String filename;
                        filename = documentFile.getName();
                        if (filename == null) {
                            filename = uri.getLastPathSegment();
                            // For cleaning up slashes. Back in #1217 there is a case of
                            // Uri.getLastPathSegment() end up with a full file path
                            if (filename.contains("/"))
                                filename = filename.substring(filename.lastIndexOf('/') + 1);

                        }
                        java.lang.String finalFilePath;
                        finalFilePath = (currentPath + "/") + filename;
                        com.amaze.filemanager.utils.DataUtils dataUtils;
                        dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();
                        com.amaze.filemanager.filesystem.HybridFile hFile;
                        hFile = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN, currentPath);
                        hFile.generateMode(mainActivity);
                        switch (hFile.getMode()) {
                            case FILE :
                            case ROOT :
                                java.io.File targetFile;
                                targetFile = new java.io.File(finalFilePath);
                                if (!com.amaze.filemanager.filesystem.FileProperties.isWritableNormalOrSaf(targetFile.getParentFile(), mainActivity.getApplicationContext())) {
                                    emitter.onError(new com.amaze.filemanager.exceptions.NotAllowedException());
                                    return;
                                }
                                androidx.documentfile.provider.DocumentFile targetDocumentFile;
                                targetDocumentFile = com.amaze.filemanager.filesystem.ExternalSdCardOperation.getDocumentFile(targetFile, false, mainActivity.getApplicationContext());
                                // Fallback, in case getDocumentFile() didn't properly return a
                                // DocumentFile
                                // instance
                                if (targetDocumentFile == null) {
                                    targetDocumentFile = androidx.documentfile.provider.DocumentFile.fromFile(targetFile);
                                }
                                // Lazy check... and in fact, different apps may pass in URI in different
                                // formats, so we could only check filename matches
                                // FIXME?: Prompt overwrite instead of simply blocking
                                if (targetDocumentFile.exists() && (targetDocumentFile.length() > 0)) {
                                    emitter.onError(new com.amaze.filemanager.exceptions.OperationWouldOverwriteException());
                                    return;
                                }
                                bufferedOutputStream = new java.io.BufferedOutputStream(contentResolver.openOutputStream(targetDocumentFile.getUri()));
                                retval.add(targetFile.getPath());
                                break;
                            case SMB :
                                jcifs.smb.SmbFile targetSmbFile;
                                targetSmbFile = com.amaze.filemanager.utils.smb.SmbUtil.create(finalFilePath);
                                if (targetSmbFile.exists()) {
                                    emitter.onError(new com.amaze.filemanager.exceptions.OperationWouldOverwriteException());
                                    return;
                                } else {
                                    java.io.OutputStream outputStream;
                                    outputStream = targetSmbFile.getOutputStream();
                                    bufferedOutputStream = new java.io.BufferedOutputStream(outputStream);
                                    retval.add(com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(targetSmbFile.getPath()));
                                }
                                break;
                            case SFTP :
                                // FIXME: implement support
                                com.amaze.filemanager.application.AppConfig.toast(mainActivity, mainActivity.getString(com.amaze.filemanager.R.string.not_allowed));
                                emitter.onError(new kotlin.NotImplementedError());
                                return;
                            case DROPBOX :
                            case BOX :
                            case ONEDRIVE :
                            case GDRIVE :
                                com.amaze.filemanager.fileoperations.filesystem.OpenMode mode;
                                mode = hFile.getMode();
                                com.cloudrail.si.interfaces.CloudStorage cloudStorage;
                                cloudStorage = dataUtils.getAccount(mode);
                                java.lang.String path;
                                path = com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, finalFilePath);
                                cloudStorage.upload(path, bufferedInputStream, documentFile.length(), true);
                                retval.add(path);
                                break;
                            case OTG :
                                androidx.documentfile.provider.DocumentFile documentTargetFile;
                                documentTargetFile = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(finalFilePath, mainActivity, true);
                                if (documentTargetFile.exists()) {
                                    emitter.onError(new com.amaze.filemanager.exceptions.OperationWouldOverwriteException());
                                    return;
                                }
                                bufferedOutputStream = new java.io.BufferedOutputStream(contentResolver.openOutputStream(documentTargetFile.getUri()), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
                                retval.add(documentTargetFile.getUri().getPath());
                                break;
                            default :
                                return;
                        }
                        int count;
                        count = 0;
                        byte[] buffer;
                        buffer = new byte[com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE];
                        while (count != (-1)) {
                            count = bufferedInputStream.read(buffer);
                            if (count != (-1)) {
                                bufferedOutputStream.write(buffer, 0, count);
                            }
                        } 
                        bufferedOutputStream.flush();
                    } catch (java.io.IOException e) {
                        emitter.onError(e);
                        return;
                    } finally {
                        try {
                            if (bufferedInputStream != null) {
                                bufferedInputStream.close();
                            }
                            if (bufferedOutputStream != null) {
                                bufferedOutputStream.close();
                            }
                        } catch (java.io.IOException e) {
                            emitter.onError(e);
                        }
                    }
                    break;
                }
            }
        }
        if (retval.size() > 0) {
            emitter.onSuccess(retval);
        } else {
            emitter.onError(new java.lang.Exception());
        }
    }));
    io.reactivex.Maybe.create(writeUri).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(new io.reactivex.MaybeObserver<java.util.List<java.lang.String>>() {
        @java.lang.Override
        public void onSubscribe(@androidx.annotation.NonNull
        io.reactivex.disposables.Disposable d) {
        }


        @java.lang.Override
        public void onSuccess(@androidx.annotation.NonNull
        java.util.List<java.lang.String> paths) {
            android.media.MediaScannerConnection.scanFile(mainActivity.getApplicationContext(), paths.toArray(new java.lang.String[0]), new java.lang.String[paths.size()], null);
            if (paths.size() == 1) {
                android.widget.Toast.makeText(mainActivity, mainActivity.getString(com.amaze.filemanager.R.string.saved_single_file, paths.get(0)), android.widget.Toast.LENGTH_LONG).show();
            } else {
                android.widget.Toast.makeText(mainActivity, mainActivity.getString(com.amaze.filemanager.R.string.saved_multi_files, paths.size()), android.widget.Toast.LENGTH_LONG).show();
            }
        }


        @java.lang.Override
        public void onError(@androidx.annotation.NonNull
        java.lang.Throwable e) {
            if (e instanceof com.amaze.filemanager.exceptions.OperationWouldOverwriteException) {
                com.amaze.filemanager.application.AppConfig.toast(mainActivity, mainActivity.getString(com.amaze.filemanager.R.string.cannot_overwrite));
                return;
            }
            if (e instanceof com.amaze.filemanager.exceptions.NotAllowedException) {
                com.amaze.filemanager.application.AppConfig.toast(mainActivity, mainActivity.getResources().getString(com.amaze.filemanager.R.string.not_allowed));
            }
            com.amaze.filemanager.filesystem.FileUtil.LOG.warn("Failed to write uri to storage", e);
        }


        @java.lang.Override
        public void onComplete() {
        }

    });
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
