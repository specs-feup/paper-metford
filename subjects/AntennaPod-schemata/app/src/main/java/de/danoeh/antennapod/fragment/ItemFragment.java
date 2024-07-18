package de.danoeh.antennapod.fragment;
import java.util.Locale;
import de.danoeh.antennapod.event.EpisodeDownloadEvent;
import de.danoeh.antennapod.core.util.PlaybackStatus;
import de.danoeh.antennapod.core.preferences.UsageStatistics;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import de.danoeh.antennapod.ui.common.CircularProgressBar;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import de.danoeh.antennapod.adapter.actionbutton.PlayLocalActionButton;
import org.greenrobot.eventbus.Subscribe;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.adapter.actionbutton.StreamActionButton;
import de.danoeh.antennapod.event.FeedItemEvent;
import android.widget.Button;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import com.skydoves.balloon.Balloon;
import android.os.Build;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.Converter;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import de.danoeh.antennapod.adapter.actionbutton.DownloadActionButton;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import android.util.Log;
import de.danoeh.antennapod.adapter.actionbutton.PlayActionButton;
import de.danoeh.antennapod.view.ShownotesWebView;
import com.skydoves.balloon.ArrowOrientation;
import android.view.LayoutInflater;
import de.danoeh.antennapod.model.feed.FeedMedia;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.feed.util.ImageResourceUtils;
import de.danoeh.antennapod.core.util.DateFormatter;
import java.util.Objects;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.adapter.actionbutton.DeleteActionButton;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import org.greenrobot.eventbus.ThreadMode;
import com.skydoves.balloon.BalloonAnimation;
import de.danoeh.antennapod.event.PlayerStatusEvent;
import de.danoeh.antennapod.activity.MainActivity;
import org.greenrobot.eventbus.EventBus;
import de.danoeh.antennapod.adapter.actionbutton.PauseActionButton;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.adapter.actionbutton.ItemActionButton;
import de.danoeh.antennapod.core.util.playback.PlaybackController;
import com.skydoves.balloon.ArrowOrientationRules;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import android.widget.ProgressBar;
import de.danoeh.antennapod.adapter.actionbutton.MarkAsPlayedActionButton;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import de.danoeh.antennapod.core.util.gui.ShownotesCleaner;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import android.text.TextUtils;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import de.danoeh.antennapod.adapter.actionbutton.VisitWebsiteActionButton;
import io.reactivex.Observable;
import android.view.MenuItem;
import android.view.View;
import android.text.Layout;
import de.danoeh.antennapod.adapter.actionbutton.CancelDownloadActionButton;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays information about a FeedItem and actions.
 */
public class ItemFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "ItemFragment";

    private static final java.lang.String ARG_FEEDITEM = "feeditem";

    /**
     * Creates a new instance of an ItemFragment
     *
     * @param feeditem
     * 		The ID of the FeedItem to show
     * @return The ItemFragment instance
     */
    public static de.danoeh.antennapod.fragment.ItemFragment newInstance(long feeditem) {
        de.danoeh.antennapod.fragment.ItemFragment fragment;
        fragment = new de.danoeh.antennapod.fragment.ItemFragment();
        android.os.Bundle args;
        args = new android.os.Bundle();
        args.putLong(de.danoeh.antennapod.fragment.ItemFragment.ARG_FEEDITEM, feeditem);
        fragment.setArguments(args);
        return fragment;
    }


    private boolean itemsLoaded = false;

    private long itemId;

    private de.danoeh.antennapod.model.feed.FeedItem item;

    private java.lang.String webviewData;

    private android.view.ViewGroup root;

    private de.danoeh.antennapod.view.ShownotesWebView webvDescription;

    private android.widget.TextView txtvPodcast;

    private android.widget.TextView txtvTitle;

    private android.widget.TextView txtvDuration;

    private android.widget.TextView txtvPublished;

    private android.widget.ImageView imgvCover;

    private de.danoeh.antennapod.ui.common.CircularProgressBar progbarDownload;

    private android.widget.ProgressBar progbarLoading;

    private android.widget.TextView butAction1Text;

    private android.widget.TextView butAction2Text;

    private android.widget.ImageView butAction1Icon;

    private android.widget.ImageView butAction2Icon;

    private android.view.View butAction1;

    private android.view.View butAction2;

    private de.danoeh.antennapod.adapter.actionbutton.ItemActionButton actionButton1;

    private de.danoeh.antennapod.adapter.actionbutton.ItemActionButton actionButton2;

    private android.view.View noMediaLabel;

    private io.reactivex.disposables.Disposable disposable;

    private de.danoeh.antennapod.core.util.playback.PlaybackController controller;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // ItemFragment_0_LengthyGUICreationOperatorMutator
            case 137: {
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
    itemId = getArguments().getLong(de.danoeh.antennapod.fragment.ItemFragment.ARG_FEEDITEM);
}


@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, @androidx.annotation.Nullable
android.view.ViewGroup container, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    android.view.View layout;
    layout = inflater.inflate(de.danoeh.antennapod.R.layout.feeditem_fragment, container, false);
    switch(MUID_STATIC) {
        // ItemFragment_1_InvalidViewFocusOperatorMutator
        case 1137: {
            /**
            * Inserted by Kadabra
            */
            root = layout.findViewById(de.danoeh.antennapod.R.id.content_root);
            root.requestFocus();
            break;
        }
        // ItemFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2137: {
            /**
            * Inserted by Kadabra
            */
            root = layout.findViewById(de.danoeh.antennapod.R.id.content_root);
            root.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        root = layout.findViewById(de.danoeh.antennapod.R.id.content_root);
        break;
    }
}
switch(MUID_STATIC) {
    // ItemFragment_3_InvalidViewFocusOperatorMutator
    case 3137: {
        /**
        * Inserted by Kadabra
        */
        txtvPodcast = layout.findViewById(de.danoeh.antennapod.R.id.txtvPodcast);
        txtvPodcast.requestFocus();
        break;
    }
    // ItemFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4137: {
        /**
        * Inserted by Kadabra
        */
        txtvPodcast = layout.findViewById(de.danoeh.antennapod.R.id.txtvPodcast);
        txtvPodcast.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    txtvPodcast = layout.findViewById(de.danoeh.antennapod.R.id.txtvPodcast);
    break;
}
}
switch(MUID_STATIC) {
// ItemFragment_5_BuggyGUIListenerOperatorMutator
case 5137: {
    txtvPodcast.setOnClickListener(null);
    break;
}
default: {
txtvPodcast.setOnClickListener((android.view.View v) -> openPodcast());
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_6_InvalidViewFocusOperatorMutator
case 6137: {
/**
* Inserted by Kadabra
*/
txtvTitle = layout.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
txtvTitle.requestFocus();
break;
}
// ItemFragment_7_ViewComponentNotVisibleOperatorMutator
case 7137: {
/**
* Inserted by Kadabra
*/
txtvTitle = layout.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
txtvTitle.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvTitle = layout.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
break;
}
}
if (android.os.Build.VERSION.SDK_INT >= 23) {
txtvTitle.setHyphenationFrequency(android.text.Layout.HYPHENATION_FREQUENCY_FULL);
}
switch(MUID_STATIC) {
// ItemFragment_8_InvalidViewFocusOperatorMutator
case 8137: {
/**
* Inserted by Kadabra
*/
txtvDuration = layout.findViewById(de.danoeh.antennapod.R.id.txtvDuration);
txtvDuration.requestFocus();
break;
}
// ItemFragment_9_ViewComponentNotVisibleOperatorMutator
case 9137: {
/**
* Inserted by Kadabra
*/
txtvDuration = layout.findViewById(de.danoeh.antennapod.R.id.txtvDuration);
txtvDuration.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvDuration = layout.findViewById(de.danoeh.antennapod.R.id.txtvDuration);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_10_InvalidViewFocusOperatorMutator
case 10137: {
/**
* Inserted by Kadabra
*/
txtvPublished = layout.findViewById(de.danoeh.antennapod.R.id.txtvPublished);
txtvPublished.requestFocus();
break;
}
// ItemFragment_11_ViewComponentNotVisibleOperatorMutator
case 11137: {
/**
* Inserted by Kadabra
*/
txtvPublished = layout.findViewById(de.danoeh.antennapod.R.id.txtvPublished);
txtvPublished.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvPublished = layout.findViewById(de.danoeh.antennapod.R.id.txtvPublished);
break;
}
}
txtvTitle.setEllipsize(android.text.TextUtils.TruncateAt.END);
switch(MUID_STATIC) {
// ItemFragment_12_InvalidViewFocusOperatorMutator
case 12137: {
/**
* Inserted by Kadabra
*/
webvDescription = layout.findViewById(de.danoeh.antennapod.R.id.webvDescription);
webvDescription.requestFocus();
break;
}
// ItemFragment_13_ViewComponentNotVisibleOperatorMutator
case 13137: {
/**
* Inserted by Kadabra
*/
webvDescription = layout.findViewById(de.danoeh.antennapod.R.id.webvDescription);
webvDescription.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
webvDescription = layout.findViewById(de.danoeh.antennapod.R.id.webvDescription);
break;
}
}
webvDescription.setTimecodeSelectedListener((java.lang.Integer time) -> {
if ((((controller != null) && (item.getMedia() != null)) && (controller.getMedia() != null)) && java.util.Objects.equals(item.getMedia().getIdentifier(), controller.getMedia().getIdentifier())) {
controller.seekTo(time);
} else {
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.play_this_to_seek_position, com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
}
});
registerForContextMenu(webvDescription);
switch(MUID_STATIC) {
// ItemFragment_14_InvalidViewFocusOperatorMutator
case 14137: {
/**
* Inserted by Kadabra
*/
imgvCover = layout.findViewById(de.danoeh.antennapod.R.id.imgvCover);
imgvCover.requestFocus();
break;
}
// ItemFragment_15_ViewComponentNotVisibleOperatorMutator
case 15137: {
/**
* Inserted by Kadabra
*/
imgvCover = layout.findViewById(de.danoeh.antennapod.R.id.imgvCover);
imgvCover.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
imgvCover = layout.findViewById(de.danoeh.antennapod.R.id.imgvCover);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_16_BuggyGUIListenerOperatorMutator
case 16137: {
imgvCover.setOnClickListener(null);
break;
}
default: {
imgvCover.setOnClickListener((android.view.View v) -> openPodcast());
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_17_InvalidViewFocusOperatorMutator
case 17137: {
/**
* Inserted by Kadabra
*/
progbarDownload = layout.findViewById(de.danoeh.antennapod.R.id.circularProgressBar);
progbarDownload.requestFocus();
break;
}
// ItemFragment_18_ViewComponentNotVisibleOperatorMutator
case 18137: {
/**
* Inserted by Kadabra
*/
progbarDownload = layout.findViewById(de.danoeh.antennapod.R.id.circularProgressBar);
progbarDownload.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progbarDownload = layout.findViewById(de.danoeh.antennapod.R.id.circularProgressBar);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_19_InvalidViewFocusOperatorMutator
case 19137: {
/**
* Inserted by Kadabra
*/
progbarLoading = layout.findViewById(de.danoeh.antennapod.R.id.progbarLoading);
progbarLoading.requestFocus();
break;
}
// ItemFragment_20_ViewComponentNotVisibleOperatorMutator
case 20137: {
/**
* Inserted by Kadabra
*/
progbarLoading = layout.findViewById(de.danoeh.antennapod.R.id.progbarLoading);
progbarLoading.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progbarLoading = layout.findViewById(de.danoeh.antennapod.R.id.progbarLoading);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_21_InvalidViewFocusOperatorMutator
case 21137: {
/**
* Inserted by Kadabra
*/
butAction1 = layout.findViewById(de.danoeh.antennapod.R.id.butAction1);
butAction1.requestFocus();
break;
}
// ItemFragment_22_ViewComponentNotVisibleOperatorMutator
case 22137: {
/**
* Inserted by Kadabra
*/
butAction1 = layout.findViewById(de.danoeh.antennapod.R.id.butAction1);
butAction1.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butAction1 = layout.findViewById(de.danoeh.antennapod.R.id.butAction1);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_23_InvalidViewFocusOperatorMutator
case 23137: {
/**
* Inserted by Kadabra
*/
butAction2 = layout.findViewById(de.danoeh.antennapod.R.id.butAction2);
butAction2.requestFocus();
break;
}
// ItemFragment_24_ViewComponentNotVisibleOperatorMutator
case 24137: {
/**
* Inserted by Kadabra
*/
butAction2 = layout.findViewById(de.danoeh.antennapod.R.id.butAction2);
butAction2.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butAction2 = layout.findViewById(de.danoeh.antennapod.R.id.butAction2);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_25_InvalidViewFocusOperatorMutator
case 25137: {
/**
* Inserted by Kadabra
*/
butAction1Icon = layout.findViewById(de.danoeh.antennapod.R.id.butAction1Icon);
butAction1Icon.requestFocus();
break;
}
// ItemFragment_26_ViewComponentNotVisibleOperatorMutator
case 26137: {
/**
* Inserted by Kadabra
*/
butAction1Icon = layout.findViewById(de.danoeh.antennapod.R.id.butAction1Icon);
butAction1Icon.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butAction1Icon = layout.findViewById(de.danoeh.antennapod.R.id.butAction1Icon);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_27_InvalidViewFocusOperatorMutator
case 27137: {
/**
* Inserted by Kadabra
*/
butAction2Icon = layout.findViewById(de.danoeh.antennapod.R.id.butAction2Icon);
butAction2Icon.requestFocus();
break;
}
// ItemFragment_28_ViewComponentNotVisibleOperatorMutator
case 28137: {
/**
* Inserted by Kadabra
*/
butAction2Icon = layout.findViewById(de.danoeh.antennapod.R.id.butAction2Icon);
butAction2Icon.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butAction2Icon = layout.findViewById(de.danoeh.antennapod.R.id.butAction2Icon);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_29_InvalidViewFocusOperatorMutator
case 29137: {
/**
* Inserted by Kadabra
*/
butAction1Text = layout.findViewById(de.danoeh.antennapod.R.id.butAction1Text);
butAction1Text.requestFocus();
break;
}
// ItemFragment_30_ViewComponentNotVisibleOperatorMutator
case 30137: {
/**
* Inserted by Kadabra
*/
butAction1Text = layout.findViewById(de.danoeh.antennapod.R.id.butAction1Text);
butAction1Text.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butAction1Text = layout.findViewById(de.danoeh.antennapod.R.id.butAction1Text);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_31_InvalidViewFocusOperatorMutator
case 31137: {
/**
* Inserted by Kadabra
*/
butAction2Text = layout.findViewById(de.danoeh.antennapod.R.id.butAction2Text);
butAction2Text.requestFocus();
break;
}
// ItemFragment_32_ViewComponentNotVisibleOperatorMutator
case 32137: {
/**
* Inserted by Kadabra
*/
butAction2Text = layout.findViewById(de.danoeh.antennapod.R.id.butAction2Text);
butAction2Text.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
butAction2Text = layout.findViewById(de.danoeh.antennapod.R.id.butAction2Text);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_33_InvalidViewFocusOperatorMutator
case 33137: {
/**
* Inserted by Kadabra
*/
noMediaLabel = layout.findViewById(de.danoeh.antennapod.R.id.noMediaLabel);
noMediaLabel.requestFocus();
break;
}
// ItemFragment_34_ViewComponentNotVisibleOperatorMutator
case 34137: {
/**
* Inserted by Kadabra
*/
noMediaLabel = layout.findViewById(de.danoeh.antennapod.R.id.noMediaLabel);
noMediaLabel.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
noMediaLabel = layout.findViewById(de.danoeh.antennapod.R.id.noMediaLabel);
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_35_BuggyGUIListenerOperatorMutator
case 35137: {
butAction1.setOnClickListener(null);
break;
}
default: {
butAction1.setOnClickListener((android.view.View v) -> {
if (((actionButton1 instanceof de.danoeh.antennapod.adapter.actionbutton.StreamActionButton) && (!de.danoeh.antennapod.storage.preferences.UserPreferences.isStreamOverDownload())) && de.danoeh.antennapod.core.preferences.UsageStatistics.hasSignificantBiasTo(de.danoeh.antennapod.core.preferences.UsageStatistics.ACTION_STREAM)) {
showOnDemandConfigBalloon(true);
return;
} else if (actionButton1 == null) {
return// Not loaded yet
;// Not loaded yet

}
actionButton1.onClick(getContext());
});
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_36_BuggyGUIListenerOperatorMutator
case 36137: {
butAction2.setOnClickListener(null);
break;
}
default: {
butAction2.setOnClickListener((android.view.View v) -> {
if (((actionButton2 instanceof de.danoeh.antennapod.adapter.actionbutton.DownloadActionButton) && de.danoeh.antennapod.storage.preferences.UserPreferences.isStreamOverDownload()) && de.danoeh.antennapod.core.preferences.UsageStatistics.hasSignificantBiasTo(de.danoeh.antennapod.core.preferences.UsageStatistics.ACTION_DOWNLOAD)) {
showOnDemandConfigBalloon(false);
return;
} else if (actionButton2 == null) {
return// Not loaded yet
;// Not loaded yet

}
actionButton2.onClick(getContext());
});
break;
}
}
return layout;
}


private void showOnDemandConfigBalloon(boolean offerStreaming) {
final boolean isLocaleRtl;
isLocaleRtl = android.text.TextUtils.getLayoutDirectionFromLocale(java.util.Locale.getDefault()) == android.view.View.LAYOUT_DIRECTION_RTL;
final com.skydoves.balloon.Balloon balloon;
switch(MUID_STATIC) {
// ItemFragment_37_BinaryMutator
case 37137: {
balloon = new com.skydoves.balloon.Balloon.Builder(getContext()).setArrowOrientation(com.skydoves.balloon.ArrowOrientation.TOP).setArrowOrientationRules(com.skydoves.balloon.ArrowOrientationRules.ALIGN_FIXED).setArrowPosition(0.25F - (isLocaleRtl ^ offerStreaming ? 0.0F : 0.5F)).setWidthRatio(1.0F).setMarginLeft(8).setMarginRight(8).setBackgroundColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(getContext(), de.danoeh.antennapod.R.attr.colorSecondary)).setBalloonAnimation(com.skydoves.balloon.BalloonAnimation.OVERSHOOT).setLayout(de.danoeh.antennapod.R.layout.popup_bubble_view).setDismissWhenTouchOutside(true).setLifecycleOwner(this).build();
break;
}
default: {
balloon = new com.skydoves.balloon.Balloon.Builder(getContext()).setArrowOrientation(com.skydoves.balloon.ArrowOrientation.TOP).setArrowOrientationRules(com.skydoves.balloon.ArrowOrientationRules.ALIGN_FIXED).setArrowPosition(0.25F + (isLocaleRtl ^ offerStreaming ? 0.0F : 0.5F)).setWidthRatio(1.0F).setMarginLeft(8).setMarginRight(8).setBackgroundColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(getContext(), de.danoeh.antennapod.R.attr.colorSecondary)).setBalloonAnimation(com.skydoves.balloon.BalloonAnimation.OVERSHOOT).setLayout(de.danoeh.antennapod.R.layout.popup_bubble_view).setDismissWhenTouchOutside(true).setLifecycleOwner(this).build();
break;
}
}
final android.widget.Button positiveButton;
switch(MUID_STATIC) {
// ItemFragment_38_InvalidViewFocusOperatorMutator
case 38137: {
/**
* Inserted by Kadabra
*/
positiveButton = balloon.getContentView().findViewById(de.danoeh.antennapod.R.id.balloon_button_positive);
positiveButton.requestFocus();
break;
}
// ItemFragment_39_ViewComponentNotVisibleOperatorMutator
case 39137: {
/**
* Inserted by Kadabra
*/
positiveButton = balloon.getContentView().findViewById(de.danoeh.antennapod.R.id.balloon_button_positive);
positiveButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
positiveButton = balloon.getContentView().findViewById(de.danoeh.antennapod.R.id.balloon_button_positive);
break;
}
}
final android.widget.Button negativeButton;
switch(MUID_STATIC) {
// ItemFragment_40_InvalidViewFocusOperatorMutator
case 40137: {
/**
* Inserted by Kadabra
*/
negativeButton = balloon.getContentView().findViewById(de.danoeh.antennapod.R.id.balloon_button_negative);
negativeButton.requestFocus();
break;
}
// ItemFragment_41_ViewComponentNotVisibleOperatorMutator
case 41137: {
/**
* Inserted by Kadabra
*/
negativeButton = balloon.getContentView().findViewById(de.danoeh.antennapod.R.id.balloon_button_negative);
negativeButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
negativeButton = balloon.getContentView().findViewById(de.danoeh.antennapod.R.id.balloon_button_negative);
break;
}
}
final android.widget.TextView message;
switch(MUID_STATIC) {
// ItemFragment_42_InvalidViewFocusOperatorMutator
case 42137: {
/**
* Inserted by Kadabra
*/
message = balloon.getContentView().findViewById(de.danoeh.antennapod.R.id.balloon_message);
message.requestFocus();
break;
}
// ItemFragment_43_ViewComponentNotVisibleOperatorMutator
case 43137: {
/**
* Inserted by Kadabra
*/
message = balloon.getContentView().findViewById(de.danoeh.antennapod.R.id.balloon_message);
message.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
message = balloon.getContentView().findViewById(de.danoeh.antennapod.R.id.balloon_message);
break;
}
}
message.setText(offerStreaming ? de.danoeh.antennapod.R.string.on_demand_config_stream_text : de.danoeh.antennapod.R.string.on_demand_config_download_text);
switch(MUID_STATIC) {
// ItemFragment_44_BuggyGUIListenerOperatorMutator
case 44137: {
positiveButton.setOnClickListener(null);
break;
}
default: {
positiveButton.setOnClickListener((android.view.View v1) -> {
de.danoeh.antennapod.storage.preferences.UserPreferences.setStreamOverDownload(offerStreaming);
// Update all visible lists to reflect new streaming action button
org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.UnreadItemsUpdateEvent());
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).showSnackbarAbovePlayer(de.danoeh.antennapod.R.string.on_demand_config_setting_changed, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
balloon.dismiss();
});
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_45_BuggyGUIListenerOperatorMutator
case 45137: {
negativeButton.setOnClickListener(null);
break;
}
default: {
negativeButton.setOnClickListener((android.view.View v1) -> {
de.danoeh.antennapod.core.preferences.UsageStatistics.doNotAskAgain(de.danoeh.antennapod.core.preferences.UsageStatistics.ACTION_STREAM)// Type does not matter. Both are silenced.
;// Type does not matter. Both are silenced.

balloon.dismiss();
});
break;
}
}
switch(MUID_STATIC) {
// ItemFragment_46_BinaryMutator
case 46137: {
balloon.showAlignBottom(butAction1, 0, ((int) ((-12) / getResources().getDisplayMetrics().density)));
break;
}
default: {
balloon.showAlignBottom(butAction1, 0, ((int) ((-12) * getResources().getDisplayMetrics().density)));
break;
}
}
}


@java.lang.Override
public void onStart() {
super.onStart();
org.greenrobot.eventbus.EventBus.getDefault().register(this);
controller = new de.danoeh.antennapod.core.util.playback.PlaybackController(getActivity()) {
@java.lang.Override
public void loadMediaInfo() {
// Do nothing
}

};
controller.init();
load();
}


@java.lang.Override
public void onResume() {
super.onResume();
if (itemsLoaded) {
progbarLoading.setVisibility(android.view.View.GONE);
updateAppearance();
}
}


@java.lang.Override
public void onStop() {
super.onStop();
org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
controller.release();
}


@java.lang.Override
public void onDestroyView() {
super.onDestroyView();
if (disposable != null) {
disposable.dispose();
}
if ((webvDescription != null) && (root != null)) {
root.removeView(webvDescription);
webvDescription.destroy();
}
}


private void onFragmentLoaded() {
if ((webviewData != null) && (!itemsLoaded)) {
webvDescription.loadDataWithBaseURL("https://127.0.0.1", webviewData, "text/html", "utf-8", "about:blank");
}
updateAppearance();
}


private void updateAppearance() {
if (item == null) {
android.util.Log.d(de.danoeh.antennapod.fragment.ItemFragment.TAG, "updateAppearance item is null");
return;
}
txtvPodcast.setText(item.getFeed().getTitle());
txtvTitle.setText(item.getTitle());
if (item.getPubDate() != null) {
java.lang.String pubDateStr;
pubDateStr = de.danoeh.antennapod.core.util.DateFormatter.formatAbbrev(getActivity(), item.getPubDate());
txtvPublished.setText(pubDateStr);
txtvPublished.setContentDescription(de.danoeh.antennapod.core.util.DateFormatter.formatForAccessibility(item.getPubDate()));
}
com.bumptech.glide.request.RequestOptions options;
switch(MUID_STATIC) {
// ItemFragment_47_BinaryMutator
case 47137: {
options = new com.bumptech.glide.request.RequestOptions().error(de.danoeh.antennapod.R.color.light_gray).transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (8 / getResources().getDisplayMetrics().density)))).dontAnimate();
break;
}
default: {
options = new com.bumptech.glide.request.RequestOptions().error(de.danoeh.antennapod.R.color.light_gray).transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (8 * getResources().getDisplayMetrics().density)))).dontAnimate();
break;
}
}
com.bumptech.glide.Glide.with(this).load(item.getImageLocation()).error(com.bumptech.glide.Glide.with(this).load(de.danoeh.antennapod.core.feed.util.ImageResourceUtils.getFallbackImageLocation(item)).apply(options)).apply(options).into(imgvCover);
updateButtons();
}


private void updateButtons() {
progbarDownload.setVisibility(android.view.View.GONE);
if (item.hasMedia()) {
if (de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isDownloadingEpisode(item.getMedia().getDownload_url())) {
progbarDownload.setVisibility(android.view.View.VISIBLE);
switch(MUID_STATIC) {
// ItemFragment_48_BinaryMutator
case 48137: {
progbarDownload.setPercentage(0.01F / java.lang.Math.max(1, de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().getProgress(item.getMedia().getDownload_url())), item);
break;
}
default: {
progbarDownload.setPercentage(0.01F * java.lang.Math.max(1, de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().getProgress(item.getMedia().getDownload_url())), item);
break;
}
}
progbarDownload.setIndeterminate(de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isEpisodeQueued(item.getMedia().getDownload_url()));
}
}
de.danoeh.antennapod.model.feed.FeedMedia media;
media = item.getMedia();
if (media == null) {
actionButton1 = new de.danoeh.antennapod.adapter.actionbutton.MarkAsPlayedActionButton(item);
actionButton2 = new de.danoeh.antennapod.adapter.actionbutton.VisitWebsiteActionButton(item);
noMediaLabel.setVisibility(android.view.View.VISIBLE);
} else {
noMediaLabel.setVisibility(android.view.View.GONE);
if (media.getDuration() > 0) {
txtvDuration.setText(de.danoeh.antennapod.core.util.Converter.getDurationStringLong(media.getDuration()));
txtvDuration.setContentDescription(de.danoeh.antennapod.core.util.Converter.getDurationStringLocalized(getContext(), media.getDuration()));
}
if (de.danoeh.antennapod.core.util.PlaybackStatus.isCurrentlyPlaying(media)) {
actionButton1 = new de.danoeh.antennapod.adapter.actionbutton.PauseActionButton(item);
} else if (item.getFeed().isLocalFeed()) {
actionButton1 = new de.danoeh.antennapod.adapter.actionbutton.PlayLocalActionButton(item);
} else if (media.isDownloaded()) {
actionButton1 = new de.danoeh.antennapod.adapter.actionbutton.PlayActionButton(item);
} else {
actionButton1 = new de.danoeh.antennapod.adapter.actionbutton.StreamActionButton(item);
}
if (de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().isDownloadingEpisode(media.getDownload_url())) {
actionButton2 = new de.danoeh.antennapod.adapter.actionbutton.CancelDownloadActionButton(item);
} else if (!media.isDownloaded()) {
actionButton2 = new de.danoeh.antennapod.adapter.actionbutton.DownloadActionButton(item);
} else {
actionButton2 = new de.danoeh.antennapod.adapter.actionbutton.DeleteActionButton(item);
}
}
butAction1Text.setText(actionButton1.getLabel());
butAction1Text.setTransformationMethod(null);
butAction1Icon.setImageResource(actionButton1.getDrawable());
butAction1.setVisibility(actionButton1.getVisibility());
butAction2Text.setText(actionButton2.getLabel());
butAction2Text.setTransformationMethod(null);
butAction2Icon.setImageResource(actionButton2.getDrawable());
butAction2.setVisibility(actionButton2.getVisibility());
}


@java.lang.Override
public boolean onContextItemSelected(android.view.MenuItem item) {
return webvDescription.onContextItemSelected(item);
}


private void openPodcast() {
if (item == null) {
return;
}
androidx.fragment.app.Fragment fragment;
fragment = de.danoeh.antennapod.fragment.FeedItemlistFragment.newInstance(item.getFeedId());
((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(fragment);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.FeedItemEvent event) {
android.util.Log.d(de.danoeh.antennapod.fragment.ItemFragment.TAG, (("onEventMainThread() called with: " + "event = [") + event) + "]");
for (de.danoeh.antennapod.model.feed.FeedItem item : event.items) {
if (this.item.getId() == item.getId()) {
load();
return;
}
}
}


@org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onEventMainThread(de.danoeh.antennapod.event.EpisodeDownloadEvent event) {
if ((item == null) || (item.getMedia() == null)) {
return;
}
if (!event.getUrls().contains(item.getMedia().getDownload_url())) {
return;
}
if (itemsLoaded && (getActivity() != null)) {
updateButtons();
}
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onPlayerStatusChanged(de.danoeh.antennapod.event.PlayerStatusEvent event) {
updateButtons();
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onUnreadItemsChanged(de.danoeh.antennapod.event.UnreadItemsUpdateEvent event) {
load();
}


private void load() {
if (disposable != null) {
disposable.dispose();
}
if (!itemsLoaded) {
progbarLoading.setVisibility(android.view.View.VISIBLE);
}
disposable = io.reactivex.Observable.fromCallable(this::loadInBackground).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.model.feed.FeedItem result) -> {
progbarLoading.setVisibility(android.view.View.GONE);
item = result;
onFragmentLoaded();
itemsLoaded = true;
}, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.fragment.ItemFragment.TAG, android.util.Log.getStackTraceString(error)));
}


@androidx.annotation.Nullable
private de.danoeh.antennapod.model.feed.FeedItem loadInBackground() {
de.danoeh.antennapod.model.feed.FeedItem feedItem;
feedItem = de.danoeh.antennapod.core.storage.DBReader.getFeedItem(itemId);
android.content.Context context;
context = getContext();
if ((feedItem != null) && (context != null)) {
int duration;
duration = (feedItem.getMedia() != null) ? feedItem.getMedia().getDuration() : java.lang.Integer.MAX_VALUE;
de.danoeh.antennapod.core.storage.DBReader.loadDescriptionOfFeedItem(feedItem);
de.danoeh.antennapod.core.util.gui.ShownotesCleaner t;
t = new de.danoeh.antennapod.core.util.gui.ShownotesCleaner(context, feedItem.getDescription(), duration);
webviewData = t.processShownotes();
}
return feedItem;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
