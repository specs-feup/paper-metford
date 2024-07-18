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
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_NOTIFICATION_CLICK;
import android.app.NotificationManager;
import java.util.Calendar;
import android.os.Bundle;
import it.feio.android.omninotes.async.notes.SaveNoteTask;
import androidx.appcompat.app.AppCompatActivity;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_SNOOZE;
import android.content.Intent;
import android.os.AsyncTask;
import it.feio.android.omninotes.utils.date.DateUtils;
import com.pixplicity.easyprefs.library.Prefs;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE;
import it.feio.android.omninotes.helpers.date.RecurrenceHelper;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_POSTPONE;
import it.feio.android.omninotes.utils.ReminderHelper;
import it.feio.android.omninotes.models.listeners.OnReminderPickedListener;
import it.feio.android.omninotes.models.Note;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_PINNED;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_SNOOZE_DEFAULT;
import it.feio.android.omninotes.utils.date.ReminderPickers;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_DISMISS;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_KEY;
import java.util.Arrays;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SnoozeActivity extends androidx.appcompat.app.AppCompatActivity implements it.feio.android.omninotes.models.listeners.OnReminderPickedListener {
    static final int MUID_STATIC = getMUID();
    private it.feio.android.omninotes.models.Note note;

    private it.feio.android.omninotes.models.Note[] notes;

    public static void setNextRecurrentReminder(it.feio.android.omninotes.models.Note note) {
        long nextReminder;
        nextReminder = it.feio.android.omninotes.helpers.date.RecurrenceHelper.nextReminderFromRecurrenceRule(note);
        if (nextReminder > 0) {
            it.feio.android.omninotes.SnoozeActivity.updateNoteReminder(nextReminder, note, true);
        } else {
            new it.feio.android.omninotes.async.notes.SaveNoteTask(false).executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, note);
        }
    }


    private static void updateNoteReminder(long reminder, it.feio.android.omninotes.models.Note note) {
        it.feio.android.omninotes.SnoozeActivity.updateNoteReminder(reminder, note, false);
    }


    private static void updateNoteReminder(long reminder, it.feio.android.omninotes.models.Note noteToUpdate, boolean updateNote) {
        if (updateNote) {
            noteToUpdate.setAlarm(reminder);
            new it.feio.android.omninotes.async.notes.SaveNoteTask(false).executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, noteToUpdate);
        } else {
            it.feio.android.omninotes.utils.ReminderHelper.addReminder(it.feio.android.omninotes.OmniNotes.getAppContext(), noteToUpdate, reminder);
            it.feio.android.omninotes.utils.ReminderHelper.showReminderMessage(noteToUpdate.getAlarm());
        }
    }


    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SnoozeActivity_0_LengthyGUICreationOperatorMutator
            case 138: {
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
    if (getIntent().getParcelableExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE) != null) {
        note = getIntent().getParcelableExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE);
        manageNotification();
    } else {
        java.lang.Object[] notesObjs;
        notesObjs = ((java.lang.Object[]) (getIntent().getExtras().get(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE)));
        notes = java.util.Arrays.copyOf(notesObjs, notesObjs.length, it.feio.android.omninotes.models.Note[].class);
        postpone(it.feio.android.omninotes.utils.date.DateUtils.getNextMinute(), null);
    }
}


private void manageNotification() {
    if (it.feio.android.omninotes.utils.ConstantsBase.ACTION_DISMISS.equals(getIntent().getAction())) {
        it.feio.android.omninotes.SnoozeActivity.setNextRecurrentReminder(note);
        finish();
    } else if (it.feio.android.omninotes.utils.ConstantsBase.ACTION_SNOOZE.equals(getIntent().getAction())) {
        java.lang.String snoozeDelay;
        snoozeDelay = com.pixplicity.easyprefs.library.Prefs.getString("settings_notification_snooze_delay", it.feio.android.omninotes.utils.ConstantsBase.PREF_SNOOZE_DEFAULT);
        long newReminder;
        switch(MUID_STATIC) {
            // SnoozeActivity_1_BinaryMutator
            case 1138: {
                newReminder = java.util.Calendar.getInstance().getTimeInMillis() - ((java.lang.Integer.parseInt(snoozeDelay) * 60) * 1000);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // SnoozeActivity_2_BinaryMutator
                case 2138: {
                    newReminder = java.util.Calendar.getInstance().getTimeInMillis() + ((java.lang.Integer.parseInt(snoozeDelay) * 60) / 1000);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // SnoozeActivity_3_BinaryMutator
                    case 3138: {
                        newReminder = java.util.Calendar.getInstance().getTimeInMillis() + ((java.lang.Integer.parseInt(snoozeDelay) / 60) * 1000);
                        break;
                    }
                    default: {
                    newReminder = java.util.Calendar.getInstance().getTimeInMillis() + ((java.lang.Integer.parseInt(snoozeDelay) * 60) * 1000);
                    break;
                }
            }
            break;
        }
    }
    break;
}
}
it.feio.android.omninotes.SnoozeActivity.updateNoteReminder(newReminder, note);
finish();
} else if (it.feio.android.omninotes.utils.ConstantsBase.ACTION_POSTPONE.equals(getIntent().getAction())) {
postpone(java.lang.Long.parseLong(note.getAlarm()), note.getRecurrenceRule());
} else {
android.content.Intent intent;
switch(MUID_STATIC) {
// SnoozeActivity_4_NullIntentOperatorMutator
case 4138: {
    intent = null;
    break;
}
// SnoozeActivity_5_InvalidKeyIntentOperatorMutator
case 5138: {
    intent = new android.content.Intent((SnoozeActivity) null, it.feio.android.omninotes.MainActivity.class);
    break;
}
// SnoozeActivity_6_RandomActionIntentDefinitionOperatorMutator
case 6138: {
    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent = new android.content.Intent(this, it.feio.android.omninotes.MainActivity.class);
break;
}
}
switch(MUID_STATIC) {
// SnoozeActivity_7_NullValueIntentPutExtraOperatorMutator
case 7138: {
intent.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_KEY, new Parcelable[0]);
break;
}
// SnoozeActivity_8_IntentPayloadReplacementOperatorMutator
case 8138: {
intent.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_KEY, (Long) null);
break;
}
default: {
switch(MUID_STATIC) {
// SnoozeActivity_9_RandomActionIntentDefinitionOperatorMutator
case 9138: {
    /**
    * Inserted by Kadabra
    */
    /**
    * Inserted by Kadabra
    */
    new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_KEY, note.get_id());
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// SnoozeActivity_10_RandomActionIntentDefinitionOperatorMutator
case 10138: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.setAction(it.feio.android.omninotes.utils.ConstantsBase.ACTION_NOTIFICATION_CLICK);
break;
}
}
startActivity(intent);
finish();
}
if (!it.feio.android.omninotes.utils.ConstantsBase.ACTION_PINNED.equals(getIntent().getAction())) {
removeNotification(note);
}
}


private void postpone(java.lang.Long alarm, java.lang.String recurrenceRule) {
it.feio.android.omninotes.utils.date.ReminderPickers reminderPicker;
reminderPicker = new it.feio.android.omninotes.utils.date.ReminderPickers(this, this);
reminderPicker.pick(alarm, recurrenceRule);
}


private void removeNotification(it.feio.android.omninotes.models.Note note) {
android.app.NotificationManager manager;
manager = ((android.app.NotificationManager) (getSystemService(android.content.Context.NOTIFICATION_SERVICE)));
manager.cancel(java.lang.String.valueOf(note.get_id()), 0);
}


@java.lang.Override
public void onReminderPicked(long reminder) {
if (note != null) {
note.setAlarm(reminder);
} else {
for (it.feio.android.omninotes.models.Note currentNote : notes) {
currentNote.setAlarm(reminder);
}
}
}


@java.lang.Override
public void onRecurrenceReminderPicked(java.lang.String recurrenceRule) {
if (note != null) {
note.setRecurrenceRule(recurrenceRule);
it.feio.android.omninotes.SnoozeActivity.setNextRecurrentReminder(note);
} else {
for (it.feio.android.omninotes.models.Note processedNotes : notes) {
processedNotes.setRecurrenceRule(recurrenceRule);
it.feio.android.omninotes.SnoozeActivity.setNextRecurrentReminder(processedNotes);
}
setResult(android.app.Activity.RESULT_OK, getIntent());
}
finish();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
