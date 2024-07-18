package de.danoeh.antennapod.view.viewholder;
import de.danoeh.antennapod.ui.common.SquareImageView;
import de.danoeh.antennapod.core.util.PlaybackStatus;
import android.view.ViewGroup;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import de.danoeh.antennapod.ui.common.CircularProgressBar;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import de.danoeh.antennapod.activity.MainActivity;
import androidx.cardview.widget.CardView;
import android.view.View;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import de.danoeh.antennapod.model.feed.FeedMedia;
import de.danoeh.antennapod.adapter.actionbutton.ItemActionButton;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.feed.util.ImageResourceUtils;
import de.danoeh.antennapod.core.util.DateFormatter;
import android.widget.ImageView;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ProgressBar;
import de.danoeh.antennapod.adapter.CoverLoader;
import com.google.android.material.elevation.SurfaceColors;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HorizontalItemViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    static final int MUID_STATIC = getMUID();
    public final androidx.cardview.widget.CardView card;

    public final android.widget.ImageView secondaryActionIcon;

    private final de.danoeh.antennapod.ui.common.SquareImageView cover;

    private final android.widget.TextView title;

    private final android.widget.TextView date;

    private final android.widget.ProgressBar progressBar;

    private final de.danoeh.antennapod.ui.common.CircularProgressBar circularProgressBar;

    private final android.view.View progressBarReplacementSpacer;

    private final de.danoeh.antennapod.activity.MainActivity activity;

    private de.danoeh.antennapod.model.feed.FeedItem item;

    public HorizontalItemViewHolder(de.danoeh.antennapod.activity.MainActivity activity, android.view.ViewGroup parent) {
        super(android.view.LayoutInflater.from(activity).inflate(de.danoeh.antennapod.R.layout.horizontal_itemlist_item, parent, false));
        this.activity = activity;
        switch(MUID_STATIC) {
            // HorizontalItemViewHolder_0_InvalidViewFocusOperatorMutator
            case 0: {
                /**
                * Inserted by Kadabra
                */
                card = itemView.findViewById(de.danoeh.antennapod.R.id.card);
                card.requestFocus();
                break;
            }
            // HorizontalItemViewHolder_1_ViewComponentNotVisibleOperatorMutator
            case 1000: {
                /**
                * Inserted by Kadabra
                */
                card = itemView.findViewById(de.danoeh.antennapod.R.id.card);
                card.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            card = itemView.findViewById(de.danoeh.antennapod.R.id.card);
            break;
        }
    }
    switch(MUID_STATIC) {
        // HorizontalItemViewHolder_2_InvalidViewFocusOperatorMutator
        case 2000: {
            /**
            * Inserted by Kadabra
            */
            cover = itemView.findViewById(de.danoeh.antennapod.R.id.cover);
            cover.requestFocus();
            break;
        }
        // HorizontalItemViewHolder_3_ViewComponentNotVisibleOperatorMutator
        case 3000: {
            /**
            * Inserted by Kadabra
            */
            cover = itemView.findViewById(de.danoeh.antennapod.R.id.cover);
            cover.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        cover = itemView.findViewById(de.danoeh.antennapod.R.id.cover);
        break;
    }
}
switch(MUID_STATIC) {
    // HorizontalItemViewHolder_4_InvalidViewFocusOperatorMutator
    case 4000: {
        /**
        * Inserted by Kadabra
        */
        title = itemView.findViewById(de.danoeh.antennapod.R.id.titleLabel);
        title.requestFocus();
        break;
    }
    // HorizontalItemViewHolder_5_ViewComponentNotVisibleOperatorMutator
    case 5000: {
        /**
        * Inserted by Kadabra
        */
        title = itemView.findViewById(de.danoeh.antennapod.R.id.titleLabel);
        title.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    title = itemView.findViewById(de.danoeh.antennapod.R.id.titleLabel);
    break;
}
}
switch(MUID_STATIC) {
// HorizontalItemViewHolder_6_InvalidViewFocusOperatorMutator
case 6000: {
    /**
    * Inserted by Kadabra
    */
    date = itemView.findViewById(de.danoeh.antennapod.R.id.dateLabel);
    date.requestFocus();
    break;
}
// HorizontalItemViewHolder_7_ViewComponentNotVisibleOperatorMutator
case 7000: {
    /**
    * Inserted by Kadabra
    */
    date = itemView.findViewById(de.danoeh.antennapod.R.id.dateLabel);
    date.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
date = itemView.findViewById(de.danoeh.antennapod.R.id.dateLabel);
break;
}
}
switch(MUID_STATIC) {
// HorizontalItemViewHolder_8_InvalidViewFocusOperatorMutator
case 8000: {
/**
* Inserted by Kadabra
*/
secondaryActionIcon = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionIcon);
secondaryActionIcon.requestFocus();
break;
}
// HorizontalItemViewHolder_9_ViewComponentNotVisibleOperatorMutator
case 9000: {
/**
* Inserted by Kadabra
*/
secondaryActionIcon = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionIcon);
secondaryActionIcon.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
secondaryActionIcon = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionIcon);
break;
}
}
switch(MUID_STATIC) {
// HorizontalItemViewHolder_10_InvalidViewFocusOperatorMutator
case 10000: {
/**
* Inserted by Kadabra
*/
circularProgressBar = itemView.findViewById(de.danoeh.antennapod.R.id.circularProgressBar);
circularProgressBar.requestFocus();
break;
}
// HorizontalItemViewHolder_11_ViewComponentNotVisibleOperatorMutator
case 11000: {
/**
* Inserted by Kadabra
*/
circularProgressBar = itemView.findViewById(de.danoeh.antennapod.R.id.circularProgressBar);
circularProgressBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
circularProgressBar = itemView.findViewById(de.danoeh.antennapod.R.id.circularProgressBar);
break;
}
}
switch(MUID_STATIC) {
// HorizontalItemViewHolder_12_InvalidViewFocusOperatorMutator
case 12000: {
/**
* Inserted by Kadabra
*/
progressBar = itemView.findViewById(de.danoeh.antennapod.R.id.progressBar);
progressBar.requestFocus();
break;
}
// HorizontalItemViewHolder_13_ViewComponentNotVisibleOperatorMutator
case 13000: {
/**
* Inserted by Kadabra
*/
progressBar = itemView.findViewById(de.danoeh.antennapod.R.id.progressBar);
progressBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progressBar = itemView.findViewById(de.danoeh.antennapod.R.id.progressBar);
break;
}
}
switch(MUID_STATIC) {
// HorizontalItemViewHolder_14_InvalidViewFocusOperatorMutator
case 14000: {
/**
* Inserted by Kadabra
*/
progressBarReplacementSpacer = itemView.findViewById(de.danoeh.antennapod.R.id.progressBarReplacementSpacer);
progressBarReplacementSpacer.requestFocus();
break;
}
// HorizontalItemViewHolder_15_ViewComponentNotVisibleOperatorMutator
case 15000: {
/**
* Inserted by Kadabra
*/
progressBarReplacementSpacer = itemView.findViewById(de.danoeh.antennapod.R.id.progressBarReplacementSpacer);
progressBarReplacementSpacer.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progressBarReplacementSpacer = itemView.findViewById(de.danoeh.antennapod.R.id.progressBarReplacementSpacer);
break;
}
}
itemView.setTag(this);
}


public void bind(de.danoeh.antennapod.model.feed.FeedItem item) {
this.item = item;
card.setAlpha(1.0F);
float density;
density = activity.getResources().getDisplayMetrics().density;
switch(MUID_STATIC) {
// HorizontalItemViewHolder_16_BinaryMutator
case 16000: {
card.setCardBackgroundColor(com.google.android.material.elevation.SurfaceColors.getColorForElevation(activity, 1 / density));
break;
}
default: {
card.setCardBackgroundColor(com.google.android.material.elevation.SurfaceColors.getColorForElevation(activity, 1 * density));
break;
}
}
new de.danoeh.antennapod.adapter.CoverLoader(activity).withUri(de.danoeh.antennapod.core.feed.util.ImageResourceUtils.getEpisodeListImageLocation(item)).withFallbackUri(item.getFeed().getImageUrl()).withCoverView(cover).load();
title.setText(item.getTitle());
date.setText(de.danoeh.antennapod.core.util.DateFormatter.formatAbbrev(activity, item.getPubDate()));
date.setContentDescription(de.danoeh.antennapod.core.util.DateFormatter.formatForAccessibility(item.getPubDate()));
de.danoeh.antennapod.adapter.actionbutton.ItemActionButton actionButton;
actionButton = de.danoeh.antennapod.adapter.actionbutton.ItemActionButton.forItem(item);
actionButton.configure(secondaryActionIcon, secondaryActionIcon, activity);
secondaryActionIcon.setFocusable(false);
de.danoeh.antennapod.model.feed.FeedMedia media;
media = item.getMedia();
if (media == null) {
circularProgressBar.setPercentage(0, item);
setProgressBar(false, 0);
} else {
if (de.danoeh.antennapod.core.util.PlaybackStatus.isCurrentlyPlaying(media)) {
card.setCardBackgroundColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(activity, de.danoeh.antennapod.R.attr.colorSurfaceVariant));
}
if ((item.getMedia().getDuration() > 0) && (item.getMedia().getPosition() > 0)) {
switch(MUID_STATIC) {
// HorizontalItemViewHolder_17_BinaryMutator
case 17000: {
setProgressBar(true, (100.0F * item.getMedia().getPosition()) * item.getMedia().getDuration());
break;
}
default: {
switch(MUID_STATIC) {
// HorizontalItemViewHolder_18_BinaryMutator
case 18000: {
setProgressBar(true, (100.0F / item.getMedia().getPosition()) / item.getMedia().getDuration());
break;
}
default: {
setProgressBar(true, (100.0F * item.getMedia().getPosition()) / item.getMedia().getDuration());
break;
}
}
break;
}
}
} else {
setProgressBar(false, 0);
}
if (de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isDownloadingEpisode(media.getDownload_url())) {
float percent;
switch(MUID_STATIC) {
// HorizontalItemViewHolder_19_BinaryMutator
case 19000: {
percent = 0.01F / de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().getProgress(media.getDownload_url());
break;
}
default: {
percent = 0.01F * de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().getProgress(media.getDownload_url());
break;
}
}
circularProgressBar.setPercentage(java.lang.Math.max(percent, 0.01F), item);
circularProgressBar.setIndeterminate(de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isEpisodeQueued(media.getDownload_url()));
} else if (media.isDownloaded()) {
circularProgressBar.setPercentage(1, item)// Do not animate 100% -> 0%
;// Do not animate 100% -> 0%

circularProgressBar.setIndeterminate(false);
} else {
circularProgressBar.setPercentage(0, item)// Animate X% -> 0%
;// Animate X% -> 0%

circularProgressBar.setIndeterminate(false);
}
}
}


public void bindDummy() {
card.setAlpha(0.1F);
new de.danoeh.antennapod.adapter.CoverLoader(activity).withResource(android.R.color.transparent).withCoverView(cover).load();
title.setText("████ █████");
date.setText("███");
secondaryActionIcon.setImageDrawable(null);
circularProgressBar.setPercentage(0, null);
circularProgressBar.setIndeterminate(false);
setProgressBar(true, 50);
}


public boolean isCurrentlyPlayingItem() {
return ((item != null) && (item.getMedia() != null)) && de.danoeh.antennapod.core.util.PlaybackStatus.isCurrentlyPlaying(item.getMedia());
}


public void notifyPlaybackPositionUpdated(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
switch(MUID_STATIC) {
// HorizontalItemViewHolder_20_BinaryMutator
case 20000: {
setProgressBar(true, (100.0F * event.getPosition()) * event.getDuration());
break;
}
default: {
switch(MUID_STATIC) {
// HorizontalItemViewHolder_21_BinaryMutator
case 21000: {
setProgressBar(true, (100.0F / event.getPosition()) / event.getDuration());
break;
}
default: {
setProgressBar(true, (100.0F * event.getPosition()) / event.getDuration());
break;
}
}
break;
}
}
}


private void setProgressBar(boolean visible, float progress) {
progressBar.setVisibility(visible ? android.view.ViewGroup.VISIBLE : android.view.ViewGroup.GONE);
progressBarReplacementSpacer.setVisibility(visible ? android.view.View.GONE : android.view.ViewGroup.VISIBLE);
progressBar.setProgress(java.lang.Math.max(5, ((int) (progress))))// otherwise invisible below the edge radius
;// otherwise invisible below the edge radius

}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
