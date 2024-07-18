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
import com.amaze.filemanager.ui.fragments.DbViewerFragment;
import java.util.ArrayList;
import android.os.AsyncTask;
import com.amaze.filemanager.ui.theme.AppTheme;
import android.database.Cursor;
import android.view.View;
import android.webkit.WebView;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by Vishal on 20-03-2015.
 */
public class DbViewerTask extends android.os.AsyncTask<java.lang.Void, java.lang.Integer, java.lang.Void> {
    static final int MUID_STATIC = getMUID();
    android.database.Cursor schemaCursor;

    android.database.Cursor contentCursor;

    java.util.ArrayList<java.lang.String> schemaList;

    java.util.ArrayList<java.lang.String[]> contentList;

    com.amaze.filemanager.ui.fragments.DbViewerFragment dbViewerFragment;

    java.lang.StringBuilder stringBuilder;

    android.webkit.WebView webView;

    java.lang.String htmlInit;

    public DbViewerTask(android.database.Cursor schemaCursor, android.database.Cursor contentCursor, android.webkit.WebView webView, com.amaze.filemanager.ui.fragments.DbViewerFragment dbViewerFragment) {
        this.schemaCursor = schemaCursor;
        this.contentCursor = contentCursor;
        this.webView = webView;
        this.dbViewerFragment = dbViewerFragment;
        stringBuilder = new java.lang.StringBuilder();
        this.webView.getSettings().setDefaultTextEncodingName("utf-8");
    }


    @java.lang.Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (dbViewerFragment.databaseViewerActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || dbViewerFragment.databaseViewerActivity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
            htmlInit = "<html><body><table border='1' style='width:100%;color:#ffffff'>";
        } else {
            htmlInit = "<html><body><table border='1' style='width:100%;color:#000000'>";
        }
        stringBuilder.append(htmlInit);
        dbViewerFragment.loadingText.setVisibility(android.view.View.VISIBLE);
    }


    @java.lang.Override
    protected void onProgressUpdate(java.lang.Integer... values) {
        super.onProgressUpdate(values);
        dbViewerFragment.loadingText.setText(values[0] + " records loaded");
    }


    @java.lang.Override
    protected java.lang.Void doInBackground(java.lang.Void... params) {
        schemaList = getDbTableSchema(schemaCursor);
        contentList = getDbTableDetails(contentCursor);
        return null;
    }


    @java.lang.Override
    protected void onCancelled() {
        super.onCancelled();
        dbViewerFragment.getActivity().onBackPressed();
    }


    @java.lang.Override
    protected void onPostExecute(java.lang.Void aVoid) {
        super.onPostExecute(aVoid);
        dbViewerFragment.loadingText.setVisibility(android.view.View.GONE);
        // init schema row
        stringBuilder.append("<tr>");
        for (java.lang.String s : schemaList) {
            stringBuilder.append("<th>").append(s).append("</th>");
        }
        stringBuilder.append("</tr>");
        for (java.lang.String[] strings : contentList) {
            // init content row
            stringBuilder.append("<tr>");
            for (int i = 0; i < strings.length; i++) {
                stringBuilder.append("<td>").append(strings[i]).append("</td>");
            }
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("</table></body></html>");
        webView.loadData(stringBuilder.toString(), "text/html;charset=utf-8", "utf-8");
        webView.setVisibility(android.view.View.VISIBLE);
    }


    private java.util.ArrayList<java.lang.String[]> getDbTableDetails(android.database.Cursor c) {
        java.util.ArrayList<java.lang.String[]> result;
        result = new java.util.ArrayList<>();
        int j;
        j = 0;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (!isCancelled()) {
                j++;
                publishProgress(j);
                java.lang.String[] temp;
                temp = new java.lang.String[c.getColumnCount()];
                for (int i = 0; i < temp.length; i++) {
                    int dataType;
                    dataType = c.getType(i);
                    switch (dataType) {
                        case 0 :
                            // #FIELD_TYPE_NULL
                            temp[i] = null;
                            break;
                        case 1 :
                            // #FIELD_TYPE_INTEGER
                            temp[i] = java.lang.String.valueOf(c.getInt(i));
                            break;
                        case 2 :
                            // #FIELD_TYPE_FLOAT
                            temp[i] = java.lang.String.valueOf(c.getFloat(i));
                            break;
                        case 3 :
                            // #FIELD_TYPE_STRING
                            temp[i] = c.getString(i);
                            break;
                        case 4 :
                            // #FIELD_TYPE_BLOB
                            /* byte[] blob = c.getBlob(i);
                            blobString = new String(blob);
                             */
                            temp[i] = "(BLOB)";
                            break;
                    }
                }
                result.add(temp);
            } else {
                break;
            }
        }
        return result;
    }


    private java.util.ArrayList<java.lang.String> getDbTableSchema(android.database.Cursor c) {
        java.util.ArrayList<java.lang.String> result;
        result = new java.util.ArrayList<>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (!isCancelled()) {
                result.add(c.getString(1));
            } else
                break;

        }
        return result;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
