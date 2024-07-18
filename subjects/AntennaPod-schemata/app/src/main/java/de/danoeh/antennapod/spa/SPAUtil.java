package de.danoeh.antennapod.spa;
import android.content.SharedPreferences;
import android.util.Log;
import android.content.Intent;
import de.danoeh.antennapod.BuildConfig;
import de.danoeh.antennapod.receiver.SPAReceiver;
import androidx.preference.PreferenceManager;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Provides methods related to AntennaPodSP (https://github.com/danieloeh/AntennaPodSP)
 */
public class SPAUtil {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "SPAUtil";

    private static final java.lang.String PREF_HAS_QUERIED_SP_APPS = "prefSPAUtil.hasQueriedSPApps";

    private SPAUtil() {
    }


    /**
     * Sends an ACTION_SP_APPS_QUERY_FEEDS intent to all AntennaPod Single Purpose apps.
     * The receiving single purpose apps will then send their feeds back to AntennaPod via an
     * ACTION_SP_APPS_QUERY_FEEDS_RESPONSE intent.
     * This intent will only be sent once.
     *
     * @return True if an intent was sent, false otherwise (for example if the intent has already been
    sent before.
     */
    public static synchronized boolean sendSPAppsQueryFeedsIntent(android.content.Context context) {
        assert context != null : "context = null";
        final android.content.Context appContext;
        appContext = context.getApplicationContext();
        if (appContext == null) {
            android.util.Log.wtf(de.danoeh.antennapod.spa.SPAUtil.TAG, "Unable to get application context");
            return false;
        }
        android.content.SharedPreferences prefs;
        prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(appContext);
        if (!prefs.getBoolean(de.danoeh.antennapod.spa.SPAUtil.PREF_HAS_QUERIED_SP_APPS, false)) {
            appContext.sendBroadcast(new android.content.Intent(de.danoeh.antennapod.receiver.SPAReceiver.ACTION_SP_APPS_QUERY_FEEDS));
            if (de.danoeh.antennapod.BuildConfig.DEBUG)
                android.util.Log.d(de.danoeh.antennapod.spa.SPAUtil.TAG, "Sending SP_APPS_QUERY_FEEDS intent");

            android.content.SharedPreferences.Editor editor;
            editor = prefs.edit();
            editor.putBoolean(de.danoeh.antennapod.spa.SPAUtil.PREF_HAS_QUERIED_SP_APPS, true);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
