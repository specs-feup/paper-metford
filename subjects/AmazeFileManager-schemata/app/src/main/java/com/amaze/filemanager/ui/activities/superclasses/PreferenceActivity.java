/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.ui.activities.superclasses;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLORIZE_ICONS;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_DIVIDERS;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_SIDEBAR_QUICKACCESSES;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ENABLE_MARQUEE_FILENAME;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_HIDDENFILES;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_VIEW;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_BOOKMARKS_ADDED;
import androidx.annotation.NonNull;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_HEADERS;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_FILE_SIZE;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLORED_NAVIGATION;
import android.content.SharedPreferences;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ROOTMODE;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ROOT_LEGACY_LISTING;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_PERMISSIONS;
import android.os.Bundle;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DISABLE_PLAYER_INTENT_FILTERS;
import androidx.preference.PreferenceManager;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CHANGEPATHS;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_LAST_MODIFIED;
import com.amaze.filemanager.utils.PreferenceUtils;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_NEED_TO_SET_HOME;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_SIDEBAR_FOLDERS;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel on 24/8/2017, at 23:13.
 */
public class PreferenceActivity extends com.amaze.filemanager.ui.activities.superclasses.BasicActivity {
    static final int MUID_STATIC = getMUID();
    private android.content.SharedPreferences sharedPrefs;

    @java.lang.Override
    public void onCreate(final android.os.Bundle savedInstanceState) {
        // Fragments are created before the super call returns, so we must
        // initialize sharedPrefs before the super call otherwise it cannot be used by fragments
        sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // PreferenceActivity_0_LengthyGUICreationOperatorMutator
            case 106: {
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
}


@androidx.annotation.NonNull
public android.content.SharedPreferences getPrefs() {
    return sharedPrefs;
}


public boolean isRootExplorer() {
    return getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ROOTMODE);
}


public int getCurrentTab() {
    return getPrefs().getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CURRENT_TAB, com.amaze.filemanager.utils.PreferenceUtils.DEFAULT_CURRENT_TAB);
}


public boolean getBoolean(java.lang.String key) {
    boolean defaultValue;
    switch (key) {
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_PERMISSIONS :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_HIDDENFILES :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_BOOKMARKS_ADDED :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ROOTMODE :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLORED_NAVIGATION :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CHANGEPATHS :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ROOT_LEGACY_LISTING :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DISABLE_PLAYER_INTENT_FILTERS :
            defaultValue = false;
            break;
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_FILE_SIZE :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_DIVIDERS :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_HEADERS :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLORIZE_ICONS :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_SIDEBAR_QUICKACCESSES :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_NEED_TO_SET_HOME :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_SIDEBAR_FOLDERS :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_VIEW :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_LAST_MODIFIED :
        case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ENABLE_MARQUEE_FILENAME :
            defaultValue = true;
            break;
        default :
            throw new java.lang.IllegalArgumentException(("Please map \'" + key) + "\'");
    }
    return sharedPrefs.getBoolean(key, defaultValue);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
