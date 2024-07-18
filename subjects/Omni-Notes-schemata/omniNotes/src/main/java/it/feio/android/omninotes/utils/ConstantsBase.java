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
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public interface ConstantsBase {
    static final int MUID_STATIC = getMUID();
    java.lang.String DATABASE_NAME = "omni-notes";

    java.lang.String APP_STORAGE_DIRECTORY_SB_SYNC = "db_sync";

    java.lang.String DEV_EMAIL = "omninotes@iosue.it";

    java.lang.String GOOGLE_PLUS_COMMUNITY = "https://plus.google.com/communities/112276053772152071903";

    java.lang.String FACEBOOK_COMMUNITY = "https://www.facebook.com/OmniNotes/";

    // Used for updates retrieval
    long UPDATE_MIN_FREQUENCY = ((24L * 60L) * 60L) * 1000L;// 1 day


    java.lang.String DRIVE_FOLDER_LAST_BUILD = "https://goo.gl/gB55RE";

    // Notes swipe
    int SWIPE_MARGIN = 30;

    int SWIPE_OFFSET = 100;

    // Floating action button
    int FAB_ANIMATION_TIME = 250;

    // Notes content masking
    java.lang.String MASK_CHAR = "*";

    int THUMBNAIL_SIZE = 300;

    java.lang.String DATE_FORMAT_SORTABLE = "yyyyMMdd_HHmmss_SSS";

    java.lang.String DATE_FORMAT_SORTABLE_OLD = "yyyyMMddHHmmss";

    java.lang.String DATE_FORMAT_EXPORT = "yyyy.MM.dd-HH.mm";

    java.lang.String INTENT_KEY = "note_id";

    java.lang.String INTENT_NOTE = "note";

    java.lang.String GALLERY_TITLE = "gallery_title";

    java.lang.String GALLERY_CLICKED_IMAGE = "gallery_clicked_image";

    java.lang.String GALLERY_IMAGES = "gallery_images";

    java.lang.String INTENT_CATEGORY = "category";

    java.lang.String INTENT_GOOGLE_NOW = "com.google.android.gm.action.AUTO_SEND";

    java.lang.String INTENT_WIDGET = "widget_id";

    java.lang.String INTENT_UPDATE_DASHCLOCK = "update_dashclock";

    // Custom intent actions
    java.lang.String ACTION_START_APP = "action_start_app";

    java.lang.String ACTION_RESTART_APP = "action_restart_app";

    java.lang.String ACTION_DISMISS = "action_dismiss";

    java.lang.String ACTION_SNOOZE = "action_snooze";

    java.lang.String ACTION_POSTPONE = "action_postpone";

    java.lang.String ACTION_PINNED = "action_pinned";

    java.lang.String ACTION_SHORTCUT = "action_shortcut";

    java.lang.String ACTION_WIDGET = "action_widget";

    java.lang.String ACTION_WIDGET_TAKE_PHOTO = "action_widget_take_photo";

    java.lang.String ACTION_WIDGET_SHOW_LIST = "action_widget_show_list";

    java.lang.String ACTION_SHORTCUT_WIDGET = "action_shortcut_widget";

    java.lang.String ACTION_NOTIFICATION_CLICK = "action_notification_click";

    java.lang.String ACTION_MERGE = "action_merge";

    java.lang.String ACTION_FAB_TAKE_PHOTO = "action_fab_take_photo";

    /**
     * Used to quickly add a note, save, and perform backPress (eg. Tasker+Pushbullet) *
     */
    java.lang.String ACTION_SEND_AND_EXIT = "action_send_and_exit";

    java.lang.String ACTION_SEARCH_UNCOMPLETE_CHECKLISTS = "action_search_uncomplete_checklists";

    java.lang.String PREF_LANG = "settings_language";

    java.lang.String PREF_LAST_UPDATE_CHECK = "last_update_check";

    java.lang.String PREF_NAVIGATION = "navigation";

    java.lang.String PREF_SORTING_COLUMN = "sorting_column";

    java.lang.String PREF_PASSWORD = "password";

    java.lang.String PREF_PASSWORD_QUESTION = "password_question";

    java.lang.String PREF_PASSWORD_ANSWER = "password_answer";

    java.lang.String PREF_KEEP_CHECKED = "keep_checked";

    java.lang.String PREF_KEEP_CHECKMARKS = "show_checkmarks";

    java.lang.String PREF_EXPANDED_VIEW = "expanded_view";

    java.lang.String PREF_COLORS_APP_DEFAULT = "strip";

    java.lang.String PREF_WIDGET_PREFIX = "widget_";

    java.lang.String PREF_SHOW_UNCATEGORIZED = "settings_show_uncategorized";

    java.lang.String PREF_AUTO_LOCATION = "settings_auto_location";

    java.lang.String PREF_FILTER_PAST_REMINDERS = "settings_filter_past_reminders";

    java.lang.String PREF_FILTER_ARCHIVED_IN_CATEGORIES = "settings_filter_archived_in_categories";

    java.lang.String PREF_DYNAMIC_MENU = "settings_dynamic_menu";

    java.lang.String PREF_CURRENT_APP_VERSION = "settings_current_app_version";

    java.lang.String PREF_FAB_EXPANSION_BEHAVIOR = "settings_fab_expansion_behavior";

    java.lang.String PREF_ATTACHMENTS_ON_BOTTOM = "settings_attachments_on_bottom";

    java.lang.String PREF_SNOOZE_DEFAULT = "10";

    java.lang.String PREF_TOUR_COMPLETE = "pref_tour_complete";

    java.lang.String PREF_ENABLE_SWIPE = "settings_enable_swipe";

    java.lang.String PREF_SEND_ANALYTICS = "settings_send_analytics";

    java.lang.String PREF_PRETTIFIED_DATES = "settings_prettified_dates";

    java.lang.String PREF_ENABLE_AUTOBACKUP = "settings_enable_autobackup";

    java.lang.String PREF_ENABLE_FILE_LOGGING = "settings_enable_file_logging";

    java.lang.String PREF_BACKUP_FOLDER_URI = "backup_folder";

    java.lang.String MIME_TYPE_IMAGE = "image/jpeg";

    java.lang.String MIME_TYPE_AUDIO = "audio/amr";

    java.lang.String MIME_TYPE_VIDEO = "video/mp4";

    java.lang.String MIME_TYPE_SKETCH = "image/png";

    java.lang.String MIME_TYPE_FILES = "file/*";

    java.lang.String MIME_TYPE_IMAGE_EXT = ".jpeg";

    java.lang.String MIME_TYPE_AUDIO_EXT = ".amr";

    java.lang.String MIME_TYPE_VIDEO_EXT = ".mp4";

    java.lang.String MIME_TYPE_SKETCH_EXT = ".png";

    java.lang.String MIME_TYPE_CONTACT_EXT = ".vcf";

    java.lang.String TIMESTAMP_UNIX_EPOCH = "0";

    java.lang.String TIMESTAMP_UNIX_EPOCH_FAR = "18464193800000";

    int MENU_SORT_GROUP_ID = 11998811;

    java.lang.String MERGED_NOTES_SEPARATOR = "----------------------";

    java.lang.String PROPERTIES_PARAMS_SEPARATOR = ",";

    java.lang.String AUTO_BACKUP_DIR = "_autobackup";

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
