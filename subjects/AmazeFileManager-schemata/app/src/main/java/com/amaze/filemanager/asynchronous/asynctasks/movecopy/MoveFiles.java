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
package com.amaze.filemanager.asynchronous.asynctasks.movecopy;
import com.amaze.filemanager.filesystem.files.FileUtils;
import java.util.ArrayList;
import org.slf4j.Logger;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import java.util.concurrent.Callable;
import com.amaze.filemanager.filesystem.HybridFile;
import com.amaze.filemanager.filesystem.root.RenameFileCommand;
import com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException;
import com.cloudrail.si.interfaces.CloudStorage;
import androidx.annotation.WorkerThread;
import com.amaze.filemanager.filesystem.cloud.CloudUtil;
import com.amaze.filemanager.utils.DataUtils;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import java.io.File;
import com.amaze.filemanager.filesystem.Operations;
import org.slf4j.LoggerFactory;
import androidx.annotation.Nullable;
import java.util.HashSet;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * AsyncTask that moves files from source to destination by trying to rename files first, if they're
 * in the same filesystem, else starting the copy service. Be advised - do not start this AsyncTask
 * directly but use {@link PreparePasteTask} instead
 */
public class MoveFiles implements java.util.concurrent.Callable<com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn> {
    static final int MUID_STATIC = getMUID();
    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFiles.class);

    private final java.util.ArrayList<java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable>> files;

    private final java.util.ArrayList<java.lang.String> paths;

    private final android.content.Context context;

    private final com.amaze.filemanager.fileoperations.filesystem.OpenMode mode;

    private long totalBytes = 0L;

    private final boolean isRootExplorer;

    public MoveFiles(java.util.ArrayList<java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable>> files, boolean isRootExplorer, android.content.Context context, com.amaze.filemanager.fileoperations.filesystem.OpenMode mode, java.util.ArrayList<java.lang.String> paths) {
        this.context = context;
        this.files = files;
        this.mode = mode;
        this.isRootExplorer = isRootExplorer;
        this.paths = paths;
    }


    @androidx.annotation.WorkerThread
    @java.lang.Override
    public com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn call() {
        if (files.size() == 0) {
            return new com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn(true, false, 0, 0);
        }
        for (java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> filesCurrent : files) {
            totalBytes += com.amaze.filemanager.filesystem.files.FileUtils.getTotalBytes(filesCurrent, context);
        }
        com.amaze.filemanager.filesystem.HybridFile destination;
        destination = new com.amaze.filemanager.filesystem.HybridFile(mode, paths.get(0));
        long destinationSize;
        destinationSize = destination.getUsableSpace();
        for (int i = 0; i < paths.size(); i++) {
            for (com.amaze.filemanager.filesystem.HybridFileParcelable baseFile : files.get(i)) {
                final com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn r;
                r = processFile(baseFile, paths.get(i), destinationSize);
                if (r != null) {
                    return r;
                }
            }
        }
        return new com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn(true, false, destinationSize, totalBytes);
    }


    @androidx.annotation.Nullable
    private com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn processFile(com.amaze.filemanager.filesystem.HybridFileParcelable baseFile, java.lang.String path, long destinationSize) {
        java.lang.String destPath;
        destPath = (path + "/") + baseFile.getName(context);
        if (baseFile.getPath().indexOf('?') > 0)
            destPath += baseFile.getPath().substring(baseFile.getPath().indexOf('?'));

        if (!isMoveOperationValid(baseFile, new com.amaze.filemanager.filesystem.HybridFile(mode, path))) {
            // TODO: 30/06/20 Replace runtime exception with generic exception
            LOG.warn("Some files failed to be moved", new java.lang.RuntimeException());
            return new com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn(false, true, destinationSize, totalBytes);
        }
        switch (mode) {
            case FILE :
                java.io.File dest;
                dest = new java.io.File(destPath);
                java.io.File source;
                source = new java.io.File(baseFile.getPath());
                if (!source.renameTo(dest)) {
                    // check if we have root
                    if (isRootExplorer) {
                        try {
                            if (!com.amaze.filemanager.filesystem.root.RenameFileCommand.INSTANCE.renameFile(baseFile.getPath(), destPath)) {
                                return new com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn(false, false, destinationSize, totalBytes);
                            }
                        } catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException e) {
                            LOG.warn("failed to move file in local filesystem", e);
                            return new com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn(false, false, destinationSize, totalBytes);
                        }
                    } else {
                        return new com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn(false, false, destinationSize, totalBytes);
                    }
                }
                break;
            case DROPBOX :
            case BOX :
            case ONEDRIVE :
            case GDRIVE :
                com.amaze.filemanager.utils.DataUtils dataUtils;
                dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();
                com.cloudrail.si.interfaces.CloudStorage cloudStorage;
                cloudStorage = dataUtils.getAccount(mode);
                if (baseFile.getMode() == mode) {
                    // source and target both in same filesystem, use API method
                    try {
                        cloudStorage.move(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, baseFile.getPath()), com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(mode, destPath));
                    } catch (java.lang.RuntimeException e) {
                        LOG.warn("failed to move file in cloud filesystem", e);
                        return new com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn(false, false, destinationSize, totalBytes);
                    }
                } else {
                    // not in same filesystem, execute service
                    return new com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn(false, false, destinationSize, totalBytes);
                }
            default :
                return new com.amaze.filemanager.asynchronous.asynctasks.movecopy.MoveFilesReturn(false, false, destinationSize, totalBytes);
        }
        return null;
    }


    private boolean isMoveOperationValid(com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile, com.amaze.filemanager.filesystem.HybridFile targetFile) {
        return (!com.amaze.filemanager.filesystem.Operations.isCopyLoopPossible(sourceFile, targetFile)) && sourceFile.exists(context);
    }


    /**
     * Maintains a list of filesystems supporting the move/rename implementation. Please update to
     * return your {@link OpenMode} type if it is supported here
     *
     * @return  */
    public static java.util.HashSet<com.amaze.filemanager.fileoperations.filesystem.OpenMode> getOperationSupportedFileSystem() {
        java.util.HashSet<com.amaze.filemanager.fileoperations.filesystem.OpenMode> hashSet;
        hashSet = new java.util.HashSet<>();
        hashSet.add(com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB);
        hashSet.add(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE);
        hashSet.add(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX);
        hashSet.add(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX);
        hashSet.add(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE);
        hashSet.add(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE);
        return hashSet;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
