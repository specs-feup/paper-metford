package de.danoeh.antennapod.dialog;
import androidx.appcompat.app.AlertDialog;
import de.danoeh.antennapod.databinding.FeeditemlistItemBinding;
import de.danoeh.antennapod.databinding.SwipeactionsPickerBinding;
import de.danoeh.antennapod.databinding.SwipeactionsPickerItemBinding;
import de.danoeh.antennapod.fragment.swipeactions.SwipeActions;
import de.danoeh.antennapod.fragment.AllEpisodesFragment;
import de.danoeh.antennapod.fragment.PlaybackHistoryFragment;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import de.danoeh.antennapod.fragment.QueueFragment;
import de.danoeh.antennapod.databinding.SwipeactionsRowBinding;
import androidx.gridlayout.widget.GridLayout;
import de.danoeh.antennapod.R;
import com.annimon.stream.Stream;
import java.util.List;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import de.danoeh.antennapod.databinding.SwipeactionsDialogBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.fragment.FeedItemlistFragment;
import android.view.View;
import androidx.appcompat.content.res.AppCompatResources;
import de.danoeh.antennapod.fragment.InboxFragment;
import android.view.LayoutInflater;
import de.danoeh.antennapod.fragment.CompletedDownloadsFragment;
import android.graphics.PorterDuff;
import de.danoeh.antennapod.fragment.swipeactions.SwipeAction;
import androidx.core.graphics.drawable.DrawableCompat;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SwipeActionsDialog {
    static final int MUID_STATIC = getMUID();
    private static final int LEFT = 1;

    private static final int RIGHT = 0;

    private final android.content.Context context;

    private final java.lang.String tag;

    private de.danoeh.antennapod.fragment.swipeactions.SwipeAction rightAction;

    private de.danoeh.antennapod.fragment.swipeactions.SwipeAction leftAction;

    private java.util.List<de.danoeh.antennapod.fragment.swipeactions.SwipeAction> keys;

    public SwipeActionsDialog(android.content.Context context, java.lang.String tag) {
        this.context = context;
        this.tag = tag;
    }


    public void show(de.danoeh.antennapod.dialog.SwipeActionsDialog.Callback prefsChanged) {
        de.danoeh.antennapod.fragment.swipeactions.SwipeActions.Actions actions;
        actions = de.danoeh.antennapod.fragment.swipeactions.SwipeActions.getPrefsWithDefaults(context, tag);
        leftAction = actions.left;
        rightAction = actions.right;
        final com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context);
        keys = de.danoeh.antennapod.fragment.swipeactions.SwipeActions.swipeActions;
        java.lang.String forFragment;
        forFragment = "";
        switch (tag) {
            case de.danoeh.antennapod.fragment.InboxFragment.TAG :
                forFragment = context.getString(de.danoeh.antennapod.R.string.inbox_label);
                keys = com.annimon.stream.Stream.of(keys).filter((de.danoeh.antennapod.fragment.swipeactions.SwipeAction a) -> ((!a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.TOGGLE_PLAYED)) && (!a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.DELETE))) && (!a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_HISTORY))).toList();
                break;
            case de.danoeh.antennapod.fragment.AllEpisodesFragment.TAG :
                forFragment = context.getString(de.danoeh.antennapod.R.string.episodes_label);
                keys = com.annimon.stream.Stream.of(keys).filter((de.danoeh.antennapod.fragment.swipeactions.SwipeAction a) -> !a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_HISTORY)).toList();
                break;
            case de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG :
                forFragment = context.getString(de.danoeh.antennapod.R.string.downloads_label);
                keys = com.annimon.stream.Stream.of(keys).filter((de.danoeh.antennapod.fragment.swipeactions.SwipeAction a) -> ((!a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_INBOX)) && (!a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_HISTORY))) && (!a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.START_DOWNLOAD))).toList();
                break;
            case de.danoeh.antennapod.fragment.FeedItemlistFragment.TAG :
                forFragment = context.getString(de.danoeh.antennapod.R.string.individual_subscription);
                keys = com.annimon.stream.Stream.of(keys).filter((de.danoeh.antennapod.fragment.swipeactions.SwipeAction a) -> !a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_HISTORY)).toList();
                break;
            case de.danoeh.antennapod.fragment.QueueFragment.TAG :
                forFragment = context.getString(de.danoeh.antennapod.R.string.queue_label);
                keys = com.annimon.stream.Stream.of(keys).filter((de.danoeh.antennapod.fragment.swipeactions.SwipeAction a) -> ((!a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.ADD_TO_QUEUE)) && (!a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_INBOX))) && (!a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_HISTORY))).toList();
                break;
            case de.danoeh.antennapod.fragment.PlaybackHistoryFragment.TAG :
                forFragment = context.getString(de.danoeh.antennapod.R.string.playback_history_label);
                keys = com.annimon.stream.Stream.of(keys).filter((de.danoeh.antennapod.fragment.swipeactions.SwipeAction a) -> !a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_INBOX)).toList();
                break;
            default :
                break;
        }
        if (!tag.equals(de.danoeh.antennapod.fragment.QueueFragment.TAG)) {
            keys = com.annimon.stream.Stream.of(keys).filter((de.danoeh.antennapod.fragment.swipeactions.SwipeAction a) -> !a.getId().equals(de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_QUEUE)).toList();
        }
        builder.setTitle((context.getString(de.danoeh.antennapod.R.string.swipeactions_label) + " - ") + forFragment);
        de.danoeh.antennapod.databinding.SwipeactionsDialogBinding viewBinding;
        viewBinding = de.danoeh.antennapod.databinding.SwipeactionsDialogBinding.inflate(android.view.LayoutInflater.from(context));
        builder.setView(viewBinding.getRoot());
        viewBinding.enableSwitch.setOnCheckedChangeListener((android.widget.CompoundButton compoundButton,boolean b) -> {
            viewBinding.actionLeftContainer.getRoot().setAlpha(b ? 1.0F : 0.4F);
            viewBinding.actionRightContainer.getRoot().setAlpha(b ? 1.0F : 0.4F);
        });
        viewBinding.enableSwitch.setChecked(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.isSwipeActionEnabled(context, tag));
        setupSwipeDirectionView(viewBinding.actionLeftContainer, de.danoeh.antennapod.dialog.SwipeActionsDialog.LEFT);
        setupSwipeDirectionView(viewBinding.actionRightContainer, de.danoeh.antennapod.dialog.SwipeActionsDialog.RIGHT);
        switch(MUID_STATIC) {
            // SwipeActionsDialog_0_BuggyGUIListenerOperatorMutator
            case 62: {
                builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
                break;
            }
            default: {
            builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> {
                savePrefs(tag, rightAction.getId(), leftAction.getId());
                saveActionsEnabledPrefs(viewBinding.enableSwitch.isChecked());
                prefsChanged.onCall();
            });
            break;
        }
    }
    builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
    builder.create().show();
}


private void setupSwipeDirectionView(de.danoeh.antennapod.databinding.SwipeactionsRowBinding view, int direction) {
    de.danoeh.antennapod.fragment.swipeactions.SwipeAction action;
    action = (direction == de.danoeh.antennapod.dialog.SwipeActionsDialog.LEFT) ? leftAction : rightAction;
    view.swipeDirectionLabel.setText(direction == de.danoeh.antennapod.dialog.SwipeActionsDialog.LEFT ? de.danoeh.antennapod.R.string.swipe_left : de.danoeh.antennapod.R.string.swipe_right);
    view.swipeActionLabel.setText(action.getTitle(context));
    populateMockEpisode(view.mockEpisode);
    if ((direction == de.danoeh.antennapod.dialog.SwipeActionsDialog.RIGHT) && (view.previewContainer.getChildAt(0) != view.swipeIcon)) {
        view.previewContainer.removeView(view.swipeIcon);
        view.previewContainer.addView(view.swipeIcon, 0);
    }
    view.swipeIcon.setImageResource(action.getActionIcon());
    view.swipeIcon.setColorFilter(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, action.getActionColor()));
    switch(MUID_STATIC) {
        // SwipeActionsDialog_1_BuggyGUIListenerOperatorMutator
        case 1062: {
            view.changeButton.setOnClickListener(null);
            break;
        }
        default: {
        view.changeButton.setOnClickListener((android.view.View v) -> showPicker(view, direction));
        break;
    }
}
switch(MUID_STATIC) {
    // SwipeActionsDialog_2_BuggyGUIListenerOperatorMutator
    case 2062: {
        view.previewContainer.setOnClickListener(null);
        break;
    }
    default: {
    view.previewContainer.setOnClickListener((android.view.View v) -> showPicker(view, direction));
    break;
}
}
}


private void showPicker(de.danoeh.antennapod.databinding.SwipeactionsRowBinding view, int direction) {
com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context);
builder.setTitle(direction == de.danoeh.antennapod.dialog.SwipeActionsDialog.LEFT ? de.danoeh.antennapod.R.string.swipe_left : de.danoeh.antennapod.R.string.swipe_right);
de.danoeh.antennapod.databinding.SwipeactionsPickerBinding picker;
picker = de.danoeh.antennapod.databinding.SwipeactionsPickerBinding.inflate(android.view.LayoutInflater.from(context));
builder.setView(picker.getRoot());
builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
androidx.appcompat.app.AlertDialog dialog;
dialog = builder.show();
for (int i = 0; i < keys.size(); i++) {
final int actionIndex;
actionIndex = i;
de.danoeh.antennapod.fragment.swipeactions.SwipeAction action;
action = keys.get(actionIndex);
de.danoeh.antennapod.databinding.SwipeactionsPickerItemBinding item;
item = de.danoeh.antennapod.databinding.SwipeactionsPickerItemBinding.inflate(android.view.LayoutInflater.from(context));
item.swipeActionLabel.setText(action.getTitle(context));
android.graphics.drawable.Drawable icon;
icon = androidx.core.graphics.drawable.DrawableCompat.wrap(androidx.appcompat.content.res.AppCompatResources.getDrawable(context, action.getActionIcon()));
icon.mutate();
icon.setTintMode(android.graphics.PorterDuff.Mode.SRC_ATOP);
if (((direction == de.danoeh.antennapod.dialog.SwipeActionsDialog.LEFT) && (leftAction == action)) || ((direction == de.danoeh.antennapod.dialog.SwipeActionsDialog.RIGHT) && (rightAction == action))) {
    icon.setTint(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, action.getActionColor()));
    item.swipeActionLabel.setTextColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, action.getActionColor()));
} else {
    icon.setTint(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, de.danoeh.antennapod.R.attr.action_icon_color));
}
item.swipeIcon.setImageDrawable(icon);
switch(MUID_STATIC) {
    // SwipeActionsDialog_3_BuggyGUIListenerOperatorMutator
    case 3062: {
        item.getRoot().setOnClickListener(null);
        break;
    }
    default: {
    item.getRoot().setOnClickListener((android.view.View v) -> {
        if (direction == de.danoeh.antennapod.dialog.SwipeActionsDialog.LEFT) {
            leftAction = keys.get(actionIndex);
        } else {
            rightAction = keys.get(actionIndex);
        }
        setupSwipeDirectionView(view, direction);
        dialog.dismiss();
    });
    break;
}
}
androidx.gridlayout.widget.GridLayout.LayoutParams param;
param = new androidx.gridlayout.widget.GridLayout.LayoutParams(androidx.gridlayout.widget.GridLayout.spec(androidx.gridlayout.widget.GridLayout.UNDEFINED, androidx.gridlayout.widget.GridLayout.BASELINE), androidx.gridlayout.widget.GridLayout.spec(androidx.gridlayout.widget.GridLayout.UNDEFINED, androidx.gridlayout.widget.GridLayout.FILL, 1.0F));
param.width = 0;
picker.pickerGridLayout.addView(item.getRoot(), param);
}
picker.pickerGridLayout.setColumnCount(2);
switch(MUID_STATIC) {
// SwipeActionsDialog_4_BinaryMutator
case 4062: {
picker.pickerGridLayout.setRowCount((keys.size() + 1) * 2);
break;
}
default: {
switch(MUID_STATIC) {
// SwipeActionsDialog_5_BinaryMutator
case 5062: {
    picker.pickerGridLayout.setRowCount((keys.size() - 1) / 2);
    break;
}
default: {
picker.pickerGridLayout.setRowCount((keys.size() + 1) / 2);
break;
}
}
break;
}
}
}


private void populateMockEpisode(de.danoeh.antennapod.databinding.FeeditemlistItemBinding view) {
view.container.setAlpha(0.3F);
view.secondaryActionButton.secondaryActionButton.setVisibility(android.view.View.GONE);
view.dragHandle.setVisibility(android.view.View.GONE);
view.statusInbox.setVisibility(android.view.View.GONE);
view.txtvTitle.setText("███████");
view.txtvPosition.setText("█████");
}


private void savePrefs(java.lang.String tag, java.lang.String right, java.lang.String left) {
android.content.SharedPreferences prefs;
prefs = context.getSharedPreferences(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.PREF_NAME, android.content.Context.MODE_PRIVATE);
prefs.edit().putString(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.KEY_PREFIX_SWIPEACTIONS + tag, (right + ",") + left).apply();
}


private void saveActionsEnabledPrefs(java.lang.Boolean enabled) {
android.content.SharedPreferences prefs;
prefs = context.getSharedPreferences(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.PREF_NAME, android.content.Context.MODE_PRIVATE);
prefs.edit().putBoolean(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.KEY_PREFIX_NO_ACTION + tag, enabled).apply();
}


public interface Callback {
void onCall();

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
