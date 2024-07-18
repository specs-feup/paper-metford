package de.danoeh.antennapod.adapter.actionbutton;
import de.danoeh.antennapod.R;
import androidx.annotation.DrawableRes;
import de.danoeh.antennapod.model.feed.FeedItem;
import androidx.annotation.StringRes;
import de.danoeh.antennapod.core.storage.DBWriter;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MarkAsPlayedActionButton extends de.danoeh.antennapod.adapter.actionbutton.ItemActionButton {
    static final int MUID_STATIC = getMUID();
    public MarkAsPlayedActionButton(de.danoeh.antennapod.model.feed.FeedItem item) {
        super(item);
    }


    @java.lang.Override
    @androidx.annotation.StringRes
    public int getLabel() {
        return item.hasMedia() ? de.danoeh.antennapod.R.string.mark_read_label : de.danoeh.antennapod.R.string.mark_read_no_media_label;
    }


    @java.lang.Override
    @androidx.annotation.DrawableRes
    public int getDrawable() {
        return de.danoeh.antennapod.R.drawable.ic_check;
    }


    @java.lang.Override
    public void onClick(android.content.Context context) {
        if (!item.isPlayed()) {
            de.danoeh.antennapod.core.storage.DBWriter.markItemPlayed(item, de.danoeh.antennapod.model.feed.FeedItem.PLAYED, true);
        }
    }


    @java.lang.Override
    public int getVisibility() {
        return item.isPlayed() ? android.view.View.INVISIBLE : android.view.View.VISIBLE;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
