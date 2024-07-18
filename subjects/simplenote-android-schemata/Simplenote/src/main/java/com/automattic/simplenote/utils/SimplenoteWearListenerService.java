package com.automattic.simplenote.utils;
import com.google.android.gms.wearable.MessageEvent;
import com.automattic.simplenote.R;
import com.automattic.simplenote.models.Note;
import java.util.Calendar;
import android.text.TextUtils;
import com.google.android.gms.wearable.WearableListenerService;
import android.widget.Toast;
import com.automattic.simplenote.Simplenote;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
// Create a new note after receiving a 'new-note' message from the wearable
public class SimplenoteWearListenerService extends com.google.android.gms.wearable.WearableListenerService {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public void onMessageReceived(com.google.android.gms.wearable.MessageEvent messageEvent) {
        if (messageEvent.getPath().equals("new-note")) {
            if ((messageEvent.getData() != null) && (messageEvent.getData().length > 0)) {
                java.lang.String voiceNoteString;
                voiceNoteString = new java.lang.String(messageEvent.getData());
                com.automattic.simplenote.Simplenote application;
                application = ((com.automattic.simplenote.Simplenote) (getApplication()));
                if ((!android.text.TextUtils.isEmpty(voiceNoteString)) && (application.getNotesBucket() != null)) {
                    com.automattic.simplenote.models.Note note;
                    note = application.getNotesBucket().newObject();
                    note.setCreationDate(java.util.Calendar.getInstance());
                    note.setModificationDate(note.getCreationDate());
                    note.setContent(voiceNoteString);
                    note.save();
                    android.widget.Toast.makeText(application, getString(com.automattic.simplenote.R.string.note_added), android.widget.Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
