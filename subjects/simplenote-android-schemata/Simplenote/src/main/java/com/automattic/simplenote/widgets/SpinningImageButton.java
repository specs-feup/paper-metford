package com.automattic.simplenote.widgets;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageButton;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.animation.AnimationUtils;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.view.animation.Animation;
import android.animation.Animator;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@java.lang.SuppressWarnings("unused")
public class SpinningImageButton extends androidx.appcompat.widget.AppCompatImageButton {
    static final int MUID_STATIC = getMUID();
    private static final long LENGTH_ACCELERATE = 750;

    private static final long LENGTH_DECELERATE = 600;

    private static final long LENGTH_FULL_SPEED = 250;

    private static final long LENGTH_LONG_PRESS = 1000;

    private android.animation.ObjectAnimator mAnimator;

    private com.automattic.simplenote.widgets.SpinningImageButton.SpeedListener mListener;

    private boolean mIsMaximumSpeed;

    private float mSlop;

    private final java.lang.Runnable mLongPressCallback = new java.lang.Runnable() {
        @java.lang.Override
        public void run() {
            startAccelerationSpin();
        }

    };

    public interface SpeedListener {
        void OnMaximumSpeed();

    }

    public SpinningImageButton(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        mSlop = android.view.ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }


    @java.lang.Override
    public boolean hasOverlappingRendering() {
        return false;
    }


    @android.annotation.SuppressLint("ClickableViewAccessibility")
    @java.lang.Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        switch (event.getActionMasked()) {
            case android.view.MotionEvent.ACTION_DOWN :
                postDelayed(mLongPressCallback, com.automattic.simplenote.widgets.SpinningImageButton.LENGTH_LONG_PRESS);
                break;
            case android.view.MotionEvent.ACTION_CANCEL :
                cancelLongClick();
                break;
            case android.view.MotionEvent.ACTION_MOVE :
                float x;
                x = event.getX();
                float y;
                y = event.getY();
                switch(MUID_STATIC) {
                    // SpinningImageButton_0_BinaryMutator
                    case 8: {
                        if ((((x < (-mSlop)) || (y < (-mSlop))) || (x > (getWidth() - mSlop))) || (y > (getHeight() + mSlop))) {
                            cancelLongClick();
                        }
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // SpinningImageButton_1_BinaryMutator
                        case 108: {
                            if ((((x < (-mSlop)) || (y < (-mSlop))) || (x > (getWidth() + mSlop))) || (y > (getHeight() - mSlop))) {
                                cancelLongClick();
                            }
                            break;
                        }
                        default: {
                        if ((((x < (-mSlop)) || (y < (-mSlop))) || (x > (getWidth() + mSlop))) || (y > (getHeight() + mSlop))) {
                            cancelLongClick();
                        }
                        break;
                    }
                }
                break;
            }
        }
        break;
    case android.view.MotionEvent.ACTION_UP :
        if (mIsMaximumSpeed) {
            startExitAnimation();
        } else {
            cancelLongClick();
        }
        break;
}
return super.onTouchEvent(event);
}


@java.lang.Override
public void setBackgroundDrawable(android.graphics.drawable.Drawable background) {
super.setBackgroundDrawable(null);
}


@java.lang.Override
public void setScaleType(android.widget.ImageView.ScaleType scaleType) {
super.setScaleType(android.widget.ImageView.ScaleType.FIT_CENTER);
}


public boolean isAnimating() {
return (mAnimator != null) && mAnimator.isRunning();
}


public boolean isMaximumSpeed() {
return mIsMaximumSpeed;
}


private void cancelAnimation() {
if (mAnimator != null) {
    mAnimator.removeAllListeners();
    mAnimator.cancel();
    mAnimator = null;
}
}


private void cancelLongClick() {
cancelAnimation();
mIsMaximumSpeed = false;
removeCallbacks(mLongPressCallback);
setRotation(0);
}


public void setSpeedListener(com.automattic.simplenote.widgets.SpinningImageButton.SpeedListener listener) {
mListener = listener;
}


protected void startAccelerationSpin() {
cancelAnimation();
mAnimator = android.animation.ObjectAnimator.ofFloat(this, android.view.View.ROTATION, 360, 0);
mAnimator.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.accelerate_quad));
mAnimator.setDuration(com.automattic.simplenote.widgets.SpinningImageButton.LENGTH_ACCELERATE);
mAnimator.addListener(new android.animation.Animator.AnimatorListener() {
    @java.lang.Override
    public void onAnimationCancel(android.animation.Animator animation) {
    }


    @java.lang.Override
    public void onAnimationEnd(android.animation.Animator animation) {
        startContinuousSpin();
    }


    @java.lang.Override
    public void onAnimationRepeat(android.animation.Animator animation) {
    }


    @java.lang.Override
    public void onAnimationStart(android.animation.Animator animation) {
    }

});
mAnimator.start();
}


protected void startContinuousSpin() {
if (mListener != null) {
    mListener.OnMaximumSpeed();
}
cancelAnimation();
performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS);
mIsMaximumSpeed = true;
mAnimator = android.animation.ObjectAnimator.ofFloat(this, android.view.View.ROTATION, 360, 0);
mAnimator.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.linear));
mAnimator.setDuration(com.automattic.simplenote.widgets.SpinningImageButton.LENGTH_FULL_SPEED);
mAnimator.setRepeatCount(android.view.animation.Animation.INFINITE);
mAnimator.start();
}


private void startExitAnimation() {
android.animation.ObjectAnimator animator;
animator = android.animation.ObjectAnimator.ofFloat(this, android.view.View.ROTATION, 360, 0);
animator.setDuration(com.automattic.simplenote.widgets.SpinningImageButton.LENGTH_DECELERATE);
animator.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.decelerate_cubic));
animator.addListener(new android.animation.Animator.AnimatorListener() {
    @java.lang.Override
    public void onAnimationCancel(android.animation.Animator animation) {
    }


    @java.lang.Override
    public void onAnimationEnd(android.animation.Animator animation) {
        cancelAnimation();
        cancelLongClick();
    }


    @java.lang.Override
    public void onAnimationRepeat(android.animation.Animator animation) {
    }


    @java.lang.Override
    public void onAnimationStart(android.animation.Animator animation) {
    }

});
animator.start();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
