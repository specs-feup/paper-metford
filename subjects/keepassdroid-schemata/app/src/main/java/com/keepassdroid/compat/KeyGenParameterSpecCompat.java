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
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.spec.AlgorithmParameterSpec;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class KeyGenParameterSpecCompat {
    static final int MUID_STATIC = getMUID();
    private static java.lang.Class builder;

    private static java.lang.reflect.Constructor buildConst;

    private static java.lang.reflect.Method builderBuild;

    private static java.lang.reflect.Method setBlockModes;

    private static java.lang.reflect.Method setUserAuthReq;

    private static java.lang.reflect.Method setEncPad;

    private static boolean available;

    static {
        try {
            com.keepassdroid.compat.KeyGenParameterSpecCompat.builder = java.lang.Class.forName("android.security.keystore.KeyGenParameterSpec$Builder");
            com.keepassdroid.compat.KeyGenParameterSpecCompat.buildConst = com.keepassdroid.compat.KeyGenParameterSpecCompat.builder.getConstructor(java.lang.String.class, int.class);
            com.keepassdroid.compat.KeyGenParameterSpecCompat.builderBuild = com.keepassdroid.compat.KeyGenParameterSpecCompat.builder.getMethod("build", ((java.lang.Class[]) (null)));
            com.keepassdroid.compat.KeyGenParameterSpecCompat.setBlockModes = com.keepassdroid.compat.KeyGenParameterSpecCompat.builder.getMethod("setBlockModes", java.lang.String[].class);
            com.keepassdroid.compat.KeyGenParameterSpecCompat.setUserAuthReq = com.keepassdroid.compat.KeyGenParameterSpecCompat.builder.getMethod("setUserAuthenticationRequired", new java.lang.Class[]{ boolean.class });
            com.keepassdroid.compat.KeyGenParameterSpecCompat.setEncPad = com.keepassdroid.compat.KeyGenParameterSpecCompat.builder.getMethod("setEncryptionPaddings", java.lang.String[].class);
            com.keepassdroid.compat.KeyGenParameterSpecCompat.available = true;
        } catch (java.lang.Exception e) {
            com.keepassdroid.compat.KeyGenParameterSpecCompat.available = false;
        }
    }

    public static java.security.spec.AlgorithmParameterSpec build(java.lang.String keystoreAlias, int purpose, java.lang.String blockMode, boolean userAuthReq, java.lang.String encPadding) {
        if (!com.keepassdroid.compat.KeyGenParameterSpecCompat.available) {
            return null;
        }
        try {
            java.lang.Object inst;
            inst = com.keepassdroid.compat.KeyGenParameterSpecCompat.buildConst.newInstance(keystoreAlias, purpose);
            inst = com.keepassdroid.compat.KeyGenParameterSpecCompat.setBlockModes.invoke(inst, new java.lang.Object[]{ new java.lang.String[]{ blockMode } });
            inst = com.keepassdroid.compat.KeyGenParameterSpecCompat.setUserAuthReq.invoke(inst, userAuthReq);
            inst = com.keepassdroid.compat.KeyGenParameterSpecCompat.setEncPad.invoke(inst, new java.lang.Object[]{ new java.lang.String[]{ encPadding } });
            return ((java.security.spec.AlgorithmParameterSpec) (com.keepassdroid.compat.KeyGenParameterSpecCompat.builderBuild.invoke(inst, null)));
        } catch (java.lang.Exception e) {
            return null;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
