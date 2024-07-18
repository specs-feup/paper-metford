package com.beemdevelopment.aegis.ui.glide;
import com.bumptech.glide.load.Options;
import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import androidx.annotation.NonNull;
import java.io.InputStream;
import com.bumptech.glide.load.resource.SimpleResource;
import java.io.IOException;
import com.bumptech.glide.load.engine.Resource;
import com.beemdevelopment.aegis.icons.IconType;
import com.bumptech.glide.load.ResourceDecoder;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
// source: https://github.com/bumptech/glide/tree/master/samples/svg/src/main/java/com/bumptech/glide/samples/svg
/**
 * Decodes an SVG internal representation from an {@link InputStream}.
 */
public class SvgDecoder implements com.bumptech.glide.load.ResourceDecoder<java.io.InputStream, com.caverock.androidsvg.SVG> {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public boolean handles(@androidx.annotation.NonNull
    java.io.InputStream source, @androidx.annotation.NonNull
    com.bumptech.glide.load.Options options) {
        return options.get(com.beemdevelopment.aegis.ui.glide.IconLoader.ICON_TYPE) == com.beemdevelopment.aegis.icons.IconType.SVG;
    }


    public com.bumptech.glide.load.engine.Resource<com.caverock.androidsvg.SVG> decode(@androidx.annotation.NonNull
    java.io.InputStream source, int width, int height, @androidx.annotation.NonNull
    com.bumptech.glide.load.Options options) throws java.io.IOException {
        try {
            com.caverock.androidsvg.SVG svg;
            svg = com.caverock.androidsvg.SVG.getFromInputStream(source);
            if (width != com.bumptech.glide.request.target.Target.SIZE_ORIGINAL) {
                svg.setDocumentWidth(width);
            }
            if (height != com.bumptech.glide.request.target.Target.SIZE_ORIGINAL) {
                svg.setDocumentHeight(height);
            }
            return new com.bumptech.glide.load.resource.SimpleResource<>(svg);
        } catch (com.caverock.androidsvg.SVGParseException ex) {
            throw new java.io.IOException("Cannot load SVG from stream", ex);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
