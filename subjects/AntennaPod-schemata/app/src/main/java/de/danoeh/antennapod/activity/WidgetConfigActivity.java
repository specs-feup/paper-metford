package de.danoeh.antennapod.activity;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.widget.SeekBar;
import de.danoeh.antennapod.core.preferences.ThemeSwitcher;
import de.danoeh.antennapod.core.widget.WidgetUpdaterWorker;
import android.os.Bundle;
import android.appwidget.AppWidgetManager;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import de.danoeh.antennapod.core.receiver.PlayerWidget;
import de.danoeh.antennapod.R;
import android.os.Build;
import android.widget.TextView;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class WidgetConfigActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    private int appWidgetId = android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

    private android.widget.SeekBar opacitySeekBar;

    private android.widget.TextView opacityTextView;

    private android.view.View widgetPreview;

    private android.widget.CheckBox ckPlaybackSpeed;

    private android.widget.CheckBox ckRewind;

    private android.widget.CheckBox ckFastForward;

    private android.widget.CheckBox ckSkip;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        setTheme(de.danoeh.antennapod.core.preferences.ThemeSwitcher.getTheme(this));
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // WidgetConfigActivity_0_LengthyGUICreationOperatorMutator
            case 151: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    setContentView(de.danoeh.antennapod.R.layout.activity_widget_config);
    android.content.Intent configIntent;
    switch(MUID_STATIC) {
        // WidgetConfigActivity_1_RandomActionIntentDefinitionOperatorMutator
        case 1151: {
            configIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        configIntent = getIntent();
        break;
    }
}
android.os.Bundle extras;
extras = configIntent.getExtras();
if (extras != null) {
    appWidgetId = extras.getInt(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID);
}
android.content.Intent resultValue;
switch(MUID_STATIC) {
    // WidgetConfigActivity_2_NullIntentOperatorMutator
    case 2151: {
        resultValue = null;
        break;
    }
    // WidgetConfigActivity_3_RandomActionIntentDefinitionOperatorMutator
    case 3151: {
        resultValue = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    resultValue = new android.content.Intent();
    break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_4_NullValueIntentPutExtraOperatorMutator
case 4151: {
    resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, new Parcelable[0]);
    break;
}
// WidgetConfigActivity_5_IntentPayloadReplacementOperatorMutator
case 5151: {
    resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
    break;
}
default: {
switch(MUID_STATIC) {
    // WidgetConfigActivity_6_RandomActionIntentDefinitionOperatorMutator
    case 6151: {
        /**
        * Inserted by Kadabra
        */
        /**
        * Inserted by Kadabra
        */
        new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
    break;
}
}
break;
}
}
setResult(android.app.Activity.RESULT_CANCELED, resultValue);
if (appWidgetId == android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID) {
finish();
}
switch(MUID_STATIC) {
// WidgetConfigActivity_7_FindViewByIdReturnsNullOperatorMutator
case 7151: {
opacityTextView = null;
break;
}
// WidgetConfigActivity_8_InvalidIDFindViewOperatorMutator
case 8151: {
opacityTextView = findViewById(732221);
break;
}
// WidgetConfigActivity_9_InvalidViewFocusOperatorMutator
case 9151: {
/**
* Inserted by Kadabra
*/
opacityTextView = findViewById(de.danoeh.antennapod.R.id.widget_opacity_textView);
opacityTextView.requestFocus();
break;
}
// WidgetConfigActivity_10_ViewComponentNotVisibleOperatorMutator
case 10151: {
/**
* Inserted by Kadabra
*/
opacityTextView = findViewById(de.danoeh.antennapod.R.id.widget_opacity_textView);
opacityTextView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
opacityTextView = findViewById(de.danoeh.antennapod.R.id.widget_opacity_textView);
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_11_FindViewByIdReturnsNullOperatorMutator
case 11151: {
opacitySeekBar = null;
break;
}
// WidgetConfigActivity_12_InvalidIDFindViewOperatorMutator
case 12151: {
opacitySeekBar = findViewById(732221);
break;
}
// WidgetConfigActivity_13_InvalidViewFocusOperatorMutator
case 13151: {
/**
* Inserted by Kadabra
*/
opacitySeekBar = findViewById(de.danoeh.antennapod.R.id.widget_opacity_seekBar);
opacitySeekBar.requestFocus();
break;
}
// WidgetConfigActivity_14_ViewComponentNotVisibleOperatorMutator
case 14151: {
/**
* Inserted by Kadabra
*/
opacitySeekBar = findViewById(de.danoeh.antennapod.R.id.widget_opacity_seekBar);
opacitySeekBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
opacitySeekBar = findViewById(de.danoeh.antennapod.R.id.widget_opacity_seekBar);
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_15_FindViewByIdReturnsNullOperatorMutator
case 15151: {
widgetPreview = null;
break;
}
// WidgetConfigActivity_16_InvalidIDFindViewOperatorMutator
case 16151: {
widgetPreview = findViewById(732221);
break;
}
// WidgetConfigActivity_17_InvalidViewFocusOperatorMutator
case 17151: {
/**
* Inserted by Kadabra
*/
widgetPreview = findViewById(de.danoeh.antennapod.R.id.widgetLayout);
widgetPreview.requestFocus();
break;
}
// WidgetConfigActivity_18_ViewComponentNotVisibleOperatorMutator
case 18151: {
/**
* Inserted by Kadabra
*/
widgetPreview = findViewById(de.danoeh.antennapod.R.id.widgetLayout);
widgetPreview.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
widgetPreview = findViewById(de.danoeh.antennapod.R.id.widgetLayout);
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_19_InvalidIDFindViewOperatorMutator
case 19151: {
findViewById(732221).setOnClickListener((android.view.View v) -> confirmCreateWidget());
break;
}
default: {
switch(MUID_STATIC) {
// WidgetConfigActivity_20_BuggyGUIListenerOperatorMutator
case 20151: {
findViewById(de.danoeh.antennapod.R.id.butConfirm).setOnClickListener(null);
break;
}
default: {
findViewById(de.danoeh.antennapod.R.id.butConfirm).setOnClickListener((android.view.View v) -> confirmCreateWidget());
break;
}
}
break;
}
}
opacitySeekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
@java.lang.Override
public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean b) {
opacityTextView.setText(seekBar.getProgress() + "%");
int color;
color = getColorWithAlpha(de.danoeh.antennapod.core.receiver.PlayerWidget.DEFAULT_COLOR, opacitySeekBar.getProgress());
widgetPreview.setBackgroundColor(color);
}


@java.lang.Override
public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
}


@java.lang.Override
public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
}

});
widgetPreview.findViewById(de.danoeh.antennapod.R.id.txtNoPlaying).setVisibility(android.view.View.GONE);
android.widget.TextView title;
switch(MUID_STATIC) {
// WidgetConfigActivity_21_InvalidViewFocusOperatorMutator
case 21151: {
/**
* Inserted by Kadabra
*/
title = widgetPreview.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.requestFocus();
break;
}
// WidgetConfigActivity_22_ViewComponentNotVisibleOperatorMutator
case 22151: {
/**
* Inserted by Kadabra
*/
title = widgetPreview.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
title = widgetPreview.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
break;
}
}
title.setVisibility(android.view.View.VISIBLE);
title.setText(de.danoeh.antennapod.R.string.app_name);
android.widget.TextView progress;
switch(MUID_STATIC) {
// WidgetConfigActivity_23_InvalidViewFocusOperatorMutator
case 23151: {
/**
* Inserted by Kadabra
*/
progress = widgetPreview.findViewById(de.danoeh.antennapod.R.id.txtvProgress);
progress.requestFocus();
break;
}
// WidgetConfigActivity_24_ViewComponentNotVisibleOperatorMutator
case 24151: {
/**
* Inserted by Kadabra
*/
progress = widgetPreview.findViewById(de.danoeh.antennapod.R.id.txtvProgress);
progress.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progress = widgetPreview.findViewById(de.danoeh.antennapod.R.id.txtvProgress);
break;
}
}
progress.setVisibility(android.view.View.VISIBLE);
progress.setText(de.danoeh.antennapod.R.string.position_default_label);
switch(MUID_STATIC) {
// WidgetConfigActivity_25_FindViewByIdReturnsNullOperatorMutator
case 25151: {
ckPlaybackSpeed = null;
break;
}
// WidgetConfigActivity_26_InvalidIDFindViewOperatorMutator
case 26151: {
ckPlaybackSpeed = findViewById(732221);
break;
}
// WidgetConfigActivity_27_InvalidViewFocusOperatorMutator
case 27151: {
/**
* Inserted by Kadabra
*/
ckPlaybackSpeed = findViewById(de.danoeh.antennapod.R.id.ckPlaybackSpeed);
ckPlaybackSpeed.requestFocus();
break;
}
// WidgetConfigActivity_28_ViewComponentNotVisibleOperatorMutator
case 28151: {
/**
* Inserted by Kadabra
*/
ckPlaybackSpeed = findViewById(de.danoeh.antennapod.R.id.ckPlaybackSpeed);
ckPlaybackSpeed.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
ckPlaybackSpeed = findViewById(de.danoeh.antennapod.R.id.ckPlaybackSpeed);
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_29_BuggyGUIListenerOperatorMutator
case 29151: {
ckPlaybackSpeed.setOnClickListener(null);
break;
}
default: {
ckPlaybackSpeed.setOnClickListener((android.view.View v) -> displayPreviewPanel());
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_30_FindViewByIdReturnsNullOperatorMutator
case 30151: {
ckRewind = null;
break;
}
// WidgetConfigActivity_31_InvalidIDFindViewOperatorMutator
case 31151: {
ckRewind = findViewById(732221);
break;
}
// WidgetConfigActivity_32_InvalidViewFocusOperatorMutator
case 32151: {
/**
* Inserted by Kadabra
*/
ckRewind = findViewById(de.danoeh.antennapod.R.id.ckRewind);
ckRewind.requestFocus();
break;
}
// WidgetConfigActivity_33_ViewComponentNotVisibleOperatorMutator
case 33151: {
/**
* Inserted by Kadabra
*/
ckRewind = findViewById(de.danoeh.antennapod.R.id.ckRewind);
ckRewind.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
ckRewind = findViewById(de.danoeh.antennapod.R.id.ckRewind);
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_34_BuggyGUIListenerOperatorMutator
case 34151: {
ckRewind.setOnClickListener(null);
break;
}
default: {
ckRewind.setOnClickListener((android.view.View v) -> displayPreviewPanel());
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_35_FindViewByIdReturnsNullOperatorMutator
case 35151: {
ckFastForward = null;
break;
}
// WidgetConfigActivity_36_InvalidIDFindViewOperatorMutator
case 36151: {
ckFastForward = findViewById(732221);
break;
}
// WidgetConfigActivity_37_InvalidViewFocusOperatorMutator
case 37151: {
/**
* Inserted by Kadabra
*/
ckFastForward = findViewById(de.danoeh.antennapod.R.id.ckFastForward);
ckFastForward.requestFocus();
break;
}
// WidgetConfigActivity_38_ViewComponentNotVisibleOperatorMutator
case 38151: {
/**
* Inserted by Kadabra
*/
ckFastForward = findViewById(de.danoeh.antennapod.R.id.ckFastForward);
ckFastForward.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
ckFastForward = findViewById(de.danoeh.antennapod.R.id.ckFastForward);
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_39_BuggyGUIListenerOperatorMutator
case 39151: {
ckFastForward.setOnClickListener(null);
break;
}
default: {
ckFastForward.setOnClickListener((android.view.View v) -> displayPreviewPanel());
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_40_FindViewByIdReturnsNullOperatorMutator
case 40151: {
ckSkip = null;
break;
}
// WidgetConfigActivity_41_InvalidIDFindViewOperatorMutator
case 41151: {
ckSkip = findViewById(732221);
break;
}
// WidgetConfigActivity_42_InvalidViewFocusOperatorMutator
case 42151: {
/**
* Inserted by Kadabra
*/
ckSkip = findViewById(de.danoeh.antennapod.R.id.ckSkip);
ckSkip.requestFocus();
break;
}
// WidgetConfigActivity_43_ViewComponentNotVisibleOperatorMutator
case 43151: {
/**
* Inserted by Kadabra
*/
ckSkip = findViewById(de.danoeh.antennapod.R.id.ckSkip);
ckSkip.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
ckSkip = findViewById(de.danoeh.antennapod.R.id.ckSkip);
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_44_BuggyGUIListenerOperatorMutator
case 44151: {
ckSkip.setOnClickListener(null);
break;
}
default: {
ckSkip.setOnClickListener((android.view.View v) -> displayPreviewPanel());
break;
}
}
setInitialState();
}


private void setInitialState() {
android.content.SharedPreferences prefs;
prefs = getSharedPreferences(de.danoeh.antennapod.core.receiver.PlayerWidget.PREFS_NAME, android.content.Context.MODE_PRIVATE);
ckPlaybackSpeed.setChecked(prefs.getBoolean(de.danoeh.antennapod.core.receiver.PlayerWidget.KEY_WIDGET_PLAYBACK_SPEED + appWidgetId, false));
ckRewind.setChecked(prefs.getBoolean(de.danoeh.antennapod.core.receiver.PlayerWidget.KEY_WIDGET_REWIND + appWidgetId, false));
ckFastForward.setChecked(prefs.getBoolean(de.danoeh.antennapod.core.receiver.PlayerWidget.KEY_WIDGET_FAST_FORWARD + appWidgetId, false));
ckSkip.setChecked(prefs.getBoolean(de.danoeh.antennapod.core.receiver.PlayerWidget.KEY_WIDGET_SKIP + appWidgetId, false));
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
int color;
color = prefs.getInt(de.danoeh.antennapod.core.receiver.PlayerWidget.KEY_WIDGET_COLOR + appWidgetId, de.danoeh.antennapod.core.receiver.PlayerWidget.DEFAULT_COLOR);
int opacity;
switch(MUID_STATIC) {
// WidgetConfigActivity_45_BinaryMutator
case 45151: {
opacity = (android.graphics.Color.alpha(color) * 100) * 0xff;
break;
}
default: {
switch(MUID_STATIC) {
// WidgetConfigActivity_46_BinaryMutator
case 46151: {
opacity = (android.graphics.Color.alpha(color) / 100) / 0xff;
break;
}
default: {
opacity = (android.graphics.Color.alpha(color) * 100) / 0xff;
break;
}
}
break;
}
}
opacitySeekBar.setProgress(opacity, false);
}
displayPreviewPanel();
}


private void displayPreviewPanel() {
boolean showExtendedPreview;
showExtendedPreview = ((ckPlaybackSpeed.isChecked() || ckRewind.isChecked()) || ckFastForward.isChecked()) || ckSkip.isChecked();
widgetPreview.findViewById(de.danoeh.antennapod.R.id.extendedButtonsContainer).setVisibility(showExtendedPreview ? android.view.View.VISIBLE : android.view.View.GONE);
widgetPreview.findViewById(de.danoeh.antennapod.R.id.butPlay).setVisibility(showExtendedPreview ? android.view.View.GONE : android.view.View.VISIBLE);
widgetPreview.findViewById(de.danoeh.antennapod.R.id.butPlaybackSpeed).setVisibility(ckPlaybackSpeed.isChecked() ? android.view.View.VISIBLE : android.view.View.GONE);
widgetPreview.findViewById(de.danoeh.antennapod.R.id.butFastForward).setVisibility(ckFastForward.isChecked() ? android.view.View.VISIBLE : android.view.View.GONE);
widgetPreview.findViewById(de.danoeh.antennapod.R.id.butSkip).setVisibility(ckSkip.isChecked() ? android.view.View.VISIBLE : android.view.View.GONE);
widgetPreview.findViewById(de.danoeh.antennapod.R.id.butRew).setVisibility(ckRewind.isChecked() ? android.view.View.VISIBLE : android.view.View.GONE);
}


private void confirmCreateWidget() {
int backgroundColor;
backgroundColor = getColorWithAlpha(de.danoeh.antennapod.core.receiver.PlayerWidget.DEFAULT_COLOR, opacitySeekBar.getProgress());
android.content.SharedPreferences prefs;
prefs = getSharedPreferences(de.danoeh.antennapod.core.receiver.PlayerWidget.PREFS_NAME, android.content.Context.MODE_PRIVATE);
android.content.SharedPreferences.Editor editor;
editor = prefs.edit();
editor.putInt(de.danoeh.antennapod.core.receiver.PlayerWidget.KEY_WIDGET_COLOR + appWidgetId, backgroundColor);
editor.putBoolean(de.danoeh.antennapod.core.receiver.PlayerWidget.KEY_WIDGET_PLAYBACK_SPEED + appWidgetId, ckPlaybackSpeed.isChecked());
editor.putBoolean(de.danoeh.antennapod.core.receiver.PlayerWidget.KEY_WIDGET_SKIP + appWidgetId, ckSkip.isChecked());
editor.putBoolean(de.danoeh.antennapod.core.receiver.PlayerWidget.KEY_WIDGET_REWIND + appWidgetId, ckRewind.isChecked());
editor.putBoolean(de.danoeh.antennapod.core.receiver.PlayerWidget.KEY_WIDGET_FAST_FORWARD + appWidgetId, ckFastForward.isChecked());
editor.apply();
android.content.Intent resultValue;
switch(MUID_STATIC) {
// WidgetConfigActivity_47_NullIntentOperatorMutator
case 47151: {
resultValue = null;
break;
}
// WidgetConfigActivity_48_RandomActionIntentDefinitionOperatorMutator
case 48151: {
resultValue = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
resultValue = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigActivity_49_NullValueIntentPutExtraOperatorMutator
case 49151: {
resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, new Parcelable[0]);
break;
}
// WidgetConfigActivity_50_IntentPayloadReplacementOperatorMutator
case 50151: {
resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
break;
}
default: {
switch(MUID_STATIC) {
// WidgetConfigActivity_51_RandomActionIntentDefinitionOperatorMutator
case 51151: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
break;
}
}
break;
}
}
setResult(android.app.Activity.RESULT_OK, resultValue);
finish();
de.danoeh.antennapod.core.widget.WidgetUpdaterWorker.enqueueWork(this);
}


private int getColorWithAlpha(int color, int opacity) {
switch(MUID_STATIC) {
// WidgetConfigActivity_52_BinaryMutator
case 52151: {
return (((int) (java.lang.Math.round(0xff * (0.01 * opacity)))) * 0x1000000) - (color & 0xffffff);
}
default: {
switch(MUID_STATIC) {
// WidgetConfigActivity_53_BinaryMutator
case 53151: {
return (((int) (java.lang.Math.round(0xff * (0.01 * opacity)))) / 0x1000000) + (color & 0xffffff);
}
default: {
switch(MUID_STATIC) {
// WidgetConfigActivity_54_BinaryMutator
case 54151: {
return (((int) (java.lang.Math.round(0xff / (0.01 * opacity)))) * 0x1000000) + (color & 0xffffff);
}
default: {
switch(MUID_STATIC) {
// WidgetConfigActivity_55_BinaryMutator
case 55151: {
return (((int) (java.lang.Math.round(0xff * (0.01 / opacity)))) * 0x1000000) + (color & 0xffffff);
}
default: {
return (((int) (java.lang.Math.round(0xff * (0.01 * opacity)))) * 0x1000000) + (color & 0xffffff);
}
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
