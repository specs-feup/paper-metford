package com.automattic.simplenote;
import androidx.appcompat.app.AlertDialog;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentTransaction;
import com.automattic.simplenote.utils.DisplayUtils;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ShortcutDialogFragment extends androidx.appcompat.app.AppCompatDialogFragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String DIALOG_TAG = "shortcut_tag";

    public static final java.lang.String DIALOG_VISIBLE = "shortcut_visible";

    private boolean mIsPreview;

    private ShortcutDialogFragment(boolean isPreview) {
        mIsPreview = isPreview;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        if ((getContext() != null) && (getActivity() != null)) {
            android.view.View view;
            view = android.view.View.inflate(requireContext(), getLayout(), null);
            return new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(requireContext(), com.automattic.simplenote.R.style.Dialog)).setView(view).setPositiveButton(android.R.string.ok, null).create();
        } else {
            return super.onCreateDialog(savedInstanceState);
        }
    }


    @androidx.annotation.LayoutRes
    private int getLayout() {
        return com.automattic.simplenote.utils.DisplayUtils.isLargeScreenLandscape(requireContext()) ? com.automattic.simplenote.R.layout.dialog_shortcuts_all : !(getActivity() instanceof com.automattic.simplenote.NoteEditorActivity) ? com.automattic.simplenote.R.layout.dialog_shortcuts_list : mIsPreview ? com.automattic.simplenote.R.layout.dialog_shortcuts_editor_preview : com.automattic.simplenote.R.layout.dialog_shortcuts_editor_edit;
    }


    public static void showShortcuts(@androidx.annotation.NonNull
    androidx.fragment.app.FragmentActivity activity, boolean isPreview) {
        androidx.fragment.app.FragmentTransaction transaction;
        transaction = activity.getSupportFragmentManager().beginTransaction();
        androidx.fragment.app.Fragment fragment;
        fragment = activity.getSupportFragmentManager().findFragmentByTag(com.automattic.simplenote.ShortcutDialogFragment.DIALOG_TAG);
        if (fragment != null) {
            transaction.remove(fragment);
        }
        com.automattic.simplenote.ShortcutDialogFragment dialog;
        dialog = new com.automattic.simplenote.ShortcutDialogFragment(isPreview);
        dialog.show(transaction, com.automattic.simplenote.ShortcutDialogFragment.DIALOG_TAG);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
