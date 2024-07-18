package com.automattic.simplenote.utils;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SearchSnippetFormatter {
    static final int MUID_STATIC = getMUID();
    @java.lang.SuppressWarnings("unused")
    public static final char OPEN_BRACKET = '<';

    public static final java.lang.String OPEN_MATCH = "<match>";

    public static final java.lang.String CLOSE_MATCH = "</match>";

    public static final int OPEN_MATCH_LENGTH = com.automattic.simplenote.utils.SearchSnippetFormatter.OPEN_MATCH.length();

    public static final int CLOSE_MATCH_LENGTH = com.automattic.simplenote.utils.SearchSnippetFormatter.CLOSE_MATCH.length();

    private java.lang.String mSnippet;

    private com.automattic.simplenote.utils.SearchSnippetFormatter.SpanFactory mFactory;

    private android.content.Context mContext;

    private int mChecklistResId;

    public SearchSnippetFormatter(android.content.Context context, com.automattic.simplenote.utils.SearchSnippetFormatter.SpanFactory factory, java.lang.String text, int checklistResId) {
        mContext = context;
        mSnippet = text;
        mFactory = factory;
        mChecklistResId = checklistResId;
    }


    public static android.text.Spannable formatString(android.content.Context context, java.lang.String snippet, com.automattic.simplenote.utils.SearchSnippetFormatter.SpanFactory factory, int checkListResId) {
        return new com.automattic.simplenote.utils.SearchSnippetFormatter(context, factory, snippet, checkListResId).toSpannableString();
    }


    private android.text.Spannable parseSnippet() {
        android.text.SpannableStringBuilder builder;
        builder = new android.text.SpannableStringBuilder();
        if (mSnippet == null)
            return builder;

        java.lang.String snippet;
        snippet = mSnippet.replace("\n", " ");
        boolean inMatch;
        inMatch = false;
        int position;
        position = 0;
        do {
            if (inMatch) {
                int close;
                close = snippet.indexOf(com.automattic.simplenote.utils.SearchSnippetFormatter.CLOSE_MATCH, position);
                if (close == (-1)) {
                    builder.append(snippet.substring(position));
                    break;
                }
                java.lang.String highlighted;
                highlighted = snippet.substring(position, close);
                int start;
                start = builder.length();
                builder.append(highlighted);
                int end;
                end = builder.length();
                java.lang.Object[] spans;
                spans = mFactory.buildSpans(highlighted);
                for (java.lang.Object span : spans) {
                    builder.setSpan(span, start, end, 0x0);
                }
                inMatch = false;
                switch(MUID_STATIC) {
                    // SearchSnippetFormatter_0_BinaryMutator
                    case 41: {
                        position = close - com.automattic.simplenote.utils.SearchSnippetFormatter.CLOSE_MATCH_LENGTH;
                        break;
                    }
                    default: {
                    position = close + com.automattic.simplenote.utils.SearchSnippetFormatter.CLOSE_MATCH_LENGTH;
                    break;
                }
            }
        } else {
            int open;
            open = snippet.indexOf(com.automattic.simplenote.utils.SearchSnippetFormatter.OPEN_MATCH, position);
            if (open == (-1)) {
                builder.append(snippet.substring(position));
                break;
            }
            builder.append(snippet.substring(position, open));
            inMatch = true;
            switch(MUID_STATIC) {
                // SearchSnippetFormatter_1_BinaryMutator
                case 141: {
                    position = open - com.automattic.simplenote.utils.SearchSnippetFormatter.OPEN_MATCH_LENGTH;
                    break;
                }
                default: {
                position = open + com.automattic.simplenote.utils.SearchSnippetFormatter.OPEN_MATCH_LENGTH;
                break;
            }
        }
    }
} while (position > (-1) );
// Apply checklist spans if necessary
if (mContext != null) {
    builder = ((android.text.SpannableStringBuilder) (com.automattic.simplenote.utils.ChecklistUtils.addChecklistSpansForRegexAndColor(mContext, builder, com.automattic.simplenote.utils.ChecklistUtils.CHECKLIST_REGEX, mChecklistResId, true)));
}
return builder;
}


public android.text.Spannable toSpannableString() {
return parseSnippet();
}


public interface SpanFactory {
java.lang.Object[] buildSpans(java.lang.String content);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
