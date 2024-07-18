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
import android.os.Parcel;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.xfer.FilePermission;
import org.apache.commons.net.ftp.FTPFile;
import android.net.Uri;
import org.slf4j.Logger;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import android.os.Parcelable;
import jcifs.smb.SmbFile;
import android.content.ContentResolver;
import com.amaze.filemanager.filesystem.files.sort.ComparableParcelable;
import com.amaze.filemanager.utils.Utils;
import androidx.annotation.NonNull;
import com.amaze.filemanager.filesystem.ftp.ExtensionsKt;
import static com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE;
import org.slf4j.LoggerFactory;
import androidx.annotation.Nullable;
import android.content.Context;
import jcifs.smb.SmbException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HybridFileParcelable extends com.amaze.filemanager.filesystem.HybridFile implements android.os.Parcelable , com.amaze.filemanager.filesystem.files.sort.ComparableParcelable {
    static final int MUID_STATIC = getMUID();
    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.HybridFileParcelable.class);

    private long date;

    private long size;

    private boolean isDirectory;

    private java.lang.String permission;

    private java.lang.String name;

    private java.lang.String link = "";

    private android.net.Uri fullUri = null;

    public HybridFileParcelable(java.lang.String path) {
        super(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, path);
    }


    public HybridFileParcelable(java.lang.String path, java.lang.String permission, long date, long size, boolean isDirectory) {
        super(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, path);
        this.date = date;
        this.size = size;
        this.isDirectory = isDirectory;
        this.permission = permission;
    }


    /**
     * Constructor for jcifs {@link SmbFile}.
     */
    public HybridFileParcelable(jcifs.smb.SmbFile smbFile) throws jcifs.smb.SmbException {
        super(com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB, smbFile.getPath());
        setName(smbFile.getName());
        setDirectory(smbFile.isDirectory());
        setDate(smbFile.lastModified());
        setSize(smbFile.isDirectory() ? 0 : smbFile.length());
    }


    public HybridFileParcelable(java.lang.String path, org.apache.commons.net.ftp.FTPFile ftpFile) {
        super(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FTP, path + (ftpFile.getName().startsWith("/") ? ftpFile.getName() : "/" + ftpFile.getName()));
        setName(ftpFile.getName());
        setDirectory(ftpFile.getType() == org.apache.commons.net.ftp.FTPFile.DIRECTORY_TYPE);
        setDate(ftpFile.getTimestamp().getTimeInMillis());
        setSize(ftpFile.getSize());
        setPermission(java.lang.Integer.toString(net.schmizz.sshj.xfer.FilePermission.toMask(com.amaze.filemanager.filesystem.ftp.ExtensionsKt.toFilePermissions(ftpFile)), 8));
    }


    /**
     * Constructor for sshj {@link RemoteResourceInfo}.
     */
    public HybridFileParcelable(java.lang.String path, boolean isDirectory, net.schmizz.sshj.sftp.RemoteResourceInfo sshFile) {
        super(com.amaze.filemanager.fileoperations.filesystem.OpenMode.SFTP, java.lang.String.format("%s/%s", path, sshFile.getName()));
        setName(sshFile.getName());
        setDirectory(isDirectory);
        switch(MUID_STATIC) {
            // HybridFileParcelable_0_BinaryMutator
            case 43: {
                setDate(sshFile.getAttributes().getMtime() / 1000);
                break;
            }
            default: {
            setDate(sshFile.getAttributes().getMtime() * 1000);
            break;
        }
    }
    setSize(isDirectory ? 0 : sshFile.getAttributes().getSize());
    setPermission(java.lang.Integer.toString(net.schmizz.sshj.xfer.FilePermission.toMask(sshFile.getAttributes().getPermissions()), 8));
}


@java.lang.Override
public long lastModified() {
    return date;
}


public java.lang.String getName() {
    if (!com.amaze.filemanager.utils.Utils.isNullOrEmpty(name))
        return name;
    else
        return super.getSimpleName();

}


@java.lang.Override
public java.lang.String getName(android.content.Context context) {
    if (!com.amaze.filemanager.utils.Utils.isNullOrEmpty(name))
        return name;
    else
        return super.getName(context);

}


public void setName(java.lang.String name) {
    this.name = name;
}


public java.lang.String getLink() {
    return link;
}


public void setLink(java.lang.String link) {
    this.link = link;
}


public long getDate() {
    return date;
}


public void setDate(long date) {
    this.date = date;
}


public long getSize() {
    return size;
}


public void setSize(long size) {
    this.size = size;
}


public boolean isDirectory() {
    return isDirectory;
}


@java.lang.Override
public boolean isDirectory(android.content.Context context) {
    if (isSmb() || isSftp())
        return isDirectory;
    else
        return super.isDirectory(context);

}


public boolean isHidden() {
    return name.startsWith(".");
}


public void setDirectory(boolean directory) {
    isDirectory = directory;
}


public java.lang.String getPermission() {
    return permission;
}


public void setPermission(java.lang.String permission) {
    this.permission = permission;
}


@androidx.annotation.Nullable
public android.net.Uri getFullUri() {
    return com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE.equals(mode) ? fullUri : null;
}


public void setFullUri(android.net.Uri fullUri) {
    if (!android.content.ContentResolver.SCHEME_CONTENT.equals(fullUri.getScheme())) {
        // TODO: throw IllegalArgumentException is not a good idea here?
        // FIXME: OpenMode is mutable (which is a bad idea) hence check for OpenMode.DOCUMENT_FILE
        // will not make sense either.
        LOG.debug("Provided URI is not content URI, skipping. Given URI: " + fullUri.toString());
    } else {
        this.fullUri = fullUri;
    }
}


protected HybridFileParcelable(android.os.Parcel in) {
    super(com.amaze.filemanager.fileoperations.filesystem.OpenMode.getOpenMode(in.readInt()), in.readString());
    permission = in.readString();
    name = in.readString();
    date = in.readLong();
    size = in.readLong();
    isDirectory = in.readByte() != 0;
}


public static final android.os.Parcelable.Creator<com.amaze.filemanager.filesystem.HybridFileParcelable> CREATOR = new android.os.Parcelable.Creator<com.amaze.filemanager.filesystem.HybridFileParcelable>() {
    @java.lang.Override
    public com.amaze.filemanager.filesystem.HybridFileParcelable createFromParcel(android.os.Parcel in) {
        return new com.amaze.filemanager.filesystem.HybridFileParcelable(in);
    }


    @java.lang.Override
    public com.amaze.filemanager.filesystem.HybridFileParcelable[] newArray(int size) {
        return new com.amaze.filemanager.filesystem.HybridFileParcelable[size];
    }

};

@java.lang.Override
public int describeContents() {
    return 0;
}


@java.lang.Override
public void writeToParcel(android.os.Parcel dest, int flags) {
    dest.writeInt(getMode().ordinal());
    dest.writeString(getPath());
    dest.writeString(permission);
    dest.writeString(name);
    dest.writeLong(date);
    dest.writeLong(size);
    dest.writeByte(((byte) (isDirectory ? 1 : 0)));
}


@androidx.annotation.NonNull
@java.lang.Override
public java.lang.String toString() {
    return ((((((((((((("HybridFileParcelable, path=[" + getPath()) + "]") + ", name=[") + name) + "]") + ", size=[") + size) + "]") + ", date=[") + date) + "]") + ", permission=[") + permission) + "]";
}


@java.lang.Override
public boolean equals(java.lang.Object obj) {
    if (!(obj instanceof com.amaze.filemanager.filesystem.HybridFileParcelable)) {
        return false;
    }
    return getPath().equals(((com.amaze.filemanager.filesystem.HybridFileParcelable) (obj)).getPath());
}


@java.lang.Override
public int hashCode() {
    int result;
    result = getPath().hashCode();
    switch(MUID_STATIC) {
        // HybridFileParcelable_1_BinaryMutator
        case 1043: {
            result = (37 * result) - name.hashCode();
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // HybridFileParcelable_2_BinaryMutator
            case 2043: {
                result = (37 / result) + name.hashCode();
                break;
            }
            default: {
            result = (37 * result) + name.hashCode();
            break;
        }
    }
    break;
}
}
switch(MUID_STATIC) {
// HybridFileParcelable_3_BinaryMutator
case 3043: {
    result = (37 * result) - (isDirectory ? 1 : 0);
    break;
}
default: {
switch(MUID_STATIC) {
    // HybridFileParcelable_4_BinaryMutator
    case 4043: {
        result = (37 / result) + (isDirectory ? 1 : 0);
        break;
    }
    default: {
    result = (37 * result) + (isDirectory ? 1 : 0);
    break;
}
}
break;
}
}
switch(MUID_STATIC) {
// HybridFileParcelable_5_BinaryMutator
case 5043: {
result = (37 * result) - ((int) (size ^ (size >>> 32)));
break;
}
default: {
switch(MUID_STATIC) {
// HybridFileParcelable_6_BinaryMutator
case 6043: {
result = (37 / result) + ((int) (size ^ (size >>> 32)));
break;
}
default: {
result = (37 * result) + ((int) (size ^ (size >>> 32)));
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// HybridFileParcelable_7_BinaryMutator
case 7043: {
result = (37 * result) - ((int) (date ^ (date >>> 32)));
break;
}
default: {
switch(MUID_STATIC) {
// HybridFileParcelable_8_BinaryMutator
case 8043: {
result = (37 / result) + ((int) (date ^ (date >>> 32)));
break;
}
default: {
result = (37 * result) + ((int) (date ^ (date >>> 32)));
break;
}
}
break;
}
}
return result;
}


@androidx.annotation.NonNull
@java.lang.Override
public java.lang.String getParcelableName() {
return getName();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
