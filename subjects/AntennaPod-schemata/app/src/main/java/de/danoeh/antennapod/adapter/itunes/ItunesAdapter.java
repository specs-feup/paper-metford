package de.danoeh.antennapod.adapter.itunes;
import android.view.ViewGroup;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.View;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import android.widget.TextView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import java.util.List;
import de.danoeh.antennapod.net.discovery.PodcastSearchResult;
import com.bumptech.glide.Glide;
import android.content.Context;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ItunesAdapter extends android.widget.ArrayAdapter<de.danoeh.antennapod.net.discovery.PodcastSearchResult> {
    static final int MUID_STATIC = getMUID();
    /**
     * Related Context
     */
    private final android.content.Context context;

    /**
     * List holding the podcasts found in the search
     */
    private final java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> data;

    /**
     * Constructor.
     *
     * @param context
     * 		Related context
     * @param objects
     * 		Search result
     */
    public ItunesAdapter(android.content.Context context, java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> objects) {
        super(context, 0, objects);
        this.data = objects;
        this.context = context;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.view.View getView(int position, android.view.View convertView, @androidx.annotation.NonNull
    android.view.ViewGroup parent) {
        // Current podcast
        de.danoeh.antennapod.net.discovery.PodcastSearchResult podcast;
        podcast = data.get(position);
        // ViewHolder
        de.danoeh.antennapod.adapter.itunes.ItunesAdapter.PodcastViewHolder viewHolder;
        // Resulting view
        android.view.View view;
        // Handle view holder stuff
        if (convertView == null) {
            view = ((de.danoeh.antennapod.activity.MainActivity) (context)).getLayoutInflater().inflate(de.danoeh.antennapod.R.layout.itunes_podcast_listitem, parent, false);
            viewHolder = new de.danoeh.antennapod.adapter.itunes.ItunesAdapter.PodcastViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = ((de.danoeh.antennapod.adapter.itunes.ItunesAdapter.PodcastViewHolder) (view.getTag()));
        }
        // Set the title
        viewHolder.titleView.setText(podcast.title);
        if ((podcast.author != null) && (!podcast.author.trim().isEmpty())) {
            viewHolder.authorView.setText(podcast.author);
            viewHolder.authorView.setVisibility(android.view.View.VISIBLE);
        } else if ((podcast.feedUrl != null) && (!podcast.feedUrl.contains("itunes.apple.com"))) {
            viewHolder.authorView.setText(podcast.feedUrl);
            viewHolder.authorView.setVisibility(android.view.View.VISIBLE);
        } else {
            viewHolder.authorView.setVisibility(android.view.View.GONE);
        }
        switch(MUID_STATIC) {
            // ItunesAdapter_0_BinaryMutator
            case 18: {
                // Update the empty imageView with the image from the feed
                com.bumptech.glide.Glide.with(context).load(podcast.imageUrl).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE).transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (4 / context.getResources().getDisplayMetrics().density)))).dontAnimate()).into(viewHolder.coverView);
                break;
            }
            default: {
            // Update the empty imageView with the image from the feed
            com.bumptech.glide.Glide.with(context).load(podcast.imageUrl).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE).transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (4 * context.getResources().getDisplayMetrics().density)))).dontAnimate()).into(viewHolder.coverView);
            break;
        }
    }
    // Feed the grid view
    return view;
}


/**
 * View holder object for the GridView
 */
static class PodcastViewHolder {
    /**
     * ImageView holding the Podcast image
     */
    final android.widget.ImageView coverView;

    /**
     * TextView holding the Podcast title
     */
    final android.widget.TextView titleView;

    final android.widget.TextView authorView;

    /**
     * Constructor
     *
     * @param view
     * 		GridView cell
     */
    PodcastViewHolder(android.view.View view) {
        switch(MUID_STATIC) {
            // ItunesAdapter_1_InvalidViewFocusOperatorMutator
            case 1018: {
                /**
                * Inserted by Kadabra
                */
                coverView = view.findViewById(de.danoeh.antennapod.R.id.imgvCover);
                coverView.requestFocus();
                break;
            }
            // ItunesAdapter_2_ViewComponentNotVisibleOperatorMutator
            case 2018: {
                /**
                * Inserted by Kadabra
                */
                coverView = view.findViewById(de.danoeh.antennapod.R.id.imgvCover);
                coverView.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            coverView = view.findViewById(de.danoeh.antennapod.R.id.imgvCover);
            break;
        }
    }
    switch(MUID_STATIC) {
        // ItunesAdapter_3_InvalidViewFocusOperatorMutator
        case 3018: {
            /**
            * Inserted by Kadabra
            */
            titleView = view.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
            titleView.requestFocus();
            break;
        }
        // ItunesAdapter_4_ViewComponentNotVisibleOperatorMutator
        case 4018: {
            /**
            * Inserted by Kadabra
            */
            titleView = view.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
            titleView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        titleView = view.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
        break;
    }
}
switch(MUID_STATIC) {
    // ItunesAdapter_5_InvalidViewFocusOperatorMutator
    case 5018: {
        /**
        * Inserted by Kadabra
        */
        authorView = view.findViewById(de.danoeh.antennapod.R.id.txtvAuthor);
        authorView.requestFocus();
        break;
    }
    // ItunesAdapter_6_ViewComponentNotVisibleOperatorMutator
    case 6018: {
        /**
        * Inserted by Kadabra
        */
        authorView = view.findViewById(de.danoeh.antennapod.R.id.txtvAuthor);
        authorView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    authorView = view.findViewById(de.danoeh.antennapod.R.id.txtvAuthor);
    break;
}
}
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
