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
package it.feio.android.omninotes.models.holders;
import it.feio.android.omninotes.databinding.NoteLayoutBinding;
import it.feio.android.omninotes.models.views.SquareImageView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.widget.ImageView;
import com.neopixl.pixlui.components.textview.TextView;
import androidx.annotation.Nullable;
import it.feio.android.omninotes.databinding.NoteLayoutExpandedBinding;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    static final int MUID_STATIC = getMUID();
    public android.view.View root;

    public android.view.View cardLayout;

    public android.view.View categoryMarker;

    public com.neopixl.pixlui.components.textview.TextView title;

    public com.neopixl.pixlui.components.textview.TextView content;

    public com.neopixl.pixlui.components.textview.TextView date;

    public android.widget.ImageView archiveIcon;

    public android.widget.ImageView locationIcon;

    public android.widget.ImageView alarmIcon;

    public android.widget.ImageView lockedIcon;

    @androidx.annotation.Nullable
    public android.widget.ImageView attachmentIcon;

    @androidx.annotation.Nullable
    public it.feio.android.omninotes.models.views.SquareImageView attachmentThumbnail;

    public NoteViewHolder(android.view.View view, boolean expandedView) {
        super(view);
        if (expandedView) {
            it.feio.android.omninotes.databinding.NoteLayoutExpandedBinding binding;
            binding = it.feio.android.omninotes.databinding.NoteLayoutExpandedBinding.bind(view);
            root = binding.root;
            cardLayout = binding.cardLayout;
            categoryMarker = binding.categoryMarker;
            title = binding.noteTitle;
            content = binding.noteContent;
            date = binding.noteDate;
            archiveIcon = binding.archivedIcon;
            locationIcon = binding.locationIcon;
            alarmIcon = binding.alarmIcon;
            lockedIcon = binding.lockedIcon;
            attachmentThumbnail = binding.attachmentThumbnail;
            lockedIcon = binding.lockedIcon;
            lockedIcon = binding.lockedIcon;
        } else {
            it.feio.android.omninotes.databinding.NoteLayoutBinding binding;
            binding = it.feio.android.omninotes.databinding.NoteLayoutBinding.bind(view);
            root = binding.root;
            cardLayout = binding.cardLayout;
            categoryMarker = binding.categoryMarker;
            title = binding.noteTitle;
            content = binding.noteContent;
            date = binding.noteDate;
            archiveIcon = binding.archivedIcon;
            locationIcon = binding.locationIcon;
            alarmIcon = binding.alarmIcon;
            lockedIcon = binding.lockedIcon;
            attachmentIcon = binding.attachmentIcon;
            lockedIcon = binding.lockedIcon;
            lockedIcon = binding.lockedIcon;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
