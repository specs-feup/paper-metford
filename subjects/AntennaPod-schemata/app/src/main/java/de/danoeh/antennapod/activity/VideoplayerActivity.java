package de.danoeh.antennapod.activity;
import de.danoeh.antennapod.core.util.ShareUtils;
import de.danoeh.antennapod.fragment.ChaptersFragment;
import de.danoeh.antennapod.core.util.gui.PictureInPictureUtil;
import de.danoeh.antennapod.event.playback.BufferUpdateEvent;
import de.danoeh.antennapod.core.storage.DBWriter;
import org.greenrobot.eventbus.Subscribe;
import android.view.animation.ScaleAnimation;
import android.os.Build;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.Converter;
import android.graphics.PixelFormat;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.event.playback.PlaybackServiceEvent;
import android.view.animation.AnimationSet;
import com.bumptech.glide.Glide;
import android.media.AudioManager;
import de.danoeh.antennapod.playback.cast.CastEnabledActivity;
import de.danoeh.antennapod.event.PlayerErrorEvent;
import de.danoeh.antennapod.dialog.MediaPlayerErrorDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import de.danoeh.antennapod.dialog.PlaybackControlsDialog;
import android.os.Handler;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.widget.EditText;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.util.IntentUtils;
import de.danoeh.antennapod.model.feed.FeedMedia;
import android.graphics.drawable.ColorDrawable;
import de.danoeh.antennapod.core.storage.DBReader;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.event.MessageEvent;
import de.danoeh.antennapod.event.playback.SleepTimerUpdatedEvent;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.databinding.VideoplayerActivityBinding;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import org.greenrobot.eventbus.EventBus;
import de.danoeh.antennapod.core.service.playback.PlaybackService;
import android.view.WindowManager;
import de.danoeh.antennapod.dialog.SkipPreferenceDialog;
import android.view.animation.AlphaAnimation;
import android.view.SurfaceHolder;
import de.danoeh.antennapod.R;
import android.view.animation.AnimationUtils;
import android.view.KeyEvent;
import org.apache.commons.lang3.StringUtils;
import de.danoeh.antennapod.dialog.SleepTimerDialog;
import de.danoeh.antennapod.core.util.playback.PlaybackController;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import de.danoeh.antennapod.dialog.VariableSpeedDialog;
import de.danoeh.antennapod.ui.appstartintent.MainActivityStarter;
import android.util.Pair;
import android.view.Gravity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.view.Window;
import io.reactivex.disposables.Disposable;
import android.widget.SeekBar;
import android.os.Bundle;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import android.content.Intent;
import android.view.MenuItem;
import de.danoeh.antennapod.core.util.TimeSpeedConverter;
import de.danoeh.antennapod.dialog.ShareDialog;
import android.view.MotionEvent;
import android.view.View;
import de.danoeh.antennapod.playback.base.PlayerStatus;
import android.os.Looper;
import de.danoeh.antennapod.model.playback.Playable;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Activity for playing video files.
 */
public class VideoplayerActivity extends de.danoeh.antennapod.playback.cast.CastEnabledActivity implements android.widget.SeekBar.OnSeekBarChangeListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "VideoplayerActivity";

    /**
     * True if video controls are currently visible.
     */
    private boolean videoControlsShowing = true;

    private boolean videoSurfaceCreated = false;

    private boolean destroyingDueToReload = false;

    private long lastScreenTap = 0;

    private final android.os.Handler videoControlsHider = new android.os.Handler(android.os.Looper.getMainLooper());

    private de.danoeh.antennapod.databinding.VideoplayerActivityBinding viewBinding;

    private de.danoeh.antennapod.core.util.playback.PlaybackController controller;

    private boolean showTimeLeft = false;

    private boolean isFavorite = false;

    private boolean switchToAudioOnly = false;

    private io.reactivex.disposables.Disposable disposable;

    private float prog;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        // has to be called before setting layout content
        supportRequestWindowFeature(android.view.Window.FEATURE_ACTION_BAR_OVERLAY);
        setTheme(de.danoeh.antennapod.R.style.Theme_AntennaPod_VideoPlayer);
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // VideoplayerActivity_0_LengthyGUICreationOperatorMutator
            case 149: {
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
    android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, "onCreate()");
    getWindow().setFormat(android.graphics.PixelFormat.TRANSPARENT);
    viewBinding = de.danoeh.antennapod.databinding.VideoplayerActivityBinding.inflate(android.view.LayoutInflater.from(this));
    setContentView(viewBinding.getRoot());
    setupView();
    getSupportActionBar().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0x80000000));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
}


@java.lang.Override
protected void onResume() {
    super.onResume();
    switchToAudioOnly = false;
    if (de.danoeh.antennapod.core.service.playback.PlaybackService.isCasting()) {
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // VideoplayerActivity_1_RandomActionIntentDefinitionOperatorMutator
            case 1149: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = de.danoeh.antennapod.core.service.playback.PlaybackService.getPlayerActivityIntent(this);
            break;
        }
    }
    if (!intent.getComponent().getClassName().equals(de.danoeh.antennapod.activity.VideoplayerActivity.class.getName())) {
        destroyingDueToReload = true;
        finish();
        startActivity(intent);
    }
}
}


@java.lang.Override
protected void onStop() {
if (controller != null) {
    controller.release();
    controller = null// prevent leak
    ;// prevent leak

}
if (disposable != null) {
    disposable.dispose();
}
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
super.onStop();
if (!de.danoeh.antennapod.core.util.gui.PictureInPictureUtil.isInPictureInPictureMode(this)) {
    videoControlsHider.removeCallbacks(hideVideoControls);
}
// Controller released; we will not receive buffering updates
viewBinding.progressBar.setVisibility(android.view.View.GONE);
}


@java.lang.Override
public void onUserLeaveHint() {
if (!de.danoeh.antennapod.core.util.gui.PictureInPictureUtil.isInPictureInPictureMode(this)) {
    compatEnterPictureInPicture();
}
}


@java.lang.Override
protected void onStart() {
super.onStart();
controller = newPlaybackController();
controller.init();
loadMediaInfo();
onPositionObserverUpdate();
org.greenrobot.eventbus.EventBus.getDefault().register(this);
}


@java.lang.Override
protected void onPause() {
if (!de.danoeh.antennapod.core.util.gui.PictureInPictureUtil.isInPictureInPictureMode(this)) {
    if ((controller != null) && (controller.getStatus() == de.danoeh.antennapod.playback.base.PlayerStatus.PLAYING)) {
        controller.pause();
    }
}
super.onPause();
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


private de.danoeh.antennapod.core.util.playback.PlaybackController newPlaybackController() {
return new de.danoeh.antennapod.core.util.playback.PlaybackController(this) {
    @java.lang.Override
    protected void updatePlayButtonShowsPlay(boolean showPlay) {
        viewBinding.playButton.setIsShowPlay(showPlay);
        if (showPlay) {
            getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            setupVideoAspectRatio();
            if (videoSurfaceCreated && (controller != null)) {
                android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, "Videosurface already created, setting videosurface now");
                controller.setVideoSurface(viewBinding.videoView.getHolder());
            }
        }
    }


    @java.lang.Override
    public void loadMediaInfo() {
        de.danoeh.antennapod.activity.VideoplayerActivity.this.loadMediaInfo();
    }


    @java.lang.Override
    public void onPlaybackEnd() {
        finish();
    }

};
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
@java.lang.SuppressWarnings("unused")
public void bufferUpdate(de.danoeh.antennapod.event.playback.BufferUpdateEvent event) {
if (event.hasStarted()) {
    viewBinding.progressBar.setVisibility(android.view.View.VISIBLE);
} else if (event.hasEnded()) {
    viewBinding.progressBar.setVisibility(android.view.View.INVISIBLE);
} else {
    switch(MUID_STATIC) {
        // VideoplayerActivity_2_BinaryMutator
        case 2149: {
            viewBinding.sbPosition.setSecondaryProgress(((int) (event.getProgress() / viewBinding.sbPosition.getMax())));
            break;
        }
        default: {
        viewBinding.sbPosition.setSecondaryProgress(((int) (event.getProgress() * viewBinding.sbPosition.getMax())));
        break;
    }
}
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
@java.lang.SuppressWarnings("unused")
public void sleepTimerUpdate(de.danoeh.antennapod.event.playback.SleepTimerUpdatedEvent event) {
if (event.isCancelled() || event.wasJustEnabled()) {
supportInvalidateOptionsMenu();
}
}


protected void loadMediaInfo() {
android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, "loadMediaInfo()");
if ((controller == null) || (controller.getMedia() == null)) {
return;
}
if ((controller.getStatus() == de.danoeh.antennapod.playback.base.PlayerStatus.PLAYING) && (!controller.isPlayingVideoLocally())) {
android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, "Closing, no longer video");
destroyingDueToReload = true;
finish();
new de.danoeh.antennapod.ui.appstartintent.MainActivityStarter(this).withOpenPlayer().start();
return;
}
showTimeLeft = de.danoeh.antennapod.storage.preferences.UserPreferences.shouldShowRemainingTime();
onPositionObserverUpdate();
checkFavorite();
de.danoeh.antennapod.model.playback.Playable media;
media = controller.getMedia();
if (media != null) {
getSupportActionBar().setSubtitle(media.getEpisodeTitle());
getSupportActionBar().setTitle(media.getFeedTitle());
}
}


protected void setupView() {
showTimeLeft = de.danoeh.antennapod.storage.preferences.UserPreferences.shouldShowRemainingTime();
android.util.Log.d("timeleft", showTimeLeft ? "true" : "false");
switch(MUID_STATIC) {
// VideoplayerActivity_3_BuggyGUIListenerOperatorMutator
case 3149: {
    viewBinding.durationLabel.setOnClickListener(null);
    break;
}
default: {
viewBinding.durationLabel.setOnClickListener((android.view.View v) -> {
    showTimeLeft = !showTimeLeft;
    de.danoeh.antennapod.model.playback.Playable media;
    media = controller.getMedia();
    if (media == null) {
        return;
    }
    de.danoeh.antennapod.core.util.TimeSpeedConverter converter;
    converter = new de.danoeh.antennapod.core.util.TimeSpeedConverter(controller.getCurrentPlaybackSpeedMultiplier());
    java.lang.String length;
    if (showTimeLeft) {
        int remainingTime;
        switch(MUID_STATIC) {
            // VideoplayerActivity_4_BinaryMutator
            case 4149: {
                remainingTime = converter.convert(media.getDuration() + media.getPosition());
                break;
            }
            default: {
            remainingTime = converter.convert(media.getDuration() - media.getPosition());
            break;
        }
    }
    length = "-" + de.danoeh.antennapod.core.util.Converter.getDurationStringLong(remainingTime);
} else {
    int duration;
    duration = converter.convert(media.getDuration());
    length = de.danoeh.antennapod.core.util.Converter.getDurationStringLong(duration);
}
viewBinding.durationLabel.setText(length);
de.danoeh.antennapod.storage.preferences.UserPreferences.setShowRemainTimeSetting(showTimeLeft);
android.util.Log.d("timeleft on click", showTimeLeft ? "true" : "false");
});
break;
}
}
viewBinding.sbPosition.setOnSeekBarChangeListener(this);
switch(MUID_STATIC) {
// VideoplayerActivity_5_BuggyGUIListenerOperatorMutator
case 5149: {
viewBinding.rewindButton.setOnClickListener(null);
break;
}
default: {
viewBinding.rewindButton.setOnClickListener((android.view.View v) -> onRewind());
break;
}
}
viewBinding.rewindButton.setOnLongClickListener((android.view.View v) -> {
de.danoeh.antennapod.dialog.SkipPreferenceDialog.showSkipPreference(this, de.danoeh.antennapod.dialog.SkipPreferenceDialog.SkipDirection.SKIP_REWIND, null);
return true;
});
viewBinding.playButton.setIsVideoScreen(true);
switch(MUID_STATIC) {
// VideoplayerActivity_6_BuggyGUIListenerOperatorMutator
case 6149: {
viewBinding.playButton.setOnClickListener(null);
break;
}
default: {
viewBinding.playButton.setOnClickListener((android.view.View v) -> onPlayPause());
break;
}
}
switch(MUID_STATIC) {
// VideoplayerActivity_7_BuggyGUIListenerOperatorMutator
case 7149: {
viewBinding.fastForwardButton.setOnClickListener(null);
break;
}
default: {
viewBinding.fastForwardButton.setOnClickListener((android.view.View v) -> onFastForward());
break;
}
}
viewBinding.fastForwardButton.setOnLongClickListener((android.view.View v) -> {
de.danoeh.antennapod.dialog.SkipPreferenceDialog.showSkipPreference(this, de.danoeh.antennapod.dialog.SkipPreferenceDialog.SkipDirection.SKIP_FORWARD, null);
return false;
});
// To suppress touches directly below the slider
viewBinding.bottomControlsContainer.setOnTouchListener((android.view.View view,android.view.MotionEvent motionEvent) -> true);
viewBinding.bottomControlsContainer.setFitsSystemWindows(true);
viewBinding.videoView.getHolder().addCallback(surfaceHolderCallback);
viewBinding.videoView.setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
setupVideoControlsToggler();
getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
viewBinding.videoPlayerContainer.setOnTouchListener(onVideoviewTouched);
viewBinding.videoPlayerContainer.getViewTreeObserver().addOnGlobalLayoutListener(() -> viewBinding.videoView.setAvailableSize(viewBinding.videoPlayerContainer.getWidth(), viewBinding.videoPlayerContainer.getHeight()));
}


private final java.lang.Runnable hideVideoControls = () -> {
if (videoControlsShowing) {
android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, "Hiding video controls");
getSupportActionBar().hide();
hideVideoControls(true);
videoControlsShowing = false;
}
};

private final android.view.View.OnTouchListener onVideoviewTouched = (android.view.View v,android.view.MotionEvent event) -> {
if (event.getAction() != android.view.MotionEvent.ACTION_DOWN) {
return false;
}
if (de.danoeh.antennapod.core.util.gui.PictureInPictureUtil.isInPictureInPictureMode(this)) {
return true;
}
videoControlsHider.removeCallbacks(hideVideoControls);
switch(MUID_STATIC) {
// VideoplayerActivity_8_BinaryMutator
case 8149: {
if ((java.lang.System.currentTimeMillis() + lastScreenTap) < 300) {
if (event.getX() > (v.getMeasuredWidth() / 2.0F)) {
onFastForward();
showSkipAnimation(true);
} else {
onRewind();
showSkipAnimation(false);
}
if (videoControlsShowing) {
getSupportActionBar().hide();
hideVideoControls(false);
videoControlsShowing = false;
}
return true;
}
break;
}
default: {
if ((java.lang.System.currentTimeMillis() - lastScreenTap) < 300) {
switch(MUID_STATIC) {
// VideoplayerActivity_9_BinaryMutator
case 9149: {
if (event.getX() > (v.getMeasuredWidth() * 2.0F)) {
onFastForward();
showSkipAnimation(true);
} else {
onRewind();
showSkipAnimation(false);
}
break;
}
default: {
if (event.getX() > (v.getMeasuredWidth() / 2.0F)) {
onFastForward();
showSkipAnimation(true);
} else {
onRewind();
showSkipAnimation(false);
}
break;
}
}
if (videoControlsShowing) {
getSupportActionBar().hide();
hideVideoControls(false);
videoControlsShowing = false;
}
return true;
}
break;
}
}
toggleVideoControlsVisibility();
if (videoControlsShowing) {
setupVideoControlsToggler();
}
lastScreenTap = java.lang.System.currentTimeMillis();
return true;
};

private void showSkipAnimation(boolean isForward) {
android.view.animation.AnimationSet skipAnimation;
skipAnimation = new android.view.animation.AnimationSet(true);
skipAnimation.addAnimation(new android.view.animation.ScaleAnimation(1.0F, 2.0F, 1.0F, 2.0F, android.view.animation.Animation.RELATIVE_TO_SELF, 0.5F, android.view.animation.Animation.RELATIVE_TO_SELF, 0.5F));
skipAnimation.addAnimation(new android.view.animation.AlphaAnimation(1.0F, 0.0F));
skipAnimation.setFillAfter(false);
skipAnimation.setDuration(800);
android.widget.FrameLayout.LayoutParams params;
params = ((android.widget.FrameLayout.LayoutParams) (viewBinding.skipAnimationImage.getLayoutParams()));
if (isForward) {
viewBinding.skipAnimationImage.setImageResource(de.danoeh.antennapod.R.drawable.ic_fast_forward_video_white);
params.gravity = android.view.Gravity.RIGHT | android.view.Gravity.CENTER_VERTICAL;
} else {
viewBinding.skipAnimationImage.setImageResource(de.danoeh.antennapod.R.drawable.ic_fast_rewind_video_white);
params.gravity = android.view.Gravity.LEFT | android.view.Gravity.CENTER_VERTICAL;
}
viewBinding.skipAnimationImage.setVisibility(android.view.View.VISIBLE);
viewBinding.skipAnimationImage.setLayoutParams(params);
viewBinding.skipAnimationImage.startAnimation(skipAnimation);
skipAnimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
@java.lang.Override
public void onAnimationStart(android.view.animation.Animation animation) {
}


@java.lang.Override
public void onAnimationEnd(android.view.animation.Animation animation) {
viewBinding.skipAnimationImage.setVisibility(android.view.View.GONE);
}


@java.lang.Override
public void onAnimationRepeat(android.view.animation.Animation animation) {
}

});
}


private void setupVideoControlsToggler() {
videoControlsHider.removeCallbacks(hideVideoControls);
videoControlsHider.postDelayed(hideVideoControls, 2500);
}


private void setupVideoAspectRatio() {
if (videoSurfaceCreated && (controller != null)) {
android.util.Pair<java.lang.Integer, java.lang.Integer> videoSize;
videoSize = controller.getVideoSize();
if (((videoSize != null) && (videoSize.first > 0)) && (videoSize.second > 0)) {
android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, (("Width,height of video: " + videoSize.first) + ", ") + videoSize.second);
viewBinding.videoView.setVideoSize(videoSize.first, videoSize.second);
} else {
android.util.Log.e(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, "Could not determine video size");
}
}
}


private void toggleVideoControlsVisibility() {
if (videoControlsShowing) {
getSupportActionBar().hide();
hideVideoControls(true);
} else {
getSupportActionBar().show();
showVideoControls();
}
videoControlsShowing = !videoControlsShowing;
}


void onRewind() {
if (controller == null) {
return;
}
int curr;
curr = controller.getPosition();
switch(MUID_STATIC) {
// VideoplayerActivity_10_BinaryMutator
case 10149: {
controller.seekTo(curr + (de.danoeh.antennapod.storage.preferences.UserPreferences.getRewindSecs() * 1000));
break;
}
default: {
switch(MUID_STATIC) {
// VideoplayerActivity_11_BinaryMutator
case 11149: {
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
setupVideoControlsToggler();
}


void onPlayPause() {
if (controller == null) {
return;
}
controller.playPause();
setupVideoControlsToggler();
}


void onFastForward() {
if (controller == null) {
return;
}
int curr;
curr = controller.getPosition();
switch(MUID_STATIC) {
// VideoplayerActivity_12_BinaryMutator
case 12149: {
controller.seekTo(curr - (de.danoeh.antennapod.storage.preferences.UserPreferences.getFastForwardSecs() * 1000));
break;
}
default: {
switch(MUID_STATIC) {
// VideoplayerActivity_13_BinaryMutator
case 13149: {
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
setupVideoControlsToggler();
}


private final android.view.SurfaceHolder.Callback surfaceHolderCallback = new android.view.SurfaceHolder.Callback() {
@java.lang.Override
public void surfaceChanged(android.view.SurfaceHolder holder, int format, int width, int height) {
holder.setFixedSize(width, height);
}


@java.lang.Override
public void surfaceCreated(android.view.SurfaceHolder holder) {
android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, "Videoview holder created");
videoSurfaceCreated = true;
if ((controller != null) && (controller.getStatus() == de.danoeh.antennapod.playback.base.PlayerStatus.PLAYING)) {
controller.setVideoSurface(holder);
}
setupVideoAspectRatio();
}


@java.lang.Override
public void surfaceDestroyed(android.view.SurfaceHolder holder) {
android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, "Videosurface was destroyed");
videoSurfaceCreated = false;
if (((controller != null) && (!destroyingDueToReload)) && (!switchToAudioOnly)) {
controller.notifyVideoSurfaceAbandoned();
}
}

};

private void showVideoControls() {
viewBinding.bottomControlsContainer.setVisibility(android.view.View.VISIBLE);
viewBinding.controlsContainer.setVisibility(android.view.View.VISIBLE);
final android.view.animation.Animation animation;
animation = android.view.animation.AnimationUtils.loadAnimation(this, de.danoeh.antennapod.R.anim.fade_in);
if (animation != null) {
viewBinding.bottomControlsContainer.startAnimation(animation);
viewBinding.controlsContainer.startAnimation(animation);
}
viewBinding.videoView.setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_VISIBLE);
}


private void hideVideoControls(boolean showAnimation) {
if (showAnimation) {
final android.view.animation.Animation animation;
animation = android.view.animation.AnimationUtils.loadAnimation(this, de.danoeh.antennapod.R.anim.fade_out);
if (animation != null) {
viewBinding.bottomControlsContainer.startAnimation(animation);
viewBinding.controlsContainer.startAnimation(animation);
}
}
getWindow().getDecorView().setSystemUiVisibility(((android.view.View.SYSTEM_UI_FLAG_LOW_PROFILE | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN) | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
viewBinding.bottomControlsContainer.setFitsSystemWindows(true);
viewBinding.bottomControlsContainer.setVisibility(android.view.View.GONE);
viewBinding.controlsContainer.setVisibility(android.view.View.GONE);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
onPositionObserverUpdate();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onPlaybackServiceChanged(de.danoeh.antennapod.event.playback.PlaybackServiceEvent event) {
if (event.action == de.danoeh.antennapod.event.playback.PlaybackServiceEvent.Action.SERVICE_SHUT_DOWN) {
finish();
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onMediaPlayerError(de.danoeh.antennapod.event.PlayerErrorEvent event) {
de.danoeh.antennapod.dialog.MediaPlayerErrorDialog.show(this, event);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.MessageEvent event) {
android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, ("onEvent(" + event) + ")");
final com.google.android.material.dialog.MaterialAlertDialogBuilder errorDialog;
errorDialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(this);
errorDialog.setMessage(event.message);
if (event.action != null) {
switch(MUID_STATIC) {
// VideoplayerActivity_14_BuggyGUIListenerOperatorMutator
case 14149: {
errorDialog.setPositiveButton(event.actionText, null);
break;
}
default: {
errorDialog.setPositiveButton(event.actionText, (android.content.DialogInterface dialog,int which) -> event.action.accept(this));
break;
}
}
}
errorDialog.show();
}


@java.lang.Override
public boolean onCreateOptionsMenu(android.view.Menu menu) {
super.onCreateOptionsMenu(menu);
requestCastButton(menu);
android.view.MenuInflater inflater;
inflater = getMenuInflater();
inflater.inflate(de.danoeh.antennapod.R.menu.mediaplayer, menu);
return true;
}


@java.lang.Override
public boolean onPrepareOptionsMenu(android.view.Menu menu) {
super.onPrepareOptionsMenu(menu);
if (controller == null) {
return false;
}
de.danoeh.antennapod.model.playback.Playable media;
media = controller.getMedia();
boolean isFeedMedia;
isFeedMedia = media instanceof de.danoeh.antennapod.model.feed.FeedMedia;
menu.findItem(de.danoeh.antennapod.R.id.open_feed_item).setVisible(isFeedMedia)// FeedMedia implies it belongs to a Feed
;// FeedMedia implies it belongs to a Feed

boolean hasWebsiteLink;
hasWebsiteLink = de.danoeh.antennapod.activity.VideoplayerActivity.getWebsiteLinkWithFallback(media) != null;
menu.findItem(de.danoeh.antennapod.R.id.visit_website_item).setVisible(hasWebsiteLink);
boolean isItemAndHasLink;
isItemAndHasLink = isFeedMedia && de.danoeh.antennapod.core.util.ShareUtils.hasLinkToShare(((de.danoeh.antennapod.model.feed.FeedMedia) (media)).getItem());
boolean isItemHasDownloadLink;
isItemHasDownloadLink = isFeedMedia && (((de.danoeh.antennapod.model.feed.FeedMedia) (media)).getDownload_url() != null);
menu.findItem(de.danoeh.antennapod.R.id.share_item).setVisible((hasWebsiteLink || isItemAndHasLink) || isItemHasDownloadLink);
menu.findItem(de.danoeh.antennapod.R.id.add_to_favorites_item).setVisible(false);
menu.findItem(de.danoeh.antennapod.R.id.remove_from_favorites_item).setVisible(false);
if (isFeedMedia) {
menu.findItem(de.danoeh.antennapod.R.id.add_to_favorites_item).setVisible(!isFavorite);
menu.findItem(de.danoeh.antennapod.R.id.remove_from_favorites_item).setVisible(isFavorite);
}
menu.findItem(de.danoeh.antennapod.R.id.set_sleeptimer_item).setVisible(!controller.sleepTimerActive());
menu.findItem(de.danoeh.antennapod.R.id.disable_sleeptimer_item).setVisible(controller.sleepTimerActive());
menu.findItem(de.danoeh.antennapod.R.id.player_switch_to_audio_only).setVisible(true);
menu.findItem(de.danoeh.antennapod.R.id.audio_controls).setIcon(de.danoeh.antennapod.R.drawable.ic_sliders);
menu.findItem(de.danoeh.antennapod.R.id.playback_speed).setVisible(true);
menu.findItem(de.danoeh.antennapod.R.id.player_show_chapters).setVisible(true);
return true;
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
if (item.getItemId() == de.danoeh.antennapod.R.id.player_switch_to_audio_only) {
switchToAudioOnly = true;
finish();
return true;
} else if (item.getItemId() == android.R.id.home) {
android.content.Intent intent;
switch(MUID_STATIC) {
// VideoplayerActivity_15_NullIntentOperatorMutator
case 15149: {
intent = null;
break;
}
// VideoplayerActivity_16_InvalidKeyIntentOperatorMutator
case 16149: {
intent = new android.content.Intent((VideoplayerActivity) null, de.danoeh.antennapod.activity.MainActivity.class);
break;
}
// VideoplayerActivity_17_RandomActionIntentDefinitionOperatorMutator
case 17149: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(this, de.danoeh.antennapod.activity.MainActivity.class);
break;
}
}
switch(MUID_STATIC) {
// VideoplayerActivity_18_RandomActionIntentDefinitionOperatorMutator
case 18149: {
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
intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
break;
}
}
startActivity(intent);
finish();
return true;
} else if (item.getItemId() == de.danoeh.antennapod.R.id.player_show_chapters) {
new de.danoeh.antennapod.fragment.ChaptersFragment().show(getSupportFragmentManager(), de.danoeh.antennapod.fragment.ChaptersFragment.TAG);
return true;
}
if (controller == null) {
return false;
}
de.danoeh.antennapod.model.playback.Playable media;
media = controller.getMedia();
if (media == null) {
return false;
}
@androidx.annotation.Nullable
final de.danoeh.antennapod.model.feed.FeedItem feedItem// some options option requires FeedItem
;// some options option requires FeedItem

feedItem = de.danoeh.antennapod.activity.VideoplayerActivity.getFeedItem(media);
if ((item.getItemId() == de.danoeh.antennapod.R.id.add_to_favorites_item) && (feedItem != null)) {
de.danoeh.antennapod.core.storage.DBWriter.addFavoriteItem(feedItem);
isFavorite = true;
invalidateOptionsMenu();
} else if ((item.getItemId() == de.danoeh.antennapod.R.id.remove_from_favorites_item) && (feedItem != null)) {
de.danoeh.antennapod.core.storage.DBWriter.removeFavoriteItem(feedItem);
isFavorite = false;
invalidateOptionsMenu();
} else if ((item.getItemId() == de.danoeh.antennapod.R.id.disable_sleeptimer_item) || (item.getItemId() == de.danoeh.antennapod.R.id.set_sleeptimer_item)) {
new de.danoeh.antennapod.dialog.SleepTimerDialog().show(getSupportFragmentManager(), "SleepTimerDialog");
} else if (item.getItemId() == de.danoeh.antennapod.R.id.audio_controls) {
de.danoeh.antennapod.dialog.PlaybackControlsDialog dialog;
dialog = de.danoeh.antennapod.dialog.PlaybackControlsDialog.newInstance();
dialog.show(getSupportFragmentManager(), "playback_controls");
} else if ((item.getItemId() == de.danoeh.antennapod.R.id.open_feed_item) && (feedItem != null)) {
android.content.Intent intent;
switch(MUID_STATIC) {
// VideoplayerActivity_19_RandomActionIntentDefinitionOperatorMutator
case 19149: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = de.danoeh.antennapod.activity.MainActivity.getIntentToOpenFeed(this, feedItem.getFeedId());
break;
}
}
startActivity(intent);
} else if (item.getItemId() == de.danoeh.antennapod.R.id.visit_website_item) {
de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(this, de.danoeh.antennapod.activity.VideoplayerActivity.getWebsiteLinkWithFallback(media));
} else if ((item.getItemId() == de.danoeh.antennapod.R.id.share_item) && (feedItem != null)) {
de.danoeh.antennapod.dialog.ShareDialog shareDialog;
shareDialog = de.danoeh.antennapod.dialog.ShareDialog.newInstance(feedItem);
shareDialog.show(getSupportFragmentManager(), "ShareEpisodeDialog");
} else if (item.getItemId() == de.danoeh.antennapod.R.id.playback_speed) {
new de.danoeh.antennapod.dialog.VariableSpeedDialog().show(getSupportFragmentManager(), null);
} else {
return false;
}
return true;
}


private static java.lang.String getWebsiteLinkWithFallback(de.danoeh.antennapod.model.playback.Playable media) {
if (media == null) {
return null;
} else if (org.apache.commons.lang3.StringUtils.isNotBlank(media.getWebsiteLink())) {
return media.getWebsiteLink();
} else if (media instanceof de.danoeh.antennapod.model.feed.FeedMedia) {
return de.danoeh.antennapod.core.util.FeedItemUtil.getLinkWithFallback(((de.danoeh.antennapod.model.feed.FeedMedia) (media)).getItem());
}
return null;
}


void onPositionObserverUpdate() {
if (controller == null) {
return;
}
de.danoeh.antennapod.core.util.TimeSpeedConverter converter;
converter = new de.danoeh.antennapod.core.util.TimeSpeedConverter(controller.getCurrentPlaybackSpeedMultiplier());
int currentPosition;
currentPosition = converter.convert(controller.getPosition());
int duration;
duration = converter.convert(controller.getDuration());
int remainingTime;
switch(MUID_STATIC) {
// VideoplayerActivity_20_BinaryMutator
case 20149: {
remainingTime = converter.convert(controller.getDuration() + controller.getPosition());
break;
}
default: {
remainingTime = converter.convert(controller.getDuration() - controller.getPosition());
break;
}
}
android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, "currentPosition " + de.danoeh.antennapod.core.util.Converter.getDurationStringLong(currentPosition));
if ((currentPosition == de.danoeh.antennapod.model.playback.Playable.INVALID_TIME) || (duration == de.danoeh.antennapod.model.playback.Playable.INVALID_TIME)) {
android.util.Log.w(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, "Could not react to position observer update because of invalid time");
return;
}
viewBinding.positionLabel.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(currentPosition));
if (showTimeLeft) {
viewBinding.durationLabel.setText("-" + de.danoeh.antennapod.core.util.Converter.getDurationStringLong(remainingTime));
} else {
viewBinding.durationLabel.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(duration));
}
updateProgressbarPosition(currentPosition, duration);
}


private void updateProgressbarPosition(int position, int duration) {
android.util.Log.d(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, ((("updateProgressbarPosition(" + position) + ", ") + duration) + ")");
float progress;
switch(MUID_STATIC) {
// VideoplayerActivity_21_BinaryMutator
case 21149: {
progress = ((float) (position)) * duration;
break;
}
default: {
progress = ((float) (position)) / duration;
break;
}
}
switch(MUID_STATIC) {
// VideoplayerActivity_22_BinaryMutator
case 22149: {
viewBinding.sbPosition.setProgress(((int) (progress / viewBinding.sbPosition.getMax())));
break;
}
default: {
viewBinding.sbPosition.setProgress(((int) (progress * viewBinding.sbPosition.getMax())));
break;
}
}
}


@java.lang.Override
public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
if (controller == null) {
return;
}
if (fromUser) {
switch(MUID_STATIC) {
// VideoplayerActivity_23_BinaryMutator
case 23149: {
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
// VideoplayerActivity_24_BinaryMutator
case 24149: {
position = converter.convert(((int) (prog / controller.getDuration())));
break;
}
default: {
position = converter.convert(((int) (prog * controller.getDuration())));
break;
}
}
viewBinding.seekPositionLabel.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(position));
}
}


@java.lang.Override
public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
viewBinding.seekCardView.setScaleX(0.8F);
viewBinding.seekCardView.setScaleY(0.8F);
viewBinding.seekCardView.animate().setInterpolator(new androidx.interpolator.view.animation.FastOutSlowInInterpolator()).alpha(1.0F).scaleX(1.0F).scaleY(1.0F).setDuration(200).start();
videoControlsHider.removeCallbacks(hideVideoControls);
}


@java.lang.Override
public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
if (controller != null) {
switch(MUID_STATIC) {
// VideoplayerActivity_25_BinaryMutator
case 25149: {
controller.seekTo(((int) (prog / controller.getDuration())));
break;
}
default: {
controller.seekTo(((int) (prog * controller.getDuration())));
break;
}
}
}
viewBinding.seekCardView.setScaleX(1.0F);
viewBinding.seekCardView.setScaleY(1.0F);
viewBinding.seekCardView.animate().setInterpolator(new androidx.interpolator.view.animation.FastOutSlowInInterpolator()).alpha(0.0F).scaleX(0.8F).scaleY(0.8F).setDuration(200).start();
setupVideoControlsToggler();
}


private void checkFavorite() {
de.danoeh.antennapod.model.feed.FeedItem feedItem;
feedItem = de.danoeh.antennapod.activity.VideoplayerActivity.getFeedItem(controller.getMedia());
if (feedItem == null) {
return;
}
if (disposable != null) {
disposable.dispose();
}
disposable = io.reactivex.Observable.fromCallable(() -> de.danoeh.antennapod.core.storage.DBReader.getFeedItem(feedItem.getId())).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.feed.FeedItem item) -> {
boolean isFav;
isFav = item.isTagged(de.danoeh.antennapod.model.feed.FeedItem.TAG_FAVORITE);
if (isFavorite != isFav) {
isFavorite = isFav;
invalidateOptionsMenu();
}
}, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.activity.VideoplayerActivity.TAG, android.util.Log.getStackTraceString(error)));
}


@androidx.annotation.Nullable
private static de.danoeh.antennapod.model.feed.FeedItem getFeedItem(@androidx.annotation.Nullable
de.danoeh.antennapod.model.playback.Playable playable) {
if (playable instanceof de.danoeh.antennapod.model.feed.FeedMedia) {
return ((de.danoeh.antennapod.model.feed.FeedMedia) (playable)).getItem();
} else {
return null;
}
}


private void compatEnterPictureInPicture() {
if (de.danoeh.antennapod.core.util.gui.PictureInPictureUtil.supportsPictureInPicture(this) && (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)) {
getSupportActionBar().hide();
hideVideoControls(false);
enterPictureInPictureMode();
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
switch (keyCode) {
case android.view.KeyEvent.KEYCODE_P :
// Fallthrough
case android.view.KeyEvent.KEYCODE_SPACE :
onPlayPause();
toggleVideoControlsVisibility();
return true;
case android.view.KeyEvent.KEYCODE_J :
// Fallthrough
case android.view.KeyEvent.KEYCODE_A :
case android.view.KeyEvent.KEYCODE_COMMA :
onRewind();
showSkipAnimation(false);
return true;
case android.view.KeyEvent.KEYCODE_K :
// Fallthrough
case android.view.KeyEvent.KEYCODE_D :
case android.view.KeyEvent.KEYCODE_PERIOD :
onFastForward();
showSkipAnimation(true);
return true;
case android.view.KeyEvent.KEYCODE_F :
// Fallthrough
case android.view.KeyEvent.KEYCODE_ESCAPE :
// Exit fullscreen mode
onBackPressed();
return true;
case android.view.KeyEvent.KEYCODE_I :
compatEnterPictureInPicture();
return true;
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
// Go to x% of video:
if ((keyCode >= android.view.KeyEvent.KEYCODE_0) && (keyCode <= android.view.KeyEvent.KEYCODE_9)) {
switch(MUID_STATIC) {
// VideoplayerActivity_26_BinaryMutator
case 26149: {
controller.seekTo(((int) ((0.1F * (keyCode - android.view.KeyEvent.KEYCODE_0)) / controller.getDuration())));
break;
}
default: {
switch(MUID_STATIC) {
// VideoplayerActivity_27_BinaryMutator
case 27149: {
controller.seekTo(((int) ((0.1F / (keyCode - android.view.KeyEvent.KEYCODE_0)) * controller.getDuration())));
break;
}
default: {
switch(MUID_STATIC) {
// VideoplayerActivity_28_BinaryMutator
case 28149: {
controller.seekTo(((int) ((0.1F * (keyCode + android.view.KeyEvent.KEYCODE_0)) * controller.getDuration())));
break;
}
default: {
controller.seekTo(((int) ((0.1F * (keyCode - android.view.KeyEvent.KEYCODE_0)) * controller.getDuration())));
break;
}
}
break;
}
}
break;
}
}
return true;
}
return super.onKeyUp(keyCode, event);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
