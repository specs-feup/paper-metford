package de.danoeh.antennapod.adapter;
import android.view.ViewGroup;
import de.danoeh.antennapod.ui.common.CircularProgressBar;
import android.text.TextUtils;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import de.danoeh.antennapod.model.feed.EmbeddedChapterImage;
import de.danoeh.antennapod.model.feed.Chapter;
import android.view.View;
import androidx.core.content.ContextCompat;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.util.IntentUtils;
import de.danoeh.antennapod.model.playback.Playable;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.core.util.Converter;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.Glide;
import android.content.Context;
import com.google.android.material.elevation.SurfaceColors;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ChaptersListAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<de.danoeh.antennapod.adapter.ChaptersListAdapter.ChapterHolder> {
    static final int MUID_STATIC = getMUID();
    private de.danoeh.antennapod.model.playback.Playable media;

    private final de.danoeh.antennapod.adapter.ChaptersListAdapter.Callback callback;

    private final android.content.Context context;

    private int currentChapterIndex = -1;

    private long currentChapterPosition = -1;

    private boolean hasImages = false;

    public ChaptersListAdapter(android.content.Context context, de.danoeh.antennapod.adapter.ChaptersListAdapter.Callback callback) {
        this.callback = callback;
        this.context = context;
    }


    public void setMedia(de.danoeh.antennapod.model.playback.Playable media) {
        this.media = media;
        hasImages = false;
        if (media.getChapters() != null) {
            for (de.danoeh.antennapod.model.feed.Chapter chapter : media.getChapters()) {
                if (!android.text.TextUtils.isEmpty(chapter.getImageUrl())) {
                    hasImages = true;
                }
            }
        }
        notifyDataSetChanged();
    }


    @java.lang.Override
    public void onBindViewHolder(@androidx.annotation.NonNull
    de.danoeh.antennapod.adapter.ChaptersListAdapter.ChapterHolder holder, int position) {
        de.danoeh.antennapod.model.feed.Chapter sc;
        sc = getItem(position);
        if (sc == null) {
            holder.title.setText("Error");
            return;
        }
        holder.title.setText(sc.getTitle());
        holder.start.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(((int) (sc.getStart()))));
        long duration;
        switch(MUID_STATIC) {
            // ChaptersListAdapter_0_BinaryMutator
            case 39: {
                if ((position - 1) < media.getChapters().size()) {
                    duration = media.getChapters().get(position + 1).getStart() - sc.getStart();
                } else {
                    duration = media.getDuration() - sc.getStart();
                }
                break;
            }
            default: {
            if ((position + 1) < media.getChapters().size()) {
                switch(MUID_STATIC) {
                    // ChaptersListAdapter_1_BinaryMutator
                    case 1039: {
                        duration = media.getChapters().get(position + 1).getStart() + sc.getStart();
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // ChaptersListAdapter_2_BinaryMutator
                        case 2039: {
                            duration = media.getChapters().get(position - 1).getStart() - sc.getStart();
                            break;
                        }
                        default: {
                        duration = media.getChapters().get(position + 1).getStart() - sc.getStart();
                        break;
                    }
                }
                break;
            }
        }
    } else {
        switch(MUID_STATIC) {
            // ChaptersListAdapter_3_BinaryMutator
            case 3039: {
                duration = media.getDuration() + sc.getStart();
                break;
            }
            default: {
            duration = media.getDuration() - sc.getStart();
            break;
        }
    }
}
break;
}
}
holder.duration.setText(context.getString(de.danoeh.antennapod.R.string.chapter_duration, de.danoeh.antennapod.core.util.Converter.getDurationStringLocalized(context, ((int) (duration)))));
if (android.text.TextUtils.isEmpty(sc.getLink())) {
holder.link.setVisibility(android.view.View.GONE);
} else {
holder.link.setVisibility(android.view.View.VISIBLE);
holder.link.setText(sc.getLink());
switch(MUID_STATIC) {
// ChaptersListAdapter_4_BuggyGUIListenerOperatorMutator
case 4039: {
    holder.link.setOnClickListener(null);
    break;
}
default: {
holder.link.setOnClickListener((android.view.View v) -> de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(context, sc.getLink()));
break;
}
}
}
holder.secondaryActionIcon.setImageResource(de.danoeh.antennapod.R.drawable.ic_play_48dp);
holder.secondaryActionButton.setContentDescription(context.getString(de.danoeh.antennapod.R.string.play_chapter));
switch(MUID_STATIC) {
// ChaptersListAdapter_5_BuggyGUIListenerOperatorMutator
case 5039: {
holder.secondaryActionButton.setOnClickListener(null);
break;
}
default: {
holder.secondaryActionButton.setOnClickListener((android.view.View v) -> {
if (callback != null) {
callback.onPlayChapterButtonClicked(position);
}
});
break;
}
}
if (position == currentChapterIndex) {
float density;
density = context.getResources().getDisplayMetrics().density;
switch(MUID_STATIC) {
// ChaptersListAdapter_6_BinaryMutator
case 6039: {
holder.itemView.setBackgroundColor(com.google.android.material.elevation.SurfaceColors.getColorForElevation(context, 32 / density));
break;
}
default: {
holder.itemView.setBackgroundColor(com.google.android.material.elevation.SurfaceColors.getColorForElevation(context, 32 * density));
break;
}
}
float progress;
switch(MUID_STATIC) {
// ChaptersListAdapter_7_BinaryMutator
case 7039: {
progress = ((float) (currentChapterPosition - sc.getStart())) * duration;
break;
}
default: {
switch(MUID_STATIC) {
// ChaptersListAdapter_8_BinaryMutator
case 8039: {
progress = ((float) (currentChapterPosition + sc.getStart())) / duration;
break;
}
default: {
progress = ((float) (currentChapterPosition - sc.getStart())) / duration;
break;
}
}
break;
}
}
progress = java.lang.Math.max(progress, de.danoeh.antennapod.ui.common.CircularProgressBar.MINIMUM_PERCENTAGE);
progress = java.lang.Math.min(progress, de.danoeh.antennapod.ui.common.CircularProgressBar.MAXIMUM_PERCENTAGE);
holder.progressBar.setPercentage(progress, position);
holder.secondaryActionIcon.setImageResource(de.danoeh.antennapod.R.drawable.ic_replay);
} else {
holder.itemView.setBackgroundColor(androidx.core.content.ContextCompat.getColor(context, android.R.color.transparent));
holder.progressBar.setPercentage(0, null);
}
if (hasImages) {
holder.image.setVisibility(android.view.View.VISIBLE);
if (android.text.TextUtils.isEmpty(sc.getImageUrl())) {
com.bumptech.glide.Glide.with(context).clear(holder.image);
} else {
switch(MUID_STATIC) {
// ChaptersListAdapter_9_BinaryMutator
case 9039: {
com.bumptech.glide.Glide.with(context).load(de.danoeh.antennapod.model.feed.EmbeddedChapterImage.getModelFor(media, position)).apply(new com.bumptech.glide.request.RequestOptions().dontAnimate().transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (4 / context.getResources().getDisplayMetrics().density))))).into(holder.image);
break;
}
default: {
com.bumptech.glide.Glide.with(context).load(de.danoeh.antennapod.model.feed.EmbeddedChapterImage.getModelFor(media, position)).apply(new com.bumptech.glide.request.RequestOptions().dontAnimate().transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (4 * context.getResources().getDisplayMetrics().density))))).into(holder.image);
break;
}
}
}
} else {
holder.image.setVisibility(android.view.View.GONE);
}
}


@androidx.annotation.NonNull
@java.lang.Override
public de.danoeh.antennapod.adapter.ChaptersListAdapter.ChapterHolder onCreateViewHolder(@androidx.annotation.NonNull
android.view.ViewGroup parent, int viewType) {
android.view.LayoutInflater inflater;
inflater = android.view.LayoutInflater.from(context);
return new de.danoeh.antennapod.adapter.ChaptersListAdapter.ChapterHolder(inflater.inflate(de.danoeh.antennapod.R.layout.simplechapter_item, parent, false));
}


@java.lang.Override
public int getItemCount() {
if ((media == null) || (media.getChapters() == null)) {
return 0;
}
return media.getChapters().size();
}


static class ChapterHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
final android.widget.TextView title;

final android.widget.TextView start;

final android.widget.TextView link;

final android.widget.TextView duration;

final android.widget.ImageView image;

final android.view.View secondaryActionButton;

final android.widget.ImageView secondaryActionIcon;

final de.danoeh.antennapod.ui.common.CircularProgressBar progressBar;

public ChapterHolder(@androidx.annotation.NonNull
android.view.View itemView) {
super(itemView);
switch(MUID_STATIC) {
// ChaptersListAdapter_10_InvalidViewFocusOperatorMutator
case 10039: {
/**
* Inserted by Kadabra
*/
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.requestFocus();
break;
}
// ChaptersListAdapter_11_ViewComponentNotVisibleOperatorMutator
case 11039: {
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
switch(MUID_STATIC) {
// ChaptersListAdapter_12_InvalidViewFocusOperatorMutator
case 12039: {
/**
* Inserted by Kadabra
*/
start = itemView.findViewById(de.danoeh.antennapod.R.id.txtvStart);
start.requestFocus();
break;
}
// ChaptersListAdapter_13_ViewComponentNotVisibleOperatorMutator
case 13039: {
/**
* Inserted by Kadabra
*/
start = itemView.findViewById(de.danoeh.antennapod.R.id.txtvStart);
start.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
start = itemView.findViewById(de.danoeh.antennapod.R.id.txtvStart);
break;
}
}
switch(MUID_STATIC) {
// ChaptersListAdapter_14_InvalidViewFocusOperatorMutator
case 14039: {
/**
* Inserted by Kadabra
*/
link = itemView.findViewById(de.danoeh.antennapod.R.id.txtvLink);
link.requestFocus();
break;
}
// ChaptersListAdapter_15_ViewComponentNotVisibleOperatorMutator
case 15039: {
/**
* Inserted by Kadabra
*/
link = itemView.findViewById(de.danoeh.antennapod.R.id.txtvLink);
link.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
link = itemView.findViewById(de.danoeh.antennapod.R.id.txtvLink);
break;
}
}
switch(MUID_STATIC) {
// ChaptersListAdapter_16_InvalidViewFocusOperatorMutator
case 16039: {
/**
* Inserted by Kadabra
*/
image = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
image.requestFocus();
break;
}
// ChaptersListAdapter_17_ViewComponentNotVisibleOperatorMutator
case 17039: {
/**
* Inserted by Kadabra
*/
image = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
image.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
image = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
break;
}
}
switch(MUID_STATIC) {
// ChaptersListAdapter_18_InvalidViewFocusOperatorMutator
case 18039: {
/**
* Inserted by Kadabra
*/
duration = itemView.findViewById(de.danoeh.antennapod.R.id.txtvDuration);
duration.requestFocus();
break;
}
// ChaptersListAdapter_19_ViewComponentNotVisibleOperatorMutator
case 19039: {
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
// ChaptersListAdapter_20_InvalidViewFocusOperatorMutator
case 20039: {
/**
* Inserted by Kadabra
*/
secondaryActionButton = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionButton);
secondaryActionButton.requestFocus();
break;
}
// ChaptersListAdapter_21_ViewComponentNotVisibleOperatorMutator
case 21039: {
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
// ChaptersListAdapter_22_InvalidViewFocusOperatorMutator
case 22039: {
/**
* Inserted by Kadabra
*/
secondaryActionIcon = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionIcon);
secondaryActionIcon.requestFocus();
break;
}
// ChaptersListAdapter_23_ViewComponentNotVisibleOperatorMutator
case 23039: {
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
// ChaptersListAdapter_24_InvalidViewFocusOperatorMutator
case 24039: {
/**
* Inserted by Kadabra
*/
progressBar = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionProgress);
progressBar.requestFocus();
break;
}
// ChaptersListAdapter_25_ViewComponentNotVisibleOperatorMutator
case 25039: {
/**
* Inserted by Kadabra
*/
progressBar = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionProgress);
progressBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progressBar = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionProgress);
break;
}
}
}

}

public void notifyChapterChanged(int newChapterIndex) {
currentChapterIndex = newChapterIndex;
currentChapterPosition = getItem(newChapterIndex).getStart();
notifyDataSetChanged();
}


public void notifyTimeChanged(long timeMs) {
currentChapterPosition = timeMs;
// Passing an argument prevents flickering.
// See EpisodeItemListAdapter.notifyItemChangedCompat.
notifyItemChanged(currentChapterIndex, "foo");
}


public de.danoeh.antennapod.model.feed.Chapter getItem(int position) {
return media.getChapters().get(position);
}


public interface Callback {
void onPlayChapterButtonClicked(int position);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
