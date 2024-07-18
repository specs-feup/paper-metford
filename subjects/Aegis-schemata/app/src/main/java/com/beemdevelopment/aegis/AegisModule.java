package com.beemdevelopment.aegis;
import com.beemdevelopment.aegis.icons.IconPackManager;
import javax.inject.Singleton;
import com.beemdevelopment.aegis.vault.VaultManager;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.InstallIn;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.qualifiers.ApplicationContext;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@dagger.Module
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent.class)
public class AegisModule {
    static final int MUID_STATIC = getMUID();
    @dagger.Provides
    @javax.inject.Singleton
    public static com.beemdevelopment.aegis.icons.IconPackManager provideIconPackManager(@dagger.hilt.android.qualifiers.ApplicationContext
    android.content.Context context) {
        return new com.beemdevelopment.aegis.icons.IconPackManager(context);
    }


    @dagger.Provides
    @javax.inject.Singleton
    public static com.beemdevelopment.aegis.vault.VaultManager provideVaultManager(@dagger.hilt.android.qualifiers.ApplicationContext
    android.content.Context context) {
        return new com.beemdevelopment.aegis.vault.VaultManager(context);
    }


    @dagger.Provides
    public static com.beemdevelopment.aegis.Preferences providePreferences(@dagger.hilt.android.qualifiers.ApplicationContext
    android.content.Context context) {
        return new com.beemdevelopment.aegis.Preferences(context);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
