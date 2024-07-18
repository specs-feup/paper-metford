package com.automattic.simplenote.models;
import java.util.HashMap;
import com.simperium.client.FullTextIndex;
import java.util.Map;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteFullTextIndexer implements com.simperium.client.FullTextIndex.Indexer<com.automattic.simplenote.models.Note> {
    static final int MUID_STATIC = getMUID();
    @java.lang.SuppressWarnings("unused")
    public static final java.lang.String COMMA = ", ";

    public static final java.lang.String[] INDEXES = com.automattic.simplenote.models.Note.FULL_TEXT_INDEXES;

    @java.lang.Override
    public java.util.Map<java.lang.String, java.lang.String> index(java.lang.String[] keys, com.automattic.simplenote.models.Note note) {
        java.util.Map<java.lang.String, java.lang.String> values;
        values = new java.util.HashMap<>(keys.length);
        values.put(com.automattic.simplenote.models.NoteFullTextIndexer.INDEXES[0], note.getTitle());
        values.put(com.automattic.simplenote.models.NoteFullTextIndexer.INDEXES[1], note.getContent());
        return values;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
