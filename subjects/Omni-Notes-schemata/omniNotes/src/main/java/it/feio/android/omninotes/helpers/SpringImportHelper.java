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
import it.feio.android.omninotes.models.Category;
import it.feio.android.springpadimporter.models.SpringpadAttachment;
import java.util.HashMap;
import java.net.MalformedURLException;
import it.feio.android.omninotes.helpers.notifications.NotificationsHelper;
import android.net.Uri;
import it.feio.android.omninotes.models.Attachment;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.utils.ReminderHelper;
import it.feio.android.springpadimporter.models.SpringpadElement;
import android.widget.Toast;
import java.util.List;
import it.feio.android.springpadimporter.Importer;
import android.graphics.Color;
import android.text.TextUtils;
import java.io.IOException;
import android.content.Intent;
import android.text.Html;
import it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames;
import exceptions.ImportException;
import it.feio.android.springpadimporter.models.SpringpadItem;
import it.feio.android.omninotes.models.Note;
import it.feio.android.omninotes.utils.StorageHelper;
import it.feio.android.omninotes.db.DbHelper;
import java.io.File;
import it.feio.android.springpadimporter.models.SpringpadComment;
import it.feio.android.omninotes.utils.GeocodeHelper;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SpringImportHelper {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ACTION_DATA_IMPORT_SPRINGPAD = "action_data_import_springpad";

    public static final java.lang.String EXTRA_SPRINGPAD_BACKUP = "extra_springpad_backup";

    private final android.content.Context context;

    private int importedSpringpadNotes;

    private int importedSpringpadNotebooks;

    public SpringImportHelper(android.content.Context context) {
        this.context = context;
    }


    /**
     * Imports notes and notebooks from Springpad exported archive
     */
    public synchronized void importDataFromSpringpad(android.content.Intent intent, it.feio.android.omninotes.helpers.notifications.NotificationsHelper mNotificationsHelper) {
        java.lang.String backupPath;
        backupPath = intent.getStringExtra(it.feio.android.omninotes.helpers.SpringImportHelper.EXTRA_SPRINGPAD_BACKUP);
        it.feio.android.springpadimporter.Importer importer;
        importer = new it.feio.android.springpadimporter.Importer();
        try {
            importer.setZipProgressesListener((int percentage) -> mNotificationsHelper.setMessage(((context.getString(it.feio.android.omninotes.R.string.extracted) + " ") + percentage) + "%").show());
            importer.doImport(backupPath);
            // Updating notification
            updateImportNotification(importer, mNotificationsHelper);
        } catch (exceptions.ImportException e) {
            new it.feio.android.omninotes.helpers.notifications.NotificationsHelper(context).createStandardNotification(it.feio.android.omninotes.helpers.notifications.NotificationChannels.NotificationChannelNames.BACKUPS, it.feio.android.omninotes.R.drawable.ic_emoticon_sad_white_24dp, (context.getString(it.feio.android.omninotes.R.string.import_fail) + ": ") + e.getMessage(), null).setLedActive().show();
            return;
        }
        java.util.List<it.feio.android.springpadimporter.models.SpringpadElement> elements;
        elements = importer.getSpringpadNotes();
        // If nothing is retrieved it will exit
        if ((elements == null) || elements.isEmpty()) {
            return;
        }
        // These maps are used to associate with post processing notes to categories (notebooks)
        java.util.HashMap<java.lang.String, it.feio.android.omninotes.models.Category> categoriesWithUuid;
        categoriesWithUuid = new java.util.HashMap<>();
        // Adds all the notebooks (categories)
        for (it.feio.android.springpadimporter.models.SpringpadElement springpadElement : importer.getNotebooks()) {
            it.feio.android.omninotes.models.Category cat;
            cat = new it.feio.android.omninotes.models.Category();
            cat.setName(springpadElement.getName());
            cat.setColor(java.lang.String.valueOf(android.graphics.Color.parseColor("#F9EA1B")));
            it.feio.android.omninotes.db.DbHelper.getInstance().updateCategory(cat);
            categoriesWithUuid.put(springpadElement.getUuid(), cat);
            // Updating notification
            importedSpringpadNotebooks++;
            updateImportNotification(importer, mNotificationsHelper);
        }
        // And creates a default one for notes without notebook
        it.feio.android.omninotes.models.Category defaulCategory;
        defaulCategory = new it.feio.android.omninotes.models.Category();
        defaulCategory.setName("Springpad");
        defaulCategory.setColor(java.lang.String.valueOf(android.graphics.Color.parseColor("#F9EA1B")));
        it.feio.android.omninotes.db.DbHelper.getInstance().updateCategory(defaulCategory);
        // And then notes are created
        it.feio.android.omninotes.models.Note note;
        it.feio.android.omninotes.models.Attachment mAttachment;
        mAttachment = null;
        android.net.Uri uri;
        for (it.feio.android.springpadimporter.models.SpringpadElement springpadElement : importer.getNotes()) {
            note = new it.feio.android.omninotes.models.Note();
            // Title
            note.setTitle(springpadElement.getName());
            // Content dependent from type of Springpad note
            java.lang.StringBuilder content;
            content = new java.lang.StringBuilder();
            content.append(android.text.TextUtils.isEmpty(springpadElement.getText()) ? "" : android.text.Html.fromHtml(springpadElement.getText()));
            content.append(android.text.TextUtils.isEmpty(springpadElement.getDescription()) ? "" : springpadElement.getDescription());
            // Some notes could have been exported wrongly
            if (springpadElement.getType() == null) {
                android.widget.Toast.makeText(context, context.getString(it.feio.android.omninotes.R.string.error), android.widget.Toast.LENGTH_SHORT).show();
                continue;
            }
            if (springpadElement.getType().equals(it.feio.android.springpadimporter.models.SpringpadElement.TYPE_VIDEO)) {
                try {
                    content.append(java.lang.System.getProperty("line.separator")).append(springpadElement.getVideos().get(0));
                } catch (java.lang.IndexOutOfBoundsException e) {
                    content.append(java.lang.System.getProperty("line.separator")).append(springpadElement.getUrl());
                }
            }
            if (springpadElement.getType().equals(it.feio.android.springpadimporter.models.SpringpadElement.TYPE_TVSHOW)) {
                content.append(java.lang.System.getProperty("line.separator")).append(android.text.TextUtils.join(", ", springpadElement.getCast()));
            }
            if (springpadElement.getType().equals(it.feio.android.springpadimporter.models.SpringpadElement.TYPE_BOOK)) {
                content.append(java.lang.System.getProperty("line.separator")).append("Author: ").append(springpadElement.getAuthor()).append(java.lang.System.getProperty("line.separator")).append("Publication date: ").append(springpadElement.getPublicationDate());
            }
            if (springpadElement.getType().equals(it.feio.android.springpadimporter.models.SpringpadElement.TYPE_RECIPE)) {
                content.append(java.lang.System.getProperty("line.separator")).append("Ingredients: ").append(springpadElement.getIngredients()).append(java.lang.System.getProperty("line.separator")).append("Directions: ").append(springpadElement.getDirections());
            }
            if (springpadElement.getType().equals(it.feio.android.springpadimporter.models.SpringpadElement.TYPE_BOOKMARK)) {
                content.append(java.lang.System.getProperty("line.separator")).append(springpadElement.getUrl());
            }
            if (springpadElement.getType().equals(it.feio.android.springpadimporter.models.SpringpadElement.TYPE_BUSINESS) && (springpadElement.getPhoneNumbers() != null)) {
                content.append(java.lang.System.getProperty("line.separator")).append("Phone number: ").append(springpadElement.getPhoneNumbers().getPhone());
            }
            if (springpadElement.getType().equals(it.feio.android.springpadimporter.models.SpringpadElement.TYPE_PRODUCT)) {
                content.append(java.lang.System.getProperty("line.separator")).append("Category: ").append(springpadElement.getCategory()).append(java.lang.System.getProperty("line.separator")).append("Manufacturer: ").append(springpadElement.getManufacturer()).append(java.lang.System.getProperty("line.separator")).append("Price: ").append(springpadElement.getPrice());
            }
            if (springpadElement.getType().equals(it.feio.android.springpadimporter.models.SpringpadElement.TYPE_WINE)) {
                content.append(java.lang.System.getProperty("line.separator")).append("Wine type: ").append(springpadElement.getWine_type()).append(java.lang.System.getProperty("line.separator")).append("Varietal: ").append(springpadElement.getVarietal()).append(java.lang.System.getProperty("line.separator")).append("Price: ").append(springpadElement.getPrice());
            }
            if (springpadElement.getType().equals(it.feio.android.springpadimporter.models.SpringpadElement.TYPE_ALBUM)) {
                content.append(java.lang.System.getProperty("line.separator")).append("Artist: ").append(springpadElement.getArtist());
            }
            for (it.feio.android.springpadimporter.models.SpringpadComment springpadComment : springpadElement.getComments()) {
                content.append(java.lang.System.getProperty("line.separator")).append(springpadComment.getCommenter()).append(" commented at 0").append(springpadComment.getDate()).append(": ").append(springpadElement.getArtist());
            }
            note.setContent(content.toString());
            // Checklists
            if (springpadElement.getType().equals(it.feio.android.springpadimporter.models.SpringpadElement.TYPE_CHECKLIST)) {
                java.lang.StringBuilder sb;
                sb = new java.lang.StringBuilder();
                java.lang.String checkmark;
                for (it.feio.android.springpadimporter.models.SpringpadItem mSpringpadItem : springpadElement.getItems()) {
                    checkmark = (mSpringpadItem.getComplete()) ? it.feio.android.checklistview.interfaces.Constants.CHECKED_SYM : it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM;
                    sb.append(checkmark).append(mSpringpadItem.getName()).append(java.lang.System.getProperty("line.separator"));
                }
                note.setContent(sb.toString());
                note.setChecklist(true);
            }
            // Tags
            java.lang.String tags;
            tags = (springpadElement.getTags().size() > 0) ? "#" + android.text.TextUtils.join(" #", springpadElement.getTags()) : "";
            if (note.isChecklist()) {
                note.setTitle(note.getTitle() + tags);
            } else {
                note.setContent((note.getContent() + java.lang.System.getProperty("line.separator")) + tags);
            }
            // Address
            java.lang.String address;
            address = (springpadElement.getAddresses() != null) ? springpadElement.getAddresses().getAddress() : "";
            if (!android.text.TextUtils.isEmpty(address)) {
                try {
                    double[] coords;
                    coords = it.feio.android.omninotes.utils.GeocodeHelper.getCoordinatesFromAddress(context, address);
                    note.setLatitude(coords[0]);
                    note.setLongitude(coords[1]);
                } catch (java.io.IOException e) {
                    it.feio.android.omninotes.helpers.LogDelegate.e("An error occurred trying to resolve address to coords during Springpad " + "import");
                }
                note.setAddress(address);
            }
            // Reminder
            if (springpadElement.getDate() != null) {
                note.setAlarm(springpadElement.getDate().getTime());
            }
            // Creation, modification, category
            note.setCreation(springpadElement.getCreated().getTime());
            note.setLastModification(springpadElement.getModified().getTime());
            // Image
            java.lang.String image;
            image = springpadElement.getImage();
            if (!android.text.TextUtils.isEmpty(image)) {
                try {
                    java.io.File file;
                    file = it.feio.android.omninotes.utils.StorageHelper.createNewAttachmentFileFromHttp(context, image);
                    uri = android.net.Uri.fromFile(file);
                    java.lang.String mimeType;
                    mimeType = it.feio.android.omninotes.utils.StorageHelper.getMimeType(uri.getPath());
                    mAttachment = new it.feio.android.omninotes.models.Attachment(uri, mimeType);
                } catch (java.net.MalformedURLException e) {
                    uri = android.net.Uri.parse(importer.getWorkingPath() + image);
                    mAttachment = it.feio.android.omninotes.utils.StorageHelper.createAttachmentFromUri(context, uri, true);
                } catch (java.io.IOException e) {
                    it.feio.android.omninotes.helpers.LogDelegate.e("Error retrieving Springpad online image");
                }
                if (mAttachment != null) {
                    note.addAttachment(mAttachment);
                }
                mAttachment = null;
            }
            // Other attachments
            for (it.feio.android.springpadimporter.models.SpringpadAttachment springpadAttachment : springpadElement.getAttachments()) {
                // The attachment could be the image itself so it's jumped
                if ((image != null) && image.equals(springpadAttachment.getUrl())) {
                    continue;
                }
                if (android.text.TextUtils.isEmpty(springpadAttachment.getUrl())) {
                    continue;
                }
                // Tries first with online images
                try {
                    java.io.File file;
                    file = it.feio.android.omninotes.utils.StorageHelper.createNewAttachmentFileFromHttp(context, springpadAttachment.getUrl());
                    uri = android.net.Uri.fromFile(file);
                    java.lang.String mimeType;
                    mimeType = it.feio.android.omninotes.utils.StorageHelper.getMimeType(uri.getPath());
                    mAttachment = new it.feio.android.omninotes.models.Attachment(uri, mimeType);
                } catch (java.net.MalformedURLException e) {
                    uri = android.net.Uri.parse(importer.getWorkingPath() + springpadAttachment.getUrl());
                    mAttachment = it.feio.android.omninotes.utils.StorageHelper.createAttachmentFromUri(context, uri, true);
                } catch (java.io.IOException e) {
                    it.feio.android.omninotes.helpers.LogDelegate.e("Error retrieving Springpad online image");
                }
                if (mAttachment != null) {
                    note.addAttachment(mAttachment);
                }
                mAttachment = null;
            }
            // If the note has a category is added to the map to be post-processed
            if (!springpadElement.getNotebooks().isEmpty()) {
                note.setCategory(categoriesWithUuid.get(springpadElement.getNotebooks().get(0)));
            } else {
                note.setCategory(defaulCategory);
            }
            // The note is saved
            it.feio.android.omninotes.db.DbHelper.getInstance().updateNote(note, false);
            it.feio.android.omninotes.utils.ReminderHelper.addReminder(context, note);
            // Updating notification
            importedSpringpadNotes++;
            updateImportNotification(importer, mNotificationsHelper);
        }
        // Delete temp data
        try {
            importer.clean();
        } catch (java.io.IOException e) {
            it.feio.android.omninotes.helpers.LogDelegate.w("Springpad import temp files not deleted");
        }
    }


    private void updateImportNotification(it.feio.android.springpadimporter.Importer importer, it.feio.android.omninotes.helpers.notifications.NotificationsHelper mNotificationsHelper) {
        mNotificationsHelper.setMessage(((((((((((((((importer.getNotebooksCount() + " ") + context.getString(it.feio.android.omninotes.R.string.categories)) + " (") + importedSpringpadNotebooks) + " ") + context.getString(it.feio.android.omninotes.R.string.imported)) + "), ") + (+importer.getNotesCount())) + " ") + context.getString(it.feio.android.omninotes.R.string.notes)) + " (") + importedSpringpadNotes) + " ") + context.getString(it.feio.android.omninotes.R.string.imported)) + ")").show();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
