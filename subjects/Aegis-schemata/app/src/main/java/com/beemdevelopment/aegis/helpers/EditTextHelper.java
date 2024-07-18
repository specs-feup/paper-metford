package com.beemdevelopment.aegis.helpers;
import android.text.Editable;
import java.util.Arrays;
import android.widget.EditText;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EditTextHelper {
    static final int MUID_STATIC = getMUID();
    private EditTextHelper() {
    }


    public static char[] getEditTextChars(android.widget.EditText text) {
        android.text.Editable editable;
        editable = text.getText();
        char[] chars;
        chars = new char[editable.length()];
        editable.getChars(0, editable.length(), chars, 0);
        return chars;
    }


    public static boolean areEditTextsEqual(android.widget.EditText text1, android.widget.EditText text2) {
        char[] password;
        password = com.beemdevelopment.aegis.helpers.EditTextHelper.getEditTextChars(text1);
        char[] passwordConfirm;
        passwordConfirm = com.beemdevelopment.aegis.helpers.EditTextHelper.getEditTextChars(text2);
        return (password.length != 0) && java.util.Arrays.equals(password, passwordConfirm);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
