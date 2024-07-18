package com.automattic.simplenote.widgets;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * RecyclerView with setEmptyView method which displays a view when RecyclerView adapter is empty.
 */
public class EmptyViewRecyclerView extends androidx.recyclerview.widget.RecyclerView {
    static final int MUID_STATIC = getMUID();
    private android.view.View mEmptyView;

    private final androidx.recyclerview.widget.RecyclerView.AdapterDataObserver mObserver = new androidx.recyclerview.widget.RecyclerView.AdapterDataObserver() {
        @java.lang.Override
        public void onChanged() {
            toggleEmptyView();
        }


        @java.lang.Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleEmptyView();
        }


        @java.lang.Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleEmptyView();
        }

    };

    public EmptyViewRecyclerView(android.content.Context context) {
        super(context);
    }


    public EmptyViewRecyclerView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public EmptyViewRecyclerView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @java.lang.Override
    public void setAdapter(androidx.recyclerview.widget.RecyclerView.Adapter adapterNew) {
        final androidx.recyclerview.widget.RecyclerView.Adapter adapterOld;
        adapterOld = getAdapter();
        if (adapterOld != null) {
            adapterOld.unregisterAdapterDataObserver(mObserver);
        }
        super.setAdapter(adapterNew);
        if (adapterNew != null) {
            adapterNew.registerAdapterDataObserver(mObserver);
        }
        toggleEmptyView();
    }


    public void setEmptyView(android.view.View emptyView) {
        mEmptyView = emptyView;
        toggleEmptyView();
    }


    private void toggleEmptyView() {
        if ((mEmptyView != null) && (getAdapter() != null)) {
            final boolean empty;
            empty = getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(empty ? android.view.View.VISIBLE : android.view.View.GONE);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
