package com.beemdevelopment.aegis.ui.views;
import java.util.stream.Collectors;
import android.view.ViewGroup;
import java.util.ArrayList;
import com.beemdevelopment.aegis.icons.IconType;
import android.view.View;
import java.util.Comparator;
import com.beemdevelopment.aegis.icons.IconPack;
import com.beemdevelopment.aegis.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import java.util.Objects;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import androidx.annotation.Nullable;
import android.content.Context;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    static final int MUID_STATIC = getMUID();
    private final android.content.Context _context;

    private final java.lang.String _issuer;

    private final com.beemdevelopment.aegis.ui.views.IconAdapter.Listener _listener;

    private com.beemdevelopment.aegis.icons.IconPack _pack;

    private java.util.List<com.beemdevelopment.aegis.icons.IconPack.Icon> _icons;

    private final java.util.List<com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader> _categories;

    private java.lang.String _query;

    public IconAdapter(@androidx.annotation.NonNull
    android.content.Context context, java.lang.String issuer, @androidx.annotation.NonNull
    com.beemdevelopment.aegis.ui.views.IconAdapter.Listener listener) {
        _context = context;
        _issuer = issuer;
        _listener = listener;
        _icons = new java.util.ArrayList<>();
        _categories = new java.util.ArrayList<>();
    }


    /**
     * Loads all icons from the given icon pack into this adapter. Any icons added before this call will be overwritten.
     */
    public void loadIcons(com.beemdevelopment.aegis.icons.IconPack pack) {
        _pack = pack;
        _query = null;
        _icons = new java.util.ArrayList<>(_pack.getIcons());
        _categories.clear();
        java.util.Comparator<com.beemdevelopment.aegis.icons.IconPack.Icon> iconCategoryComparator;
        iconCategoryComparator = (com.beemdevelopment.aegis.icons.IconPack.Icon i1,com.beemdevelopment.aegis.icons.IconPack.Icon i2) -> {
            java.lang.String c1;
            c1 = getCategoryString(i1.getCategory());
            java.lang.String c2;
            c2 = getCategoryString(i2.getCategory());
            return c1.compareTo(c2);
        };
        java.util.Collections.sort(_icons, iconCategoryComparator.thenComparing(com.beemdevelopment.aegis.icons.IconPack.Icon::getName));
        long categoryCount;
        categoryCount = _icons.stream().map(com.beemdevelopment.aegis.icons.IconPack.Icon::getCategory).filter(java.util.Objects::nonNull).distinct().count();
        java.util.List<com.beemdevelopment.aegis.icons.IconPack.Icon> suggested;
        suggested = pack.getSuggestedIcons(_issuer);
        suggested.add(0, new com.beemdevelopment.aegis.ui.views.IconAdapter.DummyIcon(_context.getString(com.beemdevelopment.aegis.R.string.icon_custom)));
        if (suggested.size() > 0) {
            com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader category;
            category = new com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader(_context.getString(com.beemdevelopment.aegis.R.string.suggested));
            category.setIsCollapsed(false);
            category.getIcons().addAll(suggested);
            _categories.add(category);
        }
        com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader category;
        category = null;
        for (com.beemdevelopment.aegis.icons.IconPack.Icon icon : _icons) {
            java.lang.String iconCategory;
            iconCategory = getCategoryString(icon.getCategory());
            if ((category == null) || (!getCategoryString(category.getCategory()).equals(iconCategory))) {
                boolean collapsed;
                collapsed = !((categoryCount == 0) && (category == null));
                category = new com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader(iconCategory);
                category.setIsCollapsed(collapsed);
                _categories.add(category);
            }
            category.getIcons().add(icon);
        }
        _icons.addAll(0, suggested);
        updateCategoryPositions();
        notifyDataSetChanged();
    }


    public void setQuery(@androidx.annotation.Nullable
    java.lang.String query) {
        _query = query;
        if (_query == null) {
            loadIcons(_pack);
        } else {
            _icons = _pack.getIcons().stream().filter((com.beemdevelopment.aegis.icons.IconPack.Icon i) -> i.isSuggestedFor(query)).collect(java.util.stream.Collectors.toList());
            java.util.Collections.sort(_icons, java.util.Comparator.comparing(com.beemdevelopment.aegis.icons.IconPack.Icon::getName));
            notifyDataSetChanged();
        }
    }


    public com.beemdevelopment.aegis.icons.IconPack.Icon getIconAt(int position) {
        if (isQueryActive()) {
            return _icons.get(position);
        }
        position = translateIconPosition(position);
        return _icons.get(position);
    }


    public com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader getCategoryAt(int position) {
        return _categories.stream().filter((com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader c) -> c.getPosition() == position).findFirst().orElse(null);
    }


    private java.lang.String getCategoryString(java.lang.String category) {
        return category == null ? _context.getString(com.beemdevelopment.aegis.R.string.uncategorized) : category;
    }


    private boolean isCategoryPosition(int position) {
        if (isQueryActive()) {
            return false;
        }
        return getCategoryAt(position) != null;
    }


    private int translateIconPosition(int position) {
        int offset;
        offset = 0;
        for (com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader category : _categories) {
            if (category.getPosition() < position) {
                offset++;
                if (category.isCollapsed()) {
                    offset -= category.getIcons().size();
                }
            }
        }
        switch(MUID_STATIC) {
            // IconAdapter_0_BinaryMutator
            case 163: {
                return position + offset;
            }
            default: {
            return position - offset;
            }
    }
}


private void updateCategoryPositions() {
    int i;
    i = 0;
    for (com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader category : _categories) {
        category.setPosition(i);
        int icons;
        icons = 0;
        if (!category.isCollapsed()) {
            icons = category.getIcons().size();
        }
        switch(MUID_STATIC) {
            // IconAdapter_1_BinaryMutator
            case 1163: {
                i += 1 - icons;
                break;
            }
            default: {
            i += 1 + icons;
            break;
        }
    }
}
}


@androidx.annotation.NonNull
@java.lang.Override
public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull
android.view.ViewGroup parent, int viewType) {
android.view.View view;
view = android.view.LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
return viewType == com.beemdevelopment.aegis.R.layout.card_icon ? new com.beemdevelopment.aegis.ui.views.IconHolder(view) : new com.beemdevelopment.aegis.ui.views.IconCategoryHolder(view);
}


@java.lang.Override
public void onBindViewHolder(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
if (!isCategoryPosition(position)) {
    com.beemdevelopment.aegis.ui.views.IconHolder iconHolder;
    iconHolder = ((com.beemdevelopment.aegis.ui.views.IconHolder) (holder));
    com.beemdevelopment.aegis.icons.IconPack.Icon icon;
    icon = getIconAt(position);
    iconHolder.setData(icon);
    iconHolder.loadIcon(_context);
    switch(MUID_STATIC) {
        // IconAdapter_2_BuggyGUIListenerOperatorMutator
        case 2163: {
            iconHolder.itemView.setOnClickListener(null);
            break;
        }
        default: {
        iconHolder.itemView.setOnClickListener((android.view.View v) -> {
            if (icon instanceof com.beemdevelopment.aegis.ui.views.IconAdapter.DummyIcon) {
                _listener.onCustomSelected();
            } else {
                _listener.onIconSelected(icon);
            }
        });
        break;
    }
}
} else {
com.beemdevelopment.aegis.ui.views.IconCategoryHolder categoryHolder;
categoryHolder = ((com.beemdevelopment.aegis.ui.views.IconCategoryHolder) (holder));
com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader category;
category = getCategoryAt(position);
categoryHolder.setData(category);
switch(MUID_STATIC) {
    // IconAdapter_3_BuggyGUIListenerOperatorMutator
    case 3163: {
        categoryHolder.itemView.setOnClickListener(null);
        break;
    }
    default: {
    categoryHolder.itemView.setOnClickListener((android.view.View v) -> {
        boolean collapsed;
        collapsed = !category.isCollapsed();
        categoryHolder.setIsCollapsed(collapsed);
        category.setIsCollapsed(collapsed);
        int startPosition;
        switch(MUID_STATIC) {
            // IconAdapter_4_BinaryMutator
            case 4163: {
                startPosition = category.getPosition() - 1;
                break;
            }
            default: {
            startPosition = category.getPosition() + 1;
            break;
        }
    }
    if (category.isCollapsed()) {
        notifyItemRangeRemoved(startPosition, category.getIcons().size());
    } else {
        notifyItemRangeInserted(startPosition, category.getIcons().size());
    }
    updateCategoryPositions();
});
break;
}
}
}
}


@java.lang.Override
public int getItemCount() {
if (isQueryActive()) {
return _icons.size();
}
int items;
items = _categories.stream().filter((com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader c) -> !c.isCollapsed()).mapToInt((com.beemdevelopment.aegis.ui.views.IconAdapter.CategoryHeader c) -> c.getIcons().size()).sum();
switch(MUID_STATIC) {
// IconAdapter_5_BinaryMutator
case 5163: {
return items - _categories.size();
}
default: {
return items + _categories.size();
}
}
}


@java.lang.Override
public int getItemViewType(int position) {
if (isCategoryPosition(position)) {
return com.beemdevelopment.aegis.R.layout.card_icon_category;
}
return com.beemdevelopment.aegis.R.layout.card_icon;
}


private boolean isQueryActive() {
return _query != null;
}


public interface Listener {
void onIconSelected(com.beemdevelopment.aegis.icons.IconPack.Icon icon);


void onCustomSelected();

}

public static class DummyIcon extends com.beemdevelopment.aegis.icons.IconPack.Icon {
private final java.lang.String _name;

protected DummyIcon(java.lang.String name) {
super(null, null, null);
_name = name;
}


@java.lang.Override
public java.lang.String getName() {
return _name;
}


@java.lang.Override
public com.beemdevelopment.aegis.icons.IconType getIconType() {
return null;
}

}

public static class CategoryHeader {
private final java.lang.String _category;

private int _position = -1;

private final java.util.List<com.beemdevelopment.aegis.icons.IconPack.Icon> _icons;

private boolean _collapsed = true;

public CategoryHeader(java.lang.String category) {
_category = category;
_icons = new java.util.ArrayList<>();
}


public java.lang.String getCategory() {
return _category;
}


public int getPosition() {
return _position;
}


public void setPosition(int position) {
_position = position;
}


public java.util.List<com.beemdevelopment.aegis.icons.IconPack.Icon> getIcons() {
return _icons;
}


public boolean isCollapsed() {
return _collapsed;
}


public void setIsCollapsed(boolean collapsed) {
_collapsed = collapsed;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
