package de.danoeh.antennapod.dialog;
import java.util.Locale;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import com.google.android.material.chip.Chip;
import de.danoeh.antennapod.view.PlaybackSpeedSeekBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.event.playback.SpeedChangedEvent;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.core.util.playback.PlaybackController;
import java.util.List;
import java.util.Collections;
import java.text.DecimalFormatSymbols;
import android.os.Bundle;
import android.view.ViewGroup;
import android.os.Handler;
import de.danoeh.antennapod.view.ItemOffsetDecoration;
import android.view.View;
import android.os.Looper;
import android.view.LayoutInflater;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VariableSpeedDialog extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    static final int MUID_STATIC = getMUID();
    private de.danoeh.antennapod.dialog.VariableSpeedDialog.SpeedSelectionAdapter adapter;

    private de.danoeh.antennapod.core.util.playback.PlaybackController controller;

    private final java.util.List<java.lang.Float> selectedSpeeds;

    private de.danoeh.antennapod.view.PlaybackSpeedSeekBar speedSeekBar;

    private com.google.android.material.chip.Chip addCurrentSpeedChip;

    public VariableSpeedDialog() {
        java.text.DecimalFormatSymbols format;
        format = new java.text.DecimalFormatSymbols(java.util.Locale.US);
        format.setDecimalSeparator('.');
        selectedSpeeds = new java.util.ArrayList<>(de.danoeh.antennapod.storage.preferences.UserPreferences.getPlaybackSpeedArray());
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        controller = new de.danoeh.antennapod.core.util.playback.PlaybackController(getActivity()) {
            @java.lang.Override
            public void loadMediaInfo() {
                updateSpeed(new de.danoeh.antennapod.event.playback.SpeedChangedEvent(controller.getCurrentPlaybackSpeedMultiplier()));
            }

        };
        controller.init();
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
        updateSpeed(new de.danoeh.antennapod.event.playback.SpeedChangedEvent(controller.getCurrentPlaybackSpeedMultiplier()));
    }


    @java.lang.Override
    public void onStop() {
        super.onStop();
        controller.release();
        controller = null;
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void updateSpeed(de.danoeh.antennapod.event.playback.SpeedChangedEvent event) {
        speedSeekBar.updateSpeed(event.getNewSpeed());
        addCurrentSpeedChip.setText(java.lang.String.format(java.util.Locale.getDefault(), "%1$.2f", event.getNewSpeed()));
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        android.view.View root;
        root = android.view.View.inflate(getContext(), de.danoeh.antennapod.R.layout.speed_select_dialog, null);
        switch(MUID_STATIC) {
            // VariableSpeedDialog_0_InvalidViewFocusOperatorMutator
            case 50: {
                /**
                * Inserted by Kadabra
                */
                speedSeekBar = root.findViewById(de.danoeh.antennapod.R.id.speed_seek_bar);
                speedSeekBar.requestFocus();
                break;
            }
            // VariableSpeedDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1050: {
                /**
                * Inserted by Kadabra
                */
                speedSeekBar = root.findViewById(de.danoeh.antennapod.R.id.speed_seek_bar);
                speedSeekBar.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            speedSeekBar = root.findViewById(de.danoeh.antennapod.R.id.speed_seek_bar);
            break;
        }
    }
    speedSeekBar.setProgressChangedListener((java.lang.Float multiplier) -> {
        if (controller != null) {
            controller.setPlaybackSpeed(multiplier);
        }
    });
    androidx.recyclerview.widget.RecyclerView selectedSpeedsGrid;
    switch(MUID_STATIC) {
        // VariableSpeedDialog_2_InvalidViewFocusOperatorMutator
        case 2050: {
            /**
            * Inserted by Kadabra
            */
            selectedSpeedsGrid = root.findViewById(de.danoeh.antennapod.R.id.selected_speeds_grid);
            selectedSpeedsGrid.requestFocus();
            break;
        }
        // VariableSpeedDialog_3_ViewComponentNotVisibleOperatorMutator
        case 3050: {
            /**
            * Inserted by Kadabra
            */
            selectedSpeedsGrid = root.findViewById(de.danoeh.antennapod.R.id.selected_speeds_grid);
            selectedSpeedsGrid.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        selectedSpeedsGrid = root.findViewById(de.danoeh.antennapod.R.id.selected_speeds_grid);
        break;
    }
}
selectedSpeedsGrid.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(getContext(), 3));
selectedSpeedsGrid.addItemDecoration(new de.danoeh.antennapod.view.ItemOffsetDecoration(getContext(), 4));
adapter = new de.danoeh.antennapod.dialog.VariableSpeedDialog.SpeedSelectionAdapter();
adapter.setHasStableIds(true);
selectedSpeedsGrid.setAdapter(adapter);
switch(MUID_STATIC) {
    // VariableSpeedDialog_4_InvalidViewFocusOperatorMutator
    case 4050: {
        /**
        * Inserted by Kadabra
        */
        addCurrentSpeedChip = root.findViewById(de.danoeh.antennapod.R.id.add_current_speed_chip);
        addCurrentSpeedChip.requestFocus();
        break;
    }
    // VariableSpeedDialog_5_ViewComponentNotVisibleOperatorMutator
    case 5050: {
        /**
        * Inserted by Kadabra
        */
        addCurrentSpeedChip = root.findViewById(de.danoeh.antennapod.R.id.add_current_speed_chip);
        addCurrentSpeedChip.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    addCurrentSpeedChip = root.findViewById(de.danoeh.antennapod.R.id.add_current_speed_chip);
    break;
}
}
addCurrentSpeedChip.setCloseIconVisible(true);
addCurrentSpeedChip.setCloseIconResource(de.danoeh.antennapod.R.drawable.ic_add);
switch(MUID_STATIC) {
// VariableSpeedDialog_6_BuggyGUIListenerOperatorMutator
case 6050: {
    addCurrentSpeedChip.setOnCloseIconClickListener(null);
    break;
}
default: {
addCurrentSpeedChip.setOnCloseIconClickListener((android.view.View v) -> addCurrentSpeed());
break;
}
}
addCurrentSpeedChip.setCloseIconContentDescription(getString(de.danoeh.antennapod.R.string.add_preset));
switch(MUID_STATIC) {
// VariableSpeedDialog_7_BuggyGUIListenerOperatorMutator
case 7050: {
addCurrentSpeedChip.setOnClickListener(null);
break;
}
default: {
addCurrentSpeedChip.setOnClickListener((android.view.View v) -> addCurrentSpeed());
break;
}
}
return root;
}


private void addCurrentSpeed() {
float newSpeed;
newSpeed = speedSeekBar.getCurrentSpeed();
if (selectedSpeeds.contains(newSpeed)) {
com.google.android.material.snackbar.Snackbar.make(addCurrentSpeedChip, getString(de.danoeh.antennapod.R.string.preset_already_exists, newSpeed), com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
} else {
selectedSpeeds.add(newSpeed);
java.util.Collections.sort(selectedSpeeds);
de.danoeh.antennapod.storage.preferences.UserPreferences.setPlaybackSpeedArray(selectedSpeeds);
adapter.notifyDataSetChanged();
}
}


public class SpeedSelectionAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<de.danoeh.antennapod.dialog.VariableSpeedDialog.SpeedSelectionAdapter.ViewHolder> {
@java.lang.Override
@androidx.annotation.NonNull
public de.danoeh.antennapod.dialog.VariableSpeedDialog.SpeedSelectionAdapter.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull
android.view.ViewGroup parent, int viewType) {
com.google.android.material.chip.Chip chip;
chip = new com.google.android.material.chip.Chip(getContext());
chip.setTextAlignment(android.view.View.TEXT_ALIGNMENT_CENTER);
return new de.danoeh.antennapod.dialog.VariableSpeedDialog.SpeedSelectionAdapter.ViewHolder(chip);
}


@java.lang.Override
public void onBindViewHolder(@androidx.annotation.NonNull
de.danoeh.antennapod.dialog.VariableSpeedDialog.SpeedSelectionAdapter.ViewHolder holder, int position) {
float speed;
speed = selectedSpeeds.get(position);
holder.chip.setText(java.lang.String.format(java.util.Locale.getDefault(), "%1$.2f", speed));
holder.chip.setOnLongClickListener((android.view.View v) -> {
selectedSpeeds.remove(speed);
de.danoeh.antennapod.storage.preferences.UserPreferences.setPlaybackSpeedArray(selectedSpeeds);
notifyDataSetChanged();
return true;
});
switch(MUID_STATIC) {
// VariableSpeedDialog_8_BuggyGUIListenerOperatorMutator
case 8050: {
holder.chip.setOnClickListener(null);
break;
}
default: {
holder.chip.setOnClickListener((android.view.View v) -> {
new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
    if (controller != null) {
        dismiss();
        controller.setPlaybackSpeed(speed);
    }
}, 200);
});
break;
}
}
}


@java.lang.Override
public int getItemCount() {
return selectedSpeeds.size();
}


@java.lang.Override
public long getItemId(int position) {
return selectedSpeeds.get(position).hashCode();
}


public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
com.google.android.material.chip.Chip chip;

ViewHolder(com.google.android.material.chip.Chip itemView) {
super(itemView);
chip = itemView;
}

}
}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
