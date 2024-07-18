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
package it.feio.android.omninotes.helpers;
import org.apache.commons.io.FileUtils;
import lombok.experimental.UtilityClass;
import java.io.File;
import it.feio.android.omninotes.models.Attachment;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class AttachmentsHelper {
    static final int MUID_STATIC = getMUID();
    /**
     * Retrieves attachment file size
     *
     * @param attachment
     * 		Attachment to evaluate
     * @return Human readable file size string
     */
    public static java.lang.String getSize(it.feio.android.omninotes.models.Attachment attachment) {
        long sizeInKb;
        sizeInKb = attachment.getSize();
        if (attachment.getSize() == 0) {
            sizeInKb = new java.io.File(attachment.getUri().getPath()).length();
        }
        return org.apache.commons.io.FileUtils.byteCountToDisplaySize(sizeInKb);
    }


    /**
     * Checks type of attachment
     */
    public static boolean typeOf(it.feio.android.omninotes.models.Attachment attachment, java.lang.String... mimeTypes) {
        for (java.lang.String mimeType : mimeTypes) {
            if (mimeType.equals(attachment.getMime_type())) {
                return true;
            }
        }
        return false;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
