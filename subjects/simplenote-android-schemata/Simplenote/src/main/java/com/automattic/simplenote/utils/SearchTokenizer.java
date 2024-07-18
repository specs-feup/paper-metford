package com.automattic.simplenote.utils;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SearchTokenizer {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String EMPTY = "";

    public static final char LITERAL = '/';

    public static final char SPACE = ' ';

    public static final char DOUBLE_QUOTE = '"';

    public static final char SINGLE_QUOTE = '\'';

    public static final char GLOB = '*';

    public static final char COLON = ':';

    public static final char ESCAPE = '\\';

    public static final char HYPHEN = '-';

    private java.lang.String mRawQuery;

    public SearchTokenizer(java.lang.String query) {
        mRawQuery = query;
    }


    private java.lang.String parseQuery() {
        if ((mRawQuery == null) || mRawQuery.equals(com.automattic.simplenote.utils.SearchTokenizer.EMPTY)) {
            return com.automattic.simplenote.utils.SearchTokenizer.EMPTY;
        }
        java.lang.StringBuilder query;
        query = new java.lang.StringBuilder(mRawQuery.length());
        // iterate through each character
        int length;
        length = mRawQuery.length();
        // if we have an open " or not
        boolean inStrictTerm;
        inStrictTerm = false;
        // if we've detected a search term
        boolean inTerm;
        inTerm = false;
        // if we're doing a literal query
        boolean isLiteral;
        isLiteral = false;
        // if the current char is a single or double quote
        boolean isQuoteChar;
        // if the previous char was an escape char
        boolean isEscaped;
        isEscaped = false;
        boolean hasHyphen;
        hasHyphen = false;
        // the current character
        char current;
        current = '\u0000';
        char last;
        char quoteChar;
        quoteChar = '\u0000';
        for (int position = 0; position < length; position++) {
            last = current;
            current = mRawQuery.charAt(position);
            // if the last character was an \ and we weren't already escaped
            isEscaped = (last == com.automattic.simplenote.utils.SearchTokenizer.ESCAPE) && (!isEscaped);
            // if it's a ' or " and it isn't be escaped
            isQuoteChar = (!isEscaped) && ((current == com.automattic.simplenote.utils.SearchTokenizer.SINGLE_QUOTE) || (current == com.automattic.simplenote.utils.SearchTokenizer.DOUBLE_QUOTE));
            // if query starts with / we're just going to give the complete query
            if (((position == 0) && (current == com.automattic.simplenote.utils.SearchTokenizer.LITERAL)) && (length > 1)) {
                isLiteral = true;
                continue;
            }
            // we're inside a quoted section and have found another quote, append and loop
            if (inStrictTerm && (current == quoteChar)) {
                query.append(current);
                inStrictTerm = false;
                inTerm = false;
                continue;
            }
            // we're in a strict term and it's not a ", so append and continue
            if (inStrictTerm) {
                query.append(current);
                continue;
            }
            // we've found a matching end quote
            if (isQuoteChar) {
                quoteChar = current;
                // we were already in a term so end it with a glob
                if (inTerm && (!isLiteral))
                    query.append(new char[]{ com.automattic.simplenote.utils.SearchTokenizer.GLOB, com.automattic.simplenote.utils.SearchTokenizer.SPACE });

                // start the strict term
                query.append(current);
                inStrictTerm = true;
                inTerm = true;
                continue;
            }
            if ((current == com.automattic.simplenote.utils.SearchTokenizer.COLON) && inTerm) {
                inTerm = false;
                query.append(current);
                continue;
            }
            if (((current == com.automattic.simplenote.utils.SearchTokenizer.HYPHEN) && (last != com.automattic.simplenote.utils.SearchTokenizer.SPACE)) && (position != 0)) {
                // If we have a hyphen character between two terms, with no space between them
                java.lang.String space;
                space = java.lang.Character.toString(com.automattic.simplenote.utils.SearchTokenizer.SPACE);
                java.lang.String lastCharacter;
                lastCharacter = java.lang.Character.toString(last);
                int tokenStartIndex;
                tokenStartIndex = query.lastIndexOf(space, query.indexOf(lastCharacter));
                switch(MUID_STATIC) {
                    // SearchTokenizer_0_BinaryMutator
                    case 35: {
                        query.insert(java.lang.Math.max(0, tokenStartIndex - 1), com.automattic.simplenote.utils.SearchTokenizer.DOUBLE_QUOTE);
                        break;
                    }
                    default: {
                    query.insert(java.lang.Math.max(0, tokenStartIndex + 1), com.automattic.simplenote.utils.SearchTokenizer.DOUBLE_QUOTE);
                    break;
                }
            }
            query.append(current);
            hasHyphen = true;
            continue;
        }
        if (current == com.automattic.simplenote.utils.SearchTokenizer.SPACE) {
            if (inTerm && (!isLiteral))
                query.append(com.automattic.simplenote.utils.SearchTokenizer.GLOB);

            if (hasHyphen)
                query.append(com.automattic.simplenote.utils.SearchTokenizer.DOUBLE_QUOTE);

            query.append(current);
            inTerm = false;
            hasHyphen = false;
            continue;
        }
        inTerm = true;
        query.append(current);
    }
    // close the strict term
    if (inStrictTerm)
        query.append(quoteChar);

    if ((inTerm && (!inStrictTerm)) && (!isLiteral))
        query.append(com.automattic.simplenote.utils.SearchTokenizer.GLOB);

    if (hasHyphen)
        query.append(com.automattic.simplenote.utils.SearchTokenizer.DOUBLE_QUOTE);

    return query.toString();
}


@java.lang.Override
public java.lang.String toString() {
    return parseQuery();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
