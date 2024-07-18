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
package com.amaze.filemanager.ui.fragments;
import com.amaze.filemanager.databinding.ProcessparentBinding;
import android.content.ServiceConnection;
import com.amaze.filemanager.filesystem.files.FileUtils;
import com.amaze.filemanager.utils.DatapointParcelable;
import java.util.ArrayList;
import android.text.format.Formatter;
import com.amaze.filemanager.ui.activities.MainActivity;
import androidx.fragment.app.Fragment;
import com.amaze.filemanager.asynchronous.services.ExtractService;
import java.lang.ref.WeakReference;
import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT;
import com.amaze.filemanager.R;
import com.github.mikephil.charting.data.Entry;
import androidx.core.content.ContextCompat;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import android.os.IBinder;
import com.amaze.filemanager.asynchronous.services.ZipService;
import androidx.annotation.NonNull;
import com.amaze.filemanager.asynchronous.services.DecryptService;
import android.content.ComponentName;
import android.widget.Toast;
import com.amaze.filemanager.ui.theme.AppTheme;
import com.github.mikephil.charting.components.YAxis;
import android.graphics.Color;
import androidx.core.text.HtmlCompat;
import com.github.mikephil.charting.data.LineDataSet;
import android.os.Bundle;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.graphics.Typeface;
import android.text.Spanned;
import android.content.Intent;
import com.amaze.filemanager.asynchronous.services.CopyService;
import com.github.mikephil.charting.components.XAxis;
import com.amaze.filemanager.asynchronous.services.AbstractProgressiveService;
import android.view.View;
import com.amaze.filemanager.utils.ObtainableServiceBinder;
import com.github.mikephil.charting.data.LineData;
import android.view.LayoutInflater;
import com.amaze.filemanager.utils.Utils;
import android.graphics.drawable.ColorDrawable;
import com.amaze.filemanager.asynchronous.services.EncryptService;
import androidx.annotation.Nullable;
import com.github.mikephil.charting.charts.LineChart;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ProcessViewerFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    private static final int SERVICE_COPY = 0;

    private static final int SERVICE_EXTRACT = 1;

    private static final int SERVICE_COMPRESS = 2;

    private static final int SERVICE_ENCRYPT = 3;

    /**
     * Helps defining the result type for {@link #processResults(DatapointParcelable, int)} to process
     */
    private static final int SERVICE_DECRYPT = 4;

    private boolean isInitialized = false;

    private com.amaze.filemanager.ui.activities.MainActivity mainActivity;

    private int accentColor;

    private final com.github.mikephil.charting.data.LineData lineData = new com.github.mikephil.charting.data.LineData();

    private com.amaze.filemanager.databinding.ProcessparentBinding binding = null;

    /**
     * Time in seconds just for showing to the user. No guarantees.
     */
    private long looseTimeInSeconds = 0L;

    private android.content.ServiceConnection mCopyConnection;

    private android.content.ServiceConnection mExtractConnection;

    private android.content.ServiceConnection mCompressConnection;

    private android.content.ServiceConnection mEncryptConnection;

    private android.content.ServiceConnection mDecryptConnection;

    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        binding = com.amaze.filemanager.databinding.ProcessparentBinding.inflate(inflater);
        android.view.View rootView;
        rootView = binding.getRoot();
        mainActivity = ((com.amaze.filemanager.ui.activities.MainActivity) (getActivity()));
        accentColor = mainActivity.getAccent();
        if (mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
            rootView.setBackgroundResource(com.amaze.filemanager.R.color.cardView_background);

        if (mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
            binding.deleteButton.setImageResource(com.amaze.filemanager.R.drawable.ic_action_cancel);
            binding.cardView.setCardBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.R.color.cardView_foreground));
            binding.cardView.setCardElevation(0.0F);
        }
        return rootView;
    }


    @java.lang.Override
    public void onViewCreated(@androidx.annotation.NonNull
    android.view.View view, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCopyConnection = new com.amaze.filemanager.ui.fragments.ProcessViewerFragment.CustomServiceConnection(this, binding.progressChart, com.amaze.filemanager.ui.fragments.ProcessViewerFragment.SERVICE_COPY);
        mExtractConnection = new com.amaze.filemanager.ui.fragments.ProcessViewerFragment.CustomServiceConnection(this, binding.progressChart, com.amaze.filemanager.ui.fragments.ProcessViewerFragment.SERVICE_EXTRACT);
        mCompressConnection = new com.amaze.filemanager.ui.fragments.ProcessViewerFragment.CustomServiceConnection(this, binding.progressChart, com.amaze.filemanager.ui.fragments.ProcessViewerFragment.SERVICE_COMPRESS);
        mEncryptConnection = new com.amaze.filemanager.ui.fragments.ProcessViewerFragment.CustomServiceConnection(this, binding.progressChart, com.amaze.filemanager.ui.fragments.ProcessViewerFragment.SERVICE_ENCRYPT);
        mDecryptConnection = new com.amaze.filemanager.ui.fragments.ProcessViewerFragment.CustomServiceConnection(this, binding.progressChart, com.amaze.filemanager.ui.fragments.ProcessViewerFragment.SERVICE_DECRYPT);
        mainActivity.getAppbar().setTitle(com.amaze.filemanager.R.string.process_viewer);
        mainActivity.hideFab();
        mainActivity.getAppbar().getBottomBar().setVisibility(android.view.View.GONE);
        mainActivity.supportInvalidateOptionsMenu();
        int skin_color;
        skin_color = mainActivity.getCurrentColorPreference().getPrimaryFirstTab();
        int skinTwoColor;
        skinTwoColor = mainActivity.getCurrentColorPreference().getPrimarySecondTab();
        accentColor = mainActivity.getAccent();
        mainActivity.updateViews(new android.graphics.drawable.ColorDrawable(com.amaze.filemanager.ui.activities.MainActivity.currentTab == 1 ? skinTwoColor : skin_color));
    }


    @java.lang.Override
    public void onResume() {
        super.onResume();
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // ProcessViewerFragment_0_NullIntentOperatorMutator
            case 117: {
                intent = null;
                break;
            }
            // ProcessViewerFragment_1_InvalidKeyIntentOperatorMutator
            case 1117: {
                intent = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, com.amaze.filemanager.asynchronous.services.CopyService.class);
                break;
            }
            // ProcessViewerFragment_2_RandomActionIntentDefinitionOperatorMutator
            case 2117: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(getActivity(), com.amaze.filemanager.asynchronous.services.CopyService.class);
            break;
        }
    }
    getActivity().bindService(intent, mCopyConnection, 0);
    android.content.Intent intent1;
    switch(MUID_STATIC) {
        // ProcessViewerFragment_3_NullIntentOperatorMutator
        case 3117: {
            intent1 = null;
            break;
        }
        // ProcessViewerFragment_4_InvalidKeyIntentOperatorMutator
        case 4117: {
            intent1 = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, com.amaze.filemanager.asynchronous.services.ExtractService.class);
            break;
        }
        // ProcessViewerFragment_5_RandomActionIntentDefinitionOperatorMutator
        case 5117: {
            intent1 = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent1 = new android.content.Intent(getActivity(), com.amaze.filemanager.asynchronous.services.ExtractService.class);
        break;
    }
}
getActivity().bindService(intent1, mExtractConnection, 0);
android.content.Intent intent2;
switch(MUID_STATIC) {
    // ProcessViewerFragment_6_NullIntentOperatorMutator
    case 6117: {
        intent2 = null;
        break;
    }
    // ProcessViewerFragment_7_InvalidKeyIntentOperatorMutator
    case 7117: {
        intent2 = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, com.amaze.filemanager.asynchronous.services.ZipService.class);
        break;
    }
    // ProcessViewerFragment_8_RandomActionIntentDefinitionOperatorMutator
    case 8117: {
        intent2 = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent2 = new android.content.Intent(getActivity(), com.amaze.filemanager.asynchronous.services.ZipService.class);
    break;
}
}
getActivity().bindService(intent2, mCompressConnection, 0);
android.content.Intent intent3;
switch(MUID_STATIC) {
// ProcessViewerFragment_9_NullIntentOperatorMutator
case 9117: {
    intent3 = null;
    break;
}
// ProcessViewerFragment_10_InvalidKeyIntentOperatorMutator
case 10117: {
    intent3 = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, com.amaze.filemanager.asynchronous.services.EncryptService.class);
    break;
}
// ProcessViewerFragment_11_RandomActionIntentDefinitionOperatorMutator
case 11117: {
    intent3 = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent3 = new android.content.Intent(getActivity(), com.amaze.filemanager.asynchronous.services.EncryptService.class);
break;
}
}
getActivity().bindService(intent3, mEncryptConnection, 0);
android.content.Intent intent4;
switch(MUID_STATIC) {
// ProcessViewerFragment_12_NullIntentOperatorMutator
case 12117: {
intent4 = null;
break;
}
// ProcessViewerFragment_13_InvalidKeyIntentOperatorMutator
case 13117: {
intent4 = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, com.amaze.filemanager.asynchronous.services.DecryptService.class);
break;
}
// ProcessViewerFragment_14_RandomActionIntentDefinitionOperatorMutator
case 14117: {
intent4 = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent4 = new android.content.Intent(getActivity(), com.amaze.filemanager.asynchronous.services.DecryptService.class);
break;
}
}
getActivity().bindService(intent4, mDecryptConnection, 0);
}


@java.lang.Override
public void onPause() {
super.onPause();
getActivity().unbindService(mCopyConnection);
getActivity().unbindService(mExtractConnection);
getActivity().unbindService(mCompressConnection);
getActivity().unbindService(mEncryptConnection);
getActivity().unbindService(mDecryptConnection);
}


@java.lang.Override
public void onDestroyView() {
super.onDestroyView();
binding = null;
mainActivity = null;
}


public void processResults(final com.amaze.filemanager.utils.DatapointParcelable dataPackage, int serviceType) {
if (binding == null)
return;

if (dataPackage != null) {
java.lang.String name;
name = dataPackage.getName();
long total;
total = dataPackage.getTotalSize();
long doneBytes;
doneBytes = dataPackage.getByteProgress();
boolean move;
move = dataPackage.getMove();
if (!isInitialized) {
// initializing views for the first time
chartInit(total);
// setting progress image
setupDrawables(serviceType, move);
isInitialized = true;
}
addEntry(com.amaze.filemanager.filesystem.files.FileUtils.readableFileSizeFloat(doneBytes), com.amaze.filemanager.filesystem.files.FileUtils.readableFileSizeFloat(dataPackage.getSpeedRaw()));
binding.textViewProgressFileName.setText(name);
android.text.Spanned bytesText;
bytesText = androidx.core.text.HtmlCompat.fromHtml(((((((((getResources().getString(com.amaze.filemanager.R.string.written) + " <font color='") + accentColor) + "'><i>") + android.text.format.Formatter.formatFileSize(getContext(), doneBytes)) + " </font></i>") + getResources().getString(com.amaze.filemanager.R.string.out_of)) + " <i>") + android.text.format.Formatter.formatFileSize(getContext(), total)) + "</i>", androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT);
binding.textViewProgressBytes.setText(bytesText);
android.text.Spanned fileProcessedSpan;
fileProcessedSpan = androidx.core.text.HtmlCompat.fromHtml(((((((((getResources().getString(com.amaze.filemanager.R.string.processing_file) + " <font color='") + accentColor) + "'><i>") + dataPackage.getSourceProgress()) + " </font></i>") + getResources().getString(com.amaze.filemanager.R.string.of)) + " <i>") + dataPackage.getAmountOfSourceFiles()) + "</i>", androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT);
binding.textViewProgressFile.setText(fileProcessedSpan);
android.text.Spanned speedSpan;
speedSpan = androidx.core.text.HtmlCompat.fromHtml(((((getResources().getString(com.amaze.filemanager.R.string.current_speed) + ": <font color='") + accentColor) + "'><i>") + android.text.format.Formatter.formatFileSize(getContext(), dataPackage.getSpeedRaw())) + "/s</font></i>", androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT);
binding.textViewProgressSpeed.setText(speedSpan);
android.text.Spanned timerSpan;
timerSpan = androidx.core.text.HtmlCompat.fromHtml(((((getResources().getString(com.amaze.filemanager.R.string.service_timer) + ": <font color='") + accentColor) + "'><i>") + com.amaze.filemanager.utils.Utils.formatTimer(++looseTimeInSeconds)) + "</font></i>", androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT);
binding.textViewProgressTimer.setText(timerSpan);
if (dataPackage.getCompleted())
binding.deleteButton.setVisibility(android.view.View.GONE);

}
}


/**
 * Setup drawables and click listeners based on the SERVICE_* constants
 */
private void setupDrawables(int serviceType, boolean isMove) {
switch (serviceType) {
case com.amaze.filemanager.ui.fragments.ProcessViewerFragment.SERVICE_COPY :
if (mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
android.graphics.drawable.Drawable copyIcon;
copyIcon = androidx.core.content.ContextCompat.getDrawable(requireContext(), com.amaze.filemanager.R.drawable.ic_content_copy_white_36dp);
binding.progressImage.setImageDrawable(copyIcon);
} else {
android.graphics.drawable.Drawable greyCopyIcon;
greyCopyIcon = androidx.core.content.ContextCompat.getDrawable(requireContext(), com.amaze.filemanager.R.drawable.ic_content_copy_grey600_36dp);
binding.progressImage.setImageDrawable(greyCopyIcon);
}
binding.textViewProgressType.setText(isMove ? getResources().getString(com.amaze.filemanager.R.string.moving) : getResources().getString(com.amaze.filemanager.R.string.copying));
cancelBroadcast(new android.content.Intent(com.amaze.filemanager.asynchronous.services.CopyService.TAG_BROADCAST_COPY_CANCEL));
break;
case com.amaze.filemanager.ui.fragments.ProcessViewerFragment.SERVICE_EXTRACT :
if (mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
android.graphics.drawable.Drawable zipBoxIcon;
zipBoxIcon = androidx.core.content.ContextCompat.getDrawable(requireContext(), com.amaze.filemanager.R.drawable.ic_zip_box_white);
binding.progressImage.setImageDrawable(zipBoxIcon);
} else {
android.graphics.drawable.Drawable greyZipBoxIcon;
greyZipBoxIcon = androidx.core.content.ContextCompat.getDrawable(requireContext(), com.amaze.filemanager.R.drawable.ic_zip_box_grey);
binding.progressImage.setImageDrawable(greyZipBoxIcon);
}
binding.textViewProgressType.setText(getResources().getString(com.amaze.filemanager.R.string.extracting));
cancelBroadcast(new android.content.Intent(com.amaze.filemanager.asynchronous.services.ExtractService.TAG_BROADCAST_EXTRACT_CANCEL));
break;
case com.amaze.filemanager.ui.fragments.ProcessViewerFragment.SERVICE_COMPRESS :
if (mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
android.graphics.drawable.Drawable zipBoxIcon;
zipBoxIcon = androidx.core.content.ContextCompat.getDrawable(requireContext(), com.amaze.filemanager.R.drawable.ic_zip_box_white);
binding.progressImage.setImageDrawable(zipBoxIcon);
} else {
android.graphics.drawable.Drawable greyZipBoxIcon;
greyZipBoxIcon = androidx.core.content.ContextCompat.getDrawable(requireContext(), com.amaze.filemanager.R.drawable.ic_zip_box_grey);
binding.progressImage.setImageDrawable(greyZipBoxIcon);
}
binding.textViewProgressType.setText(getResources().getString(com.amaze.filemanager.R.string.compressing));
cancelBroadcast(new android.content.Intent(com.amaze.filemanager.asynchronous.services.ZipService.KEY_COMPRESS_BROADCAST_CANCEL));
break;
case com.amaze.filemanager.ui.fragments.ProcessViewerFragment.SERVICE_ENCRYPT :
if (mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
android.graphics.drawable.Drawable folderIcon;
folderIcon = androidx.core.content.ContextCompat.getDrawable(requireContext(), com.amaze.filemanager.R.drawable.ic_folder_lock_white_36dp);
binding.progressImage.setImageDrawable(folderIcon);
} else {
android.graphics.drawable.Drawable greyFolderIcon;
greyFolderIcon = androidx.core.content.ContextCompat.getDrawable(requireContext(), com.amaze.filemanager.R.drawable.ic_folder_lock_grey600_36dp);
binding.progressImage.setImageDrawable(greyFolderIcon);
}
binding.textViewProgressType.setText(getResources().getString(com.amaze.filemanager.R.string.crypt_encrypting));
cancelBroadcast(new android.content.Intent(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_BROADCAST_CRYPT_CANCEL));
break;
case com.amaze.filemanager.ui.fragments.ProcessViewerFragment.SERVICE_DECRYPT :
if (mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || mainActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
android.graphics.drawable.Drawable folderUnlockedIcon;
folderUnlockedIcon = androidx.core.content.ContextCompat.getDrawable(requireContext(), com.amaze.filemanager.R.drawable.ic_folder_lock_open_white_36dp);
binding.progressImage.setImageDrawable(folderUnlockedIcon);
} else {
android.graphics.drawable.Drawable greyFolderUnlockedIcon;
greyFolderUnlockedIcon = androidx.core.content.ContextCompat.getDrawable(requireContext(), com.amaze.filemanager.R.drawable.ic_folder_lock_open_grey600_36dp);
binding.progressImage.setImageDrawable(greyFolderUnlockedIcon);
}
binding.textViewProgressType.setText(getResources().getString(com.amaze.filemanager.R.string.crypt_decrypting));
cancelBroadcast(new android.content.Intent(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_BROADCAST_CRYPT_CANCEL));
break;
}
}


/**
 * Setup click listener to cancel button click for various intent types
 */
private void cancelBroadcast(final android.content.Intent intent) {
if (binding == null)
return;

switch(MUID_STATIC) {
// ProcessViewerFragment_15_BuggyGUIListenerOperatorMutator
case 15117: {
binding.deleteButton.setOnClickListener(null);
break;
}
default: {
binding.deleteButton.setOnClickListener((android.view.View v) -> {
android.widget.Toast.makeText(getActivity(), getResources().getString(com.amaze.filemanager.R.string.stopping), android.widget.Toast.LENGTH_LONG).show();
getActivity().sendBroadcast(intent);
binding.textViewProgressType.setText(getResources().getString(com.amaze.filemanager.R.string.cancelled));
binding.textViewProgressSpeed.setText("");
binding.textViewProgressFile.setText("");
binding.textViewProgressBytes.setText("");
binding.textViewProgressFileName.setText("");
binding.textViewProgressType.setTextColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), android.R.color.holo_red_light));
});
break;
}
}
}


/**
 * Add a new entry dynamically to the chart, initializes a {@link LineDataSet} if not done so
 *
 * @param xValue
 * 		the x-axis value, the number of bytes processed till now
 * @param yValue
 * 		the y-axis value, bytes processed per sec
 */
private void addEntry(float xValue, float yValue) {
com.github.mikephil.charting.interfaces.datasets.ILineDataSet dataSet;
dataSet = lineData.getDataSetByIndex(0);
if (dataSet == null) {
// adding set for first time
dataSet = createDataSet();
lineData.addDataSet(dataSet);
}
dataSet.addEntry(new com.github.mikephil.charting.data.Entry(xValue, yValue));
lineData.notifyDataChanged();
binding.progressChart.notifyDataSetChanged();
binding.progressChart.invalidate();
}


/**
 * Creates an instance for {@link LineDataSet} which will store the entries
 */
private com.github.mikephil.charting.data.LineDataSet createDataSet() {
com.github.mikephil.charting.data.LineDataSet lineDataset;
lineDataset = new com.github.mikephil.charting.data.LineDataSet(new java.util.ArrayList<com.github.mikephil.charting.data.Entry>(), null);
lineDataset.setLineWidth(1.75F);
lineDataset.setCircleRadius(5.0F);
lineDataset.setCircleHoleRadius(2.5F);
lineDataset.setColor(android.graphics.Color.WHITE);
lineDataset.setCircleColor(android.graphics.Color.WHITE);
lineDataset.setHighLightColor(android.graphics.Color.WHITE);
lineDataset.setDrawValues(false);
lineDataset.setCircleColorHole(accentColor);
return lineDataset;
}


/**
 * Initialize chart for the first time
 *
 * @param totalBytes
 * 		maximum value for x-axis
 */
private void chartInit(long totalBytes) {
binding.progressChart.setBackgroundColor(accentColor);
binding.progressChart.getLegend().setEnabled(false);
// no description text
binding.progressChart.getDescription().setEnabled(false);
com.github.mikephil.charting.components.XAxis xAxis;
xAxis = binding.progressChart.getXAxis();
com.github.mikephil.charting.components.YAxis yAxisLeft;
yAxisLeft = binding.progressChart.getAxisLeft();
binding.progressChart.getAxisRight().setEnabled(false);
yAxisLeft.setTextColor(android.graphics.Color.WHITE);
yAxisLeft.setAxisLineColor(android.graphics.Color.TRANSPARENT);
yAxisLeft.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
yAxisLeft.setGridColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.R.color.white_translucent));
xAxis.setAxisMaximum(com.amaze.filemanager.filesystem.files.FileUtils.readableFileSizeFloat(totalBytes));
xAxis.setAxisMinimum(0.0F);
xAxis.setAxisLineColor(android.graphics.Color.TRANSPARENT);
xAxis.setGridColor(android.graphics.Color.TRANSPARENT);
xAxis.setTextColor(android.graphics.Color.WHITE);
xAxis.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
binding.progressChart.setData(lineData);
binding.progressChart.invalidate();
}


private static class CustomServiceConnection implements android.content.ServiceConnection {
private final java.lang.ref.WeakReference<com.amaze.filemanager.ui.fragments.ProcessViewerFragment> fragment;

private final java.lang.ref.WeakReference<com.github.mikephil.charting.charts.LineChart> lineChart;

private final int serviceType;

public CustomServiceConnection(com.amaze.filemanager.ui.fragments.ProcessViewerFragment frag, com.github.mikephil.charting.charts.LineChart lineChart, int serviceType) {
fragment = new java.lang.ref.WeakReference<>(frag);
this.lineChart = new java.lang.ref.WeakReference<>(lineChart);
this.serviceType = serviceType;
}


@java.lang.Override
public void onServiceConnected(android.content.ComponentName name, android.os.IBinder service) {
com.amaze.filemanager.utils.ObtainableServiceBinder<? extends com.amaze.filemanager.asynchronous.services.AbstractProgressiveService> binder;
binder = ((com.amaze.filemanager.utils.ObtainableServiceBinder<? extends com.amaze.filemanager.asynchronous.services.AbstractProgressiveService>) (service));
com.amaze.filemanager.asynchronous.services.AbstractProgressiveService specificService;
specificService = binder.getService();
for (int i = 0; i < specificService.getDataPackageSize(); i++) {
com.amaze.filemanager.utils.DatapointParcelable dataPackage;
dataPackage = specificService.getDataPackage(i);
com.amaze.filemanager.ui.fragments.ProcessViewerFragment processViewerFragment;
processViewerFragment = fragment.get();
if (processViewerFragment != null) {
processViewerFragment.processResults(dataPackage, serviceType);
}
}
// animate the chart a little after initial values have been applied
com.github.mikephil.charting.charts.LineChart chart;
chart = lineChart.get();
if (chart != null) {
chart.animateXY(500, 500);
}
specificService.setProgressListener(new com.amaze.filemanager.asynchronous.services.AbstractProgressiveService.ProgressListener() {
@java.lang.Override
public void onUpdate(final com.amaze.filemanager.utils.DatapointParcelable dataPackage) {
com.amaze.filemanager.ui.fragments.ProcessViewerFragment processViewerFragment;
processViewerFragment = fragment.get();
if (processViewerFragment != null) {
if (processViewerFragment.getActivity() == null) {
    // callback called when we're not inside the app
    return;
}
processViewerFragment.getActivity().runOnUiThread(() -> processViewerFragment.processResults(dataPackage, serviceType));
}
}


@java.lang.Override
public void refresh() {
}

});
}


@java.lang.Override
public void onServiceDisconnected(android.content.ComponentName name) {
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
