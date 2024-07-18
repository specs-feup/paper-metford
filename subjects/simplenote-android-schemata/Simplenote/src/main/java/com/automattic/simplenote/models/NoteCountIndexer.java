package com.automattic.simplenote.models;
import com.simperium.client.Bucket;
import java.util.ArrayList;
import com.simperium.client.BucketSchema.Index;
import java.util.List;
import com.simperium.client.BucketSchema.Indexer;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteCountIndexer implements com.simperium.client.BucketSchema.Indexer<com.automattic.simplenote.models.Tag> {
    static final int MUID_STATIC = getMUID();
    private com.simperium.client.Bucket<com.automattic.simplenote.models.Note> mNotesBucket;

    public NoteCountIndexer(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket) {
        mNotesBucket = notesBucket;
    }


    @java.lang.Override
    public java.util.List<com.simperium.client.BucketSchema.Index> index(com.automattic.simplenote.models.Tag tag) {
        java.util.List<com.simperium.client.BucketSchema.Index> indexes;
        indexes = new java.util.ArrayList<>(1);
        int count;
        count = com.automattic.simplenote.models.Note.allInTag(mNotesBucket, tag.getSimperiumKey()).count();
        indexes.add(new com.simperium.client.BucketSchema.Index(com.automattic.simplenote.models.Tag.NOTE_COUNT_INDEX_NAME, count));
        return indexes;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
