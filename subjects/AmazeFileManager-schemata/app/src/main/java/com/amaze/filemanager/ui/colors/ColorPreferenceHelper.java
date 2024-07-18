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
package com.amaze.filemanager.ui.colors;
import androidx.annotation.ColorRes;
import android.content.SharedPreferences;
import java.util.Random;
import com.amaze.filemanager.R;
import androidx.annotation.ColorInt;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import java.util.List;
import java.util.Arrays;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ColorPreferenceHelper {
    static final int MUID_STATIC = getMUID();
    @androidx.annotation.ColorRes
    public static final int DEFAULT_PRIMARY_FIRST_TAB = com.amaze.filemanager.R.color.primary_indigo;

    @androidx.annotation.ColorRes
    public static final int DEFAULT_PRIMARY_SECOND_TAB = com.amaze.filemanager.R.color.primary_indigo;

    @androidx.annotation.ColorRes
    public static final int DEFAULT_ACCENT = com.amaze.filemanager.R.color.primary_pink;

    @androidx.annotation.ColorRes
    public static final int DEFAULT_ICON_SKIN = com.amaze.filemanager.R.color.primary_pink;

    /**
     * Combinations used when randomizing color selection at startup.
     */
    @androidx.annotation.ColorRes
    private static final int[][] RANDOM_COMBINATIONS = new int[][]{ new int[]{ com.amaze.filemanager.R.color.primary_brown, com.amaze.filemanager.R.color.primary_amber, com.amaze.filemanager.R.color.primary_orange }, new int[]{ com.amaze.filemanager.R.color.primary_indigo, com.amaze.filemanager.R.color.primary_pink, com.amaze.filemanager.R.color.primary_indigo }, new int[]{ com.amaze.filemanager.R.color.primary_teal, com.amaze.filemanager.R.color.primary_orange, com.amaze.filemanager.R.color.primary_teal }, new int[]{ com.amaze.filemanager.R.color.primary_teal_900, com.amaze.filemanager.R.color.primary_amber, com.amaze.filemanager.R.color.primary_orange }, new int[]{ com.amaze.filemanager.R.color.primary_deep_purple, com.amaze.filemanager.R.color.primary_pink, com.amaze.filemanager.R.color.primary_deep_purple }, new int[]{ com.amaze.filemanager.R.color.primary_blue_grey, com.amaze.filemanager.R.color.primary_brown, com.amaze.filemanager.R.color.primary_blue_grey }, new int[]{ com.amaze.filemanager.R.color.primary_pink, com.amaze.filemanager.R.color.primary_orange, com.amaze.filemanager.R.color.primary_pink }, new int[]{ com.amaze.filemanager.R.color.primary_blue_grey, com.amaze.filemanager.R.color.primary_red, com.amaze.filemanager.R.color.primary_blue_grey }, new int[]{ com.amaze.filemanager.R.color.primary_red, com.amaze.filemanager.R.color.primary_orange, com.amaze.filemanager.R.color.primary_red }, new int[]{ com.amaze.filemanager.R.color.primary_light_blue, com.amaze.filemanager.R.color.primary_pink, com.amaze.filemanager.R.color.primary_light_blue }, new int[]{ com.amaze.filemanager.R.color.primary_cyan, com.amaze.filemanager.R.color.primary_pink, com.amaze.filemanager.R.color.primary_cyan } };

    /**
     * Randomizes (but does not save) the colors used by the interface.
     *
     * @return The {@link ColorPreference} object itself.
     */
    public static com.amaze.filemanager.ui.colors.UserColorPreferences randomize(android.content.Context c) {
        @androidx.annotation.ColorRes
        int[] colorPos;
        colorPos = com.amaze.filemanager.ui.colors.ColorPreferenceHelper.RANDOM_COMBINATIONS[new java.util.Random().nextInt(com.amaze.filemanager.ui.colors.ColorPreferenceHelper.RANDOM_COMBINATIONS.length)];
        return new com.amaze.filemanager.ui.colors.UserColorPreferences(com.amaze.filemanager.utils.Utils.getColor(c, colorPos[0]), com.amaze.filemanager.utils.Utils.getColor(c, colorPos[0]), com.amaze.filemanager.utils.Utils.getColor(c, colorPos[1]), com.amaze.filemanager.utils.Utils.getColor(c, colorPos[2]));
    }


    /**
     * Eases the retrieval of primary colors ColorUsage. If the index is out of bounds, the first
     * primary color is returned as default.
     *
     * @param num
     * 		The primary color index
     * @return The ColorUsage for the given primary color.
     */
    @androidx.annotation.ColorInt
    public static int getPrimary(com.amaze.filemanager.ui.colors.UserColorPreferences currentColors, int num) {
        return num == 1 ? currentColors.getPrimarySecondTab() : currentColors.getPrimaryFirstTab();
    }


    private com.amaze.filemanager.ui.colors.UserColorPreferences currentColors;

    public com.amaze.filemanager.ui.colors.UserColorPreferences getCurrentUserColorPreferences(android.content.Context context, android.content.SharedPreferences prefs) {
        if (currentColors == null)
            currentColors = getColorPreferences(context, prefs);

        return currentColors;
    }


    public void saveColorPreferences(android.content.SharedPreferences prefs, com.amaze.filemanager.ui.colors.UserColorPreferences userPrefs) {
        android.content.SharedPreferences.Editor editor;
        editor = prefs.edit();
        editor.putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN, userPrefs.getPrimaryFirstTab());
        editor.putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN_TWO, userPrefs.getPrimarySecondTab());
        editor.putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ACCENT, userPrefs.getAccent());
        editor.putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ICON_SKIN, userPrefs.getIconSkin());
        editor.apply();
        currentColors = userPrefs;
    }


    private com.amaze.filemanager.ui.colors.UserColorPreferences getColorPreferences(android.content.Context c, android.content.SharedPreferences prefs) {
        if (isUsingOldColorsSystem(prefs))
            correctToNewColorsSystem(c, prefs);

        int tabOne;
        tabOne = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN, com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.ui.colors.ColorPreferenceHelper.DEFAULT_PRIMARY_FIRST_TAB));
        int tabTwo;
        tabTwo = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN_TWO, com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.ui.colors.ColorPreferenceHelper.DEFAULT_PRIMARY_SECOND_TAB));
        int accent;
        accent = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ACCENT, com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.ui.colors.ColorPreferenceHelper.DEFAULT_ACCENT));
        int iconSkin;
        iconSkin = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ICON_SKIN, com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.ui.colors.ColorPreferenceHelper.DEFAULT_ICON_SKIN));
        return new com.amaze.filemanager.ui.colors.UserColorPreferences(tabOne, tabTwo, accent, iconSkin);
    }


    /**
     * The old system used indexes, from here on in this file a correction is made so that the indexes
     * are converted into ColorInts
     */
    private boolean isUsingOldColorsSystem(android.content.SharedPreferences prefs) {
        int tabOne;
        tabOne = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN, com.amaze.filemanager.R.color.primary_indigo);
        int tabTwo;
        tabTwo = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN_TWO, com.amaze.filemanager.R.color.primary_indigo);
        int accent;
        accent = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ACCENT, com.amaze.filemanager.R.color.primary_pink);
        int iconSkin;
        iconSkin = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ICON_SKIN, com.amaze.filemanager.R.color.primary_pink);
        boolean r1;
        r1 = (((tabOne >= 0) && (tabTwo >= 0)) && (accent >= 0)) && (iconSkin >= 0);
        boolean r2;
        r2 = (((tabOne < 22) && (tabTwo < 22)) && (accent < 22)) && (iconSkin < 22);
        return r1 && r2;
    }


    private static final java.util.List<java.lang.Integer> OLD_SYSTEM_LIST = java.util.Arrays.asList(com.amaze.filemanager.R.color.primary_red, com.amaze.filemanager.R.color.primary_pink, com.amaze.filemanager.R.color.primary_purple, com.amaze.filemanager.R.color.primary_deep_purple, com.amaze.filemanager.R.color.primary_indigo, com.amaze.filemanager.R.color.primary_blue, com.amaze.filemanager.R.color.primary_light_blue, com.amaze.filemanager.R.color.primary_cyan, com.amaze.filemanager.R.color.primary_teal, com.amaze.filemanager.R.color.primary_green, com.amaze.filemanager.R.color.primary_light_green, com.amaze.filemanager.R.color.primary_amber, com.amaze.filemanager.R.color.primary_orange, com.amaze.filemanager.R.color.primary_deep_orange, com.amaze.filemanager.R.color.primary_brown, com.amaze.filemanager.R.color.primary_grey_900, com.amaze.filemanager.R.color.primary_blue_grey, com.amaze.filemanager.R.color.primary_teal_900, com.amaze.filemanager.R.color.accent_pink, com.amaze.filemanager.R.color.accent_amber, com.amaze.filemanager.R.color.accent_light_blue, com.amaze.filemanager.R.color.accent_light_green);

    private void correctToNewColorsSystem(android.content.Context c, android.content.SharedPreferences prefs) {
        int tabOne;
        tabOne = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN, -1);
        int tabTwo;
        tabTwo = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN_TWO, -1);
        int accent;
        accent = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ACCENT, -1);
        int iconSkin;
        iconSkin = prefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ICON_SKIN, -1);
        android.content.SharedPreferences.Editor editor;
        editor = prefs.edit();
        editor.putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN, correctForIndex(c, tabOne));
        editor.putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN_TWO, correctForIndex(c, tabTwo));
        editor.putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ACCENT, correctForIndex(c, accent));
        editor.putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ICON_SKIN, correctForIndex(c, iconSkin));
        editor.apply();
    }


    @androidx.annotation.ColorInt
    private int correctForIndex(android.content.Context c, int color) {
        if (color != (-1))
            return com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.ui.colors.ColorPreferenceHelper.OLD_SYSTEM_LIST.get(color));
        else
            return com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.R.color.primary_indigo);

    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
