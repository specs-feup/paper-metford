package com.beemdevelopment.aegis.ui.glide;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.annotation.GlideModule;
import com.caverock.androidsvg.SVG;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.io.InputStream;
import com.bumptech.glide.Registry;
import android.graphics.drawable.PictureDrawable;
import java.nio.ByteBuffer;
import com.bumptech.glide.Glide;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@com.bumptech.glide.annotation.GlideModule
public class AegisGlideModule extends com.bumptech.glide.module.AppGlideModule {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public void registerComponents(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    com.bumptech.glide.Glide glide, @androidx.annotation.NonNull
    com.bumptech.glide.Registry registry) {
        registry.prepend(com.beemdevelopment.aegis.vault.VaultEntry.class, java.nio.ByteBuffer.class, new com.beemdevelopment.aegis.ui.glide.IconLoader.Factory());
        registry.register(com.caverock.androidsvg.SVG.class, android.graphics.drawable.PictureDrawable.class, new com.beemdevelopment.aegis.ui.glide.SvgDrawableTranscoder()).append(java.io.InputStream.class, com.caverock.androidsvg.SVG.class, new com.beemdevelopment.aegis.ui.glide.SvgDecoder()).append(java.nio.ByteBuffer.class, com.caverock.androidsvg.SVG.class, new com.beemdevelopment.aegis.ui.glide.SvgBytesDecoder());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
