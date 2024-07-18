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
package com.amaze.filemanager.ui.colors;
import com.amaze.filemanager.R;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ColorPreference {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.Integer[] availableColors = new java.lang.Integer[]{ com.amaze.filemanager.R.color.primary_red, com.amaze.filemanager.R.color.primary_pink, com.amaze.filemanager.R.color.primary_purple, com.amaze.filemanager.R.color.primary_deep_purple, com.amaze.filemanager.R.color.primary_indigo, com.amaze.filemanager.R.color.primary_blue, com.amaze.filemanager.R.color.primary_light_blue, com.amaze.filemanager.R.color.primary_cyan, com.amaze.filemanager.R.color.primary_teal, com.amaze.filemanager.R.color.primary_green, com.amaze.filemanager.R.color.primary_light_green, com.amaze.filemanager.R.color.primary_amber, com.amaze.filemanager.R.color.primary_orange, com.amaze.filemanager.R.color.primary_deep_orange, com.amaze.filemanager.R.color.primary_brown, com.amaze.filemanager.R.color.primary_grey_900, com.amaze.filemanager.R.color.primary_blue_grey, com.amaze.filemanager.R.color.primary_teal_900 };

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
