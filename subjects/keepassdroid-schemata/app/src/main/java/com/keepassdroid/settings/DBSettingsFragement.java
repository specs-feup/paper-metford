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
import com.keepassdroid.app.App;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.app.backup.BackupManager;
import androidx.preference.EditTextPreference;
import android.content.Intent;
import androidx.preference.Preference;
import com.android.keepass.R;
import androidx.preference.PreferenceFragmentCompat;
import com.keepassdroid.database.PwEncryptionAlgorithm;
import androidx.annotation.Nullable;
import android.content.Context;
import com.keepassdroid.Database;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DBSettingsFragement extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    public static boolean KEYFILE_DEFAULT = false;

    private android.app.backup.BackupManager backupManager;

    public static void Launch(android.content.Context ctx) {
        android.content.Intent i;
        switch(MUID_STATIC) {
            // DBSettingsFragement_0_NullIntentOperatorMutator
            case 60: {
                i = null;
                break;
            }
            // DBSettingsFragement_1_InvalidKeyIntentOperatorMutator
            case 1060: {
                i = new android.content.Intent((Context) null, com.keepassdroid.settings.DBSettingsFragement.class);
                break;
            }
            // DBSettingsFragement_2_RandomActionIntentDefinitionOperatorMutator
            case 2060: {
                i = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            i = new android.content.Intent(ctx, com.keepassdroid.settings.DBSettingsFragement.class);
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
        // DBSettingsFragement_3_LengthyGUICreationOperatorMutator
        case 3060: {
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


private static final java.lang.String ROUNDS_PREFERENCE_TAG = "ROUNDS";

@java.lang.Override
public void onDisplayPreferenceDialog(androidx.preference.Preference preference) {
if (getFragmentManager().findFragmentByTag(com.keepassdroid.settings.DBSettingsFragement.ROUNDS_PREFERENCE_TAG) != null) {
    return;
}
java.lang.String key;
key = preference.getKey();
if ((preference instanceof androidx.preference.EditTextPreference) && key.equals(getString(com.android.keepass.R.string.rounds_key))) {
    final androidx.fragment.app.DialogFragment f;
    f = com.keepassdroid.settings.RoundsPreferenceFragment.newInstance(preference.getKey());
    f.setTargetFragment(this, 0);
    f.show(getFragmentManager(), com.keepassdroid.settings.DBSettingsFragement.ROUNDS_PREFERENCE_TAG);
} else {
    super.onDisplayPreferenceDialog(preference);
}
}


@java.lang.Override
public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
addPreferencesFromResource(com.android.keepass.R.xml.preferences_db);
com.keepassdroid.Database db;
db = com.keepassdroid.app.App.getDB();
if (db.Loaded() && db.pm.appSettingsEnabled()) {
    androidx.preference.Preference rounds;
    rounds = findPreference(getString(com.android.keepass.R.string.rounds_key));
    rounds.setOnPreferenceChangeListener(new androidx.preference.Preference.OnPreferenceChangeListener() {
        public boolean onPreferenceChange(androidx.preference.Preference preference, java.lang.Object newValue) {
            setRounds(com.keepassdroid.app.App.getDB(), preference);
            return true;
        }

    });
    setRounds(db, rounds);
    androidx.preference.Preference algorithm;
    algorithm = findPreference(getString(com.android.keepass.R.string.algorithm_key));
    setAlgorithm(db, algorithm);
}
backupManager = new android.app.backup.BackupManager(getContext());
}


@java.lang.Override
public void onStop() {
backupManager.dataChanged();
super.onStop();
}


private void setRounds(com.keepassdroid.Database db, androidx.preference.Preference rounds) {
rounds.setSummary(java.lang.Long.toString(db.pm.getNumRounds()));
}


private void setAlgorithm(com.keepassdroid.Database db, androidx.preference.Preference algorithm) {
int resId;
if (db.pm.getEncAlgorithm() == com.keepassdroid.database.PwEncryptionAlgorithm.Rjindal) {
    resId = com.android.keepass.R.string.rijndael;
} else {
    resId = com.android.keepass.R.string.twofish;
}
algorithm.setSummary(resId);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
