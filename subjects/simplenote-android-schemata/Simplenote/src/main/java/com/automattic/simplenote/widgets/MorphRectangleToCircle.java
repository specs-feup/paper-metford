package com.automattic.simplenote.widgets;
import android.graphics.Color;
import androidx.annotation.ColorInt;
import android.transition.ChangeBounds;
import android.view.animation.AnimationUtils;
import android.animation.ObjectAnimator;
import android.view.ViewGroup;
import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.AnimatorSet;
import android.transition.TransitionValues;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * A transition that morphs a rectangle into a circle and cross-fades the background color.
 */
public class MorphRectangleToCircle extends android.transition.ChangeBounds {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PROPERTY_COLOR = "color";

    private static final java.lang.String PROPERTY_RADIUS = "radius";

    private static final java.lang.String[] TRANSITION_PROPERTIES = new java.lang.String[]{ com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_COLOR, com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_RADIUS };

    private static final int DURATION = 300;

    @androidx.annotation.ColorInt
    private int mColorEnd = android.graphics.Color.TRANSPARENT;

    @androidx.annotation.ColorInt
    private int mColorStart = android.graphics.Color.TRANSPARENT;

    private int mRadiusEnd = -1;

    public MorphRectangleToCircle(@androidx.annotation.ColorInt
    int colorEnd, @androidx.annotation.ColorInt
    int colorStart, int radiusEnd) {
        super();
        setColorEnd(colorEnd);
        setColorStart(colorStart);
        setRadiusEnd(radiusEnd);
    }


    @java.lang.Override
    public void captureEndValues(android.transition.TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        final android.view.View view;
        view = transitionValues.view;
        if ((view.getWidth() <= 0) || (view.getHeight() <= 0)) {
            return;
        }
        transitionValues.values.put(com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_COLOR, mColorEnd);
        switch(MUID_STATIC) {
            // MorphRectangleToCircle_0_BinaryMutator
            case 7: {
                transitionValues.values.put(com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_RADIUS, mRadiusEnd >= 0 ? mRadiusEnd : view.getHeight() * 2);
                break;
            }
            default: {
            transitionValues.values.put(com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_RADIUS, mRadiusEnd >= 0 ? mRadiusEnd : view.getHeight() / 2);
            break;
        }
    }
}


@java.lang.Override
public void captureStartValues(android.transition.TransitionValues transitionValues) {
    super.captureStartValues(transitionValues);
    final android.view.View view;
    view = transitionValues.view;
    if ((view.getWidth() <= 0) || (view.getHeight() <= 0)) {
        return;
    }
    transitionValues.values.put(com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_COLOR, mColorStart);
    transitionValues.values.put(com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_RADIUS, mRadiusEnd);
}


@java.lang.Override
public android.animation.Animator createAnimator(final android.view.ViewGroup root, android.transition.TransitionValues valuesStart, android.transition.TransitionValues valuesEnd) {
    android.animation.Animator bounds;
    bounds = super.createAnimator(root, valuesStart, valuesEnd);
    if (((valuesStart == null) || (valuesEnd == null)) || (bounds == null)) {
        return null;
    }
    java.lang.Integer colorStart;
    colorStart = ((java.lang.Integer) (valuesStart.values.get(com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_COLOR)));
    java.lang.Integer radiusStart;
    radiusStart = ((java.lang.Integer) (valuesStart.values.get(com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_RADIUS)));
    java.lang.Integer colorEnd;
    colorEnd = ((java.lang.Integer) (valuesEnd.values.get(com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_COLOR)));
    java.lang.Integer radiusEnd;
    radiusEnd = ((java.lang.Integer) (valuesEnd.values.get(com.automattic.simplenote.widgets.MorphRectangleToCircle.PROPERTY_RADIUS)));
    if ((((colorStart == null) || (radiusStart == null)) || (colorEnd == null)) || (radiusEnd == null)) {
        return null;
    }
    com.automattic.simplenote.widgets.MorphDrawable background;
    background = new com.automattic.simplenote.widgets.MorphDrawable(colorStart, radiusStart);
    valuesEnd.view.setBackground(background);
    android.animation.Animator color;
    color = android.animation.ObjectAnimator.ofArgb(background, com.automattic.simplenote.widgets.MorphDrawable.COLOR, colorEnd);
    android.animation.Animator corners;
    corners = android.animation.ObjectAnimator.ofFloat(background, com.automattic.simplenote.widgets.MorphDrawable.RADIUS, radiusEnd);
    android.animation.TimeInterpolator interpolator;
    interpolator = android.view.animation.AnimationUtils.loadInterpolator(root.getContext(), android.R.interpolator.fast_out_slow_in);
    if (valuesEnd.view instanceof android.view.ViewGroup) {
        android.view.ViewGroup viewGroup;
        viewGroup = ((android.view.ViewGroup) (valuesEnd.view));
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            android.view.View child;
            child = viewGroup.getChildAt(i);
            switch(MUID_STATIC) {
                // MorphRectangleToCircle_1_BinaryMutator
                case 107: {
                    child.animate().alpha(0.0F).translationY(child.getHeight() * 3.0F).setStartDelay(0L).setDuration(50L).setInterpolator(interpolator).start();
                    break;
                }
                default: {
                child.animate().alpha(0.0F).translationY(child.getHeight() / 3.0F).setStartDelay(0L).setDuration(50L).setInterpolator(interpolator).start();
                break;
            }
        }
    }
}
android.animation.AnimatorSet transition;
transition = new android.animation.AnimatorSet();
transition.playTogether(bounds, corners, color);
transition.setDuration(com.automattic.simplenote.widgets.MorphRectangleToCircle.DURATION);
transition.setInterpolator(interpolator);
return transition;
}


@java.lang.Override
public java.lang.String[] getTransitionProperties() {
return com.automattic.simplenote.widgets.MorphRectangleToCircle.TRANSITION_PROPERTIES;
}


public void setColorEnd(@androidx.annotation.ColorInt
int colorEnd) {
mColorEnd = colorEnd;
}


public void setColorStart(@androidx.annotation.ColorInt
int colorStart) {
mColorStart = colorStart;
}


public void setRadiusEnd(int radiusEnd) {
mRadiusEnd = radiusEnd;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
