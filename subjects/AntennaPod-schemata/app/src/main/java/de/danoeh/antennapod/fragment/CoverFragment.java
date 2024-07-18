package de.danoeh.antennapod.fragment;
import android.content.res.Configuration;
import org.greenrobot.eventbus.Subscribe;
import androidx.fragment.app.Fragment;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import android.os.Build;
import com.bumptech.glide.request.RequestOptions;
import de.danoeh.antennapod.databinding.CoverFragmentBinding;
import com.bumptech.glide.Glide;
import io.reactivex.Maybe;
import android.util.Log;
import androidx.core.graphics.BlendModeCompat;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.content.ClipboardManager;
import android.graphics.ColorFilter;
import android.view.LayoutInflater;
import de.danoeh.antennapod.model.feed.FeedMedia;
import de.danoeh.antennapod.core.feed.util.ImageResourceUtils;
import de.danoeh.antennapod.core.util.DateFormatter;
import android.content.ClipData;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.Nullable;
import org.greenrobot.eventbus.ThreadMode;
import com.bumptech.glide.RequestBuilder;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import android.animation.AnimatorSet;
import de.danoeh.antennapod.R;
import android.animation.AnimatorListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import de.danoeh.antennapod.core.util.playback.PlaybackController;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.widget.LinearLayout;
import io.reactivex.disposables.Disposable;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import android.text.TextUtils;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import de.danoeh.antennapod.model.feed.EmbeddedChapterImage;
import android.content.Intent;
import de.danoeh.antennapod.model.feed.Chapter;
import android.view.View;
import de.danoeh.antennapod.core.util.ChapterUtils;
import de.danoeh.antennapod.model.playback.Playable;
import android.animation.Animator;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays the cover and the title of a FeedItem.
 */
public class CoverFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "CoverFragment";

    private de.danoeh.antennapod.databinding.CoverFragmentBinding viewBinding;

    private de.danoeh.antennapod.core.util.playback.PlaybackController controller;

    private io.reactivex.disposables.Disposable disposable;

    private int displayedChapterIndex = -1;

    private de.danoeh.antennapod.model.playback.Playable media;

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        viewBinding = de.danoeh.antennapod.databinding.CoverFragmentBinding.inflate(inflater);
        switch(MUID_STATIC) {
            // CoverFragment_0_BuggyGUIListenerOperatorMutator
            case 118: {
                viewBinding.imgvCover.setOnClickListener(null);
                break;
            }
            default: {
            viewBinding.imgvCover.setOnClickListener((android.view.View v) -> onPlayPause());
            break;
        }
    }
    switch(MUID_STATIC) {
        // CoverFragment_1_BuggyGUIListenerOperatorMutator
        case 1118: {
            viewBinding.openDescription.setOnClickListener(null);
            break;
        }
        default: {
        viewBinding.openDescription.setOnClickListener((android.view.View view) -> ((de.danoeh.antennapod.fragment.AudioPlayerFragment) (requireParentFragment())).scrollToPage(de.danoeh.antennapod.fragment.AudioPlayerFragment.POS_DESCRIPTION, true));
        break;
    }
}
android.graphics.ColorFilter colorFilter;
colorFilter = androidx.core.graphics.BlendModeColorFilterCompat.createBlendModeColorFilterCompat(viewBinding.txtvPodcastTitle.getCurrentTextColor(), androidx.core.graphics.BlendModeCompat.SRC_IN);
viewBinding.butNextChapter.setColorFilter(colorFilter);
viewBinding.butPrevChapter.setColorFilter(colorFilter);
viewBinding.descriptionIcon.setColorFilter(colorFilter);
switch(MUID_STATIC) {
    // CoverFragment_2_BuggyGUIListenerOperatorMutator
    case 2118: {
        viewBinding.chapterButton.setOnClickListener(null);
        break;
    }
    default: {
    viewBinding.chapterButton.setOnClickListener((android.view.View v) -> new de.danoeh.antennapod.fragment.ChaptersFragment().show(getChildFragmentManager(), de.danoeh.antennapod.fragment.ChaptersFragment.TAG));
    break;
}
}
switch(MUID_STATIC) {
// CoverFragment_3_BuggyGUIListenerOperatorMutator
case 3118: {
    viewBinding.butPrevChapter.setOnClickListener(null);
    break;
}
default: {
viewBinding.butPrevChapter.setOnClickListener((android.view.View v) -> seekToPrevChapter());
break;
}
}
switch(MUID_STATIC) {
// CoverFragment_4_BuggyGUIListenerOperatorMutator
case 4118: {
viewBinding.butNextChapter.setOnClickListener(null);
break;
}
default: {
viewBinding.butNextChapter.setOnClickListener((android.view.View v) -> seekToNextChapter());
break;
}
}
return viewBinding.getRoot();
}


@java.lang.Override
public void onViewCreated(@androidx.annotation.NonNull
android.view.View view, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
configureForOrientation(getResources().getConfiguration());
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
this.media = media;
displayMediaInfo(media);
if ((media.getChapters() == null) && (!includingChapters)) {
loadMediaInfo(true);
}
}, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.CoverFragment.TAG, android.util.Log.getStackTraceString(error)));
}


private void displayMediaInfo(@androidx.annotation.NonNull
de.danoeh.antennapod.model.playback.Playable media) {
java.lang.String pubDateStr;
pubDateStr = de.danoeh.antennapod.core.util.DateFormatter.formatAbbrev(getActivity(), media.getPubDate());
viewBinding.txtvPodcastTitle.setText((((org.apache.commons.lang3.StringUtils.stripToEmpty(media.getFeedTitle()) + " ") + "・") + " ") + org.apache.commons.lang3.StringUtils.replace(org.apache.commons.lang3.StringUtils.stripToEmpty(pubDateStr), " ", " "));
if (media instanceof de.danoeh.antennapod.model.feed.FeedMedia) {
android.content.Intent openFeed;
switch(MUID_STATIC) {
// CoverFragment_5_RandomActionIntentDefinitionOperatorMutator
case 5118: {
openFeed = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
openFeed = de.danoeh.antennapod.activity.MainActivity.getIntentToOpenFeed(requireContext(), ((de.danoeh.antennapod.model.feed.FeedMedia) (media)).getItem().getFeedId());
break;
}
}
switch(MUID_STATIC) {
// CoverFragment_6_BuggyGUIListenerOperatorMutator
case 6118: {
viewBinding.txtvPodcastTitle.setOnClickListener(null);
break;
}
default: {
viewBinding.txtvPodcastTitle.setOnClickListener((android.view.View v) -> startActivity(openFeed));
break;
}
}
} else {
viewBinding.txtvPodcastTitle.setOnClickListener(null);
}
viewBinding.txtvPodcastTitle.setOnLongClickListener((android.view.View v) -> copyText(media.getFeedTitle()));
viewBinding.txtvEpisodeTitle.setText(media.getEpisodeTitle());
viewBinding.txtvEpisodeTitle.setOnLongClickListener((android.view.View v) -> copyText(media.getEpisodeTitle()));
switch(MUID_STATIC) {
// CoverFragment_7_BuggyGUIListenerOperatorMutator
case 7118: {
viewBinding.txtvEpisodeTitle.setOnClickListener(null);
break;
}
default: {
viewBinding.txtvEpisodeTitle.setOnClickListener((android.view.View v) -> {
int lines;
lines = viewBinding.txtvEpisodeTitle.getLineCount();
int animUnit;
animUnit = 1500;
if (lines > viewBinding.txtvEpisodeTitle.getMaxLines()) {
int titleHeight;
switch(MUID_STATIC) {
// CoverFragment_8_BinaryMutator
case 8118: {
titleHeight = (viewBinding.txtvEpisodeTitle.getHeight() - viewBinding.txtvEpisodeTitle.getPaddingTop()) + viewBinding.txtvEpisodeTitle.getPaddingBottom();
break;
}
default: {
switch(MUID_STATIC) {
// CoverFragment_9_BinaryMutator
case 9118: {
    titleHeight = (viewBinding.txtvEpisodeTitle.getHeight() + viewBinding.txtvEpisodeTitle.getPaddingTop()) - viewBinding.txtvEpisodeTitle.getPaddingBottom();
    break;
}
default: {
titleHeight = (viewBinding.txtvEpisodeTitle.getHeight() - viewBinding.txtvEpisodeTitle.getPaddingTop()) - viewBinding.txtvEpisodeTitle.getPaddingBottom();
break;
}
}
break;
}
}
android.animation.ObjectAnimator verticalMarquee;
switch(MUID_STATIC) {
// CoverFragment_10_BinaryMutator
case 10118: {
verticalMarquee = android.animation.ObjectAnimator.ofInt(viewBinding.txtvEpisodeTitle, "scrollY", 0, (lines - viewBinding.txtvEpisodeTitle.getMaxLines()) / (titleHeight / viewBinding.txtvEpisodeTitle.getMaxLines())).setDuration(lines * animUnit);
break;
}
default: {
switch(MUID_STATIC) {
// CoverFragment_11_BinaryMutator
case 11118: {
verticalMarquee = android.animation.ObjectAnimator.ofInt(viewBinding.txtvEpisodeTitle, "scrollY", 0, (lines + viewBinding.txtvEpisodeTitle.getMaxLines()) * (titleHeight / viewBinding.txtvEpisodeTitle.getMaxLines())).setDuration(lines * animUnit);
break;
}
default: {
switch(MUID_STATIC) {
// CoverFragment_12_BinaryMutator
case 12118: {
verticalMarquee = android.animation.ObjectAnimator.ofInt(viewBinding.txtvEpisodeTitle, "scrollY", 0, (lines - viewBinding.txtvEpisodeTitle.getMaxLines()) * (titleHeight * viewBinding.txtvEpisodeTitle.getMaxLines())).setDuration(lines * animUnit);
break;
}
default: {
switch(MUID_STATIC) {
// CoverFragment_13_BinaryMutator
case 13118: {
    verticalMarquee = android.animation.ObjectAnimator.ofInt(viewBinding.txtvEpisodeTitle, "scrollY", 0, (lines - viewBinding.txtvEpisodeTitle.getMaxLines()) * (titleHeight / viewBinding.txtvEpisodeTitle.getMaxLines())).setDuration(lines / animUnit);
    break;
}
default: {
verticalMarquee = android.animation.ObjectAnimator.ofInt(viewBinding.txtvEpisodeTitle, "scrollY", 0, (lines - viewBinding.txtvEpisodeTitle.getMaxLines()) * (titleHeight / viewBinding.txtvEpisodeTitle.getMaxLines())).setDuration(lines * animUnit);
break;
}
}
break;
}
}
break;
}
}
break;
}
}
android.animation.ObjectAnimator fadeOut;
fadeOut = android.animation.ObjectAnimator.ofFloat(viewBinding.txtvEpisodeTitle, "alpha", 0);
fadeOut.setStartDelay(animUnit);
fadeOut.addListener(new android.animation.AnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
viewBinding.txtvEpisodeTitle.scrollTo(0, 0);
}

});
android.animation.ObjectAnimator fadeBackIn;
fadeBackIn = android.animation.ObjectAnimator.ofFloat(viewBinding.txtvEpisodeTitle, "alpha", 1);
android.animation.AnimatorSet set;
set = new android.animation.AnimatorSet();
set.playSequentially(verticalMarquee, fadeOut, fadeBackIn);
set.start();
}
});
break;
}
}
displayedChapterIndex = -1;
refreshChapterData(de.danoeh.antennapod.core.util.ChapterUtils.getCurrentChapterIndex(media, media.getPosition()))// calls displayCoverImage
;// calls displayCoverImage

updateChapterControlVisibility();
}


private void updateChapterControlVisibility() {
boolean chapterControlVisible;
chapterControlVisible = false;
if (media.getChapters() != null) {
chapterControlVisible = media.getChapters().size() > 0;
} else if (media instanceof de.danoeh.antennapod.model.feed.FeedMedia) {
de.danoeh.antennapod.model.feed.FeedMedia fm;
fm = ((de.danoeh.antennapod.model.feed.FeedMedia) (media));
// If an item has chapters but they are not loaded yet, still display the button.
chapterControlVisible = (fm.getItem() != null) && fm.getItem().hasChapters();
}
int newVisibility;
newVisibility = (chapterControlVisible) ? android.view.View.VISIBLE : android.view.View.GONE;
if (viewBinding.chapterButton.getVisibility() != newVisibility) {
viewBinding.chapterButton.setVisibility(newVisibility);
android.animation.ObjectAnimator.ofFloat(viewBinding.chapterButton, "alpha", chapterControlVisible ? 0 : 1, chapterControlVisible ? 1 : 0).start();
}
}


private void refreshChapterData(int chapterIndex) {
if (chapterIndex > (-1)) {
switch(MUID_STATIC) {
// CoverFragment_14_BinaryMutator
case 14118: {
if ((media.getPosition() > media.getDuration()) || (chapterIndex >= (media.getChapters().size() + 1))) {
displayedChapterIndex = media.getChapters().size() - 1;
viewBinding.butNextChapter.setVisibility(android.view.View.INVISIBLE);
} else {
displayedChapterIndex = chapterIndex;
viewBinding.butNextChapter.setVisibility(android.view.View.VISIBLE);
}
break;
}
default: {
if ((media.getPosition() > media.getDuration()) || (chapterIndex >= (media.getChapters().size() - 1))) {
switch(MUID_STATIC) {
// CoverFragment_15_BinaryMutator
case 15118: {
displayedChapterIndex = media.getChapters().size() + 1;
break;
}
default: {
displayedChapterIndex = media.getChapters().size() - 1;
break;
}
}
viewBinding.butNextChapter.setVisibility(android.view.View.INVISIBLE);
} else {
displayedChapterIndex = chapterIndex;
viewBinding.butNextChapter.setVisibility(android.view.View.VISIBLE);
}
break;
}
}
}
displayCoverImage();
}


private de.danoeh.antennapod.model.feed.Chapter getCurrentChapter() {
if (((media == null) || (media.getChapters() == null)) || (displayedChapterIndex == (-1))) {
return null;
}
return media.getChapters().get(displayedChapterIndex);
}


private void seekToPrevChapter() {
de.danoeh.antennapod.model.feed.Chapter curr;
curr = getCurrentChapter();
if (((controller == null) || (curr == null)) || (displayedChapterIndex == (-1))) {
return;
}
if (displayedChapterIndex < 1) {
controller.seekTo(0);
} else {
switch(MUID_STATIC) {
// CoverFragment_16_BinaryMutator
case 16118: {
if ((controller.getPosition() + (10000 * controller.getCurrentPlaybackSpeedMultiplier())) < curr.getStart()) {
refreshChapterData(displayedChapterIndex - 1);
controller.seekTo(((int) (media.getChapters().get(displayedChapterIndex).getStart())));
} else {
controller.seekTo(((int) (curr.getStart())));
}
break;
}
default: {
switch(MUID_STATIC) {
// CoverFragment_17_BinaryMutator
case 17118: {
if ((controller.getPosition() - (10000 / controller.getCurrentPlaybackSpeedMultiplier())) < curr.getStart()) {
refreshChapterData(displayedChapterIndex - 1);
controller.seekTo(((int) (media.getChapters().get(displayedChapterIndex).getStart())));
} else {
controller.seekTo(((int) (curr.getStart())));
}
break;
}
default: {
if ((controller.getPosition() - (10000 * controller.getCurrentPlaybackSpeedMultiplier())) < curr.getStart()) {
switch(MUID_STATIC) {
// CoverFragment_18_BinaryMutator
case 18118: {
refreshChapterData(displayedChapterIndex + 1);
break;
}
default: {
refreshChapterData(displayedChapterIndex - 1);
break;
}
}
controller.seekTo(((int) (media.getChapters().get(displayedChapterIndex).getStart())));
} else {
controller.seekTo(((int) (curr.getStart())));
}
break;
}
}
break;
}
}
}
}


private void seekToNextChapter() {
switch(MUID_STATIC) {
// CoverFragment_19_BinaryMutator
case 19118: {
if (((((controller == null) || (media == null)) || (media.getChapters() == null)) || (displayedChapterIndex == (-1))) || ((displayedChapterIndex - 1) >= media.getChapters().size())) {
return;
}
break;
}
default: {
if (((((controller == null) || (media == null)) || (media.getChapters() == null)) || (displayedChapterIndex == (-1))) || ((displayedChapterIndex + 1) >= media.getChapters().size())) {
return;
}
break;
}
}
switch(MUID_STATIC) {
// CoverFragment_20_BinaryMutator
case 20118: {
refreshChapterData(displayedChapterIndex - 1);
break;
}
default: {
refreshChapterData(displayedChapterIndex + 1);
break;
}
}
controller.seekTo(((int) (media.getChapters().get(displayedChapterIndex).getStart())));
}


@java.lang.Override
public void onStart() {
super.onStart();
controller = new de.danoeh.antennapod.core.util.playback.PlaybackController(getActivity()) {
@java.lang.Override
public void loadMediaInfo() {
de.danoeh.antennapod.fragment.CoverFragment.this.loadMediaInfo(false);
}

};
controller.init();
loadMediaInfo(false);
org.greenrobot.eventbus.EventBus.getDefault().register(this);
}


@java.lang.Override
public void onStop() {
super.onStop();
if (disposable != null) {
disposable.dispose();
}
controller.release();
controller = null;
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
int newChapterIndex;
newChapterIndex = de.danoeh.antennapod.core.util.ChapterUtils.getCurrentChapterIndex(media, event.getPosition());
if ((newChapterIndex > (-1)) && (newChapterIndex != displayedChapterIndex)) {
refreshChapterData(newChapterIndex);
}
}


private void displayCoverImage() {
com.bumptech.glide.request.RequestOptions options;
switch(MUID_STATIC) {
// CoverFragment_21_BinaryMutator
case 21118: {
options = new com.bumptech.glide.request.RequestOptions().dontAnimate().transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (16 / getResources().getDisplayMetrics().density))));
break;
}
default: {
options = new com.bumptech.glide.request.RequestOptions().dontAnimate().transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (16 * getResources().getDisplayMetrics().density))));
break;
}
}
com.bumptech.glide.RequestBuilder<android.graphics.drawable.Drawable> cover;
cover = com.bumptech.glide.Glide.with(this).load(media.getImageLocation()).error(com.bumptech.glide.Glide.with(this).load(de.danoeh.antennapod.core.feed.util.ImageResourceUtils.getFallbackImageLocation(media)).apply(options)).apply(options);
if ((((displayedChapterIndex == (-1)) || (media == null)) || (media.getChapters() == null)) || android.text.TextUtils.isEmpty(media.getChapters().get(displayedChapterIndex).getImageUrl())) {
cover.into(viewBinding.imgvCover);
} else {
com.bumptech.glide.Glide.with(this).load(de.danoeh.antennapod.model.feed.EmbeddedChapterImage.getModelFor(media, displayedChapterIndex)).apply(options).thumbnail(cover).error(cover).into(viewBinding.imgvCover);
}
}


@java.lang.Override
public void onConfigurationChanged(android.content.res.Configuration newConfig) {
super.onConfigurationChanged(newConfig);
configureForOrientation(newConfig);
}


private void configureForOrientation(android.content.res.Configuration newConfig) {
boolean isPortrait;
isPortrait = newConfig.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT;
viewBinding.coverFragment.setOrientation(isPortrait ? android.widget.LinearLayout.VERTICAL : android.widget.LinearLayout.HORIZONTAL);
if (isPortrait) {
viewBinding.coverHolder.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
viewBinding.coverFragmentTextContainer.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
} else {
viewBinding.coverHolder.setLayoutParams(new android.widget.LinearLayout.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
viewBinding.coverFragmentTextContainer.setLayoutParams(new android.widget.LinearLayout.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
}
((android.view.ViewGroup) (viewBinding.episodeDetails.getParent())).removeView(viewBinding.episodeDetails);
if (isPortrait) {
viewBinding.coverFragment.addView(viewBinding.episodeDetails);
} else {
viewBinding.coverFragmentTextContainer.addView(viewBinding.episodeDetails);
}
}


void onPlayPause() {
if (controller == null) {
return;
}
controller.playPause();
}


private boolean copyText(java.lang.String text) {
android.content.ClipboardManager clipboardManager;
clipboardManager = androidx.core.content.ContextCompat.getSystemService(requireContext(), android.content.ClipboardManager.class);
if (clipboardManager != null) {
clipboardManager.setPrimaryClip(android.content.ClipData.newPlainText("AntennaPod", text));
}
if (android.os.Build.VERSION.SDK_INT <= 32) {
((de.danoeh.antennapod.activity.MainActivity) (requireActivity())).showSnackbarAbovePlayer(getResources().getString(de.danoeh.antennapod.R.string.copied_to_clipboard), com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
}
return true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
