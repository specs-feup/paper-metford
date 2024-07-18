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
import java.util.regex.Pattern;
import java.util.Locale;
import java.util.HashMap;
import com.amaze.filemanager.filesystem.files.CryptUtil;
import android.webkit.MimeTypeMap;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public final class MimeTypes {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ALL_MIME_TYPES = "*/*";

    // construct a with an approximation of the capacity
    private static final java.util.HashMap<java.lang.String, java.lang.String> MIME_TYPES = new java.util.HashMap<>(1 + ((int) (68 / 0.75)));

    static {
        /* ================= MIME TYPES ==================== */
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("asm", "text/x-asm");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("json", "application/json");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("js", "application/javascript");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("def", "text/plain");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("in", "text/plain");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("rc", "text/plain");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("list", "text/plain");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("log", "text/plain");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("pl", "text/plain");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("prop", "text/plain");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("properties", "text/plain");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("ini", "text/plain");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("md", "text/markdown");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("epub", "application/epub+zip");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("ibooks", "application/x-ibooks+zip");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("ifb", "text/calendar");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("eml", "message/rfc822");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("msg", "application/vnd.ms-outlook");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("ace", "application/x-ace-compressed");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("bz", "application/x-bzip");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("bz2", "application/x-bzip2");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("cab", "application/vnd.ms-cab-compressed");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("gz", "application/x-gzip");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("7z", "application/x-7z-compressed");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("lrf", "application/octet-stream");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("jar", "application/java-archive");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("xz", "application/x-xz");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("lzma", "application/x-lzma");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("Z", "application/x-compress");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("bat", "application/x-msdownload");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("ksh", "text/plain");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("sh", "application/x-sh");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("db", "application/octet-stream");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("db3", "application/octet-stream");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("otf", "application/x-font-otf");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("ttf", "application/x-font-ttf");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("psf", "application/x-font-linux-psf");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("cgm", "image/cgm");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("btif", "image/prs.btif");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("dwg", "image/vnd.dwg");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("dxf", "image/vnd.dxf");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("fbs", "image/vnd.fastbidsheet");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("fpx", "image/vnd.fpx");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("fst", "image/vnd.fst");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("mdi", "image/vnd.ms-mdi");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("npx", "image/vnd.net-fpx");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("xif", "image/vnd.xiff");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("pct", "image/x-pict");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("pic", "image/x-pict");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("gif", "image/gif");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("adp", "audio/adpcm");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("au", "audio/basic");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("snd", "audio/basic");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("m2a", "audio/mpeg");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("m3a", "audio/mpeg");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("oga", "audio/ogg");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("spx", "audio/ogg");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("aac", "audio/x-aac");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("mka", "audio/x-matroska");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("opus", "audio/ogg");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("jpgv", "video/jpeg");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("jpgm", "video/jpm");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("jpm", "video/jpm");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("mj2", "video/mj2");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("mjp2", "video/mj2");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("mpa", "video/mpeg");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("ogv", "video/ogg");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("flv", "video/x-flv");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("mkv", "video/x-matroska");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put("mts", "video/mp2t");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION.replace(".", ""), "crypt/aze");
        com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.put(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION.replace(".", ""), "crypt/x-aescrypt");
    }

    /**
     * Get Mime Type of a file
     *
     * @param path
     * 		the file of which mime type to get
     * @return Mime type in form of String
     */
    public static java.lang.String getMimeType(java.lang.String path, boolean isDirectory) {
        if (isDirectory) {
            return null;
        }
        java.lang.String type;
        type = com.amaze.filemanager.ui.icons.MimeTypes.ALL_MIME_TYPES;
        final java.lang.String extension;
        extension = com.amaze.filemanager.ui.icons.MimeTypes.getExtension(path);
        // mapping extension to system mime types
        if ((extension != null) && (!extension.isEmpty())) {
            final java.lang.String extensionLowerCase;
            extensionLowerCase = extension.toLowerCase(java.util.Locale.getDefault());
            final android.webkit.MimeTypeMap mime;
            mime = android.webkit.MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extensionLowerCase);
            if (type == null) {
                type = com.amaze.filemanager.ui.icons.MimeTypes.MIME_TYPES.get(extensionLowerCase);
            }
        }
        if (type == null)
            type = com.amaze.filemanager.ui.icons.MimeTypes.ALL_MIME_TYPES;

        return type;
    }


    public static boolean mimeTypeMatch(java.lang.String mime, java.lang.String input) {
        return java.util.regex.Pattern.matches(mime.replace("*", ".*"), input);
    }


    /**
     * Helper method for {@link #getMimeType(String, boolean)} to calculate the last '.' extension of
     * files
     *
     * @param path
     * 		the path of file
     * @return extension extracted from name in lowercase
     */
    public static java.lang.String getExtension(@androidx.annotation.Nullable
    java.lang.String path) {
        if ((path != null) && path.contains(".")) {
            switch(MUID_STATIC) {
                // MimeTypes_0_BinaryMutator
                case 111: {
                    return path.substring(path.lastIndexOf(".") - 1).toLowerCase();
                }
                default: {
                return path.substring(path.lastIndexOf(".") + 1).toLowerCase();
                }
        }
    } else
        return "";

}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
