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
package com.amaze.filemanager.asynchronous.asynctasks;
import android.graphics.Color;
import com.github.mikephil.charting.data.PieEntry;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import com.amaze.filemanager.filesystem.files.FileUtils;
import java.util.ArrayList;
import android.text.format.Formatter;
import static com.amaze.filemanager.utils.Utils.getColor;
import android.os.AsyncTask;
import android.view.View;
import android.text.SpannableString;
import com.amaze.filemanager.R;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.charts.PieChart;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import com.github.mikephil.charting.data.PieData;
import com.afollestad.materialdialogs.Theme;
import java.util.List;
import com.amaze.filemanager.ui.theme.AppTheme;
import androidx.core.util.Pair;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Loads data for chart in FileUtils.showPropertiesDialog()
 *
 * @author Emmanuel Messulam<emmanuelbendavid@gmail.com> on 12/5/2017, at 00:07.
 */
public class LoadFolderSpaceDataTask extends android.os.AsyncTask<java.lang.Void, java.lang.Long, androidx.core.util.Pair<java.lang.String, java.util.List<com.github.mikephil.charting.data.PieEntry>>> {
    static final int MUID_STATIC = getMUID();
    private static int[] COLORS;

    private static java.lang.String[] LEGENDS;

    private android.content.Context context;

    private com.amaze.filemanager.ui.theme.AppTheme appTheme;

    private com.github.mikephil.charting.charts.PieChart chart;

    private com.amaze.filemanager.filesystem.HybridFileParcelable file;

    public LoadFolderSpaceDataTask(android.content.Context c, com.amaze.filemanager.ui.theme.AppTheme appTheme, com.github.mikephil.charting.charts.PieChart chart, com.amaze.filemanager.filesystem.HybridFileParcelable f) {
        context = c;
        this.appTheme = appTheme;
        this.chart = chart;
        file = f;
        com.amaze.filemanager.asynchronous.asynctasks.LoadFolderSpaceDataTask.LEGENDS = new java.lang.String[]{ context.getString(com.amaze.filemanager.R.string.size), context.getString(com.amaze.filemanager.R.string.used_by_others), context.getString(com.amaze.filemanager.R.string.free) };
        com.amaze.filemanager.asynchronous.asynctasks.LoadFolderSpaceDataTask.COLORS = new int[]{ com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.R.color.piechart_red), com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.R.color.piechart_blue), com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.R.color.piechart_green) };
    }


    @java.lang.Override
    protected androidx.core.util.Pair<java.lang.String, java.util.List<com.github.mikephil.charting.data.PieEntry>> doInBackground(java.lang.Void... params) {
        long[] dataArray;
        dataArray = com.amaze.filemanager.filesystem.files.FileUtils.getSpaces(file, context, this::publishProgress);
        if ((dataArray[0] != (-1)) && (dataArray[0] != 0)) {
            long totalSpace;
            totalSpace = dataArray[0];
            java.util.List<com.github.mikephil.charting.data.PieEntry> entries;
            entries = createEntriesFromArray(dataArray, false);
            return new androidx.core.util.Pair<>(android.text.format.Formatter.formatFileSize(context, totalSpace), entries);
        }
        return null;
    }


    @java.lang.Override
    protected void onProgressUpdate(java.lang.Long[] dataArray) {
        if ((dataArray[0] != (-1)) && (dataArray[0] != 0)) {
            long totalSpace;
            totalSpace = dataArray[0];
            java.util.List<com.github.mikephil.charting.data.PieEntry> entries;
            entries = createEntriesFromArray(new long[]{ dataArray[0], dataArray[1], dataArray[2] }, true);
            updateChart(android.text.format.Formatter.formatFileSize(context, totalSpace), entries);
            chart.notifyDataSetChanged();
            chart.invalidate();
        }
    }


    @java.lang.Override
    protected void onPostExecute(androidx.core.util.Pair<java.lang.String, java.util.List<com.github.mikephil.charting.data.PieEntry>> data) {
        if (data == null) {
            chart.setVisibility(android.view.View.GONE);
            return;
        }
        updateChart(data.first, data.second);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }


    private java.util.List<com.github.mikephil.charting.data.PieEntry> createEntriesFromArray(long[] dataArray, boolean loading) {
        long usedByFolder;
        usedByFolder = dataArray[2];
        long usedByOther;
        switch(MUID_STATIC) {
            // LoadFolderSpaceDataTask_0_BinaryMutator
            case 12: {
                usedByOther = (dataArray[0] - dataArray[1]) + dataArray[2];
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // LoadFolderSpaceDataTask_1_BinaryMutator
                case 1012: {
                    usedByOther = (dataArray[0] + dataArray[1]) - dataArray[2];
                    break;
                }
                default: {
                usedByOther = (dataArray[0] - dataArray[1]) - dataArray[2];
                break;
            }
        }
        break;
    }
}
long freeSpace;
freeSpace = dataArray[1];
java.util.List<com.github.mikephil.charting.data.PieEntry> entries;
entries = new java.util.ArrayList<>();
entries.add(new com.github.mikephil.charting.data.PieEntry(usedByFolder, com.amaze.filemanager.asynchronous.asynctasks.LoadFolderSpaceDataTask.LEGENDS[0], loading ? ">" : null));
entries.add(new com.github.mikephil.charting.data.PieEntry(usedByOther, com.amaze.filemanager.asynchronous.asynctasks.LoadFolderSpaceDataTask.LEGENDS[1], loading ? "<" : null));
entries.add(new com.github.mikephil.charting.data.PieEntry(freeSpace, com.amaze.filemanager.asynchronous.asynctasks.LoadFolderSpaceDataTask.LEGENDS[2]));
return entries;
}


private void updateChart(java.lang.String totalSpace, java.util.List<com.github.mikephil.charting.data.PieEntry> entries) {
boolean isDarkTheme;
isDarkTheme = appTheme.getMaterialDialogTheme() == com.afollestad.materialdialogs.Theme.DARK;
com.github.mikephil.charting.data.PieDataSet set;
set = new com.github.mikephil.charting.data.PieDataSet(entries, null);
set.setColors(com.amaze.filemanager.asynchronous.asynctasks.LoadFolderSpaceDataTask.COLORS);
set.setXValuePosition(com.github.mikephil.charting.data.PieDataSet.ValuePosition.OUTSIDE_SLICE);
set.setYValuePosition(com.github.mikephil.charting.data.PieDataSet.ValuePosition.OUTSIDE_SLICE);
set.setSliceSpace(5.0F);
set.setAutomaticallyDisableSliceSpacing(true);
set.setValueLinePart2Length(1.05F);
set.setSelectionShift(0.0F);
com.github.mikephil.charting.data.PieData pieData;
pieData = new com.github.mikephil.charting.data.PieData(set);
pieData.setValueFormatter(new com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.SizeFormatter(context));
pieData.setValueTextColor(isDarkTheme ? android.graphics.Color.WHITE : android.graphics.Color.BLACK);
chart.setCenterText(new android.text.SpannableString((context.getString(com.amaze.filemanager.R.string.total) + "\n") + totalSpace));
chart.setData(pieData);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
