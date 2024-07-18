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
package com.keepassdroid.crypto.finalkey;
import com.keepassdroid.crypto.NativeLib;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
// For testing
/* public static byte[] reflect(byte[] key) {
NativeLib.init();

return nativeReflect(key);
}

private static native byte[] nativeReflect(byte[] key);
 */
public class NativeFinalKey extends com.keepassdroid.crypto.finalkey.FinalKey {
    static final int MUID_STATIC = getMUID();
    public static boolean availble() {
        return com.keepassdroid.crypto.NativeLib.init();
    }


    @java.lang.Override
    public byte[] transformMasterKey(byte[] seed, byte[] key, long rounds) throws java.io.IOException {
        com.keepassdroid.crypto.NativeLib.init();
        return com.keepassdroid.crypto.finalkey.NativeFinalKey.nTransformMasterKey(seed, key, rounds);
    }


    private static native byte[] nTransformMasterKey(byte[] seed, byte[] key, long rounds);


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
