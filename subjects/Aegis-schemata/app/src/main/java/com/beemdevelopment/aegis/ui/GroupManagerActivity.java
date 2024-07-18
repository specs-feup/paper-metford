package com.beemdevelopment.aegis.ui;
import androidx.appcompat.app.AlertDialog;
import android.view.Menu;
import android.os.Bundle;
import java.util.ArrayList;
import android.view.MenuItem;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.ui.views.GroupAdapter;
import androidx.annotation.NonNull;
import androidx.activity.OnBackPressedCallback;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.util.Objects;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.HashSet;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GroupManagerActivity extends com.beemdevelopment.aegis.ui.AegisActivity implements com.beemdevelopment.aegis.ui.views.GroupAdapter.Listener {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.ui.views.GroupAdapter _adapter;

    private java.util.HashSet<java.lang.String> _removedGroups;

    private androidx.recyclerview.widget.RecyclerView _slotsView;

    private android.view.View _emptyStateView;

    private com.beemdevelopment.aegis.ui.GroupManagerActivity.BackPressHandler _backPressHandler;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // GroupManagerActivity_0_LengthyGUICreationOperatorMutator
            case 169: {
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
    setContentView(com.beemdevelopment.aegis.R.layout.activity_groups);
    switch(MUID_STATIC) {
        // GroupManagerActivity_1_InvalidIDFindViewOperatorMutator
        case 1169: {
            setSupportActionBar(findViewById(732221));
            break;
        }
        default: {
        setSupportActionBar(findViewById(com.beemdevelopment.aegis.R.id.toolbar));
        break;
    }
}
if (getSupportActionBar() != null) {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
}
_backPressHandler = new com.beemdevelopment.aegis.ui.GroupManagerActivity.BackPressHandler();
getOnBackPressedDispatcher().addCallback(this, _backPressHandler);
if (savedInstanceState != null) {
    java.util.List<java.lang.String> groups;
    groups = savedInstanceState.getStringArrayList("removedGroups");
    _removedGroups = new java.util.HashSet<>(java.util.Objects.requireNonNull(groups));
} else {
    _removedGroups = new java.util.HashSet<>();
}
_adapter = new com.beemdevelopment.aegis.ui.views.GroupAdapter(this);
switch(MUID_STATIC) {
    // GroupManagerActivity_2_FindViewByIdReturnsNullOperatorMutator
    case 2169: {
        _slotsView = null;
        break;
    }
    // GroupManagerActivity_3_InvalidIDFindViewOperatorMutator
    case 3169: {
        _slotsView = findViewById(732221);
        break;
    }
    // GroupManagerActivity_4_InvalidViewFocusOperatorMutator
    case 4169: {
        /**
        * Inserted by Kadabra
        */
        _slotsView = findViewById(com.beemdevelopment.aegis.R.id.list_slots);
        _slotsView.requestFocus();
        break;
    }
    // GroupManagerActivity_5_ViewComponentNotVisibleOperatorMutator
    case 5169: {
        /**
        * Inserted by Kadabra
        */
        _slotsView = findViewById(com.beemdevelopment.aegis.R.id.list_slots);
        _slotsView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    _slotsView = findViewById(com.beemdevelopment.aegis.R.id.list_slots);
    break;
}
}
androidx.recyclerview.widget.LinearLayoutManager layoutManager;
layoutManager = new androidx.recyclerview.widget.LinearLayoutManager(this);
_slotsView.setLayoutManager(layoutManager);
_slotsView.setAdapter(_adapter);
_slotsView.setNestedScrollingEnabled(false);
for (java.lang.String group : _vaultManager.getVault().getGroups()) {
_adapter.addGroup(group);
}
switch(MUID_STATIC) {
// GroupManagerActivity_6_FindViewByIdReturnsNullOperatorMutator
case 6169: {
    _emptyStateView = null;
    break;
}
// GroupManagerActivity_7_InvalidIDFindViewOperatorMutator
case 7169: {
    _emptyStateView = findViewById(732221);
    break;
}
// GroupManagerActivity_8_InvalidViewFocusOperatorMutator
case 8169: {
    /**
    * Inserted by Kadabra
    */
    _emptyStateView = findViewById(com.beemdevelopment.aegis.R.id.vEmptyList);
    _emptyStateView.requestFocus();
    break;
}
// GroupManagerActivity_9_ViewComponentNotVisibleOperatorMutator
case 9169: {
    /**
    * Inserted by Kadabra
    */
    _emptyStateView = findViewById(com.beemdevelopment.aegis.R.id.vEmptyList);
    _emptyStateView.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
_emptyStateView = findViewById(com.beemdevelopment.aegis.R.id.vEmptyList);
break;
}
}
updateEmptyState();
}


@java.lang.Override
protected void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
outState.putStringArrayList("removedGroups", new java.util.ArrayList<>(_removedGroups));
}


@java.lang.Override
public void onRemoveGroup(java.lang.String group) {
switch(MUID_STATIC) {
// GroupManagerActivity_10_BuggyGUIListenerOperatorMutator
case 10169: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(com.beemdevelopment.aegis.R.string.remove_group).setMessage(com.beemdevelopment.aegis.R.string.remove_group_description).setPositiveButton(android.R.string.yes, null).setNegativeButton(android.R.string.no, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(com.beemdevelopment.aegis.R.string.remove_group).setMessage(com.beemdevelopment.aegis.R.string.remove_group_description).setPositiveButton(android.R.string.yes, (android.content.DialogInterface dialog,int whichButton) -> {
_removedGroups.add(group);
_adapter.removeGroup(group);
_backPressHandler.setEnabled(true);
updateEmptyState();
}).setNegativeButton(android.R.string.no, null).create());
break;
}
}
}


private void saveAndFinish() {
if (!_removedGroups.isEmpty()) {
for (com.beemdevelopment.aegis.vault.VaultEntry entry : _vaultManager.getVault().getEntries()) {
if (_removedGroups.contains(entry.getGroup())) {
entry.setGroup(null);
}
}
saveAndBackupVault();
}
finish();
}


private void discardAndFinish() {
if (_removedGroups.isEmpty()) {
finish();
return;
}
switch(MUID_STATIC) {
// GroupManagerActivity_11_BuggyGUIListenerOperatorMutator
case 11169: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDiscardDialog(this, null, (android.content.DialogInterface dialog,int which) -> finish());
break;
}
default: {
switch(MUID_STATIC) {
// GroupManagerActivity_12_BuggyGUIListenerOperatorMutator
case 12169: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDiscardDialog(this, (android.content.DialogInterface dialog,int which) -> saveAndFinish(), null);
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDiscardDialog(this, (android.content.DialogInterface dialog,int which) -> saveAndFinish(), (android.content.DialogInterface dialog,int which) -> finish());
break;
}
}
break;
}
}
}


@java.lang.Override
public boolean onCreateOptionsMenu(android.view.Menu menu) {
getMenuInflater().inflate(com.beemdevelopment.aegis.R.menu.menu_groups, menu);
return true;
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
switch (item.getItemId()) {
case android.R.id.home :
discardAndFinish();
break;
case com.beemdevelopment.aegis.R.id.action_save :
saveAndFinish();
break;
default :
return super.onOptionsItemSelected(item);
}
return true;
}


private void updateEmptyState() {
if (_adapter.getItemCount() > 0) {
_slotsView.setVisibility(android.view.View.VISIBLE);
_emptyStateView.setVisibility(android.view.View.GONE);
} else {
_slotsView.setVisibility(android.view.View.GONE);
_emptyStateView.setVisibility(android.view.View.VISIBLE);
}
}


private class BackPressHandler extends androidx.activity.OnBackPressedCallback {
public BackPressHandler() {
super(false);
}


@java.lang.Override
public void handleOnBackPressed() {
discardAndFinish();
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
