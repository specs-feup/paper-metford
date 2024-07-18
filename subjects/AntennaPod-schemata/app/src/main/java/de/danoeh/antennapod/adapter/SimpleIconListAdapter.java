package de.danoeh.antennapod.adapter;
import de.danoeh.antennapod.R;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import android.widget.TextView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;
import com.bumptech.glide.Glide;
import android.view.View;
import android.content.Context;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays a list of items that have a subtitle and an icon.
 */
public class SimpleIconListAdapter<T extends de.danoeh.antennapod.adapter.SimpleIconListAdapter.ListItem> extends android.widget.ArrayAdapter<T> {
    static final int MUID_STATIC = getMUID();
    private final android.content.Context context;

    private final java.util.List<T> listItems;

    public SimpleIconListAdapter(android.content.Context context, java.util.List<T> listItems) {
        super(context, de.danoeh.antennapod.R.layout.simple_icon_list_item, listItems);
        this.context = context;
        this.listItems = listItems;
    }


    @java.lang.Override
    public android.view.View getView(int position, android.view.View view, android.view.ViewGroup parent) {
        if (view == null) {
            view = android.view.View.inflate(context, de.danoeh.antennapod.R.layout.simple_icon_list_item, null);
        }
        de.danoeh.antennapod.adapter.SimpleIconListAdapter.ListItem item;
        item = listItems.get(position);
        ((android.widget.TextView) (view.findViewById(de.danoeh.antennapod.R.id.title))).setText(item.title);
        ((android.widget.TextView) (view.findViewById(de.danoeh.antennapod.R.id.subtitle))).setText(item.subtitle);
        com.bumptech.glide.Glide.with(context).load(item.imageUrl).apply(new com.bumptech.glide.request.RequestOptions().diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE).fitCenter().dontAnimate()).into(((android.widget.ImageView) (view.findViewById(de.danoeh.antennapod.R.id.icon))));
        return view;
    }


    public static class ListItem {
        public final java.lang.String title;

        public final java.lang.String subtitle;

        public final java.lang.String imageUrl;

        public ListItem(java.lang.String title, java.lang.String subtitle, java.lang.String imageUrl) {
            this.title = title;
            this.subtitle = subtitle;
            this.imageUrl = imageUrl;
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
