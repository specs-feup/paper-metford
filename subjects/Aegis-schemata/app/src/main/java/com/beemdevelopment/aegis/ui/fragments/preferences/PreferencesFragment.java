package com.beemdevelopment.aegis.ui.fragments.preferences;
import javax.inject.Inject;
import android.os.Bundle;
import com.beemdevelopment.aegis.vault.VaultRepositoryException;
import android.content.Intent;
import dagger.hilt.android.AndroidEntryPoint;
import androidx.annotation.CallSuper;
import androidx.preference.Preference;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.vault.VaultManager;
import com.beemdevelopment.aegis.R;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;
import com.beemdevelopment.aegis.Preferences;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@dagger.hilt.android.AndroidEntryPoint
public abstract class PreferencesFragment extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    // activity request codes
    public static final int CODE_IMPORT_SELECT = 0;

    public static final int CODE_GROUPS = 3;

    public static final int CODE_IMPORT = 4;

    public static final int CODE_EXPORT = 5;

    public static final int CODE_EXPORT_PLAIN = 6;

    public static final int CODE_EXPORT_GOOGLE_URI = 7;

    public static final int CODE_EXPORT_HTML = 8;

    public static final int CODE_BACKUPS = 9;

    private android.content.Intent _result;

    @javax.inject.Inject
    com.beemdevelopment.aegis.Preferences _prefs;

    @javax.inject.Inject
    com.beemdevelopment.aegis.vault.VaultManager _vaultManager;

    @java.lang.Override
    @androidx.annotation.CallSuper
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        setResult(new android.content.Intent());
    }


    @java.lang.Override
    @androidx.annotation.CallSuper
    public void onResume() {
        super.onResume();
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // PreferencesFragment_0_RandomActionIntentDefinitionOperatorMutator
            case 135: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = requireActivity().getIntent();
            break;
        }
    }
    java.lang.String preference;
    preference = intent.getStringExtra("pref");
    if (preference != null) {
        scrollToPreference(preference);
        intent.removeExtra("pref");
    }
}


public android.content.Intent getResult() {
    return _result;
}


public void setResult(android.content.Intent result) {
    _result = result;
    requireActivity().setResult(android.app.Activity.RESULT_OK, _result);
}


protected boolean saveAndBackupVault() {
    try {
        _vaultManager.saveAndBackup();
    } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
        e.printStackTrace();
        com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.saving_error, e);
        return false;
    }
    return true;
}


@androidx.annotation.NonNull
protected <T extends androidx.preference.Preference> T requirePreference(@androidx.annotation.NonNull
java.lang.CharSequence key) {
    T pref;
    pref = findPreference(key);
    if (pref == null) {
        throw new java.lang.IllegalStateException(java.lang.String.format("Preference %s not found", key));
    }
    return pref;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
