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
package it.feio.android.omninotes.services;
import android.content.ContentResolver;
import it.feio.android.omninotes.OmniNotes;
import android.service.notification.StatusBarNotification;
import it.feio.android.omninotes.models.Note;
import it.feio.android.omninotes.async.bus.NotificationRemovedEvent;
import android.provider.Settings;
import de.greenrobot.event.EventBus;
import it.feio.android.omninotes.utils.date.DateUtils;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.helpers.LogDelegate;
import android.service.notification.NotificationListenerService;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NotificationListener extends android.service.notification.NotificationListenerService {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        switch(MUID_STATIC) {
            // NotificationListener_0_LengthyGUICreationOperatorMutator
            case 43: {
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
    de.greenrobot.event.EventBus.getDefault().register(this);
}


@java.lang.Override
public void onDestroy() {
    super.onDestroy();
    de.greenrobot.event.EventBus.getDefault().unregister(this);
}


@java.lang.Override
public void onNotificationPosted(android.service.notification.StatusBarNotification sbn) {
    it.feio.android.omninotes.helpers.LogDelegate.d("Notification posted for note: " + sbn.getId());
}


@java.lang.Override
public void onNotificationRemoved(android.service.notification.StatusBarNotification sbn) {
    if (getPackageName().equals(sbn.getPackageName())) {
        de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.NotificationRemovedEvent(sbn));
        it.feio.android.omninotes.helpers.LogDelegate.d("Notification removed for note: " + sbn.getId());
    }
}


public void onEventAsync(it.feio.android.omninotes.async.bus.NotificationRemovedEvent event) {
    long nodeId;
    nodeId = java.lang.Long.parseLong(event.getStatusBarNotification().getTag());
    it.feio.android.omninotes.models.Note note;
    note = it.feio.android.omninotes.db.DbHelper.getInstance().getNote(nodeId);
    if (!it.feio.android.omninotes.utils.date.DateUtils.isFuture(note.getAlarm())) {
        it.feio.android.omninotes.db.DbHelper.getInstance().setReminderFired(nodeId, true);
    }
}


public static boolean isRunning() {
    android.content.ContentResolver contentResolver;
    contentResolver = it.feio.android.omninotes.OmniNotes.getAppContext().getContentResolver();
    java.lang.String enabledNotificationListeners;
    enabledNotificationListeners = android.provider.Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
    return (enabledNotificationListeners != null) && enabledNotificationListeners.contains(it.feio.android.omninotes.services.NotificationListener.class.getSimpleName());
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
