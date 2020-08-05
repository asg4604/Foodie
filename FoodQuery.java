import com.sun.jndi.toolkit.url.Uri;
import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
import javafx.util.Pair;

import java.util.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;

public class FoodQuery {
    private Map<String, String> query_map;
    private static final HashSet<String> REQUIRED_KEYS = new HashSet<String>(Arrays.asList(
            "api_key",
            "query"
    ));
    private static final HashSet<String> AVAILABLE_KEYS = new HashSet<String>(Arrays.asList(
            "api_key",
            "query",
            "dataType",
            "pageNumber",
            "pageSize",
            "sortBy",
            "sortOrder"
    ));
    public static final String KEY_DATATYPE = "dataType";
    public static final String KEY_API_KEY = "api_key";
    public static final String KEY_PAGE_NUMBER = "pageNumber";
    public static final String KEY_PAGE_SIZE = "pageSize";
    public static final String KEY_SORT_BY = "sortBy";
    public static final String KEY_SORT_ORDER = "sortOrder";
    public static final String KEY_QUERY = "query";
    public FoodQuery() {
        query_map = new HashMap<>();
    }

    public FoodQuery(String base_query_string, String query, String api_key) {
        this(base_query_string, api_key);
        query_map = new HashMap<>();
        query_map.put("query", query);
    }

    public FoodQuery(String query, String api_key) {
        query_map = new HashMap<>();
        query_map.put("query", query);
        query_map.put("api_key", api_key);
    }

    public FoodQuery(String base_query_string) {
        query_map = new HashMap<>();
        String[] pieces = base_query_string.split("&");
        for (int x = 0; x < pieces.length; x++) {
            String[] sub_pieces = pieces[x].split("=");
            String key = sub_pieces[0];
            String value = sub_pieces[1];
            if (!AVAILABLE_KEYS.contains(key)) {
                throw new RuntimeException();
            }
            query_map.put(key, value);
        }
    }
    public Map<String, String> getQueryMap() {
        return query_map;
    }

    public String makeQuery() {
        String query_string = "";
        Collection<String> values_collection = query_map.values();
        Collection<String> keys_collection = query_map.keySet();
        String[] values = (String[]) values_collection.toArray(new String[query_map.size()]);
        String[] keys = (String[]) keys_collection.toArray(new String[query_map.size()]);
        for (int x = 0; x < query_map.size(); x++) {
            query_string += keys[x] + "=" + values[x];
            if (x < query_map.size() - 1) {
                query_string += "&";
            }
        }
        return query_string;
    }

    public String makeURLQuery(String endpoint) {
        StringBuilder query_string = new StringBuilder();
        Collection<String> values_collection = query_map.values();
        Collection<String> keys_collection = query_map.keySet();
        String[] values = values_collection.toArray(new String[query_map.size()]);
        String[] keys = keys_collection.toArray(new String[query_map.size()]);
        for (int x = 0; x < query_map.size(); x++) {
            try {
                query_string.append(URLEncoder.encode(keys[x], StandardCharsets.UTF_8.toString())).append("=").append(URLEncoder.encode(values[x], StandardCharsets.UTF_8.toString()));
            } catch(UnsupportedEncodingException e) {
                query_string.append(keys[x]).append("=").append(values[x]);
            }
            if (x < query_map.size() - 1) {
                query_string.append("&");
            }
        }
        return endpoint + "?" + query_string.toString();
    }

    public String get(String key) {
        return query_map.get(key);
    }

    public void put(String key, String value) {
        if (!AVAILABLE_KEYS.contains(key)) {
            throw new RuntimeException("Invalid key specified: " + key);
        }
        query_map.put(key, value);
    }

    public void verifyQuery() {
        for (String required_key : REQUIRED_KEYS) {
            if (!query_map.containsKey(required_key)) {
                throw new RuntimeException("Required key missing: " + required_key);
            }
        }
    }

    @Override
    public String toString() {
        return this.makeQuery();
    }
}