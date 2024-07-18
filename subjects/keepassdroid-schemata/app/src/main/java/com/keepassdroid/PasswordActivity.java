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
package com.keepassdroid;
import com.keepassdroid.app.App;
import com.keepassdroid.utils.UriUtil;
import com.keepassdroid.utils.EmptyUtils;
import android.os.Bundle;
import java.io.FileNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.Manifest;
import com.android.keepass.R;
import android.app.Activity;
import androidx.appcompat.widget.Toolbar;
import java.io.File;
import androidx.annotation.Nullable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PasswordActivity extends com.keepassdroid.LockingActivity {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String KEY_DEFAULT_FILENAME = "defaultFileName";

    public static final java.lang.String KEY_FILENAME = "fileName";

    public static final java.lang.String KEY_KEYFILE = "keyFile";

    private static final java.lang.String[] READ_WRITE_PERMISSIONS = new java.lang.String[]{ android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public static void Launch(android.app.Activity act, java.lang.String fileName) throws java.io.FileNotFoundException {
        com.keepassdroid.PasswordActivity.Launch(act, fileName, "");
    }


    public static void Launch(android.app.Activity act, java.lang.String fileName, java.lang.String keyFile) throws java.io.FileNotFoundException {
        if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(fileName)) {
            throw new java.io.FileNotFoundException();
        }
        android.net.Uri uri;
        uri = com.keepassdroid.utils.UriUtil.parseDefaultFile(fileName);
        java.lang.String scheme;
        scheme = uri.getScheme();
        if ((!com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(scheme)) && scheme.equalsIgnoreCase("file")) {
            java.io.File dbFile;
            dbFile = new java.io.File(uri.getPath());
            if (!dbFile.exists()) {
                throw new java.io.FileNotFoundException();
            }
        }
        android.content.Intent i;
        switch(MUID_STATIC) {
            // PasswordActivity_0_NullIntentOperatorMutator
            case 210: {
                i = null;
                break;
            }
            // PasswordActivity_1_InvalidKeyIntentOperatorMutator
            case 1210: {
                i = new android.content.Intent((Activity) null, com.keepassdroid.PasswordActivity.class);
                break;
            }
            // PasswordActivity_2_RandomActionIntentDefinitionOperatorMutator
            case 2210: {
                i = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            i = new android.content.Intent(act, com.keepassdroid.PasswordActivity.class);
            break;
        }
    }
    switch(MUID_STATIC) {
        // PasswordActivity_3_NullValueIntentPutExtraOperatorMutator
        case 3210: {
            i.putExtra(com.keepassdroid.PasswordActivity.KEY_FILENAME, new Parcelable[0]);
            break;
        }
        // PasswordActivity_4_IntentPayloadReplacementOperatorMutator
        case 4210: {
            i.putExtra(com.keepassdroid.PasswordActivity.KEY_FILENAME, "");
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // PasswordActivity_5_RandomActionIntentDefinitionOperatorMutator
            case 5210: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            i.putExtra(com.keepassdroid.PasswordActivity.KEY_FILENAME, fileName);
            break;
        }
    }
    break;
}
}
switch(MUID_STATIC) {
// PasswordActivity_6_NullValueIntentPutExtraOperatorMutator
case 6210: {
    i.putExtra(com.keepassdroid.PasswordActivity.KEY_KEYFILE, new Parcelable[0]);
    break;
}
// PasswordActivity_7_IntentPayloadReplacementOperatorMutator
case 7210: {
    i.putExtra(com.keepassdroid.PasswordActivity.KEY_KEYFILE, "");
    break;
}
default: {
switch(MUID_STATIC) {
    // PasswordActivity_8_RandomActionIntentDefinitionOperatorMutator
    case 8210: {
        /**
        * Inserted by Kadabra
        */
        /**
        * Inserted by Kadabra
        */
        new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    i.putExtra(com.keepassdroid.PasswordActivity.KEY_KEYFILE, keyFile);
    break;
}
}
break;
}
}
act.startActivityForResult(i, 0);
}


@java.lang.Override
protected void onCreate(@androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
switch(MUID_STATIC) {
// PasswordActivity_9_LengthyGUICreationOperatorMutator
case 9210: {
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
setContentView(com.android.keepass.R.layout.password_activity);
androidx.appcompat.widget.Toolbar toolbar;
switch(MUID_STATIC) {
// PasswordActivity_10_InvalidViewFocusOperatorMutator
case 10210: {
/**
* Inserted by Kadabra
*/
toolbar = ((androidx.appcompat.widget.Toolbar) (findViewById(com.android.keepass.R.id.toolbar)));
toolbar.requestFocus();
break;
}
// PasswordActivity_11_ViewComponentNotVisibleOperatorMutator
case 11210: {
/**
* Inserted by Kadabra
*/
toolbar = ((androidx.appcompat.widget.Toolbar) (findViewById(com.android.keepass.R.id.toolbar)));
toolbar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
toolbar = ((androidx.appcompat.widget.Toolbar) (findViewById(com.android.keepass.R.id.toolbar)));
break;
}
}
setSupportActionBar(toolbar);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
