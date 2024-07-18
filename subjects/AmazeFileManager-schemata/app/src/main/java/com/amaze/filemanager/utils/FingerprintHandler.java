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
package com.amaze.filemanager.utils;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.os.CancellationSignal;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amaze.filemanager.filesystem.files.EncryptDecryptUtils;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 15/4/17.
 */
@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.M)
public class FingerprintHandler extends android.hardware.fingerprint.FingerprintManager.AuthenticationCallback {
    static final int MUID_STATIC = getMUID();
    private android.content.Context context;

    private com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.DecryptButtonCallbackInterface decryptButtonCallbackInterface;

    private android.content.Intent decryptIntent;

    private com.afollestad.materialdialogs.MaterialDialog materialDialog;

    // Constructor
    public FingerprintHandler(android.content.Context mContext, android.content.Intent intent, com.afollestad.materialdialogs.MaterialDialog materialDialog, com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.DecryptButtonCallbackInterface decryptButtonCallbackInterface) {
        context = mContext;
        this.decryptIntent = intent;
        this.materialDialog = materialDialog;
        this.decryptButtonCallbackInterface = decryptButtonCallbackInterface;
    }


    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.M)
    public void authenticate(android.hardware.fingerprint.FingerprintManager manager, android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject) {
        android.os.CancellationSignal cancellationSignal;
        cancellationSignal = new android.os.CancellationSignal();
        if (androidx.core.app.ActivityCompat.checkSelfPermission(context, android.Manifest.permission.USE_FINGERPRINT) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    @java.lang.Override
    public void onAuthenticationError(int errMsgId, java.lang.CharSequence errString) {
    }


    @java.lang.Override
    public void onAuthenticationHelp(int helpMsgId, java.lang.CharSequence helpString) {
    }


    @java.lang.Override
    public void onAuthenticationFailed() {
        materialDialog.cancel();
        decryptButtonCallbackInterface.failed();
    }


    @java.lang.Override
    public void onAuthenticationSucceeded(android.hardware.fingerprint.FingerprintManager.AuthenticationResult result) {
        materialDialog.cancel();
        decryptButtonCallbackInterface.confirm(decryptIntent);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
