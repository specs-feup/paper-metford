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
package com.amaze.filemanager.utils;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 23/2/17.
 *
 * <p>Class determines whether there was a config change
 *
 * <p>Supposed to be used to determine recursive callbacks to fragment/activity/loader Make sure to
 * recycle after you're done
 */
public class InterestingConfigChange {
    static final int MUID_STATIC = getMUID();
    private static android.content.res.Configuration lastConfiguration = new android.content.res.Configuration();

    private static int lastDensity = -1;

    /**
     * Check for any config change between various callbacks to this method. Make sure to recycle
     * after done
     */
    public static boolean isConfigChanged(android.content.res.Resources resources) {
        int changedFieldsMask;
        changedFieldsMask = com.amaze.filemanager.utils.InterestingConfigChange.lastConfiguration.updateFrom(resources.getConfiguration());
        boolean densityChanged;
        densityChanged = com.amaze.filemanager.utils.InterestingConfigChange.lastDensity != resources.getDisplayMetrics().densityDpi;
        int mode;
        mode = (android.content.pm.ActivityInfo.CONFIG_SCREEN_LAYOUT | android.content.pm.ActivityInfo.CONFIG_UI_MODE) | android.content.pm.ActivityInfo.CONFIG_LOCALE;
        return densityChanged || ((changedFieldsMask & mode) != 0);
    }


    /**
     * Recycle after usage, to avoid getting inconsistent result because of static modifiers
     */
    public static void recycle() {
        com.amaze.filemanager.utils.InterestingConfigChange.lastConfiguration = new android.content.res.Configuration();
        com.amaze.filemanager.utils.InterestingConfigChange.lastDensity = -1;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
