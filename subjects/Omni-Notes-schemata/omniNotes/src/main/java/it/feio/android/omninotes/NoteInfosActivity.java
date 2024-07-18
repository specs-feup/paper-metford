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
package it.feio.android.omninotes;
import it.feio.android.omninotes.databinding.ActivityNoteInfosBinding;
import android.app.Activity;
import it.feio.android.omninotes.models.Note;
import android.os.Bundle;
import org.apache.commons.lang3.StringUtils;
import java.util.Objects;
import android.widget.TextView;
import it.feio.android.omninotes.helpers.NotesHelper;
import it.feio.android.omninotes.models.StatsSingleNote;
import android.view.View;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteInfosActivity extends android.app.Activity {
    static final int MUID_STATIC = getMUID();
    private it.feio.android.omninotes.databinding.ActivityNoteInfosBinding binding;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // NoteInfosActivity_0_LengthyGUICreationOperatorMutator
            case 147: {
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
    binding = it.feio.android.omninotes.databinding.ActivityNoteInfosBinding.inflate(getLayoutInflater());
    android.view.View view;
    view = binding.getRoot();
    setContentView(view);
    it.feio.android.omninotes.models.Note note;
    note = java.util.Objects.requireNonNull(getIntent().getExtras()).getParcelable(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE);
    populateViews(note);
}


private void populateViews(it.feio.android.omninotes.models.Note note) {
    it.feio.android.omninotes.models.StatsSingleNote infos;
    infos = it.feio.android.omninotes.helpers.NotesHelper.getNoteInfos(note);
    populateView(binding.noteInfosCategory, infos.getCategoryName());
    populateView(binding.noteInfosTags, infos.getTags());
    populateView(binding.noteInfosChars, infos.getChars());
    populateView(binding.noteInfosWords, infos.getWords());
    populateView(binding.noteInfosChecklistItems, infos.getChecklistItemsNumber());
    populateView(binding.noteInfosCompletedChecklistItems, it.feio.android.omninotes.NoteInfosActivity.getChecklistCompletionState(infos), !note.isChecklist());
    populateView(binding.noteInfosImages, infos.getImages());
    populateView(binding.noteInfosVideos, infos.getVideos());
    populateView(binding.noteInfosAudiorecordings, infos.getAudioRecordings());
    populateView(binding.noteInfosSketches, infos.getSketches());
    populateView(binding.noteInfosFiles, infos.getFiles());
}


static java.lang.String getChecklistCompletionState(it.feio.android.omninotes.models.StatsSingleNote infos) {
    int percentage;
    switch(MUID_STATIC) {
        // NoteInfosActivity_1_BinaryMutator
        case 1147: {
            percentage = java.lang.Math.round((((float) (infos.getChecklistCompletedItemsNumber())) / infos.getChecklistItemsNumber()) / 100);
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // NoteInfosActivity_2_BinaryMutator
            case 2147: {
                percentage = java.lang.Math.round((((float) (infos.getChecklistCompletedItemsNumber())) * infos.getChecklistItemsNumber()) * 100);
                break;
            }
            default: {
            percentage = java.lang.Math.round((((float) (infos.getChecklistCompletedItemsNumber())) / infos.getChecklistItemsNumber()) * 100);
            break;
        }
    }
    break;
}
}
return ((infos.getChecklistCompletedItemsNumber() + " (") + percentage) + "%)";
}


private void populateView(android.widget.TextView textView, int numberValue) {
java.lang.String stringValue;
stringValue = (numberValue > 0) ? java.lang.String.valueOf(numberValue) : "";
populateView(textView, stringValue);
}


private void populateView(android.widget.TextView textView, java.lang.String value) {
populateView(textView, value, false);
}


private void populateView(android.widget.TextView textView, java.lang.String value, boolean forceHide) {
if (org.apache.commons.lang3.StringUtils.isNotEmpty(value) && (!forceHide)) {
textView.setText(value);
} else {
((android.view.View) (textView.getParent())).setVisibility(android.view.View.GONE);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
