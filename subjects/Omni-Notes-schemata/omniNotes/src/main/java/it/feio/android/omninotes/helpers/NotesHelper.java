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
import it.feio.android.omninotes.utils.TagsHelper;
import static it.feio.android.omninotes.utils.ConstantsBase.MERGED_NOTES_SEPARATOR;
import java.util.ArrayList;
import lombok.experimental.UtilityClass;
import it.feio.android.omninotes.models.Attachment;
import static it.feio.android.checklistview.interfaces.Constants.CHECKED_SYM;
import org.apache.commons.lang3.ObjectUtils;
import it.feio.android.omninotes.helpers.count.CountFactory;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES;
import it.feio.android.omninotes.OmniNotes;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_VIDEO;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_AUDIO;
import static it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM;
import it.feio.android.omninotes.models.Note;
import org.apache.commons.lang3.StringUtils;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_IMAGE;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_SKETCH;
import it.feio.android.omninotes.utils.StorageHelper;
import java.util.List;
import it.feio.android.omninotes.models.StatsSingleNote;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class NotesHelper {
    static final int MUID_STATIC = getMUID();
    public static boolean haveSameId(it.feio.android.omninotes.models.Note note, it.feio.android.omninotes.models.Note currentNote) {
        return ((currentNote != null) && (currentNote.get_id() != null)) && currentNote.get_id().equals(note.get_id());
    }


    public static java.lang.StringBuilder appendContent(it.feio.android.omninotes.models.Note note, java.lang.StringBuilder content, boolean includeTitle) {
        if ((content.length() > 0) && ((!org.apache.commons.lang3.StringUtils.isEmpty(note.getTitle())) || (!org.apache.commons.lang3.StringUtils.isEmpty(note.getContent())))) {
            content.append(java.lang.System.getProperty("line.separator")).append(java.lang.System.getProperty("line.separator")).append(it.feio.android.omninotes.utils.ConstantsBase.MERGED_NOTES_SEPARATOR).append(java.lang.System.getProperty("line.separator")).append(java.lang.System.getProperty("line.separator"));
        }
        if (includeTitle && (!org.apache.commons.lang3.StringUtils.isEmpty(note.getTitle()))) {
            content.append(note.getTitle());
        }
        if ((!org.apache.commons.lang3.StringUtils.isEmpty(note.getTitle())) && (!org.apache.commons.lang3.StringUtils.isEmpty(note.getContent()))) {
            content.append(java.lang.System.getProperty("line.separator")).append(java.lang.System.getProperty("line.separator"));
        }
        if (!org.apache.commons.lang3.StringUtils.isEmpty(note.getContent())) {
            content.append(note.getContent());
        }
        return content;
    }


    public static void addAttachments(boolean keepMergedNotes, it.feio.android.omninotes.models.Note note, java.util.ArrayList<it.feio.android.omninotes.models.Attachment> attachments) {
        if (keepMergedNotes) {
            for (it.feio.android.omninotes.models.Attachment attachment : note.getAttachmentsList()) {
                attachments.add(it.feio.android.omninotes.utils.StorageHelper.createAttachmentFromUri(it.feio.android.omninotes.OmniNotes.getAppContext(), attachment.getUri()));
            }
        } else {
            attachments.addAll(note.getAttachmentsList());
        }
    }


    public static it.feio.android.omninotes.models.Note mergeNotes(java.util.List<it.feio.android.omninotes.models.Note> notes, boolean keepMergedNotes) {
        boolean locked;
        locked = false;
        java.util.ArrayList<it.feio.android.omninotes.models.Attachment> attachments;
        attachments = new java.util.ArrayList<>();
        java.lang.String reminder;
        reminder = null;
        java.lang.String reminderRecurrenceRule;
        reminderRecurrenceRule = null;
        java.lang.Double latitude;
        latitude = null;
        java.lang.Double longitude;
        longitude = null;
        it.feio.android.omninotes.models.Note mergedNote;
        mergedNote = new it.feio.android.omninotes.models.Note();
        mergedNote.setTitle(notes.get(0).getTitle());
        mergedNote.setArchived(notes.get(0).isArchived());
        mergedNote.setCategory(notes.get(0).getCategory());
        java.lang.StringBuilder content;
        content = new java.lang.StringBuilder();
        // Just first note title must not be included into the content
        boolean includeTitle;
        includeTitle = false;
        for (it.feio.android.omninotes.models.Note note : notes) {
            it.feio.android.omninotes.helpers.NotesHelper.appendContent(note, content, includeTitle);
            locked = locked || note.isLocked();
            java.lang.String currentReminder;
            currentReminder = note.getAlarm();
            if ((!org.apache.commons.lang3.StringUtils.isEmpty(currentReminder)) && (reminder == null)) {
                reminder = currentReminder;
                reminderRecurrenceRule = note.getRecurrenceRule();
            }
            latitude = org.apache.commons.lang3.ObjectUtils.defaultIfNull(latitude, note.getLatitude());
            longitude = org.apache.commons.lang3.ObjectUtils.defaultIfNull(longitude, note.getLongitude());
            it.feio.android.omninotes.helpers.NotesHelper.addAttachments(keepMergedNotes, note, attachments);
            includeTitle = true;
        }
        mergedNote.setContent(content.toString());
        mergedNote.setLocked(locked);
        mergedNote.setAlarm(reminder);
        mergedNote.setRecurrenceRule(reminderRecurrenceRule);
        mergedNote.setLatitude(latitude);
        mergedNote.setLongitude(longitude);
        mergedNote.setAttachmentsList(attachments);
        return mergedNote;
    }


    /**
     * Retrieves statistics data for a single note
     */
    public static it.feio.android.omninotes.models.StatsSingleNote getNoteInfos(it.feio.android.omninotes.models.Note note) {
        it.feio.android.omninotes.models.StatsSingleNote infos;
        infos = new it.feio.android.omninotes.models.StatsSingleNote();
        int words;
        int chars;
        if (note.isChecklist()) {
            infos.setChecklistCompletedItemsNumber(org.apache.commons.lang3.StringUtils.countMatches(note.getContent(), it.feio.android.checklistview.interfaces.Constants.CHECKED_SYM));
            switch(MUID_STATIC) {
                // NotesHelper_0_BinaryMutator
                case 125: {
                    infos.setChecklistItemsNumber(infos.getChecklistCompletedItemsNumber() - org.apache.commons.lang3.StringUtils.countMatches(note.getContent(), it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM));
                    break;
                }
                default: {
                infos.setChecklistItemsNumber(infos.getChecklistCompletedItemsNumber() + org.apache.commons.lang3.StringUtils.countMatches(note.getContent(), it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM));
                break;
            }
        }
    }
    infos.setTags(it.feio.android.omninotes.utils.TagsHelper.retrieveTags(note).size());
    words = it.feio.android.omninotes.helpers.NotesHelper.getWords(note);
    chars = it.feio.android.omninotes.helpers.NotesHelper.getChars(note);
    infos.setWords(words);
    infos.setChars(chars);
    int attachmentsAll;
    attachmentsAll = 0;
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
    for (it.feio.android.omninotes.models.Attachment attachment : note.getAttachmentsList()) {
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
        attachmentsAll++;
    }
    infos.setAttachments(attachmentsAll);
    infos.setImages(images);
    infos.setVideos(videos);
    infos.setAudioRecordings(audioRecordings);
    infos.setSketches(sketches);
    infos.setFiles(files);
    if (note.getCategory() != null) {
        infos.setCategoryName(note.getCategory().getName());
    }
    return infos;
}


/**
 * Counts words in a note
 */
public static int getWords(it.feio.android.omninotes.models.Note note) {
    return it.feio.android.omninotes.helpers.count.CountFactory.getWordCounter().countWords(note);
}


/**
 * Counts chars in a note
 */
public static int getChars(it.feio.android.omninotes.models.Note note) {
    return it.feio.android.omninotes.helpers.count.CountFactory.getWordCounter().countChars(note);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
