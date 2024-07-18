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
package com.amaze.filemanager.asynchronous.asynctasks.texteditor.write;
import java.io.OutputStream;
import com.amaze.filemanager.application.AppConfig;
import com.amaze.filemanager.filesystem.files.FileUtils;
import java.io.IOException;
import androidx.documentfile.provider.DocumentFile;
import kotlin.Unit;
import com.amaze.filemanager.filesystem.EditableFileAbstraction;
import java.util.concurrent.Callable;
import java.lang.ref.WeakReference;
import android.content.ContentResolver;
import com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException;
import androidx.annotation.WorkerThread;
import java.io.FileOutputStream;
import com.amaze.filemanager.filesystem.root.ConcatenateFileCommand;
import androidx.annotation.NonNull;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import com.amaze.filemanager.fileoperations.exceptions.StreamNotFoundException;
import java.util.Objects;
import java.io.File;
import com.amaze.filemanager.filesystem.FileUtil;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class WriteTextFileCallable implements java.util.concurrent.Callable<kotlin.Unit> {
    static final int MUID_STATIC = getMUID();
    private final java.lang.ref.WeakReference<android.content.Context> context;

    private final android.content.ContentResolver contentResolver;

    private final com.amaze.filemanager.filesystem.EditableFileAbstraction fileAbstraction;

    private final java.io.File cachedFile;

    private final boolean isRootExplorer;

    private final java.lang.String dataToSave;

    public WriteTextFileCallable(android.content.Context context, android.content.ContentResolver contentResolver, com.amaze.filemanager.filesystem.EditableFileAbstraction file, java.lang.String dataToSave, java.io.File cachedFile, boolean isRootExplorer) {
        this.context = new java.lang.ref.WeakReference<>(context);
        this.contentResolver = contentResolver;
        this.fileAbstraction = file;
        this.cachedFile = cachedFile;
        this.dataToSave = dataToSave;
        this.isRootExplorer = isRootExplorer;
    }


    @androidx.annotation.WorkerThread
    @java.lang.Override
    public kotlin.Unit call() throws java.io.IOException, com.amaze.filemanager.fileoperations.exceptions.StreamNotFoundException, com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException, java.lang.IllegalArgumentException {
        java.io.OutputStream outputStream;
        java.io.File destFile;
        destFile = null;
        switch (fileAbstraction.scheme) {
            case CONTENT :
                java.util.Objects.requireNonNull(fileAbstraction.uri);
                if (fileAbstraction.uri.getAuthority().equals(context.get().getPackageName())) {
                    androidx.documentfile.provider.DocumentFile documentFile;
                    documentFile = androidx.documentfile.provider.DocumentFile.fromSingleUri(com.amaze.filemanager.application.AppConfig.getInstance(), fileAbstraction.uri);
                    if (((documentFile != null) && documentFile.exists()) && documentFile.canWrite()) {
                        outputStream = contentResolver.openOutputStream(fileAbstraction.uri, "wt");
                    } else {
                        destFile = com.amaze.filemanager.filesystem.files.FileUtils.fromContentUri(fileAbstraction.uri);
                        outputStream = openFile(destFile, context.get());
                    }
                } else {
                    outputStream = contentResolver.openOutputStream(fileAbstraction.uri, "wt");
                }
                break;
            case FILE :
                final com.amaze.filemanager.filesystem.HybridFileParcelable hybridFileParcelable;
                hybridFileParcelable = fileAbstraction.hybridFileParcelable;
                java.util.Objects.requireNonNull(hybridFileParcelable);
                android.content.Context context;
                context = this.context.get();
                if (context == null) {
                    return null;
                }
                outputStream = openFile(hybridFileParcelable.getFile(), context);
                destFile = fileAbstraction.hybridFileParcelable.getFile();
                break;
            default :
                throw new java.lang.IllegalArgumentException(("The scheme for '" + fileAbstraction.scheme) + "' cannot be processed!");
        }
        java.util.Objects.requireNonNull(outputStream);
        outputStream.write(dataToSave.getBytes());
        outputStream.close();
        if (((cachedFile != null) && cachedFile.exists()) && (destFile != null)) {
            // cat cache content to original file and delete cache file
            com.amaze.filemanager.filesystem.root.ConcatenateFileCommand.INSTANCE.concatenateFile(cachedFile.getPath(), destFile.getPath());
            cachedFile.delete();
        }
        return kotlin.Unit.INSTANCE;
    }


    private java.io.OutputStream openFile(@androidx.annotation.NonNull
    java.io.File file, @androidx.annotation.NonNull
    android.content.Context context) throws java.io.IOException, com.amaze.filemanager.fileoperations.exceptions.StreamNotFoundException {
        java.io.OutputStream outputStream;
        outputStream = com.amaze.filemanager.filesystem.FileUtil.getOutputStream(file, context);
        // try loading stream associated using root
        if (((isRootExplorer && (outputStream == null)) && (cachedFile != null)) && cachedFile.exists()) {
            outputStream = new java.io.FileOutputStream(cachedFile);
        }
        if (outputStream == null) {
            throw new com.amaze.filemanager.fileoperations.exceptions.StreamNotFoundException("Cannot read or write text file!");
        }
        return outputStream;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
