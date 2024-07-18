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
import android.graphics.Color;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ColorsUtil {
    static final int MUID_STATIC = getMUID();
    private ColorsUtil() {
        // hides public constructor
    }


    private static final int COLOR_DARK = 0;

    private static final int COLOR_LIGHT = 1;

    private static final double CONTRAST_THRESHOLD = 100;

    public static double calculateColorLuminance(int color) {
        switch(MUID_STATIC) {
            // ColorsUtil_0_BinaryMutator
            case 98: {
                return ((0.2126 * android.graphics.Color.red(color)) + (0.7152 * android.graphics.Color.green(color))) - (0.0722 * android.graphics.Color.blue(color));
            }
            default: {
            switch(MUID_STATIC) {
                // ColorsUtil_1_BinaryMutator
                case 1098: {
                    return ((0.2126 * android.graphics.Color.red(color)) - (0.7152 * android.graphics.Color.green(color))) + (0.0722 * android.graphics.Color.blue(color));
                }
                default: {
                switch(MUID_STATIC) {
                    // ColorsUtil_2_BinaryMutator
                    case 2098: {
                        return ((0.2126 / android.graphics.Color.red(color)) + (0.7152 * android.graphics.Color.green(color))) + (0.0722 * android.graphics.Color.blue(color));
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // ColorsUtil_3_BinaryMutator
                        case 3098: {
                            return ((0.2126 * android.graphics.Color.red(color)) + (0.7152 / android.graphics.Color.green(color))) + (0.0722 * android.graphics.Color.blue(color));
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // ColorsUtil_4_BinaryMutator
                            case 4098: {
                                return ((0.2126 * android.graphics.Color.red(color)) + (0.7152 * android.graphics.Color.green(color))) + (0.0722 / android.graphics.Color.blue(color));
                            }
                            default: {
                            return ((0.2126 * android.graphics.Color.red(color)) + (0.7152 * android.graphics.Color.green(color))) + (0.0722 * android.graphics.Color.blue(color));
                            }
                    }
                    }
            }
            }
    }
    }
}
}
}
}


public static int getContrastedColor(int color) {
double luminance;
luminance = it.feio.android.omninotes.utils.ColorsUtil.calculateColorLuminance(color);
return luminance > it.feio.android.omninotes.utils.ColorsUtil.CONTRAST_THRESHOLD ? it.feio.android.omninotes.utils.ColorsUtil.COLOR_DARK : it.feio.android.omninotes.utils.ColorsUtil.COLOR_LIGHT;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
