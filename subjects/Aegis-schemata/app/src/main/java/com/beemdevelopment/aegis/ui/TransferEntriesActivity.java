package com.beemdevelopment.aegis.ui;
import com.beemdevelopment.aegis.helpers.QrCodeHelper;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.util.TypedValue;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import android.widget.Button;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import com.beemdevelopment.aegis.otp.Transferable;
import android.view.View;
import android.content.ClipboardManager;
import android.os.PersistableBundle;
import androidx.annotation.ColorInt;
import com.beemdevelopment.aegis.otp.GoogleAuthInfoException;
import com.beemdevelopment.aegis.Theme;
import android.content.ClipData;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import android.content.ClipDescription;
import android.content.Context;
import com.google.zxing.WriterException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TransferEntriesActivity extends com.beemdevelopment.aegis.ui.AegisActivity {
    static final int MUID_STATIC = getMUID();
    private java.util.List<com.beemdevelopment.aegis.otp.Transferable> _authInfos;

    private android.widget.ImageView _qrImage;

    private android.widget.TextView _description;

    private android.widget.TextView _issuer;

    private android.widget.TextView _accountName;

    private android.widget.TextView _entriesCount;

    private android.widget.Button _nextButton;

    private android.widget.Button _previousButton;

    private android.widget.Button _copyButton;

    private int _currentEntryCount = 1;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // TransferEntriesActivity_0_LengthyGUICreationOperatorMutator
            case 172: {
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
    setContentView(com.beemdevelopment.aegis.R.layout.activity_share_entry);
    switch(MUID_STATIC) {
        // TransferEntriesActivity_1_InvalidIDFindViewOperatorMutator
        case 1172: {
            setSupportActionBar(findViewById(732221));
            break;
        }
        default: {
        setSupportActionBar(findViewById(com.beemdevelopment.aegis.R.id.toolbar));
        break;
    }
}
switch(MUID_STATIC) {
    // TransferEntriesActivity_2_FindViewByIdReturnsNullOperatorMutator
    case 2172: {
        _qrImage = null;
        break;
    }
    // TransferEntriesActivity_3_InvalidIDFindViewOperatorMutator
    case 3172: {
        _qrImage = findViewById(732221);
        break;
    }
    // TransferEntriesActivity_4_InvalidViewFocusOperatorMutator
    case 4172: {
        /**
        * Inserted by Kadabra
        */
        _qrImage = findViewById(com.beemdevelopment.aegis.R.id.ivQrCode);
        _qrImage.requestFocus();
        break;
    }
    // TransferEntriesActivity_5_ViewComponentNotVisibleOperatorMutator
    case 5172: {
        /**
        * Inserted by Kadabra
        */
        _qrImage = findViewById(com.beemdevelopment.aegis.R.id.ivQrCode);
        _qrImage.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    _qrImage = findViewById(com.beemdevelopment.aegis.R.id.ivQrCode);
    break;
}
}
switch(MUID_STATIC) {
// TransferEntriesActivity_6_FindViewByIdReturnsNullOperatorMutator
case 6172: {
    _description = null;
    break;
}
// TransferEntriesActivity_7_InvalidIDFindViewOperatorMutator
case 7172: {
    _description = findViewById(732221);
    break;
}
// TransferEntriesActivity_8_InvalidViewFocusOperatorMutator
case 8172: {
    /**
    * Inserted by Kadabra
    */
    _description = findViewById(com.beemdevelopment.aegis.R.id.tvDescription);
    _description.requestFocus();
    break;
}
// TransferEntriesActivity_9_ViewComponentNotVisibleOperatorMutator
case 9172: {
    /**
    * Inserted by Kadabra
    */
    _description = findViewById(com.beemdevelopment.aegis.R.id.tvDescription);
    _description.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
_description = findViewById(com.beemdevelopment.aegis.R.id.tvDescription);
break;
}
}
switch(MUID_STATIC) {
// TransferEntriesActivity_10_FindViewByIdReturnsNullOperatorMutator
case 10172: {
_issuer = null;
break;
}
// TransferEntriesActivity_11_InvalidIDFindViewOperatorMutator
case 11172: {
_issuer = findViewById(732221);
break;
}
// TransferEntriesActivity_12_InvalidViewFocusOperatorMutator
case 12172: {
/**
* Inserted by Kadabra
*/
_issuer = findViewById(com.beemdevelopment.aegis.R.id.tvIssuer);
_issuer.requestFocus();
break;
}
// TransferEntriesActivity_13_ViewComponentNotVisibleOperatorMutator
case 13172: {
/**
* Inserted by Kadabra
*/
_issuer = findViewById(com.beemdevelopment.aegis.R.id.tvIssuer);
_issuer.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_issuer = findViewById(com.beemdevelopment.aegis.R.id.tvIssuer);
break;
}
}
switch(MUID_STATIC) {
// TransferEntriesActivity_14_FindViewByIdReturnsNullOperatorMutator
case 14172: {
_accountName = null;
break;
}
// TransferEntriesActivity_15_InvalidIDFindViewOperatorMutator
case 15172: {
_accountName = findViewById(732221);
break;
}
// TransferEntriesActivity_16_InvalidViewFocusOperatorMutator
case 16172: {
/**
* Inserted by Kadabra
*/
_accountName = findViewById(com.beemdevelopment.aegis.R.id.tvAccountName);
_accountName.requestFocus();
break;
}
// TransferEntriesActivity_17_ViewComponentNotVisibleOperatorMutator
case 17172: {
/**
* Inserted by Kadabra
*/
_accountName = findViewById(com.beemdevelopment.aegis.R.id.tvAccountName);
_accountName.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_accountName = findViewById(com.beemdevelopment.aegis.R.id.tvAccountName);
break;
}
}
switch(MUID_STATIC) {
// TransferEntriesActivity_18_FindViewByIdReturnsNullOperatorMutator
case 18172: {
_entriesCount = null;
break;
}
// TransferEntriesActivity_19_InvalidIDFindViewOperatorMutator
case 19172: {
_entriesCount = findViewById(732221);
break;
}
// TransferEntriesActivity_20_InvalidViewFocusOperatorMutator
case 20172: {
/**
* Inserted by Kadabra
*/
_entriesCount = findViewById(com.beemdevelopment.aegis.R.id.tvEntriesCount);
_entriesCount.requestFocus();
break;
}
// TransferEntriesActivity_21_ViewComponentNotVisibleOperatorMutator
case 21172: {
/**
* Inserted by Kadabra
*/
_entriesCount = findViewById(com.beemdevelopment.aegis.R.id.tvEntriesCount);
_entriesCount.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_entriesCount = findViewById(com.beemdevelopment.aegis.R.id.tvEntriesCount);
break;
}
}
switch(MUID_STATIC) {
// TransferEntriesActivity_22_FindViewByIdReturnsNullOperatorMutator
case 22172: {
_nextButton = null;
break;
}
// TransferEntriesActivity_23_InvalidIDFindViewOperatorMutator
case 23172: {
_nextButton = findViewById(732221);
break;
}
// TransferEntriesActivity_24_InvalidViewFocusOperatorMutator
case 24172: {
/**
* Inserted by Kadabra
*/
_nextButton = findViewById(com.beemdevelopment.aegis.R.id.btnNext);
_nextButton.requestFocus();
break;
}
// TransferEntriesActivity_25_ViewComponentNotVisibleOperatorMutator
case 25172: {
/**
* Inserted by Kadabra
*/
_nextButton = findViewById(com.beemdevelopment.aegis.R.id.btnNext);
_nextButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_nextButton = findViewById(com.beemdevelopment.aegis.R.id.btnNext);
break;
}
}
switch(MUID_STATIC) {
// TransferEntriesActivity_26_FindViewByIdReturnsNullOperatorMutator
case 26172: {
_previousButton = null;
break;
}
// TransferEntriesActivity_27_InvalidIDFindViewOperatorMutator
case 27172: {
_previousButton = findViewById(732221);
break;
}
// TransferEntriesActivity_28_InvalidViewFocusOperatorMutator
case 28172: {
/**
* Inserted by Kadabra
*/
_previousButton = findViewById(com.beemdevelopment.aegis.R.id.btnPrevious);
_previousButton.requestFocus();
break;
}
// TransferEntriesActivity_29_ViewComponentNotVisibleOperatorMutator
case 29172: {
/**
* Inserted by Kadabra
*/
_previousButton = findViewById(com.beemdevelopment.aegis.R.id.btnPrevious);
_previousButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_previousButton = findViewById(com.beemdevelopment.aegis.R.id.btnPrevious);
break;
}
}
switch(MUID_STATIC) {
// TransferEntriesActivity_30_FindViewByIdReturnsNullOperatorMutator
case 30172: {
_copyButton = null;
break;
}
// TransferEntriesActivity_31_InvalidIDFindViewOperatorMutator
case 31172: {
_copyButton = findViewById(732221);
break;
}
// TransferEntriesActivity_32_InvalidViewFocusOperatorMutator
case 32172: {
/**
* Inserted by Kadabra
*/
_copyButton = findViewById(com.beemdevelopment.aegis.R.id.btnCopyClipboard);
_copyButton.requestFocus();
break;
}
// TransferEntriesActivity_33_ViewComponentNotVisibleOperatorMutator
case 33172: {
/**
* Inserted by Kadabra
*/
_copyButton = findViewById(com.beemdevelopment.aegis.R.id.btnCopyClipboard);
_copyButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_copyButton = findViewById(com.beemdevelopment.aegis.R.id.btnCopyClipboard);
break;
}
}
if (getSupportActionBar() != null) {
getSupportActionBar().setDisplayHomeAsUpEnabled(true);
getSupportActionBar().setDisplayShowHomeEnabled(true);
}
android.content.Intent intent;
switch(MUID_STATIC) {
// TransferEntriesActivity_34_RandomActionIntentDefinitionOperatorMutator
case 34172: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = getIntent();
break;
}
}
_authInfos = ((java.util.ArrayList<com.beemdevelopment.aegis.otp.Transferable>) (intent.getSerializableExtra("authInfos")));
int controlVisibility;
controlVisibility = (_authInfos.size() != 1) ? android.view.View.VISIBLE : android.view.View.INVISIBLE;
_nextButton.setVisibility(controlVisibility);
switch(MUID_STATIC) {
// TransferEntriesActivity_35_BuggyGUIListenerOperatorMutator
case 35172: {
_nextButton.setOnClickListener(null);
break;
}
default: {
_nextButton.setOnClickListener((android.view.View v) -> {
if (_currentEntryCount < _authInfos.size()) {
_previousButton.setVisibility(android.view.View.VISIBLE);
_currentEntryCount++;
generateQR();
if (_currentEntryCount == _authInfos.size()) {
_nextButton.setText(com.beemdevelopment.aegis.R.string.done);
}
} else {
finish();
}
});
break;
}
}
switch(MUID_STATIC) {
// TransferEntriesActivity_36_BuggyGUIListenerOperatorMutator
case 36172: {
_previousButton.setOnClickListener(null);
break;
}
default: {
_previousButton.setOnClickListener((android.view.View v) -> {
if (_currentEntryCount > 1) {
_nextButton.setText(com.beemdevelopment.aegis.R.string.next);
_currentEntryCount--;
generateQR();
if (_currentEntryCount == 1) {
_previousButton.setVisibility(android.view.View.INVISIBLE);
}
}
});
break;
}
}
if (_authInfos.get(0) instanceof com.beemdevelopment.aegis.otp.GoogleAuthInfo) {
_copyButton.setVisibility(android.view.View.VISIBLE);
}
switch(MUID_STATIC) {
// TransferEntriesActivity_37_BuggyGUIListenerOperatorMutator
case 37172: {
_copyButton.setOnClickListener(null);
break;
}
default: {
_copyButton.setOnClickListener((android.view.View v) -> {
com.beemdevelopment.aegis.otp.Transferable selectedEntry;
switch(MUID_STATIC) {
// TransferEntriesActivity_38_BinaryMutator
case 38172: {
selectedEntry = _authInfos.get(_currentEntryCount + 1);
break;
}
default: {
selectedEntry = _authInfos.get(_currentEntryCount - 1);
break;
}
}
try {
android.content.ClipboardManager clipboard;
clipboard = ((android.content.ClipboardManager) (getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
android.content.ClipData clip;
clip = android.content.ClipData.newPlainText("text/plain", selectedEntry.getUri().toString());
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
android.os.PersistableBundle extras;
extras = new android.os.PersistableBundle();
extras.putBoolean(android.content.ClipDescription.EXTRA_IS_SENSITIVE, true);
clip.getDescription().setExtras(extras);
}
if (clipboard != null) {
clipboard.setPrimaryClip(clip);
}
android.widget.Toast.makeText(this, com.beemdevelopment.aegis.R.string.uri_copied_to_clipboard, android.widget.Toast.LENGTH_SHORT).show();
} catch (com.beemdevelopment.aegis.otp.GoogleAuthInfoException e) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.unable_to_copy_uri_to_clipboard, e);
}
});
break;
}
}
generateQR();
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
switch (item.getItemId()) {
case android.R.id.home :
finish();
break;
default :
return super.onOptionsItemSelected(item);
}
return true;
}


private void generateQR() {
com.beemdevelopment.aegis.otp.Transferable selectedEntry;
switch(MUID_STATIC) {
// TransferEntriesActivity_39_BinaryMutator
case 39172: {
selectedEntry = _authInfos.get(_currentEntryCount + 1);
break;
}
default: {
selectedEntry = _authInfos.get(_currentEntryCount - 1);
break;
}
}
if (selectedEntry instanceof com.beemdevelopment.aegis.otp.GoogleAuthInfo) {
com.beemdevelopment.aegis.otp.GoogleAuthInfo entry;
entry = ((com.beemdevelopment.aegis.otp.GoogleAuthInfo) (selectedEntry));
_issuer.setText(entry.getIssuer());
_accountName.setText(entry.getAccountName());
} else if (selectedEntry instanceof com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export) {
_description.setText(com.beemdevelopment.aegis.R.string.google_auth_compatible_transfer_description);
}
_entriesCount.setText(getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.qr_count, _authInfos.size(), _currentEntryCount, _authInfos.size()));
@androidx.annotation.ColorInt
int backgroundColor;
backgroundColor = android.graphics.Color.WHITE;
if (getConfiguredTheme() == com.beemdevelopment.aegis.Theme.LIGHT) {
android.util.TypedValue typedValue;
typedValue = new android.util.TypedValue();
getTheme().resolveAttribute(com.beemdevelopment.aegis.R.attr.background, typedValue, true);
backgroundColor = typedValue.data;
}
android.graphics.Bitmap bitmap;
try {
bitmap = com.beemdevelopment.aegis.helpers.QrCodeHelper.encodeToBitmap(selectedEntry.getUri().toString(), 512, 512, backgroundColor);
} catch (com.google.zxing.WriterException | com.beemdevelopment.aegis.otp.GoogleAuthInfoException e) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.unable_to_generate_qrcode, e);
return;
}
_qrImage.setImageBitmap(bitmap);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
