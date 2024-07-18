package de.danoeh.antennapod.fragment.preferences;
import de.danoeh.antennapod.dialog.ProxyDialog;
import android.content.SharedPreferences;
import androidx.preference.TwoStatePreference;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.os.Bundle;
import de.danoeh.antennapod.dialog.ChooseDataFolderDialog;
import androidx.preference.PreferenceManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import androidx.preference.PreferenceFragmentCompat;
import java.io.File;
import de.danoeh.antennapod.activity.PreferenceActivity;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DownloadsPreferencesFragment extends androidx.preference.PreferenceFragmentCompat implements android.content.SharedPreferences.OnSharedPreferenceChangeListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PREF_SCREEN_AUTODL = "prefAutoDownloadSettings";

    private static final java.lang.String PREF_AUTO_DELETE_LOCAL = "prefAutoDeleteLocal";

    private static final java.lang.String PREF_PROXY = "prefProxy";

    private static final java.lang.String PREF_CHOOSE_DATA_DIR = "prefChooseDataDir";

    private boolean blockAutoDeleteLocal = true;

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(de.danoeh.antennapod.R.xml.preferences_downloads);
        setupNetworkScreen();
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.downloads_pref);
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);
    }


    @java.lang.Override
    public void onStop() {
        super.onStop();
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(getContext()).unregisterOnSharedPreferenceChangeListener(this);
    }


    @java.lang.Override
    public void onResume() {
        super.onResume();
        setDataFolderText();
    }


    private void setupNetworkScreen() {
        findPreference(de.danoeh.antennapod.fragment.preferences.DownloadsPreferencesFragment.PREF_SCREEN_AUTODL).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).openScreen(de.danoeh.antennapod.R.xml.preferences_autodownload);
            return true;
        });
        // validate and set correct value: number of downloads between 1 and 50 (inclusive)
        findPreference(de.danoeh.antennapod.fragment.preferences.DownloadsPreferencesFragment.PREF_PROXY).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.dialog.ProxyDialog dialog;
            dialog = new de.danoeh.antennapod.dialog.ProxyDialog(getActivity());
            dialog.show();
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.DownloadsPreferencesFragment.PREF_CHOOSE_DATA_DIR).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.dialog.ChooseDataFolderDialog.showDialog(getContext(), (java.lang.String path) -> {
                de.danoeh.antennapod.storage.preferences.UserPreferences.setDataFolder(path);
                setDataFolderText();
            });
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.DownloadsPreferencesFragment.PREF_AUTO_DELETE_LOCAL).setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            if (blockAutoDeleteLocal && (newValue == java.lang.Boolean.TRUE)) {
                showAutoDeleteEnableDialog();
                return false;
            } else {
                return true;
            }
        });
    }


    private void setDataFolderText() {
        java.io.File f;
        f = de.danoeh.antennapod.storage.preferences.UserPreferences.getDataFolder(null);
        if (f != null) {
            findPreference(de.danoeh.antennapod.fragment.preferences.DownloadsPreferencesFragment.PREF_CHOOSE_DATA_DIR).setSummary(f.getAbsolutePath());
        }
    }


    @java.lang.Override
    public void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, java.lang.String key) {
        if (de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_UPDATE_INTERVAL.equals(key)) {
            de.danoeh.antennapod.core.util.download.FeedUpdateManager.restartUpdateAlarm(getContext(), true);
        }
    }


    private void showAutoDeleteEnableDialog() {
        switch(MUID_STATIC) {
            // DownloadsPreferencesFragment_0_BuggyGUIListenerOperatorMutator
            case 104: {
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext()).setMessage(de.danoeh.antennapod.R.string.pref_auto_local_delete_dialog_body).setPositiveButton(de.danoeh.antennapod.R.string.yes, null).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
                break;
            }
            default: {
            new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext()).setMessage(de.danoeh.antennapod.R.string.pref_auto_local_delete_dialog_body).setPositiveButton(de.danoeh.antennapod.R.string.yes, (android.content.DialogInterface dialog,int which) -> {
                blockAutoDeleteLocal = false;
                ((androidx.preference.TwoStatePreference) (findPreference(de.danoeh.antennapod.fragment.preferences.DownloadsPreferencesFragment.PREF_AUTO_DELETE_LOCAL))).setChecked(true);
                blockAutoDeleteLocal = true;
            }).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
            break;
        }
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
