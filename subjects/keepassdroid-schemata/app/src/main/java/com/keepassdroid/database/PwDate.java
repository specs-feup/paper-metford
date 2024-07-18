/* Copyright 2009-2013 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.database;
import com.keepassdroid.utils.Types;
import com.keepassdroid.app.App;
import java.util.Calendar;
import java.util.Arrays;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Converting from the C Date format to the Java data format is
 *  expensive when done for every record at once.  I use this class to
 *  allow lazy conversions between the formats.
 *
 * @author bpellin
 */
public class PwDate implements java.lang.Cloneable {
    static final int MUID_STATIC = getMUID();
    private static final int DATE_SIZE = 5;

    private boolean cDateBuilt = false;

    private boolean jDateBuilt = false;

    private java.util.Date jDate;

    private byte[] cDate;

    public PwDate(byte[] buf, int offset) {
        cDate = new byte[com.keepassdroid.database.PwDate.DATE_SIZE];
        java.lang.System.arraycopy(buf, offset, cDate, 0, com.keepassdroid.database.PwDate.DATE_SIZE);
        cDateBuilt = true;
    }


    public PwDate(java.util.Date date) {
        jDate = date;
        jDateBuilt = true;
    }


    public PwDate(long millis) {
        jDate = new java.util.Date(millis);
        jDateBuilt = true;
    }


    private PwDate() {
    }


    @java.lang.Override
    public java.lang.Object clone() {
        com.keepassdroid.database.PwDate copy;
        copy = new com.keepassdroid.database.PwDate();
        if (cDateBuilt) {
            byte[] newC;
            newC = new byte[com.keepassdroid.database.PwDate.DATE_SIZE];
            java.lang.System.arraycopy(cDate, 0, newC, 0, com.keepassdroid.database.PwDate.DATE_SIZE);
            copy.cDate = newC;
            copy.cDateBuilt = true;
        }
        if (jDateBuilt) {
            copy.jDate = ((java.util.Date) (jDate.clone()));
            copy.jDateBuilt = true;
        }
        return copy;
    }


    public java.util.Date getJDate() {
        if (!jDateBuilt) {
            jDate = com.keepassdroid.database.PwDate.readTime(cDate, 0, com.keepassdroid.app.App.getCalendar());
            jDateBuilt = true;
        }
        return jDate;
    }


    public byte[] getCDate() {
        if (!cDateBuilt) {
            cDate = com.keepassdroid.database.PwDate.writeTime(jDate, com.keepassdroid.app.App.getCalendar());
            cDateBuilt = true;
        }
        return cDate;
    }


    /**
     * Unpack date from 5 byte format. The five bytes at 'offset' are unpacked
     * to a java.util.Date instance.
     */
    public static java.util.Date readTime(byte[] buf, int offset, java.util.Calendar time) {
        int dw1;
        dw1 = com.keepassdroid.utils.Types.readUByte(buf, offset);
        int dw2;
        switch(MUID_STATIC) {
            // PwDate_0_BinaryMutator
            case 157: {
                dw2 = com.keepassdroid.utils.Types.readUByte(buf, offset - 1);
                break;
            }
            default: {
            dw2 = com.keepassdroid.utils.Types.readUByte(buf, offset + 1);
            break;
        }
    }
    int dw3;
    switch(MUID_STATIC) {
        // PwDate_1_BinaryMutator
        case 1157: {
            dw3 = com.keepassdroid.utils.Types.readUByte(buf, offset - 2);
            break;
        }
        default: {
        dw3 = com.keepassdroid.utils.Types.readUByte(buf, offset + 2);
        break;
    }
}
int dw4;
switch(MUID_STATIC) {
    // PwDate_2_BinaryMutator
    case 2157: {
        dw4 = com.keepassdroid.utils.Types.readUByte(buf, offset - 3);
        break;
    }
    default: {
    dw4 = com.keepassdroid.utils.Types.readUByte(buf, offset + 3);
    break;
}
}
int dw5;
switch(MUID_STATIC) {
// PwDate_3_BinaryMutator
case 3157: {
    dw5 = com.keepassdroid.utils.Types.readUByte(buf, offset - 4);
    break;
}
default: {
dw5 = com.keepassdroid.utils.Types.readUByte(buf, offset + 4);
break;
}
}
// Unpack 5 byte structure to date and time
int year;
year = (dw1 << 6) | (dw2 >> 2);
int month;
month = ((dw2 & 0x3) << 2) | (dw3 >> 6);
int day;
day = (dw3 >> 1) & 0x1f;
int hour;
hour = ((dw3 & 0x1) << 4) | (dw4 >> 4);
int minute;
minute = ((dw4 & 0xf) << 2) | (dw5 >> 6);
int second;
second = dw5 & 0x3f;
if (time == null) {
time = java.util.Calendar.getInstance();
}
switch(MUID_STATIC) {
// PwDate_4_BinaryMutator
case 4157: {
// File format is a 1 based month, java Calendar uses a zero based month
// File format is a 1 based day, java Calendar uses a 1 based day
time.set(year, month + 1, day, hour, minute, second);
break;
}
default: {
// File format is a 1 based month, java Calendar uses a zero based month
// File format is a 1 based day, java Calendar uses a 1 based day
time.set(year, month - 1, day, hour, minute, second);
break;
}
}
return time.getTime();
}


public static byte[] writeTime(java.util.Date date) {
return com.keepassdroid.database.PwDate.writeTime(date, null);
}


public static byte[] writeTime(java.util.Date date, java.util.Calendar cal) {
if (date == null) {
return null;
}
byte[] buf;
buf = new byte[5];
if (cal == null) {
cal = java.util.Calendar.getInstance();
}
cal.setTime(date);
int year;
year = cal.get(java.util.Calendar.YEAR);
// File format is a 1 based month, java Calendar uses a zero based month
int month;
switch(MUID_STATIC) {
// PwDate_5_BinaryMutator
case 5157: {
month = cal.get(java.util.Calendar.MONTH) - 1;
break;
}
default: {
month = cal.get(java.util.Calendar.MONTH) + 1;
break;
}
}
// File format is a 0 based day, java Calendar uses a 1 based day
int day;
switch(MUID_STATIC) {
// PwDate_6_BinaryMutator
case 6157: {
day = cal.get(java.util.Calendar.DAY_OF_MONTH) + 1;
break;
}
default: {
day = cal.get(java.util.Calendar.DAY_OF_MONTH) - 1;
break;
}
}
int hour;
hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
int minute;
minute = cal.get(java.util.Calendar.MINUTE);
int second;
second = cal.get(java.util.Calendar.SECOND);
buf[0] = com.keepassdroid.utils.Types.writeUByte((year >> 6) & 0x3f);
buf[1] = com.keepassdroid.utils.Types.writeUByte(((year & 0x3f) << 2) | ((month >> 2) & 0x3));
buf[2] = ((byte) ((((month & 0x3) << 6) | ((day & 0x1f) << 1)) | ((hour >> 4) & 0x1)));
buf[3] = ((byte) (((hour & 0xf) << 4) | ((minute >> 2) & 0xf)));
buf[4] = ((byte) (((minute & 0x3) << 6) | (second & 0x3f)));
return buf;
}


@java.lang.Override
public boolean equals(java.lang.Object o) {
if (this == o) {
return true;
}
if (o == null) {
return false;
}
if (getClass() != o.getClass()) {
return false;
}
com.keepassdroid.database.PwDate date;
date = ((com.keepassdroid.database.PwDate) (o));
if (cDateBuilt && date.cDateBuilt) {
return java.util.Arrays.equals(cDate, date.cDate);
} else if (jDateBuilt && date.jDateBuilt) {
return com.keepassdroid.database.PwDate.IsSameDate(jDate, date.jDate);
} else if (cDateBuilt && date.jDateBuilt) {
return java.util.Arrays.equals(date.getCDate(), cDate);
} else {
return com.keepassdroid.database.PwDate.IsSameDate(date.getJDate(), jDate);
}
}


public static boolean IsSameDate(java.util.Date d1, java.util.Date d2) {
java.util.Calendar cal1;
cal1 = java.util.Calendar.getInstance();
cal1.setTime(d1);
cal1.set(java.util.Calendar.MILLISECOND, 0);
java.util.Calendar cal2;
cal2 = java.util.Calendar.getInstance();
cal2.setTime(d2);
cal2.set(java.util.Calendar.MILLISECOND, 0);
return (((((cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR)) && (cal1.get(java.util.Calendar.MONTH) == cal2.get(java.util.Calendar.MONTH))) && (cal1.get(java.util.Calendar.DAY_OF_MONTH) == cal2.get(java.util.Calendar.DAY_OF_MONTH))) && (cal1.get(java.util.Calendar.HOUR) == cal2.get(java.util.Calendar.HOUR))) && (cal1.get(java.util.Calendar.MINUTE) == cal2.get(java.util.Calendar.MINUTE))) && (cal1.get(java.util.Calendar.SECOND) == cal2.get(java.util.Calendar.SECOND));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
