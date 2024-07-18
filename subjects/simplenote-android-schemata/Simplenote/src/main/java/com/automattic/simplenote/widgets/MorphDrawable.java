package com.automattic.simplenote.widgets;
import androidx.annotation.ColorInt;
import android.graphics.ColorFilter;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import android.graphics.Outline;
import com.automattic.simplenote.utils.AniUtils;
import android.util.Property;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * A drawable that can morph size, shape (via it's corner radius) and color.  Specifically this is
 * useful for animating between a floating action button and another activity.
 */
public class MorphDrawable extends android.graphics.drawable.Drawable {
    static final int MUID_STATIC = getMUID();
    private android.graphics.Paint mPaint;

    private float mRadius;

    public static final android.util.Property<com.automattic.simplenote.widgets.MorphDrawable, java.lang.Integer> COLOR = new com.automattic.simplenote.utils.AniUtils.IntProperty<com.automattic.simplenote.widgets.MorphDrawable>("color") {
        @java.lang.Override
        public java.lang.Integer get(com.automattic.simplenote.widgets.MorphDrawable morphDrawable) {
            return morphDrawable.getColor();
        }


        @java.lang.Override
        public void setValue(com.automattic.simplenote.widgets.MorphDrawable morphDrawable, int value) {
            morphDrawable.setColor(value);
        }

    };

    public static final android.util.Property<com.automattic.simplenote.widgets.MorphDrawable, java.lang.Float> RADIUS = new com.automattic.simplenote.utils.AniUtils.FloatProperty<com.automattic.simplenote.widgets.MorphDrawable>("radius") {
        @java.lang.Override
        public java.lang.Float get(com.automattic.simplenote.widgets.MorphDrawable morphDrawable) {
            return morphDrawable.getRadius();
        }


        @java.lang.Override
        public void setValue(com.automattic.simplenote.widgets.MorphDrawable morphDrawable, float value) {
            morphDrawable.setRadius(value);
        }

    };

    public MorphDrawable(@androidx.annotation.ColorInt
    int color, float radius) {
        mRadius = radius;
        mPaint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
    }


    @java.lang.Override
    public void draw(android.graphics.Canvas canvas) {
        canvas.drawRoundRect(getBounds().left, getBounds().top, getBounds().right, getBounds().bottom, mRadius, mRadius, mPaint);
    }


    @java.lang.Override
    public int getOpacity() {
        return mPaint.getAlpha();
    }


    @java.lang.Override
    public void getOutline(android.graphics.Outline outline) {
        outline.setRoundRect(getBounds(), mRadius);
    }


    @java.lang.Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }


    @java.lang.Override
    public void setColorFilter(android.graphics.ColorFilter cf) {
        mPaint.setColorFilter(cf);
        invalidateSelf();
    }


    public int getColor() {
        return mPaint.getColor();
    }


    public float getRadius() {
        return mRadius;
    }


    public void setColor(int color) {
        mPaint.setColor(color);
        invalidateSelf();
    }


    public void setRadius(float cornerRadius) {
        mRadius = cornerRadius;
        invalidateSelf();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
