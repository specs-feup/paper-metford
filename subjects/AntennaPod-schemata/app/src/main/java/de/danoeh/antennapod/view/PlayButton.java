package de.danoeh.antennapod.view;
import de.danoeh.antennapod.R;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PlayButton extends androidx.appcompat.widget.AppCompatImageButton {
    static final int MUID_STATIC = getMUID();
    private boolean isShowPlay = true;

    private boolean isVideoScreen = false;

    public PlayButton(@androidx.annotation.NonNull
    android.content.Context context) {
        super(context);
    }


    public PlayButton(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public PlayButton(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setIsVideoScreen(boolean isVideoScreen) {
        this.isVideoScreen = isVideoScreen;
    }


    public void setIsShowPlay(boolean showPlay) {
        if (this.isShowPlay != showPlay) {
            this.isShowPlay = showPlay;
            setContentDescription(getContext().getString(showPlay ? de.danoeh.antennapod.R.string.play_label : de.danoeh.antennapod.R.string.pause_label));
            if (isVideoScreen) {
                setImageResource(showPlay ? de.danoeh.antennapod.R.drawable.ic_play_video_white : de.danoeh.antennapod.R.drawable.ic_pause_video_white);
            } else if (!isShown()) {
                setImageResource(showPlay ? de.danoeh.antennapod.R.drawable.ic_play_48dp : de.danoeh.antennapod.R.drawable.ic_pause);
            } else if (showPlay) {
                androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat drawable;
                drawable = androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat.create(getContext(), de.danoeh.antennapod.R.drawable.ic_animate_pause_play);
                setImageDrawable(drawable);
                drawable.start();
            } else {
                androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat drawable;
                drawable = androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat.create(getContext(), de.danoeh.antennapod.R.drawable.ic_animate_play_pause);
                setImageDrawable(drawable);
                drawable.start();
            }
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
