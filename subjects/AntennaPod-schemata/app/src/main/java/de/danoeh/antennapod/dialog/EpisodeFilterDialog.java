package de.danoeh.antennapod.dialog;
import de.danoeh.antennapod.adapter.SimpleChipAdapter;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import android.text.TextUtils;
import de.danoeh.antennapod.databinding.EpisodeFilterDialogBinding;
import de.danoeh.antennapod.view.ItemOffsetDecoration;
import java.util.List;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.Context;
import de.danoeh.antennapod.model.feed.FeedFilter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays a dialog with a text box for filtering episodes and two radio buttons for exclusion/inclusion
 */
public abstract class EpisodeFilterDialog extends com.google.android.material.dialog.MaterialAlertDialogBuilder {
    static final int MUID_STATIC = getMUID();
    private final de.danoeh.antennapod.databinding.EpisodeFilterDialogBinding viewBinding;

    private final java.util.List<java.lang.String> termList;

    public EpisodeFilterDialog(android.content.Context context, de.danoeh.antennapod.model.feed.FeedFilter filter) {
        super(context);
        viewBinding = de.danoeh.antennapod.databinding.EpisodeFilterDialogBinding.inflate(android.view.LayoutInflater.from(context));
        setTitle(de.danoeh.antennapod.R.string.episode_filters_label);
        setView(viewBinding.getRoot());
        viewBinding.durationCheckBox.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> viewBinding.episodeFilterDurationText.setEnabled(isChecked));
        if (filter.hasMinimalDurationFilter()) {
            viewBinding.durationCheckBox.setChecked(true);
            switch(MUID_STATIC) {
                // EpisodeFilterDialog_0_BinaryMutator
                case 57: {
                    // Store minimal duration in seconds, show in minutes
                    viewBinding.episodeFilterDurationText.setText(java.lang.String.valueOf(filter.getMinimalDurationFilter() * 60));
                    break;
                }
                default: {
                // Store minimal duration in seconds, show in minutes
                viewBinding.episodeFilterDurationText.setText(java.lang.String.valueOf(filter.getMinimalDurationFilter() / 60));
                break;
            }
        }
    } else {
        viewBinding.episodeFilterDurationText.setEnabled(false);
    }
    if (filter.excludeOnly()) {
        termList = filter.getExcludeFilter();
        viewBinding.excludeRadio.setChecked(true);
    } else {
        termList = filter.getIncludeFilter();
        viewBinding.includeRadio.setChecked(true);
    }
    setupWordsList();
    setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
    switch(MUID_STATIC) {
        // EpisodeFilterDialog_1_BuggyGUIListenerOperatorMutator
        case 1057: {
            setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
            break;
        }
        default: {
        setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, this::onConfirmClick);
        break;
    }
}
}


private void setupWordsList() {
viewBinding.termsRecycler.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(getContext(), 2));
viewBinding.termsRecycler.addItemDecoration(new de.danoeh.antennapod.view.ItemOffsetDecoration(getContext(), 4));
de.danoeh.antennapod.adapter.SimpleChipAdapter adapter;
adapter = new de.danoeh.antennapod.adapter.SimpleChipAdapter(getContext()) {
    @java.lang.Override
    protected java.util.List<java.lang.String> getChips() {
        return termList;
    }


    @java.lang.Override
    protected void onRemoveClicked(int position) {
        termList.remove(position);
        notifyDataSetChanged();
    }

};
viewBinding.termsRecycler.setAdapter(adapter);
switch(MUID_STATIC) {
    // EpisodeFilterDialog_2_BuggyGUIListenerOperatorMutator
    case 2057: {
        viewBinding.termsTextInput.setEndIconOnClickListener(null);
        break;
    }
    default: {
    viewBinding.termsTextInput.setEndIconOnClickListener((android.view.View v) -> {
        java.lang.String newWord;
        newWord = viewBinding.termsTextInput.getEditText().getText().toString().replace("\"", "").trim();
        if (android.text.TextUtils.isEmpty(newWord) || termList.contains(newWord)) {
            return;
        }
        termList.add(newWord);
        viewBinding.termsTextInput.getEditText().setText("");
        adapter.notifyDataSetChanged();
    });
    break;
}
}
}


protected abstract void onConfirmed(de.danoeh.antennapod.model.feed.FeedFilter filter);


private void onConfirmClick(android.content.DialogInterface dialog, int which) {
int minimalDuration;
minimalDuration = -1;
if (viewBinding.durationCheckBox.isChecked()) {
switch(MUID_STATIC) {
    // EpisodeFilterDialog_3_BinaryMutator
    case 3057: {
        try {
            // Store minimal duration in seconds
            minimalDuration = java.lang.Integer.parseInt(viewBinding.episodeFilterDurationText.getText().toString()) / 60;
        } catch (java.lang.NumberFormatException e) {
            // Do not change anything on error
        }
        break;
    }
    default: {
    try {
        // Store minimal duration in seconds
        minimalDuration = java.lang.Integer.parseInt(viewBinding.episodeFilterDurationText.getText().toString()) * 60;
    } catch (java.lang.NumberFormatException e) {
        // Do not change anything on error
    }
    break;
}
}
}
java.lang.String excludeFilter;
excludeFilter = "";
java.lang.String includeFilter;
includeFilter = "";
if (viewBinding.includeRadio.isChecked()) {
includeFilter = toFilterString(termList);
} else {
excludeFilter = toFilterString(termList);
}
onConfirmed(new de.danoeh.antennapod.model.feed.FeedFilter(includeFilter, excludeFilter, minimalDuration));
}


private java.lang.String toFilterString(java.util.List<java.lang.String> words) {
java.lang.StringBuilder result;
result = new java.lang.StringBuilder();
for (java.lang.String word : words) {
result.append("\"").append(word).append("\" ");
}
return result.toString();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
