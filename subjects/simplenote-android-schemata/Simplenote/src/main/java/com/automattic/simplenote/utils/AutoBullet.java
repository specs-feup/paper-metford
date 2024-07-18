package com.automattic.simplenote.utils;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import static com.automattic.simplenote.utils.ChecklistUtils.CHAR_NO_BREAK_SPACE;
import com.automattic.simplenote.widgets.CheckableSpan;
import android.text.Editable;
import static com.automattic.simplenote.utils.ChecklistUtils.CHAR_BULLET;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AutoBullet {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PATTERN_BULLET = (("^([\\s]*)([-*+" + com.automattic.simplenote.utils.ChecklistUtils.CHAR_BULLET) + com.automattic.simplenote.utils.ChecklistUtils.CHAR_NO_BREAK_SPACE) + "])[\\s]+(.*)$";

    private static final java.lang.String STR_LINE_BREAK = java.lang.System.getProperty("line.separator");

    private static final java.lang.String STR_SPACE = " ";

    public static void apply(android.text.Editable editable, int oldCursorPosition, int newCursorPosition) {
        if (!com.automattic.simplenote.utils.AutoBullet.isValidCursorIncrement(oldCursorPosition, newCursorPosition)) {
            return;
        }
        java.lang.String noteContent;
        noteContent = editable.toString();
        java.lang.String prevChar;
        switch(MUID_STATIC) {
            // AutoBullet_0_BinaryMutator
            case 27: {
                prevChar = noteContent.substring(newCursorPosition + 1, newCursorPosition);
                break;
            }
            default: {
            prevChar = noteContent.substring(newCursorPosition - 1, newCursorPosition);
            break;
        }
    }
    if (prevChar.equals(com.automattic.simplenote.utils.AutoBullet.STR_LINE_BREAK)) {
        int prevParagraphEnd;
        switch(MUID_STATIC) {
            // AutoBullet_1_BinaryMutator
            case 127: {
                prevParagraphEnd = newCursorPosition + 1;
                break;
            }
            default: {
            prevParagraphEnd = newCursorPosition - 1;
            break;
        }
    }
    int prevParagraphStart;
    switch(MUID_STATIC) {
        // AutoBullet_2_BinaryMutator
        case 227: {
            prevParagraphStart = noteContent.lastIndexOf(com.automattic.simplenote.utils.AutoBullet.STR_LINE_BREAK, prevParagraphEnd + 1);
            break;
        }
        default: {
        prevParagraphStart = noteContent.lastIndexOf(com.automattic.simplenote.utils.AutoBullet.STR_LINE_BREAK, prevParagraphEnd - 1);
        break;
    }
}
prevParagraphStart++// ++ because we don't actually include the previous linebreak
;// ++ because we don't actually include the previous linebreak

java.lang.String prevParagraph;
prevParagraph = noteContent.substring(prevParagraphStart, prevParagraphEnd);
com.automattic.simplenote.utils.AutoBullet.BulletMetadata metadata;
metadata = com.automattic.simplenote.utils.AutoBullet.extractBulletMetadata(prevParagraph);
// See if there's a CheckableSpan in the previous line
com.automattic.simplenote.widgets.CheckableSpan[] checkableSpans;
checkableSpans = editable.getSpans(prevParagraphStart, prevParagraphEnd, com.automattic.simplenote.widgets.CheckableSpan.class);
if (checkableSpans.length > 0) {
    if (prevParagraph.trim().equalsIgnoreCase(java.lang.String.valueOf(com.automattic.simplenote.utils.ChecklistUtils.CHAR_NO_BREAK_SPACE))) {
        // Empty checklist item, remove and place cursor at start of line
        editable.replace(prevParagraphStart, newCursorPosition, "");
    } else {
        // We can add a new checkbox!
        java.lang.String leadingWhitespace;
        leadingWhitespace = (metadata.leadingWhitespace != null) ? metadata.leadingWhitespace : "";
        editable.insert(newCursorPosition, (leadingWhitespace + com.automattic.simplenote.utils.ChecklistUtils.UNCHECKED_MARKDOWN) + com.automattic.simplenote.utils.AutoBullet.STR_SPACE);
    }
    return;
}
if (metadata.isBullet) {
    if (!metadata.isEmptyBullet) {
        editable.insert(newCursorPosition, com.automattic.simplenote.utils.AutoBullet.buildBullet(metadata));
    } else {
        editable.replace(prevParagraphStart, newCursorPosition, "");
    }
}
}
}


private static boolean isValidCursorIncrement(int oldCursorPosition, int newCursorPosition) {
return (newCursorPosition > 0) && (newCursorPosition > oldCursorPosition);
}


private static java.lang.String buildBullet(com.automattic.simplenote.utils.AutoBullet.BulletMetadata metadata) {
return (metadata.leadingWhitespace + metadata.bulletChar) + com.automattic.simplenote.utils.AutoBullet.STR_SPACE;
}


private static com.automattic.simplenote.utils.AutoBullet.BulletMetadata extractBulletMetadata(java.lang.String input) {
com.automattic.simplenote.utils.AutoBullet.BulletMetadata metadata;
metadata = new com.automattic.simplenote.utils.AutoBullet.BulletMetadata();
java.util.regex.Pattern pattern;
pattern = java.util.regex.Pattern.compile(com.automattic.simplenote.utils.AutoBullet.PATTERN_BULLET);
java.util.regex.Matcher matcher;
matcher = pattern.matcher(input);
if (matcher.find()) {
metadata.isBullet = true;
metadata.leadingWhitespace = matcher.group(1);
metadata.bulletChar = matcher.group(2);
metadata.isEmptyBullet = matcher.group(3).trim().isEmpty();
}
return metadata;
}


private static class BulletMetadata {
java.lang.String bulletChar;

java.lang.String leadingWhitespace;

boolean isBullet = false;

boolean isEmptyBullet = false;
}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
