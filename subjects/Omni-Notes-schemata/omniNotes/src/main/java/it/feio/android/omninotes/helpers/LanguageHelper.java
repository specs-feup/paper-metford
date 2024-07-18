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
import java.util.Locale;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import android.os.Build;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_LANG;
import lombok.experimental.UtilityClass;
import com.pixplicity.easyprefs.library.Prefs;
import android.annotation.TargetApi;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class LanguageHelper {
    static final int MUID_STATIC = getMUID();
    /**
     * Updates default language with forced one
     */
    @android.annotation.SuppressLint("ApplySharedPref")
    public static android.content.Context updateLanguage(android.content.Context ctx, java.lang.String lang) {
        java.lang.String language;
        language = com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_LANG, "");
        java.util.Locale locale;
        locale = null;
        if (android.text.TextUtils.isEmpty(language) && (lang == null)) {
            locale = java.util.Locale.getDefault();
        } else if (lang != null) {
            locale = it.feio.android.omninotes.helpers.LanguageHelper.getLocale(lang);
            com.pixplicity.easyprefs.library.Prefs.edit().putString(it.feio.android.omninotes.utils.ConstantsBase.PREF_LANG, lang).commit();
        } else if (!android.text.TextUtils.isEmpty(language)) {
            locale = it.feio.android.omninotes.helpers.LanguageHelper.getLocale(language);
        }
        return it.feio.android.omninotes.helpers.LanguageHelper.setLocale(ctx, locale);
    }


    public static android.content.Context resetSystemLanguage(android.content.Context ctx) {
        com.pixplicity.easyprefs.library.Prefs.edit().remove(it.feio.android.omninotes.utils.ConstantsBase.PREF_LANG).apply();
        return it.feio.android.omninotes.helpers.LanguageHelper.setLocale(ctx, java.util.Locale.getDefault());
    }


    private static android.content.Context setLocale(android.content.Context context, java.util.Locale locale) {
        android.content.res.Configuration configuration;
        configuration = context.getResources().getConfiguration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration, null);
        return context;
    }


    /**
     * Checks country AND region
     */
    private static java.util.Locale getLocale(java.lang.String lang) {
        if (lang.contains("_")) {
            return new java.util.Locale(lang.split("_")[0], lang.split("_")[1]);
        } else {
            return new java.util.Locale(lang);
        }
    }


    @android.annotation.TargetApi(android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
    @androidx.annotation.NonNull
    static java.lang.String getLocalizedString(android.content.Context context, java.lang.String desiredLocale, int resourceId) {
        if (desiredLocale.equals(it.feio.android.omninotes.helpers.LanguageHelper.getCurrentLocaleAsString(context))) {
            return context.getResources().getString(resourceId);
        }
        android.content.res.Configuration conf;
        conf = context.getResources().getConfiguration();
        conf = new android.content.res.Configuration(conf);
        conf.setLocale(it.feio.android.omninotes.helpers.LanguageHelper.getLocale(desiredLocale));
        android.content.Context localizedContext;
        localizedContext = context.createConfigurationContext(conf);
        return localizedContext.getResources().getString(resourceId);
    }


    public static java.lang.String getCurrentLocaleAsString(android.content.Context context) {
        return it.feio.android.omninotes.helpers.LanguageHelper.getCurrentLocale(context).toString();
    }


    public static java.util.Locale getCurrentLocale(android.content.Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
