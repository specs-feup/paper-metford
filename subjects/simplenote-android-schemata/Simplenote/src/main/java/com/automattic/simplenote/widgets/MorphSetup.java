package com.automattic.simplenote.widgets;
import android.graphics.Color;
import android.view.animation.Interpolator;
import android.app.Activity;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Helper class for setting up circle to rectangle shared element transitions.
 */
public class MorphSetup {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String EXTRA_SHARED_ELEMENT_COLOR_END = "EXTRA_SHARED_ELEMENT_COLOR_END";

    public static final java.lang.String EXTRA_SHARED_ELEMENT_COLOR_START = "EXTRA_SHARED_ELEMENT_COLOR_START";

    public static final java.lang.String EXTRA_SHARED_ELEMENT_RADIUS = "EXTRA_SHARED_ELEMENT_RADIUS";

    private MorphSetup() {
    }


    /**
     * Configure the shared element transitions for morphing from a circle to rectangle.  This needs
     * to be in code as we need to supply the color to transition from/to and corner radius which is
     * dynamically supplied depending upon where this screen is launched from.
     */
    public static void setSharedElementTransitions(@androidx.annotation.NonNull
    android.app.Activity activity, @androidx.annotation.Nullable
    android.view.View target, int radius) {
        if ((!activity.getIntent().hasExtra(com.automattic.simplenote.widgets.MorphSetup.EXTRA_SHARED_ELEMENT_COLOR_END)) || (!activity.getIntent().hasExtra(com.automattic.simplenote.widgets.MorphSetup.EXTRA_SHARED_ELEMENT_COLOR_START))) {
            return;
        }
        int radiusStart;
        radiusStart = activity.getIntent().getIntExtra(com.automattic.simplenote.widgets.MorphSetup.EXTRA_SHARED_ELEMENT_RADIUS, -1);
        int colorEnd;
        colorEnd = activity.getIntent().getIntExtra(com.automattic.simplenote.widgets.MorphSetup.EXTRA_SHARED_ELEMENT_COLOR_END, android.graphics.Color.TRANSPARENT);
        int colorStart;
        colorStart = activity.getIntent().getIntExtra(com.automattic.simplenote.widgets.MorphSetup.EXTRA_SHARED_ELEMENT_COLOR_START, android.graphics.Color.TRANSPARENT);
        android.view.animation.Interpolator interpolator;
        interpolator = android.view.animation.AnimationUtils.loadInterpolator(activity, android.R.interpolator.fast_out_slow_in);
        com.automattic.simplenote.widgets.MorphCircleToRectangle morphCircleToRectangle;
        morphCircleToRectangle = new com.automattic.simplenote.widgets.MorphCircleToRectangle(colorEnd, colorStart, radius, radiusStart);
        morphCircleToRectangle.setInterpolator(interpolator);
        com.automattic.simplenote.widgets.MorphRectangleToCircle morphRectangleToCircle;
        morphRectangleToCircle = new com.automattic.simplenote.widgets.MorphRectangleToCircle(colorStart, colorEnd, radiusStart);
        morphRectangleToCircle.setInterpolator(interpolator);
        if (target != null) {
            morphCircleToRectangle.addTarget(target);
        }
        activity.getWindow().setSharedElementEnterTransition(morphCircleToRectangle);
        activity.getWindow().setSharedElementReturnTransition(morphRectangleToCircle);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
