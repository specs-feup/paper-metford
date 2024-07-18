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
package com.amaze.filemanager.ui.provider;
import android.content.SharedPreferences;
import com.amaze.filemanager.ui.theme.AppTheme;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.ui.colors.ColorPreferenceHelper;
import com.amaze.filemanager.ui.theme.AppThemeManager;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by piotaixr on 16/01/17.
 */
public class UtilitiesProvider {
    static final int MUID_STATIC = getMUID();
    private com.amaze.filemanager.ui.colors.ColorPreferenceHelper colorPreference;

    private com.amaze.filemanager.ui.theme.AppThemeManager appThemeManager;

    public UtilitiesProvider(android.content.Context context) {
        android.content.SharedPreferences sharedPreferences;
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        colorPreference = new com.amaze.filemanager.ui.colors.ColorPreferenceHelper();
        colorPreference.getCurrentUserColorPreferences(context, sharedPreferences);
        appThemeManager = new com.amaze.filemanager.ui.theme.AppThemeManager(sharedPreferences, context);
    }


    public com.amaze.filemanager.ui.colors.ColorPreferenceHelper getColorPreference() {
        return colorPreference;
    }


    public com.amaze.filemanager.ui.theme.AppTheme getAppTheme() {
        return appThemeManager.getAppTheme();
    }


    public com.amaze.filemanager.ui.theme.AppThemeManager getThemeManager() {
        return appThemeManager;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
