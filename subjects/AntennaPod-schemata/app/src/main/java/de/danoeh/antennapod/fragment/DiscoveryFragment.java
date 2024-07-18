package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.activity.OnlineFeedViewActivity;
import java.util.Locale;
import java.util.HashMap;
import de.danoeh.antennapod.event.DiscoveryDefaultUpdateEvent;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.net.discovery.ItunesTopListLoader;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import de.danoeh.antennapod.R;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import de.danoeh.antennapod.core.BuildConfig;
import java.util.List;
import android.widget.ProgressBar;
import java.util.Collections;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.content.SharedPreferences;
import de.danoeh.antennapod.adapter.itunes.ItunesAdapter;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import android.content.Intent;
import android.view.MenuItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import android.widget.GridView;
import de.danoeh.antennapod.net.discovery.PodcastSearchResult;
import java.util.Map;
import java.util.Arrays;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Searches iTunes store for top podcasts and displays results in a list.
 */
public class DiscoveryFragment extends androidx.fragment.app.Fragment implements androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "ItunesSearchFragment";

    private static final int NUM_OF_TOP_PODCASTS = 25;

    private android.content.SharedPreferences prefs;

    /**
     * Adapter responsible with the search results.
     */
    private de.danoeh.antennapod.adapter.itunes.ItunesAdapter adapter;

    private android.widget.GridView gridView;

    private android.widget.ProgressBar progressBar;

    private android.widget.TextView txtvError;

    private android.widget.Button butRetry;

    private android.widget.TextView txtvEmpty;

    /**
     * List of podcasts retreived from the search.
     */
    private java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> searchResults;

    private java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> topList;

    private io.reactivex.disposables.Disposable disposable;

    private java.lang.String countryCode = "US";

    private boolean hidden;

    private boolean needsConfirm;

    private com.google.android.material.appbar.MaterialToolbar toolbar;

    public DiscoveryFragment() {
        // Required empty public constructor
    }


    /**
     * Replace adapter data with provided search results from SearchTask.
     *
     * @param result
     * 		List of Podcast objects containing search results
     */
    private void updateData(java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> result) {
        this.searchResults = result;
        adapter.clear();
        if ((result != null) && (result.size() > 0)) {
            gridView.setVisibility(android.view.View.VISIBLE);
            txtvEmpty.setVisibility(android.view.View.GONE);
            for (de.danoeh.antennapod.net.discovery.PodcastSearchResult p : result) {
                adapter.add(p);
            }
            adapter.notifyDataSetInvalidated();
        } else {
            gridView.setVisibility(android.view.View.GONE);
            txtvEmpty.setVisibility(android.view.View.VISIBLE);
        }
    }


    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // DiscoveryFragment_0_LengthyGUICreationOperatorMutator
            case 141: {
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
    prefs = getActivity().getSharedPreferences(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREFS, android.content.Context.MODE_PRIVATE);
    countryCode = prefs.getString(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_COUNTRY_CODE, java.util.Locale.getDefault().getCountry());
    hidden = prefs.getBoolean(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_HIDDEN_DISCOVERY_COUNTRY, false);
    needsConfirm = prefs.getBoolean(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_NEEDS_CONFIRM, true);
}


@java.lang.Override
public android.view.View onCreateView(@androidx.annotation.NonNull
android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    android.view.View root;
    root = inflater.inflate(de.danoeh.antennapod.R.layout.fragment_itunes_search, container, false);
    switch(MUID_STATIC) {
        // DiscoveryFragment_1_InvalidViewFocusOperatorMutator
        case 1141: {
            /**
            * Inserted by Kadabra
            */
            gridView = root.findViewById(de.danoeh.antennapod.R.id.gridView);
            gridView.requestFocus();
            break;
        }
        // DiscoveryFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2141: {
            /**
            * Inserted by Kadabra
            */
            gridView = root.findViewById(de.danoeh.antennapod.R.id.gridView);
            gridView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        gridView = root.findViewById(de.danoeh.antennapod.R.id.gridView);
        break;
    }
}
adapter = new de.danoeh.antennapod.adapter.itunes.ItunesAdapter(getActivity(), new java.util.ArrayList<>());
gridView.setAdapter(adapter);
switch(MUID_STATIC) {
    // DiscoveryFragment_3_InvalidViewFocusOperatorMutator
    case 3141: {
        /**
        * Inserted by Kadabra
        */
        toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
        toolbar.requestFocus();
        break;
    }
    // DiscoveryFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4141: {
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
// DiscoveryFragment_5_BuggyGUIListenerOperatorMutator
case 5141: {
    toolbar.setNavigationOnClickListener(null);
    break;
}
default: {
toolbar.setNavigationOnClickListener((android.view.View v) -> getParentFragmentManager().popBackStack());
break;
}
}
toolbar.inflateMenu(de.danoeh.antennapod.R.menu.countries_menu);
android.view.MenuItem discoverHideItem;
discoverHideItem = toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.discover_hide_item);
discoverHideItem.setChecked(hidden);
toolbar.setOnMenuItemClickListener(this);
// Show information about the podcast when the list item is clicked
gridView.setOnItemClickListener((android.widget.AdapterView<?> parent,android.view.View view1,int position,long id) -> {
de.danoeh.antennapod.net.discovery.PodcastSearchResult podcast;
podcast = searchResults.get(position);
if (podcast.feedUrl == null) {
return;
}
android.content.Intent intent;
switch(MUID_STATIC) {
// DiscoveryFragment_6_InvalidKeyIntentOperatorMutator
case 6141: {
    intent = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, de.danoeh.antennapod.activity.OnlineFeedViewActivity.class);
    break;
}
// DiscoveryFragment_7_RandomActionIntentDefinitionOperatorMutator
case 7141: {
    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent = new android.content.Intent(getActivity(), de.danoeh.antennapod.activity.OnlineFeedViewActivity.class);
break;
}
}
switch(MUID_STATIC) {
// DiscoveryFragment_8_NullValueIntentPutExtraOperatorMutator
case 8141: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, new Parcelable[0]);
break;
}
// DiscoveryFragment_9_IntentPayloadReplacementOperatorMutator
case 9141: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, "");
break;
}
default: {
switch(MUID_STATIC) {
// DiscoveryFragment_10_RandomActionIntentDefinitionOperatorMutator
case 10141: {
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
});
switch(MUID_STATIC) {
// DiscoveryFragment_11_InvalidViewFocusOperatorMutator
case 11141: {
/**
* Inserted by Kadabra
*/
progressBar = root.findViewById(de.danoeh.antennapod.R.id.progressBar);
progressBar.requestFocus();
break;
}
// DiscoveryFragment_12_ViewComponentNotVisibleOperatorMutator
case 12141: {
/**
* Inserted by Kadabra
*/
progressBar = root.findViewById(de.danoeh.antennapod.R.id.progressBar);
progressBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progressBar = root.findViewById(de.danoeh.antennapod.R.id.progressBar);
break;
}
}
switch(MUID_STATIC) {
// DiscoveryFragment_13_InvalidViewFocusOperatorMutator
case 13141: {
/**
* Inserted by Kadabra
*/
txtvError = root.findViewById(de.danoeh.antennapod.R.id.txtvError);
txtvError.requestFocus();
break;
}
// DiscoveryFragment_14_ViewComponentNotVisibleOperatorMutator
case 14141: {
/**
* Inserted by Kadabra
*/
txtvError = root.findViewById(de.danoeh.antennapod.R.id.txtvError);
txtvError.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvError = root.findViewById(de.danoeh.antennapod.R.id.txtvError);
break;
}
}
switch(MUID_STATIC) {
// DiscoveryFragment_15_InvalidViewFocusOperatorMutator
case 15141: {
/**
* Inserted by Kadabra
*/
butRetry = root.findViewById(de.danoeh.antennapod.R.id.butRetry);
butRetry.requestFocus();
break;
}
// DiscoveryFragment_16_ViewComponentNotVisibleOperatorMutator
case 16141: {
/**
* Inserted by Kadabra
*/
butRetry = root.findViewById(de.danoeh.antennapod.R.id.butRetry);
butRetry.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butRetry = root.findViewById(de.danoeh.antennapod.R.id.butRetry);
break;
}
}
switch(MUID_STATIC) {
// DiscoveryFragment_17_InvalidViewFocusOperatorMutator
case 17141: {
/**
* Inserted by Kadabra
*/
txtvEmpty = root.findViewById(android.R.id.empty);
txtvEmpty.requestFocus();
break;
}
// DiscoveryFragment_18_ViewComponentNotVisibleOperatorMutator
case 18141: {
/**
* Inserted by Kadabra
*/
txtvEmpty = root.findViewById(android.R.id.empty);
txtvEmpty.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvEmpty = root.findViewById(android.R.id.empty);
break;
}
}
loadToplist(countryCode);
return root;
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
if (disposable != null) {
disposable.dispose();
}
adapter = null;
}


private void loadToplist(java.lang.String country) {
if (disposable != null) {
disposable.dispose();
}
gridView.setVisibility(android.view.View.GONE);
txtvError.setVisibility(android.view.View.GONE);
butRetry.setVisibility(android.view.View.GONE);
butRetry.setText(de.danoeh.antennapod.R.string.retry_label);
txtvEmpty.setVisibility(android.view.View.GONE);
progressBar.setVisibility(android.view.View.VISIBLE);
if (hidden) {
gridView.setVisibility(android.view.View.GONE);
txtvError.setVisibility(android.view.View.VISIBLE);
txtvError.setText(getResources().getString(de.danoeh.antennapod.R.string.discover_is_hidden));
butRetry.setVisibility(android.view.View.GONE);
txtvEmpty.setVisibility(android.view.View.GONE);
progressBar.setVisibility(android.view.View.GONE);
return;
}
// noinspection ConstantConditions
if (de.danoeh.antennapod.core.BuildConfig.FLAVOR.equals("free") && needsConfirm) {
txtvError.setVisibility(android.view.View.VISIBLE);
txtvError.setText("");
butRetry.setVisibility(android.view.View.VISIBLE);
butRetry.setText(de.danoeh.antennapod.R.string.discover_confirm);
switch(MUID_STATIC) {
// DiscoveryFragment_19_BuggyGUIListenerOperatorMutator
case 19141: {
butRetry.setOnClickListener(null);
break;
}
default: {
butRetry.setOnClickListener((android.view.View v) -> {
prefs.edit().putBoolean(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_NEEDS_CONFIRM, false).apply();
needsConfirm = false;
loadToplist(country);
});
break;
}
}
txtvEmpty.setVisibility(android.view.View.GONE);
progressBar.setVisibility(android.view.View.GONE);
return;
}
de.danoeh.antennapod.net.discovery.ItunesTopListLoader loader;
loader = new de.danoeh.antennapod.net.discovery.ItunesTopListLoader(getContext());
disposable = io.reactivex.Observable.fromCallable(() -> loader.loadToplist(country, de.danoeh.antennapod.fragment.DiscoveryFragment.NUM_OF_TOP_PODCASTS, de.danoeh.antennapod.core.storage.DBReader.getFeedList())).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> podcasts) -> {
progressBar.setVisibility(android.view.View.GONE);
topList = podcasts;
updateData(topList);
}, (java.lang.Throwable error) -> {
android.util.Log.e(de.danoeh.antennapod.fragment.DiscoveryFragment.TAG, android.util.Log.getStackTraceString(error));
progressBar.setVisibility(android.view.View.GONE);
txtvError.setText(error.getMessage());
txtvError.setVisibility(android.view.View.VISIBLE);
switch(MUID_STATIC) {
// DiscoveryFragment_20_BuggyGUIListenerOperatorMutator
case 20141: {
butRetry.setOnClickListener(null);
break;
}
default: {
butRetry.setOnClickListener((android.view.View v) -> loadToplist(country));
break;
}
}
butRetry.setVisibility(android.view.View.VISIBLE);
});
}


@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem item) {
if (super.onOptionsItemSelected(item)) {
return true;
}
final int itemId;
itemId = item.getItemId();
if (itemId == de.danoeh.antennapod.R.id.discover_hide_item) {
item.setChecked(!item.isChecked());
hidden = item.isChecked();
prefs.edit().putBoolean(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_HIDDEN_DISCOVERY_COUNTRY, hidden).apply();
org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.DiscoveryDefaultUpdateEvent());
loadToplist(countryCode);
return true;
} else if (itemId == de.danoeh.antennapod.R.id.discover_countries_item) {
android.view.LayoutInflater inflater;
inflater = getLayoutInflater();
android.view.View selectCountryDialogView;
selectCountryDialogView = inflater.inflate(de.danoeh.antennapod.R.layout.select_country_dialog, null);
com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
builder.setView(selectCountryDialogView);
java.util.List<java.lang.String> countryCodeArray;
countryCodeArray = new java.util.ArrayList<>(java.util.Arrays.asList(java.util.Locale.getISOCountries()));
java.util.Map<java.lang.String, java.lang.String> countryCodeNames;
countryCodeNames = new java.util.HashMap<>();
java.util.Map<java.lang.String, java.lang.String> countryNameCodes;
countryNameCodes = new java.util.HashMap<>();
for (java.lang.String code : countryCodeArray) {
java.util.Locale locale;
locale = new java.util.Locale("", code);
java.lang.String countryName;
countryName = locale.getDisplayCountry();
countryCodeNames.put(code, countryName);
countryNameCodes.put(countryName, code);
}
java.util.List<java.lang.String> countryNamesSort;
countryNamesSort = new java.util.ArrayList<>(countryCodeNames.values());
java.util.Collections.sort(countryNamesSort);
android.widget.ArrayAdapter<java.lang.String> dataAdapter;
dataAdapter = new android.widget.ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, countryNamesSort);
com.google.android.material.textfield.TextInputLayout textInput;
switch(MUID_STATIC) {
// DiscoveryFragment_21_InvalidViewFocusOperatorMutator
case 21141: {
/**
* Inserted by Kadabra
*/
textInput = selectCountryDialogView.findViewById(de.danoeh.antennapod.R.id.country_text_input);
textInput.requestFocus();
break;
}
// DiscoveryFragment_22_ViewComponentNotVisibleOperatorMutator
case 22141: {
/**
* Inserted by Kadabra
*/
textInput = selectCountryDialogView.findViewById(de.danoeh.antennapod.R.id.country_text_input);
textInput.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
textInput = selectCountryDialogView.findViewById(de.danoeh.antennapod.R.id.country_text_input);
break;
}
}
com.google.android.material.textfield.MaterialAutoCompleteTextView editText;
editText = ((com.google.android.material.textfield.MaterialAutoCompleteTextView) (textInput.getEditText()));
editText.setAdapter(dataAdapter);
editText.setText(countryCodeNames.get(countryCode));
switch(MUID_STATIC) {
// DiscoveryFragment_23_BuggyGUIListenerOperatorMutator
case 23141: {
editText.setOnClickListener(null);
break;
}
default: {
editText.setOnClickListener((android.view.View view) -> {
if (editText.getText().length() != 0) {
editText.setText("");
editText.postDelayed(editText::showDropDown, 100);
}
});
break;
}
}
editText.setOnFocusChangeListener((android.view.View v,boolean hasFocus) -> {
if (hasFocus) {
editText.setText("");
editText.postDelayed(editText::showDropDown, 100);
}
});
switch(MUID_STATIC) {
// DiscoveryFragment_24_BuggyGUIListenerOperatorMutator
case 24141: {
builder.setPositiveButton(android.R.string.ok, null);
break;
}
default: {
builder.setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialogInterface,int i) -> {
java.lang.String countryName;
countryName = editText.getText().toString();
if (countryNameCodes.containsKey(countryName)) {
countryCode = countryNameCodes.get(countryName);
android.view.MenuItem discoverHideItem;
discoverHideItem = toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.discover_hide_item);
discoverHideItem.setChecked(false);
hidden = false;
}
prefs.edit().putBoolean(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_HIDDEN_DISCOVERY_COUNTRY, hidden).apply();
prefs.edit().putString(de.danoeh.antennapod.net.discovery.ItunesTopListLoader.PREF_KEY_COUNTRY_CODE, countryCode).apply();
org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.DiscoveryDefaultUpdateEvent());
loadToplist(countryCode);
});
break;
}
}
builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
builder.show();
return true;
}
return false;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
