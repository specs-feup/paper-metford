package com.beemdevelopment.aegis.ui.views;
import com.beemdevelopment.aegis.R;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconCategoryHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    static final int MUID_STATIC = getMUID();
    private final android.widget.TextView _textView;

    private final android.widget.ImageView _imgView;

    public IconCategoryHolder(final android.view.View view) {
        super(view);
        switch(MUID_STATIC) {
            // IconCategoryHolder_0_InvalidViewFocusOperatorMutator
            case 155: {
                /**
                * Inserted by Kadabra
                */
                _textView = view.findViewById(com.beemdevelopment.aegis.R.id.icon_category);
                _textView.requestFocus();
                break;
            }
            // IconCategoryHolder_1_ViewComponentNotVisibleOperatorMutator
            case 1155: {
                /**
                * Inserted by Kadabra
                */
                _textView = view.findViewById(com.beemdevelopment.aegis.R.id.icon_category);
                _textView.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            _textView = view.findViewById(com.beemdevelopment.aegis.R.id.icon_category);
            break;
        }
    }
    switch(MUID_STATIC) {
        // IconCategoryHolder_2_InvalidViewFocusOperatorMutator
        case 2155: {
            /**
            * Inserted by Kadabra
            */
            _imgView = view.findViewById(com.beemdevelopment.aegis.R.id.icon_category_indicator);
            _imgView.requestFocus();
            break;
        }
        // IconCategoryHolder_3_ViewComponentNotVisibleOperatorMutator
        case 3155: {
            /**
            * Inserted by Kadabra
            */
            _imgView = view.findViewById(com.beemdevelopment.aegis.R.id.icon_category_indicator);
            _imgView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _imgView = view.findViewById(com.beemdevelopment.aegis.R.id.icon_category_indicator);
        break;
    }
}
}


public void setData(com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader header) {
_textView.setText(header.getCategory());
_imgView.setRotation(com.beemdevelopment.aegis.ui.views.IconCategoryHolder.getRotation(header.isCollapsed()));
}


public void setIsCollapsed(boolean collapsed) {
_imgView.animate().setDuration(200).rotation(com.beemdevelopment.aegis.ui.views.IconCategoryHolder.getRotation(collapsed)).start();
}


private static int getRotation(boolean collapsed) {
return collapsed ? 90 : 0;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
