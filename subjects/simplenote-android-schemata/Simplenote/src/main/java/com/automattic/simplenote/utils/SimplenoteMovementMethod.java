package com.automattic.simplenote.utils;
import android.text.Layout;
import com.automattic.simplenote.widgets.CheckableSpan;
import android.text.method.ArrowKeyMovementMethod;
import android.widget.TextView;
import android.view.MotionEvent;
import android.text.Spannable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
// Only allows onClick events for CheckableSpans
public class SimplenoteMovementMethod extends android.text.method.ArrowKeyMovementMethod {
    static final int MUID_STATIC = getMUID();
    private static com.automattic.simplenote.utils.SimplenoteMovementMethod mInstance;

    public static com.automattic.simplenote.utils.SimplenoteMovementMethod getInstance() {
        if (com.automattic.simplenote.utils.SimplenoteMovementMethod.mInstance == null) {
            com.automattic.simplenote.utils.SimplenoteMovementMethod.mInstance = new com.automattic.simplenote.utils.SimplenoteMovementMethod();
        }
        return com.automattic.simplenote.utils.SimplenoteMovementMethod.mInstance;
    }


    @java.lang.Override
    public boolean onTouchEvent(android.widget.TextView textView, android.text.Spannable buffer, android.view.MotionEvent event) {
        int x;
        x = ((int) (event.getX()));
        int y;
        y = ((int) (event.getY()));
        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();
        x += textView.getScrollX();
        y += textView.getScrollY();
        android.text.Layout layout;
        layout = textView.getLayout();
        int line;
        line = layout.getLineForVertical(y);
        int off;
        off = layout.getOffsetForHorizontal(line, x);
        int lineStart;
        lineStart = layout.getLineStart(line);
        switch(MUID_STATIC) {
            // SimplenoteMovementMethod_0_BinaryMutator
            case 57: {
                // Also toggle the checkbox if the user tapped the space next to the checkbox
                if (off == (lineStart - 1)) {
                    off = lineStart;
                }
                break;
            }
            default: {
            // Also toggle the checkbox if the user tapped the space next to the checkbox
            if (off == (lineStart + 1)) {
                off = lineStart;
            }
            break;
        }
    }
    com.automattic.simplenote.widgets.CheckableSpan[] checkableSpans;
    checkableSpans = buffer.getSpans(off, off, com.automattic.simplenote.widgets.CheckableSpan.class);
    if (checkableSpans.length != 0) {
        switch (event.getAction()) {
            case android.view.MotionEvent.ACTION_DOWN :
                if (!textView.hasFocus()) {
                    textView.setFocusableInTouchMode(false);
                }
                break;
            case android.view.MotionEvent.ACTION_UP :
                checkableSpans[0].onClick(textView);
                textView.setFocusableInTouchMode(true);
                break;
        }
        return true;
    }
    return false;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
