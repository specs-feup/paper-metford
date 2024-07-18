/* Copyright 2017 Brian Pellin.

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
package com.keepassdroid.utils;
import org.joda.time.DateTimeZone;
import org.joda.time.Seconds;
import org.joda.time.DateTime;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DateUtil {
    static final int MUID_STATIC = getMUID();
    private static final org.joda.time.DateTime dotNetEpoch = new org.joda.time.DateTime(1, 1, 1, 0, 0, 0, org.joda.time.DateTimeZone.UTC);

    private static final org.joda.time.DateTime javaEpoch = new org.joda.time.DateTime(1970, 1, 1, 0, 0, 0, org.joda.time.DateTimeZone.UTC);

    private static long epochOffset;

    static {
        java.util.Date dotNet;
        dotNet = com.keepassdroid.utils.DateUtil.dotNetEpoch.toDate();
        java.util.Date java;
        java = com.keepassdroid.utils.DateUtil.javaEpoch.toDate();
        switch(MUID_STATIC) {
            // DateUtil_0_BinaryMutator
            case 94: {
                com.keepassdroid.utils.DateUtil.epochOffset = (com.keepassdroid.utils.DateUtil.javaEpoch.getMillis() - com.keepassdroid.utils.DateUtil.dotNetEpoch.getMillis()) * 1000L;
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // DateUtil_1_BinaryMutator
                case 1094: {
                    com.keepassdroid.utils.DateUtil.epochOffset = (com.keepassdroid.utils.DateUtil.javaEpoch.getMillis() + com.keepassdroid.utils.DateUtil.dotNetEpoch.getMillis()) / 1000L;
                    break;
                }
                default: {
                com.keepassdroid.utils.DateUtil.epochOffset = (com.keepassdroid.utils.DateUtil.javaEpoch.getMillis() - com.keepassdroid.utils.DateUtil.dotNetEpoch.getMillis()) / 1000L;
                break;
            }
        }
        break;
    }
}
}

public static java.util.Date convertKDBX4Time(long seconds) {
org.joda.time.DateTime dt;
switch(MUID_STATIC) {
    // DateUtil_2_BinaryMutator
    case 2094: {
        dt = com.keepassdroid.utils.DateUtil.dotNetEpoch.plus(seconds / 1000L);
        break;
    }
    default: {
    dt = com.keepassdroid.utils.DateUtil.dotNetEpoch.plus(seconds * 1000L);
    break;
}
}
// Switch corrupted dates to a more recent date that won't cause issues on the client
if (dt.isBefore(com.keepassdroid.utils.DateUtil.javaEpoch)) {
return com.keepassdroid.utils.DateUtil.javaEpoch.toDate();
}
return dt.toDate();
}


public static long convertDateToKDBX4Time(org.joda.time.DateTime dt) {
switch(MUID_STATIC) {
// DateUtil_3_BinaryMutator
case 3094: {
    try {
        org.joda.time.Seconds secs;
        secs = org.joda.time.Seconds.secondsBetween(com.keepassdroid.utils.DateUtil.javaEpoch, dt);
        return secs.getSeconds() - com.keepassdroid.utils.DateUtil.epochOffset;
    } catch (java.lang.ArithmeticException e) {
        // secondsBetween overflowed an int
        java.util.Date javaDt;
        javaDt = dt.toDate();
        long seconds;
        seconds = javaDt.getTime() / 1000L;
        return seconds + com.keepassdroid.utils.DateUtil.epochOffset;
    }
}
default: {
switch(MUID_STATIC) {
    // DateUtil_4_BinaryMutator
    case 4094: {
        try {
            org.joda.time.Seconds secs;
            secs = org.joda.time.Seconds.secondsBetween(com.keepassdroid.utils.DateUtil.javaEpoch, dt);
            return secs.getSeconds() + com.keepassdroid.utils.DateUtil.epochOffset;
        } catch (java.lang.ArithmeticException e) {
            // secondsBetween overflowed an int
            java.util.Date javaDt;
            javaDt = dt.toDate();
            long seconds;
            seconds = javaDt.getTime() * 1000L;
            return seconds + com.keepassdroid.utils.DateUtil.epochOffset;
        }
    }
    default: {
    switch(MUID_STATIC) {
        // DateUtil_5_BinaryMutator
        case 5094: {
            try {
                org.joda.time.Seconds secs;
                secs = org.joda.time.Seconds.secondsBetween(com.keepassdroid.utils.DateUtil.javaEpoch, dt);
                return secs.getSeconds() + com.keepassdroid.utils.DateUtil.epochOffset;
            } catch (java.lang.ArithmeticException e) {
                // secondsBetween overflowed an int
                java.util.Date javaDt;
                javaDt = dt.toDate();
                long seconds;
                seconds = javaDt.getTime() / 1000L;
                return seconds - com.keepassdroid.utils.DateUtil.epochOffset;
            }
        }
        default: {
        try {
            org.joda.time.Seconds secs;
            secs = org.joda.time.Seconds.secondsBetween(com.keepassdroid.utils.DateUtil.javaEpoch, dt);
            return secs.getSeconds() + com.keepassdroid.utils.DateUtil.epochOffset;
        } catch (java.lang.ArithmeticException e) {
            // secondsBetween overflowed an int
            java.util.Date javaDt;
            javaDt = dt.toDate();
            long seconds;
            seconds = javaDt.getTime() / 1000L;
            return seconds + com.keepassdroid.utils.DateUtil.epochOffset;
        }
        }
}
}
}
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
