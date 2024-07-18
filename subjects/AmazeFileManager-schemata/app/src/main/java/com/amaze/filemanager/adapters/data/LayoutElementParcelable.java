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
package com.amaze.filemanager.adapters.data;
import com.amaze.filemanager.filesystem.files.sort.ComparableParcelable;
import android.os.Parcel;
import androidx.annotation.DrawableRes;
import java.util.Calendar;
import com.amaze.filemanager.utils.Utils;
import androidx.annotation.NonNull;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import com.amaze.filemanager.ui.icons.Icons;
import java.io.File;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import android.os.Parcelable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class LayoutElementParcelable implements android.os.Parcelable , com.amaze.filemanager.filesystem.files.sort.ComparableParcelable {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String CURRENT_YEAR = java.lang.String.valueOf(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));

    public final boolean isBack;

    public final int filetype;

    public final com.amaze.filemanager.adapters.data.IconDataParcelable iconData;

    public final java.lang.String title;

    public final java.lang.String desc;

    public final java.lang.String permissions;

    public final java.lang.String symlink;

    public java.lang.String size;

    public boolean isDirectory;

    public long date;

    public long longSize;

    public java.lang.String dateModification;

    public final boolean header;

    // same as hfile.modes but different than openmode in Main.java
    private com.amaze.filemanager.fileoperations.filesystem.OpenMode mode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE;

    public LayoutElementParcelable(@androidx.annotation.NonNull
    android.content.Context c, boolean isBack, java.lang.String goback, boolean showThumbs) {
        this(c, true, new java.io.File("..").getName(), "..", "", "", goback, 0, false, "", true, showThumbs, com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN);
    }


    public LayoutElementParcelable(@androidx.annotation.NonNull
    android.content.Context c, java.lang.String path, java.lang.String permissions, java.lang.String symlink, java.lang.String size, long longSize, boolean header, java.lang.String date, boolean isDirectory, boolean useThumbs, com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode) {
        this(c, new java.io.File(path).getName(), path, permissions, symlink, size, longSize, header, date, isDirectory, useThumbs, openMode);
    }


    public LayoutElementParcelable(@androidx.annotation.NonNull
    android.content.Context c, java.lang.String title, java.lang.String path, java.lang.String permissions, java.lang.String symlink, java.lang.String size, long longSize, boolean header, java.lang.String date, boolean isDirectory, boolean useThumbs, com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode) {
        this(c, false, title, path, permissions, symlink, size, longSize, header, date, isDirectory, useThumbs, openMode);
    }


    public LayoutElementParcelable(@androidx.annotation.NonNull
    android.content.Context c, boolean isBack, java.lang.String title, java.lang.String path, java.lang.String permissions, java.lang.String symlink, java.lang.String size, long longSize, boolean header, java.lang.String date, boolean isDirectory, boolean useThumbs, com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode) {
        filetype = com.amaze.filemanager.ui.icons.Icons.getTypeOfFile(path, isDirectory);
        @androidx.annotation.DrawableRes
        int fallbackIcon;
        fallbackIcon = com.amaze.filemanager.ui.icons.Icons.loadMimeIcon(path, isDirectory);
        this.mode = openMode;
        if (useThumbs) {
            switch (mode) {
                case SMB :
                case SFTP :
                case DROPBOX :
                case GDRIVE :
                case ONEDRIVE :
                case BOX :
                    if ((!isDirectory) && (((filetype == com.amaze.filemanager.ui.icons.Icons.IMAGE) || (filetype == com.amaze.filemanager.ui.icons.Icons.VIDEO)) || (filetype == com.amaze.filemanager.ui.icons.Icons.APK))) {
                        this.iconData = new com.amaze.filemanager.adapters.data.IconDataParcelable(com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_FROMCLOUD, path, fallbackIcon);
                    } else {
                        this.iconData = new com.amaze.filemanager.adapters.data.IconDataParcelable(com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_RES, fallbackIcon);
                    }
                    break;
                    // Until we find a way to properly handle threading issues with thread unsafe FTPClient,
                    // we refrain from loading any files via FTP as file thumbnail. - TranceLove
                case FTP :
                    this.iconData = new com.amaze.filemanager.adapters.data.IconDataParcelable(com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_RES, fallbackIcon);
                    break;
                default :
                    if (((filetype == com.amaze.filemanager.ui.icons.Icons.IMAGE) || (filetype == com.amaze.filemanager.ui.icons.Icons.VIDEO)) || (filetype == com.amaze.filemanager.ui.icons.Icons.APK)) {
                        this.iconData = new com.amaze.filemanager.adapters.data.IconDataParcelable(com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_FROMFILE, path, fallbackIcon);
                    } else {
                        this.iconData = new com.amaze.filemanager.adapters.data.IconDataParcelable(com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_RES, fallbackIcon);
                    }
            }
        } else {
            this.iconData = new com.amaze.filemanager.adapters.data.IconDataParcelable(com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_RES, fallbackIcon);
        }
        this.title = title;
        this.desc = path;
        this.permissions = permissions.trim();
        this.symlink = symlink.trim();
        this.size = size;
        this.header = header;
        this.longSize = longSize;
        this.isDirectory = isDirectory;
        if (!date.trim().equals("")) {
            this.date = java.lang.Long.parseLong(date);
            this.dateModification = com.amaze.filemanager.utils.Utils.getDate(c, this.date);
        } else {
            this.date = 0;
            this.dateModification = "";
        }
        this.isBack = isBack;
    }


    public com.amaze.filemanager.fileoperations.filesystem.OpenMode getMode() {
        return mode;
    }


    public void setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode mode) {
        this.mode = mode;
    }


    public com.amaze.filemanager.filesystem.HybridFileParcelable generateBaseFile() {
        com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
        baseFile = new com.amaze.filemanager.filesystem.HybridFileParcelable(desc, permissions, date, longSize, isDirectory);
        baseFile.setMode(mode);
        baseFile.setName(title);
        return baseFile;
    }


    public boolean hasSymlink() {
        return (symlink != null) && (symlink.length() != 0);
    }


    @java.lang.Override
    public java.lang.String toString() {
        return (title + "\n") + desc;
    }


    // Hopefully it should be safe - nobody else is using this
    public LayoutElementParcelable(android.os.Parcel im) {
        filetype = im.readInt();
        iconData = im.readParcelable(com.amaze.filemanager.adapters.data.IconDataParcelable.class.getClassLoader());
        title = im.readString();
        desc = im.readString();
        permissions = im.readString();
        symlink = im.readString();
        int j;
        j = im.readInt();
        date = im.readLong();
        int i;
        i = im.readInt();
        header = i != 0;
        isDirectory = j != 0;
        dateModification = im.readString();
        size = im.readString();
        longSize = im.readLong();
        isBack = im.readInt() != 0;
    }


    @java.lang.Override
    public int describeContents() {
        // TODO: Implement this method
        return 0;
    }


    @java.lang.Override
    public void writeToParcel(android.os.Parcel p1, int p2) {
        p1.writeInt(filetype);
        p1.writeParcelable(iconData, 0);
        p1.writeString(title);
        p1.writeString(desc);
        p1.writeString(permissions);
        p1.writeString(symlink);
        p1.writeInt(isDirectory ? 1 : 0);
        p1.writeLong(date);
        p1.writeInt(header ? 1 : 0);
        p1.writeString(dateModification);
        p1.writeString(size);
        p1.writeLong(longSize);
        p1.writeInt(isBack ? 1 : 0);
    }


    public static final android.os.Parcelable.Creator<com.amaze.filemanager.adapters.data.LayoutElementParcelable> CREATOR = new android.os.Parcelable.Creator<com.amaze.filemanager.adapters.data.LayoutElementParcelable>() {
        public com.amaze.filemanager.adapters.data.LayoutElementParcelable createFromParcel(android.os.Parcel in) {
            return new com.amaze.filemanager.adapters.data.LayoutElementParcelable(in);
        }


        public com.amaze.filemanager.adapters.data.LayoutElementParcelable[] newArray(int size) {
            return new com.amaze.filemanager.adapters.data.LayoutElementParcelable[size];
        }

    };

    @java.lang.Override
    public boolean isDirectory() {
        return isDirectory;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public java.lang.String getParcelableName() {
        return title;
    }


    @java.lang.Override
    public long getDate() {
        return date;
    }


    @java.lang.Override
    public long getSize() {
        return longSize;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
