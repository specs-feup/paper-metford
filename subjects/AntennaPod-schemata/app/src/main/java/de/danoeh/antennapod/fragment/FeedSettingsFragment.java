package de.danoeh.antennapod.fragment;
import java.util.Locale;
import de.danoeh.antennapod.event.settings.SkipIntroEndingChangedEvent;
import de.danoeh.antennapod.event.settings.SpeedPresetChangedEvent;
import de.danoeh.antennapod.core.storage.DBWriter;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import android.os.Build;
import android.provider.Settings;
import androidx.preference.PreferenceFragmentCompat;
import io.reactivex.MaybeOnSubscribe;
import de.danoeh.antennapod.model.feed.Feed;
import de.danoeh.antennapod.model.feed.FeedFilter;
import java.util.Collections;
import io.reactivex.Maybe;
import android.util.Log;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.preference.Preference;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.model.feed.FeedPreferences;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.model.feed.VolumeAdaptionSetting;
import org.greenrobot.eventbus.EventBus;
import android.net.Uri;
import de.danoeh.antennapod.R;
import java.util.concurrent.ExecutionException;
import android.content.pm.PackageManager;
import android.widget.Toast;
import de.danoeh.antennapod.databinding.PlaybackSpeedFeedSettingDialogBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import androidx.preference.SwitchPreferenceCompat;
import android.os.Bundle;
import android.view.ViewGroup;
import de.danoeh.antennapod.dialog.AuthenticationDialog;
import io.reactivex.schedulers.Schedulers;
import androidx.preference.ListPreference;
import androidx.activity.result.contract.ActivityResultContracts;
import de.danoeh.antennapod.dialog.TagSettingsDialog;
import de.danoeh.antennapod.dialog.FeedPreferenceSkipDialog;
import android.content.Intent;
import android.view.View;
import android.Manifest;
import de.danoeh.antennapod.event.settings.VolumeAdaptionChangedEvent;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.activity.result.ActivityResultLauncher;
import java.util.concurrent.Future;
import de.danoeh.antennapod.dialog.EpisodeFilterDialog;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FeedSettingsFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "FeedSettingsFragment";

    private static final java.lang.String EXTRA_FEED_ID = "de.danoeh.antennapod.extra.feedId";

    private io.reactivex.disposables.Disposable disposable;

    public static de.danoeh.antennapod.fragment.FeedSettingsFragment newInstance(de.danoeh.antennapod.model.feed.Feed feed) {
        de.danoeh.antennapod.fragment.FeedSettingsFragment fragment;
        fragment = new de.danoeh.antennapod.fragment.FeedSettingsFragment();
        android.os.Bundle arguments;
        arguments = new android.os.Bundle();
        arguments.putLong(de.danoeh.antennapod.fragment.FeedSettingsFragment.EXTRA_FEED_ID, feed.getId());
        fragment.setArguments(arguments);
        return fragment;
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        android.view.View root;
        root = inflater.inflate(de.danoeh.antennapod.R.layout.feedsettings, container, false);
        long feedId;
        feedId = getArguments().getLong(de.danoeh.antennapod.fragment.FeedSettingsFragment.EXTRA_FEED_ID);
        com.google.android.material.appbar.MaterialToolbar toolbar;
        switch(MUID_STATIC) {
            // FeedSettingsFragment_0_InvalidViewFocusOperatorMutator
            case 123: {
                /**
                * Inserted by Kadabra
                */
                toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
                toolbar.requestFocus();
                break;
            }
            // FeedSettingsFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1123: {
                /**
                * Inserted by Kadabra
                */
                toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
                toolbar.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
            break;
        }
    }
    switch(MUID_STATIC) {
        // FeedSettingsFragment_2_BuggyGUIListenerOperatorMutator
        case 2123: {
            toolbar.setNavigationOnClickListener(null);
            break;
        }
        default: {
        toolbar.setNavigationOnClickListener((android.view.View v) -> getParentFragmentManager().popBackStack());
        break;
    }
}
getParentFragmentManager().beginTransaction().replace(de.danoeh.antennapod.R.id.settings_fragment_container, de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.newInstance(feedId), "settings_fragment").commitAllowingStateLoss();
disposable = io.reactivex.Maybe.create(((io.reactivex.MaybeOnSubscribe<de.danoeh.antennapod.model.feed.Feed>) ((io.reactivex.MaybeEmitter<de.danoeh.antennapod.model.feed.Feed> emitter) -> {
    de.danoeh.antennapod.model.feed.Feed feed;
    feed = de.danoeh.antennapod.core.storage.DBReader.getFeed(feedId);
    if (feed != null) {
        emitter.onSuccess(feed);
    } else {
        emitter.onComplete();
    }
}))).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.feed.Feed result) -> toolbar.setSubtitle(result.getTitle()), (java.lang.Throwable error) -> android.util.Log.d(de.danoeh.antennapod.fragment.FeedSettingsFragment.TAG, android.util.Log.getStackTraceString(error)), () -> {
});
return root;
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
if (disposable != null) {
    disposable.dispose();
}
}


public static class FeedSettingsPreferenceFragment extends androidx.preference.PreferenceFragmentCompat {
private static final java.lang.CharSequence PREF_EPISODE_FILTER = "episodeFilter";

private static final java.lang.CharSequence PREF_SCREEN = "feedSettingsScreen";

private static final java.lang.CharSequence PREF_AUTHENTICATION = "authentication";

private static final java.lang.CharSequence PREF_AUTO_DELETE = "autoDelete";

private static final java.lang.CharSequence PREF_CATEGORY_AUTO_DOWNLOAD = "autoDownloadCategory";

private static final java.lang.CharSequence PREF_NEW_EPISODES_ACTION = "feedNewEpisodesAction";

private static final java.lang.String PREF_FEED_PLAYBACK_SPEED = "feedPlaybackSpeed";

private static final java.lang.String PREF_AUTO_SKIP = "feedAutoSkip";

private static final java.lang.String PREF_TAGS = "tags";

private de.danoeh.antennapod.model.feed.Feed feed;

private io.reactivex.disposables.Disposable disposable;

private de.danoeh.antennapod.model.feed.FeedPreferences feedPreferences;

public static de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment newInstance(long feedId) {
    de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment fragment;
    fragment = new de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment();
    android.os.Bundle arguments;
    arguments = new android.os.Bundle();
    arguments.putLong(de.danoeh.antennapod.fragment.FeedSettingsFragment.EXTRA_FEED_ID, feedId);
    fragment.setArguments(arguments);
    return fragment;
}


boolean notificationPermissionDenied = false;

private final androidx.activity.result.ActivityResultLauncher<java.lang.String> requestPermissionLauncher = registerForActivityResult(new androidx.activity.result.contract.ActivityResultContracts.RequestPermission(), (java.lang.Boolean isGranted) -> {
    if (isGranted) {
        return;
    }
    if (notificationPermissionDenied) {
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // FeedSettingsFragment_3_InvalidKeyIntentOperatorMutator
            case 3123: {
                intent = new android.content.Intent((String) null);
                break;
            }
            // FeedSettingsFragment_4_RandomActionIntentDefinitionOperatorMutator
            case 4123: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            break;
        }
    }
    android.net.Uri uri;
    uri = android.net.Uri.fromParts("package", getContext().getPackageName(), null);
    switch(MUID_STATIC) {
        // FeedSettingsFragment_5_RandomActionIntentDefinitionOperatorMutator
        case 5123: {
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
        intent.setData(uri);
        break;
    }
}
startActivity(intent);
return;
}
android.widget.Toast.makeText(getContext(), de.danoeh.antennapod.R.string.notification_permission_denied, android.widget.Toast.LENGTH_LONG).show();
notificationPermissionDenied = true;
});

@java.lang.Override
public androidx.recyclerview.widget.RecyclerView onCreateRecyclerView(android.view.LayoutInflater inflater, android.view.ViewGroup parent, android.os.Bundle state) {
final androidx.recyclerview.widget.RecyclerView view;
view = super.onCreateRecyclerView(inflater, parent, state);
// To prevent transition animation because of summary update
view.setItemAnimator(null);
view.setLayoutAnimation(null);
return view;
}


@java.lang.Override
public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
addPreferencesFromResource(de.danoeh.antennapod.R.xml.feed_settings);
// To prevent displaying partially loaded data
findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_SCREEN).setVisible(false);
long feedId;
feedId = getArguments().getLong(de.danoeh.antennapod.fragment.FeedSettingsFragment.EXTRA_FEED_ID);
disposable = io.reactivex.Maybe.create(((io.reactivex.MaybeOnSubscribe<de.danoeh.antennapod.model.feed.Feed>) ((io.reactivex.MaybeEmitter<de.danoeh.antennapod.model.feed.Feed> emitter) -> {
de.danoeh.antennapod.model.feed.Feed feed;
feed = de.danoeh.antennapod.core.storage.DBReader.getFeed(feedId);
if (feed != null) {
    emitter.onSuccess(feed);
} else {
    emitter.onComplete();
}
}))).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.feed.Feed result) -> {
feed = result;
feedPreferences = feed.getPreferences();
setupAutoDownloadGlobalPreference();
setupAutoDownloadPreference();
setupKeepUpdatedPreference();
setupAutoDeletePreference();
setupVolumeAdaptationPreferences();
setupNewEpisodesAction();
setupAuthentificationPreference();
setupEpisodeFilterPreference();
setupPlaybackSpeedPreference();
setupFeedAutoSkipPreference();
setupEpisodeNotificationPreference();
setupTags();
updateAutoDeleteSummary();
updateVolumeAdaptationValue();
updateAutoDownloadEnabled();
updateNewEpisodesAction();
if (feed.isLocalFeed()) {
    findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_AUTHENTICATION).setVisible(false);
    findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_CATEGORY_AUTO_DOWNLOAD).setVisible(false);
}
findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_SCREEN).setVisible(true);
}, (java.lang.Throwable error) -> android.util.Log.d(de.danoeh.antennapod.fragment.FeedSettingsFragment.TAG, android.util.Log.getStackTraceString(error)), () -> {
});
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
if (disposable != null) {
disposable.dispose();
}
}


private void setupFeedAutoSkipPreference() {
findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_AUTO_SKIP).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
new de.danoeh.antennapod.dialog.FeedPreferenceSkipDialog(getContext(), feedPreferences.getFeedSkipIntro(), feedPreferences.getFeedSkipEnding()) {
    @java.lang.Override
    protected void onConfirmed(int skipIntro, int skipEnding) {
        feedPreferences.setFeedSkipIntro(skipIntro);
        feedPreferences.setFeedSkipEnding(skipEnding);
        de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
        org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.settings.SkipIntroEndingChangedEvent(feedPreferences.getFeedSkipIntro(), feedPreferences.getFeedSkipEnding(), feed.getId()));
    }

}.show();
return false;
});
}


private void setupPlaybackSpeedPreference() {
androidx.preference.Preference feedPlaybackSpeedPreference;
feedPlaybackSpeedPreference = findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_FEED_PLAYBACK_SPEED);
feedPlaybackSpeedPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
de.danoeh.antennapod.databinding.PlaybackSpeedFeedSettingDialogBinding viewBinding;
viewBinding = de.danoeh.antennapod.databinding.PlaybackSpeedFeedSettingDialogBinding.inflate(getLayoutInflater());
viewBinding.seekBar.setProgressChangedListener((java.lang.Float speed) -> viewBinding.currentSpeedLabel.setText(java.lang.String.format(java.util.Locale.getDefault(), "%.2fx", speed)));
float speed;
speed = feedPreferences.getFeedPlaybackSpeed();
viewBinding.useGlobalCheckbox.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> {
    viewBinding.seekBar.setEnabled(!isChecked);
    viewBinding.seekBar.setAlpha(isChecked ? 0.4F : 1.0F);
    viewBinding.currentSpeedLabel.setAlpha(isChecked ? 0.4F : 1.0F);
});
viewBinding.useGlobalCheckbox.setChecked(speed == de.danoeh.antennapod.model.feed.FeedPreferences.SPEED_USE_GLOBAL);
viewBinding.seekBar.updateSpeed(speed == de.danoeh.antennapod.model.feed.FeedPreferences.SPEED_USE_GLOBAL ? 1 : speed);
switch(MUID_STATIC) {
    // FeedSettingsFragment_6_BuggyGUIListenerOperatorMutator
    case 6123: {
        new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext()).setTitle(de.danoeh.antennapod.R.string.playback_speed).setView(viewBinding.getRoot()).setPositiveButton(android.R.string.ok, null).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
        break;
    }
    default: {
    new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext()).setTitle(de.danoeh.antennapod.R.string.playback_speed).setView(viewBinding.getRoot()).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> {
        float newSpeed;
        newSpeed = (viewBinding.useGlobalCheckbox.isChecked()) ? de.danoeh.antennapod.model.feed.FeedPreferences.SPEED_USE_GLOBAL : viewBinding.seekBar.getCurrentSpeed();
        feedPreferences.setFeedPlaybackSpeed(newSpeed);
        de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
        org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.settings.SpeedPresetChangedEvent(feedPreferences.getFeedPlaybackSpeed(), feed.getId()));
    }).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
    break;
}
}
return true;
});
}


private void setupEpisodeFilterPreference() {
findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_EPISODE_FILTER).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
new de.danoeh.antennapod.dialog.EpisodeFilterDialog(getContext(), feedPreferences.getFilter()) {
@java.lang.Override
protected void onConfirmed(de.danoeh.antennapod.model.feed.FeedFilter filter) {
    feedPreferences.setFilter(filter);
    de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
}

}.show();
return false;
});
}


private void setupAuthentificationPreference() {
findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_AUTHENTICATION).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
new de.danoeh.antennapod.dialog.AuthenticationDialog(getContext(), de.danoeh.antennapod.R.string.authentication_label, true, feedPreferences.getUsername(), feedPreferences.getPassword()) {
@java.lang.Override
protected void onConfirmed(java.lang.String username, java.lang.String password) {
    feedPreferences.setUsername(username);
    feedPreferences.setPassword(password);
    java.util.concurrent.Future<?> setPreferencesFuture;
    setPreferencesFuture = de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
    new java.lang.Thread(() -> {
        try {
            setPreferencesFuture.get();
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
        }
        de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnce(getContext(), feed);
    }, "RefreshAfterCredentialChange").start();
}

}.show();
return false;
});
}


private void setupAutoDeletePreference() {
findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_AUTO_DELETE).setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
switch (((java.lang.String) (newValue))) {
case "global" :
    feedPreferences.setAutoDeleteAction(de.danoeh.antennapod.model.feed.FeedPreferences.AutoDeleteAction.GLOBAL);
    break;
case "always" :
    feedPreferences.setAutoDeleteAction(de.danoeh.antennapod.model.feed.FeedPreferences.AutoDeleteAction.ALWAYS);
    break;
case "never" :
    feedPreferences.setAutoDeleteAction(de.danoeh.antennapod.model.feed.FeedPreferences.AutoDeleteAction.NEVER);
    break;
default :
}
de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
updateAutoDeleteSummary();
return false;
});
}


private void updateAutoDeleteSummary() {
androidx.preference.ListPreference autoDeletePreference;
autoDeletePreference = findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_AUTO_DELETE);
switch (feedPreferences.getAutoDeleteAction()) {
case GLOBAL :
autoDeletePreference.setSummary(de.danoeh.antennapod.R.string.global_default);
autoDeletePreference.setValue("global");
break;
case ALWAYS :
autoDeletePreference.setSummary(de.danoeh.antennapod.R.string.feed_auto_download_always);
autoDeletePreference.setValue("always");
break;
case NEVER :
autoDeletePreference.setSummary(de.danoeh.antennapod.R.string.feed_auto_download_never);
autoDeletePreference.setValue("never");
break;
}
}


private void setupVolumeAdaptationPreferences() {
androidx.preference.ListPreference volumeAdaptationPreference;
volumeAdaptationPreference = findPreference("volumeReduction");
volumeAdaptationPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
switch (((java.lang.String) (newValue))) {
case "off" :
    feedPreferences.setVolumeAdaptionSetting(de.danoeh.antennapod.model.feed.VolumeAdaptionSetting.OFF);
    break;
case "light" :
    feedPreferences.setVolumeAdaptionSetting(de.danoeh.antennapod.model.feed.VolumeAdaptionSetting.LIGHT_REDUCTION);
    break;
case "heavy" :
    feedPreferences.setVolumeAdaptionSetting(de.danoeh.antennapod.model.feed.VolumeAdaptionSetting.HEAVY_REDUCTION);
    break;
case "light_boost" :
    feedPreferences.setVolumeAdaptionSetting(de.danoeh.antennapod.model.feed.VolumeAdaptionSetting.LIGHT_BOOST);
    break;
case "medium_boost" :
    feedPreferences.setVolumeAdaptionSetting(de.danoeh.antennapod.model.feed.VolumeAdaptionSetting.MEDIUM_BOOST);
    break;
case "heavy_boost" :
    feedPreferences.setVolumeAdaptionSetting(de.danoeh.antennapod.model.feed.VolumeAdaptionSetting.HEAVY_BOOST);
    break;
default :
}
de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
updateVolumeAdaptationValue();
org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.settings.VolumeAdaptionChangedEvent(feedPreferences.getVolumeAdaptionSetting(), feed.getId()));
return false;
});
}


private void updateVolumeAdaptationValue() {
androidx.preference.ListPreference volumeAdaptationPreference;
volumeAdaptationPreference = findPreference("volumeReduction");
switch (feedPreferences.getVolumeAdaptionSetting()) {
case OFF :
volumeAdaptationPreference.setValue("off");
break;
case LIGHT_REDUCTION :
volumeAdaptationPreference.setValue("light");
break;
case HEAVY_REDUCTION :
volumeAdaptationPreference.setValue("heavy");
break;
case LIGHT_BOOST :
volumeAdaptationPreference.setValue("light_boost");
break;
case MEDIUM_BOOST :
volumeAdaptationPreference.setValue("medium_boost");
break;
case HEAVY_BOOST :
volumeAdaptationPreference.setValue("heavy_boost");
break;
}
}


private void setupNewEpisodesAction() {
findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_NEW_EPISODES_ACTION).setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
int code;
code = java.lang.Integer.parseInt(((java.lang.String) (newValue)));
feedPreferences.setNewEpisodesAction(de.danoeh.antennapod.model.feed.FeedPreferences.NewEpisodesAction.fromCode(code));
de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
updateNewEpisodesAction();
return false;
});
}


private void updateNewEpisodesAction() {
androidx.preference.ListPreference newEpisodesAction;
newEpisodesAction = findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_NEW_EPISODES_ACTION);
newEpisodesAction.setValue("" + feedPreferences.getNewEpisodesAction().code);
switch (feedPreferences.getNewEpisodesAction()) {
case GLOBAL :
newEpisodesAction.setSummary(de.danoeh.antennapod.R.string.global_default);
break;
case ADD_TO_INBOX :
newEpisodesAction.setSummary(de.danoeh.antennapod.R.string.feed_new_episodes_action_add_to_inbox);
break;
case NOTHING :
newEpisodesAction.setSummary(de.danoeh.antennapod.R.string.feed_new_episodes_action_nothing);
break;
default :
}
}


private void setupKeepUpdatedPreference() {
androidx.preference.SwitchPreferenceCompat pref;
pref = findPreference("keepUpdated");
pref.setChecked(feedPreferences.getKeepUpdated());
pref.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
boolean checked;
checked = newValue == java.lang.Boolean.TRUE;
feedPreferences.setKeepUpdated(checked);
de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
pref.setChecked(checked);
return false;
});
}


private void setupAutoDownloadGlobalPreference() {
if (!de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownload()) {
androidx.preference.SwitchPreferenceCompat autodl;
autodl = findPreference("autoDownload");
autodl.setChecked(false);
autodl.setEnabled(false);
autodl.setSummary(de.danoeh.antennapod.R.string.auto_download_disabled_globally);
findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_EPISODE_FILTER).setEnabled(false);
}
}


private void setupAutoDownloadPreference() {
androidx.preference.SwitchPreferenceCompat pref;
pref = findPreference("autoDownload");
pref.setEnabled(de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownload());
if (de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownload()) {
pref.setChecked(feedPreferences.getAutoDownload());
} else {
pref.setChecked(false);
pref.setSummary(de.danoeh.antennapod.R.string.auto_download_disabled_globally);
}
pref.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
boolean checked;
checked = newValue == java.lang.Boolean.TRUE;
feedPreferences.setAutoDownload(checked);
de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
updateAutoDownloadEnabled();
pref.setChecked(checked);
return false;
});
}


private void updateAutoDownloadEnabled() {
if ((feed != null) && (feed.getPreferences() != null)) {
boolean enabled;
enabled = feed.getPreferences().getAutoDownload() && de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownload();
findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_EPISODE_FILTER).setEnabled(enabled);
}
}


private void setupTags() {
findPreference(de.danoeh.antennapod.fragment.FeedSettingsFragment.FeedSettingsPreferenceFragment.PREF_TAGS).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
de.danoeh.antennapod.dialog.TagSettingsDialog.newInstance(java.util.Collections.singletonList(feedPreferences)).show(getChildFragmentManager(), de.danoeh.antennapod.dialog.TagSettingsDialog.TAG);
return true;
});
}


private void setupEpisodeNotificationPreference() {
androidx.preference.SwitchPreferenceCompat pref;
pref = findPreference("episodeNotification");
pref.setChecked(feedPreferences.getShowEpisodeNotification());
pref.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
if ((android.os.Build.VERSION.SDK_INT >= 33) && (androidx.core.content.ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED)) {
requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
return false;
}
boolean checked;
checked = newValue == java.lang.Boolean.TRUE;
feedPreferences.setShowEpisodeNotification(checked);
de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
pref.setChecked(checked);
return false;
});
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
