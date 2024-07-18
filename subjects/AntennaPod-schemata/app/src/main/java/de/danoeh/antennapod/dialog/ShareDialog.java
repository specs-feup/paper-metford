package de.danoeh.antennapod.dialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import de.danoeh.antennapod.core.util.ShareUtils;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import de.danoeh.antennapod.databinding.ShareEpisodeDialogBinding;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.ViewGroup;
import de.danoeh.antennapod.model.feed.FeedItem;
import androidx.annotation.Nullable;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ShareDialog extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String ARGUMENT_FEED_ITEM = "feedItem";

    private static final java.lang.String PREF_NAME = "ShareDialog";

    private static final java.lang.String PREF_SHARE_EPISODE_START_AT = "prefShareEpisodeStartAt";

    private static final java.lang.String PREF_SHARE_EPISODE_TYPE = "prefShareEpisodeType";

    private android.content.Context ctx;

    private de.danoeh.antennapod.model.feed.FeedItem item;

    private android.content.SharedPreferences prefs;

    private de.danoeh.antennapod.databinding.ShareEpisodeDialogBinding viewBinding;

    public ShareDialog() {
        // Empty constructor required for DialogFragment
    }


    public static de.danoeh.antennapod.dialog.ShareDialog newInstance(de.danoeh.antennapod.model.feed.FeedItem item) {
        android.os.Bundle arguments;
        arguments = new android.os.Bundle();
        arguments.putSerializable(de.danoeh.antennapod.dialog.ShareDialog.ARGUMENT_FEED_ITEM, item);
        de.danoeh.antennapod.dialog.ShareDialog dialog;
        dialog = new de.danoeh.antennapod.dialog.ShareDialog();
        dialog.setArguments(arguments);
        return dialog;
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        if (getArguments() != null) {
            ctx = getActivity();
            item = ((de.danoeh.antennapod.model.feed.FeedItem) (getArguments().getSerializable(de.danoeh.antennapod.dialog.ShareDialog.ARGUMENT_FEED_ITEM)));
            prefs = getActivity().getSharedPreferences(de.danoeh.antennapod.dialog.ShareDialog.PREF_NAME, android.content.Context.MODE_PRIVATE);
        }
        viewBinding = de.danoeh.antennapod.databinding.ShareEpisodeDialogBinding.inflate(inflater);
        viewBinding.shareDialogRadioGroup.setOnCheckedChangeListener((android.widget.RadioGroup group,int checkedId) -> viewBinding.sharePositionCheckbox.setEnabled(checkedId == viewBinding.shareSocialRadio.getId()));
        setupOptions();
        switch(MUID_STATIC) {
            // ShareDialog_0_BuggyGUIListenerOperatorMutator
            case 49: {
                viewBinding.shareButton.setOnClickListener(null);
                break;
            }
            default: {
            viewBinding.shareButton.setOnClickListener((android.view.View v) -> {
                boolean includePlaybackPosition;
                includePlaybackPosition = viewBinding.sharePositionCheckbox.isChecked();
                int position;
                if (viewBinding.shareSocialRadio.isChecked()) {
                    de.danoeh.antennapod.core.util.ShareUtils.shareFeedItemLinkWithDownloadLink(ctx, item, includePlaybackPosition);
                    position = 1;
                } else if (viewBinding.shareMediaReceiverRadio.isChecked()) {
                    de.danoeh.antennapod.core.util.ShareUtils.shareMediaDownloadLink(ctx, item.getMedia());
                    position = 2;
                } else if (viewBinding.shareMediaFileRadio.isChecked()) {
                    de.danoeh.antennapod.core.util.ShareUtils.shareFeedItemFile(ctx, item.getMedia());
                    position = 3;
                } else {
                    throw new java.lang.IllegalStateException("Unknown share method");
                }
                prefs.edit().putBoolean(de.danoeh.antennapod.dialog.ShareDialog.PREF_SHARE_EPISODE_START_AT, includePlaybackPosition).putInt(de.danoeh.antennapod.dialog.ShareDialog.PREF_SHARE_EPISODE_TYPE, position).apply();
                dismiss();
            });
            break;
        }
    }
    return viewBinding.getRoot();
}


private void setupOptions() {
    final boolean hasMedia;
    hasMedia = item.getMedia() != null;
    boolean downloaded;
    downloaded = hasMedia && item.getMedia().isDownloaded();
    viewBinding.shareMediaFileRadio.setVisibility(downloaded ? android.view.View.VISIBLE : android.view.View.GONE);
    boolean hasDownloadUrl;
    hasDownloadUrl = hasMedia && (item.getMedia().getDownload_url() != null);
    if (!hasDownloadUrl) {
        viewBinding.shareMediaReceiverRadio.setVisibility(android.view.View.GONE);
    }
    int type;
    type = prefs.getInt(de.danoeh.antennapod.dialog.ShareDialog.PREF_SHARE_EPISODE_TYPE, 1);
    if (((type == 2) && (!hasDownloadUrl)) || ((type == 3) && (!downloaded))) {
        type = 1;
    }
    viewBinding.shareSocialRadio.setChecked(type == 1);
    viewBinding.shareMediaReceiverRadio.setChecked(type == 2);
    viewBinding.shareMediaFileRadio.setChecked(type == 3);
    boolean switchIsChecked;
    switchIsChecked = prefs.getBoolean(de.danoeh.antennapod.dialog.ShareDialog.PREF_SHARE_EPISODE_START_AT, false);
    viewBinding.sharePositionCheckbox.setChecked(switchIsChecked);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
