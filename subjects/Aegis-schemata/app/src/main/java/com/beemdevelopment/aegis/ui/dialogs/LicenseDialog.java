package com.beemdevelopment.aegis.ui.dialogs;
import com.beemdevelopment.aegis.R;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class LicenseDialog extends com.beemdevelopment.aegis.ui.dialogs.SimpleWebViewDialog {
    static final int MUID_STATIC = getMUID();
    private LicenseDialog() {
        super(com.beemdevelopment.aegis.R.string.license);
    }


    public static com.beemdevelopment.aegis.ui.dialogs.LicenseDialog create() {
        return new com.beemdevelopment.aegis.ui.dialogs.LicenseDialog();
    }


    @java.lang.Override
    protected java.lang.String getContent(android.content.Context context) {
        java.lang.String license;
        license = com.beemdevelopment.aegis.ui.dialogs.SimpleWebViewDialog.readAssetAsString(context, "LICENSE");
        java.lang.String html;
        html = com.beemdevelopment.aegis.ui.dialogs.SimpleWebViewDialog.readAssetAsString(context, "license.html");
        return java.lang.String.format(html, license, getBackgroundColor(), getTextColor());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
