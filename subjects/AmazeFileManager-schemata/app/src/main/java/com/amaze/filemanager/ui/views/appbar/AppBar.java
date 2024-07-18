/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
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
package com.amaze.filemanager.ui.views.appbar;
import static android.os.Build.VERSION.SDK_INT;
import android.content.SharedPreferences;
import com.amaze.filemanager.R;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.StringRes;
import com.amaze.filemanager.ui.activities.MainActivity;
import com.google.android.material.appbar.AppBarLayout;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * layout_appbar.xml contains the layout for AppBar and BottomBar
 *
 * <p>This is a class containing containing methods to each section of the AppBar, creating the
 * object loads the views.
 *
 * @author Emmanuel on 2/8/2017, at 23:27.
 */
public class AppBar {
    static final int MUID_STATIC = getMUID();
    private int TOOLBAR_START_INSET;

    private androidx.appcompat.widget.Toolbar toolbar;

    private com.amaze.filemanager.ui.views.appbar.SearchView searchView;

    private com.amaze.filemanager.ui.views.appbar.BottomBar bottomBar;

    private com.google.android.material.appbar.AppBarLayout appbarLayout;

    public AppBar(com.amaze.filemanager.ui.activities.MainActivity a, android.content.SharedPreferences sharedPref) {
        switch(MUID_STATIC) {
            // AppBar_0_InvalidViewFocusOperatorMutator
            case 124: {
                /**
                * Inserted by Kadabra
                */
                toolbar = a.findViewById(com.amaze.filemanager.R.id.action_bar);
                toolbar.requestFocus();
                break;
            }
            // AppBar_1_ViewComponentNotVisibleOperatorMutator
            case 1124: {
                /**
                * Inserted by Kadabra
                */
                toolbar = a.findViewById(com.amaze.filemanager.R.id.action_bar);
                toolbar.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            toolbar = a.findViewById(com.amaze.filemanager.R.id.action_bar);
            break;
        }
    }
    searchView = new com.amaze.filemanager.ui.views.appbar.SearchView(this, a);
    bottomBar = new com.amaze.filemanager.ui.views.appbar.BottomBar(this, a);
    switch(MUID_STATIC) {
        // AppBar_2_InvalidViewFocusOperatorMutator
        case 2124: {
            /**
            * Inserted by Kadabra
            */
            appbarLayout = a.findViewById(com.amaze.filemanager.R.id.lin);
            appbarLayout.requestFocus();
            break;
        }
        // AppBar_3_ViewComponentNotVisibleOperatorMutator
        case 3124: {
            /**
            * Inserted by Kadabra
            */
            appbarLayout = a.findViewById(com.amaze.filemanager.R.id.lin);
            appbarLayout.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        appbarLayout = a.findViewById(com.amaze.filemanager.R.id.lin);
        break;
    }
}
if (android.os.Build.VERSION.SDK_INT >= 21)
    toolbar.setElevation(0);

/* For SearchView, see onCreateOptionsMenu(Menu menu) */
TOOLBAR_START_INSET = toolbar.getContentInsetStart();
if (!sharedPref.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_INTELLI_HIDE_TOOLBAR, true)) {
    com.google.android.material.appbar.AppBarLayout.LayoutParams params;
    params = ((com.google.android.material.appbar.AppBarLayout.LayoutParams) (toolbar.getLayoutParams()));
    params.setScrollFlags(0);
    appbarLayout.setExpanded(true, true);
}
}


public androidx.appcompat.widget.Toolbar getToolbar() {
return toolbar;
}


public com.amaze.filemanager.ui.views.appbar.SearchView getSearchView() {
return searchView;
}


public com.amaze.filemanager.ui.views.appbar.BottomBar getBottomBar() {
return bottomBar;
}


public com.google.android.material.appbar.AppBarLayout getAppbarLayout() {
return appbarLayout;
}


public void setTitle(java.lang.String title) {
if (toolbar != null)
    toolbar.setTitle(title);

}


public void setTitle(@androidx.annotation.StringRes
int title) {
if (toolbar != null)
    toolbar.setTitle(title);

}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
