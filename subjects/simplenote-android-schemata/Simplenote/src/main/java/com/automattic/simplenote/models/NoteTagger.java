package com.automattic.simplenote.models;
import java.util.Set;
import android.util.Log;
import com.simperium.client.Bucket;
import com.simperium.client.BucketObjectNameInvalid;
import java.util.List;
import com.automattic.simplenote.utils.TagUtils;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Listens to the notes bucket and creates tags for any non-existent tags in the tags bucket.
 */
public class NoteTagger implements com.simperium.client.Bucket.Listener<com.automattic.simplenote.models.Note> {
    static final int MUID_STATIC = getMUID();
    private com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> mTagsBucket;

    public NoteTagger(com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> tagsBucket) {
        mTagsBucket = tagsBucket;
    }


    /* When a note is saved check its array of tags to make sure there is a corresponding tag
    object and create one if necessary. Re-save all tags so their indexes are updated.
     */
    @java.lang.Override
    public void onSaveObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, com.automattic.simplenote.models.Note note) {
        // make sure we have tags
        java.util.List<java.lang.String> tags;
        tags = note.getTags();
        for (java.lang.String name : tags) {
            try {
                com.automattic.simplenote.utils.TagUtils.createTagIfMissing(mTagsBucket, name);
            } catch (com.simperium.client.BucketObjectNameInvalid e) {
                android.util.Log.e("Simplenote.NoteTagger", (("Invalid tag name " + "\"") + name) + "\"", e);
            }
        }
    }


    @java.lang.Override
    public void onDeleteObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> noteBucket, com.automattic.simplenote.models.Note note) {
    }


    @java.lang.Override
    public void onNetworkChange(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> note, com.simperium.client.Bucket.ChangeType changeType, java.lang.String key) {
    }


    @java.lang.Override
    public void onBeforeUpdateObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, com.automattic.simplenote.models.Note object) {
    }


    @java.lang.Override
    public void onLocalQueueChange(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, java.util.Set<java.lang.String> queuedObjects) {
    }


    @java.lang.Override
    public void onSyncObject(com.simperium.client.Bucket<com.automattic.simplenote.models.Note> bucket, java.lang.String key) {
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
