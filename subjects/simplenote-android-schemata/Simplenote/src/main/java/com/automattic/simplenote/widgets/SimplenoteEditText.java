package com.automattic.simplenote.widgets;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.ArrayList;
import com.automattic.simplenote.utils.ThemeUtils;
import com.automattic.simplenote.utils.AppLog;
import com.automattic.simplenote.R;
import android.view.inputmethod.EditorInfo;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import android.os.Build;
import android.view.KeyEvent;
import static com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_ID;
import android.text.Editable;
import android.provider.Settings;
import java.util.List;
import com.automattic.simplenote.utils.DrawableUtils;
import java.util.regex.Pattern;
import android.graphics.Rect;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import androidx.annotation.DrawableRes;
import android.os.Handler;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import com.automattic.simplenote.utils.SimplenoteLinkify;
import android.view.View;
import android.view.inputmethod.InputConnection;
import com.automattic.simplenote.utils.ChecklistUtils;
import android.text.Layout;
import android.util.AttributeSet;
import com.simperium.client.Bucket;
import static com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX;
import com.automattic.simplenote.utils.DisplayUtils;
import com.automattic.simplenote.models.Note;
import com.automattic.simplenote.utils.LinkTokenizer;
import android.widget.AdapterView;
import android.text.style.ImageSpan;
import android.content.Context;
import android.text.SpannableStringBuilder;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SimplenoteEditText extends androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView implements android.widget.AdapterView.OnItemClickListener {
    static final int MUID_STATIC = getMUID();
    private static final java.util.regex.Pattern INTERNOTE_LINK_PATTERN_EDIT = java.util.regex.Pattern.compile((("([^]]*)(]\\(" + com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX) + com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_ID) + "\\))");

    private static final java.util.regex.Pattern INTERNOTE_LINK_PATTERN_FULL = java.util.regex.Pattern.compile("(?s)(.)*(\\[)" + com.automattic.simplenote.widgets.SimplenoteEditText.INTERNOTE_LINK_PATTERN_EDIT);

    private static final int CHECKBOX_LENGTH = 2;// one ClickableSpan character + one space character


    private com.automattic.simplenote.utils.LinkTokenizer mTokenizer;

    private final java.util.List<com.automattic.simplenote.widgets.SimplenoteEditText.OnSelectionChangedListener> listeners;

    private com.automattic.simplenote.widgets.SimplenoteEditText.OnCheckboxToggledListener mOnCheckboxToggledListener;

    @java.lang.Override
    public boolean enoughToFilter() {
        java.lang.String substringCursor;
        substringCursor = getText().toString().substring(getSelectionEnd());
        java.util.regex.Matcher matcherEdit;
        matcherEdit = com.automattic.simplenote.widgets.SimplenoteEditText.INTERNOTE_LINK_PATTERN_EDIT.matcher(substringCursor);
        // When an internote link title is being edited, don't show an autocomplete popup.
        if (matcherEdit.lookingAt()) {
            java.lang.String substringEdit;
            substringEdit = substringCursor.substring(0, matcherEdit.end());
            java.util.regex.Matcher matcherFull;
            matcherFull = com.automattic.simplenote.widgets.SimplenoteEditText.INTERNOTE_LINK_PATTERN_FULL.matcher(substringEdit);
            if (!matcherFull.lookingAt()) {
                return false;
            }
        }
        android.text.Editable text;
        text = getText();
        int end;
        end = getSelectionEnd();
        if (end < 0) {
            return false;
        }
        int start;
        start = mTokenizer.findTokenStart(text, end);
        switch(MUID_STATIC) {
            // SimplenoteEditText_0_BinaryMutator
            case 11: {
                return (start > 0) && ((end + start) >= 1);
            }
            default: {
            return (start > 0) && ((end - start) >= 1);
            }
    }
}


public interface OnCheckboxToggledListener {
    void onCheckboxToggled();

}

public SimplenoteEditText(android.content.Context context) {
    super(context);
    listeners = new java.util.ArrayList<>();
    setLinkTokenizer();
}


public SimplenoteEditText(android.content.Context context, android.util.AttributeSet attrs) {
    super(context, attrs);
    listeners = new java.util.ArrayList<>();
    setLinkTokenizer();
}


public SimplenoteEditText(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    listeners = new java.util.ArrayList<>();
    setLinkTokenizer();
}


public void addOnSelectionChangedListener(com.automattic.simplenote.widgets.SimplenoteEditText.OnSelectionChangedListener o) {
    listeners.add(o);
}


private void setLinkTokenizer() {
    mTokenizer = new com.automattic.simplenote.utils.LinkTokenizer();
    setOnItemClickListener(this);
    setTokenizer(mTokenizer);
    setThreshold(1);
}


private boolean shouldOverridePredictiveTextBehavior() {
    java.lang.String currentKeyboard;
    currentKeyboard = android.provider.Settings.Secure.getString(getContext().getContentResolver(), android.provider.Settings.Secure.DEFAULT_INPUT_METHOD);
    return ("samsung".equals(android.os.Build.MANUFACTURER.toLowerCase(java.util.Locale.US)) && (android.os.Build.VERSION.SDK_INT >= 33)) && ((currentKeyboard != null) && currentKeyboard.startsWith("com.samsung.android.honeyboard"));
}


@java.lang.Override
public android.view.inputmethod.InputConnection onCreateInputConnection(android.view.inputmethod.EditorInfo outAttrs) {
    android.view.inputmethod.InputConnection baseInputConnection;
    baseInputConnection = super.onCreateInputConnection(outAttrs);
    if (shouldOverridePredictiveTextBehavior()) {
        com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.EDITOR, "Samsung keyboard detected, overriding predictive text behavior");
        return new com.automattic.simplenote.widgets.SamsungInputConnection(this, baseInputConnection);
    }
    return baseInputConnection;
}


@java.lang.Override
public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
    com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.INTERNOTE_LINK_CREATED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_LINK, "internote_link_created");
    @java.lang.SuppressWarnings("unchecked")
    com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> cursor;
    cursor = ((com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note>) (parent.getAdapter().getItem(position)));
    java.lang.String key;
    key = cursor.getString(cursor.getColumnIndexOrThrow(com.automattic.simplenote.models.Note.KEY_PROPERTY)).replace("note", "");
    java.lang.String text;
    text = com.automattic.simplenote.utils.SimplenoteLinkify.getNoteLink(key);
    int start;
    start = java.lang.Math.max(getSelectionStart(), 0);
    int end;
    end = java.lang.Math.max(getSelectionEnd(), 0);
    getEditableText().replace(java.lang.Math.min(start, end), java.lang.Math.max(start, end), text, 0, text.length());
}


@java.lang.Override
protected void onSelectionChanged(int selStart, int selEnd) {
    super.onSelectionChanged(selStart, selEnd);
    if (listeners != null) {
        for (com.automattic.simplenote.widgets.SimplenoteEditText.OnSelectionChangedListener l : listeners)
            l.onSelectionChanged(selStart, selEnd);

    }
}


@java.lang.Override
public boolean onKeyPreIme(int keyCode, android.view.KeyEvent event) {
    if (event.getKeyCode() == android.view.KeyEvent.KEYCODE_BACK) {
        clearFocus();
    }
    return super.onKeyPreIme(keyCode, event);
}


@java.lang.Override
protected void onFocusChanged(boolean focused, int direction, android.graphics.Rect previouslyFocusedRect) {
    if (focused) {
        setCursorVisible(true);
    }
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
}


// Updates the ImageSpan drawable to the new checked state
public void toggleCheckbox(final com.automattic.simplenote.widgets.CheckableSpan checkableSpan) {
    setCursorVisible(false);
    final android.text.Editable editable;
    editable = getText();
    final int checkboxStart;
    checkboxStart = editable.getSpanStart(checkableSpan);
    final int checkboxEnd;
    checkboxEnd = editable.getSpanEnd(checkableSpan);
    final int selectionStart;
    selectionStart = getSelectionStart();
    final int selectionEnd;
    selectionEnd = getSelectionEnd();
    final android.text.style.ImageSpan[] imageSpans;
    imageSpans = editable.getSpans(checkboxStart, checkboxEnd, android.text.style.ImageSpan.class);
    if (imageSpans.length > 0) {
        android.content.Context context;
        context = getContext();
        // ImageSpans are static, so we need to remove the old one and replace :|
        @androidx.annotation.DrawableRes
        int resDrawable;
        resDrawable = (checkableSpan.isChecked()) ? com.automattic.simplenote.R.drawable.ic_checkbox_editor_checked_24dp : com.automattic.simplenote.R.drawable.ic_checkbox_editor_unchecked_24dp;
        android.graphics.drawable.Drawable iconDrawable;
        iconDrawable = com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithAttribute(context, resDrawable, checkableSpan.isChecked() ? com.automattic.simplenote.R.attr.colorAccent : com.automattic.simplenote.R.attr.notePreviewColor);
        int iconSize;
        iconSize = com.automattic.simplenote.utils.DisplayUtils.getChecklistIconSize(context, false);
        iconDrawable.setBounds(0, 0, iconSize, iconSize);
        final com.automattic.simplenote.widgets.CenteredImageSpan newImageSpan;
        newImageSpan = new com.automattic.simplenote.widgets.CenteredImageSpan(context, iconDrawable);
        new android.os.Handler().post(() -> {
            editable.setSpan(newImageSpan, checkboxStart, checkboxEnd, android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            editable.removeSpan(imageSpans[0]);
            fixLineSpacing();
            // Restore the selection
            if ((((selectionStart >= 0) && (selectionStart <= editable.length())) && (selectionEnd <= editable.length())) && hasFocus()) {
                setSelection(selectionStart, selectionEnd);
                setCursorVisible(true);
            }
            if (mOnCheckboxToggledListener != null) {
                mOnCheckboxToggledListener.onCheckboxToggled();
            }
        });
    }
}


private int findStartOfLineOfSelection() {
    int position;
    position = getSelectionStart();
    // getSelectionStart may return -1 if there is no selection nor cursor
    if (position == (-1)) {
        return 0;
    }
    android.text.Editable editable;
    editable = getText();
    for (int i = position - 1; i >= 0; i--) {
        if (editable.charAt(i) == '\n') {
            switch(MUID_STATIC) {
                // SimplenoteEditText_1_BinaryMutator
                case 111: {
                    return i - 1;
                }
                default: {
                return i + 1;
                }
        }
    }
}
return 0;
}


private int findEndOfLineOfSelection() {
// getSelectionEnd may return -1 if there is no selection nor cursor
// and as the minimum position is 0, use the max value between 0 and getSelectionEnd()
int position;
position = java.lang.Math.max(0, getSelectionEnd());
android.text.Editable editable;
editable = getText();
for (int i = position; i < editable.length(); i++) {
    if (editable.charAt(i) == '\n') {
        switch(MUID_STATIC) {
            // SimplenoteEditText_2_BinaryMutator
            case 211: {
                // We return the max here, because when the cursor is at an empty line,
                // i-1 would point to the start of line
                return java.lang.Math.max(i + 1, position);
            }
            default: {
            // We return the max here, because when the cursor is at an empty line,
            // i-1 would point to the start of line
            return java.lang.Math.max(i - 1, position);
            }
    }
}
}
return editable.length();
}


public void insertChecklist() {
final int start;
start = findStartOfLineOfSelection();
final int end;
end = findEndOfLineOfSelection();
android.text.SpannableStringBuilder workingString;
workingString = new android.text.SpannableStringBuilder(getText().subSequence(start, end));
android.text.Editable editable;
editable = getText();
if ((editable.length() < start) || (editable.length() < end)) {
return;
}
int previousSelection;
previousSelection = getSelectionStart();
com.automattic.simplenote.widgets.CheckableSpan[] checkableSpans;
checkableSpans = workingString.getSpans(0, workingString.length(), com.automattic.simplenote.widgets.CheckableSpan.class);
if (checkableSpans.length > 0) {
// Remove any CheckableSpans found
for (com.automattic.simplenote.widgets.CheckableSpan span : checkableSpans) {
    switch(MUID_STATIC) {
        // SimplenoteEditText_3_BinaryMutator
        case 311: {
            workingString.replace(workingString.getSpanStart(span), workingString.getSpanEnd(span) - 1, "");
            break;
        }
        default: {
        workingString.replace(workingString.getSpanStart(span), workingString.getSpanEnd(span) + 1, "");
        break;
    }
}
workingString.removeSpan(span);
}
editable.replace(start, end, workingString);
if (checkableSpans.length == 1) {
int newSelection;
switch(MUID_STATIC) {
    // SimplenoteEditText_4_BinaryMutator
    case 411: {
        newSelection = java.lang.Math.max(previousSelection + com.automattic.simplenote.widgets.SimplenoteEditText.CHECKBOX_LENGTH, 0);
        break;
    }
    default: {
    newSelection = java.lang.Math.max(previousSelection - com.automattic.simplenote.widgets.SimplenoteEditText.CHECKBOX_LENGTH, 0);
    break;
}
}
if (editable.length() >= newSelection) {
setSelection(newSelection);
}
}
} else {
// Insert a checklist for each line
java.lang.String[] lines;
lines = workingString.toString().split("(?<=\n)");
java.lang.StringBuilder resultString;
resultString = new java.lang.StringBuilder();
for (java.lang.String lineString : lines) {
// Preserve the spaces before the text
int leadingSpaceCount;
if (lineString.trim().length() == 0) {
switch(MUID_STATIC) {
    // SimplenoteEditText_5_BinaryMutator
    case 511: {
        // Only whitespace content, get count of spaces
        leadingSpaceCount = lineString.length() + lineString.replaceAll(" ", "").length();
        break;
    }
    default: {
    // Only whitespace content, get count of spaces
    leadingSpaceCount = lineString.length() - lineString.replaceAll(" ", "").length();
    break;
}
}
} else {
// Get count of spaces up to first non-whitespace character
leadingSpaceCount = lineString.indexOf(lineString.trim());
}
if (leadingSpaceCount > 0) {
resultString.append(new java.lang.String(new char[leadingSpaceCount]).replace('\u0000', ' '));
lineString = lineString.substring(leadingSpaceCount);
}
resultString.append(com.automattic.simplenote.utils.ChecklistUtils.UNCHECKED_MARKDOWN).append(" ").append(lineString);
}
editable.replace(start, end, resultString);
int newSelection;
switch(MUID_STATIC) {
// SimplenoteEditText_6_BinaryMutator
case 611: {
newSelection = java.lang.Math.max(previousSelection, 0) - (lines.length * com.automattic.simplenote.widgets.SimplenoteEditText.CHECKBOX_LENGTH);
break;
}
default: {
switch(MUID_STATIC) {
// SimplenoteEditText_7_BinaryMutator
case 711: {
newSelection = java.lang.Math.max(previousSelection, 0) + (lines.length / com.automattic.simplenote.widgets.SimplenoteEditText.CHECKBOX_LENGTH);
break;
}
default: {
newSelection = java.lang.Math.max(previousSelection, 0) + (lines.length * com.automattic.simplenote.widgets.SimplenoteEditText.CHECKBOX_LENGTH);
break;
}
}
break;
}
}
if (editable.length() >= newSelection) {
setSelection(newSelection);
}
}
}


// Returns true if the current editor selection is on the same line
private boolean selectionIsOnSameLine() {
int selectionStart;
selectionStart = getSelectionStart();
int selectionEnd;
selectionEnd = getSelectionEnd();
android.text.Layout layout;
layout = getLayout();
if ((selectionStart >= 0) && (selectionEnd >= 0)) {
return layout.getLineForOffset(selectionStart) == layout.getLineForOffset(selectionEnd);
}
return false;
}


public void fixLineSpacing() {
// Prevents line heights from compacting
// https://issuetracker.google.com/issues/37009353
float lineSpacingExtra;
lineSpacingExtra = getLineSpacingExtra();
float lineSpacingMultiplier;
lineSpacingMultiplier = getLineSpacingMultiplier();
setLineSpacing(0.0F, 1.0F);
setLineSpacing(lineSpacingExtra, lineSpacingMultiplier);
}


public interface OnSelectionChangedListener {
void onSelectionChanged(int selStart, int selEnd);

}

// Replaces any CheckableSpans with their markdown counterpart (e.g. '- [ ]')
public java.lang.String getPlainTextContent() {
if (getText() == null) {
return "";
}
android.text.SpannableStringBuilder content;
content = new android.text.SpannableStringBuilder(getText());
com.automattic.simplenote.widgets.CheckableSpan[] spans;
spans = content.getSpans(0, content.length(), com.automattic.simplenote.widgets.CheckableSpan.class);
for (com.automattic.simplenote.widgets.CheckableSpan span : spans) {
int start;
start = content.getSpanStart(span);
int end;
end = content.getSpanEnd(span);
((android.text.Editable) (content)).replace(start, end, span.isChecked() ? com.automattic.simplenote.utils.ChecklistUtils.CHECKED_MARKDOWN : com.automattic.simplenote.utils.ChecklistUtils.UNCHECKED_MARKDOWN);
}
return content.toString();
}


// Replaces any CheckableSpans with their markdown preview counterpart (e.g. '- [\u2a2f]')
public java.lang.String getPreviewTextContent() {
if (getText() == null) {
return "";
}
android.text.SpannableStringBuilder content;
content = new android.text.SpannableStringBuilder(getText());
com.automattic.simplenote.widgets.CheckableSpan[] spans;
spans = content.getSpans(0, content.length(), com.automattic.simplenote.widgets.CheckableSpan.class);
for (com.automattic.simplenote.widgets.CheckableSpan span : spans) {
int start;
start = content.getSpanStart(span);
int end;
end = content.getSpanEnd(span);
((android.text.Editable) (content)).replace(start, end, span.isChecked() ? com.automattic.simplenote.utils.ChecklistUtils.CHECKED_MARKDOWN_PREVIEW : com.automattic.simplenote.utils.ChecklistUtils.UNCHECKED_MARKDOWN);
}
return content.toString();
}


public void processChecklists() {
if ((getText().length() == 0) || (getContext() == null)) {
return;
}
try {
com.automattic.simplenote.utils.ChecklistUtils.addChecklistSpansForRegexAndColor(getContext(), getText(), com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX_LINES_CHECKED, com.automattic.simplenote.utils.ThemeUtils.getColorResourceFromAttribute(getContext(), com.automattic.simplenote.R.attr.colorAccent), false);
com.automattic.simplenote.utils.ChecklistUtils.addChecklistSpansForRegexAndColor(getContext(), getText(), com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX_LINES_UNCHECKED, com.automattic.simplenote.utils.ThemeUtils.getColorResourceFromAttribute(getContext(), com.automattic.simplenote.R.attr.notePreviewColor), false);
} catch (java.lang.Exception e) {
e.printStackTrace();
}
}


public void setOnCheckboxToggledListener(com.automattic.simplenote.widgets.SimplenoteEditText.OnCheckboxToggledListener listener) {
mOnCheckboxToggledListener = listener;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
