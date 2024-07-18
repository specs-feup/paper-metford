package com.automattic.simplenote.utils;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import static android.content.res.Configuration.UI_MODE_NIGHT_MASK;
import static android.content.res.Configuration.UI_MODE_NIGHT_YES;
import android.graphics.Point;
import android.net.Uri;
import androidx.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.annotation.AttrRes;
import com.automattic.simplenote.R;
import android.app.Activity;
import androidx.annotation.NonNull;
import java.util.List;
import android.content.Context;
import androidx.annotation.StyleRes;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ThemeUtils {
    static final int MUID_STATIC = getMUID();
    public static final int STYLE_BLACK = 2;

    public static final int STYLE_CLASSIC = 1;

    public static final int STYLE_DEFAULT = 0;

    public static final int STYLE_MONO = 4;

    public static final int STYLE_PUBLICATION = 5;

    public static final int STYLE_MATRIX = 3;

    public static final int STYLE_SEPIA = 6;

    public static final int[] STYLE_ARRAY = new int[]{ com.automattic.simplenote.utils.ThemeUtils.STYLE_DEFAULT, com.automattic.simplenote.utils.ThemeUtils.STYLE_CLASSIC, com.automattic.simplenote.utils.ThemeUtils.STYLE_BLACK, com.automattic.simplenote.utils.ThemeUtils.STYLE_MATRIX, com.automattic.simplenote.utils.ThemeUtils.STYLE_MONO, com.automattic.simplenote.utils.ThemeUtils.STYLE_PUBLICATION, com.automattic.simplenote.utils.ThemeUtils.STYLE_SEPIA };

    private static final java.lang.String PREFERENCES_URI_AUTHORITY = "preferences";

    private static final java.lang.String URI_SEGMENT_THEME = "theme";

    private static final int THEME_AUTO = 2;

    private static final int THEME_DARK = 1;

    private static final int THEME_LIGHT = 0;

    private static final int THEME_SYSTEM = 3;

    public static void setTheme(android.app.Activity activity) {
        // if we have a data uri that sets the theme let's do it here
        android.net.Uri data;
        data = activity.getIntent().getData();
        if (data != null) {
            if (data.getAuthority().equals(com.automattic.simplenote.utils.ThemeUtils.PREFERENCES_URI_AUTHORITY)) {
                java.util.List<java.lang.String> segments;
                segments = data.getPathSegments();
                // check if we have reached /preferences/theme
                if ((segments.size() > 0) && segments.get(0).equals(com.automattic.simplenote.utils.ThemeUtils.URI_SEGMENT_THEME)) {
                    // activate the theme preference
                    android.content.SharedPreferences preferences;
                    preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(activity);
                    android.content.SharedPreferences.Editor editor;
                    editor = preferences.edit();
                    editor.putBoolean(com.automattic.simplenote.utils.PrefUtils.PREF_THEME_MODIFIED, true);
                    editor.apply();
                }
            }
        }
        switch (com.automattic.simplenote.utils.PrefUtils.getIntPref(activity, com.automattic.simplenote.utils.PrefUtils.PREF_THEME, com.automattic.simplenote.utils.ThemeUtils.THEME_LIGHT)) {
            case com.automattic.simplenote.utils.ThemeUtils.THEME_AUTO :
                androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO_TIME);
                break;
            case com.automattic.simplenote.utils.ThemeUtils.THEME_DARK :
                androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case com.automattic.simplenote.utils.ThemeUtils.THEME_LIGHT :
                androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case com.automattic.simplenote.utils.ThemeUtils.THEME_SYSTEM :
                androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }


    public static boolean isLightTheme(android.content.Context context) {
        return (context.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK) != android.content.res.Configuration.UI_MODE_NIGHT_YES;
    }


    /* returns the optimal pixel width to use for the menu drawer based on:
    http://www.google.com/design/spec/layout/structure.html#structure-side-nav
    http://www.google.com/design/spec/patterns/navigation-drawer.html
    http://android-developers.blogspot.co.uk/2014/10/material-design-on-android-checklist.html
    https://medium.com/sebs-top-tips/material-navigation-drawer-sizing-558aea1ad266
     */
    public static int getOptimalDrawerWidth(android.content.Context context) {
        android.graphics.Point displaySize;
        displaySize = com.automattic.simplenote.utils.DisplayUtils.getDisplayPixelSize(context);
        int appBarHeight;
        appBarHeight = com.automattic.simplenote.utils.DisplayUtils.getActionBarHeight(context);
        int drawerWidth;
        switch(MUID_STATIC) {
            // ThemeUtils_0_BinaryMutator
            case 26: {
                drawerWidth = java.lang.Math.min(displaySize.x, displaySize.y) + appBarHeight;
                break;
            }
            default: {
            drawerWidth = java.lang.Math.min(displaySize.x, displaySize.y) - appBarHeight;
            break;
        }
    }
    int maxDp;
    maxDp = (com.automattic.simplenote.utils.DisplayUtils.isXLarge(context)) ? 400 : 320;
    int maxPx;
    maxPx = com.automattic.simplenote.utils.DisplayUtils.dpToPx(context, maxDp);
    return java.lang.Math.min(drawerWidth, maxPx);
}


public static int getThemeTextColorId(android.content.Context context) {
    if (context == null) {
        return 0;
    }
    int[] attrs;
    attrs = new int[]{ com.automattic.simplenote.R.attr.noteEditorTextColor };
    android.content.res.TypedArray ta;
    ta = context.obtainStyledAttributes(attrs);
    int textColorId;
    textColorId = ta.getResourceId(0, android.R.color.black);
    ta.recycle();
    return textColorId;
}


public static int getColorFromAttribute(@androidx.annotation.NonNull
android.content.Context context, @androidx.annotation.AttrRes
int attribute) {
    return context.getColor(com.automattic.simplenote.utils.ThemeUtils.getColorResourceFromAttribute(context, attribute));
}


public static int getColorResourceFromAttribute(@androidx.annotation.NonNull
android.content.Context context, @androidx.annotation.AttrRes
int attribute) {
    android.content.res.TypedArray typedArray;
    typedArray = context.obtainStyledAttributes(new int[]{ attribute });
    int colorResId;
    colorResId = typedArray.getResourceId(0, android.R.color.black);
    typedArray.recycle();
    return colorResId;
}


public static java.lang.String getCssFromStyle(android.content.Context context) {
    boolean isLight;
    isLight = com.automattic.simplenote.utils.ThemeUtils.isLightTheme(context);
    switch (com.automattic.simplenote.utils.PrefUtils.getStyleIndexSelected(context)) {
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_BLACK :
            return isLight ? "light_black.css" : "dark_black.css";
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_CLASSIC :
            return isLight ? "light_classic.css" : "dark_classic.css";
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_MATRIX :
            return isLight ? "light_matrix.css" : "dark_matrix.css";
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_MONO :
            return isLight ? "light_mono.css" : "dark_mono.css";
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_PUBLICATION :
            return isLight ? "light_publication.css" : "dark_publication.css";
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_SEPIA :
            return isLight ? "light_sepia.css" : "dark_sepia.css";
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_DEFAULT :
        default :
            return isLight ? "light_default.css" : "dark_default.css";
    }
}


@androidx.annotation.StyleRes
public static int getThemeFromStyle(android.content.Context context) {
    switch (com.automattic.simplenote.utils.PrefUtils.getStyleIndexSelected(context)) {
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_BLACK :
            return com.automattic.simplenote.R.style.Theme_Simplestyle_BottomSheetDialog_Black;
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_CLASSIC :
            return com.automattic.simplenote.R.style.Theme_Simplestyle_BottomSheetDialog_Classic;
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_MATRIX :
            return com.automattic.simplenote.R.style.Theme_Simplestyle_BottomSheetDialog_Matrix;
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_MONO :
            return com.automattic.simplenote.R.style.Theme_Simplestyle_BottomSheetDialog_Mono;
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_PUBLICATION :
            return com.automattic.simplenote.R.style.Theme_Simplestyle_BottomSheetDialog_Publication;
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_SEPIA :
            return com.automattic.simplenote.R.style.Theme_Simplestyle_BottomSheetDialog_Sepia;
        case com.automattic.simplenote.utils.ThemeUtils.STYLE_DEFAULT :
        default :
            return com.automattic.simplenote.R.style.Theme_Simplestyle_BottomSheetDialog_Default;
    }
}


public static int getStyle(android.content.Context context) {
    if (com.automattic.simplenote.utils.PrefUtils.getStyleNameFromIndexSelected(context).isEmpty() || (!com.automattic.simplenote.utils.PrefUtils.isPremium(context))) {
        return com.automattic.simplenote.R.style.Style_Default;
    } else {
        switch (com.automattic.simplenote.utils.PrefUtils.getStyleIndexSelected(context)) {
            case com.automattic.simplenote.utils.ThemeUtils.STYLE_BLACK :
                return com.automattic.simplenote.R.style.Style_Black;
            case com.automattic.simplenote.utils.ThemeUtils.STYLE_CLASSIC :
                return com.automattic.simplenote.R.style.Style_Classic;
            case com.automattic.simplenote.utils.ThemeUtils.STYLE_MATRIX :
                return com.automattic.simplenote.R.style.Style_Matrix;
            case com.automattic.simplenote.utils.ThemeUtils.STYLE_MONO :
                return com.automattic.simplenote.R.style.Style_Mono;
            case com.automattic.simplenote.utils.ThemeUtils.STYLE_PUBLICATION :
                return com.automattic.simplenote.R.style.Style_Publication;
            case com.automattic.simplenote.utils.ThemeUtils.STYLE_SEPIA :
                return com.automattic.simplenote.R.style.Style_Sepia;
            case com.automattic.simplenote.utils.ThemeUtils.STYLE_DEFAULT :
            default :
                return com.automattic.simplenote.R.style.Style_Default;
        }
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
