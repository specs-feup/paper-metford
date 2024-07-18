package com.automattic.simplenote;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.automattic.simplenote.utils.ThemeUtils;
import android.view.ViewParent;
import android.view.Gravity;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BottomSheetDialogBase extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public int getTheme() {
        return com.automattic.simplenote.utils.ThemeUtils.getThemeFromStyle(requireContext());
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
        setRetainInstance(true);
        return new com.google.android.material.bottomsheet.BottomSheetDialog(requireContext(), getTheme());
    }


    @java.lang.Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null) {
            // Limit width of bottom sheet on wide screens; non-zero width defined only for large qualifier.
            int dp;
            dp = ((int) (getDialog().getContext().getResources().getDimension(com.automattic.simplenote.R.dimen.width_layout)));
            if (dp > 0) {
                android.widget.FrameLayout bottomSheetLayout;
                switch(MUID_STATIC) {
                    // BottomSheetDialogBase_0_InvalidViewFocusOperatorMutator
                    case 70: {
                        /**
                        * Inserted by Kadabra
                        */
                        bottomSheetLayout = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                        bottomSheetLayout.requestFocus();
                        break;
                    }
                    // BottomSheetDialogBase_1_ViewComponentNotVisibleOperatorMutator
                    case 170: {
                        /**
                        * Inserted by Kadabra
                        */
                        bottomSheetLayout = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                        bottomSheetLayout.setVisibility(android.view.View.INVISIBLE);
                        break;
                    }
                    default: {
                    bottomSheetLayout = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
                    break;
                }
            }
            if (bottomSheetLayout != null) {
                android.view.ViewParent bottomSheetParent;
                bottomSheetParent = bottomSheetLayout.getParent();
                if (bottomSheetParent instanceof androidx.coordinatorlayout.widget.CoordinatorLayout) {
                    androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams coordinatorLayoutParams;
                    coordinatorLayoutParams = ((androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) (bottomSheetLayout.getLayoutParams()));
                    coordinatorLayoutParams.width = dp;
                    bottomSheetLayout.setLayoutParams(coordinatorLayoutParams);
                    androidx.coordinatorlayout.widget.CoordinatorLayout coordinatorLayout;
                    coordinatorLayout = ((androidx.coordinatorlayout.widget.CoordinatorLayout) (bottomSheetParent));
                    android.widget.FrameLayout.LayoutParams layoutParams;
                    layoutParams = ((android.widget.FrameLayout.LayoutParams) (coordinatorLayout.getLayoutParams()));
                    layoutParams.gravity = android.view.Gravity.CENTER_HORIZONTAL;
                    coordinatorLayout.setLayoutParams(layoutParams);
                }
            }
        }
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
