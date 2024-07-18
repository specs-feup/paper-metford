package com.automattic.simplenote;
import androidx.appcompat.app.AlertDialog;
import com.simperium.client.User;
import com.simperium.client.Query;
import android.content.DialogInterface;
import com.automattic.simplenote.utils.ThemeUtils;
import com.simperium.Simperium;
import android.database.Cursor;
import android.widget.CursorAdapter;
import static com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.automattic.simplenote.utils.PrefUtils;
import android.content.SharedPreferences;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import android.os.Bundle;
import android.view.ViewGroup;
import android.appwidget.AppWidgetManager;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import static com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED;
import androidx.preference.PreferenceManager;
import android.view.View;
import com.automattic.simplenote.utils.ChecklistUtils;
import android.widget.RemoteViews;
import android.view.LayoutInflater;
import com.simperium.client.Bucket;
import com.automattic.simplenote.models.Note;
import static com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK;
import android.view.ContextThemeWrapper;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.text.SpannableStringBuilder;
import com.simperium.client.Bucket.ObjectCursor;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteWidgetLightConfigureActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    private android.appwidget.AppWidgetManager mWidgetManager;

    private com.automattic.simplenote.NoteWidgetLightConfigureActivity.NotesCursorAdapter mNotesAdapter;

    private android.widget.RemoteViews mRemoteViews;

    private com.automattic.simplenote.Simplenote mApplication;

    private int mAppWidgetId = android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

    public NoteWidgetLightConfigureActivity() {
        super();
    }


    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // NoteWidgetLightConfigureActivity_0_LengthyGUICreationOperatorMutator
            case 88: {
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
    // Set the result to CANCELED.  This will cause the widget host to cancel
    // out of the widget placement if the user presses the back button.
    setResult(android.app.Activity.RESULT_CANCELED);
    setContentView(com.automattic.simplenote.R.layout.note_widget_configure);
    // Verify user authentication.
    mApplication = ((com.automattic.simplenote.Simplenote) (getApplicationContext()));
    com.simperium.Simperium simperium;
    simperium = mApplication.getSimperium();
    com.simperium.client.User user;
    user = simperium.getUser();
    if (user.getStatus().equals(com.simperium.client.User.Status.NOT_AUTHORIZED)) {
        android.widget.Toast.makeText(this, com.automattic.simplenote.R.string.log_in_add_widget, android.widget.Toast.LENGTH_LONG).show();
        finish();
    }
    // Get widget information
    mWidgetManager = android.appwidget.AppWidgetManager.getInstance(this);
    mRemoteViews = new android.widget.RemoteViews(getPackageName(), com.automattic.simplenote.utils.PrefUtils.getLayoutWidget(this, true));
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // NoteWidgetLightConfigureActivity_1_RandomActionIntentDefinitionOperatorMutator
        case 188: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = getIntent();
        break;
    }
}
android.os.Bundle extras;
extras = intent.getExtras();
if (extras != null) {
    mAppWidgetId = extras.getInt(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID);
}
if (mAppWidgetId == android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID) {
    finish();
    return;
}
showDialog();
if ((intent.hasExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK) && (intent.getExtras() != null)) && (intent.getExtras().getSerializable(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK) == com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED)) {
    com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_NOT_FOUND_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_WIDGET, "note_widget_note_not_found_tapped");
}
}


private void showDialog() {
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> mNotesBucket;
mNotesBucket = mApplication.getNotesBucket();
com.simperium.client.Query<com.automattic.simplenote.models.Note> query;
query = com.automattic.simplenote.models.Note.all(mNotesBucket);
query.include(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME, com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME);
com.automattic.simplenote.utils.PrefUtils.sortNoteQuery(query, this, true);
com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> cursor;
cursor = query.execute();
android.content.Context context;
context = new android.view.ContextThemeWrapper(this, com.automattic.simplenote.utils.PrefUtils.getStyleWidgetDialog(this));
androidx.appcompat.app.AlertDialog.Builder builder;
builder = new androidx.appcompat.app.AlertDialog.Builder(context);
@android.annotation.SuppressLint("InflateParams")
final android.view.View layout;
layout = android.view.LayoutInflater.from(context).inflate(com.automattic.simplenote.R.layout.note_widget_configure_list, null);
final android.widget.ListView list;
switch(MUID_STATIC) {
    // NoteWidgetLightConfigureActivity_2_InvalidViewFocusOperatorMutator
    case 288: {
        /**
        * Inserted by Kadabra
        */
        list = layout.findViewById(com.automattic.simplenote.R.id.list);
        list.requestFocus();
        break;
    }
    // NoteWidgetLightConfigureActivity_3_ViewComponentNotVisibleOperatorMutator
    case 388: {
        /**
        * Inserted by Kadabra
        */
        list = layout.findViewById(com.automattic.simplenote.R.id.list);
        list.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    list = layout.findViewById(com.automattic.simplenote.R.id.list);
    break;
}
}
mNotesAdapter = new com.automattic.simplenote.NoteWidgetLightConfigureActivity.NotesCursorAdapter(this, cursor);
list.setAdapter(mNotesAdapter);
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_4_BuggyGUIListenerOperatorMutator
case 488: {
    builder.setView(layout).setTitle(com.automattic.simplenote.R.string.select_note).setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
        @java.lang.Override
        public void onDismiss(android.content.DialogInterface dialog) {
            finish();
        }

    }).setNegativeButton(android.R.string.cancel, null).show();
    break;
}
default: {
builder.setView(layout).setTitle(com.automattic.simplenote.R.string.select_note).setOnDismissListener(new android.content.DialogInterface.OnDismissListener() {
    @java.lang.Override
    public void onDismiss(android.content.DialogInterface dialog) {
        finish();
    }

}).setNegativeButton(android.R.string.cancel, new android.content.DialogInterface.OnClickListener() {
    @java.lang.Override
    public void onClick(android.content.DialogInterface dialog, int which) {
        switch(MUID_STATIC) {
            // NoteWidgetLightConfigureActivity_5_LengthyGUIListenerOperatorMutator
            case 588: {
                /**
                * Inserted by Kadabra
                */
                finish();
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            finish();
            break;
        }
    }
}

}).show();
break;
}
}
}


private class NotesCursorAdapter extends android.widget.CursorAdapter {
private final com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> mCursor;

private NotesCursorAdapter(android.content.Context context, com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> cursor) {
super(context, cursor, 0);
mCursor = cursor;
}


@java.lang.Override
public android.view.View newView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup parent) {
return android.view.LayoutInflater.from(context).inflate(com.automattic.simplenote.utils.PrefUtils.getLayoutWidgetListItem(context, com.automattic.simplenote.utils.ThemeUtils.isLightTheme(context)), parent, false);
}


@java.lang.Override
public com.automattic.simplenote.models.Note getItem(int position) {
mCursor.moveToPosition(position);
return mCursor.getObject();
}


@java.lang.Override
public void bindView(android.view.View view, final android.content.Context context, final android.database.Cursor cursor) {
view.setTag(cursor.getPosition());
android.widget.TextView titleTextView;
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_6_InvalidViewFocusOperatorMutator
case 688: {
/**
* Inserted by Kadabra
*/
titleTextView = view.findViewById(com.automattic.simplenote.R.id.note_title);
titleTextView.requestFocus();
break;
}
// NoteWidgetLightConfigureActivity_7_ViewComponentNotVisibleOperatorMutator
case 788: {
/**
* Inserted by Kadabra
*/
titleTextView = view.findViewById(com.automattic.simplenote.R.id.note_title);
titleTextView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
titleTextView = view.findViewById(com.automattic.simplenote.R.id.note_title);
break;
}
}
android.widget.TextView contentTextView;
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_8_InvalidViewFocusOperatorMutator
case 888: {
/**
* Inserted by Kadabra
*/
contentTextView = view.findViewById(com.automattic.simplenote.R.id.note_content);
contentTextView.requestFocus();
break;
}
// NoteWidgetLightConfigureActivity_9_ViewComponentNotVisibleOperatorMutator
case 988: {
/**
* Inserted by Kadabra
*/
contentTextView = view.findViewById(com.automattic.simplenote.R.id.note_content);
contentTextView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
contentTextView = view.findViewById(com.automattic.simplenote.R.id.note_content);
break;
}
}
java.lang.String title;
title = "";
java.lang.String snippet;
snippet = "";
if (cursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME) > (-1)) {
title = cursor.getString(cursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.TITLE_INDEX_NAME));
}
if (cursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME) > (-1)) {
snippet = cursor.getString(cursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.CONTENT_PREVIEW_INDEX_NAME));
}
// Populate fields with extracted properties
titleTextView.setText(title);
android.text.SpannableStringBuilder snippetSpan;
snippetSpan = new android.text.SpannableStringBuilder(snippet);
snippetSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistSpansForRegexAndColor(context, snippetSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX, com.automattic.simplenote.R.color.text_title_disabled, true)));
contentTextView.setText(snippetSpan);
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_10_BuggyGUIListenerOperatorMutator
case 1088: {
view.setOnClickListener(null);
break;
}
default: {
view.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View view) {
// Get the selected note
com.automattic.simplenote.models.Note note;
note = mNotesAdapter.getItem(((int) (view.getTag())));
// Store link between note and widget in SharedPreferences
android.content.SharedPreferences preferences;
preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
preferences.edit().putString(com.automattic.simplenote.utils.PrefUtils.PREF_NOTE_WIDGET_NOTE + mAppWidgetId, note.getSimperiumKey()).apply();
// Prepare bundle for NoteEditorActivity
android.os.Bundle arguments;
arguments = new android.os.Bundle();
arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
arguments.putString(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
arguments.putBoolean(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
// Create intent to navigate to selected note on widget click
android.content.Intent intent;
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_12_InvalidKeyIntentOperatorMutator
case 1288: {
    intent = new android.content.Intent((Context) null, com.automattic.simplenote.NoteEditorActivity.class);
    break;
}
// NoteWidgetLightConfigureActivity_13_RandomActionIntentDefinitionOperatorMutator
case 1388: {
    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent = new android.content.Intent(context, com.automattic.simplenote.NoteEditorActivity.class);
break;
}
}
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_14_RandomActionIntentDefinitionOperatorMutator
case 1488: {
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
intent.putExtras(arguments);
break;
}
}
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_15_NullValueIntentPutExtraOperatorMutator
case 1588: {
intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, new Parcelable[0]);
break;
}
// NoteWidgetLightConfigureActivity_16_IntentPayloadReplacementOperatorMutator
case 1688: {
intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, (com.automattic.simplenote.analytics.AnalyticsTracker.Stat) null);
break;
}
default: {
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_17_RandomActionIntentDefinitionOperatorMutator
case 1788: {
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
intent.putExtra(com.automattic.simplenote.utils.WidgetUtils.KEY_WIDGET_CLICK, com.automattic.simplenote.analytics.AnalyticsTracker.Stat.NOTE_WIDGET_NOTE_TAPPED);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_18_RandomActionIntentDefinitionOperatorMutator
case 1888: {
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
pendingIntent = android.app.PendingIntent.getActivity(context, mAppWidgetId, intent, android.app.PendingIntent.FLAG_IMMUTABLE);
// Remove title from content
java.lang.String title;
title = note.getTitle();
java.lang.String contentWithoutTitle;
contentWithoutTitle = note.getContent().replace(title, "");
int indexOfNewline;
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_19_BinaryMutator
case 1988: {
indexOfNewline = contentWithoutTitle.indexOf("\n") - 1;
break;
}
default: {
indexOfNewline = contentWithoutTitle.indexOf("\n") + 1;
break;
}
}
java.lang.String content;
content = contentWithoutTitle.substring(indexOfNewline < contentWithoutTitle.length() ? indexOfNewline : 0);
// Set widget content
mRemoteViews.setOnClickPendingIntent(com.automattic.simplenote.R.id.widget_layout, pendingIntent);
mRemoteViews.setTextViewText(com.automattic.simplenote.R.id.widget_text, title);
mRemoteViews.setTextColor(com.automattic.simplenote.R.id.widget_text, getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
mRemoteViews.setTextViewText(com.automattic.simplenote.R.id.widget_text_title, title);
mRemoteViews.setTextColor(com.automattic.simplenote.R.id.widget_text_title, context.getResources().getColor(com.automattic.simplenote.R.color.text_title_light, context.getTheme()));
android.text.SpannableStringBuilder contentSpan;
contentSpan = new android.text.SpannableStringBuilder(content);
contentSpan = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistUnicodeSpansForRegex(contentSpan, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX)));
mRemoteViews.setTextViewText(com.automattic.simplenote.R.id.widget_text_content, contentSpan);
mWidgetManager.updateAppWidget(mAppWidgetId, mRemoteViews);
// Set the result as successful
android.content.Intent resultValue;
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_20_RandomActionIntentDefinitionOperatorMutator
case 2088: {
resultValue = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
resultValue = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_21_NullValueIntentPutExtraOperatorMutator
case 2188: {
resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, new Parcelable[0]);
break;
}
// NoteWidgetLightConfigureActivity_22_IntentPayloadReplacementOperatorMutator
case 2288: {
resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
break;
}
default: {
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_23_RandomActionIntentDefinitionOperatorMutator
case 2388: {
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
resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
break;
}
}
break;
}
}
setResult(android.app.Activity.RESULT_OK, resultValue);
switch(MUID_STATIC) {
// NoteWidgetLightConfigureActivity_11_LengthyGUIListenerOperatorMutator
case 1188: {
/**
* Inserted by Kadabra
*/
finish();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
finish();
break;
}
}
}

});
break;
}
}
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
