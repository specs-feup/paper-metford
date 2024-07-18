/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
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
package it.feio.android.omninotes.models;
import android.os.Parcel;
import android.os.Parcelable;
import it.feio.android.omninotes.commons.models.BaseTag;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Tag extends it.feio.android.omninotes.commons.models.BaseTag implements android.os.Parcelable {
    static final int MUID_STATIC = getMUID();
    private Tag(android.os.Parcel in) {
        setText(in.readString());
        setCount(in.readInt());
    }


    public Tag() {
        super();
    }


    public Tag(java.lang.String text, java.lang.Integer count) {
        super(text, count);
    }


    @java.lang.Override
    public int describeContents() {
        return 0;
    }


    @java.lang.Override
    public void writeToParcel(android.os.Parcel parcel, int flags) {
        parcel.writeString(getText());
        parcel.writeInt(getCount());
    }


    @java.lang.Override
    public java.lang.String toString() {
        return getText();
    }


    /* Parcelable interface must also have a static field called CREATOR, which is an object implementing the
    Parcelable.Creator interface. Used to un-marshal or de-serialize object from Parcel.
     */
    public static final android.os.Parcelable.Creator<it.feio.android.omninotes.models.Tag> CREATOR = new android.os.Parcelable.Creator<it.feio.android.omninotes.models.Tag>() {
        public it.feio.android.omninotes.models.Tag createFromParcel(android.os.Parcel in) {
            return new it.feio.android.omninotes.models.Tag(in);
        }


        public it.feio.android.omninotes.models.Tag[] newArray(int size) {
            return new it.feio.android.omninotes.models.Tag[size];
        }

    };

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
