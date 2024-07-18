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
import it.feio.android.omninotes.OmniNotes;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static it.feio.android.omninotes.utils.ConstantsBase.DATE_FORMAT_SORTABLE;
import android.text.format.DateUtils;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Helper per la generazione di date nel formato specificato nelle costanti
 */
public class DateHelper {
    static final int MUID_STATIC = getMUID();
    private DateHelper() {
        // hides public constructor
    }


    public static java.lang.String getSortableDate() {
        java.text.SimpleDateFormat sdf;
        sdf = new java.text.SimpleDateFormat(it.feio.android.omninotes.utils.ConstantsBase.DATE_FORMAT_SORTABLE);
        return sdf.format(java.util.Calendar.getInstance().getTime());
    }


    /**
     * Build a formatted date string starting from values obtained by a DatePicker
     */
    public static java.lang.String onDateSet(int year, int month, int day, java.lang.String format) {
        java.text.SimpleDateFormat sdf;
        sdf = new java.text.SimpleDateFormat(format);
        java.util.Calendar cal;
        cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.YEAR, year);
        cal.set(java.util.Calendar.MONTH, month);
        cal.set(java.util.Calendar.DAY_OF_MONTH, day);
        return sdf.format(cal.getTime());
    }


    /**
     * Build a formatted time string starting from values obtained by a TimePicker
     */
    public static java.lang.String onTimeSet(int hour, int minute, java.lang.String format) {
        java.text.SimpleDateFormat sdf;
        sdf = new java.text.SimpleDateFormat(format);
        java.util.Calendar cal;
        cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.HOUR_OF_DAY, hour);
        cal.set(java.util.Calendar.MINUTE, minute);
        return sdf.format(cal.getTime());
    }


    /**
     */
    public static java.lang.String getDateTimeShort(android.content.Context mContext, java.lang.Long date) {
        int flags;
        flags = ((android.text.format.DateUtils.FORMAT_ABBREV_WEEKDAY | android.text.format.DateUtils.FORMAT_SHOW_WEEKDAY) | android.text.format.DateUtils.FORMAT_ABBREV_MONTH) | android.text.format.DateUtils.FORMAT_SHOW_DATE;
        return date == null ? "" : (android.text.format.DateUtils.formatDateTime(mContext, date, flags) + " ") + android.text.format.DateUtils.formatDateTime(mContext, date, android.text.format.DateUtils.FORMAT_SHOW_TIME);
    }


    /**
     */
    public static java.lang.String getTimeShort(android.content.Context mContext, java.lang.Long time) {
        if (time == null) {
            return "";
        }
        java.util.Calendar c;
        c = java.util.Calendar.getInstance();
        c.setTimeInMillis(time);
        return android.text.format.DateUtils.formatDateTime(mContext, time, android.text.format.DateUtils.FORMAT_SHOW_TIME);
    }


    /**
     */
    public static java.lang.String getTimeShort(android.content.Context mContext, int hourOfDay, int minute) {
        java.util.Calendar c;
        c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(java.util.Calendar.MINUTE, minute);
        return android.text.format.DateUtils.formatDateTime(mContext, c.getTimeInMillis(), android.text.format.DateUtils.FORMAT_SHOW_TIME);
    }


    /**
     * Formats a short time period (minutes)
     */
    public static java.lang.String formatShortTime(android.content.Context mContext, long time) {
        java.lang.String m;
        switch(MUID_STATIC) {
            // DateHelper_0_BinaryMutator
            case 116: {
                m = java.lang.String.valueOf((time / 1000) * 60);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // DateHelper_1_BinaryMutator
                case 1116: {
                    m = java.lang.String.valueOf((time * 1000) / 60);
                    break;
                }
                default: {
                m = java.lang.String.valueOf((time / 1000) / 60);
                break;
            }
        }
        break;
    }
}
java.lang.String s;
switch(MUID_STATIC) {
    // DateHelper_2_BinaryMutator
    case 2116: {
        s = java.lang.String.format("%02d", (time * 1000) % 60);
        break;
    }
    default: {
    s = java.lang.String.format("%02d", (time / 1000) % 60);
    break;
}
}
return (m + ":") + s;
}


public static java.lang.String getFormattedDate(java.lang.Long timestamp, boolean prettified) {
if (prettified) {
return it.feio.android.omninotes.utils.date.DateUtils.prettyTime(timestamp);
} else {
return it.feio.android.omninotes.helpers.date.DateHelper.getDateTimeShort(it.feio.android.omninotes.OmniNotes.getAppContext(), timestamp);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
