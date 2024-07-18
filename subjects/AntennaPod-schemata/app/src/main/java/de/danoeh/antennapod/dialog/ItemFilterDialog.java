package de.danoeh.antennapod.dialog;
import android.widget.LinearLayout;
import java.util.Set;
import de.danoeh.antennapod.core.feed.FeedItemFilterGroup;
import com.google.android.material.button.MaterialButtonToggleGroup;
import android.os.Bundle;
import android.view.ViewGroup;
import android.text.TextUtils;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import android.widget.Button;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.widget.FrameLayout;
import de.danoeh.antennapod.databinding.FilterDialogRowBinding;
import androidx.annotation.Nullable;
import java.util.HashSet;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class ItemFilterDialog extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    static final int MUID_STATIC = getMUID();
    protected static final java.lang.String ARGUMENT_FILTER = "filter";

    private android.widget.LinearLayout rows;

    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        android.view.View layout;
        layout = inflater.inflate(de.danoeh.antennapod.R.layout.filter_dialog, null, false);
        switch(MUID_STATIC) {
            // ItemFilterDialog_0_InvalidViewFocusOperatorMutator
            case 66: {
                /**
                * Inserted by Kadabra
                */
                rows = layout.findViewById(de.danoeh.antennapod.R.id.filter_rows);
                rows.requestFocus();
                break;
            }
            // ItemFilterDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1066: {
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
    de.danoeh.antennapod.model.feed.FeedItemFilter filter;
    filter = ((de.danoeh.antennapod.model.feed.FeedItemFilter) (getArguments().getSerializable(de.danoeh.antennapod.dialog.ItemFilterDialog.ARGUMENT_FILTER)));
    for (de.danoeh.antennapod.core.feed.FeedItemFilterGroup item : de.danoeh.antennapod.core.feed.FeedItemFilterGroup.values()) {
        de.danoeh.antennapod.databinding.FilterDialogRowBinding binding;
        binding = de.danoeh.antennapod.databinding.FilterDialogRowBinding.inflate(inflater);
        binding.getRoot().addOnButtonCheckedListener((com.google.android.material.button.MaterialButtonToggleGroup group,int checkedId,boolean isChecked) -> onFilterChanged(getNewFilterValues()));
        binding.filterButton1.setText(item.values[0].displayName);
        binding.filterButton1.setTag(item.values[0].filterId);
        binding.filterButton2.setText(item.values[1].displayName);
        binding.filterButton2.setTag(item.values[1].filterId);
        binding.filterButton1.setMaxLines(3);
        binding.filterButton1.setSingleLine(false);
        binding.filterButton2.setMaxLines(3);
        binding.filterButton2.setSingleLine(false);
        rows.addView(binding.getRoot());
    }
    for (java.lang.String filterId : filter.getValues()) {
        if (!android.text.TextUtils.isEmpty(filterId)) {
            android.widget.Button button;
            button = layout.findViewWithTag(filterId);
            if (button != null) {
                ((com.google.android.material.button.MaterialButtonToggleGroup) (button.getParent())).check(button.getId());
            }
        }
    }
    return layout;
}


@androidx.annotation.NonNull
@java.lang.Override
public android.app.Dialog onCreateDialog(@androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    android.app.Dialog dialog;
    dialog = super.onCreateDialog(savedInstanceState);
    dialog.setOnShowListener((android.content.DialogInterface dialogInterface) -> {
        com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog;
        bottomSheetDialog = ((com.google.android.material.bottomsheet.BottomSheetDialog) (dialogInterface));
        setupFullHeight(bottomSheetDialog);
    });
    return dialog;
}


private void setupFullHeight(com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog) {
    android.widget.FrameLayout bottomSheet;
    switch(MUID_STATIC) {
        // ItemFilterDialog_2_InvalidViewFocusOperatorMutator
        case 2066: {
            /**
            * Inserted by Kadabra
            */
            bottomSheet = ((android.widget.FrameLayout) (bottomSheetDialog.findViewById(de.danoeh.antennapod.R.id.design_bottom_sheet)));
            bottomSheet.requestFocus();
            break;
        }
        // ItemFilterDialog_3_ViewComponentNotVisibleOperatorMutator
        case 3066: {
            /**
            * Inserted by Kadabra
            */
            bottomSheet = ((android.widget.FrameLayout) (bottomSheetDialog.findViewById(de.danoeh.antennapod.R.id.design_bottom_sheet)));
            bottomSheet.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        bottomSheet = ((android.widget.FrameLayout) (bottomSheetDialog.findViewById(de.danoeh.antennapod.R.id.design_bottom_sheet)));
        break;
    }
}
if (bottomSheet != null) {
    com.google.android.material.bottomsheet.BottomSheetBehavior<android.widget.FrameLayout> behavior;
    behavior = com.google.android.material.bottomsheet.BottomSheetBehavior.from(bottomSheet);
    android.view.ViewGroup.LayoutParams layoutParams;
    layoutParams = bottomSheet.getLayoutParams();
    bottomSheet.setLayoutParams(layoutParams);
    behavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
}
}


protected java.util.Set<java.lang.String> getNewFilterValues() {
final java.util.Set<java.lang.String> newFilterValues;
newFilterValues = new java.util.HashSet<>();
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
    newFilterValues.add(tag);
}
return newFilterValues;
}


abstract void onFilterChanged(java.util.Set<java.lang.String> newFilterValues);


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
