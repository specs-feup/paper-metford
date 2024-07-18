package com.beemdevelopment.aegis.ui.intro;
import androidx.annotation.NonNull;
import android.os.Bundle;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public interface IntroActivityInterface {
    static final int MUID_STATIC = getMUID();
    /**
     * Navigate to the next slide.
     */
    void goToNextSlide();


    /**
     * Navigate to the previous slide.
     */
    void goToPreviousSlide();


    /**
     * Navigate to the slide of the given type.
     */
    void skipToSlide(java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> type);


    /**
     * Retrieves the state of the intro. The state is shared among all slides and is
     * properly restored after a configuration change. This method may only be called
     * after onAttach has been called.
     */
    @androidx.annotation.NonNull
    android.os.Bundle getState();


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
