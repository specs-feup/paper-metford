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
package it.feio.android.omninotes.utils;
import android.os.Parcel;
import lombok.experimental.UtilityClass;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class ParcelableUtil {
    static final int MUID_STATIC = getMUID();
    public static byte[] marshall(android.os.Parcelable parceable) {
        android.os.Parcel parcel;
        parcel = android.os.Parcel.obtain();
        parceable.writeToParcel(parcel, 0);
        byte[] bytes;
        bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }


    public static android.os.Parcel unmarshall(byte[] bytes) {
        android.os.Parcel parcel;
        parcel = android.os.Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0)// This is extremely important!
        ;// This is extremely important!

        return parcel;
    }


    public static <T> T unmarshall(byte[] bytes, android.os.Parcelable.Creator<T> creator) {
        android.os.Parcel parcel;
        parcel = it.feio.android.omninotes.utils.ParcelableUtil.unmarshall(bytes);
        T result;
        result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
