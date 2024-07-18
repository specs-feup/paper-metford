package com.automattic.simplenote;
import android.text.style.RelativeSizeSpan;
import android.content.res.Configuration;
import com.simperium.client.Query;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_CONTENT_SHARED;
import android.content.res.ColorStateList;
import com.automattic.simplenote.utils.ThemeUtils;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import com.automattic.simplenote.utils.SimplenoteMovementMethod;
import android.database.Cursor;
import android.text.style.URLSpan;
import android.util.TypedValue;
import android.widget.CursorAdapter;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.widget.TextView;
import java.util.List;
import android.webkit.WebViewClient;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_PUBLISHED;
import com.automattic.simplenote.utils.DrawableUtils;
import static com.automattic.simplenote.Simplenote.SCROLL_POSITION_PREFERENCES;
import android.view.Menu;
import android.view.MenuInflater;
import static com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE;
import android.os.Handler;
import android.graphics.drawable.Drawable;
import android.graphics.Typeface;
import android.text.Spanned;
import com.automattic.simplenote.utils.SimplenoteLinkify;
import android.os.AsyncTask;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_EDITED;
import android.text.style.MetricAffectingSpan;
import com.automattic.simplenote.utils.ContextUtils;
import com.automattic.simplenote.utils.SpaceTokenizer;
import android.view.LayoutInflater;
import com.simperium.client.Bucket;
import static com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX;
import android.view.ViewStub;
import androidx.appcompat.view.ActionMode;
import com.automattic.simplenote.utils.DisplayUtils;
import com.automattic.simplenote.models.Note;
import com.automattic.simplenote.utils.MatchOffsetHighlighter;
import com.automattic.simplenote.utils.AutoBullet;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.widget.NestedScrollView;
import com.automattic.simplenote.utils.BrowserUtils;
import java.util.Set;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_CHECKLIST_INSERTED;
import android.net.Uri;
import com.automattic.simplenote.utils.NoteUtils;
import dagger.hilt.android.AndroidEntryPoint;
import com.automattic.simplenote.utils.AppLog;
import com.google.android.material.chip.Chip;
import com.automattic.simplenote.widgets.SimplenoteEditText;
import java.lang.ref.WeakReference;
import com.automattic.simplenote.utils.NetworkUtils;
import com.automattic.simplenote.utils.TagsMultiAutoCompleteTextView;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_UNPUBLISHED;
import androidx.core.view.MenuCompat;
import android.text.Editable;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import com.automattic.simplenote.utils.TextHighlighter;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import com.automattic.simplenote.utils.PrefUtils;
import android.widget.LinearLayout;
import android.content.SharedPreferences;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import android.text.util.Linkify;
import com.automattic.simplenote.viewmodels.NoteEditorViewModel;
import java.util.Calendar;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.MenuItem;
import androidx.preference.PreferenceManager;
import com.simperium.client.BucketObjectMissingException;
import android.view.View;
import android.text.style.StyleSpan;
import com.automattic.simplenote.utils.AppLog.Type;
import com.automattic.simplenote.utils.TagsMultiAutoCompleteTextView.OnTagAddedListener;
import android.text.Layout;
import com.automattic.simplenote.utils.WidgetUtils;
import com.automattic.simplenote.models.Tag;
import com.google.android.material.chip.ChipGroup;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@dagger.hilt.android.AndroidEntryPoint
public class NoteEditorFragment extends androidx.fragment.app.Fragment implements com.simperium.client.Bucket.Listener<com.automattic.simplenote.models.Note> , android.text.TextWatcher , com.automattic.simplenote.utils.TagsMultiAutoCompleteTextView.OnTagAddedListener , android.view.View.OnFocusChangeListener , com.automattic.simplenote.widgets.SimplenoteEditText.OnSelectionChangedListener , com.automattic.simplenote.ShareBottomSheetDialog.ShareSheetListener , com.automattic.simplenote.HistoryBottomSheetDialog.HistorySheetListener , com.automattic.simplenote.widgets.SimplenoteEditText.OnCheckboxToggledListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ARG_IS_FROM_WIDGET = "is_from_widget";

    public static final java.lang.String ARG_ITEM_ID = "item_id";

    public static final java.lang.String ARG_NEW_NOTE = "new_note";

    public static final java.lang.String ARG_MATCH_OFFSETS = "match_offsets";

    public static final java.lang.String ARG_MARKDOWN_ENABLED = "markdown_enabled";

    public static final java.lang.String ARG_PREVIEW_ENABLED = "preview_enabled";

    private static final java.lang.String STATE_NOTE_ID = "state_note_id";

    private static final int AUTOSAVE_DELAY_MILLIS = 2000;

    private static final int MAX_REVISIONS = 30;

    private static final int PUBLISH_TIMEOUT = 20000;

    private static final int HISTORY_TIMEOUT = 10000;

    private com.automattic.simplenote.models.Note mNote;

    private final java.lang.Runnable mAutoSaveRunnable = this::saveAndSyncNote;

    private com.simperium.client.Bucket<com.automattic.simplenote.models.Note> mNotesBucket;

    private android.view.View mRootView;

    private android.view.View mTagPadding;

    private com.automattic.simplenote.widgets.SimplenoteEditText mContentEditText;

    private com.google.android.material.chip.ChipGroup mTagChips;

    private com.automattic.simplenote.utils.TagsMultiAutoCompleteTextView mTagInput;

    private android.os.Handler mAutoSaveHandler;

    private android.os.Handler mPublishTimeoutHandler;

    private android.os.Handler mHistoryTimeoutHandler;

    private android.widget.LinearLayout mPlaceholderView;

    private android.widget.CursorAdapter mLinkAutocompleteAdapter;

    private android.widget.CursorAdapter mTagAutocompleteAdapter;

    private boolean mIsLoadingNote;

    private boolean mIsMarkdownEnabled;

    private boolean mIsPreviewEnabled;

    private androidx.appcompat.view.ActionMode mActionMode;

    private android.view.MenuItem mChecklistMenuItem;

    private android.view.MenuItem mCopyMenuItem;

    private android.view.MenuItem mInformationMenuItem;

    private android.view.MenuItem mShareMenuItem;

    private android.view.MenuItem mViewLinkMenuItem;

    private java.lang.String mLinkUrl;

    private java.lang.String mLinkText;

    private com.automattic.simplenote.utils.MatchOffsetHighlighter mHighlighter;

    private android.graphics.drawable.Drawable mBrowserIcon;

    private android.graphics.drawable.Drawable mCallIcon;

    private android.graphics.drawable.Drawable mCopyIcon;

    private android.graphics.drawable.Drawable mEmailIcon;

    private android.graphics.drawable.Drawable mLinkIcon;

    private android.graphics.drawable.Drawable mMapIcon;

    private android.graphics.drawable.Drawable mShareIcon;

    private com.automattic.simplenote.utils.MatchOffsetHighlighter.SpanFactory mMatchHighlighter;

    private java.lang.String mMatchOffsets;

    private int mCurrentCursorPosition;

    private com.automattic.simplenote.HistoryBottomSheetDialog mHistoryBottomSheet;

    private android.widget.LinearLayout mError;

    private com.automattic.simplenote.NoteMarkdownFragment mNoteMarkdownFragment;

    private android.content.SharedPreferences mPreferences;

    private java.lang.String mCss;

    private android.webkit.WebView mMarkdown;

    private boolean mIsPaused;

    private boolean mIsFromWidget;

    private com.automattic.simplenote.viewmodels.NoteEditorViewModel viewModel;

    // Hides the history bottom sheet if no revisions are loaded
    private final java.lang.Runnable mHistoryTimeoutRunnable = new java.lang.Runnable() {
        @java.lang.Override
        public void run() {
            if (!isAdded()) {
                return;
            }
            requireActivity().runOnUiThread(() -> {
                if (((mHistoryBottomSheet.getDialog() != null) && mHistoryBottomSheet.getDialog().isShowing()) && (!mHistoryBottomSheet.isHistoryLoaded())) {
                    mHistoryBottomSheet.dismiss();
                    android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.error_history, android.widget.Toast.LENGTH_LONG).show();
                }
            });
        }

    };

    private com.automattic.simplenote.InfoBottomSheetDialog mInfoBottomSheet;

    private com.automattic.simplenote.ShareBottomSheetDialog mShareBottomSheet;

    // Contextual action bar for dealing with links
    private final androidx.appcompat.view.ActionMode.Callback mActionModeCallback = new androidx.appcompat.view.ActionMode.Callback() {
        // Called when the action mode is created; startActionMode() was called
        @java.lang.Override
        public boolean onCreateActionMode(androidx.appcompat.view.ActionMode mode, android.view.Menu menu) {
            // Inflate a menu resource providing context menu items
            android.view.MenuInflater inflater;
            inflater = mode.getMenuInflater();
            if (inflater != null) {
                inflater.inflate(com.automattic.simplenote.R.menu.view_link, menu);
                mCopyMenuItem = menu.findItem(com.automattic.simplenote.R.id.menu_copy);
                mShareMenuItem = menu.findItem(com.automattic.simplenote.R.id.menu_share);
                mViewLinkMenuItem = menu.findItem(com.automattic.simplenote.R.id.menu_view_link);
                mode.setTitle(getString(com.automattic.simplenote.R.string.link));
                mode.setTitleOptionalHint(false);
                com.automattic.simplenote.utils.DrawableUtils.tintMenuWithAttribute(getActivity(), menu, com.automattic.simplenote.R.attr.toolbarIconColor);
            }
            requireActivity().getWindow().setStatusBarColor(com.automattic.simplenote.utils.ThemeUtils.getColorFromAttribute(requireContext(), com.automattic.simplenote.R.attr.mainBackgroundColor));
            return true;
        }


        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @java.lang.Override
        public boolean onPrepareActionMode(androidx.appcompat.view.ActionMode mode, android.view.Menu menu) {
            return false// Return false if nothing is done
            ;// Return false if nothing is done

        }


        // Called when the user selects a contextual menu item
        @java.lang.Override
        public boolean onActionItemClicked(androidx.appcompat.view.ActionMode mode, android.view.MenuItem item) {
            switch (item.getItemId()) {
                case com.automattic.simplenote.R.id.menu_view_link :
                    if (mLinkText != null) {
                        if (mLinkText.startsWith(com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX)) {
                            com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.INTERNOTE_LINK_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_LINK, "internote_link_tapped_editor");
                            com.automattic.simplenote.utils.SimplenoteLinkify.openNote(requireActivity(), mLinkText.replace(com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX, ""));
                        } else if (((!mLinkUrl.startsWith("geo:")) && (!mLinkUrl.startsWith("mailto:"))) && (!mLinkUrl.startsWith("tel:"))) {
                            try {
                                com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), mLinkText);
                            } catch (java.lang.Exception e) {
                                com.automattic.simplenote.utils.BrowserUtils.showDialogErrorException(requireContext(), mLinkText);
                                e.printStackTrace();
                            }
                        } else {
                            android.net.Uri uri;
                            uri = android.net.Uri.parse(mLinkUrl);
                            android.content.Intent i;
                            switch(MUID_STATIC) {
                                // NoteEditorFragment_0_NullIntentOperatorMutator
                                case 91: {
                                    i = null;
                                    break;
                                }
                                // NoteEditorFragment_1_InvalidKeyIntentOperatorMutator
                                case 191: {
                                    i = new android.content.Intent((String) null);
                                    break;
                                }
                                // NoteEditorFragment_2_RandomActionIntentDefinitionOperatorMutator
                                case 291: {
                                    i = new android.content.Intent(android.content.Intent.ACTION_SEND);
                                    break;
                                }
                                default: {
                                i = new android.content.Intent(android.content.Intent.ACTION_VIEW);
                                break;
                            }
                        }
                        switch(MUID_STATIC) {
                            // NoteEditorFragment_3_RandomActionIntentDefinitionOperatorMutator
                            case 391: {
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
                            i.setData(uri);
                            break;
                        }
                    }
                    startActivity(i);
                }
                mode.finish()// Action picked, so close the CAB
                ;// Action picked, so close the CAB

            }
            return true;
        case com.automattic.simplenote.R.id.menu_copy :
            if ((mLinkText != null) && (getActivity() != null)) {
                if (com.automattic.simplenote.utils.BrowserUtils.copyToClipboard(requireContext(), mLinkText)) {
                    com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.link_copied, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
                } else {
                    com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.link_copied_failure, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
                }
                mode.finish();
            }
            return true;
        case com.automattic.simplenote.R.id.menu_share :
            if (mLinkText != null) {
                showShare(mLinkText);
                mode.finish();
            }
            return true;
        default :
            return false;
    }
}


// Called when the user exits the action mode
@java.lang.Override
public void onDestroyActionMode(androidx.appcompat.view.ActionMode mode) {
    if (mActionMode != null) {
        mActionMode.setSubtitle("");
        mActionMode = null;
    }
    new android.os.Handler().postDelayed(() -> requireActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent, requireActivity().getTheme())), requireContext().getResources().getInteger(android.R.integer.config_mediumAnimTime));
}

};

private com.google.android.material.snackbar.Snackbar mPublishingSnackbar;

private boolean mHideActionOnSuccess;

// Resets note publish status if Simperium never returned the new publish status
private final java.lang.Runnable mPublishTimeoutRunnable = new java.lang.Runnable() {
@java.lang.Override
public void run() {
    if (!isAdded())
        return;

    requireActivity().runOnUiThread(() -> {
        mNote.setPublished(!mNote.isPublished());
        mNote.save();
        updatePublishedState(false);
    });
}

};

/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
public NoteEditorFragment() {
}


@java.lang.Override
public void onCreate(android.os.Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
switch(MUID_STATIC) {
    // NoteEditorFragment_4_LengthyGUICreationOperatorMutator
    case 491: {
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
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.NETWORK, com.automattic.simplenote.utils.NetworkUtils.getNetworkInfo(requireContext()));
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Created (NoteEditorFragment)");
mPreferences = requireContext().getSharedPreferences(com.automattic.simplenote.Simplenote.SCROLL_POSITION_PREFERENCES, android.content.Context.MODE_PRIVATE);
mInfoBottomSheet = new com.automattic.simplenote.InfoBottomSheetDialog(this);
mShareBottomSheet = new com.automattic.simplenote.ShareBottomSheetDialog(this, this);
mHistoryBottomSheet = new com.automattic.simplenote.HistoryBottomSheetDialog(this, this);
com.automattic.simplenote.Simplenote currentApp;
currentApp = ((com.automattic.simplenote.Simplenote) (requireActivity().getApplication()));
mNotesBucket = currentApp.getNotesBucket();
viewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.automattic.simplenote.viewmodels.NoteEditorViewModel.class);
setObservers();
mCallIcon = com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithAttribute(getActivity(), com.automattic.simplenote.R.drawable.ic_call_white_24dp, com.automattic.simplenote.R.attr.actionModeTextColor);
mEmailIcon = com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithAttribute(getActivity(), com.automattic.simplenote.R.drawable.ic_email_24dp, com.automattic.simplenote.R.attr.actionModeTextColor);
mLinkIcon = com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithAttribute(getActivity(), com.automattic.simplenote.R.drawable.ic_note_24dp, com.automattic.simplenote.R.attr.actionModeTextColor);
mMapIcon = com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithAttribute(getActivity(), com.automattic.simplenote.R.drawable.ic_map_24dp, com.automattic.simplenote.R.attr.actionModeTextColor);
mBrowserIcon = com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithAttribute(getActivity(), com.automattic.simplenote.R.drawable.ic_browser_24dp, com.automattic.simplenote.R.attr.actionModeTextColor);
mCopyIcon = com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithAttribute(getActivity(), com.automattic.simplenote.R.drawable.ic_copy_24dp, com.automattic.simplenote.R.attr.actionModeTextColor);
mShareIcon = com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithAttribute(getActivity(), com.automattic.simplenote.R.drawable.ic_share_24dp, com.automattic.simplenote.R.attr.actionModeTextColor);
mAutoSaveHandler = new android.os.Handler();
mPublishTimeoutHandler = new android.os.Handler();
mHistoryTimeoutHandler = new android.os.Handler();
mMatchHighlighter = new com.automattic.simplenote.utils.TextHighlighter(requireActivity(), com.automattic.simplenote.R.attr.editorSearchHighlightForegroundColor, com.automattic.simplenote.R.attr.editorSearchHighlightBackgroundColor);
mTagAutocompleteAdapter = new android.widget.CursorAdapter(getActivity(), null, 0x0) {
@java.lang.Override
public android.view.View newView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup parent) {
    android.app.Activity activity;
    activity = ((android.app.Activity) (context));
    if (activity == null)
        return null;

    return activity.getLayoutInflater().inflate(com.automattic.simplenote.R.layout.autocomplete_list_item, null);
}


@java.lang.Override
public void bindView(android.view.View view, android.content.Context context, android.database.Cursor cursor) {
    android.widget.TextView textView;
    textView = ((android.widget.TextView) (view));
    textView.setText(convertToString(cursor));
}


@java.lang.Override
public java.lang.CharSequence convertToString(android.database.Cursor cursor) {
    return cursor.getString(cursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Tag.NAME_PROPERTY));
}


@java.lang.Override
public android.database.Cursor runQueryOnBackgroundThread(java.lang.CharSequence filter) {
    android.app.Activity activity;
    activity = getActivity();
    if (activity == null)
        return null;

    com.automattic.simplenote.Simplenote application;
    application = ((com.automattic.simplenote.Simplenote) (activity.getApplication()));
    com.simperium.client.Query<com.automattic.simplenote.models.Tag> query;
    query = application.getTagsBucket().query();
    // make the tag name available to the cursor
    query.include(com.automattic.simplenote.models.Tag.NAME_PROPERTY);
    // sort the tags by their names
    query.order(com.automattic.simplenote.models.Tag.NAME_PROPERTY);
    // if there's a filter string find only matching tag names
    if (filter != null)
        query.where(com.automattic.simplenote.models.Tag.NAME_PROPERTY, com.simperium.client.Query.ComparisonType.LIKE, java.lang.String.format("%s%%", filter));

    return query.execute();
}

};
mLinkAutocompleteAdapter = new android.widget.CursorAdapter(getContext(), null, 0x0) {
private android.app.Activity mActivity = requireActivity();

@java.lang.Override
public void bindView(android.view.View view, android.content.Context context, android.database.Cursor cursor) {
    ((android.widget.TextView) (view)).setText(convertToString(cursor));
}


@java.lang.Override
public java.lang.CharSequence convertToString(android.database.Cursor cursor) {
    return cursor.getString(cursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME));
}


@java.lang.Override
public android.view.View newView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup parent) {
    return mActivity.getLayoutInflater().inflate(com.automattic.simplenote.R.layout.autocomplete_list_item, null);
}


@java.lang.Override
public android.database.Cursor runQueryOnBackgroundThread(java.lang.CharSequence filter) {
    if (filter == null) {
        return null;
    }
    com.automattic.simplenote.Simplenote application;
    application = ((com.automattic.simplenote.Simplenote) (mActivity.getApplication()));
    com.simperium.client.Query<com.automattic.simplenote.models.Note> query;
    query = application.getNotesBucket().query();
    query.include(com.automattic.simplenote.models.Note.PINNED_INDEX_NAME);
    query.include(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME);
    query.where(com.automattic.simplenote.models.Note.DELETED_PROPERTY, com.simperium.client.Query.ComparisonType.NOT_EQUAL_TO, true);
    query.where(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME, com.simperium.client.Query.ComparisonType.LIKE, java.lang.String.format("%%%s%%", filter));
    com.automattic.simplenote.utils.PrefUtils.sortNoteQuery(query, requireContext(), true);
    android.database.Cursor cursor;
    cursor = query.execute();
    final int heightAutocomplete;
    switch(MUID_STATIC) {
        // NoteEditorFragment_5_BinaryMutator
        case 591: {
            heightAutocomplete = com.automattic.simplenote.utils.DisplayUtils.dpToPx(requireContext(), cursor.getCount() / 48);
            break;
        }
        default: {
        heightAutocomplete = com.automattic.simplenote.utils.DisplayUtils.dpToPx(requireContext(), cursor.getCount() * 48);
        break;
    }
}
final int heightDisplay;
heightDisplay = com.automattic.simplenote.utils.DisplayUtils.getDisplayPixelSize(requireContext()).y;
final int heightDropdown;
switch(MUID_STATIC) {
    // NoteEditorFragment_6_BinaryMutator
    case 691: {
        heightDropdown = java.lang.Math.min(heightDisplay * 4, heightAutocomplete);
        break;
    }
    default: {
    heightDropdown = java.lang.Math.min(heightDisplay / 4, heightAutocomplete);
    break;
}
}
mActivity.runOnUiThread(() -> mContentEditText.setDropDownHeight(heightDropdown));
return cursor;
}

};
com.automattic.simplenote.utils.WidgetUtils.updateNoteWidgets(requireActivity().getApplicationContext());
}


@java.lang.Override
public android.view.View onCreateView(@androidx.annotation.NonNull
android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
mRootView = inflater.inflate(com.automattic.simplenote.R.layout.fragment_note_editor, container, false);
switch(MUID_STATIC) {
// NoteEditorFragment_7_InvalidViewFocusOperatorMutator
case 791: {
/**
* Inserted by Kadabra
*/
mContentEditText = mRootView.findViewById(com.automattic.simplenote.R.id.note_content);
mContentEditText.requestFocus();
break;
}
// NoteEditorFragment_8_ViewComponentNotVisibleOperatorMutator
case 891: {
/**
* Inserted by Kadabra
*/
mContentEditText = mRootView.findViewById(com.automattic.simplenote.R.id.note_content);
mContentEditText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mContentEditText = mRootView.findViewById(com.automattic.simplenote.R.id.note_content);
break;
}
}
mContentEditText.addOnSelectionChangedListener(this);
mContentEditText.setOnCheckboxToggledListener(this);
mContentEditText.setMovementMethod(com.automattic.simplenote.utils.SimplenoteMovementMethod.getInstance());
mContentEditText.setOnFocusChangeListener(this);
mContentEditText.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, com.automattic.simplenote.utils.PrefUtils.getFontSize(requireContext()));
mContentEditText.setDropDownBackgroundResource(com.automattic.simplenote.R.drawable.bg_list_popup);
mContentEditText.setAdapter(mLinkAutocompleteAdapter);
switch(MUID_STATIC) {
// NoteEditorFragment_9_InvalidViewFocusOperatorMutator
case 991: {
/**
* Inserted by Kadabra
*/
mTagInput = mRootView.findViewById(com.automattic.simplenote.R.id.tag_input);
mTagInput.requestFocus();
break;
}
// NoteEditorFragment_10_ViewComponentNotVisibleOperatorMutator
case 1091: {
/**
* Inserted by Kadabra
*/
mTagInput = mRootView.findViewById(com.automattic.simplenote.R.id.tag_input);
mTagInput.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mTagInput = mRootView.findViewById(com.automattic.simplenote.R.id.tag_input);
break;
}
}
mTagInput.setBucketTag(((com.automattic.simplenote.Simplenote) (requireActivity().getApplication())).getTagsBucket());
mTagInput.setDropDownBackgroundResource(com.automattic.simplenote.R.drawable.bg_list_popup);
mTagInput.setTokenizer(new com.automattic.simplenote.utils.SpaceTokenizer());
mTagInput.setAdapter(mTagAutocompleteAdapter);
mTagInput.setOnFocusChangeListener(this);
switch(MUID_STATIC) {
// NoteEditorFragment_11_InvalidViewFocusOperatorMutator
case 1191: {
/**
* Inserted by Kadabra
*/
mTagChips = mRootView.findViewById(com.automattic.simplenote.R.id.tag_chips);
mTagChips.requestFocus();
break;
}
// NoteEditorFragment_12_ViewComponentNotVisibleOperatorMutator
case 1291: {
/**
* Inserted by Kadabra
*/
mTagChips = mRootView.findViewById(com.automattic.simplenote.R.id.tag_chips);
mTagChips.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mTagChips = mRootView.findViewById(com.automattic.simplenote.R.id.tag_chips);
break;
}
}
switch(MUID_STATIC) {
// NoteEditorFragment_13_InvalidViewFocusOperatorMutator
case 1391: {
/**
* Inserted by Kadabra
*/
mTagPadding = mRootView.findViewById(com.automattic.simplenote.R.id.tag_padding);
mTagPadding.requestFocus();
break;
}
// NoteEditorFragment_14_ViewComponentNotVisibleOperatorMutator
case 1491: {
/**
* Inserted by Kadabra
*/
mTagPadding = mRootView.findViewById(com.automattic.simplenote.R.id.tag_padding);
mTagPadding.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mTagPadding = mRootView.findViewById(com.automattic.simplenote.R.id.tag_padding);
break;
}
}
mHighlighter = new com.automattic.simplenote.utils.MatchOffsetHighlighter(mMatchHighlighter, mContentEditText);
switch(MUID_STATIC) {
// NoteEditorFragment_15_InvalidViewFocusOperatorMutator
case 1591: {
/**
* Inserted by Kadabra
*/
mPlaceholderView = mRootView.findViewById(com.automattic.simplenote.R.id.placeholder);
mPlaceholderView.requestFocus();
break;
}
// NoteEditorFragment_16_ViewComponentNotVisibleOperatorMutator
case 1691: {
/**
* Inserted by Kadabra
*/
mPlaceholderView = mRootView.findViewById(com.automattic.simplenote.R.id.placeholder);
mPlaceholderView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mPlaceholderView = mRootView.findViewById(com.automattic.simplenote.R.id.placeholder);
break;
}
}
if (com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(getActivity()) && (mNote == null)) {
mPlaceholderView.setVisibility(android.view.View.VISIBLE);
requireActivity().invalidateOptionsMenu();
if (com.automattic.simplenote.utils.BrowserUtils.isWebViewInstalled(requireContext())) {
((android.view.ViewStub) (mRootView.findViewById(com.automattic.simplenote.R.id.stub_webview))).inflate();
switch(MUID_STATIC) {
// NoteEditorFragment_17_InvalidViewFocusOperatorMutator
case 1791: {
/**
* Inserted by Kadabra
*/
mMarkdown = mRootView.findViewById(com.automattic.simplenote.R.id.markdown);
mMarkdown.requestFocus();
break;
}
// NoteEditorFragment_18_ViewComponentNotVisibleOperatorMutator
case 1891: {
/**
* Inserted by Kadabra
*/
mMarkdown = mRootView.findViewById(com.automattic.simplenote.R.id.markdown);
mMarkdown.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mMarkdown = mRootView.findViewById(com.automattic.simplenote.R.id.markdown);
break;
}
}
mMarkdown.setWebViewClient(new android.webkit.WebViewClient() {
@java.lang.Override
public void onPageFinished(final android.webkit.WebView view, java.lang.String url) {
super.onPageFinished(view, url);
if (mMarkdown.getVisibility() == android.view.View.VISIBLE) {
new android.os.Handler().postDelayed(() -> {
if ((mNote != null) && (mNote.getSimperiumKey() != null)) {
((androidx.core.widget.NestedScrollView) (mRootView)).scrollTo(0, mPreferences.getInt(mNote.getSimperiumKey(), 0));
}
}, requireContext().getResources().getInteger(android.R.integer.config_mediumAnimTime));
}
}


@java.lang.Override
public boolean shouldOverrideUrlLoading(android.webkit.WebView view, android.webkit.WebResourceRequest request) {
java.lang.String url;
url = request.getUrl().toString();
if (url.startsWith(com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX)) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.INTERNOTE_LINK_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_LINK, "internote_link_tapped_markdown");
com.automattic.simplenote.utils.SimplenoteLinkify.openNote(requireActivity(), url.replace(com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX, ""));
} else {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), url);
}
return true;
}

});
mCss = com.automattic.simplenote.utils.ContextUtils.readCssFile(requireContext(), com.automattic.simplenote.utils.ThemeUtils.getCssFromStyle(requireContext()));
} else {
((android.view.ViewStub) (mRootView.findViewById(com.automattic.simplenote.R.id.stub_error))).inflate();
switch(MUID_STATIC) {
// NoteEditorFragment_19_InvalidViewFocusOperatorMutator
case 1991: {
/**
* Inserted by Kadabra
*/
mError = mRootView.findViewById(com.automattic.simplenote.R.id.error);
mError.requestFocus();
break;
}
// NoteEditorFragment_20_ViewComponentNotVisibleOperatorMutator
case 2091: {
/**
* Inserted by Kadabra
*/
mError = mRootView.findViewById(com.automattic.simplenote.R.id.error);
mError.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mError = mRootView.findViewById(com.automattic.simplenote.R.id.error);
break;
}
}
switch(MUID_STATIC) {
// NoteEditorFragment_21_BuggyGUIListenerOperatorMutator
case 2191: {
mRootView.findViewById(com.automattic.simplenote.R.id.button).setOnClickListener(null);
break;
}
default: {
mRootView.findViewById(com.automattic.simplenote.R.id.button).setOnClickListener((android.view.View view) -> com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.utils.BrowserUtils.URL_WEB_VIEW));
break;
}
}
}
}
android.os.Bundle arguments;
arguments = getArguments();
if ((arguments != null) && arguments.containsKey(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID)) {
// Load note if we were passed a note Id
java.lang.String key;
key = arguments.getString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID);
if (arguments.containsKey(com.automattic.simplenote.NoteEditorFragment.ARG_MATCH_OFFSETS)) {
mMatchOffsets = arguments.getString(com.automattic.simplenote.NoteEditorFragment.ARG_MATCH_OFFSETS);
}
mIsFromWidget = arguments.getBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET);
if (mIsFromWidget) {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACTION, "Opened from widget (NoteEditorFragment)");
} else {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACTION, "Opened from list (NoteEditorFragment)");
}
new com.automattic.simplenote.NoteEditorFragment.LoadNoteTask(this).executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, key);
} else if (com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(getActivity()) && (savedInstanceState != null)) {
// Restore selected note when in dual pane mode
java.lang.String noteId;
noteId = savedInstanceState.getString(com.automattic.simplenote.NoteEditorFragment.STATE_NOTE_ID);
if (noteId != null) {
setNote(noteId);
}
}
setHasOptionsMenu(true);
return mRootView;
}


private void setObservers() {
viewModel.getUiState().observe(this, (com.automattic.simplenote.viewmodels.NoteEditorViewModel.UiState uiState) -> updateTagList(uiState.getTags()));
viewModel.getEvent().observe(this, (com.automattic.simplenote.viewmodels.NoteEditorViewModel.NoteEditorEvent noteEditorEvent) -> {
if (getContext() == null) {
return;
}
if (noteEditorEvent instanceof com.automattic.simplenote.viewmodels.NoteEditorViewModel.NoteEditorEvent.TagAsCollaborator) {
java.lang.String collaborator;
collaborator = ((com.automattic.simplenote.viewmodels.NoteEditorViewModel.NoteEditorEvent.TagAsCollaborator) (noteEditorEvent)).getCollaborator();
java.lang.String toastMessage;
toastMessage = getString(com.automattic.simplenote.R.string.tag_added_as_collaborator, collaborator);
android.widget.Toast.makeText(requireContext(), toastMessage, android.widget.Toast.LENGTH_LONG).show();
} else if (noteEditorEvent instanceof com.automattic.simplenote.viewmodels.NoteEditorViewModel.NoteEditorEvent.InvalidTag) {
android.widget.Toast.makeText(requireContext(), com.automattic.simplenote.R.string.invalid_tag, android.widget.Toast.LENGTH_LONG).show();
}
});
}


@java.lang.Override
public void onConfigurationChanged(@androidx.annotation.NonNull
android.content.res.Configuration newConfig) {
super.onConfigurationChanged(newConfig);
// If the user changes configuration and is still traversing keywords, we need to keep the scroll to the last
// keyword checked
if (mMatchOffsets != null) {
// mContentEditText.getLayout() can be null after a configuration change, thus, we need to check when the
// layout becomes available so that the scroll position can be set.
mRootView.getViewTreeObserver().addOnPreDrawListener(new android.view.ViewTreeObserver.OnPreDrawListener() {
@java.lang.Override
public boolean onPreDraw() {
if (mContentEditText.getLayout() != null) {
setScroll();
mRootView.getViewTreeObserver().removeOnPreDrawListener(this);
}
return true;
}

});
}
hideToolbarForLandscapeEditing();
}


private int getFirstSearchMatchLocation() {
if ((getActivity() != null) && (getActivity() instanceof com.automattic.simplenote.NoteEditorActivity)) {
return ((com.automattic.simplenote.NoteEditorActivity) (getActivity())).getCurrentSearchMatchIndexLocation();
}
int defaultFirstLocation;
defaultFirstLocation = com.automattic.simplenote.utils.MatchOffsetHighlighter.getFirstMatchLocation(mContentEditText.getText(), mMatchOffsets);
return defaultFirstLocation;
}


private void setScroll() {
// If a note was loaded with search matches, scroll to the first match in the editor
if (mMatchOffsets != null) {
if (!isAdded()) {
return;
}
// Get the character location of the first search match
int matchLocation;
matchLocation = getFirstSearchMatchLocation();
if (matchLocation == 0) {
return;
}
// Calculate how far to scroll to bring the match into view
android.text.Layout layout;
layout = mContentEditText.getLayout();
if (layout != null) {
int lineTop;
lineTop = layout.getLineTop(layout.getLineForOffset(matchLocation));
((androidx.core.widget.NestedScrollView) (mRootView)).smoothScrollTo(0, lineTop);
}
} else if ((mNote != null) && (mNote.getSimperiumKey() != null)) {
((androidx.core.widget.NestedScrollView) (mRootView)).scrollTo(0, mPreferences.getInt(mNote.getSimperiumKey(), 0));
mRootView.setOnScrollChangeListener((android.view.View v,int scrollX,int scrollY,int oldScrollX,int oldScrollY) -> {
if (mNote == null) {
return;
}
mPreferences.edit().putInt(mNote.getSimperiumKey(), scrollY).apply();
});
}
}


public void removeScrollListener() {
mRootView.setOnScrollChangeListener(null);
}


public void scrollToMatch(int location) {
if (isAdded()) {
// Calculate how far to scroll to bring the match into view
android.text.Layout layout;
layout = mContentEditText.getLayout();
int lineTop;
lineTop = layout.getLineTop(layout.getLineForOffset(location));
((androidx.core.widget.NestedScrollView) (mRootView)).smoothScrollTo(0, lineTop);
}
}


@java.lang.Override
public void onResume() {
super.onResume();
checkWebView();
mIsPaused = false;
mNotesBucket.addListener(this);
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Added note bucket listener (NoteEditorFragment)");
mTagInput.setOnTagAddedListener(this);
if (mContentEditText != null) {
mContentEditText.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, com.automattic.simplenote.utils.PrefUtils.getFontSize(requireContext()));
if (mContentEditText.hasFocus()) {
showSoftKeyboard();
}
}
}


private void checkWebView() {
// When a WebView is installed and mMarkdown is null on a large landscape device, a WebView
// was not installed when the fragment was created.  So, recreate the activity to refresh
// the editor view.
if ((com.automattic.simplenote.utils.BrowserUtils.isWebViewInstalled(requireContext()) && (mMarkdown == null)) && com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(requireContext())) {
requireActivity().recreate();
}
}


private void showSoftKeyboard() {
new android.os.Handler().postDelayed(() -> {
if (getActivity() == null) {
return;
}
android.view.inputmethod.InputMethodManager inputMethodManager;
inputMethodManager = ((android.view.inputmethod.InputMethodManager) (getActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
if (inputMethodManager != null) {
inputMethodManager.showSoftInput(mContentEditText, 0);
}
}, 100);
}


@java.lang.Override
public void onPause() {
super.onPause()// Always call the superclass method first
;// Always call the superclass method first

mIsPaused = true;
// Hide soft keyboard if it is showing...
com.automattic.simplenote.utils.DisplayUtils.hideKeyboard(mContentEditText);
mTagInput.setOnTagAddedListener(null);
if (mAutoSaveHandler != null) {
mAutoSaveHandler.removeCallbacks(mAutoSaveRunnable);
mAutoSaveHandler.post(mAutoSaveRunnable);
}
if (mPublishTimeoutHandler != null) {
mPublishTimeoutHandler.removeCallbacks(mPublishTimeoutRunnable);
}
if (mHistoryTimeoutHandler != null) {
mHistoryTimeoutHandler.removeCallbacks(mHistoryTimeoutRunnable);
}
mHighlighter.stop();
saveNote();
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Paused (NoteEditorFragment)");
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
mNotesBucket.removeListener(this);
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Removed note bucket listener (NoteEditorFragment)");
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Destroyed (NoteEditorFragment)");
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
if (com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(getActivity()) && (mNote != null)) {
outState.putString(com.automattic.simplenote.NoteEditorFragment.STATE_NOTE_ID, mNote.getSimperiumKey());
}
}


@java.lang.Override
public void onCreateOptionsMenu(@androidx.annotation.NonNull
android.view.Menu menu, @androidx.annotation.NonNull
android.view.MenuInflater inflater) {
super.onCreateOptionsMenu(menu, inflater);
if ((!isAdded()) || (((!mIsFromWidget) && com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(getActivity())) && (mNoteMarkdownFragment == null))) {
return;
}
inflater.inflate(com.automattic.simplenote.R.menu.note_editor, menu);
androidx.core.view.MenuCompat.setGroupDividerEnabled(menu, true);
}


@java.lang.Override
public boolean onOptionsItemSelected(@androidx.annotation.NonNull
android.view.MenuItem item) {
switch (item.getItemId()) {
case com.automattic.simplenote.R.id.menu_checklist :
insertChecklist();
return true;
case com.automattic.simplenote.R.id.menu_copy :
if (com.automattic.simplenote.utils.BrowserUtils.copyToClipboard(requireContext(), mNote.getPublishedUrl())) {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.link_copied, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
} else {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.link_copied_failure, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
}
return true;
case com.automattic.simplenote.R.id.menu_copy_internal :
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.INTERNOTE_LINK_COPIED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_LINK, "internote_link_copied_editor");
if (com.automattic.simplenote.utils.BrowserUtils.copyToClipboard(requireContext(), com.automattic.simplenote.utils.SimplenoteLinkify.getNoteLinkWithTitle(mNote.getTitle(), mNote.getSimperiumKey()))) {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.link_copied, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
} else {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.link_copied_failure, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
}
return true;
case com.automattic.simplenote.R.id.menu_collaborators :
navigateToCollaborators();
return true;
case com.automattic.simplenote.R.id.menu_history :
showHistory();
return true;
case com.automattic.simplenote.R.id.menu_info :
showInfo();
return true;
case com.automattic.simplenote.R.id.menu_markdown :
setMarkdown(!item.isChecked());
return true;
case com.automattic.simplenote.R.id.menu_pin :
com.automattic.simplenote.utils.NoteUtils.setNotePin(mNote, !item.isChecked());
requireActivity().invalidateOptionsMenu();
return true;
case com.automattic.simplenote.R.id.menu_publish :
if (item.isChecked()) {
unpublishNote();
} else {
publishNote();
}
return true;
case com.automattic.simplenote.R.id.menu_share :
shareNote();
return true;
case com.automattic.simplenote.R.id.menu_delete :
com.automattic.simplenote.utils.NoteUtils.showDialogDeletePermanently(requireActivity(), mNote);
return true;
case com.automattic.simplenote.R.id.menu_trash :
if (!isAdded()) {
return false;
}
deleteNote();
return true;
case android.R.id.home :
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACTION, "Tapped back arrow in app bar (NoteEditorFragment)");
if (!isAdded()) {
return false;
}
requireActivity().finish();
return true;
default :
return super.onOptionsItemSelected(item);
}
}


private void navigateToCollaborators() {
if ((getActivity() == null) || (mNote == null)) {
return;
}
android.content.Intent intent;
switch(MUID_STATIC) {
// NoteEditorFragment_22_NullIntentOperatorMutator
case 2291: {
intent = null;
break;
}
// NoteEditorFragment_23_InvalidKeyIntentOperatorMutator
case 2391: {
intent = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, com.automattic.simplenote.CollaboratorsActivity.class);
break;
}
// NoteEditorFragment_24_RandomActionIntentDefinitionOperatorMutator
case 2491: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(requireActivity(), com.automattic.simplenote.CollaboratorsActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteEditorFragment_25_NullValueIntentPutExtraOperatorMutator
case 2591: {
intent.putExtra(com.automattic.simplenote.CollaboratorsActivity.NOTE_ID_ARG, new Parcelable[0]);
break;
}
// NoteEditorFragment_26_IntentPayloadReplacementOperatorMutator
case 2691: {
intent.putExtra(com.automattic.simplenote.CollaboratorsActivity.NOTE_ID_ARG, "");
break;
}
default: {
switch(MUID_STATIC) {
// NoteEditorFragment_27_RandomActionIntentDefinitionOperatorMutator
case 2791: {
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
intent.putExtra(com.automattic.simplenote.CollaboratorsActivity.NOTE_ID_ARG, mNote.getSimperiumKey());
break;
}
}
break;
}
}
startActivity(intent);
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_COLLABORATORS_ACCESSED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "collaborators_ui_accessed");
}


@java.lang.Override
public void onPrepareOptionsMenu(@androidx.annotation.NonNull
android.view.Menu menu) {
if (mNote != null) {
android.view.MenuItem pinItem;
pinItem = menu.findItem(com.automattic.simplenote.R.id.menu_pin);
android.view.MenuItem shareItem;
shareItem = menu.findItem(com.automattic.simplenote.R.id.menu_share);
android.view.MenuItem historyItem;
historyItem = menu.findItem(com.automattic.simplenote.R.id.menu_history);
android.view.MenuItem publishItem;
publishItem = menu.findItem(com.automattic.simplenote.R.id.menu_publish);
android.view.MenuItem copyLinkItem;
copyLinkItem = menu.findItem(com.automattic.simplenote.R.id.menu_copy);
android.view.MenuItem markdownItem;
markdownItem = menu.findItem(com.automattic.simplenote.R.id.menu_markdown);
android.view.MenuItem deleteItem;
deleteItem = menu.findItem(com.automattic.simplenote.R.id.menu_delete);
android.view.MenuItem trashItem;
trashItem = menu.findItem(com.automattic.simplenote.R.id.menu_trash);
mChecklistMenuItem = menu.findItem(com.automattic.simplenote.R.id.menu_checklist);
mInformationMenuItem = menu.findItem(com.automattic.simplenote.R.id.menu_info).setVisible(true);
pinItem.setChecked(mNote.isPinned());
publishItem.setChecked(mNote.isPublished());
markdownItem.setChecked(mNote.isMarkdownEnabled());
// Disable actions when note is in Trash or markdown view is shown on large device.
if (mNote.isDeleted() || ((mMarkdown != null) && (mMarkdown.getVisibility() == android.view.View.VISIBLE))) {
pinItem.setEnabled(false);
shareItem.setEnabled(false);
historyItem.setEnabled(false);
publishItem.setEnabled(false);
copyLinkItem.setEnabled(false);
markdownItem.setEnabled(false);
mChecklistMenuItem.setEnabled(false);
com.automattic.simplenote.utils.DrawableUtils.setMenuItemAlpha(mChecklistMenuItem, 0.3)// 0.3 is 30% opacity.
;// 0.3 is 30% opacity.

} else {
pinItem.setEnabled(true);
shareItem.setEnabled(true);
historyItem.setEnabled(true);
publishItem.setEnabled(true);
copyLinkItem.setEnabled(mNote.isPublished());
markdownItem.setEnabled(true);
mChecklistMenuItem.setEnabled(true);
com.automattic.simplenote.utils.DrawableUtils.setMenuItemAlpha(mChecklistMenuItem, 1.0)// 1.0 is 100% opacity.
;// 1.0 is 100% opacity.

}
// Show delete action only when note is in Trash.
// Change trash action to restore when note is in Trash.
if (mNote.isDeleted()) {
deleteItem.setVisible(true);
trashItem.setTitle(com.automattic.simplenote.R.string.restore);
} else {
deleteItem.setVisible(false);
trashItem.setTitle(com.automattic.simplenote.R.string.trash);
}
}
com.automattic.simplenote.utils.DrawableUtils.tintMenuWithAttribute(getActivity(), menu, com.automattic.simplenote.R.attr.toolbarIconColor);
super.onPrepareOptionsMenu(menu);
}


public void insertChecklist() {
com.automattic.simplenote.utils.DrawableUtils.startAnimatedVectorDrawable(mChecklistMenuItem.getIcon());
try {
mContentEditText.insertChecklist();
} catch (java.lang.Exception e) {
e.printStackTrace();
return;
}
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_CHECKLIST_INSERTED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "toolbar_button");
}


@java.lang.Override
public void onCheckboxToggled() {
// Save note (using delay) after toggling a checkbox
if (mAutoSaveHandler != null) {
mAutoSaveHandler.removeCallbacks(mAutoSaveRunnable);
mAutoSaveHandler.postDelayed(mAutoSaveRunnable, com.automattic.simplenote.NoteEditorFragment.AUTOSAVE_DELAY_MILLIS);
}
}


private void deleteNote() {
com.automattic.simplenote.utils.NoteUtils.deleteNote(mNote, getActivity());
requireActivity().finish();
}


protected void clearMarkdown() {
if (mMarkdown != null) {
mMarkdown.loadDataWithBaseURL("file:///android_asset/", mCss + "", "text/html", "utf-8", null);
}
}


protected void hideMarkdown() {
if (com.automattic.simplenote.utils.BrowserUtils.isWebViewInstalled(requireContext()) && (mMarkdown != null)) {
mMarkdown.setVisibility(android.view.View.INVISIBLE);
} else {
mError.setVisibility(android.view.View.INVISIBLE);
}
}


protected void showMarkdown() {
loadMarkdownData();
if (com.automattic.simplenote.utils.BrowserUtils.isWebViewInstalled(requireContext()) && (mMarkdown != null)) {
mMarkdown.setVisibility(android.view.View.VISIBLE);
} else {
mError.setVisibility(android.view.View.VISIBLE);
}
new android.os.Handler().postDelayed(() -> {
if (!isDetached()) {
requireActivity().invalidateOptionsMenu();
}
}, getResources().getInteger(com.automattic.simplenote.R.integer.time_animation));
}


public void shareNote() {
if (mNote != null) {
mContentEditText.clearFocus();
showShareSheet();
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_CONTENT_SHARED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "action_bar_share_button");
}
}


public void showHistory() {
if ((mNote != null) && (mNote.getVersion() > 1)) {
mContentEditText.clearFocus();
mHistoryTimeoutHandler.postDelayed(mHistoryTimeoutRunnable, com.automattic.simplenote.NoteEditorFragment.HISTORY_TIMEOUT);
showHistorySheet();
} else {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.error_history, android.widget.Toast.LENGTH_LONG).show();
}
}


public void showInfo() {
com.automattic.simplenote.utils.DrawableUtils.startAnimatedVectorDrawable(mInformationMenuItem.getIcon());
if (mNote != null) {
mContentEditText.clearFocus();
saveNote();
showInfoSheet();
}
}


private void setMarkdown(boolean isChecked) {
mIsMarkdownEnabled = isChecked;
showMarkdownActionOrTabs();
saveNote();
// Set preference so that next new note will have markdown enabled.
android.content.SharedPreferences prefs;
prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext());
android.content.SharedPreferences.Editor editor;
editor = prefs.edit();
editor.putBoolean(com.automattic.simplenote.utils.PrefUtils.PREF_MARKDOWN_ENABLED, isChecked);
editor.apply();
}


private void setMarkdownEnabled(boolean enabled) {
mIsMarkdownEnabled = enabled;
if (mIsMarkdownEnabled) {
loadMarkdownData();
}
}


private void showMarkdownActionOrTabs() {
android.app.Activity activity;
activity = getActivity();
if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
com.automattic.simplenote.NoteEditorActivity editorActivity;
editorActivity = ((com.automattic.simplenote.NoteEditorActivity) (activity));
if (mIsMarkdownEnabled) {
editorActivity.showTabs();
if (mNoteMarkdownFragment == null) {
// Get markdown fragment and update content
mNoteMarkdownFragment = editorActivity.getNoteMarkdownFragment();
mNoteMarkdownFragment.updateMarkdown(mContentEditText.getPreviewTextContent());
}
} else {
editorActivity.hideTabs();
}
} else if (activity instanceof com.automattic.simplenote.NotesActivity) {
setMarkdownEnabled(mIsMarkdownEnabled);
((com.automattic.simplenote.NotesActivity) (getActivity())).setMarkdownShowing(false);
}
}


private void loadMarkdownData() {
java.lang.String formattedContent;
formattedContent = com.automattic.simplenote.NoteMarkdownFragment.getMarkdownFormattedContent(mCss, mContentEditText.getPreviewTextContent());
if (mMarkdown != null) {
mMarkdown.loadDataWithBaseURL(null, formattedContent, "text/html", "utf-8", null);
}
}


public void setNote(java.lang.String noteID, java.lang.String matchOffsets) {
if (mAutoSaveHandler != null) {
mAutoSaveHandler.removeCallbacks(mAutoSaveRunnable);
}
mPlaceholderView.setVisibility(android.view.View.GONE);
mMatchOffsets = matchOffsets;
saveNote();
new com.automattic.simplenote.NoteEditorFragment.LoadNoteTask(this).executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, noteID);
}


private void updateNote(com.automattic.simplenote.models.Note updatedNote) {
// update note if network change arrived
mNote = updatedNote;
refreshContent(true);
}


private void refreshContent(boolean isNoteUpdate) {
if (mNote != null) {
// Restore the cursor position if possible.
int cursorPosition;
cursorPosition = newCursorLocation(mNote.getContent(), getNoteContentString(), mContentEditText.getSelectionEnd());
mContentEditText.setText(mNote.getContent());
// Set the scroll position after the note's content has been rendered
mRootView.post(this::setScroll);
if (isNoteUpdate) {
// Update markdown and preview flags from updated note.
mIsMarkdownEnabled = mNote.isMarkdownEnabled();
mIsPreviewEnabled = mNote.isPreviewEnabled();
// Show/Hide action/tabs based on markdown flag.
showMarkdownActionOrTabs();
// Save note so any local changes get synced.
mNote.save();
// Update current note object on large screen devices in landscape orientation.
if (com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(requireContext())) {
((com.automattic.simplenote.NotesActivity) (requireActivity())).setCurrentNote(mNote);
}
// Update overflow popup menu.
requireActivity().invalidateOptionsMenu();
if ((mContentEditText.hasFocus() && (cursorPosition != mContentEditText.getSelectionEnd())) && (cursorPosition < mContentEditText.getText().length())) {
mContentEditText.setSelection(cursorPosition);
}
}
afterTextChanged(mContentEditText.getText());
mContentEditText.processChecklists();
viewModel.update(mNote);
}
}


private void updateTagList(java.util.List<java.lang.String> tags) {
setChips(tags);
mTagInput.setText("");
}


private int newCursorLocation(java.lang.String newText, java.lang.String oldText, int cursorLocation) {
// Ported from the iOS app :)
// Cases:
// 0. All text after cursor (and possibly more) was removed ==> put cursor at end
// 1. Text was added after the cursor ==> no change
// 2. Text was added before the cursor ==> location advances
// 3. Text was removed after the cursor ==> no change
// 4. Text was removed before the cursor ==> location retreats
// 5. Text was added/removed on both sides of the cursor ==> not handled
cursorLocation = java.lang.Math.max(cursorLocation, 0);
int newCursorLocation;
newCursorLocation = cursorLocation;
int deltaLength;
switch(MUID_STATIC) {
// NoteEditorFragment_28_BinaryMutator
case 2891: {
deltaLength = newText.length() + oldText.length();
break;
}
default: {
deltaLength = newText.length() - oldText.length();
break;
}
}
// Case 0
if (newText.length() < cursorLocation)
return newText.length();

boolean beforeCursorMatches;
beforeCursorMatches = false;
boolean afterCursorMatches;
afterCursorMatches = false;
switch(MUID_STATIC) {
// NoteEditorFragment_29_BinaryMutator
case 2991: {
try {
beforeCursorMatches = oldText.substring(0, cursorLocation).equals(newText.substring(0, cursorLocation));
afterCursorMatches = oldText.substring(cursorLocation).equals(newText.substring(cursorLocation - deltaLength));
} catch (java.lang.Exception e) {
e.printStackTrace();
}
break;
}
default: {
try {
beforeCursorMatches = oldText.substring(0, cursorLocation).equals(newText.substring(0, cursorLocation));
afterCursorMatches = oldText.substring(cursorLocation).equals(newText.substring(cursorLocation + deltaLength));
} catch (java.lang.Exception e) {
e.printStackTrace();
}
break;
}
}
// Cases 2 and 4
if ((!beforeCursorMatches) && afterCursorMatches)
newCursorLocation += deltaLength;

// Cases 1, 3 and 5 have no change
return newCursorLocation;
}


@java.lang.Override
public void onTagAdded(java.lang.String tag) {
// When a tag is added, it triggers an event that sends an empty tag. For those cases or if the note is null,
// we avoid updating the UI and the note
if (((mNote == null) || (!isAdded())) || android.text.TextUtils.isEmpty(tag)) {
return;
}
viewModel.addTag(tag, mNote);
}


@java.lang.Override
public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
// Unused
}


@java.lang.Override
public void afterTextChanged(android.text.Editable editable) {
attemptAutoList(editable);
setTitleSpan(editable);
mContentEditText.fixLineSpacing();
}


@java.lang.Override
public void onTextChanged(java.lang.CharSequence charSequence, int start, int before, int count) {
// When text changes, start timer that will fire after AUTOSAVE_DELAY_MILLIS passes
if (mAutoSaveHandler != null) {
mAutoSaveHandler.removeCallbacks(mAutoSaveRunnable);
mAutoSaveHandler.postDelayed(mAutoSaveRunnable, com.automattic.simplenote.NoteEditorFragment.AUTOSAVE_DELAY_MILLIS);
}
// Remove search highlight spans when note content changes
if (mMatchOffsets != null) {
mMatchOffsets = null;
mHighlighter.removeMatches();
}
if (!com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(requireContext())) {
((com.automattic.simplenote.NoteEditorActivity) (requireActivity())).setSearchMatchBarVisible(false);
}
// Temporarily remove the text watcher as we process checklists to prevent callback looping
mContentEditText.removeTextChangedListener(this);
mContentEditText.processChecklists();
mContentEditText.addTextChangedListener(this);
}


/**
 * Set the note title to be a larger size and bold style.
 *
 * Remove all existing spans before applying spans or performance issues will occur.  Since both
 * {@link RelativeSizeSpan} and {@link StyleSpan} inherit from {@link MetricAffectingSpan}, all
 * spans are removed when {@link MetricAffectingSpan} is removed.
 */
private void setTitleSpan(android.text.Editable editable) {
for (android.text.style.MetricAffectingSpan span : editable.getSpans(0, editable.length(), android.text.style.MetricAffectingSpan.class)) {
if ((span instanceof android.text.style.RelativeSizeSpan) || (span instanceof android.text.style.StyleSpan)) {
editable.removeSpan(span);
}
}
int newLinePosition;
newLinePosition = getNoteContentString().indexOf("\n");
if (newLinePosition == 0) {
return;
}
int titleEndPosition;
titleEndPosition = (newLinePosition > 0) ? newLinePosition : editable.length();
editable.setSpan(new android.text.style.RelativeSizeSpan(1.3F), 0, titleEndPosition, android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
editable.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, titleEndPosition, android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
}


private void attemptAutoList(android.text.Editable editable) {
int oldCursorPosition;
oldCursorPosition = mCurrentCursorPosition;
mCurrentCursorPosition = mContentEditText.getSelectionStart();
com.automattic.simplenote.utils.AutoBullet.apply(editable, oldCursorPosition, mCurrentCursorPosition);
mCurrentCursorPosition = mContentEditText.getSelectionStart();
}


private void saveAndSyncNote() {
if (mNote == null) {
return;
}
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACTION, ((((((("Edited note (ID: " + mNote.getSimperiumKey()) + " / Title: ") + mNote.getTitle()) + " / Characters: ") + com.automattic.simplenote.utils.NoteUtils.getCharactersCount(mNote.getContent())) + " / Words: ") + com.automattic.simplenote.utils.NoteUtils.getWordCount(mNote.getContent())) + ")");
new com.automattic.simplenote.NoteEditorFragment.SaveNoteTask(this).executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
}


public boolean isPlaceholderVisible() {
if (mPlaceholderView != null) {
return mPlaceholderView.getVisibility() == android.view.View.VISIBLE;
} else {
return false;
}
}


public void setPlaceholderVisible(boolean isVisible) {
if (isVisible) {
mNote = null;
mContentEditText.setText("");
}
if (mPlaceholderView != null) {
mPlaceholderView.setVisibility(isVisible ? android.view.View.VISIBLE : android.view.View.GONE);
}
}


@java.lang.Override
public void onFocusChange(android.view.View v, boolean hasFocus) {
if (!hasFocus) {
// When the tag field looses focus, if it is not empty, the tag is added
java.lang.String tag;
tag = mTagInput.getText().toString().trim();
if ((tag.length() > 0) && (mNote != null)) {
viewModel.addTag(tag, mNote);
}
}
hideToolbarForLandscapeEditing();
}


void hideToolbarForLandscapeEditing() {
if (((getActivity() == null) || (!(getActivity() instanceof com.automattic.simplenote.NoteEditorActivity))) || (mNote == null)) {
return;
}
com.automattic.simplenote.NoteEditorActivity activity;
activity = ((com.automattic.simplenote.NoteEditorActivity) (requireActivity()));
int displayMode;
displayMode = getResources().getConfiguration().orientation;
if ((mContentEditText.hasFocus() && (displayMode == android.content.res.Configuration.ORIENTATION_LANDSCAPE)) && (!activity.isPreviewTabSelected())) {
if (mNote.isMarkdownEnabled()) {
activity.hideTabs();
}
if (activity.getSupportActionBar() != null) {
activity.getSupportActionBar().hide();
}
} else {
if (mNote.isMarkdownEnabled()) {
activity.showTabs();
}
if (activity.getSupportActionBar() != null) {
activity.getSupportActionBar().show();
}
}
}


private com.automattic.simplenote.models.Note getNote() {
return mNote;
}


public void setNote(java.lang.String noteID) {
setNote(noteID, null);
}


private java.lang.String getNoteContentString() {
if ((mContentEditText == null) || (mContentEditText.getText() == null)) {
return "";
} else {
return mContentEditText.getText().toString();
}
}


/**
 * Share bottom sheet callbacks
 */
@java.lang.Override
public void onSharePublishClicked() {
publishNote();
if (mShareBottomSheet != null) {
mShareBottomSheet.dismiss();
}
}


@java.lang.Override
public void onShareUnpublishClicked() {
unpublishNote();
if (mShareBottomSheet != null) {
mShareBottomSheet.dismiss();
}
}


@java.lang.Override
public void onWordPressPostClicked() {
if (mShareBottomSheet != null) {
mShareBottomSheet.dismiss();
}
if (getFragmentManager() == null) {
return;
}
androidx.fragment.app.FragmentTransaction ft;
ft = getFragmentManager().beginTransaction();
androidx.fragment.app.Fragment prev;
prev = getFragmentManager().findFragmentByTag(com.automattic.simplenote.WordPressDialogFragment.DIALOG_TAG);
if (prev != null) {
ft.remove(prev);
}
ft.addToBackStack(null);
// Create and show the dialog.
com.automattic.simplenote.WordPressDialogFragment wpDialogFragment;
wpDialogFragment = new com.automattic.simplenote.WordPressDialogFragment();
wpDialogFragment.setNote(mNote);
wpDialogFragment.show(ft, com.automattic.simplenote.WordPressDialogFragment.DIALOG_TAG);
}


@java.lang.Override
public void onShareCollaborateClicked() {
android.widget.Toast.makeText(getActivity(), com.automattic.simplenote.R.string.collaborate_message, android.widget.Toast.LENGTH_LONG).show();
}


@java.lang.Override
public void onShareDismissed() {
}


/**
 * History bottom sheet listeners
 */
@java.lang.Override
public void onHistoryCancelClicked() {
mContentEditText.setText(mNote.getContent());
if (mHistoryBottomSheet != null) {
mHistoryBottomSheet.dismiss();
}
}


@java.lang.Override
public void onHistoryRestoreClicked() {
if (mHistoryBottomSheet != null) {
mHistoryBottomSheet.dismiss();
}
saveAndSyncNote();
}


@java.lang.Override
public void onHistoryDismissed() {
if (!mHistoryBottomSheet.didTapOnButton()) {
mContentEditText.setText(mNote.getContent());
}
if (mHistoryTimeoutHandler != null) {
mHistoryTimeoutHandler.removeCallbacks(mHistoryTimeoutRunnable);
}
}


@java.lang.Override
public void onHistoryUpdateNote(java.lang.String content) {
mContentEditText.setText(content);
}


private void saveNote() {
try {
if (((((mNote == null) || (mNotesBucket == null)) || (mContentEditText == null)) || mIsLoadingNote) || (((mHistoryBottomSheet != null) && (mHistoryBottomSheet.getDialog() != null)) && mHistoryBottomSheet.getDialog().isShowing())) {
return;
} else {
mNote = mNotesBucket.get(mNote.getSimperiumKey());
mIsPreviewEnabled = mNote.isPreviewEnabled();
}
java.lang.String content;
content = mContentEditText.getPlainTextContent();
if (mNote.hasChanges(content, mNote.isPinned(), mIsMarkdownEnabled, mIsPreviewEnabled)) {
mNote.setContent(content);
mNote.setModificationDate(java.util.Calendar.getInstance());
mNote.setMarkdownEnabled(mIsMarkdownEnabled);
mNote.setPreviewEnabled(mIsPreviewEnabled);
mNote.save();
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_EDITED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "editor_save");
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, ((((((("Saved note locally in NoteEditorFragment (ID: " + mNote.getSimperiumKey()) + " / Title: ") + mNote.getTitle()) + " / Characters: ") + com.automattic.simplenote.utils.NoteUtils.getCharactersCount(content)) + " / Words: ") + com.automattic.simplenote.utils.NoteUtils.getWordCount(content)) + ")");
}
} catch (com.simperium.client.BucketObjectMissingException exception) {
exception.printStackTrace();
}
}


// Checks if cursor is at a URL when the selection changes
// If it is a URL, show the contextual action bar
@java.lang.Override
public void onSelectionChanged(int selStart, int selEnd) {
mCurrentCursorPosition = selEnd;
if (selStart == selEnd) {
android.text.Editable noteContent;
noteContent = mContentEditText.getText();
if (noteContent == null) {
return;
}
android.text.style.URLSpan[] urlSpans;
urlSpans = noteContent.getSpans(selStart, selStart, android.text.style.URLSpan.class);
if (urlSpans.length > 0) {
android.text.style.URLSpan urlSpan;
urlSpan = urlSpans[0];
mLinkUrl = urlSpan.getURL();
mLinkText = noteContent.subSequence(noteContent.getSpanStart(urlSpan), noteContent.getSpanEnd(urlSpan)).toString();
if (mActionMode != null) {
mActionMode.setSubtitle(mLinkText);
updateMenuItems();
return;
}
// Show the Contextual Action Bar
if (getActivity() != null) {
mActionMode = ((androidx.appcompat.app.AppCompatActivity) (getActivity())).startSupportActionMode(mActionModeCallback);
if (mActionMode != null) {
mActionMode.setSubtitle(mLinkText);
}
updateMenuItems();
}
} else if (mActionMode != null) {
mActionMode.finish();
mActionMode = null;
}
} else if (mActionMode != null) {
mActionMode.finish();
mActionMode = null;
}
}


private void updateMenuItems() {
mCopyMenuItem.setIcon(mCopyIcon);
mShareMenuItem.setIcon(mShareIcon);
if ((mViewLinkMenuItem != null) && (mLinkUrl != null)) {
if (mLinkUrl.startsWith("tel:")) {
mViewLinkMenuItem.setIcon(mCallIcon);
mViewLinkMenuItem.setTitle(getString(com.automattic.simplenote.R.string.call));
} else if (mLinkUrl.startsWith("mailto:")) {
mViewLinkMenuItem.setIcon(mEmailIcon);
mViewLinkMenuItem.setTitle(getString(com.automattic.simplenote.R.string.email));
} else if (mLinkUrl.startsWith("geo:")) {
mViewLinkMenuItem.setIcon(mMapIcon);
mViewLinkMenuItem.setTitle(getString(com.automattic.simplenote.R.string.view_map));
} else if (mLinkUrl.startsWith(com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX)) {
mViewLinkMenuItem.setIcon(mLinkIcon);
mViewLinkMenuItem.setTitle(getString(com.automattic.simplenote.R.string.open_note));
} else {
mViewLinkMenuItem.setIcon(mBrowserIcon);
mViewLinkMenuItem.setTitle(getString(com.automattic.simplenote.R.string.view_in_browser));
}
}
}


private void setPublishedNote(boolean isPublished) {
if (mNote != null) {
mNote.setPublished(isPublished);
mNote.save();
// reset publish status in 20 seconds if we don't hear back from Simperium
mPublishTimeoutHandler.postDelayed(mPublishTimeoutRunnable, com.automattic.simplenote.NoteEditorFragment.PUBLISH_TIMEOUT);
com.automattic.simplenote.analytics.AnalyticsTracker.track(isPublished ? com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_PUBLISHED : com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_UNPUBLISHED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "publish_note_button");
}
}


private void updatePublishedState(boolean isSuccess) {
if (mPublishingSnackbar == null) {
return;
}
mPublishingSnackbar.dismiss();
mPublishingSnackbar = null;
if (isSuccess && isAdded()) {
if (mNote.isPublished()) {
if (mHideActionOnSuccess) {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.publish_successful, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
} else {
switch(MUID_STATIC) {
// NoteEditorFragment_30_BuggyGUIListenerOperatorMutator
case 3091: {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.publish_successful, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(com.automattic.simplenote.R.string.undo, null).show();
break;
}
default: {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.publish_successful, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(com.automattic.simplenote.R.string.undo, (android.view.View v) -> {
mHideActionOnSuccess = true;
unpublishNote();
}).show();
break;
}
}
}
} else if (mHideActionOnSuccess) {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.unpublish_successful, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
} else {
switch(MUID_STATIC) {
// NoteEditorFragment_31_BuggyGUIListenerOperatorMutator
case 3191: {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.unpublish_successful, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(com.automattic.simplenote.R.string.undo, null).show();
break;
}
default: {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.unpublish_successful, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(com.automattic.simplenote.R.string.undo, (android.view.View v) -> {
mHideActionOnSuccess = true;
publishNote();
}).show();
break;
}
}
}
} else if (mNote.isPublished()) {
switch(MUID_STATIC) {
// NoteEditorFragment_32_BuggyGUIListenerOperatorMutator
case 3291: {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.unpublish_error, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(com.automattic.simplenote.R.string.retry, null).show();
break;
}
default: {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.unpublish_error, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(com.automattic.simplenote.R.string.retry, (android.view.View v) -> {
mHideActionOnSuccess = true;
unpublishNote();
}).show();
break;
}
}
} else {
switch(MUID_STATIC) {
// NoteEditorFragment_33_BuggyGUIListenerOperatorMutator
case 3391: {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.publish_error, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(com.automattic.simplenote.R.string.retry, null).show();
break;
}
default: {
com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.publish_error, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(com.automattic.simplenote.R.string.retry, (android.view.View v) -> {
mHideActionOnSuccess = true;
publishNote();
}).show();
break;
}
}
}
mHideActionOnSuccess = false;
requireActivity().invalidateOptionsMenu();
}


private void publishNote() {
if (!com.automattic.simplenote.utils.NetworkUtils.isNetworkAvailable(requireContext())) {
android.widget.Toast.makeText(requireContext(), com.automattic.simplenote.R.string.error_network_required, android.widget.Toast.LENGTH_LONG).show();
return;
}
if (isAdded()) {
mPublishingSnackbar = com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.publishing, com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE);
mPublishingSnackbar.show();
}
setPublishedNote(true);
}


private void unpublishNote() {
if (!com.automattic.simplenote.utils.NetworkUtils.isNetworkAvailable(requireContext())) {
android.widget.Toast.makeText(requireContext(), com.automattic.simplenote.R.string.error_network_required, android.widget.Toast.LENGTH_LONG).show();
return;
}
if (isAdded()) {
mPublishingSnackbar = com.google.android.material.snackbar.Snackbar.make(mRootView, com.automattic.simplenote.R.string.unpublishing, com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE);
mPublishingSnackbar.show();
}
setPublishedNote(false);
}


private void showShare(java.lang.String text) {
startActivity(androidx.core.app.ShareCompat.IntentBuilder.from(requireActivity()).setText(text).setType("text/plain").createChooserIntent());
}


private void showShareSheet() {
if ((isAdded() && (mShareBottomSheet != null)) && (!mShareBottomSheet.isAdded())) {
mShareBottomSheet.show(requireFragmentManager(), mNote);
}
}


private void showInfoSheet() {
if ((isAdded() && (mInfoBottomSheet != null)) && (!mInfoBottomSheet.isAdded())) {
mInfoBottomSheet.show(requireFragmentManager(), mNote);
}
}


private void showHistorySheet() {
if ((isAdded() && (mHistoryBottomSheet != null)) && (!mHistoryBottomSheet.isAdded())) {
// Request revisions for the current note
mNotesBucket.getRevisions(mNote, com.automattic.simplenote.NoteEditorFragment.MAX_REVISIONS, mHistoryBottomSheet.getRevisionsRequestCallbacks());
saveNote();
mHistoryBottomSheet.show(requireFragmentManager(), mNote);
}
}


@java.lang.Override
public void onDeleteObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> noteBucket, com.automattic.simplenote.models.Note note) {
}


@java.lang.Override
public void onNetworkChange(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> noteBucket, com.simperium.client.Bucket.ChangeType changeType, final java.lang.String key) {
if (changeType == com.simperium.client.Bucket.ChangeType.MODIFY) {
if ((getNote() != null) && getNote().getSimperiumKey().equals(key)) {
try {
final com.automattic.simplenote.models.Note updatedNote;
updatedNote = noteBucket.get(key);
if (getActivity() != null) {
getActivity().runOnUiThread(() -> {
if (mPublishTimeoutHandler != null) {
mPublishTimeoutHandler.removeCallbacks(mPublishTimeoutRunnable);
}
updateNote(updatedNote);
updatePublishedState(true);
});
}
} catch (com.simperium.client.BucketObjectMissingException e) {
e.printStackTrace();
}
}
}
}


@java.lang.Override
public void onSaveObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> noteBucket, com.automattic.simplenote.models.Note note) {
if (mIsPaused) {
mNotesBucket.removeListener(this);
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Removed note bucket listener (NoteEditorFragment)");
}
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, ((((((("Saved note callback in NoteEditorFragment (ID: " + note.getSimperiumKey()) + " / Title: ") + note.getTitle()) + " / Characters: ") + com.automattic.simplenote.utils.NoteUtils.getCharactersCount(note.getContent())) + " / Words: ") + com.automattic.simplenote.utils.NoteUtils.getWordCount(note.getContent())) + ")");
}


@java.lang.Override
public void onBeforeUpdateObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, com.automattic.simplenote.models.Note note) {
// Don't apply updates if we haven't loaded the note yet
if (mIsLoadingNote)
return;

com.automattic.simplenote.models.Note openNote;
openNote = getNote();
if ((openNote == null) || (!openNote.getSimperiumKey().equals(note.getSimperiumKey())))
return;

note.setContent(mContentEditText.getPlainTextContent());
}


@java.lang.Override
public void onLocalQueueChange(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, java.util.Set<java.lang.String> queuedObjects) {
}


@java.lang.Override
public void onSyncObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, java.lang.String key) {
}


private static class LoadNoteTask extends android.os.AsyncTask<java.lang.String, java.lang.Void, java.lang.Void> {
java.lang.ref.WeakReference<com.automattic.simplenote.NoteEditorFragment> mNoteEditorFragmentReference;

LoadNoteTask(com.automattic.simplenote.NoteEditorFragment fragment) {
mNoteEditorFragmentReference = new java.lang.ref.WeakReference<>(fragment);
}


@java.lang.Override
protected void onPreExecute() {
com.automattic.simplenote.NoteEditorFragment fragment;
fragment = mNoteEditorFragmentReference.get();
if (fragment != null) {
fragment.mContentEditText.removeTextChangedListener(fragment);
fragment.mIsLoadingNote = true;
}
}


@java.lang.Override
protected java.lang.Void doInBackground(java.lang.String... args) {
com.automattic.simplenote.NoteEditorFragment fragment;
fragment = mNoteEditorFragmentReference.get();
if ((fragment == null) || (fragment.getActivity() == null)) {
return null;
}
java.lang.String noteID;
noteID = args[0];
com.automattic.simplenote.Simplenote application;
application = ((com.automattic.simplenote.Simplenote) (fragment.getActivity().getApplication()));
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket;
notesBucket = application.getNotesBucket();
try {
fragment.mNote = notesBucket.get(noteID);
// Set the current note in NotesActivity when on a tablet
if (fragment.getActivity() instanceof com.automattic.simplenote.NotesActivity) {
((com.automattic.simplenote.NotesActivity) (fragment.getActivity())).setCurrentNote(fragment.mNote);
}
// Set markdown and preview flags for current note
if (fragment.mNote != null) {
fragment.mIsMarkdownEnabled = fragment.mNote.isMarkdownEnabled();
fragment.mIsPreviewEnabled = fragment.mNote.isPreviewEnabled();
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, ((((((("Loaded note (ID: " + fragment.mNote.getSimperiumKey()) + " / Title: ") + fragment.mNote.getTitle()) + " / Characters: ") + com.automattic.simplenote.utils.NoteUtils.getCharactersCount(fragment.mNote.getContent())) + " / Words: ") + com.automattic.simplenote.utils.NoteUtils.getWordCount(fragment.mNote.getContent())) + ")");
}
} catch (com.simperium.client.BucketObjectMissingException e) {
// See if the note is in the object store
com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> notesCursor;
notesCursor = notesBucket.allObjects();
while (notesCursor.moveToNext()) {
com.automattic.simplenote.models.Note currentNote;
currentNote = notesCursor.getObject();
if ((currentNote != null) && currentNote.getSimperiumKey().equals(noteID)) {
fragment.mNote = currentNote;
return null;
}
} 
}
return null;
}


@java.lang.Override
protected void onPostExecute(java.lang.Void nada) {
final com.automattic.simplenote.NoteEditorFragment fragment;
fragment = mNoteEditorFragmentReference.get();
if (((fragment == null) || (fragment.getActivity() == null)) || fragment.getActivity().isFinishing()) {
return;
}
fragment.refreshContent(false);
if (fragment.mMatchOffsets != null) {
int columnIndex;
columnIndex = fragment.mNote.getBucket().getSchema().getFullTextIndex().getColumnIndex(com.automattic.simplenote.models.Note.CONTENT_PROPERTY);
fragment.mHighlighter.highlightMatches(fragment.mMatchOffsets, columnIndex);
}
fragment.mContentEditText.addTextChangedListener(fragment);
if ((fragment.mNote != null) && fragment.mNote.getContent().isEmpty()) {
// Show soft keyboard
fragment.mContentEditText.requestFocus();
new android.os.Handler().postDelayed(() -> {
if (fragment.getActivity() == null) {
return;
}
android.view.inputmethod.InputMethodManager inputMethodManager;
inputMethodManager = ((android.view.inputmethod.InputMethodManager) (fragment.getActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
if (inputMethodManager != null) {
inputMethodManager.showSoftInput(fragment.mContentEditText, 0);
}
}, 100);
} else if (fragment.mNote != null) {
// If we have a valid note, hide the placeholder
fragment.setPlaceholderVisible(false);
}
fragment.updateMarkdownView();
fragment.requireActivity().invalidateOptionsMenu();
fragment.linkifyEditorContent();
fragment.mIsLoadingNote = false;
}

}

private static class SaveNoteTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void> {
java.lang.ref.WeakReference<com.automattic.simplenote.NoteEditorFragment> mNoteEditorFragmentReference;

SaveNoteTask(com.automattic.simplenote.NoteEditorFragment fragment) {
mNoteEditorFragmentReference = new java.lang.ref.WeakReference<>(fragment);
}


@java.lang.Override
protected java.lang.Void doInBackground(java.lang.Void... args) {
com.automattic.simplenote.NoteEditorFragment fragment;
fragment = mNoteEditorFragmentReference.get();
if (fragment != null) {
fragment.saveNote();
}
return null;
}


@java.lang.Override
protected void onPostExecute(java.lang.Void nada) {
com.automattic.simplenote.NoteEditorFragment fragment;
fragment = mNoteEditorFragmentReference.get();
if (((fragment != null) && (fragment.getActivity() != null)) && (!fragment.getActivity().isFinishing())) {
// Update links
fragment.linkifyEditorContent();
fragment.updateMarkdownView();
}
}

}

private void linkifyEditorContent() {
if ((getActivity() == null) || getActivity().isFinishing()) {
return;
}
if (com.automattic.simplenote.utils.PrefUtils.getBoolPref(getActivity(), com.automattic.simplenote.utils.PrefUtils.PREF_DETECT_LINKS)) {
com.automattic.simplenote.utils.SimplenoteLinkify.addLinks(mContentEditText, android.text.util.Linkify.ALL);
}
}


// Show tabs if markdown is enabled globally, for current note, and not tablet landscape
private void updateMarkdownView() {
if (!mIsMarkdownEnabled) {
return;
}
android.app.Activity activity;
activity = getActivity();
if (activity instanceof com.automattic.simplenote.NotesActivity) {
// This fragment lives in NotesActivity, so load markdown in this fragment's WebView.
loadMarkdownData();
} else {
// This fragment lives in the NoteEditorActivity's ViewPager.
if (mNoteMarkdownFragment == null) {
mNoteMarkdownFragment = ((com.automattic.simplenote.NoteEditorActivity) (requireActivity())).getNoteMarkdownFragment();
((com.automattic.simplenote.NoteEditorActivity) (requireActivity())).showTabs();
}
// Load markdown in the sibling NoteMarkdownFragment's WebView.
mNoteMarkdownFragment.updateMarkdown(mContentEditText.getPreviewTextContent());
}
}


private android.content.res.ColorStateList getChipBackgroundColor() {
int[][] states;
states = new int[][]{ new int[]{ android.R.attr.state_checked }// checked
// checked
// checked
, new int[]{ -android.R.attr.state_checked }// unchecked
// unchecked
// unchecked
 };
int[] colors;
colors = new int[]{ com.automattic.simplenote.utils.ThemeUtils.getColorFromAttribute(requireContext(), com.automattic.simplenote.R.attr.chipCheckedOnBackgroundColor), com.automattic.simplenote.utils.ThemeUtils.getColorFromAttribute(requireContext(), com.automattic.simplenote.R.attr.chipCheckedOffBackgroundColor) };
return new android.content.res.ColorStateList(states, colors);
}


private void setChips(java.util.List<java.lang.String> tags) {
mTagPadding.setVisibility(tags.size() > 0 ? android.view.View.VISIBLE : android.view.View.GONE);
mTagChips.setVisibility(tags.size() > 0 ? android.view.View.VISIBLE : android.view.View.GONE);
mTagChips.setSingleSelection(true);
mTagChips.removeAllViews();
for (java.lang.String tag : tags) {
final com.google.android.material.chip.Chip chip;
chip = new com.google.android.material.chip.Chip(requireContext());
chip.setText(tag);
chip.setCheckable(true);
chip.setCheckedIcon(null);
chip.setChipBackgroundColor(getChipBackgroundColor());
chip.setTextColor(com.automattic.simplenote.utils.ThemeUtils.getColorFromAttribute(requireContext(), com.automattic.simplenote.R.attr.chipTextColor));
chip.setStateListAnimator(null);
chip.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> chip.setCloseIconVisible(isChecked));
switch(MUID_STATIC) {
// NoteEditorFragment_34_BuggyGUIListenerOperatorMutator
case 3491: {
chip.setOnCloseIconClickListener(null);
break;
}
default: {
chip.setOnCloseIconClickListener((android.view.View view) -> {
if (mNote == null) {
return;
}
java.lang.String tagName;
tagName = ((com.google.android.material.chip.Chip) (view)).getText().toString();
viewModel.removeTag(tagName, mNote);
});
break;
}
}
mTagChips.addView(chip);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
