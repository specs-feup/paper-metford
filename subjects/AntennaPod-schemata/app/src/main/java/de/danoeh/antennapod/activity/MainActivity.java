package de.danoeh.antennapod.activity;
import de.danoeh.antennapod.event.EpisodeDownloadEvent;
import java.util.HashMap;
import android.content.res.Configuration;
import de.danoeh.antennapod.fragment.AudioPlayerFragment;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import de.danoeh.antennapod.fragment.PlaybackHistoryFragment;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import org.greenrobot.eventbus.Subscribe;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.fragment.QueueFragment;
import com.google.android.material.appbar.MaterialToolbar;
import de.danoeh.antennapod.fragment.TransitionEffect;
import androidx.fragment.app.FragmentTransaction;
import de.danoeh.antennapod.ui.home.HomeFragment;
import de.danoeh.antennapod.core.receiver.MediaButtonReceiver;
import androidx.work.WorkManager;
import org.apache.commons.lang3.Validate;
import androidx.core.view.ViewCompat;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.download.DownloadStatus;
import android.os.Build;
import de.danoeh.antennapod.view.LockableBottomSheetBehavior;
import com.bumptech.glide.Glide;
import android.media.AudioManager;
import androidx.fragment.app.FragmentContainerView;
import de.danoeh.antennapod.playback.cast.CastEnabledActivity;
import android.util.Log;
import android.widget.EditText;
import de.danoeh.antennapod.dialog.RatingDialog;
import androidx.fragment.app.FragmentManager;
import de.danoeh.antennapod.event.FeedUpdateRunningEvent;
import de.danoeh.antennapod.fragment.AddFeedFragment;
import com.google.android.material.snackbar.Snackbar;
import java.util.Map;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.fragment.NavDrawerFragment;
import de.danoeh.antennapod.event.MessageEvent;
import androidx.core.graphics.Insets;
import de.danoeh.antennapod.fragment.DownloadLogFragment;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.core.preferences.ThemeSwitcher;
import de.danoeh.antennapod.fragment.AllEpisodesFragment;
import androidx.appcompat.app.ActionBarDrawerToggle;
import org.greenrobot.eventbus.EventBus;
import de.danoeh.antennapod.fragment.SearchFragment;
import android.net.Uri;
import android.util.DisplayMetrics;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import de.danoeh.antennapod.R;
import android.view.KeyEvent;
import androidx.drawerlayout.widget.DrawerLayout;
import de.danoeh.antennapod.ui.appstartintent.MainActivityStarter;
import de.danoeh.antennapod.fragment.SubscriptionFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.work.WorkInfo;
import android.content.Intent;
import android.view.MenuItem;
import de.danoeh.antennapod.fragment.FeedItemlistFragment;
import android.view.View;
import de.danoeh.antennapod.fragment.InboxFragment;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import de.danoeh.antennapod.core.sync.queue.SynchronizationQueueSink;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import de.danoeh.antennapod.fragment.CompletedDownloadsFragment;
import org.apache.commons.lang3.ArrayUtils;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * The activity that is shown when the user launches the app.
 */
public class MainActivity extends de.danoeh.antennapod.playback.cast.CastEnabledActivity {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "MainActivity";

    public static final java.lang.String MAIN_FRAGMENT_TAG = "main";

    public static final java.lang.String PREF_NAME = "MainActivityPrefs";

    public static final java.lang.String PREF_IS_FIRST_LAUNCH = "prefMainActivityIsFirstLaunch";

    public static final java.lang.String EXTRA_FEED_ID = "fragment_feed_id";

    public static final java.lang.String EXTRA_REFRESH_ON_START = "refresh_on_start";

    public static final java.lang.String EXTRA_STARTED_FROM_SEARCH = "started_from_search";

    public static final java.lang.String EXTRA_ADD_TO_BACK_STACK = "add_to_back_stack";

    public static final java.lang.String KEY_GENERATED_VIEW_ID = "generated_view_id";

    @androidx.annotation.Nullable
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;

    @androidx.annotation.Nullable
    private androidx.appcompat.app.ActionBarDrawerToggle drawerToggle;

    private android.view.View navDrawer;

    private de.danoeh.antennapod.view.LockableBottomSheetBehavior sheetBehavior;

    private androidx.recyclerview.widget.RecyclerView.RecycledViewPool recycledViewPool = new androidx.recyclerview.widget.RecyclerView.RecycledViewPool();

    private int lastTheme = 0;

    private androidx.core.graphics.Insets navigationBarInsets = androidx.core.graphics.Insets.NONE;

    @androidx.annotation.NonNull
    public static android.content.Intent getIntentToOpenFeed(@androidx.annotation.NonNull
    android.content.Context context, long feedId) {
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // MainActivity_0_NullIntentOperatorMutator
            case 143: {
                intent = null;
                break;
            }
            // MainActivity_1_InvalidKeyIntentOperatorMutator
            case 1143: {
                intent = new android.content.Intent((Context) null, de.danoeh.antennapod.activity.MainActivity.class);
                break;
            }
            // MainActivity_2_RandomActionIntentDefinitionOperatorMutator
            case 2143: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(context.getApplicationContext(), de.danoeh.antennapod.activity.MainActivity.class);
            break;
        }
    }
    switch(MUID_STATIC) {
        // MainActivity_3_NullValueIntentPutExtraOperatorMutator
        case 3143: {
            intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_FEED_ID, new Parcelable[0]);
            break;
        }
        // MainActivity_4_IntentPayloadReplacementOperatorMutator
        case 4143: {
            intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_FEED_ID, 0);
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // MainActivity_5_RandomActionIntentDefinitionOperatorMutator
            case 5143: {
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
            intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_FEED_ID, feedId);
            break;
        }
    }
    break;
}
}
switch(MUID_STATIC) {
// MainActivity_6_RandomActionIntentDefinitionOperatorMutator
case 6143: {
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
intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
break;
}
}
return intent;
}


@java.lang.Override
public void onCreate(android.os.Bundle savedInstanceState) {
lastTheme = de.danoeh.antennapod.core.preferences.ThemeSwitcher.getNoTitleTheme(this);
setTheme(lastTheme);
if (savedInstanceState != null) {
ensureGeneratedViewIdGreaterThan(savedInstanceState.getInt(de.danoeh.antennapod.activity.MainActivity.KEY_GENERATED_VIEW_ID, 0));
}
androidx.core.view.WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
super.onCreate(savedInstanceState);
switch(MUID_STATIC) {
// MainActivity_7_LengthyGUICreationOperatorMutator
case 7143: {
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
setContentView(de.danoeh.antennapod.R.layout.main);
recycledViewPool.setMaxRecycledViews(de.danoeh.antennapod.R.id.view_type_episode_item, 25);
switch(MUID_STATIC) {
// MainActivity_8_FindViewByIdReturnsNullOperatorMutator
case 8143: {
drawerLayout = null;
break;
}
// MainActivity_9_InvalidIDFindViewOperatorMutator
case 9143: {
drawerLayout = findViewById(732221);
break;
}
// MainActivity_10_InvalidViewFocusOperatorMutator
case 10143: {
/**
* Inserted by Kadabra
*/
drawerLayout = findViewById(de.danoeh.antennapod.R.id.drawer_layout);
drawerLayout.requestFocus();
break;
}
// MainActivity_11_ViewComponentNotVisibleOperatorMutator
case 11143: {
/**
* Inserted by Kadabra
*/
drawerLayout = findViewById(de.danoeh.antennapod.R.id.drawer_layout);
drawerLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
drawerLayout = findViewById(de.danoeh.antennapod.R.id.drawer_layout);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_12_FindViewByIdReturnsNullOperatorMutator
case 12143: {
navDrawer = null;
break;
}
// MainActivity_13_InvalidIDFindViewOperatorMutator
case 13143: {
navDrawer = findViewById(732221);
break;
}
// MainActivity_14_InvalidViewFocusOperatorMutator
case 14143: {
/**
* Inserted by Kadabra
*/
navDrawer = findViewById(de.danoeh.antennapod.R.id.navDrawerFragment);
navDrawer.requestFocus();
break;
}
// MainActivity_15_ViewComponentNotVisibleOperatorMutator
case 15143: {
/**
* Inserted by Kadabra
*/
navDrawer = findViewById(de.danoeh.antennapod.R.id.navDrawerFragment);
navDrawer.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
navDrawer = findViewById(de.danoeh.antennapod.R.id.navDrawerFragment);
break;
}
}
setNavDrawerSize();
switch(MUID_STATIC) {
// MainActivity_16_InvalidIDFindViewOperatorMutator
case 16143: {
// Consume navigation bar insets - we apply them in setPlayerVisible()
androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(findViewById(732221), (android.view.View v,androidx.core.view.WindowInsetsCompat insets) -> {
navigationBarInsets = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.navigationBars());
updateInsets();
return new androidx.core.view.WindowInsetsCompat.Builder(insets).setInsets(androidx.core.view.WindowInsetsCompat.Type.navigationBars(), androidx.core.graphics.Insets.NONE).build();
});
break;
}
default: {
// Consume navigation bar insets - we apply them in setPlayerVisible()
androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(findViewById(de.danoeh.antennapod.R.id.main_view), (android.view.View v,androidx.core.view.WindowInsetsCompat insets) -> {
navigationBarInsets = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.navigationBars());
updateInsets();
return new androidx.core.view.WindowInsetsCompat.Builder(insets).setInsets(androidx.core.view.WindowInsetsCompat.Type.navigationBars(), androidx.core.graphics.Insets.NONE).build();
});
break;
}
}
final androidx.fragment.app.FragmentManager fm;
fm = getSupportFragmentManager();
if (fm.findFragmentByTag(de.danoeh.antennapod.activity.MainActivity.MAIN_FRAGMENT_TAG) == null) {
if (!de.danoeh.antennapod.storage.preferences.UserPreferences.DEFAULT_PAGE_REMEMBER.equals(de.danoeh.antennapod.storage.preferences.UserPreferences.getDefaultPage())) {
loadFragment(de.danoeh.antennapod.storage.preferences.UserPreferences.getDefaultPage(), null);
} else {
java.lang.String lastFragment;
lastFragment = de.danoeh.antennapod.fragment.NavDrawerFragment.getLastNavFragment(this);
if (org.apache.commons.lang3.ArrayUtils.contains(de.danoeh.antennapod.fragment.NavDrawerFragment.NAV_DRAWER_TAGS, lastFragment)) {
loadFragment(lastFragment, null);
} else {
try {
loadFeedFragmentById(java.lang.Integer.parseInt(lastFragment), null);
} catch (java.lang.NumberFormatException e) {
// it's not a number, this happens if we removed
// a label from the NAV_DRAWER_TAGS
// give them a nice default...
loadFragment(de.danoeh.antennapod.ui.home.HomeFragment.TAG, null);
}
}
}
}
androidx.fragment.app.FragmentTransaction transaction;
transaction = fm.beginTransaction();
de.danoeh.antennapod.fragment.NavDrawerFragment navDrawerFragment;
navDrawerFragment = new de.danoeh.antennapod.fragment.NavDrawerFragment();
transaction.replace(de.danoeh.antennapod.R.id.navDrawerFragment, navDrawerFragment, de.danoeh.antennapod.fragment.NavDrawerFragment.TAG);
de.danoeh.antennapod.fragment.AudioPlayerFragment audioPlayerFragment;
audioPlayerFragment = new de.danoeh.antennapod.fragment.AudioPlayerFragment();
transaction.replace(de.danoeh.antennapod.R.id.audioplayerFragment, audioPlayerFragment, de.danoeh.antennapod.fragment.AudioPlayerFragment.TAG);
transaction.commit();
checkFirstLaunch();
android.view.View bottomSheet;
switch(MUID_STATIC) {
// MainActivity_17_FindViewByIdReturnsNullOperatorMutator
case 17143: {
bottomSheet = null;
break;
}
// MainActivity_18_InvalidIDFindViewOperatorMutator
case 18143: {
bottomSheet = findViewById(732221);
break;
}
// MainActivity_19_InvalidViewFocusOperatorMutator
case 19143: {
/**
* Inserted by Kadabra
*/
bottomSheet = findViewById(de.danoeh.antennapod.R.id.audioplayerFragment);
bottomSheet.requestFocus();
break;
}
// MainActivity_20_ViewComponentNotVisibleOperatorMutator
case 20143: {
/**
* Inserted by Kadabra
*/
bottomSheet = findViewById(de.danoeh.antennapod.R.id.audioplayerFragment);
bottomSheet.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
bottomSheet = findViewById(de.danoeh.antennapod.R.id.audioplayerFragment);
break;
}
}
sheetBehavior = ((de.danoeh.antennapod.view.LockableBottomSheetBehavior) (com.google.android.material.bottomsheet.BottomSheetBehavior.from(bottomSheet)));
sheetBehavior.setHideable(false);
sheetBehavior.setBottomSheetCallback(bottomSheetCallback);
de.danoeh.antennapod.core.util.download.FeedUpdateManager.restartUpdateAlarm(this, false);
de.danoeh.antennapod.core.sync.queue.SynchronizationQueueSink.syncNowIfNotSyncedRecently();
androidx.work.WorkManager.getInstance(this).getWorkInfosByTagLiveData(de.danoeh.antennapod.core.util.download.FeedUpdateManager.WORK_TAG_FEED_UPDATE).observe(this, (java.util.List<androidx.work.WorkInfo> workInfos) -> {
boolean isRefreshingFeeds;
isRefreshingFeeds = false;
for (androidx.work.WorkInfo workInfo : workInfos) {
if (workInfo.getState() == androidx.work.WorkInfo.State.RUNNING) {
isRefreshingFeeds = true;
} else if (workInfo.getState() == androidx.work.WorkInfo.State.ENQUEUED) {
isRefreshingFeeds = true;
}
}
org.greenrobot.eventbus.EventBus.getDefault().postSticky(new de.danoeh.antennapod.event.FeedUpdateRunningEvent(isRefreshingFeeds));
});
androidx.work.WorkManager.getInstance(this).getWorkInfosByTagLiveData(de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.WORK_TAG).observe(this, (java.util.List<androidx.work.WorkInfo> workInfos) -> {
java.util.Map<java.lang.String, de.danoeh.antennapod.model.download.DownloadStatus> updatedEpisodes;
updatedEpisodes = new java.util.HashMap<>();
for (androidx.work.WorkInfo workInfo : workInfos) {
java.lang.String downloadUrl;
downloadUrl = null;
for (java.lang.String tag : workInfo.getTags()) {
if (tag.startsWith(de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.WORK_TAG_EPISODE_URL)) {
downloadUrl = tag.substring(de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.WORK_TAG_EPISODE_URL.length());
}
}
if (downloadUrl == null) {
continue;
}
int status;
if (workInfo.getState() == androidx.work.WorkInfo.State.RUNNING) {
status = de.danoeh.antennapod.model.download.DownloadStatus.STATE_RUNNING;
} else if ((workInfo.getState() == androidx.work.WorkInfo.State.ENQUEUED) || (workInfo.getState() == androidx.work.WorkInfo.State.BLOCKED)) {
status = de.danoeh.antennapod.model.download.DownloadStatus.STATE_QUEUED;
} else {
status = de.danoeh.antennapod.model.download.DownloadStatus.STATE_COMPLETED;
}
int progress;
progress = workInfo.getProgress().getInt(de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.WORK_DATA_PROGRESS, -1);
if ((progress == (-1)) && (status != de.danoeh.antennapod.model.download.DownloadStatus.STATE_COMPLETED)) {
status = de.danoeh.antennapod.model.download.DownloadStatus.STATE_QUEUED;
progress = 0;
}
updatedEpisodes.put(downloadUrl, new de.danoeh.antennapod.model.download.DownloadStatus(status, progress));
}
de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().setCurrentDownloads(updatedEpisodes);
org.greenrobot.eventbus.EventBus.getDefault().postSticky(new de.danoeh.antennapod.event.EpisodeDownloadEvent(updatedEpisodes));
});
}


@java.lang.Override
public void onAttachedToWindow() {
super.onAttachedToWindow();
updateInsets();
}


/**
 * View.generateViewId stores the current ID in a static variable.
 * When the process is killed, the variable gets reset.
 * This makes sure that we do not get ID collisions
 * and therefore errors when trying to restore state from another view.
 */
@java.lang.SuppressWarnings("StatementWithEmptyBody")
private void ensureGeneratedViewIdGreaterThan(int minimum) {
while (android.view.View.generateViewId() <= minimum) {
// Generate new IDs
} 
}


@java.lang.Override
protected void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
outState.putInt(de.danoeh.antennapod.activity.MainActivity.KEY_GENERATED_VIEW_ID, android.view.View.generateViewId());
}


private final com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback() {
@java.lang.Override
public void onStateChanged(@androidx.annotation.NonNull
android.view.View view, int state) {
if (state == com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED) {
onSlide(view, 0.0F);
} else if (state == com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED) {
onSlide(view, 1.0F);
}
}


@java.lang.Override
public void onSlide(@androidx.annotation.NonNull
android.view.View view, float slideOffset) {
de.danoeh.antennapod.fragment.AudioPlayerFragment audioPlayer;
audioPlayer = ((de.danoeh.antennapod.fragment.AudioPlayerFragment) (getSupportFragmentManager().findFragmentByTag(de.danoeh.antennapod.fragment.AudioPlayerFragment.TAG)));
if (audioPlayer == null) {
return;
}
if (slideOffset == 0.0F) {
// STATE_COLLAPSED
audioPlayer.scrollToPage(de.danoeh.antennapod.fragment.AudioPlayerFragment.POS_COVER);
}
audioPlayer.fadePlayerToToolbar(slideOffset);
}

};

public void setupToolbarToggle(@androidx.annotation.NonNull
com.google.android.material.appbar.MaterialToolbar toolbar, boolean displayUpArrow) {
if (drawerLayout != null) {
// Tablet layout does not have a drawer
if (drawerToggle != null) {
drawerLayout.removeDrawerListener(drawerToggle);
}
drawerToggle = new androidx.appcompat.app.ActionBarDrawerToggle(this, drawerLayout, toolbar, de.danoeh.antennapod.R.string.drawer_open, de.danoeh.antennapod.R.string.drawer_close);
drawerLayout.addDrawerListener(drawerToggle);
drawerToggle.syncState();
drawerToggle.setDrawerIndicatorEnabled(!displayUpArrow);
switch(MUID_STATIC) {
// MainActivity_21_BuggyGUIListenerOperatorMutator
case 21143: {
drawerToggle.setToolbarNavigationClickListener(null);
break;
}
default: {
drawerToggle.setToolbarNavigationClickListener((android.view.View v) -> getSupportFragmentManager().popBackStack());
break;
}
}
} else if (!displayUpArrow) {
toolbar.setNavigationIcon(null);
} else {
toolbar.setNavigationIcon(de.danoeh.antennapod.ui.common.ThemeUtils.getDrawableFromAttr(this, de.danoeh.antennapod.R.attr.homeAsUpIndicator));
switch(MUID_STATIC) {
// MainActivity_22_BuggyGUIListenerOperatorMutator
case 22143: {
toolbar.setNavigationOnClickListener(null);
break;
}
default: {
toolbar.setNavigationOnClickListener((android.view.View v) -> getSupportFragmentManager().popBackStack());
break;
}
}
}
}


@java.lang.Override
protected void onDestroy() {
super.onDestroy();
if (drawerLayout != null) {
drawerLayout.removeDrawerListener(drawerToggle);
}
}


private void checkFirstLaunch() {
android.content.SharedPreferences prefs;
prefs = getSharedPreferences(de.danoeh.antennapod.activity.MainActivity.PREF_NAME, android.content.Context.MODE_PRIVATE);
if (prefs.getBoolean(de.danoeh.antennapod.activity.MainActivity.PREF_IS_FIRST_LAUNCH, true)) {
de.danoeh.antennapod.core.util.download.FeedUpdateManager.restartUpdateAlarm(this, true);
android.content.SharedPreferences.Editor edit;
edit = prefs.edit();
edit.putBoolean(de.danoeh.antennapod.activity.MainActivity.PREF_IS_FIRST_LAUNCH, false);
edit.apply();
}
}


public boolean isDrawerOpen() {
return ((drawerLayout != null) && (navDrawer != null)) && drawerLayout.isDrawerOpen(navDrawer);
}


public de.danoeh.antennapod.view.LockableBottomSheetBehavior getBottomSheet() {
return sheetBehavior;
}


private void updateInsets() {
switch(MUID_STATIC) {
// MainActivity_23_InvalidIDFindViewOperatorMutator
case 23143: {
setPlayerVisible(findViewById(732221).getVisibility() == android.view.View.VISIBLE);
break;
}
default: {
setPlayerVisible(findViewById(de.danoeh.antennapod.R.id.audioplayerFragment).getVisibility() == android.view.View.VISIBLE);
break;
}
}
int playerHeight;
playerHeight = ((int) (getResources().getDimension(de.danoeh.antennapod.R.dimen.external_player_height)));
switch(MUID_STATIC) {
// MainActivity_24_BinaryMutator
case 24143: {
sheetBehavior.setPeekHeight(playerHeight - navigationBarInsets.bottom);
break;
}
default: {
sheetBehavior.setPeekHeight(playerHeight + navigationBarInsets.bottom);
break;
}
}
}


public void setPlayerVisible(boolean visible) {
getBottomSheet().setLocked(!visible);
if (visible) {
bottomSheetCallback.onStateChanged(null, getBottomSheet().getState())// Update toolbar visibility
;// Update toolbar visibility

} else {
getBottomSheet().setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
}
androidx.fragment.app.FragmentContainerView mainView;
switch(MUID_STATIC) {
// MainActivity_25_FindViewByIdReturnsNullOperatorMutator
case 25143: {
mainView = null;
break;
}
// MainActivity_26_InvalidIDFindViewOperatorMutator
case 26143: {
mainView = findViewById(732221);
break;
}
// MainActivity_27_InvalidViewFocusOperatorMutator
case 27143: {
/**
* Inserted by Kadabra
*/
mainView = findViewById(de.danoeh.antennapod.R.id.main_view);
mainView.requestFocus();
break;
}
// MainActivity_28_ViewComponentNotVisibleOperatorMutator
case 28143: {
/**
* Inserted by Kadabra
*/
mainView = findViewById(de.danoeh.antennapod.R.id.main_view);
mainView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mainView = findViewById(de.danoeh.antennapod.R.id.main_view);
break;
}
}
android.view.ViewGroup.MarginLayoutParams params;
params = ((android.view.ViewGroup.MarginLayoutParams) (mainView.getLayoutParams()));
int externalPlayerHeight;
externalPlayerHeight = ((int) (getResources().getDimension(de.danoeh.antennapod.R.dimen.external_player_height)));
switch(MUID_STATIC) {
// MainActivity_29_BinaryMutator
case 29143: {
params.setMargins(navigationBarInsets.left, 0, navigationBarInsets.right, navigationBarInsets.bottom - (visible ? externalPlayerHeight : 0));
break;
}
default: {
params.setMargins(navigationBarInsets.left, 0, navigationBarInsets.right, navigationBarInsets.bottom + (visible ? externalPlayerHeight : 0));
break;
}
}
mainView.setLayoutParams(params);
androidx.fragment.app.FragmentContainerView playerView;
switch(MUID_STATIC) {
// MainActivity_30_FindViewByIdReturnsNullOperatorMutator
case 30143: {
playerView = null;
break;
}
// MainActivity_31_InvalidIDFindViewOperatorMutator
case 31143: {
playerView = findViewById(732221);
break;
}
// MainActivity_32_InvalidViewFocusOperatorMutator
case 32143: {
/**
* Inserted by Kadabra
*/
playerView = findViewById(de.danoeh.antennapod.R.id.playerFragment);
playerView.requestFocus();
break;
}
// MainActivity_33_ViewComponentNotVisibleOperatorMutator
case 33143: {
/**
* Inserted by Kadabra
*/
playerView = findViewById(de.danoeh.antennapod.R.id.playerFragment);
playerView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
playerView = findViewById(de.danoeh.antennapod.R.id.playerFragment);
break;
}
}
android.view.ViewGroup.MarginLayoutParams playerParams;
playerParams = ((android.view.ViewGroup.MarginLayoutParams) (playerView.getLayoutParams()));
playerParams.setMargins(navigationBarInsets.left, 0, navigationBarInsets.right, 0);
playerView.setLayoutParams(playerParams);
switch(MUID_STATIC) {
// MainActivity_34_InvalidIDFindViewOperatorMutator
case 34143: {
findViewById(732221).setVisibility(visible ? android.view.View.VISIBLE : android.view.View.GONE);
break;
}
default: {
findViewById(de.danoeh.antennapod.R.id.audioplayerFragment).setVisibility(visible ? android.view.View.VISIBLE : android.view.View.GONE);
break;
}
}
}


public androidx.recyclerview.widget.RecyclerView.RecycledViewPool getRecycledViewPool() {
return recycledViewPool;
}


public void loadFragment(java.lang.String tag, android.os.Bundle args) {
android.util.Log.d(de.danoeh.antennapod.activity.MainActivity.TAG, ((("loadFragment(tag: " + tag) + ", args: ") + args) + ")");
androidx.fragment.app.Fragment fragment;
switch (tag) {
case de.danoeh.antennapod.ui.home.HomeFragment.TAG :
fragment = new de.danoeh.antennapod.ui.home.HomeFragment();
break;
case de.danoeh.antennapod.fragment.QueueFragment.TAG :
fragment = new de.danoeh.antennapod.fragment.QueueFragment();
break;
case de.danoeh.antennapod.fragment.InboxFragment.TAG :
fragment = new de.danoeh.antennapod.fragment.InboxFragment();
break;
case de.danoeh.antennapod.fragment.AllEpisodesFragment.TAG :
fragment = new de.danoeh.antennapod.fragment.AllEpisodesFragment();
break;
case de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG :
fragment = new de.danoeh.antennapod.fragment.CompletedDownloadsFragment();
break;
case de.danoeh.antennapod.fragment.PlaybackHistoryFragment.TAG :
fragment = new de.danoeh.antennapod.fragment.PlaybackHistoryFragment();
break;
case de.danoeh.antennapod.fragment.AddFeedFragment.TAG :
fragment = new de.danoeh.antennapod.fragment.AddFeedFragment();
break;
case de.danoeh.antennapod.fragment.SubscriptionFragment.TAG :
fragment = new de.danoeh.antennapod.fragment.SubscriptionFragment();
break;
default :
// default to home screen
fragment = new de.danoeh.antennapod.ui.home.HomeFragment();
tag = de.danoeh.antennapod.ui.home.HomeFragment.TAG;
args = null;
break;
}
if (args != null) {
fragment.setArguments(args);
}
de.danoeh.antennapod.fragment.NavDrawerFragment.saveLastNavFragment(this, tag);
loadFragment(fragment);
}


public void loadFeedFragmentById(long feedId, android.os.Bundle args) {
androidx.fragment.app.Fragment fragment;
fragment = de.danoeh.antennapod.fragment.FeedItemlistFragment.newInstance(feedId);
if (args != null) {
fragment.setArguments(args);
}
de.danoeh.antennapod.fragment.NavDrawerFragment.saveLastNavFragment(this, java.lang.String.valueOf(feedId));
loadFragment(fragment);
}


private void loadFragment(androidx.fragment.app.Fragment fragment) {
androidx.fragment.app.FragmentManager fragmentManager;
fragmentManager = getSupportFragmentManager();
// clear back stack
for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
fragmentManager.popBackStack();
}
androidx.fragment.app.FragmentTransaction t;
t = fragmentManager.beginTransaction();
t.replace(de.danoeh.antennapod.R.id.main_view, fragment, de.danoeh.antennapod.activity.MainActivity.MAIN_FRAGMENT_TAG);
fragmentManager.popBackStack();
// TODO: we have to allow state loss here
// since this function can get called from an AsyncTask which
// could be finishing after our app has already committed state
// and is about to get shutdown.  What we *should* do is
// not commit anything in an AsyncTask, but that's a bigger
// change than we want now.
t.commitAllowingStateLoss();
if (drawerLayout != null) {
// Tablet layout does not have a drawer
drawerLayout.closeDrawer(navDrawer);
}
}


public void loadChildFragment(androidx.fragment.app.Fragment fragment, de.danoeh.antennapod.fragment.TransitionEffect transition) {
org.apache.commons.lang3.Validate.notNull(fragment);
androidx.fragment.app.FragmentTransaction transaction;
transaction = getSupportFragmentManager().beginTransaction();
switch (transition) {
case FADE :
transaction.setCustomAnimations(de.danoeh.antennapod.R.anim.fade_in, de.danoeh.antennapod.R.anim.fade_out);
break;
case SLIDE :
transaction.setCustomAnimations(de.danoeh.antennapod.R.anim.slide_right_in, de.danoeh.antennapod.R.anim.slide_left_out, de.danoeh.antennapod.R.anim.slide_left_in, de.danoeh.antennapod.R.anim.slide_right_out);
break;
}
transaction.hide(getSupportFragmentManager().findFragmentByTag(de.danoeh.antennapod.activity.MainActivity.MAIN_FRAGMENT_TAG)).add(de.danoeh.antennapod.R.id.main_view, fragment, de.danoeh.antennapod.activity.MainActivity.MAIN_FRAGMENT_TAG).addToBackStack(null).commit();
}


public void loadChildFragment(androidx.fragment.app.Fragment fragment) {
loadChildFragment(fragment, de.danoeh.antennapod.fragment.TransitionEffect.NONE);
}


@java.lang.Override
protected void onPostCreate(android.os.Bundle savedInstanceState) {
super.onPostCreate(savedInstanceState);
if (drawerToggle != null) {
// Tablet layout does not have a drawer
drawerToggle.syncState();
}
}


@java.lang.Override
public void onConfigurationChanged(android.content.res.Configuration newConfig) {
super.onConfigurationChanged(newConfig);
if (drawerToggle != null) {
// Tablet layout does not have a drawer
drawerToggle.onConfigurationChanged(newConfig);
}
setNavDrawerSize();
}


private void setNavDrawerSize() {
if (drawerToggle == null) {
// Tablet layout does not have a drawer
return;
}
float screenPercent;
switch(MUID_STATIC) {
// MainActivity_35_BinaryMutator
case 35143: {
screenPercent = getResources().getInteger(de.danoeh.antennapod.R.integer.nav_drawer_screen_size_percent) / 0.01F;
break;
}
default: {
screenPercent = getResources().getInteger(de.danoeh.antennapod.R.integer.nav_drawer_screen_size_percent) * 0.01F;
break;
}
}
int width;
switch(MUID_STATIC) {
// MainActivity_36_BinaryMutator
case 36143: {
width = ((int) (getScreenWidth() / screenPercent));
break;
}
default: {
width = ((int) (getScreenWidth() * screenPercent));
break;
}
}
int maxWidth;
maxWidth = ((int) (getResources().getDimension(de.danoeh.antennapod.R.dimen.nav_drawer_max_screen_size)));
navDrawer.getLayoutParams().width = java.lang.Math.min(width, maxWidth);
}


private int getScreenWidth() {
android.util.DisplayMetrics displayMetrics;
displayMetrics = new android.util.DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
return displayMetrics.widthPixels;
}


@java.lang.Override
protected void onRestoreInstanceState(android.os.Bundle savedInstanceState) {
super.onRestoreInstanceState(savedInstanceState);
if (getBottomSheet().getState() == com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED) {
bottomSheetCallback.onSlide(null, 1.0F);
}
}


@java.lang.Override
public void onStart() {
super.onStart();
org.greenrobot.eventbus.EventBus.getDefault().register(this);
de.danoeh.antennapod.dialog.RatingDialog.init(this);
}


@java.lang.Override
protected void onResume() {
super.onResume();
handleNavIntent();
de.danoeh.antennapod.dialog.RatingDialog.check();
if (lastTheme != de.danoeh.antennapod.core.preferences.ThemeSwitcher.getNoTitleTheme(this)) {
finish();
startActivity(new android.content.Intent(this, de.danoeh.antennapod.activity.MainActivity.class));
}
if (de.danoeh.antennapod.storage.preferences.UserPreferences.getHiddenDrawerItems().contains(de.danoeh.antennapod.fragment.NavDrawerFragment.getLastNavFragment(this))) {
loadFragment(de.danoeh.antennapod.storage.preferences.UserPreferences.getDefaultPage(), null);
}
}


@java.lang.Override
protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable
android.content.Intent data) {
super.onActivityResult(requestCode, resultCode, data);
lastTheme = de.danoeh.antennapod.core.preferences.ThemeSwitcher.getNoTitleTheme(this)// Don't recreate activity when a result is pending
;// Don't recreate activity when a result is pending

}


@java.lang.Override
protected void onStop() {
super.onStop();
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
}


@java.lang.Override
public void onTrimMemory(int level) {
super.onTrimMemory(level);
com.bumptech.glide.Glide.get(this).trimMemory(level);
}


@java.lang.Override
public void onLowMemory() {
super.onLowMemory();
com.bumptech.glide.Glide.get(this).clearMemory();
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
if ((drawerToggle != null) && drawerToggle.onOptionsItemSelected(item)) {
// Tablet layout does not have a drawer
return true;
} else if (item.getItemId() == android.R.id.home) {
if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
getSupportFragmentManager().popBackStack();
}
return true;
} else {
return super.onOptionsItemSelected(item);
}
}


@java.lang.Override
public void onBackPressed() {
if (isDrawerOpen()) {
drawerLayout.closeDrawer(navDrawer);
} else if (sheetBehavior.getState() == com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED) {
sheetBehavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
} else if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
super.onBackPressed();
} else {
java.lang.String toPage;
toPage = de.danoeh.antennapod.storage.preferences.UserPreferences.getDefaultPage();
if (de.danoeh.antennapod.fragment.NavDrawerFragment.getLastNavFragment(this).equals(toPage) || de.danoeh.antennapod.storage.preferences.UserPreferences.DEFAULT_PAGE_REMEMBER.equals(toPage)) {
if (de.danoeh.antennapod.storage.preferences.UserPreferences.backButtonOpensDrawer() && (drawerLayout != null)) {
drawerLayout.openDrawer(navDrawer);
} else {
super.onBackPressed();
}
} else {
loadFragment(toPage, null);
}
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.MessageEvent event) {
android.util.Log.d(de.danoeh.antennapod.activity.MainActivity.TAG, ("onEvent(" + event) + ")");
com.google.android.material.snackbar.Snackbar snackbar;
snackbar = showSnackbarAbovePlayer(event.message, com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
if (event.action != null) {
switch(MUID_STATIC) {
// MainActivity_37_BuggyGUIListenerOperatorMutator
case 37143: {
snackbar.setAction(event.actionText, null);
break;
}
default: {
snackbar.setAction(event.actionText, (android.view.View v) -> event.action.accept(this));
break;
}
}
}
}


private void handleNavIntent() {
android.util.Log.d(de.danoeh.antennapod.activity.MainActivity.TAG, "handleNavIntent()");
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_38_RandomActionIntentDefinitionOperatorMutator
case 38143: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = getIntent();
break;
}
}
if (intent.hasExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_FEED_ID)) {
long feedId;
feedId = intent.getLongExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_FEED_ID, 0);
android.os.Bundle args;
args = intent.getBundleExtra(de.danoeh.antennapod.ui.appstartintent.MainActivityStarter.EXTRA_FRAGMENT_ARGS);
if (feedId > 0) {
boolean startedFromSearch;
startedFromSearch = intent.getBooleanExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_STARTED_FROM_SEARCH, false);
boolean addToBackStack;
addToBackStack = intent.getBooleanExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_ADD_TO_BACK_STACK, false);
if (startedFromSearch || addToBackStack) {
loadChildFragment(de.danoeh.antennapod.fragment.FeedItemlistFragment.newInstance(feedId));
} else {
loadFeedFragmentById(feedId, args);
}
}
sheetBehavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
} else if (intent.hasExtra(de.danoeh.antennapod.ui.appstartintent.MainActivityStarter.EXTRA_FRAGMENT_TAG)) {
java.lang.String tag;
tag = intent.getStringExtra(de.danoeh.antennapod.ui.appstartintent.MainActivityStarter.EXTRA_FRAGMENT_TAG);
android.os.Bundle args;
args = intent.getBundleExtra(de.danoeh.antennapod.ui.appstartintent.MainActivityStarter.EXTRA_FRAGMENT_ARGS);
if (tag != null) {
loadFragment(tag, args);
}
sheetBehavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
} else if (intent.getBooleanExtra(de.danoeh.antennapod.ui.appstartintent.MainActivityStarter.EXTRA_OPEN_PLAYER, false)) {
sheetBehavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
bottomSheetCallback.onSlide(null, 1.0F);
} else {
handleDeeplink(intent.getData());
}
if (intent.getBooleanExtra(de.danoeh.antennapod.ui.appstartintent.MainActivityStarter.EXTRA_OPEN_DRAWER, false) && (drawerLayout != null)) {
drawerLayout.open();
}
if (intent.getBooleanExtra(de.danoeh.antennapod.ui.appstartintent.MainActivityStarter.EXTRA_OPEN_DOWNLOAD_LOGS, false)) {
new de.danoeh.antennapod.fragment.DownloadLogFragment().show(getSupportFragmentManager(), null);
}
if (intent.getBooleanExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_REFRESH_ON_START, false)) {
de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnceOrAsk(this);
}
// to avoid handling the intent twice when the configuration changes
setIntent(new android.content.Intent(this, de.danoeh.antennapod.activity.MainActivity.class));
}


@java.lang.Override
protected void onNewIntent(android.content.Intent intent) {
super.onNewIntent(intent);
setIntent(intent);
handleNavIntent();
}


public com.google.android.material.snackbar.Snackbar showSnackbarAbovePlayer(java.lang.CharSequence text, int duration) {
com.google.android.material.snackbar.Snackbar s;
if (getBottomSheet().getState() == com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED) {
switch(MUID_STATIC) {
// MainActivity_39_InvalidIDFindViewOperatorMutator
case 39143: {
s = com.google.android.material.snackbar.Snackbar.make(findViewById(732221), text, duration);
break;
}
default: {
s = com.google.android.material.snackbar.Snackbar.make(findViewById(de.danoeh.antennapod.R.id.main_view), text, duration);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_40_InvalidIDFindViewOperatorMutator
case 40143: {
if (findViewById(732221).getVisibility() == android.view.View.VISIBLE) {
s.setAnchorView(findViewById(de.danoeh.antennapod.R.id.audioplayerFragment));
}
break;
}
default: {
if (findViewById(de.danoeh.antennapod.R.id.audioplayerFragment).getVisibility() == android.view.View.VISIBLE) {
switch(MUID_STATIC) {
// MainActivity_41_InvalidIDFindViewOperatorMutator
case 41143: {
s.setAnchorView(findViewById(732221));
break;
}
default: {
s.setAnchorView(findViewById(de.danoeh.antennapod.R.id.audioplayerFragment));
break;
}
}
}
break;
}
}
} else {
switch(MUID_STATIC) {
// MainActivity_42_InvalidIDFindViewOperatorMutator
case 42143: {
s = com.google.android.material.snackbar.Snackbar.make(findViewById(732221), text, duration);
break;
}
default: {
s = com.google.android.material.snackbar.Snackbar.make(findViewById(android.R.id.content), text, duration);
break;
}
}
}
s.show();
return s;
}


public com.google.android.material.snackbar.Snackbar showSnackbarAbovePlayer(int text, int duration) {
return showSnackbarAbovePlayer(getResources().getText(text), duration);
}


/**
 * Handles the deep link incoming via App Actions.
 * Performs an in-app search or opens the relevant feature of the app
 * depending on the query.
 *
 * @param uri
 * 		incoming deep link
 */
private void handleDeeplink(android.net.Uri uri) {
if ((uri == null) || (uri.getPath() == null)) {
return;
}
android.util.Log.d(de.danoeh.antennapod.activity.MainActivity.TAG, "Handling deeplink: " + uri.toString());
switch (uri.getPath()) {
case "/deeplink/search" :
java.lang.String query;
query = uri.getQueryParameter("query");
if (query == null) {
return;
}
this.loadChildFragment(de.danoeh.antennapod.fragment.SearchFragment.newInstance(query));
break;
case "/deeplink/main" :
java.lang.String feature;
feature = uri.getQueryParameter("page");
if (feature == null) {
return;
}
switch (feature) {
case "DOWNLOADS" :
loadFragment(de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG, null);
break;
case "HISTORY" :
loadFragment(de.danoeh.antennapod.fragment.PlaybackHistoryFragment.TAG, null);
break;
case "EPISODES" :
loadFragment(de.danoeh.antennapod.fragment.AllEpisodesFragment.TAG, null);
break;
case "QUEUE" :
loadFragment(de.danoeh.antennapod.fragment.QueueFragment.TAG, null);
break;
case "SUBSCRIPTIONS" :
loadFragment(de.danoeh.antennapod.fragment.SubscriptionFragment.TAG, null);
break;
default :
showSnackbarAbovePlayer(getString(de.danoeh.antennapod.R.string.app_action_not_found, feature), com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
return;
}
break;
default :
break;
}
}


// Hardware keyboard support
@java.lang.Override
public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
android.view.View currentFocus;
currentFocus = getCurrentFocus();
if (currentFocus instanceof android.widget.EditText) {
return super.onKeyUp(keyCode, event);
}
android.media.AudioManager audioManager;
audioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
java.lang.Integer customKeyCode;
customKeyCode = null;
org.greenrobot.eventbus.EventBus.getDefault().post(event);
switch (keyCode) {
case android.view.KeyEvent.KEYCODE_P :
customKeyCode = android.view.KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
break;
case android.view.KeyEvent.KEYCODE_J :
// Fallthrough
case android.view.KeyEvent.KEYCODE_A :
case android.view.KeyEvent.KEYCODE_COMMA :
customKeyCode = android.view.KeyEvent.KEYCODE_MEDIA_REWIND;
break;
case android.view.KeyEvent.KEYCODE_K :
// Fallthrough
case android.view.KeyEvent.KEYCODE_D :
case android.view.KeyEvent.KEYCODE_PERIOD :
customKeyCode = android.view.KeyEvent.KEYCODE_MEDIA_FAST_FORWARD;
break;
case android.view.KeyEvent.KEYCODE_PLUS :
// Fallthrough
case android.view.KeyEvent.KEYCODE_W :
audioManager.adjustStreamVolume(android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.ADJUST_RAISE, android.media.AudioManager.FLAG_SHOW_UI);
return true;
case android.view.KeyEvent.KEYCODE_MINUS :
// Fallthrough
case android.view.KeyEvent.KEYCODE_S :
audioManager.adjustStreamVolume(android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.ADJUST_LOWER, android.media.AudioManager.FLAG_SHOW_UI);
return true;
case android.view.KeyEvent.KEYCODE_M :
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
audioManager.adjustStreamVolume(android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.ADJUST_TOGGLE_MUTE, android.media.AudioManager.FLAG_SHOW_UI);
return true;
}
break;
}
if (customKeyCode != null) {
sendBroadcast(de.danoeh.antennapod.core.receiver.MediaButtonReceiver.createIntent(this, customKeyCode));
return true;
}
return super.onKeyUp(keyCode, event);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
