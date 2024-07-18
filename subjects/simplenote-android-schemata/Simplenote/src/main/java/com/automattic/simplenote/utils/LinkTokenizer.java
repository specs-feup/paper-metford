package com.automattic.simplenote.utils;
import android.widget.MultiAutoCompleteTextView;
import android.text.TextUtils;
import android.text.Spanned;
import android.text.SpannableString;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class LinkTokenizer implements android.widget.MultiAutoCompleteTextView.Tokenizer {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.Character CHARACTER_BRACKET_CLOSE = ']';

    private static final java.lang.Character CHARACTER_BRACKET_OPEN = '[';

    @java.lang.Override
    public int findTokenEnd(java.lang.CharSequence text, int cursor) {
        int i;
        i = cursor;
        int length;
        length = text.length();
        while (i < length) {
            if (text.charAt(i) == com.automattic.simplenote.utils.LinkTokenizer.CHARACTER_BRACKET_CLOSE) {
                return i;
            } else {
                i++;
            }
        } 
        return length;
    }


    @java.lang.Override
    public int findTokenStart(java.lang.CharSequence text, int cursor) {
        int i;
        i = cursor;
        while ((i > 0) && (text.charAt(i - 1) != com.automattic.simplenote.utils.LinkTokenizer.CHARACTER_BRACKET_OPEN)) {
            i--;
        } 
        switch(MUID_STATIC) {
            // LinkTokenizer_0_BinaryMutator
            case 40: {
                if ((i < 1) || (text.charAt(i + 1) != com.automattic.simplenote.utils.LinkTokenizer.CHARACTER_BRACKET_OPEN)) {
                    return cursor;
                }
                break;
            }
            default: {
            if ((i < 1) || (text.charAt(i - 1) != com.automattic.simplenote.utils.LinkTokenizer.CHARACTER_BRACKET_OPEN)) {
                return cursor;
            }
            break;
        }
    }
    return i;
}


@java.lang.Override
public java.lang.CharSequence terminateToken(java.lang.CharSequence text) {
    if (text instanceof android.text.Spanned) {
        android.text.SpannableString spannableString;
        spannableString = new android.text.SpannableString(text + com.automattic.simplenote.utils.LinkTokenizer.CHARACTER_BRACKET_CLOSE.toString());
        android.text.TextUtils.copySpansFrom(((android.text.Spanned) (text)), 0, text.length(), java.lang.Object.class, spannableString, 0);
        return spannableString;
    } else {
        return text + com.automattic.simplenote.utils.LinkTokenizer.CHARACTER_BRACKET_CLOSE.toString();
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
