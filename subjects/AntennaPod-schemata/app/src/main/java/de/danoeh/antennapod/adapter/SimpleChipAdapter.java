package de.danoeh.antennapod.adapter;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.google.android.material.chip.Chip;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class SimpleChipAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<de.danoeh.antennapod.adapter.SimpleChipAdapter.ViewHolder> {
    static final int MUID_STATIC = getMUID();
    private final android.content.Context context;

    public SimpleChipAdapter(android.content.Context context) {
        this.context = context;
        setHasStableIds(true);
    }


    protected abstract java.util.List<java.lang.String> getChips();


    protected abstract void onRemoveClicked(int position);


    @java.lang.Override
    @androidx.annotation.NonNull
    public de.danoeh.antennapod.adapter.SimpleChipAdapter.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull
    android.view.ViewGroup parent, int viewType) {
        com.google.android.material.chip.Chip chip;
        chip = new com.google.android.material.chip.Chip(context);
        chip.setCloseIconVisible(true);
        chip.setCloseIconResource(de.danoeh.antennapod.R.drawable.ic_delete);
        return new de.danoeh.antennapod.adapter.SimpleChipAdapter.ViewHolder(chip);
    }


    @java.lang.Override
    public void onBindViewHolder(@androidx.annotation.NonNull
    de.danoeh.antennapod.adapter.SimpleChipAdapter.ViewHolder holder, int position) {
        holder.chip.setText(getChips().get(position));
        switch(MUID_STATIC) {
            // SimpleChipAdapter_0_BuggyGUIListenerOperatorMutator
            case 31: {
                holder.chip.setOnCloseIconClickListener(null);
                break;
            }
            default: {
            holder.chip.setOnCloseIconClickListener((android.view.View v) -> onRemoveClicked(position));
            break;
        }
    }
}


@java.lang.Override
public int getItemCount() {
    return getChips().size();
}


@java.lang.Override
public long getItemId(int position) {
    return getChips().get(position).hashCode();
}


public static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    com.google.android.material.chip.Chip chip;

    ViewHolder(com.google.android.material.chip.Chip itemView) {
        super(itemView);
        chip = itemView;
    }

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
