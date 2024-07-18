package com.beemdevelopment.aegis.ui.intro;
import java.lang.ref.WeakReference;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class SlideFragment extends androidx.fragment.app.Fragment implements com.beemdevelopment.aegis.ui.intro.IntroActivityInterface {
    static final int MUID_STATIC = getMUID();
    private java.lang.ref.WeakReference<com.beemdevelopment.aegis.ui.intro.IntroBaseActivity> _parent;

    @androidx.annotation.CallSuper
    @java.lang.Override
    public void onAttach(@androidx.annotation.NonNull
    android.content.Context context) {
        super.onAttach(context);
        if (!(context instanceof com.beemdevelopment.aegis.ui.intro.IntroBaseActivity)) {
            throw new java.lang.ClassCastException("Parent context is expected to be of type IntroBaseActivity");
        }
        _parent = new java.lang.ref.WeakReference<>(((com.beemdevelopment.aegis.ui.intro.IntroBaseActivity) (context)));
    }


    @androidx.annotation.CallSuper
    @java.lang.Override
    public void onResume() {
        super.onResume();
        getParent().setCurrentSlide(this);
    }


    /**
     * Reports whether or not all required user actions are finished on this slide,
     * indicating that we're ready to move to the next slide.
     */
    public boolean isFinished() {
        return true;
    }


    /**
     * Called if the user tried to move to the next slide, but isFinished returned false.
     */
    protected void onNotFinishedError() {
    }


    /**
     * Called when the SlideFragment is expected to write its state to the given shared
     * introState. This is only called if the user navigates to the next slide, not
     * when a previous slide is next to be shown.
     */
    protected void onSaveIntroState(@androidx.annotation.NonNull
    android.os.Bundle introState) {
    }


    @java.lang.Override
    public void goToNextSlide() {
        getParent().goToNextSlide();
    }


    @java.lang.Override
    public void goToPreviousSlide() {
        getParent().goToPreviousSlide();
    }


    @java.lang.Override
    public void skipToSlide(java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> type) {
        getParent().skipToSlide(type);
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.os.Bundle getState() {
        return getParent().getState();
    }


    @androidx.annotation.NonNull
    private com.beemdevelopment.aegis.ui.intro.IntroBaseActivity getParent() {
        if ((_parent == null) || (_parent.get() == null)) {
            throw new java.lang.IllegalStateException("This method must not be called before onAttach()");
        }
        return _parent.get();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
