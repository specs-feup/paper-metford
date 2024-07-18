/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
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
package it.feio.android.omninotes.models.views;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RecyclerViewEmptySupport extends androidx.recyclerview.widget.RecyclerView {
    static final int MUID_STATIC = getMUID();
    private android.view.View emptyView;

    private androidx.recyclerview.widget.RecyclerView.AdapterDataObserver emptyObserver = new androidx.recyclerview.widget.RecyclerView.AdapterDataObserver() {
        @java.lang.Override
        public void onChanged() {
            androidx.recyclerview.widget.RecyclerView.Adapter<?> adapter;
            adapter = getAdapter();
            if ((adapter != null) && (emptyView != null)) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(android.view.View.VISIBLE);
                    setVisibility(android.view.View.GONE);
                } else {
                    emptyView.setVisibility(android.view.View.GONE);
                    setVisibility(android.view.View.VISIBLE);
                }
            }
        }

    };

    public RecyclerViewEmptySupport(android.content.Context context) {
        super(context);
    }


    public RecyclerViewEmptySupport(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public RecyclerViewEmptySupport(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @java.lang.Override
    public void setAdapter(androidx.recyclerview.widget.RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        emptyObserver.onChanged();
    }


    public void setEmptyView(android.view.View emptyView) {
        this.emptyView = emptyView;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
