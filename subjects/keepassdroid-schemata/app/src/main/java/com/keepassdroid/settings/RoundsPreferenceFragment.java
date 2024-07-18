/* Copyright 2009-2020 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.settings;
import com.keepassdroid.ProgressTask;
import com.keepassdroid.app.App;
import com.keepassdroid.database.PwDatabase;
import android.os.Bundle;
import androidx.preference.EditTextPreferenceDialogFragmentCompat;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import com.keepassdroid.database.edit.OnFinish;
import com.keepassdroid.database.edit.SaveDB;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import android.widget.EditText;
import androidx.preference.Preference;
import androidx.fragment.app.FragmentManager;
import com.android.keepass.R;
import android.util.AttributeSet;
import android.text.InputType;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import com.keepassdroid.Database;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RoundsPreferenceFragment extends androidx.preference.EditTextPreferenceDialogFragmentCompat {
    static final int MUID_STATIC = getMUID();
    private android.widget.EditText mEditText;

    private com.keepassdroid.database.PwDatabase mPM;

    @java.lang.Override
    protected void onBindDialogView(android.view.View view) {
        super.onBindDialogView(view);
        switch(MUID_STATIC) {
            // RoundsPreferenceFragment_0_InvalidViewFocusOperatorMutator
            case 63: {
                /**
                * Inserted by Kadabra
                */
                mEditText = view.findViewById(android.R.id.edit);
                mEditText.requestFocus();
                break;
            }
            // RoundsPreferenceFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1063: {
                /**
                * Inserted by Kadabra
                */
                mEditText = view.findViewById(android.R.id.edit);
                mEditText.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            mEditText = view.findViewById(android.R.id.edit);
            break;
        }
    }
    mEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_NORMAL);
    com.keepassdroid.Database db;
    db = com.keepassdroid.app.App.getDB();
    mPM = db.pm;
    mEditText.setText(java.lang.Long.toString(db.pm.getNumRounds()));
}


public static com.keepassdroid.settings.RoundsPreferenceFragment newInstance(java.lang.String key) {
    final com.keepassdroid.settings.RoundsPreferenceFragment fragment;
    fragment = new com.keepassdroid.settings.RoundsPreferenceFragment();
    final android.os.Bundle b;
    b = new android.os.Bundle(1);
    b.putString(androidx.preference.PreferenceDialogFragmentCompat.ARG_KEY, key);
    fragment.setArguments(b);
    return fragment;
}


@java.lang.Override
public void onDialogClosed(boolean positiveResult) {
    androidx.preference.EditTextPreference pref;
    pref = ((androidx.preference.EditTextPreference) (getPreference()));
    if (positiveResult) {
        int rounds;
        try {
            java.lang.String strRounds;
            strRounds = mEditText.getText().toString();
            rounds = java.lang.Integer.parseInt(strRounds);
        } catch (java.lang.NumberFormatException e) {
            android.widget.Toast.makeText(getContext(), com.android.keepass.R.string.error_rounds_not_number, android.widget.Toast.LENGTH_LONG).show();
            return;
        }
        if (rounds < 1) {
            rounds = 1;
        }
        long oldRounds;
        oldRounds = mPM.getNumRounds();
        try {
            mPM.setNumRounds(rounds);
        } catch (java.lang.NumberFormatException e) {
            android.widget.Toast.makeText(getContext(), com.android.keepass.R.string.error_rounds_too_large, android.widget.Toast.LENGTH_LONG).show();
            mPM.setNumRounds(java.lang.Integer.MAX_VALUE);
        }
        android.os.Handler handler;
        handler = new android.os.Handler();
        androidx.fragment.app.FragmentActivity activity;
        activity = getActivity();
        com.keepassdroid.database.edit.SaveDB save;
        save = new com.keepassdroid.database.edit.SaveDB(getContext(), com.keepassdroid.app.App.getDB(), new com.keepassdroid.settings.RoundsPreferenceFragment.AfterSave(activity, activity.getSupportFragmentManager(), handler, oldRounds));
        com.keepassdroid.ProgressTask pt;
        pt = new com.keepassdroid.ProgressTask(getActivity(), save, com.android.keepass.R.string.saving_database);
        pt.run();
    }
}


private class AfterSave extends com.keepassdroid.database.edit.OnFinish {
    private long mOldRounds;

    private android.content.Context mCtx;

    androidx.fragment.app.FragmentManager mFm;

    public AfterSave(android.content.Context ctx, androidx.fragment.app.FragmentManager fm, android.os.Handler handler, long oldRounds) {
        super(handler);
        mCtx = ctx;
        mFm = fm;
        mOldRounds = oldRounds;
    }


    @java.lang.Override
    public void run() {
        if (mSuccess) {
            androidx.preference.Preference preference;
            preference = getPreference();
            androidx.preference.Preference.OnPreferenceChangeListener listner;
            listner = preference.getOnPreferenceChangeListener();
            if (listner != null) {
                listner.onPreferenceChange(preference, null);
            }
        } else {
            displayMessage(mCtx, mFm);
            mPM.setNumRounds(mOldRounds);
        }
        super.run();
    }

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
