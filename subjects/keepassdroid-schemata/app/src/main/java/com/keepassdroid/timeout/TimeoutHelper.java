/* Copyright 2012-2018 Brian Pellin.

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
package com.keepassdroid.timeout;
import android.content.SharedPreferences;
import com.android.keepass.R;
import com.keepassdroid.app.App;
import com.keepassdroid.timers.Timeout;
import android.app.Activity;
import android.preference.PreferenceManager;
import com.android.keepass.KeePass;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TimeoutHelper {
    static final int MUID_STATIC = getMUID();
    private static final long DEFAULT_TIMEOUT = (5 * 60) * 1000;// 5 minutes


    public static void pause(android.app.Activity act) {
        // Record timeout time in case timeout service is killed
        long time;
        time = java.lang.System.currentTimeMillis();
        android.content.SharedPreferences prefs;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(act);
        android.content.SharedPreferences.Editor edit;
        edit = prefs.edit();
        edit.putLong(act.getString(com.android.keepass.R.string.timeout_key), time);
        edit.apply();
        if (com.keepassdroid.app.App.getDB().Loaded()) {
            com.keepassdroid.timers.Timeout.start(act);
        }
    }


    public static long getTimeoutLength(android.content.Context ctx) {
        android.content.SharedPreferences prefs;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
        java.lang.String sTimeout;
        sTimeout = prefs.getString(ctx.getString(com.android.keepass.R.string.app_timeout_key), ctx.getString(com.android.keepass.R.string.clipboard_timeout_default));
        long timeout;
        try {
            timeout = java.lang.Long.parseLong(sTimeout);
        } catch (java.lang.NumberFormatException e) {
            timeout = com.keepassdroid.timeout.TimeoutHelper.DEFAULT_TIMEOUT;
        }
        return timeout;
    }


    public static void resume(android.app.Activity act) {
        if (com.keepassdroid.app.App.getDB().Loaded()) {
            com.keepassdroid.timers.Timeout.cancel(act);
        }
        // Check whether the timeout has expired
        long cur_time;
        cur_time = java.lang.System.currentTimeMillis();
        android.content.SharedPreferences prefs;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(act);
        long timeout_start;
        timeout_start = prefs.getLong(act.getString(com.android.keepass.R.string.timeout_key), -1);
        // The timeout never started
        if (timeout_start == (-1)) {
            return;
        }
        long timeout;
        timeout = com.keepassdroid.timeout.TimeoutHelper.getTimeoutLength(act);
        // We are set to never timeout
        if (timeout == (-1)) {
            return;
        }
        long diff;
        switch(MUID_STATIC) {
            // TimeoutHelper_0_BinaryMutator
            case 83: {
                diff = cur_time + timeout_start;
                break;
            }
            default: {
            diff = cur_time - timeout_start;
            break;
        }
    }
    if (diff >= timeout) {
        // We have timed out
        com.keepassdroid.app.App.setShutdown();
    }
}


public static void checkShutdown(android.app.Activity act) {
    if (com.keepassdroid.app.App.isShutdown() && com.keepassdroid.app.App.getDB().Loaded()) {
        act.setResult(com.android.keepass.KeePass.EXIT_LOCK);
        act.finish();
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
