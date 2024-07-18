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
import android.graphics.Rect;
import android.content.res.Configuration;
import android.app.Activity;
import android.os.Build;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.WindowManager;
import it.feio.android.omninotes.helpers.LogDelegate;
import android.annotation.TargetApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Display {
    static final int MUID_STATIC = getMUID();
    private Display() {
        // hides public constructor
    }


    public static android.view.View getRootView(android.app.Activity mActivity) {
        return mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
    }


    @java.lang.SuppressWarnings("deprecation")
    @android.annotation.SuppressLint("NewApi")
    public static android.graphics.Point getUsableSize(android.content.Context mContext) {
        android.graphics.Point displaySize;
        displaySize = new android.graphics.Point();
        try {
            android.view.WindowManager manager;
            manager = ((android.view.WindowManager) (mContext.getSystemService(android.content.Context.WINDOW_SERVICE)));
            if (manager != null) {
                android.view.Display display;
                display = manager.getDefaultDisplay();
                if (display != null) {
                    display.getSize(displaySize);
                }
            }
        } catch (java.lang.Exception e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error checking display sizes", e);
        }
        return displaySize;
    }


    public static android.graphics.Point getVisibleSize(android.app.Activity activity) {
        android.graphics.Point displaySize;
        displaySize = new android.graphics.Point();
        android.graphics.Rect r;
        r = new android.graphics.Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        switch(MUID_STATIC) {
            // Display_0_BinaryMutator
            case 93: {
                displaySize.x = r.right + r.left;
                break;
            }
            default: {
            displaySize.x = r.right - r.left;
            break;
        }
    }
    switch(MUID_STATIC) {
        // Display_1_BinaryMutator
        case 1093: {
            displaySize.y = r.bottom + r.top;
            break;
        }
        default: {
        displaySize.y = r.bottom - r.top;
        break;
    }
}
return displaySize;
}


public static android.graphics.Point getFullSize(android.view.View view) {
android.graphics.Point displaySize;
displaySize = new android.graphics.Point();
displaySize.x = view.getRootView().getWidth();
displaySize.y = view.getRootView().getHeight();
return displaySize;
}


public static int getStatusBarHeight(android.content.Context mContext) {
int result;
result = 0;
int resourceId;
resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
if (resourceId > 0) {
    result = mContext.getResources().getDimensionPixelSize(resourceId);
}
return result;
}


public static int getNavigationBarHeightStandard(android.content.Context mContext) {
int resourceId;
resourceId = mContext.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
if (resourceId > 0) {
    return mContext.getResources().getDimensionPixelSize(resourceId);
}
return 0;
}


@android.annotation.TargetApi(android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
public static android.graphics.Point getScreenDimensions(android.content.Context mContext) {
android.view.WindowManager wm;
wm = ((android.view.WindowManager) (mContext.getSystemService(android.content.Context.WINDOW_SERVICE)));
android.view.Display display;
display = wm.getDefaultDisplay();
android.graphics.Point size;
size = new android.graphics.Point();
android.util.DisplayMetrics metrics;
metrics = new android.util.DisplayMetrics();
display.getRealMetrics(metrics);
size.x = metrics.widthPixels;
size.y = metrics.heightPixels;
return size;
}


@android.annotation.TargetApi(android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
public static int getNavigationBarHeightKitkat(android.content.Context mContext) {
switch(MUID_STATIC) {
    // Display_2_BinaryMutator
    case 2093: {
        return it.feio.android.omninotes.utils.Display.getScreenDimensions(mContext).y + it.feio.android.omninotes.utils.Display.getUsableSize(mContext).y;
    }
    default: {
    return it.feio.android.omninotes.utils.Display.getScreenDimensions(mContext).y - it.feio.android.omninotes.utils.Display.getUsableSize(mContext).y;
    }
}
}


public static boolean orientationLandscape(android.content.Context context) {
return context.getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE;
}


public static int getSoftButtonsBarHeight(android.app.Activity activity) {
android.util.DisplayMetrics metrics;
metrics = new android.util.DisplayMetrics();
activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
int usableHeight;
usableHeight = metrics.heightPixels;
activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
int realHeight;
realHeight = metrics.heightPixels;
if (realHeight > usableHeight) {
switch(MUID_STATIC) {
    // Display_3_BinaryMutator
    case 3093: {
        return realHeight + usableHeight;
    }
    default: {
    return realHeight - usableHeight;
    }
}
} else {
return 0;
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
