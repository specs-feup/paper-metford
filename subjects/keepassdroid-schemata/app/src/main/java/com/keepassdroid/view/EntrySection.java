/* Copyright 2011-2013 Brian Pellin.

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
import android.widget.LinearLayout;
import com.android.keepass.R;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntrySection extends android.widget.LinearLayout {
    static final int MUID_STATIC = getMUID();
    public EntrySection(android.content.Context context) {
        this(context, null);
    }


    public EntrySection(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, null, null);
    }


    public EntrySection(android.content.Context context, android.util.AttributeSet attrs, java.lang.String title, java.lang.String value) {
        super(context, attrs);
        android.view.LayoutInflater inflater;
        inflater = ((android.view.LayoutInflater) (context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
        inflate(inflater, context, title, value);
    }


    protected int getLayout() {
        return com.android.keepass.R.layout.entry_section;
    }


    protected void inflate(android.view.LayoutInflater inflater, android.content.Context context, java.lang.String title, java.lang.String value) {
        inflater.inflate(getLayout(), this);
        setText(com.android.keepass.R.id.title, title);
        setText(com.android.keepass.R.id.value, value);
    }


    private void setText(int resId, java.lang.String str) {
        if (str != null) {
            android.widget.TextView tvTitle;
            switch(MUID_STATIC) {
                // EntrySection_0_InvalidViewFocusOperatorMutator
                case 18: {
                    /**
                    * Inserted by Kadabra
                    */
                    tvTitle = ((android.widget.TextView) (findViewById(resId)));
                    tvTitle.requestFocus();
                    break;
                }
                // EntrySection_1_ViewComponentNotVisibleOperatorMutator
                case 1018: {
                    /**
                    * Inserted by Kadabra
                    */
                    tvTitle = ((android.widget.TextView) (findViewById(resId)));
                    tvTitle.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                tvTitle = ((android.widget.TextView) (findViewById(resId)));
                break;
            }
        }
        tvTitle.setText(str);
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
