package com.automattic.simplenote.widgets;
import androidx.annotation.NonNull;
import android.text.style.ClickableSpan;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CheckableSpan extends android.text.style.ClickableSpan {
    static final int MUID_STATIC = getMUID();
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }


    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    @java.lang.Override
    public void onClick(@androidx.annotation.NonNull
    android.view.View view) {
        setChecked(!isChecked);
        if (view instanceof com.automattic.simplenote.widgets.SimplenoteEditText) {
            try {
                ((com.automattic.simplenote.widgets.SimplenoteEditText) (view)).toggleCheckbox(this);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
