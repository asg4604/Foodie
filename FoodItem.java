import org.json.JSONException;
import org.json.JSONObject;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
public class FoodItem {
    public static final String KEY_FDCID = "fdcId";
    public static final String KEY_DESC = "description";
    public static final String KEY_UPC = "gtinUpc";
    public static final String KEY_BRAND_OWNER = "brandOwner";
    public static final String KEY_DATE = "publishedDate";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_DATATYPE = "dataType";

    private int fcdID;
    private String description;
    private String gtinUPC;
    private Date publishedDate;
    private String brandOwner;
    private String ingredients;
    private String dataType;

    public FoodItem(JSONObject jsonObject) {
        try {
            this.description = jsonObject.getString(KEY_DESC);
            this.gtinUPC = jsonObject.getString(KEY_UPC);
            this.fcdID = jsonObject.getInt(KEY_FDCID);
            this.brandOwner = jsonObject.getString(KEY_BRAND_OWNER);
            this.ingredients = jsonObject.getString(KEY_INGREDIENTS);
            this.dataType = jsonObject.getString(KEY_DATATYPE);
            String dateString = jsonObject.getString(KEY_DATE);
            try {
                this.publishedDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            } catch (ParseException je) {
                try {
                    this.publishedDate = new SimpleDateFormat("mm/dd/yyyy").parse(dateString);
                } catch (ParseException je2) {
                    je2.printStackTrace();
                }
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    public int getFcdID() {
        return fcdID;
    }

    public String getGtinUPC() {
        return gtinUPC;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getBrandOwner() {
        return brandOwner;
    }

    public String getDescription() {
        return description;
    }

    public String getDataType() {
        return dataType;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public String getPublishedDateAsString() {
        return publishedDate.toString();
    }
}
