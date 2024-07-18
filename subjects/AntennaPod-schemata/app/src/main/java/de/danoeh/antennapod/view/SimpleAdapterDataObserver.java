package de.danoeh.antennapod.view;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * AdapterDataObserver that relays all events to the method anythingChanged().
 */
public abstract class SimpleAdapterDataObserver extends androidx.recyclerview.widget.RecyclerView.AdapterDataObserver {
    static final int MUID_STATIC = getMUID();
    public abstract void anythingChanged();


    @java.lang.Override
    public void onChanged() {
        anythingChanged();
    }


    @java.lang.Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        anythingChanged();
    }


    @java.lang.Override
    public void onItemRangeChanged(int positionStart, int itemCount, @androidx.annotation.Nullable
    java.lang.Object payload) {
        anythingChanged();
    }


    @java.lang.Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        anythingChanged();
    }


    @java.lang.Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        anythingChanged();
    }


    @java.lang.Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        anythingChanged();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
