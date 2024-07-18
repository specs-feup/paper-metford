package de.danoeh.antennapod.adapter.actionbutton;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.receiver.MediaButtonReceiver;
import de.danoeh.antennapod.model.feed.FeedMedia;
import androidx.annotation.DrawableRes;
import de.danoeh.antennapod.core.util.PlaybackStatus;
import android.view.KeyEvent;
import de.danoeh.antennapod.model.feed.FeedItem;
import androidx.annotation.StringRes;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PauseActionButton extends de.danoeh.antennapod.adapter.actionbutton.ItemActionButton {
    static final int MUID_STATIC = getMUID();
    public PauseActionButton(de.danoeh.antennapod.model.feed.FeedItem item) {
        super(item);
    }


    @java.lang.Override
    @androidx.annotation.StringRes
    public int getLabel() {
        return de.danoeh.antennapod.R.string.pause_label;
    }


    @java.lang.Override
    @androidx.annotation.DrawableRes
    public int getDrawable() {
        return de.danoeh.antennapod.R.drawable.ic_pause;
    }


    @java.lang.Override
    public void onClick(android.content.Context context) {
        de.danoeh.antennapod.model.feed.FeedMedia media;
        media = item.getMedia();
        if (media == null) {
            return;
        }
        if (de.danoeh.antennapod.core.util.PlaybackStatus.isCurrentlyPlaying(media)) {
            context.sendBroadcast(de.danoeh.antennapod.core.receiver.MediaButtonReceiver.createIntent(context, android.view.KeyEvent.KEYCODE_MEDIA_PAUSE));
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
