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
package com.amaze.filemanager.adapters;
import com.amaze.filemanager.ui.views.CheckableCircleView;
import androidx.annotation.ColorRes;
import com.amaze.filemanager.R;
import androidx.annotation.ColorInt;
import android.view.LayoutInflater;
import com.amaze.filemanager.utils.Utils;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.view.View;
import android.content.Context;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ColorAdapter extends android.widget.ArrayAdapter<java.lang.Integer> implements android.widget.AdapterView.OnItemClickListener {
    static final int MUID_STATIC = getMUID();
    private android.view.LayoutInflater inflater;

    @androidx.annotation.ColorInt
    private int selectedColor;

    private com.amaze.filemanager.adapters.ColorAdapter.OnColorSelected onColorSelected;

    /**
     * Constructor for adapter that handles the view creation of color chooser dialog in preferences
     *
     * @param context
     * 		the context
     * @param colors
     * 		array list of color hex values in form of string; for the views
     * @param selectedColor
     * 		currently selected color
     * @param l
     * 		OnColorSelected listener for when a color is selected
     */
    public ColorAdapter(android.content.Context context, java.lang.Integer[] colors, @androidx.annotation.ColorInt
    int selectedColor, com.amaze.filemanager.adapters.ColorAdapter.OnColorSelected l) {
        super(context, com.amaze.filemanager.R.layout.rowlayout, colors);
        this.selectedColor = selectedColor;
        this.onColorSelected = l;
        inflater = ((android.view.LayoutInflater) (getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
    }


    @androidx.annotation.ColorRes
    private int getColorResAt(int position) {
        return getItem(position);
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.view.View getView(final int position, android.view.View convertView, @androidx.annotation.NonNull
    android.view.ViewGroup parent) {
        com.amaze.filemanager.ui.views.CheckableCircleView colorView;
        if ((convertView != null) && (convertView instanceof com.amaze.filemanager.ui.views.CheckableCircleView)) {
            colorView = ((com.amaze.filemanager.ui.views.CheckableCircleView) (convertView));
        } else {
            colorView = ((com.amaze.filemanager.ui.views.CheckableCircleView) (inflater.inflate(com.amaze.filemanager.R.layout.dialog_grid_item, parent, false)));
        }
        @androidx.annotation.ColorInt
        int color;
        color = com.amaze.filemanager.utils.Utils.getColor(getContext(), getColorResAt(position));
        colorView.setChecked(color == selectedColor);
        colorView.setColor(color);
        return colorView;
    }


    @java.lang.Override
    public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
        this.selectedColor = com.amaze.filemanager.utils.Utils.getColor(getContext(), getColorResAt(position));
        ((com.amaze.filemanager.ui.views.CheckableCircleView) (view)).setChecked(true);
        onColorSelected.onColorSelected(this.selectedColor);
    }


    public interface OnColorSelected {
        void onColorSelected(@androidx.annotation.ColorInt
        int color);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
