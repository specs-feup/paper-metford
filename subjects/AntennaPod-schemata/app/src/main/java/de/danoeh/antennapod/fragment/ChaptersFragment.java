package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.adapter.ChaptersListAdapter;
import androidx.appcompat.app.AlertDialog;
import org.greenrobot.eventbus.ThreadMode;
import android.content.DialogInterface;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import android.app.Dialog;
import de.danoeh.antennapod.core.util.playback.PlaybackController;
import de.danoeh.antennapod.event.playback.PlaybackPositionEvent;
import android.widget.Toast;
import android.widget.ProgressBar;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.Maybe;
import android.util.Log;
import android.os.Bundle;
import io.reactivex.schedulers.Schedulers;
import android.text.TextUtils;
import de.danoeh.antennapod.model.feed.Chapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.recyclerview.widget.DividerItemDecoration;
import android.view.View;
import de.danoeh.antennapod.playback.base.PlayerStatus;
import de.danoeh.antennapod.core.util.ChapterUtils;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import de.danoeh.antennapod.model.feed.FeedMedia;
import de.danoeh.antennapod.model.playback.Playable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ChaptersFragment extends androidx.appcompat.app.AppCompatDialogFragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "ChaptersFragment";

    private de.danoeh.antennapod.adapter.ChaptersListAdapter adapter;

    private de.danoeh.antennapod.core.util.playback.PlaybackController controller;

    private io.reactivex.disposables.Disposable disposable;

    private int focusedChapter = -1;

    private de.danoeh.antennapod.model.playback.Playable media;

    private androidx.recyclerview.widget.LinearLayoutManager layoutManager;

    private android.widget.ProgressBar progressBar;

    @androidx.annotation.NonNull
    @java.lang.Override
    public android.app.Dialog onCreateDialog(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog dialog;
        dialog = // dismisses
        new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext()).setTitle(getString(de.danoeh.antennapod.R.string.chapters_label)).setView(onCreateView(getLayoutInflater())).setPositiveButton(getString(de.danoeh.antennapod.R.string.close_label), null).setNeutralButton(getString(de.danoeh.antennapod.R.string.refresh_label), null).create();
        dialog.show();
        dialog.getButton(android.content.DialogInterface.BUTTON_NEUTRAL).setVisibility(android.view.View.INVISIBLE);
        switch(MUID_STATIC) {
            // ChaptersFragment_0_BuggyGUIListenerOperatorMutator
            case 136: {
                dialog.getButton(android.content.DialogInterface.BUTTON_NEUTRAL).setOnClickListener(null);
                break;
            }
            default: {
            dialog.getButton(android.content.DialogInterface.BUTTON_NEUTRAL).setOnClickListener((android.view.View v) -> {
                progressBar.setVisibility(android.view.View.VISIBLE);
                loadMediaInfo(true);
            });
            break;
        }
    }
    return dialog;
}


public android.view.View onCreateView(@androidx.annotation.NonNull
android.view.LayoutInflater inflater) {
    android.view.View root;
    root = inflater.inflate(de.danoeh.antennapod.R.layout.simple_list_fragment, null, false);
    root.findViewById(de.danoeh.antennapod.R.id.toolbar).setVisibility(android.view.View.GONE);
    androidx.recyclerview.widget.RecyclerView recyclerView;
    switch(MUID_STATIC) {
        // ChaptersFragment_1_InvalidViewFocusOperatorMutator
        case 1136: {
            /**
            * Inserted by Kadabra
            */
            recyclerView = root.findViewById(de.danoeh.antennapod.R.id.recyclerView);
            recyclerView.requestFocus();
            break;
        }
        // ChaptersFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2136: {
            /**
            * Inserted by Kadabra
            */
            recyclerView = root.findViewById(de.danoeh.antennapod.R.id.recyclerView);
            recyclerView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        recyclerView = root.findViewById(de.danoeh.antennapod.R.id.recyclerView);
        break;
    }
}
switch(MUID_STATIC) {
    // ChaptersFragment_3_InvalidViewFocusOperatorMutator
    case 3136: {
        /**
        * Inserted by Kadabra
        */
        progressBar = root.findViewById(de.danoeh.antennapod.R.id.progLoading);
        progressBar.requestFocus();
        break;
    }
    // ChaptersFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4136: {
        /**
        * Inserted by Kadabra
        */
        progressBar = root.findViewById(de.danoeh.antennapod.R.id.progLoading);
        progressBar.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    progressBar = root.findViewById(de.danoeh.antennapod.R.id.progLoading);
    break;
}
}
layoutManager = new androidx.recyclerview.widget.LinearLayoutManager(getActivity());
recyclerView.setLayoutManager(layoutManager);
recyclerView.addItemDecoration(new androidx.recyclerview.widget.DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation()));
adapter = new de.danoeh.antennapod.adapter.ChaptersListAdapter(getActivity(), (int pos) -> {
if (controller.getStatus() != de.danoeh.antennapod.playback.base.PlayerStatus.PLAYING) {
    controller.playPause();
}
de.danoeh.antennapod.model.feed.Chapter chapter;
chapter = adapter.getItem(pos);
controller.seekTo(((int) (chapter.getStart())));
updateChapterSelection(pos, true);
});
recyclerView.setAdapter(adapter);
progressBar.setVisibility(android.view.View.VISIBLE);
androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams wrapHeight;
wrapHeight = new androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams(androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams.MATCH_PARENT, androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams.WRAP_CONTENT);
recyclerView.setLayoutParams(wrapHeight);
return root;
}


@java.lang.Override
public void onStart() {
super.onStart();
controller = new de.danoeh.antennapod.core.util.playback.PlaybackController(getActivity()) {
@java.lang.Override
public void loadMediaInfo() {
    de.danoeh.antennapod.fragment.ChaptersFragment.this.loadMediaInfo(false);
}

};
controller.init();
org.greenrobot.eventbus.EventBus.getDefault().register(this);
loadMediaInfo(false);
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
updateChapterSelection(getCurrentChapter(media), false);
adapter.notifyTimeChanged(event.getPosition());
}


private int getCurrentChapter(de.danoeh.antennapod.model.playback.Playable media) {
if (controller == null) {
return -1;
}
return de.danoeh.antennapod.core.util.ChapterUtils.getCurrentChapterIndex(media, controller.getPosition());
}


private void loadMediaInfo(boolean forceRefresh) {
if (disposable != null) {
disposable.dispose();
}
disposable = io.reactivex.Maybe.create((io.reactivex.MaybeEmitter<java.lang.Object> emitter) -> {
de.danoeh.antennapod.model.playback.Playable media;
media = controller.getMedia();
if (media != null) {
    de.danoeh.antennapod.core.util.ChapterUtils.loadChapters(media, getContext(), forceRefresh);
    emitter.onSuccess(media);
} else {
    emitter.onComplete();
}
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.lang.Object media) -> onMediaChanged(((de.danoeh.antennapod.model.playback.Playable) (media))), (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.ChaptersFragment.TAG, android.util.Log.getStackTraceString(error)));
}


private void onMediaChanged(de.danoeh.antennapod.model.playback.Playable media) {
this.media = media;
focusedChapter = -1;
if (adapter == null) {
return;
}
if ((media.getChapters() != null) && (media.getChapters().size() == 0)) {
dismiss();
android.widget.Toast.makeText(getContext(), de.danoeh.antennapod.R.string.no_chapters_label, android.widget.Toast.LENGTH_LONG).show();
} else {
progressBar.setVisibility(android.view.View.GONE);
}
adapter.setMedia(media);
((androidx.appcompat.app.AlertDialog) (getDialog())).getButton(android.content.DialogInterface.BUTTON_NEUTRAL).setVisibility(android.view.View.INVISIBLE);
if (((media instanceof de.danoeh.antennapod.model.feed.FeedMedia) && (((de.danoeh.antennapod.model.feed.FeedMedia) (media)).getItem() != null)) && (!android.text.TextUtils.isEmpty(((de.danoeh.antennapod.model.feed.FeedMedia) (media)).getItem().getPodcastIndexChapterUrl()))) {
((androidx.appcompat.app.AlertDialog) (getDialog())).getButton(android.content.DialogInterface.BUTTON_NEUTRAL).setVisibility(android.view.View.VISIBLE);
}
int positionOfCurrentChapter;
positionOfCurrentChapter = getCurrentChapter(media);
updateChapterSelection(positionOfCurrentChapter, true);
}


private void updateChapterSelection(int position, boolean scrollTo) {
if (adapter == null) {
return;
}
if ((position != (-1)) && (focusedChapter != position)) {
focusedChapter = position;
adapter.notifyChapterChanged(focusedChapter);
if (scrollTo && ((layoutManager.findFirstCompletelyVisibleItemPosition() >= position) || (layoutManager.findLastCompletelyVisibleItemPosition() <= position))) {
    layoutManager.scrollToPositionWithOffset(position, 100);
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
