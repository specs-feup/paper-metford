package de.danoeh.antennapod.adapter.actionbutton;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.model.feed.FeedMedia;
import androidx.annotation.DrawableRes;
import de.danoeh.antennapod.core.util.PlaybackStatus;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import androidx.annotation.StringRes;
import android.widget.ImageView;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class ItemActionButton {
    static final int MUID_STATIC = getMUID();
    de.danoeh.antennapod.model.feed.FeedItem item;

    ItemActionButton(de.danoeh.antennapod.model.feed.FeedItem item) {
        this.item = item;
    }


    @androidx.annotation.StringRes
    public abstract int getLabel();


    @androidx.annotation.DrawableRes
    public abstract int getDrawable();


    public abstract void onClick(android.content.Context context);


    public int getVisibility() {
        return android.view.View.VISIBLE;
    }


    @androidx.annotation.NonNull
    public static de.danoeh.antennapod.adapter.actionbutton.ItemActionButton forItem(@androidx.annotation.NonNull
    de.danoeh.antennapod.model.feed.FeedItem item) {
        final de.danoeh.antennapod.model.feed.FeedMedia media;
        media = item.getMedia();
        if (media == null) {
            return new de.danoeh.antennapod.adapter.actionbutton.MarkAsPlayedActionButton(item);
        }
        final boolean isDownloadingMedia;
        isDownloadingMedia = de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isDownloadingEpisode(media.getDownload_url());
        if (de.danoeh.antennapod.core.util.PlaybackStatus.isCurrentlyPlaying(media)) {
            return new de.danoeh.antennapod.adapter.actionbutton.PauseActionButton(item);
        } else if (item.getFeed().isLocalFeed()) {
            return new de.danoeh.antennapod.adapter.actionbutton.PlayLocalActionButton(item);
        } else if (media.isDownloaded()) {
            return new de.danoeh.antennapod.adapter.actionbutton.PlayActionButton(item);
        } else if (isDownloadingMedia) {
            return new de.danoeh.antennapod.adapter.actionbutton.CancelDownloadActionButton(item);
        } else if (de.danoeh.antennapod.storage.preferences.UserPreferences.isStreamOverDownload()) {
            return new de.danoeh.antennapod.adapter.actionbutton.StreamActionButton(item);
        } else {
            return new de.danoeh.antennapod.adapter.actionbutton.DownloadActionButton(item);
        }
    }


    public void configure(@androidx.annotation.NonNull
    android.view.View button, @androidx.annotation.NonNull
    android.widget.ImageView icon, android.content.Context context) {
        button.setVisibility(getVisibility());
        button.setContentDescription(context.getString(getLabel()));
        switch(MUID_STATIC) {
            // ItemActionButton_0_BuggyGUIListenerOperatorMutator
            case 19: {
                button.setOnClickListener(null);
                break;
            }
            default: {
            button.setOnClickListener((android.view.View view) -> onClick(context));
            break;
        }
    }
    icon.setImageResource(getDrawable());
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
