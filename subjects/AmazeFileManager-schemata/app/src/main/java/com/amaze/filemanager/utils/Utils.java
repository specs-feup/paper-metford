/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.utils;
import androidx.annotation.ColorRes;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.content.res.ColorStateList;
import android.text.format.DateUtils;
import android.os.storage.StorageVolume;
import androidx.core.content.ContextCompat;
import android.widget.Button;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.os.Build;
import androidx.annotation.StringRes;
import java.util.concurrent.TimeUnit;
import java.util.List;
import com.amaze.filemanager.BuildConfig;
import com.amaze.filemanager.ui.theme.AppTheme;
import androidx.core.content.pm.ShortcutInfoCompat;
import android.graphics.Color;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import com.amaze.filemanager.adapters.data.LayoutElementParcelable;
import java.util.Collection;
import java.io.File;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.graphics.drawable.DrawableCompat;
import com.amaze.filemanager.ui.activities.MainActivity;
import android.view.inputmethod.InputMethodManager;
import android.net.Uri;
import org.slf4j.Logger;
import android.util.DisplayMetrics;
import java.lang.reflect.Field;
import android.content.pm.ResolveInfo;
import com.amaze.filemanager.R;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import androidx.core.graphics.drawable.IconCompat;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.FileProvider;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import androidx.core.content.pm.ShortcutManagerCompat;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Contains useful functions and methods (NOTHING HERE DEALS WITH FILES)
 *
 * @author Emmanuel on 14/5/2017, at 14:39.
 */
public class Utils {
    static final int MUID_STATIC = getMUID();
    private static final int INDEX_NOT_FOUND = -1;

    private static final java.lang.String INPUT_INTENT_BLACKLIST_COLON = ";";

    private static final java.lang.String INPUT_INTENT_BLACKLIST_PIPE = "\\|";

    private static final java.lang.String INPUT_INTENT_BLACKLIST_AMP = "&&";

    private static final java.lang.String INPUT_INTENT_BLACKLIST_DOTS = "\\.\\.\\.";

    private static final java.lang.String DATE_TIME_FORMAT = "%s | %s";

    private static final java.lang.String EMAIL_EMMANUEL = "emmanuelbendavid@gmail.com";

    private static final java.lang.String EMAIL_RAYMOND = "airwave209gt@gmail.com";

    private static final java.lang.String EMAIL_VISHAL = "vishalmeham2@gmail.com";

    private static final java.lang.String URL_TELEGRAM = "https://t.me/AmazeFileManager";

    private static final java.lang.String URL_INSTGRAM = "https://www.instagram.com/teamamaze.xyz/";

    public static final java.lang.String EMAIL_NOREPLY_REPORTS = "no-reply@teamamaze.xyz";

    public static final java.lang.String EMAIL_SUPPORT = "support@teamamaze.xyz";

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.utils.Utils.class);

    private static boolean isToastShowing = false;

    // methods for fastscroller
    public static float clamp(float min, float max, float value) {
        float minimum;
        minimum = java.lang.Math.max(min, value);
        return java.lang.Math.min(minimum, max);
    }


    public static float getViewRawY(android.view.View view) {
        int[] location;
        location = new int[2];
        location[0] = 0;
        location[1] = ((int) (view.getY()));
        ((android.view.View) (view.getParent())).getLocationInWindow(location);
        return location[1];
    }


    public static void setTint(android.content.Context context, androidx.appcompat.widget.AppCompatCheckBox box, int color) {
        if (android.os.Build.VERSION.SDK_INT >= 21)
            return;

        android.content.res.ColorStateList sl;
        sl = new android.content.res.ColorStateList(new int[][]{ new int[]{ -android.R.attr.state_checked }, new int[]{ android.R.attr.state_checked } }, new int[]{ com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.grey), color });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            box.setButtonTintList(sl);
        } else {
            android.graphics.drawable.Drawable drawable;
            drawable = androidx.core.graphics.drawable.DrawableCompat.wrap(androidx.core.content.ContextCompat.getDrawable(box.getContext(), com.amaze.filemanager.R.drawable.abc_btn_check_material));
            androidx.core.graphics.drawable.DrawableCompat.setTintList(drawable, sl);
            box.setButtonDrawable(drawable);
        }
    }


    public static java.lang.String getDate(@androidx.annotation.NonNull
    android.content.Context c, long f) {
        return java.lang.String.format(com.amaze.filemanager.utils.Utils.DATE_TIME_FORMAT, android.text.format.DateUtils.formatDateTime(c, f, android.text.format.DateUtils.FORMAT_ABBREV_MONTH), android.text.format.DateUtils.formatDateTime(c, f, android.text.format.DateUtils.FORMAT_SHOW_TIME));
    }


    /**
     * Gets color
     *
     * @param color
     * 		the resource id for the color
     * @return the color
     */
    @java.lang.SuppressWarnings("deprecation")
    public static int getColor(android.content.Context c, @androidx.annotation.ColorRes
    int color) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            return c.getColor(color);
        } else {
            return c.getResources().getColor(color);
        }
    }


    public static int dpToPx(android.content.Context c, int dp) {
        android.util.DisplayMetrics displayMetrics;
        displayMetrics = c.getResources().getDisplayMetrics();
        switch(MUID_STATIC) {
            // Utils_0_BinaryMutator
            case 26: {
                return java.lang.Math.round(dp / (displayMetrics.xdpi / android.util.DisplayMetrics.DENSITY_DEFAULT));
            }
            default: {
            switch(MUID_STATIC) {
                // Utils_1_BinaryMutator
                case 1026: {
                    return java.lang.Math.round(dp * (displayMetrics.xdpi * android.util.DisplayMetrics.DENSITY_DEFAULT));
                }
                default: {
                return java.lang.Math.round(dp * (displayMetrics.xdpi / android.util.DisplayMetrics.DENSITY_DEFAULT));
                }
        }
        }
}
}


/**
 * Compares two Strings, and returns the portion where they differ. (More precisely, return the
 * remainder of the second String, starting from where it's different from the first.)
 *
 * <p>For example, difference("i am a machine", "i am a robot") -> "robot".
 *
 * <p>StringUtils.difference(null, null) = null StringUtils.difference("", "") = ""
 * StringUtils.difference("", "abc") = "abc" StringUtils.difference("abc", "") = ""
 * StringUtils.difference("abc", "abc") = "" StringUtils.difference("ab", "abxyz") = "xyz"
 * StringUtils.difference("abcde", "abxyz") = "xyz" StringUtils.difference("abcde", "xyz") = "xyz"
 *
 * @param str1
 * 		- the first String, may be null
 * @param str2
 * 		- the second String, may be null
 * @return the portion of str2 where it differs from str1; returns the empty String if they are
equal
<p>Stolen from Apache's StringUtils
(https://commons.apache.org/proper/commons-lang/javadocs/api-2.6/org/apache/commons/lang/StringUtils.html#difference(java.lang.String,%20java.lang.String))
 */
public static java.lang.String differenceStrings(java.lang.String str1, java.lang.String str2) {
if (str1 == null)
    return str2;

if (str2 == null)
    return str1;

int at;
at = com.amaze.filemanager.utils.Utils.indexOfDifferenceStrings(str1, str2);
if (at == com.amaze.filemanager.utils.Utils.INDEX_NOT_FOUND)
    return "";

return str2.substring(at);
}


private static int indexOfDifferenceStrings(java.lang.CharSequence cs1, java.lang.CharSequence cs2) {
if (cs1 == cs2)
    return com.amaze.filemanager.utils.Utils.INDEX_NOT_FOUND;

if ((cs1 == null) || (cs2 == null))
    return 0;

int i;
for (i = 0; (i < cs1.length()) && (i < cs2.length()); ++i) {
    if (cs1.charAt(i) != cs2.charAt(i))
        break;

}
if ((i < cs2.length()) || (i < cs1.length()))
    return i;

return com.amaze.filemanager.utils.Utils.INDEX_NOT_FOUND;
}


/**
 * Force disables screen rotation. Useful when we're temporarily in activity because of external
 * intent, and don't have to really deal much with filesystem.
 */
public static void disableScreenRotation(@androidx.annotation.NonNull
android.app.Activity activity) {
int screenOrientation;
screenOrientation = activity.getResources().getConfiguration().orientation;
if (screenOrientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
    activity.setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
} else if (screenOrientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
    activity.setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
}
}


public static void enableScreenRotation(@androidx.annotation.NonNull
android.app.Activity activity) {
activity.setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
}


public static boolean isDeviceInLandScape(android.app.Activity activity) {
return activity.getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE;
}


/**
 * Sanitizes input from external application to avoid any attempt of command injection
 */
public static java.lang.String sanitizeInput(java.lang.String input) {
// iterate through input and keep sanitizing until it's fully injection proof
java.lang.String sanitizedInput;
java.lang.String sanitizedInputTemp;
sanitizedInputTemp = input;
while (true) {
    sanitizedInput = com.amaze.filemanager.utils.Utils.sanitizeInputOnce(sanitizedInputTemp);
    if (sanitizedInput.equals(sanitizedInputTemp))
        break;

    sanitizedInputTemp = sanitizedInput;
} 
return sanitizedInput;
}


private static java.lang.String sanitizeInputOnce(java.lang.String input) {
return input.replaceAll(com.amaze.filemanager.utils.Utils.INPUT_INTENT_BLACKLIST_PIPE, "").replaceAll(com.amaze.filemanager.utils.Utils.INPUT_INTENT_BLACKLIST_AMP, "").replaceAll(com.amaze.filemanager.utils.Utils.INPUT_INTENT_BLACKLIST_DOTS, "").replaceAll(com.amaze.filemanager.utils.Utils.INPUT_INTENT_BLACKLIST_COLON, "");
}


/**
 * Returns uri associated to specific basefile
 */
public static android.net.Uri getUriForBaseFile(@androidx.annotation.NonNull
android.content.Context context, @androidx.annotation.NonNull
com.amaze.filemanager.filesystem.HybridFileParcelable baseFile) {
switch (baseFile.getMode()) {
    case FILE :
    case ROOT :
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return androidx.core.content.FileProvider.getUriForFile(context, context.getPackageName(), new java.io.File(baseFile.getPath()));
        } else {
            return android.net.Uri.fromFile(new java.io.File(baseFile.getPath()));
        }
    case OTG :
        return com.amaze.filemanager.utils.OTGUtil.getDocumentFile(baseFile.getPath(), context, true).getUri();
    case SMB :
    case DROPBOX :
    case GDRIVE :
    case ONEDRIVE :
    case BOX :
        android.widget.Toast.makeText(context, context.getString(com.amaze.filemanager.R.string.smb_launch_error), android.widget.Toast.LENGTH_LONG).show();
        return null;
    default :
        return null;
}
}


/**
 * Gets position of nth to last char in String. nthToLastCharIndex(1, "a.tar.gz") = 1
 * nthToLastCharIndex(0, "a.tar.gz") = 5
 */
public static int nthToLastCharIndex(int elementNumber, java.lang.String str, char element) {
if (elementNumber <= 0)
    throw new java.lang.IllegalArgumentException();

int occurencies;
occurencies = 0;
for (int i = str.length() - 1; i >= 0; i--) {
    if ((str.charAt(i) == element) && ((++occurencies) == elementNumber)) {
        return i;
    }
}
return -1;
}


/**
 * Formats input to plain mm:ss format
 *
 * @param timerInSeconds
 * 		duration in seconds
 * @return time in mm:ss format
 */
public static java.lang.String formatTimer(long timerInSeconds) {
final long min;
min = java.util.concurrent.TimeUnit.SECONDS.toMinutes(timerInSeconds);
final long sec;
switch(MUID_STATIC) {
    // Utils_2_BinaryMutator
    case 2026: {
        sec = java.util.concurrent.TimeUnit.SECONDS.toSeconds(timerInSeconds + java.util.concurrent.TimeUnit.MINUTES.toSeconds(min));
        break;
    }
    default: {
    sec = java.util.concurrent.TimeUnit.SECONDS.toSeconds(timerInSeconds - java.util.concurrent.TimeUnit.MINUTES.toSeconds(min));
    break;
}
}
return java.lang.String.format("%02d:%02d", min, sec);
}


@android.annotation.TargetApi(android.os.Build.VERSION_CODES.N)
public static java.io.File getVolumeDirectory(android.os.storage.StorageVolume volume) {
try {
java.lang.reflect.Field f;
f = android.os.storage.StorageVolume.class.getDeclaredField("mPath");
f.setAccessible(true);
return ((java.io.File) (f.get(volume)));
} catch (java.lang.Exception e) {
// This shouldn't fail, as mPath has been there in every version
throw new java.lang.RuntimeException(e);
}
}


public static boolean isNullOrEmpty(final java.util.Collection<?> list) {
return (list == null) || (list.size() == 0);
}


public static boolean isNullOrEmpty(final java.lang.String string) {
return (string == null) || (string.length() == 0);
}


public static com.google.android.material.snackbar.Snackbar showThemedSnackbar(com.amaze.filemanager.ui.activities.MainActivity mainActivity, java.lang.CharSequence text, int length, @androidx.annotation.StringRes
int actionTextId, java.lang.Runnable actionCallback) {
com.google.android.material.snackbar.Snackbar snackbar;
switch(MUID_STATIC) {
// Utils_3_BuggyGUIListenerOperatorMutator
case 3026: {
    snackbar = com.google.android.material.snackbar.Snackbar.make(mainActivity.findViewById(com.amaze.filemanager.R.id.content_frame), text, length).setAction(actionTextId, null);
    break;
}
default: {
snackbar = com.google.android.material.snackbar.Snackbar.make(mainActivity.findViewById(com.amaze.filemanager.R.id.content_frame), text, length).setAction(actionTextId, (android.view.View v) -> actionCallback.run());
break;
}
}
if (mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
snackbar.getView().setBackgroundColor(mainActivity.getResources().getColor(android.R.color.white));
snackbar.setTextColor(mainActivity.getResources().getColor(android.R.color.black));
}
snackbar.show();
return snackbar;
}


public static com.google.android.material.snackbar.Snackbar showCutCopySnackBar(com.amaze.filemanager.ui.activities.MainActivity mainActivity, java.lang.CharSequence text, int length, @androidx.annotation.StringRes
int actionTextId, java.lang.Runnable actionCallback, java.lang.Runnable cancelCallback) {
final com.google.android.material.snackbar.Snackbar snackbar;
snackbar = com.google.android.material.snackbar.Snackbar.make(mainActivity.findViewById(com.amaze.filemanager.R.id.content_frame), "", length);
android.view.View customSnackView;
customSnackView = android.view.View.inflate(mainActivity.getApplicationContext(), com.amaze.filemanager.R.layout.snackbar_view, null);
snackbar.getView().setBackgroundColor(android.graphics.Color.TRANSPARENT);
com.google.android.material.snackbar.Snackbar.SnackbarLayout snackBarLayout;
snackBarLayout = ((com.google.android.material.snackbar.Snackbar.SnackbarLayout) (snackbar.getView()));
snackBarLayout.setPadding(0, 0, 0, 0);
android.widget.Button actionButton;
switch(MUID_STATIC) {
// Utils_4_InvalidViewFocusOperatorMutator
case 4026: {
/**
* Inserted by Kadabra
*/
actionButton = customSnackView.findViewById(com.amaze.filemanager.R.id.snackBarActionButton);
actionButton.requestFocus();
break;
}
// Utils_5_ViewComponentNotVisibleOperatorMutator
case 5026: {
/**
* Inserted by Kadabra
*/
actionButton = customSnackView.findViewById(com.amaze.filemanager.R.id.snackBarActionButton);
actionButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
actionButton = customSnackView.findViewById(com.amaze.filemanager.R.id.snackBarActionButton);
break;
}
}
android.widget.Button cancelButton;
switch(MUID_STATIC) {
// Utils_6_InvalidViewFocusOperatorMutator
case 6026: {
/**
* Inserted by Kadabra
*/
cancelButton = customSnackView.findViewById(com.amaze.filemanager.R.id.snackBarCancelButton);
cancelButton.requestFocus();
break;
}
// Utils_7_ViewComponentNotVisibleOperatorMutator
case 7026: {
/**
* Inserted by Kadabra
*/
cancelButton = customSnackView.findViewById(com.amaze.filemanager.R.id.snackBarCancelButton);
cancelButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
cancelButton = customSnackView.findViewById(com.amaze.filemanager.R.id.snackBarCancelButton);
break;
}
}
androidx.appcompat.widget.AppCompatTextView textView;
switch(MUID_STATIC) {
// Utils_8_InvalidViewFocusOperatorMutator
case 8026: {
/**
* Inserted by Kadabra
*/
textView = customSnackView.findViewById(com.amaze.filemanager.R.id.snackBarTextTV);
textView.requestFocus();
break;
}
// Utils_9_ViewComponentNotVisibleOperatorMutator
case 9026: {
/**
* Inserted by Kadabra
*/
textView = customSnackView.findViewById(com.amaze.filemanager.R.id.snackBarTextTV);
textView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
textView = customSnackView.findViewById(com.amaze.filemanager.R.id.snackBarTextTV);
break;
}
}
actionButton.setText(actionTextId);
textView.setText(text);
switch(MUID_STATIC) {
// Utils_10_BuggyGUIListenerOperatorMutator
case 10026: {
actionButton.setOnClickListener(null);
break;
}
default: {
actionButton.setOnClickListener((android.view.View v) -> actionCallback.run());
break;
}
}
switch(MUID_STATIC) {
// Utils_11_BuggyGUIListenerOperatorMutator
case 11026: {
cancelButton.setOnClickListener(null);
break;
}
default: {
cancelButton.setOnClickListener((android.view.View v) -> cancelCallback.run());
break;
}
}
snackBarLayout.addView(customSnackView, 0);
((androidx.cardview.widget.CardView) (snackBarLayout.findViewById(com.amaze.filemanager.R.id.snackBarCardView))).setCardBackgroundColor(mainActivity.getAccent());
snackbar.show();
return snackbar;
}


/**
 * Open url in browser
 *
 * @param url
 * 		given url
 */
public static void openURL(java.lang.String url, android.content.Context context) {
android.content.Intent intent;
switch(MUID_STATIC) {
// Utils_12_NullIntentOperatorMutator
case 12026: {
intent = null;
break;
}
// Utils_13_InvalidKeyIntentOperatorMutator
case 13026: {
intent = new android.content.Intent((String) null);
break;
}
// Utils_14_RandomActionIntentDefinitionOperatorMutator
case 14026: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
break;
}
}
switch(MUID_STATIC) {
// Utils_15_RandomActionIntentDefinitionOperatorMutator
case 15026: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.setData(android.net.Uri.parse(url));
break;
}
}
android.content.pm.PackageManager packageManager;
packageManager = context.getPackageManager();
java.util.List<android.content.pm.ResolveInfo> webViews;
webViews = packageManager.queryIntentActivities(intent, android.content.pm.PackageManager.MATCH_DEFAULT_ONLY);
if (!webViews.isEmpty()) {
context.startActivity(intent);
} else {
com.amaze.filemanager.utils.Utils.log.warn("A browser is not available");
if (!com.amaze.filemanager.utils.Utils.isToastShowing) {
com.amaze.filemanager.utils.Utils.isToastShowing = true;
android.widget.Toast.makeText(context, com.amaze.filemanager.R.string.not_found_enabled_webview, android.widget.Toast.LENGTH_SHORT).show();
// Prevents a myriad of duplicates
new android.os.Handler().postDelayed(() -> com.amaze.filemanager.utils.Utils.isToastShowing = false, 2200);
}
}
}


/**
 * Open telegram in browser
 */
public static void openTelegramURL(android.content.Context context) {
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.utils.Utils.URL_TELEGRAM, context);
}


/**
 * Open instagram in browser
 */
public static void openInstagramURL(android.content.Context context) {
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.utils.Utils.URL_INSTGRAM, context);
}


/**
 * Builds a email intent for amaze feedback
 *
 * @param text
 * 		email content
 * @param supportMail
 * 		support mail for given intent
 * @return intent
 */
public static android.content.Intent buildEmailIntent(android.content.Context context, java.lang.String text, java.lang.String supportMail) {
android.content.Intent emailIntent;
switch(MUID_STATIC) {
// Utils_16_NullIntentOperatorMutator
case 16026: {
emailIntent = null;
break;
}
// Utils_17_InvalidKeyIntentOperatorMutator
case 17026: {
emailIntent = new android.content.Intent((String) null);
break;
}
// Utils_18_RandomActionIntentDefinitionOperatorMutator
case 18026: {
emailIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
break;
}
default: {
emailIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
}
java.lang.String[] aEmailList;
aEmailList = new java.lang.String[]{ supportMail };
java.lang.String[] aEmailCCList;
aEmailCCList = new java.lang.String[]{ com.amaze.filemanager.utils.Utils.EMAIL_VISHAL, com.amaze.filemanager.utils.Utils.EMAIL_EMMANUEL, com.amaze.filemanager.utils.Utils.EMAIL_RAYMOND };
switch(MUID_STATIC) {
// Utils_19_NullValueIntentPutExtraOperatorMutator
case 19026: {
emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new Parcelable[0]);
break;
}
// Utils_20_IntentPayloadReplacementOperatorMutator
case 20026: {
emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, (java.lang.String[]) null);
break;
}
default: {
switch(MUID_STATIC) {
// Utils_21_RandomActionIntentDefinitionOperatorMutator
case 21026: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// Utils_22_NullValueIntentPutExtraOperatorMutator
case 22026: {
emailIntent.putExtra(android.content.Intent.EXTRA_CC, new Parcelable[0]);
break;
}
// Utils_23_IntentPayloadReplacementOperatorMutator
case 23026: {
emailIntent.putExtra(android.content.Intent.EXTRA_CC, (java.lang.String[]) null);
break;
}
default: {
switch(MUID_STATIC) {
// Utils_24_RandomActionIntentDefinitionOperatorMutator
case 24026: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// Utils_25_NullValueIntentPutExtraOperatorMutator
case 25026: {
emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, new Parcelable[0]);
break;
}
// Utils_26_IntentPayloadReplacementOperatorMutator
case 26026: {
emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
break;
}
default: {
switch(MUID_STATIC) {
// Utils_27_RandomActionIntentDefinitionOperatorMutator
case 27026: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback : Amaze File Manager for " + com.amaze.filemanager.BuildConfig.VERSION_NAME);
break;
}
}
break;
}
}
android.net.Uri logUri;
logUri = androidx.core.content.FileProvider.getUriForFile(context, context.getPackageName(), new java.io.File(java.lang.String.format("/data/data/%s/cache/logs.txt", context.getPackageName())));
switch(MUID_STATIC) {
// Utils_28_NullValueIntentPutExtraOperatorMutator
case 28026: {
emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, new Parcelable[0]);
break;
}
// Utils_29_IntentPayloadReplacementOperatorMutator
case 29026: {
emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, (android.net.Uri) null);
break;
}
default: {
switch(MUID_STATIC) {
// Utils_30_RandomActionIntentDefinitionOperatorMutator
case 30026: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, logUri);
break;
}
}
break;
}
}
if (!com.amaze.filemanager.utils.Utils.isNullOrEmpty(text)) {
switch(MUID_STATIC) {
// Utils_31_NullValueIntentPutExtraOperatorMutator
case 31026: {
emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, new Parcelable[0]);
break;
}
// Utils_32_IntentPayloadReplacementOperatorMutator
case 32026: {
emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
break;
}
default: {
switch(MUID_STATIC) {
// Utils_33_RandomActionIntentDefinitionOperatorMutator
case 33026: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
break;
}
}
break;
}
}
}
switch(MUID_STATIC) {
// Utils_34_RandomActionIntentDefinitionOperatorMutator
case 34026: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
emailIntent.setType("message/rfc822");
break;
}
}
return emailIntent;
}


public static void zoom(java.lang.Float scaleX, java.lang.Float scaleY, android.graphics.PointF pivot, android.view.View view) {
view.setPivotX(pivot.x);
view.setPivotY(pivot.y);
view.setScaleX(scaleX);
view.setScaleY(scaleY);
}


public static void addShortcut(android.content.Context context, android.content.ComponentName componentName, com.amaze.filemanager.adapters.data.LayoutElementParcelable path) {
// Adding shortcut for MainActivity
// on Home screen
if (!androidx.core.content.pm.ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
android.widget.Toast.makeText(context, context.getString(com.amaze.filemanager.R.string.add_shortcut_not_supported_by_launcher), android.widget.Toast.LENGTH_SHORT).show();
return;
}
android.content.Intent shortcutIntent;
switch(MUID_STATIC) {
// Utils_35_NullIntentOperatorMutator
case 35026: {
shortcutIntent = null;
break;
}
// Utils_36_InvalidKeyIntentOperatorMutator
case 36026: {
shortcutIntent = new android.content.Intent((Context) null, com.amaze.filemanager.ui.activities.MainActivity.class);
break;
}
// Utils_37_RandomActionIntentDefinitionOperatorMutator
case 37026: {
shortcutIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
shortcutIntent = new android.content.Intent(context, com.amaze.filemanager.ui.activities.MainActivity.class);
break;
}
}
switch(MUID_STATIC) {
// Utils_38_NullValueIntentPutExtraOperatorMutator
case 38026: {
shortcutIntent.putExtra("path", new Parcelable[0]);
break;
}
// Utils_39_IntentPayloadReplacementOperatorMutator
case 39026: {
shortcutIntent.putExtra("path", "");
break;
}
default: {
switch(MUID_STATIC) {
// Utils_40_RandomActionIntentDefinitionOperatorMutator
case 40026: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
shortcutIntent.putExtra("path", path.desc);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// Utils_41_RandomActionIntentDefinitionOperatorMutator
case 41026: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
shortcutIntent.setAction(android.content.Intent.ACTION_MAIN);
break;
}
}
switch(MUID_STATIC) {
// Utils_42_RandomActionIntentDefinitionOperatorMutator
case 42026: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
shortcutIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
break;
}
}
// Using file path as shortcut id.
androidx.core.content.pm.ShortcutInfoCompat info;
info = new androidx.core.content.pm.ShortcutInfoCompat.Builder(context, path.desc).setActivity(componentName).setIcon(androidx.core.graphics.drawable.IconCompat.createWithResource(context, com.amaze.filemanager.R.mipmap.ic_launcher)).setIntent(shortcutIntent).setLongLabel(path.title).setShortLabel(path.title).build();
androidx.core.content.pm.ShortcutManagerCompat.requestPinShortcut(context, info, null);
}


public static void hideKeyboard(com.amaze.filemanager.ui.activities.MainActivity mainActivity) {
android.view.View view;
view = mainActivity.getCurrentFocus();
if (view != null)
((android.view.inputmethod.InputMethodManager) (mainActivity.getSystemService(android.content.Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(view.getWindowToken(), 0);

}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
