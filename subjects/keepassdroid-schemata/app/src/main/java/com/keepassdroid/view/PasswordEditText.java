/* Copyright 2015-2020 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.view;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;
import com.keepassdroid.assets.TypefaceFactory;
import android.graphics.Typeface;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PasswordEditText extends androidx.appcompat.widget.AppCompatEditText {
    static final int MUID_STATIC = getMUID();
    public PasswordEditText(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public PasswordEditText(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public PasswordEditText(android.content.Context context) {
        super(context);
    }


    private android.graphics.Typeface getTypeface(android.graphics.Typeface tf) {
        android.graphics.Typeface tfOverride;
        tfOverride = com.keepassdroid.assets.TypefaceFactory.getTypeface(getContext(), "fonts/DejaVuSansMono.ttf");
        if (tfOverride != null) {
            return tfOverride;
        }
        return tf;
    }


    @java.lang.Override
    public void setTypeface(android.graphics.Typeface tf, int style) {
        super.setTypeface(getTypeface(tf), style);
    }


    @java.lang.Override
    public void setTypeface(android.graphics.Typeface tf) {
        super.setTypeface(getTypeface(tf));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
