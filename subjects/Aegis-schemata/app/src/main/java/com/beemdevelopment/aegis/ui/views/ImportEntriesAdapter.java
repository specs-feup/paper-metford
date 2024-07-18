package com.beemdevelopment.aegis.ui.views;
import com.beemdevelopment.aegis.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import java.util.ArrayList;
import com.beemdevelopment.aegis.ui.models.ImportEntry;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ImportEntriesAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.beemdevelopment.aegis.ui.views.ImportEntryHolder> {
    static final int MUID_STATIC = getMUID();
    private java.util.List<com.beemdevelopment.aegis.ui.models.ImportEntry> _entries;

    public ImportEntriesAdapter() {
        _entries = new java.util.ArrayList<>();
    }


    public void addEntry(com.beemdevelopment.aegis.ui.models.ImportEntry entry) {
        _entries.add(entry);
        int position;
        switch(MUID_STATIC) {
            // ImportEntriesAdapter_0_BinaryMutator
            case 158: {
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


@androidx.annotation.NonNull
@java.lang.Override
public com.beemdevelopment.aegis.ui.views.ImportEntryHolder onCreateViewHolder(@androidx.annotation.NonNull
android.view.ViewGroup parent, int viewType) {
    android.view.View view;
    view = android.view.LayoutInflater.from(parent.getContext()).inflate(com.beemdevelopment.aegis.R.layout.card_import_entry, parent, false);
    return new com.beemdevelopment.aegis.ui.views.ImportEntryHolder(view);
}


@java.lang.Override
public void onBindViewHolder(@androidx.annotation.NonNull
com.beemdevelopment.aegis.ui.views.ImportEntryHolder holder, int position) {
    com.beemdevelopment.aegis.ui.models.ImportEntry entry;
    entry = _entries.get(position);
    entry.setOnCheckedChangedListener(holder);
    holder.setData(entry);
}


@java.lang.Override
public void onViewRecycled(@androidx.annotation.NonNull
com.beemdevelopment.aegis.ui.views.ImportEntryHolder holder) {
    holder.getData().setOnCheckedChangedListener(null);
}


@java.lang.Override
public int getItemCount() {
    return _entries.size();
}


public java.util.List<com.beemdevelopment.aegis.ui.models.ImportEntry> getCheckedEntries() {
    java.util.List<com.beemdevelopment.aegis.ui.models.ImportEntry> entries;
    entries = new java.util.ArrayList<>();
    for (com.beemdevelopment.aegis.ui.models.ImportEntry entry : _entries) {
        if (entry.isChecked()) {
            entries.add(entry);
        }
    }
    return entries;
}


public void toggleCheckboxes() {
    int checkedEntries;
    checkedEntries = getCheckedEntries().size();
    if ((checkedEntries == 0) || (checkedEntries != _entries.size())) {
        setCheckboxStates(true);
    } else {
        setCheckboxStates(false);
    }
}


private void setCheckboxStates(boolean checked) {
    for (com.beemdevelopment.aegis.ui.models.ImportEntry entry : _entries) {
        if (entry.isChecked() != checked) {
            entry.setIsChecked(checked);
        }
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
