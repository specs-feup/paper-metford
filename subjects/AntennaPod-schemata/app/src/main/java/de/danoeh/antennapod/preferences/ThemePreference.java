package de.danoeh.antennapod.preferences;
import androidx.preference.Preference;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.util.AttributeSet;
import de.danoeh.antennapod.databinding.ThemePreferenceBinding;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceViewHolder;
import android.content.Context;
import com.google.android.material.elevation.SurfaceColors;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ThemePreference extends androidx.preference.Preference {
    static final int MUID_STATIC = getMUID();
    de.danoeh.antennapod.databinding.ThemePreferenceBinding viewBinding;

    public ThemePreference(android.content.Context context) {
        super(context);
        setLayoutResource(de.danoeh.antennapod.R.layout.theme_preference);
    }


    public ThemePreference(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(de.danoeh.antennapod.R.layout.theme_preference);
    }


    @java.lang.Override
    public void onBindViewHolder(androidx.preference.PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        viewBinding = de.danoeh.antennapod.databinding.ThemePreferenceBinding.bind(holder.itemView);
        updateUi();
    }


    void updateThemeCard(androidx.cardview.widget.CardView card, de.danoeh.antennapod.storage.preferences.UserPreferences.ThemePreference theme) {
        float density;
        density = getContext().getResources().getDisplayMetrics().density;
        int surfaceColor;
        switch(MUID_STATIC) {
            // ThemePreference_0_BinaryMutator
            case 74: {
                surfaceColor = com.google.android.material.elevation.SurfaceColors.getColorForElevation(getContext(), 1 / density);
                break;
            }
            default: {
            surfaceColor = com.google.android.material.elevation.SurfaceColors.getColorForElevation(getContext(), 1 * density);
            break;
        }
    }
    int surfaceColorActive;
    switch(MUID_STATIC) {
        // ThemePreference_1_BinaryMutator
        case 1074: {
            surfaceColorActive = com.google.android.material.elevation.SurfaceColors.getColorForElevation(getContext(), 32 / density);
            break;
        }
        default: {
        surfaceColorActive = com.google.android.material.elevation.SurfaceColors.getColorForElevation(getContext(), 32 * density);
        break;
    }
}
de.danoeh.antennapod.storage.preferences.UserPreferences.ThemePreference activeTheme;
activeTheme = de.danoeh.antennapod.storage.preferences.UserPreferences.getTheme();
card.setCardBackgroundColor(theme == activeTheme ? surfaceColorActive : surfaceColor);
switch(MUID_STATIC) {
    // ThemePreference_2_BuggyGUIListenerOperatorMutator
    case 2074: {
        card.setOnClickListener(null);
        break;
    }
    default: {
    card.setOnClickListener((android.view.View v) -> {
        de.danoeh.antennapod.storage.preferences.UserPreferences.setTheme(theme);
        if (getOnPreferenceChangeListener() != null) {
            getOnPreferenceChangeListener().onPreferenceChange(this, de.danoeh.antennapod.storage.preferences.UserPreferences.getTheme());
        }
        updateUi();
    });
    break;
}
}
}


void updateUi() {
updateThemeCard(viewBinding.themeSystemCard, de.danoeh.antennapod.storage.preferences.UserPreferences.ThemePreference.SYSTEM);
updateThemeCard(viewBinding.themeLightCard, de.danoeh.antennapod.storage.preferences.UserPreferences.ThemePreference.LIGHT);
updateThemeCard(viewBinding.themeDarkCard, de.danoeh.antennapod.storage.preferences.UserPreferences.ThemePreference.DARK);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
