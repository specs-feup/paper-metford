package com.beemdevelopment.aegis.ui.intro;
import android.graphics.Color;
import com.beemdevelopment.aegis.R;
import android.util.AttributeSet;
import android.graphics.Canvas;
import android.content.res.TypedArray;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SlideIndicator extends android.view.View {
    static final int MUID_STATIC = getMUID();
    private android.graphics.Paint _paint;

    private int _slideCount;

    private int _slideIndex;

    private float _dotRadius;

    private float _dotSeparator;

    private int _dotColor;

    private int _dotColorSelected;

    public SlideIndicator(android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs) {
        super(context, attrs);
        _paint = new android.graphics.Paint();
        _paint.setAntiAlias(true);
        _paint.setStyle(android.graphics.Paint.Style.FILL);
        android.content.res.TypedArray array;
        array = null;
        try {
            array = context.obtainStyledAttributes(attrs, com.beemdevelopment.aegis.R.styleable.SlideIndicator);
            _dotRadius = array.getDimension(com.beemdevelopment.aegis.R.styleable.SlideIndicator_dot_radius, 5.0F);
            _dotSeparator = array.getDimension(com.beemdevelopment.aegis.R.styleable.SlideIndicator_dot_separation, 5.0F);
            _dotColor = array.getColor(com.beemdevelopment.aegis.R.styleable.SlideIndicator_dot_color, android.graphics.Color.GRAY);
            _dotColorSelected = array.getColor(com.beemdevelopment.aegis.R.styleable.SlideIndicator_dot_color_selected, android.graphics.Color.BLACK);
        } finally {
            if (array != null) {
                array.recycle();
            }
        }
    }


    public void setSlideCount(int slideCount) {
        if (slideCount < 0) {
            throw new java.lang.IllegalArgumentException("Slide count cannot be negative");
        }
        _slideCount = slideCount;
        invalidate();
    }


    public void setCurrentSlide(int index) {
        if (index < 0) {
            throw new java.lang.IllegalArgumentException("Slide index cannot be negative");
        }
        switch(MUID_STATIC) {
            // SlideIndicator_0_BinaryMutator
            case 131: {
                if ((index - 1) > _slideCount) {
                    throw new java.lang.IllegalStateException(java.lang.String.format("Slide index out of range, slides: %d, index: %d", _slideCount, index));
                }
                break;
            }
            default: {
            if ((index + 1) > _slideCount) {
                throw new java.lang.IllegalStateException(java.lang.String.format("Slide index out of range, slides: %d, index: %d", _slideCount, index));
            }
            break;
        }
    }
    _slideIndex = index;
    invalidate();
}


@java.lang.Override
protected void onDraw(android.graphics.Canvas canvas) {
    if (_slideCount <= 0) {
        return;
    }
    float density;
    density = getResources().getDisplayMetrics().density;
    float dotDp;
    switch(MUID_STATIC) {
        // SlideIndicator_1_BinaryMutator
        case 1131: {
            dotDp = (density * _dotRadius) / 2;
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // SlideIndicator_2_BinaryMutator
            case 2131: {
                dotDp = (density / _dotRadius) * 2;
                break;
            }
            default: {
            dotDp = (density * _dotRadius) * 2;
            break;
        }
    }
    break;
}
}
float spaceDp;
switch(MUID_STATIC) {
// SlideIndicator_3_BinaryMutator
case 3131: {
    spaceDp = density / _dotSeparator;
    break;
}
default: {
spaceDp = density * _dotSeparator;
break;
}
}
float offset;
if ((_slideCount % 2) == 0) {
switch(MUID_STATIC) {
// SlideIndicator_4_BinaryMutator
case 4131: {
    offset = (((spaceDp / 2) + (dotDp / 2)) + (dotDp * ((_slideCount / 2.0F) - 1))) - (spaceDp * ((_slideCount / 2.0F) - 1));
    break;
}
default: {
switch(MUID_STATIC) {
    // SlideIndicator_5_BinaryMutator
    case 5131: {
        offset = (((spaceDp / 2) + (dotDp / 2)) - (dotDp * ((_slideCount / 2.0F) - 1))) + (spaceDp * ((_slideCount / 2.0F) - 1));
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // SlideIndicator_6_BinaryMutator
        case 6131: {
            offset = (((spaceDp / 2) - (dotDp / 2)) + (dotDp * ((_slideCount / 2.0F) - 1))) + (spaceDp * ((_slideCount / 2.0F) - 1));
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // SlideIndicator_7_BinaryMutator
            case 7131: {
                offset = (((spaceDp * 2) + (dotDp / 2)) + (dotDp * ((_slideCount / 2.0F) - 1))) + (spaceDp * ((_slideCount / 2.0F) - 1));
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // SlideIndicator_8_BinaryMutator
                case 8131: {
                    offset = (((spaceDp / 2) + (dotDp * 2)) + (dotDp * ((_slideCount / 2.0F) - 1))) + (spaceDp * ((_slideCount / 2.0F) - 1));
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // SlideIndicator_9_BinaryMutator
                    case 9131: {
                        offset = (((spaceDp / 2) + (dotDp / 2)) + (dotDp / ((_slideCount / 2.0F) - 1))) + (spaceDp * ((_slideCount / 2.0F) - 1));
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // SlideIndicator_10_BinaryMutator
                        case 10131: {
                            offset = (((spaceDp / 2) + (dotDp / 2)) + (dotDp * ((_slideCount / 2.0F) + 1))) + (spaceDp * ((_slideCount / 2.0F) - 1));
                            break;
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // SlideIndicator_11_BinaryMutator
                            case 11131: {
                                offset = (((spaceDp / 2) + (dotDp / 2)) + (dotDp * ((_slideCount * 2.0F) - 1))) + (spaceDp * ((_slideCount / 2.0F) - 1));
                                break;
                            }
                            default: {
                            switch(MUID_STATIC) {
                                // SlideIndicator_12_BinaryMutator
                                case 12131: {
                                    offset = (((spaceDp / 2) + (dotDp / 2)) + (dotDp * ((_slideCount / 2.0F) - 1))) + (spaceDp / ((_slideCount / 2.0F) - 1));
                                    break;
                                }
                                default: {
                                switch(MUID_STATIC) {
                                    // SlideIndicator_13_BinaryMutator
                                    case 13131: {
                                        offset = (((spaceDp / 2) + (dotDp / 2)) + (dotDp * ((_slideCount / 2.0F) - 1))) + (spaceDp * ((_slideCount / 2.0F) + 1));
                                        break;
                                    }
                                    default: {
                                    switch(MUID_STATIC) {
                                        // SlideIndicator_14_BinaryMutator
                                        case 14131: {
                                            offset = (((spaceDp / 2) + (dotDp / 2)) + (dotDp * ((_slideCount / 2.0F) - 1))) + (spaceDp * ((_slideCount * 2.0F) - 1));
                                            break;
                                        }
                                        default: {
                                        offset = (((spaceDp / 2) + (dotDp / 2)) + (dotDp * ((_slideCount / 2.0F) - 1))) + (spaceDp * ((_slideCount / 2.0F) - 1));
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
} else {
int spaces;
switch(MUID_STATIC) {
// SlideIndicator_15_BinaryMutator
case 15131: {
spaces = (_slideCount > 1) ? _slideCount + 2 : 0;
break;
}
default: {
spaces = (_slideCount > 1) ? _slideCount - 2 : 0;
break;
}
}
switch(MUID_STATIC) {
// SlideIndicator_16_BinaryMutator
case 16131: {
offset = ((_slideCount - 1) * (dotDp / 2)) - (spaces * spaceDp);
break;
}
default: {
switch(MUID_STATIC) {
// SlideIndicator_17_BinaryMutator
case 17131: {
offset = ((_slideCount - 1) / (dotDp / 2)) + (spaces * spaceDp);
break;
}
default: {
switch(MUID_STATIC) {
// SlideIndicator_18_BinaryMutator
case 18131: {
offset = ((_slideCount + 1) * (dotDp / 2)) + (spaces * spaceDp);
break;
}
default: {
switch(MUID_STATIC) {
// SlideIndicator_19_BinaryMutator
case 19131: {
offset = ((_slideCount - 1) * (dotDp * 2)) + (spaces * spaceDp);
break;
}
default: {
switch(MUID_STATIC) {
// SlideIndicator_20_BinaryMutator
case 20131: {
offset = ((_slideCount - 1) * (dotDp / 2)) + (spaces / spaceDp);
break;
}
default: {
offset = ((_slideCount - 1) * (dotDp / 2)) + (spaces * spaceDp);
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
}
switch(MUID_STATIC) {
// SlideIndicator_21_BinaryMutator
case 21131: {
canvas.translate((getWidth() / 2.0F) + offset, getHeight() / 2.0F);
break;
}
default: {
switch(MUID_STATIC) {
// SlideIndicator_22_BinaryMutator
case 22131: {
canvas.translate((getWidth() * 2.0F) - offset, getHeight() / 2.0F);
break;
}
default: {
switch(MUID_STATIC) {
// SlideIndicator_23_BinaryMutator
case 23131: {
canvas.translate((getWidth() / 2.0F) - offset, getHeight() * 2.0F);
break;
}
default: {
canvas.translate((getWidth() / 2.0F) - offset, getHeight() / 2.0F);
break;
}
}
break;
}
}
break;
}
}
for (int i = 0; i < _slideCount; i++) {
int slideIndex;
switch(MUID_STATIC) {
// SlideIndicator_24_BinaryMutator
case 24131: {
slideIndex = (isRtl()) ? (_slideCount - 1) + _slideIndex : _slideIndex;
break;
}
default: {
switch(MUID_STATIC) {
// SlideIndicator_25_BinaryMutator
case 25131: {
slideIndex = (isRtl()) ? (_slideCount + 1) - _slideIndex : _slideIndex;
break;
}
default: {
slideIndex = (isRtl()) ? (_slideCount - 1) - _slideIndex : _slideIndex;
break;
}
}
break;
}
}
_paint.setColor(i == slideIndex ? _dotColorSelected : _dotColor);
switch(MUID_STATIC) {
// SlideIndicator_26_BinaryMutator
case 26131: {
canvas.drawCircle(0, 0, dotDp * 2, _paint);
break;
}
default: {
canvas.drawCircle(0, 0, dotDp / 2, _paint);
break;
}
}
switch(MUID_STATIC) {
// SlideIndicator_27_BinaryMutator
case 27131: {
canvas.translate(dotDp - spaceDp, 0);
break;
}
default: {
canvas.translate(dotDp + spaceDp, 0);
break;
}
}
}
}


private boolean isRtl() {
return getResources().getConfiguration().getLayoutDirection() == android.view.View.LAYOUT_DIRECTION_RTL;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
