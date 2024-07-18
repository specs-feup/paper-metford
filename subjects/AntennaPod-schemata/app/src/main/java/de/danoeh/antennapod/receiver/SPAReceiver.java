package de.danoeh.antennapod.receiver;
import android.util.Log;
import de.danoeh.antennapod.core.storage.DBTasks;
import android.text.TextUtils;
import android.content.Intent;
import android.content.BroadcastReceiver;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import de.danoeh.antennapod.core.ClientConfigurator;
import android.widget.Toast;
import java.util.Arrays;
import de.danoeh.antennapod.model.feed.Feed;
import android.content.Context;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Receives intents from AntennaPod Single Purpose apps
 */
public class SPAReceiver extends android.content.BroadcastReceiver {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "SPAReceiver";

    public static final java.lang.String ACTION_SP_APPS_QUERY_FEEDS = "de.danoeh.antennapdsp.intent.SP_APPS_QUERY_FEEDS";

    private static final java.lang.String ACTION_SP_APPS_QUERY_FEEDS_REPSONSE = "de.danoeh.antennapdsp.intent.SP_APPS_QUERY_FEEDS_RESPONSE";

    private static final java.lang.String ACTION_SP_APPS_QUERY_FEEDS_REPSONSE_FEEDS_EXTRA = "feeds";

    @java.lang.Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if (!android.text.TextUtils.equals(intent.getAction(), de.danoeh.antennapod.receiver.SPAReceiver.ACTION_SP_APPS_QUERY_FEEDS_REPSONSE)) {
            return;
        }
        android.util.Log.d(de.danoeh.antennapod.receiver.SPAReceiver.TAG, "Received SP_APPS_QUERY_RESPONSE");
        if (!intent.hasExtra(de.danoeh.antennapod.receiver.SPAReceiver.ACTION_SP_APPS_QUERY_FEEDS_REPSONSE_FEEDS_EXTRA)) {
            android.util.Log.e(de.danoeh.antennapod.receiver.SPAReceiver.TAG, "Received invalid SP_APPS_QUERY_RESPONSE: Contains no extra");
            return;
        }
        java.lang.String[] feedUrls;
        feedUrls = intent.getStringArrayExtra(de.danoeh.antennapod.receiver.SPAReceiver.ACTION_SP_APPS_QUERY_FEEDS_REPSONSE_FEEDS_EXTRA);
        if (feedUrls == null) {
            android.util.Log.e(de.danoeh.antennapod.receiver.SPAReceiver.TAG, "Received invalid SP_APPS_QUERY_REPSONSE: extra was null");
            return;
        }
        android.util.Log.d(de.danoeh.antennapod.receiver.SPAReceiver.TAG, "Received feeds list: " + java.util.Arrays.toString(feedUrls));
        de.danoeh.antennapod.core.ClientConfigurator.initialize(context);
        for (java.lang.String url : feedUrls) {
            de.danoeh.antennapod.model.feed.Feed feed;
            feed = new de.danoeh.antennapod.model.feed.Feed(url, null, "Unknown podcast");
            feed.setItems(java.util.Collections.emptyList());
            de.danoeh.antennapod.core.storage.DBTasks.updateFeed(context, feed, false);
        }
        android.widget.Toast.makeText(context, de.danoeh.antennapod.R.string.sp_apps_importing_feeds_msg, android.widget.Toast.LENGTH_LONG).show();
        de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnce(context);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
