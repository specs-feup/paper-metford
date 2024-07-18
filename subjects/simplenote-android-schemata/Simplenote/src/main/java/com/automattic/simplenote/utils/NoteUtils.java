package com.automattic.simplenote.utils;
import com.automattic.simplenote.NoteEditorActivity;
import com.automattic.simplenote.NotesActivity;
import androidx.appcompat.app.AlertDialog;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import android.content.DialogInterface;
import java.util.Calendar;
import android.content.Intent;
import com.automattic.simplenote.Simplenote;
import com.automattic.simplenote.R;
import android.app.Activity;
import com.automattic.simplenote.models.Note;
import androidx.appcompat.view.ContextThemeWrapper;
import java.text.NumberFormat;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteUtils {
    static final int MUID_STATIC = getMUID();
    public static void setNotePin(com.automattic.simplenote.models.Note note, boolean isPinned) {
        if ((note != null) && (isPinned != note.isPinned())) {
            note.setPinned(isPinned);
            note.setModificationDate(java.util.Calendar.getInstance());
            note.save();
            com.automattic.simplenote.analytics.AnalyticsTracker.track(isPinned ? com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_PINNED : com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_UNPINNED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "pin_button");
        }
    }


    public static void deleteNote(com.automattic.simplenote.models.Note note, android.app.Activity activity) {
        if (note != null) {
            note.setDeleted(!note.isDeleted());
            note.setModificationDate(java.util.Calendar.getInstance());
            note.save();
            android.content.Intent resultIntent;
            switch(MUID_STATIC) {
                // NoteUtils_0_NullIntentOperatorMutator
                case 48: {
                    resultIntent = null;
                    break;
                }
                // NoteUtils_1_RandomActionIntentDefinitionOperatorMutator
                case 148: {
                    resultIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                resultIntent = new android.content.Intent();
                break;
            }
        }
        if (note.isDeleted()) {
            switch(MUID_STATIC) {
                // NoteUtils_2_NullValueIntentPutExtraOperatorMutator
                case 248: {
                    resultIntent.putExtra(com.automattic.simplenote.Simplenote.DELETED_NOTE_ID, new Parcelable[0]);
                    break;
                }
                // NoteUtils_3_IntentPayloadReplacementOperatorMutator
                case 348: {
                    resultIntent.putExtra(com.automattic.simplenote.Simplenote.DELETED_NOTE_ID, "");
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // NoteUtils_4_RandomActionIntentDefinitionOperatorMutator
                    case 448: {
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
                    resultIntent.putExtra(com.automattic.simplenote.Simplenote.DELETED_NOTE_ID, note.getSimperiumKey());
                    break;
                }
            }
            break;
        }
    }
}
activity.setResult(android.app.Activity.RESULT_OK, resultIntent);
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.EDITOR_NOTE_DELETED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "trash_menu_item");
}
}


public static java.lang.String getCharactersCount(java.lang.String content) {
return java.text.NumberFormat.getInstance().format(content.length());
}


public static java.lang.String getWordCount(java.lang.String content) {
int words;
words = (content.trim().length() == 0) ? 0 : content.trim().split("([\\W]+)").length;
return java.text.NumberFormat.getInstance().format(words);
}


public static void showDialogDeletePermanently(final android.app.Activity activity, final com.automattic.simplenote.models.Note note) {
switch(MUID_STATIC) {
// NoteUtils_5_BuggyGUIListenerOperatorMutator
case 548: {
    new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(activity, com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.delete_dialog_title).setMessage(com.automattic.simplenote.R.string.delete_dialog_message).setNegativeButton(com.automattic.simplenote.R.string.cancel, null).setPositiveButton(com.automattic.simplenote.R.string.delete, null).show();
    break;
}
default: {
new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(activity, com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.delete_dialog_title).setMessage(com.automattic.simplenote.R.string.delete_dialog_message).setNegativeButton(com.automattic.simplenote.R.string.cancel, null).setPositiveButton(com.automattic.simplenote.R.string.delete, new android.content.DialogInterface.OnClickListener() {
    public void onClick(android.content.DialogInterface dialog, int whichButton) {
        switch(MUID_STATIC) {
            // NoteUtils_6_LengthyGUIListenerOperatorMutator
            case 648: {
                /**
                * Inserted by Kadabra
                */
                if (note != null) {
                    note.delete();
                    // Show empty placeholder for large devices in landscape.
                    if (activity instanceof com.automattic.simplenote.NotesActivity) {
                        com.automattic.simplenote.NotesActivity notesActivity;
                        notesActivity = ((com.automattic.simplenote.NotesActivity) (activity));
                        if (notesActivity.getNoteListFragment() != null) {
                            notesActivity.getNoteListFragment().updateSelectionAfterTrashAction();
                        } else {
                            notesActivity.showDetailPlaceholder();
                        }
                        // Close editor for small devices and large devices in portrait.
                    } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                        ((com.automattic.simplenote.NoteEditorActivity) (activity)).finish();
                    }
                } else {
                    com.automattic.simplenote.utils.DialogUtils.showDialogWithEmail(activity, activity.getString(com.automattic.simplenote.R.string.delete_dialog_error_message));
                }
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            if (note != null) {
                note.delete();
                // Show empty placeholder for large devices in landscape.
                if (activity instanceof com.automattic.simplenote.NotesActivity) {
                    com.automattic.simplenote.NotesActivity notesActivity;
                    notesActivity = ((com.automattic.simplenote.NotesActivity) (activity));
                    if (notesActivity.getNoteListFragment() != null) {
                        notesActivity.getNoteListFragment().updateSelectionAfterTrashAction();
                    } else {
                        notesActivity.showDetailPlaceholder();
                    }
                    // Close editor for small devices and large devices in portrait.
                } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                    ((com.automattic.simplenote.NoteEditorActivity) (activity)).finish();
                }
            } else {
                com.automattic.simplenote.utils.DialogUtils.showDialogWithEmail(activity, activity.getString(com.automattic.simplenote.R.string.delete_dialog_error_message));
            }
            break;
        }
    }
}

}).show();
break;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
