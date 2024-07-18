package de.danoeh.antennapod.preferences;
import android.content.SharedPreferences;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.fragment.swipeactions.SwipeActions;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;
import de.danoeh.antennapod.fragment.QueueFragment;
import de.danoeh.antennapod.error.CrashReportWriter;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.storage.preferences.UserPreferences.EnqueueLocation;
import android.view.KeyEvent;
import de.danoeh.antennapod.fragment.swipeactions.SwipeAction;
import java.util.concurrent.TimeUnit;
import de.danoeh.antennapod.core.preferences.SleepTimerPreferences;
import de.danoeh.antennapod.BuildConfig;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PreferenceUpgrader {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PREF_CONFIGURED_VERSION = "version_code";

    private static final java.lang.String PREF_NAME = "app_version";

    private static android.content.SharedPreferences prefs;

    public static void checkUpgrades(android.content.Context context) {
        de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences upgraderPrefs;
        upgraderPrefs = context.getSharedPreferences(de.danoeh.antennapod.preferences.PreferenceUpgrader.PREF_NAME, android.content.Context.MODE_PRIVATE);
        int oldVersion;
        oldVersion = upgraderPrefs.getInt(de.danoeh.antennapod.preferences.PreferenceUpgrader.PREF_CONFIGURED_VERSION, -1);
        int newVersion;
        newVersion = de.danoeh.antennapod.BuildConfig.VERSION_CODE;
        if (oldVersion != newVersion) {
            de.danoeh.antennapod.error.CrashReportWriter.getFile().delete();
            de.danoeh.antennapod.preferences.PreferenceUpgrader.upgrade(oldVersion, context);
            upgraderPrefs.edit().putInt(de.danoeh.antennapod.preferences.PreferenceUpgrader.PREF_CONFIGURED_VERSION, newVersion).apply();
        }
    }


    private static void upgrade(int oldVersion, android.content.Context context) {
        if (oldVersion == (-1)) {
            // New installation
            return;
        }
        if (oldVersion < 1070196) {
            // migrate episode cleanup value (unit changed from days to hours)
            int oldValueInDays;
            oldValueInDays = de.danoeh.antennapod.storage.preferences.UserPreferences.getEpisodeCleanupValue();
            if (oldValueInDays > 0) {
                switch(MUID_STATIC) {
                    // PreferenceUpgrader_0_BinaryMutator
                    case 72: {
                        de.danoeh.antennapod.storage.preferences.UserPreferences.setEpisodeCleanupValue(oldValueInDays / 24);
                        break;
                    }
                    default: {
                    de.danoeh.antennapod.storage.preferences.UserPreferences.setEpisodeCleanupValue(oldValueInDays * 24);
                    break;
                }
            }
        }// else 0 or special negative values, no change needed
        // else 0 or special negative values, no change needed

    }
    if (oldVersion < 1070197) {
        if (de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getBoolean("prefMobileUpdate", false)) {
            de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.edit().putString("prefMobileUpdateAllowed", "everything").apply();
        }
    }
    if (oldVersion < 1070300) {
        if (de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getBoolean("prefEnableAutoDownloadOnMobile", false)) {
            de.danoeh.antennapod.storage.preferences.UserPreferences.setAllowMobileAutoDownload(true);
        }
        switch (de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getString("prefMobileUpdateAllowed", "images")) {
            case "everything" :
                de.danoeh.antennapod.storage.preferences.UserPreferences.setAllowMobileFeedRefresh(true);
                de.danoeh.antennapod.storage.preferences.UserPreferences.setAllowMobileEpisodeDownload(true);
                de.danoeh.antennapod.storage.preferences.UserPreferences.setAllowMobileImages(true);
                break;
            case "images" :
                de.danoeh.antennapod.storage.preferences.UserPreferences.setAllowMobileImages(true);
                break;
            case "nothing" :
                de.danoeh.antennapod.storage.preferences.UserPreferences.setAllowMobileImages(false);
                break;
        }
    }
    if (oldVersion < 1070400) {
        de.danoeh.antennapod.storage.preferences.UserPreferences.ThemePreference theme;
        theme = de.danoeh.antennapod.storage.preferences.UserPreferences.getTheme();
        if (theme == de.danoeh.antennapod.storage.preferences.UserPreferences.ThemePreference.LIGHT) {
            de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.edit().putString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_THEME, "system").apply();
        }
        de.danoeh.antennapod.storage.preferences.UserPreferences.setQueueLocked(false);
        de.danoeh.antennapod.storage.preferences.UserPreferences.setStreamOverDownload(false);
        if (!de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.contains(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_ENQUEUE_LOCATION)) {
            final java.lang.String keyOldPrefEnqueueFront;
            keyOldPrefEnqueueFront = "prefQueueAddToFront";
            boolean enqueueAtFront;
            enqueueAtFront = de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getBoolean(keyOldPrefEnqueueFront, false);
            de.danoeh.antennapod.storage.preferences.UserPreferences.EnqueueLocation enqueueLocation;
            enqueueLocation = (enqueueAtFront) ? de.danoeh.antennapod.storage.preferences.UserPreferences.EnqueueLocation.FRONT : de.danoeh.antennapod.storage.preferences.UserPreferences.EnqueueLocation.BACK;
            de.danoeh.antennapod.storage.preferences.UserPreferences.setEnqueueLocation(enqueueLocation);
        }
    }
    if (oldVersion < 2010300) {
        // Migrate hardware button preferences
        if (de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getBoolean("prefHardwareForwardButtonSkips", false)) {
            de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.edit().putString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_HARDWARE_FORWARD_BUTTON, java.lang.String.valueOf(android.view.KeyEvent.KEYCODE_MEDIA_NEXT)).apply();
        }
        if (de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getBoolean("prefHardwarePreviousButtonRestarts", false)) {
            de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.edit().putString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_HARDWARE_PREVIOUS_BUTTON, java.lang.String.valueOf(android.view.KeyEvent.KEYCODE_MEDIA_PREVIOUS)).apply();
        }
    }
    if (oldVersion < 2040000) {
        android.content.SharedPreferences swipePrefs;
        swipePrefs = context.getSharedPreferences(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.PREF_NAME, android.content.Context.MODE_PRIVATE);
        swipePrefs.edit().putString(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.KEY_PREFIX_SWIPEACTIONS + de.danoeh.antennapod.fragment.QueueFragment.TAG, (de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_QUEUE + ",") + de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_QUEUE).apply();
    }
    if (oldVersion < 2050000) {
        de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.edit().putBoolean(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_PAUSE_PLAYBACK_FOR_FOCUS_LOSS, true).apply();
    }
    if (oldVersion < 2080000) {
        // Migrate drawer feed counter setting to reflect removal of
        // "unplayed and in inbox" (0), by changing it to "unplayed" (2)
        java.lang.String feedCounterSetting;
        feedCounterSetting = de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_DRAWER_FEED_COUNTER, "1");
        if (feedCounterSetting.equals("0")) {
            de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.edit().putString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_DRAWER_FEED_COUNTER, "2").apply();
        }
        android.content.SharedPreferences sleepTimerPreferences;
        sleepTimerPreferences = context.getSharedPreferences(de.danoeh.antennapod.core.preferences.SleepTimerPreferences.PREF_NAME, android.content.Context.MODE_PRIVATE);
        java.util.concurrent.TimeUnit[] timeUnits;
        timeUnits = new java.util.concurrent.TimeUnit[]{ java.util.concurrent.TimeUnit.SECONDS, java.util.concurrent.TimeUnit.MINUTES, java.util.concurrent.TimeUnit.HOURS };
        long value;
        value = java.lang.Long.parseLong(de.danoeh.antennapod.core.preferences.SleepTimerPreferences.lastTimerValue());
        java.util.concurrent.TimeUnit unit;
        unit = timeUnits[sleepTimerPreferences.getInt("LastTimeUnit", 1)];
        de.danoeh.antennapod.core.preferences.SleepTimerPreferences.setLastTimer(java.lang.String.valueOf(unit.toMinutes(value)));
        if (de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_EPISODE_CACHE_SIZE, "20").equals(context.getString(de.danoeh.antennapod.R.string.pref_episode_cache_unlimited))) {
            de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.edit().putString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_EPISODE_CACHE_SIZE, "" + de.danoeh.antennapod.storage.preferences.UserPreferences.EPISODE_CACHE_SIZE_UNLIMITED).apply();
        }
    }
    if (oldVersion < 3000007) {
        if (de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getString("prefBackButtonBehavior", "").equals("drawer")) {
            de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.edit().putBoolean(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_BACK_OPENS_DRAWER, true).apply();
        }
    }
    if (oldVersion < 3010000) {
        if (de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_THEME, "system").equals("2")) {
            de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.edit().putString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_THEME, "1").putBoolean(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_THEME_BLACK, true).apply();
        }
        de.danoeh.antennapod.storage.preferences.UserPreferences.setAllowMobileSync(true);
        if (de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.getString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_UPDATE_INTERVAL, ":").contains(":")) {
            // Unset or "time of day"
            de.danoeh.antennapod.preferences.PreferenceUpgrader.prefs.edit().putString(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_UPDATE_INTERVAL, "12").apply();
        }
    }
    if (oldVersion < 3020000) {
        androidx.core.app.NotificationManagerCompat.from(context).deleteNotificationChannel("auto_download");
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
