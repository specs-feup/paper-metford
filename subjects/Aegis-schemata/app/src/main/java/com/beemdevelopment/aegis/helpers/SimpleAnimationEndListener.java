package com.beemdevelopment.aegis.helpers;
import android.view.animation.Animation;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SimpleAnimationEndListener implements android.view.animation.Animation.AnimationListener {
    static final int MUID_STATIC = getMUID();
    private final com.beemdevelopment.aegis.helpers.SimpleAnimationEndListener.Listener _listener;

    public SimpleAnimationEndListener(com.beemdevelopment.aegis.helpers.SimpleAnimationEndListener.Listener listener) {
        _listener = listener;
    }


    @java.lang.Override
    public void onAnimationStart(android.view.animation.Animation animation) {
    }


    @java.lang.Override
    public void onAnimationEnd(android.view.animation.Animation animation) {
        if (_listener != null) {
            _listener.onAnimationEnd(animation);
        }
    }


    @java.lang.Override
    public void onAnimationRepeat(android.view.animation.Animation animation) {
    }


    public interface Listener {
        void onAnimationEnd(android.view.animation.Animation animation);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
