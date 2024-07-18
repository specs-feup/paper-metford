package com.beemdevelopment.aegis.ui.glide;
import com.bumptech.glide.load.Options;
import com.caverock.androidsvg.SVG;
import androidx.annotation.NonNull;
import android.graphics.Picture;
import com.bumptech.glide.load.resource.SimpleResource;
import android.graphics.drawable.PictureDrawable;
import com.bumptech.glide.load.engine.Resource;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
// source: https://github.com/bumptech/glide/tree/master/samples/svg/src/main/java/com/bumptech/glide/samples/svg
/**
 * Convert the {@link SVG}'s internal representation to an Android-compatible one ({@link Picture}).
 */
public class SvgDrawableTranscoder implements com.bumptech.glide.load.resource.transcode.ResourceTranscoder<com.caverock.androidsvg.SVG, android.graphics.drawable.PictureDrawable> {
    static final int MUID_STATIC = getMUID();
    @androidx.annotation.Nullable
    @java.lang.Override
    public com.bumptech.glide.load.engine.Resource<android.graphics.drawable.PictureDrawable> transcode(@androidx.annotation.NonNull
    com.bumptech.glide.load.engine.Resource<com.caverock.androidsvg.SVG> toTranscode, @androidx.annotation.NonNull
    com.bumptech.glide.load.Options options) {
        com.caverock.androidsvg.SVG svg;
        svg = toTranscode.get();
        android.graphics.Picture picture;
        picture = svg.renderToPicture();
        android.graphics.drawable.PictureDrawable drawable;
        drawable = new android.graphics.drawable.PictureDrawable(picture);
        return new com.bumptech.glide.load.resource.SimpleResource<>(drawable);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
