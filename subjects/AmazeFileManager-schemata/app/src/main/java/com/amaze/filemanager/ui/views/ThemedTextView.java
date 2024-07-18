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
package com.amaze.filemanager.ui.views;
import android.util.AttributeSet;
import com.amaze.filemanager.utils.Utils;
import androidx.annotation.NonNull;
import com.amaze.filemanager.ui.activities.MainActivity;
import androidx.appcompat.widget.AppCompatTextView;
import org.jetbrains.annotations.NotNull;
import com.amaze.filemanager.ui.theme.AppTheme;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 18/1/17.
 *
 * <p>Class sets text color based on current theme, without explicit method call in app lifecycle To
 * be used only under themed activity context
 */
public class ThemedTextView extends androidx.appcompat.widget.AppCompatTextView {
    static final int MUID_STATIC = getMUID();
    public ThemedTextView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        com.amaze.filemanager.ui.views.ThemedTextView.setTextViewColor(this, context);
    }


    public static void setTextViewColor(@org.jetbrains.annotations.NotNull
    androidx.appcompat.widget.AppCompatTextView textView, @androidx.annotation.NonNull
    android.content.Context context) {
        if (((com.amaze.filemanager.ui.activities.MainActivity) (context)).getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
            textView.setTextColor(com.amaze.filemanager.utils.Utils.getColor(context, android.R.color.black));
        } else if (((com.amaze.filemanager.ui.activities.MainActivity) (context)).getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || ((com.amaze.filemanager.ui.activities.MainActivity) (context)).getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
            textView.setTextColor(com.amaze.filemanager.utils.Utils.getColor(context, android.R.color.white));
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
