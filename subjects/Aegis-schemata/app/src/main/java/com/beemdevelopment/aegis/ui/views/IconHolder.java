package com.beemdevelopment.aegis.ui.views;
import com.beemdevelopment.aegis.helpers.IconViewHelper;
import com.beemdevelopment.aegis.icons.IconType;
import com.beemdevelopment.aegis.helpers.ThemeHelper;
import android.view.View;
import com.beemdevelopment.aegis.icons.IconPack;
import com.beemdevelopment.aegis.R;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.io.File;
import com.bumptech.glide.Glide;
import android.content.Context;
import com.beemdevelopment.aegis.ui.glide.IconLoader;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    static final int MUID_STATIC = getMUID();
    private java.io.File _iconFile;

    private com.beemdevelopment.aegis.icons.IconType _iconType;

    private boolean _isCustom;

    private final android.widget.ImageView _imageView;

    private final android.widget.TextView _textView;

    public IconHolder(final android.view.View view) {
        super(view);
        switch(MUID_STATIC) {
            // IconHolder_0_InvalidViewFocusOperatorMutator
            case 151: {
                /**
                * Inserted by Kadabra
                */
                _imageView = view.findViewById(com.beemdevelopment.aegis.R.id.icon);
                _imageView.requestFocus();
                break;
            }
            // IconHolder_1_ViewComponentNotVisibleOperatorMutator
            case 1151: {
                /**
                * Inserted by Kadabra
                */
                _imageView = view.findViewById(com.beemdevelopment.aegis.R.id.icon);
                _imageView.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            _imageView = view.findViewById(com.beemdevelopment.aegis.R.id.icon);
            break;
        }
    }
    switch(MUID_STATIC) {
        // IconHolder_2_InvalidViewFocusOperatorMutator
        case 2151: {
            /**
            * Inserted by Kadabra
            */
            _textView = view.findViewById(com.beemdevelopment.aegis.R.id.icon_name);
            _textView.requestFocus();
            break;
        }
        // IconHolder_3_ViewComponentNotVisibleOperatorMutator
        case 3151: {
            /**
            * Inserted by Kadabra
            */
            _textView = view.findViewById(com.beemdevelopment.aegis.R.id.icon_name);
            _textView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _textView = view.findViewById(com.beemdevelopment.aegis.R.id.icon_name);
        break;
    }
}
}


public void setData(com.beemdevelopment.aegis.icons.IconPack.Icon icon) {
_iconFile = icon.getFile();
_iconType = icon.getIconType();
_isCustom = icon instanceof com.beemdevelopment.aegis.ui.views.IconAdapter.DummyIcon;
_textView.setText(icon.getName());
}


public void loadIcon(android.content.Context context) {
if (_isCustom) {
    int tint;
    tint = com.beemdevelopment.aegis.helpers.ThemeHelper.getThemeColor(com.beemdevelopment.aegis.R.attr.iconColorPrimary, context.getTheme());
    _imageView.setColorFilter(tint);
    _imageView.setImageResource(com.beemdevelopment.aegis.R.drawable.ic_plus_black_24dp);
} else {
    _imageView.setImageTintList(null);
    com.beemdevelopment.aegis.helpers.IconViewHelper.setLayerType(_imageView, _iconType);
    com.bumptech.glide.Glide.with(context).asDrawable().load(_iconFile).set(com.beemdevelopment.aegis.ui.glide.IconLoader.ICON_TYPE, _iconType).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE).skipMemoryCache(false).into(_imageView);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
