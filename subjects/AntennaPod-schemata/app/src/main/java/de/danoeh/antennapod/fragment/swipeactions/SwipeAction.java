package de.danoeh.antennapod.fragment.swipeactions;
import androidx.annotation.DrawableRes;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import androidx.fragment.app.Fragment;
import androidx.annotation.AttrRes;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public interface SwipeAction {
    static final int MUID_STATIC = getMUID();
    java.lang.String ADD_TO_QUEUE = "ADD_TO_QUEUE";

    java.lang.String REMOVE_FROM_INBOX = "REMOVE_FROM_INBOX";

    java.lang.String START_DOWNLOAD = "START_DOWNLOAD";

    java.lang.String MARK_FAV = "MARK_FAV";

    java.lang.String TOGGLE_PLAYED = "MARK_PLAYED";

    java.lang.String REMOVE_FROM_QUEUE = "REMOVE_FROM_QUEUE";

    java.lang.String DELETE = "DELETE";

    java.lang.String REMOVE_FROM_HISTORY = "REMOVE_FROM_HISTORY";

    java.lang.String getId();


    java.lang.String getTitle(android.content.Context context);


    @androidx.annotation.DrawableRes
    int getActionIcon();


    @androidx.annotation.AttrRes
    int getActionColor();


    void performAction(de.danoeh.antennapod.model.feed.FeedItem item, androidx.fragment.app.Fragment fragment, de.danoeh.antennapod.model.feed.FeedItemFilter filter);


    boolean willRemove(de.danoeh.antennapod.model.feed.FeedItemFilter filter, de.danoeh.antennapod.model.feed.FeedItem item);


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
