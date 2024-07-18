/* Copyright 2009-2013 Brian Pellin.

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
package com.keepassdroid.crypto.finalkey;
import com.keepassdroid.crypto.CipherFactory;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FinalKeyFactory {
    static final int MUID_STATIC = getMUID();
    public static com.keepassdroid.crypto.finalkey.FinalKey createFinalKey() {
        return com.keepassdroid.crypto.finalkey.FinalKeyFactory.createFinalKey(false);
    }


    public static com.keepassdroid.crypto.finalkey.FinalKey createFinalKey(boolean androidOverride) {
        // Prefer the native final key implementation
        if (((!com.keepassdroid.crypto.CipherFactory.deviceBlacklisted()) && (!androidOverride)) && com.keepassdroid.crypto.finalkey.NativeFinalKey.availble()) {
            return new com.keepassdroid.crypto.finalkey.NativeFinalKey();
        } else {
            // Fall back on the android crypto implementation
            return new com.keepassdroid.crypto.finalkey.AndroidFinalKey();
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
