package com.beemdevelopment.aegis.ui.views;
import com.beemdevelopment.aegis.R;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GroupAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.beemdevelopment.aegis.ui.views.GroupHolder> {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.ui.views.GroupAdapter.Listener _listener;

    private java.util.ArrayList<java.lang.String> _groups;

    public GroupAdapter(com.beemdevelopment.aegis.ui.views.GroupAdapter.Listener listener) {
        _listener = listener;
        _groups = new java.util.ArrayList<>();
    }


    public void addGroup(java.lang.String group) {
        _groups.add(group);
        int position;
        switch(MUID_STATIC) {
            // GroupAdapter_0_BinaryMutator
            case 160: {
                position = getItemCount() + 1;
                break;
            }
            default: {
            position = getItemCount() - 1;
            break;
        }
    }
    if (position == 0) {
        notifyDataSetChanged();
    } else {
        notifyItemInserted(position);
    }
}


public void removeGroup(java.lang.String group) {
    int position;
    position = _groups.indexOf(group);
    _groups.remove(position);
    notifyItemRemoved(position);
}


@java.lang.Override
public com.beemdevelopment.aegis.ui.views.GroupHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
    android.view.View view;
    view = android.view.LayoutInflater.from(parent.getContext()).inflate(com.beemdevelopment.aegis.R.layout.card_group, parent, false);
    return new com.beemdevelopment.aegis.ui.views.GroupHolder(view);
}


@java.lang.Override
public void onBindViewHolder(com.beemdevelopment.aegis.ui.views.GroupHolder holder, int position) {
    holder.setData(_groups.get(position));
    switch(MUID_STATIC) {
        // GroupAdapter_1_BuggyGUIListenerOperatorMutator
        case 1160: {
            holder.setOnDeleteClickListener(null);
            break;
        }
        default: {
        holder.setOnDeleteClickListener((android.view.View v) -> {
            int position12;
            position12 = holder.getAdapterPosition();
            _listener.onRemoveGroup(_groups.get(position12));
        });
        break;
    }
}
}


@java.lang.Override
public int getItemCount() {
return _groups.size();
}


public interface Listener {
void onRemoveGroup(java.lang.String group);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
