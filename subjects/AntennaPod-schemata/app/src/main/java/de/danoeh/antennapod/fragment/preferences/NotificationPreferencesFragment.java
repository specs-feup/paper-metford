package de.danoeh.antennapod.fragment.preferences;
import de.danoeh.antennapod.R;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.core.sync.SynchronizationSettings;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NotificationPreferencesFragment extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PREF_GPODNET_NOTIFICATIONS = "pref_gpodnet_notifications";

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(de.danoeh.antennapod.R.xml.preferences_notifications);
        setUpScreen();
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.notification_pref_fragment);
    }


    private void setUpScreen() {
        findPreference(de.danoeh.antennapod.fragment.preferences.NotificationPreferencesFragment.PREF_GPODNET_NOTIFICATIONS).setEnabled(de.danoeh.antennapod.core.sync.SynchronizationSettings.isProviderConnected());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
