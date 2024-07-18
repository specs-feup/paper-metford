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
import android.app.NotificationChannel;
import com.amaze.filemanager.R;
import android.app.NotificationManager;
import android.os.Build;
import android.app.Notification;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 17/9/2017, at 13:34.
 */
public class NotificationConstants {
    static final int MUID_STATIC = getMUID();
    public static final int COPY_ID = 0;

    public static final int EXTRACT_ID = 1;

    public static final int ZIP_ID = 2;

    public static final int DECRYPT_ID = 3;

    public static final int ENCRYPT_ID = 4;

    public static final int FTP_ID = 5;

    public static final int FAILED_ID = 6;

    public static final int WAIT_ID = 7;

    public static final int TYPE_NORMAL = 0;

    public static final int TYPE_FTP = 1;

    public static final java.lang.String CHANNEL_NORMAL_ID = "normalChannel";

    public static final java.lang.String CHANNEL_FTP_ID = "ftpChannel";

    /**
     * This creates a channel (API >= 26) or applies the correct metadata to a notification (API < 26)
     */
    public static void setMetadata(android.content.Context context, androidx.core.app.NotificationCompat.Builder notification, int type) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            switch (type) {
                case com.amaze.filemanager.ui.notifications.NotificationConstants.TYPE_NORMAL :
                    com.amaze.filemanager.ui.notifications.NotificationConstants.createNormalChannel(context);
                    break;
                case com.amaze.filemanager.ui.notifications.NotificationConstants.TYPE_FTP :
                    com.amaze.filemanager.ui.notifications.NotificationConstants.createFtpChannel(context);
                    break;
                default :
                    throw new java.lang.IllegalArgumentException("Unrecognized type:" + type);
            }
        } else {
            switch (type) {
                case com.amaze.filemanager.ui.notifications.NotificationConstants.TYPE_NORMAL :
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        notification.setCategory(android.app.Notification.CATEGORY_SERVICE);
                    }
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        notification.setPriority(android.app.Notification.PRIORITY_MIN);
                    }
                    break;
                case com.amaze.filemanager.ui.notifications.NotificationConstants.TYPE_FTP :
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        notification.setCategory(android.app.Notification.CATEGORY_SERVICE);
                        notification.setVisibility(androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC);
                    }
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        notification.setPriority(android.app.Notification.PRIORITY_MAX);
                    }
                    break;
                default :
                    throw new java.lang.IllegalArgumentException("Unrecognized type:" + type);
            }
        }
    }


    /**
     * You CANNOT call this from android < O. THis channel is set so it doesn't bother the user, but
     * it has importance.
     */
    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.O)
    private static void createFtpChannel(android.content.Context context) {
        android.app.NotificationManager mNotificationManager;
        mNotificationManager = ((android.app.NotificationManager) (context.getSystemService(android.content.Context.NOTIFICATION_SERVICE)));
        if (mNotificationManager.getNotificationChannel(com.amaze.filemanager.ui.notifications.NotificationConstants.CHANNEL_FTP_ID) == null) {
            android.app.NotificationChannel mChannel;
            mChannel = new android.app.NotificationChannel(com.amaze.filemanager.ui.notifications.NotificationConstants.CHANNEL_FTP_ID, context.getString(com.amaze.filemanager.R.string.channel_name_ftp), android.app.NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            mChannel.setDescription(context.getString(com.amaze.filemanager.R.string.channel_description_ftp));
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }


    /**
     * You CANNOT call this from android < O. THis channel is set so it doesn't bother the user, with
     * the lowest importance.
     */
    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.O)
    private static void createNormalChannel(android.content.Context context) {
        android.app.NotificationManager mNotificationManager;
        mNotificationManager = ((android.app.NotificationManager) (context.getSystemService(android.content.Context.NOTIFICATION_SERVICE)));
        if (mNotificationManager.getNotificationChannel(com.amaze.filemanager.ui.notifications.NotificationConstants.CHANNEL_NORMAL_ID) == null) {
            android.app.NotificationChannel mChannel;
            mChannel = new android.app.NotificationChannel(com.amaze.filemanager.ui.notifications.NotificationConstants.CHANNEL_NORMAL_ID, context.getString(com.amaze.filemanager.R.string.channel_name_normal), android.app.NotificationManager.IMPORTANCE_MIN);
            // Configure the notification channel.
            mChannel.setDescription(context.getString(com.amaze.filemanager.R.string.channel_description_normal));
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
