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
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;
import com.amaze.trashbin.TrashBinFile;
import jcifs.smb.SmbAuthException;
import android.database.Cursor;
import kotlin.collections.CollectionsKt;
import com.amaze.filemanager.utils.OnFileFound;
import androidx.annotation.NonNull;
import com.amaze.filemanager.filesystem.RootHelper;
import com.amaze.filemanager.filesystem.files.FileListSorter;
import java.util.List;
import com.amaze.filemanager.filesystem.root.ListFilesCommand;
import java.util.Collections;
import java.util.LinkedList;
import com.amaze.filemanager.utils.OnAsyncTaskFinished;
import com.amaze.trashbin.TrashBin;
import android.os.AsyncTask;
import com.amaze.filemanager.database.SortHandler;
import com.amaze.filemanager.adapters.data.LayoutElementParcelable;
import com.amaze.filemanager.filesystem.HybridFile;
import android.content.ContentResolver;
import com.amaze.filemanager.filesystem.cloud.CloudUtil;
import com.amaze.filemanager.utils.DataUtils;
import android.provider.MediaStore;
import com.amaze.filemanager.filesystem.SafRootHolder;
import java.io.File;
import com.amaze.filemanager.database.UtilsHandler;
import androidx.annotation.Nullable;
import com.amaze.filemanager.fileoperations.exceptions.CloudPluginException;
import com.amaze.filemanager.ui.activities.MainActivityViewModel;
import com.amaze.filemanager.utils.GenericExtKt;
import android.text.format.Formatter;
import android.net.Uri;
import org.slf4j.Logger;
import static android.os.Build.VERSION.SDK_INT;
import java.lang.ref.WeakReference;
import com.amaze.filemanager.ui.fragments.MainFragment;
import com.amaze.filemanager.utils.OTGUtil;
import com.cloudrail.si.interfaces.CloudStorage;
import android.content.pm.ResolveInfo;
import static android.os.Build.VERSION_CODES.Q;
import com.amaze.filemanager.R;
import android.content.pm.PackageManager;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.filesystem.FileProperties;
import androidx.core.util.Pair;
import jcifs.smb.SmbException;
import com.amaze.filemanager.ui.fragments.CloudSheetFragment;
import java.util.Calendar;
import com.amaze.filemanager.application.AppConfig;
import android.os.Bundle;
import android.content.Intent;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import jcifs.smb.SmbFile;
import java.util.Date;
import com.amaze.filemanager.filesystem.files.sort.SortType;
import com.amaze.filemanager.ui.fragments.data.MainFragmentViewModel;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class LoadFilesListTask extends android.os.AsyncTask<java.lang.Void, java.lang.Throwable, androidx.core.util.Pair<com.amaze.filemanager.fileoperations.filesystem.OpenMode, java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable>>> {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.asynchronous.asynctasks.LoadFilesListTask.class);

    private java.lang.String path;

    private java.lang.ref.WeakReference<com.amaze.filemanager.ui.fragments.MainFragment> mainFragmentReference;

    private java.lang.ref.WeakReference<android.content.Context> context;

    private com.amaze.filemanager.fileoperations.filesystem.OpenMode openmode;

    private boolean showHiddenFiles;

    private boolean showThumbs;

    private com.amaze.filemanager.utils.DataUtils dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();

    private com.amaze.filemanager.utils.OnAsyncTaskFinished<androidx.core.util.Pair<com.amaze.filemanager.fileoperations.filesystem.OpenMode, java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable>>> listener;

    private boolean forceReload;

    public LoadFilesListTask(android.content.Context context, java.lang.String path, com.amaze.filemanager.ui.fragments.MainFragment mainFragment, com.amaze.filemanager.fileoperations.filesystem.OpenMode openmode, boolean showThumbs, boolean showHiddenFiles, boolean forceReload, com.amaze.filemanager.utils.OnAsyncTaskFinished<androidx.core.util.Pair<com.amaze.filemanager.fileoperations.filesystem.OpenMode, java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable>>> l) {
        this.path = path;
        this.mainFragmentReference = new java.lang.ref.WeakReference<>(mainFragment);
        this.openmode = openmode;
        this.context = new java.lang.ref.WeakReference<>(context);
        this.showThumbs = showThumbs;
        this.showHiddenFiles = showHiddenFiles;
        this.listener = l;
        this.forceReload = forceReload;
    }


    @java.lang.Override
    @java.lang.SuppressWarnings({ "PMD.NPathComplexity", "ComplexMethod", "LongMethod" })
    @androidx.annotation.Nullable
    protected androidx.core.util.Pair<com.amaze.filemanager.fileoperations.filesystem.OpenMode, java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable>> doInBackground(java.lang.Void... p) {
        final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;
        mainFragment = this.mainFragmentReference.get();
        final android.content.Context context;
        context = this.context.get();
        if ((((mainFragment == null) || (context == null)) || (mainFragment.getMainFragmentViewModel() == null)) || (mainFragment.getMainActivityViewModel() == null)) {
            cancel(true);
            return null;
        }
        com.amaze.filemanager.filesystem.HybridFile hFile;
        hFile = null;
        com.amaze.filemanager.ui.fragments.data.MainFragmentViewModel mainFragmentViewModel;
        mainFragmentViewModel = mainFragment.getMainFragmentViewModel();
        com.amaze.filemanager.ui.activities.MainActivityViewModel mainActivityViewModel;
        mainActivityViewModel = mainFragment.getMainActivityViewModel();
        if ((com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN.equals(openmode) || com.amaze.filemanager.fileoperations.filesystem.OpenMode.CUSTOM.equals(openmode)) || com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN.equals(openmode)) {
            hFile = new com.amaze.filemanager.filesystem.HybridFile(openmode, path);
            hFile.generateMode(mainFragment.getActivity());
            openmode = hFile.getMode();
            if (hFile.isSmb()) {
                mainFragmentViewModel.setSmbPath(path);
            }
        }
        if (isCancelled())
            return null;

        mainFragmentViewModel.setFolderCount(0);
        mainFragmentViewModel.setFileCount(0);
        final java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> list;
        switch (openmode) {
            case SMB :
                list = listSmb(hFile, mainActivityViewModel, mainFragment);
                break;
            case SFTP :
                list = listSftp(mainActivityViewModel);
                break;
            case CUSTOM :
            case TRASH_BIN :
                list = getCachedMediaList(mainActivityViewModel);
                break;
            case OTG :
                list = listOtg();
                openmode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.OTG;
                break;
            case DOCUMENT_FILE :
                list = listDocumentFiles(mainActivityViewModel);
                openmode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE;
                break;
            case DROPBOX :
            case BOX :
            case GDRIVE :
            case ONEDRIVE :
                try {
                    list = listCloud(mainActivityViewModel);
                } catch (com.amaze.filemanager.fileoperations.exceptions.CloudPluginException e) {
                    com.amaze.filemanager.asynchronous.asynctasks.LoadFilesListTask.LOG.warn("failed to load cloud files", e);
                    com.amaze.filemanager.application.AppConfig.toast(context, context.getResources().getString(com.amaze.filemanager.R.string.failed_no_connection));
                    return new androidx.core.util.Pair<>(openmode, java.util.Collections.emptyList());
                }
                break;
            case ANDROID_DATA :
                list = listAppDataDirectories(path);
                break;
            default :
                // we're neither in OTG not in SMB, load the list based on root/general filesystem
                list = listDefault(mainActivityViewModel, mainFragment);
                break;
        }
        if ((list != null) && (!((openmode == com.amaze.filemanager.fileoperations.filesystem.OpenMode.CUSTOM) && (("5".equals(path) || "6".equals(path)) || "7".equals(path))))) {
            postListCustomPathProcess(list, mainFragment);
        }
        return new androidx.core.util.Pair<>(openmode, list);
    }


    @java.lang.Override
    protected void onCancelled() {
        listener.onAsyncTaskFinished(null);
    }


    @java.lang.Override
    protected void onProgressUpdate(java.lang.Throwable... values) {
        for (java.lang.Throwable exception : values) {
            if (exception instanceof jcifs.smb.SmbException) {
                if ("/".equals(android.net.Uri.parse(path).getPath())) {
                    switch(MUID_STATIC) {
                        // LoadFilesListTask_0_BuggyGUIListenerOperatorMutator
                        case 16: {
                            new androidx.appcompat.app.AlertDialog.Builder(context.get()).setTitle(com.amaze.filemanager.R.string.error_listfile_smb_title).setMessage(context.get().getString(com.amaze.filemanager.R.string.error_listfile_smb_noipcshare, com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(path))).setPositiveButton(android.R.string.ok, null).show();
                            break;
                        }
                        default: {
                        new androidx.appcompat.app.AlertDialog.Builder(context.get()).setTitle(com.amaze.filemanager.R.string.error_listfile_smb_title).setMessage(context.get().getString(com.amaze.filemanager.R.string.error_listfile_smb_noipcshare, com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(path))).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> {
                            dialog.dismiss();
                        }).show();
                        break;
                    }
                }
            } else {
                android.widget.Toast.makeText(context.get(), context.get().getString(com.amaze.filemanager.R.string.error_listfile_smb, com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(path), exception.getMessage()), android.widget.Toast.LENGTH_LONG).show();
            }
        }
    }
}


@java.lang.Override
protected void onPostExecute(@androidx.annotation.Nullable
androidx.core.util.Pair<com.amaze.filemanager.fileoperations.filesystem.OpenMode, java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable>> list) {
    listener.onAsyncTaskFinished(list);
}


private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> getCachedMediaList(com.amaze.filemanager.ui.activities.MainActivityViewModel mainActivityViewModel) throws java.lang.IllegalStateException {
    java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> list;
    int mediaType;
    mediaType = java.lang.Integer.parseInt(path);
    if (((((5 == mediaType) || (6 == mediaType)) || (7 == mediaType)) || (mainActivityViewModel.getMediaCacheHash().get(mediaType) == null)) || forceReload) {
        switch (java.lang.Integer.parseInt(path)) {
            case 0 :
                list = listImages();
                break;
            case 1 :
                list = listVideos();
                break;
            case 2 :
                list = listaudio();
                break;
            case 3 :
                list = listDocs();
                break;
            case 4 :
                list = listApks();
                break;
            case 5 :
                list = listRecent();
                break;
            case 6 :
                list = listRecentFiles();
                break;
            case 7 :
                list = listTrashBinFiles();
                break;
            default :
                throw new java.lang.IllegalStateException();
        }
        if (((5 != mediaType) && (6 != mediaType)) && (7 != mediaType)) {
            // not saving recent files in cache
            mainActivityViewModel.getMediaCacheHash().set(mediaType, list);
        }
    } else {
        list = mainActivityViewModel.getFromMediaFilesCache(mediaType);
    }
    return list;
}


private void postListCustomPathProcess(@androidx.annotation.NonNull
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> list, @androidx.annotation.NonNull
com.amaze.filemanager.ui.fragments.MainFragment mainFragment) {
    com.amaze.filemanager.filesystem.files.sort.SortType sortType;
    sortType = com.amaze.filemanager.database.SortHandler.getSortType(context.get(), path);
    com.amaze.filemanager.ui.fragments.data.MainFragmentViewModel viewModel;
    viewModel = mainFragment.getMainFragmentViewModel();
    if (viewModel == null) {
        com.amaze.filemanager.asynchronous.asynctasks.LoadFilesListTask.LOG.error("MainFragmentViewModel is null, this is a bug");
        return;
    }
    for (int i = 0; i < list.size(); i++) {
        com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElementParcelable;
        layoutElementParcelable = list.get(i);
        if (layoutElementParcelable == null) {
            // noinspection SuspiciousListRemoveInLoop
            list.remove(i);
            continue;
        }
        if (layoutElementParcelable.isDirectory) {
            viewModel.incrementFolderCount();
        } else {
            viewModel.incrementFileCount();
        }
    }
    java.util.Collections.sort(list, new com.amaze.filemanager.filesystem.files.FileListSorter(viewModel.getDsort(), sortType));
}


@androidx.annotation.Nullable
private com.amaze.filemanager.adapters.data.LayoutElementParcelable createListParcelables(com.amaze.filemanager.filesystem.HybridFileParcelable baseFile) {
    if (dataUtils.isFileHidden(baseFile.getPath())) {
        return null;
    }
    final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;
    mainFragment = this.mainFragmentReference.get();
    final android.content.Context context;
    context = this.context.get();
    if ((mainFragment == null) || (context == null)) {
        cancel(true);
        return null;
    }
    java.lang.String size;
    size = "";
    long longSize;
    longSize = 0;
    if (!baseFile.isDirectory()) {
        if (baseFile.getSize() != (-1)) {
            try {
                longSize = baseFile.getSize();
                size = android.text.format.Formatter.formatFileSize(context, longSize);
            } catch (java.lang.NumberFormatException e) {
                com.amaze.filemanager.asynchronous.asynctasks.LoadFilesListTask.LOG.warn("failed to create list parcelables", e);
            }
        }
    }
    com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElement;
    layoutElement = new com.amaze.filemanager.adapters.data.LayoutElementParcelable(context, baseFile.getName(context), baseFile.getPath(), baseFile.getPermission(), baseFile.getLink(), size, longSize, false, baseFile.getDate() + "", baseFile.isDirectory(), showThumbs, baseFile.getMode());
    return layoutElement;
}


private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listImages() {
    final java.lang.String[] projection;
    projection = new java.lang.String[]{ android.provider.MediaStore.Images.Media.DATA };
    return listMediaCommon(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null);
}


private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listVideos() {
    final java.lang.String[] projection;
    projection = new java.lang.String[]{ android.provider.MediaStore.Video.Media.DATA };
    return listMediaCommon(android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null);
}


private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listaudio() {
    java.lang.String selection;
    selection = android.provider.MediaStore.Audio.Media.IS_MUSIC + " != 0";
    java.lang.String[] projection;
    projection = new java.lang.String[]{ android.provider.MediaStore.Audio.Media.DATA };
    return listMediaCommon(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection);
}


@androidx.annotation.Nullable
private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listMediaCommon(android.net.Uri contentUri, @androidx.annotation.NonNull
java.lang.String[] projection, @androidx.annotation.Nullable
java.lang.String selection) {
    final android.content.Context context;
    context = this.context.get();
    if (context == null) {
        cancel(true);
        return null;
    }
    android.database.Cursor cursor;
    cursor = context.getContentResolver().query(contentUri, projection, selection, null, null);
    java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> retval;
    retval = new java.util.ArrayList<>();
    if (cursor == null)
        return retval;
    else if ((cursor.getCount() > 0) && cursor.moveToFirst()) {
        do {
            java.lang.String path;
            path = cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Files.FileColumns.DATA));
            com.amaze.filemanager.filesystem.HybridFileParcelable strings;
            strings = com.amaze.filemanager.filesystem.RootHelper.generateBaseFile(new java.io.File(path), showHiddenFiles);
            if (strings != null) {
                com.amaze.filemanager.adapters.data.LayoutElementParcelable parcelable;
                parcelable = createListParcelables(strings);
                if (parcelable != null)
                    retval.add(parcelable);

            }
        } while (cursor.moveToNext() );
    }
    cursor.close();
    return retval;
}


@androidx.annotation.Nullable
private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listDocs() {
    final android.content.Context context;
    context = this.context.get();
    if (context == null) {
        cancel(true);
        return null;
    }
    java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> docs;
    docs = new java.util.ArrayList<>();
    final java.lang.String[] projection;
    projection = new java.lang.String[]{ android.provider.MediaStore.Files.FileColumns.DATA };
    android.database.Cursor cursor;
    cursor = context.getContentResolver().query(android.provider.MediaStore.Files.getContentUri("external"), projection, null, null, null);
    if (cursor == null)
        return docs;
    else if ((cursor.getCount() > 0) && cursor.moveToFirst()) {
        do {
            java.lang.String path;
            path = cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Files.FileColumns.DATA));
            if ((path != null) && ((((((((((((((((((((path.endsWith(".pdf") || path.endsWith(".doc")) || path.endsWith(".docx")) || path.endsWith("txt")) || path.endsWith(".rtf")) || path.endsWith(".odt")) || path.endsWith(".html")) || path.endsWith(".xml")) || path.endsWith(".text/x-asm")) || path.endsWith(".def")) || path.endsWith(".in")) || path.endsWith(".rc")) || path.endsWith(".list")) || path.endsWith(".log")) || path.endsWith(".pl")) || path.endsWith(".prop")) || path.endsWith(".properties")) || path.endsWith(".msg")) || path.endsWith(".pages")) || path.endsWith(".wpd")) || path.endsWith(".wps"))) {
                com.amaze.filemanager.filesystem.HybridFileParcelable strings;
                strings = com.amaze.filemanager.filesystem.RootHelper.generateBaseFile(new java.io.File(path), showHiddenFiles);
                if (strings != null) {
                    com.amaze.filemanager.adapters.data.LayoutElementParcelable parcelable;
                    parcelable = createListParcelables(strings);
                    if (parcelable != null)
                        docs.add(parcelable);

                }
            }
        } while (cursor.moveToNext() );
    }
    cursor.close();
    switch(MUID_STATIC) {
        // LoadFilesListTask_1_BinaryMutator
        case 1016: {
            java.util.Collections.sort(docs, (com.amaze.filemanager.adapters.data.LayoutElementParcelable lhs,com.amaze.filemanager.adapters.data.LayoutElementParcelable rhs) -> (-1) / java.lang.Long.valueOf(lhs.date).compareTo(rhs.date));
            break;
        }
        default: {
        java.util.Collections.sort(docs, (com.amaze.filemanager.adapters.data.LayoutElementParcelable lhs,com.amaze.filemanager.adapters.data.LayoutElementParcelable rhs) -> (-1) * java.lang.Long.valueOf(lhs.date).compareTo(rhs.date));
        break;
    }
}
return docs;
}


@androidx.annotation.Nullable
private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listApks() {
final android.content.Context context;
context = this.context.get();
if (context == null) {
    cancel(true);
    return null;
}
java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> apks;
apks = new java.util.ArrayList<>();
final java.lang.String[] projection;
projection = new java.lang.String[]{ android.provider.MediaStore.Files.FileColumns.DATA };
android.database.Cursor cursor;
cursor = context.getContentResolver().query(android.provider.MediaStore.Files.getContentUri("external"), projection, null, null, null);
if (cursor == null)
    return apks;
else if ((cursor.getCount() > 0) && cursor.moveToFirst()) {
    do {
        java.lang.String path;
        path = cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Files.FileColumns.DATA));
        if ((path != null) && path.endsWith(".apk")) {
            com.amaze.filemanager.filesystem.HybridFileParcelable strings;
            strings = com.amaze.filemanager.filesystem.RootHelper.generateBaseFile(new java.io.File(path), showHiddenFiles);
            if (strings != null) {
                com.amaze.filemanager.adapters.data.LayoutElementParcelable parcelable;
                parcelable = createListParcelables(strings);
                if (parcelable != null)
                    apks.add(parcelable);

            }
        }
    } while (cursor.moveToNext() );
}
cursor.close();
return apks;
}


@androidx.annotation.Nullable
private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listRecent() {
final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;
mainFragment = this.mainFragmentReference.get();
if (mainFragment == null) {
    cancel(true);
    return null;
}
com.amaze.filemanager.database.UtilsHandler utilsHandler;
utilsHandler = com.amaze.filemanager.application.AppConfig.getInstance().getUtilsHandler();
final java.util.LinkedList<java.lang.String> paths;
paths = utilsHandler.getHistoryLinkedList();
java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> songs;
songs = new java.util.ArrayList<>();
for (java.lang.String f : paths) {
    if (!f.equals("/")) {
        com.amaze.filemanager.filesystem.HybridFileParcelable hybridFileParcelable;
        hybridFileParcelable = com.amaze.filemanager.filesystem.RootHelper.generateBaseFile(new java.io.File(f), showHiddenFiles);
        if (hybridFileParcelable != null) {
            hybridFileParcelable.generateMode(mainFragment.getActivity());
            if ((hybridFileParcelable.isSimpleFile() && (!hybridFileParcelable.isDirectory())) && hybridFileParcelable.exists()) {
                com.amaze.filemanager.adapters.data.LayoutElementParcelable parcelable;
                parcelable = createListParcelables(hybridFileParcelable);
                if (parcelable != null)
                    songs.add(parcelable);

            }
        }
    }
}
return songs;
}


@androidx.annotation.Nullable
private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listRecentFiles() {
final android.content.Context context;
context = this.context.get();
if (context == null) {
    cancel(true);
    return null;
}
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> recentFiles;
recentFiles = new java.util.ArrayList<>(20);
final java.lang.String[] projection;
projection = new java.lang.String[]{ android.provider.MediaStore.Files.FileColumns.DATA, android.provider.MediaStore.Files.FileColumns.DATE_MODIFIED };
java.util.Calendar c;
c = java.util.Calendar.getInstance();
switch(MUID_STATIC) {
    // LoadFilesListTask_2_BinaryMutator
    case 2016: {
        c.set(java.util.Calendar.DAY_OF_YEAR, c.get(java.util.Calendar.DAY_OF_YEAR) + 2);
        break;
    }
    default: {
    c.set(java.util.Calendar.DAY_OF_YEAR, c.get(java.util.Calendar.DAY_OF_YEAR) - 2);
    break;
}
}
java.util.Date d;
d = c.getTime();
android.database.Cursor cursor;
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
android.os.Bundle queryArgs;
queryArgs = new android.os.Bundle();
queryArgs.putInt(android.content.ContentResolver.QUERY_ARG_LIMIT, 20);
queryArgs.putStringArray(android.content.ContentResolver.QUERY_ARG_SORT_COLUMNS, new java.lang.String[]{ android.provider.MediaStore.Files.FileColumns.DATE_MODIFIED });
queryArgs.putInt(android.content.ContentResolver.QUERY_ARG_SORT_DIRECTION, android.content.ContentResolver.QUERY_SORT_DIRECTION_DESCENDING);
cursor = context.getContentResolver().query(android.provider.MediaStore.Files.getContentUri("external"), projection, queryArgs, null);
} else {
cursor = context.getContentResolver().query(android.provider.MediaStore.Files.getContentUri("external"), projection, null, null, android.provider.MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC LIMIT 20");
}
if (cursor == null)
return recentFiles;

if ((cursor.getCount() > 0) && cursor.moveToFirst()) {
do {
    java.lang.String path;
    path = cursor.getString(cursor.getColumnIndex(android.provider.MediaStore.Files.FileColumns.DATA));
    java.io.File f;
    f = new java.io.File(path);
    if ((d.compareTo(new java.util.Date(f.lastModified())) != 1) && (!f.isDirectory())) {
        com.amaze.filemanager.filesystem.HybridFileParcelable strings;
        strings = com.amaze.filemanager.filesystem.RootHelper.generateBaseFile(new java.io.File(path), showHiddenFiles);
        if (strings != null) {
            com.amaze.filemanager.adapters.data.LayoutElementParcelable parcelable;
            parcelable = createListParcelables(strings);
            if (parcelable != null)
                recentFiles.add(parcelable);

        }
    }
} while (cursor.moveToNext() );
}
cursor.close();
return recentFiles;
}


@androidx.annotation.Nullable
private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listTrashBinFiles() {
final android.content.Context context;
context = this.context.get();
if (context == null) {
cancel(true);
return null;
}
com.amaze.trashbin.TrashBin trashBin;
trashBin = com.amaze.filemanager.application.AppConfig.getInstance().getTrashBinInstance();
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> deletedFiles;
deletedFiles = new java.util.ArrayList<>();
if (trashBin != null) {
for (com.amaze.trashbin.TrashBinFile trashBinFile : trashBin.listFilesInBin()) {
    com.amaze.filemanager.filesystem.HybridFile hybridFile;
    hybridFile = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN, trashBinFile.getDeletedPath(com.amaze.filemanager.application.AppConfig.getInstance().getTrashBinInstance().getConfig()), trashBinFile.getFileName(), trashBinFile.isDirectory());
    if (trashBinFile.getDeleteTime() != null) {
        switch(MUID_STATIC) {
            // LoadFilesListTask_3_BinaryMutator
            case 3016: {
                hybridFile.setLastModified(trashBinFile.getDeleteTime() / 1000);
                break;
            }
            default: {
            hybridFile.setLastModified(trashBinFile.getDeleteTime() * 1000);
            break;
        }
    }
}
com.amaze.filemanager.adapters.data.LayoutElementParcelable element;
element = hybridFile.generateLayoutElement(context, true);
element.date = trashBinFile.getDeleteTime();
element.longSize = trashBinFile.getSizeBytes();
element.size = android.text.format.Formatter.formatFileSize(context, trashBinFile.getSizeBytes());
switch(MUID_STATIC) {
    // LoadFilesListTask_4_BinaryMutator
    case 4016: {
        element.dateModification = com.amaze.filemanager.utils.Utils.getDate(context, trashBinFile.getDeleteTime() / 1000);
        break;
    }
    default: {
    element.dateModification = com.amaze.filemanager.utils.Utils.getDate(context, trashBinFile.getDeleteTime() * 1000);
    break;
}
}
element.isDirectory = trashBinFile.isDirectory();
deletedFiles.add(element);
}
}
return deletedFiles;
}


@androidx.annotation.NonNull
private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listAppDataDirectories(@androidx.annotation.NonNull
java.lang.String basePath) {
if (!com.amaze.filemanager.utils.GenericExtKt.containsPath(com.amaze.filemanager.filesystem.FileProperties.ANDROID_DEVICE_DATA_DIRS, basePath)) {
throw new java.lang.IllegalArgumentException(("Invalid base path: [" + basePath) + "]");
}
android.content.Context ctx;
ctx = context.get();
@androidx.annotation.Nullable
android.content.pm.PackageManager pm;
pm = (ctx != null) ? ctx.getPackageManager() : null;
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> retval;
retval = new java.util.ArrayList<>();
if (pm != null) {
android.content.Intent intent;
switch(MUID_STATIC) {
// LoadFilesListTask_5_RandomActionIntentDefinitionOperatorMutator
case 5016: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.content.Intent.ACTION_MAIN).addCategory(android.content.Intent.CATEGORY_LAUNCHER);
break;
}
}
for (android.content.pm.ResolveInfo app : kotlin.collections.CollectionsKt.distinctBy(pm.queryIntentActivities(intent, 0), (android.content.pm.ResolveInfo resolveInfo) -> resolveInfo.activityInfo.packageName)) {
java.io.File dir;
dir = new java.io.File(new java.io.File(basePath), app.activityInfo.packageName);
if (dir.exists()) {
com.amaze.filemanager.adapters.data.LayoutElementParcelable element;
element = new com.amaze.filemanager.adapters.data.LayoutElementParcelable(ctx, dir.getAbsolutePath(), "", "", java.lang.Long.toString(dir.length()), dir.length(), false, java.lang.Long.toString(dir.lastModified()), true, false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.ANDROID_DATA);
retval.add(element);
}
}
}
return retval;
}


private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listSmb(@androidx.annotation.Nullable
final com.amaze.filemanager.filesystem.HybridFile hFile, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivityViewModel mainActivityViewModel, @androidx.annotation.NonNull
com.amaze.filemanager.ui.fragments.MainFragment mainFragment) {
com.amaze.filemanager.filesystem.HybridFile _file;
_file = hFile;
if (_file == null) {
_file = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB, path);
}
if (!_file.getPath().endsWith("/")) {
_file.setPath(_file.getPath() + "/");
}
@androidx.annotation.NonNull
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> list;
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> smbCache;
smbCache = mainActivityViewModel.getFromListCache(path);
openmode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB;
if ((smbCache != null) && (!forceReload)) {
list = smbCache;
} else {
try {
jcifs.smb.SmbFile[] smbFile;
smbFile = _file.getSmbFile(5000).listFiles();
list = mainFragment.addToSmb(smbFile, path, showHiddenFiles);
} catch (jcifs.smb.SmbAuthException e) {
if (!e.getMessage().toLowerCase().contains("denied")) {
mainFragment.reauthenticateSmb();
}
com.amaze.filemanager.asynchronous.asynctasks.LoadFilesListTask.LOG.warn("failed to load smb list, authentication issue: ", e);
publishProgress(e);
return null;
} catch (jcifs.smb.SmbException | java.lang.NullPointerException e) {
com.amaze.filemanager.asynchronous.asynctasks.LoadFilesListTask.LOG.warn("Failed to load smb files for path: " + path, e);
mainFragment.reauthenticateSmb();
return null;
}
mainActivityViewModel.putInCache(path, list);
}
return list;
}


private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listSftp(@androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivityViewModel mainActivityViewModel) {
com.amaze.filemanager.filesystem.HybridFile ftpHFile;
ftpHFile = new com.amaze.filemanager.filesystem.HybridFile(openmode, path);
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> list;
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> sftpCache;
sftpCache = mainActivityViewModel.getFromListCache(path);
if ((sftpCache != null) && (!forceReload)) {
list = sftpCache;
} else {
list = new java.util.ArrayList<>();
ftpHFile.forEachChildrenFile(context.get(), false, (com.amaze.filemanager.filesystem.HybridFileParcelable file) -> {
if (!(dataUtils.isFileHidden(file.getPath()) || (file.isHidden() && (!showHiddenFiles)))) {
com.amaze.filemanager.adapters.data.LayoutElementParcelable elem;
elem = createListParcelables(file);
if (elem != null) {
list.add(elem);
}
}
});
mainActivityViewModel.putInCache(path, list);
}
return list;
}


private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listOtg() {
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> list;
list = new java.util.ArrayList<>();
listOtgInternal(path, (com.amaze.filemanager.filesystem.HybridFileParcelable file) -> {
com.amaze.filemanager.adapters.data.LayoutElementParcelable elem;
elem = createListParcelables(file);
if (elem != null)
list.add(elem);

});
return list;
}


private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listDocumentFiles(@androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivityViewModel mainActivityViewModel) {
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> list;
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> cache;
cache = mainActivityViewModel.getFromListCache(path);
if ((cache != null) && (!forceReload)) {
list = cache;
} else {
list = new java.util.ArrayList<>();
listDocumentFilesInternal((com.amaze.filemanager.filesystem.HybridFileParcelable file) -> {
com.amaze.filemanager.adapters.data.LayoutElementParcelable elem;
elem = createListParcelables(file);
if (elem != null)
list.add(elem);

});
mainActivityViewModel.putInCache(path, list);
}
return list;
}


private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listCloud(@androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivityViewModel mainActivityViewModel) throws com.amaze.filemanager.fileoperations.exceptions.CloudPluginException {
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> list;
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> cloudCache;
cloudCache = mainActivityViewModel.getFromListCache(path);
if ((cloudCache != null) && (!forceReload)) {
list = cloudCache;
} else {
com.cloudrail.si.interfaces.CloudStorage cloudStorage;
cloudStorage = dataUtils.getAccount(openmode);
list = new java.util.ArrayList<>();
listCloudInternal(path, cloudStorage, openmode, (com.amaze.filemanager.filesystem.HybridFileParcelable file) -> {
com.amaze.filemanager.adapters.data.LayoutElementParcelable elem;
elem = createListParcelables(file);
if (elem != null)
list.add(elem);

});
mainActivityViewModel.putInCache(path, list);
}
return list;
}


private java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listDefault(@androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivityViewModel mainActivityViewModel, @androidx.annotation.NonNull
com.amaze.filemanager.ui.fragments.MainFragment mainFragment) {
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> list;
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> localCache;
localCache = mainActivityViewModel.getFromListCache(path);
openmode = com.amaze.filemanager.filesystem.root.ListFilesCommand.INSTANCE.getOpenMode(path, mainFragment.requireMainActivity().isRootExplorer());
if ((localCache != null) && (!forceReload)) {
list = localCache;
} else {
list = new java.util.ArrayList<>();
final com.amaze.filemanager.fileoperations.filesystem.OpenMode[] currentOpenMode;
currentOpenMode = new com.amaze.filemanager.fileoperations.filesystem.OpenMode[1];
com.amaze.filemanager.filesystem.root.ListFilesCommand.INSTANCE.listFiles(path, mainFragment.requireMainActivity().isRootExplorer(), showHiddenFiles, (com.amaze.filemanager.fileoperations.filesystem.OpenMode mode) -> {
currentOpenMode[0] = mode;
return null;
}, (com.amaze.filemanager.filesystem.HybridFileParcelable hybridFileParcelable) -> {
com.amaze.filemanager.adapters.data.LayoutElementParcelable elem;
elem = createListParcelables(hybridFileParcelable);
if (elem != null)
list.add(elem);

return null;
});
if (list.size() > com.amaze.filemanager.ui.activities.MainActivityViewModel.Companion.getCACHE_LOCAL_LIST_THRESHOLD()) {
mainActivityViewModel.putInCache(path, list);
}
if (null != currentOpenMode[0]) {
openmode = currentOpenMode[0];
}
}
return list;
}


/**
 * Lists files from an OTG device
 *
 * @param path
 * 		the path to the directory tree, starts with prefix {@link com.amaze.filemanager.utils.OTGUtil#PREFIX_OTG} Independent of URI (or mount point) for the
 * 		OTG
 */
private void listOtgInternal(java.lang.String path, com.amaze.filemanager.utils.OnFileFound fileFound) {
final android.content.Context context;
context = this.context.get();
if (context == null) {
cancel(true);
return;
}
com.amaze.filemanager.utils.OTGUtil.getDocumentFiles(path, context, fileFound);
}


private void listDocumentFilesInternal(com.amaze.filemanager.utils.OnFileFound fileFound) {
final android.content.Context context;
context = this.context.get();
if (context == null) {
cancel(true);
return;
}
com.amaze.filemanager.utils.OTGUtil.getDocumentFiles(com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), path, context, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, fileFound);
}


private void listCloudInternal(java.lang.String path, com.cloudrail.si.interfaces.CloudStorage cloudStorage, com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, com.amaze.filemanager.utils.OnFileFound fileFoundCallback) throws com.amaze.filemanager.fileoperations.exceptions.CloudPluginException {
final android.content.Context context;
context = this.context.get();
if (context == null) {
cancel(true);
return;
}
if (!com.amaze.filemanager.ui.fragments.CloudSheetFragment.isCloudProviderAvailable(context)) {
throw new com.amaze.filemanager.fileoperations.exceptions.CloudPluginException();
}
com.amaze.filemanager.filesystem.cloud.CloudUtil.getCloudFiles(path, cloudStorage, openMode, fileFoundCallback);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
