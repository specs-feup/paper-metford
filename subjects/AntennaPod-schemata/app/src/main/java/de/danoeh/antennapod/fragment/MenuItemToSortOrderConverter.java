package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.feed.SortOrder;
import android.view.MenuItem;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MenuItemToSortOrderConverter {
    static final int MUID_STATIC = getMUID();
    public static de.danoeh.antennapod.model.feed.SortOrder convert(android.view.MenuItem item) {
        final int itemId;
        itemId = item.getItemId();
        if (itemId == de.danoeh.antennapod.R.id.sort_episode_title_asc) {
            return de.danoeh.antennapod.model.feed.SortOrder.EPISODE_TITLE_A_Z;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_episode_title_desc) {
            return de.danoeh.antennapod.model.feed.SortOrder.EPISODE_TITLE_Z_A;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_date_asc) {
            return de.danoeh.antennapod.model.feed.SortOrder.DATE_OLD_NEW;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_date_desc) {
            return de.danoeh.antennapod.model.feed.SortOrder.DATE_NEW_OLD;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_duration_asc) {
            return de.danoeh.antennapod.model.feed.SortOrder.DURATION_SHORT_LONG;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_duration_desc) {
            return de.danoeh.antennapod.model.feed.SortOrder.DURATION_LONG_SHORT;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_feed_title_asc) {
            return de.danoeh.antennapod.model.feed.SortOrder.FEED_TITLE_A_Z;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_feed_title_desc) {
            return de.danoeh.antennapod.model.feed.SortOrder.FEED_TITLE_Z_A;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_random) {
            return de.danoeh.antennapod.model.feed.SortOrder.RANDOM;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_smart_shuffle_asc) {
            return de.danoeh.antennapod.model.feed.SortOrder.SMART_SHUFFLE_OLD_NEW;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_smart_shuffle_desc) {
            return de.danoeh.antennapod.model.feed.SortOrder.SMART_SHUFFLE_NEW_OLD;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_size_small_large) {
            return de.danoeh.antennapod.model.feed.SortOrder.SIZE_SMALL_LARGE;
        } else if (itemId == de.danoeh.antennapod.R.id.sort_size_large_small) {
            return de.danoeh.antennapod.model.feed.SortOrder.SIZE_LARGE_SMALL;
        }
        return null;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
