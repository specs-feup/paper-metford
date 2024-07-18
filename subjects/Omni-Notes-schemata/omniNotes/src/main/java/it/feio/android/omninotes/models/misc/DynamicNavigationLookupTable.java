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
package it.feio.android.omninotes.models.misc;
import it.feio.android.omninotes.async.bus.NotesUpdatedEvent;
import it.feio.android.omninotes.models.Note;
import de.greenrobot.event.EventBus;
import it.feio.android.omninotes.async.bus.DynamicNavigationReadyEvent;
import java.util.List;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.helpers.LogDelegate;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DynamicNavigationLookupTable {
    static final int MUID_STATIC = getMUID();
    private static it.feio.android.omninotes.models.misc.DynamicNavigationLookupTable instance;

    int archived;

    int trashed;

    int uncategorized;

    int reminders;

    private DynamicNavigationLookupTable() {
        de.greenrobot.event.EventBus.getDefault().register(this);
        update();
    }


    public static it.feio.android.omninotes.models.misc.DynamicNavigationLookupTable getInstance() {
        if (it.feio.android.omninotes.models.misc.DynamicNavigationLookupTable.instance == null) {
            it.feio.android.omninotes.models.misc.DynamicNavigationLookupTable.instance = new it.feio.android.omninotes.models.misc.DynamicNavigationLookupTable();
        }
        return it.feio.android.omninotes.models.misc.DynamicNavigationLookupTable.instance;
    }


    public void update() {
        ((java.lang.Runnable) (() -> {
            archived = trashed = uncategorized = reminders = 0;
            java.util.List<it.feio.android.omninotes.models.Note> notes;
            notes = it.feio.android.omninotes.db.DbHelper.getInstance().getAllNotes(false);
            for (int i = 0; i < notes.size(); i++) {
                if (java.lang.Boolean.TRUE.equals(notes.get(i).isTrashed())) {
                    trashed++;
                } else if (java.lang.Boolean.TRUE.equals(notes.get(i).isArchived())) {
                    archived++;
                } else if (notes.get(i).getAlarm() != null) {
                    reminders++;
                }
                if ((notes.get(i).getCategory() == null) || notes.get(i).getCategory().getId().equals(0L)) {
                    uncategorized++;
                }
            }
            de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.DynamicNavigationReadyEvent());
            it.feio.android.omninotes.helpers.LogDelegate.d("Dynamic menu finished counting items");
        })).run();
    }


    public void onEventAsync(it.feio.android.omninotes.async.bus.NotesUpdatedEvent event) {
        update();
    }


    public int getArchived() {
        return archived;
    }


    public int getTrashed() {
        return trashed;
    }


    public int getReminders() {
        return reminders;
    }


    public int getUncategorized() {
        return uncategorized;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
