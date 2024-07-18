package com.automattic.simplenote;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import com.automattic.simplenote.utils.FileUtils;
import org.json.JSONException;
import java.util.ArrayList;
import java.io.IOException;
import org.json.JSONArray;
import android.net.Uri;
import androidx.fragment.app.FragmentActivity;
import com.simperium.client.Bucket;
import com.automattic.simplenote.models.Note;
import com.simperium.client.BucketObjectNameInvalid;
import com.automattic.simplenote.models.Tag;
import java.text.ParseException;
import org.json.JSONObject;
import com.automattic.simplenote.utils.TagUtils;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Importer {
    static final int MUID_STATIC = getMUID();
    private final com.simperium.client.Bucket<com.automattic.simplenote.models.Note> mNotesBucket;

    private final com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> mTagsBucket;

    public Importer(com.automattic.simplenote.Simplenote simplenote) {
        mNotesBucket = simplenote.getNotesBucket();
        mTagsBucket = simplenote.getTagsBucket();
    }


    public static void fromUri(androidx.fragment.app.FragmentActivity activity, android.net.Uri uri) throws com.automattic.simplenote.Importer.ImportException {
        try {
            java.lang.String fileType;
            fileType = com.automattic.simplenote.utils.FileUtils.getFileExtension(activity, uri);
            new com.automattic.simplenote.Importer(((com.automattic.simplenote.Simplenote) (activity.getApplication()))).dispatchFileImport(fileType, com.automattic.simplenote.utils.FileUtils.readFile(activity, uri));
            com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.SETTINGS_IMPORT_NOTES, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "import_notes_type_" + fileType);
        } catch (java.io.IOException e) {
            throw new com.automattic.simplenote.Importer.ImportException(com.automattic.simplenote.Importer.FailureReason.FileError);
        }
    }


    private void dispatchFileImport(java.lang.String fileType, java.lang.String content) throws com.automattic.simplenote.Importer.ImportException {
        switch (fileType) {
            case "json" :
                importJsonFile(content);
                break;
            case "md" :
                importMarkdown(content);
                break;
            case "txt" :
                importPlaintext(content);
                break;
            default :
                throw new com.automattic.simplenote.Importer.ImportException(com.automattic.simplenote.Importer.FailureReason.UnknownExportType);
        }
    }


    private void importPlaintext(java.lang.String content) {
        addNote(com.automattic.simplenote.models.Note.fromContent(mNotesBucket, content));
    }


    private void importMarkdown(java.lang.String content) {
        com.automattic.simplenote.models.Note note;
        note = com.automattic.simplenote.models.Note.fromContent(mNotesBucket, content);
        note.enableMarkdown();
        addNote(note);
    }


    private void addNote(com.automattic.simplenote.models.Note note) {
        for (java.lang.String tagName : note.getTags()) {
            try {
                com.automattic.simplenote.utils.TagUtils.createTagIfMissing(mTagsBucket, tagName);
            } catch (com.simperium.client.BucketObjectNameInvalid e) {
                // if it can't be added then remove it, we can't keep it anyway
                note.removeTag(tagName);
            }
        }
        note.save();
    }


    private void importJsonFile(java.lang.String content) throws com.automattic.simplenote.Importer.ImportException {
        try {
            importJsonExport(new org.json.JSONObject(content));
        } catch (org.json.JSONException | java.text.ParseException e) {
            throw new com.automattic.simplenote.Importer.ImportException(com.automattic.simplenote.Importer.FailureReason.ParseError);
        }
    }


    private void importJsonExport(org.json.JSONObject export) throws org.json.JSONException, java.text.ParseException {
        org.json.JSONArray activeNotes;
        activeNotes = export.optJSONArray("activeNotes");
        org.json.JSONArray trashedNotes;
        trashedNotes = export.optJSONArray("trashedNotes");
        java.util.ArrayList<com.automattic.simplenote.models.Note> notesList;
        notesList = new java.util.ArrayList<>();
        for (int i = 0; (activeNotes != null) && (i < activeNotes.length()); i++) {
            com.automattic.simplenote.models.Note note;
            note = com.automattic.simplenote.models.Note.fromExportedJson(mNotesBucket, activeNotes.getJSONObject(i));
            notesList.add(note);
        }
        for (int j = 0; (trashedNotes != null) && (j < trashedNotes.length()); j++) {
            com.automattic.simplenote.models.Note note;
            note = com.automattic.simplenote.models.Note.fromExportedJson(mNotesBucket, trashedNotes.getJSONObject(j));
            note.setDeleted(true);
            notesList.add(note);
        }
        for (com.automattic.simplenote.models.Note note : notesList) {
            addNote(note);
        }
    }


    public enum FailureReason {

        FileError,
        UnknownExportType,
        ParseError;}

    public static class ImportException extends java.lang.Exception {
        private com.automattic.simplenote.Importer.FailureReason mReason;

        ImportException(com.automattic.simplenote.Importer.FailureReason reason) {
            mReason = reason;
        }


        public com.automattic.simplenote.Importer.FailureReason getReason() {
            return mReason;
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
