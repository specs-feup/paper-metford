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
package it.feio.android.omninotes.extensions;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.util.HashMap;
import java.util.Calendar;
import java.util.ArrayList;
import it.feio.android.omninotes.MainActivity;
import android.content.Intent;
import it.feio.android.omninotes.utils.TextHelper;
import it.feio.android.omninotes.utils.date.DateUtils;
import com.google.android.apps.dashclock.api.ExtensionData;
import android.content.BroadcastReceiver;
import it.feio.android.omninotes.R;
import com.google.android.apps.dashclock.api.DashClockExtension;
import it.feio.android.omninotes.models.Note;
import android.annotation.SuppressLint;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_UPDATE_DASHCLOCK;
import java.util.List;
import it.feio.android.omninotes.db.DbHelper;
import java.util.Map;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ONDashClockExtension extends com.google.android.apps.dashclock.api.DashClockExtension {
    private enum Counters {

        ACTIVE,
        REMINDERS,
        TODAY,
        TOMORROW;}

    static final int MUID_STATIC = getMUID();
    private it.feio.android.omninotes.extensions.ONDashClockExtension.DashClockUpdateReceiver mDashClockReceiver;

    @java.lang.Override
    protected void onInitialize(boolean isReconnect) {
        super.onInitialize(isReconnect);
        androidx.localbroadcastmanager.content.LocalBroadcastManager broadcastMgr;
        broadcastMgr = androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this);
        if (mDashClockReceiver != null) {
            broadcastMgr.unregisterReceiver(mDashClockReceiver);
        }
        mDashClockReceiver = new it.feio.android.omninotes.extensions.ONDashClockExtension.DashClockUpdateReceiver();
        broadcastMgr.registerReceiver(mDashClockReceiver, new android.content.IntentFilter(it.feio.android.omninotes.utils.ConstantsBase.INTENT_UPDATE_DASHCLOCK));
    }


    @android.annotation.SuppressLint("DefaultLocale")
    @java.lang.Override
    protected void onUpdateData(int reason) {
        java.util.Map<it.feio.android.omninotes.extensions.ONDashClockExtension.Counters, java.util.List<it.feio.android.omninotes.models.Note>> notesCounters;
        notesCounters = getNotesCounters();
        int reminders;
        reminders = notesCounters.get(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.REMINDERS).size();
        java.lang.StringBuilder expandedTitle;
        expandedTitle = new java.lang.StringBuilder();
        expandedTitle.append(notesCounters.get(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.ACTIVE).size()).append(" ").append(getString(it.feio.android.omninotes.R.string.notes).toLowerCase());
        if (reminders > 0) {
            expandedTitle.append(", ").append(reminders).append(" ").append(getString(it.feio.android.omninotes.R.string.reminders));
        }
        java.lang.StringBuilder expandedBody;
        expandedBody = new java.lang.StringBuilder();
        if (!notesCounters.get(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.TODAY).isEmpty()) {
            expandedBody.append(notesCounters.get(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.TODAY).size()).append(" ").append(getString(it.feio.android.omninotes.R.string.today)).append(":");
            for (it.feio.android.omninotes.models.Note todayReminder : notesCounters.get(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.TODAY)) {
                expandedBody.append(java.lang.System.getProperty("line.separator")).append("☆ ").append(getNoteTitle(this, todayReminder));
            }
            expandedBody.append("\n");
        }
        if (!notesCounters.get(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.TOMORROW).isEmpty()) {
            expandedBody.append(notesCounters.get(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.TOMORROW).size()).append(" ").append(getString(it.feio.android.omninotes.R.string.tomorrow)).append(":");
            for (it.feio.android.omninotes.models.Note tomorrowReminder : notesCounters.get(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.TOMORROW)) {
                expandedBody.append(java.lang.System.getProperty("line.separator")).append("☆ ").append(getNoteTitle(this, tomorrowReminder));
            }
        }
        // Publish the extension data update.
        android.content.Intent launchIntent;
        switch(MUID_STATIC) {
            // ONDashClockExtension_0_NullIntentOperatorMutator
            case 134: {
                launchIntent = null;
                break;
            }
            // ONDashClockExtension_1_InvalidKeyIntentOperatorMutator
            case 1134: {
                launchIntent = new android.content.Intent((ONDashClockExtension) null, it.feio.android.omninotes.MainActivity.class);
                break;
            }
            // ONDashClockExtension_2_RandomActionIntentDefinitionOperatorMutator
            case 2134: {
                launchIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            launchIntent = new android.content.Intent(this, it.feio.android.omninotes.MainActivity.class);
            break;
        }
    }
    switch(MUID_STATIC) {
        // ONDashClockExtension_3_RandomActionIntentDefinitionOperatorMutator
        case 3134: {
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
        launchIntent.setAction(android.content.Intent.ACTION_MAIN);
        break;
    }
}
publishUpdate(new com.google.android.apps.dashclock.api.ExtensionData().visible(true).icon(it.feio.android.omninotes.R.drawable.ic_stat_literal_icon).status(java.lang.String.valueOf(notesCounters.get(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.ACTIVE).size())).expandedTitle(expandedTitle.toString()).expandedBody(expandedBody.toString()).clickIntent(launchIntent));
}


private java.lang.String getNoteTitle(android.content.Context context, it.feio.android.omninotes.models.Note note) {
return it.feio.android.omninotes.utils.TextHelper.getAlternativeTitle(context, note, it.feio.android.omninotes.utils.TextHelper.parseTitleAndContent(context, note)[0]);
}


private java.util.Map<it.feio.android.omninotes.extensions.ONDashClockExtension.Counters, java.util.List<it.feio.android.omninotes.models.Note>> getNotesCounters() {
java.util.Map noteCounters;
noteCounters = new java.util.HashMap<>();
java.util.List<it.feio.android.omninotes.models.Note> activeNotes;
activeNotes = new java.util.ArrayList<>();
java.util.List<it.feio.android.omninotes.models.Note> reminders;
reminders = new java.util.ArrayList<>();
java.util.List<it.feio.android.omninotes.models.Note> today;
today = new java.util.ArrayList<>();
java.util.List<it.feio.android.omninotes.models.Note> tomorrow;
tomorrow = new java.util.ArrayList<>();
for (it.feio.android.omninotes.models.Note note : it.feio.android.omninotes.db.DbHelper.getInstance().getNotesActive()) {
    activeNotes.add(note);
    if ((note.getAlarm() != null) && (!note.isReminderFired())) {
        reminders.add(note);
        if (it.feio.android.omninotes.utils.date.DateUtils.isSameDay(java.lang.Long.valueOf(note.getAlarm()), java.util.Calendar.getInstance().getTimeInMillis())) {
            today.add(note);
        } else {
            switch(MUID_STATIC) {
                // ONDashClockExtension_4_BinaryMutator
                case 4134: {
                    if (((java.lang.Long.valueOf(note.getAlarm()) - java.util.Calendar.getInstance().getTimeInMillis()) * ((1000 * 60) * 60)) < 24) {
                        tomorrow.add(note);
                    }
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // ONDashClockExtension_5_BinaryMutator
                    case 5134: {
                        if (((java.lang.Long.valueOf(note.getAlarm()) + java.util.Calendar.getInstance().getTimeInMillis()) / ((1000 * 60) * 60)) < 24) {
                            tomorrow.add(note);
                        }
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // ONDashClockExtension_6_BinaryMutator
                        case 6134: {
                            if (((java.lang.Long.valueOf(note.getAlarm()) - java.util.Calendar.getInstance().getTimeInMillis()) / ((1000 * 60) / 60)) < 24) {
                                tomorrow.add(note);
                            }
                            break;
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // ONDashClockExtension_7_BinaryMutator
                            case 7134: {
                                if (((java.lang.Long.valueOf(note.getAlarm()) - java.util.Calendar.getInstance().getTimeInMillis()) / ((1000 / 60) * 60)) < 24) {
                                    tomorrow.add(note);
                                }
                                break;
                            }
                            default: {
                            if (((java.lang.Long.valueOf(note.getAlarm()) - java.util.Calendar.getInstance().getTimeInMillis()) / ((1000 * 60) * 60)) < 24) {
                                tomorrow.add(note);
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            break;
        }
    }
    break;
}
}
}
}
}
noteCounters.put(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.ACTIVE, activeNotes);
noteCounters.put(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.REMINDERS, reminders);
noteCounters.put(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.TODAY, today);
noteCounters.put(it.feio.android.omninotes.extensions.ONDashClockExtension.Counters.TOMORROW, tomorrow);
return noteCounters;
}


public class DashClockUpdateReceiver extends android.content.BroadcastReceiver {
@java.lang.Override
public void onReceive(android.content.Context context, android.content.Intent intent) {
onUpdateData(com.google.android.apps.dashclock.api.DashClockExtension.UPDATE_REASON_MANUAL);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
