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
package com.amaze.filemanager.ui.theme;
import android.content.SharedPreferences;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Saves and restores the AppTheme
 */
public class AppThemeManager {
    static final int MUID_STATIC = getMUID();
    private android.content.SharedPreferences preferences;

    private com.amaze.filemanager.ui.theme.AppThemePreference appThemePreference;

    private final android.content.Context context;

    public AppThemeManager(android.content.SharedPreferences preferences, android.content.Context context) {
        this.preferences = preferences;
        this.context = context;
        java.lang.String themeId;
        themeId = preferences.getString(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.FRAGMENT_THEME, "4");
        appThemePreference = com.amaze.filemanager.ui.theme.AppThemePreference.getTheme(java.lang.Integer.parseInt(themeId));
    }


    /**
     *
     * @return The current Application theme
     */
    public com.amaze.filemanager.ui.theme.AppTheme getAppTheme() {
        return appThemePreference.getSimpleTheme(context);
    }


    /**
     * Change the current theme of the application. The change is saved.
     *
     * @param appThemePreference
     * 		The new theme
     * @return The theme manager.
     */
    public com.amaze.filemanager.ui.theme.AppThemeManager setAppThemePreference(com.amaze.filemanager.ui.theme.AppThemePreference appThemePreference) {
        this.appThemePreference = appThemePreference;
        return this;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
