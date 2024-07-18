package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.event.playback.BufferUpdateEvent;
import org.greenrobot.eventbus.Subscribe;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;
import de.danoeh.antennapod.core.receiver.MediaButtonReceiver;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.Converter;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import de.danoeh.antennapod.event.playback.PlaybackServiceEvent;
import de.danoeh.antennapod.view.PlayButton;
import android.widget.TextView;
import java.util.List;
import de.danoeh.antennapod.event.FavoritesEvent;
import com.google.android.material.elevation.SurfaceColors;
import de.danoeh.antennapod.playback.cast.CastEnabledActivity;
import de.danoeh.antennapod.event.PlayerErrorEvent;
import de.danoeh.antennapod.dialog.MediaPlayerErrorDialog;
import io.reactivex.Maybe;
import android.util.Log;
import de.danoeh.antennapod.dialog.PlaybackControlsDialog;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import android.view.LayoutInflater;
import de.danoeh.antennapod.model.feed.FeedMedia;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.event.playback.SleepTimerUpdatedEvent;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.core.feed.util.PlaybackSpeedUtils;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import java.text.DecimalFormat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import org.greenrobot.eventbus.EventBus;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.dialog.SkipPreferenceDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import android.widget.ImageButton;
import androidx.viewpager2.widget.ViewPager2;
import de.danoeh.antennapod.event.playback.SpeedChangedEvent;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.ui.common.PlaybackSpeedIndicatorView;
import android.view.KeyEvent;
import de.danoeh.antennapod.dialog.SleepTimerDialog;
import de.danoeh.antennapod.core.util.playback.PlaybackController;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import de.danoeh.antennapod.dialog.VariableSpeedDialog;
import android.widget.ProgressBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.widget.SeekBar;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import android.content.Intent;
import android.view.MenuItem;
import de.danoeh.antennapod.model.feed.Chapter;
import de.danoeh.antennapod.core.util.TimeSpeedConverter;
import android.view.View;
import de.danoeh.antennapod.core.util.ChapterUtils;
import de.danoeh.antennapod.model.playback.Playable;
import java.text.NumberFormat;
import de.danoeh.antennapod.view.ChapterSeekBar;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Shows the audio player.
 */
public class AudioPlayerFragment extends androidx.fragment.app.Fragment implements android.widget.SeekBar.OnSeekBarChangeListener , androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "AudioPlayerFragment";

    public static final int POS_COVER = 0;

    public static final int POS_DESCRIPTION = 1;

    private static final int NUM_CONTENT_FRAGMENTS = 2;

    de.danoeh.antennapod.ui.common.PlaybackSpeedIndicatorView butPlaybackSpeed;

    android.widget.TextView txtvPlaybackSpeed;

    private androidx.viewpager2.widget.ViewPager2 pager;

    private android.widget.TextView txtvPosition;

    private android.widget.TextView txtvLength;

    private de.danoeh.antennapod.view.ChapterSeekBar sbPosition;

    private android.widget.ImageButton butRev;

    private android.widget.TextView txtvRev;

    private de.danoeh.antennapod.view.PlayButton butPlay;

    private android.widget.ImageButton butFF;

    private android.widget.TextView txtvFF;

    private android.widget.ImageButton butSkip;

    private com.google.android.material.appbar.MaterialToolbar toolbar;

    private android.widget.ProgressBar progressIndicator;

    private androidx.cardview.widget.CardView cardViewSeek;

    private android.widget.TextView txtvSeek;

    private de.danoeh.antennapod.core.util.playback.PlaybackController controller;

    private io.reactivex.disposables.Disposable disposable;

    private boolean showTimeLeft;

    private boolean seekedToChapterStart = false;

    private int currentChapterIndex = -1;

    private int duration;

    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        android.view.View root;
        root = inflater.inflate(de.danoeh.antennapod.R.layout.audioplayer_fragment, container, false);
        root.setOnTouchListener((android.view.View v,android.view.MotionEvent event) -> true)// Avoid clicks going through player to fragments below
        ;// Avoid clicks going through player to fragments below

        switch(MUID_STATIC) {
            // AudioPlayerFragment_0_InvalidViewFocusOperatorMutator
            case 139: {
                /**
                * Inserted by Kadabra
                */
                toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
                toolbar.requestFocus();
                break;
            }
            // AudioPlayerFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1139: {
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
    toolbar.setTitle("");
    switch(MUID_STATIC) {
        // AudioPlayerFragment_2_BuggyGUIListenerOperatorMutator
        case 2139: {
            toolbar.setNavigationOnClickListener(null);
            break;
        }
        default: {
        toolbar.setNavigationOnClickListener((android.view.View v) -> ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getBottomSheet().setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED));
        break;
    }
}
toolbar.setOnMenuItemClickListener(this);
de.danoeh.antennapod.fragment.ExternalPlayerFragment externalPlayerFragment;
externalPlayerFragment = new de.danoeh.antennapod.fragment.ExternalPlayerFragment();
getChildFragmentManager().beginTransaction().replace(de.danoeh.antennapod.R.id.playerFragment, externalPlayerFragment, de.danoeh.antennapod.fragment.ExternalPlayerFragment.TAG).commit();
switch(MUID_STATIC) {
    // AudioPlayerFragment_3_BinaryMutator
    case 3139: {
        root.findViewById(de.danoeh.antennapod.R.id.playerFragment).setBackgroundColor(com.google.android.material.elevation.SurfaceColors.getColorForElevation(getContext(), 8 / getResources().getDisplayMetrics().density));
        break;
    }
    default: {
    root.findViewById(de.danoeh.antennapod.R.id.playerFragment).setBackgroundColor(com.google.android.material.elevation.SurfaceColors.getColorForElevation(getContext(), 8 * getResources().getDisplayMetrics().density));
    break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_4_InvalidViewFocusOperatorMutator
case 4139: {
    /**
    * Inserted by Kadabra
    */
    butPlaybackSpeed = root.findViewById(de.danoeh.antennapod.R.id.butPlaybackSpeed);
    butPlaybackSpeed.requestFocus();
    break;
}
// AudioPlayerFragment_5_ViewComponentNotVisibleOperatorMutator
case 5139: {
    /**
    * Inserted by Kadabra
    */
    butPlaybackSpeed = root.findViewById(de.danoeh.antennapod.R.id.butPlaybackSpeed);
    butPlaybackSpeed.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
butPlaybackSpeed = root.findViewById(de.danoeh.antennapod.R.id.butPlaybackSpeed);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_6_InvalidViewFocusOperatorMutator
case 6139: {
/**
* Inserted by Kadabra
*/
txtvPlaybackSpeed = root.findViewById(de.danoeh.antennapod.R.id.txtvPlaybackSpeed);
txtvPlaybackSpeed.requestFocus();
break;
}
// AudioPlayerFragment_7_ViewComponentNotVisibleOperatorMutator
case 7139: {
/**
* Inserted by Kadabra
*/
txtvPlaybackSpeed = root.findViewById(de.danoeh.antennapod.R.id.txtvPlaybackSpeed);
txtvPlaybackSpeed.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvPlaybackSpeed = root.findViewById(de.danoeh.antennapod.R.id.txtvPlaybackSpeed);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_8_InvalidViewFocusOperatorMutator
case 8139: {
/**
* Inserted by Kadabra
*/
sbPosition = root.findViewById(de.danoeh.antennapod.R.id.sbPosition);
sbPosition.requestFocus();
break;
}
// AudioPlayerFragment_9_ViewComponentNotVisibleOperatorMutator
case 9139: {
/**
* Inserted by Kadabra
*/
sbPosition = root.findViewById(de.danoeh.antennapod.R.id.sbPosition);
sbPosition.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
sbPosition = root.findViewById(de.danoeh.antennapod.R.id.sbPosition);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_10_InvalidViewFocusOperatorMutator
case 10139: {
/**
* Inserted by Kadabra
*/
txtvPosition = root.findViewById(de.danoeh.antennapod.R.id.txtvPosition);
txtvPosition.requestFocus();
break;
}
// AudioPlayerFragment_11_ViewComponentNotVisibleOperatorMutator
case 11139: {
/**
* Inserted by Kadabra
*/
txtvPosition = root.findViewById(de.danoeh.antennapod.R.id.txtvPosition);
txtvPosition.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvPosition = root.findViewById(de.danoeh.antennapod.R.id.txtvPosition);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_12_InvalidViewFocusOperatorMutator
case 12139: {
/**
* Inserted by Kadabra
*/
txtvLength = root.findViewById(de.danoeh.antennapod.R.id.txtvLength);
txtvLength.requestFocus();
break;
}
// AudioPlayerFragment_13_ViewComponentNotVisibleOperatorMutator
case 13139: {
/**
* Inserted by Kadabra
*/
txtvLength = root.findViewById(de.danoeh.antennapod.R.id.txtvLength);
txtvLength.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvLength = root.findViewById(de.danoeh.antennapod.R.id.txtvLength);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_14_InvalidViewFocusOperatorMutator
case 14139: {
/**
* Inserted by Kadabra
*/
butRev = root.findViewById(de.danoeh.antennapod.R.id.butRev);
butRev.requestFocus();
break;
}
// AudioPlayerFragment_15_ViewComponentNotVisibleOperatorMutator
case 15139: {
/**
* Inserted by Kadabra
*/
butRev = root.findViewById(de.danoeh.antennapod.R.id.butRev);
butRev.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butRev = root.findViewById(de.danoeh.antennapod.R.id.butRev);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_16_InvalidViewFocusOperatorMutator
case 16139: {
/**
* Inserted by Kadabra
*/
txtvRev = root.findViewById(de.danoeh.antennapod.R.id.txtvRev);
txtvRev.requestFocus();
break;
}
// AudioPlayerFragment_17_ViewComponentNotVisibleOperatorMutator
case 17139: {
/**
* Inserted by Kadabra
*/
txtvRev = root.findViewById(de.danoeh.antennapod.R.id.txtvRev);
txtvRev.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvRev = root.findViewById(de.danoeh.antennapod.R.id.txtvRev);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_18_InvalidViewFocusOperatorMutator
case 18139: {
/**
* Inserted by Kadabra
*/
butPlay = root.findViewById(de.danoeh.antennapod.R.id.butPlay);
butPlay.requestFocus();
break;
}
// AudioPlayerFragment_19_ViewComponentNotVisibleOperatorMutator
case 19139: {
/**
* Inserted by Kadabra
*/
butPlay = root.findViewById(de.danoeh.antennapod.R.id.butPlay);
butPlay.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butPlay = root.findViewById(de.danoeh.antennapod.R.id.butPlay);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_20_InvalidViewFocusOperatorMutator
case 20139: {
/**
* Inserted by Kadabra
*/
butFF = root.findViewById(de.danoeh.antennapod.R.id.butFF);
butFF.requestFocus();
break;
}
// AudioPlayerFragment_21_ViewComponentNotVisibleOperatorMutator
case 21139: {
/**
* Inserted by Kadabra
*/
butFF = root.findViewById(de.danoeh.antennapod.R.id.butFF);
butFF.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butFF = root.findViewById(de.danoeh.antennapod.R.id.butFF);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_22_InvalidViewFocusOperatorMutator
case 22139: {
/**
* Inserted by Kadabra
*/
txtvFF = root.findViewById(de.danoeh.antennapod.R.id.txtvFF);
txtvFF.requestFocus();
break;
}
// AudioPlayerFragment_23_ViewComponentNotVisibleOperatorMutator
case 23139: {
/**
* Inserted by Kadabra
*/
txtvFF = root.findViewById(de.danoeh.antennapod.R.id.txtvFF);
txtvFF.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvFF = root.findViewById(de.danoeh.antennapod.R.id.txtvFF);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_24_InvalidViewFocusOperatorMutator
case 24139: {
/**
* Inserted by Kadabra
*/
butSkip = root.findViewById(de.danoeh.antennapod.R.id.butSkip);
butSkip.requestFocus();
break;
}
// AudioPlayerFragment_25_ViewComponentNotVisibleOperatorMutator
case 25139: {
/**
* Inserted by Kadabra
*/
butSkip = root.findViewById(de.danoeh.antennapod.R.id.butSkip);
butSkip.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butSkip = root.findViewById(de.danoeh.antennapod.R.id.butSkip);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_26_InvalidViewFocusOperatorMutator
case 26139: {
/**
* Inserted by Kadabra
*/
progressIndicator = root.findViewById(de.danoeh.antennapod.R.id.progLoading);
progressIndicator.requestFocus();
break;
}
// AudioPlayerFragment_27_ViewComponentNotVisibleOperatorMutator
case 27139: {
/**
* Inserted by Kadabra
*/
progressIndicator = root.findViewById(de.danoeh.antennapod.R.id.progLoading);
progressIndicator.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progressIndicator = root.findViewById(de.danoeh.antennapod.R.id.progLoading);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_28_InvalidViewFocusOperatorMutator
case 28139: {
/**
* Inserted by Kadabra
*/
cardViewSeek = root.findViewById(de.danoeh.antennapod.R.id.cardViewSeek);
cardViewSeek.requestFocus();
break;
}
// AudioPlayerFragment_29_ViewComponentNotVisibleOperatorMutator
case 29139: {
/**
* Inserted by Kadabra
*/
cardViewSeek = root.findViewById(de.danoeh.antennapod.R.id.cardViewSeek);
cardViewSeek.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
cardViewSeek = root.findViewById(de.danoeh.antennapod.R.id.cardViewSeek);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_30_InvalidViewFocusOperatorMutator
case 30139: {
/**
* Inserted by Kadabra
*/
txtvSeek = root.findViewById(de.danoeh.antennapod.R.id.txtvSeek);
txtvSeek.requestFocus();
break;
}
// AudioPlayerFragment_31_ViewComponentNotVisibleOperatorMutator
case 31139: {
/**
* Inserted by Kadabra
*/
txtvSeek = root.findViewById(de.danoeh.antennapod.R.id.txtvSeek);
txtvSeek.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvSeek = root.findViewById(de.danoeh.antennapod.R.id.txtvSeek);
break;
}
}
setupLengthTextView();
setupControlButtons();
switch(MUID_STATIC) {
// AudioPlayerFragment_32_BuggyGUIListenerOperatorMutator
case 32139: {
butPlaybackSpeed.setOnClickListener(null);
break;
}
default: {
butPlaybackSpeed.setOnClickListener((android.view.View v) -> new de.danoeh.antennapod.dialog.VariableSpeedDialog().show(getChildFragmentManager(), null));
break;
}
}
sbPosition.setOnSeekBarChangeListener(this);
switch(MUID_STATIC) {
// AudioPlayerFragment_33_InvalidViewFocusOperatorMutator
case 33139: {
/**
* Inserted by Kadabra
*/
pager = root.findViewById(de.danoeh.antennapod.R.id.pager);
pager.requestFocus();
break;
}
// AudioPlayerFragment_34_ViewComponentNotVisibleOperatorMutator
case 34139: {
/**
* Inserted by Kadabra
*/
pager = root.findViewById(de.danoeh.antennapod.R.id.pager);
pager.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
pager = root.findViewById(de.danoeh.antennapod.R.id.pager);
break;
}
}
pager.setAdapter(new de.danoeh.antennapod.fragment.AudioPlayerFragment.AudioPlayerPagerAdapter(this));
// Required for getChildAt(int) in ViewPagerBottomSheetBehavior to return the correct page
pager.setOffscreenPageLimit(((int) (de.danoeh.antennapod.fragment.AudioPlayerFragment.NUM_CONTENT_FRAGMENTS)));
pager.registerOnPageChangeCallback(new androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
@java.lang.Override
public void onPageSelected(int position) {
pager.post(() -> {
if (getActivity() != null) {
// By the time this is posted, the activity might be closed again.
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getBottomSheet().updateScrollingChild();
}
});
}

});
return root;
}


private void setChapterDividers(de.danoeh.antennapod.model.playback.Playable media) {
if (media == null) {
return;
}
float[] dividerPos;
dividerPos = null;
if ((media.getChapters() != null) && (!media.getChapters().isEmpty())) {
java.util.List<de.danoeh.antennapod.model.feed.Chapter> chapters;
chapters = media.getChapters();
dividerPos = new float[chapters.size()];
for (int i = 0; i < chapters.size(); i++) {
switch(MUID_STATIC) {
// AudioPlayerFragment_35_BinaryMutator
case 35139: {
dividerPos[i] = chapters.get(i).getStart() * ((float) (duration));
break;
}
default: {
dividerPos[i] = chapters.get(i).getStart() / ((float) (duration));
break;
}
}
}
}
sbPosition.setDividerPos(dividerPos);
}


private void setupControlButtons() {
switch(MUID_STATIC) {
// AudioPlayerFragment_36_BuggyGUIListenerOperatorMutator
case 36139: {
butRev.setOnClickListener(null);
break;
}
default: {
butRev.setOnClickListener((android.view.View v) -> {
if (controller != null) {
int curr;
curr = controller.getPosition();
switch(MUID_STATIC) {
// AudioPlayerFragment_37_BinaryMutator
case 37139: {
controller.seekTo(curr + (de.danoeh.antennapod.storage.preferences.UserPreferences.getRewindSecs() * 1000));
break;
}
default: {
switch(MUID_STATIC) {
// AudioPlayerFragment_38_BinaryMutator
case 38139: {
controller.seekTo(curr - (de.danoeh.antennapod.storage.preferences.UserPreferences.getRewindSecs() / 1000));
break;
}
default: {
controller.seekTo(curr - (de.danoeh.antennapod.storage.preferences.UserPreferences.getRewindSecs() * 1000));
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
butRev.setOnLongClickListener((android.view.View v) -> {
de.danoeh.antennapod.dialog.SkipPreferenceDialog.showSkipPreference(getContext(), de.danoeh.antennapod.dialog.SkipPreferenceDialog.SkipDirection.SKIP_REWIND, txtvRev);
return true;
});
switch(MUID_STATIC) {
// AudioPlayerFragment_39_BuggyGUIListenerOperatorMutator
case 39139: {
butPlay.setOnClickListener(null);
break;
}
default: {
butPlay.setOnClickListener((android.view.View v) -> {
if (controller != null) {
controller.init();
controller.playPause();
}
});
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_40_BuggyGUIListenerOperatorMutator
case 40139: {
butFF.setOnClickListener(null);
break;
}
default: {
butFF.setOnClickListener((android.view.View v) -> {
if (controller != null) {
int curr;
curr = controller.getPosition();
switch(MUID_STATIC) {
// AudioPlayerFragment_41_BinaryMutator
case 41139: {
controller.seekTo(curr - (de.danoeh.antennapod.storage.preferences.UserPreferences.getFastForwardSecs() * 1000));
break;
}
default: {
switch(MUID_STATIC) {
// AudioPlayerFragment_42_BinaryMutator
case 42139: {
controller.seekTo(curr + (de.danoeh.antennapod.storage.preferences.UserPreferences.getFastForwardSecs() / 1000));
break;
}
default: {
controller.seekTo(curr + (de.danoeh.antennapod.storage.preferences.UserPreferences.getFastForwardSecs() * 1000));
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
butFF.setOnLongClickListener((android.view.View v) -> {
de.danoeh.antennapod.dialog.SkipPreferenceDialog.showSkipPreference(getContext(), de.danoeh.antennapod.dialog.SkipPreferenceDialog.SkipDirection.SKIP_FORWARD, txtvFF);
return false;
});
switch(MUID_STATIC) {
// AudioPlayerFragment_43_BuggyGUIListenerOperatorMutator
case 43139: {
butSkip.setOnClickListener(null);
break;
}
default: {
butSkip.setOnClickListener((android.view.View v) -> getActivity().sendBroadcast(de.danoeh.antennapod.core.receiver.MediaButtonReceiver.createIntent(getContext(), android.view.KeyEvent.KEYCODE_MEDIA_NEXT)));
break;
}
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onUnreadItemsUpdate(de.danoeh.antennapod.event.UnreadItemsUpdateEvent event) {
if (controller == null) {
return;
}
updatePosition(new de.danoeh.antennapod.event.playback.PlaybackPositionEvent(controller.getPosition(), controller.getDuration()));
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onPlaybackServiceChanged(de.danoeh.antennapod.event.playback.PlaybackServiceEvent event) {
if (event.action == de.danoeh.antennapod.event.playback.PlaybackServiceEvent.Action.SERVICE_SHUT_DOWN) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getBottomSheet().setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
}
}


private void setupLengthTextView() {
showTimeLeft = de.danoeh.antennapod.storage.preferences.UserPreferences.shouldShowRemainingTime();
switch(MUID_STATIC) {
// AudioPlayerFragment_44_BuggyGUIListenerOperatorMutator
case 44139: {
txtvLength.setOnClickListener(null);
break;
}
default: {
txtvLength.setOnClickListener((android.view.View v) -> {
if (controller == null) {
return;
}
showTimeLeft = !showTimeLeft;
de.danoeh.antennapod.storage.preferences.UserPreferences.setShowRemainTimeSetting(showTimeLeft);
updatePosition(new de.danoeh.antennapod.event.playback.PlaybackPositionEvent(controller.getPosition(), controller.getDuration()));
});
break;
}
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void updatePlaybackSpeedButton(de.danoeh.antennapod.event.playback.SpeedChangedEvent event) {
java.lang.String speedStr;
speedStr = new java.text.DecimalFormat("0.00").format(event.getNewSpeed());
txtvPlaybackSpeed.setText(speedStr);
butPlaybackSpeed.setSpeed(event.getNewSpeed());
}


private void loadMediaInfo(boolean includingChapters) {
if (disposable != null) {
disposable.dispose();
}
disposable = io.reactivex.Maybe.<de.danoeh.antennapod.model.playback.Playable>create((io.reactivex.MaybeEmitter<de.danoeh.antennapod.model.playback.Playable> emitter) -> {
de.danoeh.antennapod.model.playback.Playable media;
media = controller.getMedia();
if (media != null) {
if (includingChapters) {
de.danoeh.antennapod.core.util.ChapterUtils.loadChapters(media, getContext(), false);
}
emitter.onSuccess(media);
} else {
emitter.onComplete();
}
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.playback.Playable media) -> {
updateUi(media);
if ((media.getChapters() == null) && (!includingChapters)) {
loadMediaInfo(true);
}
}, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.AudioPlayerFragment.TAG, android.util.Log.getStackTraceString(error)), () -> updateUi(null));
}


private de.danoeh.antennapod.core.util.playback.PlaybackController newPlaybackController() {
return new de.danoeh.antennapod.core.util.playback.PlaybackController(getActivity()) {
@java.lang.Override
protected void updatePlayButtonShowsPlay(boolean showPlay) {
butPlay.setIsShowPlay(showPlay);
}


@java.lang.Override
public void loadMediaInfo() {
de.danoeh.antennapod.fragment.AudioPlayerFragment.this.loadMediaInfo(false);
}


@java.lang.Override
public void onPlaybackEnd() {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getBottomSheet().setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
}

};
}


private void updateUi(de.danoeh.antennapod.model.playback.Playable media) {
if ((controller == null) || (media == null)) {
return;
}
duration = controller.getDuration();
updatePosition(new de.danoeh.antennapod.event.playback.PlaybackPositionEvent(media.getPosition(), media.getDuration()));
updatePlaybackSpeedButton(new de.danoeh.antennapod.event.playback.SpeedChangedEvent(de.danoeh.antennapod.core.feed.util.PlaybackSpeedUtils.getCurrentPlaybackSpeed(media)));
setChapterDividers(media);
setupOptionsMenu(media);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
@java.lang.SuppressWarnings("unused")
public void sleepTimerUpdate(de.danoeh.antennapod.event.playback.SleepTimerUpdatedEvent event) {
if (event.isCancelled() || event.wasJustEnabled()) {
this.loadMediaInfo(false);
}
}


@java.lang.Override
public void onCreate(android.os.Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
switch(MUID_STATIC) {
// AudioPlayerFragment_45_LengthyGUICreationOperatorMutator
case 45139: {
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
setRetainInstance(true);
}


@java.lang.Override
public void onStart() {
super.onStart();
controller = newPlaybackController();
controller.init();
loadMediaInfo(false);
org.greenrobot.eventbus.EventBus.getDefault().register(this);
txtvRev.setText(java.text.NumberFormat.getInstance().format(de.danoeh.antennapod.storage.preferences.UserPreferences.getRewindSecs()));
txtvFF.setText(java.text.NumberFormat.getInstance().format(de.danoeh.antennapod.storage.preferences.UserPreferences.getFastForwardSecs()));
}


@java.lang.Override
public void onStop() {
super.onStop();
controller.release();
controller = null;
progressIndicator.setVisibility(android.view.View.GONE)// Controller released; we will not receive buffering updates
;// Controller released; we will not receive buffering updates

org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
if (disposable != null) {
disposable.dispose();
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
@java.lang.SuppressWarnings("unused")
public void bufferUpdate(de.danoeh.antennapod.event.playback.BufferUpdateEvent event) {
if (event.hasStarted()) {
progressIndicator.setVisibility(android.view.View.VISIBLE);
} else if (event.hasEnded()) {
progressIndicator.setVisibility(android.view.View.GONE);
} else if ((controller != null) && controller.isStreaming()) {
switch(MUID_STATIC) {
// AudioPlayerFragment_46_BinaryMutator
case 46139: {
sbPosition.setSecondaryProgress(((int) (event.getProgress() / sbPosition.getMax())));
break;
}
default: {
sbPosition.setSecondaryProgress(((int) (event.getProgress() * sbPosition.getMax())));
break;
}
}
} else {
sbPosition.setSecondaryProgress(0);
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void updatePosition(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
if ((((controller == null) || (txtvPosition == null)) || (txtvLength == null)) || (sbPosition == null)) {
return;
}
de.danoeh.antennapod.core.util.TimeSpeedConverter converter;
converter = new de.danoeh.antennapod.core.util.TimeSpeedConverter(controller.getCurrentPlaybackSpeedMultiplier());
int currentPosition;
currentPosition = converter.convert(event.getPosition());
int duration;
duration = converter.convert(event.getDuration());
int remainingTime;
switch(MUID_STATIC) {
// AudioPlayerFragment_47_BinaryMutator
case 47139: {
remainingTime = converter.convert(java.lang.Math.max(event.getDuration() + event.getPosition(), 0));
break;
}
default: {
remainingTime = converter.convert(java.lang.Math.max(event.getDuration() - event.getPosition(), 0));
break;
}
}
currentChapterIndex = de.danoeh.antennapod.core.util.ChapterUtils.getCurrentChapterIndex(controller.getMedia(), currentPosition);
android.util.Log.d(de.danoeh.antennapod.fragment.AudioPlayerFragment.TAG, "currentPosition " + de.danoeh.antennapod.core.util.Converter.getDurationStringLong(currentPosition));
if ((currentPosition == de.danoeh.antennapod.model.playback.Playable.INVALID_TIME) || (duration == de.danoeh.antennapod.model.playback.Playable.INVALID_TIME)) {
android.util.Log.w(de.danoeh.antennapod.fragment.AudioPlayerFragment.TAG, "Could not react to position observer update because of invalid time");
return;
}
txtvPosition.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(currentPosition));
txtvPosition.setContentDescription(getString(de.danoeh.antennapod.R.string.position, de.danoeh.antennapod.core.util.Converter.getDurationStringLocalized(getContext(), currentPosition)));
showTimeLeft = de.danoeh.antennapod.storage.preferences.UserPreferences.shouldShowRemainingTime();
if (showTimeLeft) {
txtvLength.setContentDescription(getString(de.danoeh.antennapod.R.string.remaining_time, de.danoeh.antennapod.core.util.Converter.getDurationStringLocalized(getContext(), remainingTime)));
txtvLength.setText((remainingTime > 0 ? "-" : "") + de.danoeh.antennapod.core.util.Converter.getDurationStringLong(remainingTime));
} else {
txtvLength.setContentDescription(getString(de.danoeh.antennapod.R.string.chapter_duration, de.danoeh.antennapod.core.util.Converter.getDurationStringLocalized(getContext(), duration)));
txtvLength.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(duration));
}
if (!sbPosition.isPressed()) {
float progress;
switch(MUID_STATIC) {
// AudioPlayerFragment_48_BinaryMutator
case 48139: {
progress = ((float) (event.getPosition())) * event.getDuration();
break;
}
default: {
progress = ((float) (event.getPosition())) / event.getDuration();
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_49_BinaryMutator
case 49139: {
sbPosition.setProgress(((int) (progress / sbPosition.getMax())));
break;
}
default: {
sbPosition.setProgress(((int) (progress * sbPosition.getMax())));
break;
}
}
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void favoritesChanged(de.danoeh.antennapod.event.FavoritesEvent event) {
this.loadMediaInfo(false);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void mediaPlayerError(de.danoeh.antennapod.event.PlayerErrorEvent event) {
de.danoeh.antennapod.dialog.MediaPlayerErrorDialog.show(getActivity(), event);
}


@java.lang.Override
public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
if ((controller == null) || (txtvLength == null)) {
return;
}
if (fromUser) {
float prog;
switch(MUID_STATIC) {
// AudioPlayerFragment_50_BinaryMutator
case 50139: {
prog = progress * ((float) (seekBar.getMax()));
break;
}
default: {
prog = progress / ((float) (seekBar.getMax()));
break;
}
}
de.danoeh.antennapod.core.util.TimeSpeedConverter converter;
converter = new de.danoeh.antennapod.core.util.TimeSpeedConverter(controller.getCurrentPlaybackSpeedMultiplier());
int position;
switch(MUID_STATIC) {
// AudioPlayerFragment_51_BinaryMutator
case 51139: {
position = converter.convert(((int) (prog / controller.getDuration())));
break;
}
default: {
position = converter.convert(((int) (prog * controller.getDuration())));
break;
}
}
int newChapterIndex;
newChapterIndex = de.danoeh.antennapod.core.util.ChapterUtils.getCurrentChapterIndex(controller.getMedia(), position);
if (newChapterIndex > (-1)) {
if ((!sbPosition.isPressed()) && (currentChapterIndex != newChapterIndex)) {
currentChapterIndex = newChapterIndex;
position = ((int) (controller.getMedia().getChapters().get(currentChapterIndex).getStart()));
seekedToChapterStart = true;
controller.seekTo(position);
updateUi(controller.getMedia());
sbPosition.highlightCurrentChapter();
}
txtvSeek.setText((controller.getMedia().getChapters().get(newChapterIndex).getTitle() + "\n") + de.danoeh.antennapod.core.util.Converter.getDurationStringLong(position));
} else {
txtvSeek.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(position));
}
} else if (duration != controller.getDuration()) {
updateUi(controller.getMedia());
}
}


@java.lang.Override
public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
// interrupt position Observer, restart later
cardViewSeek.setScaleX(0.8F);
cardViewSeek.setScaleY(0.8F);
cardViewSeek.animate().setInterpolator(new androidx.interpolator.view.animation.FastOutSlowInInterpolator()).alpha(1.0F).scaleX(1.0F).scaleY(1.0F).setDuration(200).start();
}


@java.lang.Override
public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
if (controller != null) {
if (seekedToChapterStart) {
seekedToChapterStart = false;
} else {
float prog;
switch(MUID_STATIC) {
// AudioPlayerFragment_52_BinaryMutator
case 52139: {
prog = seekBar.getProgress() * ((float) (seekBar.getMax()));
break;
}
default: {
prog = seekBar.getProgress() / ((float) (seekBar.getMax()));
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_53_BinaryMutator
case 53139: {
controller.seekTo(((int) (prog / controller.getDuration())));
break;
}
default: {
controller.seekTo(((int) (prog * controller.getDuration())));
break;
}
}
}
}
cardViewSeek.setScaleX(1.0F);
cardViewSeek.setScaleY(1.0F);
cardViewSeek.animate().setInterpolator(new androidx.interpolator.view.animation.FastOutSlowInInterpolator()).alpha(0.0F).scaleX(0.8F).scaleY(0.8F).setDuration(200).start();
}


public void setupOptionsMenu(de.danoeh.antennapod.model.playback.Playable media) {
if (toolbar.getMenu().size() == 0) {
toolbar.inflateMenu(de.danoeh.antennapod.R.menu.mediaplayer);
}
if (controller == null) {
return;
}
boolean isFeedMedia;
isFeedMedia = media instanceof de.danoeh.antennapod.model.feed.FeedMedia;
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.open_feed_item).setVisible(isFeedMedia);
if (isFeedMedia) {
de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onPrepareMenu(toolbar.getMenu(), ((de.danoeh.antennapod.model.feed.FeedMedia) (media)).getItem());
}
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.set_sleeptimer_item).setVisible(!controller.sleepTimerActive());
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.disable_sleeptimer_item).setVisible(controller.sleepTimerActive());
((de.danoeh.antennapod.playback.cast.CastEnabledActivity) (getActivity())).requestCastButton(toolbar.getMenu());
}


@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem item) {
if (controller == null) {
return false;
}
de.danoeh.antennapod.model.playback.Playable media;
media = controller.getMedia();
if (media == null) {
return false;
}
@androidx.annotation.Nullable
final de.danoeh.antennapod.model.feed.FeedItem feedItem;
feedItem = (media instanceof de.danoeh.antennapod.model.feed.FeedMedia) ? ((de.danoeh.antennapod.model.feed.FeedMedia) (media)).getItem() : null;
if ((feedItem != null) && de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onMenuItemClicked(this, item.getItemId(), feedItem)) {
return true;
}
final int itemId;
itemId = item.getItemId();
if ((itemId == de.danoeh.antennapod.R.id.disable_sleeptimer_item) || (itemId == de.danoeh.antennapod.R.id.set_sleeptimer_item)) {
new de.danoeh.antennapod.dialog.SleepTimerDialog().show(getChildFragmentManager(), "SleepTimerDialog");
return true;
} else if (itemId == de.danoeh.antennapod.R.id.audio_controls) {
de.danoeh.antennapod.dialog.PlaybackControlsDialog dialog;
dialog = de.danoeh.antennapod.dialog.PlaybackControlsDialog.newInstance();
dialog.show(getChildFragmentManager(), "playback_controls");
return true;
} else if (itemId == de.danoeh.antennapod.R.id.open_feed_item) {
if (feedItem != null) {
android.content.Intent intent;
switch(MUID_STATIC) {
// AudioPlayerFragment_54_RandomActionIntentDefinitionOperatorMutator
case 54139: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = de.danoeh.antennapod.activity.MainActivity.getIntentToOpenFeed(getContext(), feedItem.getFeedId());
break;
}
}
startActivity(intent);
}
return true;
}
return false;
}


public void fadePlayerToToolbar(float slideOffset) {
float playerFadeProgress;
switch(MUID_STATIC) {
// AudioPlayerFragment_55_BinaryMutator
case 55139: {
playerFadeProgress = java.lang.Math.max(0.0F, java.lang.Math.min(0.2F, slideOffset - 0.2F)) * 0.2F;
break;
}
default: {
switch(MUID_STATIC) {
// AudioPlayerFragment_56_BinaryMutator
case 56139: {
playerFadeProgress = java.lang.Math.max(0.0F, java.lang.Math.min(0.2F, slideOffset + 0.2F)) / 0.2F;
break;
}
default: {
playerFadeProgress = java.lang.Math.max(0.0F, java.lang.Math.min(0.2F, slideOffset - 0.2F)) / 0.2F;
break;
}
}
break;
}
}
android.view.View player;
switch(MUID_STATIC) {
// AudioPlayerFragment_57_InvalidViewFocusOperatorMutator
case 57139: {
/**
* Inserted by Kadabra
*/
player = getView().findViewById(de.danoeh.antennapod.R.id.playerFragment);
player.requestFocus();
break;
}
// AudioPlayerFragment_58_ViewComponentNotVisibleOperatorMutator
case 58139: {
/**
* Inserted by Kadabra
*/
player = getView().findViewById(de.danoeh.antennapod.R.id.playerFragment);
player.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
player = getView().findViewById(de.danoeh.antennapod.R.id.playerFragment);
break;
}
}
switch(MUID_STATIC) {
// AudioPlayerFragment_59_BinaryMutator
case 59139: {
player.setAlpha(1 + playerFadeProgress);
break;
}
default: {
player.setAlpha(1 - playerFadeProgress);
break;
}
}
player.setVisibility(playerFadeProgress > 0.99F ? android.view.View.INVISIBLE : android.view.View.VISIBLE);
float toolbarFadeProgress;
switch(MUID_STATIC) {
// AudioPlayerFragment_60_BinaryMutator
case 60139: {
toolbarFadeProgress = java.lang.Math.max(0.0F, java.lang.Math.min(0.2F, slideOffset - 0.6F)) * 0.2F;
break;
}
default: {
switch(MUID_STATIC) {
// AudioPlayerFragment_61_BinaryMutator
case 61139: {
toolbarFadeProgress = java.lang.Math.max(0.0F, java.lang.Math.min(0.2F, slideOffset + 0.6F)) / 0.2F;
break;
}
default: {
toolbarFadeProgress = java.lang.Math.max(0.0F, java.lang.Math.min(0.2F, slideOffset - 0.6F)) / 0.2F;
break;
}
}
break;
}
}
toolbar.setAlpha(toolbarFadeProgress);
toolbar.setVisibility(toolbarFadeProgress < 0.01F ? android.view.View.INVISIBLE : android.view.View.VISIBLE);
}


private static class AudioPlayerPagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
private static final java.lang.String TAG = "AudioPlayerPagerAdapter";

public AudioPlayerPagerAdapter(@androidx.annotation.NonNull
androidx.fragment.app.Fragment fragment) {
super(fragment);
}


@androidx.annotation.NonNull
@java.lang.Override
public androidx.fragment.app.Fragment createFragment(int position) {
android.util.Log.d(de.danoeh.antennapod.fragment.AudioPlayerFragment.AudioPlayerPagerAdapter.TAG, ("getItem(" + position) + ")");
switch (position) {
case de.danoeh.antennapod.fragment.AudioPlayerFragment.POS_COVER :
return new de.danoeh.antennapod.fragment.CoverFragment();
default :
case de.danoeh.antennapod.fragment.AudioPlayerFragment.POS_DESCRIPTION :
return new de.danoeh.antennapod.fragment.ItemDescriptionFragment();
}
}


@java.lang.Override
public int getItemCount() {
return de.danoeh.antennapod.fragment.AudioPlayerFragment.NUM_CONTENT_FRAGMENTS;
}

}

public void scrollToPage(int page, boolean smoothScroll) {
if (pager == null) {
return;
}
pager.setCurrentItem(page, smoothScroll);
androidx.fragment.app.Fragment visibleChild;
visibleChild = getChildFragmentManager().findFragmentByTag("f" + de.danoeh.antennapod.fragment.AudioPlayerFragment.POS_DESCRIPTION);
if (visibleChild instanceof de.danoeh.antennapod.fragment.ItemDescriptionFragment) {
((de.danoeh.antennapod.fragment.ItemDescriptionFragment) (visibleChild)).scrollToTop();
}
}


public void scrollToPage(int page) {
scrollToPage(page, false);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
