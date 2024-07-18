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
package it.feio.android.omninotes.utils;
import android.media.ThumbnailUtils;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_CONTACT_EXT;
import it.feio.android.omninotes.helpers.AttachmentsHelper;
import android.text.TextUtils;
import lombok.experimental.UtilityClass;
import android.net.Uri;
import android.graphics.Bitmap;
import it.feio.android.omninotes.models.Attachment;
import org.apache.commons.io.FilenameUtils;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES;
import it.feio.android.omninotes.OmniNotes;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_VIDEO;
import android.os.Looper;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_AUDIO;
import it.feio.android.omninotes.R;
import java.util.concurrent.ExecutionException;
import it.feio.android.simplegallery.util.BitmapUtils;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_IMAGE;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_SKETCH;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Glide;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class BitmapHelper {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String ANDROID_RESOURCE = "android.resource://";

    /**
     * Retrieves a the bitmap relative to attachment based on mime type
     */
    public static android.graphics.Bitmap getBitmapFromAttachment(android.content.Context mContext, it.feio.android.omninotes.models.Attachment mAttachment, int width, int height) {
        android.graphics.Bitmap bmp;
        bmp = null;
        if (it.feio.android.omninotes.helpers.AttachmentsHelper.typeOf(mAttachment, it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_VIDEO, it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_IMAGE, it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_SKETCH)) {
            bmp = it.feio.android.omninotes.utils.BitmapHelper.getImageBitmap(mContext, mAttachment, width, height);
        } else if (it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_AUDIO.equals(mAttachment.getMime_type())) {
            bmp = android.media.ThumbnailUtils.extractThumbnail(it.feio.android.simplegallery.util.BitmapUtils.decodeSampledBitmapFromResourceMemOpt(mContext.getResources().openRawResource(it.feio.android.omninotes.R.raw.play), width, height), width, height);
        } else if (it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES.equals(mAttachment.getMime_type())) {
            if (it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_CONTACT_EXT.equals(org.apache.commons.io.FilenameUtils.getExtension(mAttachment.getName()))) {
                bmp = android.media.ThumbnailUtils.extractThumbnail(it.feio.android.simplegallery.util.BitmapUtils.decodeSampledBitmapFromResourceMemOpt(mContext.getResources().openRawResource(it.feio.android.omninotes.R.raw.vcard), width, height), width, height);
            } else {
                bmp = android.media.ThumbnailUtils.extractThumbnail(it.feio.android.simplegallery.util.BitmapUtils.decodeSampledBitmapFromResourceMemOpt(mContext.getResources().openRawResource(it.feio.android.omninotes.R.raw.files), width, height), width, height);
            }
        }
        return bmp;
    }


    private static android.graphics.Bitmap getImageBitmap(android.content.Context mContext, it.feio.android.omninotes.models.Attachment mAttachment, int width, int height) {
        try {
            if (android.os.Looper.getMainLooper() == android.os.Looper.myLooper()) {
                return it.feio.android.simplegallery.util.BitmapUtils.getThumbnail(mContext, mAttachment.getUri(), width, height);
            } else {
                return com.bumptech.glide.Glide.with(it.feio.android.omninotes.OmniNotes.getAppContext()).asBitmap().apply(new com.bumptech.glide.request.RequestOptions().centerCrop().error(it.feio.android.omninotes.R.drawable.attachment_broken)).load(mAttachment.getUri()).submit(width, height).get();
            }
        } catch (java.lang.NullPointerException | java.util.concurrent.ExecutionException e) {
            return null;
        } catch (java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
            return null;
        }
    }


    public static android.net.Uri getThumbnailUri(android.content.Context mContext, it.feio.android.omninotes.models.Attachment mAttachment) {
        android.net.Uri uri;
        uri = mAttachment.getUri();
        java.lang.String mimeType;
        mimeType = it.feio.android.omninotes.utils.StorageHelper.getMimeType(uri.toString());
        if (!android.text.TextUtils.isEmpty(mimeType)) {
            java.lang.String type;
            type = mimeType.split("/")[0];
            java.lang.String subtype;
            subtype = mimeType.split("/")[1];
            switch (type) {
                case "image" :
                case "video" :
                    // Nothing to do, bitmap will be retrieved from this
                    break;
                case "audio" :
                    uri = android.net.Uri.parse(((it.feio.android.omninotes.utils.BitmapHelper.ANDROID_RESOURCE + mContext.getPackageName()) + "/") + it.feio.android.omninotes.R.raw.play);
                    break;
                default :
                    int drawable;
                    drawable = ("x-vcard".equals(subtype)) ? it.feio.android.omninotes.R.raw.vcard : it.feio.android.omninotes.R.raw.files;
                    uri = android.net.Uri.parse(((it.feio.android.omninotes.utils.BitmapHelper.ANDROID_RESOURCE + mContext.getPackageName()) + "/") + drawable);
                    break;
            }
        } else {
            uri = android.net.Uri.parse(((it.feio.android.omninotes.utils.BitmapHelper.ANDROID_RESOURCE + mContext.getPackageName()) + "/") + it.feio.android.omninotes.R.raw.files);
        }
        return uri;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
