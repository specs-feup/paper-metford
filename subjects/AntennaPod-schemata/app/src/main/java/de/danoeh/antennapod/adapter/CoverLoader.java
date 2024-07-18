package de.danoeh.antennapod.adapter;
import com.bumptech.glide.RequestBuilder;
import android.graphics.drawable.Drawable;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.View;
import java.lang.ref.WeakReference;
import com.bumptech.glide.request.target.CustomViewTarget;
import androidx.annotation.NonNull;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import android.widget.TextView;
import com.bumptech.glide.request.transition.Transition;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CoverLoader {
    static final int MUID_STATIC = getMUID();
    private int resource = 0;

    private java.lang.String uri;

    private java.lang.String fallbackUri;

    private android.widget.ImageView imgvCover;

    private boolean textAndImageCombined;

    private de.danoeh.antennapod.activity.MainActivity activity;

    private android.widget.TextView fallbackTitle;

    public CoverLoader(de.danoeh.antennapod.activity.MainActivity activity) {
        this.activity = activity;
    }


    public de.danoeh.antennapod.adapter.CoverLoader withUri(java.lang.String uri) {
        this.uri = uri;
        return this;
    }


    public de.danoeh.antennapod.adapter.CoverLoader withResource(int resource) {
        this.resource = resource;
        return this;
    }


    public de.danoeh.antennapod.adapter.CoverLoader withFallbackUri(java.lang.String uri) {
        fallbackUri = uri;
        return this;
    }


    public de.danoeh.antennapod.adapter.CoverLoader withCoverView(android.widget.ImageView coverView) {
        imgvCover = coverView;
        return this;
    }


    public de.danoeh.antennapod.adapter.CoverLoader withPlaceholderView(android.widget.TextView title) {
        this.fallbackTitle = title;
        return this;
    }


    /**
     * Set cover text and if it should be shown even if there is a cover image.
     *
     * @param fallbackTitle
     * 		Fallback title text
     * @param textAndImageCombined
     * 		Show cover text even if there is a cover image?
     */
    @androidx.annotation.NonNull
    public de.danoeh.antennapod.adapter.CoverLoader withPlaceholderView(android.widget.TextView fallbackTitle, boolean textAndImageCombined) {
        this.fallbackTitle = fallbackTitle;
        this.textAndImageCombined = textAndImageCombined;
        return this;
    }


    public void load() {
        de.danoeh.antennapod.adapter.CoverLoader.CoverTarget coverTarget;
        coverTarget = new de.danoeh.antennapod.adapter.CoverLoader.CoverTarget(fallbackTitle, imgvCover, textAndImageCombined);
        if (resource != 0) {
            com.bumptech.glide.Glide.with(imgvCover).clear(coverTarget);
            imgvCover.setImageResource(resource);
            de.danoeh.antennapod.adapter.CoverLoader.CoverTarget.setTitleVisibility(fallbackTitle, textAndImageCombined);
            return;
        }
        com.bumptech.glide.request.RequestOptions options;
        options = new com.bumptech.glide.request.RequestOptions().fitCenter().dontAnimate();
        com.bumptech.glide.RequestBuilder<android.graphics.drawable.Drawable> builder;
        builder = com.bumptech.glide.Glide.with(imgvCover).as(android.graphics.drawable.Drawable.class).load(uri).apply(options);
        if (fallbackUri != null) {
            builder = builder.error(com.bumptech.glide.Glide.with(imgvCover).as(android.graphics.drawable.Drawable.class).load(fallbackUri).apply(options));
        }
        builder.into(coverTarget);
    }


    static class CoverTarget extends com.bumptech.glide.request.target.CustomViewTarget<android.widget.ImageView, android.graphics.drawable.Drawable> {
        private final java.lang.ref.WeakReference<android.widget.TextView> fallbackTitle;

        private final java.lang.ref.WeakReference<android.widget.ImageView> cover;

        private final boolean textAndImageCombined;

        public CoverTarget(android.widget.TextView fallbackTitle, android.widget.ImageView coverImage, boolean textAndImageCombined) {
            super(coverImage);
            this.fallbackTitle = new java.lang.ref.WeakReference<>(fallbackTitle);
            this.cover = new java.lang.ref.WeakReference<>(coverImage);
            this.textAndImageCombined = textAndImageCombined;
        }


        @java.lang.Override
        public void onLoadFailed(android.graphics.drawable.Drawable errorDrawable) {
            de.danoeh.antennapod.adapter.CoverLoader.CoverTarget.setTitleVisibility(fallbackTitle.get(), true);
        }


        @java.lang.Override
        public void onResourceReady(@androidx.annotation.NonNull
        android.graphics.drawable.Drawable resource, @androidx.annotation.Nullable
        com.bumptech.glide.request.transition.Transition<? super android.graphics.drawable.Drawable> transition) {
            android.widget.ImageView ivCover;
            ivCover = cover.get();
            ivCover.setImageDrawable(resource);
            de.danoeh.antennapod.adapter.CoverLoader.CoverTarget.setTitleVisibility(fallbackTitle.get(), textAndImageCombined);
        }


        @java.lang.Override
        protected void onResourceCleared(@androidx.annotation.Nullable
        android.graphics.drawable.Drawable placeholder) {
            android.widget.ImageView ivCover;
            ivCover = cover.get();
            ivCover.setImageDrawable(placeholder);
            de.danoeh.antennapod.adapter.CoverLoader.CoverTarget.setTitleVisibility(fallbackTitle.get(), textAndImageCombined);
        }


        static void setTitleVisibility(android.widget.TextView fallbackTitle, boolean textAndImageCombined) {
            if (fallbackTitle != null) {
                fallbackTitle.setVisibility(textAndImageCombined ? android.view.View.VISIBLE : android.view.View.GONE);
            }
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
