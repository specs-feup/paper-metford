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
package com.amaze.filemanager.filesystem;
import io.reactivex.android.schedulers.AndroidSchedulers;
import androidx.core.text.HtmlCompat;
import io.reactivex.disposables.Disposable;
import io.reactivex.SingleObserver;
import android.os.Parcel;
import java.util.ArrayList;
import io.reactivex.schedulers.Schedulers;
import com.amaze.filemanager.ui.activities.MainActivity;
import android.text.Spanned;
import org.slf4j.Logger;
import android.os.Parcelable;
import com.amaze.filemanager.ui.fragments.MainFragment;
import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT;
import com.amaze.filemanager.R;
import io.reactivex.Single;
import com.amaze.filemanager.utils.Utils;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.amaze.filemanager.asynchronous.asynctasks.movecopy.PreparePasteTask;
import com.google.android.material.snackbar.Snackbar;
import java.util.Arrays;
import org.slf4j.LoggerFactory;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Special immutable class for handling cut/copy operations.
 *
 * @author Emmanuel on 5/9/2017, at 09:59.
 */
public final class PasteHelper implements android.os.Parcelable {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.PasteHelper.class);

    public static final int OPERATION_COPY = 0;

    public static final int OPERATION_CUT = 1;

    private final int operation;

    private final com.amaze.filemanager.filesystem.HybridFileParcelable[] paths;

    private com.google.android.material.snackbar.Snackbar snackbar;

    @androidx.annotation.Nullable
    private com.amaze.filemanager.ui.activities.MainActivity mainActivity;

    public PasteHelper(@androidx.annotation.Nullable
    com.amaze.filemanager.ui.activities.MainActivity mainActivity, int op, com.amaze.filemanager.filesystem.HybridFileParcelable[] paths) {
        if ((paths == null) || (paths.length == 0))
            throw new java.lang.IllegalArgumentException();

        operation = op;
        this.paths = paths;
        this.mainActivity = mainActivity;
        showSnackbar();
    }


    private PasteHelper(android.os.Parcel in) {
        operation = in.readInt();
        paths = in.createTypedArray(com.amaze.filemanager.filesystem.HybridFileParcelable.CREATOR);
    }


    @java.lang.Override
    public int describeContents() {
        return 0;
    }


    @java.lang.Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(operation);
        dest.writeTypedArray(paths, 0);
    }


    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {
        public com.amaze.filemanager.filesystem.PasteHelper createFromParcel(android.os.Parcel in) {
            return new com.amaze.filemanager.filesystem.PasteHelper(in);
        }


        public com.amaze.filemanager.filesystem.PasteHelper[] newArray(int size) {
            return new com.amaze.filemanager.filesystem.PasteHelper[size];
        }

    };

    public int getOperation() {
        return this.operation;
    }


    public com.amaze.filemanager.filesystem.HybridFileParcelable[] getPaths() {
        return paths;
    }


    /**
     * Invalidates the snackbar after fragment changes / reapply config changes. Keeping the contents
     * to copy/move intact
     *
     * @param mainActivity
     * 		main activity
     * @param showSnackbar
     * 		whether to show snackbar or hide
     */
    public void invalidateSnackbar(com.amaze.filemanager.ui.activities.MainActivity mainActivity, boolean showSnackbar) {
        this.mainActivity = mainActivity;
        if (showSnackbar) {
            showSnackbar();
        } else {
            dismissSnackbar(false);
        }
    }


    public com.google.android.material.snackbar.Snackbar getSnackbar() {
        return snackbar;
    }


    /**
     * Dismisses snackbar and fab
     *
     * @param shouldClearPasteData
     * 		should the paste data be cleared
     */
    private void dismissSnackbar(boolean shouldClearPasteData) {
        if (snackbar != null) {
            snackbar.dismiss();
            snackbar = null;
        }
        if (shouldClearPasteData) {
            mainActivity.setPaste(null);
        }
    }


    private void showSnackbar() {
        io.reactivex.Single.fromCallable(() -> getSnackbarContent()).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(new io.reactivex.SingleObserver<android.text.Spanned>() {
            @java.lang.Override
            public void onSubscribe(io.reactivex.disposables.Disposable d) {
            }


            @java.lang.Override
            public void onSuccess(android.text.Spanned spanned) {
                snackbar = com.amaze.filemanager.utils.Utils.showCutCopySnackBar(mainActivity, spanned, com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE, com.amaze.filemanager.R.string.paste, () -> {
                    final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;
                    mainFragment = mainActivity.getCurrentMainFragment();
                    if (mainFragment == null)
                        return;

                    java.lang.String path;
                    path = mainFragment.getCurrentPath();
                    java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> arrayList;
                    arrayList = new java.util.ArrayList<>(java.util.Arrays.asList(paths));
                    boolean move;
                    move = operation == com.amaze.filemanager.filesystem.PasteHelper.OPERATION_CUT;
                    new com.amaze.filemanager.asynchronous.asynctasks.movecopy.PreparePasteTask(mainActivity).execute(path, move, mainActivity.isRootExplorer(), mainFragment.getMainFragmentViewModel().getOpenMode(), arrayList);
                    dismissSnackbar(true);
                }, () -> dismissSnackbar(true));
            }


            @java.lang.Override
            public void onError(java.lang.Throwable e) {
                com.amaze.filemanager.filesystem.PasteHelper.LOG.warn("Failed to show paste snackbar" + e);
            }

        });
    }


    private android.text.Spanned getSnackbarContent() {
        java.lang.String operationText;
        operationText = "<b>%s</b>";
        operationText = java.lang.String.format(operationText, operation == com.amaze.filemanager.filesystem.PasteHelper.OPERATION_COPY ? mainActivity.getString(com.amaze.filemanager.R.string.copy) : mainActivity.getString(com.amaze.filemanager.R.string.move));
        operationText = operationText.concat(": ");
        int foldersCount;
        foldersCount = 0;
        int filesCount;
        filesCount = 0;
        for (com.amaze.filemanager.filesystem.HybridFileParcelable fileParcelable : paths) {
            if (fileParcelable.isDirectory(mainActivity.getApplicationContext())) {
                foldersCount++;
            } else {
                filesCount++;
            }
        }
        operationText = operationText.concat(mainActivity.getString(com.amaze.filemanager.R.string.folderfilecount, foldersCount, filesCount));
        return androidx.core.text.HtmlCompat.fromHtml(operationText, androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
