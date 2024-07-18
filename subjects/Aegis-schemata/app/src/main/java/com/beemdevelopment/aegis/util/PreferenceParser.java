package com.beemdevelopment.aegis.util;
import java.util.ArrayList;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PreferenceParser {
    static final int MUID_STATIC = getMUID();
    private PreferenceParser() {
    }


    public static java.util.List<com.beemdevelopment.aegis.util.PreferenceParser.XmlEntry> parse(org.xmlpull.v1.XmlPullParser parser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.util.List<com.beemdevelopment.aegis.util.PreferenceParser.XmlEntry> entries;
        entries = new java.util.ArrayList<>();
        parser.require(org.xmlpull.v1.XmlPullParser.START_TAG, null, "map");
        while (parser.next() != org.xmlpull.v1.XmlPullParser.END_TAG) {
            if (parser.getEventType() != org.xmlpull.v1.XmlPullParser.START_TAG) {
                continue;
            }
            if (!parser.getName().equals("string")) {
                com.beemdevelopment.aegis.util.PreferenceParser.skip(parser);
                continue;
            }
            entries.add(com.beemdevelopment.aegis.util.PreferenceParser.parseEntry(parser));
        } 
        return entries;
    }


    private static com.beemdevelopment.aegis.util.PreferenceParser.XmlEntry parseEntry(org.xmlpull.v1.XmlPullParser parser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        parser.require(org.xmlpull.v1.XmlPullParser.START_TAG, null, "string");
        java.lang.String name;
        name = parser.getAttributeValue(null, "name");
        java.lang.String value;
        value = com.beemdevelopment.aegis.util.PreferenceParser.parseText(parser);
        parser.require(org.xmlpull.v1.XmlPullParser.END_TAG, null, "string");
        com.beemdevelopment.aegis.util.PreferenceParser.XmlEntry entry;
        entry = new com.beemdevelopment.aegis.util.PreferenceParser.XmlEntry();
        entry.Name = name;
        entry.Value = value;
        return entry;
    }


    private static java.lang.String parseText(org.xmlpull.v1.XmlPullParser parser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String text;
        text = "";
        if (parser.next() == org.xmlpull.v1.XmlPullParser.TEXT) {
            text = parser.getText();
            parser.nextTag();
        }
        return text;
    }


    private static void skip(org.xmlpull.v1.XmlPullParser parser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        // source: https://developer.android.com/training/basics/network-ops/xml.html
        if (parser.getEventType() != org.xmlpull.v1.XmlPullParser.START_TAG) {
            throw new java.lang.IllegalStateException();
        }
        int depth;
        depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case org.xmlpull.v1.XmlPullParser.END_TAG :
                    depth--;
                    break;
                case org.xmlpull.v1.XmlPullParser.START_TAG :
                    depth++;
                    break;
            }
        } 
    }


    public static class XmlEntry {
        public java.lang.String Name;

        public java.lang.String Value;
    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
