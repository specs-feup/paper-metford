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
import java.security.Key;
import com.amaze.filemanager.utils.AESCrypt;
import javax.crypto.CipherOutputStream;
import java.util.ArrayList;
import javax.crypto.spec.GCMParameterSpec;
import org.slf4j.Logger;
import kotlin.io.ByteStreamsKt;
import static android.os.Build.VERSION.SDK_INT;
import java.io.BufferedInputStream;
import androidx.annotation.NonNull;
import com.amaze.filemanager.BuildConfig;
import java.io.BufferedOutputStream;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil;
import com.amaze.filemanager.utils.security.SecretKeygen;
import com.amaze.filemanager.application.AppConfig;
import com.amaze.filemanager.utils.ProgressHandler;
import kotlin.io.ConstantsKt;
import java.io.IOException;
import java.security.GeneralSecurityException;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import com.amaze.filemanager.filesystem.MakeDirectoryOperation;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import static android.os.Build.VERSION_CODES.KITKAT;
import com.amaze.filemanager.filesystem.HybridFile;
import javax.crypto.spec.IvParameterSpec;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
import javax.crypto.Cipher;
import androidx.annotation.Nullable;
import java.security.spec.AlgorithmParameterSpec;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 6/4/17.
 *
 * <p>Class provide helper methods to encrypt/decrypt various type of files, or passwords We take
 * the password from user before encrypting file. First, the password is encrypted against the key
 * created in keystore in android {@see #encryptPassword(String)}. We're using AES encryption with
 * GCM as the processor algorithm. The encrypted password is mapped against the file path to be
 * encrypted in database for later use. This is handled by the service invoking this instance. The
 * service then calls the constructor which fires up the subsequent encryption/decryption process.
 *
 * <p>We differentiate between already encrypted files from <i>new ones</i> by encrypting the
 * plaintext {@link PreferencesConstants#ENCRYPT_PASSWORD_MASTER} and {@link PreferencesConstants#ENCRYPT_PASSWORD_FINGERPRINT} against the path in database. At the time of
 * decryption, we check for these values and either retrieve master password from preferences or
 * fire up the fingerprint sensor authentication.
 *
 * <p>From <i>new ones</i> we mean the ones when were encrypted after user changed preference for
 * master password/fingerprint sensor from settings.
 *
 * <p>We use buffered streams to process files, usage of NIO will probably mildly effect the
 * performance.
 *
 * <p>Be sure to use constructors to encrypt/decrypt files only, and to call service through {@link ServiceWatcherUtil} and to initialize watchers beforehand
 */
public class CryptUtil {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String KEY_STORE_ANDROID = "AndroidKeyStore";

    public static final java.lang.String KEY_ALIAS_AMAZE = "AmazeKey";

    public static final java.lang.String ALGO_AES = "AES/GCM/NoPadding";

    // TODO: Generate a random IV every time, and keep track of it (in database against encrypted
    // files)
    private static final java.lang.String IV = com.amaze.filemanager.BuildConfig.CRYPTO_IV;// 12 byte long IV supported by android for GCM


    private static final int GCM_TAG_LENGTH = 128;

    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.files.CryptUtil.class);

    public static final java.lang.String CRYPT_EXTENSION = ".aze";

    public static final java.lang.String AESCRYPT_EXTENSION = ".aes";

    private final com.amaze.filemanager.utils.ProgressHandler progressHandler;

    private final java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile> failedOps;

    /**
     * Constructor will start encryption process serially. Make sure to call with background thread.
     * The result file of encryption will be in the same directory with a {@link #CRYPT_EXTENSION}
     * extension
     *
     * <p>Make sure you're done with encrypting password for this file and map it with this file in
     * database
     *
     * <p>Be sure to use constructors to encrypt/decrypt files only, and to call service through
     * {@link ServiceWatcherUtil} and to initialize watchers beforehand
     *
     * @param sourceFile
     * 		the file to encrypt
     */
    public CryptUtil(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile, @androidx.annotation.NonNull
    com.amaze.filemanager.utils.ProgressHandler progressHandler, @androidx.annotation.NonNull
    java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile> failedOps, @androidx.annotation.NonNull
    java.lang.String targetFilename, boolean useAesCrypt, @androidx.annotation.Nullable
    java.lang.String password) throws java.security.GeneralSecurityException, java.io.IOException {
        this.progressHandler = progressHandler;
        this.failedOps = failedOps;
        // target encrypted file
        com.amaze.filemanager.filesystem.HybridFile hFile;
        hFile = new com.amaze.filemanager.filesystem.HybridFile(sourceFile.getMode(), sourceFile.getParent(context));
        encrypt(context, sourceFile, hFile, targetFilename, useAesCrypt, password);
    }


    /**
     * Decrypt the file in specified path. Can be used to open the file (decrypt in cache) or simply
     * decrypt the file in the same (or in a custom preference) directory Make sure to decrypt and
     * check user provided passwords beforehand from database
     *
     * <p>Be sure to use constructors to encrypt/decrypt files only, and to call service through
     * {@link ServiceWatcherUtil} and to initialize watchers beforehand
     *
     * @param baseFile
     * 		the encrypted file
     * @param targetPath
     * 		the directory in which file is to be decrypted the source's parent in normal
     * 		case
     */
    public CryptUtil(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    com.amaze.filemanager.filesystem.HybridFileParcelable baseFile, @androidx.annotation.NonNull
    java.lang.String targetPath, @androidx.annotation.NonNull
    com.amaze.filemanager.utils.ProgressHandler progressHandler, @androidx.annotation.NonNull
    java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFile> failedOps, @androidx.annotation.Nullable
    java.lang.String password) throws java.security.GeneralSecurityException, java.io.IOException {
        this.progressHandler = progressHandler;
        this.failedOps = failedOps;
        boolean useAesCrypt;
        useAesCrypt = baseFile.getName().endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION);
        com.amaze.filemanager.filesystem.HybridFile targetDirectory;
        targetDirectory = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, targetPath);
        if (!targetPath.equals(context.getExternalCacheDir())) {
            // same file system as of base file
            targetDirectory.setMode(baseFile.getMode());
        }
        decrypt(context, baseFile, targetDirectory, useAesCrypt, password);
    }


    /**
     * Wrapper around handling decryption for directory tree
     *
     * @param sourceFile
     * 		the source file to decrypt
     * @param targetDirectory
     * 		the target directory inside which we're going to decrypt
     */
    private void decrypt(@androidx.annotation.NonNull
    final android.content.Context context, @androidx.annotation.NonNull
    com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile, @androidx.annotation.NonNull
    com.amaze.filemanager.filesystem.HybridFile targetDirectory, boolean useAescrypt, @androidx.annotation.Nullable
    java.lang.String password) throws java.security.GeneralSecurityException, java.io.IOException {
        if (progressHandler.getCancelled())
            return;

        if (sourceFile.isDirectory()) {
            final com.amaze.filemanager.filesystem.HybridFile hFile;
            hFile = new com.amaze.filemanager.filesystem.HybridFile(targetDirectory.getMode(), targetDirectory.getPath(), sourceFile.getName(context).replace(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION, "").replace(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION, ""), sourceFile.isDirectory());
            com.amaze.filemanager.filesystem.MakeDirectoryOperation.mkdirs(context, hFile);
            sourceFile.forEachChildrenFile(context, sourceFile.isRoot(), (com.amaze.filemanager.filesystem.HybridFileParcelable file) -> {
                try {
                    decrypt(context, file, hFile, useAescrypt, password);
                } catch (java.io.IOException | java.security.GeneralSecurityException e) {
                    throw new java.lang.IllegalStateException(e)// throw unchecked exception, no throws needed
                    ;// throw unchecked exception, no throws needed

                }
            });
        } else {
            if ((!sourceFile.getPath().endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION)) && (!sourceFile.getPath().endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION))) {
                failedOps.add(sourceFile);
                return;
            }
            java.io.BufferedInputStream inputStream;
            inputStream = new java.io.BufferedInputStream(sourceFile.getInputStream(context), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
            com.amaze.filemanager.filesystem.HybridFile targetFile;
            targetFile = new com.amaze.filemanager.filesystem.HybridFile(targetDirectory.getMode(), targetDirectory.getPath(), sourceFile.getName(context).replace(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION, "").replace(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION, ""), sourceFile.isDirectory());
            progressHandler.setFileName(sourceFile.getName(context));
            java.io.BufferedOutputStream outputStream;
            outputStream = new java.io.BufferedOutputStream(targetFile.getOutputStream(context), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
            if (useAescrypt) {
                new com.amaze.filemanager.utils.AESCrypt(password).decrypt(sourceFile.getSize(), inputStream, outputStream);
            } else {
                doEncrypt(inputStream, outputStream, javax.crypto.Cipher.DECRYPT_MODE);
            }
        }
    }


    /**
     * Wrapper around handling encryption in directory tree
     *
     * @param sourceFile
     * 		the source file to encrypt
     * @param targetDirectory
     * 		the target directory in which we're going to encrypt
     */
    private void encrypt(@androidx.annotation.NonNull
    final android.content.Context context, @androidx.annotation.NonNull
    com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile, @androidx.annotation.NonNull
    com.amaze.filemanager.filesystem.HybridFile targetDirectory, @androidx.annotation.NonNull
    java.lang.String targetFilename, boolean useAesCrypt, @androidx.annotation.Nullable
    java.lang.String password) throws java.security.GeneralSecurityException, java.io.IOException {
        if (progressHandler.getCancelled())
            return;

        if (sourceFile.isDirectory()) {
            // succeed #CRYPT_EXTENSION at end of directory/file name
            final com.amaze.filemanager.filesystem.HybridFile hFile;
            hFile = new com.amaze.filemanager.filesystem.HybridFile(targetDirectory.getMode(), targetDirectory.getPath(), targetFilename, sourceFile.isDirectory());
            com.amaze.filemanager.filesystem.MakeDirectoryOperation.mkdirs(context, hFile);
            sourceFile.forEachChildrenFile(context, sourceFile.isRoot(), (com.amaze.filemanager.filesystem.HybridFileParcelable file) -> {
                try {
                    encrypt(context, file, hFile, file.getName(context).concat(useAesCrypt ? com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION : com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION), useAesCrypt, password);
                } catch (java.io.IOException | java.security.GeneralSecurityException e) {
                    throw new java.lang.IllegalStateException(e)// throw unchecked exception, no throws needed
                    ;// throw unchecked exception, no throws needed

                }
            });
        } else {
            if (sourceFile.getName(context).endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION) || sourceFile.getName(context).endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION)) {
                failedOps.add(sourceFile);
                return;
            }
            java.io.BufferedInputStream inputStream;
            inputStream = new java.io.BufferedInputStream(sourceFile.getInputStream(context), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
            // succeed #CRYPT_EXTENSION at end of directory/file name
            com.amaze.filemanager.filesystem.HybridFile targetFile;
            targetFile = new com.amaze.filemanager.filesystem.HybridFile(targetDirectory.getMode(), targetDirectory.getPath(), targetFilename, sourceFile.isDirectory());
            progressHandler.setFileName(sourceFile.getName(context));
            java.io.BufferedOutputStream outputStream;
            outputStream = new java.io.BufferedOutputStream(targetFile.getOutputStream(context), com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE);
            if (useAesCrypt) {
                new com.amaze.filemanager.utils.AESCrypt(password).encrypt(com.amaze.filemanager.utils.AESCrypt.AESCRYPT_SPEC_VERSION, sourceFile.getInputStream(com.amaze.filemanager.application.AppConfig.getInstance()), targetFile.getOutputStream(com.amaze.filemanager.application.AppConfig.getInstance()), progressHandler);
            } else {
                doEncrypt(inputStream, outputStream, javax.crypto.Cipher.ENCRYPT_MODE);
            }
        }
    }


    /**
     * Core encryption/decryption routine.
     *
     * @param inputStream
     * 		stream associated with the file to be encrypted
     * @param outputStream
     * 		stream associated with new output encrypted file
     * @param operationMode
     * 		either <code>Cipher.ENCRYPT_MODE</code> or <code>Cipher.DECRYPT_MODE
     * 		</code>
     */
    private void doEncrypt(java.io.BufferedInputStream inputStream, java.io.BufferedOutputStream outputStream, int operationMode) throws java.security.GeneralSecurityException, java.io.IOException {
        javax.crypto.Cipher cipher;
        cipher = javax.crypto.Cipher.getInstance(com.amaze.filemanager.filesystem.files.CryptUtil.ALGO_AES);
        java.security.spec.AlgorithmParameterSpec parameterSpec;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            parameterSpec = new javax.crypto.spec.GCMParameterSpec(com.amaze.filemanager.filesystem.files.CryptUtil.GCM_TAG_LENGTH, com.amaze.filemanager.filesystem.files.CryptUtil.IV.getBytes());
        } else {
            parameterSpec = new javax.crypto.spec.IvParameterSpec(com.amaze.filemanager.filesystem.files.CryptUtil.IV.getBytes());
        }
        java.security.Key secretKey;
        secretKey = com.amaze.filemanager.utils.security.SecretKeygen.INSTANCE.getSecretKey();
        if (secretKey == null) {
            // Discard crypto setup objects and just pipe input to output
            parameterSpec = null;
            cipher = null;
            kotlin.io.ByteStreamsKt.copyTo(inputStream, outputStream, kotlin.io.ConstantsKt.DEFAULT_BUFFER_SIZE);
            inputStream.close();
            outputStream.close();
        } else {
            cipher.init(operationMode, com.amaze.filemanager.utils.security.SecretKeygen.INSTANCE.getSecretKey(), parameterSpec);
            byte[] buffer;
            buffer = new byte[com.amaze.filemanager.filesystem.files.GenericCopyUtil.DEFAULT_BUFFER_SIZE];
            int count;
            javax.crypto.CipherOutputStream cipherOutputStream;
            cipherOutputStream = new javax.crypto.CipherOutputStream(outputStream, cipher);
            try {
                while ((count = inputStream.read(buffer)) != (-1)) {
                    if (!progressHandler.getCancelled()) {
                        cipherOutputStream.write(buffer, 0, count);
                        com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.position += count;
                    } else
                        break;

                } 
            } catch (java.lang.Exception x) {
                LOG.error("I/O error writing output", x);
            } finally {
                cipherOutputStream.flush();
                cipherOutputStream.close();
                inputStream.close();
                outputStream.close();
            }
        }
    }


    /**
     * Method initializes a Cipher to be used by {@link android.hardware.fingerprint.FingerprintManager}
     */
    public static javax.crypto.Cipher initCipher() throws java.security.GeneralSecurityException {
        javax.crypto.Cipher cipher;
        cipher = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            cipher = javax.crypto.Cipher.getInstance(com.amaze.filemanager.filesystem.files.CryptUtil.ALGO_AES);
            javax.crypto.spec.GCMParameterSpec gcmParameterSpec;
            gcmParameterSpec = new javax.crypto.spec.GCMParameterSpec(com.amaze.filemanager.filesystem.files.CryptUtil.GCM_TAG_LENGTH, com.amaze.filemanager.filesystem.files.CryptUtil.IV.getBytes());
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, com.amaze.filemanager.utils.security.SecretKeygen.INSTANCE.getSecretKey(), gcmParameterSpec);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            cipher = javax.crypto.Cipher.getInstance(com.amaze.filemanager.filesystem.files.CryptUtil.ALGO_AES);
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, com.amaze.filemanager.utils.security.SecretKeygen.INSTANCE.getSecretKey());
        }
        return cipher;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
