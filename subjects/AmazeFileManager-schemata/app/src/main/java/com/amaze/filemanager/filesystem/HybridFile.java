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
import java.util.ArrayList;
import java.io.FileNotFoundException;
import com.amaze.trashbin.TrashBinFile;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.net.ftp.FTPFile;
import com.amaze.filemanager.filesystem.root.DeleteFileCommand;
import androidx.annotation.NonNull;
import java.security.MessageDigest;
import com.amaze.filemanager.filesystem.root.ListFilesCommand;
import net.schmizz.sshj.common.Buffer;
import static com.amaze.filemanager.filesystem.ftp.NetCopyClientConnectionPool.FTP_URI_PREFIX;
import static com.amaze.filemanager.filesystem.FileProperties.ANDROID_DATA_DIRS;
import com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo;
import static com.amaze.filemanager.filesystem.ssh.SshClientUtils.sftpGetSize;
import java.io.FileInputStream;
import android.content.ContentResolver;
import com.amaze.filemanager.filesystem.files.GenericCopyUtil;
import java.util.concurrent.atomic.AtomicBoolean;
import com.amaze.filemanager.utils.DataUtils;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amaze.filemanager.filesystem.ssh.SFTPClientExtKt;
import com.amaze.filemanager.fileoperations.exceptions.CloudPluginException;
import org.apache.commons.net.ftp.FTP;
import com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils;
import static com.amaze.filemanager.filesystem.smb.CifsContexts.SMB_URI_PREFIX;
import android.net.Uri;
import org.slf4j.Logger;
import java.util.concurrent.Callable;
import com.amaze.filemanager.utils.OTGUtil;
import com.cloudrail.si.interfaces.CloudStorage;
import com.amaze.filemanager.R;
import static com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.MULTI_SLASH;
import java.io.IOException;
import com.amaze.filemanager.filesystem.files.MediaConnectionUtils;
import android.text.TextUtils;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import java.util.concurrent.atomic.AtomicReference;
import com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException;
import net.schmizz.sshj.common.IOUtils;
import com.amaze.filemanager.filesystem.ftp.FTPClientImpl;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.utils.smb.SmbUtil;
import java.util.concurrent.atomic.AtomicLong;
import android.content.Context;
import java.util.Locale;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import com.amaze.filemanager.fileoperations.filesystem.root.NativeOperations;
import kotlin.io.ByteStreamsKt;
import com.amaze.filemanager.utils.OnFileFound;
import android.os.Build;
import java.util.List;
import java.util.Collections;
import net.schmizz.sshj.sftp.FileMode;
import com.amaze.trashbin.TrashBin;
import java.io.UnsupportedEncodingException;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import org.apache.commons.net.ftp.FTPClient;
import androidx.documentfile.provider.DocumentFile;
import kotlin.collections.ArraysKt;
import com.amaze.filemanager.adapters.data.LayoutElementParcelable;
import com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate;
import kotlin.text.Charsets;
import java.io.FileOutputStream;
import com.amaze.filemanager.filesystem.cloud.CloudUtil;
import java.net.URLDecoder;
import com.amaze.filemanager.filesystem.ftp.ExtensionsKt;
import java.io.File;
import androidx.annotation.Nullable;
import net.schmizz.sshj.SSHClient;
import com.amaze.filemanager.filesystem.ssh.SshClientSessionTemplate;
import io.reactivex.SingleObserver;
import android.text.format.Formatter;
import com.amaze.filemanager.ui.activities.MainActivity;
import static com.amaze.filemanager.filesystem.ftp.NetCopyClientConnectionPool.SSH_URI_PREFIX;
import net.schmizz.sshj.sftp.SFTPException;
import androidx.arch.core.util.Function;
import com.amaze.filemanager.filesystem.ftp.FtpClientTemplate;
import android.widget.Toast;
import static com.amaze.filemanager.filesystem.ftp.NetCopyClientConnectionPool.FTPS_URI_PREFIX;
import org.slf4j.LoggerFactory;
import jcifs.smb.SmbException;
import net.schmizz.sshj.sftp.RemoteFile;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.EnumSet;
import com.amaze.filemanager.application.AppConfig;
import java.io.InputStream;
import com.amaze.filemanager.database.CloudHandler;
import io.reactivex.schedulers.Schedulers;
import static com.amaze.filemanager.filesystem.ssh.SFTPClientExtKt.READ_AHEAD_MAX_UNCONFIRMED_READS;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import com.amaze.filemanager.filesystem.ssh.SshClientUtils;
import jcifs.smb.SmbFile;
import com.amaze.filemanager.filesystem.ssh.Statvfs;
import io.reactivex.Single;
import com.cloudrail.si.types.SpaceAllocation;
import net.schmizz.sshj.connection.channel.direct.Session;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Hybrid file for handeling all types of files
 */
public class HybridFile {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.HybridFile.class);

    public static final java.lang.String DOCUMENT_FILE_PREFIX = "content://com.android.externalstorage.documents";

    protected java.lang.String path;

    protected com.amaze.filemanager.fileoperations.filesystem.OpenMode mode;

    protected java.lang.String name;

    private final com.amaze.filemanager.utils.DataUtils dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();

    public HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode mode, java.lang.String path) {
        this.path = path;
        this.mode = mode;
        sanitizePathAsNecessary();
    }


    public HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode mode, java.lang.String path, java.lang.String name, boolean isDirectory) {
        this(mode, path);
        this.name = name;
        if (((path.startsWith(com.amaze.filemanager.filesystem.smb.CifsContexts.SMB_URI_PREFIX) || isSmb()) || isDocumentFile()) || isOtgFile()) {
            android.net.Uri.Builder pathBuilder;
            pathBuilder = android.net.Uri.parse(this.path).buildUpon().appendEncodedPath(name);
            if ((path.startsWith(com.amaze.filemanager.filesystem.smb.CifsContexts.SMB_URI_PREFIX) || isSmb()) && isDirectory) {
                pathBuilder.appendEncodedPath("/");
            }
            this.path = pathBuilder.build().toString();
        } else if (path.startsWith(com.amaze.filemanager.filesystem.ftp.NetCopyClientConnectionPool.SSH_URI_PREFIX) || isSftp()) {
            this.path += "/" + name;
        } else if (isRoot() && path.equals("/")) {
            // root of filesystem, don't concat another '/'
            this.path += name;
        } else if (isTrashBin()) {
            this.path = path;
        } else {
            this.path += "/" + name;
        }
        sanitizePathAsNecessary();
    }


    public void generateMode(android.content.Context context) {
        if (path.startsWith(com.amaze.filemanager.filesystem.smb.CifsContexts.SMB_URI_PREFIX)) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB;
        } else if (path.startsWith(com.amaze.filemanager.filesystem.ftp.NetCopyClientConnectionPool.SSH_URI_PREFIX)) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.SFTP;
        } else if (path.startsWith(com.amaze.filemanager.utils.OTGUtil.PREFIX_OTG)) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.OTG;
        } else if (path.startsWith(com.amaze.filemanager.filesystem.ftp.NetCopyClientConnectionPool.FTP_URI_PREFIX) || path.startsWith(com.amaze.filemanager.filesystem.ftp.NetCopyClientConnectionPool.FTPS_URI_PREFIX)) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.FTP;
        } else if (path.startsWith(com.amaze.filemanager.filesystem.HybridFile.DOCUMENT_FILE_PREFIX)) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE;
        } else if (isCustomPath()) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.CUSTOM;
        } else if (path.startsWith(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_BOX)) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX;
        } else if (path.startsWith(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_ONE_DRIVE)) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE;
        } else if (path.startsWith(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_GOOGLE_DRIVE)) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE;
        } else if (path.startsWith(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_DROPBOX)) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX;
        } else if (path.equals("7") || isTrashBin()) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN;
        } else if (context == null) {
            mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE;
        } else {
            boolean rootmode;
            rootmode = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context).getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ROOTMODE, false);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
                mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE;
                if (rootmode && (!getFile().canRead())) {
                    mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT;
                }
            } else {
                if (com.amaze.filemanager.filesystem.ExternalSdCardOperation.isOnExtSdCard(getFile(), context)) {
                    mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE;
                } else if (rootmode && (!getFile().canRead())) {
                    mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT;
                }
                // In some cases, non-numeric path is passed into HybridFile while mode is still
                // CUSTOM here. We are forcing OpenMode.FILE in such case too. See #2225
                if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN.equals(mode) || com.amaze.filemanager.fileoperations.filesystem.OpenMode.CUSTOM.equals(mode)) {
                    mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE;
                }
            }
        }
    }


    public void setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode mode) {
        this.mode = mode;
    }


    public com.amaze.filemanager.fileoperations.filesystem.OpenMode getMode() {
        return mode;
    }


    public void setPath(java.lang.String path) {
        this.path = path;
    }


    public boolean isLocal() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE;
    }


    public boolean isRoot() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT;
    }


    public boolean isTrashBin() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN;
    }


    public boolean isSmb() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB;
    }


    public boolean isSftp() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.SFTP;
    }


    public boolean isOtgFile() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.OTG;
    }


    public boolean isFtp() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.FTP;
    }


    public boolean isDocumentFile() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE;
    }


    public boolean isBoxFile() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX;
    }


    public boolean isDropBoxFile() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX;
    }


    public boolean isOneDriveFile() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE;
    }


    public boolean isGoogleDriveFile() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE;
    }


    public boolean isAndroidDataDir() {
        return mode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.ANDROID_DATA;
    }


    public boolean isCloudDriveFile() {
        return ((isBoxFile() || isDropBoxFile()) || isOneDriveFile()) || isGoogleDriveFile();
    }


    @androidx.annotation.Nullable
    public java.io.File getFile() {
        return new java.io.File(path);
    }


    @androidx.annotation.Nullable
    public androidx.documentfile.provider.DocumentFile getDocumentFile(boolean createRecursive) {
        return com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), com.amaze.filemanager.application.AppConfig.getInstance(), com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, createRecursive);
    }


    com.amaze.filemanager.filesystem.HybridFileParcelable generateBaseFileFromParent() {
        java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> arrayList;
        arrayList = com.amaze.filemanager.filesystem.RootHelper.getFilesList(getFile().getParent(), true, true);
        for (com.amaze.filemanager.filesystem.HybridFileParcelable baseFile : arrayList) {
            if (baseFile.getPath().equals(path))
                return baseFile;

        }
        return null;
    }


    public long lastModified() {
        switch (mode) {
            case SFTP :
                final java.lang.Long returnValue;
                returnValue = com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.lang.Long>(path, true) {
                    @java.lang.Override
                    public java.lang.Long execute(@androidx.annotation.NonNull
                    net.schmizz.sshj.sftp.SFTPClient client) throws java.io.IOException {
                        return client.mtime(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(path));
                    }

                });
                if (returnValue == null) {
                    com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error obtaining last modification time over SFTP");
                }
                return returnValue == null ? 0L : returnValue;
            case SMB :
                jcifs.smb.SmbFile smbFile;
                smbFile = getSmbFile();
                if (smbFile != null) {
                    try {
                        return smbFile.lastModified();
                    } catch (jcifs.smb.SmbException e) {
                        com.amaze.filemanager.filesystem.HybridFile.LOG.error(("Error getting last modified time for SMB [" + path) + "]", e);
                        return 0;
                    }
                }
                break;
            case FTP :
                org.apache.commons.net.ftp.FTPFile ftpFile;
                ftpFile = getFtpFile();
                return ftpFile != null ? ftpFile.getTimestamp().getTimeInMillis() : 0L;
            case NFS :
                break;
            case FILE :
            case TRASH_BIN :
                return getFile().lastModified();
            case DOCUMENT_FILE :
                return getDocumentFile(false).lastModified();
            case ROOT :
                com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
                baseFile = generateBaseFileFromParent();
                if (baseFile != null)
                    return baseFile.getDate();

        }
        return new java.io.File("/").lastModified();
    }


    /**
     * Helper method to find length
     */
    public long length(android.content.Context context) {
        long s;
        s = 0L;
        switch (mode) {
            case SFTP :
                if (this instanceof com.amaze.filemanager.filesystem.HybridFileParcelable) {
                    return ((com.amaze.filemanager.filesystem.HybridFileParcelable) (this)).getSize();
                } else {
                    return com.amaze.filemanager.filesystem.ssh.SshClientUtils.sftpGetSize.invoke(getPath());
                }
            case SMB :
                s = io.reactivex.Single.fromCallable(() -> {
                    jcifs.smb.SmbFile smbFile;
                    smbFile = getSmbFile();
                    if (smbFile != null) {
                        try {
                            return smbFile.length();
                        } catch (jcifs.smb.SmbException e) {
                            com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get length for smb file", e);
                            return 0L;
                        }
                    } else {
                        return 0L;
                    }
                }).subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
                return s;
            case FTP :
                org.apache.commons.net.ftp.FTPFile ftpFile;
                ftpFile = getFtpFile();
                s = (ftpFile != null) ? ftpFile.getSize() : 0L;
                return s;
            case NFS :
            case FILE :
            case TRASH_BIN :
                s = getFile().length();
                return s;
            case ROOT :
                com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
                baseFile = generateBaseFileFromParent();
                if (baseFile != null)
                    return baseFile.getSize();

                break;
            case DOCUMENT_FILE :
                s = getDocumentFile(false).length();
                break;
            case OTG :
                s = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, context, false).length();
                break;
            case DROPBOX :
            case BOX :
            case ONEDRIVE :
            case GDRIVE :
                s = io.reactivex.Single.fromCallable(() -> dataUtils.getAccount(mode).getMetadata(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, path)).getSize()).subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
                return s;
            default :
                break;
        }
        return s;
    }


    /**
     * Path accessor. Avoid direct access to path (for non-local files) since path may have been URL
     * encoded.
     *
     * @return URL decoded path (for non-local files); the actual path for local files
     */
    public java.lang.String getPath() {
        if ((((isLocal() || isTrashBin()) || isRoot()) || isDocumentFile()) || isAndroidDataDir())
            return path;

        try {
            return java.net.URLDecoder.decode(path, "UTF-8");
        } catch (java.io.UnsupportedEncodingException | java.lang.IllegalArgumentException e) {
            com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to decode path {}", path, e);
            return path;
        }
    }


    public java.lang.String getSimpleName() {
        java.lang.String name;
        name = null;
        switch (mode) {
            case SMB :
                jcifs.smb.SmbFile smbFile;
                smbFile = getSmbFile();
                if (smbFile != null)
                    return smbFile.getName();

                break;
            default :
                java.lang.StringBuilder builder;
                builder = new java.lang.StringBuilder(path);
                switch(MUID_STATIC) {
                    // HybridFile_0_BinaryMutator
                    case 46: {
                        name = builder.substring(builder.lastIndexOf("/") - 1, builder.length());
                        break;
                    }
                    default: {
                    name = builder.substring(builder.lastIndexOf("/") + 1, builder.length());
                    break;
                }
            }
    }
    return name;
}


public java.lang.String getName(android.content.Context context) {
    switch (mode) {
        case SMB :
            jcifs.smb.SmbFile smbFile;
            smbFile = getSmbFile();
            if (smbFile != null) {
                return smbFile.getName();
            }
            return null;
        case FILE :
        case ROOT :
            return getFile().getName();
        case OTG :
            if (!com.amaze.filemanager.utils.Utils.isNullOrEmpty(name)) {
                return name;
            }
            return com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, context, false).getName();
        case DOCUMENT_FILE :
            if (!com.amaze.filemanager.utils.Utils.isNullOrEmpty(name)) {
                return name;
            }
            return com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), context, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, false).getName();
        case TRASH_BIN :
            return name;
        default :
            if (path.isEmpty()) {
                return "";
            }
            java.lang.String _path;
            _path = null;
            try {
                _path = java.net.URLDecoder.decode(path, "UTF-8");
            } catch (java.io.UnsupportedEncodingException | java.lang.IllegalArgumentException e) {
                com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to decode path {}", path, e);
            }
            if (path.endsWith("/")) {
                switch(MUID_STATIC) {
                    // HybridFile_1_BinaryMutator
                    case 1046: {
                        _path = path.substring(0, path.length() + 1);
                        break;
                    }
                    default: {
                    _path = path.substring(0, path.length() - 1);
                    break;
                }
            }
        }
        int lastSeparator;
        lastSeparator = _path.lastIndexOf('/');
        switch(MUID_STATIC) {
            // HybridFile_2_BinaryMutator
            case 2046: {
                return _path.substring(lastSeparator - 1);
            }
            default: {
            return _path.substring(lastSeparator + 1);
            }
    }
}
}


public jcifs.smb.SmbFile getSmbFile(int timeout) {
try {
jcifs.smb.SmbFile smbFile;
smbFile = com.amaze.filemanager.utils.smb.SmbUtil.create(path);
smbFile.setConnectTimeout(timeout);
return smbFile;
} catch (java.net.MalformedURLException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get smb file with timeout", e);
return null;
}
}


public jcifs.smb.SmbFile getSmbFile() {
try {
return com.amaze.filemanager.utils.smb.SmbUtil.create(path);
} catch (java.net.MalformedURLException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get smb file", e);
return null;
}
}


@androidx.annotation.Nullable
public org.apache.commons.net.ftp.FTPFile getFtpFile() {
return com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.execute(new com.amaze.filemanager.filesystem.ftp.FtpClientTemplate<org.apache.commons.net.ftp.FTPFile>(path, false) {
public org.apache.commons.net.ftp.FTPFile executeWithFtpClient(@androidx.annotation.NonNull
org.apache.commons.net.ftp.FTPClient ftpClient) throws java.io.IOException {
    java.lang.String path;
    path = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(getParent(com.amaze.filemanager.application.AppConfig.getInstance()));
    ftpClient.changeWorkingDirectory(path);
    for (org.apache.commons.net.ftp.FTPFile ftpFile : ftpClient.listFiles()) {
        if (ftpFile.getName().equals(getName(com.amaze.filemanager.application.AppConfig.getInstance())))
            return ftpFile;

    }
    return null;
}

});
}


public boolean isCustomPath() {
return (((((path.equals("0") || path.equals("1")) || path.equals("2")) || path.equals("3")) || path.equals("4")) || path.equals("5")) || path.equals("6");
}


/**
 * Helper method to get parent path
 */
@androidx.annotation.Nullable
public java.lang.String getParent(android.content.Context context) {
switch (mode) {
case SMB :
    jcifs.smb.SmbFile smbFile;
    smbFile = getSmbFile();
    if (smbFile != null) {
        return smbFile.getParent();
    }
    return "";
case FILE :
case ROOT :
    return getFile().getParent();
case TRASH_BIN :
    return "7";
case SFTP :
case DOCUMENT_FILE :
    java.lang.String thisPath;
    thisPath = path;
    if (thisPath.contains("%")) {
        try {
            thisPath = java.net.URLDecoder.decode(getPath(), kotlin.text.Charsets.UTF_8.name());
        } catch (java.io.UnsupportedEncodingException ignored) {
        }
    }
    java.util.List<java.lang.String> pathSegments;
    pathSegments = android.net.Uri.parse(thisPath).getPathSegments();
    if (thisPath.isEmpty() || pathSegments.isEmpty())
        return null;

    java.lang.String currentName;
    switch(MUID_STATIC) {
        // HybridFile_3_BinaryMutator
        case 3046: {
            currentName = pathSegments.get(pathSegments.size() + 1);
            break;
        }
        default: {
        currentName = pathSegments.get(pathSegments.size() - 1);
        break;
    }
}
int currentNameStartIndex;
currentNameStartIndex = thisPath.lastIndexOf(currentName);
if (currentNameStartIndex < 0) {
    return null;
}
java.lang.String parent;
parent = thisPath.substring(0, currentNameStartIndex);
if (kotlin.collections.ArraysKt.any(com.amaze.filemanager.filesystem.FileProperties.ANDROID_DATA_DIRS, (java.lang.String dir) -> parent.endsWith(dir + "/"))) {
    return com.amaze.filemanager.filesystem.FileProperties.unmapPathForApi30OrAbove(parent);
} else {
    return parent;
}
default :
if (getPath().length() <= getName(context).length()) {
    return null;
}
int start;
start = 0;
int end;
switch(MUID_STATIC) {
    // HybridFile_4_BinaryMutator
    case 4046: {
        end = (getPath().length() - getName(context).length()) + 1;
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // HybridFile_5_BinaryMutator
        case 5046: {
            end = (getPath().length() + getName(context).length()) - 1;
            break;
        }
        default: {
        end = (getPath().length() - getName(context).length()) - 1;
        break;
    }
}
break;
}
}
return getPath().substring(start, end);
}
}


/**
 * Whether this object refers to a directory or file, handles all types of files
 *
 * @deprecated use {@link #isDirectory(Context)} to handle content resolvers
 */
public boolean isDirectory() {
boolean isDirectory;
switch (mode) {
case SFTP :
case FTP :
case SMB :
return isDirectory(com.amaze.filemanager.application.AppConfig.getInstance());
case ROOT :
isDirectory = com.amaze.filemanager.fileoperations.filesystem.root.NativeOperations.isDirectory(path);
break;
case DOCUMENT_FILE :
return getDocumentFile(false).isDirectory();
case OTG :
// TODO: support for this method in OTG on-the-fly
// you need to manually call {@link RootHelper#getDocumentFile() method
isDirectory = false;
break;
case FILE :
case TRASH_BIN :
default :
isDirectory = getFile().isDirectory();
break;
}
return isDirectory;
}


public boolean isDirectory(android.content.Context context) {
switch (mode) {
case SFTP :
final java.lang.Boolean returnValue;
returnValue = com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.lang.Boolean>(path, true) {
@java.lang.Override
public java.lang.Boolean execute(@androidx.annotation.NonNull
net.schmizz.sshj.sftp.SFTPClient client) {
try {
    return client.stat(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(path)).getType().equals(net.schmizz.sshj.sftp.FileMode.Type.DIRECTORY);
} catch (java.io.IOException notFound) {
    com.amaze.filemanager.filesystem.HybridFile.LOG.error("Fail to execute isDirectory for SFTP path :" + path, notFound);
    return false;
}
}

});
if (returnValue == null) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error obtaining if path is directory over SFTP");
return false;
}
return returnValue;
case SMB :
try {
return io.reactivex.Single.fromCallable(() -> getSmbFile().isDirectory()).subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
} catch (java.lang.Exception e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get isDirectory with context for smb file", e);
return false;
}
case FTP :
org.apache.commons.net.ftp.FTPFile ftpFile;
ftpFile = getFtpFile();
return (ftpFile != null) && ftpFile.isDirectory();
case ROOT :
return com.amaze.filemanager.fileoperations.filesystem.root.NativeOperations.isDirectory(path);
case DOCUMENT_FILE :
androidx.documentfile.provider.DocumentFile documentFile;
documentFile = getDocumentFile(false);
return (documentFile != null) && documentFile.isDirectory();
case OTG :
androidx.documentfile.provider.DocumentFile otgFile;
otgFile = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, context, false);
return (otgFile != null) && otgFile.isDirectory();
case DROPBOX :
case BOX :
case GDRIVE :
case ONEDRIVE :
return io.reactivex.Single.fromCallable(() -> dataUtils.getAccount(mode).getMetadata(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, path)).getFolder()).subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
case TRASH_BIN :
default :
// also handles the case `FILE`
java.io.File file;
file = getFile();
return (file != null) && file.isDirectory();
}
}


/**
 *
 * @deprecated use {@link #folderSize(Context)}
 */
public long folderSize() {
long size;
size = 0L;
switch (mode) {
case SFTP :
case FTP :
return folderSize(com.amaze.filemanager.application.AppConfig.getInstance());
case SMB :
jcifs.smb.SmbFile smbFile;
smbFile = getSmbFile();
size = (smbFile != null) ? com.amaze.filemanager.filesystem.files.FileUtils.folderSize(getSmbFile()) : 0;
break;
case FILE :
case TRASH_BIN :
size = com.amaze.filemanager.filesystem.files.FileUtils.folderSize(getFile(), null);
break;
case ROOT :
com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
baseFile = generateBaseFileFromParent();
if (baseFile != null)
size = baseFile.getSize();

break;
default :
return 0L;
}
return size;
}


/**
 * Helper method to get length of folder in an otg
 */
public long folderSize(android.content.Context context) {
long size;
size = 0L;
switch (mode) {
case SFTP :
java.lang.Long retval;
retval = -1L;
java.lang.String result;
result = com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(getRemoteShellCommandLineResult("du -bs \"%s\""));
if ((!android.text.TextUtils.isEmpty(result)) && (result.indexOf('\t') > 0)) {
try {
retval = java.lang.Long.valueOf(result.substring(0, result.lastIndexOf('\t')));
} catch (java.lang.NumberFormatException ifParseFailed) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("Unable to parse result (Seen {\"\"}), resort to old method", result);
retval = -1L;
}
}
if (retval == (-1L)) {
java.lang.Long returnValue;
returnValue = com.amaze.filemanager.filesystem.ssh.SshClientUtils.sftpGetSize.invoke(getPath());
if (returnValue == null) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error obtaining size of folder over SFTP");
}
return returnValue == null ? 0L : returnValue;
}
return retval;
case SMB :
jcifs.smb.SmbFile smbFile;
smbFile = getSmbFile();
size = (smbFile != null) ? com.amaze.filemanager.filesystem.files.FileUtils.folderSize(smbFile) : 0L;
break;
case FILE :
case TRASH_BIN :
size = com.amaze.filemanager.filesystem.files.FileUtils.folderSize(getFile(), null);
break;
case ROOT :
com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
baseFile = generateBaseFileFromParent();
if (baseFile != null)
size = baseFile.getSize();

break;
case OTG :
size = com.amaze.filemanager.filesystem.files.FileUtils.otgFolderSize(path, context);
break;
case DOCUMENT_FILE :
final java.util.concurrent.atomic.AtomicLong totalBytes;
totalBytes = new java.util.concurrent.atomic.AtomicLong(0);
com.amaze.filemanager.utils.OTGUtil.getDocumentFiles(com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), path, context, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, (com.amaze.filemanager.filesystem.HybridFileParcelable file) -> totalBytes.addAndGet(com.amaze.filemanager.filesystem.files.FileUtils.getBaseFileSize(file, context)));
break;
case DROPBOX :
case BOX :
case GDRIVE :
case ONEDRIVE :
size = com.amaze.filemanager.filesystem.files.FileUtils.folderSizeCloud(mode, dataUtils.getAccount(mode).getMetadata(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, path)));
break;
case FTP :
default :
return 0L;
}
return size;
}


/**
 * Gets usable i.e. free space of a device
 */
public long getUsableSpace() {
long size;
size = 0L;
switch (mode) {
case SMB :
size = io.reactivex.Single.fromCallable(((java.util.concurrent.Callable<java.lang.Long>) (() -> {
try {
jcifs.smb.SmbFile smbFile;
smbFile = getSmbFile();
return smbFile != null ? smbFile.getDiskFreeSpace() : 0L;
} catch (jcifs.smb.SmbException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get usage space for smb file", e);
return 0L;
}
}))).subscribeOn(io.reactivex.schedulers.Schedulers.io()).blockingGet();
break;
case FILE :
case ROOT :
case TRASH_BIN :
size = getFile().getUsableSpace();
break;
case DROPBOX :
case BOX :
case GDRIVE :
case ONEDRIVE :
com.cloudrail.si.types.SpaceAllocation spaceAllocation;
spaceAllocation = dataUtils.getAccount(mode).getAllocation();
switch(MUID_STATIC) {
// HybridFile_6_BinaryMutator
case 6046: {
size = spaceAllocation.getTotal() + spaceAllocation.getUsed();
break;
}
default: {
size = spaceAllocation.getTotal() - spaceAllocation.getUsed();
break;
}
}
break;
case SFTP :
final java.lang.Long returnValue;
returnValue = com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.lang.Long>(path, true) {
@java.lang.Override
public java.lang.Long execute(@androidx.annotation.NonNull
net.schmizz.sshj.sftp.SFTPClient client) throws java.io.IOException {
try {
com.amaze.filemanager.filesystem.ssh.Statvfs.Response response;
response = new com.amaze.filemanager.filesystem.ssh.Statvfs.Response(path, client.getSFTPEngine().request(com.amaze.filemanager.filesystem.ssh.Statvfs.request(client, com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(path))).retrieve());
return response.diskFreeSpace();
} catch (net.schmizz.sshj.sftp.SFTPException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error querying server", e);
return 0L;
} catch (net.schmizz.sshj.common.Buffer.BufferException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error parsing reply", e);
return 0L;
}
}

});
if (returnValue == null) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error obtaining usable space over SFTP");
}
size = (returnValue == null) ? 0L : returnValue;
break;
case DOCUMENT_FILE :
size = com.amaze.filemanager.filesystem.FileProperties.getDeviceStorageRemainingSpace(com.amaze.filemanager.filesystem.SafRootHolder.INSTANCE.getVolumeLabel());
break;
case FTP :
/* Quirk, or dirty trick.

I think 99.9% FTP servers in this world will not report their disk's remaining space,
simply because they are not Serv-U (using AVBL command) or IIS (extended LIST command on
it own). But it doesn't make sense to simply block write to FTP servers either, hence
this value Integer.MAX_VALUE = 2048MB, which should be suitable for 99% of the cases.

File sizes bigger than this, either Android device (unless TV boxes) would have
difficulty to handle, either client and server side. In that case I shall recommend you
to send it in splits, or just move to better transmission mechanism, like WiFi Direct
as provided by Amaze File Utilities ;)

- TranceLove
 */
size = java.lang.Integer.MAX_VALUE;
case OTG :
// TODO: Get free space from OTG when {@link DocumentFile} API adds support
break;
}
return size;
}


/**
 * Gets total size of the disk
 */
public long getTotal(android.content.Context context) {
long size;
size = 0L;
switch (mode) {
case SMB :
// TODO: Find total storage space of SMB when JCIFS adds support
try {
jcifs.smb.SmbFile smbFile;
smbFile = getSmbFile();
size = (smbFile != null) ? smbFile.getDiskFreeSpace() : 0L;
} catch (jcifs.smb.SmbException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get total space for smb file", e);
}
break;
case FILE :
case ROOT :
case TRASH_BIN :
size = getFile().getTotalSpace();
break;
case DROPBOX :
case BOX :
case ONEDRIVE :
case GDRIVE :
com.cloudrail.si.types.SpaceAllocation spaceAllocation;
spaceAllocation = dataUtils.getAccount(mode).getAllocation();
size = spaceAllocation.getTotal();
break;
case SFTP :
final java.lang.Long returnValue;
returnValue = com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.lang.Long>(path, true) {
@java.lang.Override
public java.lang.Long execute(@androidx.annotation.NonNull
net.schmizz.sshj.sftp.SFTPClient client) throws java.io.IOException {
try {
com.amaze.filemanager.filesystem.ssh.Statvfs.Response response;
response = new com.amaze.filemanager.filesystem.ssh.Statvfs.Response(path, client.getSFTPEngine().request(com.amaze.filemanager.filesystem.ssh.Statvfs.request(client, com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(path))).retrieve());
return response.diskSize();
} catch (net.schmizz.sshj.sftp.SFTPException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error querying server", e);
return 0L;
} catch (net.schmizz.sshj.common.Buffer.BufferException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error parsing reply", e);
return 0L;
}
}

});
if (returnValue == null) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error obtaining total space over SFTP");
}
size = (returnValue == null) ? 0L : returnValue;
break;
case OTG :
// TODO: Find total storage space of OTG when {@link DocumentFile} API adds support
androidx.documentfile.provider.DocumentFile documentFile;
documentFile = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, context, false);
size = documentFile.length();
break;
case DOCUMENT_FILE :
size = getDocumentFile(false).length();
break;
case FTP :
size = 0L;
}
return size;
}


/**
 * Helper method to list children of this file
 */
public void forEachChildrenFile(android.content.Context context, boolean isRoot, com.amaze.filemanager.utils.OnFileFound onFileFound) {
switch (mode) {
case SFTP :
com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.lang.Boolean>(getPath(), true) {
@java.lang.Override
public java.lang.Boolean execute(@androidx.annotation.NonNull
net.schmizz.sshj.sftp.SFTPClient client) {
try {
for (net.schmizz.sshj.sftp.RemoteResourceInfo info : client.ls(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(getPath()))) {
    boolean isDirectory;
    isDirectory = false;
    try {
        isDirectory = com.amaze.filemanager.filesystem.ssh.SshClientUtils.isDirectory(client, info);
    } catch (java.io.IOException ifBrokenSymlink) {
        com.amaze.filemanager.filesystem.HybridFile.LOG.warn("IOException checking isDirectory(): " + info.getPath());
        continue;
    }
    com.amaze.filemanager.filesystem.HybridFileParcelable f;
    f = new com.amaze.filemanager.filesystem.HybridFileParcelable(getPath(), isDirectory, info);
    onFileFound.onFileFound(f);
}
} catch (java.io.IOException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("IOException", e);
com.amaze.filemanager.application.AppConfig.toast(context, context.getString(com.amaze.filemanager.R.string.cannot_read_directory, com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(getPath()), e.getMessage()));
}
return true;
}

});
break;
case SMB :
try {
jcifs.smb.SmbFile smbFile;
smbFile = getSmbFile();
if (smbFile != null) {
for (jcifs.smb.SmbFile smbFile1 : smbFile.listFiles()) {
com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
try {
    jcifs.smb.SmbFile sf;
    sf = new jcifs.smb.SmbFile(smbFile1.getURL(), smbFile.getContext());
    baseFile = new com.amaze.filemanager.filesystem.HybridFileParcelable(sf);
} catch (java.net.MalformedURLException shouldNeverHappen) {
    com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get children file for smb", shouldNeverHappen);
    baseFile = new com.amaze.filemanager.filesystem.HybridFileParcelable(smbFile1);
}
onFileFound.onFileFound(baseFile);
}
}
} catch (jcifs.smb.SmbException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get children file for smb file", e);
}
break;
case FTP :
java.lang.String thisPath;
thisPath = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(getPath());
org.apache.commons.net.ftp.FTPFile[] ftpFiles;
ftpFiles = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.execute(new com.amaze.filemanager.filesystem.ftp.FtpClientTemplate<org.apache.commons.net.ftp.FTPFile[]>(getPath(), false) {
public org.apache.commons.net.ftp.FTPFile[] executeWithFtpClient(@androidx.annotation.NonNull
org.apache.commons.net.ftp.FTPClient ftpClient) throws java.io.IOException {
ftpClient.changeWorkingDirectory(thisPath);
return ftpClient.listFiles();
}

});
for (org.apache.commons.net.ftp.FTPFile ftpFile : ftpFiles) {
onFileFound.onFileFound(new com.amaze.filemanager.filesystem.HybridFileParcelable(getPath(), ftpFile));
}
break;
case OTG :
com.amaze.filemanager.utils.OTGUtil.getDocumentFiles(path, context, onFileFound);
break;
case DOCUMENT_FILE :
com.amaze.filemanager.utils.OTGUtil.getDocumentFiles(com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), path, context, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, onFileFound);
break;
case DROPBOX :
case BOX :
case GDRIVE :
case ONEDRIVE :
try {
com.amaze.filemanager.filesystem.cloud.CloudUtil.getCloudFiles(path, dataUtils.getAccount(mode), mode, onFileFound);
} catch (com.amaze.filemanager.fileoperations.exceptions.CloudPluginException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get children file for cloud file", e);
}
break;
case TRASH_BIN :
default :
com.amaze.filemanager.filesystem.root.ListFilesCommand.INSTANCE.listFiles(path, isRoot, true, (com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode) -> null, (com.amaze.filemanager.filesystem.HybridFileParcelable hybridFileParcelable) -> {
onFileFound.onFileFound(hybridFileParcelable);
return null;
});
}
}


/**
 * Helper method to list children of this file
 *
 * @deprecated use forEachChildrenFile()
 */
public java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> listFiles(android.content.Context context, boolean isRoot) {
java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> arrayList;
arrayList = new java.util.ArrayList<>();
forEachChildrenFile(context, isRoot, arrayList::add);
return arrayList;
}


public java.lang.String getReadablePath(java.lang.String path) {
if ((isSftp() || isSmb()) || isFtp())
return com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(path);
else
return path;

}


public static java.lang.String parseAndFormatUriForDisplay(@androidx.annotation.NonNull
java.lang.String uriString) {
if ((uriString.startsWith(com.amaze.filemanager.filesystem.ftp.NetCopyClientConnectionPool.SSH_URI_PREFIX) || uriString.startsWith(com.amaze.filemanager.filesystem.ftp.NetCopyClientConnectionPool.FTP_URI_PREFIX)) || uriString.startsWith(com.amaze.filemanager.filesystem.ftp.NetCopyClientConnectionPool.FTPS_URI_PREFIX)) {
com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo connInfo;
connInfo = new com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo(uriString);
return connInfo.toString();
} else {
android.net.Uri uri;
uri = android.net.Uri.parse(uriString);
return com.amaze.filemanager.filesystem.HybridFile.formatUriForDisplayInternal(uri.getScheme(), uri.getHost(), uri.getPath());
}
}


private static java.lang.String formatUriForDisplayInternal(@androidx.annotation.NonNull
java.lang.String scheme, @androidx.annotation.NonNull
java.lang.String host, @androidx.annotation.NonNull
java.lang.String path) {
return java.lang.String.format("%s://%s%s", scheme, host, path);
}


/**
 * Handles getting input stream for various {@link OpenMode}
 *
 * @param context
 * @return  */
@androidx.annotation.Nullable
public java.io.InputStream getInputStream(android.content.Context context) {
java.io.InputStream inputStream;
switch (mode) {
case SFTP :
inputStream = com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.io.InputStream>(getPath(), false) {
@java.lang.Override
public java.io.InputStream execute(@androidx.annotation.NonNull
final net.schmizz.sshj.sftp.SFTPClient client) throws java.io.IOException {
final net.schmizz.sshj.sftp.RemoteFile rf;
rf = com.amaze.filemanager.filesystem.ssh.SFTPClientExtKt.openWithReadAheadSupport(client, com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(getPath()));
return rf.new ReadAheadRemoteFileInputStream(com.amaze.filemanager.filesystem.ssh.SFTPClientExtKt.READ_AHEAD_MAX_UNCONFIRMED_READS) {
@java.lang.Override
public void close() throws java.io.IOException {
    try {
        com.amaze.filemanager.filesystem.HybridFile.LOG.debug("Closing input stream for {}", getPath());
        super.close();
    } catch (java.lang.Throwable e) {
        e.printStackTrace();
    } finally {
        com.amaze.filemanager.filesystem.HybridFile.LOG.debug("Closing client for {}", getPath());
        rf.close();
        client.close();
    }
}

};
}

});
break;
case SMB :
try {
inputStream = getSmbFile().getInputStream();
} catch (java.io.IOException e) {
inputStream = null;
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get input stream for smb file", e);
}
break;
case FTP :
inputStream = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.execute(new com.amaze.filemanager.filesystem.ftp.FtpClientTemplate<java.io.InputStream>(getPath(), false) {
public java.io.InputStream executeWithFtpClient(@androidx.annotation.NonNull
org.apache.commons.net.ftp.FTPClient ftpClient) throws java.io.IOException {
java.lang.String parent;
parent = getParent(com.amaze.filemanager.application.AppConfig.getInstance());
/* Use temp file to hold the FTP file.

Due to the single thread nature of FTPClient, it is not possible to open
both input and output streams on the same FTP server on the same time.
Hence have to use placeholder temp file to hold contents for freeing out
the thread for output stream. - TranceLove
 */
java.io.File tmpFile;
tmpFile = java.io.File.createTempFile("ftp-transfer_", ".tmp");
tmpFile.deleteOnExit();
ftpClient.changeWorkingDirectory(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(parent));
ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
java.io.InputStream fin;
fin = ftpClient.retrieveFileStream(getName(com.amaze.filemanager.application.AppConfig.getInstance()));
java.io.FileOutputStream fout;
fout = new java.io.FileOutputStream(tmpFile);
kotlin.io.ByteStreamsKt.copyTo(fin, fout, com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
fin.close();
fout.close();
ftpClient.completePendingCommand();
return com.amaze.filemanager.filesystem.ftp.FTPClientImpl.wrap(tmpFile);
}

});
break;
case DOCUMENT_FILE :
android.content.ContentResolver contentResolver;
contentResolver = context.getContentResolver();
androidx.documentfile.provider.DocumentFile documentSourceFile;
documentSourceFile = getDocumentFile(false);
try {
inputStream = contentResolver.openInputStream(documentSourceFile.getUri());
} catch (java.io.FileNotFoundException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get input stream for document file", e);
inputStream = null;
}
break;
case OTG :
contentResolver = context.getContentResolver();
documentSourceFile = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, context, false);
try {
inputStream = contentResolver.openInputStream(documentSourceFile.getUri());
} catch (java.io.FileNotFoundException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get input stream for otg file", e);
inputStream = null;
}
break;
case DROPBOX :
case BOX :
case GDRIVE :
case ONEDRIVE :
com.cloudrail.si.interfaces.CloudStorage cloudStorageOneDrive;
cloudStorageOneDrive = dataUtils.getAccount(mode);
com.amaze.filemanager.filesystem.HybridFile.LOG.debug(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, path));
inputStream = cloudStorageOneDrive.download(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, path));
break;
case TRASH_BIN :
default :
try {
inputStream = new java.io.FileInputStream(path);
} catch (java.io.FileNotFoundException e) {
inputStream = null;
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get input stream", e);
}
break;
}
return inputStream;
}


@androidx.annotation.Nullable
public java.io.OutputStream getOutputStream(android.content.Context context) {
java.io.OutputStream outputStream;
switch (mode) {
case SFTP :
return com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.io.OutputStream>(getPath(), false) {
@androidx.annotation.Nullable
@java.lang.Override
public java.io.OutputStream execute(@androidx.annotation.NonNull
net.schmizz.sshj.sftp.SFTPClient client) throws java.io.IOException {
final net.schmizz.sshj.sftp.RemoteFile rf;
rf = client.open(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(getPath()), java.util.EnumSet.of(net.schmizz.sshj.sftp.OpenMode.WRITE, net.schmizz.sshj.sftp.OpenMode.CREAT));
return rf.new RemoteFileOutputStream() {
@java.lang.Override
public void close() throws java.io.IOException {
    try {
        super.close();
    } finally {
        try {
            rf.close();
            client.close();
        } catch (java.lang.Exception e) {
            com.amaze.filemanager.filesystem.HybridFile.LOG.warn("Error closing stream", e);
        }
    }
}

};
}

});
case FTP :
outputStream = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.execute(new com.amaze.filemanager.filesystem.ftp.FtpClientTemplate<java.io.OutputStream>(path, false) {
public java.io.OutputStream executeWithFtpClient(@androidx.annotation.NonNull
org.apache.commons.net.ftp.FTPClient ftpClient) throws java.io.IOException {
ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
java.lang.String remotePath;
remotePath = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(path);
java.io.OutputStream outputStream;
outputStream = ftpClient.storeFileStream(remotePath);
if (outputStream != null) {
return com.amaze.filemanager.filesystem.ftp.FTPClientImpl.wrap(outputStream, ftpClient);
} else {
return null;
}
}

});
return outputStream;
case SMB :
try {
outputStream = getSmbFile().getOutputStream();
} catch (java.io.IOException e) {
outputStream = null;
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get output stream for smb file", e);
}
break;
case DOCUMENT_FILE :
android.content.ContentResolver contentResolver;
contentResolver = context.getContentResolver();
androidx.documentfile.provider.DocumentFile documentSourceFile;
documentSourceFile = getDocumentFile(true);
try {
outputStream = contentResolver.openOutputStream(documentSourceFile.getUri());
} catch (java.io.FileNotFoundException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get output stream for document file", e);
outputStream = null;
}
break;
case OTG :
contentResolver = context.getContentResolver();
documentSourceFile = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, context, true);
try {
outputStream = contentResolver.openOutputStream(documentSourceFile.getUri());
} catch (java.io.FileNotFoundException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get output stream for otg file", e);
outputStream = null;
}
break;
case TRASH_BIN :
default :
try {
outputStream = com.amaze.filemanager.filesystem.FileUtil.getOutputStream(getFile(), context);
} catch (java.lang.Exception e) {
outputStream = null;
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get output stream", e);
}
}
return outputStream;
}


public boolean exists() {
boolean exists;
exists = false;
if (isSftp()) {
final java.lang.Boolean executionReturn;
executionReturn = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.<net.schmizz.sshj.SSHClient, java.lang.Boolean>execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.lang.Boolean>(path, true) {
@java.lang.Override
public java.lang.Boolean execute(net.schmizz.sshj.sftp.SFTPClient client) throws java.io.IOException {
try {
return client.stat(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(path)) != null;
} catch (net.schmizz.sshj.sftp.SFTPException notFound) {
return false;
}
}

});
if (executionReturn == null) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error obtaining existance of file over SFTP");
}
// noinspection SimplifiableConditionalExpression
exists = (executionReturn == null) ? false : executionReturn;
} else if (isSmb()) {
try {
jcifs.smb.SmbFile smbFile;
smbFile = getSmbFile(2000);
exists = (smbFile != null) && smbFile.exists();
} catch (jcifs.smb.SmbException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to find existence for smb file", e);
exists = false;
}
} else if (isFtp()) {
if (getPath().equals("/"))
exists = true;
else {
exists = getFtpFile() != null;
}
} else if (isDropBoxFile()) {
com.cloudrail.si.interfaces.CloudStorage cloudStorageDropbox;
cloudStorageDropbox = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX);
exists = cloudStorageDropbox.exists(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX, path));
} else if (isBoxFile()) {
com.cloudrail.si.interfaces.CloudStorage cloudStorageBox;
cloudStorageBox = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX);
exists = cloudStorageBox.exists(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX, path));
} else if (isGoogleDriveFile()) {
com.cloudrail.si.interfaces.CloudStorage cloudStorageGoogleDrive;
cloudStorageGoogleDrive = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE);
exists = cloudStorageGoogleDrive.exists(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE, path));
} else if (isOneDriveFile()) {
com.cloudrail.si.interfaces.CloudStorage cloudStorageOneDrive;
cloudStorageOneDrive = dataUtils.getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE);
exists = cloudStorageOneDrive.exists(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE, path));
} else if (isLocal()) {
exists = getFile().exists();
} else if (isRoot()) {
return com.amaze.filemanager.filesystem.RootHelper.fileExists(path);
} else if (isTrashBin()) {
if (getFile() != null)
return getFile().exists();
else
return false;

}
return exists;
}


/**
 * Helper method to check file existence in otg
 */
public boolean exists(android.content.Context context) {
boolean exists;
exists = false;
try {
if (isOtgFile()) {
exists = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, context, false) != null;
} else if (isDocumentFile()) {
exists = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), context, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, false) != null;
} else
return exists();

} catch (java.lang.Exception e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.info("Failed to find file", e);
}
return exists;
}


/**
 * Whether file is a simple file (i.e. not a directory/smb/otg/other)
 *
 * @return true if file; other wise false
 */
public boolean isSimpleFile() {
return ((((((((((((!isSmb()) && (!isOtgFile())) && (!isDocumentFile())) && (!isCustomPath())) && (!android.util.Patterns.EMAIL_ADDRESS.matcher(path).matches())) && ((getFile() != null) && (!getFile().isDirectory()))) && (!isOneDriveFile())) && (!isGoogleDriveFile())) && (!isDropBoxFile())) && (!isBoxFile())) && (!isSftp())) && (!isFtp())) && (!isTrashBin());
}


public boolean setLastModified(final long date) {
if (isSmb()) {
try {
jcifs.smb.SmbFile smbFile;
smbFile = getSmbFile();
if (smbFile != null) {
smbFile.setLastModified(date);
return true;
} else {
return false;
}
} catch (jcifs.smb.SmbException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get last modified for smb file", e);
return false;
}
} else if (isFtp()) {
return java.lang.Boolean.TRUE.equals(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.execute(new com.amaze.filemanager.filesystem.ftp.FtpClientTemplate<java.lang.Boolean>(path, false) {
public java.lang.Boolean executeWithFtpClient(@androidx.annotation.NonNull
org.apache.commons.net.ftp.FTPClient ftpClient) throws java.io.IOException {
return ftpClient.setModificationTime(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(path), com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.getTimestampForTouch(date));
}

}));
} else if (isSftp()) {
return java.lang.Boolean.TRUE.equals(com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SshClientSessionTemplate<java.lang.Boolean>(getPath()) {
@java.lang.Override
public java.lang.Boolean execute(@androidx.annotation.NonNull
net.schmizz.sshj.connection.channel.direct.Session session) throws java.io.IOException {
net.schmizz.sshj.connection.channel.direct.Session.Command cmd;
cmd = session.exec(java.lang.String.format(java.util.Locale.US, "touch -m -t %s \"%s\"", com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.getTimestampForTouch(date), getPath()));
// Quirk: need to wait the command to finish
net.schmizz.sshj.common.IOUtils.readFully(cmd.getInputStream());
cmd.close();
return 0 == cmd.getExitStatus();
}

}));
} else if (isTrashBin()) {
// do nothing
return true;
} else {
java.io.File f;
f = getFile();
return f.setLastModified(date);
}
}


public void mkdir(android.content.Context context) {
if (isSftp()) {
com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.lang.Boolean>(path, true) {
@java.lang.Override
public java.lang.Boolean execute(@androidx.annotation.NonNull
net.schmizz.sshj.sftp.SFTPClient client) {
try {
client.mkdir(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(path));
} catch (java.io.IOException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error making directory over SFTP", e);
}
return true;
}

});
} else if (isFtp()) {
com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.execute(new com.amaze.filemanager.filesystem.ftp.FtpClientTemplate<java.lang.Boolean>(getPath(), false) {
public java.lang.Boolean executeWithFtpClient(@androidx.annotation.NonNull
org.apache.commons.net.ftp.FTPClient ftpClient) throws java.io.IOException {
com.amaze.filemanager.filesystem.ftp.ExtensionsKt.makeDirectoryTree(ftpClient, com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(getPath()));
return true;
}

});
} else if (isSmb()) {
try {
getSmbFile().mkdirs();
} catch (jcifs.smb.SmbException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to make dir for smb file", e);
}
} else if (isOtgFile()) {
if (!exists(context)) {
androidx.documentfile.provider.DocumentFile parentDirectory;
parentDirectory = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(getParent(context), context, true);
if (parentDirectory.isDirectory()) {
parentDirectory.createDirectory(getName(context));
}
}
} else if (isDocumentFile()) {
if (!exists(context)) {
androidx.documentfile.provider.DocumentFile parentDirectory;
parentDirectory = com.amaze.filemanager.utils.OTGUtil.getDocumentFile(getParent(context), com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), context, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, true);
if (parentDirectory.isDirectory()) {
parentDirectory.createDirectory(getName(context));
}
}
} else if (isCloudDriveFile()) {
com.cloudrail.si.interfaces.CloudStorage cloudStorageDropbox;
cloudStorageDropbox = dataUtils.getAccount(mode);
try {
cloudStorageDropbox.createFolder(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, path));
} catch (java.lang.Exception e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to create folder for cloud file", e);
}
} else if (isTrashBin()) {
// do nothing
} else
com.amaze.filemanager.filesystem.MakeDirectoryOperation.mkdirs(context, this);

}


public boolean delete(android.content.Context context, boolean rootmode) throws com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException, jcifs.smb.SmbException {
if (isSftp()) {
java.lang.Boolean retval;
retval = com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(new com.amaze.filemanager.filesystem.ssh.SFtpClientTemplate<java.lang.Boolean>(path, true) {
@java.lang.Override
public java.lang.Boolean execute(@androidx.annotation.NonNull
net.schmizz.sshj.sftp.SFTPClient client) throws java.io.IOException {
java.lang.String _path;
_path = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(path);
if (isDirectory(com.amaze.filemanager.application.AppConfig.getInstance()))
client.rmdir(_path);
else
client.rm(_path);

return client.statExistence(_path) == null;
}

});
return (retval != null) && retval;
} else if (isFtp()) {
java.lang.Boolean retval;
retval = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.<org.apache.commons.net.ftp.FTPClient, java.lang.Boolean>execute(new com.amaze.filemanager.filesystem.ftp.FtpClientTemplate<java.lang.Boolean>(path, false) {
@java.lang.Override
public java.lang.Boolean executeWithFtpClient(@androidx.annotation.NonNull
org.apache.commons.net.ftp.FTPClient ftpClient) throws java.io.IOException {
return ftpClient.deleteFile(com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(path));
}

});
return (retval != null) && retval;
} else if (isSmb()) {
try {
getSmbFile().delete();
} catch (jcifs.smb.SmbException e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("Error delete SMB file", e);
throw e;
}
} else if (isTrashBin()) {
try {
deletePermanentlyFromBin(context);
} catch (java.lang.Exception e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.error("failed to delete trash bin file", e);
throw e;
}
} else if (isRoot() && rootmode) {
setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT);
com.amaze.filemanager.filesystem.root.DeleteFileCommand.INSTANCE.deleteFile(getPath());
} else {
com.amaze.filemanager.filesystem.DeleteOperation.deleteFile(getFile(), context);
}
return !exists();
}


public void restoreFromBin(android.content.Context context) {
java.util.List<com.amaze.trashbin.TrashBinFile> trashBinFiles;
trashBinFiles = java.util.Collections.singletonList(this.toTrashBinFile(context));
com.amaze.trashbin.TrashBin trashBin;
trashBin = com.amaze.filemanager.application.AppConfig.getInstance().getTrashBinInstance();
if (trashBin != null) {
trashBin.moveToBin(trashBinFiles, true, (java.lang.String originalFilePath,java.lang.String trashBinDestination) -> {
java.io.File source;
source = new java.io.File(originalFilePath);
java.io.File dest;
dest = new java.io.File(trashBinDestination);
if (!source.renameTo(dest)) {
return false;
}
com.amaze.filemanager.filesystem.files.MediaConnectionUtils.scanFile(context, new com.amaze.filemanager.filesystem.HybridFile[]{ this });
return true;
});
}
}


public boolean moveToBin(android.content.Context context) {
java.util.List<com.amaze.trashbin.TrashBinFile> trashBinFiles;
trashBinFiles = java.util.Collections.singletonList(this.toTrashBinFile(context));
com.amaze.trashbin.TrashBin trashBin;
trashBin = com.amaze.filemanager.application.AppConfig.getInstance().getTrashBinInstance();
if (trashBin != null) {
trashBin.moveToBin(trashBinFiles, true, (java.lang.String originalFilePath,java.lang.String trashBinDestination) -> {
java.io.File source;
source = new java.io.File(originalFilePath);
java.io.File dest;
dest = new java.io.File(trashBinDestination);
return source.renameTo(dest);
});
}
return true;
}


public boolean deletePermanentlyFromBin(android.content.Context context) {
java.util.List<com.amaze.trashbin.TrashBinFile> trashBinFiles;
trashBinFiles = java.util.Collections.singletonList(this.toTrashBinRestoreFile(context));
com.amaze.trashbin.TrashBin trashBin;
trashBin = com.amaze.filemanager.application.AppConfig.getInstance().getTrashBinInstance();
java.util.concurrent.atomic.AtomicBoolean isDelete;
isDelete = new java.util.concurrent.atomic.AtomicBoolean(false);
if (trashBin != null) {
trashBin.deletePermanently(trashBinFiles, (java.lang.String s) -> {
com.amaze.filemanager.filesystem.HybridFile.LOG.info("deleting from bin at path " + s);
isDelete.set(com.amaze.filemanager.filesystem.DeleteOperation.deleteFile(getFile(), context));
return isDelete.get();
}, true);
}
return isDelete.get();
}


/**
 * Returns the name of file excluding it's extension If no extension is found then whole file name
 * is returned
 */
public java.lang.String getNameString(android.content.Context context) {
java.lang.String fileName;
fileName = getName(context);
int extensionStartIndex;
extensionStartIndex = fileName.lastIndexOf(".");
return fileName.substring(0, extensionStartIndex == (-1) ? fileName.length() : extensionStartIndex);
}


/**
 * Generates a {@link LayoutElementParcelable} adapted compatible element. Currently supports only
 * local filesystem
 */
public com.amaze.filemanager.adapters.data.LayoutElementParcelable generateLayoutElement(@androidx.annotation.NonNull
android.content.Context c, boolean showThumbs) {
switch (mode) {
case FILE :
case ROOT :
case TRASH_BIN :
java.io.File file;
file = getFile();
com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElement;
if (isDirectory(c)) {
layoutElement = new com.amaze.filemanager.adapters.data.LayoutElementParcelable(c, path, com.amaze.filemanager.filesystem.RootHelper.parseFilePermission(file), "", folderSize() + "", 0, true, file.lastModified() + "", file.isDirectory(), showThumbs, mode);
} else {
layoutElement = new com.amaze.filemanager.adapters.data.LayoutElementParcelable(c, file.getPath(), com.amaze.filemanager.filesystem.RootHelper.parseFilePermission(file), file.getPath(), file.length() + "", file.length(), false, file.lastModified() + "", false, showThumbs, mode);
}
return layoutElement;
default :
return null;
}
}


/**
 * Open this hybrid file
 *
 * @param activity
 * @param doShowDialog
 * 		should show confirmation dialog (in case of deeplink)
 */
public void openFile(com.amaze.filemanager.ui.activities.MainActivity activity, boolean doShowDialog) {
if (doShowDialog) {
java.util.concurrent.atomic.AtomicReference<java.lang.String> md5;
md5 = new java.util.concurrent.atomic.AtomicReference<>(activity.getString(com.amaze.filemanager.R.string.calculating));
java.util.concurrent.atomic.AtomicReference<java.lang.String> sha256;
sha256 = new java.util.concurrent.atomic.AtomicReference<>(activity.getString(com.amaze.filemanager.R.string.calculating));
java.util.concurrent.atomic.AtomicReference<java.lang.String> pathToDisplay;
pathToDisplay = new java.util.concurrent.atomic.AtomicReference<>();
pathToDisplay.set(path);
if ((isSftp() || isSmb()) || isFtp()) {
com.amaze.filemanager.filesystem.HybridFile.LOG.debug("convert authorised path to simple path for display");
pathToDisplay.set(com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(path));
}
java.util.concurrent.atomic.AtomicReference<java.lang.String> dialogContent;
dialogContent = new java.util.concurrent.atomic.AtomicReference<>(java.lang.String.format(activity.getResources().getString(com.amaze.filemanager.R.string.open_file_confirmation), getName(activity), pathToDisplay.get(), android.text.format.Formatter.formatShortFileSize(activity, length(activity)), md5.get(), sha256.get()));
com.afollestad.materialdialogs.MaterialDialog dialog;
dialog = com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showOpenFileDeeplinkDialog(this, activity, dialogContent.get(), () -> openFileInternal(activity));
dialog.show();
getMd5Checksum(activity, (java.lang.String s) -> {
md5.set(s);
dialogContent.set(java.lang.String.format(activity.getResources().getString(com.amaze.filemanager.R.string.open_file_confirmation), getName(activity), pathToDisplay.get(), android.text.format.Formatter.formatShortFileSize(activity, length(activity)), md5.get(), sha256.get()));
dialog.setContent(dialogContent.get());
return null;
});
getSha256Checksum(activity, (java.lang.String s) -> {
sha256.set(s);
dialogContent.set(java.lang.String.format(activity.getResources().getString(com.amaze.filemanager.R.string.open_file_confirmation), getName(activity), pathToDisplay.get(), android.text.format.Formatter.formatShortFileSize(activity, length(activity)), md5.get(), sha256.get()));
dialog.setContent(dialogContent.get());
return null;
});
} else {
openFileInternal(activity);
}
}


public void getMd5Checksum(android.content.Context context, androidx.arch.core.util.Function<java.lang.String, java.lang.Void> callback) {
io.reactivex.Single.fromCallable(() -> {
switch(MUID_STATIC) {
// HybridFile_7_BinaryMutator
case 7046: {
try {
switch (mode) {
case SFTP :
java.lang.String md5Command;
md5Command = "md5sum -b \"%s\" | cut -c -32";
return com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(getRemoteShellCommandLineResult(md5Command));
default :
byte[] b;
b = createChecksum(context);
java.lang.String result;
result = "";
for (byte aB : b) {
    result += java.lang.Integer.toString((aB & 0xff) - 0x100, 16).substring(1);
}
return result;
}
} catch (java.lang.Exception e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get md5 checksum for sftp file", e);
return context.getString(com.amaze.filemanager.R.string.error);
}
}
default: {
try {
switch (mode) {
case SFTP :
java.lang.String md5Command;
md5Command = "md5sum -b \"%s\" | cut -c -32";
return com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(getRemoteShellCommandLineResult(md5Command));
default :
byte[] b;
b = createChecksum(context);
java.lang.String result;
result = "";
for (byte aB : b) {
    result += java.lang.Integer.toString((aB & 0xff) + 0x100, 16).substring(1);
}
return result;
}
} catch (java.lang.Exception e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get md5 checksum for sftp file", e);
return context.getString(com.amaze.filemanager.R.string.error);
}
}
}
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(new io.reactivex.SingleObserver<java.lang.String>() {
@java.lang.Override
public void onSubscribe(io.reactivex.disposables.Disposable d) {
}


@java.lang.Override
public void onSuccess(java.lang.String t) {
callback.apply(t);
}


@java.lang.Override
public void onError(java.lang.Throwable e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get md5 for sftp file", e);
callback.apply(context.getString(com.amaze.filemanager.R.string.error));
}

});
}


public void getSha256Checksum(android.content.Context context, androidx.arch.core.util.Function<java.lang.String, java.lang.Void> callback) {
io.reactivex.Single.fromCallable(() -> {
try {
switch (mode) {
case SFTP :
java.lang.String shaCommand;
shaCommand = "sha256sum -b \"%s\" | cut -c -64";
return com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(getRemoteShellCommandLineResult(shaCommand));
default :
java.security.MessageDigest messageDigest;
messageDigest = java.security.MessageDigest.getInstance("SHA-256");
byte[] input;
input = new byte[com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE];
int length;
java.io.InputStream inputStream;
inputStream = getInputStream(context);
while ((length = inputStream.read(input)) != (-1)) {
if (length > 0)
messageDigest.update(input, 0, length);

} 
byte[] hash;
hash = messageDigest.digest();
java.lang.StringBuilder hexString;
hexString = new java.lang.StringBuilder();
for (byte aHash : hash) {
// convert hash to base 16
java.lang.String hex;
hex = java.lang.Integer.toHexString(0xff & aHash);
if (hex.length() == 1)
hexString.append('0');

hexString.append(hex);
}
inputStream.close();
return hexString.toString();
}
} catch (java.io.IOException | java.security.NoSuchAlgorithmException ne) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get sha checksum for sftp file", ne);
return context.getString(com.amaze.filemanager.R.string.error);
}
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(new io.reactivex.SingleObserver<java.lang.String>() {
@java.lang.Override
public void onSubscribe(io.reactivex.disposables.Disposable d) {
}


@java.lang.Override
public void onSuccess(java.lang.String t) {
callback.apply(t);
}


@java.lang.Override
public void onError(java.lang.Throwable e) {
com.amaze.filemanager.filesystem.HybridFile.LOG.warn("failed to get sha256 for file", e);
callback.apply(context.getString(com.amaze.filemanager.R.string.error));
}

});
}


/**
 * Returns trash bin file with path that points to deleted path
 *
 * @param context
 * @return  */
public com.amaze.trashbin.TrashBinFile toTrashBinFile(android.content.Context context) {
return new com.amaze.trashbin.TrashBinFile(getName(context), isDirectory(context), path, length(context), null);
}


/**
 * Returns trash bin file with path that points to where the file should be restored
 *
 * @param context
 * @return  */
public com.amaze.trashbin.TrashBinFile toTrashBinRestoreFile(android.content.Context context) {
com.amaze.trashbin.TrashBin trashBin;
trashBin = com.amaze.filemanager.application.AppConfig.getInstance().getTrashBinInstance();
for (com.amaze.trashbin.TrashBinFile trashBinFile : trashBin.listFilesInBin()) {
if (trashBinFile.getDeletedPath(trashBin.getConfig()).equals(path)) {
// finding path to restore to
return new com.amaze.trashbin.TrashBinFile(getName(context), isDirectory(context), trashBinFile.getPath(), length(context), null);
}
}
return null;
}


private com.amaze.filemanager.filesystem.ssh.SshClientSessionTemplate<java.lang.String> getRemoteShellCommandLineResult(java.lang.String command) {
return new com.amaze.filemanager.filesystem.ssh.SshClientSessionTemplate<java.lang.String>(path) {
@java.lang.Override
public java.lang.String execute(net.schmizz.sshj.connection.channel.direct.Session session) throws java.io.IOException {
java.lang.String extractedPath;
extractedPath = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.extractRemotePathFrom(getPath());
java.lang.String fullCommand;
fullCommand = java.lang.String.format(command, extractedPath);
net.schmizz.sshj.connection.channel.direct.Session.Command cmd;
cmd = session.exec(fullCommand);
java.lang.String result;
result = new java.lang.String(net.schmizz.sshj.common.IOUtils.readFully(cmd.getInputStream()).toByteArray());
cmd.close();
if (cmd.getExitStatus() == 0) {
return result;
} else {
return null;
}
}

};
}


private byte[] createChecksum(android.content.Context context) throws java.lang.Exception {
java.io.InputStream fis;
fis = getInputStream(context);
byte[] buffer;
buffer = new byte[8192];
java.security.MessageDigest complete;
complete = java.security.MessageDigest.getInstance("MD5");
int numRead;
do {
numRead = fis.read(buffer);
if (numRead > 0) {
complete.update(buffer, 0, numRead);
}
} while (numRead != (-1) );
fis.close();
return complete.digest();
}


private void openFileInternal(com.amaze.filemanager.ui.activities.MainActivity activity) {
switch (mode) {
case SMB :
com.amaze.filemanager.filesystem.files.FileUtils.launchSMB(this, activity);
break;
case SFTP :
case FTP :
android.widget.Toast.makeText(activity, activity.getResources().getString(com.amaze.filemanager.R.string.please_wait), android.widget.Toast.LENGTH_LONG).show();
com.amaze.filemanager.filesystem.ssh.SshClientUtils.launchFtp(this, activity);
break;
case OTG :
com.amaze.filemanager.filesystem.files.FileUtils.openFile(com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, activity, false), activity, activity.getPrefs());
break;
case DOCUMENT_FILE :
com.amaze.filemanager.filesystem.files.FileUtils.openFile(com.amaze.filemanager.utils.OTGUtil.getDocumentFile(path, com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), activity, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, false), activity, activity.getPrefs());
break;
case DROPBOX :
case BOX :
case GDRIVE :
case ONEDRIVE :
android.widget.Toast.makeText(activity, activity.getResources().getString(com.amaze.filemanager.R.string.please_wait), android.widget.Toast.LENGTH_LONG).show();
com.amaze.filemanager.filesystem.cloud.CloudUtil.launchCloud(this, mode, activity);
break;
default :
com.amaze.filemanager.filesystem.files.FileUtils.openFile(new java.io.File(path), activity, activity.getPrefs());
break;
}
}


private void sanitizePathAsNecessary() {
this.path = this.path.replaceAll(com.amaze.filemanager.filesystem.ftp.NetCopyConnectionInfo.MULTI_SLASH, "/");
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
