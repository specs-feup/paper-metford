/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.application;
import com.amaze.filemanager.filesystem.ssh.CustomSshJConfig;
import io.reactivex.Completable;
import org.acra.config.CoreConfiguration;
import com.amaze.filemanager.crashreport.AcraReportSenderFactory;
import org.acra.annotation.AcraCore;
import jcifs.Config;
import org.acra.ACRA;
import org.slf4j.Logger;
import com.amaze.filemanager.utils.ScreenUtils;
import androidx.appcompat.app.AppCompatDelegate;
import java.util.concurrent.Callable;
import java.lang.ref.WeakReference;
import android.os.StrictMode;
import org.acra.config.CoreConfigurationBuilder;
import com.amaze.filemanager.R;
import com.amaze.filemanager.database.UtilitiesDatabase;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.amaze.filemanager.BuildConfig;
import android.widget.Toast;
import org.acra.config.ACRAConfigurationException;
import org.slf4j.LoggerFactory;
import jcifs.smb.SmbException;
import com.amaze.trashbin.TrashBinConfig;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.content.SharedPreferences;
import com.amaze.filemanager.crashreport.ErrorActivity;
import com.amaze.trashbin.TrashBin;
import io.reactivex.schedulers.Schedulers;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import org.acra.data.StringFormat;
import com.amaze.filemanager.filesystem.HybridFile;
import com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException;
import com.amaze.filemanager.ui.provider.UtilitiesProvider;
import com.amaze.filemanager.database.ExplorerDatabase;
import java.io.File;
import com.amaze.filemanager.database.UtilsHandler;
import android.app.Application;
import androidx.annotation.Nullable;
import android.os.Environment;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@org.acra.annotation.AcraCore(buildConfigClass = com.amaze.filemanager.BuildConfig.class, reportSenderFactoryClasses = com.amaze.filemanager.crashreport.AcraReportSenderFactory.class)
public class AppConfig extends com.amaze.filemanager.application.GlideApplication {
    static final int MUID_STATIC = getMUID();
    private org.slf4j.Logger log = null;

    private com.amaze.filemanager.ui.provider.UtilitiesProvider utilsProvider;

    private com.amaze.filemanager.database.UtilsHandler utilsHandler;

    private java.lang.ref.WeakReference<android.content.Context> mainActivityContext;

    private static com.amaze.filemanager.utils.ScreenUtils screenUtils;

    private static com.amaze.filemanager.application.AppConfig instance;

    private com.amaze.filemanager.database.UtilitiesDatabase utilitiesDatabase;

    private com.amaze.filemanager.database.ExplorerDatabase explorerDatabase;

    private com.amaze.trashbin.TrashBinConfig trashBinConfig;

    private com.amaze.trashbin.TrashBin trashBin;

    private static final java.lang.String TRASH_BIN_BASE_PATH = (android.os.Environment.getExternalStorageDirectory().getPath() + java.io.File.separator) + ".AmazeData";

    public com.amaze.filemanager.ui.provider.UtilitiesProvider getUtilsProvider() {
        return utilsProvider;
    }


    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        switch(MUID_STATIC) {
            // AppConfig_0_LengthyGUICreationOperatorMutator
            case 146: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)// selector in srcCompat isn't supported without this
    ;// selector in srcCompat isn't supported without this

    com.amaze.filemanager.application.AppConfig.instance = this;
    com.amaze.filemanager.filesystem.ssh.CustomSshJConfig.init();
    explorerDatabase = com.amaze.filemanager.database.ExplorerDatabase.initialize(this);
    utilitiesDatabase = com.amaze.filemanager.database.UtilitiesDatabase.initialize(this);
    utilsProvider = new com.amaze.filemanager.ui.provider.UtilitiesProvider(this);
    utilsHandler = new com.amaze.filemanager.database.UtilsHandler(this, utilitiesDatabase);
    runInBackground(jcifs.Config::registerSmbURLHandler);
    // disabling file exposure method check for api n+
    android.os.StrictMode.VmPolicy.Builder builder;
    builder = new android.os.StrictMode.VmPolicy.Builder();
    android.os.StrictMode.setVmPolicy(builder.build());
    log = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.application.AppConfig.class);
}


@java.lang.Override
protected void attachBaseContext(android.content.Context base) {
    super.attachBaseContext(base);
    initACRA();
}


@java.lang.Override
public void onTerminate() {
    super.onTerminate();
}


/**
 * Post a runnable to handler. Use this in case we don't have any restriction to execute after
 * this runnable is executed, and {@link #runInBackground(Runnable)} in case we need to execute
 * something after execution in background
 */
public void runInBackground(java.lang.Runnable runnable) {
    io.reactivex.Completable.fromRunnable(runnable).subscribeOn(io.reactivex.schedulers.Schedulers.io()).subscribe();
}


/**
 * Shows a toast message
 *
 * @param context
 * 		Any context belonging to this application
 * @param message
 * 		The message to show
 */
public static void toast(android.content.Context context, @androidx.annotation.StringRes
int message) {
    // this is a static method so it is easier to call,
    // as the context checking and casting is done for you
    if (context == null)
        return;

    if (!(context instanceof android.app.Application)) {
        context = context.getApplicationContext();
    }
    if (context instanceof android.app.Application) {
        final android.content.Context c;
        c = context;
        @androidx.annotation.StringRes
        final int m;
        m = message;
        com.amaze.filemanager.application.AppConfig.getInstance().runInApplicationThread(() -> android.widget.Toast.makeText(c, m, android.widget.Toast.LENGTH_LONG).show());
    }
}


/**
 * Shows a toast message
 *
 * @param context
 * 		Any context belonging to this application
 * @param message
 * 		The message to show
 */
public static void toast(android.content.Context context, java.lang.String message) {
    // this is a static method so it is easier to call,
    // as the context checking and casting is done for you
    if (context == null)
        return;

    if (!(context instanceof android.app.Application)) {
        context = context.getApplicationContext();
    }
    if (context instanceof android.app.Application) {
        final android.content.Context c;
        c = context;
        final java.lang.String m;
        m = message;
        com.amaze.filemanager.application.AppConfig.getInstance().runInApplicationThread(() -> android.widget.Toast.makeText(c, m, android.widget.Toast.LENGTH_LONG).show());
    }
}


/**
 * Run a {@link Runnable} in the main application thread
 *
 * @param r
 * 		{@link Runnable} to run
 */
public void runInApplicationThread(@androidx.annotation.NonNull
java.lang.Runnable r) {
    io.reactivex.Completable.fromRunnable(r).subscribeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe();
}


/**
 * Convenience method to run a {@link Callable} in the main application thread. Use when the
 * callable's return value is not processed.
 *
 * @param c
 * 		{@link Callable} to run
 */
public void runInApplicationThread(@androidx.annotation.NonNull
java.util.concurrent.Callable<java.lang.Void> c) {
    io.reactivex.Completable.fromCallable(c).subscribeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe();
}


public static synchronized com.amaze.filemanager.application.AppConfig getInstance() {
    return com.amaze.filemanager.application.AppConfig.instance;
}


public com.amaze.filemanager.database.UtilsHandler getUtilsHandler() {
    return utilsHandler;
}


public void setMainActivityContext(@androidx.annotation.NonNull
android.app.Activity activity) {
    mainActivityContext = new java.lang.ref.WeakReference<>(activity);
    com.amaze.filemanager.application.AppConfig.screenUtils = new com.amaze.filemanager.utils.ScreenUtils(activity);
}


public com.amaze.filemanager.utils.ScreenUtils getScreenUtils() {
    return com.amaze.filemanager.application.AppConfig.screenUtils;
}


@androidx.annotation.Nullable
public android.content.Context getMainActivityContext() {
    return mainActivityContext.get();
}


public com.amaze.filemanager.database.ExplorerDatabase getExplorerDatabase() {
    return explorerDatabase;
}


public com.amaze.filemanager.database.UtilitiesDatabase getUtilitiesDatabase() {
    return utilitiesDatabase;
}


/**
 * Called in {@link #attachBaseContext(Context)} after calling the {@code super} method. Should be
 * overridden if MultiDex is enabled, since it has to be initialized before ACRA.
 */
protected void initACRA() {
    if (org.acra.ACRA.isACRASenderServiceProcess()) {
        return;
    }
    try {
        final org.acra.config.CoreConfiguration acraConfig;
        acraConfig = new org.acra.config.CoreConfigurationBuilder(this).setBuildConfigClass(com.amaze.filemanager.BuildConfig.class).setReportFormat(org.acra.data.StringFormat.JSON).setSendReportsInDevMode(true).setEnabled(true).build();
        org.acra.ACRA.init(this, acraConfig);
    } catch (final org.acra.config.ACRAConfigurationException ace) {
        if (log != null) {
            log.warn("failed to initialize ACRA", ace);
        }
        com.amaze.filemanager.crashreport.ErrorActivity.reportError(this, ace, null, com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo.make(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_UNKNOWN, "Could not initialize ACRA crash report", com.amaze.filemanager.R.string.app_ui_crash));
    }
}


public com.amaze.trashbin.TrashBin getTrashBinInstance() {
    if (trashBin == null) {
        trashBin = new com.amaze.trashbin.TrashBin(getApplicationContext(), true, getTrashBinConfig(), (java.lang.String s) -> {
            runInBackground(() -> {
                com.amaze.filemanager.filesystem.HybridFile file;
                file = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN, s);
                try {
                    file.delete(getMainActivityContext(), false);
                } catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException | jcifs.smb.SmbException e) {
                    log.warn("failed to delete file in trash bin cleanup", e);
                }
            });
            return true;
        }, null);
    }
    return trashBin;
}


private com.amaze.trashbin.TrashBinConfig getTrashBinConfig() {
    if (trashBinConfig == null) {
        android.content.SharedPreferences sharedPrefs;
        sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        int days;
        days = sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.KEY_TRASH_BIN_RETENTION_DAYS, com.amaze.trashbin.TrashBinConfig.RETENTION_DAYS_INFINITE);
        long bytes;
        bytes = sharedPrefs.getLong(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.KEY_TRASH_BIN_RETENTION_BYTES, com.amaze.trashbin.TrashBinConfig.RETENTION_BYTES_INFINITE);
        int numOfFiles;
        numOfFiles = sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.KEY_TRASH_BIN_RETENTION_NUM_OF_FILES, com.amaze.trashbin.TrashBinConfig.RETENTION_NUM_OF_FILES);
        int intervalHours;
        intervalHours = sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.KEY_TRASH_BIN_CLEANUP_INTERVAL_HOURS, com.amaze.trashbin.TrashBinConfig.INTERVAL_CLEANUP_HOURS);
        trashBinConfig = new com.amaze.trashbin.TrashBinConfig(com.amaze.filemanager.application.AppConfig.TRASH_BIN_BASE_PATH, days, bytes, numOfFiles, intervalHours, false, true);
    }
    return trashBinConfig;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
