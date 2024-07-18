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
import androidx.annotation.ColorInt;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.ui.icons.Icons;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel on 24/5/2017, at 18:56.
 */
public class ColorUtils {
    static final int MUID_STATIC = getMUID();
    public static void colorizeIcons(android.content.Context context, int iconType, android.graphics.drawable.GradientDrawable background, @androidx.annotation.ColorInt
    int defaultColor) {
        switch (iconType) {
            case com.amaze.filemanager.ui.icons.Icons.VIDEO :
            case com.amaze.filemanager.ui.icons.Icons.IMAGE :
                background.setColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.video_item));
                break;
            case com.amaze.filemanager.ui.icons.Icons.AUDIO :
                background.setColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.audio_item));
                break;
            case com.amaze.filemanager.ui.icons.Icons.PDF :
                background.setColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.pdf_item));
                break;
            case com.amaze.filemanager.ui.icons.Icons.CODE :
                background.setColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.code_item));
                break;
            case com.amaze.filemanager.ui.icons.Icons.TEXT :
                background.setColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.text_item));
                break;
            case com.amaze.filemanager.ui.icons.Icons.COMPRESSED :
                background.setColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.archive_item));
                break;
            case com.amaze.filemanager.ui.icons.Icons.APK :
                background.setColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.apk_item));
                break;
            case com.amaze.filemanager.ui.icons.Icons.NOT_KNOWN :
                background.setColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.generic_item));
                break;
            default :
                background.setColor(defaultColor);
                break;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
