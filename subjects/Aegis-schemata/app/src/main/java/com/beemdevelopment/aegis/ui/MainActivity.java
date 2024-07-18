package com.beemdevelopment.aegis.ui;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;
import com.beemdevelopment.aegis.SortCategory;
import com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment;
import android.widget.Button;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import androidx.annotation.NonNull;
import android.os.Build;
import com.beemdevelopment.aegis.ViewMode;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.util.TreeSet;
import com.beemdevelopment.aegis.ui.fragments.preferences.BackupsPreferencesFragment;
import android.provider.Settings;
import android.widget.TextView;
import java.util.List;
import java.util.UUID;
import java.util.Collections;
import java.util.stream.Collectors;
import android.view.Menu;
import android.view.MenuInflater;
import android.graphics.Typeface;
import android.content.ClipboardManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.widget.CheckBox;
import android.view.LayoutInflater;
import androidx.appcompat.view.ActionMode;
import com.beemdevelopment.aegis.ui.views.EntryListView;
import com.beemdevelopment.aegis.helpers.FabScrollHelper;
import java.util.Objects;
import android.content.ClipData;
import java.util.Map;
import android.content.DialogInterface;
import android.net.Uri;
import com.beemdevelopment.aegis.util.TimeUtils;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import android.view.KeyEvent;
import androidx.activity.OnBackPressedCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.Toast;
import com.beemdevelopment.aegis.Preferences;
import android.widget.LinearLayout;
import android.os.Bundle;
import com.beemdevelopment.aegis.ui.tasks.QrDecodeTask;
import android.content.Intent;
import android.view.MenuItem;
import com.google.common.base.Strings;
import android.view.View;
import java.util.Date;
import android.text.style.StyleSpan;
import android.os.PersistableBundle;
import android.Manifest;
import com.beemdevelopment.aegis.helpers.PermissionHelper;
import androidx.appcompat.widget.SearchView;
import android.annotation.SuppressLint;
import com.beemdevelopment.aegis.otp.GoogleAuthInfoException;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import android.content.ClipDescription;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MainActivity extends com.beemdevelopment.aegis.ui.AegisActivity implements com.beemdevelopment.aegis.ui.views.EntryListView.Listener {
    static final int MUID_STATIC = getMUID();
    // activity request codes
    private static final int CODE_SCAN = 0;

    private static final int CODE_ADD_ENTRY = 1;

    private static final int CODE_EDIT_ENTRY = 2;

    private static final int CODE_DO_INTRO = 3;

    private static final int CODE_DECRYPT = 4;

    private static final int CODE_PREFERENCES = 5;

    private static final int CODE_SCAN_IMAGE = 6;

    // Permission request codes
    private static final int CODE_PERM_CAMERA = 0;

    private boolean _loaded;

    private boolean _isRecreated;

    private boolean _isDPadPressed;

    private boolean _isDoingIntro;

    private boolean _isAuthenticating;

    private java.lang.String _submittedSearchQuery;

    private java.lang.String _pendingSearchQuery;

    private java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> _selectedEntries;

    private android.view.Menu _menu;

    private androidx.appcompat.widget.SearchView _searchView;

    private com.beemdevelopment.aegis.ui.views.EntryListView _entryListView;

    private android.widget.LinearLayout _btnErrorBar;

    private android.widget.TextView _textErrorBar;

    private com.beemdevelopment.aegis.helpers.FabScrollHelper _fabScrollHelper;

    private androidx.appcompat.view.ActionMode _actionMode;

    private androidx.appcompat.view.ActionMode.Callback _actionModeCallbacks = new com.beemdevelopment.aegis.ui.MainActivity.ActionModeCallbacks();

    private com.beemdevelopment.aegis.ui.MainActivity.LockBackPressHandler _lockBackPressHandler;

    private com.beemdevelopment.aegis.ui.MainActivity.SearchViewBackPressHandler _searchViewBackPressHandler;

    private com.beemdevelopment.aegis.ui.MainActivity.ActionModeBackPressHandler _actionModeBackPressHandler;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // MainActivity_0_LengthyGUICreationOperatorMutator
            case 173: {
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
    setContentView(com.beemdevelopment.aegis.R.layout.activity_main);
    switch(MUID_STATIC) {
        // MainActivity_1_InvalidIDFindViewOperatorMutator
        case 1173: {
            setSupportActionBar(findViewById(732221));
            break;
        }
        default: {
        setSupportActionBar(findViewById(com.beemdevelopment.aegis.R.id.toolbar));
        break;
    }
}
_loaded = false;
_isDPadPressed = false;
_isDoingIntro = false;
_isAuthenticating = false;
if (savedInstanceState != null) {
    _isRecreated = true;
    _pendingSearchQuery = savedInstanceState.getString("pendingSearchQuery");
    _submittedSearchQuery = savedInstanceState.getString("submittedSearchQuery");
    _isDoingIntro = savedInstanceState.getBoolean("isDoingIntro");
    _isAuthenticating = savedInstanceState.getBoolean("isAuthenticating");
}
_lockBackPressHandler = new com.beemdevelopment.aegis.ui.MainActivity.LockBackPressHandler();
getOnBackPressedDispatcher().addCallback(this, _lockBackPressHandler);
_searchViewBackPressHandler = new com.beemdevelopment.aegis.ui.MainActivity.SearchViewBackPressHandler();
getOnBackPressedDispatcher().addCallback(this, _searchViewBackPressHandler);
_actionModeBackPressHandler = new com.beemdevelopment.aegis.ui.MainActivity.ActionModeBackPressHandler();
getOnBackPressedDispatcher().addCallback(this, _actionModeBackPressHandler);
_entryListView = ((com.beemdevelopment.aegis.ui.views.EntryListView) (getSupportFragmentManager().findFragmentById(com.beemdevelopment.aegis.R.id.key_profiles)));
_entryListView.setListener(this);
_entryListView.setCodeGroupSize(_prefs.getCodeGroupSize());
_entryListView.setShowAccountName(_prefs.isAccountNameVisible());
_entryListView.setShowIcon(_prefs.isIconVisible());
_entryListView.setHighlightEntry(_prefs.isEntryHighlightEnabled());
_entryListView.setPauseFocused(_prefs.isPauseFocusedEnabled());
_entryListView.setTapToReveal(_prefs.isTapToRevealEnabled());
_entryListView.setTapToRevealTime(_prefs.getTapToRevealTime());
_entryListView.setSortCategory(_prefs.getCurrentSortCategory(), false);
_entryListView.setViewMode(_prefs.getCurrentViewMode());
_entryListView.setIsCopyOnTapEnabled(_prefs.isCopyOnTapEnabled());
_entryListView.setPrefGroupFilter(_prefs.getGroupFilter());
com.google.android.material.floatingactionbutton.FloatingActionButton fab;
switch(MUID_STATIC) {
    // MainActivity_2_FindViewByIdReturnsNullOperatorMutator
    case 2173: {
        fab = null;
        break;
    }
    // MainActivity_3_InvalidIDFindViewOperatorMutator
    case 3173: {
        fab = findViewById(732221);
        break;
    }
    // MainActivity_4_InvalidViewFocusOperatorMutator
    case 4173: {
        /**
        * Inserted by Kadabra
        */
        fab = findViewById(com.beemdevelopment.aegis.R.id.fab);
        fab.requestFocus();
        break;
    }
    // MainActivity_5_ViewComponentNotVisibleOperatorMutator
    case 5173: {
        /**
        * Inserted by Kadabra
        */
        fab = findViewById(com.beemdevelopment.aegis.R.id.fab);
        fab.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    fab = findViewById(com.beemdevelopment.aegis.R.id.fab);
    break;
}
}
switch(MUID_STATIC) {
// MainActivity_6_BuggyGUIListenerOperatorMutator
case 6173: {
    fab.setOnClickListener(null);
    break;
}
default: {
fab.setOnClickListener((android.view.View v) -> {
    android.view.View view;
    view = getLayoutInflater().inflate(com.beemdevelopment.aegis.R.layout.dialog_add_entry, null);
    com.google.android.material.bottomsheet.BottomSheetDialog dialog;
    dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(this);
    dialog.setContentView(view);
    switch(MUID_STATIC) {
        // MainActivity_7_BuggyGUIListenerOperatorMutator
        case 7173: {
            view.findViewById(com.beemdevelopment.aegis.R.id.fab_enter).setOnClickListener(null);
            break;
        }
        default: {
        view.findViewById(com.beemdevelopment.aegis.R.id.fab_enter).setOnClickListener((android.view.View v1) -> {
            dialog.dismiss();
            startEditEntryActivityForManual(com.beemdevelopment.aegis.ui.MainActivity.CODE_ADD_ENTRY);
        });
        break;
    }
}
switch(MUID_STATIC) {
    // MainActivity_8_BuggyGUIListenerOperatorMutator
    case 8173: {
        view.findViewById(com.beemdevelopment.aegis.R.id.fab_scan_image).setOnClickListener(null);
        break;
    }
    default: {
    view.findViewById(com.beemdevelopment.aegis.R.id.fab_scan_image).setOnClickListener((android.view.View v2) -> {
        dialog.dismiss();
        startScanImageActivity();
    });
    break;
}
}
switch(MUID_STATIC) {
// MainActivity_9_BuggyGUIListenerOperatorMutator
case 9173: {
    view.findViewById(com.beemdevelopment.aegis.R.id.fab_scan).setOnClickListener(null);
    break;
}
default: {
view.findViewById(com.beemdevelopment.aegis.R.id.fab_scan).setOnClickListener((android.view.View v3) -> {
    dialog.dismiss();
    startScanActivity();
});
break;
}
}
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
});
break;
}
}
switch(MUID_STATIC) {
// MainActivity_10_FindViewByIdReturnsNullOperatorMutator
case 10173: {
_btnErrorBar = null;
break;
}
// MainActivity_11_InvalidIDFindViewOperatorMutator
case 11173: {
_btnErrorBar = findViewById(732221);
break;
}
// MainActivity_12_InvalidViewFocusOperatorMutator
case 12173: {
/**
* Inserted by Kadabra
*/
_btnErrorBar = findViewById(com.beemdevelopment.aegis.R.id.btn_error_bar);
_btnErrorBar.requestFocus();
break;
}
// MainActivity_13_ViewComponentNotVisibleOperatorMutator
case 13173: {
/**
* Inserted by Kadabra
*/
_btnErrorBar = findViewById(com.beemdevelopment.aegis.R.id.btn_error_bar);
_btnErrorBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_btnErrorBar = findViewById(com.beemdevelopment.aegis.R.id.btn_error_bar);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_14_FindViewByIdReturnsNullOperatorMutator
case 14173: {
_textErrorBar = null;
break;
}
// MainActivity_15_InvalidIDFindViewOperatorMutator
case 15173: {
_textErrorBar = findViewById(732221);
break;
}
// MainActivity_16_InvalidViewFocusOperatorMutator
case 16173: {
/**
* Inserted by Kadabra
*/
_textErrorBar = findViewById(com.beemdevelopment.aegis.R.id.text_error_bar);
_textErrorBar.requestFocus();
break;
}
// MainActivity_17_ViewComponentNotVisibleOperatorMutator
case 17173: {
/**
* Inserted by Kadabra
*/
_textErrorBar = findViewById(com.beemdevelopment.aegis.R.id.text_error_bar);
_textErrorBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textErrorBar = findViewById(com.beemdevelopment.aegis.R.id.text_error_bar);
break;
}
}
_fabScrollHelper = new com.beemdevelopment.aegis.helpers.FabScrollHelper(fab);
_selectedEntries = new java.util.ArrayList<>();
}


@java.lang.Override
protected void onDestroy() {
_entryListView.setListener(null);
super.onDestroy();
}


@java.lang.Override
protected void onPause() {
java.util.Map<java.util.UUID, java.lang.Integer> usageMap;
usageMap = _entryListView.getUsageCounts();
if (usageMap != null) {
_prefs.setUsageCount(usageMap);
}
super.onPause();
}


@java.lang.Override
protected void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle instance) {
super.onSaveInstanceState(instance);
instance.putString("pendingSearchQuery", _pendingSearchQuery);
instance.putString("submittedSearchQuery", _submittedSearchQuery);
instance.putBoolean("isDoingIntro", _isDoingIntro);
instance.putBoolean("isAuthenticating", _isAuthenticating);
}


@java.lang.Override
protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
if (requestCode == com.beemdevelopment.aegis.ui.MainActivity.CODE_DECRYPT) {
_isAuthenticating = false;
}
if (requestCode == com.beemdevelopment.aegis.ui.MainActivity.CODE_DO_INTRO) {
_isDoingIntro = false;
}
if (resultCode != android.app.Activity.RESULT_OK) {
return;
}
switch (requestCode) {
case com.beemdevelopment.aegis.ui.MainActivity.CODE_SCAN :
onScanResult(data);
break;
case com.beemdevelopment.aegis.ui.MainActivity.CODE_ADD_ENTRY :
onAddEntryResult(data);
break;
case com.beemdevelopment.aegis.ui.MainActivity.CODE_EDIT_ENTRY :
onEditEntryResult(data);
break;
case com.beemdevelopment.aegis.ui.MainActivity.CODE_DO_INTRO :
onIntroResult();
break;
case com.beemdevelopment.aegis.ui.MainActivity.CODE_DECRYPT :
onDecryptResult();
break;
case com.beemdevelopment.aegis.ui.MainActivity.CODE_PREFERENCES :
onPreferencesResult(data);
break;
case com.beemdevelopment.aegis.ui.MainActivity.CODE_SCAN_IMAGE :
onScanImageResult(data);
}
super.onActivityResult(requestCode, resultCode, data);
}


@java.lang.Override
public void onRequestPermissionsResult(int requestCode, java.lang.String[] permissions, int[] grantResults) {
if (!com.beemdevelopment.aegis.helpers.PermissionHelper.checkResults(grantResults)) {
android.widget.Toast.makeText(this, getString(com.beemdevelopment.aegis.R.string.permission_denied), android.widget.Toast.LENGTH_SHORT).show();
return;
}
if (requestCode == com.beemdevelopment.aegis.ui.MainActivity.CODE_PERM_CAMERA) {
startScanActivity();
}
super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}


@java.lang.Override
public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
_isDPadPressed = com.beemdevelopment.aegis.ui.MainActivity.isDPadKey(keyCode);
return super.onKeyDown(keyCode, event);
}


private static boolean isDPadKey(int keyCode) {
return (((keyCode == android.view.KeyEvent.KEYCODE_DPAD_DOWN) || (keyCode == android.view.KeyEvent.KEYCODE_DPAD_UP)) || (keyCode == android.view.KeyEvent.KEYCODE_DPAD_RIGHT)) || (keyCode == android.view.KeyEvent.KEYCODE_DPAD_LEFT);
}


@java.lang.Override
public void onEntryListTouch() {
_isDPadPressed = false;
}


private void onPreferencesResult(android.content.Intent data) {
// refresh the entire entry list if needed
if (_loaded) {
if (data.getBooleanExtra("needsRecreate", false)) {
recreate();
} else if (data.getBooleanExtra("needsRefresh", false)) {
boolean showAccountName;
showAccountName = _prefs.isAccountNameVisible();
boolean showIcons;
showIcons = _prefs.isIconVisible();
com.beemdevelopment.aegis.Preferences.CodeGrouping codeGroupSize;
codeGroupSize = _prefs.getCodeGroupSize();
boolean highlightEntry;
highlightEntry = _prefs.isEntryHighlightEnabled();
boolean pauseFocused;
pauseFocused = _prefs.isPauseFocusedEnabled();
boolean tapToReveal;
tapToReveal = _prefs.isTapToRevealEnabled();
int tapToRevealTime;
tapToRevealTime = _prefs.getTapToRevealTime();
com.beemdevelopment.aegis.ViewMode viewMode;
viewMode = _prefs.getCurrentViewMode();
boolean copyOnTap;
copyOnTap = _prefs.isCopyOnTapEnabled();
_entryListView.setShowAccountName(showAccountName);
_entryListView.setShowIcon(showIcons);
_entryListView.setCodeGroupSize(codeGroupSize);
_entryListView.setHighlightEntry(highlightEntry);
_entryListView.setPauseFocused(pauseFocused);
_entryListView.setTapToReveal(tapToReveal);
_entryListView.setTapToRevealTime(tapToRevealTime);
_entryListView.setViewMode(viewMode);
_entryListView.setIsCopyOnTapEnabled(copyOnTap);
_entryListView.refresh(true);
}
}
}


private void startEditEntryActivityForNew(int requestCode, com.beemdevelopment.aegis.vault.VaultEntry entry) {
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_18_NullIntentOperatorMutator
case 18173: {
intent = null;
break;
}
// MainActivity_19_InvalidKeyIntentOperatorMutator
case 19173: {
intent = new android.content.Intent((MainActivity) null, com.beemdevelopment.aegis.ui.EditEntryActivity.class);
break;
}
// MainActivity_20_RandomActionIntentDefinitionOperatorMutator
case 20173: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(this, com.beemdevelopment.aegis.ui.EditEntryActivity.class);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_21_NullValueIntentPutExtraOperatorMutator
case 21173: {
intent.putExtra("newEntry", new Parcelable[0]);
break;
}
// MainActivity_22_IntentPayloadReplacementOperatorMutator
case 22173: {
intent.putExtra("newEntry", (com.beemdevelopment.aegis.vault.VaultEntry) null);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_23_RandomActionIntentDefinitionOperatorMutator
case 23173: {
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
intent.putExtra("newEntry", entry);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// MainActivity_24_NullValueIntentPutExtraOperatorMutator
case 24173: {
intent.putExtra("isManual", new Parcelable[0]);
break;
}
// MainActivity_25_IntentPayloadReplacementOperatorMutator
case 25173: {
intent.putExtra("isManual", true);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_26_RandomActionIntentDefinitionOperatorMutator
case 26173: {
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
intent.putExtra("isManual", false);
break;
}
}
break;
}
}
startActivityForResult(intent, requestCode);
}


private void startEditEntryActivityForManual(int requestCode) {
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_27_NullIntentOperatorMutator
case 27173: {
intent = null;
break;
}
// MainActivity_28_InvalidKeyIntentOperatorMutator
case 28173: {
intent = new android.content.Intent((MainActivity) null, com.beemdevelopment.aegis.ui.EditEntryActivity.class);
break;
}
// MainActivity_29_RandomActionIntentDefinitionOperatorMutator
case 29173: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(this, com.beemdevelopment.aegis.ui.EditEntryActivity.class);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_30_NullValueIntentPutExtraOperatorMutator
case 30173: {
intent.putExtra("newEntry", new Parcelable[0]);
break;
}
// MainActivity_31_IntentPayloadReplacementOperatorMutator
case 31173: {
intent.putExtra("newEntry", (VaultEntry) null);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_32_RandomActionIntentDefinitionOperatorMutator
case 32173: {
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
intent.putExtra("newEntry", com.beemdevelopment.aegis.vault.VaultEntry.getDefault());
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// MainActivity_33_NullValueIntentPutExtraOperatorMutator
case 33173: {
intent.putExtra("isManual", new Parcelable[0]);
break;
}
// MainActivity_34_IntentPayloadReplacementOperatorMutator
case 34173: {
intent.putExtra("isManual", true);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_35_RandomActionIntentDefinitionOperatorMutator
case 35173: {
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
intent.putExtra("isManual", true);
break;
}
}
break;
}
}
startActivityForResult(intent, requestCode);
}


private void startEditEntryActivity(int requestCode, com.beemdevelopment.aegis.vault.VaultEntry entry) {
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_36_NullIntentOperatorMutator
case 36173: {
intent = null;
break;
}
// MainActivity_37_InvalidKeyIntentOperatorMutator
case 37173: {
intent = new android.content.Intent((MainActivity) null, com.beemdevelopment.aegis.ui.EditEntryActivity.class);
break;
}
// MainActivity_38_RandomActionIntentDefinitionOperatorMutator
case 38173: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(this, com.beemdevelopment.aegis.ui.EditEntryActivity.class);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_39_NullValueIntentPutExtraOperatorMutator
case 39173: {
intent.putExtra("entryUUID", new Parcelable[0]);
break;
}
// MainActivity_40_IntentPayloadReplacementOperatorMutator
case 40173: {
intent.putExtra("entryUUID", (UUID) null);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_41_RandomActionIntentDefinitionOperatorMutator
case 41173: {
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
intent.putExtra("entryUUID", entry.getUUID());
break;
}
}
break;
}
}
startActivityForResult(intent, requestCode);
}


private void startIntroActivity() {
if (!_isDoingIntro) {
android.content.Intent intro;
switch(MUID_STATIC) {
// MainActivity_42_NullIntentOperatorMutator
case 42173: {
intro = null;
break;
}
// MainActivity_43_InvalidKeyIntentOperatorMutator
case 43173: {
intro = new android.content.Intent((MainActivity) null, com.beemdevelopment.aegis.ui.IntroActivity.class);
break;
}
// MainActivity_44_RandomActionIntentDefinitionOperatorMutator
case 44173: {
intro = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intro = new android.content.Intent(this, com.beemdevelopment.aegis.ui.IntroActivity.class);
break;
}
}
startActivityForResult(intro, com.beemdevelopment.aegis.ui.MainActivity.CODE_DO_INTRO);
_isDoingIntro = true;
}
}


private void onScanResult(android.content.Intent data) {
java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> entries;
entries = ((java.util.ArrayList<com.beemdevelopment.aegis.vault.VaultEntry>) (data.getSerializableExtra("entries")));
if (entries != null) {
importScannedEntries(entries);
}
}


private void onAddEntryResult(android.content.Intent data) {
if (_loaded) {
java.util.UUID entryUUID;
entryUUID = ((java.util.UUID) (data.getSerializableExtra("entryUUID")));
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = _vaultManager.getVault().getEntryByUUID(entryUUID);
_entryListView.addEntry(entry, true);
}
}


private void onEditEntryResult(android.content.Intent data) {
if (_loaded) {
java.util.UUID entryUUID;
entryUUID = ((java.util.UUID) (data.getSerializableExtra("entryUUID")));
if (data.getBooleanExtra("delete", false)) {
_entryListView.removeEntry(entryUUID);
} else {
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = _vaultManager.getVault().getEntryByUUID(entryUUID);
_entryListView.replaceEntry(entryUUID, entry);
}
}
}


private void onScanImageResult(android.content.Intent intent) {
if (intent.getData() != null) {
startDecodeQrCodeImages(java.util.Collections.singletonList(intent.getData()));
return;
}
if (intent.getClipData() != null) {
android.content.ClipData data;
data = intent.getClipData();
java.util.List<android.net.Uri> uris;
uris = new java.util.ArrayList<>();
for (int i = 0; i < data.getItemCount(); i++) {
android.content.ClipData.Item item;
item = data.getItemAt(i);
if (item.getUri() != null) {
uris.add(item.getUri());
}
}
if (uris.size() > 0) {
startDecodeQrCodeImages(uris);
}
}
}


private static java.lang.CharSequence buildImportError(java.lang.String fileName, java.lang.Throwable e) {
android.text.SpannableStringBuilder builder;
builder = new android.text.SpannableStringBuilder(java.lang.String.format("%s:\n%s", fileName, e));
builder.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, fileName.length(), android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
return builder;
}


private void startDecodeQrCodeImages(java.util.List<android.net.Uri> uris) {
com.beemdevelopment.aegis.ui.tasks.QrDecodeTask task;
task = new com.beemdevelopment.aegis.ui.tasks.QrDecodeTask(this, (java.util.List<com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Result> results) -> {
java.util.List<java.lang.CharSequence> errors;
errors = new java.util.ArrayList<>();
java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> entries;
entries = new java.util.ArrayList<>();
java.util.List<com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export> googleAuthExports;
googleAuthExports = new java.util.ArrayList<>();
for (com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Result res : results) {
if (res.getException() != null) {
errors.add(com.beemdevelopment.aegis.ui.MainActivity.buildImportError(res.getFileName(), res.getException()));
continue;
}
try {
android.net.Uri scanned;
scanned = android.net.Uri.parse(res.getResult().getText());
if (java.util.Objects.equals(scanned.getScheme(), com.beemdevelopment.aegis.otp.GoogleAuthInfo.SCHEME_EXPORT)) {
com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export export;
export = com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseExportUri(scanned);
for (com.beemdevelopment.aegis.otp.GoogleAuthInfo info : export.getEntries()) {
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = new com.beemdevelopment.aegis.vault.VaultEntry(info);
entries.add(entry);
}
googleAuthExports.add(export);
} else {
com.beemdevelopment.aegis.otp.GoogleAuthInfo info;
info = com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseUri(res.getResult().getText());
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = new com.beemdevelopment.aegis.vault.VaultEntry(info);
entries.add(entry);
}
} catch (com.beemdevelopment.aegis.otp.GoogleAuthInfoException e) {
errors.add(com.beemdevelopment.aegis.ui.MainActivity.buildImportError(res.getFileName(), e));
}
}
final android.content.DialogInterface.OnClickListener dialogDismissHandler;
switch(MUID_STATIC) {
// MainActivity_45_BuggyGUIListenerOperatorMutator
case 45173: {
dialogDismissHandler = null;
break;
}
default: {
dialogDismissHandler = (android.content.DialogInterface dialog,int which) -> importScannedEntries(entries);
break;
}
}
if (!googleAuthExports.isEmpty()) {
boolean isSingleBatch;
isSingleBatch = com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export.isSingleBatch(googleAuthExports);
if ((!isSingleBatch) && (errors.size() > 0)) {
errors.add(getString(com.beemdevelopment.aegis.R.string.unrelated_google_auth_batches_error));
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showMultiMessageDialog(this, com.beemdevelopment.aegis.R.string.import_error_title, getString(com.beemdevelopment.aegis.R.string.no_tokens_can_be_imported), errors, null);
return;
} else if (!isSingleBatch) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.import_google_auth_failure, getString(com.beemdevelopment.aegis.R.string.unrelated_google_auth_batches_error));
return;
} else {
java.util.List<java.lang.Integer> missingIndices;
missingIndices = com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export.getMissingIndices(googleAuthExports);
if (missingIndices.size() != 0) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showPartialGoogleAuthImportWarningDialog(this, missingIndices, entries.size(), errors, dialogDismissHandler);
return;
}
}
}
if (((errors.size() > 0) && (results.size() > 1)) || (errors.size() > 1)) {
switch(MUID_STATIC) {
// MainActivity_46_BinaryMutator
case 46173: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showMultiMessageDialog(this, com.beemdevelopment.aegis.R.string.import_error_title, getString(com.beemdevelopment.aegis.R.string.unable_to_read_qrcode_files, uris.size() + errors.size(), uris.size()), errors, dialogDismissHandler);
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showMultiMessageDialog(this, com.beemdevelopment.aegis.R.string.import_error_title, getString(com.beemdevelopment.aegis.R.string.unable_to_read_qrcode_files, uris.size() - errors.size(), uris.size()), errors, dialogDismissHandler);
break;
}
}
} else if (errors.size() > 0) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, getString(com.beemdevelopment.aegis.R.string.unable_to_read_qrcode_file, results.get(0).getFileName()), errors.get(0), dialogDismissHandler);
} else {
importScannedEntries(entries);
}
});
task.execute(getLifecycle(), uris);
}


private void importScannedEntries(java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> entries) {
if (entries.size() == 1) {
startEditEntryActivityForNew(com.beemdevelopment.aegis.ui.MainActivity.CODE_ADD_ENTRY, entries.get(0));
} else if (entries.size() > 1) {
for (com.beemdevelopment.aegis.vault.VaultEntry entry : entries) {
_vaultManager.getVault().addEntry(entry);
_entryListView.addEntry(entry);
}
if (saveAndBackupVault()) {
android.widget.Toast.makeText(this, getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.added_new_entries, entries.size(), entries.size()), android.widget.Toast.LENGTH_LONG).show();
}
}
}


private void updateSortCategoryMenu() {
com.beemdevelopment.aegis.SortCategory category;
category = _prefs.getCurrentSortCategory();
_menu.findItem(category.getMenuItem()).setChecked(true);
}


private void onIntroResult() {
loadEntries();
checkTimeSyncSetting();
}


private void checkTimeSyncSetting() {
boolean autoTime;
autoTime = android.provider.Settings.Global.getInt(getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 1) == 1;
if ((!autoTime) && _prefs.isTimeSyncWarningEnabled()) {
switch(MUID_STATIC) {
// MainActivity_47_BuggyGUIListenerOperatorMutator
case 47173: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTimeSyncWarningDialog(this, null);
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTimeSyncWarningDialog(this, (android.content.DialogInterface dialog,int which) -> {
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_48_InvalidKeyIntentOperatorMutator
case 48173: {
intent = new android.content.Intent((String) null);
break;
}
// MainActivity_49_RandomActionIntentDefinitionOperatorMutator
case 49173: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.provider.Settings.ACTION_DATE_SETTINGS);
break;
}
}
startActivity(intent);
});
break;
}
}
}
}


private void onDecryptResult() {
loadEntries();
checkTimeSyncSetting();
}


private void startScanActivity() {
if (!com.beemdevelopment.aegis.helpers.PermissionHelper.request(this, com.beemdevelopment.aegis.ui.MainActivity.CODE_PERM_CAMERA, android.Manifest.permission.CAMERA)) {
return;
}
android.content.Intent scannerActivity;
switch(MUID_STATIC) {
// MainActivity_50_NullIntentOperatorMutator
case 50173: {
scannerActivity = null;
break;
}
// MainActivity_51_InvalidKeyIntentOperatorMutator
case 51173: {
scannerActivity = new android.content.Intent((android.content.Context) null, com.beemdevelopment.aegis.ui.ScannerActivity.class);
break;
}
// MainActivity_52_RandomActionIntentDefinitionOperatorMutator
case 52173: {
scannerActivity = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
scannerActivity = new android.content.Intent(getApplicationContext(), com.beemdevelopment.aegis.ui.ScannerActivity.class);
break;
}
}
startActivityForResult(scannerActivity, com.beemdevelopment.aegis.ui.MainActivity.CODE_SCAN);
}


private void startScanImageActivity() {
android.content.Intent galleryIntent;
switch(MUID_STATIC) {
// MainActivity_53_NullIntentOperatorMutator
case 53173: {
galleryIntent = null;
break;
}
// MainActivity_54_InvalidKeyIntentOperatorMutator
case 54173: {
galleryIntent = new android.content.Intent((String) null);
break;
}
// MainActivity_55_RandomActionIntentDefinitionOperatorMutator
case 55173: {
galleryIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
galleryIntent = new android.content.Intent(android.content.Intent.ACTION_PICK);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_56_NullValueIntentPutExtraOperatorMutator
case 56173: {
galleryIntent.putExtra(android.content.Intent.EXTRA_ALLOW_MULTIPLE, new Parcelable[0]);
break;
}
// MainActivity_57_IntentPayloadReplacementOperatorMutator
case 57173: {
galleryIntent.putExtra(android.content.Intent.EXTRA_ALLOW_MULTIPLE, true);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_58_RandomActionIntentDefinitionOperatorMutator
case 58173: {
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
galleryIntent.putExtra(android.content.Intent.EXTRA_ALLOW_MULTIPLE, true);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// MainActivity_59_RandomActionIntentDefinitionOperatorMutator
case 59173: {
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
galleryIntent.setDataAndType(android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
break;
}
}
android.content.Intent fileIntent;
switch(MUID_STATIC) {
// MainActivity_60_NullIntentOperatorMutator
case 60173: {
fileIntent = null;
break;
}
// MainActivity_61_InvalidKeyIntentOperatorMutator
case 61173: {
fileIntent = new android.content.Intent((String) null);
break;
}
// MainActivity_62_RandomActionIntentDefinitionOperatorMutator
case 62173: {
fileIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
fileIntent = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_63_NullValueIntentPutExtraOperatorMutator
case 63173: {
fileIntent.putExtra(android.content.Intent.EXTRA_ALLOW_MULTIPLE, new Parcelable[0]);
break;
}
// MainActivity_64_IntentPayloadReplacementOperatorMutator
case 64173: {
fileIntent.putExtra(android.content.Intent.EXTRA_ALLOW_MULTIPLE, true);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_65_RandomActionIntentDefinitionOperatorMutator
case 65173: {
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
fileIntent.putExtra(android.content.Intent.EXTRA_ALLOW_MULTIPLE, true);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// MainActivity_66_RandomActionIntentDefinitionOperatorMutator
case 66173: {
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
fileIntent.setType("image/*");
break;
}
}
android.content.Intent chooserIntent;
switch(MUID_STATIC) {
// MainActivity_67_RandomActionIntentDefinitionOperatorMutator
case 67173: {
chooserIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
chooserIntent = android.content.Intent.createChooser(galleryIntent, getString(com.beemdevelopment.aegis.R.string.select_picture));
break;
}
}
switch(MUID_STATIC) {
// MainActivity_68_NullValueIntentPutExtraOperatorMutator
case 68173: {
chooserIntent.putExtra(android.content.Intent.EXTRA_INITIAL_INTENTS, new Parcelable[0]);
break;
}
// MainActivity_69_IntentPayloadReplacementOperatorMutator
case 69173: {
chooserIntent.putExtra(android.content.Intent.EXTRA_INITIAL_INTENTS, (android.content.Intent[]) null);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_70_RandomActionIntentDefinitionOperatorMutator
case 70173: {
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
chooserIntent.putExtra(android.content.Intent.EXTRA_INITIAL_INTENTS, new android.content.Intent[]{ fileIntent });
break;
}
}
break;
}
}
_vaultManager.startActivityForResult(this, chooserIntent, com.beemdevelopment.aegis.ui.MainActivity.CODE_SCAN_IMAGE);
}


private void startPreferencesActivity() {
startPreferencesActivity(null, null);
}


private void startPreferencesActivity(java.lang.Class<? extends com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment> fragmentType, java.lang.String preference) {
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_71_NullIntentOperatorMutator
case 71173: {
intent = null;
break;
}
// MainActivity_72_InvalidKeyIntentOperatorMutator
case 72173: {
intent = new android.content.Intent((MainActivity) null, com.beemdevelopment.aegis.ui.PreferencesActivity.class);
break;
}
// MainActivity_73_RandomActionIntentDefinitionOperatorMutator
case 73173: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(this, com.beemdevelopment.aegis.ui.PreferencesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_74_NullValueIntentPutExtraOperatorMutator
case 74173: {
intent.putExtra("fragment", new Parcelable[0]);
break;
}
// MainActivity_75_IntentPayloadReplacementOperatorMutator
case 75173: {
intent.putExtra("fragment", (java.lang.Class<? extends com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment>) null);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_76_RandomActionIntentDefinitionOperatorMutator
case 76173: {
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
intent.putExtra("fragment", fragmentType);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// MainActivity_77_NullValueIntentPutExtraOperatorMutator
case 77173: {
intent.putExtra("pref", new Parcelable[0]);
break;
}
// MainActivity_78_IntentPayloadReplacementOperatorMutator
case 78173: {
intent.putExtra("pref", "");
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_79_RandomActionIntentDefinitionOperatorMutator
case 79173: {
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
intent.putExtra("pref", preference);
break;
}
}
break;
}
}
startActivityForResult(intent, com.beemdevelopment.aegis.ui.MainActivity.CODE_PREFERENCES);
}


private void doShortcutActions() {
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_80_RandomActionIntentDefinitionOperatorMutator
case 80173: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = getIntent();
break;
}
}
java.lang.String action;
action = intent.getStringExtra("action");
if ((action == null) || (!_vaultManager.isVaultLoaded())) {
return;
}
switch (action) {
case "scan" :
startScanActivity();
break;
}
intent.removeExtra("action");
}


private void handleIncomingIntent() {
if (!_vaultManager.isVaultLoaded()) {
return;
}
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_81_RandomActionIntentDefinitionOperatorMutator
case 81173: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = getIntent();
break;
}
}
if (intent.getAction() == null) {
return;
}
android.net.Uri uri;
switch (intent.getAction()) {
case android.content.Intent.ACTION_VIEW :
uri = intent.getData();
if (uri != null) {
switch(MUID_STATIC) {
// MainActivity_82_RandomActionIntentDefinitionOperatorMutator
case 82173: {
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
intent.setData(null);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_83_RandomActionIntentDefinitionOperatorMutator
case 83173: {
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
intent.setAction(null);
break;
}
}
com.beemdevelopment.aegis.otp.GoogleAuthInfo info;
try {
info = com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseUri(uri);
} catch (com.beemdevelopment.aegis.otp.GoogleAuthInfoException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.unable_to_process_deeplink, e);
break;
}
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = new com.beemdevelopment.aegis.vault.VaultEntry(info);
startEditEntryActivityForNew(com.beemdevelopment.aegis.ui.MainActivity.CODE_ADD_ENTRY, entry);
}
break;
case android.content.Intent.ACTION_SEND :
if (intent.hasExtra(android.content.Intent.EXTRA_STREAM)) {
uri = intent.getParcelableExtra(android.content.Intent.EXTRA_STREAM);
switch(MUID_STATIC) {
// MainActivity_84_RandomActionIntentDefinitionOperatorMutator
case 84173: {
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
intent.setAction(null);
break;
}
}
intent.removeExtra(android.content.Intent.EXTRA_STREAM);
if (uri != null) {
startDecodeQrCodeImages(java.util.Collections.singletonList(uri));
}
}
if (intent.hasExtra(android.content.Intent.EXTRA_TEXT)) {
java.lang.String stringExtra;
stringExtra = intent.getStringExtra(android.content.Intent.EXTRA_TEXT);
switch(MUID_STATIC) {
// MainActivity_85_RandomActionIntentDefinitionOperatorMutator
case 85173: {
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
intent.setAction(null);
break;
}
}
intent.removeExtra(android.content.Intent.EXTRA_TEXT);
if (stringExtra != null) {
com.beemdevelopment.aegis.otp.GoogleAuthInfo info;
try {
info = com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseUri(stringExtra);
} catch (com.beemdevelopment.aegis.otp.GoogleAuthInfoException e) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.unable_to_process_shared_text, e);
break;
}
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = new com.beemdevelopment.aegis.vault.VaultEntry(info);
startEditEntryActivityForNew(com.beemdevelopment.aegis.ui.MainActivity.CODE_ADD_ENTRY, entry);
}
}
break;
case android.content.Intent.ACTION_SEND_MULTIPLE :
if (intent.hasExtra(android.content.Intent.EXTRA_STREAM)) {
java.util.List<android.net.Uri> uris;
uris = intent.getParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM);
switch(MUID_STATIC) {
// MainActivity_86_RandomActionIntentDefinitionOperatorMutator
case 86173: {
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
intent.setAction(null);
break;
}
}
intent.removeExtra(android.content.Intent.EXTRA_STREAM);
if (uris != null) {
uris = uris.stream().filter(java.util.Objects::nonNull).collect(java.util.stream.Collectors.toList());
startDecodeQrCodeImages(uris);
}
}
break;
}
}


@java.lang.Override
protected void onResume() {
super.onResume();
if (_vaultManager.isVaultInitNeeded()) {
if (_prefs.isIntroDone()) {
android.widget.Toast.makeText(this, getString(com.beemdevelopment.aegis.R.string.vault_not_found), android.widget.Toast.LENGTH_SHORT).show();
}
startIntroActivity();
return;
}
if ((!_vaultManager.isVaultLoaded()) && (!_vaultManager.isVaultFileLoaded())) {
switch(MUID_STATIC) {
// MainActivity_87_BuggyGUIListenerOperatorMutator
case 87173: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.vault_load_error, _vaultManager.getVaultFileError(), null);
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.vault_load_error, _vaultManager.getVaultFileError(), (android.content.DialogInterface dialog1,int which) -> finish());
break;
}
}
return;
}
if (!_vaultManager.isVaultLoaded()) {
startAuthActivity(false);
} else if (_loaded) {
// update the list of groups in the entry list view so that the chip gets updated
_entryListView.setGroups(_vaultManager.getVault().getGroups());
// update the usage counts in case they are edited outside of the EntryListView
_entryListView.setUsageCounts(_prefs.getUsageCounts());
// refresh all codes to prevent showing old ones
_entryListView.refresh(false);
} else {
loadEntries();
checkTimeSyncSetting();
}
_lockBackPressHandler.setEnabled(_vaultManager.isAutoLockEnabled(com.beemdevelopment.aegis.Preferences.AUTO_LOCK_ON_BACK_BUTTON));
handleIncomingIntent();
updateLockIcon();
doShortcutActions();
updateErrorBar();
}


private void deleteEntries(java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> entries) {
for (com.beemdevelopment.aegis.vault.VaultEntry entry : entries) {
com.beemdevelopment.aegis.vault.VaultEntry oldEntry;
oldEntry = _vaultManager.getVault().removeEntry(entry);
_entryListView.removeEntry(oldEntry);
}
saveAndBackupVault();
}


@java.lang.Override
public boolean onCreateOptionsMenu(android.view.Menu menu) {
_menu = menu;
getMenuInflater().inflate(com.beemdevelopment.aegis.R.menu.menu_main, menu);
updateLockIcon();
if (_loaded) {
_entryListView.setGroups(_vaultManager.getVault().getGroups());
updateSortCategoryMenu();
}
android.view.MenuItem searchViewMenuItem;
searchViewMenuItem = menu.findItem(com.beemdevelopment.aegis.R.id.mi_search);
_searchView = ((androidx.appcompat.widget.SearchView) (searchViewMenuItem.getActionView()));
_searchView.setMaxWidth(java.lang.Integer.MAX_VALUE);
_searchView.setOnQueryTextFocusChangeListener((android.view.View v,boolean hasFocus) -> {
boolean enabled;
enabled = (_submittedSearchQuery != null) || hasFocus;
_searchViewBackPressHandler.setEnabled(enabled);
});
_searchView.setOnCloseListener(() -> {
boolean enabled;
enabled = _submittedSearchQuery != null;
_searchViewBackPressHandler.setEnabled(enabled);
return false;
});
_searchView.setQueryHint(getString(com.beemdevelopment.aegis.R.string.search));
_searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
@java.lang.Override
public boolean onQueryTextSubmit(java.lang.String s) {
setTitle(getString(com.beemdevelopment.aegis.R.string.search));
getSupportActionBar().setSubtitle(s);
_entryListView.setSearchFilter(s);
_pendingSearchQuery = null;
_submittedSearchQuery = s;
collapseSearchView();
_searchViewBackPressHandler.setEnabled(true);
return false;
}


@java.lang.Override
public boolean onQueryTextChange(java.lang.String s) {
if (_submittedSearchQuery == null) {
_entryListView.setSearchFilter(s);
}
_pendingSearchQuery = (com.google.common.base.Strings.isNullOrEmpty(s) && (!_searchView.isIconified())) ? null : s;
if (_pendingSearchQuery != null) {
_entryListView.setSearchFilter(_pendingSearchQuery);
}
return false;
}

});
switch(MUID_STATIC) {
// MainActivity_88_BuggyGUIListenerOperatorMutator
case 88173: {
_searchView.setOnSearchClickListener(null);
break;
}
default: {
_searchView.setOnSearchClickListener((android.view.View v) -> {
java.lang.String query;
query = (_submittedSearchQuery != null) ? _submittedSearchQuery : _pendingSearchQuery;
_searchView.setQuery(query, false);
});
break;
}
}
if (_pendingSearchQuery != null) {
_searchView.setIconified(false);
_searchView.setQuery(_pendingSearchQuery, false);
_searchViewBackPressHandler.setEnabled(true);
} else if (_submittedSearchQuery != null) {
setTitle(getString(com.beemdevelopment.aegis.R.string.search));
getSupportActionBar().setSubtitle(_submittedSearchQuery);
_entryListView.setSearchFilter(_submittedSearchQuery);
_searchViewBackPressHandler.setEnabled(true);
} else if (_prefs.getFocusSearchEnabled() && (!_isRecreated)) {
_searchView.setIconified(false);
_searchView.setFocusable(true);
_searchView.requestFocus();
_searchView.requestFocusFromTouch();
}
return true;
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
switch (item.getItemId()) {
case com.beemdevelopment.aegis.R.id.action_settings :
{
startPreferencesActivity();
return true;
}
case com.beemdevelopment.aegis.R.id.action_about :
{
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_89_NullIntentOperatorMutator
case 89173: {
intent = null;
break;
}
// MainActivity_90_InvalidKeyIntentOperatorMutator
case 90173: {
intent = new android.content.Intent((MainActivity) null, com.beemdevelopment.aegis.ui.AboutActivity.class);
break;
}
// MainActivity_91_RandomActionIntentDefinitionOperatorMutator
case 91173: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(this, com.beemdevelopment.aegis.ui.AboutActivity.class);
break;
}
}
startActivity(intent);
return true;
}
case com.beemdevelopment.aegis.R.id.action_lock :
_vaultManager.lock(true);
return true;
default :
if (item.getGroupId() == com.beemdevelopment.aegis.R.id.action_sort_category) {
item.setChecked(true);
com.beemdevelopment.aegis.SortCategory sortCategory;
switch (item.getItemId()) {
case com.beemdevelopment.aegis.R.id.menu_sort_alphabetically :
sortCategory = com.beemdevelopment.aegis.SortCategory.ISSUER;
break;
case com.beemdevelopment.aegis.R.id.menu_sort_alphabetically_reverse :
sortCategory = com.beemdevelopment.aegis.SortCategory.ISSUER_REVERSED;
break;
case com.beemdevelopment.aegis.R.id.menu_sort_alphabetically_name :
sortCategory = com.beemdevelopment.aegis.SortCategory.ACCOUNT;
break;
case com.beemdevelopment.aegis.R.id.menu_sort_alphabetically_name_reverse :
sortCategory = com.beemdevelopment.aegis.SortCategory.ACCOUNT_REVERSED;
break;
case com.beemdevelopment.aegis.R.id.menu_sort_usage_count :
sortCategory = com.beemdevelopment.aegis.SortCategory.USAGE_COUNT;
break;
case com.beemdevelopment.aegis.R.id.menu_sort_custom :
default :
sortCategory = com.beemdevelopment.aegis.SortCategory.CUSTOM;
break;
}
_entryListView.setSortCategory(sortCategory, true);
_prefs.setCurrentSortCategory(sortCategory);
}
return super.onOptionsItemSelected(item);
}
}


private void collapseSearchView() {
_searchView.setQuery(null, false);
_searchView.setIconified(true);
}


private void loadEntries() {
if (!_loaded) {
_entryListView.setUsageCounts(_prefs.getUsageCounts());
_entryListView.addEntries(_vaultManager.getVault().getEntries());
_entryListView.runEntriesAnimation();
_loaded = true;
}
}


private void startAuthActivity(boolean inhibitBioPrompt) {
if (!_isAuthenticating) {
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_92_NullIntentOperatorMutator
case 92173: {
intent = null;
break;
}
// MainActivity_93_InvalidKeyIntentOperatorMutator
case 93173: {
intent = new android.content.Intent((MainActivity) null, com.beemdevelopment.aegis.ui.AuthActivity.class);
break;
}
// MainActivity_94_RandomActionIntentDefinitionOperatorMutator
case 94173: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(this, com.beemdevelopment.aegis.ui.AuthActivity.class);
break;
}
}
switch(MUID_STATIC) {
// MainActivity_95_NullValueIntentPutExtraOperatorMutator
case 95173: {
intent.putExtra("inhibitBioPrompt", new Parcelable[0]);
break;
}
// MainActivity_96_IntentPayloadReplacementOperatorMutator
case 96173: {
intent.putExtra("inhibitBioPrompt", true);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_97_RandomActionIntentDefinitionOperatorMutator
case 97173: {
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
intent.putExtra("inhibitBioPrompt", inhibitBioPrompt);
break;
}
}
break;
}
}
startActivityForResult(intent, com.beemdevelopment.aegis.ui.MainActivity.CODE_DECRYPT);
_isAuthenticating = true;
}
}


private void updateLockIcon() {
// hide the lock icon if the vault is not unlocked
if ((_menu != null) && _vaultManager.isVaultLoaded()) {
android.view.MenuItem item;
item = _menu.findItem(com.beemdevelopment.aegis.R.id.action_lock);
item.setVisible(_vaultManager.getVault().isEncryptionEnabled());
}
}


private void updateErrorBar() {
com.beemdevelopment.aegis.Preferences.BackupResult backupRes;
backupRes = _prefs.getErroredBackupResult();
if (backupRes != null) {
_textErrorBar.setText(com.beemdevelopment.aegis.R.string.backup_error_bar_message);
switch(MUID_STATIC) {
// MainActivity_98_BuggyGUIListenerOperatorMutator
case 98173: {
_btnErrorBar.setOnClickListener(null);
break;
}
default: {
_btnErrorBar.setOnClickListener((android.view.View view) -> {
switch(MUID_STATIC) {
// MainActivity_99_BuggyGUIListenerOperatorMutator
case 99173: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showBackupErrorDialog(this, backupRes, null);
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showBackupErrorDialog(this, backupRes, (android.content.DialogInterface dialog,int which) -> {
startPreferencesActivity(com.beemdevelopment.aegis.ui.fragments.preferences.BackupsPreferencesFragment.class, "pref_backups");
});
break;
}
}
});
break;
}
}
_btnErrorBar.setVisibility(android.view.View.VISIBLE);
} else if (_prefs.isBackupsReminderNeeded() && _prefs.isBackupReminderEnabled()) {
java.util.Date date;
date = _prefs.getLatestBackupOrExportTime();
if (date != null) {
_textErrorBar.setText(getString(com.beemdevelopment.aegis.R.string.backup_reminder_bar_message_with_latest, com.beemdevelopment.aegis.util.TimeUtils.getElapsedSince(this, date)));
} else {
_textErrorBar.setText(com.beemdevelopment.aegis.R.string.backup_reminder_bar_message);
}
switch(MUID_STATIC) {
// MainActivity_100_BuggyGUIListenerOperatorMutator
case 100173: {
_btnErrorBar.setOnClickListener(null);
break;
}
default: {
_btnErrorBar.setOnClickListener((android.view.View view) -> {
switch(MUID_STATIC) {
// MainActivity_101_BuggyGUIListenerOperatorMutator
case 101173: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(com.beemdevelopment.aegis.R.string.backup_reminder_bar_dialog_title).setMessage(com.beemdevelopment.aegis.R.string.backup_reminder_bar_dialog_summary).setPositiveButton(com.beemdevelopment.aegis.R.string.backup_reminder_bar_dialog_accept, null).setNegativeButton(android.R.string.cancel, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(com.beemdevelopment.aegis.R.string.backup_reminder_bar_dialog_title).setMessage(com.beemdevelopment.aegis.R.string.backup_reminder_bar_dialog_summary).setPositiveButton(com.beemdevelopment.aegis.R.string.backup_reminder_bar_dialog_accept, (android.content.DialogInterface dialog,int whichButton) -> {
startPreferencesActivity(com.beemdevelopment.aegis.ui.fragments.preferences.BackupsPreferencesFragment.class, "pref_backups");
}).setNegativeButton(android.R.string.cancel, null).create());
break;
}
}
});
break;
}
}
_btnErrorBar.setVisibility(android.view.View.VISIBLE);
} else if (_prefs.isPlaintextBackupWarningNeeded()) {
_textErrorBar.setText(com.beemdevelopment.aegis.R.string.backup_plaintext_export_warning);
switch(MUID_STATIC) {
// MainActivity_102_BuggyGUIListenerOperatorMutator
case 102173: {
_btnErrorBar.setOnClickListener(null);
break;
}
default: {
_btnErrorBar.setOnClickListener((android.view.View view) -> showPlaintextExportWarningOptions());
break;
}
}
_btnErrorBar.setVisibility(android.view.View.VISIBLE);
} else {
_btnErrorBar.setVisibility(android.view.View.GONE);
}
}


private void showPlaintextExportWarningOptions() {
android.view.View view;
view = android.view.LayoutInflater.from(this).inflate(com.beemdevelopment.aegis.R.layout.dialog_plaintext_warning, null);
androidx.appcompat.app.AlertDialog dialog;
dialog = new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(com.beemdevelopment.aegis.R.string.backup_plaintext_export_warning).setView(view).setPositiveButton(android.R.string.ok, null).setNegativeButton(android.R.string.cancel, null).create();
android.widget.CheckBox checkBox;
switch(MUID_STATIC) {
// MainActivity_103_InvalidViewFocusOperatorMutator
case 103173: {
/**
* Inserted by Kadabra
*/
checkBox = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_plaintext_warning);
checkBox.requestFocus();
break;
}
// MainActivity_104_ViewComponentNotVisibleOperatorMutator
case 104173: {
/**
* Inserted by Kadabra
*/
checkBox = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_plaintext_warning);
checkBox.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
checkBox = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_plaintext_warning);
break;
}
}
checkBox.setChecked(false);
dialog.setOnShowListener((android.content.DialogInterface d) -> {
android.widget.Button btnPos;
btnPos = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
switch(MUID_STATIC) {
// MainActivity_105_BuggyGUIListenerOperatorMutator
case 105173: {
btnPos.setOnClickListener(null);
break;
}
default: {
btnPos.setOnClickListener((android.view.View l) -> {
dialog.dismiss();
_prefs.setIsPlaintextBackupWarningDisabled(checkBox.isChecked());
_prefs.setIsPlaintextBackupWarningNeeded(false);
updateErrorBar();
});
break;
}
}
});
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


@java.lang.Override
public void onEntryClick(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (_actionMode != null) {
if (_selectedEntries.isEmpty()) {
_actionMode.finish();
} else {
setFavoriteMenuItemVisiblity();
setIsMultipleSelected(_selectedEntries.size() > 1);
}
return;
}
}


@java.lang.Override
public void onSelect(com.beemdevelopment.aegis.vault.VaultEntry entry) {
_selectedEntries.add(entry);
}


@java.lang.Override
public void onDeselect(com.beemdevelopment.aegis.vault.VaultEntry entry) {
_selectedEntries.remove(entry);
}


private void setIsMultipleSelected(boolean multipleSelected) {
_entryListView.setIsLongPressDragEnabled(!multipleSelected);
_actionMode.getMenu().findItem(com.beemdevelopment.aegis.R.id.action_edit).setVisible(!multipleSelected);
_actionMode.getMenu().findItem(com.beemdevelopment.aegis.R.id.action_copy).setVisible(!multipleSelected);
}


private void setFavoriteMenuItemVisiblity() {
android.view.MenuItem toggleFavoriteMenuItem;
toggleFavoriteMenuItem = _actionMode.getMenu().findItem(com.beemdevelopment.aegis.R.id.action_toggle_favorite);
if (_selectedEntries.size() == 1) {
if (_selectedEntries.get(0).isFavorite()) {
toggleFavoriteMenuItem.setIcon(com.beemdevelopment.aegis.R.drawable.ic_set_favorite);
toggleFavoriteMenuItem.setTitle(com.beemdevelopment.aegis.R.string.unfavorite);
} else {
toggleFavoriteMenuItem.setIcon(com.beemdevelopment.aegis.R.drawable.ic_unset_favorite);
toggleFavoriteMenuItem.setTitle(com.beemdevelopment.aegis.R.string.favorite);
}
} else {
toggleFavoriteMenuItem.setIcon(com.beemdevelopment.aegis.R.drawable.ic_unset_favorite);
toggleFavoriteMenuItem.setTitle(java.lang.String.format("%s / %s", getString(com.beemdevelopment.aegis.R.string.favorite), getString(com.beemdevelopment.aegis.R.string.unfavorite)));
}
}


@java.lang.Override
public void onLongEntryClick(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (!_selectedEntries.isEmpty()) {
return;
}
_selectedEntries.add(entry);
_entryListView.setActionModeState(true, entry);
startActionMode();
}


private void startActionMode() {
_actionMode = startSupportActionMode(_actionModeCallbacks);
_actionModeBackPressHandler.setEnabled(true);
setFavoriteMenuItemVisiblity();
}


@java.lang.Override
public void onEntryMove(com.beemdevelopment.aegis.vault.VaultEntry entry1, com.beemdevelopment.aegis.vault.VaultEntry entry2) {
_vaultManager.getVault().swapEntries(entry1, entry2);
}


@java.lang.Override
public void onEntryDrop(com.beemdevelopment.aegis.vault.VaultEntry entry) {
saveVault();
}


@java.lang.Override
public void onEntryChange(com.beemdevelopment.aegis.vault.VaultEntry entry) {
saveAndBackupVault();
}


public void onEntryCopy(com.beemdevelopment.aegis.vault.VaultEntry entry) {
copyEntryCode(entry);
}


@java.lang.Override
public void onScroll(int dx, int dy) {
if (!_isDPadPressed) {
_fabScrollHelper.onScroll(dx, dy);
}
}


@java.lang.Override
public void onListChange() {
_fabScrollHelper.setVisible(true);
}


@java.lang.Override
public void onSaveGroupFilter(java.util.List<java.lang.String> groupFilter) {
_prefs.setGroupFilter(groupFilter);
}


@java.lang.Override
public void onLocked(boolean userInitiated) {
if (_actionMode != null) {
_actionMode.finish();
}
if ((_searchView != null) && (!_searchView.isIconified())) {
collapseSearchView();
}
_entryListView.clearEntries();
_loaded = false;
if (userInitiated) {
startAuthActivity(true);
} else {
super.onLocked(false);
}
}


@android.annotation.SuppressLint("InlinedApi")
private void copyEntryCode(com.beemdevelopment.aegis.vault.VaultEntry entry) {
java.lang.String otp;
try {
otp = entry.getInfo().getOtp();
} catch (com.beemdevelopment.aegis.otp.OtpInfoException e) {
return;
}
android.content.ClipboardManager clipboard;
clipboard = ((android.content.ClipboardManager) (getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
android.content.ClipData clip;
clip = android.content.ClipData.newPlainText("text/plain", otp);
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
android.os.PersistableBundle extras;
extras = new android.os.PersistableBundle();
extras.putBoolean(android.content.ClipDescription.EXTRA_IS_SENSITIVE, true);
clip.getDescription().setExtras(extras);
}
clipboard.setPrimaryClip(clip);
if (_prefs.isMinimizeOnCopyEnabled()) {
moveTaskToBack(true);
}
}


private class SearchViewBackPressHandler extends androidx.activity.OnBackPressedCallback {
public SearchViewBackPressHandler() {
super(false);
}


@java.lang.Override
public void handleOnBackPressed() {
if ((!_searchView.isIconified()) || (_submittedSearchQuery != null)) {
_submittedSearchQuery = null;
_pendingSearchQuery = null;
_entryListView.setSearchFilter(null);
collapseSearchView();
setTitle(com.beemdevelopment.aegis.R.string.app_name);
getSupportActionBar().setSubtitle(null);
}
}

}

private class LockBackPressHandler extends androidx.activity.OnBackPressedCallback {
public LockBackPressHandler() {
super(false);
}


@java.lang.Override
public void handleOnBackPressed() {
if (_vaultManager.isAutoLockEnabled(com.beemdevelopment.aegis.Preferences.AUTO_LOCK_ON_BACK_BUTTON)) {
_vaultManager.lock(false);
}
}

}

private class ActionModeBackPressHandler extends androidx.activity.OnBackPressedCallback {
public ActionModeBackPressHandler() {
super(false);
}


@java.lang.Override
public void handleOnBackPressed() {
if (_actionMode != null) {
_actionMode.finish();
}
}

}

private class ActionModeCallbacks implements androidx.appcompat.view.ActionMode.Callback {
@java.lang.Override
public boolean onCreateActionMode(androidx.appcompat.view.ActionMode mode, android.view.Menu menu) {
android.view.MenuInflater inflater;
inflater = getMenuInflater();
inflater.inflate(com.beemdevelopment.aegis.R.menu.menu_action_mode, menu);
return true;
}


@java.lang.Override
public boolean onPrepareActionMode(androidx.appcompat.view.ActionMode mode, android.view.Menu menu) {
return false;
}


@java.lang.Override
public boolean onActionItemClicked(androidx.appcompat.view.ActionMode mode, android.view.MenuItem item) {
if (_selectedEntries.size() == 0) {
mode.finish();
return true;
}
switch (item.getItemId()) {
case com.beemdevelopment.aegis.R.id.action_copy :
copyEntryCode(_selectedEntries.get(0));
mode.finish();
return true;
case com.beemdevelopment.aegis.R.id.action_edit :
startEditEntryActivity(com.beemdevelopment.aegis.ui.MainActivity.CODE_EDIT_ENTRY, _selectedEntries.get(0));
mode.finish();
return true;
case com.beemdevelopment.aegis.R.id.action_toggle_favorite :
for (com.beemdevelopment.aegis.vault.VaultEntry entry : _selectedEntries) {
entry.setIsFavorite(!entry.isFavorite());
_entryListView.replaceEntry(entry.getUUID(), entry);
}
_entryListView.refresh(true);
saveAndBackupVault();
mode.finish();
return true;
case com.beemdevelopment.aegis.R.id.action_share_qr :
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivity_106_NullIntentOperatorMutator
case 106173: {
intent = null;
break;
}
// MainActivity_107_InvalidKeyIntentOperatorMutator
case 107173: {
intent = new android.content.Intent((android.content.Context) null, com.beemdevelopment.aegis.ui.TransferEntriesActivity.class);
break;
}
// MainActivity_108_RandomActionIntentDefinitionOperatorMutator
case 108173: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(getBaseContext(), com.beemdevelopment.aegis.ui.TransferEntriesActivity.class);
break;
}
}
java.util.ArrayList<com.beemdevelopment.aegis.otp.GoogleAuthInfo> authInfos;
authInfos = new java.util.ArrayList<>();
for (com.beemdevelopment.aegis.vault.VaultEntry entry : _selectedEntries) {
com.beemdevelopment.aegis.otp.GoogleAuthInfo authInfo;
authInfo = new com.beemdevelopment.aegis.otp.GoogleAuthInfo(entry.getInfo(), entry.getName(), entry.getIssuer());
authInfos.add(authInfo);
}
switch(MUID_STATIC) {
// MainActivity_109_NullValueIntentPutExtraOperatorMutator
case 109173: {
intent.putExtra("authInfos", new Parcelable[0]);
break;
}
// MainActivity_110_IntentPayloadReplacementOperatorMutator
case 110173: {
intent.putExtra("authInfos", (java.util.ArrayList<com.beemdevelopment.aegis.otp.GoogleAuthInfo>) null);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivity_111_RandomActionIntentDefinitionOperatorMutator
case 111173: {
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
intent.putExtra("authInfos", authInfos);
break;
}
}
break;
}
}
startActivity(intent);
mode.finish();
return true;
case com.beemdevelopment.aegis.R.id.action_delete :
switch(MUID_STATIC) {
// MainActivity_112_BuggyGUIListenerOperatorMutator
case 112173: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDeleteEntriesDialog(com.beemdevelopment.aegis.ui.MainActivity.this, _selectedEntries, null);
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDeleteEntriesDialog(com.beemdevelopment.aegis.ui.MainActivity.this, _selectedEntries, (android.content.DialogInterface d,int which) -> {
deleteEntries(_selectedEntries);
for (com.beemdevelopment.aegis.vault.VaultEntry entry : _selectedEntries) {
if (entry.getGroup() != null) {
java.util.TreeSet<java.lang.String> groups;
groups = _vaultManager.getVault().getGroups();
if (!groups.contains(entry.getGroup())) {
_entryListView.setGroups(groups);
break;
}
}
}
mode.finish();
});
break;
}
}
return true;
default :
return false;
}
}


@java.lang.Override
public void onDestroyActionMode(androidx.appcompat.view.ActionMode mode) {
_entryListView.setActionModeState(false, null);
_actionModeBackPressHandler.setEnabled(false);
_selectedEntries.clear();
_actionMode = null;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
