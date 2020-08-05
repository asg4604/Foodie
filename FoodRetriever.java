import org.json.JSONException;
import org.json.JSONObject;

public class FoodRetriever {
    public static final String API_KEY = "6JlTqbKSysUnTBRVwJlInrQI69lh557jBwi3VzPl";
    public static final String API_URL = "https://api.nal.usda.gov";
    public static final String API_ENDPOINT = "https://api.nal.usda.gov/fdc/v1/foods/search";

    public static String loadData(FoodQuery query) {
        // Load the query into a string
        String content = null;
        try {
            content = URLConnectionReader.getText(query.makeURLQuery(API_ENDPOINT));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void buildFood(FoodQuery foodQuery) {
        String requestContent = loadData(foodQuery);
        try {
            JSONObject jsonObject = new JSONObject(requestContent);
            int totalHits = jsonObject.getInt("totalHits");
            String query = jsonObject.getString(FoodQuery.KEY_QUERY);

            System.out.println(totalHits + " " + query);
        } catch(JSONException JSONException) {
            JSONException.printStackTrace();
        }
    }

    public static void main(String[] args)  {
        FoodQuery a = new FoodQuery("Daiya", API_KEY);
        a.put(FoodQuery.KEY_DATATYPE, "Branded");
        a.put(FoodQuery.KEY_PAGE_SIZE, "1");
        System.out.println(a.makeURLQuery(API_ENDPOINT));
        buildFood(a);

    }
}
