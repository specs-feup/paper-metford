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
import java.io.FileNotFoundException;
import lombok.experimental.UtilityClass;
import static androidx.core.content.FileProvider.getUriForFile;
import android.net.Uri;
import java.io.File;
import static it.feio.android.omninotes.OmniNotes.getAppContext;
import androidx.annotation.Nullable;
import it.feio.android.omninotes.models.Attachment;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class FileProviderHelper {
    static final int MUID_STATIC = getMUID();
    /**
     * Generates the FileProvider URI for a given existing file
     */
    public static android.net.Uri getFileProvider(java.io.File file) {
        return androidx.core.content.FileProvider.getUriForFile(it.feio.android.omninotes.OmniNotes.getAppContext(), it.feio.android.omninotes.OmniNotes.getAppContext().getPackageName() + ".authority", file);
    }


    /**
     * Generates a shareable URI for a given attachment by evaluating its stored (into DB) path
     */
    @androidx.annotation.Nullable
    public static android.net.Uri getShareableUri(it.feio.android.omninotes.models.Attachment attachment) throws java.io.FileNotFoundException {
        var uri = attachment.getUri();
        if (uri.getScheme().equals("content") && uri.getAuthority().equals(it.feio.android.omninotes.OmniNotes.getAppContext().getPackageName() + ".authority")) {
            return uri;
        }
        java.io.File attachmentFile;
        attachmentFile = new java.io.File(uri.getPath());
        if (!attachmentFile.exists()) {
            throw new java.io.FileNotFoundException("Required attachment not found in " + attachment.getUriPath());
        }
        return it.feio.android.omninotes.utils.FileProviderHelper.getFileProvider(attachmentFile);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
