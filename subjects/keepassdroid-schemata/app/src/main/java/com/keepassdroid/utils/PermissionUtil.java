/* Copyright 2018 Brian Pellin.

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
import androidx.core.app.ActivityCompat;
import android.Manifest;
import androidx.core.content.ContextCompat;
import android.app.Activity;
import android.content.pm.PackageManager;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PermissionUtil {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String[] READ_WRITE_PERMISSIONS = new java.lang.String[]{ android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public static boolean checkAndRequest(android.app.Activity act, int reqId) {
        boolean hasRead;
        hasRead = androidx.core.content.ContextCompat.checkSelfPermission(act, android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED;
        boolean hasWrite;
        hasWrite = androidx.core.content.ContextCompat.checkSelfPermission(act, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED;
        if ((!hasRead) || (!hasWrite)) {
            androidx.core.app.ActivityCompat.requestPermissions(act, com.keepassdroid.utils.PermissionUtil.READ_WRITE_PERMISSIONS, reqId);
            return false;
        }
        return true;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
