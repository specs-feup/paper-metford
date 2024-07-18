package com.automattic.simplenote;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.util.TypedValue;
import androidx.annotation.DimenRes;
import androidx.fragment.app.FragmentTransaction;
import androidx.annotation.IdRes;
import androidx.core.view.ViewCompat;
import androidx.annotation.NonNull;
import android.app.Dialog;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import com.automattic.simplenote.utils.DrawableUtils;
import android.view.Window;
import android.view.Menu;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import androidx.core.content.res.ResourcesCompat;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.content.res.Resources;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * A {@link DialogFragment} implementing the full-screen dialog pattern defined in the
 * <a href="https://material.io/guidelines/components/dialogs.html#dialogs-full-screen-dialogs">
 * Material Design guidelines</a>.
 */
public class FullScreenDialogFragment extends androidx.fragment.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = com.automattic.simplenote.FullScreenDialogFragment.class.getSimpleName();

    private static final java.lang.String ARG_ACTION = "ARG_ACTION";

    private static final java.lang.String ARG_CONTAINER = "ARG_CONTAINER";

    private static final java.lang.String ARG_ELEVATION = "ARG_ELEVATION";

    private static final java.lang.String ARG_HIDE_ACTIVITY_BAR = "ARG_HIDE_ACTIVITY_BAR";

    private static final java.lang.String ARG_SUBTITLE = "ARG_SUBTITLE";

    private static final java.lang.String ARG_TITLE = "ARG_TITLE";

    private static final int ID_ACTION = 1;

    private androidx.fragment.app.Fragment mFragment;

    private com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogController mController;

    private com.automattic.simplenote.FullScreenDialogFragment.OnConfirmListener mOnConfirmListener;

    private com.automattic.simplenote.FullScreenDialogFragment.OnDismissListener mOnDismissListener;

    private java.lang.String mAction;

    private java.lang.String mSubtitle;

    private java.lang.String mTitle;

    private androidx.appcompat.widget.Toolbar mToolbar;

    private boolean mHideActivityBar;

    private float mElevation;

    @androidx.annotation.IdRes
    private int mContainer;

    public interface FullScreenDialogContent {
        boolean onConfirmClicked(com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogController controller);


        boolean onDismissClicked(com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogController controller);


        void onViewCreated(com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogController controller);

    }

    public interface FullScreenDialogController {
        void confirm(@androidx.annotation.Nullable
        android.os.Bundle result);


        void dismiss();

    }

    public interface OnConfirmListener {
        void onConfirm(@androidx.annotation.Nullable
        android.os.Bundle result);

    }

    public interface OnDismissListener {
        void onDismiss();

    }

    protected static com.automattic.simplenote.FullScreenDialogFragment newInstance(com.automattic.simplenote.FullScreenDialogFragment.Builder builder) {
        com.automattic.simplenote.FullScreenDialogFragment dialog;
        dialog = new com.automattic.simplenote.FullScreenDialogFragment();
        dialog.setArguments(com.automattic.simplenote.FullScreenDialogFragment.setArguments(builder));
        // noinspection deprecation
        dialog.setContent(androidx.fragment.app.Fragment.instantiate(builder.mContext, builder.mClass.getName(), builder.mArguments));
        dialog.setOnConfirmListener(builder.mOnConfirmListener);
        dialog.setOnDismissListener(builder.mOnDismissListener);
        dialog.setHideActivityBar(builder.mHideActivityBar);
        return dialog;
    }


    private static android.os.Bundle setArguments(com.automattic.simplenote.FullScreenDialogFragment.Builder builder) {
        android.os.Bundle bundle;
        bundle = new android.os.Bundle();
        bundle.putString(com.automattic.simplenote.FullScreenDialogFragment.ARG_ACTION, builder.mAction);
        bundle.putInt(com.automattic.simplenote.FullScreenDialogFragment.ARG_CONTAINER, builder.mContainer);
        bundle.putFloat(com.automattic.simplenote.FullScreenDialogFragment.ARG_ELEVATION, builder.mElevation);
        bundle.putString(com.automattic.simplenote.FullScreenDialogFragment.ARG_TITLE, builder.mTitle);
        bundle.putString(com.automattic.simplenote.FullScreenDialogFragment.ARG_SUBTITLE, builder.mSubtitle);
        bundle.putBoolean(com.automattic.simplenote.FullScreenDialogFragment.ARG_HIDE_ACTIVITY_BAR, builder.mHideActivityBar);
        return bundle;
    }


    @java.lang.Override
    public void onActivityCreated(android.os.Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction().setCustomAnimations(com.automattic.simplenote.R.anim.full_screen_dialog_fragment_none, 0, 0, com.automattic.simplenote.R.anim.full_screen_dialog_fragment_none).add(com.automattic.simplenote.R.id.full_screen_dialog_fragment_content, mFragment).commitNow();
        }
    }


    @java.lang.Override
    public void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // FullScreenDialogFragment_0_LengthyGUICreationOperatorMutator
            case 92: {
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
    if (savedInstanceState != null) {
        mFragment = getChildFragmentManager().findFragmentById(com.automattic.simplenote.R.id.full_screen_dialog_fragment_content);
    }
    mController = new com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogController() {
        @java.lang.Override
        public void confirm(@androidx.annotation.Nullable
        android.os.Bundle result) {
            com.automattic.simplenote.FullScreenDialogFragment.this.confirm(result);
        }


        @java.lang.Override
        public void dismiss() {
            com.automattic.simplenote.FullScreenDialogFragment.this.dismiss();
        }

    };
}


@androidx.annotation.NonNull
@java.lang.Override
public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
    initBuilderArguments();
    android.app.Dialog dialog;
    dialog = new android.app.Dialog(requireContext(), getTheme()) {
        @java.lang.Override
        public void onBackPressed() {
            onDismissClicked();
        }

    };
    dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
    return dialog;
}


@androidx.annotation.Nullable
@java.lang.Override
public android.view.View onCreateView(@androidx.annotation.NonNull
android.view.LayoutInflater inflater, @androidx.annotation.Nullable
android.view.ViewGroup container, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    initBuilderArguments();
    if (mHideActivityBar) {
        hideActivityBar();
    }
    android.view.ViewGroup view;
    view = ((android.view.ViewGroup) (inflater.inflate(com.automattic.simplenote.R.layout.fragment_full_screen_dialog, container, false)));
    initToolbar(view);
    setThemeBackground(view);
    view.setFocusableInTouchMode(true);
    view.requestFocus();
    return view;
}


@java.lang.Override
public void onViewCreated(@androidx.annotation.NonNull
android.view.View view, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    ((com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogContent) (getContent())).onViewCreated(mController);
}


@java.lang.Override
public void dismiss() {
    // If isStateSaved() is true, it means that the application is not in the foreground, thus, we cannot
    // dismiss the dialog because it would cause an IllegalStateException
    if (isStateSaved()) {
        return;
    }
    if (mOnDismissListener != null) {
        mOnDismissListener.onDismiss();
    }
    if (mHideActivityBar) {
        showActivityBar();
    }
    super.dismiss();
}


@java.lang.Override
public void show(androidx.fragment.app.FragmentManager manager, java.lang.String tag) {
    show(manager.beginTransaction(), tag);
}


@java.lang.Override
public int show(androidx.fragment.app.FragmentTransaction transaction, java.lang.String tag) {
    initBuilderArguments();
    transaction.setCustomAnimations(com.automattic.simplenote.R.anim.full_screen_dialog_fragment_slide_up, 0, 0, com.automattic.simplenote.R.anim.full_screen_dialog_fragment_slide_down);
    @androidx.annotation.IdRes
    int container;
    container = (mContainer != 0) ? mContainer : android.R.id.content;
    return transaction.add(container, this, tag).addToBackStack(null).commit();
}


protected void confirm(android.os.Bundle result) {
    if (mOnConfirmListener != null) {
        mOnConfirmListener.onConfirm(result);
    }
    dismiss();
}


/**
 * Get {@link Fragment} to be able to interact directly with it.
 *
 * @return {@link Fragment} dialog content
 */
public androidx.fragment.app.Fragment getContent() {
    return mFragment;
}


/**
 * Hide {@link androidx.appcompat.app.AppCompatActivity} bar when showing fullscreen dialog.
 */
public void hideActivityBar() {
    androidx.fragment.app.FragmentActivity activity;
    activity = getActivity();
    if (activity instanceof androidx.appcompat.app.AppCompatActivity) {
        androidx.appcompat.app.ActionBar actionBar;
        actionBar = ((androidx.appcompat.app.AppCompatActivity) (activity)).getSupportActionBar();
        if ((actionBar != null) && actionBar.isShowing()) {
            actionBar.hide();
        }
    }
}


/**
 * Initialize arguments passed in {@link Builder}.
 */
private void initBuilderArguments() {
    if (getArguments() != null) {
        android.os.Bundle bundle;
        bundle = getArguments();
        mAction = bundle.getString(com.automattic.simplenote.FullScreenDialogFragment.ARG_ACTION);
        mContainer = bundle.getInt(com.automattic.simplenote.FullScreenDialogFragment.ARG_CONTAINER);
        mElevation = bundle.getFloat(com.automattic.simplenote.FullScreenDialogFragment.ARG_ELEVATION);
        mTitle = bundle.getString(com.automattic.simplenote.FullScreenDialogFragment.ARG_TITLE);
        mSubtitle = bundle.getString(com.automattic.simplenote.FullScreenDialogFragment.ARG_SUBTITLE);
        mHideActivityBar = bundle.getBoolean(com.automattic.simplenote.FullScreenDialogFragment.ARG_HIDE_ACTIVITY_BAR);
    }
}


/**
 * Initialize toolbar title and action.
 *
 * @param view
 * 		{@link View}
 */
private void initToolbar(android.view.View view) {
    switch(MUID_STATIC) {
        // FullScreenDialogFragment_1_InvalidViewFocusOperatorMutator
        case 192: {
            /**
            * Inserted by Kadabra
            */
            mToolbar = view.findViewById(com.automattic.simplenote.R.id.full_screen_dialog_fragment_toolbar);
            mToolbar.requestFocus();
            break;
        }
        // FullScreenDialogFragment_2_ViewComponentNotVisibleOperatorMutator
        case 292: {
            /**
            * Inserted by Kadabra
            */
            mToolbar = view.findViewById(com.automattic.simplenote.R.id.full_screen_dialog_fragment_toolbar);
            mToolbar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        mToolbar = view.findViewById(com.automattic.simplenote.R.id.full_screen_dialog_fragment_toolbar);
        break;
    }
}
mToolbar.setElevation(mElevation);
mToolbar.setTitle(mTitle);
mToolbar.setSubtitle(mSubtitle);
mToolbar.setNavigationIcon(com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithAttribute(view.getContext(), com.automattic.simplenote.R.drawable.ic_cross_24dp, com.automattic.simplenote.R.attr.toolbarIconColor));
mToolbar.setNavigationContentDescription(com.automattic.simplenote.R.string.description_close);
switch(MUID_STATIC) {
    // FullScreenDialogFragment_3_BuggyGUIListenerOperatorMutator
    case 392: {
        mToolbar.setNavigationOnClickListener(null);
        break;
    }
    default: {
    mToolbar.setNavigationOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View view) {
            switch(MUID_STATIC) {
                // FullScreenDialogFragment_4_LengthyGUIListenerOperatorMutator
                case 492: {
                    /**
                    * Inserted by Kadabra
                    */
                    onDismissClicked();
                    try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                    break;
                }
                default: {
                onDismissClicked();
                break;
            }
        }
    }

});
break;
}
}
if (!mAction.isEmpty()) {
android.view.Menu menu;
menu = mToolbar.getMenu();
android.view.MenuItem action;
action = menu.add(0, com.automattic.simplenote.FullScreenDialogFragment.ID_ACTION, 0, mAction);
action.setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_ALWAYS);
action.setOnMenuItemClickListener(new android.view.MenuItem.OnMenuItemClickListener() {
@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem item) {
    if (item.getItemId() == com.automattic.simplenote.FullScreenDialogFragment.ID_ACTION) {
        onConfirmClicked();
        return true;
    } else {
        return false;
    }
}

});
}
}


public void onBackPressed() {
if (isAdded()) {
onDismissClicked();
}
}


protected void onConfirmClicked() {
boolean isConsumed;
isConsumed = ((com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogContent) (mFragment)).onConfirmClicked(mController);
if (!isConsumed) {
mController.confirm(null);
}
}


protected void onDismissClicked() {
boolean isConsumed;
isConsumed = ((com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogContent) (mFragment)).onDismissClicked(mController);
if (!isConsumed) {
mController.dismiss();
}
}


/**
 * Set {@link Fragment} as dialog content.
 *
 * @param fragment
 * 		{@link Fragment} to set as dialog content
 */
private void setContent(androidx.fragment.app.Fragment fragment) {
mFragment = fragment;
}


/**
 * Set flag to hide activity bar when showing fullscreen dialog.
 *
 * @param hide
 * 		boolean to hide activity bar
 */
public void setHideActivityBar(boolean hide) {
mHideActivityBar = hide;
}


/**
 * Set callback to call when dialog is closed due to confirm click.
 *
 * @param listener
 * 		{@link OnConfirmListener} interface to call on confirm click
 */
public void setOnConfirmListener(@androidx.annotation.Nullable
com.automattic.simplenote.FullScreenDialogFragment.OnConfirmListener listener) {
mOnConfirmListener = listener;
}


/**
 * Set callback to call when dialog is closed due to dismiss click.
 *
 * @param listener
 * 		{@link OnDismissListener} interface to call on dismiss click
 */
public void setOnDismissListener(@androidx.annotation.Nullable
com.automattic.simplenote.FullScreenDialogFragment.OnDismissListener listener) {
mOnDismissListener = listener;
}


/**
 * Set {@link FullScreenDialogFragment} subtitle text.
 *
 * @param text
 * 		{@link String} to set as subtitle text
 */
public void setSubtitle(@androidx.annotation.NonNull
java.lang.String text) {
mSubtitle = text;
mToolbar.setSubtitle(mSubtitle);
}


/**
 * Set {@link FullScreenDialogFragment} subtitle text.
 *
 * @param textId
 * 		resource ID to set as subtitle text
 */
public void setSubtitle(@androidx.annotation.StringRes
int textId) {
mSubtitle = requireContext().getString(textId);
mToolbar.setSubtitle(mSubtitle);
}


/**
 * Set theme background for {@link FullScreenDialogFragment} view.
 *
 * @param view
 * 		{@link View} to set background
 */
private void setThemeBackground(android.view.View view) {
android.util.TypedValue value;
value = new android.util.TypedValue();
requireActivity().getTheme().resolveAttribute(android.R.attr.windowBackground, value, true);
if ((value.type >= android.util.TypedValue.TYPE_FIRST_COLOR_INT) && (value.type <= android.util.TypedValue.TYPE_LAST_COLOR_INT)) {
view.setBackgroundColor(value.data);
} else {
try {
androidx.core.view.ViewCompat.setBackground(view, androidx.core.content.res.ResourcesCompat.getDrawable(requireActivity().getResources(), value.resourceId, requireActivity().getTheme()));
} catch (android.content.res.Resources.NotFoundException ignore) {
}
}
}


/**
 * Show {@link androidx.appcompat.app.AppCompatActivity} bar when hiding fullscreen dialog.
 */
public void showActivityBar() {
androidx.fragment.app.FragmentActivity activity;
activity = getActivity();
if (activity instanceof androidx.appcompat.app.AppCompatActivity) {
androidx.appcompat.app.ActionBar actionBar;
actionBar = ((androidx.appcompat.app.AppCompatActivity) (activity)).getSupportActionBar();
if ((actionBar != null) && (!actionBar.isShowing())) {
actionBar.show();
}
}
}


public static class Builder {
android.os.Bundle mArguments;

java.lang.Class<? extends androidx.fragment.app.Fragment> mClass;

android.content.Context mContext;

com.automattic.simplenote.FullScreenDialogFragment.OnConfirmListener mOnConfirmListener;

com.automattic.simplenote.FullScreenDialogFragment.OnDismissListener mOnDismissListener;

java.lang.String mAction = "";

java.lang.String mSubtitle = "";

java.lang.String mTitle = "";

boolean mHideActivityBar = false;

float mElevation = 0;

@androidx.annotation.IdRes
int mContainer = 0;

/**
 * Builder to construct {@link FullScreenDialogFragment}.
 *
 * @param context
 * 		{@link Context}
 */
public Builder(@androidx.annotation.NonNull
android.content.Context context) {
mContext = context;
mElevation = mContext.getResources().getDimension(com.automattic.simplenote.R.dimen.elevation_toolbar);
}


/**
 * Creates {@link FullScreenDialogFragment} with provided parameters.
 *
 * @return {@link FullScreenDialogFragment} instance created
 */
public com.automattic.simplenote.FullScreenDialogFragment build() {
return com.automattic.simplenote.FullScreenDialogFragment.newInstance(this);
}


/**
 * Set {@link FullScreenDialogFragment} action text.
 *
 * @param text
 * 		{@link String} to set as action text
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setAction(@androidx.annotation.NonNull
java.lang.String text) {
mAction = text;
return this;
}


/**
 * Set {@link FullScreenDialogFragment} action text.
 *
 * @param textId
 * 		resource ID to set as action text
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setAction(@androidx.annotation.StringRes
int textId) {
return setAction(mContext.getString(textId));
}


/**
 * Set {@link Fragment} to be added as dialog, which must implement {@link FullScreenDialogContent}.
 *
 * @param contentClass
 * 		Fragment class to be instantiated
 * @param contentArguments
 * 		arguments to be added to Fragment
 * @return {@link Builder} object to allow for chaining of calls to set methods
 * @throws IllegalArgumentException
 * 		if content class does not implement
 * 		{@link FullScreenDialogContent} interface
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setContent(java.lang.Class<? extends androidx.fragment.app.Fragment> contentClass, @androidx.annotation.Nullable
android.os.Bundle contentArguments) {
if (!com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogContent.class.isAssignableFrom(contentClass)) {
throw new java.lang.IllegalArgumentException("The fragment class must implement FullScreenDialogContent interface");
}
mClass = contentClass;
mArguments = contentArguments;
return this;
}


/**
 * Set flag to hide activity bar when showing fullscreen dialog.
 *
 * @param hide
 * 		boolean to hide activity bar
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setHideActivityBar(boolean hide) {
mHideActivityBar = hide;
return this;
}


/**
 * Set {@link FullScreenDialogFragment} subtitle text.
 *
 * @param text
 * 		{@link String} to set as subtitle text
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setSubtitle(@androidx.annotation.NonNull
java.lang.String text) {
mSubtitle = text;
return this;
}


/**
 * Set {@link FullScreenDialogFragment} subtitle text.
 *
 * @param textId
 * 		resource ID to set as subtitle text
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setSubtitle(@androidx.annotation.StringRes
int textId) {
mSubtitle = mContext.getString(textId);
return this;
}


/**
 * Set {@link FullScreenDialogFragment} title text.
 *
 * @param text
 * 		{@link String} to set as title text
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setTitle(@androidx.annotation.NonNull
java.lang.String text) {
mTitle = text;
return this;
}


/**
 * Set {@link FullScreenDialogFragment} title text.
 *
 * @param textId
 * 		resource ID to set as title text
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setTitle(@androidx.annotation.StringRes
int textId) {
mTitle = mContext.getString(textId);
return this;
}


/**
 * Set {@link FullScreenDialogFragment} toolbar elevation.
 *
 * @param elevation
 * 		{@link Float} to set as toolbar elevation
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setToolbarElevation(float elevation) {
mElevation = elevation;
return this;
}


/**
 * Set {@link FullScreenDialogFragment} toolbar elevation.
 *
 * @param dimension
 * 		resource ID dimension to set as toolbar elevation
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setToolbarElevation(@androidx.annotation.DimenRes
int dimension) {
if (dimension == 0) {
return setToolbarElevation(java.lang.Float.valueOf(dimension));
} else {
mElevation = mContext.getResources().getDimension(dimension);
return this;
}
}


/**
 * Set callback to call when dialog is closed due to confirm click.
 *
 * @param listener
 * 		{@link OnConfirmListener} interface to call on confirm click
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setOnConfirmListener(@androidx.annotation.Nullable
com.automattic.simplenote.FullScreenDialogFragment.OnConfirmListener listener) {
mOnConfirmListener = listener;
return this;
}


/**
 * Set callback to call when dialog is closed due to dismiss click.
 *
 * @param listener
 * 		{@link OnDismissListener} interface to call on dismiss click
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setOnDismissListener(@androidx.annotation.Nullable
com.automattic.simplenote.FullScreenDialogFragment.OnDismissListener listener) {
mOnDismissListener = listener;
return this;
}


/**
 * Set container view for full-screen dialog.
 *
 * @param container
 * 		resource ID to use as container of full-screen dialog
 * @return {@link Builder} object to allow for chaining of calls to set methods
 */
public com.automattic.simplenote.FullScreenDialogFragment.Builder setViewContainer(@androidx.annotation.IdRes
int container) {
mContainer = container;
return this;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
