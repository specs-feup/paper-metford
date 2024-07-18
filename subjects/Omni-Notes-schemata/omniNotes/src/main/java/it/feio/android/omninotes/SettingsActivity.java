/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
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
package it.feio.android.omninotes;
import androidx.preference.Preference;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import android.os.Bundle;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import de.keyboardsurfer.android.widget.crouton.Style;
import java.util.List;
import androidx.fragment.app.Fragment;
import android.view.View;
import it.feio.android.omninotes.databinding.ActivitySettingsBinding;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SettingsActivity extends androidx.appcompat.app.AppCompatActivity implements androidx.preference.PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    static final int MUID_STATIC = getMUID();
    private java.util.List<androidx.fragment.app.Fragment> backStack = new java.util.ArrayList<>();

    private it.feio.android.omninotes.databinding.ActivitySettingsBinding binding;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SettingsActivity_0_LengthyGUICreationOperatorMutator
            case 144: {
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
    binding = it.feio.android.omninotes.databinding.ActivitySettingsBinding.inflate(getLayoutInflater());
    android.view.View view;
    view = binding.getRoot();
    setContentView(view);
    initUI();
    getSupportFragmentManager().beginTransaction().replace(it.feio.android.omninotes.R.id.content_frame, new it.feio.android.omninotes.SettingsFragment()).commit();
}


void initUI() {
    setSupportActionBar(binding.toolbar.toolbar);
    switch(MUID_STATIC) {
        // SettingsActivity_1_BuggyGUIListenerOperatorMutator
        case 1144: {
            binding.toolbar.toolbar.setNavigationOnClickListener(null);
            break;
        }
        default: {
        binding.toolbar.toolbar.setNavigationOnClickListener((android.view.View v) -> onBackPressed());
        break;
    }
}
getSupportActionBar().setDisplayHomeAsUpEnabled(true);
getSupportActionBar().setHomeButtonEnabled(true);
}


private void replaceFragment(androidx.fragment.app.Fragment sf) {
getSupportFragmentManager().beginTransaction().setCustomAnimations(it.feio.android.omninotes.R.animator.fade_in, it.feio.android.omninotes.R.animator.fade_out, it.feio.android.omninotes.R.animator.fade_in, it.feio.android.omninotes.R.animator.fade_out).replace(it.feio.android.omninotes.R.id.content_frame, sf).commit();
}


@java.lang.Override
public void onBackPressed() {
if (!backStack.isEmpty()) {
    switch(MUID_STATIC) {
        // SettingsActivity_2_BinaryMutator
        case 2144: {
            replaceFragment(backStack.remove(backStack.size() + 1));
            break;
        }
        default: {
        replaceFragment(backStack.remove(backStack.size() - 1));
        break;
    }
}
} else {
super.onBackPressed();
}
}


public void showMessage(int messageId, de.keyboardsurfer.android.widget.crouton.Style style) {
showMessage(getString(messageId), style);
}


public void showMessage(java.lang.String message, de.keyboardsurfer.android.widget.crouton.Style style) {
// ViewGroup used to show Crouton keeping compatibility with the new Toolbar
de.keyboardsurfer.android.widget.crouton.Crouton.makeText(this, message, style, binding.croutonHandle.croutonHandle).show();
}


@java.lang.Override
public void onPointerCaptureChanged(boolean hasCapture) {
// Nothing to do
}


@java.lang.Override
public boolean onPreferenceStartFragment(androidx.preference.PreferenceFragmentCompat caller, androidx.preference.Preference pref) {
android.os.Bundle b;
b = new android.os.Bundle();
b.putString(it.feio.android.omninotes.SettingsFragment.XML_NAME, pref.getKey());
final androidx.fragment.app.Fragment fragment;
fragment = getSupportFragmentManager().getFragmentFactory().instantiate(getClassLoader(), pref.getFragment());
fragment.setArguments(b);
fragment.setTargetFragment(caller, 0);
getSupportFragmentManager().beginTransaction().replace(it.feio.android.omninotes.R.id.content_frame, fragment).addToBackStack(null).commit();
return true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
