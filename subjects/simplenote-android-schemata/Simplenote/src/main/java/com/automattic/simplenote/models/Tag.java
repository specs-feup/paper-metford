package com.automattic.simplenote.models;
import java.util.Locale;
import com.simperium.client.Query;
import com.simperium.client.Query.ComparisonType;
import java.util.ArrayList;
import com.simperium.client.BucketObject;
import com.simperium.client.BucketSchema;
import static com.automattic.simplenote.models.Note.TAGS_PROPERTY;
import com.simperium.client.Bucket;
import com.simperium.client.BucketObjectNameInvalid;
import org.json.JSONObject;
import java.util.List;
import com.automattic.simplenote.utils.TagUtils;
import com.simperium.client.Bucket.ObjectCursor;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Tag extends com.simperium.client.BucketObject {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String BUCKET_NAME = "tag";

    public static final java.lang.String NOTE_COUNT_INDEX_NAME = "note_count";

    public static final java.lang.String NAME_PROPERTY = "name";

    private static final java.lang.String INDEX_PROPERTY = "index";

    protected java.lang.String name = "";

    public Tag(java.lang.String key) {
        super(key, new org.json.JSONObject());
    }


    public Tag(java.lang.String key, org.json.JSONObject properties) {
        super(key, properties);
    }


    public static com.simperium.client.Query<com.automattic.simplenote.models.Tag> all(com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> bucket) {
        return bucket.query().order(com.automattic.simplenote.models.Tag.INDEX_PROPERTY).orderByKey();
    }


    public static com.simperium.client.Query<com.automattic.simplenote.models.Tag> allWithName(com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> bucket) {
        return com.automattic.simplenote.models.Tag.all(bucket).include(com.automattic.simplenote.models.Tag.NAME_PROPERTY);
    }


    public static com.simperium.client.Query<com.automattic.simplenote.models.Tag> allSortedAlphabetically(com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> bucket) {
        java.lang.String lowerCaseOrderBy;
        lowerCaseOrderBy = java.lang.String.format(java.util.Locale.US, "LOWER(%s)", com.automattic.simplenote.models.Tag.NAME_PROPERTY);
        return bucket.query().include(com.automattic.simplenote.models.Tag.NAME_PROPERTY).order(lowerCaseOrderBy);
    }


    public java.lang.String getName() {
        java.lang.String name;
        name = ((java.lang.String) (getProperty(com.automattic.simplenote.models.Tag.NAME_PROPERTY)));
        if (name == null) {
            name = getSimperiumKey();
        }
        return name;
    }


    public void setName(java.lang.String name) {
        if (name == null) {
            name = "";
        }
        setProperty(com.automattic.simplenote.models.Tag.NAME_PROPERTY, name);
    }


    public java.lang.Integer getIndex() {
        return ((java.lang.Integer) (getProperty(com.automattic.simplenote.models.Tag.INDEX_PROPERTY)));
    }


    public boolean hasIndex() {
        return getProperty(com.automattic.simplenote.models.Tag.INDEX_PROPERTY) != null;
    }


    public void setIndex(java.lang.Integer tagIndex) {
        if (tagIndex == null) {
            getProperties().remove("index");
        } else {
            setProperty("index", tagIndex);
        }
    }


    public void renameTo(java.lang.String tagOld, java.lang.String tagNew, int index, com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket) throws com.simperium.client.BucketObjectNameInvalid {
        // When old tag ID is equal to new tag hash, tag is being renamed to lexical variation.
        boolean isOldIdEqualToNewHash;
        isOldIdEqualToNewHash = getSimperiumKey().equals(com.automattic.simplenote.utils.TagUtils.hashTag(tagNew));
        // noinspection unchecked
        com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> tagsBucket;
        tagsBucket = ((com.simperium.client.Bucket<com.automattic.simplenote.models.Tag>) (getBucket()));
        // Get all notes with old tag to update.
        com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> notes;
        notes = findNotes(notesBucket, tagOld);
        while (notes.moveToNext()) {
            com.automattic.simplenote.models.Note note;
            note = notes.getObject();
            java.util.List<java.lang.String> tagsNew;
            tagsNew = new java.util.ArrayList<>();
            java.util.List<java.lang.String> tagsHash;
            tagsHash = new java.util.ArrayList<>();
            // Create lists of note's tags excluding old tag.
            for (java.lang.String tag : note.getTags()) {
                if (!tag.equals(tagOld)) {
                    tagsNew.add(tag);
                    tagsHash.add(com.automattic.simplenote.utils.TagUtils.hashTag(tag));
                }
            }
            // Add lexical tag to note.  Update this tag's name and save it.
            if (isOldIdEqualToNewHash) {
                tagsNew.add(tagNew);
                if (!getName().equals(tagNew)) {
                    setName(tagNew);
                    save();
                }
                // Add new canonical tag to note and create new tag.  Delete this tag.
            } else {
                // Add new tag if note doesn't already have same hashed tag.
                if (!tagsHash.contains(com.automattic.simplenote.utils.TagUtils.hashTag(tagNew))) {
                    tagsNew.add(com.automattic.simplenote.utils.TagUtils.getCanonicalFromLexical(tagsBucket, tagNew));
                }
                // Create new tag if canonical tag doesn't already exist.
                if (!com.automattic.simplenote.utils.TagUtils.hasCanonicalOfLexical(tagsBucket, tagNew)) {
                    com.automattic.simplenote.utils.TagUtils.createTag(tagsBucket, tagNew, index);
                }
                delete();
            }
            // Add new tags to note and save it.
            note.setTags(tagsNew);
            note.save();
        } 
        notes.close();
    }


    public com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> findNotes(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket, java.lang.String name) {
        return notesBucket.query().where(com.automattic.simplenote.models.Note.TAGS_PROPERTY, com.simperium.client.Query.ComparisonType.LIKE, name).execute();
    }


    public static class Schema extends com.simperium.client.BucketSchema<com.automattic.simplenote.models.Tag> {
        public Schema() {
            autoIndex();
        }


        public java.lang.String getRemoteName() {
            return com.automattic.simplenote.models.Tag.BUCKET_NAME;
        }


        public com.automattic.simplenote.models.Tag build(java.lang.String key, org.json.JSONObject properties) {
            return new com.automattic.simplenote.models.Tag(key, properties);
        }


        public void update(com.automattic.simplenote.models.Tag tag, org.json.JSONObject properties) {
            tag.setProperties(properties);
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
