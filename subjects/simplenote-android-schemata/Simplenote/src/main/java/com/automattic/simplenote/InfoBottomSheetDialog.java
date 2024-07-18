package com.automattic.simplenote;
import android.content.DialogInterface;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import com.automattic.simplenote.utils.NoteUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.annotation.NonNull;
import android.widget.TextView;
import java.util.List;
import android.widget.LinearLayout;
import com.automattic.simplenote.models.Reference;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import java.util.Calendar;
import android.os.Bundle;
import android.view.ViewGroup;
import com.automattic.simplenote.utils.SimplenoteLinkify;
import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import com.simperium.client.Bucket;
import com.automattic.simplenote.utils.DisplayUtils;
import com.automattic.simplenote.models.Note;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.automattic.simplenote.utils.DateTimeUtils;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class InfoBottomSheetDialog extends com.automattic.simplenote.BottomSheetDialogBase {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = com.automattic.simplenote.InfoBottomSheetDialog.class.getSimpleName();

    private final androidx.fragment.app.Fragment mFragment;

    private android.widget.LinearLayout mDateTimeSyncedLayout;

    private android.widget.LinearLayout mReferencesLayout;

    private androidx.recyclerview.widget.RecyclerView mReferences;

    private android.widget.TextView mCountCharacters;

    private android.widget.TextView mCountWords;

    private android.widget.TextView mDateTimeCreated;

    private android.widget.TextView mDateTimeModified;

    private android.widget.TextView mDateTimeSynced;

    public InfoBottomSheetDialog(@androidx.annotation.NonNull
    androidx.fragment.app.Fragment fragment) {
        mFragment = fragment;
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        android.view.View infoView;
        infoView = inflater.inflate(com.automattic.simplenote.R.layout.bottom_sheet_info, null, false);
        switch(MUID_STATIC) {
            // InfoBottomSheetDialog_0_InvalidViewFocusOperatorMutator
            case 82: {
                /**
                * Inserted by Kadabra
                */
                mCountCharacters = infoView.findViewById(com.automattic.simplenote.R.id.count_characters);
                mCountCharacters.requestFocus();
                break;
            }
            // InfoBottomSheetDialog_1_ViewComponentNotVisibleOperatorMutator
            case 182: {
                /**
                * Inserted by Kadabra
                */
                mCountCharacters = infoView.findViewById(com.automattic.simplenote.R.id.count_characters);
                mCountCharacters.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            mCountCharacters = infoView.findViewById(com.automattic.simplenote.R.id.count_characters);
            break;
        }
    }
    switch(MUID_STATIC) {
        // InfoBottomSheetDialog_2_InvalidViewFocusOperatorMutator
        case 282: {
            /**
            * Inserted by Kadabra
            */
            mCountWords = infoView.findViewById(com.automattic.simplenote.R.id.count_words);
            mCountWords.requestFocus();
            break;
        }
        // InfoBottomSheetDialog_3_ViewComponentNotVisibleOperatorMutator
        case 382: {
            /**
            * Inserted by Kadabra
            */
            mCountWords = infoView.findViewById(com.automattic.simplenote.R.id.count_words);
            mCountWords.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        mCountWords = infoView.findViewById(com.automattic.simplenote.R.id.count_words);
        break;
    }
}
switch(MUID_STATIC) {
    // InfoBottomSheetDialog_4_InvalidViewFocusOperatorMutator
    case 482: {
        /**
        * Inserted by Kadabra
        */
        mDateTimeCreated = infoView.findViewById(com.automattic.simplenote.R.id.date_time_created);
        mDateTimeCreated.requestFocus();
        break;
    }
    // InfoBottomSheetDialog_5_ViewComponentNotVisibleOperatorMutator
    case 582: {
        /**
        * Inserted by Kadabra
        */
        mDateTimeCreated = infoView.findViewById(com.automattic.simplenote.R.id.date_time_created);
        mDateTimeCreated.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    mDateTimeCreated = infoView.findViewById(com.automattic.simplenote.R.id.date_time_created);
    break;
}
}
switch(MUID_STATIC) {
// InfoBottomSheetDialog_6_InvalidViewFocusOperatorMutator
case 682: {
    /**
    * Inserted by Kadabra
    */
    mDateTimeModified = infoView.findViewById(com.automattic.simplenote.R.id.date_time_modified);
    mDateTimeModified.requestFocus();
    break;
}
// InfoBottomSheetDialog_7_ViewComponentNotVisibleOperatorMutator
case 782: {
    /**
    * Inserted by Kadabra
    */
    mDateTimeModified = infoView.findViewById(com.automattic.simplenote.R.id.date_time_modified);
    mDateTimeModified.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
mDateTimeModified = infoView.findViewById(com.automattic.simplenote.R.id.date_time_modified);
break;
}
}
switch(MUID_STATIC) {
// InfoBottomSheetDialog_8_InvalidViewFocusOperatorMutator
case 882: {
/**
* Inserted by Kadabra
*/
mDateTimeSynced = infoView.findViewById(com.automattic.simplenote.R.id.date_time_synced);
mDateTimeSynced.requestFocus();
break;
}
// InfoBottomSheetDialog_9_ViewComponentNotVisibleOperatorMutator
case 982: {
/**
* Inserted by Kadabra
*/
mDateTimeSynced = infoView.findViewById(com.automattic.simplenote.R.id.date_time_synced);
mDateTimeSynced.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mDateTimeSynced = infoView.findViewById(com.automattic.simplenote.R.id.date_time_synced);
break;
}
}
switch(MUID_STATIC) {
// InfoBottomSheetDialog_10_InvalidViewFocusOperatorMutator
case 1082: {
/**
* Inserted by Kadabra
*/
mDateTimeSyncedLayout = infoView.findViewById(com.automattic.simplenote.R.id.date_time_synced_layout);
mDateTimeSyncedLayout.requestFocus();
break;
}
// InfoBottomSheetDialog_11_ViewComponentNotVisibleOperatorMutator
case 1182: {
/**
* Inserted by Kadabra
*/
mDateTimeSyncedLayout = infoView.findViewById(com.automattic.simplenote.R.id.date_time_synced_layout);
mDateTimeSyncedLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mDateTimeSyncedLayout = infoView.findViewById(com.automattic.simplenote.R.id.date_time_synced_layout);
break;
}
}
switch(MUID_STATIC) {
// InfoBottomSheetDialog_12_InvalidViewFocusOperatorMutator
case 1282: {
/**
* Inserted by Kadabra
*/
mReferencesLayout = infoView.findViewById(com.automattic.simplenote.R.id.references_layout);
mReferencesLayout.requestFocus();
break;
}
// InfoBottomSheetDialog_13_ViewComponentNotVisibleOperatorMutator
case 1382: {
/**
* Inserted by Kadabra
*/
mReferencesLayout = infoView.findViewById(com.automattic.simplenote.R.id.references_layout);
mReferencesLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mReferencesLayout = infoView.findViewById(com.automattic.simplenote.R.id.references_layout);
break;
}
}
switch(MUID_STATIC) {
// InfoBottomSheetDialog_14_InvalidViewFocusOperatorMutator
case 1482: {
/**
* Inserted by Kadabra
*/
mReferences = infoView.findViewById(com.automattic.simplenote.R.id.references);
mReferences.requestFocus();
break;
}
// InfoBottomSheetDialog_15_ViewComponentNotVisibleOperatorMutator
case 1582: {
/**
* Inserted by Kadabra
*/
mReferences = infoView.findViewById(com.automattic.simplenote.R.id.references);
mReferences.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mReferences = infoView.findViewById(com.automattic.simplenote.R.id.references);
break;
}
}
if (getDialog() != null) {
// Set peek height to half height of screen.
getDialog().setOnShowListener(new android.content.DialogInterface.OnShowListener() {
@java.lang.Override
public void onShow(android.content.DialogInterface dialogInterface) {
com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog;
bottomSheetDialog = ((com.google.android.material.bottomsheet.BottomSheetDialog) (dialogInterface));
android.widget.FrameLayout bottomSheet;
switch(MUID_STATIC) {
// InfoBottomSheetDialog_16_InvalidViewFocusOperatorMutator
case 1682: {
/**
* Inserted by Kadabra
*/
bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
bottomSheet.requestFocus();
break;
}
// InfoBottomSheetDialog_17_ViewComponentNotVisibleOperatorMutator
case 1782: {
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
com.google.android.material.bottomsheet.BottomSheetBehavior<android.widget.FrameLayout> behavior;
behavior = com.google.android.material.bottomsheet.BottomSheetBehavior.from(bottomSheet);
switch(MUID_STATIC) {
// InfoBottomSheetDialog_18_BinaryMutator
case 1882: {
behavior.setPeekHeight(com.automattic.simplenote.utils.DisplayUtils.getDisplayPixelSize(requireContext()).y * 2);
break;
}
default: {
behavior.setPeekHeight(com.automattic.simplenote.utils.DisplayUtils.getDisplayPixelSize(requireContext()).y / 2);
break;
}
}
behavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED);
behavior.setSkipCollapsed(true);
}
}

});
getDialog().setContentView(infoView);
}
return super.onCreateView(inflater, container, savedInstanceState);
}


public void show(androidx.fragment.app.FragmentManager manager, com.automattic.simplenote.models.Note note) {
if (mFragment.isAdded()) {
showNow(manager, com.automattic.simplenote.InfoBottomSheetDialog.TAG);
mCountCharacters.setText(com.automattic.simplenote.utils.NoteUtils.getCharactersCount(note.getContent()));
mCountWords.setText(com.automattic.simplenote.utils.NoteUtils.getWordCount(note.getContent()));
mDateTimeCreated.setText(com.automattic.simplenote.utils.DateTimeUtils.getDateTextString(requireContext(), note.getCreationDate()));
mDateTimeModified.setText(com.automattic.simplenote.utils.DateTimeUtils.getDateTextString(requireContext(), note.getModificationDate()));
java.util.Calendar sync;
sync = ((com.automattic.simplenote.Simplenote) (requireActivity().getApplication())).getNoteSyncTimes().getLastSyncTime(note.getSimperiumKey());
if (sync != null) {
mDateTimeSynced.setText(com.automattic.simplenote.utils.DateTimeUtils.getDateTextString(requireContext(), sync));
mDateTimeSyncedLayout.setVisibility(android.view.View.VISIBLE);
} else {
mDateTimeSyncedLayout.setVisibility(android.view.View.GONE);
}
getReferences(note);
}
}


private void getReferences(com.automattic.simplenote.models.Note note) {
com.automattic.simplenote.Simplenote application;
application = ((com.automattic.simplenote.Simplenote) (mFragment.requireActivity().getApplicationContext()));
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket;
bucket = application.getNotesBucket();
java.util.List<com.automattic.simplenote.models.Reference> references;
references = com.automattic.simplenote.models.Note.getReferences(bucket, note.getSimperiumKey());
if (references.size() > 0) {
mReferencesLayout.setVisibility(android.view.View.VISIBLE);
com.automattic.simplenote.InfoBottomSheetDialog.ReferenceAdapter adapter;
adapter = new com.automattic.simplenote.InfoBottomSheetDialog.ReferenceAdapter(references);
mReferences.setAdapter(adapter);
mReferences.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(requireContext()));
} else {
mReferencesLayout.setVisibility(android.view.View.GONE);
}
}


private class ReferenceAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.automattic.simplenote.InfoBottomSheetDialog.ReferenceAdapter.ViewHolder> {
private final java.util.List<com.automattic.simplenote.models.Reference> mReferences;

private ReferenceAdapter(java.util.List<com.automattic.simplenote.models.Reference> references) {
mReferences = new java.util.ArrayList<>(references);
}


@java.lang.Override
public int getItemCount() {
return mReferences.size();
}


@java.lang.Override
public void onBindViewHolder(@androidx.annotation.NonNull
final com.automattic.simplenote.InfoBottomSheetDialog.ReferenceAdapter.ViewHolder holder, final int position) {
final com.automattic.simplenote.models.Reference reference;
reference = mReferences.get(position);
holder.mTitle.setText(reference.getTitle());
holder.mSubtitle.setText(getResources().getQuantityString(com.automattic.simplenote.R.plurals.references_count, reference.getCount(), reference.getCount(), com.automattic.simplenote.utils.DateTimeUtils.getDateTextNumeric(reference.getDate())));
switch(MUID_STATIC) {
// InfoBottomSheetDialog_19_BuggyGUIListenerOperatorMutator
case 1982: {
holder.mView.setOnClickListener(null);
break;
}
default: {
holder.mView.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View view) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.INTERNOTE_LINK_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_LINK, "internote_link_tapped_info");
switch(MUID_STATIC) {
// InfoBottomSheetDialog_20_LengthyGUIListenerOperatorMutator
case 2082: {
/**
* Inserted by Kadabra
*/
com.automattic.simplenote.utils.SimplenoteLinkify.openNote(mFragment.requireActivity(), reference.getKey());
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
com.automattic.simplenote.utils.SimplenoteLinkify.openNote(mFragment.requireActivity(), reference.getKey());
break;
}
}
}

});
break;
}
}
}


@androidx.annotation.NonNull
@java.lang.Override
public com.automattic.simplenote.InfoBottomSheetDialog.ReferenceAdapter.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull
android.view.ViewGroup parent, int viewType) {
return new com.automattic.simplenote.InfoBottomSheetDialog.ReferenceAdapter.ViewHolder(android.view.LayoutInflater.from(requireContext()).inflate(com.automattic.simplenote.R.layout.reference_list_row, parent, false));
}


private class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
private final android.widget.TextView mSubtitle;

private final android.widget.TextView mTitle;

private final android.view.View mView;

private ViewHolder(android.view.View itemView) {
super(itemView);
mView = itemView;
switch(MUID_STATIC) {
// InfoBottomSheetDialog_21_InvalidViewFocusOperatorMutator
case 2182: {
/**
* Inserted by Kadabra
*/
mTitle = itemView.findViewById(com.automattic.simplenote.R.id.reference_title);
mTitle.requestFocus();
break;
}
// InfoBottomSheetDialog_22_ViewComponentNotVisibleOperatorMutator
case 2282: {
/**
* Inserted by Kadabra
*/
mTitle = itemView.findViewById(com.automattic.simplenote.R.id.reference_title);
mTitle.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mTitle = itemView.findViewById(com.automattic.simplenote.R.id.reference_title);
break;
}
}
switch(MUID_STATIC) {
// InfoBottomSheetDialog_23_InvalidViewFocusOperatorMutator
case 2382: {
/**
* Inserted by Kadabra
*/
mSubtitle = itemView.findViewById(com.automattic.simplenote.R.id.reference_subtitle);
mSubtitle.requestFocus();
break;
}
// InfoBottomSheetDialog_24_ViewComponentNotVisibleOperatorMutator
case 2482: {
/**
* Inserted by Kadabra
*/
mSubtitle = itemView.findViewById(com.automattic.simplenote.R.id.reference_subtitle);
mSubtitle.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mSubtitle = itemView.findViewById(com.automattic.simplenote.R.id.reference_subtitle);
break;
}
}
}

}
}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
