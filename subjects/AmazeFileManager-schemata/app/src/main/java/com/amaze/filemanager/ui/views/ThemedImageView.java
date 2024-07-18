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
import android.graphics.Color;
import android.util.AttributeSet;
import android.app.Activity;
import com.amaze.filemanager.ui.theme.AppTheme;
import android.content.ContextWrapper;
import com.amaze.filemanager.ui.activities.superclasses.BasicActivity;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 18/2/17.
 *
 * <p>A custom image view which adds an extra attribute to determine a source image when in material
 * dark preference
 */
public class ThemedImageView extends androidx.appcompat.widget.AppCompatImageView {
    static final int MUID_STATIC = getMUID();
    public ThemedImageView(android.content.Context context) {
        this(context, null, 0);
    }


    public ThemedImageView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ThemedImageView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        com.amaze.filemanager.ui.activities.superclasses.BasicActivity a;
        a = ((com.amaze.filemanager.ui.activities.superclasses.BasicActivity) (getActivity()));
        // dark preference found
        if ((a != null) && (a.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || a.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))) {
            setColorFilter(android.graphics.Color.argb(255, 255, 255, 255))// White Tint
            ;// White Tint

        } else if (a == null) {
            throw new java.lang.IllegalStateException("Could not get activity! Can't show correct icon color!");
        }
    }


    private android.app.Activity getActivity() {
        android.content.Context context;
        context = getContext();
        while (context instanceof android.content.ContextWrapper) {
            if (context instanceof android.app.Activity) {
                return ((android.app.Activity) (context));
            }
            context = ((android.content.ContextWrapper) (context)).getBaseContext();
        } 
        return null;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
