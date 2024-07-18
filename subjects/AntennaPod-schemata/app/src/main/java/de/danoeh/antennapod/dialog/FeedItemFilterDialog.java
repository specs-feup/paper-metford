package de.danoeh.antennapod.dialog;
import java.util.Set;
import android.os.Bundle;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.model.feed.Feed;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FeedItemFilterDialog extends de.danoeh.antennapod.dialog.ItemFilterDialog {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String ARGUMENT_FEED_ID = "feedId";

    public static de.danoeh.antennapod.dialog.FeedItemFilterDialog newInstance(de.danoeh.antennapod.model.feed.Feed feed) {
        de.danoeh.antennapod.dialog.FeedItemFilterDialog dialog;
        dialog = new de.danoeh.antennapod.dialog.FeedItemFilterDialog();
        android.os.Bundle arguments;
        arguments = new android.os.Bundle();
        arguments.putSerializable(de.danoeh.antennapod.dialog.ItemFilterDialog.ARGUMENT_FILTER, feed.getItemFilter());
        arguments.putLong(de.danoeh.antennapod.dialog.FeedItemFilterDialog.ARGUMENT_FEED_ID, feed.getId());
        dialog.setArguments(arguments);
        return dialog;
    }


    @java.lang.Override
    void onFilterChanged(java.util.Set<java.lang.String> newFilterValues) {
        long feedId;
        feedId = getArguments().getLong(de.danoeh.antennapod.dialog.FeedItemFilterDialog.ARGUMENT_FEED_ID);
        de.danoeh.antennapod.core.storage.DBWriter.setFeedItemsFilter(feedId, newFilterValues);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
