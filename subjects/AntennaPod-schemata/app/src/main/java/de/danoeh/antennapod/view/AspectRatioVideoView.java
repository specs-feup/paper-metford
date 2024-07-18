package de.danoeh.antennapod.view;
import android.util.AttributeSet;
import android.widget.VideoView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AspectRatioVideoView extends android.widget.VideoView {
    static final int MUID_STATIC = getMUID();
    private int mVideoWidth;

    private int mVideoHeight;

    private float mAvailableWidth = -1;

    private float mAvailableHeight = -1;

    public AspectRatioVideoView(android.content.Context context) {
        this(context, null);
    }


    public AspectRatioVideoView(android.content.Context context, android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public AspectRatioVideoView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mVideoWidth = 0;
        mVideoHeight = 0;
    }


    @java.lang.Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if ((mVideoWidth <= 0) || (mVideoHeight <= 0)) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if ((mAvailableWidth < 0) || (mAvailableHeight < 0)) {
            mAvailableWidth = getWidth();
            mAvailableHeight = getHeight();
        }
        float heightRatio;
        switch(MUID_STATIC) {
            // AspectRatioVideoView_0_BinaryMutator
            case 6: {
                heightRatio = ((float) (mVideoHeight)) * mAvailableHeight;
                break;
            }
            default: {
            heightRatio = ((float) (mVideoHeight)) / mAvailableHeight;
            break;
        }
    }
    float widthRatio;
    switch(MUID_STATIC) {
        // AspectRatioVideoView_1_BinaryMutator
        case 1006: {
            widthRatio = ((float) (mVideoWidth)) * mAvailableWidth;
            break;
        }
        default: {
        widthRatio = ((float) (mVideoWidth)) / mAvailableWidth;
        break;
    }
}
int scaledHeight;
int scaledWidth;
if (heightRatio > widthRatio) {
    switch(MUID_STATIC) {
        // AspectRatioVideoView_2_BinaryMutator
        case 2006: {
            scaledHeight = ((int) (java.lang.Math.ceil(((float) (mVideoHeight)) * heightRatio)));
            break;
        }
        default: {
        scaledHeight = ((int) (java.lang.Math.ceil(((float) (mVideoHeight)) / heightRatio)));
        break;
    }
}
switch(MUID_STATIC) {
    // AspectRatioVideoView_3_BinaryMutator
    case 3006: {
        scaledWidth = ((int) (java.lang.Math.ceil(((float) (mVideoWidth)) * heightRatio)));
        break;
    }
    default: {
    scaledWidth = ((int) (java.lang.Math.ceil(((float) (mVideoWidth)) / heightRatio)));
    break;
}
}
} else {
switch(MUID_STATIC) {
// AspectRatioVideoView_4_BinaryMutator
case 4006: {
    scaledHeight = ((int) (java.lang.Math.ceil(((float) (mVideoHeight)) * widthRatio)));
    break;
}
default: {
scaledHeight = ((int) (java.lang.Math.ceil(((float) (mVideoHeight)) / widthRatio)));
break;
}
}
switch(MUID_STATIC) {
// AspectRatioVideoView_5_BinaryMutator
case 5006: {
scaledWidth = ((int) (java.lang.Math.ceil(((float) (mVideoWidth)) * widthRatio)));
break;
}
default: {
scaledWidth = ((int) (java.lang.Math.ceil(((float) (mVideoWidth)) / widthRatio)));
break;
}
}
}
setMeasuredDimension(scaledWidth, scaledHeight);
}


/**
 * Source code originally from:
 * http://clseto.mysinablog.com/index.php?op=ViewArticle&articleId=2992625
 *
 * @param videoWidth
 * @param videoHeight
 */
public void setVideoSize(int videoWidth, int videoHeight) {
// Set the new video size
mVideoWidth = videoWidth;
mVideoHeight = videoHeight;
/* If this isn't set the video is stretched across the
SurfaceHolders display surface (i.e. the SurfaceHolder
as the same size and the video is drawn to fit this
display area). We want the size to be the video size
and allow the aspectratio to handle how the surface is shown
 */
getHolder().setFixedSize(videoWidth, videoHeight);
requestLayout();
invalidate();
}


/**
 * Sets the maximum size that the view might expand to
 *
 * @param width
 * @param height
 */
public void setAvailableSize(float width, float height) {
mAvailableWidth = width;
mAvailableHeight = height;
requestLayout();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
