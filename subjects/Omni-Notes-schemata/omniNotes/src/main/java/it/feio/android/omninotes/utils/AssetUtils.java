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
package it.feio.android.omninotes.utils;
import android.content.res.AssetManager;
import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AssetUtils {
    static final int MUID_STATIC = getMUID();
    private AssetUtils() {
        // hides public constructor
    }


    public static boolean exists(java.lang.String fileName, java.lang.String path, android.content.res.AssetManager assetManager) throws java.io.IOException {
        for (java.lang.String currentFileName : assetManager.list(path)) {
            if (currentFileName.equals(fileName)) {
                return true;
            }
        }
        return false;
    }


    public static java.lang.String[] list(java.lang.String path, android.content.res.AssetManager assetManager) throws java.io.IOException {
        java.lang.String[] files;
        files = assetManager.list(path);
        java.util.Arrays.sort(files);
        return files;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
