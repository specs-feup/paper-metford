package de.danoeh.antennapod.view;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoRelayoutTextView extends androidx.appcompat.widget.AppCompatTextView {
    static final int MUID_STATIC = getMUID();
    private boolean requestLayoutEnabled = false;

    private float maxTextLength = 0;

    public NoRelayoutTextView(@androidx.annotation.NonNull
    android.content.Context context) {
        super(context);
    }


    public NoRelayoutTextView(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public NoRelayoutTextView(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @java.lang.Override
    public void requestLayout() {
        if (requestLayoutEnabled) {
            super.requestLayout();
        }
        requestLayoutEnabled = false;
    }


    @java.lang.Override
    public void setText(java.lang.CharSequence text, android.widget.TextView.BufferType type) {
        float textLength;
        textLength = getPaint().measureText(text.toString());
        if (textLength > maxTextLength) {
            maxTextLength = textLength;
            requestLayoutEnabled = true;
        }
        super.setText(text, type);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
