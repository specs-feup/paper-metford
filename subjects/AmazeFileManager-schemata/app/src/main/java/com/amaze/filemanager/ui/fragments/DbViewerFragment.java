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
package com.amaze.filemanager.ui.fragments;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import com.amaze.filemanager.asynchronous.asynctasks.DbViewerTask;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import android.database.Cursor;
import android.view.View;
import com.amaze.filemanager.R;
import android.view.LayoutInflater;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.ui.activities.DatabaseViewerActivity;
import android.widget.RelativeLayout;
import com.amaze.filemanager.ui.theme.AppTheme;
import androidx.annotation.Nullable;
import android.webkit.WebView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by Vishal on 06-02-2015.
 */
public class DbViewerFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    public com.amaze.filemanager.ui.activities.DatabaseViewerActivity databaseViewerActivity;

    private java.lang.String tableName;

    private android.view.View rootView;

    private android.database.Cursor schemaCursor;

    private android.database.Cursor contentCursor;

    private android.widget.RelativeLayout relativeLayout;

    public androidx.appcompat.widget.AppCompatTextView loadingText;

    private android.webkit.WebView webView;

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        databaseViewerActivity = ((com.amaze.filemanager.ui.activities.DatabaseViewerActivity) (getActivity()));
        rootView = inflater.inflate(com.amaze.filemanager.R.layout.fragment_db_viewer, null);
        switch(MUID_STATIC) {
            // DbViewerFragment_0_InvalidViewFocusOperatorMutator
            case 116: {
                /**
                * Inserted by Kadabra
                */
                webView = rootView.findViewById(com.amaze.filemanager.R.id.webView1);
                webView.requestFocus();
                break;
            }
            // DbViewerFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1116: {
                /**
                * Inserted by Kadabra
                */
                webView = rootView.findViewById(com.amaze.filemanager.R.id.webView1);
                webView.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            webView = rootView.findViewById(com.amaze.filemanager.R.id.webView1);
            break;
        }
    }
    switch(MUID_STATIC) {
        // DbViewerFragment_2_InvalidViewFocusOperatorMutator
        case 2116: {
            /**
            * Inserted by Kadabra
            */
            loadingText = rootView.findViewById(com.amaze.filemanager.R.id.loadingText);
            loadingText.requestFocus();
            break;
        }
        // DbViewerFragment_3_ViewComponentNotVisibleOperatorMutator
        case 3116: {
            /**
            * Inserted by Kadabra
            */
            loadingText = rootView.findViewById(com.amaze.filemanager.R.id.loadingText);
            loadingText.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        loadingText = rootView.findViewById(com.amaze.filemanager.R.id.loadingText);
        break;
    }
}
switch(MUID_STATIC) {
    // DbViewerFragment_4_InvalidViewFocusOperatorMutator
    case 4116: {
        /**
        * Inserted by Kadabra
        */
        relativeLayout = rootView.findViewById(com.amaze.filemanager.R.id.tableLayout);
        relativeLayout.requestFocus();
        break;
    }
    // DbViewerFragment_5_ViewComponentNotVisibleOperatorMutator
    case 5116: {
        /**
        * Inserted by Kadabra
        */
        relativeLayout = rootView.findViewById(com.amaze.filemanager.R.id.tableLayout);
        relativeLayout.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    relativeLayout = rootView.findViewById(com.amaze.filemanager.R.id.tableLayout);
    break;
}
}
tableName = getArguments().getString("table");
databaseViewerActivity.setTitle(tableName);
schemaCursor = databaseViewerActivity.sqLiteDatabase.rawQuery(("PRAGMA table_info(" + tableName) + ");", null);
contentCursor = databaseViewerActivity.sqLiteDatabase.rawQuery("SELECT * FROM " + tableName, null);
new com.amaze.filemanager.asynchronous.asynctasks.DbViewerTask(schemaCursor, contentCursor, webView, this).execute();
return rootView;
}


@java.lang.Override
public void onActivityCreated(@androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
super.onActivityCreated(savedInstanceState);
if (databaseViewerActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK)) {
relativeLayout.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.R.color.holo_dark_background));
webView.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.R.color.holo_dark_background));
} else if (databaseViewerActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
relativeLayout.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), android.R.color.black));
webView.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), android.R.color.black));
} else {
relativeLayout.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
webView.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
}
}


@java.lang.Override
public void onDetach() {
super.onDetach();
schemaCursor.close();
contentCursor.close();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
