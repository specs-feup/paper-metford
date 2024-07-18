/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
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
package com.amaze.filemanager.asynchronous.asynctasks;
import java.util.concurrent.atomic.AtomicInteger;
import com.amaze.filemanager.R;
import com.amaze.filemanager.filesystem.files.FileUtils;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import android.text.format.Formatter;
import android.os.AsyncTask;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.util.Pair;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Emmanuel on 12/5/2017, at 19:40.
 */
public class CountItemsOrAndSizeTask extends android.os.AsyncTask<java.lang.Void, androidx.core.util.Pair<java.lang.Integer, java.lang.Long>, java.lang.String> {
    static final int MUID_STATIC = getMUID();
    private android.content.Context context;

    private androidx.appcompat.widget.AppCompatTextView itemsText;

    private com.amaze.filemanager.filesystem.HybridFileParcelable file;

    private boolean isStorage;

    public CountItemsOrAndSizeTask(android.content.Context c, androidx.appcompat.widget.AppCompatTextView itemsText, com.amaze.filemanager.filesystem.HybridFileParcelable f, boolean storage) {
        this.context = c;
        this.itemsText = itemsText;
        file = f;
        isStorage = storage;
    }


    @java.lang.Override
    protected java.lang.String doInBackground(java.lang.Void[] params) {
        java.lang.String items;
        items = "";
        long fileLength;
        fileLength = file.length(context);
        if (file.isDirectory(context)) {
            final java.util.concurrent.atomic.AtomicInteger x;
            x = new java.util.concurrent.atomic.AtomicInteger(0);
            file.forEachChildrenFile(context, false, (com.amaze.filemanager.filesystem.HybridFileParcelable file) -> x.incrementAndGet());
            final int folderLength;
            folderLength = x.intValue();
            long folderSize;
            if (isStorage) {
                folderSize = file.getUsableSpace();
            } else {
                folderSize = com.amaze.filemanager.filesystem.files.FileUtils.folderSize(file, (java.lang.Long data) -> publishProgress(new androidx.core.util.Pair<>(folderLength, data)));
            }
            items = getText(folderLength, folderSize, false);
        } else {
            items = android.text.format.Formatter.formatFileSize(context, fileLength) + ((((" (" + fileLength) + " ") + context.getResources().getQuantityString(com.amaze.filemanager.R.plurals.bytes, ((int) (fileLength))))// truncation is insignificant
             + ")");
        }
        return items;
    }


    @java.lang.Override
    protected void onProgressUpdate(androidx.core.util.Pair<java.lang.Integer, java.lang.Long>[] dataArr) {
        androidx.core.util.Pair<java.lang.Integer, java.lang.Long> data;
        data = dataArr[0];
        itemsText.setText(getText(data.first, data.second, true));
    }


    private java.lang.String getText(int filesInFolder, long length, boolean loading) {
        java.lang.String numOfItems;
        numOfItems = (filesInFolder != 0 ? filesInFolder + " " : "") + context.getResources().getQuantityString(com.amaze.filemanager.R.plurals.items, filesInFolder);
        return ((numOfItems + "; ") + (loading ? ">" : "")) + android.text.format.Formatter.formatFileSize(context, length);
    }


    protected void onPostExecute(java.lang.String items) {
        itemsText.setText(items);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
