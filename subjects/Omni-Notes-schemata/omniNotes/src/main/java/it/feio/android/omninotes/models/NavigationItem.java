/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
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
package it.feio.android.omninotes.models;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NavigationItem {
    static final int MUID_STATIC = getMUID();
    private int arrayIndex;

    private java.lang.String text;

    private int icon;

    private int iconSelected;

    public NavigationItem(int arrayIndex, java.lang.String text, int icon, int iconSelected) {
        this.arrayIndex = arrayIndex;
        this.text = text;
        this.icon = icon;
        this.iconSelected = iconSelected;
    }


    public int getArrayIndex() {
        return arrayIndex;
    }


    public void setArrayIndex(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }


    public java.lang.String getText() {
        return text;
    }


    public void setText(java.lang.String text) {
        this.text = text;
    }


    public int getIcon() {
        return icon;
    }


    public void setIcon(int icon) {
        this.icon = icon;
    }


    public int getIconSelected() {
        return iconSelected;
    }


    public void setIconSelected(int iconSelected) {
        this.iconSelected = iconSelected;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
