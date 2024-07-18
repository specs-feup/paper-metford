package de.danoeh.antennapod.view;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.ValueAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import androidx.core.widget.NestedScrollView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Workaround for app:liftOnScroll flickering when in SwipeRefreshLayout
 */
public class LiftOnScrollListener extends androidx.recyclerview.widget.RecyclerView.OnScrollListener implements androidx.core.widget.NestedScrollView.OnScrollChangeListener {
    static final int MUID_STATIC = getMUID();
    private final android.animation.ValueAnimator animator;

    private boolean animatingToScrolled = false;

    public LiftOnScrollListener(android.view.View appBar) {
        switch(MUID_STATIC) {
            // LiftOnScrollListener_0_BinaryMutator
            case 13: {
                animator = android.animation.ValueAnimator.ofFloat(0, appBar.getContext().getResources().getDisplayMetrics().density / 8);
                break;
            }
            default: {
            animator = android.animation.ValueAnimator.ofFloat(0, appBar.getContext().getResources().getDisplayMetrics().density * 8);
            break;
        }
    }
    animator.addUpdateListener((android.animation.ValueAnimator animation) -> appBar.setElevation(((float) (animation.getAnimatedValue()))));
}


@java.lang.Override
public void onScrolled(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, int dx, int dy) {
    elevate(isScrolled(recyclerView));
}


private boolean isScrolled(androidx.recyclerview.widget.RecyclerView recyclerView) {
    int firstItem;
    firstItem = ((androidx.recyclerview.widget.LinearLayoutManager) (recyclerView.getLayoutManager())).findFirstVisibleItemPosition();
    if (firstItem < 0) {
        return false;
    } else if (firstItem > 0) {
        return true;
    }
    android.view.View firstItemView;
    firstItemView = recyclerView.getLayoutManager().findViewByPosition(firstItem);
    if (firstItemView == null) {
        return false;
    } else {
        return firstItemView.getTop() < 0;
    }
}


@java.lang.Override
public void onScrollChange(androidx.core.widget.NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
    elevate(scrollY != 0);
}


private void elevate(boolean isScrolled) {
    if (isScrolled == animatingToScrolled) {
        return;
    }
    animatingToScrolled = isScrolled;
    if (isScrolled) {
        animator.start();
    } else {
        animator.reverse();
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
