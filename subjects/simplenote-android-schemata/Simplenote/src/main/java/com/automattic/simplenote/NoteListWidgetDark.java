package com.automattic.simplenote;
import com.simperium.client.User;
import com.simperium.client.Query;
import android.appwidget.AppWidgetProvider;
import com.simperium.Simperium;
import static com.automattic.simplenote.utils.WidgetUtils.MINIMUM_WIDTH_FOR_BUTTON;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_TAPPED;
import static com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_SIGN_IN_TAPPED;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_FIRST_ADDED;
import com.automattic.simplenote.utils.PrefUtils;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_LAST_DELETED;
import android.os.Bundle;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_DELETED;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import static com.automattic.simplenote.utils.WidgetUtils.MINIMUM_HEIGHT_FOR_BUTTON;
import android.view.View;
import static com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK;
import android.widget.RemoteViews;
import com.simperium.client.Bucket;
import com.automattic.simplenote.models.Note;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_BUTTON_TAPPED;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
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
public class NoteListWidgetDark extends android.appwidget.AppWidgetProvider {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String KEY_LIST_WIDGET_IDS_DARK = "key_list_widget_ids_dark";

    @java.lang.Override
    public void onAppWidgetOptionsChanged(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int appWidgetId, android.os.Bundle newOptions) {
        android.widget.RemoteViews views;
        views = new android.widget.RemoteViews(context.getPackageName(), com.automattic.simplenote.utils.PrefUtils.getLayoutWidgetList(context, false));
        if (((com.automattic.simplenote.Simplenote) (context.getApplicationContext())).getSimperium().getUser().getStatus().equals(com.simperium.client.User.Status.AUTHORIZED)) {
            resizeWidget(context, newOptions, views);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @java.lang.Override
    public void onDeleted(android.content.Context context, int[] appWidgetIds) {
        com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_DELETED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET, "note_list_widget_deleted");
    }


    @java.lang.Override
    public void onEnabled(android.content.Context context) {
        com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_FIRST_ADDED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET, "note_list_widget_first_added");
    }


    @java.lang.Override
    public void onDisabled(android.content.Context context) {
        com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_LAST_DELETED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET, "note_list_widget_last_deleted");
    }


    @java.lang.Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if ((intent.getExtras() != null) && intent.hasExtra(com.automattic.simplenote.NoteListWidgetDark.KEY_LIST_WIDGET_IDS_DARK)) {
            int[] ids;
            ids = intent.getExtras().getIntArray(com.automattic.simplenote.NoteListWidgetDark.KEY_LIST_WIDGET_IDS_DARK);
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


    private void resizeWidget(android.content.Context context, android.os.Bundle appWidgetOptions, android.widget.RemoteViews views) {
        // Show/Hide add button based on widget height and width
        if ((appWidgetOptions.getInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT) > com.automattic.simplenote.utils.WidgetUtils.MINIMUM_HEIGHT_FOR_BUTTON) && (appWidgetOptions.getInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH) > com.automattic.simplenote.utils.WidgetUtils.MINIMUM_WIDTH_FOR_BUTTON)) {
            views.setViewPadding(com.automattic.simplenote.R.id.widget_list, 0, 0, 0, context.getResources().getDimensionPixelSize(com.automattic.simplenote.R.dimen.note_list_item_padding_bottom_button_widget));
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_button, android.view.View.VISIBLE);
        } else {
            views.setViewPadding(com.automattic.simplenote.R.id.widget_list, 0, 0, 0, 0);
            views.setViewVisibility(com.automattic.simplenote.R.id.widget_button, android.view.View.GONE);
        }
    }


    @android.annotation.SuppressLint("UnspecifiedImmutableFlag")
    private void updateWidget(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int appWidgetId, android.os.Bundle appWidgetOptions) {
        android.widget.RemoteViews views;
        views = new android.widget.RemoteViews(context.getPackageName(), com.automattic.simplenote.utils.PrefUtils.getLayoutWidgetList(context, false));
        resizeWidget(context, appWidgetOptions, views);
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
                // NoteListWidgetDark_0_NullIntentOperatorMutator
                case 75: {
                    intent = null;
                    break;
                }
                // NoteListWidgetDark_1_InvalidKeyIntentOperatorMutator
                case 175: {
                    intent = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
                    break;
                }
                // NoteListWidgetDark_2_RandomActionIntentDefinitionOperatorMutator
                case 275: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
                break;
            }
        }
        switch(MUID_STATIC) {
            // NoteListWidgetDark_3_NullValueIntentPutExtraOperatorMutator
            case 375: {
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
                break;
            }
            // NoteListWidgetDark_4_IntentPayloadReplacementOperatorMutator
            case 475: {
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // NoteListWidgetDark_5_RandomActionIntentDefinitionOperatorMutator
                case 575: {
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
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_SIGN_IN_TAPPED);
                break;
            }
        }
        break;
    }
}
switch(MUID_STATIC) {
    // NoteListWidgetDark_6_RandomActionIntentDefinitionOperatorMutator
    case 675: {
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
// Reset intent to navigate to note editor on note list add button click to navigate to notes activity, which redirects to login/signup
android.content.Intent intentButton;
switch(MUID_STATIC) {
// NoteListWidgetDark_7_NullIntentOperatorMutator
case 775: {
    intentButton = null;
    break;
}
// NoteListWidgetDark_8_InvalidKeyIntentOperatorMutator
case 875: {
    intentButton = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
    break;
}
// NoteListWidgetDark_9_RandomActionIntentDefinitionOperatorMutator
case 975: {
    intentButton = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intentButton = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
break;
}
}
views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_button, android.app.PendingIntent.getActivity(context, appWidgetId, intentButton, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE));
views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.log_in_use_widget));
views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_dark, context.getTheme()));
views.setViewVisibility(com.automattic.simplenote.R.id.widget_text, android.view.View.VISIBLE);
views.setViewVisibility(com.automattic.simplenote.R.id.widget_list, android.view.View.GONE);
views.setViewVisibility(com.automattic.simplenote.R.id.widget_button, android.view.View.GONE);
} else {
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket;
notesBucket = currentApp.getNotesBucket();
com.simperium.client.Query<com.automattic.simplenote.models.Note> query;
query = com.automattic.simplenote.models.Note.all(notesBucket);
query.include(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME, com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME);
com.automattic.simplenote.utils.PrefUtils.sortNoteQuery(query, context, true);
com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> cursor;
cursor = query.execute();
if (cursor.getCount() > 0) {
// Create intent to navigate to notes activity on widget click while loading
android.content.Intent intentLoading;
switch(MUID_STATIC) {
// NoteListWidgetDark_10_NullIntentOperatorMutator
case 1075: {
    intentLoading = null;
    break;
}
// NoteListWidgetDark_11_InvalidKeyIntentOperatorMutator
case 1175: {
    intentLoading = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
    break;
}
// NoteListWidgetDark_12_RandomActionIntentDefinitionOperatorMutator
case 1275: {
    intentLoading = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intentLoading = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetDark_13_NullValueIntentPutExtraOperatorMutator
case 1375: {
intentLoading.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
break;
}
// NoteListWidgetDark_14_IntentPayloadReplacementOperatorMutator
case 1475: {
intentLoading.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetDark_15_RandomActionIntentDefinitionOperatorMutator
case 1575: {
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
intentLoading.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_TAPPED);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetDark_16_RandomActionIntentDefinitionOperatorMutator
case 1675: {
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
intentLoading.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
break;
}
}
android.app.PendingIntent pendingIntentLoading;
pendingIntentLoading = android.app.PendingIntent.getActivity(context, appWidgetId, intentLoading, android.app.PendingIntent.FLAG_IMMUTABLE);
views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntentLoading);
// Create intent for note list widget service
android.content.Intent intent;
switch(MUID_STATIC) {
// NoteListWidgetDark_17_NullIntentOperatorMutator
case 1775: {
intent = null;
break;
}
// NoteListWidgetDark_18_InvalidKeyIntentOperatorMutator
case 1875: {
intent = new android.content.Intent((Context) null, com.automattic.simplenote.NoteListWidgetDarkService.class);
break;
}
// NoteListWidgetDark_19_RandomActionIntentDefinitionOperatorMutator
case 1975: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(context, com.automattic.simplenote.NoteListWidgetDarkService.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetDark_20_NullValueIntentPutExtraOperatorMutator
case 2075: {
intent.putExtra(com.automattic.simplenote.NoteListWidgetFactory.EXTRA_IS_LIGHT, new Parcelable[0]);
break;
}
// NoteListWidgetDark_21_IntentPayloadReplacementOperatorMutator
case 2175: {
intent.putExtra(com.automattic.simplenote.NoteListWidgetFactory.EXTRA_IS_LIGHT, true);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetDark_22_RandomActionIntentDefinitionOperatorMutator
case 2275: {
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
intent.putExtra(com.automattic.simplenote.NoteListWidgetFactory.EXTRA_IS_LIGHT, false);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetDark_23_NullValueIntentPutExtraOperatorMutator
case 2375: {
intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, new Parcelable[0]);
break;
}
// NoteListWidgetDark_24_IntentPayloadReplacementOperatorMutator
case 2475: {
intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetDark_25_RandomActionIntentDefinitionOperatorMutator
case 2575: {
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
intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
break;
}
}
break;
}
}
views.setRemoteAdapter(com.automattic.simplenote.R.id.widget_list, intent);
// Create intent to navigate to note editor on note list item click
android.content.Intent intentItem;
switch(MUID_STATIC) {
// NoteListWidgetDark_26_NullIntentOperatorMutator
case 2675: {
intentItem = null;
break;
}
// NoteListWidgetDark_27_InvalidKeyIntentOperatorMutator
case 2775: {
intentItem = new android.content.Intent((Context) null, com.automattic.simplenote.NoteEditorActivity.class);
break;
}
// NoteListWidgetDark_28_RandomActionIntentDefinitionOperatorMutator
case 2875: {
intentItem = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intentItem = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
break;
}
}
android.app.PendingIntent pendingIntentItem;
pendingIntentItem = null;
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
pendingIntentItem = android.app.PendingIntent.getActivity(context, appWidgetId, intentItem, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_MUTABLE);
} else {
pendingIntentItem = android.app.PendingIntent.getActivity(context, appWidgetId, intentItem, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
}
views.setPendingIntentTemplate(com.automattic.simplenote.R.id.widget_list, pendingIntentItem);
// Create intent to navigate to note editor on note list add button click
android.content.Intent intentButton;
switch(MUID_STATIC) {
// NoteListWidgetDark_29_NullIntentOperatorMutator
case 2975: {
intentButton = null;
break;
}
// NoteListWidgetDark_30_InvalidKeyIntentOperatorMutator
case 3075: {
intentButton = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
break;
}
// NoteListWidgetDark_31_RandomActionIntentDefinitionOperatorMutator
case 3175: {
intentButton = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intentButton = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetDark_32_NullValueIntentPutExtraOperatorMutator
case 3275: {
intentButton.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
break;
}
// NoteListWidgetDark_33_IntentPayloadReplacementOperatorMutator
case 3375: {
intentButton.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetDark_34_RandomActionIntentDefinitionOperatorMutator
case 3475: {
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
intentButton.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_BUTTON_TAPPED);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetDark_35_RandomActionIntentDefinitionOperatorMutator
case 3575: {
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
intentButton.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
break;
}
}
android.app.PendingIntent pendingIntentButton;
pendingIntentButton = android.app.PendingIntent.getActivity(context, appWidgetId, intentButton, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_button, pendingIntentButton);
views.setEmptyView(com.automattic.simplenote.R.id.widget_list, com.automattic.simplenote.R.id.widget_text);
views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_dark, context.getTheme()));
views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.empty_notes_widget));
views.setViewVisibility(com.automattic.simplenote.R.id.widget_text, android.view.View.GONE);
views.setViewVisibility(com.automattic.simplenote.R.id.widget_list, android.view.View.VISIBLE);
} else {
// Create intent to navigate to notes activity on widget click
android.content.Intent intent;
switch(MUID_STATIC) {
// NoteListWidgetDark_36_NullIntentOperatorMutator
case 3675: {
intent = null;
break;
}
// NoteListWidgetDark_37_InvalidKeyIntentOperatorMutator
case 3775: {
intent = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
break;
}
// NoteListWidgetDark_38_RandomActionIntentDefinitionOperatorMutator
case 3875: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetDark_39_NullValueIntentPutExtraOperatorMutator
case 3975: {
intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
break;
}
// NoteListWidgetDark_40_IntentPayloadReplacementOperatorMutator
case 4075: {
intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetDark_41_RandomActionIntentDefinitionOperatorMutator
case 4175: {
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
intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_TAPPED);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetDark_42_RandomActionIntentDefinitionOperatorMutator
case 4275: {
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
// Create intent to navigate to note editor on note list add button click
android.content.Intent intentButton;
switch(MUID_STATIC) {
// NoteListWidgetDark_43_NullIntentOperatorMutator
case 4375: {
intentButton = null;
break;
}
// NoteListWidgetDark_44_InvalidKeyIntentOperatorMutator
case 4475: {
intentButton = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
break;
}
// NoteListWidgetDark_45_RandomActionIntentDefinitionOperatorMutator
case 4575: {
intentButton = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intentButton = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetDark_46_NullValueIntentPutExtraOperatorMutator
case 4675: {
intentButton.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
break;
}
// NoteListWidgetDark_47_IntentPayloadReplacementOperatorMutator
case 4775: {
intentButton.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetDark_48_RandomActionIntentDefinitionOperatorMutator
case 4875: {
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
intentButton.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_LIST_WIDGET_BUTTON_TAPPED);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetDark_49_RandomActionIntentDefinitionOperatorMutator
case 4975: {
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
intentButton.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
break;
}
}
android.app.PendingIntent pendingIntentButton;
pendingIntentButton = android.app.PendingIntent.getActivity(context, appWidgetId, intentButton, android.app.PendingIntent.FLAG_UPDATE_CURRENT | android.app.PendingIntent.FLAG_IMMUTABLE);
views.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_button, pendingIntentButton);
views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_dark, context.getTheme()));
views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.empty_notes_widget));
views.setViewVisibility(com.automattic.simplenote.R.id.widget_text, android.view.View.VISIBLE);
views.setViewVisibility(com.automattic.simplenote.R.id.widget_list, android.view.View.GONE);
}
}
appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, com.automattic.simplenote.R.id.widget_list);
appWidgetManager.updateAppWidget(appWidgetId, views);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
