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
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_CURRENT_APP_VERSION;
import android.content.pm.PackageManager.NameNotFoundException;
import lombok.experimental.UtilityClass;
import com.pixplicity.easyprefs.library.Prefs;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class AppVersionHelper {
    static final int MUID_STATIC = getMUID();
    public static boolean isAppUpdated(android.content.Context context) {
        return it.feio.android.omninotes.helpers.AppVersionHelper.getCurrentAppVersion(context) > it.feio.android.omninotes.helpers.AppVersionHelper.getAppVersionFromPreferences(context);
    }


    public static int getAppVersionFromPreferences(android.content.Context context) {
        switch(MUID_STATIC) {
            // AppVersionHelper_0_BinaryMutator
            case 126: {
                try {
                    return com.pixplicity.easyprefs.library.Prefs.getInt(it.feio.android.omninotes.utils.ConstantsBase.PREF_CURRENT_APP_VERSION, 1);
                } catch (java.lang.ClassCastException e) {
                    return it.feio.android.omninotes.helpers.AppVersionHelper.getCurrentAppVersion(context) + 1;
                }
            }
            default: {
            try {
                return com.pixplicity.easyprefs.library.Prefs.getInt(it.feio.android.omninotes.utils.ConstantsBase.PREF_CURRENT_APP_VERSION, 1);
            } catch (java.lang.ClassCastException e) {
                return it.feio.android.omninotes.helpers.AppVersionHelper.getCurrentAppVersion(context) - 1;
            }
            }
    }
}


public static void updateAppVersionInPreferences(android.content.Context context) {
    com.pixplicity.easyprefs.library.Prefs.edit().putInt(it.feio.android.omninotes.utils.ConstantsBase.PREF_CURRENT_APP_VERSION, it.feio.android.omninotes.helpers.AppVersionHelper.getCurrentAppVersion(context)).apply();
}


public static int getCurrentAppVersion(android.content.Context context) {
    try {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        // Cannot happen, it's just this app, but, ok...
        return 1;
    }
}


public static java.lang.String getCurrentAppVersionName(android.content.Context context) {
    try {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        // Cannot happen, it's just this app, but, ok...
        return "1.0.0";
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
