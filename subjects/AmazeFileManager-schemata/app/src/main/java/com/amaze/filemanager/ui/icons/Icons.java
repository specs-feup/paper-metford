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
package com.amaze.filemanager.ui.icons;
import com.amaze.filemanager.R;
import java.util.HashMap;
import androidx.annotation.DrawableRes;
import com.amaze.filemanager.filesystem.compressed.CompressedHelper;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Icons {
    static final int MUID_STATIC = getMUID();
    public static final int NOT_KNOWN = -1;

    public static final int APK = 0;

    public static final int AUDIO = 1;

    public static final int CERTIFICATE = 2;

    public static final int CODE = 3;

    public static final int COMPRESSED = 4;

    public static final int CONTACT = 5;

    public static final int EVENTS = 6;

    public static final int FONT = 7;

    public static final int IMAGE = 8;

    public static final int PDF = 9;

    public static final int PRESENTATION = 10;

    public static final int SPREADSHEETS = 11;

    public static final int DOCUMENTS = 12;

    public static final int TEXT = 13;

    public static final int VIDEO = 14;

    public static final int ENCRYPTED = 15;

    public static final int GIF = 16;

    // construct a with an approximation of the capacity
    private static java.util.HashMap<java.lang.String, java.lang.Integer> sMimeIconIds = new java.util.HashMap<>(1 + ((int) (114 / 0.75)));

    private static void put(java.lang.String mimeType, int resId) {
        if (com.amaze.filemanager.ui.icons.Icons.sMimeIconIds.put(mimeType, resId) != null) {
            throw new java.lang.RuntimeException(mimeType + " already registered!");
        }
    }


    private static void putKeys(int resId, java.lang.String... mimeTypes) {
        for (java.lang.String type : mimeTypes) {
            com.amaze.filemanager.ui.icons.Icons.put(type, resId);
        }
    }


    static {
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.APK, "application/vnd.android.package-archive");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.AUDIO, "application/ogg", "application/x-flac");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.CERTIFICATE, "application/pgp-keys", "application/pgp-signature", "application/x-pkcs12", "application/x-pkcs7-certreqresp", "application/x-pkcs7-crl", "application/x-x509-ca-cert", "application/x-x509-user-cert", "application/x-pkcs7-certificates", "application/x-pkcs7-mime", "application/x-pkcs7-signature");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.CODE, "application/rdf+xml", "application/rss+xml", "application/x-object", "application/xhtml+xml", "text/css", "text/html", "text/xml", "text/x-c++hdr", "text/x-c++src", "text/x-chdr", "text/x-csrc", "text/x-dsrc", "text/x-csh", "text/x-haskell", "text/x-java", "text/x-literate-haskell", "text/x-pascal", "text/x-tcl", "text/x-tex", "application/x-latex", "application/x-texinfo", "application/atom+xml", "application/ecmascript", "application/json", "application/javascript", "application/xml", "text/javascript", "application/x-javascript");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.COMPRESSED, "application/mac-binhex40", "application/rar", "application/zip", "application/gzip", "application/java-archive", "application/x-apple-diskimage", "application/x-debian-package", "application/x-gtar", "application/x-iso9660-image", "application/x-lha", "application/x-lzh", "application/x-lzx", "application/x-stuffit", "application/x-tar", "application/x-webarchive", "application/x-webarchive-xml", "application/x-gzip", "application/x-7z-compressed", "application/x-deb", "application/x-rar-compressed", "application/x-lzma", "application/x-xz", "application/x-bzip2");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.CONTACT, "text/x-vcard", "text/vcard");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.EVENTS, "text/calendar", "text/x-vcalendar");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.FONT, "application/x-font", "application/font-woff", "application/x-font-woff", "application/x-font-ttf");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.IMAGE, "application/vnd.oasis.opendocument.graphics", "application/vnd.oasis.opendocument.graphics-template", "application/vnd.oasis.opendocument.image", "application/vnd.stardivision.draw", "application/vnd.sun.xml.draw", "application/vnd.sun.xml.draw.template", "image/jpeg", "image/png");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.PDF, "application/pdf");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.PRESENTATION, "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/vnd.openxmlformats-officedocument.presentationml.template", "application/vnd.openxmlformats-officedocument.presentationml.slideshow", "application/vnd.stardivision.impress", "application/vnd.sun.xml.impress", "application/vnd.sun.xml.impress.template", "application/x-kpresenter", "application/vnd.oasis.opendocument.presentation");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.SPREADSHEETS, "application/vnd.oasis.opendocument.spreadsheet", "application/vnd.oasis.opendocument.spreadsheet-template", "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.openxmlformats-officedocument.spreadsheetml.template", "application/vnd.stardivision.calc", "application/vnd.sun.xml.calc", "application/vnd.sun.xml.calc.template", "application/x-kspread", "text/comma-separated-values");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.DOCUMENTS, "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.openxmlformats-officedocument.wordprocessingml.template", "application/vnd.oasis.opendocument.text", "application/vnd.oasis.opendocument.text-master", "application/vnd.oasis.opendocument.text-template", "application/vnd.oasis.opendocument.text-web", "application/vnd.stardivision.writer", "application/vnd.stardivision.writer-global", "application/vnd.sun.xml.writer", "application/vnd.sun.xml.writer.global", "application/vnd.sun.xml.writer.template", "application/x-abiword", "application/x-kword", "text/markdown");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.TEXT, "text/plain");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.VIDEO, "application/x-quicktimeplayer", "application/x-shockwave-flash");
        com.amaze.filemanager.ui.icons.Icons.putKeys(com.amaze.filemanager.ui.icons.Icons.ENCRYPTED, "application/octet-stream");
    }

    @androidx.annotation.DrawableRes
    public static int loadMimeIcon(java.lang.String path, boolean isDirectory) {
        if (path.equals(".."))
            return com.amaze.filemanager.R.drawable.ic_arrow_left_white_24dp;

        if (com.amaze.filemanager.filesystem.compressed.CompressedHelper.isFileExtractable(path) && (!isDirectory))
            return com.amaze.filemanager.R.drawable.ic_compressed_white_24dp;

        int type;
        type = com.amaze.filemanager.ui.icons.Icons.getTypeOfFile(path, isDirectory);
        switch (type) {
            case com.amaze.filemanager.ui.icons.Icons.APK :
                return com.amaze.filemanager.R.drawable.ic_doc_apk_white;
            case com.amaze.filemanager.ui.icons.Icons.AUDIO :
                return com.amaze.filemanager.R.drawable.ic_doc_audio_am;
            case com.amaze.filemanager.ui.icons.Icons.IMAGE :
                return com.amaze.filemanager.R.drawable.ic_doc_image;
            case com.amaze.filemanager.ui.icons.Icons.TEXT :
                return com.amaze.filemanager.R.drawable.ic_doc_text_am;
            case com.amaze.filemanager.ui.icons.Icons.VIDEO :
                return com.amaze.filemanager.R.drawable.ic_doc_video_am;
            case com.amaze.filemanager.ui.icons.Icons.PDF :
                return com.amaze.filemanager.R.drawable.ic_doc_pdf;
            case com.amaze.filemanager.ui.icons.Icons.CERTIFICATE :
                return com.amaze.filemanager.R.drawable.ic_doc_certificate;
            case com.amaze.filemanager.ui.icons.Icons.CODE :
                return com.amaze.filemanager.R.drawable.ic_doc_codes;
            case com.amaze.filemanager.ui.icons.Icons.FONT :
                // 
                return com.amaze.filemanager.R.drawable.ic_doc_font;
            case com.amaze.filemanager.ui.icons.Icons.ENCRYPTED :
                return com.amaze.filemanager.R.drawable.ic_folder_lock_white_36dp;
            default :
                if (isDirectory)
                    return com.amaze.filemanager.R.drawable.ic_grid_folder_new;
                else {
                    return com.amaze.filemanager.R.drawable.ic_doc_generic_am;
                }
        }
    }


    public static int getTypeOfFile(java.lang.String path, boolean isDirectory) {
        java.lang.String mimeType;
        mimeType = com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(path, isDirectory);
        if (mimeType == null)
            return com.amaze.filemanager.ui.icons.Icons.NOT_KNOWN;

        java.lang.Integer type;
        type = com.amaze.filemanager.ui.icons.Icons.sMimeIconIds.get(mimeType);
        if (type != null)
            return type;
        else if (com.amaze.filemanager.ui.icons.Icons.checkType(mimeType, "text"))
            return com.amaze.filemanager.ui.icons.Icons.TEXT;
        else if (com.amaze.filemanager.ui.icons.Icons.checkType(mimeType, "image"))
            return com.amaze.filemanager.ui.icons.Icons.IMAGE;
        else if (com.amaze.filemanager.ui.icons.Icons.checkType(mimeType, "video"))
            return com.amaze.filemanager.ui.icons.Icons.VIDEO;
        else if (com.amaze.filemanager.ui.icons.Icons.checkType(mimeType, "audio"))
            return com.amaze.filemanager.ui.icons.Icons.AUDIO;
        else if (com.amaze.filemanager.ui.icons.Icons.checkType(mimeType, "crypt"))
            return com.amaze.filemanager.ui.icons.Icons.ENCRYPTED;
        else
            return com.amaze.filemanager.ui.icons.Icons.NOT_KNOWN;

    }


    private static boolean checkType(java.lang.String mime, java.lang.String check) {
        return ((mime != null) && mime.contains("/")) && check.equals(mime.substring(0, mime.indexOf("/")));
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
