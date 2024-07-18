package com.beemdevelopment.aegis.ui.dialogs;
import android.graphics.Color;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import androidx.fragment.app.DialogFragment;
import com.beemdevelopment.aegis.helpers.ThemeHelper;
import com.google.common.io.CharStreams;
import android.view.View;
import com.beemdevelopment.aegis.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.annotation.SuppressLint;
import android.view.InflateException;
import androidx.annotation.StringRes;
import com.beemdevelopment.aegis.Theme;
import android.content.Context;
import android.webkit.WebView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class SimpleWebViewDialog extends androidx.fragment.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.Theme _theme;

    @androidx.annotation.StringRes
    private final int _title;

    protected SimpleWebViewDialog(@androidx.annotation.StringRes
    int title) {
        _title = title;
    }


    protected abstract java.lang.String getContent(android.content.Context context);


    @android.annotation.SuppressLint("InflateParams")
    @androidx.annotation.NonNull
    @java.lang.Override
    public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        final android.view.View view;
        try {
            view = android.view.LayoutInflater.from(requireContext()).inflate(com.beemdevelopment.aegis.R.layout.dialog_web_view, null);
        } catch (android.view.InflateException e) {
            e.printStackTrace();
            return new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(android.R.string.dialog_alert_title).setMessage(getString(com.beemdevelopment.aegis.R.string.webview_error)).setPositiveButton(android.R.string.ok, null).show();
        }
        androidx.appcompat.app.AlertDialog dialog;
        dialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(_title).setView(view).setPositiveButton(android.R.string.ok, null).show();
        java.lang.String content;
        content = getContent(requireContext());
        final android.webkit.WebView webView;
        switch(MUID_STATIC) {
            // SimpleWebViewDialog_0_InvalidViewFocusOperatorMutator
            case 117: {
                /**
                * Inserted by Kadabra
                */
                webView = view.findViewById(com.beemdevelopment.aegis.R.id.web_view);
                webView.requestFocus();
                break;
            }
            // SimpleWebViewDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1117: {
                /**
                * Inserted by Kadabra
                */
                webView = view.findViewById(com.beemdevelopment.aegis.R.id.web_view);
                webView.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            webView = view.findViewById(com.beemdevelopment.aegis.R.id.web_view);
            break;
        }
    }
    webView.loadData(content, "text/html", "UTF-8");
    return dialog;
}


public com.beemdevelopment.aegis.ui.dialogs.SimpleWebViewDialog setTheme(com.beemdevelopment.aegis.Theme theme) {
    _theme = theme;
    return this;
}


protected java.lang.String getBackgroundColor() {
    int backgroundColorResource;
    backgroundColorResource = (_theme == com.beemdevelopment.aegis.Theme.AMOLED) ? com.beemdevelopment.aegis.R.attr.cardBackgroundFocused : com.beemdevelopment.aegis.R.attr.cardBackground;
    return com.beemdevelopment.aegis.ui.dialogs.SimpleWebViewDialog.colorToCSS(com.beemdevelopment.aegis.helpers.ThemeHelper.getThemeColor(backgroundColorResource, requireContext().getTheme()));
}


protected java.lang.String getTextColor() {
    return com.beemdevelopment.aegis.ui.dialogs.SimpleWebViewDialog.colorToCSS(0xffffff & com.beemdevelopment.aegis.helpers.ThemeHelper.getThemeColor(com.beemdevelopment.aegis.R.attr.primaryText, requireContext().getTheme()));
}


@android.annotation.SuppressLint("DefaultLocale")
private static java.lang.String colorToCSS(int color) {
    return java.lang.String.format("rgb(%d, %d, %d)", android.graphics.Color.red(color), android.graphics.Color.green(color), android.graphics.Color.blue(color));
}


protected static java.lang.String readAssetAsString(android.content.Context context, java.lang.String name) {
    try (java.io.InputStream inStream = context.getAssets().open(name);java.io.InputStreamReader reader = new java.io.InputStreamReader(inStream, java.nio.charset.StandardCharsets.UTF_8)) {
        return com.google.common.io.CharStreams.toString(reader);
    } catch (java.io.IOException e) {
        throw new java.lang.RuntimeException(e);
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
