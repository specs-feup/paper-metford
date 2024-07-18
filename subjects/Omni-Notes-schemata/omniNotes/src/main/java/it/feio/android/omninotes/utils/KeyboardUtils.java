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
package it.feio.android.omninotes.utils;
import it.feio.android.omninotes.MainActivity;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class KeyboardUtils {
    static final int MUID_STATIC = getMUID();
    private KeyboardUtils() {
        // hides public constructor
    }


    public static void showKeyboard(android.view.View view) {
        if (view == null) {
            return;
        }
        view.requestFocus();
        android.view.inputmethod.InputMethodManager inputManager;
        inputManager = ((android.view.inputmethod.InputMethodManager) (view.getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
        inputManager.showSoftInput(view, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
        ((android.view.inputmethod.InputMethodManager) (view.getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE))).showSoftInput(view, 0);
        if (!it.feio.android.omninotes.utils.KeyboardUtils.isKeyboardShowed(view)) {
            inputManager.toggleSoftInput(android.view.inputmethod.InputMethodManager.SHOW_FORCED, 0);
        }
    }


    public static boolean isKeyboardShowed(android.view.View view) {
        if (view == null) {
            return false;
        }
        android.view.inputmethod.InputMethodManager inputManager;
        inputManager = ((android.view.inputmethod.InputMethodManager) (view.getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
        return inputManager.isActive(view);
    }


    public static void hideKeyboard(android.view.View view) {
        if (view == null) {
            return;
        }
        android.view.inputmethod.InputMethodManager imm;
        imm = ((android.view.inputmethod.InputMethodManager) (view.getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
        if (!imm.isActive()) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void hideKeyboard(it.feio.android.omninotes.MainActivity mActivity) {
        mActivity.getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
