/* Copyright 2018 Brian Pellin.

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
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.app.Notification;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NotificationUtil {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String COPY_CHANNEL_ID = "copy";

    public static final java.lang.String COPY_CHANNEL_NAME = "Copy username and password";

    public static void createChannels(android.content.Context ctx) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel;
            channel = new android.app.NotificationChannel(com.keepassdroid.utils.NotificationUtil.COPY_CHANNEL_ID, com.keepassdroid.utils.NotificationUtil.COPY_CHANNEL_NAME, android.app.NotificationManager.IMPORTANCE_LOW);
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setLockscreenVisibility(android.app.Notification.VISIBILITY_PRIVATE);
            android.app.NotificationManager manager;
            manager = ((android.app.NotificationManager) (ctx.getSystemService(android.content.Context.NOTIFICATION_SERVICE)));
            if (manager == null)
                return;

            manager.createNotificationChannel(channel);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
