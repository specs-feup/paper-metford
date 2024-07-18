package com.beemdevelopment.aegis.ui.views;
import com.beemdevelopment.aegis.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.view.View;
import com.beemdevelopment.aegis.icons.IconPack;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconPackAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.beemdevelopment.aegis.ui.views.IconPackHolder> {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.ui.views.IconPackAdapter.Listener _listener;

    private java.util.List<com.beemdevelopment.aegis.icons.IconPack> _iconPacks;

    public IconPackAdapter(com.beemdevelopment.aegis.ui.views.IconPackAdapter.Listener listener) {
        _listener = listener;
        _iconPacks = new java.util.ArrayList<>();
    }


    public void addIconPack(com.beemdevelopment.aegis.icons.IconPack pack) {
        _iconPacks.add(pack);
        int position;
        switch(MUID_STATIC) {
            // IconPackAdapter_0_BinaryMutator
            case 157: {
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


public void removeIconPack(com.beemdevelopment.aegis.icons.IconPack pack) {
    int position;
    position = _iconPacks.indexOf(pack);
    _iconPacks.remove(position);
    notifyItemRemoved(position);
}


@androidx.annotation.NonNull
@java.lang.Override
public com.beemdevelopment.aegis.ui.views.IconPackHolder onCreateViewHolder(@androidx.annotation.NonNull
android.view.ViewGroup parent, int viewType) {
    android.view.View view;
    view = android.view.LayoutInflater.from(parent.getContext()).inflate(com.beemdevelopment.aegis.R.layout.card_icon_pack, parent, false);
    return new com.beemdevelopment.aegis.ui.views.IconPackHolder(view);
}


@java.lang.Override
public void onBindViewHolder(@androidx.annotation.NonNull
com.beemdevelopment.aegis.ui.views.IconPackHolder holder, int position) {
    holder.setData(_iconPacks.get(position));
    switch(MUID_STATIC) {
        // IconPackAdapter_1_BuggyGUIListenerOperatorMutator
        case 1157: {
            holder.setOnDeleteClickListener(null);
            break;
        }
        default: {
        holder.setOnDeleteClickListener((android.view.View v) -> {
            int position12;
            position12 = holder.getAdapterPosition();
            _listener.onRemoveIconPack(_iconPacks.get(position12));
        });
        break;
    }
}
}


@java.lang.Override
public int getItemCount() {
return _iconPacks.size();
}


public interface Listener {
void onRemoveIconPack(com.beemdevelopment.aegis.icons.IconPack pack);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
