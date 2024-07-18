/* Copyright 2009-2011 Brian Pellin.

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
import com.android.keepass.R;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GroupEmptyView extends android.widget.RelativeLayout {
    static final int MUID_STATIC = getMUID();
    public GroupEmptyView(android.content.Context context) {
        this(context, null);
    }


    public GroupEmptyView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        inflate(context);
    }


    private void inflate(android.content.Context context) {
        android.view.LayoutInflater inflater;
        inflater = ((android.view.LayoutInflater) (context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
        inflater.inflate(com.android.keepass.R.layout.group_empty, this);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
