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
package com.amaze.filemanager.ui.dialogs;
import androidx.preference.PreferenceDialogFragmentCompat;
import android.content.res.ColorStateList;
import com.amaze.filemanager.R;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.os.Build;
import androidx.annotation.StringRes;
import com.amaze.filemanager.ui.theme.AppTheme;
import androidx.core.util.Pair;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.content.SharedPreferences;
import com.amaze.filemanager.ui.views.CircularColorsView;
import android.os.Parcel;
import com.amaze.filemanager.application.AppConfig;
import android.os.Bundle;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.widget.RadioButton;
import com.amaze.filemanager.ui.colors.UserColorPreferences;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.preference.PreferenceManager;
import android.os.Parcelable;
import android.view.View;
import android.view.LayoutInflater;
import com.amaze.filemanager.utils.Utils;
import android.content.res.Resources;
import androidx.preference.Preference.BaseSavedState;
import com.afollestad.materialdialogs.Theme;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * This is only the dialog, that shows a list of color combinations and a customization and random
 * one.
 *
 * @author Emmanuel on 11/10/2017, at 12:48.
 */
public class ColorPickerDialog extends androidx.preference.PreferenceDialogFragmentCompat {
    static final int MUID_STATIC = getMUID();
    public static final int DEFAULT = 0;

    public static final int NO_DATA = -1;

    public static final int CUSTOM_INDEX = -2;

    public static final int RANDOM_INDEX = -3;

    /**
     * ONLY add new elements to the end of the array
     */
    private static final com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ColorItemPair[] COLORS = new com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ColorItemPair[]{ new com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ColorItemPair(com.amaze.filemanager.R.string.default_string, new int[]{ com.amaze.filemanager.R.color.primary_indigo, com.amaze.filemanager.R.color.primary_indigo, com.amaze.filemanager.R.color.primary_pink, com.amaze.filemanager.R.color.accent_pink }), new com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ColorItemPair(com.amaze.filemanager.R.string.orange, new int[]{ com.amaze.filemanager.R.color.primary_orange, com.amaze.filemanager.R.color.primary_orange, com.amaze.filemanager.R.color.primary_deep_orange, com.amaze.filemanager.R.color.accent_amber }), new com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ColorItemPair(com.amaze.filemanager.R.string.blue, new int[]{ com.amaze.filemanager.R.color.primary_blue, com.amaze.filemanager.R.color.primary_blue, com.amaze.filemanager.R.color.primary_deep_purple, com.amaze.filemanager.R.color.accent_light_blue }), new com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ColorItemPair(com.amaze.filemanager.R.string.green, new int[]{ com.amaze.filemanager.R.color.primary_green, com.amaze.filemanager.R.color.primary_green, com.amaze.filemanager.R.color.primary_teal_900, com.amaze.filemanager.R.color.accent_light_green }) };

    private static final java.lang.String ARG_COLOR_PREF = "colorPref";

    private static final java.lang.String ARG_APP_THEME = "appTheme";

    private android.content.SharedPreferences sharedPrefs;

    private com.amaze.filemanager.ui.dialogs.ColorPickerDialog.OnAcceptedConfig listener;

    private android.view.View selectedItem = null;

    private int selectedIndex = -1;

    public static com.amaze.filemanager.ui.dialogs.ColorPickerDialog newInstance(java.lang.String key, com.amaze.filemanager.ui.colors.UserColorPreferences color, com.amaze.filemanager.ui.theme.AppTheme theme) {
        com.amaze.filemanager.ui.dialogs.ColorPickerDialog retval;
        retval = new com.amaze.filemanager.ui.dialogs.ColorPickerDialog();
        final android.os.Bundle b;
        b = new android.os.Bundle(2);
        b.putString(androidx.preference.PreferenceDialogFragmentCompat.ARG_KEY, key);
        b.putParcelable(com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ARG_COLOR_PREF, color);
        b.putString(com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ARG_APP_THEME, theme.toString());
        retval.setArguments(b);
        return retval;
    }


    public void setListener(com.amaze.filemanager.ui.dialogs.ColorPickerDialog.OnAcceptedConfig l) {
        listener = l;
    }


    @java.lang.Override
    public void onBindDialogView(android.view.View view) {
        sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext());
        int accentColor;
        accentColor = ((com.amaze.filemanager.ui.colors.UserColorPreferences) (requireArguments().getParcelable(com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ARG_COLOR_PREF))).getAccent();
        if (selectedIndex == com.amaze.filemanager.ui.dialogs.ColorPickerDialog.NO_DATA) {
            // if instance was restored the value is already set
            boolean isUsingDefault;
            isUsingDefault = ((((sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLOR_CONFIG, com.amaze.filemanager.ui.dialogs.ColorPickerDialog.NO_DATA) == com.amaze.filemanager.ui.dialogs.ColorPickerDialog.NO_DATA) && (sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN, com.amaze.filemanager.R.color.primary_indigo) == com.amaze.filemanager.R.color.primary_indigo)) && (sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SKIN_TWO, com.amaze.filemanager.R.color.primary_indigo) == com.amaze.filemanager.R.color.primary_indigo)) && (sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ACCENT, com.amaze.filemanager.R.color.primary_pink) == com.amaze.filemanager.R.color.primary_pink)) && (sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ICON_SKIN, com.amaze.filemanager.R.color.primary_pink) == com.amaze.filemanager.R.color.primary_pink);
            if (isUsingDefault) {
                sharedPrefs.edit().putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLOR_CONFIG, com.amaze.filemanager.ui.dialogs.ColorPickerDialog.DEFAULT).apply();
            }
            if (sharedPrefs.getBoolean("random_checkbox", false)) {
                sharedPrefs.edit().putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLOR_CONFIG, com.amaze.filemanager.ui.dialogs.ColorPickerDialog.RANDOM_INDEX).apply();
            }
            sharedPrefs.edit().remove("random_checkbox").apply();
            selectedIndex = sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLOR_CONFIG, com.amaze.filemanager.ui.dialogs.ColorPickerDialog.CUSTOM_INDEX);
        }
        android.widget.LinearLayout container;
        switch(MUID_STATIC) {
            // ColorPickerDialog_0_InvalidViewFocusOperatorMutator
            case 95: {
                /**
                * Inserted by Kadabra
                */
                container = view.findViewById(com.amaze.filemanager.R.id.container);
                container.requestFocus();
                break;
            }
            // ColorPickerDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1095: {
                /**
                * Inserted by Kadabra
                */
                container = view.findViewById(com.amaze.filemanager.R.id.container);
                container.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            container = view.findViewById(com.amaze.filemanager.R.id.container);
            break;
        }
    }
    for (int i = 0; i < com.amaze.filemanager.ui.dialogs.ColorPickerDialog.COLORS.length; i++) {
        android.view.View child;
        child = inflateItem(container, i, accentColor);
        if (selectedIndex == i) {
            selectedItem = child;
            select(selectedItem, true);
        }
        ((androidx.appcompat.widget.AppCompatTextView) (child.findViewById(com.amaze.filemanager.R.id.text))).setText(com.amaze.filemanager.ui.dialogs.ColorPickerDialog.COLORS[i].first);
        com.amaze.filemanager.ui.views.CircularColorsView colorsView;
        switch(MUID_STATIC) {
            // ColorPickerDialog_2_InvalidViewFocusOperatorMutator
            case 2095: {
                /**
                * Inserted by Kadabra
                */
                colorsView = child.findViewById(com.amaze.filemanager.R.id.circularColorsView);
                colorsView.requestFocus();
                break;
            }
            // ColorPickerDialog_3_ViewComponentNotVisibleOperatorMutator
            case 3095: {
                /**
                * Inserted by Kadabra
                */
                colorsView = child.findViewById(com.amaze.filemanager.R.id.circularColorsView);
                colorsView.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            colorsView = child.findViewById(com.amaze.filemanager.R.id.circularColorsView);
            break;
        }
    }
    colorsView.setColors(getColor(i, 0), getColor(i, 1), getColor(i, 2), getColor(i, 3));
    com.amaze.filemanager.ui.theme.AppTheme appTheme;
    appTheme = com.amaze.filemanager.ui.theme.AppTheme.valueOf(requireArguments().getString(com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ARG_APP_THEME));
    if (appTheme.getMaterialDialogTheme() == com.afollestad.materialdialogs.Theme.LIGHT)
        colorsView.setDividerColor(android.graphics.Color.WHITE);
    else
        colorsView.setDividerColor(android.graphics.Color.BLACK);

    container.addView(child);
}
{
    android.view.View child;
    child = inflateItem(container, com.amaze.filemanager.ui.dialogs.ColorPickerDialog.CUSTOM_INDEX, accentColor);
    if (selectedIndex == com.amaze.filemanager.ui.dialogs.ColorPickerDialog.CUSTOM_INDEX) {
        selectedItem = child;
        select(selectedItem, true);
    }
    ((androidx.appcompat.widget.AppCompatTextView) (child.findViewById(com.amaze.filemanager.R.id.text))).setText(com.amaze.filemanager.R.string.custom);
    child.findViewById(com.amaze.filemanager.R.id.circularColorsView).setVisibility(android.view.View.INVISIBLE);
    container.addView(child);
}
{
    android.view.View child;
    child = inflateItem(container, com.amaze.filemanager.ui.dialogs.ColorPickerDialog.RANDOM_INDEX, accentColor);
    if (selectedIndex == com.amaze.filemanager.ui.dialogs.ColorPickerDialog.RANDOM_INDEX) {
        selectedItem = child;
        select(selectedItem, true);
    }
    ((androidx.appcompat.widget.AppCompatTextView) (child.findViewById(com.amaze.filemanager.R.id.text))).setText(com.amaze.filemanager.R.string.random);
    child.findViewById(com.amaze.filemanager.R.id.circularColorsView).setVisibility(android.view.View.INVISIBLE);
    container.addView(child);
}
}


private void select(android.view.View listChild, boolean checked) {
android.widget.RadioButton button;
switch(MUID_STATIC) {
    // ColorPickerDialog_4_InvalidViewFocusOperatorMutator
    case 4095: {
        /**
        * Inserted by Kadabra
        */
        button = listChild.findViewById(com.amaze.filemanager.R.id.select);
        button.requestFocus();
        break;
    }
    // ColorPickerDialog_5_ViewComponentNotVisibleOperatorMutator
    case 5095: {
        /**
        * Inserted by Kadabra
        */
        button = listChild.findViewById(com.amaze.filemanager.R.id.select);
        button.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    button = listChild.findViewById(com.amaze.filemanager.R.id.select);
    break;
}
}
button.setChecked(checked);
}


private android.view.View inflateItem(android.widget.LinearLayout container, final int index, int accentColor) {
android.view.View.OnClickListener clickListener;
switch(MUID_STATIC) {
// ColorPickerDialog_6_BuggyGUIListenerOperatorMutator
case 6095: {
    clickListener = null;
    break;
}
default: {
clickListener = (android.view.View v) -> {
    if (!v.isSelected()) {
        select(selectedItem, false);
        select(v, true);
        selectedItem = v;
        selectedIndex = index;
    }
};
break;
}
}
android.view.LayoutInflater inflater;
inflater = ((android.view.LayoutInflater) (requireContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
android.view.View child;
child = inflater.inflate(com.amaze.filemanager.R.layout.item_colorpicker, container, false);
child.setOnClickListener(clickListener);
android.widget.RadioButton radio;
switch(MUID_STATIC) {
// ColorPickerDialog_7_InvalidViewFocusOperatorMutator
case 7095: {
/**
* Inserted by Kadabra
*/
radio = child.findViewById(com.amaze.filemanager.R.id.select);
radio.requestFocus();
break;
}
// ColorPickerDialog_8_ViewComponentNotVisibleOperatorMutator
case 8095: {
/**
* Inserted by Kadabra
*/
radio = child.findViewById(com.amaze.filemanager.R.id.select);
radio.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
radio = child.findViewById(com.amaze.filemanager.R.id.select);
break;
}
}
radio.setOnClickListener(clickListener);
if (android.os.Build.VERSION.SDK_INT >= 21) {
android.content.res.ColorStateList colorStateList;
colorStateList = new android.content.res.ColorStateList(new int[][]{ new int[]{ -android.R.attr.state_enabled }// disabled
// disabled
// disabled
, new int[]{ android.R.attr.state_enabled }// enabled
// enabled
// enabled
 }, new int[]{ accentColor, accentColor });
radio.setButtonTintList(colorStateList);
}
return child;
}


@androidx.annotation.NonNull
@java.lang.Override
public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
android.app.Dialog dialog;
dialog = super.onCreateDialog(savedInstanceState);
dialog.show();
android.content.res.Resources res;
res = requireContext().getResources();
int accentColor;
accentColor = ((com.amaze.filemanager.ui.colors.UserColorPreferences) (requireArguments().getParcelable(com.amaze.filemanager.ui.dialogs.ColorPickerDialog.ARG_COLOR_PREF))).getAccent();
// Button views
((androidx.appcompat.widget.AppCompatButton) (dialog.findViewById(res.getIdentifier("button1", "id", "android")))).setTextColor(accentColor);
((androidx.appcompat.widget.AppCompatButton) (dialog.findViewById(res.getIdentifier("button2", "id", "android")))).setTextColor(accentColor);
return dialog;
}


@java.lang.Override
public void onDialogClosed(boolean positiveResult) {
// When the user selects "OK", persist the new value
if (positiveResult) {
sharedPrefs.edit().putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLOR_CONFIG, selectedIndex).apply();
if ((selectedIndex != com.amaze.filemanager.ui.dialogs.ColorPickerDialog.CUSTOM_INDEX) && (selectedIndex != com.amaze.filemanager.ui.dialogs.ColorPickerDialog.RANDOM_INDEX)) {
com.amaze.filemanager.application.AppConfig.getInstance().getUtilsProvider().getColorPreference().saveColorPreferences(sharedPrefs, new com.amaze.filemanager.ui.colors.UserColorPreferences(getColor(selectedIndex, 0), getColor(selectedIndex, 1), getColor(selectedIndex, 2), getColor(selectedIndex, 3)));
}
listener.onAcceptedConfig();
} else {
selectedIndex = sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLOR_CONFIG, com.amaze.filemanager.ui.dialogs.ColorPickerDialog.NO_DATA);
}
}


@androidx.annotation.StringRes
public static int getTitle(int index) {
if (index == com.amaze.filemanager.ui.dialogs.ColorPickerDialog.RANDOM_INDEX) {
return com.amaze.filemanager.R.string.random;
} else if (index == com.amaze.filemanager.ui.dialogs.ColorPickerDialog.CUSTOM_INDEX) {
return com.amaze.filemanager.R.string.custom;
} else if ((index >= 0) && (index < com.amaze.filemanager.ui.dialogs.ColorPickerDialog.COLORS.length)) {
return com.amaze.filemanager.ui.dialogs.ColorPickerDialog.COLORS[index].first;
} else {
return com.amaze.filemanager.ui.dialogs.ColorPickerDialog.COLORS[0].first;
}
}


private int getColor(int i, int pos) {
return com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.ui.dialogs.ColorPickerDialog.COLORS[i].second[pos]);
}


/**
 * typedef Pair<int, int[]> ColorItemPair
 */
private static class ColorItemPair extends androidx.core.util.Pair<java.lang.Integer, int[]> {
/**
 * Constructor for a Pair.
 *
 * @param first
 * 		the first object in the Pair
 * @param second
 * 		the second object in the pair
 */
public ColorItemPair(java.lang.Integer first, int[] second) {
super(first, second);
}

}

public interface OnAcceptedConfig {
void onAcceptedConfig();

}

public static class SavedState extends androidx.preference.Preference.BaseSavedState {
public int selectedItem;

public SavedState(android.os.Parcel source) {
super(source);
selectedItem = source.readInt();
}


@java.lang.Override
public void writeToParcel(android.os.Parcel dest, int flags) {
super.writeToParcel(dest, flags);
dest.writeInt(selectedItem);
}


public SavedState(android.os.Parcelable superState) {
super(superState);
}


public static final android.os.Parcelable.Creator<com.amaze.filemanager.ui.dialogs.ColorPickerDialog.SavedState> CREATOR = new android.os.Parcelable.Creator<com.amaze.filemanager.ui.dialogs.ColorPickerDialog.SavedState>() {
public com.amaze.filemanager.ui.dialogs.ColorPickerDialog.SavedState createFromParcel(android.os.Parcel in) {
return new com.amaze.filemanager.ui.dialogs.ColorPickerDialog.SavedState(in);
}


public com.amaze.filemanager.ui.dialogs.ColorPickerDialog.SavedState[] newArray(int size) {
return new com.amaze.filemanager.ui.dialogs.ColorPickerDialog.SavedState[size];
}

};
}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
