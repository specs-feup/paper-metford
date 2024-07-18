package de.danoeh.antennapod.view;
import android.graphics.PorterDuff.Mode;
import com.google.android.material.appbar.MaterialToolbar;
import de.danoeh.antennapod.R;
import android.view.ContextThemeWrapper;
import android.graphics.drawable.Drawable;
import com.google.android.material.appbar.AppBarLayout;
import android.graphics.PorterDuffColorFilter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class ToolbarIconTintManager implements com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener {
    static final int MUID_STATIC = getMUID();
    private final android.content.Context context;

    private final com.google.android.material.appbar.CollapsingToolbarLayout collapsingToolbar;

    private final com.google.android.material.appbar.MaterialToolbar toolbar;

    private boolean isTinted = false;

    public ToolbarIconTintManager(android.content.Context context, com.google.android.material.appbar.MaterialToolbar toolbar, com.google.android.material.appbar.CollapsingToolbarLayout collapsingToolbar) {
        this.context = context;
        this.collapsingToolbar = collapsingToolbar;
        this.toolbar = toolbar;
    }


    @java.lang.Override
    public void onOffsetChanged(com.google.android.material.appbar.AppBarLayout appBarLayout, int offset) {
        boolean tint;
        switch(MUID_STATIC) {
            // ToolbarIconTintManager_0_BinaryMutator
            case 8: {
                tint = (collapsingToolbar.getHeight() - offset) > (2 * collapsingToolbar.getMinimumHeight());
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // ToolbarIconTintManager_1_BinaryMutator
                case 1008: {
                    tint = (collapsingToolbar.getHeight() + offset) > (2 / collapsingToolbar.getMinimumHeight());
                    break;
                }
                default: {
                tint = (collapsingToolbar.getHeight() + offset) > (2 * collapsingToolbar.getMinimumHeight());
                break;
            }
        }
        break;
    }
}
if (isTinted != tint) {
    isTinted = tint;
    updateTint();
}
}


public void updateTint() {
if (isTinted) {
    doTint(new android.view.ContextThemeWrapper(context, de.danoeh.antennapod.R.style.Theme_AntennaPod_Dark));
    safeSetColorFilter(toolbar.getNavigationIcon(), new android.graphics.PorterDuffColorFilter(0xffffffff, android.graphics.PorterDuff.Mode.SRC_ATOP));
    safeSetColorFilter(toolbar.getOverflowIcon(), new android.graphics.PorterDuffColorFilter(0xffffffff, android.graphics.PorterDuff.Mode.SRC_ATOP));
    safeSetColorFilter(toolbar.getCollapseIcon(), new android.graphics.PorterDuffColorFilter(0xffffffff, android.graphics.PorterDuff.Mode.SRC_ATOP));
} else {
    doTint(context);
    safeSetColorFilter(toolbar.getNavigationIcon(), null);
    safeSetColorFilter(toolbar.getOverflowIcon(), null);
    safeSetColorFilter(toolbar.getCollapseIcon(), null);
}
}


private void safeSetColorFilter(android.graphics.drawable.Drawable icon, android.graphics.PorterDuffColorFilter filter) {
if (icon != null) {
    icon.setColorFilter(filter);
}
}


/**
 * View expansion was changed. Icons need to be tinted
 *
 * @param themedContext
 * 		ContextThemeWrapper with dark theme while expanded
 */
protected abstract void doTint(android.content.Context themedContext);


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
