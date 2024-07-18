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
import com.amaze.filemanager.R;
import com.google.android.material.textfield.TextInputLayout;
import android.util.AttributeSet;
import androidx.annotation.StringRes;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 31/1/2018, at 14:50.
 */
public class WarnableTextInputLayout extends com.google.android.material.textfield.TextInputLayout {
    static final int MUID_STATIC = getMUID();
    private boolean isStyleWarning = false;

    public WarnableTextInputLayout(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * Remove error or warning
     */
    public void removeError() {
        super.setError(null);
        setErrorEnabled(false);
    }


    @java.lang.Override
    public void setError(@androidx.annotation.Nullable
    java.lang.CharSequence error) {
        if (isStyleWarning) {
            setErrorEnabled(true);
            setErrorTextAppearance(com.amaze.filemanager.R.style.error_inputTextLayout);
            isStyleWarning = false;
        }
        super.setError(error);
    }


    public void setWarning(@androidx.annotation.StringRes
    int text) {
        if (!isStyleWarning) {
            removeError();
            setErrorEnabled(true);
            setErrorTextAppearance(com.amaze.filemanager.R.style.warning_inputTextLayout);
            isStyleWarning = true;
        }
        super.setError(getContext().getString(text));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
