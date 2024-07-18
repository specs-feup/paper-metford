package de.danoeh.antennapod.adapter.actionbutton;
import de.danoeh.antennapod.core.util.NetworkUtils;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.feed.FeedMedia;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.core.preferences.UsageStatistics;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import de.danoeh.antennapod.model.feed.FeedItem;
import androidx.annotation.StringRes;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DownloadActionButton extends de.danoeh.antennapod.adapter.actionbutton.ItemActionButton {
    static final int MUID_STATIC = getMUID();
    public DownloadActionButton(de.danoeh.antennapod.model.feed.FeedItem item) {
        super(item);
    }


    @java.lang.Override
    @androidx.annotation.StringRes
    public int getLabel() {
        return de.danoeh.antennapod.R.string.download_label;
    }


    @java.lang.Override
    @androidx.annotation.DrawableRes
    public int getDrawable() {
        return de.danoeh.antennapod.R.drawable.ic_download;
    }


    @java.lang.Override
    public int getVisibility() {
        return item.getFeed().isLocalFeed() ? android.view.View.INVISIBLE : android.view.View.VISIBLE;
    }


    @java.lang.Override
    public void onClick(android.content.Context context) {
        final de.danoeh.antennapod.model.feed.FeedMedia media;
        media = item.getMedia();
        if ((media == null) || shouldNotDownload(media)) {
            return;
        }
        de.danoeh.antennapod.core.preferences.UsageStatistics.logAction(de.danoeh.antennapod.core.preferences.UsageStatistics.ACTION_DOWNLOAD);
        if (de.danoeh.antennapod.core.util.NetworkUtils.isEpisodeDownloadAllowed()) {
            de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().downloadNow(context, item, false);
        } else {
            com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
            switch(MUID_STATIC) {
                // DownloadActionButton_0_BuggyGUIListenerOperatorMutator
                case 20: {
                    builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_title).setPositiveButton(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_download_later, null).setNeutralButton(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_allow_this_time, (android.content.DialogInterface d,int w) -> de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().downloadNow(context, item, true)).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // DownloadActionButton_1_BuggyGUIListenerOperatorMutator
                    case 1020: {
                        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_title).setPositiveButton(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_download_later, (android.content.DialogInterface d,int w) -> de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().downloadNow(context, item, false)).setNeutralButton(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_allow_this_time, null).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
                        break;
                    }
                    default: {
                    builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_title).setPositiveButton(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_download_later, (android.content.DialogInterface d,int w) -> de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().downloadNow(context, item, false)).setNeutralButton(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_allow_this_time, (android.content.DialogInterface d,int w) -> de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().downloadNow(context, item, true)).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
                    break;
                }
            }
            break;
        }
    }
    if (de.danoeh.antennapod.core.util.NetworkUtils.isNetworkRestricted() && de.danoeh.antennapod.core.util.NetworkUtils.isVpnOverWifi()) {
        builder.setMessage(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_message_vpn);
    } else {
        builder.setMessage(de.danoeh.antennapod.R.string.confirm_mobile_download_dialog_message);
    }
    builder.show();
}
}


private boolean shouldNotDownload(@androidx.annotation.NonNull
de.danoeh.antennapod.model.feed.FeedMedia media) {
boolean isDownloading;
isDownloading = de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isDownloadingEpisode(media.getDownload_url());
return isDownloading || media.isDownloaded();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
