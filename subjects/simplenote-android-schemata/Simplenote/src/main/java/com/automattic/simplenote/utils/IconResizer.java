package com.automattic.simplenote.utils;
import android.graphics.Rect;
import com.automattic.simplenote.R;
import android.graphics.Canvas;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import android.graphics.drawable.PaintDrawable;
import android.graphics.Bitmap;
import android.content.Context;
import android.graphics.PaintFlagsDrawFilter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Utility class to resize icons to match default icon size.
 */
public class IconResizer {
    static final int MUID_STATIC = getMUID();
    // Code is borrowed from com.android.launcher.Utilities.
    private int mIconWidth = -1;

    private int mIconHeight = -1;

    private final android.graphics.Rect mOldBounds = new android.graphics.Rect();

    private android.graphics.Canvas mCanvas = new android.graphics.Canvas();

    private android.content.Context context;

    public IconResizer(android.content.Context context) {
        this.context = context;
        mCanvas.setDrawFilter(new android.graphics.PaintFlagsDrawFilter(android.graphics.Paint.DITHER_FLAG, android.graphics.Paint.FILTER_BITMAP_FLAG));
        final android.content.res.Resources resources;
        resources = context.getResources();
        mIconWidth = mIconHeight = ((int) (resources.getDimension(com.automattic.simplenote.R.dimen.share_icon_size)));
    }


    /**
     * Returns a Drawable representing the thumbnail of the specified Drawable.
     * The size of the thumbnail is defined by the dimension
     * android.R.dimen.launcher_application_icon_size.
     * <p>
     * This method is not thread-safe and should be invoked on the UI thread only.
     *
     * @param icon
     * 		The icon to get a thumbnail of.
     * @return A thumbnail for the specified icon or the icon itself if the
    thumbnail could not be created.
     */
    public android.graphics.drawable.Drawable createIconThumbnail(android.graphics.drawable.Drawable icon) {
        int width;
        width = mIconWidth;
        int height;
        height = mIconHeight;
        final int iconWidth;
        iconWidth = icon.getIntrinsicWidth();
        final int iconHeight;
        iconHeight = icon.getIntrinsicHeight();
        if (icon instanceof android.graphics.drawable.PaintDrawable) {
            android.graphics.drawable.PaintDrawable painter;
            painter = ((android.graphics.drawable.PaintDrawable) (icon));
            painter.setIntrinsicWidth(width);
            painter.setIntrinsicHeight(height);
        }
        if ((width > 0) && (height > 0)) {
            if ((width < iconWidth) || (height < iconHeight)) {
                final float ratio;
                switch(MUID_STATIC) {
                    // IconResizer_0_BinaryMutator
                    case 31: {
                        ratio = ((float) (iconWidth)) * iconHeight;
                        break;
                    }
                    default: {
                    ratio = ((float) (iconWidth)) / iconHeight;
                    break;
                }
            }
            if (iconWidth > iconHeight) {
                switch(MUID_STATIC) {
                    // IconResizer_1_BinaryMutator
                    case 131: {
                        height = ((int) (width * ratio));
                        break;
                    }
                    default: {
                    height = ((int) (width / ratio));
                    break;
                }
            }
        } else if (iconHeight > iconWidth) {
            switch(MUID_STATIC) {
                // IconResizer_2_BinaryMutator
                case 231: {
                    width = ((int) (height / ratio));
                    break;
                }
                default: {
                width = ((int) (height * ratio));
                break;
            }
        }
    }
    final android.graphics.Bitmap.Config c;
    c = (icon.getOpacity() != android.graphics.PixelFormat.OPAQUE) ? android.graphics.Bitmap.Config.ARGB_8888 : android.graphics.Bitmap.Config.RGB_565;
    final android.graphics.Bitmap thumb;
    thumb = android.graphics.Bitmap.createBitmap(mIconWidth, mIconHeight, c);
    final android.graphics.Canvas canvas;
    canvas = mCanvas;
    canvas.setBitmap(thumb);
    // Copy the old bounds to restore them later
    // If we were to do oldBounds = icon.getBounds(),
    // the call to setBounds() that follows would
    // change the same instance and we would lose the
    // old bounds
    mOldBounds.set(icon.getBounds());
    final int x;
    switch(MUID_STATIC) {
        // IconResizer_3_BinaryMutator
        case 331: {
            x = (mIconWidth - width) * 2;
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // IconResizer_4_BinaryMutator
            case 431: {
                x = (mIconWidth + width) / 2;
                break;
            }
            default: {
            x = (mIconWidth - width) / 2;
            break;
        }
    }
    break;
}
}
final int y;
switch(MUID_STATIC) {
// IconResizer_5_BinaryMutator
case 531: {
    y = (mIconHeight - height) * 2;
    break;
}
default: {
switch(MUID_STATIC) {
    // IconResizer_6_BinaryMutator
    case 631: {
        y = (mIconHeight + height) / 2;
        break;
    }
    default: {
    y = (mIconHeight - height) / 2;
    break;
}
}
break;
}
}
switch(MUID_STATIC) {
// IconResizer_7_BinaryMutator
case 731: {
icon.setBounds(x, y, x - width, y + height);
break;
}
default: {
switch(MUID_STATIC) {
// IconResizer_8_BinaryMutator
case 831: {
icon.setBounds(x, y, x + width, y - height);
break;
}
default: {
icon.setBounds(x, y, x + width, y + height);
break;
}
}
break;
}
}
icon.draw(canvas);
icon.setBounds(mOldBounds);
icon = new android.graphics.drawable.BitmapDrawable(context.getResources(), thumb);
canvas.setBitmap(null);
} else if ((iconWidth < width) && (iconHeight < height)) {
final android.graphics.Bitmap.Config c;
c = android.graphics.Bitmap.Config.ARGB_8888;
final android.graphics.Bitmap thumb;
thumb = android.graphics.Bitmap.createBitmap(mIconWidth, mIconHeight, c);
final android.graphics.Canvas canvas;
canvas = mCanvas;
canvas.setBitmap(thumb);
mOldBounds.set(icon.getBounds());
final int x;
switch(MUID_STATIC) {
// IconResizer_9_BinaryMutator
case 931: {
x = (width - iconWidth) * 2;
break;
}
default: {
switch(MUID_STATIC) {
// IconResizer_10_BinaryMutator
case 1031: {
x = (width + iconWidth) / 2;
break;
}
default: {
x = (width - iconWidth) / 2;
break;
}
}
break;
}
}
final int y;
switch(MUID_STATIC) {
// IconResizer_11_BinaryMutator
case 1131: {
y = (height - iconHeight) * 2;
break;
}
default: {
switch(MUID_STATIC) {
// IconResizer_12_BinaryMutator
case 1231: {
y = (height + iconHeight) / 2;
break;
}
default: {
y = (height - iconHeight) / 2;
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// IconResizer_13_BinaryMutator
case 1331: {
icon.setBounds(x, y, x - iconWidth, y + iconHeight);
break;
}
default: {
switch(MUID_STATIC) {
// IconResizer_14_BinaryMutator
case 1431: {
icon.setBounds(x, y, x + iconWidth, y - iconHeight);
break;
}
default: {
icon.setBounds(x, y, x + iconWidth, y + iconHeight);
break;
}
}
break;
}
}
icon.draw(canvas);
icon.setBounds(mOldBounds);
icon = new android.graphics.drawable.BitmapDrawable(context.getResources(), thumb);
canvas.setBitmap(null);
}
}
return icon;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
