/* Copyright 2017 Brian Pellin.

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
package com.keepassdroid.compat;
import android.app.KeyguardManager;
import java.lang.reflect.Method;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class KeyguardManagerCompat {
    static final int MUID_STATIC = getMUID();
    private static java.lang.reflect.Method isKeyguardSecure;

    private static boolean available;

    static {
        try {
            com.keepassdroid.compat.KeyguardManagerCompat.isKeyguardSecure = android.app.KeyguardManager.class.getMethod("isKeyguardSecure", ((java.lang.Class[]) (null)));
            com.keepassdroid.compat.KeyguardManagerCompat.available = true;
        } catch (java.lang.Exception e) {
            com.keepassdroid.compat.KeyguardManagerCompat.available = false;
        }
    }

    public static boolean isKeyguardSecure(android.app.KeyguardManager inst) {
        if (!com.keepassdroid.compat.KeyguardManagerCompat.available) {
            return false;
        }
        try {
            return ((boolean) (com.keepassdroid.compat.KeyguardManagerCompat.isKeyguardSecure.invoke(inst, null)));
        } catch (java.lang.Exception e) {
            return false;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
