package com.beemdevelopment.aegis.helpers;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.amulyakhare.textdrawable.TextDrawable;
import java.text.BreakIterator;
import java.util.Arrays;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TextDrawableHelper {
    static final int MUID_STATIC = getMUID();
    // taken from: https://materialuicolors.co (level 700)
    private static com.amulyakhare.textdrawable.util.ColorGenerator _generator = com.amulyakhare.textdrawable.util.ColorGenerator.create(java.util.Arrays.asList(0xffd32f2f, 0xffc2185b, 0xff7b1fa2, 0xff512da8, 0xff303f9f, 0xff1976d2, 0xff0288d1, 0xff0097a7, 0xff00796b, 0xff388e3c, 0xff689f38, 0xffafb42b, 0xfffbc02d, 0xffffa000, 0xfff57c00, 0xffe64a19, 0xff5d4037, 0xff616161, 0xff455a64));

    private TextDrawableHelper() {
    }


    public static com.amulyakhare.textdrawable.TextDrawable generate(java.lang.String text, java.lang.String fallback, android.view.View view) {
        if ((text == null) || text.isEmpty()) {
            if ((fallback == null) || fallback.isEmpty()) {
                return null;
            }
            text = fallback;
        }
        int color;
        color = com.beemdevelopment.aegis.helpers.TextDrawableHelper._generator.getColor(text);
        return com.amulyakhare.textdrawable.TextDrawable.builder().beginConfig().width(view.getLayoutParams().width).height(view.getLayoutParams().height).endConfig().buildRound(com.beemdevelopment.aegis.helpers.TextDrawableHelper.getFirstGrapheme(text).toUpperCase(), color);
    }


    private static java.lang.String getFirstGrapheme(java.lang.String text) {
        java.text.BreakIterator iter;
        iter = java.text.BreakIterator.getCharacterInstance();
        iter.setText(text);
        int start;
        start = iter.first();
        int end;
        end = iter.next();
        if (end == java.text.BreakIterator.DONE) {
            return "";
        }
        return text.substring(start, end);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
