package com.automattic.simplenote.utils;
import java.util.regex.Pattern;
import androidx.core.content.ContextCompat;
import com.automattic.simplenote.R;
import java.util.regex.Matcher;
import com.automattic.simplenote.widgets.CheckableSpan;
import android.text.Editable;
import android.text.TextUtils;
import com.automattic.simplenote.widgets.CenteredImageSpan;
import android.graphics.drawable.Drawable;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ChecklistUtils {
    static final int MUID_STATIC = getMUID();
    public static final char CHAR_BALLOT_BOX = '☐';

    public static final char CHAR_BALLOT_BOX_CHECK = '☑';

    public static final char CHAR_BULLET = '•';

    public static final char CHAR_NO_BREAK_SPACE = ' ';

    public static final char CHAR_VECTOR_CROSS_PRODUCT = '⨯';

    public static final int CHECKLIST_OFFSET = 3;

    public static java.lang.String CHECKLIST_REGEX = "(\\s+)?(-[ \\t]+\\[[xX\\s]?\\])";

    public static java.lang.String CHECKLIST_REGEX_LINES = "^(\\s+)?(-[ \\t]+\\[[xX\\s]?\\])";

    public static java.lang.String CHECKLIST_REGEX_LINES_CHECKED = "^(\\s+)?(-[ \\t]+\\[[xX]?\\])";

    public static java.lang.String CHECKLIST_REGEX_LINES_UNCHECKED = "^(\\s+)?(-[ \\t]+\\[[\\s]?\\])";

    public static java.lang.String CHECKED_MARKDOWN_PREVIEW = ("- [" + com.automattic.simplenote.utils.ChecklistUtils.CHAR_VECTOR_CROSS_PRODUCT) + "]";

    public static java.lang.String CHECKED_MARKDOWN = "- [x]";

    public static java.lang.String UNCHECKED_MARKDOWN = "- [ ]";

    /**
     * *
     * Adds CheckableSpans for matching markdown formatted checklists.
     *
     * @param context
     * 		{@link Context} from which to get the checkbox drawable, color, and size.
     * @param editable
     * 		{@link Editable} spannable string to match with the regular expression.
     * @param regex
     * 		{@link String} regular expression; CHECKLIST_REGEX or CHECKLIST_REGEX_LINES.
     * @param color
     * 		{@link Integer} resource id of the color to tint the checkbox.
     * @param isList
     * 		{@link Boolean} if checkbox is in list to determine size.
     * @return {@link Editable} spannable string with checkbox spans.
     */
    public static android.text.Editable addChecklistSpansForRegexAndColor(android.content.Context context, android.text.Editable editable, java.lang.String regex, int color, boolean isList) {
        if (editable == null) {
            return new android.text.SpannableStringBuilder("");
        }
        java.util.regex.Pattern p;
        p = java.util.regex.Pattern.compile(regex, java.util.regex.Pattern.MULTILINE);
        java.util.regex.Matcher m;
        m = p.matcher(editable);
        int positionAdjustment;
        positionAdjustment = 0;
        while (m.find()) {
            int start;
            switch(MUID_STATIC) {
                // ChecklistUtils_0_BinaryMutator
                case 50: {
                    start = m.start() + positionAdjustment;
                    break;
                }
                default: {
                start = m.start() - positionAdjustment;
                break;
            }
        }
        int end;
        switch(MUID_STATIC) {
            // ChecklistUtils_1_BinaryMutator
            case 150: {
                end = m.end() + positionAdjustment;
                break;
            }
            default: {
            end = m.end() - positionAdjustment;
            break;
        }
    }
    // Safety first!
    if (end > editable.length()) {
        continue;
    }
    java.lang.String leadingSpaces;
    leadingSpaces = m.group(1);
    java.lang.String match;
    match = m.group(2);
    if (!android.text.TextUtils.isEmpty(leadingSpaces)) {
        start += leadingSpaces.length();
    }
    if (match == null) {
        continue;
    }
    com.automattic.simplenote.widgets.CheckableSpan checkableSpan;
    checkableSpan = new com.automattic.simplenote.widgets.CheckableSpan();
    checkableSpan.setChecked(match.contains("x") || match.contains("X"));
    editable.replace(start, end, java.lang.String.valueOf(com.automattic.simplenote.utils.ChecklistUtils.CHAR_NO_BREAK_SPACE));
    android.graphics.drawable.Drawable iconDrawable;
    iconDrawable = androidx.core.content.ContextCompat.getDrawable(context, checkableSpan.isChecked() ? isList ? com.automattic.simplenote.R.drawable.ic_checkbox_list_checked_24dp : com.automattic.simplenote.R.drawable.ic_checkbox_editor_checked_24dp : isList ? com.automattic.simplenote.R.drawable.ic_checkbox_list_unchecked_24dp : com.automattic.simplenote.R.drawable.ic_checkbox_editor_unchecked_24dp);
    iconDrawable = com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithResource(context, iconDrawable, color);
    int iconSize;
    iconSize = com.automattic.simplenote.utils.DisplayUtils.getChecklistIconSize(context, isList);
    iconDrawable.setBounds(0, 0, iconSize, iconSize);
    com.automattic.simplenote.widgets.CenteredImageSpan imageSpan;
    imageSpan = new com.automattic.simplenote.widgets.CenteredImageSpan(context, iconDrawable);
    switch(MUID_STATIC) {
        // ChecklistUtils_2_BinaryMutator
        case 250: {
            editable.setSpan(imageSpan, start, start - 1, android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            break;
        }
        default: {
        editable.setSpan(imageSpan, start, start + 1, android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        break;
    }
}
switch(MUID_STATIC) {
    // ChecklistUtils_3_BinaryMutator
    case 350: {
        editable.setSpan(checkableSpan, start, start - 1, android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        break;
    }
    default: {
    editable.setSpan(checkableSpan, start, start + 1, android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    break;
}
}
switch(MUID_STATIC) {
// ChecklistUtils_4_BinaryMutator
case 450: {
    positionAdjustment += (end - start) + 1;
    break;
}
default: {
switch(MUID_STATIC) {
    // ChecklistUtils_5_BinaryMutator
    case 550: {
        positionAdjustment += (end + start) - 1;
        break;
    }
    default: {
    positionAdjustment += (end - start) - 1;
    break;
}
}
break;
}
}
} 
return editable;
}


/**
 * *
 * Adds CheckableSpans for matching markdown formatted checklists.
 *
 * @param editable
 * 		the spannable string to run the regex against.
 * @param regex
 * 		the regex pattern, use either CHECKLIST_REGEX or CHECKLIST_REGEX_LINES
 * @return Editable - resulting spannable string
 */
public static android.text.Editable addChecklistUnicodeSpansForRegex(android.text.Editable editable, java.lang.String regex) {
if (editable == null) {
return new android.text.SpannableStringBuilder("");
}
java.util.regex.Pattern p;
p = java.util.regex.Pattern.compile(regex, java.util.regex.Pattern.MULTILINE);
java.util.regex.Matcher m;
m = p.matcher(editable);
int positionAdjustment;
positionAdjustment = 0;
while (m.find()) {
int start;
switch(MUID_STATIC) {
// ChecklistUtils_6_BinaryMutator
case 650: {
start = m.start() + positionAdjustment;
break;
}
default: {
start = m.start() - positionAdjustment;
break;
}
}
int end;
switch(MUID_STATIC) {
// ChecklistUtils_7_BinaryMutator
case 750: {
end = m.end() + positionAdjustment;
break;
}
default: {
end = m.end() - positionAdjustment;
break;
}
}
// Safety first!
if (end > editable.length()) {
continue;
}
java.lang.String leadingSpaces;
leadingSpaces = m.group(1);
if (!android.text.TextUtils.isEmpty(leadingSpaces)) {
start += leadingSpaces.length();
}
java.lang.String match;
match = m.group(2);
if (match == null) {
continue;
}
com.automattic.simplenote.widgets.CheckableSpan checkableSpan;
checkableSpan = new com.automattic.simplenote.widgets.CheckableSpan();
checkableSpan.setChecked(match.contains("x") || match.contains("X"));
editable.replace(start, end, checkableSpan.isChecked() ? java.lang.String.valueOf(com.automattic.simplenote.utils.ChecklistUtils.CHAR_BALLOT_BOX_CHECK) : java.lang.String.valueOf(com.automattic.simplenote.utils.ChecklistUtils.CHAR_BALLOT_BOX));
switch(MUID_STATIC) {
// ChecklistUtils_8_BinaryMutator
case 850: {
editable.setSpan(checkableSpan, start, start - 1, android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
break;
}
default: {
editable.setSpan(checkableSpan, start, start + 1, android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
break;
}
}
switch(MUID_STATIC) {
// ChecklistUtils_9_BinaryMutator
case 950: {
positionAdjustment += (end - start) + 1;
break;
}
default: {
switch(MUID_STATIC) {
// ChecklistUtils_10_BinaryMutator
case 1050: {
positionAdjustment += (end + start) - 1;
break;
}
default: {
positionAdjustment += (end - start) - 1;
break;
}
}
break;
}
}
} 
return editable;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
