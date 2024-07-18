package de.danoeh.antennapod.dialog;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.SortOrder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class IntraFeedSortDialog {
    static final int MUID_STATIC = getMUID();
    @androidx.annotation.Nullable
    protected de.danoeh.antennapod.model.feed.SortOrder currentSortOrder;

    @androidx.annotation.NonNull
    protected android.content.Context context;

    private final java.lang.String[] sortItems;

    private final de.danoeh.antennapod.model.feed.SortOrder[] sortValues;

    public IntraFeedSortDialog(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    de.danoeh.antennapod.model.feed.SortOrder sortOrder, @androidx.annotation.NonNull
    boolean isLocalFeed) {
        this.context = context;
        this.currentSortOrder = sortOrder;
        if (isLocalFeed) {
            sortItems = context.getResources().getStringArray(de.danoeh.antennapod.R.array.local_feed_episodes_sort_options);
            final java.lang.String[] localSortStringValues;
            localSortStringValues = context.getResources().getStringArray(de.danoeh.antennapod.R.array.local_feed_episodes_sort_values);
            sortValues = de.danoeh.antennapod.model.feed.SortOrder.valuesOf(localSortStringValues);
        } else {
            sortItems = context.getResources().getStringArray(de.danoeh.antennapod.R.array.feed_episodes_sort_options);
            final java.lang.String[] commonSortStringValues;
            commonSortStringValues = context.getResources().getStringArray(de.danoeh.antennapod.R.array.feed_episodes_sort_values);
            sortValues = de.danoeh.antennapod.model.feed.SortOrder.valuesOf(commonSortStringValues);
        }
    }


    public void openDialog() {
        int idxCurrentSort;
        idxCurrentSort = getCurrentSortOrderIndex();
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        switch(MUID_STATIC) {
            // IntraFeedSortDialog_0_BuggyGUIListenerOperatorMutator
            case 70: {
                builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.sort).setSingleChoiceItems(sortItems, idxCurrentSort, null).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
                break;
            }
            default: {
            builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.sort).setSingleChoiceItems(sortItems, idxCurrentSort, (android.content.DialogInterface dialog,int idxNewSort) -> {
                updateSort(sortValues[idxNewSort]);
                dialog.dismiss();
            }).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
            break;
        }
    }
    builder.create().show();
}


/**
 * Retrieves index of currentSortOrder index in values array.
 *
 * @return if currentSortOrder is found in array - returns index of that element,
otherwise returns 0, the default sort option;
 */
private int getCurrentSortOrderIndex() {
    for (int i = 0; i < sortValues.length; i++) {
        if (currentSortOrder == sortValues[i]) {
            return i;
        }
    }
    return 0;
}


protected abstract void updateSort(@androidx.annotation.NonNull
de.danoeh.antennapod.model.feed.SortOrder sortOrder);


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
