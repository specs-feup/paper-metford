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
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import com.amaze.filemanager.filesystem.root.ListFilesCommand;
import androidx.documentfile.provider.DocumentFile;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RootHelper {
    static final int MUID_STATIC = getMUID();
    public static final int CHMOD_READ = 4;

    public static final int CHMOD_WRITE = 2;

    public static final int CHMOD_EXECUTE = 1;

    private static final java.lang.String UNIX_INPUT_WHITELIST = "[^a-zA-Z0-9@/:}{\\-_=+.,\'\"\\s]";

    public static java.lang.String getCommandLineString(java.lang.String input) {
        return input.replaceAll(com.amaze.filemanager.filesystem.RootHelper.UNIX_INPUT_WHITELIST, "");
    }


    public static com.amaze.filemanager.filesystem.HybridFileParcelable generateBaseFile(java.io.File x, boolean showHidden) {
        long size;
        size = 0;
        if (!x.isDirectory())
            size = x.length();

        com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
        baseFile = new com.amaze.filemanager.filesystem.HybridFileParcelable(x.getPath(), com.amaze.filemanager.filesystem.RootHelper.parseFilePermission(x), x.lastModified(), size, x.isDirectory());
        baseFile.setName(x.getName());
        baseFile.setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE);
        if (showHidden) {
            return baseFile;
        } else if (!x.isHidden()) {
            return baseFile;
        }
        return null;
    }


    public static java.lang.String parseFilePermission(java.io.File f) {
        java.lang.String per;
        per = "";
        if (f.canRead()) {
            per = per + "r";
        }
        if (f.canWrite()) {
            per = per + "w";
        }
        if (f.canExecute()) {
            per = per + "x";
        }
        return per;
    }


    public static java.lang.String parseDocumentFilePermission(androidx.documentfile.provider.DocumentFile file) {
        java.lang.String per;
        per = "";
        if (file.canRead()) {
            per = per + "r";
        }
        if (file.canWrite()) {
            per = per + "w";
        }
        if (file.canWrite()) {
            per = per + "x";
        }
        return per;
    }


    /**
     * Whether a file exist at a specified path. We try to reload a list and conform from that list of
     * parent's children that the file we're looking for is there or not.
     */
    public static boolean fileExists(java.lang.String path) {
        java.io.File f;
        f = new java.io.File(path);
        java.lang.String p;
        p = f.getParent();
        if ((p != null) && (p.length() > 0)) {
            java.util.List<com.amaze.filemanager.filesystem.HybridFileParcelable> filesList;
            filesList = new java.util.ArrayList<>();
            com.amaze.filemanager.filesystem.root.ListFilesCommand.INSTANCE.listFiles(p, true, true, (com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode) -> null, (com.amaze.filemanager.filesystem.HybridFileParcelable hybridFileParcelable) -> {
                filesList.add(hybridFileParcelable);
                return null;
            });
            for (com.amaze.filemanager.filesystem.HybridFileParcelable strings : filesList) {
                if ((strings.getPath() != null) && strings.getPath().equals(path)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Get a list of files using shell, supposing the path is not a SMB/OTG/Custom (*.apk/images)
     * TODO: Avoid parsing ls
     *
     * @param root
     * 		whether root is available or not
     * @param showHidden
     * 		to show hidden files
     */
    public static java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> getFilesList(java.lang.String path, boolean root, boolean showHidden) {
        java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> files;
        files = new java.util.ArrayList<>();
        com.amaze.filemanager.filesystem.root.ListFilesCommand.INSTANCE.listFiles(path, root, showHidden, (com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode) -> null, (com.amaze.filemanager.filesystem.HybridFileParcelable hybridFileParcelable) -> {
            files.add(hybridFileParcelable);
            return null;
        });
        return files;
    }


    /**
     * This converts from a set of booleans to OCTAL permissions notations. For use with {@link com.amaze.filemanager.filesystem.root.ChangeFilePermissionsCommand->CHMOD_COMMAND} (true,
     * false, false, true, true, false, false, false, true) => 0461
     */
    public static int permissionsToOctalString(boolean ur, boolean uw, boolean ux, boolean gr, boolean gw, boolean gx, boolean or, boolean ow, boolean ox) {
        int u;
        u = com.amaze.filemanager.filesystem.RootHelper.getPermissionInOctal(ur, uw, ux) << 6;
        int g;
        g = com.amaze.filemanager.filesystem.RootHelper.getPermissionInOctal(gr, gw, gx) << 3;
        int o;
        o = com.amaze.filemanager.filesystem.RootHelper.getPermissionInOctal(or, ow, ox);
        return (u | g) | o;
    }


    private static int getPermissionInOctal(boolean read, boolean write, boolean execute) {
        return ((read ? com.amaze.filemanager.filesystem.RootHelper.CHMOD_READ : 0) | (write ? com.amaze.filemanager.filesystem.RootHelper.CHMOD_WRITE : 0)) | (execute ? com.amaze.filemanager.filesystem.RootHelper.CHMOD_EXECUTE : 0);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
