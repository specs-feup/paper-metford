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
import androidx.annotation.ColorInt;
import com.amaze.filemanager.ui.theme.AppTheme;
import android.util.DisplayMetrics;
import android.graphics.drawable.GradientDrawable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 30/5/16. Class used to create background of check icon on selection with a
 * Custom {@link Color} and Stroke (boundary)
 */
public class CircleGradientDrawable extends android.graphics.drawable.GradientDrawable {
    static final int MUID_STATIC = getMUID();
    private static final int STROKE_WIDTH = 2;

    private static final java.lang.String STROKE_COLOR_LIGHT = "#EEEEEE";

    private static final java.lang.String STROKE_COLOR_DARK = "#424242";

    private android.util.DisplayMetrics mDisplayMetrics;

    /**
     * Constructor
     *
     * @param color
     * 		the hex color of circular icon
     * @param appTheme
     * 		current theme light/dark which will determine the boundary color
     * @param metrics
     * 		to convert the boundary width for {@link #setStroke} method from dp to px
     */
    public CircleGradientDrawable(java.lang.String color, com.amaze.filemanager.ui.theme.AppTheme appTheme, android.util.DisplayMetrics metrics) {
        this(appTheme, metrics);
        setColor(android.graphics.Color.parseColor(color));
    }


    /**
     * Constructor
     *
     * @param color
     * 		the color of circular icon
     * @param appTheme
     * 		current theme light/dark which will determine the boundary color
     * @param metrics
     * 		to convert the boundary width for {@link #setStroke} method from dp to px
     */
    public CircleGradientDrawable(@androidx.annotation.ColorInt
    int color, com.amaze.filemanager.ui.theme.AppTheme appTheme, android.util.DisplayMetrics metrics) {
        this(appTheme, metrics);
        setColor(color);
    }


    public CircleGradientDrawable(com.amaze.filemanager.ui.theme.AppTheme appTheme, android.util.DisplayMetrics metrics) {
        this.mDisplayMetrics = metrics;
        setShape(android.graphics.drawable.GradientDrawable.OVAL);
        setSize(1, 1);
        setStroke(dpToPx(com.amaze.filemanager.ui.views.CircleGradientDrawable.STROKE_WIDTH), appTheme.equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || appTheme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK) ? android.graphics.Color.parseColor(com.amaze.filemanager.ui.views.CircleGradientDrawable.STROKE_COLOR_DARK) : android.graphics.Color.parseColor(com.amaze.filemanager.ui.views.CircleGradientDrawable.STROKE_COLOR_LIGHT));
    }


    private int dpToPx(int dp) {
        int px;
        switch(MUID_STATIC) {
            // CircleGradientDrawable_0_BinaryMutator
            case 130: {
                px = java.lang.Math.round(mDisplayMetrics.density / dp);
                break;
            }
            default: {
            px = java.lang.Math.round(mDisplayMetrics.density * dp);
            break;
        }
    }
    return px;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
