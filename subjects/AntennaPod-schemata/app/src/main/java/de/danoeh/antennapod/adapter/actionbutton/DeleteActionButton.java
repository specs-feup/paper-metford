package de.danoeh.antennapod.adapter.actionbutton;
import de.danoeh.antennapod.view.LocalDeleteModal;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.feed.FeedMedia;
import androidx.annotation.DrawableRes;
import de.danoeh.antennapod.model.feed.FeedItem;
import androidx.annotation.StringRes;
import de.danoeh.antennapod.core.storage.DBWriter;
import android.view.View;
import android.content.Context;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DeleteActionButton extends de.danoeh.antennapod.adapter.actionbutton.ItemActionButton {
    static final int MUID_STATIC = getMUID();
    public DeleteActionButton(de.danoeh.antennapod.model.feed.FeedItem item) {
        super(item);
    }


    @java.lang.Override
    @androidx.annotation.StringRes
    public int getLabel() {
        return de.danoeh.antennapod.R.string.delete_label;
    }


    @java.lang.Override
    @androidx.annotation.DrawableRes
    public int getDrawable() {
        return de.danoeh.antennapod.R.drawable.ic_delete;
    }


    @java.lang.Override
    public void onClick(android.content.Context context) {
        final de.danoeh.antennapod.model.feed.FeedMedia media;
        media = item.getMedia();
        if (media == null) {
            return;
        }
        de.danoeh.antennapod.view.LocalDeleteModal.showLocalFeedDeleteWarningIfNecessary(context, java.util.Collections.singletonList(item), () -> de.danoeh.antennapod.core.storage.DBWriter.deleteFeedMediaOfItem(context, media.getId()));
    }


    @java.lang.Override
    public int getVisibility() {
        if ((item.getMedia() != null) && (item.getMedia().isDownloaded() || item.getFeed().isLocalFeed())) {
            return android.view.View.VISIBLE;
        }
        return android.view.View.INVISIBLE;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
