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
package it.feio.android.omninotes.utils;
import java.util.Calendar;
import android.os.Handler;
import android.text.TextUtils;
import android.content.Intent;
import it.feio.android.omninotes.utils.date.DateUtils;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE;
import it.feio.android.omninotes.OmniNotes;
import it.feio.android.omninotes.helpers.date.DateHelper;
import static it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.models.Note;
import android.app.AlarmManager;
import static android.app.PendingIntent.FLAG_NO_CREATE;
import android.widget.Toast;
import android.app.PendingIntent;
import it.feio.android.omninotes.receiver.AlarmReceiver;
import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ReminderHelper {
    static final int MUID_STATIC = getMUID();
    private ReminderHelper() {
        // hides public constructor
    }


    public static void addReminder(android.content.Context context, it.feio.android.omninotes.models.Note note) {
        if (note.getAlarm() != null) {
            it.feio.android.omninotes.utils.ReminderHelper.addReminder(context, note, java.lang.Long.parseLong(note.getAlarm()));
        }
    }


    public static void addReminder(android.content.Context context, it.feio.android.omninotes.models.Note note, long reminder) {
        if (it.feio.android.omninotes.utils.date.DateUtils.isFuture(reminder)) {
            android.content.Intent intent;
            switch(MUID_STATIC) {
                // ReminderHelper_0_NullIntentOperatorMutator
                case 94: {
                    intent = null;
                    break;
                }
                // ReminderHelper_1_InvalidKeyIntentOperatorMutator
                case 1094: {
                    intent = new android.content.Intent((Context) null, it.feio.android.omninotes.receiver.AlarmReceiver.class);
                    break;
                }
                // ReminderHelper_2_RandomActionIntentDefinitionOperatorMutator
                case 2094: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(context, it.feio.android.omninotes.receiver.AlarmReceiver.class);
                break;
            }
        }
        switch(MUID_STATIC) {
            // ReminderHelper_3_NullValueIntentPutExtraOperatorMutator
            case 3094: {
                intent.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE, new Parcelable[0]);
                break;
            }
            // ReminderHelper_4_IntentPayloadReplacementOperatorMutator
            case 4094: {
                intent.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE, (byte[]) null);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // ReminderHelper_5_RandomActionIntentDefinitionOperatorMutator
                case 5094: {
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
                intent.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE, it.feio.android.omninotes.utils.ParcelableUtil.marshall(note));
                break;
            }
        }
        break;
    }
}
android.app.PendingIntent sender;
sender = android.app.PendingIntent.getBroadcast(context, it.feio.android.omninotes.utils.ReminderHelper.getRequestCode(note), intent, it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag(android.app.PendingIntent.FLAG_CANCEL_CURRENT));
android.app.AlarmManager am;
am = ((android.app.AlarmManager) (context.getSystemService(android.content.Context.ALARM_SERVICE)));
am.setExact(android.app.AlarmManager.RTC_WAKEUP, reminder, sender);
}
}


/**
 * Checks if exists any reminder for given note
 */
public static boolean checkReminder(android.content.Context context, it.feio.android.omninotes.models.Note note) {
return android.app.PendingIntent.getBroadcast(context, it.feio.android.omninotes.utils.ReminderHelper.getRequestCode(note), new android.content.Intent(context, it.feio.android.omninotes.receiver.AlarmReceiver.class), it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag(android.app.PendingIntent.FLAG_NO_CREATE)) != null;
}


static int getRequestCode(it.feio.android.omninotes.models.Note note) {
long longCode;
switch(MUID_STATIC) {
// ReminderHelper_6_BinaryMutator
case 6094: {
    longCode = (note.getCreation() != null) ? note.getCreation() : java.util.Calendar.getInstance().getTimeInMillis() * 1000L;
    break;
}
default: {
longCode = (note.getCreation() != null) ? note.getCreation() : java.util.Calendar.getInstance().getTimeInMillis() / 1000L;
break;
}
}
return ((int) (longCode));
}


public static void removeReminder(android.content.Context context, it.feio.android.omninotes.models.Note note) {
if (!android.text.TextUtils.isEmpty(note.getAlarm())) {
android.app.AlarmManager am;
am = ((android.app.AlarmManager) (context.getSystemService(android.content.Context.ALARM_SERVICE)));
android.content.Intent intent;
switch(MUID_STATIC) {
// ReminderHelper_7_NullIntentOperatorMutator
case 7094: {
    intent = null;
    break;
}
// ReminderHelper_8_InvalidKeyIntentOperatorMutator
case 8094: {
    intent = new android.content.Intent((Context) null, it.feio.android.omninotes.receiver.AlarmReceiver.class);
    break;
}
// ReminderHelper_9_RandomActionIntentDefinitionOperatorMutator
case 9094: {
    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent = new android.content.Intent(context, it.feio.android.omninotes.receiver.AlarmReceiver.class);
break;
}
}
android.app.PendingIntent p;
p = android.app.PendingIntent.getBroadcast(context, it.feio.android.omninotes.utils.ReminderHelper.getRequestCode(note), intent, it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag(0));
am.cancel(p);
p.cancel();
}
}


public static void showReminderMessage(java.lang.String reminderString) {
if (reminderString != null) {
long reminder;
reminder = java.lang.Long.parseLong(reminderString);
if (reminder > java.util.Calendar.getInstance().getTimeInMillis()) {
new android.os.Handler(it.feio.android.omninotes.OmniNotes.getAppContext().getMainLooper()).post(() -> android.widget.Toast.makeText(it.feio.android.omninotes.OmniNotes.getAppContext(), (it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.alarm_set_on) + " ") + it.feio.android.omninotes.helpers.date.DateHelper.getDateTimeShort(it.feio.android.omninotes.OmniNotes.getAppContext(), reminder), android.widget.Toast.LENGTH_LONG).show());
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
