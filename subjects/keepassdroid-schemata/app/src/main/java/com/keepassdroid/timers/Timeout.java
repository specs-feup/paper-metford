/* Copyright 2012-2019 Brian Pellin.

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
package com.keepassdroid.timers;
import android.content.SharedPreferences;
import com.android.keepass.R;
import android.util.Log;
import com.keepassdroid.intents.Intents;
import android.os.Build;
import android.app.AlarmManager;
import android.content.Intent;
import android.app.PendingIntent;
import android.preference.PreferenceManager;
import com.android.keepass.KeePass;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Timeout {
    static final int MUID_STATIC = getMUID();
    private static final int REQUEST_ID = 0;

    private static final long DEFAULT_TIMEOUT = (5 * 60) * 1000;// 5 minutes


    private static java.lang.String TAG = "KeePass Timeout";

    private static android.app.PendingIntent buildIntent(android.content.Context ctx) {
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // Timeout_0_NullIntentOperatorMutator
            case 51: {
                intent = null;
                break;
            }
            // Timeout_1_InvalidKeyIntentOperatorMutator
            case 1051: {
                intent = new android.content.Intent((String) null);
                break;
            }
            // Timeout_2_RandomActionIntentDefinitionOperatorMutator
            case 2051: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(com.keepassdroid.intents.Intents.TIMEOUT);
            break;
        }
    }
    switch(MUID_STATIC) {
        // Timeout_3_RandomActionIntentDefinitionOperatorMutator
        case 3051: {
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
        intent.setPackage("com.android.keepass");
        break;
    }
}
int flags;
flags = android.app.PendingIntent.FLAG_CANCEL_CURRENT;
if (android.os.Build.VERSION.SDK_INT >= 23) {
    flags |= android.app.PendingIntent.FLAG_IMMUTABLE;
}
android.app.PendingIntent sender;
sender = android.app.PendingIntent.getBroadcast(ctx, com.keepassdroid.timers.Timeout.REQUEST_ID, intent, flags);
return sender;
}


public static void start(android.content.Context ctx) {
android.content.SharedPreferences prefs;
prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
java.lang.String sTimeout;
sTimeout = prefs.getString(ctx.getString(com.android.keepass.R.string.app_timeout_key), ctx.getString(com.android.keepass.R.string.clipboard_timeout_default));
long timeout;
try {
    timeout = java.lang.Long.parseLong(sTimeout);
} catch (java.lang.NumberFormatException e) {
    timeout = com.keepassdroid.timers.Timeout.DEFAULT_TIMEOUT;
}
if (timeout == (-1)) {
    // No timeout don't start timeout service
    return;
}
long triggerTime;
switch(MUID_STATIC) {
    // Timeout_4_BinaryMutator
    case 4051: {
        triggerTime = java.lang.System.currentTimeMillis() - timeout;
        break;
    }
    default: {
    triggerTime = java.lang.System.currentTimeMillis() + timeout;
    break;
}
}
android.app.AlarmManager am;
am = ((android.app.AlarmManager) (ctx.getSystemService(android.content.Context.ALARM_SERVICE)));
android.util.Log.d(com.keepassdroid.timers.Timeout.TAG, "Timeout start");
am.set(android.app.AlarmManager.RTC, triggerTime, com.keepassdroid.timers.Timeout.buildIntent(ctx));
}


public static void cancel(android.content.Context ctx) {
android.app.AlarmManager am;
am = ((android.app.AlarmManager) (ctx.getSystemService(android.content.Context.ALARM_SERVICE)));
android.util.Log.d(com.keepassdroid.timers.Timeout.TAG, "Timeout cancel");
am.cancel(com.keepassdroid.timers.Timeout.buildIntent(ctx));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
