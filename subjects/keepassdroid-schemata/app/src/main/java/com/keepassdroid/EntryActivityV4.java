/* Copyright 2010-2014 Brian Pellin.

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
package com.keepassdroid;
import com.android.keepass.R;
import com.keepassdroid.app.App;
import com.keepassdroid.database.PwEntryV4;
import com.keepassdroid.database.PwDatabase;
import android.view.ViewGroup;
import com.keepassdroid.database.security.ProtectedString;
import com.keepassdroid.view.EntrySection;
import com.keepassdroid.utils.SprEngineV4;
import java.util.Map;
import android.view.View;
import com.keepassdroid.utils.SprEngine;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntryActivityV4 extends com.keepassdroid.EntryActivity {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected void setEntryView() {
        setContentView(com.android.keepass.R.layout.entry_view_v4);
    }


    @java.lang.Override
    protected void fillData(boolean trimList) {
        super.fillData(trimList);
        android.view.ViewGroup group;
        switch(MUID_STATIC) {
            // EntryActivityV4_0_InvalidViewFocusOperatorMutator
            case 202: {
                /**
                * Inserted by Kadabra
                */
                group = ((android.view.ViewGroup) (findViewById(com.android.keepass.R.id.extra_strings)));
                group.requestFocus();
                break;
            }
            // EntryActivityV4_1_ViewComponentNotVisibleOperatorMutator
            case 1202: {
                /**
                * Inserted by Kadabra
                */
                group = ((android.view.ViewGroup) (findViewById(com.android.keepass.R.id.extra_strings)));
                group.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            group = ((android.view.ViewGroup) (findViewById(com.android.keepass.R.id.extra_strings)));
            break;
        }
    }
    if (trimList) {
        group.removeAllViews();
    }
    com.keepassdroid.database.PwEntryV4 entry;
    entry = ((com.keepassdroid.database.PwEntryV4) (mEntry));
    com.keepassdroid.database.PwDatabase pm;
    pm = com.keepassdroid.app.App.getDB().pm;
    com.keepassdroid.utils.SprEngine spr;
    spr = com.keepassdroid.utils.SprEngineV4.getInstance(pm);
    // Display custom strings
    if (entry.strings.size() > 0) {
        for (java.util.Map.Entry<java.lang.String, com.keepassdroid.database.security.ProtectedString> pair : entry.strings.entrySet()) {
            java.lang.String key;
            key = pair.getKey();
            if (!com.keepassdroid.database.PwEntryV4.IsStandardString(key)) {
                java.lang.String text;
                text = pair.getValue().toString();
                android.view.View view;
                view = new com.keepassdroid.view.EntrySection(this, null, key, spr.compile(text, entry, pm));
                group.addView(view);
            }
        }
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
