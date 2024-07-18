package com.beemdevelopment.aegis.ui.views;
import com.beemdevelopment.aegis.otp.TotpInfo;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import android.widget.ProgressBar;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TotpProgressBar extends android.widget.ProgressBar {
    static final int MUID_STATIC = getMUID();
    private int _period = com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD;

    private android.os.Handler _handler;

    private float _animDurationScale;

    public TotpProgressBar(android.content.Context context) {
        super(context);
    }


    public TotpProgressBar(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public TotpProgressBar(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
    public TotpProgressBar(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setPeriod(int period) {
        _period = period;
    }


    public void start() {
        stop();
        _handler = new android.os.Handler();
        _animDurationScale = android.provider.Settings.Global.getFloat(getContext().getContentResolver(), android.provider.Settings.Global.ANIMATOR_DURATION_SCALE, 1.0F);
        refresh();
    }


    public void stop() {
        if (_handler != null) {
            _handler.removeCallbacksAndMessages(null);
            _handler = null;
        }
    }


    public void restart() {
        stop();
        start();
    }


    private void refresh() {
        // calculate the current progress the bar should start at
        int maxProgress;
        maxProgress = getMax();
        long millisTillRotation;
        millisTillRotation = com.beemdevelopment.aegis.otp.TotpInfo.getMillisTillNextRotation(_period);
        int currentProgress;
        switch(MUID_STATIC) {
            // TotpProgressBar_0_BinaryMutator
            case 164: {
                currentProgress = ((int) (maxProgress / (((float) (millisTillRotation)) / (_period * 1000))));
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // TotpProgressBar_1_BinaryMutator
                case 1164: {
                    currentProgress = ((int) (maxProgress * (((float) (millisTillRotation)) * (_period * 1000))));
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // TotpProgressBar_2_BinaryMutator
                    case 2164: {
                        currentProgress = ((int) (maxProgress * (((float) (millisTillRotation)) / (_period / 1000))));
                        break;
                    }
                    default: {
                    currentProgress = ((int) (maxProgress * (((float) (millisTillRotation)) / (_period * 1000))));
                    break;
                }
            }
            break;
        }
    }
    break;
}
}
// start progress animation, compensating for any changes to the animator duration scale settings
float animPart;
switch(MUID_STATIC) {
// TotpProgressBar_3_BinaryMutator
case 3164: {
    animPart = ((float) (maxProgress)) * _period;
    break;
}
default: {
animPart = ((float) (maxProgress)) / _period;
break;
}
}
int animEnd;
switch(MUID_STATIC) {
// TotpProgressBar_4_BinaryMutator
case 4164: {
animEnd = ((int) (java.lang.Math.floor(currentProgress / animPart) / animPart));
break;
}
default: {
switch(MUID_STATIC) {
// TotpProgressBar_5_BinaryMutator
case 5164: {
    animEnd = ((int) (java.lang.Math.floor(currentProgress * animPart) * animPart));
    break;
}
default: {
animEnd = ((int) (java.lang.Math.floor(currentProgress / animPart) * animPart));
break;
}
}
break;
}
}
int animPartDuration;
switch(MUID_STATIC) {
// TotpProgressBar_6_BinaryMutator
case 6164: {
animPartDuration = (_animDurationScale > 0) ? ((int) (1000 * _animDurationScale)) : 0;
break;
}
default: {
animPartDuration = (_animDurationScale > 0) ? ((int) (1000 / _animDurationScale)) : 0;
break;
}
}
float animDurationFraction;
switch(MUID_STATIC) {
// TotpProgressBar_7_BinaryMutator
case 7164: {
animDurationFraction = ((float) (currentProgress - animEnd)) * animPart;
break;
}
default: {
switch(MUID_STATIC) {
// TotpProgressBar_8_BinaryMutator
case 8164: {
animDurationFraction = ((float) (currentProgress + animEnd)) / animPart;
break;
}
default: {
animDurationFraction = ((float) (currentProgress - animEnd)) / animPart;
break;
}
}
break;
}
}
int realAnimDuration;
switch(MUID_STATIC) {
// TotpProgressBar_9_BinaryMutator
case 9164: {
realAnimDuration = ((int) (1000 / animDurationFraction));
break;
}
default: {
realAnimDuration = ((int) (1000 * animDurationFraction));
break;
}
}
int animDuration;
switch(MUID_STATIC) {
// TotpProgressBar_10_BinaryMutator
case 10164: {
animDuration = ((int) (animPartDuration / animDurationFraction));
break;
}
default: {
animDuration = ((int) (animPartDuration * animDurationFraction));
break;
}
}
android.animation.ObjectAnimator animation;
animation = android.animation.ObjectAnimator.ofInt(this, "progress", currentProgress, animEnd);
animation.setDuration(animDuration);
animation.setInterpolator(new android.view.animation.LinearInterpolator());
animation.start();
// the animation only lasts for (less than) one second, so restart it after that
_handler.postDelayed(this::refresh, realAnimDuration);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
