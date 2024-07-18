package com.automattic.simplenote;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_NOTE_TAPPED;
import com.simperium.client.Query;
import android.content.Intent;
import android.view.View;
import com.automattic.simplenote.utils.ChecklistUtils;
import androidx.annotation.LayoutRes;
import android.widget.RemoteViews;
import static com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK;
import com.simperium.client.Bucket;
import com.automattic.simplenote.models.Note;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import android.content.Context;
import android.text.SpannableStringBuilder;
import com.automattic.simplenote.utils.PrefUtils;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteListWidgetFactory implements android.widget.RemoteViewsService.RemoteViewsFactory {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String EXTRA_IS_LIGHT = "is_light";

    private com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> mCursor;

    private android.content.Context mContext;

    private boolean mIsLight;

    public NoteListWidgetFactory(android.content.Context context, android.content.Intent intent) {
        mContext = context;
        mIsLight = (intent.getExtras() == null) || intent.getExtras().getBoolean(com.automattic.simplenote.NoteListWidgetFactory.EXTRA_IS_LIGHT, true);
    }


    @java.lang.Override
    public int getCount() {
        return mCursor.getCount();
    }


    @java.lang.Override
    public long getItemId(int position) {
        return position;
    }


    @java.lang.Override
    public android.widget.RemoteViews getLoadingView() {
        return null;
    }


    @java.lang.Override
    public android.widget.RemoteViews getViewAt(int position) {
        @androidx.annotation.LayoutRes
        int layout;
        layout = com.automattic.simplenote.utils.PrefUtils.getLayoutWidgetListItem(mContext, mIsLight);
        android.widget.RemoteViews views;
        views = new android.widget.RemoteViews(mContext.getPackageName(), layout);
        if (mCursor.moveToPosition(position)) {
            com.automattic.simplenote.models.Note note;
            note = mCursor.getObject();
            views.setTextViewText(com.automattic.simplenote.R.id.note_title, note.getTitle());
            android.text.SpannableStringBuilder contentSpan;
            contentSpan = new android.text.SpannableStringBuilder(note.getContentPreview());
            contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
            views.setTextViewText(com.automattic.simplenote.R.id.note_content, contentSpan);
            views.setViewVisibility(com.automattic.simplenote.R.id.note_pinned, note.isPinned() ? android.view.View.VISIBLE : android.view.View.GONE);
            views.setViewVisibility(com.automattic.simplenote.R.id.note_published, note.isPublished() ? android.view.View.VISIBLE : android.view.View.GONE);
            views.setViewVisibility(com.automattic.simplenote.R.id.note_status, note.isPinned() || note.isPublished() ? android.view.View.VISIBLE : android.view.View.GONE);
            boolean isCondensed;
            isCondensed = com.automattic.simplenote.utils.PrefUtils.getBoolPref(mContext, com.automattic.simplenote.utils.PrefUtils.PREF_CONDENSED_LIST, false);
            views.setViewVisibility(com.automattic.simplenote.R.id.note_content, isCondensed ? android.view.View.GONE : android.view.View.VISIBLE);
            // Create intent to navigate to note editor on note list item click
            android.content.Intent intent;
            switch(MUID_STATIC) {
                // NoteListWidgetFactory_0_NullIntentOperatorMutator
                case 84: {
                    intent = null;
                    break;
                }
                // NoteListWidgetFactory_1_InvalidKeyIntentOperatorMutator
                case 184: {
                    intent = new android.content.Intent((android.content.Context) null, com.automattic.simplenote.NoteEditorActivity.class);
                    break;
                }
                // NoteListWidgetFactory_2_RandomActionIntentDefinitionOperatorMutator
                case 284: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(mContext, com.automattic.simplenote.NoteEditorActivity.class);
                break;
            }
        }
        switch(MUID_STATIC) {
            // NoteListWidgetFactory_3_NullValueIntentPutExtraOperatorMutator
            case 384: {
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
                break;
            }
            // NoteListWidgetFactory_4_IntentPayloadReplacementOperatorMutator
            case 484: {
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // NoteListWidgetFactory_5_RandomActionIntentDefinitionOperatorMutator
                case 584: {
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
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_NOTE_TAPPED);
                break;
            }
        }
        break;
    }
}
switch(MUID_STATIC) {
    // NoteListWidgetFactory_6_NullValueIntentPutExtraOperatorMutator
    case 684: {
        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, new Parcelable[0]);
        break;
    }
    // NoteListWidgetFactory_7_IntentPayloadReplacementOperatorMutator
    case 784: {
        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // NoteListWidgetFactory_8_RandomActionIntentDefinitionOperatorMutator
        case 884: {
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
        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
        break;
    }
}
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetFactory_9_NullValueIntentPutExtraOperatorMutator
case 984: {
intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, new Parcelable[0]);
break;
}
// NoteListWidgetFactory_10_IntentPayloadReplacementOperatorMutator
case 1084: {
intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, "");
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetFactory_11_RandomActionIntentDefinitionOperatorMutator
case 1184: {
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
intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetFactory_12_NullValueIntentPutExtraOperatorMutator
case 1284: {
intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, new Parcelable[0]);
break;
}
// NoteListWidgetFactory_13_IntentPayloadReplacementOperatorMutator
case 1384: {
intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, true);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetFactory_14_RandomActionIntentDefinitionOperatorMutator
case 1484: {
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
intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetFactory_15_NullValueIntentPutExtraOperatorMutator
case 1584: {
intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, new Parcelable[0]);
break;
}
// NoteListWidgetFactory_16_IntentPayloadReplacementOperatorMutator
case 1684: {
intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, true);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetFactory_17_RandomActionIntentDefinitionOperatorMutator
case 1784: {
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
intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetFactory_18_RandomActionIntentDefinitionOperatorMutator
case 1884: {
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
intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
break;
}
}
views.setOnClickFillInIntent(com.automattic.simplenote.R.id.widget_item, intent);
}
return views;
}


@java.lang.Override
public int getViewTypeCount() {
return 1;
}


@java.lang.Override
public boolean hasStableIds() {
return true;
}


@java.lang.Override
public void onCreate() {
}


@java.lang.Override
public void onDataSetChanged() {
if (mCursor != null) {
mCursor.close();
}
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket;
notesBucket = ((com.automattic.simplenote.Simplenote) (mContext.getApplicationContext())).getNotesBucket();
com.simperium.client.Query<com.automattic.simplenote.models.Note> query;
query = com.automattic.simplenote.models.Note.all(notesBucket);
query.include(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME, com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME);
com.automattic.simplenote.utils.PrefUtils.sortNoteQuery(query, mContext, true);
mCursor = query.execute();
}


@java.lang.Override
public void onDestroy() {
if (mCursor != null) {
mCursor.close();
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
