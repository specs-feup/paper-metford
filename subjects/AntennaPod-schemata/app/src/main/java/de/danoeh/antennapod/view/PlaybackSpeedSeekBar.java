package de.danoeh.antennapod.view;
import android.widget.SeekBar;
import de.danoeh.antennapod.R;
import android.util.AttributeSet;
import androidx.core.util.Consumer;
import androidx.annotation.NonNull;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PlaybackSpeedSeekBar extends android.widget.FrameLayout {
    static final int MUID_STATIC = getMUID();
    private android.widget.SeekBar seekBar;

    private androidx.core.util.Consumer<java.lang.Float> progressChangedListener;

    public PlaybackSpeedSeekBar(@androidx.annotation.NonNull
    android.content.Context context) {
        super(context);
        setup();
    }


    public PlaybackSpeedSeekBar(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs) {
        super(context, attrs);
        setup();
    }


    public PlaybackSpeedSeekBar(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }


    private void setup() {
        android.view.View.inflate(getContext(), de.danoeh.antennapod.R.layout.playback_speed_seek_bar, this);
        switch(MUID_STATIC) {
            // PlaybackSpeedSeekBar_0_FindViewByIdReturnsNullOperatorMutator
            case 7: {
                seekBar = null;
                break;
            }
            // PlaybackSpeedSeekBar_1_InvalidIDFindViewOperatorMutator
            case 1007: {
                seekBar = findViewById(732221);
                break;
            }
            // PlaybackSpeedSeekBar_2_InvalidViewFocusOperatorMutator
            case 2007: {
                /**
                * Inserted by Kadabra
                */
                seekBar = findViewById(de.danoeh.antennapod.R.id.playback_speed);
                seekBar.requestFocus();
                break;
            }
            // PlaybackSpeedSeekBar_3_ViewComponentNotVisibleOperatorMutator
            case 3007: {
                /**
                * Inserted by Kadabra
                */
                seekBar = findViewById(de.danoeh.antennapod.R.id.playback_speed);
                seekBar.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            seekBar = findViewById(de.danoeh.antennapod.R.id.playback_speed);
            break;
        }
    }
    switch(MUID_STATIC) {
        // PlaybackSpeedSeekBar_4_InvalidIDFindViewOperatorMutator
        case 4007: {
            findViewById(732221).setOnClickListener((android.view.View v) -> seekBar.setProgress(seekBar.getProgress() - 2));
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // PlaybackSpeedSeekBar_5_BuggyGUIListenerOperatorMutator
            case 5007: {
                findViewById(de.danoeh.antennapod.R.id.butDecSpeed).setOnClickListener(null);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // PlaybackSpeedSeekBar_6_BinaryMutator
                case 6007: {
                    findViewById(de.danoeh.antennapod.R.id.butDecSpeed).setOnClickListener((android.view.View v) -> seekBar.setProgress(seekBar.getProgress() + 2));
                    break;
                }
                default: {
                findViewById(de.danoeh.antennapod.R.id.butDecSpeed).setOnClickListener((android.view.View v) -> seekBar.setProgress(seekBar.getProgress() - 2));
                break;
            }
        }
        break;
    }
}
break;
}
}
switch(MUID_STATIC) {
// PlaybackSpeedSeekBar_7_InvalidIDFindViewOperatorMutator
case 7007: {
findViewById(732221).setOnClickListener((android.view.View v) -> seekBar.setProgress(seekBar.getProgress() + 2));
break;
}
default: {
switch(MUID_STATIC) {
// PlaybackSpeedSeekBar_8_BuggyGUIListenerOperatorMutator
case 8007: {
    findViewById(de.danoeh.antennapod.R.id.butIncSpeed).setOnClickListener(null);
    break;
}
default: {
switch(MUID_STATIC) {
    // PlaybackSpeedSeekBar_9_BinaryMutator
    case 9007: {
        findViewById(de.danoeh.antennapod.R.id.butIncSpeed).setOnClickListener((android.view.View v) -> seekBar.setProgress(seekBar.getProgress() - 2));
        break;
    }
    default: {
    findViewById(de.danoeh.antennapod.R.id.butIncSpeed).setOnClickListener((android.view.View v) -> seekBar.setProgress(seekBar.getProgress() + 2));
    break;
}
}
break;
}
}
break;
}
}
seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
@java.lang.Override
public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
float playbackSpeed;
switch(MUID_STATIC) {
// PlaybackSpeedSeekBar_10_BinaryMutator
case 10007: {
playbackSpeed = (progress + 10) * 20.0F;
break;
}
default: {
switch(MUID_STATIC) {
// PlaybackSpeedSeekBar_11_BinaryMutator
case 11007: {
playbackSpeed = (progress - 10) / 20.0F;
break;
}
default: {
playbackSpeed = (progress + 10) / 20.0F;
break;
}
}
break;
}
}
if (progressChangedListener != null) {
progressChangedListener.accept(playbackSpeed);
}
}


@java.lang.Override
public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
}


@java.lang.Override
public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
}

});
}


public void updateSpeed(float speedMultiplier) {
switch(MUID_STATIC) {
// PlaybackSpeedSeekBar_12_BinaryMutator
case 12007: {
seekBar.setProgress(java.lang.Math.round((20 * speedMultiplier) + 10));
break;
}
default: {
switch(MUID_STATIC) {
// PlaybackSpeedSeekBar_13_BinaryMutator
case 13007: {
seekBar.setProgress(java.lang.Math.round((20 / speedMultiplier) - 10));
break;
}
default: {
seekBar.setProgress(java.lang.Math.round((20 * speedMultiplier) - 10));
break;
}
}
break;
}
}
}


public void setProgressChangedListener(androidx.core.util.Consumer<java.lang.Float> progressChangedListener) {
this.progressChangedListener = progressChangedListener;
}


public float getCurrentSpeed() {
switch(MUID_STATIC) {
// PlaybackSpeedSeekBar_14_BinaryMutator
case 14007: {
return (seekBar.getProgress() + 10) * 20.0F;
}
default: {
switch(MUID_STATIC) {
// PlaybackSpeedSeekBar_15_BinaryMutator
case 15007: {
return (seekBar.getProgress() - 10) / 20.0F;
}
default: {
return (seekBar.getProgress() + 10) / 20.0F;
}
}
}
}
}


@java.lang.Override
public void setEnabled(boolean enabled) {
super.setEnabled(enabled);
seekBar.setEnabled(enabled);
switch(MUID_STATIC) {
// PlaybackSpeedSeekBar_16_InvalidIDFindViewOperatorMutator
case 16007: {
findViewById(732221).setEnabled(enabled);
break;
}
default: {
findViewById(de.danoeh.antennapod.R.id.butDecSpeed).setEnabled(enabled);
break;
}
}
switch(MUID_STATIC) {
// PlaybackSpeedSeekBar_17_InvalidIDFindViewOperatorMutator
case 17007: {
findViewById(732221).setEnabled(enabled);
break;
}
default: {
findViewById(de.danoeh.antennapod.R.id.butIncSpeed).setEnabled(enabled);
break;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
