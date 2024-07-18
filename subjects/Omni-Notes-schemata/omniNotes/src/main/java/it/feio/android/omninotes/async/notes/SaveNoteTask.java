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
package it.feio.android.omninotes.async.notes;
import it.feio.android.omninotes.OmniNotes;
import it.feio.android.omninotes.utils.ReminderHelper;
import it.feio.android.omninotes.models.Note;
import it.feio.android.omninotes.utils.StorageHelper;
import android.os.AsyncTask;
import it.feio.android.omninotes.utils.date.DateUtils;
import java.util.List;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.helpers.LogDelegate;
import it.feio.android.omninotes.models.Attachment;
import it.feio.android.omninotes.models.listeners.OnNoteSaved;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SaveNoteTask extends android.os.AsyncTask<it.feio.android.omninotes.models.Note, java.lang.Void, it.feio.android.omninotes.models.Note> {
    static final int MUID_STATIC = getMUID();
    private android.content.Context context;

    private boolean updateLastModification = true;

    private it.feio.android.omninotes.models.listeners.OnNoteSaved mOnNoteSaved;

    public SaveNoteTask(boolean updateLastModification) {
        this(null, updateLastModification);
    }


    public SaveNoteTask(it.feio.android.omninotes.models.listeners.OnNoteSaved mOnNoteSaved, boolean updateLastModification) {
        super();
        this.context = it.feio.android.omninotes.OmniNotes.getAppContext();
        this.mOnNoteSaved = mOnNoteSaved;
        this.updateLastModification = updateLastModification;
    }


    @java.lang.Override
    protected it.feio.android.omninotes.models.Note doInBackground(it.feio.android.omninotes.models.Note... params) {
        it.feio.android.omninotes.models.Note note;
        note = params[0];
        purgeRemovedAttachments(note);
        boolean reminderMustBeSet;
        reminderMustBeSet = it.feio.android.omninotes.utils.date.DateUtils.isFuture(note.getAlarm());
        if (reminderMustBeSet) {
            note.setReminderFired(false);
        }
        note = it.feio.android.omninotes.db.DbHelper.getInstance().updateNote(note, updateLastModification);
        if (reminderMustBeSet) {
            it.feio.android.omninotes.utils.ReminderHelper.addReminder(context, note);
        }
        return note;
    }


    private void purgeRemovedAttachments(it.feio.android.omninotes.models.Note note) {
        java.util.List<it.feio.android.omninotes.models.Attachment> deletedAttachments;
        deletedAttachments = note.getAttachmentsListOld();
        for (it.feio.android.omninotes.models.Attachment attachment : note.getAttachmentsList()) {
            if (attachment.getId() != null) {
                // Workaround to prevent deleting attachments if instance is changed (app restart)
                if (deletedAttachments.indexOf(attachment) == (-1)) {
                    attachment = getFixedAttachmentInstance(deletedAttachments, attachment);
                }
                deletedAttachments.remove(attachment);
            }
        }
        // Remove from database deleted attachments
        for (it.feio.android.omninotes.models.Attachment deletedAttachment : deletedAttachments) {
            it.feio.android.omninotes.utils.StorageHelper.delete(context, deletedAttachment.getUri().getPath());
            it.feio.android.omninotes.helpers.LogDelegate.d("Removed attachment " + deletedAttachment.getUri());
        }
    }


    private it.feio.android.omninotes.models.Attachment getFixedAttachmentInstance(java.util.List<it.feio.android.omninotes.models.Attachment> deletedAttachments, it.feio.android.omninotes.models.Attachment attachment) {
        for (it.feio.android.omninotes.models.Attachment deletedAttachment : deletedAttachments) {
            if (deletedAttachment.getId().equals(attachment.getId())) {
                return deletedAttachment;
            }
        }
        return attachment;
    }


    @java.lang.Override
    protected void onPostExecute(it.feio.android.omninotes.models.Note note) {
        super.onPostExecute(note);
        if (this.mOnNoteSaved != null) {
            mOnNoteSaved.onNoteSaved(note);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
