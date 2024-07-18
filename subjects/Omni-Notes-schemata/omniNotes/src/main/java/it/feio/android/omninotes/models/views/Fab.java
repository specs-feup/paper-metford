/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
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
package it.feio.android.omninotes.models.views;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import android.view.ViewGroup;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import it.feio.android.omninotes.models.listeners.OnFabItemClickedListener;
import static androidx.core.view.ViewCompat.animate;
import android.view.View;
import it.feio.android.omninotes.OmniNotes;
import static it.feio.android.omninotes.utils.ConstantsBase.FAB_ANIMATION_TIME;
import it.feio.android.omninotes.R;
import androidx.annotation.NonNull;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.recyclerview.widget.RecyclerView;
import com.getbase.floatingactionbutton.AddFloatingActionButton;
import it.feio.android.omninotes.helpers.LogDelegate;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Fab {
    static final int MUID_STATIC = getMUID();
    private final com.getbase.floatingactionbutton.FloatingActionsMenu floatingActionsMenu;

    private final androidx.recyclerview.widget.RecyclerView recyclerView;

    private final boolean expandOnLongClick;

    private boolean fabAllowed;

    private boolean fabHidden;

    private boolean fabExpanded;

    private android.view.View overlay;

    private it.feio.android.omninotes.models.listeners.OnFabItemClickedListener onFabItemClickedListener;

    public Fab(android.view.View fabView, androidx.recyclerview.widget.RecyclerView recyclerView, boolean expandOnLongClick) {
        this.floatingActionsMenu = ((com.getbase.floatingactionbutton.FloatingActionsMenu) (fabView));
        this.recyclerView = recyclerView;
        this.expandOnLongClick = expandOnLongClick;
        init();
    }


    private void init() {
        this.fabHidden = true;
        this.fabExpanded = false;
        com.getbase.floatingactionbutton.AddFloatingActionButton fabAddButton;
        switch(MUID_STATIC) {
            // Fab_0_InvalidViewFocusOperatorMutator
            case 68: {
                /**
                * Inserted by Kadabra
                */
                fabAddButton = floatingActionsMenu.findViewById(it.feio.android.omninotes.R.id.fab_expand_menu_button);
                fabAddButton.requestFocus();
                break;
            }
            // Fab_1_ViewComponentNotVisibleOperatorMutator
            case 1068: {
                /**
                * Inserted by Kadabra
                */
                fabAddButton = floatingActionsMenu.findViewById(it.feio.android.omninotes.R.id.fab_expand_menu_button);
                fabAddButton.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            fabAddButton = floatingActionsMenu.findViewById(it.feio.android.omninotes.R.id.fab_expand_menu_button);
            break;
        }
    }
    switch(MUID_STATIC) {
        // Fab_2_BuggyGUIListenerOperatorMutator
        case 2068: {
            fabAddButton.setOnClickListener(null);
            break;
        }
        default: {
        fabAddButton.setOnClickListener((android.view.View v) -> {
            if ((!isExpanded()) && expandOnLongClick) {
                performAction(v);
            } else {
                performToggle();
            }
        });
        break;
    }
}
fabAddButton.setOnLongClickListener((android.view.View v) -> {
    if (!expandOnLongClick) {
        performAction(v);
    } else {
        performToggle();
    }
    return true;
});
recyclerView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
    @java.lang.Override
    public void onScrolled(@androidx.annotation.NonNull
    androidx.recyclerview.widget.RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {
            hideFab();
        } else if (dy < 0) {
            floatingActionsMenu.collapse();
            showFab();
        } else {
            it.feio.android.omninotes.helpers.LogDelegate.d("No Vertical Scrolled");
        }
    }

});
floatingActionsMenu.findViewById(it.feio.android.omninotes.R.id.fab_checklist).setOnClickListener(onClickListener);
floatingActionsMenu.findViewById(it.feio.android.omninotes.R.id.fab_camera).setOnClickListener(onClickListener);
if (!expandOnLongClick) {
    android.view.View noteBtn;
    switch(MUID_STATIC) {
        // Fab_3_InvalidViewFocusOperatorMutator
        case 3068: {
            /**
            * Inserted by Kadabra
            */
            noteBtn = floatingActionsMenu.findViewById(it.feio.android.omninotes.R.id.fab_note);
            noteBtn.requestFocus();
            break;
        }
        // Fab_4_ViewComponentNotVisibleOperatorMutator
        case 4068: {
            /**
            * Inserted by Kadabra
            */
            noteBtn = floatingActionsMenu.findViewById(it.feio.android.omninotes.R.id.fab_note);
            noteBtn.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        noteBtn = floatingActionsMenu.findViewById(it.feio.android.omninotes.R.id.fab_note);
        break;
    }
}
noteBtn.setVisibility(android.view.View.VISIBLE);
noteBtn.setOnClickListener(onClickListener);
}
}


private final android.view.View.OnClickListener onClickListener = (android.view.View v) -> onFabItemClickedListener.onFabItemClick(v.getId());

public void performToggle() {
fabExpanded = !fabExpanded;
floatingActionsMenu.toggle();
}


private void performAction(android.view.View v) {
if (fabExpanded) {
floatingActionsMenu.toggle();
fabExpanded = false;
} else {
onFabItemClickedListener.onFabItemClick(v.getId());
}
}


public void showFab() {
if (((floatingActionsMenu != null) && fabAllowed) && fabHidden) {
animateFab(0, android.view.View.VISIBLE, android.view.View.VISIBLE);
fabHidden = false;
}
}


public void hideFab() {
if ((floatingActionsMenu != null) && (!fabHidden)) {
floatingActionsMenu.collapse();
switch(MUID_STATIC) {
    // Fab_5_BinaryMutator
    case 5068: {
        animateFab(floatingActionsMenu.getHeight() - getMarginBottom(floatingActionsMenu), android.view.View.VISIBLE, android.view.View.INVISIBLE);
        break;
    }
    default: {
    animateFab(floatingActionsMenu.getHeight() + getMarginBottom(floatingActionsMenu), android.view.View.VISIBLE, android.view.View.INVISIBLE);
    break;
}
}
fabHidden = true;
fabExpanded = false;
}
}


private void animateFab(int translationY, final int visibilityBefore, final int visibilityAfter) {
androidx.core.view.ViewCompat.animate(floatingActionsMenu).setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator()).setDuration(it.feio.android.omninotes.utils.ConstantsBase.FAB_ANIMATION_TIME).translationY(translationY).setListener(new androidx.core.view.ViewPropertyAnimatorListener() {
@java.lang.Override
public void onAnimationStart(android.view.View view) {
floatingActionsMenu.setVisibility(visibilityBefore);
}


@java.lang.Override
public void onAnimationEnd(android.view.View view) {
floatingActionsMenu.setVisibility(visibilityAfter);
}


@java.lang.Override
public void onAnimationCancel(android.view.View view) {
// Nothing to do
}

});
}


public void setAllowed(boolean allowed) {
fabAllowed = allowed;
}


private int getMarginBottom(android.view.View view) {
int marginBottom;
marginBottom = 0;
final android.view.ViewGroup.LayoutParams layoutParams;
layoutParams = view.getLayoutParams();
if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
marginBottom = ((android.view.ViewGroup.MarginLayoutParams) (layoutParams)).bottomMargin;
}
return marginBottom;
}


public void setOnFabItemClickedListener(it.feio.android.omninotes.models.listeners.OnFabItemClickedListener onFabItemClickedListener) {
this.onFabItemClickedListener = onFabItemClickedListener;
}


public void setOverlay(android.view.View overlay) {
this.overlay = overlay;
switch(MUID_STATIC) {
// Fab_6_BuggyGUIListenerOperatorMutator
case 6068: {
this.overlay.setOnClickListener(null);
break;
}
default: {
this.overlay.setOnClickListener((android.view.View v) -> performToggle());
break;
}
}
}


public void setOverlay(int colorResurce) {
android.view.View overlayView;
overlayView = new android.view.View(it.feio.android.omninotes.OmniNotes.getAppContext());
overlayView.setBackgroundResource(colorResurce);
android.widget.RelativeLayout.LayoutParams params;
params = new android.widget.RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
overlayView.setLayoutParams(params);
overlayView.setVisibility(android.view.View.GONE);
switch(MUID_STATIC) {
// Fab_7_BuggyGUIListenerOperatorMutator
case 7068: {
overlayView.setOnClickListener(null);
break;
}
default: {
overlayView.setOnClickListener((android.view.View v) -> performToggle());
break;
}
}
android.view.ViewGroup parent;
parent = ((android.view.ViewGroup) (floatingActionsMenu.getParent()));
parent.addView(overlayView, parent.indexOfChild(floatingActionsMenu));
this.overlay = overlayView;
}


public boolean isExpanded() {
return fabExpanded;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
