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
package it.feio.android.omninotes.helpers.notifications;
import it.feio.android.omninotes.OmniNotes;
import it.feio.android.omninotes.R;
import static it.feio.android.omninotes.utils.Constants.CHANNEL_BACKUPS_ID;
import android.app.NotificationManager;
import static it.feio.android.omninotes.utils.Constants.CHANNEL_REMINDERS_ID;
import android.os.Build;
import static it.feio.android.omninotes.utils.Constants.CHANNEL_PINNED_ID;
import java.util.EnumMap;
import java.util.Map;
import android.annotation.TargetApi;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@android.annotation.TargetApi(android.os.Build.VERSION_CODES.O)
public class NotificationChannels {
    static final int MUID_STATIC = getMUID();
    protected static java.util.Map<it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames, it.feio.android.omninotes.helpers.notifications.NotificationChannel> channels;

    static {
        it.feio.android.omninotes.helpers.notifications.NotificationChannels.channels = new java.util.EnumMap<>(it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames.class);
        it.feio.android.omninotes.helpers.notifications.NotificationChannels.channels.put(it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames.BACKUPS, new it.feio.android.omninotes.helpers.notifications.NotificationChannel(android.app.NotificationManager.IMPORTANCE_DEFAULT, it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.channel_backups_name), it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.channel_backups_description), it.feio.android.omninotes.utils.Constants.CHANNEL_BACKUPS_ID));
        it.feio.android.omninotes.helpers.notifications.NotificationChannels.channels.put(it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames.REMINDERS, new it.feio.android.omninotes.helpers.notifications.NotificationChannel(android.app.NotificationManager.IMPORTANCE_DEFAULT, it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.channel_reminders_name), it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.channel_reminders_description), it.feio.android.omninotes.utils.Constants.CHANNEL_REMINDERS_ID));
        it.feio.android.omninotes.helpers.notifications.NotificationChannels.channels.put(it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames.PINNED, new it.feio.android.omninotes.helpers.notifications.NotificationChannel(android.app.NotificationManager.IMPORTANCE_DEFAULT, it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.channel_pinned_name), it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.channel_pinned_description), it.feio.android.omninotes.utils.Constants.CHANNEL_PINNED_ID));
    }

    public enum NotificationChannelNames {

        BACKUPS,
        REMINDERS,
        PINNED;}

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
