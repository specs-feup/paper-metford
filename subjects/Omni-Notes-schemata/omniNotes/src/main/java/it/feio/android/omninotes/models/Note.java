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
import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;
import it.feio.android.omninotes.commons.models.BaseCategory;
import it.feio.android.omninotes.commons.models.BaseNote;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Note extends it.feio.android.omninotes.commons.models.BaseNote implements android.os.Parcelable {
    static final int MUID_STATIC = getMUID();
    /* Parcelable interface must also have a static field called CREATOR, which is an object implementing the
    Parcelable.Creator interface. Used to un-marshal or de-serialize object from Parcel.
     */
    public static final android.os.Parcelable.Creator<it.feio.android.omninotes.models.Note> CREATOR = new android.os.Parcelable.Creator<it.feio.android.omninotes.models.Note>() {
        public it.feio.android.omninotes.models.Note createFromParcel(android.os.Parcel in) {
            return new it.feio.android.omninotes.models.Note(in);
        }


        public it.feio.android.omninotes.models.Note[] newArray(int size) {
            return new it.feio.android.omninotes.models.Note[size];
        }

    };

    // Not saved in DB
    private boolean passwordChecked = false;

    public Note() {
        super();
    }


    public Note(java.lang.Long creation, java.lang.Long lastModification, java.lang.String title, java.lang.String content, java.lang.Integer archived, java.lang.Integer trashed, java.lang.String alarm, java.lang.String recurrenceRule, java.lang.Integer reminderFired, java.lang.String latitude, java.lang.String longitude, it.feio.android.omninotes.models.Category category, java.lang.Integer locked, java.lang.Integer checklist) {
        super(creation, lastModification, title, content, archived, trashed, alarm, reminderFired, recurrenceRule, latitude, longitude, category, locked, checklist);
    }


    public Note(it.feio.android.omninotes.models.Note note) {
        super(note);
        setPasswordChecked(note.isPasswordChecked());
    }


    private Note(android.os.Parcel in) {
        setCreation(in.readString());
        setLastModification(in.readString());
        setTitle(in.readString());
        setContent(in.readString());
        setArchived(in.readInt());
        setTrashed(in.readInt());
        setAlarm(in.readString());
        setReminderFired(in.readInt());
        setRecurrenceRule(in.readString());
        setLatitude(in.readString());
        setLongitude(in.readString());
        setAddress(in.readString());
        super.setCategory(in.readParcelable(it.feio.android.omninotes.models.Category.class.getClassLoader()));
        setLocked(in.readInt());
        setChecklist(in.readInt());
        in.readList(getAttachmentsList(), it.feio.android.omninotes.models.Attachment.class.getClassLoader());
    }


    public java.util.List<it.feio.android.omninotes.models.Attachment> getAttachmentsList() {
        // List<Attachment> list = new ArrayList<>();
        // for (it.feio.android.omninotes.commons.models.Attachment attachment : super.getAttachmentsList()) {
        // if (attachment.getClass().equals(Attachment.class)) {
        // list.add((Attachment) attachment);
        // } else {
        // list.add(new Attachment(attachment));
        // }
        // }
        // return list;
        // FIXME This fixes https://github.com/federicoiosue/Omni-Notes/issues/199 but could introduce other issues
        return ((java.util.List<it.feio.android.omninotes.models.Attachment>) (super.getAttachmentsList()));
    }


    public void setAttachmentsList(java.util.ArrayList<it.feio.android.omninotes.models.Attachment> attachmentsList) {
        super.setAttachmentsList(attachmentsList);
    }


    public void addAttachment(it.feio.android.omninotes.models.Attachment attachment) {
        java.util.List<it.feio.android.omninotes.models.Attachment> attachmentsList;
        attachmentsList = ((java.util.List<it.feio.android.omninotes.models.Attachment>) (super.getAttachmentsList()));
        attachmentsList.add(attachment);
        setAttachmentsList(attachmentsList);
    }


    public void removeAttachment(it.feio.android.omninotes.models.Attachment attachment) {
        java.util.List<it.feio.android.omninotes.models.Attachment> attachmentsList;
        attachmentsList = ((java.util.List<it.feio.android.omninotes.models.Attachment>) (super.getAttachmentsList()));
        attachmentsList.remove(attachment);
        setAttachmentsList(attachmentsList);
    }


    public java.util.List<it.feio.android.omninotes.models.Attachment> getAttachmentsListOld() {
        return ((java.util.List<it.feio.android.omninotes.models.Attachment>) (super.getAttachmentsListOld()));
    }


    public void setAttachmentsListOld(java.util.ArrayList<it.feio.android.omninotes.models.Attachment> attachmentsListOld) {
        super.setAttachmentsListOld(attachmentsListOld);
    }


    public boolean isPasswordChecked() {
        return passwordChecked;
    }


    public void setPasswordChecked(boolean passwordChecked) {
        this.passwordChecked = passwordChecked;
    }


    @java.lang.Override
    public it.feio.android.omninotes.models.Category getCategory() {
        try {
            return ((it.feio.android.omninotes.models.Category) (super.getCategory()));
        } catch (java.lang.ClassCastException e) {
            return new it.feio.android.omninotes.models.Category(super.getCategory());
        }
    }


    public void setCategory(it.feio.android.omninotes.models.Category category) {
        if ((category != null) && category.getClass().equals(it.feio.android.omninotes.commons.models.BaseCategory.class)) {
            setCategory(new it.feio.android.omninotes.models.Category(category));
        }
        super.setCategory(category);
    }


    @java.lang.Override
    public void buildFromJson(java.lang.String jsonNote) {
        super.buildFromJson(jsonNote);
        java.util.List<it.feio.android.omninotes.models.Attachment> attachments;
        attachments = new java.util.ArrayList<>();
        for (it.feio.android.omninotes.commons.models.BaseAttachment attachment : getAttachmentsList()) {
            attachments.add(new it.feio.android.omninotes.models.Attachment(attachment));
        }
        setAttachmentsList(attachments);
    }


    @java.lang.Override
    public int describeContents() {
        return 0;
    }


    @java.lang.Override
    public void writeToParcel(android.os.Parcel parcel, int flags) {
        parcel.writeString(java.lang.String.valueOf(getCreation()));
        parcel.writeString(java.lang.String.valueOf(getLastModification()));
        parcel.writeString(getTitle());
        parcel.writeString(getContent());
        parcel.writeInt(isArchived() ? 1 : 0);
        parcel.writeInt(isTrashed() ? 1 : 0);
        parcel.writeString(getAlarm());
        parcel.writeInt(isReminderFired() ? 1 : 0);
        parcel.writeString(getRecurrenceRule());
        parcel.writeString(java.lang.String.valueOf(getLatitude()));
        parcel.writeString(java.lang.String.valueOf(getLongitude()));
        parcel.writeString(getAddress());
        parcel.writeParcelable(getCategory(), 0);
        parcel.writeInt(isLocked() ? 1 : 0);
        parcel.writeInt(isChecklist() ? 1 : 0);
        parcel.writeList(getAttachmentsList());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
