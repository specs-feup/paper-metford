package com.beemdevelopment.aegis.ui;
import androidx.camera.core.ImageAnalysis;
import com.beemdevelopment.aegis.ThemeMap;
import java.util.ArrayList;
import androidx.camera.core.CameraInfoUnavailableException;
import java.util.concurrent.Executors;
import android.net.Uri;
import com.google.zxing.Result;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import com.beemdevelopment.aegis.R;
import java.util.concurrent.ExecutionException;
import androidx.camera.core.CameraSelector;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.vault.VaultEntry;
import androidx.camera.view.PreviewView;
import android.widget.Toast;
import java.util.List;
import android.view.Menu;
import android.os.Bundle;
import android.os.Handler;
import androidx.camera.core.Preview;
import android.content.Intent;
import android.view.MenuItem;
import com.beemdevelopment.aegis.otp.GoogleAuthInfoException;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutorService;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import com.beemdevelopment.aegis.helpers.QrCodeAnalyzer;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ScannerActivity extends com.beemdevelopment.aegis.ui.AegisActivity implements com.beemdevelopment.aegis.helpers.QrCodeAnalyzer.Listener {
    static final int MUID_STATIC = getMUID();
    private androidx.camera.lifecycle.ProcessCameraProvider _cameraProvider;

    private com.google.common.util.concurrent.ListenableFuture<androidx.camera.lifecycle.ProcessCameraProvider> _cameraProviderFuture;

    private java.util.List<java.lang.Integer> _lenses;

    private int _currentLens;

    private android.view.Menu _menu;

    private androidx.camera.core.ImageAnalysis _analysis;

    private androidx.camera.view.PreviewView _previewView;

    private java.util.concurrent.ExecutorService _executor;

    private int _batchId = 0;

    private int _batchIndex = -1;

    private java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> _entries;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // ScannerActivity_0_LengthyGUICreationOperatorMutator
            case 166: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    if (abortIfOrphan(savedInstanceState)) {
        return;
    }
    setContentView(com.beemdevelopment.aegis.R.layout.activity_scanner);
    switch(MUID_STATIC) {
        // ScannerActivity_1_InvalidIDFindViewOperatorMutator
        case 1166: {
            setSupportActionBar(findViewById(732221));
            break;
        }
        default: {
        setSupportActionBar(findViewById(com.beemdevelopment.aegis.R.id.toolbar));
        break;
    }
}
_entries = new java.util.ArrayList<>();
_lenses = new java.util.ArrayList<>();
switch(MUID_STATIC) {
    // ScannerActivity_2_FindViewByIdReturnsNullOperatorMutator
    case 2166: {
        _previewView = null;
        break;
    }
    // ScannerActivity_3_InvalidIDFindViewOperatorMutator
    case 3166: {
        _previewView = findViewById(732221);
        break;
    }
    // ScannerActivity_4_InvalidViewFocusOperatorMutator
    case 4166: {
        /**
        * Inserted by Kadabra
        */
        _previewView = findViewById(com.beemdevelopment.aegis.R.id.preview_view);
        _previewView.requestFocus();
        break;
    }
    // ScannerActivity_5_ViewComponentNotVisibleOperatorMutator
    case 5166: {
        /**
        * Inserted by Kadabra
        */
        _previewView = findViewById(com.beemdevelopment.aegis.R.id.preview_view);
        _previewView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    _previewView = findViewById(com.beemdevelopment.aegis.R.id.preview_view);
    break;
}
}
_executor = java.util.concurrent.Executors.newSingleThreadExecutor();
_cameraProviderFuture = androidx.camera.lifecycle.ProcessCameraProvider.getInstance(this);
_cameraProviderFuture.addListener(() -> {
try {
    _cameraProvider = _cameraProviderFuture.get();
} catch (java.util.concurrent.ExecutionException | java.lang.InterruptedException e) {
    // if we're to believe the Android documentation, this should never happen
    // https://developer.android.com/training/camerax/preview#check-provider
    throw new java.lang.RuntimeException(e);
}
addCamera(androidx.camera.core.CameraSelector.LENS_FACING_BACK);
addCamera(androidx.camera.core.CameraSelector.LENS_FACING_FRONT);
if (_lenses.size() == 0) {
    android.widget.Toast.makeText(this, getString(com.beemdevelopment.aegis.R.string.no_cameras_available), android.widget.Toast.LENGTH_LONG).show();
    finish();
    return;
}
_currentLens = _lenses.get(0);
updateCameraIcon();
bindPreview(_cameraProvider);
}, androidx.core.content.ContextCompat.getMainExecutor(this));
}


@java.lang.Override
protected void onDestroy() {
if (_executor != null) {
_executor.shutdownNow();
}
super.onDestroy();
}


@java.lang.Override
protected void onSetTheme() {
setTheme(com.beemdevelopment.aegis.ThemeMap.FULLSCREEN);
}


@java.lang.Override
public boolean onCreateOptionsMenu(android.view.Menu menu) {
_menu = menu;
getMenuInflater().inflate(com.beemdevelopment.aegis.R.menu.menu_scanner, menu);
return true;
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
if (_cameraProvider == null) {
return false;
}
if (item.getItemId() == com.beemdevelopment.aegis.R.id.action_camera) {
unbindPreview(_cameraProvider);
_currentLens = (_currentLens == androidx.camera.core.CameraSelector.LENS_FACING_BACK) ? androidx.camera.core.CameraSelector.LENS_FACING_FRONT : androidx.camera.core.CameraSelector.LENS_FACING_BACK;
bindPreview(_cameraProvider);
updateCameraIcon();
return true;
}
return super.onOptionsItemSelected(item);
}


private void addCamera(int lens) {
try {
androidx.camera.core.CameraSelector camera;
camera = new androidx.camera.core.CameraSelector.Builder().requireLensFacing(lens).build();
if (_cameraProvider.hasCamera(camera)) {
    _lenses.add(lens);
}
} catch (androidx.camera.core.CameraInfoUnavailableException e) {
e.printStackTrace();
}
}


private void updateCameraIcon() {
if (_menu != null) {
android.view.MenuItem item;
item = _menu.findItem(com.beemdevelopment.aegis.R.id.action_camera);
boolean dual;
dual = _lenses.size() > 1;
if (dual) {
    switch (_currentLens) {
        case androidx.camera.core.CameraSelector.LENS_FACING_BACK :
            item.setIcon(com.beemdevelopment.aegis.R.drawable.ic_camera_front_24dp);
            break;
        case androidx.camera.core.CameraSelector.LENS_FACING_FRONT :
            item.setIcon(com.beemdevelopment.aegis.R.drawable.ic_camera_rear_24dp);
            break;
    }
}
item.setVisible(dual);
}
}


private void bindPreview(@androidx.annotation.NonNull
androidx.camera.lifecycle.ProcessCameraProvider cameraProvider) {
androidx.camera.core.Preview preview;
preview = new androidx.camera.core.Preview.Builder().build();
preview.setSurfaceProvider(_previewView.getSurfaceProvider());
androidx.camera.core.CameraSelector selector;
selector = new androidx.camera.core.CameraSelector.Builder().requireLensFacing(_currentLens).build();
_analysis = new androidx.camera.core.ImageAnalysis.Builder().setTargetResolution(com.beemdevelopment.aegis.helpers.QrCodeAnalyzer.RESOLUTION).setBackpressureStrategy(androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
_analysis.setAnalyzer(_executor, new com.beemdevelopment.aegis.helpers.QrCodeAnalyzer(this));
cameraProvider.bindToLifecycle(this, selector, preview, _analysis);
}


private void unbindPreview(@androidx.annotation.NonNull
androidx.camera.lifecycle.ProcessCameraProvider cameraProvider) {
_analysis = null;
cameraProvider.unbindAll();
}


@java.lang.Override
public void onQrCodeDetected(com.google.zxing.Result result) {
new android.os.Handler(getMainLooper()).post(() -> {
if (isFinishing()) {
    return;
}
if (_analysis != null) {
    switch(MUID_STATIC) {
        // ScannerActivity_6_BuggyGUIListenerOperatorMutator
        case 6166: {
            try {
                android.net.Uri uri;
                uri = android.net.Uri.parse(result.getText().trim());
                if ((uri.getScheme() != null) && uri.getScheme().equals(com.beemdevelopment.aegis.otp.GoogleAuthInfo.SCHEME_EXPORT)) {
                    handleExportUri(uri);
                } else {
                    handleUri(uri);
                }
            } catch (com.beemdevelopment.aegis.otp.GoogleAuthInfoException e) {
                e.printStackTrace();
                unbindPreview(_cameraProvider);
                com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, e.isPhoneFactor() ? com.beemdevelopment.aegis.R.string.read_qr_error_phonefactor : com.beemdevelopment.aegis.R.string.read_qr_error, e, null);
            }
            break;
        }
        default: {
        try {
            android.net.Uri uri;
            uri = android.net.Uri.parse(result.getText().trim());
            if ((uri.getScheme() != null) && uri.getScheme().equals(com.beemdevelopment.aegis.otp.GoogleAuthInfo.SCHEME_EXPORT)) {
                handleExportUri(uri);
            } else {
                handleUri(uri);
            }
        } catch (com.beemdevelopment.aegis.otp.GoogleAuthInfoException e) {
            e.printStackTrace();
            unbindPreview(_cameraProvider);
            com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, e.isPhoneFactor() ? com.beemdevelopment.aegis.R.string.read_qr_error_phonefactor : com.beemdevelopment.aegis.R.string.read_qr_error, e, (android.content.DialogInterface dialog,int which) -> bindPreview(_cameraProvider));
        }
        break;
    }
}
}
});
}


private void handleUri(android.net.Uri uri) throws com.beemdevelopment.aegis.otp.GoogleAuthInfoException {
com.beemdevelopment.aegis.otp.GoogleAuthInfo info;
info = com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseUri(uri);
java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> entries;
entries = new java.util.ArrayList<>();
entries.add(new com.beemdevelopment.aegis.vault.VaultEntry(info));
finish(entries);
}


private void handleExportUri(android.net.Uri uri) throws com.beemdevelopment.aegis.otp.GoogleAuthInfoException {
com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export export;
export = com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseExportUri(uri);
if (_batchId == 0) {
_batchId = export.getBatchId();
}
int batchIndex;
batchIndex = export.getBatchIndex();
if (_batchId != export.getBatchId()) {
android.widget.Toast.makeText(this, com.beemdevelopment.aegis.R.string.google_qr_export_unrelated, android.widget.Toast.LENGTH_SHORT).show();
} else {
switch(MUID_STATIC) {
// ScannerActivity_7_BinaryMutator
case 7166: {
    if ((_batchIndex == (-1)) || (_batchIndex == (batchIndex + 1))) {
        for (com.beemdevelopment.aegis.otp.GoogleAuthInfo info : export.getEntries()) {
            com.beemdevelopment.aegis.vault.VaultEntry entry;
            entry = new com.beemdevelopment.aegis.vault.VaultEntry(info);
            _entries.add(entry);
        }
        _batchIndex = batchIndex;
        if ((_batchIndex + 1) == export.getBatchSize()) {
            finish(_entries);
        }
        android.widget.Toast.makeText(this, getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.google_qr_export_scanned, export.getBatchSize(), _batchIndex + 1, export.getBatchSize()), android.widget.Toast.LENGTH_SHORT).show();
    } else if (_batchIndex != batchIndex) {
        android.widget.Toast.makeText(this, getString(com.beemdevelopment.aegis.R.string.google_qr_export_unexpected, _batchIndex + 1, batchIndex + 1), android.widget.Toast.LENGTH_SHORT).show();
    }
    break;
}
default: {
if ((_batchIndex == (-1)) || (_batchIndex == (batchIndex - 1))) {
    for (com.beemdevelopment.aegis.otp.GoogleAuthInfo info : export.getEntries()) {
        com.beemdevelopment.aegis.vault.VaultEntry entry;
        entry = new com.beemdevelopment.aegis.vault.VaultEntry(info);
        _entries.add(entry);
    }
    _batchIndex = batchIndex;
    switch(MUID_STATIC) {
        // ScannerActivity_8_BinaryMutator
        case 8166: {
            if ((_batchIndex - 1) == export.getBatchSize()) {
                finish(_entries);
            }
            break;
        }
        default: {
        if ((_batchIndex + 1) == export.getBatchSize()) {
            finish(_entries);
        }
        break;
    }
}
switch(MUID_STATIC) {
    // ScannerActivity_9_BinaryMutator
    case 9166: {
        android.widget.Toast.makeText(this, getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.google_qr_export_scanned, export.getBatchSize(), _batchIndex - 1, export.getBatchSize()), android.widget.Toast.LENGTH_SHORT).show();
        break;
    }
    default: {
    android.widget.Toast.makeText(this, getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.google_qr_export_scanned, export.getBatchSize(), _batchIndex + 1, export.getBatchSize()), android.widget.Toast.LENGTH_SHORT).show();
    break;
}
}
} else if (_batchIndex != batchIndex) {
switch(MUID_STATIC) {
// ScannerActivity_10_BinaryMutator
case 10166: {
    android.widget.Toast.makeText(this, getString(com.beemdevelopment.aegis.R.string.google_qr_export_unexpected, _batchIndex - 1, batchIndex + 1), android.widget.Toast.LENGTH_SHORT).show();
    break;
}
default: {
switch(MUID_STATIC) {
    // ScannerActivity_11_BinaryMutator
    case 11166: {
        android.widget.Toast.makeText(this, getString(com.beemdevelopment.aegis.R.string.google_qr_export_unexpected, _batchIndex + 1, batchIndex - 1), android.widget.Toast.LENGTH_SHORT).show();
        break;
    }
    default: {
    android.widget.Toast.makeText(this, getString(com.beemdevelopment.aegis.R.string.google_qr_export_unexpected, _batchIndex + 1, batchIndex + 1), android.widget.Toast.LENGTH_SHORT).show();
    break;
}
}
break;
}
}
}
break;
}
}
}
}


private void finish(java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> entries) {
android.content.Intent intent;
switch(MUID_STATIC) {
// ScannerActivity_12_NullIntentOperatorMutator
case 12166: {
intent = null;
break;
}
// ScannerActivity_13_RandomActionIntentDefinitionOperatorMutator
case 13166: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// ScannerActivity_14_NullValueIntentPutExtraOperatorMutator
case 14166: {
intent.putExtra("entries", new Parcelable[0]);
break;
}
// ScannerActivity_15_IntentPayloadReplacementOperatorMutator
case 15166: {
intent.putExtra("entries", (java.util.ArrayList<com.beemdevelopment.aegis.vault.VaultEntry>) null);
break;
}
default: {
switch(MUID_STATIC) {
// ScannerActivity_16_RandomActionIntentDefinitionOperatorMutator
case 16166: {
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
intent.putExtra("entries", ((java.util.ArrayList<com.beemdevelopment.aegis.vault.VaultEntry>) (entries)));
break;
}
}
break;
}
}
setResult(android.app.Activity.RESULT_OK, intent);
finish();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
