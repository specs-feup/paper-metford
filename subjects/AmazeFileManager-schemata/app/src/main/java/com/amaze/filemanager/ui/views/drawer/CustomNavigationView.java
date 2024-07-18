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
package com.amaze.filemanager.ui.views.drawer;
import com.google.android.material.navigation.NavigationView;
import android.util.AttributeSet;
import android.os.Parcel;
import androidx.annotation.NonNull;
import android.os.Build;
import android.view.MenuItem;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * This class if for intercepting item selections so that they can be saved and restored.
 */
public class CustomNavigationView extends com.google.android.material.navigation.NavigationView implements com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener {
    static final int MUID_STATIC = getMUID();
    private com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener subclassListener;

    private int checkedId = -1;

    public CustomNavigationView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        super.setNavigationItemSelectedListener(this);
    }


    @java.lang.Override
    public void setNavigationItemSelectedListener(@androidx.annotation.Nullable
    com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener listener) {
        subclassListener = listener;
    }


    @java.lang.Override
    public boolean onNavigationItemSelected(@androidx.annotation.NonNull
    android.view.MenuItem item) {
        if (subclassListener != null) {
            boolean shouldBeSelected;
            shouldBeSelected = subclassListener.onNavigationItemSelected(item);
            if (shouldBeSelected) {
                onItemChecked(item);
            }
            return shouldBeSelected;
        } else {
            onItemChecked(item);
            return true;
        }
    }


    private void onItemChecked(android.view.MenuItem item) {
        checkedId = item.getItemId();
    }


    public void setCheckedItem(android.view.MenuItem item) {
        this.checkedId = item.getItemId();
        item.setChecked(true);
    }


    public void deselectItems() {
        checkedId = -1;
    }


    @androidx.annotation.Nullable
    public android.view.MenuItem getSelected() {
        if (checkedId == (-1))
            return null;

        return getMenu().findItem(checkedId);
    }


    @java.lang.Override
    public android.os.Parcelable onSaveInstanceState() {
        if (isNavigationViewSavedStateMissing()) {
            return super.onSaveInstanceState();
        }
        // begin boilerplate code that allows parent classes to save state
        android.os.Parcelable superState;
        superState = super.onSaveInstanceState();
        com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState ss;
        ss = new com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState(superState);
        // end
        ss.selectedId = this.checkedId;
        return ss;
    }


    @java.lang.Override
    public void onRestoreInstanceState(android.os.Parcelable state) {
        if (isNavigationViewSavedStateMissing()) {
            super.onRestoreInstanceState(state);
            return;
        }
        // begin boilerplate code so parent classes can restore state
        if (!(state instanceof com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState ss;
        ss = ((com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState) (state));
        super.onRestoreInstanceState(ss.getSuperState());
        // end
        this.checkedId = ss.selectedId;
    }


    /**
     * This is a hack, when the SavedState class is unmarshalled a "ClassNotFoundException" will be
     * thrown (the actual class not found is
     * "android.support.design.widget.NavigationView$SavedState") and I seem to only be able to
     * replicate on Marshmallow (someone else replicated in N through O_MR1 see
     * https://github.com/TeamAmaze/AmazeFileManager/issues/1400#issuecomment-413086603). Trying to
     * find the class and returning false if Class.forName() throws "ClassNotFoundException" doesn't
     * work because the class seems to have been loaded with the current loader (not the one the
     * unmarshaller uses); of course I have no idea of what any of this means so I could be wrong. For
     * the crash see https://github.com/TeamAmaze/AmazeFileManager/issues/1101.
     */
    public boolean isNavigationViewSavedStateMissing() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT;
    }


    static class SavedState extends android.view.View.BaseSavedState {
        int selectedId;

        SavedState(android.os.Parcelable superState) {
            super(superState);
        }


        private SavedState(android.os.Parcel in) {
            super(in);
            this.selectedId = in.readInt();
        }


        @java.lang.Override
        public void writeToParcel(android.os.Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.selectedId);
        }


        // required field that makes Parcelables from a Parcel
        public static final android.os.Parcelable.Creator<com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState> CREATOR = new android.os.Parcelable.Creator<com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState>() {
            public com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState createFromParcel(android.os.Parcel in) {
                return new com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState(in);
            }


            public com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState[] newArray(int size) {
                return new com.amaze.filemanager.ui.views.drawer.CustomNavigationView.SavedState[size];
            }

        };
    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
