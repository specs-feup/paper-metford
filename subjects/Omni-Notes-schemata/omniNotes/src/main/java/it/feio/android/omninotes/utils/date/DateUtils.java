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
package it.feio.android.omninotes.utils.date;
import it.feio.android.omninotes.OmniNotes;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.lang3.StringUtils;
import static it.feio.android.omninotes.utils.ConstantsBase.DATE_FORMAT_SORTABLE_OLD;
import java.text.ParseException;
import it.feio.android.omninotes.helpers.LogDelegate;
import org.ocpsoft.prettytime.PrettyTime;
import android.content.Context;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Helper per la generazione di date nel formato specificato nelle costanti
 */
public class DateUtils {
    static final int MUID_STATIC = getMUID();
    private DateUtils() {
        throw new java.lang.IllegalStateException("Utility class");
    }


    public static java.lang.String getString(long date, java.lang.String format) {
        java.util.Date d;
        d = new java.util.Date(date);
        return it.feio.android.omninotes.utils.date.DateUtils.getString(d, format);
    }


    public static java.lang.String getString(java.util.Date d, java.lang.String format) {
        java.text.SimpleDateFormat sdf;
        sdf = new java.text.SimpleDateFormat(format);
        return sdf.format(d);
    }


    public static java.util.Calendar getDateFromString(java.lang.String str, java.lang.String format) {
        java.util.Calendar cal;
        cal = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sdf;
        sdf = new java.text.SimpleDateFormat(format);
        try {
            cal.setTime(sdf.parse(str));
        } catch (java.text.ParseException e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Malformed datetime string" + e.getMessage());
        } catch (java.lang.NullPointerException e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Date or time not set");
        }
        return cal;
    }


    public static java.util.Calendar getLongFromDateTime(java.lang.String date, java.lang.String dateFormat, java.lang.String time, java.lang.String timeFormat) {
        java.util.Calendar cal;
        cal = java.util.Calendar.getInstance();
        java.util.Calendar cDate;
        cDate = java.util.Calendar.getInstance();
        java.util.Calendar cTime;
        cTime = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sdfDate;
        sdfDate = new java.text.SimpleDateFormat(dateFormat);
        java.text.SimpleDateFormat sdfTime;
        sdfTime = new java.text.SimpleDateFormat(timeFormat);
        try {
            cDate.setTime(sdfDate.parse(date));
            cTime.setTime(sdfTime.parse(time));
        } catch (java.text.ParseException e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Date or time parsing error: " + e.getMessage());
        }
        cal.set(java.util.Calendar.YEAR, cDate.get(java.util.Calendar.YEAR));
        cal.set(java.util.Calendar.MONTH, cDate.get(java.util.Calendar.MONTH));
        cal.set(java.util.Calendar.DAY_OF_MONTH, cDate.get(java.util.Calendar.DAY_OF_MONTH));
        cal.set(java.util.Calendar.HOUR_OF_DAY, cTime.get(java.util.Calendar.HOUR_OF_DAY));
        cal.set(java.util.Calendar.MINUTE, cTime.get(java.util.Calendar.MINUTE));
        cal.set(java.util.Calendar.SECOND, 0);
        return cal;
    }


    public static java.util.Calendar getCalendar(java.lang.Long dateTime) {
        java.util.Calendar cal;
        cal = java.util.Calendar.getInstance();
        if ((dateTime != null) && (dateTime != 0)) {
            cal.setTimeInMillis(dateTime);
        }
        return cal;
    }


    public static java.lang.String getLocalizedDateTime(android.content.Context mContext, java.lang.String dateString, java.lang.String format) {
        java.lang.String res;
        res = null;
        java.text.SimpleDateFormat sdf;
        sdf = new java.text.SimpleDateFormat(format);
        java.util.Date date;
        date = null;
        try {
            date = sdf.parse(dateString);
        } catch (java.text.ParseException e) {
            sdf = new java.text.SimpleDateFormat(it.feio.android.omninotes.utils.ConstantsBase.DATE_FORMAT_SORTABLE_OLD);
            try {
                date = sdf.parse(dateString);
            } catch (java.text.ParseException e1) {
                it.feio.android.omninotes.helpers.LogDelegate.e("String is not formattable into date");
            }
        }
        if (date != null) {
            java.lang.String dateFormatted;
            dateFormatted = android.text.format.DateUtils.formatDateTime(mContext, date.getTime(), android.text.format.DateUtils.FORMAT_ABBREV_MONTH);
            java.lang.String timeFormatted;
            timeFormatted = android.text.format.DateUtils.formatDateTime(mContext, date.getTime(), android.text.format.DateUtils.FORMAT_SHOW_TIME);
            res = (dateFormatted + " ") + timeFormatted;
        }
        return res;
    }


    public static boolean is24HourMode(android.content.Context mContext) {
        java.util.Calendar c;
        c = java.util.Calendar.getInstance();
        java.lang.String timeFormatted;
        timeFormatted = android.text.format.DateUtils.formatDateTime(mContext, c.getTimeInMillis(), android.text.format.DateUtils.FORMAT_SHOW_TIME);
        return (!timeFormatted.toLowerCase().contains("am")) && (!timeFormatted.toLowerCase().contains("pm"));
    }


    public static boolean isSameDay(long date1, long date2) {
        java.util.Calendar cal1;
        cal1 = java.util.Calendar.getInstance();
        java.util.Calendar cal2;
        cal2 = java.util.Calendar.getInstance();
        cal1.setTimeInMillis(date1);
        cal2.setTimeInMillis(date2);
        return (cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR)) && (cal1.get(java.util.Calendar.DAY_OF_YEAR) == cal2.get(java.util.Calendar.DAY_OF_YEAR));
    }


    public static long getNextMinute() {
        switch(MUID_STATIC) {
            // DateUtils_0_BinaryMutator
            case 83: {
                return java.util.Calendar.getInstance().getTimeInMillis() - (1000 * 60);
            }
            default: {
            switch(MUID_STATIC) {
                // DateUtils_1_BinaryMutator
                case 1083: {
                    return java.util.Calendar.getInstance().getTimeInMillis() + (1000 / 60);
                }
                default: {
                return java.util.Calendar.getInstance().getTimeInMillis() + (1000 * 60);
                }
        }
        }
}
}


/**
 * Returns actually set reminder if that is on the future, next-minute-reminder otherwise
 */
public static long getPresetReminder(java.lang.Long currentReminder) {
long now;
now = java.util.Calendar.getInstance().getTimeInMillis();
return (currentReminder != null) && (currentReminder > now) ? currentReminder : it.feio.android.omninotes.utils.date.DateUtils.getNextMinute();
}


public static java.lang.Long getPresetReminder(java.lang.String alarm) {
long alarmChecked;
alarmChecked = (alarm == null) ? 0 : java.lang.Long.parseLong(alarm);
return it.feio.android.omninotes.utils.date.DateUtils.getPresetReminder(alarmChecked);
}


/**
 * Checks if a epoch-date timestamp is in the future
 */
public static boolean isFuture(java.lang.String timestamp) {
return (!org.apache.commons.lang3.StringUtils.isEmpty(timestamp)) && it.feio.android.omninotes.utils.date.DateUtils.isFuture(java.lang.Long.parseLong(timestamp));
}


/**
 * Checks if a epoch-date timestamp is in the future
 */
public static boolean isFuture(java.lang.Long timestamp) {
return (timestamp != null) && (timestamp > java.util.Calendar.getInstance().getTimeInMillis());
}


public static java.lang.String prettyTime(java.lang.String timeInMillisec) {
if (timeInMillisec == null) {
    return "";
}
return it.feio.android.omninotes.utils.date.DateUtils.prettyTime(java.lang.Long.parseLong(timeInMillisec), it.feio.android.omninotes.OmniNotes.getAppContext().getResources().getConfiguration().locale);
}


public static java.lang.String prettyTime(java.lang.Long timeInMillisec) {
return it.feio.android.omninotes.utils.date.DateUtils.prettyTime(timeInMillisec, it.feio.android.omninotes.OmniNotes.getAppContext().getResources().getConfiguration().locale);
}


static java.lang.String prettyTime(java.lang.Long timeInMillisec, java.util.Locale locale) {
if (timeInMillisec == null) {
    return "";
}
java.util.Date d;
d = new java.util.Date(timeInMillisec);
org.ocpsoft.prettytime.PrettyTime pt;
pt = new org.ocpsoft.prettytime.PrettyTime();
if (locale != null) {
    pt.setLocale(locale);
}
return pt.format(d);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
