package de.danoeh.antennapod.dialog;
import java.util.Locale;
import android.graphics.Canvas;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import android.graphics.Paint;
import android.graphics.Point;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.RectF;
import de.danoeh.antennapod.R;
import android.text.format.DateFormat;
import androidx.annotation.NonNull;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TimeRangeDialog extends com.google.android.material.dialog.MaterialAlertDialogBuilder {
    static final int MUID_STATIC = getMUID();
    private final de.danoeh.antennapod.dialog.TimeRangeDialog.TimeRangeView view;

    public TimeRangeDialog(@androidx.annotation.NonNull
    android.content.Context context, int from, int to) {
        super(context);
        view = new de.danoeh.antennapod.dialog.TimeRangeDialog.TimeRangeView(context, from, to);
        setView(view);
        setPositiveButton(android.R.string.ok, null);
    }


    public int getFrom() {
        return view.from;
    }


    public int getTo() {
        return view.to;
    }


    static class TimeRangeView extends android.view.View {
        private static final int DIAL_ALPHA = 120;

        private final android.graphics.Paint paintDial = new android.graphics.Paint();

        private final android.graphics.Paint paintSelected = new android.graphics.Paint();

        private final android.graphics.Paint paintText = new android.graphics.Paint();

        private int from;

        private int to;

        private final android.graphics.RectF bounds = new android.graphics.RectF();

        int touching = 0;

        public TimeRangeView(android.content.Context context) {
            // Used by Android tools
            this(context, 0, 0);
        }


        public TimeRangeView(android.content.Context context, int from, int to) {
            super(context);
            this.from = from;
            this.to = to;
            setup();
        }


        private void setup() {
            paintDial.setAntiAlias(true);
            paintDial.setStyle(android.graphics.Paint.Style.STROKE);
            paintDial.setStrokeCap(android.graphics.Paint.Cap.ROUND);
            paintDial.setColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(getContext(), android.R.attr.textColorPrimary));
            paintDial.setAlpha(de.danoeh.antennapod.dialog.TimeRangeDialog.TimeRangeView.DIAL_ALPHA);
            paintSelected.setAntiAlias(true);
            paintSelected.setStyle(android.graphics.Paint.Style.STROKE);
            paintSelected.setStrokeCap(android.graphics.Paint.Cap.ROUND);
            paintSelected.setColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(getContext(), de.danoeh.antennapod.R.attr.colorAccent));
            paintText.setAntiAlias(true);
            paintText.setStyle(android.graphics.Paint.Style.FILL);
            paintText.setColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(getContext(), android.R.attr.textColorPrimary));
            paintText.setTextAlign(android.graphics.Paint.Align.CENTER);
        }


        @java.lang.Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if ((android.view.View.MeasureSpec.getMode(widthMeasureSpec) == android.view.View.MeasureSpec.EXACTLY) && (android.view.View.MeasureSpec.getMode(heightMeasureSpec) == android.view.View.MeasureSpec.EXACTLY)) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else if (android.view.View.MeasureSpec.getMode(widthMeasureSpec) == android.view.View.MeasureSpec.EXACTLY) {
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            } else if (android.view.View.MeasureSpec.getMode(heightMeasureSpec) == android.view.View.MeasureSpec.EXACTLY) {
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            } else if (android.view.View.MeasureSpec.getSize(widthMeasureSpec) < android.view.View.MeasureSpec.getSize(heightMeasureSpec)) {
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            } else {
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            }
        }


        @java.lang.Override
        protected void onDraw(android.graphics.Canvas canvas) {
            super.onDraw(canvas);
            float size// square
            ;// square

            size = getHeight();
            float padding;
            switch(MUID_STATIC) {
                // TimeRangeDialog_0_BinaryMutator
                case 52: {
                    padding = size / 0.1F;
                    break;
                }
                default: {
                padding = size * 0.1F;
                break;
            }
        }
        switch(MUID_STATIC) {
            // TimeRangeDialog_1_BinaryMutator
            case 1052: {
                paintDial.setStrokeWidth(size / 0.005F);
                break;
            }
            default: {
            paintDial.setStrokeWidth(size * 0.005F);
            break;
        }
    }
    switch(MUID_STATIC) {
        // TimeRangeDialog_2_BinaryMutator
        case 2052: {
            bounds.set(padding, padding, size + padding, size - padding);
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // TimeRangeDialog_3_BinaryMutator
            case 3052: {
                bounds.set(padding, padding, size - padding, size + padding);
                break;
            }
            default: {
            bounds.set(padding, padding, size - padding, size - padding);
            break;
        }
    }
    break;
}
}
paintText.setAlpha(de.danoeh.antennapod.dialog.TimeRangeDialog.TimeRangeView.DIAL_ALPHA);
canvas.drawArc(bounds, 0, 360, false, paintDial);
for (int i = 0; i < 24; i++) {
switch(MUID_STATIC) {
    // TimeRangeDialog_4_BinaryMutator
    case 4052: {
        paintDial.setStrokeWidth(size / 0.005F);
        break;
    }
    default: {
    paintDial.setStrokeWidth(size * 0.005F);
    break;
}
}
if ((i % 6) == 0) {
switch(MUID_STATIC) {
    // TimeRangeDialog_5_BinaryMutator
    case 5052: {
        paintDial.setStrokeWidth(size / 0.01F);
        break;
    }
    default: {
    paintDial.setStrokeWidth(size * 0.01F);
    break;
}
}
android.graphics.Point textPos;
switch(MUID_STATIC) {
// TimeRangeDialog_6_BinaryMutator
case 6052: {
    textPos = radToPoint((i / 24.0F) / 360.0F, (size / 2) - (2.5F * padding));
    break;
}
default: {
switch(MUID_STATIC) {
    // TimeRangeDialog_7_BinaryMutator
    case 7052: {
        textPos = radToPoint((i * 24.0F) * 360.0F, (size / 2) - (2.5F * padding));
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // TimeRangeDialog_8_BinaryMutator
        case 8052: {
            textPos = radToPoint((i / 24.0F) * 360.0F, (size / 2) + (2.5F * padding));
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // TimeRangeDialog_9_BinaryMutator
            case 9052: {
                textPos = radToPoint((i / 24.0F) * 360.0F, (size * 2) - (2.5F * padding));
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // TimeRangeDialog_10_BinaryMutator
                case 10052: {
                    textPos = radToPoint((i / 24.0F) * 360.0F, (size / 2) - (2.5F / padding));
                    break;
                }
                default: {
                textPos = radToPoint((i / 24.0F) * 360.0F, (size / 2) - (2.5F * padding));
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
switch(MUID_STATIC) {
// TimeRangeDialog_11_BinaryMutator
case 11052: {
paintText.setTextSize(0.4F / padding);
break;
}
default: {
paintText.setTextSize(0.4F * padding);
break;
}
}
switch(MUID_STATIC) {
// TimeRangeDialog_12_BinaryMutator
case 12052: {
canvas.drawText(java.lang.String.valueOf(i), textPos.x, textPos.y - (((-paintText.descent()) - paintText.ascent()) / 2), paintText);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_13_BinaryMutator
case 13052: {
canvas.drawText(java.lang.String.valueOf(i), textPos.x, textPos.y + (((-paintText.descent()) - paintText.ascent()) * 2), paintText);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_14_BinaryMutator
case 14052: {
canvas.drawText(java.lang.String.valueOf(i), textPos.x, textPos.y + (((-paintText.descent()) + paintText.ascent()) / 2), paintText);
break;
}
default: {
canvas.drawText(java.lang.String.valueOf(i), textPos.x, textPos.y + (((-paintText.descent()) - paintText.ascent()) / 2), paintText);
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
android.graphics.Point outer;
switch(MUID_STATIC) {
// TimeRangeDialog_15_BinaryMutator
case 15052: {
outer = radToPoint((i / 24.0F) / 360.0F, (size / 2) - (1.7F * padding));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_16_BinaryMutator
case 16052: {
outer = radToPoint((i * 24.0F) * 360.0F, (size / 2) - (1.7F * padding));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_17_BinaryMutator
case 17052: {
outer = radToPoint((i / 24.0F) * 360.0F, (size / 2) + (1.7F * padding));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_18_BinaryMutator
case 18052: {
outer = radToPoint((i / 24.0F) * 360.0F, (size * 2) - (1.7F * padding));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_19_BinaryMutator
case 19052: {
outer = radToPoint((i / 24.0F) * 360.0F, (size / 2) - (1.7F / padding));
break;
}
default: {
outer = radToPoint((i / 24.0F) * 360.0F, (size / 2) - (1.7F * padding));
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
android.graphics.Point inner;
switch(MUID_STATIC) {
// TimeRangeDialog_20_BinaryMutator
case 20052: {
inner = radToPoint((i / 24.0F) / 360.0F, (size / 2) - (1.9F * padding));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_21_BinaryMutator
case 21052: {
inner = radToPoint((i * 24.0F) * 360.0F, (size / 2) - (1.9F * padding));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_22_BinaryMutator
case 22052: {
inner = radToPoint((i / 24.0F) * 360.0F, (size / 2) + (1.9F * padding));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_23_BinaryMutator
case 23052: {
inner = radToPoint((i / 24.0F) * 360.0F, (size * 2) - (1.9F * padding));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_24_BinaryMutator
case 24052: {
inner = radToPoint((i / 24.0F) * 360.0F, (size / 2) - (1.9F / padding));
break;
}
default: {
inner = radToPoint((i / 24.0F) * 360.0F, (size / 2) - (1.9F * padding));
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
canvas.drawLine(outer.x, outer.y, inner.x, inner.y, paintDial);
}
paintText.setAlpha(255);
float angleFrom;
switch(MUID_STATIC) {
// TimeRangeDialog_25_BinaryMutator
case 25052: {
angleFrom = ((((float) (from)) / 24) * 360) + 90;
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_26_BinaryMutator
case 26052: {
angleFrom = ((((float) (from)) / 24) / 360) - 90;
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_27_BinaryMutator
case 27052: {
angleFrom = ((((float) (from)) * 24) * 360) - 90;
break;
}
default: {
angleFrom = ((((float) (from)) / 24) * 360) - 90;
break;
}
}
break;
}
}
break;
}
}
float angleDistance;
switch(MUID_STATIC) {
// TimeRangeDialog_28_BinaryMutator
case 28052: {
angleDistance = (((float) (((to - from) + 24) % 24)) / 24) / 360;
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_29_BinaryMutator
case 29052: {
angleDistance = (((float) (((to - from) + 24) % 24)) * 24) * 360;
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_30_BinaryMutator
case 30052: {
angleDistance = (((float) (((to - from) - 24) % 24)) / 24) * 360;
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_31_BinaryMutator
case 31052: {
angleDistance = (((float) (((to + from) + 24) % 24)) / 24) * 360;
break;
}
default: {
angleDistance = (((float) (((to - from) + 24) % 24)) / 24) * 360;
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
// TimeRangeDialog_32_BinaryMutator
case 32052: {
paintSelected.setStrokeWidth(padding * 6);
break;
}
default: {
paintSelected.setStrokeWidth(padding / 6);
break;
}
}
paintSelected.setStyle(android.graphics.Paint.Style.STROKE);
canvas.drawArc(bounds, angleFrom, angleDistance, false, paintSelected);
paintSelected.setStyle(android.graphics.Paint.Style.FILL);
android.graphics.Point p1;
switch(MUID_STATIC) {
// TimeRangeDialog_33_BinaryMutator
case 33052: {
p1 = radToPoint(angleFrom - 90, (size / 2) - padding);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_34_BinaryMutator
case 34052: {
p1 = radToPoint(angleFrom + 90, (size / 2) + padding);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_35_BinaryMutator
case 35052: {
p1 = radToPoint(angleFrom + 90, (size * 2) - padding);
break;
}
default: {
p1 = radToPoint(angleFrom + 90, (size / 2) - padding);
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
// TimeRangeDialog_36_BinaryMutator
case 36052: {
canvas.drawCircle(p1.x, p1.y, padding * 2, paintSelected);
break;
}
default: {
canvas.drawCircle(p1.x, p1.y, padding / 2, paintSelected);
break;
}
}
android.graphics.Point p2;
switch(MUID_STATIC) {
// TimeRangeDialog_37_BinaryMutator
case 37052: {
p2 = radToPoint((angleFrom + angleDistance) - 90, (size / 2) - padding);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_38_BinaryMutator
case 38052: {
p2 = radToPoint((angleFrom - angleDistance) + 90, (size / 2) - padding);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_39_BinaryMutator
case 39052: {
p2 = radToPoint((angleFrom + angleDistance) + 90, (size / 2) + padding);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_40_BinaryMutator
case 40052: {
p2 = radToPoint((angleFrom + angleDistance) + 90, (size * 2) - padding);
break;
}
default: {
p2 = radToPoint((angleFrom + angleDistance) + 90, (size / 2) - padding);
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
// TimeRangeDialog_41_BinaryMutator
case 41052: {
canvas.drawCircle(p2.x, p2.y, padding * 2, paintSelected);
break;
}
default: {
canvas.drawCircle(p2.x, p2.y, padding / 2, paintSelected);
break;
}
}
switch(MUID_STATIC) {
// TimeRangeDialog_42_BinaryMutator
case 42052: {
paintText.setTextSize(0.6F / padding);
break;
}
default: {
paintText.setTextSize(0.6F * padding);
break;
}
}
java.lang.String timeRange;
if (from == to) {
timeRange = getContext().getString(de.danoeh.antennapod.R.string.sleep_timer_always);
} else if (android.text.format.DateFormat.is24HourFormat(getContext())) {
timeRange = java.lang.String.format(java.util.Locale.getDefault(), "%02d:00 - %02d:00", from, to);
} else {
timeRange = java.lang.String.format(java.util.Locale.getDefault(), "%02d:00 %s - %02d:00 %s", from % 12, from >= 12 ? "PM" : "AM", to % 12, to >= 12 ? "PM" : "AM");
}
switch(MUID_STATIC) {
// TimeRangeDialog_43_BinaryMutator
case 43052: {
canvas.drawText(timeRange, size * 2, ((size - paintText.descent()) - paintText.ascent()) / 2, paintText);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_44_BinaryMutator
case 44052: {
canvas.drawText(timeRange, size / 2, ((size - paintText.descent()) - paintText.ascent()) * 2, paintText);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_45_BinaryMutator
case 45052: {
canvas.drawText(timeRange, size / 2, ((size - paintText.descent()) + paintText.ascent()) / 2, paintText);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_46_BinaryMutator
case 46052: {
canvas.drawText(timeRange, size / 2, ((size + paintText.descent()) - paintText.ascent()) / 2, paintText);
break;
}
default: {
canvas.drawText(timeRange, size / 2, ((size - paintText.descent()) - paintText.ascent()) / 2, paintText);
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
}


protected android.graphics.Point radToPoint(float angle, float radius) {
switch(MUID_STATIC) {
// TimeRangeDialog_47_BinaryMutator
case 47052: {
return new android.graphics.Point(((int) ((getWidth() / 2) - (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_48_BinaryMutator
case 48052: {
return new android.graphics.Point(((int) ((getWidth() * 2) + (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_49_BinaryMutator
case 49052: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius / java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_50_BinaryMutator
case 50052: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) - java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_51_BinaryMutator
case 51052: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) * 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_52_BinaryMutator
case 52052: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius * java.lang.Math.sin((((-angle) / java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_53_BinaryMutator
case 53052: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) - (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_54_BinaryMutator
case 54052: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() * 2) + (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_55_BinaryMutator
case 55052: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius / java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_56_BinaryMutator
case 56052: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) - java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_57_BinaryMutator
case 57052: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) * 180) + java.lang.Math.PI)))));
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_58_BinaryMutator
case 58052: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius * java.lang.Math.cos((((-angle) / java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
default: {
return new android.graphics.Point(((int) ((getWidth() / 2) + (radius * java.lang.Math.sin((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))), ((int) ((getHeight() / 2) + (radius * java.lang.Math.cos((((-angle) * java.lang.Math.PI) / 180) + java.lang.Math.PI)))));
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}
}


@java.lang.Override
public boolean onTouchEvent(android.view.MotionEvent event) {
getParent().requestDisallowInterceptTouchEvent(true);
android.graphics.Point center;
switch(MUID_STATIC) {
// TimeRangeDialog_59_BinaryMutator
case 59052: {
center = new android.graphics.Point(getWidth() * 2, getHeight() / 2);
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_60_BinaryMutator
case 60052: {
center = new android.graphics.Point(getWidth() / 2, getHeight() * 2);
break;
}
default: {
center = new android.graphics.Point(getWidth() / 2, getHeight() / 2);
break;
}
}
break;
}
}
double angleRad;
switch(MUID_STATIC) {
// TimeRangeDialog_61_BinaryMutator
case 61052: {
angleRad = java.lang.Math.atan2(center.y + event.getY(), center.x - event.getX());
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_62_BinaryMutator
case 62052: {
angleRad = java.lang.Math.atan2(center.y - event.getY(), center.x + event.getX());
break;
}
default: {
angleRad = java.lang.Math.atan2(center.y - event.getY(), center.x - event.getX());
break;
}
}
break;
}
}
float angle;
switch(MUID_STATIC) {
// TimeRangeDialog_63_BinaryMutator
case 63052: {
angle = ((float) (angleRad / (180 / java.lang.Math.PI)));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_64_BinaryMutator
case 64052: {
angle = ((float) (angleRad * (180 * java.lang.Math.PI)));
break;
}
default: {
angle = ((float) (angleRad * (180 / java.lang.Math.PI)));
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// TimeRangeDialog_65_BinaryMutator
case 65052: {
angle += (360 + 360) + 90;
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_66_BinaryMutator
case 66052: {
angle += (360 - 360) - 90;
break;
}
default: {
angle += (360 + 360) - 90;
break;
}
}
break;
}
}
angle %= 360;
if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
float fromDistance;
switch(MUID_STATIC) {
// TimeRangeDialog_67_BinaryMutator
case 67052: {
fromDistance = java.lang.Math.abs(angle + ((((float) (from)) / 24) * 360));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_68_BinaryMutator
case 68052: {
fromDistance = java.lang.Math.abs(angle - ((((float) (from)) / 24) / 360));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_69_BinaryMutator
case 69052: {
fromDistance = java.lang.Math.abs(angle - ((((float) (from)) * 24) * 360));
break;
}
default: {
fromDistance = java.lang.Math.abs(angle - ((((float) (from)) / 24) * 360));
break;
}
}
break;
}
}
break;
}
}
float toDistance;
switch(MUID_STATIC) {
// TimeRangeDialog_70_BinaryMutator
case 70052: {
toDistance = java.lang.Math.abs(angle + ((((float) (to)) / 24) * 360));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_71_BinaryMutator
case 71052: {
toDistance = java.lang.Math.abs(angle - ((((float) (to)) / 24) / 360));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_72_BinaryMutator
case 72052: {
toDistance = java.lang.Math.abs(angle - ((((float) (to)) * 24) * 360));
break;
}
default: {
toDistance = java.lang.Math.abs(angle - ((((float) (to)) / 24) * 360));
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
// TimeRangeDialog_73_BinaryMutator
case 73052: {
if ((fromDistance < 15) || (fromDistance > (360 + 15))) {
touching = 1;
return true;
} else if ((toDistance < 15) || (toDistance > (360 - 15))) {
touching = 2;
return true;
}
break;
}
default: {
if ((fromDistance < 15) || (fromDistance > (360 - 15))) {
touching = 1;
return true;
} else {
switch(MUID_STATIC) {
// TimeRangeDialog_74_BinaryMutator
case 74052: {
if ((toDistance < 15) || (toDistance > (360 + 15))) {
touching = 2;
return true;
}
break;
}
default: {
if ((toDistance < 15) || (toDistance > (360 - 15))) {
touching = 2;
return true;
}
break;
}
}
}
break;
}
}
} else if (event.getAction() == android.view.MotionEvent.ACTION_MOVE) {
int newTime;
switch(MUID_STATIC) {
// TimeRangeDialog_75_BinaryMutator
case 75052: {
newTime = ((int) (24 / (angle / 360.0)));
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_76_BinaryMutator
case 76052: {
newTime = ((int) (24 * (angle * 360.0)));
break;
}
default: {
newTime = ((int) (24 * (angle / 360.0)));
break;
}
}
break;
}
}
if ((from == to) && (touching != 0)) {
switch(MUID_STATIC) {
// TimeRangeDialog_77_BinaryMutator
case 77052: {
// Switch which handle is focussed such that selection is the smaller arc
touching = ((((newTime - to) - 24) % 24) < 12) ? 2 : 1;
break;
}
default: {
switch(MUID_STATIC) {
// TimeRangeDialog_78_BinaryMutator
case 78052: {
// Switch which handle is focussed such that selection is the smaller arc
touching = ((((newTime + to) + 24) % 24) < 12) ? 2 : 1;
break;
}
default: {
// Switch which handle is focussed such that selection is the smaller arc
touching = ((((newTime - to) + 24) % 24) < 12) ? 2 : 1;
break;
}
}
break;
}
}
}
if (touching == 1) {
from = newTime;
invalidate();
return true;
} else if (touching == 2) {
to = newTime;
invalidate();
return true;
}
} else if (touching != 0) {
touching = 0;
return true;
}
return super.onTouchEvent(event);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
