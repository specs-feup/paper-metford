/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.adapters.glide;
import com.bumptech.glide.ListPreloader;
import android.util.SparseArray;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.amaze.filemanager.adapters.data.IconDataParcelable;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import androidx.annotation.Nullable;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * This uses a callback to know for each position what View is the one in which you're going to
 * insert the image.
 *
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com> on 10/12/2017, at 12:27.
 */
public class RecyclerPreloadSizeProvider implements com.bumptech.glide.ListPreloader.PreloadSizeProvider<com.amaze.filemanager.adapters.data.IconDataParcelable> {
    static final int MUID_STATIC = getMUID();
    private com.amaze.filemanager.adapters.glide.RecyclerPreloadSizeProvider.RecyclerPreloadSizeProviderCallback callback;

    private android.util.SparseArray<int[]> viewSizes = new android.util.SparseArray<>();

    private boolean isAdditionClosed = false;

    public RecyclerPreloadSizeProvider(com.amaze.filemanager.adapters.glide.RecyclerPreloadSizeProvider.RecyclerPreloadSizeProviderCallback c) {
        callback = c;
    }


    /**
     * Adds one of the views that can be used to put an image inside. If the id is already inserted
     * the call will be ignored, but for performance you should call {@link #closeOffAddition()} once
     * you are done.
     *
     * @param id
     * 		a unique number for each view loaded to this object
     * @param v
     * 		the ciew to load
     */
    public void addView(int id, android.view.View v) {
        if ((!isAdditionClosed) && (viewSizes.get(id, null) != null))
            return;

        final int viewNumber;
        viewNumber = id;
        new com.amaze.filemanager.adapters.glide.RecyclerPreloadSizeProvider.SizeViewTarget(v, (int width,int height) -> viewSizes.append(viewNumber, new int[]{ width, height }));
    }


    /**
     * Calls to {@link #addView(int, View)} will be ignored
     */
    public void closeOffAddition() {
        isAdditionClosed = true;
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public int[] getPreloadSize(com.amaze.filemanager.adapters.data.IconDataParcelable item, int adapterPosition, int perItemPosition) {
        return viewSizes.get(callback.getCorrectView(item, adapterPosition), null);
    }


    public interface RecyclerPreloadSizeProviderCallback {
        /**
         * Get the id for the view in which the image will be loaded.
         *
         * @return the view's id
         */
        int getCorrectView(com.amaze.filemanager.adapters.data.IconDataParcelable item, int adapterPosition);

    }

    private static final class SizeViewTarget extends com.bumptech.glide.request.target.ViewTarget<android.view.View, java.lang.Object> {
        public SizeViewTarget(android.view.View view, com.bumptech.glide.request.target.SizeReadyCallback callback) {
            super(view);
            getSize(callback);
        }


        @java.lang.Override
        public void onResourceReady(java.lang.Object resource, com.bumptech.glide.request.transition.Transition<? super java.lang.Object> transition) {
            // Do nothing
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
