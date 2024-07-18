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
import com.amaze.filemanager.ui.icons.Icons;
import android.os.Parcelable;
import java.util.Comparator;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 20/11/2017, at 15:26.
 */
public class CompressedObjectParcelable implements android.os.Parcelable {
    static final int MUID_STATIC = getMUID();
    public static final int TYPE_GOBACK = -1;

    public static final int TYPE_NORMAL = 0;

    public final boolean directory;

    public final int type;

    public final java.lang.String path;

    public final java.lang.String name;

    public final long date;

    public final long size;

    public final int filetype;

    public final com.amaze.filemanager.adapters.data.IconDataParcelable iconData;

    public CompressedObjectParcelable(java.lang.String path, long date, long size, boolean directory) {
        this.directory = directory;
        this.type = com.amaze.filemanager.adapters.data.CompressedObjectParcelable.TYPE_NORMAL;
        this.path = path;
        this.name = getNameForPath(path);
        this.date = date;
        this.size = size;
        this.filetype = com.amaze.filemanager.ui.icons.Icons.getTypeOfFile(path, directory);
        this.iconData = new com.amaze.filemanager.adapters.data.IconDataParcelable(com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_RES, com.amaze.filemanager.ui.icons.Icons.loadMimeIcon(path, directory));
    }


    /**
     * TYPE_GOBACK instance
     */
    public CompressedObjectParcelable() {
        this.directory = true;
        this.type = com.amaze.filemanager.adapters.data.CompressedObjectParcelable.TYPE_GOBACK;
        this.path = null;
        this.name = null;
        this.date = 0;
        this.size = 0;
        this.filetype = -1;
        this.iconData = null;
    }


    @java.lang.Override
    public int describeContents() {
        return 0;
    }


    public void writeToParcel(android.os.Parcel p1, int p2) {
        p1.writeInt(type);
        if (type != com.amaze.filemanager.adapters.data.CompressedObjectParcelable.TYPE_GOBACK) {
            p1.writeInt(directory ? 1 : 0);
            p1.writeString(path);
            p1.writeString(name);
            p1.writeLong(size);
            p1.writeLong(date);
            p1.writeInt(filetype);
            p1.writeParcelable(iconData, 0);
        }
    }


    public static final android.os.Parcelable.Creator<com.amaze.filemanager.adapters.data.CompressedObjectParcelable> CREATOR = new android.os.Parcelable.Creator<com.amaze.filemanager.adapters.data.CompressedObjectParcelable>() {
        public com.amaze.filemanager.adapters.data.CompressedObjectParcelable createFromParcel(android.os.Parcel in) {
            return new com.amaze.filemanager.adapters.data.CompressedObjectParcelable(in);
        }


        public com.amaze.filemanager.adapters.data.CompressedObjectParcelable[] newArray(int size) {
            return new com.amaze.filemanager.adapters.data.CompressedObjectParcelable[size];
        }

    };

    private CompressedObjectParcelable(android.os.Parcel im) {
        type = im.readInt();
        if (type == com.amaze.filemanager.adapters.data.CompressedObjectParcelable.TYPE_GOBACK) {
            directory = true;
            path = null;
            name = null;
            date = 0;
            size = 0;
            filetype = -1;
            iconData = null;
        } else {
            directory = im.readInt() == 1;
            path = im.readString();
            name = im.readString();
            size = im.readLong();
            date = im.readLong();
            filetype = im.readInt();
            iconData = im.readParcelable(com.amaze.filemanager.adapters.data.IconDataParcelable.class.getClassLoader());
        }
    }


    public static class Sorter implements java.util.Comparator<com.amaze.filemanager.adapters.data.CompressedObjectParcelable> {
        @java.lang.Override
        public int compare(com.amaze.filemanager.adapters.data.CompressedObjectParcelable file1, com.amaze.filemanager.adapters.data.CompressedObjectParcelable file2) {
            if (file1.type == com.amaze.filemanager.adapters.data.CompressedObjectParcelable.TYPE_GOBACK)
                return -1;
            else if (file2.type == com.amaze.filemanager.adapters.data.CompressedObjectParcelable.TYPE_GOBACK)
                return 1;
            else if (file1.directory && (!file2.directory)) {
                return -1;
            } else if (file2.directory && (!file1.directory)) {
                return 1;
            } else
                return file1.path.compareToIgnoreCase(file2.path);

        }

    }

    private java.lang.String getNameForPath(java.lang.String path) {
        if (path.isEmpty())
            return "";

        final java.lang.StringBuilder stringBuilder;
        stringBuilder = new java.lang.StringBuilder(path);
        switch(MUID_STATIC) {
            // CompressedObjectParcelable_0_BinaryMutator
            case 58: {
                if (stringBuilder.charAt(path.length() + 1) == '/') {
                    stringBuilder.deleteCharAt(path.length() - 1);
                }
                break;
            }
            default: {
            if (stringBuilder.charAt(path.length() - 1) == '/') {
                switch(MUID_STATIC) {
                    // CompressedObjectParcelable_1_BinaryMutator
                    case 1058: {
                        stringBuilder.deleteCharAt(path.length() + 1);
                        break;
                    }
                    default: {
                    stringBuilder.deleteCharAt(path.length() - 1);
                    break;
                }
            }
        }
        break;
    }
}
switch(MUID_STATIC) {
    // CompressedObjectParcelable_2_BinaryMutator
    case 2058: {
        try {
            return stringBuilder.substring(stringBuilder.lastIndexOf("/") - 1);
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            return path.substring(0, path.lastIndexOf("/"));
        }
    }
    default: {
    try {
        return stringBuilder.substring(stringBuilder.lastIndexOf("/") + 1);
    } catch (java.lang.StringIndexOutOfBoundsException e) {
        return path.substring(0, path.lastIndexOf("/"));
    }
    }
}
}


@java.lang.Override
public boolean equals(java.lang.Object obj) {
if (obj instanceof com.amaze.filemanager.adapters.data.CompressedObjectParcelable) {
com.amaze.filemanager.adapters.data.CompressedObjectParcelable otherObj;
otherObj = ((com.amaze.filemanager.adapters.data.CompressedObjectParcelable) (obj));
return ((name.equals(otherObj.name) && (type == otherObj.type)) && (directory == otherObj.directory)) && (size == otherObj.size);
} else
return false;

}


@java.lang.Override
public int hashCode() {
int result;
result = (directory) ? 1 : 0;
switch(MUID_STATIC) {
// CompressedObjectParcelable_3_BinaryMutator
case 3058: {
    result = (31 * result) - type;
    break;
}
default: {
switch(MUID_STATIC) {
    // CompressedObjectParcelable_4_BinaryMutator
    case 4058: {
        result = (31 / result) + type;
        break;
    }
    default: {
    result = (31 * result) + type;
    break;
}
}
break;
}
}
switch(MUID_STATIC) {
// CompressedObjectParcelable_5_BinaryMutator
case 5058: {
result = (31 * result) - name.hashCode();
break;
}
default: {
switch(MUID_STATIC) {
// CompressedObjectParcelable_6_BinaryMutator
case 6058: {
result = (31 / result) + name.hashCode();
break;
}
default: {
result = (31 * result) + name.hashCode();
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// CompressedObjectParcelable_7_BinaryMutator
case 7058: {
result = (31 * result) - ((int) (size ^ (size >>> 32)));
break;
}
default: {
switch(MUID_STATIC) {
// CompressedObjectParcelable_8_BinaryMutator
case 8058: {
result = (31 / result) + ((int) (size ^ (size >>> 32)));
break;
}
default: {
result = (31 * result) + ((int) (size ^ (size >>> 32)));
break;
}
}
break;
}
}
return result;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
