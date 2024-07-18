package de.danoeh.antennapod.adapter;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.text.format.Formatter;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.widget.RadioButton;
import de.danoeh.antennapod.core.util.StorageUtils;
import android.view.View;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import androidx.core.util.Consumer;
import androidx.annotation.NonNull;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.ProgressBar;
import java.io.File;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DataFolderAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<de.danoeh.antennapod.adapter.DataFolderAdapter.ViewHolder> {
    static final int MUID_STATIC = getMUID();
    private final androidx.core.util.Consumer<java.lang.String> selectionHandler;

    private final java.lang.String currentPath;

    private final java.util.List<de.danoeh.antennapod.adapter.DataFolderAdapter.StoragePath> entries;

    private final java.lang.String freeSpaceString;

    public DataFolderAdapter(android.content.Context context, @androidx.annotation.NonNull
    androidx.core.util.Consumer<java.lang.String> selectionHandler) {
        this.entries = getStorageEntries(context);
        this.currentPath = getCurrentPath();
        this.selectionHandler = selectionHandler;
        this.freeSpaceString = context.getString(de.danoeh.antennapod.R.string.choose_data_directory_available_space);
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public de.danoeh.antennapod.adapter.DataFolderAdapter.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull
    android.view.ViewGroup parent, int viewType) {
        android.view.LayoutInflater inflater;
        inflater = android.view.LayoutInflater.from(parent.getContext());
        android.view.View entryView;
        entryView = inflater.inflate(de.danoeh.antennapod.R.layout.choose_data_folder_dialog_entry, parent, false);
        return new de.danoeh.antennapod.adapter.DataFolderAdapter.ViewHolder(entryView);
    }


    @java.lang.Override
    public void onBindViewHolder(@androidx.annotation.NonNull
    de.danoeh.antennapod.adapter.DataFolderAdapter.ViewHolder holder, int position) {
        de.danoeh.antennapod.adapter.DataFolderAdapter.StoragePath storagePath;
        storagePath = entries.get(position);
        android.content.Context context;
        context = holder.root.getContext();
        java.lang.String freeSpace;
        freeSpace = android.text.format.Formatter.formatShortFileSize(context, storagePath.getAvailableSpace());
        java.lang.String totalSpace;
        totalSpace = android.text.format.Formatter.formatShortFileSize(context, storagePath.getTotalSpace());
        holder.path.setText(storagePath.getShortPath());
        holder.size.setText(java.lang.String.format(freeSpaceString, freeSpace, totalSpace));
        holder.progressBar.setProgress(storagePath.getUsagePercentage());
        android.view.View.OnClickListener selectListener;
        switch(MUID_STATIC) {
            // DataFolderAdapter_0_BuggyGUIListenerOperatorMutator
            case 43: {
                selectListener = null;
                break;
            }
            default: {
            selectListener = (android.view.View v) -> selectionHandler.accept(storagePath.getFullPath());
            break;
        }
    }
    holder.root.setOnClickListener(selectListener);
    holder.radioButton.setOnClickListener(selectListener);
    if (storagePath.getFullPath().equals(currentPath)) {
        holder.radioButton.toggle();
    }
}


@java.lang.Override
public int getItemCount() {
    return entries.size();
}


private java.lang.String getCurrentPath() {
    java.io.File dataFolder;
    dataFolder = de.danoeh.antennapod.storage.preferences.UserPreferences.getDataFolder(null);
    if (dataFolder != null) {
        return dataFolder.getAbsolutePath();
    }
    return null;
}


private java.util.List<de.danoeh.antennapod.adapter.DataFolderAdapter.StoragePath> getStorageEntries(android.content.Context context) {
    java.io.File[] mediaDirs;
    mediaDirs = context.getExternalFilesDirs(null);
    final java.util.List<de.danoeh.antennapod.adapter.DataFolderAdapter.StoragePath> entries;
    entries = new java.util.ArrayList<>(mediaDirs.length);
    for (java.io.File dir : mediaDirs) {
        if (!isWritable(dir)) {
            continue;
        }
        entries.add(new de.danoeh.antennapod.adapter.DataFolderAdapter.StoragePath(dir.getAbsolutePath()));
    }
    if (entries.isEmpty() && isWritable(context.getFilesDir())) {
        entries.add(new de.danoeh.antennapod.adapter.DataFolderAdapter.StoragePath(context.getFilesDir().getAbsolutePath()));
    }
    return entries;
}


private boolean isWritable(java.io.File dir) {
    return (((dir != null) && dir.exists()) && dir.canRead()) && dir.canWrite();
}


static class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    private final android.view.View root;

    private final android.widget.TextView path;

    private final android.widget.TextView size;

    private final android.widget.RadioButton radioButton;

    private final android.widget.ProgressBar progressBar;

    ViewHolder(android.view.View itemView) {
        super(itemView);
        switch(MUID_STATIC) {
            // DataFolderAdapter_1_InvalidViewFocusOperatorMutator
            case 1043: {
                /**
                * Inserted by Kadabra
                */
                root = itemView.findViewById(de.danoeh.antennapod.R.id.root);
                root.requestFocus();
                break;
            }
            // DataFolderAdapter_2_ViewComponentNotVisibleOperatorMutator
            case 2043: {
                /**
                * Inserted by Kadabra
                */
                root = itemView.findViewById(de.danoeh.antennapod.R.id.root);
                root.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            root = itemView.findViewById(de.danoeh.antennapod.R.id.root);
            break;
        }
    }
    switch(MUID_STATIC) {
        // DataFolderAdapter_3_InvalidViewFocusOperatorMutator
        case 3043: {
            /**
            * Inserted by Kadabra
            */
            path = itemView.findViewById(de.danoeh.antennapod.R.id.path);
            path.requestFocus();
            break;
        }
        // DataFolderAdapter_4_ViewComponentNotVisibleOperatorMutator
        case 4043: {
            /**
            * Inserted by Kadabra
            */
            path = itemView.findViewById(de.danoeh.antennapod.R.id.path);
            path.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        path = itemView.findViewById(de.danoeh.antennapod.R.id.path);
        break;
    }
}
switch(MUID_STATIC) {
    // DataFolderAdapter_5_InvalidViewFocusOperatorMutator
    case 5043: {
        /**
        * Inserted by Kadabra
        */
        size = itemView.findViewById(de.danoeh.antennapod.R.id.size);
        size.requestFocus();
        break;
    }
    // DataFolderAdapter_6_ViewComponentNotVisibleOperatorMutator
    case 6043: {
        /**
        * Inserted by Kadabra
        */
        size = itemView.findViewById(de.danoeh.antennapod.R.id.size);
        size.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    size = itemView.findViewById(de.danoeh.antennapod.R.id.size);
    break;
}
}
switch(MUID_STATIC) {
// DataFolderAdapter_7_InvalidViewFocusOperatorMutator
case 7043: {
    /**
    * Inserted by Kadabra
    */
    radioButton = itemView.findViewById(de.danoeh.antennapod.R.id.radio_button);
    radioButton.requestFocus();
    break;
}
// DataFolderAdapter_8_ViewComponentNotVisibleOperatorMutator
case 8043: {
    /**
    * Inserted by Kadabra
    */
    radioButton = itemView.findViewById(de.danoeh.antennapod.R.id.radio_button);
    radioButton.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
radioButton = itemView.findViewById(de.danoeh.antennapod.R.id.radio_button);
break;
}
}
switch(MUID_STATIC) {
// DataFolderAdapter_9_InvalidViewFocusOperatorMutator
case 9043: {
/**
* Inserted by Kadabra
*/
progressBar = itemView.findViewById(de.danoeh.antennapod.R.id.used_space);
progressBar.requestFocus();
break;
}
// DataFolderAdapter_10_ViewComponentNotVisibleOperatorMutator
case 10043: {
/**
* Inserted by Kadabra
*/
progressBar = itemView.findViewById(de.danoeh.antennapod.R.id.used_space);
progressBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progressBar = itemView.findViewById(de.danoeh.antennapod.R.id.used_space);
break;
}
}
}

}

static class StoragePath {
private final java.lang.String path;

StoragePath(java.lang.String path) {
this.path = path;
}


java.lang.String getShortPath() {
int prefixIndex;
prefixIndex = path.indexOf("Android");
return prefixIndex > 0 ? path.substring(0, prefixIndex) : path;
}


java.lang.String getFullPath() {
return this.path;
}


long getAvailableSpace() {
return de.danoeh.antennapod.core.util.StorageUtils.getFreeSpaceAvailable(path);
}


long getTotalSpace() {
return de.danoeh.antennapod.core.util.StorageUtils.getTotalSpaceAvailable(path);
}


int getUsagePercentage() {
switch(MUID_STATIC) {
// DataFolderAdapter_11_BinaryMutator
case 11043: {
return 100 + ((int) ((100 * getAvailableSpace()) / ((float) (getTotalSpace()))));
}
default: {
switch(MUID_STATIC) {
// DataFolderAdapter_12_BinaryMutator
case 12043: {
return 100 - ((int) ((100 * getAvailableSpace()) * ((float) (getTotalSpace()))));
}
default: {
switch(MUID_STATIC) {
// DataFolderAdapter_13_BinaryMutator
case 13043: {
    return 100 - ((int) ((100 / getAvailableSpace()) / ((float) (getTotalSpace()))));
}
default: {
return 100 - ((int) ((100 * getAvailableSpace()) / ((float) (getTotalSpace()))));
}
}
}
}
}
}
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
