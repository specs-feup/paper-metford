package com.automattic.simplenote.utils;
import androidx.appcompat.app.AlertDialog;
import com.automattic.simplenote.R;
import androidx.appcompat.view.ContextThemeWrapper;
import android.text.method.LinkMovementMethod;
import java.util.Objects;
import android.widget.TextView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DialogUtils {
    static final int MUID_STATIC = getMUID();
    /**
     * Show an alert dialog with a link to the support@simplenote.com email address,
     * which can be tapped to launch the device's email app.
     *
     * @param context
     * 		{@link Context} from which to determine theme and resources.
     * @param message
     * 		{@link String} for the dialog message.
     */
    public static void showDialogWithEmail(android.content.Context context, java.lang.String message) {
        final androidx.appcompat.app.AlertDialog dialog;
        dialog = new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(context, com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.error).setMessage(com.automattic.simplenote.utils.HtmlCompat.fromHtml(java.lang.String.format(message, context.getString(com.automattic.simplenote.R.string.support_email), "<span style=\"color:#", java.lang.Integer.toHexString(com.automattic.simplenote.utils.ThemeUtils.getColorFromAttribute(context, com.automattic.simplenote.R.attr.colorAccent) & 0xffffff), "\">", "</span>"))).setPositiveButton(android.R.string.ok, null).show();
        ((android.widget.TextView) (java.util.Objects.requireNonNull(dialog.findViewById(android.R.id.message)))).setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
