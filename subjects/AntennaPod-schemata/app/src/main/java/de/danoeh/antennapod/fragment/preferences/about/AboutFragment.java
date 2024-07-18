package de.danoeh.antennapod.fragment.preferences.about;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.util.IntentUtils;
import android.os.Build;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import de.danoeh.antennapod.BuildConfig;
import android.content.ClipData;
import com.google.android.material.snackbar.Snackbar;
import de.danoeh.antennapod.activity.PreferenceActivity;
import android.content.ClipboardManager;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AboutFragment extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(de.danoeh.antennapod.R.xml.preferences_about);
        findPreference("about_version").setSummary(java.lang.String.format("%s (%s)", de.danoeh.antennapod.BuildConfig.VERSION_NAME, de.danoeh.antennapod.BuildConfig.COMMIT_HASH));
        findPreference("about_version").setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            android.content.ClipboardManager clipboard;
            clipboard = ((android.content.ClipboardManager) (getContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
            android.content.ClipData clip;
            clip = android.content.ClipData.newPlainText(getString(de.danoeh.antennapod.R.string.bug_report_title), findPreference("about_version").getSummary());
            clipboard.setPrimaryClip(clip);
            if (android.os.Build.VERSION.SDK_INT <= 32) {
                com.google.android.material.snackbar.Snackbar.make(getView(), de.danoeh.antennapod.R.string.copied_to_clipboard, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
            }
            return true;
        });
        findPreference("about_contributors").setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            getParentFragmentManager().beginTransaction().replace(de.danoeh.antennapod.R.id.settingsContainer, new de.danoeh.antennapod.fragment.preferences.about.ContributorsPagerFragment()).addToBackStack(getString(de.danoeh.antennapod.R.string.contributors)).commit();
            return true;
        });
        findPreference("about_privacy_policy").setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(getContext(), "https://antennapod.org/privacy/");
            return true;
        });
        findPreference("about_licenses").setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            getParentFragmentManager().beginTransaction().replace(de.danoeh.antennapod.R.id.settingsContainer, new de.danoeh.antennapod.fragment.preferences.about.LicensesFragment()).addToBackStack(getString(de.danoeh.antennapod.R.string.translators)).commit();
            return true;
        });
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.about_pref);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
