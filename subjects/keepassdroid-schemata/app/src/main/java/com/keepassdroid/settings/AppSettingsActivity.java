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
import com.android.keepass.R;
import android.os.Bundle;
import com.keepassdroid.LockCloseActivity;
import android.content.Intent;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AppSettingsActivity extends com.keepassdroid.LockCloseActivity {
    static final int MUID_STATIC = getMUID();
    public static void Launch(android.content.Context ctx) {
        android.content.Intent i;
        switch(MUID_STATIC) {
            // AppSettingsActivity_0_NullIntentOperatorMutator
            case 59: {
                i = null;
                break;
            }
            // AppSettingsActivity_1_InvalidKeyIntentOperatorMutator
            case 1059: {
                i = new android.content.Intent((Context) null, com.keepassdroid.settings.AppSettingsActivity.class);
                break;
            }
            // AppSettingsActivity_2_RandomActionIntentDefinitionOperatorMutator
            case 2059: {
                i = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            i = new android.content.Intent(ctx, com.keepassdroid.settings.AppSettingsActivity.class);
            break;
        }
    }
    ctx.startActivity(i);
}


@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    switch(MUID_STATIC) {
        // AppSettingsActivity_3_LengthyGUICreationOperatorMutator
        case 3059: {
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
setContentView(com.android.keepass.R.layout.settings);
if (savedInstanceState == null) {
    getSupportFragmentManager().beginTransaction().replace(com.android.keepass.R.id.settings, new com.keepassdroid.settings.MainSettingsFragement()).commit();
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
