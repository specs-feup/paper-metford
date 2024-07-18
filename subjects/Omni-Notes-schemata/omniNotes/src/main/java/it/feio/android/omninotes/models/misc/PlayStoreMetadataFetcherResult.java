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
package it.feio.android.omninotes.models.misc;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PlayStoreMetadataFetcherResult {
    static final int MUID_STATIC = getMUID();
    private java.lang.String datePublished;

    private java.lang.String fileSize;

    private java.lang.String numDownloads;

    private java.lang.String softwareVersion;

    private java.lang.String operatingSystems;

    private java.lang.String contentRating;

    public java.lang.String getDatePublished() {
        return datePublished;
    }


    public void setDatePublished(java.lang.String datePublished) {
        this.datePublished = datePublished;
    }


    public java.lang.String getFileSize() {
        return fileSize;
    }


    public void setFileSize(java.lang.String fileSize) {
        this.fileSize = fileSize;
    }


    public java.lang.String getNumDownloads() {
        return numDownloads;
    }


    public void setNumDownloads(java.lang.String numDownloads) {
        this.numDownloads = numDownloads;
    }


    public java.lang.String getSoftwareVersion() {
        return softwareVersion;
    }


    public void setSoftwareVersion(java.lang.String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }


    public java.lang.String getOperatingSystems() {
        return operatingSystems;
    }


    public void setOperatingSystems(java.lang.String operatingSystems) {
        this.operatingSystems = operatingSystems;
    }


    public java.lang.String getContentRating() {
        return contentRating;
    }


    public void setContentRating(java.lang.String contentRating) {
        this.contentRating = contentRating;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
