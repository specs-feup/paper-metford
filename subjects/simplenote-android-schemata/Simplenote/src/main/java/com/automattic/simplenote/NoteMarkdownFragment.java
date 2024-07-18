package com.automattic.simplenote;
import java.util.Set;
import com.automattic.simplenote.utils.ThemeUtils;
import androidx.fragment.app.Fragment;
import com.automattic.simplenote.utils.NoteUtils;
import com.automattic.simplenote.utils.AppLog;
import androidx.fragment.app.FragmentActivity;
import com.automattic.simplenote.utils.NetworkUtils;
import androidx.annotation.NonNull;
import androidx.core.view.MenuCompat;
import java.lang.ref.SoftReference;
import android.webkit.WebViewClient;
import android.webkit.WebView;
import com.automattic.simplenote.utils.DrawableUtils;
import android.content.SharedPreferences;
import static com.automattic.simplenote.Simplenote.SCROLL_POSITION_PREFERENCES;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import android.view.Menu;
import android.view.MenuInflater;
import static com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.automattic.simplenote.utils.SimplenoteLinkify;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.MenuItem;
import com.simperium.client.BucketObjectMissingException;
import android.view.View;
import com.commonsware.cwac.anddown.AndDown;
import com.automattic.simplenote.utils.ContextUtils;
import com.automattic.simplenote.utils.AppLog.Type;
import android.view.LayoutInflater;
import com.simperium.client.Bucket;
import static com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX;
import com.automattic.simplenote.models.Note;
import com.google.android.material.snackbar.Snackbar;
import android.content.Context;
import androidx.core.widget.NestedScrollView;
import com.automattic.simplenote.utils.BrowserUtils;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteMarkdownFragment extends androidx.fragment.app.Fragment implements com.simperium.client.Bucket.Listener<com.automattic.simplenote.models.Note> {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ARG_ITEM_ID = "item_id";

    private com.simperium.client.Bucket<com.automattic.simplenote.models.Note> mNotesBucket;

    private com.automattic.simplenote.models.Note mNote;

    private android.content.SharedPreferences mPreferences;

    private java.lang.String mCss;

    private android.webkit.WebView mMarkdown;

    private boolean mIsLoadingNote;

    @java.lang.Override
    public void onCreateOptionsMenu(@androidx.annotation.NonNull
    android.view.Menu menu, @androidx.annotation.NonNull
    android.view.MenuInflater inflater) {
        inflater.inflate(com.automattic.simplenote.R.menu.note_markdown, menu);
        androidx.core.view.MenuCompat.setGroupDividerEnabled(menu, true);
        com.automattic.simplenote.utils.DrawableUtils.tintMenuWithAttribute(requireContext(), menu, com.automattic.simplenote.R.attr.toolbarIconColor);
        for (int i = 0; i < menu.size(); i++) {
            android.view.MenuItem item;
            item = menu.getItem(i);
            com.automattic.simplenote.utils.DrawableUtils.setMenuItemAlpha(item, 0.3)// 0.3 is 30% opacity.
            ;// 0.3 is 30% opacity.

        }
        if (mNote != null) {
            android.view.MenuItem viewPublishedNoteItem;
            viewPublishedNoteItem = menu.findItem(com.automattic.simplenote.R.id.menu_info);
            viewPublishedNoteItem.setVisible(true);
            android.view.MenuItem trashItem;
            trashItem = menu.findItem(com.automattic.simplenote.R.id.menu_trash);
            if (mNote.isDeleted()) {
                trashItem.setTitle(com.automattic.simplenote.R.string.restore);
            } else {
                trashItem.setTitle(com.automattic.simplenote.R.string.trash);
            }
            com.automattic.simplenote.utils.DrawableUtils.tintMenuItemWithAttribute(getActivity(), trashItem, com.automattic.simplenote.R.attr.toolbarIconColor);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Created (NoteMarkdownFragment)");
        mNotesBucket = ((com.automattic.simplenote.Simplenote) (requireActivity().getApplication())).getNotesBucket();
        mPreferences = requireContext().getSharedPreferences(com.automattic.simplenote.Simplenote.SCROLL_POSITION_PREFERENCES, android.content.Context.MODE_PRIVATE);
        // Load note if we were passed an ID.
        android.os.Bundle arguments;
        arguments = getArguments();
        if ((arguments != null) && arguments.containsKey(com.automattic.simplenote.NoteMarkdownFragment.ARG_ITEM_ID)) {
            java.lang.String key;
            key = arguments.getString(com.automattic.simplenote.NoteMarkdownFragment.ARG_ITEM_ID);
            new com.automattic.simplenote.NoteMarkdownFragment.LoadNoteTask(this).executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, key);
        }
        setHasOptionsMenu(true);
        final android.view.View layout;
        if (com.automattic.simplenote.utils.BrowserUtils.isWebViewInstalled(requireContext())) {
            layout = inflater.inflate(com.automattic.simplenote.R.layout.fragment_note_markdown, container, false);
            ((androidx.core.widget.NestedScrollView) (layout)).setOnScrollChangeListener(new androidx.core.widget.NestedScrollView.OnScrollChangeListener() {
                @java.lang.Override
                public void onScrollChange(androidx.core.widget.NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    mPreferences.edit().putInt(mNote.getSimperiumKey(), scrollY).apply();
                }

            });
            switch(MUID_STATIC) {
                // NoteMarkdownFragment_0_InvalidViewFocusOperatorMutator
                case 95: {
                    /**
                    * Inserted by Kadabra
                    */
                    mMarkdown = layout.findViewById(com.automattic.simplenote.R.id.markdown);
                    mMarkdown.requestFocus();
                    break;
                }
                // NoteMarkdownFragment_1_ViewComponentNotVisibleOperatorMutator
                case 195: {
                    /**
                    * Inserted by Kadabra
                    */
                    mMarkdown = layout.findViewById(com.automattic.simplenote.R.id.markdown);
                    mMarkdown.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                mMarkdown = layout.findViewById(com.automattic.simplenote.R.id.markdown);
                break;
            }
        }
        final long delay;
        delay = requireContext().getResources().getInteger(android.R.integer.config_mediumAnimTime);
        mMarkdown.setWebViewClient(new android.webkit.WebViewClient() {
            @java.lang.Override
            public void onPageFinished(final android.webkit.WebView view, java.lang.String url) {
                super.onPageFinished(view, url);
                new android.os.Handler().postDelayed(new java.lang.Runnable() {
                    @java.lang.Override
                    public void run() {
                        if ((mNote != null) && (mNote.getSimperiumKey() != null)) {
                            ((androidx.core.widget.NestedScrollView) (layout)).smoothScrollTo(0, mPreferences.getInt(mNote.getSimperiumKey(), 0));
                        }
                    }

                }, delay);
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
        layout = inflater.inflate(com.automattic.simplenote.R.layout.fragment_note_error, container, false);
        layout.findViewById(com.automattic.simplenote.R.id.error).setVisibility(android.view.View.VISIBLE);
        switch(MUID_STATIC) {
            // NoteMarkdownFragment_2_BuggyGUIListenerOperatorMutator
            case 295: {
                layout.findViewById(com.automattic.simplenote.R.id.button).setOnClickListener(null);
                break;
            }
            default: {
            layout.findViewById(com.automattic.simplenote.R.id.button).setOnClickListener(new android.view.View.OnClickListener() {
                @java.lang.Override
                public void onClick(android.view.View v) {
                    switch(MUID_STATIC) {
                        // NoteMarkdownFragment_3_LengthyGUIListenerOperatorMutator
                        case 395: {
                            /**
                            * Inserted by Kadabra
                            */
                            com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.utils.BrowserUtils.URL_WEB_VIEW);
                            try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                            break;
                        }
                        default: {
                        com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.utils.BrowserUtils.URL_WEB_VIEW);
                        break;
                    }
                }
            }

        });
        break;
    }
}
}
return layout;
}


@java.lang.Override
public boolean onOptionsItemSelected(@androidx.annotation.NonNull
android.view.MenuItem item) {
switch (item.getItemId()) {
case android.R.id.home :
    if (!isAdded()) {
        return false;
    }
    requireActivity().finish();
    return true;
case com.automattic.simplenote.R.id.menu_delete :
    com.automattic.simplenote.utils.NoteUtils.showDialogDeletePermanently(requireActivity(), mNote);
    return true;
case com.automattic.simplenote.R.id.menu_collaborators :
    navigateToCollaborators();
    return true;
case com.automattic.simplenote.R.id.menu_trash :
    if (!isAdded()) {
        return false;
    }
    deleteNote();
    return true;
case com.automattic.simplenote.R.id.menu_copy_internal :
    com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.INTERNOTE_LINK_COPIED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_LINK, "internote_link_copied_markdown");
    if (!isAdded()) {
        return false;
    }
    if (com.automattic.simplenote.utils.BrowserUtils.copyToClipboard(requireContext(), com.automattic.simplenote.utils.SimplenoteLinkify.getNoteLinkWithTitle(mNote.getTitle(), mNote.getSimperiumKey()))) {
        com.google.android.material.snackbar.Snackbar.make(mMarkdown, com.automattic.simplenote.R.string.link_copied, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
    } else {
        com.google.android.material.snackbar.Snackbar.make(mMarkdown, com.automattic.simplenote.R.string.link_copied_failure, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
    }
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
// NoteMarkdownFragment_4_NullIntentOperatorMutator
case 495: {
    intent = null;
    break;
}
// NoteMarkdownFragment_5_InvalidKeyIntentOperatorMutator
case 595: {
    intent = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, com.automattic.simplenote.CollaboratorsActivity.class);
    break;
}
// NoteMarkdownFragment_6_RandomActionIntentDefinitionOperatorMutator
case 695: {
    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent = new android.content.Intent(requireActivity(), com.automattic.simplenote.CollaboratorsActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteMarkdownFragment_7_NullValueIntentPutExtraOperatorMutator
case 795: {
intent.putExtra(com.automattic.simplenote.CollaboratorsActivity.NOTE_ID_ARG, new Parcelable[0]);
break;
}
// NoteMarkdownFragment_8_IntentPayloadReplacementOperatorMutator
case 895: {
intent.putExtra(com.automattic.simplenote.CollaboratorsActivity.NOTE_ID_ARG, "");
break;
}
default: {
switch(MUID_STATIC) {
// NoteMarkdownFragment_9_RandomActionIntentDefinitionOperatorMutator
case 995: {
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


private void deleteNote() {
com.automattic.simplenote.utils.NoteUtils.deleteNote(mNote, getActivity());
requireActivity().finish();
}


@java.lang.Override
public void onPrepareOptionsMenu(@androidx.annotation.NonNull
android.view.Menu menu) {
// Show delete action only when note is in Trash.
menu.findItem(com.automattic.simplenote.R.id.menu_delete).setVisible((mNote != null) && mNote.isDeleted());
// Disable trash action until note is loaded.
menu.findItem(com.automattic.simplenote.R.id.menu_trash).setEnabled(!mIsLoadingNote);
android.view.MenuItem pinItem;
pinItem = menu.findItem(com.automattic.simplenote.R.id.menu_pin);
android.view.MenuItem publishItem;
publishItem = menu.findItem(com.automattic.simplenote.R.id.menu_publish);
android.view.MenuItem copyLinkItem;
copyLinkItem = menu.findItem(com.automattic.simplenote.R.id.menu_copy);
android.view.MenuItem markdownItem;
markdownItem = menu.findItem(com.automattic.simplenote.R.id.menu_markdown);
android.view.MenuItem copyLinkInternalItem;
copyLinkInternalItem = menu.findItem(com.automattic.simplenote.R.id.menu_copy_internal);
if (mNote != null) {
pinItem.setChecked(mNote.isPinned());
publishItem.setChecked(mNote.isPublished());
markdownItem.setChecked(mNote.isMarkdownEnabled());
}
pinItem.setEnabled(false);
publishItem.setEnabled(false);
copyLinkItem.setEnabled(false);
markdownItem.setEnabled(false);
copyLinkInternalItem.setEnabled(true);
super.onPrepareOptionsMenu(menu);
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
mNotesBucket.removeListener(this);
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Removed note bucket listener (NoteMarkdownFragment)");
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Destroyed (NoteMarkdownFragment)");
}


@java.lang.Override
public void onResume() {
super.onResume();
// First inflation of the webview may invalidate the value of uiMode,
// so we re-apply it to make sure that the webview has the right css files
// Check https://issuetracker.google.com/issues/37124582 for more details
((androidx.appcompat.app.AppCompatActivity) (requireActivity())).getDelegate().applyDayNight();
checkWebView();
mNotesBucket.addListener(this);
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Added note bucket listener (NoteMarkdownFragment)");
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.NETWORK, com.automattic.simplenote.utils.NetworkUtils.getNetworkInfo(requireContext()));
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SCREEN, "Resumed (NoteMarkdownFragment)");
}


@java.lang.Override
public void onBeforeUpdateObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, com.automattic.simplenote.models.Note note) {
}


@java.lang.Override
public void onDeleteObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, com.automattic.simplenote.models.Note note) {
}


@java.lang.Override
public void onNetworkChange(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, com.simperium.client.Bucket.ChangeType type, java.lang.String key) {
}


@java.lang.Override
public void onSaveObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, com.automattic.simplenote.models.Note note) {
if (note.equals(mNote)) {
mNote = note;
requireActivity().invalidateOptionsMenu();
}
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, ((((((("Saved note callback in NoteMarkdownFragment (ID: " + note.getSimperiumKey()) + " / Title: ") + note.getTitle()) + " / Characters: ") + com.automattic.simplenote.utils.NoteUtils.getCharactersCount(note.getContent())) + " / Words: ") + com.automattic.simplenote.utils.NoteUtils.getWordCount(note.getContent())) + ")");
}


private void checkWebView() {
// When a WebView is installed and mMarkdown is null, a WebView was not installed when the
// fragment was created.  So, open the note again to show the markdown preview.
if (com.automattic.simplenote.utils.BrowserUtils.isWebViewInstalled(requireContext()) && (mMarkdown == null)) {
com.automattic.simplenote.utils.SimplenoteLinkify.openNote(requireActivity(), mNote.getSimperiumKey());
}
}


public void updateMarkdown(java.lang.String text) {
if (mMarkdown != null) {
mMarkdown.loadDataWithBaseURL(null, com.automattic.simplenote.NoteMarkdownFragment.getMarkdownFormattedContent(mCss, text), "text/html", "utf-8", null);
}
}


public static java.lang.String getMarkdownFormattedContent(java.lang.String cssContent, java.lang.String sourceContent) {
java.lang.String header;
header = (("<html><head>" + ("<link href=\"https://fonts.googleapis.com/css?family=Noto+Serif\" rel=\"stylesheet\">" + "<meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,initial-scale=1\">\n")) + cssContent) + "</head><body>";
java.lang.String parsedMarkdown;
parsedMarkdown = new com.commonsware.cwac.anddown.AndDown().markdownToHtml(sourceContent, ((com.commonsware.cwac.anddown.AndDown.HOEDOWN_EXT_STRIKETHROUGH | com.commonsware.cwac.anddown.AndDown.HOEDOWN_EXT_FENCED_CODE) | com.commonsware.cwac.anddown.AndDown.HOEDOWN_EXT_QUOTE) | com.commonsware.cwac.anddown.AndDown.HOEDOWN_EXT_TABLES, com.commonsware.cwac.anddown.AndDown.HOEDOWN_HTML_ESCAPE);
// Set auto alignment for lists, tables, and quotes based on language of start.
parsedMarkdown = parsedMarkdown.replaceAll("<ol>", "<ol dir=\"auto\">").replaceAll("<ul>", "<ul dir=\"auto\">").replaceAll("<table>", "<table dir=\"auto\">").replaceAll("<blockquote>", "<blockquote dir=\"auto\">");
return ((header + "<div class=\"note-detail-markdown\">") + parsedMarkdown) + "</div></body></html>";
}


@java.lang.Override
public void onLocalQueueChange(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, java.util.Set<java.lang.String> queuedObjects) {
}


@java.lang.Override
public void onSyncObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, java.lang.String key) {
}


private static class LoadNoteTask extends android.os.AsyncTask<java.lang.String, java.lang.Void, java.lang.Void> {
private java.lang.ref.SoftReference<com.automattic.simplenote.NoteMarkdownFragment> mNoteMarkdownFragmentReference;

private LoadNoteTask(com.automattic.simplenote.NoteMarkdownFragment context) {
mNoteMarkdownFragmentReference = new java.lang.ref.SoftReference<>(context);
}


@java.lang.Override
protected void onPreExecute() {
com.automattic.simplenote.NoteMarkdownFragment fragment;
fragment = mNoteMarkdownFragmentReference.get();
fragment.mIsLoadingNote = true;
}


@java.lang.Override
protected java.lang.Void doInBackground(java.lang.String... args) {
com.automattic.simplenote.NoteMarkdownFragment fragment;
fragment = mNoteMarkdownFragmentReference.get();
androidx.fragment.app.FragmentActivity activity;
activity = fragment.getActivity();
if (activity == null) {
return null;
}
java.lang.String noteID;
noteID = args[0];
com.automattic.simplenote.Simplenote application;
application = ((com.automattic.simplenote.Simplenote) (activity.getApplication()));
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket;
notesBucket = application.getNotesBucket();
try {
fragment.mNote = notesBucket.get(noteID);
} catch (com.simperium.client.BucketObjectMissingException exception) {
// TODO: Handle a missing note
}
return null;
}


@java.lang.Override
protected void onPostExecute(java.lang.Void nada) {
com.automattic.simplenote.NoteMarkdownFragment fragment;
fragment = mNoteMarkdownFragmentReference.get();
fragment.mIsLoadingNote = false;
fragment.requireActivity().invalidateOptionsMenu();
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
