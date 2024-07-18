package de.danoeh.antennapod.fragment;
import de.danoeh.antennapod.ui.statistics.StatisticsFragment;
import io.reactivex.Completable;
import android.content.res.Configuration;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import com.google.android.material.appbar.MaterialToolbar;
import androidx.annotation.NonNull;
import android.os.Build;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import de.danoeh.antennapod.view.ToolbarIconTintManager;
import io.reactivex.MaybeOnSubscribe;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import de.danoeh.antennapod.model.feed.Feed;
import com.joanzapata.iconify.Iconify;
import io.reactivex.Maybe;
import android.util.Log;
import de.danoeh.antennapod.core.storage.DBTasks;
import android.graphics.LightingColorFilter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.documentfile.provider.DocumentFile;
import de.danoeh.antennapod.model.feed.FeedFunding;
import de.danoeh.antennapod.dialog.EditUrlSettingsDialog;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.util.IntentUtils;
import android.content.ActivityNotFoundException;
import de.danoeh.antennapod.core.storage.DBReader;
import android.content.ClipData;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.core.util.syndication.HtmlToPlainText;
import de.danoeh.antennapod.activity.MainActivity;
import android.net.Uri;
import de.danoeh.antennapod.ui.glide.FastBlurTransformation;
import de.danoeh.antennapod.R;
import org.apache.commons.lang3.StringUtils;
import java.util.Iterator;
import android.widget.Toast;
import de.danoeh.antennapod.menuhandler.FeedMenuHandler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import android.text.TextUtils;
import androidx.activity.result.contract.ActivityResultContracts;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.content.res.AppCompatResources;
import de.danoeh.antennapod.ui.statistics.feed.FeedStatisticsFragment;
import androidx.activity.result.ActivityResultLauncher;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays information about a feed.
 */
public class FeedInfoFragment extends androidx.fragment.app.Fragment implements androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String EXTRA_FEED_ID = "de.danoeh.antennapod.extra.feedId";

    private static final java.lang.String TAG = "FeedInfoActivity";

    private final androidx.activity.result.ActivityResultLauncher<android.net.Uri> addLocalFolderLauncher = registerForActivityResult(new de.danoeh.antennapod.fragment.FeedInfoFragment.AddLocalFolder(), this::addLocalFolderResult);

    private de.danoeh.antennapod.model.feed.Feed feed;

    private io.reactivex.disposables.Disposable disposable;

    private android.widget.ImageView imgvCover;

    private android.widget.TextView txtvTitle;

    private android.widget.TextView txtvDescription;

    private android.widget.TextView txtvFundingUrl;

    private android.widget.TextView lblSupport;

    private android.widget.TextView txtvUrl;

    private android.widget.TextView txtvAuthorHeader;

    private android.widget.ImageView imgvBackground;

    private android.view.View infoContainer;

    private android.view.View header;

    private com.google.android.material.appbar.MaterialToolbar toolbar;

    public static de.danoeh.antennapod.fragment.FeedInfoFragment newInstance(de.danoeh.antennapod.model.feed.Feed feed) {
        de.danoeh.antennapod.fragment.FeedInfoFragment fragment;
        fragment = new de.danoeh.antennapod.fragment.FeedInfoFragment();
        android.os.Bundle arguments;
        arguments = new android.os.Bundle();
        arguments.putLong(de.danoeh.antennapod.fragment.FeedInfoFragment.EXTRA_FEED_ID, feed.getId());
        fragment.setArguments(arguments);
        return fragment;
    }


    private final android.view.View.OnClickListener copyUrlToClipboard = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            switch(MUID_STATIC) {
                // FeedInfoFragment_0_LengthyGUIListenerOperatorMutator
                case 129: {
                    /**
                    * Inserted by Kadabra
                    */
                    if ((feed != null) && (feed.getDownload_url() != null)) {
                        java.lang.String url;
                        url = feed.getDownload_url();
                        android.content.ClipData clipData;
                        clipData = android.content.ClipData.newPlainText(url, url);
                        android.content.ClipboardManager cm;
                        cm = ((android.content.ClipboardManager) (getContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
                        cm.setPrimaryClip(clipData);
                        if (android.os.Build.VERSION.SDK_INT <= 32) {
                            ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.copied_to_clipboard, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
                        }
                    }
                    try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                    break;
                }
                default: {
                if ((feed != null) && (feed.getDownload_url() != null)) {
                    java.lang.String url;
                    url = feed.getDownload_url();
                    android.content.ClipData clipData;
                    clipData = android.content.ClipData.newPlainText(url, url);
                    android.content.ClipboardManager cm;
                    cm = ((android.content.ClipboardManager) (getContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
                    cm.setPrimaryClip(clipData);
                    if (android.os.Build.VERSION.SDK_INT <= 32) {
                        ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.copied_to_clipboard, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
                    }
                }
                break;
            }
        }
    }

};

@androidx.annotation.Nullable
@java.lang.Override
public android.view.View onCreateView(@androidx.annotation.NonNull
android.view.LayoutInflater inflater, @androidx.annotation.Nullable
android.view.ViewGroup container, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    android.view.View root;
    root = inflater.inflate(de.danoeh.antennapod.R.layout.feedinfo, null);
    switch(MUID_STATIC) {
        // FeedInfoFragment_1_InvalidViewFocusOperatorMutator
        case 1129: {
            /**
            * Inserted by Kadabra
            */
            toolbar = root.findViewById(de.danoeh.antennapod.R.id.toolbar);
            toolbar.requestFocus();
            break;
        }
        // FeedInfoFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2129: {
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
toolbar.setTitle("");
toolbar.inflateMenu(de.danoeh.antennapod.R.menu.feedinfo);
switch(MUID_STATIC) {
    // FeedInfoFragment_3_BuggyGUIListenerOperatorMutator
    case 3129: {
        toolbar.setNavigationOnClickListener(null);
        break;
    }
    default: {
    toolbar.setNavigationOnClickListener((android.view.View v) -> getParentFragmentManager().popBackStack());
    break;
}
}
toolbar.setOnMenuItemClickListener(this);
refreshToolbarState();
com.google.android.material.appbar.AppBarLayout appBar;
switch(MUID_STATIC) {
// FeedInfoFragment_4_InvalidViewFocusOperatorMutator
case 4129: {
    /**
    * Inserted by Kadabra
    */
    appBar = root.findViewById(de.danoeh.antennapod.R.id.appBar);
    appBar.requestFocus();
    break;
}
// FeedInfoFragment_5_ViewComponentNotVisibleOperatorMutator
case 5129: {
    /**
    * Inserted by Kadabra
    */
    appBar = root.findViewById(de.danoeh.antennapod.R.id.appBar);
    appBar.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
appBar = root.findViewById(de.danoeh.antennapod.R.id.appBar);
break;
}
}
com.google.android.material.appbar.CollapsingToolbarLayout collapsingToolbar;
switch(MUID_STATIC) {
// FeedInfoFragment_6_InvalidViewFocusOperatorMutator
case 6129: {
/**
* Inserted by Kadabra
*/
collapsingToolbar = root.findViewById(de.danoeh.antennapod.R.id.collapsing_toolbar);
collapsingToolbar.requestFocus();
break;
}
// FeedInfoFragment_7_ViewComponentNotVisibleOperatorMutator
case 7129: {
/**
* Inserted by Kadabra
*/
collapsingToolbar = root.findViewById(de.danoeh.antennapod.R.id.collapsing_toolbar);
collapsingToolbar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
collapsingToolbar = root.findViewById(de.danoeh.antennapod.R.id.collapsing_toolbar);
break;
}
}
de.danoeh.antennapod.view.ToolbarIconTintManager iconTintManager;
iconTintManager = new de.danoeh.antennapod.view.ToolbarIconTintManager(getContext(), toolbar, collapsingToolbar) {
@java.lang.Override
protected void doTint(android.content.Context themedContext) {
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.visit_website_item).setIcon(androidx.appcompat.content.res.AppCompatResources.getDrawable(themedContext, de.danoeh.antennapod.R.drawable.ic_web));
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.share_item).setIcon(androidx.appcompat.content.res.AppCompatResources.getDrawable(themedContext, de.danoeh.antennapod.R.drawable.ic_share));
}

};
iconTintManager.updateTint();
appBar.addOnOffsetChangedListener(iconTintManager);
switch(MUID_STATIC) {
// FeedInfoFragment_8_InvalidViewFocusOperatorMutator
case 8129: {
/**
* Inserted by Kadabra
*/
imgvCover = root.findViewById(de.danoeh.antennapod.R.id.imgvCover);
imgvCover.requestFocus();
break;
}
// FeedInfoFragment_9_ViewComponentNotVisibleOperatorMutator
case 9129: {
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
// FeedInfoFragment_10_InvalidViewFocusOperatorMutator
case 10129: {
/**
* Inserted by Kadabra
*/
txtvTitle = root.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
txtvTitle.requestFocus();
break;
}
// FeedInfoFragment_11_ViewComponentNotVisibleOperatorMutator
case 11129: {
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
// FeedInfoFragment_12_InvalidViewFocusOperatorMutator
case 12129: {
/**
* Inserted by Kadabra
*/
txtvAuthorHeader = root.findViewById(de.danoeh.antennapod.R.id.txtvAuthor);
txtvAuthorHeader.requestFocus();
break;
}
// FeedInfoFragment_13_ViewComponentNotVisibleOperatorMutator
case 13129: {
/**
* Inserted by Kadabra
*/
txtvAuthorHeader = root.findViewById(de.danoeh.antennapod.R.id.txtvAuthor);
txtvAuthorHeader.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvAuthorHeader = root.findViewById(de.danoeh.antennapod.R.id.txtvAuthor);
break;
}
}
switch(MUID_STATIC) {
// FeedInfoFragment_14_InvalidViewFocusOperatorMutator
case 14129: {
/**
* Inserted by Kadabra
*/
imgvBackground = root.findViewById(de.danoeh.antennapod.R.id.imgvBackground);
imgvBackground.requestFocus();
break;
}
// FeedInfoFragment_15_ViewComponentNotVisibleOperatorMutator
case 15129: {
/**
* Inserted by Kadabra
*/
imgvBackground = root.findViewById(de.danoeh.antennapod.R.id.imgvBackground);
imgvBackground.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
imgvBackground = root.findViewById(de.danoeh.antennapod.R.id.imgvBackground);
break;
}
}
switch(MUID_STATIC) {
// FeedInfoFragment_16_InvalidViewFocusOperatorMutator
case 16129: {
/**
* Inserted by Kadabra
*/
header = root.findViewById(de.danoeh.antennapod.R.id.headerContainer);
header.requestFocus();
break;
}
// FeedInfoFragment_17_ViewComponentNotVisibleOperatorMutator
case 17129: {
/**
* Inserted by Kadabra
*/
header = root.findViewById(de.danoeh.antennapod.R.id.headerContainer);
header.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
header = root.findViewById(de.danoeh.antennapod.R.id.headerContainer);
break;
}
}
switch(MUID_STATIC) {
// FeedInfoFragment_18_InvalidViewFocusOperatorMutator
case 18129: {
/**
* Inserted by Kadabra
*/
infoContainer = root.findViewById(de.danoeh.antennapod.R.id.infoContainer);
infoContainer.requestFocus();
break;
}
// FeedInfoFragment_19_ViewComponentNotVisibleOperatorMutator
case 19129: {
/**
* Inserted by Kadabra
*/
infoContainer = root.findViewById(de.danoeh.antennapod.R.id.infoContainer);
infoContainer.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
infoContainer = root.findViewById(de.danoeh.antennapod.R.id.infoContainer);
break;
}
}
root.findViewById(de.danoeh.antennapod.R.id.butShowInfo).setVisibility(android.view.View.INVISIBLE);
root.findViewById(de.danoeh.antennapod.R.id.butShowSettings).setVisibility(android.view.View.INVISIBLE);
root.findViewById(de.danoeh.antennapod.R.id.butFilter).setVisibility(android.view.View.INVISIBLE);
// https://github.com/bumptech/glide/issues/529
imgvBackground.setColorFilter(new android.graphics.LightingColorFilter(0xff828282, 0x0));
switch(MUID_STATIC) {
// FeedInfoFragment_20_InvalidViewFocusOperatorMutator
case 20129: {
/**
* Inserted by Kadabra
*/
txtvDescription = root.findViewById(de.danoeh.antennapod.R.id.txtvDescription);
txtvDescription.requestFocus();
break;
}
// FeedInfoFragment_21_ViewComponentNotVisibleOperatorMutator
case 21129: {
/**
* Inserted by Kadabra
*/
txtvDescription = root.findViewById(de.danoeh.antennapod.R.id.txtvDescription);
txtvDescription.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvDescription = root.findViewById(de.danoeh.antennapod.R.id.txtvDescription);
break;
}
}
switch(MUID_STATIC) {
// FeedInfoFragment_22_InvalidViewFocusOperatorMutator
case 22129: {
/**
* Inserted by Kadabra
*/
txtvUrl = root.findViewById(de.danoeh.antennapod.R.id.txtvUrl);
txtvUrl.requestFocus();
break;
}
// FeedInfoFragment_23_ViewComponentNotVisibleOperatorMutator
case 23129: {
/**
* Inserted by Kadabra
*/
txtvUrl = root.findViewById(de.danoeh.antennapod.R.id.txtvUrl);
txtvUrl.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvUrl = root.findViewById(de.danoeh.antennapod.R.id.txtvUrl);
break;
}
}
switch(MUID_STATIC) {
// FeedInfoFragment_24_InvalidViewFocusOperatorMutator
case 24129: {
/**
* Inserted by Kadabra
*/
lblSupport = root.findViewById(de.danoeh.antennapod.R.id.lblSupport);
lblSupport.requestFocus();
break;
}
// FeedInfoFragment_25_ViewComponentNotVisibleOperatorMutator
case 25129: {
/**
* Inserted by Kadabra
*/
lblSupport = root.findViewById(de.danoeh.antennapod.R.id.lblSupport);
lblSupport.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
lblSupport = root.findViewById(de.danoeh.antennapod.R.id.lblSupport);
break;
}
}
switch(MUID_STATIC) {
// FeedInfoFragment_26_InvalidViewFocusOperatorMutator
case 26129: {
/**
* Inserted by Kadabra
*/
txtvFundingUrl = root.findViewById(de.danoeh.antennapod.R.id.txtvFundingUrl);
txtvFundingUrl.requestFocus();
break;
}
// FeedInfoFragment_27_ViewComponentNotVisibleOperatorMutator
case 27129: {
/**
* Inserted by Kadabra
*/
txtvFundingUrl = root.findViewById(de.danoeh.antennapod.R.id.txtvFundingUrl);
txtvFundingUrl.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvFundingUrl = root.findViewById(de.danoeh.antennapod.R.id.txtvFundingUrl);
break;
}
}
txtvUrl.setOnClickListener(copyUrlToClipboard);
long feedId;
feedId = getArguments().getLong(de.danoeh.antennapod.fragment.FeedInfoFragment.EXTRA_FEED_ID);
getParentFragmentManager().beginTransaction().replace(de.danoeh.antennapod.R.id.statisticsFragmentContainer, de.danoeh.antennapod.ui.statistics.feed.FeedStatisticsFragment.newInstance(feedId, false), "feed_statistics_fragment").commitAllowingStateLoss();
switch(MUID_STATIC) {
// FeedInfoFragment_28_BuggyGUIListenerOperatorMutator
case 28129: {
root.findViewById(de.danoeh.antennapod.R.id.btnvOpenStatistics).setOnClickListener(null);
break;
}
default: {
root.findViewById(de.danoeh.antennapod.R.id.btnvOpenStatistics).setOnClickListener((android.view.View view) -> {
de.danoeh.antennapod.ui.statistics.StatisticsFragment fragment;
fragment = new de.danoeh.antennapod.ui.statistics.StatisticsFragment();
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(fragment, de.danoeh.antennapod.fragment.TransitionEffect.SLIDE);
});
break;
}
}
return root;
}


@java.lang.Override
public void onViewCreated(@androidx.annotation.NonNull
android.view.View view, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
long feedId;
feedId = getArguments().getLong(de.danoeh.antennapod.fragment.FeedInfoFragment.EXTRA_FEED_ID);
disposable = io.reactivex.Maybe.create(((io.reactivex.MaybeOnSubscribe<de.danoeh.antennapod.model.feed.Feed>) ((io.reactivex.MaybeEmitter<de.danoeh.antennapod.model.feed.Feed> emitter) -> {
de.danoeh.antennapod.model.feed.Feed feed;
feed = de.danoeh.antennapod.core.storage.DBReader.getFeed(feedId);
if (feed != null) {
emitter.onSuccess(feed);
} else {
emitter.onComplete();
}
}))).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.feed.Feed result) -> {
feed = result;
showFeed();
}, (java.lang.Throwable error) -> android.util.Log.d(de.danoeh.antennapod.fragment.FeedInfoFragment.TAG, android.util.Log.getStackTraceString(error)), () -> {
});
}


@java.lang.Override
public void onConfigurationChanged(@androidx.annotation.NonNull
android.content.res.Configuration newConfig) {
super.onConfigurationChanged(newConfig);
if ((header == null) || (infoContainer == null)) {
return;
}
int horizontalSpacing;
horizontalSpacing = ((int) (getResources().getDimension(de.danoeh.antennapod.R.dimen.additional_horizontal_spacing)));
header.setPadding(horizontalSpacing, header.getPaddingTop(), horizontalSpacing, header.getPaddingBottom());
infoContainer.setPadding(horizontalSpacing, infoContainer.getPaddingTop(), horizontalSpacing, infoContainer.getPaddingBottom());
}


private void showFeed() {
android.util.Log.d(de.danoeh.antennapod.fragment.FeedInfoFragment.TAG, "Language is " + feed.getLanguage());
android.util.Log.d(de.danoeh.antennapod.fragment.FeedInfoFragment.TAG, "Author is " + feed.getAuthor());
android.util.Log.d(de.danoeh.antennapod.fragment.FeedInfoFragment.TAG, "URL is " + feed.getDownload_url());
com.bumptech.glide.Glide.with(this).load(feed.getImageUrl()).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).error(de.danoeh.antennapod.R.color.light_gray).fitCenter().dontAnimate()).into(imgvCover);
com.bumptech.glide.Glide.with(this).load(feed.getImageUrl()).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.image_readability_tint).error(de.danoeh.antennapod.R.color.image_readability_tint).transform(new de.danoeh.antennapod.ui.glide.FastBlurTransformation()).dontAnimate()).into(imgvBackground);
txtvTitle.setText(feed.getTitle());
txtvTitle.setMaxLines(6);
java.lang.String description;
description = de.danoeh.antennapod.core.util.syndication.HtmlToPlainText.getPlainText(feed.getDescription());
txtvDescription.setText(description);
if (!android.text.TextUtils.isEmpty(feed.getAuthor())) {
txtvAuthorHeader.setText(feed.getAuthor());
}
txtvUrl.setText(feed.getDownload_url() + " {fa-paperclip}");
if ((feed.getPaymentLinks() == null) || (feed.getPaymentLinks().size() == 0)) {
lblSupport.setVisibility(android.view.View.GONE);
txtvFundingUrl.setVisibility(android.view.View.GONE);
} else {
lblSupport.setVisibility(android.view.View.VISIBLE);
java.util.ArrayList<de.danoeh.antennapod.model.feed.FeedFunding> fundingList;
fundingList = feed.getPaymentLinks();
// Filter for duplicates, but keep items in the order that they have in the feed.
java.util.Iterator<de.danoeh.antennapod.model.feed.FeedFunding> i;
i = fundingList.iterator();
while (i.hasNext()) {
de.danoeh.antennapod.model.feed.FeedFunding funding;
funding = i.next();
for (de.danoeh.antennapod.model.feed.FeedFunding other : fundingList) {
if (android.text.TextUtils.equals(other.url, funding.url)) {
if (((other.content != null) && (funding.content != null)) && (other.content.length() > funding.content.length())) {
i.remove();
break;
}
}
}
} 
java.lang.StringBuilder str;
str = new java.lang.StringBuilder();
for (de.danoeh.antennapod.model.feed.FeedFunding funding : fundingList) {
str.append(funding.content.isEmpty() ? getContext().getResources().getString(de.danoeh.antennapod.R.string.support_podcast) : funding.content).append(" ").append(funding.url);
str.append("\n");
}
str = new java.lang.StringBuilder(org.apache.commons.lang3.StringUtils.trim(str.toString()));
txtvFundingUrl.setText(str.toString());
}
com.joanzapata.iconify.Iconify.addIcons(txtvUrl);
refreshToolbarState();
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
if (disposable != null) {
disposable.dispose();
}
}


private void refreshToolbarState() {
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.reconnect_local_folder).setVisible((feed != null) && feed.isLocalFeed());
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.share_item).setVisible((feed != null) && (!feed.isLocalFeed()));
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.visit_website_item).setVisible(((feed != null) && (feed.getLink() != null)) && de.danoeh.antennapod.core.util.IntentUtils.isCallable(getContext(), new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(feed.getLink()))));
toolbar.getMenu().findItem(de.danoeh.antennapod.R.id.edit_feed_url_item).setVisible((feed != null) && (!feed.isLocalFeed()));
}


@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem item) {
if (feed == null) {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.please_wait_for_data, android.widget.Toast.LENGTH_LONG);
return false;
}
boolean handled;
handled = de.danoeh.antennapod.menuhandler.FeedMenuHandler.onOptionsItemClicked(getContext(), item, feed);
if (item.getItemId() == de.danoeh.antennapod.R.id.reconnect_local_folder) {
com.google.android.material.dialog.MaterialAlertDialogBuilder alert;
alert = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
alert.setMessage(de.danoeh.antennapod.R.string.reconnect_local_folder_warning);
switch(MUID_STATIC) {
// FeedInfoFragment_29_BuggyGUIListenerOperatorMutator
case 29129: {
alert.setPositiveButton(android.R.string.ok, null);
break;
}
default: {
alert.setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> {
try {
addLocalFolderLauncher.launch(null);
} catch (android.content.ActivityNotFoundException e) {
android.util.Log.e(de.danoeh.antennapod.fragment.FeedInfoFragment.TAG, "No activity found. Should never happen...");
}
});
break;
}
}
alert.setNegativeButton(android.R.string.cancel, null);
alert.show();
return true;
}
if (item.getItemId() == de.danoeh.antennapod.R.id.edit_feed_url_item) {
new de.danoeh.antennapod.dialog.EditUrlSettingsDialog(getActivity(), feed) {
@java.lang.Override
protected void setUrl(java.lang.String url) {
feed.setDownload_url(url);
txtvUrl.setText(feed.getDownload_url() + " {fa-paperclip}");
com.joanzapata.iconify.Iconify.addIcons(txtvUrl);
}

}.show();
return true;
}
return handled;
}


private void addLocalFolderResult(final android.net.Uri uri) {
if (uri == null) {
return;
}
reconnectLocalFolder(uri);
}


private void reconnectLocalFolder(android.net.Uri uri) {
if (feed == null) {
return;
}
io.reactivex.Completable.fromAction(() -> {
getActivity().getContentResolver().takePersistableUriPermission(uri, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
androidx.documentfile.provider.DocumentFile documentFile;
documentFile = androidx.documentfile.provider.DocumentFile.fromTreeUri(getContext(), uri);
if (documentFile == null) {
throw new java.lang.IllegalArgumentException("Unable to retrieve document tree");
}
feed.setDownload_url(de.danoeh.antennapod.model.feed.Feed.PREFIX_LOCAL_FOLDER + uri.toString());
de.danoeh.antennapod.core.storage.DBTasks.updateFeed(getContext(), feed, true);
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(() -> ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(android.R.string.ok, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT), (java.lang.Throwable error) -> ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(error.getLocalizedMessage(), com.google.android.material.snackbar.Snackbar.LENGTH_LONG));
}


private static class AddLocalFolder extends androidx.activity.result.contract.ActivityResultContracts.OpenDocumentTree {
@androidx.annotation.NonNull
@java.lang.Override
public android.content.Intent createIntent(@androidx.annotation.NonNull
final android.content.Context context, @androidx.annotation.Nullable
final android.net.Uri input) {
switch(MUID_STATIC) {
// FeedInfoFragment_30_RandomActionIntentDefinitionOperatorMutator
case 30129: {
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
