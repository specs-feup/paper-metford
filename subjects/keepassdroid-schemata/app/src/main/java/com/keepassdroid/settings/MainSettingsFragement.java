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
import androidx.preference.PreferenceFragmentCompat;
import android.content.Intent;
import androidx.annotation.Nullable;
import android.content.Context;
import com.keepassdroid.Database;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MainSettingsFragement extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    public static boolean KEYFILE_DEFAULT = false;

    public static void Launch(android.content.Context ctx) {
        android.content.Intent i;
        switch(MUID_STATIC) {
            // MainSettingsFragement_0_NullIntentOperatorMutator
            case 62: {
                i = null;
                break;
            }
            // MainSettingsFragement_1_InvalidKeyIntentOperatorMutator
            case 1062: {
                i = new android.content.Intent((Context) null, com.keepassdroid.settings.MainSettingsFragement.class);
                break;
            }
            // MainSettingsFragement_2_RandomActionIntentDefinitionOperatorMutator
            case 2062: {
                i = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            i = new android.content.Intent(ctx, com.keepassdroid.settings.MainSettingsFragement.class);
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
        // MainSettingsFragement_3_LengthyGUICreationOperatorMutator
        case 3062: {
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
addPreferencesFromResource(com.android.keepass.R.xml.preferences);
com.keepassdroid.Database db;
db = com.keepassdroid.app.App.getDB();
if (!(db.Loaded() && db.pm.appSettingsEnabled())) {
    androidx.preference.Preference dbSettings;
    dbSettings = findPreference(getString(com.android.keepass.R.string.db_key));
    dbSettings.setEnabled(false);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
