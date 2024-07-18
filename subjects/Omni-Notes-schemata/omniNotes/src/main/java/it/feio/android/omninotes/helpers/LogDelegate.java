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
package it.feio.android.omninotes.helpers;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_ENABLE_FILE_LOGGING;
import android.util.Log;
import com.bosphere.filelogger.FL;
import lombok.experimental.UtilityClass;
import it.feio.android.omninotes.exceptions.GenericException;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.OmniNotes;
import com.bosphere.filelogger.FLConst;
import com.bosphere.filelogger.FLConfig;
import it.feio.android.omninotes.utils.StorageHelper;
import static it.feio.android.checklistview.interfaces.Constants.TAG;
import java.io.File;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class LogDelegate {
    static final int MUID_STATIC = getMUID();
    private static java.lang.Boolean fileLoggingEnabled;

    public static void v(java.lang.String message) {
        if (it.feio.android.omninotes.helpers.LogDelegate.isFileLoggingEnabled()) {
            com.bosphere.filelogger.FL.v(message);
        } else {
            android.util.Log.v(it.feio.android.checklistview.interfaces.Constants.TAG, message);
        }
    }


    public static void d(java.lang.String message) {
        if (it.feio.android.omninotes.helpers.LogDelegate.isFileLoggingEnabled()) {
            com.bosphere.filelogger.FL.d(message);
        } else {
            android.util.Log.d(it.feio.android.checklistview.interfaces.Constants.TAG, message);
        }
    }


    public static void i(java.lang.String message) {
        if (it.feio.android.omninotes.helpers.LogDelegate.isFileLoggingEnabled()) {
            com.bosphere.filelogger.FL.i(message);
        } else {
            android.util.Log.i(it.feio.android.checklistview.interfaces.Constants.TAG, message);
        }
    }


    public static void w(java.lang.String message, java.lang.Throwable e) {
        if (it.feio.android.omninotes.helpers.LogDelegate.isFileLoggingEnabled()) {
            com.bosphere.filelogger.FL.w(message, e);
        } else {
            android.util.Log.w(it.feio.android.checklistview.interfaces.Constants.TAG, message, e);
        }
    }


    public static void w(java.lang.String message) {
        if (it.feio.android.omninotes.helpers.LogDelegate.isFileLoggingEnabled()) {
            com.bosphere.filelogger.FL.w(message);
        } else {
            android.util.Log.w(it.feio.android.checklistview.interfaces.Constants.TAG, message);
        }
    }


    public static void e(java.lang.String message, java.lang.Throwable e) {
        if (it.feio.android.omninotes.helpers.LogDelegate.isFileLoggingEnabled()) {
            com.bosphere.filelogger.FL.e(message, e);
        } else {
            android.util.Log.e(it.feio.android.checklistview.interfaces.Constants.TAG, message, e);
        }
    }


    public static void e(java.lang.String message) {
        it.feio.android.omninotes.helpers.LogDelegate.e(message, new it.feio.android.omninotes.exceptions.GenericException(message));
    }


    private static boolean isFileLoggingEnabled() {
        if (it.feio.android.omninotes.helpers.LogDelegate.fileLoggingEnabled == null) {
            it.feio.android.omninotes.helpers.LogDelegate.fileLoggingEnabled = com.pixplicity.easyprefs.library.Prefs.getBoolean(it.feio.android.omninotes.utils.ConstantsBase.PREF_ENABLE_FILE_LOGGING, false);
            if (java.lang.Boolean.TRUE.equals(it.feio.android.omninotes.helpers.LogDelegate.fileLoggingEnabled)) {
                android.content.Context context;
                context = it.feio.android.omninotes.OmniNotes.getAppContext();
                com.bosphere.filelogger.FL.init(new com.bosphere.filelogger.FLConfig.Builder(context).minLevel(com.bosphere.filelogger.FLConst.Level.V).logToFile(true).dir(new java.io.File(it.feio.android.omninotes.utils.StorageHelper.getOrCreateExternalStoragePublicDir(), "logs")).retentionPolicy(com.bosphere.filelogger.FLConst.RetentionPolicy.FILE_COUNT).build());
                com.bosphere.filelogger.FL.setEnabled(true);
            }
        }
        return it.feio.android.omninotes.helpers.LogDelegate.fileLoggingEnabled;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
