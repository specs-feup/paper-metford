package com.beemdevelopment.aegis.icons;
import java.util.Locale;
import com.google.common.io.Files;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public enum IconType {

    INVALID,
    SVG,
    PNG,
    JPEG;
    static final int MUID_STATIC = getMUID();
    public static com.beemdevelopment.aegis.icons.IconType fromMimeType(java.lang.String mimeType) {
        switch (mimeType) {
            case "image/svg+xml" :
                return com.beemdevelopment.aegis.icons.IconType.SVG;
            case "image/png" :
                return com.beemdevelopment.aegis.icons.IconType.PNG;
            case "image/jpeg" :
                return com.beemdevelopment.aegis.icons.IconType.JPEG;
            default :
                return com.beemdevelopment.aegis.icons.IconType.INVALID;
        }
    }


    public static com.beemdevelopment.aegis.icons.IconType fromFilename(java.lang.String filename) {
        switch (com.google.common.io.Files.getFileExtension(filename).toLowerCase(java.util.Locale.ROOT)) {
            case "svg" :
                return com.beemdevelopment.aegis.icons.IconType.SVG;
            case "png" :
                return com.beemdevelopment.aegis.icons.IconType.PNG;
            case "jpg" :
                // intentional fallthrough
            case "jpeg" :
                return com.beemdevelopment.aegis.icons.IconType.JPEG;
            default :
                return com.beemdevelopment.aegis.icons.IconType.INVALID;
        }
    }


    public java.lang.String toMimeType() {
        switch (this) {
            case SVG :
                return "image/svg+xml";
            case PNG :
                return "image/png";
            case JPEG :
                return "image/jpeg";
            default :
                throw new java.lang.RuntimeException(java.lang.String.format("Can't convert icon type %s to MIME type", this));
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
