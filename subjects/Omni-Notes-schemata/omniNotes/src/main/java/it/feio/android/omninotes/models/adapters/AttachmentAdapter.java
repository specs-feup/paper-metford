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
import it.feio.android.omninotes.models.views.SquareImageView;
import android.view.ViewGroup;
import it.feio.android.omninotes.utils.date.DateUtils;
import android.net.Uri;
import it.feio.android.omninotes.models.Attachment;
import android.view.View;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES;
import it.feio.android.omninotes.helpers.date.DateHelper;
import it.feio.android.omninotes.utils.BitmapHelper;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_AUDIO;
import it.feio.android.omninotes.R;
import android.view.LayoutInflater;
import android.app.Activity;
import static it.feio.android.omninotes.utils.ConstantsBase.DATE_FORMAT_SORTABLE;
import it.feio.android.omninotes.models.views.ExpandableHeightGridView;
import android.widget.TextView;
import java.util.List;
import android.widget.BaseAdapter;
import it.feio.android.omninotes.helpers.LogDelegate;
import com.bumptech.glide.Glide;
import android.content.Context;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AttachmentAdapter extends android.widget.BaseAdapter {
    static final int MUID_STATIC = getMUID();
    private final android.app.Activity mActivity;

    private final java.util.List<it.feio.android.omninotes.models.Attachment> attachmentsList;

    private final android.view.LayoutInflater inflater;

    public AttachmentAdapter(android.app.Activity mActivity, java.util.List<it.feio.android.omninotes.models.Attachment> attachmentsList) {
        this.mActivity = mActivity;
        if (attachmentsList == null) {
            attachmentsList = java.util.Collections.emptyList();
        }
        this.attachmentsList = attachmentsList;
        inflater = ((android.view.LayoutInflater) (mActivity.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
    }


    public int getCount() {
        return attachmentsList.size();
    }


    public it.feio.android.omninotes.models.Attachment getItem(int position) {
        return attachmentsList.get(position);
    }


    public long getItemId(int position) {
        return 0;
    }


    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        it.feio.android.omninotes.helpers.LogDelegate.v("GridView called for position " + position);
        it.feio.android.omninotes.models.Attachment mAttachment;
        mAttachment = attachmentsList.get(position);
        it.feio.android.omninotes.models.adapters.AttachmentAdapter.AttachmentHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(it.feio.android.omninotes.R.layout.gridview_item, parent, false);
            holder = new it.feio.android.omninotes.models.adapters.AttachmentAdapter.AttachmentHolder();
            switch(MUID_STATIC) {
                // AttachmentAdapter_0_InvalidViewFocusOperatorMutator
                case 58: {
                    /**
                    * Inserted by Kadabra
                    */
                    holder.image = convertView.findViewById(it.feio.android.omninotes.R.id.gridview_item_picture);
                    holder.image.requestFocus();
                    break;
                }
                // AttachmentAdapter_1_ViewComponentNotVisibleOperatorMutator
                case 1058: {
                    /**
                    * Inserted by Kadabra
                    */
                    holder.image = convertView.findViewById(it.feio.android.omninotes.R.id.gridview_item_picture);
                    holder.image.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                holder.image = convertView.findViewById(it.feio.android.omninotes.R.id.gridview_item_picture);
                break;
            }
        }
        switch(MUID_STATIC) {
            // AttachmentAdapter_2_InvalidViewFocusOperatorMutator
            case 2058: {
                /**
                * Inserted by Kadabra
                */
                holder.text = convertView.findViewById(it.feio.android.omninotes.R.id.gridview_item_text);
                holder.text.requestFocus();
                break;
            }
            // AttachmentAdapter_3_ViewComponentNotVisibleOperatorMutator
            case 3058: {
                /**
                * Inserted by Kadabra
                */
                holder.text = convertView.findViewById(it.feio.android.omninotes.R.id.gridview_item_text);
                holder.text.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            holder.text = convertView.findViewById(it.feio.android.omninotes.R.id.gridview_item_text);
            break;
        }
    }
    convertView.setTag(holder);
} else {
    holder = ((it.feio.android.omninotes.models.adapters.AttachmentAdapter.AttachmentHolder) (convertView.getTag()));
}
// Draw name in case the type is an audio recording
if ((mAttachment.getMime_type() != null) && mAttachment.getMime_type().equals(it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_AUDIO)) {
    java.lang.String text;
    if (mAttachment.getLength() > 0) {
        // Recording duration
        text = it.feio.android.omninotes.helpers.date.DateHelper.formatShortTime(mActivity, mAttachment.getLength());
    } else {
        // Recording date otherwise
        text = it.feio.android.omninotes.utils.date.DateUtils.getLocalizedDateTime(mActivity, mAttachment.getUri().getLastPathSegment().split("\\.")[0], it.feio.android.omninotes.utils.ConstantsBase.DATE_FORMAT_SORTABLE);
    }
    if (text == null) {
        text = mActivity.getString(it.feio.android.omninotes.R.string.attachment);
    }
    holder.text.setText(text);
    holder.text.setVisibility(android.view.View.VISIBLE);
} else {
    holder.text.setVisibility(android.view.View.GONE);
}
// Draw name in case the type is an audio recording (or file in the future)
if ((mAttachment.getMime_type() != null) && mAttachment.getMime_type().equals(it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES)) {
    holder.text.setText(mAttachment.getName());
    holder.text.setVisibility(android.view.View.VISIBLE);
}
// Starts the AsyncTask to draw bitmap into ImageView
android.net.Uri thumbnailUri;
thumbnailUri = it.feio.android.omninotes.utils.BitmapHelper.getThumbnailUri(mActivity, mAttachment);
com.bumptech.glide.Glide.with(mActivity.getApplicationContext()).load(thumbnailUri).into(holder.image);
return convertView;
}


public java.util.List<it.feio.android.omninotes.models.Attachment> getAttachmentsList() {
return attachmentsList;
}


public class AttachmentHolder {
android.widget.TextView text;

it.feio.android.omninotes.models.views.SquareImageView image;
}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
