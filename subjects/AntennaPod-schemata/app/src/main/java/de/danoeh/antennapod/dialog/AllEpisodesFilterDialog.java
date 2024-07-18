package de.danoeh.antennapod.dialog;
import java.util.Set;
import android.os.Bundle;
import org.greenrobot.eventbus.EventBus;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AllEpisodesFilterDialog extends de.danoeh.antennapod.dialog.ItemFilterDialog {
    static final int MUID_STATIC = getMUID();
    public static de.danoeh.antennapod.dialog.AllEpisodesFilterDialog newInstance(de.danoeh.antennapod.model.feed.FeedItemFilter filter) {
        de.danoeh.antennapod.dialog.AllEpisodesFilterDialog dialog;
        dialog = new de.danoeh.antennapod.dialog.AllEpisodesFilterDialog();
        android.os.Bundle arguments;
        arguments = new android.os.Bundle();
        arguments.putSerializable(de.danoeh.antennapod.dialog.ItemFilterDialog.ARGUMENT_FILTER, filter);
        dialog.setArguments(arguments);
        return dialog;
    }


    @java.lang.Override
    void onFilterChanged(java.util.Set<java.lang.String> newFilterValues) {
        org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.dialog.AllEpisodesFilterDialog.AllEpisodesFilterChangedEvent(newFilterValues));
    }


    public static class AllEpisodesFilterChangedEvent {
        public final java.util.Set<java.lang.String> filterValues;

        public AllEpisodesFilterChangedEvent(java.util.Set<java.lang.String> filterValues) {
            this.filterValues = filterValues;
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
