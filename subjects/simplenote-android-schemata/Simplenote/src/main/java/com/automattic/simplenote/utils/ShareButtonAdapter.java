package com.automattic.simplenote.utils;
import com.automattic.simplenote.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ShareButtonAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.automattic.simplenote.utils.ShareButtonAdapter.ViewHolder> {
    static final int MUID_STATIC = getMUID();
    private java.util.List<com.automattic.simplenote.utils.ShareButtonAdapter.ShareButtonItem> mItems;

    private com.automattic.simplenote.utils.ShareButtonAdapter.ItemListener mListener;

    public ShareButtonAdapter(java.util.List<com.automattic.simplenote.utils.ShareButtonAdapter.ShareButtonItem> items, com.automattic.simplenote.utils.ShareButtonAdapter.ItemListener listener) {
        mItems = items;
        mListener = listener;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public com.automattic.simplenote.utils.ShareButtonAdapter.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull
    android.view.ViewGroup parent, int viewType) {
        return new com.automattic.simplenote.utils.ShareButtonAdapter.ViewHolder(android.view.LayoutInflater.from(parent.getContext()).inflate(com.automattic.simplenote.R.layout.share_button_item, parent, false));
    }


    @java.lang.Override
    public void onBindViewHolder(@androidx.annotation.NonNull
    com.automattic.simplenote.utils.ShareButtonAdapter.ViewHolder holder, int position) {
        holder.setData(mItems.get(position));
    }


    @java.lang.Override
    public int getItemCount() {
        return mItems.size();
    }


    public interface ItemListener {
        void onItemClick(com.automattic.simplenote.utils.ShareButtonAdapter.ShareButtonItem item);

    }

    public static class ShareButtonItem {
        private android.graphics.drawable.Drawable mDrawableRes;

        private java.lang.CharSequence mTitle;

        private java.lang.String mPackageName;

        private java.lang.String mActivityName;

        public ShareButtonItem(android.graphics.drawable.Drawable drawable, java.lang.CharSequence title, java.lang.String packageName, java.lang.String activityName) {
            mDrawableRes = drawable;
            mTitle = title;
            mPackageName = packageName;
            mActivityName = activityName;
        }


        public android.graphics.drawable.Drawable getDrawable() {
            return mDrawableRes;
        }


        public java.lang.CharSequence getTitle() {
            return mTitle;
        }


        public java.lang.String getPackageName() {
            return mPackageName;
        }


        public java.lang.String getActivityName() {
            return mActivityName;
        }

    }

    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener {
        public android.widget.TextView button;

        public com.automattic.simplenote.utils.ShareButtonAdapter.ShareButtonItem item;

        public ViewHolder(android.view.View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            switch(MUID_STATIC) {
                // ShareButtonAdapter_0_InvalidViewFocusOperatorMutator
                case 44: {
                    /**
                    * Inserted by Kadabra
                    */
                    button = itemView.findViewById(com.automattic.simplenote.R.id.share_button);
                    button.requestFocus();
                    break;
                }
                // ShareButtonAdapter_1_ViewComponentNotVisibleOperatorMutator
                case 144: {
                    /**
                    * Inserted by Kadabra
                    */
                    button = itemView.findViewById(com.automattic.simplenote.R.id.share_button);
                    button.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                button = itemView.findViewById(com.automattic.simplenote.R.id.share_button);
                break;
            }
        }
    }


    public void setData(com.automattic.simplenote.utils.ShareButtonAdapter.ShareButtonItem item) {
        this.item = item;
        button.setCompoundDrawablesWithIntrinsicBounds(null, item.getDrawable(), null, null);
        button.setText(item.getTitle());
    }


    @java.lang.Override
    public void onClick(android.view.View v) {
        if (mListener != null) {
            mListener.onItemClick(item);
        }
    }

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
