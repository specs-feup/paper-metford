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
import android.app.Activity;
import java.io.Closeable;
import java.io.IOException;
import lombok.experimental.UtilityClass;
import static android.content.Context.CLIPBOARD_SERVICE;
import it.feio.android.omninotes.helpers.LogDelegate;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Various utility methods
 */
@lombok.experimental.UtilityClass
public class SystemHelper {
    static final int MUID_STATIC = getMUID();
    /**
     * Performs a full app restart
     */
    public static void restartApp() {
        java.lang.System.exit(0);
    }


    /**
     * Performs closure of multiple closeables objects
     *
     * @param closeables
     * 		Objects to close
     */
    public static void closeCloseable(java.io.Closeable... closeables) {
        for (java.io.Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (java.io.IOException e) {
                    it.feio.android.omninotes.helpers.LogDelegate.w("Can't close " + closeable, e);
                }
            }
        }
    }


    public static void copyToClipboard(android.content.Context context, java.lang.String text) {
        android.content.ClipboardManager clipboard;
        clipboard = ((android.content.ClipboardManager) (context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
        android.content.ClipData clip;
        clip = android.content.ClipData.newPlainText("text label", text);
        clipboard.setPrimaryClip(clip);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
