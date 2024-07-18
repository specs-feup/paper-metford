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
package it.feio.android.omninotes.async;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import it.feio.android.omninotes.helpers.BackupHelper;
import it.feio.android.omninotes.helpers.notifications.NotificationsHelper;
import it.feio.android.omninotes.MainActivity;
import android.net.Uri;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.models.Attachment;
import android.os.Build.VERSION_CODES;
import it.feio.android.omninotes.OmniNotes;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.utils.ReminderHelper;
import android.os.Build;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_BACKUP_FOLDER_URI;
import android.app.IntentService;
import it.feio.android.omninotes.helpers.SpringImportHelper;
import rx.Observable;
import android.content.Intent;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_RESTART_APP;
import it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames;
import static it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag;
import it.feio.android.omninotes.models.Note;
import com.lazygeniouz.dfc.file.DocumentFileCompat;
import it.feio.android.omninotes.utils.StorageHelper;
import android.app.PendingIntent;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.helpers.LogDelegate;
import it.feio.android.omninotes.models.listeners.OnAttachingFileListener;
import java.io.File;
import android.annotation.TargetApi;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DataBackupIntentService extends android.app.IntentService implements it.feio.android.omninotes.models.listeners.OnAttachingFileListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String INTENT_BACKUP_NAME = "backup_name";

    public static final java.lang.String ACTION_DATA_EXPORT = "action_data_export";

    public static final java.lang.String ACTION_DATA_IMPORT = "action_data_import";

    public static final java.lang.String ACTION_DATA_DELETE = "action_data_delete";

    private it.feio.android.omninotes.helpers.notifications.NotificationsHelper mNotificationsHelper;

    // {
    // File autoBackupDir = StorageHelper.getBackupDir(Constants.AUTO_BACKUP_DIR);
    // BackupHelper.exportNotes(autoBackupDir);
    // BackupHelper.exportAttachments(autoBackupDir);
    // }
    public DataBackupIntentService() {
        super("DataBackupIntentService");
    }


    @java.lang.Override
    protected void onHandleIntent(android.content.Intent intent) {
        mNotificationsHelper = new it.feio.android.omninotes.helpers.notifications.NotificationsHelper(this).start(it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames.BACKUPS, it.feio.android.omninotes.R.drawable.ic_content_save_white_24dp, getString(it.feio.android.omninotes.R.string.working));
        // If an alarm has been fired a notification must be generated
        if (it.feio.android.omninotes.async.DataBackupIntentService.ACTION_DATA_EXPORT.equals(intent.getAction())) {
            exportData(intent);
        } else if (it.feio.android.omninotes.async.DataBackupIntentService.ACTION_DATA_IMPORT.equals(intent.getAction())) {
            importData(intent);
        } else if (it.feio.android.omninotes.helpers.SpringImportHelper.ACTION_DATA_IMPORT_SPRINGPAD.equals(intent.getAction())) {
            importDataFromSpringpad(intent, mNotificationsHelper);
        } else if (it.feio.android.omninotes.async.DataBackupIntentService.ACTION_DATA_DELETE.equals(intent.getAction())) {
            deleteData(intent);
        }
    }


    private void importDataFromSpringpad(android.content.Intent intent, it.feio.android.omninotes.helpers.notifications.NotificationsHelper mNotificationsHelper) {
        new it.feio.android.omninotes.helpers.SpringImportHelper(it.feio.android.omninotes.OmniNotes.getAppContext()).importDataFromSpringpad(intent, mNotificationsHelper);
        java.lang.String title;
        title = getString(it.feio.android.omninotes.R.string.data_import_completed);
        java.lang.String text;
        text = getString(it.feio.android.omninotes.R.string.click_to_refresh_application);
        createNotification(intent, this, title, text);
    }


    private void exportData(android.content.Intent intent) {
        java.lang.String backupName;
        backupName = intent.getStringExtra(it.feio.android.omninotes.async.DataBackupIntentService.INTENT_BACKUP_NAME);
        var backupDir = com.lazygeniouz.dfc.file.DocumentFileCompat.Companion.fromTreeUri(getBaseContext(), android.net.Uri.parse(com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_BACKUP_FOLDER_URI, null))).createDirectory(backupName);
        it.feio.android.omninotes.helpers.BackupHelper.exportNotes(backupDir);
        it.feio.android.omninotes.helpers.BackupHelper.exportAttachments(backupDir, mNotificationsHelper);
        var readableBackupFolder = (it.feio.android.omninotes.helpers.BackupHelper.getBackupFolderPath() + "/") + backupName;
        mNotificationsHelper.finish(getString(it.feio.android.omninotes.R.string.data_export_completed), readableBackupFolder);
    }


    @android.annotation.TargetApi(android.os.Build.VERSION_CODES.O)
    private synchronized void importData(android.content.Intent intent) {
        var backupDir = rx.Observable.from(com.lazygeniouz.dfc.file.DocumentFileCompat.Companion.fromTreeUri(getBaseContext(), android.net.Uri.parse(com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_BACKUP_FOLDER_URI, null))).listFiles()).filter((com.lazygeniouz.dfc.file.DocumentFileCompat f) -> f.getName().equals(intent.getStringExtra(it.feio.android.omninotes.async.DataBackupIntentService.INTENT_BACKUP_NAME))).toBlocking().single();
        it.feio.android.omninotes.helpers.BackupHelper.importNotes(backupDir);
        it.feio.android.omninotes.helpers.BackupHelper.importAttachments(backupDir, mNotificationsHelper);
        resetReminders();
        mNotificationsHelper.cancel();
        createNotification(intent, this, getString(it.feio.android.omninotes.R.string.data_import_completed), getString(it.feio.android.omninotes.R.string.click_to_refresh_application));
        // Performs auto-backup filling after backup restore
        // if (Prefs.getBoolean(Constants.PREF_ENABLE_AUTOBACKUP, false)) {
        // File autoBackupDir = StorageHelper.getBackupDir(Constants.AUTO_BACKUP_DIR);
        // BackupHelper.exportNotes(autoBackupDir);
        // BackupHelper.exportAttachments(autoBackupDir);
        // }
    }


    private synchronized void deleteData(android.content.Intent intent) {
        java.lang.String backupName;
        backupName = intent.getStringExtra(it.feio.android.omninotes.async.DataBackupIntentService.INTENT_BACKUP_NAME);
        java.io.File backupDir;
        backupDir = it.feio.android.omninotes.utils.StorageHelper.getOrCreateBackupDir(backupName);
        it.feio.android.omninotes.utils.StorageHelper.delete(this, backupDir.getAbsolutePath());
        mNotificationsHelper.finish(getString(it.feio.android.omninotes.R.string.data_deletion_completed), (backupName + " ") + getString(it.feio.android.omninotes.R.string.deleted));
    }


    private void createNotification(android.content.Intent intent, android.content.Context context, java.lang.String title, java.lang.String message) {
        android.content.Intent intentLaunch;
        if (it.feio.android.omninotes.async.DataBackupIntentService.ACTION_DATA_IMPORT.equals(intent.getAction()) || it.feio.android.omninotes.helpers.SpringImportHelper.ACTION_DATA_IMPORT_SPRINGPAD.equals(intent.getAction())) {
            switch(MUID_STATIC) {
                // DataBackupIntentService_0_NullIntentOperatorMutator
                case 32: {
                    intentLaunch = null;
                    break;
                }
                // DataBackupIntentService_1_InvalidKeyIntentOperatorMutator
                case 1032: {
                    intentLaunch = new android.content.Intent((Context) null, it.feio.android.omninotes.MainActivity.class);
                    break;
                }
                // DataBackupIntentService_2_RandomActionIntentDefinitionOperatorMutator
                case 2032: {
                    intentLaunch = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intentLaunch = new android.content.Intent(context, it.feio.android.omninotes.MainActivity.class);
                break;
            }
        }
        switch(MUID_STATIC) {
            // DataBackupIntentService_3_RandomActionIntentDefinitionOperatorMutator
            case 3032: {
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
            intentLaunch.setAction(it.feio.android.omninotes.utils.ConstantsBase.ACTION_RESTART_APP);
            break;
        }
    }
} else {
    switch(MUID_STATIC) {
        // DataBackupIntentService_4_NullIntentOperatorMutator
        case 4032: {
            intentLaunch = null;
            break;
        }
        // DataBackupIntentService_5_RandomActionIntentDefinitionOperatorMutator
        case 5032: {
            intentLaunch = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intentLaunch = new android.content.Intent();
        break;
    }
}
}
switch(MUID_STATIC) {
// DataBackupIntentService_6_RandomActionIntentDefinitionOperatorMutator
case 6032: {
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
// Add this bundle to the intent
intentLaunch.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
break;
}
}
switch(MUID_STATIC) {
// DataBackupIntentService_7_RandomActionIntentDefinitionOperatorMutator
case 7032: {
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
intentLaunch.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
break;
}
}
android.app.PendingIntent notifyIntent;
notifyIntent = android.app.PendingIntent.getActivity(context, 0, intentLaunch, it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag(android.app.PendingIntent.FLAG_UPDATE_CURRENT));
it.feio.android.omninotes.helpers.notifications.NotificationsHelper notificationsHelper;
notificationsHelper = new it.feio.android.omninotes.helpers.notifications.NotificationsHelper(context);
notificationsHelper.createStandardNotification(it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames.BACKUPS, it.feio.android.omninotes.R.drawable.ic_content_save_white_24dp, title, notifyIntent).setMessage(message).setRingtone(com.pixplicity.easyprefs.library.Prefs.getString("settings_notification_ringtone", null)).setLedActive();
if (com.pixplicity.easyprefs.library.Prefs.getBoolean("settings_notification_vibration", true)) {
notificationsHelper.setVibration();
}
notificationsHelper.show();
}


/**
 * Schedules reminders
 */
private void resetReminders() {
it.feio.android.omninotes.helpers.LogDelegate.d("Resetting reminders");
for (it.feio.android.omninotes.models.Note note : it.feio.android.omninotes.db.DbHelper.getInstance().getNotesWithReminderNotFired()) {
it.feio.android.omninotes.utils.ReminderHelper.addReminder(it.feio.android.omninotes.OmniNotes.getAppContext(), note);
}
}


@java.lang.Override
public void onAttachingFileErrorOccurred(it.feio.android.omninotes.models.Attachment mAttachment) {
// TODO Auto-generated method stub
}


@java.lang.Override
public void onAttachingFileFinished(it.feio.android.omninotes.models.Attachment mAttachment) {
// TODO Auto-generated method stub
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
