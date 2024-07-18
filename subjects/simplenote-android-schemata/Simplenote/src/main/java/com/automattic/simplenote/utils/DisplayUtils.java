package com.automattic.simplenote.utils;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import org.wordpress.passcodelock.AppLockManager;
import android.view.View;
import android.util.TypedValue;
import com.automattic.simplenote.R;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.Display;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DisplayUtils {
    static final int MUID_STATIC = getMUID();
    private DisplayUtils() {
        throw new java.lang.AssertionError();
    }


    public static boolean isLandscape(android.content.Context context) {
        return (context != null) && (context.getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE);
    }


    public static android.graphics.Point getDisplayPixelSize(android.content.Context context) {
        android.view.WindowManager wm;
        wm = ((android.view.WindowManager) (context.getSystemService(android.content.Context.WINDOW_SERVICE)));
        android.view.Display display;
        display = wm.getDefaultDisplay();
        android.graphics.Point size;
        size = new android.graphics.Point();
        display.getSize(size);
        return size;
    }


    @java.lang.SuppressWarnings("ConstantConditions")
    public static java.lang.String getDisplaySizeAndOrientation(android.content.Context context) {
        boolean isLarge;
        isLarge = com.automattic.simplenote.utils.DisplayUtils.isLarge(context) || com.automattic.simplenote.utils.DisplayUtils.isXLarge(context);
        boolean isLandscape;
        isLandscape = com.automattic.simplenote.utils.DisplayUtils.isLandscape(context);
        if (isLarge && isLandscape) {
            return "Large, landscape";
        } else if (isLarge && (!isLandscape)) {
            return "Large, portrait";
        } else if ((!isLarge) && isLandscape) {
            return "Small, landscape";
        } else if ((!isLarge) && (!isLandscape)) {
            return "Small, portrait";
        } else {
            return "Unknown";
        }
    }


    public static int dpToPx(android.content.Context context, int dp) {
        float px;
        px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return ((int) (px));
    }


    public static boolean isLarge(android.content.Context context) {
        return (context.getResources().getConfiguration().screenLayout & android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK) == android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    public static boolean isXLarge(android.content.Context context) {
        return (context.getResources().getConfiguration().screenLayout & android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK) == android.content.res.Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }


    public static boolean isLargeScreen(android.content.Context context) {
        return com.automattic.simplenote.utils.DisplayUtils.isLarge(context) || com.automattic.simplenote.utils.DisplayUtils.isXLarge(context);
    }


    public static boolean isLargeScreenLandscape(android.content.Context context) {
        return com.automattic.simplenote.utils.DisplayUtils.isLargeScreen(context) && com.automattic.simplenote.utils.DisplayUtils.isLandscape(context);
    }


    /**
     * returns the height of the ActionBar if one is enabled - supports both the native ActionBar
     * and ActionBarSherlock - http://stackoverflow.com/a/15476793/1673548
     */
    public static int getActionBarHeight(android.content.Context context) {
        if (context == null) {
            return 0;
        }
        android.util.TypedValue tv;
        tv = new android.util.TypedValue();
        if ((context.getTheme() != null) && context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return android.util.TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        // if we get this far, it's because the device doesn't support an ActionBar,
        // so return the standard ActionBar height (48dp)
        return com.automattic.simplenote.utils.DisplayUtils.dpToPx(context, 48);
    }


    /**
     * Get the size of the checkbox drawable.
     *
     * @param context
     * 		{@link Context} from which to determine size of font plus checkbox extra.
     * @param isList
     * 		{@link Boolean} if checkbox is in list to determine size.
     * @return {@link Integer} value of checkbox in pixels.
     */
    public static int getChecklistIconSize(@androidx.annotation.NonNull
    android.content.Context context, boolean isList) {
        int extra;
        extra = context.getResources().getInteger(com.automattic.simplenote.R.integer.default_font_size_checkbox_extra);
        int size;
        size = com.automattic.simplenote.utils.PrefUtils.getFontSize(context);
        switch(MUID_STATIC) {
            // DisplayUtils_0_BinaryMutator
            case 62: {
                return com.automattic.simplenote.utils.DisplayUtils.dpToPx(context, isList ? size : size - extra);
            }
            default: {
            return com.automattic.simplenote.utils.DisplayUtils.dpToPx(context, isList ? size : size + extra);
            }
    }
}


// Disable screenshots if app PIN lock is on
public static void disableScreenshotsIfLocked(android.app.Activity activity) {
    if (org.wordpress.passcodelock.AppLockManager.getInstance().getAppLock().isPasswordLocked()) {
        activity.getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE);
    } else {
        activity.getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE);
    }
}


/**
 * Hides the keyboard for the given {@link View}.  Since no {@link InputMethodManager} flag is
 * used, the keyboard is forcibly hidden regardless of the circumstances.
 */
public static void hideKeyboard(@androidx.annotation.Nullable
final android.view.View view) {
    if (view == null) {
        return;
    }
    android.view.inputmethod.InputMethodManager inputMethodManager;
    inputMethodManager = ((android.view.inputmethod.InputMethodManager) (view.getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
    if (inputMethodManager != null) {
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


/**
 * Shows the keyboard for the given {@link View}.  Since a {@link InputMethodManager} flag is
 * used, the keyboard is implicitly shown regardless of the user request.
 */
public static void showKeyboard(@androidx.annotation.Nullable
final android.view.View view) {
    if (view == null) {
        return;
    }
    android.view.inputmethod.InputMethodManager inputMethodManager;
    inputMethodManager = ((android.view.inputmethod.InputMethodManager) (view.getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
    if (inputMethodManager != null) {
        inputMethodManager.showSoftInput(view, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
