package de.danoeh.antennapod.receiver;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.util.Log;
import de.danoeh.antennapod.core.storage.DBTasks;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import de.danoeh.antennapod.core.ClientConfigurator;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
// modified from http://developer.android.com/training/monitoring-device-state/battery-monitoring.html
// and ConnectivityActionReceiver.java
// Updated based on http://stackoverflow.com/questions/20833241/android-charge-intent-has-no-extra-data
// Since the intent doesn't have the EXTRA_STATUS like the android.com article says it does
// (though it used to)
public class PowerConnectionReceiver extends android.content.BroadcastReceiver {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "PowerConnectionReceiver";

    @java.lang.Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        final java.lang.String action;
        action = intent.getAction();
        android.util.Log.d(de.danoeh.antennapod.receiver.PowerConnectionReceiver.TAG, "charging intent: " + action);
        de.danoeh.antennapod.core.ClientConfigurator.initialize(context);
        if (android.content.Intent.ACTION_POWER_CONNECTED.equals(action)) {
            android.util.Log.d(de.danoeh.antennapod.receiver.PowerConnectionReceiver.TAG, "charging, starting auto-download");
            // we're plugged in, this is a great time to auto-download if everything else is
            // right. So, even if the user allows auto-dl on battery, let's still start
            // downloading now. They shouldn't mind.
            // autodownloadUndownloadedItems will make sure we're on the right wifi networks,
            // etc... so we don't have to worry about it.
            de.danoeh.antennapod.core.storage.DBTasks.autodownloadUndownloadedItems(context);
        } else // if we're not supposed to be auto-downloading when we're not charging, stop it
        if (!de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownloadOnBattery()) {
            android.util.Log.d(de.danoeh.antennapod.receiver.PowerConnectionReceiver.TAG, "not charging anymore, canceling auto-download");
            de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().cancelAll(context);
        } else {
            android.util.Log.d(de.danoeh.antennapod.receiver.PowerConnectionReceiver.TAG, "not charging anymore, but the user allows auto-download " + "when on battery so we'll keep going");
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
