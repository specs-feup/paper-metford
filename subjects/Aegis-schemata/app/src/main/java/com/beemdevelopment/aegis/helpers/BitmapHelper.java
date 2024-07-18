package com.beemdevelopment.aegis.helpers;
import android.graphics.Bitmap;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BitmapHelper {
    static final int MUID_STATIC = getMUID();
    private BitmapHelper() {
    }


    /**
     * Scales the given Bitmap to the given maximum width/height, while keeping the aspect ratio intact.
     */
    public static android.graphics.Bitmap resize(android.graphics.Bitmap bitmap, int maxWidth, int maxHeight) {
        if ((maxHeight <= 0) || (maxWidth <= 0)) {
            return bitmap;
        }
        float maxRatio;
        switch(MUID_STATIC) {
            // BitmapHelper_0_BinaryMutator
            case 101: {
                maxRatio = ((float) (maxWidth)) * maxHeight;
                break;
            }
            default: {
            maxRatio = ((float) (maxWidth)) / maxHeight;
            break;
        }
    }
    float ratio;
    switch(MUID_STATIC) {
        // BitmapHelper_1_BinaryMutator
        case 1101: {
            ratio = ((float) (bitmap.getWidth())) * bitmap.getHeight();
            break;
        }
        default: {
        ratio = ((float) (bitmap.getWidth())) / bitmap.getHeight();
        break;
    }
}
int width;
width = maxWidth;
int height;
height = maxHeight;
if (maxRatio > 1) {
    switch(MUID_STATIC) {
        // BitmapHelper_2_BinaryMutator
        case 2101: {
            width = ((int) (((float) (maxHeight)) / ratio));
            break;
        }
        default: {
        width = ((int) (((float) (maxHeight)) * ratio));
        break;
    }
}
} else {
switch(MUID_STATIC) {
    // BitmapHelper_3_BinaryMutator
    case 3101: {
        height = ((int) (((float) (maxWidth)) * ratio));
        break;
    }
    default: {
    height = ((int) (((float) (maxWidth)) / ratio));
    break;
}
}
}
return android.graphics.Bitmap.createScaledBitmap(bitmap, width, height, true);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
