package de.danoeh.antennapod.adapter;
import android.util.Log;
import android.view.ViewGroup;
import java.util.ArrayList;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import de.danoeh.antennapod.activity.MainActivity;
import android.text.format.DateUtils;
import de.danoeh.antennapod.model.download.DownloadResult;
import android.view.View;
import de.danoeh.antennapod.core.util.DownloadErrorLabel;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.view.viewholder.DownloadLogItemViewHolder;
import de.danoeh.antennapod.model.feed.FeedMedia;
import android.app.Activity;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import de.danoeh.antennapod.model.download.DownloadError;
import de.danoeh.antennapod.adapter.actionbutton.DownloadActionButton;
import android.widget.Toast;
import java.util.List;
import android.widget.BaseAdapter;
import de.danoeh.antennapod.model.feed.Feed;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays a list of DownloadStatus entries.
 */
public class DownloadLogAdapter extends android.widget.BaseAdapter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "DownloadLogAdapter";

    private final android.app.Activity context;

    private java.util.List<de.danoeh.antennapod.model.download.DownloadResult> downloadLog = new java.util.ArrayList<>();

    public DownloadLogAdapter(android.app.Activity context) {
        super();
        this.context = context;
    }


    public void setDownloadLog(java.util.List<de.danoeh.antennapod.model.download.DownloadResult> downloadLog) {
        this.downloadLog = downloadLog;
        notifyDataSetChanged();
    }


    @java.lang.Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        de.danoeh.antennapod.view.viewholder.DownloadLogItemViewHolder holder;
        if (convertView == null) {
            holder = new de.danoeh.antennapod.view.viewholder.DownloadLogItemViewHolder(context, parent);
            holder.itemView.setTag(holder);
        } else {
            holder = ((de.danoeh.antennapod.view.viewholder.DownloadLogItemViewHolder) (convertView.getTag()));
        }
        bind(holder, getItem(position), position);
        return holder.itemView;
    }


    private void bind(de.danoeh.antennapod.view.viewholder.DownloadLogItemViewHolder holder, de.danoeh.antennapod.model.download.DownloadResult status, int position) {
        java.lang.String statusText;
        statusText = "";
        if (status.getFeedfileType() == de.danoeh.antennapod.model.feed.Feed.FEEDFILETYPE_FEED) {
            statusText += context.getString(de.danoeh.antennapod.R.string.download_type_feed);
        } else if (status.getFeedfileType() == de.danoeh.antennapod.model.feed.FeedMedia.FEEDFILETYPE_FEEDMEDIA) {
            statusText += context.getString(de.danoeh.antennapod.R.string.download_type_media);
        }
        statusText += " · ";
        statusText += android.text.format.DateUtils.getRelativeTimeSpanString(status.getCompletionDate().getTime(), java.lang.System.currentTimeMillis(), android.text.format.DateUtils.MINUTE_IN_MILLIS, 0);
        holder.status.setText(statusText);
        if (status.getTitle() != null) {
            holder.title.setText(status.getTitle());
        } else {
            holder.title.setText(de.danoeh.antennapod.R.string.download_log_title_unknown);
        }
        if (status.isSuccessful()) {
            holder.icon.setTextColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, de.danoeh.antennapod.R.attr.icon_green));
            holder.icon.setText("{fa-check-circle}");
            holder.icon.setContentDescription(context.getString(de.danoeh.antennapod.R.string.download_successful));
            holder.secondaryActionButton.setVisibility(android.view.View.INVISIBLE);
            holder.reason.setVisibility(android.view.View.GONE);
            holder.tapForDetails.setVisibility(android.view.View.GONE);
        } else {
            if (status.getReason() == de.danoeh.antennapod.model.download.DownloadError.ERROR_PARSER_EXCEPTION_DUPLICATE) {
                holder.icon.setTextColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, de.danoeh.antennapod.R.attr.icon_yellow));
                holder.icon.setText("{fa-exclamation-circle}");
            } else {
                holder.icon.setTextColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, de.danoeh.antennapod.R.attr.icon_red));
                holder.icon.setText("{fa-times-circle}");
            }
            holder.icon.setContentDescription(context.getString(de.danoeh.antennapod.R.string.error_label));
            holder.reason.setText(de.danoeh.antennapod.core.util.DownloadErrorLabel.from(status.getReason()));
            holder.reason.setVisibility(android.view.View.VISIBLE);
            holder.tapForDetails.setVisibility(android.view.View.VISIBLE);
            if (newerWasSuccessful(position, status.getFeedfileType(), status.getFeedfileId())) {
                holder.secondaryActionButton.setVisibility(android.view.View.INVISIBLE);
                holder.secondaryActionButton.setOnClickListener(null);
                holder.secondaryActionButton.setTag(null);
            } else {
                holder.secondaryActionIcon.setImageResource(de.danoeh.antennapod.R.drawable.ic_refresh);
                holder.secondaryActionButton.setVisibility(android.view.View.VISIBLE);
                if (status.getFeedfileType() == de.danoeh.antennapod.model.feed.Feed.FEEDFILETYPE_FEED) {
                    switch(MUID_STATIC) {
                        // DownloadLogAdapter_0_BuggyGUIListenerOperatorMutator
                        case 34: {
                            holder.secondaryActionButton.setOnClickListener(null);
                            break;
                        }
                        default: {
                        holder.secondaryActionButton.setOnClickListener((android.view.View v) -> {
                            holder.secondaryActionButton.setVisibility(android.view.View.INVISIBLE);
                            de.danoeh.antennapod.model.feed.Feed feed;
                            feed = de.danoeh.antennapod.core.storage.DBReader.getFeed(status.getFeedfileId());
                            if (feed == null) {
                                android.util.Log.e(de.danoeh.antennapod.adapter.DownloadLogAdapter.TAG, "Could not find feed for feed id: " + status.getFeedfileId());
                                return;
                            }
                            de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnce(context, feed);
                        });
                        break;
                    }
                }
            } else if (status.getFeedfileType() == de.danoeh.antennapod.model.feed.FeedMedia.FEEDFILETYPE_FEEDMEDIA) {
                switch(MUID_STATIC) {
                    // DownloadLogAdapter_1_BuggyGUIListenerOperatorMutator
                    case 1034: {
                        holder.secondaryActionButton.setOnClickListener(null);
                        break;
                    }
                    default: {
                    holder.secondaryActionButton.setOnClickListener((android.view.View v) -> {
                        holder.secondaryActionButton.setVisibility(android.view.View.INVISIBLE);
                        de.danoeh.antennapod.model.feed.FeedMedia media;
                        media = de.danoeh.antennapod.core.storage.DBReader.getFeedMedia(status.getFeedfileId());
                        if (media == null) {
                            android.util.Log.e(de.danoeh.antennapod.adapter.DownloadLogAdapter.TAG, "Could not find feed media for feed id: " + status.getFeedfileId());
                            return;
                        }
                        new de.danoeh.antennapod.adapter.actionbutton.DownloadActionButton(media.getItem()).onClick(context);
                        ((de.danoeh.antennapod.activity.MainActivity) (context)).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.status_downloading_label, android.widget.Toast.LENGTH_SHORT);
                    });
                    break;
                }
            }
        }
    }
}
}


private boolean newerWasSuccessful(int downloadStatusIndex, int feedTypeId, long id) {
for (int i = 0; i < downloadStatusIndex; i++) {
    de.danoeh.antennapod.model.download.DownloadResult status;
    status = downloadLog.get(i);
    if (((status.getFeedfileType() == feedTypeId) && (status.getFeedfileId() == id)) && status.isSuccessful()) {
        return true;
    }
}
return false;
}


@java.lang.Override
public int getCount() {
return downloadLog.size();
}


@java.lang.Override
public de.danoeh.antennapod.model.download.DownloadResult getItem(int position) {
if (position < downloadLog.size()) {
    return downloadLog.get(position);
}
return null;
}


@java.lang.Override
public long getItemId(int position) {
return position;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
