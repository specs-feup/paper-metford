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
package com.amaze.filemanager.ui.activities.superclasses;
import android.content.res.Configuration;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import android.view.WindowManager;
import android.app.ActivityManager;
import static android.os.Build.VERSION.SDK_INT;
import android.os.PowerManager;
import com.amaze.filemanager.R;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import android.os.Build;
import androidx.appcompat.widget.Toolbar;
import com.amaze.filemanager.ui.theme.AppTheme;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLORED_NAVIGATION;
import com.amaze.filemanager.ui.colors.ColorPreferenceHelper;
import android.content.IntentFilter;
import android.view.Window;
import android.content.SharedPreferences;
import com.amaze.filemanager.ui.theme.AppThemePreference;
import android.os.Bundle;
import android.view.ViewGroup;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.graphics.drawable.BitmapDrawable;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import com.amaze.filemanager.ui.colors.UserColorPreferences;
import android.content.Intent;
import com.amaze.filemanager.ui.dialogs.ColorPickerDialog;
import androidx.preference.PreferenceManager;
import android.content.BroadcastReceiver;
import android.view.View;
import com.amaze.filemanager.utils.PreferenceUtils;
import androidx.annotation.ColorInt;
import com.amaze.filemanager.utils.Utils;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by arpitkh996 on 03-03-2016.
 */
public class ThemedActivity extends com.amaze.filemanager.ui.activities.superclasses.PreferenceActivity {
    static final int MUID_STATIC = getMUID();
    private int uiModeNight = -1;

    /**
     * BroadcastReceiver responsible for updating the theme if battery saver mode is turned on or off
     */
    private final android.content.BroadcastReceiver powerModeReceiver = new android.content.BroadcastReceiver() {
        @java.lang.Override
        public void onReceive(android.content.Context context, android.content.Intent i) {
            android.content.SharedPreferences preferences;
            preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
            boolean followBatterySaver;
            followBatterySaver = preferences.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.FRAGMENT_FOLLOW_BATTERY_SAVER, false);
            com.amaze.filemanager.ui.theme.AppThemePreference theme;
            theme = com.amaze.filemanager.ui.theme.AppThemePreference.getTheme(java.lang.Integer.parseInt(preferences.getString(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.FRAGMENT_THEME, "4")));
            if (followBatterySaver && theme.getCanBeLight()) {
                recreate();
            }
        }

    };

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // ThemedActivity_0_LengthyGUICreationOperatorMutator
            case 105: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    registerPowerModeReceiver();
    // setting window background color instead of each item, in order to reduce pixel overdraw
    if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
        getWindow().setBackgroundDrawableResource(android.R.color.white);
    } else if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
        getWindow().setBackgroundDrawableResource(android.R.color.black);
    } else {
        getWindow().setBackgroundDrawableResource(com.amaze.filemanager.R.color.holo_dark_background);
    }
    // checking if theme should be set light/dark or automatic
    int colorPickerPref;
    colorPickerPref = getPrefs().getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLOR_CONFIG, com.amaze.filemanager.ui.dialogs.ColorPickerDialog.NO_DATA);
    if (colorPickerPref == com.amaze.filemanager.ui.dialogs.ColorPickerDialog.RANDOM_INDEX) {
        getColorPreference().saveColorPreferences(getPrefs(), com.amaze.filemanager.ui.colors.ColorPreferenceHelper.randomize(this));
    }
    if (android.os.Build.VERSION.SDK_INT >= 21) {
        android.app.ActivityManager.TaskDescription taskDescription;
        taskDescription = new android.app.ActivityManager.TaskDescription(getString(com.amaze.filemanager.R.string.appbar_name), ((android.graphics.drawable.BitmapDrawable) (androidx.core.content.ContextCompat.getDrawable(this, com.amaze.filemanager.R.mipmap.ic_launcher))).getBitmap(), getPrimary());
        setTaskDescription(taskDescription);
    }
    setTheme();
}


/**
 * Set status bar and navigation bar colors based on sdk
 *
 * @param parentView
 * 		parent view required to set margin on kitkat top
 */
public void initStatusBarResources(android.view.View parentView) {
    if (getToolbar() != null) {
        getToolbar().setBackgroundColor(getPrimary());
    }
    android.view.Window window;
    window = getWindow();
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        switch(MUID_STATIC) {
            // ThemedActivity_1_FindViewByIdReturnsNullOperatorMutator
            case 1105: {
                if ((null != null) || (findViewById(com.amaze.filemanager.R.id.drawer_layout) == null)) {
                    window.setStatusBarColor(com.amaze.filemanager.utils.PreferenceUtils.getStatusColor(getPrimary()));
                    window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                } else {
                    window.addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
                break;
            }
            // ThemedActivity_2_InvalidIDFindViewOperatorMutator
            case 2105: {
                if ((findViewById(732221) != null) || (findViewById(com.amaze.filemanager.R.id.drawer_layout) == null)) {
                    window.setStatusBarColor(com.amaze.filemanager.utils.PreferenceUtils.getStatusColor(getPrimary()));
                    window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                } else {
                    window.addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // ThemedActivity_3_FindViewByIdReturnsNullOperatorMutator
                case 3105: {
                    if ((findViewById(com.amaze.filemanager.R.id.tab_frame) != null) || (null == null)) {
                        window.setStatusBarColor(com.amaze.filemanager.utils.PreferenceUtils.getStatusColor(getPrimary()));
                        window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    } else {
                        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                    break;
                }
                // ThemedActivity_4_InvalidIDFindViewOperatorMutator
                case 4105: {
                    if ((findViewById(com.amaze.filemanager.R.id.tab_frame) != null) || (findViewById(732221) == null)) {
                        window.setStatusBarColor(com.amaze.filemanager.utils.PreferenceUtils.getStatusColor(getPrimary()));
                        window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    } else {
                        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }
                    break;
                }
                default: {
                if ((findViewById(com.amaze.filemanager.R.id.tab_frame) != null) || (findViewById(com.amaze.filemanager.R.id.drawer_layout) == null)) {
                    window.setStatusBarColor(com.amaze.filemanager.utils.PreferenceUtils.getStatusColor(getPrimary()));
                    window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                } else {
                    window.addFlags(android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
                break;
            }
        }
        break;
    }
}
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLORED_NAVIGATION)) {
    window.setNavigationBarColor(com.amaze.filemanager.utils.PreferenceUtils.getStatusColor(getPrimary()));
} else if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
    window.setNavigationBarColor(com.amaze.filemanager.utils.Utils.getColor(this, android.R.color.white));
} else if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
    window.setNavigationBarColor(com.amaze.filemanager.utils.Utils.getColor(this, android.R.color.black));
} else {
    window.setNavigationBarColor(com.amaze.filemanager.utils.Utils.getColor(this, com.amaze.filemanager.R.color.holo_dark_background));
}
} else if ((android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.KITKAT_WATCH) || (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.KITKAT)) {
setKitkatStatusBarMargin(parentView);
setKitkatStatusBarTint();
}
}


@java.lang.Override
public void onConfigurationChanged(@androidx.annotation.NonNull
android.content.res.Configuration newConfig) {
super.onConfigurationChanged(newConfig);
final int newUiModeNight;
newUiModeNight = newConfig.uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
// System theme change
if (uiModeNight != newUiModeNight) {
uiModeNight = newUiModeNight;
if (getPrefs().getString(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.FRAGMENT_THEME, "4").equals("4")) {
    getUtilsProvider().getThemeManager().setAppThemePreference(com.amaze.filemanager.ui.theme.AppThemePreference.getTheme(4));
    // Recreate activity, handling saved state
    // 
    // Not smooth, but will only be called if the user changes the system theme, not
    // the app theme.
    recreate();
}
}
}


public com.amaze.filemanager.ui.colors.UserColorPreferences getCurrentColorPreference() {
return getColorPreference().getCurrentUserColorPreferences(this, getPrefs());
}


@androidx.annotation.ColorInt
public int getAccent() {
return getColorPreference().getCurrentUserColorPreferences(this, getPrefs()).getAccent();
}


private void setKitkatStatusBarMargin(android.view.View parentView) {
com.readystatesoftware.systembartint.SystemBarTintManager tintManager;
tintManager = new com.readystatesoftware.systembartint.SystemBarTintManager(this);
tintManager.setStatusBarTintEnabled(true);
// tintManager.setStatusBarTintColor(Color.parseColor((currentTab==1 ? skinTwo : skin)));
android.view.ViewGroup.MarginLayoutParams p;
p = ((android.view.ViewGroup.MarginLayoutParams) (parentView.getLayoutParams()));
com.readystatesoftware.systembartint.SystemBarTintManager.SystemBarConfig config;
config = tintManager.getConfig();
p.setMargins(0, config.getStatusBarHeight(), 0, 0);
}


private void setKitkatStatusBarTint() {
com.readystatesoftware.systembartint.SystemBarTintManager tintManager;
tintManager = new com.readystatesoftware.systembartint.SystemBarTintManager(this);
tintManager.setStatusBarTintEnabled(true);
tintManager.setStatusBarTintColor(getPrimary());
}


@androidx.annotation.ColorInt
public int getPrimary() {
return com.amaze.filemanager.ui.colors.ColorPreferenceHelper.getPrimary(getCurrentColorPreference(), getCurrentTab());
}


@androidx.annotation.Nullable
private androidx.appcompat.widget.Toolbar getToolbar() {
switch(MUID_STATIC) {
// ThemedActivity_5_FindViewByIdReturnsNullOperatorMutator
case 5105: {
    return null;
}
// ThemedActivity_6_InvalidIDFindViewOperatorMutator
case 6105: {
    return findViewById(732221);
}
default: {
return findViewById(com.amaze.filemanager.R.id.toolbar);
}
}
}


void setTheme() {
com.amaze.filemanager.ui.theme.AppTheme theme;
theme = getAppTheme();
if (android.os.Build.VERSION.SDK_INT >= 21) {
java.lang.String stringRepresentation;
stringRepresentation = java.lang.String.format("#%06X", 0xffffff & getAccent());
switch (stringRepresentation.toUpperCase()) {
case "#F44336" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_red);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_red);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_red);

    break;
case "#E91E63" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_pink);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_pink);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_pink);

    break;
case "#9C27B0" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_purple);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_purple);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_purple);

    break;
case "#673AB7" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_deep_purple);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_deep_purple);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_deep_purple);

    break;
case "#3F51B5" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_indigo);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_indigo);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_indigo);

    break;
case "#2196F3" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_blue);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_blue);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_blue);

    break;
case "#03A9F4" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_light_blue);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_light_blue);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_light_blue);

    break;
case "#00BCD4" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_cyan);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_cyan);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_cyan);

    break;
case "#009688" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_teal);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_teal);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_teal);

    break;
case "#4CAF50" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_green);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_green);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_green);

    break;
case "#8BC34A" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_light_green);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_light_green);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_light_green);

    break;
case "#FFC107" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_amber);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_amber);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_amber);

    break;
case "#FF9800" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_orange);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_orange);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_orange);

    break;
case "#FF5722" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_deep_orange);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_deep_orange);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_deep_orange);

    break;
case "#795548" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_brown);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_brown);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_brown);

    break;
case "#212121" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_black);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_black);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_black);

    break;
case "#607D8B" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_blue_grey);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_blue_grey);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_blue_grey);

    break;
case "#004D40" :
    if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT))
        setTheme(com.amaze.filemanager.R.style.pref_accent_light_super_su);
    else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
        setTheme(com.amaze.filemanager.R.style.pref_accent_black_super_su);
    else
        setTheme(com.amaze.filemanager.R.style.pref_accent_dark_super_su);

    break;
}
} else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
setTheme(com.amaze.filemanager.R.style.appCompatLight);
} else if (theme.equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
setTheme(com.amaze.filemanager.R.style.appCompatBlack);
} else {
setTheme(com.amaze.filemanager.R.style.appCompatDark);
}
}


@java.lang.Override
protected void onResume() {
super.onResume();
uiModeNight = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
setTheme();
}


@java.lang.Override
protected void onDestroy() {
super.onDestroy();
unregisterPowerModeReceiver();
}


/**
 * Registers the BroadcastReceiver \`powerModeReceiver\` to listen to broadcasts that the battery
 * save mode has been changed
 */
private void registerPowerModeReceiver() {
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
switch(MUID_STATIC) {
// ThemedActivity_7_RandomActionIntentDefinitionOperatorMutator
case 7105: {
    /**
    * Inserted by Kadabra
    */
    /**
    * Inserted by Kadabra
    */
    new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
registerReceiver(powerModeReceiver, new android.content.IntentFilter(android.os.PowerManager.ACTION_POWER_SAVE_MODE_CHANGED));
break;
}
}
}
}


/**
 * Unregisters the BroadcastReceiver \`powerModeReceiver\`
 */
private void unregisterPowerModeReceiver() {
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
unregisterReceiver(powerModeReceiver);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
