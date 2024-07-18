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
import java.net.MalformedURLException;
import com.amaze.filemanager.filesystem.files.FileUtils;
import com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils;
import java.util.ArrayList;
import org.slf4j.Logger;
import com.amaze.filemanager.filesystem.root.RenameFileCommand;
import androidx.arch.core.util.Function;
import java.net.URL;
import com.amaze.filemanager.filesystem.ftp.FtpClientTemplate;
import com.amaze.filemanager.utils.OTGUtil;
import com.cloudrail.si.interfaces.CloudStorage;
import com.amaze.filemanager.filesystem.root.MakeDirectoryCommand;
import com.amaze.filemanager.R;
import java.util.concurrent.Executor;
import androidx.annotation.NonNull;
import android.os.Build;
import com.amaze.filemanager.filesystem.root.MakeFileCommand;
import org.slf4j.LoggerFactory;
import jcifs.smb.SmbException;
import static com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS;
import static com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_GENERAL;
import java.io.IOException;
import com.amaze.filemanager.filesystem.files.MediaConnectionUtils;
import android.text.TextUtils;
import net.schmizz.sshj.sftp.SFTPClient;
import android.content.Intent;
import android.os.AsyncTask;
import org.apache.commons.net.ftp.FTPClient;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import androidx.documentfile.provider.DocumentFile;
import com.amaze.filemanager.filesystem.ssh.SshClientUtils;
import jcifs.smb.SmbFile;
import com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate;
import com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException;
import com.amaze.filemanager.filesystem.cloud.CloudUtil;
import com.amaze.filemanager.utils.DataUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Operations {
    static final int MUID_STATIC = getMUID();
    private static final java.util.concurrent.Executor executor = android.os.AsyncTask.THREAD_POOL_EXECUTOR;

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.Operations.class);

    // reserved characters by OS, shall not be allowed in file names
    private static final java.lang.String FOREWARD_SLASH = "/";

    private static final java.lang.String BACKWARD_SLASH = "\\";

    private static final java.lang.String COLON = ":";

    private static final java.lang.String ASTERISK = "*";

    private static final java.lang.String QUESTION_MARK = "?";

    private static final java.lang.String QUOTE = "\"";

    private static final java.lang.String GREATER_THAN = ">";

    private static final java.lang.String LESS_THAN = "<";

    private static final java.lang.String FAT = "FAT";

    public interface ErrorCallBack {
        /**
         * Callback fired when file being created in process already exists
         */
        void exists(com.amaze.filemanager.filesystem.HybridFile file);


        /**
         * Callback fired when creating new file/directory and required storage access framework
         * permission to access SD Card is not available
         */
        void launchSAF(com.amaze.filemanager.filesystem.HybridFile file);


        /**
         * Callback fired when renaming file and required storage access framework permission to access
         * SD Card is not available
         */
        void launchSAF(com.amaze.filemanager.filesystem.HybridFile file, com.amaze.filemanager.filesystem.HybridFile file1);


        /**
         * Callback fired when we're done processing the operation
         *
         * @param b
         * 		defines whether operation was successful
         */
        void done(com.amaze.filemanager.filesystem.HybridFile hFile, boolean b);


        /**
         * Callback fired when an invalid file name is found.
         */
        void invalidName(com.amaze.filemanager.filesystem.HybridFile file);

    }

    public static void mkdir(final com.amaze.filemanager.filesystem.HybridFile parentFile, @androidx.annotation.NonNull
    final com.amaze.filemanager.filesystem.HybridFile file, final android.content.Context context, final boolean rootMode, @androidx.annotation.NonNull
    final com.amaze.filemanager.filesystem.Operations.ErrorCallBack errorCallBack) {
        new android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void>() {
            private com.amaze.filemanager.utils.DataUtils dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();

            private androidx.arch.core.util.Function<androidx.documentfile.provider.DocumentFile, java.lang.Void> safCreateDirectory = (androidx.documentfile.provider.DocumentFile input) -> {
                if ((input != null) && input.isDirectory()) {
                    boolean result;
                    result = false;
                    try {
                        result = input.createDirectory(file.getName(context)) != null;
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("Failed to make directory", e);
                    }
                    errorCallBack.done(file, result);
                } else
                    errorCallBack.done(file, false);

                return null;
            };

            @java.lang.Override
            protected java.lang.Void doInBackground(java.lang.Void... params) {
                // checking whether filename is valid or a recursive call possible
                if (!com.amaze.filemanager.filesystem.Operations.isFileNameValid(file.getName(context))) {
                    errorCallBack.invalidName(file);
                    return null;
                }
                if (file.exists()) {
                    errorCallBack.exists(file);
                    return null;
                }
                // Android data directory, prohibit create directory
                if (file.isAndroidDataDir()) {
                    errorCallBack.done(file, false);
                    return null;
                }
                if (file.isSftp() || file.isFtp()) {
                    file.mkdir(context);
                    /* FIXME: throw Exceptions from HybridFile.mkdir() so errorCallback can throw Exceptions
                    here
                     */
                    errorCallBack.done(file, true);
                    return null;
                }
                if (file.isSmb()) {
                    try {
                        file.getSmbFile(2000).mkdirs();
                    } catch (jcifs.smb.SmbException e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make smb directories", e);
                        errorCallBack.done(file, false);
                        return null;
                    }
                    errorCallBack.done(file, file.exists());
                    return null;
                }
                if (file.isOtgFile()) {
                    if (com.amaze.filemanager.filesystem.Operations.checkOtgNewFileExists(file, context)) {
                        errorCallBack.exists(file);
                        return null;
                    }
                    safCreateDirectory.apply(com.amaze.filemanager.utils.OTGUtil.getDocumentFile(parentFile.getPath(), context, false));
                    return null;
                }
                if (file.isDocumentFile()) {
                    if (com.amaze.filemanager.filesystem.Operations.checkDocumentFileNewFileExists(file, context)) {
                        errorCallBack.exists(file);
                        return null;
                    }
                    safCreateDirectory.apply(com.amaze.filemanager.utils.OTGUtil.getDocumentFile(parentFile.getPath(), com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), context, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, false));
                    return null;
                } else if (file.isDropBoxFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageDropbox;
                    cloudStorageDropbox = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX);
                    try {
                        cloudStorageDropbox.createFolder(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX, file.getPath()));
                        errorCallBack.done(file, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make directory in cloud connection", e);
                        errorCallBack.done(file, false);
                    }
                } else if (file.isBoxFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageBox;
                    cloudStorageBox = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX);
                    try {
                        cloudStorageBox.createFolder(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX, file.getPath()));
                        errorCallBack.done(file, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make directory in cloud connection", e);
                        errorCallBack.done(file, false);
                    }
                } else if (file.isOneDriveFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageOneDrive;
                    cloudStorageOneDrive = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE);
                    try {
                        cloudStorageOneDrive.createFolder(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE, file.getPath()));
                        errorCallBack.done(file, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make directory in cloud connection", e);
                        errorCallBack.done(file, false);
                    }
                } else if (file.isGoogleDriveFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageGdrive;
                    cloudStorageGdrive = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE);
                    try {
                        cloudStorageGdrive.createFolder(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE, file.getPath()));
                        errorCallBack.done(file, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make directory in cloud connection", e);
                        errorCallBack.done(file, false);
                    }
                } else {
                    if (file.isLocal() || file.isRoot()) {
                        int mode;
                        mode = com.amaze.filemanager.filesystem.Operations.checkFolder(new java.io.File(file.getParent(context)), context);
                        if (mode == 2) {
                            errorCallBack.launchSAF(file);
                            return null;
                        }
                        if ((mode == 1) || (mode == 0))
                            com.amaze.filemanager.filesystem.MakeDirectoryOperation.mkdir(file.getFile(), context);

                        if ((!file.exists()) && rootMode) {
                            file.setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT);
                            if (file.exists())
                                errorCallBack.exists(file);

                            try {
                                com.amaze.filemanager.filesystem.root.MakeDirectoryCommand.INSTANCE.makeDirectory(file.getParent(context), file.getName(context));
                            } catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException e) {
                                com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make directory in local filesystem", e);
                            }
                            errorCallBack.done(file, file.exists());
                            return null;
                        }
                        errorCallBack.done(file, file.exists());
                        return null;
                    }
                    errorCallBack.done(file, file.exists());
                }
                return null;
            }

        }.executeOnExecutor(com.amaze.filemanager.filesystem.Operations.executor);
    }


    public static void mkfile(final com.amaze.filemanager.filesystem.HybridFile parentFile, @androidx.annotation.NonNull
    final com.amaze.filemanager.filesystem.HybridFile file, final android.content.Context context, final boolean rootMode, @androidx.annotation.NonNull
    final com.amaze.filemanager.filesystem.Operations.ErrorCallBack errorCallBack) {
        new android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void>() {
            private com.amaze.filemanager.utils.DataUtils dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();

            private androidx.arch.core.util.Function<androidx.documentfile.provider.DocumentFile, java.lang.Void> safCreateFile = (androidx.documentfile.provider.DocumentFile input) -> {
                if ((input != null) && input.isDirectory()) {
                    boolean result;
                    result = false;
                    try {
                        result = input.createFile(file.getName(context).substring(file.getName(context).lastIndexOf(".")), file.getName(context)) != null;
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn(getClass().getSimpleName(), "Failed to make file", e);
                    }
                    errorCallBack.done(file, result);
                } else
                    errorCallBack.done(file, false);

                return null;
            };

            @java.lang.Override
            protected java.lang.Void doInBackground(java.lang.Void... params) {
                // check whether filename is valid or not
                if (!com.amaze.filemanager.filesystem.Operations.isFileNameValid(file.getName(context))) {
                    errorCallBack.invalidName(file);
                    return null;
                }
                if (file.exists()) {
                    errorCallBack.exists(file);
                    return null;
                }
                // Android data directory, prohibit create file
                if (file.isAndroidDataDir()) {
                    errorCallBack.done(file, false);
                    return null;
                }
                if (file.isSftp() || file.isFtp()) {
                    java.io.OutputStream out;
                    out = file.getOutputStream(context);
                    if (out == null) {
                        errorCallBack.done(file, false);
                        return null;
                    }
                    try {
                        out.close();
                        errorCallBack.done(file, true);
                        return null;
                    } catch (java.io.IOException e) {
                        errorCallBack.done(file, false);
                        return null;
                    }
                }
                if (file.isSmb()) {
                    try {
                        file.getSmbFile(2000).createNewFile();
                    } catch (jcifs.smb.SmbException e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make file in smb connection", e);
                        errorCallBack.done(file, false);
                        return null;
                    }
                    errorCallBack.done(file, file.exists());
                    return null;
                } else if (file.isDropBoxFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageDropbox;
                    cloudStorageDropbox = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX);
                    try {
                        byte[] tempBytes;
                        tempBytes = new byte[0];
                        java.io.ByteArrayInputStream byteArrayInputStream;
                        byteArrayInputStream = new java.io.ByteArrayInputStream(tempBytes);
                        cloudStorageDropbox.upload(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX, file.getPath()), byteArrayInputStream, 0L, true);
                        errorCallBack.done(file, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make file in cloud connection", e);
                        errorCallBack.done(file, false);
                    }
                } else if (file.isBoxFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageBox;
                    cloudStorageBox = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX);
                    try {
                        byte[] tempBytes;
                        tempBytes = new byte[0];
                        java.io.ByteArrayInputStream byteArrayInputStream;
                        byteArrayInputStream = new java.io.ByteArrayInputStream(tempBytes);
                        cloudStorageBox.upload(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX, file.getPath()), byteArrayInputStream, 0L, true);
                        errorCallBack.done(file, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make file in cloud connection", e);
                        errorCallBack.done(file, false);
                    }
                } else if (file.isOneDriveFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageOneDrive;
                    cloudStorageOneDrive = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE);
                    try {
                        byte[] tempBytes;
                        tempBytes = new byte[0];
                        java.io.ByteArrayInputStream byteArrayInputStream;
                        byteArrayInputStream = new java.io.ByteArrayInputStream(tempBytes);
                        cloudStorageOneDrive.upload(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE, file.getPath()), byteArrayInputStream, 0L, true);
                        errorCallBack.done(file, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make file in cloud connection", e);
                        errorCallBack.done(file, false);
                    }
                } else if (file.isGoogleDriveFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageGdrive;
                    cloudStorageGdrive = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE);
                    try {
                        byte[] tempBytes;
                        tempBytes = new byte[0];
                        java.io.ByteArrayInputStream byteArrayInputStream;
                        byteArrayInputStream = new java.io.ByteArrayInputStream(tempBytes);
                        cloudStorageGdrive.upload(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE, file.getPath()), byteArrayInputStream, 0L, true);
                        errorCallBack.done(file, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make file in cloud connection", e);
                        errorCallBack.done(file, false);
                    }
                } else if (file.isOtgFile()) {
                    if (com.amaze.filemanager.filesystem.Operations.checkOtgNewFileExists(file, context)) {
                        errorCallBack.exists(file);
                        return null;
                    }
                    safCreateFile.apply(com.amaze.filemanager.utils.OTGUtil.getDocumentFile(parentFile.getPath(), context, false));
                    return null;
                } else if (file.isDocumentFile()) {
                    if (com.amaze.filemanager.filesystem.Operations.checkDocumentFileNewFileExists(file, context)) {
                        errorCallBack.exists(file);
                        return null;
                    }
                    safCreateFile.apply(com.amaze.filemanager.utils.OTGUtil.getDocumentFile(parentFile.getPath(), com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), context, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, false));
                    return null;
                } else {
                    if (file.isLocal() || file.isRoot()) {
                        int mode;
                        mode = com.amaze.filemanager.filesystem.Operations.checkFolder(new java.io.File(file.getParent(context)), context);
                        if (mode == 2) {
                            errorCallBack.launchSAF(file);
                            return null;
                        }
                        if ((mode == 1) || (mode == 0))
                            com.amaze.filemanager.filesystem.MakeFileOperation.mkfile(file.getFile(), context);

                        if ((!file.exists()) && rootMode) {
                            file.setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT);
                            if (file.exists())
                                errorCallBack.exists(file);

                            try {
                                com.amaze.filemanager.filesystem.root.MakeFileCommand.INSTANCE.makeFile(file.getPath());
                            } catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException e) {
                                com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to make file in local filesystem", e);
                            }
                            errorCallBack.done(file, file.exists());
                            return null;
                        }
                        errorCallBack.done(file, file.exists());
                        return null;
                    }
                    errorCallBack.done(file, file.exists());
                }
                return null;
            }

        }.executeOnExecutor(com.amaze.filemanager.filesystem.Operations.executor);
    }


    public static void rename(@androidx.annotation.NonNull
    final com.amaze.filemanager.filesystem.HybridFile oldFile, @androidx.annotation.NonNull
    final com.amaze.filemanager.filesystem.HybridFile newFile, final boolean rootMode, @androidx.annotation.NonNull
    final android.content.Context context, @androidx.annotation.NonNull
    final com.amaze.filemanager.filesystem.Operations.ErrorCallBack errorCallBack) {
        new android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void>() {
            private final com.amaze.filemanager.utils.DataUtils dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();

            /**
             * Determines whether double rename is required based on original and new file name regardless
             * of the case-sensitivity of the filesystem
             */
            private final boolean isCaseSensitiveRename = oldFile.getSimpleName().equalsIgnoreCase(newFile.getSimpleName()) && (!oldFile.getSimpleName().equals(newFile.getSimpleName()));

            /**
             * random string that is appended to file to prevent name collision, max file name is 255
             * bytes
             */
            private static final java.lang.String TEMP_FILE_EXT = "u0CtHRqWUnvxIaeBQ@nY2umVm9MDyR1P";

            private boolean localRename(@androidx.annotation.NonNull
            com.amaze.filemanager.filesystem.HybridFile oldFile, @androidx.annotation.NonNull
            com.amaze.filemanager.filesystem.HybridFile newFile) {
                java.io.File file;
                file = new java.io.File(oldFile.getPath());
                java.io.File file1;
                file1 = new java.io.File(newFile.getPath());
                boolean result;
                result = false;
                switch (oldFile.getMode()) {
                    case FILE :
                        int mode;
                        mode = com.amaze.filemanager.filesystem.Operations.checkFolder(file.getParentFile(), context);
                        if ((mode == 1) || (mode == 0)) {
                            try {
                                com.amaze.filemanager.filesystem.RenameOperation.renameFolder(file, file1, context);
                            } catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException e) {
                                com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to rename file in local filesystem", e);
                            }
                            result = (!file.exists()) && file1.exists();
                            if ((!result) && rootMode) {
                                try {
                                    com.amaze.filemanager.filesystem.root.RenameFileCommand.INSTANCE.renameFile(file.getPath(), file1.getPath());
                                } catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException e) {
                                    com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to rename file in local filesystem", e);
                                }
                                oldFile.setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT);
                                newFile.setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT);
                                result = (!file.exists()) && file1.exists();
                            }
                        }
                        break;
                    case ROOT :
                        try {
                            result = com.amaze.filemanager.filesystem.root.RenameFileCommand.INSTANCE.renameFile(file.getPath(), file1.getPath());
                        } catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException e) {
                            com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to rename file in root", e);
                        }
                        newFile.setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT);
                        break;
                }
                return result;
            }


            private boolean localDoubleRename(@androidx.annotation.NonNull
            com.amaze.filemanager.filesystem.HybridFile oldFile, @androidx.annotation.NonNull
            com.amaze.filemanager.filesystem.HybridFile newFile) {
                com.amaze.filemanager.filesystem.HybridFile tempFile;
                tempFile = new com.amaze.filemanager.filesystem.HybridFile(oldFile.mode, oldFile.getPath().concat(TEMP_FILE_EXT));
                if (localRename(oldFile, tempFile)) {
                    if (localRename(tempFile, newFile)) {
                        return true;
                    } else {
                        // attempts to rollback
                        // changes the temporary file name back to original file name
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("reverting temporary file rename");
                        return localRename(tempFile, oldFile);
                    }
                }
                return false;
            }


            private androidx.arch.core.util.Function<androidx.documentfile.provider.DocumentFile, java.lang.Void> safRenameFile = (androidx.documentfile.provider.DocumentFile input) -> {
                boolean result;
                result = false;
                try {
                    result = input.renameTo(newFile.getName(context));
                } catch (java.lang.Exception e) {
                    com.amaze.filemanager.filesystem.Operations.LOG.warn(getClass().getSimpleName(), "Failed to rename", e);
                }
                errorCallBack.done(newFile, result);
                return null;
            };

            @java.lang.Override
            protected java.lang.Void doInBackground(java.lang.Void... params) {
                // check whether file names for new file are valid or recursion occurs.
                // If rename is on OTG, we are skipping
                if (!com.amaze.filemanager.filesystem.Operations.isFileNameValid(newFile.getName(context))) {
                    errorCallBack.invalidName(newFile);
                    return null;
                }
                if (newFile.exists() && (!isCaseSensitiveRename)) {
                    errorCallBack.exists(newFile);
                    return null;
                }
                if (oldFile.isSmb()) {
                    try {
                        jcifs.smb.SmbFile smbFile;
                        smbFile = oldFile.getSmbFile();
                        // FIXME: smbFile1 should be created from SmbUtil too so it can be mocked
                        jcifs.smb.SmbFile smbFile1;
                        smbFile1 = new jcifs.smb.SmbFile(new java.net.URL(newFile.getPath()), smbFile.getContext());
                        if (newFile.exists()) {
                            errorCallBack.exists(newFile);
                            return null;
                        }
                        smbFile.renameTo(smbFile1);
                        if ((!smbFile.exists()) && smbFile1.exists())
                            errorCallBack.done(newFile, true);

                    } catch (jcifs.smb.SmbException | java.net.MalformedURLException e) {
                        java.lang.String errmsg;
                        errmsg = context.getString(com.amaze.filemanager.R.string.cannot_rename_file, com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(oldFile.getPath()), e.getMessage());
                        try {
                            java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> failedOps;
                            failedOps = new java.util.ArrayList<>();
                            failedOps.add(new com.amaze.filemanager.filesystem.HybridFileParcelable(oldFile.getSmbFile()));
                            context.sendBroadcast(new android.content.Intent(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_GENERAL).putParcelableArrayListExtra(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS, failedOps));
                        } catch (jcifs.smb.SmbException exceptionThrownDuringBuildParcelable) {
                            com.amaze.filemanager.filesystem.Operations.LOG.error("Error creating HybridFileParcelable", exceptionThrownDuringBuildParcelable);
                        }
                        com.amaze.filemanager.filesystem.Operations.LOG.error(errmsg, e);
                    }
                    return null;
                } else if (oldFile.isSftp()) {
                    com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.lang.Void>(oldFile.getPath(), true) {
                        @java.lang.Override
                        public java.lang.Void execute(@androidx.annotation.NonNull
                        net.schmizz.sshj.sftp.SFTPClient client) {
                            try {
                                client.rename(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(oldFile.getPath()), com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(newFile.getPath()));
                                errorCallBack.done(newFile, true);
                            } catch (java.io.IOException e) {
                                java.lang.String errmsg;
                                errmsg = context.getString(com.amaze.filemanager.R.string.cannot_rename_file, com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(oldFile.getPath()), e.getMessage());
                                com.amaze.filemanager.filesystem.Operations.LOG.error(errmsg);
                                java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> failedOps;
                                failedOps = new java.util.ArrayList<>();
                                // Nobody care the size or actual permission here. Put a simple "r" and zero
                                // here
                                failedOps.add(new com.amaze.filemanager.filesystem.HybridFileParcelable(oldFile.getPath(), "r", oldFile.lastModified(), 0, oldFile.isDirectory(context)));
                                context.sendBroadcast(new android.content.Intent(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_GENERAL).putParcelableArrayListExtra(com.amaze.filemanager.ui.activities.MainActivity.TAG_INTENT_FILTER_FAILED_OPS, failedOps));
                                errorCallBack.done(newFile, false);
                            }
                            return null;
                        }

                    });
                } else if (oldFile.isFtp()) {
                    com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.execute(new com.amaze.filemanager.filesystem.ftp.FtpClientTemplate<java.lang.Boolean>(oldFile.getPath(), false) {
                        public java.lang.Boolean executeWithFtpClient(@androidx.annotation.NonNull
                        org.apache.commons.net.ftp.FTPClient ftpClient) throws java.io.IOException {
                            boolean result;
                            result = ftpClient.rename(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(oldFile.getPath()), com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(newFile.getPath()));
                            errorCallBack.done(newFile, result);
                            return result;
                        }

                    });
                } else if (oldFile.isDropBoxFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageDropbox;
                    cloudStorageDropbox = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX);
                    try {
                        cloudStorageDropbox.move(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX, oldFile.getPath()), com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX, newFile.getPath()));
                        errorCallBack.done(newFile, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to rename file in cloud connection", e);
                        errorCallBack.done(newFile, false);
                    }
                } else if (oldFile.isBoxFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageBox;
                    cloudStorageBox = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX);
                    try {
                        cloudStorageBox.move(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX, oldFile.getPath()), com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX, newFile.getPath()));
                        errorCallBack.done(newFile, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to rename file in cloud connection", e);
                        errorCallBack.done(newFile, false);
                    }
                } else if (oldFile.isOneDriveFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageOneDrive;
                    cloudStorageOneDrive = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE);
                    try {
                        cloudStorageOneDrive.move(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE, oldFile.getPath()), com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE, newFile.getPath()));
                        errorCallBack.done(newFile, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to rename file in cloud connection", e);
                        errorCallBack.done(newFile, false);
                    }
                } else if (oldFile.isGoogleDriveFile()) {
                    com.cloudrail.si.interfaces.CloudStorage cloudStorageGdrive;
                    cloudStorageGdrive = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE);
                    try {
                        cloudStorageGdrive.move(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE, oldFile.getPath()), com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE, newFile.getPath()));
                        errorCallBack.done(newFile, true);
                    } catch (java.lang.Exception e) {
                        com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to rename file in cloud connection", e);
                        errorCallBack.done(newFile, false);
                    }
                } else if (oldFile.isOtgFile()) {
                    if (com.amaze.filemanager.filesystem.Operations.checkOtgNewFileExists(newFile, context)) {
                        errorCallBack.exists(newFile);
                        return null;
                    }
                    safRenameFile.apply(com.amaze.filemanager.utils.OTGUtil.getDocumentFile(oldFile.getPath(), context, false));
                    return null;
                } else if (oldFile.isDocumentFile()) {
                    if (com.amaze.filemanager.filesystem.Operations.checkDocumentFileNewFileExists(newFile, context)) {
                        errorCallBack.exists(newFile);
                        return null;
                    }
                    safRenameFile.apply(com.amaze.filemanager.utils.OTGUtil.getDocumentFile(oldFile.getPath(), com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), context, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, false));
                    return null;
                } else {
                    java.io.File file;
                    file = new java.io.File(oldFile.getPath());
                    if (oldFile.getMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE) {
                        int mode;
                        mode = com.amaze.filemanager.filesystem.Operations.checkFolder(file.getParentFile(), context);
                        if (mode == 2) {
                            errorCallBack.launchSAF(oldFile, newFile);
                        }
                    }
                    boolean result;
                    if (isCaseSensitiveRename) {
                        result = localDoubleRename(oldFile, newFile);
                    } else {
                        result = localRename(oldFile, newFile);
                    }
                    errorCallBack.done(newFile, result);
                }
                return null;
            }


            @java.lang.Override
            protected void onPostExecute(java.lang.Void aVoid) {
                super.onPostExecute(aVoid);
                if ((newFile != null) && (oldFile != null)) {
                    com.amaze.filemanager.filesystem.HybridFile[] hybridFiles;
                    hybridFiles = new com.amaze.filemanager.filesystem.HybridFile[]{ newFile, oldFile };
                    com.amaze.filemanager.filesystem.files.MediaConnectionUtils.scanFile(context, hybridFiles);
                }
            }

        }.executeOnExecutor(com.amaze.filemanager.filesystem.Operations.executor);
    }


    private static boolean checkOtgNewFileExists(com.amaze.filemanager.filesystem.HybridFile newFile, android.content.Context context) {
        boolean doesFileExist;
        doesFileExist = false;
        try {
            doesFileExist = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(newFile.getPath(), context, false) != null;
        } catch (java.lang.Exception e) {
            com.amaze.filemanager.filesystem.Operations.LOG.debug("Failed find existing file", e);
        }
        return doesFileExist;
    }


    private static boolean checkDocumentFileNewFileExists(com.amaze.filemanager.filesystem.HybridFile newFile, android.content.Context context) {
        boolean doesFileExist;
        doesFileExist = false;
        try {
            doesFileExist = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(newFile.getPath(), com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), context, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, false) != null;
        } catch (java.lang.Exception e) {
            com.amaze.filemanager.filesystem.Operations.LOG.warn("Failed to find existing file", e);
        }
        return doesFileExist;
    }


    private static int checkFolder(final java.io.File folder, android.content.Context context) {
        boolean lol;
        lol = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
        if (lol) {
            boolean ext;
            ext = com.amaze.filemanager.filesystem.ExternalSdCardOperation.isOnExtSdCard(folder, context);
            if (ext) {
                if ((!folder.exists()) || (!folder.isDirectory())) {
                    return 0;
                }
                // On Android 5, trigger storage access framework.
                if (!com.amaze.filemanager.filesystem.FileProperties.isWritableNormalOrSaf(folder, context)) {
                    return 2;
                }
                return 1;
            }
        } else if (android.os.Build.VERSION.SDK_INT == 19) {
            // Assume that Kitkat workaround works
            if (com.amaze.filemanager.filesystem.ExternalSdCardOperation.isOnExtSdCard(folder, context))
                return 1;

        }
        // file not on external sd card
        if (com.amaze.filemanager.filesystem.FileProperties.isWritable(new java.io.File(folder, com.amaze.filemanager.filesystem.files.FileUtils.DUMMY_FILE))) {
            return 1;
        } else {
            return 0;
        }
    }


    /**
     * Well, we wouldn't want to copy when the target is inside the source otherwise it'll end into a
     * loop
     *
     * @return true when copy loop is possible
     */
    public static boolean isCopyLoopPossible(com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile, com.amaze.filemanager.filesystem.HybridFile targetFile) {
        return targetFile.getPath().contains(sourceFile.getPath());
    }


    /**
     * Validates file name special reserved characters shall not be allowed in the file names on FAT
     * filesystems
     *
     * @param fileName
     * 		the filename, not the full path!
     * @return boolean if the file name is valid or invalid
     */
    public static boolean isFileNameValid(java.lang.String fileName) {
        // Trim the trailing slash if there is one.
        if (fileName.endsWith("/")) {
            switch(MUID_STATIC) {
                // Operations_0_BinaryMutator
                case 41: {
                    fileName = fileName.substring(0, fileName.lastIndexOf('/') + 1);
                    break;
                }
                default: {
                fileName = fileName.substring(0, fileName.lastIndexOf('/') - 1);
                break;
            }
        }
    }
    // Trim the leading slashes if there is any.
    if (fileName.contains("/")) {
        switch(MUID_STATIC) {
            // Operations_1_BinaryMutator
            case 1041: {
                fileName = fileName.substring(fileName.lastIndexOf('/') - 1);
                break;
            }
            default: {
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
            break;
        }
    }
}
return (!android.text.TextUtils.isEmpty(fileName)) && (!(((((((fileName.contains(com.amaze.filemanager.filesystem.Operations.ASTERISK) || fileName.contains(com.amaze.filemanager.filesystem.Operations.BACKWARD_SLASH)) || fileName.contains(com.amaze.filemanager.filesystem.Operations.COLON)) || fileName.contains(com.amaze.filemanager.filesystem.Operations.FOREWARD_SLASH)) || fileName.contains(com.amaze.filemanager.filesystem.Operations.GREATER_THAN)) || fileName.contains(com.amaze.filemanager.filesystem.Operations.LESS_THAN)) || fileName.contains(com.amaze.filemanager.filesystem.Operations.QUESTION_MARK)) || fileName.contains(com.amaze.filemanager.filesystem.Operations.QUOTE)));
}


private static boolean isFileSystemFAT(java.lang.String mountPoint) {
java.lang.String[] args;
args = new java.lang.String[]{ "/bin/bash", "-c", ("df -DO_NOT_REPLACE | awk \'{print $1,$2,$NF}\' | grep \"^" + mountPoint) + "\"" };
try {
    java.lang.Process proc;
    proc = new java.lang.ProcessBuilder(args).start();
    java.io.OutputStream outputStream;
    outputStream = proc.getOutputStream();
    java.lang.String buffer;
    buffer = null;
    outputStream.write(buffer.getBytes());
    return (buffer != null) && buffer.contains(com.amaze.filemanager.filesystem.Operations.FAT);
} catch (java.io.IOException e) {
    com.amaze.filemanager.filesystem.Operations.LOG.warn("failed to determin is filesystem FAT", e);
    // process interrupted, returning true, as a word of cation
    return true;
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
