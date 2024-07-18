package de.danoeh.antennapod.adapter;
import de.danoeh.antennapod.dialog.StreamingConfirmationDialog;
import de.danoeh.antennapod.model.playback.RemoteMedia;
import de.danoeh.antennapod.core.util.syndication.HtmlToPlainText;
import android.view.ViewGroup;
import de.danoeh.antennapod.core.util.playback.PlaybackServiceStarter;
import de.danoeh.antennapod.core.service.playback.PlaybackService;
import android.view.View;
import de.danoeh.antennapod.core.util.NetworkUtils;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import android.widget.Button;
import de.danoeh.antennapod.model.playback.Playable;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.model.playback.MediaType;
import de.danoeh.antennapod.core.util.DateFormatter;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.Nullable;
import android.content.Context;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * List adapter for showing a list of FeedItems with their title and description.
 */
public class FeedItemlistDescriptionAdapter extends android.widget.ArrayAdapter<de.danoeh.antennapod.model.feed.FeedItem> {
    static final int MUID_STATIC = getMUID();
    private static final int MAX_LINES_COLLAPSED = 2;

    public FeedItemlistDescriptionAdapter(android.content.Context context, int resource, java.util.List<de.danoeh.antennapod.model.feed.FeedItem> objects) {
        super(context, resource, objects);
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.view.View getView(int position, @androidx.annotation.Nullable
    android.view.View convertView, @androidx.annotation.NonNull
    android.view.ViewGroup parent) {
        de.danoeh.antennapod.adapter.FeedItemlistDescriptionAdapter.Holder holder;
        de.danoeh.antennapod.model.feed.FeedItem item;
        item = getItem(position);
        // Inflate layout
        if (convertView == null) {
            holder = new de.danoeh.antennapod.adapter.FeedItemlistDescriptionAdapter.Holder();
            android.view.LayoutInflater inflater;
            inflater = ((android.view.LayoutInflater) (getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
            convertView = inflater.inflate(de.danoeh.antennapod.R.layout.itemdescription_listitem, parent, false);
            switch(MUID_STATIC) {
                // FeedItemlistDescriptionAdapter_0_InvalidViewFocusOperatorMutator
                case 33: {
                    /**
                    * Inserted by Kadabra
                    */
                    holder.title = convertView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
                    holder.title.requestFocus();
                    break;
                }
                // FeedItemlistDescriptionAdapter_1_ViewComponentNotVisibleOperatorMutator
                case 1033: {
                    /**
                    * Inserted by Kadabra
                    */
                    holder.title = convertView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
                    holder.title.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                holder.title = convertView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
                break;
            }
        }
        switch(MUID_STATIC) {
            // FeedItemlistDescriptionAdapter_2_InvalidViewFocusOperatorMutator
            case 2033: {
                /**
                * Inserted by Kadabra
                */
                holder.pubDate = convertView.findViewById(de.danoeh.antennapod.R.id.txtvPubDate);
                holder.pubDate.requestFocus();
                break;
            }
            // FeedItemlistDescriptionAdapter_3_ViewComponentNotVisibleOperatorMutator
            case 3033: {
                /**
                * Inserted by Kadabra
                */
                holder.pubDate = convertView.findViewById(de.danoeh.antennapod.R.id.txtvPubDate);
                holder.pubDate.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            holder.pubDate = convertView.findViewById(de.danoeh.antennapod.R.id.txtvPubDate);
            break;
        }
    }
    switch(MUID_STATIC) {
        // FeedItemlistDescriptionAdapter_4_InvalidViewFocusOperatorMutator
        case 4033: {
            /**
            * Inserted by Kadabra
            */
            holder.description = convertView.findViewById(de.danoeh.antennapod.R.id.txtvDescription);
            holder.description.requestFocus();
            break;
        }
        // FeedItemlistDescriptionAdapter_5_ViewComponentNotVisibleOperatorMutator
        case 5033: {
            /**
            * Inserted by Kadabra
            */
            holder.description = convertView.findViewById(de.danoeh.antennapod.R.id.txtvDescription);
            holder.description.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        holder.description = convertView.findViewById(de.danoeh.antennapod.R.id.txtvDescription);
        break;
    }
}
switch(MUID_STATIC) {
    // FeedItemlistDescriptionAdapter_6_InvalidViewFocusOperatorMutator
    case 6033: {
        /**
        * Inserted by Kadabra
        */
        holder.preview = convertView.findViewById(de.danoeh.antennapod.R.id.butPreview);
        holder.preview.requestFocus();
        break;
    }
    // FeedItemlistDescriptionAdapter_7_ViewComponentNotVisibleOperatorMutator
    case 7033: {
        /**
        * Inserted by Kadabra
        */
        holder.preview = convertView.findViewById(de.danoeh.antennapod.R.id.butPreview);
        holder.preview.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    holder.preview = convertView.findViewById(de.danoeh.antennapod.R.id.butPreview);
    break;
}
}
convertView.setTag(holder);
} else {
holder = ((de.danoeh.antennapod.adapter.FeedItemlistDescriptionAdapter.Holder) (convertView.getTag()));
}
holder.title.setText(item.getTitle());
holder.pubDate.setText(de.danoeh.antennapod.core.util.DateFormatter.formatAbbrev(getContext(), item.getPubDate()));
if (item.getDescription() != null) {
java.lang.String description;
description = de.danoeh.antennapod.core.util.syndication.HtmlToPlainText.getPlainText(item.getDescription()).replaceAll("\n", " ").replaceAll("\\s+", " ").trim();
holder.description.setText(description);
holder.description.setMaxLines(de.danoeh.antennapod.adapter.FeedItemlistDescriptionAdapter.MAX_LINES_COLLAPSED);
}
holder.description.setTag(java.lang.Boolean.FALSE)// not expanded
;// not expanded

holder.preview.setVisibility(android.view.View.GONE);
switch(MUID_STATIC) {
// FeedItemlistDescriptionAdapter_8_BuggyGUIListenerOperatorMutator
case 8033: {
holder.preview.setOnClickListener(null);
break;
}
default: {
holder.preview.setOnClickListener((android.view.View v) -> {
if (item.getMedia() == null) {
    return;
}
de.danoeh.antennapod.model.playback.Playable playable;
playable = new de.danoeh.antennapod.model.playback.RemoteMedia(item);
if (!de.danoeh.antennapod.core.util.NetworkUtils.isStreamingAllowed()) {
    new de.danoeh.antennapod.dialog.StreamingConfirmationDialog(getContext(), playable).show();
    return;
}
new de.danoeh.antennapod.core.util.playback.PlaybackServiceStarter(getContext(), playable).callEvenIfRunning(true).start();
if (playable.getMediaType() == de.danoeh.antennapod.model.playback.MediaType.VIDEO) {
    getContext().startActivity(de.danoeh.antennapod.core.service.playback.PlaybackService.getPlayerActivityIntent(getContext(), playable));
}
});
break;
}
}
switch(MUID_STATIC) {
// FeedItemlistDescriptionAdapter_9_BuggyGUIListenerOperatorMutator
case 9033: {
convertView.setOnClickListener(null);
break;
}
default: {
convertView.setOnClickListener((android.view.View v) -> {
if (holder.description.getTag() == java.lang.Boolean.TRUE) {
holder.description.setMaxLines(de.danoeh.antennapod.adapter.FeedItemlistDescriptionAdapter.MAX_LINES_COLLAPSED);
holder.preview.setVisibility(android.view.View.GONE);
holder.description.setTag(java.lang.Boolean.FALSE);
} else {
holder.description.setMaxLines(30);
holder.description.setTag(java.lang.Boolean.TRUE);
holder.preview.setVisibility(item.getMedia() != null ? android.view.View.VISIBLE : android.view.View.GONE);
holder.preview.setText(de.danoeh.antennapod.R.string.preview_episode);
}
});
break;
}
}
return convertView;
}


static class Holder {
android.widget.TextView title;

android.widget.TextView pubDate;

android.widget.TextView description;

android.widget.Button preview;
}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
