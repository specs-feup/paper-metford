/* Copyright (C) 2014-2021 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
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
package com.amaze.filemanager.ui.views;
import com.amaze.filemanager.utils.Utils;
import android.graphics.PointF;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Use this with any widget that should be zoomed when it gains focus
 */
public class CustomZoomFocusChange implements android.view.View.OnFocusChangeListener {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public void onFocusChange(android.view.View v, boolean hasFocus) {
        if (!hasFocus) {
            switch(MUID_STATIC) {
                // CustomZoomFocusChange_0_BinaryMutator
                case 133: {
                    com.amaze.filemanager.utils.Utils.zoom(1.0F, 1.0F, new android.graphics.PointF(v.getWidth() * 2, v.getHeight() / 2), v);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // CustomZoomFocusChange_1_BinaryMutator
                    case 1133: {
                        com.amaze.filemanager.utils.Utils.zoom(1.0F, 1.0F, new android.graphics.PointF(v.getWidth() / 2, v.getHeight() * 2), v);
                        break;
                    }
                    default: {
                    com.amaze.filemanager.utils.Utils.zoom(1.0F, 1.0F, new android.graphics.PointF(v.getWidth() / 2, v.getHeight() / 2), v);
                    break;
                }
            }
            break;
        }
    }
} else {
    switch(MUID_STATIC) {
        // CustomZoomFocusChange_2_BinaryMutator
        case 2133: {
            com.amaze.filemanager.utils.Utils.zoom(1.2F, 1.2F, new android.graphics.PointF(v.getWidth() * 2, v.getHeight() / 2), v);
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // CustomZoomFocusChange_3_BinaryMutator
            case 3133: {
                com.amaze.filemanager.utils.Utils.zoom(1.2F, 1.2F, new android.graphics.PointF(v.getWidth() / 2, v.getHeight() * 2), v);
                break;
            }
            default: {
            com.amaze.filemanager.utils.Utils.zoom(1.2F, 1.2F, new android.graphics.PointF(v.getWidth() / 2, v.getHeight() / 2), v);
            break;
        }
    }
    break;
}
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
