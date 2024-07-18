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
package com.amaze.filemanager.ui.views.appbar;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import com.amaze.filemanager.filesystem.files.FileUtils;
import java.util.ArrayList;
import com.amaze.filemanager.ui.activities.MainActivity;
import androidx.fragment.app.Fragment;
import com.amaze.filemanager.utils.BottomBarButtonPath;
import com.amaze.filemanager.ui.fragments.MainFragment;
import com.amaze.filemanager.utils.MainActivityHelper;
import com.amaze.filemanager.R;
import android.view.GestureDetector;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import android.animation.AnimatorListenerAdapter;
import android.view.KeyEvent;
import android.widget.HorizontalScrollView;
import android.view.Gravity;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.os.CountDownTimer;
import android.view.ViewGroup;
import com.amaze.filemanager.ui.fragments.TabFragment;
import android.os.Handler;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatButton;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CHANGEPATHS;
import com.amaze.filemanager.filesystem.HybridFile;
import com.amaze.filemanager.ui.fragments.CompressedExplorerFragment;
import androidx.annotation.ColorInt;
import com.amaze.filemanager.utils.Utils;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import java.util.Objects;
import android.animation.Animator;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * layout_appbar.xml contains the layout for AppBar and BottomBar
 *
 * <p>BottomBar, it lays under the toolbar, used to show data of what is being displayed in the
 * MainFragment, for example directory, folder and file amounts, etc.
 *
 * @author Emmanuel on 2/8/2017, at 23:31.
 */
public class BottomBar implements android.view.View.OnTouchListener {
    static final int MUID_STATIC = getMUID();
    private static final int PATH_ANIM_START_DELAY = 0;

    private static final int PATH_ANIM_END_DELAY = 0;

    private com.amaze.filemanager.ui.activities.MainActivity mainActivity;

    private com.amaze.filemanager.ui.views.appbar.AppBar appbar;

    private java.lang.String newPath;

    private android.widget.FrameLayout frame;

    private android.widget.LinearLayout pathLayout;

    private android.widget.LinearLayout buttons;

    private android.widget.HorizontalScrollView scroll;

    private android.widget.HorizontalScrollView pathScroll;

    private androidx.appcompat.widget.AppCompatTextView pathText;

    private androidx.appcompat.widget.AppCompatTextView fullPathText;

    private androidx.appcompat.widget.AppCompatTextView fullPathAnim;

    private android.widget.LinearLayout.LayoutParams buttonParams;

    private androidx.appcompat.widget.AppCompatImageButton buttonRoot;

    private androidx.appcompat.widget.AppCompatImageButton buttonStorage;

    private java.util.ArrayList<androidx.appcompat.widget.AppCompatImageView> arrowButtons = new java.util.ArrayList<>();

    private int lastUsedArrowButton = 0;

    private java.util.ArrayList<androidx.appcompat.widget.AppCompatButton> folderButtons = new java.util.ArrayList<>();

    private int lastUsedFolderButton = 0;

    private android.graphics.drawable.Drawable arrow;

    private android.os.CountDownTimer timer;

    private android.view.GestureDetector gestureDetector;

    public BottomBar(com.amaze.filemanager.ui.views.appbar.AppBar appbar, com.amaze.filemanager.ui.activities.MainActivity a) {
        mainActivity = a;
        this.appbar = appbar;
        switch(MUID_STATIC) {
            // BottomBar_0_InvalidViewFocusOperatorMutator
            case 123: {
                /**
                * Inserted by Kadabra
                */
                frame = a.findViewById(com.amaze.filemanager.R.id.buttonbarframe);
                frame.requestFocus();
                break;
            }
            // BottomBar_1_ViewComponentNotVisibleOperatorMutator
            case 1123: {
                /**
                * Inserted by Kadabra
                */
                frame = a.findViewById(com.amaze.filemanager.R.id.buttonbarframe);
                frame.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            frame = a.findViewById(com.amaze.filemanager.R.id.buttonbarframe);
            break;
        }
    }
    switch(MUID_STATIC) {
        // BottomBar_2_InvalidViewFocusOperatorMutator
        case 2123: {
            /**
            * Inserted by Kadabra
            */
            scroll = a.findViewById(com.amaze.filemanager.R.id.scroll);
            scroll.requestFocus();
            break;
        }
        // BottomBar_3_ViewComponentNotVisibleOperatorMutator
        case 3123: {
            /**
            * Inserted by Kadabra
            */
            scroll = a.findViewById(com.amaze.filemanager.R.id.scroll);
            scroll.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        scroll = a.findViewById(com.amaze.filemanager.R.id.scroll);
        break;
    }
}
switch(MUID_STATIC) {
    // BottomBar_4_InvalidViewFocusOperatorMutator
    case 4123: {
        /**
        * Inserted by Kadabra
        */
        buttons = a.findViewById(com.amaze.filemanager.R.id.buttons);
        buttons.requestFocus();
        break;
    }
    // BottomBar_5_ViewComponentNotVisibleOperatorMutator
    case 5123: {
        /**
        * Inserted by Kadabra
        */
        buttons = a.findViewById(com.amaze.filemanager.R.id.buttons);
        buttons.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    buttons = a.findViewById(com.amaze.filemanager.R.id.buttons);
    break;
}
}
switch(MUID_STATIC) {
// BottomBar_6_InvalidViewFocusOperatorMutator
case 6123: {
    /**
    * Inserted by Kadabra
    */
    pathLayout = a.findViewById(com.amaze.filemanager.R.id.pathbar);
    pathLayout.requestFocus();
    break;
}
// BottomBar_7_ViewComponentNotVisibleOperatorMutator
case 7123: {
    /**
    * Inserted by Kadabra
    */
    pathLayout = a.findViewById(com.amaze.filemanager.R.id.pathbar);
    pathLayout.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
pathLayout = a.findViewById(com.amaze.filemanager.R.id.pathbar);
break;
}
}
switch(MUID_STATIC) {
// BottomBar_8_InvalidViewFocusOperatorMutator
case 8123: {
/**
* Inserted by Kadabra
*/
pathScroll = a.findViewById(com.amaze.filemanager.R.id.scroll1);
pathScroll.requestFocus();
break;
}
// BottomBar_9_ViewComponentNotVisibleOperatorMutator
case 9123: {
/**
* Inserted by Kadabra
*/
pathScroll = a.findViewById(com.amaze.filemanager.R.id.scroll1);
pathScroll.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
pathScroll = a.findViewById(com.amaze.filemanager.R.id.scroll1);
break;
}
}
switch(MUID_STATIC) {
// BottomBar_10_InvalidViewFocusOperatorMutator
case 10123: {
/**
* Inserted by Kadabra
*/
fullPathText = a.findViewById(com.amaze.filemanager.R.id.fullpath);
fullPathText.requestFocus();
break;
}
// BottomBar_11_ViewComponentNotVisibleOperatorMutator
case 11123: {
/**
* Inserted by Kadabra
*/
fullPathText = a.findViewById(com.amaze.filemanager.R.id.fullpath);
fullPathText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
fullPathText = a.findViewById(com.amaze.filemanager.R.id.fullpath);
break;
}
}
switch(MUID_STATIC) {
// BottomBar_12_InvalidViewFocusOperatorMutator
case 12123: {
/**
* Inserted by Kadabra
*/
fullPathAnim = a.findViewById(com.amaze.filemanager.R.id.fullpath_anim);
fullPathAnim.requestFocus();
break;
}
// BottomBar_13_ViewComponentNotVisibleOperatorMutator
case 13123: {
/**
* Inserted by Kadabra
*/
fullPathAnim = a.findViewById(com.amaze.filemanager.R.id.fullpath_anim);
fullPathAnim.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
fullPathAnim = a.findViewById(com.amaze.filemanager.R.id.fullpath_anim);
break;
}
}
switch(MUID_STATIC) {
// BottomBar_14_InvalidViewFocusOperatorMutator
case 14123: {
/**
* Inserted by Kadabra
*/
pathText = a.findViewById(com.amaze.filemanager.R.id.pathname);
pathText.requestFocus();
break;
}
// BottomBar_15_ViewComponentNotVisibleOperatorMutator
case 15123: {
/**
* Inserted by Kadabra
*/
pathText = a.findViewById(com.amaze.filemanager.R.id.pathname);
pathText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
pathText = a.findViewById(com.amaze.filemanager.R.id.pathname);
break;
}
}
scroll.setSmoothScrollingEnabled(true);
pathScroll.setSmoothScrollingEnabled(true);
pathScroll.setOnKeyListener((android.view.View v,int keyCode,android.view.KeyEvent event) -> {
if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
if (event.getKeyCode() == android.view.KeyEvent.KEYCODE_DPAD_DOWN) {
mainActivity.findViewById(com.amaze.filemanager.R.id.content_frame).requestFocus();
} else if (event.getKeyCode() == android.view.KeyEvent.KEYCODE_DPAD_LEFT) {
mainActivity.getDrawer().getDonateImageView().requestFocus();
} else if (event.getKeyCode() == android.view.KeyEvent.KEYCODE_BACK) {
mainActivity.onBackPressed();
} else {
return false;
}
}
return true;
});
buttonParams = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
buttonParams.gravity = android.view.Gravity.CENTER_VERTICAL;
buttonRoot = new androidx.appcompat.widget.AppCompatImageButton(a);
buttonRoot.setBackgroundColor(android.graphics.Color.TRANSPARENT);
buttonRoot.setLayoutParams(buttonParams);
buttonStorage = new androidx.appcompat.widget.AppCompatImageButton(a);
buttonStorage.setImageDrawable(a.getResources().getDrawable(com.amaze.filemanager.R.drawable.ic_sd_storage_white_24dp));
buttonStorage.setBackgroundColor(android.graphics.Color.TRANSPARENT);
buttonStorage.setLayoutParams(buttonParams);
arrow = mainActivity.getResources().getDrawable(com.amaze.filemanager.R.drawable.ic_keyboard_arrow_right_white_24dp);
timer = new android.os.CountDownTimer(5000, 1000) {
@java.lang.Override
public void onTick(long l) {
}


@java.lang.Override
public void onFinish() {
com.amaze.filemanager.filesystem.files.FileUtils.crossfadeInverse(buttons, pathLayout);
}

};
gestureDetector = new android.view.GestureDetector(a.getApplicationContext(), new android.view.GestureDetector.SimpleOnGestureListener() {
@java.lang.Override
public boolean onDown(android.view.MotionEvent e) {
return true;
}


@java.lang.Override
public boolean onSingleTapConfirmed(android.view.MotionEvent e) {
androidx.fragment.app.Fragment fragmentAtFrame;
fragmentAtFrame = mainActivity.getFragmentAtFrame();
if (fragmentAtFrame instanceof com.amaze.filemanager.ui.fragments.TabFragment) {
final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;
mainFragment = mainActivity.getCurrentMainFragment();
java.util.Objects.requireNonNull(mainFragment);
if (((mainFragment.getMainFragmentViewModel() != null) && (com.amaze.filemanager.fileoperations.filesystem.OpenMode.CUSTOM != mainFragment.getMainFragmentViewModel().getOpenMode())) && (com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN != mainFragment.getMainFragmentViewModel().getOpenMode())) {
com.amaze.filemanager.filesystem.files.FileUtils.crossfade(buttons, pathLayout);
timer.cancel();
timer.start();
showButtons(mainFragment);
}
} else if (fragmentAtFrame instanceof com.amaze.filemanager.ui.fragments.CompressedExplorerFragment) {
com.amaze.filemanager.filesystem.files.FileUtils.crossfade(buttons, pathLayout);
timer.cancel();
timer.start();
showButtons(((com.amaze.filemanager.utils.BottomBarButtonPath) (fragmentAtFrame)));
}
return false;
}


@java.lang.Override
public void onLongPress(android.view.MotionEvent e) {
final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;
mainFragment = mainActivity.getCurrentMainFragment();
java.util.Objects.requireNonNull(mainFragment);
if (mainActivity.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CHANGEPATHS) && ((mainFragment.getMainFragmentViewModel() != null) || (buttons.getVisibility() == android.view.View.VISIBLE))) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showChangePathsDialog(mainActivity, mainActivity.getPrefs());
}
}

});
}


public void setClickListener() {
// TODO: 15/8/2017 this is a horrible hack, if you see this, correct it
frame.setOnTouchListener(this);
scroll.setOnTouchListener(this);
buttons.setOnTouchListener(this);
pathLayout.setOnTouchListener(this);
pathScroll.setOnTouchListener(this);
fullPathText.setOnTouchListener(this);
pathText.setOnTouchListener(this);
scroll.setOnTouchListener(this);
pathScroll.setOnTouchListener(this);
}


public void resetClickListener() {
frame.setOnTouchListener(null);
}


public void setPathText(java.lang.String text) {
pathText.setText(text);
}


public void setFullPathText(java.lang.String text) {
fullPathText.setText(text);
}


public java.lang.String getFullPathText() {
return fullPathText.getText().toString();
}


public boolean areButtonsShowing() {
return buttons.getVisibility() == android.view.View.VISIBLE;
}


public void showButtons(final com.amaze.filemanager.utils.BottomBarButtonPath buttonPathInterface) {
final java.lang.String path;
path = buttonPathInterface.getPath();
if (buttons.getVisibility() == android.view.View.VISIBLE) {
lastUsedArrowButton = 0;
lastUsedFolderButton = 0;
buttons.removeAllViews();
buttons.setMinimumHeight(pathLayout.getHeight());
buttonRoot.setImageDrawable(mainActivity.getResources().getDrawable(buttonPathInterface.getRootDrawable()));
java.lang.String[] names;
names = com.amaze.filemanager.filesystem.files.FileUtils.getFolderNamesInPath(path);
final java.lang.String[] paths;
paths = com.amaze.filemanager.filesystem.files.FileUtils.getPathsInPath(path);
android.view.View view;
view = new android.view.View(mainActivity);
android.widget.LinearLayout.LayoutParams params1;
params1 = new android.widget.LinearLayout.LayoutParams(appbar.getToolbar().getContentInsetLeft(), android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
view.setLayoutParams(params1);
buttons.addView(view);
for (int i = 0; i < names.length; i++) {
final int k;
k = i;
if (i == 0) {
switch(MUID_STATIC) {
// BottomBar_16_BuggyGUIListenerOperatorMutator
case 16123: {
buttonRoot.setOnClickListener(null);
break;
}
default: {
buttonRoot.setOnClickListener((android.view.View p1) -> {
if (paths.length != 0) {
buttonPathInterface.changePath(paths[k]);
timer.cancel();
timer.start();
}
});
break;
}
}
buttons.addView(buttonRoot);
} else if (com.amaze.filemanager.filesystem.files.FileUtils.isStorage(paths[i])) {
switch(MUID_STATIC) {
// BottomBar_17_BuggyGUIListenerOperatorMutator
case 17123: {
buttonStorage.setOnClickListener(null);
break;
}
default: {
buttonStorage.setOnClickListener((android.view.View p1) -> {
buttonPathInterface.changePath(paths[k]);
timer.cancel();
timer.start();
});
break;
}
}
buttons.addView(buttonStorage);
} else {
androidx.appcompat.widget.AppCompatButton button;
button = createFolderButton(names[i]);
switch(MUID_STATIC) {
// BottomBar_18_BuggyGUIListenerOperatorMutator
case 18123: {
button.setOnClickListener(null);
break;
}
default: {
button.setOnClickListener((android.view.View p1) -> {
buttonPathInterface.changePath(paths[k]);
timer.cancel();
timer.start();
});
break;
}
}
buttons.addView(button);
}
switch(MUID_STATIC) {
// BottomBar_19_BinaryMutator
case 19123: {
if ((names.length + i) != 1) {
buttons.addView(createArrow());
}
break;
}
default: {
if ((names.length - i) != 1) {
buttons.addView(createArrow());
}
break;
}
}
}
scroll.post(() -> {
sendScroll(scroll);
sendScroll(pathScroll);
});
if (buttons.getVisibility() == android.view.View.VISIBLE) {
timer.cancel();
timer.start();
}
}
}


public android.widget.FrameLayout getPathLayout() {
return this.frame;
}


private androidx.appcompat.widget.AppCompatImageView createArrow() {
androidx.appcompat.widget.AppCompatImageView buttonArrow;
if (lastUsedArrowButton >= arrowButtons.size()) {
buttonArrow = new androidx.appcompat.widget.AppCompatImageView(mainActivity);
buttonArrow.setImageDrawable(arrow);
buttonArrow.setLayoutParams(buttonParams);
arrowButtons.add(buttonArrow);
} else {
buttonArrow = arrowButtons.get(lastUsedArrowButton);
}
lastUsedArrowButton++;
return buttonArrow;
}


private androidx.appcompat.widget.AppCompatButton createFolderButton(java.lang.String text) {
androidx.appcompat.widget.AppCompatButton button;
if (lastUsedFolderButton >= folderButtons.size()) {
button = new androidx.appcompat.widget.AppCompatButton(mainActivity);
button.setTextColor(com.amaze.filemanager.utils.Utils.getColor(mainActivity, android.R.color.white));
button.setTextSize(13);
button.setLayoutParams(buttonParams);
button.setBackgroundResource(0);
folderButtons.add(button);
} else {
button = folderButtons.get(lastUsedFolderButton);
}
button.setText(text);
lastUsedFolderButton++;
return button;
}


public void setBackgroundColor(@androidx.annotation.ColorInt
int color) {
frame.setBackgroundColor(color);
}


public void setVisibility(int visibility) {
frame.setVisibility(visibility);
}


public void updatePath(@androidx.annotation.NonNull
final java.lang.String news, com.amaze.filemanager.fileoperations.filesystem.OpenMode openmode, int folderCount, int fileCount, com.amaze.filemanager.utils.BottomBarButtonPath buttonPathInterface) {
if (news.length() == 0)
return;

com.amaze.filemanager.utils.MainActivityHelper mainActivityHelper;
mainActivityHelper = mainActivity.mainActivityHelper;
switch (openmode) {
case SFTP :
case SMB :
case FTP :
newPath = com.amaze.filemanager.filesystem.HybridFile.parseAndFormatUriForDisplay(news);
break;
case OTG :
newPath = mainActivityHelper.parseOTGPath(news);
break;
case CUSTOM :
case TRASH_BIN :
newPath = mainActivityHelper.getIntegralNames(news);
break;
case DROPBOX :
case BOX :
case ONEDRIVE :
case GDRIVE :
newPath = mainActivityHelper.parseCloudPath(openmode, news);
break;
default :
newPath = news;
}
pathText.setText(mainActivity.getString(com.amaze.filemanager.R.string.folderfilecount, folderCount, fileCount));
final java.lang.String oldPath;
oldPath = fullPathText.getText().toString();
if (oldPath.equals(newPath))
return;

if (!areButtonsShowing()) {
final android.view.animation.Animation slideIn;
slideIn = android.view.animation.AnimationUtils.loadAnimation(mainActivity, com.amaze.filemanager.R.anim.slide_in);
android.view.animation.Animation slideOut;
slideOut = android.view.animation.AnimationUtils.loadAnimation(mainActivity, com.amaze.filemanager.R.anim.slide_out);
if (((newPath.length() > oldPath.length()) && newPath.contains(oldPath)) && (oldPath.length() != 0)) {
// navigate forward
fullPathAnim.setAnimation(slideIn);
fullPathAnim.animate().setListener(new android.animation.AnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
super.onAnimationEnd(animation);
new android.os.Handler().postDelayed(() -> {
fullPathAnim.setVisibility(android.view.View.GONE);
fullPathText.setText(newPath);
}, com.amaze.filemanager.ui.views.appbar.BottomBar.PATH_ANIM_END_DELAY);
}


@java.lang.Override
public void onAnimationStart(android.animation.Animator animation) {
super.onAnimationStart(animation);
fullPathAnim.setVisibility(android.view.View.VISIBLE);
fullPathAnim.setText(com.amaze.filemanager.utils.Utils.differenceStrings(oldPath, newPath));
// fullPathText.setText(oldPath);
scroll.post(() -> pathScroll.fullScroll(android.view.View.FOCUS_RIGHT));
}


@java.lang.Override
public void onAnimationCancel(android.animation.Animator animation) {
super.onAnimationCancel(animation);
// onAnimationEnd(animation);
}

}).setStartDelay(com.amaze.filemanager.ui.views.appbar.BottomBar.PATH_ANIM_START_DELAY).start();
} else if ((newPath.length() < oldPath.length()) && oldPath.contains(newPath)) {
// navigate backwards
fullPathAnim.setAnimation(slideOut);
fullPathAnim.animate().setListener(new android.animation.AnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
super.onAnimationEnd(animation);
fullPathAnim.setVisibility(android.view.View.GONE);
fullPathText.setText(newPath);
scroll.post(() -> pathScroll.fullScroll(android.view.View.FOCUS_RIGHT));
}


@java.lang.Override
public void onAnimationStart(android.animation.Animator animation) {
super.onAnimationStart(animation);
fullPathAnim.setVisibility(android.view.View.VISIBLE);
fullPathAnim.setText(com.amaze.filemanager.utils.Utils.differenceStrings(newPath, oldPath));
fullPathText.setText(newPath);
scroll.post(() -> pathScroll.fullScroll(android.view.View.FOCUS_LEFT));
}

}).setStartDelay(com.amaze.filemanager.ui.views.appbar.BottomBar.PATH_ANIM_START_DELAY).start();
} else if (oldPath.isEmpty()) {
// case when app starts
fullPathAnim.setAnimation(slideIn);
fullPathAnim.setText(newPath);
fullPathAnim.animate().setListener(new android.animation.AnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationStart(android.animation.Animator animation) {
super.onAnimationStart(animation);
fullPathAnim.setVisibility(android.view.View.VISIBLE);
fullPathText.setText("");
scroll.post(() -> pathScroll.fullScroll(android.view.View.FOCUS_RIGHT));
}


@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
super.onAnimationEnd(animation);
new android.os.Handler().postDelayed(() -> {
fullPathAnim.setVisibility(android.view.View.GONE);
fullPathText.setText(newPath);
}, com.amaze.filemanager.ui.views.appbar.BottomBar.PATH_ANIM_END_DELAY);
}


@java.lang.Override
public void onAnimationCancel(android.animation.Animator animation) {
super.onAnimationCancel(animation);
// onAnimationEnd(animation);
}

}).setStartDelay(com.amaze.filemanager.ui.views.appbar.BottomBar.PATH_ANIM_START_DELAY).start();
} else {
// completely different path
// first slide out of old path followed by slide in of new path
fullPathAnim.setAnimation(slideOut);
fullPathAnim.animate().setListener(new android.animation.AnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationStart(android.animation.Animator animator) {
super.onAnimationStart(animator);
fullPathAnim.setVisibility(android.view.View.VISIBLE);
fullPathAnim.setText(oldPath);
fullPathText.setText("");
scroll.post(() -> pathScroll.fullScroll(android.view.View.FOCUS_LEFT));
}


@java.lang.Override
public void onAnimationEnd(android.animation.Animator animator) {
super.onAnimationEnd(animator);
// fullPathAnim.setVisibility(View.GONE);
fullPathAnim.setText(newPath);
fullPathText.setText("");
fullPathAnim.setAnimation(slideIn);
fullPathAnim.animate().setListener(new android.animation.AnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
super.onAnimationEnd(animation);
new android.os.Handler().postDelayed(() -> {
fullPathAnim.setVisibility(android.view.View.GONE);
fullPathText.setText(newPath);
}, com.amaze.filemanager.ui.views.appbar.BottomBar.PATH_ANIM_END_DELAY);
}


@java.lang.Override
public void onAnimationStart(android.animation.Animator animation) {
super.onAnimationStart(animation);
// we should not be having anything here in path bar
fullPathAnim.setVisibility(android.view.View.VISIBLE);
fullPathText.setText("");
scroll.post(() -> pathScroll.fullScroll(android.view.View.FOCUS_RIGHT));
}

}).start();
}


@java.lang.Override
public void onAnimationCancel(android.animation.Animator animation) {
super.onAnimationCancel(animation);
// onAnimationEnd(animation);
}

}).setStartDelay(com.amaze.filemanager.ui.views.appbar.BottomBar.PATH_ANIM_START_DELAY).start();
}
} else {
showButtons(buttonPathInterface);
fullPathText.setText(newPath);
}
}


private void sendScroll(final android.widget.HorizontalScrollView scrollView) {
new android.os.Handler().postDelayed(() -> scrollView.fullScroll(android.view.View.FOCUS_RIGHT), 100);
}


@java.lang.Override
public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
return gestureDetector.onTouchEvent(event);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
