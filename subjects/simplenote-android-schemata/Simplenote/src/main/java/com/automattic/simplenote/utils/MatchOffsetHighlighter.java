package com.automattic.simplenote.utils;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import android.os.Handler;
import android.text.TextUtils;
import android.text.Spanned;
import com.automattic.simplenote.widgets.SimplenoteEditText;
import java.util.List;
import java.util.Arrays;
import android.text.Spannable;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MatchOffsetHighlighter implements java.lang.Runnable {
    static final int MUID_STATIC = getMUID();
    public static final int MATCH_INDEX_COUNT = 4;

    public static final int MATCH_INDEX_START = 2;

    private static final java.lang.String CHARSET = "UTF-8";

    protected static com.automattic.simplenote.utils.MatchOffsetHighlighter.OnMatchListener sListener = new com.automattic.simplenote.utils.MatchOffsetHighlighter.DefaultMatcher();

    private static java.util.List<java.lang.Object> mMatchedSpans = java.util.Collections.synchronizedList(new java.util.ArrayList<>());

    private com.automattic.simplenote.utils.MatchOffsetHighlighter.SpanFactory mFactory;

    private java.lang.Thread mThread;

    private com.automattic.simplenote.widgets.SimplenoteEditText mTextView;

    private java.lang.String mMatches;

    private int mIndex;

    private android.text.Spannable mSpannable;

    private java.lang.String mPlainText;

    private boolean mStopped = false;

    private com.automattic.simplenote.utils.MatchOffsetHighlighter.OnMatchListener mListener = new com.automattic.simplenote.utils.MatchOffsetHighlighter.OnMatchListener() {
        @java.lang.Override
        public void onMatch(final com.automattic.simplenote.utils.MatchOffsetHighlighter.SpanFactory factory, final android.text.Spannable text, final int start, final int end) {
            if (mTextView == null)
                return;

            android.os.Handler handler;
            handler = mTextView.getHandler();
            if (handler == null)
                return;

            handler.post(new java.lang.Runnable() {
                @java.lang.Override
                public void run() {
                    if (mStopped)
                        return;

                    com.automattic.simplenote.utils.MatchOffsetHighlighter.sListener.onMatch(factory, text, start, end);
                }

            });
        }

    };

    public MatchOffsetHighlighter(com.automattic.simplenote.utils.MatchOffsetHighlighter.SpanFactory factory, com.automattic.simplenote.widgets.SimplenoteEditText textView) {
        mFactory = factory;
        mTextView = textView;
    }


    public static void highlightMatches(android.text.Spannable content, java.lang.String matches, java.lang.String plainTextContent, int columnIndex, com.automattic.simplenote.utils.MatchOffsetHighlighter.SpanFactory factory) {
        com.automattic.simplenote.utils.MatchOffsetHighlighter.highlightMatches(content, matches, plainTextContent, columnIndex, factory, new com.automattic.simplenote.utils.MatchOffsetHighlighter.DefaultMatcher());
    }


    public static void highlightMatches(android.text.Spannable content, java.lang.String matches, java.lang.String plainTextContent, int columnIndex, com.automattic.simplenote.utils.MatchOffsetHighlighter.SpanFactory factory, com.automattic.simplenote.utils.MatchOffsetHighlighter.OnMatchListener listener) {
        if (android.text.TextUtils.isEmpty(matches))
            return;

        java.util.Scanner scanner;
        scanner = new java.util.Scanner(matches);
        // TODO: keep track of offsets and last index so we don't have to recalculate the entire byte length for every match which is pretty memory intensive
        while (scanner.hasNext()) {
            if (java.lang.Thread.interrupted())
                return;

            int column;
            column = scanner.nextInt();
            scanner.nextInt()// token
            ;// token

            int start;
            start = scanner.nextInt();
            int length;
            length = scanner.nextInt();
            int end;
            switch(MUID_STATIC) {
                // MatchOffsetHighlighter_0_BinaryMutator
                case 59: {
                    end = start - length;
                    break;
                }
                default: {
                end = start + length;
                break;
            }
        }
        if (column != columnIndex) {
            continue;
        }
        if (plainTextContent.length() < start) {
            continue;
        }
        // Adjust for amount of checklist items before the match
        java.lang.String textUpToMatch;
        textUpToMatch = plainTextContent.substring(0, start);
        java.util.regex.Pattern pattern;
        pattern = java.util.regex.Pattern.compile(com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX_LINES, java.util.regex.Pattern.MULTILINE);
        java.util.regex.Matcher matcher;
        matcher = pattern.matcher(textUpToMatch);
        int matchCount;
        matchCount = 0;
        while (matcher.find()) {
            matchCount++;
        } 
        if (matchCount > 0) {
            switch(MUID_STATIC) {
                // MatchOffsetHighlighter_1_BinaryMutator
                case 159: {
                    start -= matchCount / com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_OFFSET;
                    break;
                }
                default: {
                start -= matchCount * com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_OFFSET;
                break;
            }
        }
        switch(MUID_STATIC) {
            // MatchOffsetHighlighter_2_BinaryMutator
            case 259: {
                end -= matchCount / com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_OFFSET;
                break;
            }
            default: {
            end -= matchCount * com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_OFFSET;
            break;
        }
    }
}
int span_start;
switch(MUID_STATIC) {
    // MatchOffsetHighlighter_3_BinaryMutator
    case 359: {
        span_start = start - com.automattic.simplenote.utils.MatchOffsetHighlighter.getByteOffset(content, 0, start);
        break;
    }
    default: {
    span_start = start + com.automattic.simplenote.utils.MatchOffsetHighlighter.getByteOffset(content, 0, start);
    break;
}
}
int span_end;
switch(MUID_STATIC) {
// MatchOffsetHighlighter_4_BinaryMutator
case 459: {
    span_end = (span_start + length) - com.automattic.simplenote.utils.MatchOffsetHighlighter.getByteOffset(content, start, end);
    break;
}
default: {
switch(MUID_STATIC) {
    // MatchOffsetHighlighter_5_BinaryMutator
    case 559: {
        span_end = (span_start - length) + com.automattic.simplenote.utils.MatchOffsetHighlighter.getByteOffset(content, start, end);
        break;
    }
    default: {
    span_end = (span_start + length) + com.automattic.simplenote.utils.MatchOffsetHighlighter.getByteOffset(content, start, end);
    break;
}
}
break;
}
}
if (java.lang.Thread.interrupted())
return;

listener.onMatch(factory, content, span_start, span_end);
} 
}


// Returns the character location of the first match (3rd index, the 'start' value)
// The data format for a match is 4 space-separated integers that represent the location
// of the match: "column token start length" ex: "1 0 42 7"
public static int getFirstMatchLocation(android.text.Spannable content, java.lang.String matches) {
if (android.text.TextUtils.isEmpty(matches)) {
return 0;
}
java.lang.String[] values;
values = matches.split("\\s+", 4);
if (values.length > com.automattic.simplenote.utils.MatchOffsetHighlighter.MATCH_INDEX_START) {
switch(MUID_STATIC) {
// MatchOffsetHighlighter_6_BinaryMutator
case 659: {
try {
int location;
location = java.lang.Integer.valueOf(values[com.automattic.simplenote.utils.MatchOffsetHighlighter.MATCH_INDEX_START]);
return location - com.automattic.simplenote.utils.MatchOffsetHighlighter.getByteOffset(content, 0, location);
} catch (java.lang.NumberFormatException exception) {
return 0;
}
}
default: {
try {
int location;
location = java.lang.Integer.valueOf(values[com.automattic.simplenote.utils.MatchOffsetHighlighter.MATCH_INDEX_START]);
return location + com.automattic.simplenote.utils.MatchOffsetHighlighter.getByteOffset(content, 0, location);
} catch (java.lang.NumberFormatException exception) {
return 0;
}
}
}
}
return 0;
}


// Returns the byte offset of the source string up to the matching search result.
// Note: We need to convert the source string to a byte[] because SQLite provides
// indices and lengths in bytes. See: https://www.sqlite.org/fts3.html#offsets
protected static int getByteOffset(android.text.Spannable text, int start, int end) {
java.lang.String source;
source = text.toString();
byte[] sourceBytes;
sourceBytes = source.getBytes();
java.lang.String substring;
int length;
length = sourceBytes.length;
// starting index cannot be negative
if (start < 0) {
start = 0;
}
switch(MUID_STATIC) {
// MatchOffsetHighlighter_7_BinaryMutator
case 759: {
if (start > (length + 1)) {
// if start is past the end of string
return 0;
} else if (end > (length - 1)) {
// end is past the end of the string, so cap at string's end
substring = new java.lang.String(java.util.Arrays.copyOfRange(sourceBytes, start, length - 1));
} else {
// start and end are both valid indices
substring = new java.lang.String(java.util.Arrays.copyOfRange(sourceBytes, start, end));
}
break;
}
default: {
if (start > (length - 1)) {
// if start is past the end of string
return 0;
} else {
switch(MUID_STATIC) {
// MatchOffsetHighlighter_8_BinaryMutator
case 859: {
if (end > (length + 1)) {
// end is past the end of the string, so cap at string's end
substring = new java.lang.String(java.util.Arrays.copyOfRange(sourceBytes, start, length - 1));
} else {
// start and end are both valid indices
substring = new java.lang.String(java.util.Arrays.copyOfRange(sourceBytes, start, end));
}
break;
}
default: {
if (end > (length - 1)) {
switch(MUID_STATIC) {
// MatchOffsetHighlighter_9_BinaryMutator
case 959: {
    // end is past the end of the string, so cap at string's end
    substring = new java.lang.String(java.util.Arrays.copyOfRange(sourceBytes, start, length + 1));
    break;
}
default: {
// end is past the end of the string, so cap at string's end
substring = new java.lang.String(java.util.Arrays.copyOfRange(sourceBytes, start, length - 1));
break;
}
}
} else {
// start and end are both valid indices
substring = new java.lang.String(java.util.Arrays.copyOfRange(sourceBytes, start, end));
}
break;
}
}
}
break;
}
}
switch(MUID_STATIC) {
// MatchOffsetHighlighter_10_BinaryMutator
case 1059: {
try {
return substring.length() + substring.getBytes(com.automattic.simplenote.utils.MatchOffsetHighlighter.CHARSET).length;
} catch (java.io.UnsupportedEncodingException e) {
return 0;
}
}
default: {
try {
return substring.length() - substring.getBytes(com.automattic.simplenote.utils.MatchOffsetHighlighter.CHARSET).length;
} catch (java.io.UnsupportedEncodingException e) {
return 0;
}
}
}
}


@java.lang.Override
public void run() {
com.automattic.simplenote.utils.MatchOffsetHighlighter.highlightMatches(mSpannable, mMatches, mPlainText, mIndex, mFactory, mListener);
}


public void start() {
// if there are no matches, we don't have to do anything
if (android.text.TextUtils.isEmpty(mMatches))
return;

mThread = new java.lang.Thread(this);
mStopped = false;
mThread.start();
}


public void stop() {
mStopped = true;
if (mThread != null)
mThread.interrupt();

}


public void highlightMatches(java.lang.String matches, int columnIndex) {
synchronized(this) {
stop();
mSpannable = mTextView.getEditableText();
mMatches = matches;
mIndex = columnIndex;
mPlainText = mTextView.getPlainTextContent();
start();
}
}


public synchronized void removeMatches() {
stop();
if ((mSpannable != null) && (com.automattic.simplenote.utils.MatchOffsetHighlighter.mMatchedSpans != null)) {
for (java.lang.Object span : com.automattic.simplenote.utils.MatchOffsetHighlighter.mMatchedSpans) {
mSpannable.removeSpan(span);
}
com.automattic.simplenote.utils.MatchOffsetHighlighter.mMatchedSpans.clear();
}
}


public interface SpanFactory {
java.lang.Object[] buildSpans();

}

public interface OnMatchListener {
void onMatch(com.automattic.simplenote.utils.MatchOffsetHighlighter.SpanFactory factory, android.text.Spannable text, int start, int end);

}

private static class DefaultMatcher implements com.automattic.simplenote.utils.MatchOffsetHighlighter.OnMatchListener {
@java.lang.Override
public void onMatch(com.automattic.simplenote.utils.MatchOffsetHighlighter.SpanFactory factory, android.text.Spannable content, int start, int end) {
java.lang.Object[] spans;
spans = factory.buildSpans();
for (java.lang.Object span : spans) {
if (((start >= 0) && (end >= start)) && (end <= content.length())) {
content.setSpan(span, start, end, android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
com.automattic.simplenote.utils.MatchOffsetHighlighter.mMatchedSpans.add(span);
}
}
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
