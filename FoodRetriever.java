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

    public static FoodRequestData buildFood(FoodQuery foodQuery) {
        String requestContent = loadData(foodQuery);
        FoodRequestData requestData = new FoodRequestData(requestContent);
        return requestData;
    }

    public static void main(String[] args)  {
        FoodQuery a = new FoodQuery("Daiya", API_KEY);
        a.put(FoodQuery.KEY_DATATYPE, "Branded");
        a.put(FoodQuery.KEY_PAGE_SIZE, "6");
        System.out.println(a.makeURLQuery(API_ENDPOINT));
        // buildFood(a);
        FoodFilter foodFilter = new FoodFilter("Milk, Corn", "Milk:Coconut,Milk:Almond");
        System.out.println(foodFilter.getRestrictedWords());
        System.out.println(foodFilter.getSaveWords("Milk"));
        System.out.println(foodFilter.deepSearch("almond juice"));
    }
}
