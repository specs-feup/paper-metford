/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.feio.android.omninotes.widget;
import android.graphics.Color;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_COLORS_APP_DEFAULT;
import it.feio.android.omninotes.utils.Navigation;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_WIDGET_PREFIX;
import android.os.Bundle;
import android.appwidget.AppWidgetManager;
import android.text.Spanned;
import android.content.Intent;
import it.feio.android.omninotes.utils.TextHelper;
import android.graphics.Bitmap;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.models.Attachment;
import android.view.View;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE;
import it.feio.android.omninotes.OmniNotes;
import it.feio.android.omninotes.utils.BitmapHelper;
import android.widget.RemoteViews;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.models.Note;
import java.util.List;
import android.app.Application;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.helpers.LogDelegate;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ListRemoteViewsFactory implements android.widget.RemoteViewsService.RemoteViewsFactory {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String SET_BACKGROUND_COLOR = "setBackgroundColor";

    private static boolean showThumbnails = true;

    private static boolean showTimestamps = true;

    private final int WIDTH = 80;

    private final int HEIGHT = 80;

    private it.feio.android.omninotes.OmniNotes app;

    private int appWidgetId;

    private java.util.List<it.feio.android.omninotes.models.Note> notes;

    private int navigation;

    public ListRemoteViewsFactory(android.app.Application app, android.content.Intent intent) {
        this.app = ((it.feio.android.omninotes.OmniNotes) (app));
        appWidgetId = intent.getIntExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    static void updateConfiguration(int mAppWidgetId, java.lang.String sqlCondition, boolean thumbnails, boolean timestamps) {
        it.feio.android.omninotes.helpers.LogDelegate.d("Widget configuration updated");
        com.pixplicity.easyprefs.library.Prefs.edit().putString(it.feio.android.omninotes.utils.ConstantsBase.PREF_WIDGET_PREFIX + mAppWidgetId, sqlCondition).apply();
        it.feio.android.omninotes.widget.ListRemoteViewsFactory.showThumbnails = thumbnails;
        it.feio.android.omninotes.widget.ListRemoteViewsFactory.showTimestamps = timestamps;
    }


    @java.lang.Override
    public void onCreate() {
        it.feio.android.omninotes.helpers.LogDelegate.d("Created widget " + appWidgetId);
        java.lang.String condition;
        condition = com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_WIDGET_PREFIX + appWidgetId, "");
        notes = it.feio.android.omninotes.db.DbHelper.getInstance().getNotes(condition, true);
    }


    @java.lang.Override
    public void onDataSetChanged() {
        it.feio.android.omninotes.helpers.LogDelegate.d("onDataSetChanged widget " + appWidgetId);
        navigation = it.feio.android.omninotes.utils.Navigation.getNavigation();
        java.lang.String condition;
        condition = com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_WIDGET_PREFIX + appWidgetId, "");
        notes = it.feio.android.omninotes.db.DbHelper.getInstance().getNotes(condition, true);
    }


    @java.lang.Override
    public void onDestroy() {
        com.pixplicity.easyprefs.library.Prefs.edit().remove(it.feio.android.omninotes.utils.ConstantsBase.PREF_WIDGET_PREFIX + appWidgetId).apply();
    }


    @java.lang.Override
    public int getCount() {
        return notes.size();
    }


    @java.lang.Override
    public android.widget.RemoteViews getViewAt(int position) {
        android.widget.RemoteViews row;
        row = new android.widget.RemoteViews(app.getPackageName(), it.feio.android.omninotes.R.layout.note_layout_widget);
        it.feio.android.omninotes.models.Note note;
        note = notes.get(position);
        android.text.Spanned[] titleAndContent;
        titleAndContent = it.feio.android.omninotes.utils.TextHelper.parseTitleAndContent(app, note);
        row.setTextViewText(it.feio.android.omninotes.R.id.note_title, titleAndContent[0]);
        row.setTextViewText(it.feio.android.omninotes.R.id.note_content, titleAndContent[1]);
        color(note, row);
        if (((!note.isLocked()) && it.feio.android.omninotes.widget.ListRemoteViewsFactory.showThumbnails) && (!note.getAttachmentsList().isEmpty())) {
            it.feio.android.omninotes.models.Attachment mAttachment;
            mAttachment = note.getAttachmentsList().get(0);
            android.graphics.Bitmap bmp;
            bmp = it.feio.android.omninotes.utils.BitmapHelper.getBitmapFromAttachment(app, mAttachment, WIDTH, HEIGHT);
            row.setBitmap(it.feio.android.omninotes.R.id.attachmentThumbnail, "setImageBitmap", bmp);
            row.setInt(it.feio.android.omninotes.R.id.attachmentThumbnail, "setVisibility", android.view.View.VISIBLE);
        } else {
            row.setInt(it.feio.android.omninotes.R.id.attachmentThumbnail, "setVisibility", android.view.View.GONE);
        }
        if (it.feio.android.omninotes.widget.ListRemoteViewsFactory.showTimestamps) {
            row.setTextViewText(it.feio.android.omninotes.R.id.note_date, it.feio.android.omninotes.utils.TextHelper.getDateText(app, note, navigation));
        } else {
            row.setTextViewText(it.feio.android.omninotes.R.id.note_date, "");
        }
        // Next, set a fill-intent, which will be used to fill in the pending intent template
        // that is set on the collection view in StackWidgetProvider.
        android.os.Bundle extras;
        extras = new android.os.Bundle();
        extras.putParcelable(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE, note);
        android.content.Intent fillInIntent;
        switch(MUID_STATIC) {
            // ListRemoteViewsFactory_0_NullIntentOperatorMutator
            case 107: {
                fillInIntent = null;
                break;
            }
            // ListRemoteViewsFactory_1_RandomActionIntentDefinitionOperatorMutator
            case 1107: {
                fillInIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            fillInIntent = new android.content.Intent();
            break;
        }
    }
    switch(MUID_STATIC) {
        // ListRemoteViewsFactory_2_RandomActionIntentDefinitionOperatorMutator
        case 2107: {
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
        fillInIntent.putExtras(extras);
        break;
    }
}
// Make it possible to distinguish the individual on-click
// action of a given item
row.setOnClickFillInIntent(it.feio.android.omninotes.R.id.root, fillInIntent);
return row;
}


@java.lang.Override
public android.widget.RemoteViews getLoadingView() {
return null;
}


@java.lang.Override
public int getViewTypeCount() {
return 1;
}


@java.lang.Override
public long getItemId(int position) {
return position;
}


@java.lang.Override
public boolean hasStableIds() {
return false;
}


private void color(it.feio.android.omninotes.models.Note note, android.widget.RemoteViews row) {
java.lang.String colorsPref;
colorsPref = com.pixplicity.easyprefs.library.Prefs.getString("settings_colors_widget", it.feio.android.omninotes.utils.ConstantsBase.PREF_COLORS_APP_DEFAULT);
// Checking preference
if (!colorsPref.equals("disabled")) {
    // Resetting transparent color to the view
    row.setInt(it.feio.android.omninotes.R.id.tag_marker, it.feio.android.omninotes.widget.ListRemoteViewsFactory.SET_BACKGROUND_COLOR, android.graphics.Color.parseColor("#00000000"));
    // If tag is set the color will be applied on the appropriate target
    if ((note.getCategory() != null) && (note.getCategory().getColor() != null)) {
        if (colorsPref.equals("list")) {
            row.setInt(it.feio.android.omninotes.R.id.card_layout, it.feio.android.omninotes.widget.ListRemoteViewsFactory.SET_BACKGROUND_COLOR, java.lang.Integer.parseInt(note.getCategory().getColor()));
        } else {
            row.setInt(it.feio.android.omninotes.R.id.tag_marker, it.feio.android.omninotes.widget.ListRemoteViewsFactory.SET_BACKGROUND_COLOR, java.lang.Integer.parseInt(note.getCategory().getColor()));
        }
    } else {
        row.setInt(it.feio.android.omninotes.R.id.tag_marker, it.feio.android.omninotes.widget.ListRemoteViewsFactory.SET_BACKGROUND_COLOR, 0);
    }
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
