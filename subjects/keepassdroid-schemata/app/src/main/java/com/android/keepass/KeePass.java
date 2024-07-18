/* Copyright 2009 Brian Pellin.

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
package com.android.keepass;
import android.app.Activity;
import android.os.Bundle;
import com.keepassdroid.fileselect.FileSelectActivity;
import android.content.Intent;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class KeePass extends android.app.Activity {
    static final int MUID_STATIC = getMUID();
    public static final int EXIT_NORMAL = 0;

    public static final int EXIT_LOCK = 1;

    public static final int EXIT_REFRESH = 2;

    public static final int EXIT_REFRESH_TITLE = 3;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // KeePass_0_LengthyGUICreationOperatorMutator
            case 12: {
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
}


@java.lang.Override
protected void onStart() {
    super.onStart();
    startFileSelect();
}


private void startFileSelect() {
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // KeePass_1_NullIntentOperatorMutator
        case 1012: {
            intent = null;
            break;
        }
        // KeePass_2_InvalidKeyIntentOperatorMutator
        case 2012: {
            intent = new android.content.Intent((KeePass) null, com.keepassdroid.fileselect.FileSelectActivity.class);
            break;
        }
        // KeePass_3_RandomActionIntentDefinitionOperatorMutator
        case 3012: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent(this, com.keepassdroid.fileselect.FileSelectActivity.class);
        break;
    }
}
startActivityForResult(intent, 0);
}


@java.lang.Override
protected void onDestroy() {
super.onDestroy();
}


@java.lang.Override
protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
super.onActivityResult(requestCode, resultCode, data);
if (resultCode == com.android.keepass.KeePass.EXIT_NORMAL) {
    finish();
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
