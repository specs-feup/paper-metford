package de.danoeh.antennapod.fragment.preferences;
import de.danoeh.antennapod.activity.BugReportActivity;
import de.danoeh.antennapod.fragment.preferences.about.AboutFragment;
import java.util.Locale;
import android.os.Bundle;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.PorterDuffColorFilter;
import com.bytehamster.lib.preferencesearch.SearchConfiguration;
import com.bytehamster.lib.preferencesearch.SearchPreference;
import androidx.preference.Preference;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.util.IntentUtils;
import org.apache.commons.io.IOUtils;
import android.graphics.PorterDuff;
import org.apache.commons.lang3.ArrayUtils;
import androidx.preference.PreferenceFragmentCompat;
import de.danoeh.antennapod.activity.PreferenceActivity;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MainPreferencesFragment extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PREF_SCREEN_USER_INTERFACE = "prefScreenInterface";

    private static final java.lang.String PREF_SCREEN_PLAYBACK = "prefScreenPlayback";

    private static final java.lang.String PREF_SCREEN_DOWNLOADS = "prefScreenDownloads";

    private static final java.lang.String PREF_SCREEN_IMPORT_EXPORT = "prefScreenImportExport";

    private static final java.lang.String PREF_SCREEN_SYNCHRONIZATION = "prefScreenSynchronization";

    private static final java.lang.String PREF_DOCUMENTATION = "prefDocumentation";

    private static final java.lang.String PREF_VIEW_FORUM = "prefViewForum";

    private static final java.lang.String PREF_SEND_BUG_REPORT = "prefSendBugReport";

    private static final java.lang.String PREF_CATEGORY_PROJECT = "project";

    private static final java.lang.String PREF_ABOUT = "prefAbout";

    private static final java.lang.String PREF_NOTIFICATION = "notifications";

    private static final java.lang.String PREF_CONTRIBUTE = "prefContribute";

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(de.danoeh.antennapod.R.xml.preferences);
        setupMainScreen();
        setupSearch();
        // If you are writing a spin-off, please update the details on screens like "About" and "Report bug"
        // and afterwards remove the following lines. Please keep in mind that AntennaPod is licensed under the GPL.
        // This means that your application needs to be open-source under the GPL, too.
        // It must also include a prominent copyright notice.
        int packageHash;
        packageHash = getContext().getPackageName().hashCode();
        if ((packageHash != 1790437538) && (packageHash != (-1190467065))) {
            findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_CATEGORY_PROJECT).setVisible(false);
            androidx.preference.Preference copyrightNotice;
            copyrightNotice = new androidx.preference.Preference(getContext());
            copyrightNotice.setIcon(de.danoeh.antennapod.R.drawable.ic_info_white);
            copyrightNotice.getIcon().mutate().setColorFilter(new android.graphics.PorterDuffColorFilter(0xffcc0000, android.graphics.PorterDuff.Mode.MULTIPLY));
            copyrightNotice.setSummary("This application is based on AntennaPod." + ((" The AntennaPod team does NOT provide support for this unofficial version." + " If you can read this message, the developers of this modification") + " violate the GNU General Public License (GPL)."));
            findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_CATEGORY_PROJECT).getParent().addPreference(copyrightNotice);
        } else if (packageHash == (-1190467065)) {
            androidx.preference.Preference debugNotice;
            debugNotice = new androidx.preference.Preference(getContext());
            debugNotice.setIcon(de.danoeh.antennapod.R.drawable.ic_info_white);
            debugNotice.getIcon().mutate().setColorFilter(new android.graphics.PorterDuffColorFilter(0xffcc0000, android.graphics.PorterDuff.Mode.MULTIPLY));
            debugNotice.setOrder(-1);
            debugNotice.setSummary("This is a development version of AntennaPod and not meant for daily use");
            findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_CATEGORY_PROJECT).getParent().addPreference(debugNotice);
        }
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.settings_label);
    }


    private void setupMainScreen() {
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_SCREEN_USER_INTERFACE).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).openScreen(de.danoeh.antennapod.R.xml.preferences_user_interface);
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_SCREEN_PLAYBACK).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).openScreen(de.danoeh.antennapod.R.xml.preferences_playback);
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_SCREEN_DOWNLOADS).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).openScreen(de.danoeh.antennapod.R.xml.preferences_downloads);
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_SCREEN_SYNCHRONIZATION).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).openScreen(de.danoeh.antennapod.R.xml.preferences_synchronization);
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_SCREEN_IMPORT_EXPORT).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).openScreen(de.danoeh.antennapod.R.xml.preferences_import_export);
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_NOTIFICATION).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).openScreen(de.danoeh.antennapod.R.xml.preferences_notifications);
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_ABOUT).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            getParentFragmentManager().beginTransaction().replace(de.danoeh.antennapod.R.id.settingsContainer, new de.danoeh.antennapod.fragment.preferences.about.AboutFragment()).addToBackStack(getString(de.danoeh.antennapod.R.string.about_pref)).commit();
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_DOCUMENTATION).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(getContext(), getLocalizedWebsiteLink() + "/documentation/");
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_VIEW_FORUM).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(getContext(), "https://forum.antennapod.org/");
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_CONTRIBUTE).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(getContext(), getLocalizedWebsiteLink() + "/contribute/");
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment.PREF_SEND_BUG_REPORT).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            startActivity(new android.content.Intent(getActivity(), de.danoeh.antennapod.activity.BugReportActivity.class));
            return true;
        });
    }


    private java.lang.String getLocalizedWebsiteLink() {
        try (java.io.InputStream is = getContext().getAssets().open("website-languages.txt")) {
            java.lang.String[] languages;
            languages = org.apache.commons.io.IOUtils.toString(is, java.nio.charset.StandardCharsets.UTF_8.name()).split("\n");
            java.lang.String deviceLanguage;
            deviceLanguage = java.util.Locale.getDefault().getLanguage();
            if (org.apache.commons.lang3.ArrayUtils.contains(languages, deviceLanguage) && (!"en".equals(deviceLanguage))) {
                return "https://antennapod.org/" + deviceLanguage;
            } else {
                return "https://antennapod.org";
            }
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    private void setupSearch() {
        com.bytehamster.lib.preferencesearch.SearchPreference searchPreference;
        searchPreference = findPreference("searchPreference");
        com.bytehamster.lib.preferencesearch.SearchConfiguration config;
        config = searchPreference.getSearchConfiguration();
        config.setActivity(((androidx.appcompat.app.AppCompatActivity) (getActivity())));
        config.setFragmentContainerViewId(de.danoeh.antennapod.R.id.settingsContainer);
        config.setBreadcrumbsEnabled(true);
        config.index(de.danoeh.antennapod.R.xml.preferences_user_interface).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.preferences_user_interface));
        config.index(de.danoeh.antennapod.R.xml.preferences_playback).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.preferences_playback));
        config.index(de.danoeh.antennapod.R.xml.preferences_downloads).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.preferences_downloads));
        config.index(de.danoeh.antennapod.R.xml.preferences_import_export).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.preferences_import_export));
        config.index(de.danoeh.antennapod.R.xml.preferences_autodownload).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.preferences_downloads)).addBreadcrumb(de.danoeh.antennapod.R.string.automation).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.preferences_autodownload));
        config.index(de.danoeh.antennapod.R.xml.preferences_synchronization).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.preferences_synchronization));
        config.index(de.danoeh.antennapod.R.xml.preferences_notifications).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.preferences_notifications));
        config.index(de.danoeh.antennapod.R.xml.feed_settings).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.feed_settings));
        config.index(de.danoeh.antennapod.R.xml.preferences_swipe).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.preferences_user_interface)).addBreadcrumb(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(de.danoeh.antennapod.R.xml.preferences_swipe));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
