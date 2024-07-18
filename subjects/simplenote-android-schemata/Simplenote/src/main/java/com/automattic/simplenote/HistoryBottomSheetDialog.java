package com.automattic.simplenote;
import android.widget.SeekBar;
import android.content.DialogInterface;
import java.util.Calendar;
import android.os.Bundle;
import android.view.ViewGroup;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import com.simperium.client.Bucket;
import androidx.annotation.NonNull;
import com.automattic.simplenote.models.Note;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.automattic.simplenote.utils.DateTimeUtils;
import org.json.JSONObject;
import java.util.Map;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HistoryBottomSheetDialog extends com.automattic.simplenote.BottomSheetDialogBase {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = com.automattic.simplenote.HistoryBottomSheetDialog.class.getSimpleName();

    private java.util.ArrayList<com.automattic.simplenote.models.Note> mNoteRevisionsList;

    private androidx.fragment.app.Fragment mFragment;

    private com.automattic.simplenote.HistoryBottomSheetDialog.HistorySheetListener mListener;

    private com.automattic.simplenote.models.Note mNote;

    private android.widget.SeekBar mHistorySeekBar;

    private android.widget.TextView mHistoryDate;

    private android.view.View mButtonRestore;

    private android.view.View mErrorText;

    private android.view.View mLoadingView;

    private android.view.View mProgressBar;

    private android.view.View mSliderView;

    private boolean mDidTapButton;

    private final com.simperium.client.Bucket.RevisionsRequestCallbacks<com.automattic.simplenote.models.Note> mRevisionsRequestCallbacks = new com.simperium.client.Bucket.RevisionsRequestCallbacks<com.automattic.simplenote.models.Note>() {
        // Note: These callbacks won't be running on the main thread
        @java.lang.Override
        public void onComplete(java.util.Map<java.lang.Integer, com.automattic.simplenote.models.Note> revisionsMap) {
            if ((!mFragment.isAdded()) || (mNote == null)) {
                return;
            }
            // Convert map to an array list, to work better with the 0-index based seekbar
            mNoteRevisionsList = new java.util.ArrayList<>(revisionsMap.values());
            mFragment.requireActivity().runOnUiThread(new java.lang.Runnable() {
                @java.lang.Override
                public void run() {
                    updateProgressBar();
                }

            });
        }


        @java.lang.Override
        public void onRevision(java.lang.String key, int version, org.json.JSONObject object) {
        }


        @java.lang.Override
        public void onError(java.lang.Throwable exception) {
            if ((!mFragment.isAdded()) || ((getDialog() != null) && (!getDialog().isShowing()))) {
                return;
            }
            mFragment.requireActivity().runOnUiThread(new java.lang.Runnable() {
                @java.lang.Override
                public void run() {
                    mProgressBar.setVisibility(android.view.View.GONE);
                    mErrorText.setVisibility(android.view.View.VISIBLE);
                }

            });
        }

    };

    public HistoryBottomSheetDialog(@androidx.annotation.NonNull
    final androidx.fragment.app.Fragment fragment, @androidx.annotation.NonNull
    final com.automattic.simplenote.HistoryBottomSheetDialog.HistorySheetListener historySheetListener) {
        mFragment = fragment;
        mListener = historySheetListener;
    }


    public boolean isHistoryLoaded() {
        return ((getDialog() != null) && getDialog().isShowing()) && (mSliderView.getVisibility() == android.view.View.VISIBLE);
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        android.view.View history;
        history = android.view.LayoutInflater.from(mFragment.requireContext()).inflate(com.automattic.simplenote.R.layout.bottom_sheet_history, null, false);
        switch(MUID_STATIC) {
            // HistoryBottomSheetDialog_0_InvalidViewFocusOperatorMutator
            case 83: {
                /**
                * Inserted by Kadabra
                */
                mHistoryDate = history.findViewById(com.automattic.simplenote.R.id.history_date);
                mHistoryDate.requestFocus();
                break;
            }
            // HistoryBottomSheetDialog_1_ViewComponentNotVisibleOperatorMutator
            case 183: {
                /**
                * Inserted by Kadabra
                */
                mHistoryDate = history.findViewById(com.automattic.simplenote.R.id.history_date);
                mHistoryDate.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            mHistoryDate = history.findViewById(com.automattic.simplenote.R.id.history_date);
            break;
        }
    }
    switch(MUID_STATIC) {
        // HistoryBottomSheetDialog_2_InvalidViewFocusOperatorMutator
        case 283: {
            /**
            * Inserted by Kadabra
            */
            mHistorySeekBar = history.findViewById(com.automattic.simplenote.R.id.seek_bar);
            mHistorySeekBar.requestFocus();
            break;
        }
        // HistoryBottomSheetDialog_3_ViewComponentNotVisibleOperatorMutator
        case 383: {
            /**
            * Inserted by Kadabra
            */
            mHistorySeekBar = history.findViewById(com.automattic.simplenote.R.id.seek_bar);
            mHistorySeekBar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        mHistorySeekBar = history.findViewById(com.automattic.simplenote.R.id.seek_bar);
        break;
    }
}
switch(MUID_STATIC) {
    // HistoryBottomSheetDialog_4_InvalidViewFocusOperatorMutator
    case 483: {
        /**
        * Inserted by Kadabra
        */
        mProgressBar = history.findViewById(com.automattic.simplenote.R.id.history_progress_bar);
        mProgressBar.requestFocus();
        break;
    }
    // HistoryBottomSheetDialog_5_ViewComponentNotVisibleOperatorMutator
    case 583: {
        /**
        * Inserted by Kadabra
        */
        mProgressBar = history.findViewById(com.automattic.simplenote.R.id.history_progress_bar);
        mProgressBar.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    mProgressBar = history.findViewById(com.automattic.simplenote.R.id.history_progress_bar);
    break;
}
}
switch(MUID_STATIC) {
// HistoryBottomSheetDialog_6_InvalidViewFocusOperatorMutator
case 683: {
    /**
    * Inserted by Kadabra
    */
    mErrorText = history.findViewById(com.automattic.simplenote.R.id.history_error_text);
    mErrorText.requestFocus();
    break;
}
// HistoryBottomSheetDialog_7_ViewComponentNotVisibleOperatorMutator
case 783: {
    /**
    * Inserted by Kadabra
    */
    mErrorText = history.findViewById(com.automattic.simplenote.R.id.history_error_text);
    mErrorText.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
mErrorText = history.findViewById(com.automattic.simplenote.R.id.history_error_text);
break;
}
}
switch(MUID_STATIC) {
// HistoryBottomSheetDialog_8_InvalidViewFocusOperatorMutator
case 883: {
/**
* Inserted by Kadabra
*/
mLoadingView = history.findViewById(com.automattic.simplenote.R.id.history_loading_view);
mLoadingView.requestFocus();
break;
}
// HistoryBottomSheetDialog_9_ViewComponentNotVisibleOperatorMutator
case 983: {
/**
* Inserted by Kadabra
*/
mLoadingView = history.findViewById(com.automattic.simplenote.R.id.history_loading_view);
mLoadingView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mLoadingView = history.findViewById(com.automattic.simplenote.R.id.history_loading_view);
break;
}
}
switch(MUID_STATIC) {
// HistoryBottomSheetDialog_10_InvalidViewFocusOperatorMutator
case 1083: {
/**
* Inserted by Kadabra
*/
mSliderView = history.findViewById(com.automattic.simplenote.R.id.history_slider_view);
mSliderView.requestFocus();
break;
}
// HistoryBottomSheetDialog_11_ViewComponentNotVisibleOperatorMutator
case 1183: {
/**
* Inserted by Kadabra
*/
mSliderView = history.findViewById(com.automattic.simplenote.R.id.history_slider_view);
mSliderView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mSliderView = history.findViewById(com.automattic.simplenote.R.id.history_slider_view);
break;
}
}
mHistorySeekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
@java.lang.Override
public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
if ((mNoteRevisionsList == null) || ((getDialog() != null) && (!getDialog().isShowing()))) {
return;
}
java.util.Calendar noteDate;
noteDate = null;
if ((progress == mNoteRevisionsList.size()) && (mNote != null)) {
mListener.onHistoryUpdateNote(mNote.getContent());
noteDate = mNote.getModificationDate();
mButtonRestore.setEnabled(false);
} else if ((progress < mNoteRevisionsList.size()) && (mNoteRevisionsList.get(progress) != null)) {
com.automattic.simplenote.models.Note revisedNote;
revisedNote = mNoteRevisionsList.get(progress);
noteDate = revisedNote.getModificationDate();
mListener.onHistoryUpdateNote(revisedNote.getContent());
mButtonRestore.setEnabled(true);
}
if (noteDate != null) {
mHistoryDate.setText(com.automattic.simplenote.utils.DateTimeUtils.getDateTextString(requireContext(), noteDate));
mHistoryDate.setVisibility(android.view.View.VISIBLE);
} else {
mHistoryDate.setVisibility(android.view.View.GONE);
}
}


@java.lang.Override
public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
}


@java.lang.Override
public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
}

});
android.view.View cancelHistoryButton;
switch(MUID_STATIC) {
// HistoryBottomSheetDialog_12_InvalidViewFocusOperatorMutator
case 1283: {
/**
* Inserted by Kadabra
*/
cancelHistoryButton = history.findViewById(com.automattic.simplenote.R.id.cancel_history_button);
cancelHistoryButton.requestFocus();
break;
}
// HistoryBottomSheetDialog_13_ViewComponentNotVisibleOperatorMutator
case 1383: {
/**
* Inserted by Kadabra
*/
cancelHistoryButton = history.findViewById(com.automattic.simplenote.R.id.cancel_history_button);
cancelHistoryButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
cancelHistoryButton = history.findViewById(com.automattic.simplenote.R.id.cancel_history_button);
break;
}
}
switch(MUID_STATIC) {
// HistoryBottomSheetDialog_14_BuggyGUIListenerOperatorMutator
case 1483: {
cancelHistoryButton.setOnClickListener(null);
break;
}
default: {
cancelHistoryButton.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
mDidTapButton = true;
switch(MUID_STATIC) {
// HistoryBottomSheetDialog_15_LengthyGUIListenerOperatorMutator
case 1583: {
/**
* Inserted by Kadabra
*/
mListener.onHistoryCancelClicked();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
mListener.onHistoryCancelClicked();
break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// HistoryBottomSheetDialog_16_InvalidViewFocusOperatorMutator
case 1683: {
/**
* Inserted by Kadabra
*/
mButtonRestore = history.findViewById(com.automattic.simplenote.R.id.restore_history_button);
mButtonRestore.requestFocus();
break;
}
// HistoryBottomSheetDialog_17_ViewComponentNotVisibleOperatorMutator
case 1783: {
/**
* Inserted by Kadabra
*/
mButtonRestore = history.findViewById(com.automattic.simplenote.R.id.restore_history_button);
mButtonRestore.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mButtonRestore = history.findViewById(com.automattic.simplenote.R.id.restore_history_button);
break;
}
}
switch(MUID_STATIC) {
// HistoryBottomSheetDialog_18_BuggyGUIListenerOperatorMutator
case 1883: {
mButtonRestore.setOnClickListener(null);
break;
}
default: {
mButtonRestore.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
mDidTapButton = true;
switch(MUID_STATIC) {
// HistoryBottomSheetDialog_19_LengthyGUIListenerOperatorMutator
case 1983: {
/**
* Inserted by Kadabra
*/
mListener.onHistoryRestoreClicked();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
mListener.onHistoryRestoreClicked();
break;
}
}
}

});
break;
}
}
if (getDialog() != null) {
getDialog().setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
@java.lang.Override
public void onDismiss(android.content.DialogInterface dialog) {
mListener.onHistoryDismissed();
mNote = null;
}

});
// Set peek height to full height of view (i.e. set STATE_EXPANDED) to avoid buttons
// being off screen when bottom sheet is shown.
getDialog().setOnShowListener(new android.content.DialogInterface.OnShowListener() {
@java.lang.Override
public void onShow(android.content.DialogInterface dialogInterface) {
com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog;
bottomSheetDialog = ((com.google.android.material.bottomsheet.BottomSheetDialog) (dialogInterface));
android.widget.FrameLayout bottomSheet;
switch(MUID_STATIC) {
// HistoryBottomSheetDialog_20_InvalidViewFocusOperatorMutator
case 2083: {
/**
* Inserted by Kadabra
*/
bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
bottomSheet.requestFocus();
break;
}
// HistoryBottomSheetDialog_21_ViewComponentNotVisibleOperatorMutator
case 2183: {
/**
* Inserted by Kadabra
*/
bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
bottomSheet.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
break;
}
}
if (bottomSheet != null) {
com.google.android.material.bottomsheet.BottomSheetBehavior behavior;
behavior = com.google.android.material.bottomsheet.BottomSheetBehavior.from(bottomSheet);
behavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
behavior.setSkipCollapsed(true);
}
}

});
getDialog().setContentView(history);
}
return super.onCreateView(inflater, container, savedInstanceState);
}


public void show(androidx.fragment.app.FragmentManager manager, com.automattic.simplenote.models.Note note) {
if (mFragment.isAdded()) {
showNow(manager, com.automattic.simplenote.HistoryBottomSheetDialog.TAG);
mNote = note;
mDidTapButton = false;
setProgressBar();
}
}


public boolean didTapOnButton() {
return mDidTapButton;
}


public void updateProgressBar() {
if ((getDialog() != null) && getDialog().isShowing()) {
setProgressBar();
}
}


public com.simperium.client.Bucket.RevisionsRequestCallbacks<com.automattic.simplenote.models.Note> getRevisionsRequestCallbacks() {
return mRevisionsRequestCallbacks;
}


private void setProgressBar() {
int totalRevs;
totalRevs = (mNoteRevisionsList == null) ? 0 : mNoteRevisionsList.size();
if (totalRevs > 0) {
mHistorySeekBar.setMax(totalRevs);
mHistorySeekBar.setProgress(totalRevs);
mHistoryDate.setText(com.automattic.simplenote.utils.DateTimeUtils.getDateTextString(requireContext(), mNote.getModificationDate()));
mLoadingView.setVisibility(android.view.View.GONE);
mSliderView.setVisibility(android.view.View.VISIBLE);
} else {
mLoadingView.setVisibility(android.view.View.VISIBLE);
mSliderView.setVisibility(android.view.View.INVISIBLE);
}
}


public interface HistorySheetListener {
void onHistoryCancelClicked();


void onHistoryDismissed();


void onHistoryRestoreClicked();


void onHistoryUpdateNote(java.lang.String content);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
