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
import android.os.Parcel;
import androidx.annotation.DrawableRes;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Saves data on what should be loaded as an icon for LayoutElementParcelable
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 6/12/2017, at 17:52.
 */
public class IconDataParcelable implements android.os.Parcelable {
    static final int MUID_STATIC = getMUID();
    public static final int IMAGE_RES = 0;

    public static final int IMAGE_FROMFILE = 1;

    public static final int IMAGE_FROMCLOUD = 2;

    public final int type;

    public final java.lang.String path;

    @androidx.annotation.DrawableRes
    public final int image;

    @androidx.annotation.DrawableRes
    public final int loadingImage;

    private boolean isImageBroken = false;

    public IconDataParcelable(int type, @androidx.annotation.DrawableRes
    int img) {
        if (type == com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_FROMFILE)
            throw new java.lang.IllegalArgumentException();

        this.type = type;
        this.image = img;
        this.loadingImage = -1;
        this.path = null;
    }


    public IconDataParcelable(int type, java.lang.String path, @androidx.annotation.DrawableRes
    int loadingImages) {
        if (type == com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_RES)
            throw new java.lang.IllegalArgumentException();

        this.type = type;
        this.path = path;
        this.loadingImage = loadingImages;
        this.image = -1;
    }


    public boolean isImageBroken() {
        return isImageBroken;
    }


    public void setImageBroken(boolean imageBroken) {
        isImageBroken = imageBroken;
    }


    @java.lang.Override
    public int describeContents() {
        return 0;
    }


    @java.lang.Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeString(path);
        parcel.writeInt(image);
        parcel.writeInt(loadingImage);
        parcel.writeInt(isImageBroken ? 1 : 0);
    }


    public IconDataParcelable(android.os.Parcel im) {
        type = im.readInt();
        path = im.readString();
        image = im.readInt();
        loadingImage = im.readInt();
        isImageBroken = im.readInt() == 1;
    }


    public static final android.os.Parcelable.Creator<com.amaze.filemanager.adapters.data.IconDataParcelable> CREATOR = new android.os.Parcelable.Creator<com.amaze.filemanager.adapters.data.IconDataParcelable>() {
        public com.amaze.filemanager.adapters.data.IconDataParcelable createFromParcel(android.os.Parcel in) {
            return new com.amaze.filemanager.adapters.data.IconDataParcelable(in);
        }


        public com.amaze.filemanager.adapters.data.IconDataParcelable[] newArray(int size) {
            return new com.amaze.filemanager.adapters.data.IconDataParcelable[size];
        }

    };

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
