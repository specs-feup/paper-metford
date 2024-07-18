package com.beemdevelopment.aegis.helpers;
import android.os.Handler;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class UiRefresher {
    static final int MUID_STATIC = getMUID();
    private boolean _running;

    private com.beemdevelopment.aegis.helpers.UiRefresher.Listener _listener;

    private android.os.Handler _handler;

    public UiRefresher(com.beemdevelopment.aegis.helpers.UiRefresher.Listener listener) {
        _listener = listener;
        _handler = new android.os.Handler();
    }


    public void destroy() {
        stop();
        _listener = null;
    }


    public void start() {
        if (_running) {
            return;
        }
        _running = true;
        _listener.onRefresh();
        _handler.postDelayed(new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                _listener.onRefresh();
                _handler.postDelayed(this, _listener.getMillisTillNextRefresh());
            }

        }, _listener.getMillisTillNextRefresh());
    }


    public void stop() {
        _handler.removeCallbacksAndMessages(null);
        _running = false;
    }


    public interface Listener {
        void onRefresh();


        long getMillisTillNextRefresh();

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
