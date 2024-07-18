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
import it.feio.android.omninotes.async.bus.NotesLoadedEvent;
import it.feio.android.omninotes.exceptions.NotesLoadingException;
import it.feio.android.omninotes.models.Note;
import java.util.ArrayList;
import java.lang.reflect.Method;
import de.greenrobot.event.EventBus;
import android.os.AsyncTask;
import java.util.List;
import it.feio.android.omninotes.db.DbHelper;
import java.lang.reflect.InvocationTargetException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteLoaderTask extends android.os.AsyncTask<java.lang.Object, java.lang.Void, java.util.List<it.feio.android.omninotes.models.Note>> {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String ERROR_RETRIEVING_NOTES = "Error retrieving notes";

    private static it.feio.android.omninotes.async.notes.NoteLoaderTask instance;

    private NoteLoaderTask() {
    }


    public static it.feio.android.omninotes.async.notes.NoteLoaderTask getInstance() {
        if (it.feio.android.omninotes.async.notes.NoteLoaderTask.instance != null) {
            if ((it.feio.android.omninotes.async.notes.NoteLoaderTask.instance.getStatus() == android.os.AsyncTask.Status.RUNNING) && (!it.feio.android.omninotes.async.notes.NoteLoaderTask.instance.isCancelled())) {
                it.feio.android.omninotes.async.notes.NoteLoaderTask.instance.cancel(true);
            } else if (it.feio.android.omninotes.async.notes.NoteLoaderTask.instance.getStatus() == android.os.AsyncTask.Status.PENDING) {
                return it.feio.android.omninotes.async.notes.NoteLoaderTask.instance;
            }
        }
        it.feio.android.omninotes.async.notes.NoteLoaderTask.instance = new it.feio.android.omninotes.async.notes.NoteLoaderTask();
        return it.feio.android.omninotes.async.notes.NoteLoaderTask.instance;
    }


    @java.lang.Override
    protected java.util.List<it.feio.android.omninotes.models.Note> doInBackground(java.lang.Object... params) {
        java.lang.String methodName;
        methodName = params[0].toString();
        it.feio.android.omninotes.db.DbHelper db;
        db = it.feio.android.omninotes.db.DbHelper.getInstance();
        if ((params.length < 2) || (params[1] == null)) {
            try {
                java.lang.reflect.Method method;
                method = db.getClass().getDeclaredMethod(methodName);
                return ((java.util.List<it.feio.android.omninotes.models.Note>) (method.invoke(db)));
            } catch (java.lang.NoSuchMethodException e) {
                return new java.util.ArrayList<>();
            } catch (java.lang.IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                throw new it.feio.android.omninotes.exceptions.NotesLoadingException(it.feio.android.omninotes.async.notes.NoteLoaderTask.ERROR_RETRIEVING_NOTES, e);
            }
        } else {
            java.lang.Object methodArgs;
            methodArgs = params[1];
            java.lang.Class[] paramClass;
            paramClass = new java.lang.Class[]{ methodArgs.getClass() };
            try {
                java.lang.reflect.Method method;
                method = db.getClass().getDeclaredMethod(methodName, paramClass);
                return ((java.util.List<it.feio.android.omninotes.models.Note>) (method.invoke(db, paramClass[0].cast(methodArgs))));
            } catch (java.lang.Exception e) {
                throw new it.feio.android.omninotes.exceptions.NotesLoadingException(it.feio.android.omninotes.async.notes.NoteLoaderTask.ERROR_RETRIEVING_NOTES, e);
            }
        }
    }


    @java.lang.Override
    protected void onPostExecute(java.util.List<it.feio.android.omninotes.models.Note> notes) {
        super.onPostExecute(notes);
        de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.NotesLoadedEvent(notes));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
