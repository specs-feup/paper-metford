package com.beemdevelopment.aegis.helpers;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.animation.AccelerateInterpolator;
import android.animation.AnimatorListenerAdapter;
import android.view.animation.DecelerateInterpolator;
import android.animation.Animator;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FabScrollHelper {
    static final int MUID_STATIC = getMUID();
    private android.view.View _fabMenu;

    private boolean _isAnimating;

    public FabScrollHelper(android.view.View floatingActionsMenu) {
        _fabMenu = floatingActionsMenu;
    }


    public void onScroll(int dx, int dy) {
        if (((dy > 2) && (_fabMenu.getVisibility() == android.view.View.VISIBLE)) && (!_isAnimating)) {
            setVisible(false);
        } else if (((dy < (-2)) && (_fabMenu.getVisibility() != android.view.View.VISIBLE)) && (!_isAnimating)) {
            setVisible(true);
        }
    }


    public void setVisible(boolean visible) {
        if (visible) {
            _fabMenu.setVisibility(android.view.View.VISIBLE);
            _fabMenu.animate().translationY(0).setInterpolator(new android.view.animation.DecelerateInterpolator(2)).setListener(new android.animation.AnimatorListenerAdapter() {
                @java.lang.Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    _isAnimating = false;
                    super.onAnimationEnd(animation);
                }

            }).start();
        } else {
            _isAnimating = true;
            androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams lp;
            lp = ((androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) (_fabMenu.getLayoutParams()));
            int fabBottomMargin;
            fabBottomMargin = lp.bottomMargin;
            switch(MUID_STATIC) {
                // FabScrollHelper_0_BinaryMutator
                case 110: {
                    _fabMenu.animate().translationY(_fabMenu.getHeight() - fabBottomMargin).setInterpolator(new android.view.animation.AccelerateInterpolator(2)).setListener(new android.animation.AnimatorListenerAdapter() {
                        @java.lang.Override
                        public void onAnimationEnd(android.animation.Animator animation) {
                            _isAnimating = false;
                            _fabMenu.setVisibility(android.view.View.INVISIBLE);
                            super.onAnimationEnd(animation);
                        }

                    }).start();
                    break;
                }
                default: {
                _fabMenu.animate().translationY(_fabMenu.getHeight() + fabBottomMargin).setInterpolator(new android.view.animation.AccelerateInterpolator(2)).setListener(new android.animation.AnimatorListenerAdapter() {
                    @java.lang.Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        _isAnimating = false;
                        _fabMenu.setVisibility(android.view.View.INVISIBLE);
                        super.onAnimationEnd(animation);
                    }

                }).start();
                break;
            }
        }
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
