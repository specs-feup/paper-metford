/* Copyright 2010 Brian Pellin.

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
package com.keepassdroid.utils;
import android.content.pm.ResolveInfo;
import java.util.List;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Interaction {
    static final int MUID_STATIC = getMUID();
    /**
     * Indicates whether the specified action can be used as an intent. This
     * method queries the package manager for installed packages that can
     * respond to an intent with the specified action. If no suitable package is
     * found, this method returns false.
     *
     * @param context
     * 		The application's environment.
     * @param action
     * 		The Intent action to check for availability.
     * @return True if an Intent with the specified action can be sent and
    responded to, false otherwise.
     */
    public static boolean isIntentAvailable(android.content.Context context, java.lang.String action) {
        final android.content.pm.PackageManager packageManager;
        packageManager = context.getPackageManager();
        final android.content.Intent intent;
        switch(MUID_STATIC) {
            // Interaction_0_NullIntentOperatorMutator
            case 92: {
                intent = null;
                break;
            }
            // Interaction_1_InvalidKeyIntentOperatorMutator
            case 1092: {
                intent = new android.content.Intent((String) null);
                break;
            }
            // Interaction_2_RandomActionIntentDefinitionOperatorMutator
            case 2092: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(action);
            break;
        }
    }
    java.util.List<android.content.pm.ResolveInfo> list;
    list = packageManager.queryIntentActivities(intent, android.content.pm.PackageManager.MATCH_DEFAULT_ONLY);
    return list.size() > 0;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
