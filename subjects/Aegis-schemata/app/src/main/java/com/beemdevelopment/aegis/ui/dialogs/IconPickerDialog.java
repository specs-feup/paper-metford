package com.beemdevelopment.aegis.ui.dialogs;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import android.text.TextWatcher;
import com.beemdevelopment.aegis.ui.views.IconAdapter;
import com.beemdevelopment.aegis.icons.IconPack;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.beemdevelopment.aegis.R;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.widget.TextView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;
import com.bumptech.glide.Glide;
import java.util.Collections;
import java.util.stream.Collectors;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import android.view.Menu;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.LayoutInflater;
import com.beemdevelopment.aegis.ui.views.IconRecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import android.widget.FrameLayout;
import androidx.appcompat.widget.PopupMenu;
import com.google.android.material.textfield.TextInputEditText;
import androidx.annotation.Nullable;
import com.beemdevelopment.aegis.ui.glide.IconLoader;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconPickerDialog {
    static final int MUID_STATIC = getMUID();
    private IconPickerDialog() {
    }


    public static com.google.android.material.bottomsheet.BottomSheetDialog create(android.app.Activity activity, java.util.List<com.beemdevelopment.aegis.icons.IconPack> iconPacks, java.lang.String issuer, com.beemdevelopment.aegis.ui.views.IconAdapter.Listener listener) {
        android.view.View view;
        view = android.view.LayoutInflater.from(activity).inflate(com.beemdevelopment.aegis.R.layout.dialog_icon_picker, null);
        android.widget.TextView textIconPack;
        switch(MUID_STATIC) {
            // IconPickerDialog_0_InvalidViewFocusOperatorMutator
            case 113: {
                /**
                * Inserted by Kadabra
                */
                textIconPack = view.findViewById(com.beemdevelopment.aegis.R.id.text_icon_pack);
                textIconPack.requestFocus();
                break;
            }
            // IconPickerDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1113: {
                /**
                * Inserted by Kadabra
                */
                textIconPack = view.findViewById(com.beemdevelopment.aegis.R.id.text_icon_pack);
                textIconPack.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            textIconPack = view.findViewById(com.beemdevelopment.aegis.R.id.text_icon_pack);
            break;
        }
    }
    com.google.android.material.bottomsheet.BottomSheetDialog dialog;
    dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(activity);
    dialog.setContentView(view);
    dialog.create();
    android.widget.FrameLayout rootView;
    switch(MUID_STATIC) {
        // IconPickerDialog_2_InvalidViewFocusOperatorMutator
        case 2113: {
            /**
            * Inserted by Kadabra
            */
            rootView = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            rootView.requestFocus();
            break;
        }
        // IconPickerDialog_3_ViewComponentNotVisibleOperatorMutator
        case 3113: {
            /**
            * Inserted by Kadabra
            */
            rootView = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            rootView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        rootView = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        break;
    }
}
rootView.getLayoutParams().height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
com.beemdevelopment.aegis.ui.views.IconAdapter adapter;
adapter = new com.beemdevelopment.aegis.ui.views.IconAdapter(dialog.getContext(), issuer, new com.beemdevelopment.aegis.ui.views.IconAdapter.Listener() {
    @java.lang.Override
    public void onIconSelected(com.beemdevelopment.aegis.icons.IconPack.Icon icon) {
        dialog.dismiss();
        listener.onIconSelected(icon);
    }


    @java.lang.Override
    public void onCustomSelected() {
        dialog.dismiss();
        listener.onCustomSelected();
    }

});
class IconPreloadProvider implements com.bumptech.glide.ListPreloader.PreloadModelProvider<com.beemdevelopment.aegis.icons.IconPack.Icon> {
    @androidx.annotation.NonNull
    @java.lang.Override
    public java.util.List<com.beemdevelopment.aegis.icons.IconPack.Icon> getPreloadItems(int position) {
        com.beemdevelopment.aegis.icons.IconPack.Icon icon;
        icon = adapter.getIconAt(position);
        return java.util.Collections.singletonList(icon);
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public com.bumptech.glide.RequestBuilder<android.graphics.drawable.Drawable> getPreloadRequestBuilder(@androidx.annotation.NonNull
    com.beemdevelopment.aegis.icons.IconPack.Icon icon) {
        return com.bumptech.glide.Glide.with(dialog.getContext()).asDrawable().load(icon.getFile()).set(com.beemdevelopment.aegis.ui.glide.IconLoader.ICON_TYPE, icon.getIconType()).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE).skipMemoryCache(false);
    }

}
com.google.android.material.textfield.TextInputEditText iconSearch;
switch(MUID_STATIC) {
    // IconPickerDialog_4_InvalidViewFocusOperatorMutator
    case 4113: {
        /**
        * Inserted by Kadabra
        */
        iconSearch = view.findViewById(com.beemdevelopment.aegis.R.id.text_search_icon);
        iconSearch.requestFocus();
        break;
    }
    // IconPickerDialog_5_ViewComponentNotVisibleOperatorMutator
    case 5113: {
        /**
        * Inserted by Kadabra
        */
        iconSearch = view.findViewById(com.beemdevelopment.aegis.R.id.text_search_icon);
        iconSearch.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    iconSearch = view.findViewById(com.beemdevelopment.aegis.R.id.text_search_icon);
    break;
}
}
iconSearch.setOnFocusChangeListener((android.view.View v,boolean hasFocus) -> {
if (hasFocus) {
    com.google.android.material.bottomsheet.BottomSheetBehavior<android.widget.FrameLayout> behavior;
    behavior = com.google.android.material.bottomsheet.BottomSheetBehavior.from(rootView);
    behavior.setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
}
});
iconSearch.addTextChangedListener(new android.text.TextWatcher() {
@java.lang.Override
public void beforeTextChanged(java.lang.CharSequence s, int start, int count, int after) {
}


@java.lang.Override
public void onTextChanged(java.lang.CharSequence s, int start, int before, int count) {
    java.lang.String query;
    query = s.toString();
    adapter.setQuery(query.isEmpty() ? null : query);
}


@java.lang.Override
public void afterTextChanged(android.text.Editable s) {
}

});
com.bumptech.glide.util.ViewPreloadSizeProvider<com.beemdevelopment.aegis.icons.IconPack.Icon> preloadSizeProvider;
preloadSizeProvider = new com.bumptech.glide.util.ViewPreloadSizeProvider<>();
IconPreloadProvider modelProvider;
modelProvider = new IconPreloadProvider();
com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader<com.beemdevelopment.aegis.icons.IconPack.Icon> preloader;
preloader = new com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader<>(activity, modelProvider, preloadSizeProvider, 10);
com.beemdevelopment.aegis.ui.views.IconRecyclerView recyclerView;
switch(MUID_STATIC) {
// IconPickerDialog_6_InvalidViewFocusOperatorMutator
case 6113: {
    /**
    * Inserted by Kadabra
    */
    recyclerView = view.findViewById(com.beemdevelopment.aegis.R.id.list_icons);
    recyclerView.requestFocus();
    break;
}
// IconPickerDialog_7_ViewComponentNotVisibleOperatorMutator
case 7113: {
    /**
    * Inserted by Kadabra
    */
    recyclerView = view.findViewById(com.beemdevelopment.aegis.R.id.list_icons);
    recyclerView.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
recyclerView = view.findViewById(com.beemdevelopment.aegis.R.id.list_icons);
break;
}
}
androidx.recyclerview.widget.GridLayoutManager layoutManager;
layoutManager = recyclerView.getGridLayoutManager();
layoutManager.setSpanSizeLookup(new androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup() {
@java.lang.Override
public int getSpanSize(int position) {
if (adapter.getItemViewType(position) == com.beemdevelopment.aegis.R.layout.card_icon) {
    return 1;
}
return recyclerView.getSpanCount();
}

});
recyclerView.setLayoutManager(layoutManager);
recyclerView.setAdapter(adapter);
recyclerView.addOnScrollListener(preloader);
adapter.loadIcons(iconPacks.get(0));
textIconPack.setText(iconPacks.get(0).getName());
switch(MUID_STATIC) {
// IconPickerDialog_8_BuggyGUIListenerOperatorMutator
case 8113: {
view.findViewById(com.beemdevelopment.aegis.R.id.btn_icon_pack).setOnClickListener(null);
break;
}
default: {
view.findViewById(com.beemdevelopment.aegis.R.id.btn_icon_pack).setOnClickListener((android.view.View v) -> {
java.util.List<java.lang.String> iconPackNames;
iconPackNames = iconPacks.stream().map(com.beemdevelopment.aegis.icons.IconPack::getName).collect(java.util.stream.Collectors.toList());
androidx.appcompat.widget.PopupMenu popupMenu;
popupMenu = new androidx.appcompat.widget.PopupMenu(activity, v);
popupMenu.setOnMenuItemClickListener((android.view.MenuItem item) -> {
    com.beemdevelopment.aegis.icons.IconPack pack;
    pack = iconPacks.get(iconPackNames.indexOf(item.getTitle().toString()));
    adapter.loadIcons(pack);
    java.lang.String query;
    query = iconSearch.getText().toString();
    if (!query.isEmpty()) {
        adapter.setQuery(query);
    }
    textIconPack.setText(pack.getName());
    return true;
});
android.view.Menu menu;
menu = popupMenu.getMenu();
for (java.lang.String name : iconPackNames) {
    menu.add(name);
}
popupMenu.show();
});
break;
}
}
return dialog;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
