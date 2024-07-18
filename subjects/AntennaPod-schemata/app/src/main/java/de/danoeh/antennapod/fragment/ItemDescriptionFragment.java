package de.danoeh.antennapod.fragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.content.SharedPreferences;
import io.reactivex.Maybe;
import android.util.Log;
import de.danoeh.antennapod.core.util.gui.ShownotesCleaner;
import de.danoeh.antennapod.view.ShownotesWebView;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import android.view.View;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import de.danoeh.antennapod.model.feed.FeedMedia;
import de.danoeh.antennapod.model.playback.Playable;
import android.app.Activity;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.util.playback.PlaybackController;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays the description of a Playable object in a Webview.
 */
public class ItemDescriptionFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "ItemDescriptionFragment";

    private static final java.lang.String PREF = "ItemDescriptionFragmentPrefs";

    private static final java.lang.String PREF_SCROLL_Y = "prefScrollY";

    private static final java.lang.String PREF_PLAYABLE_ID = "prefPlayableId";

    private de.danoeh.antennapod.view.ShownotesWebView webvDescription;

    private io.reactivex.disposables.Disposable webViewLoader;

    private de.danoeh.antennapod.core.util.playback.PlaybackController controller;

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.util.Log.d(de.danoeh.antennapod.fragment.ItemDescriptionFragment.TAG, "Creating view");
        android.view.View root;
        root = inflater.inflate(de.danoeh.antennapod.R.layout.item_description_fragment, container, false);
        switch(MUID_STATIC) {
            // ItemDescriptionFragment_0_InvalidViewFocusOperatorMutator
            case 131: {
                /**
                * Inserted by Kadabra
                */
                webvDescription = root.findViewById(de.danoeh.antennapod.R.id.webview);
                webvDescription.requestFocus();
                break;
            }
            // ItemDescriptionFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1131: {
                /**
                * Inserted by Kadabra
                */
                webvDescription = root.findViewById(de.danoeh.antennapod.R.id.webview);
                webvDescription.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            webvDescription = root.findViewById(de.danoeh.antennapod.R.id.webview);
            break;
        }
    }
    webvDescription.setTimecodeSelectedListener((java.lang.Integer time) -> {
        if (controller != null) {
            controller.seekTo(time);
        }
    });
    webvDescription.setPageFinishedListener(() -> {
        // Restoring the scroll position might not always work
        webvDescription.postDelayed(this::restoreFromPreference, 50);
    });
    root.addOnLayoutChangeListener(new android.view.View.OnLayoutChangeListener() {
        @java.lang.Override
        public void onLayoutChange(android.view.View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (root.getMeasuredHeight() != webvDescription.getMinimumHeight()) {
                webvDescription.setMinimumHeight(root.getMeasuredHeight());
            }
            root.removeOnLayoutChangeListener(this);
        }

    });
    registerForContextMenu(webvDescription);
    return root;
}


@java.lang.Override
public void onDestroy() {
    super.onDestroy();
    android.util.Log.d(de.danoeh.antennapod.fragment.ItemDescriptionFragment.TAG, "Fragment destroyed");
    if (webvDescription != null) {
        webvDescription.removeAllViews();
        webvDescription.destroy();
    }
}


@java.lang.Override
public boolean onContextItemSelected(android.view.MenuItem item) {
    return webvDescription.onContextItemSelected(item);
}


private void load() {
    android.util.Log.d(de.danoeh.antennapod.fragment.ItemDescriptionFragment.TAG, "load()");
    if (webViewLoader != null) {
        webViewLoader.dispose();
    }
    android.content.Context context;
    context = getContext();
    if (context == null) {
        return;
    }
    webViewLoader = io.reactivex.Maybe.<java.lang.String>create((io.reactivex.MaybeEmitter<java.lang.String> emitter) -> {
        de.danoeh.antennapod.model.playback.Playable media;
        media = controller.getMedia();
        if (media == null) {
            emitter.onComplete();
            return;
        }
        if (media instanceof de.danoeh.antennapod.model.feed.FeedMedia) {
            de.danoeh.antennapod.model.feed.FeedMedia feedMedia;
            feedMedia = ((de.danoeh.antennapod.model.feed.FeedMedia) (media));
            if (feedMedia.getItem() == null) {
                feedMedia.setItem(de.danoeh.antennapod.core.storage.DBReader.getFeedItem(feedMedia.getItemId()));
            }
            de.danoeh.antennapod.core.storage.DBReader.loadDescriptionOfFeedItem(feedMedia.getItem());
        }
        de.danoeh.antennapod.core.util.gui.ShownotesCleaner shownotesCleaner;
        shownotesCleaner = new de.danoeh.antennapod.core.util.gui.ShownotesCleaner(context, media.getDescription(), media.getDuration());
        emitter.onSuccess(shownotesCleaner.processShownotes());
    }).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.lang.String data) -> {
        webvDescription.loadDataWithBaseURL("https://127.0.0.1", data, "text/html", "utf-8", "about:blank");
        android.util.Log.d(de.danoeh.antennapod.fragment.ItemDescriptionFragment.TAG, "Webview loaded");
    }, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.ItemDescriptionFragment.TAG, android.util.Log.getStackTraceString(error)));
}


@java.lang.Override
public void onPause() {
    super.onPause();
    savePreference();
}


private void savePreference() {
    android.util.Log.d(de.danoeh.antennapod.fragment.ItemDescriptionFragment.TAG, "Saving preferences");
    android.content.SharedPreferences prefs;
    prefs = getActivity().getSharedPreferences(de.danoeh.antennapod.fragment.ItemDescriptionFragment.PREF, android.app.Activity.MODE_PRIVATE);
    android.content.SharedPreferences.Editor editor;
    editor = prefs.edit();
    if (((controller != null) && (controller.getMedia() != null)) && (webvDescription != null)) {
        android.util.Log.d(de.danoeh.antennapod.fragment.ItemDescriptionFragment.TAG, "Saving scroll position: " + webvDescription.getScrollY());
        editor.putInt(de.danoeh.antennapod.fragment.ItemDescriptionFragment.PREF_SCROLL_Y, webvDescription.getScrollY());
        editor.putString(de.danoeh.antennapod.fragment.ItemDescriptionFragment.PREF_PLAYABLE_ID, controller.getMedia().getIdentifier().toString());
    } else {
        android.util.Log.d(de.danoeh.antennapod.fragment.ItemDescriptionFragment.TAG, "savePreferences was called while media or webview was null");
        editor.putInt(de.danoeh.antennapod.fragment.ItemDescriptionFragment.PREF_SCROLL_Y, -1);
        editor.putString(de.danoeh.antennapod.fragment.ItemDescriptionFragment.PREF_PLAYABLE_ID, "");
    }
    editor.apply();
}


private boolean restoreFromPreference() {
    android.util.Log.d(de.danoeh.antennapod.fragment.ItemDescriptionFragment.TAG, "Restoring from preferences");
    android.app.Activity activity;
    activity = getActivity();
    if (activity != null) {
        android.content.SharedPreferences prefs;
        prefs = activity.getSharedPreferences(de.danoeh.antennapod.fragment.ItemDescriptionFragment.PREF, android.app.Activity.MODE_PRIVATE);
        java.lang.String id;
        id = prefs.getString(de.danoeh.antennapod.fragment.ItemDescriptionFragment.PREF_PLAYABLE_ID, "");
        int scrollY;
        scrollY = prefs.getInt(de.danoeh.antennapod.fragment.ItemDescriptionFragment.PREF_SCROLL_Y, -1);
        if (((((controller != null) && (scrollY != (-1))) && (controller.getMedia() != null)) && id.equals(controller.getMedia().getIdentifier().toString())) && (webvDescription != null)) {
            android.util.Log.d(de.danoeh.antennapod.fragment.ItemDescriptionFragment.TAG, "Restored scroll Position: " + scrollY);
            webvDescription.scrollTo(webvDescription.getScrollX(), scrollY);
            return true;
        }
    }
    return false;
}


public void scrollToTop() {
    webvDescription.scrollTo(0, 0);
    savePreference();
}


@java.lang.Override
public void onStart() {
    super.onStart();
    controller = new de.danoeh.antennapod.core.util.playback.PlaybackController(getActivity()) {
        @java.lang.Override
        public void loadMediaInfo() {
            load();
        }

    };
    controller.init();
    load();
}


@java.lang.Override
public void onStop() {
    super.onStop();
    if (webViewLoader != null) {
        webViewLoader.dispose();
    }
    controller.release();
    controller = null;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
