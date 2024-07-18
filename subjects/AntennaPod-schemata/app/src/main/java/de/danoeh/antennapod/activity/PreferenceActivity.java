package de.danoeh.antennapod.activity;
import de.danoeh.antennapod.event.MessageEvent;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.core.preferences.ThemeSwitcher;
import de.danoeh.antennapod.fragment.preferences.UserInterfacePreferencesFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import android.view.inputmethod.InputMethodManager;
import de.danoeh.antennapod.fragment.preferences.DownloadsPreferencesFragment;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.databinding.SettingsActivityBinding;
import android.os.Build;
import android.provider.Settings;
import androidx.preference.PreferenceFragmentCompat;
import de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment;
import androidx.appcompat.app.ActionBar;
import de.danoeh.antennapod.fragment.preferences.AutoDownloadPreferencesFragment;
import android.util.Log;
import de.danoeh.antennapod.fragment.preferences.NotificationPreferencesFragment;
import android.os.Bundle;
import de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment;
import de.danoeh.antennapod.fragment.preferences.SwipePreferencesFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.MenuItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import de.danoeh.antennapod.fragment.preferences.PlaybackPreferencesFragment;
import com.bytehamster.lib.preferencesearch.SearchPreferenceResultListener;
import de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment;
import com.google.android.material.snackbar.Snackbar;
import com.bytehamster.lib.preferencesearch.SearchPreferenceResult;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * PreferenceActivity for API 11+. In order to change the behavior of the preference UI, see
 * PreferenceController.
 */
public class PreferenceActivity extends androidx.appcompat.app.AppCompatActivity implements com.bytehamster.lib.preferencesearch.SearchPreferenceResultListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String FRAGMENT_TAG = "tag_preferences";

    public static final java.lang.String OPEN_AUTO_DOWNLOAD_SETTINGS = "OpenAutoDownloadSettings";

    private de.danoeh.antennapod.databinding.SettingsActivityBinding binding;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        setTheme(de.danoeh.antennapod.core.preferences.ThemeSwitcher.getTheme(this));
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // PreferenceActivity_0_LengthyGUICreationOperatorMutator
            case 144: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    androidx.appcompat.app.ActionBar ab;
    ab = getSupportActionBar();
    if (ab != null) {
        ab.setDisplayHomeAsUpEnabled(true);
    }
    binding = de.danoeh.antennapod.databinding.SettingsActivityBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    if (getSupportFragmentManager().findFragmentByTag(de.danoeh.antennapod.activity.PreferenceActivity.FRAGMENT_TAG) == null) {
        getSupportFragmentManager().beginTransaction().replace(binding.settingsContainer.getId(), new de.danoeh.antennapod.fragment.preferences.MainPreferencesFragment(), de.danoeh.antennapod.activity.PreferenceActivity.FRAGMENT_TAG).commit();
    }
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // PreferenceActivity_1_RandomActionIntentDefinitionOperatorMutator
        case 1144: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = getIntent();
        break;
    }
}
if (intent.getBooleanExtra(de.danoeh.antennapod.activity.PreferenceActivity.OPEN_AUTO_DOWNLOAD_SETTINGS, false)) {
    openScreen(de.danoeh.antennapod.R.xml.preferences_autodownload);
}
}


private androidx.preference.PreferenceFragmentCompat getPreferenceScreen(int screen) {
androidx.preference.PreferenceFragmentCompat prefFragment;
prefFragment = null;
if (screen == de.danoeh.antennapod.R.xml.preferences_user_interface) {
    prefFragment = new de.danoeh.antennapod.fragment.preferences.UserInterfacePreferencesFragment();
} else if (screen == de.danoeh.antennapod.R.xml.preferences_downloads) {
    prefFragment = new de.danoeh.antennapod.fragment.preferences.DownloadsPreferencesFragment();
} else if (screen == de.danoeh.antennapod.R.xml.preferences_import_export) {
    prefFragment = new de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment();
} else if (screen == de.danoeh.antennapod.R.xml.preferences_autodownload) {
    prefFragment = new de.danoeh.antennapod.fragment.preferences.AutoDownloadPreferencesFragment();
} else if (screen == de.danoeh.antennapod.R.xml.preferences_synchronization) {
    prefFragment = new de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment();
} else if (screen == de.danoeh.antennapod.R.xml.preferences_playback) {
    prefFragment = new de.danoeh.antennapod.fragment.preferences.PlaybackPreferencesFragment();
} else if (screen == de.danoeh.antennapod.R.xml.preferences_notifications) {
    prefFragment = new de.danoeh.antennapod.fragment.preferences.NotificationPreferencesFragment();
} else if (screen == de.danoeh.antennapod.R.xml.preferences_swipe) {
    prefFragment = new de.danoeh.antennapod.fragment.preferences.SwipePreferencesFragment();
}
return prefFragment;
}


public static int getTitleOfPage(int preferences) {
if (preferences == de.danoeh.antennapod.R.xml.preferences_downloads) {
    return de.danoeh.antennapod.R.string.downloads_pref;
} else if (preferences == de.danoeh.antennapod.R.xml.preferences_autodownload) {
    return de.danoeh.antennapod.R.string.pref_automatic_download_title;
} else if (preferences == de.danoeh.antennapod.R.xml.preferences_playback) {
    return de.danoeh.antennapod.R.string.playback_pref;
} else if (preferences == de.danoeh.antennapod.R.xml.preferences_import_export) {
    return de.danoeh.antennapod.R.string.import_export_pref;
} else if (preferences == de.danoeh.antennapod.R.xml.preferences_user_interface) {
    return de.danoeh.antennapod.R.string.user_interface_label;
} else if (preferences == de.danoeh.antennapod.R.xml.preferences_synchronization) {
    return de.danoeh.antennapod.R.string.synchronization_pref;
} else if (preferences == de.danoeh.antennapod.R.xml.preferences_notifications) {
    return de.danoeh.antennapod.R.string.notification_pref_fragment;
} else if (preferences == de.danoeh.antennapod.R.xml.feed_settings) {
    return de.danoeh.antennapod.R.string.feed_settings_label;
} else if (preferences == de.danoeh.antennapod.R.xml.preferences_swipe) {
    return de.danoeh.antennapod.R.string.swipeactions_label;
}
return de.danoeh.antennapod.R.string.settings_label;
}


public androidx.preference.PreferenceFragmentCompat openScreen(int screen) {
androidx.preference.PreferenceFragmentCompat fragment;
fragment = getPreferenceScreen(screen);
if ((screen == de.danoeh.antennapod.R.xml.preferences_notifications) && (android.os.Build.VERSION.SDK_INT >= 26)) {
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // PreferenceActivity_2_NullIntentOperatorMutator
        case 2144: {
            intent = null;
            break;
        }
        // PreferenceActivity_3_RandomActionIntentDefinitionOperatorMutator
        case 3144: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent();
        break;
    }
}
switch(MUID_STATIC) {
    // PreferenceActivity_4_RandomActionIntentDefinitionOperatorMutator
    case 4144: {
        /**
        * Inserted by Kadabra
        */
        /**
        * Inserted by Kadabra
        */
        new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent.setAction(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS);
    break;
}
}
switch(MUID_STATIC) {
// PreferenceActivity_5_NullValueIntentPutExtraOperatorMutator
case 5144: {
    intent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, new Parcelable[0]);
    break;
}
// PreferenceActivity_6_IntentPayloadReplacementOperatorMutator
case 6144: {
    intent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, "");
    break;
}
default: {
switch(MUID_STATIC) {
    // PreferenceActivity_7_RandomActionIntentDefinitionOperatorMutator
    case 7144: {
        /**
        * Inserted by Kadabra
        */
        /**
        * Inserted by Kadabra
        */
        new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, getPackageName());
    break;
}
}
break;
}
}
startActivity(intent);
} else {
getSupportFragmentManager().beginTransaction().replace(binding.settingsContainer.getId(), fragment).addToBackStack(getString(de.danoeh.antennapod.activity.PreferenceActivity.getTitleOfPage(screen))).commit();
}
return fragment;
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
if (item.getItemId() == android.R.id.home) {
if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
finish();
} else {
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
android.view.View view;
view = getCurrentFocus();
// If no view currently has focus, create a new one, just so we can grab a window token from it
if (view == null) {
view = new android.view.View(this);
}
imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
getSupportFragmentManager().popBackStack();
}
return true;
}
return false;
}


@java.lang.Override
public void onSearchResultClicked(com.bytehamster.lib.preferencesearch.SearchPreferenceResult result) {
int screen;
screen = result.getResourceFile();
if (screen == de.danoeh.antennapod.R.xml.feed_settings) {
com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(this);
builder.setTitle(de.danoeh.antennapod.R.string.feed_settings_label);
builder.setMessage(de.danoeh.antennapod.R.string.pref_feed_settings_dialog_msg);
builder.setPositiveButton(android.R.string.ok, null);
builder.show();
} else if (screen == de.danoeh.antennapod.R.xml.preferences_notifications) {
openScreen(screen);
} else {
androidx.preference.PreferenceFragmentCompat fragment;
fragment = openScreen(result.getResourceFile());
result.highlight(fragment);
}
}


@java.lang.Override
protected void onStart() {
super.onStart();
org.greenrobot.eventbus.EventBus.getDefault().register(this);
}


@java.lang.Override
protected void onStop() {
super.onStop();
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.MessageEvent event) {
android.util.Log.d(de.danoeh.antennapod.activity.PreferenceActivity.FRAGMENT_TAG, ("onEvent(" + event) + ")");
com.google.android.material.snackbar.Snackbar s;
s = com.google.android.material.snackbar.Snackbar.make(binding.getRoot(), event.message, com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
if (event.action != null) {
switch(MUID_STATIC) {
// PreferenceActivity_8_BuggyGUIListenerOperatorMutator
case 8144: {
s.setAction(event.actionText, null);
break;
}
default: {
s.setAction(event.actionText, (android.view.View v) -> event.action.accept(this));
break;
}
}
}
s.show();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
