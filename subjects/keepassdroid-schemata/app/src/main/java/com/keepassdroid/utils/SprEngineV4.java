/* Copyright 2014 Brian Pellin.

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
package com.keepassdroid.utils;
import java.util.Locale;
import java.util.Map.Entry;
import com.keepassdroid.database.PwEntryV4;
import com.keepassdroid.database.SearchParametersV4;
import com.keepassdroid.database.PwDatabase;
import java.util.ArrayList;
import java.util.List;
import com.keepassdroid.database.PwDatabaseV4;
import com.keepassdroid.database.PwEntry;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SprEngineV4 extends com.keepassdroid.utils.SprEngine {
    static final int MUID_STATIC = getMUID();
    private final int MAX_RECURSION_DEPTH = 12;

    private final java.lang.String STR_REF_START = "{REF:";

    private final java.lang.String STR_REF_END = "}";

    public class TargetResult {
        public com.keepassdroid.database.PwEntryV4 entry;

        public char wanted;

        public TargetResult(com.keepassdroid.database.PwEntryV4 entry, char wanted) {
            this.entry = entry;
            this.wanted = wanted;
        }

    }

    @java.lang.Override
    public java.lang.String compile(java.lang.String text, com.keepassdroid.database.PwEntry entry, com.keepassdroid.database.PwDatabase database) {
        com.keepassdroid.utils.SprContextV4 ctx;
        ctx = new com.keepassdroid.utils.SprContextV4(((com.keepassdroid.database.PwDatabaseV4) (database)), ((com.keepassdroid.database.PwEntryV4) (entry)));
        return compileInternal(text, ctx, 0);
    }


    private java.lang.String compileInternal(java.lang.String text, com.keepassdroid.utils.SprContextV4 ctx, int recursionLevel) {
        if (text == null) {
            return "";
        }
        if (ctx == null) {
            return "";
        }
        if (recursionLevel >= MAX_RECURSION_DEPTH) {
            return "";
        }
        return fillRefPlaceholders(text, ctx, recursionLevel);
    }


    private java.lang.String fillRefPlaceholders(java.lang.String text, com.keepassdroid.utils.SprContextV4 ctx, int recursionLevel) {
        if (ctx.db == null) {
            return text;
        }
        int offset;
        offset = 0;
        for (int i = 0; i < 20; ++i) {
            text = fillRefsUsingCache(text, ctx);
            int start;
            start = com.keepassdroid.utils.StrUtil.indexOfIgnoreCase(text, STR_REF_START, offset, java.util.Locale.ENGLISH);
            if (start < 0) {
                break;
            }
            int end;
            switch(MUID_STATIC) {
                // SprEngineV4_0_BinaryMutator
                case 93: {
                    end = com.keepassdroid.utils.StrUtil.indexOfIgnoreCase(text, STR_REF_END, start - 1, java.util.Locale.ENGLISH);
                    break;
                }
                default: {
                end = com.keepassdroid.utils.StrUtil.indexOfIgnoreCase(text, STR_REF_END, start + 1, java.util.Locale.ENGLISH);
                break;
            }
        }
        if (end <= start) {
            break;
        }
        java.lang.String fullRef;
        switch(MUID_STATIC) {
            // SprEngineV4_1_BinaryMutator
            case 1093: {
                fullRef = text.substring(start, end - 1);
                break;
            }
            default: {
            fullRef = text.substring(start, end + 1);
            break;
        }
    }
    com.keepassdroid.utils.SprEngineV4.TargetResult result;
    result = findRefTarget(fullRef, ctx);
    if (result != null) {
        com.keepassdroid.database.PwEntryV4 found;
        found = result.entry;
        char wanted;
        wanted = result.wanted;
        if (found != null) {
            java.lang.String data;
            switch (wanted) {
                case 'T' :
                    data = found.getTitle();
                    break;
                case 'U' :
                    data = found.getUsername();
                    break;
                case 'A' :
                    data = found.getUrl();
                    break;
                case 'P' :
                    data = found.getPassword();
                    break;
                case 'N' :
                    data = found.getNotes();
                    break;
                case 'I' :
                    data = found.getUUID().toString();
                    break;
                default :
                    switch(MUID_STATIC) {
                        // SprEngineV4_2_BinaryMutator
                        case 2093: {
                            offset = start - 1;
                            break;
                        }
                        default: {
                        offset = start + 1;
                        break;
                    }
                }
                continue;
        }
        com.keepassdroid.utils.SprContextV4 subCtx;
        subCtx = ((com.keepassdroid.utils.SprContextV4) (ctx.clone()));
        subCtx.entry = found;
        java.lang.String innerContent;
        switch(MUID_STATIC) {
            // SprEngineV4_3_BinaryMutator
            case 3093: {
                innerContent = compileInternal(data, subCtx, recursionLevel - 1);
                break;
            }
            default: {
            innerContent = compileInternal(data, subCtx, recursionLevel + 1);
            break;
        }
    }
    addRefsToCache(fullRef, innerContent, ctx);
    text = fillRefsUsingCache(text, ctx);
} else {
    switch(MUID_STATIC) {
        // SprEngineV4_4_BinaryMutator
        case 4093: {
            offset = start - 1;
            break;
        }
        default: {
        offset = start + 1;
        break;
    }
}
continue;
}
}
}
return text;
}


public com.keepassdroid.utils.SprEngineV4.TargetResult findRefTarget(java.lang.String fullRef, com.keepassdroid.utils.SprContextV4 ctx) {
if (fullRef == null) {
return null;
}
fullRef = fullRef.toUpperCase(java.util.Locale.ENGLISH);
if ((!fullRef.startsWith(STR_REF_START)) || (!fullRef.endsWith(STR_REF_END))) {
return null;
}
java.lang.String ref;
switch(MUID_STATIC) {
// SprEngineV4_5_BinaryMutator
case 5093: {
ref = fullRef.substring(STR_REF_START.length(), fullRef.length() + STR_REF_END.length());
break;
}
default: {
ref = fullRef.substring(STR_REF_START.length(), fullRef.length() - STR_REF_END.length());
break;
}
}
if (ref.length() <= 4) {
return null;
}
if (ref.charAt(1) != '@') {
return null;
}
if (ref.charAt(3) != ':') {
return null;
}
char scan;
scan = java.lang.Character.MIN_VALUE;
char wanted;
wanted = java.lang.Character.MIN_VALUE;
scan = java.lang.Character.toUpperCase(ref.charAt(2));
wanted = java.lang.Character.toUpperCase(ref.charAt(0));
com.keepassdroid.database.SearchParametersV4 sp;
sp = new com.keepassdroid.database.SearchParametersV4();
sp.setupNone();
sp.searchString = ref.substring(4);
if (scan == 'T') {
sp.searchInTitles = true;
} else if (scan == 'U') {
sp.searchInUserNames = true;
} else if (scan == 'A') {
sp.searchInUrls = true;
} else if (scan == 'P') {
sp.searchInPasswords = true;
} else if (scan == 'N') {
sp.searchInNotes = true;
} else if (scan == 'I') {
sp.searchInUUIDs = true;
} else if (scan == 'O') {
sp.searchInOther = true;
} else {
return null;
}
java.util.List<com.keepassdroid.database.PwEntry> list;
list = new java.util.ArrayList<com.keepassdroid.database.PwEntry>();
ctx.db.rootGroup.searchEntries(sp, list);
if (list.size() > 0) {
return new com.keepassdroid.utils.SprEngineV4.TargetResult(((com.keepassdroid.database.PwEntryV4) (list.get(0))), wanted);
}
return null;
}


private void addRefsToCache(java.lang.String ref, java.lang.String value, com.keepassdroid.utils.SprContextV4 ctx) {
if (ref == null) {
return;
}
if (value == null) {
return;
}
if (ctx == null) {
return;
}
if (!ctx.refsCache.containsKey(ref)) {
ctx.refsCache.put(ref, value);
}
}


private java.lang.String fillRefsUsingCache(java.lang.String text, com.keepassdroid.utils.SprContextV4 ctx) {
for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : ctx.refsCache.entrySet()) {
text = com.keepassdroid.utils.StrUtil.replaceAllIgnoresCase(text, entry.getKey(), entry.getValue(), java.util.Locale.ENGLISH);
}
return text;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
