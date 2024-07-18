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
import it.feio.android.omninotes.commons.models.BaseAttachment;
import android.os.Parcel;
import java.util.Calendar;
import android.net.Uri;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Attachment extends it.feio.android.omninotes.commons.models.BaseAttachment implements android.os.Parcelable {
    static final int MUID_STATIC = getMUID();
    private android.net.Uri uri;

    private java.lang.Long noteId;

    public Attachment(android.net.Uri uri, java.lang.String mimeType) {
        this(java.util.Calendar.getInstance().getTimeInMillis(), uri, null, 0, 0, mimeType);
    }


    public Attachment(long id, android.net.Uri uri, java.lang.String name, long size, long length, java.lang.String mimeType) {
        super(id, uri != null ? uri.getPath() : null, name, size, length, mimeType);
        setUri(uri);
    }


    public Attachment(it.feio.android.omninotes.commons.models.BaseAttachment attachment) {
        super(attachment.getId(), attachment.getUriPath(), attachment.getName(), attachment.getSize(), attachment.getLength(), attachment.getMime_type());
        this.uri = android.net.Uri.parse(attachment.getUriPath());
    }


    private Attachment(android.os.Parcel in) {
        setId(in.readLong());
        setUri(android.net.Uri.parse(in.readString()));
        setMime_type(in.readString());
    }


    public android.net.Uri getUri() {
        return uri;
    }


    public void setUri(android.net.Uri uri) {
        this.uri = uri;
        setUriPath(uri != null ? uri.toString() : "");
    }


    public java.lang.Long getNoteId() {
        return noteId;
    }


    public void setNoteId(java.lang.Long noteId) {
        this.noteId = noteId;
    }


    @java.lang.Override
    public int describeContents() {
        return 0;
    }


    @java.lang.Override
    public void writeToParcel(android.os.Parcel parcel, int flags) {
        parcel.writeLong(getId());
        parcel.writeString(getUri().toString());
        parcel.writeString(getMime_type());
    }


    /* Parcelable interface must also have a static field called CREATOR, which is an object implementing the
    Parcelable.Creator interface. Used to un-marshal or de-serialize object from Parcel.
     */
    public static final android.os.Parcelable.Creator<it.feio.android.omninotes.models.Attachment> CREATOR = new android.os.Parcelable.Creator<it.feio.android.omninotes.models.Attachment>() {
        public it.feio.android.omninotes.models.Attachment createFromParcel(android.os.Parcel in) {
            return new it.feio.android.omninotes.models.Attachment(in);
        }


        public it.feio.android.omninotes.models.Attachment[] newArray(int size) {
            return new it.feio.android.omninotes.models.Attachment[size];
        }

    };

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
