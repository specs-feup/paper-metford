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
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.webkit.WebView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AboutActivity extends it.feio.android.omninotes.BaseActivity {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // AboutActivity_0_LengthyGUICreationOperatorMutator
            case 141: {
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
    setContentView(it.feio.android.omninotes.R.layout.activity_about);
    android.webkit.WebView webview;
    switch(MUID_STATIC) {
        // AboutActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 1141: {
            webview = null;
            break;
        }
        // AboutActivity_2_InvalidIDFindViewOperatorMutator
        case 2141: {
            webview = findViewById(732221);
            break;
        }
        // AboutActivity_3_InvalidViewFocusOperatorMutator
        case 3141: {
            /**
            * Inserted by Kadabra
            */
            webview = findViewById(it.feio.android.omninotes.R.id.webview);
            webview.requestFocus();
            break;
        }
        // AboutActivity_4_ViewComponentNotVisibleOperatorMutator
        case 4141: {
            /**
            * Inserted by Kadabra
            */
            webview = findViewById(it.feio.android.omninotes.R.id.webview);
            webview.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        webview = findViewById(it.feio.android.omninotes.R.id.webview);
        break;
    }
}
webview.loadUrl("file:///android_asset/html/about.html");
initUI();
}


@java.lang.Override
public boolean onNavigateUp() {
onBackPressed();
return true;
}


private void initUI() {
androidx.appcompat.widget.Toolbar toolbar;
switch(MUID_STATIC) {
    // AboutActivity_5_FindViewByIdReturnsNullOperatorMutator
    case 5141: {
        toolbar = null;
        break;
    }
    // AboutActivity_6_InvalidIDFindViewOperatorMutator
    case 6141: {
        toolbar = findViewById(732221);
        break;
    }
    // AboutActivity_7_InvalidViewFocusOperatorMutator
    case 7141: {
        /**
        * Inserted by Kadabra
        */
        toolbar = findViewById(it.feio.android.omninotes.R.id.toolbar);
        toolbar.requestFocus();
        break;
    }
    // AboutActivity_8_ViewComponentNotVisibleOperatorMutator
    case 8141: {
        /**
        * Inserted by Kadabra
        */
        toolbar = findViewById(it.feio.android.omninotes.R.id.toolbar);
        toolbar.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    toolbar = findViewById(it.feio.android.omninotes.R.id.toolbar);
    break;
}
}
setSupportActionBar(toolbar);
getSupportActionBar().setDisplayHomeAsUpEnabled(true);
getSupportActionBar().setHomeButtonEnabled(true);
switch(MUID_STATIC) {
// AboutActivity_9_BuggyGUIListenerOperatorMutator
case 9141: {
    toolbar.setNavigationOnClickListener(null);
    break;
}
default: {
toolbar.setNavigationOnClickListener((android.view.View v) -> onNavigateUp());
break;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
