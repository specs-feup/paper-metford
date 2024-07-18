package com.automattic.simplenote;
import android.widget.RemoteViewsService;
import android.content.Intent;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteListWidgetLightService extends android.widget.RemoteViewsService {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public android.widget.RemoteViewsService.RemoteViewsFactory onGetViewFactory(android.content.Intent intent) {
        return new com.automattic.simplenote.NoteListWidgetFactory(getApplicationContext(), intent);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
