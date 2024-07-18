package de.danoeh.antennapod.receiver;
import android.util.Log;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import de.danoeh.antennapod.core.ClientConfigurator;
import de.danoeh.antennapod.core.util.download.NetworkConnectionChangeHandler;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ConnectivityActionReceiver extends android.content.BroadcastReceiver {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "ConnectivityActionRecvr";

    @java.lang.Override
    public void onReceive(final android.content.Context context, android.content.Intent intent) {
        if (android.text.TextUtils.equals(intent.getAction(), android.net.ConnectivityManager.CONNECTIVITY_ACTION)) {
            android.util.Log.d(de.danoeh.antennapod.receiver.ConnectivityActionReceiver.TAG, "Received intent");
            de.danoeh.antennapod.core.ClientConfigurator.initialize(context);
            de.danoeh.antennapod.core.util.download.NetworkConnectionChangeHandler.networkChangedDetected();
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
