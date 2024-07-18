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
package it.feio.android.omninotes;
import android.app.Activity;
import android.os.Bundle;
import it.feio.android.omninotes.models.Stats;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.TextView;
import it.feio.android.omninotes.db.DbHelper;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class StatsActivity extends android.app.Activity {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // StatsActivity_0_LengthyGUICreationOperatorMutator
            case 136: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    setContentView(it.feio.android.omninotes.R.layout.activity_stats);
    initData();
}


@android.annotation.SuppressLint("NewApi")
private void initData() {
    class StatsTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, it.feio.android.omninotes.models.Stats> {
        @java.lang.Override
        protected it.feio.android.omninotes.models.Stats doInBackground(java.lang.Void... params) {
            return it.feio.android.omninotes.db.DbHelper.getInstance().getStats();
        }


        @java.lang.Override
        protected void onPostExecute(it.feio.android.omninotes.models.Stats result) {
            populateViews(result);
        }

    }
    new StatsTask().executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
}


private void populateViews(it.feio.android.omninotes.models.Stats mStats) {
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_notes_total))).setText(java.lang.String.valueOf(mStats.getNotesTotalNumber()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_notes_active))).setText(java.lang.String.valueOf(mStats.getNotesActive()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_notes_archived))).setText(java.lang.String.valueOf(mStats.getNotesArchived()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_notes_trashed))).setText(java.lang.String.valueOf(mStats.getNotesTrashed()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_reminders))).setText(java.lang.String.valueOf(mStats.getReminders()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_reminders_futures))).setText(java.lang.String.valueOf(mStats.getRemindersFutures()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_checklists))).setText(java.lang.String.valueOf(mStats.getNotesChecklist()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_masked))).setText(java.lang.String.valueOf(mStats.getNotesMasked()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_categories))).setText(java.lang.String.valueOf(mStats.getCategories()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_tags))).setText(java.lang.String.valueOf(mStats.getTags()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_attachments))).setText(java.lang.String.valueOf(mStats.getAttachments()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_attachments_images))).setText(java.lang.String.valueOf(mStats.getImages()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_attachments_videos))).setText(java.lang.String.valueOf(mStats.getVideos()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_attachments_audiorecordings))).setText(java.lang.String.valueOf(mStats.getAudioRecordings()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_attachments_sketches))).setText(java.lang.String.valueOf(mStats.getSketches()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_attachments_files))).setText(java.lang.String.valueOf(mStats.getFiles()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_locations))).setText(java.lang.String.valueOf(mStats.getLocation()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_words))).setText(java.lang.String.valueOf(mStats.getWords()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_words_max))).setText(java.lang.String.valueOf(mStats.getWordsMax()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_words_avg))).setText(java.lang.String.valueOf(mStats.getWordsAvg()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_chars))).setText(java.lang.String.valueOf(mStats.getChars()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_chars_max))).setText(java.lang.String.valueOf(mStats.getCharsMax()));
    ((android.widget.TextView) (findViewById(it.feio.android.omninotes.R.id.stat_chars_avg))).setText(java.lang.String.valueOf(mStats.getCharsAvg()));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
