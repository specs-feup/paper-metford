package de.danoeh.antennapod.config;
import de.danoeh.antennapod.PodcastApp;
import de.danoeh.antennapod.core.ApplicationCallbacks;
import android.app.Application;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ApplicationCallbacksImpl implements de.danoeh.antennapod.core.ApplicationCallbacks {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public android.app.Application getApplicationInstance() {
        return de.danoeh.antennapod.PodcastApp.getInstance();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
