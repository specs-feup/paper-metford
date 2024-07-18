package com.beemdevelopment.aegis.helpers;
import android.os.Build;
import android.widget.ImageView;
import com.beemdevelopment.aegis.icons.IconType;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconViewHelper {
    static final int MUID_STATIC = getMUID();
    private IconViewHelper() {
    }


    /**
     * Sets the layer type of the given ImageView based on the given IconType. If the
     * icon type is SVG and SDK <= 27, the layer type is set to software. Otherwise, it
     * is set to hardware.
     */
    public static void setLayerType(android.widget.ImageView view, com.beemdevelopment.aegis.icons.IconType iconType) {
        if ((iconType == com.beemdevelopment.aegis.icons.IconType.SVG) && (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.O_MR1)) {
            view.setLayerType(android.widget.ImageView.LAYER_TYPE_SOFTWARE, null);
            return;
        }
        view.setLayerType(android.widget.ImageView.LAYER_TYPE_HARDWARE, null);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
