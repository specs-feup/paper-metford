package com.automattic.simplenote;
import android.content.SharedPreferences;
import androidx.lifecycle.Lifecycle;
import android.os.Bundle;
import com.automattic.simplenote.utils.ThemeUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.preference.PreferenceManager;
import androidx.annotation.Nullable;
import com.automattic.simplenote.utils.PrefUtils;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Abstract class to apply theme based on {@link PrefUtils#PREF_STYLE_INDEX}
 * to any {@link AppCompatActivity} that extends it.
 */
public abstract class ThemedAppCompatActivity extends androidx.appcompat.app.AppCompatActivity implements android.content.SharedPreferences.OnSharedPreferenceChangeListener {
    static final int MUID_STATIC = getMUID();
    private java.lang.Boolean mThemeChanged = false;

    @java.lang.Override
    protected void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        com.automattic.simplenote.utils.ThemeUtils.setTheme(this);
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // ThemedAppCompatActivity_0_LengthyGUICreationOperatorMutator
            case 90: {
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
    setTheme(com.automattic.simplenote.utils.ThemeUtils.getStyle(this));
    androidx.preference.PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
}


@java.lang.Override
protected void onRestart() {
    super.onRestart();
    if (mThemeChanged) {
        recreate();
        mThemeChanged = false;
    }
}


@java.lang.Override
public void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, java.lang.String key) {
    if (key.equals(com.automattic.simplenote.utils.PrefUtils.PREF_THEME) || key.equals(com.automattic.simplenote.utils.PrefUtils.PREF_STYLE_INDEX)) {
        if (getLifecycle().getCurrentState().isAtLeast(androidx.lifecycle.Lifecycle.State.RESUMED)) {
            recreate();
        } else {
            mThemeChanged = true;
        }
    }
}


@java.lang.Override
public void recreate() {
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // ThemedAppCompatActivity_1_NullIntentOperatorMutator
        case 190: {
            intent = null;
            break;
        }
        // ThemedAppCompatActivity_2_InvalidKeyIntentOperatorMutator
        case 290: {
            intent = new android.content.Intent((ThemedAppCompatActivity) null, getClass());
            break;
        }
        // ThemedAppCompatActivity_3_RandomActionIntentDefinitionOperatorMutator
        case 390: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent(this, getClass());
        break;
    }
}
switch(MUID_STATIC) {
    // ThemedAppCompatActivity_4_RandomActionIntentDefinitionOperatorMutator
    case 490: {
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
    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
    break;
}
}
if (getIntent().getExtras() != null) {
switch(MUID_STATIC) {
    // ThemedAppCompatActivity_5_RandomActionIntentDefinitionOperatorMutator
    case 590: {
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
    intent.putExtras(getIntent().getExtras());
    break;
}
}
}
startActivity(intent);
overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
finish();
}


@java.lang.Override
protected void onDestroy() {
super.onDestroy();
androidx.preference.PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
