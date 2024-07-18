package de.danoeh.antennapod.dialog;
import androidx.appcompat.app.AlertDialog;
import de.danoeh.antennapod.event.MessageEvent;
import org.greenrobot.eventbus.EventBus;
import de.danoeh.antennapod.model.download.DownloadResult;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.ClipboardManager;
import de.danoeh.antennapod.core.util.DownloadErrorLabel;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.model.feed.FeedMedia;
import androidx.annotation.NonNull;
import android.os.Build;
import de.danoeh.antennapod.core.storage.DBReader;
import android.widget.TextView;
import android.content.ClipData;
import de.danoeh.antennapod.model.feed.Feed;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DownloadLogDetailsDialog extends com.google.android.material.dialog.MaterialAlertDialogBuilder {
    static final int MUID_STATIC = getMUID();
    public DownloadLogDetailsDialog(@androidx.annotation.NonNull
    android.content.Context context, de.danoeh.antennapod.model.download.DownloadResult status) {
        super(context);
        java.lang.String url;
        url = "unknown";
        if (status.getFeedfileType() == de.danoeh.antennapod.model.feed.FeedMedia.FEEDFILETYPE_FEEDMEDIA) {
            de.danoeh.antennapod.model.feed.FeedMedia media;
            media = de.danoeh.antennapod.core.storage.DBReader.getFeedMedia(status.getFeedfileId());
            if (media != null) {
                url = media.getDownload_url();
            }
        } else if (status.getFeedfileType() == de.danoeh.antennapod.model.feed.Feed.FEEDFILETYPE_FEED) {
            de.danoeh.antennapod.model.feed.Feed feed;
            feed = de.danoeh.antennapod.core.storage.DBReader.getFeed(status.getFeedfileId());
            if (feed != null) {
                url = feed.getDownload_url();
            }
        }
        java.lang.String message;
        message = context.getString(de.danoeh.antennapod.R.string.download_successful);
        if (!status.isSuccessful()) {
            message = status.getReasonDetailed();
        }
        java.lang.String messageFull;
        messageFull = context.getString(de.danoeh.antennapod.R.string.download_log_details_message, context.getString(de.danoeh.antennapod.core.util.DownloadErrorLabel.from(status.getReason())), message, url);
        setTitle(de.danoeh.antennapod.R.string.download_error_details);
        setMessage(messageFull);
        setPositiveButton(android.R.string.ok, null);
        switch(MUID_STATIC) {
            // DownloadLogDetailsDialog_0_BuggyGUIListenerOperatorMutator
            case 56: {
                setNeutralButton(de.danoeh.antennapod.R.string.copy_to_clipboard, null);
                break;
            }
            default: {
            setNeutralButton(de.danoeh.antennapod.R.string.copy_to_clipboard, (android.content.DialogInterface dialog,int which) -> {
                android.content.ClipboardManager clipboard;
                clipboard = ((android.content.ClipboardManager) (getContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
                android.content.ClipData clip;
                clip = android.content.ClipData.newPlainText(context.getString(de.danoeh.antennapod.R.string.download_error_details), messageFull);
                clipboard.setPrimaryClip(clip);
                if (android.os.Build.VERSION.SDK_INT < 32) {
                    org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.MessageEvent(context.getString(de.danoeh.antennapod.R.string.copied_to_clipboard)));
                }
            });
            break;
        }
    }
}


@java.lang.Override
public androidx.appcompat.app.AlertDialog show() {
    androidx.appcompat.app.AlertDialog dialog;
    dialog = super.show();
    ((android.widget.TextView) (dialog.findViewById(android.R.id.message))).setTextIsSelectable(true);
    return dialog;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
