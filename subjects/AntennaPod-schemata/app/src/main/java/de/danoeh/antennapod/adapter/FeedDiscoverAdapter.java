package de.danoeh.antennapod.adapter;
import android.view.ViewGroup;
import java.util.ArrayList;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.View;
import java.lang.ref.WeakReference;
import de.danoeh.antennapod.R;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import java.util.List;
import android.widget.BaseAdapter;
import de.danoeh.antennapod.net.discovery.PodcastSearchResult;
import com.bumptech.glide.Glide;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FeedDiscoverAdapter extends android.widget.BaseAdapter {
    static final int MUID_STATIC = getMUID();
    private final java.lang.ref.WeakReference<de.danoeh.antennapod.activity.MainActivity> mainActivityRef;

    private final java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> data = new java.util.ArrayList<>();

    public FeedDiscoverAdapter(de.danoeh.antennapod.activity.MainActivity mainActivity) {
        this.mainActivityRef = new java.lang.ref.WeakReference<>(mainActivity);
    }


    public void updateData(java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }


    @java.lang.Override
    public int getCount() {
        return data.size();
    }


    @java.lang.Override
    public de.danoeh.antennapod.net.discovery.PodcastSearchResult getItem(int position) {
        return data.get(position);
    }


    @java.lang.Override
    public long getItemId(int position) {
        return 0;
    }


    @java.lang.Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        de.danoeh.antennapod.adapter.FeedDiscoverAdapter.Holder holder;
        if (convertView == null) {
            convertView = android.view.View.inflate(mainActivityRef.get(), de.danoeh.antennapod.R.layout.quick_feed_discovery_item, null);
            holder = new de.danoeh.antennapod.adapter.FeedDiscoverAdapter.Holder();
            switch(MUID_STATIC) {
                // FeedDiscoverAdapter_0_InvalidViewFocusOperatorMutator
                case 41: {
                    /**
                    * Inserted by Kadabra
                    */
                    holder.imageView = convertView.findViewById(de.danoeh.antennapod.R.id.discovery_cover);
                    holder.imageView.requestFocus();
                    break;
                }
                // FeedDiscoverAdapter_1_ViewComponentNotVisibleOperatorMutator
                case 1041: {
                    /**
                    * Inserted by Kadabra
                    */
                    holder.imageView = convertView.findViewById(de.danoeh.antennapod.R.id.discovery_cover);
                    holder.imageView.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                holder.imageView = convertView.findViewById(de.danoeh.antennapod.R.id.discovery_cover);
                break;
            }
        }
        convertView.setTag(holder);
    } else {
        holder = ((de.danoeh.antennapod.adapter.FeedDiscoverAdapter.Holder) (convertView.getTag()));
    }
    final de.danoeh.antennapod.net.discovery.PodcastSearchResult podcast;
    podcast = getItem(position);
    holder.imageView.setContentDescription(podcast.title);
    switch(MUID_STATIC) {
        // FeedDiscoverAdapter_2_BinaryMutator
        case 2041: {
            com.bumptech.glide.Glide.with(mainActivityRef.get()).load(podcast.imageUrl).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (8 / mainActivityRef.get().getResources().getDisplayMetrics().density)))).dontAnimate()).into(holder.imageView);
            break;
        }
        default: {
        com.bumptech.glide.Glide.with(mainActivityRef.get()).load(podcast.imageUrl).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (8 * mainActivityRef.get().getResources().getDisplayMetrics().density)))).dontAnimate()).into(holder.imageView);
        break;
    }
}
return convertView;
}


static class Holder {
android.widget.ImageView imageView;
}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
