package com.automattic.simplenote;
import com.simperium.client.User;
import android.appwidget.AppWidgetProvider;
import com.simperium.Simperium;
import static com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_SIGN_IN_TAPPED;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_FIRST_ADDED;
import com.automattic.simplenote.utils.PrefUtils;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import android.os.Bundle;
import android.appwidget.AppWidgetManager;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_DELETED;
import android.content.Intent;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED;
import com.simperium.client.BucketObjectMissingException;
import android.view.View;
import com.automattic.simplenote.utils.ChecklistUtils;
import android.widget.RemoteViews;
import com.simperium.client.Bucket;
import com.automattic.simplenote.models.Note;
import static com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_LAST_DELETED;
import android.app.PendingIntent;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteWidgetLight extends android.appwidget.AppWidgetProvider {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String KEY_WIDGET_IDS_LIGHT = "key_widget_ids_light";

    @java.lang.Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if ((intent.getExtras() != null) && intent.hasExtra(com.automattic.simplenote.NoteWidgetLight.KEY_WIDGET_IDS_LIGHT)) {
            int[] ids;
            ids = intent.getExtras().getIntArray(com.automattic.simplenote.NoteWidgetLight.KEY_WIDGET_IDS_LIGHT);
            this.onUpdate(context, android.appwidget.AppWidgetManager.getInstance(context), ids);
        } else {
            super.onReceive(context, intent);
        }
    }


    @java.lang.Override
    public void onUpdate(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            android.os.Bundle appWidgetOptions;
            appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            updateWidget(context, appWidgetManager, appWidgetId, appWidgetOptions);
        }
    }


    @java.lang.Override
    public void onAppWidgetOptionsChanged(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int appWidgetId, android.os.Bundle newOptions) {
        android.widget.RemoteViews views;
        views = new android.widget.RemoteViews(context.getPackageName(), com.automattic.simplenote.utils.PrefUtils.getLayoutWidget(context, true));
        resizeWidget(newOptions, views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @java.lang.Override
    public void onDeleted(android.content.Context context, int[] appWidgetIds) {
        com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_DELETED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET, "note_widget_deleted");
    }


    @java.lang.Override
    public void onEnabled(android.content.Context context) {
        com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_FIRST_ADDED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET, "note_widget_first_added");
    }


    @java.lang.Override
    public void onDisabled(android.content.Context context) {
        com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_LAST_DELETED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET, "note_widget_last_deleted");
    }


    private void resizeWidget(android.os.Bundle appWidgetOptions, android.widget.RemoteViews views) {
        // Show/Hide larger title and content based on widget width
        if (appWidgetOptions.getInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH) > 200) {
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text, android.view.View.GONE);
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_title, android.view.View.VISIBLE);
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.VISIBLE);
        } else {
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text, android.view.View.VISIBLE);
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_title, android.view.View.GONE);
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
        }
    }


    private void updateWidget(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int appWidgetId, android.os.Bundle appWidgetOptions) {
        // Get widget views
        android.widget.RemoteViews views;
        views = new android.widget.RemoteViews(context.getPackageName(), com.automattic.simplenote.utils.PrefUtils.getLayoutWidget(context, true));
        resizeWidget(appWidgetOptions, views);
        // Verify user authentication
        com.automattic.simplenote.Simplenote currentApp;
        currentApp = ((com.automattic.simplenote.Simplenote) (context.getApplicationContext()));
        com.simperium.Simperium simperium;
        simperium = currentApp.getSimperium();
        com.simperium.client.User user;
        user = simperium.getUser();
        if (user.getStatus().equals(com.simperium.client.User.Status.NOT_AUTHORIZED)) {
            // Create intent to navigate to notes activity which redirects to login on widget click
            android.content.Intent intent;
            switch(MUID_STATIC) {
                // NoteWidgetLight_0_NullIntentOperatorMutator
                case 69: {
                    intent = null;
                    break;
                }
                // NoteWidgetLight_1_InvalidKeyIntentOperatorMutator
                case 169: {
                    intent = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
                    break;
                }
                // NoteWidgetLight_2_RandomActionIntentDefinitionOperatorMutator
                case 269: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
                break;
            }
        }
        switch(MUID_STATIC) {
            // NoteWidgetLight_3_NullValueIntentPutExtraOperatorMutator
            case 369: {
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, new Parcelable[0]);
                break;
            }
            // NoteWidgetLight_4_IntentPayloadReplacementOperatorMutator
            case 469: {
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // NoteWidgetLight_5_RandomActionIntentDefinitionOperatorMutator
                case 569: {
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
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_SIGN_IN_TAPPED);
                break;
            }
        }
        break;
    }
}
switch(MUID_STATIC) {
    // NoteWidgetLight_6_RandomActionIntentDefinitionOperatorMutator
    case 669: {
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
android.app.PendingIntent pendingIntent;
pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_IMMUTABLE);
views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.log_in_use_widget));
views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.log_in_use_widget));
views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
} else {
// Get note id from SharedPreferences
java.lang.String key;
key = com.automattic.simplenote.utils.PrefUtils.getStringPref(context, com.automattic.simplenote.utils.PrefUtils.PREF_NOTE_WIDGET_NOTE + appWidgetId);
if (!key.isEmpty()) {
// Get notes bucket
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket;
notesBucket = currentApp.getNotesBucket();
switch(MUID_STATIC) {
    // NoteWidgetLight_7_NullIntentOperatorMutator
    case 769: {
        try {
            // Update note
            com.automattic.simplenote.models.Note updatedNote;
            updatedNote = notesBucket.get(key);
            // Prepare bundle for NoteEditorActivity
            android.os.Bundle arguments;
            arguments = new android.os.Bundle();
            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
            arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
            // Create intent to navigate to selected note on widget click
            android.content.Intent intent;
            intent = null;
            intent.putExtras(arguments);
            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            android.app.PendingIntent pendingIntent;
            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
            // Remove title from content
            java.lang.String title;
            title = updatedNote.getTitle();
            java.lang.String contentWithoutTitle;
            contentWithoutTitle = updatedNote.getContent().replace(title, "");
            int indexOfNewline;
            indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
            java.lang.String content;
            content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
            // Set widget content
            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            android.text.SpannableStringBuilder contentSpan;
            contentSpan = new android.text.SpannableStringBuilder(content);
            contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
        } catch (com.simperium.client.BucketObjectMissingException e) {
            // Create intent to navigate to widget configure activity on widget click
            android.content.Intent intent;
            intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
            intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            android.app.PendingIntent pendingIntent;
            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
        }
        break;
    }
    // NoteWidgetLight_8_InvalidKeyIntentOperatorMutator
    case 869: {
        try {
            // Update note
            com.automattic.simplenote.models.Note updatedNote;
            updatedNote = notesBucket.get(key);
            // Prepare bundle for NoteEditorActivity
            android.os.Bundle arguments;
            arguments = new android.os.Bundle();
            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
            arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
            // Create intent to navigate to selected note on widget click
            android.content.Intent intent;
            intent = new android.content.Intent((Context) null, com.automattic.simplenote.NoteEditorActivity.class);
            intent.putExtras(arguments);
            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            android.app.PendingIntent pendingIntent;
            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
            // Remove title from content
            java.lang.String title;
            title = updatedNote.getTitle();
            java.lang.String contentWithoutTitle;
            contentWithoutTitle = updatedNote.getContent().replace(title, "");
            int indexOfNewline;
            indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
            java.lang.String content;
            content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
            // Set widget content
            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            android.text.SpannableStringBuilder contentSpan;
            contentSpan = new android.text.SpannableStringBuilder(content);
            contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
        } catch (com.simperium.client.BucketObjectMissingException e) {
            // Create intent to navigate to widget configure activity on widget click
            android.content.Intent intent;
            intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
            intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            android.app.PendingIntent pendingIntent;
            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
        }
        break;
    }
    // NoteWidgetLight_9_RandomActionIntentDefinitionOperatorMutator
    case 969: {
        try {
            // Update note
            com.automattic.simplenote.models.Note updatedNote;
            updatedNote = notesBucket.get(key);
            // Prepare bundle for NoteEditorActivity
            android.os.Bundle arguments;
            arguments = new android.os.Bundle();
            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
            arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
            // Create intent to navigate to selected note on widget click
            android.content.Intent intent;
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            intent.putExtras(arguments);
            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            android.app.PendingIntent pendingIntent;
            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
            // Remove title from content
            java.lang.String title;
            title = updatedNote.getTitle();
            java.lang.String contentWithoutTitle;
            contentWithoutTitle = updatedNote.getContent().replace(title, "");
            int indexOfNewline;
            indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
            java.lang.String content;
            content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
            // Set widget content
            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            android.text.SpannableStringBuilder contentSpan;
            contentSpan = new android.text.SpannableStringBuilder(content);
            contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
        } catch (com.simperium.client.BucketObjectMissingException e) {
            // Create intent to navigate to widget configure activity on widget click
            android.content.Intent intent;
            intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
            intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            android.app.PendingIntent pendingIntent;
            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
        }
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // NoteWidgetLight_10_RandomActionIntentDefinitionOperatorMutator
        case 1069: {
            try {
                // Update note
                com.automattic.simplenote.models.Note updatedNote;
                updatedNote = notesBucket.get(key);
                // Prepare bundle for NoteEditorActivity
                android.os.Bundle arguments;
                arguments = new android.os.Bundle();
                arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                // Create intent to navigate to selected note on widget click
                android.content.Intent intent;
                intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                new android.content.Intent(android.content.Intent.ACTION_SEND);;
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                android.app.PendingIntent pendingIntent;
                pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                // Remove title from content
                java.lang.String title;
                title = updatedNote.getTitle();
                java.lang.String contentWithoutTitle;
                contentWithoutTitle = updatedNote.getContent().replace(title, "");
                int indexOfNewline;
                indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                java.lang.String content;
                content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                // Set widget content
                views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                android.text.SpannableStringBuilder contentSpan;
                contentSpan = new android.text.SpannableStringBuilder(content);
                contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
            } catch (com.simperium.client.BucketObjectMissingException e) {
                // Create intent to navigate to widget configure activity on widget click
                android.content.Intent intent;
                intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                android.app.PendingIntent pendingIntent;
                pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
            }
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // NoteWidgetLight_11_NullValueIntentPutExtraOperatorMutator
            case 1169: {
                try {
                    // Update note
                    com.automattic.simplenote.models.Note updatedNote;
                    updatedNote = notesBucket.get(key);
                    // Prepare bundle for NoteEditorActivity
                    android.os.Bundle arguments;
                    arguments = new android.os.Bundle();
                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                    arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                    // Create intent to navigate to selected note on widget click
                    android.content.Intent intent;
                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                    intent.putExtras(arguments);
                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, new Parcelable[0]);
                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    android.app.PendingIntent pendingIntent;
                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                    // Remove title from content
                    java.lang.String title;
                    title = updatedNote.getTitle();
                    java.lang.String contentWithoutTitle;
                    contentWithoutTitle = updatedNote.getContent().replace(title, "");
                    int indexOfNewline;
                    indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                    java.lang.String content;
                    content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                    // Set widget content
                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                    android.text.SpannableStringBuilder contentSpan;
                    contentSpan = new android.text.SpannableStringBuilder(content);
                    contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                } catch (com.simperium.client.BucketObjectMissingException e) {
                    // Create intent to navigate to widget configure activity on widget click
                    android.content.Intent intent;
                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                    intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                    android.app.PendingIntent pendingIntent;
                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                    views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                }
                break;
            }
            // NoteWidgetLight_12_IntentPayloadReplacementOperatorMutator
            case 1269: {
                try {
                    // Update note
                    com.automattic.simplenote.models.Note updatedNote;
                    updatedNote = notesBucket.get(key);
                    // Prepare bundle for NoteEditorActivity
                    android.os.Bundle arguments;
                    arguments = new android.os.Bundle();
                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                    arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                    // Create intent to navigate to selected note on widget click
                    android.content.Intent intent;
                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                    intent.putExtras(arguments);
                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    android.app.PendingIntent pendingIntent;
                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                    // Remove title from content
                    java.lang.String title;
                    title = updatedNote.getTitle();
                    java.lang.String contentWithoutTitle;
                    contentWithoutTitle = updatedNote.getContent().replace(title, "");
                    int indexOfNewline;
                    indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                    java.lang.String content;
                    content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                    // Set widget content
                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                    android.text.SpannableStringBuilder contentSpan;
                    contentSpan = new android.text.SpannableStringBuilder(content);
                    contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                } catch (com.simperium.client.BucketObjectMissingException e) {
                    // Create intent to navigate to widget configure activity on widget click
                    android.content.Intent intent;
                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                    intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                    android.app.PendingIntent pendingIntent;
                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                    views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                }
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // NoteWidgetLight_13_RandomActionIntentDefinitionOperatorMutator
                case 1369: {
                    try {
                        // Update note
                        com.automattic.simplenote.models.Note updatedNote;
                        updatedNote = notesBucket.get(key);
                        // Prepare bundle for NoteEditorActivity
                        android.os.Bundle arguments;
                        arguments = new android.os.Bundle();
                        arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                        arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                        arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                        arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                        // Create intent to navigate to selected note on widget click
                        android.content.Intent intent;
                        intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                        intent.putExtras(arguments);
                        /**
                        * Inserted by Kadabra
                        */
                        /**
                        * Inserted by Kadabra
                        */
                        new android.content.Intent(android.content.Intent.ACTION_SEND);;
                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        android.app.PendingIntent pendingIntent;
                        pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                        // Remove title from content
                        java.lang.String title;
                        title = updatedNote.getTitle();
                        java.lang.String contentWithoutTitle;
                        contentWithoutTitle = updatedNote.getContent().replace(title, "");
                        int indexOfNewline;
                        indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                        java.lang.String content;
                        content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                        // Set widget content
                        views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                        views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                        views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                        android.text.SpannableStringBuilder contentSpan;
                        contentSpan = new android.text.SpannableStringBuilder(content);
                        contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                    } catch (com.simperium.client.BucketObjectMissingException e) {
                        // Create intent to navigate to widget configure activity on widget click
                        android.content.Intent intent;
                        intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                        intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                        android.app.PendingIntent pendingIntent;
                        pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                        views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                        views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                        views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                        views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                    }
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // NoteWidgetLight_14_RandomActionIntentDefinitionOperatorMutator
                    case 1469: {
                        try {
                            // Update note
                            com.automattic.simplenote.models.Note updatedNote;
                            updatedNote = notesBucket.get(key);
                            // Prepare bundle for NoteEditorActivity
                            android.os.Bundle arguments;
                            arguments = new android.os.Bundle();
                            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                            arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                            // Create intent to navigate to selected note on widget click
                            android.content.Intent intent;
                            intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                            intent.putExtras(arguments);
                            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                            /**
                            * Inserted by Kadabra
                            */
                            /**
                            * Inserted by Kadabra
                            */
                            new android.content.Intent(android.content.Intent.ACTION_SEND);;
                            android.app.PendingIntent pendingIntent;
                            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                            // Remove title from content
                            java.lang.String title;
                            title = updatedNote.getTitle();
                            java.lang.String contentWithoutTitle;
                            contentWithoutTitle = updatedNote.getContent().replace(title, "");
                            int indexOfNewline;
                            indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                            java.lang.String content;
                            content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                            // Set widget content
                            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                            android.text.SpannableStringBuilder contentSpan;
                            contentSpan = new android.text.SpannableStringBuilder(content);
                            contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                        } catch (com.simperium.client.BucketObjectMissingException e) {
                            // Create intent to navigate to widget configure activity on widget click
                            android.content.Intent intent;
                            intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                            intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                            android.app.PendingIntent pendingIntent;
                            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                        }
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // NoteWidgetLight_15_BinaryMutator
                        case 1569: {
                            try {
                                // Update note
                                com.automattic.simplenote.models.Note updatedNote;
                                updatedNote = notesBucket.get(key);
                                // Prepare bundle for NoteEditorActivity
                                android.os.Bundle arguments;
                                arguments = new android.os.Bundle();
                                arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                // Create intent to navigate to selected note on widget click
                                android.content.Intent intent;
                                intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                intent.putExtras(arguments);
                                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                android.app.PendingIntent pendingIntent;
                                pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                // Remove title from content
                                java.lang.String title;
                                title = updatedNote.getTitle();
                                java.lang.String contentWithoutTitle;
                                contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                int indexOfNewline;
                                indexOfNewline = contentWithoutTitle.indexOf("\n") - 1;
                                java.lang.String content;
                                content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                // Set widget content
                                views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                android.text.SpannableStringBuilder contentSpan;
                                contentSpan = new android.text.SpannableStringBuilder(content);
                                contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                            } catch (com.simperium.client.BucketObjectMissingException e) {
                                // Create intent to navigate to widget configure activity on widget click
                                android.content.Intent intent;
                                intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                                intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                                android.app.PendingIntent pendingIntent;
                                pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                            }
                            break;
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // NoteWidgetLight_16_NullIntentOperatorMutator
                            case 1669: {
                                try {
                                    // Update note
                                    com.automattic.simplenote.models.Note updatedNote;
                                    updatedNote = notesBucket.get(key);
                                    // Prepare bundle for NoteEditorActivity
                                    android.os.Bundle arguments;
                                    arguments = new android.os.Bundle();
                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                    arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                    // Create intent to navigate to selected note on widget click
                                    android.content.Intent intent;
                                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                    intent.putExtras(arguments);
                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    android.app.PendingIntent pendingIntent;
                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                    // Remove title from content
                                    java.lang.String title;
                                    title = updatedNote.getTitle();
                                    java.lang.String contentWithoutTitle;
                                    contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                    int indexOfNewline;
                                    indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                    java.lang.String content;
                                    content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                    // Set widget content
                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    android.text.SpannableStringBuilder contentSpan;
                                    contentSpan = new android.text.SpannableStringBuilder(content);
                                    contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                } catch (com.simperium.client.BucketObjectMissingException e) {
                                    // Create intent to navigate to widget configure activity on widget click
                                    android.content.Intent intent;
                                    intent = null;
                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                                    intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                                    android.app.PendingIntent pendingIntent;
                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                }
                                break;
                            }
                            // NoteWidgetLight_17_InvalidKeyIntentOperatorMutator
                            case 1769: {
                                try {
                                    // Update note
                                    com.automattic.simplenote.models.Note updatedNote;
                                    updatedNote = notesBucket.get(key);
                                    // Prepare bundle for NoteEditorActivity
                                    android.os.Bundle arguments;
                                    arguments = new android.os.Bundle();
                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                    arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                    // Create intent to navigate to selected note on widget click
                                    android.content.Intent intent;
                                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                    intent.putExtras(arguments);
                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    android.app.PendingIntent pendingIntent;
                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                    // Remove title from content
                                    java.lang.String title;
                                    title = updatedNote.getTitle();
                                    java.lang.String contentWithoutTitle;
                                    contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                    int indexOfNewline;
                                    indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                    java.lang.String content;
                                    content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                    // Set widget content
                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    android.text.SpannableStringBuilder contentSpan;
                                    contentSpan = new android.text.SpannableStringBuilder(content);
                                    contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                } catch (com.simperium.client.BucketObjectMissingException e) {
                                    // Create intent to navigate to widget configure activity on widget click
                                    android.content.Intent intent;
                                    intent = new android.content.Intent((Context) null, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                                    intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                                    android.app.PendingIntent pendingIntent;
                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                }
                                break;
                            }
                            // NoteWidgetLight_18_RandomActionIntentDefinitionOperatorMutator
                            case 1869: {
                                try {
                                    // Update note
                                    com.automattic.simplenote.models.Note updatedNote;
                                    updatedNote = notesBucket.get(key);
                                    // Prepare bundle for NoteEditorActivity
                                    android.os.Bundle arguments;
                                    arguments = new android.os.Bundle();
                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                    arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                    // Create intent to navigate to selected note on widget click
                                    android.content.Intent intent;
                                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                    intent.putExtras(arguments);
                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    android.app.PendingIntent pendingIntent;
                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                    // Remove title from content
                                    java.lang.String title;
                                    title = updatedNote.getTitle();
                                    java.lang.String contentWithoutTitle;
                                    contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                    int indexOfNewline;
                                    indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                    java.lang.String content;
                                    content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                    // Set widget content
                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    android.text.SpannableStringBuilder contentSpan;
                                    contentSpan = new android.text.SpannableStringBuilder(content);
                                    contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                } catch (com.simperium.client.BucketObjectMissingException e) {
                                    // Create intent to navigate to widget configure activity on widget click
                                    android.content.Intent intent;
                                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                                    intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                                    android.app.PendingIntent pendingIntent;
                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                    views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                }
                                break;
                            }
                            default: {
                            switch(MUID_STATIC) {
                                // NoteWidgetLight_19_RandomActionIntentDefinitionOperatorMutator
                                case 1969: {
                                    try {
                                        // Update note
                                        com.automattic.simplenote.models.Note updatedNote;
                                        updatedNote = notesBucket.get(key);
                                        // Prepare bundle for NoteEditorActivity
                                        android.os.Bundle arguments;
                                        arguments = new android.os.Bundle();
                                        arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                        arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                        arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                        arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                        // Create intent to navigate to selected note on widget click
                                        android.content.Intent intent;
                                        intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                        intent.putExtras(arguments);
                                        intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        android.app.PendingIntent pendingIntent;
                                        pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                        // Remove title from content
                                        java.lang.String title;
                                        title = updatedNote.getTitle();
                                        java.lang.String contentWithoutTitle;
                                        contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                        int indexOfNewline;
                                        indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                        java.lang.String content;
                                        content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                        // Set widget content
                                        views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                        views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                        views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                        android.text.SpannableStringBuilder contentSpan;
                                        contentSpan = new android.text.SpannableStringBuilder(content);
                                        contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                    } catch (com.simperium.client.BucketObjectMissingException e) {
                                        // Create intent to navigate to widget configure activity on widget click
                                        android.content.Intent intent;
                                        intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                                        /**
                                        * Inserted by Kadabra
                                        */
                                        /**
                                        * Inserted by Kadabra
                                        */
                                        new android.content.Intent(android.content.Intent.ACTION_SEND);;
                                        intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                                        intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                                        android.app.PendingIntent pendingIntent;
                                        pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                        views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                        views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                        views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                        views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                    }
                                    break;
                                }
                                default: {
                                switch(MUID_STATIC) {
                                    // NoteWidgetLight_20_NullValueIntentPutExtraOperatorMutator
                                    case 2069: {
                                        try {
                                            // Update note
                                            com.automattic.simplenote.models.Note updatedNote;
                                            updatedNote = notesBucket.get(key);
                                            // Prepare bundle for NoteEditorActivity
                                            android.os.Bundle arguments;
                                            arguments = new android.os.Bundle();
                                            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                            arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                            // Create intent to navigate to selected note on widget click
                                            android.content.Intent intent;
                                            intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                            intent.putExtras(arguments);
                                            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            android.app.PendingIntent pendingIntent;
                                            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                            // Remove title from content
                                            java.lang.String title;
                                            title = updatedNote.getTitle();
                                            java.lang.String contentWithoutTitle;
                                            contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                            int indexOfNewline;
                                            indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                            java.lang.String content;
                                            content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                            // Set widget content
                                            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                            android.text.SpannableStringBuilder contentSpan;
                                            contentSpan = new android.text.SpannableStringBuilder(content);
                                            contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                        } catch (com.simperium.client.BucketObjectMissingException e) {
                                            // Create intent to navigate to widget configure activity on widget click
                                            android.content.Intent intent;
                                            intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                                            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, new Parcelable[0]);
                                            intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                                            android.app.PendingIntent pendingIntent;
                                            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                        }
                                        break;
                                    }
                                    // NoteWidgetLight_21_IntentPayloadReplacementOperatorMutator
                                    case 2169: {
                                        try {
                                            // Update note
                                            com.automattic.simplenote.models.Note updatedNote;
                                            updatedNote = notesBucket.get(key);
                                            // Prepare bundle for NoteEditorActivity
                                            android.os.Bundle arguments;
                                            arguments = new android.os.Bundle();
                                            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                            arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                            arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                            // Create intent to navigate to selected note on widget click
                                            android.content.Intent intent;
                                            intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                            intent.putExtras(arguments);
                                            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            android.app.PendingIntent pendingIntent;
                                            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                            // Remove title from content
                                            java.lang.String title;
                                            title = updatedNote.getTitle();
                                            java.lang.String contentWithoutTitle;
                                            contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                            int indexOfNewline;
                                            indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                            java.lang.String content;
                                            content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                            // Set widget content
                                            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                            android.text.SpannableStringBuilder contentSpan;
                                            contentSpan = new android.text.SpannableStringBuilder(content);
                                            contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                        } catch (com.simperium.client.BucketObjectMissingException e) {
                                            // Create intent to navigate to widget configure activity on widget click
                                            android.content.Intent intent;
                                            intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                                            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
                                            intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                                            android.app.PendingIntent pendingIntent;
                                            pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                            views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                            views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                            views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                            views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                            views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                        }
                                        break;
                                    }
                                    default: {
                                    switch(MUID_STATIC) {
                                        // NoteWidgetLight_22_RandomActionIntentDefinitionOperatorMutator
                                        case 2269: {
                                            try {
                                                // Update note
                                                com.automattic.simplenote.models.Note updatedNote;
                                                updatedNote = notesBucket.get(key);
                                                // Prepare bundle for NoteEditorActivity
                                                android.os.Bundle arguments;
                                                arguments = new android.os.Bundle();
                                                arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                                arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                                arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                                arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                                // Create intent to navigate to selected note on widget click
                                                android.content.Intent intent;
                                                intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                                intent.putExtras(arguments);
                                                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                android.app.PendingIntent pendingIntent;
                                                pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                                // Remove title from content
                                                java.lang.String title;
                                                title = updatedNote.getTitle();
                                                java.lang.String contentWithoutTitle;
                                                contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                                int indexOfNewline;
                                                indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                                java.lang.String content;
                                                content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                                // Set widget content
                                                views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                                views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                                views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                                views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                android.text.SpannableStringBuilder contentSpan;
                                                contentSpan = new android.text.SpannableStringBuilder(content);
                                                contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                                views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                            } catch (com.simperium.client.BucketObjectMissingException e) {
                                                // Create intent to navigate to widget configure activity on widget click
                                                android.content.Intent intent;
                                                intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                                                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                /**
                                                * Inserted by Kadabra
                                                */
                                                /**
                                                * Inserted by Kadabra
                                                */
                                                new android.content.Intent(android.content.Intent.ACTION_SEND);;
                                                intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                                                android.app.PendingIntent pendingIntent;
                                                pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                                views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                                views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                                views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                                views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                            }
                                            break;
                                        }
                                        default: {
                                        switch(MUID_STATIC) {
                                            // NoteWidgetLight_23_NullValueIntentPutExtraOperatorMutator
                                            case 2369: {
                                                try {
                                                    // Update note
                                                    com.automattic.simplenote.models.Note updatedNote;
                                                    updatedNote = notesBucket.get(key);
                                                    // Prepare bundle for NoteEditorActivity
                                                    android.os.Bundle arguments;
                                                    arguments = new android.os.Bundle();
                                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                                    arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                                    // Create intent to navigate to selected note on widget click
                                                    android.content.Intent intent;
                                                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                                    intent.putExtras(arguments);
                                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    android.app.PendingIntent pendingIntent;
                                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                                    // Remove title from content
                                                    java.lang.String title;
                                                    title = updatedNote.getTitle();
                                                    java.lang.String contentWithoutTitle;
                                                    contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                                    int indexOfNewline;
                                                    indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                                    java.lang.String content;
                                                    content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                                    // Set widget content
                                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    android.text.SpannableStringBuilder contentSpan;
                                                    contentSpan = new android.text.SpannableStringBuilder(content);
                                                    contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                                } catch (com.simperium.client.BucketObjectMissingException e) {
                                                    // Create intent to navigate to widget configure activity on widget click
                                                    android.content.Intent intent;
                                                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                                                    intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, new Parcelable[0]);
                                                    android.app.PendingIntent pendingIntent;
                                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                                }
                                                break;
                                            }
                                            // NoteWidgetLight_24_IntentPayloadReplacementOperatorMutator
                                            case 2469: {
                                                try {
                                                    // Update note
                                                    com.automattic.simplenote.models.Note updatedNote;
                                                    updatedNote = notesBucket.get(key);
                                                    // Prepare bundle for NoteEditorActivity
                                                    android.os.Bundle arguments;
                                                    arguments = new android.os.Bundle();
                                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                                    arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                                    // Create intent to navigate to selected note on widget click
                                                    android.content.Intent intent;
                                                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                                    intent.putExtras(arguments);
                                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    android.app.PendingIntent pendingIntent;
                                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                                    // Remove title from content
                                                    java.lang.String title;
                                                    title = updatedNote.getTitle();
                                                    java.lang.String contentWithoutTitle;
                                                    contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                                    int indexOfNewline;
                                                    indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                                    java.lang.String content;
                                                    content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                                    // Set widget content
                                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    android.text.SpannableStringBuilder contentSpan;
                                                    contentSpan = new android.text.SpannableStringBuilder(content);
                                                    contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                                } catch (com.simperium.client.BucketObjectMissingException e) {
                                                    // Create intent to navigate to widget configure activity on widget click
                                                    android.content.Intent intent;
                                                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                                                    intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
                                                    android.app.PendingIntent pendingIntent;
                                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                                }
                                                break;
                                            }
                                            default: {
                                            switch(MUID_STATIC) {
                                                // NoteWidgetLight_25_RandomActionIntentDefinitionOperatorMutator
                                                case 2569: {
                                                    try {
                                                        // Update note
                                                        com.automattic.simplenote.models.Note updatedNote;
                                                        updatedNote = notesBucket.get(key);
                                                        // Prepare bundle for NoteEditorActivity
                                                        android.os.Bundle arguments;
                                                        arguments = new android.os.Bundle();
                                                        arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                                        arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                                        arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                                        arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                                        // Create intent to navigate to selected note on widget click
                                                        android.content.Intent intent;
                                                        intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                                        intent.putExtras(arguments);
                                                        intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        android.app.PendingIntent pendingIntent;
                                                        pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                                        // Remove title from content
                                                        java.lang.String title;
                                                        title = updatedNote.getTitle();
                                                        java.lang.String contentWithoutTitle;
                                                        contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                                        int indexOfNewline;
                                                        indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                                        java.lang.String content;
                                                        content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                                        // Set widget content
                                                        views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                                        views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                                        views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                        android.text.SpannableStringBuilder contentSpan;
                                                        contentSpan = new android.text.SpannableStringBuilder(content);
                                                        contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                                    } catch (com.simperium.client.BucketObjectMissingException e) {
                                                        // Create intent to navigate to widget configure activity on widget click
                                                        android.content.Intent intent;
                                                        intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                                                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                                                        /**
                                                        * Inserted by Kadabra
                                                        */
                                                        /**
                                                        * Inserted by Kadabra
                                                        */
                                                        new android.content.Intent(android.content.Intent.ACTION_SEND);;
                                                        android.app.PendingIntent pendingIntent;
                                                        pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                                        views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                                        views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                        views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                                        views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                        views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                                    }
                                                    break;
                                                }
                                                default: {
                                                try {
                                                    // Update note
                                                    com.automattic.simplenote.models.Note updatedNote;
                                                    updatedNote = notesBucket.get(key);
                                                    // Prepare bundle for NoteEditorActivity
                                                    android.os.Bundle arguments;
                                                    arguments = new android.os.Bundle();
                                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                                                    arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, updatedNote.getSimperiumKey());
                                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, updatedNote.isMarkdownEnabled());
                                                    arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, updatedNote.isPreviewEnabled());
                                                    // Create intent to navigate to selected note on widget click
                                                    android.content.Intent intent;
                                                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
                                                    intent.putExtras(arguments);
                                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
                                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    android.app.PendingIntent pendingIntent;
                                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                                    // Remove title from content
                                                    java.lang.String title;
                                                    title = updatedNote.getTitle();
                                                    java.lang.String contentWithoutTitle;
                                                    contentWithoutTitle = updatedNote.getContent().replace(title, "");
                                                    int indexOfNewline;
                                                    indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
                                                    java.lang.String content;
                                                    content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
                                                    // Set widget content
                                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    android.text.SpannableStringBuilder contentSpan;
                                                    contentSpan = new android.text.SpannableStringBuilder(content);
                                                    contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
                                                } catch (com.simperium.client.BucketObjectMissingException e) {
                                                    // Create intent to navigate to widget configure activity on widget click
                                                    android.content.Intent intent;
                                                    intent = new android.content.Intent(context, com.automattic.simplenote.NoteWidgetLightConfigureActivity.class);
                                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED);
                                                    intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                                                    android.app.PendingIntent pendingIntent;
                                                    pendingIntent = android.app.PendingIntent.getActivity(context, appWidgetId, intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
                                                    views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
                                                    views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
                                                    views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        break;
    }
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
} else {
views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, null);
views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
views.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getString(com.automattic.simplenote.R.string.note_not_found));
views.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
views.setViewVisibility(com.automattic.simplenote.R.id.widget_text_content, android.view.View.GONE);
}
}
appWidgetManager.updateAppWidget(appWidgetId, views);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
