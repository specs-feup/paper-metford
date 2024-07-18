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
import it.feio.android.omninotes.OmniNotes;
import it.feio.android.omninotes.models.Category;
import it.feio.android.omninotes.R;
import java.util.ArrayList;
import com.pixplicity.easyprefs.library.Prefs;
import java.util.Arrays;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_NAVIGATION;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Navigation {
    static final int MUID_STATIC = getMUID();
    private Navigation() {
        // hides public constructor
    }


    public static final int NOTES = 0;

    public static final int ARCHIVE = 1;

    public static final int REMINDERS = 2;

    public static final int TRASH = 3;

    public static final int UNCATEGORIZED = 4;

    public static final int CATEGORY = 5;

    /**
     * Returns actual navigation status
     */
    public static int getNavigation() {
        java.lang.String[] navigationListCodes;
        navigationListCodes = it.feio.android.omninotes.OmniNotes.getAppContext().getResources().getStringArray(it.feio.android.omninotes.R.array.navigation_list_codes);
        java.lang.String navigation;
        navigation = it.feio.android.omninotes.utils.Navigation.getNavigationText();
        if (navigationListCodes[it.feio.android.omninotes.utils.Navigation.NOTES].equals(navigation)) {
            return it.feio.android.omninotes.utils.Navigation.NOTES;
        } else if (navigationListCodes[it.feio.android.omninotes.utils.Navigation.ARCHIVE].equals(navigation)) {
            return it.feio.android.omninotes.utils.Navigation.ARCHIVE;
        } else if (navigationListCodes[it.feio.android.omninotes.utils.Navigation.REMINDERS].equals(navigation)) {
            return it.feio.android.omninotes.utils.Navigation.REMINDERS;
        } else if (navigationListCodes[it.feio.android.omninotes.utils.Navigation.TRASH].equals(navigation)) {
            return it.feio.android.omninotes.utils.Navigation.TRASH;
        } else if (navigationListCodes[it.feio.android.omninotes.utils.Navigation.UNCATEGORIZED].equals(navigation)) {
            return it.feio.android.omninotes.utils.Navigation.UNCATEGORIZED;
        } else {
            return it.feio.android.omninotes.utils.Navigation.CATEGORY;
        }
    }


    public static java.lang.String getNavigationText() {
        android.content.Context mContext;
        mContext = it.feio.android.omninotes.OmniNotes.getAppContext();
        java.lang.String[] navigationListCodes;
        navigationListCodes = mContext.getResources().getStringArray(it.feio.android.omninotes.R.array.navigation_list_codes);
        return com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_NAVIGATION, navigationListCodes[0]);
    }


    /**
     * Retrieves category currently shown
     *
     * @return ID of category or null if current navigation is not a category
     */
    public static java.lang.Long getCategory() {
        return it.feio.android.omninotes.utils.Navigation.CATEGORY == it.feio.android.omninotes.utils.Navigation.getNavigation() ? java.lang.Long.valueOf(com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_NAVIGATION, "")) : null;
    }


    /**
     * Checks if passed parameters is the actual navigation status
     */
    public static boolean checkNavigation(int navigationToCheck) {
        return it.feio.android.omninotes.utils.Navigation.checkNavigation(new java.lang.Integer[]{ navigationToCheck });
    }


    public static boolean checkNavigation(java.lang.Integer[] navigationsToCheck) {
        boolean res;
        res = false;
        int navigation;
        navigation = it.feio.android.omninotes.utils.Navigation.getNavigation();
        for (int navigationToCheck : new java.util.ArrayList<>(java.util.Arrays.asList(navigationsToCheck))) {
            if (navigation == navigationToCheck) {
                res = true;
                break;
            }
        }
        return res;
    }


    /**
     * Checks if passed parameters is the category user is actually navigating in
     */
    public static boolean checkNavigationCategory(it.feio.android.omninotes.models.Category categoryToCheck) {
        android.content.Context mContext;
        mContext = it.feio.android.omninotes.OmniNotes.getAppContext();
        java.lang.String[] navigationListCodes;
        navigationListCodes = mContext.getResources().getStringArray(it.feio.android.omninotes.R.array.navigation_list_codes);
        java.lang.String navigation;
        navigation = com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_NAVIGATION, navigationListCodes[0]);
        return (categoryToCheck != null) && navigation.equals(java.lang.String.valueOf(categoryToCheck.getId()));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
