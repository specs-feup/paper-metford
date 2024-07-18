package com.automattic.simplenote;
import androidx.appcompat.app.AlertDialog;
import java.util.Locale;
import android.content.DialogInterface;
import com.automattic.simplenote.widgets.SpinningImageButton.SpeedListener;
import com.automattic.simplenote.utils.ThemeUtils;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;
import com.automattic.simplenote.utils.PrefUtils;
import com.automattic.simplenote.utils.HtmlCompat;
import java.util.Calendar;
import android.os.Bundle;
import android.view.ViewGroup;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.LayoutInflater;
import android.content.ActivityNotFoundException;
import com.automattic.simplenote.widgets.SpinningImageButton;
import java.util.Objects;
import androidx.annotation.Nullable;
import com.automattic.simplenote.utils.BrowserUtils;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AboutFragment extends androidx.fragment.app.Fragment implements com.automattic.simplenote.widgets.SpinningImageButton.SpeedListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PLAY_STORE_URL = "http://play.google.com/store/apps/details?id=";

    private static final java.lang.String PLAY_STORE_URI = "market://details?id=";

    private static final java.lang.String SIMPLENOTE_BLOG_URL = "https://simplenote.com/blog";

    private static final java.lang.String SIMPLENOTE_HELP_URL = "https://simplenote.com/help";

    private static final java.lang.String SIMPLENOTE_HIRING_HANDLE = "https://automattic.com/work-with-us/";

    private static final java.lang.String SIMPLENOTE_TWITTER_HANDLE = "simplenoteapp";

    private static final java.lang.String TWITTER_PROFILE_URL = "https://twitter.com/#!/";

    private static final java.lang.String TWITTER_APP_URI = "twitter://user?screen_name=";

    private static final java.lang.String URL_CALIFORNIA = "https://automattic.com/privacy/#california-consumer-privacy-act-ccpa";

    private static final java.lang.String URL_CONTRIBUTE = "https://github.com/Automattic/simplenote-android";

    private static final java.lang.String URL_PRIVACY = "https://automattic.com/privacy";

    private static final java.lang.String URL_TERMS = "https://simplenote.com/terms";

    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        android.view.View view;
        view = inflater.inflate(com.automattic.simplenote.R.layout.fragment_about, container, false);
        android.widget.TextView version;
        switch(MUID_STATIC) {
            // AboutFragment_0_InvalidViewFocusOperatorMutator
            case 72: {
                /**
                * Inserted by Kadabra
                */
                version = view.findViewById(com.automattic.simplenote.R.id.about_version);
                version.requestFocus();
                break;
            }
            // AboutFragment_1_ViewComponentNotVisibleOperatorMutator
            case 172: {
                /**
                * Inserted by Kadabra
                */
                version = view.findViewById(com.automattic.simplenote.R.id.about_version);
                version.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            version = view.findViewById(com.automattic.simplenote.R.id.about_version);
            break;
        }
    }
    android.view.View blog;
    switch(MUID_STATIC) {
        // AboutFragment_2_InvalidViewFocusOperatorMutator
        case 272: {
            /**
            * Inserted by Kadabra
            */
            blog = view.findViewById(com.automattic.simplenote.R.id.about_blog);
            blog.requestFocus();
            break;
        }
        // AboutFragment_3_ViewComponentNotVisibleOperatorMutator
        case 372: {
            /**
            * Inserted by Kadabra
            */
            blog = view.findViewById(com.automattic.simplenote.R.id.about_blog);
            blog.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        blog = view.findViewById(com.automattic.simplenote.R.id.about_blog);
        break;
    }
}
android.view.View twitter;
switch(MUID_STATIC) {
    // AboutFragment_4_InvalidViewFocusOperatorMutator
    case 472: {
        /**
        * Inserted by Kadabra
        */
        twitter = view.findViewById(com.automattic.simplenote.R.id.about_twitter);
        twitter.requestFocus();
        break;
    }
    // AboutFragment_5_ViewComponentNotVisibleOperatorMutator
    case 572: {
        /**
        * Inserted by Kadabra
        */
        twitter = view.findViewById(com.automattic.simplenote.R.id.about_twitter);
        twitter.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    twitter = view.findViewById(com.automattic.simplenote.R.id.about_twitter);
    break;
}
}
android.view.View help;
switch(MUID_STATIC) {
// AboutFragment_6_InvalidViewFocusOperatorMutator
case 672: {
    /**
    * Inserted by Kadabra
    */
    help = view.findViewById(com.automattic.simplenote.R.id.about_help);
    help.requestFocus();
    break;
}
// AboutFragment_7_ViewComponentNotVisibleOperatorMutator
case 772: {
    /**
    * Inserted by Kadabra
    */
    help = view.findViewById(com.automattic.simplenote.R.id.about_help);
    help.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
help = view.findViewById(com.automattic.simplenote.R.id.about_help);
break;
}
}
android.view.View store;
switch(MUID_STATIC) {
// AboutFragment_8_InvalidViewFocusOperatorMutator
case 872: {
/**
* Inserted by Kadabra
*/
store = view.findViewById(com.automattic.simplenote.R.id.about_store);
store.requestFocus();
break;
}
// AboutFragment_9_ViewComponentNotVisibleOperatorMutator
case 972: {
/**
* Inserted by Kadabra
*/
store = view.findViewById(com.automattic.simplenote.R.id.about_store);
store.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
store = view.findViewById(com.automattic.simplenote.R.id.about_store);
break;
}
}
android.view.View contribute;
switch(MUID_STATIC) {
// AboutFragment_10_InvalidViewFocusOperatorMutator
case 1072: {
/**
* Inserted by Kadabra
*/
contribute = view.findViewById(com.automattic.simplenote.R.id.about_contribute);
contribute.requestFocus();
break;
}
// AboutFragment_11_ViewComponentNotVisibleOperatorMutator
case 1172: {
/**
* Inserted by Kadabra
*/
contribute = view.findViewById(com.automattic.simplenote.R.id.about_contribute);
contribute.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
contribute = view.findViewById(com.automattic.simplenote.R.id.about_contribute);
break;
}
}
android.view.View hiring;
switch(MUID_STATIC) {
// AboutFragment_12_InvalidViewFocusOperatorMutator
case 1272: {
/**
* Inserted by Kadabra
*/
hiring = view.findViewById(com.automattic.simplenote.R.id.about_careers);
hiring.requestFocus();
break;
}
// AboutFragment_13_ViewComponentNotVisibleOperatorMutator
case 1372: {
/**
* Inserted by Kadabra
*/
hiring = view.findViewById(com.automattic.simplenote.R.id.about_careers);
hiring.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
hiring = view.findViewById(com.automattic.simplenote.R.id.about_careers);
break;
}
}
android.widget.TextView privacy;
switch(MUID_STATIC) {
// AboutFragment_14_InvalidViewFocusOperatorMutator
case 1472: {
/**
* Inserted by Kadabra
*/
privacy = view.findViewById(com.automattic.simplenote.R.id.about_privacy);
privacy.requestFocus();
break;
}
// AboutFragment_15_ViewComponentNotVisibleOperatorMutator
case 1572: {
/**
* Inserted by Kadabra
*/
privacy = view.findViewById(com.automattic.simplenote.R.id.about_privacy);
privacy.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
privacy = view.findViewById(com.automattic.simplenote.R.id.about_privacy);
break;
}
}
android.widget.TextView terms;
switch(MUID_STATIC) {
// AboutFragment_16_InvalidViewFocusOperatorMutator
case 1672: {
/**
* Inserted by Kadabra
*/
terms = view.findViewById(com.automattic.simplenote.R.id.about_terms);
terms.requestFocus();
break;
}
// AboutFragment_17_ViewComponentNotVisibleOperatorMutator
case 1772: {
/**
* Inserted by Kadabra
*/
terms = view.findViewById(com.automattic.simplenote.R.id.about_terms);
terms.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
terms = view.findViewById(com.automattic.simplenote.R.id.about_terms);
break;
}
}
android.widget.TextView california;
switch(MUID_STATIC) {
// AboutFragment_18_InvalidViewFocusOperatorMutator
case 1872: {
/**
* Inserted by Kadabra
*/
california = view.findViewById(com.automattic.simplenote.R.id.about_california);
california.requestFocus();
break;
}
// AboutFragment_19_ViewComponentNotVisibleOperatorMutator
case 1972: {
/**
* Inserted by Kadabra
*/
california = view.findViewById(com.automattic.simplenote.R.id.about_california);
california.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
california = view.findViewById(com.automattic.simplenote.R.id.about_california);
break;
}
}
android.widget.TextView copyright;
switch(MUID_STATIC) {
// AboutFragment_20_InvalidViewFocusOperatorMutator
case 2072: {
/**
* Inserted by Kadabra
*/
copyright = view.findViewById(com.automattic.simplenote.R.id.about_copyright);
copyright.requestFocus();
break;
}
// AboutFragment_21_ViewComponentNotVisibleOperatorMutator
case 2172: {
/**
* Inserted by Kadabra
*/
copyright = view.findViewById(com.automattic.simplenote.R.id.about_copyright);
copyright.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
copyright = view.findViewById(com.automattic.simplenote.R.id.about_copyright);
break;
}
}
java.lang.String colorLink;
colorLink = java.lang.Integer.toHexString(androidx.core.content.ContextCompat.getColor(requireContext(), com.automattic.simplenote.R.color.blue_5) & 0xffffff);
version.setText(com.automattic.simplenote.utils.PrefUtils.versionInfo());
int thisYear;
thisYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
copyright.setText(java.lang.String.format(java.util.Locale.getDefault(), getString(com.automattic.simplenote.R.string.about_copyright), thisYear));
switch(MUID_STATIC) {
// AboutFragment_22_BuggyGUIListenerOperatorMutator
case 2272: {
blog.setOnClickListener(null);
break;
}
default: {
blog.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// AboutFragment_23_LengthyGUIListenerOperatorMutator
case 2372: {
/**
* Inserted by Kadabra
*/
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.SIMPLENOTE_BLOG_URL);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.SIMPLENOTE_BLOG_URL);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// AboutFragment_24_BuggyGUIListenerOperatorMutator
case 2472: {
twitter.setOnClickListener(null);
break;
}
default: {
twitter.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// AboutFragment_25_LengthyGUIListenerOperatorMutator
case 2572: {
/**
* Inserted by Kadabra
*/
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.TWITTER_APP_URI + com.automattic.simplenote.AboutFragment.SIMPLENOTE_TWITTER_HANDLE);
} catch (java.lang.Exception e) {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.TWITTER_PROFILE_URL + com.automattic.simplenote.AboutFragment.SIMPLENOTE_TWITTER_HANDLE);
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.TWITTER_APP_URI + com.automattic.simplenote.AboutFragment.SIMPLENOTE_TWITTER_HANDLE);
} catch (java.lang.Exception e) {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.TWITTER_PROFILE_URL + com.automattic.simplenote.AboutFragment.SIMPLENOTE_TWITTER_HANDLE);
}
break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// AboutFragment_26_BuggyGUIListenerOperatorMutator
case 2672: {
help.setOnClickListener(null);
break;
}
default: {
help.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// AboutFragment_27_LengthyGUIListenerOperatorMutator
case 2772: {
/**
* Inserted by Kadabra
*/
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.SIMPLENOTE_HELP_URL);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.SIMPLENOTE_HELP_URL);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// AboutFragment_28_BuggyGUIListenerOperatorMutator
case 2872: {
store.setOnClickListener(null);
break;
}
default: {
store.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
java.lang.String url;
url = com.automattic.simplenote.AboutFragment.PLAY_STORE_URI + requireActivity().getPackageName();
android.content.Intent goToMarket;
switch(MUID_STATIC) {
// AboutFragment_30_InvalidKeyIntentOperatorMutator
case 3072: {
goToMarket = new android.content.Intent((String) null, android.net.Uri.parse(url));
break;
}
// AboutFragment_31_RandomActionIntentDefinitionOperatorMutator
case 3172: {
goToMarket = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
goToMarket = new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url));
break;
}
}
switch(MUID_STATIC) {
// AboutFragment_32_RandomActionIntentDefinitionOperatorMutator
case 3272: {
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
goToMarket.addFlags((android.content.Intent.FLAG_ACTIVITY_NO_HISTORY | android.content.Intent.FLAG_ACTIVITY_NEW_DOCUMENT) | android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
break;
}
}
switch(MUID_STATIC) {
// AboutFragment_29_LengthyGUIListenerOperatorMutator
case 2972: {
/**
* Inserted by Kadabra
*/
try {
if (com.automattic.simplenote.utils.BrowserUtils.isBrowserInstalled(requireContext())) {
startActivity(goToMarket);
} else {
com.automattic.simplenote.utils.BrowserUtils.showDialogErrorBrowser(requireContext(), url);
}
} catch (android.content.ActivityNotFoundException e) {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), url);
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
try {
if (com.automattic.simplenote.utils.BrowserUtils.isBrowserInstalled(requireContext())) {
startActivity(goToMarket);
} else {
com.automattic.simplenote.utils.BrowserUtils.showDialogErrorBrowser(requireContext(), url);
}
} catch (android.content.ActivityNotFoundException e) {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), url);
}
break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// AboutFragment_33_BuggyGUIListenerOperatorMutator
case 3372: {
contribute.setOnClickListener(null);
break;
}
default: {
contribute.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// AboutFragment_34_LengthyGUIListenerOperatorMutator
case 3472: {
/**
* Inserted by Kadabra
*/
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.URL_CONTRIBUTE);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.URL_CONTRIBUTE);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// AboutFragment_35_BuggyGUIListenerOperatorMutator
case 3572: {
hiring.setOnClickListener(null);
break;
}
default: {
hiring.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// AboutFragment_36_LengthyGUIListenerOperatorMutator
case 3672: {
/**
* Inserted by Kadabra
*/
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.SIMPLENOTE_HIRING_HANDLE);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.SIMPLENOTE_HIRING_HANDLE);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
break;
}
}
}

});
break;
}
}
privacy.setText(android.text.Html.fromHtml(java.lang.String.format(getResources().getString(com.automattic.simplenote.R.string.link_privacy), "<u><span style=\"color:#", colorLink, "\">", "</span></u>")));
switch(MUID_STATIC) {
// AboutFragment_37_BuggyGUIListenerOperatorMutator
case 3772: {
privacy.setOnClickListener(null);
break;
}
default: {
privacy.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// AboutFragment_38_LengthyGUIListenerOperatorMutator
case 3872: {
/**
* Inserted by Kadabra
*/
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.URL_PRIVACY);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.URL_PRIVACY);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
break;
}
}
}

});
break;
}
}
terms.setText(android.text.Html.fromHtml(java.lang.String.format(getResources().getString(com.automattic.simplenote.R.string.link_terms), "<u><span style=\"color:#", colorLink, "\">", "</span></u>")));
switch(MUID_STATIC) {
// AboutFragment_39_BuggyGUIListenerOperatorMutator
case 3972: {
terms.setOnClickListener(null);
break;
}
default: {
terms.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// AboutFragment_40_LengthyGUIListenerOperatorMutator
case 4072: {
/**
* Inserted by Kadabra
*/
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.URL_TERMS);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.AboutFragment.URL_TERMS);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
break;
}
}
}

});
break;
}
}
california.setText(android.text.Html.fromHtml(java.lang.String.format(getResources().getString(com.automattic.simplenote.R.string.link_california), "<u><span style=\"color:#", colorLink, "\">", "</span></u>")));
switch(MUID_STATIC) {
// AboutFragment_41_BuggyGUIListenerOperatorMutator
case 4172: {
california.setOnClickListener(null);
break;
}
default: {
california.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// AboutFragment_42_LengthyGUIListenerOperatorMutator
case 4272: {
/**
* Inserted by Kadabra
*/
try {
startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(com.automattic.simplenote.AboutFragment.URL_CALIFORNIA)));
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
try {
startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(com.automattic.simplenote.AboutFragment.URL_CALIFORNIA)));
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
break;
}
}
}

});
break;
}
}
((com.automattic.simplenote.widgets.SpinningImageButton) (view.findViewById(com.automattic.simplenote.R.id.about_logo))).setSpeedListener(this);
return view;
}


@java.lang.Override
public void OnMaximumSpeed() {
java.lang.String[] items;
items = requireActivity().getResources().getStringArray(com.automattic.simplenote.R.array.array_about);
long index;
index = java.lang.System.currentTimeMillis() % items.length;
final androidx.appcompat.app.AlertDialog dialog;
dialog = new androidx.appcompat.app.AlertDialog.Builder(requireActivity()).setMessage(com.automattic.simplenote.utils.HtmlCompat.fromHtml(java.lang.String.format(items[((int) (index))], "<span style=\"color:#", java.lang.Integer.toHexString(com.automattic.simplenote.utils.ThemeUtils.getColorFromAttribute(requireActivity(), com.automattic.simplenote.R.attr.colorAccent) & 0xffffff), "\">", "</span>"))).show();
final android.os.Handler handler;
handler = new android.os.Handler();
final java.lang.Runnable runnable;
runnable = new java.lang.Runnable() {
@java.lang.Override
public void run() {
if (dialog.isShowing()) {
dialog.dismiss();
}
}

};
dialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
@java.lang.Override
public void onDismiss(android.content.DialogInterface dialog) {
handler.removeCallbacks(runnable);
}

});
switch(MUID_STATIC) {
// AboutFragment_43_BinaryMutator
case 4372: {
handler.postDelayed(runnable, items[((int) (index))].length() / 50);
break;
}
default: {
handler.postDelayed(runnable, items[((int) (index))].length() * 50);
break;
}
}
((android.widget.TextView) (java.util.Objects.requireNonNull(dialog.findViewById(android.R.id.message)))).setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
