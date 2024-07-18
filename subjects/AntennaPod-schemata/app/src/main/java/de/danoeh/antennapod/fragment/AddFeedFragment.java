package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.activity.OnlineFeedViewActivity;
import de.danoeh.antennapod.net.discovery.PodcastIndexPodcastSearcher;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.inputmethod.InputMethodManager;
import de.danoeh.antennapod.net.discovery.CombinedSearcher;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.databinding.EditTextDialogBinding;
import de.danoeh.antennapod.databinding.AddfeedBinding;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.activity.OpmlImportActivity;
import de.danoeh.antennapod.model.feed.Feed;
import java.util.Collections;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.util.Log;
import de.danoeh.antennapod.core.storage.DBTasks;
import de.danoeh.antennapod.net.discovery.GpodnetPodcastSearcher;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import de.danoeh.antennapod.model.feed.SortOrder;
import androidx.activity.result.contract.ActivityResultContracts;
import io.reactivex.Observable;
import android.content.Intent;
import de.danoeh.antennapod.net.discovery.ItunesPodcastSearcher;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.documentfile.provider.DocumentFile;
import android.view.View;
import android.content.ClipboardManager;
import androidx.activity.result.contract.ActivityResultContracts.GetContent;
import android.view.LayoutInflater;
import android.content.ActivityNotFoundException;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import de.danoeh.antennapod.net.discovery.FyydPodcastSearcher;
import android.content.ClipData;
import androidx.activity.result.ActivityResultLauncher;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.Nullable;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Provides actions for adding new podcast subscriptions.
 */
public class AddFeedFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "AddFeedFragment";

    private static final java.lang.String KEY_UP_ARROW = "up_arrow";

    private de.danoeh.antennapod.databinding.AddfeedBinding viewBinding;

    private de.danoeh.antennapod.activity.MainActivity activity;

    private boolean displayUpArrow;

    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> chooseOpmlImportPathLauncher = registerForActivityResult(new androidx.activity.result.contract.ActivityResultContracts.GetContent(), this::chooseOpmlImportPathResult);

    private final androidx.activity.result.ActivityResultLauncher<android.net.Uri> addLocalFolderLauncher = registerForActivityResult(new de.danoeh.antennapod.fragment.AddFeedFragment.AddLocalFolder(), this::addLocalFolderResult);

    @java.lang.Override
    @androidx.annotation.Nullable
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewBinding = de.danoeh.antennapod.databinding.AddfeedBinding.inflate(inflater);
        activity = ((de.danoeh.antennapod.activity.MainActivity) (getActivity()));
        displayUpArrow = getParentFragmentManager().getBackStackEntryCount() != 0;
        if (savedInstanceState != null) {
            displayUpArrow = savedInstanceState.getBoolean(de.danoeh.antennapod.fragment.AddFeedFragment.KEY_UP_ARROW);
        }
        ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).setupToolbarToggle(viewBinding.toolbar, displayUpArrow);
        switch(MUID_STATIC) {
            // AddFeedFragment_0_BuggyGUIListenerOperatorMutator
            case 134: {
                viewBinding.searchItunesButton.setOnClickListener(null);
                break;
            }
            default: {
            viewBinding.searchItunesButton.setOnClickListener((android.view.View v) -> activity.loadChildFragment(de.danoeh.antennapod.fragment.OnlineSearchFragment.newInstance(de.danoeh.antennapod.net.discovery.ItunesPodcastSearcher.class)));
            break;
        }
    }
    switch(MUID_STATIC) {
        // AddFeedFragment_1_BuggyGUIListenerOperatorMutator
        case 1134: {
            viewBinding.searchFyydButton.setOnClickListener(null);
            break;
        }
        default: {
        viewBinding.searchFyydButton.setOnClickListener((android.view.View v) -> activity.loadChildFragment(de.danoeh.antennapod.fragment.OnlineSearchFragment.newInstance(de.danoeh.antennapod.net.discovery.FyydPodcastSearcher.class)));
        break;
    }
}
switch(MUID_STATIC) {
    // AddFeedFragment_2_BuggyGUIListenerOperatorMutator
    case 2134: {
        viewBinding.searchGPodderButton.setOnClickListener(null);
        break;
    }
    default: {
    viewBinding.searchGPodderButton.setOnClickListener((android.view.View v) -> activity.loadChildFragment(de.danoeh.antennapod.fragment.OnlineSearchFragment.newInstance(de.danoeh.antennapod.net.discovery.GpodnetPodcastSearcher.class)));
    break;
}
}
switch(MUID_STATIC) {
// AddFeedFragment_3_BuggyGUIListenerOperatorMutator
case 3134: {
    viewBinding.searchPodcastIndexButton.setOnClickListener(null);
    break;
}
default: {
viewBinding.searchPodcastIndexButton.setOnClickListener((android.view.View v) -> activity.loadChildFragment(de.danoeh.antennapod.fragment.OnlineSearchFragment.newInstance(de.danoeh.antennapod.net.discovery.PodcastIndexPodcastSearcher.class)));
break;
}
}
viewBinding.combinedFeedSearchEditText.setOnEditorActionListener((android.widget.TextView v,int actionId,android.view.KeyEvent event) -> {
performSearch();
return true;
});
switch(MUID_STATIC) {
// AddFeedFragment_4_BuggyGUIListenerOperatorMutator
case 4134: {
viewBinding.addViaUrlButton.setOnClickListener(null);
break;
}
default: {
viewBinding.addViaUrlButton.setOnClickListener((android.view.View v) -> showAddViaUrlDialog());
break;
}
}
switch(MUID_STATIC) {
// AddFeedFragment_5_BuggyGUIListenerOperatorMutator
case 5134: {
viewBinding.opmlImportButton.setOnClickListener(null);
break;
}
default: {
viewBinding.opmlImportButton.setOnClickListener((android.view.View v) -> {
try {
chooseOpmlImportPathLauncher.launch("*/*");
} catch (android.content.ActivityNotFoundException e) {
e.printStackTrace();
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.unable_to_start_system_file_manager, com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
}
});
break;
}
}
switch(MUID_STATIC) {
// AddFeedFragment_6_BuggyGUIListenerOperatorMutator
case 6134: {
viewBinding.addLocalFolderButton.setOnClickListener(null);
break;
}
default: {
viewBinding.addLocalFolderButton.setOnClickListener((android.view.View v) -> {
try {
addLocalFolderLauncher.launch(null);
} catch (android.content.ActivityNotFoundException e) {
e.printStackTrace();
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.unable_to_start_system_file_manager, com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
}
});
break;
}
}
switch(MUID_STATIC) {
// AddFeedFragment_7_BuggyGUIListenerOperatorMutator
case 7134: {
viewBinding.searchButton.setOnClickListener(null);
break;
}
default: {
viewBinding.searchButton.setOnClickListener((android.view.View view) -> performSearch());
break;
}
}
return viewBinding.getRoot();
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
outState.putBoolean(de.danoeh.antennapod.fragment.AddFeedFragment.KEY_UP_ARROW, displayUpArrow);
super.onSaveInstanceState(outState);
}


private void showAddViaUrlDialog() {
com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
builder.setTitle(de.danoeh.antennapod.R.string.add_podcast_by_url);
final de.danoeh.antennapod.databinding.EditTextDialogBinding dialogBinding;
dialogBinding = de.danoeh.antennapod.databinding.EditTextDialogBinding.inflate(getLayoutInflater());
dialogBinding.urlEditText.setHint(de.danoeh.antennapod.R.string.add_podcast_by_url_hint);
android.content.ClipboardManager clipboard;
clipboard = ((android.content.ClipboardManager) (getContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
final android.content.ClipData clipData;
clipData = clipboard.getPrimaryClip();
if (((clipData != null) && (clipData.getItemCount() > 0)) && (clipData.getItemAt(0).getText() != null)) {
final java.lang.String clipboardContent;
clipboardContent = clipData.getItemAt(0).getText().toString();
if (clipboardContent.trim().startsWith("http")) {
dialogBinding.urlEditText.setText(clipboardContent.trim());
}
}
builder.setView(dialogBinding.getRoot());
switch(MUID_STATIC) {
// AddFeedFragment_8_BuggyGUIListenerOperatorMutator
case 8134: {
builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
break;
}
default: {
builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> addUrl(dialogBinding.urlEditText.getText().toString()));
break;
}
}
builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
builder.show();
}


private void addUrl(java.lang.String url) {
android.content.Intent intent;
switch(MUID_STATIC) {
// AddFeedFragment_9_NullIntentOperatorMutator
case 9134: {
intent = null;
break;
}
// AddFeedFragment_10_InvalidKeyIntentOperatorMutator
case 10134: {
intent = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, de.danoeh.antennapod.activity.OnlineFeedViewActivity.class);
break;
}
// AddFeedFragment_11_RandomActionIntentDefinitionOperatorMutator
case 11134: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(getActivity(), de.danoeh.antennapod.activity.OnlineFeedViewActivity.class);
break;
}
}
switch(MUID_STATIC) {
// AddFeedFragment_12_NullValueIntentPutExtraOperatorMutator
case 12134: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, new Parcelable[0]);
break;
}
// AddFeedFragment_13_IntentPayloadReplacementOperatorMutator
case 13134: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, "");
break;
}
default: {
switch(MUID_STATIC) {
// AddFeedFragment_14_RandomActionIntentDefinitionOperatorMutator
case 14134: {
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
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, url);
break;
}
}
break;
}
}
startActivity(intent);
}


private void performSearch() {
viewBinding.combinedFeedSearchEditText.clearFocus();
android.view.inputmethod.InputMethodManager in;
in = ((android.view.inputmethod.InputMethodManager) (getActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
in.hideSoftInputFromWindow(viewBinding.combinedFeedSearchEditText.getWindowToken(), 0);
java.lang.String query;
query = viewBinding.combinedFeedSearchEditText.getText().toString();
if (query.matches("http[s]?://.*")) {
addUrl(query);
return;
}
activity.loadChildFragment(de.danoeh.antennapod.fragment.OnlineSearchFragment.newInstance(de.danoeh.antennapod.net.discovery.CombinedSearcher.class, query));
viewBinding.combinedFeedSearchEditText.post(() -> viewBinding.combinedFeedSearchEditText.setText(""));
}


@java.lang.Override
public void onCreate(android.os.Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
switch(MUID_STATIC) {
// AddFeedFragment_15_LengthyGUICreationOperatorMutator
case 15134: {
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


private void chooseOpmlImportPathResult(final android.net.Uri uri) {
if (uri == null) {
return;
}
final android.content.Intent intent;
switch(MUID_STATIC) {
// AddFeedFragment_16_NullIntentOperatorMutator
case 16134: {
intent = null;
break;
}
// AddFeedFragment_17_InvalidKeyIntentOperatorMutator
case 17134: {
intent = new android.content.Intent((android.content.Context) null, de.danoeh.antennapod.activity.OpmlImportActivity.class);
break;
}
// AddFeedFragment_18_RandomActionIntentDefinitionOperatorMutator
case 18134: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(getContext(), de.danoeh.antennapod.activity.OpmlImportActivity.class);
break;
}
}
switch(MUID_STATIC) {
// AddFeedFragment_19_RandomActionIntentDefinitionOperatorMutator
case 19134: {
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
}


private void addLocalFolderResult(final android.net.Uri uri) {
if (uri == null) {
return;
}
io.reactivex.Observable.fromCallable(() -> addLocalFolder(uri)).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.feed.Feed feed) -> {
androidx.fragment.app.Fragment fragment;
fragment = de.danoeh.antennapod.fragment.FeedItemlistFragment.newInstance(feed.getId());
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(fragment);
}, (java.lang.Throwable error) -> {
android.util.Log.e(de.danoeh.antennapod.fragment.AddFeedFragment.TAG, android.util.Log.getStackTraceString(error));
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(error.getLocalizedMessage(), com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
});
}


private de.danoeh.antennapod.model.feed.Feed addLocalFolder(android.net.Uri uri) {
getActivity().getContentResolver().takePersistableUriPermission(uri, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION | android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
androidx.documentfile.provider.DocumentFile documentFile;
documentFile = androidx.documentfile.provider.DocumentFile.fromTreeUri(getContext(), uri);
if (documentFile == null) {
throw new java.lang.IllegalArgumentException("Unable to retrieve document tree");
}
java.lang.String title;
title = documentFile.getName();
if (title == null) {
title = getString(de.danoeh.antennapod.R.string.local_folder);
}
de.danoeh.antennapod.model.feed.Feed dirFeed;
dirFeed = new de.danoeh.antennapod.model.feed.Feed(de.danoeh.antennapod.model.feed.Feed.PREFIX_LOCAL_FOLDER + uri.toString(), null, title);
dirFeed.setItems(java.util.Collections.emptyList());
dirFeed.setSortOrder(de.danoeh.antennapod.model.feed.SortOrder.EPISODE_TITLE_A_Z);
de.danoeh.antennapod.model.feed.Feed fromDatabase;
fromDatabase = de.danoeh.antennapod.core.storage.DBTasks.updateFeed(getContext(), dirFeed, false);
de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnce(requireContext(), fromDatabase);
return fromDatabase;
}


private static class AddLocalFolder extends androidx.activity.result.contract.ActivityResultContracts.OpenDocumentTree {
@androidx.annotation.NonNull
@java.lang.Override
public android.content.Intent createIntent(@androidx.annotation.NonNull
final android.content.Context context, @androidx.annotation.Nullable
final android.net.Uri input) {
switch(MUID_STATIC) {
// AddFeedFragment_20_RandomActionIntentDefinitionOperatorMutator
case 20134: {
return new android.content.Intent(android.content.Intent.ACTION_SEND);
}
default: {
return super.createIntent(context, input).addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
}
}
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
