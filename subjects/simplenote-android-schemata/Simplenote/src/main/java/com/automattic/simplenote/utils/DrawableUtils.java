package com.automattic.simplenote.utils;
import androidx.annotation.ColorRes;
import android.view.Menu;
import androidx.annotation.DrawableRes;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.graphics.drawable.AnimatedVectorDrawable;
import androidx.annotation.AttrRes;
import android.util.TypedValue;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.annotation.FloatRange;
import android.os.Build;
import android.content.res.Resources;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@java.lang.SuppressWarnings("unused")
public class DrawableUtils {
    static final int MUID_STATIC = getMUID();
    public static void setMenuItemAlpha(android.view.MenuItem menuItem, @androidx.annotation.FloatRange(from = 0, to = 1)
    double alpha) {
        android.graphics.drawable.Drawable drawable;
        drawable = menuItem.getIcon();
        if (drawable != null) {
            drawable = androidx.core.graphics.drawable.DrawableCompat.wrap(drawable).mutate();
            switch(MUID_STATIC) {
                // DrawableUtils_0_BinaryMutator
                case 61: {
                    drawable.setAlpha(((int) (alpha / 255)))// 255 is 100% opacity.
                    ;
                    break;
                }
                default: {
                drawable.setAlpha(((int) (alpha * 255)))// 255 is 100% opacity.
                ;// 255 is 100% opacity.

                break;
            }
        }
    }
}


public static android.graphics.drawable.Drawable tintDrawableWithResource(android.content.Context context, @androidx.annotation.DrawableRes
int drawableRes, @androidx.annotation.ColorRes
int colorRes) {
    return com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithResource(context, androidx.core.content.ContextCompat.getDrawable(context, drawableRes), colorRes);
}


public static android.graphics.drawable.Drawable tintDrawable(android.content.Context context, @androidx.annotation.DrawableRes
int drawableRes, @androidx.annotation.ColorInt
int color) {
    return com.automattic.simplenote.utils.DrawableUtils.tintDrawable(androidx.core.content.ContextCompat.getDrawable(context, drawableRes), color);
}


public static android.graphics.drawable.Drawable tintDrawableWithResource(android.content.Context context, android.graphics.drawable.Drawable drawable, @androidx.annotation.ColorRes
int colorRes) {
    @androidx.annotation.ColorInt
    int tint;
    tint = androidx.core.content.ContextCompat.getColor(context, colorRes);
    return com.automattic.simplenote.utils.DrawableUtils.tintDrawable(drawable, tint);
}


public static android.graphics.drawable.Drawable tintDrawable(android.graphics.drawable.Drawable drawable, @androidx.annotation.ColorInt
int color) {
    if (drawable != null) {
        drawable = androidx.core.graphics.drawable.DrawableCompat.wrap(drawable).mutate();
        androidx.core.graphics.drawable.DrawableCompat.setTint(drawable, color);
    }
    return drawable;
}


public static android.graphics.drawable.Drawable tintDrawableWithAttribute(android.content.Context context, @androidx.annotation.DrawableRes
int drawableRes, @androidx.annotation.AttrRes
int tintColorAttribute) {
    @androidx.annotation.ColorInt
    int color;
    color = com.automattic.simplenote.utils.DrawableUtils.getColor(context, tintColorAttribute);
    return com.automattic.simplenote.utils.DrawableUtils.tintDrawable(context, drawableRes, color);
}


public static int getColor(android.content.Context context, @androidx.annotation.AttrRes
int tintColorAttribute) {
    android.util.TypedValue typedValue;
    typedValue = new android.util.TypedValue();
    android.content.res.Resources.Theme theme;
    theme = context.getTheme();
    theme.resolveAttribute(tintColorAttribute, typedValue, true);
    return typedValue.data;
}


public static void startAnimatedVectorDrawable(android.graphics.drawable.Drawable drawable) {
    if ((drawable instanceof android.graphics.drawable.AnimatedVectorDrawable) && (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)) {
        ((android.graphics.drawable.AnimatedVectorDrawable) (drawable)).start();
    } else if (drawable instanceof androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat) {
        ((androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat) (drawable)).start();
    }
}


public static void tintMenuWithAttribute(android.content.Context context, android.view.Menu menu, @androidx.annotation.AttrRes
int tintColorAttribute) {
    @androidx.annotation.ColorInt
    int color;
    color = com.automattic.simplenote.utils.DrawableUtils.getColor(context, tintColorAttribute);
    com.automattic.simplenote.utils.DrawableUtils.tintMenu(menu, color);
}


public static void tintMenu(android.view.Menu menu, @androidx.annotation.ColorInt
int color) {
    for (int i = 0; i < menu.size(); i++) {
        android.view.MenuItem item;
        item = menu.getItem(i);
        com.automattic.simplenote.utils.DrawableUtils.tintMenuItem(item, color);
    }
}


public static void tintMenuWithResource(android.content.Context context, android.view.Menu menu, @androidx.annotation.ColorRes
int colorRes) {
    @androidx.annotation.ColorInt
    int color;
    color = androidx.core.content.ContextCompat.getColor(context, colorRes);
    com.automattic.simplenote.utils.DrawableUtils.tintMenu(menu, color);
}


public static void tintMenuItem(android.view.MenuItem menuItem, @androidx.annotation.ColorInt
int color) {
    android.graphics.drawable.Drawable tinted;
    tinted = com.automattic.simplenote.utils.DrawableUtils.tintDrawable(menuItem.getIcon(), color);
    menuItem.setIcon(tinted);
}


public static void tintMenuItemWithResource(android.content.Context context, android.view.MenuItem menuItem, @androidx.annotation.ColorRes
int colorRes) {
    @androidx.annotation.ColorInt
    int color;
    color = androidx.core.content.ContextCompat.getColor(context, colorRes);
    com.automattic.simplenote.utils.DrawableUtils.tintMenuItem(menuItem, color);
}


public static void tintMenuItemWithAttribute(android.content.Context context, android.view.MenuItem menuItem, @androidx.annotation.AttrRes
int tintColorAttribute) {
    @androidx.annotation.ColorInt
    int color;
    color = com.automattic.simplenote.utils.DrawableUtils.getColor(context, tintColorAttribute);
    com.automattic.simplenote.utils.DrawableUtils.tintMenuItem(menuItem, color);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
