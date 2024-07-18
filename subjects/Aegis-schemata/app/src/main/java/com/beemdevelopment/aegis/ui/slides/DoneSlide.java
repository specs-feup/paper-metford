package com.beemdevelopment.aegis.ui.slides;
import com.beemdevelopment.aegis.R;
import android.view.LayoutInflater;
import com.beemdevelopment.aegis.ui.intro.SlideFragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DoneSlide extends com.beemdevelopment.aegis.ui.intro.SlideFragment {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        return inflater.inflate(com.beemdevelopment.aegis.R.layout.fragment_done_slide, container, false);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
