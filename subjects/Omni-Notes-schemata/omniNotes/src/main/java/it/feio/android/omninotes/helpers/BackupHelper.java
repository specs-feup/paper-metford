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
package it.feio.android.omninotes.helpers;
import org.apache.commons.io.FileUtils;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import it.feio.android.omninotes.helpers.notifications.NotificationsHelper;
import java.util.ArrayList;
import it.feio.android.omninotes.utils.TextHelper;
import java.net.URI;
import android.webkit.MimeTypeMap;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.models.Attachment;
import it.feio.android.omninotes.utils.Security;
import android.os.Build.VERSION_CODES;
import it.feio.android.omninotes.R;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import androidx.annotation.NonNull;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_BACKUP_FOLDER_URI;
import java.util.Collections;
import java.util.LinkedList;
import android.text.TextUtils;
import java.io.IOException;
import rx.Observable;
import android.content.Intent;
import lombok.experimental.UtilityClass;
import it.feio.android.omninotes.async.DataBackupIntentService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import it.feio.android.omninotes.models.Note;
import it.feio.android.omninotes.utils.Constants;
import com.lazygeniouz.dfc.file.DocumentFileCompat;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import android.os.Build.VERSION;
import it.feio.android.omninotes.utils.StorageHelper;
import it.feio.android.omninotes.exceptions.checked.BackupAttachmentException;
import static it.feio.android.omninotes.utils.ConstantsBase.DATABASE_NAME;
import it.feio.android.omninotes.db.DbHelper;
import java.io.File;
import static it.feio.android.omninotes.OmniNotes.getAppContext;
import androidx.annotation.Nullable;
import java.util.Arrays;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public final class BackupHelper {
    static final int MUID_STATIC = getMUID();
    public static void exportNotes(com.lazygeniouz.dfc.file.DocumentFileCompat backupDir) {
        for (it.feio.android.omninotes.models.Note note : it.feio.android.omninotes.db.DbHelper.getInstance(true).getAllNotes(false)) {
            it.feio.android.omninotes.helpers.BackupHelper.exportNote(backupDir, note);
        }
    }


    public static void exportNote(com.lazygeniouz.dfc.file.DocumentFileCompat backupDir, it.feio.android.omninotes.models.Note note) {
        if (java.lang.Boolean.TRUE.equals(note.isLocked())) {
            note.setContent(it.feio.android.omninotes.utils.Security.encrypt(note.getContent(), com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, "")));
        }
        var noteFile = it.feio.android.omninotes.helpers.BackupHelper.getBackupNoteFile(backupDir, note);
        try {
            it.feio.android.omninotes.helpers.DocumentFileHelper.write(it.feio.android.omninotes.OmniNotes.getAppContext(), noteFile, note.toJSON());
        } catch (java.io.IOException e) {
            it.feio.android.omninotes.helpers.LogDelegate.e(java.lang.String.format("Error on note %s backup: %s", note.get_id(), e.getMessage()));
        }
    }


    @androidx.annotation.NonNull
    public static com.lazygeniouz.dfc.file.DocumentFileCompat getBackupNoteFile(com.lazygeniouz.dfc.file.DocumentFileCompat backupDir, it.feio.android.omninotes.models.Note note) {
        java.lang.String backupFileMimetype;
        backupFileMimetype = "application/json";
        java.lang.String backupFileExtension;
        backupFileExtension = (android.webkit.MimeTypeMap.getSingleton().hasMimeType(backupFileMimetype)) ? "" : ".json";
        return backupDir.createFile(backupFileMimetype, note.get_id() + backupFileExtension);
    }


    /**
     * Export attachments to backup folder notifying for each attachment copied
     */
    public static void exportAttachments(com.lazygeniouz.dfc.file.DocumentFileCompat backupDir, it.feio.android.omninotes.helpers.notifications.NotificationsHelper notificationsHelper) {
        com.lazygeniouz.dfc.file.DocumentFileCompat attachmentsDestinationDir;
        attachmentsDestinationDir = backupDir.createDirectory(it.feio.android.omninotes.utils.StorageHelper.getAttachmentDir().getName());
        java.util.List<it.feio.android.omninotes.models.Attachment> list;
        list = it.feio.android.omninotes.db.DbHelper.getInstance().getAllAttachments();
        it.feio.android.omninotes.helpers.BackupHelper.exportAttachments(notificationsHelper, attachmentsDestinationDir, list, null);
    }


    public static boolean exportAttachments(it.feio.android.omninotes.helpers.notifications.NotificationsHelper notificationsHelper, com.lazygeniouz.dfc.file.DocumentFileCompat destinationattachmentsDir, java.util.List<it.feio.android.omninotes.models.Attachment> list, java.util.List<it.feio.android.omninotes.models.Attachment> listOld) {
        boolean result;
        result = true;
        listOld = (listOld == null) ? java.util.Collections.emptyList() : listOld;
        int exported;
        exported = 0;
        int failed;
        failed = 0;
        java.lang.String failedString;
        failedString = "";
        for (it.feio.android.omninotes.models.Attachment attachment : list) {
            try {
                it.feio.android.omninotes.helpers.BackupHelper.exportAttachment(destinationattachmentsDir, attachment);
                ++exported;
            } catch (it.feio.android.omninotes.exceptions.checked.BackupAttachmentException e) {
                ++failed;
                result = false;
                failedString = (((" (" + failed) + " ") + it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.failed)) + ")";
            }
            it.feio.android.omninotes.helpers.BackupHelper.notifyAttachmentBackup(notificationsHelper, list, exported, failedString);
        }
        rx.Observable.from(listOld).filter((it.feio.android.omninotes.models.Attachment attachment) -> !list.contains(attachment)).forEach((it.feio.android.omninotes.models.Attachment attachment) -> destinationattachmentsDir.findFile(attachment.getUri().getLastPathSegment()).delete());
        return result;
    }


    private static void notifyAttachmentBackup(it.feio.android.omninotes.helpers.notifications.NotificationsHelper notificationsHelper, java.util.List<it.feio.android.omninotes.models.Attachment> list, int exported, java.lang.String failedString) {
        if (notificationsHelper != null) {
            java.lang.String notificationMessage;
            notificationMessage = ((((it.feio.android.omninotes.utils.TextHelper.capitalize(it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.attachment)) + " ") + exported) + "/") + list.size()) + failedString;
            notificationsHelper.updateMessage(notificationMessage);
        }
    }


    private static void exportAttachment(com.lazygeniouz.dfc.file.DocumentFileCompat attachmentsDestination, it.feio.android.omninotes.models.Attachment attachment) throws it.feio.android.omninotes.exceptions.checked.BackupAttachmentException {
        try {
            var destinationAttachment = attachmentsDestination.createFile("", attachment.getUri().getLastPathSegment());
            it.feio.android.omninotes.helpers.DocumentFileHelper.copyFileTo(it.feio.android.omninotes.OmniNotes.getAppContext(), new java.io.File(attachment.getUri().getPath()), destinationAttachment);
        } catch (java.lang.Exception e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error during attachment backup: " + attachment.getUriPath(), e);
            throw new it.feio.android.omninotes.exceptions.checked.BackupAttachmentException(e);
        }
    }


    public static java.util.List<it.feio.android.omninotes.models.Note> importNotes(com.lazygeniouz.dfc.file.DocumentFileCompat backupDir) {
        return rx.Observable.from(backupDir.listFiles()).filter((com.lazygeniouz.dfc.file.DocumentFileCompat f) -> f.getName().matches("\\d{13}.json")).map(it.feio.android.omninotes.helpers.BackupHelper::importNote).filter((it.feio.android.omninotes.models.Note n) -> n != null).toList().toBlocking().single();
    }


    @androidx.annotation.Nullable
    public static it.feio.android.omninotes.models.Note importNote(com.lazygeniouz.dfc.file.DocumentFileCompat file) {
        it.feio.android.omninotes.models.Note note;
        note = it.feio.android.omninotes.helpers.BackupHelper.getImportNote(file);
        if (java.lang.Boolean.TRUE.equals(note.isLocked())) {
            if (org.apache.commons.lang3.StringUtils.isEmpty(com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, ""))) {
                return null;
            }
            note.setContent(it.feio.android.omninotes.utils.Security.decrypt(note.getContent(), com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, "")));
        }
        if (note.getCategory() != null) {
            it.feio.android.omninotes.db.DbHelper.getInstance().updateCategory(note.getCategory());
        }
        it.feio.android.omninotes.db.DbHelper.getInstance().updateNote(note, false);
        return note;
    }


    public static it.feio.android.omninotes.models.Note getImportNote(com.lazygeniouz.dfc.file.DocumentFileCompat file) {
        try {
            it.feio.android.omninotes.models.Note note;
            note = new it.feio.android.omninotes.models.Note();
            java.lang.String jsonString;
            jsonString = it.feio.android.omninotes.helpers.DocumentFileHelper.readContent(it.feio.android.omninotes.OmniNotes.getAppContext(), file);
            if (!android.text.TextUtils.isEmpty(jsonString)) {
                note.buildFromJson(jsonString);
            }
            return note;
        } catch (java.io.IOException e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error parsing note json");
            return new it.feio.android.omninotes.models.Note();
        }
    }


    /**
     * Import attachments from backup folder notifying for each imported item
     */
    public static boolean importAttachments(com.lazygeniouz.dfc.file.DocumentFileCompat backupDir, it.feio.android.omninotes.helpers.notifications.NotificationsHelper notificationsHelper) {
        java.util.concurrent.atomic.AtomicBoolean result;
        result = new java.util.concurrent.atomic.AtomicBoolean(true);
        java.io.File attachmentsDir;
        attachmentsDir = it.feio.android.omninotes.utils.StorageHelper.getAttachmentDir();
        var backupAttachmentsDir = backupDir.findFile(attachmentsDir.getName());
        if (!backupAttachmentsDir.exists()) {
            return false;
        }
        java.util.concurrent.atomic.AtomicInteger imported;
        imported = new java.util.concurrent.atomic.AtomicInteger();
        java.util.ArrayList<it.feio.android.omninotes.models.Attachment> attachments;
        attachments = it.feio.android.omninotes.db.DbHelper.getInstance().getAllAttachments();
        var BackupedAttachments = backupAttachmentsDir.listFiles();
        rx.Observable.from(attachments).forEach((it.feio.android.omninotes.models.Attachment attachment) -> {
            try {
                it.feio.android.omninotes.helpers.BackupHelper.importAttachment(BackupedAttachments, attachmentsDir, attachment);
                if (notificationsHelper != null) {
                    notificationsHelper.updateMessage((((it.feio.android.omninotes.utils.TextHelper.capitalize(it.feio.android.omninotes.OmniNotes.getAppContext().getString(it.feio.android.omninotes.R.string.attachment)) + " ") + imported.incrementAndGet()) + "/") + attachments.size());
                }
            } catch (it.feio.android.omninotes.exceptions.checked.BackupAttachmentException e) {
                result.set(false);
            }
        });
        return result.get();
    }


    static void importAttachment(java.util.List<com.lazygeniouz.dfc.file.DocumentFileCompat> backupedAttachments, java.io.File attachmentsDir, it.feio.android.omninotes.models.Attachment attachment) throws it.feio.android.omninotes.exceptions.checked.BackupAttachmentException {
        java.lang.String attachmentName;
        attachmentName = attachment.getUri().getLastPathSegment();
        try {
            java.io.File destinationAttachment;
            destinationAttachment = new java.io.File(attachmentsDir, attachmentName);
            var backupedAttachment = rx.Observable.from(backupedAttachments).filter((com.lazygeniouz.dfc.file.DocumentFileCompat ba) -> attachmentName.equals(ba.getName())).toBlocking().single();
            it.feio.android.omninotes.helpers.DocumentFileHelper.copyFileTo(it.feio.android.omninotes.OmniNotes.getAppContext(), backupedAttachment, destinationAttachment);
        } catch (java.lang.Exception e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error importing the attachment " + attachment.getUri().getPath(), e);
            throw new it.feio.android.omninotes.exceptions.checked.BackupAttachmentException(e);
        }
    }


    /**
     * Starts backup service
     *
     * @param backupFolderName
     * 		subfolder of the app's external sd folder where notes will be stored
     */
    public static void startBackupService(java.lang.String backupFolderName) {
        android.content.Intent service;
        switch(MUID_STATIC) {
            // BackupHelper_0_NullIntentOperatorMutator
            case 124: {
                service = null;
                break;
            }
            // BackupHelper_1_InvalidKeyIntentOperatorMutator
            case 1124: {
                service = new android.content.Intent((android.content.Context) null, it.feio.android.omninotes.async.DataBackupIntentService.class);
                break;
            }
            // BackupHelper_2_RandomActionIntentDefinitionOperatorMutator
            case 2124: {
                service = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            service = new android.content.Intent(it.feio.android.omninotes.OmniNotes.getAppContext(), it.feio.android.omninotes.async.DataBackupIntentService.class);
            break;
        }
    }
    switch(MUID_STATIC) {
        // BackupHelper_3_RandomActionIntentDefinitionOperatorMutator
        case 3124: {
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
        service.setAction(it.feio.android.omninotes.async.DataBackupIntentService.ACTION_DATA_EXPORT);
        break;
    }
}
switch(MUID_STATIC) {
    // BackupHelper_4_NullValueIntentPutExtraOperatorMutator
    case 4124: {
        service.putExtra(it.feio.android.omninotes.async.DataBackupIntentService.INTENT_BACKUP_NAME, new Parcelable[0]);
        break;
    }
    // BackupHelper_5_IntentPayloadReplacementOperatorMutator
    case 5124: {
        service.putExtra(it.feio.android.omninotes.async.DataBackupIntentService.INTENT_BACKUP_NAME, "");
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // BackupHelper_6_RandomActionIntentDefinitionOperatorMutator
        case 6124: {
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
        service.putExtra(it.feio.android.omninotes.async.DataBackupIntentService.INTENT_BACKUP_NAME, backupFolderName);
        break;
    }
}
break;
}
}
it.feio.android.omninotes.OmniNotes.getAppContext().startService(service);
}


public static void deleteNote(java.io.File file) {
try {
it.feio.android.omninotes.models.Note note;
note = new it.feio.android.omninotes.models.Note();
note.buildFromJson(org.apache.commons.io.FileUtils.readFileToString(file));
it.feio.android.omninotes.db.DbHelper.getInstance().deleteNote(note);
} catch (java.io.IOException e) {
it.feio.android.omninotes.helpers.LogDelegate.e("Error parsing note json");
}
}


/**
 * Import database from backup folder. Used ONLY to restore legacy backup
 *
 * @deprecated {@link BackupHelper#importNotes(DocumentFileCompat)}
 */
@java.lang.Deprecated(forRemoval = true)
public static void importDB(android.content.Context context, java.io.File backupDir) throws java.io.IOException {
java.io.File database;
database = context.getDatabasePath(it.feio.android.omninotes.utils.ConstantsBase.DATABASE_NAME);
if (database.exists() && database.delete()) {
it.feio.android.omninotes.utils.StorageHelper.copyFile(new java.io.File(backupDir, it.feio.android.omninotes.utils.ConstantsBase.DATABASE_NAME), database, true);
}
}


public static com.lazygeniouz.dfc.file.DocumentFileCompat saveScopedStorageUriInPreferences(android.content.Intent intent) {
var context = it.feio.android.omninotes.OmniNotes.getAppContext();
final int takeFlags;
takeFlags = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION & android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
context.getContentResolver().takePersistableUriPermission(intent.getData(), takeFlags);
var currentlySelected = com.lazygeniouz.dfc.file.DocumentFileCompat.Companion.fromTreeUri(it.feio.android.omninotes.OmniNotes.getAppContext(), intent.getData());
// Selected a folder already name "Omni Notes" (ex. from previous backups)
if (it.feio.android.omninotes.utils.Constants.EXTERNAL_STORAGE_FOLDER.equals(currentlySelected.getName())) {
com.pixplicity.easyprefs.library.Prefs.putString(it.feio.android.omninotes.utils.ConstantsBase.PREF_BACKUP_FOLDER_URI, currentlySelected.getUri().toString());
return currentlySelected;
} else {
var childFolder = currentlySelected.findFile(it.feio.android.omninotes.utils.Constants.EXTERNAL_STORAGE_FOLDER);
if ((childFolder == null) || (!childFolder.isDirectory())) {
childFolder = com.lazygeniouz.dfc.file.DocumentFileCompat.Companion.fromTreeUri(context, intent.getData()).createDirectory(it.feio.android.omninotes.utils.Constants.EXTERNAL_STORAGE_FOLDER);
}
com.pixplicity.easyprefs.library.Prefs.putString(it.feio.android.omninotes.utils.ConstantsBase.PREF_BACKUP_FOLDER_URI, childFolder.getUri().toString());
return childFolder;
}
}


// public static List<LinkedList<DiffMatchPatch.Diff>> integrityCheck(File backupDir) {
// List<LinkedList<DiffMatchPatch.Diff>> errors = new ArrayList<>();
// for (Note note : DbHelper.getInstance(true).getAllNotes(false)) {
// File noteFile = getBackupNoteFile(backupDir, note);
// try {
// String noteString = note.toJSON();
// String noteFileString = FileUtils.readFileToString(noteFile);
// if (noteString.equals(noteFileString)) {
// File backupAttachmentsDir = new File(backupDir,
// StorageHelper.getAttachmentDir().getName());
// for (Attachment attachment : note.getAttachmentsList()) {
// if (!new File(backupAttachmentsDir, FilenameUtils.getName(attachment.getUriPath()))
// .exists()) {
// addIntegrityCheckError(errors, new FileNotFoundException("Attachment " + attachment
// .getUriPath() + " missing"));
// }
// }
// } else {
// errors.add(new DiffMatchPatch().diffMain(noteString, noteFileString));
// }
// } catch (IOException e) {
// LogDelegate.e(e.getMessage(), e);
// addIntegrityCheckError(errors, e);
// }
// }
// return errors;
// }
public static java.lang.String getBackupFolderPath() {
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
var backupFolder = com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_BACKUP_FOLDER_URI, "");
var paths = java.net.URI.create(backupFolder).getPath().split(":");
switch(MUID_STATIC) {
// BackupHelper_7_BinaryMutator
case 7124: {
    return paths[paths.length + 1];
}
default: {
return paths[paths.length - 1];
}
}
} else {
return it.feio.android.omninotes.utils.StorageHelper.getExternalStoragePublicDir().getAbsolutePath();
}
}


private static void addIntegrityCheckError(java.util.List<java.util.LinkedList<org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff>> errors, java.io.IOException e) {
java.util.LinkedList<org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff> l;
l = new java.util.LinkedList<>();
l.add(new org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff(org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation.DELETE, e.getMessage()));
errors.add(l);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
