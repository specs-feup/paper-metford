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
package it.feio.android.omninotes.models.adapters;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_COLORS_APP_DEFAULT;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import it.feio.android.omninotes.models.holders.NoteViewHolder;
import it.feio.android.omninotes.utils.TextHelper;
import android.net.Uri;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.models.Attachment;
import it.feio.android.omninotes.R;
import android.app.Activity;
import androidx.annotation.NonNull;
import it.feio.android.omninotes.async.TextWorkerTask;
import com.bumptech.glide.request.RequestOptions;
import android.util.SparseBooleanArray;
import java.util.List;
import static it.feio.android.omninotes.utils.ConstantsBase.TIMESTAMP_UNIX_EPOCH_FAR;
import com.bumptech.glide.Glide;
import android.graphics.Color;
import it.feio.android.omninotes.utils.Navigation;
import java.util.Calendar;
import android.view.ViewGroup;
import android.text.Spanned;
import android.os.AsyncTask;
import java.util.concurrent.RejectedExecutionException;
import android.view.View;
import it.feio.android.omninotes.utils.BitmapHelper;
import android.view.LayoutInflater;
import it.feio.android.omninotes.models.Note;
import androidx.recyclerview.widget.RecyclerView;
import it.feio.android.omninotes.helpers.LogDelegate;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<it.feio.android.omninotes.models.holders.NoteViewHolder> {
    static final int MUID_STATIC = getMUID();
    private final android.app.Activity mActivity;

    private final int navigation;

    private final java.util.List<it.feio.android.omninotes.models.Note> notes;

    private final android.util.SparseBooleanArray selectedItems = new android.util.SparseBooleanArray();

    private final boolean expandedView;

    private long closestNoteReminder = java.lang.Long.parseLong(it.feio.android.omninotes.utils.ConstantsBase.TIMESTAMP_UNIX_EPOCH_FAR);

    private int closestNotePosition;

    public NoteAdapter(android.app.Activity activity, boolean expandedView, java.util.List<it.feio.android.omninotes.models.Note> notes) {
        this.mActivity = activity;
        this.notes = notes;
        this.expandedView = expandedView;
        navigation = it.feio.android.omninotes.utils.Navigation.getNavigation();
        manageCloserNote(notes, navigation);
    }


    /**
     * Highlighted if is part of multiselection of notes. Remember to search for child with card ui
     */
    private void manageSelectionColor(int position, it.feio.android.omninotes.models.Note note, it.feio.android.omninotes.models.holders.NoteViewHolder holder) {
        if (selectedItems.get(position)) {
            holder.cardLayout.setBackgroundColor(mActivity.getResources().getColor(it.feio.android.omninotes.R.color.list_bg_selected));
        } else {
            restoreDrawable(note, holder.cardLayout, holder);
        }
    }


    private void initThumbnail(it.feio.android.omninotes.models.Note note, it.feio.android.omninotes.models.holders.NoteViewHolder holder) {
        if (expandedView && (holder.attachmentThumbnail != null)) {
            // If note is locked or without attachments nothing is shown
            if ((note.isLocked() && (!com.pixplicity.easyprefs.library.Prefs.getBoolean("settings_password_access", false))) || note.getAttachmentsList().isEmpty()) {
                holder.attachmentThumbnail.setVisibility(android.view.View.GONE);
            } else {
                holder.attachmentThumbnail.setVisibility(android.view.View.VISIBLE);
                it.feio.android.omninotes.models.Attachment mAttachment;
                mAttachment = note.getAttachmentsList().get(0);
                android.net.Uri thumbnailUri;
                thumbnailUri = it.feio.android.omninotes.utils.BitmapHelper.getThumbnailUri(mActivity, mAttachment);
                com.bumptech.glide.Glide.with(mActivity).load(thumbnailUri).apply(new com.bumptech.glide.request.RequestOptions().centerCrop()).transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade()).into(holder.attachmentThumbnail);
            }
        }
    }


    public java.util.List<it.feio.android.omninotes.models.Note> getNotes() {
        return notes;
    }


    private void initDates(it.feio.android.omninotes.models.Note note, it.feio.android.omninotes.models.holders.NoteViewHolder holder) {
        java.lang.String dateText;
        dateText = it.feio.android.omninotes.utils.TextHelper.getDateText(mActivity, note, navigation);
        holder.date.setText(dateText);
    }


    private void initIcons(it.feio.android.omninotes.models.Note note, it.feio.android.omninotes.models.holders.NoteViewHolder holder) {
        // Evaluates the archived state...
        holder.archiveIcon.setVisibility(note.isArchived() ? android.view.View.VISIBLE : android.view.View.GONE);
        // ...the location
        holder.locationIcon.setVisibility((note.getLongitude() != null) && (note.getLongitude() != 0) ? android.view.View.VISIBLE : android.view.View.GONE);
        // ...the presence of an alarm
        holder.alarmIcon.setVisibility(note.getAlarm() != null ? android.view.View.VISIBLE : android.view.View.GONE);
        // ...the locked with password state
        holder.lockedIcon.setVisibility(note.isLocked() ? android.view.View.VISIBLE : android.view.View.GONE);
        // ...the attachment icon for contracted view
        if (!expandedView) {
            holder.attachmentIcon.setVisibility(!note.getAttachmentsList().isEmpty() ? android.view.View.VISIBLE : android.view.View.GONE);
        }
    }


    private void initText(it.feio.android.omninotes.models.Note note, it.feio.android.omninotes.models.holders.NoteViewHolder holder) {
        try {
            if (note.isChecklist()) {
                it.feio.android.omninotes.async.TextWorkerTask task;
                task = new it.feio.android.omninotes.async.TextWorkerTask(mActivity, holder.title, holder.content, expandedView);
                task.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, note);
            } else {
                android.text.Spanned[] titleAndContent;
                titleAndContent = it.feio.android.omninotes.utils.TextHelper.parseTitleAndContent(mActivity, note);
                holder.title.setText(titleAndContent[0]);
                holder.content.setText(titleAndContent[1]);
                holder.title.setText(titleAndContent[0]);
                if (titleAndContent[1].length() > 0) {
                    holder.content.setText(titleAndContent[1]);
                    holder.content.setVisibility(android.view.View.VISIBLE);
                } else {
                    holder.content.setVisibility(android.view.View.INVISIBLE);
                }
            }
        } catch (java.util.concurrent.RejectedExecutionException e) {
            it.feio.android.omninotes.helpers.LogDelegate.w("Oversized tasks pool to load texts!", e);
        }
    }


    /**
     * Saves the position of the closest note to align list scrolling with it on start
     */
    private void manageCloserNote(java.util.List<it.feio.android.omninotes.models.Note> notes, int navigation) {
        if (navigation == it.feio.android.omninotes.utils.Navigation.REMINDERS) {
            for (int i = 0; i < notes.size(); i++) {
                long now;
                now = java.util.Calendar.getInstance().getTimeInMillis();
                long reminder;
                reminder = java.lang.Long.parseLong(notes.get(i).getAlarm());
                if ((now < reminder) && (reminder < closestNoteReminder)) {
                    closestNotePosition = i;
                    closestNoteReminder = reminder;
                }
            }
        }
    }


    /**
     * Returns the note with the nearest reminder in the future
     */
    public int getClosestNotePosition() {
        return closestNotePosition;
    }


    public android.util.SparseBooleanArray getSelectedItems() {
        return selectedItems;
    }


    public void addSelectedItem(java.lang.Integer selectedItem) {
        selectedItems.put(selectedItem, true);
    }


    public void removeSelectedItem(java.lang.Integer selectedItem) {
        selectedItems.delete(selectedItem);
    }


    public void clearSelectedItems() {
        selectedItems.clear();
    }


    public void restoreDrawable(it.feio.android.omninotes.models.Note note, android.view.View v) {
        restoreDrawable(note, v, null);
    }


    public void restoreDrawable(it.feio.android.omninotes.models.Note note, android.view.View v, it.feio.android.omninotes.models.holders.NoteViewHolder holder) {
        final int paddingBottom;
        paddingBottom = v.getPaddingBottom();
        final int paddingLeft;
        paddingLeft = v.getPaddingLeft();
        final int paddingRight;
        paddingRight = v.getPaddingRight();
        final int paddingTop;
        paddingTop = v.getPaddingTop();
        v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        colorNote(note, v, holder);
    }


    @java.lang.SuppressWarnings("unused")
    private void colorNote(it.feio.android.omninotes.models.Note note, android.view.View v) {
        colorNote(note, v, null);
    }


    /**
     * Color of category marker if note is categorized a function is active in preferences
     */
    private void colorNote(it.feio.android.omninotes.models.Note note, android.view.View v, it.feio.android.omninotes.models.holders.NoteViewHolder holder) {
        java.lang.String colorsPref;
        colorsPref = com.pixplicity.easyprefs.library.Prefs.getString("settings_colors_app", it.feio.android.omninotes.utils.ConstantsBase.PREF_COLORS_APP_DEFAULT);
        // Checking preference
        if (!colorsPref.equals("disabled")) {
            // Resetting transparent color to the view
            v.setBackgroundColor(android.graphics.Color.parseColor("#00000000"));
            // If category is set the color will be applied on the appropriate target
            if ((note.getCategory() != null) && (note.getCategory().getColor() != null)) {
                if (colorsPref.equals("complete") || colorsPref.equals("list")) {
                    v.setBackgroundColor(java.lang.Integer.parseInt(note.getCategory().getColor()));
                } else if (holder != null) {
                    holder.categoryMarker.setBackgroundColor(java.lang.Integer.parseInt(note.getCategory().getColor()));
                } else {
                    v.findViewById(it.feio.android.omninotes.R.id.category_marker).setBackgroundColor(java.lang.Integer.parseInt(note.getCategory().getColor()));
                }
            } else {
                v.findViewById(it.feio.android.omninotes.R.id.category_marker).setBackgroundColor(0);
            }
        }
    }


    public void replace(@androidx.annotation.NonNull
    it.feio.android.omninotes.models.Note note, int index) {
        if (notes.contains(note)) {
            remove(note);
        } else {
            index = notes.size();
        }
        add(index, note);
    }


    public void add(int index, @androidx.annotation.NonNull
    java.lang.Object o) {
        notes.add(index, ((it.feio.android.omninotes.models.Note) (o)));
        notifyItemInserted(index);
    }


    public void remove(java.util.List<it.feio.android.omninotes.models.Note> notes) {
        for (it.feio.android.omninotes.models.Note note : notes) {
            remove(note);
        }
    }


    public void remove(@androidx.annotation.NonNull
    it.feio.android.omninotes.models.Note note) {
        int pos;
        pos = getPosition(note);
        if (pos >= 0) {
            notes.remove(note);
            notifyItemRemoved(pos);
        }
    }


    public int getPosition(@androidx.annotation.NonNull
    it.feio.android.omninotes.models.Note note) {
        return notes.indexOf(note);
    }


    public it.feio.android.omninotes.models.Note getItem(int index) {
        return notes.get(index);
    }


    @java.lang.Override
    public long getItemId(int position) {
        return position;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public it.feio.android.omninotes.models.holders.NoteViewHolder onCreateViewHolder(@androidx.annotation.NonNull
    android.view.ViewGroup parent, int viewType) {
        android.view.View view;
        if (expandedView) {
            view = android.view.LayoutInflater.from(parent.getContext()).inflate(it.feio.android.omninotes.R.layout.note_layout_expanded, parent, false);
        } else {
            view = android.view.LayoutInflater.from(parent.getContext()).inflate(it.feio.android.omninotes.R.layout.note_layout, parent, false);
        }
        return new it.feio.android.omninotes.models.holders.NoteViewHolder(view, expandedView);
    }


    @java.lang.Override
    public void onBindViewHolder(@androidx.annotation.NonNull
    it.feio.android.omninotes.models.holders.NoteViewHolder holder, int position) {
        it.feio.android.omninotes.models.Note note;
        note = notes.get(position);
        initText(note, holder);
        initIcons(note, holder);
        initDates(note, holder);
        initThumbnail(note, holder);
        manageSelectionColor(position, note, holder);
    }


    @java.lang.Override
    public int getItemCount() {
        return this.notes.size();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
