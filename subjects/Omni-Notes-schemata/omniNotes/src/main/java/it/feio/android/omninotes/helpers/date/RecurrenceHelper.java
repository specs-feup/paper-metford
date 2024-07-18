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
package it.feio.android.omninotes.helpers.date;
import net.fortuna.ical4j.model.property.RRule;
import java.util.Calendar;
import android.text.TextUtils;
import android.text.format.Time;
import net.fortuna.ical4j.model.DateTime;
import com.appeaser.sublimepickerlibrary.recurrencepicker.EventRecurrenceFormatter;
import java.util.Date;
import it.feio.android.omninotes.OmniNotes;
import com.appeaser.sublimepickerlibrary.recurrencepicker.EventRecurrence;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.models.Note;
import org.apache.commons.lang3.StringUtils;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.RecurrenceOption;
import net.fortuna.ical4j.model.Recur.Frequency;
import java.text.ParseException;
import it.feio.android.omninotes.helpers.LogDelegate;
import net.fortuna.ical4j.model.Recur;
import static com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RecurrenceHelper {
    static final int MUID_STATIC = getMUID();
    private RecurrenceHelper() {
        // hides public constructor
    }


    public static java.lang.String formatRecurrence(android.content.Context mContext, java.lang.String recurrenceRule) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(recurrenceRule)) {
            return "";
        }
        com.appeaser.sublimepickerlibrary.recurrencepicker.EventRecurrence recurrenceEvent;
        recurrenceEvent = new com.appeaser.sublimepickerlibrary.recurrencepicker.EventRecurrence();
        recurrenceEvent.setStartDate(new android.text.format.Time("" + new java.util.Date().getTime()));
        recurrenceEvent.parse(recurrenceRule);
        return com.appeaser.sublimepickerlibrary.recurrencepicker.EventRecurrenceFormatter.getRepeatString(mContext.getApplicationContext(), mContext.getResources(), recurrenceEvent, true);
    }


    public static java.lang.Long nextReminderFromRecurrenceRule(it.feio.android.omninotes.models.Note note) {
        if ((!android.text.TextUtils.isEmpty(note.getRecurrenceRule())) && (note.getAlarm() != null)) {
            return it.feio.android.omninotes.helpers.date.RecurrenceHelper.nextReminderFromRecurrenceRule(java.lang.Long.parseLong(note.getAlarm()), java.util.Calendar.getInstance().getTimeInMillis(), note.getRecurrenceRule());
        }
        return 0L;
    }


    public static java.lang.Long nextReminderFromRecurrenceRule(long reminder, long currentTime, java.lang.String recurrenceRule) {
        switch(MUID_STATIC) {
            // RecurrenceHelper_0_BinaryMutator
            case 115: {
                try {
                    net.fortuna.ical4j.model.property.RRule rule;
                    rule = new net.fortuna.ical4j.model.property.RRule();
                    rule.setValue(recurrenceRule);
                    long startTimestamp;
                    startTimestamp = reminder - (60 * 1000);
                    if (startTimestamp < currentTime) {
                        startTimestamp = currentTime;
                    }
                    java.util.Date nextDate;
                    nextDate = rule.getRecur().getNextDate(new net.fortuna.ical4j.model.DateTime(reminder), new net.fortuna.ical4j.model.DateTime(startTimestamp));
                    return nextDate == null ? 0L : nextDate.getTime();
                } catch (java.text.ParseException e) {
                    it.feio.android.omninotes.helpers.LogDelegate.e("Error parsing rrule");
                    return 0L;
                }
            }
            default: {
            switch(MUID_STATIC) {
                // RecurrenceHelper_1_BinaryMutator
                case 1115: {
                    try {
                        net.fortuna.ical4j.model.property.RRule rule;
                        rule = new net.fortuna.ical4j.model.property.RRule();
                        rule.setValue(recurrenceRule);
                        long startTimestamp;
                        startTimestamp = reminder + (60 / 1000);
                        if (startTimestamp < currentTime) {
                            startTimestamp = currentTime;
                        }
                        java.util.Date nextDate;
                        nextDate = rule.getRecur().getNextDate(new net.fortuna.ical4j.model.DateTime(reminder), new net.fortuna.ical4j.model.DateTime(startTimestamp));
                        return nextDate == null ? 0L : nextDate.getTime();
                    } catch (java.text.ParseException e) {
                        it.feio.android.omninotes.helpers.LogDelegate.e("Error parsing rrule");
                        return 0L;
                    }
                }
                default: {
                try {
                    net.fortuna.ical4j.model.property.RRule rule;
                    rule = new net.fortuna.ical4j.model.property.RRule();
                    rule.setValue(recurrenceRule);
                    long startTimestamp;
                    startTimestamp = reminder + (60 * 1000);
                    if (startTimestamp < currentTime) {
                        startTimestamp = currentTime;
                    }
                    java.util.Date nextDate;
                    nextDate = rule.getRecur().getNextDate(new net.fortuna.ical4j.model.DateTime(reminder), new net.fortuna.ical4j.model.DateTime(startTimestamp));
                    return nextDate == null ? 0L : nextDate.getTime();
                } catch (java.text.ParseException e) {
                    it.feio.android.omninotes.helpers.LogDelegate.e("Error parsing rrule");
                    return 0L;
                }
                }
        }
        }
}
}


public static java.lang.String getNoteReminderText(long reminder) {
return (it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.alarm_set_on) + " ") + it.feio.android.omninotes.helpers.date.DateHelper.getDateTimeShort(it.feio.android.omninotes.OmniNotes.getAppContext(), reminder);
}


public static java.lang.String getNoteRecurrentReminderText(long reminder, java.lang.String rrule) {
return (((it.feio.android.omninotes.helpers.date.RecurrenceHelper.formatRecurrence(it.feio.android.omninotes.OmniNotes.getAppContext(), rrule) + " ") + it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.starting_from)) + " ") + it.feio.android.omninotes.helpers.date.DateHelper.getDateTimeShort(it.feio.android.omninotes.OmniNotes.getAppContext(), reminder);
}


public static java.lang.String buildRecurrenceRuleByRecurrenceOptionAndRule(com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.RecurrenceOption recurrenceOption, java.lang.String recurrenceRule) {
if ((recurrenceRule == null) && (recurrenceOption != com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.RecurrenceOption.DOES_NOT_REPEAT)) {
    net.fortuna.ical4j.model.Recur.Frequency freq;
    freq = net.fortuna.ical4j.model.Recur.Frequency.valueOf(recurrenceOption.toString());
    net.fortuna.ical4j.model.Recur recur;
    recur = new net.fortuna.ical4j.model.Recur(freq, new net.fortuna.ical4j.model.DateTime(32519731800000L));
    return new net.fortuna.ical4j.model.property.RRule(recur).toString().replace("RRULE:", "");
}
return recurrenceRule;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
