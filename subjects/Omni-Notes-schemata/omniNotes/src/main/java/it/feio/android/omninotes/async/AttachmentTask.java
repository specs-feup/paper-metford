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
package it.feio.android.omninotes.async;
import java.lang.ref.WeakReference;
import it.feio.android.omninotes.OmniNotes;
import android.text.TextUtils;
import it.feio.android.omninotes.utils.StorageHelper;
import android.os.AsyncTask;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import it.feio.android.omninotes.models.listeners.OnAttachingFileListener;
import it.feio.android.omninotes.models.Attachment;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AttachmentTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, it.feio.android.omninotes.models.Attachment> {
    static final int MUID_STATIC = getMUID();
    private final java.lang.ref.WeakReference<androidx.fragment.app.Fragment> mFragmentWeakReference;

    private it.feio.android.omninotes.models.listeners.OnAttachingFileListener mOnAttachingFileListener;

    private android.net.Uri uri;

    private java.lang.String fileName;

    public AttachmentTask(androidx.fragment.app.Fragment mFragment, android.net.Uri uri, it.feio.android.omninotes.models.listeners.OnAttachingFileListener mOnAttachingFileListener) {
        this(mFragment, uri, null, mOnAttachingFileListener);
    }


    public AttachmentTask(androidx.fragment.app.Fragment mFragment, android.net.Uri uri, java.lang.String fileName, it.feio.android.omninotes.models.listeners.OnAttachingFileListener mOnAttachingFileListener) {
        mFragmentWeakReference = new java.lang.ref.WeakReference<>(mFragment);
        this.uri = uri;
        this.fileName = (android.text.TextUtils.isEmpty(fileName)) ? "" : fileName;
        this.mOnAttachingFileListener = mOnAttachingFileListener;
    }


    @java.lang.Override
    protected it.feio.android.omninotes.models.Attachment doInBackground(java.lang.Void... params) {
        it.feio.android.omninotes.models.Attachment attachment;
        attachment = it.feio.android.omninotes.utils.StorageHelper.createAttachmentFromUri(it.feio.android.omninotes.OmniNotes.getAppContext(), uri);
        if (attachment != null) {
            attachment.setName(this.fileName);
        }
        return attachment;
    }


    @java.lang.Override
    @java.lang.Deprecated
    protected void onPostExecute(it.feio.android.omninotes.models.Attachment mAttachment) {
        if (isAlive()) {
            if (mAttachment != null) {
                mOnAttachingFileListener.onAttachingFileFinished(mAttachment);
            } else {
                mOnAttachingFileListener.onAttachingFileErrorOccurred(null);
            }
        } else if (mAttachment != null) {
            it.feio.android.omninotes.utils.StorageHelper.delete(it.feio.android.omninotes.OmniNotes.getAppContext(), mAttachment.getUri().getPath());
        }
    }


    private boolean isAlive() {
        return ((((mFragmentWeakReference != null) && (mFragmentWeakReference.get() != null)) && mFragmentWeakReference.get().isAdded()) && (mFragmentWeakReference.get().getActivity() != null)) && (!mFragmentWeakReference.get().getActivity().isFinishing());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
