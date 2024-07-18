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
public class NoteListWidgetLight extends android.appwidget.AppWidgetProvider {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String KEY_LIST_WIDGET_IDS_LIGHT = "key_list_widget_ids_light";

    @java.lang.Override
    public void onAppWidgetOptionsChanged(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int appWidgetId, android.os.Bundle newOptions) {
        android.widget.RemoteViews views;
        views = new android.widget.RemoteViews(context.getPackageName(), com.automattic.simplenote.utils.PrefUtils.getLayoutWidgetList(context, true));
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
        if ((intent.getExtras() != null) && intent.hasExtra(com.automattic.simplenote.NoteListWidgetLight.KEY_LIST_WIDGET_IDS_LIGHT)) {
            int[] ids;
            ids = intent.getExtras().getIntArray(com.automattic.simplenote.NoteListWidgetLight.KEY_LIST_WIDGET_IDS_LIGHT);
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
        views = new android.widget.RemoteViews(context.getPackageName(), com.automattic.simplenote.utils.PrefUtils.getLayoutWidgetList(context, true));
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
                // NoteListWidgetLight_0_NullIntentOperatorMutator
                case 73: {
                    intent = null;
                    break;
                }
                // NoteListWidgetLight_1_InvalidKeyIntentOperatorMutator
                case 173: {
                    intent = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
                    break;
                }
                // NoteListWidgetLight_2_RandomActionIntentDefinitionOperatorMutator
                case 273: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
                break;
            }
        }
        switch(MUID_STATIC) {
            // NoteListWidgetLight_3_NullValueIntentPutExtraOperatorMutator
            case 373: {
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
                break;
            }
            // NoteListWidgetLight_4_IntentPayloadReplacementOperatorMutator
            case 473: {
                intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // NoteListWidgetLight_5_RandomActionIntentDefinitionOperatorMutator
                case 573: {
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
    // NoteListWidgetLight_6_RandomActionIntentDefinitionOperatorMutator
    case 673: {
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
// NoteListWidgetLight_7_NullIntentOperatorMutator
case 773: {
    intentButton = null;
    break;
}
// NoteListWidgetLight_8_InvalidKeyIntentOperatorMutator
case 873: {
    intentButton = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
    break;
}
// NoteListWidgetLight_9_RandomActionIntentDefinitionOperatorMutator
case 973: {
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
views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
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
// NoteListWidgetLight_10_NullIntentOperatorMutator
case 1073: {
    intentLoading = null;
    break;
}
// NoteListWidgetLight_11_InvalidKeyIntentOperatorMutator
case 1173: {
    intentLoading = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
    break;
}
// NoteListWidgetLight_12_RandomActionIntentDefinitionOperatorMutator
case 1273: {
    intentLoading = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intentLoading = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetLight_13_NullValueIntentPutExtraOperatorMutator
case 1373: {
intentLoading.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
break;
}
// NoteListWidgetLight_14_IntentPayloadReplacementOperatorMutator
case 1473: {
intentLoading.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetLight_15_RandomActionIntentDefinitionOperatorMutator
case 1573: {
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
// NoteListWidgetLight_16_RandomActionIntentDefinitionOperatorMutator
case 1673: {
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
// NoteListWidgetLight_17_NullIntentOperatorMutator
case 1773: {
intent = null;
break;
}
// NoteListWidgetLight_18_InvalidKeyIntentOperatorMutator
case 1873: {
intent = new android.content.Intent((Context) null, com.automattic.simplenote.NoteListWidgetLightService.class);
break;
}
// NoteListWidgetLight_19_RandomActionIntentDefinitionOperatorMutator
case 1973: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(context, com.automattic.simplenote.NoteListWidgetLightService.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetLight_20_NullValueIntentPutExtraOperatorMutator
case 2073: {
intent.putExtra(com.automattic.simplenote.NoteListWidgetFactory.EXTRA_IS_LIGHT, new Parcelable[0]);
break;
}
// NoteListWidgetLight_21_IntentPayloadReplacementOperatorMutator
case 2173: {
intent.putExtra(com.automattic.simplenote.NoteListWidgetFactory.EXTRA_IS_LIGHT, true);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetLight_22_RandomActionIntentDefinitionOperatorMutator
case 2273: {
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
intent.putExtra(com.automattic.simplenote.NoteListWidgetFactory.EXTRA_IS_LIGHT, true);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetLight_23_NullValueIntentPutExtraOperatorMutator
case 2373: {
intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, new Parcelable[0]);
break;
}
// NoteListWidgetLight_24_IntentPayloadReplacementOperatorMutator
case 2473: {
intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetLight_25_RandomActionIntentDefinitionOperatorMutator
case 2573: {
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
// NoteListWidgetLight_26_NullIntentOperatorMutator
case 2673: {
intentItem = null;
break;
}
// NoteListWidgetLight_27_InvalidKeyIntentOperatorMutator
case 2773: {
intentItem = new android.content.Intent((Context) null, com.automattic.simplenote.NoteEditorActivity.class);
break;
}
// NoteListWidgetLight_28_RandomActionIntentDefinitionOperatorMutator
case 2873: {
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
// NoteListWidgetLight_29_NullIntentOperatorMutator
case 2973: {
intentButton = null;
break;
}
// NoteListWidgetLight_30_InvalidKeyIntentOperatorMutator
case 3073: {
intentButton = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
break;
}
// NoteListWidgetLight_31_RandomActionIntentDefinitionOperatorMutator
case 3173: {
intentButton = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intentButton = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetLight_32_NullValueIntentPutExtraOperatorMutator
case 3273: {
intentButton.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
break;
}
// NoteListWidgetLight_33_IntentPayloadReplacementOperatorMutator
case 3373: {
intentButton.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetLight_34_RandomActionIntentDefinitionOperatorMutator
case 3473: {
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
// NoteListWidgetLight_35_RandomActionIntentDefinitionOperatorMutator
case 3573: {
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
views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
views.setTextViewText(com.automattic.simplenote.R.id.widget_text, context.getResources().getString(com.automattic.simplenote.R.string.empty_notes_widget));
views.setViewVisibility(com.automattic.simplenote.R.id.widget_text, android.view.View.GONE);
views.setViewVisibility(com.automattic.simplenote.R.id.widget_list, android.view.View.VISIBLE);
} else {
// Create intent to navigate to notes activity on widget click
android.content.Intent intent;
switch(MUID_STATIC) {
// NoteListWidgetLight_36_NullIntentOperatorMutator
case 3673: {
intent = null;
break;
}
// NoteListWidgetLight_37_InvalidKeyIntentOperatorMutator
case 3773: {
intent = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
break;
}
// NoteListWidgetLight_38_RandomActionIntentDefinitionOperatorMutator
case 3873: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetLight_39_NullValueIntentPutExtraOperatorMutator
case 3973: {
intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
break;
}
// NoteListWidgetLight_40_IntentPayloadReplacementOperatorMutator
case 4073: {
intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetLight_41_RandomActionIntentDefinitionOperatorMutator
case 4173: {
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
// NoteListWidgetLight_42_RandomActionIntentDefinitionOperatorMutator
case 4273: {
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
// NoteListWidgetLight_43_NullIntentOperatorMutator
case 4373: {
intentButton = null;
break;
}
// NoteListWidgetLight_44_InvalidKeyIntentOperatorMutator
case 4473: {
intentButton = new android.content.Intent((Context) null, com.automattic.simplenote.NotesActivity.class);
break;
}
// NoteListWidgetLight_45_RandomActionIntentDefinitionOperatorMutator
case 4573: {
intentButton = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intentButton = new android.content.Intent(context, com.automattic.simplenote.NotesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteListWidgetLight_46_NullValueIntentPutExtraOperatorMutator
case 4673: {
intentButton.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, new Parcelable[0]);
break;
}
// NoteListWidgetLight_47_IntentPayloadReplacementOperatorMutator
case 4773: {
intentButton.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_LIST_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
break;
}
default: {
switch(MUID_STATIC) {
// NoteListWidgetLight_48_RandomActionIntentDefinitionOperatorMutator
case 4873: {
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
// NoteListWidgetLight_49_RandomActionIntentDefinitionOperatorMutator
case 4973: {
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
views.setTextColor(com.automattic.simplenote.R.id.widget_text, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
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
