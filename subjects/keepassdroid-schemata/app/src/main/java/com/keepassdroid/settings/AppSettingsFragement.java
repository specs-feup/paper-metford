/* Copyright 2020 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.settings;
import androidx.preference.Preference;
import com.android.keepass.R;
import com.keepassdroid.app.App;
import android.os.Bundle;
import android.app.backup.BackupManager;
import androidx.preference.PreferenceFragmentCompat;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AppSettingsFragement extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    public static boolean KEYFILE_DEFAULT = false;

    private android.app.backup.BackupManager backupManager;

    public static void Launch(android.content.Context ctx) {
        android.content.Intent i;
        switch(MUID_STATIC) {
            // AppSettingsFragement_0_NullIntentOperatorMutator
            case 61: {
                i = null;
                break;
            }
            // AppSettingsFragement_1_InvalidKeyIntentOperatorMutator
            case 1061: {
                i = new android.content.Intent((Context) null, com.keepassdroid.settings.AppSettingsFragement.class);
                break;
            }
            // AppSettingsFragement_2_RandomActionIntentDefinitionOperatorMutator
            case 2061: {
                i = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            i = new android.content.Intent(ctx, com.keepassdroid.settings.AppSettingsFragement.class);
            break;
        }
    }
    ctx.startActivity(i);
}


@java.lang.Override
public void onCreate(@androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    switch(MUID_STATIC) {
        // AppSettingsFragement_3_LengthyGUICreationOperatorMutator
        case 3061: {
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
setRetainInstance(true);
}


@java.lang.Override
public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
addPreferencesFromResource(com.android.keepass.R.xml.preferences_app);
androidx.preference.Preference keyFile;
keyFile = findPreference(getString(com.android.keepass.R.string.keyfile_key));
keyFile.setOnPreferenceChangeListener(new androidx.preference.Preference.OnPreferenceChangeListener() {
    public boolean onPreferenceChange(androidx.preference.Preference preference, java.lang.Object newValue) {
        java.lang.Boolean value;
        value = ((java.lang.Boolean) (newValue));
        if (!value.booleanValue()) {
            com.keepassdroid.app.App.getFileHistory().deleteAllKeys();
        }
        return true;
    }

});
androidx.preference.Preference recentHistory;
recentHistory = findPreference(getString(com.android.keepass.R.string.recentfile_key));
recentHistory.setOnPreferenceChangeListener(new androidx.preference.Preference.OnPreferenceChangeListener() {
    public boolean onPreferenceChange(androidx.preference.Preference preference, java.lang.Object newValue) {
        java.lang.Boolean value;
        value = ((java.lang.Boolean) (newValue));
        if (value == null) {
            value = true;
        }
        if (!value) {
            com.keepassdroid.app.App.getFileHistory().deleteAll();
        }
        return true;
    }

});
backupManager = new android.app.backup.BackupManager(getContext());
}


@java.lang.Override
public void onStop() {
backupManager.dataChanged();
super.onStop();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
