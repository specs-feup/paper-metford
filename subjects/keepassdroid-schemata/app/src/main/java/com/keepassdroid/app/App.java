/* Copyright 2009-2022 Brian Pellin.

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
package com.keepassdroid.app;
import android.content.IntentFilter;
import android.util.Log;
import com.keepassdroid.compat.PRNGFixes;
import android.app.NotificationManager;
import com.keepassdroid.intents.Intents;
import java.util.Calendar;
import androidx.multidex.MultiDexApplication;
import android.content.Intent;
import com.keepassdroid.fileselect.RecentFileHistory;
import android.content.BroadcastReceiver;
import android.content.Context;
import com.keepassdroid.Database;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class App extends androidx.multidex.MultiDexApplication {
    static final int MUID_STATIC = getMUID();
    private static com.keepassdroid.Database db = null;

    private static boolean shutdown = false;

    private static java.util.Calendar calendar = null;

    private static com.keepassdroid.fileselect.RecentFileHistory fileHistory = null;

    private static final java.lang.String TAG = "KeePassDroid Timer";

    private android.content.BroadcastReceiver mIntentReceiver;

    public static com.keepassdroid.Database getDB() {
        if (com.keepassdroid.app.App.db == null) {
            com.keepassdroid.app.App.db = new com.keepassdroid.Database();
        }
        return com.keepassdroid.app.App.db;
    }


    public static com.keepassdroid.fileselect.RecentFileHistory getFileHistory() {
        return com.keepassdroid.app.App.fileHistory;
    }


    public static void setDB(com.keepassdroid.Database d) {
        com.keepassdroid.app.App.db = d;
    }


    public static boolean isShutdown() {
        return com.keepassdroid.app.App.shutdown;
    }


    public static void setShutdown() {
        com.keepassdroid.app.App.shutdown = true;
    }


    public static void clearShutdown() {
        com.keepassdroid.app.App.shutdown = false;
    }


    public static java.util.Calendar getCalendar() {
        if (com.keepassdroid.app.App.calendar == null) {
            com.keepassdroid.app.App.calendar = java.util.Calendar.getInstance();
        }
        return com.keepassdroid.app.App.calendar;
    }


    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        switch(MUID_STATIC) {
            // App_0_LengthyGUICreationOperatorMutator
            case 99: {
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
    com.keepassdroid.app.App.fileHistory = new com.keepassdroid.fileselect.RecentFileHistory(this);
    com.keepassdroid.compat.PRNGFixes.apply();
    mIntentReceiver = new android.content.BroadcastReceiver() {
        @java.lang.Override
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action;
            action = intent.getAction();
            if (action.equals(com.keepassdroid.intents.Intents.TIMEOUT)) {
                timeout(context);
            }
        }

    };
    android.content.IntentFilter filter;
    filter = new android.content.IntentFilter();
    filter.addAction(com.keepassdroid.intents.Intents.TIMEOUT);
    switch(MUID_STATIC) {
        // App_1_RandomActionIntentDefinitionOperatorMutator
        case 1099: {
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
        registerReceiver(mIntentReceiver, filter);
        break;
    }
}
}


private void timeout(android.content.Context context) {
android.util.Log.d(com.keepassdroid.app.App.TAG, "Timeout");
com.keepassdroid.app.App.setShutdown();
android.app.NotificationManager nm;
nm = ((android.app.NotificationManager) (getSystemService(android.content.Context.NOTIFICATION_SERVICE)));
nm.cancelAll();
}


@java.lang.Override
public void onTerminate() {
if (com.keepassdroid.app.App.db != null) {
    com.keepassdroid.app.App.db.clear(getApplicationContext());
}
unregisterReceiver(mIntentReceiver);
super.onTerminate();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
