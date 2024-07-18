package de.danoeh.antennapod.adapter.actionbutton;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.feed.FeedMedia;
import de.danoeh.antennapod.core.storage.DBTasks;
import androidx.annotation.DrawableRes;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.playback.PlaybackServiceStarter;
import de.danoeh.antennapod.model.playback.MediaType;
import androidx.annotation.StringRes;
import de.danoeh.antennapod.core.service.playback.PlaybackService;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PlayActionButton extends de.danoeh.antennapod.adapter.actionbutton.ItemActionButton {
    static final int MUID_STATIC = getMUID();
    public PlayActionButton(de.danoeh.antennapod.model.feed.FeedItem item) {
        super(item);
    }


    @java.lang.Override
    @androidx.annotation.StringRes
    public int getLabel() {
        return de.danoeh.antennapod.R.string.play_label;
    }


    @java.lang.Override
    @androidx.annotation.DrawableRes
    public int getDrawable() {
        return de.danoeh.antennapod.R.drawable.ic_play_24dp;
    }


    @java.lang.Override
    public void onClick(android.content.Context context) {
        de.danoeh.antennapod.model.feed.FeedMedia media;
        media = item.getMedia();
        if (media == null) {
            return;
        }
        if (!media.fileExists()) {
            de.danoeh.antennapod.core.storage.DBTasks.notifyMissingFeedMediaFile(context, media);
            return;
        }
        new de.danoeh.antennapod.core.util.playback.PlaybackServiceStarter(context, media).callEvenIfRunning(true).start();
        if (media.getMediaType() == de.danoeh.antennapod.model.playback.MediaType.VIDEO) {
            context.startActivity(de.danoeh.antennapod.core.service.playback.PlaybackService.getPlayerActivityIntent(context, media));
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
