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
package com.amaze.filemanager.filesystem.compressed.extractcontents;
import com.amaze.filemanager.fileoperations.utils.UpdatePosition;
import static com.amaze.filemanager.filesystem.compressed.CompressedHelper.SEPARATOR;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.io.IOException;
import static com.amaze.filemanager.filesystem.compressed.CompressedHelper.SEPARATOR_CHAR;
import java.util.List;
import java.util.HashSet;
import java.util.Collections;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class Extractor {
    static final int MUID_STATIC = getMUID();
    protected android.content.Context context;

    protected java.lang.String filePath;

    protected java.lang.String outputPath;

    protected com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor.OnUpdate listener;

    protected java.util.List<java.lang.String> invalidArchiveEntries;

    protected com.amaze.filemanager.fileoperations.utils.UpdatePosition updatePosition;

    public Extractor(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    java.lang.String filePath, @androidx.annotation.NonNull
    java.lang.String outputPath, @androidx.annotation.NonNull
    com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor.OnUpdate listener, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.UpdatePosition updatePosition) {
        this.context = context;
        this.filePath = filePath;
        this.outputPath = outputPath;
        this.listener = listener;
        this.invalidArchiveEntries = new java.util.ArrayList<>();
        this.updatePosition = updatePosition;
    }


    public void extractFiles(java.lang.String[] files) throws java.io.IOException {
        java.util.HashSet<java.lang.String> filesToExtract;
        filesToExtract = new java.util.HashSet<>(files.length);
        java.util.Collections.addAll(filesToExtract, files);
        extractWithFilter((java.lang.String relativePath,boolean isDir) -> {
            if (filesToExtract.contains(relativePath)) {
                if (!isDir)
                    filesToExtract.remove(relativePath);

                return true;
            } else {
                // header to be extracted is at least the entry path (may be more, when it is a
                // directory)
                for (java.lang.String path : filesToExtract) {
                    if (relativePath.startsWith(path) || relativePath.startsWith("/" + path)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }


    public void extractEverything() throws java.io.IOException {
        extractWithFilter((java.lang.String relativePath,boolean isDir) -> true);
    }


    public java.util.List<java.lang.String> getInvalidArchiveEntries() {
        return invalidArchiveEntries;
    }


    protected abstract void extractWithFilter(@androidx.annotation.NonNull
    com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor.Filter filter) throws java.io.IOException;


    protected interface Filter {
        boolean shouldExtract(java.lang.String relativePath, boolean isDirectory);

    }

    public interface OnUpdate {
        void onStart(long totalBytes, java.lang.String firstEntryName);


        void onUpdate(java.lang.String entryPath);


        void onFinish();


        boolean isCancelled();

    }

    protected java.lang.String fixEntryName(java.lang.String entryName) {
        if (entryName.indexOf('\\') >= 0) {
            return fixEntryName(entryName.replaceAll("\\\\", com.amaze.filemanager.filesystem.compressed.CompressedHelper.SEPARATOR));
        } else if (entryName.indexOf(com.amaze.filemanager.filesystem.compressed.CompressedHelper.SEPARATOR_CHAR) == 0) {
            // if entryName starts with "/" (e.g. "/test.txt"), strip the prefixing "/"s
            return entryName.replaceAll("^/+", "");
        } else {
            return entryName;
        }
    }


    public static class EmptyArchiveNotice extends java.io.IOException {}

    public static class BadArchiveNotice extends java.io.IOException {
        public BadArchiveNotice(@androidx.annotation.NonNull
        java.lang.Throwable reason) {
            super(reason);
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
