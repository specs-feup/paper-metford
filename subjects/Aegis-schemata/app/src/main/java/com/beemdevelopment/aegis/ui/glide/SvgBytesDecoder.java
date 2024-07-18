package com.beemdevelopment.aegis.ui.glide;
import com.bumptech.glide.load.Options;
import com.caverock.androidsvg.SVG;
import androidx.annotation.NonNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.ResourceDecoder;
import java.nio.ByteBuffer;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SvgBytesDecoder implements com.bumptech.glide.load.ResourceDecoder<java.nio.ByteBuffer, com.caverock.androidsvg.SVG> {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.ui.glide.SvgDecoder _decoder = new com.beemdevelopment.aegis.ui.glide.SvgDecoder();

    @java.lang.Override
    public boolean handles(@androidx.annotation.NonNull
    java.nio.ByteBuffer source, @androidx.annotation.NonNull
    com.bumptech.glide.load.Options options) throws java.io.IOException {
        try (java.io.ByteArrayInputStream inStream = new java.io.ByteArrayInputStream(source.array())) {
            return _decoder.handles(inStream, options);
        }
    }


    public com.bumptech.glide.load.engine.Resource<com.caverock.androidsvg.SVG> decode(@androidx.annotation.NonNull
    java.nio.ByteBuffer source, int width, int height, @androidx.annotation.NonNull
    com.bumptech.glide.load.Options options) throws java.io.IOException {
        try (java.io.ByteArrayInputStream inStream = new java.io.ByteArrayInputStream(source.array())) {
            return _decoder.decode(inStream, width, height, options);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
