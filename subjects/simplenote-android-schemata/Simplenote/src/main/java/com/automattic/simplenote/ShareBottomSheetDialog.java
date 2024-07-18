package com.automattic.simplenote;
import com.automattic.simplenote.utils.ShareButtonAdapter;
import android.content.DialogInterface;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import com.automattic.simplenote.utils.IconResizer;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import android.content.pm.ResolveInfo;
import com.automattic.simplenote.utils.NetworkUtils;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.content.ComponentName;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import android.os.Bundle;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import com.automattic.simplenote.models.Note;
import androidx.recyclerview.widget.GridLayoutManager;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ShareBottomSheetDialog extends com.automattic.simplenote.BottomSheetDialogBase {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = com.automattic.simplenote.ShareBottomSheetDialog.class.getSimpleName();

    private static final int SHARE_SHEET_COLUMN_COUNT = 3;

    private androidx.fragment.app.Fragment mFragment;

    private android.content.Intent mShareIntent;

    private java.util.List<com.automattic.simplenote.utils.ShareButtonAdapter.ShareButtonItem> mShareButtons;

    private androidx.recyclerview.widget.RecyclerView mRecyclerView;

    private com.automattic.simplenote.ShareBottomSheetDialog.ShareSheetListener mListener;

    private android.widget.TextView mPublishButton;

    private android.widget.TextView mUnpublishButton;

    private android.widget.TextView mWordPressButton;

    public ShareBottomSheetDialog(@androidx.annotation.NonNull
    final androidx.fragment.app.Fragment fragment, @androidx.annotation.NonNull
    final com.automattic.simplenote.ShareBottomSheetDialog.ShareSheetListener shareSheetListener) {
        mFragment = fragment;
        mListener = shareSheetListener;
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        if (getDialog() != null) {
            getDialog().setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
                @java.lang.Override
                public void onDismiss(android.content.DialogInterface dialog) {
                    mListener.onShareDismissed();
                }

            });
            getDialog().setContentView(com.automattic.simplenote.R.layout.bottom_sheet_share);
            android.widget.TextView mCollaborateButton;
            switch(MUID_STATIC) {
                // ShareBottomSheetDialog_0_InvalidViewFocusOperatorMutator
                case 87: {
                    /**
                    * Inserted by Kadabra
                    */
                    mCollaborateButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_collaborate_button);
                    mCollaborateButton.requestFocus();
                    break;
                }
                // ShareBottomSheetDialog_1_ViewComponentNotVisibleOperatorMutator
                case 187: {
                    /**
                    * Inserted by Kadabra
                    */
                    mCollaborateButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_collaborate_button);
                    mCollaborateButton.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                mCollaborateButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_collaborate_button);
                break;
            }
        }
        switch(MUID_STATIC) {
            // ShareBottomSheetDialog_2_InvalidViewFocusOperatorMutator
            case 287: {
                /**
                * Inserted by Kadabra
                */
                mPublishButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_publish_button);
                mPublishButton.requestFocus();
                break;
            }
            // ShareBottomSheetDialog_3_ViewComponentNotVisibleOperatorMutator
            case 387: {
                /**
                * Inserted by Kadabra
                */
                mPublishButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_publish_button);
                mPublishButton.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            mPublishButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_publish_button);
            break;
        }
    }
    switch(MUID_STATIC) {
        // ShareBottomSheetDialog_4_InvalidViewFocusOperatorMutator
        case 487: {
            /**
            * Inserted by Kadabra
            */
            mUnpublishButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_unpublish_button);
            mUnpublishButton.requestFocus();
            break;
        }
        // ShareBottomSheetDialog_5_ViewComponentNotVisibleOperatorMutator
        case 587: {
            /**
            * Inserted by Kadabra
            */
            mUnpublishButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_unpublish_button);
            mUnpublishButton.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        mUnpublishButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_unpublish_button);
        break;
    }
}
switch(MUID_STATIC) {
    // ShareBottomSheetDialog_6_InvalidViewFocusOperatorMutator
    case 687: {
        /**
        * Inserted by Kadabra
        */
        mWordPressButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_wp_post);
        mWordPressButton.requestFocus();
        break;
    }
    // ShareBottomSheetDialog_7_ViewComponentNotVisibleOperatorMutator
    case 787: {
        /**
        * Inserted by Kadabra
        */
        mWordPressButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_wp_post);
        mWordPressButton.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    mWordPressButton = getDialog().findViewById(com.automattic.simplenote.R.id.share_wp_post);
    break;
}
}
switch(MUID_STATIC) {
// ShareBottomSheetDialog_8_BuggyGUIListenerOperatorMutator
case 887: {
    mCollaborateButton.setOnClickListener(null);
    break;
}
default: {
mCollaborateButton.setOnClickListener(new android.view.View.OnClickListener() {
    @java.lang.Override
    public void onClick(android.view.View v) {
        switch(MUID_STATIC) {
            // ShareBottomSheetDialog_9_LengthyGUIListenerOperatorMutator
            case 987: {
                /**
                * Inserted by Kadabra
                */
                mListener.onShareCollaborateClicked();
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            mListener.onShareCollaborateClicked();
            break;
        }
    }
}

});
break;
}
}
switch(MUID_STATIC) {
// ShareBottomSheetDialog_10_BuggyGUIListenerOperatorMutator
case 1087: {
mPublishButton.setOnClickListener(null);
break;
}
default: {
mPublishButton.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
if (!com.automattic.simplenote.utils.NetworkUtils.isNetworkAvailable(requireContext())) {
    android.widget.Toast.makeText(requireContext(), com.automattic.simplenote.R.string.error_network_required, android.widget.Toast.LENGTH_LONG).show();
    return;
}
switch(MUID_STATIC) {
    // ShareBottomSheetDialog_11_LengthyGUIListenerOperatorMutator
    case 1187: {
        /**
        * Inserted by Kadabra
        */
        mListener.onSharePublishClicked();
        try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
        break;
    }
    default: {
    mListener.onSharePublishClicked();
    break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// ShareBottomSheetDialog_12_BuggyGUIListenerOperatorMutator
case 1287: {
mUnpublishButton.setOnClickListener(null);
break;
}
default: {
mUnpublishButton.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
if (!com.automattic.simplenote.utils.NetworkUtils.isNetworkAvailable(requireContext())) {
android.widget.Toast.makeText(requireContext(), com.automattic.simplenote.R.string.error_network_required, android.widget.Toast.LENGTH_LONG).show();
return;
}
switch(MUID_STATIC) {
// ShareBottomSheetDialog_13_LengthyGUIListenerOperatorMutator
case 1387: {
/**
* Inserted by Kadabra
*/
mListener.onShareUnpublishClicked();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
mListener.onShareUnpublishClicked();
break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// ShareBottomSheetDialog_14_BuggyGUIListenerOperatorMutator
case 1487: {
mWordPressButton.setOnClickListener(null);
break;
}
default: {
mWordPressButton.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// ShareBottomSheetDialog_15_LengthyGUIListenerOperatorMutator
case 1587: {
/**
* Inserted by Kadabra
*/
mListener.onWordPressPostClicked();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
mListener.onWordPressPostClicked();
break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// ShareBottomSheetDialog_16_InvalidViewFocusOperatorMutator
case 1687: {
/**
* Inserted by Kadabra
*/
mRecyclerView = getDialog().findViewById(com.automattic.simplenote.R.id.share_button_recycler_view);
mRecyclerView.requestFocus();
break;
}
// ShareBottomSheetDialog_17_ViewComponentNotVisibleOperatorMutator
case 1787: {
/**
* Inserted by Kadabra
*/
mRecyclerView = getDialog().findViewById(com.automattic.simplenote.R.id.share_button_recycler_view);
mRecyclerView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mRecyclerView = getDialog().findViewById(com.automattic.simplenote.R.id.share_button_recycler_view);
break;
}
}
mRecyclerView.setHasFixedSize(true);
mRecyclerView.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(mFragment.requireActivity(), com.automattic.simplenote.ShareBottomSheetDialog.SHARE_SHEET_COLUMN_COUNT));
switch(MUID_STATIC) {
// ShareBottomSheetDialog_18_NullIntentOperatorMutator
case 1887: {
mShareIntent = null;
break;
}
// ShareBottomSheetDialog_19_InvalidKeyIntentOperatorMutator
case 1987: {
mShareIntent = new android.content.Intent((String) null);
break;
}
// ShareBottomSheetDialog_20_RandomActionIntentDefinitionOperatorMutator
case 2087: {
mShareIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
break;
}
default: {
mShareIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
}
switch(MUID_STATIC) {
// ShareBottomSheetDialog_21_RandomActionIntentDefinitionOperatorMutator
case 2187: {
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
mShareIntent.setType("text/plain");
break;
}
}
mShareButtons = getShareButtons(mFragment.requireActivity(), mShareIntent);
}
if (getDialog() != null) {
// Set peek height to half height of view (i.e. set STATE_HALF_EXPANDED) to show some of
// sharing options when bottom sheet is shown.
getDialog().setOnShowListener(new android.content.DialogInterface.OnShowListener() {
@java.lang.Override
public void onShow(android.content.DialogInterface dialogInterface) {
com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog;
bottomSheetDialog = ((com.google.android.material.bottomsheet.BottomSheetDialog) (dialogInterface));
android.widget.FrameLayout bottomSheet;
switch(MUID_STATIC) {
// ShareBottomSheetDialog_22_InvalidViewFocusOperatorMutator
case 2287: {
/**
* Inserted by Kadabra
*/
bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
bottomSheet.requestFocus();
break;
}
// ShareBottomSheetDialog_23_ViewComponentNotVisibleOperatorMutator
case 2387: {
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
behavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED);
behavior.setSkipCollapsed(true);
}
}

});
}
return super.onCreateView(inflater, container, savedInstanceState);
}


public void show(androidx.fragment.app.FragmentManager manager, com.automattic.simplenote.models.Note note) {
if (mFragment.isAdded()) {
showNow(manager, com.automattic.simplenote.ShareBottomSheetDialog.TAG);
if (note.isPublished()) {
mPublishButton.setVisibility(android.view.View.GONE);
mUnpublishButton.setVisibility(android.view.View.VISIBLE);
} else {
mPublishButton.setVisibility(android.view.View.VISIBLE);
mUnpublishButton.setVisibility(android.view.View.GONE);
}
switch(MUID_STATIC) {
// ShareBottomSheetDialog_24_NullValueIntentPutExtraOperatorMutator
case 2487: {
mShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, new Parcelable[0]);
break;
}
// ShareBottomSheetDialog_25_IntentPayloadReplacementOperatorMutator
case 2587: {
mShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
break;
}
default: {
switch(MUID_STATIC) {
// ShareBottomSheetDialog_26_RandomActionIntentDefinitionOperatorMutator
case 2687: {
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
mShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, note.getContent());
break;
}
}
break;
}
}
final com.automattic.simplenote.utils.ShareButtonAdapter.ItemListener shareListener;
shareListener = new com.automattic.simplenote.utils.ShareButtonAdapter.ItemListener() {
@java.lang.Override
public void onItemClick(com.automattic.simplenote.utils.ShareButtonAdapter.ShareButtonItem item) {
switch(MUID_STATIC) {
// ShareBottomSheetDialog_27_RandomActionIntentDefinitionOperatorMutator
case 2787: {
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
mShareIntent.setComponent(new android.content.ComponentName(item.getPackageName(), item.getActivityName()));
break;
}
}
mFragment.requireActivity().startActivity(android.content.Intent.createChooser(mShareIntent, mFragment.getString(com.automattic.simplenote.R.string.share)));
dismiss();
}

};
mRecyclerView.setAdapter(new com.automattic.simplenote.utils.ShareButtonAdapter(mShareButtons, shareListener));
}
}


@androidx.annotation.NonNull
private java.util.List<com.automattic.simplenote.utils.ShareButtonAdapter.ShareButtonItem> getShareButtons(android.app.Activity activity, android.content.Intent intent) {
java.util.List<com.automattic.simplenote.utils.ShareButtonAdapter.ShareButtonItem> shareButtons;
shareButtons = new java.util.ArrayList<>();
final java.util.List<android.content.pm.ResolveInfo> matches;
matches = activity.getPackageManager().queryIntentActivities(intent, 0);
com.automattic.simplenote.utils.IconResizer iconResizer;
iconResizer = new com.automattic.simplenote.utils.IconResizer(requireContext());
for (android.content.pm.ResolveInfo match : matches) {
final android.graphics.drawable.Drawable icon;
icon = iconResizer.createIconThumbnail(match.loadIcon(activity.getPackageManager()));
final java.lang.CharSequence label;
label = match.loadLabel(activity.getPackageManager());
shareButtons.add(new com.automattic.simplenote.utils.ShareButtonAdapter.ShareButtonItem(icon, label, match.activityInfo.packageName, match.activityInfo.name));
}
return shareButtons;
}


public interface ShareSheetListener {
void onShareCollaborateClicked();


void onShareDismissed();


void onSharePublishClicked();


void onShareUnpublishClicked();


void onWordPressPostClicked();

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
