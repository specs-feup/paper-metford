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
package it.feio.android.omninotes.async;
import androidx.core.app.JobIntentService;
import it.feio.android.omninotes.OmniNotes;
import it.feio.android.omninotes.utils.ReminderHelper;
import androidx.annotation.NonNull;
import it.feio.android.omninotes.models.Note;
import android.os.Build;
import it.feio.android.omninotes.BaseActivity;
import android.content.Intent;
import java.util.List;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.helpers.LogDelegate;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Verify version code and add wake lock in manifest is important to avoid crash
 */
public class AlarmRestoreOnRebootService extends androidx.core.app.JobIntentService {
    static final int MUID_STATIC = getMUID();
    public static final int JOB_ID = 0x1;

    public static void enqueueWork(android.content.Context context, android.content.Intent work) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            androidx.core.app.JobIntentService.enqueueWork(context, it.feio.android.omninotes.async.AlarmRestoreOnRebootService.class, it.feio.android.omninotes.async.AlarmRestoreOnRebootService.JOB_ID, work);
        } else {
            android.content.Intent jobIntent;
            switch(MUID_STATIC) {
                // AlarmRestoreOnRebootService_0_NullIntentOperatorMutator
                case 27: {
                    jobIntent = null;
                    break;
                }
                // AlarmRestoreOnRebootService_1_InvalidKeyIntentOperatorMutator
                case 1027: {
                    jobIntent = new android.content.Intent((Context) null, it.feio.android.omninotes.async.AlarmRestoreOnRebootService.class);
                    break;
                }
                // AlarmRestoreOnRebootService_2_RandomActionIntentDefinitionOperatorMutator
                case 2027: {
                    jobIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                jobIntent = new android.content.Intent(context, it.feio.android.omninotes.async.AlarmRestoreOnRebootService.class);
                break;
            }
        }
        context.startService(jobIntent);
    }
}


@java.lang.Override
protected void onHandleWork(@androidx.annotation.NonNull
android.content.Intent intent) {
    it.feio.android.omninotes.helpers.LogDelegate.i("System rebooted: service refreshing reminders");
    android.content.Context mContext;
    mContext = getApplicationContext();
    it.feio.android.omninotes.BaseActivity.notifyAppWidgets(mContext);
    java.util.List<it.feio.android.omninotes.models.Note> notes;
    notes = it.feio.android.omninotes.db.DbHelper.getInstance().getNotesWithReminderNotFired();
    it.feio.android.omninotes.helpers.LogDelegate.d(("Found " + notes.size()) + " reminders");
    for (it.feio.android.omninotes.models.Note note : notes) {
        it.feio.android.omninotes.utils.ReminderHelper.addReminder(it.feio.android.omninotes.OmniNotes.getAppContext(), note);
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
