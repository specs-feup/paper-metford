package de.danoeh.antennapod.dialog;
import de.danoeh.antennapod.model.feed.FeedPreferences;
import java.util.Set;
import java.util.ArrayList;
import androidx.fragment.app.DialogFragment;
import de.danoeh.antennapod.databinding.EditTagsDialogBinding;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.adapter.SimpleChipAdapter;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import android.app.Dialog;
import java.util.List;
import java.util.HashSet;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.util.Log;
import de.danoeh.antennapod.core.storage.NavDrawerData;
import android.os.Bundle;
import io.reactivex.schedulers.Schedulers;
import android.text.TextUtils;
import io.reactivex.Observable;
import de.danoeh.antennapod.view.ItemOffsetDecoration;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import de.danoeh.antennapod.core.storage.DBReader;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TagSettingsDialog extends androidx.fragment.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "TagSettingsDialog";

    private static final java.lang.String ARG_FEED_PREFERENCES = "feed_preferences";

    private java.util.List<java.lang.String> displayedTags;

    private de.danoeh.antennapod.databinding.EditTagsDialogBinding viewBinding;

    private de.danoeh.antennapod.adapter.SimpleChipAdapter adapter;

    public static de.danoeh.antennapod.dialog.TagSettingsDialog newInstance(java.util.List<de.danoeh.antennapod.model.feed.FeedPreferences> preferencesList) {
        de.danoeh.antennapod.dialog.TagSettingsDialog fragment;
        fragment = new de.danoeh.antennapod.dialog.TagSettingsDialog();
        android.os.Bundle args;
        args = new android.os.Bundle();
        args.putSerializable(de.danoeh.antennapod.dialog.TagSettingsDialog.ARG_FEED_PREFERENCES, new java.util.ArrayList<>(preferencesList));
        fragment.setArguments(args);
        return fragment;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.app.Dialog onCreateDialog(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        java.util.ArrayList<de.danoeh.antennapod.model.feed.FeedPreferences> feedPreferencesList;
        feedPreferencesList = ((java.util.ArrayList<de.danoeh.antennapod.model.feed.FeedPreferences>) (getArguments().getSerializable(de.danoeh.antennapod.dialog.TagSettingsDialog.ARG_FEED_PREFERENCES)));
        java.util.Set<java.lang.String> commonTags;
        commonTags = new java.util.HashSet<>(feedPreferencesList.get(0).getTags());
        for (de.danoeh.antennapod.model.feed.FeedPreferences preference : feedPreferencesList) {
            commonTags.retainAll(preference.getTags());
        }
        displayedTags = new java.util.ArrayList<>(commonTags);
        displayedTags.remove(de.danoeh.antennapod.model.feed.FeedPreferences.TAG_ROOT);
        viewBinding = de.danoeh.antennapod.databinding.EditTagsDialogBinding.inflate(getLayoutInflater());
        viewBinding.tagsRecycler.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(getContext(), 2));
        viewBinding.tagsRecycler.addItemDecoration(new de.danoeh.antennapod.view.ItemOffsetDecoration(getContext(), 4));
        adapter = new de.danoeh.antennapod.adapter.SimpleChipAdapter(getContext()) {
            @java.lang.Override
            protected java.util.List<java.lang.String> getChips() {
                return displayedTags;
            }


            @java.lang.Override
            protected void onRemoveClicked(int position) {
                displayedTags.remove(position);
                notifyDataSetChanged();
            }

        };
        viewBinding.tagsRecycler.setAdapter(adapter);
        viewBinding.rootFolderCheckbox.setChecked(commonTags.contains(de.danoeh.antennapod.model.feed.FeedPreferences.TAG_ROOT));
        switch(MUID_STATIC) {
            // TagSettingsDialog_0_BuggyGUIListenerOperatorMutator
            case 68: {
                viewBinding.newTagTextInput.setEndIconOnClickListener(null);
                break;
            }
            default: {
            viewBinding.newTagTextInput.setEndIconOnClickListener((android.view.View v) -> addTag(viewBinding.newTagEditText.getText().toString().trim()));
            break;
        }
    }
    loadTags();
    viewBinding.newTagEditText.setThreshold(1);
    viewBinding.newTagEditText.setOnTouchListener(new android.view.View.OnTouchListener() {
        @java.lang.Override
        public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
            viewBinding.newTagEditText.showDropDown();
            viewBinding.newTagEditText.requestFocus();
            return false;
        }

    });
    if (feedPreferencesList.size() > 1) {
        viewBinding.commonTagsInfo.setVisibility(android.view.View.VISIBLE);
    }
    com.google.android.material.dialog.MaterialAlertDialogBuilder dialog;
    dialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
    dialog.setView(viewBinding.getRoot());
    dialog.setTitle(de.danoeh.antennapod.R.string.feed_tags_label);
    switch(MUID_STATIC) {
        // TagSettingsDialog_1_BuggyGUIListenerOperatorMutator
        case 1068: {
            dialog.setPositiveButton(android.R.string.ok, null);
            break;
        }
        default: {
        dialog.setPositiveButton(android.R.string.ok, (android.content.DialogInterface d,int input) -> {
            addTag(viewBinding.newTagEditText.getText().toString().trim());
            updatePreferencesTags(feedPreferencesList, commonTags);
        });
        break;
    }
}
dialog.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
return dialog.create();
}


private void loadTags() {
io.reactivex.Observable.fromCallable(() -> {
    de.danoeh.antennapod.core.storage.NavDrawerData data;
    data = de.danoeh.antennapod.core.storage.DBReader.getNavDrawerData(null);
    java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> items;
    items = data.items;
    java.util.List<java.lang.String> folders;
    folders = new java.util.ArrayList<java.lang.String>();
    for (de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem item : items) {
        if (item.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.TAG) {
            folders.add(item.getTitle());
        }
    }
    return folders;
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.List<java.lang.String> result) -> {
    android.widget.ArrayAdapter<java.lang.String> acAdapter;
    acAdapter = new android.widget.ArrayAdapter<java.lang.String>(getContext(), de.danoeh.antennapod.R.layout.single_tag_text_view, result);
    viewBinding.newTagEditText.setAdapter(acAdapter);
}, (java.lang.Throwable error) -> {
    android.util.Log.e(de.danoeh.antennapod.dialog.TagSettingsDialog.TAG, android.util.Log.getStackTraceString(error));
});
}


private void addTag(java.lang.String name) {
if (android.text.TextUtils.isEmpty(name) || displayedTags.contains(name)) {
    return;
}
displayedTags.add(name);
viewBinding.newTagEditText.setText("");
adapter.notifyDataSetChanged();
}


private void updatePreferencesTags(java.util.List<de.danoeh.antennapod.model.feed.FeedPreferences> feedPreferencesList, java.util.Set<java.lang.String> commonTags) {
if (viewBinding.rootFolderCheckbox.isChecked()) {
    displayedTags.add(de.danoeh.antennapod.model.feed.FeedPreferences.TAG_ROOT);
}
for (de.danoeh.antennapod.model.feed.FeedPreferences preferences : feedPreferencesList) {
    preferences.getTags().removeAll(commonTags);
    preferences.getTags().addAll(displayedTags);
    de.danoeh.antennapod.core.storage.DBWriter.setFeedPreferences(preferences);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
