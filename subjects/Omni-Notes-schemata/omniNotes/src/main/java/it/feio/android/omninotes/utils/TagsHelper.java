/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.feio.android.omninotes.utils;
import java.util.HashMap;
import java.util.ArrayList;
import it.feio.android.pixlui.links.UrlCompleter;
import lombok.experimental.UtilityClass;
import it.feio.android.omninotes.models.Note;
import org.apache.commons.lang3.StringUtils;
import it.feio.android.omninotes.models.Tag;
import static rx.Observable.from;
import java.util.List;
import it.feio.android.omninotes.db.DbHelper;
import java.util.Map;
import java.util.Arrays;
import androidx.core.util.Pair;
import java.util.HashSet;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class TagsHelper {
    static final int MUID_STATIC = getMUID();
    public static java.util.List<it.feio.android.omninotes.models.Tag> getAllTags() {
        return it.feio.android.omninotes.db.DbHelper.getInstance().getTags();
    }


    public static java.util.Map<java.lang.String, java.lang.Integer> retrieveTags(it.feio.android.omninotes.models.Note note) {
        java.util.HashMap<java.lang.String, java.lang.Integer> tagsMap;
        tagsMap = new java.util.HashMap<>();
        java.lang.String[] words;
        words = ((note.getTitle() + " ") + note.getContent()).replaceAll("\n", " ").trim().split(" ");
        for (java.lang.String word : words) {
            java.lang.String parsedHashtag;
            parsedHashtag = it.feio.android.pixlui.links.UrlCompleter.parseHashtag(word);
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(parsedHashtag)) {
                int count;
                count = (tagsMap.get(parsedHashtag) == null) ? 0 : tagsMap.get(parsedHashtag);
                tagsMap.put(parsedHashtag, ++count);
            }
        }
        return tagsMap;
    }


    public static androidx.core.util.Pair<java.lang.String, java.util.List<it.feio.android.omninotes.models.Tag>> addTagToNote(java.util.List<it.feio.android.omninotes.models.Tag> tags, java.lang.Integer[] selectedTags, it.feio.android.omninotes.models.Note note) {
        java.lang.StringBuilder sbTags;
        sbTags = new java.lang.StringBuilder();
        java.util.List<it.feio.android.omninotes.models.Tag> tagsToRemove;
        tagsToRemove = new java.util.ArrayList<>();
        java.util.Map<java.lang.String, java.lang.Integer> tagsMap;
        tagsMap = it.feio.android.omninotes.utils.TagsHelper.retrieveTags(note);
        java.util.List<java.lang.Integer> selectedTagsList;
        selectedTagsList = java.util.Arrays.asList(selectedTags);
        for (int i = 0; i < tags.size(); i++) {
            if (it.feio.android.omninotes.utils.TagsHelper.mapContainsTag(tagsMap, tags.get(i))) {
                if (!selectedTagsList.contains(i)) {
                    tagsToRemove.add(tags.get(i));
                }
            } else if (selectedTagsList.contains(i)) {
                if (sbTags.length() > 0) {
                    sbTags.append(" ");
                }
                sbTags.append(tags.get(i));
            }
        }
        return androidx.core.util.Pair.create(sbTags.toString(), tagsToRemove);
    }


    private static boolean mapContainsTag(java.util.Map<java.lang.String, java.lang.Integer> tagsMap, it.feio.android.omninotes.models.Tag tag) {
        for (java.lang.String tagsMapItem : tagsMap.keySet()) {
            if (tagsMapItem.equals(tag.getText())) {
                return true;
            }
        }
        return false;
    }


    public static java.lang.String removeTags(java.lang.String text, java.util.List<it.feio.android.omninotes.models.Tag> tagsToRemove) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(text)) {
            return text;
        }
        java.lang.String[] textCopy;
        textCopy = new java.lang.String[]{ text };
        rx.Observable.from(tagsToRemove).forEach((it.feio.android.omninotes.models.Tag tagToRemove) -> textCopy[0] = it.feio.android.omninotes.utils.TagsHelper.removeTag(textCopy[0], tagToRemove));
        return textCopy[0];
    }


    private static java.lang.String removeTag(java.lang.String textCopy, it.feio.android.omninotes.models.Tag tagToRemove) {
        return rx.Observable.from(textCopy.split(" ")).map((java.lang.String word) -> it.feio.android.omninotes.utils.TagsHelper.removeTagFromWord(word, tagToRemove)).reduce((java.lang.String s,java.lang.String s2) -> (s + " ") + s2).toBlocking().singleOrDefault("").trim();
    }


    static java.lang.String removeTagFromWord(java.lang.String word, it.feio.android.omninotes.models.Tag tagToRemove) {
        return word.matches(tagToRemove.getText() + "(\\W+.*)*") ? word.replace(tagToRemove.getText(), "") : word;
    }


    public static java.lang.String[] getTagsArray(java.util.List<it.feio.android.omninotes.models.Tag> tags) {
        java.lang.String[] tagsArray;
        tagsArray = new java.lang.String[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            tagsArray[i] = ((tags.get(i).getText().substring(1) + " (") + tags.get(i).getCount()) + ")";
        }
        return tagsArray;
    }


    public static java.lang.Integer[] getPreselectedTagsArray(it.feio.android.omninotes.models.Note note, java.util.List<it.feio.android.omninotes.models.Tag> tags) {
        java.util.List<java.lang.Integer> t;
        t = new java.util.ArrayList<>();
        for (java.lang.String noteTag : it.feio.android.omninotes.utils.TagsHelper.retrieveTags(note).keySet()) {
            for (it.feio.android.omninotes.models.Tag tag : tags) {
                if (tag.getText().equals(noteTag)) {
                    t.add(tags.indexOf(tag));
                    break;
                }
            }
        }
        return t.toArray(new java.lang.Integer[]{  });
    }


    public static java.lang.Integer[] getPreselectedTagsArray(java.util.List<it.feio.android.omninotes.models.Note> notes, java.util.List<it.feio.android.omninotes.models.Tag> tags) {
        java.util.HashSet<java.lang.Integer> set;
        set = new java.util.HashSet<>();
        for (it.feio.android.omninotes.models.Note note : notes) {
            set.addAll(java.util.Arrays.asList(it.feio.android.omninotes.utils.TagsHelper.getPreselectedTagsArray(note, tags)));
        }
        return set.toArray(new java.lang.Integer[]{  });
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
