package com.automattic.simplenote.models;
import java.util.ArrayList;
import com.simperium.client.BucketSchema.Index;
import java.util.List;
import com.simperium.client.BucketSchema.Indexer;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteIndexer implements com.simperium.client.BucketSchema.Indexer<com.automattic.simplenote.models.Note> {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public java.util.List<com.simperium.client.BucketSchema.Index> index(com.automattic.simplenote.models.Note note) {
        java.util.List<com.simperium.client.BucketSchema.Index> indexes;
        indexes = new java.util.ArrayList<>();
        indexes.add(new com.simperium.client.BucketSchema.Index(com.automattic.simplenote.models.Note.PINNED_INDEX_NAME, note.isPinned()));
        indexes.add(new com.simperium.client.BucketSchema.Index(com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME, note.getContentPreview()));
        indexes.add(new com.simperium.client.BucketSchema.Index(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME, note.getTitle()));
        indexes.add(new com.simperium.client.BucketSchema.Index(com.automattic.simplenote.models.Note.MODIFIED_INDEX_NAME, note.getModificationDate().getTimeInMillis()));
        indexes.add(new com.simperium.client.BucketSchema.Index(com.automattic.simplenote.models.Note.CREATED_INDEX_NAME, note.getCreationDate().getTimeInMillis()));
        return indexes;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
