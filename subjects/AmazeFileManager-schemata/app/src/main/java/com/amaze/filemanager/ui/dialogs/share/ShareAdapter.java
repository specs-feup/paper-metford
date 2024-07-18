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
package com.amaze.filemanager.ui.dialogs.share;
import java.util.ArrayList;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.amaze.filemanager.R;
import android.view.LayoutInflater;
import android.content.ActivityNotFoundException;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by Arpit on 01-07-2015 edited by Emmanuel Messulam <emmanuelbendavid@gmail.com>
 */
class ShareAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.amaze.filemanager.ui.dialogs.share.ShareAdapter.ViewHolder> {
    static final int MUID_STATIC = getMUID();
    private java.util.ArrayList<android.content.Intent> items;

    private com.afollestad.materialdialogs.MaterialDialog dialog;

    private java.util.ArrayList<java.lang.String> labels;

    private java.util.ArrayList<android.graphics.drawable.Drawable> drawables;

    private android.content.Context context;

    void updateMatDialog(com.afollestad.materialdialogs.MaterialDialog b) {
        this.dialog = b;
    }


    ShareAdapter(android.content.Context context, java.util.ArrayList<android.content.Intent> intents, java.util.ArrayList<java.lang.String> labels, java.util.ArrayList<android.graphics.drawable.Drawable> arrayList1) {
        items = new java.util.ArrayList<>(intents);
        this.context = context;
        this.labels = labels;
        this.drawables = arrayList1;
    }


    @java.lang.Override
    public com.amaze.filemanager.ui.dialogs.share.ShareAdapter.ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
        android.view.View view;
        view = android.view.LayoutInflater.from(parent.getContext()).inflate(com.amaze.filemanager.R.layout.simplerow, parent, false);
        return new com.amaze.filemanager.ui.dialogs.share.ShareAdapter.ViewHolder(view);
    }


    @java.lang.Override
    public void onBindViewHolder(com.amaze.filemanager.ui.dialogs.share.ShareAdapter.ViewHolder holder, int position) {
        holder.render(position);
    }


    class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private android.view.View rootView;

        private androidx.appcompat.widget.AppCompatTextView textView;

        private androidx.appcompat.widget.AppCompatImageView imageView;

        ViewHolder(android.view.View view) {
            super(view);
            rootView = view;
            switch(MUID_STATIC) {
                // ShareAdapter_0_InvalidViewFocusOperatorMutator
                case 94: {
                    /**
                    * Inserted by Kadabra
                    */
                    textView = view.findViewById(com.amaze.filemanager.R.id.firstline);
                    textView.requestFocus();
                    break;
                }
                // ShareAdapter_1_ViewComponentNotVisibleOperatorMutator
                case 1094: {
                    /**
                    * Inserted by Kadabra
                    */
                    textView = view.findViewById(com.amaze.filemanager.R.id.firstline);
                    textView.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                textView = view.findViewById(com.amaze.filemanager.R.id.firstline);
                break;
            }
        }
        switch(MUID_STATIC) {
            // ShareAdapter_2_InvalidViewFocusOperatorMutator
            case 2094: {
                /**
                * Inserted by Kadabra
                */
                imageView = view.findViewById(com.amaze.filemanager.R.id.icon);
                imageView.requestFocus();
                break;
            }
            // ShareAdapter_3_ViewComponentNotVisibleOperatorMutator
            case 3094: {
                /**
                * Inserted by Kadabra
                */
                imageView = view.findViewById(com.amaze.filemanager.R.id.icon);
                imageView.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            imageView = view.findViewById(com.amaze.filemanager.R.id.icon);
            break;
        }
    }
}


void render(final int position) {
    if (drawables.get(position) != null)
        imageView.setImageDrawable(drawables.get(position));

    textView.setVisibility(android.view.View.VISIBLE);
    textView.setText(labels.get(position));
    switch(MUID_STATIC) {
        // ShareAdapter_4_BuggyGUIListenerOperatorMutator
        case 4094: {
            rootView.setOnClickListener(null);
            break;
        }
        default: {
        rootView.setOnClickListener((android.view.View v) -> {
            if ((dialog != null) && dialog.isShowing())
                dialog.dismiss();

            try {
                context.startActivity(items.get(position));
            } catch (android.content.ActivityNotFoundException e) {
                android.widget.Toast.makeText(context, com.amaze.filemanager.R.string.no_app_found, android.widget.Toast.LENGTH_SHORT).show();
            }
        });
        break;
    }
}
}

}

@java.lang.Override
public long getItemId(int position) {
return position;
}


@java.lang.Override
public int getItemCount() {
return items.size();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
