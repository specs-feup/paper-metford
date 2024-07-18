package de.danoeh.antennapod.fragment.preferences;
import android.net.wifi.WifiManager;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.util.Log;
import androidx.preference.CheckBoxPreference;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import java.util.ArrayList;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceScreen;
import androidx.preference.Preference;
import de.danoeh.antennapod.R;
import android.app.Activity;
import android.os.Build;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import androidx.preference.PreferenceFragmentCompat;
import java.util.List;
import java.util.Arrays;
import de.danoeh.antennapod.activity.PreferenceActivity;
import android.content.Context;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AutoDownloadPreferencesFragment extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "AutoDnldPrefFragment";

    private androidx.preference.CheckBoxPreference[] selectedNetworks;

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(de.danoeh.antennapod.R.xml.preferences_autodownload);
        setupAutoDownloadScreen();
        buildAutodownloadSelectedNetworksPreference();
        setSelectedNetworksEnabled(de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownloadWifiFilter());
        buildEpisodeCleanupPreference();
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.pref_automatic_download_title);
    }


    @java.lang.Override
    public void onResume() {
        super.onResume();
        checkAutodownloadItemVisibility(de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownload());
    }


    private void setupAutoDownloadScreen() {
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_ENABLE_AUTODL).setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            if (newValue instanceof java.lang.Boolean) {
                checkAutodownloadItemVisibility(((java.lang.Boolean) (newValue)));
            }
            return true;
        });
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_ENABLE_AUTODL_WIFI_FILTER).setVisible(false);
        }
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_ENABLE_AUTODL_WIFI_FILTER).setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            if (newValue instanceof java.lang.Boolean) {
                setSelectedNetworksEnabled(((java.lang.Boolean) (newValue)));
                return true;
            } else {
                return false;
            }
        });
    }


    private void checkAutodownloadItemVisibility(boolean autoDownload) {
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_EPISODE_CACHE_SIZE).setEnabled(autoDownload);
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_ENABLE_AUTODL_ON_BATTERY).setEnabled(autoDownload);
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_ENABLE_AUTODL_WIFI_FILTER).setEnabled(autoDownload);
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_EPISODE_CLEANUP).setEnabled(autoDownload);
        setSelectedNetworksEnabled(autoDownload && de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownloadWifiFilter());
    }


    private static java.lang.String blankIfNull(java.lang.String val) {
        return val == null ? "" : val;
    }


    // getConfiguredNetworks needs location permission starting with API 29
    @android.annotation.SuppressLint("MissingPermission")
    private void buildAutodownloadSelectedNetworksPreference() {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            return;
        }
        final android.app.Activity activity;
        activity = getActivity();
        if (selectedNetworks != null) {
            clearAutodownloadSelectedNetworsPreference();
        }
        // get configured networks
        android.net.wifi.WifiManager wifiservice;
        wifiservice = ((android.net.wifi.WifiManager) (activity.getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE)));
        java.util.List<android.net.wifi.WifiConfiguration> networks;
        networks = wifiservice.getConfiguredNetworks();
        if (networks == null) {
            android.util.Log.e(de.danoeh.antennapod.fragment.preferences.AutoDownloadPreferencesFragment.TAG, "Couldn't get list of configure Wi-Fi networks");
            return;
        }
        java.util.Collections.sort(networks, (android.net.wifi.WifiConfiguration x,android.net.wifi.WifiConfiguration y) -> de.danoeh.antennapod.fragment.preferences.AutoDownloadPreferencesFragment.blankIfNull(x.SSID).compareToIgnoreCase(de.danoeh.antennapod.fragment.preferences.AutoDownloadPreferencesFragment.blankIfNull(y.SSID)));
        selectedNetworks = new androidx.preference.CheckBoxPreference[networks.size()];
        java.util.List<java.lang.String> prefValues;
        prefValues = java.util.Arrays.asList(de.danoeh.antennapod.storage.preferences.UserPreferences.getAutodownloadSelectedNetworks());
        androidx.preference.PreferenceScreen prefScreen;
        prefScreen = getPreferenceScreen();
        androidx.preference.Preference.OnPreferenceClickListener clickListener;
        clickListener = (androidx.preference.Preference preference) -> {
            if (preference instanceof androidx.preference.CheckBoxPreference) {
                java.lang.String key;
                key = preference.getKey();
                java.util.List<java.lang.String> prefValuesList;
                prefValuesList = new java.util.ArrayList<>(java.util.Arrays.asList(de.danoeh.antennapod.storage.preferences.UserPreferences.getAutodownloadSelectedNetworks()));
                boolean newValue;
                newValue = ((androidx.preference.CheckBoxPreference) (preference)).isChecked();
                android.util.Log.d(de.danoeh.antennapod.fragment.preferences.AutoDownloadPreferencesFragment.TAG, (("Selected network " + key) + ". New state: ") + newValue);
                int index;
                index = prefValuesList.indexOf(key);
                if ((index >= 0) && (!newValue)) {
                    // remove network
                    prefValuesList.remove(index);
                } else if ((index < 0) && newValue) {
                    prefValuesList.add(key);
                }
                de.danoeh.antennapod.storage.preferences.UserPreferences.setAutodownloadSelectedNetworks(prefValuesList.toArray(new java.lang.String[0]));
                return true;
            } else {
                return false;
            }
        };
        // create preference for each known network. attach listener and set
        // value
        for (int i = 0; i < networks.size(); i++) {
            android.net.wifi.WifiConfiguration config;
            config = networks.get(i);
            androidx.preference.CheckBoxPreference pref;
            pref = new androidx.preference.CheckBoxPreference(activity);
            java.lang.String key;
            key = java.lang.Integer.toString(config.networkId);
            pref.setTitle(config.SSID);
            pref.setKey(key);
            pref.setOnPreferenceClickListener(clickListener);
            pref.setPersistent(false);
            pref.setChecked(prefValues.contains(key));
            selectedNetworks[i] = pref;
            prefScreen.addPreference(pref);
        }
    }


    private void clearAutodownloadSelectedNetworsPreference() {
        if (selectedNetworks != null) {
            androidx.preference.PreferenceScreen prefScreen;
            prefScreen = getPreferenceScreen();
            for (androidx.preference.CheckBoxPreference network : selectedNetworks) {
                if (network != null) {
                    prefScreen.removePreference(network);
                }
            }
        }
    }


    private void buildEpisodeCleanupPreference() {
        final android.content.res.Resources res;
        res = getActivity().getResources();
        androidx.preference.ListPreference pref;
        pref = findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_EPISODE_CLEANUP);
        java.lang.String[] values;
        values = res.getStringArray(de.danoeh.antennapod.R.array.episode_cleanup_values);
        java.lang.String[] entries;
        entries = new java.lang.String[values.length];
        for (int x = 0; x < values.length; x++) {
            int v;
            v = java.lang.Integer.parseInt(values[x]);
            if (v == de.danoeh.antennapod.storage.preferences.UserPreferences.EPISODE_CLEANUP_EXCEPT_FAVORITE) {
                entries[x] = res.getString(de.danoeh.antennapod.R.string.episode_cleanup_except_favorite_removal);
            } else if (v == de.danoeh.antennapod.storage.preferences.UserPreferences.EPISODE_CLEANUP_QUEUE) {
                entries[x] = res.getString(de.danoeh.antennapod.R.string.episode_cleanup_queue_removal);
            } else if (v == de.danoeh.antennapod.storage.preferences.UserPreferences.EPISODE_CLEANUP_NULL) {
                entries[x] = res.getString(de.danoeh.antennapod.R.string.episode_cleanup_never);
            } else if (v == 0) {
                entries[x] = res.getString(de.danoeh.antennapod.R.string.episode_cleanup_after_listening);
            } else if ((v > 0) && (v < 24)) {
                entries[x] = res.getQuantityString(de.danoeh.antennapod.R.plurals.episode_cleanup_hours_after_listening, v, v);
            } else {
                int numDays// assume underlying value will be NOT fraction of days, e.g., 36 (hours)
                ;// assume underlying value will be NOT fraction of days, e.g., 36 (hours)

                switch(MUID_STATIC) {
                    // AutoDownloadPreferencesFragment_0_BinaryMutator
                    case 98: {
                        numDays = v * 24;
                        break;
                    }
                    default: {
                    numDays = v / 24;
                    break;
                }
            }
            entries[x] = res.getQuantityString(de.danoeh.antennapod.R.plurals.episode_cleanup_days_after_listening, numDays, numDays);
        }
    }
    pref.setEntries(entries);
}


private void setSelectedNetworksEnabled(boolean b) {
    if (selectedNetworks != null) {
        for (androidx.preference.Preference p : selectedNetworks) {
            p.setEnabled(b);
        }
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
