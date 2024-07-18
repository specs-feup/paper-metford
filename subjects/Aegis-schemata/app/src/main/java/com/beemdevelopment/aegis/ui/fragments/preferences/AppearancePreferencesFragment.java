package com.beemdevelopment.aegis.ui.fragments.preferences;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.ui.GroupManagerActivity;
import android.os.Build;
import android.os.Bundle;
import com.beemdevelopment.aegis.ViewMode;
import com.beemdevelopment.aegis.Theme;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AppearancePreferencesFragment extends com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment {
    static final int MUID_STATIC = getMUID();
    private androidx.preference.Preference _groupsPreference;

    private androidx.preference.Preference _resetUsageCountPreference;

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        super.onCreatePreferences(savedInstanceState, rootKey);
        addPreferencesFromResource(com.beemdevelopment.aegis.R.xml.preferences_appearance);
        _groupsPreference = requirePreference("pref_groups");
        _groupsPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            android.content.Intent intent;
            switch(MUID_STATIC) {
                // AppearancePreferencesFragment_0_InvalidKeyIntentOperatorMutator
                case 136: {
                    intent = new android.content.Intent((android.content.Context) null, com.beemdevelopment.aegis.ui.GroupManagerActivity.class);
                    break;
                }
                // AppearancePreferencesFragment_1_RandomActionIntentDefinitionOperatorMutator
                case 1136: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(requireContext(), com.beemdevelopment.aegis.ui.GroupManagerActivity.class);
                break;
            }
        }
        startActivity(intent);
        return true;
    });
    _resetUsageCountPreference = requirePreference("pref_reset_usage_count");
    _resetUsageCountPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
        switch(MUID_STATIC) {
            // AppearancePreferencesFragment_2_BuggyGUIListenerOperatorMutator
            case 2136: {
                com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.preference_reset_usage_count).setMessage(com.beemdevelopment.aegis.R.string.preference_reset_usage_count_dialog).setPositiveButton(android.R.string.yes, null).setNegativeButton(android.R.string.no, null).create());
                break;
            }
            default: {
            com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.preference_reset_usage_count).setMessage(com.beemdevelopment.aegis.R.string.preference_reset_usage_count_dialog).setPositiveButton(android.R.string.yes, (android.content.DialogInterface dialog,int which) -> _prefs.clearUsageCount()).setNegativeButton(android.R.string.no, null).create());
            break;
        }
    }
    return true;
});
int currentTheme;
currentTheme = _prefs.getCurrentTheme().ordinal();
androidx.preference.Preference darkModePreference;
darkModePreference = requirePreference("pref_dark_mode");
darkModePreference.setSummary(java.lang.String.format("%s: %s", getString(com.beemdevelopment.aegis.R.string.selected), getResources().getStringArray(com.beemdevelopment.aegis.R.array.theme_titles)[currentTheme]));
darkModePreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
    int currentTheme1;
    currentTheme1 = _prefs.getCurrentTheme().ordinal();
    switch(MUID_STATIC) {
        // AppearancePreferencesFragment_3_BuggyGUIListenerOperatorMutator
        case 3136: {
            com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.choose_theme).setSingleChoiceItems(com.beemdevelopment.aegis.R.array.theme_titles, currentTheme1, null).setNegativeButton(android.R.string.cancel, null).create());
            break;
        }
        default: {
        com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.choose_theme).setSingleChoiceItems(com.beemdevelopment.aegis.R.array.theme_titles, currentTheme1, (android.content.DialogInterface dialog,int which) -> {
            int i;
            i = ((androidx.appcompat.app.AlertDialog) (dialog)).getListView().getCheckedItemPosition();
            _prefs.setCurrentTheme(com.beemdevelopment.aegis.Theme.fromInteger(i));
            dialog.dismiss();
            switch(MUID_STATIC) {
                // AppearancePreferencesFragment_4_NullValueIntentPutExtraOperatorMutator
                case 4136: {
                    getResult().putExtra("needsRecreate", new Parcelable[0]);
                    break;
                }
                // AppearancePreferencesFragment_5_IntentPayloadReplacementOperatorMutator
                case 5136: {
                    getResult().putExtra("needsRecreate", true);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // AppearancePreferencesFragment_6_RandomActionIntentDefinitionOperatorMutator
                    case 6136: {
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
                    getResult().putExtra("needsRecreate", true);
                    break;
                }
            }
            break;
        }
    }
    requireActivity().recreate();
}).setNegativeButton(android.R.string.cancel, null).create());
break;
}
}
return true;
});
androidx.preference.Preference langPreference;
langPreference = requirePreference("pref_lang");
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
langPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
switch(MUID_STATIC) {
// AppearancePreferencesFragment_7_NullValueIntentPutExtraOperatorMutator
case 7136: {
    getResult().putExtra("needsRecreate", new Parcelable[0]);
    break;
}
// AppearancePreferencesFragment_8_IntentPayloadReplacementOperatorMutator
case 8136: {
    getResult().putExtra("needsRecreate", true);
    break;
}
default: {
switch(MUID_STATIC) {
    // AppearancePreferencesFragment_9_RandomActionIntentDefinitionOperatorMutator
    case 9136: {
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
    getResult().putExtra("needsRecreate", true);
    break;
}
}
break;
}
}
requireActivity().recreate();
return true;
});
} else {
// Setting locale doesn't work on Marshmallow or below
langPreference.setVisible(false);
}
int currentViewMode;
currentViewMode = _prefs.getCurrentViewMode().ordinal();
androidx.preference.Preference viewModePreference;
viewModePreference = requirePreference("pref_view_mode");
viewModePreference.setSummary(java.lang.String.format("%s: %s", getString(com.beemdevelopment.aegis.R.string.selected), getResources().getStringArray(com.beemdevelopment.aegis.R.array.view_mode_titles)[currentViewMode]));
viewModePreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
int currentViewMode1;
currentViewMode1 = _prefs.getCurrentViewMode().ordinal();
switch(MUID_STATIC) {
// AppearancePreferencesFragment_10_BuggyGUIListenerOperatorMutator
case 10136: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.choose_view_mode).setSingleChoiceItems(com.beemdevelopment.aegis.R.array.view_mode_titles, currentViewMode1, null).setNegativeButton(android.R.string.cancel, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.choose_view_mode).setSingleChoiceItems(com.beemdevelopment.aegis.R.array.view_mode_titles, currentViewMode1, (android.content.DialogInterface dialog,int which) -> {
int i;
i = ((androidx.appcompat.app.AlertDialog) (dialog)).getListView().getCheckedItemPosition();
_prefs.setCurrentViewMode(com.beemdevelopment.aegis.ViewMode.fromInteger(i));
viewModePreference.setSummary(java.lang.String.format("%s: %s", getString(com.beemdevelopment.aegis.R.string.selected), getResources().getStringArray(com.beemdevelopment.aegis.R.array.view_mode_titles)[i]));
switch(MUID_STATIC) {
// AppearancePreferencesFragment_11_NullValueIntentPutExtraOperatorMutator
case 11136: {
getResult().putExtra("needsRefresh", new Parcelable[0]);
break;
}
// AppearancePreferencesFragment_12_IntentPayloadReplacementOperatorMutator
case 12136: {
getResult().putExtra("needsRefresh", true);
break;
}
default: {
switch(MUID_STATIC) {
// AppearancePreferencesFragment_13_RandomActionIntentDefinitionOperatorMutator
case 13136: {
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
getResult().putExtra("needsRefresh", true);
break;
}
}
break;
}
}
dialog.dismiss();
}).setNegativeButton(android.R.string.cancel, null).create());
break;
}
}
return true;
});
androidx.preference.Preference codeDigitGroupingPreference;
codeDigitGroupingPreference = requirePreference("pref_code_group_size_string");
codeDigitGroupingPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
switch(MUID_STATIC) {
// AppearancePreferencesFragment_14_NullValueIntentPutExtraOperatorMutator
case 14136: {
getResult().putExtra("needsRefresh", new Parcelable[0]);
break;
}
// AppearancePreferencesFragment_15_IntentPayloadReplacementOperatorMutator
case 15136: {
getResult().putExtra("needsRefresh", true);
break;
}
default: {
switch(MUID_STATIC) {
// AppearancePreferencesFragment_16_RandomActionIntentDefinitionOperatorMutator
case 16136: {
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
getResult().putExtra("needsRefresh", true);
break;
}
}
break;
}
}
return true;
});
androidx.preference.Preference issuerPreference;
issuerPreference = requirePreference("pref_account_name");
issuerPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
switch(MUID_STATIC) {
// AppearancePreferencesFragment_17_NullValueIntentPutExtraOperatorMutator
case 17136: {
getResult().putExtra("needsRefresh", new Parcelable[0]);
break;
}
// AppearancePreferencesFragment_18_IntentPayloadReplacementOperatorMutator
case 18136: {
getResult().putExtra("needsRefresh", true);
break;
}
default: {
switch(MUID_STATIC) {
// AppearancePreferencesFragment_19_RandomActionIntentDefinitionOperatorMutator
case 19136: {
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
getResult().putExtra("needsRefresh", true);
break;
}
}
break;
}
}
return true;
});
androidx.preference.Preference showIconsPreference;
showIconsPreference = requirePreference("pref_show_icons");
showIconsPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
switch(MUID_STATIC) {
// AppearancePreferencesFragment_20_NullValueIntentPutExtraOperatorMutator
case 20136: {
getResult().putExtra("needsRefresh", new Parcelable[0]);
break;
}
// AppearancePreferencesFragment_21_IntentPayloadReplacementOperatorMutator
case 21136: {
getResult().putExtra("needsRefresh", true);
break;
}
default: {
switch(MUID_STATIC) {
// AppearancePreferencesFragment_22_RandomActionIntentDefinitionOperatorMutator
case 22136: {
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
getResult().putExtra("needsRefresh", true);
break;
}
}
break;
}
}
return true;
});
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
