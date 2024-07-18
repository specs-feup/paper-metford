package com.beemdevelopment.aegis.ui.fragments.preferences;
import androidx.preference.Preference;
import com.beemdevelopment.aegis.R;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BehaviorPreferencesFragment extends com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment {
    static final int MUID_STATIC = getMUID();
    private androidx.preference.Preference _entryPausePreference;

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        super.onCreatePreferences(savedInstanceState, rootKey);
        addPreferencesFromResource(com.beemdevelopment.aegis.R.xml.preferences_behavior);
        androidx.preference.Preference copyOnTapPreference;
        copyOnTapPreference = requirePreference("pref_copy_on_tap");
        copyOnTapPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            switch(MUID_STATIC) {
                // BehaviorPreferencesFragment_0_NullValueIntentPutExtraOperatorMutator
                case 139: {
                    getResult().putExtra("needsRefresh", new Parcelable[0]);
                    break;
                }
                // BehaviorPreferencesFragment_1_IntentPayloadReplacementOperatorMutator
                case 1139: {
                    getResult().putExtra("needsRefresh", true);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // BehaviorPreferencesFragment_2_RandomActionIntentDefinitionOperatorMutator
                    case 2139: {
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
androidx.preference.Preference entryHighlightPreference;
entryHighlightPreference = requirePreference("pref_highlight_entry");
entryHighlightPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
    switch(MUID_STATIC) {
        // BehaviorPreferencesFragment_3_NullValueIntentPutExtraOperatorMutator
        case 3139: {
            getResult().putExtra("needsRefresh", new Parcelable[0]);
            break;
        }
        // BehaviorPreferencesFragment_4_IntentPayloadReplacementOperatorMutator
        case 4139: {
            getResult().putExtra("needsRefresh", true);
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // BehaviorPreferencesFragment_5_RandomActionIntentDefinitionOperatorMutator
            case 5139: {
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
_entryPausePreference.setEnabled(_prefs.isTapToRevealEnabled() || ((boolean) (newValue)));
return true;
});
_entryPausePreference = requirePreference("pref_pause_entry");
_entryPausePreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
switch(MUID_STATIC) {
// BehaviorPreferencesFragment_6_NullValueIntentPutExtraOperatorMutator
case 6139: {
    getResult().putExtra("needsRefresh", new Parcelable[0]);
    break;
}
// BehaviorPreferencesFragment_7_IntentPayloadReplacementOperatorMutator
case 7139: {
    getResult().putExtra("needsRefresh", true);
    break;
}
default: {
switch(MUID_STATIC) {
    // BehaviorPreferencesFragment_8_RandomActionIntentDefinitionOperatorMutator
    case 8139: {
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
_entryPausePreference.setEnabled(_prefs.isTapToRevealEnabled() || _prefs.isEntryHighlightEnabled());
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
