package com.automattic.simplenote.utils;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.widget.TextView;
import android.animation.Animator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Animation.AnimationListener;
import android.util.Property;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@java.lang.SuppressWarnings("unused")
public class AniUtils {
    static final int MUID_STATIC = getMUID();
    private AniUtils() {
        throw new java.lang.AssertionError();
    }


    // fades in the passed view
    public static void fadeIn(final android.view.View target) {
        com.automattic.simplenote.utils.AniUtils.fadeIn(target, null);
    }


    public static void fadeIn(final android.view.View target, android.view.animation.Animation.AnimationListener listener) {
        if (target == null)
            return;

        android.view.animation.Animation animation;
        animation = android.view.animation.AnimationUtils.loadAnimation(target.getContext(), android.R.anim.fade_in);
        if (listener != null)
            animation.setAnimationListener(listener);

        target.startAnimation(animation);
        if (target.getVisibility() != android.view.View.VISIBLE)
            target.setVisibility(android.view.View.VISIBLE);

    }


    // fades out the passed view
    public static void fadeOut(final android.view.View target, int endVisibility) {
        com.automattic.simplenote.utils.AniUtils.fadeOut(target, endVisibility, null);
    }


    public static void fadeOut(final android.view.View target, int endVisibility, android.view.animation.Animation.AnimationListener listener) {
        if (target == null)
            return;

        android.view.animation.Animation animation;
        animation = android.view.animation.AnimationUtils.loadAnimation(target.getContext(), android.R.anim.fade_out);
        if (listener != null)
            animation.setAnimationListener(listener);

        target.startAnimation(animation);
        if (target.getVisibility() != endVisibility)
            target.setVisibility(endVisibility);

    }


    // fade out the passed text view, then replace its text and fade it back in
    public static void fadeTextOutIn(final android.widget.TextView textView, final java.lang.String newText) {
        if (textView == null)
            return;

        android.view.animation.Animation animationOut;
        animationOut = android.view.animation.AnimationUtils.loadAnimation(textView.getContext(), android.R.anim.fade_out);
        android.view.animation.Animation.AnimationListener outListener;
        outListener = new android.view.animation.Animation.AnimationListener() {
            @java.lang.Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                android.view.animation.Animation animationIn;
                animationIn = android.view.animation.AnimationUtils.loadAnimation(textView.getContext(), android.R.anim.fade_in);
                textView.setText(newText);
                textView.startAnimation(animationIn);
            }


            @java.lang.Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {
            }


            @java.lang.Override
            public void onAnimationStart(android.view.animation.Animation animation) {
            }

        };
        animationOut.setAnimationListener(outListener);
        textView.startAnimation(animationOut);
    }


    // Animates the view off-screen to the left
    public static void swipeOutToLeft(final android.view.View view) {
        if (view == null)
            return;

        view.animate().xBy(-view.getWidth()).alpha(0.0F).setInterpolator(new android.view.animation.AnticipateInterpolator()).setListener(new android.animation.Animator.AnimatorListener() {
            @java.lang.Override
            public void onAnimationStart(android.animation.Animator animation) {
            }


            @java.lang.Override
            public void onAnimationEnd(android.animation.Animator animation) {
                view.setVisibility(android.view.View.GONE);
            }


            @java.lang.Override
            public void onAnimationCancel(android.animation.Animator animation) {
            }


            @java.lang.Override
            public void onAnimationRepeat(android.animation.Animator animation) {
            }

        }).start();
    }


    /**
     * An implementation of {@link android.util.Property} to be used specifically with fields of
     * type <code>float</code>. This type-specific subclass enables performance benefit by allowing
     * calls to a {@link #set(Object, Float) set()} function that takes the primitive
     * <code>float</code> type and avoids autoboxing and other overhead associated with the
     * <code>Float</code> class.
     *
     * @param <T>
     * 		The class on which the Property is declared.
     */
    public abstract static class FloatProperty<T> extends android.util.Property<T, java.lang.Float> {
        public FloatProperty(java.lang.String name) {
            super(java.lang.Float.class, name);
        }


        /**
         * A type-specific override of the {@link #set(Object, Float)} that is faster when dealing
         * with fields of type <code>float</code>.
         */
        public abstract void setValue(T object, float value);


        @java.lang.Override
        public final void set(T object, java.lang.Float value) {
            setValue(object, value);
        }

    }

    /**
     * An implementation of {@link android.util.Property} to be used specifically with fields of
     * type <code>int</code>. This type-specific subclass enables performance benefit by allowing
     * calls to a {@link #set(Object, Integer) set()} function that takes the primitive
     * <code>int</code> type and avoids autoboxing and other overhead associated with the
     * <code>Integer</code> class.
     *
     * @param <T>
     * 		The class on which the Property is declared.
     */
    public abstract static class IntProperty<T> extends android.util.Property<T, java.lang.Integer> {
        public IntProperty(java.lang.String name) {
            super(java.lang.Integer.class, name);
        }


        /**
         * A type-specific override of the {@link #set(Object, Integer)} that is faster when dealing
         * with fields of type <code>int</code>.
         */
        public abstract void setValue(T object, int value);


        @java.lang.Override
        public final void set(T object, java.lang.Integer value) {
            setValue(object, value);
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
