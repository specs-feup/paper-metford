package de.danoeh.antennapod.fragment;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import de.danoeh.antennapod.core.service.playback.PlaybackService;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.playback.MediaType;
import de.danoeh.antennapod.core.util.playback.PlaybackController;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import de.danoeh.antennapod.event.playback.PlaybackServiceEvent;
import de.danoeh.antennapod.view.PlayButton;
import android.widget.TextView;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.Maybe;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import android.content.Intent;
import android.view.View;
import de.danoeh.antennapod.playback.base.PlayerStatus;
import android.view.LayoutInflater;
import de.danoeh.antennapod.model.playback.Playable;
import de.danoeh.antennapod.core.feed.util.ImageResourceUtils;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Fragment which is supposed to be displayed outside of the MediaplayerActivity.
 */
public class ExternalPlayerFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "ExternalPlayerFragment";

    private android.widget.ImageView imgvCover;

    private android.widget.TextView txtvTitle;

    private de.danoeh.antennapod.view.PlayButton butPlay;

    private android.widget.TextView feedName;

    private android.widget.ProgressBar progressBar;

    private de.danoeh.antennapod.core.util.playback.PlaybackController controller;

    private io.reactivex.disposables.Disposable disposable;

    public ExternalPlayerFragment() {
        super();
    }


    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View root;
        root = inflater.inflate(de.danoeh.antennapod.R.layout.external_player_fragment, container, false);
        switch(MUID_STATIC) {
            // ExternalPlayerFragment_0_InvalidViewFocusOperatorMutator
            case 125: {
                /**
                * Inserted by Kadabra
                */
                imgvCover = root.findViewById(de.danoeh.antennapod.R.id.imgvCover);
                imgvCover.requestFocus();
                break;
            }
            // ExternalPlayerFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1125: {
                /**
                * Inserted by Kadabra
                */
                imgvCover = root.findViewById(de.danoeh.antennapod.R.id.imgvCover);
                imgvCover.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            imgvCover = root.findViewById(de.danoeh.antennapod.R.id.imgvCover);
            break;
        }
    }
    switch(MUID_STATIC) {
        // ExternalPlayerFragment_2_InvalidViewFocusOperatorMutator
        case 2125: {
            /**
            * Inserted by Kadabra
            */
            txtvTitle = root.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
            txtvTitle.requestFocus();
            break;
        }
        // ExternalPlayerFragment_3_ViewComponentNotVisibleOperatorMutator
        case 3125: {
            /**
            * Inserted by Kadabra
            */
            txtvTitle = root.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
            txtvTitle.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        txtvTitle = root.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
        break;
    }
}
switch(MUID_STATIC) {
    // ExternalPlayerFragment_4_InvalidViewFocusOperatorMutator
    case 4125: {
        /**
        * Inserted by Kadabra
        */
        butPlay = root.findViewById(de.danoeh.antennapod.R.id.butPlay);
        butPlay.requestFocus();
        break;
    }
    // ExternalPlayerFragment_5_ViewComponentNotVisibleOperatorMutator
    case 5125: {
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
// ExternalPlayerFragment_6_InvalidViewFocusOperatorMutator
case 6125: {
    /**
    * Inserted by Kadabra
    */
    feedName = root.findViewById(de.danoeh.antennapod.R.id.txtvAuthor);
    feedName.requestFocus();
    break;
}
// ExternalPlayerFragment_7_ViewComponentNotVisibleOperatorMutator
case 7125: {
    /**
    * Inserted by Kadabra
    */
    feedName = root.findViewById(de.danoeh.antennapod.R.id.txtvAuthor);
    feedName.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
feedName = root.findViewById(de.danoeh.antennapod.R.id.txtvAuthor);
break;
}
}
switch(MUID_STATIC) {
// ExternalPlayerFragment_8_InvalidViewFocusOperatorMutator
case 8125: {
/**
* Inserted by Kadabra
*/
progressBar = root.findViewById(de.danoeh.antennapod.R.id.episodeProgress);
progressBar.requestFocus();
break;
}
// ExternalPlayerFragment_9_ViewComponentNotVisibleOperatorMutator
case 9125: {
/**
* Inserted by Kadabra
*/
progressBar = root.findViewById(de.danoeh.antennapod.R.id.episodeProgress);
progressBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progressBar = root.findViewById(de.danoeh.antennapod.R.id.episodeProgress);
break;
}
}
switch(MUID_STATIC) {
// ExternalPlayerFragment_10_BuggyGUIListenerOperatorMutator
case 10125: {
root.findViewById(de.danoeh.antennapod.R.id.fragmentLayout).setOnClickListener(null);
break;
}
default: {
root.findViewById(de.danoeh.antennapod.R.id.fragmentLayout).setOnClickListener((android.view.View v) -> {
android.util.Log.d(de.danoeh.antennapod.fragment.ExternalPlayerFragment.TAG, "layoutInfo was clicked");
if ((controller != null) && (controller.getMedia() != null)) {
if (controller.getMedia().getMediaType() == de.danoeh.antennapod.model.playback.MediaType.AUDIO) {
    ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getBottomSheet().setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
} else {
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // ExternalPlayerFragment_11_RandomActionIntentDefinitionOperatorMutator
        case 11125: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = de.danoeh.antennapod.core.service.playback.PlaybackService.getPlayerActivityIntent(getActivity(), controller.getMedia());
        break;
    }
}
startActivity(intent);
}
}
});
break;
}
}
return root;
}


@java.lang.Override
public void onViewCreated(@androidx.annotation.NonNull
android.view.View view, android.os.Bundle savedInstanceState) {
super.onViewCreated(view, savedInstanceState);
switch(MUID_STATIC) {
// ExternalPlayerFragment_12_BuggyGUIListenerOperatorMutator
case 12125: {
butPlay.setOnClickListener(null);
break;
}
default: {
butPlay.setOnClickListener((android.view.View v) -> {
if (controller == null) {
return;
}
if (((controller.getMedia() != null) && (controller.getMedia().getMediaType() == de.danoeh.antennapod.model.playback.MediaType.VIDEO)) && (controller.getStatus() != de.danoeh.antennapod.playback.base.PlayerStatus.PLAYING)) {
controller.playPause();
getContext().startActivity(de.danoeh.antennapod.core.service.playback.PlaybackService.getPlayerActivityIntent(getContext(), controller.getMedia()));
} else {
controller.playPause();
}
});
break;
}
}
loadMediaInfo();
}


private de.danoeh.antennapod.core.util.playback.PlaybackController setupPlaybackController() {
return new de.danoeh.antennapod.core.util.playback.PlaybackController(getActivity()) {
@java.lang.Override
protected void updatePlayButtonShowsPlay(boolean showPlay) {
butPlay.setIsShowPlay(showPlay);
}


@java.lang.Override
public void loadMediaInfo() {
de.danoeh.antennapod.fragment.ExternalPlayerFragment.this.loadMediaInfo();
}


@java.lang.Override
public void onPlaybackEnd() {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).setPlayerVisible(false);
}

};
}


@java.lang.Override
public void onStart() {
super.onStart();
controller = setupPlaybackController();
controller.init();
loadMediaInfo();
org.greenrobot.eventbus.EventBus.getDefault().register(this);
}


@java.lang.Override
public void onStop() {
super.onStop();
if (controller != null) {
controller.release();
controller = null;
}
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onPositionObserverUpdate(de.danoeh.antennapod.event.playback.PlaybackPositionEvent event) {
if (controller == null) {
return;
} else if ((controller.getPosition() == de.danoeh.antennapod.model.playback.Playable.INVALID_TIME) || (controller.getDuration() == de.danoeh.antennapod.model.playback.Playable.INVALID_TIME)) {
return;
}
switch(MUID_STATIC) {
// ExternalPlayerFragment_13_BinaryMutator
case 13125: {
progressBar.setProgress(((int) ((((double) (controller.getPosition())) / controller.getDuration()) / 100)));
break;
}
default: {
switch(MUID_STATIC) {
// ExternalPlayerFragment_14_BinaryMutator
case 14125: {
progressBar.setProgress(((int) ((((double) (controller.getPosition())) * controller.getDuration()) * 100)));
break;
}
default: {
progressBar.setProgress(((int) ((((double) (controller.getPosition())) / controller.getDuration()) * 100)));
break;
}
}
break;
}
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onPlaybackServiceChanged(de.danoeh.antennapod.event.playback.PlaybackServiceEvent event) {
if (event.action == de.danoeh.antennapod.event.playback.PlaybackServiceEvent.Action.SERVICE_SHUT_DOWN) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).setPlayerVisible(false);
}
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
android.util.Log.d(de.danoeh.antennapod.fragment.ExternalPlayerFragment.TAG, "Fragment is about to be destroyed");
if (disposable != null) {
disposable.dispose();
}
}


@java.lang.Override
public void onPause() {
super.onPause();
if (controller != null) {
controller.pause();
}
}


private void loadMediaInfo() {
android.util.Log.d(de.danoeh.antennapod.fragment.ExternalPlayerFragment.TAG, "Loading media info");
if (controller == null) {
android.util.Log.w(de.danoeh.antennapod.fragment.ExternalPlayerFragment.TAG, "loadMediaInfo was called while PlaybackController was null!");
return;
}
if (disposable != null) {
disposable.dispose();
}
disposable = io.reactivex.Maybe.fromCallable(() -> controller.getMedia()).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(this::updateUi, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.ExternalPlayerFragment.TAG, android.util.Log.getStackTraceString(error)), () -> ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).setPlayerVisible(false));
}


private void updateUi(de.danoeh.antennapod.model.playback.Playable media) {
if (media == null) {
return;
}
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).setPlayerVisible(true);
txtvTitle.setText(media.getEpisodeTitle());
feedName.setText(media.getFeedTitle());
onPositionObserverUpdate(new de.danoeh.antennapod.event.playback.PlaybackPositionEvent(media.getPosition(), media.getDuration()));
com.bumptech.glide.request.RequestOptions options;
options = new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).error(de.danoeh.antennapod.R.color.light_gray).fitCenter().dontAnimate();
com.bumptech.glide.Glide.with(this).load(de.danoeh.antennapod.core.feed.util.ImageResourceUtils.getEpisodeListImageLocation(media)).error(com.bumptech.glide.Glide.with(this).load(de.danoeh.antennapod.core.feed.util.ImageResourceUtils.getFallbackImageLocation(media)).apply(options)).apply(options).into(imgvCover);
if ((controller != null) && controller.isPlayingVideoLocally()) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getBottomSheet().setLocked(true);
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getBottomSheet().setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
} else {
butPlay.setVisibility(android.view.View.VISIBLE);
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).getBottomSheet().setLocked(false);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
