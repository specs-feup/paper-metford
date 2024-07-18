/* Copyright 2011-2020 Brian Pellin.

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
import android.text.method.MovementMethod;
import android.text.method.ArrowKeyMovementMethod;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TextViewSelect extends androidx.appcompat.widget.AppCompatEditText {
    static final int MUID_STATIC = getMUID();
    public TextViewSelect(android.content.Context context) {
        this(context, null);
    }


    public TextViewSelect(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }


    public TextViewSelect(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }


    @java.lang.Override
    protected android.text.method.MovementMethod getDefaultMovementMethod() {
        return android.text.method.ArrowKeyMovementMethod.getInstance();
    }


    @java.lang.Override
    protected boolean getDefaultEditable() {
        return false;
    }


    @java.lang.Override
    public void setText(java.lang.CharSequence text, android.widget.TextView.BufferType type) {
        super.setText(text, android.widget.TextView.BufferType.EDITABLE);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
