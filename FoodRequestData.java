import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodRequestData {
    public static final String KEY_DATATYPE = "dataType";
    public static final String KEY_QUERY = "query";
    public static final String KEY_SEARCH_INPUT = "generalSearchInput";
    public static final String KEY_CRITERIA = "foodSearchCriteria";
    public static final String KEY_CURRENT_PAGE = "currentPage";
    public static final String KEY_REQUIRE_ALLWORDS = "requireAllWords";
    public static final String KEY_PAGE_NUMBER = "pageNumber";
    public static final String KEY_TOTAL_HITS = "totalHits";
    public static final String KEY_FOODS = "foods";

    private ArrayList<String> dataType;
    private ArrayList<FoodItem> foodItems;
    private String query;
    private String searchInput;
    private int pageNumber;
    private boolean requireAllWords;
    private int totalHits;
    private int currentPage;

    private void handleDataTypes(JSONArray jsonDataTypes) {
        this.dataType = new ArrayList<>();
        try {
            for (int x = 0; x < jsonDataTypes.length(); x++) {
                this.dataType.add(jsonDataTypes.getString(x));
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    public FoodRequestData(String jsonContent) {
        try {
            // Get the initial objects
            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONObject foodSearchCriteriaObject = jsonObject.getJSONObject(KEY_CRITERIA);

            this.totalHits = jsonObject.getInt(KEY_TOTAL_HITS);
            this.query = foodSearchCriteriaObject.getString(KEY_QUERY);
            this.searchInput = foodSearchCriteriaObject.getString(KEY_SEARCH_INPUT);
            this.currentPage = jsonObject.getInt(KEY_CURRENT_PAGE);
            handleDataTypes(foodSearchCriteriaObject.getJSONArray(KEY_DATATYPE));
            this.pageNumber = foodSearchCriteriaObject.getInt(KEY_PAGE_NUMBER);
            this.requireAllWords = foodSearchCriteriaObject.getBoolean(KEY_REQUIRE_ALLWORDS);
            JSONArray foodArrayJson = jsonObject.getJSONArray(KEY_FOODS);
            foodItems = new ArrayList<>();
            // Loop handling creation of each food object
            for (int x = 0; x < foodArrayJson.length(); x++) {
                foodItems.add(new FoodItem(foodArrayJson.getJSONObject(x)));
            }
            printNamesofFoods();
        } catch(JSONException je) {
            je.printStackTrace();
        }
    }

    public void printNamesofFoods() {
        for (int x = 0; x < foodItems.size(); x++) {
            System.out.println(foodItems.get(x).getDescription());
        }
    }

}
