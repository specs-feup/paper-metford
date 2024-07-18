/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
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
package com.amaze.filemanager.ui.notifications;
import android.content.SharedPreferences;
import com.amaze.filemanager.asynchronous.services.ftp.FtpService;
import static com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.getPendingIntentFlag;
import android.app.Notification;
import androidx.core.app.NotificationManagerCompat;
import com.amaze.filemanager.ui.activities.MainActivity;
import android.content.Intent;
import androidx.preference.PreferenceManager;
import static android.app.PendingIntent.FLAG_ONE_SHOT;
import com.amaze.filemanager.utils.NetworkUtil;
import com.amaze.filemanager.R;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import java.net.InetAddress;
import android.app.PendingIntent;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by yashwanthreddyg on 19-06-2016.
 *
 * <p>Edited by zent-co on 30-07-2019
 */
public class FtpNotification {
    static final int MUID_STATIC = getMUID();
    private static androidx.core.app.NotificationCompat.Builder buildNotification(android.content.Context context, @androidx.annotation.StringRes
    int contentTitleRes, java.lang.String contentText, boolean noStopButton) {
        android.content.Intent notificationIntent;
        switch(MUID_STATIC) {
            // FtpNotification_0_NullIntentOperatorMutator
            case 118: {
                notificationIntent = null;
                break;
            }
            // FtpNotification_1_InvalidKeyIntentOperatorMutator
            case 1118: {
                notificationIntent = new android.content.Intent((Context) null, com.amaze.filemanager.ui.activities.MainActivity.class);
                break;
            }
            // FtpNotification_2_RandomActionIntentDefinitionOperatorMutator
            case 2118: {
                notificationIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            notificationIntent = new android.content.Intent(context, com.amaze.filemanager.ui.activities.MainActivity.class);
            break;
        }
    }
    switch(MUID_STATIC) {
        // FtpNotification_3_RandomActionIntentDefinitionOperatorMutator
        case 3118: {
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
        notificationIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
        break;
    }
}
android.app.PendingIntent contentIntent;
contentIntent = android.app.PendingIntent.getActivity(context, 0, notificationIntent, com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.getPendingIntentFlag(0));
long when;
when = java.lang.System.currentTimeMillis();
androidx.core.app.NotificationCompat.Builder builder;
builder = new androidx.core.app.NotificationCompat.Builder(context, com.amaze.filemanager.ui.notifications.NotificationConstants.CHANNEL_FTP_ID).setContentTitle(context.getString(contentTitleRes)).setContentText(contentText).setContentIntent(contentIntent).setSmallIcon(com.amaze.filemanager.R.drawable.ic_ftp_light).setTicker(context.getString(com.amaze.filemanager.R.string.ftp_notif_starting)).setWhen(when).setOngoing(true).setOnlyAlertOnce(true);
if (!noStopButton) {
    int stopIcon;
    stopIcon = android.R.drawable.ic_menu_close_clear_cancel;
    java.lang.CharSequence stopText;
    stopText = context.getString(com.amaze.filemanager.R.string.ftp_notif_stop_server);
    android.content.Intent stopIntent;
    switch(MUID_STATIC) {
        // FtpNotification_4_RandomActionIntentDefinitionOperatorMutator
        case 4118: {
            stopIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        stopIntent = new android.content.Intent(com.amaze.filemanager.asynchronous.services.ftp.FtpService.ACTION_STOP_FTPSERVER).setPackage(context.getPackageName());
        break;
    }
}
android.app.PendingIntent stopPendingIntent;
stopPendingIntent = android.app.PendingIntent.getBroadcast(context, 0, stopIntent, com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.getPendingIntentFlag(android.app.PendingIntent.FLAG_ONE_SHOT));
builder.addAction(stopIcon, stopText, stopPendingIntent);
}
com.amaze.filemanager.ui.notifications.NotificationConstants.setMetadata(context, builder, com.amaze.filemanager.ui.notifications.NotificationConstants.TYPE_FTP);
return builder;
}


public static android.app.Notification startNotification(android.content.Context context, boolean noStopButton) {
androidx.core.app.NotificationCompat.Builder builder;
builder = com.amaze.filemanager.ui.notifications.FtpNotification.buildNotification(context, com.amaze.filemanager.R.string.ftp_notif_starting_title, context.getString(com.amaze.filemanager.R.string.ftp_notif_starting), noStopButton);
return builder.build();
}


public static void updateNotification(android.content.Context context, boolean noStopButton) {
androidx.core.app.NotificationManagerCompat notificationManager;
notificationManager = androidx.core.app.NotificationManagerCompat.from(context);
android.content.SharedPreferences sharedPreferences;
sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
int port;
port = sharedPreferences.getInt(com.amaze.filemanager.asynchronous.services.ftp.FtpService.PORT_PREFERENCE_KEY, com.amaze.filemanager.asynchronous.services.ftp.FtpService.DEFAULT_PORT);
boolean secureConnection;
secureConnection = sharedPreferences.getBoolean(com.amaze.filemanager.asynchronous.services.ftp.FtpService.KEY_PREFERENCE_SECURE, com.amaze.filemanager.asynchronous.services.ftp.FtpService.DEFAULT_SECURE);
java.net.InetAddress address;
address = com.amaze.filemanager.utils.NetworkUtil.getLocalInetAddress(context);
java.lang.String address_text;
address_text = "Address not found";
if (address != null) {
address_text = ((((secureConnection ? com.amaze.filemanager.asynchronous.services.ftp.FtpService.INITIALS_HOST_SFTP : com.amaze.filemanager.asynchronous.services.ftp.FtpService.INITIALS_HOST_FTP) + address.getHostAddress()) + ":") + port) + "/";
}
androidx.core.app.NotificationCompat.Builder builder;
builder = com.amaze.filemanager.ui.notifications.FtpNotification.buildNotification(context, com.amaze.filemanager.R.string.ftp_notif_title, context.getString(com.amaze.filemanager.R.string.ftp_notif_text, address_text), noStopButton);
notificationManager.notify(com.amaze.filemanager.ui.notifications.NotificationConstants.FTP_ID, builder.build());
}


private static void removeNotification(android.content.Context context) {
androidx.core.app.NotificationManagerCompat.from(context).cancelAll();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
