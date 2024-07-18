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
package com.amaze.filemanager.asynchronous.asynctasks.texteditor.read;
import com.amaze.filemanager.filesystem.root.CopyFilesCommand;
import com.amaze.filemanager.application.AppConfig;
import com.amaze.filemanager.filesystem.files.FileUtils;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import androidx.documentfile.provider.DocumentFile;
import com.amaze.filemanager.filesystem.EditableFileAbstraction;
import com.amaze.filemanager.ui.activities.texteditor.ReturnedValueOnReadFile;
import java.io.FileInputStream;
import java.util.concurrent.Callable;
import android.content.ContentResolver;
import com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException;
import androidx.annotation.WorkerThread;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import com.amaze.filemanager.fileoperations.exceptions.StreamNotFoundException;
import java.util.Objects;
import java.io.File;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ReadTextFileCallable implements java.util.concurrent.Callable<com.amaze.filemanager.ui.activities.texteditor.ReturnedValueOnReadFile> {
    static final int MUID_STATIC = getMUID();
    public static final int MAX_FILE_SIZE_CHARS = 50 * 1024;

    private final android.content.ContentResolver contentResolver;

    private final com.amaze.filemanager.filesystem.EditableFileAbstraction fileAbstraction;

    private final java.io.File externalCacheDir;

    private final boolean isRootExplorer;

    private java.io.File cachedFile = null;

    public ReadTextFileCallable(android.content.ContentResolver contentResolver, com.amaze.filemanager.filesystem.EditableFileAbstraction file, java.io.File cacheDir, boolean isRootExplorer) {
        this.contentResolver = contentResolver;
        this.fileAbstraction = file;
        this.externalCacheDir = cacheDir;
        this.isRootExplorer = isRootExplorer;
    }


    @androidx.annotation.WorkerThread
    @java.lang.Override
    public com.amaze.filemanager.ui.activities.texteditor.ReturnedValueOnReadFile call() throws com.amaze.filemanager.fileoperations.exceptions.StreamNotFoundException, java.io.IOException, java.lang.OutOfMemoryError, com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException {
        java.io.InputStream inputStream;
        switch (fileAbstraction.scheme) {
            case CONTENT :
                java.util.Objects.requireNonNull(fileAbstraction.uri);
                final com.amaze.filemanager.application.AppConfig appConfig;
                appConfig = com.amaze.filemanager.application.AppConfig.getInstance();
                if (fileAbstraction.uri.getAuthority().equals(appConfig.getPackageName())) {
                    androidx.documentfile.provider.DocumentFile documentFile;
                    documentFile = androidx.documentfile.provider.DocumentFile.fromSingleUri(appConfig, fileAbstraction.uri);
                    if (((documentFile != null) && documentFile.exists()) && documentFile.canWrite()) {
                        inputStream = contentResolver.openInputStream(documentFile.getUri());
                    } else {
                        inputStream = loadFile(com.amaze.filemanager.filesystem.files.FileUtils.fromContentUri(fileAbstraction.uri));
                    }
                } else {
                    inputStream = contentResolver.openInputStream(fileAbstraction.uri);
                }
                break;
            case FILE :
                final com.amaze.filemanager.filesystem.HybridFileParcelable hybridFileParcelable;
                hybridFileParcelable = fileAbstraction.hybridFileParcelable;
                java.util.Objects.requireNonNull(hybridFileParcelable);
                java.io.File file;
                file = hybridFileParcelable.getFile();
                inputStream = loadFile(file);
                break;
            default :
                throw new java.lang.IllegalArgumentException(("The scheme for '" + fileAbstraction.scheme) + "' cannot be processed!");
        }
        java.util.Objects.requireNonNull(inputStream);
        java.io.InputStreamReader inputStreamReader;
        inputStreamReader = new java.io.InputStreamReader(inputStream);
        char[] buffer;
        buffer = new char[com.amaze.filemanager.asynchronous.asynctasks.texteditor.read.ReadTextFileCallable.MAX_FILE_SIZE_CHARS];
        final int readChars;
        readChars = inputStreamReader.read(buffer);
        boolean tooLong;
        tooLong = (-1) != inputStream.read();
        inputStreamReader.close();
        final java.lang.String fileContents;
        if (readChars == (-1)) {
            fileContents = "";
        } else {
            fileContents = java.lang.String.valueOf(buffer, 0, readChars);
        }
        return new com.amaze.filemanager.ui.activities.texteditor.ReturnedValueOnReadFile(fileContents, cachedFile, tooLong);
    }


    private java.io.InputStream loadFile(java.io.File file) throws com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException, java.io.IOException {
        java.io.InputStream inputStream;
        if ((!file.canWrite()) && isRootExplorer) {
            // try loading stream associated using root
            cachedFile = new java.io.File(externalCacheDir, file.getName());
            // Scrap previously cached file if exist
            if (cachedFile.exists()) {
                cachedFile.delete();
            }
            cachedFile.createNewFile();
            cachedFile.deleteOnExit();
            // creating a cache file
            com.amaze.filemanager.filesystem.root.CopyFilesCommand.INSTANCE.copyFiles(file.getAbsolutePath(), cachedFile.getPath());
            inputStream = new java.io.FileInputStream(cachedFile);
        } else if (file.canRead()) {
            // readable file in filesystem
            try {
                inputStream = new java.io.FileInputStream(file.getAbsolutePath());
            } catch (java.io.FileNotFoundException e) {
                throw new java.io.FileNotFoundException(("Unable to open file [" + file.getAbsolutePath()) + "] for reading");
            }
        } else {
            throw new java.io.IOException("Cannot read or write text file!");
        }
        return inputStream;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
