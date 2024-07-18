/* Copyright 2009-2017 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.database;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import android.annotation.SuppressLint;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwDatabaseV4XML {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String ElemDocNode = "KeePassFile";

    public static final java.lang.String ElemMeta = "Meta";

    public static final java.lang.String ElemRoot = "Root";

    public static final java.lang.String ElemGroup = "Group";

    public static final java.lang.String ElemEntry = "Entry";

    public static final java.lang.String ElemGenerator = "Generator";

    public static final java.lang.String ElemHeaderHash = "HeaderHash";

    public static final java.lang.String ElemSettingsChanged = "SettingsChanged";

    public static final java.lang.String ElemDbName = "DatabaseName";

    public static final java.lang.String ElemDbNameChanged = "DatabaseNameChanged";

    public static final java.lang.String ElemDbDesc = "DatabaseDescription";

    public static final java.lang.String ElemDbDescChanged = "DatabaseDescriptionChanged";

    public static final java.lang.String ElemDbDefaultUser = "DefaultUserName";

    public static final java.lang.String ElemDbDefaultUserChanged = "DefaultUserNameChanged";

    public static final java.lang.String ElemDbMntncHistoryDays = "MaintenanceHistoryDays";

    public static final java.lang.String ElemDbColor = "Color";

    public static final java.lang.String ElemDbKeyChanged = "MasterKeyChanged";

    public static final java.lang.String ElemDbKeyChangeRec = "MasterKeyChangeRec";

    public static final java.lang.String ElemDbKeyChangeForce = "MasterKeyChangeForce";

    public static final java.lang.String ElemDbKeyChangeForceOnce = "MasterKeyChangeForceOnce";

    public static final java.lang.String ElemRecycleBinEnabled = "RecycleBinEnabled";

    public static final java.lang.String ElemRecycleBinUuid = "RecycleBinUUID";

    public static final java.lang.String ElemRecycleBinChanged = "RecycleBinChanged";

    public static final java.lang.String ElemEntryTemplatesGroup = "EntryTemplatesGroup";

    public static final java.lang.String ElemEntryTemplatesGroupChanged = "EntryTemplatesGroupChanged";

    public static final java.lang.String ElemHistoryMaxItems = "HistoryMaxItems";

    public static final java.lang.String ElemHistoryMaxSize = "HistoryMaxSize";

    public static final java.lang.String ElemLastSelectedGroup = "LastSelectedGroup";

    public static final java.lang.String ElemLastTopVisibleGroup = "LastTopVisibleGroup";

    public static final java.lang.String ElemPreviousParentGroup = "PreviousParentGroup";

    public static final java.lang.String ElemMemoryProt = "MemoryProtection";

    public static final java.lang.String ElemProtTitle = "ProtectTitle";

    public static final java.lang.String ElemProtUserName = "ProtectUserName";

    public static final java.lang.String ElemProtPassword = "ProtectPassword";

    public static final java.lang.String ElemProtURL = "ProtectURL";

    public static final java.lang.String ElemProtNotes = "ProtectNotes";

    public static final java.lang.String ElemProtAutoHide = "AutoEnableVisualHiding";

    public static final java.lang.String ElemCustomIcons = "CustomIcons";

    public static final java.lang.String ElemCustomIconItem = "Icon";

    public static final java.lang.String ElemCustomIconItemID = "UUID";

    public static final java.lang.String ElemCustomIconItemData = "Data";

    public static final java.lang.String ElemAutoType = "AutoType";

    public static final java.lang.String ElemHistory = "History";

    public static final java.lang.String ElemName = "Name";

    public static final java.lang.String ElemNotes = "Notes";

    public static final java.lang.String ElemUuid = "UUID";

    public static final java.lang.String ElemIcon = "IconID";

    public static final java.lang.String ElemCustomIconID = "CustomIconUUID";

    public static final java.lang.String ElemFgColor = "ForegroundColor";

    public static final java.lang.String ElemBgColor = "BackgroundColor";

    public static final java.lang.String ElemOverrideUrl = "OverrideURL";

    public static final java.lang.String ElemQualityCheck = "QualityCheck";

    public static final java.lang.String ElemTimes = "Times";

    public static final java.lang.String ElemTags = "Tags";

    public static final java.lang.String ElemCreationTime = "CreationTime";

    public static final java.lang.String ElemLastModTime = "LastModificationTime";

    public static final java.lang.String ElemLastAccessTime = "LastAccessTime";

    public static final java.lang.String ElemExpiryTime = "ExpiryTime";

    public static final java.lang.String ElemExpires = "Expires";

    public static final java.lang.String ElemUsageCount = "UsageCount";

    public static final java.lang.String ElemLocationChanged = "LocationChanged";

    public static final java.lang.String ElemGroupDefaultAutoTypeSeq = "DefaultAutoTypeSequence";

    public static final java.lang.String ElemEnableAutoType = "EnableAutoType";

    public static final java.lang.String ElemEnableSearching = "EnableSearching";

    public static final java.lang.String ElemString = "String";

    public static final java.lang.String ElemBinary = "Binary";

    public static final java.lang.String ElemKey = "Key";

    public static final java.lang.String ElemValue = "Value";

    public static final java.lang.String ElemAutoTypeEnabled = "Enabled";

    public static final java.lang.String ElemAutoTypeObfuscation = "DataTransferObfuscation";

    public static final java.lang.String ElemAutoTypeDefaultSeq = "DefaultSequence";

    public static final java.lang.String ElemAutoTypeItem = "Association";

    public static final java.lang.String ElemWindow = "Window";

    public static final java.lang.String ElemKeystrokeSequence = "KeystrokeSequence";

    public static final java.lang.String ElemBinaries = "Binaries";

    public static final java.lang.String AttrId = "ID";

    public static final java.lang.String AttrRef = "Ref";

    public static final java.lang.String AttrProtected = "Protected";

    public static final java.lang.String AttrCompressed = "Compressed";

    public static final java.lang.String ElemIsExpanded = "IsExpanded";

    public static final java.lang.String ElemLastTopVisibleEntry = "LastTopVisibleEntry";

    public static final java.lang.String ElemDeletedObjects = "DeletedObjects";

    public static final java.lang.String ElemDeletedObject = "DeletedObject";

    public static final java.lang.String ElemDeletionTime = "DeletionTime";

    public static final java.lang.String ValFalse = "False";

    public static final java.lang.String ValTrue = "True";

    public static final java.lang.String ElemCustomData = "CustomData";

    public static final java.lang.String ElemStringDictExItem = "Item";

    public static final java.lang.ThreadLocal<java.text.SimpleDateFormat> dateFormatter = new java.lang.ThreadLocal<java.text.SimpleDateFormat>() {
        @java.lang.Override
        protected java.text.SimpleDateFormat initialValue() {
            java.text.SimpleDateFormat dateFormat;
            dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            return dateFormat;
        }

    };

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
