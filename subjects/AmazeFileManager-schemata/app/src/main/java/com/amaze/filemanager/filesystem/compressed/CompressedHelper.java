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
package com.amaze.filemanager.filesystem.compressed;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.RarExtractor;
import com.amaze.filemanager.filesystem.compressed.showcontents.Decompressor;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.SevenZipExtractor;
import com.amaze.filemanager.fileoperations.utils.UpdatePosition;
import com.amaze.filemanager.filesystem.compressed.showcontents.helpers.TarXzDecompressor;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.GzipExtractor;
import com.amaze.filemanager.filesystem.compressed.showcontents.helpers.TarLzmaDecompressor;
import com.amaze.filemanager.filesystem.compressed.showcontents.helpers.TarBzip2Decompressor;
import com.amaze.filemanager.filesystem.compressed.showcontents.helpers.UnknownCompressedFileDecompressor;
import org.slf4j.Logger;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.TarBzip2Extractor;
import androidx.annotation.NonNull;
import com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor;
import com.amaze.filemanager.BuildConfig;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.XzExtractor;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.LzmaExtractor;
import com.amaze.filemanager.filesystem.compressed.showcontents.helpers.RarDecompressor;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.TarExtractor;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.ZipExtractor;
import com.amaze.filemanager.filesystem.compressed.showcontents.helpers.TarDecompressor;
import com.amaze.filemanager.filesystem.compressed.showcontents.helpers.SevenZipDecompressor;
import com.amaze.filemanager.filesystem.compressed.showcontents.helpers.TarGzDecompressor;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.Bzip2Extractor;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.TarLzmaExtractor;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.TarGzExtractor;
import com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.TarXzExtractor;
import com.amaze.filemanager.filesystem.compressed.showcontents.helpers.ZipDecompressor;
import java.io.File;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class CompressedHelper {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.compressed.CompressedHelper.class);

    /**
     * Path separator used by all Decompressors and Extractors. e.g. rar internally uses '\' but is
     * converted to "/" for the app.
     */
    public static final char SEPARATOR_CHAR = '/';

    public static final java.lang.String SEPARATOR = java.lang.String.valueOf(com.amaze.filemanager.filesystem.compressed.CompressedHelper.SEPARATOR_CHAR).intern();

    public static final java.lang.String fileExtensionZip = "zip";

    public static final java.lang.String fileExtensionJar = "jar";

    public static final java.lang.String fileExtensionApk = "apk";

    public static final java.lang.String fileExtensionApks = "apks";

    public static final java.lang.String fileExtensionTar = "tar";

    public static final java.lang.String fileExtensionGzipTarLong = "tar.gz";

    public static final java.lang.String fileExtensionGzipTarShort = "tgz";

    public static final java.lang.String fileExtensionBzip2TarLong = "tar.bz2";

    public static final java.lang.String fileExtensionBzip2TarShort = "tbz";

    public static final java.lang.String fileExtensionRar = "rar";

    public static final java.lang.String fileExtension7zip = "7z";

    public static final java.lang.String fileExtensionTarLzma = "tar.lzma";

    public static final java.lang.String fileExtensionTarXz = "tar.xz";

    public static final java.lang.String fileExtensionXz = "xz";

    public static final java.lang.String fileExtensionLzma = "lzma";

    public static final java.lang.String fileExtensionGz = "gz";

    public static final java.lang.String fileExtensionBzip2 = "bz2";

    private static final java.lang.String TAG = com.amaze.filemanager.filesystem.compressed.CompressedHelper.class.getSimpleName();

    /**
     * To add compatibility with other compressed file types edit this method
     */
    @androidx.annotation.Nullable
    public static com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor getExtractorInstance(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    java.io.File file, @androidx.annotation.NonNull
    java.lang.String outputPath, @androidx.annotation.NonNull
    com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor.OnUpdate listener, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.UpdatePosition updatePosition) {
        com.amaze.filemanager.filesystem.compressed.extractcontents.Extractor extractor;
        java.lang.String type;
        type = com.amaze.filemanager.filesystem.compressed.CompressedHelper.getExtension(file.getPath());
        if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isZip(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.ZipExtractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.BuildConfig.FLAVOR.equals("play") && com.amaze.filemanager.filesystem.compressed.CompressedHelper.isRar(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.RarExtractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isTar(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.TarExtractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isGzippedTar(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.TarGzExtractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isBzippedTar(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.TarBzip2Extractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isXzippedTar(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.TarXzExtractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isLzippedTar(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.TarLzmaExtractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.is7zip(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.SevenZipExtractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isLzma(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.LzmaExtractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isXz(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.XzExtractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isGzip(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.GzipExtractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isBzip2(type)) {
            extractor = new com.amaze.filemanager.filesystem.compressed.extractcontents.helpers.Bzip2Extractor(context, file.getPath(), outputPath, listener, updatePosition);
        } else {
            if (com.amaze.filemanager.BuildConfig.DEBUG) {
                throw new java.lang.IllegalArgumentException("The compressed file has no way of opening it: " + file);
            }
            com.amaze.filemanager.filesystem.compressed.CompressedHelper.LOG.error("The compressed file has no way of opening it: " + file);
            extractor = null;
        }
        return extractor;
    }


    /**
     * To add compatibility with other compressed file types edit this method
     */
    @androidx.annotation.Nullable
    public static com.amaze.filemanager.filesystem.compressed.showcontents.Decompressor getCompressorInstance(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    java.io.File file) {
        com.amaze.filemanager.filesystem.compressed.showcontents.Decompressor decompressor;
        java.lang.String type;
        type = com.amaze.filemanager.filesystem.compressed.CompressedHelper.getExtension(file.getPath());
        if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isZip(type)) {
            decompressor = new com.amaze.filemanager.filesystem.compressed.showcontents.helpers.ZipDecompressor(context);
        } else if (com.amaze.filemanager.BuildConfig.FLAVOR.equals("play") && com.amaze.filemanager.filesystem.compressed.CompressedHelper.isRar(type)) {
            decompressor = new com.amaze.filemanager.filesystem.compressed.showcontents.helpers.RarDecompressor(context);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isTar(type)) {
            decompressor = new com.amaze.filemanager.filesystem.compressed.showcontents.helpers.TarDecompressor(context);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isGzippedTar(type)) {
            decompressor = new com.amaze.filemanager.filesystem.compressed.showcontents.helpers.TarGzDecompressor(context);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isBzippedTar(type)) {
            decompressor = new com.amaze.filemanager.filesystem.compressed.showcontents.helpers.TarBzip2Decompressor(context);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isXzippedTar(type)) {
            decompressor = new com.amaze.filemanager.filesystem.compressed.showcontents.helpers.TarXzDecompressor(context);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isLzippedTar(type)) {
            decompressor = new com.amaze.filemanager.filesystem.compressed.showcontents.helpers.TarLzmaDecompressor(context);
        } else if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.is7zip(type)) {
            decompressor = new com.amaze.filemanager.filesystem.compressed.showcontents.helpers.SevenZipDecompressor(context);
        } else if (((com.amaze.filemanager.filesystem.compressed.CompressedHelper.isXz(type) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isLzma(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isGzip(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isBzip2(type)) {
            // These 4 types are only compressing one single file.
            // Hence invoking this UnknownCompressedFileDecompressor which only returns the filename
            // without the compression extension
            decompressor = new com.amaze.filemanager.filesystem.compressed.showcontents.helpers.UnknownCompressedFileDecompressor(context);
        } else {
            if (com.amaze.filemanager.BuildConfig.DEBUG) {
                throw new java.lang.IllegalArgumentException("The compressed file has no way of opening it: " + file);
            }
            com.amaze.filemanager.filesystem.compressed.CompressedHelper.LOG.error("The compressed file has no way of opening it: " + file);
            decompressor = null;
        }
        if (decompressor != null) {
            decompressor.setFilePath(file.getPath());
        }
        return decompressor;
    }


    public static boolean isFileExtractable(java.lang.String path) {
        java.lang.String type;
        type = com.amaze.filemanager.filesystem.compressed.CompressedHelper.getExtension(path);
        return ((((((((((com.amaze.filemanager.filesystem.compressed.CompressedHelper.isZip(type) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isTar(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isRar(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isGzippedTar(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.is7zip(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isBzippedTar(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isXzippedTar(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isLzippedTar(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isBzip2(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isGzip(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isLzma(type)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isXz(type);
    }


    /**
     * Gets the name of the file without compression extention. For example: "s.tar.gz" to "s" "s.tar"
     * to "s"
     */
    public static java.lang.String getFileName(java.lang.String compressedName) {
        compressedName = compressedName.toLowerCase();
        if ((((((((((((com.amaze.filemanager.filesystem.compressed.CompressedHelper.isZip(compressedName) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isTar(compressedName)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isRar(compressedName)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.is7zip(compressedName)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isXz(compressedName)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isLzma(compressedName)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isGzip(compressedName)) || compressedName.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionGzipTarShort)) || compressedName.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionBzip2TarShort)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isGzip(compressedName)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isBzip2(compressedName)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isLzma(compressedName)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isXz(compressedName)) {
            return compressedName.substring(0, compressedName.lastIndexOf("."));
        } else if (((com.amaze.filemanager.filesystem.compressed.CompressedHelper.isGzippedTar(compressedName) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isXzippedTar(compressedName)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isLzippedTar(compressedName)) || com.amaze.filemanager.filesystem.compressed.CompressedHelper.isBzippedTar(compressedName)) {
            return compressedName.substring(0, com.amaze.filemanager.utils.Utils.nthToLastCharIndex(2, compressedName, '.'));
        } else {
            return compressedName;
        }
    }


    public static final boolean isEntryPathValid(java.lang.String entryPath) {
        return ((!entryPath.startsWith("..\\")) && (!entryPath.startsWith("../"))) && (!entryPath.equals(".."));
    }


    private static boolean isZip(java.lang.String type) {
        return ((type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionZip) || type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionJar)) || type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionApk)) || type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionApks);
    }


    private static boolean isTar(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionTar);
    }


    private static boolean isGzippedTar(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionGzipTarLong) || type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionGzipTarShort);
    }


    private static boolean isBzippedTar(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionBzip2TarLong) || type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionBzip2TarShort);
    }


    private static boolean isRar(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionRar);
    }


    private static boolean is7zip(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtension7zip);
    }


    private static boolean isXzippedTar(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionTarXz);
    }


    private static boolean isLzippedTar(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionTarLzma);
    }


    private static boolean isXz(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionXz) && (!com.amaze.filemanager.filesystem.compressed.CompressedHelper.isXzippedTar(type));
    }


    private static boolean isLzma(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionLzma) && (!com.amaze.filemanager.filesystem.compressed.CompressedHelper.isLzippedTar(type));
    }


    private static boolean isGzip(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionGz) && (!com.amaze.filemanager.filesystem.compressed.CompressedHelper.isGzippedTar(type));
    }


    private static boolean isBzip2(java.lang.String type) {
        return type.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionBzip2) && (!com.amaze.filemanager.filesystem.compressed.CompressedHelper.isBzippedTar(type));
    }


    private static java.lang.String getExtension(java.lang.String path) {
        switch(MUID_STATIC) {
            // CompressedHelper_0_BinaryMutator
            case 34: {
                return path.substring(path.indexOf('.') - 1).toLowerCase();
            }
            default: {
            return path.substring(path.indexOf('.') + 1).toLowerCase();
            }
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
