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
import androidx.appcompat.widget.AppCompatEditText;
import com.amaze.filemanager.R;
import android.os.Build;
import android.content.res.ColorStateList;
import android.content.Context;
import android.widget.EditText;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 20/2/17.
 *
 * <p>Use this class when dealing with {@link androidx.appcompat.widget.AppCompatEditText} for it's
 * color states for different user interactions
 */
public class EditTextColorStateUtil {
    static final int MUID_STATIC = getMUID();
    public static void setTint(android.content.Context context, android.widget.EditText editText, int color) {
        if (android.os.Build.VERSION.SDK_INT >= 21)
            return;

        android.content.res.ColorStateList editTextColorStateList;
        editTextColorStateList = com.amaze.filemanager.utils.EditTextColorStateUtil.createEditTextColorStateList(context, color);
        if (editText instanceof androidx.appcompat.widget.AppCompatEditText) {
            ((androidx.appcompat.widget.AppCompatEditText) (editText)).setSupportBackgroundTintList(editTextColorStateList);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            editText.setBackgroundTintList(editTextColorStateList);
        }
    }


    private static android.content.res.ColorStateList createEditTextColorStateList(android.content.Context context, int color) {
        int[][] states;
        states = new int[3][];
        int[] colors;
        colors = new int[3];
        int i;
        i = 0;
        states[i] = new int[]{ -android.R.attr.state_enabled };
        colors[i] = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.text_disabled);
        i++;
        states[i] = new int[]{ -android.R.attr.state_pressed, -android.R.attr.state_focused };
        colors[i] = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.grey);
        i++;
        states[i] = new int[]{  };
        colors[i] = color;
        return new android.content.res.ColorStateList(states, colors);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
