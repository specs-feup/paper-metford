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
package it.feio.android.omninotes;
import com.larswerkman.holocolorpicker.ColorPicker;
import it.feio.android.checklistview.utils.AlphaManager;
import android.widget.PopupWindow;
import java.io.FileNotFoundException;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.net.Uri;
import android.view.WindowManager;
import androidx.fragment.app.Fragment;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import it.feio.android.omninotes.models.listeners.OnDrawChangedListener;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.os.Bundle;
import android.view.ViewGroup;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import it.feio.android.omninotes.databinding.FragmentSketchBinding;
import android.view.MenuItem;
import android.view.View;
import android.view.LayoutInflater;
import java.io.FileOutputStream;
import android.provider.MediaStore;
import it.feio.android.omninotes.models.views.SketchView;
import android.view.View.OnClickListener;
import com.afollestad.materialdialogs.MaterialDialog;
import it.feio.android.omninotes.helpers.LogDelegate;
import java.io.File;
import it.feio.android.omninotes.models.ONStyle;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SketchFragment extends androidx.fragment.app.Fragment implements it.feio.android.omninotes.models.listeners.OnDrawChangedListener {
    static final int MUID_STATIC = getMUID();
    private int seekBarStrokeProgress;

    private int seekBarEraserProgress;

    private android.view.View popupLayout;

    private android.view.View popupEraserLayout;

    private android.widget.ImageView strokeImageView;

    private android.widget.ImageView eraserImageView;

    private int size;

    private com.larswerkman.holocolorpicker.ColorPicker mColorPicker;

    private it.feio.android.omninotes.databinding.FragmentSketchBinding binding;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SketchFragment_0_LengthyGUICreationOperatorMutator
            case 146: {
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
    setHasOptionsMenu(true);
    setRetainInstance(false);
}


@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    binding = it.feio.android.omninotes.databinding.FragmentSketchBinding.inflate(inflater, container, false);
    return binding.getRoot();
}


@java.lang.Override
public void onActivityCreated(android.os.Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    switch(MUID_STATIC) {
        // SketchFragment_1_BuggyGUIListenerOperatorMutator
        case 1146: {
            getMainActivity().getToolbar().setNavigationOnClickListener(null);
            break;
        }
        default: {
        getMainActivity().getToolbar().setNavigationOnClickListener((android.view.View v) -> getActivity().onBackPressed());
        break;
    }
}
binding.drawing.setOnDrawChangedListener(this);
android.net.Uri baseUri;
baseUri = getArguments().getParcelable("base");
if (baseUri != null) {
    android.graphics.Bitmap bmp;
    try {
        bmp = android.graphics.BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(baseUri));
        binding.drawing.setBackgroundBitmap(getActivity(), bmp);
    } catch (java.io.FileNotFoundException e) {
        it.feio.android.omninotes.helpers.LogDelegate.e("Error replacing sketch bitmap background", e);
    }
}
// Show the Up button in the action bar.
if (getMainActivity().getSupportActionBar() != null) {
    getMainActivity().getSupportActionBar().setDisplayShowTitleEnabled(true);
    getMainActivity().getSupportActionBar().setTitle(it.feio.android.omninotes.R.string.title_activity_sketch);
    getMainActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
}
switch(MUID_STATIC) {
    // SketchFragment_2_BuggyGUIListenerOperatorMutator
    case 2146: {
        binding.sketchStroke.setOnClickListener(null);
        break;
    }
    default: {
    binding.sketchStroke.setOnClickListener((android.view.View v) -> {
        if (binding.drawing.getMode() == it.feio.android.omninotes.models.views.SketchView.STROKE) {
            showPopup(v, it.feio.android.omninotes.models.views.SketchView.STROKE);
        } else {
            binding.drawing.setMode(it.feio.android.omninotes.models.views.SketchView.STROKE);
            it.feio.android.checklistview.utils.AlphaManager.setAlpha(binding.sketchEraser, 0.4F);
            it.feio.android.checklistview.utils.AlphaManager.setAlpha(binding.sketchStroke, 1.0F);
        }
    });
    break;
}
}
it.feio.android.checklistview.utils.AlphaManager.setAlpha(binding.sketchEraser, 0.4F);
switch(MUID_STATIC) {
// SketchFragment_3_BuggyGUIListenerOperatorMutator
case 3146: {
    binding.sketchEraser.setOnClickListener(null);
    break;
}
default: {
binding.sketchEraser.setOnClickListener((android.view.View v) -> {
    if (binding.drawing.getMode() == it.feio.android.omninotes.models.views.SketchView.ERASER) {
        showPopup(v, it.feio.android.omninotes.models.views.SketchView.ERASER);
    } else {
        binding.drawing.setMode(it.feio.android.omninotes.models.views.SketchView.ERASER);
        it.feio.android.checklistview.utils.AlphaManager.setAlpha(binding.sketchStroke, 0.4F);
        it.feio.android.checklistview.utils.AlphaManager.setAlpha(binding.sketchEraser, 1.0F);
    }
});
break;
}
}
switch(MUID_STATIC) {
// SketchFragment_4_BuggyGUIListenerOperatorMutator
case 4146: {
binding.sketchUndo.setOnClickListener(null);
break;
}
default: {
binding.sketchUndo.setOnClickListener((android.view.View v) -> binding.drawing.undo());
break;
}
}
switch(MUID_STATIC) {
// SketchFragment_5_BuggyGUIListenerOperatorMutator
case 5146: {
binding.sketchRedo.setOnClickListener(null);
break;
}
default: {
binding.sketchRedo.setOnClickListener((android.view.View v) -> binding.drawing.redo());
break;
}
}
switch(MUID_STATIC) {
// SketchFragment_6_BuggyGUIListenerOperatorMutator
case 6146: {
binding.sketchErase.setOnClickListener(null);
break;
}
default: {
binding.sketchErase.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// SketchFragment_7_LengthyGUIListenerOperatorMutator
case 7146: {
    /**
    * Inserted by Kadabra
    */
    askForErase();
    try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
    break;
}
default: {
askForErase();
break;
}
}
}


private void askForErase() {
switch(MUID_STATIC) {
// SketchFragment_8_LengthyGUIListenerOperatorMutator
case 8146: {
/**
* Inserted by Kadabra
*/
new com.afollestad.materialdialogs.MaterialDialog.Builder(getActivity()).content(it.feio.android.omninotes.R.string.erase_sketch).positiveText(it.feio.android.omninotes.R.string.confirm).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> binding.drawing.erase()).build().show();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
new com.afollestad.materialdialogs.MaterialDialog.Builder(getActivity()).content(it.feio.android.omninotes.R.string.erase_sketch).positiveText(it.feio.android.omninotes.R.string.confirm).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> binding.drawing.erase()).build().show();
break;
}
}
}

});
break;
}
}
// Inflate the popup_layout.XML
android.view.LayoutInflater inflater;
inflater = ((android.view.LayoutInflater) (getActivity().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
popupLayout = inflater.inflate(it.feio.android.omninotes.R.layout.popup_sketch_stroke, null);
// And the one for eraser
android.view.LayoutInflater inflaterEraser;
inflaterEraser = ((android.view.LayoutInflater) (getActivity().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
popupEraserLayout = inflaterEraser.inflate(it.feio.android.omninotes.R.layout.popup_sketch_eraser, null);
switch(MUID_STATIC) {
// SketchFragment_9_InvalidViewFocusOperatorMutator
case 9146: {
/**
* Inserted by Kadabra
*/
// Actual stroke shape size is retrieved
strokeImageView = popupLayout.findViewById(it.feio.android.omninotes.R.id.stroke_circle);
strokeImageView.requestFocus();
break;
}
// SketchFragment_10_ViewComponentNotVisibleOperatorMutator
case 10146: {
/**
* Inserted by Kadabra
*/
// Actual stroke shape size is retrieved
strokeImageView = popupLayout.findViewById(it.feio.android.omninotes.R.id.stroke_circle);
strokeImageView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
// Actual stroke shape size is retrieved
strokeImageView = popupLayout.findViewById(it.feio.android.omninotes.R.id.stroke_circle);
break;
}
}
final android.graphics.drawable.Drawable circleDrawable;
circleDrawable = getResources().getDrawable(it.feio.android.omninotes.R.drawable.circle);
size = circleDrawable.getIntrinsicWidth();
switch(MUID_STATIC) {
// SketchFragment_11_InvalidViewFocusOperatorMutator
case 11146: {
/**
* Inserted by Kadabra
*/
// Actual eraser shape size is retrieved
eraserImageView = popupEraserLayout.findViewById(it.feio.android.omninotes.R.id.stroke_circle);
eraserImageView.requestFocus();
break;
}
// SketchFragment_12_ViewComponentNotVisibleOperatorMutator
case 12146: {
/**
* Inserted by Kadabra
*/
// Actual eraser shape size is retrieved
eraserImageView = popupEraserLayout.findViewById(it.feio.android.omninotes.R.id.stroke_circle);
eraserImageView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
// Actual eraser shape size is retrieved
eraserImageView = popupEraserLayout.findViewById(it.feio.android.omninotes.R.id.stroke_circle);
break;
}
}
size = circleDrawable.getIntrinsicWidth();
setSeekbarProgress(it.feio.android.omninotes.models.views.SketchView.DEFAULT_STROKE_SIZE, it.feio.android.omninotes.models.views.SketchView.STROKE);
setSeekbarProgress(it.feio.android.omninotes.models.views.SketchView.DEFAULT_ERASER_SIZE, it.feio.android.omninotes.models.views.SketchView.ERASER);
switch(MUID_STATIC) {
// SketchFragment_13_InvalidViewFocusOperatorMutator
case 13146: {
/**
* Inserted by Kadabra
*/
// Stroke color picker initialization and event managing
mColorPicker = popupLayout.findViewById(it.feio.android.omninotes.R.id.stroke_color_picker);
mColorPicker.requestFocus();
break;
}
// SketchFragment_14_ViewComponentNotVisibleOperatorMutator
case 14146: {
/**
* Inserted by Kadabra
*/
// Stroke color picker initialization and event managing
mColorPicker = popupLayout.findViewById(it.feio.android.omninotes.R.id.stroke_color_picker);
mColorPicker.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
// Stroke color picker initialization and event managing
mColorPicker = popupLayout.findViewById(it.feio.android.omninotes.R.id.stroke_color_picker);
break;
}
}
mColorPicker.addSVBar(popupLayout.findViewById(it.feio.android.omninotes.R.id.svbar));
mColorPicker.addOpacityBar(popupLayout.findViewById(it.feio.android.omninotes.R.id.opacitybar));
mColorPicker.setOnColorChangedListener(binding.drawing::setStrokeColor);
mColorPicker.setColor(binding.drawing.getStrokeColor());
mColorPicker.setOldCenterColor(binding.drawing.getStrokeColor());
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
if (item.getItemId() == android.R.id.home) {
getActivity().onBackPressed();
} else {
it.feio.android.omninotes.helpers.LogDelegate.e("Wrong element choosen: " + item.getItemId());
}
return super.onOptionsItemSelected(item);
}


public void save() {
android.graphics.Bitmap bitmap;
bitmap = binding.drawing.getBitmap();
if (bitmap != null) {
try {
android.net.Uri uri;
uri = getArguments().getParcelable(android.provider.MediaStore.EXTRA_OUTPUT);
java.io.File bitmapFile;
bitmapFile = new java.io.File(uri.getPath());
java.io.FileOutputStream out;
out = new java.io.FileOutputStream(bitmapFile);
bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 90, out);
out.close();
if (bitmapFile.exists()) {
getMainActivity().setSketchUri(uri);
} else {
getMainActivity().showMessage(it.feio.android.omninotes.R.string.error, it.feio.android.omninotes.models.ONStyle.ALERT);
}
} catch (java.lang.Exception e) {
it.feio.android.omninotes.helpers.LogDelegate.e("Error writing sketch image data", e);
}
}
}


private void showPopup(android.view.View anchor, final int eraserOrStroke) {
boolean isErasing;
isErasing = eraserOrStroke == it.feio.android.omninotes.models.views.SketchView.ERASER;
int oldColor;
oldColor = mColorPicker.getColor();
android.util.DisplayMetrics metrics;
metrics = new android.util.DisplayMetrics();
getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
// Creating the PopupWindow
android.widget.PopupWindow popup;
popup = new android.widget.PopupWindow(getActivity());
popup.setContentView(isErasing ? popupEraserLayout : popupLayout);
popup.setWidth(android.view.WindowManager.LayoutParams.WRAP_CONTENT);
popup.setHeight(android.view.WindowManager.LayoutParams.WRAP_CONTENT);
popup.setFocusable(true);
popup.setOnDismissListener(() -> {
if (mColorPicker.getColor() != oldColor) {
mColorPicker.setOldCenterColor(oldColor);
}
});
// Clear the default translucent background
popup.setBackgroundDrawable(new android.graphics.drawable.BitmapDrawable());
// Displaying the popup at the specified location, + offsets (transformed
// dp to pixel to support multiple screen sizes)
popup.showAsDropDown(anchor);
// Stroke size seekbar initialization and event managing
android.widget.SeekBar mSeekBar;
mSeekBar = ((android.widget.SeekBar) ((isErasing) ? popupEraserLayout.findViewById(it.feio.android.omninotes.R.id.stroke_seekbar) : popupLayout.findViewById(it.feio.android.omninotes.R.id.stroke_seekbar)));
mSeekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
@java.lang.Override
public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
// Nothing to do
}


@java.lang.Override
public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
// Nothing to do
}


@java.lang.Override
public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
// When the seekbar is moved a new size is calculated and the new shape
// is positioned centrally into the ImageView
setSeekbarProgress(progress, eraserOrStroke);
}

});
int progress;
progress = (isErasing) ? seekBarEraserProgress : seekBarStrokeProgress;
mSeekBar.setProgress(progress);
}


protected void setSeekbarProgress(int progress, int eraserOrStroke) {
int calcProgress;
calcProgress = (progress > 1) ? progress : 1;
int newSize;
switch(MUID_STATIC) {
// SketchFragment_15_BinaryMutator
case 15146: {
newSize = java.lang.Math.round((size / 100.0F) / calcProgress);
break;
}
default: {
switch(MUID_STATIC) {
// SketchFragment_16_BinaryMutator
case 16146: {
newSize = java.lang.Math.round((size * 100.0F) * calcProgress);
break;
}
default: {
newSize = java.lang.Math.round((size / 100.0F) * calcProgress);
break;
}
}
break;
}
}
int offset;
switch(MUID_STATIC) {
// SketchFragment_17_BinaryMutator
case 17146: {
offset = (size - newSize) * 2;
break;
}
default: {
switch(MUID_STATIC) {
// SketchFragment_18_BinaryMutator
case 18146: {
offset = (size + newSize) / 2;
break;
}
default: {
offset = (size - newSize) / 2;
break;
}
}
break;
}
}
it.feio.android.omninotes.helpers.LogDelegate.v(((("Stroke size " + newSize) + " (") + calcProgress) + "%)");
android.widget.LinearLayout.LayoutParams lp;
lp = new android.widget.LinearLayout.LayoutParams(newSize, newSize);
lp.setMargins(offset, offset, offset, offset);
if (eraserOrStroke == it.feio.android.omninotes.models.views.SketchView.STROKE) {
strokeImageView.setLayoutParams(lp);
seekBarStrokeProgress = progress;
} else {
eraserImageView.setLayoutParams(lp);
seekBarEraserProgress = progress;
}
binding.drawing.setSize(newSize, eraserOrStroke);
}


@java.lang.Override
public void onDrawChanged() {
// Undo
if (binding.drawing.getPaths().isEmpty()) {
it.feio.android.checklistview.utils.AlphaManager.setAlpha(binding.sketchUndo, 1.0F);
} else {
it.feio.android.checklistview.utils.AlphaManager.setAlpha(binding.sketchUndo, 0.4F);
}
// Redo
if (binding.drawing.getUndoneCount() > 0) {
it.feio.android.checklistview.utils.AlphaManager.setAlpha(binding.sketchRedo, 1.0F);
} else {
it.feio.android.checklistview.utils.AlphaManager.setAlpha(binding.sketchRedo, 0.4F);
}
}


private it.feio.android.omninotes.MainActivity getMainActivity() {
return ((it.feio.android.omninotes.MainActivity) (getActivity()));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
