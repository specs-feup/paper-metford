package com.automattic.simplenote.utils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TextHighlighter implements com.automattic.simplenote.utils.MatchOffsetHighlighter.SpanFactory , com.automattic.simplenote.utils.SearchSnippetFormatter.SpanFactory {
    static final int MUID_STATIC = getMUID();
    int mForegroundColor;

    int mBackgroundColor;

    @android.annotation.SuppressLint("ResourceType")
    public TextHighlighter(android.content.Context context, int foregroundResId, int backgroundResId) {
        android.content.res.TypedArray colors;
        colors = context.obtainStyledAttributes(new int[]{ foregroundResId, backgroundResId });
        mForegroundColor = colors.getColor(0, 0xffff0000);
        mBackgroundColor = colors.getColor(1, 0xff00ffff);
        colors.recycle();
    }


    @java.lang.Override
    public java.lang.Object[] buildSpans() {
        return buildSpans(null);
    }


    @java.lang.Override
    public java.lang.Object[] buildSpans(java.lang.String content) {
        return new java.lang.Object[]{ new android.text.style.ForegroundColorSpan(mForegroundColor), new android.text.style.BackgroundColorSpan(mBackgroundColor) };
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
