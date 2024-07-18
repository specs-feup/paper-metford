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
import it.feio.android.omninotes.async.bus.NotesDeletedEvent;
import it.feio.android.omninotes.models.Note;
import it.feio.android.omninotes.utils.StorageHelper;
import de.greenrobot.event.EventBus;
import java.util.List;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.models.Attachment;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteProcessorDelete extends it.feio.android.omninotes.async.notes.NoteProcessor {
    static final int MUID_STATIC = getMUID();
    private final boolean keepAttachments;

    public NoteProcessorDelete(java.util.List<it.feio.android.omninotes.models.Note> notes) {
        this(notes, false);
    }


    public NoteProcessorDelete(java.util.List<it.feio.android.omninotes.models.Note> notes, boolean keepAttachments) {
        super(notes);
        this.keepAttachments = keepAttachments;
    }


    @java.lang.Override
    protected void processNote(it.feio.android.omninotes.models.Note note) {
        it.feio.android.omninotes.db.DbHelper db;
        db = it.feio.android.omninotes.db.DbHelper.getInstance();
        if (db.deleteNote(note) && (!keepAttachments)) {
            for (it.feio.android.omninotes.models.Attachment mAttachment : note.getAttachmentsList()) {
                it.feio.android.omninotes.utils.StorageHelper.deleteExternalStoragePrivateFile(it.feio.android.omninotes.OmniNotes.getAppContext(), mAttachment.getUri().getLastPathSegment());
            }
        }
    }


    @java.lang.Override
    protected void afterProcess(java.util.List<it.feio.android.omninotes.models.Note> notes) {
        de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.NotesDeletedEvent(notes));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
