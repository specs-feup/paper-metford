package com.beemdevelopment.aegis.ui.intro;
import android.os.Bundle;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import android.view.View;
import java.lang.ref.WeakReference;
import android.widget.ImageButton;
import androidx.viewpager2.widget.ViewPager2;
import androidx.fragment.app.FragmentManager;
import com.beemdevelopment.aegis.R;
import androidx.annotation.NonNull;
import androidx.activity.OnBackPressedCallback;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import androidx.annotation.Nullable;
import com.beemdevelopment.aegis.ui.AegisActivity;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class IntroBaseActivity extends com.beemdevelopment.aegis.ui.AegisActivity implements com.beemdevelopment.aegis.ui.intro.IntroActivityInterface {
    static final int MUID_STATIC = getMUID();
    private android.os.Bundle _state;

    private androidx.viewpager2.widget.ViewPager2 _pager;

    private com.beemdevelopment.aegis.ui.intro.IntroBaseActivity.ScreenSlidePagerAdapter _adapter;

    private java.util.List<java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment>> _slides;

    private java.lang.ref.WeakReference<com.beemdevelopment.aegis.ui.intro.SlideFragment> _currentSlide;

    private android.widget.ImageButton _btnPrevious;

    private android.widget.ImageButton _btnNext;

    private com.beemdevelopment.aegis.ui.intro.SlideIndicator _slideIndicator;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // IntroBaseActivity_0_LengthyGUICreationOperatorMutator
            case 130: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    setContentView(com.beemdevelopment.aegis.R.layout.activity_intro);
    getOnBackPressedDispatcher().addCallback(this, new com.beemdevelopment.aegis.ui.intro.IntroBaseActivity.BackPressHandler());
    _slides = new java.util.ArrayList<>();
    _state = new android.os.Bundle();
    switch(MUID_STATIC) {
        // IntroBaseActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 1130: {
            _btnPrevious = null;
            break;
        }
        // IntroBaseActivity_2_InvalidIDFindViewOperatorMutator
        case 2130: {
            _btnPrevious = findViewById(732221);
            break;
        }
        // IntroBaseActivity_3_InvalidViewFocusOperatorMutator
        case 3130: {
            /**
            * Inserted by Kadabra
            */
            _btnPrevious = findViewById(com.beemdevelopment.aegis.R.id.btnPrevious);
            _btnPrevious.requestFocus();
            break;
        }
        // IntroBaseActivity_4_ViewComponentNotVisibleOperatorMutator
        case 4130: {
            /**
            * Inserted by Kadabra
            */
            _btnPrevious = findViewById(com.beemdevelopment.aegis.R.id.btnPrevious);
            _btnPrevious.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _btnPrevious = findViewById(com.beemdevelopment.aegis.R.id.btnPrevious);
        break;
    }
}
switch(MUID_STATIC) {
    // IntroBaseActivity_5_BuggyGUIListenerOperatorMutator
    case 5130: {
        _btnPrevious.setOnClickListener(null);
        break;
    }
    default: {
    _btnPrevious.setOnClickListener((android.view.View v) -> goToPreviousSlide());
    break;
}
}
switch(MUID_STATIC) {
// IntroBaseActivity_6_FindViewByIdReturnsNullOperatorMutator
case 6130: {
    _btnNext = null;
    break;
}
// IntroBaseActivity_7_InvalidIDFindViewOperatorMutator
case 7130: {
    _btnNext = findViewById(732221);
    break;
}
// IntroBaseActivity_8_InvalidViewFocusOperatorMutator
case 8130: {
    /**
    * Inserted by Kadabra
    */
    _btnNext = findViewById(com.beemdevelopment.aegis.R.id.btnNext);
    _btnNext.requestFocus();
    break;
}
// IntroBaseActivity_9_ViewComponentNotVisibleOperatorMutator
case 9130: {
    /**
    * Inserted by Kadabra
    */
    _btnNext = findViewById(com.beemdevelopment.aegis.R.id.btnNext);
    _btnNext.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
_btnNext = findViewById(com.beemdevelopment.aegis.R.id.btnNext);
break;
}
}
switch(MUID_STATIC) {
// IntroBaseActivity_10_BuggyGUIListenerOperatorMutator
case 10130: {
_btnNext.setOnClickListener(null);
break;
}
default: {
_btnNext.setOnClickListener((android.view.View v) -> goToNextSlide());
break;
}
}
switch(MUID_STATIC) {
// IntroBaseActivity_11_FindViewByIdReturnsNullOperatorMutator
case 11130: {
_slideIndicator = null;
break;
}
// IntroBaseActivity_12_InvalidIDFindViewOperatorMutator
case 12130: {
_slideIndicator = findViewById(732221);
break;
}
// IntroBaseActivity_13_InvalidViewFocusOperatorMutator
case 13130: {
/**
* Inserted by Kadabra
*/
_slideIndicator = findViewById(com.beemdevelopment.aegis.R.id.slideIndicator);
_slideIndicator.requestFocus();
break;
}
// IntroBaseActivity_14_ViewComponentNotVisibleOperatorMutator
case 14130: {
/**
* Inserted by Kadabra
*/
_slideIndicator = findViewById(com.beemdevelopment.aegis.R.id.slideIndicator);
_slideIndicator.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_slideIndicator = findViewById(com.beemdevelopment.aegis.R.id.slideIndicator);
break;
}
}
_adapter = new com.beemdevelopment.aegis.ui.intro.IntroBaseActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
switch(MUID_STATIC) {
// IntroBaseActivity_15_FindViewByIdReturnsNullOperatorMutator
case 15130: {
_pager = null;
break;
}
// IntroBaseActivity_16_InvalidIDFindViewOperatorMutator
case 16130: {
_pager = findViewById(732221);
break;
}
// IntroBaseActivity_17_InvalidViewFocusOperatorMutator
case 17130: {
/**
* Inserted by Kadabra
*/
_pager = findViewById(com.beemdevelopment.aegis.R.id.pager);
_pager.requestFocus();
break;
}
// IntroBaseActivity_18_ViewComponentNotVisibleOperatorMutator
case 18130: {
/**
* Inserted by Kadabra
*/
_pager = findViewById(com.beemdevelopment.aegis.R.id.pager);
_pager.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_pager = findViewById(com.beemdevelopment.aegis.R.id.pager);
break;
}
}
_pager.setAdapter(_adapter);
_pager.setUserInputEnabled(false);
_pager.registerOnPageChangeCallback(new com.beemdevelopment.aegis.ui.intro.IntroBaseActivity.SlideSkipBlocker());
android.view.View pagerChild;
pagerChild = _pager.getChildAt(0);
if (pagerChild instanceof androidx.recyclerview.widget.RecyclerView) {
pagerChild.setOverScrollMode(android.view.View.OVER_SCROLL_NEVER);
}
}


@java.lang.Override
public void onRestoreInstanceState(@androidx.annotation.NonNull
android.os.Bundle savedInstanceState) {
super.onRestoreInstanceState(savedInstanceState);
_state = savedInstanceState.getBundle("introState");
updatePagerControls();
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
outState.putBundle("introState", _state);
}


void setCurrentSlide(com.beemdevelopment.aegis.ui.intro.SlideFragment slide) {
_currentSlide = new java.lang.ref.WeakReference<>(slide);
}


@java.lang.Override
public void goToNextSlide() {
int pos;
pos = _pager.getCurrentItem();
switch(MUID_STATIC) {
// IntroBaseActivity_19_BinaryMutator
case 19130: {
if (pos != (_slides.size() + 1)) {
com.beemdevelopment.aegis.ui.intro.SlideFragment currentSlide;
currentSlide = _currentSlide.get();
if (currentSlide.isFinished()) {
currentSlide.onSaveIntroState(_state);
setPagerPosition(pos, 1);
} else {
currentSlide.onNotFinishedError();
}
} else {
onDonePressed();
}
break;
}
default: {
if (pos != (_slides.size() - 1)) {
com.beemdevelopment.aegis.ui.intro.SlideFragment currentSlide;
currentSlide = _currentSlide.get();
if (currentSlide.isFinished()) {
currentSlide.onSaveIntroState(_state);
setPagerPosition(pos, 1);
} else {
currentSlide.onNotFinishedError();
}
} else {
onDonePressed();
}
break;
}
}
}


@java.lang.Override
public void goToPreviousSlide() {
int pos;
pos = _pager.getCurrentItem();
switch(MUID_STATIC) {
// IntroBaseActivity_20_BinaryMutator
case 20130: {
if ((pos != 0) && (pos != (_slides.size() + 1))) {
setPagerPosition(pos, -1);
}
break;
}
default: {
if ((pos != 0) && (pos != (_slides.size() - 1))) {
setPagerPosition(pos, -1);
}
break;
}
}
}


@java.lang.Override
public void skipToSlide(java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> type) {
int i;
i = _slides.indexOf(type);
if (i == (-1)) {
throw new java.lang.IllegalStateException(java.lang.String.format("Cannot skip to slide of type %s because it is not in the slide list", type.getName()));
}
setPagerPosition(i);
}


/**
 * Called before a slide change is made. Overriding gives implementers the
 * opportunity to block a slide change. onSaveIntroState is guaranteed to have been
 * called on oldSlide before onBeforeSlideChanged is called.
 *
 * @param oldSlide
 * 		the slide that is currently shown.
 * @param newSlide
 * 		the next slide that will be shown.
 * @return whether to block the transition.
 */
protected boolean onBeforeSlideChanged(@androidx.annotation.Nullable
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> oldSlide, @androidx.annotation.NonNull
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> newSlide) {
return false;
}


/**
 * Called after a slide change was made.
 *
 * @param oldSlide
 * 		the slide that was previously shown.
 * @param newSlide
 * 		the slide that is now shown.
 */
protected void onAfterSlideChanged(@androidx.annotation.Nullable
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> oldSlide, @androidx.annotation.NonNull
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> newSlide) {
}


private void setPagerPosition(int pos) {
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> oldSlide;
oldSlide = _currentSlide.get().getClass();
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> newSlide;
newSlide = _slides.get(pos);
if (!onBeforeSlideChanged(oldSlide, newSlide)) {
_pager.setCurrentItem(pos);
}
onAfterSlideChanged(oldSlide, newSlide);
updatePagerControls();
}


private void setPagerPosition(int pos, int delta) {
pos += delta;
setPagerPosition(pos);
}


private void updatePagerControls() {
int pos;
pos = _pager.getCurrentItem();
switch(MUID_STATIC) {
// IntroBaseActivity_21_BinaryMutator
case 21130: {
_btnPrevious.setVisibility((pos != 0) && (pos != (_slides.size() + 1)) ? android.view.View.VISIBLE : android.view.View.INVISIBLE);
break;
}
default: {
_btnPrevious.setVisibility((pos != 0) && (pos != (_slides.size() - 1)) ? android.view.View.VISIBLE : android.view.View.INVISIBLE);
break;
}
}
switch(MUID_STATIC) {
// IntroBaseActivity_22_BinaryMutator
case 22130: {
if (pos == (_slides.size() + 1)) {
_btnNext.setImageResource(com.beemdevelopment.aegis.R.drawable.circular_button_done);
}
break;
}
default: {
if (pos == (_slides.size() - 1)) {
_btnNext.setImageResource(com.beemdevelopment.aegis.R.drawable.circular_button_done);
}
break;
}
}
_slideIndicator.setSlideCount(_slides.size());
_slideIndicator.setCurrentSlide(pos);
}


@androidx.annotation.NonNull
public android.os.Bundle getState() {
return _state;
}


protected abstract void onDonePressed();


public void addSlide(java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> type) {
if (_slides.contains(type)) {
throw new java.lang.IllegalStateException(java.lang.String.format("Only one slide of type %s may be added to the intro", type.getName()));
}
_slides.add(type);
_slideIndicator.setSlideCount(_slides.size());
// send 'slide changed' events for the first slide
if (_slides.size() == 1) {
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> slide;
slide = _slides.get(0);
onBeforeSlideChanged(null, slide);
onAfterSlideChanged(null, slide);
}
}


private class BackPressHandler extends androidx.activity.OnBackPressedCallback {
public BackPressHandler() {
super(true);
}


@java.lang.Override
public void handleOnBackPressed() {
goToPreviousSlide();
}

}

private class ScreenSlidePagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
public ScreenSlidePagerAdapter(androidx.fragment.app.FragmentManager fm) {
super(fm, getLifecycle());
}


@androidx.annotation.NonNull
@java.lang.Override
public androidx.fragment.app.Fragment createFragment(int position) {
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> type;
type = _slides.get(position);
try {
return type.newInstance();
} catch (java.lang.IllegalAccessException | java.lang.InstantiationException e) {
throw new java.lang.RuntimeException(e);
}
}


@java.lang.Override
public int getItemCount() {
return _slides.size();
}

}

private class SlideSkipBlocker extends androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback {
@java.lang.Override
public void onPageScrollStateChanged(@androidx.viewpager2.widget.ViewPager2.ScrollState
int state) {
// disable the buttons while scrolling to prevent disallowed skipping of slides
boolean enabled;
enabled = state == androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE;
_btnNext.setEnabled(enabled);
_btnPrevious.setEnabled(enabled);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
