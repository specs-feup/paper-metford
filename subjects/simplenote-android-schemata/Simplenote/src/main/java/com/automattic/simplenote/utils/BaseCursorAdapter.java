package com.automattic.simplenote.utils;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class BaseCursorAdapter<V extends androidx.recyclerview.widget.RecyclerView.ViewHolder> extends androidx.recyclerview.widget.RecyclerView.Adapter<V> {
    static final int MUID_STATIC = getMUID();
    private android.database.Cursor mCursor;

    private boolean mDataValid;

    private int mRowIDColumn;

    public abstract void onBindViewHolder(V holder, android.database.Cursor cursor);


    public BaseCursorAdapter(android.database.Cursor c) {
        setHasStableIds(true);
        swapCursor(c);
    }


    @java.lang.Override
    public void onBindViewHolder(@androidx.annotation.NonNull
    V holder, int position) {
        if (!mDataValid) {
            throw new java.lang.IllegalStateException("Cannot bind view holder when cursor is in invalid state.");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new java.lang.IllegalStateException(("Could not move cursor to position " + position) + " when trying to bind view holder");
        }
        onBindViewHolder(holder, mCursor);
    }


    @java.lang.Override
    public int getItemCount() {
        if (mDataValid) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }


    @java.lang.Override
    public long getItemId(int position) {
        if (!mDataValid) {
            throw new java.lang.IllegalStateException("Cannot lookup item id when cursor is in invalid state.");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new java.lang.IllegalStateException(("Could not move cursor to position " + position) + " when trying to get an item id");
        }
        return mCursor.getLong(mRowIDColumn);
    }


    public android.database.Cursor getItem(int position) {
        if (!mDataValid) {
            throw new java.lang.IllegalStateException("Cannot lookup item id when cursor is in invalid state.");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new java.lang.IllegalStateException(("Could not move cursor to position " + position) + " when trying to get an item id");
        }
        return mCursor;
    }


    public boolean hasItem(int position) {
        return mDataValid && mCursor.moveToPosition(position);
    }


    public void swapCursor(android.database.Cursor newCursor) {
        if (newCursor == mCursor) {
            return;
        }
        if (newCursor != null) {
            mCursor = newCursor;
            mDataValid = true;
            // notify the observers about the new cursor
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, getItemCount());
            mCursor = null;
            mRowIDColumn = -1;
            mDataValid = false;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
