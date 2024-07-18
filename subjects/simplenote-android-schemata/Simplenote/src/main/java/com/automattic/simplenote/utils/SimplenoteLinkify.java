package com.automattic.simplenote.utils;
import com.automattic.simplenote.NoteEditorActivity;
import com.automattic.simplenote.NotesActivity;
import java.util.regex.Pattern;
import android.util.Log;
import android.text.util.Linkify;
import android.content.Intent;
import com.automattic.simplenote.Simplenote;
import com.simperium.client.BucketObjectMissingException;
import android.text.SpannableString;
import com.automattic.simplenote.R;
import com.simperium.client.Bucket;
import android.app.Activity;
import com.automattic.simplenote.NoteEditorFragment;
import com.automattic.simplenote.models.Note;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Spannable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SimplenoteLinkify {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String SIMPLENOTE_SCHEME = "simplenote://";

    public static final java.lang.String SIMPLENOTE_LINK_PREFIX = com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_SCHEME + "note/";

    public static final java.lang.String SIMPLENOTE_LINK_ID = "([a-zA-Z0-9_\\.\\-%@]{1,256})";

    public static final java.util.regex.Pattern SIMPLENOTE_LINK_PATTERN = java.util.regex.Pattern.compile(com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX + com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_ID);

    // Works the same as Linkify.addLinks, but doesn't set movement method
    public static boolean addLinks(android.widget.TextView text, int mask) {
        if (mask == 0) {
            return false;
        }
        java.lang.CharSequence t;
        t = text.getText();
        if (t instanceof android.text.Spannable) {
            boolean linked;
            linked = android.text.util.Linkify.addLinks(((android.text.Spannable) (t)), mask);
            android.text.util.Linkify.addLinks(((android.text.Spannable) (t)), com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PATTERN, com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_SCHEME);
            return linked;
        } else {
            android.text.SpannableString s;
            s = android.text.SpannableString.valueOf(t);
            if (android.text.util.Linkify.addLinks(s, mask)) {
                text.setText(s);
                return true;
            }
            return false;
        }
    }


    public static java.lang.String getNoteLink(java.lang.String id) {
        return (("(" + com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX) + id) + ")";
    }


    public static java.lang.String getNoteLinkWithTitle(java.lang.String title, java.lang.String id) {
        return (("[" + title) + "]") + com.automattic.simplenote.utils.SimplenoteLinkify.getNoteLink(id);
    }


    public static void openNote(android.app.Activity activity, java.lang.String id) {
        com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket;
        bucket = ((com.automattic.simplenote.Simplenote) (activity.getApplication())).getNotesBucket();
        switch(MUID_STATIC) {
            // SimplenoteLinkify_0_NullIntentOperatorMutator
            case 37: {
                try {
                    com.automattic.simplenote.models.Note note;
                    note = bucket.get(id);
                    if (activity instanceof com.automattic.simplenote.NotesActivity) {
                        ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                        ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                        ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                    } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                        android.content.Intent intent;
                        intent = null;
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                        activity.finish();
                    } else {
                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                        android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                    }
                } catch (com.simperium.client.BucketObjectMissingException e) {
                    android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                    android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                }
                break;
            }
            // SimplenoteLinkify_1_InvalidKeyIntentOperatorMutator
            case 137: {
                try {
                    com.automattic.simplenote.models.Note note;
                    note = bucket.get(id);
                    if (activity instanceof com.automattic.simplenote.NotesActivity) {
                        ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                        ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                        ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                    } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                        android.content.Intent intent;
                        intent = new android.content.Intent((Activity) null, com.automattic.simplenote.NoteEditorActivity.class);
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                        activity.finish();
                    } else {
                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                        android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                    }
                } catch (com.simperium.client.BucketObjectMissingException e) {
                    android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                    android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                }
                break;
            }
            // SimplenoteLinkify_2_RandomActionIntentDefinitionOperatorMutator
            case 237: {
                try {
                    com.automattic.simplenote.models.Note note;
                    note = bucket.get(id);
                    if (activity instanceof com.automattic.simplenote.NotesActivity) {
                        ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                        ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                        ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                    } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                        android.content.Intent intent;
                        intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.startActivity(intent);
                        activity.finish();
                    } else {
                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                        android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                    }
                } catch (com.simperium.client.BucketObjectMissingException e) {
                    android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                    android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                }
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // SimplenoteLinkify_3_NullValueIntentPutExtraOperatorMutator
                case 337: {
                    try {
                        com.automattic.simplenote.models.Note note;
                        note = bucket.get(id);
                        if (activity instanceof com.automattic.simplenote.NotesActivity) {
                            ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                            ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                            ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                        } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                            android.content.Intent intent;
                            intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, new Parcelable[0]);
                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intent);
                            activity.finish();
                        } else {
                            android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                            android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                        }
                    } catch (com.simperium.client.BucketObjectMissingException e) {
                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                        android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                    }
                    break;
                }
                // SimplenoteLinkify_4_IntentPayloadReplacementOperatorMutator
                case 437: {
                    try {
                        com.automattic.simplenote.models.Note note;
                        note = bucket.get(id);
                        if (activity instanceof com.automattic.simplenote.NotesActivity) {
                            ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                            ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                            ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                        } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                            android.content.Intent intent;
                            intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, true);
                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intent);
                            activity.finish();
                        } else {
                            android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                            android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                        }
                    } catch (com.simperium.client.BucketObjectMissingException e) {
                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                        android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                    }
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // SimplenoteLinkify_5_RandomActionIntentDefinitionOperatorMutator
                    case 537: {
                        try {
                            com.automattic.simplenote.models.Note note;
                            note = bucket.get(id);
                            if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                            } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                android.content.Intent intent;
                                intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                /**
                                * Inserted by Kadabra
                                */
                                /**
                                * Inserted by Kadabra
                                */
                                new android.content.Intent(android.content.Intent.ACTION_SEND);;
                                intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                                intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                                intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                activity.startActivity(intent);
                                activity.finish();
                            } else {
                                android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                            }
                        } catch (com.simperium.client.BucketObjectMissingException e) {
                            android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                            android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                        }
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // SimplenoteLinkify_6_NullValueIntentPutExtraOperatorMutator
                        case 637: {
                            try {
                                com.automattic.simplenote.models.Note note;
                                note = bucket.get(id);
                                if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                    ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                    ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                    ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                    android.content.Intent intent;
                                    intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, new Parcelable[0]);
                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    activity.startActivity(intent);
                                    activity.finish();
                                } else {
                                    android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                    android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                }
                            } catch (com.simperium.client.BucketObjectMissingException e) {
                                android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                            }
                            break;
                        }
                        // SimplenoteLinkify_7_IntentPayloadReplacementOperatorMutator
                        case 737: {
                            try {
                                com.automattic.simplenote.models.Note note;
                                note = bucket.get(id);
                                if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                    ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                    ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                    ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                    android.content.Intent intent;
                                    intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, "");
                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    activity.startActivity(intent);
                                    activity.finish();
                                } else {
                                    android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                    android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                }
                            } catch (com.simperium.client.BucketObjectMissingException e) {
                                android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                            }
                            break;
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // SimplenoteLinkify_8_RandomActionIntentDefinitionOperatorMutator
                            case 837: {
                                try {
                                    com.automattic.simplenote.models.Note note;
                                    note = bucket.get(id);
                                    if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                        ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                        ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                        ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                    } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                        android.content.Intent intent;
                                        intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                        /**
                                        * Inserted by Kadabra
                                        */
                                        /**
                                        * Inserted by Kadabra
                                        */
                                        new android.content.Intent(android.content.Intent.ACTION_SEND);;
                                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        activity.startActivity(intent);
                                        activity.finish();
                                    } else {
                                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                        android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                    }
                                } catch (com.simperium.client.BucketObjectMissingException e) {
                                    android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                    android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                                }
                                break;
                            }
                            default: {
                            switch(MUID_STATIC) {
                                // SimplenoteLinkify_9_NullValueIntentPutExtraOperatorMutator
                                case 937: {
                                    try {
                                        com.automattic.simplenote.models.Note note;
                                        note = bucket.get(id);
                                        if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                            ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                            ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                            ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                        } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                            android.content.Intent intent;
                                            intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, new Parcelable[0]);
                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                                            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            activity.startActivity(intent);
                                            activity.finish();
                                        } else {
                                            android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                            android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                        }
                                    } catch (com.simperium.client.BucketObjectMissingException e) {
                                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                        android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                                    }
                                    break;
                                }
                                // SimplenoteLinkify_10_IntentPayloadReplacementOperatorMutator
                                case 1037: {
                                    try {
                                        com.automattic.simplenote.models.Note note;
                                        note = bucket.get(id);
                                        if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                            ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                            ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                            ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                        } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                            android.content.Intent intent;
                                            intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, true);
                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                                            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            activity.startActivity(intent);
                                            activity.finish();
                                        } else {
                                            android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                            android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                        }
                                    } catch (com.simperium.client.BucketObjectMissingException e) {
                                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                        android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                                    }
                                    break;
                                }
                                default: {
                                switch(MUID_STATIC) {
                                    // SimplenoteLinkify_11_RandomActionIntentDefinitionOperatorMutator
                                    case 1137: {
                                        try {
                                            com.automattic.simplenote.models.Note note;
                                            note = bucket.get(id);
                                            if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                                ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                                ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                                ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                            } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                                android.content.Intent intent;
                                                intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                                intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                                intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                                                /**
                                                * Inserted by Kadabra
                                                */
                                                /**
                                                * Inserted by Kadabra
                                                */
                                                new android.content.Intent(android.content.Intent.ACTION_SEND);;
                                                intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                                                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                activity.startActivity(intent);
                                                activity.finish();
                                            } else {
                                                android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                            }
                                        } catch (com.simperium.client.BucketObjectMissingException e) {
                                            android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                            android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                                        }
                                        break;
                                    }
                                    default: {
                                    switch(MUID_STATIC) {
                                        // SimplenoteLinkify_12_NullValueIntentPutExtraOperatorMutator
                                        case 1237: {
                                            try {
                                                com.automattic.simplenote.models.Note note;
                                                note = bucket.get(id);
                                                if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                                    ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                                    ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                                    ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                                } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                                    android.content.Intent intent;
                                                    intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, new Parcelable[0]);
                                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    activity.startActivity(intent);
                                                    activity.finish();
                                                } else {
                                                    android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                    android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                                }
                                            } catch (com.simperium.client.BucketObjectMissingException e) {
                                                android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                                            }
                                            break;
                                        }
                                        // SimplenoteLinkify_13_IntentPayloadReplacementOperatorMutator
                                        case 1337: {
                                            try {
                                                com.automattic.simplenote.models.Note note;
                                                note = bucket.get(id);
                                                if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                                    ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                                    ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                                    ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                                } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                                    android.content.Intent intent;
                                                    intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                                                    intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, true);
                                                    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    activity.startActivity(intent);
                                                    activity.finish();
                                                } else {
                                                    android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                    android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                                }
                                            } catch (com.simperium.client.BucketObjectMissingException e) {
                                                android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                                            }
                                            break;
                                        }
                                        default: {
                                        switch(MUID_STATIC) {
                                            // SimplenoteLinkify_14_RandomActionIntentDefinitionOperatorMutator
                                            case 1437: {
                                                try {
                                                    com.automattic.simplenote.models.Note note;
                                                    note = bucket.get(id);
                                                    if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                                        ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                                        ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                                        ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                                    } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                                        android.content.Intent intent;
                                                        intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                                                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                                                        /**
                                                        * Inserted by Kadabra
                                                        */
                                                        /**
                                                        * Inserted by Kadabra
                                                        */
                                                        new android.content.Intent(android.content.Intent.ACTION_SEND);;
                                                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        activity.startActivity(intent);
                                                        activity.finish();
                                                    } else {
                                                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                        android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                                    }
                                                } catch (com.simperium.client.BucketObjectMissingException e) {
                                                    android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                    android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                                                }
                                                break;
                                            }
                                            default: {
                                            switch(MUID_STATIC) {
                                                // SimplenoteLinkify_15_RandomActionIntentDefinitionOperatorMutator
                                                case 1537: {
                                                    try {
                                                        com.automattic.simplenote.models.Note note;
                                                        note = bucket.get(id);
                                                        if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                                            ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                                            ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                                            ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                                        } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                                            android.content.Intent intent;
                                                            intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                                                            intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                                                            /**
                                                            * Inserted by Kadabra
                                                            */
                                                            /**
                                                            * Inserted by Kadabra
                                                            */
                                                            new android.content.Intent(android.content.Intent.ACTION_SEND);;
                                                            activity.startActivity(intent);
                                                            activity.finish();
                                                        } else {
                                                            android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                            android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                                        }
                                                    } catch (com.simperium.client.BucketObjectMissingException e) {
                                                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                        android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
                                                    }
                                                    break;
                                                }
                                                default: {
                                                try {
                                                    com.automattic.simplenote.models.Note note;
                                                    note = bucket.get(id);
                                                    if (activity instanceof com.automattic.simplenote.NotesActivity) {
                                                        ((com.automattic.simplenote.NotesActivity) (activity)).selectDefaultTag();
                                                        ((com.automattic.simplenote.NotesActivity) (activity)).onNoteSelected(note.getSimperiumKey(), null, note.isMarkdownEnabled(), note.isPreviewEnabled());
                                                        ((com.automattic.simplenote.NotesActivity) (activity)).scrollToSelectedNote(note.getSimperiumKey());
                                                    } else if (activity instanceof com.automattic.simplenote.NoteEditorActivity) {
                                                        android.content.Intent intent;
                                                        intent = new android.content.Intent(activity, com.automattic.simplenote.NoteEditorActivity.class);
                                                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_IS_FROM_WIDGET, false);
                                                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_ITEM_ID, note.getSimperiumKey());
                                                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_MARKDOWN_ENABLED, note.isMarkdownEnabled());
                                                        intent.putExtra(com.automattic.simplenote.NoteEditorFragment.ARG_PREVIEW_ENABLED, note.isPreviewEnabled());
                                                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        activity.startActivity(intent);
                                                        activity.finish();
                                                    } else {
                                                        android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                        android.util.Log.d("openNote", "Activity is not NotesActivity nor NoteEditorActivity");
                                                    }
                                                } catch (com.simperium.client.BucketObjectMissingException e) {
                                                    android.widget.Toast.makeText(activity, com.automattic.simplenote.R.string.open_note_error, android.widget.Toast.LENGTH_SHORT).show();
                                                    android.util.Log.d("openNote", activity.getString(com.automattic.simplenote.R.string.open_note_error));
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
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
