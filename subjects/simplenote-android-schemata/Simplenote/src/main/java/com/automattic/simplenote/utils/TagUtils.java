package com.automattic.simplenote.utils;
import java.util.Locale;
import android.util.Log;
import com.simperium.client.Bucket;
import java.text.Normalizer;
import com.simperium.client.BucketObjectNameInvalid;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.automattic.simplenote.models.Tag;
import java.util.List;
import com.simperium.client.BucketObjectMissingException;
import java.net.URLEncoder;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TagUtils {
    static final int MUID_STATIC = getMUID();
    private static int MAXIMUM_LENGTH_ENCODED_HASH = 256;

    /**
     * Create a tag with the @param key and @param name in the @param bucket.
     *
     * @param bucket
     * 		{@link Bucket<Tag>} in which to create the tag.
     * @param name
     * 		{@link String} to use as tag name.
     * @param index
     * 		{@link int} to use as tag index.
     */
    public static void createTag(com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> bucket, java.lang.String name, int index) throws com.simperium.client.BucketObjectNameInvalid {
        try {
            com.automattic.simplenote.models.Tag tag;
            tag = bucket.newObject(com.automattic.simplenote.utils.TagUtils.hashTag(name));
            tag.setName(name);
            tag.setIndex(index);
            tag.save();
        } catch (com.simperium.client.BucketObjectNameInvalid e) {
            android.util.Log.e("createTag", (("Could not create tag " + "\"") + name) + "\"", e);
            throw new com.simperium.client.BucketObjectNameInvalid(name);
        }
    }


    /**
     * Create a tag the @param key and @param name in the @param bucket if it does not exist.
     *
     * @param bucket
     * 		{@link Bucket<Tag>} in which to create the tag.
     * @param name
     * 		{@link String} to use creating the tag.
     */
    public static void createTagIfMissing(com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> bucket, java.lang.String name) throws com.simperium.client.BucketObjectNameInvalid {
        if (com.automattic.simplenote.utils.TagUtils.isTagMissing(bucket, name)) {
            com.automattic.simplenote.utils.TagUtils.createTag(bucket, name, bucket.count());
        }
    }


    /**
     * Find the tags that match the canonical representation of tagSearch
     *
     * @param tags
     * 		{@link List<String>} list of tags where tagSearch is going to be matched.
     * @param tagSearch
     * 		{@link String} tag to be searched.
     * @return {@link List<String>} Sublist of tags that matched tagSearch's canonical
    representation.
     */
    public static java.util.List<java.lang.String> findTagsMatch(java.util.List<java.lang.String> tags, java.lang.String tagSearch) {
        java.util.List<java.lang.String> tagsMatched;
        tagsMatched = new java.util.ArrayList<>();
        // Get the canonical hash of tag that is searched
        java.lang.String tagSearchHash;
        tagSearchHash = com.automattic.simplenote.utils.TagUtils.hashTag(tagSearch);
        for (java.lang.String tag : tags) {
            java.lang.String tagHash;
            tagHash = com.automattic.simplenote.utils.TagUtils.hashTag(tag);
            if (tagHash.equals(tagSearchHash)) {
                tagsMatched.add(tag);
            }
        }
        return tagsMatched;
    }


    /**
     * Get the canonical representation of a tag from the hashed value of the lexical variation.
     *
     * @param bucket
     * 		{@link Bucket<Tag>} in which to get tag.
     * @param lexical
     * 		{@link String} lexical variation of tag.
     * @return {@link String} canonical of tag if exists; lexical variation otherwise.
     */
    public static java.lang.String getCanonicalFromLexical(com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> bucket, java.lang.String lexical) {
        java.lang.String hashed;
        hashed = com.automattic.simplenote.utils.TagUtils.hashTag(lexical);
        try {
            com.automattic.simplenote.models.Tag tag;
            tag = bucket.getObject(hashed);
            android.util.Log.d("getCanonicalFromLexical", ((("Tag " + "\"") + hashed) + "\"") + " does exist");
            return tag.getName();
        } catch (com.simperium.client.BucketObjectMissingException e) {
            android.util.Log.d("getCanonicalFromLexical", ((("Tag " + "\"") + hashed) + "\"") + " does not exist");
            return lexical;
        }
    }


    /**
     * A canonical representation of a tag exists from the hashed value of the lexical variation.
     *
     * @param bucket
     * 		{@link Bucket<Tag>} in which to get tag.
     * @param lexical
     * 		{@link String} lexical variation of tag.
     * @return {@link Boolean} TRUE if canonical tag exists; FALSE otherwise.
     */
    public static boolean hasCanonicalOfLexical(com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> bucket, java.lang.String lexical) {
        java.lang.String hashed;
        hashed = com.automattic.simplenote.utils.TagUtils.hashTag(lexical);
        try {
            bucket.getObject(hashed);
            android.util.Log.d("hasCanonicalOfLexical", ((("Tag " + "\"") + hashed) + "\"") + " does exist");
            return true;
        } catch (com.simperium.client.BucketObjectMissingException e) {
            android.util.Log.d("hasCanonicalOfLexical", ((("Tag " + "\"") + hashed) + "\"") + " does not exist");
            return false;
        }
    }


    /**
     * Hash the tag @param name with normalizing, lowercasing, and encoding.
     *
     * @param name
     * 		{@link String} to hash as the tag kay.
     * @return {@link String} hashed to use as tag key.
     */
    public static java.lang.String hashTag(java.lang.String name) {
        try {
            java.lang.String normalized;
            normalized = java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFC);
            java.lang.String lowercased;
            lowercased = normalized.toLowerCase(java.util.Locale.US);
            java.lang.String encoded;
            encoded = java.net.URLEncoder.encode(lowercased, java.nio.charset.StandardCharsets.UTF_8.name());
            return com.automattic.simplenote.utils.TagUtils.replaceEncoded(encoded);
        } catch (java.io.UnsupportedEncodingException e) {
            // TODO: Handle encoding exception with a custom UTF-8 encoder.
            return name;
        }
    }


    /**
     * Determine if the hashed tag @param name is valid after normalizing, lowercasing, and encoding.
     *
     * @param name
     * 		{@link String} to hash as the tag kay.
     * @return {@link boolean} true if hashed value is valid; false otherwise.
     */
    public static boolean hashTagValid(java.lang.String name) {
        try {
            java.lang.String normalized;
            normalized = java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFC);
            java.lang.String lowercased;
            lowercased = normalized.toLowerCase(java.util.Locale.US);
            java.lang.String encoded;
            encoded = com.automattic.simplenote.utils.TagUtils.replaceEncoded(java.net.URLEncoder.encode(lowercased, java.nio.charset.StandardCharsets.UTF_8.name()));
            return encoded.length() <= com.automattic.simplenote.utils.TagUtils.MAXIMUM_LENGTH_ENCODED_HASH;
        } catch (java.io.UnsupportedEncodingException e) {
            // TODO: Handle encoding exception with a custom UTF-8 encoder.
            return false;
        }
    }


    /**
     * Determine if the tag with @param name is missing from @param bucket or not.
     *
     * @param bucket
     * 		{@link Bucket<Tag>} in which to create the tag.
     * @param name
     * 		{@link String} to use creating the tag.
     * @return {@link Boolean} true if tag is missing; false otherwise.
     */
    public static boolean isTagMissing(com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> bucket, java.lang.String name) {
        try {
            bucket.getObject(com.automattic.simplenote.utils.TagUtils.hashTag(name));
            android.util.Log.d("isTagMissing", ((("Tag " + "\"") + name) + "\"") + " already exists");
            return false;
        } catch (com.simperium.client.BucketObjectMissingException e) {
            android.util.Log.d("isTagMissing", ((("Tag " + "\"") + name) + "\"") + " does not exist");
            return true;
        }
    }


    /**
     * Replace certain characters in @param encoded that were not encoded with encoded value.
     *
     * All "+" characters in a tag are encoded upstream and passed as "%2B" in {@param encoded}.
     * All " " characters in a tag are encoded upstream and passed as "+" in {@param encoded}.
     * Thus, all "+" in {@param encoded} should be replaced with "%20" as an encoded space.
     *
     * @param encoded
     * 		{@link String} to replace certain characters with encoded value.
     * @return {@link String} replaced characters with encoded values.
     */
    private static java.lang.String replaceEncoded(java.lang.String encoded) {
        return encoded.replace("+", "%20").replace("*", "%2A").replace("-", "%2D").replace(".", "%2E").replace("_", "%5F");
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
