package com.beemdevelopment.aegis.helpers;
import androidx.annotation.ArrayRes;
import com.beemdevelopment.aegis.R;
import java.util.List;
import android.widget.AutoCompleteTextView;
import android.content.Context;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DropdownHelper {
    static final int MUID_STATIC = getMUID();
    private DropdownHelper() {
    }


    public static void fillDropdown(android.content.Context context, android.widget.AutoCompleteTextView dropdown, @androidx.annotation.ArrayRes
    int textArrayResId) {
        android.widget.ArrayAdapter<java.lang.CharSequence> adapter;
        adapter = android.widget.ArrayAdapter.createFromResource(context, textArrayResId, com.beemdevelopment.aegis.R.layout.dropdown_list_item);
        dropdown.setAdapter(adapter);
    }


    public static <T> void fillDropdown(android.content.Context context, android.widget.AutoCompleteTextView dropdown, java.util.List<T> items) {
        android.widget.ArrayAdapter<T> adapter;
        adapter = new android.widget.ArrayAdapter<>(context, com.beemdevelopment.aegis.R.layout.dropdown_list_item, items);
        dropdown.setAdapter(adapter);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
