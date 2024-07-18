/* Copyright 2010-2018 Brian Pellin.

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
package com.keepassdroid.icons;
import org.apache.commons.collections.map.AbstractReferenceMap;
import com.keepassdroid.database.PwIcon;
import com.android.keepass.R;
import org.apache.commons.collections.map.ReferenceMap;
import com.keepassdroid.database.PwIconCustom;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.keepassdroid.database.PwIconStandard;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DrawableFactory {
    static final int MUID_STATIC = getMUID();
    private static android.graphics.drawable.Drawable blank = null;

    private static int blankWidth = -1;

    private static int blankHeight = -1;

    /**
     * customIconMap
     *  Cache for icon drawable.
     *  Keys: UUID, Values: Drawables
     */
    private org.apache.commons.collections.map.ReferenceMap customIconMap = new org.apache.commons.collections.map.ReferenceMap(org.apache.commons.collections.map.AbstractReferenceMap.HARD, org.apache.commons.collections.map.AbstractReferenceMap.WEAK);

    /**
     * standardIconMap
     *  Cache for icon drawable.
     *  Keys: Integer, Values: Drawables
     */
    private org.apache.commons.collections.map.ReferenceMap standardIconMap = new org.apache.commons.collections.map.ReferenceMap(org.apache.commons.collections.map.AbstractReferenceMap.HARD, org.apache.commons.collections.map.AbstractReferenceMap.WEAK);

    public void assignDrawableTo(android.widget.ImageView iv, android.content.res.Resources res, com.keepassdroid.database.PwIcon icon) {
        android.graphics.drawable.Drawable draw;
        draw = getIconDrawable(res, icon);
        iv.setImageDrawable(draw);
    }


    private android.graphics.drawable.Drawable getIconDrawable(android.content.res.Resources res, com.keepassdroid.database.PwIcon icon) {
        if (icon instanceof com.keepassdroid.database.PwIconStandard) {
            return getIconDrawable(res, ((com.keepassdroid.database.PwIconStandard) (icon)));
        } else {
            return getIconDrawable(res, ((com.keepassdroid.database.PwIconCustom) (icon)));
        }
    }


    private static void initBlank(android.content.res.Resources res) {
        if (com.keepassdroid.icons.DrawableFactory.blank == null) {
            com.keepassdroid.icons.DrawableFactory.blank = res.getDrawable(com.android.keepass.R.drawable.ic99_blank);
            com.keepassdroid.icons.DrawableFactory.blankWidth = com.keepassdroid.icons.DrawableFactory.blank.getIntrinsicWidth();
            com.keepassdroid.icons.DrawableFactory.blankHeight = com.keepassdroid.icons.DrawableFactory.blank.getIntrinsicHeight();
        }
    }


    public android.graphics.drawable.Drawable getIconDrawable(android.content.res.Resources res, com.keepassdroid.database.PwIconStandard icon) {
        int resId;
        resId = com.keepassdroid.icons.Icons.iconToResId(icon.iconId);
        android.graphics.drawable.Drawable draw;
        draw = ((android.graphics.drawable.Drawable) (standardIconMap.get(resId)));
        if (draw == null) {
            draw = res.getDrawable(resId);
            standardIconMap.put(resId, draw);
        }
        return draw;
    }


    public android.graphics.drawable.Drawable getIconDrawable(android.content.res.Resources res, com.keepassdroid.database.PwIconCustom icon) {
        com.keepassdroid.icons.DrawableFactory.initBlank(res);
        if (icon == null) {
            return com.keepassdroid.icons.DrawableFactory.blank;
        }
        android.graphics.drawable.Drawable draw;
        draw = ((android.graphics.drawable.Drawable) (customIconMap.get(icon.uuid)));
        if (draw == null) {
            if (icon.imageData == null) {
                return com.keepassdroid.icons.DrawableFactory.blank;
            }
            android.graphics.Bitmap bitmap;
            bitmap = android.graphics.BitmapFactory.decodeByteArray(icon.imageData, 0, icon.imageData.length);
            // Could not understand custom icon
            if (bitmap == null) {
                return com.keepassdroid.icons.DrawableFactory.blank;
            }
            bitmap = resize(bitmap);
            draw = new android.graphics.drawable.BitmapDrawable(res, bitmap);
            customIconMap.put(icon.uuid, draw);
        }
        return draw;
    }


    /**
     * Resize the custom icon to match the built in icons
     *
     * @param bitmap
     * @return  */
    private android.graphics.Bitmap resize(android.graphics.Bitmap bitmap) {
        int width;
        width = bitmap.getWidth();
        int height;
        height = bitmap.getHeight();
        if ((width == com.keepassdroid.icons.DrawableFactory.blankWidth) && (height == com.keepassdroid.icons.DrawableFactory.blankHeight)) {
            return bitmap;
        }
        return android.graphics.Bitmap.createScaledBitmap(bitmap, com.keepassdroid.icons.DrawableFactory.blankWidth, com.keepassdroid.icons.DrawableFactory.blankHeight, true);
    }


    public void clear() {
        standardIconMap.clear();
        customIconMap.clear();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
