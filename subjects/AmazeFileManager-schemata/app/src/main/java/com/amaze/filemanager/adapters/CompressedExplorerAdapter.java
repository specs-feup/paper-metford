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
package com.amaze.filemanager.adapters;
import com.amaze.filemanager.filesystem.compressed.showcontents.Decompressor;
import com.amaze.filemanager.adapters.data.CompressedObjectParcelable;
import java.util.ArrayList;
import android.text.format.Formatter;
import com.amaze.filemanager.ui.colors.ColorUtils;
import com.amaze.filemanager.R;
import android.app.Activity;
import android.view.animation.AnimationUtils;
import android.os.Build;
import java.util.List;
import android.widget.Toast;
import com.amaze.filemanager.ui.theme.AppTheme;
import com.amaze.filemanager.ui.views.CircleGradientDrawable;
import android.content.SharedPreferences;
import com.amaze.filemanager.adapters.holders.CompressedItemViewHolder;
import android.view.ViewGroup;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.text.TextUtils;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageButton;
import com.amaze.filemanager.GlideApp;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import android.graphics.drawable.GradientDrawable;
import com.amaze.filemanager.ui.fragments.CompressedExplorerFragment;
import com.amaze.filemanager.ui.provider.UtilitiesProvider;
import android.view.LayoutInflater;
import com.amaze.filemanager.utils.AnimUtils;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import com.amaze.filemanager.filesystem.compressed.CompressedHelper;
import android.view.animation.Animation;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by Arpit on 25-01-2015 edited by Emmanuel Messulam<emmanuelbendavid@gmail.com>
 */
public class CompressedExplorerAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.amaze.filemanager.adapters.holders.CompressedItemViewHolder> {
    static final int MUID_STATIC = getMUID();
    private static final int TYPE_HEADER = 0;

    private static final int TYPE_ITEM = 1;

    public boolean stoppedAnimation = false;

    private android.content.Context context;

    private com.amaze.filemanager.ui.provider.UtilitiesProvider utilsProvider;

    private android.graphics.drawable.Drawable folder;

    private java.util.List<com.amaze.filemanager.adapters.data.CompressedObjectParcelable> items;

    private com.amaze.filemanager.ui.fragments.CompressedExplorerFragment compressedExplorerFragment;

    private com.amaze.filemanager.filesystem.compressed.showcontents.Decompressor decompressor;

    private android.view.LayoutInflater mInflater;

    private boolean[] itemsChecked;

    private int offset = 0;

    private android.content.SharedPreferences sharedPrefs;

    public CompressedExplorerAdapter(android.content.Context c, com.amaze.filemanager.ui.provider.UtilitiesProvider utilsProvider, java.util.List<com.amaze.filemanager.adapters.data.CompressedObjectParcelable> items, com.amaze.filemanager.ui.fragments.CompressedExplorerFragment compressedExplorerFragment, com.amaze.filemanager.filesystem.compressed.showcontents.Decompressor decompressor, android.content.SharedPreferences sharedPrefs) {
        setHasStableIds(true);
        this.utilsProvider = utilsProvider;
        this.items = items;
        this.decompressor = decompressor;
        itemsChecked = new boolean[items.size()];
        context = c;
        if (c == null)
            return;

        folder = c.getResources().getDrawable(com.amaze.filemanager.R.drawable.ic_grid_folder_new);
        this.compressedExplorerFragment = compressedExplorerFragment;
        mInflater = ((android.view.LayoutInflater) (c.getSystemService(android.app.Activity.LAYOUT_INFLATER_SERVICE)));
        this.sharedPrefs = sharedPrefs;
    }


    public void toggleChecked(boolean check) {
        int k;
        k = 0;
        for (int i = k; i < items.size(); i++) {
            itemsChecked[i] = check;
            notifyItemChanged(i);
        }
    }


    public java.util.ArrayList<java.lang.Integer> getCheckedItemPositions() {
        java.util.ArrayList<java.lang.Integer> checkedItemPositions;
        checkedItemPositions = new java.util.ArrayList<>();
        for (int i = 0; i < itemsChecked.length; i++) {
            if (itemsChecked[i]) {
                checkedItemPositions.add(i);
            }
        }
        return checkedItemPositions;
    }


    /**
     * called as to toggle selection of any item in adapter
     *
     * @param position
     * 		the position of the item
     * @param imageView
     * 		the circular {@link CircleGradientDrawable} that is to be animated
     */
    private void toggleChecked(int position, androidx.appcompat.widget.AppCompatImageView imageView) {
        compressedExplorerFragment.stopAnim();
        stoppedAnimation = true;
        android.view.animation.Animation animation;
        if (itemsChecked[position]) {
            animation = android.view.animation.AnimationUtils.loadAnimation(context, com.amaze.filemanager.R.anim.check_out);
        } else {
            animation = android.view.animation.AnimationUtils.loadAnimation(context, com.amaze.filemanager.R.anim.check_in);
        }
        if (imageView != null) {
            imageView.setAnimation(animation);
        } else {
            // TODO: we don't have the check icon object probably because of config change
        }
        itemsChecked[position] = !itemsChecked[position];
        notifyDataSetChanged();
        if ((!compressedExplorerFragment.selection) || (compressedExplorerFragment.mActionMode == null)) {
            compressedExplorerFragment.selection = true;
            /* compressedExplorerFragment.mActionMode = compressedExplorerFragment.getActivity().startActionMode(
            compressedExplorerFragment.mActionModeCallback);
             */
            compressedExplorerFragment.mActionMode = compressedExplorerFragment.requireMainActivity().getAppbar().getToolbar().startActionMode(compressedExplorerFragment.mActionModeCallback);
        }
        compressedExplorerFragment.mActionMode.invalidate();
        if (getCheckedItemPositions().size() == 0) {
            compressedExplorerFragment.selection = false;
            compressedExplorerFragment.mActionMode.finish();
            compressedExplorerFragment.mActionMode = null;
        }
    }


    private void animate(com.amaze.filemanager.adapters.holders.CompressedItemViewHolder holder) {
        holder.rl.clearAnimation();
        android.view.animation.Animation localAnimation;
        localAnimation = android.view.animation.AnimationUtils.loadAnimation(compressedExplorerFragment.getActivity(), com.amaze.filemanager.R.anim.fade_in_top);
        localAnimation.setStartOffset(this.offset);
        holder.rl.startAnimation(localAnimation);
        switch(MUID_STATIC) {
            // CompressedExplorerAdapter_0_BinaryMutator
            case 62: {
                this.offset = 30 - this.offset;
                break;
            }
            default: {
            this.offset = 30 + this.offset;
            break;
        }
    }
}


public void generateZip(java.util.List<com.amaze.filemanager.adapters.data.CompressedObjectParcelable> arrayList) {
    offset = 0;
    stoppedAnimation = false;
    items = arrayList;
    notifyDataSetChanged();
    itemsChecked = new boolean[items.size()];
}


@java.lang.Override
public long getItemId(int position) {
    return position;
}


@java.lang.Override
public int getItemViewType(int position) {
    if (isPositionHeader(position))
        return com.amaze.filemanager.adapters.CompressedExplorerAdapter.TYPE_HEADER;

    return com.amaze.filemanager.adapters.CompressedExplorerAdapter.TYPE_ITEM;
}


@java.lang.Override
public com.amaze.filemanager.adapters.holders.CompressedItemViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
    if (viewType == com.amaze.filemanager.adapters.CompressedExplorerAdapter.TYPE_HEADER) {
        android.view.View v;
        v = mInflater.inflate(com.amaze.filemanager.R.layout.rowlayout, parent, false);
        v.findViewById(com.amaze.filemanager.R.id.picture_icon).setVisibility(android.view.View.INVISIBLE);
        return new com.amaze.filemanager.adapters.holders.CompressedItemViewHolder(v);
    } else if (viewType == com.amaze.filemanager.adapters.CompressedExplorerAdapter.TYPE_ITEM) {
        android.view.View v;
        v = mInflater.inflate(com.amaze.filemanager.R.layout.rowlayout, parent, false);
        com.amaze.filemanager.adapters.holders.CompressedItemViewHolder vh;
        vh = new com.amaze.filemanager.adapters.holders.CompressedItemViewHolder(v);
        androidx.appcompat.widget.AppCompatImageButton about;
        switch(MUID_STATIC) {
            // CompressedExplorerAdapter_1_InvalidViewFocusOperatorMutator
            case 1062: {
                /**
                * Inserted by Kadabra
                */
                about = v.findViewById(com.amaze.filemanager.R.id.properties);
                about.requestFocus();
                break;
            }
            // CompressedExplorerAdapter_2_ViewComponentNotVisibleOperatorMutator
            case 2062: {
                /**
                * Inserted by Kadabra
                */
                about = v.findViewById(com.amaze.filemanager.R.id.properties);
                about.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            about = v.findViewById(com.amaze.filemanager.R.id.properties);
            break;
        }
    }
    about.setVisibility(android.view.View.INVISIBLE);
    return vh;
} else {
    throw new java.lang.IllegalStateException();
}
}


@java.lang.Override
public void onBindViewHolder(final com.amaze.filemanager.adapters.holders.CompressedItemViewHolder holder, int position) {
if (!stoppedAnimation) {
    animate(holder);
}
boolean enableMarquee;
enableMarquee = sharedPrefs.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ENABLE_MARQUEE_FILENAME, true);
holder.txtTitle.setEllipsize(enableMarquee ? android.text.TextUtils.TruncateAt.MARQUEE : android.text.TextUtils.TruncateAt.MIDDLE);
final com.amaze.filemanager.adapters.data.CompressedObjectParcelable rowItem;
rowItem = items.get(position);
android.graphics.drawable.GradientDrawable gradientDrawable;
gradientDrawable = ((android.graphics.drawable.GradientDrawable) (holder.genericIcon.getBackground()));
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
    holder.checkImageView.setBackground(new com.amaze.filemanager.ui.views.CircleGradientDrawable(compressedExplorerFragment.accentColor, utilsProvider.getAppTheme(), compressedExplorerFragment.getResources().getDisplayMetrics()));
} else
    holder.checkImageView.setBackgroundDrawable(new com.amaze.filemanager.ui.views.CircleGradientDrawable(compressedExplorerFragment.accentColor, utilsProvider.getAppTheme(), compressedExplorerFragment.getResources().getDisplayMetrics()));

if (rowItem.type == com.amaze.filemanager.adapters.data.CompressedObjectParcelable.TYPE_GOBACK) {
    com.amaze.filemanager.GlideApp.with(compressedExplorerFragment).load(com.amaze.filemanager.R.drawable.ic_arrow_left_white_24dp).into(holder.genericIcon);
    gradientDrawable.setColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.goback_item));
    holder.txtTitle.setText("..");
    holder.txtDesc.setText("");
    holder.date.setText(com.amaze.filemanager.R.string.goback);
} else {
    com.amaze.filemanager.GlideApp.with(compressedExplorerFragment).load(rowItem.iconData.image).into(holder.genericIcon);
    if (compressedExplorerFragment.showLastModified)
        holder.date.setText(com.amaze.filemanager.utils.Utils.getDate(context, rowItem.date));

    if (rowItem.directory) {
        holder.genericIcon.setImageDrawable(folder);
        gradientDrawable.setColor(compressedExplorerFragment.iconskin);
        holder.txtTitle.setText(rowItem.name);
    } else {
        if (compressedExplorerFragment.showSize)
            holder.txtDesc.setText(android.text.format.Formatter.formatFileSize(context, rowItem.size));

        switch(MUID_STATIC) {
            // CompressedExplorerAdapter_3_BinaryMutator
            case 3062: {
                holder.txtTitle.setText(rowItem.path.substring(rowItem.path.lastIndexOf("/") - 1));
                break;
            }
            default: {
            holder.txtTitle.setText(rowItem.path.substring(rowItem.path.lastIndexOf("/") + 1));
            break;
        }
    }
    if (compressedExplorerFragment.coloriseIcons) {
        com.amaze.filemanager.ui.colors.ColorUtils.colorizeIcons(context, rowItem.filetype, gradientDrawable, compressedExplorerFragment.iconskin);
    } else
        gradientDrawable.setColor(compressedExplorerFragment.iconskin);

}
}
holder.rl.setOnLongClickListener((android.view.View view) -> {
if (rowItem.type != com.amaze.filemanager.adapters.data.CompressedObjectParcelable.TYPE_GOBACK) {
    toggleChecked(position, holder.checkImageView);
}
return true;
});
switch(MUID_STATIC) {
// CompressedExplorerAdapter_4_BuggyGUIListenerOperatorMutator
case 4062: {
    holder.genericIcon.setOnClickListener(null);
    break;
}
default: {
holder.genericIcon.setOnClickListener((android.view.View view) -> {
    if (rowItem.type != com.amaze.filemanager.adapters.data.CompressedObjectParcelable.TYPE_GOBACK) {
        toggleChecked(position, holder.checkImageView);
    } else {
        compressedExplorerFragment.goBack();
    }
});
break;
}
}
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
holder.rl.setBackgroundResource(com.amaze.filemanager.R.drawable.safr_ripple_white);
} else {
holder.rl.setBackgroundResource(com.amaze.filemanager.R.drawable.safr_ripple_black);
}
holder.rl.setSelected(false);
if (itemsChecked[position]) {
// holder.genericIcon.setImageDrawable(compressedExplorerFragment.getResources().getDrawable(R.drawable.abc_ic_cab_done_holo_dark));
holder.checkImageView.setVisibility(android.view.View.VISIBLE);
gradientDrawable.setColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.goback_item));
holder.rl.setSelected(true);
} else
holder.checkImageView.setVisibility(android.view.View.INVISIBLE);

switch(MUID_STATIC) {
// CompressedExplorerAdapter_5_BuggyGUIListenerOperatorMutator
case 5062: {
holder.rl.setOnClickListener(null);
break;
}
default: {
holder.rl.setOnClickListener((android.view.View p1) -> {
if (rowItem.type == com.amaze.filemanager.adapters.data.CompressedObjectParcelable.TYPE_GOBACK)
    compressedExplorerFragment.goBack();
else if (compressedExplorerFragment.selection) {
    toggleChecked(position, holder.checkImageView);
} else if (rowItem.directory) {
    java.lang.String newPath;
    switch(MUID_STATIC) {
        // CompressedExplorerAdapter_6_BinaryMutator
        case 6062: {
            newPath = (rowItem.path.endsWith("/")) ? rowItem.path.substring(0, rowItem.path.length() + 1) : rowItem.path;
            break;
        }
        default: {
        newPath = (rowItem.path.endsWith("/")) ? rowItem.path.substring(0, rowItem.path.length() - 1) : rowItem.path;
        break;
    }
}
compressedExplorerFragment.changePath(newPath);
} else {
java.lang.String fileName;
fileName = com.amaze.filemanager.filesystem.compressed.CompressedHelper.getFileName(compressedExplorerFragment.compressedFile.getName());
java.lang.String archiveCacheDirPath;
archiveCacheDirPath = (compressedExplorerFragment.getActivity().getExternalCacheDir().getPath() + com.amaze.filemanager.filesystem.compressed.CompressedHelper.SEPARATOR) + fileName;
com.amaze.filemanager.filesystem.HybridFileParcelable file;
file = new com.amaze.filemanager.filesystem.HybridFileParcelable((archiveCacheDirPath + com.amaze.filemanager.filesystem.compressed.CompressedHelper.SEPARATOR) + rowItem.path.replaceAll("\\\\", com.amaze.filemanager.filesystem.compressed.CompressedHelper.SEPARATOR));
file.setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE);
// this file will be opened once service finishes up it's extraction
compressedExplorerFragment.files.add(file);
// setting flag for binder to know
compressedExplorerFragment.isOpen = true;
android.widget.Toast.makeText(compressedExplorerFragment.getContext(), compressedExplorerFragment.getContext().getString(com.amaze.filemanager.R.string.please_wait), android.widget.Toast.LENGTH_SHORT).show();
decompressor.decompress(compressedExplorerFragment.getActivity().getExternalCacheDir().getPath(), new java.lang.String[]{ rowItem.path });
}
});
break;
}
}
}


@java.lang.Override
public int getItemCount() {
return items.size();
}


@java.lang.Override
public void onViewDetachedFromWindow(com.amaze.filemanager.adapters.holders.CompressedItemViewHolder holder) {
super.onViewAttachedToWindow(holder);
holder.rl.clearAnimation();
holder.txtTitle.setSelected(false);
}


@java.lang.Override
public void onViewAttachedToWindow(com.amaze.filemanager.adapters.holders.CompressedItemViewHolder holder) {
super.onViewAttachedToWindow(holder);
boolean enableMarqueeFilename;
enableMarqueeFilename = sharedPrefs.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ENABLE_MARQUEE_FILENAME, true);
if (enableMarqueeFilename) {
com.amaze.filemanager.utils.AnimUtils.marqueeAfterDelay(2000, holder.txtTitle);
}
}


@java.lang.Override
public boolean onFailedToRecycleView(com.amaze.filemanager.adapters.holders.CompressedItemViewHolder holder) {
holder.rl.clearAnimation();
holder.txtTitle.setSelected(false);
return super.onFailedToRecycleView(holder);
}


private boolean isPositionHeader(int position) {
return false// TODO:
;// TODO:

}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
