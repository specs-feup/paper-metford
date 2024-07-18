package de.danoeh.antennapod.activity;
import de.danoeh.antennapod.event.EpisodeDownloadEvent;
import de.danoeh.antennapod.parser.feed.UnsupportedFeedtypeException;
import de.danoeh.antennapod.model.playback.RemoteMedia;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import java.util.ArrayList;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import de.danoeh.antennapod.core.service.download.Downloader;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.databinding.OnlinefeedviewActivityBinding;
import org.greenrobot.eventbus.Subscribe;
import de.danoeh.antennapod.net.discovery.CombinedSearcher;
import de.danoeh.antennapod.core.service.download.DownloadRequestCreator;
import de.danoeh.antennapod.adapter.FeedItemlistDescriptionAdapter;
import androidx.annotation.NonNull;
import android.app.Dialog;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import com.bumptech.glide.Glide;
import de.danoeh.antennapod.model.feed.Feed;
import de.danoeh.antennapod.core.feed.FeedUrlNotFoundException;
import io.reactivex.Maybe;
import android.util.Log;
import de.danoeh.antennapod.core.storage.DBTasks;
import android.text.style.ForegroundColorSpan;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadRequest;
import android.graphics.LightingColorFilter;
import androidx.annotation.UiThread;
import de.danoeh.antennapod.parser.feed.FeedHandlerResult;
import de.danoeh.antennapod.core.util.syndication.FeedDiscoverer;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.text.SpannableString;
import de.danoeh.antennapod.core.preferences.PlaybackPreferences;
import de.danoeh.antennapod.core.util.IntentUtils;
import de.danoeh.antennapod.net.common.UrlChecker;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.databinding.OnlinefeedviewHeaderBinding;
import android.widget.AdapterView;
import com.google.android.material.snackbar.Snackbar;
import de.danoeh.antennapod.net.discovery.PodcastSearchResult;
import java.io.File;
import java.util.Map;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.model.feed.FeedPreferences;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import org.greenrobot.eventbus.ThreadMode;
import android.content.DialogInterface;
import de.danoeh.antennapod.core.preferences.ThemeSwitcher;
import de.danoeh.antennapod.core.util.syndication.HtmlToPlainText;
import de.danoeh.antennapod.event.PlayerStatusEvent;
import org.greenrobot.eventbus.EventBus;
import de.danoeh.antennapod.model.download.DownloadResult;
import de.danoeh.antennapod.core.util.DownloadErrorLabel;
import de.danoeh.antennapod.net.discovery.PodcastSearcherRegistry;
import de.danoeh.antennapod.ui.glide.FastBlurTransformation;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.parser.feed.FeedHandler;
import de.danoeh.antennapod.core.service.download.HttpDownloader;
import de.danoeh.antennapod.model.download.DownloadError;
import org.apache.commons.lang3.StringUtils;
import android.widget.Toast;
import de.danoeh.antennapod.event.FeedListUpdateEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.content.SharedPreferences;
import de.danoeh.antennapod.core.service.playback.PlaybackServiceInterface;
import android.os.Bundle;
import android.view.ViewGroup;
import de.danoeh.antennapod.dialog.AuthenticationDialog;
import io.reactivex.schedulers.Schedulers;
import android.text.TextUtils;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import androidx.core.app.NavUtils;
import io.reactivex.observers.DisposableMaybeObserver;
import android.content.Context;
import android.text.Spannable;
import android.widget.ArrayAdapter;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Downloads a feed from a feed URL and parses it. Subclasses can display the
 * feed object that was parsed. This activity MUST be started with a given URL
 * or an Exception will be thrown.
 * <p/>
 * If the feed cannot be downloaded or parsed, an error dialog will be displayed
 * and the activity will finish as soon as the error dialog is closed.
 */
public class OnlineFeedViewActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ARG_FEEDURL = "arg.feedurl";

    // Optional argument: specify a title for the actionbar.
    private static final int RESULT_ERROR = 2;

    private static final java.lang.String TAG = "OnlineFeedViewActivity";

    private static final java.lang.String PREFS = "OnlineFeedViewActivityPreferences";

    private static final java.lang.String PREF_LAST_AUTO_DOWNLOAD = "lastAutoDownload";

    private static final int DESCRIPTION_MAX_LINES_COLLAPSED = 4;

    private volatile java.util.List<de.danoeh.antennapod.model.feed.Feed> feeds;

    private java.lang.String selectedDownloadUrl;

    private de.danoeh.antennapod.core.service.download.Downloader downloader;

    private java.lang.String username = null;

    private java.lang.String password = null;

    private boolean isPaused;

    private boolean didPressSubscribe = false;

    private boolean isFeedFoundBySearch = false;

    private android.app.Dialog dialog;

    private io.reactivex.disposables.Disposable download;

    private io.reactivex.disposables.Disposable parser;

    private io.reactivex.disposables.Disposable updater;

    private de.danoeh.antennapod.databinding.OnlinefeedviewHeaderBinding headerBinding;

    private de.danoeh.antennapod.databinding.OnlinefeedviewActivityBinding viewBinding;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        setTheme(de.danoeh.antennapod.core.preferences.ThemeSwitcher.getTranslucentTheme(this));
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // OnlineFeedViewActivity_0_LengthyGUICreationOperatorMutator
            case 150: {
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
    viewBinding = de.danoeh.antennapod.databinding.OnlinefeedviewActivityBinding.inflate(getLayoutInflater());
    setContentView(viewBinding.getRoot());
    switch(MUID_STATIC) {
        // OnlineFeedViewActivity_1_BuggyGUIListenerOperatorMutator
        case 1150: {
            viewBinding.transparentBackground.setOnClickListener(null);
            break;
        }
        default: {
        viewBinding.transparentBackground.setOnClickListener((android.view.View v) -> finish());
        break;
    }
}
switch(MUID_STATIC) {
    // OnlineFeedViewActivity_2_BuggyGUIListenerOperatorMutator
    case 2150: {
        viewBinding.closeButton.setOnClickListener(null);
        break;
    }
    default: {
    viewBinding.closeButton.setOnClickListener((android.view.View view) -> finish());
    break;
}
}
viewBinding.card.setOnClickListener(null);
viewBinding.card.setCardBackgroundColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(this, de.danoeh.antennapod.R.attr.colorSurface));
headerBinding = de.danoeh.antennapod.databinding.OnlinefeedviewHeaderBinding.inflate(getLayoutInflater());
java.lang.String feedUrl;
feedUrl = null;
if (getIntent().hasExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL)) {
feedUrl = getIntent().getStringExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL);
} else if (android.text.TextUtils.equals(getIntent().getAction(), android.content.Intent.ACTION_SEND)) {
feedUrl = getIntent().getStringExtra(android.content.Intent.EXTRA_TEXT);
} else if (android.text.TextUtils.equals(getIntent().getAction(), android.content.Intent.ACTION_VIEW)) {
feedUrl = getIntent().getDataString();
}
if (feedUrl == null) {
android.util.Log.e(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, "feedUrl is null.");
showNoPodcastFoundError();
} else {
android.util.Log.d(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, "Activity was started with url " + feedUrl);
setLoadingLayout();
// Remove subscribeonandroid.com from feed URL in order to subscribe to the actual feed URL
if (feedUrl.contains("subscribeonandroid.com")) {
    feedUrl = feedUrl.replaceFirst("((www.)?(subscribeonandroid.com/))", "");
}
if (savedInstanceState != null) {
    username = savedInstanceState.getString("username");
    password = savedInstanceState.getString("password");
}
lookupUrlAndDownload(feedUrl);
}
}


private void showNoPodcastFoundError() {
switch(MUID_STATIC) {
// OnlineFeedViewActivity_3_BuggyGUIListenerOperatorMutator
case 3150: {
    runOnUiThread(() -> new com.google.android.material.dialog.MaterialAlertDialogBuilder(this).setNeutralButton(android.R.string.ok, null).setTitle(de.danoeh.antennapod.R.string.error_label).setMessage(de.danoeh.antennapod.R.string.null_value_podcast_error).setOnDismissListener((android.content.DialogInterface dialog1) -> {
        setResult(de.danoeh.antennapod.activity.OnlineFeedViewActivity.RESULT_ERROR);
        finish();
    }).show());
    break;
}
default: {
runOnUiThread(() -> new com.google.android.material.dialog.MaterialAlertDialogBuilder(this).setNeutralButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> finish()).setTitle(de.danoeh.antennapod.R.string.error_label).setMessage(de.danoeh.antennapod.R.string.null_value_podcast_error).setOnDismissListener((android.content.DialogInterface dialog1) -> {
    setResult(de.danoeh.antennapod.activity.OnlineFeedViewActivity.RESULT_ERROR);
    finish();
}).show());
break;
}
}
}


/**
 * Displays a progress indicator.
 */
private void setLoadingLayout() {
viewBinding.progressBar.setVisibility(android.view.View.VISIBLE);
viewBinding.feedDisplayContainer.setVisibility(android.view.View.GONE);
}


@java.lang.Override
protected void onStart() {
super.onStart();
isPaused = false;
org.greenrobot.eventbus.EventBus.getDefault().register(this);
}


@java.lang.Override
protected void onStop() {
super.onStop();
isPaused = true;
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
if ((downloader != null) && (!downloader.isFinished())) {
downloader.cancel();
}
if ((dialog != null) && dialog.isShowing()) {
dialog.dismiss();
}
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
if (updater != null) {
updater.dispose();
}
if (download != null) {
download.dispose();
}
if (parser != null) {
parser.dispose();
}
}


@java.lang.Override
protected void onSaveInstanceState(android.os.Bundle outState) {
super.onSaveInstanceState(outState);
outState.putString("username", username);
outState.putString("password", password);
}


private void resetIntent(java.lang.String url) {
android.content.Intent intent;
switch(MUID_STATIC) {
// OnlineFeedViewActivity_4_NullIntentOperatorMutator
case 4150: {
intent = null;
break;
}
// OnlineFeedViewActivity_5_RandomActionIntentDefinitionOperatorMutator
case 5150: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// OnlineFeedViewActivity_6_NullValueIntentPutExtraOperatorMutator
case 6150: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, new Parcelable[0]);
break;
}
// OnlineFeedViewActivity_7_IntentPayloadReplacementOperatorMutator
case 7150: {
intent.putExtra(de.danoeh.antennapod.activity.OnlineFeedViewActivity.ARG_FEEDURL, "");
break;
}
default: {
switch(MUID_STATIC) {
// OnlineFeedViewActivity_8_RandomActionIntentDefinitionOperatorMutator
case 8150: {
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
setIntent(intent);
}


@java.lang.Override
public void finish() {
super.finish();
overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
if (item.getItemId() == android.R.id.home) {
android.content.Intent destIntent;
switch(MUID_STATIC) {
// OnlineFeedViewActivity_9_NullIntentOperatorMutator
case 9150: {
destIntent = null;
break;
}
// OnlineFeedViewActivity_10_InvalidKeyIntentOperatorMutator
case 10150: {
destIntent = new android.content.Intent((OnlineFeedViewActivity) null, de.danoeh.antennapod.activity.MainActivity.class);
break;
}
// OnlineFeedViewActivity_11_RandomActionIntentDefinitionOperatorMutator
case 11150: {
destIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
destIntent = new android.content.Intent(this, de.danoeh.antennapod.activity.MainActivity.class);
break;
}
}
if (androidx.core.app.NavUtils.shouldUpRecreateTask(this, destIntent)) {
startActivity(destIntent);
} else {
androidx.core.app.NavUtils.navigateUpFromSameTask(this);
}
return true;
}
return super.onOptionsItemSelected(item);
}


private void lookupUrlAndDownload(java.lang.String url) {
download = de.danoeh.antennapod.net.discovery.PodcastSearcherRegistry.lookupUrl(url).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.schedulers.Schedulers.io()).subscribe(this::startFeedDownload, (java.lang.Throwable error) -> {
if (error instanceof de.danoeh.antennapod.core.feed.FeedUrlNotFoundException) {
tryToRetrieveFeedUrlBySearch(((de.danoeh.antennapod.core.feed.FeedUrlNotFoundException) (error)));
} else {
showNoPodcastFoundError();
android.util.Log.e(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, android.util.Log.getStackTraceString(error));
}
});
}


private void tryToRetrieveFeedUrlBySearch(de.danoeh.antennapod.core.feed.FeedUrlNotFoundException error) {
android.util.Log.d(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, "Unable to retrieve feed url, trying to retrieve feed url from search");
java.lang.String url;
url = searchFeedUrlByTrackName(error.getTrackName(), error.getArtistName());
if (url != null) {
android.util.Log.d(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, "Successfully retrieve feed url");
isFeedFoundBySearch = true;
startFeedDownload(url);
} else {
showNoPodcastFoundError();
android.util.Log.d(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, "Failed to retrieve feed url");
}
}


private java.lang.String searchFeedUrlByTrackName(java.lang.String trackName, java.lang.String artistName) {
de.danoeh.antennapod.net.discovery.CombinedSearcher searcher;
searcher = new de.danoeh.antennapod.net.discovery.CombinedSearcher();
java.lang.String query;
query = (trackName + " ") + artistName;
java.util.List<de.danoeh.antennapod.net.discovery.PodcastSearchResult> results;
results = searcher.search(query).blockingGet();
for (de.danoeh.antennapod.net.discovery.PodcastSearchResult result : results) {
if ((((result.feedUrl != null) && (result.author != null)) && result.author.equalsIgnoreCase(artistName)) && result.title.equalsIgnoreCase(trackName)) {
return result.feedUrl;
}
}
return null;
}


private void startFeedDownload(java.lang.String url) {
android.util.Log.d(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, "Starting feed download");
selectedDownloadUrl = de.danoeh.antennapod.net.common.UrlChecker.prepareUrl(url);
de.danoeh.antennapod.net.download.serviceinterface.DownloadRequest request;
request = de.danoeh.antennapod.core.service.download.DownloadRequestCreator.create(new de.danoeh.antennapod.model.feed.Feed(selectedDownloadUrl, null)).withAuthentication(username, password).withInitiatedByUser(true).build();
download = io.reactivex.Observable.fromCallable(() -> {
feeds = de.danoeh.antennapod.core.storage.DBReader.getFeedList();
downloader = new de.danoeh.antennapod.core.service.download.HttpDownloader(request);
downloader.call();
return downloader.getResult();
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.download.DownloadResult status) -> checkDownloadResult(status, request.getDestination()), (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, android.util.Log.getStackTraceString(error)));
}


private void checkDownloadResult(@androidx.annotation.NonNull
de.danoeh.antennapod.model.download.DownloadResult status, java.lang.String destination) {
if (status.isSuccessful()) {
parseFeed(destination);
} else if (status.getReason() == de.danoeh.antennapod.model.download.DownloadError.ERROR_UNAUTHORIZED) {
if ((!isFinishing()) && (!isPaused)) {
if ((username != null) && (password != null)) {
android.widget.Toast.makeText(this, de.danoeh.antennapod.R.string.download_error_unauthorized, android.widget.Toast.LENGTH_LONG).show();
}
dialog = new de.danoeh.antennapod.activity.OnlineFeedViewActivity.FeedViewAuthenticationDialog(this, de.danoeh.antennapod.R.string.authentication_notification_title, downloader.getDownloadRequest().getSource()).create();
dialog.show();
}
} else {
showErrorDialog(getString(de.danoeh.antennapod.core.util.DownloadErrorLabel.from(status.getReason())), status.getReasonDetailed());
}
}


@org.greenrobot.eventbus.Subscribe
public void onFeedListChanged(de.danoeh.antennapod.event.FeedListUpdateEvent event) {
updater = io.reactivex.Observable.fromCallable(de.danoeh.antennapod.core.storage.DBReader::getFeedList).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<de.danoeh.antennapod.model.feed.Feed> feeds) -> {
this.feeds = feeds;
handleUpdatedFeedStatus();
}, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, android.util.Log.getStackTraceString(error)));
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.EpisodeDownloadEvent event) {
handleUpdatedFeedStatus();
}


private void parseFeed(java.lang.String destination) {
android.util.Log.d(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, "Parsing feed");
parser = io.reactivex.Maybe.fromCallable(() -> doParseFeed(destination)).subscribeOn(io.reactivex.schedulers.Schedulers.computation()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribeWith(new io.reactivex.observers.DisposableMaybeObserver<de.danoeh.antennapod.parser.feed.FeedHandlerResult>() {
@java.lang.Override
public void onSuccess(@androidx.annotation.NonNull
de.danoeh.antennapod.parser.feed.FeedHandlerResult result) {
showFeedInformation(result.feed, result.alternateFeedUrls);
}


@java.lang.Override
public void onComplete() {
// Ignore null result: We showed the discovery dialog.
}


@java.lang.Override
public void onError(@androidx.annotation.NonNull
java.lang.Throwable error) {
showErrorDialog(error.getMessage(), "");
android.util.Log.d(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, "Feed parser exception: " + android.util.Log.getStackTraceString(error));
}

});
}


/**
 * Try to parse the feed.
 *
 * @return The FeedHandlerResult if successful.
Null if unsuccessful but we started another attempt.
 * @throws Exception
 * 		If unsuccessful but we do not know a resolution.
 */
@androidx.annotation.Nullable
private de.danoeh.antennapod.parser.feed.FeedHandlerResult doParseFeed(java.lang.String destination) throws java.lang.Exception {
de.danoeh.antennapod.parser.feed.FeedHandler handler;
handler = new de.danoeh.antennapod.parser.feed.FeedHandler();
de.danoeh.antennapod.model.feed.Feed feed;
feed = new de.danoeh.antennapod.model.feed.Feed(selectedDownloadUrl, null);
feed.setFile_url(destination);
java.io.File destinationFile;
destinationFile = new java.io.File(destination);
try {
return handler.parseFeed(feed);
} catch (de.danoeh.antennapod.parser.feed.UnsupportedFeedtypeException e) {
android.util.Log.d(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, "Unsupported feed type detected");
if ("html".equalsIgnoreCase(e.getRootElement())) {
boolean dialogShown;
dialogShown = showFeedDiscoveryDialog(destinationFile, selectedDownloadUrl);
if (dialogShown) {
return null// Should not display an error message
;// Should not display an error message

} else {
throw new de.danoeh.antennapod.parser.feed.UnsupportedFeedtypeException(getString(de.danoeh.antennapod.R.string.download_error_unsupported_type_html));
}
} else {
throw e;
}
} catch (java.lang.Exception e) {
android.util.Log.e(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, android.util.Log.getStackTraceString(e));
throw e;
} finally {
boolean rc;
rc = destinationFile.delete();
android.util.Log.d(de.danoeh.antennapod.activity.OnlineFeedViewActivity.TAG, "Deleted feed source file. Result: " + rc);
}
}


/**
 * Called when feed parsed successfully.
 * This method is executed on the GUI thread.
 */
private void showFeedInformation(final de.danoeh.antennapod.model.feed.Feed feed, java.util.Map<java.lang.String, java.lang.String> alternateFeedUrls) {
viewBinding.progressBar.setVisibility(android.view.View.GONE);
viewBinding.feedDisplayContainer.setVisibility(android.view.View.VISIBLE);
if (isFeedFoundBySearch) {
int resId;
resId = de.danoeh.antennapod.R.string.no_feed_url_podcast_found_by_search;
switch(MUID_STATIC) {
// OnlineFeedViewActivity_12_InvalidIDFindViewOperatorMutator
case 12150: {
com.google.android.material.snackbar.Snackbar.make(findViewById(732221), resId, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
break;
}
default: {
com.google.android.material.snackbar.Snackbar.make(findViewById(android.R.id.content), resId, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
break;
}
}
}
viewBinding.backgroundImage.setColorFilter(new android.graphics.LightingColorFilter(0xff828282, 0x0));
viewBinding.listView.addHeaderView(headerBinding.getRoot());
viewBinding.listView.setSelector(android.R.color.transparent);
viewBinding.listView.setAdapter(new de.danoeh.antennapod.adapter.FeedItemlistDescriptionAdapter(this, 0, feed.getItems()));
if (org.apache.commons.lang3.StringUtils.isNotBlank(feed.getImageUrl())) {
com.bumptech.glide.Glide.with(this).load(feed.getImageUrl()).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).error(de.danoeh.antennapod.R.color.light_gray).fitCenter().dontAnimate()).into(viewBinding.coverImage);
com.bumptech.glide.Glide.with(this).load(feed.getImageUrl()).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.image_readability_tint).error(de.danoeh.antennapod.R.color.image_readability_tint).transform(new de.danoeh.antennapod.ui.glide.FastBlurTransformation()).dontAnimate()).into(viewBinding.backgroundImage);
}
viewBinding.titleLabel.setText(feed.getTitle());
viewBinding.authorLabel.setText(feed.getAuthor());
headerBinding.txtvDescription.setText(de.danoeh.antennapod.core.util.syndication.HtmlToPlainText.getPlainText(feed.getDescription()));
switch(MUID_STATIC) {
// OnlineFeedViewActivity_13_BuggyGUIListenerOperatorMutator
case 13150: {
viewBinding.subscribeButton.setOnClickListener(null);
break;
}
default: {
viewBinding.subscribeButton.setOnClickListener((android.view.View v) -> {
if (feedInFeedlist()) {
openFeed();
} else {
de.danoeh.antennapod.core.storage.DBTasks.updateFeed(this, feed, false);
didPressSubscribe = true;
handleUpdatedFeedStatus();
}
});
break;
}
}
switch(MUID_STATIC) {
// OnlineFeedViewActivity_14_BuggyGUIListenerOperatorMutator
case 14150: {
viewBinding.stopPreviewButton.setOnClickListener(null);
break;
}
default: {
viewBinding.stopPreviewButton.setOnClickListener((android.view.View v) -> {
de.danoeh.antennapod.core.preferences.PlaybackPreferences.writeNoMediaPlaying();
de.danoeh.antennapod.core.util.IntentUtils.sendLocalBroadcast(this, de.danoeh.antennapod.core.service.playback.PlaybackServiceInterface.ACTION_SHUTDOWN_PLAYBACK_SERVICE);
});
break;
}
}
if (de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownload()) {
android.content.SharedPreferences preferences;
preferences = getSharedPreferences(de.danoeh.antennapod.activity.OnlineFeedViewActivity.PREFS, android.content.Context.MODE_PRIVATE);
viewBinding.autoDownloadCheckBox.setChecked(preferences.getBoolean(de.danoeh.antennapod.activity.OnlineFeedViewActivity.PREF_LAST_AUTO_DOWNLOAD, true));
}
headerBinding.txtvDescription.setMaxLines(de.danoeh.antennapod.activity.OnlineFeedViewActivity.DESCRIPTION_MAX_LINES_COLLAPSED);
switch(MUID_STATIC) {
// OnlineFeedViewActivity_15_BuggyGUIListenerOperatorMutator
case 15150: {
headerBinding.txtvDescription.setOnClickListener(null);
break;
}
default: {
headerBinding.txtvDescription.setOnClickListener((android.view.View v) -> {
if (headerBinding.txtvDescription.getMaxLines() > de.danoeh.antennapod.activity.OnlineFeedViewActivity.DESCRIPTION_MAX_LINES_COLLAPSED) {
headerBinding.txtvDescription.setMaxLines(de.danoeh.antennapod.activity.OnlineFeedViewActivity.DESCRIPTION_MAX_LINES_COLLAPSED);
} else {
headerBinding.txtvDescription.setMaxLines(2000);
}
});
break;
}
}
if (alternateFeedUrls.isEmpty()) {
viewBinding.alternateUrlsSpinner.setVisibility(android.view.View.GONE);
} else {
viewBinding.alternateUrlsSpinner.setVisibility(android.view.View.VISIBLE);
final java.util.List<java.lang.String> alternateUrlsList;
alternateUrlsList = new java.util.ArrayList<>();
final java.util.List<java.lang.String> alternateUrlsTitleList;
alternateUrlsTitleList = new java.util.ArrayList<>();
alternateUrlsList.add(feed.getDownload_url());
alternateUrlsTitleList.add(feed.getTitle());
alternateUrlsList.addAll(alternateFeedUrls.keySet());
for (java.lang.String url : alternateFeedUrls.keySet()) {
alternateUrlsTitleList.add(alternateFeedUrls.get(url));
}
android.widget.ArrayAdapter<java.lang.String> adapter;
adapter = new android.widget.ArrayAdapter<java.lang.String>(this, de.danoeh.antennapod.R.layout.alternate_urls_item, alternateUrlsTitleList) {
@java.lang.Override
public android.view.View getDropDownView(int position, @androidx.annotation.Nullable
android.view.View convertView, @androidx.annotation.NonNull
android.view.ViewGroup parent) {
// reusing the old view causes a visual bug on Android <= 10
return super.getDropDownView(position, null, parent);
}

};
adapter.setDropDownViewResource(de.danoeh.antennapod.R.layout.alternate_urls_dropdown_item);
viewBinding.alternateUrlsSpinner.setAdapter(adapter);
viewBinding.alternateUrlsSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
@java.lang.Override
public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
selectedDownloadUrl = alternateUrlsList.get(position);
}


@java.lang.Override
public void onNothingSelected(android.widget.AdapterView<?> parent) {
}

});
}
handleUpdatedFeedStatus();
}


private void openFeed() {
// feed.getId() is always 0, we have to retrieve the id from the feed list from
// the database
android.content.Intent intent;
switch(MUID_STATIC) {
// OnlineFeedViewActivity_16_RandomActionIntentDefinitionOperatorMutator
case 16150: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = de.danoeh.antennapod.activity.MainActivity.getIntentToOpenFeed(this, getFeedId());
break;
}
}
switch(MUID_STATIC) {
// OnlineFeedViewActivity_17_NullValueIntentPutExtraOperatorMutator
case 17150: {
intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_STARTED_FROM_SEARCH, new Parcelable[0]);
break;
}
// OnlineFeedViewActivity_18_IntentPayloadReplacementOperatorMutator
case 18150: {
intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_STARTED_FROM_SEARCH, true);
break;
}
default: {
switch(MUID_STATIC) {
// OnlineFeedViewActivity_19_RandomActionIntentDefinitionOperatorMutator
case 19150: {
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
intent.putExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_STARTED_FROM_SEARCH, getIntent().getBooleanExtra(de.danoeh.antennapod.activity.MainActivity.EXTRA_STARTED_FROM_SEARCH, false));
break;
}
}
break;
}
}
finish();
startActivity(intent);
}


private void handleUpdatedFeedStatus() {
if (de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isDownloadingEpisode(selectedDownloadUrl)) {
viewBinding.subscribeButton.setEnabled(false);
viewBinding.subscribeButton.setText(de.danoeh.antennapod.R.string.subscribing_label);
} else if (feedInFeedlist()) {
viewBinding.subscribeButton.setEnabled(true);
viewBinding.subscribeButton.setText(de.danoeh.antennapod.R.string.open_podcast);
if (didPressSubscribe) {
didPressSubscribe = false;
de.danoeh.antennapod.model.feed.Feed feed1;
feed1 = de.danoeh.antennapod.core.storage.DBReader.getFeed(getFeedId());
de.danoeh.antennapod.model.feed.FeedPreferences feedPreferences;
feedPreferences = feed1.getPreferences();
if (de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownload()) {
boolean autoDownload;
autoDownload = viewBinding.autoDownloadCheckBox.isChecked();
feedPreferences.setAutoDownload(autoDownload);
android.content.SharedPreferences preferences;
preferences = getSharedPreferences(de.danoeh.antennapod.activity.OnlineFeedViewActivity.PREFS, android.content.Context.MODE_PRIVATE);
android.content.SharedPreferences.Editor editor;
editor = preferences.edit();
editor.putBoolean(de.danoeh.antennapod.activity.OnlineFeedViewActivity.PREF_LAST_AUTO_DOWNLOAD, autoDownload);
editor.apply();
}
if (username != null) {
feedPreferences.setUsername(username);
feedPreferences.setPassword(password);
}
de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(feedPreferences);
openFeed();
}
} else {
viewBinding.subscribeButton.setEnabled(true);
viewBinding.subscribeButton.setText(de.danoeh.antennapod.R.string.subscribe_label);
if (de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownload()) {
viewBinding.autoDownloadCheckBox.setVisibility(android.view.View.VISIBLE);
}
}
}


private boolean feedInFeedlist() {
return getFeedId() != 0;
}


private long getFeedId() {
if (feeds == null) {
return 0;
}
for (de.danoeh.antennapod.model.feed.Feed f : feeds) {
if (f.getDownload_url().equals(selectedDownloadUrl)) {
return f.getId();
}
}
return 0;
}


@androidx.annotation.UiThread
private void showErrorDialog(java.lang.String errorMsg, java.lang.String details) {
if ((!isFinishing()) && (!isPaused)) {
com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(this);
builder.setTitle(de.danoeh.antennapod.R.string.error_label);
if (errorMsg != null) {
java.lang.String total;
total = (errorMsg + "\n\n") + details;
android.text.SpannableString errorMessage;
errorMessage = new android.text.SpannableString(total);
errorMessage.setSpan(new android.text.style.ForegroundColorSpan(0x88888888), errorMsg.length(), total.length(), android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
builder.setMessage(errorMessage);
} else {
builder.setMessage(de.danoeh.antennapod.R.string.download_error_error_unknown);
}
switch(MUID_STATIC) {
// OnlineFeedViewActivity_20_BuggyGUIListenerOperatorMutator
case 20150: {
builder.setPositiveButton(android.R.string.ok, null);
break;
}
default: {
builder.setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> dialog.cancel());
break;
}
}
builder.setOnDismissListener((android.content.DialogInterface dialog) -> {
setResult(de.danoeh.antennapod.activity.OnlineFeedViewActivity.RESULT_ERROR);
finish();
});
if ((dialog != null) && dialog.isShowing()) {
dialog.dismiss();
}
dialog = builder.show();
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void playbackStateChanged(de.danoeh.antennapod.event.PlayerStatusEvent event) {
boolean isPlayingPreview;
isPlayingPreview = de.danoeh.antennapod.core.preferences.PlaybackPreferences.getCurrentlyPlayingMediaType() == de.danoeh.antennapod.model.playback.RemoteMedia.PLAYABLE_TYPE_REMOTE_MEDIA;
viewBinding.stopPreviewButton.setVisibility(isPlayingPreview ? android.view.View.VISIBLE : android.view.View.GONE);
}


/**
 *
 * @return true if a FeedDiscoveryDialog is shown, false otherwise (e.g., due to no feed found).
 */
private boolean showFeedDiscoveryDialog(java.io.File feedFile, java.lang.String baseUrl) {
de.danoeh.antennapod.core.util.syndication.FeedDiscoverer fd;
fd = new de.danoeh.antennapod.core.util.syndication.FeedDiscoverer();
final java.util.Map<java.lang.String, java.lang.String> urlsMap;
try {
urlsMap = fd.findLinks(feedFile, baseUrl);
if ((urlsMap == null) || urlsMap.isEmpty()) {
return false;
}
} catch (java.io.IOException e) {
e.printStackTrace();
return false;
}
if (isPaused || isFinishing()) {
return false;
}
final java.util.List<java.lang.String> titles;
titles = new java.util.ArrayList<>();
final java.util.List<java.lang.String> urls;
urls = new java.util.ArrayList<>(urlsMap.keySet());
for (java.lang.String url : urls) {
titles.add(urlsMap.get(url));
}
if (urls.size() == 1) {
// Skip dialog and display the item directly
resetIntent(urls.get(0));
startFeedDownload(urls.get(0));
return true;
}
final android.widget.ArrayAdapter<java.lang.String> adapter;
adapter = new android.widget.ArrayAdapter<>(this, de.danoeh.antennapod.R.layout.ellipsize_start_listitem, de.danoeh.antennapod.R.id.txtvTitle, titles);
android.content.DialogInterface.OnClickListener onClickListener;
switch(MUID_STATIC) {
// OnlineFeedViewActivity_21_BuggyGUIListenerOperatorMutator
case 21150: {
onClickListener = null;
break;
}
default: {
onClickListener = (android.content.DialogInterface dialog,int which) -> {
java.lang.String selectedUrl;
selectedUrl = urls.get(which);
dialog.dismiss();
resetIntent(selectedUrl);
startFeedDownload(selectedUrl);
};
break;
}
}
com.google.android.material.dialog.MaterialAlertDialogBuilder ab;
ab = new com.google.android.material.dialog.MaterialAlertDialogBuilder(this).setTitle(de.danoeh.antennapod.R.string.feeds_label).setCancelable(true).setOnCancelListener((android.content.DialogInterface dialog) -> finish()).setAdapter(adapter, onClickListener);
runOnUiThread(() -> {
if ((dialog != null) && dialog.isShowing()) {
dialog.dismiss();
}
dialog = ab.show();
});
return true;
}


private class FeedViewAuthenticationDialog extends de.danoeh.antennapod.dialog.AuthenticationDialog {
private final java.lang.String feedUrl;

FeedViewAuthenticationDialog(android.content.Context context, int titleRes, java.lang.String feedUrl) {
super(context, titleRes, true, username, password);
this.feedUrl = feedUrl;
}


@java.lang.Override
protected void onCancelled() {
super.onCancelled();
finish();
}


@java.lang.Override
protected void onConfirmed(java.lang.String username, java.lang.String password) {
de.danoeh.antennapod.activity.OnlineFeedViewActivity.this.username = username;
de.danoeh.antennapod.activity.OnlineFeedViewActivity.this.password = password;
startFeedDownload(feedUrl);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
