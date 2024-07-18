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
import com.amaze.filemanager.filesystem.PasteHelper;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB;
import java.util.ArrayList;
import com.amaze.filemanager.ui.colors.ColorUtils;
import static com.amaze.filemanager.filesystem.compressed.CompressedHelper.*;
import android.app.Activity;
import androidx.annotation.NonNull;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_HEADERS;
import com.amaze.filemanager.adapters.data.IconDataParcelable;
import android.os.Build;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_FILE_SIZE;
import java.util.List;
import com.amaze.filemanager.adapters.holders.ItemViewHolder;
import com.amaze.filemanager.ui.theme.AppTheme;
import com.amaze.filemanager.ui.views.CircleGradientDrawable;
import java.util.Collections;
import android.graphics.Color;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.amaze.filemanager.adapters.holders.SpecialViewHolder;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.os.Handler;
import com.amaze.filemanager.ui.ItemPopupMenu;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatTextView;
import com.amaze.filemanager.GlideApp;
import com.bumptech.glide.load.DataSource;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES;
import androidx.appcompat.widget.AppCompatImageView;
import com.amaze.filemanager.adapters.data.LayoutElementParcelable;
import com.amaze.filemanager.ui.icons.MimeTypes;
import android.graphics.drawable.GradientDrawable;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_LAST_MODIFIED;
import com.amaze.filemanager.ui.provider.UtilitiesProvider;
import com.amaze.filemanager.adapters.glide.RecyclerPreloadModelProvider;
import android.view.LayoutInflater;
import androidx.appcompat.view.ActionMode;
import android.widget.PopupMenu;
import java.util.Objects;
import androidx.annotation.Nullable;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLORIZE_ICONS;
import com.amaze.filemanager.ui.icons.Icons;
import com.amaze.filemanager.ui.drag.RecyclerAdapterDragListener;
import com.amaze.filemanager.ui.activities.MainActivity;
import org.slf4j.Logger;
import com.amaze.filemanager.ui.fragments.MainFragment;
import com.bumptech.glide.request.target.Target;
import com.amaze.filemanager.R;
import android.view.animation.AnimationUtils;
import android.view.KeyEvent;
import com.amaze.filemanager.filesystem.files.CryptUtil;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.ui.activities.superclasses.PreferenceActivity;
import com.amaze.filemanager.filesystem.files.sort.DirSortBy;
import android.content.SharedPreferences;
import androidx.annotation.IntDef;
import com.bumptech.glide.request.RequestListener;
import com.amaze.filemanager.application.AppConfig;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_PERMISSIONS;
import android.view.ViewGroup;
import android.text.TextUtils;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import android.view.View;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON;
import com.amaze.filemanager.utils.AnimUtils;
import com.amaze.filemanager.utils.MainActivityActionMode;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.utils.GlideConstants;
import androidx.appcompat.view.ContextThemeWrapper;
import com.amaze.filemanager.adapters.glide.RecyclerPreloadSizeProvider;
import android.view.animation.Animation;
import com.amaze.filemanager.ui.selection.SelectionPopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.engine.GlideException;
import com.amaze.filemanager.adapters.holders.EmptyViewHolder;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * This class is the information that serves to load the files into a "list" (a RecyclerView). There
 * are 3 types of item TYPE_ITEM, TYPE_HEADER_FOLDERS and TYPE_HEADER_FILES, EMPTY_LAST_ITEM and
 * TYPE_BACK represeted by ItemViewHolder, SpecialViewHolder and EmptyViewHolder respectively. The
 * showPopup shows the file's popup menu. The 'go to parent' aka '..' button (go to settings to
 * activate it) is just a folder.
 *
 * <p>Created by Arpit on 11-04-2015 edited by Emmanuel Messulam <emmanuelbendavid@gmail.com> edited
 * by Jens Klingenberg <mail@jensklingenberg.de>
 */
public class RecyclerAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> implements com.amaze.filemanager.adapters.glide.RecyclerPreloadSizeProvider.RecyclerPreloadSizeProviderCallback {
    static final int MUID_STATIC = getMUID();
    public static final int TYPE_ITEM = 0;

    public static final int TYPE_HEADER_FOLDERS = 1;

    public static final int TYPE_HEADER_FILES = 2;

    public static final int EMPTY_LAST_ITEM = 3;

    public static final int TYPE_BACK = 4;

    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.adapters.RecyclerAdapter.class);

    private static final int VIEW_GENERIC = 0;

    private static final int VIEW_PICTURE = 1;

    private static final int VIEW_APK = 2;

    private static final int VIEW_THUMB = 3;

    public boolean stoppedAnimation = false;

    @androidx.annotation.NonNull
    private final com.amaze.filemanager.ui.activities.superclasses.PreferenceActivity preferenceActivity;

    @androidx.annotation.NonNull
    private final com.amaze.filemanager.ui.provider.UtilitiesProvider utilsProvider;

    @androidx.annotation.NonNull
    private final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;

    @androidx.annotation.NonNull
    private final android.content.SharedPreferences sharedPrefs;

    private com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader<com.amaze.filemanager.adapters.data.IconDataParcelable> preloader;

    private com.amaze.filemanager.adapters.glide.RecyclerPreloadSizeProvider sizeProvider;

    private com.amaze.filemanager.adapters.glide.RecyclerPreloadModelProvider modelProvider;

    @androidx.annotation.NonNull
    private final android.content.Context context;

    private final android.view.LayoutInflater mInflater;

    private final float minRowHeight;

    private final int grey_color;

    private final int accentColor;

    private final int iconSkinColor;

    private final int goBackColor;

    private final int videoColor;

    private final int audioColor;

    private final int pdfColor;

    private final int codeColor;

    private final int textColor;

    private final int archiveColor;

    private final int genericColor;

    private final int apkColor;

    private int offset = 0;

    private final boolean enableMarquee;

    private final int dragAndDropPreference;

    private final boolean isGrid;

    @androidx.annotation.IntDef({ com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_GENERIC, com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_PICTURE, com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_APK, com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_THUMB })
    public @interface ViewType {}

    @androidx.annotation.IntDef({ com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_ITEM, com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FOLDERS, com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FILES, com.amaze.filemanager.adapters.RecyclerAdapter.EMPTY_LAST_ITEM, com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_BACK })
    public @interface ListElemType {}

    public RecyclerAdapter(@androidx.annotation.NonNull
    com.amaze.filemanager.ui.activities.superclasses.PreferenceActivity preferenceActivity, @androidx.annotation.NonNull
    com.amaze.filemanager.ui.fragments.MainFragment mainFragment, @androidx.annotation.NonNull
    com.amaze.filemanager.ui.provider.UtilitiesProvider utilsProvider, @androidx.annotation.NonNull
    android.content.SharedPreferences sharedPrefs, @androidx.annotation.NonNull
    androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull
    java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> itemsRaw, @androidx.annotation.NonNull
    android.content.Context context, boolean isGrid) {
        setHasStableIds(true);
        this.preferenceActivity = preferenceActivity;
        this.mainFragment = mainFragment;
        this.utilsProvider = utilsProvider;
        this.context = context;
        this.sharedPrefs = sharedPrefs;
        this.enableMarquee = sharedPrefs.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ENABLE_MARQUEE_FILENAME, true);
        this.dragAndDropPreference = sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_AND_DROP_PREFERENCE, com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_DEFAULT);
        this.isGrid = isGrid;
        mInflater = ((android.view.LayoutInflater) (context.getSystemService(android.app.Activity.LAYOUT_INFLATER_SERVICE)));
        accentColor = mainFragment.getMainActivity().getAccent();
        iconSkinColor = mainFragment.getMainActivity().getCurrentColorPreference().getIconSkin();
        goBackColor = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.goback_item);
        videoColor = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.video_item);
        audioColor = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.audio_item);
        pdfColor = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.pdf_item);
        codeColor = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.code_item);
        textColor = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.text_item);
        archiveColor = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.archive_item);
        genericColor = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.generic_item);
        minRowHeight = context.getResources().getDimension(com.amaze.filemanager.R.dimen.minimal_row_height);
        grey_color = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.grey);
        apkColor = com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.apk_item);
        setItems(recyclerView, itemsRaw, false);
    }


    /**
     * called as to toggle selection of any item in adapter
     *
     * @param position
     * 		the position of the item
     * @param imageView
     * 		the check {@link CircleGradientDrawable} that is to be animated
     */
    public void toggleChecked(int position, androidx.appcompat.widget.AppCompatImageView imageView) {
        if ((getItemsDigested().size() <= position) || (position < 0)) {
            com.amaze.filemanager.application.AppConfig.toast(context, com.amaze.filemanager.R.string.operation_not_supported);
            return;
        }
        if (getItemsDigested().get(position).getChecked() == com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.UNCHECKABLE) {
            throw new java.lang.IllegalArgumentException("You have checked a header");
        }
        if (!stoppedAnimation) {
            mainFragment.stopAnimation();
        }
        if (getItemsDigested().get(position).getChecked() == com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED) {
            // if the view at position is checked, un-check it
            LOG.debug("the view at position {} is checked, un-check it", position);
            getItemsDigested().get(position).setChecked(false);
            android.view.animation.Animation iconAnimation;
            iconAnimation = android.view.animation.AnimationUtils.loadAnimation(context, com.amaze.filemanager.R.anim.check_out);
            if (imageView != null) {
                imageView.clearAnimation();
                imageView.startAnimation(iconAnimation);
            } else {
                // TODO: we don't have the check icon object probably because of config change
            }
        } else {
            // if view is un-checked, check it
            LOG.debug("the view at position {} is unchecked, check it", position);
            getItemsDigested().get(position).setChecked(true);
            android.view.animation.Animation iconAnimation;
            iconAnimation = android.view.animation.AnimationUtils.loadAnimation(context, com.amaze.filemanager.R.anim.check_in);
            if (imageView != null) {
                imageView.clearAnimation();
                imageView.startAnimation(iconAnimation);
            } else {
                // TODO: we don't have the check icon object probably because of config change
            }
        }
        invalidateSelection();
        notifyItemChanged(position);
    }


    private void invalidateSelection() {
        if (mainFragment.getMainFragmentViewModel() != null) {
            mainFragment.getMainActivity().setListItemSelected(mainFragment.getMainFragmentViewModel().getCheckedItems().size() != 0);
        }
    }


    public void invalidateActionMode() {
        if (mainFragment.getMainFragmentViewModel() != null) {
            // we have the actionmode visible, invalidate it's views
            if (mainFragment.getMainActivity().getListItemSelected()) {
                if (mainFragment.getMainActivity().getActionModeHelper().getActionMode() == null) {
                    androidx.appcompat.view.ActionMode.Callback mActionModeCallback;
                    mActionModeCallback = mainFragment.getMainActivity().getActionModeHelper();
                    mainFragment.getMainActivity().getActionModeHelper().setActionMode(mainFragment.getMainActivity().startSupportActionMode(mActionModeCallback));
                } else {
                    mainFragment.getMainActivity().getActionModeHelper().getActionMode().invalidate();
                }
            } else if (mainFragment.getMainActivity().getActionModeHelper().getActionMode() != null) {
                mainFragment.getMainActivity().getActionModeHelper().getActionMode().finish();
                mainFragment.getMainActivity().getActionModeHelper().setActionMode(null);
            }
        }
    }


    public void toggleChecked(boolean selectAll, java.lang.String path) {
        int i;
        i = (path.equals("/") || (!getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON))) ? 0 : 1;
        for (; i < getItemsDigested().size(); i++) {
            com.amaze.filemanager.adapters.RecyclerAdapter.ListItem item;
            item = getItemsDigested().get(i);
            if (selectAll && (item.getChecked() != com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED)) {
                item.setChecked(true);
                notifyItemChanged(i);
            } else if ((!selectAll) && (item.getChecked() == com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED)) {
                item.setChecked(false);
                notifyItemChanged(i);
            }
        }
        invalidateSelection();
        invalidateActionMode();
    }


    public void toggleInverse(java.lang.String path) {
        int i;
        i = (path.equals("/") || (!getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON))) ? 0 : 1;
        for (; i < getItemsDigested().size(); i++) {
            com.amaze.filemanager.adapters.RecyclerAdapter.ListItem item;
            item = getItemsDigested().get(i);
            if (item.getChecked() != com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED) {
                item.setChecked(true);
                notifyItemChanged(i);
            } else if (item.getChecked() == com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED) {
                item.setChecked(false);
                notifyItemChanged(i);
            }
        }
    }


    public void toggleSameTypes() {
        java.util.ArrayList<java.lang.Integer> checkedItemsIndexes;
        checkedItemsIndexes = getCheckedItemsIndex();
        for (int i = 0; i < checkedItemsIndexes.size(); i++) {
            com.amaze.filemanager.adapters.RecyclerAdapter.ListItem selectedItem;
            selectedItem = getItemsDigested().get(checkedItemsIndexes.get(i));
            if (!selectedItem.specialTypeHasFile()) {
                continue;
            }
            com.amaze.filemanager.adapters.data.LayoutElementParcelable selectedElement;
            selectedElement = selectedItem.requireLayoutElementParcelable();
            for (int z = 0; z < getItemsDigested().size(); z++) {
                com.amaze.filemanager.adapters.RecyclerAdapter.ListItem currentItem;
                currentItem = getItemsDigested().get(z);
                if (!currentItem.specialTypeHasFile()) {
                    // header type list item ('Files' / 'Folders')
                    continue;
                }
                com.amaze.filemanager.adapters.data.LayoutElementParcelable currentElement;
                currentElement = currentItem.requireLayoutElementParcelable();
                if (selectedElement.isDirectory || currentElement.isDirectory) {
                    if (selectedElement.isDirectory && currentElement.isDirectory) {
                        if (currentItem.getChecked() != com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED) {
                            currentItem.setChecked(true);
                            notifyItemChanged(z);
                        }
                    }
                } else {
                    java.lang.String mimeTypeCurrentItem;
                    mimeTypeCurrentItem = com.amaze.filemanager.ui.icons.MimeTypes.getExtension(currentElement.desc);
                    java.lang.String mimeTypeSelectedElement;
                    mimeTypeSelectedElement = com.amaze.filemanager.ui.icons.MimeTypes.getExtension(selectedElement.desc);
                    if (mimeTypeCurrentItem.equalsIgnoreCase(mimeTypeSelectedElement) && (currentItem.getChecked() != com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED)) {
                        currentItem.setChecked(true);
                        notifyItemChanged(z);
                    }
                }
            }
        }
    }


    public void toggleSameDates() {
        java.util.ArrayList<java.lang.Integer> checkedItemsIndexes;
        checkedItemsIndexes = getCheckedItemsIndex();
        for (int i = 0; i < checkedItemsIndexes.size(); i++) {
            com.amaze.filemanager.adapters.RecyclerAdapter.ListItem selectedItem;
            selectedItem = getItemsDigested().get(checkedItemsIndexes.get(i));
            if (!selectedItem.specialTypeHasFile()) {
                continue;
            }
            com.amaze.filemanager.adapters.data.LayoutElementParcelable selectedElement;
            selectedElement = selectedItem.requireLayoutElementParcelable();
            for (int y = 0; y < getItemsDigested().size(); y++) {
                com.amaze.filemanager.adapters.RecyclerAdapter.ListItem currentItem;
                currentItem = getItemsDigested().get(y);
                if (!currentItem.specialTypeHasFile()) {
                    // header type list item ('Files' / 'Folders')
                    continue;
                }
                java.lang.String dateModifiedCurrentItem;
                dateModifiedCurrentItem = currentItem.requireLayoutElementParcelable().dateModification.split("\\|")[0];
                java.lang.String dateModifiedSelectedElement;
                dateModifiedSelectedElement = selectedElement.dateModification.split("\\|")[0];
                if (dateModifiedCurrentItem.trim().equalsIgnoreCase(dateModifiedSelectedElement.trim()) && (currentItem.getChecked() != com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED)) {
                    currentItem.setChecked(true);
                    notifyItemChanged(y);
                }
            }
        }
    }


    public void toggleFill() {
        java.util.ArrayList<java.lang.Integer> checkedItemsIndexes;
        checkedItemsIndexes = getCheckedItemsIndex();
        java.util.Collections.sort(checkedItemsIndexes);
        if (checkedItemsIndexes.size() >= 2) {
            for (int i = checkedItemsIndexes.get(0); i < checkedItemsIndexes.get(checkedItemsIndexes.size() - 1); i++) {
                java.util.Objects.requireNonNull(getItemsDigested()).get(i).setChecked(true);
                notifyItemChanged(i);
            }
        }
    }


    public void toggleSimilarNames() {
        java.util.ArrayList<java.lang.Integer> checkedItemsIndexes;
        checkedItemsIndexes = getCheckedItemsIndex();
        for (int i = 0; i < checkedItemsIndexes.size(); i++) {
            com.amaze.filemanager.adapters.RecyclerAdapter.ListItem selectedItem;
            selectedItem = getItemsDigested().get(checkedItemsIndexes.get(i));
            if (!selectedItem.specialTypeHasFile()) {
                continue;
            }
            com.amaze.filemanager.adapters.data.LayoutElementParcelable selectedElement;
            selectedElement = selectedItem.requireLayoutElementParcelable();
            int fuzzinessFactor;
            switch(MUID_STATIC) {
                // RecyclerAdapter_0_BinaryMutator
                case 61: {
                    fuzzinessFactor = selectedElement.title.length() * com.amaze.filemanager.ui.selection.SelectionPopupMenu.FUZZYNESS_FACTOR;
                    break;
                }
                default: {
                fuzzinessFactor = selectedElement.title.length() / com.amaze.filemanager.ui.selection.SelectionPopupMenu.FUZZYNESS_FACTOR;
                break;
            }
        }
        if (fuzzinessFactor >= 1) {
            for (int z = 0; z < getItemsDigested().size(); z++) {
                com.amaze.filemanager.adapters.RecyclerAdapter.ListItem currentItem;
                currentItem = getItemsDigested().get(z);
                if (!currentItem.specialTypeHasFile()) {
                    // header type list item ('Files' / 'Folders')
                    continue;
                }
                int remainingFuzzyness;
                remainingFuzzyness = fuzzinessFactor;
                char[] currentItemName;
                currentItemName = currentItem.requireLayoutElementParcelable().title.toCharArray();
                char[] selectedElementName;
                selectedElementName = selectedElement.title.toCharArray();
                boolean isSimilar;
                isSimilar = true;
                for (int j = 0; j < java.lang.Math.min(currentItemName.length, selectedElementName.length); j++) {
                    if ((currentItemName[j] != selectedElementName[j]) && ((remainingFuzzyness--) < 0)) {
                        isSimilar = false;
                        break;
                    }
                }
                switch(MUID_STATIC) {
                    // RecyclerAdapter_1_BinaryMutator
                    case 1061: {
                        if (isSimilar && (java.lang.Math.abs(currentItemName.length + selectedElementName.length) <= remainingFuzzyness)) {
                            if (currentItem.getChecked() != com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED) {
                                currentItem.setChecked(true);
                                notifyItemChanged(z);
                            }
                        }
                        break;
                    }
                    default: {
                    if (isSimilar && (java.lang.Math.abs(currentItemName.length - selectedElementName.length) <= remainingFuzzyness)) {
                        if (currentItem.getChecked() != com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED) {
                            currentItem.setChecked(true);
                            notifyItemChanged(z);
                        }
                    }
                    break;
                }
            }
        }
    }
}
}


/**
 * called when we would want to toggle check for all items in the adapter
 *
 * @param b
 * 		if to toggle true or false
 */
public void toggleChecked(boolean b) {
for (int i = 0; i < getItemsDigested().size(); i++) {
    com.amaze.filemanager.adapters.RecyclerAdapter.ListItem item;
    item = getItemsDigested().get(i);
    if (b && (item.getChecked() != com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED)) {
        item.setChecked(true);
        notifyItemChanged(i);
    } else if ((!b) && (item.getChecked() == com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED)) {
        item.setChecked(false);
        notifyItemChanged(i);
    }
}
invalidateSelection();
invalidateActionMode();
}


@androidx.annotation.NonNull
public java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> getCheckedItems() {
return mainFragment.getMainFragmentViewModel().getCheckedItems();
}


@androidx.annotation.Nullable
public java.util.ArrayList<com.amaze.filemanager.adapters.RecyclerAdapter.ListItem> getItemsDigested() {
return mainFragment.getMainFragmentViewModel() != null ? mainFragment.getMainFragmentViewModel().getAdapterListItems() : null;
}


public boolean isItemsDigestedNullOrEmpty() {
return (getItemsDigested() == null) || getItemsDigested().isEmpty();
}


public boolean areAllChecked(java.lang.String path) {
int i;
i = (path.equals("/") || (!getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON))) ? 0 : 1;
for (; i < getItemsDigested().size(); i++) {
    if (getItemsDigested().get(i).getChecked() == com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.NOT_CHECKED) {
        return false;
    }
}
return true;
}


public java.util.ArrayList<java.lang.Integer> getCheckedItemsIndex() {
java.util.ArrayList<java.lang.Integer> checked;
checked = new java.util.ArrayList<>();
for (int i = 0; i < getItemsDigested().size(); i++) {
    if (getItemsDigested().get(i).getChecked() == com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED) {
        checked.add(i);
    }
}
return checked;
}


@java.lang.Override
public void onViewDetachedFromWindow(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder holder) {
if (holder instanceof com.amaze.filemanager.adapters.holders.ItemViewHolder) {
    ((com.amaze.filemanager.adapters.holders.ItemViewHolder) (holder)).baseItemView.clearAnimation();
    ((com.amaze.filemanager.adapters.holders.ItemViewHolder) (holder)).txtTitle.setSelected(false);
    if (dragAndDropPreference != com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_DEFAULT) {
        ((com.amaze.filemanager.adapters.holders.ItemViewHolder) (holder)).baseItemView.setOnDragListener(null);
    }
}
super.onViewDetachedFromWindow(holder);
}


@java.lang.Override
public void onViewAttachedToWindow(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder holder) {
super.onViewAttachedToWindow(holder);
boolean enableMarqueeFilename;
enableMarqueeFilename = sharedPrefs.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ENABLE_MARQUEE_FILENAME, true);
if (enableMarqueeFilename && (holder instanceof com.amaze.filemanager.adapters.holders.ItemViewHolder)) {
    com.amaze.filemanager.utils.AnimUtils.marqueeAfterDelay(2000, ((com.amaze.filemanager.adapters.holders.ItemViewHolder) (holder)).txtTitle);
}
super.onViewAttachedToWindow(holder);
}


@java.lang.Override
public boolean onFailedToRecycleView(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder holder) {
((com.amaze.filemanager.adapters.holders.ItemViewHolder) (holder)).baseItemView.clearAnimation();
((com.amaze.filemanager.adapters.holders.ItemViewHolder) (holder)).txtTitle.setSelected(false);
return super.onFailedToRecycleView(holder);
}


private void animate(com.amaze.filemanager.adapters.holders.ItemViewHolder holder) {
holder.baseItemView.clearAnimation();
android.view.animation.Animation localAnimation;
localAnimation = android.view.animation.AnimationUtils.loadAnimation(context, com.amaze.filemanager.R.anim.fade_in_top);
localAnimation.setStartOffset(this.offset);
holder.baseItemView.startAnimation(localAnimation);
this.offset += 30;
}


/**
 * Adds item to the end of the list, don't use this unless you are dynamically loading the
 * adapter, after you are finished you must call createHeaders
 */
public void addItem(@androidx.annotation.NonNull
com.amaze.filemanager.adapters.data.LayoutElementParcelable element) {
// TODO: simplify if condition
if (((mainFragment.getMainFragmentViewModel() != null) && mainFragment.getMainFragmentViewModel().isList()) && (getItemsDigested().size() > 0)) {
    switch(MUID_STATIC) {
        // RecyclerAdapter_2_BinaryMutator
        case 2061: {
            getItemsDigested().add(getItemsDigested().size() + 1, new com.amaze.filemanager.adapters.RecyclerAdapter.ListItem(element));
            break;
        }
        default: {
        getItemsDigested().add(getItemsDigested().size() - 1, new com.amaze.filemanager.adapters.RecyclerAdapter.ListItem(element));
        break;
    }
}
} else if ((mainFragment.getMainFragmentViewModel() != null) && mainFragment.getMainFragmentViewModel().isList()) {
getItemsDigested().add(new com.amaze.filemanager.adapters.RecyclerAdapter.ListItem(element));
getItemsDigested().add(new com.amaze.filemanager.adapters.RecyclerAdapter.ListItem(com.amaze.filemanager.adapters.RecyclerAdapter.EMPTY_LAST_ITEM));
} else {
getItemsDigested().add(new com.amaze.filemanager.adapters.RecyclerAdapter.ListItem(element));
}
notifyItemInserted(getItemCount());
}


public void setItems(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> elements) {
setItems(recyclerView, elements, true);
}


private void setItems(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull
java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> elements, boolean invalidate) {
if (preloader != null) {
recyclerView.removeOnScrollListener(preloader);
preloader = null;
}
if ((getItemsDigested() != null) && invalidate) {
getItemsDigested().clear();
if (mainFragment.getMainFragmentViewModel().getIconList() != null) {
    mainFragment.getMainFragmentViewModel().getIconList().clear();
}
}
offset = 0;
stoppedAnimation = false;
java.util.ArrayList<com.amaze.filemanager.adapters.data.IconDataParcelable> uris;
uris = new java.util.ArrayList<>();
java.util.ArrayList<com.amaze.filemanager.adapters.RecyclerAdapter.ListItem> listItems;
listItems = new java.util.ArrayList<>();
for (com.amaze.filemanager.adapters.data.LayoutElementParcelable e : elements) {
if (invalidate || isItemsDigestedNullOrEmpty()) {
    if (e != null) {
        listItems.add(new com.amaze.filemanager.adapters.RecyclerAdapter.ListItem(e.isBack, e));
    }
    uris.add(e != null ? e.iconData : null);
}
}
if ((((mainFragment.getMainFragmentViewModel() != null) && mainFragment.getMainFragmentViewModel().isList()) && (listItems.size() > 0)) && (invalidate || isItemsDigestedNullOrEmpty())) {
listItems.add(new com.amaze.filemanager.adapters.RecyclerAdapter.ListItem(com.amaze.filemanager.adapters.RecyclerAdapter.EMPTY_LAST_ITEM));
uris.add(null);
}
if (invalidate || isItemsDigestedNullOrEmpty()) {
mainFragment.getMainFragmentViewModel().setAdapterListItems(listItems);
mainFragment.getMainFragmentViewModel().setIconList(uris);
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_HEADERS)) {
    createHeaders(invalidate, mainFragment.getMainFragmentViewModel().getIconList());
}
}
boolean isItemCircular;
isItemCircular = !isGrid;
sizeProvider = new com.amaze.filemanager.adapters.glide.RecyclerPreloadSizeProvider(this);
modelProvider = new com.amaze.filemanager.adapters.glide.RecyclerPreloadModelProvider(mainFragment, mainFragment.getMainFragmentViewModel().getIconList(), isItemCircular);
preloader = new com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader<>(com.amaze.filemanager.GlideApp.with(mainFragment), modelProvider, sizeProvider, com.amaze.filemanager.utils.GlideConstants.MAX_PRELOAD_FILES);
recyclerView.addOnScrollListener(preloader);
}


public void createHeaders(boolean invalidate, java.util.List<com.amaze.filemanager.adapters.data.IconDataParcelable> uris) {
if ((((mainFragment.getMainFragmentViewModel() != null) && (mainFragment.getMainFragmentViewModel().getDsort() == com.amaze.filemanager.filesystem.files.sort.DirSortBy.NONE_ON_TOP)) || (getItemsDigested() == null)) || getItemsDigested().isEmpty()) {
return;
} else {
boolean[] headers;
headers = new boolean[]{ false, false };
for (int i = 0; i < getItemsDigested().size(); i++) {
    if (getItemsDigested().get(i).layoutElementParcelable != null) {
        com.amaze.filemanager.adapters.data.LayoutElementParcelable nextItem;
        nextItem = getItemsDigested().get(i).layoutElementParcelable;
        if (nextItem != null) {
            if ((!headers[0]) && nextItem.isDirectory) {
                headers[0] = true;
                getItemsDigested().add(i, new com.amaze.filemanager.adapters.RecyclerAdapter.ListItem(com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FOLDERS));
                uris.add(i, null);
                continue;
            }
            if ((((!headers[1]) && (!nextItem.isDirectory)) && (!nextItem.title.equals("."))) && (!nextItem.title.equals(".."))) {
                headers[1] = true;
                getItemsDigested().add(i, new com.amaze.filemanager.adapters.RecyclerAdapter.ListItem(com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FILES));
                uris.add(i, null);
                continue// leave this continue for symmetry
                ;// leave this continue for symmetry

            }
        }
    }
}
if (invalidate) {
    notifyDataSetChanged();
}
}
}


@java.lang.Override
public int getItemCount() {
return getItemsDigested().size();
}


@java.lang.Override
public long getItemId(int position) {
return position;
}


@java.lang.Override
public int getItemViewType(int position) {
if (getItemsDigested().get(position).specialType != (-1)) {
return getItemsDigested().get(position).specialType;
} else {
return com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_ITEM;
}
}


@androidx.annotation.NonNull
@java.lang.Override
public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull
android.view.ViewGroup parent, int viewType) {
android.view.View view;
switch (viewType) {
case com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FOLDERS :
case com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FILES :
    if ((mainFragment.getMainFragmentViewModel() != null) && mainFragment.getMainFragmentViewModel().isList()) {
        view = mInflater.inflate(com.amaze.filemanager.R.layout.list_header, parent, false);
    } else {
        view = mInflater.inflate(com.amaze.filemanager.R.layout.grid_header, parent, false);
    }
    int type;
    type = (viewType == com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FOLDERS) ? com.amaze.filemanager.adapters.holders.SpecialViewHolder.HEADER_FOLDERS : com.amaze.filemanager.adapters.holders.SpecialViewHolder.HEADER_FILES;
    return new com.amaze.filemanager.adapters.holders.SpecialViewHolder(context, view, utilsProvider, type);
case com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_ITEM :
case com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_BACK :
    if ((mainFragment.getMainFragmentViewModel() != null) && mainFragment.getMainFragmentViewModel().isList()) {
        view = mInflater.inflate(com.amaze.filemanager.R.layout.rowlayout, parent, false);
        sizeProvider.addView(com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_GENERIC, view.findViewById(com.amaze.filemanager.R.id.generic_icon));
        sizeProvider.addView(com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_PICTURE, view.findViewById(com.amaze.filemanager.R.id.picture_icon));
        sizeProvider.addView(com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_APK, view.findViewById(com.amaze.filemanager.R.id.apk_icon));
    } else {
        view = mInflater.inflate(com.amaze.filemanager.R.layout.griditem, parent, false);
        sizeProvider.addView(com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_GENERIC, view.findViewById(com.amaze.filemanager.R.id.generic_icon));
        sizeProvider.addView(com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_THUMB, view.findViewById(com.amaze.filemanager.R.id.icon_thumb));
    }
    sizeProvider.closeOffAddition();
    return new com.amaze.filemanager.adapters.holders.ItemViewHolder(view);
case com.amaze.filemanager.adapters.RecyclerAdapter.EMPTY_LAST_ITEM :
    int totalFabHeight;
    totalFabHeight = ((int) (context.getResources().getDimension(com.amaze.filemanager.R.dimen.fab_height)));
    int marginFab;
    marginFab = ((int) (context.getResources().getDimension(com.amaze.filemanager.R.dimen.fab_margin)));
    view = new android.view.View(context);
    switch(MUID_STATIC) {
        // RecyclerAdapter_3_BinaryMutator
        case 3061: {
            view.setMinimumHeight(totalFabHeight - marginFab);
            break;
        }
        default: {
        view.setMinimumHeight(totalFabHeight + marginFab);
        break;
    }
}
return new com.amaze.filemanager.adapters.holders.EmptyViewHolder(view);
default :
throw new java.lang.IllegalArgumentException("Illegal: " + viewType);
}
}


@java.lang.Override
public void onBindViewHolder(@androidx.annotation.NonNull
final androidx.recyclerview.widget.RecyclerView.ViewHolder vholder, int position) {
if (!(vholder instanceof com.amaze.filemanager.adapters.holders.ItemViewHolder)) {
return;
}
@androidx.annotation.NonNull
final com.amaze.filemanager.adapters.holders.ItemViewHolder holder;
holder = ((com.amaze.filemanager.adapters.holders.ItemViewHolder) (vholder));
holder.baseItemView.setOnFocusChangeListener((android.view.View v,boolean hasFocus) -> {
if (hasFocus) {
mainFragment.adjustListViewForTv(holder, mainFragment.getMainActivity());
}
});
holder.txtTitle.setEllipsize(enableMarquee ? android.text.TextUtils.TruncateAt.MARQUEE : android.text.TextUtils.TruncateAt.MIDDLE);
final boolean isBackButton;
isBackButton = getItemsDigested().get(position).specialType == com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_BACK;
if (isBackButton) {
holder.about.setVisibility(android.view.View.GONE);
}
if ((!this.stoppedAnimation) && (!getItemsDigested().get(position).getAnimating())) {
animate(holder);
getItemsDigested().get(position).setAnimate(true);
}
if (dragAndDropPreference != com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_DEFAULT) {
holder.baseItemView.setOnDragListener(new com.amaze.filemanager.ui.drag.RecyclerAdapterDragListener(this, holder, dragAndDropPreference, mainFragment));
}
if (mainFragment.getMainFragmentViewModel().isList()) {
bindViewHolderList(holder, position);
} else {
bindViewHolderGrid(holder, position);
}
invalidateActionMode();
}


private void bindViewHolderList(@androidx.annotation.NonNull
final com.amaze.filemanager.adapters.holders.ItemViewHolder holder, int position) {
final boolean isBackButton;
isBackButton = getItemsDigested().get(position).specialType == com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_BACK;
@androidx.annotation.Nullable
final com.amaze.filemanager.adapters.data.LayoutElementParcelable rowItem;
rowItem = getItemsDigested().get(position).layoutElementParcelable;
switch(MUID_STATIC) {
// RecyclerAdapter_4_BinaryMutator
case 4061: {
if ((mainFragment.getMainFragmentViewModel() != null) && (position == (getItemCount() + 1))) {
    holder.baseItemView.setMinimumHeight(((int) (minRowHeight)));
    if (getItemsDigested().size() == (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON) ? 1 : 0)) {
        holder.txtTitle.setText(com.amaze.filemanager.R.string.no_files);
    } else {
        holder.txtTitle.setText("");
    }
    return;
}
break;
}
default: {
if ((mainFragment.getMainFragmentViewModel() != null) && (position == (getItemCount() - 1))) {
holder.baseItemView.setMinimumHeight(((int) (minRowHeight)));
if (getItemsDigested().size() == (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON) ? 1 : 0))
    holder.txtTitle.setText(com.amaze.filemanager.R.string.no_files);
else {
    holder.txtTitle.setText("");
}
return;
}
break;
}
}
holder.baseItemView.setOnLongClickListener((android.view.View p1) -> {
if (hasPendingPasteOperation())
return false;

if (!isBackButton) {
if ((dragAndDropPreference == com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_DEFAULT) || ((dragAndDropPreference == com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_TO_MOVE_COPY) && (getItemsDigested().get(holder.getAdapterPosition()).getChecked() != com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED))) {
mainFragment.registerListItemChecked(holder.getAdapterPosition(), holder.checkImageView);
}
initDragListener(position, p1, holder);
}
return true;
});
// clear previously cached icon
com.amaze.filemanager.GlideApp.with(mainFragment).clear(holder.genericIcon);
com.amaze.filemanager.GlideApp.with(mainFragment).clear(holder.pictureIcon);
com.amaze.filemanager.GlideApp.with(mainFragment).clear(holder.apkIcon);
com.amaze.filemanager.GlideApp.with(mainFragment).clear(holder.baseItemView);
switch(MUID_STATIC) {
// RecyclerAdapter_5_BuggyGUIListenerOperatorMutator
case 5061: {
holder.baseItemView.setOnClickListener(null);
break;
}
default: {
holder.baseItemView.setOnClickListener((android.view.View v) -> {
mainFragment.onListItemClicked(isBackButton, holder.getAdapterPosition(), rowItem, holder.checkImageView);
});
break;
}
}
holder.about.setOnKeyListener((android.view.View v,int keyCode,android.view.KeyEvent event) -> {
if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
if (event.getKeyCode() == android.view.KeyEvent.KEYCODE_DPAD_RIGHT) {
mainFragment.getMainActivity().getFAB().requestFocus();
} else if (event.getKeyCode() == android.view.KeyEvent.KEYCODE_DPAD_CENTER) {
showPopup(v, rowItem);
} else if (event.getKeyCode() == android.view.KeyEvent.KEYCODE_BACK) {
mainFragment.getMainActivity().onBackPressed();
} else {
return false;
}
}
return true;
});
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
holder.checkImageView.setBackground(new com.amaze.filemanager.ui.views.CircleGradientDrawable(accentColor, utilsProvider.getAppTheme(), mainFragment.getResources().getDisplayMetrics()));
} else {
holder.checkImageView.setBackgroundDrawable(new com.amaze.filemanager.ui.views.CircleGradientDrawable(accentColor, utilsProvider.getAppTheme(), mainFragment.getResources().getDisplayMetrics()));
}
holder.txtTitle.setText(rowItem.title);
holder.genericText.setText("");
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
holder.about.setColorFilter(grey_color);
}
switch(MUID_STATIC) {
// RecyclerAdapter_6_BuggyGUIListenerOperatorMutator
case 6061: {
holder.about.setOnClickListener(null);
break;
}
default: {
holder.about.setOnClickListener((android.view.View v) -> showPopup(v, rowItem));
break;
}
}
switch(MUID_STATIC) {
// RecyclerAdapter_7_BuggyGUIListenerOperatorMutator
case 7061: {
holder.genericIcon.setOnClickListener(null);
break;
}
default: {
holder.genericIcon.setOnClickListener((android.view.View v) -> {
int id;
id = v.getId();
if (((id == com.amaze.filemanager.R.id.generic_icon) || (id == com.amaze.filemanager.R.id.picture_icon)) || (id == com.amaze.filemanager.R.id.apk_icon)) {
// TODO: transform icon on press to the properties dialog with animation
if (!isBackButton) {
toggleChecked(holder.getAdapterPosition(), holder.checkImageView);
} else
mainFragment.goBack();

}
});
break;
}
}
switch(MUID_STATIC) {
// RecyclerAdapter_8_BuggyGUIListenerOperatorMutator
case 8061: {
holder.pictureIcon.setOnClickListener(null);
break;
}
default: {
holder.pictureIcon.setOnClickListener((android.view.View view) -> {
if (!isBackButton) {
toggleChecked(holder.getAdapterPosition(), holder.checkImageView);
} else
mainFragment.goBack();

});
break;
}
}
switch(MUID_STATIC) {
// RecyclerAdapter_9_BuggyGUIListenerOperatorMutator
case 9061: {
holder.apkIcon.setOnClickListener(null);
break;
}
default: {
holder.apkIcon.setOnClickListener((android.view.View view) -> {
if (!isBackButton) {
toggleChecked(holder.getAdapterPosition(), holder.checkImageView);
} else
mainFragment.goBack();

});
break;
}
}
// resetting icons visibility
holder.genericIcon.setVisibility(android.view.View.VISIBLE);
holder.pictureIcon.setVisibility(android.view.View.INVISIBLE);
holder.apkIcon.setVisibility(android.view.View.INVISIBLE);
holder.checkImageView.setVisibility(android.view.View.INVISIBLE);
// setting icons for various cases
// apkIcon holder refers to square/non-circular drawable
// pictureIcon is circular drawable
switch (rowItem.filetype) {
case com.amaze.filemanager.ui.icons.Icons.IMAGE :
case com.amaze.filemanager.ui.icons.Icons.VIDEO :
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB) && (rowItem.getMode() != com.amaze.filemanager.fileoperations.filesystem.OpenMode.FTP)) {
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES)) {
showThumbnailWithBackground(holder, rowItem.iconData, holder.pictureIcon, rowItem.iconData::setImageBroken);
} else {
showThumbnailWithBackground(holder, rowItem.iconData, holder.apkIcon, rowItem.iconData::setImageBroken);
}
} else {
holder.genericIcon.setImageResource(rowItem.filetype == com.amaze.filemanager.ui.icons.Icons.IMAGE ? com.amaze.filemanager.R.drawable.ic_doc_image : com.amaze.filemanager.R.drawable.ic_doc_video_am);
}
break;
case com.amaze.filemanager.ui.icons.Icons.APK :
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB)) {
showThumbnailWithBackground(holder, rowItem.iconData, holder.apkIcon, rowItem.iconData::setImageBroken);
} else {
holder.genericIcon.setImageResource(com.amaze.filemanager.R.drawable.ic_doc_apk_white);
}
break;
case com.amaze.filemanager.ui.icons.Icons.NOT_KNOWN :
holder.genericIcon.setVisibility(android.view.View.VISIBLE);
// if the file type is any unknown variable
java.lang.String ext;
ext = (!rowItem.isDirectory) ? com.amaze.filemanager.ui.icons.MimeTypes.getExtension(rowItem.title) : null;
if ((ext != null) && (ext.trim().length() != 0)) {
holder.genericText.setText(ext);
holder.genericIcon.setImageDrawable(null);
holder.genericIcon.setVisibility(android.view.View.INVISIBLE);
} else {
// we could not find the extension, set a generic file type icon probably a directory
modelProvider.getPreloadRequestBuilder(rowItem.iconData).into(holder.genericIcon);
}
break;
case com.amaze.filemanager.ui.icons.Icons.ENCRYPTED :
default :
holder.genericIcon.setVisibility(android.view.View.VISIBLE);
modelProvider.getPreloadRequestBuilder(rowItem.iconData).into(holder.genericIcon);
break;
}
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
holder.baseItemView.setBackgroundResource(com.amaze.filemanager.R.drawable.safr_ripple_white);
} else {
holder.baseItemView.setBackgroundResource(com.amaze.filemanager.R.drawable.safr_ripple_black);
}
holder.baseItemView.setSelected(false);
if (getItemsDigested().get(position).getChecked() == com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED) {
if (holder.checkImageView.getVisibility() == android.view.View.INVISIBLE)
holder.checkImageView.setVisibility(android.view.View.VISIBLE);

// making sure the generic icon background color filter doesn't get changed
// to grey on picture/video/apk/generic text icons when checked
// so that user can still look at the thumbs even after selection
if ((((rowItem.filetype != com.amaze.filemanager.ui.icons.Icons.IMAGE) && (rowItem.filetype != com.amaze.filemanager.ui.icons.Icons.APK)) && (rowItem.filetype != com.amaze.filemanager.ui.icons.Icons.VIDEO)) || (!getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB))) {
holder.apkIcon.setVisibility(android.view.View.GONE);
holder.pictureIcon.setVisibility(android.view.View.GONE);
holder.genericIcon.setVisibility(android.view.View.VISIBLE);
android.graphics.drawable.GradientDrawable gradientDrawable;
gradientDrawable = ((android.graphics.drawable.GradientDrawable) (holder.genericIcon.getBackground()));
gradientDrawable.setColor(goBackColor);
}
holder.baseItemView.setSelected(true);
// holder.genericText.setText("");
} else {
holder.checkImageView.setVisibility(android.view.View.INVISIBLE);
if (!((((rowItem.filetype == com.amaze.filemanager.ui.icons.Icons.APK) || (rowItem.filetype == com.amaze.filemanager.ui.icons.Icons.IMAGE)) || (rowItem.filetype == com.amaze.filemanager.ui.icons.Icons.VIDEO)) && getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB))) {
holder.genericIcon.setVisibility(android.view.View.VISIBLE);
android.graphics.drawable.GradientDrawable gradientDrawable;
gradientDrawable = ((android.graphics.drawable.GradientDrawable) (holder.genericIcon.getBackground()));
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_COLORIZE_ICONS)) {
if (rowItem.isDirectory) {
gradientDrawable.setColor(iconSkinColor);
} else {
com.amaze.filemanager.ui.colors.ColorUtils.colorizeIcons(context, rowItem.filetype, gradientDrawable, iconSkinColor);
}
} else {
gradientDrawable.setColor(iconSkinColor);
}
if (isBackButton) {
gradientDrawable.setColor(goBackColor);
}
}
}
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_PERMISSIONS)) {
holder.perm.setText(rowItem.permissions);
}
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_LAST_MODIFIED)) {
holder.date.setText(rowItem.dateModification);
} else {
holder.date.setVisibility(android.view.View.GONE);
}
if (isBackButton) {
holder.date.setText(rowItem.size);
holder.txtDesc.setText("");
} else if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_FILE_SIZE)) {
holder.txtDesc.setText(rowItem.size);
}
}


private void bindViewHolderGrid(@androidx.annotation.NonNull
final com.amaze.filemanager.adapters.holders.ItemViewHolder holder, int position) {
final boolean isBackButton;
isBackButton = getItemsDigested().get(position).specialType == com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_BACK;
@androidx.annotation.Nullable
final com.amaze.filemanager.adapters.data.LayoutElementParcelable rowItem;
rowItem = getItemsDigested().get(position).layoutElementParcelable;
holder.baseItemView.setOnLongClickListener((android.view.View p1) -> {
if (hasPendingPasteOperation())
return false;

if (!isBackButton) {
if ((dragAndDropPreference == com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_DEFAULT) || ((dragAndDropPreference == com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_TO_MOVE_COPY) && (getItemsDigested().get(holder.getAdapterPosition()).getChecked() != com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED))) {
mainFragment.registerListItemChecked(holder.getAdapterPosition(), holder.checkImageViewGrid);
}
initDragListener(position, p1, holder);
}
return true;
});
// view is a grid view
// clear previously cached icon
com.amaze.filemanager.GlideApp.with(mainFragment).clear(holder.genericIcon);
com.amaze.filemanager.GlideApp.with(mainFragment).clear(holder.iconLayout);
com.amaze.filemanager.GlideApp.with(mainFragment).clear(holder.imageView1);
com.amaze.filemanager.GlideApp.with(mainFragment).clear(holder.baseItemView);
holder.checkImageViewGrid.setColorFilter(accentColor);
switch(MUID_STATIC) {
// RecyclerAdapter_10_BuggyGUIListenerOperatorMutator
case 10061: {
holder.baseItemView.setOnClickListener(null);
break;
}
default: {
holder.baseItemView.setOnClickListener((android.view.View v) -> {
mainFragment.onListItemClicked(isBackButton, holder.getAdapterPosition(), rowItem, holder.checkImageViewGrid);
});
break;
}
}
holder.txtTitle.setText(rowItem.title);
holder.imageView1.setVisibility(android.view.View.INVISIBLE);
holder.genericIcon.setVisibility(android.view.View.VISIBLE);
holder.checkImageViewGrid.setVisibility(android.view.View.INVISIBLE);
if ((rowItem.filetype == com.amaze.filemanager.ui.icons.Icons.IMAGE) || (rowItem.filetype == com.amaze.filemanager.ui.icons.Icons.VIDEO)) {
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB) && (rowItem.getMode() != com.amaze.filemanager.fileoperations.filesystem.OpenMode.FTP)) {
holder.imageView1.setVisibility(android.view.View.VISIBLE);
holder.imageView1.setImageDrawable(null);
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
holder.imageView1.setBackgroundColor(android.graphics.Color.BLACK);

showRoundedThumbnail(holder, rowItem.iconData, holder.imageView1, rowItem.iconData::setImageBroken);
} else if (rowItem.filetype == com.amaze.filemanager.ui.icons.Icons.IMAGE)
holder.genericIcon.setImageResource(com.amaze.filemanager.R.drawable.ic_doc_image);
else
holder.genericIcon.setImageResource(com.amaze.filemanager.R.drawable.ic_doc_video_am);

} else if (rowItem.filetype == com.amaze.filemanager.ui.icons.Icons.APK) {
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB))
showRoundedThumbnail(holder, rowItem.iconData, holder.genericIcon, rowItem.iconData::setImageBroken);
else {
holder.genericIcon.setImageResource(com.amaze.filemanager.R.drawable.ic_doc_apk_white);
}
} else {
com.amaze.filemanager.GlideApp.with(mainFragment).load(rowItem.iconData.image).into(holder.genericIcon);
}
if (holder.genericIcon.getVisibility() == android.view.View.VISIBLE) {
android.view.View iconBackground;
iconBackground = (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES)) ? holder.genericIcon : holder.iconLayout;
if (rowItem.isDirectory) {
iconBackground.setBackgroundColor(iconSkinColor);
} else {
switch (rowItem.filetype) {
case com.amaze.filemanager.ui.icons.Icons.VIDEO :
if (!getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB))
iconBackground.setBackgroundColor(videoColor);

break;
case com.amaze.filemanager.ui.icons.Icons.AUDIO :
iconBackground.setBackgroundColor(audioColor);
break;
case com.amaze.filemanager.ui.icons.Icons.PDF :
iconBackground.setBackgroundColor(pdfColor);
break;
case com.amaze.filemanager.ui.icons.Icons.CODE :
iconBackground.setBackgroundColor(codeColor);
break;
case com.amaze.filemanager.ui.icons.Icons.TEXT :
iconBackground.setBackgroundColor(textColor);
break;
case com.amaze.filemanager.ui.icons.Icons.COMPRESSED :
iconBackground.setBackgroundColor(archiveColor);
break;
case com.amaze.filemanager.ui.icons.Icons.NOT_KNOWN :
iconBackground.setBackgroundColor(genericColor);
break;
case com.amaze.filemanager.ui.icons.Icons.APK :
if (!getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB))
iconBackground.setBackgroundColor(apkColor);

break;
case com.amaze.filemanager.ui.icons.Icons.IMAGE :
if (!getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB))
iconBackground.setBackgroundColor(videoColor);

break;
default :
iconBackground.setBackgroundColor(iconSkinColor);
break;
}
}
if (isBackButton) {
iconBackground.setBackgroundColor(goBackColor);
}
}
if (getItemsDigested().get(position).getChecked() == com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED) {
if (holder.genericIcon.getVisibility() == android.view.View.VISIBLE) {
if ((((rowItem.filetype != com.amaze.filemanager.ui.icons.Icons.IMAGE) && (rowItem.filetype != com.amaze.filemanager.ui.icons.Icons.APK)) && (rowItem.filetype != com.amaze.filemanager.ui.icons.Icons.VIDEO)) || (!getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB))) {
android.view.View iconBackground;
iconBackground = (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES)) ? holder.genericIcon : holder.iconLayout;
iconBackground.setBackgroundColor(goBackColor);
}
}
holder.checkImageViewGrid.setVisibility(android.view.View.VISIBLE);
holder.baseItemView.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.item_background));
} else {
holder.checkImageViewGrid.setVisibility(android.view.View.INVISIBLE);
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
holder.baseItemView.setBackgroundResource(com.amaze.filemanager.R.drawable.item_doc_grid);
} else {
holder.baseItemView.setBackgroundResource(com.amaze.filemanager.R.drawable.ic_grid_card_background_dark);
holder.baseItemView.findViewById(com.amaze.filemanager.R.id.icon_frame_grid).setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(context, com.amaze.filemanager.R.color.icon_background_dark));
}
}
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
holder.about.setColorFilter(grey_color);
}
switch(MUID_STATIC) {
// RecyclerAdapter_11_BuggyGUIListenerOperatorMutator
case 11061: {
holder.about.setOnClickListener(null);
break;
}
default: {
holder.about.setOnClickListener((android.view.View v) -> showPopup(v, rowItem));
break;
}
}
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_LAST_MODIFIED)) {
holder.date.setText(rowItem.dateModification);
}
if (isBackButton) {
holder.date.setText(rowItem.size);
holder.txtDesc.setText("");
}
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_PERMISSIONS)) {
holder.perm.setText(rowItem.permissions);
}
}


@java.lang.Override
@com.amaze.filemanager.adapters.RecyclerAdapter.ViewType
public int getCorrectView(com.amaze.filemanager.adapters.data.IconDataParcelable item, int adapterPosition) {
int specialType;
specialType = getItemsDigested().get(adapterPosition).specialType;
if ((specialType != com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_ITEM) && (specialType != com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_BACK)) {
// These have no icons
throw new java.lang.IllegalStateException("Setting view type to wrong item");
}
if ((mainFragment.getMainFragmentViewModel() != null) && mainFragment.getMainFragmentViewModel().isList()) {
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB)) {
int filetype;
filetype = getItemsDigested().get(adapterPosition).requireLayoutElementParcelable().filetype;
if ((filetype == com.amaze.filemanager.ui.icons.Icons.VIDEO) || (filetype == com.amaze.filemanager.ui.icons.Icons.IMAGE)) {
if (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES)) {
return com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_PICTURE;
} else {
return com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_APK;
}
} else if (filetype == com.amaze.filemanager.ui.icons.Icons.APK) {
return com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_APK;
}
}
return com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_GENERIC;
} else if (item.type == com.amaze.filemanager.adapters.data.IconDataParcelable.IMAGE_FROMFILE) {
return com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_THUMB;
} else {
return com.amaze.filemanager.adapters.RecyclerAdapter.VIEW_GENERIC;
}
}


private void initDragListener(int position, android.view.View view, com.amaze.filemanager.adapters.holders.ItemViewHolder itemViewHolder) {
if ((dragAndDropPreference != com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_DEFAULT) && ((getItemsDigested().get(position).getChecked() == com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED) || (dragAndDropPreference == com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_TO_SELECT))) {
// toggle drag flag to true for list item due to the fact
// that we might have set it false in a previous drag event
if (!getItemsDigested().get(position).shouldToggleDragChecked) {
getItemsDigested().get(position).toggleShouldToggleDragChecked();
}
android.view.View shadowView;
shadowView = (dragAndDropPreference == com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_TO_SELECT) ? itemViewHolder.dummyView : getDragShadow(getCheckedItems().size());
android.view.View.DragShadowBuilder dragShadowBuilder;
dragShadowBuilder = new android.view.View.DragShadowBuilder(shadowView);
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
view.startDragAndDrop(null, dragShadowBuilder, null, 0);
} else {
view.startDrag(null, dragShadowBuilder, null, 0);
}
mainFragment.getMainActivity().initCornersDragListener(false, dragAndDropPreference != com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_TO_SELECT);
}
}


private android.view.View getDragShadow(int selectionCount) {
mainFragment.getMainActivity().getTabFragment().getDragPlaceholder().setVisibility(android.view.View.VISIBLE);
java.lang.String rememberMovePreference;
rememberMovePreference = sharedPrefs.getString(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_AND_DROP_REMEMBERED, "");
androidx.appcompat.widget.AppCompatImageView icon;
switch(MUID_STATIC) {
// RecyclerAdapter_12_InvalidViewFocusOperatorMutator
case 12061: {
/**
* Inserted by Kadabra
*/
icon = mainFragment.getMainActivity().getTabFragment().getDragPlaceholder().findViewById(com.amaze.filemanager.R.id.icon);
icon.requestFocus();
break;
}
// RecyclerAdapter_13_ViewComponentNotVisibleOperatorMutator
case 13061: {
/**
* Inserted by Kadabra
*/
icon = mainFragment.getMainActivity().getTabFragment().getDragPlaceholder().findViewById(com.amaze.filemanager.R.id.icon);
icon.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
icon = mainFragment.getMainActivity().getTabFragment().getDragPlaceholder().findViewById(com.amaze.filemanager.R.id.icon);
break;
}
}
android.view.View filesCountParent;
switch(MUID_STATIC) {
// RecyclerAdapter_14_InvalidViewFocusOperatorMutator
case 14061: {
/**
* Inserted by Kadabra
*/
filesCountParent = mainFragment.getMainActivity().getTabFragment().getDragPlaceholder().findViewById(com.amaze.filemanager.R.id.files_count_parent);
filesCountParent.requestFocus();
break;
}
// RecyclerAdapter_15_ViewComponentNotVisibleOperatorMutator
case 15061: {
/**
* Inserted by Kadabra
*/
filesCountParent = mainFragment.getMainActivity().getTabFragment().getDragPlaceholder().findViewById(com.amaze.filemanager.R.id.files_count_parent);
filesCountParent.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
filesCountParent = mainFragment.getMainActivity().getTabFragment().getDragPlaceholder().findViewById(com.amaze.filemanager.R.id.files_count_parent);
break;
}
}
androidx.appcompat.widget.AppCompatTextView filesCount;
switch(MUID_STATIC) {
// RecyclerAdapter_16_InvalidViewFocusOperatorMutator
case 16061: {
/**
* Inserted by Kadabra
*/
filesCount = mainFragment.getMainActivity().getTabFragment().getDragPlaceholder().findViewById(com.amaze.filemanager.R.id.files_count);
filesCount.requestFocus();
break;
}
// RecyclerAdapter_17_ViewComponentNotVisibleOperatorMutator
case 17061: {
/**
* Inserted by Kadabra
*/
filesCount = mainFragment.getMainActivity().getTabFragment().getDragPlaceholder().findViewById(com.amaze.filemanager.R.id.files_count);
filesCount.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
filesCount = mainFragment.getMainActivity().getTabFragment().getDragPlaceholder().findViewById(com.amaze.filemanager.R.id.files_count);
break;
}
}
icon.setImageDrawable(context.getResources().getDrawable(getDragIconReference(rememberMovePreference)));
android.graphics.drawable.GradientDrawable gradientDrawable;
gradientDrawable = ((android.graphics.drawable.GradientDrawable) (icon.getBackground()));
gradientDrawable.setColor(grey_color);
filesCount.setText(java.lang.String.valueOf(selectionCount));
filesCountParent.setBackgroundDrawable(new com.amaze.filemanager.ui.views.CircleGradientDrawable(accentColor, utilsProvider.getAppTheme(), mainFragment.getResources().getDisplayMetrics()));
return mainFragment.getMainActivity().getTabFragment().getDragPlaceholder();
}


private int getDragIconReference(java.lang.String rememberMovePreference) {
int iconRef;
iconRef = com.amaze.filemanager.R.drawable.ic_add_white_24dp;
if (rememberMovePreference.equalsIgnoreCase(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_REMEMBER_MOVE)) {
iconRef = com.amaze.filemanager.R.drawable.ic_content_cut_white_36dp;
} else if (rememberMovePreference.equalsIgnoreCase(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DRAG_REMEMBER_COPY)) {
iconRef = com.amaze.filemanager.R.drawable.ic_content_copy_white_24dp;
}
return iconRef;
}


private void showThumbnailWithBackground(com.amaze.filemanager.adapters.holders.ItemViewHolder viewHolder, com.amaze.filemanager.adapters.data.IconDataParcelable iconData, androidx.appcompat.widget.AppCompatImageView view, com.amaze.filemanager.adapters.RecyclerAdapter.OnImageProcessed errorListener) {
if (iconData.isImageBroken()) {
viewHolder.genericIcon.setVisibility(android.view.View.VISIBLE);
com.amaze.filemanager.GlideApp.with(mainFragment).load(com.amaze.filemanager.R.drawable.ic_broken_image_white_24dp).into(viewHolder.genericIcon);
android.graphics.drawable.GradientDrawable gradientDrawable;
gradientDrawable = ((android.graphics.drawable.GradientDrawable) (viewHolder.genericIcon.getBackground()));
gradientDrawable.setColor(grey_color);
errorListener.onImageProcessed(true);
return;
}
viewHolder.genericIcon.setVisibility(android.view.View.VISIBLE);
com.amaze.filemanager.GlideApp.with(mainFragment).load(iconData.loadingImage).into(viewHolder.genericIcon);
android.graphics.drawable.GradientDrawable gradientDrawable;
gradientDrawable = ((android.graphics.drawable.GradientDrawable) (viewHolder.genericIcon.getBackground()));
com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable> requestListener;
requestListener = new com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable>() {
@java.lang.Override
public boolean onLoadFailed(@androidx.annotation.Nullable
com.bumptech.glide.load.engine.GlideException e, java.lang.Object model, com.bumptech.glide.request.target.Target target, boolean isFirstResource) {
new android.os.Handler((android.os.Message msg) -> {
viewHolder.genericIcon.setVisibility(android.view.View.VISIBLE);
com.amaze.filemanager.GlideApp.with(mainFragment).load(com.amaze.filemanager.R.drawable.ic_broken_image_white_24dp).into(viewHolder.genericIcon);
return false;
}).obtainMessage().sendToTarget();
gradientDrawable.setColor(grey_color);
errorListener.onImageProcessed(true);
return true;
}


@java.lang.Override
public boolean onResourceReady(android.graphics.drawable.Drawable resource, java.lang.Object model, com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
viewHolder.genericIcon.setImageDrawable(null);
viewHolder.genericIcon.setVisibility(android.view.View.GONE);
gradientDrawable.setColor(mainFragment.getResources().getColor(android.R.color.transparent));
view.setVisibility(android.view.View.VISIBLE);
errorListener.onImageProcessed(false);
return false;
}

};
modelProvider.getPreloadRequestBuilder(iconData).listener(requestListener).into(view);
}


private void showRoundedThumbnail(com.amaze.filemanager.adapters.holders.ItemViewHolder viewHolder, com.amaze.filemanager.adapters.data.IconDataParcelable iconData, androidx.appcompat.widget.AppCompatImageView view, com.amaze.filemanager.adapters.RecyclerAdapter.OnImageProcessed errorListener) {
if (iconData.isImageBroken()) {
android.view.View iconBackground;
iconBackground = (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES)) ? viewHolder.genericIcon : viewHolder.iconLayout;
viewHolder.genericIcon.setVisibility(android.view.View.VISIBLE);
iconBackground.setBackgroundColor(grey_color);
com.amaze.filemanager.GlideApp.with(mainFragment).load(com.amaze.filemanager.R.drawable.ic_broken_image_white_24dp).into(viewHolder.genericIcon);
view.setVisibility(android.view.View.INVISIBLE);
errorListener.onImageProcessed(true);
return;
}
android.view.View iconBackground;
iconBackground = (getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_USE_CIRCULAR_IMAGES)) ? viewHolder.genericIcon : viewHolder.iconLayout;
viewHolder.genericIcon.setVisibility(android.view.View.VISIBLE);
com.amaze.filemanager.GlideApp.with(mainFragment).load(iconData.loadingImage).into(viewHolder.genericIcon);
view.setVisibility(android.view.View.INVISIBLE);
com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable> requestListener;
requestListener = new com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable>() {
@java.lang.Override
public boolean onLoadFailed(@androidx.annotation.Nullable
com.bumptech.glide.load.engine.GlideException e, java.lang.Object model, com.bumptech.glide.request.target.Target target, boolean isFirstResource) {
iconBackground.setBackgroundColor(grey_color);
new android.os.Handler((android.os.Message msg) -> {
com.amaze.filemanager.GlideApp.with(mainFragment).load(com.amaze.filemanager.R.drawable.ic_broken_image_white_24dp).into(viewHolder.genericIcon);
return false;
}).obtainMessage().sendToTarget();
errorListener.onImageProcessed(true);
return true;
}


@java.lang.Override
public boolean onResourceReady(android.graphics.drawable.Drawable resource, java.lang.Object model, com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
viewHolder.genericIcon.setImageDrawable(null);
viewHolder.genericIcon.setVisibility(android.view.View.GONE);
view.setVisibility(android.view.View.VISIBLE);
iconBackground.setBackgroundColor(mainFragment.getResources().getColor(android.R.color.transparent));
errorListener.onImageProcessed(false);
return false;
}

};
modelProvider.getPreloadRequestBuilder(iconData).listener(requestListener).into(view);
}


private void showPopup(@androidx.annotation.NonNull
android.view.View view, @androidx.annotation.NonNull
final com.amaze.filemanager.adapters.data.LayoutElementParcelable rowItem) {
if (hasPendingPasteOperation())
return;

android.content.Context currentContext;
currentContext = this.context;
if (mainFragment.getMainActivity().getAppTheme() == com.amaze.filemanager.ui.theme.AppTheme.BLACK) {
currentContext = new androidx.appcompat.view.ContextThemeWrapper(context, com.amaze.filemanager.R.style.overflow_black);
}
android.widget.PopupMenu popupMenu;
popupMenu = new com.amaze.filemanager.ui.ItemPopupMenu(currentContext, mainFragment.requireMainActivity(), utilsProvider, mainFragment, rowItem, view, sharedPrefs);
popupMenu.inflate(com.amaze.filemanager.R.menu.item_extras);
java.lang.String description;
description = rowItem.desc.toLowerCase();
if (rowItem.isDirectory) {
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.open_with).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.share).setVisible(false);
if (mainFragment.getMainActivity().mReturnIntent) {
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.return_select).setVisible(true);
}
} else {
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.book).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.compress).setVisible(true);
if ((((((((((((((((description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionZip) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionJar)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionApk)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionApks)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionRar)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionTar)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionGzipTarLong)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionGzipTarShort)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionBzip2TarLong)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionBzip2TarShort)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionTarXz)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionTarLzma)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtension7zip)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionGz)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionBzip2)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionLzma)) || description.endsWith(com.amaze.filemanager.filesystem.compressed.CompressedHelper.fileExtensionXz)) {
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.ex).setVisible(true);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.compress).setVisible(false);
}
}
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
if (description.endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION) || description.endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION)) {
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.decrypt).setVisible(true);
} else {
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.encrypt).setVisible(true);
}
}
if (rowItem.getMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN) {
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.return_select).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.cut).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.cpy).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.rename).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.encrypt).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.decrypt).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.about).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.compress).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.share).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.ex).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.book).setVisible(false);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.restore).setVisible(true);
popupMenu.getMenu().findItem(com.amaze.filemanager.R.id.delete).setVisible(true);
}
popupMenu.show();
}


/**
 * Helps in deciding whether to allow file modification or not, depending on the state of the
 * copy/paste operation.
 *
 * @return true if there is an unfinished copy/paste operation, false otherwise.
 */
private boolean hasPendingPasteOperation() {
com.amaze.filemanager.ui.activities.MainActivity mainActivity;
mainActivity = mainFragment.getMainActivity();
if (mainActivity == null)
return false;

com.amaze.filemanager.utils.MainActivityActionMode mainActivityActionMode;
mainActivityActionMode = mainActivity.mainActivityActionMode;
com.amaze.filemanager.filesystem.PasteHelper pasteHelper;
pasteHelper = mainActivityActionMode.getPasteHelper();
if (((pasteHelper != null) && (pasteHelper.getSnackbar() != null)) && pasteHelper.getSnackbar().isShown()) {
android.widget.Toast.makeText(mainFragment.requireContext(), mainFragment.getString(com.amaze.filemanager.R.string.complete_paste_warning), android.widget.Toast.LENGTH_LONG).show();
return true;
}
return false;
}


private boolean getBoolean(java.lang.String key) {
return preferenceActivity.getBoolean(key);
}


public static class ListItem {
public static final int CHECKED = 0;

public static final int NOT_CHECKED = 1;

public static final int UNCHECKABLE = 2;

/**
 * Not null if {@link ListItem#specialTypeHasFile()} returns true
 */
@androidx.annotation.Nullable
private final com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElementParcelable;

@com.amaze.filemanager.adapters.RecyclerAdapter.ListElemType
private final int specialType;

private boolean checked;

private boolean animate;

private boolean shouldToggleDragChecked = true;

ListItem(@androidx.annotation.NonNull
com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElementParcelable) {
this(false, layoutElementParcelable);
}


ListItem(boolean isBack, @androidx.annotation.NonNull
com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElementParcelable) {
this.layoutElementParcelable = layoutElementParcelable;
specialType = (isBack) ? com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_BACK : com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_ITEM;
}


ListItem(@com.amaze.filemanager.adapters.RecyclerAdapter.ListElemType
int specialType) {
this.specialType = specialType;
this.layoutElementParcelable = null;
}


public void setChecked(boolean checked) {
if (specialType == com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_ITEM)
this.checked = checked;

}


public int getChecked() {
if (checked)
return com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.CHECKED;
else if (specialType == com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_ITEM)
return com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.NOT_CHECKED;
else
return com.amaze.filemanager.adapters.RecyclerAdapter.ListItem.UNCHECKABLE;

}


@androidx.annotation.Nullable
public com.amaze.filemanager.adapters.data.LayoutElementParcelable getLayoutElementParcelable() {
return layoutElementParcelable;
}


/**
 * Check that {@link ListItem#specialTypeHasFile()} returns true, if it does this method doesn't
 * return null.
 */
@androidx.annotation.NonNull
public com.amaze.filemanager.adapters.data.LayoutElementParcelable requireLayoutElementParcelable() {
if (!specialTypeHasFile()) {
throw new java.lang.IllegalStateException(("Type of item " + specialType) + " has no LayoutElementParcelable!");
}
return layoutElementParcelable;
}


public int getSpecialType() {
return this.specialType;
}


/**
 * This method effectively has a contract that allows {@link ListItem#requireLayoutElementParcelable} afterwards without crashing.
 */
public boolean specialTypeHasFile() {
return (specialType == com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_ITEM) || (specialType == com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_BACK);
}


public boolean getShouldToggleDragChecked() {
return (!checked) && this.shouldToggleDragChecked;
}


public void toggleShouldToggleDragChecked() {
this.shouldToggleDragChecked = !this.shouldToggleDragChecked;
}


public void setAnimate(boolean animating) {
if (specialType == (-1))
this.animate = animating;

}


public boolean getAnimating() {
return animate;
}

}

public interface OnImageProcessed {
void onImageProcessed(boolean isImageBroken);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
