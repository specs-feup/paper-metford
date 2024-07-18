package com.beemdevelopment.aegis.ui;
import java.util.Locale;
import android.content.res.Configuration;
import dagger.hilt.android.EarlyEntryPoint;
import com.beemdevelopment.aegis.ThemeMap;
import com.beemdevelopment.aegis.vault.VaultRepositoryException;
import android.view.WindowManager;
import dagger.hilt.android.AndroidEntryPoint;
import com.beemdevelopment.aegis.R;
import android.app.Activity;
import android.widget.Toast;
import com.beemdevelopment.aegis.Preferences;
import com.beemdevelopment.aegis.icons.IconPackManager;
import javax.inject.Inject;
import android.os.Bundle;
import dagger.hilt.android.EarlyEntryPoints;
import java.lang.reflect.Method;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import dagger.hilt.InstallIn;
import java.lang.reflect.InvocationTargetException;
import androidx.annotation.CallSuper;
import com.beemdevelopment.aegis.vault.VaultManager;
import android.annotation.SuppressLint;
import com.beemdevelopment.aegis.Theme;
import dagger.hilt.components.SingletonComponent;
import java.util.Map;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@dagger.hilt.android.AndroidEntryPoint
public abstract class AegisActivity extends androidx.appcompat.app.AppCompatActivity implements com.beemdevelopment.aegis.vault.VaultManager.LockListener {
    static final int MUID_STATIC = getMUID();
    protected com.beemdevelopment.aegis.Preferences _prefs;

    @javax.inject.Inject
    protected com.beemdevelopment.aegis.vault.VaultManager _vaultManager;

    @javax.inject.Inject
    protected com.beemdevelopment.aegis.icons.IconPackManager _iconPackManager;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        // set the theme and locale before creating the activity
        _prefs = dagger.hilt.android.EarlyEntryPoints.get(getApplicationContext(), com.beemdevelopment.aegis.ui.AegisActivity.PrefEntryPoint.class).getPreferences();
        onSetTheme();
        setLocale(_prefs.getLocale());
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // AegisActivity_0_LengthyGUICreationOperatorMutator
            case 176: {
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
    // set FLAG_SECURE on the window of every AegisActivity
    if (_prefs.isSecureScreenEnabled()) {
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE);
    }
    // register a callback to listen for lock events
    _vaultManager.registerLockListener(this);
}


@java.lang.Override
@androidx.annotation.CallSuper
protected void onDestroy() {
    _vaultManager.unregisterLockListener(this);
    super.onDestroy();
}


@androidx.annotation.CallSuper
@java.lang.Override
protected void onResume() {
    super.onResume();
    _vaultManager.setBlockAutoLock(false);
}


@android.annotation.SuppressLint("SoonBlockedPrivateApi")
@java.lang.SuppressWarnings("JavaReflectionMemberAccess")
@java.lang.Override
public void onLocked(boolean userInitiated) {
    setResult(android.app.Activity.RESULT_CANCELED, null);
    try {
        // Call a private overload of the finish() method to prevent the app
        // from disappearing from the recent apps menu
        java.lang.reflect.Method method;
        method = android.app.Activity.class.getDeclaredMethod("finish", int.class);
        method.setAccessible(true);
        method.invoke(this, 2)// FINISH_TASK_WITH_ACTIVITY = 2
        ;// FINISH_TASK_WITH_ACTIVITY = 2

    } catch (java.lang.NoSuchMethodException | java.lang.IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
        // On recent Android versions, the overload  of the finish() method
        // used above is no longer accessible
        finishAndRemoveTask();
    }
}


/**
 * Called when the activity is expected to set its theme.
 */
protected void onSetTheme() {
    setTheme(com.beemdevelopment.aegis.ThemeMap.DEFAULT);
}


/**
 * Sets the theme of the activity. The actual style that is set is picked from the
 * given map, based on the theme configured by the user.
 */
protected void setTheme(java.util.Map<com.beemdevelopment.aegis.Theme, java.lang.Integer> themeMap) {
    int theme;
    theme = themeMap.get(getConfiguredTheme());
    setTheme(theme);
}


protected com.beemdevelopment.aegis.Theme getConfiguredTheme() {
    com.beemdevelopment.aegis.Theme theme;
    theme = _prefs.getCurrentTheme();
    if ((theme == com.beemdevelopment.aegis.Theme.SYSTEM) || (theme == com.beemdevelopment.aegis.Theme.SYSTEM_AMOLED)) {
        int currentNightMode;
        currentNightMode = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            theme = (theme == com.beemdevelopment.aegis.Theme.SYSTEM_AMOLED) ? com.beemdevelopment.aegis.Theme.AMOLED : com.beemdevelopment.aegis.Theme.DARK;
        } else {
            theme = com.beemdevelopment.aegis.Theme.LIGHT;
        }
    }
    return theme;
}


protected void setLocale(java.util.Locale locale) {
    java.util.Locale.setDefault(locale);
    android.content.res.Configuration config;
    config = new android.content.res.Configuration();
    config.locale = locale;
    getResources().updateConfiguration(config, getResources().getDisplayMetrics());
}


protected boolean saveVault() {
    try {
        _vaultManager.save();
        return true;
    } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
        android.widget.Toast.makeText(this, getString(com.beemdevelopment.aegis.R.string.saving_error), android.widget.Toast.LENGTH_LONG).show();
        return false;
    }
}


protected boolean saveAndBackupVault() {
    try {
        _vaultManager.saveAndBackup();
        return true;
    } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
        android.widget.Toast.makeText(this, getString(com.beemdevelopment.aegis.R.string.saving_error), android.widget.Toast.LENGTH_LONG).show();
        return false;
    }
}


/**
 * Closes this activity if it has become an orphan (isOrphan() == true) and launches MainActivity.
 *
 * @param savedInstanceState
 * 		the bundle passed to onCreate.
 * @return whether to abort onCreate.
 */
protected boolean abortIfOrphan(android.os.Bundle savedInstanceState) {
    if ((savedInstanceState == null) || (!isOrphan())) {
        return false;
    }
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // AegisActivity_1_NullIntentOperatorMutator
        case 1176: {
            intent = null;
            break;
        }
        // AegisActivity_2_InvalidKeyIntentOperatorMutator
        case 2176: {
            intent = new android.content.Intent((AegisActivity) null, com.beemdevelopment.aegis.ui.MainActivity.class);
            break;
        }
        // AegisActivity_3_RandomActionIntentDefinitionOperatorMutator
        case 3176: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent(this, com.beemdevelopment.aegis.ui.MainActivity.class);
        break;
    }
}
switch(MUID_STATIC) {
    // AegisActivity_4_RandomActionIntentDefinitionOperatorMutator
    case 4176: {
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
    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
    break;
}
}
startActivity(intent);
finish();
return true;
}


/**
 * Reports whether this Activity instance has become an orphan. This can happen if
 * the vault was killed/locked by an external trigger while the Activity was still open.
 */
private boolean isOrphan() {
return (((!(this instanceof com.beemdevelopment.aegis.ui.MainActivity)) && (!(this instanceof com.beemdevelopment.aegis.ui.AuthActivity))) && (!(this instanceof com.beemdevelopment.aegis.ui.IntroActivity))) && (!_vaultManager.isVaultLoaded());
}


@dagger.hilt.android.EarlyEntryPoint
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent.class)
public interface PrefEntryPoint {
com.beemdevelopment.aegis.Preferences getPreferences();

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
