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
package com.amaze.filemanager.filesystem.files;
import java.util.Locale;
import com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import java.util.ArrayList;
import com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity;
import android.app.Activity;
import androidx.annotation.NonNull;
import com.amaze.filemanager.filesystem.RootHelper;
import android.os.Build;
import java.util.List;
import com.amaze.filemanager.filesystem.Operations;
import com.amaze.filemanager.ui.theme.AppTheme;
import com.amaze.filemanager.utils.OnProgressUpdate;
import java.util.Collections;
import java.util.LinkedList;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.os.AsyncTask;
import androidx.documentfile.provider.DocumentFile;
import com.cloudrail.si.types.CloudMetaData;
import kotlin.collections.ArraysKt;
import com.amaze.filemanager.adapters.data.LayoutElementParcelable;
import com.amaze.filemanager.ui.icons.MimeTypes;
import com.amaze.filemanager.filesystem.HybridFile;
import com.amaze.filemanager.filesystem.cloud.CloudUtil;
import com.amaze.filemanager.utils.DataUtils;
import android.content.ActivityNotFoundException;
import com.amaze.filemanager.filesystem.compressed.CompressedHelper;
import com.amaze.filemanager.utils.PackageInstallValidation;
import java.io.File;
import com.afollestad.materialdialogs.MaterialDialog;
import androidx.annotation.Nullable;
import com.amaze.filemanager.ui.dialogs.share.ShareTask;
import com.amaze.filemanager.ui.activities.MainActivity;
import android.net.Uri;
import org.slf4j.Logger;
import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import net.schmizz.sshj.sftp.SFTPException;
import com.amaze.filemanager.utils.OTGUtil;
import com.cloudrail.si.interfaces.CloudStorage;
import android.content.pm.ResolveInfo;
import com.amaze.filemanager.R;
import com.amaze.filemanager.fileoperations.filesystem.smbstreamer.Streamer;
import com.amaze.filemanager.ui.activities.DatabaseViewerActivity;
import android.animation.AnimatorListenerAdapter;
import static com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.CONTENT;
import android.content.pm.PackageManager;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import java.text.ParsePosition;
import com.amaze.filemanager.ui.activities.superclasses.PreferenceActivity;
import androidx.core.util.Pair;
import android.content.SharedPreferences;
import com.amaze.filemanager.ui.dialogs.OpenFileDialogFragment;
import com.amaze.filemanager.application.AppConfig;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import android.content.Intent;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import android.view.View;
import jcifs.smb.SmbFile;
import java.util.Date;
import java.text.SimpleDateFormat;
import android.Manifest;
import androidx.core.content.FileProvider;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import android.animation.Animator;
import java.util.concurrent.atomic.AtomicLong;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Functions that deal with files
 */
public class FileUtils {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.files.FileUtils.class);

    private static final java.lang.String[] COMPRESSED_FILE_EXTENSIONS = new java.lang.String[]{ "zip", "rar", "cab", "bz2", "ace", "bz", "gz", "7z", "jar", "apk", "xz", "lzma", "Z" };

    public static final java.lang.String FILE_PROVIDER_PREFIX = "storage_root";

    public static final java.lang.String NOMEDIA_FILE = ".nomedia";

    public static final java.lang.String DUMMY_FILE = ".DummyFile";

    public static long folderSize(java.io.File directory, com.amaze.filemanager.utils.OnProgressUpdate<java.lang.Long> updateState) {
        long length;
        length = 0;
        try {
            for (java.io.File file : directory.listFiles()) {
                if (file.isFile())
                    length += file.length();
                // null because updateState would be called for children dirs
                else
                    length += com.amaze.filemanager.filesystem.files.FileUtils.folderSize(file, null);
                // null because updateState would be called for children dirs
                // null because updateState would be called for children dirs

                if (updateState != null)
                    updateState.onUpdate(length);

            }
        } catch (java.lang.Exception e) {
            com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to get folder size", e);
        }
        return length;
    }


    public static long folderSize(com.amaze.filemanager.filesystem.HybridFile directory, com.amaze.filemanager.utils.OnProgressUpdate<java.lang.Long> updateState) {
        if (directory.isSimpleFile())
            return com.amaze.filemanager.filesystem.files.FileUtils.folderSize(new java.io.File(directory.getPath()), updateState);
        else
            return directory.folderSize(com.amaze.filemanager.application.AppConfig.getInstance());

    }


    public static long folderSize(jcifs.smb.SmbFile directory) {
        long length;
        length = 0;
        try {
            for (jcifs.smb.SmbFile file : directory.listFiles()) {
                if (file.isFile())
                    length += file.length();
                else
                    length += com.amaze.filemanager.filesystem.files.FileUtils.folderSize(file);

            }
        } catch (java.lang.Exception e) {
            com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to get folder size", e);
        }
        return length;
    }


    /**
     * Use recursive <code>ls</code> to get folder size.
     *
     * <p>It is slow, it is stupid, and may be inaccurate (because of permission problems). Only for
     * fallback use when <code>du</code> is not available.
     *
     * @see HybridFile#folderSize(Context)
     * @return Folder size in bytes
     */
    public static java.lang.Long folderSizeSftp(net.schmizz.sshj.sftp.SFTPClient client, java.lang.String remotePath) {
        java.lang.Long retval;
        retval = 0L;
        try {
            for (net.schmizz.sshj.sftp.RemoteResourceInfo info : client.ls(remotePath)) {
                if (info.isDirectory())
                    retval += com.amaze.filemanager.filesystem.files.FileUtils.folderSizeSftp(client, info.getPath());
                else
                    retval += info.getAttributes().getSize();

            }
        } catch (net.schmizz.sshj.sftp.SFTPException e) {
            // Usually happens when permission denied listing files in directory
            com.amaze.filemanager.filesystem.files.FileUtils.LOG.error("folderSizeSftp", "Problem accessing " + remotePath, e);
        } finally {
            return retval;
        }
    }


    public static long folderSizeCloud(com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, com.cloudrail.si.types.CloudMetaData sourceFileMeta) {
        com.amaze.filemanager.utils.DataUtils dataUtils;
        dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();
        long length;
        length = 0;
        com.cloudrail.si.interfaces.CloudStorage cloudStorage;
        cloudStorage = dataUtils.getAccount(openMode);
        for (com.cloudrail.si.types.CloudMetaData metaData : cloudStorage.getChildren(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(openMode, sourceFileMeta.getPath()))) {
            if (metaData.getFolder()) {
                length += com.amaze.filemanager.filesystem.files.FileUtils.folderSizeCloud(openMode, metaData);
            } else {
                length += metaData.getSize();
            }
        }
        return length;
    }


    /**
     * Helper method to get size of an otg folder
     */
    public static long otgFolderSize(java.lang.String path, final android.content.Context context) {
        final java.util.concurrent.atomic.AtomicLong totalBytes;
        totalBytes = new java.util.concurrent.atomic.AtomicLong(0);
        com.amaze.filemanager.utils.OTGUtil.getDocumentFiles(path, context, (com.amaze.filemanager.filesystem.HybridFileParcelable file) -> totalBytes.addAndGet(com.amaze.filemanager.filesystem.files.FileUtils.getBaseFileSize(file, context)));
        return totalBytes.longValue();
    }


    /**
     * Helper method to calculate source files size
     */
    public static long getTotalBytes(java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> files, android.content.Context context) {
        long totalBytes;
        totalBytes = 0L;
        for (com.amaze.filemanager.filesystem.HybridFileParcelable file : files) {
            totalBytes += com.amaze.filemanager.filesystem.files.FileUtils.getBaseFileSize(file, context);
        }
        return totalBytes;
    }


    public static long getBaseFileSize(com.amaze.filemanager.filesystem.HybridFileParcelable baseFile, android.content.Context context) {
        if (baseFile.isDirectory(context)) {
            return baseFile.folderSize(context);
        } else {
            return baseFile.length(context);
        }
    }


    public static void crossfade(android.view.View buttons, final android.view.View pathbar) {
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        buttons.setAlpha(0.0F);
        buttons.setVisibility(android.view.View.VISIBLE);
        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        buttons.animate().alpha(1.0F).setDuration(100).setListener(null);
        pathbar.animate().alpha(0.0F).setDuration(100).setListener(new android.animation.AnimatorListenerAdapter() {
            @java.lang.Override
            public void onAnimationEnd(android.animation.Animator animation) {
                pathbar.setVisibility(android.view.View.GONE);
            }

        });
        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
    }


    public static void crossfadeInverse(final android.view.View buttons, final android.view.View pathbar) {
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        pathbar.setAlpha(0.0F);
        pathbar.setVisibility(android.view.View.VISIBLE);
        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        pathbar.animate().alpha(1.0F).setDuration(500).setListener(null);
        buttons.animate().alpha(0.0F).setDuration(500).setListener(new android.animation.AnimatorListenerAdapter() {
            @java.lang.Override
            public void onAnimationEnd(android.animation.Animator animation) {
                buttons.setVisibility(android.view.View.GONE);
            }

        });
        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
    }


    public static void shareCloudFile(java.lang.String path, final com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, final android.content.Context context) {
        new android.os.AsyncTask<java.lang.String, java.lang.Void, java.lang.String>() {
            @java.lang.Override
            protected java.lang.String doInBackground(java.lang.String... params) {
                java.lang.String shareFilePath;
                shareFilePath = params[0];
                com.cloudrail.si.interfaces.CloudStorage cloudStorage;
                cloudStorage = com.amaze.filemanager.utils.DataUtils.getInstance().getAccount(openMode);
                return cloudStorage.createShareLink(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(openMode, shareFilePath));
            }


            @java.lang.Override
            protected void onPostExecute(java.lang.String s) {
                super.onPostExecute(s);
                com.amaze.filemanager.filesystem.files.FileUtils.copyToClipboard(context, s);
                android.widget.Toast.makeText(context, context.getString(com.amaze.filemanager.R.string.cloud_share_copied), android.widget.Toast.LENGTH_LONG).show();
            }

        }.execute(path);
    }


    public static void shareCloudFiles(java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> files, final com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, final android.content.Context context) {
        java.lang.String[] paths;
        paths = new java.lang.String[files.size()];
        for (int i = 0; i < files.size(); i++) {
            paths[i] = files.get(i).desc;
        }
        new android.os.AsyncTask<java.lang.String, java.lang.Void, java.lang.String>() {
            @java.lang.Override
            protected java.lang.String doInBackground(java.lang.String... params) {
                com.cloudrail.si.interfaces.CloudStorage cloudStorage;
                cloudStorage = com.amaze.filemanager.utils.DataUtils.getInstance().getAccount(openMode);
                java.lang.StringBuilder links;
                links = new java.lang.StringBuilder();
                links.append(cloudStorage.createShareLink(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(openMode, params[0])));
                for (int i = 1; i < params.length; i++) {
                    links.append('\n');
                    links.append(cloudStorage.createShareLink(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(openMode, params[i])));
                }
                return links.toString();
            }


            @java.lang.Override
            protected void onPostExecute(java.lang.String s) {
                super.onPostExecute(s);
                com.amaze.filemanager.filesystem.files.FileUtils.copyToClipboard(context, s);
                android.widget.Toast.makeText(context, context.getString(com.amaze.filemanager.R.string.cloud_share_copied), android.widget.Toast.LENGTH_LONG).show();
            }

        }.execute(paths);
    }


    public static void shareFiles(java.util.ArrayList<java.io.File> files, android.app.Activity activity, com.amaze.filemanager.ui.theme.AppTheme appTheme, int fab_skin) {
        java.util.ArrayList<android.net.Uri> uris;
        uris = new java.util.ArrayList<>();
        boolean isGenericFileType;
        isGenericFileType = false;
        java.lang.String mime;
        mime = (files.size() > 1) ? com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(files.get(0).getPath(), files.get(0).isDirectory()) : null;
        for (java.io.File f : files) {
            uris.add(androidx.core.content.FileProvider.getUriForFile(activity, activity.getPackageName(), f));
            if ((!isGenericFileType) && ((mime == null) || (!mime.equals(com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(f.getPath(), f.isDirectory()))))) {
                isGenericFileType = true;
            }
        }
        if (isGenericFileType || (mime == null))
            mime = com.amaze.filemanager.ui.icons.MimeTypes.ALL_MIME_TYPES;

        try {
            new com.amaze.filemanager.ui.dialogs.share.ShareTask(activity, uris, appTheme, fab_skin).execute(mime);
        } catch (java.lang.Exception e) {
            com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to get share files", e);
        }
    }


    public static float readableFileSizeFloat(long size) {
        if (size <= 0)
            return 0;

        switch(MUID_STATIC) {
            // FileUtils_0_BinaryMutator
            case 37: {
                return ((float) (size * (1024 * 1024)));
            }
            default: {
            switch(MUID_STATIC) {
                // FileUtils_1_BinaryMutator
                case 1037: {
                    return ((float) (size / (1024 / 1024)));
                }
                default: {
                return ((float) (size / (1024 * 1024)));
                }
        }
        }
}
}


/**
 * Install .apk file.
 *
 * @param permissionsActivity
 * 		needed to ask for {@link Manifest.permission#REQUEST_INSTALL_PACKAGES} permission
 */
public static void installApk(@androidx.annotation.NonNull
final java.io.File f, @androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity permissionsActivity) {
try {
    com.amaze.filemanager.utils.PackageInstallValidation.validatePackageInstallability(f);
} catch (com.amaze.filemanager.utils.PackageInstallValidation.PackageCannotBeInstalledException e) {
    android.widget.Toast.makeText(permissionsActivity, com.amaze.filemanager.R.string.error_google_play_cannot_update_myself, android.widget.Toast.LENGTH_LONG).show();
    return;
} catch (java.lang.IllegalStateException e) {
    android.widget.Toast.makeText(permissionsActivity, permissionsActivity.getString(com.amaze.filemanager.R.string.error_cannot_get_package_info, f.getAbsolutePath()), android.widget.Toast.LENGTH_LONG).show();
}
if ((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) && (!permissionsActivity.getPackageManager().canRequestPackageInstalls())) {
    permissionsActivity.requestInstallApkPermission(() -> com.amaze.filemanager.filesystem.files.FileUtils.installApk(f, permissionsActivity), true);
}
android.content.Intent intent;
switch(MUID_STATIC) {
    // FileUtils_2_NullIntentOperatorMutator
    case 2037: {
        intent = null;
        break;
    }
    // FileUtils_3_InvalidKeyIntentOperatorMutator
    case 3037: {
        intent = new android.content.Intent((String) null);
        break;
    }
    // FileUtils_4_RandomActionIntentDefinitionOperatorMutator
    case 4037: {
        intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
    break;
}
}
java.lang.String type;
type = "application/vnd.android.package-archive";
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
android.net.Uri downloadedApk;
downloadedApk = androidx.core.content.FileProvider.getUriForFile(permissionsActivity.getApplicationContext(), permissionsActivity.getPackageName(), f);
switch(MUID_STATIC) {
    // FileUtils_5_RandomActionIntentDefinitionOperatorMutator
    case 5037: {
        /**
        * Inserted by Kadabra
        */
        /**
        * Inserted by Kadabra
        */
        new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent.setDataAndType(downloadedApk, type);
    break;
}
}
switch(MUID_STATIC) {
// FileUtils_6_RandomActionIntentDefinitionOperatorMutator
case 6037: {
    /**
    * Inserted by Kadabra
    */
    /**
    * Inserted by Kadabra
    */
    new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
break;
}
}
} else {
switch(MUID_STATIC) {
// FileUtils_7_RandomActionIntentDefinitionOperatorMutator
case 7037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.setDataAndType(android.net.Uri.fromFile(f), type);
break;
}
}
switch(MUID_STATIC) {
// FileUtils_8_RandomActionIntentDefinitionOperatorMutator
case 8037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
break;
}
}
}
try {
permissionsActivity.startActivity(intent);
} catch (java.lang.Exception e) {
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to install apk", e);
android.widget.Toast.makeText(permissionsActivity, com.amaze.filemanager.R.string.failed_install_apk, android.widget.Toast.LENGTH_SHORT).show();
}
}


private static void openUnknownInternal(android.net.Uri contentUri, java.lang.String type, com.amaze.filemanager.ui.activities.MainActivity c, boolean forcechooser, boolean useNewStack) {
android.content.Intent chooserIntent;
switch(MUID_STATIC) {
// FileUtils_9_NullIntentOperatorMutator
case 9037: {
chooserIntent = null;
break;
}
// FileUtils_10_RandomActionIntentDefinitionOperatorMutator
case 10037: {
chooserIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
chooserIntent = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// FileUtils_11_RandomActionIntentDefinitionOperatorMutator
case 11037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
chooserIntent.setAction(android.content.Intent.ACTION_VIEW);
break;
}
}
switch(MUID_STATIC) {
// FileUtils_12_RandomActionIntentDefinitionOperatorMutator
case 12037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
chooserIntent.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
break;
}
}
if (((type != null) && (type.trim().length() != 0)) && (!type.equals(com.amaze.filemanager.ui.icons.MimeTypes.ALL_MIME_TYPES))) {
switch(MUID_STATIC) {
// FileUtils_13_RandomActionIntentDefinitionOperatorMutator
case 13037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
chooserIntent.setDataAndType(contentUri, type);
break;
}
}
android.content.Intent activityIntent;
if (forcechooser) {
if (useNewStack)
com.amaze.filemanager.filesystem.files.FileUtils.applyNewDocFlag(chooserIntent);

switch(MUID_STATIC) {
// FileUtils_14_RandomActionIntentDefinitionOperatorMutator
case 14037: {
activityIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
activityIntent = android.content.Intent.createChooser(chooserIntent, c.getString(com.amaze.filemanager.R.string.open_with));
break;
}
}
} else {
activityIntent = chooserIntent;
if (useNewStack)
com.amaze.filemanager.filesystem.files.FileUtils.applyNewDocFlag(chooserIntent);

}
try {
c.startActivity(activityIntent);
} catch (android.content.ActivityNotFoundException e) {
com.amaze.filemanager.filesystem.files.FileUtils.LOG.error(e.getMessage(), e);
android.widget.Toast.makeText(c, com.amaze.filemanager.R.string.no_app_found, android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.filesystem.files.FileUtils.openWith(contentUri, c, useNewStack);
}
} else {
com.amaze.filemanager.filesystem.files.FileUtils.openWith(contentUri, c, useNewStack);
}
}


private static void applyNewDocFlag(android.content.Intent i) {
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
switch(MUID_STATIC) {
// FileUtils_15_RandomActionIntentDefinitionOperatorMutator
case 15037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
i.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
break;
}
}
} else {
switch(MUID_STATIC) {
// FileUtils_16_RandomActionIntentDefinitionOperatorMutator
case 16037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
i.setFlags((android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK) | android.content.Intent.FLAG_ACTIVITY_TASK_ON_HOME);
break;
}
}
}
}


/**
 * Method supports showing a UI to ask user to open a file without any extension/mime
 */
public static void openWith(final java.io.File f, final com.amaze.filemanager.ui.activities.superclasses.PreferenceActivity activity, final boolean useNewStack) {
com.amaze.filemanager.filesystem.files.FileUtils.openWith(androidx.core.content.FileProvider.getUriForFile(activity, activity.getPackageName(), f), activity, useNewStack);
}


public static void openWith(final androidx.documentfile.provider.DocumentFile f, final com.amaze.filemanager.ui.activities.superclasses.PreferenceActivity activity, final boolean useNewStack) {
com.amaze.filemanager.filesystem.files.FileUtils.openWith(f.getUri(), activity, useNewStack);
}


public static void openWith(final android.net.Uri uri, final com.amaze.filemanager.ui.activities.superclasses.PreferenceActivity activity, final boolean useNewStack) {
com.afollestad.materialdialogs.MaterialDialog.Builder a;
a = new com.afollestad.materialdialogs.MaterialDialog.Builder(activity);
a.title(activity.getString(com.amaze.filemanager.R.string.open_as));
java.lang.String[] items;
items = new java.lang.String[]{ activity.getString(com.amaze.filemanager.R.string.text), activity.getString(com.amaze.filemanager.R.string.image), activity.getString(com.amaze.filemanager.R.string.video), activity.getString(com.amaze.filemanager.R.string.audio), activity.getString(com.amaze.filemanager.R.string.database), activity.getString(com.amaze.filemanager.R.string.other) };
a.items(items).itemsCallback((com.afollestad.materialdialogs.MaterialDialog materialDialog,android.view.View view,int i,java.lang.CharSequence charSequence) -> {
java.lang.String mimeType;
mimeType = null;
android.content.Intent intent;
intent = null;
switch (i) {
case 0 :
mimeType = "text/*";
break;
case 1 :
mimeType = "image/*";
break;
case 2 :
mimeType = "video/*";
break;
case 3 :
mimeType = "audio/*";
break;
case 4 :
switch(MUID_STATIC) {
// FileUtils_17_InvalidKeyIntentOperatorMutator
case 17037: {
intent = new android.content.Intent((PreferenceActivity) null, com.amaze.filemanager.ui.activities.DatabaseViewerActivity.class);
break;
}
// FileUtils_18_RandomActionIntentDefinitionOperatorMutator
case 18037: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(activity, com.amaze.filemanager.ui.activities.DatabaseViewerActivity.class);
break;
}
}
switch(MUID_STATIC) {
// FileUtils_19_RandomActionIntentDefinitionOperatorMutator
case 19037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.setAction(android.content.Intent.ACTION_VIEW);
break;
}
}
switch(MUID_STATIC) {
// FileUtils_20_RandomActionIntentDefinitionOperatorMutator
case 20037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
break;
}
}
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
switch(MUID_STATIC) {
// FileUtils_21_RandomActionIntentDefinitionOperatorMutator
case 21037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.addFlags(android.content.Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS);
break;
}
}
}
switch(MUID_STATIC) {
// FileUtils_22_NullValueIntentPutExtraOperatorMutator
case 22037: {
// DatabaseViewerActivity only accepts java.io.File paths, need to strip the URI
// to file's absolute path
intent.putExtra("path", new Parcelable[0]);
break;
}
// FileUtils_23_IntentPayloadReplacementOperatorMutator
case 23037: {
// DatabaseViewerActivity only accepts java.io.File paths, need to strip the URI
// to file's absolute path
intent.putExtra("path", "");
break;
}
default: {
switch(MUID_STATIC) {
// FileUtils_24_RandomActionIntentDefinitionOperatorMutator
case 24037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
switch(MUID_STATIC) {
// FileUtils_25_BinaryMutator
case 25037: {
// DatabaseViewerActivity only accepts java.io.File paths, need to strip the URI
// to file's absolute path
intent.putExtra("path", uri.getPath().substring(uri.getPath().indexOf(com.amaze.filemanager.filesystem.files.FileUtils.FILE_PROVIDER_PREFIX) + 1, com.amaze.filemanager.filesystem.files.FileUtils.FILE_PROVIDER_PREFIX.length() + 1));
break;
}
default: {
switch(MUID_STATIC) {
// FileUtils_26_BinaryMutator
case 26037: {
// DatabaseViewerActivity only accepts java.io.File paths, need to strip the URI
// to file's absolute path
intent.putExtra("path", uri.getPath().substring(uri.getPath().indexOf(com.amaze.filemanager.filesystem.files.FileUtils.FILE_PROVIDER_PREFIX) - 1, com.amaze.filemanager.filesystem.files.FileUtils.FILE_PROVIDER_PREFIX.length() - 1));
break;
}
default: {
// DatabaseViewerActivity only accepts java.io.File paths, need to strip the URI
// to file's absolute path
intent.putExtra("path", uri.getPath().substring(uri.getPath().indexOf(com.amaze.filemanager.filesystem.files.FileUtils.FILE_PROVIDER_PREFIX) - 1, com.amaze.filemanager.filesystem.files.FileUtils.FILE_PROVIDER_PREFIX.length() + 1));
break;
}
}
break;
}
}
break;
}
}
break;
}
}
break;
case 5 :
mimeType = com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(uri.getPath(), false);
if (mimeType == null)
mimeType = com.amaze.filemanager.ui.icons.MimeTypes.ALL_MIME_TYPES;

break;
}
try {
if (intent != null) {
activity.startActivity(intent);
} else {
com.amaze.filemanager.ui.dialogs.OpenFileDialogFragment.Companion.openFileOrShow(uri, mimeType, useNewStack, activity, true);
}
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(activity, com.amaze.filemanager.R.string.no_app_found, android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.filesystem.files.FileUtils.openWith(uri, activity, useNewStack);
}
});
a.build().show();
}


/**
 * Method determines if there is something to go back to
 */
public static boolean canGoBack(android.content.Context context, com.amaze.filemanager.filesystem.HybridFile currentFile) {
switch (currentFile.getMode()) {
// we're on main thread and can't list the cloud files
case DROPBOX :
case BOX :
case GDRIVE :
case ONEDRIVE :
case OTG :
case SFTP :
return true;
default :
return true;// TODO: 29/9/2017 there might be nothing to go back to (check parent)

}
}


public static long[] getSpaces(com.amaze.filemanager.filesystem.HybridFile hFile, android.content.Context context, final com.amaze.filemanager.utils.OnProgressUpdate<java.lang.Long[]> updateState) {
long totalSpace;
totalSpace = hFile.getTotal(context);
long freeSpace;
freeSpace = hFile.getUsableSpace();
long fileSize;
fileSize = 0L;
if (hFile.isDirectory(context)) {
fileSize = hFile.folderSize(context);
} else {
fileSize = hFile.length(context);
}
return new long[]{ totalSpace, freeSpace, fileSize };
}


public static boolean copyToClipboard(android.content.Context context, java.lang.String text) {
try {
android.content.ClipboardManager clipboard;
clipboard = ((android.content.ClipboardManager) (context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
android.content.ClipData clip;
clip = android.content.ClipData.newPlainText(context.getString(com.amaze.filemanager.R.string.clipboard_path_copy), text);
clipboard.setPrimaryClip(clip);
return true;
} catch (java.lang.Exception e) {
return false;
}
}


public static java.lang.String[] getFolderNamesInPath(java.lang.String path) {
if (!path.endsWith("/"))
path += "/";

@androidx.annotation.Nullable
androidx.core.util.Pair<java.lang.String, java.lang.String> splitUri;
splitUri = com.amaze.filemanager.filesystem.files.FileUtils.splitUri(path);
if (splitUri != null) {
path = splitUri.second;
}
return ("root" + path).split("/");
}


/**
 * Parse a given path to a string array of the &quot;steps&quot; to target.
 *
 * <p>For local paths, output will be like <code>
 * ["/", "/storage", "/storage/emulated", "/storage/emulated/0", "/storage/emulated/0/Download", "/storage/emulated/0/Download/file.zip"]
 * </code> For URI paths, output will be like <code>
 * ["smb://user;workgroup:passw0rd@12.3.4", "smb://user;workgroup:passw0rd@12.3.4/user", "smb://user;workgroup:passw0rd@12.3.4/user/Documents", "smb://user;workgroup:passw0rd@12.3.4/user/Documents/flare.doc"]
 * </code>
 *
 * @param path
 * @return string array of incremental path segments
 */
public static java.lang.String[] getPathsInPath(java.lang.String path) {
if (path.endsWith("/")) {
switch(MUID_STATIC) {
// FileUtils_27_BinaryMutator
case 27037: {
path = path.substring(0, path.length() + 1);
break;
}
default: {
path = path.substring(0, path.length() - 1);
break;
}
}
}
path = path.trim();
java.util.ArrayList<java.lang.String> paths;
paths = new java.util.ArrayList<>();
@androidx.annotation.Nullable
java.lang.String urlPrefix;
urlPrefix = null;
@androidx.annotation.Nullable
androidx.core.util.Pair<java.lang.String, java.lang.String> splitUri;
splitUri = com.amaze.filemanager.filesystem.files.FileUtils.splitUri(path);
if (splitUri != null) {
urlPrefix = splitUri.first;
path = splitUri.second;
}
if (!path.startsWith("/")) {
path = "/" + path;
}
while (path.length() > 0) {
if (urlPrefix != null) {
paths.add(urlPrefix + path);
} else {
paths.add(path);
}
if (path.contains("/")) {
path = path.substring(0, path.lastIndexOf('/'));
} else {
break;
}
} 
if (urlPrefix != null) {
paths.add(urlPrefix);
} else {
paths.add("/");
}
java.util.Collections.reverse(paths);
return paths.toArray(new java.lang.String[0]);
}


/**
 * Splits a given path to URI prefix (if exists) and path.
 *
 * @param path
 * @return {@link Pair} tuple if given path is URI (scheme is not null). Tuple contains:
<ul>
<li>First: URI section of the given path, if given path is an URI
<li>Second: Path section of the given path. Never null
</ul>
 */
@androidx.annotation.Nullable
public static androidx.core.util.Pair<java.lang.String, java.lang.String> splitUri(@androidx.annotation.NonNull
final java.lang.String path) {
android.net.Uri uri;
uri = android.net.Uri.parse(path);
if (uri.getScheme() != null) {
java.lang.String urlPrefix;
urlPrefix = (uri.getScheme() + "://") + uri.getEncodedAuthority();
java.lang.String retPath;
retPath = path.substring(urlPrefix.length());
return new androidx.core.util.Pair<>(urlPrefix, retPath);
} else {
return null;
}
}


public static boolean canListFiles(java.io.File f) {
return f.canRead() && f.isDirectory();
}


public static void openFile(@androidx.annotation.NonNull
final java.io.File f, @androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.MainActivity mainActivity, @androidx.annotation.NonNull
final android.content.SharedPreferences sharedPrefs) {
boolean useNewStack;
useNewStack = sharedPrefs.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK, false);
boolean defaultHandler;
defaultHandler = com.amaze.filemanager.filesystem.files.FileUtils.isSelfDefault(f, mainActivity);
if (f.getName().toLowerCase().endsWith(".apk")) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showPackageDialog(f, mainActivity);
} else if (defaultHandler && com.amaze.filemanager.filesystem.compressed.CompressedHelper.isFileExtractable(f.getPath())) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showArchiveDialog(f, mainActivity);
} else if (defaultHandler && f.getName().toLowerCase().endsWith(".db")) {
android.content.Intent intent;
switch(MUID_STATIC) {
// FileUtils_28_NullIntentOperatorMutator
case 28037: {
intent = null;
break;
}
// FileUtils_29_InvalidKeyIntentOperatorMutator
case 29037: {
intent = new android.content.Intent((MainActivity) null, com.amaze.filemanager.ui.activities.DatabaseViewerActivity.class);
break;
}
// FileUtils_30_RandomActionIntentDefinitionOperatorMutator
case 30037: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(mainActivity, com.amaze.filemanager.ui.activities.DatabaseViewerActivity.class);
break;
}
}
switch(MUID_STATIC) {
// FileUtils_31_RandomActionIntentDefinitionOperatorMutator
case 31037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.setType(com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(f.getPath(), false));
break;
}
}
switch(MUID_STATIC) {
// FileUtils_32_NullValueIntentPutExtraOperatorMutator
case 32037: {
intent.putExtra("path", new Parcelable[0]);
break;
}
// FileUtils_33_IntentPayloadReplacementOperatorMutator
case 33037: {
intent.putExtra("path", "");
break;
}
default: {
switch(MUID_STATIC) {
// FileUtils_34_RandomActionIntentDefinitionOperatorMutator
case 34037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.putExtra("path", f.getPath());
break;
}
}
break;
}
}
mainActivity.startActivity(intent);
} else {
try {
com.amaze.filemanager.filesystem.files.FileUtils.openFileDialogFragmentFor(f, mainActivity, useNewStack);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(mainActivity, mainActivity.getString(com.amaze.filemanager.R.string.no_app_found), android.widget.Toast.LENGTH_LONG).show();
com.amaze.filemanager.filesystem.files.FileUtils.openWith(f, mainActivity, useNewStack);
}
}
}


private static void openFileDialogFragmentFor(@androidx.annotation.NonNull
java.io.File file, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivity mainActivity, @androidx.annotation.NonNull
java.lang.Boolean useNewStack) {
com.amaze.filemanager.filesystem.files.FileUtils.openFileDialogFragmentFor(file, mainActivity, com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(file.getAbsolutePath(), false), useNewStack);
}


private static void openFileDialogFragmentFor(@androidx.annotation.NonNull
java.io.File file, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivity mainActivity, @androidx.annotation.NonNull
java.lang.String mimeType, @androidx.annotation.NonNull
java.lang.Boolean useNewStack) {
com.amaze.filemanager.ui.dialogs.OpenFileDialogFragment.Companion.openFileOrShow(androidx.core.content.FileProvider.getUriForFile(mainActivity, mainActivity.getPackageName(), file), mimeType, useNewStack, mainActivity, false);
}


private static void openFileDialogFragmentFor(@androidx.annotation.NonNull
androidx.documentfile.provider.DocumentFile file, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivity mainActivity, @androidx.annotation.NonNull
java.lang.Boolean useNewStack) {
com.amaze.filemanager.filesystem.files.FileUtils.openFileDialogFragmentFor(file.getUri(), mainActivity, com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(file.getUri().toString(), false), useNewStack);
}


private static void openFileDialogFragmentFor(@androidx.annotation.NonNull
android.net.Uri uri, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivity mainActivity, @androidx.annotation.NonNull
java.lang.String mimeType, @androidx.annotation.NonNull
java.lang.Boolean useNewStack) {
com.amaze.filemanager.ui.dialogs.OpenFileDialogFragment.Companion.openFileOrShow(uri, mimeType, useNewStack, mainActivity, false);
}


private static boolean isSelfDefault(java.io.File f, android.content.Context c) {
android.content.Intent intent;
switch(MUID_STATIC) {
// FileUtils_35_NullIntentOperatorMutator
case 35037: {
intent = null;
break;
}
// FileUtils_36_InvalidKeyIntentOperatorMutator
case 36037: {
intent = new android.content.Intent((String) null);
break;
}
// FileUtils_37_RandomActionIntentDefinitionOperatorMutator
case 37037: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
break;
}
}
switch(MUID_STATIC) {
// FileUtils_38_RandomActionIntentDefinitionOperatorMutator
case 38037: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.setDataAndType(android.net.Uri.fromFile(f), com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(f.getPath(), f.isDirectory()));
break;
}
}
android.content.pm.ResolveInfo info;
info = c.getPackageManager().resolveActivity(intent, android.content.pm.PackageManager.MATCH_DEFAULT_ONLY);
if ((info != null) && (info.activityInfo != null)) {
return info.activityInfo.packageName.equals(c.getPackageName());
} else {
return true;
}
}


/**
 * Support file opening for {@link DocumentFile} (eg. OTG)
 */
public static void openFile(final androidx.documentfile.provider.DocumentFile f, final com.amaze.filemanager.ui.activities.MainActivity m, android.content.SharedPreferences sharedPrefs) {
boolean useNewStack;
useNewStack = sharedPrefs.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK, false);
try {
com.amaze.filemanager.filesystem.files.FileUtils.openFileDialogFragmentFor(f, m, useNewStack);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(m, m.getString(com.amaze.filemanager.R.string.no_app_found), android.widget.Toast.LENGTH_LONG).show();
com.amaze.filemanager.filesystem.files.FileUtils.openWith(f, m, useNewStack);
}
}


public static void launchSMB(final com.amaze.filemanager.filesystem.HybridFile baseFile, final android.app.Activity activity) {
final com.amaze.filemanager.fileoperations.filesystem.smbstreamer.Streamer s;
s = com.amaze.filemanager.fileoperations.filesystem.smbstreamer.Streamer.getInstance();
new java.lang.Thread() {
public void run() {
try {
/* List<SmbFile> subtitleFiles = new ArrayList<SmbFile>();

// finding subtitles
for (Layoutelements layoutelement : LIST_ELEMENTS) {
SmbFile smbFile = new SmbFile(layoutelement.getDesc());
if (smbFile.getName().contains(smbFile.getName())) subtitleFiles.add(smbFile);
}
 */
s.setStreamSrc(baseFile.getSmbFile(), baseFile.length(activity));
activity.runOnUiThread(() -> {
switch(MUID_STATIC) {
// FileUtils_39_InvalidKeyIntentOperatorMutator
case 39037: {
try {
android.net.Uri uri;
uri = android.net.Uri.parse(com.amaze.filemanager.fileoperations.filesystem.smbstreamer.Streamer.URL + android.net.Uri.fromFile(new java.io.File(android.net.Uri.parse(baseFile.getPath()).getPath())).getEncodedPath());
android.content.Intent i;
i = new android.content.Intent((String) null);
i.setDataAndType(uri, com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(baseFile.getPath(), baseFile.isDirectory(activity)));
android.content.pm.PackageManager packageManager;
packageManager = activity.getPackageManager();
java.util.List<android.content.pm.ResolveInfo> resInfos;
resInfos = packageManager.queryIntentActivities(i, 0);
if ((resInfos != null) && (resInfos.size() > 0)) {
activity.startActivity(i);
} else {
android.widget.Toast.makeText(activity, activity.getResources().getString(com.amaze.filemanager.R.string.smb_launch_error), android.widget.Toast.LENGTH_SHORT).show();
}
} catch (android.content.ActivityNotFoundException e) {
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("Failed to launch smb file due to no activity", e);
}
break;
}
// FileUtils_40_RandomActionIntentDefinitionOperatorMutator
case 40037: {
try {
android.net.Uri uri;
uri = android.net.Uri.parse(com.amaze.filemanager.fileoperations.filesystem.smbstreamer.Streamer.URL + android.net.Uri.fromFile(new java.io.File(android.net.Uri.parse(baseFile.getPath()).getPath())).getEncodedPath());
android.content.Intent i;
i = new android.content.Intent(android.content.Intent.ACTION_SEND);
i.setDataAndType(uri, com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(baseFile.getPath(), baseFile.isDirectory(activity)));
android.content.pm.PackageManager packageManager;
packageManager = activity.getPackageManager();
java.util.List<android.content.pm.ResolveInfo> resInfos;
resInfos = packageManager.queryIntentActivities(i, 0);
if ((resInfos != null) && (resInfos.size() > 0)) {
activity.startActivity(i);
} else {
android.widget.Toast.makeText(activity, activity.getResources().getString(com.amaze.filemanager.R.string.smb_launch_error), android.widget.Toast.LENGTH_SHORT).show();
}
} catch (android.content.ActivityNotFoundException e) {
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("Failed to launch smb file due to no activity", e);
}
break;
}
default: {
switch(MUID_STATIC) {
// FileUtils_41_RandomActionIntentDefinitionOperatorMutator
case 41037: {
try {
android.net.Uri uri;
uri = android.net.Uri.parse(com.amaze.filemanager.fileoperations.filesystem.smbstreamer.Streamer.URL + android.net.Uri.fromFile(new java.io.File(android.net.Uri.parse(baseFile.getPath()).getPath())).getEncodedPath());
android.content.Intent i;
i = new android.content.Intent(android.content.Intent.ACTION_VIEW);
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);;
android.content.pm.PackageManager packageManager;
packageManager = activity.getPackageManager();
java.util.List<android.content.pm.ResolveInfo> resInfos;
resInfos = packageManager.queryIntentActivities(i, 0);
if ((resInfos != null) && (resInfos.size() > 0)) {
activity.startActivity(i);
} else {
android.widget.Toast.makeText(activity, activity.getResources().getString(com.amaze.filemanager.R.string.smb_launch_error), android.widget.Toast.LENGTH_SHORT).show();
}
} catch (android.content.ActivityNotFoundException e) {
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("Failed to launch smb file due to no activity", e);
}
break;
}
default: {
try {
android.net.Uri uri;
uri = android.net.Uri.parse(com.amaze.filemanager.fileoperations.filesystem.smbstreamer.Streamer.URL + android.net.Uri.fromFile(new java.io.File(android.net.Uri.parse(baseFile.getPath()).getPath())).getEncodedPath());
android.content.Intent i;
i = new android.content.Intent(android.content.Intent.ACTION_VIEW);
i.setDataAndType(uri, com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(baseFile.getPath(), baseFile.isDirectory(activity)));
android.content.pm.PackageManager packageManager;
packageManager = activity.getPackageManager();
java.util.List<android.content.pm.ResolveInfo> resInfos;
resInfos = packageManager.queryIntentActivities(i, 0);
if ((resInfos != null) && (resInfos.size() > 0))
activity.startActivity(i);
else
android.widget.Toast.makeText(activity, activity.getResources().getString(com.amaze.filemanager.R.string.smb_launch_error), android.widget.Toast.LENGTH_SHORT).show();

} catch (android.content.ActivityNotFoundException e) {
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("Failed to launch smb file due to no activity", e);
}
break;
}
}
break;
}
}
});
} catch (java.lang.Exception e) {
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to launch smb file", e);
}
}

}.start();
}


public static java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile> toHybridFileConcurrentRadixTree(com.googlecode.concurrenttrees.radix.ConcurrentRadixTree<com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue> a) {
java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile> b;
b = new java.util.ArrayList<>();
for (java.lang.CharSequence o : a.getKeysStartingWith("")) {
com.amaze.filemanager.filesystem.HybridFile hFile;
hFile = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN, o.toString());
hFile.generateMode(null);
b.add(hFile);
}
return b;
}


public static java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile> toHybridFileArrayList(java.util.LinkedList<java.lang.String> a) {
java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile> b;
b = new java.util.ArrayList<>();
for (java.lang.String s : a) {
com.amaze.filemanager.filesystem.HybridFile hFile;
hFile = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN, s);
hFile.generateMode(null);
b.add(hFile);
}
return b;
}


/**
 * We're parsing a line returned from a stdout of shell.
 *
 * @param line
 * 		must be the line returned from 'ls' or 'stat' command
 */
public static com.amaze.filemanager.filesystem.HybridFileParcelable parseName(java.lang.String line, boolean isStat) {
boolean linked;
linked = false;
java.lang.StringBuilder name;
name = new java.lang.StringBuilder();
java.lang.StringBuilder link;
link = new java.lang.StringBuilder();
java.lang.String size;
size = "-1";
java.lang.String date;
date = "";
java.lang.String[] array;
array = line.split(" +");
if (array.length < 6)
return null;

for (java.lang.String anArray : array) {
if (anArray.contains("->") && array[0].startsWith("l")) {
linked = true;
break;
}
}
int p;
p = com.amaze.filemanager.filesystem.files.FileUtils.getColonPosition(array);
switch(MUID_STATIC) {
// FileUtils_42_BinaryMutator
case 42037: {
if ((p != (-1)) && ((p - 1) != array.length)) {
date = (array[p - 1] + " | ") + array[p];
size = array[p - 2];
} else if (isStat) {
date = array[5];
size = array[4];
p = 5;
}
break;
}
default: {
if ((p != (-1)) && ((p + 1) != array.length)) {
switch(MUID_STATIC) {
// FileUtils_43_BinaryMutator
case 43037: {
date = (array[p + 1] + " | ") + array[p];
break;
}
default: {
date = (array[p - 1] + " | ") + array[p];
break;
}
}
switch(MUID_STATIC) {
// FileUtils_44_BinaryMutator
case 44037: {
size = array[p + 2];
break;
}
default: {
size = array[p - 2];
break;
}
}
} else if (isStat) {
date = array[5];
size = array[4];
p = 5;
}
break;
}
}
if (!linked) {
for (int i = p + 1; i < array.length; i++) {
name.append(" ").append(array[i]);
}
name = new java.lang.StringBuilder(name.toString().trim());
} else {
int q;
q = com.amaze.filemanager.filesystem.files.FileUtils.getLinkPosition(array);
for (int i = p + 1; i < q; i++) {
name.append(" ").append(array[i]);
}
// Newer *boxes may introduce full path during stat. Trim down to the very last /
if (name.lastIndexOf("/") > 0) {
switch(MUID_STATIC) {
// FileUtils_45_BinaryMutator
case 45037: {
name.delete(0, name.lastIndexOf("/") - 1);
break;
}
default: {
name.delete(0, name.lastIndexOf("/") + 1);
break;
}
}
}
name = new java.lang.StringBuilder(name.toString().trim());
for (int i = q + 1; i < array.length; i++) {
link.append(" ").append(array[i]);
}
link = new java.lang.StringBuilder(link.toString().trim());
}
long Size;
if ((size == null) || (size.trim().length() == 0)) {
Size = -1;
} else {
try {
Size = java.lang.Long.parseLong(size);
} catch (java.lang.NumberFormatException ifItIsNotANumber) {
Size = -1;
}
}
if ((date.trim().length() > 0) && (!isStat)) {
java.text.ParsePosition pos;
pos = new java.text.ParsePosition(0);
java.text.SimpleDateFormat simpledateformat;
simpledateformat = new java.text.SimpleDateFormat("yyyy-MM-dd | HH:mm", java.util.Locale.US);
java.util.Date stringDate;
stringDate = simpledateformat.parse(date, pos);
if (stringDate == null) {
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn(("parseName: unable to parse datetime string [" + date) + "]");
}
com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
baseFile = new com.amaze.filemanager.filesystem.HybridFileParcelable(name.toString(), array[0], stringDate != null ? stringDate.getTime() : 0, Size, true);
baseFile.setLink(link.toString());
return baseFile;
} else if (isStat) {
com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
switch(MUID_STATIC) {
// FileUtils_46_BinaryMutator
case 46037: {
baseFile = new com.amaze.filemanager.filesystem.HybridFileParcelable(name.toString(), array[0], java.lang.Long.parseLong(date) / 1000, Size, true);
break;
}
default: {
baseFile = new com.amaze.filemanager.filesystem.HybridFileParcelable(name.toString(), array[0], java.lang.Long.parseLong(date) * 1000, Size, true);
break;
}
}
baseFile.setLink(link.toString());
return baseFile;
} else {
com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
baseFile = new com.amaze.filemanager.filesystem.HybridFileParcelable(name.toString(), array[0], new java.io.File("/").lastModified(), Size, true);
baseFile.setLink(link.toString());
return baseFile;
}
}


private static int getLinkPosition(java.lang.String[] array) {
for (int i = 0; i < array.length; i++) {
if (array[i].contains("->"))
return i;

}
return 0;
}


private static int getColonPosition(java.lang.String[] array) {
for (int i = 0; i < array.length; i++) {
if (array[i].contains(":"))
return i;

}
return -1;
}


public static java.util.ArrayList<java.lang.Boolean[]> parse(java.lang.String permLine) {
java.util.ArrayList<java.lang.Boolean[]> arrayList;
arrayList = new java.util.ArrayList<>(3);
java.lang.Boolean[] read;
read = new java.lang.Boolean[]{ permLine.charAt(1) == 'r', permLine.charAt(4) == 'r', permLine.charAt(7) == 'r' };
java.lang.Boolean[] write;
write = new java.lang.Boolean[]{ permLine.charAt(2) == 'w', permLine.charAt(5) == 'w', permLine.charAt(8) == 'w' };
java.lang.Boolean[] execute;
execute = new java.lang.Boolean[]{ permLine.charAt(3) == 'x', permLine.charAt(6) == 'x', permLine.charAt(9) == 'x' };
arrayList.add(read);
arrayList.add(write);
arrayList.add(execute);
return arrayList;
}


public static boolean isStorage(java.lang.String path) {
for (java.lang.String s : com.amaze.filemanager.utils.DataUtils.getInstance().getStorages())
if (s.equals(path))
return true;


return false;
}


public static boolean isPathAccessible(java.lang.String dir, android.content.SharedPreferences pref) {
java.io.File f;
f = new java.io.File(dir);
boolean showIfHidden;
showIfHidden = pref.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_HIDDENFILES, false);
boolean isDirSelfOrParent;
isDirSelfOrParent = dir.endsWith("/.") || dir.endsWith("/..");
boolean showIfRoot;
showIfRoot = pref.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ROOTMODE, false);
return ((f.exists() && f.isDirectory()) && ((!f.isHidden()) || (showIfHidden && (!isDirSelfOrParent)))) && ((!com.amaze.filemanager.filesystem.files.FileUtils.isRoot(dir)) || showIfRoot);
// TODO: 2/5/2017 use another system that doesn't create new object
}


public static boolean isRoot(java.lang.String dir) {
// TODO: 5/5/2017 hardcoding root might lead to problems down the line
return ((!dir.contains(com.amaze.filemanager.utils.OTGUtil.PREFIX_OTG)) && (!dir.startsWith(com.amaze.filemanager.utils.OTGUtil.PREFIX_MEDIA_REMOVABLE))) && (!dir.startsWith("/storage"));
}


/**
 * Convenience method to return if a path points to a compressed file.
 */
public static boolean isCompressedFile(java.lang.String path) {
@androidx.annotation.Nullable
java.lang.String extension;
extension = com.amaze.filemanager.ui.icons.MimeTypes.getExtension(path);
return kotlin.collections.ArraysKt.indexOf(com.amaze.filemanager.filesystem.files.FileUtils.COMPRESSED_FILE_EXTENSIONS, extension) > (-1);
}


/**
 * Converts ArrayList of HybridFileParcelable to ArrayList of File
 */
public static java.util.ArrayList<java.io.File> hybridListToFileArrayList(java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> a) {
java.util.ArrayList<java.io.File> b;
b = new java.util.ArrayList<>();
for (int i = 0; i < a.size(); i++) {
b.add(new java.io.File(a.get(i).getPath()));
}
return b;
}


/**
 * Checks whether path for bookmark exists If path is not found, empty directory is created
 */
public static void checkForPath(android.content.Context context, java.lang.String path, boolean isRootExplorer) {
// TODO: Add support for SMB and OTG in this function
if (!new java.io.File(path).exists()) {
android.widget.Toast.makeText(context, context.getString(com.amaze.filemanager.R.string.bookmark_lost), android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.filesystem.Operations.mkdir(new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, path), com.amaze.filemanager.filesystem.RootHelper.generateBaseFile(new java.io.File(path), true), context, isRootExplorer, new com.amaze.filemanager.filesystem.Operations.ErrorCallBack() {
// TODO empty
@java.lang.Override
public void exists(com.amaze.filemanager.filesystem.HybridFile file) {
}


@java.lang.Override
public void launchSAF(com.amaze.filemanager.filesystem.HybridFile file) {
}


@java.lang.Override
public void launchSAF(com.amaze.filemanager.filesystem.HybridFile file, com.amaze.filemanager.filesystem.HybridFile file1) {
}


@java.lang.Override
public void done(com.amaze.filemanager.filesystem.HybridFile hFile, boolean b) {
}


@java.lang.Override
public void invalidName(com.amaze.filemanager.filesystem.HybridFile file) {
}

});
}
}


public static java.io.File fromContentUri(@androidx.annotation.NonNull
android.net.Uri uri) {
if (!com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.CONTENT.name().equalsIgnoreCase(uri.getScheme())) {
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn(("URI must start with content://. URI was [" + uri) + "]");
}
java.io.File pathFile;
switch(MUID_STATIC) {
// FileUtils_47_BinaryMutator
case 47037: {
pathFile = new java.io.File(uri.getPath().substring(com.amaze.filemanager.filesystem.files.FileUtils.FILE_PROVIDER_PREFIX.length() - 1));
break;
}
default: {
pathFile = new java.io.File(uri.getPath().substring(com.amaze.filemanager.filesystem.files.FileUtils.FILE_PROVIDER_PREFIX.length() + 1));
break;
}
}
if (!pathFile.exists()) {
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to navigate to path {}", pathFile.getPath());
pathFile = new java.io.File(uri.getPath());
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("trying to navigate to path {}", pathFile.getPath());
}
return pathFile;
}


/**
 * Uninstalls a given package
 *
 * @param pkg
 * 		packge
 * @param context
 * 		context
 * @return success
 */
public static boolean uninstallPackage(java.lang.String pkg, android.content.Context context) {
switch(MUID_STATIC) {
// FileUtils_48_NullIntentOperatorMutator
case 48037: {
try {
android.content.Intent intent;
intent = null;
intent.setData(android.net.Uri.parse("package:" + pkg));
context.startActivity(intent);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(context, "" + e, android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to uninstall apk", e);
return false;
}
break;
}
// FileUtils_49_InvalidKeyIntentOperatorMutator
case 49037: {
try {
android.content.Intent intent;
intent = new android.content.Intent((String) null);
intent.setData(android.net.Uri.parse("package:" + pkg));
context.startActivity(intent);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(context, "" + e, android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to uninstall apk", e);
return false;
}
break;
}
// FileUtils_50_RandomActionIntentDefinitionOperatorMutator
case 50037: {
try {
android.content.Intent intent;
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
intent.setData(android.net.Uri.parse("package:" + pkg));
context.startActivity(intent);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(context, "" + e, android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to uninstall apk", e);
return false;
}
break;
}
default: {
switch(MUID_STATIC) {
// FileUtils_51_RandomActionIntentDefinitionOperatorMutator
case 51037: {
try {
android.content.Intent intent;
intent = new android.content.Intent(android.content.Intent.ACTION_DELETE);
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);;
context.startActivity(intent);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(context, "" + e, android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to uninstall apk", e);
return false;
}
break;
}
default: {
try {
android.content.Intent intent;
intent = new android.content.Intent(android.content.Intent.ACTION_DELETE);
intent.setData(android.net.Uri.parse("package:" + pkg));
context.startActivity(intent);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(context, "" + e, android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.filesystem.files.FileUtils.LOG.warn("failed to uninstall apk", e);
return false;
}
break;
}
}
break;
}
}
return true;
}


/**
 * Determines the specified path is beyond storage level, i.e should require root access.
 */
@java.lang.SuppressWarnings("PMD.DoNotHardCodeSDCard")
public static boolean isRunningAboveStorage(@androidx.annotation.NonNull
java.lang.String path) {
return (!path.startsWith("/storage")) && (!path.startsWith("/sdcard"));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
