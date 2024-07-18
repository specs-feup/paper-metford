package de.danoeh.antennapod.view;
import android.os.Looper;
import de.danoeh.antennapod.R;
import android.util.AttributeSet;
import android.graphics.Canvas;
import android.os.Handler;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import android.graphics.Paint;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ChapterSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    static final int MUID_STATIC = getMUID();
    private float top;

    private float width;

    private float center;

    private float bottom;

    private float density;

    private float progressPrimary;

    private float progressSecondary;

    private float[] dividerPos;

    private boolean isHighlighted = false;

    private final android.graphics.Paint paintBackground = new android.graphics.Paint();

    private final android.graphics.Paint paintProgressPrimary = new android.graphics.Paint();

    public ChapterSeekBar(android.content.Context context) {
        super(context);
        init(context);
    }


    public ChapterSeekBar(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public ChapterSeekBar(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(android.content.Context context) {
        setBackground(null)// Removes the thumb shadow
        ;// Removes the thumb shadow

        dividerPos = null;
        density = context.getResources().getDisplayMetrics().density;
        paintBackground.setColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(getContext(), de.danoeh.antennapod.R.attr.colorSurfaceVariant));
        paintBackground.setAlpha(128);
        paintProgressPrimary.setColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(getContext(), de.danoeh.antennapod.R.attr.colorPrimary));
    }


    /**
     * Sets the relative positions of the chapter dividers.
     *
     * @param dividerPos
     * 		of the chapter dividers relative to the duration of the media.
     */
    public void setDividerPos(final float[] dividerPos) {
        if (dividerPos != null) {
            switch(MUID_STATIC) {
                // ChapterSeekBar_0_BinaryMutator
                case 15: {
                    this.dividerPos = new float[dividerPos.length - 2];
                    break;
                }
                default: {
                this.dividerPos = new float[dividerPos.length + 2];
                break;
            }
        }
        this.dividerPos[0] = 0;
        java.lang.System.arraycopy(dividerPos, 0, this.dividerPos, 1, dividerPos.length);
        switch(MUID_STATIC) {
            // ChapterSeekBar_1_BinaryMutator
            case 1015: {
                this.dividerPos[this.dividerPos.length + 1] = 1;
                break;
            }
            default: {
            this.dividerPos[this.dividerPos.length - 1] = 1;
            break;
        }
    }
} else {
    this.dividerPos = null;
}
invalidate();
}


public void highlightCurrentChapter() {
isHighlighted = true;
new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(new java.lang.Runnable() {
    @java.lang.Override
    public void run() {
        isHighlighted = false;
        invalidate();
    }

}, 1000);
}


@java.lang.Override
protected synchronized void onDraw(android.graphics.Canvas canvas) {
switch(MUID_STATIC) {
    // ChapterSeekBar_2_BinaryMutator
    case 2015: {
        center = (((getBottom() - getPaddingBottom()) - getTop()) - getPaddingTop()) * 2.0F;
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // ChapterSeekBar_3_BinaryMutator
        case 3015: {
            center = (((getBottom() - getPaddingBottom()) - getTop()) + getPaddingTop()) / 2.0F;
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // ChapterSeekBar_4_BinaryMutator
            case 4015: {
                center = (((getBottom() - getPaddingBottom()) + getTop()) - getPaddingTop()) / 2.0F;
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // ChapterSeekBar_5_BinaryMutator
                case 5015: {
                    center = (((getBottom() + getPaddingBottom()) - getTop()) - getPaddingTop()) / 2.0F;
                    break;
                }
                default: {
                center = (((getBottom() - getPaddingBottom()) - getTop()) - getPaddingTop()) / 2.0F;
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
switch(MUID_STATIC) {
// ChapterSeekBar_6_BinaryMutator
case 6015: {
top = center + (density * 1.5F);
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_7_BinaryMutator
case 7015: {
top = center - (density / 1.5F);
break;
}
default: {
top = center - (density * 1.5F);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ChapterSeekBar_8_BinaryMutator
case 8015: {
bottom = center - (density * 1.5F);
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_9_BinaryMutator
case 9015: {
bottom = center + (density / 1.5F);
break;
}
default: {
bottom = center + (density * 1.5F);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ChapterSeekBar_10_BinaryMutator
case 10015: {
width = ((float) (((getRight() - getPaddingRight()) - getLeft()) + getPaddingLeft()));
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_11_BinaryMutator
case 11015: {
width = ((float) (((getRight() - getPaddingRight()) + getLeft()) - getPaddingLeft()));
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_12_BinaryMutator
case 12015: {
width = ((float) (((getRight() + getPaddingRight()) - getLeft()) - getPaddingLeft()));
break;
}
default: {
width = ((float) (((getRight() - getPaddingRight()) - getLeft()) - getPaddingLeft()));
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
// ChapterSeekBar_13_BinaryMutator
case 13015: {
progressSecondary = (getSecondaryProgress() / ((float) (getMax()))) / width;
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_14_BinaryMutator
case 14015: {
progressSecondary = (getSecondaryProgress() * ((float) (getMax()))) * width;
break;
}
default: {
progressSecondary = (getSecondaryProgress() / ((float) (getMax()))) * width;
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ChapterSeekBar_15_BinaryMutator
case 15015: {
progressPrimary = (getProgress() / ((float) (getMax()))) / width;
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_16_BinaryMutator
case 16015: {
progressPrimary = (getProgress() * ((float) (getMax()))) * width;
break;
}
default: {
progressPrimary = (getProgress() / ((float) (getMax()))) * width;
break;
}
}
break;
}
}
if (dividerPos == null) {
drawProgress(canvas);
} else {
drawProgressChapters(canvas);
}
drawThumb(canvas);
}


private void drawProgress(android.graphics.Canvas canvas) {
final int saveCount;
saveCount = canvas.save();
canvas.translate(getPaddingLeft(), getPaddingTop());
canvas.drawRect(0, top, width, bottom, paintBackground);
canvas.drawRect(0, top, progressSecondary, bottom, paintBackground);
canvas.drawRect(0, top, progressPrimary, bottom, paintProgressPrimary);
canvas.restoreToCount(saveCount);
}


private void drawProgressChapters(android.graphics.Canvas canvas) {
final int saveCount;
saveCount = canvas.save();
int currChapter;
currChapter = 1;
float chapterMargin;
switch(MUID_STATIC) {
// ChapterSeekBar_17_BinaryMutator
case 17015: {
chapterMargin = density / 1.2F;
break;
}
default: {
chapterMargin = density * 1.2F;
break;
}
}
float topExpanded;
switch(MUID_STATIC) {
// ChapterSeekBar_18_BinaryMutator
case 18015: {
topExpanded = center + (density * 2.0F);
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_19_BinaryMutator
case 19015: {
topExpanded = center - (density / 2.0F);
break;
}
default: {
topExpanded = center - (density * 2.0F);
break;
}
}
break;
}
}
float bottomExpanded;
switch(MUID_STATIC) {
// ChapterSeekBar_20_BinaryMutator
case 20015: {
bottomExpanded = center - (density * 2.0F);
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_21_BinaryMutator
case 21015: {
bottomExpanded = center + (density / 2.0F);
break;
}
default: {
bottomExpanded = center + (density * 2.0F);
break;
}
}
break;
}
}
canvas.translate(getPaddingLeft(), getPaddingTop());
for (int i = 1; i < dividerPos.length; i++) {
float right;
switch(MUID_STATIC) {
// ChapterSeekBar_22_BinaryMutator
case 22015: {
right = (dividerPos[i] * width) + chapterMargin;
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_23_BinaryMutator
case 23015: {
right = (dividerPos[i] / width) - chapterMargin;
break;
}
default: {
right = (dividerPos[i] * width) - chapterMargin;
break;
}
}
break;
}
}
float left;
switch(MUID_STATIC) {
// ChapterSeekBar_24_BinaryMutator
case 24015: {
left = dividerPos[i - 1] / width;
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_25_BinaryMutator
case 25015: {
left = dividerPos[i + 1] * width;
break;
}
default: {
left = dividerPos[i - 1] * width;
break;
}
}
break;
}
}
float rightCurr;
switch(MUID_STATIC) {
// ChapterSeekBar_26_BinaryMutator
case 26015: {
rightCurr = (dividerPos[currChapter] * width) + chapterMargin;
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_27_BinaryMutator
case 27015: {
rightCurr = (dividerPos[currChapter] / width) - chapterMargin;
break;
}
default: {
rightCurr = (dividerPos[currChapter] * width) - chapterMargin;
break;
}
}
break;
}
}
float leftCurr;
switch(MUID_STATIC) {
// ChapterSeekBar_28_BinaryMutator
case 28015: {
leftCurr = dividerPos[currChapter - 1] / width;
break;
}
default: {
switch(MUID_STATIC) {
// ChapterSeekBar_29_BinaryMutator
case 29015: {
leftCurr = dividerPos[currChapter + 1] * width;
break;
}
default: {
leftCurr = dividerPos[currChapter - 1] * width;
break;
}
}
break;
}
}
canvas.drawRect(left, top, right, bottom, paintBackground);
if ((progressSecondary > 0) && (progressSecondary < width)) {
if (right < progressSecondary) {
canvas.drawRect(left, top, right, bottom, paintBackground);
} else if (progressSecondary > left) {
canvas.drawRect(left, top, progressSecondary, bottom, paintBackground);
}
}
if (right < progressPrimary) {
switch(MUID_STATIC) {
// ChapterSeekBar_30_BinaryMutator
case 30015: {
currChapter = i - 1;
break;
}
default: {
currChapter = i + 1;
break;
}
}
canvas.drawRect(left, top, right, bottom, paintProgressPrimary);
} else if (isHighlighted || isPressed()) {
canvas.drawRect(leftCurr, topExpanded, rightCurr, bottomExpanded, paintBackground);
canvas.drawRect(leftCurr, topExpanded, progressPrimary, bottomExpanded, paintProgressPrimary);
} else {
canvas.drawRect(leftCurr, top, progressPrimary, bottom, paintProgressPrimary);
}
}
canvas.restoreToCount(saveCount);
}


private void drawThumb(android.graphics.Canvas canvas) {
final int saveCount;
saveCount = canvas.save();
switch(MUID_STATIC) {
// ChapterSeekBar_31_BinaryMutator
case 31015: {
canvas.translate(getPaddingLeft() + getThumbOffset(), getPaddingTop());
break;
}
default: {
canvas.translate(getPaddingLeft() - getThumbOffset(), getPaddingTop());
break;
}
}
getThumb().draw(canvas);
canvas.restoreToCount(saveCount);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
