package de.danoeh.antennapod.fragment.swipeactions;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import androidx.fragment.app.Fragment;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AddToQueueSwipeAction implements de.danoeh.antennapod.fragment.swipeactions.SwipeAction {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public java.lang.String getId() {
        return de.danoeh.antennapod.fragment.swipeactions.SwipeAction.ADD_TO_QUEUE;
    }


    @java.lang.Override
    public int getActionIcon() {
        return de.danoeh.antennapod.R.drawable.ic_playlist_play;
    }


    @java.lang.Override
    public int getActionColor() {
        return de.danoeh.antennapod.R.attr.colorAccent;
    }


    @java.lang.Override
    public java.lang.String getTitle(android.content.Context context) {
        return context.getString(de.danoeh.antennapod.R.string.add_to_queue_label);
    }


    @java.lang.Override
    public void performAction(de.danoeh.antennapod.model.feed.FeedItem item, androidx.fragment.app.Fragment fragment, de.danoeh.antennapod.model.feed.FeedItemFilter filter) {
        if (!item.isTagged(de.danoeh.antennapod.model.feed.FeedItem.TAG_QUEUE)) {
            de.danoeh.antennapod.core.storage.DBWriter.addQueueItem(fragment.requireContext(), item);
        } else {
            new de.danoeh.antennapod.fragment.swipeactions.RemoveFromQueueSwipeAction().performAction(item, fragment, filter);
        }
    }


    @java.lang.Override
    public boolean willRemove(de.danoeh.antennapod.model.feed.FeedItemFilter filter, de.danoeh.antennapod.model.feed.FeedItem item) {
        return filter.showQueued || filter.showNew;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
