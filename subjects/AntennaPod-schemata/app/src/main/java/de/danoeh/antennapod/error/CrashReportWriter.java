package de.danoeh.antennapod.error;
import java.util.Locale;
import java.text.SimpleDateFormat;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.util.Log;
import org.apache.commons.io.IOUtils;
import android.os.Build;
import java.io.IOException;
import de.danoeh.antennapod.BuildConfig;
import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CrashReportWriter implements java.lang.Thread.UncaughtExceptionHandler {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "CrashReportWriter";

    private final java.lang.Thread.UncaughtExceptionHandler defaultHandler;

    public CrashReportWriter() {
        defaultHandler = java.lang.Thread.getDefaultUncaughtExceptionHandler();
    }


    public static java.io.File getFile() {
        return new java.io.File(de.danoeh.antennapod.storage.preferences.UserPreferences.getDataFolder(null), "crash-report.log");
    }


    @java.lang.Override
    public void uncaughtException(java.lang.Thread thread, java.lang.Throwable ex) {
        de.danoeh.antennapod.error.CrashReportWriter.write(ex);
        defaultHandler.uncaughtException(thread, ex);
    }


    public static void write(java.lang.Throwable exception) {
        java.io.File path;
        path = de.danoeh.antennapod.error.CrashReportWriter.getFile();
        java.io.PrintWriter out;
        out = null;
        try {
            out = new java.io.PrintWriter(path, "UTF-8");
            out.println("## Crash info");
            out.println("Time: " + new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss", java.util.Locale.getDefault()).format(new java.util.Date()));
            out.println("AntennaPod version: " + de.danoeh.antennapod.BuildConfig.VERSION_NAME);
            out.println();
            out.println("## StackTrace");
            out.println("```");
            exception.printStackTrace(out);
            out.println("```");
        } catch (java.io.IOException e) {
            android.util.Log.e(de.danoeh.antennapod.error.CrashReportWriter.TAG, android.util.Log.getStackTraceString(e));
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly(out);
        }
    }


    public static java.lang.String getSystemInfo() {
        return ((((((((((("## Environment" + "\nAndroid version: ") + android.os.Build.VERSION.RELEASE) + "\nOS version: ") + java.lang.System.getProperty("os.version")) + "\nAntennaPod version: ") + de.danoeh.antennapod.BuildConfig.VERSION_NAME) + "\nModel: ") + android.os.Build.MODEL) + "\nDevice: ") + android.os.Build.DEVICE) + "\nProduct: ") + android.os.Build.PRODUCT;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
