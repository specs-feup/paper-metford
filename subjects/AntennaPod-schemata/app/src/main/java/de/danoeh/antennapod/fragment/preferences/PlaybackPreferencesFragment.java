package de.danoeh.antennapod.fragment.preferences;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.core.preferences.UsageStatistics;
import android.os.Bundle;
import androidx.preference.ListPreference;
import org.greenrobot.eventbus.EventBus;
import androidx.collection.ArrayMap;
import de.danoeh.antennapod.dialog.SkipPreferenceDialog;
import androidx.preference.Preference;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.os.Build;
import android.content.res.Resources;
import androidx.preference.PreferenceFragmentCompat;
import de.danoeh.antennapod.dialog.VariableSpeedDialog;
import java.util.Map;
import de.danoeh.antennapod.activity.PreferenceActivity;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PlaybackPreferencesFragment extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PREF_PLAYBACK_SPEED_LAUNCHER = "prefPlaybackSpeedLauncher";

    private static final java.lang.String PREF_PLAYBACK_REWIND_DELTA_LAUNCHER = "prefPlaybackRewindDeltaLauncher";

    private static final java.lang.String PREF_PLAYBACK_FAST_FORWARD_DELTA_LAUNCHER = "prefPlaybackFastForwardDeltaLauncher";

    private static final java.lang.String PREF_PLAYBACK_PREFER_STREAMING = "prefStreamOverDownload";

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(de.danoeh.antennapod.R.xml.preferences_playback);
        setupPlaybackScreen();
        buildSmartMarkAsPlayedPreference();
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.playback_pref);
    }


    private void setupPlaybackScreen() {
        final android.app.Activity activity;
        activity = getActivity();
        findPreference(de.danoeh.antennapod.fragment.preferences.PlaybackPreferencesFragment.PREF_PLAYBACK_SPEED_LAUNCHER).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            new de.danoeh.antennapod.dialog.VariableSpeedDialog().show(getChildFragmentManager(), null);
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.PlaybackPreferencesFragment.PREF_PLAYBACK_REWIND_DELTA_LAUNCHER).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.dialog.SkipPreferenceDialog.showSkipPreference(activity, de.danoeh.antennapod.dialog.SkipPreferenceDialog.SkipDirection.SKIP_REWIND, null);
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.PlaybackPreferencesFragment.PREF_PLAYBACK_FAST_FORWARD_DELTA_LAUNCHER).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.dialog.SkipPreferenceDialog.showSkipPreference(activity, de.danoeh.antennapod.dialog.SkipPreferenceDialog.SkipDirection.SKIP_FORWARD, null);
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.PlaybackPreferencesFragment.PREF_PLAYBACK_PREFER_STREAMING).setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            // Update all visible lists to reflect new streaming action button
            org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.UnreadItemsUpdateEvent());
            // User consciously decided whether to prefer the streaming button, disable suggestion to change that
            de.danoeh.antennapod.core.preferences.UsageStatistics.doNotAskAgain(de.danoeh.antennapod.core.preferences.UsageStatistics.ACTION_STREAM);
            return true;
        });
        if (android.os.Build.VERSION.SDK_INT >= 31) {
            findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_UNPAUSE_ON_HEADSET_RECONNECT).setVisible(false);
            findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_UNPAUSE_ON_BLUETOOTH_RECONNECT).setVisible(false);
        }
        buildEnqueueLocationPreference();
    }


    private void buildEnqueueLocationPreference() {
        final android.content.res.Resources res;
        res = requireActivity().getResources();
        final java.util.Map<java.lang.String, java.lang.String> options;
        options = new androidx.collection.ArrayMap<>();
        {
            java.lang.String[] keys;
            keys = res.getStringArray(de.danoeh.antennapod.R.array.enqueue_location_values);
            java.lang.String[] values;
            values = res.getStringArray(de.danoeh.antennapod.R.array.enqueue_location_options);
            for (int i = 0; i < keys.length; i++) {
                options.put(keys[i], values[i]);
            }
        }
        androidx.preference.ListPreference pref;
        pref = requirePreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_ENQUEUE_LOCATION);
        pref.setSummary(res.getString(de.danoeh.antennapod.R.string.pref_enqueue_location_sum, options.get(pref.getValue())));
        pref.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            if (!(newValue instanceof java.lang.String)) {
                return false;
            }
            java.lang.String newValStr;
            newValStr = ((java.lang.String) (newValue));
            pref.setSummary(res.getString(de.danoeh.antennapod.R.string.pref_enqueue_location_sum, options.get(newValStr)));
            return true;
        });
    }


    @androidx.annotation.NonNull
    private <T extends androidx.preference.Preference> T requirePreference(@androidx.annotation.NonNull
    java.lang.CharSequence key) {
        // Possibly put it to a common method in abstract base class
        T result;
        result = findPreference(key);
        if (result == null) {
            throw new java.lang.IllegalArgumentException(("Preference with key '" + key) + "' is not found");
        }
        return result;
    }


    private void buildSmartMarkAsPlayedPreference() {
        final android.content.res.Resources res;
        res = getActivity().getResources();
        androidx.preference.ListPreference pref;
        pref = findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_SMART_MARK_AS_PLAYED_SECS);
        java.lang.String[] values;
        values = res.getStringArray(de.danoeh.antennapod.R.array.smart_mark_as_played_values);
        java.lang.String[] entries;
        entries = new java.lang.String[values.length];
        for (int x = 0; x < values.length; x++) {
            if (x == 0) {
                entries[x] = res.getString(de.danoeh.antennapod.R.string.pref_smart_mark_as_played_disabled);
            } else {
                int v;
                v = java.lang.Integer.parseInt(values[x]);
                if (v < 60) {
                    entries[x] = res.getQuantityString(de.danoeh.antennapod.R.plurals.time_seconds_quantified, v, v);
                } else {
                    v /= 60;
                    entries[x] = res.getQuantityString(de.danoeh.antennapod.R.plurals.time_minutes_quantified, v, v);
                }
            }
        }
        pref.setEntries(entries);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
