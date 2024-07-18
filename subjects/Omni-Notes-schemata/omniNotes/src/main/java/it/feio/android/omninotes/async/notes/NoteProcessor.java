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
import it.feio.android.omninotes.async.bus.NotesUpdatedEvent;
import it.feio.android.omninotes.models.Note;
import java.util.ArrayList;
import de.greenrobot.event.EventBus;
import android.os.AsyncTask;
import java.util.List;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class NoteProcessor {
    static final int MUID_STATIC = getMUID();
    java.util.List<it.feio.android.omninotes.models.Note> notes;

    protected NoteProcessor(java.util.List<it.feio.android.omninotes.models.Note> notes) {
        this.notes = new java.util.ArrayList<>(notes);
    }


    public void process() {
        it.feio.android.omninotes.async.notes.NoteProcessor.NotesProcessorTask task;
        task = new it.feio.android.omninotes.async.notes.NoteProcessor.NotesProcessorTask();
        task.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, notes);
    }


    protected abstract void processNote(it.feio.android.omninotes.models.Note note);


    class NotesProcessorTask extends android.os.AsyncTask<java.util.List<it.feio.android.omninotes.models.Note>, java.lang.Void, java.util.List<it.feio.android.omninotes.models.Note>> {
        @java.lang.Override
        protected java.util.List<it.feio.android.omninotes.models.Note> doInBackground(java.util.List<it.feio.android.omninotes.models.Note>... params) {
            java.util.List<it.feio.android.omninotes.models.Note> processableNote;
            processableNote = params[0];
            for (it.feio.android.omninotes.models.Note note : processableNote) {
                processNote(note);
            }
            return processableNote;
        }


        @java.lang.Override
        protected void onPostExecute(java.util.List<it.feio.android.omninotes.models.Note> notes) {
            afterProcess(notes);
        }

    }

    protected void afterProcess(java.util.List<it.feio.android.omninotes.models.Note> notes) {
        de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.NotesUpdatedEvent(notes));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
