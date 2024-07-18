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
import it.feio.android.omninotes.commons.models.BaseCategory;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Category extends it.feio.android.omninotes.commons.models.BaseCategory implements android.os.Parcelable {
    static final int MUID_STATIC = getMUID();
    private Category(android.os.Parcel in) {
        setId(in.readLong());
        setName(in.readString());
        setDescription(in.readString());
        setColor(in.readString());
    }


    public Category() {
        super();
    }


    public Category(it.feio.android.omninotes.commons.models.BaseCategory category) {
        super(category.getId(), category.getName(), category.getDescription(), category.getColor());
    }


    public Category(java.lang.Long id, java.lang.String title, java.lang.String description, java.lang.String color) {
        super(id, title, description, color);
    }


    public Category(java.lang.Long id, java.lang.String title, java.lang.String description, java.lang.String color, int count) {
        super(id, title, description, color, count);
    }


    @java.lang.Override
    public int describeContents() {
        return 0;
    }


    @java.lang.Override
    public void writeToParcel(android.os.Parcel parcel, int flags) {
        parcel.writeLong(getId());
        parcel.writeString(getName());
        parcel.writeString(getDescription());
        parcel.writeString(getColor());
    }


    @java.lang.Override
    public java.lang.String toString() {
        return getName();
    }


    /* Parcelable interface must also have a static field called CREATOR, which is an object implementing the
    Parcelable.Creator interface. Used to un-marshal or de-serialize object from Parcel.
     */
    public static final android.os.Parcelable.Creator<it.feio.android.omninotes.models.Category> CREATOR = new android.os.Parcelable.Creator<it.feio.android.omninotes.models.Category>() {
        public it.feio.android.omninotes.models.Category createFromParcel(android.os.Parcel in) {
            return new it.feio.android.omninotes.models.Category(in);
        }


        public it.feio.android.omninotes.models.Category[] newArray(int size) {
            return new it.feio.android.omninotes.models.Category[size];
        }

    };

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
