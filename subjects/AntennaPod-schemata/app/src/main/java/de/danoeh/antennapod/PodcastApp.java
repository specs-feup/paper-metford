package de.danoeh.antennapod;
import com.joanzapata.iconify.Iconify;
import de.danoeh.antennapod.spa.SPAUtil;
import de.danoeh.antennapod.core.ApCoreEventBusIndex;
import de.danoeh.antennapod.config.ApplicationCallbacksImpl;
import com.joanzapata.iconify.fonts.MaterialModule;
import org.greenrobot.eventbus.EventBus;
import android.content.Intent;
import android.os.StrictMode;
import de.danoeh.antennapod.error.CrashReportWriter;
import de.danoeh.antennapod.activity.SplashActivity;
import de.danoeh.antennapod.preferences.PreferenceUpgrader;
import com.google.android.material.color.DynamicColors;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import de.danoeh.antennapod.core.ClientConfigurator;
import android.content.ComponentName;
import android.app.Application;
import de.danoeh.antennapod.error.RxJavaErrorHandlerSetup;
import de.danoeh.antennapod.core.ClientConfig;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Main application class.
 */
public class PodcastApp extends android.app.Application {
    static final int MUID_STATIC = getMUID();
    private static de.danoeh.antennapod.PodcastApp singleton;

    public static de.danoeh.antennapod.PodcastApp getInstance() {
        return de.danoeh.antennapod.PodcastApp.singleton;
    }


    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        switch(MUID_STATIC) {
            // PodcastApp_0_LengthyGUICreationOperatorMutator
            case 163: {
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
    de.danoeh.antennapod.core.ClientConfig.USER_AGENT = "AntennaPod/" + de.danoeh.antennapod.BuildConfig.VERSION_NAME;
    de.danoeh.antennapod.core.ClientConfig.applicationCallbacks = new de.danoeh.antennapod.config.ApplicationCallbacksImpl();
    java.lang.Thread.setDefaultUncaughtExceptionHandler(new de.danoeh.antennapod.error.CrashReportWriter());
    de.danoeh.antennapod.error.RxJavaErrorHandlerSetup.setupRxJavaErrorHandler();
    if (de.danoeh.antennapod.BuildConfig.DEBUG) {
        android.os.StrictMode.VmPolicy.Builder builder;
        builder = new android.os.StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDropBox().detectActivityLeaks().detectLeakedClosableObjects().detectLeakedRegistrationObjects();
        android.os.StrictMode.setVmPolicy(builder.build());
    }
    de.danoeh.antennapod.PodcastApp.singleton = this;
    de.danoeh.antennapod.core.ClientConfigurator.initialize(this);
    de.danoeh.antennapod.preferences.PreferenceUpgrader.checkUpgrades(this);
    com.joanzapata.iconify.Iconify.with(new com.joanzapata.iconify.fonts.FontAwesomeModule());
    com.joanzapata.iconify.Iconify.with(new com.joanzapata.iconify.fonts.MaterialModule());
    de.danoeh.antennapod.spa.SPAUtil.sendSPAppsQueryFeedsIntent(this);
    org.greenrobot.eventbus.EventBus.builder().addIndex(new de.danoeh.antennapod.ApEventBusIndex()).addIndex(new de.danoeh.antennapod.core.ApCoreEventBusIndex()).logNoSubscriberMessages(false).sendNoSubscriberEvent(false).installDefaultEventBus();
    com.google.android.material.color.DynamicColors.applyToActivitiesIfAvailable(this);
}


public static void forceRestart() {
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // PodcastApp_1_NullIntentOperatorMutator
        case 1163: {
            intent = null;
            break;
        }
        // PodcastApp_2_InvalidKeyIntentOperatorMutator
        case 2163: {
            intent = new android.content.Intent((de.danoeh.antennapod.PodcastApp) null, de.danoeh.antennapod.activity.SplashActivity.class);
            break;
        }
        // PodcastApp_3_RandomActionIntentDefinitionOperatorMutator
        case 3163: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent(de.danoeh.antennapod.PodcastApp.getInstance(), de.danoeh.antennapod.activity.SplashActivity.class);
        break;
    }
}
android.content.ComponentName cn;
cn = intent.getComponent();
android.content.Intent mainIntent;
switch(MUID_STATIC) {
    // PodcastApp_4_RandomActionIntentDefinitionOperatorMutator
    case 4163: {
        mainIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    mainIntent = android.content.Intent.makeRestartActivityTask(cn);
    break;
}
}
de.danoeh.antennapod.PodcastApp.getInstance().startActivity(mainIntent);
java.lang.Runtime.getRuntime().exit(0);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
