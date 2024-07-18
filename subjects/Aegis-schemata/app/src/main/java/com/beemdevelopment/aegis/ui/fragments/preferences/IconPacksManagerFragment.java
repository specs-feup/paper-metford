package com.beemdevelopment.aegis.ui.fragments.preferences;
import androidx.appcompat.app.AlertDialog;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.beemdevelopment.aegis.icons.IconPackExistsException;
import com.beemdevelopment.aegis.icons.IconPack;
import com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.TextView;
import com.beemdevelopment.aegis.ui.views.IconPackAdapter;
import android.widget.LinearLayout;
import com.beemdevelopment.aegis.icons.IconPackManager;
import javax.inject.Inject;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.content.Intent;
import android.view.View;
import com.beemdevelopment.aegis.icons.IconPackException;
import com.beemdevelopment.aegis.vault.VaultManager;
import com.beemdevelopment.aegis.helpers.FabScrollHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@dagger.hilt.android.AndroidEntryPoint
public class IconPacksManagerFragment extends androidx.fragment.app.Fragment implements com.beemdevelopment.aegis.ui.views.IconPackAdapter.Listener {
    static final int MUID_STATIC = getMUID();
    private static final int CODE_IMPORT = 0;

    @javax.inject.Inject
    com.beemdevelopment.aegis.icons.IconPackManager _iconPackManager;

    @javax.inject.Inject
    com.beemdevelopment.aegis.vault.VaultManager _vaultManager;

    private android.view.View _iconPacksView;

    private androidx.recyclerview.widget.RecyclerView _iconPacksRecyclerView;

    private com.beemdevelopment.aegis.ui.views.IconPackAdapter _adapter;

    private android.widget.LinearLayout _noIconPacksView;

    private com.beemdevelopment.aegis.helpers.FabScrollHelper _fabScrollHelper;

    public IconPacksManagerFragment() {
        super(com.beemdevelopment.aegis.R.layout.fragment_icon_packs);
    }


    @java.lang.Override
    public void onViewCreated(@androidx.annotation.NonNull
    android.view.View view, android.os.Bundle savedInstanceState) {
        com.google.android.material.floatingactionbutton.FloatingActionButton fab;
        switch(MUID_STATIC) {
            // IconPacksManagerFragment_0_InvalidViewFocusOperatorMutator
            case 134: {
                /**
                * Inserted by Kadabra
                */
                fab = view.findViewById(com.beemdevelopment.aegis.R.id.fab);
                fab.requestFocus();
                break;
            }
            // IconPacksManagerFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1134: {
                /**
                * Inserted by Kadabra
                */
                fab = view.findViewById(com.beemdevelopment.aegis.R.id.fab);
                fab.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            fab = view.findViewById(com.beemdevelopment.aegis.R.id.fab);
            break;
        }
    }
    switch(MUID_STATIC) {
        // IconPacksManagerFragment_2_BuggyGUIListenerOperatorMutator
        case 2134: {
            fab.setOnClickListener(null);
            break;
        }
        default: {
        fab.setOnClickListener((android.view.View v) -> startImportIconPack());
        break;
    }
}
_fabScrollHelper = new com.beemdevelopment.aegis.helpers.FabScrollHelper(fab);
switch(MUID_STATIC) {
    // IconPacksManagerFragment_3_InvalidViewFocusOperatorMutator
    case 3134: {
        /**
        * Inserted by Kadabra
        */
        _noIconPacksView = view.findViewById(com.beemdevelopment.aegis.R.id.vEmptyList);
        _noIconPacksView.requestFocus();
        break;
    }
    // IconPacksManagerFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4134: {
        /**
        * Inserted by Kadabra
        */
        _noIconPacksView = view.findViewById(com.beemdevelopment.aegis.R.id.vEmptyList);
        _noIconPacksView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    _noIconPacksView = view.findViewById(com.beemdevelopment.aegis.R.id.vEmptyList);
    break;
}
}
((android.widget.TextView) (view.findViewById(com.beemdevelopment.aegis.R.id.txt_no_icon_packs))).setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
switch(MUID_STATIC) {
// IconPacksManagerFragment_5_InvalidViewFocusOperatorMutator
case 5134: {
    /**
    * Inserted by Kadabra
    */
    _iconPacksView = view.findViewById(com.beemdevelopment.aegis.R.id.view_icon_packs);
    _iconPacksView.requestFocus();
    break;
}
// IconPacksManagerFragment_6_ViewComponentNotVisibleOperatorMutator
case 6134: {
    /**
    * Inserted by Kadabra
    */
    _iconPacksView = view.findViewById(com.beemdevelopment.aegis.R.id.view_icon_packs);
    _iconPacksView.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
_iconPacksView = view.findViewById(com.beemdevelopment.aegis.R.id.view_icon_packs);
break;
}
}
_adapter = new com.beemdevelopment.aegis.ui.views.IconPackAdapter(this);
switch(MUID_STATIC) {
// IconPacksManagerFragment_7_InvalidViewFocusOperatorMutator
case 7134: {
/**
* Inserted by Kadabra
*/
_iconPacksRecyclerView = view.findViewById(com.beemdevelopment.aegis.R.id.list_icon_packs);
_iconPacksRecyclerView.requestFocus();
break;
}
// IconPacksManagerFragment_8_ViewComponentNotVisibleOperatorMutator
case 8134: {
/**
* Inserted by Kadabra
*/
_iconPacksRecyclerView = view.findViewById(com.beemdevelopment.aegis.R.id.list_icon_packs);
_iconPacksRecyclerView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_iconPacksRecyclerView = view.findViewById(com.beemdevelopment.aegis.R.id.list_icon_packs);
break;
}
}
androidx.recyclerview.widget.LinearLayoutManager layoutManager;
layoutManager = new androidx.recyclerview.widget.LinearLayoutManager(requireContext());
_iconPacksRecyclerView.setLayoutManager(layoutManager);
_iconPacksRecyclerView.setAdapter(_adapter);
_iconPacksRecyclerView.setNestedScrollingEnabled(false);
_iconPacksRecyclerView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
@java.lang.Override
public void onScrolled(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, int dx, int dy) {
super.onScrolled(recyclerView, dx, dy);
_fabScrollHelper.onScroll(dx, dy);
}

});
for (com.beemdevelopment.aegis.icons.IconPack pack : _iconPackManager.getIconPacks()) {
_adapter.addIconPack(pack);
}
updateEmptyState();
}


@java.lang.Override
public void onRemoveIconPack(com.beemdevelopment.aegis.icons.IconPack pack) {
switch(MUID_STATIC) {
// IconPacksManagerFragment_9_BuggyGUIListenerOperatorMutator
case 9134: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.remove_icon_pack).setMessage(com.beemdevelopment.aegis.R.string.remove_icon_pack_description).setPositiveButton(android.R.string.yes, null).setNegativeButton(android.R.string.no, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.remove_icon_pack).setMessage(com.beemdevelopment.aegis.R.string.remove_icon_pack_description).setPositiveButton(android.R.string.yes, (android.content.DialogInterface dialog,int whichButton) -> {
try {
_iconPackManager.removeIconPack(pack);
} catch (com.beemdevelopment.aegis.icons.IconPackException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.icon_pack_delete_error, e);
return;
}
_adapter.removeIconPack(pack);
updateEmptyState();
}).setNegativeButton(android.R.string.no, null).create());
break;
}
}
}


@java.lang.Override
public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
super.onActivityResult(requestCode, resultCode, data);
if ((((requestCode == com.beemdevelopment.aegis.ui.fragments.preferences.IconPacksManagerFragment.CODE_IMPORT) && (resultCode == android.app.Activity.RESULT_OK)) && (data != null)) && (data.getData() != null)) {
importIconPack(data.getData());
}
}


private void importIconPack(android.net.Uri uri) {
com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask task;
task = new com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask(requireContext(), (com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Result result) -> {
java.lang.Exception e;
e = result.getException();
if (e instanceof com.beemdevelopment.aegis.icons.IconPackExistsException) {
switch(MUID_STATIC) {
// IconPacksManagerFragment_10_BuggyGUIListenerOperatorMutator
case 10134: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.error_occurred).setMessage(com.beemdevelopment.aegis.R.string.icon_pack_import_exists_error).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, null).setNegativeButton(com.beemdevelopment.aegis.R.string.no, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.error_occurred).setMessage(com.beemdevelopment.aegis.R.string.icon_pack_import_exists_error).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, (android.content.DialogInterface dialog,int which) -> {
if (removeIconPack(((com.beemdevelopment.aegis.icons.IconPackExistsException) (e)).getIconPack())) {
    importIconPack(uri);
}
}).setNegativeButton(com.beemdevelopment.aegis.R.string.no, null).create());
break;
}
}
} else if (e != null) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.icon_pack_import_error, e);
} else {
_adapter.addIconPack(result.getIconPack());
updateEmptyState();
}
});
task.execute(getLifecycle(), new com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Params(_iconPackManager, uri));
}


private boolean removeIconPack(com.beemdevelopment.aegis.icons.IconPack pack) {
try {
_iconPackManager.removeIconPack(pack);
} catch (com.beemdevelopment.aegis.icons.IconPackException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.icon_pack_delete_error, e);
return false;
}
_adapter.removeIconPack(pack);
updateEmptyState();
return true;
}


private void startImportIconPack() {
android.content.Intent intent;
switch(MUID_STATIC) {
// IconPacksManagerFragment_11_NullIntentOperatorMutator
case 11134: {
intent = null;
break;
}
// IconPacksManagerFragment_12_InvalidKeyIntentOperatorMutator
case 12134: {
intent = new android.content.Intent((String) null);
break;
}
// IconPacksManagerFragment_13_RandomActionIntentDefinitionOperatorMutator
case 13134: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
break;
}
}
switch(MUID_STATIC) {
// IconPacksManagerFragment_14_RandomActionIntentDefinitionOperatorMutator
case 14134: {
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
intent.setType("*/*");
break;
}
}
_vaultManager.startActivityForResult(this, intent, com.beemdevelopment.aegis.ui.fragments.preferences.IconPacksManagerFragment.CODE_IMPORT);
}


private void updateEmptyState() {
if (_adapter.getItemCount() > 0) {
_iconPacksView.setVisibility(android.view.View.VISIBLE);
_noIconPacksView.setVisibility(android.view.View.GONE);
} else {
_iconPacksView.setVisibility(android.view.View.GONE);
_noIconPacksView.setVisibility(android.view.View.VISIBLE);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
