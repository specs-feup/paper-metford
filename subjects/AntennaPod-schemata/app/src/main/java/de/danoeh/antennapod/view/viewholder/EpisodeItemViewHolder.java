package de.danoeh.antennapod.view.viewholder;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.core.util.PlaybackStatus;
import android.text.format.Formatter;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import de.danoeh.antennapod.ui.common.CircularProgressBar;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.core.util.NetworkUtils;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.adapter.actionbutton.ItemActionButton;
import android.os.Build;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.model.playback.MediaType;
import de.danoeh.antennapod.core.util.Converter;
import android.widget.ImageView;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import android.widget.TextView;
import android.widget.ProgressBar;
import de.danoeh.antennapod.adapter.CoverLoader;
import com.google.android.material.elevation.SurfaceColors;
import com.joanzapata.iconify.Iconify;
import android.util.Log;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import de.danoeh.antennapod.core.util.download.MediaSizeLoader;
import android.view.View;
import android.text.Layout;
import android.view.LayoutInflater;
import de.danoeh.antennapod.model.feed.FeedMedia;
import de.danoeh.antennapod.model.playback.Playable;
import de.danoeh.antennapod.core.util.DateFormatter;
import de.danoeh.antennapod.core.feed.util.ImageResourceUtils;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Holds the view which shows FeedItems.
 */
public class EpisodeItemViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "EpisodeItemViewHolder";

    private final android.view.View container;

    public final android.widget.ImageView dragHandle;

    private final android.widget.TextView placeholder;

    private final android.widget.ImageView cover;

    private final android.widget.TextView title;

    private final android.widget.TextView pubDate;

    private final android.widget.TextView position;

    private final android.widget.TextView duration;

    private final android.widget.TextView size;

    public final android.widget.ImageView isInbox;

    public final android.widget.ImageView isInQueue;

    private final android.widget.ImageView isVideo;

    public final android.widget.ImageView isFavorite;

    private final android.widget.ProgressBar progressBar;

    public final android.view.View secondaryActionButton;

    public final android.widget.ImageView secondaryActionIcon;

    private final de.danoeh.antennapod.ui.common.CircularProgressBar secondaryActionProgress;

    private final android.widget.TextView separatorIcons;

    private final android.view.View leftPadding;

    public final androidx.cardview.widget.CardView coverHolder;

    private final de.danoeh.antennapod.activity.MainActivity activity;

    private de.danoeh.antennapod.model.feed.FeedItem item;

    public EpisodeItemViewHolder(de.danoeh.antennapod.activity.MainActivity activity, android.view.ViewGroup parent) {
        super(android.view.LayoutInflater.from(activity).inflate(de.danoeh.antennapod.R.layout.feeditemlist_item, parent, false));
        this.activity = activity;
        switch(MUID_STATIC) {
            // EpisodeItemViewHolder_0_InvalidViewFocusOperatorMutator
            case 2: {
                /**
                * Inserted by Kadabra
                */
                container = itemView.findViewById(de.danoeh.antennapod.R.id.container);
                container.requestFocus();
                break;
            }
            // EpisodeItemViewHolder_1_ViewComponentNotVisibleOperatorMutator
            case 1002: {
                /**
                * Inserted by Kadabra
                */
                container = itemView.findViewById(de.danoeh.antennapod.R.id.container);
                container.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            container = itemView.findViewById(de.danoeh.antennapod.R.id.container);
            break;
        }
    }
    switch(MUID_STATIC) {
        // EpisodeItemViewHolder_2_InvalidViewFocusOperatorMutator
        case 2002: {
            /**
            * Inserted by Kadabra
            */
            dragHandle = itemView.findViewById(de.danoeh.antennapod.R.id.drag_handle);
            dragHandle.requestFocus();
            break;
        }
        // EpisodeItemViewHolder_3_ViewComponentNotVisibleOperatorMutator
        case 3002: {
            /**
            * Inserted by Kadabra
            */
            dragHandle = itemView.findViewById(de.danoeh.antennapod.R.id.drag_handle);
            dragHandle.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        dragHandle = itemView.findViewById(de.danoeh.antennapod.R.id.drag_handle);
        break;
    }
}
switch(MUID_STATIC) {
    // EpisodeItemViewHolder_4_InvalidViewFocusOperatorMutator
    case 4002: {
        /**
        * Inserted by Kadabra
        */
        placeholder = itemView.findViewById(de.danoeh.antennapod.R.id.txtvPlaceholder);
        placeholder.requestFocus();
        break;
    }
    // EpisodeItemViewHolder_5_ViewComponentNotVisibleOperatorMutator
    case 5002: {
        /**
        * Inserted by Kadabra
        */
        placeholder = itemView.findViewById(de.danoeh.antennapod.R.id.txtvPlaceholder);
        placeholder.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    placeholder = itemView.findViewById(de.danoeh.antennapod.R.id.txtvPlaceholder);
    break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_6_InvalidViewFocusOperatorMutator
case 6002: {
    /**
    * Inserted by Kadabra
    */
    cover = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
    cover.requestFocus();
    break;
}
// EpisodeItemViewHolder_7_ViewComponentNotVisibleOperatorMutator
case 7002: {
    /**
    * Inserted by Kadabra
    */
    cover = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
    cover.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
cover = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_8_InvalidViewFocusOperatorMutator
case 8002: {
/**
* Inserted by Kadabra
*/
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.requestFocus();
break;
}
// EpisodeItemViewHolder_9_ViewComponentNotVisibleOperatorMutator
case 9002: {
/**
* Inserted by Kadabra
*/
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
break;
}
}
if (android.os.Build.VERSION.SDK_INT >= 23) {
title.setHyphenationFrequency(android.text.Layout.HYPHENATION_FREQUENCY_FULL);
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_10_InvalidViewFocusOperatorMutator
case 10002: {
/**
* Inserted by Kadabra
*/
pubDate = itemView.findViewById(de.danoeh.antennapod.R.id.txtvPubDate);
pubDate.requestFocus();
break;
}
// EpisodeItemViewHolder_11_ViewComponentNotVisibleOperatorMutator
case 11002: {
/**
* Inserted by Kadabra
*/
pubDate = itemView.findViewById(de.danoeh.antennapod.R.id.txtvPubDate);
pubDate.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
pubDate = itemView.findViewById(de.danoeh.antennapod.R.id.txtvPubDate);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_12_InvalidViewFocusOperatorMutator
case 12002: {
/**
* Inserted by Kadabra
*/
position = itemView.findViewById(de.danoeh.antennapod.R.id.txtvPosition);
position.requestFocus();
break;
}
// EpisodeItemViewHolder_13_ViewComponentNotVisibleOperatorMutator
case 13002: {
/**
* Inserted by Kadabra
*/
position = itemView.findViewById(de.danoeh.antennapod.R.id.txtvPosition);
position.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
position = itemView.findViewById(de.danoeh.antennapod.R.id.txtvPosition);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_14_InvalidViewFocusOperatorMutator
case 14002: {
/**
* Inserted by Kadabra
*/
duration = itemView.findViewById(de.danoeh.antennapod.R.id.txtvDuration);
duration.requestFocus();
break;
}
// EpisodeItemViewHolder_15_ViewComponentNotVisibleOperatorMutator
case 15002: {
/**
* Inserted by Kadabra
*/
duration = itemView.findViewById(de.danoeh.antennapod.R.id.txtvDuration);
duration.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
duration = itemView.findViewById(de.danoeh.antennapod.R.id.txtvDuration);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_16_InvalidViewFocusOperatorMutator
case 16002: {
/**
* Inserted by Kadabra
*/
progressBar = itemView.findViewById(de.danoeh.antennapod.R.id.progressBar);
progressBar.requestFocus();
break;
}
// EpisodeItemViewHolder_17_ViewComponentNotVisibleOperatorMutator
case 17002: {
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
// EpisodeItemViewHolder_18_InvalidViewFocusOperatorMutator
case 18002: {
/**
* Inserted by Kadabra
*/
isInQueue = itemView.findViewById(de.danoeh.antennapod.R.id.ivInPlaylist);
isInQueue.requestFocus();
break;
}
// EpisodeItemViewHolder_19_ViewComponentNotVisibleOperatorMutator
case 19002: {
/**
* Inserted by Kadabra
*/
isInQueue = itemView.findViewById(de.danoeh.antennapod.R.id.ivInPlaylist);
isInQueue.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
isInQueue = itemView.findViewById(de.danoeh.antennapod.R.id.ivInPlaylist);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_20_InvalidViewFocusOperatorMutator
case 20002: {
/**
* Inserted by Kadabra
*/
isVideo = itemView.findViewById(de.danoeh.antennapod.R.id.ivIsVideo);
isVideo.requestFocus();
break;
}
// EpisodeItemViewHolder_21_ViewComponentNotVisibleOperatorMutator
case 21002: {
/**
* Inserted by Kadabra
*/
isVideo = itemView.findViewById(de.danoeh.antennapod.R.id.ivIsVideo);
isVideo.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
isVideo = itemView.findViewById(de.danoeh.antennapod.R.id.ivIsVideo);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_22_InvalidViewFocusOperatorMutator
case 22002: {
/**
* Inserted by Kadabra
*/
isInbox = itemView.findViewById(de.danoeh.antennapod.R.id.statusInbox);
isInbox.requestFocus();
break;
}
// EpisodeItemViewHolder_23_ViewComponentNotVisibleOperatorMutator
case 23002: {
/**
* Inserted by Kadabra
*/
isInbox = itemView.findViewById(de.danoeh.antennapod.R.id.statusInbox);
isInbox.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
isInbox = itemView.findViewById(de.danoeh.antennapod.R.id.statusInbox);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_24_InvalidViewFocusOperatorMutator
case 24002: {
/**
* Inserted by Kadabra
*/
isFavorite = itemView.findViewById(de.danoeh.antennapod.R.id.isFavorite);
isFavorite.requestFocus();
break;
}
// EpisodeItemViewHolder_25_ViewComponentNotVisibleOperatorMutator
case 25002: {
/**
* Inserted by Kadabra
*/
isFavorite = itemView.findViewById(de.danoeh.antennapod.R.id.isFavorite);
isFavorite.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
isFavorite = itemView.findViewById(de.danoeh.antennapod.R.id.isFavorite);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_26_InvalidViewFocusOperatorMutator
case 26002: {
/**
* Inserted by Kadabra
*/
size = itemView.findViewById(de.danoeh.antennapod.R.id.size);
size.requestFocus();
break;
}
// EpisodeItemViewHolder_27_ViewComponentNotVisibleOperatorMutator
case 27002: {
/**
* Inserted by Kadabra
*/
size = itemView.findViewById(de.danoeh.antennapod.R.id.size);
size.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
size = itemView.findViewById(de.danoeh.antennapod.R.id.size);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_28_InvalidViewFocusOperatorMutator
case 28002: {
/**
* Inserted by Kadabra
*/
separatorIcons = itemView.findViewById(de.danoeh.antennapod.R.id.separatorIcons);
separatorIcons.requestFocus();
break;
}
// EpisodeItemViewHolder_29_ViewComponentNotVisibleOperatorMutator
case 29002: {
/**
* Inserted by Kadabra
*/
separatorIcons = itemView.findViewById(de.danoeh.antennapod.R.id.separatorIcons);
separatorIcons.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
separatorIcons = itemView.findViewById(de.danoeh.antennapod.R.id.separatorIcons);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_30_InvalidViewFocusOperatorMutator
case 30002: {
/**
* Inserted by Kadabra
*/
secondaryActionProgress = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionProgress);
secondaryActionProgress.requestFocus();
break;
}
// EpisodeItemViewHolder_31_ViewComponentNotVisibleOperatorMutator
case 31002: {
/**
* Inserted by Kadabra
*/
secondaryActionProgress = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionProgress);
secondaryActionProgress.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
secondaryActionProgress = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionProgress);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_32_InvalidViewFocusOperatorMutator
case 32002: {
/**
* Inserted by Kadabra
*/
secondaryActionButton = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionButton);
secondaryActionButton.requestFocus();
break;
}
// EpisodeItemViewHolder_33_ViewComponentNotVisibleOperatorMutator
case 33002: {
/**
* Inserted by Kadabra
*/
secondaryActionButton = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionButton);
secondaryActionButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
secondaryActionButton = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionButton);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_34_InvalidViewFocusOperatorMutator
case 34002: {
/**
* Inserted by Kadabra
*/
secondaryActionIcon = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionIcon);
secondaryActionIcon.requestFocus();
break;
}
// EpisodeItemViewHolder_35_ViewComponentNotVisibleOperatorMutator
case 35002: {
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
// EpisodeItemViewHolder_36_InvalidViewFocusOperatorMutator
case 36002: {
/**
* Inserted by Kadabra
*/
coverHolder = itemView.findViewById(de.danoeh.antennapod.R.id.coverHolder);
coverHolder.requestFocus();
break;
}
// EpisodeItemViewHolder_37_ViewComponentNotVisibleOperatorMutator
case 37002: {
/**
* Inserted by Kadabra
*/
coverHolder = itemView.findViewById(de.danoeh.antennapod.R.id.coverHolder);
coverHolder.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
coverHolder = itemView.findViewById(de.danoeh.antennapod.R.id.coverHolder);
break;
}
}
switch(MUID_STATIC) {
// EpisodeItemViewHolder_38_InvalidViewFocusOperatorMutator
case 38002: {
/**
* Inserted by Kadabra
*/
leftPadding = itemView.findViewById(de.danoeh.antennapod.R.id.left_padding);
leftPadding.requestFocus();
break;
}
// EpisodeItemViewHolder_39_ViewComponentNotVisibleOperatorMutator
case 39002: {
/**
* Inserted by Kadabra
*/
leftPadding = itemView.findViewById(de.danoeh.antennapod.R.id.left_padding);
leftPadding.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
leftPadding = itemView.findViewById(de.danoeh.antennapod.R.id.left_padding);
break;
}
}
itemView.setTag(this);
}


public void bind(de.danoeh.antennapod.model.feed.FeedItem item) {
this.item = item;
placeholder.setText(item.getFeed().getTitle());
title.setText(item.getTitle());
leftPadding.setContentDescription(item.getTitle());
pubDate.setText(de.danoeh.antennapod.core.util.DateFormatter.formatAbbrev(activity, item.getPubDate()));
pubDate.setContentDescription(de.danoeh.antennapod.core.util.DateFormatter.formatForAccessibility(item.getPubDate()));
if (item.isPlayed()) {
cover.setContentDescription(activity.getString(de.danoeh.antennapod.R.string.is_played));
cover.setImportantForAccessibility(android.view.View.IMPORTANT_FOR_ACCESSIBILITY_YES);
} else {
cover.setImportantForAccessibility(android.view.View.IMPORTANT_FOR_ACCESSIBILITY_NO);
}
isInbox.setVisibility(item.isNew() ? android.view.View.VISIBLE : android.view.View.GONE);
isFavorite.setVisibility(item.isTagged(de.danoeh.antennapod.model.feed.FeedItem.TAG_FAVORITE) ? android.view.View.VISIBLE : android.view.View.GONE);
isInQueue.setVisibility(item.isTagged(de.danoeh.antennapod.model.feed.FeedItem.TAG_QUEUE) ? android.view.View.VISIBLE : android.view.View.GONE);
container.setAlpha(item.isPlayed() ? 0.5F : 1.0F);
de.danoeh.antennapod.adapter.actionbutton.ItemActionButton actionButton;
actionButton = de.danoeh.antennapod.adapter.actionbutton.ItemActionButton.forItem(item);
actionButton.configure(secondaryActionButton, secondaryActionIcon, activity);
secondaryActionButton.setFocusable(false);
if (item.getMedia() != null) {
bind(item.getMedia());
} else {
secondaryActionProgress.setPercentage(0, item);
secondaryActionProgress.setIndeterminate(false);
isVideo.setVisibility(android.view.View.GONE);
progressBar.setVisibility(android.view.View.GONE);
duration.setVisibility(android.view.View.GONE);
position.setVisibility(android.view.View.GONE);
itemView.setBackgroundResource(de.danoeh.antennapod.ui.common.ThemeUtils.getDrawableFromAttr(activity, de.danoeh.antennapod.R.attr.selectableItemBackground));
}
if (coverHolder.getVisibility() == android.view.View.VISIBLE) {
new de.danoeh.antennapod.adapter.CoverLoader(activity).withUri(de.danoeh.antennapod.core.feed.util.ImageResourceUtils.getEpisodeListImageLocation(item)).withFallbackUri(item.getFeed().getImageUrl()).withPlaceholderView(placeholder).withCoverView(cover).load();
}
}


private void bind(de.danoeh.antennapod.model.feed.FeedMedia media) {
isVideo.setVisibility(media.getMediaType() == de.danoeh.antennapod.model.playback.MediaType.VIDEO ? android.view.View.VISIBLE : android.view.View.GONE);
duration.setVisibility(media.getDuration() > 0 ? android.view.View.VISIBLE : android.view.View.GONE);
if (de.danoeh.antennapod.core.util.PlaybackStatus.isCurrentlyPlaying(media)) {
float density;
density = activity.getResources().getDisplayMetrics().density;
switch(MUID_STATIC) {
// EpisodeItemViewHolder_40_BinaryMutator
case 40002: {
itemView.setBackgroundColor(com.google.android.material.elevation.SurfaceColors.getColorForElevation(activity, 8 / density));
break;
}
default: {
itemView.setBackgroundColor(com.google.android.material.elevation.SurfaceColors.getColorForElevation(activity, 8 * density));
break;
}
}
} else {
itemView.setBackgroundResource(de.danoeh.antennapod.ui.common.ThemeUtils.getDrawableFromAttr(activity, de.danoeh.antennapod.R.attr.selectableItemBackground));
}
if (de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isDownloadingEpisode(media.getDownload_url())) {
float percent;
switch(MUID_STATIC) {
// EpisodeItemViewHolder_41_BinaryMutator
case 41002: {
percent = 0.01F / de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().getProgress(media.getDownload_url());
break;
}
default: {
percent = 0.01F * de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().getProgress(media.getDownload_url());
break;
}
}
secondaryActionProgress.setPercentage(java.lang.Math.max(percent, 0.01F), item);
secondaryActionProgress.setIndeterminate(de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isEpisodeQueued(media.getDownload_url()));
} else if (media.isDownloaded()) {
secondaryActionProgress.setPercentage(1, item)// Do not animate 100% -> 0%
;// Do not animate 100% -> 0%

secondaryActionProgress.setIndeterminate(false);
} else {
secondaryActionProgress.setPercentage(0, item)// Animate X% -> 0%
;// Animate X% -> 0%

secondaryActionProgress.setIndeterminate(false);
}
duration.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(media.getDuration()));
duration.setContentDescription(activity.getString(de.danoeh.antennapod.R.string.chapter_duration, de.danoeh.antennapod.core.util.Converter.getDurationStringLocalized(activity, media.getDuration())));
if (de.danoeh.antennapod.core.util.PlaybackStatus.isPlaying(item.getMedia()) || item.isInProgress()) {
int progress;
switch(MUID_STATIC) {
// EpisodeItemViewHolder_42_BinaryMutator
case 42002: {
progress = ((int) ((100.0 * media.getPosition()) * media.getDuration()));
break;
}
default: {
switch(MUID_STATIC) {
// EpisodeItemViewHolder_43_BinaryMutator
case 43002: {
progress = ((int) ((100.0 / media.getPosition()) / media.getDuration()));
break;
}
default: {
progress = ((int) ((100.0 * media.getPosition()) / media.getDuration()));
break;
}
}
break;
}
}
int remainingTime;
switch(MUID_STATIC) {
// EpisodeItemViewHolder_44_BinaryMutator
case 44002: {
remainingTime = java.lang.Math.max(media.getDuration() + media.getPosition(), 0);
break;
}
default: {
remainingTime = java.lang.Math.max(media.getDuration() - media.getPosition(), 0);
break;
}
}
progressBar.setProgress(progress);
position.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(media.getPosition()));
position.setContentDescription(activity.getString(de.danoeh.antennapod.R.string.position, de.danoeh.antennapod.core.util.Converter.getDurationStringLocalized(activity, media.getPosition())));
progressBar.setVisibility(android.view.View.VISIBLE);
position.setVisibility(android.view.View.VISIBLE);
if (de.danoeh.antennapod.storage.preferences.UserPreferences.shouldShowRemainingTime()) {
duration.setText((remainingTime > 0 ? "-" : "") + de.danoeh.antennapod.core.util.Converter.getDurationStringLong(remainingTime));
switch(MUID_STATIC) {
// EpisodeItemViewHolder_45_BinaryMutator
case 45002: {
duration.setContentDescription(activity.getString(de.danoeh.antennapod.R.string.chapter_duration, de.danoeh.antennapod.core.util.Converter.getDurationStringLocalized(activity, media.getDuration() + media.getPosition())));
break;
}
default: {
duration.setContentDescription(activity.getString(de.danoeh.antennapod.R.string.chapter_duration, de.danoeh.antennapod.core.util.Converter.getDurationStringLocalized(activity, media.getDuration() - media.getPosition())));
break;
}
}
}
} else {
progressBar.setVisibility(android.view.View.GONE);
position.setVisibility(android.view.View.GONE);
}
if (media.getSize() > 0) {
size.setText(android.text.format.Formatter.formatShortFileSize(activity, media.getSize()));
} else if (de.danoeh.antennapod.core.util.NetworkUtils.isEpisodeHeadDownloadAllowed() && (!media.checkedOnSizeButUnknown())) {
size.setText("{fa-spinner}");
com.joanzapata.iconify.Iconify.addIcons(size);
de.danoeh.antennapod.core.util.download.MediaSizeLoader.getFeedMediaSizeObservable(media).subscribe((java.lang.Long sizeValue) -> {
if (sizeValue > 0) {
size.setText(android.text.format.Formatter.formatShortFileSize(activity, sizeValue));
} else {
size.setText("");
}
}, (java.lang.Throwable error) -> {
size.setText("");
android.util.Log.e(de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder.TAG, android.util.Log.getStackTraceString(error));
});
} else {
size.setText("");
}
}


public void bindDummy() {
item = new de.danoeh.antennapod.model.feed.FeedItem();
container.setAlpha(0.1F);
secondaryActionIcon.setImageDrawable(null);
isInbox.setVisibility(android.view.View.VISIBLE);
isVideo.setVisibility(android.view.View.GONE);
isFavorite.setVisibility(android.view.View.GONE);
isInQueue.setVisibility(android.view.View.GONE);
title.setText("███████");
pubDate.setText("████");
duration.setText("████");
secondaryActionProgress.setPercentage(0, null);
secondaryActionProgress.setIndeterminate(false);
progressBar.setVisibility(android.view.View.GONE);
position.setVisibility(android.view.View.GONE);
dragHandle.setVisibility(android.view.View.GONE);
size.setText("");
itemView.setBackgroundResource(de.danoeh.antennapod.ui.common.ThemeUtils.getDrawableFromAttr(activity, de.danoeh.antennapod.R.attr.selectableItemBackground));
placeholder.setText("");
if (coverHolder.getVisibility() == android.view.View.VISIBLE) {
new de.danoeh.antennapod.adapter.CoverLoader(activity).withResource(de.danoeh.antennapod.R.color.medium_gray).withPlaceholderView(placeholder).withCoverView(cover).load();
}
}


private void updateDuration(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
if (getFeedItem().getMedia() != null) {
getFeedItem().getMedia().setPosition(event.getPosition());
getFeedItem().getMedia().setDuration(event.getDuration());
}
int currentPosition;
currentPosition = event.getPosition();
int timeDuration;
timeDuration = event.getDuration();
int remainingTime;
switch(MUID_STATIC) {
// EpisodeItemViewHolder_46_BinaryMutator
case 46002: {
remainingTime = java.lang.Math.max(timeDuration + currentPosition, 0);
break;
}
default: {
remainingTime = java.lang.Math.max(timeDuration - currentPosition, 0);
break;
}
}
android.util.Log.d(de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder.TAG, "currentPosition " + de.danoeh.antennapod.core.util.Converter.getDurationStringLong(currentPosition));
if ((currentPosition == de.danoeh.antennapod.model.playback.Playable.INVALID_TIME) || (timeDuration == de.danoeh.antennapod.model.playback.Playable.INVALID_TIME)) {
android.util.Log.w(de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder.TAG, "Could not react to position observer update because of invalid time");
return;
}
if (de.danoeh.antennapod.storage.preferences.UserPreferences.shouldShowRemainingTime()) {
duration.setText((remainingTime > 0 ? "-" : "") + de.danoeh.antennapod.core.util.Converter.getDurationStringLong(remainingTime));
} else {
duration.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(timeDuration));
}
}


public de.danoeh.antennapod.model.feed.FeedItem getFeedItem() {
return item;
}


public boolean isCurrentlyPlayingItem() {
return (item.getMedia() != null) && de.danoeh.antennapod.core.util.PlaybackStatus.isCurrentlyPlaying(item.getMedia());
}


public void notifyPlaybackPositionUpdated(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
switch(MUID_STATIC) {
// EpisodeItemViewHolder_47_BinaryMutator
case 47002: {
progressBar.setProgress(((int) ((100.0 * event.getPosition()) * event.getDuration())));
break;
}
default: {
switch(MUID_STATIC) {
// EpisodeItemViewHolder_48_BinaryMutator
case 48002: {
progressBar.setProgress(((int) ((100.0 / event.getPosition()) / event.getDuration())));
break;
}
default: {
progressBar.setProgress(((int) ((100.0 * event.getPosition()) / event.getDuration())));
break;
}
}
break;
}
}
position.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(event.getPosition()));
updateDuration(event);
duration.setVisibility(android.view.View.VISIBLE)// Even if the duration was previously unknown, it is now known
;// Even if the duration was previously unknown, it is now known

}


/**
 * Hides the separator dot between icons and text if there are no icons.
 */
public void hideSeparatorIfNecessary() {
boolean hasIcons;
hasIcons = ((((isInbox.getVisibility() == android.view.View.VISIBLE) || (isInQueue.getVisibility() == android.view.View.VISIBLE)) || (isVideo.getVisibility() == android.view.View.VISIBLE)) || (isFavorite.getVisibility() == android.view.View.VISIBLE)) || (isInbox.getVisibility() == android.view.View.VISIBLE);
separatorIcons.setVisibility(hasIcons ? android.view.View.VISIBLE : android.view.View.GONE);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
