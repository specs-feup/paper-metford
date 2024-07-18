package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.activity.OnlineFeedViewActivity;
import java.util.Locale;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.event.DiscoveryDefaultUpdateEvent;
import java.util.ArrayList;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.net.discovery.ItunesTopListLoader;
import android.util.DisplayMetrics;
import de.danoeh.antennapod.R;
import android.widget.Button;
import static android.content.Context.MODE_PRIVATE;
import android.widget.TextView;
import de.danoeh.antennapod.BuildConfig;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.widget.LinearLayout;
import io.reactivex.disposables.Disposable;
import android.content.SharedPreferences;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import android.text.TextUtils;
import de.danoeh.antennapod.adapter.FeedDiscoverAdapter;
import io.reactivex.Observable;
import android.content.Intent;
import android.view.View;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import android.widget.AdapterView;
import android.widget.GridView;
import de.danoeh.antennapod.net.discovery.PodcastSearchResult;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class QuickFeedDiscoveryFragment extends androidx.fragment.app.Fragment implements android.widget.AdapterView.OnItemClickListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "FeedDiscoveryFragment";

    private static final int NUM_SUGGESTIONS = 12;

    private io.reactivex.disposables.Disposable disposable;

    private de.danoeh.antennapod.adapter.FeedDiscoverAdapter adapter;

    private android.widget.GridView discoverGridLayout;

    private android.widget.TextView errorTextView;

    private android.widget.TextView poweredByTextView;

    private android.widget.LinearLayout errorView;

    private android.widget.Button errorRetry;

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        android.view.View root;
        root = inflater.inflate(de.danoeh.antennapod.R.layout.quick_feed_discovery, container, false);
        android.view.View discoverMore;
        switch(MUID_STATIC) {
            // QuickFeedDiscoveryFragment_0_InvalidViewFocusOperatorMutator
            case 124: {
                /**
                * Inserted by Kadabra
                */
                discoverMore = root.findViewById(de.danoeh.antennapod.R.id.discover_more);
                discoverMore.requestFocus();
                break;
            }
            // QuickFeedDiscoveryFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1124: {
                /**
                * Inserted by Kadabra
                */
                discoverMore = root.findViewById(de.danoeh.antennapod.R.id.discover_more);
                discoverMore.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            discoverMore = root.findViewById(de.danoeh.antennapod.R.id.discover_more);
            break;
        }
    }
    switch(MUID_STATIC) {
        // QuickFeedDiscoveryFragment_2_BuggyGUIListenerOperatorMutator
        case 2124: {
            discoverMore.setOnClickListener(null);
            break;
        }
        default: {
        discoverMore.setOnClickListener((android.view.View v) -> ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(new de.danoeh.antennapod.fragment.DiscoveryFragment()));
        break;
    }
}
switch(MUID_STATIC) {
    // QuickFeedDiscoveryFragment_3_InvalidViewFocusOperatorMutator
    case 3124: {
        /**
        * Inserted by Kadabra
        */
        discoverGridLayout = root.findViewById(de.danoeh.antennapod.R.id.discover_grid);
        discoverGridLayout.requestFocus();
        break;
    }
    // QuickFeedDiscoveryFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4124: {
        /**
        * Inserted by Kadabra
        */
        discoverGridLayout = root.findViewById(de.danoeh.antennapod.R.id.discover_grid);
        discoverGridLayout.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    discoverGridLayout = root.findViewById(de.danoeh.antennapod.R.id.discover_grid);
    break;
}
}
switch(MUID_STATIC) {
// QuickFeedDiscoveryFragment_5_InvalidViewFocusOperatorMutator
case 5124: {
    /**
    * Inserted by Kadabra
    */
    errorView = root.findViewById(de.danoeh.antennapod.R.id.discover_error);
    errorView.requestFocus();
    break;
}
// QuickFeedDiscoveryFragment_6_ViewComponentNotVisibleOperatorMutator
case 6124: {
    /**
    * Inserted by Kadabra
    */
    errorView = root.findViewById(de.danoeh.antennapod.R.id.discover_error);
    errorView.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
errorView = root.findViewById(de.danoeh.antennapod.R.id.discover_error);
break;
}
}
switch(MUID_STATIC) {
// QuickFeedDiscoveryFragment_7_InvalidViewFocusOperatorMutator
case 7124: {
/**
* Inserted by Kadabra
*/
errorTextView = root.findViewById(de.danoeh.antennapod.R.id.discover_error_txtV);
errorTextView.requestFocus();
break;
}
// QuickFeedDiscoveryFragment_8_ViewComponentNotVisibleOperatorMutator
case 8124: {
/**
* Inserted by Kadabra
*/
errorTextView = root.findViewById(de.danoeh.antennapod.R.id.discover_error_txtV);
errorTextView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
errorTextView = root.findViewById(de.danoeh.antennapod.R.id.discover_error_txtV);
break;
}
}
switch(MUID_STATIC) {
// QuickFeedDiscoveryFragment_9_InvalidViewFocusOperatorMutator
case 9124: {
/**
* Inserted by Kadabra
*/
errorRetry = root.findViewById(de.danoeh.antennapod.R.id.discover_error_retry_btn);
errorRetry.requestFocus();
break;
}
// QuickFeedDiscoveryFragment_10_ViewComponentNotVisibleOperatorMutator
case 10124: {
/**
* Inserted by Kadabra
*/
errorRetry = root.findViewById(de.danoeh.antennapod.R.id.discover_error_retry_btn);
errorRetry.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
errorRetry = root.findViewById(de.danoeh.antennapod.R.id.discover_error_retry_btn);
break;
}
}
switch(MUID_STATIC) {
// QuickFeedDiscoveryFragment_11_InvalidViewFocusOperatorMutator
case 11124: {
/**
* Inserted by Kadabra
*/
poweredByTextView = root.findViewById(de.danoeh.antennapod.R.id.discover_powered_by_itunes);
poweredByTextView.requestFocus();
break;
}
// QuickFeedDiscoveryFragment_12_ViewComponentNotVisibleOperatorMutator
case 12124: {
/**
* Inserted by Kadabra
*/
poweredByTextView = root.findViewById(de.danoeh.antennapod.R.id.discover_powered_by_itunes);
poweredByTextView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
poweredByTextView = root.findViewById(de.danoeh.antennapod.R.id.discover_powered_by_itunes);
break;
}
}
adapter = new de.danoeh.antennapod.adapter.FeedDiscoverAdapter(((de.danoeh.antennapod.activity.MainActivity) (getActivity())));
discoverGridLayout.setAdapter(adapter);
discoverGridLayout.setOnItemClickListener(this);
android.util.DisplayMetrics displayMetrics;
displayMetrics = getContext().getResources().getDisplayMetrics();
float screenWidthDp;
switch(MUID_STATIC) {
// QuickFeedDiscoveryFragment_13_BinaryMutator
case 13124: {
screenWidthDp = displayMetrics.widthPixels * displayMetrics.density;
break;
}
default: {
screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
break;
}
}
if (screenWidthDp > 600) {
discoverGridLayout.setNumColumns(6);
} else {
discoverGridLayout.setNumColumns(4);
}
// Fill with dummy elements to have a fixed height and
// prevent the UI elements below from jumping on slow connections
java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> dummies;
dummies = new java.util.ArrayList<>();
for (int i = 0; i < de.danoeh.antennapod.fragment.QuickFeedDiscoveryFragment.NUM_SUGGESTIONS; i++) {
dummies.add(de.danoeh.antennapod.net.discovery.PodcastSearchResult.dummy());
}
adapter.updateData(dummies);
loadToplist();
org.greenrobot.eventbus.EventBus.getDefault().register(this);
return root;
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
if (disposable != null) {
disposable.dispose();
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
@java.lang.SuppressWarnings("unused")
public void onDiscoveryDefaultUpdateEvent(de.danoeh.antennapod.event.DiscoveryDefaultUpdateEvent event) {
loadToplist();
}


private void loadToplist() {
errorView.setVisibility(android.view.View.GONE);
errorRetry.setVisibility(android.view.View.INVISIBLE);
errorRetry.setText(de.danoeh.antennapod.R.string.retry_label);
poweredByTextView.setVisibility(android.view.View.VISIBLE);
de.danoeh.antennapod.net.discovery.ItunesTopListLoader loader;
loader = new de.danoeh.antennapod.net.discovery.ItunesTopListLoader(getContext());
android.content.SharedPreferences prefs;
prefs = getActivity().getSharedPreferences(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREFS, android.content.Context.MODE_PRIVATE);
java.lang.String countryCode;
countryCode = prefs.getString(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_COUNTRY_CODE, java.util.Locale.getDefault().getCountry());
if (prefs.getBoolean(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_HIDDEN_DISCOVERY_COUNTRY, false)) {
errorTextView.setText(de.danoeh.antennapod.R.string.discover_is_hidden);
errorView.setVisibility(android.view.View.VISIBLE);
discoverGridLayout.setVisibility(android.view.View.GONE);
errorRetry.setVisibility(android.view.View.GONE);
poweredByTextView.setVisibility(android.view.View.GONE);
return;
}
// noinspection ConstantConditions
if (de.danoeh.antennapod.BuildConfig.FLAVOR.equals("free") && prefs.getBoolean(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_NEEDS_CONFIRM, true)) {
errorTextView.setText("");
errorView.setVisibility(android.view.View.VISIBLE);
discoverGridLayout.setVisibility(android.view.View.VISIBLE);
errorRetry.setVisibility(android.view.View.VISIBLE);
errorRetry.setText(de.danoeh.antennapod.R.string.discover_confirm);
poweredByTextView.setVisibility(android.view.View.VISIBLE);
switch(MUID_STATIC) {
// QuickFeedDiscoveryFragment_14_BuggyGUIListenerOperatorMutator
case 14124: {
errorRetry.setOnClickListener(null);
break;
}
default: {
errorRetry.setOnClickListener((android.view.View v) -> {
prefs.edit().putBoolean(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_NEEDS_CONFIRM, false).apply();
loadToplist();
});
break;
}
}
return;
}
disposable = io.reactivex.Observable.fromCallable(() -> loader.loadToplist(countryCode, de.danoeh.antennapod.fragment.QuickFeedDiscoveryFragment.NUM_SUGGESTIONS, de.danoeh.antennapod.core.storage.DBReader.getFeedList())).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> podcasts) -> {
errorView.setVisibility(android.view.View.GONE);
if (podcasts.size() == 0) {
errorTextView.setText(getResources().getText(de.danoeh.antennapod.R.string.search_status_no_results));
errorView.setVisibility(android.view.View.VISIBLE);
discoverGridLayout.setVisibility(android.view.View.INVISIBLE);
} else {
discoverGridLayout.setVisibility(android.view.View.VISIBLE);
adapter.updateData(podcasts);
}
}, (java.lang.Throwable error) -> {
android.util.Log.e(de.danoeh.antennapod.fragment.QuickFeedDiscoveryFragment.TAG, android.util.Log.getStackTraceString(error));
errorTextView.setText(error.getLocalizedMessage());
errorView.setVisibility(android.view.View.VISIBLE);
discoverGridLayout.setVisibility(android.view.View.INVISIBLE);
errorRetry.setVisibility(android.view.View.VISIBLE);
switch(MUID_STATIC) {
// QuickFeedDiscoveryFragment_15_BuggyGUIListenerOperatorMutator
case 15124: {
errorRetry.setOnClickListener(null);
break;
}
default: {
errorRetry.setOnClickListener((android.view.View v) -> loadToplist());
break;
}
}
});
}


@java.lang.Override
public void onItemClick(android.widget.AdapterView<?> parent, final android.view.View view, int position, long id) {
de.danoeh.antennapod.net.discovery.PodcastSearchResult podcast;
podcast = adapter.getItem(position);
if (android.text.TextUtils.isEmpty(podcast.feedUrl)) {
return;
}
android.content.Intent intent;
switch(MUID_STATIC) {
// QuickFeedDiscoveryFragment_16_NullIntentOperatorMutator
case 16124: {
intent = null;
break;
}
// QuickFeedDiscoveryFragment_17_InvalidKeyIntentOperatorMutator
case 17124: {
intent = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, de.danoeh.antennapod.activity.OnlineFeedViewActivity.class);
break;
}
// QuickFeedDiscoveryFragment_18_RandomActionIntentDefinitionOperatorMutator
case 18124: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(getActivity(), de.danoeh.antennapod.activity.OnlineFeedViewActivity.class);
break;
}
}
switch(MUID_STATIC) {
// QuickFeedDiscoveryFragment_19_NullValueIntentPutExtraOperatorMutator
case 19124: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, new Parcelable[0]);
break;
}
// QuickFeedDiscoveryFragment_20_IntentPayloadReplacementOperatorMutator
case 20124: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, "");
break;
}
default: {
switch(MUID_STATIC) {
// QuickFeedDiscoveryFragment_21_RandomActionIntentDefinitionOperatorMutator
case 21124: {
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
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, podcast.feedUrl);
break;
}
}
break;
}
}
startActivity(intent);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
