package com.automattic.simplenote;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_NOTE_TAPPED;
import android.content.res.Configuration;
import com.automattic.simplenote.widgets.RobotoMediumTextView;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;
import com.automattic.simplenote.utils.AppLog;
import androidx.viewpager.widget.PagerAdapter;
import android.widget.ImageButton;
import com.automattic.simplenote.utils.NetworkUtils;
import static com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED;
import android.view.HapticFeedbackConstants;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.view.KeyEvent;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;
import java.util.LinkedList;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import com.automattic.simplenote.widgets.NoteEditorViewPager;
import android.os.Bundle;
import androidx.fragment.app.FragmentPagerAdapter;
import android.os.Handler;
import com.google.android.material.tabs.TabLayout;
import android.content.Intent;
import com.simperium.client.BucketObjectMissingException;
import org.wordpress.passcodelock.AppLockManager;
import android.view.View;
import static com.automattic.simplenote.utils.DisplayUtils.disableScreenshotsIfLocked;
import static com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK;
import androidx.fragment.app.FragmentManager;
import com.automattic.simplenote.utils.AppLog.Type;
import com.simperium.client.Bucket;
import com.automattic.simplenote.utils.DisplayUtils;
import com.automattic.simplenote.models.Note;
import static com.automattic.simplenote.utils.MatchOffsetHighlighter.MATCH_INDEX_COUNT;
import static com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK;
import android.widget.RelativeLayout;
import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static com.automattic.simplenote.utils.MatchOffsetHighlighter.MATCH_INDEX_START;
import java.util.Arrays;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@dagger.hilt.android.AndroidEntryPoint
public class NoteEditorActivity extends com.automattic.simplenote.ThemedAppCompatActivity {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String STATE_TAB_EDIT = "TAB_EDIT";

    private static final java.lang.String STATE_TAB_PREVIEW = "TAB_PREVIEW";

    private static final java.lang.String STATE_MATCHES_INDEX = "MATCHES_INDEX";

    private static final java.lang.String STATE_MATCHES_LOCATIONS = "MATCHES_LOCATIONS";

    private static final int INDEX_TAB_EDIT = 0;

    private static final int INDEX_TAB_PREVIEW = 1;

    private android.widget.ImageButton mButtonPrevious;

    private android.widget.ImageButton mButtonNext;

    private com.automattic.simplenote.models.Note mNote;

    private com.automattic.simplenote.NoteEditorFragment mNoteEditorFragment;

    private com.automattic.simplenote.NoteEditorActivity.NoteEditorFragmentPagerAdapter mNoteEditorFragmentPagerAdapter;

    private com.automattic.simplenote.widgets.NoteEditorViewPager mViewPager;

    private android.widget.RelativeLayout mSearchMatchBar;

    private com.automattic.simplenote.widgets.RobotoMediumTextView mNumberPosition;

    private com.automattic.simplenote.widgets.RobotoMediumTextView mNumberTotal;

    private java.lang.String mNoteId;

    private com.google.android.material.tabs.TabLayout mTabLayout;

    private boolean isMarkdownEnabled;

    private boolean isPreviewEnabled;

    private boolean isSearchMatch;

    private int[] mSearchMatchIndexes;

    private int mSearchMatchIndex;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // NoteEditorActivity_0_LengthyGUICreationOperatorMutator
            case 80: {
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
    com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.NETWORK, com.automattic.simplenote.utils.NetworkUtils.getNetworkInfo(this));
    com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Created (NoteEditorActivity)");
    setContentView(com.automattic.simplenote.R.layout.activity_note_editor);
    // No title, please.
    setTitle("");
    androidx.appcompat.widget.Toolbar toolbar;
    switch(MUID_STATIC) {
        // NoteEditorActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 180: {
            toolbar = null;
            break;
        }
        // NoteEditorActivity_2_InvalidIDFindViewOperatorMutator
        case 280: {
            toolbar = findViewById(732221);
            break;
        }
        // NoteEditorActivity_3_InvalidViewFocusOperatorMutator
        case 380: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.automattic.simplenote.R.id.toolbar);
            toolbar.requestFocus();
            break;
        }
        // NoteEditorActivity_4_ViewComponentNotVisibleOperatorMutator
        case 480: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.automattic.simplenote.R.id.toolbar);
            toolbar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        toolbar = findViewById(com.automattic.simplenote.R.id.toolbar);
        break;
    }
}
setSupportActionBar(toolbar);
if (getSupportActionBar() != null) {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    switch(MUID_STATIC) {
        // NoteEditorActivity_5_BuggyGUIListenerOperatorMutator
        case 580: {
            // Add a custom handler for back button click
            toolbar.setNavigationOnClickListener(null);
            break;
        }
        default: {
        // Add a custom handler for back button click
        toolbar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                switch(MUID_STATIC) {
                    // NoteEditorActivity_6_LengthyGUIListenerOperatorMutator
                    case 680: {
                        /**
                        * Inserted by Kadabra
                        */
                        handleBackPressed();
                        try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                        break;
                    }
                    default: {
                    handleBackPressed();
                    break;
                }
            }
        }

    });
    break;
}
}
}
mNoteEditorFragment = new com.automattic.simplenote.NoteEditorFragment();
com.automattic.simplenote.NoteMarkdownFragment noteMarkdownFragment;
mNoteEditorFragmentPagerAdapter = new com.automattic.simplenote.NoteEditorActivity.NoteEditorFragmentPagerAdapter(getSupportFragmentManager(), androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
switch(MUID_STATIC) {
// NoteEditorActivity_7_FindViewByIdReturnsNullOperatorMutator
case 780: {
mViewPager = null;
break;
}
// NoteEditorActivity_8_InvalidIDFindViewOperatorMutator
case 880: {
mViewPager = findViewById(732221);
break;
}
// NoteEditorActivity_9_InvalidViewFocusOperatorMutator
case 980: {
/**
* Inserted by Kadabra
*/
mViewPager = findViewById(com.automattic.simplenote.R.id.pager);
mViewPager.requestFocus();
break;
}
// NoteEditorActivity_10_ViewComponentNotVisibleOperatorMutator
case 1080: {
/**
* Inserted by Kadabra
*/
mViewPager = findViewById(com.automattic.simplenote.R.id.pager);
mViewPager.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mViewPager = findViewById(com.automattic.simplenote.R.id.pager);
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_11_FindViewByIdReturnsNullOperatorMutator
case 1180: {
mTabLayout = null;
break;
}
// NoteEditorActivity_12_InvalidIDFindViewOperatorMutator
case 1280: {
mTabLayout = findViewById(732221);
break;
}
// NoteEditorActivity_13_InvalidViewFocusOperatorMutator
case 1380: {
/**
* Inserted by Kadabra
*/
mTabLayout = findViewById(com.automattic.simplenote.R.id.tabs);
mTabLayout.requestFocus();
break;
}
// NoteEditorActivity_14_ViewComponentNotVisibleOperatorMutator
case 1480: {
/**
* Inserted by Kadabra
*/
mTabLayout = findViewById(com.automattic.simplenote.R.id.tabs);
mTabLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mTabLayout = findViewById(com.automattic.simplenote.R.id.tabs);
break;
}
}
android.content.Intent intent;
switch(MUID_STATIC) {
// NoteEditorActivity_15_RandomActionIntentDefinitionOperatorMutator
case 1580: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = getIntent();
break;
}
}
mNoteId = intent.getStringExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID);
try {
com.automattic.simplenote.Simplenote application;
application = ((com.automattic.simplenote.Simplenote) (getApplication()));
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket;
notesBucket = application.getNotesBucket();
mNote = notesBucket.get(mNoteId);
} catch (com.simperium.client.BucketObjectMissingException exception) {
exception.printStackTrace();
}
if (savedInstanceState == null) {
// Create the note editor fragment
android.os.Bundle arguments;
arguments = new android.os.Bundle();
arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, mNoteId);
arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, intent.getBooleanExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false));
boolean isNewNote;
isNewNote = intent.getBooleanExtra(com.automattic.simplenote.NoteEditorFragment.ARG_NEW_NOTE, false);
arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_NEW_NOTE, isNewNote);
if (intent.hasExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MATCH_OFFSETS))
arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_MATCH_OFFSETS, intent.getStringExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MATCH_OFFSETS));

mNoteEditorFragment.setArguments(arguments);
noteMarkdownFragment = new com.automattic.simplenote.NoteMarkdownFragment();
noteMarkdownFragment.setArguments(arguments);
mNoteEditorFragmentPagerAdapter.addFragment(mNoteEditorFragment, getString(com.automattic.simplenote.R.string.tab_edit));
mNoteEditorFragmentPagerAdapter.addFragment(noteMarkdownFragment, getString(com.automattic.simplenote.R.string.tab_preview));
mViewPager.setPagingEnabled(false);
mViewPager.addOnPageChangeListener(new androidx.viewpager.widget.ViewPager.OnPageChangeListener() {
@java.lang.Override
public void onPageSelected(int position) {
if (position == com.automattic.simplenote.NoteEditorActivity.INDEX_TAB_PREVIEW) {
com.automattic.simplenote.utils.DisplayUtils.hideKeyboard(mViewPager);
}
try {
com.automattic.simplenote.Simplenote application;
application = ((com.automattic.simplenote.Simplenote) (getApplication()));
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket;
notesBucket = application.getNotesBucket();
mNote = notesBucket.get(mNoteId);
if (mNote != null) {
mNote.setPreviewEnabled(position == com.automattic.simplenote.NoteEditorActivity.INDEX_TAB_PREVIEW);
mNote.save();
}
} catch (com.simperium.client.BucketObjectMissingException exception) {
exception.printStackTrace();
}
}


@java.lang.Override
public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
}


@java.lang.Override
public void onPageScrollStateChanged(int state) {
}

});
isMarkdownEnabled = intent.getBooleanExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, false);
isPreviewEnabled = intent.getBooleanExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, false);
} else {
mNoteEditorFragmentPagerAdapter.addFragment(getSupportFragmentManager().getFragment(savedInstanceState, com.automattic.simplenote.NoteEditorActivity.STATE_TAB_EDIT), getString(com.automattic.simplenote.R.string.tab_edit));
mNoteEditorFragmentPagerAdapter.addFragment(getSupportFragmentManager().getFragment(savedInstanceState, com.automattic.simplenote.NoteEditorActivity.STATE_TAB_PREVIEW), getString(com.automattic.simplenote.R.string.tab_preview));
isMarkdownEnabled = savedInstanceState.getBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED);
isPreviewEnabled = savedInstanceState.getBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED);
mSearchMatchIndex = savedInstanceState.getInt(com.automattic.simplenote.NoteEditorActivity.STATE_MATCHES_INDEX, 0);
mSearchMatchIndexes = savedInstanceState.getIntArray(com.automattic.simplenote.NoteEditorActivity.STATE_MATCHES_LOCATIONS);
}
if (intent.hasExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MATCH_OFFSETS)) {
setUpSearchMatchBar(intent);
isSearchMatch = true;
}
mViewPager.setAdapter(mNoteEditorFragmentPagerAdapter);
mTabLayout.setupWithViewPager(mViewPager);
// Show tabs if markdown is enabled for the current note.
if (isMarkdownEnabled) {
showTabs();
if (isPreviewEnabled & (!isSearchMatch)) {
switch(MUID_STATIC) {
// NoteEditorActivity_16_BinaryMutator
case 1680: {
mViewPager.setCurrentItem(mNoteEditorFragmentPagerAdapter.getCount() + 1);
break;
}
default: {
mViewPager.setCurrentItem(mNoteEditorFragmentPagerAdapter.getCount() - 1);
break;
}
}
}
}
if ((intent.hasExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK) && (intent.getExtras() != null)) && (intent.getExtras().getSerializable(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK) == com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED)) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET, "note_widget_note_tapped");
}
if ((intent.hasExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK) && (intent.getExtras() != null)) && (intent.getExtras().getSerializable(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK) == com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_NOTE_TAPPED)) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_NOTE_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET, "note_list_widget_note_tapped");
}
}


@java.lang.Override
public void onBackPressed() {
handleBackPressed();
}


private void handleBackPressed() {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACTION, "Tapped back button in navigation bar (NoteEditorActivity)");
if (isTaskRoot()) {
// The editor can be the task root when it comes from an action on a widget
// In these cases, instead of going to the home screen, the notes activity
// is started
android.content.Intent intent;
switch(MUID_STATIC) {
// NoteEditorActivity_17_NullIntentOperatorMutator
case 1780: {
intent = null;
break;
}
// NoteEditorActivity_18_InvalidKeyIntentOperatorMutator
case 1880: {
intent = new android.content.Intent((android.content.Context) null, com.automattic.simplenote.NotesActivity.class);
break;
}
// NoteEditorActivity_19_RandomActionIntentDefinitionOperatorMutator
case 1980: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(getApplicationContext(), com.automattic.simplenote.NotesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_20_RandomActionIntentDefinitionOperatorMutator
case 2080: {
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
intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
break;
}
}
startActivity(intent);
finish();
} else {
super.onBackPressed();
}
}


@java.lang.Override
protected void onPause() {
super.onPause();
if (org.wordpress.passcodelock.AppLockManager.getInstance().isAppLockFeatureEnabled()) {
org.wordpress.passcodelock.AppLockManager.getInstance().getAppLock().setExemptActivities(null);
}
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Paused (NoteEditorActivity)");
}


@java.lang.Override
protected void onResume() {
super.onResume();
com.automattic.simplenote.utils.DisplayUtils.disableScreenshotsIfLocked(this);
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.NETWORK, com.automattic.simplenote.utils.NetworkUtils.getNetworkInfo(this));
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Resumed (NoteEditorActivity)");
}


@java.lang.Override
protected void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
if ((mNoteEditorFragmentPagerAdapter.getCount() > 0) && mNoteEditorFragmentPagerAdapter.getItem(com.automattic.simplenote.NoteEditorActivity.INDEX_TAB_EDIT).isAdded()) {
getSupportFragmentManager().putFragment(outState, com.automattic.simplenote.NoteEditorActivity.STATE_TAB_EDIT, mNoteEditorFragmentPagerAdapter.getItem(com.automattic.simplenote.NoteEditorActivity.INDEX_TAB_EDIT));
}
if ((mNoteEditorFragmentPagerAdapter.getCount() > 1) && mNoteEditorFragmentPagerAdapter.getItem(com.automattic.simplenote.NoteEditorActivity.INDEX_TAB_PREVIEW).isAdded()) {
getSupportFragmentManager().putFragment(outState, com.automattic.simplenote.NoteEditorActivity.STATE_TAB_PREVIEW, mNoteEditorFragmentPagerAdapter.getItem(com.automattic.simplenote.NoteEditorActivity.INDEX_TAB_PREVIEW));
}
outState.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, isMarkdownEnabled);
outState.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, isPreviewEnabled);
outState.putInt(com.automattic.simplenote.NoteEditorActivity.STATE_MATCHES_INDEX, mSearchMatchIndex);
outState.putIntArray(com.automattic.simplenote.NoteEditorActivity.STATE_MATCHES_LOCATIONS, mSearchMatchIndexes);
super.onSaveInstanceState(outState);
}


@java.lang.Override
public void onConfigurationChanged(@androidx.annotation.NonNull
android.content.res.Configuration newConfig) {
super.onConfigurationChanged(newConfig);
// Relaunch shortcut dialog when window is maximized or restored (Chrome OS).
if (getSupportFragmentManager().findFragmentByTag(com.automattic.simplenote.ShortcutDialogFragment.DIALOG_TAG) != null) {
com.automattic.simplenote.ShortcutDialogFragment.showShortcuts(this, isPreviewTabSelected());
}
// If changing to large screen landscape, we finish the activity to go back to
// NotesActivity with the note selected in the multipane layout.
if ((com.automattic.simplenote.utils.DisplayUtils.isLargeScreen(this) && (newConfig.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE)) && (mNoteId != null)) {
android.content.Intent resultIntent;
switch(MUID_STATIC) {
// NoteEditorActivity_21_NullIntentOperatorMutator
case 2180: {
resultIntent = null;
break;
}
// NoteEditorActivity_22_RandomActionIntentDefinitionOperatorMutator
case 2280: {
resultIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
resultIntent = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_23_NullValueIntentPutExtraOperatorMutator
case 2380: {
resultIntent.putExtra(com.automattic.simplenote.Simplenote.SELECTED_NOTE_ID, new Parcelable[0]);
break;
}
// NoteEditorActivity_24_IntentPayloadReplacementOperatorMutator
case 2480: {
resultIntent.putExtra(com.automattic.simplenote.Simplenote.SELECTED_NOTE_ID, "");
break;
}
default: {
switch(MUID_STATIC) {
// NoteEditorActivity_25_RandomActionIntentDefinitionOperatorMutator
case 2580: {
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
resultIntent.putExtra(com.automattic.simplenote.Simplenote.SELECTED_NOTE_ID, mNoteId);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_26_NullValueIntentPutExtraOperatorMutator
case 2680: {
resultIntent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, new Parcelable[0]);
break;
}
// NoteEditorActivity_27_IntentPayloadReplacementOperatorMutator
case 2780: {
resultIntent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, true);
break;
}
default: {
switch(MUID_STATIC) {
// NoteEditorActivity_28_RandomActionIntentDefinitionOperatorMutator
case 2880: {
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
resultIntent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, isPreviewEnabled);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_29_NullValueIntentPutExtraOperatorMutator
case 2980: {
resultIntent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, new Parcelable[0]);
break;
}
// NoteEditorActivity_30_IntentPayloadReplacementOperatorMutator
case 3080: {
resultIntent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, true);
break;
}
default: {
switch(MUID_STATIC) {
// NoteEditorActivity_31_RandomActionIntentDefinitionOperatorMutator
case 3180: {
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
resultIntent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, isMarkdownEnabled);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_32_NullValueIntentPutExtraOperatorMutator
case 3280: {
resultIntent.putExtra(com.automattic.simplenote.ShortcutDialogFragment.DIALOG_VISIBLE, new Parcelable[0]);
break;
}
// NoteEditorActivity_33_IntentPayloadReplacementOperatorMutator
case 3380: {
resultIntent.putExtra(com.automattic.simplenote.ShortcutDialogFragment.DIALOG_VISIBLE, true);
break;
}
default: {
switch(MUID_STATIC) {
// NoteEditorActivity_34_RandomActionIntentDefinitionOperatorMutator
case 3480: {
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
resultIntent.putExtra(com.automattic.simplenote.ShortcutDialogFragment.DIALOG_VISIBLE, getSupportFragmentManager().findFragmentByTag(com.automattic.simplenote.ShortcutDialogFragment.DIALOG_TAG) != null);
break;
}
}
break;
}
}
setResult(android.app.Activity.RESULT_OK, resultIntent);
finish();
overridePendingTransition(0, 0);
}
}


@java.lang.Override
public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
switch (keyCode) {
case android.view.KeyEvent.KEYCODE_C :
if (event.isShiftPressed() && event.isCtrlPressed()) {
if (!isPreviewTabSelected()) {
if (mNoteEditorFragment != null) {
mNoteEditorFragment.insertChecklist();
}
} else {
android.widget.Toast.makeText(this, com.automattic.simplenote.R.string.item_action_toggle_checklist_edit_error, android.widget.Toast.LENGTH_SHORT).show();
}
return true;
} else {
return super.onKeyUp(keyCode, event);
}
case android.view.KeyEvent.KEYCODE_COMMA :
if (event.isCtrlPressed()) {
com.automattic.simplenote.ShortcutDialogFragment.showShortcuts(this, isPreviewTabSelected());
return true;
} else {
return super.onKeyUp(keyCode, event);
}
case android.view.KeyEvent.KEYCODE_H :
if (event.isCtrlPressed()) {
if (!isPreviewTabSelected()) {
if (mNoteEditorFragment != null) {
mNoteEditorFragment.showHistory();
}
} else {
android.widget.Toast.makeText(this, com.automattic.simplenote.R.string.item_action_show_history_edit_error, android.widget.Toast.LENGTH_SHORT).show();
}
return true;
} else {
return super.onKeyUp(keyCode, event);
}
case android.view.KeyEvent.KEYCODE_I :
if (event.isCtrlPressed()) {
if (!isPreviewTabSelected()) {
if (mNoteEditorFragment != null) {
mNoteEditorFragment.showInfo();
}
} else {
android.widget.Toast.makeText(this, com.automattic.simplenote.R.string.item_action_show_information_edit_error, android.widget.Toast.LENGTH_SHORT).show();
}
return true;
} else {
return super.onKeyUp(keyCode, event);
}
case android.view.KeyEvent.KEYCODE_P :
if (event.isShiftPressed() && event.isCtrlPressed()) {
if ((mNote != null) && mNote.isMarkdownEnabled()) {
togglePreview();
} else {
android.widget.Toast.makeText(this, com.automattic.simplenote.R.string.item_action_toggle_preview_enable_error, android.widget.Toast.LENGTH_SHORT).show();
}
return true;
} else {
return super.onKeyUp(keyCode, event);
}
case android.view.KeyEvent.KEYCODE_S :
if (event.isCtrlPressed()) {
if (!isPreviewTabSelected()) {
if (mNoteEditorFragment != null) {
mNoteEditorFragment.shareNote();
}
} else {
android.widget.Toast.makeText(this, com.automattic.simplenote.R.string.item_action_show_share_edit_error, android.widget.Toast.LENGTH_SHORT).show();
}
return true;
} else {
return super.onKeyUp(keyCode, event);
}
default :
return super.onKeyUp(keyCode, event);
}
}


protected com.automattic.simplenote.NoteMarkdownFragment getNoteMarkdownFragment() {
return ((com.automattic.simplenote.NoteMarkdownFragment) (mNoteEditorFragmentPagerAdapter.getItem(1)));
}


public void hideTabs() {
mTabLayout.setVisibility(android.view.View.GONE);
mViewPager.setPagingEnabled(false);
}


boolean isPreviewTabSelected() {
return (((mNote != null) && mNote.isMarkdownEnabled()) && (mViewPager != null)) && (mViewPager.getCurrentItem() == com.automattic.simplenote.NoteEditorActivity.INDEX_TAB_PREVIEW);
}


public void showTabs() {
mTabLayout.setVisibility(android.view.View.VISIBLE);
mViewPager.setPagingEnabled(true);
}


public void setSearchMatchBarVisible(boolean isVisible) {
if (mSearchMatchBar != null) {
mSearchMatchBar.setVisibility(isVisible ? android.view.View.VISIBLE : android.view.View.GONE);
}
}


public java.lang.Integer getCurrentSearchMatchIndexLocation() {
return mSearchMatchIndexes[mSearchMatchIndex];
}


private void setUpSearchMatchBar(android.content.Intent intent) {
if (mSearchMatchIndexes == null) {
java.lang.String matchOffsets;
matchOffsets = intent.getStringExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MATCH_OFFSETS);
java.lang.String[] matches;
matches = (matchOffsets != null) ? matchOffsets.split("\\s+") : new java.lang.String[]{  };
java.lang.String[] matchesStart;
switch(MUID_STATIC) {
// NoteEditorActivity_35_BinaryMutator
case 3580: {
matchesStart = new java.lang.String[matches.length * com.automattic.simplenote.utils.MatchOffsetHighlighter.MATCH_INDEX_COUNT];
break;
}
default: {
matchesStart = new java.lang.String[matches.length / com.automattic.simplenote.utils.MatchOffsetHighlighter.MATCH_INDEX_COUNT];
break;
}
}
// Get "start" item from matches.  The format is four space-separated integers that
// represent the location of the match: "column token start length" ex: "1 3 3 7"
for (int i = com.automattic.simplenote.utils.MatchOffsetHighlighter.MATCH_INDEX_START, j = 0; i < matches.length; i += com.automattic.simplenote.utils.MatchOffsetHighlighter.MATCH_INDEX_COUNT , j++) {
matchesStart[j] = matches[i];
}
// Remove duplicate items with linked hash set and linked list since full-text search
// may return the same position more than once when parsing both title and content.
matchesStart = new java.util.LinkedHashSet<>(new java.util.LinkedList<>(java.util.Arrays.asList(matchesStart))).toArray(new java.lang.String[0]);
mSearchMatchIndexes = new int[matchesStart.length];
// Convert matches string array to integer array.
for (int i = 0; i < matchesStart.length; i++) {
mSearchMatchIndexes[i] = java.lang.Integer.parseInt(matchesStart[i]);
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_36_FindViewByIdReturnsNullOperatorMutator
case 3680: {
mSearchMatchBar = null;
break;
}
// NoteEditorActivity_37_InvalidIDFindViewOperatorMutator
case 3780: {
mSearchMatchBar = findViewById(732221);
break;
}
// NoteEditorActivity_38_InvalidViewFocusOperatorMutator
case 3880: {
/**
* Inserted by Kadabra
*/
mSearchMatchBar = findViewById(com.automattic.simplenote.R.id.search_match_bar);
mSearchMatchBar.requestFocus();
break;
}
// NoteEditorActivity_39_ViewComponentNotVisibleOperatorMutator
case 3980: {
/**
* Inserted by Kadabra
*/
mSearchMatchBar = findViewById(com.automattic.simplenote.R.id.search_match_bar);
mSearchMatchBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mSearchMatchBar = findViewById(com.automattic.simplenote.R.id.search_match_bar);
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_40_FindViewByIdReturnsNullOperatorMutator
case 4080: {
mNumberPosition = null;
break;
}
// NoteEditorActivity_41_InvalidIDFindViewOperatorMutator
case 4180: {
mNumberPosition = findViewById(732221);
break;
}
// NoteEditorActivity_42_InvalidViewFocusOperatorMutator
case 4280: {
/**
* Inserted by Kadabra
*/
mNumberPosition = findViewById(com.automattic.simplenote.R.id.text_position);
mNumberPosition.requestFocus();
break;
}
// NoteEditorActivity_43_ViewComponentNotVisibleOperatorMutator
case 4380: {
/**
* Inserted by Kadabra
*/
mNumberPosition = findViewById(com.automattic.simplenote.R.id.text_position);
mNumberPosition.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mNumberPosition = findViewById(com.automattic.simplenote.R.id.text_position);
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_44_FindViewByIdReturnsNullOperatorMutator
case 4480: {
mNumberTotal = null;
break;
}
// NoteEditorActivity_45_InvalidIDFindViewOperatorMutator
case 4580: {
mNumberTotal = findViewById(732221);
break;
}
// NoteEditorActivity_46_InvalidViewFocusOperatorMutator
case 4680: {
/**
* Inserted by Kadabra
*/
mNumberTotal = findViewById(com.automattic.simplenote.R.id.text_total);
mNumberTotal.requestFocus();
break;
}
// NoteEditorActivity_47_ViewComponentNotVisibleOperatorMutator
case 4780: {
/**
* Inserted by Kadabra
*/
mNumberTotal = findViewById(com.automattic.simplenote.R.id.text_total);
mNumberTotal.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mNumberTotal = findViewById(com.automattic.simplenote.R.id.text_total);
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_48_FindViewByIdReturnsNullOperatorMutator
case 4880: {
mButtonPrevious = null;
break;
}
// NoteEditorActivity_49_InvalidIDFindViewOperatorMutator
case 4980: {
mButtonPrevious = findViewById(732221);
break;
}
// NoteEditorActivity_50_InvalidViewFocusOperatorMutator
case 5080: {
/**
* Inserted by Kadabra
*/
mButtonPrevious = findViewById(com.automattic.simplenote.R.id.button_previous);
mButtonPrevious.requestFocus();
break;
}
// NoteEditorActivity_51_ViewComponentNotVisibleOperatorMutator
case 5180: {
/**
* Inserted by Kadabra
*/
mButtonPrevious = findViewById(com.automattic.simplenote.R.id.button_previous);
mButtonPrevious.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mButtonPrevious = findViewById(com.automattic.simplenote.R.id.button_previous);
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_52_FindViewByIdReturnsNullOperatorMutator
case 5280: {
mButtonNext = null;
break;
}
// NoteEditorActivity_53_InvalidIDFindViewOperatorMutator
case 5380: {
mButtonNext = findViewById(732221);
break;
}
// NoteEditorActivity_54_InvalidViewFocusOperatorMutator
case 5480: {
/**
* Inserted by Kadabra
*/
mButtonNext = findViewById(com.automattic.simplenote.R.id.button_next);
mButtonNext.requestFocus();
break;
}
// NoteEditorActivity_55_ViewComponentNotVisibleOperatorMutator
case 5580: {
/**
* Inserted by Kadabra
*/
mButtonNext = findViewById(com.automattic.simplenote.R.id.button_next);
mButtonNext.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mButtonNext = findViewById(com.automattic.simplenote.R.id.button_next);
break;
}
}
switch(MUID_STATIC) {
// NoteEditorActivity_56_BuggyGUIListenerOperatorMutator
case 5680: {
mButtonPrevious.setOnClickListener(null);
break;
}
default: {
mButtonPrevious.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// NoteEditorActivity_57_LengthyGUIListenerOperatorMutator
case 5780: {
/**
* Inserted by Kadabra
*/
if (mSearchMatchIndex > 0) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.SEARCH_MATCH_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_SEARCH, "search_match_tapped_previous");
mSearchMatchIndex--;
mNoteEditorFragment.scrollToMatch(mSearchMatchIndexes[mSearchMatchIndex]);
new android.os.Handler().postDelayed(new java.lang.Runnable() {
@java.lang.Override
public void run() {
updateSearchMatchBarStatus();
}

}, getResources().getInteger(android.R.integer.config_mediumAnimTime));
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
if (mSearchMatchIndex > 0) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.SEARCH_MATCH_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_SEARCH, "search_match_tapped_previous");
mSearchMatchIndex--;
mNoteEditorFragment.scrollToMatch(mSearchMatchIndexes[mSearchMatchIndex]);
new android.os.Handler().postDelayed(new java.lang.Runnable() {
@java.lang.Override
public void run() {
updateSearchMatchBarStatus();
}

}, getResources().getInteger(android.R.integer.config_mediumAnimTime));
}
break;
}
}
}

});
break;
}
}
mButtonPrevious.setOnLongClickListener(new android.view.View.OnLongClickListener() {
@java.lang.Override
public boolean onLongClick(android.view.View v) {
if (v.isHapticFeedbackEnabled()) {
v.performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS);
}
android.widget.Toast.makeText(com.automattic.simplenote.NoteEditorActivity.this, getString(com.automattic.simplenote.R.string.previous), android.widget.Toast.LENGTH_SHORT).show();
return true;
}

});
switch(MUID_STATIC) {
// NoteEditorActivity_58_BuggyGUIListenerOperatorMutator
case 5880: {
mButtonNext.setOnClickListener(null);
break;
}
default: {
mButtonNext.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// NoteEditorActivity_59_LengthyGUIListenerOperatorMutator
case 5980: {
/**
* Inserted by Kadabra
*/
if (mSearchMatchIndex < (mSearchMatchIndexes.length - 1)) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.SEARCH_MATCH_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_SEARCH, "search_match_tapped_next");
mSearchMatchIndex++;
mNoteEditorFragment.scrollToMatch(mSearchMatchIndexes[mSearchMatchIndex]);
new android.os.Handler().postDelayed(new java.lang.Runnable() {
@java.lang.Override
public void run() {
updateSearchMatchBarStatus();
}

}, getResources().getInteger(android.R.integer.config_mediumAnimTime));
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
switch(MUID_STATIC) {
// NoteEditorActivity_60_BinaryMutator
case 6080: {
if (mSearchMatchIndex < (mSearchMatchIndexes.length + 1)) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.SEARCH_MATCH_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_SEARCH, "search_match_tapped_next");
mSearchMatchIndex++;
mNoteEditorFragment.scrollToMatch(mSearchMatchIndexes[mSearchMatchIndex]);
new android.os.Handler().postDelayed(new java.lang.Runnable() {
@java.lang.Override
public void run() {
updateSearchMatchBarStatus();
}

}, getResources().getInteger(android.R.integer.config_mediumAnimTime));
}
break;
}
default: {
if (mSearchMatchIndex < (mSearchMatchIndexes.length - 1)) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.SEARCH_MATCH_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_SEARCH, "search_match_tapped_next");
mSearchMatchIndex++;
mNoteEditorFragment.scrollToMatch(mSearchMatchIndexes[mSearchMatchIndex]);
new android.os.Handler().postDelayed(new java.lang.Runnable() {
@java.lang.Override
public void run() {
updateSearchMatchBarStatus();
}

}, getResources().getInteger(android.R.integer.config_mediumAnimTime));
}
break;
}
}
break;
}
}
}

});
break;
}
}
mButtonNext.setOnLongClickListener(new android.view.View.OnLongClickListener() {
@java.lang.Override
public boolean onLongClick(android.view.View v) {
if (v.isHapticFeedbackEnabled()) {
v.performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS);
}
android.widget.Toast.makeText(com.automattic.simplenote.NoteEditorActivity.this, getString(com.automattic.simplenote.R.string.next), android.widget.Toast.LENGTH_SHORT).show();
return true;
}

});
mNoteEditorFragment.scrollToMatch(mSearchMatchIndexes[mSearchMatchIndex]);
setSearchMatchBarVisible(true);
updateSearchMatchBarStatus();
}


private void togglePreview() {
int position;
position = (mNote.isPreviewEnabled()) ? com.automattic.simplenote.NoteEditorActivity.INDEX_TAB_EDIT : com.automattic.simplenote.NoteEditorActivity.INDEX_TAB_PREVIEW;
mViewPager.setCurrentItem(position);
try {
com.automattic.simplenote.Simplenote application;
application = ((com.automattic.simplenote.Simplenote) (getApplication()));
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket;
notesBucket = application.getNotesBucket();
mNote = notesBucket.get(mNoteId);
if (mNote != null) {
mNote.setPreviewEnabled(position == com.automattic.simplenote.NoteEditorActivity.INDEX_TAB_PREVIEW);
mNote.save();
}
} catch (com.simperium.client.BucketObjectMissingException exception) {
exception.printStackTrace();
}
}


private void updateSearchMatchBarStatus() {
switch(MUID_STATIC) {
// NoteEditorActivity_61_BinaryMutator
case 6180: {
mNumberPosition.setText(java.lang.String.valueOf(mSearchMatchIndex - 1));
break;
}
default: {
mNumberPosition.setText(java.lang.String.valueOf(mSearchMatchIndex + 1));
break;
}
}
mNumberTotal.setText(java.lang.String.valueOf(mSearchMatchIndexes.length));
mButtonPrevious.setEnabled(mSearchMatchIndex > 0);
switch(MUID_STATIC) {
// NoteEditorActivity_62_BinaryMutator
case 6280: {
mButtonNext.setEnabled(mSearchMatchIndex < (mSearchMatchIndexes.length + 1));
break;
}
default: {
mButtonNext.setEnabled(mSearchMatchIndex < (mSearchMatchIndexes.length - 1));
break;
}
}
}


private static class NoteEditorFragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
private final java.util.ArrayList<androidx.fragment.app.Fragment> mFragments = new java.util.ArrayList<>();

private final java.util.ArrayList<java.lang.String> mTitles = new java.util.ArrayList<>();

NoteEditorFragmentPagerAdapter(@androidx.annotation.NonNull
androidx.fragment.app.FragmentManager fm, int behavior) {
super(fm, behavior);
}


@java.lang.Override
public int getCount() {
return mFragments.size();
}


@androidx.annotation.NonNull
@java.lang.Override
public androidx.fragment.app.Fragment getItem(int position) {
return mFragments.get(position);
}


@java.lang.Override
public int getItemPosition(@androidx.annotation.NonNull
java.lang.Object object) {
return androidx.viewpager.widget.PagerAdapter.POSITION_NONE;
}


@java.lang.Override
public java.lang.CharSequence getPageTitle(int position) {
return mTitles.get(position);
}


void addFragment(androidx.fragment.app.Fragment fragment, java.lang.String title) {
mFragments.add(fragment);
mTitles.add(title);
notifyDataSetChanged();
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
