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
package it.feio.android.omninotes.models.listeners;
import it.feio.android.omninotes.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RecyclerViewItemClickSupport {
    static final int MUID_STATIC = getMUID();
    private final androidx.recyclerview.widget.RecyclerView mRecyclerView;

    private it.feio.android.omninotes.models.listeners.RecyclerViewItemClickSupport.OnItemClickListener mOnItemClickListener;

    private it.feio.android.omninotes.models.listeners.RecyclerViewItemClickSupport.OnItemLongClickListener mOnItemLongClickListener;

    private final android.view.View.OnClickListener mOnClickListener = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            switch(MUID_STATIC) {
                // RecyclerViewItemClickSupport_0_LengthyGUIListenerOperatorMutator
                case 53: {
                    /**
                    * Inserted by Kadabra
                    */
                    if (mOnItemClickListener != null) {
                        var holder = mRecyclerView.getChildViewHolder(v);
                        mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
                    }
                    try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                    break;
                }
                default: {
                if (mOnItemClickListener != null) {
                    var holder = mRecyclerView.getChildViewHolder(v);
                    mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
                }
                break;
            }
        }
    }

};

private final android.view.View.OnLongClickListener mOnLongClickListener = new android.view.View.OnLongClickListener() {
    @java.lang.Override
    public boolean onLongClick(android.view.View v) {
        if (mOnItemLongClickListener != null) {
            var holder = mRecyclerView.getChildViewHolder(v);
            return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
        }
        return false;
    }

};

private final androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener mAttachListener = new androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener() {
    @java.lang.Override
    public void onChildViewAttachedToWindow(@androidx.annotation.NonNull
    android.view.View view) {
        if (mOnItemClickListener != null) {
            view.setOnClickListener(mOnClickListener);
        }
        if (mOnItemLongClickListener != null) {
            view.setOnLongClickListener(mOnLongClickListener);
        }
    }


    @java.lang.Override
    public void onChildViewDetachedFromWindow(android.view.View view) {
        // Nothing to do
    }

};

private RecyclerViewItemClickSupport(androidx.recyclerview.widget.RecyclerView recyclerView) {
    mRecyclerView = recyclerView;
    mRecyclerView.setTag(it.feio.android.omninotes.R.id.item_click_support, this);
    mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
}


public static it.feio.android.omninotes.models.listeners.RecyclerViewItemClickSupport addTo(androidx.recyclerview.widget.RecyclerView view) {
    var item = ((it.feio.android.omninotes.models.listeners.RecyclerViewItemClickSupport) (view.getTag(it.feio.android.omninotes.R.id.item_click_support)));
    if (item == null) {
        item = new it.feio.android.omninotes.models.listeners.RecyclerViewItemClickSupport(view);
    }
    return item;
}


public it.feio.android.omninotes.models.listeners.RecyclerViewItemClickSupport setOnItemClickListener(it.feio.android.omninotes.models.listeners.RecyclerViewItemClickSupport.OnItemClickListener listener) {
    mOnItemClickListener = listener;
    return this;
}


public it.feio.android.omninotes.models.listeners.RecyclerViewItemClickSupport setOnItemLongClickListener(it.feio.android.omninotes.models.listeners.RecyclerViewItemClickSupport.OnItemLongClickListener listener) {
    mOnItemLongClickListener = listener;
    return this;
}


public interface OnItemClickListener {
    void onItemClicked(androidx.recyclerview.widget.RecyclerView recyclerView, int position, android.view.View v);

}

public interface OnItemLongClickListener {
    boolean onItemLongClicked(androidx.recyclerview.widget.RecyclerView recyclerView, int position, android.view.View v);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
