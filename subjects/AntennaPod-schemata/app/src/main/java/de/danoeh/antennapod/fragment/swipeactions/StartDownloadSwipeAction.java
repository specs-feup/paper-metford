package de.danoeh.antennapod.fragment.swipeactions;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.adapter.actionbutton.DownloadActionButton;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import androidx.fragment.app.Fragment;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class StartDownloadSwipeAction implements de.danoeh.antennapod.fragment.swipeactions.SwipeAction {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public java.lang.String getId() {
        return de.danoeh.antennapod.fragment.swipeactions.SwipeAction.START_DOWNLOAD;
    }


    @java.lang.Override
    public int getActionIcon() {
        return de.danoeh.antennapod.R.drawable.ic_download;
    }


    @java.lang.Override
    public int getActionColor() {
        return de.danoeh.antennapod.R.attr.icon_green;
    }


    @java.lang.Override
    public java.lang.String getTitle(android.content.Context context) {
        return context.getString(de.danoeh.antennapod.R.string.download_label);
    }


    @java.lang.Override
    public void performAction(de.danoeh.antennapod.model.feed.FeedItem item, androidx.fragment.app.Fragment fragment, de.danoeh.antennapod.model.feed.FeedItemFilter filter) {
        if ((!item.isDownloaded()) && (!item.getFeed().isLocalFeed())) {
            new de.danoeh.antennapod.adapter.actionbutton.DownloadActionButton(item).onClick(fragment.requireContext());
        }
    }


    @java.lang.Override
    public boolean willRemove(de.danoeh.antennapod.model.feed.FeedItemFilter filter, de.danoeh.antennapod.model.feed.FeedItem item) {
        return false;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
