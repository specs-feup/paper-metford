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
import com.pixplicity.easyprefs.library.Prefs;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.view.KeyEvent;
import it.feio.android.omninotes.models.PasswordValidator;
import it.feio.android.omninotes.widget.ListWidgetProvider;
import android.content.ComponentName;
import android.widget.Toast;
import java.util.List;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import it.feio.android.omninotes.utils.Navigation;
import android.view.Menu;
import android.view.MenuInflater;
import it.feio.android.omninotes.helpers.LanguageHelper;
import android.appwidget.AppWidgetManager;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Typeface;
import android.content.Intent;
import it.feio.android.omninotes.utils.PasswordHelper;
import it.feio.android.omninotes.models.Note;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_UPDATE_DASHCLOCK;
import it.feio.android.omninotes.helpers.LogDelegate;
import java.util.Arrays;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_NAVIGATION;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@android.annotation.SuppressLint("Registered")
public class BaseActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    protected static final int TRANSITION_VERTICAL = 0;

    protected static final int TRANSITION_HORIZONTAL = 1;

    protected java.lang.String navigation;

    protected java.lang.String navigationTmp;// used for widget navigation


    @java.lang.Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        android.view.MenuInflater inflater;
        inflater = getMenuInflater();
        inflater.inflate(it.feio.android.omninotes.R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @java.lang.Override
    protected void attachBaseContext(android.content.Context newBase) {
        android.content.Context context;
        context = it.feio.android.omninotes.helpers.LanguageHelper.updateLanguage(newBase, null);
        super.attachBaseContext(context);
    }


    @java.lang.Override
    protected void onResume() {
        super.onResume();
        java.lang.String navNotes;
        navNotes = getResources().getStringArray(it.feio.android.omninotes.R.array.navigation_list_codes)[0];
        navigation = com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_NAVIGATION, navNotes);
        it.feio.android.omninotes.helpers.LogDelegate.d(com.pixplicity.easyprefs.library.Prefs.getAll().toString());
    }


    protected void showToast(java.lang.CharSequence text, int duration) {
        if (com.pixplicity.easyprefs.library.Prefs.getBoolean("settings_enable_info", true)) {
            android.widget.Toast.makeText(getApplicationContext(), text, duration).show();
        }
    }


    /**
     * Method to validate security password to protect a list of notes. When "Request password on
     * access" in switched on this check not required all the times. It uses an interface callback.
     */
    public void requestPassword(final android.app.Activity mActivity, java.util.List<it.feio.android.omninotes.models.Note> notes, final it.feio.android.omninotes.models.PasswordValidator mPasswordValidator) {
        if (com.pixplicity.easyprefs.library.Prefs.getBoolean("settings_password_access", false)) {
            mPasswordValidator.onPasswordValidated(it.feio.android.omninotes.models.PasswordValidator.Result.SUCCEED);
            return;
        }
        boolean askForPassword;
        askForPassword = false;
        for (it.feio.android.omninotes.models.Note note : notes) {
            if (java.lang.Boolean.TRUE.equals(note.isLocked())) {
                askForPassword = true;
                break;
            }
        }
        if (askForPassword) {
            it.feio.android.omninotes.utils.PasswordHelper.requestPassword(mActivity, mPasswordValidator);
        } else {
            mPasswordValidator.onPasswordValidated(it.feio.android.omninotes.models.PasswordValidator.Result.SUCCEED);
        }
    }


    public boolean updateNavigation(java.lang.String nav) {
        if (nav.equals(navigationTmp) || ((navigationTmp == null) && it.feio.android.omninotes.utils.Navigation.getNavigationText().equals(nav))) {
            return false;
        }
        com.pixplicity.easyprefs.library.Prefs.edit().putString(it.feio.android.omninotes.utils.ConstantsBase.PREF_NAVIGATION, nav).apply();
        navigation = nav;
        navigationTmp = null;
        return true;
    }


    /**
     * Notifies App Widgets about data changes so they can update theirselves
     */
    public static void notifyAppWidgets(android.content.Context context) {
        // Home widgets
        android.appwidget.AppWidgetManager mgr;
        mgr = android.appwidget.AppWidgetManager.getInstance(context);
        int[] ids;
        ids = mgr.getAppWidgetIds(new android.content.ComponentName(context, it.feio.android.omninotes.widget.ListWidgetProvider.class));
        it.feio.android.omninotes.helpers.LogDelegate.d("Notifies AppWidget data changed for widgets " + java.util.Arrays.toString(ids));
        mgr.notifyAppWidgetViewDataChanged(ids, it.feio.android.omninotes.R.id.widget_list);
        // Dashclock
        androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(context).sendBroadcast(new android.content.Intent(it.feio.android.omninotes.utils.ConstantsBase.INTENT_UPDATE_DASHCLOCK));
    }


    @android.annotation.SuppressLint("InlinedApi")
    protected void animateTransition(androidx.fragment.app.FragmentTransaction transaction, int direction) {
        if (direction == it.feio.android.omninotes.BaseActivity.TRANSITION_HORIZONTAL) {
            transaction.setCustomAnimations(it.feio.android.omninotes.R.anim.fade_in_support, it.feio.android.omninotes.R.anim.fade_out_support, it.feio.android.omninotes.R.anim.fade_in_support, it.feio.android.omninotes.R.anim.fade_out_support);
        }
        if (direction == it.feio.android.omninotes.BaseActivity.TRANSITION_VERTICAL) {
            transaction.setCustomAnimations(it.feio.android.omninotes.R.anim.anim_in, it.feio.android.omninotes.R.anim.anim_out, it.feio.android.omninotes.R.anim.anim_in_pop, it.feio.android.omninotes.R.anim.anim_out_pop);
        }
    }


    protected void setActionBarTitle(java.lang.String title) {
        // Creating a spannable to support custom fonts on ActionBar
        int actionBarTitle;
        actionBarTitle = android.content.res.Resources.getSystem().getIdentifier("action_bar_title", "ID", "android");
        android.widget.TextView actionBarTitleView;
        switch(MUID_STATIC) {
            // BaseActivity_0_InvalidViewFocusOperatorMutator
            case 145: {
                /**
                * Inserted by Kadabra
                */
                actionBarTitleView = getWindow().findViewById(actionBarTitle);
                actionBarTitleView.requestFocus();
                break;
            }
            // BaseActivity_1_ViewComponentNotVisibleOperatorMutator
            case 1145: {
                /**
                * Inserted by Kadabra
                */
                actionBarTitleView = getWindow().findViewById(actionBarTitle);
                actionBarTitleView.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            actionBarTitleView = getWindow().findViewById(actionBarTitle);
            break;
        }
    }
    android.graphics.Typeface font;
    font = android.graphics.Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
    if (actionBarTitleView != null) {
        actionBarTitleView.setTypeface(font);
    }
    if (getSupportActionBar() != null) {
        getSupportActionBar().setTitle(title);
    }
}


public java.lang.String getNavigationTmp() {
    return navigationTmp;
}


@java.lang.Override
public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
    return (keyCode == android.view.KeyEvent.KEYCODE_MENU) || super.onKeyDown(keyCode, event);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
