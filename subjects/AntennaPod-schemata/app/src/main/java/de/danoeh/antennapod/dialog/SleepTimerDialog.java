package de.danoeh.antennapod.dialog;
import java.util.Locale;
import de.danoeh.antennapod.event.playback.SleepTimerUpdatedEvent;
import org.greenrobot.eventbus.ThreadMode;
import androidx.fragment.app.DialogFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import android.view.inputmethod.InputMethodManager;
import de.danoeh.antennapod.core.service.playback.PlaybackService;
import de.danoeh.antennapod.R;
import android.widget.Button;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.app.Dialog;
import de.danoeh.antennapod.core.util.Converter;
import de.danoeh.antennapod.core.util.playback.PlaybackController;
import android.widget.ImageView;
import de.danoeh.antennapod.core.preferences.SleepTimerPreferences;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.os.Bundle;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.text.format.DateFormat;
import com.google.android.material.snackbar.Snackbar;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SleepTimerDialog extends androidx.fragment.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    private de.danoeh.antennapod.core.util.playback.PlaybackController controller;

    private android.widget.EditText etxtTime;

    private android.widget.LinearLayout timeSetup;

    private android.widget.LinearLayout timeDisplay;

    private android.widget.TextView time;

    private android.widget.CheckBox chAutoEnable;

    public SleepTimerDialog() {
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        controller = new de.danoeh.antennapod.core.util.playback.PlaybackController(getActivity()) {
            @java.lang.Override
            public void loadMediaInfo() {
            }

        };
        controller.init();
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
    }


    @java.lang.Override
    public void onStop() {
        super.onStop();
        if (controller != null) {
            controller.release();
        }
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        android.view.View content;
        content = android.view.View.inflate(getContext(), de.danoeh.antennapod.R.layout.time_dialog, null);
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
        builder.setTitle(de.danoeh.antennapod.R.string.sleep_timer_label);
        builder.setView(content);
        builder.setPositiveButton(de.danoeh.antennapod.R.string.close_label, null);
        switch(MUID_STATIC) {
            // SleepTimerDialog_0_InvalidViewFocusOperatorMutator
            case 47: {
                /**
                * Inserted by Kadabra
                */
                etxtTime = content.findViewById(de.danoeh.antennapod.R.id.etxtTime);
                etxtTime.requestFocus();
                break;
            }
            // SleepTimerDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1047: {
                /**
                * Inserted by Kadabra
                */
                etxtTime = content.findViewById(de.danoeh.antennapod.R.id.etxtTime);
                etxtTime.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            etxtTime = content.findViewById(de.danoeh.antennapod.R.id.etxtTime);
            break;
        }
    }
    switch(MUID_STATIC) {
        // SleepTimerDialog_2_InvalidViewFocusOperatorMutator
        case 2047: {
            /**
            * Inserted by Kadabra
            */
            timeSetup = content.findViewById(de.danoeh.antennapod.R.id.timeSetup);
            timeSetup.requestFocus();
            break;
        }
        // SleepTimerDialog_3_ViewComponentNotVisibleOperatorMutator
        case 3047: {
            /**
            * Inserted by Kadabra
            */
            timeSetup = content.findViewById(de.danoeh.antennapod.R.id.timeSetup);
            timeSetup.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        timeSetup = content.findViewById(de.danoeh.antennapod.R.id.timeSetup);
        break;
    }
}
switch(MUID_STATIC) {
    // SleepTimerDialog_4_InvalidViewFocusOperatorMutator
    case 4047: {
        /**
        * Inserted by Kadabra
        */
        timeDisplay = content.findViewById(de.danoeh.antennapod.R.id.timeDisplay);
        timeDisplay.requestFocus();
        break;
    }
    // SleepTimerDialog_5_ViewComponentNotVisibleOperatorMutator
    case 5047: {
        /**
        * Inserted by Kadabra
        */
        timeDisplay = content.findViewById(de.danoeh.antennapod.R.id.timeDisplay);
        timeDisplay.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    timeDisplay = content.findViewById(de.danoeh.antennapod.R.id.timeDisplay);
    break;
}
}
timeDisplay.setVisibility(android.view.View.GONE);
switch(MUID_STATIC) {
// SleepTimerDialog_6_InvalidViewFocusOperatorMutator
case 6047: {
    /**
    * Inserted by Kadabra
    */
    time = content.findViewById(de.danoeh.antennapod.R.id.time);
    time.requestFocus();
    break;
}
// SleepTimerDialog_7_ViewComponentNotVisibleOperatorMutator
case 7047: {
    /**
    * Inserted by Kadabra
    */
    time = content.findViewById(de.danoeh.antennapod.R.id.time);
    time.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
time = content.findViewById(de.danoeh.antennapod.R.id.time);
break;
}
}
android.widget.Button extendSleepFiveMinutesButton;
switch(MUID_STATIC) {
// SleepTimerDialog_8_InvalidViewFocusOperatorMutator
case 8047: {
/**
* Inserted by Kadabra
*/
extendSleepFiveMinutesButton = content.findViewById(de.danoeh.antennapod.R.id.extendSleepFiveMinutesButton);
extendSleepFiveMinutesButton.requestFocus();
break;
}
// SleepTimerDialog_9_ViewComponentNotVisibleOperatorMutator
case 9047: {
/**
* Inserted by Kadabra
*/
extendSleepFiveMinutesButton = content.findViewById(de.danoeh.antennapod.R.id.extendSleepFiveMinutesButton);
extendSleepFiveMinutesButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
extendSleepFiveMinutesButton = content.findViewById(de.danoeh.antennapod.R.id.extendSleepFiveMinutesButton);
break;
}
}
extendSleepFiveMinutesButton.setText(getString(de.danoeh.antennapod.R.string.extend_sleep_timer_label, 5));
android.widget.Button extendSleepTenMinutesButton;
switch(MUID_STATIC) {
// SleepTimerDialog_10_InvalidViewFocusOperatorMutator
case 10047: {
/**
* Inserted by Kadabra
*/
extendSleepTenMinutesButton = content.findViewById(de.danoeh.antennapod.R.id.extendSleepTenMinutesButton);
extendSleepTenMinutesButton.requestFocus();
break;
}
// SleepTimerDialog_11_ViewComponentNotVisibleOperatorMutator
case 11047: {
/**
* Inserted by Kadabra
*/
extendSleepTenMinutesButton = content.findViewById(de.danoeh.antennapod.R.id.extendSleepTenMinutesButton);
extendSleepTenMinutesButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
extendSleepTenMinutesButton = content.findViewById(de.danoeh.antennapod.R.id.extendSleepTenMinutesButton);
break;
}
}
extendSleepTenMinutesButton.setText(getString(de.danoeh.antennapod.R.string.extend_sleep_timer_label, 10));
android.widget.Button extendSleepTwentyMinutesButton;
switch(MUID_STATIC) {
// SleepTimerDialog_12_InvalidViewFocusOperatorMutator
case 12047: {
/**
* Inserted by Kadabra
*/
extendSleepTwentyMinutesButton = content.findViewById(de.danoeh.antennapod.R.id.extendSleepTwentyMinutesButton);
extendSleepTwentyMinutesButton.requestFocus();
break;
}
// SleepTimerDialog_13_ViewComponentNotVisibleOperatorMutator
case 13047: {
/**
* Inserted by Kadabra
*/
extendSleepTwentyMinutesButton = content.findViewById(de.danoeh.antennapod.R.id.extendSleepTwentyMinutesButton);
extendSleepTwentyMinutesButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
extendSleepTwentyMinutesButton = content.findViewById(de.danoeh.antennapod.R.id.extendSleepTwentyMinutesButton);
break;
}
}
extendSleepTwentyMinutesButton.setText(getString(de.danoeh.antennapod.R.string.extend_sleep_timer_label, 20));
switch(MUID_STATIC) {
// SleepTimerDialog_14_BuggyGUIListenerOperatorMutator
case 14047: {
extendSleepFiveMinutesButton.setOnClickListener(null);
break;
}
default: {
extendSleepFiveMinutesButton.setOnClickListener((android.view.View v) -> {
if (controller != null) {
switch(MUID_STATIC) {
// SleepTimerDialog_15_BinaryMutator
case 15047: {
controller.extendSleepTimer((5 * 1000) / 60);
break;
}
default: {
switch(MUID_STATIC) {
// SleepTimerDialog_16_BinaryMutator
case 16047: {
    controller.extendSleepTimer((5 / 1000) * 60);
    break;
}
default: {
controller.extendSleepTimer((5 * 1000) * 60);
break;
}
}
break;
}
}
}
});
break;
}
}
switch(MUID_STATIC) {
// SleepTimerDialog_17_BuggyGUIListenerOperatorMutator
case 17047: {
extendSleepTenMinutesButton.setOnClickListener(null);
break;
}
default: {
extendSleepTenMinutesButton.setOnClickListener((android.view.View v) -> {
if (controller != null) {
switch(MUID_STATIC) {
// SleepTimerDialog_18_BinaryMutator
case 18047: {
controller.extendSleepTimer((10 * 1000) / 60);
break;
}
default: {
switch(MUID_STATIC) {
// SleepTimerDialog_19_BinaryMutator
case 19047: {
controller.extendSleepTimer((10 / 1000) * 60);
break;
}
default: {
controller.extendSleepTimer((10 * 1000) * 60);
break;
}
}
break;
}
}
}
});
break;
}
}
switch(MUID_STATIC) {
// SleepTimerDialog_20_BuggyGUIListenerOperatorMutator
case 20047: {
extendSleepTwentyMinutesButton.setOnClickListener(null);
break;
}
default: {
extendSleepTwentyMinutesButton.setOnClickListener((android.view.View v) -> {
if (controller != null) {
switch(MUID_STATIC) {
// SleepTimerDialog_21_BinaryMutator
case 21047: {
controller.extendSleepTimer((20 * 1000) / 60);
break;
}
default: {
switch(MUID_STATIC) {
// SleepTimerDialog_22_BinaryMutator
case 22047: {
controller.extendSleepTimer((20 / 1000) * 60);
break;
}
default: {
controller.extendSleepTimer((20 * 1000) * 60);
break;
}
}
break;
}
}
}
});
break;
}
}
etxtTime.setText(de.danoeh.antennapod.core.preferences.SleepTimerPreferences.lastTimerValue());
etxtTime.postDelayed(() -> {
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
imm.showSoftInput(etxtTime, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
}, 100);
final android.widget.CheckBox cbShakeToReset;
switch(MUID_STATIC) {
// SleepTimerDialog_23_InvalidViewFocusOperatorMutator
case 23047: {
/**
* Inserted by Kadabra
*/
cbShakeToReset = content.findViewById(de.danoeh.antennapod.R.id.cbShakeToReset);
cbShakeToReset.requestFocus();
break;
}
// SleepTimerDialog_24_ViewComponentNotVisibleOperatorMutator
case 24047: {
/**
* Inserted by Kadabra
*/
cbShakeToReset = content.findViewById(de.danoeh.antennapod.R.id.cbShakeToReset);
cbShakeToReset.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
cbShakeToReset = content.findViewById(de.danoeh.antennapod.R.id.cbShakeToReset);
break;
}
}
final android.widget.CheckBox cbVibrate;
switch(MUID_STATIC) {
// SleepTimerDialog_25_InvalidViewFocusOperatorMutator
case 25047: {
/**
* Inserted by Kadabra
*/
cbVibrate = content.findViewById(de.danoeh.antennapod.R.id.cbVibrate);
cbVibrate.requestFocus();
break;
}
// SleepTimerDialog_26_ViewComponentNotVisibleOperatorMutator
case 26047: {
/**
* Inserted by Kadabra
*/
cbVibrate = content.findViewById(de.danoeh.antennapod.R.id.cbVibrate);
cbVibrate.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
cbVibrate = content.findViewById(de.danoeh.antennapod.R.id.cbVibrate);
break;
}
}
switch(MUID_STATIC) {
// SleepTimerDialog_27_InvalidViewFocusOperatorMutator
case 27047: {
/**
* Inserted by Kadabra
*/
chAutoEnable = content.findViewById(de.danoeh.antennapod.R.id.chAutoEnable);
chAutoEnable.requestFocus();
break;
}
// SleepTimerDialog_28_ViewComponentNotVisibleOperatorMutator
case 28047: {
/**
* Inserted by Kadabra
*/
chAutoEnable = content.findViewById(de.danoeh.antennapod.R.id.chAutoEnable);
chAutoEnable.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
chAutoEnable = content.findViewById(de.danoeh.antennapod.R.id.chAutoEnable);
break;
}
}
final android.widget.ImageView changeTimesButton;
switch(MUID_STATIC) {
// SleepTimerDialog_29_InvalidViewFocusOperatorMutator
case 29047: {
/**
* Inserted by Kadabra
*/
changeTimesButton = content.findViewById(de.danoeh.antennapod.R.id.changeTimesButton);
changeTimesButton.requestFocus();
break;
}
// SleepTimerDialog_30_ViewComponentNotVisibleOperatorMutator
case 30047: {
/**
* Inserted by Kadabra
*/
changeTimesButton = content.findViewById(de.danoeh.antennapod.R.id.changeTimesButton);
changeTimesButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
changeTimesButton = content.findViewById(de.danoeh.antennapod.R.id.changeTimesButton);
break;
}
}
cbShakeToReset.setChecked(de.danoeh.antennapod.core.preferences.SleepTimerPreferences.shakeToReset());
cbVibrate.setChecked(de.danoeh.antennapod.core.preferences.SleepTimerPreferences.vibrate());
chAutoEnable.setChecked(de.danoeh.antennapod.core.preferences.SleepTimerPreferences.autoEnable());
changeTimesButton.setEnabled(chAutoEnable.isChecked());
changeTimesButton.setAlpha(chAutoEnable.isChecked() ? 1.0F : 0.5F);
cbShakeToReset.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> de.danoeh.antennapod.core.preferences.SleepTimerPreferences.setShakeToReset(isChecked));
cbVibrate.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> de.danoeh.antennapod.core.preferences.SleepTimerPreferences.setVibrate(isChecked));
chAutoEnable.setOnCheckedChangeListener((android.widget.CompoundButton compoundButton,boolean isChecked) -> {
de.danoeh.antennapod.core.preferences.SleepTimerPreferences.setAutoEnable(isChecked);
changeTimesButton.setEnabled(isChecked);
changeTimesButton.setAlpha(isChecked ? 1.0F : 0.5F);
});
updateAutoEnableText();
switch(MUID_STATIC) {
// SleepTimerDialog_31_BuggyGUIListenerOperatorMutator
case 31047: {
changeTimesButton.setOnClickListener(null);
break;
}
default: {
changeTimesButton.setOnClickListener((android.view.View changeTimesBtn) -> {
int from;
from = de.danoeh.antennapod.core.preferences.SleepTimerPreferences.autoEnableFrom();
int to;
to = de.danoeh.antennapod.core.preferences.SleepTimerPreferences.autoEnableTo();
showTimeRangeDialog(getContext(), from, to);
});
break;
}
}
android.widget.Button disableButton;
switch(MUID_STATIC) {
// SleepTimerDialog_32_InvalidViewFocusOperatorMutator
case 32047: {
/**
* Inserted by Kadabra
*/
disableButton = content.findViewById(de.danoeh.antennapod.R.id.disableSleeptimerButton);
disableButton.requestFocus();
break;
}
// SleepTimerDialog_33_ViewComponentNotVisibleOperatorMutator
case 33047: {
/**
* Inserted by Kadabra
*/
disableButton = content.findViewById(de.danoeh.antennapod.R.id.disableSleeptimerButton);
disableButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
disableButton = content.findViewById(de.danoeh.antennapod.R.id.disableSleeptimerButton);
break;
}
}
switch(MUID_STATIC) {
// SleepTimerDialog_34_BuggyGUIListenerOperatorMutator
case 34047: {
disableButton.setOnClickListener(null);
break;
}
default: {
disableButton.setOnClickListener((android.view.View v) -> {
if (controller != null) {
controller.disableSleepTimer();
}
});
break;
}
}
android.widget.Button setButton;
switch(MUID_STATIC) {
// SleepTimerDialog_35_InvalidViewFocusOperatorMutator
case 35047: {
/**
* Inserted by Kadabra
*/
setButton = content.findViewById(de.danoeh.antennapod.R.id.setSleeptimerButton);
setButton.requestFocus();
break;
}
// SleepTimerDialog_36_ViewComponentNotVisibleOperatorMutator
case 36047: {
/**
* Inserted by Kadabra
*/
setButton = content.findViewById(de.danoeh.antennapod.R.id.setSleeptimerButton);
setButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
setButton = content.findViewById(de.danoeh.antennapod.R.id.setSleeptimerButton);
break;
}
}
switch(MUID_STATIC) {
// SleepTimerDialog_37_BuggyGUIListenerOperatorMutator
case 37047: {
setButton.setOnClickListener(null);
break;
}
default: {
setButton.setOnClickListener((android.view.View v) -> {
if (!de.danoeh.antennapod.core.service.playback.PlaybackService.isRunning) {
com.google.android.material.snackbar.Snackbar.make(content, de.danoeh.antennapod.R.string.no_media_playing_label, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
return;
}
try {
long time;
time = java.lang.Long.parseLong(etxtTime.getText().toString());
if (time == 0) {
throw new java.lang.NumberFormatException("Timer must not be zero");
}
de.danoeh.antennapod.core.preferences.SleepTimerPreferences.setLastTimer(etxtTime.getText().toString());
if (controller != null) {
controller.setSleepTimer(de.danoeh.antennapod.core.preferences.SleepTimerPreferences.timerMillis());
}
closeKeyboard(content);
} catch (java.lang.NumberFormatException e) {
e.printStackTrace();
com.google.android.material.snackbar.Snackbar.make(content, de.danoeh.antennapod.R.string.time_dialog_invalid_input, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
}
});
break;
}
}
return builder.create();
}


private void showTimeRangeDialog(android.content.Context context, int from, int to) {
de.danoeh.antennapod.dialog.TimeRangeDialog dialog;
dialog = new de.danoeh.antennapod.dialog.TimeRangeDialog(context, from, to);
dialog.setOnDismissListener((android.content.DialogInterface v) -> {
de.danoeh.antennapod.core.preferences.SleepTimerPreferences.setAutoEnableFrom(dialog.getFrom());
de.danoeh.antennapod.core.preferences.SleepTimerPreferences.setAutoEnableTo(dialog.getTo());
updateAutoEnableText();
});
dialog.show();
}


private void updateAutoEnableText() {
java.lang.String text;
int from;
from = de.danoeh.antennapod.core.preferences.SleepTimerPreferences.autoEnableFrom();
int to;
to = de.danoeh.antennapod.core.preferences.SleepTimerPreferences.autoEnableTo();
if (from == to) {
text = getString(de.danoeh.antennapod.R.string.auto_enable_label);
} else if (android.text.format.DateFormat.is24HourFormat(getContext())) {
java.lang.String formattedFrom;
formattedFrom = java.lang.String.format(java.util.Locale.getDefault(), "%02d:00", from);
java.lang.String formattedTo;
formattedTo = java.lang.String.format(java.util.Locale.getDefault(), "%02d:00", to);
text = getString(de.danoeh.antennapod.R.string.auto_enable_label_with_times, formattedFrom, formattedTo);
} else {
java.lang.String formattedFrom;
formattedFrom = java.lang.String.format(java.util.Locale.getDefault(), "%02d:00 %s", from % 12, from >= 12 ? "PM" : "AM");
java.lang.String formattedTo;
formattedTo = java.lang.String.format(java.util.Locale.getDefault(), "%02d:00 %s", to % 12, to >= 12 ? "PM" : "AM");
text = getString(de.danoeh.antennapod.R.string.auto_enable_label_with_times, formattedFrom, formattedTo);
}
chAutoEnable.setText(text);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
@java.lang.SuppressWarnings("unused")
public void timerUpdated(de.danoeh.antennapod.event.playback.SleepTimerUpdatedEvent event) {
timeDisplay.setVisibility(event.isOver() || event.isCancelled() ? android.view.View.GONE : android.view.View.VISIBLE);
timeSetup.setVisibility(event.isOver() || event.isCancelled() ? android.view.View.VISIBLE : android.view.View.GONE);
time.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(((int) (event.getTimeLeft()))));
}


private void closeKeyboard(android.view.View content) {
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (getContext().getSystemService(android.app.Activity.INPUT_METHOD_SERVICE)));
imm.hideSoftInputFromWindow(content.getWindowToken(), 0);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
