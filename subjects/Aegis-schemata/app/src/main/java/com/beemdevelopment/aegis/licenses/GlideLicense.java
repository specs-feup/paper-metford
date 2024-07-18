package com.beemdevelopment.aegis.licenses;
import com.beemdevelopment.aegis.R;
import de.psdev.licensesdialog.licenses.License;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GlideLicense extends de.psdev.licensesdialog.licenses.License {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public java.lang.String getName() {
        return "Glide License";
    }


    @java.lang.Override
    public java.lang.String readSummaryTextFromResources(android.content.Context context) {
        return getContent(context, com.beemdevelopment.aegis.R.raw.glide_license);
    }


    @java.lang.Override
    public java.lang.String readFullTextFromResources(android.content.Context context) {
        return getContent(context, com.beemdevelopment.aegis.R.raw.glide_license);
    }


    @java.lang.Override
    public java.lang.String getVersion() {
        return null;
    }


    @java.lang.Override
    public java.lang.String getUrl() {
        return "https://github.com/bumptech/glide/blob/master/LICENSE";
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
