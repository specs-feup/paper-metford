package de.danoeh.antennapod.net.common;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import android.util.Log;

import okhttp3.HttpUrl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Provides methods for checking and editing a URL.
 */
public final class UrlChecker {

    /**
     * Class shall not be instantiated.
     */
    private UrlChecker() {
    }

    /**
     * Logging tag.
     */
    private static final String TAG = "UrlChecker";

    private static final String AP_SUBSCRIBE = "antennapod-subscribe://";
    private static final String AP_SUBSCRIBE_DEEPLINK = "antennapod.org/deeplink/subscribe";

    /**
     * Checks if URL is valid and modifies it if necessary.
     *
     * @param url The url which is going to be prepared
     * @return The prepared url
     */
    public static String prepareUrl(@NonNull String url) {
        url = url.trim();
        String lowerCaseUrl = url.toLowerCase(Locale.ROOT); // protocol names are case insensitive
        if (lowerCaseUrl.startsWith("feed://")) {
            Log.d(TAG, "Replacing feed:// with http://");
            return prepareUrl(url.substring("feed://".length()));
        } else if (lowerCaseUrl.startsWith("pcast://")) {
            Log.d(TAG, "Removing pcast://");
            return prepareUrl(url.substring("pcast://".length()));
        } else if (lowerCaseUrl.startsWith("pcast:")) {
            Log.d(TAG, "Removing pcast:");
            return prepareUrl(url.substring("pcast:".length()));
        } else if (lowerCaseUrl.startsWith("itpc")) {
            Log.d(TAG, "Replacing itpc:// with http://");
            return prepareUrl(url.substring("itpc://".length()));
        } else if (lowerCaseUrl.startsWith(AP_SUBSCRIBE)) {
            Log.d(TAG, "Removing antennapod-subscribe://");
            return prepareUrl(url.substring(AP_SUBSCRIBE.length()));
        } else if (lowerCaseUrl.contains(AP_SUBSCRIBE_DEEPLINK)) {
            Log.d(TAG, "Removing " + AP_SUBSCRIBE_DEEPLINK);
            String removedWebsite = url.substring(url.indexOf("?url=") + "?url=".length());
            try {
                return prepareUrl(URLDecoder.decode(removedWebsite, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return prepareUrl(removedWebsite);
            }
        } else if (!(lowerCaseUrl.startsWith("http://") || lowerCaseUrl.startsWith("https://"))) {
            Log.d(TAG, "Adding http:// at the beginning of the URL");
            return "http://" + url;
        } else {
            return url;
        }
    }

    /**
     * Checks if URL is valid and modifies it if necessary.
     * This method also handles protocol relative URLs.
     *
     * @param url  The url which is going to be prepared
     * @param base The url against which the (possibly relative) url is applied. If this is null,
     *             the result of prepareURL(url) is returned instead.
     * @return The prepared url
     */
    public static String prepareUrl(String url, String base) {
        if (base == null) {
            return prepareUrl(url);
        }
        url = url.trim();
        base = prepareUrl(base);
        Uri urlUri = Uri.parse(url);
        Uri baseUri = Uri.parse(base);
        if (urlUri.isRelative() && baseUri.isAbsolute()) {
            return urlUri.buildUpon().scheme(baseUri.getScheme()).build().toString();
        } else {
            return prepareUrl(url);
        }
    }

    public static boolean containsUrl(List<String> list, String url) {
        for (String item : list) {
            if (urlEquals(item, url)) {
                return true;
            }
        }
        return false;
    }

    public static boolean urlEquals(String string1, String string2) {
        HttpUrl url1 = HttpUrl.parse(string1);
        HttpUrl url2 = HttpUrl.parse(string2);
        if (!url1.host().equals(url2.host())) {
            return false;
        }
        List<String> pathSegments1 = normalizePathSegments(url1.pathSegments());
        List<String> pathSegments2 = normalizePathSegments(url2.pathSegments());
        if (!pathSegments1.equals(pathSegments2)) {
            return false;
        }
        if (TextUtils.isEmpty(url1.query())) {
            return TextUtils.isEmpty(url2.query());
        }
        return url1.query().equals(url2.query());
    }

    /**
     * Removes empty segments and converts all to lower case.
     * @param input List of path segments
     * @return Normalized list of path segments
     */
    private static List<String> normalizePathSegments(List<String> input) {
        List<String> result = new ArrayList<>();
        for (String string : input) {
            if (!TextUtils.isEmpty(string)) {
                result.add(string.toLowerCase(Locale.ROOT));
            }
        }
        return result;
    }
}
