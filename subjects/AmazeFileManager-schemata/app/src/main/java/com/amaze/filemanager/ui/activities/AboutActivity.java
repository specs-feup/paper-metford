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
package com.amaze.filemanager.ui.activities;
import com.amaze.filemanager.ui.dialogs.share.ShareTask;
import androidx.palette.graphics.Palette;
import java.util.ArrayList;
import com.amaze.filemanager.LogHelper;
import com.mikepenz.aboutlibraries.Libs;
import android.net.Uri;
import org.slf4j.Logger;
import android.graphics.Bitmap;
import com.amaze.filemanager.R;
import com.mikepenz.aboutlibraries.LibsBuilder;
import android.os.Build;
import androidx.appcompat.widget.Toolbar;
import com.amaze.filemanager.ui.activities.superclasses.ThemedActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.amaze.filemanager.ui.theme.AppTheme;
import android.graphics.BitmapFactory;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLORED_NAVIGATION;
import org.slf4j.LoggerFactory;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import com.amaze.filemanager.utils.Billing;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import static com.amaze.filemanager.utils.Utils.openURL;
import com.amaze.filemanager.utils.PreferenceUtils;
import androidx.core.content.FileProvider;
import com.amaze.filemanager.utils.Utils;
import java.io.File;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 27/7/16.
 */
public class AboutActivity extends com.amaze.filemanager.ui.activities.superclasses.ThemedActivity implements android.view.View.OnClickListener {
    static final int MUID_STATIC = getMUID();
    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.ui.activities.AboutActivity.class);

    private static final int HEADER_HEIGHT = 1024;

    private static final int HEADER_WIDTH = 500;

    private com.google.android.material.appbar.AppBarLayout mAppBarLayout;

    private com.google.android.material.appbar.CollapsingToolbarLayout mCollapsingToolbarLayout;

    private androidx.appcompat.widget.AppCompatTextView mTitleTextView;

    private android.view.View mAuthorsDivider;

    private android.view.View mDeveloper1Divider;

    private com.amaze.filemanager.utils.Billing billing;

    private static final java.lang.String URL_AUTHOR1_GITHUB = "https://github.com/arpitkh96";

    private static final java.lang.String URL_AUTHOR2_GITHUB = "https://github.com/VishalNehra";

    private static final java.lang.String URL_DEVELOPER1_GITHUB = "https://github.com/EmmanuelMess";

    private static final java.lang.String URL_DEVELOPER2_GITHUB = "https://github.com/TranceLove";

    private static final java.lang.String URL_REPO_CHANGELOG = "https://github.com/TeamAmaze/AmazeFileManager/commits/master";

    private static final java.lang.String URL_REPO = "https://github.com/TeamAmaze/AmazeFileManager";

    private static final java.lang.String URL_REPO_ISSUES = "https://github.com/TeamAmaze/AmazeFileManager/issues";

    private static final java.lang.String URL_REPO_TRANSLATE = "https://www.transifex.com/amaze/amaze-file-manager/";

    private static final java.lang.String URL_REPO_XDA = "http://forum.xda-developers.com/android/apps-games/app-amaze-file-managermaterial-theme-t2937314";

    private static final java.lang.String URL_REPO_RATE = "market://details?id=com.amaze.filemanager";

    public static final java.lang.String PACKAGE_AMAZE_UTILS = "com.amaze.fileutilities";

    public static final java.lang.String URL_AMAZE_UTILS = "market://details?id=" + com.amaze.filemanager.ui.activities.AboutActivity.PACKAGE_AMAZE_UTILS;

    public static final java.lang.String URL_AMAZE_UTILS_FDROID = ("https://f-droid.org/en/packages/" + com.amaze.filemanager.ui.activities.AboutActivity.PACKAGE_AMAZE_UTILS) + "/";

    @java.lang.Override
    public void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // AboutActivity_0_LengthyGUICreationOperatorMutator
            case 108: {
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
    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
        if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK)) {
            setTheme(com.amaze.filemanager.R.style.aboutDark);
        } else if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
            setTheme(com.amaze.filemanager.R.style.aboutBlack);
        } else {
            setTheme(com.amaze.filemanager.R.style.aboutLight);
        }
    }
    setContentView(com.amaze.filemanager.R.layout.activity_about);
    switch(MUID_STATIC) {
        // AboutActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 1108: {
            mAppBarLayout = null;
            break;
        }
        // AboutActivity_2_InvalidIDFindViewOperatorMutator
        case 2108: {
            mAppBarLayout = findViewById(732221);
            break;
        }
        // AboutActivity_3_InvalidViewFocusOperatorMutator
        case 3108: {
            /**
            * Inserted by Kadabra
            */
            mAppBarLayout = findViewById(com.amaze.filemanager.R.id.appBarLayout);
            mAppBarLayout.requestFocus();
            break;
        }
        // AboutActivity_4_ViewComponentNotVisibleOperatorMutator
        case 4108: {
            /**
            * Inserted by Kadabra
            */
            mAppBarLayout = findViewById(com.amaze.filemanager.R.id.appBarLayout);
            mAppBarLayout.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        mAppBarLayout = findViewById(com.amaze.filemanager.R.id.appBarLayout);
        break;
    }
}
switch(MUID_STATIC) {
    // AboutActivity_5_FindViewByIdReturnsNullOperatorMutator
    case 5108: {
        mCollapsingToolbarLayout = null;
        break;
    }
    // AboutActivity_6_InvalidIDFindViewOperatorMutator
    case 6108: {
        mCollapsingToolbarLayout = findViewById(732221);
        break;
    }
    // AboutActivity_7_InvalidViewFocusOperatorMutator
    case 7108: {
        /**
        * Inserted by Kadabra
        */
        mCollapsingToolbarLayout = findViewById(com.amaze.filemanager.R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.requestFocus();
        break;
    }
    // AboutActivity_8_ViewComponentNotVisibleOperatorMutator
    case 8108: {
        /**
        * Inserted by Kadabra
        */
        mCollapsingToolbarLayout = findViewById(com.amaze.filemanager.R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    mCollapsingToolbarLayout = findViewById(com.amaze.filemanager.R.id.collapsing_toolbar_layout);
    break;
}
}
switch(MUID_STATIC) {
// AboutActivity_9_FindViewByIdReturnsNullOperatorMutator
case 9108: {
    mTitleTextView = null;
    break;
}
// AboutActivity_10_InvalidIDFindViewOperatorMutator
case 10108: {
    mTitleTextView = findViewById(732221);
    break;
}
// AboutActivity_11_InvalidViewFocusOperatorMutator
case 11108: {
    /**
    * Inserted by Kadabra
    */
    mTitleTextView = findViewById(com.amaze.filemanager.R.id.text_view_title);
    mTitleTextView.requestFocus();
    break;
}
// AboutActivity_12_ViewComponentNotVisibleOperatorMutator
case 12108: {
    /**
    * Inserted by Kadabra
    */
    mTitleTextView = findViewById(com.amaze.filemanager.R.id.text_view_title);
    mTitleTextView.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
mTitleTextView = findViewById(com.amaze.filemanager.R.id.text_view_title);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_13_FindViewByIdReturnsNullOperatorMutator
case 13108: {
mAuthorsDivider = null;
break;
}
// AboutActivity_14_InvalidIDFindViewOperatorMutator
case 14108: {
mAuthorsDivider = findViewById(732221);
break;
}
// AboutActivity_15_InvalidViewFocusOperatorMutator
case 15108: {
/**
* Inserted by Kadabra
*/
mAuthorsDivider = findViewById(com.amaze.filemanager.R.id.view_divider_authors);
mAuthorsDivider.requestFocus();
break;
}
// AboutActivity_16_ViewComponentNotVisibleOperatorMutator
case 16108: {
/**
* Inserted by Kadabra
*/
mAuthorsDivider = findViewById(com.amaze.filemanager.R.id.view_divider_authors);
mAuthorsDivider.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mAuthorsDivider = findViewById(com.amaze.filemanager.R.id.view_divider_authors);
break;
}
}
switch(MUID_STATIC) {
// AboutActivity_17_FindViewByIdReturnsNullOperatorMutator
case 17108: {
mDeveloper1Divider = null;
break;
}
// AboutActivity_18_InvalidIDFindViewOperatorMutator
case 18108: {
mDeveloper1Divider = findViewById(732221);
break;
}
// AboutActivity_19_InvalidViewFocusOperatorMutator
case 19108: {
/**
* Inserted by Kadabra
*/
mDeveloper1Divider = findViewById(com.amaze.filemanager.R.id.view_divider_developers_1);
mDeveloper1Divider.requestFocus();
break;
}
// AboutActivity_20_ViewComponentNotVisibleOperatorMutator
case 20108: {
/**
* Inserted by Kadabra
*/
mDeveloper1Divider = findViewById(com.amaze.filemanager.R.id.view_divider_developers_1);
mDeveloper1Divider.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mDeveloper1Divider = findViewById(com.amaze.filemanager.R.id.view_divider_developers_1);
break;
}
}
mAppBarLayout.setLayoutParams(calculateHeaderViewParams());
androidx.appcompat.widget.Toolbar mToolbar;
switch(MUID_STATIC) {
// AboutActivity_21_FindViewByIdReturnsNullOperatorMutator
case 21108: {
mToolbar = null;
break;
}
// AboutActivity_22_InvalidIDFindViewOperatorMutator
case 22108: {
mToolbar = findViewById(732221);
break;
}
// AboutActivity_23_InvalidViewFocusOperatorMutator
case 23108: {
/**
* Inserted by Kadabra
*/
mToolbar = findViewById(com.amaze.filemanager.R.id.toolBar);
mToolbar.requestFocus();
break;
}
// AboutActivity_24_ViewComponentNotVisibleOperatorMutator
case 24108: {
/**
* Inserted by Kadabra
*/
mToolbar = findViewById(com.amaze.filemanager.R.id.toolBar);
mToolbar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mToolbar = findViewById(com.amaze.filemanager.R.id.toolBar);
break;
}
}
setSupportActionBar(mToolbar);
getSupportActionBar().setDisplayHomeAsUpEnabled(true);
getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(com.amaze.filemanager.R.drawable.md_nav_back));
getSupportActionBar().setDisplayShowTitleEnabled(false);
switchIcons();
android.graphics.Bitmap bitmap;
bitmap = android.graphics.BitmapFactory.decodeResource(getResources(), com.amaze.filemanager.R.drawable.about_header);
// It will generate colors based on the image in an AsyncTask.
androidx.palette.graphics.Palette.from(bitmap).generate((androidx.palette.graphics.Palette palette) -> {
int mutedColor;
mutedColor = palette.getMutedColor(com.amaze.filemanager.utils.Utils.getColor(this, com.amaze.filemanager.R.color.primary_blue));
int darkMutedColor;
darkMutedColor = palette.getDarkMutedColor(com.amaze.filemanager.utils.Utils.getColor(this, com.amaze.filemanager.R.color.primary_blue));
mCollapsingToolbarLayout.setContentScrimColor(mutedColor);
mCollapsingToolbarLayout.setStatusBarScrimColor(darkMutedColor);
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
getWindow().setStatusBarColor(darkMutedColor);
}
});
mAppBarLayout.addOnOffsetChangedListener((com.google.android.material.appbar.AppBarLayout appBarLayout,int verticalOffset) -> {
switch(MUID_STATIC) {
// AboutActivity_25_BinaryMutator
case 25108: {
mTitleTextView.setAlpha(java.lang.Math.abs(verticalOffset * ((float) (appBarLayout.getTotalScrollRange()))));
break;
}
default: {
mTitleTextView.setAlpha(java.lang.Math.abs(verticalOffset / ((float) (appBarLayout.getTotalScrollRange()))));
break;
}
}
});
mAppBarLayout.setOnFocusChangeListener((android.view.View v,boolean hasFocus) -> {
mAppBarLayout.setExpanded(hasFocus, true);
});
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLORED_NAVIGATION)) {
getWindow().setNavigationBarColor(com.amaze.filemanager.utils.PreferenceUtils.getStatusColor(getPrimary()));
} else if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
getWindow().setNavigationBarColor(com.amaze.filemanager.utils.Utils.getColor(this, android.R.color.white));
} else if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
getWindow().setNavigationBarColor(com.amaze.filemanager.utils.Utils.getColor(this, android.R.color.black));
} else {
getWindow().setNavigationBarColor(com.amaze.filemanager.utils.Utils.getColor(this, com.amaze.filemanager.R.color.holo_dark_background));
}
}
}


/**
 * Calculates aspect ratio for the Amaze header
 *
 * @return the layout params with new set of width and height attribute
 */
private androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams calculateHeaderViewParams() {
// calculating cardview height as per the youtube video thumb aspect ratio
androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams layoutParams;
layoutParams = ((androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) (mAppBarLayout.getLayoutParams()));
float vidAspectRatio;
switch(MUID_STATIC) {
// AboutActivity_26_BinaryMutator
case 26108: {
vidAspectRatio = ((float) (com.amaze.filemanager.ui.activities.AboutActivity.HEADER_WIDTH)) * ((float) (com.amaze.filemanager.ui.activities.AboutActivity.HEADER_HEIGHT));
break;
}
default: {
vidAspectRatio = ((float) (com.amaze.filemanager.ui.activities.AboutActivity.HEADER_WIDTH)) / ((float) (com.amaze.filemanager.ui.activities.AboutActivity.HEADER_HEIGHT));
break;
}
}
LOG.debug(vidAspectRatio + "");
int screenWidth;
screenWidth = getResources().getDisplayMetrics().widthPixels;
float reqHeightAsPerAspectRatio;
switch(MUID_STATIC) {
// AboutActivity_27_BinaryMutator
case 27108: {
reqHeightAsPerAspectRatio = ((float) (screenWidth)) / vidAspectRatio;
break;
}
default: {
reqHeightAsPerAspectRatio = ((float) (screenWidth)) * vidAspectRatio;
break;
}
}
LOG.debug(reqHeightAsPerAspectRatio + "");
LOG.debug((("new width: " + screenWidth) + " and height: ") + reqHeightAsPerAspectRatio);
layoutParams.width = screenWidth;
layoutParams.height = ((int) (reqHeightAsPerAspectRatio));
return layoutParams;
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
switch (item.getItemId()) {
case android.R.id.home :
onBackPressed();
break;
}
return super.onOptionsItemSelected(item);
}


/**
 * Method switches icon resources as per current theme
 */
private void switchIcons() {
if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
// dark theme
mAuthorsDivider.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(this, com.amaze.filemanager.R.color.divider_dark_card));
mDeveloper1Divider.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(this, com.amaze.filemanager.R.color.divider_dark_card));
}
}


@java.lang.Override
public void onClick(android.view.View v) {
switch (v.getId()) {
case com.amaze.filemanager.R.id.relative_layout_source :
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.ui.activities.AboutActivity.URL_REPO, this);
break;
case com.amaze.filemanager.R.id.relative_layout_issues :
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.ui.activities.AboutActivity.URL_REPO_ISSUES, this);
break;
case com.amaze.filemanager.R.id.relative_layout_share_logs :
try {
android.net.Uri logUri;
logUri = androidx.core.content.FileProvider.getUriForFile(this, this.getPackageName(), new java.io.File(java.lang.String.format("/data/data/%s/cache/logs.txt", getPackageName())));
java.util.ArrayList<android.net.Uri> logUriList;
logUriList = new java.util.ArrayList<>();
logUriList.add(logUri);
new com.amaze.filemanager.ui.dialogs.share.ShareTask(this, logUriList, this.getAppTheme(), getAccent()).execute("text/plain");
} catch (java.lang.Exception e) {
LOG.warn("failed to share logs", e);
}
break;
case com.amaze.filemanager.R.id.relative_layout_changelog :
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.ui.activities.AboutActivity.URL_REPO_CHANGELOG, this);
break;
case com.amaze.filemanager.R.id.relative_layout_licenses :
com.mikepenz.aboutlibraries.LibsBuilder libsBuilder;
libsBuilder = // Not auto-detected for some reason
new com.mikepenz.aboutlibraries.LibsBuilder().withLibraries("apachemina").withActivityTitle(getString(com.amaze.filemanager.R.string.libraries)).withAboutIconShown(true).withAboutVersionShownName(true).withAboutVersionShownCode(false).withAboutDescription(getString(com.amaze.filemanager.R.string.about_amaze)).withAboutSpecial1(getString(com.amaze.filemanager.R.string.license)).withAboutSpecial1Description(getString(com.amaze.filemanager.R.string.amaze_license)).withLicenseShown(true);
switch (getAppTheme()) {
case LIGHT :
libsBuilder.withActivityStyle(com.mikepenz.aboutlibraries.Libs.ActivityStyle.LIGHT_DARK_TOOLBAR);
break;
case DARK :
libsBuilder.withActivityStyle(com.mikepenz.aboutlibraries.Libs.ActivityStyle.DARK);
break;
case BLACK :
libsBuilder.withActivityTheme(com.amaze.filemanager.R.style.AboutLibrariesTheme_Black);
break;
default :
com.amaze.filemanager.LogHelper.logOnProductionOrCrash("Incorrect value for switch");
}
libsBuilder.start(this);
break;
case com.amaze.filemanager.R.id.text_view_author_1_github :
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.ui.activities.AboutActivity.URL_AUTHOR1_GITHUB, this);
break;
case com.amaze.filemanager.R.id.text_view_author_2_github :
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.ui.activities.AboutActivity.URL_AUTHOR2_GITHUB, this);
break;
case com.amaze.filemanager.R.id.text_view_developer_1_github :
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.ui.activities.AboutActivity.URL_DEVELOPER1_GITHUB, this);
break;
case com.amaze.filemanager.R.id.text_view_developer_2_github :
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.ui.activities.AboutActivity.URL_DEVELOPER2_GITHUB, this);
break;
case com.amaze.filemanager.R.id.relative_layout_translate :
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.ui.activities.AboutActivity.URL_REPO_TRANSLATE, this);
break;
case com.amaze.filemanager.R.id.relative_layout_xda :
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.ui.activities.AboutActivity.URL_REPO_XDA, this);
break;
case com.amaze.filemanager.R.id.relative_layout_rate :
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.ui.activities.AboutActivity.URL_REPO_RATE, this);
break;
case com.amaze.filemanager.R.id.relative_layout_donate :
billing = new com.amaze.filemanager.utils.Billing(this);
break;
}
}


@java.lang.Override
protected void onDestroy() {
super.onDestroy();
LOG.debug("Destroying the manager.");
if (billing != null) {
billing.destroyBillingInstance();
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
