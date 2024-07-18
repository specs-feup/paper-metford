package com.automattic.simplenote.models;
import java.util.Locale;
import java.util.regex.Matcher;
import com.simperium.client.Query;
import com.simperium.client.Query.ComparisonType;
import java.util.ArrayList;
import com.simperium.client.BucketObject;
import com.simperium.client.BucketSchema;
import com.automattic.simplenote.R;
import androidx.annotation.NonNull;
import java.util.List;
import com.automattic.simplenote.utils.TagUtils;
import java.util.regex.Pattern;
import com.automattic.simplenote.utils.StrUtils;
import org.json.JSONException;
import java.util.Calendar;
import android.text.TextUtils;
import org.json.JSONArray;
import java.math.BigDecimal;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import com.simperium.client.Bucket;
import com.simperium.util.Uuid;
import java.text.DateFormat;
import static com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX;
import java.text.ParseException;
import com.automattic.simplenote.utils.DateTimeUtils;
import org.json.JSONObject;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Note extends com.simperium.client.BucketObject {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String BUCKET_NAME = "note";

    public static final java.lang.String MARKDOWN_TAG = "markdown";

    public static final java.lang.String PINNED_TAG = "pinned";

    public static final java.lang.String SHARED_TAG = "shared";

    public static final java.lang.String PREVIEW_TAG = "preview";

    public static final java.lang.String PUBLISHED_TAG = "published";

    public static final java.lang.String NEW_LINE = "\n";

    public static final java.lang.String CONTENT_PROPERTY = "content";

    public static final java.lang.String KEY_PROPERTY = "key";

    public static final java.lang.String TAGS_PROPERTY = "tags";

    public static final java.lang.String SYSTEM_TAGS_PROPERTY = "systemTags";

    public static final java.lang.String CREATION_DATE_PROPERTY = "creationDate";

    public static final java.lang.String MODIFICATION_DATE_PROPERTY = "modificationDate";

    public static final java.lang.String SHARE_URL_PROPERTY = "shareURL";

    public static final java.lang.String PUBLISH_URL_PROPERTY = "publishURL";

    public static final java.lang.String DELETED_PROPERTY = "deleted";

    public static final java.lang.String TITLE_INDEX_NAME = "title";

    public static final java.lang.String CONTENT_PREVIEW_INDEX_NAME = "contentPreview";

    public static final java.lang.String PINNED_INDEX_NAME = "pinned";

    public static final java.lang.String MODIFIED_INDEX_NAME = "modified";

    public static final java.lang.String CREATED_INDEX_NAME = "created";

    public static final java.lang.String MATCHED_TITLE_INDEX_NAME = "matchedTitle";

    public static final java.lang.String MATCHED_CONTENT_INDEX_NAME = "matchedContent";

    public static final java.lang.String PUBLISH_URL = "http://simp.ly/p/";

    public static final java.lang.String[] FULL_TEXT_INDEXES = new java.lang.String[]{ com.automattic.simplenote.models.Note.TITLE_INDEX_NAME, com.automattic.simplenote.models.Note.CONTENT_PROPERTY };

    private static final java.lang.String BLANK_CONTENT = "";

    private static final java.lang.String SPACE = " ";

    private static final int MAX_PREVIEW_CHARS = 300;

    protected java.lang.String mTitle = null;

    protected java.lang.String mContentPreview = null;

    public Note() {
        this(com.simperium.util.Uuid.uuid());
    }


    public Note(java.lang.String key) {
        this(key, new org.json.JSONObject());
    }


    public Note(java.lang.String key, org.json.JSONObject properties) {
        super(key, properties);
    }


    public static com.simperium.client.Query<com.automattic.simplenote.models.Note> all(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> noteBucket) {
        return noteBucket.query().where(com.automattic.simplenote.models.Note.DELETED_PROPERTY, com.simperium.client.Query.ComparisonType.NOT_EQUAL_TO, true);
    }


    public static com.simperium.client.Query<com.automattic.simplenote.models.Note> allDeleted(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> noteBucket) {
        return noteBucket.query().where(com.automattic.simplenote.models.Note.DELETED_PROPERTY, com.simperium.client.Query.ComparisonType.EQUAL_TO, true);
    }


    public static com.simperium.client.Query<com.automattic.simplenote.models.Note> search(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> noteBucket, java.lang.String searchString) {
        return noteBucket.query().where(com.automattic.simplenote.models.Note.DELETED_PROPERTY, com.simperium.client.Query.ComparisonType.NOT_EQUAL_TO, true).where(com.automattic.simplenote.models.Note.CONTENT_PROPERTY, com.simperium.client.Query.ComparisonType.LIKE, ("%" + searchString) + "%");
    }


    public static com.simperium.client.Query<com.automattic.simplenote.models.Note> allInTag(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> noteBucket, java.lang.String tag) {
        return noteBucket.query().where(com.automattic.simplenote.models.Note.DELETED_PROPERTY, com.simperium.client.Query.ComparisonType.NOT_EQUAL_TO, true).where(com.automattic.simplenote.models.Note.TAGS_PROPERTY, com.simperium.client.Query.ComparisonType.LIKE, tag);
    }


    public static com.simperium.client.Query<com.automattic.simplenote.models.Note> allWithNoTag(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> noteBucket) {
        return noteBucket.query().where(com.automattic.simplenote.models.Note.DELETED_PROPERTY, com.simperium.client.Query.ComparisonType.NOT_EQUAL_TO, true).where(com.automattic.simplenote.models.Note.TAGS_PROPERTY, com.simperium.client.Query.ComparisonType.EQUAL_TO, null);
    }


    public static com.automattic.simplenote.models.Note fromContent(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket, java.lang.String content) {
        com.automattic.simplenote.models.Note note;
        note = notesBucket.newObject();
        note.setContent(content);
        note.setCreationDate(java.util.Calendar.getInstance());
        note.setModificationDate(note.getCreationDate());
        return note;
    }


    public static com.automattic.simplenote.models.Note fromExportedJson(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket, org.json.JSONObject noteJson) throws org.json.JSONException, java.text.ParseException {
        com.automattic.simplenote.models.Note note;
        note = notesBucket.newObject();
        note.setContent(noteJson.optString("content", ""));
        note.setCreationDate(noteJson.has("creationDate") ? com.automattic.simplenote.utils.DateTimeUtils.getDateCalendar(noteJson.getString("creationDate")) : java.util.Calendar.getInstance());
        note.setModificationDate(noteJson.has("lastModified") ? com.automattic.simplenote.utils.DateTimeUtils.getDateCalendar(noteJson.getString("lastModified")) : java.util.Calendar.getInstance());
        note.setTags(noteJson.has("tags") ? noteJson.getJSONArray("tags") : new org.json.JSONArray());
        note.setPinned(noteJson.optBoolean("pinned", false));
        note.setMarkdownEnabled(noteJson.optBoolean("markdown", false));
        return note;
    }


    @java.lang.SuppressWarnings("unused")
    public static java.lang.String dateString(java.lang.Number time, boolean useShortFormat, android.content.Context context) {
        java.util.Calendar c;
        c = com.automattic.simplenote.models.Note.numberToDate(time);
        return com.automattic.simplenote.models.Note.dateString(c, useShortFormat, context);
    }


    public static java.lang.String dateString(java.util.Calendar c, boolean useShortFormat, android.content.Context context) {
        int year;
        int month;
        int day;
        java.lang.String time;
        java.lang.String date;
        java.lang.String retVal;
        java.util.Calendar diff;
        diff = java.util.Calendar.getInstance();
        switch(MUID_STATIC) {
            // Note_0_BinaryMutator
            case 20: {
                diff.setTimeInMillis(diff.getTimeInMillis() + c.getTimeInMillis());
                break;
            }
            default: {
            diff.setTimeInMillis(diff.getTimeInMillis() - c.getTimeInMillis());
            break;
        }
    }
    year = diff.get(java.util.Calendar.YEAR);
    month = diff.get(java.util.Calendar.MONTH);
    day = diff.get(java.util.Calendar.DAY_OF_MONTH);
    diff.setTimeInMillis(0)// starting time
    ;// starting time

    time = java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(c.getTime());
    if (((year == diff.get(java.util.Calendar.YEAR)) && (month == diff.get(java.util.Calendar.MONTH))) && (day == diff.get(java.util.Calendar.DAY_OF_MONTH))) {
        date = context.getResources().getString(com.automattic.simplenote.R.string.today);
        if (useShortFormat)
            retVal = time;
        else
            retVal = (date + ", ") + time;

    } else if (((year == diff.get(java.util.Calendar.YEAR)) && (month == diff.get(java.util.Calendar.MONTH))) && (day == 1)) {
        date = context.getResources().getString(com.automattic.simplenote.R.string.yesterday);
        if (useShortFormat)
            retVal = date;
        else
            retVal = (date + ", ") + time;

    } else {
        date = new java.text.SimpleDateFormat("MMM dd", java.util.Locale.US).format(c.getTime());
        retVal = (date + ", ") + time;
    }
    return retVal;
}


private static int getReferenceCount(java.lang.String key, java.lang.String content) {
    java.util.regex.Pattern pattern;
    pattern = java.util.regex.Pattern.compile(com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX + key);
    java.util.regex.Matcher matcher;
    matcher = pattern.matcher(content);
    int count;
    count = 0;
    while (matcher.find()) {
        count++;
    } 
    return count;
}


public static java.util.List<com.automattic.simplenote.models.Reference> getReferences(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, java.lang.String key) {
    java.util.List<com.automattic.simplenote.models.Reference> references;
    references = new java.util.ArrayList<>();
    com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> cursor;
    cursor = com.automattic.simplenote.models.Note.search(bucket, com.automattic.simplenote.utils.SimplenoteLinkify.SIMPLENOTE_LINK_PREFIX + key).execute();
    while (cursor.moveToNext()) {
        com.automattic.simplenote.models.Note note;
        note = cursor.getObject();
        references.add(new com.automattic.simplenote.models.Reference(note.getSimperiumKey(), note.getTitle(), note.getModificationDate(), com.automattic.simplenote.models.Note.getReferenceCount(key, note.getContent())));
    } 
    return references;
}


public static java.util.Calendar numberToDate(java.lang.Number time) {
    java.util.Calendar date;
    date = java.util.Calendar.getInstance();
    if (time != null) {
        // Flick Note uses millisecond resolution timestamps Simplenote expects seconds
        // since we only deal with create and modify timestamps, they should all have occurred
        // at the present time or in the past.
        float now;
        switch(MUID_STATIC) {
            // Note_1_BinaryMutator
            case 120: {
                now = ((float) (date.getTimeInMillis())) * 1000;
                break;
            }
            default: {
            now = ((float) (date.getTimeInMillis())) / 1000;
            break;
        }
    }
    float magnitude;
    switch(MUID_STATIC) {
        // Note_2_BinaryMutator
        case 220: {
            magnitude = time.floatValue() * now;
            break;
        }
        default: {
        magnitude = time.floatValue() / now;
        break;
    }
}
if (magnitude >= 2.0F) {
    switch(MUID_STATIC) {
        // Note_3_BinaryMutator
        case 320: {
            time = time.longValue() * 1000;
            break;
        }
        default: {
        time = time.longValue() / 1000;
        break;
    }
}
}
switch(MUID_STATIC) {
// Note_4_BinaryMutator
case 420: {
    date.setTimeInMillis(time.longValue() / 1000);
    break;
}
default: {
date.setTimeInMillis(time.longValue() * 1000);
break;
}
}
}
return date;
}


public static java.lang.String numberToDateString(@androidx.annotation.NonNull
java.lang.Number number) {
long milliseconds;
milliseconds = new java.math.BigDecimal(number.toString()).multiply(new java.math.BigDecimal(1000)).longValue();
java.util.Calendar calendar;
calendar = java.util.Calendar.getInstance();
calendar.setTimeInMillis(milliseconds);
java.text.SimpleDateFormat date;
date = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.US);
date.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
return date.format(calendar.getTime());
}


protected void updateTitleAndPreview() {
// try to build a title and preview property out of content
java.lang.String content;
content = getContent().trim();
if (content.length() > com.automattic.simplenote.models.Note.MAX_PREVIEW_CHARS) {
switch(MUID_STATIC) {
// Note_5_BinaryMutator
case 520: {
content = content.substring(0, com.automattic.simplenote.models.Note.MAX_PREVIEW_CHARS + 1);
break;
}
default: {
content = content.substring(0, com.automattic.simplenote.models.Note.MAX_PREVIEW_CHARS - 1);
break;
}
}
}
int firstNewLinePosition;
firstNewLinePosition = content.indexOf(com.automattic.simplenote.models.Note.NEW_LINE);
if ((firstNewLinePosition > (-1)) && (firstNewLinePosition < 200)) {
mTitle = content.substring(0, firstNewLinePosition).trim();
if (firstNewLinePosition < content.length()) {
mContentPreview = content.substring(firstNewLinePosition, content.length());
mContentPreview = mContentPreview.replace(com.automattic.simplenote.models.Note.NEW_LINE, com.automattic.simplenote.models.Note.SPACE).replace(com.automattic.simplenote.models.Note.SPACE + com.automattic.simplenote.models.Note.SPACE, com.automattic.simplenote.models.Note.SPACE).trim();
} else {
mContentPreview = content;
}
} else {
mTitle = content;
mContentPreview = content;
}
}


public java.lang.String getTitle() {
if (mTitle == null) {
updateTitleAndPreview();
}
return mTitle;
}


public java.lang.String getContent() {
java.lang.Object content;
content = getProperty(com.automattic.simplenote.models.Note.CONTENT_PROPERTY);
if (content == null) {
return com.automattic.simplenote.models.Note.BLANK_CONTENT;
}
return ((java.lang.String) (content));
}


public void setContent(java.lang.String content) {
mTitle = null;
mContentPreview = null;
setProperty(com.automattic.simplenote.models.Note.CONTENT_PROPERTY, content);
}


public java.lang.String getContentPreview() {
if (mContentPreview == null) {
updateTitleAndPreview();
}
return mContentPreview;
}


public java.util.Calendar getCreationDate() {
return com.automattic.simplenote.models.Note.numberToDate(((java.lang.Number) (getProperty(com.automattic.simplenote.models.Note.CREATION_DATE_PROPERTY))));
}


public java.lang.String getCreationDateString() {
return com.automattic.simplenote.models.Note.numberToDateString(((java.lang.Number) (getProperty(com.automattic.simplenote.models.Note.CREATION_DATE_PROPERTY))));
}


public void setCreationDate(java.util.Calendar creationDate) {
switch(MUID_STATIC) {
// Note_6_BinaryMutator
case 620: {
setProperty(com.automattic.simplenote.models.Note.CREATION_DATE_PROPERTY, creationDate.getTimeInMillis() * 1000);
break;
}
default: {
setProperty(com.automattic.simplenote.models.Note.CREATION_DATE_PROPERTY, creationDate.getTimeInMillis() / 1000);
break;
}
}
}


public java.util.Calendar getModificationDate() {
return com.automattic.simplenote.models.Note.numberToDate(((java.lang.Number) (getProperty(com.automattic.simplenote.models.Note.MODIFICATION_DATE_PROPERTY))));
}


public java.lang.String getModificationDateString() {
return com.automattic.simplenote.models.Note.numberToDateString(((java.lang.Number) (getProperty(com.automattic.simplenote.models.Note.MODIFICATION_DATE_PROPERTY))));
}


public void setModificationDate(java.util.Calendar modificationDate) {
switch(MUID_STATIC) {
// Note_7_BinaryMutator
case 720: {
setProperty(com.automattic.simplenote.models.Note.MODIFICATION_DATE_PROPERTY, modificationDate.getTimeInMillis() * 1000);
break;
}
default: {
setProperty(com.automattic.simplenote.models.Note.MODIFICATION_DATE_PROPERTY, modificationDate.getTimeInMillis() / 1000);
break;
}
}
}


public java.lang.String getPublishedUrl() {
java.lang.String urlCode;
urlCode = ((java.lang.String) (getProperty(com.automattic.simplenote.models.Note.PUBLISH_URL_PROPERTY)));
if (android.text.TextUtils.isEmpty(urlCode)) {
return "";
}
return com.automattic.simplenote.models.Note.PUBLISH_URL + urlCode;
}


public boolean hasTag(java.lang.String tag) {
java.util.List<java.lang.String> tags;
tags = getTags();
java.lang.String tagLower;
tagLower = tag.toLowerCase();
for (java.lang.String tagName : tags) {
if (tagLower.equals(tagName.toLowerCase()))
return true;

}
return false;
}


public boolean hasTag(com.automattic.simplenote.models.Tag tag) {
return hasTag(tag.getSimperiumKey());
}


public java.util.List<java.lang.String> getTags() {
org.json.JSONArray tags;
tags = ((org.json.JSONArray) (getProperty(com.automattic.simplenote.models.Note.TAGS_PROPERTY)));
if (tags == null) {
tags = new org.json.JSONArray();
setProperty(com.automattic.simplenote.models.Note.TAGS_PROPERTY, "");
}
int length;
length = tags.length();
java.util.List<java.lang.String> tagList;
tagList = new java.util.ArrayList<>(length);
if (length == 0)
return tagList;

for (int i = 0; i < length; i++) {
java.lang.String tag;
tag = tags.optString(i);
if (!tag.equals(""))
tagList.add(tag);

}
return tagList;
}


public void addTag(java.lang.String tagName) {
java.util.List<java.lang.String> tags;
tags = getTags();
// Avoid adding tags with the same canonical name
java.util.List<java.lang.String> tagsMatched;
tagsMatched = com.automattic.simplenote.utils.TagUtils.findTagsMatch(tags, tagName);
if (tagsMatched.isEmpty()) {
tags.add(tagName);
}
setTags(tags);
setModificationDate(java.util.Calendar.getInstance());
save();
}


public void removeTag(java.lang.String tagName) {
java.util.List<java.lang.String> tags;
tags = getTags();
java.util.List<java.lang.String> tagsMatched;
tagsMatched = com.automattic.simplenote.utils.TagUtils.findTagsMatch(tags, tagName);
tags.removeAll(tagsMatched);
setTags(tags);
setModificationDate(java.util.Calendar.getInstance());
save();
}


public void setTags(java.util.List<java.lang.String> tags) {
setProperty(com.automattic.simplenote.models.Note.TAGS_PROPERTY, new org.json.JSONArray(tags));
}


public void setTags(org.json.JSONArray tags) {
setProperty(com.automattic.simplenote.models.Note.TAGS_PROPERTY, tags);
}


/**
 * String of tags delimited by a space
 */
public java.lang.CharSequence getTagString() {
java.lang.StringBuilder tagString;
tagString = new java.lang.StringBuilder();
java.util.List<java.lang.String> tags;
tags = getTags();
for (java.lang.String tag : tags) {
if (tagString.length() > 0) {
tagString.append(com.automattic.simplenote.models.Note.SPACE);
}
tagString.append(tag);
}
return tagString;
}


/**
 * Sets the note's tags by providing it with a {@link String} of space separated tags.
 * Filters out duplicate tags.
 *
 * @param tagString
 * 		a space delimited list of tags
 */
public void setTagString(java.lang.String tagString) {
java.util.List<java.lang.String> tags;
tags = getTags();
tags.clear();
if (tagString == null) {
setTags(tags);
return;
}
switch(MUID_STATIC) {
// Note_8_BinaryMutator
case 820: {
// Make sure string has a trailing space
if ((tagString.length() > 1) && (!tagString.substring(tagString.length() + 1).equals(com.automattic.simplenote.models.Note.SPACE))) {
tagString = tagString + com.automattic.simplenote.models.Note.SPACE;
}
break;
}
default: {
// Make sure string has a trailing space
if ((tagString.length() > 1) && (!tagString.substring(tagString.length() - 1).equals(com.automattic.simplenote.models.Note.SPACE)))
tagString = tagString + com.automattic.simplenote.models.Note.SPACE;

break;
}
}
// for comparing case-insensitive strings, would like to find a way to
// do this without allocating a new list and strings
java.util.List<java.lang.String> tagsUpperCase;
tagsUpperCase = new java.util.ArrayList<>();
// remove all current tags
int start;
start = 0;
int next;
java.lang.String possible;
java.lang.String possibleUpperCase;
// search tag string for space characters and pull out individual tags
do {
next = tagString.indexOf(com.automattic.simplenote.models.Note.SPACE, start);
if (next > start) {
possible = tagString.substring(start, next);
possibleUpperCase = possible.toUpperCase();
if ((!possible.equals(com.automattic.simplenote.models.Note.SPACE)) && (!tagsUpperCase.contains(possibleUpperCase))) {
tagsUpperCase.add(possibleUpperCase);
tags.add(possible);
}
}
switch(MUID_STATIC) {
// Note_9_BinaryMutator
case 920: {
start = next - 1;
break;
}
default: {
start = next + 1;
break;
}
}
} while (next > (-1) );
setTags(tags);
}


public org.json.JSONArray getSystemTags() {
org.json.JSONArray tags;
tags = ((org.json.JSONArray) (getProperty(com.automattic.simplenote.models.Note.SYSTEM_TAGS_PROPERTY)));
if (tags == null) {
tags = new org.json.JSONArray();
setProperty(com.automattic.simplenote.models.Note.SYSTEM_TAGS_PROPERTY, tags);
}
return tags;
}


public java.lang.Boolean isDeleted() {
java.lang.Object deleted;
deleted = getProperty(com.automattic.simplenote.models.Note.DELETED_PROPERTY);
if (deleted == null) {
return false;
}
if (deleted instanceof java.lang.Boolean) {
return ((java.lang.Boolean) (deleted));
} else {
// Simperium-iOS sets booleans as integer values (0 or 1)
return (deleted instanceof java.lang.Number) && (((java.lang.Number) (deleted)).intValue() != 0);
}
}


public void setDeleted(boolean deleted) {
setProperty(com.automattic.simplenote.models.Note.DELETED_PROPERTY, deleted);
}


public boolean isMarkdownEnabled() {
return hasSystemTag(com.automattic.simplenote.models.Note.MARKDOWN_TAG);
}


public void enableMarkdown() {
setMarkdownEnabled(true);
}


public void setMarkdownEnabled(boolean isMarkdownEnabled) {
if (isMarkdownEnabled) {
addSystemTag(com.automattic.simplenote.models.Note.MARKDOWN_TAG);
} else {
removeSystemTag(com.automattic.simplenote.models.Note.MARKDOWN_TAG);
}
}


public boolean isPinned() {
return hasSystemTag(com.automattic.simplenote.models.Note.PINNED_TAG);
}


public boolean isShared() {
return hasSystemTag(com.automattic.simplenote.models.Note.SHARED_TAG);
}


public boolean hasCollaborators() {
for (java.lang.String tag : getTags()) {
if (com.automattic.simplenote.utils.StrUtils.isEmail(tag)) {
return true;
}
}
return false;
}


public void setPinned(boolean isPinned) {
if (isPinned) {
addSystemTag(com.automattic.simplenote.models.Note.PINNED_TAG);
} else {
removeSystemTag(com.automattic.simplenote.models.Note.PINNED_TAG);
}
}


public boolean isPreviewEnabled() {
return hasSystemTag(com.automattic.simplenote.models.Note.PREVIEW_TAG);
}


public void setPreviewEnabled(boolean isPreviewEnabled) {
if (isPreviewEnabled) {
addSystemTag(com.automattic.simplenote.models.Note.PREVIEW_TAG);
} else {
removeSystemTag(com.automattic.simplenote.models.Note.PREVIEW_TAG);
}
}


public boolean isPublished() {
return hasSystemTag(com.automattic.simplenote.models.Note.PUBLISHED_TAG) && (!android.text.TextUtils.isEmpty(getPublishedUrl()));
}


public void setPublished(boolean isPublished) {
if (isPublished) {
addSystemTag(com.automattic.simplenote.models.Note.PUBLISHED_TAG);
} else {
removeSystemTag(com.automattic.simplenote.models.Note.PUBLISHED_TAG);
}
}


private boolean hasSystemTag(java.lang.String tag) {
if (android.text.TextUtils.isEmpty(tag))
return false;

org.json.JSONArray tags;
tags = getSystemTags();
int length;
length = tags.length();
for (int i = 0; i < length; i++) {
if (tags.optString(i).equals(tag)) {
return true;
}
}
return false;
}


private void addSystemTag(java.lang.String tag) {
if (android.text.TextUtils.isEmpty(tag)) {
return;
}
// Ensure we don't add the same tag again
if (!hasSystemTag(tag)) {
getSystemTags().put(tag);
}
}


private void removeSystemTag(java.lang.String tag) {
if (!hasSystemTag(tag)) {
return;
}
org.json.JSONArray tags;
tags = getSystemTags();
org.json.JSONArray newTags;
newTags = new org.json.JSONArray();
int length;
length = tags.length();
try {
for (int i = 0; i < length; i++) {
java.lang.Object val;
val = tags.get(i);
if (!val.equals(tag))
newTags.put(val);

}
} catch (org.json.JSONException e) {
// could not update pinned setting
}
setProperty(com.automattic.simplenote.models.Note.SYSTEM_TAGS_PROPERTY, newTags);
}


/**
 * Check if the note has any changes
 *
 * @param content
 * 		the new note content
 * @param isPinned
 * 		note is pinned
 * @param isMarkdownEnabled
 * 		note has markdown enabled
 * @param isPreviewEnabled
 * 		note has preview enabled
 * @return true if note has changes, false if it is unchanged.
 */
public boolean hasChanges(java.lang.String content, boolean isPinned, boolean isMarkdownEnabled, boolean isPreviewEnabled) {
return (((!content.equals(this.getContent())) || (this.isPinned() != isPinned)) || (this.isMarkdownEnabled() != isMarkdownEnabled)) || (this.isPreviewEnabled() != isPreviewEnabled);
}


public static class Schema extends com.simperium.client.BucketSchema<com.automattic.simplenote.models.Note> {
protected static com.automattic.simplenote.models.NoteIndexer sNoteIndexer = new com.automattic.simplenote.models.NoteIndexer();

protected static com.automattic.simplenote.models.NoteFullTextIndexer sFullTextIndexer = new com.automattic.simplenote.models.NoteFullTextIndexer();

public Schema() {
autoIndex();
addIndex(com.automattic.simplenote.models.Note.Schema.sNoteIndexer);
setupFullTextIndex(com.automattic.simplenote.models.Note.Schema.sFullTextIndexer, com.automattic.simplenote.models.NoteFullTextIndexer.INDEXES);
setDefault(com.automattic.simplenote.models.Note.CONTENT_PROPERTY, "");
setDefault(com.automattic.simplenote.models.Note.SYSTEM_TAGS_PROPERTY, new org.json.JSONArray());
setDefault(com.automattic.simplenote.models.Note.TAGS_PROPERTY, new org.json.JSONArray());
setDefault(com.automattic.simplenote.models.Note.DELETED_PROPERTY, false);
setDefault(com.automattic.simplenote.models.Note.SHARE_URL_PROPERTY, "");
setDefault(com.automattic.simplenote.models.Note.PUBLISH_URL_PROPERTY, "");
}


public java.lang.String getRemoteName() {
return com.automattic.simplenote.models.Note.BUCKET_NAME;
}


public com.automattic.simplenote.models.Note build(java.lang.String key, org.json.JSONObject properties) {
return new com.automattic.simplenote.models.Note(key, properties);
}


public void update(com.automattic.simplenote.models.Note note, org.json.JSONObject properties) {
note.setProperties(properties);
note.mTitle = null;
note.mContentPreview = null;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
