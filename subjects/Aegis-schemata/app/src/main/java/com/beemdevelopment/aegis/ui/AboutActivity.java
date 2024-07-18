package com.beemdevelopment.aegis.ui;
import com.beemdevelopment.aegis.licenses.ProtobufLicense;
import android.os.Bundle;
import com.beemdevelopment.aegis.licenses.GlideLicense;
import de.psdev.licensesdialog.LicenseResolver;
import androidx.core.view.LayoutInflaterCompat;
import de.psdev.licensesdialog.LicensesDialog;
import android.content.Intent;
import android.view.MenuItem;
import android.net.Uri;
import com.beemdevelopment.aegis.helpers.ThemeHelper;
import android.view.View;
import androidx.annotation.AttrRes;
import android.content.ClipboardManager;
import com.beemdevelopment.aegis.R;
import androidx.annotation.StringRes;
import com.beemdevelopment.aegis.Theme;
import com.beemdevelopment.aegis.ui.dialogs.LicenseDialog;
import android.widget.TextView;
import android.widget.Toast;
import com.beemdevelopment.aegis.BuildConfig;
import android.content.ClipData;
import com.beemdevelopment.aegis.ui.dialogs.ChangelogDialog;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AboutActivity extends com.beemdevelopment.aegis.ui.AegisActivity {
    static final int MUID_STATIC = getMUID();
    private static java.lang.String GITHUB = "https://github.com/beemdevelopment/Aegis";

    private static java.lang.String WEBSITE_ALEXANDER = "https://alexbakker.me";

    private static java.lang.String GITHUB_MICHAEL = "https://github.com/michaelschattgen";

    private static java.lang.String MAIL_BEEMDEVELOPMENT = "beemdevelopment@gmail.com";

    private static java.lang.String WEBSITE_BEEMDEVELOPMENT = "https://beem.dev/";

    private static java.lang.String PLAYSTORE_BEEMDEVELOPMENT = "https://play.google.com/store/apps/details?id=com.beemdevelopment.aegis";

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        androidx.core.view.LayoutInflaterCompat.setFactory2(getLayoutInflater(), new com.mikepenz.iconics.context.IconicsLayoutInflater2(getDelegate()));
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // AboutActivity_0_LengthyGUICreationOperatorMutator
            case 174: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    if (abortIfOrphan(savedInstanceState)) {
        return;
    }
    setContentView(com.beemdevelopment.aegis.R.layout.activity_about);
    switch(MUID_STATIC) {
        // AboutActivity_1_InvalidIDFindViewOperatorMutator
        case 1174: {
            setSupportActionBar(findViewById(732221));
            break;
        }
        default: {
        setSupportActionBar(findViewById(com.beemdevelopment.aegis.R.id.toolbar));
        break;
    }
}
if (getSupportActionBar() != null) {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
}
android.view.View btnLicense;
switch(MUID_STATIC) {
    // AboutActivity_2_FindViewByIdReturnsNullOperatorMutator
    case 2174: {
        btnLicense = null;
        break;
    }
    // AboutActivity_3_InvalidIDFindViewOperatorMutator
    case 3174: {
        btnLicense = findViewById(732221);
        break;
    }
    // AboutActivity_4_InvalidViewFocusOperatorMutator
    case 4174: {
        /**
        * Inserted by Kadabra
        */
        btnLicense = findViewById(com.beemdevelopment.aegis.R.id.btn_license);
        btnLicense.requestFocus();
        break;
    }
    // AboutActivity_5_ViewComponentNotVisibleOperatorMutator
    case 5174: {
        /**
        * Inserted by Kadabra
        */
        btnLicense = findViewById(com.beemdevelopment.aegis.R.id.btn_license);
        btnLicense.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    btnLicense = findViewById(com.beemdevelopment.aegis.R.id.btn_license);
    break;
}
}
switch(MUID_STATIC) {
// AboutActivity_6_BuggyGUIListenerOperatorMutator
case 6174: {
    btnLicense.setOnClickListener(null);
    break;
}
default: {
btnLicense.setOnClickListener((android.view.View v) -> {
    com.beemdevelopment.aegis.ui.dialogs.LicenseDialog.create().setTheme(getConfiguredTheme()).show(getSupportFragmentManager(), null);
});
break;
}
}
android.view.View btnThirdPartyLicenses;
switch(MUID_STATIC) {
// AboutActivity_7_FindViewByIdReturnsNullOperatorMutator
case 7174: {
btnThirdPartyLicenses = null;
break;
}
// AboutActivity_8_InvalidIDFindViewOperatorMutator
case 8174: {
btnThirdPartyLicenses = findViewById(732221);
break;
}
// AboutActivity_9_InvalidViewFocusOperatorMutator
case 9174: {
/**
* Inserted by Kadabra
*/
btnThirdPartyLicenses = findViewById(com.beemdevelopment.aegis.R.id.btn_third_party_licenses);
btnThirdPartyLicenses.requestFocus();
break;
}
// AboutActivity_10_ViewComponentNotVisibleOperatorMutator
case 10174: {
/**
* Inserted by Kadabra
*/
btnThirdPartyLicenses = findViewById(com.beemdevelopment.aegis.R.id.btn_third_party_licenses);
btnThirdPartyLicenses.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
btnThirdPartyLicenses = findViewById(com.beemdevelopment.aegis.R.id.btn_third_party_licenses);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_11_BuggyGUIListenerOperatorMutator
case 11174: {
btnThirdPartyLicenses.setOnClickListener(null);
break;
}
default: {
btnThirdPartyLicenses.setOnClickListener((android.view.View v) -> showThirdPartyLicenseDialog());
break;
}
}
android.widget.TextView appVersion;
switch(MUID_STATIC) {
// AboutActivity_12_FindViewByIdReturnsNullOperatorMutator
case 12174: {
appVersion = null;
break;
}
// AboutActivity_13_InvalidIDFindViewOperatorMutator
case 13174: {
appVersion = findViewById(732221);
break;
}
// AboutActivity_14_InvalidViewFocusOperatorMutator
case 14174: {
/**
* Inserted by Kadabra
*/
appVersion = findViewById(com.beemdevelopment.aegis.R.id.app_version);
appVersion.requestFocus();
break;
}
// AboutActivity_15_ViewComponentNotVisibleOperatorMutator
case 15174: {
/**
* Inserted by Kadabra
*/
appVersion = findViewById(com.beemdevelopment.aegis.R.id.app_version);
appVersion.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
appVersion = findViewById(com.beemdevelopment.aegis.R.id.app_version);
break;
}
}
appVersion.setText(com.beemdevelopment.aegis.ui.AboutActivity.getCurrentAppVersion());
android.view.View btnAppVersion;
switch(MUID_STATIC) {
// AboutActivity_16_FindViewByIdReturnsNullOperatorMutator
case 16174: {
btnAppVersion = null;
break;
}
// AboutActivity_17_InvalidIDFindViewOperatorMutator
case 17174: {
btnAppVersion = findViewById(732221);
break;
}
// AboutActivity_18_InvalidViewFocusOperatorMutator
case 18174: {
/**
* Inserted by Kadabra
*/
btnAppVersion = findViewById(com.beemdevelopment.aegis.R.id.btn_app_version);
btnAppVersion.requestFocus();
break;
}
// AboutActivity_19_ViewComponentNotVisibleOperatorMutator
case 19174: {
/**
* Inserted by Kadabra
*/
btnAppVersion = findViewById(com.beemdevelopment.aegis.R.id.btn_app_version);
btnAppVersion.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
btnAppVersion = findViewById(com.beemdevelopment.aegis.R.id.btn_app_version);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_20_BuggyGUIListenerOperatorMutator
case 20174: {
btnAppVersion.setOnClickListener(null);
break;
}
default: {
btnAppVersion.setOnClickListener((android.view.View v) -> {
copyToClipboard(com.beemdevelopment.aegis.ui.AboutActivity.getCurrentAppVersion(), com.beemdevelopment.aegis.R.string.version_copied);
});
break;
}
}
android.view.View btnGithub;
switch(MUID_STATIC) {
// AboutActivity_21_FindViewByIdReturnsNullOperatorMutator
case 21174: {
btnGithub = null;
break;
}
// AboutActivity_22_InvalidIDFindViewOperatorMutator
case 22174: {
btnGithub = findViewById(732221);
break;
}
// AboutActivity_23_InvalidViewFocusOperatorMutator
case 23174: {
/**
* Inserted by Kadabra
*/
btnGithub = findViewById(com.beemdevelopment.aegis.R.id.btn_github);
btnGithub.requestFocus();
break;
}
// AboutActivity_24_ViewComponentNotVisibleOperatorMutator
case 24174: {
/**
* Inserted by Kadabra
*/
btnGithub = findViewById(com.beemdevelopment.aegis.R.id.btn_github);
btnGithub.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
btnGithub = findViewById(com.beemdevelopment.aegis.R.id.btn_github);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_25_BuggyGUIListenerOperatorMutator
case 25174: {
btnGithub.setOnClickListener(null);
break;
}
default: {
btnGithub.setOnClickListener((android.view.View v) -> openUrl(com.beemdevelopment.aegis.ui.AboutActivity.GITHUB));
break;
}
}
android.view.View btnAlexander;
switch(MUID_STATIC) {
// AboutActivity_26_FindViewByIdReturnsNullOperatorMutator
case 26174: {
btnAlexander = null;
break;
}
// AboutActivity_27_InvalidIDFindViewOperatorMutator
case 27174: {
btnAlexander = findViewById(732221);
break;
}
// AboutActivity_28_InvalidViewFocusOperatorMutator
case 28174: {
/**
* Inserted by Kadabra
*/
btnAlexander = findViewById(com.beemdevelopment.aegis.R.id.btn_alexander);
btnAlexander.requestFocus();
break;
}
// AboutActivity_29_ViewComponentNotVisibleOperatorMutator
case 29174: {
/**
* Inserted by Kadabra
*/
btnAlexander = findViewById(com.beemdevelopment.aegis.R.id.btn_alexander);
btnAlexander.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
btnAlexander = findViewById(com.beemdevelopment.aegis.R.id.btn_alexander);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_30_BuggyGUIListenerOperatorMutator
case 30174: {
btnAlexander.setOnClickListener(null);
break;
}
default: {
btnAlexander.setOnClickListener((android.view.View v) -> openUrl(com.beemdevelopment.aegis.ui.AboutActivity.WEBSITE_ALEXANDER));
break;
}
}
android.view.View btnMichael;
switch(MUID_STATIC) {
// AboutActivity_31_FindViewByIdReturnsNullOperatorMutator
case 31174: {
btnMichael = null;
break;
}
// AboutActivity_32_InvalidIDFindViewOperatorMutator
case 32174: {
btnMichael = findViewById(732221);
break;
}
// AboutActivity_33_InvalidViewFocusOperatorMutator
case 33174: {
/**
* Inserted by Kadabra
*/
btnMichael = findViewById(com.beemdevelopment.aegis.R.id.btn_michael);
btnMichael.requestFocus();
break;
}
// AboutActivity_34_ViewComponentNotVisibleOperatorMutator
case 34174: {
/**
* Inserted by Kadabra
*/
btnMichael = findViewById(com.beemdevelopment.aegis.R.id.btn_michael);
btnMichael.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
btnMichael = findViewById(com.beemdevelopment.aegis.R.id.btn_michael);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_35_BuggyGUIListenerOperatorMutator
case 35174: {
btnMichael.setOnClickListener(null);
break;
}
default: {
btnMichael.setOnClickListener((android.view.View v) -> openUrl(com.beemdevelopment.aegis.ui.AboutActivity.GITHUB_MICHAEL));
break;
}
}
android.view.View btnMail;
switch(MUID_STATIC) {
// AboutActivity_36_FindViewByIdReturnsNullOperatorMutator
case 36174: {
btnMail = null;
break;
}
// AboutActivity_37_InvalidIDFindViewOperatorMutator
case 37174: {
btnMail = findViewById(732221);
break;
}
// AboutActivity_38_InvalidViewFocusOperatorMutator
case 38174: {
/**
* Inserted by Kadabra
*/
btnMail = findViewById(com.beemdevelopment.aegis.R.id.btn_email);
btnMail.requestFocus();
break;
}
// AboutActivity_39_ViewComponentNotVisibleOperatorMutator
case 39174: {
/**
* Inserted by Kadabra
*/
btnMail = findViewById(com.beemdevelopment.aegis.R.id.btn_email);
btnMail.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
btnMail = findViewById(com.beemdevelopment.aegis.R.id.btn_email);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_40_BuggyGUIListenerOperatorMutator
case 40174: {
btnMail.setOnClickListener(null);
break;
}
default: {
btnMail.setOnClickListener((android.view.View v) -> openMail(com.beemdevelopment.aegis.ui.AboutActivity.MAIL_BEEMDEVELOPMENT));
break;
}
}
android.view.View btnWebsite;
switch(MUID_STATIC) {
// AboutActivity_41_FindViewByIdReturnsNullOperatorMutator
case 41174: {
btnWebsite = null;
break;
}
// AboutActivity_42_InvalidIDFindViewOperatorMutator
case 42174: {
btnWebsite = findViewById(732221);
break;
}
// AboutActivity_43_InvalidViewFocusOperatorMutator
case 43174: {
/**
* Inserted by Kadabra
*/
btnWebsite = findViewById(com.beemdevelopment.aegis.R.id.btn_website);
btnWebsite.requestFocus();
break;
}
// AboutActivity_44_ViewComponentNotVisibleOperatorMutator
case 44174: {
/**
* Inserted by Kadabra
*/
btnWebsite = findViewById(com.beemdevelopment.aegis.R.id.btn_website);
btnWebsite.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
btnWebsite = findViewById(com.beemdevelopment.aegis.R.id.btn_website);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_45_BuggyGUIListenerOperatorMutator
case 45174: {
btnWebsite.setOnClickListener(null);
break;
}
default: {
btnWebsite.setOnClickListener((android.view.View v) -> openUrl(com.beemdevelopment.aegis.ui.AboutActivity.WEBSITE_BEEMDEVELOPMENT));
break;
}
}
android.view.View btnRate;
switch(MUID_STATIC) {
// AboutActivity_46_FindViewByIdReturnsNullOperatorMutator
case 46174: {
btnRate = null;
break;
}
// AboutActivity_47_InvalidIDFindViewOperatorMutator
case 47174: {
btnRate = findViewById(732221);
break;
}
// AboutActivity_48_InvalidViewFocusOperatorMutator
case 48174: {
/**
* Inserted by Kadabra
*/
btnRate = findViewById(com.beemdevelopment.aegis.R.id.btn_rate);
btnRate.requestFocus();
break;
}
// AboutActivity_49_ViewComponentNotVisibleOperatorMutator
case 49174: {
/**
* Inserted by Kadabra
*/
btnRate = findViewById(com.beemdevelopment.aegis.R.id.btn_rate);
btnRate.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
btnRate = findViewById(com.beemdevelopment.aegis.R.id.btn_rate);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_50_BuggyGUIListenerOperatorMutator
case 50174: {
btnRate.setOnClickListener(null);
break;
}
default: {
btnRate.setOnClickListener((android.view.View v) -> openUrl(com.beemdevelopment.aegis.ui.AboutActivity.PLAYSTORE_BEEMDEVELOPMENT));
break;
}
}
android.view.View btnChangelog;
switch(MUID_STATIC) {
// AboutActivity_51_FindViewByIdReturnsNullOperatorMutator
case 51174: {
btnChangelog = null;
break;
}
// AboutActivity_52_InvalidIDFindViewOperatorMutator
case 52174: {
btnChangelog = findViewById(732221);
break;
}
// AboutActivity_53_InvalidViewFocusOperatorMutator
case 53174: {
/**
* Inserted by Kadabra
*/
btnChangelog = findViewById(com.beemdevelopment.aegis.R.id.btn_changelog);
btnChangelog.requestFocus();
break;
}
// AboutActivity_54_ViewComponentNotVisibleOperatorMutator
case 54174: {
/**
* Inserted by Kadabra
*/
btnChangelog = findViewById(com.beemdevelopment.aegis.R.id.btn_changelog);
btnChangelog.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
btnChangelog = findViewById(com.beemdevelopment.aegis.R.id.btn_changelog);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_55_BuggyGUIListenerOperatorMutator
case 55174: {
btnChangelog.setOnClickListener(null);
break;
}
default: {
btnChangelog.setOnClickListener((android.view.View v) -> {
com.beemdevelopment.aegis.ui.dialogs.ChangelogDialog.create().setTheme(getConfiguredTheme()).show(getSupportFragmentManager(), null);
});
break;
}
}
}


private static java.lang.String getCurrentAppVersion() {
if (com.beemdevelopment.aegis.BuildConfig.DEBUG) {
return java.lang.String.format("%s-%s (%s)", com.beemdevelopment.aegis.BuildConfig.VERSION_NAME, com.beemdevelopment.aegis.BuildConfig.GIT_HASH, com.beemdevelopment.aegis.BuildConfig.GIT_BRANCH);
}
return com.beemdevelopment.aegis.BuildConfig.VERSION_NAME;
}


private void openUrl(java.lang.String url) {
android.content.Intent browserIntent;
switch(MUID_STATIC) {
// AboutActivity_56_NullIntentOperatorMutator
case 56174: {
browserIntent = null;
break;
}
// AboutActivity_57_InvalidKeyIntentOperatorMutator
case 57174: {
browserIntent = new android.content.Intent((String) null);
break;
}
// AboutActivity_58_RandomActionIntentDefinitionOperatorMutator
case 58174: {
browserIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
browserIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_59_RandomActionIntentDefinitionOperatorMutator
case 59174: {
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
browserIntent.setData(android.net.Uri.parse(url));
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_60_RandomActionIntentDefinitionOperatorMutator
case 60174: {
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
browserIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
break;
}
}
startActivity(browserIntent);
}


private void copyToClipboard(java.lang.String text, @androidx.annotation.StringRes
int messageId) {
android.content.ClipboardManager clipboard;
clipboard = ((android.content.ClipboardManager) (getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
android.content.ClipData data;
data = android.content.ClipData.newPlainText("text/plain", text);
clipboard.setPrimaryClip(data);
android.widget.Toast.makeText(this, messageId, android.widget.Toast.LENGTH_SHORT).show();
}


private void openMail(java.lang.String mailaddress) {
android.content.Intent mailIntent;
switch(MUID_STATIC) {
// AboutActivity_61_NullIntentOperatorMutator
case 61174: {
mailIntent = null;
break;
}
// AboutActivity_62_InvalidKeyIntentOperatorMutator
case 62174: {
mailIntent = new android.content.Intent((String) null);
break;
}
// AboutActivity_63_RandomActionIntentDefinitionOperatorMutator
case 63174: {
mailIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
mailIntent = new android.content.Intent(android.content.Intent.ACTION_SENDTO);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_64_RandomActionIntentDefinitionOperatorMutator
case 64174: {
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
mailIntent.setData(android.net.Uri.parse("mailto:" + mailaddress));
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_65_NullValueIntentPutExtraOperatorMutator
case 65174: {
mailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new Parcelable[0]);
break;
}
// AboutActivity_66_IntentPayloadReplacementOperatorMutator
case 66174: {
mailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "");
break;
}
default: {
switch(MUID_STATIC) {
// AboutActivity_67_RandomActionIntentDefinitionOperatorMutator
case 67174: {
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
mailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, mailaddress);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_68_NullValueIntentPutExtraOperatorMutator
case 68174: {
mailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, new Parcelable[0]);
break;
}
// AboutActivity_69_IntentPayloadReplacementOperatorMutator
case 69174: {
mailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, 0);
break;
}
default: {
switch(MUID_STATIC) {
// AboutActivity_70_RandomActionIntentDefinitionOperatorMutator
case 70174: {
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
mailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, com.beemdevelopment.aegis.R.string.app_name_full);
break;
}
}
break;
}
}
startActivity(android.content.Intent.createChooser(mailIntent, getString(com.beemdevelopment.aegis.R.string.email)));
}


private void showThirdPartyLicenseDialog() {
java.lang.String stylesheet;
stylesheet = getString(com.beemdevelopment.aegis.R.string.custom_notices_format_style);
int backgroundColorResource;
backgroundColorResource = (getConfiguredTheme() == com.beemdevelopment.aegis.Theme.AMOLED) ? com.beemdevelopment.aegis.R.attr.cardBackgroundFocused : com.beemdevelopment.aegis.R.attr.cardBackground;
java.lang.String backgroundColor;
backgroundColor = getThemeColorAsHex(backgroundColorResource);
java.lang.String textColor;
textColor = getThemeColorAsHex(com.beemdevelopment.aegis.R.attr.primaryText);
java.lang.String licenseColor;
licenseColor = getThemeColorAsHex(com.beemdevelopment.aegis.R.attr.cardBackgroundFocused);
java.lang.String linkColor;
linkColor = getThemeColorAsHex(com.beemdevelopment.aegis.R.attr.colorAccent);
stylesheet = java.lang.String.format(stylesheet, backgroundColor, textColor, licenseColor, linkColor);
de.psdev.licensesdialog.LicenseResolver.registerLicense(new com.beemdevelopment.aegis.licenses.GlideLicense());
de.psdev.licensesdialog.LicenseResolver.registerLicense(new com.beemdevelopment.aegis.licenses.ProtobufLicense());
new de.psdev.licensesdialog.LicensesDialog.Builder(this).setNotices(com.beemdevelopment.aegis.R.raw.notices).setTitle(com.beemdevelopment.aegis.R.string.third_party_licenses).setNoticesCssStyle(stylesheet).setIncludeOwnLicense(true).build().show();
}


private java.lang.String getThemeColorAsHex(@androidx.annotation.AttrRes
int attributeId) {
return java.lang.String.format("%06X", 0xffffff & com.beemdevelopment.aegis.helpers.ThemeHelper.getThemeColor(attributeId, getTheme()));
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
switch (item.getItemId()) {
case android.R.id.home :
finish();
break;
default :
return super.onOptionsItemSelected(item);
}
return true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
