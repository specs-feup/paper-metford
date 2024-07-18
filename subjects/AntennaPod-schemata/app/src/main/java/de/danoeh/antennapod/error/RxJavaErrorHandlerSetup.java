package de.danoeh.antennapod.error;
import io.reactivex.plugins.RxJavaPlugins;
import android.util.Log;
import de.danoeh.antennapod.BuildConfig;
import io.reactivex.exceptions.UndeliverableException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RxJavaErrorHandlerSetup {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "RxJavaErrorHandler";

    private RxJavaErrorHandlerSetup() {
    }


    public static void setupRxJavaErrorHandler() {
        io.reactivex.plugins.RxJavaPlugins.setErrorHandler((java.lang.Throwable exception) -> {
            if (exception instanceof io.reactivex.exceptions.UndeliverableException) {
                // Probably just disposed because the fragment was left
                android.util.Log.d(de.danoeh.antennapod.error.RxJavaErrorHandlerSetup.TAG, "Ignored exception: " + android.util.Log.getStackTraceString(exception));
                return;
            }
            // Usually, undeliverable exceptions are wrapped in an UndeliverableException.
            // If an undeliverable exception is a NPE (or some others), wrapping does not happen.
            // AntennaPod threads might throw NPEs after disposing because we set controllers to null.
            // Just swallow all exceptions here.
            android.util.Log.e(de.danoeh.antennapod.error.RxJavaErrorHandlerSetup.TAG, android.util.Log.getStackTraceString(exception));
            de.danoeh.antennapod.error.CrashReportWriter.write(exception);
            if (de.danoeh.antennapod.BuildConfig.DEBUG) {
                java.lang.Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(java.lang.Thread.currentThread(), exception);
            }
        });
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
