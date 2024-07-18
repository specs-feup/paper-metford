package de.danoeh.antennapod.fragment.swipeactions;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import androidx.fragment.app.Fragment;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TogglePlaybackStateSwipeAction implements de.danoeh.antennapod.fragment.swipeactions.SwipeAction {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public java.lang.String getId() {
        return de.danoeh.antennapod.fragment.swipeactions.SwipeAction.TOGGLE_PLAYED;
    }


    @java.lang.Override
    public int getActionIcon() {
        return de.danoeh.antennapod.R.drawable.ic_mark_played;
    }


    @java.lang.Override
    public int getActionColor() {
        return de.danoeh.antennapod.R.attr.icon_gray;
    }


    @java.lang.Override
    public java.lang.String getTitle(android.content.Context context) {
        return context.getString(de.danoeh.antennapod.R.string.toggle_played_label);
    }


    @java.lang.Override
    public void performAction(de.danoeh.antennapod.model.feed.FeedItem item, androidx.fragment.app.Fragment fragment, de.danoeh.antennapod.model.feed.FeedItemFilter filter) {
        int newState;
        newState = (item.getPlayState() == de.danoeh.antennapod.model.feed.FeedItem.UNPLAYED) ? de.danoeh.antennapod.model.feed.FeedItem.PLAYED : de.danoeh.antennapod.model.feed.FeedItem.UNPLAYED;
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.markReadWithUndo(fragment, item, newState, willRemove(filter, item));
    }


    @java.lang.Override
    public boolean willRemove(de.danoeh.antennapod.model.feed.FeedItemFilter filter, de.danoeh.antennapod.model.feed.FeedItem item) {
        if (item.getPlayState() == de.danoeh.antennapod.model.feed.FeedItem.NEW) {
            return filter.showPlayed || filter.showNew;
        } else {
            return (filter.showUnplayed || filter.showPlayed) || filter.showNew;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
