package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.activity.OnlineFeedViewActivity;
import java.util.ArrayList;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.inputmethod.InputMethodManager;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.net.discovery.PodcastSearcher;
import com.google.android.material.appbar.MaterialToolbar;
import de.danoeh.antennapod.net.discovery.PodcastSearcherRegistry;
import de.danoeh.antennapod.R;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import android.widget.ProgressBar;
import io.reactivex.disposables.Disposable;
import de.danoeh.antennapod.adapter.itunes.ItunesAdapter;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.LayoutInflater;
import androidx.appcompat.widget.SearchView;
import android.widget.GridView;
import de.danoeh.antennapod.net.discovery.PodcastSearchResult;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class OnlineSearchFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "FyydSearchFragment";

    private static final java.lang.String ARG_SEARCHER = "searcher";

    private static final java.lang.String ARG_QUERY = "query";

    /**
     * Adapter responsible with the search results
     */
    private de.danoeh.antennapod.adapter.itunes.ItunesAdapter adapter;

    private de.danoeh.antennapod.net.discovery.PodcastSearcher searchProvider;

    private android.widget.GridView gridView;

    private android.widget.ProgressBar progressBar;

    private android.widget.TextView txtvError;

    private android.widget.Button butRetry;

    private android.widget.TextView txtvEmpty;

    /**
     * List of podcasts retreived from the search
     */
    private java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> searchResults;

    private io.reactivex.disposables.Disposable disposable;

    public static de.danoeh.antennapod.fragment.OnlineSearchFragment newInstance(java.lang.Class<? extends de.danoeh.antennapod.net.discovery.PodcastSearcher> searchProvider) {
        return de.danoeh.antennapod.fragment.OnlineSearchFragment.newInstance(searchProvider, null);
    }


    public static de.danoeh.antennapod.fragment.OnlineSearchFragment newInstance(java.lang.Class<? extends de.danoeh.antennapod.net.discovery.PodcastSearcher> searchProvider, java.lang.String query) {
        de.danoeh.antennapod.fragment.OnlineSearchFragment fragment;
        fragment = new de.danoeh.antennapod.fragment.OnlineSearchFragment();
        android.os.Bundle arguments;
        arguments = new android.os.Bundle();
        arguments.putString(de.danoeh.antennapod.fragment.OnlineSearchFragment.ARG_SEARCHER, searchProvider.getName());
        arguments.putString(de.danoeh.antennapod.fragment.OnlineSearchFragment.ARG_QUERY, query);
        fragment.setArguments(arguments);
        return fragment;
    }


    /**
     * Constructor
     */
    public OnlineSearchFragment() {
        // Required empty public constructor
    }


    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // OnlineSearchFragment_0_LengthyGUICreationOperatorMutator
            case 127: {
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
    for (de.danoeh.antennapod.net.discovery.PodcastSearcherRegistry.SearcherInfo info : de.danoeh.antennapod.net.discovery.PodcastSearcherRegistry.getSearchProviders()) {
        if (info.searcher.getClass().getName().equals(getArguments().getString(de.danoeh.antennapod.fragment.OnlineSearchFragment.ARG_SEARCHER))) {
            searchProvider = info.searcher;
            break;
        }
    }
    if (searchProvider == null) {
        throw new java.lang.IllegalArgumentException("Podcast searcher not found");
    }
}


@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    android.view.View root;
    root = inflater.inflate(de.danoeh.antennapod.R.layout.fragment_itunes_search, container, false);
    switch(MUID_STATIC) {
        // OnlineSearchFragment_1_InvalidViewFocusOperatorMutator
        case 1127: {
            /**
            * Inserted by Kadabra
            */
            gridView = root.findViewById(de.danoeh.antennapod.R.id.gridView);
            gridView.requestFocus();
            break;
        }
        // OnlineSearchFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2127: {
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
// Show information about the podcast when the list item is clicked
gridView.setOnItemClickListener((android.widget.AdapterView<?> parent,android.view.View view1,int position,long id) -> {
    de.danoeh.antennapod.net.discovery.PodcastSearchResult podcast;
    podcast = searchResults.get(position);
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // OnlineSearchFragment_3_InvalidKeyIntentOperatorMutator
        case 3127: {
            intent = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, de.danoeh.antennapod.activity.OnlineFeedViewActivity.class);
            break;
        }
        // OnlineSearchFragment_4_RandomActionIntentDefinitionOperatorMutator
        case 4127: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent(getActivity(), de.danoeh.antennapod.activity.OnlineFeedViewActivity.class);
        break;
    }
}
switch(MUID_STATIC) {
    // OnlineSearchFragment_5_NullValueIntentPutExtraOperatorMutator
    case 5127: {
        intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, new Parcelable[0]);
        break;
    }
    // OnlineSearchFragment_6_IntentPayloadReplacementOperatorMutator
    case 6127: {
        intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, "");
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // OnlineSearchFragment_7_RandomActionIntentDefinitionOperatorMutator
        case 7127: {
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
switch(MUID_STATIC) {
// OnlineSearchFragment_8_NullValueIntentPutExtraOperatorMutator
case 8127: {
intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_STARTED_FROM_SEARCH, new Parcelable[0]);
break;
}
// OnlineSearchFragment_9_IntentPayloadReplacementOperatorMutator
case 9127: {
intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_STARTED_FROM_SEARCH, true);
break;
}
default: {
switch(MUID_STATIC) {
// OnlineSearchFragment_10_RandomActionIntentDefinitionOperatorMutator
case 10127: {
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
intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_STARTED_FROM_SEARCH, true);
break;
}
}
break;
}
}
startActivity(intent);
});
switch(MUID_STATIC) {
// OnlineSearchFragment_11_InvalidViewFocusOperatorMutator
case 11127: {
/**
* Inserted by Kadabra
*/
progressBar = root.findViewById(de.danoeh.antennapod.R.id.progressBar);
progressBar.requestFocus();
break;
}
// OnlineSearchFragment_12_ViewComponentNotVisibleOperatorMutator
case 12127: {
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
// OnlineSearchFragment_13_InvalidViewFocusOperatorMutator
case 13127: {
/**
* Inserted by Kadabra
*/
txtvError = root.findViewById(de.danoeh.antennapod.R.id.txtvError);
txtvError.requestFocus();
break;
}
// OnlineSearchFragment_14_ViewComponentNotVisibleOperatorMutator
case 14127: {
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
// OnlineSearchFragment_15_InvalidViewFocusOperatorMutator
case 15127: {
/**
* Inserted by Kadabra
*/
butRetry = root.findViewById(de.danoeh.antennapod.R.id.butRetry);
butRetry.requestFocus();
break;
}
// OnlineSearchFragment_16_ViewComponentNotVisibleOperatorMutator
case 16127: {
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
// OnlineSearchFragment_17_InvalidViewFocusOperatorMutator
case 17127: {
/**
* Inserted by Kadabra
*/
txtvEmpty = root.findViewById(android.R.id.empty);
txtvEmpty.requestFocus();
break;
}
// OnlineSearchFragment_18_ViewComponentNotVisibleOperatorMutator
case 18127: {
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
android.widget.TextView txtvPoweredBy;
switch(MUID_STATIC) {
// OnlineSearchFragment_19_InvalidViewFocusOperatorMutator
case 19127: {
/**
* Inserted by Kadabra
*/
txtvPoweredBy = root.findViewById(de.danoeh.antennapod.R.id.search_powered_by);
txtvPoweredBy.requestFocus();
break;
}
// OnlineSearchFragment_20_ViewComponentNotVisibleOperatorMutator
case 20127: {
/**
* Inserted by Kadabra
*/
txtvPoweredBy = root.findViewById(de.danoeh.antennapod.R.id.search_powered_by);
txtvPoweredBy.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvPoweredBy = root.findViewById(de.danoeh.antennapod.R.id.search_powered_by);
break;
}
}
txtvPoweredBy.setText(getString(de.danoeh.antennapod.R.string.search_powered_by, searchProvider.getName()));
setupToolbar(root.findViewById(de.danoeh.antennapod.R.id.toolbar));
gridView.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {
@java.lang.Override
public void onScrollStateChanged(android.widget.AbsListView view, int scrollState) {
if (scrollState == android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (getActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
}
}


@java.lang.Override
public void onScroll(android.widget.AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
}

});
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


private void setupToolbar(com.google.android.material.appbar.MaterialToolbar toolbar) {
toolbar.inflateMenu(de.danoeh.antennapod.R.menu.online_search);
switch(MUID_STATIC) {
// OnlineSearchFragment_21_BuggyGUIListenerOperatorMutator
case 21127: {
toolbar.setNavigationOnClickListener(null);
break;
}
default: {
toolbar.setNavigationOnClickListener((android.view.View v) -> getParentFragmentManager().popBackStack());
break;
}
}
android.view.MenuItem searchItem;
searchItem = toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.action_search);
final androidx.appcompat.widget.SearchView sv;
sv = ((androidx.appcompat.widget.SearchView) (searchItem.getActionView()));
sv.setQueryHint(getString(de.danoeh.antennapod.R.string.search_podcast_hint));
sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
@java.lang.Override
public boolean onQueryTextSubmit(java.lang.String s) {
sv.clearFocus();
search(s);
return true;
}


@java.lang.Override
public boolean onQueryTextChange(java.lang.String s) {
return false;
}

});
sv.setOnQueryTextFocusChangeListener((android.view.View view,boolean hasFocus) -> {
if (hasFocus) {
showInputMethod(view.findFocus());
}
});
searchItem.setOnActionExpandListener(new android.view.MenuItem.OnActionExpandListener() {
@java.lang.Override
public boolean onMenuItemActionExpand(android.view.MenuItem item) {
return true;
}


@java.lang.Override
public boolean onMenuItemActionCollapse(android.view.MenuItem item) {
getActivity().getSupportFragmentManager().popBackStack();
return true;
}

});
searchItem.expandActionView();
if (getArguments().getString(de.danoeh.antennapod.fragment.OnlineSearchFragment.ARG_QUERY, null) != null) {
sv.setQuery(getArguments().getString(de.danoeh.antennapod.fragment.OnlineSearchFragment.ARG_QUERY, null), true);
}
}


private void search(java.lang.String query) {
if (disposable != null) {
disposable.dispose();
}
showOnlyProgressBar();
disposable = searchProvider.search(query).subscribe((java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> result) -> {
searchResults = result;
progressBar.setVisibility(android.view.View.GONE);
adapter.clear();
adapter.addAll(searchResults);
adapter.notifyDataSetInvalidated();
gridView.setVisibility(!searchResults.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
txtvEmpty.setVisibility(searchResults.isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
txtvEmpty.setText(getString(de.danoeh.antennapod.R.string.no_results_for_query, query));
}, (java.lang.Throwable error) -> {
android.util.Log.e(de.danoeh.antennapod.fragment.OnlineSearchFragment.TAG, android.util.Log.getStackTraceString(error));
progressBar.setVisibility(android.view.View.GONE);
txtvError.setText(error.toString());
txtvError.setVisibility(android.view.View.VISIBLE);
switch(MUID_STATIC) {
// OnlineSearchFragment_22_BuggyGUIListenerOperatorMutator
case 22127: {
butRetry.setOnClickListener(null);
break;
}
default: {
butRetry.setOnClickListener((android.view.View v) -> search(query));
break;
}
}
butRetry.setVisibility(android.view.View.VISIBLE);
});
}


private void showOnlyProgressBar() {
gridView.setVisibility(android.view.View.GONE);
txtvError.setVisibility(android.view.View.GONE);
butRetry.setVisibility(android.view.View.GONE);
txtvEmpty.setVisibility(android.view.View.GONE);
progressBar.setVisibility(android.view.View.VISIBLE);
}


private void showInputMethod(android.view.View view) {
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (getActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
if (imm != null) {
imm.showSoftInput(view, 0);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
