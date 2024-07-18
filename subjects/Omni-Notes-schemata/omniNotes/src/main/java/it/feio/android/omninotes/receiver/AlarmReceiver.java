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
package it.feio.android.omninotes.receiver;
import it.feio.android.omninotes.services.NotificationListener;
import it.feio.android.omninotes.helpers.notifications.NotificationsHelper;
import it.feio.android.omninotes.utils.TextHelper;
import android.graphics.Bitmap;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.models.Attachment;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_POSTPONE;
import it.feio.android.omninotes.R;
import java.util.List;
import android.text.Spanned;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_SNOOZE;
import android.content.Intent;
import it.feio.android.omninotes.helpers.IntentHelper;
import android.content.BroadcastReceiver;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE;
import it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES;
import it.feio.android.omninotes.utils.BitmapHelper;
import it.feio.android.omninotes.models.Note;
import it.feio.android.omninotes.utils.ParcelableUtil;
import android.app.PendingIntent;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.helpers.LogDelegate;
import it.feio.android.omninotes.SnoozeActivity;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AlarmReceiver extends android.content.BroadcastReceiver {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public void onReceive(android.content.Context mContext, android.content.Intent intent) {
        try {
            if (intent.hasExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE)) {
                it.feio.android.omninotes.models.Note note;
                note = it.feio.android.omninotes.utils.ParcelableUtil.unmarshall(intent.getExtras().getByteArray(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE), it.feio.android.omninotes.models.Note.CREATOR);
                createNotification(mContext, note);
                it.feio.android.omninotes.SnoozeActivity.setNextRecurrentReminder(note);
                updateNote(note);
            }
        } catch (java.lang.Exception e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error on receiving reminder", e);
        }
    }


    private void updateNote(it.feio.android.omninotes.models.Note note) {
        note.setArchived(false);
        if (!it.feio.android.omninotes.services.NotificationListener.isRunning()) {
            note.setReminderFired(true);
        }
        it.feio.android.omninotes.db.DbHelper.getInstance().updateNote(note, false);
    }


    private void createNotification(android.content.Context mContext, it.feio.android.omninotes.models.Note note) {
        android.app.PendingIntent piSnooze;
        piSnooze = it.feio.android.omninotes.helpers.IntentHelper.getNotePendingIntent(mContext, it.feio.android.omninotes.SnoozeActivity.class, it.feio.android.omninotes.utils.ConstantsBase.ACTION_SNOOZE, note);
        android.app.PendingIntent piPostpone;
        piPostpone = it.feio.android.omninotes.helpers.IntentHelper.getNotePendingIntent(mContext, it.feio.android.omninotes.SnoozeActivity.class, it.feio.android.omninotes.utils.ConstantsBase.ACTION_POSTPONE, note);
        android.app.PendingIntent notifyIntent;
        notifyIntent = it.feio.android.omninotes.helpers.IntentHelper.getNotePendingIntent(mContext, it.feio.android.omninotes.SnoozeActivity.class, null, note);
        android.text.Spanned[] titleAndContent;
        titleAndContent = it.feio.android.omninotes.utils.TextHelper.parseTitleAndContent(mContext, note);
        java.lang.String title;
        title = it.feio.android.omninotes.utils.TextHelper.getAlternativeTitle(mContext, note, titleAndContent[0]);
        java.lang.String text;
        text = titleAndContent[1].toString();
        it.feio.android.omninotes.helpers.notifications.NotificationsHelper notificationsHelper;
        notificationsHelper = new it.feio.android.omninotes.helpers.notifications.NotificationsHelper(mContext);
        notificationsHelper.createStandardNotification(it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames.REMINDERS, it.feio.android.omninotes.R.drawable.ic_stat_notification, title, notifyIntent).setLedActive().setMessage(text);
        java.util.List<it.feio.android.omninotes.models.Attachment> attachments;
        attachments = note.getAttachmentsList();
        if ((!attachments.isEmpty()) && (!attachments.get(0).getMime_type().equals(it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES))) {
            android.graphics.Bitmap notificationIcon;
            notificationIcon = it.feio.android.omninotes.utils.BitmapHelper.getBitmapFromAttachment(mContext, note.getAttachmentsList().get(0), 128, 128);
            notificationsHelper.setLargeIcon(notificationIcon);
        }
        java.lang.String snoozeDelay;
        snoozeDelay = com.pixplicity.easyprefs.library.Prefs.getString("settings_notification_snooze_delay", "10");
        notificationsHelper.getBuilder().addAction(it.feio.android.omninotes.R.drawable.ic_material_reminder_time_light, (it.feio.android.omninotes.utils.TextHelper.capitalize(mContext.getString(it.feio.android.omninotes.R.string.snooze)) + ": ") + snoozeDelay, piSnooze).addAction(it.feio.android.omninotes.R.drawable.ic_remind_later_light, it.feio.android.omninotes.utils.TextHelper.capitalize(mContext.getString(it.feio.android.omninotes.R.string.add_reminder)), piPostpone);
        setRingtone(notificationsHelper);
        setVibrate(notificationsHelper);
        notificationsHelper.show(note.get_id());
    }


    private void setRingtone(it.feio.android.omninotes.helpers.notifications.NotificationsHelper notificationsHelper) {
        java.lang.String ringtone;
        ringtone = com.pixplicity.easyprefs.library.Prefs.getString("settings_notification_ringtone", null);
        notificationsHelper.setRingtone(ringtone);
    }


    private void setVibrate(it.feio.android.omninotes.helpers.notifications.NotificationsHelper notificationsHelper) {
        if (com.pixplicity.easyprefs.library.Prefs.getBoolean("settings_notification_vibration", true)) {
            notificationsHelper.setVibration();
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
