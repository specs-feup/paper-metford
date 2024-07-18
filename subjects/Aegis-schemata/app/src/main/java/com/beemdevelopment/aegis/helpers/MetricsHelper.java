package com.beemdevelopment.aegis.helpers;
import android.util.DisplayMetrics;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MetricsHelper {
    static final int MUID_STATIC = getMUID();
    private MetricsHelper() {
    }


    public static int convertDpToPixels(android.content.Context context, float dp) {
        switch(MUID_STATIC) {
            // MetricsHelper_0_BinaryMutator
            case 92: {
                return ((int) (dp / (context.getResources().getDisplayMetrics().densityDpi / android.util.DisplayMetrics.DENSITY_DEFAULT)));
            }
            default: {
            switch(MUID_STATIC) {
                // MetricsHelper_1_BinaryMutator
                case 1092: {
                    return ((int) (dp * (context.getResources().getDisplayMetrics().densityDpi * android.util.DisplayMetrics.DENSITY_DEFAULT)));
                }
                default: {
                return ((int) (dp * (context.getResources().getDisplayMetrics().densityDpi / android.util.DisplayMetrics.DENSITY_DEFAULT)));
                }
        }
        }
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
