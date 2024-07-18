package com.automattic.simplenote;
import android.text.style.TextAppearanceSpan;
import com.simperium.client.Query;
import java.util.ArrayList;
import com.automattic.simplenote.utils.ThemeUtils;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.RECENT_SEARCH_TAPPED;
import static com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_ASCENDING;
import android.database.Cursor;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.TypedValue;
import com.automattic.simplenote.models.Suggestion;
import android.widget.CursorAdapter;
import static com.automattic.simplenote.models.Note.TAGS_PROPERTY;
import android.widget.ListView;
import static com.automattic.simplenote.models.Preferences.MAX_RECENT_SEARCHES;
import android.view.HapticFeedbackConstants;
import androidx.annotation.NonNull;
import com.simperium.client.BucketObjectNameInvalid;
import java.lang.ref.SoftReference;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import static com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_DESCENDING;
import com.automattic.simplenote.utils.DrawableUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.annotation.DrawableRes;
import com.automattic.simplenote.utils.SearchTokenizer;
import android.os.Handler;
import androidx.fragment.app.ListFragment;
import android.widget.AbsListView;
import com.automattic.simplenote.utils.SimplenoteLinkify;
import android.os.AsyncTask;
import android.text.style.AbsoluteSizeSpan;
import android.text.SpannableString;
import com.automattic.simplenote.utils.ChecklistUtils;
import static com.automattic.simplenote.models.Suggestion.Type.QUERY;
import android.view.LayoutInflater;
import com.simperium.client.Bucket;
import android.view.ActionMode;
import com.automattic.simplenote.utils.DisplayUtils;
import com.automattic.simplenote.models.Note;
import androidx.appcompat.widget.PopupMenu;
import android.widget.RelativeLayout;
import static com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_DESCENDING;
import android.widget.AdapterView;
import com.google.android.material.snackbar.Snackbar;
import com.automattic.simplenote.utils.BrowserUtils;
import com.simperium.client.Bucket.ObjectCursor;
import com.automattic.simplenote.utils.SearchSnippetFormatter;
import java.util.Set;
import java.util.regex.Matcher;
import static com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY;
import com.automattic.simplenote.utils.AppLog;
import static com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_DESCENDING;
import android.widget.ImageButton;
import com.automattic.simplenote.utils.NetworkUtils;
import com.automattic.simplenote.widgets.RobotoRegularTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.DiffUtil;
import android.util.SparseBooleanArray;
import android.widget.Toast;
import com.automattic.simplenote.models.Preferences;
import com.automattic.simplenote.utils.TextHighlighter;
import android.view.Gravity;
import com.automattic.simplenote.utils.PrefUtils;
import java.util.regex.Pattern;
import android.widget.LinearLayout;
import com.automattic.simplenote.utils.StrUtils;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import static com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_ASCENDING;
import java.util.Calendar;
import android.os.Bundle;
import android.view.ViewGroup;
import android.text.TextUtils;
import static com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_ASCENDING;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.view.MenuItem;
import com.simperium.client.BucketObjectMissingException;
import android.view.MotionEvent;
import android.view.View;
import static com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_SEARCH;
import com.automattic.simplenote.utils.AppLog.Type;
import com.automattic.simplenote.utils.WidgetUtils;
import android.annotation.SuppressLint;
import static com.automattic.simplenote.models.Suggestion.Type.TAG;
import com.automattic.simplenote.models.Tag;
import androidx.recyclerview.widget.RecyclerView;
import com.automattic.simplenote.utils.DateTimeUtils;
import static com.automattic.simplenote.models.Suggestion.Type.HISTORY;
import static com.automattic.simplenote.models.Tag.NAME_PROPERTY;
import android.content.Context;
import android.text.SpannableStringBuilder;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * A list fragment representing a list of Notes. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link NoteEditorFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class NoteListFragment extends androidx.fragment.app.ListFragment implements android.widget.AdapterView.OnItemLongClickListener , android.widget.AbsListView.MultiChoiceModeListener , com.simperium.client.Bucket.Listener<com.automattic.simplenote.models.Preferences> {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG_PREFIX = "tag:";

    /**
     * The preferences key representing the activated item position. Only used on tablets.
     */
    private static final java.lang.String STATE_ACTIVATED_POSITION = "activated_position";

    private static final int POPUP_MENU_FIRST_ITEM_POSITION = 0;

    public static final java.lang.String ACTION_NEW_NOTE = "com.automattic.simplenote.NEW_NOTE";

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static com.automattic.simplenote.NoteListFragment.Callbacks sCallbacks = new com.automattic.simplenote.NoteListFragment.Callbacks() {
        @java.lang.Override
        public void onActionModeCreated() {
        }


        @java.lang.Override
        public void onActionModeDestroyed() {
        }


        @java.lang.Override
        public void onNoteSelected(java.lang.String noteID, java.lang.String matchOffsets, boolean isMarkdownEnabled, boolean isPreviewEnabled) {
        }

    };

    protected com.automattic.simplenote.NoteListFragment.NotesCursorAdapter mNotesAdapter;

    protected java.lang.String mSearchString;

    private com.simperium.client.Bucket<com.automattic.simplenote.models.Preferences> mBucketPreferences;

    private com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> mBucketTag;

    private android.view.ActionMode mActionMode;

    private android.view.View mRootView;

    private com.automattic.simplenote.widgets.RobotoRegularTextView mEmptyViewButton;

    private android.widget.ImageView mEmptyViewImage;

    private android.widget.TextView mEmptyViewText;

    private android.view.View mDividerLine;

    private com.google.android.material.floatingactionbutton.FloatingActionButton mFloatingActionButton;

    private boolean mIsCondensedNoteList;

    private boolean mIsSearching;

    private android.widget.ListView mList;

    private androidx.recyclerview.widget.RecyclerView mSuggestionList;

    private android.widget.RelativeLayout mSuggestionLayout;

    private java.lang.String mSelectedNoteId;

    private com.automattic.simplenote.NoteListFragment.SuggestionAdapter mSuggestionAdapter;

    private com.automattic.simplenote.NoteListFragment.RefreshListTask mRefreshListTask;

    private com.automattic.simplenote.NoteListFragment.RefreshListForSearchTask mRefreshListForSearchTask;

    private int mDeletedItemIndex;

    private int mTitleFontSize;

    private int mPreviewFontSize;

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private com.automattic.simplenote.NoteListFragment.Callbacks mCallbacks = com.automattic.simplenote.NoteListFragment.sCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = android.widget.ListView.INVALID_POSITION;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteListFragment() {
    }


    @java.lang.Override
    public boolean onItemLongClick(android.widget.AdapterView<?> adapterView, android.view.View view, int position, long l) {
        getListView().setChoiceMode(android.widget.ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setItemChecked(position, true);
        if (mActionMode == null) {
            requireActivity().startActionMode(this);
        }
        return true;
    }


    @java.lang.Override
    public boolean onCreateActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
        mCallbacks.onActionModeCreated();
        android.view.MenuInflater inflater;
        inflater = actionMode.getMenuInflater();
        inflater.inflate(com.automattic.simplenote.R.menu.bulk_edit, menu);
        com.automattic.simplenote.utils.DrawableUtils.tintMenuWithAttribute(getActivity(), menu, com.automattic.simplenote.R.attr.actionModeTextColor);
        mActionMode = actionMode;
        requireActivity().getWindow().setStatusBarColor(com.automattic.simplenote.utils.ThemeUtils.getColorFromAttribute(requireContext(), com.automattic.simplenote.R.attr.mainBackgroundColor));
        return true;
    }


    @java.lang.Override
    public boolean onPrepareActionMode(android.view.ActionMode mode, android.view.Menu menu) {
        return false;
    }


    @java.lang.Override
    public boolean onActionItemClicked(android.view.ActionMode mode, android.view.MenuItem item) {
        if (getListView().getCheckedItemIds().length > 0) {
            switch (item.getItemId()) {
                case com.automattic.simplenote.R.id.menu_link :
                    com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.INTERNOTE_LINK_COPIED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_LINK, "internote_link_copied_list");
                    com.automattic.simplenote.utils.BrowserUtils.copyToClipboard(requireContext(), getSelectedNoteLinks());
                    mode.finish();
                    break;
                case com.automattic.simplenote.R.id.menu_trash :
                    new com.automattic.simplenote.NoteListFragment.TrashNotesTask(this).executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
                    break;
                case com.automattic.simplenote.R.id.menu_pin :
                    new com.automattic.simplenote.NoteListFragment.PinNotesTask(this).executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
                    break;
            }
        }
        return false;
    }


    private java.lang.String getSelectedNoteLinks() {
        android.util.SparseBooleanArray checkedPositions;
        checkedPositions = getListView().getCheckedItemPositions();
        java.lang.StringBuilder links;
        links = new java.lang.StringBuilder();
        for (int i = 0; i < checkedPositions.size(); i++) {
            if (checkedPositions.valueAt(i)) {
                com.automattic.simplenote.models.Note note;
                note = mNotesAdapter.getItem(checkedPositions.keyAt(i));
                links.append(com.automattic.simplenote.utils.SimplenoteLinkify.getNoteLinkWithTitle(note.getTitle(), note.getSimperiumKey())).append("\n");
            }
        }
        return links.toString();
    }


    public java.util.List<java.lang.Integer> getSelectedNotesPositions() {
        android.util.SparseBooleanArray checkedPositions;
        checkedPositions = getListView().getCheckedItemPositions();
        java.util.ArrayList<java.lang.Integer> positions;
        positions = new java.util.ArrayList<>();
        for (int i = 0; i < checkedPositions.size(); i++) {
            if (checkedPositions.valueAt(i)) {
                switch(MUID_STATIC) {
                    // NoteListFragment_0_BinaryMutator
                    case 86: {
                        positions.add(checkedPositions.keyAt(i) + mList.getHeaderViewsCount());
                        break;
                    }
                    default: {
                    positions.add(checkedPositions.keyAt(i) - mList.getHeaderViewsCount());
                    break;
                }
            }
        }
    }
    return positions;
}


public void updateSelectionAfterTrashAction() {
    if (com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(getActivity())) {
        // Try to find the nearest note to the first deleted item
        java.util.List<java.lang.Integer> deletedNotesPositions;
        deletedNotesPositions = getSelectedNotesPositions();
        int firstDeletedNote;
        firstDeletedNote = deletedNotesPositions.get(0);
        int positionToSelect;
        positionToSelect = -1;
        // Loop through the notes below
        for (int i = firstDeletedNote + 1; i < mNotesAdapter.getCount(); i++) {
            if (!deletedNotesPositions.contains(i)) {
                positionToSelect = i;
                break;
            }
        }
        if (positionToSelect == (-1)) {
            // Loop through the above notes
            for (int i = firstDeletedNote - 1; i >= 0; i--) {
                if (!deletedNotesPositions.contains(i)) {
                    positionToSelect = i;
                    break;
                }
            }
        }
        if (positionToSelect != (-1)) {
            com.automattic.simplenote.models.Note noteToSelect;
            switch(MUID_STATIC) {
                // NoteListFragment_1_BinaryMutator
                case 186: {
                    noteToSelect = mNotesAdapter.getItem(positionToSelect - mList.getHeaderViewsCount());
                    break;
                }
                default: {
                noteToSelect = mNotesAdapter.getItem(positionToSelect + mList.getHeaderViewsCount());
                break;
            }
        }
        mCallbacks.onNoteSelected(noteToSelect.getSimperiumKey(), null, noteToSelect.isMarkdownEnabled(), noteToSelect.isPreviewEnabled());
        // As we will trigger a list refresh later, save the selectedNoteId
        mSelectedNoteId = noteToSelect.getSimperiumKey();
    } else {
        // The list of notes is empty
        ((com.automattic.simplenote.NotesActivity) (requireActivity())).showDetailPlaceholder();
    }
}
}


@java.lang.Override
public void onDestroyActionMode(android.view.ActionMode mode) {
mCallbacks.onActionModeDestroyed();
mActionMode = null;
if (getActivity() != null) {
    com.automattic.simplenote.NotesActivity notesActivity;
    notesActivity = ((com.automattic.simplenote.NotesActivity) (getActivity()));
    setActivateOnItemClick(com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(notesActivity));
    if (mSelectedNoteId == null) {
        notesActivity.showDetailPlaceholder();
    }
}
new android.os.Handler().postDelayed(new java.lang.Runnable() {
    @java.lang.Override
    public void run() {
        requireActivity().getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent, requireActivity().getTheme()));
    }

}, requireContext().getResources().getInteger(android.R.integer.config_longAnimTime));
}


@java.lang.Override
public void onItemCheckedStateChanged(android.view.ActionMode actionMode, int position, long id, boolean checked) {
int checkedCount;
checkedCount = getListView().getCheckedItemCount();
if (checkedCount == 0) {
    actionMode.setTitle("");
} else {
    actionMode.setTitle(getResources().getQuantityString(com.automattic.simplenote.R.plurals.selected_notes, checkedCount, checkedCount));
}
actionMode.invalidate();
}


@java.lang.Override
public void onCreate(android.os.Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
switch(MUID_STATIC) {
    // NoteListFragment_2_LengthyGUICreationOperatorMutator
    case 286: {
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
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Created (NoteListFragment)");
mBucketPreferences = ((com.automattic.simplenote.Simplenote) (requireActivity().getApplication())).getPreferencesBucket();
mBucketTag = ((com.automattic.simplenote.Simplenote) (requireActivity().getApplication())).getTagsBucket();
}


protected void getPrefs() {
mIsCondensedNoteList = com.automattic.simplenote.utils.PrefUtils.getBoolPref(getActivity(), com.automattic.simplenote.utils.PrefUtils.PREF_CONDENSED_LIST, false);
mTitleFontSize = com.automattic.simplenote.utils.PrefUtils.getFontSize(getActivity());
switch(MUID_STATIC) {
// NoteListFragment_3_BinaryMutator
case 386: {
    mPreviewFontSize = mTitleFontSize + 2;
    break;
}
default: {
mPreviewFontSize = mTitleFontSize - 2;
break;
}
}
}


@java.lang.Override
public android.view.View onCreateView(@androidx.annotation.NonNull
android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
return inflater.inflate(com.automattic.simplenote.R.layout.fragment_notes_list, container, false);
}


@java.lang.Override
public void onViewCreated(@androidx.annotation.NonNull
android.view.View view, android.os.Bundle savedInstanceState) {
super.onViewCreated(view, savedInstanceState);
com.automattic.simplenote.NotesActivity notesActivity;
notesActivity = ((com.automattic.simplenote.NotesActivity) (requireActivity()));
if (com.automattic.simplenote.NoteListFragment.ACTION_NEW_NOTE.equals(notesActivity.getIntent().getAction()) && (!notesActivity.userIsUnauthorized())) {
// if user tap on "app shortcut", create a new note
createNewNote("", "new_note_shortcut");
}
switch(MUID_STATIC) {
// NoteListFragment_4_InvalidViewFocusOperatorMutator
case 486: {
/**
* Inserted by Kadabra
*/
mRootView = view.findViewById(com.automattic.simplenote.R.id.list_root);
mRootView.requestFocus();
break;
}
// NoteListFragment_5_ViewComponentNotVisibleOperatorMutator
case 586: {
/**
* Inserted by Kadabra
*/
mRootView = view.findViewById(com.automattic.simplenote.R.id.list_root);
mRootView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mRootView = view.findViewById(com.automattic.simplenote.R.id.list_root);
break;
}
}
android.widget.LinearLayout emptyView;
switch(MUID_STATIC) {
// NoteListFragment_6_InvalidViewFocusOperatorMutator
case 686: {
/**
* Inserted by Kadabra
*/
emptyView = view.findViewById(android.R.id.empty);
emptyView.requestFocus();
break;
}
// NoteListFragment_7_ViewComponentNotVisibleOperatorMutator
case 786: {
/**
* Inserted by Kadabra
*/
emptyView = view.findViewById(android.R.id.empty);
emptyView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
emptyView = view.findViewById(android.R.id.empty);
break;
}
}
emptyView.setVisibility(android.view.View.GONE);
switch(MUID_STATIC) {
// NoteListFragment_8_InvalidViewFocusOperatorMutator
case 886: {
/**
* Inserted by Kadabra
*/
mEmptyViewButton = emptyView.findViewById(com.automattic.simplenote.R.id.button);
mEmptyViewButton.requestFocus();
break;
}
// NoteListFragment_9_ViewComponentNotVisibleOperatorMutator
case 986: {
/**
* Inserted by Kadabra
*/
mEmptyViewButton = emptyView.findViewById(com.automattic.simplenote.R.id.button);
mEmptyViewButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mEmptyViewButton = emptyView.findViewById(com.automattic.simplenote.R.id.button);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_10_InvalidViewFocusOperatorMutator
case 1086: {
/**
* Inserted by Kadabra
*/
mEmptyViewImage = emptyView.findViewById(com.automattic.simplenote.R.id.image);
mEmptyViewImage.requestFocus();
break;
}
// NoteListFragment_11_ViewComponentNotVisibleOperatorMutator
case 1186: {
/**
* Inserted by Kadabra
*/
mEmptyViewImage = emptyView.findViewById(com.automattic.simplenote.R.id.image);
mEmptyViewImage.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mEmptyViewImage = emptyView.findViewById(com.automattic.simplenote.R.id.image);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_12_InvalidViewFocusOperatorMutator
case 1286: {
/**
* Inserted by Kadabra
*/
mEmptyViewText = emptyView.findViewById(com.automattic.simplenote.R.id.text);
mEmptyViewText.requestFocus();
break;
}
// NoteListFragment_13_ViewComponentNotVisibleOperatorMutator
case 1386: {
/**
* Inserted by Kadabra
*/
mEmptyViewText = emptyView.findViewById(com.automattic.simplenote.R.id.text);
mEmptyViewText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mEmptyViewText = emptyView.findViewById(com.automattic.simplenote.R.id.text);
break;
}
}
setEmptyListImage(com.automattic.simplenote.R.drawable.ic_notes_24dp);
setEmptyListMessage(getString(com.automattic.simplenote.R.string.empty_notes_all));
switch(MUID_STATIC) {
// NoteListFragment_14_InvalidViewFocusOperatorMutator
case 1486: {
/**
* Inserted by Kadabra
*/
mDividerLine = view.findViewById(com.automattic.simplenote.R.id.divider_line);
mDividerLine.requestFocus();
break;
}
// NoteListFragment_15_ViewComponentNotVisibleOperatorMutator
case 1586: {
/**
* Inserted by Kadabra
*/
mDividerLine = view.findViewById(com.automattic.simplenote.R.id.divider_line);
mDividerLine.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mDividerLine = view.findViewById(com.automattic.simplenote.R.id.divider_line);
break;
}
}
if (com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(notesActivity)) {
setActivateOnItemClick(true);
mDividerLine.setVisibility(android.view.View.VISIBLE);
}
switch(MUID_STATIC) {
// NoteListFragment_16_InvalidViewFocusOperatorMutator
case 1686: {
/**
* Inserted by Kadabra
*/
mFloatingActionButton = view.findViewById(com.automattic.simplenote.R.id.fab_button);
mFloatingActionButton.requestFocus();
break;
}
// NoteListFragment_17_ViewComponentNotVisibleOperatorMutator
case 1786: {
/**
* Inserted by Kadabra
*/
mFloatingActionButton = view.findViewById(com.automattic.simplenote.R.id.fab_button);
mFloatingActionButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mFloatingActionButton = view.findViewById(com.automattic.simplenote.R.id.fab_button);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_18_BuggyGUIListenerOperatorMutator
case 1886: {
mFloatingActionButton.setOnClickListener(null);
break;
}
default: {
mFloatingActionButton.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// NoteListFragment_19_LengthyGUIListenerOperatorMutator
case 1986: {
/**
* Inserted by Kadabra
*/
createNewNote("", "action_bar_button");
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
createNewNote("", "action_bar_button");
break;
}
}
}

});
break;
}
}
mFloatingActionButton.setOnLongClickListener(new android.view.View.OnLongClickListener() {
@java.lang.Override
public boolean onLongClick(android.view.View v) {
if (v.isHapticFeedbackEnabled()) {
v.performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS);
}
android.widget.Toast.makeText(getContext(), requireContext().getString(com.automattic.simplenote.R.string.new_note), android.widget.Toast.LENGTH_SHORT).show();
return true;
}

});
switch(MUID_STATIC) {
// NoteListFragment_20_InvalidViewFocusOperatorMutator
case 2086: {
/**
* Inserted by Kadabra
*/
mSuggestionLayout = view.findViewById(com.automattic.simplenote.R.id.suggestion_layout);
mSuggestionLayout.requestFocus();
break;
}
// NoteListFragment_21_ViewComponentNotVisibleOperatorMutator
case 2186: {
/**
* Inserted by Kadabra
*/
mSuggestionLayout = view.findViewById(com.automattic.simplenote.R.id.suggestion_layout);
mSuggestionLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mSuggestionLayout = view.findViewById(com.automattic.simplenote.R.id.suggestion_layout);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_22_InvalidViewFocusOperatorMutator
case 2286: {
/**
* Inserted by Kadabra
*/
mSuggestionList = view.findViewById(com.automattic.simplenote.R.id.suggestion_list);
mSuggestionList.requestFocus();
break;
}
// NoteListFragment_23_ViewComponentNotVisibleOperatorMutator
case 2386: {
/**
* Inserted by Kadabra
*/
mSuggestionList = view.findViewById(com.automattic.simplenote.R.id.suggestion_list);
mSuggestionList.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mSuggestionList = view.findViewById(com.automattic.simplenote.R.id.suggestion_list);
break;
}
}
mSuggestionAdapter = new com.automattic.simplenote.NoteListFragment.SuggestionAdapter(new java.util.ArrayList<com.automattic.simplenote.models.Suggestion>());
mSuggestionList.setAdapter(mSuggestionAdapter);
mSuggestionList.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(requireContext()));
switch(MUID_STATIC) {
// NoteListFragment_24_InvalidViewFocusOperatorMutator
case 2486: {
/**
* Inserted by Kadabra
*/
mList = view.findViewById(android.R.id.list);
mList.requestFocus();
break;
}
// NoteListFragment_25_ViewComponentNotVisibleOperatorMutator
case 2586: {
/**
* Inserted by Kadabra
*/
mList = view.findViewById(android.R.id.list);
mList.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mList = view.findViewById(android.R.id.list);
break;
}
}
mNotesAdapter = new com.automattic.simplenote.NoteListFragment.NotesCursorAdapter(requireActivity().getBaseContext(), null, 0);
setListAdapter(mNotesAdapter);
getListView().setOnItemLongClickListener(this);
getListView().setMultiChoiceModeListener(this);
}


public void showListPadding(boolean show) {
mList.setPadding(mList.getPaddingLeft(), mList.getPaddingTop(), mList.getPaddingRight(), show ? ((int) (getResources().getDimension(com.automattic.simplenote.R.dimen.note_list_item_padding_bottom_button))) : 0);
}


public void createNewNote(java.lang.String title, java.lang.String label) {
if (!isAdded()) {
return;
}
addNote(title);
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.LIST_NOTE_CREATED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, label);
}


@java.lang.Override
public void onAttach(@androidx.annotation.NonNull
android.content.Context activity) {
super.onAttach(activity);
// Activities containing this fragment must implement its callbacks.
if (!(activity instanceof com.automattic.simplenote.NoteListFragment.Callbacks)) {
throw new java.lang.IllegalStateException("Activity must implement fragment's callbacks.");
}
mCallbacks = ((com.automattic.simplenote.NoteListFragment.Callbacks) (activity));
}


@java.lang.Override
public void onResume() {
super.onResume();
getPrefs();
if (mIsSearching) {
refreshListForSearch();
} else {
refreshList();
}
mBucketPreferences.start();
mBucketPreferences.addOnDeleteObjectListener(this);
mBucketPreferences.addOnNetworkChangeListener(this);
mBucketPreferences.addOnSaveObjectListener(this);
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Added preference bucket listener (NoteListFragment)");
}


@java.lang.Override
public void onPause() {
super.onPause();
mBucketPreferences.removeOnDeleteObjectListener(this);
mBucketPreferences.removeOnNetworkChangeListener(this);
mBucketPreferences.removeOnSaveObjectListener(this);
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Removed preference bucket listener (NoteListFragment)");
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Paused (NoteListFragment)");
}


@java.lang.Override
public void onDetach() {
super.onDetach();
// Reset the active callbacks interface to the dummy implementation.
mCallbacks = com.automattic.simplenote.NoteListFragment.sCallbacks;
}


public void setEmptyListButton(java.lang.String message) {
if (mEmptyViewButton != null) {
if (!message.isEmpty()) {
mEmptyViewButton.setVisibility(android.view.View.VISIBLE);
mEmptyViewButton.setText(message);
} else {
mEmptyViewButton.setVisibility(android.view.View.GONE);
}
}
}


public void setEmptyListImage(@androidx.annotation.DrawableRes
int image) {
if (mEmptyViewImage != null) {
if (image != (-1)) {
mEmptyViewImage.setVisibility(android.view.View.VISIBLE);
mEmptyViewImage.setImageResource(image);
} else {
mEmptyViewImage.setVisibility(android.view.View.GONE);
}
}
}


public void setEmptyListMessage(java.lang.String message) {
if ((mEmptyViewText != null) && (message != null)) {
mEmptyViewText.setText(message);
}
}


@java.lang.Override
public void onListItemClick(@androidx.annotation.NonNull
android.widget.ListView listView, @androidx.annotation.NonNull
android.view.View view, int position, long id) {
if (!isAdded())
return;

super.onListItemClick(listView, view, position, id);
com.automattic.simplenote.NoteListFragment.NoteViewHolder holder;
holder = ((com.automattic.simplenote.NoteListFragment.NoteViewHolder) (view.getTag()));
java.lang.String noteID;
noteID = holder.getNoteId();
if (noteID != null) {
com.automattic.simplenote.models.Note note;
note = mNotesAdapter.getItem(position);
mCallbacks.onNoteSelected(noteID, holder.mMatchOffsets, note.isMarkdownEnabled(), note.isPreviewEnabled());
}
mActivatedPosition = position;
}


/**
 * Selects first row in the list if available
 */
public void selectFirstNote() {
if (mNotesAdapter.getCount() > 0) {
com.automattic.simplenote.models.Note selectedNote;
selectedNote = mNotesAdapter.getItem(mList.getHeaderViewsCount());
mCallbacks.onNoteSelected(selectedNote.getSimperiumKey(), null, selectedNote.isMarkdownEnabled(), selectedNote.isPreviewEnabled());
}
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
if (mActivatedPosition != android.widget.ListView.INVALID_POSITION) {
// Serialize and persist the activated item position.
outState.putInt(com.automattic.simplenote.NoteListFragment.STATE_ACTIVATED_POSITION, mActivatedPosition);
}
}


public android.view.View getRootView() {
return mRootView;
}


/**
 * Turns on activate-on-click mode. When this mode is on, list items will be
 * given the 'activated' state when touched.
 */
public void setActivateOnItemClick(boolean activateOnItemClick) {
// When setting CHOICE_MODE_SINGLE, ListView will automatically
// give items the 'activated' state when touched.
getListView().setChoiceMode(activateOnItemClick ? android.widget.ListView.CHOICE_MODE_SINGLE : android.widget.ListView.CHOICE_MODE_NONE);
}


public void setActivatedPosition(int position) {
if (getListView() != null) {
if (position == android.widget.ListView.INVALID_POSITION) {
getListView().setItemChecked(mActivatedPosition, false);
} else {
getListView().setItemChecked(position, true);
}
mActivatedPosition = position;
}
}


public void setDividerVisible(boolean visible) {
mDividerLine.setVisibility(visible ? android.view.View.VISIBLE : android.view.View.GONE);
}


public void setFloatingActionButtonVisible(boolean visible) {
if (mFloatingActionButton == null)
return;

if (visible) {
mFloatingActionButton.show();
} else {
mFloatingActionButton.hide();
}
}


public void refreshList() {
refreshList(false);
}


public void refreshList(boolean fromNav) {
if ((mRefreshListTask != null) && (mRefreshListTask.getStatus() != android.os.AsyncTask.Status.FINISHED)) {
mRefreshListTask.cancel(true);
}
mRefreshListTask = new com.automattic.simplenote.NoteListFragment.RefreshListTask(this);
mRefreshListTask.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, fromNav);
com.automattic.simplenote.utils.WidgetUtils.updateNoteWidgets(requireActivity().getApplicationContext());
}


private void refreshListForSearch() {
if ((mRefreshListForSearchTask != null) && (mRefreshListForSearchTask.getStatus() != android.os.AsyncTask.Status.FINISHED)) {
mRefreshListForSearchTask.cancel(true);
}
mRefreshListForSearchTask = new com.automattic.simplenote.NoteListFragment.RefreshListForSearchTask(this);
mRefreshListForSearchTask.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
}


public void refreshListFromNavSelect() {
refreshList(true);
}


public com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> queryNotes() {
if (!isAdded())
return null;

com.automattic.simplenote.NotesActivity notesActivity;
notesActivity = ((com.automattic.simplenote.NotesActivity) (requireActivity()));
com.simperium.client.Query<com.automattic.simplenote.models.Note> query;
query = notesActivity.getSelectedTag().query();
java.lang.String searchString;
searchString = mSearchString;
if (hasSearchQuery()) {
searchString = queryTags(query, mSearchString);
}
if (!android.text.TextUtils.isEmpty(searchString)) {
query.where(new com.simperium.client.Query.FullTextMatch(new com.automattic.simplenote.utils.SearchTokenizer(searchString)));
query.include(new com.simperium.client.Query.FullTextOffsets("match_offsets"));
query.include(new com.simperium.client.Query.FullTextSnippet(com.automattic.simplenote.models.Note.MATCHED_TITLE_INDEX_NAME, com.automattic.simplenote.models.Note.TITLE_INDEX_NAME));
query.include(new com.simperium.client.Query.FullTextSnippet(com.automattic.simplenote.models.Note.MATCHED_CONTENT_INDEX_NAME, com.automattic.simplenote.models.Note.CONTENT_PROPERTY));
query.include(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME, com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME);
} else {
query.include(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME, com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME);
}
query.include(com.automattic.simplenote.models.Note.PINNED_INDEX_NAME);
com.automattic.simplenote.utils.PrefUtils.sortNoteQuery(query, requireContext(), true);
return query.execute();
}


private com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> queryNotesForSearch() {
if (!isAdded()) {
return null;
}
com.simperium.client.Query<com.automattic.simplenote.models.Note> query;
query = com.automattic.simplenote.models.Note.all(((com.automattic.simplenote.Simplenote) (requireActivity().getApplication())).getNotesBucket());
java.lang.String searchString;
searchString = mSearchString;
if (hasSearchQuery()) {
searchString = queryTags(query, mSearchString);
}
if (!android.text.TextUtils.isEmpty(searchString)) {
query.where(new com.simperium.client.Query.FullTextMatch(new com.automattic.simplenote.utils.SearchTokenizer(searchString)));
query.include(new com.simperium.client.Query.FullTextOffsets("match_offsets"));
query.include(new com.simperium.client.Query.FullTextSnippet(com.automattic.simplenote.models.Note.MATCHED_TITLE_INDEX_NAME, com.automattic.simplenote.models.Note.TITLE_INDEX_NAME));
query.include(new com.simperium.client.Query.FullTextSnippet(com.automattic.simplenote.models.Note.MATCHED_CONTENT_INDEX_NAME, com.automattic.simplenote.models.Note.CONTENT_PROPERTY));
query.include(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME, com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME);
} else {
query.include(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME, com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME);
}
com.automattic.simplenote.utils.PrefUtils.sortNoteQuery(query, requireContext(), false);
return query.execute();
}


private java.lang.String queryTags(com.simperium.client.Query<com.automattic.simplenote.models.Note> query, java.lang.String searchString) {
java.util.regex.Pattern pattern;
pattern = java.util.regex.Pattern.compile(com.automattic.simplenote.NoteListFragment.TAG_PREFIX + "(.*?)( |$)");
java.util.regex.Matcher matcher;
matcher = pattern.matcher(searchString);
while (matcher.find()) {
query.where(com.automattic.simplenote.models.Note.TAGS_PROPERTY, com.simperium.client.Query.ComparisonType.LIKE, matcher.group(1));
} 
return matcher.replaceAll("");
}


public void addNote(java.lang.String title) {
// Prevents jarring 'New note...' from showing in the list view when creating a new note
com.automattic.simplenote.NotesActivity notesActivity;
notesActivity = ((com.automattic.simplenote.NotesActivity) (requireActivity()));
if (!com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(notesActivity)) {
notesActivity.stopListeningToNotesBucket();
}
// Create & save new note
com.automattic.simplenote.Simplenote simplenote;
simplenote = ((com.automattic.simplenote.Simplenote) (requireActivity().getApplication()));
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket;
notesBucket = simplenote.getNotesBucket();
final com.automattic.simplenote.models.Note note;
note = notesBucket.newObject();
note.setContent(title);
note.setCreationDate(java.util.Calendar.getInstance());
note.setModificationDate(note.getCreationDate());
note.setMarkdownEnabled(com.automattic.simplenote.utils.PrefUtils.getBoolPref(getActivity(), com.automattic.simplenote.utils.PrefUtils.PREF_MARKDOWN_ENABLED, false));
if ((notesActivity.getSelectedTag() != null) && (notesActivity.getSelectedTag().name != null)) {
java.lang.String tagName;
tagName = notesActivity.getSelectedTag().name;
if (((!tagName.equals(getString(com.automattic.simplenote.R.string.all_notes))) && (!tagName.equals(getString(com.automattic.simplenote.R.string.trash)))) && (!tagName.equals(getString(com.automattic.simplenote.R.string.untagged_notes)))) {
note.setTagString(tagName);
}
}
note.save();
if (com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(getActivity())) {
// Hack: Simperium saves async so we add a small delay to ensure the new note is truly
// saved before proceeding.
new android.os.Handler().postDelayed(new java.lang.Runnable() {
@java.lang.Override
public void run() {
mCallbacks.onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
}

}, 50);
} else {
android.os.Bundle arguments;
arguments = new android.os.Bundle();
arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_NEW_NOTE, true);
arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
android.content.Intent editNoteIntent;
switch(MUID_STATIC) {
// NoteListFragment_26_NullIntentOperatorMutator
case 2686: {
editNoteIntent = null;
break;
}
// NoteListFragment_27_InvalidKeyIntentOperatorMutator
case 2786: {
editNoteIntent = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, com.automattic.simplenote.NoteEditorActivity.class);
break;
}
// NoteListFragment_28_RandomActionIntentDefinitionOperatorMutator
case 2886: {
editNoteIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
editNoteIntent = new android.content.Intent(getActivity(), com.automattic.simplenote.NoteEditorActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_29_RandomActionIntentDefinitionOperatorMutator
case 2986: {
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
editNoteIntent.putExtras(arguments);
break;
}
}
requireActivity().startActivityForResult(editNoteIntent, com.automattic.simplenote.Simplenote.INTENT_EDIT_NOTE);
}
}


public void setNoteSelected(java.lang.String selectedNoteID) {
// Loop through notes and set note selected if found
// noinspection unchecked
com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> cursor;
cursor = ((com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note>) (mNotesAdapter.getCursor()));
if (cursor != null) {
for (int i = 0; i < cursor.getCount(); i++) {
cursor.moveToPosition(i);
java.lang.String noteKey;
noteKey = cursor.getSimperiumKey();
if ((noteKey != null) && noteKey.equals(selectedNoteID)) {
switch(MUID_STATIC) {
// NoteListFragment_30_BinaryMutator
case 3086: {
setActivatedPosition(i - mList.getHeaderViewsCount());
break;
}
default: {
setActivatedPosition(i + mList.getHeaderViewsCount());
break;
}
}
return;
}
}
}
// Didn't find the note, let's try again after the cursor updates (see RefreshListTask)
mSelectedNoteId = selectedNoteID;
}


public void searchNotes(java.lang.String searchString, boolean isSubmit) {
mIsSearching = true;
mSuggestionLayout.setVisibility(android.view.View.VISIBLE);
if (!searchString.equals(mSearchString)) {
mSearchString = searchString;
}
if (searchString.isEmpty()) {
getSearchItems();
} else {
getTagSuggestions(searchString);
}
if (isSubmit) {
mSuggestionLayout.setVisibility(android.view.View.GONE);
refreshListForSearch();
}
}


/**
 * Clear search and load all notes
 */
public void clearSearch() {
mIsSearching = false;
mSuggestionLayout.setVisibility(android.view.View.GONE);
refreshList();
if ((mSearchString != null) && (!mSearchString.equals(""))) {
mSearchString = null;
refreshList();
}
}


public boolean hasSearchQuery() {
return (mSearchString != null) && (!mSearchString.equals(""));
}


public void addSearchItem(java.lang.String item, int index) {
com.automattic.simplenote.models.Preferences preferences;
preferences = getPreferences();
if (preferences != null) {
java.util.List<java.lang.String> recents;
recents = preferences.getRecentSearches();
recents.remove(item);
recents.add(index, item);
// Trim recent searches to MAX_RECENT_SEARCHES (currently 5) if size is greater than MAX_RECENT_SEARCHES.
preferences.setRecentSearches(recents.subList(0, recents.size() > com.automattic.simplenote.models.Preferences.MAX_RECENT_SEARCHES ? com.automattic.simplenote.models.Preferences.MAX_RECENT_SEARCHES : recents.size()));
preferences.save();
} else {
android.util.Log.e("addSearchItem", "Could not get preferences entity");
}
}


private void deleteSearchItem(java.lang.String item) {
com.automattic.simplenote.models.Preferences preferences;
preferences = getPreferences();
if (preferences != null) {
java.util.List<java.lang.String> recents;
recents = preferences.getRecentSearches();
mDeletedItemIndex = recents.indexOf(item);
recents.remove(item);
preferences.setRecentSearches(recents);
preferences.save();
} else {
android.util.Log.e("deleteSearchItem", "Could not get preferences entity");
}
}


private com.automattic.simplenote.models.Preferences getPreferences() {
try {
return mBucketPreferences.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY);
} catch (com.simperium.client.BucketObjectMissingException exception) {
try {
com.automattic.simplenote.models.Preferences preferences;
preferences = mBucketPreferences.newObject(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY);
preferences.save();
return preferences;
} catch (com.simperium.client.BucketObjectNameInvalid invalid) {
android.util.Log.e("getPreferences", "Could not create preferences entity", invalid);
return null;
}
}
}


private void getSearchItems() {
com.automattic.simplenote.models.Preferences preferences;
preferences = getPreferences();
if (preferences != null) {
java.util.ArrayList<com.automattic.simplenote.models.Suggestion> suggestions;
suggestions = new java.util.ArrayList<>();
for (java.lang.String recent : preferences.getRecentSearches()) {
suggestions.add(new com.automattic.simplenote.models.Suggestion(recent, com.automattic.simplenote.models.Suggestion.Type.HISTORY));
}
mSuggestionAdapter.updateItems(suggestions);
} else {
android.util.Log.e("getSearchItems", "Could not get preferences entity");
}
}


private void getTagSuggestions(java.lang.String query) {
java.util.ArrayList<com.automattic.simplenote.models.Suggestion> suggestions;
suggestions = new java.util.ArrayList<>();
suggestions.add(new com.automattic.simplenote.models.Suggestion(query, com.automattic.simplenote.models.Suggestion.Type.QUERY));
com.simperium.client.Query<com.automattic.simplenote.models.Tag> tags;
tags = com.automattic.simplenote.models.Tag.all(mBucketTag).reorder().order(com.automattic.simplenote.models.Tag.NOTE_COUNT_INDEX_NAME, com.simperium.client.Query.SortType.DESCENDING);
if (!query.endsWith(com.automattic.simplenote.NoteListFragment.TAG_PREFIX)) {
tags.where(com.automattic.simplenote.models.Tag.NAME_PROPERTY, com.simperium.client.Query.ComparisonType.LIKE, ("%" + query) + "%");
}
try (com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Tag> cursor = tags.execute()) {
while (cursor.moveToNext()) {
suggestions.add(new com.automattic.simplenote.models.Suggestion(cursor.getObject().getName(), com.automattic.simplenote.models.Suggestion.Type.TAG));
} 
}
mSuggestionAdapter = new com.automattic.simplenote.NoteListFragment.SuggestionAdapter(suggestions);
mSuggestionList.setAdapter(mSuggestionAdapter);
}


@java.lang.Override
public void onLocalQueueChange(com.simperium.client.Bucket<com.automattic.simplenote.models.Preferences> bucket, java.util.Set<java.lang.String> queuedObjects) {
}


@java.lang.Override
public void onSyncObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Preferences> bucket, java.lang.String key) {
}


/**
 * A callback interface that all activities containing this fragment must
 * implement. This mechanism allows activities to be notified of item
 * selections.
 */
public interface Callbacks {
/**
 * Callback for when action mode is created.
 */
void onActionModeCreated();


/**
 * Callback for when action mode is destroyed.
 */
void onActionModeDestroyed();


/**
 * Callback for when a note has been selected.
 */
void onNoteSelected(java.lang.String noteID, java.lang.String matchOffsets, boolean isMarkdownEnabled, boolean isPreviewEnabled);

}

// view holder for NotesCursorAdapter
private static class NoteViewHolder {
private android.widget.ImageView mHasCollaborators;

private android.widget.ImageView mPinned;

private android.widget.ImageView mPublished;

private android.widget.TextView mContent;

private android.widget.TextView mDate;

private android.widget.TextView mTitle;

private java.lang.String mMatchOffsets;

private java.lang.String mNoteId;

private android.view.View mStatus;

public java.lang.String getNoteId() {
return mNoteId;
}


public void setNoteId(java.lang.String noteId) {
mNoteId = noteId;
}

}

public class NotesCursorAdapter extends android.widget.CursorAdapter {
private com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> mCursor;

private com.automattic.simplenote.utils.SearchSnippetFormatter.SpanFactory mSnippetHighlighter = new com.automattic.simplenote.utils.TextHighlighter(requireActivity(), com.automattic.simplenote.R.attr.listSearchHighlightForegroundColor, com.automattic.simplenote.R.attr.listSearchHighlightBackgroundColor);

public NotesCursorAdapter(android.content.Context context, com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> c, int flags) {
super(context, c, flags);
mCursor = c;
}


public void changeCursor(com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> cursor) {
mCursor = cursor;
super.changeCursor(cursor);
}


@java.lang.Override
public com.automattic.simplenote.models.Note getItem(int position) {
switch(MUID_STATIC) {
// NoteListFragment_31_BinaryMutator
case 3186: {
mCursor.moveToPosition(position + mList.getHeaderViewsCount());
break;
}
default: {
mCursor.moveToPosition(position - mList.getHeaderViewsCount());
break;
}
}
return mCursor.getObject();
}


/* nbradbury - implemented "holder pattern" to boost performance with large note lists */
@java.lang.Override
@android.annotation.SuppressLint("Range")
public android.view.View getView(final int position, android.view.View view, android.view.ViewGroup parent) {
final com.automattic.simplenote.NoteListFragment.NoteViewHolder holder;
if (view == null) {
view = android.view.View.inflate(requireActivity().getBaseContext(), com.automattic.simplenote.R.layout.note_list_row, null);
holder = new com.automattic.simplenote.NoteListFragment.NoteViewHolder();
switch(MUID_STATIC) {
// NoteListFragment_32_InvalidViewFocusOperatorMutator
case 3286: {
/**
* Inserted by Kadabra
*/
holder.mTitle = view.findViewById(com.automattic.simplenote.R.id.note_title);
holder.mTitle.requestFocus();
break;
}
// NoteListFragment_33_ViewComponentNotVisibleOperatorMutator
case 3386: {
/**
* Inserted by Kadabra
*/
holder.mTitle = view.findViewById(com.automattic.simplenote.R.id.note_title);
holder.mTitle.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
holder.mTitle = view.findViewById(com.automattic.simplenote.R.id.note_title);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_34_InvalidViewFocusOperatorMutator
case 3486: {
/**
* Inserted by Kadabra
*/
holder.mContent = view.findViewById(com.automattic.simplenote.R.id.note_content);
holder.mContent.requestFocus();
break;
}
// NoteListFragment_35_ViewComponentNotVisibleOperatorMutator
case 3586: {
/**
* Inserted by Kadabra
*/
holder.mContent = view.findViewById(com.automattic.simplenote.R.id.note_content);
holder.mContent.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
holder.mContent = view.findViewById(com.automattic.simplenote.R.id.note_content);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_36_InvalidViewFocusOperatorMutator
case 3686: {
/**
* Inserted by Kadabra
*/
holder.mDate = view.findViewById(com.automattic.simplenote.R.id.note_date);
holder.mDate.requestFocus();
break;
}
// NoteListFragment_37_ViewComponentNotVisibleOperatorMutator
case 3786: {
/**
* Inserted by Kadabra
*/
holder.mDate = view.findViewById(com.automattic.simplenote.R.id.note_date);
holder.mDate.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
holder.mDate = view.findViewById(com.automattic.simplenote.R.id.note_date);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_38_InvalidViewFocusOperatorMutator
case 3886: {
/**
* Inserted by Kadabra
*/
holder.mHasCollaborators = view.findViewById(com.automattic.simplenote.R.id.note_shared);
holder.mHasCollaborators.requestFocus();
break;
}
// NoteListFragment_39_ViewComponentNotVisibleOperatorMutator
case 3986: {
/**
* Inserted by Kadabra
*/
holder.mHasCollaborators = view.findViewById(com.automattic.simplenote.R.id.note_shared);
holder.mHasCollaborators.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
holder.mHasCollaborators = view.findViewById(com.automattic.simplenote.R.id.note_shared);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_40_InvalidViewFocusOperatorMutator
case 4086: {
/**
* Inserted by Kadabra
*/
holder.mPinned = view.findViewById(com.automattic.simplenote.R.id.note_pinned);
holder.mPinned.requestFocus();
break;
}
// NoteListFragment_41_ViewComponentNotVisibleOperatorMutator
case 4186: {
/**
* Inserted by Kadabra
*/
holder.mPinned = view.findViewById(com.automattic.simplenote.R.id.note_pinned);
holder.mPinned.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
holder.mPinned = view.findViewById(com.automattic.simplenote.R.id.note_pinned);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_42_InvalidViewFocusOperatorMutator
case 4286: {
/**
* Inserted by Kadabra
*/
holder.mPublished = view.findViewById(com.automattic.simplenote.R.id.note_published);
holder.mPublished.requestFocus();
break;
}
// NoteListFragment_43_ViewComponentNotVisibleOperatorMutator
case 4386: {
/**
* Inserted by Kadabra
*/
holder.mPublished = view.findViewById(com.automattic.simplenote.R.id.note_published);
holder.mPublished.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
holder.mPublished = view.findViewById(com.automattic.simplenote.R.id.note_published);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_44_InvalidViewFocusOperatorMutator
case 4486: {
/**
* Inserted by Kadabra
*/
holder.mStatus = view.findViewById(com.automattic.simplenote.R.id.note_status);
holder.mStatus.requestFocus();
break;
}
// NoteListFragment_45_ViewComponentNotVisibleOperatorMutator
case 4586: {
/**
* Inserted by Kadabra
*/
holder.mStatus = view.findViewById(com.automattic.simplenote.R.id.note_status);
holder.mStatus.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
holder.mStatus = view.findViewById(com.automattic.simplenote.R.id.note_status);
break;
}
}
view.setTag(holder);
} else {
holder = ((com.automattic.simplenote.NoteListFragment.NoteViewHolder) (view.getTag()));
}
if (holder.mTitle.getTextSize() != mTitleFontSize) {
holder.mTitle.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, mTitleFontSize);
holder.mContent.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, mPreviewFontSize);
holder.mDate.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, mPreviewFontSize);
}
if (position == getListView().getCheckedItemPosition()) {
view.setActivated(true);
} else {
view.setActivated(false);
}
// for performance reasons we are going to get indexed values
// from the cursor instead of instantiating the entire bucket object
holder.mContent.setVisibility(mIsCondensedNoteList ? android.view.View.GONE : android.view.View.VISIBLE);
mCursor.moveToPosition(position);
holder.setNoteId(mCursor.getSimperiumKey());
java.util.Calendar date;
date = getDateByPreference(mCursor.getObject());
holder.mDate.setText(date != null ? com.automattic.simplenote.utils.DateTimeUtils.getDateTextNumeric(date) : "");
holder.mDate.setVisibility(mIsSearching && (date != null) ? android.view.View.VISIBLE : android.view.View.GONE);
boolean hasCollaborators;
hasCollaborators = mCursor.getObject().hasCollaborators();
holder.mHasCollaborators.setVisibility((!hasCollaborators) || mIsSearching ? android.view.View.GONE : android.view.View.VISIBLE);
boolean isPinned;
isPinned = mCursor.getObject().isPinned();
holder.mPinned.setVisibility((!isPinned) || mIsSearching ? android.view.View.GONE : android.view.View.VISIBLE);
boolean isPublished;
isPublished = !mCursor.getObject().getPublishedUrl().isEmpty();
holder.mPublished.setVisibility((!isPublished) || mIsSearching ? android.view.View.GONE : android.view.View.VISIBLE);
boolean showIcons;
showIcons = (isPinned || isPublished) || hasCollaborators;
boolean showDate;
showDate = mIsSearching && (date != null);
holder.mStatus.setVisibility(showIcons || showDate ? android.view.View.VISIBLE : android.view.View.GONE);
java.lang.String title;
title = mCursor.getString(mCursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME));
if (android.text.TextUtils.isEmpty(title)) {
android.text.SpannableString newNoteString;
newNoteString = new android.text.SpannableString(getString(com.automattic.simplenote.R.string.new_note_list));
newNoteString.setSpan(new android.text.style.TextAppearanceSpan(getActivity(), com.automattic.simplenote.R.style.UntitledNoteAppearance), 0, newNoteString.length(), android.text.SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
newNoteString.setSpan(new android.text.style.AbsoluteSizeSpan(mTitleFontSize, true), 0, newNoteString.length(), android.text.SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
holder.mTitle.setText(newNoteString);
} else {
android.text.SpannableStringBuilder titleChecklistString;
titleChecklistString = new android.text.SpannableStringBuilder(title);
titleChecklistString = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistSpansForRegexAndColor(getContext(), titleChecklistString, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX, com.automattic.simplenote.utils.ThemeUtils.getThemeTextColorId(getContext()), true)));
holder.mTitle.setText(titleChecklistString);
}
holder.mMatchOffsets = null;
int matchOffsetsIndex;
matchOffsetsIndex = -1;
try {
matchOffsetsIndex = mCursor.getColumnIndexOrThrow("match_offsets");
} catch (java.lang.IllegalArgumentException ignored) {
}
if (hasSearchQuery() && (matchOffsetsIndex != (-1))) {
title = mCursor.getString(mCursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.MATCHED_TITLE_INDEX_NAME));
java.lang.String snippet;
snippet = mCursor.getString(mCursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.MATCHED_CONTENT_INDEX_NAME));
holder.mMatchOffsets = mCursor.getString(matchOffsetsIndex);
try {
holder.mContent.setText(com.automattic.simplenote.utils.SearchSnippetFormatter.formatString(getContext(), snippet, mSnippetHighlighter, com.automattic.simplenote.R.color.text_title_disabled));
holder.mTitle.setText(com.automattic.simplenote.utils.SearchSnippetFormatter.formatString(getContext(), title, mSnippetHighlighter, com.automattic.simplenote.utils.ThemeUtils.getThemeTextColorId(getContext())));
} catch (java.lang.NullPointerException e) {
title = com.automattic.simplenote.utils.StrUtils.notNullStr(mCursor.getString(mCursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME)));
holder.mTitle.setText(title);
java.lang.String matchedContentPreview;
matchedContentPreview = com.automattic.simplenote.utils.StrUtils.notNullStr(mCursor.getString(mCursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME)));
holder.mContent.setText(matchedContentPreview);
}
} else if (!mIsCondensedNoteList) {
java.lang.String contentPreview;
contentPreview = mCursor.getString(mCursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME));
if (((title == null) || title.equals(contentPreview)) || title.equals(getString(com.automattic.simplenote.R.string.new_note_list))) {
holder.mContent.setVisibility(android.view.View.GONE);
} else {
holder.mContent.setText(contentPreview);
android.text.SpannableStringBuilder checklistString;
checklistString = new android.text.SpannableStringBuilder(contentPreview);
checklistString = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistSpansForRegexAndColor(getContext(), checklistString, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX, com.automattic.simplenote.R.color.text_title_disabled, true)));
holder.mContent.setText(checklistString);
}
}
// Add mouse right click support for showing a popup menu
view.setOnTouchListener(new android.view.View.OnTouchListener() {
@android.annotation.SuppressLint("ClickableViewAccessibility")
@java.lang.Override
public boolean onTouch(android.view.View view, android.view.MotionEvent event) {
if ((event.getButtonState() == android.view.MotionEvent.BUTTON_SECONDARY) && (event.getAction() == android.view.MotionEvent.ACTION_DOWN)) {
showPopupMenuAtPosition(view, position);
return true;
}
return false;
}

});
return view;
}


@java.lang.Override
public android.view.View newView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup viewGroup) {
return null;
}


@java.lang.Override
public void bindView(android.view.View view, android.content.Context context, android.database.Cursor cursor) {
}

}

@java.lang.Override
public void onBeforeUpdateObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Preferences> bucket, com.automattic.simplenote.models.Preferences object) {
}


@java.lang.Override
public void onDeleteObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Preferences> bucket, com.automattic.simplenote.models.Preferences object) {
if (isAdded()) {
requireActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
getSearchItems();
}

});
}
}


@java.lang.Override
public void onNetworkChange(com.simperium.client.Bucket<com.automattic.simplenote.models.Preferences> bucket, com.simperium.client.Bucket.ChangeType type, java.lang.String key) {
if (isAdded()) {
requireActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
getSearchItems();
}

});
}
}


@java.lang.Override
public void onSaveObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Preferences> bucket, com.automattic.simplenote.models.Preferences object) {
if (isAdded()) {
requireActivity().runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
getSearchItems();
}

});
}
}


private class SuggestionAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.automattic.simplenote.NoteListFragment.SuggestionAdapter.ViewHolder> {
private final java.util.List<com.automattic.simplenote.models.Suggestion> mSuggestions;

private SuggestionAdapter(java.util.List<com.automattic.simplenote.models.Suggestion> suggestions) {
mSuggestions = new java.util.ArrayList<>(suggestions);
}


@java.lang.Override
public int getItemCount() {
return mSuggestions.size();
}


@java.lang.Override
public int getItemViewType(int position) {
return mSuggestions.get(position).getType();
}


@android.annotation.SuppressLint("SetTextI18n")
@java.lang.Override
public void onBindViewHolder(@androidx.annotation.NonNull
final com.automattic.simplenote.NoteListFragment.SuggestionAdapter.ViewHolder holder, final int position) {
switch (holder.mViewType) {
case com.automattic.simplenote.models.Suggestion.Type.HISTORY :
holder.mSuggestionText.setText(mSuggestions.get(position).getName());
holder.mSuggestionIcon.setImageResource(com.automattic.simplenote.R.drawable.ic_history_24dp);
holder.mButtonDelete.setVisibility(android.view.View.VISIBLE);
break;
case com.automattic.simplenote.models.Suggestion.Type.QUERY :
holder.mSuggestionText.setText(mSuggestions.get(position).getName());
holder.mSuggestionIcon.setImageResource(com.automattic.simplenote.R.drawable.ic_search_24dp);
holder.mButtonDelete.setVisibility(android.view.View.GONE);
break;
case com.automattic.simplenote.models.Suggestion.Type.TAG :
holder.mSuggestionText.setText(com.automattic.simplenote.NoteListFragment.TAG_PREFIX + mSuggestions.get(position).getName());
holder.mSuggestionIcon.setImageResource(com.automattic.simplenote.R.drawable.ic_tag_24dp);
holder.mButtonDelete.setVisibility(android.view.View.GONE);
break;
}
switch(MUID_STATIC) {
// NoteListFragment_46_BuggyGUIListenerOperatorMutator
case 4686: {
holder.mButtonDelete.setOnClickListener(null);
break;
}
default: {
holder.mButtonDelete.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View view) {
if (!isAdded()) {
return;
}
final java.lang.String item;
item = holder.mSuggestionText.getText().toString();
deleteSearchItem(item);
switch(MUID_STATIC) {
// NoteListFragment_47_LengthyGUIListenerOperatorMutator
case 4786: {
/**
* Inserted by Kadabra
*/
com.google.android.material.snackbar.Snackbar.make(getRootView(), com.automattic.simplenote.R.string.snackbar_deleted_recent_search, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(getString(com.automattic.simplenote.R.string.undo), new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View view) {
addSearchItem(item, mDeletedItemIndex);
}

}).show();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
switch(MUID_STATIC) {
// NoteListFragment_48_BuggyGUIListenerOperatorMutator
case 4886: {
com.google.android.material.snackbar.Snackbar.make(getRootView(), com.automattic.simplenote.R.string.snackbar_deleted_recent_search, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(getString(com.automattic.simplenote.R.string.undo), null).show();
break;
}
default: {
com.google.android.material.snackbar.Snackbar.make(getRootView(), com.automattic.simplenote.R.string.snackbar_deleted_recent_search, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(getString(com.automattic.simplenote.R.string.undo), new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View view) {
switch(MUID_STATIC) {
// NoteListFragment_49_LengthyGUIListenerOperatorMutator
case 4986: {
/**
* Inserted by Kadabra
*/
addSearchItem(item, mDeletedItemIndex);
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
addSearchItem(item, mDeletedItemIndex);
break;
}
}
}

}).show();
break;
}
}
break;
}
}
}

});
break;
}
}
holder.mButtonDelete.setOnLongClickListener(new android.view.View.OnLongClickListener() {
@java.lang.Override
public boolean onLongClick(android.view.View v) {
if (v.isHapticFeedbackEnabled()) {
v.performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS);
}
android.widget.Toast.makeText(getContext(), requireContext().getString(com.automattic.simplenote.R.string.description_delete_item), android.widget.Toast.LENGTH_SHORT).show();
return true;
}

});
switch(MUID_STATIC) {
// NoteListFragment_50_BuggyGUIListenerOperatorMutator
case 5086: {
holder.mView.setOnClickListener(null);
break;
}
default: {
holder.mView.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View view) {
((com.automattic.simplenote.NotesActivity) (requireActivity())).submitSearch(holder.mSuggestionText.getText().toString());
switch(MUID_STATIC) {
// NoteListFragment_51_LengthyGUIListenerOperatorMutator
case 5186: {
/**
* Inserted by Kadabra
*/
if (holder.mViewType == com.automattic.simplenote.models.Suggestion.Type.HISTORY) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.RECENT_SEARCH_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_SEARCH, "recent_search_tapped");
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
if (holder.mViewType == com.automattic.simplenote.models.Suggestion.Type.HISTORY) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.RECENT_SEARCH_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_SEARCH, "recent_search_tapped");
}
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
public com.automattic.simplenote.NoteListFragment.SuggestionAdapter.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull
android.view.ViewGroup parent, int viewType) {
return new com.automattic.simplenote.NoteListFragment.SuggestionAdapter.ViewHolder(android.view.LayoutInflater.from(requireContext()).inflate(com.automattic.simplenote.R.layout.search_suggestion, parent, false), viewType);
}


private class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
private android.widget.ImageButton mButtonDelete;

private android.widget.ImageView mSuggestionIcon;

private android.widget.TextView mSuggestionText;

private android.view.View mView;

private int mViewType;

private ViewHolder(android.view.View itemView, int viewType) {
super(itemView);
mView = itemView;
mViewType = viewType;
switch(MUID_STATIC) {
// NoteListFragment_52_InvalidViewFocusOperatorMutator
case 5286: {
/**
* Inserted by Kadabra
*/
mSuggestionText = itemView.findViewById(com.automattic.simplenote.R.id.suggestion_text);
mSuggestionText.requestFocus();
break;
}
// NoteListFragment_53_ViewComponentNotVisibleOperatorMutator
case 5386: {
/**
* Inserted by Kadabra
*/
mSuggestionText = itemView.findViewById(com.automattic.simplenote.R.id.suggestion_text);
mSuggestionText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mSuggestionText = itemView.findViewById(com.automattic.simplenote.R.id.suggestion_text);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_54_InvalidViewFocusOperatorMutator
case 5486: {
/**
* Inserted by Kadabra
*/
mSuggestionIcon = itemView.findViewById(com.automattic.simplenote.R.id.suggestion_icon);
mSuggestionIcon.requestFocus();
break;
}
// NoteListFragment_55_ViewComponentNotVisibleOperatorMutator
case 5586: {
/**
* Inserted by Kadabra
*/
mSuggestionIcon = itemView.findViewById(com.automattic.simplenote.R.id.suggestion_icon);
mSuggestionIcon.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mSuggestionIcon = itemView.findViewById(com.automattic.simplenote.R.id.suggestion_icon);
break;
}
}
switch(MUID_STATIC) {
// NoteListFragment_56_InvalidViewFocusOperatorMutator
case 5686: {
/**
* Inserted by Kadabra
*/
mButtonDelete = itemView.findViewById(com.automattic.simplenote.R.id.suggestion_delete);
mButtonDelete.requestFocus();
break;
}
// NoteListFragment_57_ViewComponentNotVisibleOperatorMutator
case 5786: {
/**
* Inserted by Kadabra
*/
mButtonDelete = itemView.findViewById(com.automattic.simplenote.R.id.suggestion_delete);
mButtonDelete.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mButtonDelete = itemView.findViewById(com.automattic.simplenote.R.id.suggestion_delete);
break;
}
}
}

}

private void updateItems(java.util.List<com.automattic.simplenote.models.Suggestion> suggestions) {
androidx.recyclerview.widget.DiffUtil.DiffResult diffResult;
diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(new com.automattic.simplenote.NoteListFragment.SuggestionDiffCallback(mSuggestions, suggestions));
mSuggestions.clear();
mSuggestions.addAll(suggestions);
diffResult.dispatchUpdatesTo(this);
}

}

private class SuggestionDiffCallback extends androidx.recyclerview.widget.DiffUtil.Callback {
private java.util.List<com.automattic.simplenote.models.Suggestion> mListNew;

private java.util.List<com.automattic.simplenote.models.Suggestion> mListOld;

private SuggestionDiffCallback(java.util.List<com.automattic.simplenote.models.Suggestion> oldList, java.util.List<com.automattic.simplenote.models.Suggestion> newList) {
mListOld = oldList;
mListNew = newList;
}


@java.lang.Override
public boolean areContentsTheSame(int itemPositionOld, int itemPositionNew) {
com.automattic.simplenote.models.Suggestion itemOld;
itemOld = mListOld.get(itemPositionOld);
com.automattic.simplenote.models.Suggestion itemNew;
itemNew = mListNew.get(itemPositionNew);
return itemOld.getName().equalsIgnoreCase(itemNew.getName());
}


@java.lang.Override
public boolean areItemsTheSame(int itemPositionOld, int itemPositionNew) {
com.automattic.simplenote.models.Suggestion itemOld;
itemOld = mListOld.get(itemPositionOld);
com.automattic.simplenote.models.Suggestion itemNew;
itemNew = mListNew.get(itemPositionNew);
return itemOld.getName().equalsIgnoreCase(itemNew.getName());
}


@java.lang.Override
public int getNewListSize() {
return mListNew.size();
}


@java.lang.Override
public int getOldListSize() {
return mListOld.size();
}

}

private java.util.Calendar getDateByPreference(com.automattic.simplenote.models.Note note) {
switch (com.automattic.simplenote.utils.PrefUtils.getIntPref(requireContext(), com.automattic.simplenote.utils.PrefUtils.PREF_SORT_ORDER)) {
case com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_ASCENDING :
case com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_DESCENDING :
return note.getCreationDate();
case com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_ASCENDING :
case com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_DESCENDING :
return note.getModificationDate();
case com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_ASCENDING :
case com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_DESCENDING :
default :
return null;
}
}


private void showPopupMenuAtPosition(android.view.View view, int position) {
if (view.getContext() == null) {
return;
}
final com.automattic.simplenote.models.Note note;
switch(MUID_STATIC) {
// NoteListFragment_58_BinaryMutator
case 5886: {
note = mNotesAdapter.getItem(position - mList.getHeaderViewsCount());
break;
}
default: {
note = mNotesAdapter.getItem(position + mList.getHeaderViewsCount());
break;
}
}
if (note == null) {
return;
}
androidx.appcompat.widget.PopupMenu popup;
popup = new androidx.appcompat.widget.PopupMenu(view.getContext(), view, android.view.Gravity.END);
android.view.MenuInflater inflater;
inflater = popup.getMenuInflater();
inflater.inflate(com.automattic.simplenote.R.menu.bulk_edit, popup.getMenu());
if (!getListView().isLongClickable()) {
// If viewing the trash, remove pin menu item and change trash menu title to 'Restore'
popup.getMenu().removeItem(com.automattic.simplenote.R.id.menu_pin);
if (popup.getMenu().getItem(com.automattic.simplenote.NoteListFragment.POPUP_MENU_FIRST_ITEM_POSITION) != null) {
popup.getMenu().getItem(com.automattic.simplenote.NoteListFragment.POPUP_MENU_FIRST_ITEM_POSITION).setTitle(com.automattic.simplenote.R.string.restore);
}
} else if (popup.getMenu().getItem(com.automattic.simplenote.NoteListFragment.POPUP_MENU_FIRST_ITEM_POSITION) != null) {
// If not viewing the trash, set pin menu title based on note pin state
int pinTitle;
pinTitle = (note.isPinned()) ? com.automattic.simplenote.R.string.unpin_from_top : com.automattic.simplenote.R.string.pin_to_top;
popup.getMenu().getItem(com.automattic.simplenote.NoteListFragment.POPUP_MENU_FIRST_ITEM_POSITION).setTitle(pinTitle);
}
popup.setOnMenuItemClickListener(new androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener() {
@java.lang.Override
public boolean onMenuItemClick(android.view.MenuItem item) {
switch (item.getItemId()) {
case com.automattic.simplenote.R.id.menu_pin :
note.setPinned(!note.isPinned());
note.setModificationDate(java.util.Calendar.getInstance());
note.save();
refreshList();
return true;
case com.automattic.simplenote.R.id.menu_trash :
if (getActivity() != null) {
((com.automattic.simplenote.NotesActivity) (getActivity())).trashNote(note);
}
return true;
default :
return false;
}
}

});
popup.show();
}


private static class RefreshListTask extends android.os.AsyncTask<java.lang.Boolean, java.lang.Void, com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note>> {
private java.lang.ref.SoftReference<com.automattic.simplenote.NoteListFragment> mNoteListFragmentReference;

private boolean mIsFromNavSelect;

private RefreshListTask(com.automattic.simplenote.NoteListFragment context) {
mNoteListFragmentReference = new java.lang.ref.SoftReference<>(context);
}


@java.lang.Override
protected com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> doInBackground(java.lang.Boolean... args) {
com.automattic.simplenote.NoteListFragment fragment;
fragment = mNoteListFragmentReference.get();
mIsFromNavSelect = args[0];
return fragment.queryNotes();
}


@java.lang.Override
protected void onPostExecute(com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> cursor) {
com.automattic.simplenote.NoteListFragment fragment;
fragment = mNoteListFragmentReference.get();
if (((cursor == null) || (fragment.getActivity() == null)) || fragment.getActivity().isFinishing()) {
return;
}
// While using a Query.FullTextMatch it's easy to enter an invalid term so catch the error and clear the cursor
int count;
try {
fragment.mNotesAdapter.changeCursor(cursor);
count = fragment.mNotesAdapter.getCount();
} catch (android.database.sqlite.SQLiteException e) {
count = 0;
android.util.Log.e(com.automattic.simplenote.Simplenote.TAG, "Invalid SQL statement", e);
fragment.mNotesAdapter.changeCursor(null);
}
com.automattic.simplenote.NotesActivity notesActivity;
notesActivity = ((com.automattic.simplenote.NotesActivity) (fragment.getActivity()));
if (notesActivity != null) {
if (mIsFromNavSelect && com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(notesActivity)) {
if (count == 0) {
notesActivity.showDetailPlaceholder();
} else {
// Select the first note
fragment.selectFirstNote();
}
}
notesActivity.updateTrashMenuItem(true);
}
if (fragment.mSelectedNoteId != null) {
fragment.setNoteSelected(fragment.mSelectedNoteId);
fragment.mSelectedNoteId = null;
}
}

}

private static class RefreshListForSearchTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note>> {
private java.lang.ref.SoftReference<com.automattic.simplenote.NoteListFragment> mNoteListFragmentReference;

private RefreshListForSearchTask(com.automattic.simplenote.NoteListFragment context) {
mNoteListFragmentReference = new java.lang.ref.SoftReference<>(context);
}


@java.lang.Override
protected com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> doInBackground(java.lang.Void... args) {
com.automattic.simplenote.NoteListFragment fragment;
fragment = mNoteListFragmentReference.get();
return fragment.queryNotesForSearch();
}


@java.lang.Override
protected void onPostExecute(com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> cursor) {
com.automattic.simplenote.NoteListFragment fragment;
fragment = mNoteListFragmentReference.get();
if (((cursor == null) || (fragment.getActivity() == null)) || fragment.getActivity().isFinishing()) {
return;
}
// While using Query.FullTextMatch, it's easy to enter an invalid term so catch the error and clear the cursor.
try {
fragment.mNotesAdapter.changeCursor(cursor);
} catch (android.database.sqlite.SQLiteException e) {
android.util.Log.e(com.automattic.simplenote.Simplenote.TAG, "Invalid SQL statement", e);
fragment.mNotesAdapter.changeCursor(null);
}
com.automattic.simplenote.NotesActivity notesActivity;
notesActivity = ((com.automattic.simplenote.NotesActivity) (fragment.requireActivity()));
notesActivity.updateTrashMenuItem(true);
if (fragment.mSelectedNoteId != null) {
fragment.setNoteSelected(fragment.mSelectedNoteId);
fragment.mSelectedNoteId = null;
}
}

}

private static class PinNotesTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void> {
private java.lang.ref.SoftReference<com.automattic.simplenote.NoteListFragment> mNoteListFragmentReference;

private android.util.SparseBooleanArray mSelectedRows = new android.util.SparseBooleanArray();

private PinNotesTask(com.automattic.simplenote.NoteListFragment context) {
mNoteListFragmentReference = new java.lang.ref.SoftReference<>(context);
}


@java.lang.Override
protected void onPreExecute() {
com.automattic.simplenote.NoteListFragment fragment;
fragment = mNoteListFragmentReference.get();
mSelectedRows = fragment.getListView().getCheckedItemPositions();
}


@java.lang.Override
protected java.lang.Void doInBackground(java.lang.Void... args) {
com.automattic.simplenote.NoteListFragment fragment;
fragment = mNoteListFragmentReference.get();
// Get the checked notes and add them to the pinnedNotesList
// We can't modify the note in this loop because the adapter could change
java.util.List<com.automattic.simplenote.models.Note> pinnedNotesList;
pinnedNotesList = new java.util.ArrayList<>();
for (int i = 0; i < mSelectedRows.size(); i++) {
if (mSelectedRows.valueAt(i)) {
pinnedNotesList.add(fragment.mNotesAdapter.getItem(mSelectedRows.keyAt(i)));
}
}
// Now loop through the notes list and mark them as pinned
for (com.automattic.simplenote.models.Note pinnedNote : pinnedNotesList) {
pinnedNote.setPinned(!pinnedNote.isPinned());
pinnedNote.setModificationDate(java.util.Calendar.getInstance());
pinnedNote.save();
}
return null;
}


@java.lang.Override
protected void onPostExecute(java.lang.Void aVoid) {
com.automattic.simplenote.NoteListFragment fragment;
fragment = mNoteListFragmentReference.get();
fragment.mActionMode.finish();
fragment.refreshList();
}

}

private static class TrashNotesTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void> {
private java.util.List<java.lang.String> mDeletedNoteIds = new java.util.ArrayList<>();

private java.lang.ref.SoftReference<com.automattic.simplenote.NoteListFragment> mNoteListFragmentReference;

private android.util.SparseBooleanArray mSelectedRows = new android.util.SparseBooleanArray();

private TrashNotesTask(com.automattic.simplenote.NoteListFragment context) {
mNoteListFragmentReference = new java.lang.ref.SoftReference<>(context);
}


@java.lang.Override
protected void onPreExecute() {
com.automattic.simplenote.NoteListFragment fragment;
fragment = mNoteListFragmentReference.get();
mSelectedRows = fragment.getListView().getCheckedItemPositions();
}


@java.lang.Override
protected java.lang.Void doInBackground(java.lang.Void... args) {
com.automattic.simplenote.NoteListFragment fragment;
fragment = mNoteListFragmentReference.get();
// Get the checked notes and add them to the deletedNotesList
// We can't modify the note in this loop because the adapter could change
java.util.List<com.automattic.simplenote.models.Note> deletedNotesList;
deletedNotesList = new java.util.ArrayList<>();
for (int i = 0; i < mSelectedRows.size(); i++) {
if (mSelectedRows.valueAt(i)) {
deletedNotesList.add(fragment.mNotesAdapter.getItem(mSelectedRows.keyAt(i)));
}
}
// Now loop through the notes list and mark them as deleted
for (com.automattic.simplenote.models.Note deletedNote : deletedNotesList) {
mDeletedNoteIds.add(deletedNote.getSimperiumKey());
deletedNote.setDeleted(!deletedNote.isDeleted());
deletedNote.setModificationDate(java.util.Calendar.getInstance());
deletedNote.save();
}
return null;
}


@java.lang.Override
protected void onPostExecute(java.lang.Void aVoid) {
com.automattic.simplenote.NoteListFragment fragment;
fragment = mNoteListFragmentReference.get();
com.automattic.simplenote.NotesActivity notesActivity;
notesActivity = ((com.automattic.simplenote.NotesActivity) (fragment.getActivity()));
if (notesActivity != null) {
notesActivity.showUndoBarWithNoteIds(mDeletedNoteIds);
}
if (!fragment.isDetached()) {
fragment.updateSelectionAfterTrashAction();
fragment.mActionMode.finish();
fragment.refreshList();
}
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
