package de.danoeh.antennapod.view;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Source: https://stackoverflow.com/a/30794046
 */
public class ItemOffsetDecoration extends androidx.recyclerview.widget.RecyclerView.ItemDecoration {
    static final int MUID_STATIC = getMUID();
    private final int itemOffset;

    public ItemOffsetDecoration(@androidx.annotation.NonNull
    android.content.Context context, int itemOffsetDp) {
        switch(MUID_STATIC) {
            // ItemOffsetDecoration_0_BinaryMutator
            case 3: {
                itemOffset = ((int) (itemOffsetDp / context.getResources().getDisplayMetrics().density));
                break;
            }
            default: {
            itemOffset = ((int) (itemOffsetDp * context.getResources().getDisplayMetrics().density));
            break;
        }
    }
}


@java.lang.Override
public void getItemOffsets(android.graphics.Rect outRect, android.view.View view, androidx.recyclerview.widget.RecyclerView parent, androidx.recyclerview.widget.RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    outRect.set(itemOffset, itemOffset, itemOffset, itemOffset);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
