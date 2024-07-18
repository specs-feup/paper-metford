package de.danoeh.antennapod.adapter.actionbutton;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.model.feed.FeedMedia;
import androidx.annotation.DrawableRes;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import de.danoeh.antennapod.model.feed.FeedItem;
import androidx.annotation.StringRes;
import de.danoeh.antennapod.core.storage.DBWriter;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CancelDownloadActionButton extends de.danoeh.antennapod.adapter.actionbutton.ItemActionButton {
    static final int MUID_STATIC = getMUID();
    public CancelDownloadActionButton(de.danoeh.antennapod.model.feed.FeedItem item) {
        super(item);
    }


    @java.lang.Override
    @androidx.annotation.StringRes
    public int getLabel() {
        return de.danoeh.antennapod.R.string.cancel_download_label;
    }


    @java.lang.Override
    @androidx.annotation.DrawableRes
    public int getDrawable() {
        return de.danoeh.antennapod.R.drawable.ic_cancel;
    }


    @java.lang.Override
    public void onClick(android.content.Context context) {
        de.danoeh.antennapod.model.feed.FeedMedia media;
        media = item.getMedia();
        de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().cancel(context, media);
        if (de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownload()) {
            item.disableAutoDownload();
            de.danoeh.antennapod.core.storage.DBWriter.setFeedItem(item);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
