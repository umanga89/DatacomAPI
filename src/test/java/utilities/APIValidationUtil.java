package utilities;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import static org.hamcrest.Matchers.*;

public class APIValidationUtil extends BaseUtil {

    public APIValidationUtil() {
        BaseUtil.logger = LogManager.getLogger(APIValidationUtil.class.getName());
    }

    public static void validateStatusCode(Response response, int statusCode) throws AssertionError {
        try {
            response.then().statusCode(statusCode);
        } catch (AssertionError e) {
            throw e;
        }
    }

    public static void validateBodyHasParameter(Response response, String key) throws AssertionError {
        try {
            response.then().body("$", hasKey(key));
        } catch (AssertionError e) {
            throw new AssertionError("\"" + key + "\" field does not exist in response body");
        }
    }

    public static void validateAllRecordsHasGivenParameter(Response response, String fieldName) throws AssertionError {
        boolean hasKey = true;
        try {
            JSONArray jsonArray = new JSONArray(response.getBody().asString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (!jsonObject.has(fieldName)) {
                    hasKey = false;
                }
            }
            Assert.assertTrue(hasKey);
        } catch (AssertionError e) {
            throw new AssertionError("field \"" + fieldName + "\" is not found in one or many records");
        }
    }

    public static void validateResponseHasNnumberOfRecords(Response response, int numberOfRecords) throws AssertionError {
        try {
            JSONArray jsonArray = new JSONArray(response.getBody().asString());
            Assert.assertEquals(jsonArray.length(), numberOfRecords, "Response did not return expected number of records");
        } catch (AssertionError e) {
            throw e;
        }
    }
}
