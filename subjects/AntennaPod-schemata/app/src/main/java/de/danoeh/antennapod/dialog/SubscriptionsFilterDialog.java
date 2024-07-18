package de.danoeh.antennapod.dialog;
import android.widget.LinearLayout;
import java.util.Set;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.core.feed.SubscriptionsFilterGroup;
import com.google.android.material.button.MaterialButtonToggleGroup;
import android.text.TextUtils;
import org.greenrobot.eventbus.EventBus;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import android.widget.Button;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import de.danoeh.antennapod.databinding.FilterDialogRowBinding;
import de.danoeh.antennapod.model.feed.SubscriptionsFilter;
import java.util.Arrays;
import java.util.HashSet;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SubscriptionsFilterDialog {
    static final int MUID_STATIC = getMUID();
    public static void showDialog(android.content.Context context) {
        de.danoeh.antennapod.model.feed.SubscriptionsFilter subscriptionsFilter;
        subscriptionsFilter = de.danoeh.antennapod.storage.preferences.UserPreferences.getSubscriptionsFilter();
        final java.util.Set<java.lang.String> filterValues;
        filterValues = new java.util.HashSet<>(java.util.Arrays.asList(subscriptionsFilter.getValues()));
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context);
        builder.setTitle(context.getString(de.danoeh.antennapod.R.string.pref_filter_feed_title));
        android.view.LayoutInflater inflater;
        inflater = android.view.LayoutInflater.from(context);
        android.view.View layout;
        layout = inflater.inflate(de.danoeh.antennapod.R.layout.filter_dialog, null, false);
        android.widget.LinearLayout rows;
        switch(MUID_STATIC) {
            // SubscriptionsFilterDialog_0_InvalidViewFocusOperatorMutator
            case 69: {
                /**
                * Inserted by Kadabra
                */
                rows = layout.findViewById(de.danoeh.antennapod.R.id.filter_rows);
                rows.requestFocus();
                break;
            }
            // SubscriptionsFilterDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1069: {
                /**
                * Inserted by Kadabra
                */
                rows = layout.findViewById(de.danoeh.antennapod.R.id.filter_rows);
                rows.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            rows = layout.findViewById(de.danoeh.antennapod.R.id.filter_rows);
            break;
        }
    }
    builder.setView(layout);
    for (de.danoeh.antennapod.core.feed.SubscriptionsFilterGroup item : de.danoeh.antennapod.core.feed.SubscriptionsFilterGroup.values()) {
        de.danoeh.antennapod.databinding.FilterDialogRowBinding binding;
        binding = de.danoeh.antennapod.databinding.FilterDialogRowBinding.inflate(inflater);
        binding.buttonGroup.setWeightSum(item.values.length);
        binding.filterButton1.setText(item.values[0].displayName);
        binding.filterButton1.setTag(item.values[0].filterId);
        if (item.values.length == 2) {
            binding.filterButton2.setText(item.values[1].displayName);
            binding.filterButton2.setTag(item.values[1].filterId);
        } else {
            binding.filterButton2.setVisibility(android.view.View.GONE);
        }
        binding.filterButton1.setMaxLines(3);
        binding.filterButton1.setSingleLine(false);
        binding.filterButton2.setMaxLines(3);
        binding.filterButton2.setSingleLine(false);
        rows.addView(binding.getRoot());
    }
    for (java.lang.String filterId : filterValues) {
        if (!android.text.TextUtils.isEmpty(filterId)) {
            android.widget.Button button;
            button = layout.findViewWithTag(filterId);
            if (button != null) {
                ((com.google.android.material.button.MaterialButtonToggleGroup) (button.getParent())).check(button.getId());
            }
        }
    }
    switch(MUID_STATIC) {
        // SubscriptionsFilterDialog_2_BuggyGUIListenerOperatorMutator
        case 2069: {
            builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
            break;
        }
        default: {
        builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> {
            filterValues.clear();
            for (int i = 0; i < rows.getChildCount(); i++) {
                if (!(rows.getChildAt(i) instanceof com.google.android.material.button.MaterialButtonToggleGroup)) {
                    continue;
                }
                com.google.android.material.button.MaterialButtonToggleGroup group;
                group = ((com.google.android.material.button.MaterialButtonToggleGroup) (rows.getChildAt(i)));
                if (group.getCheckedButtonId() == android.view.View.NO_ID) {
                    continue;
                }
                java.lang.String tag;
                tag = ((java.lang.String) (group.findViewById(group.getCheckedButtonId()).getTag()));
                if (tag == null) {
                    // Clear buttons use no tag
                    continue;
                }
                filterValues.add(tag);
            }
            de.danoeh.antennapod.dialog.SubscriptionsFilterDialog.updateFilter(filterValues);
        });
        break;
    }
}
builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
builder.show();
}


private static void updateFilter(java.util.Set<java.lang.String> filterValues) {
de.danoeh.antennapod.model.feed.SubscriptionsFilter subscriptionsFilter;
subscriptionsFilter = new de.danoeh.antennapod.model.feed.SubscriptionsFilter(filterValues.toArray(new java.lang.String[0]));
de.danoeh.antennapod.storage.preferences.UserPreferences.setSubscriptionsFilter(subscriptionsFilter);
org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.UnreadItemsUpdateEvent());
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
