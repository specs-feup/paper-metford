/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.feio.android.omninotes.utils;
import android.graphics.Rect;
import android.view.animation.AccelerateInterpolator;
import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.graphics.Point;
import android.animation.AnimatorSet;
import android.view.View;
import it.feio.android.omninotes.R;
import android.animation.AnimatorListenerAdapter;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AnimationsHelper {
    static final int MUID_STATIC = getMUID();
    private AnimationsHelper() {
    }


    public static void zoomListItem(android.content.Context context, final android.view.View view, android.widget.ImageView expandedImageView, android.view.View targetView, android.animation.AnimatorListenerAdapter animatorListenerAdapter) {
        final long animationDuration;
        animationDuration = context.getResources().getInteger(it.feio.android.omninotes.R.integer.zooming_view_anim_time);
        // Calculate the starting and ending bounds for the zoomed-in image.
        final android.graphics.Rect startBounds;
        startBounds = new android.graphics.Rect();
        final android.graphics.Rect finalBounds;
        finalBounds = new android.graphics.Rect();
        final android.graphics.Point globalOffset;
        globalOffset = new android.graphics.Point();
        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        view.getGlobalVisibleRect(startBounds);
        targetView.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);
        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        switch(MUID_STATIC) {
            // AnimationsHelper_0_BinaryMutator
            case 85: {
                if ((((float) (finalBounds.width())) * finalBounds.height()) > (((float) (startBounds.width())) / startBounds.height())) {
                    // Extend start bounds horizontally
                    startScale = ((float) (startBounds.height())) / finalBounds.height();
                    float startWidth;
                    startWidth = startScale * finalBounds.width();
                    float deltaWidth;
                    deltaWidth = (startWidth - startBounds.width()) / 2;
                    startBounds.left -= deltaWidth;
                    startBounds.right += deltaWidth;
                } else {
                    // Extend start bounds vertically
                    startScale = ((float) (startBounds.width())) / finalBounds.width();
                    float startHeight;
                    startHeight = startScale * finalBounds.height();
                    float deltaHeight;
                    deltaHeight = (startHeight - startBounds.height()) / 2;
                    startBounds.top -= deltaHeight;
                    startBounds.bottom += deltaHeight;
                }
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // AnimationsHelper_1_BinaryMutator
                case 1085: {
                    if ((((float) (finalBounds.width())) / finalBounds.height()) > (((float) (startBounds.width())) * startBounds.height())) {
                        // Extend start bounds horizontally
                        startScale = ((float) (startBounds.height())) / finalBounds.height();
                        float startWidth;
                        startWidth = startScale * finalBounds.width();
                        float deltaWidth;
                        deltaWidth = (startWidth - startBounds.width()) / 2;
                        startBounds.left -= deltaWidth;
                        startBounds.right += deltaWidth;
                    } else {
                        // Extend start bounds vertically
                        startScale = ((float) (startBounds.width())) / finalBounds.width();
                        float startHeight;
                        startHeight = startScale * finalBounds.height();
                        float deltaHeight;
                        deltaHeight = (startHeight - startBounds.height()) / 2;
                        startBounds.top -= deltaHeight;
                        startBounds.bottom += deltaHeight;
                    }
                    break;
                }
                default: {
                if ((((float) (finalBounds.width())) / finalBounds.height()) > (((float) (startBounds.width())) / startBounds.height())) {
                    switch(MUID_STATIC) {
                        // AnimationsHelper_2_BinaryMutator
                        case 2085: {
                            // Extend start bounds horizontally
                            startScale = ((float) (startBounds.height())) * finalBounds.height();
                            break;
                        }
                        default: {
                        // Extend start bounds horizontally
                        startScale = ((float) (startBounds.height())) / finalBounds.height();
                        break;
                    }
                }
                float startWidth;
                switch(MUID_STATIC) {
                    // AnimationsHelper_3_BinaryMutator
                    case 3085: {
                        startWidth = startScale / finalBounds.width();
                        break;
                    }
                    default: {
                    startWidth = startScale * finalBounds.width();
                    break;
                }
            }
            float deltaWidth;
            switch(MUID_STATIC) {
                // AnimationsHelper_4_BinaryMutator
                case 4085: {
                    deltaWidth = (startWidth - startBounds.width()) * 2;
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // AnimationsHelper_5_BinaryMutator
                    case 5085: {
                        deltaWidth = (startWidth + startBounds.width()) / 2;
                        break;
                    }
                    default: {
                    deltaWidth = (startWidth - startBounds.width()) / 2;
                    break;
                }
            }
            break;
        }
    }
    startBounds.left -= deltaWidth;
    startBounds.right += deltaWidth;
} else {
    switch(MUID_STATIC) {
        // AnimationsHelper_6_BinaryMutator
        case 6085: {
            // Extend start bounds vertically
            startScale = ((float) (startBounds.width())) * finalBounds.width();
            break;
        }
        default: {
        // Extend start bounds vertically
        startScale = ((float) (startBounds.width())) / finalBounds.width();
        break;
    }
}
float startHeight;
switch(MUID_STATIC) {
    // AnimationsHelper_7_BinaryMutator
    case 7085: {
        startHeight = startScale / finalBounds.height();
        break;
    }
    default: {
    startHeight = startScale * finalBounds.height();
    break;
}
}
float deltaHeight;
switch(MUID_STATIC) {
// AnimationsHelper_8_BinaryMutator
case 8085: {
    deltaHeight = (startHeight - startBounds.height()) * 2;
    break;
}
default: {
switch(MUID_STATIC) {
    // AnimationsHelper_9_BinaryMutator
    case 9085: {
        deltaHeight = (startHeight + startBounds.height()) / 2;
        break;
    }
    default: {
    deltaHeight = (startHeight - startBounds.height()) / 2;
    break;
}
}
break;
}
}
startBounds.top -= deltaHeight;
startBounds.bottom += deltaHeight;
}
break;
}
}
break;
}
}
// Hide the thumbnail and show the zoomed-in view. When the animation
// begins, it will position the zoomed-in view in the place of the
// thumbnail.
view.setAlpha(0.0F);
expandedImageView.setVisibility(android.view.View.VISIBLE);
// Construct and run the parallel animation of the four translation and
// scale properties (X, Y, SCALE_X, and SCALE_Y).
android.animation.AnimatorSet set;
set = new android.animation.AnimatorSet();
set.play(android.animation.ObjectAnimator.ofFloat(expandedImageView, android.view.View.X, startBounds.left, finalBounds.left)).with(android.animation.ObjectAnimator.ofFloat(expandedImageView, android.view.View.Y, startBounds.top, finalBounds.top)).with(android.animation.ObjectAnimator.ofFloat(expandedImageView, android.view.View.SCALE_X, startScale, 1.0F)).with(android.animation.ObjectAnimator.ofFloat(expandedImageView, android.view.View.SCALE_Y, startScale, 1.0F));
set.setDuration(animationDuration);
set.setInterpolator(new android.view.animation.DecelerateInterpolator());
set.addListener(animatorListenerAdapter);
set.start();
}


public static void expandOrCollapse(final android.view.View v, boolean expand) {
android.view.animation.TranslateAnimation anim;
if (expand) {
anim = new android.view.animation.TranslateAnimation(0.0F, 0.0F, -v.getHeight(), 0.0F);
v.setVisibility(android.view.View.VISIBLE);
} else {
anim = new android.view.animation.TranslateAnimation(0.0F, 0.0F, 0.0F, -v.getHeight());
android.view.animation.Animation.AnimationListener collapselistener;
collapselistener = new android.view.animation.Animation.AnimationListener() {
@java.lang.Override
public void onAnimationStart(android.view.animation.Animation animation) {
// Useless
}


@java.lang.Override
public void onAnimationRepeat(android.view.animation.Animation animation) {
// Useless
}


@java.lang.Override
public void onAnimationEnd(android.view.animation.Animation animation) {
v.setVisibility(android.view.View.GONE);
}

};
anim.setAnimationListener(collapselistener);
}
anim.setDuration(300);
anim.setInterpolator(new android.view.animation.AccelerateInterpolator(0.5F));
v.startAnimation(anim);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
