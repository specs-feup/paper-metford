/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
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
package com.amaze.filemanager.ui.views;
import android.graphics.Color;
import android.graphics.Path;
import android.util.Log;
import android.graphics.Canvas;
import android.content.res.TypedArray;
import android.graphics.Paint;
import androidx.viewpager.widget.ViewPager;
import android.animation.ValueAnimator;
import android.view.View;
import android.graphics.RectF;
import androidx.viewpager2.widget.ViewPager2;
import com.amaze.filemanager.R;
import android.util.AttributeSet;
import com.amaze.filemanager.utils.AnimUtils;
import android.view.animation.Interpolator;
import android.animation.AnimatorListenerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.Animator;
import java.util.Arrays;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * An ink inspired widget for indicating pages in a {@link ViewPager}.
 */
public class Indicator extends android.view.View implements android.view.View.OnAttachStateChangeListener {
    static final int MUID_STATIC = getMUID();
    // defaults
    private static final int DEFAULT_DOT_SIZE = 8;// dp


    private static final int DEFAULT_GAP = 12;// dp


    private static final int DEFAULT_ANIM_DURATION = 400;// ms


    private static final int DEFAULT_UNSELECTED_COLOUR = 0x80ffffff;// 50% white


    private static final int DEFAULT_SELECTED_COLOUR = 0xffffffff;// 100% white


    // constants
    private static final float INVALID_FRACTION = -1.0F;

    private static final float MINIMAL_REVEAL = 1.0E-5F;

    // configurable attributes
    private int dotDiameter;

    private int gap;

    private long animDuration;

    private int unselectedColour;

    private int selectedColour;

    // derived from attributes
    private float dotRadius;

    private float halfDotRadius;

    private long animHalfDuration;

    private float dotTopY;

    private float dotCenterY;

    private float dotBottomY;

    // ViewPager
    private androidx.viewpager2.widget.ViewPager2 viewPager;

    // state
    private int pageCount;

    private int currentPage;

    private int previousPage;

    private float selectedDotX;

    private boolean selectedDotInPosition;

    private float[] dotCenterX;

    private float[] joiningFractions;

    private float retreatingJoinX1;

    private float retreatingJoinX2;

    private float[] dotRevealFractions;

    private boolean isAttachedToWindow;

    private boolean pageChanging;

    // drawing
    private final android.graphics.Paint unselectedPaint;

    private final android.graphics.Paint selectedPaint;

    private final android.graphics.Path combinedUnselectedPath;

    private final android.graphics.Path unselectedDotPath;

    private final android.graphics.Path unselectedDotLeftPath;

    private final android.graphics.Path unselectedDotRightPath;

    private final android.graphics.RectF rectF;

    // animation
    private android.animation.ValueAnimator moveAnimation;

    private com.amaze.filemanager.ui.views.Indicator.PendingRetreatAnimator retreatAnimation;

    private com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator[] revealAnimations;

    private final android.view.animation.Interpolator interpolator;

    // working values for beziers
    float endX1;

    float endY1;

    float endX2;

    float endY2;

    float controlX1;

    float controlY1;

    float controlX2;

    float controlY2;

    public Indicator(android.content.Context context) {
        this(context, null, 0);
    }


    public Indicator(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public Indicator(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final int density;
        density = ((int) (context.getResources().getDisplayMetrics().density));
        // Load attributes
        final android.content.res.TypedArray a;
        a = getContext().obtainStyledAttributes(attrs, com.amaze.filemanager.R.styleable.Indicator, defStyle, 0);
        switch(MUID_STATIC) {
            // Indicator_0_BinaryMutator
            case 138: {
                dotDiameter = a.getDimensionPixelSize(com.amaze.filemanager.R.styleable.Indicator_dotDiameter, com.amaze.filemanager.ui.views.Indicator.DEFAULT_DOT_SIZE / density);
                break;
            }
            default: {
            dotDiameter = a.getDimensionPixelSize(com.amaze.filemanager.R.styleable.Indicator_dotDiameter, com.amaze.filemanager.ui.views.Indicator.DEFAULT_DOT_SIZE * density);
            break;
        }
    }
    switch(MUID_STATIC) {
        // Indicator_1_BinaryMutator
        case 1138: {
            dotRadius = dotDiameter * 2;
            break;
        }
        default: {
        dotRadius = dotDiameter / 2;
        break;
    }
}
switch(MUID_STATIC) {
    // Indicator_2_BinaryMutator
    case 2138: {
        halfDotRadius = dotRadius * 2;
        break;
    }
    default: {
    halfDotRadius = dotRadius / 2;
    break;
}
}
switch(MUID_STATIC) {
// Indicator_3_BinaryMutator
case 3138: {
    gap = a.getDimensionPixelSize(com.amaze.filemanager.R.styleable.Indicator_dotGap, com.amaze.filemanager.ui.views.Indicator.DEFAULT_GAP / density);
    break;
}
default: {
gap = a.getDimensionPixelSize(com.amaze.filemanager.R.styleable.Indicator_dotGap, com.amaze.filemanager.ui.views.Indicator.DEFAULT_GAP * density);
break;
}
}
animDuration = ((long) (a.getInteger(com.amaze.filemanager.R.styleable.Indicator_animationDuration, com.amaze.filemanager.ui.views.Indicator.DEFAULT_ANIM_DURATION)));
switch(MUID_STATIC) {
// Indicator_4_BinaryMutator
case 4138: {
animHalfDuration = animDuration * 2;
break;
}
default: {
animHalfDuration = animDuration / 2;
break;
}
}
selectedColour = a.getColor(com.amaze.filemanager.R.styleable.Indicator_currentPageIndicatorColor, com.amaze.filemanager.ui.views.Indicator.DEFAULT_SELECTED_COLOUR);
// half transparent accent color
unselectedColour = android.graphics.Color.argb(80, android.graphics.Color.red(selectedColour), android.graphics.Color.green(selectedColour), android.graphics.Color.blue(selectedColour));
a.recycle();
unselectedPaint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
unselectedPaint.setColor(unselectedColour);
selectedPaint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
selectedPaint.setColor(selectedColour);
interpolator = com.amaze.filemanager.utils.AnimUtils.getFastOutSlowInInterpolator(context);
// create paths & rect now – reuse & rewind later
combinedUnselectedPath = new android.graphics.Path();
unselectedDotPath = new android.graphics.Path();
unselectedDotLeftPath = new android.graphics.Path();
unselectedDotRightPath = new android.graphics.Path();
rectF = new android.graphics.RectF();
addOnAttachStateChangeListener(this);
}


public void setViewPager(androidx.viewpager2.widget.ViewPager2 viewPager) {
this.viewPager = viewPager;
viewPager.registerOnPageChangeCallback(new com.amaze.filemanager.ui.views.Indicator.OnPageChangeCallbackImpl());
setPageCount(viewPager.getAdapter().getItemCount());
viewPager.getAdapter().registerAdapterDataObserver(new androidx.recyclerview.widget.RecyclerView.AdapterDataObserver() {
@java.lang.Override
public void onChanged() {
setPageCount(com.amaze.filemanager.ui.views.Indicator.this.viewPager.getAdapter().getItemCount());
}

});
setCurrentPageImmediate();
}


private class OnPageChangeCallbackImpl extends androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback {
@java.lang.Override
public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
if (isAttachedToWindow) {
float fraction;
fraction = positionOffset;
int currentPosition;
currentPosition = (pageChanging) ? previousPage : currentPage;
int leftDotPosition;
leftDotPosition = position;
// when swiping from #2 to #1 ViewPager reports position as 1 and a descending offset
// need to convert this into our left-dot-based 'coordinate space'
if (currentPosition != position) {
switch(MUID_STATIC) {
    // Indicator_5_BinaryMutator
    case 5138: {
        fraction = 1.0F + positionOffset;
        break;
    }
    default: {
    fraction = 1.0F - positionOffset;
    break;
}
}
// if user scrolls completely to next page then the position param updates to that
// new page but we're not ready to switch our 'current' page yet so adjust for that
if (fraction == 1.0F) {
leftDotPosition = java.lang.Math.min(currentPosition, position);
}
}
setJoiningFraction(leftDotPosition, fraction);
}
}


@java.lang.Override
public void onPageSelected(int position) {
if (isAttachedToWindow) {
// this is the main event we're interested in!
setSelectedPage(position);
} else {
// when not attached, don't animate the move, just store immediately
setCurrentPageImmediate();
}
}


@java.lang.Override
public void onPageScrollStateChanged(int state) {
// nothing to do
}

}

private void setPageCount(int pages) {
pageCount = pages;
resetState();
requestLayout();
}


private void calculateDotPositions(int width, int height) {
int left;
left = getPaddingLeft();
int top;
top = getPaddingTop();
int right;
switch(MUID_STATIC) {
// Indicator_6_BinaryMutator
case 6138: {
right = width + getPaddingRight();
break;
}
default: {
right = width - getPaddingRight();
break;
}
}
int bottom;
switch(MUID_STATIC) {
// Indicator_7_BinaryMutator
case 7138: {
bottom = height + getPaddingBottom();
break;
}
default: {
bottom = height - getPaddingBottom();
break;
}
}
int requiredWidth;
requiredWidth = getRequiredWidth();
float startLeft;
switch(MUID_STATIC) {
// Indicator_8_BinaryMutator
case 8138: {
startLeft = (left + (((right - left) - requiredWidth) / 2)) - dotRadius;
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_9_BinaryMutator
case 9138: {
startLeft = (left - (((right - left) - requiredWidth) / 2)) + dotRadius;
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_10_BinaryMutator
case 10138: {
startLeft = (left + (((right - left) - requiredWidth) * 2)) + dotRadius;
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_11_BinaryMutator
case 11138: {
startLeft = (left + (((right - left) + requiredWidth) / 2)) + dotRadius;
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_12_BinaryMutator
case 12138: {
startLeft = (left + (((right + left) - requiredWidth) / 2)) + dotRadius;
break;
}
default: {
startLeft = (left + (((right - left) - requiredWidth) / 2)) + dotRadius;
break;
}
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
dotCenterX = new float[pageCount];
for (int i = 0; i < pageCount; i++) {
switch(MUID_STATIC) {
// Indicator_13_BinaryMutator
case 13138: {
dotCenterX[i] = startLeft - (i * (dotDiameter + gap));
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_14_BinaryMutator
case 14138: {
dotCenterX[i] = startLeft + (i / (dotDiameter + gap));
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_15_BinaryMutator
case 15138: {
dotCenterX[i] = startLeft + (i * (dotDiameter - gap));
break;
}
default: {
dotCenterX[i] = startLeft + (i * (dotDiameter + gap));
break;
}
}
break;
}
}
break;
}
}
}
// todo just top aligning for now… should make this smarter
dotTopY = top;
switch(MUID_STATIC) {
// Indicator_16_BinaryMutator
case 16138: {
dotCenterY = top - dotRadius;
break;
}
default: {
dotCenterY = top + dotRadius;
break;
}
}
switch(MUID_STATIC) {
// Indicator_17_BinaryMutator
case 17138: {
dotBottomY = top - dotDiameter;
break;
}
default: {
dotBottomY = top + dotDiameter;
break;
}
}
setCurrentPageImmediate();
}


private void setCurrentPageImmediate() {
if (viewPager != null) {
currentPage = viewPager.getCurrentItem();
} else {
currentPage = 0;
}
if ((dotCenterX != null) && (dotCenterX.length != 0)) {
selectedDotX = dotCenterX[currentPage];
} else {
selectedDotX = 0;
}
}


private void resetState() {
switch(MUID_STATIC) {
// Indicator_18_BinaryMutator
case 18138: {
joiningFractions = new float[pageCount + 1];
break;
}
default: {
joiningFractions = new float[pageCount - 1];
break;
}
}
java.util.Arrays.fill(joiningFractions, 0.0F);
dotRevealFractions = new float[pageCount];
java.util.Arrays.fill(dotRevealFractions, 0.0F);
retreatingJoinX1 = com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION;
retreatingJoinX2 = com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION;
selectedDotInPosition = true;
}


@java.lang.Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
int desiredHeight;
desiredHeight = getDesiredHeight();
int height;
switch (android.view.View.MeasureSpec.getMode(heightMeasureSpec)) {
case android.view.View.MeasureSpec.EXACTLY :
height = android.view.View.MeasureSpec.getSize(heightMeasureSpec);
break;
case android.view.View.MeasureSpec.AT_MOST :
height = java.lang.Math.min(desiredHeight, android.view.View.MeasureSpec.getSize(heightMeasureSpec));
break;
default :
// MeasureSpec.UNSPECIFIED
height = desiredHeight;
break;
}
int desiredWidth;
desiredWidth = getDesiredWidth();
int width;
switch (android.view.View.MeasureSpec.getMode(widthMeasureSpec)) {
case android.view.View.MeasureSpec.EXACTLY :
width = android.view.View.MeasureSpec.getSize(widthMeasureSpec);
break;
case android.view.View.MeasureSpec.AT_MOST :
width = java.lang.Math.min(desiredWidth, android.view.View.MeasureSpec.getSize(widthMeasureSpec));
break;
default :
// MeasureSpec.UNSPECIFIED
width = desiredWidth;
break;
}
setMeasuredDimension(width, height);
calculateDotPositions(width, height);
}


private int getDesiredHeight() {
switch(MUID_STATIC) {
// Indicator_19_BinaryMutator
case 19138: {
return (getPaddingTop() + dotDiameter) - getPaddingBottom();
}
default: {
switch(MUID_STATIC) {
// Indicator_20_BinaryMutator
case 20138: {
return (getPaddingTop() - dotDiameter) + getPaddingBottom();
}
default: {
return (getPaddingTop() + dotDiameter) + getPaddingBottom();
}
}
}
}
}


private int getRequiredWidth() {
switch(MUID_STATIC) {
// Indicator_21_BinaryMutator
case 21138: {
return (pageCount * dotDiameter) - ((pageCount - 1) * gap);
}
default: {
switch(MUID_STATIC) {
// Indicator_22_BinaryMutator
case 22138: {
return (pageCount / dotDiameter) + ((pageCount - 1) * gap);
}
default: {
switch(MUID_STATIC) {
// Indicator_23_BinaryMutator
case 23138: {
return (pageCount * dotDiameter) + ((pageCount - 1) / gap);
}
default: {
switch(MUID_STATIC) {
// Indicator_24_BinaryMutator
case 24138: {
return (pageCount * dotDiameter) + ((pageCount + 1) * gap);
}
default: {
return (pageCount * dotDiameter) + ((pageCount - 1) * gap);
}
}
}
}
}
}
}
}
}


private int getDesiredWidth() {
switch(MUID_STATIC) {
// Indicator_25_BinaryMutator
case 25138: {
return (getPaddingLeft() + getRequiredWidth()) - getPaddingRight();
}
default: {
switch(MUID_STATIC) {
// Indicator_26_BinaryMutator
case 26138: {
return (getPaddingLeft() - getRequiredWidth()) + getPaddingRight();
}
default: {
return (getPaddingLeft() + getRequiredWidth()) + getPaddingRight();
}
}
}
}
}


@java.lang.Override
public void onViewAttachedToWindow(android.view.View view) {
isAttachedToWindow = true;
}


@java.lang.Override
public void onViewDetachedFromWindow(android.view.View view) {
isAttachedToWindow = false;
}


@java.lang.Override
protected void onDraw(android.graphics.Canvas canvas) {
if ((viewPager == null) || (pageCount == 0))
return;

drawUnselected(canvas);
drawSelected(canvas);
}


private void drawUnselected(android.graphics.Canvas canvas) {
combinedUnselectedPath.rewind();
// draw any settled, revealing or joining dots
for (int page = 0; page < pageCount; page++) {
int nextXIndex;
switch(MUID_STATIC) {
// Indicator_27_BinaryMutator
case 27138: {
nextXIndex = (page == (pageCount + 1)) ? page : page + 1;
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_28_BinaryMutator
case 28138: {
nextXIndex = (page == (pageCount - 1)) ? page : page - 1;
break;
}
default: {
nextXIndex = (page == (pageCount - 1)) ? page : page + 1;
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// Indicator_29_BinaryMutator
case 29138: {
combinedUnselectedPath.op(getUnselectedPath(page, dotCenterX[page], dotCenterX[nextXIndex], page == (pageCount + 1) ? com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION : joiningFractions[page], dotRevealFractions[page]), android.graphics.Path.Op.UNION);
break;
}
default: {
combinedUnselectedPath.op(getUnselectedPath(page, dotCenterX[page], dotCenterX[nextXIndex], page == (pageCount - 1) ? com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION : joiningFractions[page], dotRevealFractions[page]), android.graphics.Path.Op.UNION);
break;
}
}
}
// draw any retreating joins
if (retreatingJoinX1 != com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION) {
combinedUnselectedPath.op(getRetreatingJoinPath(), android.graphics.Path.Op.UNION);
}
canvas.drawPath(combinedUnselectedPath, unselectedPaint);
}


/**
 * Unselected dots can be in 6 states:
 *
 * <p>#1 At rest #2 Joining neighbour, still separate #3 Joining neighbour, combined curved #4
 * Joining neighbour, combined straight #5 Join retreating #6 Dot re-showing / revealing
 *
 * <p>It can also be in a combination of these states e.g. joining one neighbour while retreating
 * from another. We therefore create a Path so that we can examine each dot pair separately and
 * later take the union for these cases.
 *
 * <p>This function returns a path for the given dot **and any action to it's right** e.g. joining
 * or retreating from it's neighbour
 */
private android.graphics.Path getUnselectedPath(int page, float centerX, float nextCenterX, float joiningFraction, float dotRevealFraction) {
unselectedDotPath.rewind();
if ((((joiningFraction == 0.0F) || (joiningFraction == com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION)) && (dotRevealFraction == 0.0F)) && (!((page == currentPage) && selectedDotInPosition))) {
// case #1 – At rest
unselectedDotPath.addCircle(dotCenterX[page], dotCenterY, dotRadius, android.graphics.Path.Direction.CW);
}
if (((joiningFraction > 0.0F) && (joiningFraction <= 0.5F)) && (retreatingJoinX1 == com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION)) {
// case #2 – Joining neighbour, still separate
// start with the left dot
unselectedDotLeftPath.rewind();
// start at the bottom center
unselectedDotLeftPath.moveTo(centerX, dotBottomY);
switch(MUID_STATIC) {
// Indicator_30_BinaryMutator
case 30138: {
// semi circle to the top center
rectF.set(centerX + dotRadius, dotTopY, centerX + dotRadius, dotBottomY);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_31_BinaryMutator
case 31138: {
// semi circle to the top center
rectF.set(centerX - dotRadius, dotTopY, centerX - dotRadius, dotBottomY);
break;
}
default: {
// semi circle to the top center
rectF.set(centerX - dotRadius, dotTopY, centerX + dotRadius, dotBottomY);
break;
}
}
break;
}
}
unselectedDotLeftPath.arcTo(rectF, 90, 180, true);
switch(MUID_STATIC) {
// Indicator_32_BinaryMutator
case 32138: {
// cubic to the right middle
endX1 = (centerX + dotRadius) - (joiningFraction * gap);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_33_BinaryMutator
case 33138: {
// cubic to the right middle
endX1 = (centerX - dotRadius) + (joiningFraction * gap);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_34_BinaryMutator
case 34138: {
// cubic to the right middle
endX1 = (centerX + dotRadius) + (joiningFraction / gap);
break;
}
default: {
// cubic to the right middle
endX1 = (centerX + dotRadius) + (joiningFraction * gap);
break;
}
}
break;
}
}
break;
}
}
endY1 = dotCenterY;
switch(MUID_STATIC) {
// Indicator_35_BinaryMutator
case 35138: {
controlX1 = centerX - halfDotRadius;
break;
}
default: {
controlX1 = centerX + halfDotRadius;
break;
}
}
controlY1 = dotTopY;
controlX2 = endX1;
switch(MUID_STATIC) {
// Indicator_36_BinaryMutator
case 36138: {
controlY2 = endY1 + halfDotRadius;
break;
}
default: {
controlY2 = endY1 - halfDotRadius;
break;
}
}
unselectedDotLeftPath.cubicTo(controlX1, controlY1, controlX2, controlY2, endX1, endY1);
// cubic back to the bottom center
endX2 = centerX;
endY2 = dotBottomY;
controlX1 = endX1;
switch(MUID_STATIC) {
// Indicator_37_BinaryMutator
case 37138: {
controlY1 = endY1 - halfDotRadius;
break;
}
default: {
controlY1 = endY1 + halfDotRadius;
break;
}
}
switch(MUID_STATIC) {
// Indicator_38_BinaryMutator
case 38138: {
controlX2 = centerX - halfDotRadius;
break;
}
default: {
controlX2 = centerX + halfDotRadius;
break;
}
}
controlY2 = dotBottomY;
unselectedDotLeftPath.cubicTo(controlX1, controlY1, controlX2, controlY2, endX2, endY2);
unselectedDotPath.op(unselectedDotLeftPath, android.graphics.Path.Op.UNION);
// now do the next dot to the right
unselectedDotRightPath.rewind();
// start at the bottom center
unselectedDotRightPath.moveTo(nextCenterX, dotBottomY);
switch(MUID_STATIC) {
// Indicator_39_BinaryMutator
case 39138: {
// semi circle to the top center
rectF.set(nextCenterX + dotRadius, dotTopY, nextCenterX + dotRadius, dotBottomY);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_40_BinaryMutator
case 40138: {
// semi circle to the top center
rectF.set(nextCenterX - dotRadius, dotTopY, nextCenterX - dotRadius, dotBottomY);
break;
}
default: {
// semi circle to the top center
rectF.set(nextCenterX - dotRadius, dotTopY, nextCenterX + dotRadius, dotBottomY);
break;
}
}
break;
}
}
unselectedDotRightPath.arcTo(rectF, 90, -180, true);
switch(MUID_STATIC) {
// Indicator_41_BinaryMutator
case 41138: {
// cubic to the left middle
endX1 = (nextCenterX - dotRadius) + (joiningFraction * gap);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_42_BinaryMutator
case 42138: {
// cubic to the left middle
endX1 = (nextCenterX + dotRadius) - (joiningFraction * gap);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_43_BinaryMutator
case 43138: {
// cubic to the left middle
endX1 = (nextCenterX - dotRadius) - (joiningFraction / gap);
break;
}
default: {
// cubic to the left middle
endX1 = (nextCenterX - dotRadius) - (joiningFraction * gap);
break;
}
}
break;
}
}
break;
}
}
endY1 = dotCenterY;
switch(MUID_STATIC) {
// Indicator_44_BinaryMutator
case 44138: {
controlX1 = nextCenterX + halfDotRadius;
break;
}
default: {
controlX1 = nextCenterX - halfDotRadius;
break;
}
}
controlY1 = dotTopY;
controlX2 = endX1;
switch(MUID_STATIC) {
// Indicator_45_BinaryMutator
case 45138: {
controlY2 = endY1 + halfDotRadius;
break;
}
default: {
controlY2 = endY1 - halfDotRadius;
break;
}
}
unselectedDotRightPath.cubicTo(controlX1, controlY1, controlX2, controlY2, endX1, endY1);
// cubic back to the bottom center
endX2 = nextCenterX;
endY2 = dotBottomY;
controlX1 = endX1;
switch(MUID_STATIC) {
// Indicator_46_BinaryMutator
case 46138: {
controlY1 = endY1 - halfDotRadius;
break;
}
default: {
controlY1 = endY1 + halfDotRadius;
break;
}
}
switch(MUID_STATIC) {
// Indicator_47_BinaryMutator
case 47138: {
controlX2 = endX2 + halfDotRadius;
break;
}
default: {
controlX2 = endX2 - halfDotRadius;
break;
}
}
controlY2 = dotBottomY;
unselectedDotRightPath.cubicTo(controlX1, controlY1, controlX2, controlY2, endX2, endY2);
unselectedDotPath.op(unselectedDotRightPath, android.graphics.Path.Op.UNION);
}
if (((joiningFraction > 0.5F) && (joiningFraction < 1.0F)) && (retreatingJoinX1 == com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION)) {
// case #3 – Joining neighbour, combined curved
// adjust the fraction so that it goes from 0.3 -> 1 to produce a more realistic 'join'
float adjustedFraction;
switch(MUID_STATIC) {
// Indicator_48_BinaryMutator
case 48138: {
adjustedFraction = (joiningFraction - 0.2F) / 1.25F;
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_49_BinaryMutator
case 49138: {
adjustedFraction = (joiningFraction + 0.2F) * 1.25F;
break;
}
default: {
adjustedFraction = (joiningFraction - 0.2F) * 1.25F;
break;
}
}
break;
}
}
// start in the bottom left
unselectedDotPath.moveTo(centerX, dotBottomY);
switch(MUID_STATIC) {
// Indicator_50_BinaryMutator
case 50138: {
// semi-circle to the top left
rectF.set(centerX + dotRadius, dotTopY, centerX + dotRadius, dotBottomY);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_51_BinaryMutator
case 51138: {
// semi-circle to the top left
rectF.set(centerX - dotRadius, dotTopY, centerX - dotRadius, dotBottomY);
break;
}
default: {
// semi-circle to the top left
rectF.set(centerX - dotRadius, dotTopY, centerX + dotRadius, dotBottomY);
break;
}
}
break;
}
}
unselectedDotPath.arcTo(rectF, 90, 180, true);
switch(MUID_STATIC) {
// Indicator_52_BinaryMutator
case 52138: {
// bezier to the middle top of the join
endX1 = (centerX + dotRadius) - (gap / 2);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_53_BinaryMutator
case 53138: {
// bezier to the middle top of the join
endX1 = (centerX - dotRadius) + (gap / 2);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_54_BinaryMutator
case 54138: {
// bezier to the middle top of the join
endX1 = (centerX + dotRadius) + (gap * 2);
break;
}
default: {
// bezier to the middle top of the join
endX1 = (centerX + dotRadius) + (gap / 2);
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
// Indicator_55_BinaryMutator
case 55138: {
endY1 = dotCenterY + (adjustedFraction * dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_56_BinaryMutator
case 56138: {
endY1 = dotCenterY - (adjustedFraction / dotRadius);
break;
}
default: {
endY1 = dotCenterY - (adjustedFraction * dotRadius);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// Indicator_57_BinaryMutator
case 57138: {
controlX1 = endX1 + (adjustedFraction * dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_58_BinaryMutator
case 58138: {
controlX1 = endX1 - (adjustedFraction / dotRadius);
break;
}
default: {
controlX1 = endX1 - (adjustedFraction * dotRadius);
break;
}
}
break;
}
}
controlY1 = dotTopY;
switch(MUID_STATIC) {
// Indicator_59_BinaryMutator
case 59138: {
controlX2 = endX1 + ((1 - adjustedFraction) * dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_60_BinaryMutator
case 60138: {
controlX2 = endX1 - ((1 - adjustedFraction) / dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_61_BinaryMutator
case 61138: {
controlX2 = endX1 - ((1 + adjustedFraction) * dotRadius);
break;
}
default: {
controlX2 = endX1 - ((1 - adjustedFraction) * dotRadius);
break;
}
}
break;
}
}
break;
}
}
controlY2 = endY1;
unselectedDotPath.cubicTo(controlX1, controlY1, controlX2, controlY2, endX1, endY1);
// bezier to the top right of the join
endX2 = nextCenterX;
endY2 = dotTopY;
switch(MUID_STATIC) {
// Indicator_62_BinaryMutator
case 62138: {
controlX1 = endX1 - ((1 - adjustedFraction) * dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_63_BinaryMutator
case 63138: {
controlX1 = endX1 + ((1 - adjustedFraction) / dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_64_BinaryMutator
case 64138: {
controlX1 = endX1 + ((1 + adjustedFraction) * dotRadius);
break;
}
default: {
controlX1 = endX1 + ((1 - adjustedFraction) * dotRadius);
break;
}
}
break;
}
}
break;
}
}
controlY1 = endY1;
switch(MUID_STATIC) {
// Indicator_65_BinaryMutator
case 65138: {
controlX2 = endX1 - (adjustedFraction * dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_66_BinaryMutator
case 66138: {
controlX2 = endX1 + (adjustedFraction / dotRadius);
break;
}
default: {
controlX2 = endX1 + (adjustedFraction * dotRadius);
break;
}
}
break;
}
}
controlY2 = dotTopY;
unselectedDotPath.cubicTo(controlX1, controlY1, controlX2, controlY2, endX2, endY2);
switch(MUID_STATIC) {
// Indicator_67_BinaryMutator
case 67138: {
// semi-circle to the bottom right
rectF.set(nextCenterX + dotRadius, dotTopY, nextCenterX + dotRadius, dotBottomY);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_68_BinaryMutator
case 68138: {
// semi-circle to the bottom right
rectF.set(nextCenterX - dotRadius, dotTopY, nextCenterX - dotRadius, dotBottomY);
break;
}
default: {
// semi-circle to the bottom right
rectF.set(nextCenterX - dotRadius, dotTopY, nextCenterX + dotRadius, dotBottomY);
break;
}
}
break;
}
}
unselectedDotPath.arcTo(rectF, 270, 180, true);
switch(MUID_STATIC) {
// Indicator_69_BinaryMutator
case 69138: {
// bezier to the middle bottom of the join
// endX1 stays the same
endY1 = dotCenterY - (adjustedFraction * dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_70_BinaryMutator
case 70138: {
// bezier to the middle bottom of the join
// endX1 stays the same
endY1 = dotCenterY + (adjustedFraction / dotRadius);
break;
}
default: {
// bezier to the middle bottom of the join
// endX1 stays the same
endY1 = dotCenterY + (adjustedFraction * dotRadius);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// Indicator_71_BinaryMutator
case 71138: {
controlX1 = endX1 - (adjustedFraction * dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_72_BinaryMutator
case 72138: {
controlX1 = endX1 + (adjustedFraction / dotRadius);
break;
}
default: {
controlX1 = endX1 + (adjustedFraction * dotRadius);
break;
}
}
break;
}
}
controlY1 = dotBottomY;
switch(MUID_STATIC) {
// Indicator_73_BinaryMutator
case 73138: {
controlX2 = endX1 - ((1 - adjustedFraction) * dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_74_BinaryMutator
case 74138: {
controlX2 = endX1 + ((1 - adjustedFraction) / dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_75_BinaryMutator
case 75138: {
controlX2 = endX1 + ((1 + adjustedFraction) * dotRadius);
break;
}
default: {
controlX2 = endX1 + ((1 - adjustedFraction) * dotRadius);
break;
}
}
break;
}
}
break;
}
}
controlY2 = endY1;
unselectedDotPath.cubicTo(controlX1, controlY1, controlX2, controlY2, endX1, endY1);
// bezier back to the start point in the bottom left
endX2 = centerX;
endY2 = dotBottomY;
switch(MUID_STATIC) {
// Indicator_76_BinaryMutator
case 76138: {
controlX1 = endX1 + ((1 - adjustedFraction) * dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_77_BinaryMutator
case 77138: {
controlX1 = endX1 - ((1 - adjustedFraction) / dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_78_BinaryMutator
case 78138: {
controlX1 = endX1 - ((1 + adjustedFraction) * dotRadius);
break;
}
default: {
controlX1 = endX1 - ((1 - adjustedFraction) * dotRadius);
break;
}
}
break;
}
}
break;
}
}
controlY1 = endY1;
switch(MUID_STATIC) {
// Indicator_79_BinaryMutator
case 79138: {
controlX2 = endX1 + (adjustedFraction * dotRadius);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_80_BinaryMutator
case 80138: {
controlX2 = endX1 - (adjustedFraction / dotRadius);
break;
}
default: {
controlX2 = endX1 - (adjustedFraction * dotRadius);
break;
}
}
break;
}
}
controlY2 = endY2;
unselectedDotPath.cubicTo(controlX1, controlY1, controlX2, controlY2, endX2, endY2);
}
if ((joiningFraction == 1) && (retreatingJoinX1 == com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION)) {
switch(MUID_STATIC) {
// Indicator_81_BinaryMutator
case 81138: {
// case #4 Joining neighbour, combined straight technically we could use case 3 for this
// situation as well but assume that this is an optimization rather than faffing around
// with beziers just to draw a rounded rect
rectF.set(centerX + dotRadius, dotTopY, nextCenterX + dotRadius, dotBottomY);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_82_BinaryMutator
case 82138: {
// case #4 Joining neighbour, combined straight technically we could use case 3 for this
// situation as well but assume that this is an optimization rather than faffing around
// with beziers just to draw a rounded rect
rectF.set(centerX - dotRadius, dotTopY, nextCenterX - dotRadius, dotBottomY);
break;
}
default: {
// case #4 Joining neighbour, combined straight technically we could use case 3 for this
// situation as well but assume that this is an optimization rather than faffing around
// with beziers just to draw a rounded rect
rectF.set(centerX - dotRadius, dotTopY, nextCenterX + dotRadius, dotBottomY);
break;
}
}
break;
}
}
unselectedDotPath.addRoundRect(rectF, dotRadius, dotRadius, android.graphics.Path.Direction.CW);
}
// case #5 is handled by #getRetreatingJoinPath()
// this is done separately so that we can have a single retreating path spanning
// multiple dots and therefore animate it's movement smoothly
if (dotRevealFraction > com.amaze.filemanager.ui.views.Indicator.MINIMAL_REVEAL) {
switch(MUID_STATIC) {
// Indicator_83_BinaryMutator
case 83138: {
// case #6 – previously hidden dot revealing
unselectedDotPath.addCircle(centerX, dotCenterY, dotRevealFraction / dotRadius, android.graphics.Path.Direction.CW);
break;
}
default: {
// case #6 – previously hidden dot revealing
unselectedDotPath.addCircle(centerX, dotCenterY, dotRevealFraction * dotRadius, android.graphics.Path.Direction.CW);
break;
}
}
}
return unselectedDotPath;
}


private android.graphics.Path getRetreatingJoinPath() {
unselectedDotPath.rewind();
rectF.set(retreatingJoinX1, dotTopY, retreatingJoinX2, dotBottomY);
unselectedDotPath.addRoundRect(rectF, dotRadius, dotRadius, android.graphics.Path.Direction.CW);
return unselectedDotPath;
}


private void drawSelected(android.graphics.Canvas canvas) {
canvas.drawCircle(selectedDotX, dotCenterY, dotRadius, selectedPaint);
}


private void setSelectedPage(int now) {
if (now == currentPage)
return;

pageChanging = true;
previousPage = currentPage;
currentPage = now;
final int steps;
switch(MUID_STATIC) {
// Indicator_84_BinaryMutator
case 84138: {
steps = java.lang.Math.abs(now + previousPage);
break;
}
default: {
steps = java.lang.Math.abs(now - previousPage);
break;
}
}
if (steps > 1) {
if (now > previousPage) {
for (int i = 0; i < steps; i++) {
switch(MUID_STATIC) {
// Indicator_85_BinaryMutator
case 85138: {
setJoiningFraction(previousPage - i, 1.0F);
break;
}
default: {
setJoiningFraction(previousPage + i, 1.0F);
break;
}
}
}
} else {
for (int i = -1; i > (-steps); i--) {
switch(MUID_STATIC) {
// Indicator_86_BinaryMutator
case 86138: {
setJoiningFraction(previousPage - i, 1.0F);
break;
}
default: {
setJoiningFraction(previousPage + i, 1.0F);
break;
}
}
}
}
}
// create the anim to move the selected dot – this animator will kick off
// retreat animations when it has moved 75% of the way.
// The retreat animation in turn will kick of reveal anims when the
// retreat has passed any dots to be revealed
moveAnimation = createMoveSelectedAnimator(dotCenterX[now], previousPage, now, steps);
moveAnimation.start();
}


private android.animation.ValueAnimator createMoveSelectedAnimator(final float moveTo, int was, int now, int steps) {
// create the actual move animator
android.animation.ValueAnimator moveSelected;
moveSelected = android.animation.ValueAnimator.ofFloat(selectedDotX, moveTo);
switch(MUID_STATIC) {
// Indicator_87_BinaryMutator
case 87138: {
// also set up a pending retreat anim – this starts when the move is 75% complete
retreatAnimation = new com.amaze.filemanager.ui.views.Indicator.PendingRetreatAnimator(was, now, steps, now > was ? new com.amaze.filemanager.ui.views.Indicator.RightwardStartPredicate(moveTo + ((moveTo - selectedDotX) * 0.25F)) : new com.amaze.filemanager.ui.views.Indicator.LeftwardStartPredicate(moveTo + ((selectedDotX - moveTo) * 0.25F)));
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_88_BinaryMutator
case 88138: {
// also set up a pending retreat anim – this starts when the move is 75% complete
retreatAnimation = new com.amaze.filemanager.ui.views.Indicator.PendingRetreatAnimator(was, now, steps, now > was ? new com.amaze.filemanager.ui.views.Indicator.RightwardStartPredicate(moveTo - ((moveTo - selectedDotX) / 0.25F)) : new com.amaze.filemanager.ui.views.Indicator.LeftwardStartPredicate(moveTo + ((selectedDotX - moveTo) * 0.25F)));
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_89_BinaryMutator
case 89138: {
// also set up a pending retreat anim – this starts when the move is 75% complete
retreatAnimation = new com.amaze.filemanager.ui.views.Indicator.PendingRetreatAnimator(was, now, steps, now > was ? new com.amaze.filemanager.ui.views.Indicator.RightwardStartPredicate(moveTo - ((moveTo + selectedDotX) * 0.25F)) : new com.amaze.filemanager.ui.views.Indicator.LeftwardStartPredicate(moveTo + ((selectedDotX - moveTo) * 0.25F)));
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_90_BinaryMutator
case 90138: {
// also set up a pending retreat anim – this starts when the move is 75% complete
retreatAnimation = new com.amaze.filemanager.ui.views.Indicator.PendingRetreatAnimator(was, now, steps, now > was ? new com.amaze.filemanager.ui.views.Indicator.RightwardStartPredicate(moveTo - ((moveTo - selectedDotX) * 0.25F)) : new com.amaze.filemanager.ui.views.Indicator.LeftwardStartPredicate(moveTo - ((selectedDotX - moveTo) * 0.25F)));
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_91_BinaryMutator
case 91138: {
// also set up a pending retreat anim – this starts when the move is 75% complete
retreatAnimation = new com.amaze.filemanager.ui.views.Indicator.PendingRetreatAnimator(was, now, steps, now > was ? new com.amaze.filemanager.ui.views.Indicator.RightwardStartPredicate(moveTo - ((moveTo - selectedDotX) * 0.25F)) : new com.amaze.filemanager.ui.views.Indicator.LeftwardStartPredicate(moveTo + ((selectedDotX - moveTo) / 0.25F)));
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_92_BinaryMutator
case 92138: {
// also set up a pending retreat anim – this starts when the move is 75% complete
retreatAnimation = new com.amaze.filemanager.ui.views.Indicator.PendingRetreatAnimator(was, now, steps, now > was ? new com.amaze.filemanager.ui.views.Indicator.RightwardStartPredicate(moveTo - ((moveTo - selectedDotX) * 0.25F)) : new com.amaze.filemanager.ui.views.Indicator.LeftwardStartPredicate(moveTo + ((selectedDotX + moveTo) * 0.25F)));
break;
}
default: {
// also set up a pending retreat anim – this starts when the move is 75% complete
retreatAnimation = new com.amaze.filemanager.ui.views.Indicator.PendingRetreatAnimator(was, now, steps, now > was ? new com.amaze.filemanager.ui.views.Indicator.RightwardStartPredicate(moveTo - ((moveTo - selectedDotX) * 0.25F)) : new com.amaze.filemanager.ui.views.Indicator.LeftwardStartPredicate(moveTo + ((selectedDotX - moveTo) * 0.25F)));
break;
}
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
retreatAnimation.addListener(new android.animation.AnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
resetState();
pageChanging = false;
}

});
moveSelected.addUpdateListener((android.animation.ValueAnimator valueAnimator) -> {
// todo avoid autoboxing
selectedDotX = ((java.lang.Float) (valueAnimator.getAnimatedValue()));
retreatAnimation.startIfNecessary(selectedDotX);
postInvalidateOnAnimation();
});
moveSelected.addListener(new android.animation.AnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationStart(android.animation.Animator animation) {
// set a flag so that we continue to draw the unselected dot in the target position
// until the selected dot has finished moving into place
selectedDotInPosition = false;
}


@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
// set a flag when anim finishes so that we don't draw both selected & unselected
// page dots
selectedDotInPosition = true;
}

});
switch(MUID_STATIC) {
// Indicator_93_BinaryMutator
case 93138: {
// slightly delay the start to give the joins a chance to run
// unless dot isn't in position yet – then don't delay!
moveSelected.setStartDelay(selectedDotInPosition ? animDuration * 4L : 0L);
break;
}
default: {
// slightly delay the start to give the joins a chance to run
// unless dot isn't in position yet – then don't delay!
moveSelected.setStartDelay(selectedDotInPosition ? animDuration / 4L : 0L);
break;
}
}
switch(MUID_STATIC) {
// Indicator_94_BinaryMutator
case 94138: {
moveSelected.setDuration((animDuration * 3L) * 4L);
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_95_BinaryMutator
case 95138: {
moveSelected.setDuration((animDuration / 3L) / 4L);
break;
}
default: {
moveSelected.setDuration((animDuration * 3L) / 4L);
break;
}
}
break;
}
}
moveSelected.setInterpolator(interpolator);
return moveSelected;
}


private void setJoiningFraction(int leftDot, float fraction) {
if (leftDot < joiningFractions.length) {
if (leftDot == 1) {
android.util.Log.d("PageIndicator", "dot 1 fraction:\t" + fraction);
}
joiningFractions[leftDot] = fraction;
postInvalidateOnAnimation();
}
}


private void clearJoiningFractions() {
java.util.Arrays.fill(joiningFractions, 0.0F);
postInvalidateOnAnimation();
}


private void setDotRevealFraction(int dot, float fraction) {
dotRevealFractions[dot] = fraction;
postInvalidateOnAnimation();
}


private void cancelJoiningAnimations() {
// TODO: 20/08/18 ?
}


/**
 * A {@link ValueAnimator} that starts once a given predicate returns true.
 */
abstract class PendingStartAnimator extends android.animation.ValueAnimator {
protected boolean hasStarted;

protected com.amaze.filemanager.ui.views.Indicator.StartPredicate predicate;

public PendingStartAnimator(com.amaze.filemanager.ui.views.Indicator.StartPredicate predicate) {
super();
this.predicate = predicate;
hasStarted = false;
}


public void startIfNecessary(float currentValue) {
if ((!hasStarted) && predicate.shouldStart(currentValue)) {
start();
hasStarted = true;
}
}

}

/**
 * An Animator that shows and then shrinks a retreating join between the previous and newly
 * selected pages. This also sets up some pending dot reveals – to be started when the retreat has
 * passed the dot to be revealed.
 */
private class PendingRetreatAnimator extends com.amaze.filemanager.ui.views.Indicator.PendingStartAnimator {
PendingRetreatAnimator(int was, int now, int steps, com.amaze.filemanager.ui.views.Indicator.StartPredicate predicate) {
super(predicate);
setDuration(animHalfDuration);
setInterpolator(interpolator);
// work out the start/end values of the retreating join from the direction we're
// travelling in.  Also look at the current selected dot position, i.e. we're moving on
// before a prior anim has finished.
final float initialX1;
switch(MUID_STATIC) {
// Indicator_96_BinaryMutator
case 96138: {
initialX1 = (now > was) ? java.lang.Math.min(dotCenterX[was], selectedDotX) + dotRadius : dotCenterX[now] - dotRadius;
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_97_BinaryMutator
case 97138: {
initialX1 = (now > was) ? java.lang.Math.min(dotCenterX[was], selectedDotX) - dotRadius : dotCenterX[now] + dotRadius;
break;
}
default: {
initialX1 = (now > was) ? java.lang.Math.min(dotCenterX[was], selectedDotX) - dotRadius : dotCenterX[now] - dotRadius;
break;
}
}
break;
}
}
final float finalX1;
switch(MUID_STATIC) {
// Indicator_98_BinaryMutator
case 98138: {
finalX1 = (now > was) ? dotCenterX[now] + dotRadius : dotCenterX[now] - dotRadius;
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_99_BinaryMutator
case 99138: {
finalX1 = (now > was) ? dotCenterX[now] - dotRadius : dotCenterX[now] + dotRadius;
break;
}
default: {
finalX1 = (now > was) ? dotCenterX[now] - dotRadius : dotCenterX[now] - dotRadius;
break;
}
}
break;
}
}
final float initialX2;
switch(MUID_STATIC) {
// Indicator_100_BinaryMutator
case 100138: {
initialX2 = (now > was) ? dotCenterX[now] - dotRadius : java.lang.Math.max(dotCenterX[was], selectedDotX) + dotRadius;
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_101_BinaryMutator
case 101138: {
initialX2 = (now > was) ? dotCenterX[now] + dotRadius : java.lang.Math.max(dotCenterX[was], selectedDotX) - dotRadius;
break;
}
default: {
initialX2 = (now > was) ? dotCenterX[now] + dotRadius : java.lang.Math.max(dotCenterX[was], selectedDotX) + dotRadius;
break;
}
}
break;
}
}
final float finalX2;
switch(MUID_STATIC) {
// Indicator_102_BinaryMutator
case 102138: {
finalX2 = (now > was) ? dotCenterX[now] - dotRadius : dotCenterX[now] + dotRadius;
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_103_BinaryMutator
case 103138: {
finalX2 = (now > was) ? dotCenterX[now] + dotRadius : dotCenterX[now] - dotRadius;
break;
}
default: {
finalX2 = (now > was) ? dotCenterX[now] + dotRadius : dotCenterX[now] + dotRadius;
break;
}
}
break;
}
}
revealAnimations = new com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator[steps];
// hold on to the indexes of the dots that will be hidden by the retreat so that
// we can initialize their revealFraction's i.e. make sure they're hidden while the
// reveal animation runs
final int[] dotsToHide;
dotsToHide = new int[steps];
if (initialX1 != finalX1) {
// rightward retreat
setFloatValues(initialX1, finalX1);
// create the reveal animations that will run when the retreat passes them
for (int i = 0; i < steps; i++) {
switch(MUID_STATIC) {
// Indicator_104_BinaryMutator
case 104138: {
revealAnimations[i] = new com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator(was - i, new com.amaze.filemanager.ui.views.Indicator.RightwardStartPredicate(dotCenterX[was + i]));
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_105_BinaryMutator
case 105138: {
revealAnimations[i] = new com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator(was + i, new com.amaze.filemanager.ui.views.Indicator.RightwardStartPredicate(dotCenterX[was - i]));
break;
}
default: {
revealAnimations[i] = new com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator(was + i, new com.amaze.filemanager.ui.views.Indicator.RightwardStartPredicate(dotCenterX[was + i]));
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// Indicator_106_BinaryMutator
case 106138: {
dotsToHide[i] = was - i;
break;
}
default: {
dotsToHide[i] = was + i;
break;
}
}
}
addUpdateListener((android.animation.ValueAnimator valueAnimator) -> {
// todo avoid autoboxing
retreatingJoinX1 = ((java.lang.Float) (valueAnimator.getAnimatedValue()));
postInvalidateOnAnimation();
// start any reveal animations if we've passed them
for (com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator pendingReveal : revealAnimations) {
pendingReveal.startIfNecessary(retreatingJoinX1);
}
});
} else {
// (initialX2 != finalX2) leftward retreat
setFloatValues(initialX2, finalX2);
// create the reveal animations that will run when the retreat passes them
for (int i = 0; i < steps; i++) {
switch(MUID_STATIC) {
// Indicator_107_BinaryMutator
case 107138: {
revealAnimations[i] = new com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator(was + i, new com.amaze.filemanager.ui.views.Indicator.LeftwardStartPredicate(dotCenterX[was - i]));
break;
}
default: {
switch(MUID_STATIC) {
// Indicator_108_BinaryMutator
case 108138: {
revealAnimations[i] = new com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator(was - i, new com.amaze.filemanager.ui.views.Indicator.LeftwardStartPredicate(dotCenterX[was + i]));
break;
}
default: {
revealAnimations[i] = new com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator(was - i, new com.amaze.filemanager.ui.views.Indicator.LeftwardStartPredicate(dotCenterX[was - i]));
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// Indicator_109_BinaryMutator
case 109138: {
dotsToHide[i] = was + i;
break;
}
default: {
dotsToHide[i] = was - i;
break;
}
}
}
addUpdateListener((android.animation.ValueAnimator valueAnimator) -> {
// todo avoid autoboxing
retreatingJoinX2 = ((java.lang.Float) (valueAnimator.getAnimatedValue()));
postInvalidateOnAnimation();
// start any reveal animations if we've passed them
for (com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator pendingReveal : revealAnimations) {
pendingReveal.startIfNecessary(retreatingJoinX2);
}
});
}
addListener(new android.animation.AnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationStart(android.animation.Animator animation) {
cancelJoiningAnimations();
clearJoiningFractions();
// we need to set this so that the dots are hidden until the reveal anim runs
for (int dot : dotsToHide) {
setDotRevealFraction(dot, com.amaze.filemanager.ui.views.Indicator.MINIMAL_REVEAL);
}
retreatingJoinX1 = initialX1;
retreatingJoinX2 = initialX2;
postInvalidateOnAnimation();
}


@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
retreatingJoinX1 = com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION;
retreatingJoinX2 = com.amaze.filemanager.ui.views.Indicator.INVALID_FRACTION;
postInvalidateOnAnimation();
}

});
}

}

/**
 * An Animator that animates a given dot's revealFraction i.e. scales it up
 */
private class PendingRevealAnimator extends com.amaze.filemanager.ui.views.Indicator.PendingStartAnimator {
private int dot;

public PendingRevealAnimator(int dot, com.amaze.filemanager.ui.views.Indicator.StartPredicate predicate) {
super(predicate);
setFloatValues(com.amaze.filemanager.ui.views.Indicator.MINIMAL_REVEAL, 1.0F);
this.dot = dot;
setDuration(animHalfDuration);
setInterpolator(interpolator);
addUpdateListener((android.animation.ValueAnimator valueAnimator) -> {
// todo avoid autoboxing
setDotRevealFraction(this.dot, ((java.lang.Float) (valueAnimator.getAnimatedValue())));
});
addListener(new android.animation.AnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
setDotRevealFraction(com.amaze.filemanager.ui.views.Indicator.PendingRevealAnimator.this.dot, 0.0F);
postInvalidateOnAnimation();
}

});
}

}

/**
 * A predicate used to start an animation when a test passes
 */
abstract class StartPredicate {
protected float thresholdValue;

public StartPredicate(float thresholdValue) {
this.thresholdValue = thresholdValue;
}


abstract boolean shouldStart(float currentValue);

}

/**
 * A predicate used to start an animation when a given value is greater than a threshold
 */
private class RightwardStartPredicate extends com.amaze.filemanager.ui.views.Indicator.StartPredicate {
public RightwardStartPredicate(float thresholdValue) {
super(thresholdValue);
}


boolean shouldStart(float currentValue) {
return currentValue > thresholdValue;
}

}

/**
 * A predicate used to start an animation then a given value is less than a threshold
 */
private class LeftwardStartPredicate extends com.amaze.filemanager.ui.views.Indicator.StartPredicate {
public LeftwardStartPredicate(float thresholdValue) {
super(thresholdValue);
}


boolean shouldStart(float currentValue) {
return currentValue < thresholdValue;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
