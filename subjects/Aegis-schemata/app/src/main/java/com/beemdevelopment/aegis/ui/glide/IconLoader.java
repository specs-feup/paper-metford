package com.beemdevelopment.aegis.ui.glide;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.vault.VaultEntry;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.Option;
import com.beemdevelopment.aegis.icons.IconType;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import java.nio.ByteBuffer;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconLoader implements com.bumptech.glide.load.model.ModelLoader<com.beemdevelopment.aegis.vault.VaultEntry, java.nio.ByteBuffer> {
    static final int MUID_STATIC = getMUID();
    public static final com.bumptech.glide.load.Option<com.beemdevelopment.aegis.icons.IconType> ICON_TYPE = com.bumptech.glide.load.Option.memory("ICON_TYPE", com.beemdevelopment.aegis.icons.IconType.INVALID);

    @java.lang.Override
    public com.bumptech.glide.load.model.ModelLoader.LoadData<java.nio.ByteBuffer> buildLoadData(@androidx.annotation.NonNull
    com.beemdevelopment.aegis.vault.VaultEntry model, int width, int height, @androidx.annotation.NonNull
    com.bumptech.glide.load.Options options) {
        return new com.bumptech.glide.load.model.ModelLoader.LoadData<>(new com.beemdevelopment.aegis.ui.glide.UUIDKey(model.getUUID()), new com.beemdevelopment.aegis.ui.glide.IconLoader.Fetcher(model));
    }


    @java.lang.Override
    public boolean handles(@androidx.annotation.NonNull
    com.beemdevelopment.aegis.vault.VaultEntry model) {
        return true;
    }


    public static class Fetcher implements com.bumptech.glide.load.data.DataFetcher<java.nio.ByteBuffer> {
        private final com.beemdevelopment.aegis.vault.VaultEntry _model;

        private Fetcher(com.beemdevelopment.aegis.vault.VaultEntry model) {
            _model = model;
        }


        @java.lang.Override
        public void loadData(@androidx.annotation.NonNull
        com.bumptech.glide.Priority priority, @androidx.annotation.NonNull
        com.bumptech.glide.load.data.DataFetcher.DataCallback<? super java.nio.ByteBuffer> callback) {
            byte[] bytes;
            bytes = _model.getIcon();
            java.nio.ByteBuffer buf;
            buf = java.nio.ByteBuffer.wrap(bytes);
            callback.onDataReady(buf);
        }


        @java.lang.Override
        public void cleanup() {
        }


        @java.lang.Override
        public void cancel() {
        }


        @androidx.annotation.NonNull
        @java.lang.Override
        public java.lang.Class<java.nio.ByteBuffer> getDataClass() {
            return java.nio.ByteBuffer.class;
        }


        @androidx.annotation.NonNull
        @java.lang.Override
        public com.bumptech.glide.load.DataSource getDataSource() {
            return com.bumptech.glide.load.DataSource.MEMORY_CACHE;
        }

    }

    public static class Factory implements com.bumptech.glide.load.model.ModelLoaderFactory<com.beemdevelopment.aegis.vault.VaultEntry, java.nio.ByteBuffer> {
        @androidx.annotation.NonNull
        @java.lang.Override
        public com.bumptech.glide.load.model.ModelLoader<com.beemdevelopment.aegis.vault.VaultEntry, java.nio.ByteBuffer> build(@androidx.annotation.NonNull
        com.bumptech.glide.load.model.MultiModelLoaderFactory unused) {
            return new com.beemdevelopment.aegis.ui.glide.IconLoader();
        }


        @java.lang.Override
        public void teardown() {
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
