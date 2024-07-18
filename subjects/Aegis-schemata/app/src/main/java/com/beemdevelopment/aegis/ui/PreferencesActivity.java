package com.beemdevelopment.aegis.ui;
import com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment;
import androidx.preference.Preference;
import androidx.fragment.app.FragmentManager;
import com.beemdevelopment.aegis.R;
import androidx.annotation.NonNull;
import android.os.Bundle;
import com.beemdevelopment.aegis.ui.fragments.preferences.MainPreferencesFragment;
import androidx.preference.PreferenceFragmentCompat;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PreferencesActivity extends com.beemdevelopment.aegis.ui.AegisActivity implements androidx.preference.PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    static final int MUID_STATIC = getMUID();
    private androidx.fragment.app.Fragment _fragment;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // PreferencesActivity_0_LengthyGUICreationOperatorMutator
            case 168: {
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
    if (abortIfOrphan(savedInstanceState)) {
        return;
    }
    setContentView(com.beemdevelopment.aegis.R.layout.activity_preferences);
    switch(MUID_STATIC) {
        // PreferencesActivity_1_InvalidIDFindViewOperatorMutator
        case 1168: {
            setSupportActionBar(findViewById(732221));
            break;
        }
        default: {
        setSupportActionBar(findViewById(com.beemdevelopment.aegis.R.id.toolbar));
        break;
    }
}
getSupportFragmentManager().registerFragmentLifecycleCallbacks(new com.beemdevelopment.aegis.ui.PreferencesActivity.FragmentResumeListener(), true);
if (getSupportActionBar() != null) {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
}
if (savedInstanceState == null) {
    _fragment = new com.beemdevelopment.aegis.ui.fragments.preferences.MainPreferencesFragment();
    _fragment.setArguments(getIntent().getExtras());
    getSupportFragmentManager().beginTransaction().replace(com.beemdevelopment.aegis.R.id.content, _fragment).commit();
    com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment requestedFragment;
    requestedFragment = getRequestedFragment();
    if (requestedFragment != null) {
        _fragment = requestedFragment;
        showFragment(_fragment);
    }
} else {
    _fragment = getSupportFragmentManager().findFragmentById(com.beemdevelopment.aegis.R.id.content);
}
}


@java.lang.Override
protected void onRestoreInstanceState(@androidx.annotation.NonNull
final android.os.Bundle inState) {
if (_fragment instanceof com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment) {
    // pass the stored result intent back to the fragment
    if (inState.containsKey("result")) {
        ((com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment) (_fragment)).setResult(inState.getParcelable("result"));
    }
}
super.onRestoreInstanceState(inState);
}


@java.lang.Override
protected void onSaveInstanceState(@androidx.annotation.NonNull
final android.os.Bundle outState) {
if (_fragment instanceof com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment) {
    // save the result intent of the fragment
    // this is done so we don't lose anything if the fragment calls recreate on this activity
    outState.putParcelable("result", ((com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment) (_fragment)).getResult());
}
super.onSaveInstanceState(outState);
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
if (item.getItemId() == android.R.id.home) {
    getOnBackPressedDispatcher().onBackPressed();
} else {
    return super.onOptionsItemSelected(item);
}
return true;
}


@java.lang.Override
public boolean onPreferenceStartFragment(@androidx.annotation.NonNull
androidx.preference.PreferenceFragmentCompat caller, androidx.preference.Preference pref) {
_fragment = getSupportFragmentManager().getFragmentFactory().instantiate(getClassLoader(), pref.getFragment());
_fragment.setArguments(pref.getExtras());
_fragment.setTargetFragment(caller, 0);
showFragment(_fragment);
setTitle(pref.getTitle());
return true;
}


private void showFragment(androidx.fragment.app.Fragment fragment) {
getSupportFragmentManager().beginTransaction().setCustomAnimations(com.beemdevelopment.aegis.R.anim.slide_in_right, com.beemdevelopment.aegis.R.anim.slide_out_left, com.beemdevelopment.aegis.R.anim.slide_in_left, com.beemdevelopment.aegis.R.anim.slide_out_right).replace(com.beemdevelopment.aegis.R.id.content, fragment).addToBackStack(null).commit();
}


@java.lang.SuppressWarnings("unchecked")
private com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment getRequestedFragment() {
java.lang.Class<? extends com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment> fragmentType;
fragmentType = ((java.lang.Class<? extends com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment>) (getIntent().getSerializableExtra("fragment")));
if (fragmentType == null) {
    return null;
}
try {
    return fragmentType.newInstance();
} catch (java.lang.IllegalAccessException | java.lang.InstantiationException e) {
    throw new java.lang.RuntimeException(e);
}
}


private class FragmentResumeListener extends androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks {
@java.lang.Override
public void onFragmentStarted(@androidx.annotation.NonNull
androidx.fragment.app.FragmentManager fm, @androidx.annotation.NonNull
androidx.fragment.app.Fragment f) {
    if (f instanceof com.beemdevelopment.aegis.ui.fragments.preferences.MainPreferencesFragment) {
        setTitle(com.beemdevelopment.aegis.R.string.action_settings);
    }
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
