package com.automattic.simplenote.utils;
import androidx.appcompat.app.AlertDialog;
import com.automattic.simplenote.R;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import android.view.ContextThemeWrapper;
import android.content.Intent;
import android.widget.Toast;
import android.content.ClipData;
import android.net.Uri;
import android.content.ClipboardManager;
import android.content.Context;
import android.webkit.WebView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BrowserUtils {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String URL_WEB_VIEW = "https://play.google.com/store/apps/details?id=com.google.android.webview";

    public static boolean isBrowserInstalled(android.content.Context context) {
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // BrowserUtils_0_NullIntentOperatorMutator
            case 47: {
                intent = null;
                break;
            }
            // BrowserUtils_1_InvalidKeyIntentOperatorMutator
            case 147: {
                intent = new android.content.Intent((String) null, android.net.Uri.parse(context.getString(com.automattic.simplenote.R.string.simperium_url)));
                break;
            }
            // BrowserUtils_2_RandomActionIntentDefinitionOperatorMutator
            case 247: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(context.getString(com.automattic.simplenote.R.string.simperium_url)));
            break;
        }
    }
    return intent.resolveActivity(context.getPackageManager()) != null;
}


public static boolean isWebViewInstalled(android.content.Context context) {
    try {
        new android.webkit.WebView(context);
        return true;
    } catch (java.lang.Exception exception) {
        return false;
    }
}


public static boolean copyToClipboard(android.content.Context base, java.lang.String url) {
    android.content.Context context;
    context = new android.view.ContextThemeWrapper(base, base.getTheme());
    try {
        android.content.ClipboardManager clipboard;
        clipboard = ((android.content.ClipboardManager) (context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
        android.content.ClipData clip;
        clip = android.content.ClipData.newPlainText(context.getString(com.automattic.simplenote.R.string.app_name), url);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            return true;
        } else {
            return false;
        }
    } catch (java.lang.Exception e) {
        return false;
    }
}


public static void launchBrowserOrShowError(@androidx.annotation.NonNull
android.content.Context context, java.lang.String url) {
    if (com.automattic.simplenote.utils.BrowserUtils.isBrowserInstalled(context)) {
        context.startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url)));
    } else {
        com.automattic.simplenote.utils.BrowserUtils.showDialogErrorBrowser(context, url);
    }
}


public static void showDialogErrorBrowser(android.content.Context base, final java.lang.String url) {
    final android.content.Context context;
    context = new android.view.ContextThemeWrapper(base, base.getTheme());
    switch(MUID_STATIC) {
        // BrowserUtils_3_BuggyGUIListenerOperatorMutator
        case 347: {
            new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.automattic.simplenote.R.string.simperium_dialog_title_error_browser).setMessage(com.automattic.simplenote.R.string.simperium_error_browser).setNeutralButton(com.automattic.simplenote.R.string.simperium_dialog_button_copy_url, null).setPositiveButton(android.R.string.ok, null).show();
            break;
        }
        default: {
        new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.automattic.simplenote.R.string.simperium_dialog_title_error_browser).setMessage(com.automattic.simplenote.R.string.simperium_error_browser).setNeutralButton(com.automattic.simplenote.R.string.simperium_dialog_button_copy_url, new android.content.DialogInterface.OnClickListener() {
            @java.lang.Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                switch(MUID_STATIC) {
                    // BrowserUtils_4_LengthyGUIListenerOperatorMutator
                    case 447: {
                        /**
                        * Inserted by Kadabra
                        */
                        if (com.automattic.simplenote.utils.BrowserUtils.copyToClipboard(context, url)) {
                            android.widget.Toast.makeText(context, com.automattic.simplenote.R.string.simperium_error_browser_copy_success, android.widget.Toast.LENGTH_SHORT).show();
                        } else {
                            android.widget.Toast.makeText(context, com.automattic.simplenote.R.string.simperium_error_browser_copy_failure, android.widget.Toast.LENGTH_SHORT).show();
                        }
                        try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                        break;
                    }
                    default: {
                    if (com.automattic.simplenote.utils.BrowserUtils.copyToClipboard(context, url)) {
                        android.widget.Toast.makeText(context, com.automattic.simplenote.R.string.simperium_error_browser_copy_success, android.widget.Toast.LENGTH_SHORT).show();
                    } else {
                        android.widget.Toast.makeText(context, com.automattic.simplenote.R.string.simperium_error_browser_copy_failure, android.widget.Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }

    }).setPositiveButton(android.R.string.ok, null).show();
    break;
}
}
}


public static void showDialogErrorException(android.content.Context base, final java.lang.String url) {
final android.content.Context context;
context = new android.view.ContextThemeWrapper(base, base.getTheme());
switch(MUID_STATIC) {
// BrowserUtils_5_BuggyGUIListenerOperatorMutator
case 547: {
    new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.automattic.simplenote.R.string.dialog_browser_exception_title).setMessage(com.automattic.simplenote.R.string.dialog_browser_exception_message).setNeutralButton(com.automattic.simplenote.R.string.dialog_browser_exception_button_copy_url, null).setPositiveButton(android.R.string.ok, null).show();
    break;
}
default: {
new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.automattic.simplenote.R.string.dialog_browser_exception_title).setMessage(com.automattic.simplenote.R.string.dialog_browser_exception_message).setNeutralButton(com.automattic.simplenote.R.string.dialog_browser_exception_button_copy_url, new android.content.DialogInterface.OnClickListener() {
    @java.lang.Override
    public void onClick(android.content.DialogInterface dialog, int which) {
        switch(MUID_STATIC) {
            // BrowserUtils_6_LengthyGUIListenerOperatorMutator
            case 647: {
                /**
                * Inserted by Kadabra
                */
                if (com.automattic.simplenote.utils.BrowserUtils.copyToClipboard(context, url)) {
                    android.widget.Toast.makeText(context, com.automattic.simplenote.R.string.dialog_browser_exception_toast_copy_success, android.widget.Toast.LENGTH_SHORT).show();
                } else {
                    android.widget.Toast.makeText(context, com.automattic.simplenote.R.string.dialog_browser_exception_toast_copy_failure, android.widget.Toast.LENGTH_SHORT).show();
                }
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            if (com.automattic.simplenote.utils.BrowserUtils.copyToClipboard(context, url)) {
                android.widget.Toast.makeText(context, com.automattic.simplenote.R.string.dialog_browser_exception_toast_copy_success, android.widget.Toast.LENGTH_SHORT).show();
            } else {
                android.widget.Toast.makeText(context, com.automattic.simplenote.R.string.dialog_browser_exception_toast_copy_failure, android.widget.Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
}

}).setPositiveButton(android.R.string.ok, null).show();
break;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
