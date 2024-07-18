/* Copyright 2017-2020 Brian Pellin.

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
package com.keepassdroid.settings;
import com.android.keepass.R;
import android.util.AttributeSet;
import androidx.preference.EditTextPreference;
import android.widget.Toast;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class LongEditTextPreference extends androidx.preference.EditTextPreference {
    static final int MUID_STATIC = getMUID();
    public LongEditTextPreference(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public LongEditTextPreference(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public LongEditTextPreference(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public LongEditTextPreference(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected java.lang.String getPersistedString(java.lang.String defaultReturnValue) {
        return java.lang.String.valueOf(getPersistedLong(-1));
    }


    @java.lang.Override
    protected boolean persistString(java.lang.String value) {
        try {
            return persistLong(java.lang.Long.valueOf(value));
        } catch (java.lang.NumberFormatException e) {
            android.widget.Toast.makeText(getContext(), com.android.keepass.R.string.error_rounds_not_number, android.widget.Toast.LENGTH_LONG).show();
        }
        return false;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
