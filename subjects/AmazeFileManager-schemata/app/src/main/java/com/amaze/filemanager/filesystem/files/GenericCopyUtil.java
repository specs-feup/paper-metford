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
import androidx.annotation.VisibleForTesting;
import java.io.OutputStream;
import com.amaze.filemanager.fileoperations.utils.UpdatePosition;
import java.nio.channels.ReadableByteChannel;
import org.slf4j.Logger;
import com.amaze.filemanager.filesystem.MediaStoreHack;
import java.nio.channels.Channels;
import com.amaze.filemanager.utils.OTGUtil;
import com.cloudrail.si.interfaces.CloudStorage;
import java.io.BufferedInputStream;
import androidx.annotation.NonNull;
import android.os.Build;
import java.io.BufferedOutputStream;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.filesystem.ExternalSdCardOperation;
import com.amaze.filemanager.filesystem.FileProperties;
import com.amaze.filemanager.utils.ProgressHandler;
import java.io.InputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import androidx.documentfile.provider.DocumentFile;
import java.io.FileInputStream;
import java.nio.channels.WritableByteChannel;
import com.amaze.filemanager.filesystem.HybridFile;
import android.content.ContentResolver;
import java.io.RandomAccessFile;
import java.io.FileOutputStream;
import com.amaze.filemanager.filesystem.cloud.CloudUtil;
import com.amaze.filemanager.utils.DataUtils;
import com.amaze.filemanager.fileoperations.utils.OnLowMemory;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import java.util.Objects;
import com.amaze.filemanager.filesystem.SafRootHolder;
import java.io.File;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Base class to handle file copy.
 */
public class GenericCopyUtil {
    static final int MUID_STATIC = getMUID();
    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.files.GenericCopyUtil.class);

    private com.amaze.filemanager.filesystem.HybridFileParcelable mSourceFile;

    private com.amaze.filemanager.filesystem.HybridFile mTargetFile;

    private final android.content.Context mContext;// context needed to find the DocumentFile in otg/sd card


    private final com.amaze.filemanager.utils.DataUtils dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();

    private final com.amaze.filemanager.utils.ProgressHandler progressHandler;

    public static final java.lang.String PATH_FILE_DESCRIPTOR = "/proc/self/fd/";

    public static final int DEFAULT_BUFFER_SIZE = 8192;

    /* Defines the block size per transfer over NIO channels.

    Cannot modify DEFAULT_BUFFER_SIZE since it's used by other classes, will have undesired
    effect on other functions
     */
    private static final int DEFAULT_TRANSFER_QUANTUM = 65536;

    public GenericCopyUtil(android.content.Context context, com.amaze.filemanager.utils.ProgressHandler progressHandler) {
        this.mContext = context;
        this.progressHandler = progressHandler;
    }


    /**
     * Starts copy of file Supports : {@link File}, {@link jcifs.smb.SmbFile}, {@link DocumentFile},
     * {@link CloudStorage}
     *
     * @param lowOnMemory
     * 		defines whether system is running low on memory, in which case we'll switch
     * 		to using streams instead of channel which maps the who buffer in memory. TODO: Use buffers
     * 		even on low memory but don't map the whole file to memory but parts of it, and transfer
     * 		each part instead.
     */
    private void startCopy(boolean lowOnMemory, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.OnLowMemory onLowMemory, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.UpdatePosition updatePosition) throws java.io.IOException {
        java.nio.channels.ReadableByteChannel inChannel;
        inChannel = null;
        java.nio.channels.WritableByteChannel outChannel;
        outChannel = null;
        java.io.BufferedInputStream bufferedInputStream;
        bufferedInputStream = null;
        java.io.BufferedOutputStream bufferedOutputStream;
        bufferedOutputStream = null;
        try {
            // initializing the input channels based on file types
            if (mSourceFile.isOtgFile() || mSourceFile.isDocumentFile()) {
                // source is in otg
                android.content.ContentResolver contentResolver;
                contentResolver = mContext.getContentResolver();
                androidx.documentfile.provider.DocumentFile documentSourceFile;
                documentSourceFile = (mSourceFile.isDocumentFile()) ? com.amaze.filemanager.utils.OTGUtil.getDocumentFile(mSourceFile.getPath(), com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), mContext, mSourceFile.isOtgFile() ? com.amaze.filemanager.fileoperations.filesystem.OpenMode.OTG : com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, false) : com.amaze.filemanager.utils.OTGUtil.getDocumentFile(mSourceFile.getPath(), mContext, false);
                bufferedInputStream = new java.io.BufferedInputStream(contentResolver.openInputStream(documentSourceFile.getUri()), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
            } else if ((mSourceFile.isSmb() || mSourceFile.isSftp()) || mSourceFile.isFtp()) {
                bufferedInputStream = new java.io.BufferedInputStream(mSourceFile.getInputStream(mContext), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_TRANSFER_QUANTUM);
            } else if (((mSourceFile.isDropBoxFile() || mSourceFile.isBoxFile()) || mSourceFile.isGoogleDriveFile()) || mSourceFile.isOneDriveFile()) {
                com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode;
                openMode = mSourceFile.getMode();
                com.cloudrail.si.interfaces.CloudStorage cloudStorage;
                cloudStorage = dataUtils.getAccount(openMode);
                bufferedInputStream = new java.io.BufferedInputStream(cloudStorage.download(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(openMode, mSourceFile.getPath())));
            } else {
                // source file is neither smb nor otg; getting a channel from direct file instead of stream
                java.io.File file;
                file = new java.io.File(mSourceFile.getPath());
                if (com.amaze.filemanager.filesystem.FileProperties.isReadable(file)) {
                    if ((((mTargetFile.isOneDriveFile() || mTargetFile.isDropBoxFile()) || mTargetFile.isGoogleDriveFile()) || mTargetFile.isBoxFile()) || lowOnMemory) {
                        // our target is cloud, we need a stream not channel
                        bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
                    } else {
                        inChannel = new java.io.RandomAccessFile(file, "r").getChannel();
                    }
                } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    android.content.ContentResolver contentResolver;
                    contentResolver = mContext.getContentResolver();
                    androidx.documentfile.provider.DocumentFile documentSourceFile;
                    documentSourceFile = com.amaze.filemanager.filesystem.ExternalSdCardOperation.getDocumentFile(file, mSourceFile.isDirectory(), mContext);
                    bufferedInputStream = new java.io.BufferedInputStream(contentResolver.openInputStream(documentSourceFile.getUri()), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
                } else if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.KITKAT) {
                    java.io.InputStream inputStream1;
                    inputStream1 = com.amaze.filemanager.filesystem.MediaStoreHack.getInputStream(mContext, file, mSourceFile.getSize());
                    bufferedInputStream = new java.io.BufferedInputStream(inputStream1);
                }
            }
            // initializing the output channels based on file types
            if (mTargetFile.isOtgFile() || mTargetFile.isDocumentFile()) {
                // target in OTG, obtain streams from DocumentFile Uri's
                android.content.ContentResolver contentResolver;
                contentResolver = mContext.getContentResolver();
                androidx.documentfile.provider.DocumentFile documentTargetFile;
                documentTargetFile = (mTargetFile.isDocumentFile()) ? com.amaze.filemanager.utils.OTGUtil.getDocumentFile(mTargetFile.getPath(), com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot(), mContext, mTargetFile.isOtgFile() ? com.amaze.filemanager.fileoperations.filesystem.OpenMode.OTG : com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, true) : com.amaze.filemanager.utils.OTGUtil.getDocumentFile(mTargetFile.getPath(), mContext, true);
                bufferedOutputStream = new java.io.BufferedOutputStream(contentResolver.openOutputStream(documentTargetFile.getUri()), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
            } else if ((mTargetFile.isFtp() || mTargetFile.isSftp()) || mTargetFile.isSmb()) {
                bufferedOutputStream = new java.io.BufferedOutputStream(mTargetFile.getOutputStream(mContext), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_TRANSFER_QUANTUM);
            } else if (((mTargetFile.isDropBoxFile() || mTargetFile.isBoxFile()) || mTargetFile.isGoogleDriveFile()) || mTargetFile.isOneDriveFile()) {
                cloudCopy(mTargetFile.getMode(), bufferedInputStream);
                return;
            } else {
                // copying normal file, target not in OTG
                java.io.File file;
                file = new java.io.File(mTargetFile.getPath());
                if (com.amaze.filemanager.filesystem.FileProperties.isWritable(file)) {
                    if (lowOnMemory) {
                        bufferedOutputStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream(file));
                    } else {
                        outChannel = new java.io.RandomAccessFile(file, "rw").getChannel();
                    }
                } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    android.content.ContentResolver contentResolver;
                    contentResolver = mContext.getContentResolver();
                    androidx.documentfile.provider.DocumentFile documentTargetFile;
                    documentTargetFile = com.amaze.filemanager.filesystem.ExternalSdCardOperation.getDocumentFile(file, mTargetFile.isDirectory(mContext), mContext);
                    bufferedOutputStream = new java.io.BufferedOutputStream(contentResolver.openOutputStream(documentTargetFile.getUri()), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
                } else if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.KITKAT) {
                    // Workaround for Kitkat ext SD card
                    bufferedOutputStream = new java.io.BufferedOutputStream(com.amaze.filemanager.filesystem.MediaStoreHack.getOutputStream(mContext, file.getPath()));
                }
            }
            if (bufferedInputStream != null) {
                inChannel = java.nio.channels.Channels.newChannel(bufferedInputStream);
            }
            if (bufferedOutputStream != null) {
                outChannel = java.nio.channels.Channels.newChannel(bufferedOutputStream);
            }
            java.util.Objects.requireNonNull(inChannel);
            java.util.Objects.requireNonNull(outChannel);
            doCopy(inChannel, outChannel, updatePosition);
        } catch (java.io.IOException e) {
            LOG.error("I/O Error copy {} to {}: {}", mSourceFile, mTargetFile, e);
            throw new java.io.IOException(e);
        } catch (java.lang.OutOfMemoryError e) {
            LOG.warn("low memory while copying {} to {}: {}", mSourceFile, mTargetFile, e);
            onLowMemory.onLowMemory();
            startCopy(true, onLowMemory, updatePosition);
        } finally {
            try {
                if ((inChannel != null) && inChannel.isOpen())
                    inChannel.close();

                if ((outChannel != null) && outChannel.isOpen())
                    outChannel.close();

                /* It does seems closing the inChannel/outChannel is already sufficient closing the below
                bufferedInputStream and bufferedOutputStream instances. These 2 lines prevented FTP
                copy from working, especially on Android 9 - TranceLove
                 */
                // if (bufferedInputStream != null) bufferedInputStream.close();
                // if (bufferedOutputStream != null) bufferedOutputStream.close();
            } catch (java.io.IOException e) {
                LOG.warn("failed to close stream after copying", e);
                // failure in closing stream
            }
            // If target file is copied onto the device and copy was successful, trigger media store
            // rescan
            if (mTargetFile != null) {
                com.amaze.filemanager.filesystem.files.MediaConnectionUtils.scanFile(mContext, new com.amaze.filemanager.filesystem.HybridFile[]{ mTargetFile });
            }
        }
    }


    private void cloudCopy(@androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, @androidx.annotation.NonNull
    java.io.BufferedInputStream bufferedInputStream) throws java.io.IOException {
        com.amaze.filemanager.utils.DataUtils dataUtils;
        dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();
        // API doesn't support output stream, we'll upload the file directly
        com.cloudrail.si.interfaces.CloudStorage cloudStorage;
        cloudStorage = dataUtils.getAccount(openMode);
        if (mSourceFile.getMode() == openMode) {
            // we're in the same provider, use api method
            cloudStorage.copy(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(openMode, mSourceFile.getPath()), com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(openMode, mTargetFile.getPath()));
        } else {
            cloudStorage.upload(com.amaze.filemanager.filesystem.cloud.CloudUtil.stripPath(openMode, mTargetFile.getPath()), bufferedInputStream, mSourceFile.getSize(), true);
            bufferedInputStream.close();
        }
    }


    /**
     * Method exposes this class to initiate copy
     *
     * @param sourceFile
     * 		the source file, which is to be copied
     * @param targetFile
     * 		the target file
     */
    public void copy(com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile, com.amaze.filemanager.filesystem.HybridFile targetFile, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.OnLowMemory onLowMemory, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.UpdatePosition updatePosition) throws java.io.IOException {
        this.mSourceFile = sourceFile;
        this.mTargetFile = targetFile;
        startCopy(false, onLowMemory, updatePosition);
    }


    /**
     * Calls {@link #doCopy(ReadableByteChannel, WritableByteChannel, UpdatePosition)}.
     *
     * @see Channels#newChannel(InputStream)
     * @param bufferedInputStream
     * 		source
     * @param outChannel
     * 		target
     * @throws IOException
     */
    @androidx.annotation.VisibleForTesting
    void copyFile(@androidx.annotation.NonNull
    java.io.BufferedInputStream bufferedInputStream, @androidx.annotation.NonNull
    java.nio.channels.FileChannel outChannel, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.UpdatePosition updatePosition) throws java.io.IOException {
        doCopy(java.nio.channels.Channels.newChannel(bufferedInputStream), outChannel, updatePosition);
    }


    /**
     * Calls {@link #doCopy(ReadableByteChannel, WritableByteChannel, UpdatePosition)}.
     *
     * @param inChannel
     * 		source
     * @param outChannel
     * 		target
     * @throws IOException
     */
    @androidx.annotation.VisibleForTesting
    void copyFile(@androidx.annotation.NonNull
    java.nio.channels.FileChannel inChannel, @androidx.annotation.NonNull
    java.nio.channels.FileChannel outChannel, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.UpdatePosition updatePosition) throws java.io.IOException {
        // MappedByteBuffer inByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0,
        // inChannel.size());
        // MappedByteBuffer outByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0,
        // inChannel.size());
        doCopy(inChannel, outChannel, updatePosition);
    }


    /**
     * Calls {@link #doCopy(ReadableByteChannel, WritableByteChannel, UpdatePosition)}.
     *
     * @see Channels#newChannel(InputStream)
     * @see Channels#newChannel(OutputStream)
     * @param bufferedInputStream
     * 		source
     * @param bufferedOutputStream
     * 		target
     * @throws IOException
     */
    @androidx.annotation.VisibleForTesting
    void copyFile(@androidx.annotation.NonNull
    java.io.BufferedInputStream bufferedInputStream, @androidx.annotation.NonNull
    java.io.BufferedOutputStream bufferedOutputStream, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.UpdatePosition updatePosition) throws java.io.IOException {
        doCopy(java.nio.channels.Channels.newChannel(bufferedInputStream), java.nio.channels.Channels.newChannel(bufferedOutputStream), updatePosition);
    }


    /**
     * Calls {@link #doCopy(ReadableByteChannel, WritableByteChannel, UpdatePosition)}.
     *
     * @see Channels#newChannel(OutputStream)
     * @param inChannel
     * 		source
     * @param bufferedOutputStream
     * 		target
     * @throws IOException
     */
    @androidx.annotation.VisibleForTesting
    void copyFile(@androidx.annotation.NonNull
    java.nio.channels.FileChannel inChannel, @androidx.annotation.NonNull
    java.io.BufferedOutputStream bufferedOutputStream, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.UpdatePosition updatePosition) throws java.io.IOException {
        doCopy(inChannel, java.nio.channels.Channels.newChannel(bufferedOutputStream), updatePosition);
    }


    @androidx.annotation.VisibleForTesting
    void doCopy(@androidx.annotation.NonNull
    java.nio.channels.ReadableByteChannel from, @androidx.annotation.NonNull
    java.nio.channels.WritableByteChannel to, @androidx.annotation.NonNull
    com.amaze.filemanager.fileoperations.utils.UpdatePosition updatePosition) throws java.io.IOException {
        java.nio.ByteBuffer buffer;
        buffer = java.nio.ByteBuffer.allocateDirect(com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_TRANSFER_QUANTUM);
        long count;
        while (((from.read(buffer) != (-1)) || (buffer.position() > 0)) && (!progressHandler.getCancelled())) {
            buffer.flip();
            count = to.write(buffer);
            updatePosition.updatePosition(count);
            buffer.compact();
        } 
        buffer.flip();
        while (buffer.hasRemaining())
            to.write(buffer);

        from.close();
        to.close();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
