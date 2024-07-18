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
package it.feio.android.omninotes.db;
import it.feio.android.omninotes.models.Category;
import java.util.HashMap;
import android.database.SQLException;
import it.feio.android.omninotes.models.Stats;
import java.util.ArrayList;
import android.net.Uri;
import android.database.Cursor;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.models.Attachment;
import it.feio.android.omninotes.utils.Security;
import it.feio.android.omninotes.OmniNotes;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_AUDIO;
import java.util.Map.Entry;
import static it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM;
import org.apache.commons.lang3.StringUtils;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_IMAGE;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_SKETCH;
import it.feio.android.omninotes.helpers.NotesHelper;
import java.util.List;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD;
import java.util.Collections;
import java.util.regex.Pattern;
import android.database.sqlite.SQLiteOpenHelper;
import it.feio.android.omninotes.utils.Navigation;
import it.feio.android.omninotes.utils.TagsHelper;
import it.feio.android.omninotes.async.upgrade.UpgradeProcessor;
import java.util.Calendar;
import android.content.ContentValues;
import java.io.IOException;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_FILTER_PAST_REMINDERS;
import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
import java.lang.reflect.InvocationTargetException;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_VIDEO;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_SORTING_COLUMN;
import it.feio.android.omninotes.utils.AssetUtils;
import it.feio.android.omninotes.models.Note;
import java.util.Objects;
import android.database.sqlite.SQLiteDatabase;
import it.feio.android.omninotes.models.Tag;
import it.feio.android.omninotes.exceptions.DatabaseException;
import it.feio.android.omninotes.helpers.LogDelegate;
import java.util.Map;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_FILTER_ARCHIVED_IN_CATEGORIES;
import static it.feio.android.omninotes.utils.ConstantsBase.TIMESTAMP_UNIX_EPOCH;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DbHelper extends android.database.sqlite.SQLiteOpenHelper {
    static final int MUID_STATIC = getMUID();
    // Database name
    // Database version aligned if possible to software version
    private static final int DATABASE_VERSION = 625;

    // Sql query file directory
    private static final java.lang.String SQL_DIR = "sql";

    // Notes table name
    public static final java.lang.String TABLE_NOTES = "notes";

    // Notes table columns
    public static final java.lang.String KEY_ID = "creation";

    public static final java.lang.String KEY_CREATION = "creation";

    public static final java.lang.String KEY_LAST_MODIFICATION = "last_modification";

    public static final java.lang.String KEY_TITLE = "title";

    public static final java.lang.String KEY_CONTENT = "content";

    public static final java.lang.String KEY_ARCHIVED = "archived";

    public static final java.lang.String KEY_TRASHED = "trashed";

    public static final java.lang.String KEY_REMINDER = "alarm";

    public static final java.lang.String KEY_REMINDER_FIRED = "reminder_fired";

    public static final java.lang.String KEY_RECURRENCE_RULE = "recurrence_rule";

    public static final java.lang.String KEY_LATITUDE = "latitude";

    public static final java.lang.String KEY_LONGITUDE = "longitude";

    public static final java.lang.String KEY_ADDRESS = "address";

    public static final java.lang.String KEY_CATEGORY = "category_id";

    public static final java.lang.String KEY_LOCKED = "locked";

    public static final java.lang.String KEY_CHECKLIST = "checklist";

    // Attachments table name
    public static final java.lang.String TABLE_ATTACHMENTS = "attachments";

    // Attachments table columns
    public static final java.lang.String KEY_ATTACHMENT_ID = "attachment_id";

    public static final java.lang.String KEY_ATTACHMENT_URI = "uri";

    public static final java.lang.String KEY_ATTACHMENT_NAME = "name";

    public static final java.lang.String KEY_ATTACHMENT_SIZE = "size";

    public static final java.lang.String KEY_ATTACHMENT_LENGTH = "length";

    public static final java.lang.String KEY_ATTACHMENT_MIME_TYPE = "mime_type";

    public static final java.lang.String KEY_ATTACHMENT_NOTE_ID = "note_id";

    // Categories table name
    public static final java.lang.String TABLE_CATEGORY = "categories";

    // Categories table columns
    public static final java.lang.String KEY_CATEGORY_ID = "category_id";

    public static final java.lang.String KEY_CATEGORY_NAME = "name";

    public static final java.lang.String KEY_CATEGORY_DESCRIPTION = "description";

    public static final java.lang.String KEY_CATEGORY_COLOR = "color";

    // Queries
    private static final java.lang.String CREATE_QUERY = "create.sql";

    private static final java.lang.String UPGRADE_QUERY_PREFIX = "upgrade-";

    private static final java.lang.String UPGRADE_QUERY_SUFFIX = ".sql";

    private final android.content.Context mContext;

    private static it.feio.android.omninotes.db.DbHelper instance = null;

    private android.database.sqlite.SQLiteDatabase db;

    public static synchronized it.feio.android.omninotes.db.DbHelper getInstance() {
        return it.feio.android.omninotes.db.DbHelper.getInstance(it.feio.android.omninotes.OmniNotes.getAppContext());
    }


    public static synchronized it.feio.android.omninotes.db.DbHelper getInstance(android.content.Context context) {
        if (it.feio.android.omninotes.db.DbHelper.instance == null) {
            it.feio.android.omninotes.db.DbHelper.instance = new it.feio.android.omninotes.db.DbHelper(context);
        }
        return it.feio.android.omninotes.db.DbHelper.instance;
    }


    public static synchronized it.feio.android.omninotes.db.DbHelper getInstance(boolean forcedNewInstance) {
        if ((it.feio.android.omninotes.db.DbHelper.instance == null) || forcedNewInstance) {
            android.content.Context context;
            context = ((it.feio.android.omninotes.db.DbHelper.instance == null) || (it.feio.android.omninotes.db.DbHelper.instance.mContext == null)) ? it.feio.android.omninotes.OmniNotes.getAppContext() : it.feio.android.omninotes.db.DbHelper.instance.mContext;
            it.feio.android.omninotes.db.DbHelper.instance = new it.feio.android.omninotes.db.DbHelper(context);
        }
        return it.feio.android.omninotes.db.DbHelper.instance;
    }


    private DbHelper(android.content.Context mContext) {
        super(mContext, it.feio.android.omninotes.utils.ConstantsBase.DATABASE_NAME, null, it.feio.android.omninotes.db.DbHelper.DATABASE_VERSION);
        this.mContext = mContext;
    }


    public java.lang.String getDatabaseName() {
        return it.feio.android.omninotes.utils.ConstantsBase.DATABASE_NAME;
    }


    public android.database.sqlite.SQLiteDatabase getDatabase() {
        return getDatabase(false);
    }


    public android.database.sqlite.SQLiteDatabase getDatabase(boolean forceWritable) {
        try {
            return forceWritable ? getWritableDatabase() : getReadableDatabase();
        } catch (java.lang.IllegalStateException e) {
            return this.db;
        }
    }


    @java.lang.Override
    public void onOpen(android.database.sqlite.SQLiteDatabase db) {
        db.disableWriteAheadLogging();
        super.onOpen(db);
    }


    @java.lang.Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        try {
            it.feio.android.omninotes.helpers.LogDelegate.i("Database creation");
            execSqlFile(it.feio.android.omninotes.db.DbHelper.CREATE_QUERY, db);
        } catch (java.io.IOException e) {
            throw new it.feio.android.omninotes.exceptions.DatabaseException("Database creation failed: " + e.getMessage(), e);
        }
    }


    @java.lang.Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        this.db = db;
        it.feio.android.omninotes.helpers.LogDelegate.i((("Upgrading database version from " + oldVersion) + " to ") + newVersion);
        switch(MUID_STATIC) {
            // DbHelper_0_BinaryMutator
            case 34: {
                try {
                    it.feio.android.omninotes.async.upgrade.UpgradeProcessor.process(oldVersion, newVersion);
                    for (java.lang.String sqlFile : it.feio.android.omninotes.utils.AssetUtils.list(it.feio.android.omninotes.db.DbHelper.SQL_DIR, mContext.getAssets())) {
                        if (sqlFile.startsWith(it.feio.android.omninotes.db.DbHelper.UPGRADE_QUERY_PREFIX)) {
                            int fileVersion;
                            fileVersion = java.lang.Integer.parseInt(sqlFile.substring(it.feio.android.omninotes.db.DbHelper.UPGRADE_QUERY_PREFIX.length(), sqlFile.length() + it.feio.android.omninotes.db.DbHelper.UPGRADE_QUERY_SUFFIX.length()));
                            if ((fileVersion > oldVersion) && (fileVersion <= newVersion)) {
                                execSqlFile(sqlFile, db);
                            }
                        }
                    }
                    it.feio.android.omninotes.helpers.LogDelegate.i("Database upgrade successful");
                } catch (java.io.IOException | java.lang.reflect.InvocationTargetException | java.lang.IllegalAccessException e) {
                    throw new java.lang.RuntimeException("Database upgrade failed", e);
                }
                break;
            }
            default: {
            try {
                it.feio.android.omninotes.async.upgrade.UpgradeProcessor.process(oldVersion, newVersion);
                for (java.lang.String sqlFile : it.feio.android.omninotes.utils.AssetUtils.list(it.feio.android.omninotes.db.DbHelper.SQL_DIR, mContext.getAssets())) {
                    if (sqlFile.startsWith(it.feio.android.omninotes.db.DbHelper.UPGRADE_QUERY_PREFIX)) {
                        int fileVersion;
                        fileVersion = java.lang.Integer.parseInt(sqlFile.substring(it.feio.android.omninotes.db.DbHelper.UPGRADE_QUERY_PREFIX.length(), sqlFile.length() - it.feio.android.omninotes.db.DbHelper.UPGRADE_QUERY_SUFFIX.length()));
                        if ((fileVersion > oldVersion) && (fileVersion <= newVersion)) {
                            execSqlFile(sqlFile, db);
                        }
                    }
                }
                it.feio.android.omninotes.helpers.LogDelegate.i("Database upgrade successful");
            } catch (java.io.IOException | java.lang.reflect.InvocationTargetException | java.lang.IllegalAccessException e) {
                throw new java.lang.RuntimeException("Database upgrade failed", e);
            }
            break;
        }
    }
}


public it.feio.android.omninotes.models.Note updateNote(it.feio.android.omninotes.models.Note note, boolean updateLastModification) {
    db = getDatabase(true);
    java.lang.String content;
    content = (java.lang.Boolean.TRUE.equals(note.isLocked())) ? it.feio.android.omninotes.utils.Security.encrypt(note.getContent(), com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, "")) : note.getContent();
    // To ensure note and attachments insertions are atomic and boost performances transaction are used
    db.beginTransaction();
    android.content.ContentValues values;
    values = new android.content.ContentValues();
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_TITLE, note.getTitle());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_CONTENT, content);
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_CREATION, note.getCreation() != null ? note.getCreation() : java.util.Calendar.getInstance().getTimeInMillis());
    long lastModification;
    lastModification = ((note.getLastModification() != null) && (!updateLastModification)) ? note.getLastModification() : java.util.Calendar.getInstance().getTimeInMillis();
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_LAST_MODIFICATION, lastModification);
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_ARCHIVED, note.isArchived());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_TRASHED, note.isTrashed());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_REMINDER, note.getAlarm());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_REMINDER_FIRED, note.isReminderFired());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_RECURRENCE_RULE, note.getRecurrenceRule());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_LATITUDE, note.getLatitude());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_LONGITUDE, note.getLongitude());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_ADDRESS, note.getAddress());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY, note.getCategory() != null ? note.getCategory().getId() : null);
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_LOCKED, (note.isLocked() != null) && note.isLocked());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_CHECKLIST, (note.isChecklist() != null) && note.isChecklist());
    db.insertWithOnConflict(it.feio.android.omninotes.db.DbHelper.TABLE_NOTES, it.feio.android.omninotes.db.DbHelper.KEY_ID, values, android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE);
    it.feio.android.omninotes.helpers.LogDelegate.d(("Updated note titled '" + note.getTitle()) + "'");
    // Updating attachments
    java.util.List<it.feio.android.omninotes.models.Attachment> deletedAttachments;
    deletedAttachments = note.getAttachmentsListOld();
    for (it.feio.android.omninotes.models.Attachment attachment : note.getAttachmentsList()) {
        updateAttachment(note.get_id() != null ? note.get_id() : values.getAsLong(it.feio.android.omninotes.db.DbHelper.KEY_CREATION), attachment, db);
        deletedAttachments.remove(attachment);
    }
    // Remove from database deleted attachments
    for (it.feio.android.omninotes.models.Attachment attachmentDeleted : deletedAttachments) {
        db.delete(it.feio.android.omninotes.db.DbHelper.TABLE_ATTACHMENTS, it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_ID + " = ?", new java.lang.String[]{ java.lang.String.valueOf(attachmentDeleted.getId()) });
    }
    db.setTransactionSuccessful();
    db.endTransaction();
    // Fill the note with correct data before returning it
    note.setCreation(note.getCreation() != null ? note.getCreation() : values.getAsLong(it.feio.android.omninotes.db.DbHelper.KEY_CREATION));
    note.setLastModification(values.getAsLong(it.feio.android.omninotes.db.DbHelper.KEY_LAST_MODIFICATION));
    return note;
}


private void execSqlFile(java.lang.String sqlFile, android.database.sqlite.SQLiteDatabase db) throws android.database.SQLException, java.io.IOException {
    it.feio.android.omninotes.helpers.LogDelegate.i("  exec sql file: {}" + sqlFile);
    for (java.lang.String sqlInstruction : it.feio.android.omninotes.db.SqlParser.parseSqlFile((it.feio.android.omninotes.db.DbHelper.SQL_DIR + "/") + sqlFile, mContext.getAssets())) {
        it.feio.android.omninotes.helpers.LogDelegate.v("    sql: {}" + sqlInstruction);
        try {
            db.execSQL(sqlInstruction);
        } catch (java.lang.Exception e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error executing command: " + sqlInstruction, e);
        }
    }
}


/**
 * New attachment insertion
 */
public it.feio.android.omninotes.models.Attachment updateAttachment(it.feio.android.omninotes.models.Attachment attachment) {
    var noteId = (attachment.getNoteId() != null) ? attachment.getNoteId() : -1;
    return updateAttachment(noteId, attachment, getDatabase(true));
}


/**
 * Attachments update
 */
public it.feio.android.omninotes.models.Attachment updateAttachment(long noteId, it.feio.android.omninotes.models.Attachment attachment, android.database.sqlite.SQLiteDatabase db) {
    var valuesAttachments = new android.content.ContentValues();
    valuesAttachments.put(it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_ID, attachment.getId() != null ? attachment.getId() : java.util.Calendar.getInstance().getTimeInMillis());
    valuesAttachments.put(it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_NOTE_ID, noteId);
    valuesAttachments.put(it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_URI, attachment.getUri().toString());
    valuesAttachments.put(it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_MIME_TYPE, attachment.getMime_type());
    valuesAttachments.put(it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_NAME, attachment.getName());
    valuesAttachments.put(it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_SIZE, attachment.getSize());
    valuesAttachments.put(it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_LENGTH, attachment.getLength());
    db.insertWithOnConflict(it.feio.android.omninotes.db.DbHelper.TABLE_ATTACHMENTS, it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_ID, valuesAttachments, android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE);
    return attachment;
}


/**
 * Getting single note
 */
public it.feio.android.omninotes.models.Note getNote(long id) {
    java.util.List<it.feio.android.omninotes.models.Note> notes;
    notes = getNotes(((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_ID) + " = ") + id, true);
    return notes.isEmpty() ? null : notes.get(0);
}


/**
 * Getting All notes
 *
 * @param checkNavigation
 * 		Tells if navigation status (notes, archived) must be kept in
 * 		consideration or if all notes have to be retrieved
 * @return Notes list
 */
public java.util.List<it.feio.android.omninotes.models.Note> getAllNotes(java.lang.Boolean checkNavigation) {
    java.lang.String whereCondition;
    whereCondition = "";
    if (java.lang.Boolean.TRUE.equals(checkNavigation)) {
        int navigation;
        navigation = it.feio.android.omninotes.utils.Navigation.getNavigation();
        switch (navigation) {
            case it.feio.android.omninotes.utils.Navigation.NOTES :
                return getNotesActive();
            case it.feio.android.omninotes.utils.Navigation.ARCHIVE :
                return getNotesArchived();
            case it.feio.android.omninotes.utils.Navigation.REMINDERS :
                return getNotesWithReminder(com.pixplicity.easyprefs.library.Prefs.getBoolean(it.feio.android.omninotes.utils.ConstantsBase.PREF_FILTER_PAST_REMINDERS, false));
            case it.feio.android.omninotes.utils.Navigation.TRASH :
                return getNotesTrashed();
            case it.feio.android.omninotes.utils.Navigation.UNCATEGORIZED :
                return getNotesUncategorized();
            case it.feio.android.omninotes.utils.Navigation.CATEGORY :
                return getNotesByCategory(it.feio.android.omninotes.utils.Navigation.getCategory());
            default :
                return getNotes(whereCondition, true);
        }
    } else {
        return getNotes(whereCondition, true);
    }
}


public java.util.List<it.feio.android.omninotes.models.Note> getNotesActive() {
    java.lang.String whereCondition;
    whereCondition = (((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_ARCHIVED) + " IS NOT 1 AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS NOT 1 ";
    return getNotes(whereCondition, true);
}


public java.util.List<it.feio.android.omninotes.models.Note> getNotesArchived() {
    java.lang.String whereCondition;
    whereCondition = (((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_ARCHIVED) + " = 1 AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS NOT 1 ";
    return getNotes(whereCondition, true);
}


public java.util.List<it.feio.android.omninotes.models.Note> getNotesTrashed() {
    java.lang.String whereCondition;
    whereCondition = (" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " = 1 ";
    return getNotes(whereCondition, true);
}


public java.util.List<it.feio.android.omninotes.models.Note> getNotesUncategorized() {
    java.lang.String whereCondition;
    whereCondition = (((((((" WHERE " + "(") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID) + " IS NULL OR ") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID) + " == 0) ") + "AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS NOT 1";
    return getNotes(whereCondition, true);
}


public java.util.List<it.feio.android.omninotes.models.Note> getNotesWithLocation() {
    java.lang.String whereCondition;
    whereCondition = ((((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_LONGITUDE) + " IS NOT NULL ") + "AND ") + it.feio.android.omninotes.db.DbHelper.KEY_LONGITUDE) + " != 0 ";
    return getNotes(whereCondition, true);
}


/**
 * Common method for notes retrieval. It accepts a query to perform and returns matching records.
 */
public java.util.List<it.feio.android.omninotes.models.Note> getNotes(java.lang.String whereCondition, boolean order) {
    java.util.List<it.feio.android.omninotes.models.Note> noteList;
    noteList = new java.util.ArrayList<>();
    java.lang.String sortColumn;
    sortColumn = "";
    java.lang.String sortOrder;
    sortOrder = "";
    // Getting sorting criteria from preferences. Reminder screen forces sorting.
    if (it.feio.android.omninotes.utils.Navigation.checkNavigation(it.feio.android.omninotes.utils.Navigation.REMINDERS)) {
        sortColumn = it.feio.android.omninotes.db.DbHelper.KEY_REMINDER;
    } else {
        sortColumn = com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_SORTING_COLUMN, it.feio.android.omninotes.db.DbHelper.KEY_TITLE);
    }
    if (order) {
        sortOrder = (it.feio.android.omninotes.db.DbHelper.KEY_TITLE.equals(sortColumn) || it.feio.android.omninotes.db.DbHelper.KEY_REMINDER.equals(sortColumn)) ? " ASC " : " DESC ";
    }
    // In case of title sorting criteria it must be handled empty title by concatenating content
    sortColumn = (it.feio.android.omninotes.db.DbHelper.KEY_TITLE.equals(sortColumn)) ? (it.feio.android.omninotes.db.DbHelper.KEY_TITLE + "||") + it.feio.android.omninotes.db.DbHelper.KEY_CONTENT : sortColumn;
    // In case of reminder sorting criteria the empty reminder notes must be moved on bottom of results
    sortColumn = (it.feio.android.omninotes.db.DbHelper.KEY_REMINDER.equals(sortColumn)) ? (((("IFNULL(" + it.feio.android.omninotes.db.DbHelper.KEY_REMINDER) + ", ") + "") + it.feio.android.omninotes.utils.ConstantsBase.TIMESTAMP_UNIX_EPOCH) + ")" : sortColumn;
    // Generic query to be specialized with conditions passed as parameter
    java.lang.String query;
    query = ((((((((((((((((((((((((((((((((((((((((((("SELECT " + it.feio.android.omninotes.db.DbHelper.KEY_CREATION) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_LAST_MODIFICATION) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_TITLE) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CONTENT) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_ARCHIVED) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_REMINDER) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_REMINDER_FIRED) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_RECURRENCE_RULE) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_LATITUDE) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_LONGITUDE) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_ADDRESS) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_LOCKED) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CHECKLIST) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_NAME) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_DESCRIPTION) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_COLOR) + " FROM ") + it.feio.android.omninotes.db.DbHelper.TABLE_NOTES) + " LEFT JOIN ") + it.feio.android.omninotes.db.DbHelper.TABLE_CATEGORY) + " USING( ") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY) + ") ") + whereCondition) + (order ? ((" ORDER BY " + sortColumn) + " COLLATE NOCASE ") + sortOrder : "");
    it.feio.android.omninotes.helpers.LogDelegate.v("Query: " + query);
    try (android.database.Cursor cursor = getDatabase().rawQuery(query, null)) {
        if (cursor.moveToFirst()) {
            do {
                int i;
                i = 0;
                it.feio.android.omninotes.models.Note note;
                note = new it.feio.android.omninotes.models.Note();
                note.setCreation(cursor.getLong(i++));
                note.setLastModification(cursor.getLong(i++));
                note.setTitle(cursor.getString(i++));
                note.setContent(cursor.getString(i++));
                note.setArchived("1".equals(cursor.getString(i++)));
                note.setTrashed("1".equals(cursor.getString(i++)));
                note.setAlarm(cursor.getString(i++));
                note.setReminderFired(cursor.getInt(i++));
                note.setRecurrenceRule(cursor.getString(i++));
                note.setLatitude(cursor.getString(i++));
                note.setLongitude(cursor.getString(i++));
                note.setAddress(cursor.getString(i++));
                note.setLocked("1".equals(cursor.getString(i++)));
                note.setChecklist("1".equals(cursor.getString(i++)));
                // Eventual decryption of content
                if (java.lang.Boolean.TRUE.equals(note.isLocked())) {
                    note.setContent(it.feio.android.omninotes.utils.Security.decrypt(note.getContent(), com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, "")));
                }
                // Set category
                long categoryId;
                categoryId = cursor.getLong(i++);
                if (categoryId != 0) {
                    it.feio.android.omninotes.models.Category category;
                    category = new it.feio.android.omninotes.models.Category(categoryId, cursor.getString(i++), cursor.getString(i++), cursor.getString(i));
                    note.setCategory(category);
                }
                // Add eventual attachments uri
                note.setAttachmentsList(getNoteAttachments(note));
                // Adding note to list
                noteList.add(note);
            } while (cursor.moveToNext() );
        }
    }
    it.feio.android.omninotes.helpers.LogDelegate.v("Query: Retrieval finished!");
    return noteList;
}


/**
 * Archives/restore single note
 */
public void archiveNote(it.feio.android.omninotes.models.Note note, boolean archive) {
    note.setArchived(archive);
    updateNote(note, false);
}


/**
 * Trashes/restore single note
 */
public void trashNote(it.feio.android.omninotes.models.Note note, boolean trash) {
    note.setTrashed(trash);
    updateNote(note, false);
}


/**
 * Deleting single note
 */
public boolean deleteNote(it.feio.android.omninotes.models.Note note) {
    return deleteNote(note, false);
}


/**
 * Deleting single note, eventually keeping attachments
 */
public boolean deleteNote(it.feio.android.omninotes.models.Note note, boolean keepAttachments) {
    return deleteNote(note.get_id(), keepAttachments);
}


/**
 * Deleting single note by its ID
 */
public boolean deleteNote(long noteId, boolean keepAttachments) {
    android.database.sqlite.SQLiteDatabase db;
    db = getDatabase(true);
    db.delete(it.feio.android.omninotes.db.DbHelper.TABLE_NOTES, it.feio.android.omninotes.db.DbHelper.KEY_ID + " = ?", new java.lang.String[]{ java.lang.String.valueOf(noteId) });
    if (!keepAttachments) {
        db.delete(it.feio.android.omninotes.db.DbHelper.TABLE_ATTACHMENTS, it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_NOTE_ID + " = ?", new java.lang.String[]{ java.lang.String.valueOf(noteId) });
    }
    return true;
}


/**
 * Empties trash deleting all trashed notes
 */
public void emptyTrash() {
    for (it.feio.android.omninotes.models.Note note : getNotesTrashed()) {
        deleteNote(note);
    }
}


/**
 * Gets notes matching pattern with title or content text
 *
 * @param pattern
 * 		String to match with
 * @return Notes list
 */
public java.util.List<it.feio.android.omninotes.models.Note> getNotesByPattern(java.lang.String pattern) {
    java.lang.String escapedPattern;
    escapedPattern = it.feio.android.omninotes.db.DbHelper.escapeSql(pattern);
    int navigation;
    navigation = it.feio.android.omninotes.utils.Navigation.getNavigation();
    java.lang.String whereCondition;
    whereCondition = ((((((((((((((((((((((((((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + (navigation == it.feio.android.omninotes.utils.Navigation.TRASH ? " IS 1" : " IS NOT 1")) + (navigation == it.feio.android.omninotes.utils.Navigation.ARCHIVE ? (" AND " + it.feio.android.omninotes.db.DbHelper.KEY_ARCHIVED) + " IS 1" : "")) + (navigation == it.feio.android.omninotes.utils.Navigation.CATEGORY ? ((" AND " + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY) + " = ") + it.feio.android.omninotes.utils.Navigation.getCategory() : "")) + (navigation == it.feio.android.omninotes.utils.Navigation.UNCATEGORIZED ? (((" AND (" + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY) + " IS NULL OR ") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID) + " == 0) " : "")) + (it.feio.android.omninotes.utils.Navigation.checkNavigation(it.feio.android.omninotes.utils.Navigation.REMINDERS) ? (" AND " + it.feio.android.omninotes.db.DbHelper.KEY_REMINDER) + " IS NOT NULL" : "")) + " AND (") + " ( ") + it.feio.android.omninotes.db.DbHelper.KEY_LOCKED) + " IS NOT 1 AND (") + it.feio.android.omninotes.db.DbHelper.KEY_TITLE) + " LIKE '%") + escapedPattern) + "%\' ESCAPE \'\\\' ") + " OR ") + it.feio.android.omninotes.db.DbHelper.KEY_CONTENT) + " LIKE '%") + escapedPattern) + "%\' ESCAPE \'\\\' ))") + " OR ( ") + it.feio.android.omninotes.db.DbHelper.KEY_LOCKED) + " = 1 AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TITLE) + " LIKE '%") + escapedPattern) + "%\' ESCAPE \'\\\' )") + ")";
    return getNotes(whereCondition, true);
}


static java.lang.String escapeSql(java.lang.String pattern) {
    return org.apache.commons.lang3.StringUtils.replace(pattern, "'", "''").replace("%", "\\%").replace("_", "\\_");
}


/**
 * Search for notes with reminder
 *
 * @param filterPastReminders
 * 		Excludes past reminders
 * @return Notes list
 */
public java.util.List<it.feio.android.omninotes.models.Note> getNotesWithReminder(boolean filterPastReminders) {
    java.lang.String whereCondition;
    whereCondition = (((((((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_REMINDER) + (filterPastReminders ? " >= " + java.util.Calendar.getInstance().getTimeInMillis() : " IS NOT NULL")) + " AND ") + it.feio.android.omninotes.db.DbHelper.KEY_ARCHIVED) + " IS NOT 1") + " AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS NOT 1";
    return getNotes(whereCondition, true);
}


/**
 * Returns all notes that have a reminder that has not been alredy fired
 *
 * @return Notes list
 */
public java.util.List<it.feio.android.omninotes.models.Note> getNotesWithReminderNotFired() {
    java.lang.String whereCondition;
    whereCondition = ((((((((((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_REMINDER) + " IS NOT NULL") + " AND ") + it.feio.android.omninotes.db.DbHelper.KEY_REMINDER_FIRED) + " IS NOT 1") + " AND ") + it.feio.android.omninotes.db.DbHelper.KEY_ARCHIVED) + " IS NOT 1") + " AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS NOT 1";
    return getNotes(whereCondition, true);
}


/**
 * Retrieves locked or unlocked notes
 */
public java.util.List<it.feio.android.omninotes.models.Note> getNotesWithLock(boolean locked) {
    java.lang.String whereCondition;
    whereCondition = (" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_LOCKED) + (locked ? " = 1 " : " IS NOT 1 ");
    return getNotes(whereCondition, true);
}


/**
 * Search for notes with reminder expiring the current day
 *
 * @return Notes list
 */
public java.util.List<it.feio.android.omninotes.models.Note> getTodayReminders() {
    java.lang.String whereCondition;
    whereCondition = (((" WHERE DATE(" + it.feio.android.omninotes.db.DbHelper.KEY_REMINDER) + "/1000, 'unixepoch') = DATE('now') AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS NOT 1";
    return getNotes(whereCondition, false);
}


/**
 * Retrieves all attachments related to specific note
 */
public java.util.List<it.feio.android.omninotes.models.Attachment> getNoteAttachments(it.feio.android.omninotes.models.Note note) {
    java.lang.String whereCondition;
    whereCondition = ((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_NOTE_ID) + " = ") + note.get_id();
    return getAttachments(whereCondition);
}


public java.util.List<it.feio.android.omninotes.models.Note> getChecklists() {
    java.lang.String whereCondition;
    whereCondition = (" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_CHECKLIST) + " = 1";
    return getNotes(whereCondition, false);
}


public java.util.List<it.feio.android.omninotes.models.Note> getMasked() {
    java.lang.String whereCondition;
    whereCondition = (" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_LOCKED) + " = 1";
    return getNotes(whereCondition, false);
}


/**
 * Retrieves all notes related to Category it passed as parameter
 *
 * @param categoryId
 * 		Category integer identifier
 * @return List of notes with requested category
 */
public java.util.List<it.feio.android.omninotes.models.Note> getNotesByCategory(java.lang.Long categoryId) {
    java.util.List<it.feio.android.omninotes.models.Note> notes;
    boolean filterArchived;
    filterArchived = com.pixplicity.easyprefs.library.Prefs.getBoolean(it.feio.android.omninotes.utils.ConstantsBase.PREF_FILTER_ARCHIVED_IN_CATEGORIES + categoryId, false);
    try {
        java.lang.String whereCondition;
        whereCondition = ((((((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID) + " = ") + categoryId) + " AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS NOT 1") + (filterArchived ? (" AND " + it.feio.android.omninotes.db.DbHelper.KEY_ARCHIVED) + " IS NOT 1" : "");
        notes = getNotes(whereCondition, true);
    } catch (java.lang.NumberFormatException e) {
        notes = getAllNotes(true);
    }
    return notes;
}


/**
 * Retrieves all tags
 */
public java.util.List<it.feio.android.omninotes.models.Tag> getTags() {
    return getTags(null);
}


/**
 * Retrieves all tags of a specified note
 */
public java.util.List<it.feio.android.omninotes.models.Tag> getTags(it.feio.android.omninotes.models.Note note) {
    java.util.List<it.feio.android.omninotes.models.Tag> tags;
    tags = new java.util.ArrayList<>();
    java.util.HashMap<java.lang.String, java.lang.Integer> tagsMap;
    tagsMap = new java.util.HashMap<>();
    java.lang.String whereCondition;
    whereCondition = (((((((((((" WHERE " + (note != null ? ((it.feio.android.omninotes.db.DbHelper.KEY_ID + " = ") + note.get_id()) + " AND " : "")) + "(") + it.feio.android.omninotes.db.DbHelper.KEY_CONTENT) + " LIKE '%#%' OR ") + it.feio.android.omninotes.db.DbHelper.KEY_TITLE) + " LIKE '%#%' ") + ")") + " AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS ") + (it.feio.android.omninotes.utils.Navigation.checkNavigation(it.feio.android.omninotes.utils.Navigation.TRASH) ? "" : " NOT ")) + " 1";
    java.util.List<it.feio.android.omninotes.models.Note> notesRetrieved;
    notesRetrieved = getNotes(whereCondition, true);
    for (it.feio.android.omninotes.models.Note noteRetrieved : notesRetrieved) {
        java.util.Map<java.lang.String, java.lang.Integer> tagsRetrieved;
        tagsRetrieved = it.feio.android.omninotes.utils.TagsHelper.retrieveTags(noteRetrieved);
        for (java.lang.String s : tagsRetrieved.keySet()) {
            int count;
            count = (tagsMap.get(s) == null) ? 0 : tagsMap.get(s);
            tagsMap.put(s, ++count);
        }
    }
    for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : tagsMap.entrySet()) {
        it.feio.android.omninotes.models.Tag tag;
        tag = new it.feio.android.omninotes.models.Tag(entry.getKey(), entry.getValue());
        tags.add(tag);
    }
    java.util.Collections.sort(tags, (it.feio.android.omninotes.models.Tag tag1,it.feio.android.omninotes.models.Tag tag2) -> tag1.getText().compareToIgnoreCase(tag2.getText()));
    return tags;
}


/**
 * Retrieves all notes related to category it passed as parameter
 */
public java.util.List<it.feio.android.omninotes.models.Note> getNotesByTag(java.lang.String tag) {
    if (tag.contains(",")) {
        return getNotesByTag(tag.split(","));
    } else {
        return getNotesByTag(new java.lang.String[]{ tag });
    }
}


/**
 * Retrieves all notes with specified tags
 */
public java.util.List<it.feio.android.omninotes.models.Note> getNotesByTag(java.lang.String[] tags) {
    java.lang.StringBuilder whereCondition;
    whereCondition = new java.lang.StringBuilder();
    whereCondition.append(" WHERE ");
    for (int i = 0; i < tags.length; i++) {
        if (i != 0) {
            whereCondition.append(" AND ");
        }
        whereCondition.append(("(" + it.feio.android.omninotes.db.DbHelper.KEY_CONTENT) + " LIKE '%").append(tags[i]).append("%' OR ").append(it.feio.android.omninotes.db.DbHelper.KEY_TITLE).append(" LIKE '%").append(tags[i]).append("%')");
    }
    // Trashed notes must be included in search results only if search if performed from trash
    whereCondition.append((" AND " + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS ").append(it.feio.android.omninotes.utils.Navigation.checkNavigation(it.feio.android.omninotes.utils.Navigation.TRASH) ? "" : "" + " NOT ").append(" 1");
    return rx.Observable.from(getNotes(whereCondition.toString(), true)).map((it.feio.android.omninotes.models.Note note) -> {
        boolean matches;
        matches = rx.Observable.from(tags).all((java.lang.String tag) -> {
            java.util.regex.Pattern p;
            p = java.util.regex.Pattern.compile((".*(\\s|^)" + tag) + "(\\s|$).*", java.util.regex.Pattern.MULTILINE);
            return p.matcher((note.getTitle() + " ") + note.getContent()).find();
        }).toBlocking().single();
        return matches ? note : null;
    }).filter((it.feio.android.omninotes.models.Note o) -> o != null).toList().toBlocking().single();
}


/**
 * Retrieves all uncompleted checklists
 */
public java.util.List<it.feio.android.omninotes.models.Note> getNotesByUncompleteChecklist() {
    java.lang.String whereCondition;
    whereCondition = (((((((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_CHECKLIST) + " = 1 AND ") + it.feio.android.omninotes.db.DbHelper.KEY_CONTENT) + " LIKE '%") + it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM) + "%' AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + (it.feio.android.omninotes.utils.Navigation.checkNavigation(it.feio.android.omninotes.utils.Navigation.TRASH) ? " IS 1" : " IS NOT 1");
    return getNotes(whereCondition, true);
}


/**
 * Retrieves all attachments
 */
public java.util.ArrayList<it.feio.android.omninotes.models.Attachment> getAllAttachments() {
    return getAttachments("");
}


/**
 * Retrieves attachments using a condition passed as parameter
 *
 * @return List of attachments
 */
public java.util.ArrayList<it.feio.android.omninotes.models.Attachment> getAttachments(java.lang.String whereCondition) {
    java.util.ArrayList<it.feio.android.omninotes.models.Attachment> attachmentsList;
    attachmentsList = new java.util.ArrayList<>();
    java.lang.String sql;
    sql = ((((((((((((((("SELECT " + it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_ID) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_URI) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_NAME) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_SIZE) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_LENGTH) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_MIME_TYPE) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_ATTACHMENT_NOTE_ID) + " FROM ") + it.feio.android.omninotes.db.DbHelper.TABLE_ATTACHMENTS) + whereCondition;
    android.database.Cursor cursor;
    cursor = null;
    try {
        cursor = getDatabase().rawQuery(sql, null);
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                var attachment = new it.feio.android.omninotes.models.Attachment(cursor.getLong(0), android.net.Uri.parse(cursor.getString(1)), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5));
                attachment.setNoteId(cursor.getLong(6));
                attachmentsList.add(attachment);
            } while (cursor.moveToNext() );
        }
    } finally {
        if (cursor != null) {
            cursor.close();
        }
    }
    return attachmentsList;
}


/**
 * Retrieves categories list from database
 *
 * @return List of categories
 */
public java.util.ArrayList<it.feio.android.omninotes.models.Category> getCategories() {
    java.util.ArrayList<it.feio.android.omninotes.models.Category> categoriesList;
    categoriesList = new java.util.ArrayList<>();
    java.lang.String sql;
    sql = (((((((((((((((((((((((((((((((((((("SELECT " + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_NAME) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_DESCRIPTION) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_COLOR) + ",") + " COUNT(") + it.feio.android.omninotes.db.DbHelper.KEY_ID) + ") count") + " FROM ") + it.feio.android.omninotes.db.DbHelper.TABLE_CATEGORY) + " LEFT JOIN (") + " SELECT ") + it.feio.android.omninotes.db.DbHelper.KEY_ID) + ", ") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY) + " FROM ") + it.feio.android.omninotes.db.DbHelper.TABLE_NOTES) + " WHERE ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS NOT 1") + ") USING( ") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY) + ") ") + " GROUP BY ") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_NAME) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_DESCRIPTION) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_COLOR) + " ORDER BY IFNULL(NULLIF(") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_NAME) + ", ''),'zzzzzzzz') ";
    android.database.Cursor cursor;
    cursor = null;
    try {
        cursor = getDatabase().rawQuery(sql, null);
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                categoriesList.add(new it.feio.android.omninotes.models.Category(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
            } while (cursor.moveToNext() );
        }
    } finally {
        if (cursor != null) {
            cursor.close();
        }
    }
    return categoriesList;
}


/**
 * Updates or insert a new a category
 *
 * @param category
 * 		Category to be updated or inserted
 * @return Rows affected or new inserted category ID
 */
public it.feio.android.omninotes.models.Category updateCategory(it.feio.android.omninotes.models.Category category) {
    android.content.ContentValues values;
    values = new android.content.ContentValues();
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID, category.getId() != null ? category.getId() : java.util.Calendar.getInstance().getTimeInMillis());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_NAME, category.getName());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_DESCRIPTION, category.getDescription());
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_COLOR, category.getColor());
    getDatabase(true).insertWithOnConflict(it.feio.android.omninotes.db.DbHelper.TABLE_CATEGORY, it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID, values, android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE);
    return category;
}


/**
 * Deletion of  a category
 *
 * @param category
 * 		Category to be deleted
 * @return Number 1 if category's record has been deleted, 0 otherwise
 */
public long deleteCategory(it.feio.android.omninotes.models.Category category) {
    long deleted;
    android.database.sqlite.SQLiteDatabase db;
    db = getDatabase(true);
    // Un-categorize notes associated with this category
    android.content.ContentValues values;
    values = new android.content.ContentValues();
    values.put(it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY, "");
    // Updating row
    db.update(it.feio.android.omninotes.db.DbHelper.TABLE_NOTES, values, it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY + " = ?", new java.lang.String[]{ java.lang.String.valueOf(category.getId()) });
    // Delete category
    deleted = db.delete(it.feio.android.omninotes.db.DbHelper.TABLE_CATEGORY, it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID + " = ?", new java.lang.String[]{ java.lang.String.valueOf(category.getId()) });
    return deleted;
}


/**
 * Get note Category
 */
public it.feio.android.omninotes.models.Category getCategory(java.lang.Long id) {
    it.feio.android.omninotes.models.Category category;
    category = null;
    java.lang.String sql;
    sql = (((((((((((("SELECT " + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_NAME) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_DESCRIPTION) + ",") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_COLOR) + " FROM ") + it.feio.android.omninotes.db.DbHelper.TABLE_CATEGORY) + " WHERE ") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY_ID) + " = ") + id;
    try (android.database.Cursor cursor = getDatabase().rawQuery(sql, null)) {
        if (cursor.moveToFirst()) {
            category = new it.feio.android.omninotes.models.Category(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
    }
    return category;
}


public int getCategorizedCount(it.feio.android.omninotes.models.Category category) {
    int count;
    count = 0;
    java.lang.String sql;
    sql = ((((("SELECT COUNT(*)" + " FROM ") + it.feio.android.omninotes.db.DbHelper.TABLE_NOTES) + " WHERE ") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY) + " = ") + category.getId();
    try (android.database.Cursor cursor = getDatabase().rawQuery(sql, null)) {
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
    }
    return count;
}


/**
 * Retrieves statistics data based on app usage
 */
public it.feio.android.omninotes.models.Stats getStats() {
    it.feio.android.omninotes.models.Stats mStats;
    mStats = new it.feio.android.omninotes.models.Stats();
    // Categories
    mStats.setCategories(getCategories().size());
    // Everything about notes and their text stats
    int notesActive;
    notesActive = 0;
    int notesArchived;
    notesArchived = 0;
    int notesTrashed;
    notesTrashed = 0;
    int reminders;
    reminders = 0;
    int remindersFuture;
    remindersFuture = 0;
    int checklists;
    checklists = 0;
    int notesMasked;
    notesMasked = 0;
    int tags;
    tags = 0;
    int locations;
    locations = 0;
    int totalWords;
    totalWords = 0;
    int totalChars;
    totalChars = 0;
    int maxWords;
    maxWords = 0;
    int maxChars;
    maxChars = 0;
    int avgWords;
    int avgChars;
    int words;
    int chars;
    java.util.List<it.feio.android.omninotes.models.Note> notes;
    notes = getAllNotes(false);
    for (it.feio.android.omninotes.models.Note note : notes) {
        if (java.lang.Boolean.TRUE.equals(note.isTrashed())) {
            notesTrashed++;
        } else if (note.isArchived()) {
            notesArchived++;
        } else {
            notesActive++;
        }
        if ((note.getAlarm() != null) && (java.lang.Long.parseLong(note.getAlarm()) > 0)) {
            if (java.lang.Long.parseLong(note.getAlarm()) > java.util.Calendar.getInstance().getTimeInMillis()) {
                remindersFuture++;
            } else {
                reminders++;
            }
        }
        if (java.lang.Boolean.TRUE.equals(note.isChecklist())) {
            checklists++;
        }
        if (java.lang.Boolean.TRUE.equals(note.isLocked())) {
            notesMasked++;
        }
        tags += it.feio.android.omninotes.utils.TagsHelper.retrieveTags(note).size();
        if ((note.getLongitude() != null) && (note.getLongitude() != 0)) {
            locations++;
        }
        words = it.feio.android.omninotes.helpers.NotesHelper.getWords(note);
        chars = it.feio.android.omninotes.helpers.NotesHelper.getChars(note);
        if (words > maxWords) {
            maxWords = words;
        }
        if (chars > maxChars) {
            maxChars = chars;
        }
        totalWords += words;
        totalChars += chars;
    }
    mStats.setNotesActive(notesActive);
    mStats.setNotesArchived(notesArchived);
    mStats.setNotesTrashed(notesTrashed);
    mStats.setReminders(reminders);
    mStats.setRemindersFutures(remindersFuture);
    mStats.setNotesChecklist(checklists);
    mStats.setNotesMasked(notesMasked);
    mStats.setTags(tags);
    mStats.setLocation(locations);
    switch(MUID_STATIC) {
        // DbHelper_1_BinaryMutator
        case 1034: {
            avgWords = totalWords * (!notes.isEmpty() ? notes.size() : 1);
            break;
        }
        default: {
        avgWords = totalWords / (!notes.isEmpty() ? notes.size() : 1);
        break;
    }
}
switch(MUID_STATIC) {
    // DbHelper_2_BinaryMutator
    case 2034: {
        avgChars = totalChars * (!notes.isEmpty() ? notes.size() : 1);
        break;
    }
    default: {
    avgChars = totalChars / (!notes.isEmpty() ? notes.size() : 1);
    break;
}
}
mStats.setWords(totalWords);
mStats.setWordsMax(maxWords);
mStats.setWordsAvg(avgWords);
mStats.setChars(totalChars);
mStats.setCharsMax(maxChars);
mStats.setCharsAvg(avgChars);
// Everything about attachments
int images;
images = 0;
int videos;
videos = 0;
int audioRecordings;
audioRecordings = 0;
int sketches;
sketches = 0;
int files;
files = 0;
java.util.List<it.feio.android.omninotes.models.Attachment> attachments;
attachments = getAllAttachments();
for (it.feio.android.omninotes.models.Attachment attachment : attachments) {
if (it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_IMAGE.equals(attachment.getMime_type())) {
    images++;
} else if (it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_VIDEO.equals(attachment.getMime_type())) {
    videos++;
} else if (it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_AUDIO.equals(attachment.getMime_type())) {
    audioRecordings++;
} else if (it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_SKETCH.equals(attachment.getMime_type())) {
    sketches++;
} else if (it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES.equals(attachment.getMime_type())) {
    files++;
}
}
mStats.setAttachments(attachments.size());
mStats.setImages(images);
mStats.setVideos(videos);
mStats.setAudioRecordings(audioRecordings);
mStats.setSketches(sketches);
mStats.setFiles(files);
return mStats;
}


public void setReminderFired(long noteId, boolean fired) {
android.content.ContentValues values;
values = new android.content.ContentValues();
values.put(it.feio.android.omninotes.db.DbHelper.KEY_REMINDER_FIRED, fired);
getDatabase(true).update(it.feio.android.omninotes.db.DbHelper.TABLE_NOTES, values, it.feio.android.omninotes.db.DbHelper.KEY_ID + " = ?", new java.lang.String[]{ java.lang.String.valueOf(noteId) });
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
